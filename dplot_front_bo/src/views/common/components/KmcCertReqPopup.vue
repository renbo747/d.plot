<template>
  <div>
    <form ref="reqKMCISForm" name="reqKMCISForm" method="post" action="https://www.kmcert.com/kmcis/web/kmcisReq.jsp" target="KMCISWindow">
      <input type="hidden" ref="trCert" name="tr_cert"/>
      <input type="hidden" name="tr_url" v-model="kmcInfo.tr_url" />
      <input type="hidden" name="tr_add" v-model="kmcInfo.tr_add" />
    </form>
  </div>
</template>

<script>
export default {
  name: "kmc.cert.req.popup",
  data() {
    return {
      kmcInfo: {
        cpId: process.env.VUE_APP_KMC_CERT_CPID, //회원사id
        urlCode: "", //URL코드
        certNum: "", //요청번호 => 중복되지않게 생성해야함
        date: "", //요청일시
        certMet: "M", //본인인증방법('M':휴대폰 본인확인, 'P':공인인증서)
        name: "", //이용자성명
        phoneNo: "", //휴대폰번호
        phoneCorp: "", //이동통신사
        birthDay: "", //생년월일
        gender: "", //이용자성별
        nation: "", //내외국인
        plusInfo: "", //추가DATA정보
        tr_cert: "", //요청정보(암호화)
        tr_url: process.env.VUE_APP_KMC_CERT_REDIRECT_URI_POPUP_PARTNER, //본인인증서비스 결과수신 POPUP URL
        //tr_url: "http://local.mobilefactory.co.kr:8080/kmcResult", //본인인증서비스 결과수신 POPUP URL
        extendVar: "0000000000000000", //확장변수
        tr_add: "N", //iframe사용여부
      }
    }
  },
  mounted() {
    let system = window.sessionStorage.getItem("system");
    this.kmcInfo.tr_url = (system === 'ADMIN') ? process.env.VUE_APP_KMC_CERT_REDIRECT_URI_POPUP_ADMIN : process.env.VUE_APP_KMC_CERT_REDIRECT_URI_POPUP_PARTNER;
    this.kmcInfo.urlCode = this.$route.query.code;
    this.kmcInfo.name = this.$route.query.name;
    this.kmcInfo.phoneNo = this.$route.query.phone;

    this.$http.post("/common/kmcRequest", this.kmcInfo).then((result) => {
      if (result.statusCode === 200) {

        this.$refs.trCert.value = result.data.tr_cert;
        this.$storage.setSessionStorage('CERTNUM', result.data.certnum);
        this.$refs.reqKMCISForm.submit();
      }
    });
  }
}

</script>

<style scoped />