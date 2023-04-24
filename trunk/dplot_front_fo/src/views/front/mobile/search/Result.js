import SearchFilter from "@views.mobile/search/popup/SearchFilter.vue";
import InfiniteLoading from "vue-infinite-loading";

export default {
    components: {
        InfiniteLoading
    },
    data() {
        return {
            modalBrandOption: {
                observeParents: true,
                observer: true,
                pagination: {
                    el: ".swiper-pagination",
                },
            },
            setPriceValue: {
                currentValue: [0, 10000],
                min: 0,
                max: 10000,
                interval: 1000,
            },
            searchData: {},
            relatedList: [], //연관상품목록
            goodsList: [], //상품목록
            brandList: [], //브랜드목록
            contentList: [], //컨텐츠목록
            priceList: [], //가격목록
            giconList: [], // 아이콘설정목록
            recomList: [], // 추천유형목록
            goodsLen: 0, //상품전체수
            brandLen: 0, //브랜드전체수
            contentLen: 0, //컨텐츠전체수
            sortActive: "best",
            sortList: [
                {
                    value: "best",
                    label: "인기순",
                },
                {
                    value: "new",
                    label: "최신순",
                },
                {
                    value: "highscore",
                    label: "평점높은순",
                },
                {
                    value: "lowscore",
                    label: "평점낮은순",
                },
                {
                    value: "highprice",
                    label: "가격높은순",
                },
                {
                    value: "lowprice",
                    label: "가격낮은순",
                },
            ],
            isloading: true,
            infiniteId: + new Date(),
            pagingData: {
                currentPage: 1, // 현재 페이지
                listTotal: 0, // 조회목록 전체 수
                listCnt: 10, // 한페이지에 노출할 목록수
                init: 'F'
            },
            platform: window.sessionStorage.getItem("platform"),
            membertype: "DMT001", //회원구분(비로그인:일반회원,브론즈)
            memlvtype: "MDL001", //회원레벨(비로그인:일반회원,브론즈)
            setCateidx: "",
            setBrandList: [],
            setColorList: [],
            setBenefitList: [],
            setRatingList: [],
            setFilterList: [],
            setPriceList: [],
            setCateList: [],
            setGiconList: [], // 선택 아이콘설정 리스트
            setRecomList: [], // 선택 추천유형 리스트
            activeTap: 0,
            queryStr: "",
            isEnd: false,
            benefitList: [],
            initMinPrice: 0,
            initMaxPrice: 0,
            ratingList:[],
            colorList:[],
            isFilter: false
        };
    },
    methods: {
        /***************************
         * 탭 컨버터
         **************************/
        tapConverter(name) {
            //같은 탭 클릭시 전환x
            if (name == this.$route.name) {
                return;
            }
            this.pagingData.currentPage = 1;
            this.$front.setSearchData(this);
            const searchStr = encodeURIComponent(JSON.stringify(this.searchData));
            this.$router.push({ name: name, query: { content: this.$route.query.content, search: searchStr }, params: { init: true } });
        },
        /************************
         *  정렬 선택 이벤트 처리
         ************************/
        sortSelect() {
            this.filterRouterChange();
        },
        /*****************
         * 정렬쿼리 리턴
         *******************/
        setSortStr() {
            if (this.sortActive == "best") {
                return "goods@RANK/DESC";
            } else if (this.sortActive == "new") {
                return "goods@date/DESC";
            } else if (this.sortActive == "highscore") {
                return "goods@reviewavg/DESC";
            } else if (this.sortActive == "lowscore") {
                return "goods@reviewavg/ASC";
            } else if (this.sortActive == "highprice") {
                return this.platform == "MAC001"
                    ? "goods@pcsaleamt/DESC"
                    : this.platform == "MAC002"
                        ? "goods@mosaleamt/DESC"
                        : "goods@appsaleamt/DESC";
            } else {
                return this.platform == "MAC001"
                    ? "goods@pcsaleamt/ASC"
                    : this.platform == "MAC002"
                        ? "goods@mosaleamt/ASC"
                        : "goods@appsaleamt/ASC";
            }
        },
        /************************
         * 데이터 초기화
         ***********************/
        initData() {
            this.goodsLen = 0;
            this.brandLen = 0;
            this.contentLen = 0;
            this.goodsList = []; //상품목록
            this.brandList = []; //브랜드목록
            this.contentList = []; //컨텐츠목록
            this.isloading = true;
            this.infiniteId += 1;
            // this.pagingData.currentPage = 1;
        },

        /************************
         * 스크롤 페이징 처리
         ************************/
        async getGoodsList($state) {
            this.$util.debug("getGoodsList....");
            this.brandList = [];
            this.contentList = [];
            if (this.isloading) {
                try {
                    this.queryStr = this.$front.setSearchUrl(this);
                    this.$http.get("/search/search.jsp?" + this.queryStr).then((result) => {
                        if (result.ReturnMsg == "success") {
                            if (result.Data[0].Count == 0) {
                                this.goodsLen = result.Data[0].TotalCount;
                                this.brandLen = result.Data[1].TotalCount;
                                this.contentLen = result.Data[2].TotalCount;

                                this.isloading = false;
                                $state.complete();
                            } else {
                                let list = result.Data[0].Result;
                                this.goodsLen = result.Data[0].TotalCount;
                                this.brandLen = result.Data[1].TotalCount;
                                this.contentLen = result.Data[2].TotalCount;
                                this.pagingData.listTotal = result.Data[0].TotalCount;
                                let tempList = [];
                                list.forEach(element => {
                                    tempList.push(element.goodsno);
                                });
                                let param = {
                                    goodslist: tempList,
                                    iskeep: true,
                                    reqname: this.$route.name
                                }
                                param = Object.assign(param, this.pagingData);
                                this.$http.post('/goods/filter/iswished', param).then(result => {
                                    if (result.statusCode == 200) {
                                        let goodswishlist = result.data.goodswishlist;

                                        list.forEach((item) => {
                                            //이미지 파일 셋팅
                                            item.goodscpnamt = 0;
                                            if (this.$store.state.platform == "MAC001") {
                                                item.fullpath = item.pcfullpath;
                                                item.saleamt = item.pcsaleamt;
                                                item.iscoupon = item.ispccoupon;
                                            } else if (this.$store.state.platform == "MAC002") {
                                                item.fullpath = item.mofullpath;
                                                item.saleamt = item.mosaleamt;
                                                item.iscoupon = item.ismocoupon;
                                            } else {
                                                item.fullpath = item.appfullpath;
                                                item.saleamt = item.appsaleamt;
                                                item.iscoupon = item.isappcoupon;
                                            }
                                            if (goodswishlist.filter((x) => (x.goodsno == item.goodsno)).length > 0) {
                                                item.iswished = "T";
                                            } else {
                                                item.iswished = "F";
                                            }
                                            item.brandname = this.$util.isNull(item.brandenname) ? item.brandname : item.brandenname;
                                        });
                                        this.goodsList = [...this.goodsList || [], ...list];
                                        if (this.goodsList.length == this.pagingData.listTotal) {
                                            this.isloading = false;
                                            $state.complete();
                                        }
                                        this.isloading = true;
                                        this.pagingData.currentPage += 1;
                                        $state.loaded();
                                    } else {
                                        this.$util.debug("상품 좋아요 여부 조회 실패");
                                        this.isloading = false;
                                        $state.complete();
                                    }
                                })
                            }
                        }
                    })
                } catch (error) {
                    this.$eventBus.$emit("alert", "알림", "목록 조회에 실패했습니다.");
                }
            }
        },
        /************************
        * 스크롤 페이징 처리
        ************************/
        getBrandList($state) {
            this.$util.debug("getBrandList....");
            this.goodsList = [];
            this.contentList = [];
            if (this.isloading) {
                this.queryStr = this.$front.setSearchUrl(this);
                this.$http.get("/search/search.jsp?" + this.queryStr).then((result) => {
                    if (result.ReturnMsg == "success") {
                        if (result.Data[1].Count == 0) {
                            this.goodsLen = result.Data[0].TotalCount;
                            this.brandLen = result.Data[1].TotalCount;
                            this.contentLen = result.Data[2].TotalCount;

                            this.isloading = false;
                            $state.complete();
                        } else {
                            this.goodsLen = result.Data[0].TotalCount;
                            this.brandLen = result.Data[1].TotalCount;
                            this.contentLen = result.Data[2].TotalCount;

                            this.brandList = [...this.brandList || [], ...result.Data[1].Result];

                            this.isloading = true;
                            this.pagingData.currentPage += 1;
                            $state.loaded();
                        }
                    }
                })
            }
        },
        /************************
        * 스크롤 페이징 처리
        ************************/
        getContentsList($state) {
            this.brandList = [];
            this.goodsList = [];
            if (this.isloading) {
                this.queryStr = this.$front.setSearchUrl(this);
                this.$http.get("/search/search.jsp?" + this.queryStr).then((result) => {
                    if (result.ReturnMsg == "success") {
                        if (result.Data[2].Count == 0) {
                            this.goodsLen = result.Data[0].TotalCount;
                            this.brandLen = result.Data[1].TotalCount;
                            this.contentLen = result.Data[2].TotalCount;

                            this.isloading = false;
                            $state.complete();
                        } else {
                            this.goodsLen = result.Data[0].TotalCount;
                            this.brandLen = result.Data[1].TotalCount;
                            this.contentLen = result.Data[2].TotalCount;

                            this.contentList = [...this.contentList || [], ...result.Data[2].Result];

                            this.isloading = true;
                            this.pagingData.currentPage += 1;
                            $state.loaded();
                        }
                    }
                })
            }
        },
        /************************
         * 연관검색어 목록 조회
         ************************/
        getRelateSearch() {
            let param = {
                query: this.$route.query.content,
                target: "recommend",
                label: "TOTAL",
            };
            let str = "query=" + encodeURIComponent(`${param.query}`) + "&target=" + `${param.target}` + "&label=" + `${param.label}`;
            this.$http.get("/search/popword/popword.jsp?" + str).then((result) => {
                this.relatedList = this.$util.isNull(result.Data.Word) ? [] : result.Data.Word;
                if (this.relatedList.length == 0) {
                    /******************************
                    * 자동완성 목록 조회처리
                    ******************************/
                    let param = {
                        query: this.$route.query.content,  //검색어
                        target: "common",   //통합검색(고객의 요건에 따라 자동완성 대상값은 추가할 수 있음)
                        convert: "fw"       //전후방일치값 구분(fw:전방일치, rw:후방일치)
                    }
                    let str = "query=" + encodeURIComponent(`${param.query}`) + "&target=" + `${param.target}` + "&convert=" + `${param.convert}`;
                    this.$http.get("/search/ark/ark_trans.jsp?" + str).then((result) => {
                        let list1 = result.result[0].totalcount > 0 ? result.result[0].items : []; //convert가fw일경우 전방
                        let list2 = result.result[1].totalcount > 0 ? result.result[1].items : []; //convert가fw일경우 후방
                        //전방
                        //this.relatedList = list1;
                        this.relatedList = Object.assign(list1, list2);
                    }).catch((error) => {
                        console.error(error);
                    });
                }
            }).catch((error) => {
                console.error(error);
            });
        },
        /************************
         * 헤더 inputBox 검색처리
         ************************/
        setSearch(content) {
            this.$parent.setSearch(content);
        },
        /************************
         * 필터정보 가져오기
         ************************/
        getReturnFilter() {
            this.$front.getFilterInfo(this);
            this.initData();
        },
        /************************
         * 필터정보 초기화
         ************************/
        getFilterInit() {
            this.$router.push({ name: this.$route.name, query: { content: this.$route.query.content }, params: { init: true } });
        },
        /************************
         * 필터 모달
         ************************/
        openFilterModal() {
            this.$eventBus.$emit("showModal", SearchFilter, "searchFilterModal", null);
        },
        /**************************
         * 필터 항목 삭제
         *************************/
        delFilter(item) {
            this.$front.delFilter(this, item);
            this.pagingData.currentPage = 1;
            //this.initData();
            this.filterRouterChange();
        },
        /**********************
       * 필터 변경으로 인한 페이지이동
       *********************/
        filterRouterChange() {
            this.$front.setSearchData(this);
            const searchStr = encodeURIComponent(JSON.stringify(this.searchData));
            this.$router.push({ name: this.$route.name, query: { content: this.$route.query.content, search: searchStr }, params: { init: true } });
        },
    },
    created() {
        if (this.$route.params.init) {
            this.$storage.removeSessionStorage('param-' + this.$route.name);
        } else {
            const param = this.$storage.getSessionStorage('param-' + this.$route.name);
            if (!this.$util.isEmpty(param)) {
                this.pagingData.currentPage = param.currentPage;
                this.pagingData.init = "T";
            }
        }
        //카테고리조회
        this.queryStr = this.$front.setFilterSearchUrl(this);
        this.$http.get("/search/search.jsp?" + this.queryStr).then((result) => {
            if (result.ReturnMsg == "success") {
                /******************
                 * 필터 가격 지정
                 *****************/
                this.initMinPrice = parseInt(result.Data[0].MinPrice);
                this.initMaxPrice = parseInt(result.Data[0].MaxPrice);
                this.setPriceValue.min = parseInt(result.Data[0].MinPrice);
                this.setPriceValue.max = parseInt(result.Data[0].MaxPrice);
                this.setPriceValue.interval = (this.setPriceValue.max - this.setPriceValue.min) / 10;
                this.$front.getPriceList(this, 5);
                /******************
                 * 카테고리 목록  셋팅
                 *****************/
                let cateList = result.Data[0].CategoryGroup[0].cateidx[0].Category;
                /******************
                 * 브랜드 목록 셋팅
                 *****************/
                let brandList = result.Data[0].CategoryGroup[1].brandidx[0].Category;
                /******************
                 * 별점 목록 셋팅
                 *****************/
                let reviewList = result.Data[0].CategoryGroup[2].reviewavg[0].Category;
                /**************************************
                 * 리뷰 필터 셋팅
                 ***************************************/
                reviewList = reviewList.sort(
                    (a, b) => Number(a.Name) - Number(b.Name)
                );
                this.ratingList = [];
                reviewList.forEach((item) => {
                    let rating = Number(item.Name);
                    switch (rating) {
                        case 0:
                            //this.ratingList.push({'rating':rating, 'name':"☆☆☆☆☆"});
                            break;
                        case 1:
                            this.ratingList.push({ rating: rating, name: "★☆☆☆☆" });
                            break;
                        case 2:
                            this.ratingList.push({ rating: rating, name: "★★☆☆☆" });
                            break;
                        case 3:
                            this.ratingList.push({ rating: rating, name: "★★★☆☆" });
                            break;
                        case 4:
                            this.ratingList.push({ rating: rating, name: "★★★★☆" });
                            break;
                        case 5:
                            this.ratingList.push({ rating: rating, name: "★★★★★" });
                            break;
                        default:
                            break;
                    }
                });
                /******************
                 * 색상 목록 셋팅
                 *****************/
                // this.colorList = [];
                // let colorList = result.Data[0].CategoryGroup[3].colortype[0].Category;
                // colorList.forEach((item) => {
                //     item.colortype = item.Name;
                // });
                /******************
                 * 혜택 목록 셋팅
                 *****************/
                this.benefitList = [];
                let isdelivList =
                    result.Data[0].CategoryGroup[4].isdelivfree[0].Category;
                let ispccouponList =
                    result.Data[0].CategoryGroup[5].ispccoupon[0].Category;
                let ismocouponList =
                    result.Data[0].CategoryGroup[6].ismocoupon[0].Category;
                let isappcouponList =
                    result.Data[0].CategoryGroup[7].isappcoupon[0].Category;
                let iscouponList =
                    this.platform == "MAC001"
                        ? ispccouponList
                        : this.platform == "MAC002"
                            ? ismocouponList
                            : isappcouponList;

                if (isdelivList.filter((x) => x.Name == "T").length > 0) {
                    this.benefitList.push({ id: "freeDeli", name: "무료배송" });
                }
                if (iscouponList.filter((x) => x.Name == "T").length > 0) {
                    this.benefitList.push({ id: "goodscoupon", name: "상품쿠폰" });
                }
                if (this.benefitList.length > 1 || this.ratingList.length > 1 || cateList.length > 1 || brandList.length > 1) {
                    this.isFilter = true;
                }else {
                    this.isFilter = false;
                }
            } else {
                this.$eventBus.$emit("alert", "알림", "검색어 조회에 실패했습니다.");
            }
        });
    },
    mounted() {
        this.getRelateSearch();
        if (this.$util.isBlank(this.$route.query.search)) {
            this.$front.setSearchData(this);
            this.initData();
        } else {
            const searchStr = decodeURIComponent(this.$route.query.search);
            this.searchData = JSON.parse(searchStr);
            //this.pagingData.currentPage = this.$util.isNull(this.searchData.page) ? this.pagingData.currentPage : this.searchData.page;
            this.sortActive = this.$util.isEmpty(this.searchData.sort) ? this.sortActive : this.searchData.sort;
            this.setMaxPrice = this.$util.isNull(this.searchData.maxprice) ? null : this.searchData.maxprice;
            this.setMinPrice = this.$util.isNull(this.searchData.minprice) ? null : this.searchData.minprice;
            this.setBenefitList = this.$util.isEmpty(this.searchData.benefitList) ? [] : this.searchData.benefitList;
            this.setColorList = this.$util.isEmpty(this.searchData.colorList) ? [] : this.searchData.colorList;
            this.setBrandList = this.$util.isEmpty(this.searchData.fBrandList) ? [] : this.searchData.fBrandList;
            this.setRatingList = this.$util.isEmpty(this.searchData.ratingList) ? [] : this.searchData.ratingList;
            this.setFilterList = this.$util.isEmpty(this.searchData.setFilterList) ? [] : this.searchData.setFilterList;
            this.setPriceList = this.$util.isEmpty(this.searchData.setPriceList) ? [] : this.searchData.setPriceList;
            this.setCateList = this.$util.isEmpty(this.searchData.setCateList) ? [] : this.searchData.setCateList;
            this.setGiconList = this.$util.isEmpty(this.searchData.giconList) ? [] : this.searchData.giconList;
            this.setRecomList = this.$util.isEmpty(this.searchData.recomList) ? [] : this.searchData.recomList;
            if (!(this.$util.isNull(this.searchData.maxprice) || this.$util.isNull(this.searchData.minprice))) {
                this.setPriceValue.currentValue[0] = this.searchData.minprice;
                this.setPriceValue.currentValue[1] = this.searchData.maxprice;
            }
            this.initData();
        }
    },
    updated() {
        this.$nextTick(() => {
            if (this.goodsList.length > 0 && this.pagingData.init == 'T') {
                this.$eventBus.$emit('scrollTo', this.$route.name);
                this.pagingData.init = 'F';
            }
        })
    }
};