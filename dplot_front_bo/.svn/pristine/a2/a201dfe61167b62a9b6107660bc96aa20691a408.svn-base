<template>
    <!-- 주문상세정보 - 교환 팝업 -->
    <div class="tab-area" :style="isPartner? 'height: calc(90vh - 100px);':'height: calc(90vh - 290px);'">
        <template v-for="(item, index) in claimList">
            <hr class="dash" :key="'dash'+index" v-if="index > 0"/><!-- 차수별 구분선 -->
            <div class="clearfix" :key="'title'+index">
                <div class="bar-title small fl">교환상품 [교환 {{ index + 1 }}차]</div>
            </div>
            <table cellpadding="0" cellspacing="0" class="data-tb align-c mt0" :key="'list'+index">
                <caption>교환상품 목록</caption>
                <colgroup>
                    <col width="5.8%" /><!-- 교환 신청일 -->
                    <col width="5.8%" /><!-- 교환 완료일 -->
                    <col width="4.5%" /><!-- 판매구분 -->
                    <col width="5.5%" /><!-- 파트너사 -->
                    <col width="6.5%" /><!-- 상품코드 -->
                    <col width="3.5%" /><!-- 단품코드 -->
                    <col width="62px" /><!-- 상품이미지 -->
                    <col width="" /><!-- 상품명 -->
                    <col width="7%" /><!-- 옵션 -->
                    <col width="3.8%" /><!-- 주문수량 -->
                    <col width="3.8%" /><!-- 교환수량 -->
                    <col width="5.8%" /><!-- 판매단가 -->
                    <col width="5.8%" /><!-- 판매금액 -->
                    <col width="5%" /><!-- 귀책대상 -->
                    <col width="4.5%" /><!-- 교환상태 -->
                    <col width="8.3%" /><!-- 교환번호 -->
                </colgroup>
                <thead>
                    <tr>
                        <th>교환 신청일</th>
                        <th>교환 완료일</th>
                        <th>판매구분</th>
                        <th>파트너사</th>
                        <th>상품코드</th>
                        <th>단품코드</th>
                        <th colspan="2">상품명</th>
                        <th>옵션</th>
                        <th>주문수량</th>
                        <th>교환수량</th>
                        <th>판매단가</th>
                        <th>판매금액</th>
                        <th>귀책대상</th>
                        <th>교환상태</th>
                        <th>교환번호</th>
                    </tr>
                </thead>
                <tbody v-if="item.claimgoodslist.length > 0">
                    <tr v-for="row in item.claimgoodslist" :key="row.clmgdidx">
                        <td>{{ row.clmreqdate }}</td>
                        <td>{{ row.clmcompdate }}</td>
                        <td>{{ row.ispbgoods==='T'? '자사':'파트너사'}}</td>
                        <td>{{ row.dealernm }}</td>
                        <td>{{ row.goodscode }}</td>
                        <td>{{ row.optioncode }}</td>
                        <td>
                            <div class="img-thumb size60 link" :class="{'no-image': $util.isNull(row.fullpath)}">
                                <img :src="row.fullpath" v-if="!$util.isNull(row.fullpath)">
                            </div>
                        </td>
                        <td class="left no-left"><a href="javascript:void(0);" class="link" @click="goGoodsDetail(row.goodsno)">{{ row.goodsname }}</a></td>
                        <td style="white-space: pre-wrap">{{ row.bfoptionname }}</td>
                        <td>{{ $util.maskComma(row.bfordcnt) }}</td>
                        <td>{{ $util.maskComma(row.clmcnt) }}</td>
                        <td class="right">{{ $util.maskComma(row.price) }}</td>
                        <td class="right">{{ $util.maskComma(Number(row.price) * Number(row.bfordcnt)) }}</td>
                        <td>{{ row.exctype===$store.getters['ADMIN'].EXCHANGE_OPTION_CHANGE? '고객':'판매자'}}</td>
                        <td><a href="javascript:void(0);" class="link" @click="goClaimHistory(row.clmgdidx)">{{ row.boclmstatusnm }}</a></td>
                        <td><a href="javascript:void(0);" class="link" @click="goExchangeDetail(row)">{{ row.clmno }}</a></td>
                    </tr>
                </tbody>
            </table>
            <div class="col3 pd" :key="'orderInfo'+index">
                <div class="left">
                    <div class="bar-title small">이전 주문정보</div>
                    <table cellpadding="0" cellspacing="0" class="gray-tb lower">
                        <colgroup>
                            <col width="150px">
                            <col width="230px">
                            <col width="">
                        </colgroup>
                        <tbody>
                            <tr>
                                <th>판매금액 합계</th>
                                <td colspan="2" class="right">{{ $util.maskComma(item.bfordtotprice) }}</td>
                            </tr>
                            <tr v-if="!isPartner">
                                <th>자사 배송비(+)</th>
                                <td>{{ item.isolatetype==='N'? '기본배송비':'도서산간배송비' }}</td>
                                <td class="right">{{ $util.maskComma(item.bfdadadelivamt) }}</td>
                            </tr>
                            <tr>
                                <th>파트너사 배송비(+)</th>
                                <td>{{ item.isolatetype==='N'? '기본배송비':'도서산간배송비' }}</td>
                                <td class="right">{{ $util.maskComma(item.bfptndelivamt) }}</td>
                            </tr>
                            <tr>
                                <th>프로모션 할인</th>
                                <td class="txt-gray2" v-if="!$util.isNull(item.bftotsalepromoamt) && Number(item.bftotsalepromoamt)>0"></td>
                                <td class="txt-gray2" v-else>(적용할인없음)</td>
                                <td class="right txt-red">{{ $util.maskComma(Number(item.bftotsalepromoamt)*-1) }}</td>
                            </tr>
                            <tr>
                                <th>상품 할인</th>
                                <td class="txt-gray2" v-if="!$util.isNull(item.bftotgoodscpnamt) && Number(item.bftotgoodscpnamt)>0"></td>
                                <td class="txt-gray2" v-else>(적용할인없음)</td>
                                <td class="right txt-red">{{ $util.maskComma(Number(item.bftotgoodscpnamt)*-1) }}</td>
                            </tr>
                            <tr>
                                <th>장바구니 할인</th>
                                <td class="txt-gray2" v-if="!$util.isNull(item.bfbasketcpnamt) && Number(item.bfbasketcpnamt)>0"></td>
                                <td class="txt-gray2" v-else>(적용할인없음)</td>
                                <td class="right txt-red">{{ $util.maskComma(Number(item.bfbasketcpnamt)*-1) }}</td>
                            </tr>
                            <tr>
                                <th>배송비 할인</th>
                                <td class="txt-gray2" v-if="!$util.isNull(item.bftotdelivcpnamt) && Number(item.bftotdelivcpnamt)>0"></td>
                                <td class="txt-gray2" v-else>(적용할인없음)</td>
                                <td class="right txt-red">{{ $util.maskComma(Number(item.bftotdelivcpnamt)*-1) }}</td>
                            </tr>
                            <tr v-if="!isPartner">
                                <th>임직원적립금 사용(-)</th>
                                <td colspan="2" class="right txt-red">{{ $util.maskComma(Number(item.bfempreservetotamt)*-1) }}</td>
                            </tr>
                            <tr v-if="!isPartner">
                                <th>적립금 사용(-)</th>
                                <td colspan="2" class="right txt-red">{{ $util.maskComma(Number(item.bfreservetotamt)*-1) }}</td>
                            </tr>
                            <tr v-if="!isPartner">
                                <th>D포인트 사용(-)</th>
                                <td colspan="2" class="right txt-red">{{ $util.maskComma(Number(item.bfepointtotamt)*-1) }}</td>
                            </tr>
                            <tr v-if="isPartner">
                                <th>적립금 사용(-)</th>
                                <td colspan="2" class="right txt-red">{{ $util.maskComma((Number(item.bfempreservetotamt)+Number(item.bfreservetotamt)+Number(item.bfepointtotamt))*-1) }}</td>
                            </tr>
                            <tr>
                                <th>실 결제금액</th>
                                <td colspan="2" class="right"><strong class="large-txt">{{ $util.maskComma(item.bfrpaytotprice) }}</strong></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="middle">
                    <div class="bar-title small">재 계산 판매금액 합계</div>
                    <table cellpadding="0" cellspacing="0" class="gray-tb lower">
                        <colgroup>
                            <col width="150px">
                            <col width="230px">
                            <col width="">
                        </colgroup>
                        <tbody>
                            <tr>
                                <th>재 계산 주문금액 합계</th>
                                <td colspan="2" class="right">{{ $util.maskComma(item.afordtotprice) }}</td>
                            </tr>
                            <tr v-if="!isPartner">
                                <th>자사 배송비(+)</th>
                                <td>{{ item.isolatetype==='N'? '기본배송비':'도서산간배송비' }}</td>
                                <td class="right">{{ $util.maskComma(item.afdadadelivamt) }}</td>
                            </tr>
                            <tr>
                                <th>파트너사 배송비(+)</th>
                                <td>{{ item.isolatetype==='N'? '기본배송비':'도서산간배송비' }}</td>
                                <td class="right">{{ $util.maskComma(item.afptndelivamt) }}</td>
                            </tr>
                            <tr>
                                <th>프로모션 할인</th>
                                <td class="txt-gray2" v-if="!$util.isNull(item.aftotsalepromoamt) && Number(item.aftotsalepromoamt)>0"></td>
                                <td class="txt-gray2" v-else>(적용할인없음)</td>
                                <td class="right txt-red">{{ $util.maskComma(Number(item.aftotsalepromoamt)*-1) }}</td>
                            </tr>
                            <tr>
                                <th>상품 할인</th>
                                <td class="txt-gray2" v-if="!$util.isNull(item.aftotgoodscpnamt) && Number(item.aftotgoodscpnamt)>0"></td>
                                <td class="txt-gray2" v-else>(적용할인없음)</td>
                                <td class="right txt-red">{{ $util.maskComma(Number(item.aftotgoodscpnamt)*-1) }}</td>
                            </tr>
                            <tr>
                                <th>장바구니 할인</th>
                                <td class="txt-gray2" v-if="!$util.isNull(item.afbasketcpnamt) && Number(item.afbasketcpnamt)>0"></td>
                                <td class="txt-gray2" v-else>(적용할인없음)</td>
                                <td class="right txt-red">{{ $util.maskComma(Number(item.afbasketcpnamt)*-1) }}</td>
                            </tr>
                            <tr>
                                <th>배송비 할인</th>
                                <td class="txt-gray2" v-if="!$util.isNull(item.aftotdelivcpnamt) && Number(item.aftotdelivcpnamt)>0"></td>
                                <td class="txt-gray2" v-else>(적용할인없음)</td>
                                <td class="right txt-red">{{ $util.maskComma(Number(item.aftotdelivcpnamt)*-1) }}</td>
                            </tr>
                            <tr v-if="!isPartner">
                                <th>임직원적립금 사용(-)</th>
                                <td colspan="2" class="right txt-red">{{ $util.maskComma(Number(item.afempreservetotamt)*-1) }}</td>
                            </tr>
                            <tr v-if="!isPartner">
                                <th>적립금 사용(-)</th>
                                <td colspan="2" class="right txt-red">{{ $util.maskComma(Number(item.afreservetotamt)*-1) }}</td>
                            </tr>
                            <tr v-if="!isPartner">
                                <th>D포인트 사용(-)</th>
                                <td colspan="2" class="right txt-red">{{ $util.maskComma(Number(item.afepointtotamt)*-1) }}</td>
                            </tr>
                            <tr v-if="isPartner">
                                <th>적립금 사용(-)</th>
                                <td colspan="2" class="right txt-red">{{ $util.maskComma((Number(item.afempreservetotamt)+Number(item.afreservetotamt)+Number(item.afepointtotamt))*-1) }}</td>
                            </tr>
                            <tr>
                                <th>재 계산 최종금액</th>
                                <td colspan="2" class="right"><strong class="large-txt">{{ $util.maskComma(item.afrpaytotprice) }}</strong></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="right">
                    <div class="bar-title small">추가배송비</div>
                    <table cellpadding="0" cellspacing="0" class="gray-tb lower">
                        <colgroup>
                            <col width="150px">
                            <col width="">
                        </colgroup>
                        <tbody>
                            <tr v-if="!isPartner">
                                <th>자사배송비</th>
                                <td class="right">{{ $util.maskComma(item.adddadadelivamt) }}</td>
                            </tr>
                            <tr>
                                <th>파트너사 배송비</th>
                                <td class="right">{{ $util.maskComma(item.addptndelivamt) }}</td>
                            </tr>
                        </tbody>
                    </table>
                    <template v-if="Number(item.addrpaytotprice) > 0">
                        <div class="bar-title small">추가결제내역<span class="normal txt-orange ml10" v-if="item.excstatus === $store.getters['ADMIN'].EXCHANGE_WAITING_DEPOSIT">추가금 발생일로부터 {{ $util.maskComma(item.elapsedaycnt) }}일 경과</span></div>
                        <table cellpadding="0" cellspacing="0" class="gray-tb lower">
                            <colgroup>
                                <col width="150px">
                                <col width="">
                            </colgroup>
                            <tbody>
                                <tr>
                                    <th>결제일시</th>
                                    <td>{{ item.addpaymentdate }}</td>
                                </tr>
                                <tr>
                                    <th>결제자명</th>
                                    <td>{{ item.ordname }}</td>
                                </tr>
                                <tr>
                                    <th>결제수단</th>
                                    <td>{{ item.addpaywaytypenm }} 
                                        {{ item.addpaywaytype===$store.getters['ADMIN'].PAY_CREDIT_CARD? '('+item.cardcompany + '/' + (item.planmonth==0||$util.isNull(item.planmonth)? '일시불':item.planmonth) +')' : '' }}
                                        {{ item.addpaywaytype===$store.getters['ADMIN'].PAY_VIRTURE_ACCOUNT? '('+item.virbank + '/' + item.accntnumber+')' : '' }}
                                        {{ item.addpaywaytype===$store.getters['ADMIN'].PAY_ACCOUNT_TRANSFER? '('+item.trsbank+')' : '' }}
                                        {{ item.addpaywaytype===$store.getters['ADMIN'].PAY_MOBILE? '('+item.mopcarrier+')' : '' }}
                                    </td>
                                </tr>
                                <tr>
                                    <th>금액</th>
                                    <td class="right">{{ item.addrpaytotprice===0? '' : $util.maskComma(item.addrpaytotprice) }}</td>
                                </tr>
                            </tbody>
                        </table>
                    </template>
                    <div class="bar-title small">환불 금액</div>
                    <table cellpadding="0" cellspacing="0" class="gray-tb lower">
                        <colgroup>
                            <col width="150px">
                            <col width="">
                        </colgroup>
                        <tbody>
                            <tr>
                                <th>환불예정금액</th>
                                <td class="right">{{ $util.maskComma(item.rtnamt) }}</td>
                            </tr>
                            <tr>
                                <th>최종환불금액</th>
                                <td class="right">{{ $util.maskComma(item.finadjustamt) }}</td>
                            </tr>
                            <tr>
                                <th>조정금액(-)</th>
                                <td class="right">{{ $util.maskComma((Number(item.rtnamt) - Number(item.finadjustamt))*-1) }}</td>
                            </tr>
                            <tr>
                                <th>조정사유</th>
                                <td>{{ item.adjustreason }}</td>
                            </tr>
                        </tbody>
                    </table>
                    <template v-if="item.paywaytype === $store.getters['ADMIN'].PAY_VIRTURE_ACCOUNT && Number(item.rtnpayamt) > 0">
                        <div class="bar-title small">환불계좌 정보</div>
                        <table cellpadding="0" cellspacing="0" class="gray-tb lower">
                            <colgroup>
                                <col width="150px">
                                <col width="">
                            </colgroup>
                            <tbody>
                                <tr>
                                    <th>예금주명</th>
                                    <td>{{ item.refcusname }}</td>
                                </tr>
                                <tr>
                                    <th>환불계좌</th>
                                    <td>[{{ item.refbanknm }}] {{ item.refaccnumber }}</td>
                                </tr>
                            </tbody>
                        </table>
                    </template>
                    <div class="bar-title small">최종환불</div>
                    <table cellpadding="0" cellspacing="0" class="gray-tb lower">
                        <colgroup>
                            <col width="150px">
                            <col width="">
                        </colgroup>
                        <tbody>
                            <tr>
                                <th>금액(PG)</th>
                                <td>{{ item.paywaytypenm }}</td>
                                <td class="right"><strong class="large-txt">{{ $util.maskComma(item.rtnpayamt) }}</strong></td>
                            </tr>
                            <tr v-if="!isPartner">
                                <th>임직원적립금 반환</th>
                                <td></td>
                                <td class="right">{{ $util.maskComma(item.rtnempresamt) }}</td>
                            </tr>
                            <tr v-if="!isPartner">
                                <th>적립금 반환</th>
                                <td></td>
                                <td class="right">{{ $util.maskComma(item.rtnresamt) }}</td>
                            </tr>
                            <tr v-if="!isPartner">
                                <th>D포인트 반환</th>
                                <td></td>
                                <td class="right">{{ $util.maskComma(item.rtnepoamt) }}</td>
                            </tr>
                            <tr v-if="isPartner">
                                <th>적립금 반환</th>
                                <td></td>
                                <td class="right">{{ $util.maskComma(Number(item.rtnempresamt)+Number(item.rtnresamt)+Number(item.rtnepoamt)) }}</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </template>
        <template v-if="$util.isNull(claimList) || claimList.length === 0">
            <div class="clearfix">
                <div class="bar-title small fl">교환상품</div>
            </div>
            <table cellpadding="0" cellspacing="0" class="data-tb align-c mt0">
                <caption>교환상품 목록</caption>
                <colgroup>
                    <col width="5.8%" /><!-- 교환 신청일 -->
                    <col width="5.8%" /><!-- 교환 완료일 -->
                    <col width="4.5%" /><!-- 판매구분 -->
                    <col width="5.5%" /><!-- 파트너사 -->
                    <col width="6.5%" /><!-- 상품코드 -->
                    <col width="3.5%" /><!-- 단품코드 -->
                    <col width="3.5%" /><!-- 상품순번 -->
                    <col width="62px" /><!-- 상품이미지 -->
                    <col width="" /><!-- 상품명 -->
                    <col width="7%" /><!-- 옵션 -->
                    <col width="3.8%" /><!-- 주문수량 -->
                    <col width="3.8%" /><!-- 교환수량 -->
                    <col width="5.8%" /><!-- 판매단가 -->
                    <col width="5.8%" /><!-- 판매금액 -->
                    <col width="5%" /><!-- 귀책대상 -->
                    <col width="4.5%" /><!-- 교환상태 -->
                    <col width="8.3%" /><!-- 교환번호 -->
                </colgroup>
                <thead>
                    <tr>
                        <th>교환 신청일</th>
                        <th>교환 완료일</th>
                        <th>판매구분</th>
                        <th>파트너사</th>
                        <th>상품코드</th>
                        <th>단품코드</th>
                        <th>상품순번</th>
                        <th colspan="2">상품명</th>
                        <th>옵션</th>
                        <th>주문수량</th>
                        <th>교환수량</th>
                        <th>판매단가</th>
                        <th>판매금액</th>
                        <th>귀책대상</th>
                        <th>교환상태</th>
                        <th>교환번호</th>
                    </tr>
                </thead>
                <tbody>
                    <tr><td colspan="17">교환내역이 존재하지 않습니다.</td></tr>
                </tbody>
            </table>
        </template>
        <div class="bar-title small">관리자 메모</div>
        <div class="memo-write" v-if="isWrite">
            <textarea placeholder="메모를 작성해 주세요!" v-model="memo.inputMemo"></textarea>
            <button type="button" class="btn big blue" @click="addMemo">메모<br>저장</button>
        </div>
        <div class="scroll-y" style="width: 100%; max-height: 550px; margin-bottom: 0;">
            <table cellpadding="0" cellspacing="0" class="data-tb align-c">
                <colgroup>
                    <col width="5%" /><!-- No -->
                    <col width="20%" /><!-- 작성일시 -->
                    <col width="8%" /><!-- 작성자 -->
                    <col width="" /><!-- 상품명 -->
                    <col width="40px" /><!-- 삭제 -->
                </colgroup>
                <thead>
                    <tr>
                        <th>No</th>
                        <th>작성일시
                            <button type="button" class="sort" :value="memo.psort"
                                :class="[{up : memo.psort === 'regdate_asc'}, {down : memo.psort === 'regdate_desc'}]"
                                @click="memoSortToggle(memo.psort)"></button>
                        </th>
                        <th>작성자</th>
                        <th colspan="2">내용</th>
                    </tr>
                </thead>
                <tbody v-if="!$util.isNull(orderMemoList) && orderMemoList.length > 0">
                    <tr v-for="(item, index) in orderMemoList" :key="item.ordmemoidx">
                        <td>{{ index + 1 }}</td>
                        <td>{{ item.regdate }}</td>
                        <td>{{ item.regusername }}</td>
                        <td class="left">{{ item.memo }}</td>
                        <td class="no-left"><button type="button" class="del mg0" @click="removeMeno(item.ordmemoidx)" v-if="isWrite"></button></td>
                    </tr>
                </tbody>
                <tbody v-else>
                    <tr><td colspan="5">조회 결과가 존재하지 않습니다.</td></tr>
                </tbody>
            </table>
        </div>
        <GoodsDetail v-if="isGoodsDetailShow" :activeGoodsNo="activeGoodsNo" @closePopup="closeGoodsDetail"></GoodsDetail>
        <ExchangeDetail v-if="isShowExchangeDetail" :activeClmParam="activeClmParam" :activeOrderInfo="activeOrderInfo" @closeDetail="closeExchangeDetail"></ExchangeDetail>
    </div>
    <!-- /주문상세정보 - 교환 팝업 -->
