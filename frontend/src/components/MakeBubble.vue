<template>
      <!-- <input v-model="messageData.sender" type="number" placeholder="보내는 사람의 ID를 입력">
      <input v-model="messageData.retentionTime" type="number" placeholder="보관 시간을 입력">  -->
      <!-- <input v-model="messageData.shareId" type="text" placeholder="공유 ID를 입력하세요"> -->


      <!-- <textarea v-model="messageData.content" rows="4" cols="50" placeholder="메시지를 입력"></textarea> -->
      <!-- <input v-model="messageData.nickname" type="text" placeholder="보내는 사람의 nickname입력"> -->
    <!-- <button @click="sendMessage">전송</button><br>
    <button @click="closeDetail()">창 닫기</button> -->
  <div class="message-frame animate__animated animate__fadeIn">
    <div class="window">
      <div class="close-button" @click="closeDetail()"></div>
        <div class="upper-bar">
          <input class="nickname-input" placeholder="닉네임" type="text" v-model="messageData.nickname">
          <div class="time-select-box" @click="changeLifeTime()">
            <img class="time-icon" src="../assets/clock.png" alt="">
            <div class="time-selector">
              <div>{{ messageData.retentionTime }}h</div>
            </div>
          </div>
        </div>
          <div class="content-input-box">
            <textarea class="content-input" placeholder="내용" v-model="messageData.content"></textarea>
          </div>
          <!-- <input class="content-input" type="text" v-model="contents"> -->
          <div class="button-box">
            <div class="cancel-button" @click="closeDetail()">취소</div>
            <div class="send-button" @click="sendMessage()">전송</div>
          </div>

    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import {  sendUserMessage } from "@/api/user"
const userStore = "userStore";

export default {
  name: 'MakeBubble',
  components : {

  },
  data() {
    return {
      messageData: {
        content: '',
        sender: null,
        share_id: this.$route.params.pageid,
        nickname: '',
        retentionTime: 1,
      },
    };
  },
  created() {
    // 컴포넌트가 생성될 때 로그인 상태를 확인하여 로그인한 경우 sender에 member_id 할당
    if (this.isLogin == true) {
      this.messageData.sender = this.userInfo.member_id;
    }
  },
  methods: {
    sendMessage(){
      sendUserMessage(
        this.messageData, 
        (response) => {
        if (response.status == 200) {
          console.log(response.data)
          this.$store.commit('SHOW_MAKE_WINDOW', !this.showMakeWindow)
          this.$parent.sendmessageupdate(response.data);
        } else {
          console.log("잘못");
        }
      },
        (error) => {
          console.log(error);
      })
    },
  

    closeDetail() {
      this.$store.commit('SHOW_MAKE_WINDOW', !this.showMakeWindow)
    },

    changeLifeTime() {
      if (this.messageData.retentionTime === 1) {
        this.messageData.retentionTime = 3
      } else if (this.messageData.retentionTime === 3) {
        this.messageData.retentionTime = 24
      } else if (this.messageData.retentionTime === 24) {
        this.messageData.retentionTime = 1
      }
    },
  },
    computed: {
    ...mapState(['showMakeWindow']),
    ...mapState(userStore, ['isLogin','userInfo'])
  }

}
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
  top: 51.5%;
  left: 50.5%;
  width: calc(var(--vh, 1vh) * 40);
  height: 36%;
  transform: translate(-50%, -50%);
  filter: drop-shadow(2px 2px 2px rgba(0,0,0, 0.3));
  background-color: transparent;
  background-image: url("../assets/message-background.png");
  background-size: 100%;
  background-repeat: no-repeat;
  display: flex;
  justify-content: center;
  flex-wrap: wrap;
}

.close-button {
  width: 100%;
  height: 16%;
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
  background: linear-gradient(180deg, #FFFFFF 0%, #B9D7EB 99.99%, #B9D7EB 100%);
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
  margin-right: 11%;
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
  background: linear-gradient(180deg, #FFFFFF 0%, #B9D7EB 99.99%, #B9D7EB 100%);
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

  color: #f7a2bd;
  margin-left: 10px;
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


  margin-right: 10px;
}


::placeholder {
  color: gray;
  font-weight: normal;
}

.scroll::-webkit-scrollbar {
  display: none;
}
</style>