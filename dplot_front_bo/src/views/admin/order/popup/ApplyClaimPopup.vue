<template>
    <!-- 클레임신청 팝업 -->
    <div id="modal-wrap" class="modal" style="display: block;">
        <div class="modal-content" style="width: 1600px;">
            <div class="pop-header">
                <h2>{{clmname}}신청</h2>
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
                            <span v-if="orderInfo.isnonmember=='F'">[{{ orderInfo.membertypename }}] [{{ orderInfo.memlvtypename }}]
                                <a href="javascript:void(0);" class="link" @click="goMemDetail(orderInfo.orduserno)">{{ orderInfo.ordname }}</a>({{ orderInfo.ordid }})
                            </span>
                            <span v-else>{{ orderInfo.ordname }}</span>
                            <span class="left-bar" v-if="orderInfo.isnonmember=='F'">{{ $util.maskTel(orderInfo.ordtel) }}</span>
                            <span class="left-bar" v-if="orderInfo.isnonmember=='F'">{{ orderInfo.ordemail }}</span>
                        </div>
                        <!-- <div class="fr txt-gray">
                            <span>결제일 : {{ orderInfo.paymentdate }}</span>
                        </div> -->
                    </div>
                </div>
                <div class="clearfix mt20">
                    <div class="bar-title small fl">{{clmname}}상품</div>
                </div>
                <table cellpadding="0" cellspacing="0" class="data-tb align-c mt0">
                    <caption>상품 목록</caption>
                    <colgroup>
                        <col width="6%" /><!-- 판매구분 -->
                        <col width="6%" /><!-- 파트너사 -->
                        <col width="6%" /><!-- 상품코드 -->
                        <col width="6%" /><!-- 단품코드 -->
                        <col width="5%" /><!-- 상품순번 -->
                        <col width="62px" /><!-- 상품이미지 -->
                        <col width="" /><!-- 상품명 -->
                        <col width="11%" /><!-- 옵션 -->
                        <col width="5%" /><!-- 주문수량 -->
                        <col width="5%" /><!-- 클레임수량 -->
                        <col width="6.5%" /><!-- 판매단가 -->
                        <col width="6.5%" /><!-- 판매금액 -->
                        <col width="5%" /><!-- 주문상태 -->
                    </colgroup>
                    <thead>
                        <tr>
                            <th>판매구분</th>
                            <th>파트너사</th>
                            <th>상품코드</th>
                            <th>단품코드</th>
                            <th>상품순번</th>
                            <th colspan="2">상품명</th>
                            <th>옵션</th>
                            <th>주문수량</th>
                            <th>{{clmname}}수량</th>
                            <th>판매단가</th>
                            <th>판매금액</th>
                            <th>주문상태</th>
                        </tr>
                    </thead>
                    <tbody v-if="!$util.isNull(clmInfo.dispitems) && clmInfo.dispitems.length > 0">
                        <tr v-for="row in clmInfo.dispitems" :key="row.ordgdidx">
                            <td>{{ row.ispbgoods==='T'? '자사':'파트너사' }}</td>
                            <td>{{ row.dealername }}</td>
                            <td>{{ row.goodscode }}</td>
                            <td>{{ row.optioncode }}</td>
                            <td>{{ row.goodsturn }}</td>
                            <td>
                                <div class="img-thumb size60 link" @click="goFrontGoodsDetail(row.goodscode)" :class="{'no-image': $util.isNull(row.fullpath)}">
                                    <img :src="row.fullpath" v-if="!$util.isNull(row.fullpath)">
                                </div>
                            </td>
                            <td class="left no-left">
                                <a href="javascript:void(0);" class="link" @click="goGoodsDetail(row.goodsno)">{{ row.goodsname }}</a>
                            </td>
                            <td style="white-space: pre-wrap">{{ row.optionname }}</td>
                            <td>{{ $util.maskComma(row.origincnt) }}</td>
                            <td>
                                <select class="short" v-model="row.clmcnt" @change="recalClaimRtnAmt">
                                    <option v-for="i in row.origincnt" :key="i" :value="i">{{ i }}</option>
                                </select>
                            </td>
                            <td class="right">{{ $util.maskComma(row.price) }}</td>
                            <td class="right">{{ $util.maskComma(row.price * row.origincnt) }}</td>
                            <td><a href="javascript:void(0);" class="link" @click="goOrderHistory(row.ordgdidx)">{{ row.ordstatusname }}</a></td>
                        </tr>
                    </tbody>
                </table>
                <table cellpadding="0" cellspacing="0" class="gray-tb">
                    <colgroup>
                        <col width="150px">
                        <col width="">
                    </colgroup>
                    <tbody>
                        <tr>
                            <th>{{clmname}}사유<i class="essential"></i></th>
                            <td>
                                <select v-model="rstype" @change="recalClaimRtnAmt">
                                    <option value="">구분선택</option>
                                    <option v-for="item in commonCode.rstype" :key="item.cmcode" :value="item.cmcode">{{ item.codename }}</option>
                                </select>
                                <input type="text" class="ml3" style="width: calc(100% - 156px);" placeholder="상세사유입력" v-model="rsdetail" maxlength="500"/>
                            </td>
                        </tr>
                        <tr v-if="clmtype !== $store.getters['ADMIN'].CLM_CANCEL">
                            <th>사진첨부</th>
                            <td>
                                <button type="button" class="btn blue-line" @click="fileAttach">파일첨부</button>
                                <input type="file" id="input-fileP" accept="image/*" ref="inputFile" @change="handleFileUpload($event.target)" hidden multiple />
                                <div v-for="(row, index) in uploadFiles" :key="index" class="dpib">
                                    <a class="file-link" @click="viewFile(row.file)">{{ row.file.name }}</a>
                                    <button type="button" class="file-del" @click="removeFile(index)"></button>
                                </div>
                            </td>
                        </tr>
                    </tbody>
                </table>
                <template v-if="clmtype === $store.getters['ADMIN'].CLM_EXCHANGE">
                    <div class="clearfix mt20">
                        <div class="bar-title small fl">교환출고상품</div>
                    </div>
                    <table cellpadding="0" cellspacing="0" class="data-tb align-c mt0">
                        <caption>주문 목록</caption>
                        <colgroup>
                            <col width="6%" /><!-- 판매구분 -->
                            <col width="6%" /><!-- 파트너사 -->
                            <col width="6%" /><!-- 상품코드 -->
                            <col width="5%" /><!-- 상품순번 -->
                            <col width="" /><!-- 상품명/옵션변경 -->
                            <col width="5%" /><!-- 교환수량 -->
                            <col width="6.5%" /><!-- 판매금액 -->
                        </colgroup>
                        <thead>
                            <tr>
                                <th>판매구분</th>
                                <th>파트너사</th>
                                <th>상품코드</th>
                                <th>상품순번</th>
                                <th>상품명/옵션변경</th>
                                <th>교환수량</th>
                                <th>교환금액</th>
                            </tr>
                        </thead>
                        <tbody v-if="!$util.isNull(excgoods)">
                            <tr>
                                <td>{{ excgoods.ispbgoods==='T'? '자사':'파트너사'}}</td>
                                <td>{{ excgoods.ispbgoods==='T'? '-':excgoods.dealername}}</td>
                                <td>{{ excgoods.goodscode }}</td>
                                <td>{{ excgoods.optioncode }}</td>
                                <td class="left">
                                    <span class="dpb">{{ excgoods.goodsname }}</span>
                                    <div class="dpb">
                                        <select style="width: 100%;" v-model="excoption">
                                            <option value="">선택</option>
                                            <option v-for="(row, index) in excgoodslist" :key="index" :value="row.optioncode">{{ row.optionname }}</option>
                                        </select>
                                    </div>
                                </td>
                                <td>{{ $util.maskComma(excgoods.exccnt) }}</td>
                                <td class="right">{{ $util.maskComma(Number(excgoods.exccnt) * Number(excgoods.price)) }}</td>
                            </tr>
                        </tbody>
                        <tbody v-else>
                            <tr><td colspan="7">교환 가능한 상품이 존재하지 않습니다.</td></tr>
                        </tbody>
                    </table>
                </template>
                <div class="bar-title small" v-if="!$util.isNull(claimGiftList) && claimGiftList.length > 0">{{clmname}} 사은품</div>
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
                            <th colspan="2">사은품명<button type="button" class="sort down"></button></th>
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
                <div class="clearfix" v-if="!$util.isNull(reapplyGiftList) && reapplyGiftList.length > 0">
                    <div class="bar-title small fl">재 적용 사은품</div>
                    <div class="btn-group fr">
                        <span class="txt-orange mr5">사은품을 다시 선택하시기 바랍니다.</span>
                        <button type="button" class="btn blue-line" @click="selectReapplyGiftList">사은품 선택</button>
                    </div>
                </div>
                <table cellpadding="0" cellspacing="0" class="data-tb align-c mt0" v-if="!$util.isNull(reapplyGiftList) && reapplyGiftList.length > 0">
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
                            <th colspan="2">사은품명<button type="button" class="sort down"></button></th>
                            <th>수량</th>
                            <th>적용프로모션 명</th>
                        </tr>
                    </thead>
                    <tbody v-if="!$util.isNull(chkReapplyGiftList) && chkReapplyGiftList.length > 0">
                        <tr v-for="(item, index) in chkReapplyGiftList" :key="'chkgift'+index">
                            <td>{{ item.giftcode }}</td>
                            <td>
                                <div class="img-thumb size60" :class="{'no-image': $util.isNull(item.fullpath)}">
                                    <img :src="item.fullpath" v-if="!$util.isNull(item.fullpath)">
                                </div>
                            </td>
                            <td class="left no-left"><p class="mg0">{{ item.giftname }}</p></td>
                            <td>{{ $util.maskComma(item.rcvamt) }}</td>
                            <td>{{ item.promoname }}</td>
                        </tr>
                    </tbody>
                    <tbody v-else>
                        <tr><td colspan="5">조회 결과가 존재하지 않습니다.</td></tr>
                    </tbody>
                </table>
                <template v-if="clmtype !== $store.getters['ADMIN'].CLM_CANCEL">
                    <hr class="dash" />
                    <div class="col2 pd scroll">
                        <div class="left">
                            <div class="bar-title small">회수지 정보</div>
                            <table cellpadding="0" cellspacing="0" class="gray-tb">
                                <colgroup>
                                    <col width="150px">
                                    <col width="">
                                </colgroup>
                                <tbody>
                                    <tr>
                                        <th>회수자 명</th>
                                        <td><input type="text" v-model="recInfo.recname" maxlength="50"/></td>
                                    </tr>
                                    <tr>
                                        <th>연락처</th>
                                        <td><input type="text" v-model="recInfo.rectel" maxlength="20"/></td>
                                    </tr>
                                    <tr>
                                        <th>회수지 주소</th>
                                        <td colspan="3">
                                            <div class="dpb">
                                                <input type="text" class="short" v-model="recInfo.recpost" maxlength="7" readonly>
                                                <button type="button" class="btn blue-line ml3" @click="searchAddress('rec')">주소검색</button>
                                            </div>
                                            <input type="text" class="dpb" style="width: 100%;" v-model="recInfo.recaddrroad" maxlength="100" readonly>
                                            <input type="text" class="dpb" style="width: 100%;" v-model="recInfo.recaddrdetailroad" maxlength="100">
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                        <div class="right">
                            <div class="bar-title small">회수택배사 정보</div>
                            <table cellpadding="0" cellspacing="0" class="gray-tb">
                                <colgroup>
                                    <col width="150px">
                                    <col width="">
                                </colgroup>
                                <tbody>
                                    <tr>
                                        <th>회수 택배사</th>
                                        <td>
                                            <select v-model="recInfo.reclogistype" style="width: 160px;" disabled>
                                                <option :value="null">선택</option>
                                                <option v-for="row in rtnlogislist" :key="row.idx" :value="row.logistype">{{ row.logistname }}</option>
                                            </select>
                                            <input type="text" class="ml3" placeholder="송장번호" v-model="recInfo.recinvoiceno" maxlength="50" disabled oninput="this.value = this.value.replace(/([^0-9])/gi, '');"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>회수 완료일</th>
                                        <td>
                                            <CommonDatePicker :value="recInfo.reccompdate" @change="onChangeRecDate" :disable="true"/>
                                            <input type="text" class="ml3" v-model="recInfo.reccomptime" readonly/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>상태</th>
                                        <td>
                                            <input type="text" v-model="recInfo.recstatname" readonly/>
                                            <button type="button" class="btn blue-line ml3" disabled>배송조회</button>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <hr class="dash" />
                </template>
                <template v-if="clmtype === $store.getters['ADMIN'].CLM_EXCHANGE">
                    <div class="col2 pd scroll">
                        <div class="left">
                            <div class="bar-title small">배송지 정보</div>
                            <table cellpadding="0" cellspacing="0" class="gray-tb">
                                <colgroup>
                                    <col width="150px">
                                    <col width="">
                                </colgroup>
                                <tbody>
                                    <tr>
                                        <th>수령인명</th>
                                        <td><input type="text" v-model="excInfo.excdlvname" maxlength="50" /></td>
                                    </tr>
                                    <tr>
                                        <th>연락처</th>
                                        <td><input type="text" v-model="excInfo.excdlvtel" maxlength="20" /></td>
                                    </tr>
                                    <tr>
                                        <th>배송지 주소</th>
                                        <td colspan="3">
                                            <div class="dpb">
                                                <input type="text" class="short" v-model="excInfo.excpost" maxlength="7" readonly>
                                                <button type="button" class="btn blue-line ml3" @click="searchAddress('exc')">주소검색</button>
                                            </div>
                                            <input type="text" class="dpb" style="width: 100%;" v-model="excInfo.excdlvaddrroad" maxlength="100" readonly>
                                            <input type="text" class="dpb" style="width: 100%;" v-model="excInfo.excdlvaddrdetailroad" maxlength="100">
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                        <div class="right">
                            <div class="bar-title small">배송택배사 정보</div>
                            <table cellpadding="0" cellspacing="0" class="gray-tb">
                                <colgroup>
                                    <col width="150px">
                                    <col width="">
                                </colgroup>
                                <tbody>
                                    <tr>
                                        <th>배송 택배사</th>
                                        <td>
                                            <select v-model="excInfo.exclogistype" style="width: 160px;" disabled>
                                                <option :value="null">선택</option>
                                                <option v-for="logistype in commonCode.logistype" :key="logistype.cmcode" :value="logistype.cmcode">{{ logistype.codename }}</option>
                                            </select>
                                            <input type="text" class="ml3" placeholder="송장번호" v-model="excInfo.excinvoiceno" maxlength="50" disabled
                                                oninput="this.value = this.value.replace(/([^0-9])/gi, '');"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>배송 완료일</th>
                                        <td>
                                            <CommonDatePicker :value="excInfo.exccompdate" @change="onChangeRecDate" :disable="true"/>
                                            <input type="text" class="ml3" v-model="excInfo.exccomptime" readonly/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>상태</th>
                                        <td>
                                            <input type="text" v-model="excInfo.excstatname" readonly/>
                                            <button type="button" class="btn blue-line ml3" disabled>배송조회</button>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <hr class="dash" />
                </template>
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
                        <template v-if="clmtype !== $store.getters['ADMIN'].CLM_CANCEL">
                            <div class="bar-title small">추가배송비</div>
                            <table cellpadding="0" cellspacing="0" class="gray-tb lower">
                                <colgroup>
                                    <col width="150px">
                                    <col width="">
                                </colgroup>
                                <tbody>
                                    <tr>
                                        <th>자사배송비</th>
                                        <td><input type="text" class="dpb right" style="width: 100%;" v-model="clmInfo.calinfo.adddadadelivamt" maxlength="11" 
                                            :disabled="ispbgoods==='F'" oninput="this.value = this.value.replace(/(^0[0-9]|[^0-9])/gi, '');"></td>
                                    </tr>
                                    <tr>
                                        <th>파트너사 배송비</th>
                                        <td><input type="text" class="dpb right" style="width: 100%;" v-model="clmInfo.calinfo.addptndelivamt" maxlength="11" 
                                            :disabled="ispbgoods==='T'" oninput="this.value = this.value.replace(/(^0[0-9]|[^0-9])/gi, '');"></td>
                                    </tr>
                                </tbody>
                            </table>
                        </template>
                        <template v-if="clmtype === $store.getters['ADMIN'].CLM_CANCEL && isAddPayment">
                            <div class="bar-title small">추가결제 예정금액<span class="normal txt-orange ml10">고객 직접 결제</span></div>
                            <table cellpadding="0" cellspacing="0" class="gray-tb lower">
                                <colgroup>
                                    <col width="150px">
                                    <col width="">
                                </colgroup>
                                <tbody>
                                    <tr>
                                        <th>결제예정금액</th>
                                        <td class="right">
                                            <input type="text" class="right" style="width: 100%;" v-model="clmInfo.calinfo.addrpaytotprice" maxlength="11"/>
                                        </td>
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
                            <div class="bar-title small fl mb0">환불계좌 정보</div>
                            <div class="btn-group fr">
                                <button type="button" class="btn small blue-line" @click="accountConfirm">환불계좌 인증</button>
                            </div>
                            <table cellpadding="0" cellspacing="0" class="gray-tb lower">
                                <colgroup>
                                    <col width="150px">
                                    <col width="">
                                </colgroup>
                                <tbody>
                                    <tr>
                                        <th>예금주명/생년월일</th>
                                        <td>
                                            <input type="text" style="width: 115px;" placeholder="예금주명" v-model="refInfo.refcusname" maxlength="50">
                                            <input type="text" class="ml3" style="width: 220px;" placeholder="생년월일 6자리 입력" v-model="refInfo.refbirthdate" maxlength="6" oninput="this.value = this.value.replace(/([^0-9])/gi, '');"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>환불은행/계좌번호</th>
                                        <td>
                                            <select v-model="refInfo.refbank" style="width: 115px;">
                                                <option :value="null">은행 선택</option>
                                                <option value="002">산업은행</option>
                                                <option value="003">기업은행</option>
                                                <option value="004">국민은행</option>
                                                <option value="007">수협은행/수협중앙회</option>
                                                <option value="011">농협은행</option>
                                                <option value="012">농협중앙회</option>
                                                <option value="020">우리은행</option>
                                                <option value="023">SC제일은행</option>
                                                <option value="027">한국씨티은행</option>
                                                <option value="031">대구은행</option>
                                                <option value="032">부산은행</option>
                                                <option value="034">광주은행</option>
                                                <option value="035">제주은행</option>
                                                <option value="037">전북은행</option>
                                                <option value="039">경남은행</option>
                                                <option value="045">새마을금고중앙회</option>
                                                <option value="048">신협중앙회</option>
                                                <option value="050">상호저축은행</option>
                                                <option value="054">HSBC은행</option>
                                                <option value="055">도이치은행</option>
                                                <option value="057">제이피모간체이스은행</option>
                                                <option value="060">BOA은행</option>
                                                <option value="062">중국공상은행</option>
                                                <option value="064">산림조합중앙회</option>
                                                <option value="071">우체국</option>
                                                <option value="081">KEB하나은행</option>
                                                <option value="088">신한은행</option>
                                                <option value="089">K뱅크</option>
                                                <option value="090">카카오뱅크</option>
                                                <option value="209">유안타증권</option>
                                                <option value="218">KB증권</option>
                                                <option value="238">미래에셋대우</option>
                                                <option value="240">삼성증권</option>
                                                <option value="243">한국투자증권</option>
                                                <option value="247">NH투자증권</option>
                                                <option value="261">교보증권</option>
                                                <option value="262">하이투자증권</option>
                                                <option value="263">현대차투자증권</option>
                                                <option value="264">키움증권</option>
                                                <option value="265">이베스트투자증권</option>
                                                <option value="266">SK증권</option>
                                                <option value="267">대신증권</option>
                                                <option value="269">한화투자증권</option>
                                                <option value="270">하나금융투자</option>
                                                <option value="278">신한금융투자</option>
                                                <option value="279">동부증권</option>
                                                <option value="280">유진투자증권</option>
                                                <option value="287">메리츠종합금융증권</option>
                                                <option value="290">부국증권</option>
                                                <option value="291">신영증권</option>
                                                <option value="292">케이프투자증권</option>
                                                <option value="103">SBI 저축은행</option>
                                            </select>
                                            <input type="text" class="ml3" style="width: 220px;" placeholder="계좌번호 '-'없이 숫자 입력" v-model="refInfo.refaccnumber" maxlength="50" oninput="this.value = this.value.replace(/([^0-9])/gi, '');"/>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </template>
                        <div class="bar-title small">최종환불</div>
                        <table cellpadding="0" cellspacing="0" class="gray-tb lower">
                            <colgroup>
                                <col width="150px">
                                <col width="40%">
                                <col width="">
                            </colgroup>
                            <tbody>
                                <tr>
                                    <th>금액(PG)</th>
                                    <td>{{ clmInfo.bforder.paywaytypename }}</td>
                                    <td class="right"><strong class="large-txt">{{ $util.maskComma(clmInfo.calinfo.rtnpayamt) }}</strong></td>
                                </tr>
                                <tr>
                                    <th>임직원적립금 반환</th>
                                    <td></td>
                                    <td class="right">{{ $util.maskComma(clmInfo.calinfo.rtnempresamt) }}</td>
                                </tr>
                                <tr>
                                    <th>적립금 반환</th>
                                    <td></td>
                                    <td class="right">{{ $util.maskComma(clmInfo.calinfo.rtnresamt) }}</td>
                                </tr>
                                <tr>
                                    <th>D포인트 반환</th>
                                    <td></td>
                                    <td class="right">{{ $util.maskComma(clmInfo.calinfo.rtnepoamt) }}</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="btn-group">
                    <button type="button" class="btn big blue" v-if="isCncProc" @click="saveClaim">취소처리</button>
                    <button type="button" class="btn big blue" v-else @click="saveClaim">{{clmname}}신청</button>
                    <button type="button" class="btn big darkgray" @click="onClose">닫기</button>
                </div>
            </div>
        </div>
        <AdminMemberInfo v-if="isShowMemDetail" :activeUserNo="activeUserNo" @closeDetail="closeMemDetail"></AdminMemberInfo>
        <GoodsDetail v-if="isGoodsDetailShow" :activeGoodsNo="activeGoodsNo" @closePopup="closeGoodsDetail"></GoodsDetail>
        <AddReapplyGiftPopup v-if="isShowAddGift" :reapplyGiftList="reapplyGiftList" @closePopup="closeAddGiftPopup"></AddReapplyGiftPopup>
    </div>
    <!-- /클레임신청 팝업 -->
