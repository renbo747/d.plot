<template>
    <div class="content m-leftmenu">
        <common-navigator></common-navigator>
        <NoticeRegist v-if="showRegist" v-bind:commonCodeList="commonCode" v-on:closeRegist="closeRegist"></NoticeRegist>
        <NoticeDetail v-if="showDetail" :commonCodeList="commonCode" :boardIdx="idx" v-on:closeDetail="closeDetail"></NoticeDetail>
        <div class="inner">
            <div class="boxing search-area">
                <dl>
                    <dt>직접검색</dt>
                    <dd>
                        <select v-model="searchData.skey">
                            <option value="">전체</option>
                            <option value="subject">제목</option>
                            <option value="regusername">작성자</option>
                        </select>
                        <input type="text" v-model="searchData.sword" @keyup.enter="searchList(1)"/>
                    </dd>
                </dl>
                <dl>
                    <dt>조회기간</dt>
                    <dd>
                        <select v-model="searchData.stype">
                            <option value="">전체</option>
                            <option value="poststtime">시작일자</option>
                            <option value="topedtime">종료일자</option>
                            <option value="regdate">등록일자</option>
                        </select>
                        
                        <common-date-picker :value="searchData.startDate" @change="onChangeStartDate"></common-date-picker>
                        <span>-</span>
                        <common-date-picker :value="searchData.endDate" @change="onChangeEndDate"></common-date-picker>
                        <div class="radio_wrap">
                            <input type="radio" name="period" id="rd1" v-model="searchData.period" value="-1"/><label for="rd1">어제</label>
                            <input type="radio" name="period" id="rd2" v-model="searchData.period" value="0"/><label for="rd2">오늘</label>
                            <input type="radio" name="period" id="rd3" v-model="searchData.period" value="7"/><label for="rd3">일주일</label>
                            <input type="radio" name="period" id="rd4" v-model="searchData.period" value="1"/><label for="rd4">1개월</label>
                            <input type="radio" name="period" id="rd5" v-model="searchData.period" value="3" checked/><label for="rd5">3개월</label>
                            <input type="radio" name="period" id="rd6" v-model="searchData.period" value="6"/><label for="rd6">6개월</label>
                        </div>
                    </dd>
                </dl>
                <dl>
                    <dt>노출상태</dt>
                    <dd>
                        <div class="radio_wrap wide3">
                            <input type="radio" name="group00" id="group01" v-model="searchData.isdisplay" value="" checked><label for="group01">전체</label>
                            <input type="radio" name="group00" id="group02" v-model="searchData.isdisplay" value="T"><label for="group02">노출</label>
                            <input type="radio" name="group00" id="group03" v-model="searchData.isdisplay" value="F"><label for="group03">미노출</label>
                        </div>
                    </dd>
                </dl>
                <dl>
                    <dt>노출채널</dt>
                    <dd>
                        <div class="check-wrap">
                            <input type="checkbox" v-model="searchData.isallmuappch" id="isallmuappch" true-value="T" false-value="F" @change="checkAllAppchtype">
                            <label for="isallmuappch">전체</label>
                        </div>
                        <div class="check-wrap" v-for="item in commonCode.muappchtype" :key="item.cmcode">
                            <input type="checkbox" v-model="searchData.muappchtypearr" :id="'muappchtype_'+item.cmcode" true-value="[]" :value="item.cmcode">
                            <label :for="'muappchtype_'+item.cmcode">{{item.codename}}</label>
                        </div>
                    </dd>
                </dl>
                <dl>
                    <dt>상단고정여부</dt>
                    <dd>
                        <div class="radio_wrap wide3">
                            <input type="radio" name="group02" id="group21" v-model="searchData.istopfix" value="" checked><label for="group21">전체</label>
                            <input type="radio" name="group02" id="group22" v-model="searchData.istopfix" value="F"><label for="group22">일반</label>
                            <input type="radio" name="group02" id="group23" v-model="searchData.istopfix" value="T"><label for="group23">고정</label>
                        </div>
                    </dd>
                </dl>
                <dl>
                    <dt>게시상태</dt>
                    <dd>
                        <div class="check-wrap">
                            <input type="checkbox" v-model="searchData.isallpostst" id="isallpostst" true-value="T" false-value="F" @change="checkAllPostSttype">
                            <label for="isallpostst">전체</label>
                        </div>
                        <div class="check-wrap" v-for="item in commonCode.postst" :key="item.cmcode">
                            <input type="checkbox" v-model="searchData.poststarr" :id="'poststtype_'+item.cmcode" true-value="[]" :value="item.cmcode">
                            <label :for="'poststtype_'+item.cmcode">{{item.codename}}</label>
                        </div>
                    </dd>
                </dl>
                <dl>
                    <dt>대상회원유형</dt>
                    <dd>
                        <div class="check-wrap">
                            <input type="checkbox" v-model="searchData.isallmumember" id="isallmumember" true-value="T" false-value="F" @change="checkAllMembertype">
                            <label for="isallmumember">전체</label>
                        </div>
                        <div class="check-wrap" v-for="item in commonCode.mumembertype" :key="item.cmcode">
                            <input type="checkbox" v-model="searchData.mumembertypearr" :id="'mumembertype_'+item.cmcode" true-value="[]" :value="item.cmcode">
                            <label :for="'mumembertype_'+item.cmcode">{{item.codename}}</label>
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
                    <span>노출 <strong>{{ count.usecnt }}</strong>건</span>
                    <span>미노출 <strong>{{ count.trashcnt }}</strong>건</span>
                </div>
                <div class="btn-group fr">
                    <button type="button" v-if="isWrite" class="btn blue-line" @click="useChange('T')">노출</button>
                    <button type="button" v-if="isWrite" class="btn red-line" @click="useChange('F')">미노출</button>
                    <button type="button" v-if="isWrite" class="btn black-line" @click="deleteList">목록삭제</button>
                    <button type="button" v-if="isRead" class="btn green-line" @click="fnExcelDownload"><i class="icon-excel"></i>엑셀다운로드</button>
                    <select v-model="pagingData.pageCount">
                        <option value="20">20개씩 보기</option>
                        <option value="50">50개씩 보기</option>
                        <option value="100">100개씩 보기</option>
                    </select>
                </div>
            </div>
            <table cellpadding="0" cellspacing="0" class="data-tb align-c">
                <caption>공지사항 목록</caption>
                <colgroup>
                    <col width="3%" /><!-- checkbox -->
                    <col width="3%" /><!-- No -->
                    <col width="4%" /><!-- 상단고정 -->
                    <col width="%" /><!-- 제목 -->
                    <col width="6%" /><!-- 유형 -->
                    <col width="5%" /><!-- 예약여부 -->
                    <col width="8%" /><!-- 적용채널 -->
                    <col width="5%" /><!-- 사용여부 -->
                    <col width="6%" /><!-- 작성자 -->
                    <col width="5%" /><!-- 조회수 -->
                    <col width="5%" /><!-- 게시상태 -->
                    <col width="8%" /><!-- 시작일자 -->
                    <col width="8%" /><!-- 종료일자 -->
                    <col width="8%" /><!-- 등록일자 -->
                </colgroup>
                <thead>
                    <tr>
                        <th><input type="checkbox" id="chkall" v-model="isallchk" @change="checkAllList($event.target.checked)"/></th>
                        <th>No</th>
                        <th>상단고정</th>
                        <th>제목</th>
                        <th>유형</th>
                        <th>예약여부</th>
                        <th>노출채널</th>
                        <th>노출상태</th>
                        <th>작성자</th>
                        <th>조회수
                            <button type="button" v-if="isRead" 
                                    :value="sortData.hits"
                                    class="sort"
                                    :class="[{up : sortData.hits === 'hits_asc'}, {down : sortData.hits === 'hits_desc'}]"
                                    @click="sortToggle(sortData.hits)">
                            </button>
                        </th>
                        <th>게시상태</th>
                        <th>게시시작일자
                            <button type="button" v-if="isRead" 
                                    :value="sortData.poststtime"
                                    class="sort"
                                    :class="[{up : sortData.poststtime === 'poststtime_asc'}, {down : sortData.poststtime === 'poststtime_desc'}]"
                                    @click="sortToggle(sortData.poststtime)">
                            </button>
                        </th>
                        <th>상단고정종료일자
                            <button type="button" v-if="isRead" 
                                    :value="sortData.topedtime"
                                    class="sort"
                                    :class="[{up : sortData.topedtime === 'topedtime_asc'}, {down : sortData.topedtime === 'topedtime_desc'}]"
                                    @click="sortToggle(sortData.topedtime)">
                            </button>
                        </th>
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
                <tbody v-if="this.list.length > 0">
                    <tr v-for="(row, index) in this.list" :key="index">
                        <td><input type="checkbox" v-model="checkedList" :id="row.idx" :value="row.idx" @change="checkList($event.target.checked)"/></td>
                        <td>{{row.no}}</td>
                        <td>{{row.istopfix === 'T' ? '●':'-'}}</td>
                        <td class="left"><a class="link" @click="goDetail(row.idx)">{{row.subject}}</a></td>
                        <td>{{row.mumembertype}}</td>
                        <td>{{row.isrightnow}}</td>
                        <td>{{row.muappchtype}}</td>
                        <td>{{row.isdisplay}}</td>
                        <td>{{row.writer}}</td>
                        <td>{{row.hits}}</td>
                        <td>{{row.postst}}</td>
                        <td>{{row.poststtime}}</td>
                        <td>{{row.topedtime}}</td>
                        <td>{{row.regdate}}</td>
                    </tr>
                </tbody>
                <tbody v-else>
                    <tr><td colspan="14">조회 결과가 존재하지 않습니다.</td></tr>
                </tbody>
            </table>
            <div class="bottom-group">
                <div class="paging">
                    <CommonPageNavigator v-show="isRead" :pagingData="pagingData" v-on:setPagingData="setPagingData" />
                </div>
                <div class="btn-group">
                    <button type="button" class="btn blue" v-if="isWrite" @click="goRegist">공지 등록</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import CommonNavigator from '@views.admin/common/CommonNavigator.vue';
