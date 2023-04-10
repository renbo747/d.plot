<template>
    <!-- 추천리워드 이력조회 팝업 -->
    <div id="modal-wrap" class="modal" style="display: block;">
        <div class="modal-content" style="width: 1200px;">
            <div class="pop-header">
                <h2>추천리워드 이력조회</h2>
                <button type="button" class="pop-close" @click="onClose"></button>
            </div>
            <div class="pop-body">
                <div class="boxing search-area">
                    <dl>
                        <dt>직접검색</dt>
                        <dd>
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
                                <input type="radio" v-model="searchData.period" name="period" id="rd1" value="-1" /><label for="rd1">어제</label>
                                <input type="radio" v-model="searchData.period" name="period" id="rd2" value="0" /><label for="rd2">오늘</label>
                                <input type="radio" v-model="searchData.period" name="period" id="rd3" value="7" /><label for="rd3">일주일</label>
                                <input type="radio" v-model="searchData.period" name="period" id="rd4" value="1" checked/><label for="rd4">1개월</label>
                                <input type="radio" v-model="searchData.period" name="period" id="rd5" value="3" /><label for="rd5">3개월</label>
                                <input type="radio" v-model="searchData.period" name="period" id="rd6" value="6" /><label for="rd6">6개월</label>
                            </div>
                        </dd>
                    </dl>
                    <dl>
                        <dt>혜택구분</dt>
                        <dd>
                            <span>
                                <input type="checkbox" v-model="searchData.isallrecom" id="chk01" true-value="T" false-value="F" @change="checkAllRecom" checked>
                                <label for="chk01">전체</label>
                            </span>
                            <span class="check-for" v-for="item in commonCode.RECOMTYPE" :key="item.cmcode">
                                <input type="checkbox" v-model="searchData.recomtypearr" :id="'chk'+item.cmcode" true-value="[]" :value="item.cmcode">
                                <label :for="'chk'+item.cmcode">{{item.codename}}</label>
                            </span>
                        </dd>
                    </dl>
                </div>
                <div class="btn-group" v-if="isRead">
                    <button type="button" class="btn big blue" @click="onSearch(1)">검색</button>
                    <button type="button" class="btn big gray" @click="onSearchDataReset">초기화</button>
                </div>
                <div class="caption-group mt10 clearfix">
                    <div class="total-group fl">
                        <span class="total">전체 <strong>{{pagingData.listCount}}</strong>건</span>
                    </div>
                    <div class="btn-group fr">
                        <button type="button" class="btn green-line" v-if="isRead" @click="fnExcelDownload"><i class="icon-excel"></i>엑셀다운로드</button>
                        <select v-model="pagingData.pageCount" v-if="isRead">
                            <option value="20">20개씩 보기</option>
                            <option value="50">50개씩 보기</option>
                            <option value="100">100개씩 보기</option>
                        </select>
                    </div>
                </div>
                <div class="scroll-y" style="max-height: 500px;">
                    <table cellpadding="0" cellspacing="0" class="data-tb align-c" style="margin-left: 0;">
                        <colgroup>
                            <col width="3.5%" /><!-- No -->
                            <col width="7%" /><!-- 혜택구분 -->
                            <col width="" /><!-- 진행기간 -->
                            <col width="12%" /><!-- 피추천인지급 -->
                            <col width="12%" /><!-- 피추천인 회원가입 시 지급 -->
                            <col width="12%" /><!-- 피추천인이 첫 구매확정 시 지급 -->
                            <col width="11%" /><!-- 소멸일자(D포인트) -->
                            <col width="8%" /><!-- 중복사용여부(D포인트) -->
                            <col width="6%" /><!-- 피추천인수 제한 -->
                            <col width="7%" /><!-- 등록자 -->
                            <col width="9%" /><!-- 저장일자 -->
                        </colgroup>
                        <thead>
                            <tr>
                                <th>No</th>
                                <th>혜택구분</th>
                                <th>진행기간</th>
                                <th>신규회원 지급</th>
                                <th>기존회원 지급<br>(신규회원 가입 시)</th>
                                <th>기존회원 지급<br>(신규회원 첫 구매확정 시)</th>
                                <th>소멸일자<br>(D포인트)</th>
                                <th>중복사용여부<br>(D포인트)</th>
                                <th>추천받는 수<br>제한
                                        <button type="button" v-if="isRead"
                                            :value="sortData.reclimitcnt" 
                                            class="sort"
                                            :class="[{up : sortData.reclimitcnt === 'reclimitcnt_asc'}, {down : sortData.reclimitcnt === 'reclimitcnt_desc'}]"
                                            @click="sortToggle(sortData.reclimitcnt)">
                                        </button>
                                </th>
                                <th>등록자</th>
                                <th>저장일자
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
                            <tr v-for="(row,i) in this.listData" :key="i">
                                <td>{{row.no}}</td>
                                <td>{{!$util.isNull(row.recomtype) ? row.recomtype : '-'}}</td>
                                <td>{{!$util.isNull(row.recomstday) || !$util.isNull(row.recomedday) ? ($util.isNull(row.recomstday) ? '' : row.recomstday) + ' ~ ' + ($util.isNull(row.recomedday) ? '' : row.recomedday) : '-'}}</td>
                                <td>{{!$util.isNull(row.revpoint) ? row.revpoint : '-'}}</td>
                                <td>{{!$util.isNull(row.recjoinpoint) && row.isjoingive == 'T' ? row.recjoinpoint : '지급안함'}}</td>
                                <td>{{!$util.isNull(row.reccfmpoint) && row.iscfmgive == 'T' ? row.reccfmpoint : '지급안함'}}</td>
                                <td>{{row.limitdate}}</td>
                                <td>{{row.isepointdup}}</td>
                                <td>{{row.reclimitcnt}}</td>
                                <td>{{row.regusername}}</td>
                                <td>{{row.regdate}}</td>
                            </tr>
                        </tbody>
                        <tbody v-else>
                            <tr><td colspan="11">대상 리워드가 없습니다.</td></tr>
                        </tbody>
                    </table>
                </div>
                <div class="bottom-group">
                    <CommonPageNavigator :pagingData="pagingData" v-on:setPagingData="setPagingData"/>
                </div>
                <div class="btn-group mt20">
                    <button type="button" class="btn big darkgray" @click="onClose">닫기</button>
                </div>
            </div>
        </div>
    </div>
    <!-- /추천리워드 이력조회 팝업 -->
