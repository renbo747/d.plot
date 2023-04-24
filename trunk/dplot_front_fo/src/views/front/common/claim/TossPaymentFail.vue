<template>
    <main class="dp-loading">
        <div class="container">
            <div class="dp-loading-wrap">
                <div class="dp-loading__img mb-2">
                <figure class="dp-loading__figure">
                  <logo />
                </figure>
                </div>
                <div class="dp-loading__text__wrap dp-panel">
                <p class="dp-loading__text mb-0">처리중입니다. </p>
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
export default {
  name: 'PaymentFail',
  data() {
    return {
      page : 'mypage'
    }
  },
  mounted() {
    window.addEventListener("load", () => {
      document.getElementById("HappytalkIframe").style.display = "none";
    });
    this.$http.post('/member/checkAuth', {isloading : false}).then(result => {
      if(result.statusCode != 200) {
        this.page = 'nonemember';
      }
      if(this.$util.isBlank(this.$route.query.orderId)) {
        this.$eventBus.$emit('alert', '알림', '잘못된 접근입니다.', ()=>{
          this.$router.replace({name:this.page + '-claim'});
        })
      }

      this.$nextTick(()=>{
        let message = this.$route.query.message;
        if(this.$util.isBlank(message)){
          message = '결제처리중 에러가 발생하였습니다';
        }
        this.$eventBus.$emit('alert', '알림', message, () => {
          this.getClaimInfo();
        });
      });
    });
  },
  methods : {
    getClaimInfo() {
      const param = {
        clmno : this.$route.query.orderId,
        isloading : false
      }
      
      this.$http.post('/' + this.page + '/claim/info', param).then(result => {
        if (result.statusCode == 200) {
          if(this.$store.state.platform != 'MAC001'){
            setTimeout(() => {
              window.opener.popupCallback(result.data);
              window.close()
            }, 500);
          } else {
            this.goDetail(result.data);
          }
        } else {
          if(this.$store.state.platform != 'MAC001'){
            window.close();
          } else {
            this.$router.replace({name: this.page + '-claim'});
          }
        }
      });
    },
    goDetail(param) {
      if(param.clmtype == 'CLM001') {
          this.$router.replace({name: this.page + '-claim-cancel-detail', params : {clmno : param.clmno}});
      } else if(param.clmtype == 'CLM002') {
          this.$router.replace({name: this.page + '-claim-return-detail', params : {clmno : param.clmno}});
      } else {
          this.$router.replace({name: this.page + '-claim-exchange-detail', params : {clmno : param.clmno}});
      }
    }
  }
}
</script>

<style lang="scss">
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