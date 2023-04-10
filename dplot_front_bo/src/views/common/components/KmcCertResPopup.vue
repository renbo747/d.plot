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
    let kmcCertUrl;
    let system = window.sessionStorage.getItem("system");
    if (system == 'PARTNER') {
        kmcCertUrl="/common/partnerkmcCertDecry";
    }else if (system == 'ADMIN') {
        kmcCertUrl="/common/adminkmcCertDecry";
    }

    this.$http.post(kmcCertUrl, this.kmcInfoReq).then((result) =>{
      if(result.statusCode === 200){
        let param = Object.assign(result.data.kmcinfo, result.data.memberinfo);
        window.opener.popupCallback(param);

        alert("본인인증이 완료 되었습니다.");
        self.close();
      }
    }).catch(error => {
      this.$util.debug(error);
    });
  }
}
</script>