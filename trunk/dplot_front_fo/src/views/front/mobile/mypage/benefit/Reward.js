import EpointInfo from './popup/EpointInfo.vue'
import RewardInfo from './popup/RewardInfo.vue'
import InfiniteLoading from "vue-infinite-loading";
export default {
    beforeCreate() {
        let title = "";
        if (this.$route.name == "mypage-epoint") {
            title = "D포인트";
        } else if (this.$route.name == "mypage-reward") {
            title = "적립금";
        } else {
            title = "임직원 적립금";
        }
        this.$store.commit("onSubHeaderOption", {
            title: title,
            searchIcon: true,
            cartIcon: true,
            homeIcon: false,
        });
    },
    components: {
        InfiniteLoading
    },
    data() {
        return {
            pointType: [
                {
                    id: "all",
                    label: "전체",
                    checked: true,
                },
                {
                    id: "pay",
                    label: "적립",
                    checked: false,
                },
                {
                    id: "use",
                    label: "차감",
                    checked: false,
                },
                {
                    id: "extinct",
                    label: "소멸",
                    checked: false,
                },
            ],
            // 임직원 라디오 타이프
            employeePointType: [
                {
                    id: "all",
                    label: "전체",
                    checked: true,
                },
                {
                    id: "pay",
                    label: "적립",
                    checked: false,
                },
                {
                    id: "use",
                    label: "차감",
                    checked: false,
                },
            ],
            isloading: true,           //스크롤페이징 조회할지 여부
            infiniteId: + new Date(),   //스크롤페이징 id
            pagingData: {
                currentPage: 1,         // 현재 페이지
                listTotal: 0,           // 조회목록 전체 수
                listCnt: 10              // 한페이지에 노출할 목록수
            },
            rewardList: [],
            serialno: "",
            type: "all",
            extinctpoint: 0,
            userpoint: 0,
            extinctday: "",
            constants: this.$store.getters['MEMBER'],
            isEnd: false,
            isToTop:true
        };
    },
    methods: {
        /**********************
         * 데이터 초기화
         ********************/
        initData() {
            this.isEnd = false;
            this.serialno = "";
            this.rewardList = [];
            this.infiniteId += 1;
            this.isloading = true;
            this.pagingData.currentPage = 1;
            this.pagingData.listTotal = 0;
        },
        /*************************
         * 적립금 & D포인트 목록 조회
         *************************/
        getRewardList($state) {
            if (this.isloading) {
                let url = "";
                let param = {};
                if (this.$route.name == "mypage-epoint") {
                    url = "/mypage/epoint/list";
                } else if (this.$route.name == "mypage-employee-reward") {
                    url = "/mypage/reserves/list";
                    param.isempreserve = "T";
                } else {
                    url = "/mypage/reserves/list";
                    param.isempreserve = "F";
                }
                param.type = this.type;
                param = Object.assign(param, this.pagingData);
                this.isEnd = false;
                this.$http.post(url, param).then(result => {
                    if (result.statusCode == 200) {
                        this.extinctpoint = result.data.extinctpoint;
                        this.extinctday = result.data.extinctday;
                        this.userpoint = result.data.userpoint;
                        if (result.data.rewardlist.length > 0) {
                            this.rewardList = [...this.rewardList || [], ...result.data.rewardlist];
                            this.pagingData.listTotal = result.data.rewardlist[0].totcnt;
                            this.pagingData.currentPage += 1;
                            $state.loaded();
                            this.isloading = true;
                            // if (this.pagingData.listTotal <= this.rewardList.length) {
                            //     this.isloading = false;
                            //     $state.complete();
                            // }else {
                            //     $state.loaded();
                            //     this.isloading = true;
                            // }
                        } else {
                            this.isloading = false;
                            $state.complete();
                        }
                    }
                    if (!this.isToTop) {
                        this.$nextTick(() => {
                            this.$util.scrollToTop();
                            this.isToTop = true;
                        });
                    }
                    this.isEnd = true;
                })
            }
        },
        /*************************
         * D포인트, 적립금 사용안내 모달 오픈
         *************************/
        openModal(modalId) {
            if (modalId == 'epointModal') {
                this.$eventBus.$emit('showModal', EpointInfo, 'epointModal');
            } else if (modalId == 'rewardInfoModal') {
                this.$eventBus.$emit('showModal', RewardInfo, 'rewardInfoModal');
            } else {
                return;
            }
        },
        /*************************
         * 프로모션 코드 등록 처리
         *************************/
        addPromotion() {
            this.$eventBus.$emit('confirm', '확인', '혜택을 등록 하시겠습니까?', () => {
                this.$util.debug(this.serialno);
                if (this.$util.isNull(this.serialno)) {
                    this.$eventBus.$emit('alert', '확인', "프로모션 코드를 입력해주세요.");
                    return;
                }
                let param = {
                    serialno: this.serialno,
                    srptype: this.$route.name == "mypage-reward" ? this.constants.SRPTYPE_SRT_SAVE : this.constants.SRPTYPE_SRT_EPOINT
                }

                this.$http.post('/mypage/addPromotionCode', param).then(result => {
                    if (result.statusCode == 200) {
                        this.$toast.clear();
                        this.$toast.open("혜택등록이 완료되었습니다.");
                        this.initData();
                    }
                });
            });
        }
    },
    mounted() {
        this.isToTop=false;
        this.initData();
    },
    watch: {
        type(val) {
            this.initData();
        }
    }
};