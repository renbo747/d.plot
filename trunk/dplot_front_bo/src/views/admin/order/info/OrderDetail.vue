<template>
    <!-- 주문상세정보 -->
    <div id="modal-wrap" class="modal" style="display: block;">
        <div class="modal-content" style="width: 1600px;">
            <div class="pop-header">
                <h2>주문상세정보</h2>
                <button type="button" class="pop-close" @click="onClose"></button>
            </div>
            <div class="pop-body">
                <div class="gray-box mg0">
                    <div class="clearfix">
                        <div class="fl">[{{ info.ordpathtypename }}] 주문번호 : <strong>{{ info.ordno }}</strong></div>
                        <div class="fr txt-gray">
                            <span>주문일 : {{ info.orderdate }}</span>
                            <span v-if="isPartner" class="left-bar">결제일 : {{ info.paymentdate }}</span>
                        </div>
                    </div>
                    <hr class="solid" style="margin: 10px 0;" v-if="!isPartner"/>
                    <div class="clearfix" v-if="!isPartner">
                        <div class="fl">
                            <span v-if="info.isnonmember=='F'">[{{ info.membertypename }}] [{{ info.memlvtypename }}]
                                <a href="javascript:void(0);" class="link" @click="goMemDetail(info.orduserno)">{{ info.ordname }}</a>({{ info.ordid }})
                            </span>
                            <span v-else>{{ info.ordname }}</span>
                            <span class="left-bar" v-if="!$util.isNull(info.ordtel)">{{ $util.maskTel(info.ordtel) }}</span>
                            <span class="left-bar" v-if="!$util.isNull(info.ordemail)">{{ info.ordemail }}</span>
                        </div>
                        <div class="fr txt-gray">
                            <span>결제일 : {{ info.paymentdate }}</span>
                        </div>
                    </div>
                </div>
                <div class="clearfix mt10" style="height: 30px;">
                    <!-- <div class="btn-group fr">
                        <button type="button" class="btn blue">최초주문정보</button>
                    </div> -->
                </div>
                <div class="tab-group clearfix">
                    <ul><!-- 활성화탭 li에 class="active" 추가 -->
                        <li :class="{ active: item.isActive }" v-for="item in tabs" :key="item.tab" @click="toggleTab(item)"><a href="javascript:void(0);">{{ item.name }} {{ $util.isNull(item.cntName)? '': '('+count[item.cntName]+')'}}</a></li>
                    </ul>
                </div>
                <AdminOrderInfo v-if="tabArea === 'INFO'" :activeOrderCode="activeOrderCode" :orderInfo="info" :isRead="isRead" :isWrite="isWrite" @setIsreload="setIsreload" @setOrderInfo="setOrderInfo"></AdminOrderInfo>
                <AdminOrderCancel v-if="tabArea === 'CANCEL'" :activeOrderIdx="activeOrderIdx" :orderInfo="info" :isRead="isRead" :isWrite="isWrite"></AdminOrderCancel>
                <AdminOrderReturn v-if="tabArea === 'RETURN'" :activeOrderIdx="activeOrderIdx" :orderInfo="info" :isRead="isRead" :isWrite="isWrite"></AdminOrderReturn>
                <AdminOrderExchange v-if="tabArea === 'EXCHANGE'" :activeOrderIdx="activeOrderIdx" :orderInfo="info" :isRead="isRead" :isWrite="isWrite"></AdminOrderExchange>
                <AdminOrderCs v-if="tabArea === 'CS'" :activeOrderIdx="activeOrderIdx" :isRead="isRead" :isWrite="isWrite"></AdminOrderCs>
                <AdminMemberInfo v-if="isShowMemDetail" :activeUserNo="activeUserNo" @closeDetail="closeMemDetail" :isRead="isRead" :isWrite="isWrite"></AdminMemberInfo>
                <div class="btn-group">
                    <button type="button" class="btn big darkgray" @click="onClose">닫기</button>
                </div>
            </div>
        </div>
    </div>
    <!-- /주문상세정보 -->
</template>

