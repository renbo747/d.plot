<template>
    <!-- 주문상세정보 - 주문정보 팝업 -->
    <div class="tab-area" :style="isPartner? 'height: calc(90vh - 100px);':'height: calc(90vh - 290px);'">
        <div v-if="($util.isNull(waitingDeposit.list) || waitingDeposit.list.length === 0)
                && ($util.isNull(prepGoods.list) || prepGoods.list.length === 0)
                && ($util.isNull(prepDeliv.list) || prepDeliv.list.length === 0)
                && ($util.isNull(inDeliv.list) || inDeliv.list.length === 0)
                && ($util.isNull(completeDeliv.list) || completeDeliv.list.length === 0)"><!-- 주문정보 없는 테이블-->
            <div class="clearfix">
                <div class="bar-title small fl">주문상품목록</div>
            </div>
            <div class=" mt0" :class="{'scroll-x': !isPartner}">
                <table cellpadding="0" cellspacing="0" class="data-tb align-c" :style="!isPartner? 'width: 1800px;':''">
                    <caption>주문 목록</caption>
                    <colgroup v-if="!isPartner">
                        <col width="4.5%"/><!-- 판매구분 -->
                        <col width="5%" /><!-- 파트너사 -->
                        <col width="5.5%" /><!-- 상품코드 -->
                        <col width="4.5%" /><!-- 단품코드 -->
                        <col width="4.5%" /><!-- 상품순번 -->
                        <col width="62px" /><!-- 상품이미지 -->
                        <col width="" /><!-- 상품명 -->
                        <col width="7%" /><!-- 옵션 -->
                        <col width="4.5%" /><!-- 주문수량 -->
                        <col width="5%" /><!-- 판매단가 -->
                        <col width="5%" /><!-- 판매금액 -->
                        <col width="4%" /><!-- 입금자명 -->
                        <col width="5%" /><!-- 결제수단 -->
                        <col width="4.5%" /><!-- 배송유형 -->
                        <col width="4.5%" /><!-- 배송조건 -->
                        <col width="3.5%" /><!-- 배송비 -->
                        <col width="4.5%" /><!-- 주문상태 -->
                        <col width="3.8%" /><!-- 클레임상태 -->
                        <col width="5.5%" /><!-- 추가상품여부 -->
                    </colgroup>
                    <colgroup v-else>
                        <col width="7%" /><!-- 상품코드 -->
                        <col width="4.5%" /><!-- 단품코드 -->
                        <col width="4.5%" /><!-- 상품순번 -->
                        <col width="62px" /><!-- 상품이미지 -->
                        <col width="" /><!-- 상품명 -->
                        <col width="8%" /><!-- 옵션 -->
                        <col width="4.5%" /><!-- 주문수량 -->
                        <col width="5.5%" /><!-- 판매단가 -->
                        <col width="5.5%" /><!-- 판매금액 -->
                        <col width="5%" /><!-- 결제수단 -->
                        <col width="4.5%" /><!-- 배송유형 -->
                        <col width="4.5%" /><!-- 배송조건 -->
                        <col width="5%" /><!-- 배송비 -->
                        <col width="5%" /><!-- 주문상태 -->
                        <col width="5%" /><!-- 클레임상태 -->
                    </colgroup>
                    <thead>
                        <tr>
                            <th v-if="!isPartner">판매구분</th>
                            <th v-if="!isPartner">파트너사</th>
                            <th>상품코드</th>
                            <th>단품코드</th>
                            <th>상품순번</th>
                            <th colspan="2">상품명</th>
                            <th>옵션</th>
                            <th>주문수량</th>
                            <th>판매단가</th>
                            <th>판매금액</th>
                            <th v-if="!isPartner">입금자</th>
                            <th>결제수단</th>
                            <th>배송유형</th>
                            <th>배송조건</th>
                            <th>배송비</th>
                            <th>주문상태</th>
                            <th>클레임상태</th>
                            <th v-if="!isPartner">추가상품여부</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td :colspan="isPartner? 15:19">주문상품 목록이 존재하지 않습니다.</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <div v-if="!$util.isNull(waitingDeposit.list) && waitingDeposit.list.length > 0"><!-- 주문내역 : 입금대기 -->
            <div class="clearfix">
                <div class="bar-title small fl">입금대기</div>
            </div>
            <div class=" mt0" :class="{'scroll-x': !isPartner}">
                <table cellpadding="0" cellspacing="0" class="data-tb align-c" :style="!isPartner? 'width: 1800px;':''">
                    <caption>주문 목록</caption>
                    <colgroup v-if="!isPartner">
                        <col width="4.5%"/><!-- 판매구분 -->
                        <col width="5%" /><!-- 파트너사 -->
                        <col width="5.5%" /><!-- 상품코드 -->
                        <col width="4.5%" /><!-- 단품코드 -->
                        <col width="4.5%" /><!-- 상품순번 -->
                        <col width="62px" /><!-- 상품이미지 -->
                        <col width="" /><!-- 상품명 -->
                        <col width="7%" /><!-- 옵션 -->
                        <col width="4.5%" /><!-- 주문수량 -->
                        <col width="5%" /><!-- 판매단가 -->
                        <col width="5%" /><!-- 판매금액 -->
                        <col width="4%" /><!-- 입금자명 -->
                        <col width="5%" /><!-- 결제수단 -->
                        <col width="4.5%" /><!-- 배송유형 -->
                        <col width="4.5%" /><!-- 배송조건 -->
                        <col width="3.5%" /><!-- 배송비 -->
                        <col width="4.5%" /><!-- 주문상태 -->
                        <col width="3.8%" /><!-- 클레임상태 -->
                        <col width="5.5%" /><!-- 추가상품여부 -->
                    </colgroup>
                    <colgroup v-else>
                        <col width="7%" /><!-- 상품코드 -->
                        <col width="4.5%" /><!-- 단품코드 -->
                        <col width="4.5%" /><!-- 상품순번 -->
                        <col width="62px" /><!-- 상품이미지 -->
                        <col width="" /><!-- 상품명 -->
                        <col width="8%" /><!-- 옵션 -->
                        <col width="4.5%" /><!-- 주문수량 -->
                        <col width="5.5%" /><!-- 판매단가 -->
                        <col width="5.5%" /><!-- 판매금액 -->
                        <col width="5%" /><!-- 결제수단 -->
                        <col width="4.5%" /><!-- 배송유형 -->
                        <col width="4.5%" /><!-- 배송조건 -->
                        <col width="5%" /><!-- 배송비 -->
                        <col width="5%" /><!-- 주문상태 -->
                        <col width="5%" /><!-- 클레임상태 -->
                    </colgroup>
                    <thead>
                        <tr>
                            <th v-if="!isPartner">판매구분</th>
                            <th v-if="!isPartner">파트너사</th>
                            <th>상품코드</th>
                            <th>단품코드</th>
                            <th>상품순번</th>
                            <th colspan="2">상품명</th>
                            <th>옵션</th>
                            <th>주문수량</th>
                            <th>판매단가</th>
                            <th>판매금액</th>
                            <th v-if="!isPartner">입금자</th>
                            <th>결제수단</th>
                            <th>배송유형</th>
                            <th>배송조건</th>
                            <th>배송비</th>
                            <th>주문상태</th>
                            <th>클레임상태</th>
                            <th v-if="!isPartner">추가상품여부</th>
                        </tr>
                    </thead>
                    <tbody v-if="waitingDeposit.list.length > 0">
                        <tr v-for="(item, index) in waitingDeposit.list" :key="index">
                            <td v-if="!isPartner">{{ item.ispbgoodsname }}</td>
                            <td v-if="!isPartner">{{ item.dealername }}</td>
                            <td>{{ item.goodscode }}</td>
                            <td>{{ item.optioncode }}</td>
                            <td>{{ item.goodsturn }}</td>
                            <td>
                                <div class="img-thumb size60 link" @click="goFrontGoodsDetail(item.goodscode)" :class="{'no-image': $util.isNull(item.fullpath)}">
                                    <img :src="item.fullpath" v-if="!$util.isNull(item.fullpath)">
                                </div>
                            </td>
                            <td class="left no-left">
                                <a href="javascript:void(0);" class="link" @click="goGoodsDetail(item.goodsno)">{{ item.goodsname }}</a>
                            </td>
                            <td style="white-space: pre-wrap">{{ item.optionconts }}</td>
                            <td>{{ $util.maskComma(item.ordcnt) }}</td>
                            <td class="right">{{ $util.maskComma(item.price) }}</td>
                            <td class="right">{{ $util.maskComma(item.totprice) }}</td>
                            <td v-if="!isPartner">{{ item.vircusname }}</td>
                            <td>{{ item.paywaytypename }}</td>
                            <td>{{ item.iscombdelivname }}</td>
                            <td>{{ item.delivfaretypename }}</td>
                            <td class="right">{{ $util.maskComma(item.delivamt) }}</td>
                            <td><a href="javascript:void(0);" class="link" @click="goOrderHistory(item.orgdelividx)">{{ item.ordstatusname }}</a></td>
                            <td><a href="javascript:void(0);" class="link" @click="goClaimHistory(item)">{{ item.clmstatusname }}</a></td>
                            <td v-if="!isPartner">{{ item.isaddgoods }}</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <div v-if="!$util.isNull(prepGoods.list) && prepGoods.list.length > 0"><!-- 주문내역 : 상품준비중 -->
            <div class="clearfix">
                <div class="bar-title small fl">상품준비중</div>
                <div class="btn-group fr" v-if="isWrite">
                    <button type="button" class="btn blue-line" @click="fnChangeOrdStatus('prepGoods', $store.getters['ADMIN'].ORDER_PREPARING_DELIV)">배송준비중</button>
                    <button type="button" class="btn red-line" v-if="!isPartner" @click="goCancelApply('prepGoods');">주문취소</button>
                </div>
            </div>
            <div class=" mt0" :class="{'scroll-x': !isPartner}">
                <table cellpadding="0" cellspacing="0" class="data-tb align-c" :style="!isPartner? 'width: 1800px;':''">
                    <caption>주문 목록</caption>
                    <colgroup v-if="!isPartner">
                        <col width="2%" /><!-- checkbox -->
                        <col width="4.5%" /><!-- 판매구분 -->
                        <col width="5%" /><!-- 파트너사 -->
                        <col width="5.5%" /><!-- 상품코드 -->
                        <col width="5%" /><!-- 단품코드 -->
                        <col width="4.5%" /><!-- 상품순번 -->
                        <col width="62px" /><!-- 상품이미지 -->
                        <col width="" /><!-- 상품명 -->
                        <col width="7%" /><!-- 옵션 -->
                        <col width="4.5%" /><!-- 주문수량 -->
                        <col width="5%" /><!-- 판매단가 -->
                        <col width="5%" /><!-- 판매금액 -->
                        <col width="4.5%" /><!-- 배송유형 -->
                        <col width="4.5%" /><!-- 배송조건 -->
                        <col width="3.5%" /><!-- 배송비 -->
                        <col width="4.5%" /><!-- 주문상태 -->
                        <col width="4%" /><!-- 클레임상태 -->
                        <col width="5.5%" /><!-- 추가상품여부 -->
                    </colgroup>
                    <colgroup v-else>
                        <col width="2%" /><!-- checkbox -->
                        <col width="7%" /><!-- 상품코드 -->
                        <col width="4.5%" /><!-- 단품코드 -->
                        <col width="4.5%" /><!-- 상품순번 -->
                        <col width="62px" /><!-- 상품이미지 -->
                        <col width="" /><!-- 상품명 -->
                        <col width="8%" /><!-- 옵션 -->
                        <col width="4.5%" /><!-- 주문수량 -->
                        <col width="5.5%" /><!-- 판매단가 -->
                        <col width="5.5%" /><!-- 판매금액 -->
                        <col width="4.5%" /><!-- 배송유형 -->
                        <col width="4.5%" /><!-- 배송조건 -->
                        <col width="5%" /><!-- 배송비 -->
                        <col width="5%" /><!-- 주문상태 -->
                        <col width="5%" /><!-- 클레임상태 -->
                    </colgroup>
                    <thead>
                        <tr>
                            <th><input type="checkbox" id="chkall" v-model="prepGoods.isallchk" @change="checkAllList('prepGoods', $event.target.checked)"/></th>
                            <th v-if="!isPartner">판매구분</th>
                            <th v-if="!isPartner">파트너사</th>
                            <th>상품코드</th>
                            <th>단품코드</th>
                            <th>상품순번</th>
                            <th colspan="2">상품명</th>
                            <th>옵션</th>
                            <th>주문수량</th>
                            <th>판매단가</th>
                            <th>판매금액</th>
                            <th>배송유형</th>
                            <th>배송조건</th>
                            <th>배송비</th>
                            <th>주문상태</th>
                            <th>클레임상태</th>
                            <th v-if="!isPartner">추가상품여부</th>
                        </tr>
                    </thead>
                    <tbody v-if="prepGoods.list.length > 0">
                        <tr v-for="(item, index) in prepGoods.list" :key="index">
                            <td><input type="checkbox" :id="item.orgdelividx" v-model="prepGoods.checkedList" :value="item.orgdelividx" @change="checkList('prepGoods')"/></td>
                            <td v-if="!isPartner">{{ item.ispbgoodsname }}</td>
                            <td v-if="!isPartner">{{ item.dealername }}</td>
                            <td>{{ item.goodscode }}</td>
                            <td>{{ item.optioncode }}</td>
                            <td>{{ item.goodsturn }}</td>
                            <td>
                                <div class="img-thumb size60 link" @click="goFrontGoodsDetail(item.goodscode)" :class="{'no-image': $util.isNull(item.fullpath)}">
                                    <img :src="item.fullpath" v-if="!$util.isNull(item.fullpath)">
                                </div>
                            </td>
                            <td class="left no-left">
                                <a href="javascript:void(0);" class="link" @click="goGoodsDetail(item.goodsno)">{{ item.goodsname }}</a>
                            </td>
                            <td style="white-space: pre-wrap">{{ item.optionconts }}</td>
                            <td>{{ $util.maskComma(item.ordcnt) }}</td>
                            <td class="right">{{ $util.maskComma(item.price) }}</td>
                            <td class="right">{{ $util.maskComma(item.totprice) }}</td>
                            <td>{{ item.iscombdelivname }}</td>
                            <td>{{ item.delivfaretypename }}</td>
                            <td class="right">{{ $util.maskComma(item.delivamt) }}</td>
                            <td><a href="javascript:void(0);" class="link" @click="goOrderHistory(item.orgdelividx)">{{ item.ordstatusname }}</a></td>
                            <td><a href="javascript:void(0);" class="link" @click="goClaimHistory(item)">{{ item.clmstatusname }}</a></td>
                            <td v-if="!isPartner">{{ item.isaddgoods }}</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <div v-if="!$util.isNull(prepDeliv.list) && prepDeliv.list.length > 0"><!-- 주문내역 : 배송준비중 -->
            <div class="clearfix">
                <div class="bar-title small fl">배송준비중</div>
                <div class="btn-group fr" v-if="isWrite">
                    <button type="button" class="btn blue-line" @click="saveInvoiceAll">일괄등록처리</button>
                    <button type="button" class="btn blue-line" @click="fnChangeOrdStatus('prepDeliv', $store.getters['ADMIN'].ORDER_PREPARING_GOODS)">상품준비중</button>
                    <button type="button" class="btn blue-line" @click="fnChangeOrdStatus('prepDeliv', $store.getters['ADMIN'].ORDER_IN_DELIVERY)">배송중처리</button>
                    <button type="button" class="btn blue-line" @click="fnChangeOrdStatus('prepDeliv', $store.getters['ADMIN'].ORDER_COMPLETE_DELIV)">배송완료</button>
                    <button type="button" class="btn red-line" v-if="!isPartner" @click="goCancelApply('prepDeliv')">주문취소</button>
                </div>
            </div>
            <div class=" mt0" :class="{'scroll-x': !isPartner}">
                <table cellpadding="0" cellspacing="0" class="data-tb align-c" :style="!isPartner? 'width: 2200px;':''">
                    <caption>주문 목록</caption>
                    <colgroup v-if="!isPartner">
                        <col width="1.5%" /><!-- checkbox -->
                        <col width="3.5%" /><!-- 판매구분 -->
                        <col width="4.5%" /><!-- 파트너사 -->
                        <col width="5%" /><!-- 상품코드 -->
                        <col width="4.5%" /><!-- 단품코드 -->
                        <col width="3.5%" /><!-- 상품순번 -->
                        <col width="62px" /><!-- 상품이미지 -->
                        <col width="" /><!-- 상품명 -->
                        <col width="6%" /><!-- 옵션 -->
                        <col width="4%" /><!-- 주문수량 -->
                        <col width="4.5%" /><!-- 판매단가 -->
                        <col width="4.5%" /><!-- 판매금액 -->
                        <col width="4%" /><!-- 배송유형 -->
                        <col width="4%" /><!-- 배송조건 -->
                        <col width="3%" /><!-- 배송비 -->
                        <col width="400px" /><!-- 택배사/송장번호등록 -->
                        <col width="4%" /><!-- 배송수량 -->
                        <col width="3.5%" /><!-- 주문상태 -->
                        <col width="4%" /><!-- 클레임상태 -->
                        <col width="3.5%" /><!-- 추가상품여부 -->
                    </colgroup>
                    <colgroup v-else>
                        <col width="1.5%" /><!-- checkbox -->
                        <col width="6%" /><!-- 상품코드 -->
                        <col width="4%" /><!-- 단품코드 -->
                        <col width="4%" /><!-- 상품순번 -->
                        <col width="62px" /><!-- 상품이미지 -->
                        <col width="" /><!-- 상품명 -->
                        <col width="6.5%" /><!-- 옵션 -->
                        <col width="4%" /><!-- 주문수량 -->
                        <col width="5%" /><!-- 판매단가 -->
                        <col width="5%" /><!-- 판매금액 -->
                        <col width="4%" /><!-- 배송유형 -->
                        <col width="4%" /><!-- 배송조건 -->
                        <col width="4.5%" /><!-- 배송비 -->
                        <col width="360px" /><!-- 택배사/송장번호/직배송안내문구 등록 -->
                        <col width="4%" /><!-- 배송수량 -->
                        <col width="5%" /><!-- 주문상태 -->
                        <col width="5%" /><!-- 클레임상태 -->
                    </colgroup>
                    <thead>
                        <tr>
                            <th><input type="checkbox" id="chkall" v-model="prepDeliv.isallchk" @change="checkAllList('prepDeliv', $event.target.checked)"/></th>
                            <th v-if="!isPartner">판매구분</th>
                            <th v-if="!isPartner">파트너사</th>
                            <th>상품코드</th>
                            <th>단품코드</th>
                            <th>상품순번</th>
                            <th colspan="2">상품명</th>
                            <th>옵션</th>
                            <th>주문수량</th>
                            <th>판매단가</th>
                            <th>판매금액</th>
                            <th>배송유형</th>
                            <th>배송조건</th>
                            <th>배송비</th>
                            <th>택배사/송장번호/직배송안내문구 등록</th>
                            <th>배송수량</th>
                            <th>주문상태</th>
                            <th>클레임상태</th>
                            <th v-if="!isPartner">추가상품여부</th>
                        </tr>
                    </thead>
                    <tbody v-if="prepDeliv.list.length > 0">
                        <tr v-for="(item, index) in prepDeliv.list" :key="index">
                            <td><input type="checkbox" :id="item.orgdelividx" v-model="prepDeliv.checkedList" :value="item.orgdelividx" 
                                :disabled="(item.istracking!=='F' && $util.isNull(item.trckerrmsg)) || item.orgdelivsort>1" @change="checkList('prepDeliv')"/></td>
                            <td v-if="!isPartner">{{ item.ispbgoodsname }}</td>
                            <td v-if="!isPartner">{{ item.dealername }}</td>
                            <td>{{ item.goodscode }}</td>
                            <td>{{ item.optioncode }}</td>
                            <td>{{ item.goodsturn }}</td>
                            <td>
                                <div class="img-thumb size60 link" @click="goFrontGoodsDetail(item.goodscode)" :class="{'no-image': $util.isNull(item.fullpath)}">
                                    <img :src="item.fullpath" v-if="!$util.isNull(item.fullpath)">
                                </div>
                            </td>
                            <td class="left no-left">
                                <a href="javascript:void(0);" class="link" @click="goGoodsDetail(item.goodsno)">{{ item.goodsname }}</a>
                            </td>
                            <td style="white-space: pre-wrap">{{ item.optionconts }}</td>
                            <td>{{ $util.maskComma(item.ordcnt) }}</td>
                            <td class="right">{{ $util.maskComma(item.price) }}</td>
                            <td class="right">{{ $util.maskComma(item.totprice) }}</td>
                            <td>{{ item.iscombdelivname }}</td>
                            <td>{{ item.delivfaretypename }}</td>
                            <td class="right">{{ $util.maskComma(item.delivamt) }}</td>
                            <td v-if="item.ispbgoods==='T'">
                                {{ !$util.isNull(item.logistypename)? '택배사: ' + item.logistypename : '' }}{{ !$util.isNull(item.invoiceno)? ', 운송장번호: ' + item.invoiceno : '' }}
                            </td>
                            <td v-if="item.ispbgoods==='F' && item.delivtype === $store.getters['ADMIN'].DELIV_PARCEL">
                                <div class="dpb">
                                    <select v-model="item.logistype" :disabled="item.istracking==='T' && $util.isNull(item.trckerrmsg)">
                                        <option v-for="logistype in commonCode.logistype" :key="logistype.cmcode" :value="logistype.cmcode">{{ logistype.codename }}</option>
                                    </select>
                                    <input type="text" class="short ml3" v-model="item.invoiceno" maxlength="50" oninput="this.value = this.value.replace(/(^0|[^0-9])/gi, '');" :disabled="item.istracking==='T' && $util.isNull(item.trckerrmsg)"/>
                                    <button type="button" class="add ml3" @click="goAddInvoice(item)" v-if="item.istracking==='F' && item.orgdelivsort===1 && isWrite"></button>
                                    <button type="button" class="del ml3" @click="removeInvoice(item)" v-if="item.istracking==='F' && item.orgdelivsort>1 && isWrite"></button>
                                    <button type="button" class="btn blue ml3" @click="saveInvoice(item)" v-if="$util.isNull(item.trckerrmsg) && isWrite" :disabled="item.istracking==='T'|| item.orgdelivsort>1">등록</button>
                                    <button type="button" class="btn blue ml3" @click="saveInvoice(item)" v-if="!$util.isNull(item.trckerrmsg) && isWrite">재등록</button>
                                    <button type="button" class="btn blue ml3" @click="showTrckErrMsg(item.trckerrmsg)" v-if="!$util.isNull(item.trckerrmsg)">사유</button>
                                </div>
                            </td>
                            <td v-if="item.ispbgoods==='F' && item.delivtype === $store.getters['ADMIN'].DELIV_DIRECT">
                                <input type="text" class="ml3" style="width: 170px;" v-model="item.dirdelivmsg" maxlength="300"/>
                                <button type="button" class="btn blue ml3" @click="saveDirDelivMsg(item)">저장</button>
                                <button type="button" class="btn blue ml3" @click="fnChangeOrdStatus('prepDeliv', $store.getters['ADMIN'].ORDER_IN_DELIVERY, item)" v-if="isWrite">배송중처리</button>
                                <button type="button" class="btn blue ml3" @click="fnChangeOrdStatus('prepDeliv', $store.getters['ADMIN'].ORDER_COMPLETE_DELIV, item)" v-if="isWrite">배송완료</button>
                            </td>
                            <td>{{ $util.maskComma(item.delivcnt) }}</td>
                            <td><a href="javascript:void(0);" class="link" @click="goOrderHistory(item.orgdelividx)">{{ item.ordstatusname }}</a></td>
                            <td><a href="javascript:void(0);" class="link" @click="goClaimHistory(item)">{{ item.clmstatusname }}</a></td>
                            <td v-if="!isPartner">{{ item.isaddgoods }}</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <div v-if="!$util.isNull(inDeliv.list) && inDeliv.list.length > 0"><!-- 주문내역 : 배송중 -->
            <div class="clearfix">
                <div class="bar-title small fl">배송중</div>
                <div class="btn-group fr" v-if="isWrite">
                    <button v-if="!isPartner" type="button" class="btn blue-line" @click="fnChangeOrdStatus('inDeliv', $store.getters['ADMIN'].ORDER_PREPARING_DELIV)">배송준비중</button>
                    <button type="button" class="btn blue-line" @click="checkValidCompDeliv('inDeliv', $store.getters['ADMIN'].ORDER_COMPLETE_DELIV)">배송완료</button>
                </div>
            </div>
            <div class=" mt0" :class="{'scroll-x': !isPartner}">
                <table cellpadding="0" cellspacing="0" class="data-tb align-c" :style="!isPartner? 'width: 2100px;':''">
                    <caption>주문 목록</caption>
                    <colgroup v-if="!isPartner">
                        <col width="1.5%" /><!-- checkbox -->
                        <col width="4%" /><!-- 판매구분 -->
                        <col width="5%" /><!-- 파트너사 -->
                        <col width="4.5%" /><!-- 상품코드 -->
                        <col width="4%" /><!-- 단품코드 -->
                        <col width="4%" /><!-- 상품순번 -->
                        <col width="62px" /><!-- 상품이미지 -->
                        <col width="" /><!-- 상품명 -->
                        <col width="6%" /><!-- 옵션 -->
                        <col width="4%" /><!-- 주문수량 -->
                        <col width="5%" /><!-- 판매단가 -->
                        <col width="5%" /><!-- 판매금액 -->
                        <col width="4.5%" /><!-- 배송유형 -->
                        <col width="4.5%" /><!-- 배송조건 -->
                        <col width="3.5%" /><!-- 배송비 -->
                        <col width="5%" /><!-- 택배사 -->
                        <col width="6%" /><!-- 송장번호 -->
                        <col width="6.5%" /><!-- 직배송 안내문구 -->
                        <col width="4.5%" /><!-- 배송수량 -->
                        <col width="4%" /><!-- 주문상태 -->
                        <col width="4%" /><!-- 클레임상태 -->
                        <col width="4%" /><!-- 추가상품여부 -->
                    </colgroup>
                    <colgroup v-else>
                        <col width="1.5%" /><!-- checkbox -->
                        <col width="6.5%" /><!-- 상품코드 -->
                        <col width="4.5%" /><!-- 단품코드 -->
                        <col width="4.5%" /><!-- 상품순번 -->
                        <col width="62px" /><!-- 상품이미지 -->
                        <col width="" /><!-- 상품명 -->
                        <col width="8%" /><!-- 옵션 -->
                        <col width="4%" /><!-- 주문수량 -->
                        <col width="5.5%" /><!-- 판매단가 -->
                        <col width="5.5%" /><!-- 판매금액 -->
                        <col width="4.5%" /><!-- 배송유형 -->
                        <col width="4.5%" /><!-- 배송조건 -->
                        <col width="5%" /><!-- 배송비 -->
                        <col width="5.5%" /><!-- 택배사 -->
                        <col width="8%" /><!-- 송장번호 -->
                        <col width="8.5%" /><!-- 직배송 안내문구 -->
                        <col width="5%" /><!-- 배송수량 -->
                        <col width="5%" /><!-- 주문상태 -->
                        <col width="5%" /><!-- 클레임상태 -->
                    </colgroup>
                    <thead>
                        <tr>
                            <th><input type="checkbox" id="chkall" v-model="inDeliv.isallchk" @change="checkAllList('inDeliv', $event.target.checked)"/></th>
                            <th v-if="!isPartner">판매구분</th>
                            <th v-if="!isPartner">파트너사</th>
                            <th>상품코드</th>
                            <th>단품코드</th>
                            <th>상품순번</th>
                            <th colspan="2">상품명</th>
                            <th>옵션</th>
                            <th>주문수량</th>
                            <th>판매단가</th>
                            <th>판매금액</th>
                            <th>배송유형</th>
                            <th>배송조건</th>
                            <th>배송비</th>
                            <th>택배사</th>
                            <th>송장번호</th>
                            <th>직배송 안내문구</th>
                            <th>배송수량</th>
                            <th>주문상태</th>
                            <th>클레임상태</th>
                            <th v-if="!isPartner">추가상품여부</th>
                        </tr>
                    </thead>
                    <tbody v-if="inDeliv.list.length > 0">
                        <tr v-for="(item, index) in inDeliv.list" :key="index">
                            <td><input type="checkbox" :id="item.orgdelividx" v-model="inDeliv.checkedList" :disabled="item.ispbgoods==='T'" :value="item.orgdelividx" @change="checkList('inDeliv')"/></td>
                            <td v-if="!isPartner">{{ item.ispbgoodsname }}</td>
                            <td v-if="!isPartner">{{ item.dealername }}</td>
                            <td>{{ item.goodscode }}</td>
                            <td>{{ item.optioncode }}</td>
                            <td>{{ item.goodsturn }}</td>
                            <td>
                                <div class="img-thumb size60 link" @click="goFrontGoodsDetail(item.goodscode)" :class="{'no-image': $util.isNull(item.fullpath)}">
                                    <img :src="item.fullpath" v-if="!$util.isNull(item.fullpath)">
                                </div>
                            </td>
                            <td class="left no-left">
                                <a href="javascript:void(0);" class="link" @click="goGoodsDetail(item.goodsno)">{{ item.goodsname }}</a>
                            </td>
                            <td style="white-space: pre-wrap">{{ item.optionconts }}</td>
                            <td>{{ $util.maskComma(item.ordcnt) }}</td>
                            <td class="right">{{ $util.maskComma(item.price) }}</td>
                            <td class="right">{{ $util.maskComma(item.totprice) }}</td>
                            <td>{{ item.iscombdelivname }}</td>
                            <td>{{ item.delivfaretypename }}</td>
                            <td class="right">{{ $util.maskComma(item.delivamt) }}</td>
                            <td>{{ item.logistypename }}</td>
                            <td v-if="item.delivtype===$store.getters['ADMIN'].DELIV_PARCEL">
                                <a href="javascript:void(0);" class="link" @click="goDelivTracking(item.logistype, item.invoiceno)">{{ item.invoiceno }}</a>
                            </td>
                            <td v-if="item.delivtype===$store.getters['ADMIN'].DELIV_DIRECT">
                                <!-- <button type="button" class="btn blue ml3" @click="goDirectDelivTracking(item)">배송조회</button> -->
                            </td>
                            <td>{{ item.dirdelivmsg }}</td>
                            <td>{{ $util.maskComma(item.delivcnt) }}</td>
                            <td><a href="javascript:void(0);" class="link" @click="goOrderHistory(item.orgdelividx)">{{ item.ordstatusname }}</a></td>
                            <td><a href="javascript:void(0);" class="link" @click="goClaimHistory(item)">{{ item.clmstatusname }}</a></td>
                            <td v-if="!isPartner">{{ item.isaddgoods }}</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <div v-if="!$util.isNull(completeDeliv.list) && completeDeliv.list.length > 0"><!-- 주문내역 : 배송완료 -->
            <div class="clearfix">
                <div class="bar-title small fl">배송완료(구매확정)</div>
                <div class="btn-group fr" v-if="isWrite && !isPartner">
                    <button type="button" class="btn blue-line" @click="fnChangeOrdStatus('completeDeliv', $store.getters['ADMIN'].ORDER_PURCH_CONFIRM)">구매확정처리</button>
                    <button type="button" class="btn red-line" @click="goExchangeApply('completeDeliv')">교환</button>
                    <button type="button" class="btn red-line" @click="goReturnApply('completeDeliv')">반품</button>
                </div>
            </div>
            <div class=" mt0" :class="{'scroll-x': !isPartner}">
                <table cellpadding="0" cellspacing="0" class="data-tb align-c" :style="!isPartner? 'width: 2100px;':''">
                    <caption>주문 목록</caption>
                    <colgroup v-if="!isPartner">
                        <col width="1.5%" /><!-- checkbox -->
                        <col width="4%" /><!-- 판매구분 -->
                        <col width="5%" /><!-- 파트너사 -->
                        <col width="5%" /><!-- 상품코드 -->
                        <col width="3.5%" /><!-- 단품코드 -->
                        <col width="3.5%" /><!-- 상품순번 -->
                        <col width="62px" /><!-- 상품이미지 -->
                        <col width="" /><!-- 상품명 -->
                        <col width="7%" /><!-- 옵션 -->
                        <col width="3.5%" /><!-- 주문수량 -->
                        <col width="5%" /><!-- 판매단가 -->
                        <col width="5%" /><!-- 판매금액 -->
                        <col width="4%" /><!-- 배송유형 -->
                        <col width="4%" /><!-- 배송조건 -->
                        <col width="3.5%" /><!-- 배송비 -->
                        <col width="5%" /><!-- 택배사 -->
                        <col width="6%" /><!-- 송장번호 -->
                        <col width="6.5%" /><!-- 직배송 안내문구 -->
                        <col width="3.5%" /><!-- 배송수량 -->
                        <col width="4.5%" /><!-- 구매확정 -->
                        <col width="4%" /><!-- 주문상태 -->
                        <col width="3.5%" /><!-- 클레임상태 -->
                        <col width="4%" /><!-- 추가상품여부 -->
                    </colgroup>
                    <colgroup v-else>
                        <col width="6.5%" /><!-- 상품코드 -->
                        <col width="4.5%" /><!-- 단품코드 -->
                        <col width="4.5%" /><!-- 상품순번 -->
                        <col width="62px" /><!-- 상품이미지 -->
                        <col width="" /><!-- 상품명 -->
                        <col width="8%" /><!-- 옵션 -->
                        <col width="4%" /><!-- 주문수량 -->
                        <col width="5.5%" /><!-- 판매단가 -->
                        <col width="5.5%" /><!-- 판매금액 -->
                        <col width="4.5%" /><!-- 배송유형 -->
                        <col width="4.5%" /><!-- 배송조건 -->
                        <col width="5%" /><!-- 배송비 -->
                        <col width="5.5%" /><!-- 택배사 -->
                        <col width="8%" /><!-- 송장번호 -->
                        <col width="8.5%" /><!-- 직배송 안내문구 -->
                        <col width="4%" /><!-- 배송수량 -->
                        <col width="5%" /><!-- 주문상태 -->
                        <col width="5%" /><!-- 클레임상태 -->
                    </colgroup>
                    <thead>
                        <tr>
                            <th v-if="!isPartner"><input type="checkbox" id="chkall" v-model="completeDeliv.isallchk" @change="checkAllList('completeDeliv', $event.target.checked)"/></th>
                            <th v-if="!isPartner">판매구분</th>
                            <th v-if="!isPartner">파트너사</th>
                            <th>상품코드</th>
                            <th>단품코드</th>
                            <th>상품순번</th>
                            <th colspan="2">상품명</th>
                            <th>옵션</th>
                            <th>주문수량</th>
                            <th>판매단가</th>
                            <th>판매금액</th>
                            <th>배송유형</th>
                            <th>배송조건</th>
                            <th>배송비</th>
                            <th>택배사</th>
                            <th>송장번호</th>
                            <th>직배송 안내문구</th>
                            <th>배송수량</th>
                            <th v-if="!isPartner">구매확정</th>
                            <th>주문상태</th>
                            <th>클레임상태</th>
                            <th v-if="!isPartner">추가상품여부</th>
                        </tr>
                    </thead>
                    <tbody v-if="completeDeliv.list.length > 0">
                        <tr v-for="(item, index) in completeDeliv.list" :key="index">
                            <td v-if="!isPartner ">
                                <input type="checkbox" :id="item.orgdelividx+'_'+item.clmgdidx" v-model="completeDeliv.checkedList" :value="item.orgdelividx+'_'+item.clmgdidx" @change="checkList('completeDeliv')"/>
                            </td>
                            <td v-if="!isPartner">{{ item.ispbgoodsname }}</td>
                            <td v-if="!isPartner">{{ item.dealername }}</td>
                            <td>{{ item.goodscode }}</td>
                            <td>{{ item.optioncode }}</td>
                            <td>{{ item.goodsturn }}</td>
                            <td>
                                <div class="img-thumb size60 link" @click="goFrontGoodsDetail(item.goodscode)" :class="{'no-image': $util.isNull(item.fullpath)}">
                                    <img :src="item.fullpath" v-if="!$util.isNull(item.fullpath)">
                                </div>
                            </td>
                            <td class="left no-left">
                                <a href="javascript:void(0);" class="link" @click="goGoodsDetail(item.goodsno)">{{ item.goodsname }}</a>
                            </td>
                            <td style="white-space: pre-wrap">{{ item.optionconts }}</td>
                            <td>{{ $util.maskComma(item.ordcnt) }}</td>
                            <td class="right">{{ $util.maskComma(item.price) }}</td>
                            <td class="right">{{ $util.maskComma(item.totprice) }}</td>
                            <td>{{ item.iscombdelivname }}</td>
                            <td>{{ item.delivfaretypename }}</td>
                            <td class="right">{{ $util.maskComma(item.delivamt) }}</td>
                            <td>{{ item.logistypename }}</td>
                            <td v-if="item.delivtype===$store.getters['ADMIN'].DELIV_PARCEL">
                                <a href="javascript:void(0);" class="link" @click="goDelivTracking(item.logistype, item.invoiceno)">{{ item.invoiceno }}</a>
                            </td>
                            <td v-if="item.delivtype===$store.getters['ADMIN'].DELIV_DIRECT">
                                <!-- <button type="button" class="btn blue ml3" @click="goDirectDelivTracking(item)">배송조회</button> -->
                            </td>
                            <td>{{ item.dirdelivmsg }}</td>
                            <td>{{ $util.maskComma(item.delivcnt) }}</td>
                            <td v-if="!isPartner" style="white-space: pre-wrap">{{ item.cfmconts }}</td>
                            <td><a href="javascript:void(0);" class="link" @click="goOrderHistory(item.orgdelividx)">{{ item.ordstatusname }}</a></td>
                            <td><a href="javascript:void(0);" class="link" @click="goClaimHistory(item)">{{ item.clmstatusname }}</a></td>
                            <td v-if="!isPartner">{{ item.isaddgoods }}</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="bar-title small">사은품 정보</div>
        <table cellpadding="0" cellspacing="0" class="data-tb align-c">
            <caption>사은품 목록</caption>
            <colgroup>
                <col width="10%" /><!-- 사은품코드 -->
                <col width="62px" /><!-- 사은품이미지 -->
                <col width="20%" /><!-- 사은품명 -->
                <col width="7%" /><!-- 수량 -->
                <col width="" /><!-- 적용프로모션 명 -->
                <!-- <col width="7%" /> --><!-- 구분 -->
                <col width="12%" /><!-- 일자 -->
            </colgroup>
            <thead>
                <tr>
                    <th>사은품코드</th>
                    <th colspan="2">사은품명</th>
                    <th>수량</th>
                    <th>적용프로모션 명</th>
                    <th>일자</th>
                </tr>
            </thead>
            <tbody v-if="!$util.isNull(orderGiftList) && orderGiftList.length > 0">
                <tr v-for="(item, index) in orderGiftList" :key="'gift_'+index">
                    <td>{{ item.giftcode }}</td>
                    <td>
                        <div class="img-thumb size60" :class="{'no-image': $util.isNull(item.fullpath)}">
                            <img :src="item.fullpath" v-if="!$util.isNull(item.fullpath)">
                        </div>
                    </td>
                    <td class="left no-left"><p class="mg0">{{ item.giftname }}</p></td>
                    <td>{{ item.rcvamt }}</td>
                    <td>{{ item.promoname }}</td>
                    <td>{{ item.regdate }}</td>
                </tr>
            </tbody>
            <tbody v-else>
                <tr><td colspan="7">사은품 내역이 존재하지 않습니다.</td></tr>
            </tbody>
        </table>
        <div class="col2 pd">
            <div class="left">
                <div class="bar-title small">주문정보</div>
                <table cellpadding="0" cellspacing="0" class="gray-tb">
                    <colgroup>
                        <col width="200px">
                        <col width="150px">
                        <col width="">
                    </colgroup>
                    <tbody>
                        <tr>
                            <th>판매금액 합계</th>
                            <td colspan="2" class="right"><strong class="large-txt">{{ $util.maskComma(info.ordtotprice) }}</strong></td>
                        </tr>
                        <tr v-if="!isPartner">
                            <th>자사 배송비(+)</th>
                            <td>{{ info.isolatetype==='N'? '기본배송비':'도서산간배송비' }}</td>
                            <td class="right">{{ $util.maskComma(info.dadadelivamt) }}</td>
                        </tr>
                        <tr>
                            <th>파트너사 배송비(+)</th>
                            <td>{{ info.isolatetype==='N'? '기본배송비':'도서산간배송비' }}</td>
                            <td class="right">{{ $util.maskComma(info.ptndelivamt) }}</td>
                        </tr>
                        <tr>
                            <th>할인 내역 합계(-)</th>
                            <td colspan="2" class="right"><span class="txt-red">{{ $util.maskComma(Number(info.totsaleamt)*-1) }}</span></td>
                        </tr>
                        <tr>
                            <th v-if="!isPartner">적립금/D포인트 사용 합계(-)</th>
                            <th v-else>적립금 사용 합계(-)</th>
                            <td colspan="2" class="right"><span class="txt-red">{{ $util.maskComma(Number(info.totpointamt)*-1) }}</span></td>
                        </tr>
                        <tr>
                            <th>실 결제금액</th>
                            <td colspan="2" class="right"><strong class="large-txt">{{ $util.maskComma(info.rpaytotprice) }}</strong></td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div class="right">
                <div class="bar-title small">할인 내역</div>
                <div class="scroll-y mt0" style="max-height: 260px;">
                    <table cellpadding="0" cellspacing="0" class="gray-tb align-c">
                        <colgroup>
                            <col width="15%">
                            <col width="13%">
                            <col width="12%">
                            <col width="">
                            <col width="15%">
                        </colgroup>
                        <thead>
                            <tr>
                                <th>구분</th>
                                <th>상품코드</th>
                                <th>단품코드</th>
                                <th>내용</th>
                                <th>금액</th>
                            </tr>
                        </thead>
                        <tbody v-if="!$util.isNull(orderDiscountList) && orderDiscountList.length > 0">
                            <tr v-for="(item, index) in orderDiscountList" :key="'discount_'+index">
                                <td v-if="item.rowno === 1" :rowspan="item.rowspan">{{ item.typename }}</td>
                                <td>{{ item.goodscode }}</td>
                                <td>{{ item.optioncode }}</td>
                                <td class="left">{{ item.cpnname }}</td>
                                <td class="right"><span class="txt-red">{{ $util.maskComma(item.discountamt) }}</span></td>
                            </tr>
                        </tbody>
                        <tbody v-else>
                            <tr><td colspan="4">조회 결과가 존재하지 않습니다.</td></tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <div class="col2 pd mt10">
            <div class="left">
                <div class="bar-title small">결제정보</div>
                <table cellpadding="0" cellspacing="0" class="gray-tb">
                    <colgroup>
                        <col width="200px">
                        <col width="">
                    </colgroup>
                    <tbody>
                        <tr>
                            <th>결제일시</th>
                            <td>{{ info.paymentdate }}</td>
                        </tr>
                        <tr>
                            <th>결제수단</th>
                            <td>
                                <span>{{ info.paywaytypename }}</span>
                                <span class="left-bar" v-if="info.paywaytype===$store.getters['ADMIN'].PAY_CREDIT_CARD">{{ info.cardcompany }}</span>
                                <span class="left-bar" v-if="info.paywaytype===$store.getters['ADMIN'].PAY_CREDIT_CARD">{{ info.planmonth==0||$util.isNull(info.planmonth)? '일시불':info.planmonth+'개월' }}</span>
                                <span class="left-bar" v-if="info.paywaytype===$store.getters['ADMIN'].PAY_VIRTURE_ACCOUNT">{{ info.virbank }}</span>
                                <span class="left-bar" v-if="info.paywaytype===$store.getters['ADMIN'].PAY_VIRTURE_ACCOUNT">{{ info.accntnumber }}</span>
                                <span class="left-bar" v-if="info.paywaytype===$store.getters['ADMIN'].PAY_MOBILE">{{ info.mopcarrier }}</span>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div class="right">
                <div class="bar-title small" v-if="!isPartner">적립금/D포인트 사용 내역</div>
                <div class="bar-title small" v-else>적립금 사용 내역</div>
                <table cellpadding="0" cellspacing="0" class="gray-tb align-c">
                    <colgroup>
                        <col width="200px">
                        <col width="">
                    </colgroup>
                    <thead>
                        <tr>
                            <th>구분</th>
                            <th>금액</th>
                        </tr>
                    </thead>
                    <tbody v-if="!isPartner">
                        <tr>
                            <td>임직원적립금</td>
                            <td class="right"><span class="txt-red">{{ $util.maskComma(info.empreservetotamt) }}</span></td>
                        </tr>
                        <tr>
                            <td>적립금</td>
                            <td class="right"><span class="txt-red">{{ $util.maskComma(info.reservetotamt) }}</span></td>
                        </tr>
                        <tr>
                            <td>D포인트</td>
                            <td class="right"><span class="txt-red">{{ $util.maskComma(info.epointtotamt) }}</span></td>
                        </tr>
                    </tbody>
                    <tbody v-else>
                      <tr>
                        <td>적립금</td>
                        <td class="right"><span class="txt-red">{{ $util.maskComma(info.reservetotamt + info.empreservetotamt + info.epointtotamt) }}</span></td>
                      </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="col2 pd mt10">
            <div class="left">
                <div class="bar-title small">주문자 정보</div>
                <table cellpadding="0" cellspacing="0" class="gray-tb">
                    <colgroup>
                        <col width="120px">
                        <col width="155px">
                        <col width="120px">
                        <col width="">
                    </colgroup>
                    <tbody v-if="!isPartner">
                        <tr>
                            <th>주문자명</th>
                            <td>{{ info.ordname }}</td>
                            <th>주문자ID</th>
                            <td>{{ info.ordid }}</td>
                        </tr>
                        <tr>
                            <th>주문자 연락처</th>
                            <td colspan="3">{{ $util.maskTel(info.ordtel) }}</td>
                        </tr>
                        <tr>
                            <th>주문자 Email</th>
                            <td colspan="3">{{ info.ordemail }}</td>
                        </tr>
                        <tr>
                            <th>주문자 주소</th>
                            <td colspan="3">{{ !$util.isNull(info.ordpost)? '['+ info.ordpost+']':'' }} {{ info.ordaddr }}{{ $util.isNull(info.ordaddrdetail)? '':' '+info.ordaddrdetail }}</td>
                        </tr>
                    </tbody>
                    <tbody v-else>
                        <tr>
                            <th>주문자명</th>
                            <td colspan="3">{{ info.ordname }}</td>
                        </tr>
                        <tr>
                            <th>주문자 연락처</th>
                            <td colspan="3">{{ $util.maskTel(info.ordtel) }}</td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div class="right">
                <div class="clearfix">
                    <div class="bar-title small fl mb0">배송지 정보</div>
                    <div class="btn-group fr" v-if="isWrite">
                        <button type="button" class="btn small blue-line" v-if="ischgrcvinfo" @click="goChgRcvinfo(info.ordno)">배송지 정보 수정</button>
                    </div>
                </div>
                <table cellpadding="0" cellspacing="0" class="gray-tb">
                    <colgroup>
                        <col width="120px">
                        <col width="155px">
                        <col width="120px">
                        <col width="">
                    </colgroup>
                    <tbody>
                        <tr>
                            <th>수령인 명</th>
                            <td colspan="3">{{ info.rcvname }}</td>
                        </tr>
                        <tr>
                            <th>연락처1</th>
                            <td>{{ $util.maskTel(info.rcvtel1) }}</td>
                            <th>연락처2</th>
                            <td>{{ $util.maskTel(info.rcvtel2) }}</td>
                        </tr>
                        <tr>
                            <th>주소</th>
                            <td colspan="3">[{{ info.rcvpost }}] {{ info.rcvaddr }}{{ $util.isNull(info.rcvaddrdetail)? '':' '+info.rcvaddrdetail }}</td>
                        </tr>
                        <tr>
                            <th>배송요청사항</th>
                            <td colspan="3">
                                <span>{{ info.rcvreqtypename }}</span>
                                <span class="left-bar" v-if="!$util.isNull(info.rcvreqdetail)">{{ info.rcvreqdetail }}</span>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
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
                                @click="sortToggle(memo.psort)"></button>
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
        <ChangeRcvinfoPopup v-if="isShowChgRcvInfo" :activeOrdno="activeOrdno" @closePopup="closeChgRcvinfo"></ChangeRcvinfoPopup>
        <AddInvoicePopup v-if="isShowAddInvoice" :activeOrdgdidx="activeOrdgdidx" :activeOrgdelividx="activeOrgdelividx" @closePopup="closeAddInvoice"></AddInvoicePopup>
        <ApplyClaimPopup v-if="isShowApplyClaim" :claimParams="claimParams" @closePopup="closeApplyClaim"></ApplyClaimPopup>
    </div>
    <!-- /주문상세정보 - 주문내역 팝업 -->
