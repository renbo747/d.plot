<template>
    <!-- 어드민 취소상세 팝업 -->
    <div id="modal-wrap" class="modal" style="display: block;">
        <div class="modal-content" style="width: 1600px;">
            <div class="pop-header">
                <h2>주문취소상세{{ ' - ' + claimInfo.cncstatusnm }}</h2>
                <button type="button" class="pop-close" @click="onClose"></button>
            </div>
            <div class="pop-body">
                <div class="gray-box mg0">
                    <div class="clearfix">
                        <div class="fl">[{{ orderInfo.ordpathtypename }}] 주문번호 : <strong>{{ orderInfo.ordno }}</strong></div>
                        <div class="fr txt-gray">
                            <span>주문일 : {{ orderInfo.orderdate }}</span>
                        </div>
                    </div>
                    <hr class="solid" style="margin: 10px 0;" />
                    <div class="clearfix">
                        <div class="fl">
                            <span v-if="orderInfo.isnonmember=='F'">[{{ orderInfo.membertypename }}] [{{ orderInfo.memlvtypename }}] {{ orderInfo.ordname }}({{ orderInfo.ordid }})</span>
                            <span v-else>{{ orderInfo.ordname }}</span>
                            <span class="left-bar" v-if="orderInfo.isnonmember=='F'">{{ $util.maskTel(orderInfo.ordtel) }}</span>
                            <span class="left-bar" v-if="orderInfo.isnonmember=='F'">{{ orderInfo.ordemail }}</span>
                        </div>
                        <div class="fr txt-gray">
                            <span>취소신청일 : {{ orderInfo.clmreqdate }}</span>
                        </div>
                    </div>
                </div>
                <div class="clearfix mt20">
                    <div class="bar-title small fl">주문취소상품 {{ '[부분취소' +claimInfo.clmturn+ '차]' }}</div>
                </div>
                <template v-for="row in claimGoodsList">
                    <table cellpadding="0" cellspacing="0" class="data-tb align-c mt0" :key="'list_'+row.ordgdidx">
                        <caption>취소상품 목록</caption>
                        <colgroup>
                            <col width="4%" /><!-- 판매구분 -->
                            <col width="6%" /><!-- 파트너사 -->
                            <col width="6.5%" /><!-- 상품코드 -->
                            <col width="4%" /><!-- 단품코드 -->
                            <col width="62px" /><!-- 상품이미지 -->
                            <col width="" /><!-- 상품명 -->
                            <col width="8%" /><!-- 옵션 -->
                            <col width="4%" /><!-- 주문수량 -->
                            <col width="4%" /><!-- 취소수량 -->
                            <col width="5.5%" /><!-- 판매단가 -->
                            <col width="5.5%" /><!-- 판매금액 -->
                            <col width="5.5%" /><!-- 취소금액 -->
                            <col width="16%" /><!-- 취소상태 -->
                            <col width="9%" /><!-- 취소번호 -->
                        </colgroup>
                        <thead>
                            <tr>
                                <th>판매구분</th>
                                <th>파트너사</th>
                                <th>상품코드</th>
                                <th>단품코드</th>
                                <th colspan="2">상품명</th>
                                <th>옵션</th>
                                <th>주문수량</th>
                                <th>취소수량</th>
                                <th>판매단가</th>
                                <th>판매금액</th>
                                <th>취소금액</th>
                                <th>취소상태</th>
                                <th>취소번호</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>{{ row.ispbgoods==='T'? '자사':'파트너사' }}</td>
                                <td>{{ row.dealernm }}</td>
                                <td>{{ row.goodscode }}</td>
                                <td>{{ row.optioncode }}</td>
                                <td>
                                    <div class="img-thumb size60 link" @click="goFrontGoodsDetail(row.goodscode)" :class="{'no-image': $util.isNull(row.fullpath)}">
                                        <img :src="row.fullpath" v-if="!$util.isNull(row.fullpath)">
                                    </div>
                                </td>
                                <td class="left no-left">
                                    <a href="javascript:void(0);" class="link" @click="goGoodsDetail(row.goodsno)">{{ row.goodsname }}</a>
                                </td>
                                <td style="white-space: pre-wrap">{{ row.optionname }}</td>
                                <td>{{ $util.maskComma(row.bfordcnt) }}</td>
                                <td>{{ $util.maskComma(row.clmcnt) }}</td>
                                <td class="right">{{ $util.maskComma(row.price) }}</td>
                                <td class="right">{{ $util.maskComma(Number(row.price) * Number(row.bfordcnt)) }}</td>
                                <td class="right">{{ $util.maskComma(Number(row.price) * Number(row.clmcnt)) }}</td>
                                <td>
                                    <select style="width: 170px;" v-model="row.clmstatus" :disabled="!isEdit || row.isDisabled" @change="changeClaimStatus(row)">
                                        <option v-for="item in row.cncstatuslist" :key="item.cmcode" :value="item.cmcode">{{ item.codename }}</option>
                                    </select>
                                    <button type="button" class="btn blue ml3" @click="saveGoodsCncStatus(row)" v-if="isWrite" :disabled="!isEdit || row.isDisabled">저장</button>
                                </td>
                                <td>{{ row.clmno }}</td>
                            </tr>
                        </tbody>
                    </table>
                    <table cellpadding="0" cellspacing="0" class="gray-tb" :key="'reason_'+row.ordgdidx"
                        v-if="(row.orgclmstatus!==$store.getters['ADMIN'].CNC_REQ_REJECT && row.orgclmstatus!==$store.getters['ADMIN'].CNC_REJECT && row.clmstatus===$store.getters['ADMIN'].CNC_REJECT)
                            || row.clmstatus===$store.getters['ADMIN'].CNC_WITHDRAW || row.clmstatus===$store.getters['ADMIN'].CNC_REQ_REJECT
                            ||(row.orgclmstatus===$store.getters['ADMIN'].CNC_REQ_REJECT && row.clmstatus===$store.getters['ADMIN'].CNC_COMP_APPRV)
                            || !$util.isNull(row.rejreason) || !$util.isNull(row.dropreason) || !$util.isNull(row.rejsubreason) || !$util.isNull(row.rejdenyreason)">
                        <colgroup>
                            <col width="150px">
                            <col width="">
                        </colgroup>
                        <tbody>
                            <tr v-if="(row.orgclmstatus!==$store.getters['ADMIN'].CNC_REQ_REJECT && row.orgclmstatus!==$store.getters['ADMIN'].CNC_REJECT && row.clmstatus===$store.getters['ADMIN'].CNC_REJECT) 
                                   || (row.orgclmstatus===$store.getters['ADMIN'].CNC_REJECT && $util.isNull(row.rejsubreason) && !$util.isNull(row.rejreason))">
                                <th>반려사유<i class="essential"></i></th>
                                <td><input type="text" style="width: 100%;" placeholder="반려사유입력" v-model="row.rejreason" maxlength="200" :disabled="!row.isEditReason || row.isDisabled"/></td>
                            </tr>
                            <tr v-if="row.clmstatus===$store.getters['ADMIN'].CNC_WITHDRAW || !$util.isNull(row.dropreason)">
                                <th>철회사유<i class="essential"></i></th>
                                <td><input type="text" style="width: 100%;" placeholder="철회사유입력" v-model="row.dropreason" maxlength="200" :disabled="!row.isEditReason || row.isDisabled"/></td>
                            </tr>
                            <tr v-if="row.clmstatus===$store.getters['ADMIN'].CNC_REQ_REJECT || !$util.isNull(row.rejsubreason)">
                                <th>반려승인요청사유<i class="essential"></i></th>
                                <td><input type="text" style="width: 100%;" placeholder="반려승인요청사유입력" v-model="row.rejsubreason" maxlength="200" disabled/></td>
                            </tr>
                            <tr v-if="(row.orgclmstatus===$store.getters['ADMIN'].CNC_REQ_REJECT && row.clmstatus===$store.getters['ADMIN'].CNC_REJECT) 
                                   || (row.orgclmstatus===$store.getters['ADMIN'].CNC_REJECT && !$util.isNull(row.rejsubreason) && !$util.isNull(row.rejreason))">
                                <th>반려승인사유<i class="essential"></i></th>
                                <td><input type="text" style="width: 100%;" placeholder="반려승인사유입력" v-model="row.rejreason" maxlength="200" :disabled="!row.isEditReason || row.isDisabled"/></td>
                            </tr>
                            <tr v-if="(row.orgclmstatus===$store.getters['ADMIN'].CNC_REQ_REJECT && row.clmstatus===$store.getters['ADMIN'].CNC_COMP_APPRV) 
                                   || !$util.isNull(row.rejdenyreason)">
                                <th>반려승인거부사유<i class="essential"></i></th>
                                <td><input type="text" style="width: 100%;" placeholder="반려승인거부사유입력" v-model="row.rejdenyreason" maxlength="200" :disabled="!row.isEditReason || row.isDisabled"/></td>
                            </tr>
                        </tbody>
                    </table>
                </template>
                <table cellpadding="0" cellspacing="0" class="gray-tb">
                    <colgroup>
                        <col width="150px">
                        <col width="">
                    </colgroup>
                    <tbody>
                        <tr>
                            <th>취소사유<i class="essential"></i></th>
                            <td>
                                <select v-model="claimInfo.cnctype" disabled>
                                    <option value="">구분선택</option>
                                    <option v-for="item in commonCode.cnctype" :key="item.cmcode" :value="item.cmcode">{{ item.codename }}</option>
                                </select>
                                <input type="text" class="ml3" style="width: calc(100% - 156px);" v-model="claimInfo.clmdetail" maxlength="500" disabled/>
                            </td>
                        </tr>
                    </tbody>
                </table>
                <div class="bar-title small" v-if="!$util.isNull(claimGiftList) && claimGiftList.length > 0">취소 사은품</div>
                <table cellpadding="0" cellspacing="0" class="data-tb align-c" v-if="!$util.isNull(claimGiftList) && claimGiftList.length > 0">
                    <caption>사은품 목록</caption>
                    <colgroup>
                        <col width="10%" /><!-- 사은품코드 -->
                        <col width="62px" /><!-- 사은품이미지 -->
                        <col width="" /><!-- 사은품명 -->
                        <col width="7%" /><!-- 수량 -->
                        <col width="35%" /><!-- 적용프로모션 명 -->
                    </colgroup>
                    <thead>
                        <tr>
                            <th>사은품코드</th>
                            <th colspan="2">사은품명
                            <th>수량</th>
                            <th>적용프로모션 명</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="(item, index) in claimGiftList" :key="'cncgift'+index">
                            <td>{{ item.giftcode }}</td>
                            <td>
                                <div class="img-thumb size60" :class="{'no-image': $util.isNull(item.fullpath)}">
                                    <img :src="item.fullpath" v-if="!$util.isNull(item.fullpath)">
                                </div>
                            </td>
                            <td class="left no-left"><p class="mg0">{{ item.giftname }}</p></td>
                            <td>{{ item.rcvamt }}</td>
                            <td>{{ item.promoname }}</td>
                        </tr>
                    </tbody>
                </table>
                <div class="clearfix" v-if="isEditGift || (!$util.isNull(chkReapplyGiftList) && chkReapplyGiftList.length > 0)">
                    <div class="bar-title small fl">재 적용 사은품</div>
                    <!-- <div class="btn-group fr" v-if="isWrite && !isPartner && claimInfo.orgclmstatus !== $store.getters['ADMIN'].CNC_PRCCOMP && isEditGift">
                        <span class="txt-orange mr5">사은품을 다시 선택하시기 바랍니다.</span>
                        <button type="button" class="btn blue-line" @click="selectReapplyGiftList">사은품 선택</button>
                    </div> -->
                </div>
                <table cellpadding="0" cellspacing="0" class="data-tb align-c mt0" v-if="isEditGift || (!$util.isNull(chkReapplyGiftList) && chkReapplyGiftList.length > 0)">
                    <caption>사은품 목록</caption>
                    <colgroup>
                        <col width="10%" /><!-- 사은품코드 -->
                        <col width="62px" /><!-- 사은품이미지 -->
                        <col width="" /><!-- 사은품명 -->
                        <col width="7%" /><!-- 수량 -->
                        <col width="35%" /><!-- 적용프로모션 명 -->
                    </colgroup>
                    <thead>
                        <tr>
                            <th>사은품코드</th>
                            <th colspan="2">사은품명
                            <th>수량</th>
                            <th>적용프로모션 명</th>
                        </tr>
                    </thead>
                    <tbody v-if="chkReapplyGiftList.length > 0">
                        <tr v-for="(item, index) in chkReapplyGiftList" :key="'chkgift'+index">
                            <td>{{ item.giftcode }}</td>
                            <td>
                                <div class="img-thumb size60" :class="{'no-image': $util.isNull(item.fullpath)}">
                                    <img :src="item.fullpath" v-if="!$util.isNull(item.fullpath)">
                                </div>
                            </td>
                            <td class="left no-left"><p class="mg0">{{ item.giftname }}</p></td>
                            <td>{{ item.rcvamt }}</td>
                            <td>{{ item.promoname }}</td>
                        </tr>
                    </tbody>
                    <tbody v-else>
                        <tr><td colspan="5">조회 결과가 존재하지 않습니다.</td></tr>
                    </tbody>
                </table>
                <div class="col3 pd scroll">
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
                                    <td colspan="2" class="right">{{ $util.maskComma(clmInfo.bforder.ordtotprice) }}</td>
                                </tr>
                                <tr>
                                    <th>자사 배송비(+)</th>
                                    <td>{{ clmInfo.bforder.isolatetype==='N'? '기본배송비':'도서산간배송비' }}</td>
                                    <td class="right">{{ $util.maskComma(clmInfo.bforder.dadadelivamt) }}</td>
                                </tr>
                                <tr>
                                    <th>파트너사 배송비(+)</th>
                                    <td>{{ clmInfo.bforder.isolatetype==='N'? '기본배송비':'도서산간배송비' }}</td>
                                    <td class="right">{{ $util.maskComma(clmInfo.bforder.ptndelivamt) }}</td>
                                </tr>
                                <!-- 이전프로모션할인 -->
                                <tr v-if="$util.isNull(clmInfo.bfpromotion) || clmInfo.bfpromotion.length === 0">
                                    <th>프로모션 할인</th>
                                    <td class="txt-gray2">(적용할인없음)</td>
                                    <td class="right txt-red">0</td>
                                </tr>
                                <tr v-else v-for="(promoRow, index) in clmInfo.bfpromotion" :key="promoRow.idx">
                                    <th :rowspan="clmInfo.bfpromotion.length" v-if="index===0">프로모션 할인</th>
                                    <td>{{ promoRow.promoname }}</td>
                                    <td class="right txt-red">{{ $util.maskComma(Number(promoRow.disamount)*-1) }}</td>
                                </tr>
                                <!-- 이전상품쿠폰할인 -->
                                <tr v-if="$util.isNull(clmInfo.bfgoodscoupon) || clmInfo.bfgoodscoupon.length === 0">
                                    <th>상품 할인</th>
                                    <td class="txt-gray2">(적용할인없음)</td>
                                    <td class="right txt-red">0</td>
                                </tr>
                                <tr v-else v-for="(goodsCpnRow, index) in clmInfo.bfgoodscoupon" :key="goodsCpnRow.idx">
                                    <th :rowspan="clmInfo.bfgoodscoupon.length" v-if="index===0">상품 할인</th>
                                    <td>{{ goodsCpnRow.cpnname }}</td>
                                    <td class="right txt-red">{{ $util.maskComma(Number(goodsCpnRow.disamount)*-1) }}</td>
                                </tr>
                                <!-- 이전장바구니쿠폰할인 -->
                                <tr>
                                    <th>장바구니 할인</th>
                                    <td v-if="!$util.isNull(clmInfo.bfbasketcoupon) && clmInfo.bforder.basketcpnamt > 0">{{ clmInfo.bfbasketcoupon.cpnname }}</td>
                                    <td v-else class="txt-gray2">(적용할인없음)</td>
                                    <td class="right txt-red">{{ $util.maskComma(Number(clmInfo.bforder.basketcpnamt)*-1) }}</td>
                                </tr>
                                <!-- 이전배송비쿠폰할인 -->
                                <tr>
                                    <th>배송비 할인</th>
                                    <td v-if="!$util.isNull(clmInfo.bfdelivcoupon) && clmInfo.bforder.totdelivcpnamt > 0">{{ clmInfo.bfdelivcoupon.cpnname }}</td>
                                    <td v-else class="txt-gray2">(적용할인없음)</td>
                                    <td class="right txt-red">{{ $util.maskComma(Number(clmInfo.bforder.totdelivcpnamt)*-1) }}</td>
                                </tr>
                                <tr>
                                    <th>임직원적립금 사용(-)</th>
                                    <td colspan="2" class="right txt-red">{{ $util.maskComma(Number(clmInfo.bforder.empreservetotamt)*-1) }}</td>
                                </tr>
                                <tr>
                                    <th>적립금 사용(-)</th>
                                    <td colspan="2" class="right txt-red">{{ $util.maskComma(Number(clmInfo.bforder.reservetotamt)*-1) }}</td>
                                </tr>
                                <tr>
                                    <th>D포인트 사용(-)</th>
                                    <td colspan="2" class="right txt-red">{{ $util.maskComma(Number(clmInfo.bforder.epointtotamt)*-1) }}</td>
                                </tr>
                                <tr>
                                    <th>실 결제금액</th>
                                    <td colspan="2" class="right"><strong class="large-txt">{{ $util.maskComma(clmInfo.bforder.rpaytotprice) }}</strong></td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="middle">
                        <div class="bar-title small">주문금액 재 계산</div>
                        <table cellpadding="0" cellspacing="0" class="gray-tb lower">
                            <colgroup>
                                <col width="150px">
                                <col width="230px">
                                <col width="">
                            </colgroup>
                            <tbody>
                                <tr>
                                    <th>재 계산 주문금액 합계</th>
                                    <td colspan="2" class="right">{{ $util.maskComma(clmInfo.aforder.ordtotprice) }}</td>
                                </tr>
                                <tr>
                                    <th>자사 배송비(+)</th>
                                    <td>{{ clmInfo.bforder.isolatetype==='N'? '기본배송비':'도서산간배송비' }}</td>
                                    <td class="right">{{ $util.maskComma(clmInfo.aforder.dadadelivamt) }}</td>
                                </tr>
                                <tr>
                                    <th>파트너사 배송비(+)</th>
                                    <td>{{ clmInfo.bforder.isolatetype==='N'? '기본배송비':'도서산간배송비' }}</td>
                                    <td class="right">{{ $util.maskComma(clmInfo.aforder.ptndelivamt) }}</td>
                                </tr>
                                <!-- 재계산프로모션할인 -->
                                <tr v-if="$util.isNull(clmInfo.afpromotion) || clmInfo.afpromotion.length === 0">
                                    <th>프로모션 할인</th>
                                    <td class="txt-gray2">(적용할인없음)</td>
                                    <td class="right txt-red">0</td>
                                </tr>
                                <tr v-else v-for="(promoRow, index) in clmInfo.afpromotion" :key="promoRow.idx">
                                    <th :rowspan="clmInfo.afpromotion.length" v-if="index===0">프로모션 할인</th>
                                    <td>{{ promoRow.promoname }}</td>
                                    <td class="right txt-red">{{ $util.maskComma(Number(promoRow.disamount)*-1) }}</td>
                                </tr>
                                <!-- 재계산상품쿠폰할인 -->
                                <tr v-if="$util.isNull(clmInfo.afgoodscoupon) || clmInfo.afgoodscoupon.length === 0">
                                    <th>상품 할인</th>
                                    <td class="txt-gray2">(적용할인없음)</td>
                                    <td class="right txt-red">0</td>
                                </tr>
                                <tr v-else v-for="(goodsCpnRow, index) in clmInfo.afgoodscoupon" :key="goodsCpnRow.idx">
                                    <th :rowspan="clmInfo.afgoodscoupon.length" v-if="index===0">상품 할인</th>
                                    <td>{{ goodsCpnRow.cpnname }}</td>
                                    <td class="right txt-red">{{ $util.maskComma(Number(goodsCpnRow.disamount)*-1) }}</td>
                                </tr>
                                <!-- 재계산장바구니쿠폰할인 -->
                                <tr>
                                    <th>장바구니 할인</th>
                                    <td v-if="!$util.isNull(clmInfo.afbasketcoupon) && clmInfo.aforder.basketcpnamt > 0">{{ clmInfo.afbasketcoupon.cpnname }}</td>
                                    <td v-else class="txt-gray2">(적용할인없음)</td>
                                    <td class="right txt-red">{{ $util.maskComma(Number(clmInfo.aforder.basketcpnamt)*-1) }}</td>
                                </tr>
                                <!-- 재계산배송비쿠폰할인 -->
                                <tr>
                                    <th>배송비 할인</th>
                                    <td v-if="!$util.isNull(clmInfo.afdelivcoupon) && clmInfo.aforder.totdelivcpnamt > 0">{{ clmInfo.afdelivcoupon.cpnname }}</td>
                                    <td v-else class="txt-gray2">(적용할인없음)</td>
                                    <td class="right txt-red">{{ $util.maskComma(Number(clmInfo.aforder.totdelivcpnamt)*-1) }}</td>
                                </tr>
                                <tr>
                                    <th>임직원적립금 사용(-)</th>
                                    <td colspan="2" class="right txt-red">{{ $util.maskComma(Number(clmInfo.aforder.empreservetotamt)*-1) }}</td>
                                </tr>
                                <tr>
                                    <th>적립금 사용(-)</th>
                                    <td colspan="2" class="right txt-red">{{ $util.maskComma(Number(clmInfo.aforder.reservetotamt)*-1) }}</td>
                                </tr>
                                <tr>
                                    <th>D포인트 사용(-)</th>
                                    <td colspan="2" class="right txt-red">{{ $util.maskComma(Number(clmInfo.aforder.epointtotamt)*-1) }}</td>
                                </tr>
                                <tr>
                                    <th>재 계산 최종금액</th>
                                    <td colspan="2" class="right"><strong class="large-txt">{{ $util.maskComma(clmInfo.aforder.rpaytotprice) }}</strong></td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="right">
                        <template v-if="Number(clmInfo.calinfo.rtndadadelivamt) + Number(clmInfo.calinfo.rtnptndelivamt) > 0">
                            <div class="bar-title small">차감배송비</div>
                            <table cellpadding="0" cellspacing="0" class="gray-tb lower">
                                <colgroup>
                                    <col width="150px">
                                    <col width="">
                                </colgroup>
                                <tbody>
                                    <tr>
                                        <th>자사배송비</th>
                                        <td class="right">{{ $util.maskComma(clmInfo.calinfo.rtndadadelivamt) }}</td>
                                    </tr>
                                    <tr>
                                        <th>파트너사 배송비</th>
                                        <td class="right">{{ $util.maskComma(clmInfo.calinfo.rtnptndelivamt) }}</td>
                                    </tr>
                                </tbody>
                            </table>
                        </template>
                        <div class="bar-title small">추가 결제 예정 금액<span class="normal txt-orange ml10">고객 직접 결제</span></div>
                        <table cellpadding="0" cellspacing="0" class="gray-tb lower">
                            <colgroup>
                                <col width="150px">
                                <col width="">
                            </colgroup>
                            <tbody>
                                <tr>
                                    <th>결제예정금액</th>
                                    <td class="right">{{ $util.maskComma(claimInfo.addrpaytotprice) }}</td>
                                </tr>
                            </tbody>
                        </table>
                        <template v-if="Number(claimInfo.addrpaytotprice) > 0">
                            <div class="bar-title small">추가결제내역<span class="normal txt-orange ml10" v-if="claimInfo.cncstatus === $store.getters['ADMIN'].CNC_WAITING_PAYMENT">추가금 발생일로부터 {{ $util.maskComma(claimInfo.elapsedaycnt) }}일 경과</span></div>
                            <table cellpadding="0" cellspacing="0" class="gray-tb lower">
                                <colgroup>
                                    <col width="150px">
                                    <col width="">
                                </colgroup>
                                <tbody>
                                    <tr>
                                        <th>결제일시</th>
                                        <td>{{ claimInfo.addpaymentdate }}</td>
                                    </tr>
                                    <tr>
                                        <th>결제자명</th>
                                        <td>{{ $util.isNull(claimInfo.addpaymentdate)? '':orderInfo.ordname }}</td>
                                    </tr>
                                    <tr>
                                        <th>결제수단</th>
                                        <td>{{ claimInfo.addpaywaytypenm }} 
                                            {{ claimInfo.addpaywaytype===$store.getters['ADMIN'].PAY_CREDIT_CARD? '('+claimInfo.cardcompany + '/' + (claimInfo.planmonth==0||$util.isNull(claimInfo.planmonth)? '일시불':claimInfo.planmonth+'개월') +')' : '' }}
                                            {{ claimInfo.addpaywaytype===$store.getters['ADMIN'].PAY_VIRTURE_ACCOUNT? '('+claimInfo.virbank + '/' + claimInfo.accntnumber+')' : '' }}
                                            {{ claimInfo.addpaywaytype===$store.getters['ADMIN'].PAY_ACCOUNT_TRANSFER? '('+claimInfo.trsbank+')' : '' }}
                                            {{ claimInfo.addpaywaytype===$store.getters['ADMIN'].PAY_MOBILE? '('+claimInfo.mopcarrier+')' : '' }}
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>금액</th>
                                        <td class="right">{{ claimInfo.addrpaytotprice===0? '' : $util.maskComma(claimInfo.addrpaytotprice) }}</td>
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
                                    <td class="right"><strong class="large-txt">{{ $util.maskComma(clmInfo.calinfo.rtnamt) }}</strong></td>
                                </tr>
                            </tbody>
                        </table>
                        <template v-if="clmInfo.bforder.paywaytype === $store.getters['ADMIN'].PAY_VIRTURE_ACCOUNT">
                            <div class="bar-title small">환불계좌 정보</div>
                            <table cellpadding="0" cellspacing="0" class="gray-tb lower">
                                <colgroup>
                                    <col width="150px">
                                    <col width="">
                                </colgroup>
                                <tbody>
                                    <tr>
                                        <th>예금주명</th>
                                        <td>{{ claimInfo.refcusname }}</td>
                                    </tr>
                                    <tr>
                                        <th>환불계좌</th>
                                        <td>[{{ claimInfo.refbanknm }}] {{ claimInfo.refaccnumber }}</td>
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
                                    <td>{{ clmInfo.bforder.paywaytypename }}</td>
                                    <td class="right"><strong class="large-txt">{{ $util.maskComma(claimInfo.rtnpayamt) }}</strong></td>
                                </tr>
                                <tr>
                                    <th>임직원적립금 반환</th>
                                    <td></td>
                                    <td class="right">{{ $util.maskComma(claimInfo.rtnempresamt) }}</td>
                                </tr>
                                <tr>
                                    <th>적립금 반환</th>
                                    <td></td>
                                    <td class="right">{{ $util.maskComma(claimInfo.rtnresamt) }}</td>
                                </tr>
                                <tr>
                                    <th>D포인트 반환</th>
                                    <td></td>
                                    <td class="right">{{ $util.maskComma(claimInfo.rtnepoamt) }}</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="btn-group" v-if="isWrite">
                    <button type="button" class="btn big blue" v-if="claimInfo.iscnccomp==='T' && isEdit" @click="saveClaim('')">취소처리</button>
                    <button type="button" class="btn big red" v-if="claimInfo.cncstatus === $store.getters['ADMIN'].CNC_WAITING_PAYMENT" @click="saveClaim($store.getters['ADMIN'].CNC_WITHDRAW)">취소철회</button>
                    <button type="button" class="btn big darkgray" @click="onClose">닫기</button>
                </div>
            </div>
        </div>
        <AdminMemberInfo v-if="isShowMemDetail" :activeUserNo="activeUserNo" @closeDetail="closeMemDetail"></AdminMemberInfo>
        <GoodsDetail v-if="isGoodsDetailShow" :activeGoodsNo="activeGoodsNo" @closePopup="closeGoodsDetail"></GoodsDetail>
        <AddReapplyGiftPopup v-if="isShowAddGift" :reapplyGiftList="reapplyGiftList" @closePopup="closeAddGiftPopup"></AddReapplyGiftPopup>
    </div>
    <!-- /어드민 취소상세 팝업 -->
