
import { swiper, swiperSlide } from "vue-awesome-swiper";
import InfiniteLoading from 'vue-infinite-loading';
import ImageModal from './popup/ImageModal.vue';
import ReviewReward from './popup/ReviewReward.vue'

export default {
  components: {
    swiper,
    swiperSlide,
    InfiniteLoading
  },
  beforeCreate() {
    this.$store.commit("onSubHeaderOption", {
      title: "나의 리뷰",
      searchIcon: true,
      cartIcon: true,
      homeIcon: false,
    });
  },
  data() {
    return {
      isMediaType: true, // Todo : 미디어 타입으로 이미지와 영상 표시 구분 필요.
      isModalOpen: false,
      selectedIndex: "",
      imgSwiperOption: {
        slidesPerView: "auto",
        spaceBetween: 10,
        observeParents: true,
        observer: true,
      },
      modalSwiperOption: {
        slidesPerView: "auto",
        loop: true,
      },
      playerOptions: {
        // videojs options
        muted: true,
        width: '400px',
        height: '400px',
        autoplay: true,
        sources: [
          {
            type: 'video/mp4',
            src: 'https://d2yxem29c3b5r4.cloudfront.net/mov/review32/32_1649202052208.mp4',
          }
        ],
      },
      filter: 'before',
      isloading: true,
      infiniteId: +new Date(),
      reviewList: [],
      pagingData: {
        currentPage: 1,  // 현재 페이지
        listTotal: 0,   // 조회목록 전체 수
        listCnt: 10,   // 한페이지에 노출할 목록수
        init: 'F'
      },
      isEnd: false, // 최초 데이터 목록을 조회전 조회 결과 없음이 순간 노출되는게 있어서 처리용
      isTop: true   // 일부핸드폰에서 최초 화면로딩시 scroll이 탑으로 가지않는 현상 처리용
    };
  },
  methods: {
    /************************
     * 데이터 초기화
     ***********************/
    initData() {
      this.reviewList = [];
      this.isloading = true;
      //this.pagingData.currentPage = 1;  // 현재 페이지
      this.pagingData.listTotal = 0,   // 조회목록 전체 수
        this.infiniteId += 1;
    },
    /*******************************
     * 나의 리뷰 목록 조회
     ******************************/
    infiniteHandler($state) {
      this.$util.debug("infiniteHandler........");
      if (this.isloading) {
        this.isEnd = false;
        this.filter = this.$route.name;

        var param = {
          isreview: this.$route.name == "before" ? "F" : "T",
          iskeep: true,
          reqname: this.$route.name
        }
        param = Object.assign(this.pagingData, param);
        this.$http.post('/review/mylist', param).then(result => {
          if (result.statusCode == 200) {
            if (result.data.reviewlist.length) {
              this.pagingData.currentPage += 1;
              //옵션명 설정
              let list = result.data.reviewlist;
              for (let i = 0; i < list.length; i++) {
                let optionnames = list[i].optionnm.split(',');
                list[i].opthtml = '';
                if (!this.$util.isBlank(list[i].optionnm) && optionnames.length > 0) {
                  optionnames[0] = list[i].isaddgoods == "T" ? "추가상품: " : "옵션: " + optionnames[0];
                  for (let i = 0; i < optionnames.length; i++) {
                    optionnames[i] = '<span>' + optionnames[i] + '</span>';
                  }
                  list[i].opthtml = optionnames.join('<span class="dp-bar h10"></span>');
                }
              }
              this.reviewList.push(...list);
              this.pagingData.listTotal = result.data.listtotal;
              $state.loaded();
              this.isloading = true;
              if (result.data.reviewlist.length < this.pagingData.listCnt) {
                $state.complete();
                this.isloading = false;
              }
            } else {
              $state.complete();
              this.isloading = false;
            }
          }
          this.isEnd = true;
        })
      }
    },
    /*******************************
     * 탭변경시 처리
     ******************************/
    changeTap(name) {
      this.$util.debug("changeTap()...");
      this.filter = name;
      this.isEnd = false;
      this.$router.replace({ name: name, params: { init: true } });
    },
    /******************************
     * 리뷰 삭제
     *******************************/
    deleteReview(list) {
      if (list.isdeadline == "T") {
        this.$eventBus.$emit('alert', '알림', "리뷰 삭제는 작성후 3일이내에 가능합니다.");
        return;
      }
      if (list.isbest == "T") {
        this.$eventBus.$emit('alert', '알림', "베스트에 선정된 리뷰는 삭제할수 없습니다.");
        return;
      }
      this.$eventBus.$emit('confirm', '확인', '작성하신 리뷰를 삭제하시겠습니까? 리뷰 삭제 시 재작성은 불가 합니다.', () => {
        let param = {
          reviewidx: list.reviewidx,
          regdate: list.regdatefull
        }
        this.$http.post('/review/delete', param).then(result => {
          if (result.statusCode == 200) {
            this.$toast.clear();
            this.$toast.open('리뷰가 삭제되었습니다.');
            // this.initData();
            this.changeTap('after');
          } else {
            this.$eventBus.$emit('alert', '알림', result.message);
          }
        })
      });
    },
    goShopDetail(id) {
      this.$router.push({ name: "shop-detail", params: { pid: id } });
    },
    /*****************************
     * 리뷰 상세 페이지이동
     ****************************/
    goToReviewWrite(list) {
      if (list.isdeadline == "T") {
        this.$eventBus.$emit('alert', '알림', "리뷰 수정은 작성후 3일이내에 가능합니다.");
        return;
      }
      this.$router.replace({ name: "mypage-my-review-write", query: { orderidx: list.orderidx, ordgdidx: list.ordgdidx } });
    },
    /*****************************
     * 이미지 모달 열기
     ****************************/
    openModal(files, index) {
      let param = {
        files: files,
        filetype: files[index].filetype
      }
      this.$eventBus.$emit('showNoBvModal', ImageModal, param);
    },
    showModal(modalId) {
      if (modalId == 'reviewRewardModal') {
        this.$eventBus.$emit('showModal', ReviewReward, modalId);
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
      if (this.reviewList.length > 0 && this.pagingData.init == 'T') {
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
  // watch: {
  //   '$route.name'(val) {
  //     this.$util.debug("화면이동::" + val);
  //     this.initData();
  //   }
  // },
};