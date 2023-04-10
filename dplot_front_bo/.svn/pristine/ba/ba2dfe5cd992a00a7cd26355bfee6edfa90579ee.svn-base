<template>
    <div class="content m-leftmenu">
        <common-navigator></common-navigator>
        <ExhibitRegist v-if="registShow" v-bind:commonCode="commonCode" v-on:closeRegist="closeRegist"></ExhibitRegist>
        <ExhibitDetail v-if="detailShow" v-bind:exhibitidx="exhibitidx" v-bind:commonCode="commonCode" v-on:closeDetail="closeDetail"></ExhibitDetail>
        <div class="inner">
            <div class="clearfix">
                <div class="bar-title fl">기획전</div>
            </div>
            <div class="boxing search-area">
                <dl>
                    <dt>직접검색</dt>
                    <dd>
                        <select v-model="searchData.skey">
                            <option value="">전체</option>
                            <option value="subject">제목</option>
                            <option value="regusername">작성자</option>
                        </select>
                        <input type="text" v-model="searchData.sword"/>
                    </dd>
                </dl>
                <dl>
                    <dt>조회기간</dt>
                    <dd>
                        <select v-model="searchData.stype">
                            <option value="regdate">등록일자</option>
                            <option value="expsttime">시작일자</option>
                            <option value="expedtime">종료일자</option>
                        </select>
                        <CommonDatePicker :value="searchData.startDate" @change="onChangeStartDate"/>
                        <span>-</span>
                        <CommonDatePicker :value="searchData.endDate" @change="onChangeEndDate"/>
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
                            <input type="radio" v-model="searchData.istrash" name="group00" id="group01" value="" checked><label for="group01">전체</label>
                            <input type="radio" v-model="searchData.istrash" name="group00" id="group02" value="F"><label for="group02">사용</label>
                            <input type="radio" v-model="searchData.istrash" name="group00" id="group03" value="T"><label for="group03">미사용</label>
                        </div>
                    </dd>
                </dl>
                <dl>
                    <dt>적용채널</dt>
                    <dd>
                        <span>
                                <input type="checkbox" v-model="searchData.isallmuappch" id="group11" true-value="T" false-value="F" @change="checkAllMuAppch" checked>
                                <label for="group11">전체</label>
                        </span>
                        <span class="check-for" v-for="item in commonCode.MUAPPCHTYPE" :key="item.cmcode">
                            <input type="checkbox" v-model="searchData.muappchtypeArr" :id="'group_'+item.cmcode" true-value="[]" :value="item.cmcode">
                            <label :for="'group_'+item.cmcode">{{item.codename}}</label>
                        </span>
                    </dd>
                </dl>
                <dl>
                    <dt>메인노출여부</dt>
                    <dd>
                        <div class="radio_wrap wide3">
                            <input type="radio" v-model="searchData.ismaindisp" name="group02" id="group21" value="" checked><label for="group21">전체</label>
                            <input type="radio" v-model="searchData.ismaindisp" name="group02" id="group22" value="T"><label for="group22">노출</label>
                            <input type="radio" v-model="searchData.ismaindisp" name="group02" id="group23" value="F"><label for="group23">비노출</label>
                        </div>
                    </dd>
                </dl>
                <dl>
                    <dt>진행상태</dt>
                    <dd>
                        <span>
                                <input type="checkbox" v-model="searchData.isallexpstatus" id="group31" true-value="T" false-value="F" @change="checkAllexpstatus" checked>
                                <label for="group31">전체</label>
                        </span>
                        <span class="check-for" v-for="item in commonCode.EXPSTATUS" :key="item.cmcode">
                            <input type="checkbox" v-model="searchData.expstatusArr" :id="'group_'+item.cmcode" true-value="[]" :value="item.cmcode">
                            <label :for="'group_'+item.cmcode">{{item.codename}}</label>
                        </span>
                    </dd>
                </dl>
                <dl>
                    <dt>대상회원유형</dt>
                    <dd>
                        <span>
                                <input type="checkbox" v-model="searchData.isallmumember" id="group41" true-value="T" false-value="F" @change="checkAllMuMemer" checked>
                                <label for="group41">전체</label>
                        </span>
                        <span class="check-for" v-for="item in commonCode.MUMEMBERTYPE" :key="item.cmcode">
                            <input type="checkbox" v-model="searchData.mumembertypeArr" :id="'group_'+item.cmcode" true-value="[]" :value="item.cmcode">
                            <label :for="'group_'+item.cmcode">{{item.codename}}</label>
                        </span>                   
                    </dd>
                </dl>
                <dl>
                    <dt>대상회원등급</dt>
                    <dd>
                        <span>
                                <input type="checkbox" v-model="searchData.isallmumemlv" id="group51" true-value="T" false-value="F" @change="checkAllMuMemLv" checked>
                                <label for="group51">전체</label>
                        </span>
                        <span class="check-for" v-for="item in commonCode.MUMEMLVTYPE" :key="item.cmcode">
                            <input type="checkbox" v-model="searchData.muMemLvtypeArr" :id="'group_'+item.cmcode" true-value="[]" :value="item.cmcode">
                            <label :for="'group_'+item.cmcode">{{item.codename}}</label>
                        </span>     
                    </dd>
                </dl>
            </div>
            <div class="btn-group">
                <button type="button" class="btn big blue" v-if="isRead" @click="onSearch">검색</button>
                <button type="button" class="btn big gray" v-if="isRead" @click="onSearchDataReset">초기화</button>
            </div>
            <div class="caption-group mt10 clearfix">
                <div class="total-group fl">
                    <span class="total">전체 <strong>{{pagingData.listCount}}</strong>건</span>
                    <span>사용 <strong>{{useCnt}}</strong>건</span>
                    <span>미사용 <strong>{{trashCnt}}</strong>건</span>
                </div>
                <div class="btn-group fr">
                    <button type="button" class="btn blue-line" v-if="isWrite" @click="onChangeUse('F')">사용</button>
                    <button type="button" class="btn red-line" v-if="isWrite" @click="onChangeUse('T')">미사용</button>
                    <button type="button" class="btn green-line" v-if="isRead" @click="fnExcelDownload()"><i class="icon-excel"></i>엑셀다운로드</button>
                    <select v-model="pagingData.pageCount">
                        <option value="20">20개씩 보기</option>
                        <option value="50">50개씩 보기</option>
                        <option value="100">100개씩 보기</option>
                    </select>
                </div>
            </div>
            <table cellpadding="0" cellspacing="0" class="data-tb align-c">
                <caption>기획전 목록</caption>
                <colgroup>
                    <col width="3%" /><!-- radio -->
                    <col width="3%" /><!-- No -->
                    <col width="5%" /><!-- 메인노출 -->
                    <col width="%" /><!-- 제목 -->
                    <col width="5%" /><!-- 유형 -->
                    <col width="10%" /><!-- 등급 -->
                    <col width="10%" /><!-- 적용채널 -->
                    <col width="5%" /><!-- 대상상품 -->
                    <col width="5%" /><!-- 사용여부 -->
                    <col width="6%" /><!-- 작성자 -->
                    <col width="5%" /><!-- 조회수 -->
                    <col width="5%" /><!-- 진행상태 -->
                    <col width="7%" /><!-- 시작일자 -->
                    <col width="7%" /><!-- 종료일자 -->
                    <col width="7%" /><!-- 등록일자 -->
                </colgroup>
                <thead>
                    <tr>
                        <th>선택</th>
                        <th>No</th>
                        <th>메인노출</th>
                        <th>제목</th>
                        <th>유형</th>
                        <th>등급</th>
                        <th>적용채널</th>
                        <th>대상상품</th>
                        <th>사용여부</th>
                        <th>작성자</th>
                        <th>조회수<button type="button" v-if="isRead" :value="sortData.readcnt" class="sort" :class="[{up : sortData.readcnt === 'readcnt_asc'}, {down : sortData.readcnt === 'readcnt_desc'}]" @click="sortToggle(sortData.readcnt)"></button></th>
                        <th>진행상태</th>
                        <th>시작일자<button type="button" v-if="isRead" :value="sortData.expsttime" class="sort" :class="[{up : sortData.expsttime === 'expsttime_asc'}, {down : sortData.expsttime === 'expsttime_desc'}]" @click="sortToggle(sortData.expsttime)"></button></th>
                        <th>종료일자<button type="button" v-if="isRead" :value="sortData.expedtime" class="sort" :class="[{up : sortData.expedtime === 'expedtime_asc'}, {down : sortData.expedtime === 'expedtime_desc'}]" @click="sortToggle(sortData.expedtime)"></button></th>
                        <th>등록일자<button type="button" v-if="isRead" :value="sortData.regdate" class="sort" :class="[{up : sortData.regdate === 'regdate_asc'}, {down : sortData.regdate === 'regdate_desc'}]" @click="sortToggle(sortData.regdate)"></button></th>
                    </tr>
                </thead>
                <tbody v-if="this.listData.length > 0">
                    <tr v-for="(row, i) in this.listData" :key="i">
                        <td><input type="radio" v-model="checkData" name="radio00" :id="i < 10 ? 'rd0'+i : 'rd'+i" class="circle" :value="row"/></td>
                        <td>{{row.no}}</td>
                        <td>{{row.ismaindisp === 'T' ? '●':''}}</td>
                        <td class="left"><a class="link" @click="goDetail(row.exhibitidx)">{{row.subject}}</a></td>
                        <td>{{row.mumembertype}}</td>
                        <td>{{row.mumemlvtype}}</td>
                        <td>{{row.muappchtype}}</td>
                        <td>{{row.goodscnt}}</td>
                        <td>{{row.istrash}}</td>
                        <td>{{row.regusername}}</td>
                        <td>{{row.readcnt}}</td>
                        <td>{{row.expstatus}}</td>
                        <td>{{$util.getFormatDate(row.expsttime,'-')}}</td>
                        <td>{{$util.getFormatDate(row.expedtime,'-')}}</td>
                        <td>{{row.regdate}}</td>
                    </tr>
                </tbody>
                <tbody v-else>
                    <tr>
                        <td colspan="15">조회 결과가 존재하지 않습니다.</td>
                    </tr>
                </tbody>
            </table>
            <div class="bottom-group">
                <div class="paging">
                    <CommonPageNavigator v-show="isRead" v-bind:pagingData="this.pagingData"
                               v-on:setPagingData="setPagingData"></CommonPageNavigator>
                </div>
                <div class="btn-group">
                    <button type="button" class="btn blue" v-if="isWrite" @click="openRegist()">기획전 등록</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import CommonNavigator from '@/views/admin/common/CommonNavigator'