</template>

<script>
import GoodsDetail from '@views.admin/goods/manage/GoodsDetail.vue';
import ExchangeDetail from '@views.admin/order/claim/ExchangeDetail.vue';
import ClaimStatusHistoryPopup from '@views.admin/order/popup/ClaimStatusHistoryPopup.vue';

export default {
    name: "AdminOrderExchange",
    props: {
        orderInfo: Object,
        activeOrderIdx: Number,
        isRead: Boolean,
        isWrite: Boolean
    },
    components:{
        GoodsDetail, ExchangeDetail
    },
    data() {
        return {
            isPartner: false,
            user: {},
            claimList: [],              // 교환목록
            orderMemoList: [],          // 관리자메모목록
            memo: {
                inputMemo: '',          // 입력된 메모내용
                psort: 'regdate_desc'   // 메모정렬
            },
            activeGoodsNo: '',
            activeClmParam: {},
            activeOrderInfo: {},
            isGoodsDetailShow: false,     // 상품상세 노출여부
            isShowExchangeDetail: false,  // 클레임교환상세 팝업 노출여부
        }
    },
    mounted() {
        this.isPartner = this.$util.isAuthorized(this.$store.getters['CONSTANTS'].PARTNER_USER);
        this.user = this.$util.getUser(this.$store.getters['CONSTANTS'].MANAGER_SESSION);
        this.getOrderExcInfo();
    },
    methods: {
        // 교환내역 조회
        getOrderExcInfo: function() {
            let params = {
                orderidx : this.activeOrderIdx, 
                clmtype : this.$store.getters['ADMIN'].CLM_EXCHANGE, 
                dealerno: this.isPartner? this.user.no : ''
            };
            this.$http.post('/admin/order/manage/claim/info', params)
                .then(result => {
                    this.$util.debug(result);
                    this.claimList = result.data.claimlist;
                    this.orderMemoList = result.data.ordermemolist;
                }).catch(error => {
                    this.$util.debug(error);
                })
        },
        // 메모 추가
        addMemo: function() {
            if (this.$util.isNull(this.memo.inputMemo.trim())){
                alert('메모 내용을 입력해주세요.');
                return;
            }
            let params = {
                orderidx: this.activeOrderIdx,
                memo: this.memo.inputMemo
            }
            this.$http.post('/admin/order/manage/memo/add', params)
                .then(result => {
                    this.$util.debug(result);
                    this.memo.inputMemo = '';
                    this.getOrderExcInfo();
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 메모 삭제
        removeMeno: function(value) {
            let params = { ordmemoidx: value };
            this.$http.post('/admin/order/manage/memo/remove', params)
                .then(result => {
                    this.$util.debug(result);
                    this.memo.inputMemo = '';
                    this.getOrderExcInfo();
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 메모정렬
        memoSortToggle(key){            
            let arr = key.split('_');
            let sortKey = arr[0];
            let sortOrder = (arr[1] === 'asc') ? 'desc' : 'asc';
            let sortName = sortKey + '_' + sortOrder;
            this.memo.psort = sortName;

            this.orderMemoList.sort((a, b) => {
                a[sortKey] = this.$util.isNull(a[sortKey]) ? '' : a[sortKey];
                b[sortKey] = this.$util.isNull(b[sortKey]) ? '' : b[sortKey];
                if (a[sortKey] < b[sortKey]) {
                    return sortOrder == 'asc'? -1: 1;
                } else if (a[sortKey] > b[sortKey]) {
                    return sortOrder == 'asc'? 1: -1;
                }
                return 0;
            });
        },
        // 클레임상세 팝업 오픈
        goExchangeDetail: function(obj) {
            this.isShowExchangeDetail = true;
            this.activeClmParam = {
                orderidx: obj.orderidx,
                clmidx: obj.clmidx,
                clmgdidx: obj.clmgdidx
            };
            this.activeOrderInfo = Object.assign({}, obj, this.orderInfo);
        },
        // 클레임상세 팝업 닫기
        closeExchangeDetail: function(isreload) {
            this.isShowExchangeDetail = false;
            if (isreload) {
                this.getOrderExcInfo();
            }
        },
        // 클레임상태변경이력 팝업 오픈
        goClaimHistory: function(value) {
            let param = { clmgdidx: value };
            this.$eventBus.$emit('modalShow', ClaimStatusHistoryPopup, param, null);
        },
        // 상품상세 팝업 오픈
        goGoodsDetail: function(value) {
            this.activeGoodsNo = value;
            this.isGoodsDetailShow = true;
        },
        // 상품상세 팝업 닫기
        closeGoodsDetail: function () {
            this.isGoodsDetailShow = false;
        },
        onClose() {
            this.info = this.$options.data().info;
            this.$emit('closeDetail');
        },
    }
}
</script>
