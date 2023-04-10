<template>
    <!-- 컨텐츠 영역 -->
    <div class="content m-leftmenu">
        <common-navigator></common-navigator>
        <div class="inner">
            <div class="clearfix">
                <div class="bar-title fl">프로모션 관리</div>
            </div>
            <div class="boxing search-area">
                <dl>
                    <dt>직접검색</dt>
                    <dd>
                        <select v-model="searchData.skey">
                            <option value="">전체</option>
                            <option value="promoname">프로모션명</option>
                            <option value="regusername">등록자</option>
                        </select>
                        <input type="text" v-model="searchData.sword" maxlength="200" @keyup.enter="searchList(1)"/>
                    </dd>
                </dl>
                <dl>
                    <dt>조회기간</dt>
                    <dd>
                        <select v-model="searchData.sdate">
                            <option value="">전체</option>
                            <option value="start">시작일자</option>
                            <option value="end">종료일자</option>
                            <option value="reg">등록일자</option>
                        </select>
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
                    <dt>사용여부</dt>
                    <dd>
                        <div class="radio_wrap wide3">
                            <input type="radio" name="istrash" id="istrashAll" v-model="searchData.istrash" value=""/>
                            <label for="istrashAll">전체</label>
                            <input type="radio" name="istrash" id="istrashF" v-model="searchData.istrash" value="F"/>
                            <label for="istrashF">사용</label>
                            <input type="radio" name="istrash" id="istrashT" v-model="searchData.istrash" value="T"/>
                            <label for="istrashT">미사용</label>
                        </div>
                    </dd>
                </dl>
                <dl>
                    <dt>진행상태</dt>
                    <dd>
                        <div class="check-wrap">
                            <input type="checkbox" id="chkAllPromost" v-model="searchData.isallpromosttype" true-value="T" false-value="F" @change="checkAllPromosttype">
                            <label for="chkAllPromost">전체</label>
                        </div>
                        <div class="check-wrap" v-for="item in commonCode.promosttype" :key="item.cmcode">
                            <input type="checkbox" :id="'promosttype_'+item.cmcode" v-model="searchData.promosttypeArr" :true-value="[]" :value="item.cmcode">
                            <label :for="'promosttype_'+item.cmcode">{{ item.codename }}</label>
                        </div>
                    </dd>
                </dl>
                <dl>
                    <dt>프로모션구분</dt>
                    <dd>
                        <div class="check-wrap">
                            <input type="checkbox" id="chkAllPromodiv" v-model="searchData.isallpromodivtype" true-value="T" false-value="F" @change="checkAllPromodivtype">
                            <label for="chkAllPromodiv">전체</label>
                        </div>
                        <div class="check-wrap" v-for="item in commonCode.promodivtype" :key="item.cmcode">
                            <input type="checkbox" :id="'promodivtype_'+item.cmcode" v-model="searchData.promodivtypeArr" :true-value="[]" :value="item.cmcode">
                            <label :for="'promodivtype_'+item.cmcode">{{ item.codename }}</label>
                        </div>
                    </dd>
                </dl>
                <dl>
                    <dt>적용채널</dt>
                    <dd>
                        <div class="check-wrap">
                            <input type="checkbox" id="chkAllAppch" v-model="searchData.isallchannel" true-value="T" false-value="F" @change="checkAllAppchtype">
                            <label for="chkAllAppch">전체</label>
                        </div>
                        <div class="check-wrap" v-for="item in commonCode.muappchtype" :key="item.cmcode">
                            <input type="checkbox" :id="'muappchtype_'+item.cmcode" v-model="searchData.muappchtypeArr" :true-value="[]" :value="item.cmcode">
                            <label :for="'muappchtype_'+item.cmcode">{{ item.codename }}</label>
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
                            <input type="checkbox" :id="'dadamembertype_'+item.cmcode" v-model="searchData.mumembertypeArr" :true-value="[]" :value="item.cmcode">
                            <label :for="'dadamembertype_'+item.cmcode">{{ item.codename }}</label>
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
                    <span class="total">전체 <strong>{{ count.totalcnt }}</strong>건</span>
                    <span>사용 <strong>{{ count.usecnt }}</strong>건</span>
                    <span>미사용 <strong>{{ count.unusecnt }}</strong>건</span>
                </div>
                <div class="btn-group fr">
                    <button type="button" class="btn blue-line" v-if="isWrite" @click="setIsTrash('F')">사용</button>
                    <button type="button" class="btn red-line" v-if="isWrite" @click="setIsTrash('T')">미사용</button>
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
                    <col width="2.5%" /><!-- checkbox -->
                    <col width="" /><!-- 프로모션명 -->
                    <col width="5%" /><!-- 유형 -->
                    <col width="4%" /><!-- 등급 -->
                    <col width="4%" /><!-- 적용채널 -->
                    <col width="4%" /><!-- 구분 -->
                    <col width="5%" /><!-- 할인 -->
                    <col width="7%" /><!-- 사은품 지급조건 -->
                    <col width="4%" /><!-- 대상 상품 -->
                    <col width="4%" /><!-- 구매수량조건 -->
                    <col width="4%" /><!-- 대상사은품 -->
                    <col width="4%" /><!-- 사은품선택 -->
                    <col width="6%" /><!-- 구매금액제한 -->
                    <col width="4.5%" /><!-- 적립금 -->
                    <col width="4%" /><!-- 사용여부 -->
                    <col width="6.5%" /><!-- 시작일자 -->
                    <col width="6.5%" /><!-- 종료일자 -->
                    <col width="5%" /><!-- 등록자 -->
                    <col width="6.5%" /><!-- 등록일자 -->
                    <col width="4%" /><!-- 진행상태 -->
                </colgroup>
                <thead>
                    <tr>
                        <th><input type="checkbox" id="chkall" v-model="isallchk" @change="checkAllList($event.target.checked)"/></th>
                        <th>프로모션명</th>
                        <th>유형</th>
                        <th>등급</th>
                        <th>적용채널</th>
                        <th>구분</th>
                        <th>할인</th>
                        <th>사은품 지급조건</th>
                        <th>대상상품
                            <button type="button" class="sort" :value="sortData.goodscnt" v-if="isRead"
                                :class="[{up : sortData.goodscnt === 'goodscnt_asc'}, {down : sortData.goodscnt === 'goodscnt_desc'}]"
                                @click="sortToggle(sortData.goodscnt)"></button>
                        </th>
                        <th>구매수량조건
                            <button type="button" class="sort" :value="sortData.giftgoodscnt" v-if="isRead"
                                :class="[{up : sortData.giftgoodscnt === 'giftgoodscnt_asc'}, {down : sortData.giftgoodscnt === 'giftgoodscnt_desc'}]"
                                @click="sortToggle(sortData.giftgoodscnt)"></button>
                        </th>
                        <th>대상사은품
                            <button type="button" class="sort" :value="sortData.giftcnt" v-if="isRead"
                                :class="[{up : sortData.giftcnt === 'giftcnt_asc'}, {down : sortData.giftcnt === 'giftcnt_desc'}]"
                                @click="sortToggle(sortData.giftcnt)"></button>
                        </th>
                        <th>사은품선택
                            <button type="button" class="sort" :value="sortData.giftselcnt" v-if="isRead"
                                :class="[{up : sortData.giftselcnt === 'giftselcnt_asc'}, {down : sortData.giftselcnt === 'giftselcnt_desc'}]"
                                @click="sortToggle(sortData.giftselcnt)"></button>
                        </th>
                        <th>구매금액제한</th>
                        <th>적립금</th>
                        <th>사용여부</th>
                        <th>시작일자
                            <button type="button" class="sort" :value="sortData.promostday" v-if="isRead"
                                :class="[{up : sortData.promostday === 'promostday_asc'}, {down : sortData.promostday === 'promostday_desc'}]"
                                @click="sortToggle(sortData.promostday)"></button>
                        </th>
                        <th>종료일자
                            <button type="button" class="sort" :value="sortData.promoedday" v-if="isRead"
                                :class="[{up : sortData.promoedday === 'promoedday_asc'}, {down : sortData.promoedday === 'promoedday_desc'}]"
                                @click="sortToggle(sortData.promoedday)"></button>
                        </th>
                        <th>등록자</th>
                        <th>등록일자
                            <button type="button" class="sort" :value="sortData.regdate" v-if="isRead"
                                :class="[{up : sortData.regdate === 'regdate_asc'}, {down : sortData.regdate === 'regdate_desc'}]"
                                @click="sortToggle(sortData.regdate)"></button>
                        </th>
                        <th>진행상태</th>
                    </tr>
                </thead>
                <tbody v-if="list.length > 0">
                    <tr v-for="item in list" :key="item.promoidx">
                        <td>
                            <input type="checkbox" :id="item.promoidx" v-model="checkedList" :value="item.promoidx" @change="checkList($event.target.checked)" v-if="item.promosttype!=$store.getters['ADMIN'].PROMO_ST_END"/>
                        </td>
                        <td class="left"><a href="javascript:void(0);" class="link" @click="goPromotionDetail(item.promoidx)">{{ item.promoname }}</a></td>
                        <td>{{ item.mumembertypename }}</td>
                        <td>{{ item.mumemlvtypename }}</td>
                        <td>{{ item.muappchtypename }}</td>
                        <td>{{ item.promodivtypename }}</td>
                        <td>
                            {{ item.ispercentname }}<br>
                            {{ (item.ispercent=='T' && !$util.isNull(item.dispercent))? '('+$util.maskComma(item.dispercent)+'%)' : '' }}
                            {{ (item.ispercent=='F' && !$util.isNull(item.disprice))? '('+$util.maskComma(item.disprice)+'원)' : '' }}
                        </td>
                        <td>{{ item.gifttermtypename }}</td>
                        <td>{{ $util.isNull(item.goodscnt)? '':$util.maskComma(item.goodscnt) }}</td>
                        <td>{{ $util.isNull(item.giftgoodscnt)? '':$util.maskComma(item.giftgoodscnt) }}</td>
                        <td>{{ $util.isNull(item.giftcnt)? '':$util.maskComma(item.giftcnt) }}</td>
                        <td>{{ $util.isNull(item.giftselcnt)? '':$util.maskComma(item.giftselcnt) }}</td>
                        <td>
                            {{ $util.isNull(item.orderfromprice)? '' : $util.maskComma(item.orderfromprice) }} 
                            {{ ($util.isNull(item.orderfromprice) && $util.isNull(item.ordertoprice))? '' : ' ~ ' }} 
                            {{ $util.isNull(item.ordertoprice)? '' : $util.maskComma(item.ordertoprice) }}
                        </td>
                        <td>{{ $util.isNull(item.reservepoint)? '':$util.maskComma(item.reservepoint) }}</td>
                        <td>{{ item.istrashname }}</td>
                        <td>{{ item.promostdate }}</td>
                        <td>{{ item.promoeddate }}</td>
                        <td>{{ item.regusername }}</td>
                        <td>{{ item.regdate }}</td>
                        <td>{{ item.promosttypename }}</td>
                    </tr>
                </tbody>
                <tbody v-else>
                    <tr><td colspan="20">조회 결과가 존재하지 않습니다.</td></tr>
                </tbody>
            </table>
            <div class="bottom-group">
                <common-page-navigator v-show="isRead" :pagingData="pagingData" v-on:setPagingData="setPagingData"/>
                <div class="btn-group">
                    <button type="button" v-if="isWrite" class="btn blue" @click="goPromotionRegist">프로모션 등록</button>
                </div>
            </div>
        </div>
        <PromotionRegist v-if="isPromoRegistShow" @closePopup="closePromoRegistPopup"></PromotionRegist>
        <PromotionDetail v-if="isPromoDetailShow" :activePromoIdx="activePromoIdx" @closePopup="closePromoDetailPopup"></PromotionDetail>
    </div>
    <!-- /컨텐츠 영역 -->
