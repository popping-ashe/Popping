<template>
  <div class="message-frame animate__animated animate__fadeIn">
    <div class="window" v-click-outside="closeshare">
      <div class="close-button" @click="closeshare"></div>
        <div class="upper-bar">
          <div class="share-text font-kor">
            <div style="margin-bottom:10px;">정말로 탈퇴하시겠습니까?</div>
            <div style="font-size:70%;">회원 정보는 한 달 동안 유지되며 <br>
기간 내에 다시 로그인하면 계정이 복구됩니다.</div>
          </div>
        </div>
      <div class="button-box">
        <div class="cancel-button" @click="closeshare">취소</div>
        <div class="send-button" @click="[deleteuser(), analyticsSignout()]">탈퇴</div>
      </div>
    </div>
  </div>
</template>

<script>
import vClickOutside from "v-click-outside";
import { mapActions } from "vuex";
const userStore = "userStore";

export default {
  name: "DeleteUser",
  directives: {
    ClickOutside: vClickOutside.directive,
  },
  methods: {
    ...mapActions(userStore, ["userdelete"]),
    deleteuser() {
      this.userdelete();
    },
    closeshare() {
      this.$emit("close"); // 이벤트를 부모 컴포넌트에 전달하여 닫히도록 함
    },
    analyticsSignout(){
      this.$gtag.event('click', {
        event_category: 'settings',
        event_label: 'signout',
        value: 'signout',
      }); 
    },
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
  height: calc(var(--vh, 1vh) * 29);
  transform: translate(-50%, -50%);
  filter: drop-shadow(2px 2px 2px rgba(0,0,0, 0.3));
  background-color: transparent;
  background-image: url("../assets/그림2.png");
  background-size: 100%;
  background-repeat: no-repeat;
  display: flex;
  justify-content: center;
  align-content: flex-start;
  flex-wrap: wrap;
}

.close-button {
  
  width: 100%;
  height: 16%;
    margin-bottom: 15px;
  /* border: 1px solid black; */
}

.upper-bar {
  width: 100%;
  height: 10%;
  display: flex;
  margin-bottom: 1%;
}

.share-text {
  width: 100%;
  height: 100%;
  display: flex;
  align-content: flex-end;
  justify-content: center;
  text-align: center;
  margin-left:  5%;
  margin-right: 10%;
  margin-top: 13%;
  font-size: 100%;
  flex-direction: column;

}

.button-box {
  width: 100%;
  height: 13%;
  top: 66%;
  position: absolute;
  /* z-index: 10; */
  display: flex;
  justify-content: space-around;
  margin-right: 4%;
  /* margin-bottom: 20%; */
}

.cancel-button {
  width: 20%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 13px;
  border: 1px solid rgb(0, 0, 0);
  border-radius: 24px;
  margin-left: 15%;
  font-weight: bold;
}

.send-button {
  width: 20%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 13px;
  /* background-color: rgb(198, 198, 198); */
  border: 1px solid rgb(0, 0, 0);
  border-radius: 24px;
  color: #ff0000;
  margin-right: 15%;
  font-weight: bold;
}
.font-kor{
  font-family: 'Galmuri9';
  font-weight: 200;
}

.font-eng{
  font-family: 'hydrophilia'
}
</style>