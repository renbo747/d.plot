<template>
  <b-modal
    id="id-find-fail"
    modal-class="dp-modal page-layer id-find__fail"
    scrollable
    :hide-footer="true"
  >
    <!-- Id-find Success01 Modal Header -->
    <template #modal-header="{ cancel }">
      <h5 class="modal-title">회원정보 확인결과</h5>
      <i class="modal-close" @click="cancel()">
        <img
          src="@assets.mobile/img/icon/icon-close-black-20px.svg"
          alt="닫기"
        />
      </i>
    </template>

    <!-- Id-find Success01 Modal Body -->
    <div class="page-layer__body">
      <div class="dp-panel pt-0">
        <p class="id-find__description">
          입력하신 정보와 일치하는 회원 <br />
          정보를 찾지 못했습니다.
        </p>
        <div class="id-find__description2">
          <p>
            입력하신 정보로 확인되지 않을 경우, 다른방법으로 <br />
            시도하시거나 고객센터로 연락해 주시기 바랍니다.
          </p>
          <p>
            카카오/네이버로 가입하신 회원은 카카오/네이버 <br />
            간편로그인을 사용해주세요.
          </p>
        </div>
      </div>
      <hr class="dp-hr justify-margin" />
      <div class="dp-panel customer-center">
        <p class="customer-center__title">고객센터 안내</p>
        <p class="customer-center__number">1666-3642</p>
        <div class="customer-center__time">
          <p class="center-title">운영시간</p>
          <p class="center-time">월~금 10:00 ~ 16:00</p>
        </div>
        <div class="customer-center__time">
          <p class="center-title">점심시간</p>
          <p class="center-time">12:00 ~ 13:00  ※휴뮤 - 토,일 및 공휴일</p>
        </div>
      </div>
    </div>
    <!-- Todo: (개발) 0420 디자인 변경 (버튼추가) -->
    <div class="btn-group">
      <b-button
        class="dp-btn"
        variant="outline-gray-400"
        squared
        @click="$bvModal.hide('id-find-fail')"
      >
        <span>닫기</span>
      </b-button>
      <b-button
        class="dp-btn text-white"
        variant="gray-800"
        squared
        @click="goSignup()"
      >
        <span>회원가입</span>
      </b-button>
    </div>
    <!-- // (개발) 0420 디자인 변경 (버튼추가) -->
  </b-modal>
</template>
<script>
import KmcCert from '@views.mobile/member/KmcCert.js'
export default {
  mixins : [KmcCert],
  methods:{
        goSignup(){
        this.signup();
      }
  }
};
</script>