<template>
  <div class="page-container">
    <div class="page" :class="slideClass">
      <div class="frame" style="z-index: 0">
        <ShareChoose v-if="openshare" @close="closeshare" />
        <MessageDetail
          :bubbledetail-props="bubbleDetail"
          class="message-detail"
          v-if="showReceivedDetail"
        />
        <MakeBubble v-if="showMakeWindow" />
        <div class="upper-bar">
          <div
            class="new-button font-eng"
            style="margin-left: 6%"
            v-if="pageid == getshareid"
            @click="opensharecomponent"
          >
            Share
          </div>
          <div v-else style="margin-left: 6%">
            <div class="new-button font-eng" v-if="isLogin" @click="toHome()">Home</div>
            <div class="none-button" v-else></div>
          </div>
          <div class="username font-kor" @click="generateRandomSizes()">
            <!-- 본인페이지 여부에 따라 표시 -->
            {{ this.nickname }}'s BUBBLE
          </div>
          <div
            v-if="isLogin"
            class="new-button font-eng link-button"
            style="margin-right: 6%"
            @click="goToPage('/mypage')"
          >
            My
          </div>
          <div
            v-else
            class="new-button font-eng link-button"
            style="margin-right: 6%"
            @click="$router.push('/')"
          >
            Login
          </div>
        </div>

        <div class="please-share font-kor" v-if="pageid == getshareid && showPleaseShare">
          <div class="share-ment">
            <div class="share-touch">Share</div>
            <div>&nbsp;를 터치해 링크를 공유하고</div>
          </div>
          <div>버블을 받아보세요</div>
        </div>

        <!-- 본인 페이지 여부에 따라 버블 클릭 가능/불가능 -->
        <div class="bubble-area">
          <div
            class="bubble-frame font-kor"
            @click="openDetail($event.target, index)"
            v-for="(message, index) in receivedmessages"
            :key="index"
            :style="{ width: randomBubbleSize[index], margin: randomX[index] }"
          >
            <div class="time-left" :style="{ fontSize: randomFontSize[index] }">
              {{ calLeftTime(index) }}
              <img class="bubble" src="../assets/bubble.png" />
            </div>
          </div>
        </div>

        <!-- 로그인 상태에 따라 동적으로 메세지 보내기 버튼 활성화/비활성화 -->
        <div class="bubble-make-btn font-kor" v-if="pageid == getshareid" @click="refresh()">
          <img class="bubble-make-image" src="../assets/makebubblebtn.png" alt="" />
          <div class="bubble-make-text">새로고침</div>
        </div>
        <div class="bubble-make-btn font-kor" v-else @click="openMake()">
          <img class="bubble-make-image" src="../assets/makebubblebtn.png" alt="" />
          <div class="bubble-make-text">버블 만들기</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import MessageDetail from "@/components/MessageDetail.vue";
import MakeBubble from "@/components/MakeBubble.vue";
import ShareChoose from "@/components/ShareChoose.vue";
import { mapState, mapActions } from "vuex";
// import axios from 'axios'
import { getshareidmessages, receivedUserMessage } from "@/api/user";
// import { getshareid, getshareidmessages, getUserInfo, receivedUserMessage } from "@/api/user"
const userStore = "userStore";

