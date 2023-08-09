import { apiInstance } from "./index.js";
import CryptoJS from "crypto-js";

const api = apiInstance();

//카카오로그인
async function kakaologin(code, success, fail) {
  await api.get(`/oauth/kakao/callback?code=${code}`).then(success).catch(fail);
}

//유저 정보 받아오기
async function getUserInfo(success, fail) {
  let accessToken = localStorage.getItem("access-token");
  const cipher = CryptoJS.AES.decrypt(
    accessToken,
    CryptoJS.enc.Utf8.parse(process.env.VUE_APP_KEY),
    {
      iv: CryptoJS.enc.Utf8.parse(process.env.VUE_APP_IV),
      mode: CryptoJS.mode.CBC,
    }
  );
  const decryptAccessToken = CryptoJS.enc.Utf8.stringify(cipher).toString();
  let token = "Bearer " + decryptAccessToken;
  api.defaults.headers["Authorization"] = token;
  await api.get(`/members/me`).then(success).catch(fail);
}

//내가 받은 메세지
async function receivedUserMessage(success, fail) {
  let accessToken = localStorage.getItem("access-token");
  const cipher = CryptoJS.AES.decrypt(
    accessToken,
    CryptoJS.enc.Utf8.parse(process.env.VUE_APP_KEY),
    {
      iv: CryptoJS.enc.Utf8.parse(process.env.VUE_APP_IV),
      mode: CryptoJS.mode.CBC,
    }
  );
  const decryptAccessToken = CryptoJS.enc.Utf8.stringify(cipher).toString();
  let token = "Bearer " + decryptAccessToken;
  api.defaults.headers["Authorization"] = token;
  await api.get(`/messages/me/received`).then(success).catch(fail);
}

//내가 보낸 메세지
async function sentUserMessage(success, fail) {
  let accessToken = localStorage.getItem("access-token");
  const cipher = CryptoJS.AES.decrypt(
    accessToken,
    CryptoJS.enc.Utf8.parse(process.env.VUE_APP_KEY),
    {
      iv: CryptoJS.enc.Utf8.parse(process.env.VUE_APP_IV),
      mode: CryptoJS.mode.CBC,
    }
  );
  const decryptAccessToken = CryptoJS.enc.Utf8.stringify(cipher).toString();
  let token = "Bearer " + decryptAccessToken;
  api.defaults.headers["Authorization"] = token;
  await api.get(`/messages/me/sent?sort=createTime,DESC`).then(success).catch(fail);
}

// 보내고 받고 안읽은 메세지 개수
async function receivedUserMessageCount(success, fail) {
  let accessToken = localStorage.getItem("access-token");
  const cipher = CryptoJS.AES.decrypt(
    accessToken,
    CryptoJS.enc.Utf8.parse(process.env.VUE_APP_KEY),
    {
      iv: CryptoJS.enc.Utf8.parse(process.env.VUE_APP_IV),
      mode: CryptoJS.mode.CBC,
    }
  );
  const decryptAccessToken = CryptoJS.enc.Utf8.stringify(cipher).toString();
  let token = "Bearer " + decryptAccessToken;
  api.defaults.headers["Authorization"] = token;
  await api.get(`/messages/me`).then(success).catch(fail);
}

//메세지 보내기
async function sendUserMessage(messageData, success, fail) {
  // let token = "Bearer " + localStorage.getItem("access-token");
  // api.defaults.headers["Authorization"] = token;
  await api.post(`/messages`, messageData).then(success).catch(fail);
}

//로그아웃
async function logout(success, fail) {
  let accessToken = localStorage.getItem("access-token");
  const cipher = CryptoJS.AES.decrypt(
    accessToken,
    CryptoJS.enc.Utf8.parse(process.env.VUE_APP_KEY),
    {
      iv: CryptoJS.enc.Utf8.parse(process.env.VUE_APP_IV),
      mode: CryptoJS.mode.CBC,
    }
  );
  const decryptAccessToken = CryptoJS.enc.Utf8.stringify(cipher).toString();
  let token = "Bearer " + decryptAccessToken;
  api.defaults.headers["Authorization"] = token;
  await api.get(`/auth/logout`).then(success).catch(fail);
}

