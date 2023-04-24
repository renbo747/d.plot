export default {
    data() {
        return {
            currentPage: 1,        //현재페이지
            totalPage: 5,         // 페이징영역 노출 최대 페이지(1~N, 1+N~N+N)        
            listTotal: 0,        //조회 목록 전체 수
            listCnt: 10,        //페이지 목록 수
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
        /**************************
         * 공지사항 데이터 로딩
         ****************************/
        onNotice() {
            let param = Object.assign({}, this.pagingData);
            param.iskeep = true;
            param.reqname = this.$route.name;
            this.$http.post('/cscenter/notice', param).then(result => {
                if (result.statusCode == 200) {
                    this.pagingData.listTotal = result.data.noticelistcount;
                    this.noticeListData = result.data.noticelist;
                }
            })
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
         * PC 페이징 처리
         *********************/
        changePage(page) {
            this.pagingData.currentPage = page;
            this.$store.commit("savedPosition", {});
            this.onNotice();
        },
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
        this.onNotice();
    },
    updated() {
        this.$nextTick(() => {
          if (this.noticeListData.length > 0) {
            this.$eventBus.$emit('scrollTo', this.$route.name, (flag) => {
              if (!flag) {
                window.scrollTo(0, 0);
              }
            });
          } else {
            window.scrollTo(0, 0);
          }
        })
      }
};