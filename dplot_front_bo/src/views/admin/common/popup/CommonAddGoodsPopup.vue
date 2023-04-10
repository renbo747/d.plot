<template>
    <!-- 상품추가 팝업 -->
    <div id="modal-wrap" class="modal" style="display: block;">
        <div class="modal-content" style="width: 1400px;">
            <div class="pop-header">
                <h2>상품추가</h2>
                <button type="button" class="pop-close" @click="$modal.hide('commonModal');"></button>
            </div>
            <div class="pop-body">
                <div class="boxing search-area">
                    <dl>
                        <dt>직접검색</dt>
                        <dd>
                            <select v-model="searchData.skey">
                                <option v-for="item in searchData.skeyArr" :key="item.key" :value="item.key">{{ item.name }}</option>
                            </select>
                            <input type="text" v-model="searchData.sword" maxlength="200" @keyup.enter="searchGoodsList"/>
                        </dd>
                    </dl>
                    <dl>
                        <dt>등록일자</dt>
                        <dd>
                            <common-date-picker :value="searchData.startDate" @change="onChangeStartDate"></common-date-picker>
                            <span>-</span>
                            <common-date-picker :value="searchData.endDate" @change="onChangeEndDate"></common-date-picker>
                            <div class="radio_wrap">
                                <input type="radio" v-model="searchData.period" id="periodCP_aday_1" value='aday_1'/><label for="periodCP_aday_1">어제</label>
                                <input type="radio" v-model="searchData.period" id="periodCP_aday_0" value='aday_0'/><label for="periodCP_aday_0">오늘</label>
                                <input type="radio" v-model="searchData.period" id="periodCP_days_7" value='days_7'/><label for="periodCP_days_7">일주일</label>
                                <input type="radio" v-model="searchData.period" id="periodCP_months_1" value='months_1'/><label for="periodCP_months_1">1개월</label>
                                <input type="radio" v-model="searchData.period" id="periodCP_months_3" value='months_3'/><label for="periodCP_months_3">3개월</label>
                                <input type="radio" v-model="searchData.period" id="periodCP_months_6" value='months_6'/><label for="periodCP_months_6">6개월</label>
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
                        <dt>판매상태</dt>
                        <dd>
                            <div class="check-wrap">
                                <input type="checkbox" id="chkAllSellTypeD" v-model="searchData.isallselltype" true-value="T" false-value="F" @change="checkAllSellType">
                                <label for="chkAllSellTypeD">전체</label>
                            </div>
                            <div class="check-wrap" v-for="item in commonCode.goodsselltype" :key="item.cmcode">
                                <input type="checkbox" :id="'goodsselltypeD_'+item.cmcode" v-model="searchData.goodsselltypeArr" :true-value="[]" :value="item.cmcode"/>
                                <label :for="'goodsselltypeD_'+item.cmcode">{{ item.codename }}</label>
                            </div>
                        </dd>
                    </dl>
                    <dl>
                        <dt>상품상태</dt>
                        <dd>
                            <div class="check-wrap">
                                <input type="checkbox" id="chkAllTypeD" v-model="searchData.isalltype" true-value="T" false-value="F" @change="checkAllType">
                                <label for="chkAllTypeD">전체</label>
                            </div>
                            <div class="check-wrap" v-for="item in commonCode.goodsdivtype" :key="item.cmcode">
                                <input type="checkbox" :id="'goodsdivtypeD_'+item.cmcode" v-model="searchData.goodsdivtypeArr" :true-value="[]" :value="item.cmcode"/>
                                <label :for="'goodsdivtypeD_'+item.cmcode">{{ item.codename }}</label>
                            </div>
                        </dd>
                    </dl>
                    <dl>
                        <dt>전시여부</dt>
                        <dd>
                            <div class="radio_wrap wide3">
                                <input type="radio" name="isdisplayD" id="isdisplayDALL" value="" v-model="searchData.isdisplay"/>
                                <label for="isdisplayDALL">전체</label>
                                <input type="radio" name="isdisplayD" id="isdisplayDT" value="T" v-model="searchData.isdisplay"/>
                                <label for="isdisplayDT">전시</label>
                                <input type="radio" name="isdisplayD" id="isdisplayDF" value="F" v-model="searchData.isdisplay"/>
                                <label for="isdisplayDF">미 전시</label>
                            </div>
                        </dd>
                    </dl>
                </div>
                <div class="btn-group">
                    <button type="button" class="btn big blue" @click="searchGoodsList">검색</button>
                    <button type="button" class="btn big gray" @click="initSearchData">초기화</button>
                </div>
                <div class="caption-group mt10 clearfix">
                    <div class="total-group fl">
                        <span class="total">전체 <strong>{{ goodsList.length }}</strong>건</span>
                    </div>
                </div>
                <div class="scroll-y" style="max-height: 400px;">
                    <table cellpadding="0" cellspacing="0" class="data-tb align-c">
                        <colgroup>
                            <col width="2%" v-if="!searchData.isread"/><!-- checkbox -->
                            <col width="3%" /><!-- No -->
                            <col width="8%" /><!-- 파트너사명 -->
                            <col width="8%" /><!-- 브랜드명 -->
                            <col width="10%" /><!-- 상품코드 -->
                            <col width="6%" v-if="searchData.isshowoption==='T'" /><!-- 단품코드 -->
                            <col width="62px" /><!-- 이미지 -->
                            <col width="" /><!-- 상품명 -->
                            <col width="12%" v-if="searchData.isshowoption==='T'" /><!-- 옵션명 -->
                            <col width="8%" v-if="searchData.isshowkeyword==='T'" /><!-- 키워드 -->
                            <col width="7%" /><!-- 판매가 -->
                            <col width="5%" /><!-- 판매상태 -->
                            <col width="5%" /><!-- 상품상태 -->
                            <col width="5%" /><!-- 전시상태 -->
                            <col width="5%" /><!-- 재고 -->
                            <col width="7%" /><!-- 등록일자 -->
                        </colgroup>
                        <thead>
                            <tr>
                                <th v-if="!searchData.isread"><input v-if="!searchData.issingle" type="checkbox" id="chkallD" v-model="isallchk" @change="checkAllGoodsList($event.target.checked)"/></th>
                                <th>No</th>
                                <th>파트너사명</th>
                                <th>브랜드명</th>
                                <th>상품코드
                                    <button type="button" class="sort" :value="sortData.code"
                                        :class="[{up : sortData.code=== 'code_asc'}, {down : sortData.code === 'code_desc'}]"
                                        @click="sortToggle(sortData.code)"></button>
                                </th>
                                <th v-if="searchData.isshowoption==='T'">단품코드</th>
                                <th colspan="2">상품명</th>
                                <th v-if="searchData.isshowoption==='T'">옵션명</th>
                                <th v-if="searchData.isshowkeyword==='T'">대표키워드</th>
                                <th>판매가
                                    <button type="button" class="sort" :value="sortData.price"
                                        :class="[{up : sortData.price=== 'price_asc'}, {down : sortData.price === 'price_desc'}]"
                                        @click="sortToggle(sortData.price)"></button>
                                </th>
                                <th>판매상태</th>
                                <th>상품상태</th>
                                <th>전시상태</th>
                                <th>재고
                                    <button type="button" class="sort" :value="sortData.stock"
                                        :class="[{up : sortData.stock=== 'stock_asc'}, {down : sortData.stock === 'stock_desc'}]"
                                        @click="sortToggle(sortData.stock)"></button>
                                </th>
                                <th>등록일자
                                    <button type="button" class="sort" :value="sortData.regdate"
                                        :class="[{up : sortData.regdate=== 'regdate_asc'}, {down : sortData.regdate === 'regdate_desc'}]"
                                        @click="sortToggle(sortData.regdate)"></button>
                                </th>
                            </tr>
                        </thead>
                        <tbody v-if="goodsList.length > 0">
                            <tr v-for="(item, index) in goodsList" :key="item.goodsno+'_'+item.optioncode">
                                <td v-if="!searchData.isread">
                                    <input type="checkbox" v-if="!searchData.issingle" :id="item.goodsno+'_'+item.optioncode" v-model="checkedList" :value="item.goodsno+'_'+item.optioncode" @change="checkGoodsList"/>
                                    <input type="radio" v-if="searchData.issingle" class="circle" name="checked" @change="setCheckedList(item.goodsno+'_'+item.optioncode)"/>
                                </td>
                                <td>{{ index+1 }}</td>
                                <td>{{ $util.isNull(item.dealername)? '-' : item.dealername }}</td>
                                <td>{{ $util.isNull(item.brandname)? '-' : item.brandname }}</td>
                                <td>{{ item.goodscode }}</td>
                                <td v-if="searchData.isshowoption==='T'">{{ item.optioncode }}</td>
                                <td>
                                    <div class="img-thumb size60" :class="{'no-image': $util.isNull(item.fullpath)}">
                                        <img :src="item.fullpath" v-if="!$util.isNull(item.fullpath)">
                                    </div>
                                </td>
                                <td class="left no-left">
                                    <span class="small-txt">{{ item.fullcategoryname }}</span>
                                    <p class="mg0">{{ item.goodsname }}</p>
                                </td>
                                <td v-if="searchData.isshowoption==='T'" style="white-space: pre-wrap">{{ item.optionconts }}</td>
                                <td v-if="searchData.isshowkeyword==='T'">{{ item.frstkeyword }}</td>
                                <td>{{ $util.maskComma(item.price) }}</td>
                                <td>{{ item.goodsselltypename }}</td>
                                <td>{{ item.goodsdivtypename }}</td>
                                <td>{{ item.isdisplayname }}</td>
                                <td>{{ $util.maskComma(item.stockcnt) }}</td>
                                <td>{{ item.regdate }}</td>
                            </tr>
                        </tbody>
                        <tbody v-else>
                            <tr><td :colspan="colcnt">조회 결과가 존재하지 않습니다.</td></tr>
                        </tbody>
                    </table>
                </div>
                <div class="btn-group" v-if="!searchData.isread">
                    <button type="button" class="btn big blue" @click="sendAllList" v-if="!searchData.issingle">전체 적용</button>
                    <button type="button" class="btn big blue" @click="sendSelectedList">선택 대상 적용</button>
                    <button type="button" class="btn big darkgray" @click="$modal.hide('commonModal');">닫기</button>
                </div>
                <div class="btn-group" v-else>
                    <button type="button" class="btn big darkgray" @click="$modal.hide('commonModal');">닫기</button>
                </div>
            </div>
        </div>
    </div>
    <!-- /상품추가 팝업 -->