export default {
  name: "MainView",
  components: {
    MessageDetail,
    MakeBubble,
    ShareChoose,
  },
  data() {
    return {
      pageid: this.$route.params.pageid,
      nickname: "",
      randomBubbleSize: [],
      randomFontSize: [],
      randomX: [],
      randomSpeed: [],
      receivedmessages: [],
      bubbleDetail: "",
      getshareid: "",
      openshare: false,
      slideClass: "",
      showPleaseShare: false,
    };
  },
  created() {
    this.calLeftTime();
  },
  async mounted() {
    if (this.isLogin == true) {
      const shareid = this.$store.getters["userStore/checkShareId"];
      this.getshareid = shareid?.share_id;
      const nickname = this.$store.getters["userStore/checkUserInfo"].nickname;
      this.nickname = nickname;
      if (this.getshareid == this.pageid) {
        receivedUserMessage(
          (response) => {
            if (response.status == 200) {
              const receivedmessages = response.data;
              this.receivedmessages = receivedmessages;
              this.generateRandomSizes();
              this.generateRandomPosition();

              if (receivedmessages.length == 0) {
                this.showPleaseShare = true;
              } else {
                this.showPleaseShare = false;
              }
            } else {
              // console.log("받은 메세지 없음");
            }
          },
          async (error) => {
            console.log(error);
            await this.getnewaccesstoken();
          }
        );
      }
      if (this.pageid != this.getshareid) {
        const page = this.pageid;

        getshareidmessages(
          page,
          (response) => {
            if (response.status == 200) {
              const othermessages = response.data.data;
              this.nickname = response.data.nickname;
              this.receivedmessages = othermessages;
              this.generateRandomSizes();
              this.generateRandomPosition();
            } else {
              // console.log("잘못");
            }
          },
          (error) => {
            console.log(error);
            this.$router.push("/error");
          }
        );
      }
    }
    if (this.isLogin == false) {
      const page = this.pageid;
      getshareidmessages(
        page,
        (response) => {
          if (response.status == 200) {
            const othermessages = response.data.data;
            this.nickname = response.data.nickname;
            this.receivedmessages = othermessages;
            this.generateRandomSizes();
            this.generateRandomPosition();
          } else {
            // console.log("잘못");
          }
        },
        (error) => {
          console.log(error);
          this.$router.push("/error");
        }
      );
    }
  },

  methods: {
    goToPage(path) {
      this.slideClass = "slide-in"; // 슬라이드 효과 시작
      setTimeout(() => {
        this.$router.push(path); // 페이지 전환
      }, 300); // 애니메이션 시간 (300ms) 이후에 페이지 전환 실행
    },
    goBackPage(path) {
      this.slideClass = "slide-out"; // 슬라이드 효과 시작
      setTimeout(() => {
        this.$router.push(path); // 페이지 전환
      }, 300); // 애니메이션 시간 (300ms) 이후에 페이지 전환 실행
    },
    opensharecomponent() {
      this.openshare = true;
    },
    closeshare() {
      this.openshare = false;
    },
    openDetail(elem, idx) {
      if (this.pageid == this.getshareid) {
        elem.parentElement.parentElement.style.display = "none";
        this.bubbleDetail = this.receivedmessages[idx];
        this.bubbleDetail.content = this.bubbleDetail.content.replace(/\n/gi, "<br>");
        this.$store.commit("SHOW_DETAIL", !this.showReceivedDetail);
        this.changeread(this.bubbleDetail.message_id);
      }
    },
    openMake() {
      this.$store.commit("SHOW_MAKE_WINDOW", !this.showMakeWindow);
    },
    generateRandomSizes() {
      const minSize = 65; // Minimum bubble size (adjust as needed)
      const maxSize = 135; // Maximum bubble size (adjust as needed)
      if (this.receivedmessages) {
        for (let i = 0; i < this.receivedmessages.length; i++) {
          const randomSize = Math.floor(Math.random() * (maxSize - minSize + 1) + minSize);
          this.randomBubbleSize.push(randomSize + "px");
          // 폰트 크기 따라가게
          const randomFont = randomSize / 7;
          this.randomFontSize.push(randomFont + "px");
        }
      }
    },
    generateRandomPosition() {
      const minX = 0;
      const maxX = 35;
      const minSpeed = 2; // 버블 움직이는 속도
      const maxSpeed = 6;

      for (let j = 0; j < this.receivedmessages.length; j++) {
        const randomXPosition = Math.floor(Math.random() * (maxX - minX + 1) + minX);
        this.randomX.push(randomXPosition + "px");
        const randomS = Math.floor(Math.random() * (maxSpeed - minSpeed + 1) + minSpeed);
        this.randomSpeed.push(randomS + "s");
      }
    },
    sendmessageupdate(data) {
      this.receivedmessages.push(data);
      this.generateRandomSizes();
      this.generateRandomPosition();
    },
    toHome() {
      location.href = `http://www.pop-ping.com/main/${this.getshareid}`;
    },

    calLeftTime(index) {
      var bubbletime = new Date(this.receivedmessages[index]?.expiration_time);
      var today = new Date();
      var year = today.getFullYear();
      var month = ("0" + (today.getMonth() + 1)).slice(-2);
      var day = ("0" + today.getDate()).slice(-2);

      var dateString = year + "-" + month + "-" + day;

      var hours = ("0" + today.getHours()).slice(-2);
      var minutes = ("0" + today.getMinutes()).slice(-2);
      var seconds = ("0" + today.getSeconds()).slice(-2);
      var timeString = hours + ":" + minutes + ":" + seconds;
      var timeNow = new Date(dateString + " " + timeString);

      const diffSec = bubbletime - timeNow;
      const diffMin = Math.floor(diffSec / (60 * 1000));
      const diffHour = Math.floor(diffMin / 60);

      if (diffMin < 60) {
        return diffMin + "m";
      } else {
        return diffHour + "h";
      }
    },

    refresh() {
      window.location.reload();
    },

    ...mapActions(userStore, [
      "getnewaccesstoken",
      "showusersbubble",
      "shareidmessage",
      "changeread",
      "receivedUserMessage",
    ]),
  },

  computed: {
    ...mapState(["showReceivedDetail", "showMakeWindow"]),
    ...mapState(userStore, ["userInfo", "shareid", "updateUserData", "othermessages", "isLogin"]),
  },
};
</script>

