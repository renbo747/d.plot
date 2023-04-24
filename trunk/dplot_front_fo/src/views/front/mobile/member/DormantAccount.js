export default {
    data() {
        return {
            platform: null
        }
    },
    mounted() {
        this.platform = window.sessionStorage.getItem('platform');
    },
    methods: {
        /****************************
         * 일반회원가입처리
         **************************/
        signup() {
            this.kmcOpen();
        },
        /****************************
         * KMC API 요청 처리
         **************************/
        kmcOpen() {
            this.$storage.removeSessionStorage("KMCINFO");

            this.$eventBus.$emit('confirm', '휴면계정 해제 안내', '휴면계정 해제를 위해 본인인증을 해주세요.',
                () => {
                    //확인버튼이벤트
                    let kmcEnv = process.env.VUE_APP_KMC_CERT_SIGN_UP_MO;
                    if (window.sessionStorage.getItem('platform') == "MAC001") {
                        kmcEnv = process.env.VUE_APP_KMC_CERT_SIGN_UP_PC;
                    }
                    
                    /* 모바일 인증은 도메인/URL이 다 등록되어야 테스트 가능함
                       www.kmcert.com 에 회원사전용 페이지 들어가서 로긴 후 도메인/URL을 등록해야 하며, URL 등록 시 URL CODE가 같이 나오므로 해당 URL CODE를 파라미터로 던져야 한다. (2022-11-18, James) 
                    */

                    window.popupCallback = this.wakeUppcKmcOk;
                    //KmcCertReqPopup.vue
                    this.certPopupObj = window.open('/common/kmcCertPopup?code=' + kmcEnv, "KMCISWindow", "width=425, height=550, resizable=0, scrollbars=no, status=0, titlebar=0, toolbar=0, left=435, top=250");
                },
                () => {
                    //닫기버튼이벤트
                },
                '본인 인증하기'
            );
        },
        /****************************
         * KMC API 요청 결과 처리
         **************************/
        wakeUppcKmcOk(param) {
            console.log('===============================================================');
            console.log(param);
            if (param != null) {
                let sleepUserId = this.$storage.getSessionStorage('SLEEP_USER_ID');
                this.$storage.removeSessionStorage('SLEEP_USER_ID');
                
                console.log("sleepUserId : " + sleepUserId);
                console.log("param.issleep : " + param.issleep);
                console.log("param.sleepmember : " + param.sleepmember);
                if (param.issleep == false) {
                    alert('본인 인증 결과에 해당하는 회원은 휴면회원이 아닙니다.');
                } else {
                    if(param.sleepmember.userid !== sleepUserId) {        // 로그인 시도 시 userno와 본인인증 결과의 userno가 다른 경우. 이럴땐 방법이 없음
                        alert('휴면 해제 처리 시 오류가 발생하였습니다. CS에 문의 바랍니다.');
                        return;
                    }
                    const params = {
                        user_no_arr_str : param.sleepmember.userno
                    }
                    /** 휴면해제 처리 로직 넣어야 함. url 은 /member/sleep/restore 이고 파라미터는 userno로만 넘기면 됨. */
                    this.$http.post('/member/sleep/restore', params).then(result => {
                        if (result.statusCode == 200) {
                            /** 로그인 페이지로 이동 처리 */
                            alert('휴면 해제 처리되었습니다.');
                            this.$router.push({ name: 'member-login' });
                        /*} else {
                            alert('관리자에게 문의하세요.');*/
                        }
                    });
                }
            }
            console.log('===============================================================');
        },
    },
    beforeDestroy() {
        this.$eventBus.$emit('loading', false);
    },
}