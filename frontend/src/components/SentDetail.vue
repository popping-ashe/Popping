<template>
  <div class="message-frame">
    <div class="window">
      {{ sentList[detailIndex] }} <br>
    <button @click="closeDetail()">창 닫기</button>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'

export default {
  name: 'SentDetail',
  methods: {
    closeDetail() {
      this.$store.commit('SHOW_SENT_DETAIL', !this.showSentDetail)

    }
  },

  computed: {
    ...mapState(['sentList', 'detailIndex', 'showSentDetail'])
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
  top: 50%;
  left: 51%;
  width: 40%;
  height:30%;
  background: white;
  transform: translate(-50%, -50%);
  border-radius: 45px;
  box-shadow: 0px 0px 20px black;
}
</style>