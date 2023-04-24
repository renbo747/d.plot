<template>
    <!-- 환불내역 상세 팝업 -->
    <div id="modal-wrap" class="modal" style="display: block;">
        <div class="modal-content" style="width: 1000px;">
            <div class="pop-header">
                <h2>환불내역 상세</h2>
                <button type="button" class="pop-close" @click="closePopup"></button>
            </div>
            <div class="pop-body">
                <table cellpadding="0" cellspacing="0" class="gray-tb">
                    <colgroup>
                        <col width="130px">
                        <col width="190px">
                        <col width="130px">
                        <col width="190px">
                        <col width="130px">
                        <col width="">
                    </colgroup>
                    <tbody>
                        <tr>
                            <th>주문번호</th>
                            <td>{{ refundInfo.ordno }}</td>
                            <th>환불처리일</th>
                            <td colspan="3">{{ refundInfo.clmcompdate }}</td>
                        </tr>
                        <tr>
                            <th>주문자 명</th>
                            <td>{{ refundInfo.ordname }}</td>
                            <th>아이디</th>
                            <td>{{ refundInfo.ordid }}</td>
                            <th>연락처</th>
                            <td>{{ $util.maskTel(refundInfo.ordtel) }}</td>
                        </tr>
                    </tbody>
                </table>
                <div class="col3 pd scroll">
                    <div class="left">
                        <div class="bar-title small">환불내역</div>
                        <table cellpadding="0" cellspacing="0" class="gray-tb">
                            <colgroup>
                                <col width="130px">
                                <col width="">
                            </colgroup>
                            <tbody>
                                <tr>
                                    <th>적립금사용 반환</th>
                                    <td class="right">{{ $util.maskComma(refundInfo.rtnresamt) }}</td>
                                </tr>
                                <tr>
                                    <th>D포인트 반환</th>
                                    <td class="right">{{ $util.maskComma(refundInfo.rtnepoamt) }}</td>
                                </tr>
                                <tr>
                                    <th>임직원적립금 반환</th>
                                    <td class="right">{{ $util.maskComma(refundInfo.rtnempresamt) }}</td>
                                </tr>
                                <!-- <tr>
                                    <th>실 결제금액</th>
                                    <td class="right">{{ $util.maskComma(refundInfo.addrpaytotprice) }}</td>
                                </tr> -->
                            </tbody>
                        </table>
                    </div>
                    <div class="right">
                        <div class="bar-title small">환불처리</div>
                        <table cellpadding="0" cellspacing="0" class="gray-tb">
                            <colgroup>
                                <col width="130px">
                                <col width="">
                            </colgroup>
                            <tbody>
                                <tr>
                                    <th>환불금액</th>
                                    <td>{{ $util.maskComma(refundInfo.finadjustamt) }}</td>
                                </tr>
                                <tr>
                                    <th>환불수단</th>
                                    <td>{{ refundInfo.paywaytypename }}</td>
                                </tr>
                                <tr v-if="refundInfo.paywaytype===constants.PAY_VIRTURE_ACCOUNT">
                                    <th>은행명</th>
                                    <td>{{ refundInfo.refbank }}</td>
                                </tr>
                                <tr v-if="refundInfo.paywaytype===constants.PAY_VIRTURE_ACCOUNT">
                                    <th>계좌번호</th>
                                    <td>{{ refundInfo.refaccnumber }}</td>
                                </tr>
                                <tr v-if="refundInfo.paywaytype===constants.PAY_VIRTURE_ACCOUNT">
                                    <th>예금주</th>
                                    <td>{{ refundInfo.refcusname }}</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                <hr class="dash" />
                <div class="bar-title small">클레임 처리 이력</div>
                <div class="scroll-y" style="max-height: 500px;">
                    <table cellpadding="0" cellspacing="0" class="data-tb align-c">
                        <colgroup>
                            <col width="4%" /><!-- No -->
                            <col width="" /><!-- 상품명 -->
                            <col width="20%" /><!-- 처리일시 -->
                            <col width="20%" /><!-- 처리상태 -->
                            <col width="15%" /><!-- 처리구분 -->
                            <col width="15%" /><!-- 처리자 -->
                        </colgroup>
                        <thead>
                            <tr>
                                <th>No</th>
                                <th>상품명</th>
                                <th>처리일시</th>
                                <th>처리상태</th>
                                <th>처리구분</th>
                                <th>처리자</th>
                            </tr>
                        </thead>
                        <tbody v-if="clmprcHistList.length > 0">
                            <tr v-for="(item, index) in clmprcHistList" :key="item.clmstidx" >
                                <td>{{ clmprcHistList.length-index }}</td>
                                <td class="left" :rowspan="item.rowcnt" v-if="item.rownum===item.rowcnt">
                                    <p class="mg0">{{ item.goodsname }}</p>
                                    <span class="small-txt">{{ item.optionconts }}</span>
                                </td>
                                <td>{{ item.clmprcdate }}</td>
                                <td>{{ item.clmprcaftstatus }}</td>
                                <td>{{ item.clmprctypename }}</td>
                                <td>{{ item.clmprcmanager }}</td>
                            </tr>
                        </tbody>
                        <tbody v-else>
                            <tr><td colspan="6">조회 결과가 존재하지 않습니다.</td></tr>
                        </tbody>
                    </table>
                </div>
                <div class="btn-group">
                    <button type="button" class="btn big darkgray" @click="closePopup">닫기</button>
                </div>
            </div>
        </div>
    </div>
    <!-- /환불내역 상세 팝업 -->
</template>

<script>
import store from '@/js/store';

export default {
    name: 'ClaimRefundDetailPopup',
    props: {
        modalComp: Object,
        params: Object,
        callbackFn: Function
    },
    data() {
        return {
            constants: store.getters['ADMIN'],
            refundInfo: {},     // 환불정보
            clmprcHistList: []  // 클레임처리이력
        }
    },
    mounted() {
        this.getClaimRefundInfo();
    },
    methods: {
        // 환불상세내역 조회
        getClaimRefundInfo: function() {
            this.$http.post('/admin/order/claim/refund/info', { clmidx: this.params.clmidx })
                    .then(result => {
                        this.$util.debug(result);
                        this.refundInfo = result.data.refundinfo;
                        this.clmprcHistList = result.data.clmprchistlist;
                    })
                    .catch(error => {
                        this.$util.debug(error);
                    });
        },
        // 팝업닫기
        closePopup(params){
            if(typeof(this.callbackFn) === 'function') {
                this.callbackFn(params);
            }
            this.$modal.hide('commonModal');
        }
    }
}
</script>