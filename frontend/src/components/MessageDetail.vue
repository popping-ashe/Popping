<template>
  <!-- {{ bubbledetailProps }} -->
  <div class="message-frame animate__animated animate__fadeIn">
    <div class="window font-pre" v-click-outside="closeDetail">
      <div class="close-button" @click="closeDetail()"></div>
      <div class="upper-bar">
        <div class="nickname-and-time">
        <div class="nickname-box">
          <div>&nbsp;&nbsp;&nbsp;<span v-html="bubbledetailProps.nickname"></span>&nbsp;&nbsp;&nbsp;</div>
        </div>
        <div class="sent-time">{{ bubbledetailProps.create_time.substr(11, 5) }}</div>
        </div>
        <div class="reply-button font-kor" v-if="bubbledetailProps.reply_available === 'Y'" @click="openReply">&nbsp;&nbsp;&nbsp;답장&nbsp;&nbsp;&nbsp;</div>
      </div>
      <div class="content-box">
        <span v-html="bubbledetailProps.content"></span>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";
import vClickOutside from "v-click-outside";

export default {
  name: "MessageDetail",
  components: {
  },
  data() {
    return {

    };
  },
  directives: {
    clickOutside: vClickOutside.directive,
  },
  methods: {
    closeDetail() {
      this.$store.commit("SHOW_DETAIL", !this.showReceivedDetail);
      // this.$router.push({ name: "MainView", params: { pageid: this.state.userStore.shareid.share_id } })
    },
    
    openReply() {
      this.closeDetail()
      this.$emit("openReply");
      this.$emit("replyID", this.bubbledetailProps.message_id)

    }
  },
  props: ["bubbledetailProps"],

  computed: {
    ...mapState(["showReceivedDetail"]),
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
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
  top: calc(var(--vh, 1vh) * 50.5);
  left: 50.5%;
  width: calc(var(--vh, 1vh) * 40);
  height: calc(var(--vh, 1vh) * 36);
  transform: translate(-50%, -50%);
  filter: drop-shadow(2px 2px 2px rgba(0, 0, 0, 0.3));
  background-color: transparent;
  background-image: url("../assets/message-background.png");
  background-size: 100%;
  background-repeat: no-repeat;
  display: flex;
  /* justify-content: center; */
  flex-wrap: wrap;
}

.close-button {
  width: 35%;
  height: 15%;
  /* border: 1px solid black; */
}

.upper-bar {
  width: 100%;
  height: 10%;
  display: flex;
  justify-content: space-between;
  margin-left: 6.5%;
  margin-right: 11.5%;
}

.nickname-and-time {
  display: flex;
}

.nickname-box {
  width: auto;
  height: 100%;
  border: 1px solid darkslategray;
  border-radius: 0px 20px 20px 20px;
  outline: none;
  white-space: nowrap;
  overflow: hidden;
  /* text-overflow: ellipsis; */
  font-size: 12px;
  font-weight: 600;
  background: linear-gradient(180deg, #ffffff 0%, #b9d7eb 99.99%, #b9d7eb 100%);
  display: flex;
  align-items: center;

}

.sent-time {
  display: flex;
  align-items: flex-end;
  margin-left: 5px;
  margin-right: 3px;
  color: gray;
  width: auto;
  font-size: 10px;
}

.reply-button {
  display: flex;
  width: auto;
  height: 100%;
  border: 1px solid darkslategray;
  border-radius: 20px;
  align-items: center;
  justify-content: center;
  font-size: 10px;
  white-space: nowrap;
  margin-top: 0.5%;
  /* margin-right: 11%; */
  background: rgba(158, 216, 245, 0.25);
  
}

.content-box {
  width: 100%;
  height: 51%;
  font-size: 13px;
  border: 1px solid darkslategray;
  border-radius: 0px 20px 20px 20px;
  outline: none;
  padding: 9px 12px 5px 12px;
  resize: none;
  background: linear-gradient(180deg, #ffffff 0%, #b9d7eb 99.99%, #b9d7eb 100%);
  margin-bottom: 6%;
  margin-left: 6.5%;
  margin-right: 11%;
  padding-top: 3.2%;
  overflow: scroll;
  white-space: normal;
}

::placeholder {
  color: gray;
  font-weight: normal;
}

.scroll::-webkit-scrollbar {
  display: none;
}
</style>