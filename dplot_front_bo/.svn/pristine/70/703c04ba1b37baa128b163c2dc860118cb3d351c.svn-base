<template>
    <div class="content m-leftmenu">
        <common-navigator></common-navigator>
        <div class="inner">
            <div class="clearfix">
                <div class="bar-title fl">반짝특가</div>
            </div>
            <div class="boxing search-area">
                <dl>
                    <dt>직접검색</dt>
                    <dd>
                        <select v-model="searchData.skey">
                            <option value="">전체</option>
                            <option value="subject">관리제목</option>
                            <option value="regusername">등록(지정)자</option>
                        </select>
                        <input type="text" v-model="searchData.sword"/>
                    </dd>
                </dl>
                <dl>
                    <dt>조회기간</dt>
                    <dd>
                        <select v-model="searchData.stype">
                            <option value="">전체</option>
                            <option value="bpsttime">노출 시작일자</option>
                            <option value="bpedtime">노출 종료일자</option>
                            <option value="regdate">등록(지정)일자</option>
                        </select>
                        <CommonDatePicker :value="searchData.startdate" @change="onChangestartdate"/>
                        <span>-</span>
                        <CommonDatePicker :value="searchData.enddate" @change="onChangeenddate"/>
                        <div class="radio_wrap">
                            <input type="radio" v-model="searchData.period" name="period" id="rd1" value="-1" /><label for="rd1">어제</label>
                            <input type="radio" v-model="searchData.period" name="period" id="rd2" value="0" /><label for="rd2">오늘</label>
                            <input type="radio" v-model="searchData.period" name="period" id="rd3" value="7" /><label for="rd3">일주일</label>
                            <input type="radio" v-model="searchData.period" name="period" id="rd4" value="1" /><label for="rd4">1개월</label>
                            <input type="radio" v-model="searchData.period" name="period" id="rd5" value="3" checked/><label for="rd5">3개월</label>
                            <input type="radio" v-model="searchData.period" name="period" id="rd6" value="6" /><label for="rd6">6개월</label>
                        </div>
                    </dd>
                </dl>
                <dl>
                    <dt>사용여부</dt>
                    <dd>
                        <div class="radio_wrap wide3">
                            <input type="radio" v-model="searchData.istrash" name="group00" id="group01" value=""  checked><label for="group01">전체</label>
                            <input type="radio" v-model="searchData.istrash" name="group00" id="group02" value="F"><label for="group02">사용</label>
                            <input type="radio" v-model="searchData.istrash" name="group00" id="group03" value="T"><label for="group03">미사용</label>
                        </div>
                    </dd>
                </dl>
                <dl>
                    <dt>진행상태</dt>
                    <dd>
                        <div class="check-wrap">
                            <input type="checkbox" v-model="searchData.isallbp" id="group31" true-value="T" false-value="F" @change="checkAllExp" checked>
                            <label for="group31">전체</label>
                        </div>
                        <div class="check-wrap" v-for="item in commonCode.BPTYPE" :key="item.cmcode">
                            <input type="checkbox" v-model="searchData.bptypearr" :id="'group_'+item.cmcode" true-value="[]" :value="item.cmcode">
                            <label :for="'group_'+item.cmcode">{{item.codename}}</label>
                        </div>
                    </dd>
                </dl>
            </div>
            <div class="btn-group">
                <button type="button" class="btn big blue" v-if="isRead" @click="onSearch">검색</button>
                <button type="button" class="btn big gray" v-if="isRead" @click="onSearchDataReset">초기화</button>
            </div>
            <div class="caption-group mt10 clearfix">
                <div class="total-group fl">
                    <span class="total">전체 <strong>{{totalcnt}}</strong>건</span>
                    <span>사용 <strong>{{usecnt}}</strong>건</span>
                    <span>미사용 <strong>{{trashcnt}}</strong>건</span>
                </div>
                <div class="btn-group fr">
                    <button type="button" class="btn blue-line" v-if="isWrite" @click="onChangeUse('F')">사용</button>
                    <button type="button" class="btn red-line" v-if="isWrite" @click="onChangeUse('T')">미사용</button>
                    <button type="button" class="btn green-line" v-if="isRead" @click="fnExcelDownload()"><i class="icon-excel"></i>엑셀다운로드</button>
                    <select v-model="pagingData.pageCount" v-if="isRead">
                    <option value="20">20개씩 보기</option>
                    <option value="50">50개씩 보기</option>
                    <option value="100">100개씩 보기</option>
                    </select>
                </div>
            </div>
            <table cellpadding="0" cellspacing="0" class="data-tb align-c">
                <caption>반짝특가</caption>
                <colgroup>
                    <col width="3%" /><!-- checkbox -->
                    <col width="4%" /><!-- No -->
                    <col width="" /><!-- 관리제목 -->
                    <col width="7%" /><!-- 대상상품 -->
                    <col width="6%" /><!-- 진행상태 -->
                    <col width="10%" /><!-- 시작일자 -->
                    <col width="10%" /><!-- 종료일자 -->
                    <col width="6%" /><!-- 사용여부 -->
                    <col width="10%" /><!-- 등록(지정)자 -->
                    <col width="10%" /><!-- 등록(지정)일자 -->
                </colgroup>
                <thead>
                    <tr>
                        <th><input type="checkbox" id="chkall" /></th>
                        <th>No</th>
                        <th>관리제목</th>
                        <th>대상상품<button type="button" v-show="isRead" :value="sortData.goodscnt" class="sort" :class="[{up : sortData.goodscnt === 'goodscnt_asc'}, {down : sortData.goodscnt === 'goodscnt_desc'}]" @click="sortToggle(sortData.goodscnt)"></button></th>
                        <th>진행상태</th>
                        <th>시작일자</th>
                        <th>종료일자</th>
                        <th>사용여부</th>
                        <th>등록(지정)자</th>
                        <th>등록(지정)일자<button type="button"  v-show="isRead" :value="sortData.regdate" class="sort" :class="[{up : sortData.regdate === 'regdate_asc'}, {down : sortData.regdate === 'regdate_desc'}]" @click="sortToggle(sortData.regdate)"></button></th>
                    </tr>
                </thead>
                <tbody v-if="this.listData.length > 0">
                    <tr v-for="(row, i) in this.listData" :key="i">
                        <td><input type="checkbox" v-model="checkedList" :id="'chk'+i" class="circle" :value="row.bspcidx"/></td>
                        <td>{{row.no}}</td>
                        <td class="left"><a class="link" @click="goDetail(row.bspcidx)">{{row.subject}}</a></td>
                        <td>{{row.goodscnt}}</td>
                        <td>{{row.bptype}}</td>
                        <td>{{$util.getFormatDate(row.bpsttime,'-')}}</td>
                        <td>{{$util.getFormatDate(row.bpedtime,'-')}}</td>
                        <td>{{row.istrash}}</td>
                        <td>{{row.regusername}}</td>
                        <td>{{row.regdate}}</td>
                    </tr>
                </tbody>
                <tbody v-else>
                    <tr><td colspan="10">대상 상품이 없습니다.</td></tr>
                </tbody>
            </table>
            <div class="bottom-group">
                <div class="paging">
                    <CommonPageNavigator v-show="isRead" v-bind:pagingData="this.pagingData"
                               v-on:setPagingData="setPagingData"></CommonPageNavigator>
                </div>
                <div class="btn-group">
                    <button type="button" class="btn blue" v-if="isWrite" @click="openNew">반짝특가 등록</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import CommonNavigator from '../../common/CommonNavigator'
