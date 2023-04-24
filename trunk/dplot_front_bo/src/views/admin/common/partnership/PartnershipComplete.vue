<template>
  <div>
    <div class="visual">
      <h1>입 / 점 / 신 / 청</h1>
      <p>성공적인 비즈니스 파트너! D.PLOT과 함께하세요!</p>
    </div>
    <div class="join-content">
      <div class="flow clearfix">
        <ul>
          <li>
            <span>Step 1.</span>
            <p>입점절차 및 사업자 인증</p>
          </li>
          <li>
            <span>Step 2.</span>
            <p>약관동의</p>
          </li>
          <li>
            <span>Step 3.</span>
            <p>정보입력</p>
          </li>
          <li class="on">
            <span>Step 4.</span>
            <p>신청완료</p>
          </li>
        </ul>
      </div>
      <div class="step4">
        <img src="@assets.admin/img/partnership-img.png" alt="">
        <h3>신청완료</h3>
        <p>입점신청서가 정상적으로 접수되었습니다.<br>신청해주셔서 감사드리며, 확인 후 결과를 통보해 드리겠습니다.</p>
        <span class="small-txt">IT개발팀 : 02-2008-5724</span>
      </div>
      <div class="btn-group">
        <button type="button" class="btn big blue" @click="goPartnershipLogin">확인</button>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: 'PartnershipComplete',
  methods: {
    goPartnershipLogin: function() {
      this.$router.push({name : 'partners.login'});
    }
  }
}
</script>

<style scoped src="@assets.admin/css/partnership.css"></style>