<template>
  <div id="error-page">
    <img src="@assets.admin/img/logo-dplot.svg" alt="D.PLOT">
    <h2>서비스 이용에 불편을 드려 죄송합니다.</h2>
    <p>
      입력하신 페이지의 주소가 잘못 입력되었거나, 변경 혹은 삭제되어 요청하신 페이지를 찾을 수 없습니다.<br>
      입력하신 주소가 정확한지 다시 한번 확인해주세요.<br>
      서버에서 정보를 가져오는 도중 오류가 발생하였습니다.<br>
      잠시 후 다시 시도해주세요.
    </p>
    <div class="btn-wrap">
      <button type="button" class="prev" @click="backPage">이전 페이지</button>
      <button type="button" class="main" @click="main">메인 페이지</button>
    </div>
  </div>
</template>

<script>
export default {
  name: "NotFoundPage",
  methods :{
    backPage(){
      this.$router.go(-1);
    },
    main(){
      this.$router.push("/main");
    }
  }
}
</script>

<style scoped>

</style>