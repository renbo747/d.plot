<template>
    <!-- 컨텐츠 영역 -->
    <div class="content m-leftmenu">
        <common-navigator></common-navigator>
        <div class="inner">
            <div class="clearfix">
                <div class="bar-title fl">교환관리</div>
            </div>
            <div class="boxing search-area pd0">
                <dl>
                    <dt>직접검색</dt>
                    <dd>
                        <select v-model="searchData.skey">
                            <option v-for="item in searchData.skeyArr" :key="item.key" :value="item.key">{{ item.name }}</option>
                        </select>
                        <input type="text" v-model="searchData.sword" maxlength="200" @keyup.enter="searchList(1)"/>
                    </dd>
                </dl>
                <dl>
                    <dt>조회일자</dt>
                    <dd>
                        <select v-model="searchData.dtkey">
                            <option value="clmreq">교환신청일</option>
                            <option value="clmcomp">교환완료일</option>
                            <option value="order">주문일</option>
                        </select>
                        <CommonDatePicker :value="searchData.startdate" @change="onChangeStartDate"/>
                        <span>-</span>
                        <CommonDatePicker :value="searchData.enddate" @change="onChangeEndDate"/>
                        <div class="radio_wrap">
                            <input type="radio" v-model="searchData.period" id="period_aday_1" value='aday_1'/><label for="period_aday_1">어제</label>
                            <input type="radio" v-model="searchData.period" id="period_aday_0" value='aday_0'/><label for="period_aday_0">오늘</label>
                            <input type="radio" v-model="searchData.period" id="period_days_7" value='days_7'/><label for="period_days_7">일주일</label>
                            <input type="radio" v-model="searchData.period" id="period_months_1" value='months_1'/><label for="period_months_1">1개월</label>
                            <input type="radio" v-model="searchData.period" id="period_months_3" value='months_3'/><label for="period_months_3">3개월</label>
                            <input type="radio" v-model="searchData.period" id="period_months_6" value='months_6'/><label for="period_months_6">6개월</label>
                        </div>
                    </dd>
                </dl>
                <dl>
                    <dt>상품검색</dt>
                    <dd>
                        <select v-model="searchData.gdkey">
                            <option value="goodsname">상품명</option>
                            <option value="goodscode">상품코드</option>
                            <option value="optioncode">단품코드</option>
                            <option value="optionname">옵션명</option>
                        </select>
                        <input type="text" v-model="searchData.gdword" maxlength="500"/>
                    </dd>
                </dl>
                <dl>
                    <dt>신청구분</dt>
                    <dd>
                        <div class="check-wrap">
                            <input type="checkbox" id="chkAllClmreqtype" v-model="searchData.isallclmreqtype" true-value="T" false-value="F" @change="checkAllClmreqtype">
                            <label for="chkAllClmreqtype">전체</label>
                        </div>
                        <div class="check-wrap" v-for="item in commonCode.clmreqtype" :key="item.cmcode">
                            <input type="checkbox" :id="'clmreqtype_'+item.cmcode" v-model="searchData.clmreqtypeArr" :true-value="[]" :value="item.cmcode"/>
                            <label :for="'clmreqtype_'+item.cmcode">{{ item.codename }}</label>
                        </div>
                    </dd>
                </dl>
                <dl>
                    <dt>교환상태</dt>
                    <dd>
                        <div class="check-wrap">
                            <input type="checkbox" id="chkAllExcstatus" v-model="searchData.isallexcstatus" true-value="T" false-value="F" @change="checkAllExcstatus">
                            <label for="chkAllExcstatus">전체</label>
                        </div>
                        <div class="check-wrap" v-for="item in commonCode.excstatus1" :key="item.cmcode">
                            <input type="checkbox" :id="'excstatus_'+item.cmcode" v-model="searchData.excstatusArr" :true-value="[]" :value="item.cmcode"/>
                            <label :for="'excstatus_'+item.cmcode">{{ item.codename }}</label>
                        </div>
                    </dd>
                </dl>
                <dl>
                    <dt></dt>
                    <dd>
                        <div class="check-wrap" v-for="item in commonCode.excstatus2" :key="item.cmcode">
                            <input type="checkbox" :id="'excstatus_'+item.cmcode" v-model="searchData.excstatusArr" :true-value="[]" :value="item.cmcode"/>
                            <label :for="'excstatus_'+item.cmcode">{{ item.codename }}</label>
                        </div>
                    </dd>
                </dl>
            </div>
            <div class="btn-group" v-if="isRead">
                <button type="button" class="btn big blue" @click="searchList(1)">검색</button>
                <button type="button" class="btn big gray" @click="initSearchData">초기화</button>
            </div>
            <div class="caption-group mt10 clearfix">
                <div class="total-group fl">
                    <span class="total">전체 <strong>{{ count.totalcnt }}</strong>건</span>
                </div>
                <div class="btn-group fr">
                    <button type="button" v-if="isWrite" class="btn blue-line" @click="fnChangeExcstatus($store.getters['ADMIN'].EXCHANGE_APPROVAL_COMPLETE, '교환승인')">교환승인</button>
                    <button type="button" v-if="isWrite && !isPartner" class="btn red-line" @click="fnChangeExcstatus($store.getters['ADMIN'].EXCHANGE_REJECT, '교환반려')">교환반려</button>
                    <button type="button" v-if="isWrite && isPartner" class="btn red-line" @click="fnChangeExcstatus($store.getters['ADMIN'].EXCHANGE_REQUEST_REFUSE, '교환반려승인요청')">교환반려승인요청</button>
                    <!-- <button type="button" v-if="isWrite" class="btn red-line" @click="fnChangeExcstatus($store.getters['ADMIN'].EXCHANGE_CANCEL, '교환철회')">교환철회</button> -->
                    <button type="button" v-if="isWrite" class="btn black-line" @click="fnChangeExcstatus($store.getters['ADMIN'].EXCHANGE_RETURN_COMPLETE, '회수완료')">회수완료</button>
                    <button type="button" v-if="isWrite" class="btn red-line" @click="fnChangeExcstatus($store.getters['ADMIN'].EXCHANGE_A_RETURN, '반송처리')">반송처리</button>
                    <button type="button" v-if="isWrite" class="btn black-line" @click="fnChangeExcstatus($store.getters['ADMIN'].EXCHANGE_COMPLETE_DELIV, '배송완료')">배송완료</button>
                    <button type="button" v-if="isWrite && !isPartner" class="btn blue-line" @click="fnChangeExcstatus($store.getters['ADMIN'].EXCHANGE_COMPLETE, '처리완료')">처리완료</button>
                    <button type="button" v-if="isRead" class="btn green-line" @click="fnExcelDownload"><i class="icon-excel"></i>엑셀다운로드</button>
                    <select v-model="pagingData.pageCount" v-if="isRead">
                        <option value="20">20개씩 보기</option>
                        <option value="50">50개씩 보기</option>
                        <option value="100">100개씩 보기</option>
                    </select>
                </div>
            </div>
            <div class="scroll-x">
                <table cellpadding="0" cellspacing="0" class="data-tb align-c" :style="isPartner? 'width: 1800px;': 'width: 2400px;'">
                    <caption>취소 목록</caption>
                    <colgroup v-if="!isPartner">
                        <col width="1.2%" /><!-- checkbox -->
                        <col width="4%" /><!-- 교환 신청일 -->
                        <col width="4%" /><!-- 교환 완료일 -->
                        <col width="5.2%" /><!-- 교환번호 -->
                        <col width="4%" /><!-- 주문일 -->
                        <col width="3%" /><!-- 주문자 -->
                        <col width="4%" /><!-- 아이디 -->
                        <col width="5%" /><!-- 연락처 -->
                        <col width="3.5%" /><!-- 회원구분 -->
                        <col width="2.5%" /><!-- 유형 -->
                        <col width="2.7%" /><!-- 등급 -->
                        <col width="2.2%" /><!-- 차수 -->
                        <col width="3.2%" /><!-- 판매구분 -->
                        <col width="4%" /><!-- 파트너사 -->
                        <col width="4%" /><!-- 상품코드 -->
                        <col width="3.2%" /><!-- 단품코드 -->
                        <col width="62px" /><!-- 상품이미지 -->
                        <col width="" /><!-- 상품명 -->
                        <col width="5%" /><!-- 옵션 -->
                        <col width="3.2%" /><!-- 주문수량 -->
                        <col width="3.2%" /><!-- 교환수량 -->
                        <col width="3.5%" /><!-- 판매단가 -->
                        <col width="3.5%" /><!-- 판매금액 -->
                        <col width="3.1%" /><!-- 신청구분 -->
                        <col width="3.1%" /><!-- 교환상태 -->
                        <col width="5.5%" /><!-- 주문번호 -->
                    </colgroup>
                    <colgroup v-else>
                        <col width="1.2%" /><!-- checkbox -->
                        <col width="5%" /><!-- 교환 신청일 -->
                        <col width="5%" /><!-- 교환 완료일 -->
                        <col width="7%" /><!-- 교환번호 -->
                        <col width="5%" /><!-- 주문일 -->
                        <col width="5%" /><!-- 판매구분 -->
                        <col width="4.2%" /><!-- 단품코드 -->
                        <col width="62px" /><!-- 상품이미지 -->
                        <col width="" /><!-- 상품명 -->
                        <col width="8%" /><!-- 옵션 -->
                        <col width="4.5%" /><!-- 주문수량 -->
                        <col width="4.5%" /><!-- 교환수량 -->
                        <col width="5%" /><!-- 판매단가 -->
                        <col width="5%" /><!-- 판매금액 -->
                        <col width="4.5%" /><!-- 신청구분 -->
                        <col width="4.2%" /><!-- 교환상태 -->
                        <col width="7%" /><!-- 주문번호 -->
                    </colgroup>
                    <thead>
                        <tr>
                            <th><input type="checkbox" id="chkall" v-model="isallchk" @change="checkAllList($event.target.checked)"/></th>
                            <th>교환 신청일
                                <button type="button" class="sort" :value="sortData.clmreqdate"
                                    :class="[{up : sortData.clmreqdate === 'clmreqdate_asc'}, {down : sortData.clmreqdate === 'clmreqdate_desc'}]"
                                    @click="sortToggle(sortData.clmreqdate)"></button>
                            </th>
                            <th>교환 완료일
                                <button type="button" class="sort" :value="sortData.clmcompdate"
                                    :class="[{up : sortData.clmcompdate === 'clmcompdate_asc'}, {down : sortData.clmcompdate === 'clmcompdate_desc'}]"
                                    @click="sortToggle(sortData.clmcompdate)"></button>
                            </th>
                            <th>교환번호
                                <button type="button" class="sort" :value="sortData.clmno"
                                    :class="[{up : sortData.clmno === 'clmno_asc'}, {down : sortData.clmno === 'clmno_desc'}]"
                                    @click="sortToggle(sortData.clmno)"></button>
                            </th>
                            <th>주문일
                                <button type="button" class="sort" :value="sortData.orderdate"
                                    :class="[{up : sortData.orderdate === 'orderdate_asc'}, {down : sortData.orderdate === 'orderdate_desc'}]"
                                    @click="sortToggle(sortData.orderdate)"></button>
                            </th>
                            <th v-if="!isPartner">주문자
                                <button type="button" class="sort" :value="sortData.ordname"
                                    :class="[{up : sortData.ordname === 'ordname_asc'}, {down : sortData.ordname === 'ordname_desc'}]"
                                    @click="sortToggle(sortData.ordname)"></button>
                            </th>
                            <th v-if="!isPartner">아이디
                                <button type="button" class="sort" :value="sortData.ordid"
                                    :class="[{up : sortData.ordid === 'ordid_asc'}, {down : sortData.ordid === 'ordid_desc'}]"
                                    @click="sortToggle(sortData.ordid)"></button>
                            </th>
                            <th v-if="!isPartner">연락처
                                <button type="button" class="sort" :value="sortData.ordtel"
                                    :class="[{up : sortData.ordtel === 'ordtel_asc'}, {down : sortData.ordtel === 'ordtel_desc'}]"
                                    @click="sortToggle(sortData.ordtel)"></button>
                            </th>
                            <th v-if="!isPartner">회원구분
                                <button type="button" class="sort" :value="sortData.isnonmember"
                                    :class="[{up : sortData.isnonmember === 'isnonmember_asc'}, {down : sortData.isnonmember === 'isnonmember_desc'}]"
                                    @click="sortToggle(sortData.isnonmember)"></button>
                            </th>
                            <th v-if="!isPartner">유형
                                <button type="button" class="sort" :value="sortData.membertype"
                                    :class="[{up : sortData.membertype === 'membertype_asc'}, {down : sortData.membertype === 'membertype_desc'}]"
                                    @click="sortToggle(sortData.membertype)"></button>
                            </th>
                            <th v-if="!isPartner">등급
                                <button type="button" class="sort" :value="sortData.memlvtype"
                                    :class="[{up : sortData.memlvtype === 'memlvtype_asc'}, {down : sortData.memlvtype === 'memlvtype_desc'}]"
                                    @click="sortToggle(sortData.memlvtype)"></button>
                            </th>
                            <th v-if="!isPartner">차수
                                <button type="button" class="sort" :value="sortData.clmturn"
                                    :class="[{up : sortData.clmturn === 'clmturn_asc'}, {down : sortData.clmturn === 'clmturn_desc'}]"
                                    @click="sortToggle(sortData.clmturn)"></button>
                            </th>
                            <th v-if="!isPartner">판매구분
                                <button type="button" class="sort" :value="sortData.ispbgoods"
                                    :class="[{up : sortData.ispbgoods === 'ispbgoods_asc'}, {down : sortData.ispbgoods === 'ispbgoods_desc'}]"
                                    @click="sortToggle(sortData.ispbgoods)"></button>
                            </th>
                            <th v-if="!isPartner">파트너사
                                <button type="button" class="sort" :value="sortData.dealername"
                                    :class="[{up : sortData.dealername === 'dealername_asc'}, {down : sortData.dealername === 'dealername_desc'}]"
                                    @click="sortToggle(sortData.dealername)"></button>
                            </th>
                            <th>상품코드
                                <button type="button" class="sort" :value="sortData.goodscode"
                                    :class="[{up : sortData.goodscode === 'goodscode_asc'}, {down : sortData.goodscode === 'goodscode_desc'}]"
                                    @click="sortToggle(sortData.goodscode)"></button>
                            </th>
                            <th>단품코드
                                <button type="button" class="sort" :value="sortData.optioncode"
                                    :class="[{up : sortData.optioncode === 'optioncode_asc'}, {down : sortData.optioncode === 'optioncode_desc'}]"
                                    @click="sortToggle(sortData.optioncode)"></button>
                            </th>
                            <th colspan="2">상품명
                                <button type="button" class="sort" :value="sortData.goodsname"
                                    :class="[{up : sortData.goodsname === 'goodsname_asc'}, {down : sortData.goodsname === 'goodsname_desc'}]"
                                    @click="sortToggle(sortData.goodsname)"></button>
                            </th>
                            <th>옵션
                                <button type="button" class="sort" :value="sortData.optionconts"
                                    :class="[{up : sortData.optionconts === 'optionconts_asc'}, {down : sortData.optionconts === 'optionconts_desc'}]"
                                    @click="sortToggle(sortData.optionconts)"></button>
                            </th>
                            <th>주문수량
                                <button type="button" class="sort" :value="sortData.bfordcnt"
                                    :class="[{up : sortData.bfordcnt === 'bfordcnt_asc'}, {down : sortData.bfordcnt === 'bfordcnt_desc'}]"
                                    @click="sortToggle(sortData.bfordcnt)"></button>
                            </th>
                            <th>교환수량
                                <button type="button" class="sort" :value="sortData.clmcnt"
                                    :class="[{up : sortData.clmcnt === 'clmcnt_asc'}, {down : sortData.clmcnt === 'clmcnt_desc'}]"
                                    @click="sortToggle(sortData.clmcnt)"></button>
                            </th>
                            <th>판매단가
                                <button type="button" class="sort" :value="sortData.price"
                                    :class="[{up : sortData.price === 'price_asc'}, {down : sortData.price === 'price_desc'}]"
                                    @click="sortToggle(sortData.price)"></button>
                            </th>
                            <th>판매금액
                                <button type="button" class="sort" :value="sortData.totprice"
                                    :class="[{up : sortData.totprice === 'totprice_asc'}, {down : sortData.totprice === 'totprice_desc'}]"
                                    @click="sortToggle(sortData.totprice)"></button>
                            </th>
                            <th>신청구분
                                <button type="button" class="sort" :value="sortData.clmreqtype"
                                    :class="[{up : sortData.clmreqtype === 'clmreqtype_asc'}, {down : sortData.clmreqtype === 'clmreqtype_desc'}]"
                                    @click="sortToggle(sortData.clmreqtype)"></button>
                            </th>
                            <th>교환상태
                                <button type="button" class="sort" :value="sortData.excstatus"
                                    :class="[{up : sortData.excstatus === 'excstatus_asc'}, {down : sortData.excstatus === 'excstatus_desc'}]"
                                    @click="sortToggle(sortData.excstatus)"></button>
                            </th>
                            <th>주문번호
                                <button type="button" class="sort" :value="sortData.ordno"
                                    :class="[{up : sortData.ordno === 'ordno_asc'}, {down : sortData.ordno === 'ordno_desc'}]"
                                    @click="sortToggle(sortData.ordno)"></button>
                            </th>
                        </tr>
                    </thead>
                    <tbody v-if="list.length>0">
                        <tr v-for="item in list" :key="item.clmgdidx">
                            <td><input type="checkbox" :id="item.clmgdidx" v-model="checkedList" :value="item.clmgdidx" @change="checkList($event.target.checked)"/></td>
                            <td>{{ item.clmreqdate }}<br>{{ item.clmreqtime }}</td>
                            <td>{{ item.clmcompdate }}<br>{{ item.clmcomptime }}</td>
                            <td><a href="javascript:void(0);" class="link" @click="goExchangeDetail(item)">{{ item.clmno }}</a></td>
                            <td>{{ item.orderdate }}<br>{{ item.ordertime }}</td>
                            <td v-if="item.isnonmember==='F' && !isPartner"><a href="javascript:void(0);" class="link" @click="goMemDetail(item.orduserno)">{{ item.ordname }}</a></td>
                            <td v-if="item.isnonmember!=='F' && !isPartner">{{ item.ordname }}</td>
                            <td v-if="!isPartner">{{ item.ordid }}</td>
                            <td v-if="!isPartner">{{ $util.maskTel(item.ordtel) }}</td>
                            <td v-if="!isPartner">{{ item.isnonmembername }}</td>
                            <td v-if="!isPartner">{{ item.membertypename }}</td>
                            <td v-if="!isPartner">{{ item.memlvtypename }}</td>
                            <td v-if="!isPartner">{{ item.clmturn }}</td>
                            <td v-if="!isPartner">{{ item.ispbgoodsname }}</td>
                            <td v-if="!isPartner">{{ item.dealername }}</td>
                            <td>{{ item.goodscode }}</td>
                            <td>{{ item.optioncode }}</td>
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
                            <td>{{ $util.maskComma(item.clmcnt) }}</td>
                            <td class="right">{{ $util.maskComma(item.price) }}</td>
                            <td class="right">{{ $util.maskComma(Number(item.totprice)) }}</td>
                            <td>{{ item.clmreqtypename }}</td>
                            <td><a href="javascript:void(0);" class="link" @click="goClaimHistory(item.clmgdidx)">{{ item.excstatusname }}</a></td>
                            <td><a href="javascript:void(0);" class="link" @click="goOrderDetail(item.ordno)">{{ item.ordno }}</a></td>
                        </tr>
                    </tbody>
                    <tbody v-else>
                        <tr><td :colspan="isPartner? 17 :26">조회 결과가 존재하지 않습니다.</td></tr>
                    </tbody>
                </table>
            </div>
            <div class="bottom-group">
                <div class="paging">
                    <CommonPageNavigator v-show="isRead" :pagingData="pagingData" v-on:setPagingData="setPagingData" />
                </div>
            </div>
        </div>
        <ExchangeDetail v-if="isShowExchangeDetail" :activeClmParam="activeClmParam" :activeOrderInfo="activeOrderInfo" @closeDetail="closeExchangeDetail"></ExchangeDetail>
        <OrderDetail v-if="isShowOrderDetail" :activeOrderCode="activeOrderCode" @closeDetail="closeOrderDetail"></OrderDetail>
        <AdminMemberInfo v-if="isShowMemDetail" :activeUserNo="activeUserNo" @closeDetail="closeMemDetail"></AdminMemberInfo>
        <GoodsDetail v-if="isGoodsDetailShow" :activeGoodsNo="activeGoodsNo" @closePopup="closeGoodsDetail"></GoodsDetail>
    </div>
    <!-- /컨텐츠 영역 -->
