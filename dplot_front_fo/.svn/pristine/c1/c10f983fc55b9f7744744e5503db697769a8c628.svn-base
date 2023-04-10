import Claim from "@views.mobile/mypage/shop/claim/Claim.js";
import Common from "@views.mobile/mypage/shop/Common.js";

export default {
    mixins : [Claim, Common],
    data() {
        return {
            pagingData: {
                currentPage: 1,         // 현재 페이지
                listTotal: 0,           // 조회목록 전체 수
                listCnt: 5              // 한페이지에 노출할 목록수
            },
            claimName : '',
            isEnd:false
        };
    },
    created() {
        if(this.$route.params.init) {
          this.$storage.removeSessionStorage('param-' + this.$route.name);
        }
        
        if(!this.$util.isBlank(this.$route.params.state)){
            this.searchData.state = this.$route.params.state;
        } else {
          const param = this.$storage.getSessionStorage('param-' + this.$route.name);
          if(!this.$util.isEmpty(param)) {
              this.searchData.date = param.date;
              this.searchData.state = param.state;
              this.pagingData.currentPage = param.currentPage;
          }
        }
        this.getClmStatus();
    },
    mounted() {
        this.getClmList();
    },
    methods: {
        /**********************
         * 데이터 초기화
         ********************/
        initData() {
            this.isEnd = false;
            this.pagingData.currentPage = 1;
            this.pagingData.listTotal = 0;
            this.claimList = [];
        },
        getClmList() {
            let param = {
                date : this.searchData.date,
                state : this.searchData.state == 'ALL' ? '' : this.searchData.state,
                iskeep : true,
                reqname : this.$route.name
            }
            param = Object.assign({}, param, this.pagingData);
            this.isEnd = false;
            this.$http.post('/mypage/claim/list', param).then(result => {
                if (result.statusCode == 200) {
                    //전체목록수
                    this.pagingData.listTotal = result.data.listtotal;
                    this.claimList = [];
                    this.beforeClaimList(result.data.list);
                    window.scrollTo(0,0);
                }
                this.isEnd = true;
            });
        },
        changePage(page) {
            if(this.pagingData.currentPage != page) {
                this.pagingData.currentPage = page;
                this.getClmList();
            }
        },
        changeSearch() {
            if(this.searchData.state == 'CLM001') {
                this.claimName = '취소';
            } else if(this.searchData.state == 'CLM002') {
                this.claimName = '반품';
            } else if(this.searchData.state == 'CLM003') {
                this.claimName = '교환';
            } else {
                this.claimName = '';
            }
            this.initData();
            this.getClmList();
        }
    },
}