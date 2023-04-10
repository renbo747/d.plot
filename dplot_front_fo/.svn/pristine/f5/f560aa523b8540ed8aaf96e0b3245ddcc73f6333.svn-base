export default {
    methods: {
        payment(param) {
            if(this.payInfo.paywaytype == 'PWT005') {
                if(param.amount < 100) {
                    this.$eventBus.$emit('alert','알림','최소 100원이상 결제가 가능합니다.');
                    return;
                }
                let productItems = [];
                productItems.push({
                    "categoryType": "PRODUCT",
                    "categoryId" : "GENERAL",
                    "uid" : param.clmno,
                    "name" : param.goodsname,
                    "payReferrer" : "ETC",
                    "count" : 1
                });
                const naverParam = {
                    "merchantUserKey": param.clmno,
                    "merchantPayKey": this.userno,
                    "productName": param.goodsname,
                    "totalPayAmount": param.amount,
                    "taxScopeAmount": param.amount,
                    "taxExScopeAmount": 0,
                    "productCount": 1,
                    "returnUrl": window.location.origin + "/claim-naverpaymentSucc?orderId=" + param.clmno + "&amount=" + param.amount,
                    "productItems": productItems
                };

                if(window.sessionStorage.getItem('platform') != 'MAC001') {
                    setTimeout(() => {
                        window.popupCallback = this.goDetail;
                        const popupUrl = '/common/naverPayment?payinfo=' + encodeURIComponent(JSON.stringify(naverParam));
                        window.open(popupUrl, "_blank", "width=425, height=550, resizable=0, scrollbars=no, status=0, titlebar=0, toolbar=0, left=435, top=250");
                        this.goDetail(param);
                    });
                } else {
                    if(!Naver.isInitialized) {
                        Naver.Pay.create({
                            "mode" : process.env.VUE_APP_NAVER_PAY_MODE,
                            "clientId" : process.env.VUE_APP_NAVER_PAY_CLIENT_KEY,
                        })
                    }
                    Naver.Pay.open(naverParam);
                }
            } else {
                let payType = '카드';
                        
                let tossInfo = {
                    amount : param.amount,
                    orderId : param.clmno,
                    orderName : param.goodsname,
                    customerName : param.ordname,
                    successUrl: window.location.origin + "/claim-tossPaymentSucc",
                    failUrl: window.location.origin + "/claim-tossPaymentFail",
                }
                
                switch (this.payInfo.paywaytype) {
                    case 'PWT001':
                        payType = '카드';
                        if(this.payInfo.cardCompany != '') {
                            tossInfo.cardCompany = this.payInfo.cardCompany;
                            tossInfo.cardInstallmentPlan = this.payInfo.cardInstallmentPlan;
                            tossInfo.flowMode = 'DIRECT';
                        }
                        
                        break;
                    case 'PWT002':
                        payType = '가상계좌';
                        tossInfo.validHours = 72; //가상계좌 유효시간
                        if(process.env.NODE_ENV != 'local') {
                            tossInfo.virtualAccountCallbackUrl = window.location.origin + "/api/common/toss/virtualAccount";
                        } else {
                            tossInfo.virtualAccountCallbackUrl = "http://local.mobilefactory.co.kr/api/common/toss/virtualAccount";
                        }
                        break;
                    case 'PWT003':
                        payType = '계좌이체';
                        break;
                    case 'PWT004':
                        payType = '휴대폰';
                        break;
                    case 'PWT006':
                        payType = '토스결제';
                        break;
                    case 'PWT007':
                        payType = "카드";
                        tossInfo.flowMode = 'DIRECT';
                        tossInfo.easyPay = "페이코";
                        break;
                    case 'PWT008':
                        payType = "카드";
                        tossInfo.flowMode = 'DIRECT';
                        tossInfo.easyPay = "카카오페이";
                        break;
                    default:
                        payType = '카드';
                        break;
                }
                this.$util.debug("Toss결제 파라미터 : " + payType + " - " + JSON.stringify(tossInfo));
                
                if(window.sessionStorage.getItem('platform') != 'MAC001') {
                    setTimeout(() => {
                        window.popupCallback = this.goDetail;
                        const popupUrl = '/common/tossPayment?payType=' + payType + '&tossInfo=' + JSON.stringify(tossInfo);
                        window.open(popupUrl, "TossWindow", "width=425, height=550, resizable=0, scrollbars=no, status=0, titlebar=0, toolbar=0, left=435, top=250");
                        this.goDetail(param);
                    });
                } else {
                    const tossPayments = TossPayments(process.env.VUE_APP_TOSS_CLIENT_KEY);
                    tossPayments.requestPayment(payType, tossInfo).catch(error => {
                        const msg = error + '';
                        if(msg.indexOf('중복된 주문번호') > -1) {
                            tossInfo.orderId = tossInfo.orderId + "_" + Date.now();
                            tossPayments.requestPayment(payType, tossInfo).catch(error => {
                                this.$eventBus.$emit('alert', '알림', '추가결제 신청중 에러가 발생하였습니다. 다시시도해주세요.');
                                if(this.$route.name.indexOf('apply') > -1) {
                                    this.goDetail(param);
                                } 
                            });
                        } else {
                            this.$eventBus.$emit('alert', '알림', error);
                            if(this.$route.name.indexOf('apply') > -1) {
                                this.goDetail(param);
                            } 
                        }
                    });
                }
            }
        },
        goDetail(param) {
            let page = 'mypage';
            if(this.$route.name.indexOf('nonemember') > -1) {
                page = 'nonemember';
            }
            if(param.clmtype == 'CLM001') {
                this.$router.replace({name: page + '-claim-cancel-detail', params : {clmno : param.clmno}});
            } else if(param.clmtype == 'CLM002') {
                this.$router.replace({name: page + '-claim-return-detail', params : {clmno : param.clmno}});
            } else {
                this.$router.replace({name: page + '-claim-exchange-detail', params : {clmno : param.clmno}});
            }
        },
    }
}