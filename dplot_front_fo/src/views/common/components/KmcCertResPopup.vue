<template>
  <div></div>
</template>

<script>
export default {
  name: "kmc.cert.req.popup",
  data(){
    return {
      kmcInfoReq : {
        rec_cert: "",
        certNum:""
      },
    }
  },
  mounted() {
    this.kmcInfoReq.rec_cert = this.$route.query.rec_cert;
    this.kmcInfoReq.certNum = this.$storage.getSessionStorage('CERTNUM');
    this.failPath = this.$storage.getSessionStorage('FAILPATH');
    
    this.$http.post("/common/kmcCertDecry", this.kmcInfoReq).then((result) =>{
      if(result.statusCode === 200){
        let param = Object.assign(result.data.kmcinfo, result.data.memberinfo);
        window.localStorage.setItem('CERT_DATA', JSON.stringify(param));
        self.close();
      }
    }).catch(error => {
      this.$util.debug(error);
    });
  }
}
</script>