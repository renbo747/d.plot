export default {
    name: 'GoodsApprHistoryPopup',
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
            goodsno: '',        // 상품번호
            goodscode: '',      // 상품코드
            goodsname: '',      // 상품명
            dealerno: '',       // 입점업체번호
            dealername: '',     // 입점업체명
            apprhistList: [],   // 처리이력목록
        }
    },
    methods : {
        // 화면초기화
        onInit: function() {
            this.goodsno = this.params.goodsno;
            this.searchList();
        },
        // 상품 처리이력목록 조회
        searchList: function() {
            this.$http.post('/admin/goods/manage/apprhist/list', { goodsno: this.goodsno })
                .then(result => {
                    let data = result.data;
                    this.goodscode = data.info.goodscode;
                    this.goodsname = data.info.goodsname;
                    this.dealerno = data.info.dealerno;
                    this.dealername = data.info.dealername;
                    this.apprhistList = data.list;
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        }
    }
}