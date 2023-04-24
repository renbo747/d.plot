<template>
  <b-modal
    id="AlreadySignupModal"
    modal-class="dp-modal page-layer already-signup id-find__fail"
    scrollable
    centered
    :hide-footer="true"
  >
    <!-- Already Signup Modal Header -->
    <template #modal-header="{ cancel }">
      <h5 class="modal-title">회원정보 안내</h5>
      <i class="modal-close" @click="cancel()">
        <img
          src="@assets.mobile/img/icon/icon-close-black-20px.svg"
          alt="닫기"
        />
      </i>
    </template>

    <!-- Already Signup Modal Body -->
    <div class="page-layer__body">
      <p class="already-signup__desc" v-if="true">
        {{ textData }}
      </p>
      <p class="text-gray-700 mb-0 dp-p-sm" v-else>
        탈퇴 후 30일이 경과하지 않은<br />
        회원정보가 포함되어 있습니다.<br />
        탈퇴 회원은 30일간 재가입이 <br />
        불가합니다.<br />
      </p>
      <div class="already-signup__info">
        <div class="already-signup__flex">
          <p>아이디</p>
          <p class="already-signup__id">{{ param.useridaster }}</p>
        </div>
        <div class="already-signup__flex">
          <p>이메일</p>
          <p class="already-signup__id">{{ param.emailaster }}</p>
        </div>
      </div>

      <b-button
        class="dp-btn text-white"
        variant="gray-800"
        squared
        @click="$bvModal.hide('AlreadySignupModal')"
      >
        <span>로그인하기</span>
      </b-button>
      <b-button
        v-if="false"
        class="dp-btn text-white"
        variant="gray-800"
        squared
      >
        <span>닫기</span>
      </b-button>

      <p class="text-gray-700 dp-p-sm dp-mt-20 dp-mb-30">
        회원정보가 변경된 경우,<br />
        로그인 후 회원정보 수정 페이지에서 수정해주세요.<br />
        기존 가입 정보가 기억나지 않으시면<br />
        고객센터로 연락해주세요.
      </p>

      <hr class="dp-hr justify-margin" />

      <div class="dp-panel customer-center">
        <p class="customer-center__title">고객센터 안내</p>
        <p class="customer-center__number">1666-3642</p>
        <div class="customer-center__time">
          <p class="center-title">운영시간</p>
          <p class="center-time">월~금 10:00 ~ 16:00</p>
        </div>
        <div class="customer-center__time">
          <p class="center-title">점심시간</p>
          <p class="center-time">12:00 ~ 13:00  ※휴뮤 - 토,일 및 공휴일</p>
        </div>
      </div>
    </div>
  </b-modal>
</template>

<script>
export default {
  props: {
    param: { type: Object },
  },
  data() {
    return {
      userType: "",
      textData: "",
      // snsTitle:""
    };
  },
  mounted() {
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

<style>
</style>