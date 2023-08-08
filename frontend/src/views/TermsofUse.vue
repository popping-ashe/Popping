<template>
  <div class="page-container">
    <div class="page" :class="slideClass">
      <div class="frame" style="z-index: 0">
        <div class="upper-bar">
          <div class="new-button font-eng">
            <div @click="goBackPage(-1)">Back</div>
          </div>
          <div class="username font-kor">

          </div>
        </div>
        <br /><br /><br /><br />

        <div class="term-frame font-pre">
          <div class="nickname">서비스 이용약관에<br>동의해주세요.</div><br>
          <div>
            <label for="chkAll"><input id="chkAll" type="checkbox" v-model="agreetoall" @click="toggleAllAgreements">&nbsp; 네, 모두 동의합니다.</label>
            <br><hr>
          </div>
          <div class="term" v-for="(term, index) in terms_agreement" :key=index>
            <div>
              <label :for="term.terms_id"><input :id="term.terms_id" type="checkbox" v-model="terms_agreement[index].state" true-value="ACTIVE" false-value="PENDING">
              &nbsp; {{ getMandatoryLabel(term.mandatory) }} {{ term.title }}</label>
            </div>
            <div class="term-detail">보기</div>
          </div>
            <div class="signup-button-frame">
              <div class="signup-button" @click="signUp()" :style="{ background: signupButtonColor }">가입하기</div>
            </div>
        </div>
        <br>
        {{ agreedTermIds }}<br>
        signUp : {{ temp }}

        <br />
      </div>
    </div>
  </div>
</template>

<script>

export default {
  name: "TermsofUse",
  components: {
  },
  data() {
    return {
      toggler: true,
      terms_agreement: [
        {
          terms_agreement_id: 1,
          terms_id: 1,
          title: "서비스 이용약관에 동의",
          content: "카카오스타일 쇼핑 플랫폼 이용약관",
          mandatory: "Y",
          state: "ACTIVE",
          agreement_date: "2023-08-07T15:50:05.980233"
        },
        {
          terms_agreement_id: 2,
          terms_id: 2,
          title: "개인정보 수집 및 이용 동의",
          content: "개인정보 수집 및 이용 동의",
          mandatory: "Y",
          state: "PENDING",
          agreement_date: "2023-08-07T15:49:06.454691"
        },
        {
          terms_agreement_id: 3,
          terms_id: 3,
          title: "개인정보 수집 및 이용 동의",
          content: "개인정보 수집 및 이용 동의",
          mandatory: "N",
          state: "PENDING",
          agreement_date: "2023-08-07T15:49:06.454691"
        },
      ],
      agreetoall: false,  // 전체동의/해제용 변수
      temp: false,        // 버튼 눌렸는지 체크용, 지워도됨
    };
  },
  methods: {
    // mandatory: Y, N으로 넘어오는거 글자로 변환
    getMandatoryLabel(mandatory) {
      return mandatory === "Y" ? "(필수)" : "(선택)";
    },


    // 전체동의 전체해제
    toggleAllAgreements() {
      const newState = this.agreetoall ? "PENDING" : "ACTIVE"
      this.terms_agreement.forEach((term) => {
        term.state = newState
      })
    },

    // 가입하기 버튼 필수 선택 안됐을땐 비활성화
    signUp() {
      const mandatoryCheck = this.terms_agreement
      .filter(term => term.mandatory === "Y")
      .every(term => term.state === "ACTIVE");

      if (mandatoryCheck) {
        // 여기가 가입하기 버튼 클릭됐을때
        this.temp = true;
      } else {
        this.temp = false;
      }
    }
    

  },
    computed: {
    // 가입하기 버튼 색
    signupButtonColor() {
      const allMandatoryActive = this.terms_agreement
        .filter(term => term.mandatory === "Y")
        .every(term => term.state === "ACTIVE")

      return allMandatoryActive ? "linear-gradient(180deg, #FFFFFF 0%, #B9D7EB 99.99%)" : "linear-gradient(180deg, #FFFFFF 0%, #CCCCCC 99.99%)"
    },
    
    // 동의한 항목만 리스트로 표시
    agreedTermIds() {
      return this.terms_agreement
        .filter(term => term.state === "ACTIVE")
        .map(term => term.terms_id);
    }
  }
};
</script>

<style scoped>
.page-container {
  position: relative;
  width: 100%;
  height: calc(var(--vh, 1vh) * 100);
  /* overflow: hidden; */
}

.page {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  transition: transform 0.3s;
}

.slide-in {
  transform: translateX(-100%);
}
.slide-out {
  transform: translateX(100%);
}
@font-face {
  font-family: "hydrophilia";
  src: url("../assets/fonts/hydrophilia-iced-regular.ttf") format("truetype");
  font-weight: normal;
  font-style: normal;
}
.frame {
  position: relative;
  height: 100%;
  width: 100%;
  overflow: hidden;
}

.upper-bar {
  position: relative;
  width: 100%;
  height: 5.6%;
  top: 4.5%;
  display: flex;
  /* justify-content: space-between; */
  align-items: center;
}

.new-button {
  width: 65px;
  height: 29px;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: white;
  box-sizing: border-box;
  border-style: solid;
  border-color: #808384;
  border-color: rgba(128, 131, 132, 1);
  border-width: 1px;
  border-radius: 16px;
  -moz-border-radius: 16px;
  -webkit-border-radius: 16px;
  box-shadow: inset 0px -3px 3px #d8d8d8;
  padding: 2px 10px 4px;
  text-decoration: none;
  font-family: "hydrophilia";
  color: black;
  font-size: 14px;
  font-weight: 600;

  position: absolute;
  left: 6%;
  z-index: 1;
}

.username {
  flex: 1;
  width: 176px;
  height: 48px;
  justify-content: center;
  margin-top: 2px;

  font-style: normal;
  font-weight: 400;
  font-size: 15px;
  line-height: 21px;
  /* or 105% */
  display: flex;
  align-items: center;
  text-align: center;
  letter-spacing: -0.32px;
  color: #000000;
  text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.25);

  position: relative;
  z-index: 0;
}



.nickname {
  font-weight: bold;
  font-size: 20px;
}


.slide-enter-active,
.slide-leave-active {
  transition: transform 0.3s;
}

.slide-enter,
.slide-leave-to /* .slide-leave-active in <2.1.8 */ {
  transform: translateX(100%);
}
.nickname-edit-overlay {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 1);
  z-index: 999;
  display: flex;
  justify-content: center;
  align-items: center;
}

.term-frame {
  width: 100%;
  padding-left: 8%;
  padding-right: 8%;

}

.term {
  width: 100%;
  display: flex;
  justify-content: space-between;
  margin-bottom: 8px;

}

.term-detail {
  color: gray;
  text-decoration: underline;
}

.signup-button-frame {
  margin-top: 24px;
  width: 100%;
  height: 40px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.signup-button {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 95%;
  height: 100%;
  background-color: lightgray;
  border-radius: 24px;
  border: 1px solid black;
  /* #9ed8f5 */
}



</style>
