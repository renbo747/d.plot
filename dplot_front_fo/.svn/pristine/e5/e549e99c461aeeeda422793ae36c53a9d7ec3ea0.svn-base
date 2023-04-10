import Common from "@views.mobile/nonemember/shop/Common.js";
import TossPayment from '@views.mobile/mypage/shop/claim/TossPayment.js'
import { swiper, swiperSlide } from "vue-awesome-swiper";
import ImageModal from '../../../mypage/shop/claim/popup/ImageModal.vue';

export default {
    mixins : [Common, TossPayment],
    components : {
        swiper,
        swiperSlide,
    },
    beforeCreate() {
        let title = "취소상세";
        if(this.$route.name.indexOf('return') > -1) {
            title = '반품상세';
        } else if(this.$route.name.indexOf('exchange') > -1) {
            title = '교환상세';
        } 
        this.$store.commit("onSubHeaderOption", {
            title: title,
            searchIcon: true,
            cartIcon: true,
            homeIcon: false,
        });
    },
    data () {
        return {
            userno : 0,
            claimType : 'CLM001', // CLM001 : 취소 , CLM002 : 반품 , CLM003 : 교환
            claimName : '취소',
            orderInfo : {},
            claimInfo : {
                clmreqdate : ''
            },
            claimList : [],
            dealers : [],
            paywaylist : [],  // 결제수단
            cardlist : [],    // 카드종류
            cardPlan : [],    // 할부개월
            payInfo : {       // 결제정보
                paywaytype : 'PWT001', // 결재수단
                cardCompany : '',
                cardInstallmentPlan : '0',
                useEscrow : false, // 에스크로사용여부
            },
            item : [],
            excOption : null,
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
            imgSwiperOption01: {
                slidesPerView: "auto",
                spaceBetween: 10,
                observeParents: true,
                observer: true,
            },
        }
    },
    mounted() {
        if(this.$route.name.indexOf('return') > -1) {
            this.claimType = 'CLM002';
            this.claimName = '반품';
        } else if(this.$route.name.indexOf('exchange') > -1) {
            this.claimType = 'CLM003';
            this.claimName = '교환';
        } 
        this.getClaimDetail();
    },
    methods : {
        getClaimDetail() {
            const param = {
                clmno : this.$route.params.clmno
            }
            this.$http.post('/nonemember/claim/detail', param).then(result => {
                if (result.statusCode == 200) {
                    //주문상세
                    this.orderInfo = result.data.order;

                    //클레임상세
                    this.claimInfo = result.data.claim;
                    this.claimInfo.clmreqdate = this.claimInfo.clmreqdate.replace(/-/gi, '').replace(/:/gi, '');
                    
                    //클레임상품
                    this.claimList = result.data.list;

                    //할부
                    this.cardPlan.push({value : '0', label : '일시불'});
                    for(let i = 2 ; i <= 12 ; i++) {
                        this.cardPlan.push({value : i + '', label : i+'개월'});
                    }

                    //결제수단
                    for(let i = 0 ; i < result.data.paywaylist.length ; i++) {
                        if(result.data.paywaylist[i].cmcode != "PWT002") {
                            this.paywaylist.push(result.data.paywaylist[i]);
                        }
                    }

                    //카드종류
                    this.cardlist = result.data.cardlist;

                    this.item = result.data.item;
                    
                    //클레임목록
                    let list = this.claimList;
                    
                    for(let i = 0 ; i < list.length ; i++) {
                        list[i].ordcnt = list[i].clmcnt;
                        
                        list[i] = this.setClaimItem(list[i]);

                        //판매자
                        let idx = this.$front.containIdx(this.dealers, "dealerno", list[i].dealerno);
                        if(idx == -1) {
                            var dealer = {
                                dealerno : list[i].dealerno,
                                dealernm : list[i].dealernm,
                                delivamt : 0
                            }
                            this.$set(dealer, 'items', []);
                            this.dealers.push(dealer);
                        }
                    }
                    
                    //입점사와 입점사상품 세팅
                    for(let i = 0 ; i < this.dealers.length ; i++) {
                        for(let j = 0 ; j < list.length; j++) {
                            if(this.dealers[i].dealerno == list[j].dealerno) {
                                if(list[j].excitem != null) {
                                    this.excOption = list[j].excitem.opthtml;
                                }
                                this.dealers[i].items.push(list[j]);
                            }
                        }
                    }
                } else {
                    this.$router.replace({name:'nonemember-claim'});
                }
            });
        },
        paymentProc() {
            const param = {
                clmno : this.claimInfo.clmno,
                clmtype : this.claimType,
                amount : this.claimInfo.addrpaytotprice,
                ordname : this.orderInfo.ordname,
                goodsname : "[추가결제]" + this.claimList[0].goodsname,
            }

            this.payment(param);
        },
        //결제수단수정
        changePayWayType() {
            this.payInfo.cardCompany = '';
        },
        openModal(files, index) {
            let param = {
                files: files,
                filetype: files[index].filetype
            }
            this.$eventBus.$emit('showNoBvModal', ImageModal, param);
        },
    }
}