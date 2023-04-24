import InfiniteLoading from "vue-infinite-loading";
export default {
  components: {
    InfiniteLoading
  },
  data() {
    return {
      infiniteId: + new Date(),
      isloading: true,
      noticeListData: [],
      pagingData: {
        currentPage: 1,  // 현재 페이지
        listTotal: 0,   // 조회목록 전체 수
        listCnt: 10,   // 한페이지에 노출할 목록수
        init: 'F'
      },
    };
  },
  methods: {
    /***********************
     * 데이터 초기화
     ***********************/
    initData() {
      this.pagingData.listTotal = 0;
      this.isloading = true;
      this.infiniteId += 1;
      this.noticeListData = [];
    },
    goDetail(idx) {
      var param = {
        idx: idx
        , isloading: false
      }
      //해당 게시물 조회수 증가 처리
      this.$http.post('/cscenter/hit', param).then(result => {
        this.$router.push({ name: 'cs-notice-detail', params: { idx: idx } });
      }).catch(error => {
        this.$util.debug(error);
      })
    },
    /*********************
     * 공지사항 조회(스크롤페이징 처리)
     *********************/
    infiniteHandler($state) {
      if (this.isloading) {
        let param = Object.assign({}, this.pagingData);
        param.iskeep = true;
        param.reqname = this.$route.name;
        this.$http.post('/cscenter/notice', param).then(result => {
          if (result.statusCode == 200) {
            this.pagingData.listTotal = result.data.noticelistcount;
            if (result.data.noticelist.length) {
              this.isloading = true;
              this.pagingData.currentPage += 1;
              this.noticeListData = [...this.noticeListData || [], ...result.data.noticelist];

              if (this.noticeListData >= this.pagingData.listTotal) {
                $state.complete();
                this.isloading = false;
              } else {
                this.isloading = true;
                $state.loaded();
              }
            } else {
              this.isloading = false;
              $state.complete();
            }
          }
        })
      }
    },
  },
  beforeCreate() {
    this.$store.commit("onSubHeaderOption", {
      title: "공지사항",
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
        this.pagingData.currentPage = param.currentPage;
        this.pagingData.init = 'T';
      }
    }
  },
  mounted() {
    this.initData();
  },
  updated() {
    this.$nextTick(() => {
      if (this.noticeListData.length > 0  && this.pagingData.init == 'T') {
        this.$eventBus.$emit('scrollTo', this.$route.name);
        this.pagingData.init = 'F';
      }
    });
  },
};