<template>
  <div class="frame" style="z-index: 0;">
    <MessageDetail :bubbledetail-props="bubbleDetail" class="message-detail" v-if="showReceivedDetail"/>
    <MakeBubble v-if="showMakeWindow"/>
    <div class="upper-bar">
      <div class="btn-top font-btn" style="margin-left: 6%">
        <div v-if="pageid == shareid" @click="shareCopy()">
          Share
          <!-- <img src="../assets/홈.png" alt="홈" class="icons" > -->
        </div>
        <div v-else @click="toHome()">
          Home
        </div>
      </div>
      <div class="username font-hangeul" @click="generateRandomSizes()">
        <!-- 본인페이지 여부에 따라 표시 -->
        {{ this.nickname }}'s BUBBLE
      </div>
      <div class="btn-top" style="margin-right: 6%" @click="$router.push('/mypage')">
          My
      </div>
    </div>

    <!-- 본인 페이지 여부에 따라 버블 클릭 가능/불가능 -->
    <div class="bubble-area">
      <div v-for="(message, index) in receivedmessages" :key="index" :style="{ width: randomBubbleSize[index], margin: randomX[index] }">
        <img class="bubble" @click="openDetail($event.target, index)" :id="index" src="../assets/bubble.png">
      </div>
      <!-- <img class="bubble1" src="../assets/bubble.png" alt="">
      <img class="bubble2" src="../assets/bubble.png" alt=""> -->
    </div>


    <!-- 로그인 상태에 따라 동적으로 메세지 보내기 버튼 활성화/비활성화 -->
    <div class="bubble-make-btn font-hangeul" v-if="pageid != shareid">
      <img class="bubble-make-image" src="../assets/makebubblebtn.png" @click="openMake()" alt="">
      <div class="bubble-make-text">
        버블 만들기
      </div>
    </div>

  </div>
</template>



<script>
import MessageDetail from '@/components/MessageDetail.vue'
import MakeBubble from '@/components/MakeBubble.vue'
import { mapState, mapActions } from 'vuex'
// import axios from 'axios'
import { getshareid, getshareidmessages, receivedUserMessage } from "@/api/user"
// import { getshareid, getshareidmessages, getUserInfo, receivedUserMessage } from "@/api/user"
const userStore = "userStore";

export default {
  name: 'MainView',
  components: {
    MessageDetail,
    MakeBubble,
  },
  data() {
    return {
      pageid: this.$route.params.pageid,
      nickname:"",
      randomBubbleSize: [],
      randomX: [],
      randomY: [],
      receivedmessages: [],
      bubbleDetail: "",
      shareid:"",
    };
  },
  created() {
    // this.updateUserData()
  },
  mounted() {
    // this.updateUserData()
    getshareid(
      (response) => {
        if (response.status == 200) {
          const shareid = this.$store.getters["userStore/checkShareId"];
          this.shareid = shareid.share_id
          // sessionStorage.setItem("shareid", JSON.stringify(response.data));
          // console.log(userStore.state.userInfo.nickname);
          console.log(this.shareid)
          console.log(this.pageid)

      receivedUserMessage(
        (response) => {
          if (response.status == 200) {
            // sessionStorage.setItem("receivedmessages", JSON.stringify(response.data));
            // console.log(response);
            const receivedmessages = response.data
            this.receivedmessages = receivedmessages
            console.log(receivedmessages)
            this.generateRandomSizes();
            this.generateRandomPosition();
          } else {
            console.log("받은 메세지 없음");
          }
        },
        async (error) => {
          console.log(error);
          console.log('받은메세지 받아오기 에러');
        }
      )
        } else {
          console.log("shareid 없음");
        }
      },
      async (error) => {
        console.log(error);
        // console.log(this.shareid);

      }
    );
    // const shareid = this.$store.getters["userStore/checkShareId"];
    // this.shareid = shareid.share_id
    // console.log(this.shareid)
    // console.log(this.pageid)
    // console.log(this.shareid)
    // console.log(this.pageid)

    if (this.pageid != this.shareid) {
      const page = this.pageid;

      getshareidmessages(
        page, 
        (response) => {
        if (response.status == 200) {
          console.log(response.data)
          // console.log(response.data.nickname)
          const othermessages = response.data.data
          console.log(othermessages)
          this.nickname = response.data.nickname
          console.log(this.nickname)
          this.receivedmessages = othermessages
          this.generateRandomSizes();
          this.generateRandomPosition();
        } else {
          console.log("잘못");
        }
      },
        (error) => {
        console.log(error);
      })
    }
    console.log(this.nickname)
  },

  methods: {
    openDetail(elem, idx) {
      if (this.pageid == this.shareid) {
        elem.style.display="none";
        this.bubbleDetail = this.receivedmessages[idx]
        this.$store.commit('SHOW_DETAIL', !this.showReceivedDetail)
        // const messageid = this.bubbleDetail.message_id
        // console.log(messageid)
        this.changeread(this.bubbleDetail.message_id);
      }
    },
    openMake() {
      this.$store.commit('SHOW_MAKE_WINDOW', !this.showMakeWindow)
    },
    generateRandomSizes() {
        const minSize = 65; // Minimum bubble size (adjust as needed)
        const maxSize = 135; // Maximum bubble size (adjust as needed)
        if (this.receivedmessages) {

          for (let i = 0; i < this.receivedmessages.length; i++) {
            const randomSize = Math.floor(Math.random() * (maxSize - minSize + 1) + minSize);
            this.randomBubbleSize.push(randomSize + 'px');
        }
        }
      },
    generateRandomPosition() {
      const minX = 0;
      const maxX = 35;
      const minY = 0;     // Y는 필요 없어짐!
      const maxY = 3;

      for (let j = 0; j < this.receivedmessages.length; j++) {
        const randomXPosition = Math.floor(Math.random() * (maxX - minX + 1 ) + minX);
        this.randomX.push(randomXPosition + 'px');
        const randomYPosition = Math.floor(Math.random() * (maxY - minY + 1 ) + minY);
        this.randomY.push(randomYPosition + '%');
      }
    },
    shareCopy() {
      // var url = ''
      // var textarea = document.createElement("textarea")
      // document.body.appendChild(textarea)
      // url = window.document.location.href
      // textarea.value = url
      // textarea.select()
      // document.execCommand("copy")
      // document.body.removeChild(textarea)
      this.$copyText(window.document.location.href)
      this.$toast.center('복사되었습니다')
    },
    sendmessageupdate(data){
      console.log("test");
      console.log(data);
      this.receivedmessages.push(data);
      this.generateRandomSizes();
      this.generateRandomPosition();
    },
    toHome() {
      // console.log(this.shareid)
      location.href = `http://dev.pop-ping.com:3000/main/${this.shareid}`
    },
    ...mapActions(userStore, ["showusersbubble", "shareidmessage","changeread", "receivedUserMessage"])
 },


  computed: {
    ...mapState(['showReceivedDetail', 'showMakeWindow']),
    ...mapState( userStore, ['userInfo', 'shareid', 'updateUserData','othermessages']),
  }
}


