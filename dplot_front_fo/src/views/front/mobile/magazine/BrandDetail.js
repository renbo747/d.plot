import { swiper, swiperSlide } from "vue-awesome-swiper";
import SnsShareModal from "@views.mobile/shop/popup/SnsShareModal.vue";

export default {
  components: {
    swiper,
    swiperSlide,
  },
  data() {
    return {
      brandInfo: {},
      // 이벤트 배너
      eventBanner: [],
      // 이벤트배너 스와이퍼
      bannerSwiperOption: {
        loop: true,
        pagination: {
          el: ".swiper-pagination",
          clickable: true,
        },
      },
      // 상품리스트 선택
      selectOptionData: "best",
      listSelectOptions: [
        {
          label: "인기순",
          value: "best",
        },
        {
          label: "최신순",
          value: "new",
        },
        {
          label: "평점높은순",
          value: "highscore",
        },
        {
          label: "평점낮은순",
          value: "lowscore",
        },
        {
          label: "가격높은순",
          value: "highprice",
        },
        {
          label: "가격낮은순",
          value: "lowprice",
        },
      ],
      // 브랜드 상품
      brandProduct: [],
      // 컨텐츠 리스트
      contentslist: [],
      pagingData: {
        currentPage: 1,  // 현재 페이지
        listTotal: 0,   // 조회목록 전체 수
        listCnt: 10,   // 한페이지에 노출할 목록수
        init: 'F'
      },
    };
  },
  methods: {
    // 데이터값 확인
    handleSelect(val) {
      if (!this.$util.isNull(val)) {
        this.selectOptionData = val;
      }

      this.getBrandData('T');
    },
    getBrandData(sort) {
      let brandlist = [];
      brandlist.push(this.$route.params.bid);
      let param = {
        brandlist: brandlist,
        brandidx: this.$route.params.bid,
        sort: this.selectOptionData,
        iskeep: true,
        reqname: this.$route.name
      }

      if (sort === 'T') {
        param.sortgoods = 'T';
      }
      param = Object.assign(param, this.pagingData);
      this.$http.post('/brand/mzdetail', param).then(result => {
        if (result.statusCode === 200) {
          let data = result.data;
          this.brandProduct = data.goodslist;
          this.brandProduct.forEach(item => {
            item.saleamt = item.saleamt - item.goodscpnamt;
          });
          if (sort !== 'T') {
            if (this.$util.isNull(data.info)) {
              this.$eventBus.$emit("alert", "알림", "노출중인 브랜드가 아닙니다.", () => {
                this.$router.go(-1);
              });
            } else {
              this.brandInfo = data.info;
              this.eventBanner = data.bannerlist;
              this.contentslist = data.contentslist;
            }
          }
        }
      })
    },
    changeWish() {
      if (!this.$store.state.isLogin) {
        this.$eventBus.$emit('confirm', '로그인', '로그인 하시겠습니까?', () => {
          this.$storage.setSessionStorage('redirectPath', { name: this.$route.name, query: this.$route.query });
          this.$router.push({ name: 'member-login' });
        });
        return;
      }
      let param = {
        bridx: this.$route.params.bid,
        iswished: this.brandInfo.iswished,
      };
      this.$http.post("/brand/wish", param)
        .then((result) => {
          if (result.statusCode == 200) {
            this.brandInfo.wishcnt = result.data.wishcnt;
            this.brandInfo.iswished = this.brandInfo.iswished == "T" ? "F" : "T";
          }
        });
    },
    goLink(list) {
      this.$util.debug(list);
      if (window.sessionStorage.getItem('platform') == "MAC001") {
        if (!this.$util.isNull(list.pclinkurl)) {
          if (list.ispcnwindow == "T") {
            window.open(list.pclinkurl, "_blank");
          } else {
            window.location.href = list.pclinkurl;
          }
        }
      } else {
        if (!this.$util.isNull(list.molinkurl)) {
          if (list.ismonwindow == "T") {
            window.open(list.molinkurl, "_blank");
          } else {
            window.location.href = list.molinkurl;
          }
        }
      }
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
            title: "[D.PLOT]" + this.brandInfo.name + " 브랜드",
            description: description,
            imageUrl: this.brandInfo.fullpath,
            link: {
              mobileWebUrl: window.location.href,
              webUrl: window.location.href
            },
          },
        },
        meta: {
          title: "D.PLOT",
          summary: "상세내용",
          img: this.brandInfo.fullpath
        }
      }
      this.$eventBus.$emit('showModal', SnsShareModal, "snsShareModal", param);
    }
  },
  beforeCreate() {
    this.$store.commit("onSubHeaderOption", {
      title: "BRAND",
      searchIcon: true,
      cartIcon: true,
      homeIcon: false,
    });
  },
  created() {
    if (this.$route.params.init) {
      this.$storage.removeSessionStorage('param-' + this.$route.name);
    } else {
      const param = this.$storage.getSessionStorage('param-' + this.$route.name);
      if (!this.$util.isEmpty(param)) {
        this.pagingData.init = 'T';
      }
    }
  },
  mounted() {
    this.getBrandData();
  },
  updated() {
    this.$nextTick(() => {
      if (this.brandProduct.length > 0 && this.pagingData.init == 'T') {
        this.$eventBus.$emit('scrollTo', this.$route.name);
        this.pagingData.init = 'F';
      }
    });
  },
  watch: {
    'eventBanner': function (value) {
      if (value.length === 1) {
        this.bannerSwiperOption.loop = false;
      } else {
        this.bannerSwiperOption.loop = true;
      }
    }
  }
};