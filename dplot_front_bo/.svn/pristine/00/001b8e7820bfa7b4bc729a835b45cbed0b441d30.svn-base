<template>
    <div class="content m-leftmenu">
        <common-navigator></common-navigator>
        <div class="inner">
            <div class="boxing search-area">
                <dl>
                    <dt>직접검색</dt>
                    <dd>
                        <select v-model="searchData.skey">
                            <option v-for="item in searchData.skeyArr" :key="item.key" :value="item.key">{{ item.name }}</option>
                        </select>
                        <input type="text" v-model="searchData.sword" maxlength="200" @keyup.enter="searchList(1)"/>
                    </dd>
                </dl>
                <dl v-if="!isPartner">
                    <dt>파트너사</dt>
                    <dd>
                        <select style="width: 250px;" v-model="searchData.dealerno">
                            <option value="">파트너사 전체</option>
                            <option v-for="item in partnerList" :key="item.no" :value="item.no">{{ item.name }} </option>
                        </select>
                    </dd>
                </dl>
                <dl>
                    <dt>조회기간</dt>
                    <dd>
                        <select v-model="searchData.sdate">
                            <option value="reg">등록일</option>
                            <option value="mod">처리일</option>
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
                <!-- <dl v-if="!isPartner">
                    <dt>상품구분</dt>
                    <dd>
                        <div class="radio_wrap wide3">
                            <input type="radio" name="isdeal" id="isdealAll" v-model="searchData.isdeal" value=""/>
                            <label for="isdealAll">전체</label>
                            <input type="radio" name="isdeal" id="isdealF" v-model="searchData.isdeal" value="F"/>
                            <label for="isdealF">일반상품</label>
                            <input type="radio" name="isdeal" id="isdealT" v-model="searchData.isdeal" value="T"/>
                            <label for="isdealT">딜 상품</label>
                        </div>
                    </dd>
                </dl> -->
                <dl>
                    <dt>상태구분</dt>
                    <dd>
                        <div class="check-wrap">
                            <input type="checkbox" id="chkAllApprType" v-model="searchData.isallapprtype" true-value="T" false-value="F" @change="checkAllApprType">
                            <label for="chkAllApprType">전체</label>
                        </div>
                        <div class="check-wrap" v-for="item in commonCode.goodsapprtype" :key="item.cmcode">
                            <input type="checkbox" :id="'goodsapprtype_'+item.cmcode" v-model="searchData.goodsapprtypeArr" :true-value="[]" :value="item.cmcode"/>
                            <label :for="'goodsapprtype_'+item.cmcode">{{ item.codename }}</label>
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
                    <span v-if="!isPartner">요청 <strong>{{ count.gat002cnt }}</strong>건</span>
                    <span v-if="!isPartner">반려 <strong>{{ count.gat003cnt }}</strong>건</span>
                    <span v-if="!isPartner">승인 <strong>{{ count.gat004cnt }}</strong>건</span>
                </div>
                <div class="btn-group fr">
                    <button type="button" class="btn blue-line" @click="changeGoodsAppr($store.getters['ADMIN'].GOODS_STATUS_APPROVAL)" v-if="!isPartner && isWrite">승인</button>
                    <button type="button" class="btn red-line" @click="changeGoodsAppr($store.getters['ADMIN'].GOODS_STATUS_REJECT)" v-if="!isPartner && isWrite">반려</button>
                    <button type="button" class="btn red-line" @click="changeGoodsAppr($store.getters['ADMIN'].GOODS_STATUS_REQ)" v-if="isPartner && isWrite">승인요청</button>
                    <select v-model="pagingData.pageCount" v-if="isRead">
                        <option value="20">20개씩 보기</option>
                        <option value="50">50개씩 보기</option>
                        <option value="100">100개씩 보기</option>
                    </select>
                </div>
            </div>
            <table cellpadding="0" cellspacing="0" class="data-tb align-c">
                <colgroup v-if="!isPartner">
                    <col width="2%" /><!-- checkbox -->
                    <col width="2.5%" /><!-- No -->
                    <col width="5%" /><!-- 파트너사명 -->
                    <col width="3.5%" /><!-- 상품구분 -->
                    <col width="6.5%" /><!-- 상품코드 -->
                    <col width="62px" /><!-- 상품명(이미지) -->
                    <col width="" /><!-- 상품명 -->
                    <col width="5%" /><!-- 정상가 -->
                    <col width="5%" /><!-- 판매가 -->
                    <col width="5%" /><!-- 수수료율 -->
                    <col width="4%" /><!-- 상태 -->
                    <col width="4%" /><!-- MD -->
                    <col width="6%" /><!-- 등록일 -->
                    <col width="6%" /><!-- 처리일 -->
                </colgroup>
                <colgroup v-else>
                    <col width="2%" /><!-- checkbox -->
                    <col width="2.5%" /><!-- No -->
                    <col width="4%" /><!-- 상태 -->
                    <col width="6.5%" /><!-- 상품코드 -->
                    <col width="62px" /><!-- 상품명(이미지) -->
                    <col width="" /><!-- 상품명 -->
                    <col width="5%" /><!-- 정상가 -->
                    <col width="5%" /><!-- 판매가 -->
                    <col width="4%" /><!-- MD -->
                    <col width="6%" /><!-- 등록일 -->
                    <col width="6%" /><!-- 처리일 -->
                </colgroup>
                <thead>
                    <tr>
                        <th><input type="checkbox" id="chkall" v-model="isallchk" @change="checkAllGoodsList($event.target.checked)"/></th>
                        <th>No</th>
                        <th v-if="isPartner">상태
                            <button type="button" class="sort" :value="sortData.goodsapprtype" v-if="isRead"
                                :class="[{up : sortData.goodsapprtype=== 'goodsapprtype_asc'}, {down : sortData.goodsapprtype === 'goodsapprtype_desc'}]"
                                @click="sortToggle(sortData.goodsapprtype)"></button>
                        </th>
                        <th v-if="!isPartner">파트너사명</th>
                        <th v-if="!isPartner">상품구분</th>
                        <th>상품코드
                            <button type="button" class="sort" :value="sortData.code" v-if="isRead"
                                :class="[{up : sortData.code=== 'code_asc'}, {down : sortData.code === 'code_desc'}]"
                                @click="sortToggle(sortData.code)"></button>
                        </th>
                        <th colspan="2">상품명</th>
                        <th>정상가
                            <button type="button" class="sort" :value="sortData.marketprice" v-if="isRead"
                                :class="[{up : sortData.marketprice=== 'marketprice_asc'}, {down : sortData.marketprice === 'marketprice_desc'}]"
                                @click="sortToggle(sortData.marketprice)"></button>
                        </th>
                        <th>판매가
                            <button type="button" class="sort" :value="sortData.price" v-if="isRead"
                                :class="[{up : sortData.price=== 'price_asc'}, {down : sortData.price === 'price_desc'}]"
                                @click="sortToggle(sortData.price)"></button>
                        </th>
                        <th v-if="!isPartner">수수료율
                            <button type="button" class="sort" :value="sortData.rate" v-if="isRead"
                                :class="[{up : sortData.rate=== 'rate_asc'}, {down : sortData.rate === 'rate_desc'}]"
                                @click="sortToggle(sortData.rate)"></button>
                        </th>
                        <th v-if="!isPartner">상태</th>
                        <th>MD
                            <button type="button" class="sort" :value="sortData.mdname" v-if="isPartner && isRead"
                                :class="[{up : sortData.mdname=== 'mdname_asc'}, {down : sortData.mdname === 'mdname_desc'}]"
                                @click="sortToggle(sortData.mdname)"></button>
                        </th>
                        <th>등록일
                            <button type="button" class="sort" :value="sortData.regdate" v-if="isRead"
                                :class="[{up : sortData.regdate=== 'regdate_asc'}, {down : sortData.regdate === 'regdate_desc'}]"
                                @click="sortToggle(sortData.regdate)"></button>
                        </th>
                        <th>수정일
                            <button type="button" class="sort" :value="sortData.moddate" v-if="isRead"
                                :class="[{up : sortData.moddate=== 'moddate_asc'}, {down : sortData.moddate === 'moddate_desc'}]"
                                @click="sortToggle(sortData.moddate)"></button>
                        </th>
                    </tr>
                </thead>
                <tbody v-if="goodsList.length > 0">
                    <tr v-for="(item, index) in goodsList" :key="index">
                        <td>
                            <input type="checkbox" :id="item.goodsno" v-model="checkedList" :value="item.goodsno" @change="checkGoodsList($event.target.checked)"/>
                        </td>
                        <td>{{ loopNumberForPaging(index) }}</td>
                        <td v-if="isPartner">{{ item.goodsapprtypename }}</td>
                        <td v-if="!isPartner"><a href="javascript:void(0)" class="link" @click="goPartnerDetail(item.dealerno)">{{ item.dealername }}</a></td>
                        <td v-if="!isPartner">{{ item.isdealname }}</td>
                        <td>{{ item.goodscode }}</td>
                        <td>
                            <div class="img-thumb size60 link" @click="goFrontGoodsDetail(item.goodscode)" :class="{'no-image': $util.isNull(item.fullpath)}">
                                <img :src="item.fullpath" v-if="!$util.isNull(item.fullpath)">
                            </div>
                        </td>
                        <td class="left no-left">
                            <span class="small-txt">{{ item.fullcategoryname }}</span>
                            <a href="javascript:void(0)" class="dpb link" @click="item.isdeal=='T'? goDealDetail(item.goodsno) : goGoodsDetail(item.goodsno)">{{ item.goodsname }}</a>
                        </td>
                        <td class="right">{{ $util.maskComma(item.marketprice) }}</td>
                        <td class="right">{{ $util.maskComma(item.price) }}</td>
                        <td v-if="!isPartner">{{ item.commrate }}</td>
                        <td v-if="!isPartner">{{ item.goodsapprtypename }}</td>
                        <td>{{ item.mdname }}</td>
                        <td>{{ item.regdate }}</td>
                        <td><a href="javascript:void(0)" class="link" @click="goGoodsApprHistory(item.goodsno)">{{ item.moddate }}</a></td>
                    </tr>
                </tbody>
                <tbody v-else>
                    <tr><td :colspan="isPartner? '11':'14'">조회 결과가 존재하지 않습니다.</td></tr>
                </tbody>
            </table>
            <div class="bottom-group">
                <common-page-navigator :pagingData="pagingData" v-on:setPagingData="setPagingData"/>
            </div>
        </div>
        <PartnersApplyDetail v-if="isPartnerDetailShow" :activeUserNo="activeDealerNo" @closeDetail="closePartnerDetail"></PartnersApplyDetail>
        <GoodsDetail v-if="isGoodsDetailShow" :activeGoodsNo="activeGoodsNo" @closePopup="closeGoodsDetail"></GoodsDetail>
        <DealDetail v-if="isDealDetailShow" :activeGoodsNo="activeGoodsNo" @closePopup="closeDealDetail"></DealDetail>
    </div>
