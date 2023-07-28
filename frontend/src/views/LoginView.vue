<template>
  <div class="frame">
    <div class="logo-frame">
      <img class="logo" src="../assets/logo_final.png" alt="">
    </div>

    <!-- <div class="progress-bar">
      <div class="loading-text">Loading...</div>
      <progress :value="progressValue" max="100"></progress>
    </div> -->

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
      progressValue: 0, // Set the initial value of the progress bar
      intervalId: null,
    };
  },
  methods: {
    //버블 배경 애니메이션

    //카카오 로그인
    kakaologin() {
      location.href = "https://kauth.kakao.com/oauth/authorize?client_id=cecace976e616b34de2152ac78d7542b&redirect_uri=http://dev.pop-ping.com:3000/oauth/kakao/callback&response_type=code"
    },

    updateProgressBar() {
      if (this.progressValue < 99) {
        this.progressValue += 1; // Update the progress value as needed
        setTimeout(this.updateProgressBar, 10); // Call the function recursively after 1 second
      } else {
        this.progressValue = 0;
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
  position: relative;
  height: calc(var(--vh, 1vh) * 100);
  width: 100%;
  overflow: hidden;
  background-image: url("../assets/background.png");
  background-size: 100%;
  background-repeat: repeat-x;
  }

.logo-frame {
  position: relative;
  width: 100%;
  display: flex;
  justify-content: center;
  top: 28%;
}

.logo {
  width: 78%;
  /* filter: drop-shadow(2px 2px 4px rgba(0,0,0,0.2)); */
  filter: brightness(115%);
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


.progress-bar {
  position: relative;
  display: flex;
  flex-direction: row;
  justify-content: center;
  flex-wrap: wrap;
  top: 29%;
}

.loading-text {
  width: 100%;
}

progress {
  width: 65%;
  height: 20px;
}
</style>
