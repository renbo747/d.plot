<template>
    <!-- 컨텐츠 영역 -->
    <div class="content m-leftmenu">
        <common-navigator></common-navigator>
        <div class="inner">
            <div class="boxing search-area">                
                <dl>
                    <dt>기간조회</dt>
                    <dd>
                        <CommonDatePicker :value="searchData.startdate" @change="onChangeStartDate"/>
                        <span>-</span>
                        <CommonDatePicker :value="searchData.enddate" @change="onChangeEndDate"/>
                        <div class="radio_wrap">
                            <input type="radio" v-model="searchData.period" id="period_aday_1" value='aday_1'/><label for="period_aday_1">어제</label>
                            <input type="radio" v-model="searchData.period" id="period_aday_0" value='aday_0'/><label for="period_aday_0">오늘</label>
                            <input type="radio" v-model="searchData.period" id="period_days_7" value='days_7'/><label for="period_days_7">일주일</label>
                            <input type="radio" v-model="searchData.period" id="period_months_1" value='months_1'/><label for="period_months_1">1개월</label>
                            <input type="radio" v-model="searchData.period" id="period_months_3" value='months_3'/><label for="period_months_3">3개월</label>
                            <input type="radio" v-model="searchData.period" id="period_months_6" value='months_6'/><label for="period_months_6">6개월</label>
                        </div>
                    </dd>
                </dl>
                <dl>
                    <dt>직접검색</dt>
                    <dd>
                        <select v-model="searchData.skey">
                            <option value="">전체</option>
                            <option value="subject">제목</option>
                            <option value="regusername">작성자</option>
                        </select>
                        <input type="text" v-model="searchData.sword" maxlength="200" @keyup.enter="searchList(1)"/>
                    </dd>
                </dl>
            </div>
            <div class="btn-group" v-if="isRead">
                <button type="button" class="btn big blue" @click="searchList(1)">검색</button>
                <button type="button" class="btn big gray" @click="initSearchData">초기화</button>
            </div>
            <div class="caption-group mt10 clearfix">
                <div class="total-group fl">
                    <span class="total">전체 <strong>{{ count.totalcnt }}</strong>건</span>            
                </div>
                <div class="btn-group fr">
                    <select v-model="pagingData.pageCount" v-if="isRead">
                        <option value="20">20개씩 보기</option>
                        <option value="50">50개씩 보기</option>
                        <option value="100">100개씩 보기</option>
                    </select>
                </div>
            </div>        
            <table cellpadding="0" cellspacing="0" class="data-tb align-c">
                <caption>답변 템플릿 목록</caption>
                <colgroup>
                    <col width="32px" /><!-- checkbox -->
                    <col width="8%" /><!-- 노출순서 -->
                    <col width="4%" /><!-- No -->
                    <col width="" /><!-- 답변제목 -->
                    <col width="10%" /><!-- 작성자 -->
                    <col width="12%" /><!-- 등록일 -->
                </colgroup>
                <thead>
                    <tr>
                        <th></th>
                        <th>노출순서
                            <button type="button" class="sort" :value="sortData.sortnum" v-if="isRead"
                                :class="[{up : sortData.sortnum === 'sortnum_asc'}, {down : sortData.sortnum === 'sortnum_desc'}]"
                                @click="sortToggle(sortData.sortnum)"></button>
                        </th>
                        <th>No</th>
                        <th>답변제목
                            <button type="button" class="sort" :value="sortData.subject" v-if="isRead"
                                :class="[{up : sortData.subject === 'subject_asc'}, {down : sortData.subject === 'subject_desc'}]"
                                @click="sortToggle(sortData.subject)"></button>
                        </th>
                        <th>작성자</th>
                        <th>등록일
                            <button type="button" class="sort" :value="sortData.regdate" v-if="isRead"
                                :class="[{up : sortData.regdate === 'regdate_asc'}, {down : sortData.regdate === 'regdate_desc'}]"
                                @click="sortToggle(sortData.regdate)"></button>
                        </th>
                    </tr>
                </thead>
                <tbody v-if="list.length > 0">
                    <tr v-for="(item, index) in list" :key="item.tplidx">
                        <td><input type="checkbox" v-model="moveData.targetIdx" :id="item.tplidx" :true-value="index"/></td>
                        <td>{{ item.sortnum }}</td>
                        <td>{{ index+1 }}</td>
                        <td class="left">
                            <a href="javascript:void(0);" class="link" @click="goDetail(item.tplidx)">{{ item.subject }}</a>
                        </td>
                        <td>{{ item.regusername }}</td>
                        <td>{{ item.regdate }}</td>
                    </tr>
                </tbody>
                <tbody v-else>
                    <tr><td colspan="6">조회 결과가 존재하지 않습니다.</td></tr>
                </tbody>
            </table>         
            <div class="bottom-group">
                <CommonArraySort :list-data="list"
                                :move-data="moveData"
                                v-on:getModifySortNumArray="getModifySortNumArray"
                                :is-active-save-btn="true"
                                key-name="tplidx"
                                v-if="isWrite"
                />
                <div class="paging">
                    <CommonPageNavigator v-show="isRead" :pagingData="pagingData" v-on:setPagingData="setPagingData" />
                </div>
                <div class="btn-group">
                    <button type="button" class="btn blue" @click="goRegist">등록</button>
                </div>
            </div>
        </div>
        <RepTemplateRegist v-if="isShowRegist" @closePopup="closeRegist" />
        <RepTemplateDetail v-if="isShowDetail" :activeTplIdx="activeTplIdx" @closePopup="closeDetail" />
    </div>
    <!-- /컨텐츠 영역 -->
