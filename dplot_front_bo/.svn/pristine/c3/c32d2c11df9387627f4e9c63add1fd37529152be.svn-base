export default {
    name: 'DelivIsolListPopup',
    mounted() {
        // 목록 조회
        this.searchDelivIsolList();
    },
    data :function(){
        return {
            searchData: {
                sword: ''
            },
            pagingData: {
              pageCount : 20,
              page : 1,
              listCount : 0
            },
            delivIsolListCnt: 0,
            delivIsolList: []
        }
    },
    methods : {
        // 페이징데이터 초기화
        initPagingData: function() {
            this.pagingData = {
                pageCount : 20,
                page : 1,
                listCount : 0
            }
        },
        // 스크롤페이징
        onScroll: function({ target: { scrollTop, clientHeight, scrollHeight }}) {
            // 테이블 스크롤이 바닥인 경우
            if (scrollTop + clientHeight + 37.08 >= scrollHeight) {
                if (this.delivIsolListCnt > this.delivIsolList.length) {
                    this.pagingData.page++;
                    this.searchDelivIsolList(false);
                }
            }
        }, 
        // 제주/도서산간지역 목록 조회
        searchDelivIsolList: function(isInit) {
            // 페이징데이터 초기화
            isInit = this.$util.isNull(isInit)? true : isInit;
            if (isInit) {
                this.initPagingData();
            }

            let params = Object.assign(this.searchData, this.pagingData);
            this.$http.post('/admin/goods/regist/isolation/list', params)
                .then(result => {
                    this.$util.debug(result);
                    let data = result.data;
                    if (isInit) {
                        this.delivIsolList = data.list;
                        this.delivIsolListCnt = data.listcount;
                        this.$refs.scrollTarget.scrollTop = 0;
                    } else {
                        this.delivIsolList = this.delivIsolList.concat(data.list);
                    }
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
    }
}