<template>
  <div class="carousel-message-frame animate__animated animate__fadeIn">
    <div class="carousel-window font-pre" v-click-outside="closeTutorial">
      <div class="carousel-close-button" @click="closeTutorial"></div>
      <div class="carousel-frame">
        <carousel class="carousel-main" 
          :paginationSize="6" :perPage="1" :navigationEnabled="false"
          :autoplayTimeout="4000" :autoplayHoverPause="true"
          :paginationPadding="5">
          <slide class="carousel-slide">
            <img class="carousel-image" src="../assets/tutorial_01.png" alt=""><br><br>
            <span class="carousel-text">공유 버튼을 터치해<br>친구와 파핑 페이지를 주고받으세요</span>
          </slide>
          <slide class="carousel-slide">
            <img class="carousel-image" src="../assets/tutorial_02.png" alt=""><br><br>
            <span class="carousel-text">친구의 파핑 페이지에서<br>버블 만들기를 눌러 버블을 보내보세요</span>
          </slide>
          <slide class="carousel-slide">
            <img class="carousel-image" src="../assets/tutorial_03.png" alt=""><br><br>
            <span class="carousel-text">닉네임, 내용을 작성하고<br>버블 만료 시간을 정해주세요</span>
          </slide>
          <slide class="carousel-slide">
            <img class="carousel-image" src="../assets/tutorial_10.png" alt=""><br><br>
            <span class="carousel-text">버블을 보낼 때<br>토글 버튼으로 답장을 받을지<br>결정할 수 있습니다</span>
          </slide>
          <slide class="carousel-slide">
            <img class="carousel-image" src="../assets/tutorial_08.png" alt=""><br><br>
            <span class="carousel-text">기본 만료 시간은 24시간으로<br>터치해서 변경할 수 있습니다</span>
          </slide>
          <slide class="carousel-slide">
            <img class="carousel-image" src="../assets/tutorial_04.png" alt=""><br><br>
            <span class="carousel-text">닉네임을 작성하지 않으면<br>'익명'으로 발송됩니다</span>
          </slide>
          <slide class="carousel-slide">
            <img class="carousel-image" src="../assets/tutorial_05.png" alt=""><br><br>
            <span class="carousel-text">버블 위 숫자는<br>버블이 터지기까지 남은 시간입니다<br>읽은 버블은 회색으로 변합니다</span>
          </slide>
          <slide class="carousel-slide">
            <img class="carousel-image" src="../assets/tutorial_06.png" alt=""><br><br>
            <span class="carousel-text">받은 버블은 만료 시간이 지나면<br>다시 볼 수 없으니 조심하세요!</span>
          </slide>
          <slide class="carousel-slide">
            <img class="carousel-image" src="../assets/tutorial_09.png" alt=""><br><br>
            <span class="carousel-text">편집 버튼을 터치해<br>상태 메시지를 변경해보세요</span>
          </slide>
          <slide class="carousel-slide">
            <img class="carousel-image" src="../assets/tutorial_07.png" style="margin-left: 2px;" alt=""><br><br>
            <span class="carousel-text">마이페이지로 이동해<br>내가 보낸 버블과<br>친구의 상태 메시지를 확인해보세요</span>
          </slide>
        </carousel>
      </div>
    </div>
  </div>
</template>

<script>
// import carousel from 'vue-owl-carousel'
import vClickOutside from "v-click-outside";

export default {
  name: "tutorialPopup",
  // components: { carousel },
  directives: {
    clickOutside: vClickOutside.directive,
  },
  methods: {
    closeTutorial() {
      this.$emit("close");
    }
  },
  // props: ["termdetailProps"],

  computed: {

  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style>
.carousel-message-frame {
  position: fixed;
  top: 0px;
  left: 0px;
  width: 100%;
  height: 100%;
  z-index: 999;
  background: rgba(0, 0, 0, 0.3);
}

.carousel-window {
  position: absolute;
  top: calc(var(--vh, 1vh) * 50.5);
  left: 50%;
  width: calc(var(--vh, 1vh) * 42.5);
  height: calc(var(--vh, 1vh) * 50);
  transform: translate(-50%, -50%);
  filter: drop-shadow(2px 2px 2px rgba(0, 0, 0, 0.3));
  background-color: transparent;
  background-image: url("../assets/tutorial_window.png");
  background-size: 100%;
  background-repeat: no-repeat;
  display: flex;
  flex-direction: column;
}

.carousel-close-button {
  width: 30%;
  height: 9%;
  /* border: 1px solid black; */
}

.carousel-close-button-frame {
  height: 24px;
  display: flex;
  justify-content: center;
  margin-top: 5%;
}

.carousel-close-button-two {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 30%;
  height: 100%;
  background-color: lightgray;
  border-radius: 24px;
  border: 1px solid black;
  margin-right: 3%;
  font-size: 15px;
  background: linear-gradient(180deg, #FFFFFF 0%, #B9D7EB 99.99%);
  /* background: linear-gradient(180deg, #FFFFFF 0%, #CCCCCC 99.99%); */
  /* #9ed8f5 */
  /* filter: drop-shadow(2px 2px 2px rgba(0, 0, 0, 0.25)); */
}


.carousel-frame {
  margin-top: 10%;
  width: 100%;
  height: 72.5%;
  display: flex;
  justify-content: center;
}


.carousel-main {
  margin-right: 3%;
  width: calc(var(--vh, 1vh) * 30);
}

.carousel-slide {
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  text-align: center;
  font-size: 80%;
}

.carousel-image {
  width: 100%;
}

.carousel-text {
  margin-top: 2%;
  margin-bottom: 15%;
}

.VueCarousel-pagination {
  height: 16%;
}

.VueCarousel-dot-container {
  height: 100%;
  margin-top: 0% !important;
}

.VueCarousel-dot {
  margin-top: 2% !important;
}

.VueCarousel-dot--active {
  background-color: #B9D7EB !important
}

.scroll::-webkit-scrollbar {
  display: none;
}
</style>
