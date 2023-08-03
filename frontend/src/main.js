import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import userStore from './store/modules/userStore'
import vuetify from './plugins/vuetify'
import 'vue2-toast/lib/toast.css'
import Toast from 'vue2-toast'
import VueClipboard from 'vue-clipboard2'


VueClipboard.config.autoSetContainer = true;
Vue.use(VueClipboard)

Vue.use(Toast);


Vue.config.productionTip = false



new Vue({
  router,
  store,
  userStore,
  vuetify, // 수정된 부분
  render: h => h(App),
}).$mount('#app')

const KAKAKO_API_KEY = "6b633448ee0923565844cb4bee991a38";//이것은 나의 카카오 앱키
window.Kakao.init(KAKAKO_API_KEY);
// window.Kakao.init("");

