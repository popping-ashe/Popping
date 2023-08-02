<template>
  <div class="frame">
    <div class="logo-frame">
      <img class="logo" src="../assets/logo_final.png" alt="">
    </div>

    <div class="progress-bar-frame">
      <div class="loading font-pre">Loading...</div>
      <div class="progress-bar-outer">
        <div class="progress-bar-inner" :style="{ width: progressCSS}">
        </div>
      </div>
    </div>

    <div class="kakao">
      <!-- <a href="https://kauth.kakao.com/oauth/authorize?client_id=cecace976e616b34de2152ac78d7542b&redirect_uri=http://localhost:8080/oauth/kakao/callback&response_type=code"> -->
      <img class="kakao-image" src="../assets/kakao_login.png" alt="" @click="kakaologin()">
      <!-- </a> -->
    </div>

  </div>
</template>

<script>
// import Vue from 'vue';
// import ClickMessage from '../components/ClickMessage.vue';

export default {
  name: 'LoginView',
  components: {
    // ClickMessage
  },
  data() {
    return {
      progressValue: 0,
      progressCSS : '',
      intervalId: null,
      client_id: process.env.VUE_APP_KAKAO_CLIENT_ID
    };
  },
  
  methods: {
    //버블 배경 애니메이션

    //카카오 로그인
    kakaologin() {
      // console.log(process.env.VUE_APP_KAKAO_CLIENT_ID)
      location.href = `https://kauth.kakao.com/oauth/authorize?client_id=cecace976e616b34de2152ac78d7542b&redirect_uri=https://dev.pop-ping.com/oauth/kakao/callback&response_type=code`
    },

    updateProgressBar() {
      if (this.progressValue < 99) {
        this.progressValue += 0.5;
        this.progressCSS = this.progressValue + '%'
        setTimeout(this.updateProgressBar, 10);


      } else {
        this.progressValue = 0;
        this.progressCSS = this.progressValue + '%'

        this.updateProgressBar()

      }
    },
  },
  mounted() {
    // Start the progress bar animation on component mount (just for demonstration purposes)
    this.updateProgressBar();
  },
};
</script>

<style scoped>
.frame {
  height: calc(var(--vh, 1vh) * 100);
  width: 100%;
  background-image: url("../assets/background.png");
  background-size: contain;
  background-repeat: repeat-x;
  }

.logo-frame {
  position: relative;
  width: 100%;
  display: flex;
  justify-content: center;
  top: 25%;
}

.logo {
  width: 72%;
  /* filter: drop-shadow(2px 2px 4px rgba(0,0,0,0.2)); */
  filter: brightness(115%);
}

.progress-bar-frame {
  position: relative;
  width: 100%;
  top: 26.5%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.loading {
  display: flex;
  font-style: italic;
  font-size: 15px;
}

.progress-bar-outer {
  margin-top: 6px;
  width: 62%;
  height: 10px;
  border: 2px solid #96a0a7;
  border-radius: 8px;
}

.progress-bar-inner {
  height: 100%;
  border-radius: 8px;
  background-color: #c0dff4;
}

.kakao {
  position: relative;
  display: flex;
  justify-content: center;
  top: 48%;
  width: 100%;
}

.kakao-image {
  width: 55%;
  filter: drop-shadow(2px 2px 4px rgba(0, 0, 0, 0.3))
}

</style>
