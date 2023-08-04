<template>
  <div class="frame" style="z-index: 2">
    <div class="upper-bar">
      <div class="new-button font-eng" style="margin-left: 6%">
        <div class="back-button font-eng" @click="closeNicknameEdit">Back</div>
      </div>
      <div class="username font-kor">
        <!-- 본인페이지 여부에 따라 표시 -->
        프로필 수정
      </div>
      <div class="new-button font-eng" style="margin-right: 6%">
        <div class="back-button font-eng" @click="stopEditing()">Done</div>
      </div>
    </div>
    <br /><br /><br /><br />

    <div class="article-counts font-pre" style="margin-left: 6%; margin-right: 8%">
      <div>
        <div style="font-size: 13px">닉네임 수정</div>
        <div v-if="!isEditing" class="input-container">
          <!-- <br> -->
          <div class="input-wrapper">
            <input
              class="edit-input"
              type="text"
              :placeholder="nickname"
              v-model="changednickname"
              ref="editInput"
              @keydown.enter="stopEditing"
              @input="handleInput"
            />
            <span class="clear-button" v-if="changednickname" @click="clearInput">X</span>
          </div>
        </div>
        <!-- <button @click="stopEditing">수정</button> -->
        <div style="font-size: 12px">{{ changednickname.length + 1 }} / 10</div>
      </div>
      <br />
    </div>
  </div>
</template>

<script>
import { mapActions } from "vuex";
import { changenickname } from "@/api/user";
const userStore = "userStore";

export default {
  name: "ChangeNickname",
  data() {
    return {
      nickname: this.$store.getters["userStore/checkUserInfo"].nickname,
      changednickname: "",
      isEditing: false,
    };
  },
  methods: {
    ...mapActions(userStore, ["logoutUser", "updateUserData"]),
    closeNicknameEdit() {
      this.$emit("close"); // 이벤트를 부모 컴포넌트에 전달하여 닫히도록 함
    },
    handleInput() {
      if (this.changednickname.length > 10) {
        this.changednickname = this.changednickname.slice(0, 10);
      }
    },
    startEditing() {
      this.isEditing = true;
      this.changednickname = this.nickname;
      this.$nextTick(() => {
        this.$refs.editInput.focus();
      });
    },
    stopEditing() {
      this.isEditing = false;
      if (this.nickname !== this.changednickname) {
        this.change();
      }
    },
    clearInput() {
      this.changednickname = "";
    },
    async change() {
      if (!this.changednickname) {
        this.isEditing = false;
        return;
      }
      changenickname(
        this.changednickname,
        (response) => {
          if (response.status == 200) {
            this.nickname = this.changednickname;
            this.updateUserData({ nickname: this.changednickname });
          } else {
            // console.log("잘못");
          }
          this.isEditing = false;
        },
        (error) => {
          console.log(error);
          this.isEditing = false;
        }
      );
      this.$emit("nickname-updated", this.changednickname);
      this.closeNicknameEdit();
    },
    analyticsNickname(){
      this.$gtag.event('click', {
        event_category: 'settings',
        event_label: 'changeNickname',
        value: 'changeNickname',
      }); 
    },
  },
};
</script>

<style scoped>
/* Your existing styles */
@font-face {
  font-family: "hydrophilia";
  src: url("../assets/fonts/hydrophilia-iced-regular.ttf") format("truetype");
  font-weight: normal;
  font-style: normal;
}
.frame {
  position: relative;
  height: calc(var(--vh, 1vh) * 100);
  width: calc(var(--vw, 1vw) * 110);
  overflow: hidden;
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

/* Your other styles */

.upper-bar {
  position: relative;
  width: 100%;
  height: 5.6%;
  top: 4.5%;
  display: flex;
  justify-content: space-between;
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
}

.username {
  width: 176px;
  height: 48px;
  justify-content: center;
  margin-top: 2px;

  font-style: normal;
  font-weight: 400;
  font-size: 14.5px;
  line-height: 21px;
  /* or 105% */
  display: flex;
  align-items: center;
  text-align: center;
  letter-spacing: -0.32px;
  color: #000000;

  text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.25);
}

.changenickname {
  font-style: normal;
  font-weight: 400;
  font-size: 11px;
  line-height: 21px;
  color: blue;
  display: flex;
  margin-top: -20px;
}

.nickname {
  font-weight: bold;
  font-size: 20px;
}
.logout {
  font-weight: bold;
  font-size: 15px;
}

.delete {
  display: flex;
  justify-content: center;
  font-size: 11px;
  color: #808384;
  text-decoration: underline;
}

.font-kor {
  font-family: "Galmuri9";
  font-weight: 200;
}

.font-eng {
  font-family: "hydrophilia";
}

.underline-input {
  position: relative;
  border: none;
  display: inline-block;
  /* border-bottom: 1px solid black; */
  padding-bottom: 2px;
}

.edit-input {
  display: inline-block;
  border: none;
  border-bottom: 1px solid black;
  outline: none;
  font-size: 16px;
  font-weight: bold;
  padding-left: 5px;
  width: 100%;
}
.input-container {
  display: flex;
  align-items: center;
}

.input-wrapper {
  position: relative;
  width: 100%;
}
.clear-button {
  position: absolute;
  right: 5px;
  top: 50%;
  transform: translateY(-50%);
  cursor: pointer;
}
</style>
