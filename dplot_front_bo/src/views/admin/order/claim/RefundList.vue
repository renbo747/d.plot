<template>
    <!-- 컨텐츠 영역 -->
    <div class="content m-leftmenu">
        <common-navigator></common-navigator>
        <div class="inner">
            <div class="clearfix">
                <div class="bar-title fl">환불관리</div>
            </div>
            <div class="boxing search-area pd0">
                <dl>
                    <dt>직접검색</dt>
                    <dd>
                        <select v-model="searchData.skey">
                            <option value="">전체</option>
                            <option value="ordno">주문번호</option>
                            <option value="ordname">주문자명</option>
                            <option value="ordid">주문자ID</option>
                            <option value="ordtel">주문자연락처</option>
                        </select>
                        <input type="text" v-model="searchData.sword" maxlength="200" @keyup.enter="searchList(1)"/>
                    </dd>
                </dl>
                <dl>
                    <dt>조회일자</dt>
                    <dd>
                        <select v-model="searchData.dtkey">
                            <option value="clmcomp">환불처리일</option>
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
                    <dt>클레임상태</dt>
                    <dd>
                        <div class="check-wrap">
                            <input type="checkbox" id="chkAllClmstatus" v-model="searchData.isallclmstatus" true-value="T" false-value="F" @change="checkAllClmstatus">
                            <label for="chkAllClmstatus">전체</label>
                        </div>
                        <div class="check-wrap" v-for="item in commonCode.clmstatus" :key="item.cmcode">
                            <input type="checkbox" :id="'clmstatus_'+item.cmcode" v-model="searchData.clmstatusArr" :true-value="[]" :value="item.cmcode"/>
                            <label :for="'clmstatus_'+item.cmcode">{{ item.codename }}</label>
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
                    <button type="button" v-if="isRead" class="btn green-line" @click="fnExcelDownload"><i class="icon-excel"></i>엑셀다운로드</button>
                    <select v-model="pagingData.pageCount" v-if="isRead">
                        <option value="20">20개씩 보기</option>
                        <option value="50">50개씩 보기</option>
                        <option value="100">100개씩 보기</option>
                    </select>
                </div>
            </div>
            <table cellpadding="0" cellspacing="0" class="data-tb align-c">
                <caption>환불 목록</caption>
                <colgroup>
                    <col width="30px" /><!-- checkbox -->
                    <col width="6%" /><!-- 환불처리일 -->
                    <col width="6%" /><!-- 주문일 -->
                    <col width="8.5%" /><!-- 주문번호 -->
                    <col width="5%" /><!-- 주문자 -->
                    <col width="5.5%" /><!-- 아이디 -->
                    <col width="8%" /><!-- 연락처 -->
                    <col width="5%" /><!-- 회원구분 -->
                    <col width="4%" /><!-- 유형 -->
                    <col width="4%" /><!-- 등급 -->
                    <col width="5%" /><!-- 환불수단 -->
                    <col width="5.5%" /><!-- 환불금액 -->
                    <col width="5.5%" /><!-- 환불은행 -->
                    <col width="" /><!-- 계좌번호 -->
                    <col width="5%" /><!-- 예금주 -->
                    <col width="8.5%" /><!-- 클레임번호 -->
                    <col width="8%" /><!-- 클레임상태 -->
                </colgroup>
                <thead>
                    <tr>
                        <th><input type="checkbox" id="chkall" v-model="isallchk" @change="checkAllList($event.target.checked)"/></th>
                        <th>환불처리일
                            <button type="button" class="sort" :value="sortData.clmcompdate"
                                :class="[{up : sortData.clmcompdate === 'clmcompdate_asc'}, {down : sortData.clmcompdate === 'clmcompdate_desc'}]"
                                @click="sortToggle(sortData.clmcompdate)"></button>
                        </th>
                        <th>주문일
                            <button type="button" class="sort" :value="sortData.orderdate"
                                :class="[{up : sortData.orderdate === 'orderdate_asc'}, {down : sortData.orderdate === 'orderdate_desc'}]"
                                @click="sortToggle(sortData.orderdate)"></button>
                        </th>
                        <th>주문번호
                            <button type="button" class="sort" :value="sortData.ordno"
                                :class="[{up : sortData.ordno === 'ordno_asc'}, {down : sortData.ordno === 'ordno_desc'}]"
                                @click="sortToggle(sortData.ordno)"></button>
                        </th>
                        <th>주문자
                            <button type="button" class="sort" :value="sortData.ordname"
                                :class="[{up : sortData.ordname === 'ordname_asc'}, {down : sortData.ordname === 'ordname_desc'}]"
                                @click="sortToggle(sortData.ordname)"></button>
                        </th>
                        <th>아이디
                            <button type="button" class="sort" :value="sortData.ordid"
                                :class="[{up : sortData.ordid === 'ordid_asc'}, {down : sortData.ordid === 'ordid_desc'}]"
                                @click="sortToggle(sortData.ordid)"></button>
                        </th>
                        <th>연락처
                            <button type="button" class="sort" :value="sortData.ordtel"
                                :class="[{up : sortData.ordtel === 'ordtel_asc'}, {down : sortData.ordtel === 'ordtel_desc'}]"
                                @click="sortToggle(sortData.ordtel)"></button>
                        </th>
                        <th>회원구분
                            <button type="button" class="sort" :value="sortData.isnonmember"
                                :class="[{up : sortData.isnonmember === 'isnonmember_asc'}, {down : sortData.isnonmember === 'isnonmember_desc'}]"
                                @click="sortToggle(sortData.isnonmember)"></button>
                        </th>
                        <th>유형
                            <button type="button" class="sort" :value="sortData.membertype"
                                :class="[{up : sortData.membertype === 'membertype_asc'}, {down : sortData.membertype === 'membertype_desc'}]"
                                @click="sortToggle(sortData.membertype)"></button>
                        </th>
                        <th>등급
                            <button type="button" class="sort" :value="sortData.memlvtype"
                                :class="[{up : sortData.memlvtype === 'memlvtype_asc'}, {down : sortData.memlvtype === 'memlvtype_desc'}]"
                                @click="sortToggle(sortData.memlvtype)"></button>
                        </th>
                        <th>환불수단
                            <button type="button" class="sort" :value="sortData.paywaytype"
                                :class="[{up : sortData.paywaytype === 'paywaytype_asc'}, {down : sortData.paywaytype === 'paywaytype_desc'}]"
                                @click="sortToggle(sortData.paywaytype)"></button>
                        </th>
                        <th>환불금액
                            <button type="button" class="sort" :value="sortData.finadjustamt"
                                :class="[{up : sortData.finadjustamt === 'finadjustamt_asc'}, {down : sortData.finadjustamt === 'finadjustamt_desc'}]"
                                @click="sortToggle(sortData.finadjustamt)"></button>
                        </th>
                        <th>환불은행
                            <button type="button" class="sort" :value="sortData.refbank"
                                :class="[{up : sortData.refbank === 'refbank_asc'}, {down : sortData.refbank === 'refbank_desc'}]"
                                @click="sortToggle(sortData.refbank)"></button>
                        </th>
                        <th>계좌번호
                            <button type="button" class="sort" :value="sortData.refaccnumber"
                                :class="[{up : sortData.refaccnumber === 'refaccnumber_asc'}, {down : sortData.refaccnumber === 'refaccnumber_desc'}]"
                                @click="sortToggle(sortData.refaccnumber)"></button>
                        </th>
                        <th>예금주
                            <button type="button" class="sort" :value="sortData.refcusname"
                                :class="[{up : sortData.refcusname === 'refcusname_asc'}, {down : sortData.refcusname === 'refcusname_desc'}]"
                                @click="sortToggle(sortData.refcusname)"></button>
                        </th>
                        <th>클레임번호
                            <button type="button" class="sort" :value="sortData.clmno"
                                :class="[{up : sortData.clmno === 'clmno_asc'}, {down : sortData.clmno === 'clmno_desc'}]"
                                @click="sortToggle(sortData.clmno)"></button>
                        </th>
                        <th>클레임상태
                            <button type="button" class="sort" :value="sortData.clmstatus"
                                :class="[{up : sortData.clmstatus === 'clmstatus_asc'}, {down : sortData.clmstatus === 'clmstatus_desc'}]"
                                @click="sortToggle(sortData.clmstatus)"></button>
                        </th>
                    </tr>
                </thead>
                <tbody v-if="list.length>0">
                    <tr v-for="item in list" :key="item.clmidx">
                        <td><input type="checkbox" :id="item.clmgdidx" v-model="checkedList" :value="item.clmidx" @change="checkList($event.target.checked)"/></td>
                        <td>{{ item.clmcompdate }}</td>
                        <td>{{ item.orderdate }}<br>{{ item.ordertime }}</td>
                        <td><a href="javascript:void(0);" class="link" @click="goOrderDetail(item.ordno)">{{ item.ordno }}</a></td>
                        <td v-if="item.isnonmember==='F'"><a href="javascript:void(0);" class="link" @click="goMemDetail(item.orduserno)">{{ item.ordname }}</a></td>
                        <td v-else>{{ item.ordname }}</td>
                        <td>{{ item.ordid }}</td>
                        <td>{{ $util.maskTel(item.ordtel) }}</td>
                        <td>{{ item.isnonmembername }}</td>
                        <td>{{ item.membertypename }}</td>
                        <td>{{ item.memlvtypename }}</td>
                        <td>{{ item.paywaytypename }}</td>
                        <td class="right">{{ $util.maskComma(item.finadjustamt) }}</td>
                        <td>{{ item.refbank }}</td>
                        <td>{{ item.refaccnumber }}</td>
                        <td>{{ item.refcusname }}</td>
                        <td>{{ item.clmno }}</td>
                        <td><a href="javascript:void(0);" class="link" @click="goRefundDetail(item.clmidx)">{{ item.clmstatusname }}</a></td>
                    </tr>
                </tbody>
                <tbody v-else>
                    <tr><td colspan="17">조회 결과가 존재하지 않습니다.</td></tr>
                </tbody>
            </table>
            <div class="bottom-group">
                <div class="paging">
                    <CommonPageNavigator v-show="isRead" :pagingData="pagingData" v-on:setPagingData="setPagingData" />
                </div>
            </div>
        </div>
        <OrderDetail v-if="isShowOrderDetail" :activeOrderCode="activeOrderCode" @closeDetail="closeOrderDetail"></OrderDetail>
        <AdminMemberInfo v-if="isShowMemDetail" :activeUserNo="activeUserNo" @closeDetail="closeMemDetail"></AdminMemberInfo>
    </div>
    <!-- /컨텐츠 영역 -->