</template>

<script>
import AdminMemberInfo from '@views.admin/member/info/AdminMemberInfo.vue';
import GoodsDetail from '@views.admin/goods/manage/GoodsDetail.vue';
import AddReapplyGiftPopup from '@/views/admin/order/popup/AddReapplyGiftPopup.vue';

export default {
    name: 'admin.order.claim.cancel.detail',
    props: ['activeClmParam', 'activeOrderInfo'],
    components:{
        AdminMemberInfo,
        GoodsDetail,
        AddReapplyGiftPopup
    },
    data() {
        return {
            orderInfo: {},          // 주문정보
            claimInfo: {},          // 클레임정보
            claimGoodsList: [],     // 클레임상품목록
            clmInfo: {
                bforder: {},            // 이전클레임 정보
                bfpromotion: [],        // 이전프로모션
                bfgoodscoupon: [],      // 이전상품쿠폰
                bfdelivcoupon: [],      // 이전배송비쿠폰
                bfbasketcoupon: [],     // 이전장바구니쿠폰
                calinfo: {},            // 재계산정보
                aforder: {},            // 재계산정보
                afpromotion: [],        // 재계산프로모션
                afgoodscoupon: [],      // 재계산상품쿠폰
                afdelivcoupon: [],      // 재계산배송비쿠폰
                afbasketcoupon: [],     // 재계산장바구니쿠폰
                dispitems: [],          // 클레임신청(노출)
                items: [],              // 클레임상품
                afitems: [],            // 주문후상품
                giftlist: []            // 사은품목록
            },
            commonCode: {
                cncstatus: [],      // 취소상태
                cnctype: []         // 취소사유
            },
            isEditGift: false,      // 사은품수정가능여부
            reapplyGiftList: [],    // 재적용사은품목록
            claimGiftList: [],      // 클레임사은품목록
            chkReapplyGiftList: [], // 선택한 재적용사은품 목록
            activeUserNo: '',
            activeGoodsNo : false,
            isShowMemDetail: false,     // 회원상세 팝업 노출여부
            isGoodsDetailShow: false,   // 상품상세 팝업 노출여부
            isShowAddGift: false,       // 재적용사은품 팝업 노출여부
            isRead : false,
            isWrite : false,
            isEdit: false,
            isreload: false
        }
    },
    mounted() {
        this.orderInfo = this.activeOrderInfo;
        let params = { url: this.$options.name, isloading: false };
        this.$http.post('/admin/common/pageAuth/check', params)
            .then(result => {
                this.isRead = (result.data.isread === 'T');
                this.isWrite = (result.data.iswrite === 'T');
            
                if(this.isRead) {
                    // 공통코드 조회
                    this.getCommonCodeList();
                } else {
                    alert('페이지 접근 권한이 없습니다.');
                    this.onClose();
                }
            }).catch(error => {
                this.$util.debug(error);
            });
    },
    methods: {
        // 공통코드 목록 조회
        getCommonCodeList: function() {
            let cmclassArr = ['CNCTYPE', 'CNCSTATUS'];
            this.$http.post('/common/code/map/list', {cmclass : cmclassArr, isloading: false})
                .then(result =>{
                    let data = result.data;
                    for (const [key] of Object.entries(data)) {
                        this.commonCode[key] = data[key];
                    }
                    // 취소정보 조회
                    this.searchOrdCancelInfo();
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 취소정보 조회
        searchOrdCancelInfo: function() {
            let params = { 
                orderidx: this.activeClmParam.orderidx, 
                clmidx: this.activeClmParam.clmidx, 
                clmtype: this.$store.getters['ADMIN'].CLM_CANCEL 
            };
            this.$http.post('/admin/order/claim/detail/info', params)
                .then(result => {
                    this.$util.debug(result);
                    let data = result.data;
                    this.claimInfo = data.claiminfo;
                    this.claimGoodsList = data.claimgoodslist;
                    this.claimInfo.orgcncstatus = this.claimInfo.cncstatus;
                    for (const [key] of Object.entries(data)) {
                        this.clmInfo[key] = data[key];
                    }

                    // 재계산 자사/파트너사 배송비, 적립금/임직원적립금/E포인트
                    this.clmInfo.aforder.dadadelivamt = Number(this.clmInfo.aforder.dadadelivamt) - Number(this.clmInfo.calinfo.rtndadadelivamt);
                    this.clmInfo.aforder.ptndelivamt = Number(this.clmInfo.aforder.ptndelivamt) - Number(this.clmInfo.calinfo.rtnptndelivamt);
                    this.clmInfo.aforder.empreservetotamt = Number(this.clmInfo.bforder.empreservetotamt) - Number(this.clmInfo.calinfo.rtnempresamt);
                    this.clmInfo.aforder.reservetotamt = Number(this.clmInfo.bforder.reservetotamt) - Number(this.clmInfo.calinfo.rtnresamt);
                    this.clmInfo.aforder.epointtotamt = Number(this.clmInfo.bforder.epointtotamt) - Number(this.clmInfo.calinfo.rtnepoamt);
                    this.clmInfo.aforder.rpaytotprice = Number(this.clmInfo.aforder.rpaytotprice) - Number(this.clmInfo.calinfo.rtndadadelivamt) - Number(this.clmInfo.calinfo.rtnptndelivamt)
                                                      + Number(this.clmInfo.calinfo.rtnempresamt) + Number(this.clmInfo.calinfo.rtnresamt) + Number(this.clmInfo.calinfo.rtnepoamt);

                    // 클레임정보 세팅(결제대기경과일)
                    this.claimInfo.elapsedaycnt = this.clmInfo.items[0].elapsedaycnt;
                    
                    this.claimGoodsList.forEach(item => {
                        item.orgclmstatus = item.clmstatus;
                        item.cncstatuslist = JSON.parse(JSON.stringify(this.commonCode.cncstatus));
                        item.isDisabled = false;
                        item.isEditReason = true;
                        if (item.orgclmstatus === this.$store.getters['ADMIN'].CNC_WAITING_APPRV) {
                            // 승인대기 : 승인대기/취소반려/취소철회/승인완료
                            item.cncstatuslist.splice(6, 1);
                            item.cncstatuslist.splice(1, 2);
                        } else if (item.orgclmstatus === this.$store.getters['ADMIN'].CNC_WAITING_PAYMENT) {
                            // 결제대기 : 결제대기
                            item.cncstatuslist.splice(2, 5);
                            item.cncstatuslist.splice(0, 1);
                            item.isDisabled = true;
                            item.isEditReason = false;
                        } else if (item.orgclmstatus === this.$store.getters['ADMIN'].CNC_REQ_REJECT) {
                            // 반려승인요청 : 반려승인요청/취소반려/승인완료
                            item.cncstatuslist.splice(6, 1);
                            item.cncstatuslist.splice(4, 1);
                            item.cncstatuslist.splice(0, 2);
                        } else if (item.orgclmstatus === this.$store.getters['ADMIN'].CNC_COMP_APPRV) {
                            // 승인완료 : 승인완료 (반려승인요청사유 보여질 수 있음)
                            item.cncstatuslist.splice(6, 1);
                            item.cncstatuslist.splice(0, 5);
                        } else if (item.orgclmstatus === this.$store.getters['ADMIN'].CNC_REJECT) {
                            // 취소반려 : 취소반려 (반려승인요청사유 보여질 수 있음)
                            item.cncstatuslist.splice(4, 3);
                            item.cncstatuslist.splice(0, 3);
                        } else {
                            item.isDisabled = true;
                            item.isEditReason = false;
                        }
                    });

                    // 수정가능여부 세팅
                    let isNotEditStatusArr = [
                        this.$store.getters['ADMIN'].CNC_PRCCOMP,
                        this.$store.getters['ADMIN'].CNC_WAITING_PAYMENT,
                        this.$store.getters['ADMIN'].CNC_WITHDRAW
                    ];
                    if (isNotEditStatusArr.indexOf(this.claimInfo.cncstatus) < 0) {
                        this.isEdit = true;
                    }

                    // 사은품목록 세팅
                    this.claimGiftList = data.claimgiftlist.filter(item => {return item.isadd==='F';});
                    this.chkReapplyGiftList = data.claimgiftlist.filter(item => {return item.isadd==='T';});
                    this.reapplyGiftList = data.giftlist.filter(item => {return item.isadd==='T';});
                    // 사은품 수정가능여부 세팅
                    let isEditGiftStatusArr = [
                        this.$store.getters['ADMIN'].CNC_WAITING_APPRV
                    ];
                    if (isEditGiftStatusArr.indexOf(this.claimInfo.orgcncstatus) > -1 && !this.$util.isNull(this.reapplyGiftList) && this.reapplyGiftList.length > 0) {
                        this.isEditGift = true;
                    }
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 클레임 환불금액 재계산
        recalClaimRtnAmt: function() {
            let params = {
                orderidx: this.claimInfo.orderidx,
                clmidx : this.claimInfo.clmidx,
                claim: this.clmInfo.dispitems
            }
            this.$http.post('/admin/order/claim/detail/info', params)
                .then(result => {
                    this.$util.debug(result);
                    let data = result.data;
                    this.clmInfo.aforder = data.aforder;
                    this.clmInfo.bforder = data.bforder;
                    this.clmInfo.calinfo = data.calinfo;
                    this.clmInfo.bfpromotion = data.bfpromotion;
                    this.clmInfo.bfgoodscoupon = data.bfgoodscoupon;
                    this.clmInfo.bfdelivcoupon = data.bfdelivcoupon;
                    this.clmInfo.bfbasketcoupon = data.bfbasketcoupon;
                    this.clmInfo.afpromotion = data.afpromotion;
                    this.clmInfo.afgoodscoupon = data.afgoodscoupon;
                    this.clmInfo.afdelivcoupon = data.afdelivcoupon;
                    this.clmInfo.afbasketcoupon = data.afbasketcoupon;
                    this.clmInfo.claimitems = data.items;
                    this.claimInfo = data.claiminfo;
                    this.clmInfo.calinfo.basketcpnidx = this.claimInfo.basketcpnidx;
                    
                    // 재계산 자사/파트너사 배송비, 적립금/임직원적립금/E포인트
                    this.clmInfo.aforder.dadadelivamt = Number(this.clmInfo.aforder.dadadelivamt) - Number(this.clmInfo.calinfo.rtndadadelivamt);
                    this.clmInfo.aforder.ptndelivamt = Number(this.clmInfo.aforder.ptndelivamt) - Number(this.clmInfo.calinfo.rtnptndelivamt);
                    this.clmInfo.aforder.empreservetotamt = Number(this.clmInfo.bforder.empreservetotamt) - Number(this.clmInfo.calinfo.rtnempresamt);
                    this.clmInfo.aforder.reservetotamt = Number(this.clmInfo.bforder.reservetotamt) - Number(this.clmInfo.calinfo.rtnresamt);
                    this.clmInfo.aforder.epointtotamt = Number(this.clmInfo.bforder.epointtotamt) - Number(this.clmInfo.calinfo.rtnepoamt);
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 클레임상품 취소상태 변경
        changeClaimStatus: function(obj) {
            let isEditRsStatusArr = [
                this.$store.getters['ADMIN'].CNC_REQ_REJECT,
                this.$store.getters['ADMIN'].CNC_WITHDRAW,
                this.$store.getters['ADMIN'].CNC_REJECT,
                this.$store.getters['ADMIN'].CNC_COMP_APPRV
            ];
            if (isEditRsStatusArr.indexOf(obj.clmstatus) > -1) {
                obj.isEditReason = true;
            }
        },
        // 클레임상품 취소상태 저장
        saveGoodsCncStatus: function(obj) {
            if (obj.orgclmstatus !== this.$store.getters['ADMIN'].CNC_REQ_REJECT && obj.clmstatus === this.$store.getters['ADMIN'].CNC_REJECT && (this.$util.isNull(obj.rejreason) || obj.rejreason.trim()==='')) {
                alert('반려사유를 입력해주세요.');
                return;
            }
            if (obj.clmstatus === this.$store.getters['ADMIN'].CNC_WITHDRAW && (this.$util.isNull(obj.dropreason) || obj.dropreason.trim()==='')) {
                alert('철회사유를 입력해주세요.');
                return;
            }
            if (obj.clmstatus === this.$store.getters['ADMIN'].CNC_REQ_REJECT && (this.$util.isNull(obj.rejsubreason) || obj.rejsubreason.trim()==='')) {
                alert('반려승인요청사유를 입력해주세요.');
                return;
            }
            if (obj.orgclmstatus === this.$store.getters['ADMIN'].CNC_REQ_REJECT && obj.clmstatus === this.$store.getters['ADMIN'].CNC_REJECT && (this.$util.isNull(obj.rejreason) || obj.rejreason.trim()==='')) {
                alert('반려승인사유를 입력해주세요.');
                return;
            }
            if (obj.orgclmstatus === this.$store.getters['ADMIN'].CNC_REQ_REJECT && obj.clmstatus === this.$store.getters['ADMIN'].CNC_COMP_APPRV && (this.$util.isNull(obj.rejdenyreason) || obj.rejdenyreason.trim()==='')) {
                alert('반려승인거부사유를 입력해주세요.');
                return;
            }
            let params = { 
                orderidx: this.claimInfo.orderidx,
                clmtype: this.claimInfo.clmtype,
                clmidx: obj.clmidx,
                clmgdidx: obj.clmgdidx,
                orgcncstatus: obj.orgclmstatus,
                cncstatus: obj.clmstatus,
                rejreason: obj.clmstatus === this.$store.getters['ADMIN'].CNC_REJECT? obj.rejreason : null,
                dropreason: obj.clmstatus === this.$store.getters['ADMIN'].CNC_WITHDRAW? obj.dropreason : null,
                rejsubreason: obj.orgclmstatus === this.$store.getters['ADMIN'].CNC_REQ_REJECT || obj.clmstatus === this.$store.getters['ADMIN'].CNC_REQ_REJECT? obj.rejsubreason : null,
                rejdenyreason: obj.orgclmstatus === this.$store.getters['ADMIN'].CNC_REQ_REJECT && obj.clmstatus === this.$store.getters['ADMIN'].CNC_COMP_APPRV? obj.rejdenyreason : null,
                adddelivamt: this.claimInfo.adddelivamt,
                addpaytotprice: this.claimInfo.addpaytotprice,
                addrpaytotprice: this.claimInfo.addrpaytotprice,
                finadjustamt: this.claimInfo.finadjustamt
            };
            if (confirm('저장하시겠습니까?')) {
                this.$http.post('/admin/order/claim/save/status', params).then(result => {
                    this.$util.debug(result);
                    if (result.statusCode === 200) {
                        alert('저장이 완료되었습니다.');
                        // 취소정보 조회
                        this.searchOrdCancelInfo();
                        this.isreload = true;
                    }
                });
            }
        },
        // 취소처리 저장
        saveClaim: function(reqStatus) {
            let params = { 
                orderidx: this.claimInfo.orderidx,
                clmtype: this.claimInfo.clmtype,
                clmidx: this.claimInfo.clmidx,
                cncstatus: ''
            };
            if (this.isEditGift) {
                // this.$set(params, 'giftlist', Object.assign([], this.claimGiftList, this.chkReapplyGiftList));
            }
            let msg = '';
            if (this.$util.isNull(reqStatus)) {
                if (Number(this.clmInfo.calinfo.addrpaytotprice)>0) {
                    params.cncstatus = this.$store.getters['ADMIN'].CNC_WAITING_PAYMENT;    // 결제대기
                    msg = '추가결제금이 존재하여 \'취소처리(결제대기)\' 상태로 진행됩니다. 처리하시겠습니까?';
                } else {
                    params.cncstatus = this.$store.getters['ADMIN'].CNC_PRCCOMP;            // 취소완료(처리완료)
                    msg = '취소처리 하시겠습니까?';
                }
            } else {
                params.cncstatus = this.$store.getters['ADMIN'].CNC_WITHDRAW;               // 취소완료(취소철회)
                msg = '취소철회 하시겠습니까?';
            }
            if (confirm(msg)) {
                this.$http.post('/admin/order/claim/save/status', params).then(result => {
                    this.$util.debug(result);
                    if (result.statusCode === 200) {
                        alert('처리가 완료되었습니다.');
                        this.onClose(true);
                    }
                })
                .catch(error => {
                    this.$util.debug(error);
                });
            }
        },
        // 재적용사은품 선택
        selectReapplyGiftList: function() {
            let param = { reapplyGiftList: this.reapplyGiftList };
            this.$eventBus.$emit('modalShow', AddReapplyGiftPopup, param, 
                (result) => {
                    this.chkReapplyGiftList = result.list;
                }
            );
        },
        // 재적용사은품 선택
        closeAddGiftPopup: function() {
            this.isShowAddGift = false;
        },
        // Front 화면으로 이동
        goFrontGoodsDetail: function(value) {
            window.open(process.env.VUE_APP_PC_GOODS_DETAIL_URL + value, "_blank");
        },
        // 회원상세 팝업 오픈
        goMemDetail: function(value) {
            this.isShowMemDetail = true;
            this.activeUserNo = value;
        },
        // 회원상세 팝업 닫기
        closeMemDetail: function() {
            this.isShowMemDetail = false;
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
        // 팝업닫기
        onClose(isreload) {
            if(typeof(isreload) === 'boolean' && isreload) {
                this.isreload = isreload;
                this.$emit('closeDetail', this.isreload);
            } else {
                this.$emit('closeDetail', this.isreload);
            }
        }
    }
}
</script>