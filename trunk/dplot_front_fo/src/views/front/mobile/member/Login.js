import KmcCert from '@views.mobile/member/KmcCert.js'
import IdFind from './popup/IdFind.vue'
import PasswordReset from './popup/PasswordReset.vue'
import PasswordChange from './popup/PasswordChange.vue'

export default {
    mixins: [KmcCert],
    beforeCreate() {
        this.$store.commit("onSubHeaderOption", {
            title: "로그인",
            searchIcon: true,
            cartIcon: true,
            homeIcon: false,
        });
    },
    data() {
        return {
            id: '',
            pw: '',
            rememberMe: false,
            isSaveId: false,
            isApp: false,
            captchaImgSrc: null, // 캡차 이미지
            captchaLoadText: null, // 캡차 이미지 로드 중 보일 텍스트
            captchaKey: null, // 캡차 API에서 생성한 키
            captchaValue: "", // 유저가 입력한 값
            captchaType: "image", // 캡차타입
            captchaValid: false, // 캡차 검증결과
            isNeedCaptcha: false, // 캡차가 필요할 경우 true로 하고 캡차 검증결과가 true일 경우에만 로그인 로직 실행

            // Todo: 기기에 따라 나타나는 조건값 수정 필요
            // WebAOS, NativeAOS, WebIOS, NativeIOS
            browser: "",
            // 카카오 데이터
            kakaoSignupData: {
                nameData: "",
            },
        }
    },
    mounted() {
        this.checkCaptchar();

        if (!Kakao.isInitialized()) {
            Kakao.init(process.env.VUE_APP_KAKAO_SCRIPT_KEY);
        }

        if (this.isNeedCaptcha == true) {
            const param = {
                requestType: "get",
                captchatype: this.captchaType
            };
            this.getCaptcha(param);
        }

        this.browser = this.$util.browser();
        //this.browser = 'Safari';

        if (document.location.href.indexOf('local') > -1) {
            this.id = 'mofac01';
            this.pw = 'mofac1009!';
        }

        const id = this.$cookies.get("SAVE_ID");
        if (!this.$util.isNull(id) && !this.$util.isEmpty(id)) {
            this.id = id;
            this.isSaveId = true;
        }
        //임직원 회원가입처리
        // if (!this.$util.isEmpty(this.$route.params)) {
        //     if (this.$route.params.isEmp) {
        //         this.isEmp = this.$route.params.isEmp;
        //         this.signup();
        //     }

        // }
    },
    methods: {
        /****************************
        * 팝업창 요청 처리
        **************************/
        showModal(modalId) {
            if (modalId == 'id-find') {
                this.$eventBus.$emit('showModal', IdFind, modalId, null, () => {
                    this.signup();
                });
            } else {
                this.$eventBus.$emit('showModal', PasswordReset, modalId);
            }
        },
        /****************************
         * 캡차 타당성 검사 처리
         **************************/
        getCaptchaValid() {
            if (this.captchaValue == "") {
                this.$eventBus.$emit('alert', '알림', "보안문자를 입력하세요.");
                return false;
            }
            const param = {
                requestType: "validation",
                captchakey: this.captchaKey,
                captchavalue: this.captchaValue,
                captchatype: this.captchaType
            }
            this.getCaptcha(param);
        },
        /****************************
         * 캡차 타입 설정(image 또는 sound)
         **************************/
        setCaptcha(type) {
            this.captchaType = type;
            this.getCaptcha({});
        },
        /****************************
        * 캡차 requestType이 get일 경우 이미지/소리 받아옴
        * 캡차 requestType이 validation일 경우 입력한 captchavalue가 맞는지 판단해서 captchaValid에 세팅
        **************************/
        getCaptcha(param) {
            param["captchatype"] = this.captchaType;
            this.$util.debug("캡차 실행");
            if (param.requestType == null || param.requestType == undefined) {
                param["requestType"] = "get";
            }
            // API 호출 전 로딩하는 모습 보여줌
            if (param.requestType == "get") {
                this.captchaImgSrc = null;
                this.captchaLoadText = "Loading...";
                this.captchaKey = null;
            }
            this.$http.post('/member/captcha', param).then(result => {
                if (result.statusCode == 200) {
                    if (result.data != null) {
                        if (param.requestType == "get") {
                            this.captchaKey = result.data.key;
                            const src = result.data.src;
                            if (src != null && src != undefined) {
                                if (this.captchaType == "sound") {
                                    const audio = new Audio(src);
                                    audio.play();
                                    this.captchaLoadText = "스피커 음성을 입력해주세요.";
                                } else {
                                    this.captchaImgSrc = src;
                                    this.captchaLoadText = null;
                                }
                            }
                        } else {
                            this.captchaValid = result.data.verification;
                            if (this.captchaValid == false) {
                                this.captchaValue = "";
                                if (this.captchaType == "image") {
                                    this.$eventBus.$emit('alert', '알림', "보안문자가 올바르지 않습니다.");
                                    const reParam = {
                                        requestType: "get",
                                        captchatype: this.captchaType
                                    };
                                    this.getCaptcha(reParam);
                                } else {
                                    this.$eventBus.$emit('alert', '알림', "보안문자가 올바르지 않습니다.<br>다시 재생해주세요.");
                                }
                            } else {
                                this.captchaImgSrc = null;
                                this.captchaLoadText = null;
                                this.login();
                            }
                        }
                    }
                } else {
                    this.$eventBus.$emit('alert', '알림', "보안문구를 불러올 수 없습니다.");
                    this.captchaLoadText = "보안문구를 불러올 수 없습니다.";

                    // 오류 발생으로 캡차 종료
                    this.isNeedCaptcha = false;
                }
            }).catch(() => {
                this.$eventBus.$emit('alert', '알림', "보안문구를 불러올 수 없습니다.");
                this.captchaLoadText = "보안문구를 불러올 수 없습니다.";

                // 오류 발생으로 캡차 종료
                this.isNeedCaptcha = false;
            });
        },
        /****************************
         * 캡처 필요 처리
         **************************/
        checkCaptchar() {
            // 로그인 5회 실패했을 경우
            if (!this.$util.isNull(this.$cookies.get("OTL"))) {
                this.isNeedCaptcha = true;
            } else {
                this.isNeedCaptcha = false;
            }

            if (this.isNeedCaptcha) {
                this.getCaptcha({});
            }
        },
        /****************************
        * 로그인 처리
        **************************/
        login() {
            if (this.id.trim() == "") {
                this.$eventBus.$emit('alert', '알림', "아이디를 입력해주세요.");
                return false;
            }
            if (this.pw.trim() == "") {
                this.$eventBus.$emit('alert', '알림', "비밀번호를 입력해주세요.");
                return false;
            }
            let param = {
                id: this.id,
                pw: this.pw
            }

            if (this.isSaveId == true) {
                param["isSaveId"] = 'T';
                this.$cookies.set("SAVE_ID", this.id, "7d");
            } else {
                this.$cookies.remove("SAVE_ID");
            }
            if (this.rememberMe == true) {
                param["rememberMe"] = 'T';
            }

            if (this.isNeedCaptcha == true && this.captchaValid == false) {
                this.getCaptchaValid();
                return false;
            }
            this.$http.post('/member/login_act', param).then(result => {
                if (result.statusCode == 200) {
                    this.$store.commit("isLogin", true);
                    //this.$store.commit("name", result.data.memberinfo.username);
                    this.$storage.setSessionStorage('name', result.data.memberinfo.username);
                    //네이티브일경우 Prefrence 설정
                    this.$front.sendNativeMessage({
                        type: 'PREF_SAVE',
                        data: {
                            isLogin: true,
                            isAlarm: result.data.memberinfo.ispush,
                            isBio: result.data.memberinfo.isbio,
                            appVerion: '1.0.0',
                            name: result.data.memberinfo.username,
                            userid: result.data.memberinfo.userid,
                        }
                    })

                    //장바구니 담기
                    this.$eventBus.$emit('saveCart', () => {
                        //result.data.isneedchangepw = true;
                        this.$storage.removeLocalStorage("CART_ITEMS");
                        if (result.data.isneedinitpw) {
                            if (this.platform != 'MAC001') {
                                this.$eventBus.$emit('showModal', PasswordChange, 'password-change', {param: result.data,isTempPassword: true});
                            } else {
                                this.$router.replace({ name: 'member-password-change', query: { info: result.data.memberinfo.userno, isTempPassword: true } });
                            }
                        } else if (result.data.isneedchangepw) {
                            if (this.platform != 'MAC001') {
                                this.$eventBus.$emit('showModal', PasswordChange, 'password-change', { param: result.data,isTempPassword: false });
                            } else {
                                this.$router.replace({ name: 'member-password-change', query: { info: result.data.memberinfo.userno, isTempPassword: false } });
                            }
                        } else {
                            this.$front.redirectPage(this);
                        }
                    });
                } else {
                    if (result.data != null && result.data.issleep == true) { 
                        this.$storage.setSessionStorage('redirectPath', '/member/dormant-account');
                        this.$storage.setSessionStorage('SLEEP_USER_ID', this.id);
                        this.$front.redirectPage(this);
                        return;
                    }
                    // 캡차해야하는데, 로그인 실패 시, 캡차 다시 불러옴
                    if (this.isNeedCaptcha == true) {
                        this.captchaValue = "";
                        this.captchaValid = false;
                        const param = {
                            requestType: "get",
                            type: "image"
                        };
                        this.getCaptcha(param);
                    }
                    this.$eventBus.$emit('alert', '알림', result.message);
                }
                this.checkCaptchar();
            });
        },
        /****************************
        * 카카오 API 처리
        **************************/
        kakaoLogin: function () {
            window.popupCallback = this.snsLogin;
            let redirectUrl = encodeURIComponent(window.sessionStorage.getItem('domain') + '/kakao-auth');
            let kakaoUrl = 'https://kauth.kakao.com/oauth/authorize?client_id=' + process.env.VUE_APP_KAKAO_API_KEY;
            kakaoUrl += '&redirect_uri=' + redirectUrl;
            kakaoUrl += '&response_type=code';
            console.log(kakaoUrl);
            window.open(kakaoUrl, 'snsLoginPoup', 'width=600, height=800, top=50, left=50, titlebar=1, resizable=1, scrollbars=yes');
        },
        /****************************
         * 네이버 API 요청 처리
         **************************/
        naverLogin: function () {
            window.popupCallback = this.snsLogin;
            const naverLogin = new naver.LoginWithNaverId({
                clientId: process.env.VUE_APP_NAVER_CLIENT_KEY,
                callbackUrl: window.sessionStorage.getItem('domain') + process.env.VUE_APP_NAVER_REDIRECT_URI,
                isPopup: true,
                callbackHandle: true
            });
            naverLogin.init();
            window.open(naverLogin.generateAuthorizeUrl(), 'snsLoginPoup', 'width=600, height=800, top=50, left=50, titlebar=1, resizable=1, scrollbars=yes');
        },
        /****************************
         * 생체 로그인 처리
         **************************/
        bioLogin: function () {
            if (!window.ReactNativeWebView) {
                alert('ReactNativeWebView 객체가 없습니다.');
            }
            window.ReactNativeWebView.postMessage(
                JSON.stringify({
                    type: 'BIO_REQ',
                    data: {}
                })
            )
        },
        /**********************
         * apple API 요청 성공시 처리
         ********************/
        callSuccess(result) {
            this.isApple = true;
            const param = {
                snsmemberno: result.userData.sub,
                email: result.userData.email,
                snsemail: result.userData.email,
                token: result.userData.sub, //현 솔루션상 토큰은 사용하지않고 애플 토큰이 길어서.. sub값으로 저장
                snstype: 'ULT003'
            }
            this.snsLogin(param);
        },
        /**********************
        * apple API 요청 실패시 처리
        ********************/
        callFail(err) {
            //사용자가 팝업 닫기했을경우 예외처리
            if (err.detail.error == "popup_closed_by_user") {
                return;
            }
            this.$eventBus.$emit('alert', '알림', '애플 로그인 처리중 에러가 발생하였습니다.');
        }
    },
    // watch: {
    //     isNeedCaptcha(n, o) {
    //         if (o == false && n == true) {
    //             this.getCaptcha({});
    //         }
    //     }
    // },
};