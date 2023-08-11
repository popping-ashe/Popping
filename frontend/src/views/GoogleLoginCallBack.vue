<template>
  <div></div>
</template>
<script>
import { mapState, mapActions } from "vuex";

const userStore = "userStore";

export default {
  name: "LoginCallBack",
  components: {},
  data() {
    return {
      message: "",
    };
  },
  computed: {
    ...mapState(userStore, ["isLogin", "isLoginError", "userInfo"]),
  },
  async created() {
    let code = this.$route.query.code;
    this.google(code);
  },
  methods: {
    ...mapActions(userStore, ["google"]),
  },
};
</script>

<style scoped></style>
