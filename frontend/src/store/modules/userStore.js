import router from "@/router";
// import { kakaologin } from "@/api/user";
import { getshareid, getUserInfo, kakaologin, sentUserMessage, receivedUserMessage, logout } from "@/api/user";


const userStore = {
  namespaced: true,
  state: {
    isLogin: false,
    isLoginError: false,
    userInfo: null,
    isValidToken: false,
    sentmessages: null,
    receivedmessages: null,
    shareid: null,
    // othermessages: null,
  },
  getters: {
    checkUserInfo: function (state) {
      return state.userInfo;
    },
    checkSentMessages: function (state) {
      return state.sentmessages;
    },
    checkShareId: function (state) {
      return state.shareid;
    },
    checkReceivedMessages: function (state) {
      return state.receivedmessages;
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
    SET_RECEIVED_MESSAGES: (state, receivedmessages) => {
      state.receivedmessages = receivedmessages;
    },
    SET_SHAREID: (state, shareid) => {
      state.shareid = shareid;
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
            console.log(this.state)
            sessionStorage.setItem("access-token", accessToken);
            sessionStorage.setItem("refresh-token", refreshToken);
            console.log(sessionStorage.getItem("access-token"))
            getUserInfo(
              (response) => {
                if (response.status == 200) {
                  commit("SET_USER_INFO", response.data);
                  sessionStorage.setItem("userinfo", JSON.stringify(response.data));
                  // console.log(userStore.state.userInfo.nickname);
                  // console.log(this.state)
                  router.push({ name: "MainView", params: { pageid: this.state.userStore.shareid.share_id } });
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
            getshareid(
              (response) => {
                if (response.status == 200) {
                  commit("SET_SHAREID", response.data);
                  sessionStorage.setItem("shareid", JSON.stringify(response.data));
                  // console.log(userStore.state.userInfo.nickname);
                  console.log(this.state.userStore.shareid.share_id)
                  
                } else {
                  console.log("shareid 없음");
                }
              },
              async (error) => {
                console.log(error);
                // commit("SET_SHAREID", null);
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
                console.log('보낸ap세지 받아오기 에러');
              }
            )
            receivedUserMessage(
              (response) => {
                if (response.status == 200) {
                  commit("SET_RECEIVED_MESSAGES", response.data)
                  sessionStorage.setItem("receivedmessages", JSON.stringify(response.data));
                  console.log(userStore.state.receivedmessages);
                } else {
                  console.log("받은 메세지 없음");
                }
              },
              async (error) => {
                console.log(error);
                console.log('받은메세지 받아오기 에러');
              }
            )
          } else {
            commit("SET_IS_LOGIN", false);
            commit("SET_IS_LOGIN_ERROR", true);
            commit("SET_IS_VALID_TOKEN", false);
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
    //여기까지 로그인 시 받아오는 정보

    async logoutUser({ commit }) {
      await logout((response) => {
        if (response.status == 200) {
          commit("SET_IS_LOGIN", false);
          commit("SET_IS_LOGIN_ERROR", true);
          commit("SET_IS_VALID_TOKEN", false);
          sessionStorage.clear();
        } else {
          console.log("잘못된 access token임. 로그아웃 처리.");
          commit("SET_IS_LOGIN", false);
          commit("SET_IS_LOGIN_ERROR", true);
          commit("SET_IS_VALID_TOKEN", false);
          sessionStorage.clear();
        }
      },
        (error) => {
        console.log(error);
        console.log('이건가');

      })
    },
    // async showusersbubble({ commit }) {
    //   await getshareidmessages((response) => {
    //     if (response.status == 200) {
    //       console.log(response.data)
    //     } else {
    //       console.log();

    //     }
    //   },
    //     (error) => {
    //     console.log(error);
    //   })
    // },
    // // async getnewaccesstoken({ commit }) {
    // async sendUserMessage( ) {

    // }
  },
  
}
const accessToken = sessionStorage.getItem("access-token");
const refreshToken = sessionStorage.getItem("refresh-token");
const userinfo = JSON.parse(sessionStorage.getItem("userinfo"));
const sentmessages = JSON.parse(sessionStorage.getItem("sentmessages"));
const receivedmessages = JSON.parse(sessionStorage.getItem("receivedmessages"));
const shareid = JSON.parse(sessionStorage.getItem("shareid"));

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
if (receivedmessages) {
  userStore.state.receivedmessages = receivedmessages;
}
if (shareid) {
  userStore.state.shareid = shareid;
}


export default userStore;