</template>

<script>
import CommonNavigator from '@/views/admin/common/CommonNavigator.vue'
import CommonDatePicker from '@views.admin/common/CommonDatePicker.vue';
import CommonPageNavigator from "@views.admin/common/CommonPageNavigator.vue";
import PartnersApplyDetail from "@views.admin/partners/apply/PartnersApplyDetail.vue";
import GoodsDetail from "@views.admin/goods/manage/GoodsDetail.vue";
import DealDetail from "@views.admin/goods/manage/DealDetail.vue";
import GoodsApprHistoryPopup from "@views.admin/goods/popup/GoodsApprHistoryPopup.vue";
import InputRejectRsPopup from "@views.admin/goods/popup/InputRejectRsPopup.vue";

export default {
    name: 'admin.goods.manage.goodsApprovalManage',
    components: {
        CommonNavigator,
        CommonDatePicker,
        CommonPageNavigator,
        PartnersApplyDetail,
        GoodsDetail,
        DealDetail
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
            isPartner: this.$util.isAuthorized(this.$store.getters['CONSTANTS'].PARTNER_USER),
            user: {},
            searchData: {
                skeyArr: [
                    {key: "", name: "전체", isShowPartner: true},
                    {key: "goodsname", name: "상품명", isShowPartner: true},
                    {key: "goodscode", name: "상품코드", isShowPartner: true},
                    {key: "dealername", name: "파트너사명", isShowPartner: false},
                    {key: "mdname", name: "MD명", isShowPartner: true},
                    {key: "brandname", name: "브랜드명", isShowPartner: false}
                ],
                skey: '',               //검색키워드
                sword: '',              //검색어
                ispbgoods: '',          //직매입여부
                dealerno: '',           //입점업체번호
                isdeal: 'F',            //딜여부
                isallapprtype: 'T',     //상품승인상태 전체체크여부
                goodsapprtypeArr: [],   //상품승인상태배열
                sdate: 'reg',           //기간구분(default: 등록일자)
                startDate: '',          //등록시작일자 
                endDate: '',            //등록종료일자
                period: 'months_3',     //기간
                psort: 'regdate_desc',  //정렬조건
                goodsselltypeArr: [
                    this.$store.getters['ADMIN'].GOODS_SELL_TYPE_GST001,
                    this.$store.getters['ADMIN'].GOODS_SELL_TYPE_GST002,
                    this.$store.getters['ADMIN'].GOODS_SELL_TYPE_GST003,
                    this.$store.getters['ADMIN'].GOODS_SELL_TYPE_GST004,
                    this.$store.getters['ADMIN'].GOODS_SELL_TYPE_GST005
                ]                       //상품판매상태배열(영구종료 제외)
            },
            pagingData: {
              pageCount: 20,        //페이징 옵션 (최대수)
              page: 1,              //현재 페이지
              listCount: 0          //총 페이지
            },
            sortData: {
                code : 'code_asc',
                marketprice : 'marketprice_asc',
                price : 'price_asc',
                rate : 'rate_asc',
                stock : 'stock_asc',
                hits : 'hits_asc',
                regdate : 'regdate_desc',
                moddate : 'moddate_asc',
                goodsapprtype : 'goodsapprtype_asc',
                mdname : 'mdname_asc'
            },
            commonCode: {
                goodsapprtype: []   //상품승인상태
            },
            isRead : false,
            isWrite : false,
            checkedList: [],
            activeDealerNo: null,
            activeGoodsNo: null,
            isPartnerDetailShow: false,     //파트너사상세팝업여부
            isGoodsDetailShow: false,       //상품상세팝업여부
            isDealDetailShow: false,        //딜상품상세팝업여부
            isallchk: false,        //전체체크여부
            partnerList: [],        //파트너사 목록
            goodsList: [],          //상품목록
            count: {
                totalcnt: 0,        //전체 건수
                gat002cnt: 0,       //승인요청 건수
                gat003cnt: 0,       //반려 건수
                gat004cnt: 0,       //상품승인완료 건수
            },
            isLink : false, //대시보드에서 링크를 타고왔는지 체크
        }
    },
    methods : {
        onInit: function() {
            if(typeof this.$route.params.type !== 'undefined' && this.$route.params.type === 'LINK'){
              this.isLink = true;
            }

            this.user = this.$util.getUser(this.$store.getters['CONSTANTS'].MANAGER_SESSION);
            this.getCommonCodeList();
        },
        // 검색조건 초기화
        initSearchData: function () {
            this.searchData.skey = '';
            this.searchData.sword = '';
            this.searchData.isdeal = 'F';
            this.searchData.isallapprtype = 'T';
            this.searchData.sdate = 'reg';
            this.searchData.startDate = this.$util.getAddMonth(this.$util.getDate(), -3, '-');
            this.searchData.endDate = this.$util.getDate('-');
            this.searchData.period = 'months_3';
            if (this.isPartner) {
                this.searchData.dealerno = this.user.no;
                for (let i=this.searchData.skeyArr.length-1; i>0; i--) {
                    if (!this.searchData.skeyArr[i].isShowPartner) {
                        this.searchData.skeyArr.splice(i, 1);
                    }
                }
            } else {
                this.searchData.dealerno = '';
                // 파트너목록 조회
                this.getPartnerList();
            }
            this.checkAllApprType();
        },
        // 공통코드 목록 조회
        getCommonCodeList: function() {
            let cmclassArr = ['GOODSAPPRTYPE'];
            this.$http.post('/common/code/map/list', {cmclass : cmclassArr, isloading: false})
                .then(result =>{
                    let data = result.data;
                    for (const [key] of Object.entries(data)) {
                        this.commonCode[key] = data[key];
                    }
                    // 마스터:임시저장 제외
                    if (!this.isPartner) {
                        this.commonCode.goodsapprtype.splice(0, 1);
                    }
                    // 검색조건 초기화
                    this.initSearchData();
                    this.$util.componentSetSearchParam(this);

                    if(this.isLink){
                      let linkParam = this.$route.params;
                      this.searchData.period = linkParam.period;
                      this.searchData.startDate = linkParam.startdate;
                      this.searchData.endDate = linkParam.enddate;
                      this.searchData.goodsapprtypeArr = [linkParam.goodsapprtype];
                    }

                    // 목록 조회
                    // this.searchList();	// 최초 로딩 시 조회되지 않도록 김태선님이 요청(James, 2022-09-28)
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 파트너사 목록 조회
        getPartnerList: function() {
            let params = this.searchData;
            params.isloading = false;
            this.$http.post('/admin/goods/manage/partner/list', params)
                .then(result => {
                    this.$util.debug(result);
                    this.partnerList = result.data.list;
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 상품승인상태 전체체크
        checkAllApprType: function() {
            let isAllCheck = this.searchData.isallapprtype;
            this.searchData.goodsapprtypeArr = [];
            if (isAllCheck == 'T') {
                for (let i=0; i<this.commonCode.goodsapprtype.length; i++) {
                    let item = this.commonCode.goodsapprtype[i];
                    this.searchData.goodsapprtypeArr.push(item.cmcode);

                }
            }
        },
        // 시작날짜 picker 콜백 함수
        onChangeStartDate(value) {
            this.searchData.startDate = value;
        },
        // 종료날짜 picker 콜백 함수
        onChangeEndDate(value) {
            this.searchData.endDate = value;
        },
        // 페이징데이터 세팅
        setPagingData(param){
            this.pagingData = param;
            this.searchList();
        },
        // 정렬
        sortToggle(key){
            if (!this.isRead) return;
            
            let arr = key.split("_");
            let sortKey = arr[0];
            let sortOrder = (arr[1] === 'asc') ? 'desc' : 'asc';
            let sortName = sortKey + '_' + sortOrder;

            this.sortData[sortKey] = sortName;
            this.searchData.psort = sortName;

            this.searchList();
        },
        // 상품목록 전체체크
        checkAllGoodsList: function(value) {
            this.checkedList = [];
            if (value) {
                this.goodsList.forEach(item => {
                    this.checkedList.push(item.goodsno);
                });
            }
        },
        // 상품목록 개별체크
        checkGoodsList: function() {
            if (this.goodsList.length > this.checkedList.length){
                this.isallchk = false;
            } else {
                this.isallchk = true;
            }
        },
        // 목록 조회
        searchList: function(page) {
            let params = Object.assign(this.searchData, this.pagingData);
            params.page = (typeof page === 'undefined') ? this.pagingData.page : 1;
            params.isloading = true;
            this.$http.post('/admin/goods/manage/list', params)
                .then(result => {
                    this.$util.debug(result);
                    let data = result.data;
                    this.goodsList = data.list;
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
        // 승인상태 수정
        changeGoodsAppr: function(value) {
            if (this.checkedList.length == 0) {
                alert("승인상태를 변경할 상품을 선택해주세요.");
                return;
            }
            if (value == this.$store.getters['ADMIN'].GOODS_STATUS_APPROVAL) {
                for (let i=0; i<this.checkedList.length; i++) {
                    for (let j=0; j<this.goodsList.length; j++) {
                        if (this.checkedList[i] === this.goodsList[j].goodsno) {
                            let goodsapprtype = this.goodsList[j].goodsapprtype;
                            if (goodsapprtype != this.$store.getters['ADMIN'].GOODS_STATUS_REQ) {
                                alert('승인은 \'승인요청\'상태에서만 가능합니다. 확인후 진행해주세요.');
                                return;
                            }
                        }
                    }
                }
                let params = {
                    goodsnolist: this.checkedList,
                    goodsapprtype: value,
                    contents : '승인합니다.'
                }
                this.updateGoodsApprType(params);
            } else if (value == this.$store.getters['ADMIN'].GOODS_STATUS_REJECT) {
                for (let i=0; i<this.checkedList.length; i++) {
                    for (let j=0; j<this.goodsList.length; j++) {
                        if (this.checkedList[i] === this.goodsList[j].goodsno) {
                            let goodsapprtype = this.goodsList[j].goodsapprtype;
                            if (goodsapprtype != this.$store.getters['ADMIN'].GOODS_STATUS_REQ) {
                                alert('반려는 \'승인요청\'상태에서만 가능합니다. 확인후 진행해주세요.');
                                return;
                            }
                        }
                    }
                }
                this.$eventBus.$emit('modalShow', InputRejectRsPopup, null,
                    (result) => {
                            let params = {
                                goodsnolist: this.checkedList,
                                goodsapprtype: value,
                                contents : result.data
                            }
                            this.updateGoodsApprType(params);
                    }
                );
            } else if (value == this.$store.getters['ADMIN'].GOODS_STATUS_REQ) {
                for (let i=0; i<this.checkedList.length; i++) {
                    for (let j=0; j<this.goodsList.length; j++) {
                        if (this.checkedList[i] === this.goodsList[j].goodsno) {
                            let goodsapprtype = this.goodsList[j].goodsapprtype;
                            if (goodsapprtype != this.$store.getters['ADMIN'].GOODS_STATUS_TEMP && goodsapprtype != this.$store.getters['ADMIN'].GOODS_STATUS_REJECT) {
                                alert('승인요청은 \'임시저장\' 또는 \'반려\'상태에서만 가능합니다. 확인후 진행해주세요.');
                                return;
                            }
                        }
                    }
                }
                let params = {
                    goodsnolist: this.checkedList,
                    goodsapprtype: value,
                    istempsave: 'F',
                    contents : '요청합니다.'
                }
                this.updateGoodsApprType(params);
            }
        },
        updateGoodsApprType: function(params) {
            let msg = '승인이';
            if (params.goodsapprtype == this.$store.getters['ADMIN'].GOODS_STATUS_REJECT) {
                msg = '반려가';
            } else if (params.goodsapprtype == this.$store.getters['ADMIN'].GOODS_STATUS_REQ) {
                msg = '승인요청이';
                params.istempsave = 'F';
            }
            if (this.isPartner) {
                params.dealerno = this.user.no;
            }
            this.$http.post("/admin/goods/manage/apprtype/update", params)
                .then(result => {
                    this.$util.debug(result);
                    if (result.statusCode == '200') {
                        alert(msg + " 완료되었습니다.");
                        this.isallchk = false;
                        this.searchList();
                    } else {
                        alert(msg + " 실패하였습니다.");
                    }
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        loopNumberForPaging(index) {
            if(this.pagingData.page > 1){
                let page = this.pagingData.page - 1;
                return (index+1) + (page * this.pagingData.pageCount);
            } else {
                return (index+1);
            }
        },
        // 파트너사 상세정보
        goPartnerDetail: function(value) {
            this.activeDealerNo = value;
            this.isPartnerDetailShow = true;
        },
        closePartnerDetail: function () {
            this.isPartnerDetailShow = false;
        },
        // 상품 상세정보
        goGoodsDetail: function(value) {
            this.activeGoodsNo = value;
            this.isGoodsDetailShow = true;
        },
        closeGoodsDetail: function (isreload) {
            this.isGoodsDetailShow = false;
            if (isreload === true) {
                this.searchList();
            }
        },
        // 딜상품 상세정보
        goDealDetail: function(value) {
            this.activeGoodsNo = value;
            this.isDealDetailShow = true;
        },
        closeDealDetail: function (isreload) {
            this.isDealDetailShow = false;
            if (isreload === true) {
                this.searchList();
            }
        },
        // 상품변경이력
        goGoodsApprHistory: function(value) {
            this.$eventBus.$emit('modalShow', GoodsApprHistoryPopup, { goodsno: value }, null);
        },
        // Front 화면으로 이동
        goFrontGoodsDetail: function(value) {
            window.open(process.env.VUE_APP_PC_GOODS_DETAIL_URL + value, "_blank");
        }
    },
    watch: {
        // 상품승인상태
        'searchData.goodsapprtypeArr': function(value) {
            if (this.$util.isNull(value)) return;
            if (value.length < this.commonCode.goodsapprtype.length) {
                this.searchData.isallapprtype = 'F';
            } else {
                this.searchData.isallapprtype = 'T';
            }
        },
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
        }
    }
}
</script>