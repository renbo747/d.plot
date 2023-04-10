import PhotoReview from "@views.mobile/shop/popup/PhotoReview.vue";
import ReviewList from "@views.mobile/shop/components/ReviewList.vue";
export default {
    components: {
        ReviewList
    },
    props : ['isReview'],
    data() {
        return {
            reviewavg: 0,
            totalcnt: 0,
            reviewList: [],
            reviewFileList: [],
            reviewFileTopList: [] //10개
        }
    },
    methods: {
        /*****************************
         * 리뷰 목록 조회
         ****************************/
        getReviewInfo() {
            let param = {
                goodscode: this.$route.params.pid,
                isnopaging: "T"
            }
            this.$http.post('/review/photo/list', param).then(result => {
                if (result.statusCode == 200) {
                    this.$util.debug("리뷰 목록 조회(getReviewInfo)");
                    this.reviewFileList = result.data.fstfilelist;
                    //리뷰 파일이미지 10개 추출
                    let listLen = this.reviewFileList.length >= 5 ? 5 : this.reviewFileList.length;
                    for (let i = 0; i < listLen; i++) {
                        this.reviewFileTopList.push(this.reviewFileList[i]);
                    }
                    //리뷰총점 및 갯수 추출
                    this.reviewavg = this.$util.isNull(result.data.reviewavg)?0:result.data.reviewavg;
                    this.totalcnt = this.$util.isNull(result.data.listtotal)?0:result.data.listtotal;
                }
            })
        },
        /*****************************
         * 마이페이지 나의 리뷰페이지 이동
         ****************************/
        goMyReviewList() {
            if (this.$util.isNull(this.$route.params.pid)) {
                this.$eventBus.$emit("alert", "알림", "해당 상품 정보가 없습니다.");
                return;
            }

            if(!this.$store.state.isLogin) {
                // this.$eventBus.$emit("alert", "알림", "로그인 후 이용해주세요.");
                this.$eventBus.$emit('confirm', '로그인', '로그인 하시겠습니까?',()=>{
                    this.$storage.setSessionStorage('redirectPath', {path : this.$route.path, query : this.$route.query});
                    this.$router.push({name : 'member-login'});
                });
                return;
            }
            
            let param = {
                goodscode: this.$route.params.pid,
                isreview: "F", //리뷰 작성여부:F
                isnopaging: "T"
            }

            this.$http.post('/review/mylist', param).then(result => {
                if (result.statusCode == 200) {
                    this.$util.debug("나의 작성가능 리뷰")
                    this.$util.debug(result.data);
                    if (result.data.listtotal == 1) {
                        this.$router.push({ name: "mypage-my-review-write", query: { orderidx: result.data.reviewlist[0].orderidx, ordgdidx: result.data.reviewlist[0].ordgdidx } });
                    } else if (result.data.listtotal > 1) {
                        this.$router.push('/mypage/my-review/before');
                    } else {
                        this.$eventBus.$emit("alert", "알림", "해당 상품을 구매후 리뷰를 작성해주세요.");
                    }
                } else {
                    this.$eventBus.$emit("alert", "알림", result.message);
                }
            })
        },
        /*****************************
         *모달 팝업 오픈
         ****************************/
        openModal(modalId, param) {
            this.$util.debug(param);
            this.$eventBus.$emit('showModal', PhotoReview, modalId, param);
        }
    },
    mounted() {
        this.getReviewInfo();
        this.$util.debug("review.js.................");
    }
};