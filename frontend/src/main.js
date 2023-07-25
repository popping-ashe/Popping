import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import userStore from './store/modules/userStore'
import vuetify from './plugins/vuetify'
import 'vue2-toast/lib/toast.css';
import Toast from 'vue2-toast';


Vue.use(Toast);


Vue.config.productionTip = false



new Vue({
  router,
  store,
  userStore,
  vuetify, // 수정된 부분
  render: h => h(App),
}).$mount('#app')

window.Kakao.init("");

