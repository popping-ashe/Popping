<template>
  <div class="message-frame animate__animated animate__fadeIn">
    <div class="window font-pre" v-click-outside="closeDetail">
      <div class="close-button" @click="[closeDetail(),analyticsCancel()]"></div>
      <div class="upper-bar">
        <input
          class="nickname-input"
          maxlength="10"
          placeholder="닉네임"
          type="text"
          v-model="messageData.nickname"
          @keyup="checkNicknameLength"
        />
        <!-- 임시! 답장 가능여부 표시 -->
        <div class="time-select-box" @click="changeLifeTime()" :style="{ marginRight: isLogin ? '0' : '11%' }">
          <img class="time-icon" src="../assets/clock.png" alt="" />
          <div class="time-selector">
            <div>{{ messageData.retentionTime }}h</div>
          </div>
        </div>
        <label v-if="isLogin" class="switch">
          <input type="checkbox" @click="toggleReplyAvailability()">
          <div class="slider round"></div>
        </label>
      </div>
      <div class="content-input-box">
        <textarea
          class="content-input"
          maxlength="200"
          placeholder="내용"
          v-model="messageData.content"
          @keyup="checkContentLength"
        ></textarea>
      </div>
      <!-- <input class="content-input" type="text" v-model="contents"> -->
      <div class="button-box">
        <div class="cancel-button" @click="[closeDetail(), analyticsCancel()]">취소</div>
        <div class="send-button" @click="[sendMessage(),analyticsSend()]">전송</div>
      </div>
      
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";
import { sendUserMessage } from "@/api/user";
import vClickOutside from "v-click-outside";
const userStore = "userStore";

export default {
  name: "MakeBubble",
  directives: {
    clickOutside: vClickOutside.directive,
  },
  components: {},
  data() {
    return {
      messageData: {
        content: "",
        sender: null,
        share_id: this.$route.params.pageid,
        nickname: "",
        retentionTime: 24,
        reply_available: "N",
      },
    };
  },
  created() {
    // 컴포넌트가 생성될 때 로그인 상태를 확인하여 로그인한 경우 sender에 member_id 할당
    if (this.isLogin == true) {
      this.messageData.sender = this.userInfo.member_id;
      this.reply_available = "Y"
    } else {
      this.reply_available = "N"
    }
    if (this.isLogin) {
      this.messageData.reply_available = "Y"
    }
    
  },
  methods: {
    toggleReplyAvailability() {
      this.messageData.reply_available = this.messageData.reply_available === 'Y' ? 'N' : 'Y';
      if (this.messageData.reply_available == 'Y') {
        this.$toast.top("답장을 받을 수 있습니다.")
      } else {
        this.$toast.top("답장을 받을 수 없습니다.")
      }
    },
    sendMessage() {
      // 빈칸일때 익명, 내용없음으로 가짐
      if (this.messageData.nickname.trim() === "") {
        this.messageData.nickname = "익명"
      }
      // 내용 없을때 안보내지게
      if (this.messageData.content == "")
      {
        this.$toast.center("내용을 입력해주세요.")
      } else {
        sendUserMessage(
          this.messageData,
          (response) => {
            if (response.status == 200) {
              this.$store.commit("SHOW_MAKE_WINDOW", !this.showMakeWindow);
              this.$parent.sendmessageupdate(response.data);
              this.$toast.center("버블을 보냈습니다.");
            } else {
              // console.log("잘못");
            }
          },
          (error) => {
            console.log(error);
          }
        );
      }
    },

    closeDetail() {
      this.$store.commit("SHOW_MAKE_WINDOW", !this.showMakeWindow);
    },

    changeLifeTime() {
      if (this.messageData.retentionTime === 24) {
        this.messageData.retentionTime = 12;
      } else if (this.messageData.retentionTime === 12) {
        this.messageData.retentionTime = 6;
      } else if (this.messageData.retentionTime === 6) {
        this.messageData.retentionTime = 3;
      } else if (this.messageData.retentionTime === 3) {
        this.messageData.retentionTime = 24;
      }
    },

    checkNicknameLength() {
      if (this.messageData.nickname.length == 10) {
        this.$toast.center("닉네임 최대 길이는 10글자입니다.");
      }
    },

    checkContentLength() {
      if (this.messageData.content.length == 200) {
        this.$toast.center("내용 최대 길이는 200글자입니다.");
      }
    },

    analyticsSend(){
      this.$gtag.event('click', {
        event_category: 'message',
        event_label: 'send',
        value: 'send',
      }); 
    },
    analyticsCancel(){
      this.$gtag.event('click', {
        event_category: 'message',
        event_label: 'cancel',
        value: 'cancel',
      }); 
    },
  },
  computed: {
    ...mapState(["showMakeWindow"]),
    ...mapState(userStore, ["isLogin", "userInfo"]),
  },
};
</script>

