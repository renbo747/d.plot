<template>
    <!-- 컨텐츠 영역 -->
    <div class="content m-leftmenu">
        <common-navigator></common-navigator>
        <div class="inner">
            <div class="clearfix">
                <div class="bar-title fl">적립/차감/소멸내역</div>
            </div>
            <div class="boxing search-area">
                <dl>
                    <dt>직접검색</dt>
                    <dd>
                        <select v-model="searchData.skey">
                            <option value="">전체</option>
                            <option value="userid">아이디</option>
                            <option value="username">이름</option>
                            <option value="ordercode">주문번호</option>
                        </select>
                        <input type="text" v-model="searchData.sword" maxlength="200" @keyup.enter="searchList(1)"/>
                    </dd>
                </dl>
                <dl>
                    <dt>조회기간</dt>
                    <dd>
                        <common-date-picker :value="searchData.startDate" @change="onChangeStartDate"></common-date-picker>
                        <span>-</span>
                        <common-date-picker :value="searchData.endDate" @change="onChangeEndDate"></common-date-picker>
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
                    <dt>적립금 구분</dt>
                    <dd>
                        <div class="check-wrap">
                            <input type="checkbox" id="isempreserveAll" v-model="searchData.isempreserveAll" true-value="T" false-value="F" @change="checkAllEmpReserve">
                            <label for="isempreserveAll">전체</label>
                        </div>
                        <div class="check-wrap" v-for="item in commonCode.isempreserve" :key="item.cmcode">
                            <input type="checkbox" :id="'isempreserve_'+item.cmcode" v-model="searchData.isempreserveArr" :true-value="[]" :value="item.cmcode"/>
                            <label :for="'isempreserve_'+item.cmcode">{{ item.codename }}</label>
                        </div>
                    </dd>
                </dl>
                <dl>
                    <dt>적립/차감/소멸</dt>
                    <dd>
                        <div class="radio_wrap wide4">
                            <input type="radio" name="type" id="all" v-model="searchData.type" value=""/>
                            <label for="all">전체</label>
                            <input type="radio" name="type" id="pay" v-model="searchData.type" value="pay"/>
                            <label for="pay">적립(+)</label>
                            <input type="radio" name="type" id="use" v-model="searchData.type" value="use"/>
                            <label for="use">차감(-)</label>
                            <input type="radio" name="type" id="extinct" v-model="searchData.type" value="extinct"/>
                            <label for="extinct">소멸(-)</label>
                        </div>
                    </dd>
                </dl>
                <dl v-if="$util.isNull(searchData.type) || searchData.type=='pay'">
                    <dt>상세구분</dt>
                    <dd>
                        <div class="check-wrap">
                            <input type="checkbox" id="chkAllRespayType" v-model="searchData.isallrespaytype" true-value="T" false-value="F" @change="checkAllRespayType">
                            <label for="chkAllRespayType">전체</label>
                        </div>
                        <div class="check-wrap" v-for="item in commonCode.respaytype1" :key="item.cmcode">
                            <input type="checkbox" :id="'respaytype_'+item.cmcode" v-model="searchData.respaytypeArr" :true-value="[]" :value="item.cmcode"/>
                            <label :for="'respaytype_'+item.cmcode">{{ item.codename }}</label>
                        </div>
                    </dd>                    
                </dl>
                <dl v-if="$util.isNull(searchData.type) || searchData.type=='pay'">
                    <dt></dt>
                    <dd>
                        <div class="check-wrap" v-for="item in commonCode.respaytype2" :key="item.cmcode" >
                            <input type="checkbox" :id="'respaytype_'+item.cmcode" v-model="searchData.respaytypeArr" :true-value="[]" :value="item.cmcode"/>
                            <label :for="'respaytype_'+item.cmcode">{{ item.codename }}</label>
                        </div>
                    </dd>
                </dl>
                <dl v-if="$util.isNull(searchData.type)">
                    <dt></dt>
                    <dd>
                        <div class="check-wrap" v-for="item in commonCode.resusetype" :key="item.cmcode">
                            <input type="checkbox" :id="'respaytype_'+item.cmcode" v-model="searchData.respaytypeArr" :true-value="[]" :value="item.cmcode" v-if="$util.isNull(searchData.type)"/>
                            <label :for="'respaytype_'+item.cmcode" v-if="$util.isNull(searchData.type)">{{ item.codename }}</label>
                        </div>
                    </dd>
                </dl>
                <dl v-if="!$util.isNull(searchData.type) && searchData.type=='use'">
                    <dt>상세구분</dt>
                    <dd>
                        <div class="check-wrap">
                            <input type="checkbox" id="chkAllRespayType1" v-model="searchData.isallrespaytype" true-value="T" false-value="F" @change="checkAllRespayType">
                            <label for="chkAllRespayType1">전체</label>
                        </div>
                        <div class="check-wrap" v-for="item in commonCode.resusetype1" :key="item.cmcode">
                            <input type="checkbox" :id="'respaytype_'+item.cmcode" v-model="searchData.respaytypeArr" :true-value="[]" :value="item.cmcode"/>
                            <label :for="'respaytype_'+item.cmcode">{{ item.codename }}</label>
                        </div>
                    </dd>
                </dl>
                <dl v-if="!$util.isNull(searchData.type) && searchData.type=='extinct'">
                    <dt>상세구분</dt>
                    <dd>
                        <div class="check-wrap">
                            <input type="checkbox" id="chkAllRespayType2" v-model="searchData.isallrespaytype" true-value="T" false-value="F" @change="checkAllRespayType">
                            <label for="chkAllRespayType2">전체</label>
                        </div>
                        <div class="check-wrap" v-for="item in commonCode.resusetype2" :key="item.cmcode">
                            <input type="checkbox" :id="'respaytype_'+item.cmcode" v-model="searchData.respaytypeArr" :true-value="[]" :value="item.cmcode"/>
                            <label :for="'respaytype_'+item.cmcode">{{ item.codename }}</label>
                        </div>
                    </dd>
                </dl>
                <dl>
                    <dt>대상회원유형</dt>
                    <dd>
                        <div class="check-wrap">
                            <input type="checkbox" id="chkAllMember" v-model="searchData.isallmember" true-value="T" false-value="F" @change="checkAllMembertype">
                            <label for="chkAllMember">전체</label>
                        </div>
                        <div class="check-wrap" v-for="item in commonCode.dadamembertype" :key="item.cmcode">
                            <input type="checkbox" :id="'mumembertype_'+item.cmcode" v-model="searchData.mumembertypeArr" :true-value="[]" :value="item.cmcode">
                            <label :for="'mumembertype_'+item.cmcode">{{ item.codename }}</label>
                        </div>
                    </dd>
                </dl>
                <dl>
                    <dt>대상회원등급</dt>
                    <dd>
                        <div class="check-wrap">
                            <input type="checkbox" id="chkAllMemlv" v-model="searchData.isallmemlv" true-value="T" false-value="F" @change="checkAllMemlvtype">
                            <label for="chkAllMemlv">전체</label>
                        </div>
                        <div class="check-wrap" v-for="item in commonCode.memlvtype" :key="item.cmcode">
                            <input type="checkbox" :id="'mumemlvtype_'+item.cmcode" v-model="searchData.mumemlvtypeArr" :true-value="[]" :value="item.cmcode">
                            <label :for="'mumemlvtype_'+item.cmcode">{{ item.codename }}</label>
                        </div>
                    </dd>
                </dl>
            </div>
            <div class="btn-group">
                <button type="button" class="btn big blue" v-if="isRead" @click="searchList(1)">검색</button>
                <button type="button" class="btn big gray" v-if="isRead" @click="initSearchData">초기화</button>
            </div>
            <div class="caption-group mt10 clearfix">
                <div class="total-group fl">
                    <span class="total">전체 <strong>{{ $util.maskComma(totalcnt) }}</strong>건</span>
                </div>
                <div class="btn-group fr">
                    <button type="button" class="btn green-line" v-if="isRead" @click="downloadExcel"><i class="icon-excel"></i>엑셀다운로드</button>
                    <select v-model="pagingData.pageCount" v-if="isRead">
                        <option value="20">20개씩 보기</option>
                        <option value="50">50개씩 보기</option>
                        <option value="100">100개씩 보기</option>
                    </select>
                </div>
            </div>
            <table cellpadding="0" cellspacing="0" class="data-tb align-c">
                <caption>이벤트</caption>
                <colgroup>
                    <col width="4%" /><!-- No -->
                    <col width="8%" /><!-- 구분 -->
                    <col width="8%" /><!-- 아이디 -->
                    <col width="8%" /><!-- 이름 -->
                    <col width="7%" /><!-- 유형 -->
                    <col width="7%" /><!-- 등급 -->
                    <col width="7%" /><!-- 포인트 -->
                    <col width="6%" /><!-- 종류 -->
                    <col width="6%" /><!-- 수동처리여부 -->
                    <col width="9%" /><!-- 상세 구분 -->
                    <col width="" /><!-- 수동지급/차감 사유 -->
                    <col width="10%" /><!-- 주문번호 -->
                    <col width="10%" /><!-- 적립/차감/소멸일자 -->
                </colgroup>
                <thead>
                    <tr>
                        <th>No</th>
                        <th>구분</th>
                        <th>아이디</th>
                        <th>이름</th>
                        <th>유형
                            <button type="button" class="sort" :value="sortData.memtype" v-if="isRead"
                                :class="[{up : sortData.memtype=== 'memtype_asc'}, {down : sortData.memtype === 'memtype_desc'}]"
                                @click="sortToggle(sortData.memtype)"></button>
                        </th>
                        <th>등급
                            <button type="button" class="sort" :value="sortData.memlv" v-if="isRead"
                                :class="[{up : sortData.memlv=== 'memlv_asc'}, {down : sortData.memlv === 'memlv_desc'}]"
                                @click="sortToggle(sortData.memlv)"></button>
                        </th>
                        <th>포인트
                            <button type="button" class="sort" :value="sortData.point" v-if="isRead"
                                :class="[{up : sortData.point=== 'point_asc'}, {down : sortData.point === 'point_desc'}]"
                                @click="sortToggle(sortData.point)"></button>
                        </th>
                        <th>종류</th>
                        <th>수동처리여부</th>
                        <th>상세 구분</th>
                        <th>수동지급/차감 사유</th>
                        <th>주문번호</th>
                        <th>적립/차감/소멸일자
                            <button type="button" class="sort" :value="sortData.regdate" v-if="isRead"
                                :class="[{up : sortData.regdate=== 'regdate_asc'}, {down : sortData.regdate === 'regdate_desc'}]"
                                @click="sortToggle(sortData.regdate)"></button>
                        </th>
                    </tr>
                </thead>
                <tbody v-if="reserveList.length > 0">
                    <tr v-for="(item, index) in reserveList" :key="index">
                        <td>{{ loopNumberForPaging(index) }}</td>
                        <td>{{ item.isempreservename }}</td>
                        <td>{{ item.userid }}</td>
                        <td><a href="javascript:void(0);" class="link" @click="goMemDetail(item.userno)">{{ item.username }}</a></td>
                        <td>{{ item.membertypename }}</td>
                        <td>{{ item.memlvtypename }}</td>
                        <td>{{ item.formatpoint }}</td>
                        <td>{{ item.typename }}</td>
                        <td>{{ item.isautoname }}</td>
                        <td>{{ item.respaytypename }}</td>
                        <td>{{ item.resreason }}</td>
                        <td><a href="javascript:void(0);" class="link" @click="goOrderDetail(item.ordercode)">{{ item.ordercode }}</a></td>
                        <td>{{ item.regdate }}</td>
                    </tr>
                </tbody>
                <tbody v-else>
                    <tr><td colspan="13">조회 결과가 존재하지 않습니다.</td></tr>
                </tbody>
            </table>
            <div class="bottom-group">
                <common-page-navigator v-show="isRead" :pagingData="pagingData" v-on:setPagingData="setPagingData"/>
                <div class="btn-group">
                    <button type="button" v-if="isWrite" class="btn blue" @click="goManualDeduction">적립금 수동차감</button>
                    <button type="button" v-if="isWrite" class="btn blue" @click="goManualRegist">적립금 수동지급</button>
                </div>
            </div>
        </div>
        <AdminMemberInfo v-if="isMemDetailShow" :activeUserNo="activeUserNo" @closeDetail="closeMemDetailPopup"></AdminMemberInfo>
        <OrderDetail v-if="isOrderDetailShow" :activeOrderCode="activeOrderCode" @closeDetail="closeOrderDetailPopup"></OrderDetail>
        <ManualRegist v-if="isManualRegistShow" @closePopup="closeManualRegistPopup"></ManualRegist>
        <ManualDeduction v-if="isManualDeductionShow" @closePopup="closeManualDeductionPopup"></ManualDeduction>
    </div>
    <!-- /컨텐츠 영역 -->