import CommonDatePicker from "@views.admin/common/CommonDatePicker";
import CommonPageNavigator from "../../common/CommonPageNavigator";
import ExhibitRegist from "./ExhibitRegist.vue";
import ExhibitDetail from "./ExhibitDetail.vue";

export default {
    name: 'admin.operation.display.exhibitlist',
    components: {
        CommonNavigator,
        CommonDatePicker,
        CommonPageNavigator,
        ExhibitRegist,
        ExhibitDetail
    },
    data() {
        return{
            searchData: {
                skey: '',           // 검색조건
                sword: '',          // 검색단어
                stype: 'regdate',   // 조회기간 종류
                startDate: '',      // 시작년도
                endDate: '',        // 종료년도
                period: 3,          // 조회기간 검색 옵션
                istrash: '',        // 사용여부
                ismaindisp: '',     // 메인노출여부
                isallmuappch: 'T',   // 적용채널 전체여부
                muappchtype: '',    // 적용채널
                muappchtypeArr: [], // 적용채널Array
                isallexpstatus: 'T', // 진행상태 전체여부
                expstatus: '',      // 진행상태
                expstatusArr: [],   // 진행상태Array
                isallmumember: 'T',  // 대상회원유형 전체여부
                mumembertype: '',   // 대상회원유형
                mumembertypeArr: [],// 대상회원유형Array
                isallmumemlv: 'T',    // 대상회원등급전체여부
                muMemLvtype: '',    // 대상회원등급
                muMemLvtypeArr: [],    // 대상회원등급Array
            },
            commonCode: {
                MUAPPCHTYPE: [],    // 적용채널
                MUMEMBERTYPE: [],   // 대상회원유형
                MUMEMLVTYPE: [],    // 대상회원등급
                EXPSTATUS: [{'cmcode':'EXS001','codename':'진행전'},{'cmcode':'EXS002','codename':'진행중'},{'cmcode':'EXS003','codename':'종료'}],
            },
            pagingData: {
                pageCount: 20,      // 페이징 옵션 (최대수)
                page: 1,            // 현재 페이지
                listCount: 0        // 총 페이지
            },
            sortData: {
                readcnt : 'readcnt_desc',
                expsttime : 'expsttime_desc',
                expedtime : 'expedtime_desc',
                regdate : 'regdate_desc'
            },
            user: {},
            useCnt: 0,
            trashCnt: 0,
            listData: [],       // response 데이터 리스트
            checkData: '',      // 선택된 데이터
            isRead: false,      // 읽기여부
            isWrite: false,     // 쓰기여부
            registShow: false,
            detailShow: false,
            exhibitidx: '',
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
            this.searchData.startDate = this.$util.getAddMonth(this.$util.getDate(''), -3, '-');
            this.searchData.endDate = this.$util.getDate('-');

            // 유저정보 세팅
            this.user = this.$util.getUser(this.$store.getters['CONSTANTS'].ADMIN_USER);

            // 공통코드 조회
            this.getCommonCodeList();
        },
        getCommonCodeList(){
            for(const [key] of Object.entries(this.commonCode)) {
                if(key === 'EXPSTATUS'){
                    // 진행상태 초기값 전체선택
                    this.checkAllexpstatus();
                    continue;
                }
                let params = { cmclass : key };
                this.$http.post('/common/code/list', params)
                .then(result => {
                    if(result.statusCode === 200){
                        this.commonCode[key] = result.data.list;
                        // 적용채널 초기값 전체선택
                        if(key === 'MUAPPCHTYPE'){
                            this.checkAllMuAppch();
                        }
                        // 대상회원유형 초기값 전체선택
                        if(key === 'MUMEMBERTYPE'){
                            this.checkAllMuMemer();
                        }
                        // 대상회원등급 초기값 전체선택
                        if(key === 'MUMEMLVTYPE') {
                            this.checkAllMuMemLv();
                        }
                    }else{
                        this.commonCode[key] = [];
                    }
                })
                .catch(error => {
                    this.$util.debug(error);
                })
            }
            this.onSearch();
            this.$util.componentSetSearchParam(this);
        },
        onSearch() {
            let param = this.searchData;
            param.pageCount = this.pagingData.pageCount;
            param.page = (typeof page === 'undefined') ? this.pagingData.page : 1;
            param.listCount = this.pagingData.listCount;

            this.$http.post("/admin/operation/display/exhibit/search", param)
                .then(result => {
                    let data = result.data;
                    this.listData = data.list;
                    this.pagingData.listCount = data.listcount;
                    this.useCnt = data.usecnt;
                    this.trashCnt = data.trashcnt;
                    this.checkData = '';

                    this.$util.dataSetSearchParam(this);
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        onChangeUse(check){
            let params = {
                istrash : check,
                exhibitidx : this.checkData.exhibitidx,
                expsttime : this.checkData.expsttime,
                expedtime : this.checkData.expedtime,
                type : 'istrash'
            }

            if(this.checkData === ''){
                alert("변경할 기획전을 선택해주세요.");
                return;
            }
            
            if(confirm("상태를 전환 하시겠습니까?")){
                if(check === 'F' && this.checkData.ismaindisp === 'T'){
                    this.$http.post('/admin/operation/display/exhibit/check', params)
                    .then(result => {
                        if(result.statusCode === 200){
                            if(result.data.check){
                                this.$http.post('/admin/operation/display/exhibit/update', params)
                                .then(result => {
                                    if(result.statusCode === 200){
                                        alert("변경이 완료되었습니다.");
                                        // this.onSearch();
                                    }else {
                                        alert("변경에 실패했습니다.");
                                    }
                                })
                                .catch(error => {
                                    this.$util.debug(error);
                                });
                            }else{
                                alert("메인노출은 동일 전시기간내\n1건만 지정 할 수 있습니다(<사용> 상태에 한함)");
                                return;
                            }
                        }else {
                            alert("변경에 실패했습니다.");
                            return;
                        }
                    })
                    .catch(error => {
                        this.$util.debug(error);
                    });
                }else{
                    this.$http.post('/admin/operation/display/exhibit/update', params)
                    .then(result => {
                        if(result.statusCode === 200){
                            alert("변경이 완료되었습니다.");
                            this.onSearch();
                        }else {
                            alert("변경에 실패했습니다.");
                        }
                    })
                    .catch(error => {
                        this.$util.debug(error);
                    });
                }
            }
        },
        onSearchDataReset(){
            this.searchData = this.$options.data().searchData;
            this.searchData.startDate = this.$util.getAddMonth(this.$util.getDate(''), -3, '-');
            this.searchData.endDate = this.$util.getDate('-');
            this.checkAllMuAppch();
            this.checkAllMuMemer();
            this.checkAllMuMemLv();
            this.checkAllexpstatus();
        },
        setPagingData(param){
            this.pagingData = param;
            this.onSearch();
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
        onChangeStartDate(val) {
            this.searchData.startDate = val;
        },
        // 날짜 picker 콜백 함수
        onChangeEndDate(val) {
            this.searchData.endDate = val;
        },
        checkAllMuAppch() {
            let isAllCheck = this.searchData.isallmuappch;
            this.searchData.muappchtypeArr = [];
            if (isAllCheck === 'T') {
                for(let type of this.commonCode.MUAPPCHTYPE){
                    this.searchData.muappchtypeArr.push(type.cmcode);
                }
            }
        },
        checkAllMuMemer() {
            let isAllCheck = this.searchData.isallmumember;
            this.searchData.mumembertypeArr = [];
            if (isAllCheck === 'T') {
                for(let type of this.commonCode.MUMEMBERTYPE){
                    this.searchData.mumembertypeArr.push(type.cmcode);
                }
            }
        },
        checkAllMuMemLv() {
            let isAllCheck = this.searchData.isallmumemlv;
            this.searchData.muMemLvtypeArr = [];
            if (isAllCheck === 'T') {
                for(let type of this.commonCode.MUMEMLVTYPE){
                    this.searchData.muMemLvtypeArr.push(type.cmcode);
                }
            }
        },
        checkAllexpstatus() {
            let isAllCheck = this.searchData.isallexpstatus;
            this.searchData.expstatusArr = [];
            if (isAllCheck === 'T') {
                for(let type of this.commonCode.EXPSTATUS){
                    this.searchData.expstatusArr.push(type.cmcode);
                }
            }
        },
        fnExcelDownload(){
            let param = this.searchData;
            param.time = this.$util.getDate()+this.$util.getTime();
            let postConfig = { responseType: 'arraybuffer' };
            this.$http.post("/admin/operation/display/exhibit/excel", param, postConfig)
            .then(result => {
                this.$util.debug(result);
            })
            .catch(error => {
                this.$util.debug(error);
            });
        },
        openRegist(){
            this.registShow = true;
        },
        closeRegist(){
            this.registShow = false;
            this.onSearch();
        },
        goDetail(idx){
            this.exhibitidx = idx;
            this.detailShow = true;
        },
        closeDetail(){
            this.detailShow = false;
            this.onSearch();
        }
    },
    watch: {
        'searchData.period': function(val){
            let dayType = ["-1", "0", "7"];
            let monthType = ["1", "3", "6"];
            let valueToInt = parseInt(val);

            if(dayType.includes(val)){
                if(valueToInt >= 0){
                    this.searchData.startDate = this.$util.getAddDate(this.$util.getDate(''), (-1 * valueToInt), '-');
                    this.searchData.endDate = this.$util.getDate('-');
                }else{
                    this.searchData.startDate = this.$util.getAddDate(this.$util.getDate(''), valueToInt, '-');
                    this.searchData.endDate = this.$util.getDate('-');
                }
            }else if(monthType.includes(val)){
                this.searchData.startDate = this.$util.getAddMonth(this.$util.getDate(''), (-1 * valueToInt), '-');
                this.searchData.endDate = this.$util.getDate('-');
            }
        },
        'searchData.muappchtypeArr': function(value){
            if (value.length < this.commonCode.MUAPPCHTYPE.length) {
                this.searchData.isallmuappch = 'F';
            } else {
                this.searchData.isallmuappch = 'T';
            }
            this.searchData.muappchtype = this.searchData.muappchtypeArr.join();
        },
        'searchData.mumembertypeArr': function(value){
            if (value.length < this.commonCode.MUMEMBERTYPE.length) {
                this.searchData.isallmumember = 'F';
            } else {
                this.searchData.isallmumember = 'T';
            }
            this.searchData.mumembertype = this.searchData.mumembertypeArr.join();
        },
        'searchData.muMemLvtypeArr': function(value){
            if (value.length < this.commonCode.MUMEMLVTYPE.length) {
                this.searchData.isallmumemlv = 'F';
            } else {
                this.searchData.isallmumemlv = 'T';
            }
            this.searchData.muMemLvtype = this.searchData.muMemLvtypeArr.join();
        },
        'searchData.expstatusArr': function(value){
            if (value.length < this.commonCode.EXPSTATUS.length) {
                this.searchData.isallexpstatus = 'F';
            } else {
                this.searchData.isallexpstatus = 'T';
            }
            this.searchData.expstatus = this.searchData.expstatusArr.join();
        },
    }
}
</script>

<style>

</style>