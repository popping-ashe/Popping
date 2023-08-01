<template>
  <div class="message-frame">
    <div class="window">
      <div class="close-button" @click="closeshare"></div>
      <div class="share-text font-kor">
        <div>내 페이지를 SNS에 공유해보세요</div>
      </div>

        <div class="nickname-box">
            <img @click="shareKakao()" class="shareimg" src="@/assets/kakao.png" alt="kakao" style="margin-right:3%">
            <img @click="shareFacebook()" class="shareimg" src="@/assets/facebook.png" alt="facebook" style="margin-right:3%">
            <img @click="shareTwitter()" class="shareimg" src="@/assets/twitter.png" alt="twitter" style="margin-right:1%">
            <img @click="shareCopy()" class="shareimg" src="@/assets/link.png" alt="link" style="width:23.5%; hight:23.5%;">
        </div>

    </div>
  </div>
</template>

<script>
export default {
name: 'ShareChoose',
  methods: {
    closeshare() {
      this.$emit('close'); // 이벤트를 부모 컴포넌트에 전달하여 닫히도록 함
    },
    shareKakao() {
			// 카카오톡 공유 요청 보내기
      window.Kakao.Share.sendCustom({
        templateId: 96612,  // 내가 만들어놓은 메세지 템플릿
        installTalk: true,  // 카카오톡이 설치 되지 않았을때 마켓으로 이동
        templateArgs: {
          shareId : this.$store.getters["userStore/checkShareId"].share_id // 여기에 shareId 담아주면 댐
        }
      });
    },
    shareTwitter() {
      const text = "💙POPPING에서 익명 메세지를 보내보세요💙"; // 작성될 트위
      const url = window.document.location.href;  // 여기에 공유할 full url 넣기
      console.log(url)
      window.open("https://twitter.com/intent/tweet?text="+text+"&url="+url);
    },
    shareFacebook() {
      const url = window.document.location.href;  // 여기에 공유할 full url 넣기 (localhost는 현재 적용안됨)
      // const url ="https://dev.pop-ping.com/";
      window.open("https://www.facebook.com/sharer/sharer.php?u="+url);
    },
    shareCopy() {
      this.$copyText(window.document.location.href)
      this.$toast.center('복사되었습니다')
    },

  },
  props: ['bubbledetailProps'],

  computed: {
  }

}
</script>

<style>
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
  height: 30%;
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
    margin-bottom: 35px;
  /* border: 1px solid black; */
}

.upper-bar {
  width: 100%;
  height: 25%;
  display: flex;
}

.share-text {
  height: 30px;
  display: flex;
  align-content: flex-end;
  justify-content: center;
  margin-right: 4%;
}
.nickname-box {
  width: 83%;
  height: 100px;;
  /* border: 1px solid darkslategray;
  border-radius: 20px 20px 20px 20px; */
  outline: none;
  font-size: 80%;
  font-weight: bold;
  /* background: linear-gradient(180deg, #FFFFFF 0%, #B9D7EB 99.99%, #B9D7EB 100%); */
  display: flex;
  align-items: center;
  justify-content: center;
  /* margin-left: 6.5%; */
  padding-left: 3.5%;
  padding-right: 4.2%;
  margin-right: 4%;
  
}

.shareimg {
    margin: 0px 10px;
    width: 20%;
    filter: drop-shadow(2px 2px 4px rgba(0,0,0, 0.4))
}
.font-kor{
  font-family: 'Galmuri9';
  font-weight: 200;
}

.font-eng{
  font-family: 'hydrophilia'
}
</style>