<template>
  <div>
    <form
      ref="reqKMCISForm"
      name="reqKMCISForm"
      method="post"
      action="https://www.kmcert.com/kmcis/web/kmcisReq.jsp"
    >
      <input type="hidden" ref="trCert" name="tr_cert" />
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
        tr_url: window.sessionStorage.getItem('domain') + process.env.VUE_APP_KMC_CERT_REDIRECT_URI, //본인인증서비스 결과수신 POPUP URL
        extendVar: "0000000000000000", //확장변수
        tr_add: "N", //iframe사용여부
      },
    };
  },
  mounted() {
    this.kmcInfo.urlCode = this.$route.query.code;
    /*********************************************
     * CERTNUM 얻은후 kmc 인증창 오픈 처리
     **********************************************/
    this.$http.post("/common/kmcRequest", this.kmcInfo).then((result) => {
      if (result.statusCode === 200) {
        this.$refs.trCert.value = result.data.tr_cert;
        this.$storage.setSessionStorage('CERTNUM', result.data.certnum);
        this.$refs.reqKMCISForm.submit();
      }
    });
  },
};
</script>

<style scoped />