</script>

<style scoped>
@font-face {
    font-family: 'hydrophilia';
    src: url('../assets/fonts/hydrophilia-iced-regular.ttf') format('truetype');
    font-weight: normal;
    font-style: normal;
}

.btn-top { 
  width: 65px;
  height: 29px;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: white;
  box-sizing: border-box;
  border-style : solid;
  border-color : #808384;
  border-color : rgba(128, 131, 132, 1);
  border-width : 1px;
  border-radius : 16px;
  -moz-border-radius : 16px;
  -webkit-border-radius : 16px;
  box-shadow: inset 0px -3px 3px #D8D8D8;
  padding :2px 10px 4px;
  text-decoration: none;
  font-family: 'hydrophilia';
  color:black;
  font-size: 14px;
  font-weight: 600;
}

#index {
  display:none;
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

  }

.share-ellipse {
  box-sizing: border-box;
  width: 40px;
  height: 40px;
  margin-left: 6%;
  background: #FFFFFF;
  border: 1px solid #505050;
  border-radius: 50%;
  box-shadow: 1px 1px 3px rgba(0, 0, 0, 0.25);
  transition: all ease 0.4s;
  }

  .share-ellipse:hover {
    filter: brightness(70%);
    transition: all ease 0.4s;
  }

.mypage-ellipse {
  box-sizing: border-box;

  width: 40px;
  height: 40px;
  margin-right: 6%;
  background: #FFFFFF;
  border: 1px solid #505050;
  border-radius: 50%;
  box-shadow: 1px 1px 3px rgba(0, 0, 0, 0.25);
  transition: all ease 0.4s;
  }

.mypage-ellipse:hover {
  filter: brightness(70%);
  transition: all ease 0.4s;
  }

.share-emoji {
  position: relative;
  width: 32px;
  height: 32px;
  left: 12px;
  top: 4px;
}

.tohome-emoji {
  position: relative;
  width: 32px;
  height: 32px;
  left: 10.5px;
  top: 4.2px;
}

.mypage-emoji {
  position: relative;
  width: 32px;
  height: 32px;
  left: 12px;
  top: 4px;
}

.username {
  width: 176px;
  height: 48px;
  justify-content: center;
  margin-top: 2px;

  font-family: 'Inter';
  font-style: normal;
  font-weight: 400;
  font-size: 14.5px;
  line-height: 21px;
  /* or 105% */
  display: flex;
  align-items: center;
  text-align: center;
  letter-spacing: -0.32px;
  font-family: '글씨체';
  color: #000000;

  text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.25);
  }

.bubble-area {
  position: absolute;
  width: 95%;
  height: 71.3%;
  top: 13%;
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;
  overflow: scroll;
  }

::-webkit-scrollbar {
  display: none;
}

.bubble {
  position: relative;
  width: 100%;
  margin: 5px;
  /* filter: drop-shadow(1px 1px 1px black); */
  animation: popping1 3s ease-in-out infinite alternate;
}
@keyframes popping1 {
  0% {
    transform: scale(1) translateY(0);
  }
  50% {
    transform: scale(1) translateY(-10%);
  }
  100% {
    transform: scale(1) translateY(0%);
  }
}

.bubble-make-btn {
  position: relative;
  top: 65%;
  width: 100%;
  height: 40%;
  display: flex;
  justify-content: center;
  align-items: center;
  transition: all ease 0.4s;
  }

.bubble-make-image {
  width: 95%;
  height: 36%;
}

.bubble-make-text {
  position: absolute;
  margin-bottom: 5%;
}

.bubble-make-btn:hover {
  transition: all ease 0.4s;
}

.font-stardust{
  font-family: 'Stardust'
}
.font-btn{
  font-family: 'hydrophilia'
}

.font-hangeul{
  font-family: 'Galmuri9';
  font-weight: 200 !important;
}
</style>
