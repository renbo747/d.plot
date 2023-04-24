<template>
  <div>
    <h3>현재 FO 기획서에 있는 결제 목록</h3>
    <hr/>
<!--    <button @click="openPay('')">1111</button><hr/>-->
    <button @click="openPay('CARD')">카드결제(가능)</button><br/><br/>
<!--    <button @click="openPay('TOSSPAY')">TOSS</button><hr/>-->
    <button @click="openPay('VIRTUAL_ACCOUNT')">무통장입금(가능)</button><br/><br/>
    <button @click="openPay('TRANSFER')">계좌이체(가능)</button><br/><br/>

    <button @click="openPay('VIRTUAL_ACCOUNT')">NaverPay(불가)</button><br/><br/>
    <button @click="openPay('VIRTUAL_ACCOUNT')">KakaoPay(불가)</button><br/><br/>
    <button @click="openPay('TOSSPAY')">TOSS(가능)</button><br/><br/>
    <button @click="openPay('CARD', '페이코')">PAYCO(불가)</button><br/><br/>
    <button @click="openPay('CARD', '삼성페이')">SamsungPay(불가)</button><br/><br/>

    <button @click="openPay('MOBILE_PHONE')">휴대폰(가능)</button><br/><br/>
<!--    <button @click="openPay('CULTURE_GIFT_CERTIFICATE')">문화상품권</button><hr/>-->
<!--    <button @click="openPay('BOOK_GIFT_CERTIFICATE')">도서문화상품권</button><hr/>-->
<!--    <button @click="openPay('GAME_GIFT_CERTIFICATE')">게임문화상품권</button>-->
  </div>
</template>


<script>

export default {
  name: "CommonPayment",
  data() {
    return {
      clientKey : process.env.VUE_APP_TOSS_CLIENT_KEY
    }
  },
  mounted() {

    // let externalScript = document.createElement('script')
    // externalScript.setAttribute('src', 'https://js.tosspayments.com/v1')
    // document.head.appendChild(externalScript);

  },
  methods: {
    openPay(type, param) {
      //var params = Object.assign({}, this.payParam, param);
      // if(typeof direct !== 'undefined'){

      //   this.payParam.flowMode = 'DEFAULT';
      //   this.payParam.easyPay = direct;
      //   // this.payParam.flowMode = 'DIRECT';
      //   // this.payParam.easyPay = direct;
      //   // this.payParam.cardCompany = direct;
      // }

      let tossPayments = TossPayments(this.clientKey);
      tossPayments.requestPayment(type, param);
    },

  }
}
</script>

<style scoped>

</style>