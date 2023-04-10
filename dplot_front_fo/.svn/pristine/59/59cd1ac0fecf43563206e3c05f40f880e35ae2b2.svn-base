<template>
  <!-- Page Modal -->
  <!-- TODO: YIY 네이버 로그인 변경 필요(PC버전) -->
  <b-modal
    id="naver-signup"
    modal-class="dp-modal page-layer naver-signup__success"
    scrollable
    centered
    :hide-footer="true"
  >
    <!-- Page Modal Header -->
    <template #modal-header="{ cancel }">
      <h5 class="modal-title">회원가입</h5>
      <i class="modal-close" @click="cancel()">
        <img
          src="@assets.mobile/img/icon/icon-close-black-20px.svg"
          alt="닫기"
        />
      </i>
    </template>

    <!-- Page Modal Body -->
    <div class="page-layer__body">
      <div class="dp-signup-modal">
        <form>
          <!--   이용약관 동의 부분   -->
          <div class="df-signup__agree-service">
            <p class="agree-service__description pt-0">
              DA LIFE 서비스 약관에 동의해주세요.
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
              <!-- TODO : 7/14 평생회원 숨김처리 -->
              <!-- <li>
                <base-checkbox
                  label="[선택]평생회원으로 가입"
                  v-model="islife"
                  id="agreeChk9"
                  name="Checkbox"
                />
                <i @click="showModal('LifetimeMemberModal')">
                  <span>혜택보기</span>
                  <img
                    class="more-icon"
                    src="@/assets/common/icon/icon-more-black-16px.svg"
                    alt="더보기 아이콘"
                  />
                </i>
              </li> -->
            </ul>
          </div>

          <div class="dp-signup__btn-box">
            <b-button
              class="dp-btn text-white"
              variant="gray-800"
              squared
              @click="signUp()"
            >
              <span>회원가입하기</span>
            </b-button>
          </div>
        </form>
      </div>
      <component
        :is="modalInfo.comp"
        :id="modalInfo.id"
        :param="modalInfo.param"
        :fnConfirm="modalInfo.fnConfirm"
        :fnCancel="modalInfo.fnCancel"
      />
    </div>
  </b-modal>
  <!-- // Page Modal -->
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
      modalInfo: {
        comp: null,
        id: null,
        param: null,
        fnConfirm: () => {},
        fnCancel: () => {},
      },
    };
  },
  props: {
    param: { type: Object },
  },
  mounted() {
    this.signupData = this.param.snsinfo;
    this.apiTerm();
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
        this.modalInfo.comp = Terms;
      } else {
        this.modalInfo.comp = LifetimeMember;
      }
      this.modalInfo.id = modalId;

      if (!this.$util.isNull(param)) {
        this.modalInfo.param = param;
      } else {
        this.modalInfo.param = {};
      }

      this.$nextTick(function () {
        this.$bvModal.show(modalId);
      });
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
      //평생회원
      // if (this.islife && (!isEmailTerm || !isSnsTerm)) {
      //   this.$eventBus.$emit(
      //     "alert",
      //     "알림",
      //     "평생회원일경우 마켓팅 수신동의는 필수입니다."
      //   );
      //   return;
      // }
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
              this.$eventBus.$emit("alert","회원가입","회원가입이 완료되었습니다.",
                () => {
                  this.$eventBus.$emit("showModal",SignUpComplete,"SignupSuccessModal",{
                      name: result.data.name, 
                      mobile: result.data.mobile,
                      userno: result.data.userno,
                      isSns: 'T',
                    }
                  );
                  this.$front.otherLogin(this, result.data.userid, "sns", result.data.snsmemberno, result.data.snstype);
                }
              );
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
};
</script>

<style>
</style>