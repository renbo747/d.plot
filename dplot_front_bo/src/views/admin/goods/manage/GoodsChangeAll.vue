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
                <dl>
                    <dt>판매구분</dt>
                    <dd>
                        <div class="radio_wrap wide3">
                            <input type="radio" name="ispbgoods" id="ispbgoodsAll" v-model="searchData.ispbgoods" value=""/>
                            <label for="ispbgoodsAll">전체</label>
                            <input type="radio" name="ispbgoods" id="ispbgoodsT" v-model="searchData.ispbgoods" value="T" @click="searchData.dealerno=''"/>
                            <label for="ispbgoodsT">직매입</label>
                            <input type="radio" name="ispbgoods" id="ispbgoodsF" v-model="searchData.ispbgoods" value="F"/>
                            <label for="ispbgoodsF">위탁</label>
                        </div>
                        <select :disabled="!$util.isNull(searchData.ispbgoods) && searchData.ispbgoods!='F'" v-model="searchData.dealerno">
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
                <dl>
                    <dt>판매상태</dt>
                    <dd>
                        <div class="check-wrap">
                            <input type="checkbox" id="chkAllSellType" v-model="searchData.isallselltype" true-value="T" false-value="F" @change="checkAllSellType">
                            <label for="chkAllSellType">전체</label>
                        </div>
                        <div class="check-wrap" v-for="item in commonCode.goodsselltype" :key="item.cmcode">
                            <input type="checkbox" :id="'goodsselltype_'+item.cmcode" v-model="searchData.goodsselltypeArr" :true-value="[]" :value="item.cmcode"/>
                            <label :for="'goodsselltype_'+item.cmcode">{{ item.codename }}</label>
                        </div>
                    </dd>
                </dl>
            </div>
            <div class="btn-group" v-if="isRead">
                <button type="button" class="btn big blue" @click="searchList(1)">검색</button>
                <button type="button" class="btn big gray" @click="initSearchData">초기화</button>
            </div>
            <div class="caption-group mt10 clearfix" v-if="isRead">
                <div class="total-group fl">
                    <span class="total">전체 <strong>{{ totalcnt }}</strong>건</span>
                </div>
                <div class="btn-group fr">
                    <select v-model="ckey" v-if="isWrite">
                        <option value="">변경항목 선택</option>
                        <option v-for="(value, name, index) in ckeyObject" :key="index" :value="value.key">{{ value.name }}</option>
                    </select>
                    <button type="button" class="btn blue ml3" v-if="isWrite" @click="goGoodsChange">일괄변경</button>
                    <select v-model="pagingData.pageCount">
                        <option value="20">20개씩 보기</option>
                        <option value="50">50개씩 보기</option>
                        <option value="100">100개씩 보기</option>
                    </select>
                </div>
            </div>
            <table cellpadding="0" cellspacing="0" class="data-tb align-c">
                <colgroup>
                    <col width="2%" /><!-- checkbox -->
                    <col width="2.5%" /><!-- No -->
                    <col width="3.5%" /><!-- 판매구분 -->
                    <col width="5%" /><!-- 파트너사명 -->
                    <col width="6%" /><!-- 상품코드 -->
                    <col width="62px" /><!-- 상품명(이미지) -->
                    <col width="%" /><!-- 상품명 -->
                    <col width="5.5%" /><!-- 정상가 -->
                    <col width="5.5%" /><!-- 판매가 -->
                    <col width="4%" /><!-- 판매상태 -->
                    <col width="4%" /><!-- 전시상태 -->
                    <col width="4%" /><!-- MD -->
                    <col width="6%" /><!-- 등록일 -->
                    <col width="6%" /><!-- 수정일 -->
                </colgroup>
                <thead>
                    <tr>
                        <th><input type="checkbox" id="chkall" v-model="isallchk" @change="checkAllGoodsList($event.target.checked)"/></th>
                        <th>No</th>
                        <th>판매구분</th>
                        <th>파트너사명</th>
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
                        <th>판매상태</th>
                        <th>전시상태</th>
                        <th>MD</th>
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
                    <tr v-for="(item, index) in goodsList" :key="index" :class="{'bg gray': item.goodsselltype == $store.getters['ADMIN'].GOODS_SELL_TYPE_GST006}">
                        <td>
                            <input type="checkbox" v-if="item.goodsselltype != $store.getters['ADMIN'].GOODS_SELL_TYPE_GST006"
                                :id="item.goodsno" v-model="checkedList" :value="item.goodsno" @change="checkGoodsList($event.target.checked)"/>
                        </td>
                        <td>{{ loopNumberForPaging(index) }}</td>
                        <td>{{ item.ispbgoodsname }}</td>
                        <td><a href="javascript:void(0)" class="link" @click="goPartnerDetail(item.dealerno)">{{ item.dealername }}</a></td>
                        <td>{{ item.goodscode }}</td>
                        <td>
                            <div class="img-thumb size60 link" @click="goFrontGoodsDetail(item.goodscode)" :class="{'no-image': $util.isNull(item.fullpath)}">
                                <img :src="item.fullpath" v-if="!$util.isNull(item.fullpath)">
                            </div>
                        </td>
                        <td class="left no-left">
                            <span class="small-txt">{{ item.fullcategoryname }}</span>
                            <a href="javascript:void(0)" class="dpb link" @click="goGoodsDetail(item.goodsno)"
                                :class="{'middle-line': item.goodsselltype == $store.getters['ADMIN'].GOODS_SELL_TYPE_GST006}">{{ item.goodsname }}</a>
                        </td>
                        <td class="right">{{ $util.maskComma(item.marketprice) }}</td>
                        <td class="right">{{ $util.maskComma(item.price) }}</td>
                        <td>{{ item.goodsselltypename }}</td>
                        <td>{{ item.isdisplayname }}</td>
                        <td>{{ item.mdname }}</td>
                        <td>{{ item.regdate }}</td>
                        <td><a href="javascript:void(0)" class="link" @click="goGoodsHistory(item.goodsno)">{{ item.moddate }}</a></td>
                    </tr>
                </tbody>
                <tbody v-else>
                    <tr><td colspan="14">조회 결과가 존재하지 않습니다.</td></tr>
                </tbody>
            </table>
            <div class="bottom-group">
                <common-page-navigator :pagingData="pagingData" v-on:setPagingData="setPagingData"/>
            </div>
        </div>
        <GoodsDetail v-if="isGoodsDetailShow" :activeGoodsNo="activeGoodsNo" @closePopup="closeGoodsDetail"></GoodsDetail>
        <PartnersApplyDetail v-if="isPartnerDetailShow" :activeUserNo="activeDealerNo" @closeDetail="closePartnerDetail"></PartnersApplyDetail>
        <GoodsChangeBasicInfo  v-if="ckeyObject.basic.isShow"    :checkedList="checkedList" :ckey="ckey" @closePopup="closeGoodsChange"></GoodsChangeBasicInfo>
        <GoodsChangeSellInfo   v-if="ckeyObject.sell.isShow"     :checkedList="checkedList" :ckey="ckey" @closePopup="closeGoodsChange"></GoodsChangeSellInfo>
        <GoodsChangeImageInfo  v-if="ckeyObject.image.isShow"    :checkedList="checkedList" :ckey="ckey" @closePopup="closeGoodsChange"></GoodsChangeImageInfo>
        <GoodsChangeDelivInfo  v-if="ckeyObject.delivery.isShow" :checkedList="checkedList" :ckey="ckey" :dealerno="activeDealerNo" @closePopup="closeGoodsChange"></GoodsChangeDelivInfo>
        <GoodsChangeNotifyInfo v-if="ckeyObject.notify.isShow"   :checkedList="checkedList" :ckey="ckey" @closePopup="closeGoodsChange"></GoodsChangeNotifyInfo>
        <GoodsChangeEtcInfo    v-if="ckeyObject.etc.isShow"      :checkedList="checkedList" :ckey="ckey" @closePopup="closeGoodsChange"></GoodsChangeEtcInfo>
    </div>
