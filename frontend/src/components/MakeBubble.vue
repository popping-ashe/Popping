<template>
  <div class="message-frame">
    <div class="window"><br>
      senderID, reciverID HIDE<br>
      nickname <input class="nickname-input" type="text" v-model="nickname"><br>
      <div class="time-select">
        lifeTime
        <div class="time-one" @click="lifeTimeSel='1'"
          :class="{ 'selected' : lifeTimeSel === '1' }">1</div>|
        <div class="time-three" @click="lifeTimeSel='3'"
          :class="{ 'selected' : lifeTimeSel === '3'}">3</div>|
        <div class="time-day" @click="lifeTimeSel='24'"
          :class="{ 'selected' : lifeTimeSel === '24'}">24</div>
      </div>
      contents <input class="context-input" type="text" v-model="contents"><br><br>
      <br>
    <button class="send">보내기</button><br>
    <button @click="closeDetail()">창 닫기</button>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'

export default {
  name: 'MakeBubble',
  components : {

  },
  data() {
    return {
      lifeTimeSel: '1',
      nickname : '',
      contents: '',
    };
  },
  methods: {
    closeDetail() {
      this.$store.commit('SHOW_MAKE_WINDOW', !this.showMakeWindow)
    }
  },
    computed: {
    ...mapState(['showMakeWindow'])
  }

}
</script>

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
  width: calc(var(--vw, 1vw) * 100);
  height:45%;
  background: white;
  transform: translate(-50%, -50%);
  border-radius: 45px;
  box-shadow: 0px 0px 20px rgba(0, 0, 0, 0.4);
}

.nickname-input {
  width: 40%;
  height: 32px;
  font-size: 15px;
  border: 0;
  border-radius: 15px;
  outline: none;
  padding-left: 10px;
  background-color: rgb(233, 233, 233);
}

.context-input {
  width: 70%;
  height: 100px;
  font-size: 15px;
  border: 0;
  border-radius: 15px;
  outline: none;
  padding-left: 10px;
  background-color: rgb(233, 233, 233);
}

.time-select {
  display: flex;
  color: gray;
}

.time-one {
  margin: 0px 5px;
}

.time-three {
  margin: 0px 5px;
}

.time-day {
  margin: 0px 5px;
}

.selected {
  color: black
}
</style>