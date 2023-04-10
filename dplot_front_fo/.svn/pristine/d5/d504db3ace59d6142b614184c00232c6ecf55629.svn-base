import AsApplyModal from './popup/AsApplyModal.vue';

export default {
  beforeCreate() {
  },
  created() {
    if (this.$route.params.init) {
      this.$storage.removeSessionStorage('param-' + this.$route.name);
    } else {
      const param = this.$storage.getSessionStorage('param-' + this.$route.name);
      if (!this.$util.isEmpty(param)) {
        this.month = param.lastmonth;
        this.asStatus = param.asstatus;
        this.pagingData.currentPage = param.currentPage;
      }
    }
    this.commonTypeCode();
  },
  mounted() {
    this.getList();
  },
  updated() {
    this.$nextTick(() => {
      if (this.product.length > 0) {
        this.$eventBus.$emit('scrollTo', this.$route.name, (flag) => {
          if (!flag) {
            window.scrollTo(0, 0);
          }
        });
      } else {
        window.scrollTo(0, 0);
      }
    })
  },
  data() {
    return {
      pagingData: {
        currentPage: 1,         // 현재 페이지
        listTotal: 0,           // 조회목록 전체 수
        listCnt: 5,             // 한페이지에 노출할 목록수
        init: 'F'
      },

      month: "1",          //최근 개월수 선택 했을 때
      asStatus: "all",       //문의 타입 선택 했을 때
      monthSelect: [],
      asStatusSelect: [],
      //as 신청한 리스트
      product: [],
      // 내 주문 목록 리스트
      myOrder: [],
      isEnd: false
    };
  },
  methods: {
    /**********************
     * 데이터 초기화
     ********************/
    initData() {
      this.isEnd = false;
      this.pagingData.listTotal = 0;
      this.product = [];
    },
    /*********************
     * AS전용 문의 유형 코드 호출
     *********************/
    commonTypeCode() {
      this.$util.debug("commonTypeCode.......");
      //공통코드 목록 불러오기
      this.asStatusSelect = [];
      this.asStatusSelect.push({
        label: "전체",
        value: "all",
      });

      let cmclassArr = ['ASSTATUS'];
      this.$http.post('/common/code/map/list', { cmclass: cmclassArr, isloading: false })
        .then(result => {
          for (var i = 0; i < result.data.asstatus.length; i++) {
            var temp = {};
            if (result.data.asstatus[i].cmcode != 'AST002') {
              temp.value = result.data.asstatus[i].cmcode;
              temp.label = result.data.asstatus[i].codename;
              this.asStatusSelect.push(temp);
            }
          }

          this.monthSelect = [
            {
              label: "최근 1개월",
              value: "1",
            },
            {
              label: "최근 3개월",
              value: "3",
            },
            {
              label: "최근 6개월",
              value: "6",
            },
            {
              label: "최근 1년",
              value: "12",
            },
          ]
        })
    },
    getList() {
      this.$util.debug("getList.......");
      var param = this.pagingData;
      param.iskeep = true;
      param.reqname = this.$route.name;
      param.asstatus = this.asStatus;
      param.lastmonth = this.month;
      param.asstatus = this.asstatus;
      this.isEnd = false;
      //1:1 문의 데이터 로드
      this.$http.post('/mypage/as/applylist', param).then(result => {
        if (result.statusCode == 200) {
          this.$util.debug("getList success.......");
          this.product = result.data.list;
          this.pagingData.listTotal = result.data.listcnt;
          this.myOrder = result.data.myorder;

          window.scrollTo(0, 0);
        }
        this.isEnd = true;
      })
    },
    goToAsDetail(asidx) {
      this.$util.debug("goToAsDetail ...............");
      this.$util.debug(asidx);
      this.$router.push({ name: "mypage-as-detail", params: { pid: asidx } });
    },
    /*********************************
     * 페이징 처리
     *********************************/
    changePage(page) {
      this.pagingData.currentPage = page;
      this.$store.commit("savedPosition", {});
      this.getList();
    },
    /*********************
     * 검색조건변경
     *********************/
    changeSearch() {
      this.$util.debug("changeSearch ........................");
      this.$store.commit("savedPosition", {});
      this.pagingData.currentPage = 1;
      this.initData();
      this.getList();
    },
    openModal() {
      if (this.$util.isEmpty(this.myOrder)) {
        this.$eventBus.$emit('alert', '알림', "AS문의가 가능한 상품이 없습니다.");
      } else {
        this.$util.debug("openModal ........................");
        this.$eventBus.$emit("showModal", AsApplyModal, 'AsApplyModal', {}, () => {
          this.$util.debug("AsApplyModal Close ....");
          this.$util.debug("AsApplyModal Close result data >>>>>>>>>>>>>>>>");
        });
      }
    },
  },
};