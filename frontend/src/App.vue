<template>
  <v-app>
    <v-main>
      <div
        v-if="isMobile"
        class="bgpopping"
        style="width: 100vw; height: calc(var(--vh, 1vh) * 100); overflow: hidden"
      >
        <div class="main">
          <router-view />
        </div>
      </div>

      <div
        v-else
        style="
          width: 100vw;
          height: calc(var(--vh, 1vh) * 100);
          display: flex;
          justify-content: center;
          overflow: hidden;
        "
      >
        <div
          class="bgpopping"
          style="width: calc(var(--vw, 1vw) * 125); height: 100%; overflow-x: hidden"
        >
          <div class="main">
            <router-view />
          </div>
        </div>
      </div>
    </v-main>
  </v-app>
</template>

<script>
import MobileDetect from "mobile-detect";
export default {
  name: "CloseBtn",
  data: () => {
    return {
      isMobile: false,
    };
  },
  methods: {
    toggleImageSize(event) {
      const image = event.target;
      image.classList.toggle("enlarged-image");
      setTimeout(() => {
        image.classList.toggle("enlarged-image");
      }, 300);
    },
  },
  created() {
    const md = new MobileDetect(window.navigator.userAgent);
    if (md.mobile()) {
      this.isMobile = true;
    } else {
      this.isMobile = false;
    }
    const customVH = window.innerHeight * 0.01;
    document.documentElement.style.setProperty("--vh", customVH + "px");
    document.documentElement.style.setProperty("--vw", customVH * (393 / 852) + "px");
    window.addEventListener("resize", () => {
      document.documentElement.style.setProperty("--vh", customVH + "px");
    });
  },
};
</script>

<style>
.bgpopping {
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  -o-user-select: none;
  user-select: none;
  /* background: rgba(19, 127, 250, 0.53); */
  background: hsla(200, 81%, 79%, 1);

  background: linear-gradient(
    270deg,
    hsla(200, 81%, 79%, 1) 0%,
    hsla(0, 0%, 100%, 1) 49%,
    hsla(0, 0%, 100%, 1) 66%,
    hsla(200, 81%, 79%, 1) 100%
  );

  background: -moz-linear-gradient(
    270deg,
    hsla(200, 81%, 79%, 1) 0%,
    hsla(0, 0%, 100%, 1) 49%,
    hsla(0, 0%, 100%, 1) 66%,
    hsla(200, 81%, 79%, 1) 100%
  );

  background: -webkit-linear-gradient(
    270deg,
    hsla(200, 81%, 79%, 1) 0%,
    hsla(0, 0%, 100%, 1) 49%,
    hsla(0, 0%, 100%, 1) 66%,
    hsla(200, 81%, 79%, 1) 100%
  );

  filter: progid: DXImageTransform.Microsoft.gradient( startColorstr="#A0D8F5", endColorstr="#FFFFFF", GradientType=1 );
}

.com-image {
  width: 10%;
  height: 5%;
  transition: all 0.3s ease-in-out;
}
.com-image.enlarged-image {
  transform: scale(1.2);
}

#app {
  height: calc(var(--vh, 1vh) * 100);
  overflow: scroll;
}

.v-application--wrap {
  min-height: calc(var(--vh, 1vh) * 100) !important;
}

body {
  position: fixed;
}

::-webkit-scrollbar {
  display: none;
}

.font-kor {
  font-family: "Galmuri9";
  font-weight: 200;
}

.font-eng {
  font-family: "hydrophilia";
}

.font-stardust {
  font-family: "PFStardust";
}

.font-pre {
  font-family: "Pretendard-Regular";
}
</style>
