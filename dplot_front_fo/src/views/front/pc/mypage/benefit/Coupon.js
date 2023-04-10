import CouponUse from '@views.mobile/mypage/benefit/popup/CouponUse.vue'
export default {
    data() {
        return {
            downList: [],
            useCouponList: [],
            tapActive: 0,
            pagingData: {
                currentPage: 1,         // 현재 페이지
                listTotal: 0,           // 조회목록 전체 수
                listCnt: 6              // 한페이지에 노출할 목록수
            },
            downcnt: 0,     //다운로드 가능 쿠폰수
            deadlinecnt: 0, //사용임박쿠폰수
            usecnt: 0,       //사용가능쿠폰수
            serialno: "",
            isEnd:false
        };
    },
    methods: {
        /**********************
         * 데이터 초기화
         ********************/
        initData() {
            this.isEnd = false;
            this.serialno="";
            this.useCouponList = [];
            this.downList = [];
            this.pagingData.currentPage = 1;
            this.pagingData.listTotal = 0;
            this.getCouponInfo();
        },
        /*************************
         * 쿠폰 정보 조회
         *************************/
        getCouponInfo() {
            this.$util.debug(this.tapActive);
            let param = {
                isDown: this.tapActive == 0 ? "F" : "T",
                isdownload: this.tapActive == 0 ? "T" : "F",
            }
            param = Object.assign(param, this.pagingData);
            this.isEnd = false;
            this.$http.post('/coupon/mypage/list', param).then(result => {
                if (result.statusCode == 200) {
                    this.downcnt = result.data.downcnt;         // 다운가능 쿠폰수
                    this.usecnt = result.data.usecnt;          // 사용가능 쿠폰수
                    this.deadlinecnt = result.data.deadlinecnt; // 사용임박 쿠폰수

                    this.$util.debug("쿠폰 목록 조회");
                    this.$util.debug(result.data);

                    if (this.tapActive == 0) {
                        this.useCouponList = result.data.couponlist;   //다운로드 쿠폰(사용가능쿠폰)
                    } else {
                        this.downList = result.data.couponlist;   //다운로드 쿠폰(사용가능쿠폰)
                    }
                    this.pagingData.listTotal = result.data.couponlist.length > 0?result.data.couponlist[0].totcnt:0;
                }
                this.isEnd = true;
            })
        },
        /*************************
         * 쿠폰 사용안내 모달 오픈
         *************************/
        openModal() {
            this.$eventBus.$emit('showModal', CouponUse, 'couponUseModal');
        },
        /*************************
         * 프로모션 코드 등록 처리
         *************************/
         addPromotion(){
            this.$eventBus.$emit('confirm', '확인', '혜택을 등록 하시겠습니까?', () => {
                this.$util.debug(this.serialno);
                if(this.$util.isNull(this.serialno)){
                    this.$eventBus.$emit('alert', '확인' ,"프로모션 코드를 입력해주세요.");
                    return;
                }
                let param ={
                    serialno:this.serialno,
                    srptype : "SRT003" //쿠폰
                }

                this.$http.post('/mypage/addPromotionCode', param).then(result => {
                    if (result.statusCode == 200) {
                        this.$toast.clear();
                        this.$toast.open("혜택등록이 완료되었습니다.");
                        this.initData();
                    }
                });
            });
        },
        /*************************
         * 쿠폰 다운로드 처리
         *************************/
         downloadCoupon(coupon) {
            this.$http.post('/coupon/download', coupon).then(result => {
                if (result.statusCode == 200) {
                    this.$toast.clear();
                    this.$toast.open("쿠폰 받기가 완료 되었어요.");
                    this.initData();
                }
            });
        },
        /*************************
         * 전체 쿠폰 다운로드 처리
         *************************/
        downloadAll() {
            this.$http.post('/coupon/pageDownload/all', {}).then(result => {
                if (result.statusCode == 200) {
                    this.$toast.clear();
                    this.$toast.open("쿠폰 받기가 완료 되었어요.");
                    this.initData();
                }
            });
        },
        /*****************************
         * 페이지 변경시
         ****************************/
         changePage(page) {
            this.pagingData.currentPage = page;
            this.useCouponList = [];
            this.downList = [];
            this.getCouponInfo();
        },
    },
    mounted() {
        this.initData();
    },
    watch: {
        'tapActive'() {
            this.initData();
        }
    }
};