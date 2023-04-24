export default {
    name: 'SearchBrandListPopup',
    props : {
        modalComp: Object,
        params: Object,
        callbackFn: Function
    },
    mounted() {
        this.$refs.sword.focus();
        this.searchData.sword = this.params.brandname;
        this.searchData.istrash = this.params.istrash;
        this.searchBrandList();
    },
    data :function(){
        return {
            searchData: {
                skey: 'brandname',
                sword: '',
                istrash: '',
                psort: 'code_asc'
            },
            pagingData: {
              pageCount : 20,
              page : 1,
              listCount : 0
            },
            sortData: {
                code : 'code_asc',
                brandname : 'brandname_asc',
                enname : 'enname_asc',
            },
            issearch: false,
            brandList: [],
            brandListCnt: 0
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
        // 브랜드 목록 조회
        searchBrandList: function(isInit) {
            this.issearch = true;
            // 페이징데이터 초기화
            isInit = this.$util.isNull(isInit)? true : isInit;
            if (isInit) {
                this.initPagingData();
            }

            let params = Object.assign(this.searchData, this.pagingData);
            this.$http.post('/admin/goods/regist/brand/list', params)
                .then(result => {
                    this.$util.debug(result);
                    let data = result.data;
                    if (isInit) {
                        this.brandList = data.list;
                        this.brandListCnt = data.listcount;
                        this.$refs.scrollTarget.scrollTop = 0;
                    } else {
                        this.brandList = this.brandList.concat(data.list);
                    }
                    setTimeout(function () {
                        this.issearch = false;
                    }.bind(this), 50);
                })
                .catch(error => {
                    this.$util.debug(error);
                    setTimeout(function () {
                        this.issearch = false;
                    }.bind(this), 50);
                });
        },
        // 테이블 정렬
        sortToggle: function(key){
            let arr = key.split("_");
            let sortKey = arr[0];
            let sortOrder = (arr[1] === 'asc') ? 'desc' : 'asc';
            let sortName = sortKey + '_' + sortOrder;
            this.sortData = this.$options.data().sortData;
        
            this.sortData[sortKey] = sortName;
            this.searchData.psort = sortName;
        
            this.searchBrandList();
        },
        // 스크롤페이징
        onScroll: function({ target: { scrollTop, clientHeight, scrollHeight }}) {
            // 테이블 스크롤이 바닥인 경우
            if (!this.issearch && scrollTop + clientHeight + 37.08 >= scrollHeight) {
                if (this.brandListCnt > this.brandList.length) {
                    this.pagingData.page++;
                    this.searchBrandList(false);
                }
            }
        }, 
        // 선택한 데이터 전달
        sendData: function(brandObj) {
            if( typeof(this.callbackFn) == 'function') {
                this.callbackFn(brandObj);
            }
            this.$modal.hide('commonModal');
        },
    }
}