import { apiInstance } from "./index.js";

const api = apiInstance();

async function kakaologin(code, success, fail) {
  await api.get(`/oauth/kakao/callback?code=${code}`).then(success).catch(fail);
}

async function getUserInfo( success, fail) {
  let token = "Bearer " + sessionStorage.getItem("access-token");
  api.defaults.headers["Authorization"] = token;
  await api.get(`/members/1`).then(success).catch(fail);
}

async function receivedUserMessage(success, fail) {
  let token = "Bearer " + sessionStorage.getItem("access-token");
  api.defaults.headers["Authorization"] = token;
    await api.get(`/messages/me/received`).then(success).catch(fail);
  }

async function sentUserMessage(success, fail) {
  let token = "Bearer " + sessionStorage.getItem("access-token");
  api.defaults.headers["Authorization"] = token;
    await api.get(`/messages/me/sent`).then(success).catch(fail);
  }

async function sendUserMessage(success, fail) {
  let token = "Bearer " + sessionStorage.getItem("access-token");
  api.defaults.headers["Authorization"] = token;
    await api.post(`/messages`).then(success).catch(fail);
  }



export { kakaologin, getUserInfo, receivedUserMessage, sentUserMessage, sendUserMessage };