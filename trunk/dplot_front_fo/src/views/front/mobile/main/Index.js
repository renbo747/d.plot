import { swiper, swiperSlide } from "vue-awesome-swiper";
import InstagramCard from "@views.common/components/main/InstagramCard";

export default {
  components: {
    InstagramCard,
    swiper,
    swiperSlide,
  },
  data() {
    return {
      isMenu: false,
      mainBannerList: [],
      swiperOption: {
        slidesPerView: "auto",
        loop: true,
        pagination: {
          el: ".swiper-pagination",
          clickable: true,
        },
      },
      swiperOptionPc: {
        slidesPerView: 1,
        loop: true,
        navigation: {
          nextEl: ".swiper-btn-next",
          prevEl: ".swiper-btn-prev",
        },
      },
      magazineDataList: [],
      instagramData: [],
      swiperMultiOption: {
        slidesPerView: 4,
        spaceBetween: 20,
        loopedSlides:4,
        loop:true,
        slidesPerGroup: 4,
        //observeParents: false,
        //observer: true,
        navigation: {
          nextEl: ".swiper-btn-next",
          prevEl: ".swiper-btn-prev",
        },
      },
      isMainPopup:true
    };
  },
  methods: {
    nextSection() {
      const firstSectionHeight = this.$refs.mainBanner.offsetHeight;
      window.scrollTo({
        top: firstSectionHeight,
        left: 0,
        behavior: "smooth",
      });
    },

    getMzList() {
        this.$http.post('/mz/list', {})
        .then(result => {
            if(result.statusCode === 200) {
                let data = result.data;
                this.mainBannerList = data.mainbannerlist;
                this.magazineDataList = data.magazinedatalist;
                this.instagramData = data.instagramdata;
                for(let i=this.instagramData.length -1; i >= 0; i--) {
                  let data = this.instagramData[i];
                  if(data.isdel === 'T') {
                    this.instagramData.splice(i,1);
                  }
                }
                
                if(this.mainBannerList.length > 0) {
                  if(this.mainBannerList[0].iswhite === 'F') {
                      this.$emit("load", '#ffffff');
                  }
                }

                // 기획 확인 후 수정필요로 임시주석
                // let savedPosition = this.$store.state.savedPosition;
                // if(savedPosition) {
                //   this.$nextTick(()=>{
                //     window.scrollTo(savedPosition.x,savedPosition.y);
                //   });
                // }
            }
        })
    },

    goLink(type) {
      if(type === 'mainSwiper') {
        let list = this.mainBannerList[this.$refs.mainSwiper.swiper.realIndex];
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
      } else if(type === 'instaSwiper'){
        let insta = this.instagramData[this.$refs.instaSwiper.swiper.realIndex];
        if (!this.$util.isNull(insta.permalink)) {
              window.open(insta.permalink, "_blank");
        }
      } else {
        if (window.sessionStorage.getItem('platform') == "MAC001") {
          if (!this.$util.isNull(type.pclinkurl)) {
              if (type.ispcnwindow == "T") {
                  window.open(type.pclinkurl, "_blank");
              } else {
                  window.location.href = type.pclinkurl;
              }
          }
        } else {
            if (!this.$util.isNull(type.molinkurl)) {
                if (type.ismonwindow == "T") {
                    window.open(type.molinkurl, "_blank");
                } else {
                    window.location.href = type.molinkurl;
                }
            }
        }
      }
    },

    goInstaLink() {
      window.open(process.env.VUE_APP_INSTA_MAIN_URL, "_blank");
    },

    handleSwiper() {
      const idx = this.$refs.mainSwiper.swiper.realIndex;
      //let bgColor = '#ffffff';//흰색
      let bgColor = '#000000';//흰색
      if(this.mainBannerList[idx].iswhite === 'F') {
        bgColor = '#ffffff';//검정
      }
      this.$emit("load", bgColor);
    },

    likeChange(goodsno, iswished) {
      this.magazineDataList.forEach(item => {
        item.goodsdatalist.forEach(goods => {
          if(goods.goodsno === goodsno) {
            goods.iswished = iswished;
          }
        })
      });
    },
    menuSticky() {
      if (window.sessionStorage.getItem('platform') == "MAC001") {
        let scH = window.scrollY;
        let menu = this.$refs.magazine_menu.offsetTop;
        if (scH > menu) {
          this.isMenu = true;
        } else {
          this.isMenu = false;
        }
      }
    },
  },
  /**************************
   * 메인팝업 호출
   **************************/
   beforeCreate(){
    let name = '';
    if (window.sessionStorage.getItem('platform') == "MAC001") {
      name = 'MAIN_POP_CLOSE_ONEDAY_PC';
    } else {
      name = 'MAIN_POP_CLOSE_ONEDAY_MO';
    }
    if (this.$util.isNull(this.$cookies.get(name))) {
      this.$http.post('/mz/popup', { isdispmagazine : 'T' })
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
    this.getMzList();
    window.addEventListener("scroll", this.menuSticky);
  },
  beforeDestroy() {
    window.removeEventListener("scroll", this.menuSticky);
  },
  watch: {
    'instagramData': function(value){
      if (value.length < 4) {
        this.swiperMultiOption.loop = false;
      }else {
        this.swiperMultiOption.loop = true;
      }
    }
  }
};