<style scoped>
.message-frame {
  position: fixed;
  top: 0px;
  left: 0px;
  width: 100%;
  height: 100%;
  z-index: 999;
  background: rgba(0, 0, 0, 0.3);
}

.window {
  position: absolute;
  top: calc(var(--vh, 1vh) * 50.5);
  left: 50.5%;
  width: calc(var(--vh, 1vh) * 40);
  height: calc(var(--vh, 1vh) * 36);
  transform: translate(-50%, -50%);
  filter: drop-shadow(2px 2px 2px rgba(0, 0, 0, 0.3));
  background-color: transparent;
  background-image: url("../assets/message-background.png");
  background-size: 100%;
  background-repeat: no-repeat;
  display: flex;
  /* justify-content: center; */
  flex-wrap: wrap;
}

.close-button {
  width: 35%;
  height: 15%;
  /* border: 1px solid black; */
}

.upper-bar {
  width: 100%;
  height: 10%;
  display: flex;
  justify-content: space-between;
}

.nickname-input {
  width: 35%;
  height: 100%;
  border: 1px solid darkslategray;
  border-radius: 0px 20px 20px 20px;
  outline: none;
  font-size: 85%;
  font-weight: bold;
  background: linear-gradient(180deg, #ffffff 0%, #b9d7eb 99.99%, #b9d7eb 100%);
  margin-left: 6.5%;
  padding-left: 3%;
  padding-right: 3.4%;
}

.time-select-box {
  display: flex;
  width: 20%;
  height: 100%;
  border: 1px solid darkslategray;
  border-radius: 20px;
  align-items: center;
  /* margin-right: 11%; */
}
.time-select-box2 {
  display: flex;
  width: 20%;
  height: 100%;
  border: 1px solid darkslategray;
  border-radius: 20px;
  align-items: center;
  justify-content: center;
  font-size: 12px;
  /* margin-right: 11%; */
}

.time-icon {
  width: 20%;
  height: auto;
  margin-left: 9%;
}

.time-selector {
  display: flex;
  margin-left: 7%;
  width: 55%;
  height: 60%;
  background-color: #abd7f2;
  text-align: center;
  border-radius: 16px;
  align-items: center;
  justify-content: center;
  font-size: 68%;
}

.content-input-box {
  width: 100%;
  height: 51%;
  border: 1px solid darkslategray;
  border-radius: 0px 20px 20px 20px;
  padding: 9px 12px 5px 12px;
  resize: none;
  background: linear-gradient(180deg, #ffffff 0%, #b9d7eb 99.99%, #b9d7eb 100%);
  margin-bottom: 6%;
  margin-left: 6.5%;
  margin-right: 11%;
  padding-left: 3%;
  padding-top: 2.3%;
  scroll-padding: 5%;
}

.content-input {
  width: 100%;
  height: 78%;
  font-size: 85%;
  resize: none;
  outline: none;
}

.button-box {
  width: 100%;
  height: 7%;
  top: 78%;
  position: absolute;
  z-index: 10;
  display: flex;
  justify-content: space-around;
  margin-right: 4%;
}

.cancel-button {
  width: 20%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 12px;
  background-color: white;
  border-radius: 24px;
  border: 1px solid darkslategray;
  color: #f7a2bd;
  margin-left: 5%;
}

.send-button {
  width: 20%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 12px;
  background-color: white;
  border-radius: 24px;
  border: 1px solid darkslategray;
  margin-right: 10%;
}

::placeholder {
  color: gray;
  font-weight: normal;
}

.scroll::-webkit-scrollbar {
  display: none;
}

.switch {
  position: relative;
  display: inline-block;
  /* width: 60px; */
  /* height: 34px; */
  width: 18.5%;
  height: 100%;
    margin-right: 12%;
}

.switch input {
  display: none;
}

.slider {
  position: absolute;
  cursor: pointer;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  /* background: linear-gradient(180deg, #ffffff 0%, #b9d7eb 99.99%, #b9d7eb 100%); */
  
  background: rgba(158, 216, 245, 0.5);
  border: 1px solid darkslategray;
  -webkit-transition: 0.4s;
  transition: 0.4s;
}

.slider:before {
  position: absolute;
  content: "";
  height: 75%;
  width: 33%;
  right: 12.5%;
  bottom: 12%;
  border: 1px solid darkslategray;
  background: white;
  -webkit-transition: 0.4s;
  transition: 0.4s;
  filter: drop-shadow(1px 1px 1px rgba(0,0,0,0.1));
}


input:checked + .slider {
  background: lightgray;
}

input:checked + .slider:before {
  -webkit-transform: translateX(-124%);
  -ms-transform: translateX(-124%);
  transform: translateX(-124%);
}

.slider.round {
  border-radius: 34px;
}

.slider.round:before {
  border-radius: 50%;
}
</style>