</template>

<script>
import CommonDatePicker from "@views.admin/common/CommonDatePicker";
import CommonPageNavigator from "../../common/CommonPageNavigator";
export default {
    name: 'admin.promotion.promotion.recomrewardlog',
    components: {
        CommonDatePicker,
        CommonPageNavigator
    },
    data() {
        return {
            searchData: {
                sword: '',          // 검색단어
                startdate: '',      // 시작일자
                enddate: '',        // 종료일자
                period: 3,          // 검색기간
                isallrecom: 'T',    // 혜택구분 전체체크여부
                recomtypearr: [],   // 혜택구분 배열
                recomtype: '',      // 혜택구분
            },
            commonCode: {
                RECOMTYPE: '',
            },
            pagingData: {
                pageCount: 20,      // 페이징 옵션 (최대수)
                page: 1,            // 현재 페이지
                listCount: 0        // 총 페이지
            },
            sortData: {
                reclimitcnt : 'reclimitcnt_desc',
                regdate : 'regdate_desc'
            },
            listData: [],  
            isRead: false,
            isWrite: false,
        }
    },
    mounted() {
        this.$http.post('/admin/common/pageAuth/check', {url : this.$options.name, isloading: false}).then(result => {
            this.isRead = (result.data.isread === 'T');
            this.isWrite = (result.data.iswrite === 'T');

            if(this.isRead){
                this.oninit();
            } else {
                alert('페이지 접근 권한이 없습니다.');
                this.onClose();
            }
        }).catch(error => {
            this.$util.debug(error);
        });
    },
    methods: {
        oninit(){
            this.searchData.startdate = this.$util.getAddMonth(this.$util.getDate(''), -3, '-');
            this.searchData.enddate = this.$util.getDate('-');

            // 공통코드 조회
            this.getCommonCodeList();
        },
        getCommonCodeList(){
            for(const [key] of Object.entries(this.commonCode)) {
                let params = { cmclass : key };
                this.$http.post('/common/code/list', params)
                .then(result => {
                    if(result.statusCode === 200){
                        this.commonCode[key] = result.data.list;
                        // 혜택구분 초기값 전체선택
                        if(key === 'RECOMTYPE'){
                            this.checkAllRecom();
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
        },
        checkAllRecom() {
            let isAllCheck = this.searchData.isallrecom;
            this.searchData.recomtypearr = [];
            if (isAllCheck === 'T') {
                for(let type of this.commonCode.RECOMTYPE){
                    this.searchData.recomtypearr.push(type.cmcode);
                }
            }
        },
        onSearch(page) {
            let param = this.searchData;
            param.pageCount = this.pagingData.pageCount;
            param.page = (typeof page === 'undefined') ? this.pagingData.page : 1;
            param.listCount = this.pagingData.listCount;

            this.$http.post("/admin/promotion/promotion/reward/log/search", param)
            .then(result => {
                let data = result.data;
                this.listData = data.list;
                this.pagingData.listCount = data.listcount;

                this.$util.dataSetSearchParam(this);
            })
            .catch(error => {
                this.$util.debug(error);
            });
        },
        onSearchDataReset(){
            this.searchData = this.$options.data().searchData;
            this.searchData.startdate = this.$util.getAddMonth(this.$util.getDate(''), -3, '-');
            this.searchData.enddate = this.$util.getDate('-');
            this.checkAllRecom();
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
            this.searchData.startdate = val;
        },
        // 날짜 picker 콜백 함수
        onChangeEndDate(val) {
            this.searchData.enddate = val;
        },
        fnExcelDownload(){
            let param = this.searchData;
            param.time = this.$util.getDate()+this.$util.getTime();
            let postConfig = { responseType: 'arraybuffer' };
            this.$http.post("/admin/promotion/promotion/reward/log/excel", param, postConfig)
            .then(result => {
                this.$util.debug(result);
            })
            .catch(error => {
                this.$util.debug(error);
            });
        },
        onClose() {
            this.searchData = this.$options.data().searchData;
            this.pagingData = this.$options.data().pagingData;
            this.sortData = this.$options.data().sortData;
            this.$emit('closeList');
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
                    this.searchData.enddate = this.$util.getAddDate(this.$util.getDate(''), valueToInt, '-');
                }
            }else if(monthType.includes(val)){
                this.searchData.startdate = this.$util.getAddMonth(this.$util.getDate(''), (-1 * valueToInt), '-');
                this.searchData.enddate = this.$util.getDate('-');
            }
        },
        // 회원 등급 체크 상태 검사
        'searchData.recomtypearr'(value) {
        if (value.length < this.commonCode.RECOMTYPE.length) {
            this.searchData.isallrecom = 'F';
        } else {
            this.searchData.isallrecom = 'T';
        }
        this.searchData.recomtype = this.searchData.recomtypearr.join();
        },
    }
}
</script>

<style>

</style>