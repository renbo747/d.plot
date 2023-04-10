export default {
    beforeCreate() {
      this.$store.commit("onSubHeaderOption", {
        title: "나의 등급안내",
        searchIcon: true,
        cartIcon: true,
        homeIcon: false,
      });
    },
    data() {
      return {
        nowdate: "", //yyyymm
        startdate: "",
        enddate: "",
        month: "",
        nextmonth: "",
        memberinfo: {},
        orderinfo:{},
        userIndex:0, //사용자 등급 index
        nextIndex:0, //다음 등급 index
        // 데이터 초기값을 받아오기 위한 변수 선언
        currentBenefit: 0, // 현재 등급 혜택
        currentColor: null, // 현재 등급 색상
        currentBenefitData: {}, // 현재 등급 혜택 내역
        requiredAmount: 0, // 등급 업에 필요한 구매 금액
        requiredCount: 0, // 등급 업에 필요한 구매 건수
        userData: {
          // 유저 데이터
          grade: {
            // bronze, silver, gold, vip, vvip 등급
            id: 2,
            img: require("@/assets/mobile/img/mypage/grade/img-silver-level-64px.png"), // 등급 이미지
            text: "silver", // 등급 이름
            upgradePrice: 2000000, // 등급 업에 필요한 총 주문 금액
            upgradeCount: 5, // 등급 업에 필요한 총 구매 건수
          },
          name: "홍길동", // 유저 이름
          totalPrice: 1050000, // 총 주문 금액
          totalCount: 2, // 총 주문 건수
        },
        gradeData: [
          // 등급 혜택
        ],
      };
    },
    computed: {
      // 등급업에 필요한 구매 금액의 퍼센트 계산 (progress percent data)
      getAmountPercent() {
        return (
          (this.userData.totalPrice / this.userData.grade.upgradePrice) * 100
        );
      },
      // 등급업에 필요한 구매 건수의 퍼센트 계산 (progress percent data)
      getCountPercent() {
        return (
          (this.userData.totalCount / this.userData.grade.upgradeCount) * 100
        );
      },
    },
    created(){
      this.gradeData =  this.$front.gradeList();
    },
    mounted() {
      this.$util.debug("나의 등급안내");
      this.date = this.$util.getYearMonth();
      this.startdate = this.$util.getMonthFirstDate(this.date);
      this.enddate = this.$util.getMonthLastDate(this.date);
      this.month = this.$util.getMonth();
      this.nextmonth = String(Number(this.month) + 1);
      
      if (this.month.length == 2) {
        this.month = this.month.substring(1, 2);
      }
      this.gradeList();
    },
    methods: {
      gradeList() {
        this.$http.post("/mypage/grade", {}).then((result) => {
          if (result.statusCode == 200) {
            this.$util.debug("등급");
            this.memberinfo = result.data.memberinfo;
            this.orderinfo = result.data.orderinfo;
            this.$util.debug(result.data);
            this.$util.debug(this.memberinfo);
  
  
            //등급 인덱스 구하기
            this.getGradeIndex();
  
            // id 값을 기준으로 index에 접근하기 위한 변수
            // const selected = this.userData.grade.id - 1;
            // // 컬러값 및 혜택 내용 초기 설정
            // this.currentBenefit = this.userData.grade.id; // 현재 유저 등급
            // this.currentColor = this.gradeData[selected].color; // 현재 선택된 등급 색상
            // this.requiredAmount =
            //   this.userData.grade.upgradePrice - this.userData.totalPrice; // 등급업에 필요한 남은 구매 금액
            // this.requiredCount =
            //   this.userData.grade.upgradeCount - this.userData.totalCount; // 등급업에 필요한 남은 구매 건수
            // this.currentBenefitData = this.gradeData[selected].benefitList; // 현재 선택된 혜택 목록
          } else {
            this.$util.debug(result.message);
          }
        });
      },
      /**********************
       * 등급 인덱스 구하기
       **********************/
      getGradeIndex() {
        for (let step = 0; step < this.gradeData.length; step++) {
          if (this.gradeData[step].type == this.memberinfo.memlvtype) {
            this.userIndex = step;
            if (this.gradeData[step].type == "MDL005") {
              this.nextIndex = 4;
            }else {
              this.nextIndex = step + 1;
            }
            this.setAmt(step);
            this.setData(step);
            break;
          }
        }
      },
      /**********************
       * 추가 금액 셋팅
       **********************/
      setAmt(index){
        //slet upgradeamt = this.gradeData[index].
        //this.requiredAmount = 
      },
      /**********************
       * 인덱스에 맞는 데이터 셋팅
       **********************/
      setData(index) {
        this.currentBenefit = index + 1;
        this.currentColor = this.gradeData[index].benefitList;
        this.currentBenefitData = this.gradeData[index].benefitList;
      },
    },
  };