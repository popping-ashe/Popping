<template>
  <div v-if="isMobile" class="bgnewjeans" style="width: 100vw; height:100vh;">
    <div class="container2">
      <router-link :to="{ name: 'MainView' }"><img src="../src/assets/컴퓨터.png" alt="com" class="com-image" @click="toggleImageSize($event)">Main</router-link>
      <br>
      <router-link :to="{ name: 'LoginView' }"><img src="../src/assets/pen.png" alt="com" class="com-image" @click="toggleImageSize($event)">Login</router-link>
      <br>
      <router-link :to="{ name: 'MypageView' }">Mypage</router-link>
      <br>
    </div>
    <router-view/>
  </div>

  <div v-else style="width: 100vw; height: 100vh; background-color: white; display: flex; justify-content: center;">
    <div class="bgnewjeans" style="width:30vw; height:100vh">
      <router-view/>

    </div>
  </div>
</template>

<script>
import MobileDetect from 'mobile-detect';
export default {
  name: 'CloseBtn',
  data: () => {
    return {
      isMobile: false,
    };
  },
  methods: {
     toggleImageSize(event) {
      const image = event.target;
      image.classList.toggle('enlarged-image');
      setTimeout(() => {
        image.classList.toggle('enlarged-image');
      }, 300);
    },
  },
  mounted() {
    const md = new MobileDetect(window.navigator.userAgent);
    if (md.mobile()) {
      this.isMobile = true;
    } else {
      this.isMobile = false;
    } 
  },
};
</script>


<style scoped>
.bgnewjeans{
background: hsla(228, 100%, 84%, 1);

background: linear-gradient(270deg, hsla(228, 100%, 84%, 1) 0%, hsla(201, 100%, 84%, 1) 7%, hsla(0, 0%, 100%, 1) 56%, hsla(0, 0%, 100%, 1) 88%, hsla(201, 100%, 84%, 1) 100%);

background: -moz-linear-gradient(270deg, hsla(228, 100%, 84%, 1) 0%, hsla(201, 100%, 84%, 1) 7%, hsla(0, 0%, 100%, 1) 56%, hsla(0, 0%, 100%, 1) 88%, hsla(201, 100%, 84%, 1) 100%);

background: -webkit-linear-gradient(270deg, hsla(228, 100%, 84%, 1) 0%, hsla(201, 100%, 84%, 1) 7%, hsla(0, 0%, 100%, 1) 56%, hsla(0, 0%, 100%, 1) 88%, hsla(201, 100%, 84%, 1) 100%);

filter: progid: DXImageTransform.Microsoft.gradient( startColorstr="#ACBCFF", endColorstr="#AEE2FF", GradientType=1 );
}

.com-image {
  width: 10%;
  height: 5%;
  transition: all 0.3s ease-in-out;
}
.com-image.enlarged-image {
  transform: scale(1.2);
}
</style>