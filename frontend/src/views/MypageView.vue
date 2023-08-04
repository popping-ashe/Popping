<template>
  <div class="page-container">
    <div class="page" :class="slideClass">
      <div class="frame" style="z-index: 0">
        <SentDetail :messagedetail-props="messageDetail" v-if="showSentDetail" />
        <div class="upper-bar">
          <div class="new-button link-button" style="margin-left: 6%" @click="goBackPage(-1)">
            Back
          </div>
          <div class="mypage font-kor">MYPAGE</div>
          <div
            class="new-button link-button"
            style="margin-right: 6%; font-size: 13px"
            @click="goToPage('/settings')"
          >
            Settings
          </div>
        </div>
        <div class="article-counts font-pre">
          <div style="display: flex; justify-content: space-evenly">
            <div class="received-count">{{ this.receivedmessagescount }}</div>
            <div class="sent-count">{{ this.sentmessagescount }}</div>
            <div class="unread-count">{{ this.unreadMessageCount }}</div>
          </div>
          <div style="display: flex; justify-content: space-evenly">
            <div class="received">받음</div>
            <div class="sent">보냄</div>
            <div class="unread">안읽음</div>
          </div>
          <div style="display: flex; justify-content: center">
            <hr style="width: 85%" />
          </div>
        </div>
        <div class="sent-bubble-frame1" style="margin-top: 5%">
          <div class="sent-bubble-text-frame">
            <div class="sent-bubble-text font-stardust">보낸 버블</div>
            <div class="selector-frame font-stardust">
              <div
                class="selector-read"
                :style="{ color: readOption === 'read' ? 'black' : 'gray' }"
                @click="showReadOnly"
              >
                읽음
              </div>
              |
              <div
                class="selector-unread"
                :style="{ color: readOption === 'unread' ? 'black' : 'gray' }"
                @click="showUnreadOnly"
              >
                안읽음
              </div>
            </div>
          </div>
        </div>
        <div class="sent-bubble-frame2" style="margin-top: 5%">
          <div class="sent-message-frame">
            <div class="font-kor" v-if="pleaseShare" style="margin-top: 40px; font-size: 13px">
              친구의 페이지를 공유받아 버블을 보내보세요
            </div>
            <div
              v-for="(article, index) in nowShowing"
              :key="index"
              class="sent-message-box"
              @click="sentDetail(index)"
            >
              <div class="sent-message-ellipse font-kor">
                <!-- 유저 아이디 첫글자 -> 이미지로 변경-->
                <div class="initial">{{ article.receiver_nickname.substr(0, 1) }}</div>
              </div>
              <div class="sent-bubble-info-frame">
                <div class="sent-upper font-pre">
                  <div class="sent-upper-left">
                    <div class="sent-mynickname">{{ article.nickname.substr(0, 10) }}</div>
                    <div class="sent-datetime">
                      {{ article.create_time.substr(5, 2) }}/{{ article.create_time.substr(8, 2) }}
                      {{ article.create_time.substr(11, 5) }}
                    </div>
                  </div>
                  <div class="sent-state">{{ article.state }}</div>
                </div>
                <div class="sent-lower font-pre">
                  {{ article.content }}
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import SentDetail from "@/components/SentDetail.vue";

import { mapState, mapActions } from "vuex";
const userStore = "userStore";
import { sentUserMessage, receivedUserMessageCount } from "@/api/user";

