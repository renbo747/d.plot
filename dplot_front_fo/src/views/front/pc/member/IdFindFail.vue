<template>
  <main class="id-find member-top-padding">
    <div class="container container-400">
      <div>
        <h1 class="signup__complete-title font-weight-700 text-black mb-0">
          회원정보 확인결과
        </h1>
      </div>
      <div class="id-find-fail-wrap">
        <div class="dp-panel id-find-fail-list pt-0">
          <p
            class="id-find-fail__description dp-p text-black font-weight-400 mb-0"
          >
            입력하신 정보와 일치하는 회원 정보를 찾지 못했습니다.
          </p>
          <div class="id-find-fail__description2 dp-p font-weight-400 mb-0">
            <p>
              입력하신 정보로 확인되지 않을 경우, 다른방법으로 시도하시거나
              고객센터로 연락해 주시기 바랍니다.
            </p>
            <p class="mb-0">
               카카오/네이버로 가입하신 회원은 카카오/네이버 간편로그인을
              사용해주세요.
            </p>
          </div>
        </div>
        <hr class="dp-hr" />
        <div class="dp-panel customer-center-pc pb-0">
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
                월~금 10:00 ~ 16:00
              </p>
            </div>
            <div class="customer-center__time02 d-flex">
              <p class="center-title-pc dp-p mb-0">점심시간</p>
              <p class="center-time-pc dp-p text-black mb-0">
                12:00 ~ 13:00 (휴무-토,일 및 공휴일)
              </p>
            </div>
          </div>
        </div>
        <div class="id-find__success-buttons d-flex">
          <b-button
            class="id-find__success-btn01 dp-btn pl-0 pr-0 btn-h48"
            variant="outline-gray-800 not-hover"
            squared
            @click="$router.replace('/shop')"
          >
            <span class="dp-p text-gray-800 font-weight-400">메인으로</span>
          </b-button>
          <b-button
            class="id-find__success-btn02 dp-btn pl-0 pr-0 text-white btn-h48"
            variant="gray-800"
            squared
            @click="goSignup()"
          >
            <span class="dp-p font-weight-400">회원가입하기</span>
          </b-button>
        </div>
      </div>
    </div>
  </main>
</template>

<script>
import KmcCert from '@views.mobile/member/KmcCert.js'
export default {
  mixins : [KmcCert],
  methods: {
      goSignup(){
        this.$router.push({name:'member-login'});
        this.signup();
      }
  }
}
</script>