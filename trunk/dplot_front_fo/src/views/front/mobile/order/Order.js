import OrderSuccess from '@views.mobile/order/popup/OrderSuccess.vue'

export default {
    methods : {
        orderProc(orderInfo) {
            window.sessionStorage.removeItem('ORDERPAYLIST');
            this.$http.post('/order/save', orderInfo).then(result => {
                if (result.statusCode == 200) {
                    if(result.data.message) {
                        this.$eventBus.$emit('alert', '알림', result.data.message);
                    }
                    
                    //레코픽로그
                    for(let i = 0 ; i < orderInfo.orderlist.length ; i++){
                        const order = {
                            id : orderInfo.orderlist[i].goodscode + '', 
                            count : orderInfo.orderlist[i].ordcnt,
                            total_sales : orderInfo.orderlist[i].realgoodsamt,
                            order_no : orderInfo.ordno
                        }
                        recoPick('sendLog','order', order);
                    }

                    //카트삭제 및 조회
                    if(this.$store.state.isLogin) {
                        this.$front.getCartList(this,()=>{
                            this.afterProc(orderInfo);
                        });
                    } else {
                        for(let i = 0 ; i < orderInfo.orderlist.length ; i++) {
                            orderInfo.orderlist[i].istrash = 'T'
                        }
                        this.$front.saveCart(this, orderInfo.orderlist,()=>{
                            this.afterProc(orderInfo);
                        });
                    }
                } else {
                    this.$eventBus.$emit('alert','알림', result.message, ()=>{
                        if(this.$store.state.platform != 'MAC001'){
                            window.close();
                        } else {
                            const order = this.$storage.getSessionStorage('orderinfo');
                            this.$storage.removeSessionStorage('orderinfo');
                            sessionStorage.setItem('refresh', 'true');
                            this.$router.replace({name:'order', query:order});
                        }
                    });
                }
            });
        },
        afterProc(orderInfo) {
            this.$storage.removeSessionStorage('orderinfo');
            
            if(this.$store.state.isLogin) {
                this.goOrderDetail(orderInfo.ordno);
            } else {
                const param = {
                    ordname : orderInfo.ordname,
                    ordno : orderInfo.ordno,
                    mode : 'guest'
                }
                this.$http.post('/member/login_act', param).then(result => {
                    if (result.statusCode == 200) {
                        this.goOrderDetail(orderInfo.ordno);
                    } else {
                        this.$eventBus.$emit('alert', '알림', result.message);
                        this.$router.replace('shop');
                    }
                });
            }
        },
        goOrderDetail(ordno) {
            if(this.$store.state.platform == 'MAC001') {
                this.$router.replace({name : 'order-order-success', params : {ordno : ordno}});
            } else {
                this.$eventBus.$emit('showModal', 
                OrderSuccess, 
                'OrderSuccessModal', 
                {ordno : ordno},
                (param) => {
                    this.$router.replace(param);
                });
            }
        }
    }
}