<template>
  <main class="id-find member-top-padding">
    <div class="container container-400">
      <div>
        <h1 class="signup__complete-title font-weight-700 text-black mb-0">
          아이디 찾기
        </h1>
      </div>
      <div class="dp-panel pt-0">
        <p
          class="id-find__description-pc dp-title02 font-weight-400 text-black mb-0"
        >
          아이디가 기억나지 않으시면 다음과 같은 방법으로<br />
          아이디를 찾아주세요.
        </p>
        <p class="id-find__title-pc dp-title02 font-weight-700 text-black mb-0">
          본인인증으로 찾기
        </p>
        <b-button
          class="dp-btn text-white btn-h48" 
          variant="gray-800" 
          @click="kmcCertReq()"
          squared>
          <span>본인인증하기</span>
        </b-button>
      </div>
      <hr class="dp-hr" />
      <div class="dp-panel pb-0">
        <p class="id-find__title-pc dp-title02 font-weight-700 text-black mb-0">
          회원 정보로 찾기
        </p>
        <form>
          <div class="form-group">
            <base-input
              label="이름"
              placeholder="이름을 입력해주세요"
              :is-label="true"
              v-model="name"
            >
            </base-input>
          </div>
          <div class="id-find__email-phone id-find__email-box">
            <p class="id-find__label-pc">이메일 또는 휴대폰번호</p>
            <div class="email-phone-find">
              <base-select
                class="base-select base-select-id-find btn-h48"
                placeholder="선택해주세요."
                :isPlaceholder="false"
                v-model="selectType"
                :options="selectOptions"
                @input="changeType"
              />
              <base-input
                class="base-input base-input-id-find"
                :type="inputType"
                :placeholder="selectType == 'MOBILE' ? '- 없이 입력해주세요.' : '이메일을 입력해주세요.'"
                v-model="emailhp"
              >
              </base-input>
            </div>
          </div>
          <b-button
            class="dp-btn text-white btn-h48"
            variant="gray-800"
            squared
            @click="findId()"
          >
            <span>회원 정보 찾기</span>
          </b-button>
        </form>
      </div>
    </div>
  </main>
</template>
<script src="@views.mobile/member/popup/IdFind.js">

</script>
