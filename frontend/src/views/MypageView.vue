<template>
  <div class="page-container">
    <div class="page" :class="slideClass">
      {{ new_fav }}
      <!-- {{ fav_res }}<br>
      <div>{{ fav_toggle }}</div>
      <div class="new_fav">{{ new_fav }}</div> -->
      <!-- {{ fav_res_new[1].share_id }} -->
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
            <div class="unread">만료됨</div>
          </div>
        </div>
        <div class="sent-bubble-frame2">
          
          <div class="sent-bubble-text-frame">
            <div class="sent-or-fav">
              <div
                class="sent-bubble-text font-stardust"
                @click="changeTabBubble"
                :class="{ 'sent-bubble-text-no' : selectedTab ==='즐겨찾기'}"
                >
                <span v-if="selectedTab === '보낸버블'">
                  &nbsp;&nbsp;&nbsp;&nbsp;보낸버블 &nbsp;&nbsp;
                </span>
                <span v-else>
                  &nbsp;&nbsp;&nbsp;&nbsp;보낸버블&nbsp;&nbsp;&nbsp;&nbsp;
                </span>
              </div>
              <div
                class="sent-bubble-text font-stardust"
                @click="changeTabFav"
                :class="{ 'sent-bubble-text-no' : selectedTab ==='보낸버블' }"
              >
                <span v-if="selectedTab === '즐겨찾기'">
                  &nbsp;&nbsp;&nbsp;&nbsp;즐겨찾기&nbsp;&nbsp;&nbsp;
                </span>
                <span v-else>
                  &nbsp;&nbsp;&nbsp;&nbsp;즐겨찾기&nbsp;&nbsp;
                </span>
              </div>
            </div>
            
            <div class="selector-frame font-stardust" v-if="selectedTab == '보낸버블'" @click="changeReadOption">
              {{ readOption }}
            </div>
          </div>

          <div class="sent-message-frame" :style="{ borderRadius: selectedTab === '보낸버블' ? '0px 25px 25px 25px' : '25px 25px 25px 25px'}">
            
            <!-- 보낸메세지 -->
            <div v-if="selectedTab == '보낸버블'">
              <div
                v-for="(article, index) in nowShowing"
                :key="index"
                class="sent-message-box"
              @click="[sentDetail(index),analyticsBubble()]"
              >
                <div class="sent-message-ellipse font-kor">
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

            <!-- 즐겨찾기 -->
            <div v-else>
              <div v-for="(fav, index) in fav_res"
              :key="index"
              class="sent-message-box"
              @click="[analyticsFavorite(), $router.push(`/main/${fav.share_id}`)]"
              >
                <div class="sent-message-ellipse font-kor">
                  <div class="initial">{{ fav.nickname.substr(0, 1) }}</div>
                </div>

                <div v-if="fav.bio != ''" class="fav-frame font-pre">
                  <div class="fav-left">
                    <div class="fav-nickname">{{ fav.nickname.substr(0, 10) }}</div>
                    <div class="fav-lower">
                      {{ fav.bio }}
                    </div>
                  </div>
                  <div class="fav-state">
                    <svg xmlns="http://www.w3.org/2000/svg" height="1em" viewBox="0 0 576 512"><!--! Font Awesome Free 6.4.2 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license (Commercial License) Copyright 2023 Fonticons, Inc. --><path d="M316.9 18C311.6 7 300.4 0 288.1 0s-23.4 7-28.8 18L195 150.3 51.4 171.5c-12 1.8-22 10.2-25.7 21.7s-.7 24.2 7.9 32.7L137.8 329 113.2 474.7c-2 12 3 24.2 12.9 31.3s23 8 33.8 2.3l128.3-68.5 128.3 68.5c10.8 5.7 23.9 4.9 33.8-2.3s14.9-19.3 12.9-31.3L438.5 329 542.7 225.9c8.6-8.5 11.7-21.2 7.9-32.7s-13.7-19.9-25.7-21.7L381.2 150.3 316.9 18z"/></svg>
                  </div>
                </div>

                <div v-else class="fav-frame font-pre">
                  <div class="fav-left-nobio">
                    <div class="fav-nickname-nobio">{{ fav.nickname.substr(0, 10) }}</div>
                  </div>
                  <div class="fav-state">
                    <svg xmlns="http://www.w3.org/2000/svg" height="1em" viewBox="0 0 576 512"><!--! Font Awesome Free 6.4.2 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license (Commercial License) Copyright 2023 Fonticons, Inc. --><path d="M316.9 18C311.6 7 300.4 0 288.1 0s-23.4 7-28.8 18L195 150.3 51.4 171.5c-12 1.8-22 10.2-25.7 21.7s-.7 24.2 7.9 32.7L137.8 329 113.2 474.7c-2 12 3 24.2 12.9 31.3s23 8 33.8 2.3l128.3-68.5 128.3 68.5c10.8 5.7 23.9 4.9 33.8-2.3s14.9-19.3 12.9-31.3L438.5 329 542.7 225.9c8.6-8.5 11.7-21.2 7.9-32.7s-13.7-19.9-25.7-21.7L381.2 150.3 316.9 18z"/></svg>
                  </div>
                </div>
              </div>
            </div>

            <div class="font-kor" v-if="pleaseShare && selectedTab == '보낸버블'" style="margin-top: 40px; font-size: 13px">
              친구의 페이지를 공유받아 버블을 보내보세요
            </div>

            <div class="font-kor" v-if="fav_res == '' && selectedTab == '즐겨찾기'" style="margin-top: 40px; font-size: 13px">
              친구의 페이지를 공유받아 즐겨찾기에 등록해보세요
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
import { sentUserMessage, receivedUserMessageCount, getfavorite, postfavorite, deletefavorite} from "@/api/user";

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
      readOption: "전체",
      messageDetail: "",
      unreadMessageCount: "",
      toShowMessage: "",
      slideClass: "",
      pleaseShare: false,
      fav_res: null,
      selectedTab: "보낸버블",
      fav_toggle: '',
      new_fav : '',
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

    changeReadOption() {
      if (this.readOption == "전체") {
        this.readOption = "읽음"
        this.nowShowing = this.sentmessages.filter((article) => article.state === "읽음");
      } else if (this.readOption == "읽음") {
        this.readOption = "안읽음"
        this.nowShowing = this.sentmessages.filter((article) => article.state === "안읽음");
      } else if (this.readOption == "안읽음") {
        this.readOption = "전체"
        this.nowShowing = this.sentmessages
      }
      
      if (this.nowShowing.length == 0) {
        this.pleaseShare = true;
      } else {
        this.pleaseShare = false;
      }
    },

    like() {
      postfavorite(
        this.id,
        (response) => {
          if (response.status == 200) {
            // console.log(response.data.favorite_id)
            this.favorite_ids.push(response.data.favorite_id)
            // console.log(this.favorite_ids)
          } else {
            // console.log("잘못");
          }
        },
        (error) => {
          console.log(error);
        }
      )
    },

    dislike(idx) {
      this.new_fav[idx] = "N"
      deletefavorite(
        this.fav_res[idx].share_id,
        (response) => {
          if (response.status == 200) {
            // console.log(response.data)
            // const indexToRemove = this.favorite_ids.indexOf(parseInt(this.pageid));
            // console.log(this.favorite_ids)
            // console.log(indexToRemove)
            // console.log(this.pageid)
            // this.favorite_ids.splice(indexToRemove, 1)
            // if (this.favorite_ids.length < 2) {
            //   this.favorite_ids = []
            // } else {
            //   this.favorite_ids.splice(indexToRemove, 1)
            // }

            // if (indexToRemove != -1) {
            //   const newfav = this.favorite_ids.splice(indexToRemove, 1)
            //   this.favorite_ids = newfav
            //   // this.favorite_ids.splice(indexToRemove, 1);
            // }
            // console.log(this.favorite_ids)
          } else {
            // console.log("잘못");
          }
        },
        (error) => {
          console.log(error);
        }
      )
    },
    
    // addToggleToFav() {
    //   if (this.fav_res) {
    //     this.new_fav = new Array(this.fav_res.length).fill('Y')

    //     for (var i=0; i < this.fav_res.length; i++) {
    //       this.new_fav[i].push(this.fav_res[i])
    //       this.fav_res_new.push(this.fav_res[i])
    //     }
    //   }
    //     this.fav_res_new = this.fav_res
        
    // },

    changeTabBubble() {
      this.selectedTab = "보낸버블"
    },

    changeTabFav() {
      this.selectedTab = "즐겨찾기"
    },

    analyticsRead(){
      this.$gtag.event('click', {
        event_category: 'mypage',
        event_label: 'readButton',
        value: 'readButton',
      }); 
    },
    analyticsUnread(){
      this.$gtag.event('click', {
        event_category: 'mypage',
        event_label: 'unreadButton',
        value: 'unreadButton',
      }); 
    },
    analyticsFavorite(){
      this.$gtag.event('click', {
        event_category: 'mypage',
        event_label: 'favorite',
        value: 'favorite',
      }); 
    },
  },
  async logoutUser() {
    this.logoutUser;
  },
  created() {
    // this.updateUserData();
  },
  mounted() {
    receivedUserMessageCount(
      (response) => {
        if (response.status == 200) {
          this.receivedmessagescount = response.data.received_messages_count;
          this.sentmessagescount = response.data.sent_messages_count;
          this.unreadMessageCount = response.data.expired_messages_count;
        } else {
          // console.log("메세지 없음");
        }
      },
      async (error) => {
        console.log(error);
        await this.getnewaccesstoken();
      }
    );
    getfavorite(
      (response) => {
        if (response.status == 200) {
          // console.log(response.data)
          this.fav_res = response.data.favorites.reverse()
          // this.fav_res_new = response.data.favorites
          // this.fav_toggle = new Array(this.fav_res.length).fill('Y')
        } else {
          // console.log("메세지 없음");
        }
      },
      async (error) => {
        console.log(error);
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
  top: 15%;
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

.sent-bubble-text-frame {
  position: relative;
  width: 100%;
  display: flex;
  justify-content: space-between;
}

.sent-or-fav {
  display: flex;

}

.sent-bubble-text {
  position: relative;
  font-style: normal;
  font-weight: 500;
  line-height: 21px;
  /* or 117% */
  letter-spacing: -0.32px;
  color: #000000;
  text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.1);

  padding-top : 10px;
  padding-bottom: 10px;
  border-radius: 12px 12px 0px 0px;
  background-color: rgba(255, 255, 255, 0.4);
  font-size: 20px;
}

.sent-bubble-text-no {
  position: relative;
  font-style: normal;
  font-weight: 500;
  line-height: 21px;
  /* or 117% */
  letter-spacing: -0.32px;
  color: #000000;
  text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.1);

  padding-top : 10px;
  padding-bottom: 10px;
  border-radius: 12px 12px 0px 0px;
  background-color: transparent;
  font-size: 15px;
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
  /* color: gray; */
  text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.1);
  display: flex;
  align-items: flex-end;
  margin-bottom: 4px;
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
  width: 88.3%;
  height: 64.8%;
  top: 25.8%;
  align-items: center;
}

