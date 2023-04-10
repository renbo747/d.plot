/**
 * 주문 목록 공통
 */
import Common from "@views.mobile/nonemember/shop/Common.js";

export default {
    mixins:[Common],
    data() {
        return {
            orderList: [],
        }
    },
    mounted() {
    },
    methods: {
        beforeOrderList(list){
            for(let i = 0 ; i < list.length; i++) {
                list[i].statusnm = list[i].ordstatusnm
                //옵션명 설정
                let optionnames = list[i].optionname.split('|');
                list[i].opthtml = '';
                if(!this.$util.isBlank(list[i].optionname) && optionnames.length > 0) {
                    optionnames[0] = '옵션 : ' + optionnames[0];
                    for(let i = 0 ; i < optionnames.length ; i++) {
                        optionnames[i] = '<span>' + optionnames[i] + '</span>';
                    } 
                    list[i].opthtml = optionnames.join('<span class="dp-bar h10"></span>');  
                }
                list[i].buttonData = [];
                
                //Common.js호출(주문상태별 버튼)
                this.setButton(list[i].ordstatus, list[i]);
    
                let idx = this.$front.containIdx(this.orderList, 'ordno', list[i].ordno);
                if(idx == -1) {
                    this.orderList.push({
                        ordno : list[i].ordno,
                        orderdate : list[i].orderdate,
                        items : []
                    })
                }
            }
    
            for(let i = 0 ; i < this.orderList.length ; i++) {
                for(let j = 0 ; j < list.length ; j++) {
                    if(this.orderList[i].ordno == list[j].ordno) {
                        this.orderList[i].items.push(list[j]);
                    }
                }
            }
        },
        goToDetail(item) {
            this.$router.push({name:'nonemember-order-detail', params : {ordno : item.ordno}});
        },
    },
}