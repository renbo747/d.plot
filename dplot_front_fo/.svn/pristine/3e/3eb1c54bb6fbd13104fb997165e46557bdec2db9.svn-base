import Order from "@views.mobile/nonemember/shop/order/Order.js";

export default {
  mixins : [Order],
  data() {
    return {
      pagingData: {
          currentPage: 1,         // 현재 페이지
          listTotal: 0,           // 조회목록 전체 수
          listCnt: 5              // 한페이지에 노출할 목록수
      },
    };
  },
  mounted() {
    this.initData();
    this.getOrdList();
  },
  methods: {
    /**********************
     * 데이터 초기화
     ********************/
    initData() {
      this.pagingData.currentPage = 1;
      this.pagingData.listTotal = 0;
      this.orderList = [];
    },
    getOrdList() {
      this.orderList = [];

      let param = this.pagingData;
      
      this.$http.post('/nonemember/order/list', param).then(result => {
        if (result.statusCode == 200) {
          //전체목록수
          this.pagingData.listTotal = result.data.listtotal;
          
          this.beforeOrderList(result.data.list);
        }
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
    searchData : {
        handler(val){
          this.changeSearch();
        },
        deep: true
    }
  }
};