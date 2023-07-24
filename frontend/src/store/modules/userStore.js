import router from "@/router";
// import { kakaologin } from "@/api/user";
import { getUserInfo, kakaologin, sentUserMessage } from "@/api/user";


const userStore = {
  namespaced: true,
  state: {
    isLogin: false,
    isLoggedIn: false,
    isLoginError: false,
    userInfo: null,
    isValidToken: false,
    sentmessages: null,
  },
  getters: {
    checkUserInfo: function (state) {
      return state.userInfo;
    },
    checkSentMessages: function (state) {
      return state.sentmessages;
    },
    checkToken: function (state) {
      return state.isValidToken;
    },
    isLoggedIn: state => state.isLoggedIn,
  },
  mutations: {
    SET_IS_LOGIN: (state, isLogin) => {
      state.isLogin = isLogin;

    },
    SET_IS_LOGIN_ERROR: (state, isLoginError) => {
      state.isLoginError = isLoginError;
    },
    SET_IS_VALID_TOKEN: (state, isValidToken) => {
      state.isValidToken = isValidToken;
    },
    SET_USER_INFO: (state, userInfo) => {
      state.isLogin = true;
      state.userInfo = userInfo;
      sessionStorage.setItem("access-token", userInfo.accessToken);
    },
    SET_SENT_MESSAGES: (state, sentmessages) => {
      state.sentmessages = sentmessages;
    },
  },
  actions: {
    async kakao({ commit }, code) {
      await kakaologin(
        code,
        (response) => {
          if (response.status === 200) {
            console.log(response)
            let accessToken = response.data["accessToken"];
            let refreshToken = response.data["refreshToken"];
            // console.log(accessToken)
            // console.log(refreshToken)
            commit("SET_IS_LOGIN", true);
            commit("SET_IS_LOGIN_ERROR", false);
            commit("SET_IS_VALID_TOKEN", true);
            commit("SET_IS_LOGGED_IN", true);
            sessionStorage.setItem("access-token", accessToken);
            sessionStorage.setItem("refresh-token", refreshToken);
            getUserInfo(
              (response) => {
                if (response.status == 200) {
                  commit("SET_USER_INFO", response.data);
                  sessionStorage.setItem("userinfo", JSON.stringify(response.data));
                  // console.log(userStore.state.userInfo.nickname);
                  // console.log(this.state)
                  router.push({ name: "MainView" });
                } else {
                  console.log("유저 정보 없음");
                }
              },
              async (error) => {
                console.log(error);
                // console.log(
                //   "getUserInfo() error code [토큰 만료되어 사용 불가] ::: ",
                //   error.response.status
                // );
                commit("SET_IS_VALID_TOKEN", false);
                router.push({ name: "LoginView" });
              }
            );
            sentUserMessage(
              (response) => {
                if (response.status == 200) {
                  commit("SET_SENT_MESSAGES", response.data)
                  sessionStorage.setItem("sentmessages", JSON.stringify(response.data));
                  console.log(userStore.state.sentmessages);
                } else {
                  console.log("보낸 메세지 없음");
                }
              },
              async (error) => {
                console.log(error);
                console.log('보낸ㅁ세지 받아오기 에러');
                
                // router.push({ name: "LoginView" });
              }
            )
          // router.push({ name: "MainView" });
          } else {
            commit("SET_IS_LOGIN", false);
            commit("SET_IS_LOGIN_ERROR", true);
            commit("SET_IS_VALID_TOKEN", false);
            commit("SET_IS_LOGGED_IN", false);
          }
          // router.push({ name: "MainView" });
        },
        (error) => {
          console.log()
          console.log(error);
        }
      );
    },
  },
}
const accessToken = sessionStorage.getItem("access-token");
const refreshToken = sessionStorage.getItem("refresh-token");
const userinfo = JSON.parse(sessionStorage.getItem("userinfo"));
const sentmessages = JSON.parse(sessionStorage.getItem("sentmessages"));

if (accessToken && refreshToken) {
  userStore.state.isLogin = true;
  userStore.state.isValidToken = true;
}
if (userinfo) {
  userStore.state.userInfo = userinfo;
}
if (sentmessages) {
  userStore.state.sentmessages = sentmessages;
}


export default userStore;