import CommonDatePicker from '@views.admin/common/CommonDatePicker';
import CommonPageNavigator from '@views.admin/common/CommonPageNavigator';
import NoticeRegist from "./NoticeRegist.vue"
import NoticeDetail from "./NoticeDetail.vue"
export default {
    name : "admin.operation.noticelist",
    components : {
        CommonNavigator,
        CommonDatePicker,
        CommonPageNavigator,
        NoticeRegist,
        NoticeDetail
    },
    data() {
        return {
            searchData : {
                startDate : '',
                endDate : '',
                skey: '',
                sword: '',
                stype: '', 
                period: '3',
                isdisplay: '',
                istopfix: '',
                isallmuappch: 'T',
                muappchtypearr: [],
                isallmumember: 'T',
                mumembertypearr: [],
                isallpostst: 'T',
                poststarr: [],
            }, 
            pagingData: {
                pageCount: 20,      // 페이징 옵션(최대수)
                page: 1,            // 현재 페이지
                listCount: 0        // 총 수량
            },
            commonCode: {
                muappchtype: [],
                mumembertype: [],
                postst: [{cmcode: 'POS001', codename:'게시전'}, {cmcode: 'POS002', codename:'게시중'}],
            },
            sortData: {
                hits: 'hits_desc',               // 조회수 정렬기준
                poststtime: 'poststtime_desc',   // 시작일자 정렬기준
                topedtime: 'topedtime_desc',   // 종료일자 정렬기준
                regdate: 'regdate_desc',        // 등록일자 정렬기준
            },
            isallchk: false,        // 목록 전체체크여부
            list: [],               // 조회 데이터
            checkedList: [],        // 선택된 목록
            count : {
                totalcnt: 0,        // 전체 수량
                usecnt: 0,          // 사용 수량
                trashcnt: 0,        // 미사용 수량
            },
            idx: '',
            showRegist: false,
            showDetail: false,
            isRead: false,              // 읽기권한여부
            isWrite: false,             // 쓰기권한여부
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
    methods : {
        oninit() {
            // 공통코드 조회
            this.getCommonCodeList();
        },
        // 공통코드 목록 조회
        getCommonCodeList: function() {
            let cmclassArr = ['MUAPPCHTYPE', 'MUMEMBERTYPE'];
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
            this.searchData = this.$options.data().searchData;
            this.searchData.startDate = this.$util.getAddMonth(this.$util.getDate(''), -3, '-');
            this.searchData.endDate = this.$util.getDate('-');

            this.checkAllAppchtype();
            this.checkAllMembertype();
            this.checkAllPostSttype();
        },
        // 검색
        searchList(page) {
            this.isChecked = false;

            let param = this.searchData;
            param.pageCount = this.pagingData.pageCount;
            param.page = (typeof page === 'undefined') ? this.pagingData.page : 1;
            param.listCount = this.pagingData.listCount;

            this.$http.post("/admin/operation/notice/search", param)
            .then(result => {
                let data = result.data;
                this.list = data.list;
                this.count = data.count;
                this.pagingData.listCount = data.count.totalcnt;
                this.$util.dataSetSearchParam(this);
                this.isallchk = false;
                this.checkedList = [];

                if(typeof this.$route.params.type !== 'undefined' && this.$route.params.type === 'LINK'){
                  this.goDetail(this.$route.params.idx);
                }

            })
            .catch(error => {
                this.$util.debug(error);
            });
        },
        // 날짜 picker 콜백 함수
        onChangeStartDate(value) {
            this.searchData.startDate = value;
        },
        // 날짜 picker 콜백 함수
        onChangeEndDate(value) {
            this.searchData.endDate = value;
        },
        // 조회조건 - 적용채널 전체체크
        checkAllAppchtype: function() {
            let isAllCheck = this.searchData.isallmuappch;
            this.searchData.muappchtypearr = [];
            if (isAllCheck == 'T') {
                for(let type of this.commonCode.muappchtype){
                    this.searchData.muappchtypearr.push(type.cmcode);
                }
            }
        },
        // 조회조건 - 적용채널 전체체크
        checkAllMembertype: function() {
            let isAllCheck = this.searchData.isallmumember;
            this.searchData.mumembertypearr = [];
            if (isAllCheck == 'T') {
                for(let type of this.commonCode.mumembertype){
                    this.searchData.mumembertypearr.push(type.cmcode);
                }
            }
        },
        // 조회조건 - 적용채널 전체체크
        checkAllPostSttype: function() {
            let isAllCheck = this.searchData.isallpostst;
            this.searchData.poststarr = [];
            if (isAllCheck == 'T') {
                for(let type of this.commonCode.postst){
                    this.searchData.poststarr.push(type.cmcode);
                }
            }
        },
        // 페이징 콜백
        setPagingData(param){
            this.pagingData = param;
            this.searchList();
        },
        // 정렬조건으로 검색
        sortToggle (key) {
            let arr = key.split('_');
            let sortKey = arr[0];
            let sortOrder = (arr[1] === 'asc') ? 'desc' : 'asc';
            let sortName = sortKey + '_' + sortOrder;

            this.sortData = this.$options.data().sortData;

            this.sortData[sortKey] = sortName;
            this.searchData.psort = sortName;

            this.searchList();
        },
        useChange(use) {
            if(this.checkedList.length === 0) {
                alert("체크된 리스트가 없습니다.");
                return;
            }

            let params = {
                idxlist : this.checkedList,
                isdisplay : use,
                isloading: false
            } 

            if(use === 'F') {
                params.overmsg = '상태를 변경 하시겠습니까?';
                params.zeromsg = '미노출';
    
                this.$http.post("/admin/operation/notice/check", params)
                .then(result => {
                    if (result.statusCode == 200) {
                        params.msg = result.data.msg;
                        this.update(params);
                    }
                })
                .catch(error => {
                    this.$util.debug(error);
                })
            } else {
                params.msg = '상태를 변경하시겠습니까?'; 
                this.update(params);
            }
        },
        update(params) {
            params.isloading = false;
            if(confirm(params.msg)) {
                    this.$http.post("/admin/operation/notice/update", params)
                    .then(result => {
                        if (result.statusCode == 200) {
                            alert("저장이 완료되었습니다.");
                            this.searchList();
                        } else {
                            alert("저장에 실패했습니다.");
                        }
                    })
                    .catch(error => {
                        this.$util.debug(error);
                    })
                }
        },
        deleteList() {
            if (this.checkedList.length == 0) {
                alert("체크된 리스트가 없습니다.");
                return;
            }

            let params = {
                idxlist : this.checkedList,
                overmsg : '삭제 하시겠습니까?',
                zeromsg : '삭제',
                isloading: false
            }

            this.$http.post("/admin/operation/notice/check", params)
            .then(result => {
                if (result.statusCode == 200) {
                    let msg = result.data.msg;

                    if(confirm(msg)) {
                        this.$http.post("/admin/operation/notice/delete", params)
                        .then(result => {
                            if (result.statusCode == 200) {
                                alert("삭제되었습니다.");
                                this.searchList();
                            }
                        })
                        .catch(error => {
                            this.$util.debug(error);
                        })
                    }
                }
            })
            .catch(error => {
                this.$util.debug(error);
            })
        },
        // 엑셀다운로드
        fnExcelDownload: function() {
            if (this.list.length == 0) {
                alert('다운로드할 내역이 존재하지 않습니다.');
                return;
            }
            let config = { responseType: 'arraybuffer' };
            this.$http.post('/admin/operation/notice/excel', this.searchData, config)
                .then(result => {
                    this.$util.debug(result);
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 목록 전체체크
        checkAllList: function(value) {
            this.checkedList = [];
            if (value) {
                this.list.forEach(item => {
                    this.checkedList.push(item.idx);
                });
            }
        },
        // 목록 개별체크
        checkList: function() {
            if (this.list.length > this.checkedList.length){
                this.isallchk = false;
            } else {
                this.isallchk = true;
            }
        },
        goRegist() {
            this.showRegist = true;
        },
        closeRegist(isreload) {
            this.showRegist = false;
             if(isreload) {
                this.searchList();
            }
        },
        goDetail(idx) {
            this.idx = idx;
            this.showDetail = true;
        },
        closeDetail(isreload) {
            this.showDetail = false; 
            if(isreload) {
                this.searchList();
            }
        }
    },
    watch: {
    'searchData.period': function (val) {
        let dayType = ["-1", "0", "7"];
        let monthType = ["1", "3", "6"];
        let valueToInt = parseInt(val);

        if (dayType.includes(val)) {
            if (valueToInt >= 0) {
            this.searchData.startDate = this.$util.getAddDate(this.$util.getDate(''), (valueToInt * -1), '-');
            this.searchData.endDate = this.$util.getDate('-');
            } else {
            this.searchData.startDate = this.$util.getAddDate(this.$util.getDate(''), valueToInt, '-');
            this.searchData.endDate = this.$util.getAddDate(this.$util.getDate(''), valueToInt, '-');
            }
        } else if (monthType.includes(val)) {
            this.searchData.startDate = this.$util.getAddMonth(this.$util.getDate(''), (valueToInt * -1), '-');
            this.searchData.endDate = this.$util.getDate('-');
        }
    },
    'searchData.muappchtypearr': function(value){
        if (value.length < this.commonCode.muappchtype.length) {
            this.searchData.isallmuappch = 'F';
        } else {
            this.searchData.isallmuappch = 'T';
        }
    },
    'searchData.mumembertypearr': function(value){
        if (value.length < this.commonCode.mumembertype.length) {
            this.searchData.isallmumember = 'F';
        } else {
            this.searchData.isallmumember = 'T';
        }
    },
    'searchData.poststarr': function(value){
        if (value.length < this.commonCode.postst.length) {
            this.searchData.isallpostst = 'F';
        } else {
            this.searchData.isallpostst = 'T';
        }
    },
  },
}
</script>

<style>

</style>