</template>

<script>
import GoodsDetail from '@views.admin/goods/manage/GoodsDetail.vue';
import AddInvoicePopup from '@views.admin/order/popup/AddInvoicePopup.vue'
import ChangeRcvinfoPopup from '@views.admin/order/popup/ChangeRcvinfoPopup.vue'
import OrderStatusHistoryPopup from '@views.admin/order/popup/OrderStatusHistoryPopup.vue';
import ApplyClaimPopup from '@/views/admin/order/popup/ApplyClaimPopup.vue';
import ClaimStatusHistoryPopup from '@views.admin/order/popup/ClaimStatusHistoryPopup.vue';

export default {
    name: "AdminOrderInfo",
    props: {
        orderInfo: Object,
        activeOrderCode: String,
        isRead: Boolean,
        isWrite: Boolean
    },
    components: {
        GoodsDetail,
        AddInvoicePopup,
        ChangeRcvinfoPopup,
        ApplyClaimPopup
    },
    data() {
        return {
            isPartner: false,
            user: {},
            info: {},
            waitingDeposit: {   // 입금대기중
                list: []
            },
            prepGoods: {        // 상품준비중
                isallchk: false,
                checkedList: [],
                list: []
            },
            prepDeliv: {        // 배송준비중
                isallchk: false,
                checkedList: [],
                list: []
            },
            inDeliv: {          // 배송중
                isallchk: false,
                checkedList: [],
                list: []
            },
            completeDeliv: {    // 배송완료
                isallchk: false,
                checkedList: [],
                list: []
            },
            orderGiftList: [],      // 사은품목록
            orderDiscountList: [],  // 할인내역목록
            orderMemoList: [],      // 관리자메모목록
            memo: {
                inputMemo: '',          // 입력된 메모내용
                psort: 'regdate_desc'   // 메모정렬
            },
            commonCode: {
                logistype: []           // 택배사종류
            },
            ischgrcvinfo: false,        // 배송지정보수정가능여부
            activeGoodsNo : false,
            activeOrdno: '',
            activeOrdgdidx: '',
            activeOrgdelividx: '',
            claimParams: {},            // 클레임신청 파라미터
            isGoodsDetailShow: false,   // 상품상세 팝업 노출여부
            isShowAddInvoice: false,    // 송장추가 팝업 노출여부
            isShowChgRcvInfo: false,    // 배송지정보수정 팝업 노출여부
            isShowApplyClaim: false,    // 클레임신청 팝업 노출여부   
            isreload: false             // 주문상세 팝업 닫은 후 새로 조회 여부
        }
    },
    mounted() {
        this.isPartner = this.$util.isAuthorized(this.$store.getters['CONSTANTS'].PARTNER_USER);
        this.user = this.$util.getUser(this.$store.getters['CONSTANTS'].MANAGER_SESSION);
        this.getCommonCodeList();
    },
    methods: {
        // 공통코드 목록 조회
        getCommonCodeList: function() {
            let cmclassArr = ['LOGISTYPE'];
            this.$http.post('/common/code/map/list', {cmclass : cmclassArr, isloading: false})
                .then(result =>{
                    let data = result.data;
                    for (const [key] of Object.entries(data)) {
                        this.commonCode[key] = data[key];
                    }
                    // 주문정보 조회
                    this.getOrderInfo();
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 주문정보 조회
        getOrderInfo: function() {
            let params = {
                ordno: this.activeOrderCode,
                isPartner: this.isPartner,
                dealerno: this.isPartner? this.user.no : ''
            }
            this.$http.post('/admin/order/manage/detail', params)
                .then(result => {
                    this.$util.debug(result);
                    this.info = result.data.info;
                    this.waitingDeposit.list = result.data.waitingdepositlist;
                    this.prepGoods.list = result.data.prepgoodslist;
                    this.prepDeliv.list = result.data.prepdelivlist;
                    this.inDeliv.list = result.data.indelivlist;
                    this.completeDeliv.list = result.data.completedelivlist;
                    this.orderGiftList = result.data.ordergiftlist;
                    this.orderMemoList = result.data.ordermemolist;
                    this.orderDiscountList = result.data.orderdiscountlist;
                    // 배송정보수정-입금대기, 상품준비중, 배송준비중(배송추적요청 전) 상태만 수정가능
                    this.ischgrcvinfo = false;
                    let prevDelivTrckCnt = this.prepDeliv.list.filter(item => {return item.istracking==='T' && this.$util.isNull(item.trckerrmsg);}).length;
                    if (this.inDeliv.list.length === 0 && this.completeDeliv.list.length === 0 && prevDelivTrckCnt === 0) {
                        this.ischgrcvinfo = true;
                    }
                    this.waitingDeposit.isallchk = false;
                    this.prepGoods.isallchk = false;
                    this.prepDeliv.isallchk = false;
                    this.inDeliv.isallchk = false;
                    this.completeDeliv.isallchk = false;
                    this.waitingDeposit.checkedList = [];
                    this.prepGoods.checkedList = [];
                    this.prepDeliv.checkedList = [];
                    this.inDeliv.checkedList = [];
                    this.completeDeliv.checkedList = [];
                }).catch(error => {
                    this.$util.debug(error);
                })
        },
        // 일괄등록 (배송추적요청)
        saveInvoiceAll: function() {
            if (this.prepDeliv.checkedList.length === 0) {
                alert('처리할 상품을 선택해주세요.');
                return;
            }
            let targetlist = [];
            for (let i=0; i<this.prepDeliv.checkedList.length; i++) {
                let orgdelividx = this.prepDeliv.checkedList[i];
                for (let j=0; j<this.prepDeliv.list.length; j++) {
                    let item = this.prepDeliv.list[j];
                    if (orgdelividx === item.orgdelividx) {
                        targetlist.push(item);
                    }
                }
            }

            let params = {
                orderdelivList: []
            }
            for (let i=0; i<targetlist.length; i++) {
                let targetItem = targetlist[i];
                for (let j=0; j<this.prepDeliv.list.length; j++) {
                    let listItem = this.prepDeliv.list[j];
                    if (targetItem.ordgdidx === listItem.ordgdidx) {
                        if (listItem.istracking != 'F' && this.$util.isNull(listItem.trckerrmsg)) {
                            alert('이미 등록처리된 상품이 존재합니다. 확인후 다시 진행하시기 바랍니다.');
                            return;
                        }
                        if (listItem.ordcnt == 0) {
                            alert('처리할 주문수량이 존재하지 않습니다. 확인후 진행해주세요.');
                            return;
                        }
                        if(listItem.clmingcnt > 0) {
                            alert('클레임처리가 진행중인 상품은 처리가 불가합니다. 확인후 진행해주세요.');
                            return;
                        }
                        if (listItem.ispbgoods === 'T') {
                            alert('파트너사 상품만 송장등록이 가능합니다. 확인후 진행해주세요.');
                            return;
                        }
                        if (listItem.delivtype === this.$store.getters['ADMIN'].DELIV_DIRECT) {
                            alert('택배배송 상품만 송장등록이 가능합니다. 확인후 진행해주세요.');
                            return;
                        }
                        if (this.$util.isNull(listItem.invoiceno)) {
                            alert('송장입력 후 처리가 가능합니다.');
                            return;
                        }
                        listItem.frstordcnt = listItem.ordcnt;
                        params.orderdelivList.push(listItem);
                    }
                }
            }

            if (confirm('일괄등록처리 하시겠습니까?')) {
                this.$http.post('/admin/order/manage/invoice/save/all', params)
                    .then(result => {
                        this.$util.debug(result);
                        if (result.statusCode == 200) {
                            alert('처리가 완료되었습니다.');
                            this.getOrderInfo();
                            this.isreload=true;
                        }
                    })
                    .catch(error => {
                        this.$util.debug(error);
                    });
            }
        },
        // 송장정보 등록
        saveInvoice: function(obj) {
            let params = {
                orderdelivList: []
            }
            for (let i=0; i<this.prepDeliv.list.length; i++) {
                let listItem = this.prepDeliv.list[i];
                if (listItem.ordgdidx === obj.ordgdidx) {
                    if (listItem.istracking != 'F' && this.$util.isNull(listItem.trckerrmsg)) {
                        alert('이미 등록처리된 상품이 존재합니다. 확인후 다시 진행하시기 바랍니다.');
                        return;
                    }
                    if (listItem.ordcnt == 0) {
                        alert('처리할 주문수량이 존재하지 않습니다. 확인후 진행해주세요.');
                        return;
                    }
                    if(listItem.clmingcnt > 0) {
                        alert('클레임처리가 진행중인 상품은 처리가 불가합니다. 확인후 진행해주세요.');
                        return;
                    }
                    if (listItem.ispbgoods === 'T') {
                        alert('파트너사 상품만 송장등록이 가능합니다. 확인후 진행해주세요.');
                        return;
                    }
                    if (listItem.delivtype === this.$store.getters['ADMIN'].DELIV_DIRECT) {
                        alert('택배배송 상품만 송장등록이 가능합니다. 확인후 진행해주세요.');
                        return;
                    }
                    if (this.$util.isNull(listItem.invoiceno)) {
                        alert('송장입력 후 처리가 가능합니다.');
                        return;
                    }
                    listItem.frstordcnt = listItem.ordcnt;
                    params.orderdelivList.push(listItem);
                }
            }
            // 송장저장 후 배송추적요청 처리
            if (confirm('송장을 등록하시겠습니까?')) {
                this.$http.post('/admin/order/manage/invoice/save/all', params)
                    .then(result => {
                        this.$util.debug(result);
                        if (result.statusCode == 200) {
                            alert('처리가 완료되었습니다.');
                            this.getOrderInfo();
                            this.isreload=true;
                        }
                    })
                    .catch(error => {
                        this.$util.debug(error);
                    });
            }
        },
        // 송장정보 삭제
        removeInvoice: function(obj) {
            if (confirm('송장정보를 삭제하시겠습니까?')) {
                this.$http.post('/admin/order/manage/invoice/remove', obj)
                    .then(result => {
                        this.$util.debug(result);
                        if (result.statusCode == 200) {
                            alert('처리가 완료되었습니다.');
                            this.getOrderInfo();
                            this.isreload=true;
                        }
                    })
                    .catch(error => {
                        this.$util.debug(error);
                    });
            }
        },
        // 배송추적요청 실패 사유
        showTrckErrMsg: function(msg) {
            alert('송장등록에 실패사유: '+ msg + '\n송장정보 확인 후 재등록해 주시기 바랍니다.');
        },

        // 배송완료 전 체크
        checkValidCompDeliv: function(targetName, reqStatus) {
            if (this[targetName].checkedList.length === 0) {
                alert('처리할 상품을 선택해주세요.');
                return;
            }
            let params = {
                orderdelivList: [],
                ordstatus: reqStatus,
                confMsg : ''
            }

            for (let i=0; i<this[targetName].checkedList.length; i++) {
                let orgdelividx =this[targetName].checkedList[i]+"";
                for (let j=0; j<this[targetName].list.length; j++) {
                    let item = this[targetName].list[j];
                    if (orgdelividx == item.orgdelividx) {
                        if (item.ispbgoods === 'T') {
                            alert('파트너사 상품만 처리가 가능합니다. 확인후 진행해주세요.');
                            return;
                        }
                        if (targetName == 'inDeliv' && reqStatus === this.$store.getters['ADMIN'].ORDER_COMPLETE_DELIV && item.ordstatus != this.$store.getters['ADMIN'].ORDER_IN_DELIVERY) {
                            alert('배송중인 상품만 배송완료로 처리가 가능합니다. 확인후 진행해주세요.');
                            return;
                        }
                        params.orderdelivList.push(item);
                    }
                }
            }
            
            params.isloading = false;
            this.$http.post('/admin/order/manage/compdeliv/valid', params).then(result => {
                if (result.data.resultcnt > 0) {
                    params.confMsg = '동일한 송장정보를 가진 상품이 존재합니다. 일괄 배송완료 처리하시겠습니까?';
                } else {
                    params.confMsg = '배송완료로 처리 하시겠습니까?';
                }
                this.fnCompleteDeliv(params);
            }).catch(error => {
                this.$util.debug(error);
            });
        },
        // 배송완료처리
        fnCompleteDeliv: function(params) {
            if (confirm(params.confMsg)) {
                if (this.isPartner) {
                    params.dealerno = this.user.no;
                }
                this.$http.post('/admin/order/manage/status/update', params).then(result => {
                    this.$util.debug(result);
                    if (result.statusCode == 200) {
                        alert('처리가 완료되었습니다.');
                        this.getOrderInfo();
                        this.isreload=true;
                    }
                })
                .catch(error => {
                    this.$util.debug(error);
                });
            }
        },
        // 주문상태 변경
        fnChangeOrdStatus: function(targetName, reqStatus, obj) {
            if (this.$util.isNull(obj) && this[targetName].checkedList.length === 0) {
                alert('처리할 상품을 선택해주세요.');
                return;
            }
            
            let params = {
                isrollback: 'F',
                orderdelivList: [],
                ordstatus: reqStatus
            }

            if (this.$util.isNull(obj)) {
                for (let i=0; i<this[targetName].checkedList.length; i++) {
                    let orgdelividx =this[targetName].checkedList[i]+"";
                    if (orgdelividx.includes('_')) {
                        let keys = this[targetName].checkedList[i].split('_');
                        orgdelividx = keys[0];
                    }
                    for (let j=0; j<this[targetName].list.length; j++) {
                        let item = this[targetName].list[j];
                        if (orgdelividx == item.orgdelividx) {
                            if (item.ispbgoods === 'T') {
                                alert('파트너사 상품만 처리가 가능합니다. 확인후 진행해주세요.');
                                return;
                            }
                            if (
                                reqStatus === this.$store.getters['ADMIN'].ORDER_PREPARING_DELIV && item.ordstatus != this.$store.getters['ADMIN'].ORDER_PREPARING_GOODS &&
                                item.ordstatus != this.$store.getters['ADMIN'].ORDER_IN_DELIVERY
                            ) {
                                alert('상품준비중인 상품만 배송준비중 처리가 가능합니다. 확인후 진행해주세요.');
                                return;
                            }
                            if(item.clmingcnt > 0) {
                                alert('클레임처리가 진행중인 상품은 처리가 불가합니다. 확인후 진행해주세요.');
                                return;
                            }
                            if (targetName == 'prepGoods' && reqStatus === this.$store.getters['ADMIN'].ORDER_PREPARING_DELIV && item.ordcnt == 0) {
                                alert('처리할 주문수량이 존재하지 않습니다. 확인후 진행해주세요.');
                                return;
                            }
                            if (reqStatus === this.$store.getters['ADMIN'].ORDER_PREPARING_GOODS && item.ordstatus != this.$store.getters['ADMIN'].ORDER_PREPARING_DELIV) {
                                alert('배송준비중인 상품만 상품준비중으로 처리가 가능합니다. 확인후 진행해주세요.');
                                return;
                            }
                            if(reqStatus === this.$store.getters['ADMIN'].ORDER_PREPARING_GOODS && item.istracking === 'T' && this.$util.isNull(item.trckerrmsg)) {
                                alert('배송추적요청된 상품은 처리가 불가합니다. 확인후 진행해주세요');
                                return;
                            }
                            if (targetName == 'prepDeliv' && reqStatus === this.$store.getters['ADMIN'].ORDER_IN_DELIVERY && item.ordcnt == 0) {
                                alert('처리할 주문수량이 존재하지 않습니다. 확인후 진행해주세요.');
                                return;
                            }
                            if (targetName == 'prepDeliv' && reqStatus === this.$store.getters['ADMIN'].ORDER_IN_DELIVERY && item.delivtype != this.$store.getters['ADMIN'].DELIV_DIRECT) {
                                alert('직배송인 상품만 배송중으로 처리가 가능합니다. 확인후 진행해주세요.');
                                return;
                            }
                            if (targetName == 'prepDeliv' && reqStatus === this.$store.getters['ADMIN'].ORDER_IN_DELIVERY && item.ordstatus != this.$store.getters['ADMIN'].ORDER_PREPARING_DELIV) {
                                alert('배송준비중인 상품만 배송중으로 처리가 가능합니다. 확인후 진행해주세요.');
                                return;
                            }
                            if (targetName == 'prepDeliv' && reqStatus === this.$store.getters['ADMIN'].ORDER_COMPLETE_DELIV && item.delivtype != this.$store.getters['ADMIN'].DELIV_DIRECT) {
                                alert('직배송인 상품만 배송완료로 처리가 가능합니다. 확인후 진행해주세요.');
                                return;
                            }
                            if (targetName == 'prepDeliv' && reqStatus === this.$store.getters['ADMIN'].ORDER_COMPLETE_DELIV && item.ordstatus != this.$store.getters['ADMIN'].ORDER_PREPARING_DELIV) {
                                alert('배송준비중인 상품만 배송완료로 처리가 가능합니다. 확인후 진행해주세요.');
                                return;
                            }
                            if (targetName == 'inDeliv' && reqStatus === this.$store.getters['ADMIN'].ORDER_COMPLETE_DELIV && item.ordstatus != this.$store.getters['ADMIN'].ORDER_IN_DELIVERY) {
                                alert('배송중인 상품만 배송완료로 처리가 가능합니다. 확인후 진행해주세요.');
                                return;
                            }
                            if (reqStatus === this.$store.getters['ADMIN'].ORDER_PURCH_CONFIRM && item.ordstatus === this.$store.getters['ADMIN'].ORDER_PURCH_CONFIRM) {
                                alert('이미 구매확정 처리된 내역이 존재합니다. 확인후 진행해주세요.');
                                return;
                            }
                            if (reqStatus === this.$store.getters['ADMIN'].ORDER_PURCH_CONFIRM && item.ordstatus != this.$store.getters['ADMIN'].ORDER_COMPLETE_DELIV) {
                                alert('배송완료된 상품만 구매확정 처리가 가능합니다. 확인후 진행해주세요.');
                                return;
                            }
                            params.orderdelivList.push(item);
                        }
                    }
                }
            } else {
                if (obj.ispbgoods === 'T') {
                    alert('파트너사 상품만 처리가 가능합니다. 확인후 진행해주세요.');
                    return;
                }
                if (reqStatus === this.$store.getters['ADMIN'].ORDER_PREPARING_DELIV && obj.ordstatus != this.$store.getters['ADMIN'].ORDER_PREPARING_GOODS) {
                    alert('상품준비중인 상품만 배송준비중 처리가 가능합니다. 확인후 진행해주세요.');
                    return;
                }
                if (reqStatus === this.$store.getters['ADMIN'].ORDER_PREPARING_GOODS && obj.ordstatus != this.$store.getters['ADMIN'].ORDER_PREPARING_DELIV) {
                    alert('배송준비중인 상품만 상품준비중으로 처리가 가능합니다. 확인후 진행해주세요.');
                    return;
                }
                if(obj.clmingcnt > 0) {
                    alert('클레임처리가 진행중인 상품은 처리가 불가합니다. 확인후 진행해주세요.');
                    return;
                }
                if(obj.rtnproccnt > 0) {
                    alert('반품처리가 진행중인 상품은 처리가 불가합니다. 확인후 진행해주세요.');
                    return;
                }
                if(obj.excproccnt > 0) {
                    alert('교환처리가 진행중인 상품은 처리가 불가합니다. 확인후 진행해주세요.');
                    return;
                }
                if (targetName == 'prepGoods' && reqStatus === this.$store.getters['ADMIN'].ORDER_PREPARING_DELIV && obj.ordcnt == 0) {
                    alert('처리할 주문수량이 존재하지 않습니다. 확인후 진행해주세요.');
                    return;
                }
                if (targetName == 'prepDeliv' && reqStatus === this.$store.getters['ADMIN'].ORDER_IN_DELIVERY && obj.ordcnt == 0) {
                    alert('처리할 주문수량이 존재하지 않습니다. 확인후 진행해주세요.');
                    return;
                }
                if (targetName == 'prepDeliv' && reqStatus === this.$store.getters['ADMIN'].ORDER_IN_DELIVERY && obj.delivtype != this.$store.getters['ADMIN'].DELIV_DIRECT) {
                    alert('직배송인 상품만 배송중으로 처리가 가능합니다. 확인후 진행해주세요.');
                    return;
                }
                if (targetName == 'prepDeliv' && reqStatus === this.$store.getters['ADMIN'].ORDER_IN_DELIVERY && obj.ordstatus != this.$store.getters['ADMIN'].ORDER_PREPARING_DELIV) {
                    alert('배송준비중인 상품만 배송중으로 처리가 가능합니다. 확인후 진행해주세요.');
                    return;
                }
                if (targetName == 'prepDeliv' && reqStatus === this.$store.getters['ADMIN'].ORDER_COMPLETE_DELIV && obj.delivtype != this.$store.getters['ADMIN'].DELIV_DIRECT) {
                    alert('직배송인 상품만 배송완료로 처리가 가능합니다. 확인후 진행해주세요.');
                    return;
                }
                params.orderdelivList.push(obj);
            }
            
            let cmfMsg = '';
            if (reqStatus == this.$store.getters['ADMIN'].ORDER_PREPARING_DELIV) {
                cmfMsg = '배송준비중';
            } else if (reqStatus == this.$store.getters['ADMIN'].ORDER_PREPARING_GOODS) {
                params.isrollback = 'T';
                cmfMsg = '상품준비중';
            } else if (reqStatus == this.$store.getters['ADMIN'].ORDER_IN_DELIVERY) {
                cmfMsg = '배송중';
            } else if (reqStatus == this.$store.getters['ADMIN'].ORDER_COMPLETE_DELIV) {
                cmfMsg = '배송완료로';
            } else if (reqStatus == this.$store.getters['ADMIN'].ORDER_PURCH_CONFIRM) {
                cmfMsg = '구매확정';
            }
            
            if (confirm(cmfMsg+ ' 처리하시겠습니까?')) {
                if (this.isPartner) {
                    params.dealerno = this.user.no;
                }
                this.$http.post('/admin/order/manage/status/update', params)
                    .then(result => {
                        this.$util.debug(result);
                        if (result.statusCode == 200) {
                            alert('처리가 완료되었습니다.');
                            this.getOrderInfo();
                            this.isreload=true;
                        }
                    })
                    .catch(error => {
                        this.$util.debug(error);
                    });
            }
        },
        // 주문취소 신청
        goCancelApply: function(targetName) {
            if (this[targetName].checkedList.length === 0) {
                alert('취소할 상품을 선택해주세요.');
                return;
            }

            let params = {
                clmtype: this.$store.getters['ADMIN'].CLM_CANCEL, 
                orgdelividxlist: this[targetName].checkedList, 
                isloading: false
            };
            this.$http.post('/admin/order/claim/check/valid', params).then(result => {
                if (result.statusCode === 200) {
                    let claimList = [];
                    for (let i=0; i<this[targetName].checkedList.length; i++) {
                        let orgdelividx = this[targetName].checkedList[i];
                        for (let j=0; j<this[targetName].list.length; j++) {
                            let item = this[targetName].list[j];
                            if (orgdelividx === item.orgdelividx) {
                                if(item.istracking === 'T' && this.$util.isNull(item.trckerrmsg)) {
                                    alert('배송추적요청된 상품은 취소가 불가합니다. 확인후 진행해주세요.');
                                    return;
                                }
                                if(item.origincnt < 1) {
                                    alert('취소 가능 수량이 없는 상품이 존재합니다. 확인후 진행해주세요.');
                                    return;
                                }
                                item.clmcnt = item.origincnt;
                                claimList.push(item);
                            }
                        }
                    }
                    // 클레임신청 팝업 오픈
                    this.goApplyClaim(this.$store.getters['ADMIN'].CLM_CANCEL, claimList);
                }
            });
        },
        // 반품신청
        goReturnApply: function(targetName) {
            if (this[targetName].checkedList.length === 0) {
                alert('반품할 상품을 선택해주세요.');
                return;
            }
            if (this[targetName].checkedList.length > 1) {
                alert('반품은 한 건씩 신청할 수 있습니다.');
                return;
            }

            let claimList = [];
            for (let i=0; i<this[targetName].checkedList.length; i++) {
                let keys = this[targetName].checkedList[i].split('_');
                let orgdelividx = keys[0];
                let clmgdidx = (this.$util.isNull(keys[1]) || keys[1]=='null')? '':keys[1];
                for (let j=0; j<this[targetName].list.length; j++) {
                    let item = this[targetName].list[j];
                    let itemClmgdidx = this.$util.isNull(item.clmgdidx)? '':item.clmgdidx;
                    if (orgdelividx == item.orgdelividx && itemClmgdidx == clmgdidx) {
                        if(item.origincnt < 1) {
                            alert('반품 가능한 수량이 없습니다. 확인후 진행해주세요.');
                            return;
                        }
                        item.clmcnt = item.origincnt;
                        claimList.push(item);
                    }
                }
            }

            // 클레임신청 팝업 오픈
            this.goApplyClaim(this.$store.getters['ADMIN'].CLM_RETURN, claimList);
        },
        // 교환신청
        goExchangeApply: function(targetName) {
            if (this[targetName].checkedList.length === 0) {
                alert('교환할 상품을 선택해주세요.');
                return;
            }
            if (this[targetName].checkedList.length > 1) {
                alert('교환은 한 건씩 신청할 수 있습니다.');
                return;
            }

            let claimList = [];
            for (let i=0; i<this[targetName].checkedList.length; i++) {
                let keys = this[targetName].checkedList[i].split('_');
                let orgdelividx = keys[0];
                let clmgdidx = (this.$util.isNull(keys[1]) || keys[1]=='null')? '':keys[1];
                for (let j=0; j<this[targetName].list.length; j++) {
                    let item = this[targetName].list[j];
                    let itemClmgdidx = this.$util.isNull(item.clmgdidx)? '':item.clmgdidx;
                    if (orgdelividx == item.orgdelividx && itemClmgdidx == clmgdidx) {
                        if(item.origincnt < 1) {
                            alert('교환 가능한 수량이 없습니다. 확인후 진행해주세요.');
                            return;
                        }
                        item.clmcnt = item.origincnt;
                        claimList.push(item);
                    }
                }
            }
            // 클레임신청 팝업 오픈
            this.goApplyClaim(this.$store.getters['ADMIN'].CLM_EXCHANGE, claimList);
        },
        // 클레임신청 팝업 열기
        goApplyClaim: function (clmtype, claimList) {
            this.claimParams = {
                orderInfo: this.orderInfo,
                orderidx: this.info.orderidx,
                clmtype : clmtype,
                claim: claimList
            }
            this.isShowApplyClaim = true;
        },
        // 클레임신청 팝업 닫기
        closeApplyClaim: function (isreload) {
            this.isShowApplyClaim = false;
            if (isreload) {
                this.getOrderInfo();
                this.$emit('setOrderInfo');
                this.$emit('setIsreload', true);
            }
        },
        // 메모 추가
        addMemo: function() {
            if (this.$util.isNull(this.memo.inputMemo.trim())){
                alert('메모 내용을 입력해주세요.');
                return;
            }
            let params = {
                orderidx: this.info.orderidx,
                memo: this.memo.inputMemo
            }
            this.$http.post('/admin/order/manage/memo/add', params)
                .then(result => {
                    this.$util.debug(result);
                    this.memo.inputMemo = '';
                    this.getOrderInfo();
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
                    this.getOrderInfo();
                })
                .catch(error => {
                    this.$util.debug(error);
                });
            
        },
        // 목록 전체체크
        checkAllList: function(targetName, value) {
            this[targetName].checkedList = [];
            if (value) {
                if (targetName === 'inDeliv') {
                    this[targetName].list.forEach(item => {
                        if (item.ispbgoods === 'F') {
                            this[targetName].checkedList.push(item.orgdelividx);
                        }
                    });
                } else if (targetName === 'prepDeliv') {
                    this[targetName].list.forEach(item => {
                        if (item.ispbgoods === 'F' && (item.istracking === 'F' || !this.$util.isNull(item.trckerrmsg))) {
                            this[targetName].checkedList.push(item.orgdelividx);
                        }
                    });
                } else if (targetName === 'completeDeliv') {
                    this[targetName].list.forEach(item => {
                        this[targetName].checkedList.push(item.orgdelividx + '_' + item.clmgdidx);
                    });
                } else {
                    this[targetName].list.forEach(item => {
                        this[targetName].checkedList.push(item.orgdelividx);
                    });
                }
            }
        },
        // 목록 개별체크
        checkList: function(targetName) {
            if (this[targetName].list.length > this[targetName].checkedList.length){
                this[targetName].isallchk = false;
            } else {
                this[targetName].isallchk = true;
            }
        },
        // 직배송메세지 저장
        saveDirDelivMsg: function(obj) {
            let params = {
                orgdelividx: obj.orgdelividx,
                dirdelivmsg: obj.dirdelivmsg
            }
            if (confirm('직배송 안내문구를 저장하시겠습니까?')) {
                this.$http.post('/admin/order/manage/save/dirdelivmsg', params).then(result => {
                    this.$util.debug(result);
                    if (result.statusCode == 200) {
                        alert('저장이 완료되었습니다.');
                        this.isreload=true;
                        this.getOrderInfo();
                    }
                })
                .catch(error => {
                    this.$util.debug(error);
                });
            }
        },
        // 정렬
        sortToggle(key){            
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
        // Front 화면으로 이동
        goFrontGoodsDetail: function(value) {
            window.open(process.env.VUE_APP_PC_GOODS_DETAIL_URL + value, "_blank");
        },
        // 송장추가 팝업 오픈
        goAddInvoice: function(obj) {
            if(obj.clmingcnt > 0) {
                alert('클레임처리가 진행중인 상품은 송장 추가가 불가합니다. 확인후 진행해주세요.');
                return;
            }
            this.isShowAddInvoice = true;
            this.activeOrdgdidx = obj.ordgdidx;
            this.activeOrgdelividx = obj.orgdelividx;
        },
        // 송장추가 팝업 닫기
        closeAddInvoice: function(isreload) {
            this.isShowAddInvoice = false;
            if (isreload) {
                this.getOrderInfo();
            }
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
        // 배송지수정 팝업 오픈
        goChgRcvinfo: function(value) {
            this.activeOrdno = value;
            this.isShowChgRcvInfo = true;
        },
        // 배송지수정 팝업 닫기
        closeChgRcvinfo: function (isreload) {
            this.isShowChgRcvInfo = false;
            if (isreload) {
                this.getOrderInfo();
            }
        },
        // 주문상태변경이력 팝업 오픈
        goOrderHistory: function(value) {
            let param = { orgdelividx: value };
            this.$eventBus.$emit('modalShow', OrderStatusHistoryPopup, param, null);
        },
        // 클래임상태변경이력 팝업 오픈
        goClaimHistory: function(obj) {
            let param = { clmgdidx: obj.clmgdidx, ordgdidx: obj.ordgdidx, clmidx: obj.clmidx };
            this.$eventBus.$emit('modalShow', ClaimStatusHistoryPopup, param, null);
        },
        // 배송추적
        goDelivTracking: function(logistype, invoiceno) {
            let params = {
                invoiceno: invoiceno,
                logistype: logistype
            }
            this.$http.post('/common/deliv/tracking', params)
                .then(result => {
                    this.$util.debug(result);
                    window.open(result.data.url, "_blank", "width=400,height=650");
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 직배송 배송조회 팝업
        goDirectDelivTracking: function(obj) {
            // TODO: 직배송 배송조회 팝업
            alert("상품명: " +  obj.goodsname + ", 직배송 배송조회 팝업 구현예정");
        }
    }
}
</script>