<script>
import AdminOrderInfo from "@views.admin/order/info/AdminOrderInfo.vue";
import AdminOrderCancel from "@views.admin/order/info/AdminOrderCancel.vue";
import AdminOrderReturn from "@views.admin/order/info/AdminOrderReturn.vue";
import AdminOrderExchange from "@views.admin/order/info/AdminOrderExchange.vue";
import AdminOrderCs from "@views.admin/order/info/AdminOrderCs.vue";

export default {
    name: "admin.order.info.orderdetail",
    props: ['activeOrderCode'],
    components:{
        AdminOrderInfo, AdminOrderCancel, AdminOrderReturn, AdminOrderExchange, AdminOrderCs,
        AdminMemberInfo : () => import('@views.admin/member/info/AdminMemberInfo.vue')
    },
    data() {
        return {
            isPartner: false,
            user: {},
            tabs: [
                {name: '주문정보', tab: 'INFO', cntName:'', isActive: true},
                {name: '취소', tab: 'CANCEL', cntName:'cancelcnt', isActive: false},
                {name: '반품', tab: 'RETURN', cntName:'returncnt', isActive: false},
                {name: '교환', tab: 'EXCHANGE', cntName:'exchangecnt', isActive: false},
                {name: 'CS처리', tab: 'CS', cntName:'cscnt', isActive: false}
            ],
            count: {
                cancelcnt: 0,
                returncnt: 0,
                exchangecnt: 0,
                cscnt: 0
            },
            info: {
                ordno: '',
                ordpathtypename: '',
                orderdate: '',
                paymentdate: '',
                isnonmembername: '',
                orduserno: '',
                ordname: '',
                ordid: '',
                ordtel: '',
                ordemail: '',
                membertypename: '',
                memlvtypename: ''
            },
            activeOrderIdx: '',
            activeUserNo: '',
            isShowMemDetail: false,     // 회원상세 팝업 노출여부
            tabArea: 'INFO',
            isRead : false,
            isWrite : false,
            isreload: false
        }
    },
    mounted() {
        this.isPartner = this.$util.isAuthorized(this.$store.getters['CONSTANTS'].PARTNER_USER);
        this.user = this.$util.getUser(this.$store.getters['CONSTANTS'].MANAGER_SESSION);
        let params = { url: this.$options.name, isloading: false };
        this.$http.post('/admin/common/pageAuth/check', params)
            .then(result => {
                this.isRead = (result.data.isread === 'T');
                this.isWrite = (result.data.iswrite === 'T');
            
                if(this.isRead) {
                    // 주문상세 정보 세팅
                    this.setOrderInfo();
                } else {
                    alert('페이지 접근 권한이 없습니다.');
                    this.onClose();
                }
            }).catch(error => {
                this.$util.debug(error);
            });
    },
    methods: {
        // 주문상세 정보 세팅
        setOrderInfo: function() {
            let params = { 
                ordno : this.activeOrderCode, 
                isloading : false,
                dealerno: this.isPartner? this.user.no : ''
            }
            this.$http.post('/admin/order/manage/info', params)
                .then(result => {
                    this.$util.debug(result);
                    let data = result.data;
                    this.info = data.info;
                    this.count = data.count;
                    this.activeOrderIdx = data.info.orderidx;
                }).catch(error => {
                    this.$util.debug(error);
                })
        },
        // 탭 Toggle
        toggleTab(obj){
            this.tabArea = obj.tab;
            this.tabs.forEach(item => {
                item.isActive = obj.name === item.name;
            });
        },
        // 회원상세 팝업 오픈
        goMemDetail: function(value) {
            this.activeUserNo = value;
            setTimeout(function () {
                this.isShowMemDetail = true;
            }.bind(this), 200);
        },
        // 회원상세 팝업 닫기
        closeMemDetail: function() {
            this.isShowMemDetail = false;
        },
        setIsreload: function(isreload) {
            this.isreload = isreload;
        },
        // 팝업 닫기
        onClose() {
            this.$emit('closeDetail', this.isreload);
        }
    }
}
</script>
