<template>
    <div>
        <!-- 관리자메모 팝업 -->
        <StatusMemo v-if="isMemoShow" ref="statusMemo"
        v-bind:row="row" v-on:closeMemo="closeMemo"/>
        <!-- 컨텐츠 영역 -->
        <div class="content m-leftmenu">
            <AdminCommonNavigator/>
            <div class="inner">
                <div class="boxing search-area">
                    <dl>
                        <dt>입점일자</dt>
                        <dd>
                            <!-- <input type="date" v-model="searchData.startDate"/><span>-</span><input type="date" v-model="searchData.endDate"/> -->
                            <CommonDatePicker :value="searchData.startDate" @change="onChangeStartDate"/>
                            <span>-</span>
                            <CommonDatePicker :value="searchData.endDate" @change="onChangeEndDate"/>
                            <div class="radio_wrap">
                            <input type="radio" v-model="searchData.period" id="rd1" value='-1'/><label for="rd1">어제</label>
                            <input type="radio" v-model="searchData.period" id="rd2" value='0'/><label for="rd2">오늘</label>
                            <input type="radio" v-model="searchData.period" id="rd3" value='7'/><label for="rd3">일주일</label>
                            <input type="radio" v-model="searchData.period" id="rd4" value='1'/><label for="rd4">1개월</label>
                            <input type="radio" v-model="searchData.period" id="rd5" value='3'/><label for="rd5">3개월</label>
                            <input type="radio" v-model="searchData.period" id="rd6" value='6'/><label for="rd6">6개월</label>
                            <input type="radio" v-model="searchData.period" id="rd7" value='100'/><label for="rd7">전체</label>
                            </div>
                        </dd>
                    </dl>
                    <dl>
                        <dt>입점상태</dt>
                        <dd>
                            <div class="radio_wrap">
                            <input type="radio" name="status" id="rd11" value="" v-model="searchData.status"/><label
                                for="rd11">전체</label>
                            <input type="radio" name="status" id="rd22" value="DST001" v-model="searchData.status"/><label
                                for="rd22">운영중</label>
                            <input type="radio" name="status" id="rd33" value="DST002" v-model="searchData.status"/><label
                                for="rd33">일시중단</label>
                            <input type="radio" name="status" id="rd44" value="DST003" v-model="searchData.status"/><label
                                for="rd44">휴점</label>
                            </div>
                        </dd>
                    </dl>
                    <dl>
                        <dt>직접검색</dt>
                        <dd>
                            <select v-model="searchData.skey">
                            <option value="">전체</option>
                            <option value="id">아이디</option>
                            <option value="name">업체명</option>
                            <!-- <option value="charger">담당자</option> -->
                            <option value="bizno">사업자등록번호</option>
                            <option value="mobile">대표자 휴대폰</option>
                            </select>
                            <input type="text" v-model="searchData.sword" @keyup.enter="onSearch(1)"/>
                        </dd>
                    </dl>
                </div>
                <div class="btn-group" v-if="isRead">
                    <button type="button" class="btn big blue" @click="onSearch(1)">검색</button>
                    <button type="button" class="btn big gray" @click="onSearchDataReset">초기화</button>
                </div>
                <div class="caption-group mt10 clearfix">
                <div class="total-group fl">
                    <span class="total">전체 <strong>{{totalCnt}}</strong>건</span>
                    <span>운영중 <strong>{{operationCnt}}</strong>건</span>
                    <span>일시중단 <strong>{{suspendCnt}}</strong>건</span>
                    <span>휴점 <strong>{{closedCnt}}</strong>건</span>
                </div>
                <div class="btn-group fr">
                    <button type="button" class="btn blue-line" v-if="isWrite" @click="onChangeStatus('DST001')">운영중</button>
                    <button type="button" class="btn blue-line" v-if="isWrite" @click="onChangeStatus('DST002')">일시중단</button>
                    <button type="button" class="btn red-line" v-if="isWrite" @click="onChangeStatus('DST004')">퇴점처리</button>
                    <button type="button" class="btn green-line" v-if="isRead" @click="fnExcelDownload()"><i class="icon-excel"></i>엑셀다운로드</button>
                    <select v-model="pagingData.pageCount" v-if="isRead">
                    <option value="20">20개씩 보기</option>
                    <option value="50">50개씩 보기</option>
                    <option value="100">100개씩 보기</option>
                    </select>
                </div>
                </div>
                <table cellpadding="0" cellspacing="0" class="data-tb align-c">
                    <caption>파트너사현황</caption>
                        <colgroup>
                        <col width="3%"/><!-- checkbox -->
                        <col width="9%"/><!-- 아이디 -->
                        <col width=""/><!-- 업체명 -->
                        <col width="8%"/><!-- 사업자등록번호 -->
                        <col width="6%"/><!-- 담당자 -->
                        <col width="9%"/><!-- 전화번호 -->
                        <col width="9%"/><!-- 휴대폰번호 -->
                        <col width="5%"/><!-- 담당MD -->
                        <col width="8%"/><!-- 입점일-->
                        <col width="5%"/><!-- 전시상품 -->
                        <col width="6%"/><!-- 상태 -->
                        <col width="3%"/><!-- 메모 -->
                        <col width="5%"/><!-- 계약요청 -->
                        <col width="5%"/><!-- 계약서 -->
                        </colgroup>
                    <thead>
                        <tr>
                            <th><input type="checkbox" id="chkall" v-model="isChecked" @change="onCheckAll($event.target.checked)"/>
                            </th>
                            <th>아이디<button type="button" v-if="isRead" :value="sortData.id" class="sort" :class="[{up : sortData.id === 'id_asc'}, {down : sortData.id === 'id_desc'}]" @click="sortToggle(sortData.id)"></button></th>
                            <th>업체명<button type="button" v-if="isRead" :value="sortData.name" class="sort" :class="[{up : sortData.name === 'name_asc'}, {down : sortData.name === 'name_desc'}]" @click="sortToggle(sortData.name)"></button></th>
                            <th>사업자등록번호</th>
                            <th>제휴 담당자<button type="button" v-if="isRead" :value="sortData.charger" class="sort" :class="[{up : sortData.charger === 'charger_asc'}, {down : sortData.id === 'charger_desc'}]" @click="sortToggle(sortData.charger)"></button></th>
                            <th>담당자 연락처</th>
                            <th>대표자 휴대폰</th>
                            <th>담당MD</th>
                            <th>입점일<button type="button" v-if="isRead" :value="sortData.regdate" class="sort" :class="[{up : sortData.regdate === 'regdate_asc'}, {down : sortData.regdate === 'regdate_desc'}]" @click="sortToggle(sortData.regdate)"></button></th>
                            <th>전시상품</th>
                            <th>상태</th>
                            <th>메모</th>
                            <th>계약요청</th>
                            <th>계약서</th>
                        </tr>
                    </thead>
                    <tbody v-if="this.listData.length > 0">
                        <tr v-for="(row,  i) in this.listData" :key="i">
                            <td><input type="checkbox" v-model="listCheckData" :value="row.no" :id="'chk'+i"/></td>
                            <td>{{ row.dealer_id }}</td>
                            <td>{{ row.name }}</td>
                            <td>{{ $util.bizNumberFilter(row.bizno, 0) }}</td>
                            <td>{{ row.chargename }}</td>
                            <td>{{ $util.phoneNumberFilter(row.tel) }}</td>
                            <td>{{ $util.phoneNumberFilter(row.repmobile) }}</td>
                            <td>{{ row.mdname }}</td>
                            <td>{{ row.regdateyyyymmdd }}</td>
                            <td>{{ row.goodscnt }}</td>
                            <td>{{ row.dealerst }}</td>
                            <td><a class="link" @click="goMemo(row)"
                                    style="cursor:pointer;">{{ row.memocnt }}</a></td>
                            <td>
                                <button type="button" class="btn blue-line" @click="requestCont(row.no)" v-if="row.dealercontst === 'DCS001'">계약요청</button>
                                {{row.dealercontst === 'DCS002' ? '계약중' : ''}}
                                {{row.dealercontst === 'DCS003' ? '계약완료' : ''}}
                            </td>
                            <td ><button type="button" class="btn blue-line" @click="downContract(row.moduid)" 
                                v-if="row.dealercontst === 'DCS003'" :disabled="contract.isread !=='T'">계약서</button></td>
                        </tr>
                    </tbody>
                    <tbody v-else> 
                        <tr>
                            <td colspan="14">조회 결과가 존재하지 않습니다.</td>
                        </tr>
                    </tbody>
                </table>
                <div class="bottom-group">
                    <CommonPageNavigator v-bind:pagingData="this.pagingData" v-on:setPagingData="setPagingData"/>
                </div>
            </div>
        </div>
  </div>
  <!-- /컨텐츠 영역 -->
