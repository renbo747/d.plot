<template>
  <main class="password member-top-padding">
    <div class="container container-400">
      <div>
        <h1 class="signup__complete-title font-weight-700 text-black mb-0">
          비밀번호 변경안내
        </h1>
      </div>
      <div class="password-wrap">
        <div class="password-change-description">
          <p
            v-if="!isTempPassword"
            class="dp-title02 text-black mb-0 font-weight-400"
          >
            회원님의 소중한 정보를 안전하게 보호하고 개인정보 도용으로 인한
            피해를 막기 위하여 비밀번호 변경을 안내드립니다.<br />
            <br />
            최근 90일 동안 비밀번호를 변경하지 않으셨습니다. 비밀번호를
            변경해주세요.
          </p>
          <p v-else class="dp-title02 text-black mb-0">
            임시 비밀번호로 로그인하셨습니다.<br />
            비밀번호를 변경해주세요.
          </p>
        </div>

        <div>
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
            <div class="form-group mb-0">
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
                class="dp-btn text-white btn-h48 password-change-btn01"
                variant="gray-800"
                @click="changeNewPw()"
                squared
              >
                <span>비밀번호 변경하기</span>
              </b-button>

              <b-button
              v-if="!isTempPassword"
                class="dp-btn not-hover btn-h48"
                variant="outline-gray-800 type02"
                @click="renewPwChangeDate()"
                squared
              >
                <span>90일 뒤 변경</span>
              </b-button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </main>
</template>
<script>
export default {
  data() {
    return {
      isTempPassword: false,
      param: {},
      newpw: "", // 새로운 비밀번호
      confirmpw: "", // 새로운 비밀번호 확인 (새로운 비밀번호랑 일치여부)
    };
  },
  mounted() {
    if (this.$util.isNull(this.$route.query.isTempPassword)  ||  typeof(this.$route.query.isTempPassword) == 'undefined') {
      this.$eventBus.$emit('alert', '알림', '잘못된 접근입니다.', () => {
        this.$router.push({ name: 'shop' });
      });
    }else {
      this.param.userno = this.$route.query.info;
      this.isTempPassword = this.$route.query.isTempPassword== 'true'||this.$route.query.isTempPassword== true?true:false;
    }
  },
  methods: {
    changeNewPw() {
      this.$front.changePw(this, () => {
        this.$front.redirectPage(this);
      });
    },
    // 90일 후 다시 알리기
    renewPwChangeDate() {
      const param = {
        userno: this.param.userno,
      };
      this.$http.post("/member/pw/changeDateRenew", param).then((result) => {
        if (result.statusCode == 200) {
          this.$front.redirectPage(this);
        }
      });
    },
  },
};
</script>
