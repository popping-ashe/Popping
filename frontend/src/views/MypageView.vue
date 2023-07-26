<template>
  <div class="frame" style="z-index: 0;">
      <SentDetail :messagedetail-props="messageDetail" v-if="showSentDetail"/>
    <div class="upper-bar">
      <div class="back-ellipse" @click="$router.go(-1)">
        <div class="back-emoji">
          <svg xmlns="http://www.w3.org/2000/svg" height="1em" viewBox="0 0 320 512"><path d="M9.4 233.4c-12.5 12.5-12.5 32.8 0 45.3l192 192c12.5 12.5 32.8 12.5 45.3 0s12.5-32.8 0-45.3L77.3 256 246.6 86.6c12.5-12.5 12.5-32.8 0-45.3s-32.8-12.5-45.3 0l-192 192z"/></svg>
        </div>
      </div>
      <div class="mypage font-stardust">
        마이페이지
      </div>
      <div class="settings-ellipse">
        <div class="settings-emoji">
          <!-- <svg xmlns="http://www.w3.org/2000/svg" height="1em" viewBox="0 0 512 512"><path d="M495.9 166.6c3.2 8.7 .5 18.4-6.4 24.6l-43.3 39.4c1.1 8.3 1.7 16.8 1.7 25.4s-.6 17.1-1.7 25.4l43.3 39.4c6.9 6.2 9.6 15.9 6.4 24.6c-4.4 11.9-9.7 23.3-15.8 34.3l-4.7 8.1c-6.6 11-14 21.4-22.1 31.2c-5.9 7.2-15.7 9.6-24.5 6.8l-55.7-17.7c-13.4 10.3-28.2 18.9-44 25.4l-12.5 57.1c-2 9.1-9 16.3-18.2 17.8c-13.8 2.3-28 3.5-42.5 3.5s-28.7-1.2-42.5-3.5c-9.2-1.5-16.2-8.7-18.2-17.8l-12.5-57.1c-15.8-6.5-30.6-15.1-44-25.4L83.1 425.9c-8.8 2.8-18.6 .3-24.5-6.8c-8.1-9.8-15.5-20.2-22.1-31.2l-4.7-8.1c-6.1-11-11.4-22.4-15.8-34.3c-3.2-8.7-.5-18.4 6.4-24.6l43.3-39.4C64.6 273.1 64 264.6 64 256s.6-17.1 1.7-25.4L22.4 191.2c-6.9-6.2-9.6-15.9-6.4-24.6c4.4-11.9 9.7-23.3 15.8-34.3l4.7-8.1c6.6-11 14-21.4 22.1-31.2c5.9-7.2 15.7-9.6 24.5-6.8l55.7 17.7c13.4-10.3 28.2-18.9 44-25.4l12.5-57.1c2-9.1 9-16.3 18.2-17.8C227.3 1.2 241.5 0 256 0s28.7 1.2 42.5 3.5c9.2 1.5 16.2 8.7 18.2 17.8l12.5 57.1c15.8 6.5 30.6 15.1 44 25.4l55.7-17.7c8.8-2.8 18.6-.3 24.5 6.8c8.1 9.8 15.5 20.2 22.1 31.2l4.7 8.1c6.1 11 11.4 22.4 15.8 34.3zM256 336a80 80 0 1 0 0-160 80 80 0 1 0 0 160z"/></svg> -->
          <SettingsPopupVue/>
        </div>
      </div>
    </div>
    <div class="article-counts font-stardust">
      <div style="display: flex; justify-content: space-evenly">
      <div class="received-count">{{this.receivedmessagescount}}</div>
      <div class="sent-count">{{ this.sentmessagescount }}</div>
      <div class="unread-count">{{ unreadMessageCount }}</div>
      </div>
      <div style="display: flex; justify-content: space-evenly">
      <div class="received">받음</div>
      <div class="sent">보냄</div>
      <div class="unread">안읽음</div>
      </div>
    </div>
    <div class="sent-bubble-frame1">
      <div class="sent-bubble-text-frame">
        <div class="sent-bubble-text font-stardust">
          보낸 버블 
        </div>
      <div class="selector-frame font-stardust">
        <div class="selector-read" :style="{color: readOption ==='read' ? 'black' : 'gray'}" @click="showReadOnly">읽음</div>|
        <div class="selector-unread" :style="{color: readOption ==='unread' ? 'black' : 'gray'}"  @click="showUnreadOnly">안읽음</div>
      </div>
    </div>
    </div>
    <div class="sent-bubble-frame2">
      <div class="sent-message-frame">
        <!-- for문 -->
        <div v-for="(article, index) in nowShowing" :key="index" class="sent-message-box" @click="sentDetail(index)">
          <div class="sent-message-ellipse font-stardust">
            <div class="user-initial "><!-- 유저 아이디 첫글자 -->{{article.nickname.substr(0,1)}}</div>
          </div>
          <div class="sent-bubble-info-frame font-healthset">
            <div class="sent-time-status">
              <div style="margin-right: 5px;">{{ article.create_time.substr(5,2) }}/{{ article.create_time.substr(8,2) }}
                {{ article.create_time.substr(11,5) }}</div>
              <div>{{ article.state }}</div>
            </div>
            <div class="sent-bubble-context">{{ article.content.substr(0,15) }}</div>
          </div>
        </div>
      <button v-if="isLogin" @click="logoutUser">로그아웃</button>
      </div>
    </div>
    <!-- <SettingsPopupVue/> -->
  </div>
