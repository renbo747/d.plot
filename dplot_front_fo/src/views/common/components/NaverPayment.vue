<template>
  <div>
  </div>
</template>
<script>
export default {
  name: "NaverPayment",
  mounted() {
    if(!this.$util.isBlank(this.$route.query.payinfo)) {
      const naverParam = JSON.parse(this.$route.query.payinfo);
      
      if(!Naver.isInitialized) {
        Naver.Pay.create({
            "mode" : process.env.VUE_APP_NAVER_PAY_MODE,
            "clientId" : process.env.VUE_APP_NAVER_PAY_CLIENT_KEY,
        })
      }
      Naver.Pay.open(naverParam);
    } else {
      if(this.$util.isBlank(temp)) {
        this.$eventBus.$emit('alert', '알림', '비정상적인 접근입니다.', ()=>{
          this.$router.replace('/shop');
        });
        return;
      }
    }
  },
}
</script>

<style scoped>
</style>