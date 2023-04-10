import PostReport from "@views.mobile/shop/popup/PostReport.vue";
import PhotoReview from "@views.mobile/shop/popup/PhotoReview.vue";
export default {
    props: {
        reviewFileTopList: {
            type: Array,
            default: []
        }
    },
    data() {
        return {
            searchData: {
                issearch: "T",
                isfile: "reviewall",
                order: "goodcnt",
                goodscode: this.$route.params.pid
            },
            reviewType: [
                {
                    id: "reviewall",
                    label: "전체",
                },
                {
                    id: "T",
                    label: "포토 & 동영상",
                },
                {
                    id: "F",
                    label: "텍스트",
                },
            ],
            reviewData: [],
            selectOptionData: "",
            sampleSelectOptions: [
                {
                    label: "좋아요순",
                    value: "goodcnt",
                },
                {
                    label: "최신순",
                    value: "new",
                },
                {
                    label: "평점높은순",
                    value: "pointtop",
                },
                {
                    label: "평점낮은순",
                    value: "pointlow",
                },
            ],
            isloading: true,
            infiniteId: + new Date(),
            reviewList: [],
            limit: 0,
            pagingData: {
                currentPage: 1,  // 현재 페이지
                listTotal: 0,   // 조회목록 전체 수
                listCnt: 5   // 한페이지에 노출할 목록수
            },
            files:[],
            deletelist:[]
        };
    },
    methods: {
        /*********************
        * 리뷰 더보기 버튼 이벤트
        *********************/
        addReviewList() {
            this.$util.debug(this.searchData);
            this.currentPage += 1;
            this.getReviewList();
        },
        /*********************
         * 페이징 데이터 초기화
         * *******************/
        initPagingData() {
            this.pagingData = {
                currentPage: 1,  // 현재 페이지
                listTotal: 0,   // 조회목록 전체 수
                listCnt: 5   // 한페이지에 노출할 목록수
            }
            this.reviewList = [];
            this.getReviewList();
        },
        /*********************
         * 리뷰 목록 조회
         *********************/
        getReviewList() {
            let param = Object.assign(this.pagingData, this.searchData);
            this.$http.post('/review/list', param).then(result => {
                if (result.statusCode == 200) {
                    this.$util.debug("리뷰 목록 조회");
                    this.$util.debug(result.data);
                    this.reviewList = [...this.reviewList || [], ...result.data.reviewlist];
                    
                    for(let i=0;i<this.reviewList.length;i++) {
                        //this.reviewList[i].content = this.reviewList[i].content.replaceAll("<br/>", "");
                    }
                    
                    this.pagingData.listTotal = this.$util.isNull(result.data.listtotal) ? 0 : result.data.listtotal;
                    this.pagingData.currentPage += 1;
                    this.$util.debug(this.reviewList);                    
                }
            });
        },
        /****************************
        * 모달 오픈
         ***************************/
        openModal(modalId, param) {
            this.$util.debug("모달");
            if (modalId == "postReportModal") {
                if(!this.$store.state.isLogin) {
                    this.$eventBus.$emit('confirm', '로그인', '로그인 하시겠습니까?',()=>{
                        this.$storage.setSessionStorage('redirectPath', {path : this.$route.path, query : this.$route.query});
                        this.$router.push({name : 'member-login'});
                    });
                    return;
                }
                this.$eventBus.$emit("showModal", PostReport, modalId, param);
            } else if (modalId == "photoReviewModal") {
                this.$eventBus.$emit("showModal", PhotoReview, modalId, param);
            }
        },
        /****************************
        * 리뷰 삭제
         ***************************/
        deleteReview(list) {
            let param ={
                reviewidx:list.reviewidx,
                regdate:list.regdatefull
            }
            this.$util.debug(list);

            this.$eventBus.$emit('confirm', '확인' ,'리뷰를 삭제하시겠습니까?' , ()=>{
                this.$http.post('/review/delete', param).then(result => {
                    if (result.statusCode == 200) {
                        this.$eventBus.$emit('alert', '알림', "리뷰를 삭제하였습니다.",()=>{
                            this.initPagingData();
                        });
                    }else {
                        this.$eventBus.$emit('alert', '알림', result.message);
                    }
                });
            });
        },
        goToReviewWrite(list) {
            this.$router.push({ name: "mypage-my-review-write", query: { orderidx: list.orderidx, ordgdidx: list.ordgdidx } });
        },
    },
    mounted() {
        this.initPagingData();
    },
    watch: {
        'searchData.isfile'(value) {
            this.$util.debug("searcData 값 변경 감지isfile...");
            this.initPagingData();
        },
        'searchData.order'(value) {
            this.$util.debug("searcData 값 변경 감지order...");
            this.initPagingData();
        }
    }
};