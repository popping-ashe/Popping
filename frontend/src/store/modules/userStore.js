import router from "@/router";
import {
  deleteuser,
  gettoken,
  readmessages,
  getshareidmessages,
  getshareid,
  getUserInfo,
  kakaologin,
  sentUserMessage,
  receivedUserMessage,
  logout,
} from "@/api/user";
import CryptoJS from "crypto-js";

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
    othermessages: null,
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
    checkOthermessages: function (state) {
      return state.othermessages;
    },
    isLoggedIn: (state) => state.isLoggedIn,
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
    SET_SENT_MESSAGES: (state, sentmessages) => {
      state.sentmessages = sentmessages;
    },
    SET_RECEIVED_MESSAGES: (state, receivedmessages) => {
      state.receivedmessages = receivedmessages;
    },
    SET_SHAREID: (state, shareid) => {
      state.shareid = shareid;
    },
    SET_OTHERMESSAGES: (state, othermessages) => {
      state.othermessages = othermessages;
    },
  },
  actions: {
    // 로그인 시 유저 정보 불러오기
    async kakao({ commit }, code) {
      await kakaologin(
        code,
        (response) => {
          if (response.status === 200) {
            let accessToken = response.data["accessToken"];
            let refreshToken = response.data["refreshToken"];
            commit("SET_IS_LOGIN", true);
            commit("SET_IS_LOGIN_ERROR", false);
            commit("SET_IS_VALID_TOKEN", true);
            const encryptAccessToken = CryptoJS.AES.encrypt(
              accessToken,
              CryptoJS.enc.Utf8.parse(process.env.VUE_APP_KEY),
              {
                iv: CryptoJS.enc.Utf8.parse(process.env.VUE_APP_IV),
                mode: CryptoJS.mode.CBC,
              }
            ).toString();
            const encryptRefreshToken = CryptoJS.AES.encrypt(
              refreshToken,
              CryptoJS.enc.Utf8.parse(process.env.VUE_APP_KEY),
              {
                iv: CryptoJS.enc.Utf8.parse(process.env.VUE_APP_IV),
                mode: CryptoJS.mode.CBC,
              }
            ).toString();
            localStorage.setItem("access-token", encryptAccessToken);
            localStorage.setItem("refresh-token", encryptRefreshToken);
            getUserInfo(
              (response) => {
                if (response.status == 200) {
                  commit("SET_USER_INFO", response.data);
                  localStorage.setItem("userinfo", JSON.stringify(response.data));
                  if (JSON.parse(localStorage.getItem("userinfo")).terms_agreement.length!=0) {
                    console.log(JSON.parse(localStorage.getItem("userinfo")).terms_agreement.length)
                   router.push('/terms') 
                  }
                } else {
                  console.log("유저 정보 없음");
                }
              },
              async (error) => {
                console.log(error);
                commit("SET_IS_VALID_TOKEN", false);
                router.push({ name: "LoginView" });
              }
            );
            getshareid(
              (response) => {
                if (response.status == 200) {
                  commit("SET_SHAREID", response.data);
                  localStorage.setItem("shareid", JSON.stringify(response.data));
                } else {
                  console.log("shareid 없음");
                }
              },
              async (error) => {
                console.log(error);
                router.push({ name: "LoginView" });
              }
            );
            sentUserMessage(
              (response) => {
                if (response.status == 200) {
                  commit("SET_SENT_MESSAGES", response.data);
                } else {
                  console.log("보낸 메세지 없음");
                }
              },
              async (error) => {
                console.log(error);
              }
            );
            receivedUserMessage(
              (response) => {
                if (response.status == 200) {
                  commit("SET_RECEIVED_MESSAGES", response.data);
                  router.push({
                    name: "MainView",
                    params: { pageid: this.state.userStore.shareid.share_id },
                  });
                } else {
                  console.log("받은 메세지 없음");
                }
              },
              async (error) => {
                console.log(error);
              }
            );
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

    // 로그아웃
    async logoutUser({ commit }) {
      await logout(
        (response) => {
          if (response.status == 200) {
            commit("SET_IS_LOGIN", false);
            commit("SET_IS_LOGIN_ERROR", true);
            commit("SET_IS_VALID_TOKEN", false);
            localStorage.clear();
            router.push({ name: "LoginView" });
          } else {
            commit("SET_IS_LOGIN", false);
            commit("SET_IS_LOGIN_ERROR", true);
            commit("SET_IS_VALID_TOKEN", false);
            localStorage.clear();
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
    //탈퇴
    async userdelete({ commit }) {
      await deleteuser(
        (response) => {
          if (response.status == 200) {
            commit("SET_IS_LOGIN", false);
            commit("SET_IS_LOGIN_ERROR", true);
            commit("SET_IS_VALID_TOKEN", false);
            localStorage.clear();
            router.push({ name: "LoginView" });
          } else {
            commit("SET_IS_LOGIN", false);
            commit("SET_IS_LOGIN_ERROR", true);
            commit("SET_IS_VALID_TOKEN", false);
            localStorage.clear();
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
    // 다른 유저 메인페이지에 메세지, 닉네임 띄우기
    async shareidmessage({ commit }, page) {
      await getshareidmessages(
        page,
        (response) => {
          if (response.status == 200) {
            commit("SET_OTHERMESSAGES", response.data);
          } else {
            console.log("잘못");
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
    // 메세지 읽음
    async changeread({ commit }, messageid) {
      await readmessages(
        messageid,
        (response) => {
          if (response.status == 200) {
            commit("SET_RECEIVED_MESSAGES", response.data);
          } else {
            console.log("잘못");
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },

    // 새로고침 or 페이지 넘어갈때 데이터 업데이트
    async updateUserData({ commit }) {
      getUserInfo(
        (response) => {
          if (response.status == 200) {
            commit("SET_USER_INFO", response.data);
          } else {
            console.log("유저 정보 없음");
          }
        },
        async (error) => {
          console.log(error);
          commit("SET_IS_VALID_TOKEN", false);
          router.push({ name: "LoginView" });
        }
      );
      sentUserMessage(
        (response) => {
          if (response.status == 200) {
            commit("SET_SENT_MESSAGES", response.data);
          } else {
            console.log("보낸 메세지 없음");
          }
        },
        async (error) => {
          console.log(error);
        }
      );
      receivedUserMessage(
        (response) => {
          if (response.status == 200) {
            commit("SET_RECEIVED_MESSAGES", response.data);
          } else {
            console.log("받은 메세지 없음");
          }
        },
        async (error) => {
          console.log(error);
        }
      );
    },

    // 새로운 access Token 발급
    async getnewaccesstoken({ commit }) {
      gettoken(
        (response) => {
          if (response.status == 200) {
            let accessToken = response.data["accessToken"];
            let refreshToken = response.data["refreshToken"];
            commit("SET_IS_LOGIN", true);
            commit("SET_IS_LOGIN_ERROR", false);
            commit("SET_IS_VALID_TOKEN", true);
            const encryptAccessToken = CryptoJS.AES.encrypt(
              accessToken,
              CryptoJS.enc.Utf8.parse(process.env.VUE_APP_KEY),
              {
                iv: CryptoJS.enc.Utf8.parse(process.env.VUE_APP_IV),
                mode: CryptoJS.mode.CBC,
              }
            ).toString();
            const encryptRefreshToken = CryptoJS.AES.encrypt(
              refreshToken,
              CryptoJS.enc.Utf8.parse(process.env.VUE_APP_KEY),
              {
                iv: CryptoJS.enc.Utf8.parse(process.env.VUE_APP_IV),
                mode: CryptoJS.mode.CBC,
              }
            ).toString();
            localStorage.setItem("access-token", encryptAccessToken);
            localStorage.setItem("refresh-token", encryptRefreshToken);
            router.go(0);
          } else {
            console.log("토큰 받아오기 오류");
          }
        },
        async (error) => {
          console.log(error);
          localStorage.clear();
          router.push({ name: "LoginView" });
        }
      );
    },
  },
};
const accessToken = localStorage.getItem("access-token");
const refreshToken = localStorage.getItem("refresh-token");
const userinfo = JSON.parse(localStorage.getItem("userinfo"));
const shareid = JSON.parse(localStorage.getItem("shareid"));

if (accessToken && refreshToken) {
  userStore.state.isLogin = true;
  userStore.state.isValidToken = true;
}
if (userinfo) {
  userStore.state.userInfo = userinfo;
}
if (shareid) {
  userStore.state.shareid = shareid;
}

export default userStore;
