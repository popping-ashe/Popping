import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import userStore from './store/modules/userStore'
import vuetify from './plugins/vuetify'



Vue.config.productionTip = false



new Vue({
  router,
  store,
  userStore,
  vuetify, // 수정된 부분
  render: h => h(App),
}).$mount('#app')

window.Kakao.init("");