</template>

<script>
import SettingsPopupVue from '@/components/SettingsPopup.vue';
import SentDetail from '@/components/SentDetail.vue'

import { mapState,mapActions } from 'vuex'
const userStore = "userStore";

export default {
  name: 'MypageView',
  components : {
    SettingsPopupVue,
    SentDetail
  },
  data() {
    return {
      dialog: false,
      sentmessagescount: null,
      receivedmessagescount:null,

      sentmessages: "",
      nowShowing: "",
      readOption: 'all',
      messageDetail : '',
      unreadMessageCount : '',
  
};
  },
  methods: {
    ...mapActions(userStore, ['logoutUser', 'updateUserData']),
    
    closeDialog() {
      this.dialog = false;
    },
    sentDetail(idx) {
      this.messageDetail = this.nowShowing[idx]
      this.$store.commit('SHOW_SENT_DETAIL', !this.showSentDetail)
    },

    showReadOnly() {
      if (this.readOption == 'all' || this.readOption == 'unread') {
        this.readOption = 'read'
        this.nowShowing = this.sentmessages.filter((article) => article.state === '읽음')
      }
      else {
        this.readOption = 'all'
        this.nowShowing = this.sentmessages
      }
    },

    showUnreadOnly() {
      if (this.readOption == 'all' || this.readOption == 'read') {
        this.readOption = 'unread'
        this.nowShowing = this.sentmessages.filter((article) => article.state === '안읽음')
      }
      else {
        this.readOption = 'all'
        this.nowShowing = this.sentmessages
      }

    }
  },
  async logoutUser() {
      this.logoutUser
  },
  created() {
    this.updateUserData()
  },
  mounted() {
    const sentmessages = this.$store.getters["userStore/checkSentMessages"];
    const receivedmessages = this.$store.getters["userStore/checkReceivedMessages"];
    console.log(sentmessages)
    this.sentmessages = sentmessages
    this.sentmessagescount = sentmessages.length;
    this.receivedmessagescount = receivedmessages.length;
    console.log(this.sentmessagescount)
    this.nowShowing = sentmessages

    this.unreadMessageCount = this.sentmessages.filter((article) => article.state === '안읽음').length

 

  },
  computed: {
    ...mapState(['showSentDetail']),
    ...mapState(userStore,['userInfo',"isLogin", "isLoginError"]),
  }
}
</script>

<style scoped>
/* iPhone 14 Pro - 2 */

