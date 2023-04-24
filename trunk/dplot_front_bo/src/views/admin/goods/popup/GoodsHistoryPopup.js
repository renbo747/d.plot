export default {
    name: 'GoodsHistoryPopup',
    props : {
        modalComp: Object,
        params: Object,
        callbackFn: Function
    },
    mounted() {
        // 초기화
        this.onInit();
    },
    data :function(){
        return {
            sortData: {
                regdate : 'regdate_desc',
            },
            info: {
                goodsno: '',         // 상품일련번호
                goodscode: '',       // 상품코드
                goodsname: '',       // 상품명
                goodshistlist: []    // 상품이력목록
            }
        }
    },
    methods : {
        // 초기화면
        onInit: function() {
            // 넘어온 파라미터 세팅
            this.info.goodsno = this.params.goodsno;
            // 상품이력 목록 조회
            this.searchList();
        },
        // 상품이력 목록 조회
        searchList: function() {
            this.$http.post('/admin/goods/manage/goodshist/list', { goodsno: this.info.goodsno })
                .then(result => {
                    this.$util.debug(result);
                    this.info = result.data;
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 테이블 정렬
        sortToggle: function(key){
            let arr = key.split("_");
            let sortKey = arr[0];
            let sortOrder = (arr[1] === 'asc') ? 'desc' : 'asc';
            let sortName = sortKey + '_' + sortOrder;
            this.sortData[sortKey] = sortName;
        
            this.info.goodshistlist.sort((a, b) => {
                a[sortKey] = this.$util.isNull(a[sortKey]) ? '' : a[sortKey];
                b[sortKey] = this.$util.isNull(b[sortKey]) ? '' : b[sortKey];
                if (a[sortKey] < b[sortKey]) {
                    return sortOrder == 'asc'? -1: 1;
                } else if (a[sortKey] > b[sortKey]) {
                    return sortOrder == 'asc'? 1: -1;
                }
                return 0;
            });
        },
    }
}