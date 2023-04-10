<template>
    <div class="content m-leftmenu">
        <common-navigator></common-navigator>
        <div class="inner">
            <div class="clearfix">
                <div class="bar-title fl">신상품</div>
            </div>
            <div class="boxing search-area">
                <dl>
                    <dt>직접검색</dt>
                    <dd>
                        <select v-model="searchData.skey">
                            <option value="">전체</option>
                            <option value="dealername">파트너사명</option>
                            <option value="brandname">브랜드</option>
                            <option value="goodscode">상품코드</option>
                            <option value="mdname">담당MD</option>
                            <option value="regusername">등록(지정)자</option>
                        </select>
                        <input type="text" v-model="searchData.sword"/>
                    </dd>
                </dl>
                <dl>
                    <dt>등록(지정)기간</dt>
                    <dd>
                        <CommonDatePicker :value="searchData.startDate" @change="onChangeStartDate"/>
                        <span>-</span>
                        <CommonDatePicker :value="searchData.endDate" @change="onChangeEndDate"/> 
                        <div class="radio_wrap">
                            <input type="radio" name="period" v-model="searchData.period" id="rd1" value='-1'/><label for="rd1">어제</label>
                                <input type="radio" name="period" v-model="searchData.period" id="rd2" value='0'/><label for="rd2">오늘</label>
                                <input type="radio" name="period" v-model="searchData.period" id="rd3" value='7'/><label for="rd3">일주일</label>
                                <input type="radio" name="period" v-model="searchData.period" id="rd4" value='1'/><label for="rd4">1개월</label>
                                <input type="radio" name="period" v-model="searchData.period" id="rd5" value='3' checked/><label for="rd5">3개월</label>
                                <input type="radio" name="period" v-model="searchData.period" id="rd6" value='6'/><label for="rd6">6개월</label>
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
                    <dt>사용여부</dt>
                    <dd>
                        <div class="radio_wrap wide3">
                            <input type="radio" v-model="searchData.istrash" name="group00" id="group01" value="" checked><label for="group01">전체</label>
                            <input type="radio" v-model="searchData.istrash" name="group00" id="group02" value="F"><label for="group02">사용</label>
                            <input type="radio" v-model="searchData.istrash" name="group00" id="group03" value="T"><label for="group03">미사용</label>
                        </div>
                    </dd>
                </dl>
                <dl>
                    <dt>적용채널</dt>
                    <dd>
                        <span>
                            <input type="checkbox" v-model="searchData.isallmuappch" id="group11" true-value="T" false-value="F" @change="checkAllMuAppch" checked>
                            <label for="group11">전체</label>
                        </span>
                        <span class="check-for" v-for="item in commonCode.MUAPPCHTYPE" :key="item.cmcode">
                            <input type="checkbox" v-model="searchData.muappchtypeArr" :id="'group_'+item.cmcode" true-value="[]" :value="item.cmcode">
                            <label :for="'group_'+item.cmcode">{{item.codename}}</label>
                        </span>
                    </dd>
                </dl>
                <dl>
                    <dt>판매구분</dt>
                    <dd>
                        <div class="radio_wrap wide3">
                            <input type="radio" v-model="searchData.isdealer" name="group02" id="group21" value="" checked/><label for="group21">전체</label>
                            <input type="radio" v-model="searchData.isdealer" name="group02" id="group22" value="F"/><label for="group22">직매입</label>
                            <input type="radio" v-model="searchData.isdealer" name="group02" id="group23" value="T"/><label for="group23">위탁</label>
                        </div>
                        <select :disabled="searchData.isdealer !== 'T'" v-model="searchData.dealerno">
                            <option value="">파트너사 선택</option>
                            <option v-for="item in partnersList" :key="item.no" :value="item.no">{{ item.name }} </option>
                        </select>
                    </dd>
                </dl>
                <dl>
                    <dt>메인노출여부</dt>
                    <dd>
                        <div class="radio_wrap wide3">
                            <input type="radio" v-model="searchData.ismaindisp" name="group03" id="group31" value="" checked><label for="group31">전체</label>
                            <input type="radio" v-model="searchData.ismaindisp" name="group03" id="group32" value="T"><label for="group32">일반</label>
                            <input type="radio" v-model="searchData.ismaindisp" name="group03" id="group33" value="F"><label for="group33">메인노출</label>
                        </div>
                    </dd>
                </dl>
                <dl>
                    <dt>대상회원유형</dt>
                    <dd>
                        <span>
                            <input type="checkbox" v-model="searchData.isallmumember" id="group41" true-value="T" false-value="F" @change="checkAllMuMemer" checked>
                            <label for="group41">전체</label>
                        </span>
                        <span class="check-for" v-for="item in commonCode.MUMEMBERTYPE" :key="item.cmcode">
                            <input type="checkbox" v-model="searchData.mumembertypeArr" :id="'group_'+item.cmcode" true-value="[]" :value="item.cmcode">
                            <label :for="'group_'+item.cmcode">{{item.codename}}</label>
                        </span>    
                    </dd>
                </dl>
                <dl>
                    <dt>대상회원등급</dt>
                    <dd>
                        <span>
                            <input type="checkbox" v-model="searchData.isallmumemlv" id="group51" true-value="T" false-value="F" @change="checkAllMuMemLv" checked>
                            <label for="group51">전체</label>
                        </span>
                        <span class="check-for" v-for="item in commonCode.MUMEMLVTYPE" :key="item.cmcode">
                            <input type="checkbox" v-model="searchData.muMemLvtypeArr" :id="'group_'+item.cmcode" true-value="[]" :value="item.cmcode">
                            <label :for="'group_'+item.cmcode">{{item.codename}}</label>
                        </span>
                    </dd>
                </dl>
            </div>
            <div class="btn-group">
                <button type="button" class="btn big blue" v-if="isRead" @click="onSearch">검색</button>
                <button type="button" class="btn big gray" v-if="isRead" @click="onSearchDataReset">초기화</button>
            </div>
            <div class="caption-group mt10 clearfix">
                <div class="total-group fl">
                    <span class="total">전체 <strong>{{totalcnt}}</strong>건</span>
                    <span>사용 <strong>{{usecnt}}</strong>건</span>
                    <span>미사용 <strong>{{trashcnt}}</strong>건</span>
                </div>
                <div class="btn-group fr">
                    <button type="button" class="btn blue-line" v-if="isWrite" @click="onChangeUse('F')">사용</button>
                    <button type="button" class="btn red-line" v-if="isWrite" @click="onChangeUse('T')">미사용</button>
                    <button type="button" class="btn green-line" v-if="isRead" @click="fnExcelDownload()"><i class="icon-excel"></i>엑셀다운로드</button>
                </div>
            </div>
            <div class="scroll-y" style="max-height: calc(100vh - 600px);">
                <table cellpadding="0" cellspacing="0" class="data-tb align-c">
                    <colgroup>
                        <col width="2%" /><!-- checkbox -->
                        <col width="2.5%" /><!-- No -->
                        <col width="3.5%" /><!-- 메인노출 -->
                        <col width="3.5%" /><!-- 판매구분 -->
                        <col width="5%" /><!-- 파트너사명 -->
                        <col width="4%" /><!-- 브랜드 -->
                        <col width="5%" /><!-- 상품코드 -->
                        <col width="62px" /><!-- 상품명(이미지) -->
                        <col width="%" /><!-- 상품명 -->
                        <col width="4%" /><!-- 담당MD -->
                        <col width="5%" /><!-- 유형 -->
                        <col width="4.5%" /><!-- 등급 -->
                        <col width="7%" /><!-- 적용채널 -->
                        <col width="4%" /><!-- 사용여부 -->
                        <col width="4%" /><!-- 등록(지정)자 -->
                        <col width="6%" /><!-- 등록(지정)일자 -->
                    </colgroup>
                    <thead>
                        <tr>
                            <th><input type="checkbox" id="chkall" v-model="isChecked" @change="onCheckAll($event.target.checked)"/></th>
                            <th>No</th>
                            <th>메인노출</th>
                            <th>판매구분</th>
                            <th>파트너사명</th>
                            <th>브랜드</th>
                            <th>상품코드<button type="button" v-if="isRead" :value="sortData.goodscode" class="sort" :class="[{up : sortData.goodscode === 'goodscode_asc'}, {down : sortData.goodscode === 'goodscode_desc'}]" @click="sortToggle(sortData.goodscode)"></button></th>
                            <th colspan="2">상품명</th>
                            <th>담당MD</th>
                            <th>유형</th>
                            <th>등급</th>
                            <th>적용채널</th>
                            <th>사용여부</th>
                            <th>등록(지정)자</th>
                            <th>등록(지정)일자<button type="button" v-if="isRead" :value="sortData.regdate" class="sort" :class="[{up : sortData.regdate === 'regdate_asc'}, {down : sortData.regdate === 'regdate_desc'}]" @click="sortToggle(sortData.regdate)"></button></th>
                        </tr>
                    </thead>
                    <tbody v-if="this.listData.length > 0">
                        <tr v-for="(row, i) in this.listData" :key="i">
                            <td><input type="checkbox" v-model="moveData.targetIdx" :id="'chk'+i" class="circle" :value="i"/></td>
                            <td>{{row.no}}</td>
                            <td>{{row.ismaindisp === 'T' ? '●':''}}</td>
                            <td>{{row.ispbgoodsname}}</td>
                            <td><a class="link" @click="goDetail(row.dealerno)">{{row.dealername}}</a></td>
                            <td>{{row.brandname}}</td>
                            <td>{{row.goodscode}}</td>
                            <td>
                                <div class="img-thumb size60" :class="{'no-image': $util.isNull(row.fullpath)}">
                                    <img :src="row.fullpath" v-if="!$util.isNull(row.fullpath)">
                                </div>
                            </td>
                            <td class="left no-left">
                                <span class="small-txt">{{ row.fullcategoryname }}</span>
                                <a href="#" class="link dpb">{{ row.goodsname }}</a>
                            </td>
                            <td>{{row.mdname}}</td>
                            <td>{{row.mumembertype}}</td>
                            <td>{{row.mumemlvtype}}</td>
                            <td>{{row.muappchtype}}</td>
                            <td>{{row.istrash === 'F' ? '사용' : '미사용'}}</td>
                            <td>{{row.regusername}}</td>
                            <td>{{row.regdate}}</td>
                        </tr>
                    </tbody>
                    <tbody v-else>
                        <tr><td colspan="16">대상 상품이 없습니다.</td></tr>
                    </tbody>
                </table>
            </div>
            <div class="bottom-group">
                <CommonArraySort :list-data="listData"
                        :move-data="moveData"
                        v-on:getModifySortNumArray="getModifySortNumArray"
                        :is-active-save-btn="true"
                        v-if="isWrite"
                />
                <div class="btn-group">
                    <button type="button" class="btn blue" v-if="isWrite" @click="openGoodsAdditionPopup">신상품 등록</button>
                </div>
            </div>
        </div>
        <PartnersApplyDetail v-if="detailShow" v-bind:activeUserNo="activeUserNo"
                         v-on:closeDetail="closeDetail"></PartnersApplyDetail>
    </div>
