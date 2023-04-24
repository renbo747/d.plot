import { swiper, swiperSlide } from "vue-awesome-swiper";
import PhotoReview from "@views.mobile/shop/popup/PhotoReview.vue";
import ReviewLiveList from "@views.mobile/magazine/component/ReviewLiveList.vue";
export default {
  components: {
    swiper,
    swiperSlide,
    ReviewLiveList
  },
  beforeCreate() {
    this.$store.commit("onSubHeaderOption", {
      title: "리뷰",
      searchIcon: true,
      cartIcon: true,
      homeIcon: false,
    });
  },
  data() {
    return {
      bestReviewSwiperOption: {
        slidesPerView: "auto",
        spaceBetween: 10,
        loop: false,
        pagination: {
          el: ".swiper-pagination",
        },
        navigation: {
          nextEl: ".swiper-button-next",
          prevEl: ".swiper-button-prev",
        },
        centeredSlides: true,
      },
      photoSwiperOptions: {
        loop: true,
        slidesPerView: "auto",
        spaceBetween: 10,

        observeParents: true,
        observer: true,
        navigation: {
          nextEl: ".swiper-button-next",
          prevEl: ".swiper-button-prev",
        },
        centeredSlides: true,
      },
      bestReviewList:[],
      bestFileTopList:[],
    };
  },
  methods: {
    getReviewList(){
      let param = {
        isbest : "T",
        limit: 5
      }
      this.$http.post('/review/mzReview', param).then(result=> {
        if (result.statusCode === 200) {
          this.$util.scrollToTop();
          this.bestReviewList = result.data.reviewlist;
          this.bestFileTopList = result.data.fstfilelist;
        }
      })
    },
    /*****************************
     *모달 팝업 오픈
     ****************************/
    openModal(modalId, param) {
      this.$eventBus.$emit('showModal', PhotoReview, modalId, param);
    }
  },
  mounted(){
    this.$util.scrollToTop();
    this.getReviewList();
  }
};