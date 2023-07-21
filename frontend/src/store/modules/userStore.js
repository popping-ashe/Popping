import router from "@/router";
// import { kakaologin } from "@/api/user";
import { getUserInfo, kakaologin } from "@/api/user";

const userStore = {
  namespaced: true,
  state: {
    isLogin: false,
    isLoginError: false,
    userInfo: null,
    isValidToken: false,
    // getmessage:
  },
  getters: {
    checkUserInfo: function (state) {
      return state.userInfo;
    },
    checkToken: function (state) {
      return state.isValidToken;
    },
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
            console.log(accessToken)
            console.log(refreshToken)
            commit("SET_IS_LOGIN", true);
            commit("SET_IS_LOGIN_ERROR", false);
            commit("SET_IS_VALID_TOKEN", true);
            sessionStorage.setItem("access-token", accessToken);
            sessionStorage.setItem("refresh-token", refreshToken);
            getUserInfo(
              (response) => {
                if (response.status == 200) {
                  commit("SET_USER_INFO", response.data);
                  console.log(response.data);
                  console.log(this.state)
                  // router.push({ name: "MainView" });
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
            // receivedUserMessage(
              // (response) => {
              //   if (response.status == 200) {
              //     commit("SET_USER_INFO", response.data);
              //     console.log(response.data);
              //   } else {
              //     console.log("메세지 없음");
              //   }
              // },
              // async (error) => {
              //   console.log(error);
              //   router.push({ name: "LoginView" });
              // }
            // )
          } else {
            commit("SET_IS_LOGIN", false);
            commit("SET_IS_LOGIN_ERROR", true);
            commit("SET_IS_VALID_TOKEN", false);
          }
          router.push({ name: "MainView" });
        },
        (error) => {
          console.log()
          console.log(error);
        }
      );
    },
  },
}

export default userStore;