//유저 닉네임 변경
async function changenickname(nickname, success, fail) {
  let accessToken = localStorage.getItem("access-token");
  const cipher = CryptoJS.AES.decrypt(
    accessToken,
    CryptoJS.enc.Utf8.parse(process.env.VUE_APP_KEY),
    {
      iv: CryptoJS.enc.Utf8.parse(process.env.VUE_APP_IV),
      mode: CryptoJS.mode.CBC,
    }
  );
  const decryptAccessToken = CryptoJS.enc.Utf8.stringify(cipher).toString();
  let token = "Bearer " + decryptAccessToken;
  api.defaults.headers["Authorization"] = token;
  await api.patch(`/members/me`, nickname).then(success).catch(fail);
}

// 유저 탈퇴
async function deleteuser(success, fail) {
  let accessToken = localStorage.getItem("access-token");
  const cipher = CryptoJS.AES.decrypt(
    accessToken,
    CryptoJS.enc.Utf8.parse(process.env.VUE_APP_KEY),
    {
      iv: CryptoJS.enc.Utf8.parse(process.env.VUE_APP_IV),
      mode: CryptoJS.mode.CBC,
    }
  );
  const decryptAccessToken = CryptoJS.enc.Utf8.stringify(cipher).toString();
  let token = "Bearer " + decryptAccessToken;
  api.defaults.headers["Authorization"] = token;
  await api.delete(`/signout`).then(success).catch(fail);
}
//access 토큰 새로 발급
async function gettoken(success, fail) {
  let refreshToken = localStorage.getItem("refresh-token");
  const cipher = CryptoJS.AES.decrypt(
    refreshToken,
    CryptoJS.enc.Utf8.parse(process.env.VUE_APP_KEY),
    {
      iv: CryptoJS.enc.Utf8.parse(process.env.VUE_APP_IV),
      mode: CryptoJS.mode.CBC,
    }
  );
  const decryptRefreshToken = CryptoJS.enc.Utf8.stringify(cipher).toString();
  let token = "Bearer " + decryptRefreshToken;
  api.defaults.headers["Authorization"] = token;
  await api.get(`/access-token/issue`).then(success).catch(fail);
}

// shareid 받아오기
async function getshareid(success, fail) {
  let accessToken = localStorage.getItem("access-token");
  const cipher = CryptoJS.AES.decrypt(
    accessToken,
    CryptoJS.enc.Utf8.parse(process.env.VUE_APP_KEY),
    {
      iv: CryptoJS.enc.Utf8.parse(process.env.VUE_APP_IV),
      mode: CryptoJS.mode.CBC,
    }
  );
  const decryptAccessToken = CryptoJS.enc.Utf8.stringify(cipher).toString();
  let token = "Bearer " + decryptAccessToken;
  api.defaults.headers["Authorization"] = token;
  await api.get(`/share`).then(success).catch(fail);
}

//메세지 읽음 상태 변경
async function readmessages(messageid, success, fail) {
  let accessToken = localStorage.getItem("access-token");
  const cipher = CryptoJS.AES.decrypt(
    accessToken,
    CryptoJS.enc.Utf8.parse(process.env.VUE_APP_KEY),
    {
      iv: CryptoJS.enc.Utf8.parse(process.env.VUE_APP_IV),
      mode: CryptoJS.mode.CBC,
    }
  );
  const decryptAccessToken = CryptoJS.enc.Utf8.stringify(cipher).toString();
  let token = "Bearer " + decryptAccessToken;
  api.defaults.headers["Authorization"] = token;
  await api.patch(`/messages/${messageid}`).then(success).catch(fail);
}

//shareid에 해당하는 유저 정보 받아오기
async function getshareidmessages(page, success, fail) {
  await api.get(`/share/${page}`).then(success).catch(fail);
}
//약관동의
async function changeagree(agree, success, fail) {
  let accessToken = localStorage.getItem("access-token");
  const cipher = CryptoJS.AES.decrypt(
    accessToken,
    CryptoJS.enc.Utf8.parse(process.env.VUE_APP_KEY),
    {
      iv: CryptoJS.enc.Utf8.parse(process.env.VUE_APP_IV),
      mode: CryptoJS.mode.CBC,
    }
  );
  const decryptAccessToken = CryptoJS.enc.Utf8.stringify(cipher).toString();
  let token = "Bearer " + decryptAccessToken;
  api.defaults.headers["Authorization"] = token;
  await api.patch(`/terms-agreement`, agree).then(success).catch(fail);
}

export {
  deleteuser,
  receivedUserMessageCount,
  readmessages,
  getshareidmessages,
  getshareid,
  kakaologin,
  getUserInfo,
  receivedUserMessage,
  sentUserMessage,
  sendUserMessage,
  logout,
  changenickname,
  gettoken,
  changeagree
};
