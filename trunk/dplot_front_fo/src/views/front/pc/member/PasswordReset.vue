<template>
  <main class="password member-top-padding">
    <div class="container container-400">
      <div>
        <h1 class="signup__complete-title font-weight-700 text-black mb-0">
          비밀번호 재설정
        </h1>
      </div>
      <div class="password-wrap">
        <div class="dp-panel pt-0">
          <p
            class="
              password-reset__description
              dp-title02
              text-black
              font-weight-400
              mb-0
            "
          >
            비밀번호가 기억나지 않으시면 다음과 같은 방법으로 비밀번호를
            재설정할 수 있습니다.
          </p>
          <p
            class="
              password-reset__title
              dp-title02
              text-black
              font-weight-700
              mb-0
            "
          >
            본인인증으로 찾기
          </p>
          <b-button
            class="dp-btn text-white btn-h48"
            variant="gray-800"
            squared
            @click="kmcCertReq()"
          >
            <span>본인인증하기</span>
          </b-button>
        </div>
        <hr class="dp-hr" />
        <div class="dp-panel pb-0">
          <p
            class="
              password-find__title
              dp-title02
              text-black
              font-weight-700
              mb-0
            "
          >
            회원 정보 확인
          </p>
          <div class="password-reset-form">
            <form>
              <div class="form-group">
                <base-input
                  label="아이디"
                  placeholder="아이디를 입력해주세요"
                  :is-label="true"
                  v-model="userid"
                  :invalid="false"
                  :disabled="isSended"
                />
              </div>
              <div class="form-group">
                <base-input
                  label="이름"
                  placeholder="이름을 입력해주세요"
                  :is-label="true"
                  v-model="name"
                  :invalid="false"
                  :disabled="isSended"
                />
              </div>
              <div class="form-group">
                <div class="password-find__email-phone">
                  <p class="password-find__label">이메일 또는 휴대폰번호</p>
                  <div class="email-phone-find base-select-email-box">
                    <base-select
                      class="base-select base-select-email btn-h48"
                      placeholder="선택해주세요."
                      :isPlaceholder="false"
                      v-model="selectType"
                      :options="selectOptions"
                      @input="changeType"
                      :disabled="isSended"
                    />
                    <base-input
                      class="base-input base-select-email-data"
                      v-model="emailhp"
                      :placeholder="
                        selectType == 'MOBILE'
                          ? '- 없이 입력해주세요.'
                          : '이메일을 입력해주세요.'
                      "
                      :disabled="isSended"
                    >
                    </base-input>
                  </div>
                </div>
                <div class="password-reset-btn">
                  <b-button
                    class="dp-btn auth-btn btn-h48"
                    variant="outline-gray-400"
                    squared
                    @click="sendAuthNum()"
                  >
                    <span class="dp-btn__gray-800 text-gray-800"
                      >인증번호 보내기</span
                    >
                  </b-button>
                </div>
              </div>
              <div v-if="isSended">
                <p
                  class="
                    password-find__label
                    password-reset__p
                    dp-p-sm
                    text-black
                    mb-0
                  "
                >
                  회원정보에 등록된 이메일 / 휴대폰번호로 발송된 인증번호를<br />
                  입력해주세요.
                </p>
                <base-input
                  class="auth-number reset-auth"
                  :type="'number'"
                  placeholder="인증번호 입력"
                  v-model="authnum"
                  :data-timer="hhmm"
                />
                <b-button
                  class="dp-btn auth-number-button btn-h48"
                  variant="outline-gray-400"
                  squared
                  @click="confirmAuthNum()"
                >
                  <span class="dp-btn__gray-800 text-gray-800">확인</span>
                </b-button>
                <!-- <div class="password-btn">
                  <b-button
                    class="dp-btn text-white btn-h48 mb-3"
                    variant="gray-800"
                    squared
                    @click="changePasswordNew"
                  >
                    <span>확인</span>
                  </b-button>
                </div> -->
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </main>
</template>

<script src="@views.mobile/member/popup/PasswordReset.js">
</script>
