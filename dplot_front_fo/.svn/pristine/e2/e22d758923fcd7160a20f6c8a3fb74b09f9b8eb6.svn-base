<template>
    <!-- 거래명세서 모달 -->
    <b-modal
        id="transactionDetailModal"
        modal-class="dp-modal page-layer transaction-detail-modal"
        centered
        hide-footer
        scrollable
    >
        <!-- 거래명세서 모달 헤더 -->
        <template #modal-header="{ cancel }">
        <h5 class="modal-title">거래명세서</h5>
        <i class="modal-close" @click="cancel()">
            <img
            src="@/assets/common/icon/icon-close-modal-30px.svg"
            alt="닫기"
            />
        </i>
        </template>

        <!-- 거래명세서 모달 바디 -->
        <div class="page-layer__body">
        <div class="dp-panel pt-0">
            <p class="transaction-detail__title">주문정보</p>
            <ul class="transaction-detail__ul list-style-none">
            <li>
                <div class="transaction-detail__text">
                <p>주문자명</p>
                <p>{{param.orderInfo.ordname}}</p>
                </div>
            </li>
            <li>
                <div class="transaction-detail__text">
                <p>주문번호</p>
                <p>{{param.orderInfo.ordno}}</p>
                </div>
            </li>
            <li>
                <div class="transaction-detail__text">
                <p>주문일시</p>
                <p>{{$util.getFormatDate(param.orderInfo.orderdate, '.')}}</p>
                </div>
            </li>
            <li>
                <div class="transaction-detail__text">
                <p>발급일시</p>
                <p>{{$util.getDate('.')}}</p>
                </div>
            </li>
            </ul>
        </div>
        <hr class="dp-hr justify-margin h6" />
        <div class="dp-panel">
            <p class="transaction-detail__title">판매/공급정보</p>
            <ul class="transaction-detail__ul list-style-none">
            <li>
                <div class="transaction-detail__text">
                <p>상호</p>
                <p>(주)다다엠앤씨</p>
                </div>
            </li>
            <li>
                <div class="transaction-detail__text">
                <p>사업자번호</p>
                <p>451-81-02355</p>
                </div>
            </li>
            <li>
                <div class="transaction-detail__text">
                <p>사업장주소</p>
                <p>
                    서울특별시 용산구 한강대로23길 55 (한강로3가) 아이파크몰
                    6층
                </p>
                </div>
            </li>
            <li>
                <div class="transaction-detail__text">
                <p>대표자</p>
                <p>서승원</p>
                </div>
            </li>
            </ul>
        </div>
        <hr class="dp-hr justify-margin h6" />
        <div class="dp-panel">
            <p class="transaction-detail__title">구매내역</p>
            <table class="buy-history__table">
            <colgroup>
                <col width="60%" />
                <col width="10%" />
                <col width="30%" />
            </colgroup>
            <thead>
                <tr>
                <th>상품정보</th>
                <th class="text-center">수량</th>
                <th class="text-right">결제금액</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="(item,index) in param.items" :key="index">
                <td>
                    {{item.goodsname}} <br />
                    <span class="text-gray-700" v-html="item.opthtml"></span>
                </td>
                <td
                    class="text-center atten-new text-black font-weight-500"
                >
                    {{item.ordcnt}}
                </td>
                <td class="text-right">
                    <span class="atten-new text-black font-weight-500"
                    >{{$util.maskComma(item.realgoodsamt)}}</span
                    ><span class="unit">원</span>
                </td>
                </tr>
                <tr>
                </tr>
            </tbody>
            </table>
        </div>
        <hr class="dp-hr justify-margin h6" />
        <div class="dp-panel">
            <p class="transaction-detail__title">결제내역</p>
            <ul class="list-style-none dp-price-info">
            <!-- <li>
                <span class="price-info__key">결제수단</span>
                <span class="price-info__unit" v-if="param.orderInfo.paywaytypename == 'PWT001'">신용카드({{param.payInfo.cardcompany}})</span>
                <span class="price-info__unit" v-else>{{param.orderInfo.paywaytypename}}</span>
            </li> -->
            <li>
                <span class="price-info__key">총주문금액</span>
                <span class="price-info__value"
                >{{$util.maskComma(totamt)}}<span class="price-info__unit">원</span></span
                >
            </li>
            <li>
                <span class="price-info__key">총할인금액</span>
                <span class="price-info__value">{{$util.maskComma(discountamt * -1)}}
                    <span class="price-info__unit">원</span>
                </span>
            </li>
            <li>
                <span class="price-info__key">결제금액</span>
                <span class="price-info__value">{{$util.maskComma(param.orderInfo.paytotprice)}}
                    <span class="price-info__unit">원</span>
                </span>
            </li>
            </ul>
            <ul class="detail-add">
                <li v-if="param.orderInfo.reservetotamt > 0">
                    <span class="price-info__key">적립금</span>
                    <span class="price-info__value">{{$util.maskComma(param.orderInfo.reservetotamt)}}
                        <span class="price-info__unit">원</span>
                    </span>
                </li>
                <li v-if="param.orderInfo.empreservetotamt > 0">
                    <span class="price-info__key">임직원적립금</span>
                    <span class="price-info__value">{{$util.maskComma(param.orderInfo.empreservetotamt)}}
                        <span class="price-info__unit">원</span>
                    </span>
                </li>
                <li v-if="param.orderInfo.rpaytotprice > 0">
                    <span class="price-info__key" v-if="param.orderInfo.paywaytype == 'PWT001'">신용카드({{param.orderInfo.cardcompany}}/{{param.orderInfo.planmonth != null && param.orderInfo.planmonth > 1 ? param.orderInfo.planmonth +'개월 할부' : '일시불'}})</span>
                    <span class="price-info__key" v-else>{{param.orderInfo.paywaytypename}}</span>
                    <span></span><span class="price-info__value">{{$util.maskComma(param.orderInfo.rpaytotprice)}}
                        <span class="price-info__unit">원</span>
                    </span>
                </li>
            </ul>
            <div class="total-price dp-mt-20">
            <p class="mb-0 total-price__text">총결제금액</p>
            <p class="mb-0">
                <span class="atten-new">{{$util.maskComma(param.orderInfo.paytotprice)}}</span
                ><span class="unit">원</span>
            </p>
            </div>
        </div>
        <div class="print-btn dp-mt-20" v-if="$store.state.platform == 'MAC001'">
            <b-button class="dp-btn" variant="outline-black" squared @click="print">
            <span>인쇄하기</span>
            </b-button>
        </div>
        </div>
    </b-modal>
    <!-- // 거래명세서 모달 -->
</template>

<script>
export default {
    props: {
        param: { type: Object },
    },
    data () {
        return {
            totamt : 0,
            discountamt : 0
        }
    },
    mounted() {
        this.totamt = this.param.orderInfo.ordtotprice + this.param.orderInfo.dadadelivamt + this.param.orderInfo.ptndelivamt;
        this.discountamt = this.param.orderInfo.totsalepromoamt + this.param.orderInfo.totgoodscpnamt + this.param.orderInfo.basketcpnamt + this.param.orderInfo.totdelivcpnamt;
    },
    methods : {
        print() {
            const options = {
                name: '_blank',
                specs: [
                    'fullscreen=no',
                    'titlebar=no',
                    'scrollbars=yes'
                ],
                styles: [
                    location.protocol + '//' + location.host + '/assets/css/print.css'
                ],
                timeout: 1000, // default timeout before the print window appears
                autoClose: true, // if false, the window will not close after printing
                windowTitle: '인쇄', // override the window title
            }
            this.$htmlToPaper('transactionDetailModal', options);
        }
    }
}
</script>

<style>

</style>