<template>
    <!-- 컨텐츠 영역 -->
    <div class="content m-leftmenu">
        <common-navigator></common-navigator>
        <GiftDetail v-if="showDetail" v-bind:giftidx="giftidx" v-on:closeDetail="closeDetail"></GiftDetail>
        <GiftRegist v-if="showRegist" v-on:closeRegist="closeRegist"></GiftRegist>
        <div class="inner">
            <div class="boxing search-area">
                <dl>
                    <dt>직접검색</dt>
                    <dd>
                        <select v-model="searchData.skey">
                            <option value="">전체</option>
                            <option value="giftcode">사은품코드</option>
                            <option value="giftname">사은품명</option>
                        </select>
                        <input type="text" v-model="searchData.sword" @keyup.enter="onSearch(1)"/>
                    </dd>
                </dl>
                <dl>
                    <dt>등록기간</dt>
                    <dd>
                        <CommonDatePicker :value="searchData.startdate" @change="onChangeStartDate"/>
                        <span>-</span>
                        <CommonDatePicker :value="searchData.enddate" @change="onChangeEndDate"/> 
                        <div class="radio_wrap">
                            <input type="radio" name="period" v-model="searchData.period" id="rd1" value='-1'/><label for="rd1">어제</label>
                            <input type="radio" name="period" v-model="searchData.period" id="rd2" value='0'/><label for="rd2">오늘</label>
                            <input type="radio" name="period" v-model="searchData.period" id="rd3" value='7'/><label for="rd3">일주일</label>
                            <input type="radio" name="period" v-model="searchData.period" id="rd4" value='1'/><label for="rd4">1개월</label>
                            <input type="radio" name="period" v-model="searchData.period" id="rd5" value='3' checked/><label for="rd5">3개월</label>
                            <input type="radio" name="period" v-model="searchData.period" id="rd6" value='6'/><label for="rd6">6개월</label>
                        </div>
                    </dd>
                </dl>
                <dl>
                    <dt>사용여부</dt>
                    <dd>
                        <div class="radio_wrap wide3">
                            <input type="radio" v-model="searchData.istrash" name="group02" id="group21" value="" checked/><label for="group21">전체</label>
                            <input type="radio" v-model="searchData.istrash" name="group02" id="group22" value="F" /><label for="group22">사용</label>
                            <input type="radio" v-model="searchData.istrash" name="group02" id="group23" value="T" /><label for="group23">미사용</label>
                        </div>
                    </dd>
                </dl>
            </div>
            <div class="btn-group">
                <button type="button" class="btn big blue" v-if="isRead" @click="onSearch(1)">검색</button>
                <button type="button" class="btn big gray" v-if="isRead" @click="onSearchDataReset">초기화</button>
            </div>
            <div class="caption-group mt10 clearfix">
                <div class="total-group fl">
                    <span class="total">전체 <strong>{{statelist.total_cnt}}</strong>건</span>
                    <span>사용 <strong>{{statelist.use_cnt}}</strong>건</span>
                    <span>미사용 <strong>{{statelist.trash_cnt}}</strong>건</span>
                </div>
                <div class="btn-group fr">
                    <button type="button" v-if="isWrite" class="btn blue-line" @click="onChangeUse('F')">사용</button>
                    <button type="button" v-if="isWrite" class="btn red-line" @click="onChangeUse('T')">미사용</button>
                    <button type="button" v-if="isRead" class="btn green-line" @click="fnExcelDownload"><i class="icon-excel"></i>엑셀다운로드</button>
                    <select v-model="pagingData.pageCount" v-if="isRead">
                        <option value="20">20개씩 보기</option>
                        <option value="50">50개씩 보기</option>
                        <option value="100">100개씩 보기</option>
                    </select>
                </div>
            </div>
            <table cellpadding="0" cellspacing="0" class="data-tb align-c">
                <colgroup>
                    <col width="4%" /><!-- checkbox -->
                    <col width="12%" /><!-- 사은품코드 -->
                    <col width="62px" /><!-- 사은품명(이미지) -->
                    <col width="" /><!-- 사은품명 -->
                    <col width="6%" /><!-- 연결 -->
                    <col width="6%" /><!-- 재고 -->
                    <col width="6%" /><!-- 사용여부 -->
                    <col width="8%" /><!-- 등록일자 -->
                </colgroup>
                <thead>
                    <tr>
                        <th><input type="checkbox" id="chkall" v-model="isChecked" @change="onCheckAll($event.target.checked)"/></th>
                        <th>사은품코드
                            <button type="button" v-if="isRead" 
                                    :value="sortData.giftcode" 
                                    class="sort"
                                    :class="[{up : sortData.giftcode === 'giftcode_asc'}, {down : sortData.giftcode === 'giftcode_desc'}]"
                                    @click="sortToggle(sortData.giftcode)">
                            </button>
                        </th>
                        <th colspan="2">상품명</th>
                        <th>연결</th>
                        <th>재고
                            <button type="button" v-if="isRead" 
                                    :value="sortData.stock" 
                                    class="sort"
                                    :class="[{up : sortData.stock === 'stock_asc'}, {down : sortData.stock === 'stock_desc'}]"
                                    @click="sortToggle(sortData.stock)">
                            </button>
                        </th>
                        <th>사용여부</th>
                        <th>등록일자
                            <button type="button" v-if="isRead" 
                                    :value="sortData.regdate" 
                                    class="sort"
                                    :class="[{up : sortData.regdate === 'regdate_asc'}, {down : sortData.regdate === 'regdate_desc'}]"
                                    @click="sortToggle(sortData.regdate)">
                            </button>    
                        </th>
                    </tr>
                </thead>
                <tbody v-if="this.listData.length > 0">
                    <tr v-for="(row, index) in this.listData" :key="index">
                        <td><input type="checkbox" v-model="checkedData" :id="'chk'+index" :value="row"/></td>
                        <td>{{row.giftcode}}</td>
                        <td>
                            <div class="img-thumb size60" :class="{'no-image': $util.isNull(row.fullpath)}">
                                <img :src="row.fullpath" v-if="!$util.isNull(row.fullpath)">
                            </div>
                        </td>
                        <td class="left no-left">
                            <a class="link" @click="goDetail(row.giftidx)">{{row.giftname}}</a>
                        </td>
                        <td>{{row.erpgiftcnt}}</td>
                        <td>{{row.giftstockcnt}}</td>
                        <td>{{row.istrash === 'F' ? '사용' : '미사용'}}</td>
                        <td>{{row.regdate}}</td>
                    </tr>
                </tbody>
                <tbody v-else>
                    <tr><td colspan="8">대상 사은품이 없습니다.</td></tr>
                </tbody>
            </table>
            <div class="bottom-group">
                <div class="paging">
                    <CommonPageNavigator v-show="isRead" v-bind:pagingData="this.pagingData"
                               v-on:setPagingData="setPagingData"></CommonPageNavigator>
                </div>
                <div class="btn-group">
                    <button type="button" v-if="isWrite" class="btn blue" @click="openRegist">사은품 등록</button>
                </div>
            </div>
        </div>
    </div>
    <!-- /컨텐츠 영역 -->
