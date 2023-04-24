import Post from "@views.common/components/ui/modal/Post.vue"
import KmcCertReq from "@views/common/components/KmcCertReq.vue"
import LifetimeMember from '@views.mobile/member/popup/LifetimeMember.vue'

export default {
    components: { KmcCertReq },
    beforeCreate() {
        this.$store.commit("onSubHeaderOption", {
            title: "회원정보 수정",
            searchIcon: false,
            cartIcon: false,
            homeIcon: false,
        });
    },
    props: {},
    data() {
        return {
            //  회원 정보
            platform: window.sessionStorage.getItem('platform'),
            memberInfo: {
                name: "",       //이름
                userpw: "",     //비밀번호1
                userpw2: "",     //비밀번호2
                mobile: "",      //휴대폰번호
                tel: "",         //전화번호
                email: "",       //이메일
                islife: "F",      //평생회원여부
                isadsms: "F",    //광고성 문자메시지수신동의
                isadmailing: "F",//광고성 문자이메일수신동의
                isChAddr: false, //주소변경여부
                addrroad: "",    //도로명주소
                addr: "",        //지번주소
                post: "",        //우편번호
                sigungucode: "", //시군구코드
                buildingcode: "",//빌딩코드
                addrdetail: "",  //주소상세
                roadnamecode: "" //도로명코드 
            },
            tempInfo: {},
            // sns toggle 부분
            isToggleKakao: false,
            isToggleNaver: false,
            isToggleApple: false,
            isToggleLivingBody: false,
            // 평생회원 가입
            selectRadioData: "",
            lifetimeJoin: {
                id: "sampleRadio01",
                label: "평생회원으로 가입",
                checked: true,
            },
            lifetimeClear: {
                id: "sampleRadio01",
                label: "평생회원 해제",
                checked: true,
            },
            pwFail: false,
            pwCkFail: false,
            emailFail: false,
            emailFail2: false,
            emailFail3: false,
            emailFail4: false,
            isClick: false,
            browser: "",
        };
    },
    methods: {
        /**********************************
         * 회원정보 조회
         *********************************/
        getMemberInfo() {
            this.$http.post('/member/info', {}).then(result => {
                if (result.statusCode == 200) {
                    this.$util.debug("회원정보 수정페이지 회원정보 조회");
                    this.$util.debug(result.data.members);
                    // this.$util.debug(result.data.memberinfo);

                    //초기 데이터 셋팅
                    this.memberInfo.userno = result.data.memberinfo.userno;
                    this.memberInfo.name = result.data.members.name;
                    this.memberInfo.mobile = result.data.members.mobile;
                    this.memberInfo.tel = result.data.members.tel;
                    this.memberInfo.email = result.data.members.email;
                    this.memberInfo.company = result.data.members.company;
                    this.memberInfo.userid = result.data.members.userid;
                    this.memberInfo.isadmailing = result.data.members.isadmailing;
                    this.memberInfo.isadsms = result.data.members.isadsms;
                    // this.memberInfo.isapple = result.data.members.isapple;
                    // this.memberInfo.iskakao = result.data.members.iskakao;
                    // this.memberInfo.isnaver = result.data.members.isnaver;
                    this.memberInfo.joinchtype = result.data.members.joinchtype;
                    this.memberInfo.joinchtypenm = result.data.members.joinchtypenm;
                    this.memberInfo.dadamembertype = result.data.members.dadamembertype;
                    this.memberInfo.memlvtype = result.data.members.memlvtype;
                    this.memberInfo.preemail = result.data.members.preemail;
                    this.memberInfo.subemail = result.data.members.subemail;
                    this.memberInfo.companytypenm = result.data.members.companytypenm;
                    //주소 정보 셋팅
                    this.memberInfo.post = result.data.members.post;                    //우편번호
                    this.memberInfo.addr = result.data.members.addr;                    //기본주소
                    this.memberInfo.addrdetail = result.data.members.addrdetail;        //주소상세
                    this.memberInfo.addrroad = result.data.members.addrroad;            //도로명주소
                    this.memberInfo.addrdetailroad = result.data.members.addrdetailroad;//도로명주소상세
                    this.memberInfo.sigungucode = result.data.members.sigungucode;     //시군구코드
                    this.memberInfo.buildingcode = result.data.members.buildingcode;   //빌딩코드
                    this.memberInfo.roadnamecode = result.data.members.roadnamecode;   //도로명코드  
                    

                    if (this.memberInfo.dadamembertype === "DMT002") {
                        this.memberInfo.islife = "T";
                    } else {
                        this.memberInfo.islife = "F";
                    }
                    this.tempInfo = this.$util.deepClone(this.memberInfo);

                    this.$store.commit("isKakao", result.data.members.iskakao == 'T' ? true: false);
                    this.$store.commit("isNaver", result.data.members.isnaver == 'T' ? true: false);
                    this.$store.commit("isApple", result.data.members.isapple == 'T' ? true: false);
                    this.$store.commit("isBio", result.data.members.isbio == 'T' ? true: false);
                } else {
                    this.$eventBus.$emit('alert', '알림', result.message);
                }
            })
        },
        /**********************************
         * 다음주소 api 호출
         *********************************/
        execDaumPostModal() {
            this.$eventBus.$emit('showModal', Post, 'postModal', null, (address) => {
                this.$util.debug(address);
                this.memberInfo.addrroad = this.$util.isBlank(address.roadAddress) ? address.autoRoadAddress: address.roadAddress;      //도로명주소
                this.memberInfo.addr = this.$util.isBlank(address.jibunAddress) ? address.autoJibunAddress : address.jibunAddress;         //구주소
                this.memberInfo.post = address.zonecode;             //우편번호
                this.memberInfo.sigungucode = address.sigunguCode;   //시군구코드
                this.memberInfo.buildingcode = address.buildingCode; //빌딩코드
                this.memberInfo.roadnamecode = address.roadnameCode; //도로명코드  
            });
        },
        /**********************************
         * 회원 휴대폰변경을 위한 KMC 호출
         *********************************/
        changePhone() {
            this.$eventBus.$emit('confirm', "휴대폰 번호 변경 안내", '휴대폰번호 변경을 위해 본인인증을 해주세요.',
                () => {
                    //확인버튼이벤트
                    let kmcEnv = "";
                    if (window.sessionStorage.getItem('platform') == "MAC001") {
                        kmcEnv = process.env.VUE_APP_KMC_CERT_SIGN_UP_PC;
                    } else {
                        kmcEnv = process.env.VUE_APP_KMC_CERT_SIGN_UP_MO;
                    }
                    window.popupCallback = this.pcKmcOk;
                    this.$refs.kmcCertReq.request(kmcEnv, "mypage-info-modify");
                },
                () => {
                    //닫기버튼이벤트
                }, '본인 인증하기'
            );
        },
        /**********************************
        * KMC 성공처리
        ***********************************/
        pcKmcOk(param) {
            this.memberInfo.mobile = param.kmcInfo.phoneno;
        },
        /**********************************
        * SNS 연결해제 처리
        * SNSTYPE::ULT001(네이버), ULT002(카카오), ULT003(애플)
        ***********************************/
        snsDisConnect(snstype, snstypeNm) {
            let param = {
                snstype: snstype
            }
            this.$http.post('/member/sns/disconnect', param).then(result => {
                if (result.statusCode == 200) {
                    this.$eventBus.$emit('alert', '알림', snstypeNm + "연결을 해제 하였습니다.");
                    if (snstype == "ULT001") {
                        this.$store.commit("isNaver", false);
                    } else if (snstype == "ULT002") {
                        this.$store.commit("isKakao", false);
                    } else {
                        this.$store.commit("isApple", false);
                    }

                } else {
                    this.$eventBus.$emit('alert', '알림', result.message);
                }
            })
        },
        /****************************
        * 카카오 API 처리
        **************************/
        kakaoConnect: function (checked) {
            if (!checked) {
                this.$util.debug("kakaoDisConnect:: 완료");
                this.snsDisConnect('ULT002', '카카오');

            } else {
                //this.$refs.kakao.$refs.btoggole.checked = false;
                window.popupCallback = this.snsLogin;
                let kakaoUrl = 'https://kauth.kakao.com/oauth/authorize?client_id=' + process.env.VUE_APP_KAKAO_API_KEY;
                kakaoUrl += '&redirect_uri=' + window.sessionStorage.getItem('domain') + '/kakao-auth';
                kakaoUrl += '&response_type=code';

                window.open(kakaoUrl, 'snsLoginPoup', 'width=600, height=800, top=50, left=50, titlebar=1, resizable=1, scrollbars=yes');
            }
        },
        /**********************************
        * SNS 연결 처리
        ***********************************/
        snsLogin: function (result) {
            result.userno = this.memberInfo.userno;
            if (!this.$util.isNull(result.mobile)) {
                result.mobile = result.mobile.replaceAll("-", "");
            }
            let _result = result;
            this.$http.post('/member/sns/connect', result).then(result => {
                if (result.statusCode == 200) {
                    this.$eventBus.$emit('alert', '알림', '계정이 연결되었습니다.');
                    if (_result.snstype == "ULT002") {
                        this.$store.commit("isKakao", true);
                    } else if (_result.snstype == "ULT001") {
                        this.$store.commit("isNaver", true);
                    } else if (_result.snstype == "ULT003") {
                        this.$store.commit("isApple", true);
                    } else {
                        return;
                    }
                } else {
                    this.$eventBus.$emit('alert', '알림', '계정 연결에 실패했습니다.');
                }
            });
        },
        /**********************************
        * APPLE SNS 연결 처리
        ***********************************/
        snsAppleLogin: function (result) {
            result.userno = this.memberInfo.userno;
            if (!this.$util.isNull(result.mobile)) {
                result.mobile = result.mobile.replaceAll("-", "");
            }
            let _result = result;
            this.$http.post('/member/sns/apple/connect', result).then(result => {
                if (result.statusCode == 200) {
                    this.$eventBus.$emit('alert', '알림', '계정이 연결되었습니다.');
                    if (_result.snstype == "ULT003") {
                        this.$store.commit("isApple", true);
                    } else {
                        return;
                    }
                    this.memberInfo.iskakao = "T";
                } else {
                    this.$eventBus.$emit('alert', '알림', '계정 연결에 실패했습니다.');
                    if (_result.snstype == "ULT003") {
                        this.$store.commit("isApple", false);
                    } else {
                        return;
                    }
                }
            });
        },
        /**********************************
        * 네이버 연결
        ***********************************/
        naverConnect: function (checked) {
            //isnaver(T)::네이버 연결해제 처리,isnaver(F)::네이버 연결 처리
            if (!checked) {
                this.snsDisConnect('ULT001', '네이버');
            } else {
                this.$refs.naver.$refs.btoggole.checked = false;
                window.popupCallback = this.snsLogin;
                const naverLogin = new naver.LoginWithNaverId({
                    clientId: process.env.VUE_APP_NAVER_CLIENT_KEY,
                    callbackUrl: window.sessionStorage.getItem('domain') + process.env.VUE_APP_NAVER_REDIRECT_URI,
                    isPopup: true,
                    callbackHandle: true
                });
                naverLogin.init();
                window.open(naverLogin.generateAuthorizeUrl(), 'naverLoginPoup', 'width=600, height=800, top=50, left=50, titlebar=1, resizable=1, scrollbars=yes');
            }
        },
        /**********************************
        * 애플 연결
        ***********************************/
        appleConnect: function (checked) {
            if (!checked) {
                this.snsDisConnect('ULT003', '애플');
            } else {
                this.$refs.applelogin.$el.click();
            }
        },
        /**********************************
        * 애플 연결 성공처리
        ***********************************/
        callSuccess(result) {
            const param = {
                snsmemberno: result.userData.sub,
                email: result.userData.email,
                snsemail: result.userData.email,
                token: result.userData.sub, //현 솔루션상 토큰은 사용하지않고 애플 토큰이 길어서.. sub값으로 저장
                snstype: 'ULT003'
            }
            this.snsAppleLogin(param);
        },
        /**********************************
        * 애플 연결 실패처리
        ***********************************/
        callFail(err) {
            this.$eventBus.$emit('alert', '알림', '계정 연결에 실패했습니다.', () => {
                this.$store.commit("isApple", false);
            });
        },
        /****************************
        * 생체 로그인 처리
        **************************/
        bioConnect: function () {
            if (!this.$store.state.isBio) {
                this.$store.commit("isBio", false);
                this.bioOn();
            } else {
                this.bioOff();
            }
        },
        /****************************
        * 생체 로그인 연결
        **************************/
        bioOn: function () {
            if (!window.ReactNativeWebView) {
                alert('ReactNativeWebView 객체가 없습니다.');
            }
            this.$http.post("/member/getEncBio", {}).then((result) => {
                if (result.statusCode == 200) {
                    window.ReactNativeWebView.postMessage(
                        JSON.stringify({
                            type: 'BIO_ON',
                            data:{
                                result:true,
                                bio: result.data.encbio
                            }
                        })
                    )
                }else{
                    console.log("err");
                }
                
            });
        },
        /****************************
        * 생체 로그인 해지
        **************************/
        bioOff: function () {
            if (!window.ReactNativeWebView) {
                alert('ReactNativeWebView 객체가 없습니다.');
            }
            window.ReactNativeWebView.postMessage(
                JSON.stringify({
                    type: 'BIO_OFF',
                    data: {}
                })
            )
        },
        // 라디오 버튼
        changeRadioData(value) {
            this.memberInfo.islife = value;
            this.$util.debug(this.memberInfo.islife);
        },
        /**********************************
         * 취소 버튼 클릭시 처리(마이페이지 이동)
         *********************************/
        goToMypage() {
            this.$router.push("/mypage");
        },
        /**********************************
         * 회원정보 수정처리
         *********************************/
        setSave() {
            this.$util.debug("setSave()");
            if (this.pwFail) {
                this.$eventBus.$emit('alert', '알림', '비밀번호를 확인해주세요.');
                this.$refs.pwfocus.$refs.binput.focus();
                return;
            } else if (this.pwCkFail) {
                this.$eventBus.$emit('alert', '알림', '비밀번호가 일치하지 않습니다.');
                this.$refs.pwckfocus.$refs.binput.focus();
                return;
            } else if (this.emailFail || this.emailFail2 || this.emailFail3 || this.emailFail2) {
                this.$eventBus.$emit('alert', '알림', '이메일을 확인해주세요.');
                this.$refs.emailfocus.$refs.binput.focus();
                return;
            }
            this.$http.post('/member/infoModify', this.memberInfo).then(result => {
                if (result.statusCode == 200) {
                    this.$util.debug("회원정보 수정완료");
                    this.$eventBus.$emit('alert', '알림', '회원정보 수정을 완료했습니다.', () => {
                        this.$router.replace({ name: 'mypage' });
                    });
                }
            })
        },
        /**********************************
         * 비밀번호 체크
         *********************************/
        pwCheck() {
            if (this.memberInfo.userpw.trim().length != 0) {
                if (this.$util.isPassword(this.memberInfo.userpw)) {
                    if (this.memberInfo.userpw.length >= 8) {
                        this.pwFail = false;
                    }
                    if (this.memberInfo.userpw.length >= 10) {
                        this.pwFail = false;
                    }
                } else {
                    this.pwFail = true;
                }
            }
        },
        /**********************************
         * 비밀번호 확인 체크
         *********************************/
        pwConfirm() {
            if (this.$util.isNull(this.memberInfo.userpw) && this.$util.isNull(this.memberInfo.userpw2)) {
                this.pwCkFail = false;
            } else {
                if (this.memberInfo.userpw == this.memberInfo.userpw2) {
                    this.pwCkFail = false;
                    this.pwFail = false;
                } else {
                    this.pwCkFail = true;
                }
            }
        },
        /**********************************
         * 이메일 체크_일반
         *********************************/
        emailCheck() {
            if (this.$util.isNull(this.memberInfo.email)) {
                this.emailFail = true;
                this.emailFail2 = false;
                this.emailFail3 = false;
                return;
            } else {
                if (this.$util.isEmail(this.memberInfo.email)) {
                    this.emailFail = false;
                    this.emailFail2 = false;
                    this.emailFail3 = false;
                } else {
                    this.emailFail = true;
                    this.emailFail2 = false;
                    this.emailFail3 = false;
                    return;
                }
            }
            this.$http.post('/member/signup/emailCheck', { email: this.memberInfo.email }).then(result => {
                if (result.statusCode == 200) {
                    this.emailFail = false;
                    this.emailFail2 = false;
                    this.emailFail3 = false;
                } else {
                    let str = result.data.message;

                    if (result.message.includes('중복')) {
                        this.emailFail = false;
                        this.emailFail2 = true;
                        this.emailFail3 = false;
                    }
                    if (result.message.includes('탈퇴')) {
                        this.emailFail = false;
                        this.emailFail2 = false;
                        this.emailFail3 = true;
                    }
                }
            });

        },
        modalOpen(modalId) {
            this.$eventBus.$emit('showModal', LifetimeMember, modalId);
        },
    },
    mounted() {
        if (!Kakao.isInitialized()) {
            Kakao.init(process.env.VUE_APP_KAKAO_SCRIPT_KEY);
        }
        this.browser = this.$util.browser();
        this.getMemberInfo();
    },
    watch: {
        // 숫자만입력
        'memberInfo.tel': function (value) {
            this.$nextTick(() => {
                if (this.$util.isNull(value)) {
                    return;
                }
                this.memberInfo.tel = value.replace(/([^0-9])/gi, '');
            });
        },
    }
};