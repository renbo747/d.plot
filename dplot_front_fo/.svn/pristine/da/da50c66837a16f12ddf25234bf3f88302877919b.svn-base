<template>
  <div>
  </div>
</template>
<script>
export default {
  name: "TossPayment",
  mounted() {
    const payType = this.$route.query.payType;
    const tossInfo = JSON.parse(this.$route.query.tossInfo);
    
    const tossPayments = TossPayments(process.env.VUE_APP_TOSS_CLIENT_KEY);
    tossPayments.requestPayment(payType, tossInfo).catch(error => {
      const msg = error + '';
      if(msg.indexOf('중복된 주문번호') > -1) {
        tossInfo.orderId = tossInfo.orderId + "_" + Date.now();
        tossPayments.requestPayment(payType, tossInfo).catch(error => {
            this.$eventBus.$emit('alert', '알림', '추가결제 신청중 에러가 발생하였습니다. 다시시도해주세요.', ()=>{
              window.close();
            });
        });
      } else {
        this.$eventBus.$emit('alert', '알림', error, () => {
          window.close();
        });
      }
    });

  },
}
</script>

<style scoped>
</style>