<template>
  <div class="message-frame animate__animated animate__fadeIn">
    <div class="window" v-click-outside="closeshare">
      <div class="close-button" @click="closeshare"></div>
        <div class="upper-bar">
          <div class="share-text font-kor">
            공유하기
          </div>
        </div>

      <div class="nickname-box">
        <div class="sharebox">
          <img @click="shareKakao()" class="shareimg" src="@/assets/kakao.png" alt="kakao" />
          <div>Kakao</div>
        </div>
        <div class="sharebox">
          <img
            @click="shareFacebook()"
            class="shareimg"
            src="@/assets/facebook.png"
            alt="facebook"
          />
          <div>Facebook</div>
        </div>
        <div class="sharebox">
          <img @click="shareTwitter()" class="shareimg" src="@/assets/twitter.png" alt="twitter" />
          <div>Twitter</div>
        </div>
      </div>
      <div class="share-link-frame font-pre">
        <div class="location-box">
          <div clas="location">{{ location }}</div>
        </div>
        <button class="button" @click="shareCopy()">&nbsp;&nbsp;URL 복사&nbsp;&nbsp;</button>
      </div>
    </div>
  </div>
</template>

<script>
import vClickOutside from "v-click-outside";

export default {
  name: "ShareChoose",
  directives: {
    ClickOutside: vClickOutside.directive,
  },
  data() {
    return {
      location: window.document.location.href,
    };
  },
  methods: {
    closeshare() {
      this.$emit("close"); // 이벤트를 부모 컴포넌트에 전달하여 닫히도록 함
    },
    shareKakao() {
      // 카카오톡 공유 요청 보내기
      window.Kakao.Share.sendCustom({
        templateId: 96840,
        installTalk: true, // 카카오톡이 설치 되지 않았을때 마켓으로 이동
        templateArgs: {
          shareId: this.$store.getters["userStore/checkShareId"].share_id, // 여기에 shareId 담아주면 댐
          userName: this.$store.getters["userStore/checkUserInfo"].nickname,
        },
      });
    },
    shareTwitter() {
      const text = "💙POPPING에서 익명 메세지를 보내보세요💙"; // 작성될 트위
      const url = window.document.location.href; // 여기에 공유할 full url 넣기
      window.open("https://twitter.com/intent/tweet?text=" + text + "&url=" + url);
    },
    shareFacebook() {
      const url = window.document.location.href; // 여기에 공유할 full url 넣기 (localhost는 현재 적용안됨)
      window.open("https://www.facebook.com/sharer/sharer.php?u=" + url);
    },
    shareCopy() {
      this.$copyText(window.document.location.href);
      this.$toast.center("복사되었습니다");
    },
    analyticsKaKao(){
      this.$gtag.event('click', {
        event_category: 'share',
        event_label: 'kakao',
        value: 'kakao',
      }); 
    },
    analyticsFacebook(){
      this.$gtag.event('click', {
        event_category: 'share',
        event_label: 'facebook',
        value: 'facebook',
      }); 
    },
    analyticsTwitter(){
      this.$gtag.event('click', {
        event_category: 'share',
        event_label: 'twitter',
        value: 'twitter',
      }); 
    },
    analyticsCopy(){
      this.$gtag.event('click', {
        event_category: 'share',
        event_label: 'copy',
        value: 'copy',
      }); 
    },
  },
  props: ["bubbledetailProps"],

  computed: {},
};
</script>

<style scoped>
.message-frame {
  position: fixed;
  top: 0px;
  left: 0px;
  width: 100%;
  height: 100%;
  z-index: 999;
  background: rgba(0, 0, 0, 0.3);
}

.window {
  position: absolute;
  top: 51.5%;
  left: 50.5%;
  width: calc(var(--vh, 1vh) * 40);
  height: calc(var(--vh, 1vh) * 29);
  transform: translate(-50%, -50%);
  filter: drop-shadow(2px 2px 2px rgba(0,0,0, 0.3));
  background-color: transparent;
  background-image: url("../assets/그림2.png");
  background-size: 100%;
  background-repeat: no-repeat;
  display: flex;
  justify-content: center;
  align-content: flex-start;
  flex-wrap: wrap;
}

.close-button {
  
  width: 100%;
  height: 16%;
    margin-bottom: 15px;
  /* border: 1px solid black; */
}

.upper-bar {
  width: 100%;
  height: 10%;
  display: flex;
  margin-bottom: 1%;
}

.share-text {
  width: 100%;
  height: 100%;
  display: flex;
  align-content: flex-end;
  justify-content: center;
  margin-right: 4%;
  font-size: 90%;
}

.nickname-box {
  width: 83%;
  height: 28%;;
  /* border: 1px solid darkslategray;
  border-radius: 20px 20px 20px 20px; */
  outline: none;
  font-size: 80%;
  font-weight: bold;
  /* background: linear-gradient(180deg, #FFFFFF 0%, #B9D7EB 99.99%, #B9D7EB 100%); */
  display: flex;
  align-items: center;
  justify-content: space-between;
  /* margin-left: 6.5%; */
  margin-right: 4%;
  margin-bottom: 4%;
  margin-top: 3%;
}

.sharebox {
  display: flex;
  flex-direction: column; 
  align-items: center; 
  justify-content: center; 
  height: 100%;
  /* margin: 10px;  */
}

.shareimg {
    margin: 0px 8%;
    width: 55%;
    margin-bottom: 5%;
    /* filter: drop-shadow(2px 2px 4px rgba(0,0,0, 0.4)) */
}
.sharetext {
    margin: 0px 9%;
    width: 20%;

    /* filter: drop-shadow(2px 2px 4px rgba(0,0,0, 0.4)) */
}

.share-link-frame {
  width: 83%;
  height: 13%;
  display: flex;
  justify-content: center;
  margin-right: 3.5%;
  margin-top: 0.5%;
  margin-bottom: 1%;
}


.location-box {
  width: 68%;
  height: 100%;
  border: 0.05px solid rgb(143, 143, 143);
  border-radius: 5px 0px 0px 5px;
  outline: none;
  font-size: 70%;
  /* font-weight: bold; */
  background: rgb(230, 230, 230);
  display: flex;
  align-items: center;
  /* justify-content: center; */
  /* padding-left: 3.5%;
  padding-right: 4.2%; */
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  padding-left: 4px;
}

.button {
  height: 100%;
  width: auto;
  background: white;
  display: flex;
  justify-content: center;
  /* border-left: 0.1px solid rgb(143, 143, 143); */
  border: 0.05px solid rgb(143, 143, 143);
  border-radius: 0px 5px 5px 0px;
  white-space: nowrap;
  font-size: 12px;
  align-items: center;
}
.font-kor{
  font-family: 'Galmuri9';
  font-weight: 200;
}

.font-eng{
  font-family: 'hydrophilia'
}

</style>
