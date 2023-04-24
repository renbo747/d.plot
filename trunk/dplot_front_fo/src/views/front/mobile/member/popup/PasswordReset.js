import KmcCertReq from "@views/common/components/KmcCertReq.vue"
import PasswordNew from "@views.mobile/member/popup/PasswordNew.vue"
import IdFindSnsSuccess from "@views.mobile/member/popup/IdFindSnsSuccess.vue"
import IdFindFail from "@views.mobile/member/popup/IdFindFail.vue"

export default {
    components : {KmcCertReq},
    data() {
        return {
            isSended: false, // 인증번호 입력 표시 플래그
            timer: null, // 타이머 settimout 객체
            userid: "", // 아이디
            name: "", // 이름
            selectType : 'EMAIL',
            selectOptions: [
                {
                label: "이메일",
                value: "EMAIL",
                },
                {
                label: "휴대폰",
                value: "MOBILE",
                },
            ],
            emailhp: "", // 이메일 또는 핸드폰번호(01011112222, test@test.com)
            timeout: 0, // 인증번호 입력 남은시간(초)
            authnum: "", // 인증번호
            hhmm: "", // timeout을 mm:ss로 변환
            userno: "",
        }
    },
    methods: {
         // 본인인증하기
        kmcCertReq() {
            this.$eventBus.$emit('confirm', "본인인증", '비밀번호 찾기를 위해 본인인증을 해주세요.',
                () => {
                    //확인버튼이벤트
                    let kmcEnv = "";
                    if (window.sessionStorage.getItem('platform') == "MAC001") {
                        kmcEnv = process.env.VUE_APP_KMC_CERT_SIGN_UP_PC;
                    } else {
                        kmcEnv = process.env.VUE_APP_KMC_CERT_SIGN_UP_MO;
                    }
                    window.popupCallback = this.pcPwKmcOk;
                    this.certPopupObj = window.open('/common/kmcCertPopup?code=' + kmcEnv, "KMCISWindow", "width=425, height=550, resizable=0, scrollbars=no, status=0, titlebar=0, toolbar=0, left=435, top=250");
                },
                () => {
                    //닫기버튼이벤트
                }, '본인 인증하기');
        },
        /****************************
         * KMC API 요청 결과 처리
         **************************/
         pcPwKmcOk(param) {
            //this.$router.push({name:'member-passwordResetCertResult', params:param});
            const memberInfo = param.memberInfo;
            if(memberInfo == null) {
                if (window.sessionStorage.getItem('platform') == "MAC001") {
                    this.$router.push({ name: 'member-id-find-fail' });
                } else {
                    this.$eventBus.$emit('showModal', IdFindFail, 'id-find-fail');
                }
            } else {
                if(memberInfo.joinchtype != 'UCT001') {
                    if (window.sessionStorage.getItem('platform') == "MAC001") {
                        this.$router.push({ name: 'member-id-find-sns-success', params: {memberInfo:memberInfo} });
                    }else{
                        this.$eventBus.$emit('showModal', IdFindSnsSuccess, 'id-find-sns-success', memberInfo);
                    }
                } else {
                    if (window.sessionStorage.getItem('platform') == "MAC001") {
                        this.$router.push({ name: 'member-password-new', params: {memberInfo:memberInfo} });
                    } else {
                        this.$eventBus.$emit('showModal', PasswordNew, 'password-new', memberInfo);
                    }
                    
                }
            }
       },
        // 인증번호 발송
        sendAuthNum() {
            // 유효성 체크
            if (this.$util.isNull(this.userid)) {
                this.$eventBus.$emit('alert', '알림', '아이디를 입력해주세요.');
                return false;
            }
            if (this.$util.isNull(this.name)) {
                this.$eventBus.$emit('alert', '알림', '이름를 입력해주세요.');
                return false;
            }
            
            if (this.selectType == 'EMAIL'){
                if (this.$util.isNull(this.emailhp)) {
                    this.$eventBus.$emit('alert', '알림', "이메일을 입력해주세요.");
                    return false;
                }
                if (this.$util.isEmail(this.emailhp) == false) {
                    this.$eventBus.$emit('alert', '알림', "이메일 형식이 올바르지 않습니다.");
                    return false;
                }
            } else {
                if (this.$util.isNull(this.emailhp)) {
                    this.$eventBus.$emit('alert', '알림', "휴대폰번호를 입력해주세요.");
                    return false;
                }
                if (this.$util.isPhone(this.emailhp) == false) {
                    this.$eventBus.$emit('alert', '알림', "핸드폰 형식이 올바르지 않습니다. 하이픈 없이 숫자만 입력해주세요.");
                    return false;
                }
            }

            const param = {
                userid: this.userid,
                name: this.name,
                type : this.selectType,
                emailhp: this.emailhp
            };

            this.$http.post('/member/find/pw/authSend', param).then(result => {
                if (this.timer) {
                    clearInterval(this.timer);
                }
                this.authnum = ""; // 입력한 인증번호 초기화

                if (result.statusCode == 200) {
                    if(result.data.ismember == 'SNS') {
                        this.$bvModal.hide('password-reset');
                        this.$eventBus.$emit('showModal', IdFindSnsSuccess, 'id-find-sns-success', result.data);
                        return;
                    }
                    this.isSended = true; // 인증번호 입력 표시 플래그
                    
                    this.timeout = result.data.timeout; // 타이머 시간 세팅
                    //this.timeout = 10; // 타이머 시간 세팅

                    // 타이머 시작
                    this.countDownTimer();
                } else {
                    this.isSended = false; // 인증번호 입력 표시 플래그
                    this.$eventBus.$emit('alert', '알림', result.message);
                }

            });
        },
        // 인증번호 확인
        confirmAuthNum() {
            const param = {
                userid: this.userid,
                name: this.name,
                type : this.selectType,
                emailhp: this.emailhp,
                authnum: this.authnum
            };
            
            this.$http.post('/member/find/pw/authConfirm', param).then(result => {
                if (result.statusCode == 200) {
                    if (this.timer) {
                        clearInterval(this.timer);
                    }
                    this.authnum = "";

                    // 시간초과
                    if (!result.data.timeout) {
                        reSendAuthNum();
                        return false;
                    }
                    // 인증번호 틀림
                    if (!result.data.matched) {
                        this.$eventBus.$emit('alert', '알림', '인증번호가 틀립니다.');
                        return false;
                    }
                    // 인증성공
                    if (result.data.authorized) {
                        if(this.$store.state.platform == 'MAC001'){
                            this.$router.push({name:'member-password-new', params : {memberInfo : result.data}});
                        } else {
                            this.$bvModal.hide('findPw');
                            this.$eventBus.$emit('showModal', PasswordNew, 'password-new', result.data);
                        }
                    }
                } else {
                    this.$eventBus.$emit('alert', '알림', result.message);
                    return false;
                }
            });
        },
        // 인증번호 재발송
        reSendAuthNum() {
            if (this.timer) {
                clearInterval(this.timer);
            }
            this.authnum = "";
            this.isSended = false;
            this.$eventBus.$emit('confirm', '인증오류', '인증시간이 초과되었습니다. 다시 인증요청하시겠습니까?', () => {
                    thisObj.sendAuthNum();
                }
            );
        },
        // 타이머
        countDownTimer() {
            this.timer = setInterval(() => {
                this.timeout -= 1;
                if(this.timeout < 0) {
                    reSendAuthNum();
                    return false;
                }
                this.hhmm =  Math.floor(this.timeout / 60) + " : " + this.$util.addZero((this.timeout % 60));
            }, 1000);
        },
        changeType() {
            if(this.selectType == 'MOBILE') {
                this.inputType = "number";
            } else {
                this.inputType = "text";
            }
            this.emailhp = "";
        }
    }
}