<template>
    <!-- 컨텐츠 영역 -->
    <div class="content m-leftmenu">
        <common-navigator></common-navigator>
        <NotifyTplDetail v-if="showDetail" v-bind:idx="idx" v-on:closeDetail="closeDetail"></NotifyTplDetail>
        <NotifyTplRegist v-if="showRegist" v-on:closeRegist="closeRegist"></NotifyTplRegist>
        <div class="inner">
            <div class="boxing search-area">
                <dl>
                    <dt>직접검색</dt>
                    <dd>
                        <select v-model="searchData.skey">
                            <option value="">전체</option>
                            <option value="title">상품정보고시명</option>
                            <option value="regusername">등록자</option>
                        </select>
                        <input type="text" v-model="searchData.sword" @keyup.enter="onSearch(1)"/>
                    </dd>
                </dl>
                <dl>
                    <dt>조회기간</dt>
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
                            
                            <input type="radio" v-model="searchData.istrash" name="group00" id="group01" value="" checked/><label for="group01">전체</label>
                            <input type="radio" v-model="searchData.istrash" name="group00" id="group02" value="F" /><label for="group02">사용</label>
                            <input type="radio" v-model="searchData.istrash" name="group00" id="group03" value="T" /><label for="group03">미사용</label>
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
                <caption>상품정보고시 목록</caption>
                <colgroup>
                    <col width="3%" /><!-- checkbox -->
                    <col width="5%" /><!-- No -->
                    <col width="" /><!-- 상품정보고시명 -->
                    <col width="10%" /><!-- 사용여부 -->
                    <col width="10%" /><!-- 등록자 -->
                    <col width="12%" /><!-- 등록일자 -->
                </colgroup>
                <thead>
                    <tr>
                        <th><input type="checkbox" id="chkall"  v-model="isChecked" @change="onCheckAll($event.target.checked)"/></th>
                        <th>No</th>
                        <th>상품정보고시명</th>
                        <th>사용여부</th>
                        <th>등록자</th>
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
                        <td><input type="checkbox" v-model="checkedData" :id="'chk'+index" :value="row.idx"/></td>
                        <td>{{row.no}}</td>
                        <td class="left"><a class="link" @click="goDetail(row.idx)">{{row.title}}</a></td>
                        <td>{{row.istrash}}</td>
                        <td>{{row.regusername}}</td>
                        <td>{{row.regdate}}</td>
                    </tr>
                </tbody>
                <tbody v-else>
                    <tr><td colspan="6">대상 상품정보고시가 없습니다.</td></tr>
                </tbody>
            </table>
            <div class="bottom-group">
                <div class="paging">
                    <CommonPageNavigator v-show="isRead" v-bind:pagingData="this.pagingData"
                               v-on:setPagingData="setPagingData"></CommonPageNavigator>
                </div>
                <div class="btn-group">
                    <button type="button" v-if="isWrite" class="btn blue" @click="openRegist">상품정보고시 등록</button>
                </div>
            </div>
        </div>
    </div>
    <!-- /컨텐츠 영역 -->
</template>

<script>
import CommonNavigator from '@views.admin/common/CommonNavigator'
import CommonDatePicker from "@views.admin/common/CommonDatePicker";
import CommonPageNavigator from "@views.admin/common/CommonPageNavigator";
import NotifyTplRegist from "./NotifyTplRegist.vue";
import NotifyTplDetail from "./NotifyTplDetail.vue";
export default {
    name: 'admin.configuration.manage.notifylist',
    components: {
        CommonNavigator,
        CommonDatePicker,
        CommonPageNavigator,
        NotifyTplRegist,
        NotifyTplDetail
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
                regdate: 'regdate_desc',          // 등록일자 정렬기준
            },
            statelist: {
                total_cnt: 0,       // 전체수량
                use_cnt: 0,         // 사용수량
                trash_cnt: 0,       // 미사용수량
            },
            isChecked: false,
            listData: [],
            checkedData: [],
            showRegist: false,
            showDetail: false,
            idx: '',
            isRead: false,
            isWrite: false,
        }
    },
    mounted() {
        this.$http.post('/admin/common/pageAuth/check', {url : this.$options.name, isloading: false}).then(result => {
            this.isRead = (result.data.isread === 'T');
            this.isWrite = (result.data.iswrite === 'T');

            if(this.isRead){
                this.searchData.startdate = this.$util.getAddMonth(this.$util.getDate(''), -3, '-');
                this.searchData.enddate = this.$util.getDate('-');
                this.$util.componentSetSearchParam(this);
                this.onSearch();
            }
        }).catch(error => {
            this.$util.debug(error);
        });
    },
    methods: {
        // 검색
        onSearch(page) {
            this.isChecked = false;

            let params = this.searchData;
            params.pageCount = this.pagingData.pageCount;
            params.page = (typeof page === 'undefined') ? this.pagingData.page : 1;
            params.listCount = this.pagingData.listCount;

            this.$http.post('/admin/configuration/manage/notify/search', params)
            .then(result => {
                if(result.statusCode === 200) {
                    let data = result.data;
                    this.listData = data.list;
                    this.pagingData.listCount = data.statelist.total_cnt;
                    this.statelist = data.statelist;
                    this.checkedData = [];
                    this.$util.dataSetSearchParam(this);
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
                alert("선택된 상품정보고시가 없습니다.");
                return false;
            }

            let params = {
                list: this.checkedData,
                istrash: use
            }

            if(confirm("상태를 전환하시겠습니까?")){
                this.$http.post("/admin/configuration/manage/notify/update", params)
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
            this.$http.post("/admin/configuration/manage/notify/excel", param, postConfig)
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
                    this.checkedData[i] = this.listData[i].idx;
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
            this.idx = idx;
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