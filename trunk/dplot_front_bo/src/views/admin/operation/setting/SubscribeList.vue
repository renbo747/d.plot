<template>
    <div class="content m-leftmenu">
        <common-navigator></common-navigator>
        <div class="inner">
            <div class="boxing search-area">
                <dl>
                    <dt>이메일 검색</dt>
                    <dd>
                        <input type="text" v-model="searchData.sword" style="width: 470px;" />
                    </dd>
                </dl>
                <dl>
                    <dt>조회기간</dt>
                    <dd>
                        <select v-model="searchData.stype">
                            <option value="subregdate">구독신청일</option>
                            <option value="canceldate">구독해지일</option>
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
            </div>
            <div class="btn-group">
                <button type="button" class="btn big blue" v-if="isRead" @click="searchList(1)">검색</button>
                <button type="button" class="btn big gray" v-if="isRead" @click="initSearchData">초기화</button>
            </div>
            <div class="caption-group mt10 clearfix">
                <div class="total-group fl">
                    <span class="total">전체 <strong>{{ pagingData.listCount }}</strong>건</span>
                </div>
                <div class="btn-group fr">
                    <button type="button" v-if="isWrite" class="btn red-line" @click="cancelSubscibe">구독취소</button>
                    <button type="button" v-if="isRead" class="btn green-line" @click="fnExcelDownload"><i class="icon-excel"></i>엑셀다운로드</button>
                    <select v-model="pagingData.pageCount">
                        <option value="20">20개씩 보기</option>
                        <option value="50">50개씩 보기</option>
                        <option value="100">100개씩 보기</option>
                    </select>
                </div>
            </div>
            <table cellpadding="0" cellspacing="0" class="data-tb align-c">
                <caption>구독관리 목록</caption>
                <colgroup>
                    <col width="3%" /><!-- checkbox -->
                    <col width="15%" /><!-- 구독신청일시 -->
                    <col width="15%" /><!-- 구독취소일시 -->
                    <col width="" /><!-- 이메일 소유 ID -->
                    <col width="" /><!-- 이메일 -->
                    <col width="10%" /><!-- 상태 -->
                </colgroup>
                <thead>
                    <tr>
                        <th><input type="checkbox" id="chkall" v-model="isallchk" @change="checkAllList($event.target.checked)"/></th>
                        <th>구독신청일시
                            <button type="button" v-if="isRead" 
                                    :value="sortData.subregdate"
                                    class="sort"
                                    :class="[{up : sortData.subregdate === 'subregdate_asc'}, {down : sortData.subregdate === 'subregdate_desc'}]"
                                    @click="sortToggle(sortData.subregdate)">
                            </button>
                        </th>
                        <th>구독취소일시
                            <button type="button" v-if="isRead" 
                                    :value="sortData.cancaldate"
                                    class="sort"
                                    :class="[{up : sortData.cancaldate === 'cancaldate_asc'}, {down : sortData.cancaldate === 'cancaldate_desc'}]"
                                    @click="sortToggle(sortData.cancaldate)">
                            </button>
                        </th>
                        <th>이메일 소유 ID</th>
                        <th>이메일
                            <button type="button" v-if="isRead" 
                                    :value="sortData.email"
                                    class="sort"
                                    :class="[{up : sortData.email === 'email_asc'}, {down : sortData.email === 'email_desc'}]"
                                    @click="sortToggle(sortData.email)">
                            </button>
                        </th>
                        <th>상태
                            <button type="button" v-if="isRead" 
                                    :value="sortData.iscancel"
                                    class="sort"
                                    :class="[{up : sortData.iscancel === 'iscancel_asc'}, {down : sortData.iscancel === 'iscancel_desc'}]"
                                    @click="sortToggle(sortData.iscancel)">
                            </button>
                        </th>
                    </tr>
                </thead>
                <tbody v-if="this.list.length > 0">
                    <tr v-for="(row, index) in this.list" :key="index" :class="row.iscancel === 'T' ? 'bg gray' : ''">
                        <td><input type="checkbox" v-model="checkedList" :id="row.subidx" :value="row.subidx" :disabled="row.iscancel === 'T'" @change="checkList($event.target.checked)"/></td>
                        <td>{{ row.subregdate }}</td>
                        <td>{{ row.cancaldate }}</td>
                        <td>{{ row.subuserid }}</td>
                        <td>{{ row.email }}</td>
                        <td>{{ row.iscancel === 'T' ? '구독취소' : '구독 중'}}</td>
                    </tr>               
                </tbody>
                <tbody v-else>
                    <tr><td colspan="6">조회 결과가 존재하지 않습니다.</td></tr>
                </tbody>
            </table>
            <div class="bottom-group">
                <CommonPageNavigator v-show="isRead" :pagingData="pagingData" v-on:setPagingData="setPagingData" />
            </div>
        </div>
    </div>
</template>

<script>
import CommonNavigator from '@views.admin/common/CommonNavigator.vue';
import CommonDatePicker from '@views.admin/common/CommonDatePicker';
import CommonPageNavigator from '@views.admin/common/CommonPageNavigator';
export default {
    name: 'admin.operation.setting.subscribelist',
    components: {
        CommonNavigator,
        CommonDatePicker,
        CommonPageNavigator
    },
    data() {
        return {
            searchData: {
                sword: '',
                stype: 'subregdate',
                startDate: '',
                endDate: '',
                period: '3',
            },
            pagingData: {
                pageCount: 20,
                page: 1,
                listCount: 0,
            },
            sortData: {
                subregdate: 'subregdate_desc',
                cancaldate: 'cancaldate_desc',
                email: 'email_desc',
                iscancel: 'iscancel_desc',
            },
            isallchk: false,
            list: [],
            checkedList: [],
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
                this.searchList();
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
        },
        // 검색
        searchList(page) {
            let param = this.searchData;
            param.pageCount = this.pagingData.pageCount;
            param.page = (typeof page === 'undefined') ? this.pagingData.page : 1;
            param.listCount = this.pagingData.listCount;

            this.$http.post("/admin/operation/setting/subscribe/search", param)
            .then(result => {
                let data = result.data;
                this.list = data.list;
                this.pagingData.listCount = data.state.totalcnt;
                this.$util.dataSetSearchParam(this);
                this.isallchk = false;
                this.checkedList= [];
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
        cancelSubscibe() {
            if(this.checkedList.length === 0) {
                alert("선택된 항목이 없습니다.");
                return ;
            }

            let params = {
                idxlist: this.checkedList,
                iscancel: 'T',
            }

            if(confirm("구독취소 하시겠습니까?")) {
                this.$http.post("/admin/operation/setting/subscribe/update", params)
                .then(result => {
                    if(result.statusCode === 200) {
                        alert("구독취소가 완료되었습니다.");
                        this.searchList();
                    }
                })
                .catch(error => {
                    this.$util.debug(error);
                });
            }
        },
        // 목록 전체체크
        checkAllList: function(value) {
            this.checkedList = [];
            if (value) {
                this.list.forEach(item => {
                    if(item.iscancel === 'F') {
                        this.checkedList.push(item.subidx);
                    }
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
        // 엑셀다운로드
        fnExcelDownload: function() {
            if (this.list.length == 0) {
                alert('다운로드할 내역이 존재하지 않습니다.');
                return;
            }
            let config = { responseType: 'arraybuffer' };
            this.$http.post('/admin/operation/setting/subscribe/excel', this.searchData, config)
            .then(result => {
                this.$util.debug(result);
            })
            .catch(error => {
                this.$util.debug(error);
            });
        },
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