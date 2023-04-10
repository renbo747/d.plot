export default {
    data() {
        return {
            messageList: [],
            pagingData: {
                currentPage: 1,         // 현재 페이지
                listTotal: 0,           // 조회목록 전체 수
                listCnt: 5              // 한페이지에 노출할 목록수
            },
            isEnd:false
        };
    },
    methods: {
        /**********************
         * 데이터 초기화
         ********************/
        initData() {
            this.pagingData.currentPage = 1;
            this.pagingData.listTotal = 5;
            this.getMessageList();
        },
        /**********************
         * 메시지 목록 조회
         ********************/
        getMessageList() {
            let param = Object.assign(this.pagingData);
            this.isEnd = false;
            this.$http.post('/mypage/msgList', param).then(result => {
                if (result.statusCode == 200) {
                    this.$util.debug("재입고 상품 목록 조회");
                    this.messageList = result.data.messagelist;
                    this.pagingData.listTotal = result.data.listtotal;
                    this.messageList.forEach(item => {
                        item.message = item.message.replaceAll('\n', '<br/>');
                    });
                    this.$util.scrollToTop();
                }
                this.isEnd = true;
            })
        },
        /*****************************
         * 자식으로 부터
         ****************************/
        changePage(page) {
            this.pagingData.currentPage = page;
            this.getMessageList();
        },
        /*********************************
         * 메시지 삭제
         ********************************/
        deleteMessage(msgidx) {
            let param = {
                msgidx: msgidx
            }
            this.$http.post("mypage/msgList/del", param).then((result => {
                if (result.statusCode == 200) {
                    this.$util.debug("삭제완료");
                    this.initData();
                }
            }))
        },
        /*********************************
        * message에 링크있을경우 열기
        ********************************/
        goLink(url) {
            if (!this.$util.isNull(url)) {
                window.open(url, "_blank");
            }
        }
    },
    mounted() {
        this.initData();
    }
};