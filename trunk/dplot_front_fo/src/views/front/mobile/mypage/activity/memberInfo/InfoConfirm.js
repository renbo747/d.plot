export default {
    beforeCreate() {
        this.$store.commit("onSubHeaderOption", {
            title: "회원정보 확인",
            searchIcon: false,
            cartIcon: false,
            homeIcon: false,
        });
    },
    data() {
        return {
            // input 부분
            memberInfoData: {},
            password: "",
            pwFail:false,
            pwFail2:false
        };
    },
    methods: {
        getMemberInfo() {
            this.$http.post('/member/info', {}).then(result => {
                if (result.statusCode == 200) {
                    this.$util.debug("회원정보::");
                    this.$util.debug(result.data.members);
                    this.memberInfoData = result.data.members;
                    if (this.memberInfoData.joinchtype != "UCT001") {
                        this.goNextpage();
                    }else {
                        if (window.sessionStorage.getItem('platform') == "MAC003") {
                            if (!window.ReactNativeWebView) {
                                return;
                            }
                            window.ReactNativeWebView.postMessage(
                                JSON.stringify({
                                    type: 'BIO_REQ',
                                    data: {}
                                })
                            )
                        }
                    }
                   
                } else {
                    this.$eventBus.$emit('alert', '알림', result.message);
                }
            })
        },
        getLogin() {
            let param = {
                id: this.memberInfoData.userid,
                pw: this.password
            }
            if (this.$util.isNull(param.pw)) {
                //this.pwFail = true;
                this.$eventBus.$emit('alert', '알림', '비밀번호를 입력해주세요.');
                return;
            }
            this.$http.post('/member/confirmCheck', param).then(result => {
                if (result.statusCode == 200) {
                    this.goNextpage();
                } else {
                    this.$eventBus.$emit('alert', '알림', result.message);
                    return;
                }
            })
        },
        goNextpage(){
            if (this.$route.query.type == 'withdraw') {
                this.$router.push("/mypage/member-withdraw");
            }else {
                this.$router.replace('/mypage/info-modify');
            }
        },
        // 취소버튼 클릭 시
        goToMypage() {
            this.$router.push("/mypage");
        },
        test(){
        }
    },
    mounted() {
        this.getMemberInfo();
    }
};