export default {
  name: "MypageView",
  components: {
    SentDetail,
  },
  data() {
    return {
      dialog: false,
      sentmessagescount: null,
      receivedmessagescount: null,

      sentmessages: "",
      nowShowing: "",
      readOption: "all",
      messageDetail: "",
      unreadMessageCount: "",
      toShowMessage: "",
      slideClass: "",
      pleaseShare: false,
    };
  },
  methods: {
    ...mapActions(userStore, ["logoutUser", "updateUserData", "getnewaccesstoken"]),

    goToPage(path) {
      this.slideClass = "slide-in"; // 슬라이드 효과 시작
      setTimeout(() => {
        this.$router.push(path); // 페이지 전환
      }, 300); // 애니메이션 시간 (300ms) 이후에 페이지 전환 실행
    },
    goBackPage(path) {
      this.slideClass = "slide-out"; // 슬라이드 효과 시작
      setTimeout(() => {
        this.$router.go(path); // 페이지 전환
      }, 300); // 애니메이션 시간 (300ms) 이후에 페이지 전환 실행
    },

    closeDialog() {
      this.dialog = false;
    },
    sentDetail(idx) {
      this.messageDetail = this.nowShowing[idx];
      this.$store.commit("SHOW_SENT_DETAIL", !this.showSentDetail);
    },

    showReadOnly() {
      if (this.readOption == "all" || this.readOption == "unread") {
        this.readOption = "read";
        this.nowShowing = this.sentmessages.filter((article) => article.state === "읽음");
      } else {
        this.readOption = "all";
        this.nowShowing = this.sentmessages;
      }

      if (this.nowShowing.length == 0) {
        this.pleaseShare = true;
      } else {
        this.pleaseShare = false;
      }
    },

    showUnreadOnly() {
      if (this.readOption == "all" || this.readOption == "read") {
        this.readOption = "unread";
        this.nowShowing = this.sentmessages.filter((article) => article.state === "안읽음");
      } else {
        this.readOption = "all";
        this.nowShowing = this.sentmessages;
      }

      if (this.nowShowing.length == 0) {
        this.pleaseShare = true;
      } else {
        this.pleaseShare = false;
      }
    },
  },
  async logoutUser() {
    this.logoutUser;
  },
  created() {
    this.updateUserData();
  },
  mounted() {
    receivedUserMessageCount(
      (response) => {
        if (response.status == 200) {
          this.receivedmessagescount = response.data.received_messages_count;
          this.sentmessagescount = response.data.sent_messages_count;
          this.unreadMessageCount = response.data.unread_messages_count;
        } else {
          // console.log("메세지 없음");
        }
      },
      async (error) => {
        console.log(error);
        await this.getnewaccesstoken();
      }
    );

    sentUserMessage(
      (response) => {
        if (response.status == 200) {
          const sentmessages = response.data;
          this.sentmessages = sentmessages;

          if (this.sentmessages.length == 0) {
            this.pleaseShare = true;
          } else {
            this.pleaseShare = false;
          }

          this.nowShowing = sentmessages;
        } else {
          // console.log("보낸 메세지 없음");
        }
      },
      async (error) => {
        console.log(error);
      }
    );
  },
  computed: {
    ...mapState(["showSentDetail"]),
    ...mapState(userStore, ["userInfo", "isLogin", "isLoginError"]),
  },
};
</script>

<style scoped>
.page-container {
  position: relative;
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
.frame {
  position: relative;
  height: calc(var(--vh, 1vh) * 100);
  width: 100%;
  display: flex;
  justify-content: center;
  overflow: hidden;
}

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

.mypage {
  width: 176px;
  height: 48px;
  justify-content: center;

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
  margin-top: 2px;
  text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.25);
}

.article-counts {
  position: absolute;
  width: 100%;
  height: 7%;
  top: 15.5%;
}

.received-count {
  position: relative;
  width: 40%;
  height: 30px;

  font-style: normal;
  font-weight: 400;
  font-size: 22px;
  line-height: 21px;
  align-items: center;
  text-align: center;
  letter-spacing: -0.32px;
  color: #000000;
  text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.1);
}

.sent-count {
  position: relative;
  width: 20%;
  height: 30px;

  font-style: normal;
  font-weight: 400;
  font-size: 22px;
  line-height: 21px;
  align-items: center;
  text-align: center;
  letter-spacing: -0.32px;
  color: #000000;
  text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.1);
}