.sent-message-frame {
  position: absolute;
  width: 100%;
  height: 100%;
  text-align: center;
  padding-right: 4px;
  padding-left: 4px;
  padding-bottom: 15px;

  background-color: rgba(255, 255, 255, 0.44);
  overflow: scroll;
}

.sent-message-box {
  position: relative;
  width: 96%;
  height: 75px;
  background: rgba(255, 255, 255, 0.7);
  box-shadow: 2px 2px 4px rgba(0, 0, 0, 0.35);
  border-radius: 25px;
  text-align: center;
  margin-top: 15px;
  display: flex;
  align-items: center;
  opacity: 100%;
  margin-left: 1.5%;
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
  margin-top : 14.5px;
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
  margin-left: 1px;
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
  padding-right: 16px;
}

.sent-lower {
  display: flex;
  margin-top: 4px;
  font-size: 16px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  margin-right: 16px;
  margin-left: 1px;
}

.sent-upper-fav {
  width: 100%;
  margin-top : 12.5px;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.sent-mynickname-fav {
  font-weight: bold;
  font-size: 16px;
  margin-left: 1.5px;
}

.sent-lower-fav {
  font-size: 12px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  padding-right: 12px;
  margin-left: 1.5px;
  margin-top: 4.2px;
}

.sent-lower-fav-nobio {
  display: flex;
  height: 100%;
  font-size: 16px;
  font-weight: bold;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  margin-left: 1px;
  align-items: center;
}

.sent-state-fav {
  position: absolute;
  font-weight: bold;
  font-size: 12px;
  padding-right: 22px;
  padding-bottom: 1px;
  right: 0.2px;
  top: 15px;
}

.fav-frame {
  position: relative;
  text-align: left;
  width: 100%;
  height: 100%;
  display: flex;
  padding-left: 77px;
  justify-content: space-between;
}

.fav-left {
  width: 86%;
  height: 100%;
  display: flex;
  flex-direction: column;
}

.fav-nickname {
  font-weight: bold;
  font-size: 16px;
  width: 100%;
  margin-left: 1.5px; 
  margin-top: 13.5px;
}

.fav-lower {
  font-size: 12px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  margin-left: 1.5px;
  margin-top: 4.5px;
}

.fav-state {
  font-weight: bold;
  font-size: 12px;
  margin-top: 15px;
  margin-right: 20px;
}

.fav-left-nobio {
  width: 87%;
  height: 100%;
  display: flex;
  align-items: center;
}

.fav-nickname-nobio {
  font-weight: bold;
  font-size: 16px;
  width: 100%;
  margin-left: 1.5px; 
}

</style>
