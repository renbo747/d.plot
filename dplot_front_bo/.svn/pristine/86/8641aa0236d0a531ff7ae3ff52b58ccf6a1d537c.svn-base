<template>
    <!-- 회원추가 팝업 -->
    <div id="modal-wrap" class="modal" style="display: block;">
        <div class="modal-content" style="width: 1000px;">
            <div class="pop-header">
                <h2>회원추가</h2>
                <button type="button" class="pop-close" @click="$modal.hide('commonModal');"></button>
            </div>
            <div class="pop-body">
                <div class="boxing search-area">
                    <dl>
                        <dt>직접검색</dt>
                        <dd>
                            <select v-model="searchData.skey">
                                <option value="">전체</option>
                                <option value="id">아이디</option>
                                <option value="name">이름</option>
                                <option value="mobile">휴대폰번호</option>
                                <option value="email">이메일</option>
                            </select>
                            <input type="text" v-model="searchData.sword" maxlength="200" @keyup.enter="searchUserList(1)"/>
                        </dd>
                    </dl>
                    <dl>
                        <dt>가입일자</dt>
                        <dd>
                            <common-date-picker :value="searchData.startDate" @change="onChangeStartDate"></common-date-picker>
                            <span>-</span>
                            <common-date-picker :value="searchData.endDate" @change="onChangeEndDate"></common-date-picker>
                            <div class="radio_wrap">
                                <input type="radio" v-model="searchData.period" id="periodD_aday_1" value='aday_1'/><label for="periodD_aday_1">어제</label>
                                <input type="radio" v-model="searchData.period" id="periodD_aday_00" value='aday_0'/><label for="periodD_aday_00">오늘</label>
                                <input type="radio" v-model="searchData.period" id="periodD_days_77" value='days_7'/><label for="periodD_days_77">일주일</label>
                                <input type="radio" v-model="searchData.period" id="periodD_months_1" value='months_1'/><label for="periodD_months_1">1개월</label>
                                <input type="radio" v-model="searchData.period" id="periodD_months_3" value='months_3'/><label for="periodD_months_3">3개월</label>
                                <input type="radio" v-model="searchData.period" id="periodD_months_6" value='months_6'/><label for="periodD_months_6">6개월</label>
                            </div>
                        </dd>
                    </dl>
                    <dl>
                        <dt>회원유형</dt>
                        <dd>
                            <div>
                                <input type="checkbox" id="chkAllMemberD" v-model="searchData.isallmember" true-value="T" false-value="F" @change="checkAllMembertype">
                                <label for="chkAllMemberD">전체</label>
                            </div>
                            <div v-for="item in commonCode.dadamembertype" :key="item.cmcode">
                                <input type="checkbox" :id="'membertypeD_'+item.cmcode" v-model="searchData.membertypeArr" :true-value="[]" :value="item.cmcode"
                                            :disabled="searchData.isempreserve==='T' && item.detail !== 'T'">
                                <label :for="'membertypeD_'+item.cmcode">{{ item.codename }}</label>
                            </div>
                        </dd>
                    </dl>
                    <dl>
                        <dt>회원등급</dt>
                        <dd>
                            <div>
                                <input type="checkbox" id="chkAllMemlvD" v-model="searchData.isallmemlv" true-value="T" false-value="F" @change="checkAllMemlvtype">
                                <label for="chkAllMemlvD">전체</label>
                            </div>
                            <div v-for="item in commonCode.memlvtype" :key="item.cmcode">
                                <input type="checkbox" :id="'memlvtypeD_'+item.cmcode" v-model="searchData.memlvtypeArr" :true-value="[]" :value="item.cmcode">
                                <label :for="'memlvtypeD_'+item.cmcode">{{ item.codename }}</label>
                            </div>
                        </dd>
                    </dl>
                </div>
                <div class="btn-group">
                    <button type="button" class="btn big blue" @click="searchUserList(1)">검색</button>
                    <button type="button" class="btn big gray" @click="initSearchData">초기화</button>
                </div>
                <div class="caption-group mt10 clearfix">
                    <div class="total-group fl">
                        <span class="total">전체 <strong>{{ $util.maskComma(userListCnt) }}</strong>건</span>
                    </div>
                </div>
                <div class="scroll-y" style="max-height: 350px;">
                    <table cellpadding="0" cellspacing="0" class="data-tb align-c">
                        <colgroup>
                            <col width="4%" /><!-- checkbox -->
                            <col width="5%" /><!-- No -->
                            <col width="12%" /><!-- 아이디 -->
                            <col width="10%" /><!-- 이름 -->
                            <col width="8%" /><!-- 유형 -->
                            <col width="8%" /><!-- 등급 -->
                            <col width="15%" /><!-- 휴대폰번호 -->
                            <col width="" /><!-- 이메일 -->
                            <col width="12%" /><!-- 가입일자 -->
                        </colgroup>
                        <thead>
                            <tr>
                                <th><input v-if="!searchData.issingle" type="checkbox" id="chkallD" v-model="isallchk" @change="checkAllMemberList($event.target.checked)"/></th>
                                <th>No</th>
                                <th>아이디
                                    <button type="button" class="sort" :value="sortData.id"
                                        :class="[{up : sortData.id=== 'id_asc'}, {down : sortData.id === 'id_desc'}]"
                                        @click="sortToggle(sortData.id)"></button>
                                </th>
                                <th>이름</th>
                                <th>유형
                                    <button type="button" class="sort" :value="sortData.membertype"
                                        :class="[{up : sortData.membertype=== 'membertype_asc'}, {down : sortData.membertype === 'membertype_desc'}]"
                                        @click="sortToggle(sortData.membertype)"></button>
                                </th>
                                <th>등급
                                    <button type="button" class="sort" :value="sortData.memlvtype"
                                        :class="[{up : sortData.memlvtype=== 'memlvtype_asc'}, {down : sortData.memlvtype === 'memlvtype_desc'}]"
                                        @click="sortToggle(sortData.memlvtype)"></button>
                                </th>
                                <th>휴대폰번호</th>
                                <th>이메일</th>
                                <th>가입일자
                                    <button type="button" class="sort" :value="sortData.regdate"
                                        :class="[{up : sortData.regdate=== 'regdate_asc'}, {down : sortData.regdate === 'regdate_desc'}]"
                                        @click="sortToggle(sortData.regdate)"></button>
                                </th>
                            </tr>
                        </thead>
                        <tbody v-if="userList.length > 0">
                            <tr v-for="(item, index) in userList" :key="item.userno">
                                <td>
                                    <input type="checkbox" v-if="!searchData.issingle" :id="item.userno" v-model="checkedList" :value="item.userno" @change="checkMemberList"/>
                                    <input type="radio" v-if="searchData.issingle" class="circle" name="checked" @change="setCheckedList(item.userno)"/>
                                </td>
                                <td>{{ loopNumberForPaging(index) }}</td>
                                <td>{{ item.userid }}</td>
                                <td>{{ item.username }}</td>
                                <td>{{ item.dadamembertypename }}</td>
                                <td>{{ item.memlvtypename }}</td>
                                <td>{{ $util.maskTel(item.mobile) }}</td>
                                <td>{{ item.email }}</td>
                                <td>{{ item.regdate }}</td>
                            </tr>
                        </tbody>
                        <tbody v-else>
                            <tr><td colspan="9">조회 결과가 존재하지 않습니다.</td></tr>
                        </tbody>
                    </table>
                </div>
                <div class="bottom-group">
                    <div class="paging">
                        <common-page-navigator :pagingData="pagingData" v-on:setPagingData="setPagingData"/>
                    </div>
                </div>
                <div class="btn-group mt10">
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
import CommonPageNavigator from '@views.admin/common/CommonPageNavigator.vue';
import CommonDatePicker from '@views.admin/common/CommonDatePicker.vue';
import store from '@js/store';

