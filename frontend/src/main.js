import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import userStore from "./store/modules/userStore";
import vuetify from "./plugins/vuetify";
import "vue2-toast/lib/toast.css";
import Toast from "vue2-toast";
import VueClipboard from "vue-clipboard2";
import VueCarousel from "vue-carousel";

VueClipboard.config.autoSetContainer = true;
Vue.use(VueClipboard);

Vue.use(Toast);
Vue.use(VueCarousel);

Vue.config.productionTip = false;

new Vue({
  router,
  store,
  userStore,
  vuetify, // 수정된 부분
  render: (h) => h(App),
}).$mount("#app");

const KAKAO_API_KEY = process.env.VUE_APP_KAKAO_API_KEY;
window.Kakao.init(KAKAO_API_KEY);
// window.Kakao.init("");
