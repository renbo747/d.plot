/**********************************
 * ike 컨텐츠(브랜드, 트렌드) 노출 6개월 (180일)
 * 각 최대 100개
 * 로그인(회원)
 * Like 최신순 10개 단위 노출
 * Like 개수 초과 시 알림 메시지(PLP, PDP 적용)
 * 좋아요 물리적 삭제 처리
 * */
import InfiniteLoading from "vue-infinite-loading";
export default {
    beforeCreate() {
        this.$store.commit("onSubHeaderOption", {
            title: "LIKE",
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
            // like 탭메뉴
            likeTab: [
                {
                    id: "all",
                    label: "전체",
                    checked: true,
                },
                {
                    id: "brand",
                    label: "BRAND",
                    checked: false,
                },
                {
                    id: "megazine",
                    label: "MEGAZINE",
                    checked: false,
                },
            ],
            likeList: [],
            recentList: [],
            recentGroup: [],
            tempGroup: [],
            isloading: true,
            infiniteId: + new Date(),
            pagingData: {
                currentPage: 1,  // 현재 페이지
                listTotal: 0,   // 조회목록 전체 수
                listCnt: 10,   // 한페이지에 노출할 목록수
                init: 'F'
            },
            searchData: {
                type: "all"
            },
            tabIndex: 0, //현재 탭
            isEnd: false, // 최초 데이터 목록을 조회전 조회 결과 없음이 순간 노출되는게 있어서 처리용
            isTop: true   // 일부핸드폰에서 최초 화면로딩시 scroll이 탑으로 가지않는 현상 처리용
        };
    },
    methods: {
        /***********************
         * 데이터 초기화
         ***********************/
        initData() {
            this.isEnd = false;
            //like가 아닐경우 검색데이터 초기화
            if (this.$route.name != "mz_like") {
                this.searchData.type = "all";
            }
            if (this.$route.name == "recent") {
                this.getRecentList();
            } else {
                //최근 본상품일경우 로컬스토리지에서 목록 조회 후 그룹핑
                //this.pagingData.currentPage = 1;
                this.pagingData.listTotal = 0;
                this.isloading = true;
                this.infiniteId += 1;
                this.likeList = [];
                this.recentGroup = [];
            }
        },
        /***********************
         * LIKE 목록 조회
         ***********************/
        getList($state) {
            this.$util.debug("목록조회 시도함...........");
            if (this.isloading) {
                this.isEnd = false;
                let param = Object.assign(this.pagingData, this.searchData);
                param.iskeep = true;
                param.reqname = this.$route.name;
                this.$http.post("/mz/likeList", param).then(result => {
                    if (result.statusCode == 200) {
                        this.isloading = true;
                        if (result.data.likelist.length) {
                            this.isloading = true;
                            this.pagingData.currentPage += 1;
                            for (let i = 0; i < result.data.likelist.length; i++) {
                                result.data.likelist[i].saleamt = result.data.likelist[i].saleamt - result.data.likelist[i].goodscpnamt;
                            }
                            this.likeList.push(...result.data.likelist);

                            this.pagingData.listTotal = result.data.listtotal;
                            this.$util.debug(this.likeList);
                            //목록 최대 100개
                            if (this.likeList.length > 100 || this.pagingData.listTotal <= this.likeList.length) {
                                $state.complete();
                                this.isloading = false;
                            } else {
                                $state.loaded();
                            }
                        } else {
                            this.isloading = false;
                            this.pagingData.listTotal = result.data.listtotal;
                            $state.complete();
                        }
                    }
                })
                this.isEnd = true;
            }
        },
        /***********************
         * LIKE 상품 목록 조회
         ***********************/
        getGoodsList($state) {
            this.$util.debug("목록조회 시도함...........");
            this.isEnd = false;
            if (this.isloading) {
                let param = Object.assign(this.pagingData, this.searchData);
                param.iskeep = true;
                param.reqname = this.$route.name;
                this.$http.post("/goods/wishList", param).then(result => {
                    if (result.statusCode == 200) {
                        this.$util.debug("LIKE 상품 목록 조회");
                        this.isloading = true;
                        this.pagingData.listTotal = result.data.listtotal;
                        if (result.data.likelist.length) {
                            this.isloading = true;
                            this.pagingData.currentPage += 1;
                            for (let i = 0; i < result.data.likelist.length; i++) {
                                result.data.likelist[i].saleamt = result.data.likelist[i].saleamt - result.data.likelist[i].goodscpnamt;
                            }

                            this.likeList.push(...result.data.likelist);
                            this.$util.debug(this.likeList);
                            //목록 최대 100개
                            if (this.likeList.length > 100 || this.pagingData.listTotal <= this.likeList.length) {
                                $state.complete();
                                this.isloading = false;
                            } else {
                                $state.loaded();
                            }
                        } else {
                            this.pagingData.listTotal = result.data.listtotal;
                            this.isloading = false;
                            $state.complete();
                        }
                    }
                    this.isEnd = true;
                })
            }
        },
        /***********************
         * 최근본 상품 목록 조회
         ***********************/
        getRecentList() {
            this.isEnd = false;
            this.recentList = [];
            this.$util.debug("최근본목록:::");
            let tempList = this.$front.getRecentItems(this);

            let param = {
                goodsnolist: tempList
            }
            param.iskeep = true;
            param.reqname = this.$route.name;
            this.$http.post('/goods/recentList', param).then(result => {
                if (result.statusCode == 200) {
                    for (let i = 0; i < result.data.recentlist.length; i++) {
                        result.data.recentlist[i].saleamt = result.data.recentlist[i].saleamt - result.data.recentlist[i].goodscpnamt;
                    }
                    let recentList = result.data.recentlist;
                    this.pagingData.listTotal = recentList.length;

                    tempList.forEach(item => {
                        let temp = recentList.filter((x) => (x.goodsno == item.goodsno));
                        if (temp.length > 0) {
                            item = Object.assign(item, temp[0]);
                        }
                    });
                    tempList.forEach(item => {
                        let idx = this.$front.containIdx(this.recentList, "regdate", item.regdate);
                        if (idx == -1) {
                            this.recentList.push({ regdate: item.regdate, list: tempList.filter((x) => (x.regdate == item.regdate)) });
                        }
                    })
                }
                this.isEnd = true;
            })
        },
        /***********************
         * 브랜드&트랜드 좋아요 목록 삭제
         ***********************/
        delLike(isAllDel, item) {
            this.$util.debug("삭제..");
            if (this.$route.name != "mz_like") {
                return;
            }
            if (isAllDel) {
                /******************
                 * 전체 삭제 처리
                 ******************/
                this.$eventBus.$emit('confirm', '확인', 'Like를 전체삭제 하시겠습니까?', () => {
                    let param = Object.assign(this.searchData);
                    this.$http.post('/mz/likedelAll', param).then(result => {
                        if (result.statusCode == 200) {
                            this.$toast.clear();
                            this.$toast.open('Like가 삭제되었습니다.');
                            this.pagingData.currentPage = 1;
                            this.initData();
                        } else {
                            this.$eventBus.$emit('alert', '알림', result.message);
                        }
                    });
                });
            } else {
                /******************
                 * 선택 삭제 처리
                 ******************/
                let param = {
                    idx: item.idx,
                    type: item.type
                }
                this.$http.post('/mz/likedel', param).then(result => {
                    if (result.statusCode == 200) {
                        this.pagingData.currentPage = 1;
                        this.initData();
                    } else {
                        this.$eventBus.$emit('alert', '알림', result.message);
                    }
                });
            }
        },
        /***********************
         * like상품 좋아요 삭제
         ***********************/
        delGoodsLike(isAllDel, item) {
            this.$util.debug("삭제..");
            if (this.$route.name != "g_like") {
                return;
            }
            if (isAllDel) {
                /******************
                 * 전체 삭제 처리
                 ******************/
                this.$eventBus.$emit('confirm', '확인', 'Like 상품을 전체삭제 하시겠습니까?', () => {
                    let param = Object.assign(this.searchData);
                    this.$http.post('/goods/wishDel', param).then(result => {
                        if (result.statusCode == 200) {
                            this.$toast.clear();
                            this.$toast.open('Like 상품이 삭제되었습니다.');
                            this.pagingData.currentPage = 1;
                            this.initData();
                        } else {
                            this.$eventBus.$emit('alert', '알림', result.message);
                        }
                    });
                });
            } else {
                /******************
                 * 선택 삭제 처리
                 ******************/
                let param = {
                    idx: item.idx,
                }
                this.$http.post('/goods/wishDel', param).then(result => {
                    if (result.statusCode == 200) {
                        this.pagingData.currentPage = 1;
                        this.initData();
                    } else {
                        this.$eventBus.$emit('alert', '알림', result.message);
                    }
                });
            }
        },
        /***********************
         * 최근본 상품 삭제
         ***********************/
        deleteRecent(isAllDel, item) {
            this.$util.debug("삭제..");
            if (this.$route.name != "recent") {
                return;
            }
            if (isAllDel) {
                /******************
                 * 전체 삭제 처리
                 ******************/
                this.$eventBus.$emit('confirm', '확인', '최근 본 상품을 전체삭제 하시겠습니까?', () => {
                    this.$storage.removeLocalStorage('RECENT_ITEMS');
                    this.getRecentList();
                });
            } else {
                /******************
                 * 선택 삭제 처리
                 ******************/
                let tempList = this.$front.getRecentItems(this);
                this.$front.delRecentItems(this, item);
                this.getRecentList();
            }
        },
        /***********************
         * 탭변경시 라우터 변경
         ***********************/
        changeTap(target) {
            this.isEnd = false;
            this.$router.replace({ name: target, params: { init: true } });
        },
        /***********************
         * LIKE탭 자식 탭 변경시 처리
         ***********************/
        changeLikeTab() {
            if (this.$route.name != "mz_like") {
                return;
            }
            this.pagingData.currentPage = 1;
            this.initData();
        },
        /***********************
         * LIKE상세 페이지 이동
         ***********************/
        goDetail(list) {
            if (list.type == 'brand') {
                this.$router.push('/magazine/brand/detail/' + list.idx);
            } else if (list.type == 'megazine') {
                this.$router.push('/magazine/trend/detail/' + list.idx);
            } else {
                return;
            }
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
            if ((this.likeList.length > 0 || this.recentList.length > 0) && this.pagingData.init == 'T') {
                this.$eventBus.$emit('scrollTo', this.$route.name);
                this.pagingData.init = 'F';
                this.isTop = false;
            }else{
                if (this.isTop) {
                    this.$util.scrollToTop();
                    this.isTop = false;
                }
            }
        });
    },
    watch: {
        /******************
         * 라우터 변경시 처리
         ******************/
        // '$route.name'() {
        //     this.$util.debug("라우터 변경됨..");
        //     this.initData();
        // },
        /******************
         * Like탭의 조회 조건 변경시 처리
         ******************/
        'searchData.type'() {
            this.changeLikeTab();
        }
    }
};