<style scoped>
.page-container {
  position: relative;
  /* overflow: hidden; */
}

.page {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  transition: transform 0.3s;
}

.slide-in {
  transform: translateX(-100%);
}
.slide-out {
  transform: translateX(100%);
}

#index {
  display: none;
}

/* iPhone 14 Pro - 1 */
.icons {
  width: 30px;
  height: 30px;
  /* transition: all 0.3s ease-in-out; */
}
.frame {
  position: relative;
  height: calc(var(--vh, 1vh) * 100);
  width: 100%;
  overflow: hidden;
}

.upper-bar {
  position: relative;
  width: 100%;
  height: 5.6%;
  top: 4.5%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 1%;
  margin-bottom: 1%;
}

.new-button {
  width: 65px;
  height: 29px;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: white;
  box-sizing: border-box;
  border-style: solid;
  border-color: #808384;
  border-color: rgba(128, 131, 132, 1);
  border-width: 1px;
  border-radius: 16px;
  -moz-border-radius: 16px;
  -webkit-border-radius: 16px;
  box-shadow: inset 0px -3px 3px #d8d8d8;
  padding: 2px 10px 4px;
  text-decoration: none;
  font-family: "hydrophilia";
  color: black;
  font-size: 14px;
  font-weight: 600;
}
.none-button {
  width: 65px;
  height: 29px;
  margin-left: 6%;
}

.username {
  width: 176px;
  height: 48px;
  justify-content: center;
  margin-top: 2px;

  font-style: normal;
  font-weight: 400;
  font-size: 14.5px;
  line-height: 21px;
  /* or 105% */
  display: flex;
  align-items: center;
  text-align: center;
  letter-spacing: -0.32px;
  color: #000000;

  text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.25);
}

.bubble-area {
  position: absolute;
  width: 95%;
  height: 70.5%;
  top: 13%;
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;
  overflow: scroll;
  padding-top: 10px;
}

.bubble-frame {
  display: flex;
  align-items: center;
}

.time-left {
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  animation: popping1 3s ease-in-out infinite alternate;
  color: #83add6;
  font-weight: 800;
  text-shadow: -2px 0 white, 0 2px white, 2px 0 white, 0 -2px white;
  /* -webkit-text-stroke-width: 2px; */
  /* -webkit-text-stroke-color: white; */
}

.bubble {
  position: absolute;
  width: 100%;
  padding-top: 7px;
  padding-right: 2px;
  /* filter: drop-shadow(1px 1px 1px black); */
}

@keyframes popping1 {
  0% {
    transform: scale(1) translateY(0);
  }
  50% {
    transform: scale(1) translateY(-15%);
  }
  100% {
    transform: scale(1) translateY(0%);
  }
}

.bubble-make-btn {
  position: relative;
  top: 79%;
  width: 100%;
  height: 71.5px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.bubble-make-image {
  width: 78%;
  height: 90%;
}

.bubble-make-text {
  position: absolute;
  margin-bottom: 2.5%;
}

.please-share {
  width: 100%;
  position: absolute;
  font-size: 16px;
  top: 43%;
  animation: popping1 3s ease-in-out infinite alternate;
  display: flex;
  justify-content: center;
  align-items: flex-start;
  filter: drop-shadow(1px 1px 1px rgba(0, 0, 0, 0.2));
  flex-wrap: wrap;
}

.hide-share {
  display: none;
}

.share-ment {
  display: flex;
  width: 100%;
  justify-content: center;
  align-items: flex-end;
}

.share-touch {
  width: 48.8px;
  height: 21.8px;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: white;
  box-sizing: border-box;
  border-style: solid;
  border-color: #808384;
  border-color: rgba(128, 131, 132, 1);
  border-width: 1px;
  border-radius: 16px;
  -moz-border-radius: 16px;
  -webkit-border-radius: 16px;
  box-shadow: inset 0px -2px 2px #d8d8d8;
  padding: 1px 5px 2px;
  text-decoration: none;
  font-family: "hydrophilia";
  color: black;
  font-size: 10.5px;
  font-weight: 600;
}
</style>