</template>

<script>
import CommonNavigator from '@/views/admin/common/CommonNavigator'
import CommonDatePicker from '@views.admin/common/CommonDatePicker.vue';
import CommonPageNavigator from '@views.admin/common/CommonPageNavigator.vue';
import PartnersApplyDetail from '@views.admin/partners/apply/PartnersApplyDetail.vue';
import GoodsDetail from '@views.admin/goods/manage/GoodsDetail.vue';
import GoodsChangeBasicInfo from '@views.admin/goods/manage/GoodsChangeBasicInfo.vue';
import GoodsChangeSellInfo from '@views.admin/goods/manage/GoodsChangeSellInfo.vue';
import GoodsChangeImageInfo from '@views.admin/goods/manage/GoodsChangeImageInfo.vue';
import GoodsChangeDelivInfo from '@views.admin/goods/manage/GoodsChangeDelivInfo.vue';
import GoodsChangeNotifyInfo from '@views.admin/goods/manage/GoodsChangeNotifyInfo.vue';
import GoodsChangeEtcInfo from '@views.admin/goods/manage/GoodsChangeEtcInfo.vue';
import GoodsHistoryPopup from '@views.admin/goods/popup/GoodsHistoryPopup.vue';

export default {
    name: 'admin.goods.manage.goodsChangeAll',
    components: {
        CommonNavigator,
        CommonDatePicker,
        CommonPageNavigator,
        PartnersApplyDetail,
        GoodsDetail,
        GoodsChangeBasicInfo,
        GoodsChangeSellInfo,
        GoodsChangeImageInfo,
        GoodsChangeDelivInfo,
        GoodsChangeNotifyInfo,
        GoodsChangeEtcInfo
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
                skeyArr: [
                    {key: '', name: '전체'},
                    {key: 'goodsname', name: '상품명'},
                    {key: 'goodscode', name: '상품코드'},
                    {key: 'dealername', name: '파트너사명'},
                    {key: 'mdname', name: 'MD명'}
                ],
                goodsapprtypeArr: [
                    this.$store.getters['ADMIN'].GOODS_STATUS_APPROVAL
                ],                      //상품승인상태(승인완료)
                istempsave: 'F',        //임시저장여부
                skey: '',               //검색키워드
                sword: '',              //검색어
                ispbgoods: '',          //직매입여부
                dealerno: '',           //입점업체번호
                isdeal: 'F',            //딜여부
                isallselltype: 'T',     //상품판매상태 전체체크여부
                goodsselltypeArr: '',   //상품판매상태Array
                sdate: 'reg',           //기간구분(default: 등록일자)
                startDate: '',          //등록시작일자 
                endDate: '',            //등록종료일자
                period: 'months_3',     //기간
                psort: 'regdate_desc'   //정렬조건
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
                regdate : 'regdate_desc',
                moddate : 'moddate_asc'
            },
            commonCode: {
                goodsselltype: []   //상품판매상태
            },
            isRead : false,
            isWrite : false,
            checkedList: [],            //선택된 상품번호 목록
            activeDealerNo: null,       //팝업에 넘어가는 입점업체번호
            activeGoodsNo: null,        //팝업에 넘어가는 상품번호
            isPartnerDetailShow: false, //파트너사상세팝업여부
            isGoodsDetailShow: false,   //상품상세팝업여부
            ckey: '',           //변경항목
            ckeyObject: {
                basic:    {key: 'basic',    name: '기본정보',     isShow: false },
                sell:     {key: 'sell',     name: '판매정보',     isShow: false },
                image:    {key: 'image',    name: '이미지정보',   isShow: false },
                delivery: {key: 'delivery', name: '배송정보',     isShow: false },
                notify:   {key: 'notify',   name: '상품정보고시', isShow: false },
                etc:      {key: 'etc',      name: '추가정보',     isShow: false }
            },                  //변경항목목록
            isallchk: false,    //전체체크여부
            partnerList: [],    //파트너사 목록
            goodsList: [],      //상품목록
            totalcnt: 0         //전체 건수
        }
    },
    methods: {
        // 화면초기화
        onInit: function() {
            this.getCommonCodeList();
            this.getPartnerList();
        },
        // 공통코드 목록 조회
        getCommonCodeList: function() {
            let cmclassArr = ['GOODSSELLTYPE'];
            this.$http.post('/common/code/map/list', {cmclass : cmclassArr, isloading: false})
                .then(result =>{
                    let data = result.data;
                    for (const [key] of Object.entries(data)) {
                        this.commonCode[key] = data[key];
                    }
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
        // 검색조건 초기화
        initSearchData: function () {
            this.searchData.skey = '';
            this.searchData.sword = '';
            this.searchData.ispbgoods = '';
            this.searchData.dealerno = '';
            this.searchData.isallselltype = 'T';
            this.searchData.sdate = 'reg';
            this.searchData.startDate = this.$util.getAddMonth(this.$util.getDate(), -3, '-');
            this.searchData.endDate = this.$util.getDate('-');
            this.searchData.period = 'months_3';
            this.checkAllSellType();
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
        // 상품구분상태 전체체크
        checkAllSellType: function() {
            let isAllCheck = this.searchData.isallselltype;
            this.searchData.goodsselltypeArr = [];
            if (isAllCheck == 'T') {
                for(let type of this.commonCode.goodsselltype){
                    this.searchData.goodsselltypeArr.push(type.cmcode);
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
            
            let arr = key.split('_');
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
                    if (item.goodsselltype != this.$store.getters['ADMIN'].GOODS_SELL_TYPE_GST006) {
                        this.checkedList.push(item.goodsno);
                    }
                });
            }
        },
        // 상품목록 개별체크
        checkGoodsList: function() {
            let endCnt = this.goodsList.filter(item => {
                return item.goodsselltype != this.$store.getters['ADMIN'].GOODS_SELL_TYPE_GST006;
            }).length;
            if (endCnt > this.checkedList.length){
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
                    this.totalcnt = data.count.totalcnt;
                    this.pagingData.listCount = data.count.totalcnt;
                    this.checkedList = [];
                    this.isallchk = false;
                    this.$util.dataSetSearchParam(this);
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 일괄변경
        goGoodsChange: function() {
            // 선택항목 체크
            if (this.checkedList.length == 0) {
                alert('변경할 상품을 선택해주세요.');
                return;
            }
            // 변경항목 선택여부 체크
            if (this.$util.isNull(this.ckey)) {
                alert("변경항목을 선택해주세요.");
                return;
            }
            // 영구종료 상품 불가
            let targetList = [];
            for (let i=0; i<this.checkedList.length; i++) {
                let checkedGoodsNo = this.checkedList[i];
                for (let j=0; j<this.goodsList.length; j++) {
                    let item = this.goodsList[j];
                    if (item.goodsno == checkedGoodsNo) {
                        if (item.goodsselltype === this.$store.getters['ADMIN'].GOODS_SELL_TYPE_GST006) {
                            alert('영구종료된 상품은 수정이 불가능합니다. 확인후 진행해주세요.');
                            return;
                        }
                        targetList.push(item);
                    }
                }
            }
            // 배송정보는 같은 파트너사인지 체크
            if (this.ckey === 'delivery') {
                for (let i=0; i<targetList.length-1; i++) {
                    let checkedItem = targetList[i];
                    for (let j=i+1; j<targetList.length; j++) {
                        let item = targetList[j];
                        if (item.ispbgoods != checkedItem.ispbgoods) {
                            alert("같은 판매구분 상품을 선택해주세요.");
                            return;
                        }
                        if (item.dealerno != checkedItem.dealerno) {
                            alert("같은 파트너사 상품을 선택해주세요.");
                            return;
                        }
                    }
                }
                this.activeDealerNo = targetList[0].dealerno;
            }
            this.ckeyObject[this.ckey].isShow = true;
        },
        loopNumberForPaging(index) {
            if(this.pagingData.page > 1){
                let page = this.pagingData.page - 1;
                return (index+1) + (page * this.pagingData.pageCount);
            } else {
                return (index+1);
            }
        },
        closeGoodsChange: function(isreload) {
            this.ckeyObject[this.ckey].isShow = false;
            if (isreload) {
                this.searchList();
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
            if (isreload) {
                this.searchList();
            }
        },
        // 상품변경이력
        goGoodsHistory: function(value) {
            this.$eventBus.$emit('modalShow', GoodsHistoryPopup, { goodsno: value }, null);
        },
        // Front 화면으로 이동
        goFrontGoodsDetail: function(value) {
            window.open(process.env.VUE_APP_PC_GOODS_DETAIL_URL + value, "_blank");
        }
    },
    watch: {
        // 상품판매상태
        'searchData.goodsselltypeArr': function(value) {
            if (value.length < this.commonCode.goodsselltype.length) {
                this.searchData.isallselltype = 'F';
            } else {
                this.searchData.isallselltype = 'T';
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