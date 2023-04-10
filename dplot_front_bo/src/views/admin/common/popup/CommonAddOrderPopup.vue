<template>
    <!-- 주문내역조회 팝업 -->
    <div id="modal-wrap" class="modal" style="display: block;">
        <div class="modal-content" style="width: 1600px;">
            <div class="pop-header">
                <h2>주문내역조회</h2>
                <button type="button" class="pop-close" @click="$modal.hide('commonModal');"></button>
            </div>
            <div class="pop-body">
                <div class="boxing search-area">
                    <dl>
                        <dt>직접검색</dt>
                        <dd>
                            <select v-model="searchData.skey">
                                <option value="">전체</option>
                                <option value="ordno">주문번호</option>
                                <option value="ordname">주문자명</option>
                                <option value="ordid">주문자ID</option>
                            </select>
                            <input type="text" v-model="searchData.sword" maxlength="200" @keyup.enter="searchList"/>
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
                            <input type="text" v-model="searchData.gdword" maxlength="500" @keyup.enter="searchList"/>
                        </dd>
                    </dl>
                    <dl>
                        <dt>조회일자</dt>
                        <dd>
                            <select v-model="searchData.dtkey">
                                <option value="order">주문일</option>
                                <option value="payment">결제일</option>
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
                    <dl v-if="searchData.isdealer==='T'">
                        <dt>파트너사</dt>
                        <dd>
                            <select :disabled="!$util.isNull(params.dealerno)" v-model="searchData.dealerno">
                                <option value="">파트너사 선택</option>
                                <option v-for="item in searchData.partnerList" :key="item.no" :value="item.no">{{ item.name }} </option>
                            </select>
                        </dd>
                    </dl>
                    <dl v-else>
                        <dt>판매구분</dt>
                        <dd>
                            <div class="radio_wrap wide3">
                                <input type="radio" name="ispbgoods" id="ispbgoodsAll" v-model="searchData.ispbgoods" value="" @click="searchData.dealerno=''"/>
                                <label for="ispbgoodsAll">전체</label>
                                <input type="radio" name="ispbgoods" id="ispbgoodsT" v-model="searchData.ispbgoods" value="T" @click="searchData.dealerno=''"/>
                                <label for="ispbgoodsT">직매입</label>
                                <input type="radio" name="ispbgoods" id="ispbgoodsF" v-model="searchData.ispbgoods" value="F"/>
                                <label for="ispbgoodsF">위탁</label>
                            </div>
                            <select :disabled="searchData.ispbgoods!='F'" v-model="searchData.dealerno">
                                <option value="">파트너사 선택</option>
                                <option v-for="item in searchData.partnerList" :key="item.no" :value="item.no">{{ item.name }} </option>
                            </select>
                        </dd>
                    </dl>
                </div>
                <div class="btn-group">
                    <button type="button" class="btn big blue" @click="searchList">검색</button>
                    <button type="button" class="btn big gray" @click="initSearchData">초기화</button>
                </div>
                <div class="caption-group mt10 clearfix">
                    <div class="total-group fl">
                        <span class="total">전체 <strong>{{ list.length }}</strong>건</span>
                    </div>
                </div>
                <div class="scroll-y" style="max-height: 500px;">
                    <table cellpadding="0" cellspacing="0" class="data-tb align-c">
                        <colgroup>
                            <col width="2%" /><!-- selectbox -->
                            <col width="6%" /><!-- 주문일 -->
                            <col width="6%" /><!-- 결제일 -->
                            <col width="5%" /><!-- 주문번호 -->
                            <col width="4.3%" /><!-- 주문자 -->
                            <col width="4.3%" /><!-- 아이디 -->
                            <col width="3.8%" /><!-- 판매구분 -->
                            <col width="6%" /><!-- 파트너사 -->
                            <col width="5%" /><!-- 상품코드 -->
                            <col width="3.5%" /><!-- 단품코드 -->
                            <col width="3.5%" /><!-- 상품순번 -->
                            <col width="62px" /><!-- 상품이미지 -->
                            <col width="" /><!-- 상품명 -->
                            <col width="7%" /><!-- 옵션 -->
                            <col width="3.8%" /><!-- 주문수량 -->
                            <col width="4.8%" /><!-- 판매단가 -->
                            <col width="4.8%" /><!-- 판매금액 -->
                            <col width="4.8%" /><!-- 송장번호 -->
                            <col width="6%" /><!-- 구매확정 -->
                            <col width="5%" /><!-- 주문상태 -->
                        </colgroup>
                        <thead>
                            <tr>
                                <th></th>
                                <th>주문일
                                    <button type="button" class="sort" :value="sortData.orderdate"
                                        :class="[{up : sortData.orderdate === 'orderdate_asc'}, {down : sortData.orderdate === 'orderdate_desc'}]"
                                        @click="sortToggle(sortData.orderdate)"></button>
                                </th>
                                <th>결제일
                                    <button type="button" class="sort" :value="sortData.paymentdate"
                                        :class="[{up : sortData.paymentdate === 'paymentdate_asc'}, {down : sortData.paymentdate === 'paymentdate_desc'}]"
                                        @click="sortToggle(sortData.paymentdate)"></button>
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
                                <th>판매구분
                                    <button type="button" class="sort" :value="sortData.ispbgoods"
                                        :class="[{up : sortData.ispbgoods === 'ispbgoods_asc'}, {down : sortData.ispbgoods === 'ispbgoods_desc'}]"
                                        @click="sortToggle(sortData.ispbgoods)"></button>
                                </th>
                                <th>파트너사
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
                                <th>상품순번
                                    <button type="button" class="sort" :value="sortData.goodsturn"
                                        :class="[{up : sortData.goodsturn === 'goodsturn_asc'}, {down : sortData.goodsturn === 'goodsturn_desc'}]"
                                        @click="sortToggle(sortData.goodsturn)"></button>
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
                                    <button type="button" class="sort" :value="sortData.ordcnt"
                                        :class="[{up : sortData.ordcnt === 'ordcnt_asc'}, {down : sortData.ordcnt === 'ordcnt_desc'}]"
                                        @click="sortToggle(sortData.ordcnt)"></button>
                                </th>
                                <th>판매단가
                                    <button type="button" class="sort" :value="sortData.price"
                                        :class="[{up : sortData.price === 'price_asc'}, {down : sortData.price === 'price_desc'}]"
                                        @click="sortToggle(sortData.price)"></button>
                                </th>
                                <th>판매금액
                                    <button type="button" class="sort" :value="sortData.totprice"
                                        :class="[{up : sortData.totprice === 'totprice_asc'}, {down : sortData.totprice === 'totprice'}]"
                                        @click="sortToggle(sortData.totprice)"></button>
                                </th>
                                <th>송장번호
                                    <button type="button" class="sort" :value="sortData.invoiceno"
                                        :class="[{up : sortData.invoiceno === 'invoiceno_asc'}, {down : sortData.invoiceno === 'invoiceno_desc'}]"
                                        @click="sortToggle(sortData.invoiceno)"></button>
                                </th>
                                <th>구매확정
                                    <button type="button" class="sort" :value="sortData.cfmconts"
                                        :class="[{up : sortData.cfmconts === 'cfmconts_asc'}, {down : sortData.cfmconts === 'cfmconts_desc'}]"
                                        @click="sortToggle(sortData.cfmconts)"></button>
                                </th>
                                <th>주문상태
                                    <button type="button" class="sort" :value="sortData.ordstatus"
                                        :class="[{up : sortData.ordstatus === 'ordstatus_asc'}, {down : sortData.ordstatus === 'ordstatus_desc'}]"
                                        @click="sortToggle(sortData.ordstatus)"></button>
                                </th>
                            </tr>
                        </thead>
                        <tbody v-if="list.length > 0">
                            <tr v-for="item in list" :key="item.orgdelividx">
                                <td><input type="radio" class="circle" name="checked" @change="setCheckedList(item)"/></td>
                                <td>{{ item.orderdate }}</td>
                                <td>{{ item.paymentdate }}</td>
                                <td>{{ item.ordno }}</td>
                                <td>{{ item.ordname }}</td>
                                <td>{{ item.ordid }}</td>
                                <td>{{ item.ispbgoodsname }}</td>
                                <td>{{ item.dealername }}</td>
                                <td>{{ item.goodscode }}</td>
                                <td>{{ item.optioncode }}</td>
                                <td>{{ item.goodsturn }}</td>
                                <td>
                                    <div class="img-thumb size60" :class="{'no-image': $util.isNull(item.fullpath)}">
                                        <img :src="item.fullpath" v-if="!$util.isNull(item.fullpath)">
                                    </div>
                                </td>
                                <td class="left no-left">{{ item.goodsname }}</td>
                                <td style="white-space: pre-wrap">{{ item.optionconts }}</td>
                                <td>{{ $util.maskComma(item.ordcnt) }}</td>
                                <td class="right">{{ $util.maskComma(item.price) }}</td>
                                <td class="right">{{ $util.maskComma(item.totprice) }}</td>
                                <td>{{ item.invoiceno }}</td>
                                <td style="white-space: pre-wrap">{{ item.cfmconts }}</td>
                                <td>{{ item.statusname }}</td>
                            </tr>
                        </tbody>
                        <tbody v-else>
                            <tr><td colspan="20">조회 결과가 존재하지 않습니다.</td></tr>
                        </tbody>
                    </table>
                </div>
                <div class="btn-group">
                    <button type="button" class="btn big blue" @click="sendData">선택 대상 적용</button>
                    <button type="button" class="btn big darkgray" @click="$modal.hide('commonModal');">닫기</button>
                </div>
            </div>
        </div>
    </div>
    <!-- //주문내역조회 팝업 -->
