<template>
  <main class="sign-up-sns member-top-padding">
    <div class="container container-400">
      <div>
        <h1 class="signup__complete-title font-weight-700 text-black">
          회원정보안내
        </h1>
      </div>
      <p class="already-signup__desc dp-title02 text-black mb-8">
        이미 가입된 회원정보가 있습니다.
      </p>
      <p
        class="text-gray-700 mb-0 dp-p text-gray-700 signup-description-sns"
        v-if="memberList.length > 0 && memberList[0].isuser == 'T'"
      >
        {{ snsName }} 계정과 연결하여 간편하게 로그인 하세요
      </p>
      <div
        class="already-signup__info"
        v-for="(list, index) in memberList"
        :key="index"
      >
        <div class="already-signup__flex d-flex">
          <p class="already-signup__name mb-0 dp-p text-gray-700">아이디</p>
          <p class="already-signup__id mb-0 dp-p text-gray-700">
            {{ list.useridaster }}
          </p>
        </div>
        <div class="already-signup__flex d-flex">
          <p class="already-signup__name mb-0 dp-p text-gray-700">이메일</p>
          <p class="already-signup__phone mb-0 dp-p text-gray-700">
            {{ list.emailaster }}
          </p>
        </div>
      </div>
      <div class="signup-already-btn">
        <b-button
          v-if="memberList.length > 0 && memberList[0].isuser == 'F'"
          class="dp-btn text-white btn-h48"
          variant="gray-800"
          squared
          @click="goLogin()"
        >
          <span>로그인하기</span>
        </b-button>
        <b-button
          v-else
          class="dp-btn text-white btn-h48"
          variant="gray-800"
          squared
          @click="snsConnect()"
        >
          <span>{{ snsName }} 계정 연결하기</span>
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
  mounted() {
    if (this.$util.isEmpty(this.$route.params)) {
      this.$nextTick(() => {
        this.$eventBus.$emit("alert", "알림", "잘못된 접근입니다.", () => {
          this.$router.replace({ name: "member-login" });
        });
      });
    }

    this.snsInfo = this.$route.params.snsinfo; //sns 정보
    this.memberList = this.$route.params.member; //회원 정보 리스트
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
      let params = this.snsInfo;
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
            }
          );
        }
      });
    },
    goLogin() {
      if (this.$route.path !== "/member/login") {
        this.$router.push("/member/login");
      }
    },
  },
};
</script>
