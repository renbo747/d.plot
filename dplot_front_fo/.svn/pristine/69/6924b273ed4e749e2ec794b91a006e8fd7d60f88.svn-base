import InfiniteLoading from "vue-infinite-loading";
import Claim from "@views.mobile/mypage/shop/claim/Claim.js";
import Common from "@views.mobile/mypage/shop/Common.js";

export default {
    mixins: [Claim, Common],
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
    created() {
        if (this.$route.params.init) {
            this.$storage.removeSessionStorage('param-' + this.$route.name);
            //this.$storage.removeSessionStorage('list-' + this.$route.name);
        }

        if (!this.$util.isBlank(this.$route.params.state)) {
            this.searchData.state = this.$route.params.state;
        } else {
            const param = this.$storage.getSessionStorage('param-' + this.$route.name);
            if (!this.$util.isEmpty(param)) {
                this.searchData.date = param.date;
                this.searchData.state = param.state;
                this.pagingData.currentPage = param.currentPage;
                this.pagingData.init = 'T';
            }


            //   const list = this.$storage.getSessionStorage('list-' + this.$route.name);
            //   if(!this.$util.isEmpty(list)) {
            //       this.claimList = list;
            //   }
        }
        this.getClmStatus();
    },
    data() {
        return {
            infiniteId: + new Date(),   //스크롤페이징 id
            pagingData: {
                currentPage: 1,         // 현재 페이지
                listTotal: 0,           // 조회목록 전체 수
                listCnt: 5,             // 한페이지에 노출할 목록수
                init: 'F'
            },
            isEnd: false, // 최초 데이터 목록을 조회전 조회 결과 없음이 순간 노출되는게 있어서 처리용
            isTop: true   // 일부핸드폰에서 최초 화면로딩시 scroll이 탑으로 가지않는 현상 처리용
        };
    },
    updated() {
        this.$nextTick(() => {
            if (this.claimList.length > 0 && this.pagingData.init == 'T') {
                this.$eventBus.$emit('scrollTo', this.$route.name);
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
    methods: {
        /**********************
         * 데이터 초기화
         ********************/
        initData() {
            this.isEnd = false;
            this.infiniteId += 1;
            this.pagingData.currentPage = 1;
            this.pagingData.listTotal = 0;
            this.claimList = [];
        },
        getClmList($state) {
            let param = {
                date: this.searchData.date,
                state: this.searchData.state == 'ALL' ? '' : this.searchData.state,
                iskeep: true,
                reqname: this.$route.name
            }
            param = Object.assign({}, param, this.pagingData);
            this.isEnd = false;
            this.$http.post('/mypage/claim/list', param).then(result => {
                if (result.statusCode == 200) {
                    //전체목록수
                    this.pagingData.currentPage += 1;
                    this.pagingData.listTotal = result.data.listtotal;

                    this.beforeClaimList(result.data.list);

                    if (result.data.list.length == 0 || result.data.list.length < this.pagingData.listCnt) {
                        $state.complete();
                    } else {
                        $state.loaded();
                    }
                    //this.$storage.setSessionStorage('list-' + this.$route.name, this.claimList);
                } else {
                    $state.complete();
                }
                this.isEnd = true;
            });
        },
        changeSearch() {
            // if (this.searchData.state == 'CLM001') {
            //     this.claimName = '취소';
            // } else if (this.searchData.state == 'CLM002') {
            //     this.claimName = '반품';
            // } else if (this.searchData.state == 'CLM003') {
            //     this.claimName = '교환';
            // } else {
            //     this.claimName = '';
            // }
            this.initData();
            //this.getClmList();
        },
    },
    // watch: {
    //     searchData: {
    //         handler(val) {
    //             this.changeSearch();
    //         },
    //         deep: true
    //     }
    // }

}