export default {
  data() {
    return {
      pagingData: {
        currentPage: 1,         // 현재 페이지
        listTotal: 0,           // 조회목록 전체 수
        listCnt: 5,              // 한페이지에 노출할 목록수
        init: 'F'
      },
      restockList: [],             //재입고 알림 목록
      isEnd: false
    };
  },
  methods: {
    /**********************
     * 데이터 초기화
     ********************/
    initData() {
      //this.pagingData.currentPage = 1;
      this.pagingData.listTotal = 0;
      this.getRestockList();
    },
    /**********************
     * 재입고알림 상품 목록 조회
     ********************/
    getRestockList() {
      let param = Object.assign(this.pagingData);
      this.isEnd = false;
      this.$http.post('/goods/restockList', param).then(result => {
        if (result.statusCode == 200) {
          this.$util.debug("재입고 상품 목록 조회");

          let list = result.data.restocklist;
          for (let i = 0; i < list.length; i++) {
            let optionnames = list[i].options.split(',');
            list[i].opthtml = '';
            if (optionnames.length > 0) {
              if (list[i].isuseaddition == "T") {
                optionnames[0] = '추가상품:' + optionnames[0];
              } else {
                optionnames[0] = '옵션:' + optionnames[0];
              }
              for (let i = 0; i < optionnames.length; i++) {
                optionnames[i] = '<span>' + optionnames[i] + '<span>';
              }
              list[i].opthtml = optionnames.join('<span class="dp-bar h10"></span>');
            }
          }
          this.restockList = result.data.restocklist;
          this.pagingData.listTotal = result.data.listtotal;
          this.$util.debug(this.restockList);
          this.$util.scrollToTop();
        }
        this.isEnd = true;
      })
    },
    /*****************************
     * 자식으로 부터
     ****************************/
    changePage(page) {
      this.pagingData.currentPage = page;
      this.getRestockList();
    },
    /**********************
     * 상품 삭제 처리
     ********************/
    delteProduct(isALL, item) {
      let param = {}
      if (!isALL) {
        param.rewidx = item.rewidx;
      }
      this.$http.post('/goods/restock/del', param).then(result => {
        if (result.statusCode == 200) {
          this.initData();
          this.$toast.clear();
          this.$toast.open("재입고알림신청이 취소되었습니다.");
        } else {
          this.$eventBus.$emit('alert', '알림', result.message);
        }
      });
    }
  },
  created() {
    if (this.$route.params.init) {
      this.$storage.removeSessionStorage('param-' + this.$route.name);
    } else {
      const param = this.$storage.getSessionStorage('param-' + this.$route.name);
      if (!this.$util.isEmpty(param)) {
        this.pagingData.currentPage = param.currentPage;
      }
    }
  },
  mounted() {
    this.initData();
  },
  updated() {
    this.$nextTick(() => {
      if (this.restockList.length > 0 ) {
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
};