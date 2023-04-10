<template>
  <main class="id-find member-top-padding">
    <div class="container container-400">
      <div>
        <h1 class="signup__complete-title font-weight-700 text-black mb-0">
          회원정보 확인결과
        </h1>
      </div>
      <p
        class="id-find__description dp-title02 text-black font-weight-400 mb-0"
      >
        고객님이 가입하신 아이디입니다.
      </p>
      <div class="id-find__box-pc">
        <p class="id-find__id mb-0 font-weight-700 dp-title02 text-black">
          {{ param.useridaster }}
        </p>
        <p class="id-find__date-pc mb-0 font-weight-400 text-gray-888 dp-p-sm">
          <span>{{ param.regdtstr }}</span> 가입
        </p>
      </div>
      <div class="id-find__email-pc">
        <p class="id-find__email-text dp-p-sm text-black mb-0">
          {{ param.emailaster }}
        </p>
        <b-button
          class="dp-btn pl-0 pr-0 id-find-email-btn"
          variant="outline-gray-800"
          @click="sendId('EMAIL')"
          squared
        >
          <span class="dp-p-sm text-gray-800">메일로 받기</span>
        </b-button>
      </div>
      <hr class="dp-hr" />
      <div class="id-find__phone-pc">
        <p class="id-find__phone-text dp-p-sm text-black mb-0">
          {{ param.mobileaster }}
        </p>
        <b-button
          class="dp-btn pl-0 pr-0 id-find-phone-btn"
          variant="outline-gray-800"
          @click="sendId('MOBILE')"
          squared
        >
          <span class="dp-p-sm text-gray-800">메시지로 받기</span>
        </b-button>
      </div>
      <p class="id-find-send-info-pc mb-0 font-weight-400 dp-p-sm">
        회원정보에 등록된 이메일 / 휴대폰번호로 전체 아이디가 발송됩니다.
      </p>
      <div class="id-find__success-buttons d-flex">
        <b-button
          class="id-find__success-btn01 dp-btn pl-0 pr-0 btn-h48"
          v-if="param.joinchtype !== 'UCT001'"
          variant="outline-gray-800"
          squared
          @click="newPw()"
        >
          <span class="dp-p font-weight-400">비밀번호 재설정</span>
        </b-button>
        <b-button
          class="id-find__success-btn02 dp-btn pl-0 pr-0 text-white btn-h48"
          variant="gray-800"
          squared
          @click="$router.replace('/member/login')"
        >
          <span class="dp-p font-weight-400">로그인</span>
        </b-button>
      </div>
    </div>
  </main>
</template>

<script>
export default {
  data() {
    return {
      signupdt: null,
      param: {
        useridaster: "",
        regdtstr: "",
        emailaster: "",
        mobileaster: "",
        joinchtype: "",
      },
    };
  },
  mounted() {
    if (this.$util.isNull(this.$route.params.memberInfo)) {
      this.$nextTick(() => {
        this.$eventBus.$emit("alert", "알림", "잘못된 접근입니다.",  ()=>{
          this.$router.replace({name:'member-login'});
        });
      });
    } else {
      this.param = this.$route.params.memberInfo;
      console.log(this.$route.params.memberInfo)
    }
  },
  methods: {
    // 메일로 아이디 보내기
    sendId(type) {
      const param = this.param;
      param.type = type;

      this.$http.post("/member/sendId", param).then((result) => {
        if (result.statusCode == 200) {
          const data = result.data;
          if (sendType == "EMAIL") {
            this.$eventBus.$emit(
              "alert",
              "알림",
              "아이디를 이메일로 전송했습니다."
            );
          } else {
            this.$eventBus.$emit(
              "alert",
              "알림",
              "아이디를 핸드폰번호로 전송했습니다."
            );
          }
        }
      });
    },
    newPw() {
      this.$router.push({
        name: "member-password-new",
        params: { memberInfo: this.param },
      });
    },
  },
};
</script>