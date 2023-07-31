<template>
  <div class="frame" style="z-index: 0;">
    <div class="upper-bar">
      <div class="new-button font-eng">
        <div @click="$router.go(-1)">
          Back
        </div>
        
      </div>
      <div class="username font-kor">
        <!-- 본인페이지 여부에 따라 표시 -->
        Settings
      </div>
    </div>
    <br><br><br><br>

    <div class="article-counts" style="margin-left: 8%; margin-right: 8%">
      <div style="display: flex;">
        <div class="nickname ">{{ nickname }}</div>
      </div>
      <br>
      <div style="display: flex;" class="changenickname" @click="toggleNicknameEdit">
        프로필 수정
      </div>
      <hr>
      <div style="display: flex;" class="logout" @click="logoutUser">
        로그아웃
      </div>
    </div>
    <br>
    <div class="delete">popping 탈퇴</div>
    <transition name="slide">
      <div v-if="showNicknameEdit" class="nickname-edit-overlay">
        <ChangeNickname @close="closeNicknameEdit" @nickname-updated="updateNickname" />
      </div>
    </transition>

  </div>  
</template>

<script>
import { mapActions } from 'vuex'
import ChangeNickname from '@/components/ChangeNickname.vue'
const userStore = "userStore";

export default {
  name: 'SettingView',
  components :{
    ChangeNickname
  },
  data()  {
    return {
      nickname: this.$store.getters["userStore/checkUserInfo"].nickname,
      showNicknameEdit: false
    }
  },
  methods: {
    ...mapActions(userStore, ['logoutUser', 'updateUserData']),
    toggleNicknameEdit() {
      this.showNicknameEdit = !this.showNicknameEdit; // true와 false를 토글
    },
    closeNicknameEdit() {
      this.showNicknameEdit = false;
    },
    // ChangeNickname 컴포넌트로부터 전달받은 nickname 업데이트
    updateNickname(newNickname) {
      this.nickname = newNickname;
    }
  },
  async logoutUser() {
      this.logoutUser
  },
}
</script>

<style scoped>
@font-face {
    font-family: 'hydrophilia';
    src: url('../assets/fonts/hydrophilia-iced-regular.ttf') format('truetype');
    font-weight: normal;
    font-style: normal;
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
  /* justify-content: space-between; */
  align-items: center;

  }

.new-button {
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
  

  position: absolute;
  left: 6%; 
  z-index: 1; 
  }

  .username {
  flex: 1;
  width: 176px;
  height: 48px;
  justify-content: center;
  margin-top: 2px;

  font-style: normal;
  font-weight: 400;
  font-size: 15px;
  line-height: 21px;
  /* or 105% */
  display: flex;
  align-items: center;
  text-align: center;
  letter-spacing: -0.32px;
  color: #000000;
  text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.25);

  position: relative;
  z-index: 0; 
  }

  .changenickname {
  font-style: normal;
  font-weight: 400;
  font-size: 11px;
  line-height: 21px;
  color: blue;
  display: flex; 
  margin-top: -20px;
  }

  .nickname {
    font-weight: bold;
    font-size: 20px;
  }
  .logout {
    font-weight: bold;
    font-size: 15px;
  }

  .delete {
    display: flex;
    justify-content: center;
    font-size: 11px;
    color: #808384;
    text-decoration: underline;
  }

  .font-kor{
  font-family: 'Galmuri9';
  font-weight: 200;
}

.font-eng{
  font-family: 'hydrophilia'
}

.slide-enter-active,
.slide-leave-active {
  transition: transform 0.3s;
}

.slide-enter,
.slide-leave-to /* .slide-leave-active in <2.1.8 */ {
  transform: translateX(100%);
}
.nickname-edit-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 1);
  z-index: 999; 
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>