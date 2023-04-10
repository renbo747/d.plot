import SnsShareModal from "@views.mobile/shop/popup/SnsShareModal.vue";
export default {
    beforeCreate() {
        this.$store.commit("onSubHeaderOption", {
            title: "MAGAZINE",
            searchIcon: true,
            cartIcon: true,
            homeIcon: false,
        });
    },
    mounted() {
        let param = {
            tridx: this.$route.params.tid
        }

        this.$http.post('/trend/detail', param)
            .then(result => {
                if (result.statusCode === 200) {
                    let data = result.data;
                    this.info = data.info;
                }
            })
    },
    data() {
        return {
            info: {},
            sub_subject : '서브 타이틀 영역 // 개발필요'
        };
    },
    methods: {
        changeWish() {
            if (!this.$store.state.isLogin) {
                this.$eventBus.$emit('confirm', '로그인', '로그인 하시겠습니까?',()=>{
                    this.$storage.setSessionStorage('redirectPath', {name : this.$route.name, query : this.$route.query});
                    this.$router.push({name : 'member-login'});
                });
                return;
            }
            let param = {
                tridx: this.info.tridx,
                iswished: this.info.iswished,
            };
            this.$http.post("/trend/wish", param)
                .then((result) => {
                    if (result.statusCode == 200) {
                        this.info.wishcnt = result.data.wishcnt;
                        this.info.iswished = this.info.iswished == "T" ? "F" : "T";
                    }
                });
        },
        /**********************
         * SNS 공유하기 모달
         ********************/
        snsShareModal() {
            let description = "";

            let param = {
                kakao: {
                    objectType: "feed",
                    content: {
                        title: "[D.PLOT]" + this.info.subject + "트랜드",
                        description: description,
                        imageUrl: this.info.fullpath,
                        link: {
                            mobileWebUrl: window.location.href,
                            webUrl: window.location.href
                        },
                    },
                },
                meta: {
                    title: "[D.PLOT]" + this.info.subject + "트랜드",
                    summary: "트랜드 상세내용",
                    img: this.info.fullpath
                }
            }
            this.$eventBus.$emit('showModal', SnsShareModal, "snsShareModal", param);
        }
    },
};