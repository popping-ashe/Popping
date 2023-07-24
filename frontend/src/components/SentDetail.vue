<template>
  <div class="message-frame">
    <div class="window">
      {{ messagedetailProps }} <br>
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

    },
  },
  props: ['messagedetailProps'],

  computed: {
    ...mapState(['showSentDetail'])
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

.message-frame {
  position: fixed;
  width: calc(var(--vw, 1vw) * 110);
  height: 100%;
  z-index: 999;
  background: rgba(0, 0, 0, 0.3);
}

.window {
  position: absolute;
  top: 50%;
  left: 50%;
  width: calc(var(--vw, 1vw) * 85);
  height:25%;
  background: white;
  transform: translate(-50%, -50%);
  border-radius: 45px;
  box-shadow: 0px 0px 20px black;
}
</style>