<template>
  <main class="password member-top-padding">
    <div class="container container-400">
      <div>
        <h1 class="signup__complete-title font-weight-700 text-black mb-0">
          새 비밀번호 설정
        </h1>
      </div>
      <div class="password-wrap">
        <div class="password-new-description">
          <p
            class="
              password-find__description
              dp-title02
              text-black
              font-weight-400
              mb-0
            "
          >
            새로운 비밀번호를 설정해주세요.
          </p>
        </div>
        <form>
          <div class="form-group">
            <base-input
              :type="'password'"
              label="새 비밀번호"
              placeholder="비밀번호를 입력해 주세요."
              :is-label="true"
              v-model="newpw"
            />
          </div>
          <div class="form-group mb-0 new-password-repeat">
            <base-input
              :type="'password'"
              label="새 비밀번호 재입력"
              placeholder="비밀번호를 다시 입력해주세요."
              :is-label="true"
              v-model="confirmpw"
            />
          </div>
          <div class="password-btn">
            <b-button
              class="dp-btn text-white btn-h48"
              variant="gray-800"
              @click="changeNewPw()"
              squared
            >
              <span>비밀번호 설정하기</span>
            </b-button>
          </div>
        </form>
      </div>
    </div>
  </main>
</template>

<script>
export default {
  data() {
    return {
      param: null,
      newpw: "", // 새로운 비밀번호
      confirmpw: "", // 새로운 비밀번호 확인 (새로운 비밀번호랑 일치여부)
    };
  },
  mounted() {
    if (this.$util.isEmpty(this.$route.params)) {
      this.$nextTick(() => {
        this.$eventBus.$emit("alert", "알림", "잘못된 접근입니다.", () => {
          this.$router.replace({ name: "member-login" });
        });
      });
    }
    this.param = this.$route.params.memberInfo;
  },
  methods: {
    changeNewPw() {
      this.$front.changePw(this, () => {
        this.$router.replace("/member/login");
      });
    },
  },
};
</script>
