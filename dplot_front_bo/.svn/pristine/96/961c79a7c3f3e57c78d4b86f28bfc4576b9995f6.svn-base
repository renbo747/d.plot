<template>
    <!-- 회원추가 팝업 -->
    <div id="modal-wrap" class="modal" style="display: block;">
        <div class="modal-content" style="width: 1000px;">
            <div class="pop-header">
                <h2>사은품 추가</h2>
                <button type="button" class="pop-close" @click="$modal.hide('commonModal');"></button>
            </div>
            <div class="pop-body">
                <div class="boxing search-area">
                    <dl>
                        <dt>직접검색</dt>
                        <dd>
                            <select v-model="searchData.skey">
                                <option value="">전체</option>
                                <option value="giftcode">사은품코드</option>
                                <option value="giftname">사은품명</option>
                                <option value="erpgiftcode">ERP사은품코드</option>
                                <option value="erpgiftname">ERP사은품명</option>
                            </select>
                            <input type="text" v-model="searchData.sword" maxlength="200" @keyup.enter="searchList"/>
                        </dd>
                    </dl>
                    <dl>
                        <dt>등록일자</dt>
                        <dd>
                            <common-date-picker :value="searchData.startDate" @change="onChangeStartDate"></common-date-picker>
                            <span>-</span>
                            <common-date-picker :value="searchData.endDate" @change="onChangeEndDate"></common-date-picker>
                            <div class="radio_wrap">
                                <input type="radio" v-model="searchData.period" id="periodD_aday_1" value='aday_1'/><label for="periodD_aday_1">어제</label>
                                <input type="radio" v-model="searchData.period" id="periodD_aday_0" value='aday_0'/><label for="periodD_aday_0">오늘</label>
                                <input type="radio" v-model="searchData.period" id="periodD_days_7" value='days_7'/><label for="periodD_days_7">일주일</label>
                                <input type="radio" v-model="searchData.period" id="periodD_months_1" value='months_1'/><label for="periodD_months_1">1개월</label>
                                <input type="radio" v-model="searchData.period" id="periodD_months_3" value='months_3'/><label for="periodD_months_3">3개월</label>
                                <input type="radio" v-model="searchData.period" id="periodD_months_6" value='months_6'/><label for="periodD_months_6">6개월</label>
                            </div>
                        </dd>
                    </dl>
                    <dl>
                        <dt>사용여부</dt>
                        <dd>
                            <div class="radio_wrap wide3">
                                <input type="radio" name="istrashPop" id="istrashPopAll" v-model="searchData.istrash" value=""/>
                                <label for="istrashPopAll">전체</label>
                                <input type="radio" name="istrashPop" id="istrashPopT" v-model="searchData.istrash" value="F"/>
                                <label for="istrashPopT">사용</label>
                                <input type="radio" name="istrashPop" id="istrashPopF" v-model="searchData.istrash" value="T"/>
                                <label for="istrashPopF">미사용</label>
                            </div>
                        </dd>
                    </dl>
                </div>
                <div class="btn-group">
                    <button type="button" class="btn big blue" @click="searchList">검색</button>
                    <button type="button" class="btn big gray" @click="initSearchData">초기화</button>
                </div>
                <div class="caption-group mt10 clearfix">
                    <div class="total-group fl">
                        <span class="total">전체 <strong>{{ count.total_cnt }}</strong>건</span>
                        <span>사용 <strong>{{ count.use_cnt }}</strong>건</span>
                        <span>미사용 <strong>{{ count.trash_cnt }}</strong>건</span>
                    </div>
                </div>
                <div class="scroll-y" style="max-height: 350px;">
                    <table cellpadding="0" cellspacing="0" class="data-tb align-c">
                        <colgroup>
                            <col width="4%" /><!-- checkbox -->
                            <col width="15%" /><!-- 사은품코드 -->
                            <col width="" /><!-- 사은품명 -->
                            <col width="15%" /><!-- ERP사은품코드 -->
                            <col width="22%" /><!-- ERP사은품명 -->
                            <col width="10%" /><!-- 사용여부 -->
                            <col width="12%" /><!-- 등록일자 -->
                        </colgroup>
                        <thead>
                            <tr>
                                <th><input v-if="!searchData.issingle" type="checkbox" id="chkallD" v-model="isallchk" @change="checkAllList($event.target.checked)"/></th>
                                <th>사은품코드
                                    <button type="button" class="sort" :value="sortData.giftcode"
                                        :class="[{up : sortData.giftcode === 'giftcode_asc'}, {down : sortData.giftcode === 'giftcode_desc'}]"
                                        @click="sortToggle(sortData.giftcode)"></button>
                                </th>
                                <th>사은품명</th>
                                <th>ERP사은품코드
                                    <button type="button" class="sort" :value="sortData.erpgiftcode"
                                        :class="[{up : sortData.erpgiftcode === 'erpgiftcode_asc'}, {down : sortData.erpgiftcode === 'erpgiftcode_desc'}]"
                                        @click="sortToggle(sortData.erpgiftcode)"></button>
                                </th>
                                <th>ERP사은품명</th>
                                <th>사용여부</th>
                                <th>등록일자
                                    <button type="button" class="sort" :value="sortData.regdate"
                                        :class="[{up : sortData.regdate === 'regdate_asc'}, {down : sortData.regdate === 'regdate_desc'}]"
                                        @click="sortToggle(sortData.regdate)"></button>
                                </th>
                            </tr>
                        </thead>
                        <tbody v-if="list.length > 0">
                            <tr v-for="item in list" :key="item.giftidx">
                                <td>
                                    <input type="checkbox" v-if="!searchData.issingle" :id="item.giftidx" v-model="checkedList" :value="item.giftidx" @change="checkList"/>
                                    <input type="radio" v-if="searchData.issingle" class="circle" name="checked" @change="setCheckedList(item.giftidx)"/>
                                </td>
                                <td>{{ item.giftcode }}</td>
                                <td>{{ item.giftname }}</td>
                                <td>{{ item.erpgiftcode }}</td>
                                <td>{{ item.erpgiftname }}</td>
                                <td>{{ item.istrash==='T'? '미사용':'사용' }}</td>
                                <td>{{ item.regdate }}</td>
                            </tr>
                        </tbody>
                        <tbody v-else>
                            <tr><td colspan="9">조회 결과가 존재하지 않습니다.</td></tr>
                        </tbody>
                    </table>
                </div>
                <div class="btn-group">
                    <button type="button" class="btn big blue" @click="sendAllList" v-if="!searchData.issingle">전체 적용</button>
                    <button type="button" class="btn big blue" @click="sendSelectedList">선택 대상 적용</button>
                    <button type="button" class="btn big darkgray" @click="$modal.hide('commonModal');">닫기</button>
                </div>
            </div>
        </div>
    </div>
    <!-- /회원추가 팝업 -->
