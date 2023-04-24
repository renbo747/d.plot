import { swiper, swiperSlide } from "vue-awesome-swiper";
import SnsShareModal from "@views.mobile/shop/popup/SnsShareModal.vue";
import PostReport from "@views.mobile/shop/promotion/popup/PostReport.vue";

export default {
    components: {
        swiper,
        swiperSlide,
        PostReport
    },
    data() {
        return {
            eventidx: 0,
            currentPage: 1,        //현재페이지
            totalPage: 5,         // 페이징영역 노출 최대 페이지(1~N, 1+N~N+N)        
            perPage: 5,        //페이지 목록 수  
            commentListTotal: 0,    //댓글 리스트 총 갯수
            skey: 0,

            Detail: {},
            goodsGroup: [],
            goodsList: [],          // 프로모션 상품 리스트
            comment: "",            //댓글 작성 textarea depth 0
            commentList: [],        //댓글 리스트
            reviseComment: [],      //댓글 수정창 열기
            reviseCmtList: [],      //댓글 수정 textarea depth 0
            reply: [],              //대댓글 textarea depth 1
            replyWrite: [],        //대댓글 작성창 열기
            ReviseOpen: [],        //대댓글 수정창 열기
            reviseRlyList: [],      //대댓글 수정 textarea depth 1
            check: {
                id: 'check01',
                checked: false,
            },
            check02: {
                id: 'check02',
                checked: false,
            },
            // promotionSwiper: {
            //     //slidesPerView: 4,
            //     spaceBetween: 20,
            //     //slidesPerGroup: 4,
            //     loop: false,
            //     loopFillGroupWithBlank: true,
            //     pagination: {
            //         el: ".swiper-pagination",
            //         clickable: true,
            //     },
            // },
            promotionSwiper: {
                slidesPerView: 4,
                spaceBetween: 20,
                slidesPerGroup: 4,
                pagination: {
                    el: ".swiper-pagination",
                    clickable: true,
                },
            },
        };
    },
    methods: {
        /**************************
         * 상세 메인 진입
         ****************************/
        onInit(param) {
            this.$util.debug("onInit.......");
            //1:1 문의 데이터 로드
            // param.iskeep = true;
            // param.reqname = this.$route.name;
            this.$http.post('/shop/promotiondtl', param).then(result => {
                if (result.statusCode == 200) {
                    this.$util.debug("onInit success.......");
                    if (this.$util.isNull(result.data.list)) {
                        this.$eventBus.$emit("alert", "알림", "전시중인 프로모션이 아닙니다.", () => {
                            this.$router.go(-1);
                        });
                    }
                    this.Detail = result.data.list;
                    this.goodsList = result.data.goodslist;
                    this.goodsList.forEach(item => {
                        item.saleamt = item.saleamt - item.goodscpnamt;
                    });
                }
            })
        },
        goBrand(brandidx) {
            this.$router.push({ name: 'magazine-brand-detail', params: { bid: brandidx } });
        },
        /**************************
         * 댓글 등록 버튼 클릭 이벤트
         ****************************/
        regComment() {
            this.$util.debug("regComment ..........");
            if (this.comment == "") {
                this.$eventBus.$emit('alert', '확인', "내용을 입력해주세요.");
                return;
            }

            var param = {
                depth: 0,
                eventidx: this.eventidx,
                comment: this.comment,
                startpage: 1,
                perpage: this.perPage,
            }

            this.saveComment(param);
        },
        /**************************
         * 댓글 등록 처리
         ****************************/
        saveComment(param) {
            if (!this.$store.state.isLogin) {
                this.$eventBus.$emit('confirm', '로그인', '로그인 하시겠습니까?', () => {
                    this.$storage.setSessionStorage('redirectPath', { path: this.$route.path, query: this.$route.query });
                    this.$router.push({ name: 'member-login' });
                });
                return;
            }
            this.$http.post('/shop/savecomment', param).then(result => {
                if (result.statusCode == 200) {
                    this.$util.debug("onInit success.......");
                    if (result.data.msg === 'success') {
                        this.$eventBus.$emit('alert', '확인', "등록이 완료되었습니다.");
                        this.comment = "";
                        this.reply = [];
                        this.replyWrite = [];
                        this.getComment(param);
                    } else {
                        this.$eventBus.$emit('alert', '확인', "등록에 실패하였습니다.");
                    }
                }
            })

        },
        /**************************
         * 댓글 리스트 호출
         ****************************/
        getComment(param) {
            this.$util.debug("commentList .......");
            this.$http.post('/shop/commentlist', param).then(result => {
                if (result.statusCode == 200) {
                    this.$util.debug("onInit success.......");
                    this.commentList = result.data.cmtlist;
                    this.commentListTotal = result.data.cmtlistcnt;
                    this.currentPage = param.startpage;
                }
            })
        },
        /**************************
         * 대댓글 취소
         ****************************/
        commentCancle(index) {
            this.$util.debug("commentCancle .......");
            this.$util.debug(this.reply);
            this.reply[index] = "";
            this.replyWrite[index] = false;
            this.skey = Date.now();
        },
        /**************************
         * 대댓글 등록 버튼 이벤트
         ****************************/
        saveReply(index, commentidx) {
            this.$util.debug("saveReply .......");
            this.$util.debug(index);
            this.$util.debug(commentidx);

            if (this.reply[index] === undefined) {
                this.$eventBus.$emit('alert', '확인', "내용을 입력해주세요");
                return;
            }

            var param = {
                depth: 1,
                commentidx: commentidx,
                eventidx: this.eventidx,
                comment: this.reply[index],
                startpage: this.currentPage,
                perpage: this.perPage,
            }
            this.$util.debug(param);

            //대댓글 저장
            this.saveComment(param);

        },
        /**************************
         * 답글쓰기창 열기
         ****************************/
        openReply(index) {
            this.$util.debug("openReply .......");
            this.skey = Date.now();
            this.replyWrite[index] = true;

        },
        /**************************
         * 페이징
         ****************************/
        changePage(page) {
            this.$util.debug("changePage .......");
            var param = {
                eventidx: this.eventidx,
                startpage: page,
                perpage: this.perPage,
            }

            this.getComment(param);
        },
        /**************************
         * 댓글 수정창 열기
         ****************************/
        reviseCmt(index) {
            this.$util.debug("reviseCmt .......");
            this.reviseComment[index] = true;
            this.skey = Date.now();
            this.reviseCmtList[index] = this.commentList[index].comment;
        },
        /**************************
         * 댓글 수정창 닫기
         ****************************/
        closeCmt(index) {
            this.$util.debug("closeCmt .......");
            this.reviseComment[index] = false;
            this.skey = Date.now();
        },
        /**************************
         * 대댓글 수정창 열기
         ****************************/
        reviseReply(index, index2) {
            this.$util.debug("reviseReply .......");
            this.ReviseOpen[index2] = true;
            this.skey = Date.now();
            this.reviseRlyList[index2] = this.commentList[index].reply[index2].comment;
        },
        /**************************
         * 대댓글 수정창 닫기
         ****************************/
        reviseRlyClose(index, index2) {
            this.$util.debug("reviseRlyClose .......");
            this.ReviseOpen[index2] = false;
            this.skey = Date.now();
        },
        /**************************
         * 댓글 수정모드 에서 수정 버튼 클릭 이벤트
         ****************************/
        goReviseComment(index, commentidx) {
            this.$util.debug("reviseComment .......");
            this.$util.debug(index);
            this.$util.debug(commentidx);
            this.$util.debug(this.reviseCmtList[index]);

            if (this.reviseCmtList[index] == "") {
                this.$eventBus.$emit('alert', '확인', "내용을 입력해주세요.");
                return;
            }
            if (this.reviseCmtList[index] == this.commentList[index].comment) {
                this.$eventBus.$emit('alert', '확인', "수정할 내용이 없습니다.");
                return;
            }
            var param = {
                comment: this.reviseCmtList[index],
                commentidx: commentidx,
                depth: 0,
                eventidx: this.eventidx,
                startpage: this.currentPage,
                perpage: this.perPage,
            }
            this.updateComment(param);
        },
        /**************************
         * 댓글 삭제 클릭 이벤트
         ****************************/
        deleteCmt(commentidx) {
            this.$util.debug("deleteCmt .......");
            this.$util.debug(commentidx);

            this.$eventBus.$emit('confirm', '확인', '댓글을 삭제하시겠습니까?', () => {
                var param = {
                    commentidx: commentidx,
                    depth: 0,
                    eventidx: this.eventidx,
                    startpage: this.currentPage,
                    perpage: this.perPage,
                }
                this.deleteComment(param);
            });
        },
        /**************************
         * 대댓글 수정모드에서 수정 버튼 클릭 이벤트
         ****************************/
        updateReply(index, index2, commentidx) {
            if (this.reviseRlyList[index2] == "") {
                this.$eventBus.$emit('alert', '확인', "내용을 입력해주세요.");
                return;
            }
            if (this.reviseRlyList[index2] == this.commentList[index].reply[index2].comment) {
                this.$eventBus.$emit('alert', '확인', "수정할 내용이 없습니다.");
                return;
            }

            var param = {
                comment: this.reviseRlyList[index2],
                commentidx: commentidx,
                depth: 1,
                eventidx: this.eventidx,
                startpage: this.currentPage,
                perpage: this.perPage,
            }
            this.updateComment(param);
        },
        /**************************
         * 대댓글 삭제 클릭 이벤트
         ****************************/
        deleteReply(commentidx) {
            this.$util.debug("deleteReply .......");
            this.$util.debug(commentidx);

            this.$eventBus.$emit('confirm', '확인', '댓글을 삭제하시겠습니까?', () => {
                var param = {
                    commentidx: commentidx,
                    depth: 1,
                    eventidx: this.eventidx,
                    startpage: this.currentPage,
                    perpage: this.perPage,
                }
                this.deleteComment(param);
            });
        },
        /**************************
         * 댓글 삭제 처리
         ****************************/
        deleteComment(param) {
            this.$util.debug("deleteComment .......");
            this.$http.post('/shop/deletecmt', param).then(result => {
                if (result.statusCode == 200) {
                    this.$util.debug("onInit success.......");
                    if (result.data.msg === 'success') {
                        this.$eventBus.$emit('alert', '확인', "삭제가 완료되었습니다.");
                        this.comment = "";
                        this.reply = [];
                        this.replyWrite = [];
                        this.reviseComment = [];
                        this.ReviseOpen = [];
                        this.reviseRlyList = [];
                        this.getComment(param);
                    } else {
                        this.$eventBus.$emit('alert', '확인', "삭제에 실패하였습니다.");
                    }
                }
            })
        },
        /**************************
         * 댓글 수정 처리
         ****************************/
        updateComment(param) {
            this.$util.debug("deleteComment .......");
            this.$http.post('/shop/updatecmt', param).then(result => {
                if (result.statusCode == 200) {
                    this.$util.debug("onInit success.......");
                    if (result.data.msg === 'success') {
                        this.$eventBus.$emit('alert', '확인', "수정이 완료되었습니다.");
                        this.comment = "";
                        this.reply = [];
                        this.replyWrite = [];
                        this.reviseComment = [];
                        this.ReviseOpen = [];
                        this.reviseRlyList = [];
                        this.getComment(param);
                    } else {
                        this.$eventBus.$emit('alert', '확인', "수정에 실패하였습니다.");
                    }
                }
            })
        },
        /**************************
         * 댓글 신고 처리
         ****************************/
        repReport(commentIdx) {
            if (this.$store.state.isLogin) {
                var param = { commentidx: commentIdx };
                // this.$eventBus.$emit('confirm', '확인' ,'해당 글을 신고하시겠습니까?' , ()=>{
                //     this.$http.post('/shop/repreport', param).then(result => {
                //         if (result.statusCode == 200) {
                //             this.$util.debug("onInit success.......");
                //             if (result.data.msg === 'success') {
                //                 this.$eventBus.$emit('alert', '확인', "신고가 접수되었습니다.");
                //                 param.eventidx = this.eventidx;
                //                 param.startpage = this.currentPage;
                //                 param.perpage = this.perPage;
                //                 this.getComment(param);
                //             }else{
                //                 this.$eventBus.$emit('alert', '확인', "잠시 후 다시 시도해주세요.");
                //             }

                //         }
                //     })
                // }); 
                this.$eventBus.$emit('showModal', PostReport, 'postReportModal', param);
            } else {
                // this.$eventBus.$emit('alert', '확인', "로그인 후 이용해주세요.");
                this.$eventBus.$emit('confirm', '로그인', '로그인 하시겠습니까?', () => {
                    this.$storage.setSessionStorage('redirectPath', { path: this.$route.path, query: this.$route.query });
                    this.$router.push({ name: 'member-login' });
                });
                //this.$router.push('/member/login');
            }
        },
        /**********************
         * SNS 공유하기 모달
         ********************/
        snsShareModal() {
            this.description = "기간: " + this.Detail.startdate + "~" + this.Detail.enddate;
            let param = {
                kakao: {
                    objectType: "feed",
                    content: {
                        title: "[D.PLOT]" + "[" + this.Detail.eventname + "]" + this.Detail.subject,
                        description: '',
                        imageUrl: this.Detail.fullpath,
                        link: {
                            mobileWebUrl: window.location.href,
                            webUrl: window.location.href
                        },
                    },
                },
                meta: {
                    title: "[D.PLOT]" + "[" + this.Detail.eventname + "]" + this.Detail.subject,
                    summary: "상세내용",
                    img: this.Detail.fullpath
                }
            }
            this.$eventBus.$emit('showModal', SnsShareModal, "snsShareModal", param);
        }
    },
    // created() {
    //     if (this.$route.params.init) {
    //         this.$storage.removeSessionStorage('param-' + this.$route.name);
    //     } else {
    //         const param = this.$storage.getSessionStorage('param-' + this.$route.name);
    //     }
    // },
    mounted() {
        this.eventidx = this.$route.params.eventidx;
        var param = {
            eventidx: this.eventidx,
            startpage: 1,
            perpage: this.perPage,
        };
        this.onInit(param);
        this.getComment(param);
    },
    // updated() {
    //     this.$nextTick(() => {
    //         if (this.goodsList.length > 0) {
    //             this.$eventBus.$emit('scrollTo', this.$route.name, (flag) => {
    //                 if (!flag) {
    //                     window.scrollTo(0, 0);
    //                 }
    //             });
    //         } else {
    //             window.scrollTo(0, 0);
    //         }
    //     })
    // }
};