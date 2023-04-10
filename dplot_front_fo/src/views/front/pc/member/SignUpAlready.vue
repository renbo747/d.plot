<template>
  <main class="sign-up-sns member-top-padding">
    <div class="container container-400">
      <div>
        <h1 class="signup__complete-title font-weight-700 text-black">
          회원정보안내
        </h1>
      </div>
      <template v-if="true">
        <p class="already-signup__desc dp-title02 text-black mb-8">
          {{ textData }}
        </p>
        <div class="already-signup__info">
          <div class="already-signup__flex d-flex">
            <p class="already-signup__name mb-0 dp-p text-gray-700">아이디</p>
            <p class="already-signup__id mb-0 dp-p text-gray-700">
              {{ param.useridaster }}
            </p>
          </div>
          <div class="already-signup__flex d-flex">
            <p class="already-signup__name mb-0 dp-p text-gray-700">이메일</p>
            <p class="already-signup__phone mb-0 dp-p text-gray-700">
              {{ param.emailaster }}
            </p>
          </div>
        </div>
        <div class="signup-already-btn">
          <b-button
            class="dp-btn text-white btn-h48"
            variant="gray-800"
            squared
            @click="$router.push({ name: 'member-login' })"
          >
            <span>로그인하기</span>
          </b-button>
        </div>
      </template>
      <hr class="dp-hr dp-mt-40" />
      <div class="dp-panel customer-center-pc mb-0 pb-0">
        <p class="customer-center__title-pc dp-p font-weight-400 mb-0">
          고객센터 안내
        </p>
        <p class="customer-center__number-pc font-weight-700 text-black mb-0">
          1666-3642
        </p>
        <div class="customer-center-wrap">
          <div class="customer-center__time01 d-flex">
            <p class="center-title-pc dp-p mb-0">운영시간</p>
            <p class="center-time-pc text-black dp-p mb-0">
              10:00 ~ 17:00 <br />※휴뮤 - 토,일 및 공휴일
            </p>
          </div>
          <div class="customer-center__time02 d-flex">
            <p class="center-title-pc dp-p mb-0">점심시간</p>
            <p class="center-time-pc dp-p text-black mb-0">12:00 ~ 13:00</p>
          </div>
        </div>
      </div>
    </div>
  </main>
</template>

<script>
export default {
  data() {
    return {
      userType: "",
      textData: "",
      // snsTitle:""
      param: {},
    };
  },
  mounted() {
    if (this.$util.isEmpty(this.$route.params.joinchtype)) {
      this.$nextTick(() => {
        this.$eventBus.$emit("alert", "알림", "비정상적인 접근입니다.", () => {
           this.$router.replace({name:'member-login'});
        });
      });
    }
    this.param = this.$route.params;
    this.$util.debug(JSON.stringify(this.param));
    switch (this.param.joinchtype) {
      case "UCT002":
        this.userType = "naverMember";
        this.textData = "네이버로 가입된 회원정보가 있습니다.";
        // this.snsTitle = "네이버";
        break;
      case "UCT003":
        this.userType = "kakaoMember";
        this.textData = "카카오로 가입된 회원정보가 있습니다.";
        // this.snsTitle = '카카오';
        break;
      case "UCT004":
        this.userType = "appleMember";
        this.textData = "애플로 가입된 회원정보가 있습니다.";
        // this.snsTitle = '카카오';
        break;
      default:
        //임시::: 애플도 일단 일반으로
        this.userType = "userMember";
        this.textData = "이미 가입된 회원정보가 있습니다.";
        break;
    }
  },
};
</script>
