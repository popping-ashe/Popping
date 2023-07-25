import Vue from 'vue'
import VueRouter from 'vue-router'
import MainView from '../views/MainView.vue'
import LoginView from '../views/LoginView.vue'
import MypageView from '../views/MypageView.vue'
import LoginCallBack from '../views/LoginCallBack.vue'


Vue.use(VueRouter)

const routes = [
  {
    path: '/main/:pageid',
    name: 'MainView',
    component: MainView
  },
  {
    path:'/',
    name: 'LoginView',
    component: LoginView
  },
  {
    path:'/mypage',
    name: 'MypageView',
    component: MypageView
  },
  {
    path: '/oauth/kakao/callback',
    name: 'loginCallBack',
    component: LoginCallBack
  },

]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
