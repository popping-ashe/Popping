<template>
  <div class="frame">
    <div class="upper-bar">
      <div class="share-ellipse">
        <div class="share-emoji">
          <svg xmlns="http://www.w3.org/2000/svg" height="1em" viewBox="0 0 576 512"><path d="M400 255.4V240 208c0-8.8-7.2-16-16-16H352 336 289.5c-50.9 0-93.9 33.5-108.3 79.6c-3.3-9.4-5.2-19.8-5.2-31.6c0-61.9 50.1-112 112-112h48 16 32c8.8 0 16-7.2 16-16V80 64.6L506 160 400 255.4zM336 240h16v48c0 17.7 14.3 32 32 32h3.7c7.9 0 15.5-2.9 21.4-8.2l139-125.1c7.6-6.8 11.9-16.5 11.9-26.7s-4.3-19.9-11.9-26.7L409.9 8.9C403.5 3.2 395.3 0 386.7 0C367.5 0 352 15.5 352 34.7V80H336 304 288c-88.4 0-160 71.6-160 160c0 60.4 34.6 99.1 63.9 120.9c5.9 4.4 11.5 8.1 16.7 11.2c4.4 2.7 8.5 4.9 11.9 6.6c3.4 1.7 6.2 3 8.2 3.9c2.2 1 4.6 1.4 7.1 1.4h2.5c9.8 0 17.8-8 17.8-17.8c0-7.8-5.3-14.7-11.6-19.5l0 0c-.4-.3-.7-.5-1.1-.8c-1.7-1.1-3.4-2.5-5-4.1c-.8-.8-1.7-1.6-2.5-2.6s-1.6-1.9-2.4-2.9c-1.8-2.5-3.5-5.3-5-8.5c-2.6-6-4.3-13.3-4.3-22.4c0-36.1 29.3-65.5 65.5-65.5H304h32zM72 32C32.2 32 0 64.2 0 104V440c0 39.8 32.2 72 72 72H408c39.8 0 72-32.2 72-72V376c0-13.3-10.7-24-24-24s-24 10.7-24 24v64c0 13.3-10.7 24-24 24H72c-13.3 0-24-10.7-24-24V104c0-13.3 10.7-24 24-24h64c13.3 0 24-10.7 24-24s-10.7-24-24-24H72z"/></svg>
        </div>
      </div>
      <div class="username">
        USERNAME
      </div>
      <div class="mypage-ellipse" @click="$router.push('/mypage')">
        <div class="mypage-emoji">
          <svg xmlns="http://www.w3.org/2000/svg" height="1em" viewBox="0 0 448 512"><path d="M304 128a80 80 0 1 0 -160 0 80 80 0 1 0 160 0zM96 128a128 128 0 1 1 256 0A128 128 0 1 1 96 128zM49.3 464H398.7c-8.9-63.3-63.3-112-129-112H178.3c-65.7 0-120.1 48.7-129 112zM0 482.3C0 383.8 79.8 304 178.3 304h91.4C368.2 304 448 383.8 448 482.3c0 16.4-13.3 29.7-29.7 29.7H29.7C13.3 512 0 498.7 0 482.3z"/></svg>
        </div>
      </div>
    </div>

<!-- 로그인 여부 -> 버블 클릭 방지 -->
    <div class="bubble-area">
      <img class="bubble1" src="../assets/bubble.png" alt="">
      <img class="bubble2" src="../assets/bubble.png" alt="">
    </div>

<!-- 로그인 여부 -> 작성 / 재밌는 기능 -->
    <div class="under-bar">
      <div class="bubble-make-btn">
        <div>버블 만들기</div>
      </div>
    </div>
  </div>
<!-- 메인 페이지 본인 여부 화면 -->

</template>

<script>
export default {
  name: 'MainView',
  
}

</script>

<style>
/* iPhone 14 Pro - 1 */
.frame {
  position: relative;
  background: #FFFFFF;
  }

.upper-bar {
  position: absolute;
  width: 393px;
  height: 48px;
  left: 0px;
  top: 61px;
  }

.share-ellipse {
  box-sizing: border-box;

  position: absolute;
  width: 40px;
  height: 40px;
  left: 26px;
  top: 4px;

  background: #FFFFFF;
  border: 1px solid #505050;
  border-radius: 50%;
  box-shadow: 1px 1px 3px rgba(0, 0, 0, 0.25);
  transition: all ease 0.4s;
  }

  .share-ellipse:hover {
    filter: brightness(70%);
    transition: all ease 0.4s;
  }

.mypage-ellipse {
  box-sizing: border-box;

  position: absolute;
  width: 40px;
  height: 40px;
  left: 327px;
  top: 4px;

  background: #FFFFFF;
  border: 1px solid #505050;
  border-radius: 50%;
  box-shadow: 1px 1px 3px rgba(0, 0, 0, 0.25);
  transition: all ease 0.4s;
  }

  .mypage-ellipse:hover {
    filter: brightness(70%);
    transition: all ease 0.4s;
  }

.share-emoji {
  position: relative;
  width: 32px;
  height: 32px;
  left: 12px;
  top: 4px;
}

.mypage-emoji {
  position: relative;
  width: 32px;
  height: 32px;
  left: 12px;
  top: 4px;
}

.username {
  position: absolute;
  width: 176px;
  height: 48px;
  left: 108px;
  top: 1px;
  justify-content: center;

  font-family: 'Inter';
  font-style: normal;
  font-weight: 400;
  font-size: 20px;
  line-height: 21px;
  /* or 105% */
  display: flex;
  align-items: center;
  text-align: center;
  letter-spacing: -0.32px;

  color: #000000;

  text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.25);
  }

.bubble-area {
  position: absolute;
  width: 393px;
  height: 616px;
  left: 0px;
  top: 123px;
  }

.bubble1 {
  position: relative;
  width: 123px;
  height: 123px;
  left: 52px;
  top: 41px;
}

.bubble2 {
  position: absolute;
  width: 78px;
  height: 78px;
  left: 262px;
  top: 80px;
}

.under-bar {
  position: absolute;
  width: 393px;
  height: 80px;
  left: 0px;
  top: 712px;
}

.bubble-make-btn {
  box-sizing: border-box;

  position: absolute;
  width: 268px;
  height: 50px;
  left: 63px;
  top: 15px;

  border: 1px solid #000000;
  border-radius: 50px;
  display: flex;
  justify-content: center;
  align-items: center;
  filter: drop-shadow(1px 1px 3px rgba(0, 0, 0, 0.25));

  transition: all ease 0.4s;
  }

.bubble-make-btn:hover {
  transition: all ease 0.4s;
}


</style>
