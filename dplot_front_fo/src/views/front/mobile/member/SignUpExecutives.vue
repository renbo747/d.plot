<template>
  <div class="df-signup-executive">
    <div>
      <p class="df-signup-executive__description">
        서비스 이용을 위한 <br />
        회원정보를 입력해주세요.
      </p>
    </div>

    <!-- input박스 영역   -->
    <form>
      <div class="form-group">
        <base-input
          label="이름"
          :is-label="true"
          placeholder="홍길동"
          :is-readonly="true"
        />
      </div>
      <div class="form-group">
        <base-input
          label="휴대폰 번호"
          :is-label="true"
          placeholder="010-1234-5678"
          :is-readonly="true"
        />
      </div>
      <div class="form-group">
        <base-input
          label="아이디"
          :is-label="true"
          v-model="dfSignupData.idData"
          @input="dataCheck"
          placeholder="아이디를 입력해주세요."
        />
      </div>
      <div class="form-group">
        <base-input
          label="비밀번호"
          :is-label="true"
          v-model="dfSignupData.passwordData"
          @input="dataCheck"
          placeholder="비밀번호를 입력해주세요."
        />
      </div>
      <div class="form-group">
        <base-input
          label="비밀번호 재입력"
          :is-label="true"
          v-model="dfSignupData.rePasswordData"
          @input="dataCheck"
          placeholder="비밀번호를 다시한번 입력해주세요."
        />
      </div>
      <div class="form-group">
        <div class="signup-executive__email">
          <p class="email__label">이메일</p>
          <div class="email-phone-find">
            <base-input
              class="email-input"
              v-model="dfSignupData.rePasswordData"
              @input="dataCheck"
              placeholder="이메일 입력"
            />
            <div class="at-sign">@</div>
            <base-select
              class="domain-select"
              placeholder="도메인 선택"
              v-model="passwordFindData.selectOptionData"
              :options="passwordFindData.selectOptions"
              @input="dataCheck"
            />
          </div>
        </div>
      </div>
      <b-button class="dp-btn send-btn" variant="outline-gray-400" squared>
        <span class="dp-btn__gray-800">이메일로 인증번호 보내기</span>
      </b-button>
      <p class="email-number__label">
        이메일로 발송된 인증번호를 입력해주세요.
      </p>
      <base-input
        class="number-input"
        :valid="true"
        placeholder="인증번호 입력"
        v-model="passwordFindData.numberData"
        @input="dataCheck"
        data-timer="02:57"
      />
      <!-- todo: 인증번호 완료 시 노출 필요 -->
      <p class="success-text dp-caption text-success">
        인증번호 확인이 완료되었습니다.
      </p>
      <b-button class="dp-btn confirm-btn" variant="outline-gray-400" squared>
        <span class="dp-btn__gray-800">확인</span>
      </b-button>

      <!--   이용약관 동의 부분   -->
      <div class="df-signup__agree-service">
        <p class="agree-service__description">서비스 약관에 동의해주세요.</p>
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
            <i>
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
            <i>
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
            <i>
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
            <i>
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
      <div class="df-signup-executive__btn-box">
        <b-button class="dp-btn text-white" variant="gray-800" squared>
          <span>임직원 회원가입하기</span>
        </b-button>
      </div>
    </form>
  </div>
</template>

<script>
export default {
  beforeCreate() {
    this.$store.commit("onSubHeaderOption", {
      title: "임직원 회원가입",
      searchIcon: false,
      cartIcon: false,
      homeIcon: false,
    });
  },
  data() {
    return {
      // input 부분
      dfSignupData: {
        idData: "",
        passwordData: "",
        rePasswordData: "",
        emailData: "",
        recommendationIdData: "",
      },
      passwordFindData: {
        nameData: "",
        idData: "",
        numberData: "",
        selectOptionData: "",
        selectOptions: [
          {
            label: "naver.com",
            value: "네이버",
          },
          {
            label: "naver.com",
            value: "네이버",
          },
        ],
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
    // input 부분
    dataCheck(val) {
      // 자식으로부터 넘어온 데이터 확인용
    },
  },
};
</script>