</template>

<script>
import CommonDatePicker from "@views.admin/common/CommonDatePicker";
import AdminMemberInfo from '@views.admin/member/info/AdminMemberInfo.vue';
import GoodsDetail from '@views.admin/goods/manage/GoodsDetail.vue';
import OrderStatusHistoryPopup from '@views.admin/order/popup/OrderStatusHistoryPopup.vue';
import AddReapplyGiftPopup from '@/views/admin/order/popup/AddReapplyGiftPopup.vue';

export default {
    name: 'ApplyClaimPopup',
    props: ['claimParams'],
    components:{
        CommonDatePicker,
        AdminMemberInfo,
        GoodsDetail,
        AddReapplyGiftPopup
    },
    data() {
        return {
            isPartner: false,       // 파트너사여부
            clmtype: '',            // 클레임구분
            clmname: '',            // 클레임명
            rstype: '',             // 클레임사유구분
            rsdetail: '',           // 클레임사유상세내용
            orderInfo: {},          // 주문정보
            isCncProc: false,       // 취소처리여부
            isAddPayment: false,    // 추가결제여부(취소시에만 사용)
            ispbgoods: null,        // 직매입여부
            rtndelivtype: null,     // 반품배송타입
            excoption: '',          // 교환상품옵션코드
            claimInfo: {},          // 클레임정보
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
                giftlist: [],           // 사은품목록
            },
            recInfo : {       // 회수지, 회수택배사 정보
                recname: null,          // 회수자명
                rectel: null,           // 회수자연락처
                recpost: null,          // 회수자우편번호
                recaddr: null,          // 회수자지번주소
                recaddrdetail: null,    // 회주사지번상세주소
                recaddrroad: null,      // 회수자도로명주소
                recaddrdetailroad: null,// 회수자도로명상세주소
                recsigungucode: null,   // 회수자시군구코드
                recbuildingcode: null,  // 회수자빌딩코드
                recroadnamecode: null,  // 회수자도로명코드
                recinvoiceno: null,     // 회수운송장코드
                reclogistype: null,     // 회수택배사코드
                reccompdate: null,      // 회수완료일자
                reccomptime: null,      // 회수완료시간
                recdelivname: null,     // 회수처리자명
                recstatname: null       // 회수상태명
            },
            excInfo : {     // 반품배송지, 반품배송사 정보
                excdlvname: null,           // 반품자명
                excdlvtel: null,            // 반품자연락처
                excpost: null,              // 반품자우편번호
                excdlvaddr: null,           // 반품자지번주소
                excdlvaddrdetail: null,     // 반품자지번상세주소
                excdlvaddrroad: null,       // 반품자도로명주소
                excdlvaddrdetailroad: null, // 반품자도로명상세주소
                excsigungucode: null,       // 반품자시군구코드
                excbuildingcode: null,      // 반품자빌딩코드
                excroadnamecode : null,     // 반품자도로명코드
                excinvoiceno: null,         // 반품운송장번호
                exclogistype: null,         // 반품택배사코드
                exccompdate: null,          // 반품완료일자
                exccomptime: null,          // 반품완료시간
                excdelivname: null,         // 반품처리자명
                excstatname: null           // 반품상태명
            },
            refInfo : {              //환불정보
                refaccnumber : null,
                refbank : null,
                refcusname : null,
                refbirthdate: '',
                confirm : false,
            },
            commonCode: {
                rstype: [],         // 클레임사유
                cnctype: [],        // 취소사유
                rtntype: [],        // 반품사유
                exctype: [],        // 교환사유
                logistype: [],      // 택배사코드
            },
            claimGiftList: [],      // 클레임사은품목록
            reapplyGiftList: [],    // 재적용가능사은품목록
            chkReapplyGiftList: [], // 재적용사은품 목록
            rtnlogislist: [],       // 반품배송사목록
            excgoodslist: [],       // 교환출고상품목록
            excgoods: {},           // 선택한 교환상품
            origincnt: 0,           // 클레임가능수량(교환/반품)
            uploadFiles : [],
            activeUserNo: '',
            activeGoodsNo : false,
            isShowMemDetail: false,     // 회원상세 팝업 노출여부
            isGoodsDetailShow: false,   // 상품상세 팝업 노출여부
            isShowAddGift: false,       // 재적용사은품 팝업 노출여부
        }
    },
    mounted() {
        this.isPartner = this.$util.isAuthorized(this.$store.getters['CONSTANTS'].PARTNER_USER);
        this.orderInfo = this.claimParams.orderInfo;
        this.clmtype = this.claimParams.clmtype;
        // 공통코드 조회
        this.getCommonCodeList();
    },
    methods: {
        // 공통코드 목록 조회
        getCommonCodeList: function() {
            let cmclassArr = ['CNCTYPE', 'RTNTYPE', 'EXCTYPE', 'LOGISTYPE'];
            this.$http.post('/common/code/map/list', {cmclass : cmclassArr, isloading: false})
                .then(result =>{
                    this.$util.debug(result.data);
                    let data = result.data;
                    for (const [key] of Object.entries(data)) {
                        this.commonCode[key] = data[key];
                    }
                    if (this.clmtype === this.$store.getters['ADMIN'].CLM_CANCEL) {
                        this.clmname = '취소';
                        this.commonCode.rstype = this.commonCode.cnctype;
                    } else if (this.clmtype === this.$store.getters['ADMIN'].CLM_RETURN) {
                        this.clmname = '반품';
                        this.commonCode.rstype = this.commonCode.rtntype;
                    } else if (this.clmtype === this.$store.getters['ADMIN'].CLM_EXCHANGE) {
                        this.clmname = '교환';
                        this.commonCode.rstype = this.commonCode.exctype;
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
            let params = this.claimParams;
            params.rstype = this.rstype;
            this.$http.post('/admin/order/claim/apply/info', params)
                .then(result => {
                    this.$util.debug(result.data);
                    let data = result.data;
                    for (const [key] of Object.entries(data)) {
                        this.clmInfo[key] = data[key];
                    }
                    this.rtnlogislist = data.rtnlogislist;
                    this.excgoodslist = data.excgoodslist;
                    this.excgoods = (this.$util.isNull(this.excgoodslist) || this.excgoodslist.length === 0)? null : this.excgoodslist[0];

                    // 재계산 자사/파트너사 배송비, 적립금/임직원적립금/E포인트
                    this.clmInfo.aforder.dadadelivamt = Number(this.clmInfo.aforder.dadadelivamt) - Number(this.clmInfo.calinfo.rtndadadelivamt);
                    this.clmInfo.aforder.ptndelivamt = Number(this.clmInfo.aforder.ptndelivamt) - Number(this.clmInfo.calinfo.rtnptndelivamt);
                    this.clmInfo.aforder.empreservetotamt = Number(this.clmInfo.bforder.empreservetotamt) - Number(this.clmInfo.calinfo.rtnempresamt);
                    this.clmInfo.aforder.reservetotamt = Number(this.clmInfo.bforder.reservetotamt) - Number(this.clmInfo.calinfo.rtnresamt);
                    this.clmInfo.aforder.epointtotamt = Number(this.clmInfo.bforder.epointtotamt) - Number(this.clmInfo.calinfo.rtnepoamt);
                    this.clmInfo.aforder.rpaytotprice = Number(this.clmInfo.aforder.rpaytotprice) - Number(this.clmInfo.calinfo.rtndadadelivamt) - Number(this.clmInfo.calinfo.rtnptndelivamt)
                                                      + Number(this.clmInfo.calinfo.rtnempresamt) + Number(this.clmInfo.calinfo.rtnresamt) + Number(this.clmInfo.calinfo.rtnepoamt);

                    // 클레임가능 수량 세팅
                    this.clmInfo.dispitems.forEach(obj => {
                        //클레임상품이 이전클레임 상품일경우(교환의 교환, 교환의 반품등) 클레임가능수량은 클레임수량
                        if (this.$util.isNull(this.origincnt) || this.origincnt == 0) {
                            this.origincnt = obj.clmcnt;
                        } 
                        obj.origincnt = this.origincnt;
                        obj.ordcnt = obj.origincnt;
                    });
                    
                    // 추가결제여부 세팅
                    if (Number(this.clmInfo.calinfo.addrpaytotprice) > 0) {
                        this.isAddPayment = true;
                    }
                    // 추가배송비 자사/파트너사 세팅
                    if (this.clmtype !== this.$store.getters['ADMIN'].CLM_CANCEL) {
                        this.ispbgoods = this.clmInfo.dispitems[0].ispbgoods;
                        this.rtndelivtype = this.clmInfo.dispitems[0].rtndelivtype;
                        let adddelivamt = this.clmInfo.calinfo.adddelivamt;
                        this.clmInfo.calinfo.adddadadelivamt = this.ispbgoods==='T'? adddelivamt : 0;
                        this.clmInfo.calinfo.addptndelivamt = this.ispbgoods==='F'? adddelivamt : 0;
                    }
                    
                    // [취소신청] 상품준비중인 경우 취소처리 버튼 노출, 배송준비중인 경우 취소신청 버튼
                    if (this.clmtype === this.$store.getters['ADMIN'].CLM_CANCEL) {
                        let prepDelivCnt = this.clmInfo.dispitems.filter(item => {
                            return item.ordstatus === this.$store.getters['ADMIN'].ORDER_PREPARING_DELIV;
                        }).length;
                        if (prepDelivCnt === 0) {
                            this.isCncProc = true;
                        }
                    }
                    
                    //회수지,교환배송지 디폴트설정
                    let addr = {
                        post : this.clmInfo.bforder.rcvpost,
                        addrroad : this.clmInfo.bforder.rcvaddrroad,
                        addrdetailroad : this.clmInfo.bforder.rcvaddrdetailroad,
                        addr : this.clmInfo.bforder.rcvaddr,
                        addrdetail : this.clmInfo.bforder.rcvaddrdetail,
                        sigungucode : this.clmInfo.bforder.rcvsigungucode,
                        buildingcode : this.clmInfo.bforder.rcvbuildingcode,
                        roadnamecode : this.clmInfo.bforder.rcvroadnamecode
                    }
                    if(this.clmtype === this.$store.getters['ADMIN'].CLM_RETURN) {
                        this.recInfo.recname = this.clmInfo.bforder.rcvname;
                        this.recInfo.rectel = this.clmInfo.bforder.rcvtel1;
                        this.setAddr('rec', addr);
                    } else if(this.clmtype === this.$store.getters['ADMIN'].CLM_EXCHANGE) {
                        this.recInfo.recname = this.clmInfo.bforder.rcvname;
                        this.recInfo.rectel = this.clmInfo.bforder.rcvtel1;
                        this.setAddr('rec', addr);
                        this.excInfo.excdlvname = this.clmInfo.bforder.rcvname;
                        this.excInfo.excdlvtel = this.clmInfo.bforder.rcvtel1;
                        this.setAddr('exc', addr);
                    }
                    //주문자명을 환불계좌명으로 디폴트설정
                    if(this.clmInfo.bforder.paywaytype === this.$store.getters['ADMIN'].PAY_VIRTURE_ACCOUNT){
                        this.refInfo.refcusname = this.recInfo.recname;
                    }

                    //회수택배사 디폴트 설정
                    if (this.$util.isNull(this.recInfo.reclogistype)) {
                        this.recInfo.reclogistype = this.clmInfo.dispitems[0].rtnlogistype;
                    }

                    // 사은품목록 세팅
                    if (this.clmtype !== this.$store.getters['ADMIN'].CLM_EXCHANGE) {
                        this.claimGiftList = this.clmInfo.giftlist.filter(item => {return item.isadd==='F';});
                        this.reapplyGiftList = this.clmInfo.giftlist.filter(item => {return item.isadd==='T';});
                    }
                })
                .catch(error => {
                    this.$util.debug(error);
                });
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
        // 환불계좌 확인
        accountConfirm: function() {
            let msg = '';
            let valid = [
                {field: 'refInfo.refcusname'    , type: 'I', name: '예금주명'       , required: true},
                {field: 'refInfo.refbirthdate'  , type: 'I', name: '예금주 생년월일', required: true},
                {field: 'refInfo.refbank'       , type: 'S', name: '환불계좌 은행'  , required: true},
                {field: 'refInfo.refaccnumber'  , type: 'I', name: '환불 계좌번호'  , required: true}
            ];
            msg = this.$util.validMsg(this.$data, valid);
            if (!this.$util.isNull(msg)) {
                alert(msg);
                return false;
            }
            
            // 환불계좌 인증
            let params = {
                accounttype: '1',
                bank: this.refInfo.refbank,
                birthday: this.refInfo.refbirthdate,
                account: this.refInfo.refaccnumber,
                name: this.refInfo.refcusname
            }
            if (confirm('계좌인증 하시겠습니까?')) {
                this.$http.post('/common/bank/account', params).then(result => {
                    this.refInfo.confirm = result.data.exist;
                    if (this.refInfo.confirm) {
                        alert('환불계좌 인증이 완료되었습니다.');
                    } else {
                        alert('환불계좌 인증이 실패하였습니다. ('+ result.data.msg +')');
                    }
                });
            }
        },
        // 클레임 환불금액 재계산
        recalClaimRtnAmt: function() {
            this.claimParams.claim = this.clmInfo.dispitems;
            this.searchOrdCancelInfo();
        },
        // 클레임신청 저장
        saveClaim : function() {
            if (!this.checkValidation()) {
                return false;
            }

            let typename = this.clmname + '신청';
            let params = {
                clmtype: this.clmtype,
                cnctype : this.clmtype === this.$store.getters['ADMIN'].CLM_CANCEL ? this.rstype : null,    //취소사유
                rtntype : this.clmtype === this.$store.getters['ADMIN'].CLM_RETURN ? this.rstype : null,    //반품사유
                exctype : this.clmtype === this.$store.getters['ADMIN'].CLM_EXCHANGE ? this.rstype : null,  //교환사유
                clmdetail : this.rsdetail,                                                                  //상세사유
                cncstatus : this.clmtype === this.$store.getters['ADMIN'].CLM_CANCEL ? 'CNS001' : null,     //취소상태
                rtnstatus : this.clmtype === this.$store.getters['ADMIN'].CLM_RETURN ? 'RTS001' : null,     //반품상태
                excstatus : this.clmtype === this.$store.getters['ADMIN'].CLM_EXCHANGE ? 'EXS001' : null,   //교환상태
                claimitems : this.clmInfo.items,    //클레임상품
                items : this.clmInfo.afitems,       //계산후주문상품
                giftlist: Object.assign([], this.claimGiftList, this.chkReapplyGiftList),
                files: this.uploadFiles             //첨부파일
            }
            params = Object.assign({}, params, this.clmInfo.calinfo);   //클레임금액
            params = Object.assign({}, params, this.recInfo);           //회수지주소
            params = Object.assign({}, params, this.excInfo);           //교환배송지주소
            if(this.clmInfo.bforder.paywaytype === this.$store.getters['ADMIN'].PAY_VIRTURE_ACCOUNT){
                params = Object.assign({}, params, this.refInfo);       //환불계좌
            }

            /**
             * [취소]
             * - 취소처리 : 상품준비중 상태의 상품이고 추가결제금이 없는 경우 취소완료(처리완료) 상태
             * - 취소처리 : 상품준비중 상태의 상품이고 추가결제금이 있는 경우 취소처리(결제대기) 상태
             * - 취소신청 : 배송준비중 상태의 상품이인 경우 취소신청(승인대기) 상태
             * [반품]
             * - 반품신청 : 추가결제금이 있는 경우 반품처리(결제대기)
             * - 반품신청 : 추가결제금이 없는 경우 반품신청(승인대기)
             * [교환]
             * - 교환신청 : 추가결제금이 있는 경우 교환처리(결제대기)
             * - 교환신청 : 추가결제금이 없는 경우 교환신청(승인대기)
             */
            // 1. 취소
            if (this.clmtype === this.$store.getters['ADMIN'].CLM_CANCEL) {
                if (this.isCncProc && !this.isAddPayment) {
                    typename = '취소처리';
                    params.cncstatus = this.$store.getters['ADMIN'].CNC_PRCCOMP;            //취소완료(처리완료)
                } else if (this.isCncProc && this.isAddPayment) {
                    typename = '취소처리';
                    params.cncstatus = this.$store.getters['ADMIN'].CNC_WAITING_PAYMENT;    //취소처리(결제대기)
                } else {
                    params.cncstatus = this.$store.getters['ADMIN'].CNC_WAITING_APPRV;      //취소신청(승인대기)
                }
            } 
            
            // 2. 반품
            else if (this.clmtype === this.$store.getters['ADMIN'].CLM_RETURN) {
                // 추가배송비 수정이 가능해서 다시 계산
                params.adddelivamt = Number(params.adddadadelivamt) + Number(params.addptndelivamt);
                if (Number(params.addpaytotprice) + Number(params.adddelivamt) > 0) {
                    params.rtnstatus = this.$store.getters['ADMIN'].RETURN_WAITING_DEPOSIT;    //반품처리(결제대기)
                } else {
                    params.rtnstatus = this.$store.getters['ADMIN'].RETURN_WAITING_APPLY;      //반품신청(승인대기)
                }
                params.claimitems.forEach(item => { 
                    item.adddelivamt = params.adddelivamt;
                });
                params.clmidx = this.clmInfo.dispitems[0].clmidx;   //이전클레임IDX(교환-반품처리용)
            }

            // [교환]
            else if (this.clmtype === this.$store.getters['ADMIN'].CLM_EXCHANGE) {
                // 추가배송비 수정이 가능해서 다시 계산
                params.adddelivamt = Number(params.adddadadelivamt) + Number(params.addptndelivamt);
                if (Number(params.addpaytotprice) + Number(params.adddelivamt) > 0) {
                    params.excstatus = this.$store.getters['ADMIN'].EXCHANGE_WAITING_DEPOSIT;    //교환처리(결제대기)
                } else {
                    params.excstatus = this.$store.getters['ADMIN'].EXCHANGE_WAITING_APPLY;      //교환신청(승인대기)
                }
                params.claimitems.forEach(item => {
                    item.adddelivamt = params.adddelivamt;
                });
                params.excoption = this.excoption;                  //교환옵션설정
                params.clmidx = this.clmInfo.dispitems[0].clmidx;   //이전클레임IDX(교환처리용)
            }

            if(confirm(typename + ' 하시겠습니까?')) {
                this.$http.post('/admin/order/claim/save', params).then(result => {
                    this.$util.debug(result);
                    if (result.statusCode == 200) {
                        alert(this.$util.josaStr(typename, '이') + ' 완료되었습니다.');
                        this.onClose(true);
                    }
                });
            }
        },
        // 클레임저장 유효성체크
        checkValidation: function() {
            if (this.$util.isNull(this.rstype)) {
                alert(this.clmname + '사유를 선택해주세요.');
                return false;
            }
            if (this.rstype === this.$store.getters['ADMIN'].CNC_REASON_INPUT && this.$util.isNull(this.rsdetail)) {
                alert('취소상세사유를 입력해주세요.');
                return false;
            }
            // if (!this.$util.isNull(this.reapplyGiftList) && this.reapplyGiftList.length > 0
            //     && (this.$util.isNull(this.chkReapplyGiftList) || this.chkReapplyGiftList.length === 0)) {
            //     alert('재 적용 사은품이 있습니다. 사은품을 다시 선택하시기 바랍니다.');
            //     return false;
            // }
            if (this.clmtype !== this.$store.getters['ADMIN'].CLM_CANCEL) {
                let msg = '';
                let valid = [
                    {field: 'recInfo.recname', type: 'I', name: '회수자 명', required: true},
                    {field: 'recInfo.rectel', type: 'I', name: '회수자 연락처', required: true},
                    {field: 'recInfo.recpost', type: 'I', name: '회수지 주소', required: true}
                ];
                msg = this.$util.validMsg(this.$data, valid);
                if (!this.$util.isNull(msg)) {
                    alert(msg);
                    return false;
                }
            }
            if (this.clmtype === this.$store.getters['ADMIN'].CLM_EXCHANGE) {
                if (this.$util.isNull(this.excoption)) {
                    alert('교환할 상품을 선택해주세요.');
                    return false;
                }
                let msg = '';
                let valid = [
                    {field: 'excInfo.excdlvname', type: 'I', name: '수령인명', required: true},
                    {field: 'excInfo.excdlvtel', type: 'I', name: '수령인 연락처', required: true},
                    {field: 'excInfo.excpost', type: 'I', name: '배송지 주소', required: true}
                ];
                msg = this.$util.validMsg(this.$data, valid);
                if (!this.$util.isNull(msg)) {
                    alert(msg);
                    return false;
                }
            }
            if (this.clmtype !== this.$store.getters['ADMIN'].CLM_EXCHANGE && this.clmInfo.bforder.paywaytype === this.$store.getters['ADMIN'].PAY_VIRTURE_ACCOUNT) {
                let msg = '';
                let valid = [
                    {field: 'refInfo.refcusname'    , type: 'I', name: '예금주명'       , required: true},
                    {field: 'refInfo.refbirthdate'  , type: 'I', name: '예금주 생년월일', required: true},
                    {field: 'refInfo.refbank'       , type: 'S', name: '환불계좌 은행'  , required: true},
                    {field: 'refInfo.refaccnumber'  , type: 'I', name: '환불 계좌번호'  , required: true}
                ];
                msg = this.$util.validMsg(this.$data, valid);
                if (!this.$util.isNull(msg)) {
                    alert(msg);
                    return false;
                }
                if (!this.refInfo.confirm) {
                    alert('환불계좌 인증 후 진행해주세요.');
                    return false;
                }
            }

            return true;
        },
        // 날짜 picker 콜백 함수
        onChangeRecDate: function(value) {
            this.recInfo.reccompdate = value;
        },
        // 날짜 picker 콜백 함수
        onChangeExcDate: function(value) {
            this.excInfo.exccompdate = value;
        },
        // 주소 세팅(회수지, 교환배송지)
        setAddr(type, addr) {
            if(type === 'rec') {
                //회수지
                this.recInfo.recpost = addr.post;
                this.recInfo.recaddr = addr.addr;
                this.recInfo.recaddrdetail = addr.addrdetail;
                this.recInfo.recaddrroad = addr.addrroad;
                this.recInfo.recaddrdetailroad = addr.addrdetailroad;
                this.recInfo.recsigungucode = addr.sigungucode;
                this.recInfo.recbuildingcode = addr.buildingcode;
                this.recInfo.recroadnamecode = addr.roadnamecode;
            } else if(type === 'exc') {
                //교환배송지
                this.excInfo.excpost = addr.post;
                this.excInfo.excdlvaddr = addr.addr;
                this.excInfo.excdlvaddrdetail = addr.addrdetail;
                this.excInfo.excdlvaddrroad = addr.addrroad;
                this.excInfo.excdlvaddrdetailroad = addr.addrdetailroad;
                this.excInfo.excsigungucode = addr.sigungucode;
                this.excInfo.excbuildingcode = addr.buildingcode;
                this.excInfo.excroadnamecode = addr.roadnamecode;
            }
        },
        // 주소검색
        searchAddress: function(type) {
            new window.daum.Postcode({
                oncomplete: (data) => {
                    let addr = {
                        post: data.zonecode,
                        addr: this.$util.isNull(data.jibunAddress)? data.autoJibunAddress : data.jibunAddress,
                        addrdetail: '',
                        addrroad: this.$util.isNull(data.roadAddress)? data.autoRoadAddress : data.roadAddress,
                        addrdetailroad: '',
                        sigungucode: data.sigunguCode,
                        buildingcode: data.buildingCode,
                        roadnamecode: data.roadnameCode
                    };
                    this.setAddr(type, addr);
                }
            }).open();
        },
        // 첨부파일(탐색기 열기)
        fileAttach: function() {
            this.$refs.inputFile.click();
        },
        // 가져온 파일 세팅
        handleFileUpload: function(target) {
            let files = target.files;
            for (let i = 0; i < files.length; i++) {
                if (this.uploadFiles.length >= 10) {
                    return;
                }
                let obj = {
                    file: files[i],
                    key: i
                }
                this.uploadFiles.push(obj);
            }
            this.$refs.inputFile.value = '';
        },
        // 파일보기
        viewFile: function(file) {
            this.$viewerApi({
                images : [URL.createObjectURL(file)]
            });
        },
        // 파일삭제
        removeFile: function(index) {
            this.uploadFiles.splice(index, 1);
        },
        // Front 화면으로 이동
        goFrontGoodsDetail: function(value) {
            window.open(process.env.VUE_APP_PC_GOODS_DETAIL_URL + value, "_blank");
        },
        // 주문상태변경이력 팝업 오픈
        goOrderHistory: function(value) {
            let param = { ordgdidx: value };
            this.$eventBus.$emit('modalShow', OrderStatusHistoryPopup, param, null);
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
        onClose: function (isreload) {
            if(typeof(isreload) === 'boolean' && isreload) {
                this.$emit('closePopup', true);
            } else {
                this.$emit('closePopup');
            }
        },
    }
}
</script>