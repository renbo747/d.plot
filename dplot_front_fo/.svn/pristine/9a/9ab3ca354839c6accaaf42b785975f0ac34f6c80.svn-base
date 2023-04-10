import InfiniteLoading from "vue-infinite-loading";
import AsApplyModal from './popup/AsApplyModal.vue';

export default {
  components: {
    InfiniteLoading
  },
  beforeCreate() {
    this.$store.commit("onSubHeaderOption", {
      title: "AS전용 문의내역",
      searchIcon: true,
      cartIcon: true,
      homeIcon: false,
    });
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
        this.pagingData.init = 'T';
      }
    }
  },
  updated() {
    this.$nextTick(() => {
      if (this.product.length > 0 && this.pagingData.init == 'T') {
        this.$eventBus.$emit('scrollTo', this.$route.name);
        this.$storage.removeSessionStorage('param-' + this.$route.name);
        this.pagingData.init = 'F';
        this.isTop = false;
      } else {
        if (this.isTop) {
          this.$util.scrollToTop();
          this.isTop = false;
        }
      }
    });
  },
  data() {
    return {
      infiniteId: + new Date(),   //스크롤페이징 id
      isloading: true,
      pagingData: {
        currentPage: 1,         // 현재 페이지
        listTotal: 0,           // 조회목록 전체 수
        listCnt: 5,             // 한페이지에 노출할 목록수
        init: 'F'
      },
      currentPage: 1,     //현재페이지
      totalPage: 5,       // 페이징영역 노출 최대 페이지(1~N, 1+N~N+N)        
      listTotal: 0,       //조회 목록 전체 수
      perPage: 5,         //페이지 목록 수

      //접수 리스트
      product: [],
      //내 주문목록
      myOrder: [],
      isEnd: false,
      isTop: true
    };
  },
  methods: {
    /************************
     * 페이징 데이터 초기화
     ***********************/
    initData() {
      this.infiniteId += 1;
      this.pagingData.listTotal = 0;
      this.product = [];
      this.isEnd = false;
    },
    goToAsDetail(asidx) {
      this.$router.push({ name: "nonemember-as-detail", params: { pid: asidx } });
    },
    /*********************
     * 리뷰목록 조회(스크롤페이징 처리)
     *********************/
    infiniteHandler($state) {
      if (this.isloading) {
        var param = {
          lastmonth: this.month,
          asstatus: this.asStatus,
          iskeep :true,
          reqname :this.$route.name
        }
        param = Object.assign(param, this.pagingData);
        this.$http.post('/nonemember/as/applylist', param).then(result => {
          if (result.statusCode == 200) {
            this.pagingData.currentPage += 1;
            this.pagingData.listTotal = result.data.listcnt;
            this.myOrder = result.data.myorder;
  
            for (let i = 0; i < result.data.list.length; i++) {
              this.product.push(result.data.list[i]);
            }
  
            if (result.data.list.length == 0 || result.data.list.length < this.pagingData.listCnt) {
              $state.complete();
            } else {
              $state.loaded();
            }
            //this.$storage.setSessionStorage('list-' + this.$route.name, this.product);
          } else {
            $state.complete();
          }
          this.isEnd = true;
        });
      }
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