.unread-count {
  position: relative;
  width: 40%;
  height: 30px;

  font-style: normal;
  font-weight: 400;
  font-size: 22px;
  line-height: 21px;
  align-items: center;
  text-align: center;
  letter-spacing: -0.32px;
  color: #000000;
  text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.1);
}

.received {
  position: relative;
  width: 40%;
  height: 30px;
  font-style: normal;
  font-weight: 400;
  font-size: 14px;
  line-height: 21px;
  align-items: center;
  text-align: center;
  letter-spacing: -0.32px;
  color: #000000;
  text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.1);
}

.sent {
  position: relative;
  width: 20%;
  height: 30px;

  font-style: normal;
  font-weight: 400;
  font-size: 14px;
  line-height: 21px;
  /* or 150% */
  text-align: center;
  letter-spacing: -0.32px;
  color: #000000;
  text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.1);
}

.unread {
  position: relative;
  width: 40%;
  height: 30px;
  font-style: normal;
  font-weight: 400;
  font-size: 14px;
  line-height: 21px;
  /* or 150% */
  text-align: center;
  letter-spacing: -0.32px;
  color: #000000;
  text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.1);
}

.sent-bubble-frame1 {
  position: absolute;
  width: 85%;
  top: 26.3%;
  display: flex;
}

.sent-bubble-text-frame {
  position: relative;
  width: 100%;
  display: flex;
  justify-content: space-between;
}

.sent-bubble-text {
  position: relative;
  left: 4%;

  font-style: normal;
  font-weight: 500;
  font-size: 20px;
  line-height: 21px;
  /* or 117% */
  letter-spacing: -0.32px;
  color: #000000;
  text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.1);
}

.selector-frame {
  position: relative;
  right: 2%;
  display: flex;
  font-style: normal;
  font-weight: 400;
  font-size: 12px;
  line-height: 21px;
  /* or 210% */
  letter-spacing: -0.32px;
  color: gray;
  text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.1);
}

.selector-read {
  position: relative;
  margin-right: 4px;
}

.selector-unread {
  position: relative;
  margin-left: 7px;
}

.sent-bubble-frame2 {
  position: absolute;
  width: 85%;
  height: 59%;
  top: 32.2%;
  align-items: center;
  overflow: scroll;
}

.sent-message-frame {
  position: absolute;
  width: 100%;
  height: 72px;
  text-align: center;
  padding-right: 4px;
  padding-left: 4px;
}

.sent-message-box {
  position: relative;
  width: 100%;
  height: 75px;
  background: rgba(255, 255, 255, 0.7);
  box-shadow: 2px 3px 4px rgba(0, 0, 0, 0.35);
  border-radius: 30px;
  text-align: center;
  margin-bottom: 18px;
  display: flex;
  align-items: center;
  opacity: 100%;
}

.profile-area {
  width: 100px;
  height: 100%;
  display: flex;
  align-items: center;
}

.sent-message-ellipse {
  box-sizing: border-box;
  position: absolute;
  width: 54px;
  height: 54px;
  left: 14px;
  box-shadow: inset 1px 1px 7px rgba(0, 0, 0, 0.5);
  border-radius: 50%;
  background: #ffffff;
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 990;
  font-size: 20px;
}

.initial {
  margin-left: 3.5px;
  margin-bottom: 1px;
}

.sent-bubble-info-frame {
  position: relative;
  text-align: left;
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  padding-left: 76px;
}

.sent-upper {
  width: 100%;
  height: 41%;
  display: flex;
  align-items: flex-end;
  justify-content: space-between;
}

.sent-upper-left {
  display: flex;
}

.sent-mynickname {
  font-weight: bold;
  font-size: 12px;
}

.sent-datetime {
  margin-left: 4px;
  font-size: 11px;
  color: darkgray;
  display: flex;
  align-items: flex-end;
}

.sent-state {
  font-weight: bold;
  font-size: 12px;
  padding-right: 24px;
}

.sent-lower {
  display: inline-block;
  height: 40%;
  margin-top: 6px;
  font-size: 16px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  padding-right: 12px;
}
</style>
