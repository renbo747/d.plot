<template>
  <b-modal
    id="LifetimeMemberModal"
    modal-class="dp-modal alarm lifetime-member"
    centered
  >
    <template #modal-header>
      <h5 class="modal-title">평생회원 혜택 안내</h5>
    </template>

    <div class="lifetime-box">
      <p class="lifetime-box__title">[평생 회원이란?]</p>
      <ul class="ul-dot">
        <li>
          1년 이상 방문하지 않아도 소중한 개인 정보와 혜택을 탈퇴 시까지 보호
          해드립니다.
        </li>
        <li>동의하지 않은 경우, 개인정보 유효기간은 1년으로 설정됩니다.</li>
      </ul>
    </div>

    <div class="lifetime-box">
      <p class="lifetime-box__title">[평생회원 가입혜택]</p>
      <p class="lifetime-box__text" v-for="(list, index) in lifecouponList" :key="index">
        평생회원으로 가입 시, 즉시 사용가능한 {{list.ispercent=='T'?list.dispercent + '%':$util.maskComma(list.disprice) +'원'}} 할인 {{list.cpnname}}을 바로 드립니다.
      </p>
    </div>

    <template #modal-footer="{ ok }">
      <div class="btn-group">
        <b-button
          class="lifetime-member__confirm-btn"
          variant="gray-800 btn-h38"
          @click="ok()"
          >확인</b-button
        >
      </div>
    </template>
  </b-modal>
</template>

<script>
export default {
  data(){
    return {
      lifecouponList :[]
    }
  },
  mounted(){
    this.getLifeCoupon()
  },
  methods : {
    getLifeCoupon(){
      this.$http.post('/coupon/lifemember', {}).then(result => {
         if (result.statusCode == 200) {
            this.lifecouponList = result.data.lifecoupon;
         }
      });
    }
  }
};
</script>

<style>
</style>