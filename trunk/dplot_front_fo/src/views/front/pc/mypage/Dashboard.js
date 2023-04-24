
export default {
  data() {
    return {
      memberInfo: {},
      orderCntInfo: {},
      orderInfo: {},
      reviewList: [],
      gradeData: [],
      userIndex: 0,
      nextIndex: 0,
      reviewmax: 0
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


          let list = result.data.reviewlist;
          this.reviewmax = result.data.reviewmax;
          //옵션명 설정
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
            list[i].reviewmax = this.reviewmax;
          }

          this.reviewList = list;
          this.getGradeIndex();
        }
      })
    },
    /********************************
     * 리뷰 상세페이지 이동
     ********************************/
    goToReviewDetail(list) {
      this.$router.push({ name: "mypage-my-review-write", query: { orderidx: list.orderidx, ordgdidx: list.ordgdidx } });
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
  },
  created() {
    this.gradeData = this.$front.gradeList();
  },
  mounted() {
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