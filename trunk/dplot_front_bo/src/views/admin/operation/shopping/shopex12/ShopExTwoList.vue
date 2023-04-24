<template>
    <div class="content m-leftmenu">
        <common-navigator></common-navigator>
        <GoodsDetail v-if="isGoodsDetailShow" :activeGoodsNo="activeGoodsNo" @closePopup="closeGoodsDetail"></GoodsDetail>
        <div class="inner">
            <div class="boxing search-area">
                <dl>
                    <dt>직접검색</dt>
                    <dd>
                        <select v-model="searchData.skey">
                            <option value="">전체</option>
                            <option value="brandname">브랜드</option>
                            <option value="goodscode">상품코드</option>
                            <option value="goodsname">상품명</option>
                            <option value="mdname">담당MD</option>
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
                    <dt>카테고리</dt>
                    <dd>
                        <select style="width: 200px;" v-model="searchData.depth1cateidx" @change="getCategoryCodeList(2, $event.target.value)">
                            <option value="">대분류</option>
                            <option v-for="item in categoryObj.depth1list" :key="item.idx" :value="item.idx">{{ item.value }}</option>
                        </select>
                        <select style="width: 200px;" v-model="searchData.depth2cateidx" @change="getCategoryCodeList(3, $event.target.value)">
                            <option value="">중분류</option>
                            <option v-for="item in categoryObj.depth2list" :key="item.idx" :value="item.idx">{{ item.value }}</option>
                        </select>
                        <select style="width: 200px;" v-model="searchData.depth3cateidx" @change="getCategoryCodeList(4, $event.target.value)">
                            <option value="">소분류</option>
                            <option v-for="item in categoryObj.depth3list" :key="item.idx" :value="item.idx">{{ item.value }}</option>
                        </select>
                        <select style="width: 200px;" v-model="searchData.depth4cateidx">
                            <option value="">세분류</option>
                            <option v-for="item in categoryObj.depth4list" :key="item.idx" :value="item.idx">{{ item.value }}</option>
                        </select>
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
                    <button type="button" v-if="isWrite" class="btn blue-line" @click="change('T')">노출</button>
                    <button type="button" v-if="isWrite" class="btn red-line" @click="change('F')">미노출</button>
                    <button type="button" v-if="isWrite" class="btn black-line" @click="deleteList">목록삭제</button>
                    <button type="button" v-if="isRead" class="btn green-line" @click="fnExcelDownload"><i class="icon-excel"></i>엑셀다운로드</button>
                </div>
            </div>
            <table cellpadding="0" cellspacing="0" class="data-tb align-c">
                <caption>전시영역1관리 목록</caption>
                <colgroup>
                    <col width="3%" /><!-- checkbox -->
                    <col width="5%" /><!-- 노출순서 -->
                    <col width="5%" /><!-- 브랜드 -->
                    <col width="8%" /><!-- 상품코드 -->
                    <col width="62px" /><!-- 상품명(이미지) -->
                    <col width="" /><!-- 상품명 -->
                    <col width="6%" /><!-- 담당MD -->
                    <col width="6%" /><!-- 노출상태 -->
                    <col width="7%" /><!-- 등록(지정)자 -->
                    <col width="9%" /><!-- 등록(지정)일자 -->
                </colgroup>
                <thead>
                    <tr>
                        <th><input type="checkbox" id="chkall" v-model="isallchk" @change="checkAllList($event.target.checked)"/></th>
                        <th>노출순서</th>
                        <th>브랜드</th>
                        <th>상품코드
                            <button type="button" v-if="isRead" 
                                    :value="sortData.goodscode"
                                    class="sort"
                                    :class="[{up : sortData.goodscode === 'goodscode_asc'}, {down : sortData.goodscode === 'goodscode_desc'}]"
                                    @click="sortToggle(sortData.goodscode)">
                            </button>
                        </th>
                        <th colspan="2">상품명</th>
                        <th>담당MD</th>
                        <th>노출상태</th>
                        <th>등록(지정)자</th>
                        <th>등록(지정)일자
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
                        <td><input type="checkbox" v-model="moveData.targetIdx" :id="row.ex12idx" :value="index" @change="checkList($event.target.checked)"/></td>
                        <td>{{ $util.isNull(row.sortnum) ? '-' : row.sortno }}</td>
                        <td>{{ row.brandname }}</td>
                        <td>{{ row.goodscode }}</td>
                        <td>
                            <div class="img-thumb size60 link" :class="{'no-image': $util.isNull(row.fullpath)}">
                                <img :src="row.fullpath" v-if="!$util.isNull(row.fullpath)">
                            </div>
                        </td>
                        <td class="left no-left">
                            <span class="small-txt">{{ row.fullcategoryname }}</span>
                            <a href="javascript:void(0)"  @click="goGoodsDetail(row.goodsno)" class="link dpb">{{ row.goodsname }}</a>
                        </td>
                        <td>{{ row.mdname }}</td>
                        <td>{{ row.isdisplay === 'T' ? '노출' : '미노출' }}</td>
                        <td>{{ row.regusername }}</td>
                        <td>{{ row.regdate }}</td>
                    </tr>   
                </tbody>
                <tbody v-else>
                    <tr><td colspan="10">조회 결과가 존재하지 않습니다.</td></tr>
                </tbody>
            </table>
            <div class="bottom-group">
                <CommonArraySort :list-data="list"
                           :move-data="moveData"
                           v-on:getModifySortNumArray="getModifySortNumArray"
                           :is-active-save-btn="true"
                           v-if="isWrite"
                />
                <div class="btn-group">
                    <button type="button" class="btn blue" v-if="isWrite" @click="openGoodsAdditionPopup">상품 등록</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import CommonNavigator from '@views.admin/common/CommonNavigator.vue';