export default {
    name: 'CommonAddUserPopup',
    components: {
        CommonPageNavigator,
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
                period: 'months_3',
                state: store.getters['ADMIN'].CM_STATE_REAL,  //상태(MST001 : 정상)
                isallmember: 'T',   //회원유형전체여부
                membertypeArr: [],  //다중대상회원유형Array
                isallmemlv: 'T',    //회원등급전체여부
                memlvtypeArr: [],   //다중대상회원등급Array
                issingle: false,    //단일상품여부
                isempreserve: ''    //임직원여부
            },
            sortData: {
                psort: 'regdate_desc',
                id : 'id_asc',
                membertype : 'membertype_asc',
                memlvtype : 'memlvtype_asc',
                regdate : 'regdate_desc'
            },
            commonCode: {
                dadamembertype: [], // 다다픽회원유형
                memlvtype: []       // 회원등급
            },
            pagingData: {
              pageCount: 100,       //페이징 옵션 (최대수)
              page: 1,              //현재 페이지
              listCount: 0          //총 페이지
            },
            isallchk: false,
            checkedList: [],
            userListCnt: 0,
            userList: []
        }
    },
    methods : {
        onInit: function() {
            // 팝업호출시 넘어온 조회조건 세팅
            let paramsKey = ['issingle', 'isempreserve'];
            if (!this.$util.isNull(this.params)) {
                for (const [key, value] of Object.entries(this.params)) {
                    if (paramsKey.indexOf(key) > -1) {
                        this.searchData[key] = value;
                    }
                }
            }
            this.getCommonCodeList();
        },
        // 검색조건 초기화
        initSearchData: function () {
            this.searchData.skey = '';
            this.searchData.sword = '';
            this.searchData.startDate = this.$util.getAddMonth(this.$util.getDate(), -1, '-');
            this.searchData.endDate = this.$util.getDate('-');
            this.searchData.period = 'months_1'
            this.searchData.isallmember = 'T';
            this.searchData.isallmemlv = 'T';
            this.checkAllMembertype();
            this.checkAllMemlvtype();
        },
        // 공통코드 목록 조회
        getCommonCodeList: function() {
            let cmclassArr = ['DADAMEMBERTYPE', 'MEMLVTYPE'];
            this.$http.post('/common/code/map/list', {cmclass : cmclassArr, isloading: false})
                .then(result =>{
                    let data = result.data;
                    for (const [key] of Object.entries(data)) {
                        this.commonCode[key] = data[key];
                    }
                            
                    // 검색조건 초기화
                    this.initSearchData();
                            
                    // 사용자목록 조회
                    this.searchUserList(1);
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 페이징데이터 세팅
        setPagingData(param){
            this.pagingData = param;
            this.searchUserList();
        },
        loopNumberForPaging(index) {
            if(this.pagingData.page > 1){
                let page = this.pagingData.page - 1;
                return (index+1) + (page * this.pagingData.pageCount);
            } else {
                return (index+1);
            }
        },
        // 사용자 조회
        searchUserList: function(page) {
            let params = Object.assign(this.searchData, this.pagingData);
            params.page = (typeof page === 'undefined') ? this.pagingData.page : 1;
            this.$http.post('/admin/common/member/list', params)
                .then(result => {
                    this.$util.debug(result);
                    let data = result.data;
                    this.userList = data.list;
                    this.userListCnt = data.count.totalcnt;
                    this.pagingData.listCount = data.count.totalcnt;
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
        
            this.searchUserList();
        },
        // 유형별 전체체크
        checkAllMembertype: function() {
            let isAllCheck = this.searchData.isallmember;
            this.searchData.membertypeArr = [];
            if (isAllCheck == 'T') {
                if (this.searchData.isempreserve === 'T') {
                    for(let type of this.commonCode.dadamembertype){
                        if (type.detail === 'T') {
                        this.searchData.membertypeArr.push(type.cmcode);
                        }
                    }
                } else {
                    for(let type of this.commonCode.dadamembertype){
                        this.searchData.membertypeArr.push(type.cmcode);
                    }
                }
            }
        },
        // 등급별 전체체크
        checkAllMemlvtype: function() {
            let isAllCheck = this.searchData.isallmemlv;
            this.searchData.memlvtypeArr = [];
            if (isAllCheck == 'T') {
                for(let type of this.commonCode.memlvtype){
                    this.searchData.memlvtypeArr.push(type.cmcode);
                }
            }
        },
        // 사용자목록 전체체크
        checkAllMemberList: function(value) {
            this.checkedList = [];
            if (value) {
                this.userList.forEach(item => {
                    this.checkedList.push(item.userno);
                });
            }
        },
        // 체크박스 사용자목록 개별체크
        checkMemberList: function() {
            if (this.userList.length > this.checkedList.length){
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
            if(this.userList.length == 0) {
                alert("적용할 회원이 존재하지 않습니다.");
                return;
            }
            if(confirm("전체적용 하시겠습니까?")) {
                // 팝업닫기 - 파라미터 전달
                this.closePopup(this.userList);
            }
        },
        // 선택적용
        sendSelectedList: function() {
            if(this.checkedList.length == 0) {
                alert("적용할 회원을 선택해 주세요.");
                return;
            }
            if(confirm("선택한 회원을 적용하시겠습니까?")) {
                let targetList = [];
                this.checkedList.forEach(userno => {
                    this.userList.forEach(item => {
                        if (userno == item.userno) {
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
        // 유형별
        'searchData.membertypeArr': function(value) {
            let chkCnt = this.commonCode.dadamembertype.length;
            if (this.searchData.isempreserve === 'T') {
                chkCnt = this.commonCode.dadamembertype.filter(obj => {return obj.detail === 'T';}).length;
            }
            if (value.length < chkCnt) {
                this.searchData.isallmember = 'F';
            } else {
                this.searchData.isallmember = 'T';
            }
        },
        // 등급별
        'searchData.memlvtypeArr': function(value) {
            if (value.length < this.commonCode.memlvtype.length) {
                this.searchData.isallmemlv = 'F';
            } else {
                this.searchData.isallmemlv = 'T';
            }
        },
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
