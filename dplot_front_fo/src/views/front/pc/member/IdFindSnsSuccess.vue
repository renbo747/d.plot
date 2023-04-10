<template>
  <main class="id-find member-top-padding">
    <div class="container container-400">
      <div>
        <h1 class="signup__complete-title font-weight-700 text-black mb-0">
          회원정보 확인결과
        </h1>
      </div>
      <div>
        <p
          class="id-find__description dp-title02 text-black font-weight-400 mb-0"
        >
          고객님은 {{signupdt}}에 {{signch}}로 가입하셨습니다. <br />
          {{signch}} 계정으로 로그인해주세요.
        </p>
        <div class="id-find-success-btn">
          <b-button
            class="dp-btn pl-0 pr-0 text-white btn-h48"
            variant="gray-800"
            squared
            @click="login()"
          >
            <span>로그인</span>
          </b-button>
        </div>
      </div>
    </div>
  </main>
</template>
<script>
export default {
    data() {
        return {
            signupdt : null,
            signch : ''
        }
    },
    mounted() {
        const param = this.$route.params.memberInfo;

        const temp = this.$util.convertStringToDate(param.regdt);
        this.signupdt = this.$util.getDateFormat(temp, 'yyyy년 MM월 dd일');
        switch (param.joinchtype) {
            case 'UCT002':
                this.signch = '네이버';
                break;
            case 'UCT003':
                this.signch = '카카오';
                break;
            default:
                this.signch = '애플';
                break;
        }
    },
    methods : {
        login() {
            this.$router.replace("/member/login");
        },
    }
}
</script>