</template>

<script>
import CommonDatePicker from '@views.admin/common/CommonDatePicker.vue';
import store from '@js/store';

export default {
    name: 'CommonAddGoodsPopup',
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
            isPartner: this.$util.isAuthorized(store.getters['CONSTANTS'].PARTNER_USER),
            searchData: {
                skeyArr: [
                    {key: "", name: "전체"},
                    {key: "goodsname", name: "상품명"},
                    {key: "goodscode", name: "상품코드"},
                    {key: "dealername", name: "파트너사명"},
                    {key: "brandname", name: "브랜드명"}
                ],
                psort: 'regdate_desc',  //정렬조건
                skey: '',               //검색구분
                sword: '',              //검색어
                startDate: '',          //등록시작일자 
                endDate: '',            //등록종료일자
                period: 'months_3',     //기간
                depth1cateidx: '',      //대분류일련번호
                depth2cateidx: '',      //중분류일련번호
                depth3cateidx: '',      //소분류일련번호
                depth4cateidx: '',      //세분류일련번호
                isalltype: 'T',         //상품판매유형전체여부
                goodsdivtypeArr: [],    //상품판매유형Array
                istempsave:'F',         //임시저장여부
                isdisplay: '',          //전시여부
                isdeal: 'F',            //딜상품여부
                goodsapprtypeArr: [store.getters['ADMIN'].GOODS_STATUS_APPROVAL],  //상품승인상태(default:승인완료)
                isallselltype: 'F',     //상품판매상태전체여부
                goodsselltypeArr: [],   //상품판매상태배열
                isshowkeyword: 'F',     //키워드컬럼 노출여부
                isshowoption: 'F',      //옵션단위 노출여부
                dealerno: '',           //입점업체번호
                isread: false           //조회용여부
            },
            sortData: {
                code : 'code_asc',
                goodsname : 'goodsname_asc',
                optioncode : 'optioncode_asc',
                optionconts : 'optionconts_asc',
                keyword : 'keyword_asc',
                price : 'price_asc',
                selltype : 'selltype_asc',
                stock : 'stock_asc',
                regdate : 'regdate_desc'
            },
            commonCode: {
                goodsdivtype: [],   // 상품판매유형
                goodsselltype: []   // 상품판매상태
            },
            categoryObj: {
                depth1list: [],
                depth2list: [],
                depth3list: [],
                depth4list: [],
            },
            isallchk: false,
            checkedList: [],
            goodsList: [],
            colcnt: 12
        }
    },
    methods : {
        onInit: function() {
            this.getCommonCodeList();
            this.getCategoryCodeList(1, 0);
        },
        // 검색조건 초기화
        initSearchData: function (isreload) {
            isreload = this.$util.isNull(isreload)? false : isreload;

            this.searchData.skey = '';
            this.searchData.sword = '';
            this.searchData.startDate = this.$util.getAddMonth(this.$util.getDate(), -3, '-');
            this.searchData.endDate = this.$util.getDate('-');
            this.searchData.period = 'months_3'
            this.searchData.isalltype = 'T';
            this.searchData.isdisplay = '';
            this.searchData.depth1cateidx =  '';
            this.searchData.depth2cateidx = '';
            this.searchData.depth3cateidx = '';
            this.searchData.depth4cateidx = '';
            this.categoryObj.depth2list = [];
            this.categoryObj.depth3list = [];
            this.categoryObj.depth4list = [];
            this.checkAllType();
            
            if (this.isPartner) {
                this.searchData.dealerno = this.$util.getUser(store.getters['CONSTANTS'].PARTNER_USER).no;
            }
                    
            // 팝업호출시 넘어온 조회조건 세팅
            let paramsKey = ['isdisplay', 'goodsselltypeArr', 'isdeal', 'istempsave', 'issingle', 'isshowkeyword', 'isshowoption', 'dealerno', 'isread'];
            if (!this.$util.isNull(this.params)) {
                for (const [key, value] of Object.entries(this.params)) {
                    if (paramsKey.indexOf(key) > -1) {
                        this.searchData[key] = value;
                    }
                }
            }
            if (this.$util.isNull(this.params) || this.$util.isNull(this.params.goodsselltypeArr)) {
                this.searchData.isallselltype='T';
                this.checkAllSellType();
            }

            if (typeof(isreload)==='boolean' && isreload) {
                // 옵션단위 노출여부 true인 경우 조회조건에 추가
                if (this.searchData.isshowoption==='T') {
                    let item1 = {key: "optioncode", name: "단품코드"};
                    this.searchData.skeyArr.push(item1);
                    let item2 = {key: "optionconts", name: "옵션명"};
                    this.searchData.skeyArr.push(item2);
                    this.colcnt = this.colcnt + 2;
                }
                // 키워드컬럼 노출여부 true인 경우 조회조건에 추가
                if (this.searchData.isshowkeyword==='T') {
                    let item = {key: "keyword", name: "대표키워드"};
                    this.searchData.skeyArr.push(item);
                    this.colcnt = this.colcnt + 1;
                }
                // 조회용 경우 체크박스 컬럼 제외
                if (this.searchData.isread) {
                    this.colcnt = this.colcnt - 1;
                }
                // 상품목록 조회
                // this.searchGoodsList();	// 최초 로딩 시 조회되지 않도록 김태선님이 요청(James, 2022-09-28)
            }
        },
        // 공통코드 목록 조회
        getCommonCodeList: function() {
            let cmclassArr = ['GOODSDIVTYPE', 'GOODSSELLTYPE'];
            this.$http.post('/common/code/map/list', {cmclass : cmclassArr, isloading: false})
                .then(result =>{
                    let data = result.data;
                    for (const [key] of Object.entries(data)) {
                        this.commonCode[key] = data[key];
                    }

                    // 검색조건 초기화
                    this.initSearchData(true);
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 상품목록 조회
        searchGoodsList: function() {
            this.$http.post('/admin/common/goods/list', this.searchData)
                .then(result => {
                    this.$util.debug(result);
                    this.goodsList = result.data.list;
                    this.isallchk = false;
                    this.checkedList = [];
                })
                .catch(error => {
                    this.$util.debug(error);
                });
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
        
            this.searchGoodsList();
        },
        // 상품구분상태 전체체크
        checkAllType: function() {
            let isAllCheck = this.searchData.isalltype;
            this.searchData.goodsdivtypeArr = [];
            if (isAllCheck == 'T') {
                for(let type of this.commonCode.goodsdivtype){
                    this.searchData.goodsdivtypeArr.push(type.cmcode);
                }
            }
        },
        // 상품판매상태 전체체크
        checkAllSellType: function() {
            let isAllCheck = this.searchData.isallselltype;
            this.searchData.goodsselltypeArr = [];
            if (isAllCheck == 'T') {
                for(let type of this.commonCode.goodsselltype){
                    this.searchData.goodsselltypeArr.push(type.cmcode);
                }
            }
        },
        // 상품목록 전체체크
        checkAllGoodsList: function(value) {
            this.checkedList = [];
            if (value) {
                this.goodsList.forEach(item => {
                    this.checkedList.push(item.goodsno+'_'+item.optioncode);
                });
            }
        },
        // 체크박스 상품목록 개별체크
        checkGoodsList: function() {
            if (this.goodsList.length > this.checkedList.length){
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
            if(this.goodsList.length == 0) {
                alert("적용할 상품이 존재하지 않습니다.");
                return;
            }
            if(confirm("전체적용 하시겠습니까?")) {
                // 팝업닫기 - 파라미터 전달
                this.closePopup(this.goodsList);
            }
        },
        // 선택적용
        sendSelectedList: function() {
            if(this.checkedList.length == 0) {
                alert("적용할 상품을 선택해 주세요.");
                return;
            }
            if(confirm("선택한 상품을 적용하시겠습니까?")) {
                let targetList = [];
                this.checkedList.forEach(itemkey => {
                    this.goodsList.forEach(item => {
                        if (itemkey == item.goodsno+'_'+item.optioncode) {
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
        // 상품구분상태
        'searchData.goodsdivtypeArr': function(value) {
            if (value.length < this.commonCode.goodsdivtype.length) {
                this.searchData.isalltype = 'F';
            } else {
                this.searchData.isalltype = 'T';
            }
        },
        // 상품판매상태
        'searchData.goodsselltypeArr': function(value) {
            if (value.length < this.commonCode.goodsselltype.length) {
                this.searchData.isallselltype = 'F';
            } else {
                this.searchData.isallselltype = 'T';
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
