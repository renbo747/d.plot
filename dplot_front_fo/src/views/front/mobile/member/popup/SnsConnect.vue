<template>
  <b-modal
    id="sns-connect"
    modal-class="dp-modal page-layer already-signup id-find__fail"
    scrollable
    centered
    :hide-footer="true"
  >
    <!-- Already Signup Modal Header -->
    <template #modal-header="{ cancel }">
      <h5 class="modal-title">회원정보안내</h5>
      <i class="modal-close" @click="cancel()">
        <img
          src="@assets.mobile/img/icon/icon-close-black-20px.svg"
          alt="닫기"
        />
      </i>
    </template>

    <!-- Already Signup Modal Body -->
    <div class="page-layer__body">
      <p class="already-signup__desc">이미 가입된 회원정보가 있습니다.</p>
      <p class="text-gray-700 mb-0 dp-p-sm" v-if="memberList.length > 0 && memberList[0].isuser == 'T'">
        {{ snsName }} 계정과 연결하여 간편하게 로그인 하세요
      </p>
      <div
        class="already-signup__info"
        v-for="(list, index) in memberList"
        :key="index"
      >
        <div class="already-signup__flex">
          <p>아이디</p>
          <p class="already-signup__id">{{ list.useridaster }}</p>
        </div>
        <div class="already-signup__flex">
          <p>이메일</p>
          <p class="already-signup__id">{{ list.emailaster }}</p>
        </div>
      </div>

      <b-button
        v-if="memberList.length > 0 && memberList[0].isuser == 'F'"
        class="dp-btn text-white"
        variant="gray-800"
        squared
        @click="goLogin()"
      >
        <span>로그인하기</span>
      </b-button>
      <b-button
        v-else
        class="dp-btn text-white"
        variant="gray-800"
        squared
        @click="snsConnect()"
      >
        <span>{{ snsName }} 계정 연결하기</span>
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
          <p class="center-time">12:00 ~ 13:00 ※휴뮤 - 토,일 및 공휴일</p>
        </div>
      </div>
    </div>
  </b-modal>
</template>

<script>
export default {
  data() {
    return {
      snsName: "",
      memberInfo: {
        userno: 0,
        email: "",
        mobile: "",
      },
      snsInfo: null,
      memberList: [],
    };
  },
  props: {
    param: { type: Object },
  },
  mounted() {
    this.snsInfo = this.param.snsinfo;
    //this.memberInfo = this.param.member;
    this.memberList = this.param.member;
    //this.snsInfo.userno = this.memberInfo.userno;
    if (this.memberList.length > 0) {
      this.snsInfo.userno = this.memberList[0].userno;
    }

    if (this.snsInfo.snstype == "ULT001") {
      this.snsName = "네이버";
    } else if (this.snsInfo.snstype == "ULT002") {
      this.snsName = "카카오";
    } else {
      this.snsName = "APPLE";
    }
  },
  methods: {
    snsConnect() {
      const params = this.snsInfo;
      this.$http.post("/member/sns/connect", params).then((result) => {
        if (result.statusCode == 200) {
          this.$eventBus.$emit(
            "alert",
            "알림",
            this.snsName + "계정이 연결되었습니다.",
            () => {
              this.$front.otherLogin(
                this,
                result.data.userid,
                "sns",
                result.data.snsmemberno,
                result.data.snstype
              );
              this.$bvModal.hide("sns-connect");
            }
          );
        }
      });
    },
    goLogin() {
      if (this.$route.path !== "/member/login") {
        this.$router.push("/member/login");
      }
      this.$bvModal.hide("sns-connect");
    },
  },
};
</script>

<style>
</style>