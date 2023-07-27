<template>
      <!-- {{ bubbledetailProps }} -->
  <div class="message-frame">
    <div class="window">
      <div class="close-button" @click="closeDetail()"></div>
      <div class="upper-bar">
        <div class="nickname-box">
          <div>임시이름</div>
        </div>
          <div class="sent-time">{{ bubbledetailProps.create_time.substr(11,5) }}</div>
      </div>
      <div class="content-box">
        {{ bubbledetailProps }}
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'

export default {
  name: 'MessageDetail',
  methods: {
    closeDetail() {
      this.$store.commit('SHOW_DETAIL', !this.showReceivedDetail)
      // this.$router.push({ name: "MainView", params: { pageid: this.state.userStore.shareid.share_id } })
    }
  },
  props: ['bubbledetailProps'],

  computed: {
    ...mapState(['showReceivedDetail'])
  }
}
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
  top: 51.5%;
  left: 50.5%;
  width: calc(var(--vh, 1vh) * 40);
  height: 36%;
  transform: translate(-50%, -50%);
  filter: drop-shadow(2px 2px 2px rgba(0,0,0, 0.3));
  background-color: transparent;
  background-image: url("../assets/message-background.png");
  background-size: 100%;
  background-repeat: no-repeat;
  display: flex;
  justify-content: center;
  flex-wrap: wrap;
}

.close-button {
  width: 100%;
  height: 16%;
  /* border: 1px solid black; */
}

.upper-bar {
  width: 100%;
  height: 10%;
  display: flex;
}

.nickname-box {
  width: auto;
  height: 100%;
  border: 0.5px solid darkslategray;
  border-radius: 0px 20px 20px 20px;
  outline: none;
  font-size: 95%;
  font-weight: bold;
  background: linear-gradient(180deg, #FFFFFF 0%, #B9D7EB 99.99%, #B9D7EB 100%);
  display: flex;
  align-items: center;
  margin-left: 6.5%;
  padding-left: 3%;
  padding-right: 3%;
}

.sent-time {
  display: flex;
  align-items: flex-end;
  margin-left: 5px;
  color: gray;
  width: auto;
  font-size: 12px;
}

.content-box {
  width: 100%;
  height: 51%;
  font-size: 100%;
  border: 1px solid darkslategray;
  border-radius: 0px 20px 20px 20px;
  outline: none;
  padding: 9px 12px 5px 12px;
  resize: none;
  background: linear-gradient(180deg, #FFFFFF 0%, #B9D7EB 99.99%, #B9D7EB 100%);
  margin-bottom: 6%;
  margin-left: 6.5%;
  margin-right: 11%;
  padding-left: 3%;
  padding-top: 2.3%;
}

::placeholder {
  color: gray;
  font-weight: normal;
}

.scroll::-webkit-scrollbar {
  display: none;
}
</style>