</template>

<script>
import CommonNavigator from '../../common/CommonNavigator'
import CommonDatePicker from "@views.admin/common/CommonDatePicker";
import CommonPageNavigator from "@views.admin/common/CommonPageNavigator";
import GiftRegist from "./GiftRegist.vue";
import GiftDetail from "./GiftDetail.vue";
export default {
    name: 'admin.promotion.promotion.giftlist',
    components: {
        CommonNavigator,
        CommonDatePicker,
        CommonPageNavigator,
        GiftRegist,
        GiftDetail
    },
    data() {
        return {
            searchData: {
                skey: '',           // 검색조건
                sword: '',          // 검색단어
                startdate: '',      // 시작일자
                enddate: '',        // 종료일자
                period: 3,          // 검색기간
                istrash: '',        // 사용여부
            },
            pagingData: {
                pageCount: 20,      // 페이징 옵션(최대수)
                page: 1,            // 현재 페이지
                listCount: 0        // 총 수량
            },
            sortData: {
                giftcode: 'giftcode_desc',          // 사은품코드 정렬기준
                stock: 'stock_desc',                // 재고 정렬기준
                regdate: 'regdate_desc',          // 등록일자 정렬기준
            },
            statelist: {
                total_cnt: 0,       // 전체수량
                use_cnt: 0,         // 사용수량
                trash_cnt: 0,       // 미사용수량
            },
            user: {},               // 현재 접속유저 정보
            isChecked: false,       // 전체체크여부
            listData: [],           // 조회해온 데이터
            checkedData: [],        // 체크된 데이터
            isRead: false,          // 읽기권한여부
            isWrite: false,         // 쓰기권한여부
            showRegist: false,      // 사은품등록 팝업 오픈여부
            showDetail: false,      // 사은품상세 팝업 오픈여부
            giftidx: '',            // 오픈된 사은품상세 idx
        }
    },
    mounted() {
        this.$http.post('/admin/common/pageAuth/check', {url : this.$options.name, isloading: false}).then(result => {
            this.isRead = (result.data.isread === 'T');
            this.isWrite = (result.data.iswrite === 'T');

            if(this.isRead){
                // 초기데이터 세팅
                this.oninit();
            }
        }).catch(error => {
            this.$util.debug(error);
        });
    },
    methods: {
        oninit() {
            // 조회날짜 현재부터 3개월전을 default로 입력 
            this.searchData.startdate = this.$util.getAddMonth(this.$util.getDate(''), -3, '-');
            this.searchData.enddate = this.$util.getDate('-');

            // 유저정보 세팅
            this.user = this.$util.getUser(this.$store.getters['CONSTANTS'].ADMIN_USER);

            this.onSearch();
        },
        // 검색
        onSearch(page) {
            this.isChecked = false;

            let params = this.searchData;
            params.pageCount = this.pagingData.pageCount;
            params.page = (typeof page === 'undefined') ? this.pagingData.page : 1;
            params.listCount = this.pagingData.listCount;

            this.$http.post('/admin/promotion/promotion/gift/search', params)
            .then(result => {
                if(result.statusCode === 200) {
                    let data = result.data;
                    this.listData = data.list;
                    this.pagingData.listCount = data.statelist.total_cnt;
                    this.statelist = data.statelist;
                    this.checkedData = [];
                }
            })
            .catch(error => {
                this.$util.debug(error);
            })
        },
        // 검색조건 초기화
        onSearchDataReset() {
            this.searchData = this.$options.data().searchData;
            this.searchData.startdate = this.$util.getAddMonth(this.$util.getDate(''), -3, '-');
            this.searchData.enddate = this.$util.getDate('-');
        },
        // 페이징 콜백
        setPagingData(param){
            this.pagingData = param;
            this.onSearch();
        },
        // 정렬조건으로 검색
        sortToggle (key) {
            let arr = key.split("_");
            let sortKey = arr[0];
            let sortOrder = (arr[1] === 'asc') ? 'desc' : 'asc';
            let sortName = sortKey + '_' + sortOrder;

            this.sortData = this.$options.data().sortData;

            this.sortData[sortKey] = sortName;
            this.searchData.psort = sortName;

            this.onSearch();
        },
        // 날짜 picker 콜백 함수
        onChangeStartDate(val) {
            this.searchData.startdate = val;
        },
        // 날짜 picker 콜백 함수
        onChangeEndDate(val) {
            this.searchData.enddate = val;
        },
        // 사은품 사용여부 변경
        onChangeUse(use) {
            if(this.checkedData.length === 0) {
                alert("선택된 사은품이 없습니다.");
                return false;
            }

            let realList = this.checkedData.filter(item => item.istrash !== use);

            let params = {
                list: this.checkedData,
                check: use,
                reallist: realList,
            }

            if(confirm("상태를 전환하시겠습니까?")){
                this.$http.post("/admin/promotion/promotion/gift/update", params)
                .then(result => {
                    if(result.statusCode === 200) {
                        alert("상태가 수정되었습니다.");
                        this.onSearch();
                    }
                })
                .catch(error => {
                    this.$util.debug(error);
                });
            }
        },
        // 엑셀다운로드
        fnExcelDownload() {
            let param = this.searchData;
            let postConfig = { responseType: 'arraybuffer' };
            this.$http.post("/admin/promotion/promotion/gift/excel", param, postConfig)
            .then(result => {
                this.$util.debug(result);
            })
            .catch(error => {
                this.$util.debug(error);
            });
        },
        // 전체 체크
        onCheckAll(checked) {
            if (checked) { // 전체 체크
                for (let i in this.listData) {
                    this.checkedData[i] = this.listData[i];
                }
            } else { // 전체 체크 해제
                this.checkedData = [];
            }
        },
        openRegist() {
            this.showRegist = true;
        },
        closeRegist(isreload) {
            this.showRegist = false;
            if(isreload) {
                this.onSearch();
            }
        },
        goDetail(idx) {
            this.giftidx = idx;
            this.showDetail = true;
        },
        closeDetail(isreload) {
            this.showDetail = false;
            if(isreload) {
                this.onSearch();
            }
        }
    },
    watch: {
        'searchData.period': function(val){
            let dayType = ["-1", "0", "7"];
            let monthType = ["1", "3", "6"];
            let valueToInt = parseInt(val);

            if(dayType.includes(val)){
                if(valueToInt >= 0){
                    this.searchData.startdate = this.$util.getAddDate(this.$util.getDate(''), (-1 * valueToInt), '-');
                    this.searchData.enddate = this.$util.getDate('-');
                }else{
                    this.searchData.startdate = this.$util.getAddDate(this.$util.getDate(''), valueToInt, '-');
                    this.searchData.enddate = this.$util.getAddDate(this.$util.getDate(''), valueToInt, '-');
                }
            }else if(monthType.includes(val)){
                this.searchData.startdate = this.$util.getAddMonth(this.$util.getDate(''), (-1 * valueToInt), '-');
                this.searchData.enddate = this.$util.getDate('-');
            }
        },
    }
}
</script>

<style>

</style>