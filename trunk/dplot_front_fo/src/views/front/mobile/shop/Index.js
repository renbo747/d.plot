import { swiper, swiperSlide } from "vue-awesome-swiper";
import EventBanner from "@views.common/components/shop/EventBanner";
import WeeklyItemVue from './components/WeeklyItem';
import Loading from "vue-loading-overlay";
import "vue-loading-overlay/dist/vue-loading.css";

export default {
  components: {
    swiper,
    swiperSlide,
    EventBanner,
    'weekly-item' : WeeklyItemVue,
    Loading,
  },
  data() {
    return {
      isLoading01: true,
      isLoading02: true,
      isLoading03: true,
      //isLoading04: true,
      mainBannerList: [
      ],
      shopBanner1List: [],
      shopBanner2List: [],
      shopExnameList: [],
      cateList: [],
      cateSubsetList: [],
      shopEx1List: [],
      shopEx2List: [],
      shopEx2Group: [],
      shopEx3List: [],
      shopEx4List: [],
      shopEx4Group: [],
      shopEx5List: [],
      shopEx6Group: [],
      currentTab: 0,
      category1Depthlist: [],
      bestGoodsList: [],
      mzTrendList: [],
      swiperOption: { //모바일 메인베너 swiper 옵션
        slidesPerView: 1,
        loop: true,
        effect: 'slide',
        pagination: {
          el: ".swiper-pagination",
          clickable: false,
          type: "progressbar",
        },
        autoplay: {
          delay: 5000,
          disableOnInteraction: false
        },
      },
      swiperOption02: { //모바일 new-arrivals swiper 옵션
        slidesPerView: 2,
        slidesPerGroup: 2,
        spaceBetween: 10,
        effect: 'slide',
        pagination: {
          el: ".swiper-pagination",
          clickable: false,
          type: "progressbar",
        },
      },
      saleSwiperOption: {
        loop: false,
        slidesPerView: 3,
        spaceBetween: 50,
        slidesPerGroup: 3,
        pagination: {
          el: ".swiper-pagination",
          clickable: true,
        },
      },
      swiperOptionPc: {//PC 메인베너 swiper 옵션
        loop: true,
        slidesPerView: "auto",
        centeredSlides: "true",
        pagination: {
          el: ".swiper-pagination",
          type: "progressbar",
        },
        autoplay: {
          delay: 5000,
          disableOnInteraction: false
        }
      },
      productOption: { //전시2영역 swiper 옵션
        loop: false,
        spaceBetween: 20,
        pagination: {
          el: ".swiper-pagination",
          clickable: true,
        },
      },
      orderSwiperOption: {//전시2영역  pc swiper 옵션
        loop: false,
        slidesPerView: 4,
        spaceBetween: 20,
        slidesPerGroup: 4,
        pagination: {
          el: ".swiper-pagination",
          type: "progressbar",
        },
      },
      highlightSwiperOption: {//전시 4영역  pc swiper 옵션
        loop: false,
        slidesPerView: 2,
        spaceBetween: 20,
        slidesPerGroup: 2,
        pagination: {
          el: ".swiper-pagination",
        },
      },
      mdPickSwiperOption: {//전시영역 6 mo 옵션
        slidesPerView: 1,
        loop: false,
        spaceBetween: 20,
        loopFillGroupWithBlank : true,
      },
      bndOption: { //전시3영역 swiper 옵션
        loop: false,
        pagination: {
          el: ".bnd__swiper .swiper-pagination",
          type: "fraction",
        },
      },
      infiniteId: + new Date(),
    };
  },
  updated() {
    this.$nextTick(()=>{
      this.$eventBus.$emit('scrollTo', this.$route.name);
    });
  },
  methods: {
    /*******************************
     * SHOP 전시 목록 조회
     *******************************/
    f_main1(){
      this.$util.scrollToTop();
      return new Promise(resolve => {
        this.$http.post('/shop/main1', {}).then(result => {
          if (result.statusCode == 200) {
            if(result.data.mainbannerlist.length == 1) {
              this.mainBannerList = result.data.mainbannerlist.concat(result.data.mainbannerlist);
            }else {
              this.mainBannerList = result.data.mainbannerlist;
            }

            //this.category1Depthlist = result.data.category1depthlist;
            //this.currentTab = 0;
            //this.getBestGoodsList();

            this.cateList = result.data.catelist;
            this.cateSubsetList = result.data.catesubsetlist;

            this.shopExnameList = result.data.shopexnamelist;

            this.isLoading01 = true;
            resolve(); 
          }
        })
      });
    },
    f_main2(){
      return new Promise(resolve => {
        this.$http.post('/shop/main2', {}).then(result => {
          if (result.statusCode == 200) {

            this.shopBanner1List = result.data.shopbanner1list;
            this.shopBanner2List = result.data.shopbanner2list;
          
            this.shopEx1List = result.data.shopex1list;
            this.shopEx1List.forEach(item => {
              item.saleamt = item.saleamt - item.goodscpnamt
            });          
            this.shopEx2List = result.data.shopex2list;
            this.shopEx2List.forEach(item => {
              item.saleamt = item.saleamt - item.goodscpnamt
            });
            this.shopEx3List = result.data.shopex3list;
            for(let i = 0 ; i < this.shopEx3List.length ;i++) {
              for(let j = 0 ; j < this.shopEx3List[i].goodslist.length ; j++) {
                this.shopEx3List[i].goodslist[j].saleamt = this.shopEx3List[i].goodslist[j].saleamt - this.shopEx3List[i].goodslist[j].goodscpnamt;
              }
            }
            this.shopEx4List = result.data.shopex4list;
            for(let i = 0 ; i < this.shopEx4List.length ;i++) {
              for(let j = 0 ; j < this.shopEx4List[i].goodslist.length ; j++) {
                this.shopEx4List[i].goodslist[j].saleamt = this.shopEx4List[i].goodslist[j].saleamt - this.shopEx4List[i].goodslist[j].goodscpnamt;
              }
            }
            this.shopEx5List = result.data.shopex5list;
            for(let i = 0 ; i < this.shopEx5List.length ;i++) {
              for(let j = 0 ; j < this.shopEx5List[i].goodslist.length ; j++) {
                this.shopEx5List[i].goodslist[j].saleamt = this.shopEx5List[i].goodslist[j].saleamt - this.shopEx5List[i].goodslist[j].goodscpnamt;
              }
            }

            this.mzTrendList = result.data.mztrendlist;

            let mzTrendListGroup = [...this.mzTrendList];
            if (mzTrendListGroup.length > 0) {
              this.shopEx6Group = this.$util.division(mzTrendListGroup, 2);
            }

            let deepList = [...this.shopEx2List];
            if (deepList.length > 0) {
              this.shopEx2Group = this.$util.division(deepList, 6);
            }

            if (this.shopEx4List.length > 0) {
              let deepList2 = [...this.shopEx4List[0].goodslist];
              this.shopEx4Group = this.$util.division(deepList2, 4);
            }
            //if (this.mzTrendList.length > 0) {
              //this.getBestGoodsList();
            //}
            // this.$nextTick(()=>{
            //   this.$eventBus.$emit('scrollTo', this.$route.name);
            // });          
            this.isLoading03 = true;
            resolve();
          }
        })
      });
    }, 
    /*******************************
     * SHOP 베스트 상품 조회
     *******************************/
    getBestGoodsList() {
      let param = {
        //cateidx: this.category1Depthlist[this.currentTab].idx,
        limit: 6,
        isloading: false,
      }
      
      this.$http.post('/shop/bestlist', param).then(result => {
        if (result.statusCode == 200) {
          this.$util.debug("베스트 상품 목록 조회");
          this.bestGoodsList = result.data.goodslist;
          this.isLoading02 = true;
        }
      });
    },        
    goPage(name) {
      this.$router.push({ name: name });
    },
    goCateLink(list) {
      this.$util.debug(list);
      dataLayer.push({
        'event':  'click_home_category',
        'banner_title': list.name
      });
      this.$router.push({
        name: 'shop-list',
        params: { idx: list.cateidx, name: list.name }
      })
    },
    goLink(list) {
      this.$util.debug(list);
    
      // 홈 메인배너 클릭(admin 내 메인배너)
      if(list.bntype == "BNT002") {
        dataLayer.push({
          'event':  'click_home_mainbanner',
          'banner_title': list.subject
        });
      }

      // 홈 하이라이트 클릭 (admin 내 전시영역4)
      if(!this.$util.isNull(list.ex45idx)) { 
        dataLayer.push({
          'event':  'click_home_highlights',
          'banner_title': list.subject
        });
      }

      if (window.sessionStorage.getItem('platform') == "MAC001") {
        if (!this.$util.isNull(list.pclinkurl)) {
          if (list.ispcnwindow == "T") {
            window.open(list.pclinkurl, "_blank");
          } else {
            window.location.href = list.pclinkurl;
          }
        }
      } else {
        if (!this.$util.isNull(list.pclinkurl)) {
          if (list.ismonwindow == "T") {
            window.open(list.molinkurl, "_blank");
          } else {
            window.location.href = list.molinkurl;
          }
        }
      }
    },
  },
  beforeCreate(){
    let name = '';
    if (window.sessionStorage.getItem('platform') == "MAC001") {
      name = 'SHOP_POP_CLOSE_ONEDAY_PC';
    } else {
      name = 'SHOP_POP_CLOSE_ONEDAY_MO';
    }
    if (this.$util.isNull(this.$cookies.get(name))) {
      this.$http.post('/mz/popup', { isdispmagazine : 'F' })
      .then(result => {
          if(result.statusCode === 200) {
            let popuplist = result.data.popuplist;
            let check = popuplist.length === 0 ? false : true;
            this.$eventBus.$emit("popup", popuplist, check);
          }
      })
    } else {
      this.$eventBus.$emit("popup", [], false);
    }
  },
  mounted() {
    this.$util.debug("목록");
    this.f_main1()
    .then(() => this.getBestGoodsList())
    .then(() => this.f_main2())
    .then(() =>{
      console.log('Done!')
    })

  },
  watch: {
    currentTab(value) {
      this.$util.debug(value);
      //if (this.mzTrendList.length > 0) {
        this.getBestGoodsList();
      //}

    }
  }
};