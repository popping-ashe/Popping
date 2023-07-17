import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

// import VueMobileDetection from 'vue-mobile-detection'
// Vue.use(VueMobileDetection)



Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