import CommonDatePicker from '@views.admin/common/CommonDatePicker';
import CommonArraySort from "@views.admin/common/CommonArraySort";
import CommonAddGoodsPopup from '@views.admin/common/popup/CommonAddGoodsPopup.vue';
import GoodsDetail from '@views.admin/goods/manage/GoodsDetail.vue';
export default {
    name: 'admin.operation.shopping.shopextwolist',
    components: {
        CommonNavigator,
        CommonDatePicker,
        CommonArraySort,
        GoodsDetail
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
                depth1cateidx: '',      //대분류일련번호
                depth2cateidx: '',      //중분류일련번호
                depth3cateidx: '',      //소분류일련번호
                depth4cateidx: '',      //세분류일련번호
                psort: '',
            },
            state: {
                totalcnt: 0,
                showcnt: 0,
                hidecnt: 0,
            },
            categoryObj: {
                depth1list: [],
                depth2list: [],
                depth3list: [],
                depth4list: [],
            },
            sortData: {
                goodscode: 'goodscode_desc',              // 상품코드 정렬기준
                regdate: 'regdate_desc',                // 등록일자 정렬기준
            },
            moveData: {                       // 노출순위 데이터
                moveValue: '',                  // 움직일 값
                targetIdx: [],                  // 대상 위치
                code: 'U',                      // 위, 아래 코드
                isSuccess: false,               // 저장 성공 여부 (** 중요)
            },
            isallchk: false,        // 목록 전체체크여부
            list: [],               // 조회 데이터
            activeGoodsNo: null,
            isGoodsDetailShow: false,
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

            this.getCategoryCodeList(1, 0);

            this.searchList();
        },
        // 카테고리분류 목록 조회
        getCategoryCodeList: function(targetDepth, parent) {
            let params = { idx: parent, isloading: false };
            // 선택한 하위 카테고리 목록 초기화
            for (let i=targetDepth; i<=4; i++) {
                this.categoryObj['depth'+i+'list'] = [];
                this.searchData['depth'+i+'cateidx'] = '';
            }
            // parent 값이 있는경우만 재조회
            if(!this.$util.isNull(parent)) {
                this.$http.post('/admin/goods/regist/cate/list', params)
                .then(result => {
                    this.$util.debug(result);
                    this.categoryObj['depth'+targetDepth+'list'] = result.data.list;
                })
                .catch(error => {
                    this.$util.debug(error);
                });
            }
        },
        // 검색
        searchList() {
            let param = this.searchData;

            this.$http.post("/admin/operation/shopping/shopextwo/search", param)
            .then(result => {
                let data = result.data;
                this.list = data.list;
                this.state = data.state;
                this.$util.dataSetSearchParam(this);
                this.isallchk = false;
                this.moveData.targetIdx = [];
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
        change(show) {
            if(this.moveData.targetIdx.length === 0) {
                alert("체크된 리스트가 없습니다.");
                return;
            }

            let thisObj = this;
            let list = [];
            this.moveData.targetIdx.forEach(function(item){
                list.push(thisObj.list[item].ex12idx);
            });

            let params = {
                idxlist : list,
                isloading: false,
                overmsg : '상태를 변경 하시겠습니까?',
                zeromsg : '수정',
                isdisplay: show,
            }

            this.$http.post("/admin/operation/shopping/shopextwo/check", params)
            .then(result => {
                if (result.statusCode == 200) {
                    params.msg = result.data.msg;
                    this.update(params);
                }
            })
            .catch(error => {
                this.$util.debug(error);
            })
        },
        update(params) {
            if(confirm(params.msg)) {
                this.$http.post("/admin/operation/shopping/shopextwo/update", params)
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
            if (this.moveData.targetIdx.length == 0) {
                alert("체크된 리스트가 없습니다.");
                return;
            }

            let thisObj = this;
            let list = [];
            this.moveData.targetIdx.forEach(function(item){
                list.push(thisObj.list[item].ex12idx);
            });

            let params = {
                idxlist : list,
                istrash : 'T',
                overmsg : '삭제 하시겠습니까?',
                zeromsg : '삭제',
                isloading: false
            }

            this.$http.post("/admin/operation/shopping/shopextwo/check", params)
            .then(result => {
                if (result.statusCode == 200) {
                    let msg = result.data.msg;

                    if(confirm(msg)) {
                        this.$http.post("/admin/operation/shopping/shopextwo/update", params)
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
        // 목록 전체체크
        checkAllList: function(value) {
            this.moveData.targetIdx = [];
            if (value) {
                for (let i in this.list) {
                    this.moveData.targetIdx[i] = i;
                }
            }
        },
        // 목록 개별체크
        checkList: function() {
            if (this.list.length > this.moveData.targetIdx.length){
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
            this.$http.post('/admin/operation/shopping/shopextwo/excel', this.searchData, config)
            .then(result => {
                this.$util.debug(result);
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

            let params = {
                numsave: '',
                list: array
            }

            this.$http.post("/admin/operation/shopping/shopextwo/update", params)
            .then(result => {
                if (result.statusCode === 200) {
                    alert("저장이 완료되었습니다.");
                    this.searchList();
                    this.moveData.targetIdx = [];
                    this.moveData.isSuccess = false;            // ** 중요
                }
            })
            .catch(error => {
                this.$util.debug(error);
            });
        },
        // 추가상품 팝업 오픈
        openGoodsAdditionPopup(index) {
            let param = {
                isdisplay: 'T',
                goodsselltypeArr:['GST002'],
            }

            this.$eventBus.$emit('modalShow', CommonAddGoodsPopup, param,
                (result) => {
                    let goodslist = result.list;

                    let param = {
                        list: goodslist,
                    }

                    this.$http.post("/admin/operation/shopping/shopextwo/save", param)
                    .then(result => {
                        if (result.statusCode === 200) {
                            alert("저장이 완료되었습니다.");
                            this.searchList();
                        }
                    })
                    .catch(error => {
                        this.$util.debug(error);
                    });
                }
            );
        },
        // 상품 상세정보
        goGoodsDetail: function(value) {
            this.activeGoodsNo = value;
            this.isGoodsDetailShow = true;
        },
        closeGoodsDetail: function () {
            this.isGoodsDetailShow = false;
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