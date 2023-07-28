<template>
  <div>
    <div class="received" v-if="isLogin" @click="logoutUser">로그아웃</div>
  </div>
</template>

<script>
const userStore = "userStore";
import { mapState, mapActions } from 'vuex'

export default {
  name: "LoginCallBack",
  data() {
    return {

    };
  },
  async logoutUser() {
      this.logoutUser
  },
  methods: {
    ...mapActions(userStore, ['logoutUser', 'updateUserData']),
  },
  computed: {
    ...mapState(userStore,['userInfo',"isLogin", "isLoginError"]),
  }
}
</script>

<style>

</style>