.frame {
  
  position: relative;
  height: calc(var(--vh, 1vh) * 100);
  width: 100%;
  display: flex;
  justify-content: center;
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

.back-ellipse {
  box-sizing: border-box;
  width: 40px;
  height: 40px;
  margin-left: 6%;
  background: #FFFFFF;
  border: 1px solid #505050;
  border-radius: 50%;
  box-shadow: 1px 1px 3px rgba(0, 0, 0, 0.25);
  transition: all ease 0.4s;
  }

.back-ellipse:hover {
    filter: brightness(70%);
    transition: all ease 0.4s;
  }

.settings-ellipse {
  box-sizing: border-box;

  width: 40px;
  height: 40px;
  margin-right: 6%;
  background: #FFFFFF;
  border: 1px solid #505050;
  border-radius: 50%;
  box-shadow: 1px 1px 3px rgba(0, 0, 0, 0.25);
  transition: all ease 0.4s;
  }

.settings-ellipse:hover {
  filter: brightness(70%);
  transition: all ease 0.4s;
  }

.back-emoji {
  position: relative;
  width: 32px;
  height: 32px;
  left: 14px;
  top: 5px;
}

.settings-emoji {
  position: relative;
  width: 32px;
  height: 32px;
  left: 10px;
  top: 0px;
}

.mypage {
  width: 176px;
  height: 48px;
  justify-content: center;


  font-style: normal;
  font-weight: 400;
  font-size: 24px;
  line-height: 21px;
  /* or 105% */
  display: flex;
  align-items: center;
  text-align: center;
  letter-spacing: -0.32px;

  color: #000000;

  text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.25);
  }

.article-counts {
  position: absolute;
  width: 100%;
  height: 7%;
  top: 15.5%;
  
}

.received-count{
  position: relative;
  width: 33.3%;
  height: 30px;

  font-style: normal;
  font-weight: 400;
  font-size: 25px;
  line-height: 21px;
  align-items: center;
  text-align: center;
  letter-spacing: -0.32px;
  color: #000000;
  text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.1);
}

.sent-count {
  position: relative;
  width: 33.3%;
  height: 30px;

  font-style: normal;
  font-weight: 400;
  font-size: 25px;
  line-height: 21px;
  align-items: center;
  text-align: center;
  letter-spacing: -0.32px;
  color: #000000;
  text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.1);

}

.unread-count {
  position: relative;
  width: 33.4%;
  height: 30px;

  font-style: normal;
  font-weight: 400;
  font-size: 25px;
  line-height: 21px;
  align-items: center;
  text-align: center;
  letter-spacing: -0.32px;
  color: #000000;
  text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.1);

}

.received{
  position: relative;
  width: 33.3%;
  height: 30px;
  left: 1px;
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
  width: 33.4%;
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
  width: 33.3%;
  height: 30px;
  left: 1px;
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
  top: 30.7%;
  align-items: center;
  overflow: scroll;

  font-family: '헬스셋';
}

::-webkit-scrollbar {
  display: none;
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
  height: 64px;
  background: rgba(255, 255, 255, 0.485);
  box-shadow: 2px 2px 2px darkgrey;
  border-radius:20px;
  text-align: center;
  margin-bottom: 12px;
  display: flex;
  align-items: center;
  opacity: 100%;
}

.sent-message-ellipse {
  box-sizing: border-box;
  position: relative;
  width: 44px;
  height: 44px;
  left: 14px;

  border-radius: 50%;
  background: #ffffff;
  display: flex;
  align-items: center;
  justify-content: center;
}

.user-initial {
  left: 3.1px;
  position: relative;
  font-style: normal;
  font-weight: 500;
  font-size: 18px;
  line-height: 21px;
  /* or 117% */
  display: flex;
  align-items: center;
  text-align: center;
  letter-spacing: -0.32px;
  margin-right: 2px;
  color: #000000;
  text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.1);
}

.sent-bubble-info-frame {
  position: relative;
  text-align: left;
  left: 24px;
  bottom: 2px;
}

.sent-time-status {
  display: flex;

  font-style: normal;
  font-weight: 400;
  font-size: 12px;
  line-height: 21px;
  letter-spacing: -0.32px;
  color: darkgray;
}

.sent-bubble-context {
  height: 20px;

  font-style: normal;
  font-weight: 400;
  font-size: 18px;
  line-height: 21px;
  /* or 117% */
  display: flex;
  align-items: center;
  letter-spacing: -0.32px;
  overflow: hidden;
  color: #000000;
  vertical-align: top;
  overflow: hidden;
}

.font-stardust{
  font-family: 'Stardust'
}

.font-healthset{
  font-family: 'HealthSet';
}


</style>