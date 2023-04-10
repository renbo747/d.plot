import Claim from "@views.mobile/nonemember/shop/claim/Claim.js";
import Common from "@views.mobile/nonemember/shop/Common.js";

export default {
    mixins : [Claim, Common],
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
        this.getClmList();
    },
    methods: {
        /**********************
         * 데이터 초기화
         ********************/
        initData() {
            this.pagingData.currentPage = 1;
            this.pagingData.listTotal = 0;
            this.claimList = [];
        },
        getClmList() {
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
                    this.pagingData.listTotal = result.data.listtotal;
                    this.claimList = [];
                    this.beforeClaimList(result.data.list);
                }
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