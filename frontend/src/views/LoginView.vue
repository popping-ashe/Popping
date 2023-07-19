<template>
  <div class="body">
    <div class="container">
      <div class="image-container animate__animated animate__fadeInUp">
        <img src="../assets/popping-clay-p.png" alt="팝핑 이미지 1" class="popping-image">
        <img src="../assets/popping-clay-o2.png" alt="팝핑 이미지 2" class="popping-image" id="a">
        <img src="../assets/popping-clay-p2.png" alt="팝핑 이미지 3" class="popping-image">
        <img src="../assets/popping-clay-12.png" alt="팝핑 이미지 8" class="popping-image" id="a">
      </div>
      <div class="image-container animate__animated animate__fadeInUp">
        <img src="../assets/popping-clay-p.png" alt="팝핑 이미지 4" class="popping-image" id="a">
        <img src="../assets/popping-clay-i2.png" alt="팝핑 이미지 5" class="popping-image">
        <img src="../assets/popping-clay-N.png" alt="팝핑 이미지 6" class="popping-image" id="a">
        <img src="../assets/popping-clay-g2.png" alt="팝핑 이미지 7" class="popping-image">
      </div>
        <!-- <div class="pointing-container animate__animated animate__fadeIn">
          <img src="../assets/pointing.png" alt="버블" class="bubble-image pointing-image">
        </div> -->
      <div class="icon-box">
        <div class="bubble-image1">
          <img src="../assets/55.png" alt="버블" class="bubble-image1" style="width:20%;">
        </div>
        <div class="bubble animate__animated animate__bounceInUp">
          <!-- <img src="../assets/bubble5.png" alt="버블" class="bubble-image" style="width:30%;"> -->
          <img src="../assets/bubble9.png" alt="버블" class="bubble-image" @click="openhowto">
        </div>
      </div>
      <div class="touch-box">
        <img src="../assets/touch-t.png" alt="touch" class="touch-image">
        <img src="../assets/touch-o.png" alt="touch" class="touch-image">
        <img src="../assets/touch-u.png" alt="touch" class="touch-image">
        <img src="../assets/touch-c.png" alt="touch" class="touch-image">
        <img src="../assets/touch-h.png" alt="touch" class="touch-image">
        <img src="../assets/touch-1.png" alt="touch" class="touch-image" style="width:2.5%">
        <img src="../assets/pointing.png" alt="버블" class="bubble-image pointing-image" style="width:10%">
      </div>
        <!-- <ClickMessage/> -->
    </div>

    <div class="kakao" @click="kakaoLogin">
      <img class="kakao_image" src="../assets/kakao_login.png" alt="">
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
  methods: {
    //버블 배경 애니메이션

    //카카오 로그인
    kakaoLogin() {
      //  Vue.$kakao.login()
      //  .then((response) => {
      //    const accessToken = response.access_token;
      //    서버로 accessToken 전달..?
      //    console.log(response)
        this.$router.push({ name: 'MainView' });
      //   })
      //   .catch((error) => {
      //     console.error(error);
      //   });
    },
  },
};
</script>

<style scoped>
#a {
  animation: popping2 2s ease-in-out infinite alternate;
}

/* .body {
  width: 100vw;
  height: 100vh;
  justify-content: center;
  align-items: center;
} */

.container {
  padding-left: 8%;
  padding-right: 10%;
  margin-top: 15%;
  z-index: 1;
}

.image-container {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  align-items: center;
  height: 100%;
  width: 100%;
  
}

.popping-image {
  width: 25%;
  height: 10%;
  margin-left: 10px;
  margin-right: 10px;
  animation: popping1 2s ease-in-out infinite alternate;
  margin: 0;
  rotate: 90%;
  filter:drop-shadow(2px 2px 6px rgb(154, 129, 230));
}

.bubble {
  z-index: 999;
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  align-items: center;
  margin-top: 5%;
}

.bubble-image {
  width: 70%;
  height: 100%;
  animation: popping3 3s ease-in-out infinite alternate;
  /* filter:drop-shadow(2px 2px 6px rgb(103, 145, 228)); */
  
}
.bubble-image1 {
  /* width: 70%; */
  height: 100%;
  animation: popping3 3s ease-in-out infinite alternate;
  /* filter:drop-shadow(2px 2px 6px rgb(103, 145, 228)); */
  z-index: 999;
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  align-items: center;
  
}

.kakao {
  position: fixed;
  bottom: 5%;
  left: 50%;
  transform: translateX(-50%);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 2;
}

.kakao_image {
  /* box-shadow: 2px 2px 4px darkgray; */
}
.chat-image {
  display: flex;
  z-index: 3;
  bottom: 50%;
}
.pointing-container {
  position: relative;
  top: 70%;
  left: 45%;
  transform: translate(-50%, 450%) rotate(45deg);
}

.pointing-image {
  width: 20%; /* Adjust the size as needed */
}

.icon-box {
  margin-top: 20%;
}
.touch-box{
  z-index: 999;
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  align-items: center;
  animation: popping4 3s ease-in-out infinite alternate;
  /* margin-top: 5%; */
}
.touch-image{
  width: 5%;
}

@keyframes popping1 {
  0% {
    transform: scale(1) translateY(0);
  }
  50% {
    transform: scale(1) translateY(-2.5%);
  }
  100% {
    transform: scale(1) translateY(0%);
  }
}

@keyframes popping2 {
  0% {
    transform: scale(1) translateY(0);
  }
  50% {
    transform: scale(1) translateY(2.5%);
  }
  100% {
    transform: scale(1) translateY(0%);
  }
}

@keyframes popping3 {
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
@keyframes popping4 {
  0% {
    transform: scale(1) translateY(0);
  }
  50% {
    transform: scale(1) translateY(-50%);
  }
  100% {
    transform: scale(1) translateY(0%);
  }
}

@media (max-width: 768px) {
  .kakao {
    bottom: 5%;
  }
}
</style>