</template>

<script>
import CommonDatePicker from '@views.admin/common/CommonDatePicker.vue';

export default {
    name: 'CommonAddGiftPopup',
    components: {
        CommonDatePicker
    },
    props : {
        modalComp: Object,
        params: Object,
        callbackFn: Function
    },
    mounted() {
        // 초기화
        this.onInit();
    },
    data :function(){
        return {
            searchData: {
                skey: '',
                sword: '',
                startDate: '',
                endDate: '',
                period: '',
                istrash: '',        //사용여부
                issingle: false     //단일상품여부
            },
            sortData: {
                psort: 'regdate_desc',
                giftcode : 'giftcode_asc',
                erpgiftcode : 'erpgiftcode_asc',
                regdate : 'regdate_desc'
            },
            isallchk: false,
            checkedList: [],
            list: [],
            count: {
                total_cnt: 0,
                use_cnt: 0,
                trash_cnt: 0
            },
        }
    },
    methods : {
        onInit: function() {
            // 팝업호출시 넘어온 조회조건 세팅
            let paramsKey = ['issingle'];
            if (!this.$util.isNull(this.params)) {
                for (const [key, value] of Object.entries(this.params)) {
                    if (paramsKey.indexOf(key) > -1) {
                        this.searchData[key] = value;
                    }
                }
            }
            // 검색조건 초기화
            this.initSearchData();
            // 목록 조회
            this.searchList();
        },
        // 검색조건 초기화
        initSearchData: function () {
            this.searchData.skey = '';
            this.searchData.sword = '';
            this.searchData.startDate = this.$util.getAddMonth(this.$util.getDate(), -3, '-');
            this.searchData.endDate = this.$util.getDate('-');
            this.searchData.period = 'months_3'
            this.searchData.istrash = 'F';
        },
        // 사용자 조회
        searchList: function() {
            let params = Object.assign(this.searchData, this.pagingData);
            this.$http.post('/admin/common/gift/list', params)
                .then(result => {
                    this.$util.debug(result);
                    let data = result.data;
                    this.list = data.list;
                    this.count = data.count;
                    this.isallchk = false;
                    this.checkedList = [];
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 시작날짜 picker 콜백 함수
        onChangeStartDate(value) {
            this.searchData.startDate = value;
        },
        // 종료날짜 picker 콜백 함수
        onChangeEndDate(value) {
            this.searchData.endDate = value;
        },
        // 테이블 정렬
        sortToggle: function(key){
            let arr = key.split("_");
            let sortKey = arr[0];
            let sortOrder = (arr[1] === 'asc') ? 'desc' : 'asc';
            let sortName = sortKey + '_' + sortOrder;
        
            this.sortData = this.$options.data().sortData;
            this.sortData[sortKey] = sortName;
            this.searchData.psort = sortName;
        
            this.list.sort((a, b) => {
                a[sortKey] = this.$util.isNull(a[sortKey]) ? '' : a[sortKey];
                b[sortKey] = this.$util.isNull(b[sortKey]) ? '' : b[sortKey];
                if (a[sortKey] < b[sortKey]) {
                    return sortOrder == 'asc'? -1: 1;
                } else if (a[sortKey] > b[sortKey]) {
                    return sortOrder == 'asc'? 1: -1;
                }
                return 0;
            });
            // this.searchList();
        },
        // 목록 전체체크
        checkAllList: function(value) {
            this.checkedList = [];
            if (value) {
                this.list.forEach(item => {
                    this.checkedList.push(item.giftidx);
                });
            }
        },
        // 체크박스 목록 개별체크
        checkList: function() {
            if (this.list.length > this.checkedList.length){
                this.isallchk = false;
            } else {
                this.isallchk = true;
            }
        },
        // 라디오버튼 선택
        setCheckedList: function(value) {
            this.checkedList = [value];
        },
        // 전체적용
        sendAllList: function() {
            if(this.list.length == 0) {
                alert("적용할 사은품이 존재하지 않습니다.");
                return;
            }
            for (let i=0; i<this.list.length; i++) {
                if (this.list[i].istrash === 'T') {
                    alert('미사용인 사은품이 존재합니다. 확인후 진행해주세요.');
                    return;
                }
            }
            if(confirm("전체적용 하시겠습니까?")) {
                // 팝업닫기 - 파라미터 전달
                this.closePopup(this.list);
            }
        },
        // 선택적용
        sendSelectedList: function() {
            if(this.checkedList.length == 0) {
                alert("적용할 사은품을 선택해 주세요.");
                return;
            }
            for (let i=0; i<this.checkedList.length; i++) {
                for (let j=0; j<this.list.length; j++) {
                    if (this.checkedList[i] === this.list[j].giftidx && this.list[j].istrash === 'T') {
                        alert('미사용인 사은품이 존재합니다. 확인후 진행해주세요.');
                        return;
                    }
                }
            }
            if(confirm("선택한 사은품을 적용하시겠습니까?")) {
                let targetList = [];
                this.checkedList.forEach(giftidx => {
                    this.list.forEach(item => {
                        if (giftidx == item.giftidx) {
                            targetList.push(item);
                        }
                    });
                });
                // 팝업닫기 - 파라미터 전달
                this.closePopup(targetList);
            }
        },
        // 팝업닫기
        closePopup: function(targetList) {
            if( typeof(this.callbackFn) == 'function') {
                this.callbackFn({list: targetList});
            }
            this.$modal.hide('commonModal');
        }
    },
    watch: {
        // 조회기간
        'searchData.period': function (value) {
            let params = value.split('_');
            let type = params[0];
            let addValue = parseInt(params[1]) * -1;

            if (type == 'aday') {
                this.searchData.startDate = this.$util.getAddDate(this.$util.getDate(), addValue, '-');
                this.searchData.endDate = this.$util.getAddDate(this.$util.getDate(), addValue, '-');
            } else if (type == 'days') {
                this.searchData.startDate = this.$util.getAddDate(this.$util.getDate(), addValue, '-');
                this.searchData.endDate = this.$util.getDate('-');
            } else if (type == 'months') {
                this.searchData.startDate = this.$util.getAddMonth(this.$util.getDate(), addValue, '-');
                this.searchData.endDate = this.$util.getDate('-');
            }
        }
    }
}
</script>
