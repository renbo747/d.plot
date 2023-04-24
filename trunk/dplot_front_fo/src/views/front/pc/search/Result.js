import { swiper, swiperSlide } from "vue-awesome-swiper";
import VueSlider from "vue-slider-component";
import BrandPlusModal from '@views.pc/shop/popup/BrandPlusModal.vue';

export default {
    components: {
        swiper,
        swiperSlide,
        VueSlider,
    },
    data() {
        return {
            showTab: false,
            searchKeyword: this.$util.isNull(this.$route.query.content)?"":this.$route.query.content,
            selectOptionData: "",
            sortActive: 'best',
            sortList: [
                {
                    value: "best",
                    label: "인기순"
                },
                {
                    value: "new",
                    label: "최신순",
                },
                {
                    value: "highscore",
                    label: "평점높은순"
                },
                {
                    value: "lowscore",
                    label: "평점낮은순"
                },
                {
                    value: "highprice",
                    label: "가격높은순"
                },
                {
                    value: "lowprice",
                    label: "가격낮은순"
                },
            ],
            pagingData: {
                currentPage: 1,  // 현재 페이지
                listTotal: 0,   // 조회목록 전체 수
                listCnt: this.$route.name == "goods" ? 9 : this.$route.name == "brand" ? 12 : 10,   // 한페이지에 노출할 목록수
                init: 'F'
            },
            //
            listData: [],
            productTabBrandData: [],
            brandOption: {
                observeParents: true,
                observer: true,
                pagination: {
                    el: ".swiper-pagination",
                },
            },
            benefitList: [],
            priceData: [],
            // 브랜드탭 데이터
            brandData: [],
            // 컨텐츠탭 데이터
            contentsData: [],
            // 모달 데이터
            categoryData: [],
            ratingList: [],
            relatedList: [],
            giconList: [], // 아이콘설정목록
            recomList: [], // 추천유형목록
            setPriceValue: {
                currentValue: [0, 10000],
                min: 0,
                max: 100000,
                interval: 1000,
            },
            searchData: {},
            goodsList: [], //상품목록
            brandList: [], //브랜드목록
            contentList: [], //컨텐츠목록
            goodsLen: 0, //상품전체수
            brandLen: 0, //브랜드전체수
            contentLen: 0, //컨텐츠전체수
            platform: window.sessionStorage.getItem("platform"),
            membertype: "DMT001", //회원구분(비로그인:일반회원,브론즈)
            memlvtype: "MDL001", //회원레벨(비로그인:일반회원,브론즈)
            setCateidx: "",
            setBrandList: [],  //선택 필터브랜드목록
            setColorList: [],  //선택 필터커러목록
            setBenefitList: [],//선택 필터혜택목록
            setRatingList: [], //선택 필터별점목록
            setFilterList: [], //선택 필터목록
            setPriceList: [],  //선택 필터가격목록
            setCateList: [],   //선택 필터카테고리목록
            setGiconList: [], // 선택 아이콘설정 리스트
            setRecomList: [], // 선택 추천유형 리스트
            queryStr: "",
            cateList: [],  //필터 카테고리목록
            fBrandList: [], //필터 브랜드목록
            colorList: [], //필터 색상목록
            brandGroup: [],//필터 브랜드 목록(모바일용)
            priceList: [], //가격목록
            autoList: [],
            priceInfo: {},
            isEnd:false,
            initMaxPrice:0,
            initMinPrice:0,
            dragEnd:false,
            brandShowMax:10,
        };
    },
    methods: {
        /*****************************
         * 페이지 컨버터
         ****************************/
        changePage(page) {
            this.pagingData.currentPage = page;
            this.filterRouterChange();
        },
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
            this.$router.push({ name: name, query: { content: this.$route.query.content, search: searchStr },params:{init:true} });
        },
        /************************
         * 목록조회 컨버터
         ************************/
        getListConverter() {
            if (this.$route.name == "goods") {
                this.getGoodsList();
            } else if (this.$route.name == "brand") {
                this.getBrandList();
            } else {
                this.getContentsList();
            }
        },
        /************************
         * 상품목록 조회
         ************************/
        getGoodsList() {
            this.queryStr = this.$front.setSearchUrl(this);
            this.isEnd = false;
            this.$http.get("/search/search.jsp?" + this.queryStr).then((result) => {
                this.goodsLen = result.Data[0].TotalCount;
                this.brandLen = result.Data[1].TotalCount;
                this.contentLen = result.Data[2].TotalCount;
                if (this.goodsLen > 0) {
                    let tempList = [];
                    let list = result.Data[0].Result;
                    list.forEach(element => {
                        tempList.push(element.goodsno);
                    });
                    let param = {
                        goodslist:tempList,
                        iskeep: true,
                        reqname : this.$route.name
                    }
                    param = Object.assign(param, this.pagingData);
                    /******************
                     * 상품 좋아요 정보 조회
                     *****************/
                    this.$http.post('/goods/filter/iswished', param).then(result => {
                        if (result.statusCode == 200) {
                            this.$util.debug("상품 좋아요 여부 조회 성공");
                            this.$util.debug(result);
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
                                item.brandname = this.$util.isNull(item.brandenname)?item.brandname:item.brandenname;
                            });
                            this.pagingData.listTotal = this.goodsLen;
                            this.goodsList = list;
                        }
                        this.isEnd=true;
                    })
                }else{
                    this.isEnd=true;
                }
            })
        },
        /************************
        * 브랜드목록 조회
        ************************/
        getBrandList() {
            this.queryStr = this.$front.setSearchUrl(this);
            this.$http.get("/search/search.jsp?" + this.queryStr).then((result) => {
                if (result.ReturnMsg == "success") {
                    this.goodsLen = result.Data[0].TotalCount;
                    this.brandLen = result.Data[1].TotalCount;
                    this.contentLen = result.Data[2].TotalCount;

                    if (this.brandLen > 0) {
                        this.brandList = result.Data[1].Result;
                        this.pagingData.listTotal = this.brandLen;
                    }
                    this.isEnd=true;
                }else{
                    this.isEnd=true;
                }
            })
        },
        /************************
        * 컨텐츠 목록 조회
        ************************/
        getContentsList() {
            this.queryStr = this.$front.setSearchUrl(this);
            this.$http.get("/search/search.jsp?" + this.queryStr).then((result) => {
                if (result.ReturnMsg == "success") {
                    this.goodsLen = result.Data[0].TotalCount;
                    this.brandLen = result.Data[1].TotalCount;
                    this.contentLen = result.Data[2].TotalCount;

                    if (this.contentLen > 0) {
                        this.contentList = result.Data[2].Result;
                        this.pagingData.listTotal = this.contentLen;
                        this.contentList.forEach(item => {
                            item.label = item.catename;
                            item.title = item.subject;
                            item.text = item.desc;
                        });
                    }
                    this.isEnd=true;
                }else{
                    this.isEnd=true;
                }
            })
        },
        /************************
         * 헤더 inputBox 검색처리
         ************************/
        setSearch(content) {
            this.$refs.search.searchStart(content);
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
                return "goods@reviewavg/DESC"
            } else if (this.sortActive == "lowscore") {
                return "goods@reviewavg/ASC"
            } else if (this.sortActive == "highprice") {
                return this.platform == "MAC001"
                    ? "goods@pcsaleamt/DESC" : this.platform == "MAC002" ? "goods@mosaleamt/DESC" : "goods@appsaleamt/DESC";
            } else {
                return this.platform == "MAC001"
                    ? "goods@pcsaleamt/ASC" : this.platform == "MAC002" ? "goods@mosaleamt/ASC" : "goods@appsaleamt/ASC";
            }
        },
        /****************************
         * 선택 옵션 리스트 셋팅 처리
         ****************************/
        setFilter(target, value) {
            this.$front.setFilter(this, target, value);
            this.pagingData.currentPage = 1;
            this.filterRouterChange();
        },
        /**************************
        * 필터 항목 삭제
        *************************/
        delFilter(item) {
            this.$front.delFilter(this, item);
            this.filterRouterChange();
        },
        /************************
         * 필터정보 초기화
         ************************/
        getFilterInit() {
            this.pagingData.currentPage = 1;
            this.$router.push({ name: this.$route.name, query: { content: this.$route.query.content } ,params:{init:true}});
            //this.$front.getFilterInfoInit(this);
        },
        /*************************
         * 가격 라디오 버튼 클릭에 따른 가격바 셋팅
         **************************/
        setPrice(list) {
            this.$nextTick(() => {
                let valueList = [];
                valueList[0] = list.startPrice;
                if (this.$util.isNull(list.endPrice)) {
                    valueList[1] = list.startPrice;
                } else {
                    valueList[1] = list.endPrice;
                }
                this.$refs.slider.setValue(valueList);
                this.updateValue(this.setPriceValue.currentValue);
            });
        },
        /*************************
        * 가격바 변경시 가격라디오에 해당하는 값
        * 없으면 라디오 리스트 비우기
        **************************/
        updateValue(priceList) {
            let isSame = false;
            let price = "";
            this.priceList.forEach((item) => {
                if (item.startPrice == priceList[0] && item.endPrice == priceList[1]) {
                    isSame = true;
                    price = this.$util.maskComma(item.startPrice) + "원" + "~" + this.$util.maskComma(item.endPrice) + "원"
                }
            });
            if (!isSame) {
                this.setPriceList = [];
                this.setFilter('setPriceList', this.$util.maskComma(priceList[0]) + "원" + "~" + this.$util.maskComma(priceList[1]) + "원");
            } else {
                this.setFilter('setPriceList', price);
            }
            this.dragEnd = true;
        },
        /************************
         * 연관검색어 목록 조회
         ************************/
        getRelateSearch() {
            let param = {
                query: this.$route.query.content,
                target: "recommend",
                label: "TOTAL"
            }
            let str = "query=" + encodeURIComponent(`${param.query}`) + "&target=" + `${param.target}` + "&label=" + `${param.label}`;
            this.$http.get('/search/popword/popword.jsp?' + str).then((result) => {
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
                        this.relatedList = Object.assign(list1, list2);
                        //this.relatedList = list1;
                    }).catch((error) => {
                        console.error(error);
                    });
                }
            });
        },
        /**********************
        * 브랜드 더보기 결과 처리
        *********************/
        // addBrandReturn() {
        //     let brandfilterinfo = this.$store.state.brandfilterinfo;
        //     this.setBrandList = brandfilterinfo.setBrandList;
        //     this.setFilterList = brandfilterinfo.setFilterList;
        //     this.fBrandList = brandfilterinfo.fBrandList;
        // },
        /**********************
        * 브랜드 추가 모달 오픈
        *********************/
        addBrandList() {
            this.$eventBus.$emit('showModal', BrandPlusModal, 'brandPlusModal',{ fBrandList: this.fBrandList });
            // let param = {
            //     setBrandList: this.setBrandList,
            //     setFilterList: this.setFilterList,
            //     fBrandList: this.fBrandList
            // }
            // this.$store.commit("brandfilterinfo", param);

            // this.$eventBus.$emit('showModal', BrandPlusModal, 'brandPlusModal', { cateidx: this.setCateidx }, this.addBrandReturn);
        },

        /************************
         *  정렬 선택 이벤트 처리
         ************************/
        sortSelect(value) {
            this.sortActive = value;
            this.pagingData.currentPage = 1;
            this.filterRouterChange();
        },
        getSearchResult() {
            // 패이지 중복 이동 방지
            if (this.$route.name === "search") {
                this.$router.push("/search/result");
            }
            this.searchInput = "";
            this.isKeyup = false;
        },
        handleKeyword(event) {
            if (event.keyCode !== 8) {
                this.isKeyup = true;
            }
            if (event.keyCode === 13) {
                this.$router.push("/search/result");
                this.searchInput = "";
                this.isKeyup = false;
            }
        },
        /**********************
       * 필터 변경으로 인한 페이지이동
       *********************/
        filterRouterChange() {
            this.$front.setSearchData(this);
            const searchStr = encodeURIComponent(JSON.stringify(this.searchData));
            this.$router.push({ name: this.$route.name, query: { content: this.$route.query.content, search: searchStr },params:{init:true} });
        },
        /************************
         * 카테고리 조회
         ***********************/
        getFilterInfo() {
            this.queryStr = this.$front.setFilterSearchUrl(this);
            this.$http.get("/search/search.jsp?" + this.queryStr).then((result) => {
                if (result.ReturnMsg == "success") {
                    /******************
                     * 필터 가격 지정
                     *****************/
                    this.initMinPrice = result.Data[0].MinPrice;
                    this.initMaxPrice = result.Data[0].MaxPrice;
                    this.setPriceValue.min = parseInt(result.Data[0].MinPrice);
                    this.setPriceValue.max = parseInt(result.Data[0].MaxPrice)== 0 ? 1000:parseInt(result.Data[0].MaxPrice);
                    this.setPriceValue.interval = (this.setPriceValue.max - this.setPriceValue.min) / 10;

                    if (this.$util.isNull(this.searchData.maxprice) || this.$util.isNull(this.searchData.minprice)) {
                        this.setPriceValue.currentValue[0] = parseInt(result.Data[0].MinPrice);
                        this.setPriceValue.currentValue[1] = parseInt(result.Data[0].MaxPrice);
                    }else {
                        this.setPriceValue.currentValue[0] = this.searchData.minprice;
                        this.setPriceValue.currentValue[1] = this.searchData.maxprice;
                    }
                    //this.$refs.slider.setValue( this.setPriceValue.currentValue);
                    this.$front.getPriceList(this, 5);

                    /******************
                     * 카테고리 목록  셋팅
                     *****************/
                    let cateList = this.$util.isNull(result.Data[0].CategoryGroup[0].cateidx[0].Category)?[]:result.Data[0].CategoryGroup[0].cateidx[0].Category;
                    
                    cateList.forEach(item => {
                        item.cateidx = Number(item.Name);
                    });
                    /******************
                     * 브랜드 목록 셋팅
                     *****************/
                    let brandList = result.Data[0].CategoryGroup[1].brandidx[0].Category;
                    brandList.forEach(item => {
                        item.idx = Number(item.Name);
                    });
                    /******************
                     * 별점 목록 셋팅
                     *****************/
                    let reviewList = result.Data[0].CategoryGroup[2].reviewavg[0].Category;
                    /**************************************
                    * 리뷰 필터 셋팅
                    ***************************************/
                    reviewList = reviewList.sort((a, b) => Number(a.Name) - Number(b.Name));
                    reviewList.forEach(item => {
                        let rating = Number(item.Name);
                        switch (rating) {
                            case 0:
                                //this.ratingList.push({'rating':rating, 'name':"☆☆☆☆☆"});
                                break;
                            case 1:
                                this.ratingList.push({ 'rating': rating, 'name': "★☆☆☆☆" });
                                break;
                            case 2:
                                this.ratingList.push({ 'rating': rating, 'name': "★★☆☆☆" });
                                break;
                            case 3:
                                this.ratingList.push({ 'rating': rating, 'name': "★★★☆☆" });
                                break;
                            case 4:
                                this.ratingList.push({ 'rating': rating, 'name': "★★★★☆" });
                                break;
                            case 5:
                                this.ratingList.push({ 'rating': rating, 'name': "★★★★★" });
                                break;
                            default:
                                break;
                        }
                    });
                    /******************
                     * 색상 목록 셋팅
                     *****************/
                    let colorList = result.Data[0].CategoryGroup[3].colortype[0].Category;
                    colorList.forEach(item => {
                        item.colortype = item.Name;
                    });
                    /******************
                     * 혜택 목록 셋팅
                     *****************/
                    let isdelivList = result.Data[0].CategoryGroup[4].isdelivfree[0].Category;
                    let ispccouponList = result.Data[0].CategoryGroup[5].ispccoupon[0].Category;
                    let ismocouponList = result.Data[0].CategoryGroup[6].ismocoupon[0].Category;
                    let isappcouponList = result.Data[0].CategoryGroup[7].isappcoupon[0].Category;
                    let iscouponList = this.platform == "MAC001" ? ispccouponList : this.platform == "MAC002" ? ismocouponList : isappcouponList;

                    if (isdelivList.filter((x) => (x.Name == 'T')).length > 0) {
                        this.benefitList.push({ id: "freeDeli", "name": "무료배송" });
                    }
                    if (iscouponList.filter((x) => (x.Name == 'T')).length > 0) {
                        this.benefitList.push({ id: "goodscoupon", "name": "상품쿠폰" });
                    }
                    /********************************
                     * 카테고리, 브랜드, 색상 목록 셋팅
                     ********************************/
                    let param = {
                        catelist: cateList,
                        brandlist: brandList,
                        colorlist: colorList
                    }
                    this.$http.post("/shop/search/filter", param).then((result) => {
                        if (result.statusCode == 200) {
                            if (result.data.memberinfo.authType == "member") {
                                this.membertype = result.data.memberinfo.membertype;
                                this.memlvtype = result.data.memberinfo.memlvtype;
                            } else {
                                this.memlvtype = "MDL001";
                                this.membertype = "DMT001";
                            }
                            this.cateList = result.data.catelist;
                            this.fBrandList = result.data.brandlist;
                            for (let i = 0; i < this.fBrandList.length; i++) {
                                if (this.setBrandList.length > 0) {
                                    if (this.setBrandList.filter((x) => x == this.fBrandList[i].idx).length > 0) {
                                        this.fBrandList[i].sort = 0;
                                    } else {
                                        this.fBrandList[i].sort = 1;
                                    }
                                }
                            }
        
                            if (this.fBrandList.length > 0) {
                                this.fBrandList = this.fBrandList.sort((a, b) => a.sort - b.sort);
        
                                let len = this.fBrandList.length <= this.brandShowMax?this.fBrandList.length:this.brandShowMax;
                                for (let i = 0; i < len; i++) {
                                    this.fBrandList[i].show = true;
                                }
                            }
                            /***********************************
                             * 아이콘설정 목록 셋팅
                             ***********************************/
                            this.giconList = result.data.giconlist;
                            /***********************************
                             * 추천유형 목록 셋팅
                             ***********************************/
                            this.recomList = result.data.recomlist;
                            this.colorList = result.data.colorlist;
                            this.getListConverter();
                        } else {
                            this.$eventBus.$emit("alert", "알림", "검색어 조회에 실패했습니다.");
                        }
                    });
                } else {
                    this.$eventBus.$emit("alert", "알림", "검색어 조회에 실패했습니다.");
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
                this.pagingData.init = "T";
            }
        }
    },
    mounted() {
        //연관검색어 조회
        this.getRelateSearch();
        if (this.$util.isBlank(this.$route.query.search)) {
            this.$front.setSearchData(this);
        } else {
            const searchStr = decodeURIComponent(this.$route.query.search);
            this.searchData = JSON.parse(searchStr);

            this.pagingData.currentPage = this.$util.isNull(this.searchData.page) ? this.pagingData.currentPage : this.searchData.page;
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
        }
        this.getFilterInfo();
    },
    updated(){
        this.$nextTick(() => {
            this.dragEnd = false;
            if (this.goodsList.length > 0 || this.brandList.length > 0) {
                this.$eventBus.$emit('scrollTo', this.$route.name, (flag)=>{
                    if(!flag && this.dragEnd) {
                        window.scrollTo(0,0);        
                    }
                });
            } else if(this.dragEnd) {
                window.scrollTo(0,0);
            }else {
                return;
            }
        })
      }
};