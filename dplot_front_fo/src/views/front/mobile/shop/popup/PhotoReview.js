import { swiper, swiperSlide } from "vue-awesome-swiper";
import PostReport from "@views.mobile/shop/popup/PostReport.vue";
import ImageModal from "@views.mobile/mypage/activity/myreview/popup/ImageModal.vue";

export default {
  components: {
    swiper,
    swiperSlide,
  },
  props: {
    param: Object,
  },
  data() {
    return {
      skey: 0,
      photoSwiperOptions: {
        slidesPerView: "auto",
        spaceBetween: 10,
        loop: false,
        observeParents: true,
        observer: true,
        navigation: {
          nextEl: ".swiper-button-next",
          prevEl: ".swiper-button-prev",
        },
        centeredSlides: true,
      },
      photoList: [],
      nowIndex: 0,
      reviewInfo: {
        brandname: null,
        content: null,
        files: [],
        fullpath: null,
        goodscnt: 0,
        goodsname: null,
        goodsno: 0,
        imglist: [],
        isaddgoods: "F",
        isbest: "F",
        ismyhelp: "F",
        isreview: "F",
        movimglist: [],
        movlist: [],
        optionnm: null,
        ordcnt: null,
        orderdate: null,
      },
      modalInfo: {
        comp: null,
        param: null,
        fnConfirm: () => { },
        fnCancel: () => { },
      },
    };
  },
  methods: {
    /****************************
     * 스와이퍼 변경시 처리
     ***************************/
    slideChange(index) {
      // this.$util.debug(this.$refs.mySwiper.swiper.activeIndex);
      // const index = this.$refs.mySwiper.swiper.activeIndex;
      this.$refs.mySwiper.swiper.activeIndex = index;
      this.nowIndex = index;
      const reviewidx = this.photoList[index].orgidx;

      this.getReviewDetail(reviewidx);
    },
    slideCheck() {
      const index = this.$refs.mySwiper.swiper.activeIndex;
      if(index !== this.nowIndex) {
        this.nowIndex = index;
        const reviewidx = this.photoList[index].orgidx;

        this.getReviewDetail(reviewidx);
      }
    },
    /****************************
     * 리뷰 조회
     ***************************/
    getReviewDetail(reviewidx) {
      let param = {
        reviewidx: reviewidx,
      };

      this.$http.post("/review/detail", param).then((result) => {
        if (result.statusCode == 200) {
          this.$util.debug("리뷰 상세 조회");
          this.$util.debug(this.reviewInfo);

          let list = result.data.reviewdetail;
          let optionnames = list.optionnm.split(',');
          list.opthtml = '';
          if (!this.$util.isBlank(list.optionnm) && optionnames.length > 0) {
            optionnames[0] = list.isaddgoods == "T" ? "추가상품: " : "옵션: " + optionnames[0];
            for (let i = 0; i < optionnames.length; i++) {
              optionnames[i] = '<span>' + optionnames[i] + '</span>';
            }
            list.opthtml = optionnames.join('<span class="dp-bar h10"></span>');
          }
          this.reviewInfo = list;
        }
      });
    },
    /****************************
     * 신고하기 모달 오픈
     ***************************/
    openModal(modalId, reviewidx) {
      let param = {
        reviewidx: reviewidx,
      };
      this.modalInfo.comp = PostReport;
      this.modalInfo.id = modalId;
      this.modalInfo.param = param;

      this.$nextTick(function () {
        this.$bvModal.show("postReportModal");
      });
    },
    /****************************
     * 이미지 모달 오픈
     ***************************/
    imagemodal(files, index) {
      let param = {
        files: files,
        filetype: files[index].filetype,
      };
      this.$eventBus.$emit("showNoBvModal", ImageModal, param);
    },
  },
  mounted() {
    this.$util.debug(this.param);
    this.photoList = this.param.photoList;
    //앞에서 선택한 reviewidx값에 맞는 슬라이드 매칭
    for (let i = 0; i < this.photoList.length; i++) {
      if (this.photoList[i].orgidx == this.param.reviewidx) {
        this.photoSwiperOptions.initialSlide = i;
        this.nowIndex = i;
      }
    }

    this.getReviewDetail(this.param.reviewidx);
  },
  watch: {
    param() {
      this.photoList = this.param.photoList;
      for (let i = 0; i < this.photoList.length; i++) {
        if (this.photoList[i].orgidx == this.param.reviewidx) {
          this.photoSwiperOptions.initialSlide = i;
        }
      }
      this.getReviewDetail(this.param.reviewidx);
    },
  },
};