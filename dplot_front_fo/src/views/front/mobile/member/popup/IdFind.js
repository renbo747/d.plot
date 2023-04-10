import IdFindSuccess from "@views.mobile/member/popup/IdFindSuccess.vue"
import IdFindSnsSuccess from "@views.mobile/member/popup/IdFindSnsSuccess.vue"
import IdFindFail from "@views.mobile/member/popup/IdFindFail.vue"

import KmcCertReq from "@views/common/components/KmcCertReq.vue";

export default {
    components: { KmcCertReq },
    props: {
        id: { type: String },
        fnConfirm: { type: Function },
        fnCancel: { type: Function },
    },
    data() {
        return {
            isShowFindIdResult: false, // 아이디 찾음 모달 토글용
            isShowFindIdNonResult: false, // 아이디 없음 모달 토글용
            name: "",
            selectType: 'EMAIL',
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
            emailhp: "",
            maskedId: "", // 아이디 모달에서 별표처리된 아이디
            maskedEmail: "", // 별표처리된 이메일
            maskedHp: "", // 별표처리된 핸드폰번호
            signUpDate: "", // 가입일
            inputType: "text",
            platform: window.sessionStorage.getItem('platform')
        }
    },
    mounted() {
    },
    methods: {
        // 본인인증하기
        kmcCertReq() {
            this.$eventBus.$emit('confirm', "본인인증", '아이디 찾기를 위해 본인인증을 해주세요.',
                () => {
                    //확인버튼이벤트
                    let kmcEnv = "";
                    if (window.sessionStorage.getItem('platform') == "MAC001") {
                        kmcEnv = process.env.VUE_APP_KMC_CERT_SIGN_UP_PC;
                    } else {
                        kmcEnv = process.env.VUE_APP_KMC_CERT_SIGN_UP_MO;
                    }
                    window.popupCallback = this.pcIdKmcOk;
                    this.certPopupObj = window.open('/common/kmcCertPopup?code=' + kmcEnv, "KMCISWindow", "width=425, height=550, resizable=0, scrollbars=no, status=0, titlebar=0, toolbar=0, left=435, top=250");
                },
                () => {
                    //닫기버튼이벤트
                }, '본인 인증하기');
        },
        /****************************
         * KMC API 요청 결과 처리
         **************************/
        pcIdKmcOk(param) {
            const memberInfo =param.memberInfo;
            if (memberInfo == null) {
                if (window.sessionStorage.getItem('platform') == "MAC001") {
                    this.$router.push({ name: 'member-id-find-fail' });
                } else {
                    this.$eventBus.$emit('showModal', IdFindFail, 'id-find-fail');
                    if (this.$route.name != 'member-login') {
                        this.$router.replace({ name: 'member-login' });
                    }
                }
                this.$eventBus.$emit('showModal', IdFindFail, 'id-find-fail');
                if (this.$route.name != 'member-login') {
                    this.$router.replace({ name: 'member-login' });
                }

            } else {
                if (memberInfo.joinchtype != 'UCT001') {
                    // let params = this.$CryptoJS.AES.encrypt(JSON.stringify(memberInfo), memberInfo.userno).toString();
                    if (window.sessionStorage.getItem('platform') == "MAC001") {
                        this.$router.push({ name: 'member-id-find-sns-success', params: {memberInfo:memberInfo} });
                        //this.$router.push({ name: 'member-id-find-sns-success', params: params });
                    } else {
                        this.$eventBus.$emit('showModal', IdFindSnsSuccess, 'id-find-sns-success', memberInfo);
                    }
                } else {
                    if (window.sessionStorage.getItem('platform') == "MAC001") {
                        this.$router.push({ name: 'member-id-find-success', params: {memberInfo:memberInfo} });
                    } else {
                        this.$eventBus.$emit('showModal', IdFindSuccess, 'id-find-success', memberInfo);
                    }
                }
            }
        },
        // 아이디 찾기
        // TODO: 핸드폰번호 DB에 하이픈 있는데, 
        // 나중에 제거된다고 해서 하이픈 없앤거 기준으로 조회함! 
        //         -> DB에 현재는 들어가있어서 조회안되는데 추후에 변경되면 수정해야함
        findId() {
            if (this.$util.isNull(this.name)) {
                this.$eventBus.$emit('alert', '알림', "이름을 입력해주세요.");
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
                name : this.name,
                type : this.selectType,
                emailhp : this.emailhp
            }
            this.$http.post('/member/find/id', param).then(result => {
                if (result.statusCode == 200) {
                    if (this.platform == 'MAC001') {
                        if (result.data.ismember == 'NO') {
                            this.$router.push({ name: 'member-id-find-fail' });
                        } else if (result.data.ismember == 'SNS') {
                            this.$router.push({ name: 'member-id-find-sns-success', params: { memberInfo: result.data } });
                        } else {
                            this.$router.push({ name: 'member-id-find-success', params: { memberInfo: result.data } });
                        }
                    } else {
                        this.$bvModal.hide('findId');
                        if (result.data.ismember == 'NO') {
                            this.$eventBus.$emit('showModal', IdFindFail, 'id-find-fail', null, () => {
                                this.fnConfirm();
                            });
                        } else if (result.data.ismember == 'SNS') {
                            this.$eventBus.$emit('showModal', IdFindSnsSuccess, 'id-find-sns-success', result.data);
                        } else {
                            this.$eventBus.$emit('showModal', IdFindSuccess, 'id-find-success', result.data);
                        }
                    }
                }
            });
        },
        changeType() {
            if (this.selectType == 'MOBILE') {
                this.inputType = "number";
            } else {
                this.inputType = "text";
            }
            this.emailhp = "";
        }
    },
}