</template>

<script>
import CommonNavigator from '@views.admin/common/CommonNavigator.vue';
import CommonDatePicker from '@views.admin/common/CommonDatePicker.vue';
import CommonPageNavigator from "@views.admin/common/CommonPageNavigator";
import CommonArraySort from "@views.admin/common/CommonArraySort";
import RepTemplateRegist from '@/views/admin/cs/reptemplate/RepTemplateRegist';
import RepTemplateDetail from '@/views/admin/cs/reptemplate/RepTemplateDetail';

export default {
    name: 'admin.cs.reptemplatelist',
    components: {
        CommonNavigator,
        CommonPageNavigator,
        CommonDatePicker,
        CommonArraySort,
        RepTemplateRegist,
        RepTemplateDetail
    },
    mounted() {
        let params = { url: this.$options.name, isloading: false };
        this.$http.post('/admin/common/pageAuth/check', params)
            .then(result => {
                this.isRead = (result.data.isread === 'T');
                this.isWrite = (result.data.iswrite === 'T');
            
                if(this.isRead) {
                    // 초기화
                    this.onInit();
                }
            }).catch(error => {
                this.$util.debug(error);
            });
    },
    data() {
        return {
            searchData: {
                skey: '',               // 직접검색KEY
                sword: '',              // 직접검색어
                period: '',             // 조회일자기간
                startdate: '',          // 조회시작일자
                enddate: '',            // 조회종료일자
                psort: 'regdate_desc'   // 정렬조건 (default: 등록일자 최신순)
            },
            sortData: {
                regdate: 'regdate_desc',    // 등록일자
                sortnum: 'sortnum_asc',     // 노출순서
                subject: 'subject_asc',     // 제목
            },
            pagingData: {
                pageCount: 20,      // 페이징 옵션(최대수)
                page: 1,            // 현재 페이지
                listCount: 0        // 총 수량
            },
            moveData: {             // 노출순위 데이터
                moveValue: '',      // 움직일 값
                targetIdx: '',      // 대상 위치
                code: 'U',          // 위, 아래 코드
                isSuccess: false,   // 저장 성공 여부 (** 중요)
            },
            list: [],               // 조회 데이터
            count: {                // 데이터 건수
                totalcnt: 0,        // 전체 건수
            },
            isRead : false,
            isWrite : false,
            activeTplIdx: '',
            isShowRegist: false,    // 등록화면 노출여부
            isShowDetail: false     // 상세화면 노출여부
        };
    },
    methods: {
        // 화면 초기화
        onInit: function() {
            // 검색조건 초기화
            this.initSearchData();
            this.$util.componentSetSearchParam(this);
            // 목록 조회
            this.searchList();
        },
        // 검색조건 초기화
        initSearchData: function () {
            this.searchData.skey ='';
            this.searchData.sword = '';
            this.searchData.period = 'months_3';
            this.searchData.startdate = this.$util.getAddMonth(this.$util.getDate(''), -3, '-');
            this.searchData.enddate = this.$util.getDate('-');
        },
        // 목록 조회
        searchList: function(page) {
            // 목록조회
            let params = Object.assign(this.searchData, this.pagingData);
            params.page = (typeof page === 'undefined') ? this.pagingData.page : 1;
            params.isloading = true;
            this.$http.post('/admin/cs/reptemp/list', params)
                .then(result => {
                    this.$util.debug(result);
                    let data = result.data;
                    this.list = data.list;
                    this.count = data.count;
                    this.pagingData.listCount = data.count.totalcnt;
                    this.$util.dataSetSearchParam(this);
                    this.moveData.targetIdx = '';
                    this.moveData.isSuccess = false;            // ** 중요
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 순서 저장
        getModifySortNumArray(array) {
            if (array.length === 0) {
                alert("변경된 데이터가 없습니다.");
                return;
            }
            this.$http.post("/admin/cs/reptemp/sort/update", { changeList : array })
                .then(result => {
                    if (result.statusCode === 200) {
                        alert("저장이 완료되었습니다.");
                        this.searchList();
                    }
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 날짜 picker 콜백 함수
        onChangeStartDate(value) {
            this.searchData.startdate = value;
        },
        // 날짜 picker 콜백 함수
        onChangeEndDate(value) {
            this.searchData.enddate = value;
        },
        // 페이징 콜백
        setPagingData(param){
            this.pagingData = param;
            this.searchList();
        },
        // 정렬
        sortToggle(key){
            let arr = key.split('_');
            let sortKey = arr[0];
            let sortOrder = (arr[1] === 'asc') ? 'desc' : 'asc';
            let sortName = sortKey + '_' + sortOrder;

            this.sortData[sortKey] = sortName;
            this.searchData.psort = sortName;

            this.searchList();
        },
        // 답변템플릿 등록화면 오픈
        goRegist: function() {
            this.isShowRegist = true;
        },
        // 답변템플릿 등록화면 닫기
        closeRegist: function(isreroad) {
            this.isShowRegist = false;
            if (isreroad) {
                this.searchList();
            }
        },
        // 답변템플릿 상세화면 오픈
        goDetail: function(value) {
            this.isShowDetail = true;
            this.activeTplIdx = value;
        },
        // 답변템플릿 상세화면 닫기
        closeDetail: function(isreroad) {
            this.isShowDetail = false;
            if (isreroad) {
                this.searchList();
            }
        }
    },
    watch: {
        // 조회기간
        'searchData.period': function (value) {
            let params = value.split('_');
            let type = params[0];
            let addValue = parseInt(params[1]) * -1;

            if (type == 'aday') {
                this.searchData.startdate = this.$util.getAddDate(this.$util.getDate(), addValue, '-');
                this.searchData.enddate = this.$util.getAddDate(this.$util.getDate(), addValue, '-');
            } else if (type == 'days') {
                this.searchData.startdate = this.$util.getAddDate(this.$util.getDate(), addValue, '-');
                this.searchData.enddate = this.$util.getDate('-');
            } else if (type == 'months') {
                this.searchData.startdate = this.$util.getAddMonth(this.$util.getDate(), addValue, '-');
                this.searchData.enddate = this.$util.getDate('-');
            }
        }
    }
}
</script>