</template>

<script>
import CommonNavigator from '@views.admin/common/CommonNavigator.vue'
import CommonPageNavigator from "@views.admin/common/CommonPageNavigator.vue";
import CommonDatePicker from '@views.admin/common/CommonDatePicker.vue';
import PromotionRegist from '@views.admin/promotion/promotion/PromotionRegist.vue';
import PromotionDetail from '@views.admin/promotion/promotion/PromotionDetail.vue';

export default {
    name: 'admin.promotion.promotion.promotionlist',
    components: {
        CommonNavigator,
        CommonPageNavigator,
        CommonDatePicker,
        PromotionRegist,
        PromotionDetail
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
                sdate: '',              // 조회기간구분
                startDate: '',          // 조회시작날짜
                endDate: '',            // 조회종료날짜
                period: '',             // 조회기간
                istrash: '',            // 사용여부
                isallpromosttype: 'T',  // 프로모션상태전체여부
                promosttypeArr: [],     // 프로모션상태Array
                isallpromodivtype: 'T', // 프로모션구분전체여부
                promodivtypeArr: [],    // 프로모션구분Array
                isallchannel: 'T',      // 다중적용채널전체여부
                muappchtypeArr: [],     // 다중적용채널Array
                isallmember: 'T',       // 다중대상회원유형전체여부
                mumembertypeArr: [],    // 다중대상회원유형Array
                isallmemlv: 'T',        // 다중대상회원등급전체여부
                mumemlvtypeArr: [],     // 다중대상회원등급Array
                psort: 'regdate_desc'   // 정렬
            },
            pagingData: {
                pageCount: 20,      // 페이징 옵션(최대수)
                page: 1,            // 현재 페이지
                listCount: 0        // 총 수량
            },
            commonCode: {
                promosttype: [],    // 프로모션상태
                promodivtype: [],   // 프로모션구분
                muappchtype: [],    // 다중적용채널
                dadamembertype: [], // 다다픽회원유형
                memlvtype: [],      // 회원등급
            },
            sortData: {
                goodscnt : 'goodscnt_asc',
                giftgoodscnt : 'giftgoodscnt_asc',
                giftcnt : 'giftcnt_asc',
                giftselcnt : 'giftselcnt_asc',
                promostday: 'promostday_asc',
                promoedday: 'promoedday_asc',
                regdate: 'regdate_desc',
            },
            isPromoDetailShow: false,   // 프로모션상세 노출여부
            isPromoRegistShow: false,   // 프로모션등록 노출여부
            activePromoIdx: '',         // 프로모션일련번호
            list: [],                   // 프로모션 목록
            count: {                    // 프로모션 건수(전체, 사용, 미사용)
                totalcnt: 0,
                usecnt: 0,
                unusecnt: 0
            },
            isallchk: false,            // 목록 전체체크여부
            checkedList: [],             // 선택된 목록
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
            this.searchData.skey = '';
            this.searchData.sword = '';
            this.searchData.sdate = '';
            this.searchData.istrash = '';
            this.searchData.period = 'months_3';
            this.searchData.startDate = this.$util.getAddMonth(this.$util.getDate(), -3, '-');
            this.searchData.endDate = this.$util.getDate('-');
            this.searchData.isallpromosttype = 'T';
            this.searchData.isallpromodivtype = 'T';
            this.searchData.isallchannel = 'T';
            this.searchData.isallmember = 'T';
            this.searchData.isallmemlv = 'T';

            this.checkAllPromosttype();
            this.checkAllPromodivtype();
            this.checkAllAppchtype();
            this.checkAllMembertype();
            this.checkAllMemlvtype();
        },
        // 프로모션목록 조회
        searchList: function(page) {
            let params = Object.assign(this.searchData, this.pagingData);
            params.page = (typeof page === 'undefined') ? this.pagingData.page : 1;
            this.$http.post('/admin/promotion/promotion/list', params)
                .then(result => {
                    this.$util.debug(result);
                    let data = result.data;
                    this.list = data.list;
                    this.count = data.count;
                    this.pagingData.listCount = data.count.totalcnt;
                    this.checkedList = [];
                    this.isallchk = false;
                    this.$util.dataSetSearchParam(this);
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 공통코드 목록 조회
        getCommonCodeList: function() {
            let cmclassArr = ['PROMOSTTYPE', 'PROMODIVTYPE', 'MUAPPCHTYPE', 'DADAMEMBERTYPE', 'MEMLVTYPE'];
            this.$http.post('/common/code/map/list', {cmclass : cmclassArr, isloading: false})
                .then(result =>{
                    let data = result.data;
                    for (const [key] of Object.entries(data)) {
                        this.commonCode[key] = data[key];
                    }
                    // 검색조건 초기화
                    this.initSearchData();
                    this.$util.componentSetSearchParam(this);

                    if(this.isLink){
                      let linkParam = this.$route.params;
                      this.searchData.period = linkParam.period
                      this.searchData.startDate = linkParam.startdate;
                      this.searchData.endDate = linkParam.enddate;
                      if(typeof linkParam.promodivtype !== 'undefined'){
                        this.searchData.isallpromosttype = 'F';
                        this.searchData.promosttypeArr = ['PST002'];
                        this.searchData.istrash = 'F';
                        this.searchData.isallpromodivtype = 'F';
                        this.searchData.promodivtypeArr = [linkParam.promodivtype];
                      }

                      if(typeof linkParam.sdate !== 'undefined'){
                        this.searchData.sdate = linkParam.sdate;
                      }
                    }
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
        // 조회조건 - 프로모션상태 전체체크
        checkAllPromosttype: function() {
            let isAllCheck = this.searchData.isallpromosttype;
            this.searchData.promosttypeArr = [];
            if (isAllCheck == 'T') {
                for(let type of this.commonCode.promosttype){
                    this.searchData.promosttypeArr.push(type.cmcode);
                }
            }
        },
        // 조회조건 - 프로모션구분 전체체크
        checkAllPromodivtype: function() {
            let isAllCheck = this.searchData.isallpromodivtype;
            this.searchData.promodivtypeArr = [];
            if (isAllCheck == 'T') {
                for(let type of this.commonCode.promodivtype){
                    this.searchData.promodivtypeArr.push(type.cmcode);
                }
            }
        },
        // 조회조건 - 적용채널 전체체크
        checkAllAppchtype: function() {
            let isAllCheck = this.searchData.isallchannel;
            this.searchData.muappchtypeArr = [];
            if (isAllCheck == 'T') {
                for(let type of this.commonCode.muappchtype){
                    this.searchData.muappchtypeArr.push(type.cmcode);
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
        // 페이징데이터 세팅
        setPagingData(param){
            this.pagingData = param;
            this.searchList();
        },
        // 정렬
        sortToggle(key){
            let arr = key.split('_');
            let sortKey = arr[0];
            let sortOrder = (arr[1] === 'asc') ? 'desc' : 'asc';
            let sortName = sortKey + '_' + sortOrder;

            this.sortData[sortKey] = sortName;
            this.searchData.psort = sortName;

            this.searchList();
        },
        // 목록 전체체크
        checkAllList: function(value) {
            this.checkedList = [];
            if (value) {
                this.list.forEach(item => {
                    if ( item.promosttype != this.$store.getters['ADMIN'].PROMO_ST_END) {
                        this.checkedList.push(item.promoidx);
                    }
                });
            }
        },
        // 목록 개별체크
        checkList: function() {
            let listLen = this.list.filter(item => {
                return item.promosttype != this.$store.getters['ADMIN'].PROMO_ST_END;
            }).length;
            if (listLen > this.checkedList.length){
                this.isallchk = false;
            } else {
                this.isallchk = true;
            }
        },
        // 프로모션 사용여부 변경
        setIsTrash: function(value) {
            if (this.checkedList.length == 0) {
                alert('상태를 전환할 프로모션을 선택해주세요.');
                return;
            }
            if (confirm('상태를 전환 하시겠습니까?')) {
                let params = { istrash: value, promoidxlist: this.checkedList };
                this.$http.post('/admin/promotion/promotion/use/update', params)
                    .then(result => {
                        this.$util.debug(result);
                        if (result.statusCode == '200') {
                            alert('전환이 완료되었습니다.');
                            this.searchList();
                        } else {
                            alert('전환이 실패하였습니다.');
                        }
                    })
                    .catch(error => {
                        this.$util.debug(error);
                    });
            }
        },
        // 엑셀다운로드
        downloadExcel: function() {
            if (this.list.length == 0) {
                alert('다운로드할 내역이 존재하지 않습니다.');
                return;
            }
            let config = { responseType: 'arraybuffer' };
            this.$http.post('/admin/promotion/promotion/exceldown', this.searchData, config)
                .then(result => {
                    this.$util.debug(result);
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 프로모션등록 팝업 SHOW
        goPromotionRegist: function() {
            this.isPromoRegistShow = true;
        },
        // 프로모션등록 팝업 닫기
        closePromoRegistPopup: function(isreload) {
            this.isPromoRegistShow = false;
            if (isreload) {
                this.searchList();
            }
        },
        // 프로모션상세 팝업 SHOW
        goPromotionDetail: function(value) {
            this.activePromoIdx = value;
            this.isPromoDetailShow = true;
        },
        // 프로모션상세 팝업 닫기
        closePromoDetailPopup: function(isreload) {
            this.isPromoDetailShow = false;
            if (isreload) {
                this.searchList();
            }
        }
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
        // 프로모션상태
        'searchData.promosttypeArr': function(value) {
            if (value.length < this.commonCode.promosttype.length) {
                this.searchData.isallpromosttype = 'F';
            } else {
                this.searchData.isallpromosttype = 'T';
            }
        },
        // 프로모션구분
        'searchData.promodivtypeArr': function(value) {
            if (value.length < this.commonCode.promodivtype.length) {
                this.searchData.isallpromodivtype = 'F';
            } else {
                this.searchData.isallpromodivtype = 'T';
            }
        },
        // 다중적용채널
        'searchData.muappchtypeArr': function(value) {
            if (value.length < this.commonCode.muappchtype.length) {
                this.searchData.isallchannel = 'F';
            } else {
                this.searchData.isallchannel = 'T';
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