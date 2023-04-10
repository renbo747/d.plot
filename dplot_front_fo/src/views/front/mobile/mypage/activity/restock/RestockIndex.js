import InfiniteLoading from "vue-infinite-loading";
/**
 * 전상품 단품코드 단위
 * 입고시까지 신청유효
 * 알림톡 발송
 * 알림신청 가능 30개
 */
export default {
    beforeCreate() {
        this.$store.commit("onSubHeaderOption", {
            title: "재입고알림 신청목록",
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
            isloading: true,           //스크롤페이징 조회할지 여부
            infiniteId: + new Date(),   //스크롤페이징 id
            pagingData: {
                currentPage: 1,         // 현재 페이지
                listTotal: 0,           // 조회목록 전체 수
                listCnt: 5,             // 한페이지에 노출할 목록수
                init: 'F'
            },
            restockList: [],             //재입고 알림 목록
            isEnd: false, // 최초 데이터 목록을 조회전 조회 결과 없음이 순간 노출되는게 있어서 처리용
            isTop: true   // 일부핸드폰에서 최초 화면로딩시 scroll이 탑으로 가지않는 현상 처리용
        };
    },
    methods: {
        /**********************
         * 데이터 초기화
         ********************/
        initData() {
            this.restockList = [];
            this.infiniteId += 1;
            this.isloading = true;
            // this.pagingData.currentPage = 1;
            this.pagingData.listTotal = 0;
        },
        /**********************
         * 재입고알림 상품 목록 조회
         ********************/
        getRestockList($state) {
            let param = Object.assign(this.pagingData);
            this.isEnd = false;
            param.iskeep = true;
            param.reqname = this.$route.name;
            this.$http.post('/goods/restockList', param).then(result => {
                if (result.statusCode == 200) {
                    this.$util.debug("재입고 상품 목록 조회");
                    this.pagingData.listTotal = result.data.listtotal;
                    if (result.data.restocklist.length <= 0) {
                        this.isloading = false;
                        $state.complete();
                    } else {
                        this.pagingData.currentPage += 1;

                        let list = result.data.restocklist;
                        for (let i = 0; i < list.length; i++) {
                            let optionnames = list[i].options.split(',');
                            list[i].opthtml = '';
                            if (optionnames.length > 0) {
                                if (list[i].isuseaddition == "T") {
                                    optionnames[0] = '추가상품:' + optionnames[0];
                                } else {
                                    optionnames[0] = '옵션:' + optionnames[0];
                                }
                                for (let i = 0; i < optionnames.length; i++) {
                                    optionnames[i] = '<span>' + optionnames[i] + '<span>';
                                }
                                list[i].opthtml = optionnames.join('<span class="dp-bar h10"></span>');
                            }
                        }
                        this.restockList.push(...list);
                        //this.restockList = [...this.restockList || [], ...result.data.restocklist];
                        if (this.restockList.length == this.pagingData.listTotal) {
                            this.isloading = false;
                            $state.complete();
                        }
                        this.isloading = true;
                        $state.loaded();
                    }
                }
                this.isEnd = true;
            })
        },
        /**********************
         * 상품 삭제 처리
         ********************/
        delteProduct(isALL, item) {
            let param = {}
            if (!isALL) {
                param.rewidx = item.rewidx;
            }
            this.$http.post('/goods/restock/del', param).then(result => {
                if (result.statusCode == 200) {
                    this.initData();
                    this.$toast.clear();
                    this.$toast.open("재입고알림신청이 취소되었습니다.");
                } else {
                    this.$eventBus.$emit('alert', '알림', result.message);
                }
            });
        }
    },
    created() {
        if (this.$route.params.init) {
            this.$storage.removeSessionStorage('param-' + this.$route.name);
        } else {
            const param = this.$storage.getSessionStorage('param-' + this.$route.name);
            if (!this.$util.isEmpty(param)) {
                this.pagingData.currentPage = param.currentPage;
                this.pagingData.init = 'T';
            }
        }
    },
    mounted() {
        this.initData();
    },
    updated() {
        this.$nextTick(() => {
            if (this.restockList.length > 0 && this.pagingData.init == 'T') {
                this.$eventBus.$emit('scrollTo', this.$route.name);
                this.pagingData.init = 'F';
                this.isTop = false;
            } else {
                if (this.isTop) {
                    this.$util.scrollToTop();
                    this.isTop = false;
                }
            }
        });
    },
};