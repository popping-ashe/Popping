<template>
  <div class="page-container">
    <div class="page" :class="slideClass">
      <div class="frame" style="z-index: 0">
        <ShareChoose v-if="openshare" @close="closeshare" />
        <MessageDetail
          :bubbledetail-props="bubbleDetail"
          class="message-detail"
          v-if="showReceivedDetail"
          @openReply="openReplyWindow"
          @replyID="replyID"
        />
        <!-- openReply: 답장창 열기 -->
        <!-- replyID: 메세지 상세창에서 답장하기 누르면 메세지 id emit받음 -->
      
        <MakeBubble v-if="showMakeWindow"/>
        <tutorialPopup v-if="openTutorial" @close="closeTutorialComponent"/>
        <!-- close: 답장창 닫기 -->
        <!-- replyid-props: MakeReply에 MessageDetail로부터 받은 메세지id 전달 -->
        <MakeReply v-if="showReplyWindow" @close="closeReplyWindow" :replyid-props="replyIDProp"/>
        <div class="upper-bar">
          <div
            class="new-button font-eng"
            style="margin-left: 6%"
            v-if="pageid == getshareid"
            @click="[opensharecomponent(),analyticsShare()]"
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
            @click="[$router.push('/'), pageidstore()]"
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
        
        <div class="please-make font-kor" v-if="pageid != getshareid && showPleaseMake && !letscheck">
          <div style="width:100%; display: flex; justify-content: center">
            <div class="make-button-for-ment">
              <img class="make-button-image" src="../assets/makebubblebtn.png" alt="">
              <div class="make-button-inner">버블 만들기</div>
            </div>
            <div style="margin-top: 8.2px;">&nbsp;&nbsp;를 눌러</div>
          </div>
          <div>버블을 만들어보세요</div>
        </div>

        <div class="upper-bar2">
          <div class="font-kor" v-if="isLogin && pageid == getshareid && editingBio">
            <input
              ref="bioInput"
              class="bio-input"
              type="text"
              maxlength="30"
              v-model="newBio.bio"
              placeholder="친구에게 보여줄 상태 메시지를 입력하세요"
              @keyup.esc="toggleEdit"
              @keyup.enter="[updateBio(), toggleEdit()]"
              @keyup="checkBioLength()"
              >
          </div>
          <div class="bio-input font-kor" style="margin-top: 4.5px;" v-else>
            {{ member_bio }}
          </div>
        </div>

        <!-- 본인 페이지 여부에 따라 버블 클릭 가능/불가능 -->
        <div v-if="pageid == getshareid && isLogin" class="bubble-area">

          <div
            class="bubble-frame font-kor"
            @click="[openDetail($event.target, index),analyticsBubble()]"
            v-for="(message, index) in receivedmessages"
            :key="index"
            :style="{ width: randomBubbleSize[index], height: randomBubbleSize[index], margin: randomX[index] }"
          >

            <div class="time-left" :style="{ fontSize: randomFontSize[index], color: message.state === '읽음' ? 'darkgray' : '#83add6'  }" >
              {{ calLeftTime(index) }}
              <img class="bubble" :style="message.state === '읽음' ? 'filter: grayscale(1)' : 'filter: none' " src="../assets/bubble.png"  />
            </div>
            <!-- <div v-if="message.state == '읽음'" class="time-left" :style="{ fontSize: randomFontSize[index] } " style="color: darkgray !important;">
              {{ calLeftTime(index) }}
              <img class="bubble" src="../assets/bubble_read.png"  />
            </div> -->
          </div>
        </div>

        <div v-else class="bubble-area">
          <div
            class="bubble-frame font-kor"
            v-for="(message, index) in receivedmessages"
            :key="index"
            :style="{ width: randomBubbleSize[index], height: randomBubbleSize[index], margin: randomX[index] }"
          >
            <div class="time-left" :style="{ fontSize: randomFontSize[index] }">
              {{ calLeftTime(index) }}
              <img class="bubble" src="../assets/bubble.png"  />
            </div>
          </div>
        </div>

        <!-- 로그인 상태에 따라 동적으로 메세지 보내기 버튼 활성화/비활성화 -->

        <div class="bubble-make-btn-tutorial-frame">
          <div class="tutorial-button font-eng" style="margin-left: 6%" @click="[openTutorialComponent(),analyticsTutorial()]">
            ?
          </div>
        <div class="bubble-make-btn font-kor" v-if="pageid == getshareid" @click="[refresh(),analyticsRefresh()]">
          <img class="bubble-make-image" src="../assets/makebubblebtn.png" alt="" />
          <div class="bubble-make-text">새로고침</div>
        </div>
        <div class="bubble-make-btn font-kor" v-else @click="[openMake(),analyticsMake()]">
          <img class="bubble-make-image" src="../assets/makebubblebtn.png" alt="" />
          <div class="bubble-make-text">버블 만들기</div>
        </div>
        <div class="tutorial-button font-eng" style="margin-right: 6%" @click="toggleEdit()" v-if="isLogin && pageid == getshareid && !editingBio">
          <svg xmlns="http://www.w3.org/2000/svg" height="1em" viewBox="0 0 512 512"><path d="M441 58.9L453.1 71c9.4 9.4 9.4 24.6 0 33.9L424 134.1 377.9 88 407 58.9c9.4-9.4 24.6-9.4 33.9 0zM209.8 256.2L344 121.9 390.1 168 255.8 302.2c-2.9 2.9-6.5 5-10.4 6.1l-58.5 16.7 16.7-58.5c1.1-3.9 3.2-7.5 6.1-10.4zM373.1 25L175.8 222.2c-8.7 8.7-15 19.4-18.3 31.1l-28.6 100c-2.4 8.4-.1 17.4 6.1 23.6s15.2 8.5 23.6 6.1l100-28.6c11.8-3.4 22.5-9.7 31.1-18.3L487 138.9c28.1-28.1 28.1-73.7 0-101.8L474.9 25C446.8-3.1 401.2-3.1 373.1 25zM88 64C39.4 64 0 103.4 0 152V424c0 48.6 39.4 88 88 88H360c48.6 0 88-39.4 88-88V312c0-13.3-10.7-24-24-24s-24 10.7-24 24V424c0 22.1-17.9 40-40 40H88c-22.1 0-40-17.9-40-40V152c0-22.1 17.9-40 40-40H200c13.3 0 24-10.7 24-24s-10.7-24-24-24H88z"/></svg>        </div>
        <div class="tutorial-button font-eng" style="margin-right: 6%" @click="[updateBio(), toggleEdit()]" v-if="isLogin && pageid == getshareid && editingBio">
          <svg xmlns="http://www.w3.org/2000/svg" height="1em" viewBox="0 0 448 512"><!--! Font Awesome Free 6.4.2 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license (Commercial License) Copyright 2023 Fonticons, Inc. --><path d="M438.6 105.4c12.5 12.5 12.5 32.8 0 45.3l-256 256c-12.5 12.5-32.8 12.5-45.3 0l-128-128c-12.5-12.5-12.5-32.8 0-45.3s32.8-12.5 45.3 0L160 338.7 393.4 105.4c12.5-12.5 32.8-12.5 45.3 0z"/></svg>
        </div>
        <div class="tutorial-button font-eng" style="margin-right: 6%" @click="like()" v-if="isLogin && pageid != getshareid && !(favorite_ids.includes(parseInt(this.pageid)))">
          <svg xmlns="http://www.w3.org/2000/svg" height="1em" viewBox="0 0 576 512"><!--! Font Awesome Free 6.4.2 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license (Commercial License) Copyright 2023 Fonticons, Inc. --><path d="M287.9 0c9.2 0 17.6 5.2 21.6 13.5l68.6 141.3 153.2 22.6c9 1.3 16.5 7.6 19.3 16.3s.5 18.1-5.9 24.5L433.6 328.4l26.2 155.6c1.5 9-2.2 18.1-9.6 23.5s-17.3 6-25.3 1.7l-137-73.2L151 509.1c-8.1 4.3-17.9 3.7-25.3-1.7s-11.2-14.5-9.7-23.5l26.2-155.6L31.1 218.2c-6.5-6.4-8.7-15.9-5.9-24.5s10.3-14.9 19.3-16.3l153.2-22.6L266.3 13.5C270.4 5.2 278.7 0 287.9 0zm0 79L235.4 187.2c-3.5 7.1-10.2 12.1-18.1 13.3L99 217.9 184.9 303c5.5 5.5 8.1 13.3 6.8 21L171.4 443.7l105.2-56.2c7.1-3.8 15.6-3.8 22.6 0l105.2 56.2L384.2 324.1c-1.3-7.7 1.2-15.5 6.8-21l85.9-85.1L358.6 200.5c-7.8-1.2-14.6-6.1-18.1-13.3L287.9 79z"/></svg>
        </div>
        <div class="tutorial-button font-eng" style="margin-right: 6%" @click="dislike()" v-if="isLogin && pageid != getshareid && favorite_ids.includes(parseInt(this.pageid))">
          <svg xmlns="http://www.w3.org/2000/svg" height="1em" viewBox="0 0 576 512"><!--! Font Awesome Free 6.4.2 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license (Commercial License) Copyright 2023 Fonticons, Inc. --><path d="M316.9 18C311.6 7 300.4 0 288.1 0s-23.4 7-28.8 18L195 150.3 51.4 171.5c-12 1.8-22 10.2-25.7 21.7s-.7 24.2 7.9 32.7L137.8 329 113.2 474.7c-2 12 3 24.2 12.9 31.3s23 8 33.8 2.3l128.3-68.5 128.3 68.5c10.8 5.7 23.9 4.9 33.8-2.3s14.9-19.3 12.9-31.3L438.5 329 542.7 225.9c8.6-8.5 11.7-21.2 7.9-32.7s-13.7-19.9-25.7-21.7L381.2 150.3 316.9 18z"/></svg>
        </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import MessageDetail from "@/components/MessageDetail.vue";
