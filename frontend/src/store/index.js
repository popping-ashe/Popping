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
      
    ],
    sentList : [
      
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



