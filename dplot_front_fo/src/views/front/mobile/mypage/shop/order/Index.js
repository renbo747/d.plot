import InfiniteLoading from "vue-infinite-loading";
import Order from "@views.mobile/mypage/shop/order/Order.js";

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
          listCnt: 5,             // 한페이지에 노출할 목록수
          init : 'F'
      },
      isEnd:false, // 최초 데이터 목록을 조회전 조회 결과 없음이 순간 노출되는게 있어서 처리용
      isTop:true   // 일부핸드폰에서 최초 화면로딩시 scroll이 탑으로 가지않는 현상 처리용
    };
  },
  created() {
    if(this.$route.params.init) {
      this.$storage.removeSessionStorage('param-' + this.$route.name);
      //this.$storage.removeSessionStorage('list-' + this.$route.name);
    } else {
      const param = this.$storage.getSessionStorage('param-' + this.$route.name);
      if(!this.$util.isEmpty(param)) {
        this.searchData.date = param.date;
        this.searchData.state = param.state;
        this.pagingData.currentPage = param.currentPage;
        this.pagingData.init = 'T';
      }
      
      // const list = this.$storage.getSessionStorage('list-' + this.$route.name);
      // if(!this.$util.isEmpty(list)) {
      //     this.orderList = list;
      // }
    }
    this.getOrdStatus();
  },
  updated() {
    this.$nextTick(()=>{
      if(this.orderList.length > 0 && this.pagingData.init == 'T'){
        this.$eventBus.$emit('scrollTo', this.$route.name);
        this.pagingData.init = 'F';
        this.isTop = false;
      }else{
        if (this.isTop) {
          this.$util.scrollToTop();
          this.isTop = false;
        }
      }
    });
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
      this.isEnd = false;
    },
    getOrdList($state) {
      let param = {
        date : this.searchData.date,
        state : this.searchData.state == 'ALL' ? '' : this.searchData.state,
        iskeep : true,
        reqname : this.$route.name
      }
      this.isEnd = false;
      param = Object.assign({}, param, this.pagingData);
      this.$http.post('/mypage/order/list', param).then(result => {
        if (result.statusCode == 200) {
          //전체목록수
          this.pagingData.currentPage += 1;
          this.pagingData.listTotal = result.data.listtotal;
          
          this.beforeOrderList(result.data.list);

          if(result.data.list.length == 0 || result.data.list.length < this.pagingData.listCnt) {
            $state.complete();
          } else {
            $state.loaded();
          }
          //this.$storage.setSessionStorage('list-' + this.$route.name, this.orderList);
        } else {
          $state.complete();
        }
        this.isEnd = true;
      });
    },
    changeSearch() {
      this.initData();
      //this.getOrdList();
    },
  },
};