</template>

<script>
import AdminCommonNavigator from "../../common/CommonNavigator";
import CommonPageNavigator from "../../common/CommonPageNavigator";
import CommonDatePicker from "@views.admin/common/CommonDatePicker";
import StatusMemo from "./StatusMemo";
// import Xlsx from "xlsx";

export default {
    name: 'admin.partners.status',
    components: {      
        AdminCommonNavigator,
        CommonPageNavigator,
        CommonDatePicker,
        StatusMemo
    },
    data() {
        return {
            searchData:{
                startDate: '',  // 시작년도
                endDate: '',    // 종료년도
                skey: '',       // 검색조건
                sword: '',      // 검색단어
                status: '',   // 입점상태
                period: '3',    // 날짜 검색 옵션
                page: 1,        // 현재 페이지
                ignoreperiod: 'F',
            }, 
            pagingData: {
                pageCount: 20, // 페이징 옵션 (최대수)
                page: 1,       // 현재 페이지
                listCount: 0   // 총 페이지
            },
            sortData: {
                id : 'id_desc',
                name : 'name_desc',
                charger : 'charger_desc',
                regdate : 'regdate_desc'
            },
            row: {},
            isChecked: false,   // 전체 체크
            listData: [],       // response 데이터 리스트
            listCheckData: [],  // 체크된 데이터 리스트
            totalCnt: 0,    // 전체
            operationCnt: 0, // 운영중
            suspendCnt: 0,   // 일시중단
            closedCnt: 0,    // 휴점
            isMemoShow: false, // 관리자메모 열림 여부
            isRead : false,
            isWrite : false,
            contract : {isread:'F',iswrite:'F'},
        }
    },
    mounted(){
        this.$http.post('/admin/common/pageAuth/check', {url : this.$options.name, isloading: false, isComponentAuth: true}).then(result => {
            this.isRead = (result.data.isread === 'T');
            this.isWrite = (result.data.iswrite === 'T');

            if(result.data.componentauth.find(x => x.id == "contract") != undefined){
               this.contract = result.data.componentauth.find(x => x.id == "contract");
            }
            this.searchData.startDate = this.$util.getAddMonth(this.$util.getDate(''), -3, '-');
            this.searchData.endDate = this.$util.getDate('-');
            
            if(this.isRead){
                this.onSearch();
                this.$util.componentSetSearchParam(this);
            }
        }).catch(error => {
            this.$util.debug(error);
        });       
    },
    methods: {
        onSearch(page) {
            this.isChecked = false;

            let param = this.searchData;
            param.pageCount = this.pagingData.pageCount;
            param.page = (typeof page === 'undefined') ? this.pagingData.page : 1;
            param.listCount = this.pagingData.listCount;
            this.$http.post("/admin/partners/status/search", param)
                .then(result => {
                    let data = result.data;
                    this.listData = data.list;
                    this.pagingData.listCount = data.listcount;
                    this.totalCnt = data.totalcnt;
                    this.operationCnt = data.operationcnt;
                    this.suspendCnt = data.suspendcnt;
                    this.closedCnt = data.closedcnt;
                    this.listCheckData = [];

                    this.$util.dataSetSearchParam(this);
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        onChangeStatus(status){
            let params = {
                user_no_arr_str: this.listCheckData,
                status: status,
            }

            if(this.listCheckData.length === 0){
                alert("체크된 리스트가 없습니다.");
            }else{
                let showMsg = "";
                switch(status){
                    case "DST001" :
                        showMsg = "운영중 상태로 변경하시겠습니까?";
                        break;
                    case "DST002" :
                        showMsg = "일시중단 상태로 변경하시면 파트너사의 전시상품이 비 노출상태로 변경됩니다. 그래도 하시겠습니까?";
                        break;
                    case "DST004" : 
                        showMsg = "퇴점처리 하시겠습니까?";
                }
                if(confirm(showMsg)){
                    this.$http.post("/admin/partners/status/change.do", params)
                    .then(result => {
                        this.$util.debug(result);
                        if(result.data.msg === "success"){
                            alert("변경이 완료되었습니다.");
                            this.listCheckData = [];
                            this.onSearch();
                        }else{
                            alert("변경을 실패했습니다.");
                        } 
                    })
                    .catch(error => {
                        this.$util.debug(error);
                    });
                }
            }
        },
        onCheckAll(checked){
            if(checked){
                for(let i in this.listData){
                    this.listCheckData[i] = this.listData[i].no;
                }
            } else{
                this.listCheckData = [];
            }
        },
        onSearchDataReset(){
            this.searchData.skey = '';
            this.searchData.sword = '';
            this.searchData.status = '';
            this.searchData.period = '3';
            this.searchData.startDate = this.$util.getAddMonth(this.$util.getDate(''), -3, '-');
            this.searchData.endDate = this.$util.getDate('-');
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
        goMemo(row){
            this.row = row;
            this.isMemoShow = true;
        },
        closeMemo(isreload){
            this.isMemoShow = false;
            if(isreload) {
                this.onSearch();
            }
        },
        // 날짜 picker 콜백 함수
        onChangeStartDate(val) {
            this.searchData.startDate = val;
        },

        // 날짜 picker 콜백 함수
        onChangeEndDate(val) {
            this.searchData.endDate = val;
        },
        fnExcelDownload(){
            let param = this.searchData;
            param.time = this.$util.getDate()+this.$util.getTime();
            let postConfig = { responseType: 'arraybuffer' };
            this.$http.post("/admin/partners/status/excel", param, postConfig)
                .then(result => {
                  this.$util.debug(result);
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 계약요청
        requestCont(no){
            let params = {
                userno : no
            };

            this.$http.post("/admin/partners/status/contract", params)
                .then(result => {
                    if(result.data.code === 'success'){
                        alert("계약요청이 완료되었습니다.");
                        this.onSearch();
                    } else {
                      alert("계약요청 중 에러가 발생하였습니다.");
                    }
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 계약서 pdf 다운로드
        downContract(moduId){
            let params = {
                moduid  : moduId
            };

            this.$http.post("/admin/partners/status/contract/download", params)
                .then(result => {
                  window.open(result.data.contract_url, "_blank");
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        }
    },
    watch: {
        'searchData.period': function(val){
            let dayType = ["-1", "0", "7"];
            let monthType = ["1", "3", "6"];
            let valueToInt = parseInt(val);

            if(val === "100") {
              this.searchData.ignoreperiod = "T";
              return;
            }
            if(dayType.includes(val)){
                if(valueToInt >= 0){
                    this.searchData.startDate = this.$util.getAddDate(this.$util.getDate(''), (-1 * valueToInt), '-');
                    this.searchData.endDate = this.$util.getDate('-');
                }else{
                    this.searchData.startDate = this.$util.getAddDate(this.$util.getDate(''), valueToInt, '-');
                    this.searchData.endDate = this.$util.getAddDate(this.$util.getDate(''), valueToInt, '-');
                }
            }else if(monthType.includes(val)){
                this.searchData.startDate = this.$util.getAddMonth(this.$util.getDate(''), (-1 * valueToInt), '-');
                this.searchData.endDate = this.$util.getDate('-');
            }
            this.searchData.ignoreperiod = "F";
        }
    }
}
</script>

<style>

</style>