</template>

<script>
import CommonNavigator from '@views.admin/common/CommonNavigator.vue';
import CommonDatePicker from '@views.admin/common/CommonDatePicker';
import CommonPageNavigator from '@views.admin/common/CommonPageNavigator';
import OrderDetail from '@views.admin/order/info/OrderDetail.vue';
import AdminMemberInfo from '@views.admin/member/info/AdminMemberInfo.vue';
import ClaimRefundDetailPopup from '@views.admin/order/popup/ClaimRefundDetailPopup.vue';

export default {
    name: 'admin.order.claim.refundlist',
    components: {
        CommonNavigator,
        CommonDatePicker,
        CommonPageNavigator,
        OrderDetail,
        AdminMemberInfo
    },
    mounted() {
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
            searchData: {
                skey: '',               // 직접검색KEY
                sword: '',              // 직접검색어
                dtkey: 'clmcomp',       // 조회일자KEY (default: 환불처리일)
                period: '',             // 조회일자기간
                startdate: '',          // 조회시작일자
                enddate: '',            // 조회종료일자
                isallclmstatus: '',       // 전체클레임상태여부
                clmstatusArr: [],         // 클레임구분Array
                psort: 'clmcompdate_desc' // 정렬조건 (default: 최근환불처리일)
            },
            commonCode: {
                
            },
            sortData: {
                clmcompdate: 'clmcompdate_asc',     // 클레임완료일
                orderdate: 'orderdate_asc',         // 주문일
                ordno: 'ordno_asc',                 // 주문번호
                ordname: 'ordname_asc',             // 주문자명
                ordid: 'ordid_asc',                 // 주문자id
                ordtel: 'ordtel_asc',               // 주문자연락처
                isnonmember: 'isnonmember_asc',     // 회원구분
                membertype: 'membertype_asc',       // 회원유형
                memlvtype: 'memlvtype_asc',         // 회원등급
                paywaytype: 'paywaytype_asc',       // 결제수단(환불수단)
                finadjustamt: 'finadjustamt_asc',   // 환불금액
                refbank: 'refbank_asc',             // 환불은행
                refaccnumber: 'refaccnumber_asc',   // 환불계좌번호
                refcusname: 'refcusname_asc',       // 환불예금주명
                clmno: 'clmno_asc',                 // 클레임번호
                clmstatus: 'clmstatus_asc'          // 클레임상태
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
            isRead : false,
            isWrite : false,
            activeOrderCode: '',
            activeUserNo: '',
            isShowOrderDetail: false,   // 주문상세 팝업 노출여부
            isShowMemDetail: false,     // 회원상세 팝업 노출여부
        };
    },
    methods: {
        // 화면 초기화
        onInit: function() {
            this.commonCode.clmstatus =  [
                {cmcode:this.$store.getters['ADMIN'].CNC_PRCCOMP, codename: '취소완료'},
                {cmcode:this.$store.getters['ADMIN'].RETURN_COMPLETE, codename: '반품완료'},
                {cmcode:this.$store.getters['ADMIN'].EXCHANGE_COMPLETE, codename: '교환완료'}
            ];
            // 검색조건 초기화
            this.initSearchData();
            this.$util.componentSetSearchParam(this);
            // 목록 조회
            this.searchList();
        },
        // 검색조건 초기화
        initSearchData: function () {
            this.searchData.skey ='';
            this.searchData.sword = '';
            this.searchData.dtkey = 'clmcomp';
            this.searchData.period = 'aday_0';
            this.searchData.startdate = this.$util.getDate('-');
            this.searchData.enddate = this.$util.getDate('-');
            this.searchData.isallclmstatus = 'T';
            this.checkAllClmstatus();
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
            this.$http.post('/admin/order/claim/refund/list', params)
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
        // 조회조건 - 클레임상태 전체 체크
        checkAllClmstatus: function() {
            let isAllCheck = this.searchData.isallclmstatus;
            this.searchData.clmstatusArr = [];
            if (isAllCheck == 'T') {
                for(let type of this.commonCode.clmstatus){
                    this.searchData.clmstatusArr.push(type.cmcode);
                }
            }
        },
        // 목록 전체체크
        checkAllList: function(value) {
            this.checkedList = [];
            if (value) {
                this.list.forEach(item => {
                    this.checkedList.push(item.clmidx);
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
            let config = { responseType: 'arraybuffer' };
            this.$http.post('/admin/order/claim/refund/exceldown', this.searchData, config)
                .then(result => {
                    this.$util.debug(result);
                })
                .catch(error => {
                    this.$util.debug(error);
                });
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
        // 환불상세내역 팝업 오픈
        goRefundDetail: function(value) {
            let param = { clmidx: value };
            this.$eventBus.$emit('modalShow', ClaimRefundDetailPopup, param, null);
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
        // 클레임상태
        'searchData.clmstatusArr': function(value) {
            if (value.length < this.commonCode.clmstatus.length) {
                this.searchData.isallclmstatus = 'F';
            } else {
                this.searchData.isallclmstatus = 'T';
            }
        }
    }
}
</script>