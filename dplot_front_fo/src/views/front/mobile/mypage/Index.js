
import { swiper, swiperSlide } from "vue-awesome-swiper";

export default {
  components: {
    swiper,
    swiperSlide,
  },
  beforeCreate() {
    this.$store.commit("onSubHeaderOption", {
      title: "마이페이지",
      searchIcon: true,
      cartIcon: true,
      homeIcon: false,
    });
  },
  data() {
    return {
      gradeData: [],
      isModalOpen: false,
      isMediaType: true, // Todo : 미디어 타입으로 이미지와 영상 표시 구분 필요.
      selectedIndex: "",
      isStaff: false, //임직원 적립금 표시
      isReview: true, // 리뷰없음 표시
      userData: {
        // 유저 데이터
        grade: {
          upgradePrice: 2000000, // 등급 업에 필요한 총 주문 금액
          upgradeCount: 3, // 등급 업에 필요한 총 구매 건수
        },
        name: "홍길동", // 유저 이름
        totalPrice: 1050000, // 총 주문 금액
        totalCount: 2, // 총 주문 건수
      },
      orderCheckData: [
        {
          id: "check01",
          num: 0,
          state: "입금대기",
          isDisable: true,
        },
        {
          id: "check02",
          num: 3,
          state: "상품/배송<br />준비중",
          isDisable: false,
        },
        {
          id: "check03",
          num: 2,
          state: "배송중",
          isDisable: false,
        },
        {
          id: "check04",
          num: 3,
          state: "배송<br />완료",
          isDisable: false,
        },
        {
          id: "check05",
          num: 1,
          state: "구매<br />확정",
          isDisable: false,
        },
      ],
      imgSwiperOption: {
        slidesPerView: "auto",
        spaceBetween: 5,
        observeParents: true,
        observer: true,
        loop: false,
      },
      reviewCompleteData: [
        {
          id: 1,
          src: require("@/assets/mobile/img/shop/img-shop-promotion02-1.jpg"),
          type: "photo",
        },
        {
          id: 2,
          src: require("@/assets/mobile/img/shop/img-shop-promotion02-2.jpg"),
          type: "photo",
        },
        {
          id: 3,
          src: require("@/assets/mobile/img/shop/img-shop-promotion02-3.jpg"),
          type: "video",
        },
      ],
      memberInfo: {},
      orderCntInfo: {},
      orderInfo: {},
      reviewList: [],
      userIndex: 0,
      nextIndex: 0,
      reviewmax: 0,
      isreward: true,
    };
  },
  methods: {
    /********************************
     * 마이페이지 정보조회
     ********************************/
    getMypageInfo() {
      this.$http.post('/mypage/list', {}).then(result => {
        if (result.statusCode == 200) {
          this.$util.debug("마이페이지 정보조회...");
          this.$util.debug(result);
          this.memberInfo = result.data.memberinfo;
          this.orderCntInfo = result.data.ordercntinfo;
          this.orderCntInfo.deliing = this.orderCntInfo.prepgoodscnt + this.orderCntInfo.prepdelivcnt;
          //취소수량(클레임취소 + 입금전취소)
          this.orderCntInfo.claimcnccnt = this.orderCntInfo.claimcnccnt + this.orderCntInfo.cancelcnt;
          
          this.orderInfo = result.data.orderinfo;
          this.reviewList = result.data.reviewlist;
          this.reviewmax = result.data.reviewmax;
          if (this.reviewList.length > 0) {
            this.reviewmax = this.reviewmax * this.reviewList[0].totcnt;
          }
          this.getGradeIndex();
        }
      })
    },
    /**********************
     * 등급 인덱스 구하기
     **********************/
    getGradeIndex() {
      for (let step = 0; step < this.gradeData.length; step++) {
        if (this.gradeData[step].type == this.memberInfo.memlvtype) {
          this.userIndex = step;
          if (this.gradeData[step].type == "MDL005") {
            this.nextIndex = 4;
          } else {
            this.nextIndex = step + 1;
          }
          break;
        }
      }
    },
    /********************************
     * 로그아웃
     ********************************/
    logout() {
      this.$http.post('/member/logout', {}).then(result => {
        if (result.statusCode == 200) {
          // 리다이렉트 패스 삭제
          this.$storage.removeSessionStorage('redirectPath');
          this.$storage.removeSessionStorage("name");
          // 카트삭제
          this.$store.commit("cartList", []);
          // 메인페이지 이동
          this.$router.replace({ name: 'shop', params: { init: true } });
        } else {
          this.$eventBus.$emit('alert', '알림', result.message);
        }
      });
    },
    /********************************
    * 리뷰 상세페이지 이동
    ********************************/
    goToReviewDetail(list) {
      this.$router.push({ name: "mypage-my-review-write", query: { orderidx: list.orderidx, ordgdidx: list.ordgdidx } });
    },
  },
  created() {
    this.gradeData = this.$front.gradeList();
  },
  mounted() {
    // 추천리워드 사용안함시 추천인 아이디 초기화
    this.$http.post('/etc/reward', { isloading: false })
      .then(result => {
        if (result.statusCode === 200) {
          if (this.$util.isNull(result.data.rewardinfo)) {
            this.isreward = false;
          }
        }
      })
    this.getMypageInfo();
  },
  computed: {
    // 등급업에 필요한 구매 금액의 퍼센트 계산 (progress percent data)
    getAmountPercent() {
      return (
        (this.userData.totalPrice / this.userData.grade.upgradePrice) * 100
      );
    },
  },
};

