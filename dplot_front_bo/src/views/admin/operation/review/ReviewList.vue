<template>
    <div class="content m-leftmenu">
        <common-navigator></common-navigator>
        <ReviewDetail v-if="showDetail" v-bind:reviewidx="idx" v-on:closeDetail="closeDetail"></ReviewDetail>
        <div class="inner">
            <div class="boxing search-area">
                <dl>
                    <dt>직접검색</dt>
                    <dd>
                        <select v-model="searchData.skey">
                            <option value="">전체</option>
                            <option value="goodsname">상품명</option>
                            <option value="content">리뷰내용</option>
                            <option v-if="searchData.isAdmin" value="reguserid">아이디</option>
                            <option v-if="searchData.isAdmin" value="regusername">이름</option>
                        </select>
                        <input type="text" v-model="searchData.sword" @keyup.enter="searchList(1)"/>
                    </dd>
                </dl>
                <dl>
                    <dt>등록일자</dt>
                    <dd>
                        <select v-if="searchData.isAdmin" v-model="searchData.stype">
                            <option value="">전체</option>
                            <option value="beststtime">시작일자</option>
                            <option value="bestedtime">종료일자</option>
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
                <dl v-if="searchData.isAdmin">
                    <dt>구분</dt>
                    <dd>
                        <div class="check-wrap">
                            <input type="checkbox" v-model="searchData.isallgubun" id="isallgubun" true-value="T" false-value="F" @change="checkAllGubuntype">
                            <label for="isallgubun">전체</label>
                        </div>
                        <div class="check-wrap" v-for="item in commonCode.gubuntype" :key="item.cmcode">
                            <input type="checkbox" v-model="searchData.gubuntypearr" :id="'gubuntype_'+item.cmcode" true-value="[]" :value="item.cmcode">
                            <label :for="'gubuntype_'+item.cmcode">{{item.codename}}</label>
                        </div>
                    </dd>
                </dl>
                <dl v-if="searchData.isAdmin">
                    <dt>베스트여부</dt>
                    <dd>
                        <div class="radio_wrap wide3">
                            <input type="radio" name="group01" id="group11" v-model="searchData.isbest" value=""  checked><label for="group11">전체</label>
                            <input type="radio" name="group01" id="group12" v-model="searchData.isbest" value="F" ><label for="group12">일반</label>
                            <input type="radio" name="group01" id="group13" v-model="searchData.isbest" value="T" ><label for="group13">베스트</label>
                        </div>
                    </dd>
                </dl>
                <dl v-if="searchData.isAdmin">
                    <dt>미노출상태</dt>
                    <dd>
                        <div class="radio_wrap wide3">
                            <input type="radio" name="group03" id="group31" v-model="searchData.isdisplay" value=""  checked><label for="group31">전체</label>
                            <input type="radio" name="group03" id="group32" v-model="searchData.isdisplay" value="T" ><label for="group32">일반</label>
                            <input type="radio" name="group03" id="group33" v-model="searchData.isdisplay" value="F" ><label for="group33">미노출</label>
                        </div>
                    </dd>
                </dl>
                <dl v-if="searchData.isAdmin">
                    <dt>신고여부</dt>
                    <dd>
                        <div class="radio_wrap wide3">
                            <input type="radio" name="group04" id="group41" v-model="searchData.isnoti" value=""  checked><label for="group41">전체</label>
                            <input type="radio" name="group04" id="group42" v-model="searchData.isnoti" value="F" ><label for="group42">N(0개)</label>
                            <input type="radio" name="group04" id="group43" v-model="searchData.isnoti" value="T" ><label for="group43">Y(1개 이상)</label>
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
                    <span class="total">전체 <strong>{{pagingData.listCount}}</strong>건</span>
                </div>
                <div class="btn-group fr">
                    <!-- <button type="button" v-if="isWrite" class="btn blue-line" @click="bestChange('T')">베스트 지정</button>
                    <button type="button" v-if="isWrite" class="btn red-line" @click="bestChange('F')">베스트 해지</button> -->
                    <button type="button" v-if="isRead"  class="btn green-line" @click="fnExcelDownload"><i class="icon-excel"></i>엑셀다운로드</button>
                    <select v-model="pagingData.pageCount">
                        <option value="20">20개씩 보기</option>
                        <option value="50">50개씩 보기</option>
                        <option value="100">100개씩 보기</option>
                    </select>
                </div>
            </div>
            <table cellpadding="0" cellspacing="0" class="data-tb align-c">
                <caption>리뷰 목록</caption>
                <colgroup>
                    <!-- <col width="3%" />checkbox -->
                    <col width="3%" /><!-- No -->
                    <col v-if="searchData.isAdmin" width="4%" /><!-- 베스트 -->
                    <col v-if="searchData.isAdmin" width="4%" /><!-- 블라인드 -->
                    <col width="15%" /><!-- 상품명 -->
                    <col width="%" /><!-- 리뷰내용 -->
                    <col v-if="searchData.isAdmin" width="6%" /><!-- 아이디 -->
                    <col v-if="searchData.isAdmin" width="6%" /><!-- 이름 -->
                    <col width="6%" /><!-- 총상품평점 -->
                    <col width="5%" /><!-- 도움 -->
                    <col width="9%" /><!-- 사진 -->
                    <col v-if="searchData.isAdmin" width="4%" /><!-- 신고 -->
                    <col v-if="searchData.isAdmin" width="8%" /><!-- 시작일자 -->
                    <col v-if="searchData.isAdmin" width="8%" /><!-- 종료일자 -->
                    <col width="8%" /><!-- 등록일자 -->
                </colgroup>
                <thead>
                    <tr>
                        <!-- <th><input type="checkbox" id="chkall" v-model="isallchk" @change="checkAllList($event.target.checked)"/></th> -->
                        <th>No</th>
                        <th v-if="searchData.isAdmin">베스트</th>
                        <th v-if="searchData.isAdmin">미노출</th>
                        <th>상품명</th>
                        <th>리뷰내용</th>
                        <th v-if="searchData.isAdmin">아이디</th>
                        <th v-if="searchData.isAdmin">이름</th>
                        <th>총상품평점
                            <button type="button" v-if="isRead" 
                                    :value="sortData.totpoint"
                                    class="sort"
                                    :class="[{up : sortData.totpoint === 'totpoint_asc'}, {down : sortData.totpoint === 'totpoint_desc'}]"
                                    @click="sortToggle(sortData.totpoint)">
                            </button>
                        </th>
                        <th>좋아요
                            <button type="button" v-if="isRead" 
                                    :value="sortData.goodcnt"
                                    class="sort"
                                    :class="[{up : sortData.goodcnt === 'goodcnt_asc'}, {down : sortData.goodcnt === 'goodcnt_desc'}]"
                                    @click="sortToggle(sortData.goodcnt)">
                            </button>
                        </th>
                        <th>포토&동영상
                            <button type="button" v-if="isRead" 
                                    :value="sortData.filecnt"
                                    class="sort"
                                    :class="[{up : sortData.filecnt === 'filecnt_asc'}, {down : sortData.filecnt === 'filecnt_desc'}]"
                                    @click="sortToggle(sortData.filecnt)">
                            </button>
                        </th>
                        <th v-if="searchData.isAdmin">신고</th>
                        <th v-if="searchData.isAdmin">시작일자
                            <button type="button" v-if="isRead" 
                                    :value="sortData.beststtime"
                                    class="sort"
                                    :class="[{up : sortData.beststtime === 'beststtime_asc'}, {down : sortData.beststtime === 'beststtime_desc'}]"
                                    @click="sortToggle(sortData.beststtime)">
                            </button>
                        </th>
                        <th v-if="searchData.isAdmin">종료일자
                            <button type="button" v-if="isRead" 
                                    :value="sortData.bestedtime"
                                    class="sort"
                                    :class="[{up : sortData.bestedtime === 'bestedtime_asc'}, {down : sortData.bestedtime === 'bestedtime_desc'}]"
                                    @click="sortToggle(sortData.bestedtime)">
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
                        <!-- <td><input type="checkbox" v-model="checkedList" :id="row.reviewidx" :value="row.reviewidx" @change="checkList($event.target.checked)"/></td> -->
                        <td>{{row.no}}</td>
                        <td v-if="searchData.isAdmin">{{row.isbest === 'T' ? '●' : '-'}}</td>
                        <td v-if="searchData.isAdmin">{{row.isdisplay === 'T' ? '-' : '●'}}</td>
                        <td class="left">{{row.goodsname}}</td>
                        <td class="left"><a class="link" @click="goDetail(row.reviewidx)">{{row.content}}</a></td>
                        <td v-if="searchData.isAdmin">{{row.reguserid}}</td>
                        <td v-if="searchData.isAdmin">{{row.regusername}}</td>
                        <td>{{row.totpoint}}</td>
                        <td>{{row.goodcnt}}</td>
                        <td>{{row.filecnt}}</td>
                        <td v-if="searchData.isAdmin">{{row.noticnt}}</td>
                        <td v-if="searchData.isAdmin">{{$util.isNull(row.beststtime) ? '-': row.beststtime}}</td>
                        <td v-if="searchData.isAdmin">{{$util.isNull(row.bestedtime) ? '-': row.bestedtime}}</td>
                        <td>{{row.regdate}}</td>
                    </tr>
                </tbody>
                <tbody v-else>
                    <tr><td :colspan="searchData.isAdmin ? 14 : 7">조회 결과가 존재하지 않습니다.</td></tr>
                </tbody>
            </table>
            <div class="bottom-group">
                <div class="paging">
                    <CommonPageNavigator v-show="isRead" :pagingData="pagingData" v-on:setPagingData="setPagingData" />
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import CommonNavigator from '@views.admin/common/CommonNavigator.vue';
import CommonDatePicker from '@views.admin/common/CommonDatePicker';
import CommonPageNavigator from '@views.admin/common/CommonPageNavigator';
import ReviewDetail from './ReviewDetail';
export default {
    name: 'admin.operation.reviewlist',
    components: {
        CommonNavigator,
        CommonDatePicker,
        CommonPageNavigator,
        ReviewDetail
    },
    data() {
        return {
            searchData: {
                isAdmin: false,
                skey: '',
                sword: '',
                stype: '',
                startDate: '',
                endDate: '',
                period: '3',
                isbest: '',
                isnoti: '',
                isdisplay: '',
                isallgubun: 'T',
                gubuntypearr: [],
                psort: '',
            },
            commonCode: {
                gubuntype: [{cmcode: 'GUB001', codename:'텍스트'}, {cmcode: 'GUB002', codename:'포토&동영상'}]
            },
            pagingData: {
                pageCount: 20,      // 페이징 옵션(최대수)
                page: 1,            // 현재 페이지
                listCount: 0        // 총 수량
            },
            sortData: {
                totpoint: 'totpoint_desc',              // 총상품평점 정렬기준
                goodcnt: 'goodcnt_desc',                // 좋아요 정렬기준
                filecnt: 'filecnt_desc',                // 포토&동영상 정렬기준
                beststtime: 'beststtime_desc',          // 시작일자 정렬기준
                bestedtime: 'bestedtime_desc',          // 종료일자 정렬기준
                regdate: 'regdate_desc',                // 등록일자 정렬기준
            },
            isallchk: false,        // 목록 전체체크여부
            list: [],               // 조회 데이터
            // checkedList: [],        // 선택된 목록
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
                this.user = this.$util.getUser(this.$store.getters['CONSTANTS'].MANAGER_SESSION);
                this.searchData.isAdmin = this.$util.isAuthorized('ADMIN_USER');
                this.searchData.dealerno = this.searchData.isAdmin ? '' : this.user.no;
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
            this.searchData.skey = '';
            this.searchData.sword = '';
            this.searchData.stype = '';
            this.searchData.period = '3';
            this.searchData.isbest = '';
            this.searchData.isnoti = '';
            this.searchData.isdisplay = '';
            this.searchData.isallgubun = 'T';
            this.searchData.gubuntypearr = [];
            this.searchData.psort = '';
            this.searchData.startDate = this.$util.getAddMonth(this.$util.getDate(''), -3, '-');
            this.searchData.endDate = this.$util.getDate('-');

            this.checkAllGubuntype();
        },
        // 검색
        searchList(page) {
            this.isChecked = false;

            let param = this.searchData;
            param.pageCount = this.pagingData.pageCount;
            param.page = (typeof page === 'undefined') ? this.pagingData.page : 1;
            param.listCount = this.pagingData.listCount;

            this.$http.post("/admin/operation/review/search", param)
            .then(result => {
                let data = result.data;
                this.list = data.list;
                this.pagingData.listCount = data.count.totalcnt;
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
        // 조회조건 - 적용채널 전체체크
        checkAllGubuntype: function() {
            let isAllCheck = this.searchData.isallgubun;
            this.searchData.gubuntypearr = [];
            if (isAllCheck === 'T') {
                for(let type of this.commonCode.gubuntype){
                    this.searchData.gubuntypearr.push(type.cmcode);
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
        // bestChange(best) {
        //     if(this.checkedList.length === 0) {
        //         alert("체크된 리스트가 없습니다.");
        //         return;
        //     }

        //     let params = {
        //         idxlist : this.checkedList,
        //         isbest : best,
        //     }

        //     if(confirm("상태를 전환 하시겠습니까?")) {
        //         this.$http.post("/admin/operation/review/update", params)
        //         .then(result => {
        //             if (result.statusCode == 200) {
        //                 alert("저장이 완료되었습니다.");
        //                 this.searchList();
        //             } else {
        //                 alert("저장에 실패했습니다.");
        //             }
        //         })
        //         .catch(error => {
        //             this.$util.debug(error);
        //         })
        //     }
        // },
        // 엑셀다운로드
        fnExcelDownload: function() {
            if (this.list.length == 0) {
                alert('다운로드할 내역이 존재하지 않습니다.');
                return;
            }
            let config = { responseType: 'arraybuffer' };
            this.$http.post('/admin/operation/review/excel', this.searchData, config)
                .then(result => {
                    this.$util.debug(result);
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // // 목록 전체체크
        // checkAllList: function(value) {
        //     this.checkedList = [];
        //     if (value) {
        //         this.list.forEach(item => {
        //             this.checkedList.push(item.reviewidx);
        //         });
        //     }
        // },
        // // 목록 개별체크
        // checkList: function() {
        //     if (this.list.length > this.checkedList.length){
        //         this.isallchk = false;
        //     } else {
        //         this.isallchk = true;
        //     }
        // },
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
        'searchData.gubuntypearr': function(value){
            if (value.length < this.commonCode.gubuntype.length) {
                this.searchData.isallgubun = 'F';
            } else {
                this.searchData.isallgubun = 'T';
            }
        },
    }
}
</script>

<style>

</style>