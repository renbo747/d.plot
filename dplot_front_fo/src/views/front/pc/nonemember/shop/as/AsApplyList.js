import AsApplyModal from './popup/AsApplyModal.vue';

export default {
  created() {
    if (this.$route.params.init) {
      this.$storage.removeSessionStorage('param-' + this.$route.name);
    } else {
      const param = this.$storage.getSessionStorage('param-' + this.$route.name);
      if (!this.$util.isEmpty(param)) {
        this.pagingData.init = 'T';
        this.pagingData.currentPage = param.currentPage;
      }
    }
  },
  mounted() {
    this.onInit();
  },
  updated() {
    this.$nextTick(() => {
      if (this.product.length > 0) {
        this.$eventBus.$emit('scrollTo', this.$route.name, (flag) => {
          if (!flag) {
            window.scrollTo(0, 0);
          }
          if (flag && this.pagingData.init == 'F') {
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
      product: [],      //as 신청한 리스트
      myOrder: [],      // 내 주문 목록 리스트
      isEnd: false
    };
  },
  methods: {
    onInit() {
      this.$util.debug("onInit.......");
      //1:1 문의 데이터 로드
      var param = this.pagingData;
      param.iskeep = true;
      param.reqname = this.$route.name;
      this.isEnd = false;
      this.$http.post('/nonemember/as/applylist', param).then(result => {
        if (result.statusCode == 200) {
          this.$util.debug("onInit success.......");
          this.product = result.data.list;
          this.pagingData.listTotal = result.data.listcnt;
          this.myOrder = result.data.myorder;
        }
        this.isEnd = true;
      })
    },
    goToAsDetail(asidx) {
      this.$router.push({ name: "nonemember-as-detail", params: { pid: asidx } });
    },
    /*********************************
     * 페이징 처리
     *********************************/
    changePage(page) {
      this.pagingData.currentPage = page;
      this.pagingData.init = 'F';
      this.onInit();

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