import Address from '@views.mobile/nonemember/shop/popup/Address.vue'
import Delivery from '@views.mobile/order/popup/Delivery.vue'

import Common from "@views.mobile/nonemember/shop/Common.js";

export default {
    mixins:[Common],
    beforeCreate() {
        this.$store.commit("onSubHeaderOption", {
            title: "주문상세",
            searchIcon: true,
            cartIcon: true,
            homeIcon: false,
        });
    },
    data() {
        return {
            orderInfo : {
                orderdate:'',
                receipturl : ''
            },
            orderList : [],
            claimList : [],
            dealers : [],
            payInfo : {},
            possbleAddrChange : true,
            ordstatus : ''
        };
    },
    mounted() {
        this.getOrderDetail();
    },
    methods: {
        getOrderDetail() {
            const param = {
                ordno : this.$route.params.ordno
            }
            this.$http.post('/nonemember/order/detail', param).then(result => {
                if (result.statusCode == 200) {
                    //주문정보
                    this.orderInfo = result.data.order;
                    //클레임목록
                    this.claimList = result.data.claim;
                    //주문결제정보
                    this.payInfo = result.data.payinfo;
                    //주문목록
                    this.orderList = result.data.ordergoods;

                    //주문상품목록
                    let list = this.orderList;
                    //클레임상품목록
                    let claimgoods = result.data.claimgoods;
                    
                    //주문상태
                    this.ordstatus = list[0].ordstatus;
                    
                    if(this.claimList.length > 0) {
                        this.payInfo.reservetotamt = this.claimList[this.claimList.length-1].afreservetotamt;
                        this.payInfo.epointtotamt = this.claimList[this.claimList.length-1].afepointtotamt;
                        this.payInfo.empreservetotamt = this.claimList[this.claimList.length-1].afempreservetotamt;
                        this.payInfo.rpaytotprice = this.claimList[this.claimList.length-1].afrpaytotprice;
                    } else {
                        this.payInfo.reservetotamt = this.orderInfo.reservetotamt;
                        this.payInfo.epointtotamt = this.orderInfo.epointtotamt;
                        this.payInfo.empreservetotamt = this.orderInfo.empreservetotamt;
                        this.payInfo.rpaytotprice = this.orderInfo.rpaytotprice;
                    }

                    this.dealers = [];
                    for(let i = 0 ; i < list.length ; i++) {
                        if(['ODS002','ODS006','ODS007','ODS008','ODS009'].indexOf(list[i].ordstatus) > -1) {
                            this.possbleAddrChange = false;
                        }
                        list[i].adddelivamt = 0;
                        list[i].statusnm = list[i].ordstatusnm;
                        //옵션명 설정
                        let optionnames = list[i].optionname.split('|');
                        list[i].opthtml = '';
                        if(!this.$util.isBlank(list[i].optionname) && optionnames.length > 0) {
                          optionnames[0] = '옵션 : ' + optionnames[0];
                          for(let i = 1 ; i < optionnames.length ; i++) {
                            optionnames[i] = '<span>' + optionnames[i] + '</span>';
                          } 
                          list[i].opthtml = optionnames.join('<span class="dp-bar h10"></span>');  
                        }
                        
                        
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
                                list[j].claim = [];
                                for(let y = 0 ; y < claimgoods.length ; y++) {
                                    if(list[j].ordgdidx == claimgoods[y].ordgdidx) {
                                        //Common.js 호출(클레임상품 화면구조변경)
                                        claimgoods[y] = this.setClaimItem(claimgoods[y]);
                                        if(claimgoods[y].clmtype == 'CLM003') {
                                            list[j].claim.push(claimgoods[y].excitem);
                                        } else {
                                            list[j].claim.push(claimgoods[y]);
                                        }
                                    }
                                }
                                list[j].ordcnt = list[j].ordcnt - list[j].cncclmcnt;

                                list[j].buttonData = [];
                                this.setButton(list[j].ordstatus, list[j]);
                                // if(list[i].ordcnt - list[i].excclmcnt > 0){
                                //     this.setButton(list[i].ordstatus, list[i]);
                                // }
                                this.dealers[i].items.push(list[j]);
                            }
                        }
                    }
                } else {
                    this.$router.replace({name:'nonemember-order'});
                }
            });
        },
        goClaimDetail(claim) {
            if(claim.clmtype == 'CLM001') {
                this.$router.push({name:'nonemember-claim-cancel-detail', params:{clmno:claim.clmno}});
            } else if(claim.clmtype == 'CLM002') {
                this.$router.push({name:'nonemember-claim-return-detail', params:{clmno:claim.clmno}});
            } else {
                this.$router.push({name:'nonemember-claim-exchange-detail', params:{clmno:claim.clmno}});
            }
        },
        //배송비 상세
        delivDetail(){
            this.$eventBus.$emit('showModal', Delivery, 'deliveryModal', {dealers : this.dealers, totAmt : this.orderInfo, isolatetype : this.orderInfo.isolatetype});
        },
        //배송지변경 팝업
        changeAddr(){
            const param = {
                title : this.orderInfo.rcvname,
                mobile : this.orderInfo.rcvtel1,
                addr : this.orderInfo.rcvaddr,
                addrdetail : this.orderInfo.rcvaddrdetail,
                addrroad : this.orderInfo.rcvaddrroad,
                addrdetailroad : this.orderInfo.rcvaddrdetailroad,
                post : this.orderInfo.rcvpost,
                type : 'change'
            }
            this.$eventBus.$emit('showModal', Address, 'addressModal', param, (address)=>{
                this.selectAddr(address);
            });
        },
        //배송지선택
        selectAddr(addr) {
            if(this.$util.isEmpty(addr)) {
                return;
            }

            if(this.orderInfo.isolatetype != addr.isolatetype) {
                if(addr.isolatetype == 'J' || addr.isolatetype == 'I') {
                    this.$eventBus.$emit('alert', '알림', '도서/산간지역으로 배송지 변경이 불가능합니다.');
                } else {
                    this.$eventBus.$emit('alert', '알림', '일반지역으로 배송지 변경이 불가능합니다.');
                }
                return;
            }
            this.$eventBus.$emit('confirm','배송지변경','배송지를 변경하시겠습니까?', ()=>{
                this.orderInfo.rcvname = addr.title;
                this.orderInfo.rcvtel1 = addr.mobile;
                this.orderInfo.rcvaddr = addr.addr;
                this.orderInfo.rcvaddrdetail = addr.addrdetail;
                this.orderInfo.rcvaddrroad = addr.addrroad;
                this.orderInfo.rcvaddrdetailroad = addr.addrdetailroad;
                this.orderInfo.rcvpost = addr.post;
                this.orderInfo.isolatetype = addr.isolatetype;
                this.orderInfo.rcvsigungucode = addr.sigungucode;
                this.orderInfo.rcvbuildingcode = addr.buildingcode;
                this.orderInfo.rcvroadnamecode = addr.rcvroadnamecode;
                
                this.$http.post('/nonemember/order/rcvsave', this.orderInfo).then(result => {
                    if (result.statusCode == 200) {
                        this.$toast.clear();
                        this.$toast.open('배송지가 변경되었습니다.');
                    }
                });
            });
        },
        //전체취소 
        allCancel() {
            let orderList = [];
            for(let i = 0 ; i < this.orderList.length ; i++) {
                let temp = this.$util.deepClone(this.orderList[i]);
                temp.clmcnt = temp.origincnt - temp.cncclmcnt;
                if(temp.clmcnt > 0) {
                    orderList.push(temp);
                }
            }
            this.$router.push({name:'nonemember-claim-cancel-apply', query : {items :  this.$front.makeClaimStr(orderList)}});
        },
        goReceipt() {
            var win = window.open(this.payInfo.receipturl, '_blank');
            win.focus();
        }
    },
    
};