import CommonDatePicker from "@views.admin/common/CommonDatePicker";
import CommonPageNavigator from "@views.admin/common/CommonPageNavigator";
export default {
    name: 'admin.operation.display.blinkspclist',
    components: {
        CommonNavigator,
        CommonDatePicker,
        CommonPageNavigator
    },
    data() {
        return {
            searchData: {
                skey: '',
                sword: '',
                stype: '',
                startdate: '',
                enddate: '',
                period: 3,
                istrash : 'F',
                isallbp: 'T',       // 노출상태전체여부
                bptype: '',         // 노출상태
                bptypearr: [],      // 노출상태Array
                reguserid: '',
            },
            commonCode: {
                BPTYPE: [{'cmcode':'BP001','codename':'진행전'},{'cmcode':'BP002','codename':'진행중'},{'cmcode':'BP003','codename':'종료'}],        // 노출상태
            },
            pagingData: {
                pageCount: 20,      // 페이징 옵션 (최대수)
                page: 1,            // 현재 페이지
                listCount: 0        // 총 페이지
            },
            sortData: {
                goodscnt : 'goodscnt_desc',
                regdate : 'regdate_desc',
            },
            isChecked: false,
            listData: [],
            checkedList: [],
            totalcnt: 0,
            usecnt: 0,
            trashcnt: 0,
            isRead: false,
            isWrite: false,
            bspcidx: '',
            detailShow: false,
            newShow: false,
        }
    },
    mounted() {
        this.$http.post('/admin/common/pageAuth/check', {url : this.$options.name, isloading: false}).then(result => {
            this.isRead = (result.data.isread === 'T');
            this.isWrite = (result.data.iswrite === 'T');

            if(this.isRead){
                this.oninit();
            }
        }).catch(error => {
            this.$util.debug(error);
        });
    },
    methods: {
        oninit(){
            this.searchData.startdate = this.$util.getAddMonth(this.$util.getDate(''), -3, '-');
            this.searchData.enddate = this.$util.getDate('-');

            // 유저정보 세팅
            let userInfo = this.$storage.getLocalStorage("ADMIN_USER");
            this.searchData.reguserid = userInfo.id;

            this.checkAllExp();

            this.onSearch();
            this.$util.componentSetSearchParam(this);
        },
        onSearch(){

        },
        onSearchDataReset(){
            this.searchData = this.$options.data().searchData;
            this.searchData.startdate = this.$util.getAddMonth(this.$util.getDate(''), -3, '-');
            this.searchData.enddate = this.$util.getDate('-');
            this.checkAllExp();
        },
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
        onChangestartdate(val) {
            this.searchData.startdate = val;
        },
        // 날짜 picker 콜백 함수
        onChangeenddate(val) {
            this.searchData.enddate = val;
        },
        checkAllExp() {
            let isAllCheck = this.searchData.isallbp;
            this.searchData.bptypearr = [];
            if (isAllCheck === 'T') {
                for(let type of this.commonCode.BPTYPE){
                    this.searchData.bptypearr.push(type.cmcode);
                }
            }
        },
        onChangeUse(check){
            if (this.checkedList.length === 0) { // 체크된 데이터가 없다면
                alert("체크된 리스트가 없습니다.");
                return;
            }

            let params = {
                list: this.checkedList,
                istrash: check,
                moduserid: this.searchData.reguserid
            }

            if (confirm("상태를 전환하시겠습니까?")) {
            this.$http.post("/admin/operation/display/blink/update", params)
                .then(result => {
                    if (result.statusCode == 200) {
                        alert("저장이 완료되었습니다.");
                        this.checkedList = [];
                        this.isChecked = false;
                        this.onSearch();
                    } else {
                        alert("저장에 실패했습니다.");
                    }
                })
                .catch(error => {
                    this.$util.debug(error);
                })
            }
        },
        fnExcelDownload(){
            let param = this.searchData;

            let postConfig = { responseType: 'arraybuffer' };
            this.$http.post("/admin/operation/display/blink/excel", param, postConfig)
            .then(result => {
                this.$util.debug(result);
            })
            .catch(error => {
                this.$util.debug(error);
            });
        },
        openNew(){
            this.newShow = true;
        },
        closeNew(){
            this.newShow = false;
        },
        goDetail(userNo) {
            this.bspcidx = bspcidx;
            this.detailShow = true;
        },
        closeDetail() {
            this.detailShow = false;
        },
        // 전체 체크
        onCheckAll(checked) {
            if (checked) { // 전체 체크
                for (let i in this.listData) {
                    this.checkedList[i] = this.listData[i].idx;
                }
            } else { // 전체 체크 해제
                this.checkedList = [];
            }
        },
        // 페이징 콜백 함수
        setPagingData(param) {
            this.pagingData = param;
            this.onSearch();
        },
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
                    this.searchData.enddate = this.$util.getDate('-');
                }
            }else if(monthType.includes(val)){
                this.searchData.startdate = this.$util.getAddMonth(this.$util.getDate(''), (-1 * valueToInt), '-');
                this.searchData.enddate = this.$util.getDate('-');
            }
        },
        'searchData.bptypearr': function(value){
            if (value.length < this.commonCode.BPTYPE.length) {
                this.searchData.isallbp = 'F';
            } else {
                this.searchData.isallbp = 'T';
            }
            this.searchData.bptype = this.searchData.bptypearr.join();
        },
        // 카테고리 선택
        'searchData.depth1Category.idx': function(value) {
            let params = {depth: 1, idx: value};
            this.getCategoryCodeList(params);
        },
        'searchData.depth2Category.idx': function(value) {
            let params = {depth: 2, idx: value};
            this.getCategoryCodeList(params);
        },
        'searchData.depth3Category.idx': function(value) {
            let params = {depth: 3, idx: value};
            this.getCategoryCodeList(params);
        },
        'searchData.depth4Category.idx': function(value) {
            let params = {depth: 4, idx: value};
            this.getCategoryCodeList(params);
        },
    }
}
</script>

<style>

</style>