import InfiniteLoading from "vue-infinite-loading";
import Order from "@views.mobile/nonemember/shop/order/Order.js";

export default {
  mixins : [Order],
  components: {
    InfiniteLoading
  },
  beforeCreate() {
    this.$store.commit("onSubHeaderOption", {
      title: "주문목록",
      searchIcon: true,
      cartIcon: true,
      homeIcon: false,
    });
  },
  data() {
    return {
      infiniteId: + new Date(),   //스크롤페이징 id
      pagingData: {
          currentPage: 1,         // 현재 페이지
          listTotal: 0,           // 조회목록 전체 수
          listCnt: 5              // 한페이지에 노출할 목록수
      },
    };
  },
  mounted() {
    this.initData();
    this.getOrdStatus();
  },
  methods: {
    /**********************
     * 데이터 초기화
     ********************/
    initData() {
      this.infiniteId += 1;
      this.pagingData.currentPage = 1;
      this.pagingData.listTotal = 0;
      this.orderList = [];
    },
    getOrdList($state) {
      let param = this.pagingData;

      param = Object.assign({}, param, this.pagingData);
      this.$http.post('/nonemember/order/list', param).then(result => {
        if (result.statusCode == 200) {
          //전체목록수
          this.pagingData.currentPage += 1;
          this.pagingData.listTotal = result.data.listtotal;

          if(result.data.list.length == 0) {
            $state.complete();
          } else {
            this.beforeOrderList(result.data.list);

            $state.loaded();
          }
        } else {
          $state.complete();
        }
      });
    },
    changeSearch() {
      this.initData();
      //this.getOrdList();
    },
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