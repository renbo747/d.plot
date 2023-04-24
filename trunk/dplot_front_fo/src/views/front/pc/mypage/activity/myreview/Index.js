import { swiper, swiperSlide } from "vue-awesome-swiper";
import ImageModal from '@views.mobile/mypage/activity/myreview/popup/ImageModal.vue';
import ReviewReward from '@views.mobile/mypage/activity/myreview/popup/ReviewReward.vue'
export default {
    components: {
        swiper,
        swiperSlide,
    },
    data() {
        return {
            isMediaType: true, // Todo : 미디어 타입으로 이미지와 영상 표시 구분 필요.
            isModalOpen: false,
            selectedIndex: "",
            imgSwiperOption: {
                slidesPerView: "auto",
                spaceBetween: 10,
                observeParents: true,
                observer: true,
                navigation: {
                    nextEl: ".img-swiper-next",
                    prevEl: ".img-swiper-prev",
                },
            },
            reviewList: [],
            pagingData: {
                currentPage: 1,  // 현재 페이지
                listTotal: 0,   // 조회목록 전체 수
                listCnt: 5 ,  // 한페이지에 노출할 목록수
                init: 'F'
            },
            isEnd:false
        };
    },
    methods: {
        /*****************************
         * 나의 리뷰 목록 조회
         ****************************/
        getMyReviewList() {
            this.$util.scrollToTop();
            var param = this.pagingData;
            param.isreview = this.$route.name == "before" ? "F" : "T";
            param.iskeep = true;
            param.reqname = this.$route.name;
            this.isEnd = false;
            this.$http.post('/review/mylist', param).then(result => {
                if (result.statusCode == 200) {
                    this.$util.debug("(pc)나의 리뷰 목록 조회");
                    let list = result.data.reviewlist;
                    this.pagingData.listTotal = result.data.listtotal;

                    //옵션명 설정
                    for (let i = 0; i < list.length; i++) {
                        let optionnames = list[i].optionnm.split(',');
                        list[i].opthtml = '';
                        if (!this.$util.isBlank(list[i].optionnm) && optionnames.length > 0) {
                            optionnames[0] = list[i].isaddgoods == "T" ? "추가상품: " : "옵션: " + optionnames[0];
                            for (let i = 0; i < optionnames.length; i++) {
                                optionnames[i] = '<span>' + optionnames[i] + '</span>';
                            }
                            list[i].opthtml = optionnames.join('<span class="dp-bar h10"></span>');
                        }
                    }
                    this.reviewList = list;
                }
                this.isEnd = true;
            })
        },
        /*****************************
         * 탭 전환
         ****************************/
        changeTap(name) {
            this.pagingData.currentPage = 1;
            this.isEnd = false;
            this.$router.replace({ name: name , params:{init:true}});
        },
        /*****************************
         * 자식으로 부터
         ****************************/
        changePage(page) {
            this.pagingData.currentPage = page;
            this.$store.commit("savedPosition", {});
            this.getMyReviewList();
        },
        /******************************
         * 리뷰 삭제
         *******************************/
        deleteReview(list) {
            this.$util.debug("삭체 처리...");
            this.$util.debug(JSON.stringify(list));
            if (list.isdeadline == "T") {
                this.$eventBus.$emit('alert', '알림', "리뷰 삭제는 작성후 3일이내에 가능합니다.");
                return;
            }
            if (list.isbest == "T") {
                this.$eventBus.$emit('alert', '알림', "베스트에 선정된 리뷰는 삭제할수 없습니다.");
                return;
            }
            this.$eventBus.$emit('confirm', '확인', '작성하신 리뷰를 삭제하시겠습니까? 리뷰 삭제 시 재작성은 불가 합니다.', () => {
                let param = {
                    reviewidx: list.reviewidx,
                    regdate: list.regdatefull
                }
                this.$http.post('/review/delete', param).then(result => {
                    if (result.statusCode == 200) {
                        this.$toast.clear();
                        this.$toast.open('리뷰가 삭제되었습니다.');
                        this.changePage(1);
                    }
                })
            });
        },
        goShopDetail(id) {
            this.$router.push({ name: "shop-detail", params: { pid: id } });
        },
        /*****************************
         * 리뷰 상세 페이지이동
         ****************************/
        goToReviewWrite(list) {
            if (list.isdeadline == "T") {
                this.$eventBus.$emit('alert', '알림', "리뷰 수정은 작성후 3일이내에 가능합니다.");
                return;
            }
            this.$router.replace({ name: "mypage-my-review-write", query: { orderidx: list.orderidx, ordgdidx: list.ordgdidx } });
        },
        /*****************************
         * 이미지 모달 열기
         ****************************/
        openModal(files, index) {
            let param = {
                files: files,
                filetype: files[index].filetype,
                index: index
            }
            this.$eventBus.$emit('showNoBvModal', ImageModal, param);
        },
        showModal(modalId) {
            if (modalId == 'reviewRewardModal') {
                this.$eventBus.$emit('showModal', ReviewReward, modalId);
            }
        }
    },
    created() {
        if (this.$route.params.init) {
            this.$storage.removeSessionStorage('param-' + this.$route.name);
        } else {
            const param = this.$storage.getSessionStorage('param-' + this.$route.name);
            if (!this.$util.isEmpty(param)) {
                this.pagingData.currentPage = param.currentPage;
            }
        }
    },
    mounted() {
        this.getMyReviewList();
    },
    updated() {
        this.$nextTick(() => {
            if (this.reviewList.length > 0) {
                this.$eventBus.$emit('scrollTo', this.$route.name, (flag) => {
                    if (!flag) {
                        window.scrollTo(0, 0);
                    }
                });
            } else {
                window.scrollTo(0, 0);
            }
        })
    },
    watch: {
        '$route.name'(val) {
            this.getMyReviewList();
        }
    },
};