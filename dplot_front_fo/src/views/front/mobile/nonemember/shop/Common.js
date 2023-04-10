/**
 * 비회원 주문/클레임 공통
 */
import ConfirmBuy from "@views.common/popup/ConfirmBuy";
import Tracking from "@views.common/popup/Tracking";
import Transaction from '@views.common/popup/Transaction.vue'

export default {
    methods: {
        //클레임상품 화면구조변경
        setClaimItem(item) {
            item.ordcnt = item.clmcnt;
            item.statusnm = item.clmstatusnm;
            //옵션명 설정
            let optionnames = item.optionname.split('|');
            
            item.opthtml = '';
            if(!this.$util.isBlank(item.optionname) && optionnames.length > 0) {
                optionnames[0] = '옵션 : ' + optionnames[0];
                for(let i = 0 ; i < optionnames.length ; i++) {
                    optionnames[i] = '<span>' + optionnames[i] + '</span>';
                } 
                item.opthtml = optionnames.join('<span class="dp-bar h10"></span>');  
            }
            
            item.excitem = null;
            if(!this.$util.isBlank(item.bfoptionname)){
                let excitem = this.$util.deepClone(item);
                let orgitem = this.$util.deepClone(item);
                let optionnames = item.bfoptionname.split('|');
                excitem.opthtml = '';
                if(optionnames.length > 0) {
                    optionnames[0] = '옵션 : ' + optionnames[0];
                    for(let i = 0 ; i < optionnames.length ; i++) {
                        optionnames[i] = '<span>' + optionnames[i] + '</span>';
                    } 
                    excitem.opthtml = optionnames.join('<span class="dp-bar h10"></span>');  
                }
                item = orgitem;
                item.excitem = excitem;
            }

            //클레임상태별
            this.setButton(item.clmstatus, item);
            return item;
        },
        setButton(status, item) {
            item.buttonData = [];
            //주문상태별 버튼
            switch (status) {
            //case 'ODS001':
            case 'ODS004':
                item.buttonData.push({text : '주문취소'});
                break;
            case 'ODS007':
                if(item.delivtype == 'DLT002') {
                    item.buttonData.push({text : '수취확인'});
                }
                item.buttonData.push({text : '배송조회'});
                break;
            case 'ODS008':
                if(item.origincnt - item.cncclmcnt - item.excclmcnt > 0) {
                    item.buttonData.push({text : '교환신청'});
                    item.buttonData.push({text : '반품신청'});
                }
                item.buttonData.push({text : '구매확정'});
                item.buttonData.push({text : '배송조회'});
                break;
            case 'ODS009':
                item.buttonData.push({text : 'AS전용 문의'});  
                // 비회원은 리뷰작성이 불가능하므로 숨김처리
                // if(item.isreview == 'F') {
                //     item.buttonData.push({text : '리뷰작성'});
                // }
                item.buttonData.push({text : '배송조회'});
                break;
            //클레임상태별 버튼
            case 'CNS001':
                item.buttonData.push({text : '취소철회'});
                break;
            case 'RTS001':
            case 'RTS002':
            case 'RTS003':
                item.buttonData.push({text : '반품철회'});
                break;
            case 'RTS007':
            case 'RTS008':
            case 'RTS010':
                item.buttonData.push({text : '반품회수조회'});
                break;
            case 'EXS001':
            case 'EXS002':
                item.buttonData.push({text : '교환철회'});
                break;
            case 'EXS007':
            case 'EXS008':
            case 'EXS009':
            case 'EXS010':
            case 'EXS012':
                item.buttonData.push({text : '교환회수조회'});
                if(['EXS009','EXS010','EXS012'].indexOf(item.clmstatus) > -1){
                    if(item.excitem != null) {
                        item.excitem.buttonData = [];
                        if(item.clmstatus == 'EXS012' && item.bfclmcnt == 0 && item.ordstatus == 'ODS008') {
                            item.excitem.buttonData.push({text : '교환신청'});
                            item.excitem.buttonData.push({text : '반품신청'});
                        }
                        item.excitem.buttonData.push({text : '교환배송조회'});
                    }
                }
                break;
            default:
                break;
            }
        },
        handleFooterButton(item, val) {
            switch (val) {
                case '배송조회':
                case '반품회수조회':
                case '교환회수조회':
                case '교환배송조회':
                    if(val == '배송조회') {
                        item.track = '1'
                    } else if(val == '교환배송조회') {
                        item.track = '2'
                    } else {
                        item.track = '3'
                    }
                    this.$eventBus.$emit('showModal', Tracking, 'trackingModal', {item});
                    break;
                case 'AS전용 문의' : 
                    this.$router.push({name : 'nonemember-apply-as', params:{pid:item.ordgdidx}});
                    break;
                case '주문취소' :
                    item.clmcnt = 1;
                    this.$router.push({name:'nonemember-claim-cancel-apply', query : {items :  this.$front.makeClaimStr([item])}});
                    break;
                case '수취확인' :
                    this.$eventBus.$emit('confirm', '수취확인', '수취확인 처리하시겠습니까?', ()=>{
                        this.$http.post('/nonemember/order/delivcomplete', item).then(result => {
                            if (result.statusCode == 200) {
                                this.$toast.clear();
                                this.$toast.open("수취확인 처리되었습니다.");
                                item.ordstatus = 'ODS008';
                                item.ordstatusnm = '배송완료';
                                item.statusnm = '배송완료';
                                this.setButton(item.ordstatus, item);
                            }
                        });
                    });
                    break;
                case '반품신청' :
                    item.clmcnt = 1;
                    this.$router.push({name:'nonemember-claim-return-apply', query : {items :  this.$front.makeClaimStr([item])}});
                    break;
                case '교환신청' :
                    item.clmcnt = 1;
                    this.$router.push({name:'nonemember-claim-exchange-apply', query : {items :  this.$front.makeClaimStr([item])}});
                    break;
                case '구매확정':
                    if(item.clmingcnt > 0) {
                        this.$eventBus.$emit('alert', '구매확정', '취소/교환/반품신청 완료 후 구매확정이 가능합니다.');
                        break;
                    }
                    var param = this.$util.deepClone(item);
                    param.ordcnt = param.ordcnt - param.clmcnt;
                    this.$eventBus.$emit('showModal', ConfirmBuy, 'confirmBuyModal', {param}, ()=>{
                        if(this.$route.name.indexOf('detail') > -1){
                            this.getOrderDetail();
                        } else {
                            this.changeSearch();
                        }
                    });
                    break;
                case '취소철회' :
                case '반품철회' :
                case '교환철회' :
                    var msg = val.replace('철회', '신청');
                    this.$eventBus.$emit('confirm', val, msg +'을 철회하시겠습니까?',()=>{
                        var param = item;
                        param.dropreason = '고객 직접철회';
                        if(item.clmtype == 'CLM001') {
                            param.cncstatus = 'CNS004';
                        } else if(item.clmtype == 'CLM002') {
                            param.rtnstatus = 'RTS005';
                        } else {
                            param.excstatus = 'EXS005';
                        }
                        this.$http.post('/nonemember/claim/cancel', param).then(result => {
                            if (result.statusCode == 200) {
                                this.$toast.clear();
                                this.$toast.open(msg + "이 철회되었습니다.");
                                this.$router.replace({name:'nonemember-claim'});
                            }
                        });
                    });
                    break;
                default:
                    break;
            }
        },
        openTransaction(){
            const param = {
                orderInfo : this.orderInfo,
                payInfo : this.payInfo,
                items : this.orderList
            }
            this.$eventBus.$emit('showModal', Transaction, 'transactionDetailModal', param);
        }
    },
    
}