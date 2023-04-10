<template>
    <div class="content m-leftmenu">
        <common-navigator></common-navigator>
        <CategoryRegist v-if="showRegist" v-on:closeRegist="closeRegist"></CategoryRegist>
        <CategoryDetail v-if="showDetail" :idx="idx" v-on:closeDetail="closeDetail"></CategoryDetail>
        <div class="inner">
            <div class="boxing search-area">
                <dl>
                    <dt>직접검색</dt>
                    <dd>
                        <select v-model="searchData.skey">
                            <option value="">전체</option>
                            <option value="subject">제목</option>
                            <option value="regusername">등록자</option>
                        </select>
                        <input type="text" v-model="searchData.sword" @keyup.enter="searchList(1)"/>
                    </dd>
                </dl>
                <dl>
                    <dt>조회기간</dt>
                    <dd>
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
            </div>
            <div class="btn-group">
                <button type="button" class="btn big blue" v-if="isRead" @click="searchList(1)">검색</button>
                <button type="button" class="btn big gray" v-if="isRead" @click="initSearchData">초기화</button>
            </div>
            <div class="caption-group mt10 clearfix">
                <div class="total-group fl">
                    <span class="total">전체 <strong>{{ state.totalcnt }}</strong>건</span>
                    <span>노출 <strong>{{ state.showcnt }}</strong>건</span>
                    <span>미노출 <strong>{{ state.hidecnt }}</strong>건</span>
                </div>
                <div class="btn-group fr">
                    <button type="button" v-if="isWrite" class="btn blue-line" @click="showChange('T')">노출</button>
                    <button type="button" v-if="isWrite" class="btn red-line" @click="showChange('F')">미노출</button>
                    <button type="button" v-if="isWrite" class="btn black-line" @click="deleteList">목록삭제</button>
                    <button type="button" v-if="isRead" class="btn green-line" @click ="fnExcelDownload"><i class="icon-excel"></i>엑셀다운로드</button>
                    <select v-model="pagingData.pageCount">
                        <option value="20">20개씩 보기</option>
                        <option value="50">50개씩 보기</option>
                        <option value="100">100개씩 보기</option>
                    </select>
                </div>
            </div>
            <table cellpadding="0" cellspacing="0" class="data-tb align-c">
                <caption>매거진카테고리관리 목록</caption>
                <colgroup>
                    <col width="3%" /><!-- checkbox -->
                    <col width="3%" /><!-- No -->
                    <col width="" /><!-- 제목 -->
                    <col width="130px" /><!-- 연결된 트렌드 컨텐츠 -->
                    <col width="80px" /><!-- 노출상태 -->
                    <col width="7%" /><!-- 등록자 -->
                    <col width="7%" /><!-- 등록일자 -->
                </colgroup>
                <thead>
                    <tr>
                        <th><input type="checkbox" id="chkall" v-model="isallchk" @change="checkAllList($event.target.checked)"/></th>
                        <th>No</th>
                        <th>제목</th>
                        <th>연결된 트렌드 컨텐츠</th>         
                        <th>노출상태</th>
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
                <tbody v-if="this.list.length > 0">
                    <tr v-for="(row, index) in this.list" :key="index">
                        <td><input type="checkbox" v-model="checkedList" :id="row.mzcateidx" :value="row.mzcateidx" @change="checkList($event.target.checked)"/></td>
                        <td>{{ row.no }}</td>
                        <td class="left"><a class="link" @click="goDetail(row.mzcateidx)">{{ row.subject }}</a></td>
                        <td>{{ row.contentscnt }}</td>
                        <td>{{ row.isdisplay }}</td>
                        <td>{{ row.regusername }}</td>
                        <td>{{ row.regdate }}</td>
                    </tr>
                </tbody>
                <tbody v-else>
                    <tr><td colspan="7">조회 결과가 존재하지 않습니다.</td></tr>
                </tbody>
            </table>
            <div class="bottom-group">
                <div class="paging">
                    <CommonPageNavigator v-show="isRead" :pagingData="pagingData" v-on:setPagingData="setPagingData" />
                </div>
                <div class="btn-group">
                    <button type="button" class="btn blue" v-if="isWrite" @click="goRegist">카테고리 등록</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import CommonNavigator from '@views.admin/common/CommonNavigator.vue';
