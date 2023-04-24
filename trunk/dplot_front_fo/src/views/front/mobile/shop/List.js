import { swiper, swiperSlide } from "vue-awesome-swiper";
import VueSlider from "vue-slider-component";
import "vue-slider-component/theme/default.css";
import ListFilter from './popup/ListFilter.vue'
import InfiniteLoading from "vue-infinite-loading";

export default {
  components: {
    swiper,
    swiperSlide,
    VueSlider,
    InfiniteLoading
  },
  data() {
    return {
      idx: 0,
      isloading: false,
      cateTitle: "",
      siblingcates : [],
      activeCate : "",
      cateMap : new Map,
      infiniteId: + new Date(),
      setCateidx: 0,
      searchData: {
        idx: "",
        parent: "",
        platform: window.sessionStorage.getItem('platform')
      },
      parentInfo: this.$store.state.parentInfo,
      cateList: this.$store.state.cateList,
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
      goodsList: [],
      setPriceValue: this.$store.state.setPriceValue,
      setFilterList: [],  //선택 필터옵션
      sortActive: "best",      //선택 정렬순
      setCateList: [],    //선택 카테고리 리스트
      setBrandList: [],   //선택 브랜드 리스트
      setColorList: [],   //선택 색상 리스트
      setBenefitList: [], //선택 혜택 리스트
      setPriceList: [],   //선택 가격 리스트
      setRatingList: [],  //선택 별점 리스트
      setGiconList: [], 
      setRecomList:[],
      pagingData: {
        currentPage: 1,  // 현재 페이지
        listTotal: 0,   // 조회목록 전체 수
        listCnt: 10,   // 한페이지에 노출할 목록수
        init: 'F'
      },
      initMinPrice: 0,
      initMaxPrice: 0,
      priceInfo: {},
      ratingList: [],
      benefitList: [],
      brandGroup: [],
      fBrandList: [],
      colorList: [],
      priceList: [],
      giconList: [],
      recomList: [],
      isFilter: false,
    };
  },
  methods: {
    /************************
     * 페이징 데이터 초기화
     ***********************/
    initPagingData() {
      this.$util.debug("initPagingData()...");
      //this.pagingData.currentPage = 1;  // 현재 페이지
      this.infiniteId += 1;
      this.isloading = true;
      this.goodsList = [];
    },
    getCateListDepth1() {
      this.$http.post("/category/list", { depth: 1 }).then((result) => {
      if (result.statusCode == 200) {
          this.$util.debug(result.data);
          this.siblingcates = result.data.catelist;
          this.activeCate = this.cateTitle;
        }
      });
    },
    /**************************
     * 조회조건 리셋
     **************************/
    getFilterInit() {
      this.$router.push({ name: 'shop-list', params: { idx: this.$route.params.idx, init: true } });
    },
    cateChagne() {      
      const cateIdx = this?.siblingcates.find(el => el?.catename == this?.activeCate)?.cateidx;
      if(!this.$route.params.idx) return;
      if(!cateIdx) return;
      if(this.$route.params.idx !== cateIdx){
        this.$router.push(`/shop/list/${cateIdx}`)
        console.log('%c cateChagne', 'color: #00ff00', cateIdx);
      }
    },
    /**************************
     * 필터 항목 삭제
     *************************/
    delFilter(item) {
      this.$front.delFilter(this, item);
      this.filterRouterChange();
    },
    /***************************
     * 카테고리 탭 선택시 이벤트 처리
     ****************************/
    changeCate(idx) {
      this.$router.push({ name: 'shop-list', params: { idx: idx, init: true } });
    },
    /**********************
     * 정렬 selectBox 변경시 이벤트 처리
     *********************/
    handleSelect() {
      this.$util.debug("handleSelect()..");
      this.initPagingData();
    },
    /*********************
     * 상품목록 조회
     *********************/
    infiniteHandler($state) {
      this.$util.debug("infiniteHandler()...");
      if (this.isloading) {
        //this.setSearchData();
        let param = Object.assign(this.pagingData, this.searchData);
        param.iskeep = true;
        param.reqname = this.$route.name;
        this.$http.post('/goods/list', param).then(result => {
          if (result.statusCode == 200) {
            this.$util.debug("상품목록조회");
            this.$util.debug(result.data);
            if (result.data.goodslist.length == 0) {
              this.isloading = false;
              $state.complete();
            } else {
              this.isloading = true;
              this.pagingData.currentPage += 1;
              let list = result.data.goodslist;
              list.forEach(item => {
                item.saleamt = item.saleamt - item.goodscpnamt;
              });
              this.goodsList.push(...list);
              this.pagingData.listTotal = result.data.listtotal;
              if (this.goodsList.length == this.pagingData.listTotal) {
                this.isloading = false;
                $state.complete();
              }
              this.isloading = true;
              $state.loaded();
            }
          }
        });
      }
    },
    /********************
     * 모달 열기
     *******************/
    openModal(modalId) {
      this.$eventBus.$emit('showModal', ListFilter, modalId, null);
    },
    /**********************
     * 정렬 selectBox 변경시 이벤트 처리
     *********************/
    setSortActive() {
      this.filterRouterChange();
    },
    /**********************
     * 필터 변경으로 인한 페이지이동
     *********************/
    filterRouterChange() {
      this.$front.setSearchData(this);
      const searchStr = encodeURIComponent(JSON.stringify(this.searchData));
      this.$router.push({ name: this.$route.name, query: { search: searchStr }, params: { idx: this.$route.params.idx, init: true } });
    }
  },
  /***************************************
    * 카테고리 목록 조회
    **************************************/
  beforeCreate() {
    /************************
     * 카테고리정보 조회
     ***********************/
    this.$http.post('/shop/filter/list', { idx: this.$route.params.idx }).then(result => {
      if (result.statusCode == 200) {
        this.$store.commit("onSubHeaderOption", {
          // title: result.data.parentinfo.name,
          title: 'CATEGORY',
          searchIcon: true,
          cartIcon: true,
          homeIcon: false,
        });
        this.cateTitle = result.data.parentinfo.name;
        this.cateList = result.data.catelist;
        this.parentInfo = result.data.parentinfo;
        let cate = {
          cateidx: this.parentInfo.idx,
          catename: "전체"
        }
        this.cateList = result.data.catelist;
        this.$nextTick(() => {
          this.cateList.unshift(cate);
          this.$store.commit("parentInfo", this.parentInfo);
          this.$store.commit("cateList", this.cateList);
        })
        /***********************************
         * 색상 셋팅
         ***********************************/
        //this.colorList = result.data.colorlist;
        /***********************************
         * 브랜드 목록 셋팅
         ***********************************/
        this.fBrandList = result.data.brandlist;
        let deepList = [...this.fBrandList];
        //리스트 N개씩 묶음처리
        this.brandGroup = this.$util.division(deepList, this.brandShowMax);
        /***********************************
         * 혜택 목록 셋팅
         ***********************************/
        this.benefitList = result.data.benefitlist;
        /***********************************
         * 평점 목록 셋팅
         ***********************************/
        this.ratingList = result.data.ratinglist.filter(
          (x) => x.rating > 0
        );
        /***********************************
         * 아이콘설정 목록 셋팅
         ***********************************/
        this.giconList = result.data.giconlist;
        /***********************************
         * 추천유형 목록 셋팅
         ***********************************/
         this.recomList = result.data.recomlist;
        /******************
         * 필터 가격 지정
         * ****************/
        this.priceInfo = result.data.priceinfo;
        this.initMinPrice = this.priceInfo.minamt;
        this.initMaxPrice = this.priceInfo.maxamt;
        this.setPriceValue.min = this.priceInfo.minamt;
        this.setPriceValue.max = this.priceInfo.maxamt;
        this.setPriceValue.interval = (this.setPriceValue.max - this.setPriceValue.min) / 10;
        /*********************
         * 5분위 가격 목록 생성
         ********************/
        this.$front.getPriceList(this, 5);
        this.isFilter = true;
        /*if (this.ratingList.length > 1 || this.benefitList.length > 1 || (this.brandGroup.length > 0 && this.brandGroup[0].length > 1) || this.priceList.length > 1) {
          this.isFilter = true;
        } else {
          this.isFilter = false;
        }*/
      } else {
        this.$eventBus.$emit('alert', '알림', "존재하지 않는 상품 입니다.", () => {
          this.$router.replace({ name: 'shop' });
        })
      }
    });
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
    this.$util.debug("mounted()");
    if (this.$util.isBlank(this.$route.query.search)) {
      this.$front.setSearchData(this);
      this.initPagingData();
    } else {
      this.$front.queryStrToParam(this);
      this.initPagingData();
    }
    this.getCateListDepth1();
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