</template>

<script>
import CommonNavigator from "../../common/CommonNavigator";
import CommonDatePicker from "@views.admin/common/CommonDatePicker";
import CommonArraySort from "@views.admin/common/CommonArraySort";
import CommonAddGoodsPopup from '@views.admin/common/popup/CommonAddGoodsPopup.vue';
import PartnersApplyDetail from "@views.admin/partners/apply/PartnersApplyDetail";

export default {
    name: 'admin.operation.display.newgoods',
    components: {
        CommonNavigator,
        CommonDatePicker,
        CommonArraySort,
        PartnersApplyDetail
    },
    data() {
        return {
            searchData: {
                skey: '',            // 검색조건
                sword: '',           // 검색조건
                startDate: '',       // 시작날짜
                endDate: '',         // 종료날짜
                period: 3,           // 검색기간
                depth1cateidx: '',   // 카테고리 대분류
                depth2cateidx: '',   // 카테고리 중분류
                depth3cateidx: '',   // 카테고리 소분류
                depth4cateidx: '',   // 카테고리 세분류
                istrash: '',         // 사용여부
                isallmuappch: 'T',   // 적용채널 전체여부
                muappchtype: '',     // 적용채널
                muappchtypeArr: [],  // 적용채널Array
                isallmumember: 'T',  // 대상회원유형 전체여부
                mumembertype: '',    // 대상회원유형
                mumembertypeArr: [], // 대상회원유형Array
                isallmumemlv: 'T',   // 대상회원등급전체여부
                muMemLvtype: '',     // 대상회원등급
                muMemLvtypeArr: [],  // 대상회원등급Array
                isdealer: '',        // 판매구분
                dealerno: '',        // 파트너사
                ismaindisp: '',      // 메인노출여부 
                reguserid: '',      // 현재 접속자
            },
            commonCode: {
                MUAPPCHTYPE: [],    // 적용채널
                MUMEMBERTYPE: [],   // 대상회원유형
                MUMEMLVTYPE: [],    // 대상회원등급
            },
            sortData: {
                goodscode : 'goodscode_desc',
                regdate : 'regdate_desc',
            },
            categoryObj: {
                depth1list: [],
                depth2list: [],
                depth3list: [],
                depth4list: [],
            },
            moveData: {                       // 노출순위 데이터
                moveValue: '',                  // 움직일 값
                targetIdx: [],                  // 대상 위치
                code: 'U',                      // 위, 아래 코드
                isSuccess: false,               // 저장 성공 여부 (** 중요)
            },
            isChecked: false,
            partnersList: [],    // 전체파트너사
            listData: [],
            totalcnt: 0,        // 전체 수량
            usecnt: 0,          // 사용 수량   
            trashcnt: 0,        // 미사용 수량
            isRead: false,      // 읽기여부
            isWrite: false,     // 쓰기여부
            activeUserNo: '',   // 파트너사 상세
            detailShow: false,  // 파트너사 상세 오픈여부
        }
    },
    mounted() {
        this.$http.post('/admin/common/pageAuth/check', {url : this.$options.name, isloading: false}).then(result => {
            this.isRead = (result.data.isread === 'T');
            this.isWrite = (result.data.iswrite === 'T');

            if(this.isRead){
                this.oninit();
            }
        }).catch(error => {
            this.$util.debug(error);
        });
    },
    methods: {
        oninit(){
            this.searchData.startDate = this.$util.getAddMonth(this.$util.getDate(''), -3, '-');
            this.searchData.endDate = this.$util.getDate('-');

            this.$http.post("/admin/goods/brand/search/partners", {})
            .then(result => {
                this.partnersList = result.data.partnerlist;
            })
            .catch(error => {
                this.$util.debug(error);
            });

            // 유저정보 세팅
            let userInfo = this.$storage.getLocalStorage("ADMIN_USER");
            this.searchData.reguserid = userInfo.id;

            // 공통코드 조회
            this.getCommonCodeList();
            // 대분류 카테고리 목록 조회
            this.getCategoryCodeList(1, 0);
            this.getCategoryCodeList(params);
        },
        getCommonCodeList(){
            for(const [key] of Object.entries(this.commonCode)) {
                let params = { cmclass : key };
                this.$http.post('/common/code/list', params)
                .then(result => {
                    if(result.statusCode === 200){
                        this.commonCode[key] = result.data.list;
                        // 적용채널 초기값 전체선택
                        if(key === 'MUAPPCHTYPE'){
                            this.checkAllMuAppch();
                        }
                        // 대상회원유형 초기값 전체선택
                        if(key === 'MUMEMBERTYPE'){
                            this.checkAllMuMemer();
                        }
                        // 대상회원등급 초기값 전체선택
                        if(key === 'MUMEMLVTYPE') {
                            this.checkAllMuMemLv();
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
            this.$util.componentSetSearchParam(this);
        },
        // 카테고리분류 목록 조회
        getCategoryCodeList: function(targetDepth, parent) {
            let params = { idx: parent };
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
        onSearch(){
            this.isChecked = false;
            let param = this.searchData;

            this.$http.post("/admin/operation/display/newgoods/search", param)
            .then(result => {
                let data = result.data;
                this.listData = data.list;
                this.totalcnt = data.totalcnt;
                this.usecnt = data.usecnt;
                this.trashcnt = data.trashcnt;
                this.checkData = '';

                this.$util.dataSetSearchParam(this);
            })
            .catch(error => {
                this.$util.debug(error);
            });
        },
        onSearchDataReset(){
            this.searchData = this.$options.data().searchData;
            this.searchData.startDate = this.$util.getAddMonth(this.$util.getDate(''), -3, '-');
            this.searchData.endDate = this.$util.getDate('-');
            this.moveData = this.$options.data().moveData;
            this.categoryObj.depth2list = [];
            this.categoryObj.depth3list = [];
            this.categoryObj.depth4list = [];
            this.moveData.targetIdx = [];
            this.checkAllMuAppch();
            this.checkAllMuMemer();
            this.checkAllMuMemLv();
            this.getCategoryCodeList(1, 0);
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
            this.searchData.startDate = val;
        },
        // 날짜 picker 콜백 함수
        onChangeEndDate(val) {
            this.searchData.endDate = val;
        },
        checkAllMuAppch() {
            let isAllCheck = this.searchData.isallmuappch;
            this.searchData.muappchtypeArr = [];
            if (isAllCheck === 'T') {
                for(let type of this.commonCode.MUAPPCHTYPE){
                    this.searchData.muappchtypeArr.push(type.cmcode);
                }
            }
        },
        checkAllMuMemer() {
            let isAllCheck = this.searchData.isallmumember;
            this.searchData.mumembertypeArr = [];
            if (isAllCheck === 'T') {
                for(let type of this.commonCode.MUMEMBERTYPE){
                    this.searchData.mumembertypeArr.push(type.cmcode);
                }
            }
        },
        checkAllMuMemLv() {
            let isAllCheck = this.searchData.isallmumemlv;
            this.searchData.muMemLvtypeArr = [];
            if (isAllCheck === 'T') {
                for(let type of this.commonCode.MUMEMLVTYPE){
                    this.searchData.muMemLvtypeArr.push(type.cmcode);
                }
            }
        },
        onChangeUse(check){
            if (this.moveData.targetIdx.length === 0) { // 체크된 데이터가 없다면
                alert("체크된 리스트가 없습니다.");
                return;
            } 
            let thisObj = this;
            let list = [];
            this.moveData.targetIdx.forEach(function(item){
                list.push(thisObj.listData[item].idx);
            });

            let params = {
                list: list,
                istrash: check
            }

            if (confirm("상태를 전환하시겠습니까?")) {
            this.$http.post("/admin/operation/display/newgoods/update", params)
                .then(result => {
                    if (result.statusCode == 200) {
                    alert("저장이 완료되었습니다.");
                    this.moveData.targetIdx = [];
                    this.onSearch();
                    } else {
                    alert("저장에 실패했습니다.");
                    }
                })
                .catch(error => {
                    this.$util.debug(error);
                })
            }
        },
        fnExcelDownload(){
            let param = this.searchData;
            param.time = this.$util.getDate()+this.$util.getTime();
            let postConfig = { responseType: 'arraybuffer' };
            this.$http.post("/admin/operation/display/newgoods/excel", param, postConfig)
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

            this.$http.post("/admin/operation/display/newgoods/update", params)
            .then(result => {
                if (result.statusCode === 200) {
                alert("저장이 완료되었습니다.");
                this.onSearch();
                this.moveData.targetIdx = [];
                this.moveData.isSuccess = false;            // ** 중요
                }
            })
            .catch(error => {
                this.$util.debug(error);
            });
        },
        // 추가상품 팝업 오픈
        openGoodsAdditionPopup() {
            let params = {
                isdisplay: 'T',
                goodsselltypeArr: ['GST002']
            }
            this.$eventBus.$emit('modalShow', CommonAddGoodsPopup, params,
                (result) => {
                   let param = {
                        data: result.list,
                        reguserid : this.searchData.reguserid,
                    }
                    
                    this.$http.post('/admin/operation/display/newgoods/insert', param)
                    .then(result => {
                        if(result.statusCode === 200){
                            alert(result.data.msg);
                            this.onSearch();
                        }
                    })
                    .catch(error => {
                        this.$util.debug(error);
                    });
                }
            );
        },
        goDetail: function (userNo) {
            this.activeUserNo = userNo;
            this.detailShow = true;
        },
        closeDetail: function () {
            this.detailShow = false;
        },
        // 전체 체크
        onCheckAll(checked) {
            if (checked) { // 전체 체크
                for (let i in this.listData) {
                    this.moveData.targetIdx[i] = i;
                }
            } else { // 전체 체크 해제
                this.moveData.targetIdx = [];
            }
        },
    },
    watch: {
        'searchData.period': function(val){
            let dayType = ["-1", "0", "7"];
            let monthType = ["1", "3", "6"];
            let valueToInt = parseInt(val);

            if(dayType.includes(val)){
                if(valueToInt >= 0){
                    this.searchData.startDate = this.$util.getAddDate(this.$util.getDate(''), (-1 * valueToInt), '-');
                    this.searchData.endDate = this.$util.getDate('-');
                }else{
                    this.searchData.startDate = this.$util.getAddDate(this.$util.getDate(''), valueToInt, '-');
                    this.searchData.endDate = this.$util.getDate('-');
                }
            }else if(monthType.includes(val)){
                this.searchData.startDate = this.$util.getAddMonth(this.$util.getDate(''), (-1 * valueToInt), '-');
                this.searchData.endDate = this.$util.getDate('-');
            }
        },
        'searchData.muappchtypeArr': function(value){
            if (value.length < this.commonCode.MUAPPCHTYPE.length) {
                this.searchData.isallmuappch = 'F';
            } else {
                this.searchData.isallmuappch = 'T';
            }
            this.searchData.muappchtype = this.searchData.muappchtypeArr.join();
        },
        'searchData.mumembertypeArr': function(value){
            if (value.length < this.commonCode.MUMEMBERTYPE.length) {
                this.searchData.isallmumember = 'F';
            } else {
                this.searchData.isallmumember = 'T';
            }
            this.searchData.mumembertype = this.searchData.mumembertypeArr.join();
        },
        'searchData.muMemLvtypeArr': function(value){
            if (value.length < this.commonCode.MUMEMLVTYPE.length) {
                this.searchData.isallmumemlv = 'F';
            } else {
                this.searchData.isallmumemlv = 'T';
            }
            this.searchData.muMemLvtype = this.searchData.muMemLvtypeArr.join();
        },
        // 카테고리 선택
        'searchData.depth1Category.idx': function(value) {
            let params = {depth: 1, idx: value};
            this.getCategoryCodeList(params);
        },
        'searchData.depth2Category.idx': function(value) {
            let params = {depth: 2, idx: value};
            this.getCategoryCodeList(params);
        },
        'searchData.depth3Category.idx': function(value) {
            let params = {depth: 3, idx: value};
            this.getCategoryCodeList(params);
        },
        'searchData.depth4Category.idx': function(value) {
            let params = {depth: 4, idx: value};
            this.getCategoryCodeList(params);
        },
    }
}
</script>

<style>

</style>