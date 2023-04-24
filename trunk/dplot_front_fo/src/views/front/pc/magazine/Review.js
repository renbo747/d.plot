import { swiper, swiperSlide } from "vue-awesome-swiper";
import PhotoReviewPc from "@views.pc/shop/popup/PhotoReviewPc.vue";
import ReviewLiveList from "@views.mobile/magazine/component/ReviewLiveList.vue";

export default {
    components: {
        swiper,
        swiperSlide,
        ReviewLiveList
    },
    data() {
        return {
            bestReviewSwiperOptionPc: {
                slidesPerView: 1,
                loop: true,
                spaceBetween:10,
                pagination: {
                    el: ".swiper-pagination",
                },
                navigation: {
                    nextEl: ".swiper-button-next",
                    prevEl: ".swiper-button-prev",
                },
            },

            bestReviewList: [],
            bestFileTopList: [],
        };
    },
    methods: {
        getReviewList() {
            let param = {
                isbest: "T",
                limit: 5
            }
            this.$http.post('/review/mzReview', param).then(result => {
                if (result.statusCode === 200) {
                    this.$util.scrollToTop();

                    let list = result.data.reviewlist;
                    for (let i = 0; i < list.length; i++) {
                        let optionnames = list[i].optionnm.split(',');
                        list[i].opthtml = '';
                        if (optionnames.length > 0) {
                            if (list[i].isuseaddition == "T") {
                                optionnames[0] = '추가상품: ' + optionnames[0];
                                list[i].optionname = '추가상품: ' + list[i].goodsname + ' ' + list[i].optionnm.replace(',', ' ');
                            } else {
                                optionnames[0] = '옵션: ' + optionnames[0];
                                list[i].optionname = '옵션: ' + list[i].goodsname + ' ' + list[i].optionnm.replace(',', ' ');
                            }
                            for (let i = 0; i < optionnames.length; i++) {
                                optionnames[i] = '<span>' + optionnames[i] + '<span>';
                            }
                            list[i].opthtml = optionnames.join('<span class="dp-bar h10"></span>');
                        }
                    }
                    this.bestReviewList = result.data.reviewlist;
                    this.bestFileTopList = result.data.fstfilelist;
                }
            })
        },
        /*****************************
         *모달 팝업 오픈
         ****************************/
        openModal(modalId, param) {
            this.$eventBus.$emit('showModal', PhotoReviewPc, modalId, param);
        }
    },
    mounted() {
        this.$util.scrollToTop();
        this.getReviewList();
    }
};

