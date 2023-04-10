<template>
    <!-- 컨텐츠 영역 -->
    <div class="content m-leftmenu">
        <common-navigator></common-navigator>
        <OperatorRegist v-bind:permilist="permilist" v-if="showRegist" v-on:closeRegist="closeRegist"></OperatorRegist>
        <OperatorDetail v-bind:userno="userno" v-if="showDetail" v-on:closeDetail="closeDetail"></OperatorDetail>
        <div class="inner">
            <div class="boxing search-area">
                <dl>
                    <dt>직접검색</dt>
                    <dd>
                        <select v-model="searchData.skey">
                            <option value="">전체</option>
                            <option value="id">아이디</option>
                            <option value="name">이름</option>
                            <option value="mobile">휴대폰번호</option>
                            <option value="regusername">등록자</option>
                        </select>
                        <input type="text" v-model="searchData.sword" @keyup.enter="onSearch(1)"/>
                    </dd>
                </dl>
                <dl>
                    <dt>등록기간</dt>
                    <dd>
                        <select v-model="searchData.stype">
                            <option value="">전체</option>
                            <option value="visitdate">최근접속일자</option>
                            <option value="regdate">등록일자</option>
                        </select>
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
                <caption>운영자 목록</caption>
                <colgroup>
                    <col width="3%" /><!-- checkbox -->
                    <col width="5%" /><!-- No -->
                    <col width="" /><!-- 아이디 -->
                    <col width="10%" /><!-- 이름 -->
                    <col width="12%" /><!-- 휴대전화번호 -->
                    <col width="10%" /><!-- 접속수 -->
                    <col width="15%" /><!-- 최근접속일시 -->
                    <col width="10%" /><!-- 사용여부 -->
                    <col width="10%" /><!-- 등록자 -->
                    <col width="12%" /><!-- 등록일자 -->
                </colgroup>
                <thead>
                    <tr>
                        <th><input type="checkbox" id="chkall"   v-model="isChecked" @change="onCheckAll($event.target.checked)"/></th>
                        <th>No</th>
                        <th>아이디</th>
                        <th>이름</th>
                        <th>휴대전화번호</th>
                        <th>접속수
                            <button type="button" v-if="isRead" 
                                    :value="sortData.visitcnt" 
                                    class="sort"
                                    :class="[{up : sortData.visitcnt === 'visitcnt_asc'}, {down : sortData.visitcnt === 'visitcnt_desc'}]"
                                    @click="sortToggle(sortData.visitcnt)">
                            </button>
                        </th>
                        <th>최근접속일시
                            <button type="button" v-if="isRead" 
                                    :value="sortData.visitdate" 
                                    class="sort"
                                    :class="[{up : sortData.visitdate === 'visitdate_asc'}, {down : sortData.visitdate === 'visitdate_desc'}]"
                                    @click="sortToggle(sortData.visitdate)">
                            </button>
                        </th>
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
                        <td><input type="checkbox" v-model="checkedData" :id="'chk'+index" :value="row.userno"/></td>
                        <td>{{row.no}}</td>
                        <td class="left"><a class="link" @click="openDetail(row.userno)">{{row.userid}}</a></td>
                        <td>{{row.name}}</td>
                        <td>{{$util.phoneNumberFilter(row.mobile)}}</td>
                        <td>{{$util.maskComma(row.visitcnt)}}</td>
                        <td>{{row.visitdate}}</td>
                        <td>{{row.istrash}}</td>
                        <td>{{row.regusername}}</td>
                        <td>{{row.regdate}}</td>
                    </tr>
                </tbody>
                <tbody v-else>
                    <tr><td colspan="10">대상 운영자가 없습니다.</td></tr>
                </tbody>
            </table>
            <div class="bottom-group">
                <div class="paging">
                    <CommonPageNavigator v-show="isRead" v-bind:pagingData="this.pagingData"
                               v-on:setPagingData="setPagingData"></CommonPageNavigator>
                </div>
                <div class="btn-group">
                    <button type="button" v-if="isWrite" class="btn blue" @click="openRegist">운영자 등록</button>
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
import OperatorRegist from "./OperatorRegist.vue";
import OperatorDetail from "./OperatorDetail.vue";
export default {
    name: 'admin.configuration.operator.operatorlist',
    components: {
        CommonNavigator,
        CommonDatePicker,
        CommonPageNavigator,
        OperatorRegist,
        OperatorDetail
    },
    data() {
        return {
            searchData: {
                skey: '',           // 검색조건
                sword: '',          // 검색단어
                stype: '',          // 검색일자조건
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
                visitcnt: 'visitcnt_desc',          // 접속수 정렬기준
                visitdate: 'visitdate_desc',        // 최근접속일시 정렬기준
                regdate: 'regdate_desc',            // 등록일자 정렬기준
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
            permilist: [],     // 등록용 메뉴별 권한 리스트
            userno: '',
            showRegist: false,      // 등록팝업 오픈여부
            showDetail: false,      // 상세팝업 오픈여부
            isRead: false,          // 읽기권한여부
            isWrite: false,         // 쓰기권한여부
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

            this.$http.post('/admin/configuration/operator/operator/search', params)
            .then(result => {
                if(result.statusCode === 200) {
                    let data = result.data;
                    this.listData = data.list;
                    this.pagingData.listCount = data.statelist.total_cnt;
                    this.statelist = data.statelist;
                    this.checkedData = [];
                    this.permilist = data.permissionlist;
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
                alert("선택된 운영자가 없습니다.");
                return false;
            }

            let params = {
                list: this.checkedData,
                istrash: use
            }

            if(confirm("상태를 전환하시겠습니까?")){
                this.$http.post("/admin/configuration/operator/operator/update", params)
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
            this.$http.post("/admin/configuration/operator/operator/excel", param, postConfig)
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
                    this.checkedData[i] = this.listData[i].userno;
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
        openDetail(userno) {
            this.userno = userno;
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