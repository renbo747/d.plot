<template>
  <main class="dp-signup member-top-padding">
    <div class="container container-400">
      <div>
        <h1 class="signup__title font-weight-700 text-black">회원가입</h1>
      </div>
      <!--   이용약관 동의 부분   -->
      <div class="dp-signup__agree-service pt-0">
        <p
          class="agree-service__description font-weight-500 dp-p mb-0 text-black"
        >
          D.PLOT 서비스 약관에 동의해주세요.
        </p>
        <ul class="agree-service__box list-style-none">
          <li>
            <base-checkbox
              label="모두 동의합니다."
              v-model="dfAgreeService[0].id"
              :id="dfAgreeService[0].id"
              name="sampleCheckbox"
              :is-checked="dfAgreeService[0].checked"
              @change="dfAgreeService[0].checked = !dfAgreeService[0].checked"
            />
            <i @click="handleTermsModal">
              <img
                class="more-icon"
                src="@/assets/common/icon/icon-arrow-bottom-black-16px.svg"
                alt="더보기 아이콘"
              />
            </i>
          </li>
          <li>
            <base-checkbox
              label="[필수] 디플롯 이용약관 동의"
              v-model="dfAgreeService[1].id"
              :id="dfAgreeService[1].id"
              name="sampleCheckbox"
              :is-checked="dfAgreeService[1].checked"
              @change="dfAgreeService[1].checked = !dfAgreeService[1].checked"
            />
            <i @click="handleTermsModal">
              <img
                class="more-icon"
                src="@/assets/common/icon/icon-more-black-16px.svg"
                alt="더보기 아이콘"
              />
            </i>
          </li>
          <li>
            <base-checkbox
              label="[필수] 개인정보 수집 및 이용 동의"
              v-model="dfAgreeService[2].id"
              :id="dfAgreeService[2].id"
              name="sampleCheckbox"
              :is-checked="dfAgreeService[2].checked"
              @change="dfAgreeService[2].checked = !dfAgreeService[2].checked"
            />
            <i @click="handleTermsModal">
              <img
                class="more-icon"
                src="@/assets/common/icon/icon-more-black-16px.svg"
                alt="더보기 아이콘"
              />
            </i>
          </li>
          <li>
            <base-checkbox
              label="[필수] 전자금융거래 이용약관 동의	"
              v-model="dfAgreeService[3].id"
              :id="dfAgreeService[3].id"
              name="sampleCheckbox"
              :is-checked="dfAgreeService[3].checked"
              @change="dfAgreeService[3].checked = !dfAgreeService[3].checked"
            />
            <i @click="handleTermsModal">
              <img
                class="more-icon"
                src="@/assets/common/icon/icon-more-black-16px.svg"
                alt="더보기 아이콘"
              />
            </i>
          </li>
          <li>
            <base-checkbox
              label="[필수] 개인정보 제공 동의"
              v-model="dfAgreeService[4].id"
              :id="dfAgreeService[4].id"
              name="sampleCheckbox"
              :is-checked="dfAgreeService[4].checked"
              @change="dfAgreeService[4].checked = !dfAgreeService[4].checked"
            />
            <i @click="handleTermsModal">
              <img
                class="more-icon"
                src="@/assets/common/icon/icon-more-black-16px.svg"
                alt="더보기 아이콘"
              />
            </i>
          </li>
          <li>
            <base-checkbox
              label="[선택] 마케팅정보 이메일 수신 동의"
              v-model="dfAgreeService[5].id"
              :id="dfAgreeService[5].id"
              name="sampleCheckbox"
              :is-checked="dfAgreeService[5].checked"
              @change="dfAgreeService[5].checked = !dfAgreeService[5].checked"
            />
          </li>
          <li>
            <base-checkbox
              label="[선택] 마케팅정보 SMS 수신 동의"
              v-model="dfAgreeService[6].id"
              :id="dfAgreeService[6].id"
              name="sampleCheckbox"
              :is-checked="dfAgreeService[6].checked"
              @change="dfAgreeService[6].checked = !dfAgreeService[6].checked"
            />
          </li>
        </ul>
      </div>

      <!-- 이용약관 모달 -->
      <b-modal
        id="termsModal"
        modal-class="dp-modal page-layer pop-over terms-modal terms-modal-pc"
        centered
        :hide-footer="true"
      >
        <!-- Page Modal Header -->
        <template #modal-header="{ cancel }">
          <h5 class="modal-title text-black font-weight-500">이용약관</h5>
          <i class="modal-close" @click="cancel()">
            <img
              src="~@/assets/common/icon/icon-close-black-26px.svg"
              alt="닫기"
            />
          </i>
        </template>

        <!-- Page Modal Body -->
        <div class="page-layer__body">
          <terms-contents />
        </div>
      </b-modal>
      <!-- // 이용약관 모달 -->

      <div class="dp-signup__btn-box">
        <b-button
          v-if="signUpDefault.isSignUp"
          class="dp-btn text-white signup-btn"
          variant="gray-800"
          squared
          @click="handleSignUp"
        >
          <span>회원가입하기</span>
        </b-button>
        <b-button
          v-else
          class="dp-btn text-white signup-btn"
          variant="gray-800"
          squared
          @click="handleSignUpExecutive"
        >
          <span>임직원 회원가입하기</span>
        </b-button>
      </div>
    </div>
  </main>
</template>

<script>
export default {
  data() {
    return {
      //회원가입&임직원 회원가입 버튼
      signUpDefault: {
        isSignUp: true,
      },
      // 이용약관 부분
      dfAgreeService: [
        {
          id: "agreeChk01",
          checked: false,
        },
        {
          id: "agreeChk02",
          checked: false,
        },
        {
          id: "agreeChk03",
          checked: false,
        },
        {
          id: "agreeChk04",
          checked: false,
        },
        {
          id: "agreeChk05",
          checked: false,
        },
        {
          id: "agreeChk06",
          checked: false,
        },
        {
          id: "agreeChk07",
          checked: false,
        },
        {
          id: "agreeChk08",
          checked: false,
        },
      ],
    };
  },
  methods: {
    handleSignUp() {
      this.$router.push("/member/sign-up");
    },
    handleSignUpExecutive() {
      this.$router.push("/member/sign-up-executive");
    },
    handleTermsModal() {
      this.$root.$emit("bv::show::modal", "termsModal");
    },
  },
};
</script>
