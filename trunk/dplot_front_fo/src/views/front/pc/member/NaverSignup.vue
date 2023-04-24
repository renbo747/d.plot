<template>
  <div class="naver-login member-top-padding">
    <div class="container container-400">
      <h1 class="signup__title">회원가입</h1>
      <div class="dp-signup-modal">
        <form>
          <!--   이용약관 동의 부분   -->
          <div class="dp-signup__agree-service">
            <p class="agree-service__description pt-0">
              D.PLOT 서비스 약관에 동의해주세요.
            </p>
            <ul class="agree-service__box list-style-none">
              <li>
                <base-checkbox
                  label="모두 동의합니다."
                  v-model="isAllAgree"
                  id="agreeChk0"
                  name="Checkbox"
                  @change="allAgreeChange"
                />
              </li>
              <li v-for="(list, index) in termList" :key="index">
                <base-checkbox
                  :label="list.label"
                  v-model="list.checked"
                  :id="list.id"
                  name="Checkbox"
                  @change="agreeChange"
                />
                <i
                  @click="
                    showModal('termsModal', { termstype: list.termstype })
                  "
                >
                  <img
                    class="more-icon"
                    src="@/assets/common/icon/icon-more-black-16px.svg"
                    alt="더보기 아이콘"
                  />
                </i>
              </li>
            </ul>
          </div>

          <div class="dp-signup__btn-box-pc">
            <b-button
              class="dp-btn text-white btn-h48"
              variant="gray-800"
              @click="signUp()"
              squared
            >
              <span>회원가입하기</span>
            </b-button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>
<script>
import Terms from "@views/front/common/components/ui/modal/Terms.vue";
import LifetimeMember from "@views.mobile/member/popup/LifetimeMember.vue";
import SignUpComplete from "@views.mobile/member/popup/SignUpComplete.vue";
export default {
  data() {
    return {
      isAllAgree: false, //전체동의 여부
      islife: false, //평생회원여부
      isEmployee: "F", //임직원 여부
      termList: [], //이용약관 목록
      signupData: null,
    };
  },
  methods: {
    /*********************
     * 이용약관 목록 조회
     *********************/
    apiTerm() {
      this.$http
        .post("/member/signup/term", { isloading: false })
        .then((result) => {
          if (result.statusCode == 200) {
            var list = result.data.list;
            for (var i = 0; i < list.length; i++) {
              list[i].checked = false;
              list[i].id = "agreeChk" + (i + 1);
              list[i].label = list[i].prefix + list[i].termstypename;
            }
            this.termList = list;
            this.$util.debug(JSON.stringify(this.termList));
          }
        });
    },
    /********************
     * 약관 모두 동의처리
     *********************/
    allAgreeChange() {
      for (var i = 0; i < this.termList.length; i++) {
        this.termList[i].checked = this.isAllAgree;
      }
    },
    /********************
     * 이용약관 동의 항목 처리
     *********************/
    agreeChange() {
      var cnt = 0;
      for (var i = 0; i < this.termList.length; i++) {
        if (this.termList[i].checked) {
          cnt++;
        }
      }
      if (cnt == this.termList.length) {
        this.isAllAgree = true;
      } else {
        this.isAllAgree = false;
      }
    },
    /*******************
     * 모달 오픈
     *******************/
    showModal(modalId, param) {
      if (modalId == "termsModal") {
        this.$eventBus.$emit("showModal", Terms, modalId, param);
      } else {
        this.$eventBus.$emit("showModal", LifetimeMember, modalId);
      }
    },
    /*********************
     * 평생회원 처리시 이용약관 전체 동의 처리
     *********************/
    liftChange() {
      if (this.islife) {
        for (var i = 0; i < this.termList.length; i++) {
          if (this.termList[i].isessen == "F") {
            this.termList[i].checked = true;
          }
        }
      }
    },
    /*********************
     * 회원가입 처리
     *********************/
    signUp() {
      var isRequireTerm = true;
      var isEmailTerm = false;
      var isPushTerm = false;
      var isSnsTerm = false;
      for (var i = 0; i < this.termList.length; i++) {
        if (!this.termList[i].checked && this.termList[i].isessen == "T") {
          isRequireTerm = false;
        }
        if (
          this.termList[i].termstype == "TRT005" &&
          this.termList[i].checked
        ) {
          isEmailTerm = true;
        }
        if (
          this.termList[i].termstype == "TRT006" &&
          this.termList[i].checked
        ) {
          isSnsTerm = true;
        }
      }
      if (!isRequireTerm) {
        this.$eventBus.$emit("alert", "알림", "서비스약관에 동의해주세요.");
        return;
      }
      //정보성이메일 수신여부
      if (isEmailTerm) {
        this.signupData.isadmailing = "T";
      } else {
        this.signupData.isadmailing = "F";
      }
      //정보성SMS 수신여부
      if (isSnsTerm) {
        this.signupData.isadsms = "T";
      } else {
        this.signupData.isadsms = "F";
      }
      //정보성PUSH 수신여부
      if (isPushTerm) {
        this.signupData.isadpush = "T";
      } else {
        this.signupData.isadpush = "F";
      }

      this.$eventBus.$emit(
        "confirm",
        "회원가입",
        "회원가입 하시겠습니까?",
        () => {
          // 파라미터 설정
          var param = this.signupData;
          if (this.islife) {
            param.dadamembertype = "DMT002"; //평생회원
          } else {
            param.dadamembertype = "DMT001"; //일반회원
          }

          param.issns = "T";
          param.dadamembertype = "DMT001";
          this.$http.post("/member/signup/act", param).then((result) => {
            if (result.statusCode == 200) {
              this.$util.debug("회원가입처리....");
              this.$util.debug(JSON.stringify(result));
              let param = {
                name: result.data.name,
                mobile: result.data.mobile,
                userno: result.data.userno,
                isSns: "T",
              }
              this.$eventBus.$emit("alert","회원가입","회원가입이 완료되었습니다.",() => {
                //회원가입후 자동 로그인 안시키기로함
                //this.$front.otherLogin(this,result.data.userid,"sns",result.data.snsmemberno, result.data.snstype);
                this.$eventBus.$emit("showModal",SignUpComplete,"SignupSuccessModal",param);
              });
              
            } else {
              this.$eventBus.$emit("alert", "알림", result.message);
              this.$bvModal.hide("naver-signup");
              this.$router.replace("/shop");
            }
          });
        }
      );
    },
  },
  mounted() {
    if (this.$util.isEmpty(this.$route.params)) {
      this.$nextTick(() => {
        this.$eventBus.$emit("alert", "알림", "잘못된 접근입니다.", () => {
          this.$router.replace({ name: "member-login" });
        });
      });
    }
    this.signupData = this.$route.params.snsinfo;
    this.apiTerm();
  },
};
</script>
