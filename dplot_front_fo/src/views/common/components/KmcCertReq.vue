<template>
    <div>
        <form ref="reqKMCISForm" name="reqKMCISForm" method="post" action="http://www.kmcert.com/kmcis/web/kmcisReq.jsp">
            <input type="hidden" ref="trCert" name="tr_cert"/>
            <input type="hidden" name="tr_url" v-model="kmcInfo.tr_url" />
            <input type="hidden" name="tr_add" v-model="kmcInfo.tr_add" />
        </form>
    </div>
</template>

<script>
export default {
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
                //tr_url: "http://local.mobilefactory.co.kr:8080/kmcResult", //본인인증서비스 결과수신 POPUP URL
                extendVar: "0000000000000000", //확장변수
                tr_add: "N", //iframe사용여부
            }
        }
  },
  methods: {
    request(urlCode, succesName ,failName, param) {
        this.kmcInfo.urlCode = urlCode;

        //파라미터 전달 필요시 plusInfo에 담아서 전달(string::구분자로 구분)
        //성공routerName, 실패 routerName, queryString(snstype=ULT003&issns=T&dadamembertype=DMT001)

        if (this.$util.isEmpty(param)) {
            this.kmcInfo.plusInfo = succesName + "," + failName;
        }else {
            var queryString = this.$util.toQueryStr(param);
            this.kmcInfo.plusInfo = succesName + "," + failName + "," + queryString;
        }
        
        this.$http.post("/common/kmcRequest", this.kmcInfo).then((result) => {
            if (result.statusCode == 200) {
                this.$refs.trCert.value = result.data.tr_cert;
                this.$storage.setSessionStorage('CERTNUM', result.data.certnum);
                
                var KMCIS_window;
                /* 모바일 접근 체크*/
                // 모바일일 경우 (변동사항 있을경우 추가 필요)
                // 모바일이 아닐 경우
                KMCIS_window = window.open(
                    "",
                    "KMCISWindow",
                    "width=425, height=550, resizable=0, scrollbars=no, status=0, titlebar=0, toolbar=0, left=435, top=250"
                );
                if (KMCIS_window == null) {
                    alert(
                        "※윈도우 XP SP2 또는 인터넷 익스플로러 7 사용자일 경우에는 \n    화면 상단에 있는 팝업 차단 알림줄을 클릭하여 팝업을 허용해 주시기 바랍니다. \n\n※ MSN,야후,구글 팝업 차단 툴바가 설치된 경우 팝업허용을 해주시기 바랍니다."
                    );
                }
                this.$refs.reqKMCISForm.target = "KMCISWindow";
                this.$refs.reqKMCISForm.submit();
            }
        });
    },
  }
}
</script>

<style>
</style>