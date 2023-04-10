import InfiniteLoading from "vue-infinite-loading";
import Claim from "@views.mobile/nonemember/shop/claim/Claim.js";
import Common from "@views.mobile/nonemember/shop/Common.js";

export default {
    mixins : [Claim, Common],
    components: {
        InfiniteLoading
    },
    beforeCreate() {
        this.$store.commit("onSubHeaderOption", {
            title: "취소/교환/반품",
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
    },
    methods: {
        /**********************
         * 데이터 초기화
         ********************/
        initData() {
            this.infiniteId += 1;
            this.pagingData.currentPage = 1;
            this.pagingData.listTotal = 0;
            this.claimList = [];
        },
        getClmList($state) {
            let param = this.pagingData;

            this.$http.post('/nonemember/claim/list', param).then(result => {
                if (result.statusCode == 200) {
                    if(result.data.memberinfo == 0) {
                        this.$eventBus.$emit('alert', '비회원 주문조회 해주세요.', ()=>{
                          this.$router.replace('login');
                        });
                        return;
                    }
                    //전체목록수
                    this.pagingData.currentPage += 1;
                    this.pagingData.listTotal = result.data.listtotal;

                    if(result.data.list.length == 0) {
                        $state.complete();
                    } else {
                    this.beforeClaimList(result.data.list);

                    $state.loaded();
                    }
                } else {
                    $state.complete();
                }
            });
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
    
}