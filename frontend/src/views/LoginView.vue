<template>
  <div class="frame">
    <div class="logo-frame">
      <img class="logo" src="../assets/logo_final.png" alt="" />
    </div>

    <div class="kakao">
      <div class="box-behind"></div>
      <div class="box-front font-pre" @click="kakaologin()">
        <img class="kakao-logo" src="../assets/kakao_logo.png" alt="" />
        <div>카카오 로그인</div>
      </div>
    </div>

    <div class="google">
      <div class="box-behind"></div>
      <div class="box-front-google font-pre" @click="googlelogin()">
        <img class="google-logo" src="../assets/google_login.png" alt="" />
        <div>구글 로그인</div>
      </div>
    </div>

  </div>
</template>

<script>
// import Vue from 'vue';
// import ClickMessage from '../components/ClickMessage.vue';
import { mapState } from "vuex";
const userStore = "userStore";
export default {
  name: "LoginView",
  components: {
    // ClickMessage
  },
  data() {
    return {
      progressValue: 0,
      progressCSS: "",
      intervalId: null,
      client_id: process.env.VUE_APP_KAKAO_CLIENT_ID,
    };
  },

  methods: {
    //버블 배경 애니메이션

    //카카오 로그인
    kakaologin() {
      location.href = `https://kauth.kakao.com/oauth/authorize?client_id=${process.env.VUE_APP_KAKAO_CLIENT_ID}&redirect_uri=${process.env.VUE_APP_BASE_URL}/oauth/kakao/callback&response_type=code`;
    },
    // 구글 로그인
    googlelogin() {
      location.href = `https://accounts.google.com/o/oauth2/v2/auth?scope=profile&client_id=${process.env.VUE_APP_GOOGLE_CLIENT_ID}&redirect_uri=${process.env.VUE_APP_BASE_URL}/oauth/google/callback&response_type=code`;
    },

    updateProgressBar() {
      if (this.progressValue < 99) {
        this.progressValue += 0.5;
        this.progressCSS = this.progressValue + "%";
        setTimeout(this.updateProgressBar, 10);
      } else {
        this.progressValue = 0;
        this.progressCSS = this.progressValue + "%";

        this.updateProgressBar();
      }
    },
  },
  mounted() {
    // Start the progress bar animation on component mount (just for demonstration purposes)
    if (this.isLogin) {

      const shareid = this.$store.getters["userStore/checkShareId"]?.share_id;
  
      if (localStorage.getItem("shareid")) {
        this.$router.push(`main/${shareid}`).catch(()=>{});
      }
      this.updateProgressBar();
    }
  },
  computed: {
    ...mapState(userStore, ["isLogin"]),
  }
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
  top: 21%;
}

.logo {
  width: 83%;
  /* filter: drop-shadow(2px 2px 4px rgba(0,0,0,0.2)); */
  filter: brightness(115%);
  animation: popping1 2.5s ease-in-out infinite alternate;
}

@keyframes popping1 {
  0% {
    transform: scale(1) translateY(0);
  }
  50% {
    transform: scale(1) translateY(-3%);
  }
  100% {
    transform: scale(1) translateY(0%);
  }
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
  top: 47%;
  width: 100%;
}
.google {
  position: relative;
  display: flex;
  justify-content: center;
  top: 38%;
  width: 100%;
}
.kakao-image {
  background-color: #fee500;
  width: 68%;
  filter: drop-shadow(2px 2px 4px rgba(0, 0, 0, 0.3));
}
.google-image {
  background-color: #ffffff;
  width: 68%;
  filter: drop-shadow(2px 2px 4px rgba(0, 0, 0, 0.3));
}

.box-behind {
  margin-top: 6.5px;
  margin-left: 13px;
  position: absolute;
  box-sizing: border-box;

  position: absolute;
  width: 70%;
  height: 42px;

  background: #ffffff;
  border: 0.1px solid #8c9799;
  border-radius: 12px;
  filter: drop-shadow(1px 1px 2px rgba(0, 0, 0, 0.25));
}

.box-front {
  position: absolute;
  box-sizing: border-box;

  position: absolute;
  width: 70%;
  height: 42px;

  background: #ffe500;
  border: 0.1px solid #8c9799;
  border-radius: 12px;

  display: flex;
  justify-content: center;
  align-items: center;
  font-weight: 800;
  box-shadow: inset 0px -3px 3px rgba(0, 0, 0, 6%);
}
.box-front-google {
  position: absolute;
  box-sizing: border-box;

  position: absolute;
  width: 70%;
  height: 42px;

  background: #ffffff;
  border: 0.1px solid #8c9799;
  border-radius: 12px;

  display: flex;
  justify-content: center;
  align-items: center;
  font-weight: 800;
  box-shadow: inset 0px -3px 3px rgba(0, 0, 0, 6%);
}

.kakao-logo {
  width: 30px;
}
.google-logo {
  width: 18px;
  height: 18px;
  margin-right : 5px;
}
</style>
