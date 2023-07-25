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
          <img src="../assets/smile.png" alt="버블" class="bubble-image1" style="width:20%; margin-top: 20%; margin-right:20%">
          <img src="../assets/55.png" alt="버블" class="bubble-image1" style="width:20%;">
          <img src="../assets/sorry.png" alt="버블" class="bubble-image1" style="width:20%; margin-top: 20%; margin-left:20%">
        </div>

        
        <div class="bubble1 animate__animated animate__bounceInUp">
          <!-- <img src="../assets/bubble5.png" alt="버블" class="bubble-image" style="width:30%;"> -->
          <!-- <img src="../assets/bubble9.png" alt="버블" class="bubble-image" @click="openhowto"> -->
          <label><input class="bubble" type="checkbox" name="dummy" value="on"></label>
        </div>
        <div class="bubble-image1">
          <img src="../assets/lol.png" alt="버블" class="bubble-image1" style="width:20%; margin-right:10%">
            <img src="../assets/touch-t.png" alt="touch" class="touch-image">
            <img src="../assets/touch-o.png" alt="touch" class="touch-image">
            <img src="../assets/touch-u.png" alt="touch" class="touch-image">
            <img src="../assets/touch-c.png" alt="touch" class="touch-image">
            <img src="../assets/touch-h.png" alt="touch" class="touch-image">
            <img src="../assets/touch-1.png" alt="touch" class="touch-image" style="width:2.5%; margin-right:10%">
            <!-- <img src="../assets/pointing.png" alt="버블" class="bubble-image pointing-image" style="width:10%"> -->
          <img src="../assets/hi.png" alt="버블" class="bubble-image1" style="width:20%;">
          
          <!-- <div class="touch-box"> -->
          <!-- </div> -->
        </div>
      </div>
        <!-- <ClickMessage/> -->
    </div>

    <div class="kakao">
      <!-- <a href="https://kauth.kakao.com/oauth/authorize?client_id=cecace976e616b34de2152ac78d7542b&redirect_uri=http://localhost:8080/oauth/kakao/callback&response_type=code"> -->
      <img class="kakao_image" src="../assets/kakao_login.png" alt="" @click="kakaologin()">
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
  methods: {
    //버블 배경 애니메이션

    //카카오 로그인
    kakaologin() {
      location.href = "https://kauth.kakao.com/oauth/authorize?client_id=cecace976e616b34de2152ac78d7542b&redirect_uri=http://localhost:8080/oauth/kakao/callback&response_type=code"
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
  margin-top: 5%;
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

.bubble1 {
  z-index: 999;
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  align-items: center;
  /* margin-top: 5%; */
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
    opacity: 0;
  }
  50% {
    opacity: 1;
  }
  100% {
    opacity: 0;
  }
}

@media (max-width: 768px) {
  .kakao {
    bottom: 5%;
  }
}


label, .bubble {
	display: block;
	-webkit-tap-highlight-color: transparent;
}
label {
	animation: float 4s ease-in-out infinite;
}
.bubble, .bubble:before, .bubble:after  {
	transition-duration: 0.2s;
}
.bubble, .bubble:after {
	border-radius: 100%;
}
.bubble {
	background-image:
		radial-gradient(8% 8% at 22% 28%,hsla(0,0%,100%) 45%,hsla(0,0%,100%,0) 50%),
		radial-gradient(8% 8% at 23% 27%,hsl(0,0%,100%) 45%,rgba(11, 104, 218, 0.09) 50%),
		radial-gradient(8% 8% at 24% 26%,hsl(0,0%,100%) 45%,hsla(0,0%,100%,0) 50%),
		radial-gradient(8% 8% at 25% 25%,hsl(0,0%,100%) 45%,hsla(0,0%,100%,0) 50%),
		radial-gradient(8% 8% at 26% 24%,hsl(0,0%,100%) 45%,hsla(127, 90%, 45%, 0) 50%),
		radial-gradient(8% 8% at 27% 23%,hsl(0,0%,100%) 45%,hsla(0,0%,100%,0) 50%),
		radial-gradient(8% 8% at 28% 22%,hsl(0,0%,100%) 45%,hsla(0,0%,100%,0) 50%);
	box-shadow:
		0 -0.06em 0.1em hsla(0, 0%, 100%, 0) inset,
		0 -0.15em 0.4em hsl(0, 0%, 100%,0) inset,
		0 0.05em 0.05em hsl(0, 0%, 100%,0) inset,
		0.05em 0 0.1em hsl(0,90%,100%,0) inset,
		-0.05em 0 0.1em hsl(0,90%,100%,0) inset,
		0 0.1em 0.4em hsl(0, 0%, 100%,0) inset;
	cursor: pointer;
	position: relative;
	width: 14em;
	height: 14em;
	transform-style: preserve-3d;
	transition-property: box-shadow, transform, width, height;
	transition-timing-function: ease-in-out, ease-in-out, var(--bubbleTiming), var(--bubbleTiming);
	will-change: transform;
	-webkit-appearance: none;
	appearance: none;
	z-index: 0;
  margin-bottom: 15%;
}
.bubble:before, .bubble:after {
	content: "";
	display: block;
	position: absolute;
	transition-timing-function: var(--bubbleTiming);
}
.bubble:before {
	border-radius: 0.75em;
	/* box-shadow:
		0 0 0 0.5em hsl(0,0%,100%) inset; */
	filter: drop-shadow(0.6em 0.6em 4px hsla(0,0%,0%,0.2));
	top: 50%;
	left: 50%;
	width: 1.5em;
	height: 1.5em;
	transform: translate3d(-50%,-50%,-1px);
	z-index: -1;
}
.bubble:after {
	background:
		radial-gradient(100% 100% at center,hsla(0,0%,0%,0) 35%,hsla(0,0%,0%,0.2) 48%,hsla(0,0%,0%,0) 50%);
	filter: blur(4px);
	top: 0.6em;
	left: 0.6em;
	width: 100%;
	height: 100%;
	transform: translate3d(0,0,-1px);
	z-index: -2;
}
.bubble:focus, .bubble:hover {
	transform: scale(1.1);
	outline: none;
}
.bubble:focus:active, .bubble:hover:active {
	width: 10em;
	height: 10em;
}
.bubble:focus:before, .bubble:hover:before {
	filter: drop-shadow(0.75em 0.75em 4px hsla(0,0%,0%,0.2));
}
.bubble:focus:after, .bubble:hover:after {
	transform: translate3d(0.15em,0.15em,-1px);
}
.bubble:checked {
  background-image:
		radial-gradient(8% 8% at 22% 28%,hsla(0,0%,100%) 45%,hsla(0,0%,100%,0) 50%),
		radial-gradient(8% 8% at 23% 27%,hsl(0,0%,100%) 45%,rgba(163, 11, 218, 0.09) 50%),
		radial-gradient(8% 8% at 24% 26%,hsl(0,0%,100%) 45%,hsla(0,0%,100%,0) 50%),
		radial-gradient(8% 8% at 25% 25%,hsl(0,0%,100%) 45%,hsla(0,0%,100%,0) 50%),
		radial-gradient(8% 8% at 26% 24%,hsl(0,0%,100%) 45%,hsla(0,0%,100%,0) 50%),
		radial-gradient(8% 8% at 27% 23%,hsl(0,0%,100%) 45%,hsla(0,0%,100%,0) 50%),
		radial-gradient(8% 8% at 28% 22%,hsl(0,0%,100%) 45%,hsla(0,0%,100%,0) 50%);
	box-shadow:
		0 -0.06em 0.1em hsla(0, 0%, 100%, 0) inset,
		0 -0.15em 0.4em hsl(0,90%,100%,0) inset,
		0 0.05em 0.05em hsl(0,90%,100%,0) inset,
		0.05em 0 0.1em hsl(0,90%,100%,0) inset,
		-0.05em 0 0.1em hsl(0,90%,100%,0) inset,
		0 0.1em 0.4em hsl(0,90%,100%,0) inset;
}
.bubble:checked:before {
	border-radius: 0.25em;
	width: 0.5em;
}
/* Reduced motion */
@media (prefers-reduced-motion: reduce) {
	label {
		animation: none;
	}
	.bubble, .bubble:before, .bubble:after {
		transition-duration: 0s;
	}
	.bubble:focus, .bubble:hover {
		transform: scale(1);
	}
	.bubble:focus:active, .bubble:hover:active {
		width: 3em;
		height: 3em;
	}
	.bubble:focus:before, .bubble:hover:before {
		filter: drop-shadow(0.6em 0.6em 4px hsla(0,0%,0%,0.2));
	}
	.bubble:focus:after, .bubble:hover:after {
		transform: translate3d(0,0,-1px);
	}
}
/* Animations */
@keyframes float {
	from, to {
		transform: translate(0,3%);
	}
	25% {
		transform: translate(-3%,0);
	}
	50% {
		transform: translate(0,-3%);
	}
	75% {
		transform: translate(3%,0);
	}
}
</style>