</template>

<script>
import CommonDatePicker from '@views.admin/common/CommonDatePicker.vue';

export default {
    name: 'CommonAddOrderPopup',
    components: {
        CommonDatePicker
    },
    props : {
        modalComp: Object,
        params: Object,
        callbackFn: Function
    },
    mounted() {
        // 팝업호출시 넘어온 조회조건 세팅
        let paramsKey = ['isdealer', 'dealerno'];
        if (!this.$util.isNull(this.params)) {
            for (const [key, value] of Object.entries(this.params)) {
                if (paramsKey.indexOf(key) > -1) {
                    this.searchData[key] = value;
                }
            }
        }
        // 검색조건 초기화
        this.initSearchData();
    },
    data :function(){
        return {
            searchData: {
                skey: '',               // 직접검색KEY
                sword: '',              // 직접검색어
                gdkey: '',              // 상품검색KEY
                gdword: '',             // 상품검색어
                dtkey: '',              // 날짜검색KEY
                period: '',             // 조회일자기간
                startdate: '',          // 조회시작일자
                enddate: '',            // 조회종료일자
                ispbgoods: '',          // 직매입여부
                dealerno: '',           // 입점업체번호
                isdealer: 'F',          // 파트너사여부
                partnerList: [],        // 파트너사 목록
                psort: 'orderdate_desc' // 정렬조건 (default: 주문일자 최신순)
            },
            sortData: {
                orderdate : 'orderdate_desc',   // 주문일
                paymentdate: 'paymentdate_asc', // 결제일
                ordno: 'ordno_asc',             // 주문번호
                ordname: 'ordname_asc',         // 주문자명
                ordid: 'ordid_asc',             // 주문자id
                ispbgoods: 'ispbgoods_asc',     // 판매구분
                dealername: 'dealername_asc',   // 판매사명
                goodscode: 'goodscode_asc',     // 상품코드
                optioncode: 'optioncode_asc',   // 단품코드
                goodsturn: 'goodsturn_asc',     // 상품순번
                goodsname: 'goodsname_asc',     // 상품명
                optionconts: 'optionconts_asc', // 옵션내용
                ordcnt: 'ordcnt_asc',           // 주문수량
                price: 'price_asc',             // 판매단가
                totprice: 'totprice_asc',       // 판매금액
                invoiceno: 'invoiceno_asc',     // 송장번호
                cfmconts: 'cfmconts_asc',       // 구매확정
                ordstatus: 'ordstatus_asc',     // 주문상태
            },
            isallchk: false,
            checkedObj: {},
            list: []
        }
    },
    methods : {
        // 검색조건 초기화
        initSearchData: function () {
            this.searchData.skey = '';
            this.searchData.sword = '';
            this.searchData.gdkey = 'goodsname';
            this.searchData.gdword = '';
            this.searchData.dtkey = 'order';
            this.searchData.period = 'months_3';
            this.searchData.startdate = this.$util.getAddMonth(this.$util.getDate(), -3, '-');
            this.searchData.enddate = this.$util.getDate('-');
            if (this.searchData.isdealer === 'T') {
                this.searchData.ispbgoods = 'F';
            } else {
                this.searchData.ispbgoods = '';
                this.searchData.dealerno = '';
            }
            // 파트너사 목록 조회
            this.getPartnerList();
            this.searchList();
        },
        // 파트너사 목록 조회
        getPartnerList: function() {
            let params = this.searchData;
            params.isloading = false;
            this.$http.post('/admin/goods/manage/partner/list', params)
                .then(result => {
                    this.$util.debug(result);
                    this.searchData.partnerList = result.data.list;
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 목록 조회
        searchList: function() {
            this.$http.post('/admin/common/order/list', this.searchData)
                .then(result => {
                    this.$util.debug(result);
                    this.list = result.data.list;
                    this.isallchk = false;
                    this.checkedObj = {};
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 시작날짜 picker 콜백 함수
        onChangeStartDate(value) {
            this.searchData.startdate = value;
        },
        // 종료날짜 picker 콜백 함수
        onChangeEndDate(value) {
            this.searchData.enddate = value;
        },
        // 테이블 정렬
        sortToggle: function(key){
            let arr = key.split("_");
            let sortKey = arr[0];
            let sortOrder = (arr[1] === 'asc') ? 'desc' : 'asc';
            let sortName = sortKey + '_' + sortOrder;
        
            this.sortData = this.$options.data().sortData;
            this.sortData[sortKey] = sortName;
            this.searchData.psort = sortName;
        
            this.list.sort((a, b) => {
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
        // 라디오버튼 선택
        setCheckedList: function(obj) {
            this.checkedObj = obj;
        },
        // 선택적용
        sendData: function() {
            if (this.$util.isNull(this.checkedObj)) {
                alert("적용할 주문내역을 선택해 주세요.");
                return;
            }
            // 팝업닫기 - 파라미터 전달
            this.closePopup(this.checkedObj);
        },
        // 팝업닫기
        closePopup: function(obj) {
            if( typeof(this.callbackFn) == 'function') {
                this.callbackFn({data: obj});
            }
            this.$modal.hide('commonModal');
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
        }
    }
}
</script>