import MakeBubble from "@/components/MakeBubble.vue";
import ShareChoose from "@/components/ShareChoose.vue";
import tutorialPopup from "@/components/tutorialPopup.vue";
import MakeReply from "@/components/MakeReply.vue";

import { mapState, mapActions } from "vuex";
// import axios from 'axios'
import { bio, getshareidmessages, receivedUserMessage, getUserInfo, deletefavorite, postfavorite, getfavorite } from "@/api/user";
// import { getshareid, getshareidmessages, getUserInfo, receivedUserMessage } from "@/api/user"
const userStore = "userStore";

export default {
  name: "MainView",
  components: {
    MessageDetail,
    MakeBubble,
    ShareChoose,
    tutorialPopup,
    MakeReply,
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
      showPleaseMake: false,
      firstlength: '',
      letscheck: false,
      openTutorial: false,
      id:{
        "favorite_id": this.$route.params.pageid
      },
      favorite_ids:[],
      showReplyWindow: false,
      replyIDProp: "",
      newBio: {
        "bio": ""
      },
      member_bio: "",
      editingBio: false,
    };
  },
  created() {
    this.calLeftTime();
    if (this.isLogin) {
      getUserInfo(
        (response) => {
          if (response.status == 200) {
            localStorage.setItem("userinfo", JSON.stringify(response.data));
            if (response.data.terms_agreement.length!=0) {
              this.$router.push('/terms') 
            }
            } else {
              console.log("유저 정보 없음");
            }
          },
        async (error) => {
          console.log(error);
          await this.getnewaccesstoken();
        }
      );
    }
  },
  async mounted() {
    if (this.isLogin == true) {
      const shareid = this.$store.getters["userStore/checkShareId"];
      this.getshareid = shareid?.share_id;
      // const nickname = this.$store.getters["userStore/checkUserInfo"].nickname;
      const nickname = JSON.parse(localStorage.getItem("userinfo")).nickname
      const bio = JSON.parse(localStorage.getItem("userinfo")).bio
      this.nickname = nickname;
      this.member_bio = bio;


      if (this.getshareid == this.pageid) {
        receivedUserMessage(
          (response) => {
            if (response.status == 200) {
              const receivedmessages = response.data;
              this.receivedmessages = receivedmessages;
              this.generateRandomSizes();
              this.generateRandomPosition();
              this.firstlength = this.receivedmessages.length

              if (receivedmessages.length == 0) {
                this.showPleaseShare = true;
              } else {
                this.showPleaseShare = false;
              }

            if (this.member_bio == '') {
              this.member_bio = '친구에게 보여줄 상태 메시지를 입력하세요'
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
              // console.log(response.data)
              const othermessages = response.data.data;
              this.nickname = response.data.nickname;
              this.receivedmessages = othermessages;
              this.member_bio = response.data.bio

            if (this.receivedmessages.length == 0) {
                this.showPleaseMake = true
              } else {
                this.showPleaseMake = false
              }

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
         getfavorite(
          (response) => {
            if (response.status == 200) {
              const favorite_data = response.data.favorites
              favorite_data.forEach(favorite => {
                this.favorite_ids.push(favorite.share_id);
              });
            } else {
              // console.log("메세지 없음");
            }
          },
          async (error) => {
            console.log(error);
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
            // console.log(response)
            const othermessages = response.data.data;
            this.nickname = response.data.nickname;
            this.receivedmessages = othermessages;
            this.member_bio = response.data.bio

            if (this.receivedmessages.length == 0) {
                this.showPleaseMake = true
              } else {
                this.showPleaseMake = false
              }

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
    openTutorialComponent() {
      this.openTutorial = true;
    },
    closeTutorialComponent() {
      this.openTutorial = false;
    },
    openDetail(elem, idx) {
      if (this.pageid == this.getshareid) {
        // elem.parentElement.parentElement.style.display = "none";
        elem.parentElement.parentElement.style.filter = "grayscale(1)";
        this.bubbleDetail = this.receivedmessages[idx];
        this.bubbleDetail.content = this.bubbleDetail.content.replace(/\n/gi, "<br>");
        this.$store.commit("SHOW_DETAIL", !this.showReceivedDetail);
        this.changeread(this.bubbleDetail.message_id);
        this.firstlength = this.firstlength - 1
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
      if (this.receivedmessages.length != 0) {
        this.letscheck = true
      }
    },
    toHome() {
      location.href = `${process.env.VUE_APP_BASE_URL}/main/${this.getshareid}`;
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
    like() {
      postfavorite(
        this.id,
        (response) => {
          if (response.status == 200) {
            // console.log(response.data.favorite_id)
            this.favorite_ids.push(response.data.favorite_id)
            this.$toast.center("즐겨찾기에 등록되었습니다.")
            // console.log(this.favorite_ids)
          } else {
            // console.log("잘못");
          }
        },
        (error) => {
          console.log(error);
        }
        )
      },
    dislike() {
      deletefavorite(
        this.id,
        (response) => {
          if (response.status == 200) {
            const indexToRemove = this.favorite_ids.indexOf(parseInt(this.pageid));
            this.favorite_ids.splice(indexToRemove, 1)
            this.$toast.center("즐겨찾기에서 해제되었습니다.")
          } else {
            // console.log("잘못");
          }
        },
        (error) => {
          console.log(error);
        }
      )
    },

    updateBio() {
      if (this.newBio.bio == '') {
        this.member_bio = '친구에게 보여줄 상태 메시지를 입력하세요'
      } else {
        this.member_bio = this.newBio.bio
      }
      bio(
          this.newBio,
          (response) => {
            if (response.status == 200) {
              // console.log(response.data.bio)
              const userinfo = JSON.parse(localStorage.getItem("userinfo"))
              userinfo.bio = response.data.bio
              localStorage.setItem("userinfo", JSON.stringify(userinfo));
              // console.log(JSON.parse(localStorage.getItem("userinfo")).bio)
            }
          },
          (error) => {
            console.log(error);
          }
        )
    },

    toggleEdit() {
      this.editingBio = !this.editingBio
      if (this.editingBio) {
        this.$toast.center('변경 후 엔터 혹은 체크 버튼을 터치하세요')
        if (this.member_bio == '친구에게 보여줄 상태 메시지를 입력하세요') {
          this.member_bio = ''
        }
        this.newBio.bio = this.member_bio
        this.$nextTick(() => {
          this.$refs.bioInput.focus();
        });
      }
    },

    refresh() {
      window.location.reload();
    },

    openReplyWindow() {
      this.showReplyWindow = true
    },
    
    closeReplyWindow() {
      this.showReplyWindow = false
    },

    replyID(value) {
      this.replyIDProp = value
    },

    checkBioLength() {
      if (this.newBio.bio.length == 30) {
        this.$toast.center("상태 메시지 최대 길이는 30글자입니다.");
      }
    },
    pageidstore() {
      localStorage.setItem("pageid", this.pageid)
    },


    analyticsShare(){
      this.$gtag.event('click', {
        event_category: 'main',
        event_label: 'shareWindow',
        value: 'shareWindow',
      }); 
    },
    analyticsRefresh(){
      this.$gtag.event('click', {
        event_category: 'main',
        event_label: 'refresh',
        value: 'refresh',
      }); 
    },
    analyticsBubble(){
      this.$gtag.event('click', {
        event_category: 'main',
        event_label: 'readBubble',
        value: 'readBubble',
      }); 
    },
    analyticsMake(){
      this.$gtag.event('click', {
        event_category: 'main',
        event_label: 'makeBubble',
        value: 'makeBubble',
      }); 
    },
    analyticsTutorial(){
      this.$gtag.event('click', {
        event_category: 'main',
        event_label: 'tutorial',
        value: 'tutorial',
      }); 
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
.upper-bar2 {
  position: relative;
  width: 100%;
  height: 5.6%;
  top: 3.3%;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 1%;
  margin-bottom: 1%;
}

.bio-input {
  font-size: 12.5px;
  text-shadow: 1px 1px 1px rgba(0, 0, 0, 0.1);
  text-align: center;
  outline: none;
  width: 10000px;
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
  height: 69%;
  top: 16.5%;
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
  width: 75%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}

.bubble-make-image {
  width: 90%;
  height: 90%;
}

.bubble-make-text {
  position: absolute;
  margin-bottom: 7px;
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

.please-make {
  width: 100%;
  position: absolute;
  font-size: 16px;
  top: 43%;
  animation: popping1 3s ease-in-out infinite alternate;
  display: flex;
  justify-content: center;
  align-items: center;
  filter: drop-shadow(1px 1px 1px rgba(0, 0, 0, 0.2));
  flex-wrap: wrap;
}

.make-button-for-ment {
  width: 180px;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-bottom: 5px;
}

.make-button-image {
  width: 100%;
  display: flex;
  justify-content: center;
}

.make-button-inner {
  position: absolute;
  font-size : 12px;
  margin-bottom: 2px;
  margin-left: 5.5px;
}

.bubble-make-btn-tutorial-frame {
  position: relative;
  top: 74%;
  width: 100%;
  height: 71.5px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.tutorial-button {
  width: 40px;
  height: 40px;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: white;
  box-sizing: border-box;
  border-style: solid;
  border-color: #808384;
  border-color: rgba(128, 131, 132, 1);
  border-width: 1px;
  border-radius: 50px;
  -moz-border-radius: 50px;
  -webkit-border-radius: 50px;
  box-shadow: inset 0px -3px 3px #d8d8d8;
  padding: 2px 10px 4px;
  text-decoration: none;
  font-family: "hydrophilia";
  color: black;
  font-size: 16px;
  font-weight: 600;
}
.bio-box {
  width: 88%;
  height: 29px;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: rgba(255, 255, 255, 0);
  box-sizing: border-box;
  border-style: solid;
  border-color: #808384;
  border-color: rgba(128, 131, 132, 1);
  border-width: 1px;
  border-radius: 16px;
  -moz-border-radius: 16px;
  -webkit-border-radius: 16px;
  /* box-shadow: inset 0px -3px 3px #d8d8d8; */
  padding: 2px 10px 4px;
  text-decoration: none;
  font-family: "hydrophilia";
  color: black;
  font-size: 14px;
  font-weight: 600;
}

</style>
