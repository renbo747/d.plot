import Order from "@views.mobile/mypage/shop/order/Order.js";

export default {
  mixins : [Order],
  data() {
    return {
      pagingData: {
          currentPage: 1,         // 현재 페이지
          listTotal: 0,           // 조회목록 전체 수
          listCnt: 5              // 한페이지에 노출할 목록수
      },
      isEnd:false
    };
  },
  created() {
    if(this.$route.params.init) {
      this.$storage.removeSessionStorage('param-' + this.$route.name);
    } else {
      const param = this.$storage.getSessionStorage('param-' + this.$route.name);
      if(!this.$util.isEmpty(param)) {
          this.searchData.date = param.date;
          this.searchData.state = param.state;
          this.pagingData.currentPage = param.currentPage;
      }
    }
    this.getOrdStatus();
  },
  mounted() {
    this.getOrdList();
  },
  methods: {
    /**********************
     * 데이터 초기화
     ********************/
    initData() {
      this.isEnd = false;
      this.pagingData.currentPage = 1;
      this.pagingData.listTotal = 0;
      this.orderList = [];
    },
    getOrdList() {
      let param = {
        date : this.searchData.date,
        state : this.searchData.state == 'ALL' ? '' : this.searchData.state,
        iskeep : true,
        reqname : this.$route.name
      }
      param = Object.assign({}, param, this.pagingData);
      this.isEnd = false;
      this.$http.post('/mypage/order/list', param).then(result => {
        if (result.statusCode == 200) {
          //전체목록수
          this.pagingData.listTotal = result.data.listtotal;
          this.orderList = [];    
          this.beforeOrderList(result.data.list);
          window.scrollTo(0,0);
        }
        this.isEnd = true;
      });
    },
    changePage(page) {
      if(this.pagingData.currentPage != page) {
        this.pagingData.currentPage = page;
        this.getOrdList();
      }
    },
    changeSearch() {
      this.initData();
      this.getOrdList();
    }
  },
  watch:{
    // searchData : {
    //     handler(val){
    //       this.changeSearch();
    //     },
    //     deep: true
    // }
  }
};