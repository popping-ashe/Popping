import { apiInstance } from "./index.js";

const api = apiInstance();

async function kakaologin(code, success, fail) {
  await api.get(`/oauth/kakao/callback?code=${code}`).then(success).catch(fail);
}

async function getUserInfo(memberId, success, fail) {
  let token = "Bearer " + sessionStorage.getItem("access-token");
  api.defaults.headers["Authorization"] = token;
  await api.get(`/${memberId}`).then(success).catch(fail);
}

async function getUserMessage(success, fail) {
    let token = "Bearer " + sessionStorage.getItem("access-token");
    api.defaults.headers["Authorization"] = token;
    await api.get(``).then(success).catch(fail);
  }



export { kakaologin, getUserInfo, getUserMessage };