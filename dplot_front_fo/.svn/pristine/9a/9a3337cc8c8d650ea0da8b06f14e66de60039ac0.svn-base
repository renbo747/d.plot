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
          <p class="dp-loading__text mb-0">
            본인인증 처리중입니다.
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
export default {
    name: "kmc.cert.req",
    data(){
        return {
            kmcInfoReq : {
                rec_cert: "",
                certNum:""
            },
            kmcInfo: {},
            memberInfo: {},
            failPath:"",
            plusInfo:[],
            plusInfoObj:{}
        }
    },
    methods: {
        /*****************************
         * 복호화 처리
         * ***************************/
        kmcCertDecry(){
            this.kmcInfoReq.isloading = false;
            this.$http.post("/common/kmcCertDecry", this.kmcInfoReq).then((result) =>{
                if (result.statusCode ==  200) {
                    let param = {kmcInfo:result.data.kmcinfo, memberInfo:result.data.memberinfo, witdrawcnt:result.data.witdrawcnt, issleep:result.data.issleep, sleepmember:result.data.sleepmember};
                    window.opener.popupCallback(param);
                    window.close();
                } else {
                    // if (!this.$util.isMobile()){
                    //     window.opener.popupCallback(params);
                    //     self.close();
                    // }else{
                    //    this.$router.push({name:'main'});
                    // }  
                    this.$util.debug(error);
                }
            });
        }
    },
    mounted() {
        this.kmcInfoReq.rec_cert = this.$route.query.rec_cert;
        this.kmcInfoReq.certNum = this.$storage.getSessionStorage('CERTNUM');
        this.kmcCertDecry();
    },
    beforeUnmount(){
        this.$storage.removeSessionStorage('CERTNUM');
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