import CommonDatePicker from '@views.admin/common/CommonDatePicker';
import CommonPageNavigator from "@views.admin/common/CommonPageNavigator";
import CategoryRegist from './CategoryRegist.vue';
import CategoryDetail from './CategoryDetail.vue';
export default {
    name: 'admin.operation.magazine.categorylist',
    components: {
        CommonNavigator,
        CommonDatePicker,
        CategoryRegist,
        CommonPageNavigator,
        CategoryDetail
    },
    data() {
        return {
            searchData: {
                skey: '',
                sword: '',
                startDate: '',
                endDate: '',
                period: '3',
                isdisplay: '',
                psort: '',
            },
            state: {
                totalcnt: 0,
                showcnt: 0,
                hidecnt: 0,
            },
            pagingData: {
                pageCount: 20,      // 페이징 옵션(최대수)
                page: 1,            // 현재 페이지
                listCount: 0        // 총 수량
            },
            sortData: {
                regdate: 'regdate_desc',                // 등록일자 정렬기준
            },
            list: [],
            isallchk: false,
            checkedList: [],
            showRegist: false,
            showDetail: false,
            isRead: false,
            isWrite: false,
        }
    },
    mounted() {
        this.$http.post('/admin/common/pageAuth/check', {url : this.$options.name, isloading: false}).then(result => {
            this.isRead = (result.data.isread === 'T');
            this.isWrite = (result.data.iswrite === 'T');

            if(this.isRead){
                this.initSearchData();
            }
        }).catch(error => {
            this.$util.debug(error);
        });
    },
    methods: {
        // 검색조건 초기화
        initSearchData: function () {
            this.searchData = this.$options.data().searchData;
            this.searchData.startDate = this.$util.getAddMonth(this.$util.getDate(''), -3, '-');
            this.searchData.endDate = this.$util.getDate('-');

            this.searchList();
        },
        // 검색
        searchList(page) {
            let param = this.searchData;
            param.pageCount = this.pagingData.pageCount;
            param.page = (typeof page === 'undefined') ? this.pagingData.page : 1;
            param.listCount = this.pagingData.listCount;

            this.$http.post("/admin/operation/magazine/category/search", param)
            .then(result => {
                let data = result.data;
                this.list = data.list;
                this.state = data.state;
                this.pagingData.listCount = data.state.totalcnt;
                this.$util.dataSetSearchParam(this);
                this.isallchk = false;
                this.checkedList = [];
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
        showChange(show) {
            if(this.checkedList.length === 0) {
                alert("체크된 리스트가 없습니다.");
                return;
            }

            let params = {
                idxlist : this.checkedList,
                isdisplay : show,
                isloading: false
            }
            
            if(show === 'F') { 
                params.overmsg = '상태를 변경 하시겠습니까?';
                params.zeromsg = '미노출';
    
                this.$http.post("/admin/operation/magazine/category/check", params)
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
                params.msg = "상태를 변경 하시겠습니까?";
                this.update(params);
            }
        },
        update(params) {
            if(confirm(params.msg)) {
                this.$http.post("/admin/operation/magazine/category/update", params)
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
                istrash : 'T',
                overmsg : '삭제 하시겠습니까?',
                zeromsg : '삭제',
                isloading: false
            }

            this.$http.post("/admin/operation/magazine/category/check", params)
            .then(result => {
                if (result.statusCode == 200) {
                    let msg = result.data.msg;

                    if(confirm(msg)) {
                        this.$http.post("/admin/operation/magazine/category/update", params)
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
            this.$http.post('/admin/operation/magazine/category/excel', this.searchData, config)
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
                    this.checkedList.push(item.mzcateidx);
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
    }
}
</script>

<style>

</style>