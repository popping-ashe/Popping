<template>
  <div class="message-frame animate__animated animate__fadeIn">
    <div class="window font-pre" v-click-outside="closeDetail">
      <div class="close-button" @click="closeDetail()"></div>
      <div class="term-detail-frame font-pre">
        <div class="term-detail" v-html="messageTemp">
        </div>
      </div>
      <div class="close-button-frame" >
        <div class="close-button-two" @click="closeDetail()">
          닫기
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";
import vClickOutside from "v-click-outside";

export default {
  name: "termsDetail",
  directives: {
    clickOutside: vClickOutside.directive,
  },
  methods: {
    closeDetail() {
      this.$store.commit("SHOW_DETAIL", !this.showReceivedDetail);
      // this.$router.push({ name: "MainView", params: { pageid: this.state.userStore.shareid.share_id } })
    },
  },
  props: ["termdetailProps"],

  computed: {
    messageTemp() {
      return this.termdetailProps.replace(/\n/gi,"<br>")
    },

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
  left: 50%;
  width: calc(var(--vh, 1vh) * 42);
  height: calc(var(--vh, 1vh) * 53);
  transform: translate(-50%, -50%);
  filter: drop-shadow(2px 2px 2px rgba(0, 0, 0, 0.3));
  background-color: transparent;
  background-image: url("../assets/term_window.png");
  background-size: 100%;
  background-repeat: no-repeat;
  display: flex;
  flex-direction: column;
}

.close-button {
  width: 30%;
  height:8%;
  /* border: 1px solid black; */
}

.term-detail-frame {
  margin-top: 5%;
  width: 100%;
  height: 67%;
  overflow: scroll;
}

.term-detail {
  margin-left: 7%;
  margin-right: 10%;
  font-size: 80%;
  border-radius: 24px;
  /* box-shadow: inset 1px 1px 2px black; */
  height: 100%;
  word-break: break-all;
}


.close-button-frame {
  height: 24px;
  display: flex;
  justify-content: center;
  margin-top: 5%;
}


.close-button-two {
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


::placeholder {
  color: gray;
  font-weight: normal;
}

.scroll::-webkit-scrollbar {
  display: none;
}
</style>
