<template>
  <main class="dp-loading">
    <div class="container container-400">
      <div class="dp-loading-wrap">
        <div class="dp-loading__img mb-2">
          <figure class="dp-loading__figure">
            <logo />
          </figure>
        </div>
        <div class="dp-loading__text__wrap dp-panel">
          <p class="dp-loading__text mb-0">
            주문/결제 처리중입니다.
          </p>
        </div>
        <div class="dp-loading__animation">
          <div class="lds-ring">
            <div></div>
            <div></div>
            <div></div>
            <div></div>
          </div>
        </div>
      </div>
    </div>
  </main>
</template>

<script>
import Order from '@views.mobile/order/Order.js'

export default {
  mixins:[Order],
  name: 'PaymentSucc',
  data() {
    return {
      orderId : '',
      amount : 0,
      paymentKey : '',
      orderInfo: null
    }
  },
  mounted() {
    window.addEventListener("load", () => {
      document.getElementById("HappytalkIframe").style.display = "none";
    });
    
    if(this.$route.query.resultCode === 'Success') {
      this.orderId = this.$route.query.orderId;
      this.amount = this.$route.query.amount;
      this.paymentKey = this.$route.query.paymentId;
      
      const payInfo = {
          orderid : this.orderId,
          amount : this.amount,
          paymentkey : this.paymentKey
      }

      if(this.$store.state.platform != 'MAC001') {
        setTimeout(() => {
          window.opener.popupCallback(payInfo)
          window.close()
        }, 500);
      } else {
        this.$nextTick(()=>{
          const temp = window.sessionStorage.getItem('ORDERPAYLIST');
          if(this.$util.isBlank(temp)) {
            this.$eventBus.$emit('alert', '알림', '비정상적인 접근입니다.', ()=>{
              this.$router.replace('/shop');
            });
            return;
          }
        
          const orderInfo = this.$CryptoJS.AES.decrypt(temp, this.orderId).toString(this.CryptoJS.enc.Utf8);
          this.orderInfo = JSON.parse(orderInfo);

          this.orderInfo.payinfo = payInfo;
          this.orderInfo.isloading = false;
          this.$util.debug("주문결제 목록 : " + JSON.stringify(this.orderInfo));
          this.orderProc(this.orderInfo);
        });
      }
    } else {
      const resultMessage = this.$route.query.resultMessage;
      let errorMsg = '네이버페이 결제처리중 오류가 발생하였습니다.';

      if(resultMessage == 'userCancel') {
        errorMsg = '결제를 취소하셨습니다.';
      } else if(resultMessage == 'paymentTimeExpire') {
        errorMsg = '결제시간을 초과하셨습니다.';
      } else if(resultMessage == 'OwnerAuthFail') {
        errorMsg = '본인카드 인증오류가 발생하였습니다.';
      } else {
        errorMsg = errorMsg + '(' + resultMessage + ')';
      }
      if(this.$store.state.platform != 'MAC001') {
        setTimeout(() => {
          this.$eventBus.$emit('alert', '알림', errorMsg, () => {
            window.close()
          });
        }, 500);
      } else {
        this.$nextTick(()=>{
          this.$eventBus.$emit('alert', '알림', errorMsg, () => {
            const order = this.$storage.getSessionStorage('orderinfo');
            this.$storage.removeSessionStorage('orderinfo');
            sessionStorage.setItem('refresh', 'true');
            this.$router.replace({name:'order', query:order});
          });
        });
      }
    }
  }
}
</script>
<style lang="scss" scoped>
.dp-loading {
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 0 auto;
  height: 100vh;
}
.dp-loading-wrap {
  text-align: center;
}

.dp-loading__figure {
  width: 200px;
  margin: 0 auto;
}

.dp-loading__text {
  font-size: 18px;
  letter-spacing: -0.72px;
  color: $gray-700;
  font-weight: 400;
}
.lds-ring {
  display: inline-block;
  position: relative;
  width: 80px;
  height: 80px;
}
.lds-ring div {
  box-sizing: border-box;
  display: block;
  position: absolute;
  width: 64px;
  height: 64px;
  margin: 8px;
  border: 8px solid $primary;
  border-radius: 50%;
  animation: lds-ring 1.2s cubic-bezier(0.5, 0, 0.5, 1) infinite;
  border-color: $primary transparent transparent transparent;
}
.lds-ring div:nth-child(1) {
  border-color: #ddd;
  animation-delay: -0.45s;
}
.lds-ring div:nth-child(2) {
  animation-delay: -0.3s;
}
.lds-ring div:nth-child(3) {
  animation-delay: -0.15s;
}
@keyframes lds-ring {
  0% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(360deg);
  }
}
</style>