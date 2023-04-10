import SnsConnect from '@views.mobile/member/popup/SnsConnect.vue'
import NaverSignup from '@views.mobile/member/popup/NaverSignup.vue'
import KakaoSignupSuccess from '@views.mobile/member/popup/KakaoSignupSuccess.vue'
import SignUpAlready from "@views.mobile/member/popup/SignUpAlready.vue"

export default {
    data() {
        return {
            platform: null,
            logininfo: {},
            isApple: false, //애플회원가입요청여부
            isEmp: false,//임직원회원가입 여부
            appleInfo: null
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
            this.isApple = false;
            this.isEmp = false;
            this.kmcOpen();
        },
        /****************************
        * 애플회원가입처리
        **************************/
        appleSignup(params) {
            this.isApple = true;
            this.isEmp = false;
            this.appleInfo = params;
            this.kmcOpen();
        },
        /****************************
        * 임직원회원가입
        **************************/
        empSignUp() {
            this.isApple = false;
            this.isEmp = true;
            this.kmcOpen();
        },
        /****************************
         * KMC API 요청 처리
         **************************/
        kmcOpen() {
            //session삭제
            if (this.$route.path != "/member/login") {
                this.$router.push('/member/login');
            }
            this.$storage.removeSessionStorage("KMCINFO");

            this.$eventBus.$emit('confirm', this.isApple ? "애플 회원가입 안내" : "회원가입 안내", '회원가입을 위해 본인인증을 해주세요.',
                () => {
                    //확인버튼이벤트
                    let kmcEnv = "";
                    if (window.sessionStorage.getItem('platform') == "MAC001") {
                        kmcEnv = process.env.VUE_APP_KMC_CERT_SIGN_UP_PC;
                    } else {
                        kmcEnv = process.env.VUE_APP_KMC_CERT_SIGN_UP_MO;
                    }

                    window.popupCallback = this.pcKmcOk;
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
        pcKmcOk(param) {
            if (param != null) {
                if (param.witdrawcnt > 0) {
                    this.$eventBus.$emit('alert', '알림', "탈퇴 회원은 30일이내 재가입이 불가능합니다.");
                    return;
                }
                if (param.memberInfo == null) {
                    this.$storage.setSessionStorage("KMCINFO", { kmcInfo: param.kmcInfo, appleInfo: this.appleInfo, isApple: this.isApple, isEmp: this.isEmp });
                    this.$router.push({ name: "member-signUp" });
                } else {
                    param.memberInfo.isApple = this.isApple;
                    if (this.platform == 'MAC001') {
                        this.$router.push({ name: "member-signup-already" , params:param.memberInfo});
                    }else {
                        this.$eventBus.$emit("showModal", SignUpAlready, "AlreadySignupModal", param.memberInfo);
                    }
                 
                }
            }
        },
        snsLogin(params) {
            this.$http.post('/member/sns/login', params).then(result => {
                if (result.statusCode == 200) {
                    if (!this.$util.isNull(result.data.witdrawcnt) && result.data.witdrawcnt > 0) {
                        this.$eventBus.$emit('alert', '알림', "탈퇴 회원은 30일이내 재가입이 불가능합니다.");
                    }else if (!this.$util.isEmpty(result.data.membersns)) {
                        //SNS 회원정보가 있을경우
                        this.$front.otherLogin(this, result.data.membersns[0].userid, 'sns', result.data.membersns[0].snsmemberno, result.data.membersns[0].snstype, params.isadpush, params.isadmailing, params.isadsms);
                    } else if (!this.$util.isEmpty(result.data.member)) {
                        //기존 회원정보가 있을경우
                        //this.logininfo = Object.assign(params, result.data);
                        if (this.platform == 'MAC001') {
                            this.$router.replace({ name: 'member-sns-connect', params: result.data });
                        } else {
                            this.$eventBus.$emit('showModal', SnsConnect, 'sns-connect', result.data);
                            //this.$router.replace({ name: 'member-login' });
                        }
                    } else {
                        params.issns = 'T';
                        params.dadamembertype = "DMT001";

                        //회원정보가 없을경우
                        if (params.snstype == 'ULT001') {
                            //네이버
                            if (this.platform == 'MAC001') {
                                this.$router.replace({ name: 'nav-signup', params: result.data });
                            }else{
                                this.$eventBus.$emit('showModal', NaverSignup, 'naver-signup', result.data);
                            }
                            //this.$router.replace('/member/login');
                        } else if (params.snstype == 'ULT002') {
                            if(this.$util.isNull(params.mobile)) {
                              alert('카카오 계정정보에 휴대폰 번호가 없어 간편가입이 불가능 합니다. 일반회원으로 가입해 주세요.');
                              return;
                            } else {
                              this.$http.post('/member/signup/act', params).then(result => {
                                  if (result.statusCode == 200) {
                                      this.$eventBus.$emit('alert', '회원가입', '회원가입이 완료되었습니다.', () => {
                                          let successParam = Object.assign({}, result.data);
                                          successParam.issns = 'T';

                                          this.$eventBus.$emit('showModal', KakaoSignupSuccess, 'kakao-signup-success', successParam);
                                          //this.$front.otherLogin(this, result.data.userid, 'sns', result.data.snsmemberno, result.data.snstype);
                                      });
                                  } else {
                                      this.$eventBus.$emit('alert', '알림', result.message);
                                      //this.$router.replcae('/main');
                                  }
                              });
                            }
                        } else {
                            //애플 회원가입 처리를 위하여 login페이지로 이동
                            params.isApple = true;
                            //this.$router.replace({'name':'member-login', 'params': params});
                            this.appleSignup(params);
                        }
                    }
                }
            });
        }
    },
    beforeDestroy() {
        this.$eventBus.$emit('loading', false);
    },
}