</template>

<script>
import CommonNavigator from '@views.admin/common/CommonNavigator.vue'
import CommonPageNavigator from "@views.admin/common/CommonPageNavigator.vue";
import CommonDatePicker from '@views.admin/common/CommonDatePicker.vue';
import ManualRegist from '@views.admin/promotion/reserve/ManualRegist.vue';
import ManualDeduction from '@views.admin/promotion/reserve/ManualDeduction.vue';
import AdminMemberInfo from '@views.admin/member/info/AdminMemberInfo.vue';
import OrderDetail from '@views.admin/order/info/OrderDetail.vue';

export default {
    name: 'admin.promotion.reserve.reservelist',
    components: {
        CommonNavigator,
        CommonPageNavigator,
        CommonDatePicker,
        ManualRegist,
        ManualDeduction,
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
            isRead : false,
            isWrite : false,
            searchData: {
                skey: '',               // 직접검색 조건
                sword: '',              // 직접검색 단어
                startDate: '',          // 조회시작날짜
                endDate: '',            // 조회종료날짜
                period: 'months_3',     // 조회기간
                isempreserveAll: 'T',   // 적립금구분 전체체크 여부
                isempreserveArr: [],    // 적립금구분Array(default: 전체)
                type: '',               // 지급구분(적립:pay, 차감:use, 소멸:extinct)
                isallrespaytype: 'T',   // 적립금지급구분 전체체크여부
                respaytypeArr: [],      // 적립금지급구분Array
                isallmemlv: 'T',        // 회원등급전체여부
                mumemlvtypeArr: [],     // 다중대상회원등급Array
                isallmember: 'T',       // 다중대상회원유형전체여부
                mumembertypeArr: [],    // 다중대상회원유형Array
                psort: 'regdate_desc'   // 정렬
            },
            pagingData: {
                pageCount: 20,      // 페이징 옵션(최대수)
                page: 1,            // 현재 페이지
                listCount: 0        // 총 수량
            },
            commonCode: {
                respaytype: [],     // 적립금지급구분
                respaytype1: [],    // 적립금지급구분분리1(상단 라인)
                respaytype2: [],    // 적립금지급구분분리2(하단 라인)
                resusetype: [],     // 적립금사용구분
                resusetype1: [],    // 적립금사용구분분리1
                resusetype2: [],    // 적립금사용구분분리2
                isempreserve: [{cmcode: 'F', codename: '적립금'}, {cmcode: 'T', codename: '임직원적립금'}],   // 임직원적립금여부
                dadamembertype: [], // 다다픽회원유형
                memlvtype: [],      // 회원등급
            },
            sortData: {
                memtype : 'memtype_asc',
                memlv : 'memlv_asc',
                point : 'point_asc',
                regdate : 'regdate_desc'
            },
            isMemDetailShow: false,     // 회원상세 노출여부
            isOrderDetailShow: false,   // 주문상세 노출여부
            isManualRegistShow: false,    // 적립금수동지급 노출여부
            isManualDeductionShow: false, // 적립금수동차감 노출여부
            activeUserNo: '',           // 회원번호
            activeOrderCode: '',        // 주문번호
            reserveList: [],            // 적립/차감/소멸목록
            totalcnt: 0                 // 적립/차감/소멸목록 전체건수
        };
    },
    methods: {
        // 화면 초기화
        onInit: function() {
            // 공통코드 조회
            this.getCommonCodeList();

        },
        // 검색조건 초기화
        initSearchData: function () {
            this.searchData.skey = '';
            this.searchData.sword = '';
            this.searchData.period = 'months_3';
            this.searchData.startDate = this.$util.getAddMonth(this.$util.getDate(), -3, '-');
            this.searchData.endDate = this.$util.getDate('-');
            this.searchData.ispay = '';
            this.searchData.type = '';
            this.searchData.isempreserveAll = 'T';
            this.searchData.isallrespaytype = 'T';
            this.searchData.isallmemlv = 'T';
            this.searchData.isallmember = 'T';

            this.checkAllEmpReserve();
            this.checkAllRespayType();
            this.checkAllMemlvtype();
            this.checkAllMembertype();
        },
        // 적립금 내역 조회
        searchList: function(page) {
            this.pagingData.page = this.$util.isNull(page)? this.pagingData.page : 1;
            let params = Object.assign(this.searchData, this.pagingData);
            this.$http.post('/admin/promotion/reserve/list', params)
                .then(result => {
                    this.$util.debug(result);
                    let data = result.data;
                    this.reserveList = data.list;
                    this.totalcnt = data.totalcnt;
                    this.pagingData.listCount = data.totalcnt;
                    this.$util.dataSetSearchParam(this);
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 공통코드 목록 조회
        getCommonCodeList: function() {
            let cmclassArr = ['RESPAYTYPE', 'RESUSETYPE', 'DADAMEMBERTYPE', 'MEMLVTYPE'];
            this.$http.post('/common/code/map/list', {cmclass : cmclassArr, isloading: false})
                .then(result =>{
                    let data = result.data;
                    for (const [key] of Object.entries(data)) {
                        this.commonCode[key] = data[key];
                    }
                    
                    let respaytypeArr = Object.assign([], this.commonCode.respaytype);
                    let resusetypeArr = Object.assign([], this.commonCode.resusetype);
                    // 적립금사용구분, 적립금지급구분
                    this.commonCode.resusetype.forEach((item, index) => {
                        if (item.cmcode == this.$store.getters['ADMIN'].RES_USE_ORD_CANCEL) {
                            item.type = 'pay';
                            respaytypeArr.push(item);
                            resusetypeArr.splice(index, 1);
                        } else if (item.cmcode == this.$store.getters['ADMIN'].RES_USE_EXTINCT) {
                            item.type = 'extinct';
                            this.commonCode.resusetype2.push(item);
                        } else {
                            item.type = 'use';
                            this.commonCode.resusetype1.push(item);
                        }
                    });
                    respaytypeArr.forEach(item => {
                        item.type = 'pay';
                    });
                    this.commonCode.respaytype1 = respaytypeArr.splice(0, 7);
                    this.commonCode.respaytype2 = respaytypeArr;
                    this.commonCode.resusetype = resusetypeArr;

                    // 검색조건 초기화
                    this.initSearchData();
                    this.$util.componentSetSearchParam(this);
                    // 목록 조회
                    this.searchList();
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 시작날짜 picker 콜백 함수
        onChangeStartDate: function (value) {
            this.searchData.startDate = value;
        },
        // 종료날짜 picker 콜백 함수
        onChangeEndDate: function (value) {
            this.searchData.endDate = value;
        },
        // 조회조건 - 적립금구분 전체체크
        checkAllEmpReserve: function() {
            let isAllCheck = this.searchData.isempreserveAll;
            this.searchData.isempreserveArr = [];
            if (isAllCheck == 'T') {
                for(let type of this.commonCode.isempreserve){
                    this.searchData.isempreserveArr.push(type.cmcode);
                }
            }
        },
        // 조회조건 - 적립금지급구분 전체체크
        checkAllRespayType: function() {
            let isAllCheck = this.searchData.isallrespaytype;
            this.searchData.respaytypeArr = [];
            if (isAllCheck == 'T') {
                if (this.$util.isNull(this.searchData.type)) {
                    for(let item of this.commonCode.respaytype1){
                        this.searchData.respaytypeArr.push(item.cmcode);
                    }
                    for(let item of this.commonCode.respaytype2){
                        this.searchData.respaytypeArr.push(item.cmcode);
                    }
                    for(let item of this.commonCode.resusetype1){
                        this.searchData.respaytypeArr.push(item.cmcode);
                    }
                    for(let item of this.commonCode.resusetype2){
                        this.searchData.respaytypeArr.push(item.cmcode);
                    }
                } else if (this.searchData.type == 'pay') {
                    for(let item of this.commonCode.respaytype1){
                        this.searchData.respaytypeArr.push(item.cmcode);
                    }
                    for(let item of this.commonCode.respaytype2){
                        this.searchData.respaytypeArr.push(item.cmcode);
                    }
                } else if (this.searchData.type == 'use') {
                    for(let item of this.commonCode.resusetype1){
                        this.searchData.respaytypeArr.push(item.cmcode);
                    }
                } else if (this.searchData.type == 'extinct') {
                    for(let item of this.commonCode.resusetype2){
                        this.searchData.respaytypeArr.push(item.cmcode);
                    }
                }
            }
        },
        // 조회조건 - 대상회원등급 전체체크
        checkAllMemlvtype: function() {
            let isAllCheck = this.searchData.isallmemlv;
            this.searchData.mumemlvtypeArr = [];
            if (isAllCheck == 'T') {
                for(let type of this.commonCode.memlvtype){
                    this.searchData.mumemlvtypeArr.push(type.cmcode);
                }
            }
        },
        // 조회조건 - 대상회원유형 전체체크
        checkAllMembertype: function() {
            let isAllCheck = this.searchData.isallmember;
            this.searchData.mumembertypeArr = [];
            if (isAllCheck == 'T') {
                for(let type of this.commonCode.dadamembertype){
                    this.searchData.mumembertypeArr.push(type.cmcode);
                }
            }
        },
        // 엑셀다운로드
        downloadExcel: function() {
            if (this.reserveList.length == 0) {
                alert('다운로드할 내역이 존재하지 않습니다.');
                return;
            }
            let config = { responseType: 'arraybuffer' };
            this.$http.post('/admin/promotion/reserve/exceldown', this.searchData, config)
                .then(result => {
                    this.$util.debug(result);
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 회원상세 팝업 오픈
        goMemDetail: function(userno) {
            this.isMemDetailShow = true;
            this.activeUserNo = userno;
        },
        // 회원상세 팝업 닫기
        closeMemDetailPopup: function() {
            this.isMemDetailShow = false;
        },
        // 주문상세 팝업 오픈
        goOrderDetail: function(ordercode) {
            this.isOrderDetailShow = true;
            this.activeOrderCode = ordercode;
        },
        // 주문상세 팝업 닫기
        closeOrderDetailPopup: function() {
            this.isOrderDetailShow = false;
        },
        // 적립금수동지급 팝업 오픈
        goManualRegist: function() {
            this.isManualRegistShow = true;
        },
        // 적립금수동지급 팝업 닫기
        closeManualRegistPopup: function(isreload) {
            this.isManualRegistShow = false;
            if (isreload) {
                this.searchList();
            }
        },
        // 적립금수동차감 팝업 오픈
        goManualDeduction: function() {
            this.isManualDeductionShow = true;
        },
        // 적립금수동차감 팝업 닫기
        closeManualDeductionPopup: function(isreload) {
            this.isManualDeductionShow = false;
            if (isreload) {
                this.searchList();
            }
        },
        // 페이징데이터 세팅
        setPagingData(param){
            this.pagingData = param;
            this.searchList();
        },
        loopNumberForPaging(index) {
            if(this.pagingData.page > 1){
                let page = this.pagingData.page - 1;
                return (index+1) + (page * this.pagingData.pageCount);
            } else {
                return (index+1);
            }
        },
        // 정렬
        sortToggle(key){
            let arr = key.split('_');
            let sortKey = arr[0];
            let sortOrder = (arr[1] === 'asc') ? 'desc' : 'asc';
            let sortName = sortKey + '_' + sortOrder;

            this.sortData = this.$options.data().sortData;

            this.sortData[sortKey] = sortName;
            this.searchData.psort = sortName;

            this.searchList();
        },
    },
    watch: {
        // 조회기간
        'searchData.period': function (value) {
            let params = value.split('_');
            let type = params[0];
            let addValue = parseInt(params[1]) * -1;

            if (type == 'aday') {
                this.searchData.startDate = this.$util.getAddDate(this.$util.getDate(), addValue, '-');
                this.searchData.endDate = this.$util.getAddDate(this.$util.getDate(), addValue, '-');
            } else if (type == 'days') {
                this.searchData.startDate = this.$util.getAddDate(this.$util.getDate(), addValue, '-');
                this.searchData.endDate = this.$util.getDate('-');
            } else if (type == 'months') {
                this.searchData.startDate = this.$util.getAddMonth(this.$util.getDate(), addValue, '-');
                this.searchData.endDate = this.$util.getDate('-');
            }
        },
        // 임직원적립금여부
        'searchData.isempreserveArr': function (value) {
            if (value.length < this.commonCode.isempreserve.length) {
                this.searchData.isempreserveAll = 'F';
            } else {
                this.searchData.isempreserveAll = 'T';
            }
        },
        // 적립/차감 구분
        'searchData.type': function () {
            this.searchData.isallrespaytype = 'T';
            this.checkAllRespayType();
        },
        // 적립금지급구분
        'searchData.respaytypeArr': function(value) {
            let checkCnt = 0;
            let respayCnt = this.commonCode.respaytype.length+1;
            let resuse1Cnt = this.commonCode.resusetype1.length;
            let resuse2Cnt = this.commonCode.resusetype2.length;

            if (this.$util.isNull(this.searchData.type)) {
                checkCnt = respayCnt + resuse1Cnt + resuse2Cnt;
            } else if (this.searchData.type == 'pay') {
                checkCnt = respayCnt;
            } else if (this.searchData.type == 'use') {
                checkCnt = resuse1Cnt;
            } else if (this.searchData.type == 'extinct') {
                checkCnt = resuse2Cnt;
            }

            if (value.length < checkCnt) {
                this.searchData.isallrespaytype = 'F';
            } else {
                this.searchData.isallrespaytype = 'T';
            }
        },
        // 대상회원유형
        'searchData.mumembertypeArr': function(value) {
            if (value.length < this.commonCode.dadamembertype.length) {
                this.searchData.isallmember = 'F';
            } else {
                this.searchData.isallmember = 'T';
            }
        },
        // 대상회원등급
        'searchData.mumemlvtypeArr': function(value) {
            if (value.length < this.commonCode.memlvtype.length) {
                this.searchData.isallmemlv = 'F';
            } else {
                this.searchData.isallmemlv = 'T';
            }
        },
    }
}
</script>
