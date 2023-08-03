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
    this.kakao(code);
  },
  methods: {
    ...mapActions(userStore, ["kakao"]),
  },
};
</script>

<style scoped></style>
