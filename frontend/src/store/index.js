import Vue from 'vue'
import Vuex from 'vuex'
import userStore from "@/store/modules/userStore";


Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    detailIndex : 0,
    showReceivedDetail : false,
    showSentDetail : false,
    messages: [
      {
        sentUser : '유저1',
        context : '내용1',
        timeLeft : '147',
      },
      {
        sentUser : '유저2',
        context : '내용2',
        timeLeft : '42',
      },
      {
        sentUser : '유저3',
        context : '내용3',
        timeLeft : '12',
      },
      {
        sentUser : '유저4',
        context : '내용4',
        timeLeft : '3',
      },
      {
        sentUser : '유저5',
        context : '내용5',
        timeLeft : '91',
      },
      {
        sentUser : '유저6',
        context : '내용6',
        timeLeft : '132',
      },
      {
        sentUser : '유저7',
        context : '내용7',
        timeLeft : '211',
      },
      {
        sentUser : '유저8',
        context : '내용8',
        timeLeft : '215',
      },
      {
        sentUser : '유저9',
        context : '내용9',
        timeLeft : '221',
      },
      {
        sentUser : '유저10',
        context : '내용10',
        timeLeft : '134',
      },
    ],
    sentList : [
      {
        name : '김지은',
        time : '07:59',
        status : true,
        context : '내용1내용1내용112312312312312321121'
      },
      {
        name : '박민아',
        time : '10:01',
        status : true,
        context : '내용2내용2내용2'
      },
      {
        name : '박정은',
        time : '12:31',
        status : false,
        context : '내용3 내용3 내용3'
      },
      {
        name : '서동훈',
        time : '14:36',
        status : false,
        context : '내용4 내용4 내용4'
      },
      {
        name : '양정훈',
        time : '17:51',
        status : true,
        context : '내용5 내용5 내용5'
      },
      {
        name : '임준수',
        time : '20:24',
        status : false,
        context : '내용6 내용6 내용6'
      },
      {
        name : '김김김',
        time : '21:30',
        status : true,
        context : '내용7 내용7 내용7'
      },
      {
        name : '이이이',
        time : '22:45',
        status : false,
        context : '내용8 내용8 내용'
      },
    ]
  },
  getters: {
  },
  mutations: {
    SET_NEW_INDEX(state, data){
      state.detailIndex = data;
    },
    SHOW_DETAIL(state, data){
      state.showReceivedDetail = data;
    },
    SHOW_SENT_DETAIL(state, data){
      state.showSentDetail = data;
    }
  },
  actions: {
  },
  modules: {
    userStore
  },
})



