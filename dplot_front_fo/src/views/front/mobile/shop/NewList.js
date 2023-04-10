import InfiniteLoading from "vue-infinite-loading";
export default {

    beforeCreate(){
        this.$http.post('/shop/newlistname', {extype:"EXN001"}).then(result => {
            if (result.statusCode == 200) {
                this.name = result.data.shopname;
                this.$store.commit("onSubHeaderOption", {
                    title: this.$util.isNull(result.data.shopname)?"NewIN":result.data.shopname,
                    searchIcon: true,
                    cartIcon: true,
                    homeIcon: false,
                });
            }
        });
    },
    components: {
        InfiniteLoading
    },
    data() {
        return {
            name:"",
            listType: [
                {
                    id: "01",
                    label: "여성적인",
                    checked: false,
                },
                {
                    id: "02",
                    label: "심플한",
                    checked: false,
                },
                {
                    id: "03",
                    label: "안락한",
                    checked: false,
                },
                {
                    id: "04",
                    label: "뷰티",
                    checked: false,
                },
                {
                    id: "05",
                    label: "인테리어",
                    checked: false,
                },
            ],
            newinData: [],
            isloading: true,
            infiniteId: + new Date(),
            shopNewList: [],
            pagingData: {
                currentPage: 1,  // 현재 페이지
                listTotal: 0,   // 조회목록 전체 수
                listCnt: 10   // 한페이지에 노출할 목록수
            },
        };
    },
    methods: {
        /*********************
         * 리뷰목록 조회(스크롤페이징 처리)
         *********************/
        infiniteHandler($state) {
            if (this.isloading) {
                this.$http.post('/shop/newlist', this.pagingData).then(result => {
                    if (result.statusCode == 200) {
                        this.$util.debug("신상품 목록조회");
                        if (result.data.shopnewlist.length == 0) {
                            $state.complete();
                            this.isloading = false;
                        } else {
                            $state.loaded();
                            this.isloading = true;
                            this.pagingData.currentPage += 1;
                            this.pagingData.listTotal = result.data.listtotal;
                            this.shopNewList = [...this.shopNewList || [], ...result.data.shopnewlist];
                        }
                        this.$util.debug(this.shopNewList);
                    }
                });
            }
        },
    }
};