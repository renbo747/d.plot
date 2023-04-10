<template>
  <div>
    <div class="visual">
      <h1>입 / 점 / 신 / 청</h1>
      <p>성공적인 비즈니스 파트너! D.PLOT과 함께하세요!</p>
    </div>
    <!-- 컨텐츠 영역 -->
    <div class="join-content">
      <div class="flow clearfix">
        <ul>
          <li>
            <span>Step 1.</span>
            <p>입점절차 및 사업자 인증</p>
          </li>
          <li class="on">
            <span>Step 2.</span>
            <p>약관동의</p>
          </li>
          <li>
            <span>Step 3.</span>
            <p>정보입력</p>
          </li>
          <li>
            <span>Step 4.</span>
            <p>신청완료</p>
          </li>
        </ul>
      </div>
      <div v-for="(row, index) in list" :key="index">
        <h2 class="title">{{ row.termsname }}(필수)</h2>
        <div class="terms-area" v-html="row.content"></div>
      </div>
      <div class="btn-group">
        <button type="button" class="btn big blue" @click="goNext">전체 동의 후 가입</button>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: 'PartnershipAgree',
  data() {
    return {
      list : []
    }
  },
  beforeRouteEnter: (to, from, next) => {
    (from.name === 'common.partnership.auth' && to.query.isAuth) ? next() : next('/common/partnership/auth');
  },
  mounted() {
    this.$http.post('/partnership/terms', {}).then(result =>{
        this.list = result.data.list;
    }).catch(error =>{
      this.$util.debug(error);
    })
  },
  methods: {
    goNext: function(){
      this.$router.push({name: 'common.partnership.join', query: this.$route.query});
    }
  }
}
</script>

<style scoped src="@assets.admin/css/partnership.css"></style>
