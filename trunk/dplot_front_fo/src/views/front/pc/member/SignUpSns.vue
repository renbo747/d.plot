<template>
  <!-- TODO : 07/01 yiy pc signupsns 변경 필요 -->
  <main class="sign-up-sns member-top-padding">
    <div class="container container-400">
      <div>
        <h1 class="signup__complete-title font-weight-700 text-black">
          회원정보 안내
        </h1>
      </div>
      <p class="already-signup__desc dp-title02 text-black mb-8">
        {{ textData }}
      </p>
      <p
        class="text-gray-700 mb-0 dp-p text-gray-700 signup-description-sns"
        v-if="userType === 'expiredMember'"
      >
        탈퇴 회원은 30일간 재가입이 불가합니다.
      </p>
      <p
        class="text-gray-700 mb-0 dp-p text-gray-700 signup-description-sns"
        v-if="userType === 'naverMember'"
      >
        네이버 계정과 연결하여 간편하게 로그인 하세요
      </p>
      <div class="already-signup__info">
        <div
          v-if="
            userType === 'kakaoMember' ||
            userType === 'naverMember' ||
            userType === 'userMember'
          "
          class="already-signup__flex d-flex"
        >
          <p class="already-signup__name mb-0 dp-p text-gray-700">아이디</p>
          <p class="already-signup__id mb-0 dp-p text-gray-700">dadamnc</p>
        </div>
      </div>
      <div class="signup-already-btn">
        <b-button
          v-if="userType === 'userMember' || userType === 'kakaoMember'"
          class="dp-btn text-white btn-h48"
          variant="gray-800"
          squared
          @click="handleLogIn"
        >
          <span>로그인하기</span>
        </b-button>
        <b-button
          v-else-if="userType === 'naverMember'"
          class="dp-btn text-white btn-h48"
          variant="gray-800"
          squared
        >
          <span>네이버 계정 연결하기</span>
        </b-button>
        <b-button
          v-else-if="userType === 'expiredMember'"
          class="dp-btn text-white btn-h48"
          variant="gray-800"
          squared
          @click="$router.push('/')"
        >
          <span>메인으로</span>
        </b-button>
      </div>
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
              월~금 10:00 ~ 16:00
            </p>
          </div>
          <div class="customer-center__time02 d-flex">
            <p class="center-title-pc dp-p mb-0">점심시간</p>
            <p class="center-time-pc dp-p text-black mb-0">12:00 ~ 13:00  <br/>※휴뮤 - 토,일 및 공휴일</p>
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
      // todo:  가입된 경우 유저 타입으로 구분 필요
      // userMember (일반), kakaoMember (카카오), naverMember (네이버), expiredMember (탈퇴멤버)
      userType: "expiredMember",
      textData: "이미 가입된 회원정보가 있습니다.",
    };
  },
  created() {
    if (this.userType === "userMember") {
      this.textData = "이미 가입된 회원정보가 있습니다.";
    } else if (this.userType === "kakaoMember") {
      this.textData = "카카오로 가입된 회원정보가 있습니다.";
    } else if (this.userType === "naverMember") {
      this.textData = "네이버로 가입된 회원정보가 있습니다.";
    } else if (this.userType === "expiredMember") {
      this.textData =
        "탈퇴 후 30일이 경과하지 않은 회원정보가 포함되어 있습니다.";
    }
  },
  methods: {
    handleLogIn() {
      this.$router.push("/member/login");
    },
  },
};
</script>
