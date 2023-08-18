import Vue from "vue";
import Vuex from "vuex";
import userStore from "@/store/modules/userStore";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    showReceivedDetail: false,
    showSentDetail: false,
    showMakeWindow: false,
    messages: [],
    sentList: [],
  },
  getters: {},
  mutations: {
    SHOW_DETAIL(state, data) {
      state.showReceivedDetail = data;
    },
    SHOW_SENT_DETAIL(state, data) {
      state.showSentDetail = data;
    },
    SHOW_MAKE_WINDOW(state, data) {
      state.showMakeWindow = data;
    },
  },
  actions: {},

  modules: {
    userStore,
  },
});
