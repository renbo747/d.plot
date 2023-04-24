import Common from "@views.mobile/mypage/shop/Common.js";
import TossPayment from '@views.mobile/mypage/shop/claim/TossPayment.js'
import { swiper, swiperSlide } from "vue-awesome-swiper";
import ImageModal from './popup/ImageModal.vue';

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
            isemp : false,
            claimType : 'CLM001', // CLM001 : 취소 , CLM002 : 반품 , CLM003 : 교환
            claimName : '취소',
            orderInfo : {},
            claimInfo : {
                clmreqdate : '',
                //[D] 리뷰쪽 이미지와 동일하게 처리 부탁드립니다. 2022.11.23
                // files : [],
            },
            claimList : [],
            dealers : [],
            paywaylist : [],  // 결제수단
            cardlist : [],    // 카드종류
            cardPlan : [],    // 할부개월
            item:[],          // 클레임 첨부사진들
            payInfo : {       // 결제정보
                paywaytype : 'PWT001', // 결재수단
                cardCompany : '',
                cardInstallmentPlan : '0',
                useEscrow : false, // 에스크로사용여부
            },
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
            // 사은품 데이터
            giftList : []
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
            this.$http.post('/mypage/claim/detail', param).then(result => {
                if (result.statusCode == 200) {
                    this.userno = result.data.memberinfo.userno;
                    if(result.data.memberinfo.membertype != 'DMT001' && result.data.memberinfo.membertype != 'DMT002') {
                        this.isemp = true;
                    }
                    //주문상세
                    this.orderInfo = result.data.order;

                    //클레임상세
                    this.claimInfo = result.data.claim;
                    
                    this.claimInfo.clmreqdate = this.claimInfo.clmreqdate.replace(/-/gi, '').replace(/:/gi, '');
                    
                    //클레임상품
                    this.claimList = result.data.list;

                    this.item = result.data.item;

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
                    
                    //사은품
                    this.giftList = [];
                    list = result.data.giftlist;

                    for(let i = 0 ; i < list.length ; i++) {
                        //사은품구조 변경
                        const temp = this.$util.deepClone(list[i]);
                        
                        let idx = this.$front.containIdx(this.giftList, "giftpromoidx", temp.giftpromoidx);
                                
                        if(idx == -1) {
                            temp.detail = [];
                            temp.detail.push(this.$util.deepClone(list[i]));
                            this.giftList.push(temp);
                        } else {
                            this.giftList[idx].detail.push(temp);
                        }
                    }
                } else {
                    this.$router.replace({name:'mypage-claim'});
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
        /*****************************
         * 이미지 모달 열기
         ****************************/
        openModal(files, index) {
            let param = {
                files: files,
                filetype: files[index].filetype
            }
            this.$eventBus.$emit('showNoBvModal', ImageModal, param);
        },
    }
}