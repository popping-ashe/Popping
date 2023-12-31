import Vue from "vue";
import VueRouter from "vue-router";
import MainView from "../views/MainView.vue";
import LoginView from "../views/LoginView.vue";
import MypageView from "../views/MypageView.vue";
import GoogleLoginCallBack from "../views/GoogleLoginCallBack.vue";
import KakaoLoginCallBack from "../views/KakaoLoginCallBack.vue";
import SettingView from "../views/SettingView.vue";
import ErrorView from "../views/ErrorView.vue";
import TermsofUse from "../views/TermsofUse.vue";

// import userStore from "@/store/modules/userStore";

Vue.use(VueRouter);

const routes = [
  {
    path: "/main/:pageid",
    name: "MainView",
    component: MainView,
  },
  {
    path: "/",
    name: "LoginView",
    component: LoginView,
  },
  {
    path: "/mypage",
    name: "MypageView",
    component: MypageView,
  },
  {
    path: "/oauth/kakao/callback",
    name: "kakaoLoginCallBack",
    component: KakaoLoginCallBack,
  },
  {
    path: "/oauth/google/callback",
    name: "googleLoginCallBack",
    component: GoogleLoginCallBack,
  },
  {
    path: "/settings",
    name: "SettingView",
    component: SettingView,
  },
  {
    path: "/terms",
    name: "TermsofUse",
    component: TermsofUse,
  },
  {
    path: "/error",
    name: "ErrorView",
    component: ErrorView,
  },

];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

// router.beforeEach(async (to, from, next) => {
//   // 페이지에 접근하기 전에 사용자 데이터 업데이트
//   await userStore.dispatch("userStore/updateUserData");

//   // 사용자가 로그인 상태인지 확인
//   const isLoggedIn = userStore.getters["userStore/isLoggedIn"];

//   // 로그인이 필요한 페이지라면
//   if (to.meta.requiresAuth && !isLoggedIn) {
//     next({ name: "LoginView" });
//   } else {
//     next();
//   }
// });

export default router;