</template>

<script>
import CommonNavigator from '@views.admin/common/CommonNavigator.vue';
import CommonDatePicker from '@views.admin/common/CommonDatePicker';
import CommonPageNavigator from '@views.admin/common/CommonPageNavigator';
import OrderDetail from '@views.admin/order/info/OrderDetail.vue';
import ExchangeDetail from '@views.admin/order/claim/ExchangeDetail.vue';
import AdminMemberInfo from '@views.admin/member/info/AdminMemberInfo.vue';
import GoodsDetail from '@views.admin/goods/manage/GoodsDetail.vue';
import ClaimStatusHistoryPopup from '@views.admin/order/popup/ClaimStatusHistoryPopup.vue';
import InputReasonPopup from '@/views/admin/order/popup/InputReasonPopup';

export default {
    name: 'admin.order.claim.exchangelist',
    components: {
        CommonNavigator,
        CommonDatePicker,
        CommonPageNavigator,
        GoodsDetail,
        OrderDetail,
        ExchangeDetail,
        AdminMemberInfo
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
                    // 초기화
                    this.onInit();
                }
            }).catch(error => {
                this.$util.debug(error);
            });
    },
    data() {
        return {
            isPartner: false,
            user: {},
            searchData: {
                clmtype: '',            // 클레임구분(교환)
                skeyArr: [],
                skey: '',               // 직접검색KEY
                sword: '',              // 직접검색어
                gdkey: '',              // 상품검색KEY
                gdword: '',             // 상품검색어
                dtkey: 'clmreq',        // 조회일자KEY (default: 교환신청일)
                period: '',             // 조회일자기간
                startdate: '',          // 조회시작일자
                enddate: '',            // 조회종료일자
                isallclmreqtype: '',    // 전체신청구분여부
                clmreqtypeArr: [],      // 신청구분Array
                isallexcstatus: '',     // 전체교환상태여부
                excstatusArr: [],       // 교환상태Array
                dealerno: '',           // 입점업체번호
                psort: 'clmreqdate_desc'  // 정렬조건 (default: 최근교환신청일)
            },
            commonCode: {
                clmreqtype: [],         // 클레임신청구분
                excstatus: [],          // 교환상태
                excstatus1: [],         // 교환상태1
                excstatus2: []          // 교환상태2
            },
            sortData: {
                clmreqdate: 'clmreqdate_asc',   // 클레임요청일
                clmcompdate: 'clmcompdate_asc', // 클레임완료일
                clmno: 'clmno_asc',             // 클레임번호
                orderdate: 'orderdate_asc',     // 주문일
                ordname: 'ordname_asc',         // 주문자명
                ordid: 'ordid_asc',             // 주문자id
                ordtel: 'ordtel_asc',           // 주문자연락처
                isnonmember: 'isnonmember_asc', // 회원구분
                membertype: 'membertype_asc',   // 회원유형
                memlvtype: 'memlvtype_asc',     // 회원등급
                clmturn: 'clmturn_asc',         // 차수
                ispbgoods: 'ispbgoods_asc',     // 판매구분
                dealername: 'dealername_asc',   // 판매사명
                goodscode: 'goodscode_asc',     // 상품코드
                optioncode: 'optioncode_asc',   // 단품코드
                goodsname: 'goodsname_asc',     // 상품명
                optionconts: 'optionconts_asc', // 옵션내용
                ordcnt: 'ordcnt_asc',           // 주문수량
                bfordcnt: 'bfordcnt_asc',       // 주문수량
                clmcnt: 'clmcnt_asc',           // 교환수량
                price: 'price_asc',             // 판매단가
                totprice: 'totprice_asc',       // 판매금액
                clmreqtype: 'clmreqtype_asc',   // 신청구분
                excstatus: 'excstatus_asc',     // 교환상태
                ordno: 'ordno_asc'              // 주문번호
            },
            pagingData: {
                pageCount: 20,      // 페이징 옵션(최대수)
                page: 1,            // 현재 페이지
                listCount: 0        // 총 수량
            },
            isallchk: false,        // 목록 전체체크여부
            list: [],               // 조회 데이터
            count: {                // 데이터 건수
                totalcnt: 0,        // 전체 건수
            },
            checkedList: [],        // 선택된 목록
            activeClmParam: {},
            activeOrderInfo: {},
            isRead : false,
            isWrite : false,
            activeGoodsNo : '',
            activeOrderCode: '',
            activeUserNo: '',
            isGoodsDetailShow: false,   // 상품상세 팝업 노출여부
            isShowMemDetail: false,     // 회원상세 팝업 노출여부
            isShowOrderDetail: false,   // 주문상세 팝업 노출여부
            isShowExchangeDetail: false, // 클레임교환상세 팝업 노출여부
            isLink : false, //대시보드에서 링크를 타고왔는지 체크
        };
    },
    methods: {
        // 화면 초기화
        onInit: function() {
            if(typeof this.$route.params.type !== 'undefined' && this.$route.params.type === 'LINK'){
              this.isLink = true;
            }
            // 공통코드 조회
            this.getCommonCodeList();
        },
        // 검색조건 초기화
        initSearchData: function () {
            this.searchData.clmtype = this.$store.getters['ADMIN'].CLM_EXCHANGE;
            this.searchData.skeyArr = [
                {key: '', name: '전체', isShowPartner: true},
                {key: 'ordno', name: '주문번호', isShowPartner: true},
                {key: 'ordname', name: '주문자명', isShowPartner: false},
                {key: 'ordid', name: '주문자ID', isShowPartner: false},
                {key: 'ordtel', name: '주문자연락처', isShowPartner: false},
                {key: 'clmno', name: '교환번호', isShowPartner: true}
            ];
            this.searchData.skey ='';
            this.searchData.sword = '';
            this.searchData.gdkey = 'goodsname';
            this.searchData.gdword = '';
            this.searchData.dtkey = 'clmreq';
            this.searchData.period = 'aday_0';
            this.searchData.startdate = this.$util.getDate('-');
            this.searchData.enddate = this.$util.getDate('-');
            this.searchData.isallclmreqtype = 'T';
            this.searchData.isallexcstatus = 'T';

            this.checkAllExcstatus();
            this.checkAllClmreqtype();
            
            if (this.isPartner) {
                this.searchData.dealerno = this.user.no;
                for (let i=this.searchData.skeyArr.length-1; i>0; i--) {
                    if (!this.searchData.skeyArr[i].isShowPartner) {
                        this.searchData.skeyArr.splice(i, 1);
                    }
                }
            } else {
                this.searchData.dealerno = '';
            }
        },
        // 목록 조회
        searchList: function(page) {
            this.pagingData.page = this.$util.isNull(page)? this.pagingData.page : 1;
            // 조회일자 1년 이내 체크
            if (this.$util.isNull(this.searchData.startdate) || this.$util.isNull(this.searchData.enddate)){
                alert('조회일자는 필수 조회조건 입니다.');
                return;
            } else {
                let startDate = this.searchData.startdate.replaceAll('-', '');
                let endDate = this.searchData.enddate.replaceAll('-', '');
                let checkMaxDate = this.$util.getAddDate(this.$util.getAddYear(startDate, 1), -1);
                if (endDate > checkMaxDate) {
                    alert('조회일자는 최대 1년까지만 조회 가능합니다.');
                    return;
                }
            }

            // 목록조회
            let params = Object.assign(this.searchData, this.pagingData);
            this.$http.post('/admin/order/claim/list', params)
                .then(result => {
                    this.$util.debug(result);
                    let data = result.data;
                    this.list = data.list;
                    this.count = data.count;
                    this.pagingData.listCount = data.count.totalcnt;
                    this.$util.dataSetSearchParam(this);
                    this.isallchk = false;
                    this.checkedList = [];
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 공통코드 목록 조회
        getCommonCodeList: function() {
            let cmclassArr = ['CLMREQTYPE', 'EXCSTATUS'];
            this.$http.post('/common/code/map/list', {cmclass : cmclassArr, isloading: false})
                .then(result =>{
                    let data = result.data;
                    for (const [key] of Object.entries(data)) {
                        this.commonCode[key] = data[key];
                    }
                    // 교환상태 2Row로 분리
                    this.commonCode.excstatus1 = this.commonCode.excstatus.splice(0, 6);
                    this.commonCode.excstatus2 = this.commonCode.excstatus;
                    // 검색조건 초기화
                    this.initSearchData();
                    this.$util.componentSetSearchParam(this);

                    if(this.isLink){
                      let linkParam = this.$route.params;
                      this.searchData.period = linkParam.period;
                      this.searchData.startdate = linkParam.startdate;
                      this.searchData.enddate = linkParam.enddate;
                      this.searchData.excstatusArr = linkParam.excstatus;
                    }
                    // 목록 조회
                    this.searchList();
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 조회조건 - 교환상태 전체 체크
        checkAllExcstatus: function() {
            let isAllCheck = this.searchData.isallexcstatus;
            this.searchData.excstatusArr = [];
            if (isAllCheck == 'T') {
                for(let type of this.commonCode.excstatus1){
                    this.searchData.excstatusArr.push(type.cmcode);
                }
                for(let type of this.commonCode.excstatus2){
                    this.searchData.excstatusArr.push(type.cmcode);
                }
            }
        },
        // 조회조건 - 신청구분 전체 체크
        checkAllClmreqtype: function() {
            let isAllCheck = this.searchData.isallclmreqtype;
            this.searchData.clmreqtypeArr = [];
            if (isAllCheck == 'T') {
                for(let type of this.commonCode.clmreqtype){
                    this.searchData.clmreqtypeArr.push(type.cmcode);
                }
            }
        },
        // 목록 전체체크
        checkAllList: function(value) {
            this.checkedList = [];
            if (value) {
                this.list.forEach(item => {
                    this.checkedList.push(item.clmgdidx);
                });
            }
        },
        // 목록 개별체크
        checkList: function() {
            if (this.list.length > this.checkedList.length){
                this.isallchk = false;
            } else {
                this.isallchk = true;
            }
        },
        // 날짜 picker 콜백 함수
        onChangeStartDate(value) {
            this.searchData.startdate = value;
        },
        // 날짜 picker 콜백 함수
        onChangeEndDate(value) {
            this.searchData.enddate = value;
        },
        // 페이징 콜백
        setPagingData(param){
            this.pagingData = param;
            this.searchList();
        },
        // 정렬
        sortToggle(key){
            if (!this.isRead) return;
            
            let arr = key.split('_');
            let sortKey = arr[0];
            let sortOrder = (arr[1] === 'asc') ? 'desc' : 'asc';
            let sortName = sortKey + '_' + sortOrder;
            this.sortData = this.$options.data().sortData;

            this.sortData[sortKey] = sortName;
            this.searchData.psort = sortName;

            this.searchList();
        },
        // 엑셀다운로드
        fnExcelDownload: function() {
            if (this.list.length == 0) {
                alert('다운로드할 내역이 존재하지 않습니다.');
                return;
            }
            let params = this.searchData;
            params.isPartner = this.isPartner;
            let config = { responseType: 'arraybuffer' };
            this.$http.post('/admin/order/claim/exceldown', params, config)
                .then(result => {
                    this.$util.debug(result);
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 교환상태 변경
        fnChangeExcstatus: function(reqStatus, reqName) {
            if (this.checkedList.length === 0) {
                alert('처리할 목록을 선택해주세요.');
                return;
            }

            let possibleStatus = [];
            let possibleStatusStr = '';
            let isShowRsPopup = false;
            let rsKeyName = '';
            switch(reqStatus) {
                // 교환승인(승인대기, 결제완료)
                case this.$store.getters['ADMIN'].EXCHANGE_APPROVAL_COMPLETE:
                    possibleStatus = [
                        this.$store.getters['ADMIN'].EXCHANGE_WAITING_APPLY,
                        this.$store.getters['ADMIN'].EXCHANGE_COMPLEATE_PAYMENT
                    ];
                    possibleStatusStr = '교환신청(승인대기), 교환처리(결제완료)';
                    break;
                // 교환반려(승인대기, 결제대기, 결제완료)
                case this.$store.getters['ADMIN'].EXCHANGE_REJECT:
                    possibleStatus = [
                        this.$store.getters['ADMIN'].EXCHANGE_WAITING_APPLY,
                        this.$store.getters['ADMIN'].EXCHANGE_COMPLEATE_PAYMENT,
                        this.$store.getters['ADMIN'].EXCHANGE_WAITING_DEPOSIT
                    ];
                    possibleStatusStr = '교환신청(승인대기), 교환처리(결제대기), 교환처리(결제완료)';
                    isShowRsPopup = true;
                    rsKeyName = 'rejreason';
                    break;
                // 교환반려승인요청(승인대기, 결제완료)
                case this.$store.getters['ADMIN'].EXCHANGE_REQUEST_REFUSE:
                    possibleStatus = [
                        this.$store.getters['ADMIN'].EXCHANGE_WAITING_APPLY,
                        this.$store.getters['ADMIN'].EXCHANGE_COMPLEATE_PAYMENT
                    ];
                    possibleStatusStr = '교환신청(승인대기), 교환처리(결제완료)';
                    isShowRsPopup = true;
                    rsKeyName = 'rejsubreason';
                    break;
                // 교환철회(결제대기)
                // case this.$store.getters['ADMIN'].EXCHANGE_CANCEL:
                //     possibleStatus = [
                //         this.$store.getters['ADMIN'].EXCHANGE_WAITING_DEPOSIT
                //     ];
                //     possibleStatusStr = '교환처리(결제대기)';
                //     isShowRsPopup = true;
                //     rsKeyName = 'dropreason';
                //     break;
                // 회수완료(회수진행)
                case this.$store.getters['ADMIN'].EXCHANGE_RETURN_COMPLETE:
                    possibleStatus = [
                        this.$store.getters['ADMIN'].EXCHANGE_RETURN_DELIVERY
                    ];
                    possibleStatusStr = '교환처리(회수진행)';
                    break;
                // 반송처리(회수완료)
                case this.$store.getters['ADMIN'].EXCHANGE_A_RETURN:
                    possibleStatus = [
                        this.$store.getters['ADMIN'].EXCHANGE_RETURN_COMPLETE
                    ];
                    possibleStatusStr = '교환처리(회수완료)';
                    isShowRsPopup = true;
                    rsKeyName = 'rtnreason';
                    break;
                // 배송완료(배송진행)
                case this.$store.getters['ADMIN'].EXCHANGE_COMPLETE_DELIV:
                    possibleStatus = [
                        this.$store.getters['ADMIN'].EXCHANGE_IN_DELIVERY
                    ];
                    possibleStatusStr = '교환처리(배송진행)';
                    break;
                // 구매확정(배송완료)
                case this.$store.getters['ADMIN'].EXCHANGE_COMPLETE:
                    possibleStatus = [
                        this.$store.getters['ADMIN'].EXCHANGE_COMPLETE_DELIV
                    ];
                    possibleStatusStr = '교환처리(배송완료)';
                    break;
                default:
                    break;
            }

            let targetlist = [];
            for (let i=0; i<this.list.length; i++) {
                for (let j=0; j<this.checkedList.length; j++) {
                    let clmgdidx = this.checkedList[j];
                    if (this.list[i].clmgdidx === clmgdidx) {
                        let excstatus = this.list[i].excstatus;
                        if (!this.$util.isNull(possibleStatus) && possibleStatus.length > 0 && possibleStatus.indexOf(excstatus) < 0) {
                            alert(this.$util.josaStr(reqName, '은') + ' \'' + possibleStatusStr + '\'상태에서만 처리할 수 있습니다. 확인후 진행해주세요.');
                            return;
                        }
                        targetlist.push(this.list[i]);
                    }
                }
            }

            let params = {
                clmtype: this.searchData.clmtype,
                targetlist: targetlist,
                reqName: reqName,
                reqStatus: reqStatus
            }
            if (isShowRsPopup) {
                this.$eventBus.$emit('modalShow', InputReasonPopup, params,
                    (result) => {
                        params[rsKeyName] = result.reason;
                        params.rtnlogistype = result.rtnlogistype;
                        params.rtninvoiceno = result.rtninvoiceno;
                        this.updateClaimStatus(params);
                    }
                );
            } else {
                this.updateClaimStatus(params);
            }
        },
        // 상태 갱신
        updateClaimStatus: function(params) {
            if (confirm(params.reqName + ' 하시겠습니까?')) {
                this.$http.post('/admin/order/claim/save/status/all', params).then(result => {
                    this.$util.debug(result);
                    if (result.statusCode === 200) {
                        alert(this.$util.josaStr(params.reqName, '이') + ' 완료되었습니다.');
                        this.searchList();
                    }
                })
                .catch(error => {
                    this.$util.debug(error);
                });
            }
        },
        // Front 화면으로 이동
        goFrontGoodsDetail: function(value) {
            window.open(process.env.VUE_APP_PC_GOODS_DETAIL_URL + value, "_blank");
        },
        // 클레임상세 팝업 오픈
        goExchangeDetail: function(obj) {
            this.isShowExchangeDetail = true;
            this.activeClmParam = {
                orderidx: obj.orderidx,
                clmidx: obj.clmidx,
                clmgdidx: obj.clmgdidx
            }
            this.activeOrderInfo = obj;
        },
        // 클레임상세 팝업 닫기
        closeExchangeDetail: function(isreload) {
            this.isShowExchangeDetail = false;
            if (isreload) {
                this.searchList();
            }
        },
        // 주문상세 팝업 오픈
        goOrderDetail: function(value) {
            this.isShowOrderDetail = true;
            this.activeOrderCode = value;
        },
        // 주문상세 팝업 닫기
        closeOrderDetail: function() {
            this.isShowOrderDetail = false;
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
        // 클래임상태변경이력 팝업 오픈
        goClaimHistory: function(value) {
            let param = { clmgdidx: value };
            this.$eventBus.$emit('modalShow', ClaimStatusHistoryPopup, param, null);
        }
    },
    watch: {
        // 조회기간
        'searchData.period': function (value) {
            let params = value.split('_');
            let type = params[0];
            let addValue = parseInt(params[1]) * -1;

            if (type == 'aday') {
                this.searchData.startdate = this.$util.getAddDate(this.$util.getDate(), addValue, '-');
                this.searchData.enddate = this.$util.getAddDate(this.$util.getDate(), addValue, '-');
            } else if (type == 'days') {
                this.searchData.startdate = this.$util.getAddDate(this.$util.getDate(), addValue, '-');
                this.searchData.enddate = this.$util.getDate('-');
            } else if (type == 'months') {
                this.searchData.startdate = this.$util.getAddMonth(this.$util.getDate(), addValue, '-');
                this.searchData.enddate = this.$util.getDate('-');
            }
        },
        // 교환상태
        'searchData.excstatusArr': function(value) {
            if (value.length < this.commonCode.excstatus1.length + this.commonCode.excstatus2.length) {
                this.searchData.isallexcstatus = 'F';
            } else {
                this.searchData.isallexcstatus = 'T';
            }
        },
        // 신청구분
        'searchData.clmreqtypeArr': function(value) {
            if (value.length < this.commonCode.clmreqtype.length) {
                this.searchData.isallclmreqtype = 'F';
            } else {
                this.searchData.isallclmreqtype = 'T';
            }
        }
    }
}
</script>