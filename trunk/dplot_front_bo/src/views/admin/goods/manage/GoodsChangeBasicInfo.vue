<template>    
    <!-- 기본정보 일괄변경 팝업 -->
    <div id="modal-wrap" class="modal" style="display: block;">
        <div class="modal-content" style="width: 1400px;">
            <div class="pop-header">
                <h2>기본정보 일괄변경</h2>
                <button type="button" class="pop-close" @click="$emit('closePopup');"></button>
            </div>
            <div class="pop-body">
                <div class="blue-box mg0">총 {{ goodsnoList.length }}개 상품의 기본정보를 일괄 변경합니다.</div>
                <div class="clearfix mt10">
                    <div class="bar-title fl">기본정보
                        <span class="txt-orange ml10"><i class="icon-alert"></i>수정을 원하는 항목을 체크하신 후 일괄변경 하시기 바랍니다.</span>
                    </div>
                </div>
                <div class="boxing mt10">
                    <div class="form-area" style="display: block;">
                        <dl>
                            <dt><input type="checkbox" id="ischkcategory" v-model="chkObject.category.ischecked" true-value="T" false-value="F"><label for="ischkcategory">카테고리</label></dt>
                            <dd>
                                <span>카테고리 명 검색</span>
                                <input type="search" style="width: 440px;" placeholder="예) 냉장고" :maxlength="100" v-model="info.categoryname" @keyup.enter="onCategorynameShow">
                                <button type="button" class="btn-search" @click="onCategorynameShow">검색</button>
                                <!-- 카테고리 명 검색결과 리스트 -->
                                <div class="result-component" style="display: block; margin-left: 100px;" v-show="showInfo.iscateshow" v-click-outside="closeCatenameLayer">
                                    <div class="list">
                                        <ul v-if="info.categorynameList.length > 0">
                                            <li v-for="item in info.categorynameList" :key="item.fullcategoryidx">
                                                <a href="javascript:void(0);" @click="setCategoryInfo(item)">{{ item.fullcategoryname}}</a>
                                            </li>
                                        </ul>
                                        <ul v-else>
                                            <li><a>조회 결과가 존재하지 않습니다.</a></li>
                                        </ul>
                                    </div>
                                </div>
                                <!-- // 카테고리 명 검색결과 리스트 -->
                            </dd>
                            <dd class="dd-right" style="width: 180px;">
                                <button type="button" class="btn blue-line" @click="addCategory">추가</button>
                                <button type="button" class="btn blue-line" @click="onCateTemplateShow">카테고리템플릿</button>
                                <!-- 카테고리템플릿 검색결과 리스트 -->
                                <div class="result-component right round" style="display: block;" v-show="showInfo.istempshow" v-click-outside="closeCatetempLayer">
                                    <div class="list checklist">
                                        <ul v-if="info.categoryTemplateList.length > 0">
                                            <li v-for="(item, index) in info.categoryTemplateList" :key="item.cateidx">
                                                <input type="checkbox" :id="item.cateidx" v-model="item.ischecked"><label :for="item.cateidx">{{ item.fullcategoryname }}</label>
                                                <button type="button" class="del" @click="deleteCateTemplate(item, index)"></button>
                                            </li>
                                        </ul>
                                        <ul v-else>
                                            <li><input type="checkbox"><label>조회 결과가 존재하지 않습니다.</label></li>
                                        </ul>
                                    </div>
                                    <div class="btn-group" v-if="info.categoryTemplateList.length > 0">
                                        <button type="button" class="btn blue-line" @click="setGoodsCategory">적용</button>
                                        <button type="button" class="btn gray" @click="initCategoryTemplate">초기화</button>
                                    </div>
                                </div>
                                <!-- // 카테고리템플릿 검색결과 리스트 -->
                            </dd>
                        </dl>
                        <dl>
                            <dt></dt>
                            <dd>
                                <table cellpadding="0" cellspacing="0" class="category-depth">
                                    <colgroup>
                                        <col width="25%">
                                        <col width="25%">
                                        <col width="25%">
                                        <col width="25%">
                                    </colgroup>
                                    <thead>
                                        <tr>
                                            <th>대분류</th>
                                            <th>중분류</th>
                                            <th>소분류</th>
                                            <th>세분류</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td>
                                                <div class="list">
                                                    <ul>
                                                        <li v-for="item in info.categoryList.depth1List" 
                                                            :key="item.idx" @click="setCategoryCodeInfo(item)"
                                                            :class="{ active: info.depth1Category.idx == item.idx }">
                                                            <a href="javascript:void(0);">{{ item.value }}</a>
                                                        </li>
                                                    </ul>
                                                </div>
                                            </td>
                                            <td>
                                                <div class="list">
                                                    <ul>
                                                        <li v-for="item in info.categoryList.depth2List"
                                                            :key="item.idx" @click="setCategoryCodeInfo(item)"
                                                            :class="{ active: info.depth2Category.idx == item.idx }">
                                                            <a href="javascript:void(0);">{{ item.value }}</a>
                                                        </li>
                                                    </ul>
                                                </div>
                                            </td>
                                            <td>
                                                <div class="list">
                                                    <ul>
                                                        <li v-for="item in info.categoryList.depth3List"
                                                            :key="item.idx" @click="setCategoryCodeInfo(item)"
                                                            :class="{ active: info.depth3Category.idx == item.idx }">
                                                            <a href="javascript:void(0);">{{ item.value }}</a>
                                                        </li>
                                                    </ul>
                                                </div>
                                            </td>
                                            <td>
                                                <div class="list">
                                                    <ul>
                                                        <li v-for="item in info.categoryList.depth4List"
                                                            :key="item.idx" @click="setCategoryCodeInfo(item)"
                                                            :class="{ active: info.depth4Category.idx == item.idx }">
                                                            <a href="javascript:void(0);">{{ item.value }}</a>
                                                        </li>
                                                    </ul>
                                                </div>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                                <div class="category-selected">
                                    <ul>
                                        <li v-for="(item, index) in info.goodsCategoryList" :key="index" @click="selectCategory(item)">
                                            <input type="radio" class="circle" name="cate-sel" :id="'isrepre_'+index" :checked="item.isrepre=='T'" @change="setIsrepreCategory(item)">
                                            <label :for="'isrepre_'+index">대표</label>
                                            <span class="box" :class="{'blue': item.isselected=='T'}">선택카테고리</span>
                                            <span class="ml10" :class="{'txt-blue': item.isselected=='T'}">{{ item.fullcategoryname }}</span>
                                            <div class="left-bar">
                                                <input type="checkbox" class="star" v-model="item.isstar" true-value="T" false-value="F" @click="saveCategoryTemp(item)">
                                            </div>
                                            <div class="left-bar"><button type="button" class="del" @click="removeGoodsCategory(item)"></button></div>
                                            <span class="txt-orange left-bar small-txt" v-if="index==0"><i class="icon-alert"></i>별 아이콘 체크 시 [카테고리템플릿]에 저장됩니다.</span>
                                        </li>
                                    </ul>
                                </div>
                            </dd>
                        </dl>
                        <dl>
                            <dt><input type="checkbox" id="ischkgoodsname" v-model="chkObject.goodsname.ischecked" true-value="T" false-value="F"><label for="ischkgoodsname">상품명</label></dt>
                            <dd><input type="text" value="" style="width: 100%;" placeholder="상품명을 입력하세요!" v-model="info.goodsname" :maxlength="250"></dd>
                        </dl>
                        <dl>
                            <dt><input type="checkbox" id="ischksummary" v-model="chkObject.summary.ischecked" true-value="T" false-value="F"><label for="ischksummary">상품요약설명</label></dt>
                            <dd><textarea style="width: 100%;" v-model="info.summary" :maxlength="250"></textarea></dd>
                        </dl>
                        <dl>
                            <dt><input type="checkbox" id="ischkkeyword" v-model="chkObject.keyword.ischecked" true-value="T" false-value="F"><label for="ischkkeyword">검색키워드</label></dt>
                            <dd>
                                <input type="text" value="" style="width: 100%;" placeholder="검색어는 ,(콤마)로 구분됩니다." v-model="info.keyword" :maxlength="100">
                            </dd>
                            <dd class="dd-right" style="width: 105px;"><span>({{ info.keyword.length }}/100)</span></dd>
                        </dl>
                        <dl>
                            <dt><input type="checkbox" id="ischkbrand" v-model="chkObject.brand.ischecked" true-value="T" false-value="F"><label for="ischkbrand">브랜드</label></dt>
                            <dd>
                                <input type="search" style="width: 220px;" placeholder="브랜드명을 검색하세요." :maxlength="200"
                                    v-model="info.brandname" :disabled="info.isnobrand=='T'"
                                    @keyup.enter="openSearchBrandPopup"
                                    @change="info.brandidx = ''" />
                                <button type="button" class="btn-search" @click="openSearchBrandPopup">검색</button>
                                <input type="checkbox" id="no-brand" class="ml10" v-model="info.isnobrand" @click="initBrandInfo" true-value="T" false-value="F">
                                <label for="no-brand">브랜드 없음</label>
                                <span class="left-bar txt-red" v-if="info.isnobrand=='T'">설정안함</span>
                            </dd>
                        </dl>
                        <dl>
                            <dt><input type="checkbox" id="ischkmdifidx" v-model="chkObject.mdifidx.ischecked" true-value="T" false-value="F"><label for="ischkmdifidx">담당MD</label></dt>
                            <dd>
                                <input type="search" style="width: 220px;" placeholder="담당MD를 선택하세요."
                                    :value="$util.isNull(info.mdsabun)? '' : '['+info.mdsabun+'] '+info.empname" readonly disabled>
                                <button type="button" class="btn-search" @click="onChargemdShow">검색</button>
                                <!-- 담당MD 조회 컴포넌트 -->
                                <div class="result-component ml0 no-round" style="display: block; width: 700px;" v-show="showInfo.ismdshow" v-click-outside="closeChargemdLayer">
                                    <div class="list-header" style="width: 100%;">
                                        <table cellspacing="0" cellpadding="0" class="data-tb align-c mg0" style="width: 100%;">
                                            <colgroup>
                                                <col width="6%">
                                                <col width="6%">
                                                <col width="15%">
                                                <col width="15%">
                                                <col width="15%">
                                                <col width="15%">
                                                <col width="">
                                            </colgroup>
                                            <thead>
                                                <tr>
                                                <tr>
                                                    <th>선택</th>
                                                    <th>No</th>
                                                    <th>MD사번
                                                        <button type="button" :value="sortData.chargemd.mdsabun" class="sort" 
                                                            :class="[{up : sortData.chargemd.mdsabun === 'mdsabun_asc'}, {down : sortData.chargemd.mdsabun === 'mdsabun_desc'}]"
                                                            @click="sortToggle('chargemd', sortData.chargemd.mdsabun)"></button>
                                                    </th>
                                                    <th>사원이름
                                                        <button type="button" :value="sortData.chargemd.empname" class="sort"
                                                            :class="[{up : sortData.chargemd.empname === 'empname_asc'}, {down : sortData.chargemd.empname === 'empname_desc'}]"
                                                            @click="sortToggle('chargemd', sortData.chargemd.empname)"></button>
                                                    </th>
                                                    <th>코드
                                                        <button type="button" :value="sortData.chargemd.mdcode" class="sort" 
                                                            :class="[{up : sortData.chargemd.mdcode === 'mdcode_asc'}, {down : sortData.chargemd.mdcode === 'mdcode_desc'}]"
                                                            @click="sortToggle('chargemd', sortData.chargemd.mdcode)"></button>
                                                    </th>
                                                    <th>MD명
                                                        <button type="button" :value="sortData.chargemd.mdname" class="sort"
                                                            :class="[{up : sortData.chargemd.mdname === 'mdname_asc'}, {down : sortData.chargemd.mdname === 'mdname_desc'}]"
                                                            @click="sortToggle('chargemd', sortData.chargemd.mdname)"></button>
                                                    </th>
                                                    <th>이메일</th>
                                                </tr>
                                            </thead>
                                        </table>
                                    </div>
                                    <div class="list">
                                        <table cellspacing="0" cellpadding="0" class="data-tb align-c mg0" style="width: 100%;">
                                            <colgroup>
                                                <col width="6%">
                                                <col width="6%">
                                                <col width="15%">
                                                <col width="15%">
                                                <col width="15%">
                                                <col width="15%">
                                                <col width="">
                                            </colgroup>
                                            <tbody v-if="info.chargemdList.length > 0">
                                                <tr v-for="(item, index) in info.chargemdList" :key="item.mdifidx">
                                                    <td>
                                                        <input type="radio" class="circle" name="mdifidx" v-model="info.mdifidx"
                                                            :value="item.mdifidx" :id="'mdifidx' + index"
                                                            @change="setChargemdInfo(item)">
                                                    </td>
                                                    <td>{{ index+1 }}</td>
                                                    <td>{{ item.mdsabun }}</td>
                                                    <td>{{ item.empname }}</td>
                                                    <td>{{ item.mdcode }}</td>
                                                    <td>{{ item.mdname }}</td>
                                                    <td>{{ item.mdemail }}</td>
                                                </tr>
                                            </tbody>
                                            <tbody v-else>
                                                <tr><td colspan="7">조회 결과가 존재하지 않습니다.</td></tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                                <!-- // 담당MD 조회 컴포넌트 -->
                            </dd>
                        </dl>
                        <dl>
                            <dt><input type="checkbox" id="ischkmuappchtype" v-model="chkObject.muappchtype.ischecked" true-value="T" false-value="F"><label for="ischkmuappchtype">적용채널</label></dt>
                            <dd>
                                <div class="check-wrap">
                                    <input type="checkbox" id="chkAllChannel" v-model="info.isallchannel" true-value="T" false-value="F" @change="checkAllChannel">
                                    <label for="chkAllChannel">전체적용</label>
                                </div>
                                <div class="check-wrap" v-for="item in commonCode.muappchtype" :key="item.cmcode">
                                    <input type="checkbox" :id="'muappchtype_'+item.cmcode" v-model="info.muappchtypeArr" :true-value="[]" :value="item.cmcode"/>
                                    <label :for="'muappchtype_'+item.cmcode">{{ item.codename }}</label>
                                </div>
                            </dd>
                        </dl>
                    </div>
                </div>
                <div class="btn-group">
                    <button type="button" class="btn big blue" @click="openConfirmPopup">일괄변경</button>
                    <button type="button" class="btn big darkgray" @click="$emit('closePopup');">닫기</button>
                </div>
            </div>
        </div>
    </div>
    <!-- /기본정보 일괄변경 팝업 -->
</template>

<script>
import vClickOutside from 'v-click-outside';
import GoodsChangeConfirmPopup from '@views.admin/goods/popup/GoodsChangeConfirmPopup.vue';
import SearchBrandListPopup from '@views.admin/goods/popup/SearchBrandListPopup.vue';

export default {
    name: 'admin.goods.manage.goodsChangeinfo',
    props : ['checkedList', 'ckey'],
    directives: {
        clickOutside: vClickOutside.directive
    },
    mounted() {
        // 초기화
        this.onInit();
    },
    data() {
        return {
            goodsnoList: [],    // 상품번호 목록
            chkObject: {        // 체크항목 목록
                category: { key: 'category', name:'카테고리', ischecked:'F' },
                goodsname: { key: 'goodsname', name:'상품명', ischecked:'F' },
                summary: { key: 'summary', name:'상품요약설명', ischecked:'F' },
                keyword: { key: 'keyword', name:'검색키워드', ischecked:'F' },
                brand: { key: 'brand', name:'브랜드', ischecked:'F' },
                mdifidx: { key: 'mdifidx', name:'담당MD', ischecked:'F' },
                muappchtype: { key: 'muappchtype', name:'적용채널', ischecked:'F' }
            },
            commonCode: {
                muappchtype: [],    // 적용채널
            },
            showInfo: {         // 내부 레이어컴포넌트 노출정보
                ismdshow: false,        // 담당MD조회노출여부
                iscateshow: false,      // 카테고리명조회 노출여부
                istempshow: false       // 카테고리템플릿조회 노출여부
            },
            sortData: {
                chargemd: {
                    psort: 'mdsabun_asc',
                    mdsabun: 'mdsabun_asc',
                    empname: 'empname_asc',
                    mdcode: 'mdcode_asc',
                    mdname: 'mdname_asc'
                }
            },
            info: {             // 상세정보
                categoryname: '',   // 카테고리명
                categoryidx: '',    // 카테고리일련번호
                goodsname: '',      // 상품명
                summary: '',        // 상품요약설명
                keyword: '',        // 검색키워드
                brandidx: '',       // 브랜드일련번호
                brandname: '',      // 브랜드명
                isnobrand: 'F',     // 브랜드없음여부
                mdsabun: '',        // MD사번
                empname: '',        // 사원이름
                mdifidx: '',        // MD정보IDX
                isallchannel:'',    // 전체채널여부
                muappchtype: '',    // 다중적용채널
                muappchtypeArr: [], // 다중적용채널Array
                categorynameList:[],// 카테고리명 목록
                depth1Category: {category:'대분류', idx: '', text:''},  //대분류 카테고리일련번호
                depth2Category: {category:'중분류', idx: '', text:''},  //중분류 카테고리일련번호
                depth3Category: {category:'소분류', idx: '', text:''},  //소분류 카테고리일련번호
                depth4Category: {category:'세분류', idx: '', text:''},  //세분류 카테고리일련번호
                categoryList: {     // 카테고리분류 목록
                    depth1List: [],
                    depth2List: [],
                    depth3List: [],
                    depth4List: []
                },
                goodsCategoryList: [],
                categoryTemplateList:[],
                chargemdList: []
            }
        }
    },
    methods: {
        // 화면초기화
        onInit: function() {
            // 넘어온 파라미터 세팅
            this.goodsnoList = this.checkedList;
            // 공통코드 조회
            this.getCommonCodeList();
            // 대분류 카테고리 목록 조회
            let params = { depth: 0, idx: 0, name: ''};
            this.getCategoryCodeList(params);
        },
        // 일괄변경 확인팝업 오픈
        openConfirmPopup: function() {
            // 파라미터 세팅
            let checkItemList = [];
            for (const [key, value] of Object.entries(this.chkObject)) {
                if (this.chkObject[key].ischecked === 'T') {
                    checkItemList.push(value);
                }
            }
            // 선택한 항목 체크
            if (checkItemList.length == 0) {
                alert('변경할 항목을 선택해주세요.');
                return;
            }
            if (this.checkValidation()) {
                // 일괄변경 확인팝업 오픈
                this.$eventBus.$emit('modalShow', GoodsChangeConfirmPopup, { checkItemList: checkItemList},
                    (result) => {
                        if (result.isconfirm === 'T') {
                            this.changeAll();
                        }
                    }
                );
            }
        },
        // 일괄변경
        changeAll: function() {
            // 변경파라미터 세팅
            let params = {
                ckey: this.ckey,
                iscategorychange: this.chkObject.category.ischecked,
                isgoodsnamechange: this.chkObject.goodsname.ischecked,
                issummarychange: this.chkObject.summary.ischecked,
                iskeywordchange: this.chkObject.keyword.ischecked,
                isbrandchange: this.chkObject.brand.ischecked,
                ismdifidxchange: this.chkObject.mdifidx.ischecked,
                ismuappchtypechange: this.chkObject.muappchtype.ischecked,
                goodsnolist: this.goodsnoList
            }
            params = Object.assign({}, params, this.info);
            this.$http.post('/admin/goods/manage/update', params)
                .then(result => {
                    if (result.statusCode === 200) {
                        alert('일괄변경이 완료되었습니다.');
                        this.$emit('closePopup', 'T');
                    }
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 입력 validation 체크
        checkValidation: function() {
            let checkResult = true;

            // 카테고리
            if (this.chkObject.category.ischecked === 'T') {
                // 필수체크
                if (this.info.goodsCategoryList.length == 0) {
                    alert('카테고리를 추가해주세요.');
                    checkResult = false;
                } else {
                    // 대표카테고리 선택여부 확인
                    let existRepreCnt = this.info.goodsCategoryList.filter(item => {
                        return item.isrepre == true;
                    }).lengt;
                    if (existRepreCnt == 0) {
                        alert('대표카테고리를 선택해주세요.');
                        checkResult = false;
                    }
                }
            }
            // 상품명
            if (checkResult && this.chkObject.goodsname.ischecked === 'T') {
                // 필수체크
                if (this.$util.isNull(this.info.goodsname.trim())) {
                    alert('상품명을 입력해주세요.');
                    checkResult = false;
                }
            }
            // // 상품요약
            // if (checkResult && this.chkObject.summary.ischecked === 'T') {
            //     // 필수체크
            //     if (this.$util.isNull(this.info.summary)) {
            //         alert('상품요약설명을 입력해주세요.');
            //         checkResult = false;
            //     }
            // }
            // // 검색키워드
            // if (checkResult && this.chkObject.keyword.ischecked === 'T') {
            //     // 필수체크
            //     if (this.$util.isNull(this.info.keyword)) {
            //         alert('검색키워드를 입력해주세요.');
            //         checkResult = false;
            //     }
            // }
            // 브랜드
            if (checkResult && this.chkObject.brand.ischecked === 'T') {
                // 필수체크
                if (this.info.isnobrand == 'F' &&  this.$util.isNull(this.info.brandidx)) {
                    alert('브랜드를 입력해주세요.');
                    checkResult = false;
                }
            }
            // 담당MD
            if (checkResult && this.chkObject.mdifidx.ischecked === 'T') {
                // 필수체크
                if (this.$util.isNull(this.info.mdifidx)) {
                    alert('담당MD를 선택해주세요.');
                    checkResult = false;
                }
            }
            // // 적용채널
            // if (checkResult && this.chkObject.muappchtype.ischecked === 'T') {
            //     // 필수체크
            //     if (this.$util.isNull(this.info.muappchtype)) {
            //         alert('적용채널을 선택해주세요.');
            //         checkResult = false;
            //     }
            // }

            return checkResult;
        },
        // 공통코드 세팅
        getCommonCodeList: function() {
            let cmclassArr = ['MUAPPCHTYPE'];
            this.$http.post('/common/code/map/list', {cmclass : cmclassArr, isloading: false})
                .then(result =>{
                    let data = result.data;
                    for (const [key] of Object.entries(data)) {
                        this.commonCode[key] = data[key];
                    }
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 카테고리분류 목록 조회
        getCategoryCodeList: function(obj) {
            // 세분류가 아닌경우만 하위 카테고리 조회
            if (obj.depth < 4) {
                // 하위 카테고리 목록 초기화
                for (let i=obj.depth; i<=4; i++) {
                    let listName = 'depth'+ (i+1) +'List';
                    this.info.categoryList[listName] = [];
                }
                // 하위카테고리 목록 조회
                obj.isloading = false;
                this.$http.post('/admin/goods/regist/cate/list', obj)
                    .then(result => {
                        this.$util.debug(result);
                        let categoryList = result.data.list;

                        // 카테고리 목록 세팅
                        let targetDepth = obj.depth +1;
                        let targetListName = 'depth'+ targetDepth +'List';
                        this.info.categoryList[targetListName] = categoryList;
                    })
                    .catch(error => {
                        this.$util.debug(error);
                    });
            } else {
                this.info.depth4CategoryIdx = obj.idx;
            }
        },
        // 카테고리명 조회 show/hide
        onCategorynameShow: function() {
            if (!this.showInfo.iscateshow) {
                this.getCategorynameList();
            } else {
                this.info.categorynameList = [];
                this.showInfo.iscateshow = false;
            }
        },
        // 카테고리명 목록 조회
        getCategorynameList: function() {
            let param = { categoryname: this.info.categoryname, isloading: false };
            this.$http.post('/admin/goods/regist/catename/list', param)
                .then(result => {
                    this.$util.debug(result);
                    this.info.categorynameList = result.data.list;
                    this.showInfo.iscateshow = true;
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 카테고리 정보 셋팅
        setCategoryInfo: function(obj) {
            this.closeCatenameLayer();
            this.info.categoryname = obj.fullcategoryname;
            this.info.categoryidx = obj.cateidx;
            this.info.depth1Category.idx = obj.depth1idx;
            this.info.depth1Category.value = obj.depth1name;
            this.info.depth2Category.idx = obj.depth2idx;
            this.info.depth2Category.value = obj.depth2name;
            this.info.depth3Category.idx = obj.depth3idx;
            this.info.depth3Category.value = obj.depth3name;
            this.info.depth4Category.idx = obj.depth4idx;
            this.info.depth4Category.value = obj.depth4name;
        },
        // 카테고리명조회 레이어 닫기
        closeCatenameLayer: function() {
            this.showInfo.iscateshow = false;
            this.info.categorynameList = [];
        },
        // 상품별 카테고리 추가
        addCategory: function() {
            // 카테고리 선택 필수 체크
            if (this.$util.isNull(this.info.depth1Category.idx)) {
                alert('추가할 카테고리를 선택해주세요.');
                return;
            }
            
            let cateidx = '';
            let fullCategoryName = '';
            for (let i=0; i<4; i++) {
                let listName = 'depth' + (i+1) + 'List';
                let categoryName = 'depth' + (i+1) + 'Category';
                let categoryObj = this.info[categoryName];
                
                // 하위 카테고리까지 선택여부 체크
                if (this.info.categoryList[listName].length > 0 && this.$util.isNull(categoryObj.idx)) {
                    alert(categoryObj.category + ' 카테고리를 선택해주세요.');
                    return;
                }
                // 카테고리명 세팅
                if (i == 0) {
                    fullCategoryName = fullCategoryName.concat(categoryObj.value);
                } else {
                    if (this.info.categoryList[listName].length > 0 && !this.$util.isNull(categoryObj.idx)) {
                        fullCategoryName = fullCategoryName.concat(' > ', categoryObj.value);
                    }
                }
                // 최종선택 카테고리 세팅
                if (this.info.categoryList[listName].length > 0 && !this.$util.isNull(categoryObj.idx)) {
                    cateidx = categoryObj.idx;
                }
            }

            let isrepreExist = false;
            for (let i=0; i<this.info.goodsCategoryList.length; i++) {
                let categoryObj = this.info.goodsCategoryList[i];
                // 대표여부 확인
                if (categoryObj.isrepre == 'T') {
                    isrepreExist = true;
                }
                // 카테고리 중복체크
                if (cateidx == categoryObj.cateidx) {
                    alert('이미 추가된 카테고리 입니다.');
                    return;
                }
                // 선택카테고리 여부 초기화
                categoryObj.isselected = false;
            }

            // 카테고리 목록에 추가
            let params = {
                isselected: 'T',
                isrepre: isrepreExist? 'F' : 'T',
                fullcategoryname: fullCategoryName,
                cateidx: cateidx,
                isstar: 'F'
            }
            this.info.goodsCategoryList.push(params);
        },
        // 카테고리템플릿 조회 show/hide
        onCateTemplateShow: function() {
            if (!this.showInfo.istempshow) {
                this.getCateTemplateList();
            } else {
                this.info.categoryTemplateList = [];
                this.showInfo.istempshow = false;
            }

        },
        // 카테고리템플릿목록 조회
        getCateTemplateList: function() {
            this.$http.post('/admin/goods/regist/catetemp/list')
                .then(result => {
                    this.$util.debug(result);
                    this.info.categoryTemplateList = result.data.list;
                    this.showInfo.istempshow = true;
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 카테고리템플릿 삭제
        deleteCateTemplate: function(obj) {
            obj.isstar = 'T';
            this.saveCategoryTemp(obj);
        },
        // 카테고리템플릿 적용
        setGoodsCategory: function() {
            // 체크여부 확인
            let checkCnt = this.info.categoryTemplateList.filter(item => {
                return item.ischecked == true;
            }).length;
            if (checkCnt == 0) {
                alert('적용할 카테고리를 선택해주세요.');
                return;
            }

            // 상품별 카테고리에 적용 (이미 추가되있는거 제외)
            this.info.categoryTemplateList.forEach(catetemp => {
                let params = {};
                let goodsCateCnt = this.info.goodsCategoryList.length;
                if (catetemp.ischecked ) {
                    let existCnt = this.info.goodsCategoryList.filter(goodscate => {
                        return goodscate.cateidx == catetemp.cateidx;
                    }).length;
                    if (existCnt == 0) {
                        params = {
                            isstar: 'T',
                            isselected: goodsCateCnt==0? 'T' : 'F',
                            isrepre: goodsCateCnt==0? 'T' : 'F',
                            fullcategoryname: catetemp.fullcategoryname,
                            cateidx: catetemp.cateidx
                        }
                        this.info.goodsCategoryList.push(params);
                    }
                }
            });

            // 카테고리템플릿 레이어 닫기
            this.closeCatetempLayer();
        },
        // 카테고리템플릿 초기화
        initCategoryTemplate: function() {
            this.info.categoryTemplateList.forEach(item => {
                item.ischecked = false;
            });
        },
        // 카테고리 템플릿 저장
        saveCategoryTemp: function(obj) {
            obj.isloading = false;
            this.$http.post('/admin/goods/regist/catetemp/save', obj)
                .then(result => {
                    this.$util.debug(result);
                    if (result.statusCode == 200) {
                        if (obj.isstar == 'T') {
                            let findIndex = this.info.categoryTemplateList.indexOf(obj)
                            this.info.categoryTemplateList.splice(findIndex, 1);
                        }
                    } else {
                        obj.isstar = 'T';
                        alert(result.message);
                    }
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 카테고리템플릿 레이어 닫기
        closeCatetempLayer: function() {
            this.showInfo.istempshow = false;
            this.info.categoryTemplateList = [];
        },
        // 카테고리분류 정보 셋팅
        setCategoryCodeInfo: function(obj) {
            let targetName = 'depth' + obj.depth + 'Category';
            this.info[targetName].idx = obj.idx;
            this.info[targetName].value = obj.value;
        },
        // 선택카테고리 표시
        selectCategory: function(obj) {
            this.info.goodsCategoryList.forEach(item => {
                item.isselected = 'F';
            })
            obj.isselected = 'T';
        },
        // 대표카테고리 선택
        setIsrepreCategory: function(obj) {
            this.info.goodsCategoryList.forEach(item => {
                item.isrepre = 'F';
            });
            obj.isrepre = 'T';
        },
        // 상품별 카테고리 삭제
        removeGoodsCategory: function(obj) {
            let findIndex = this.info.goodsCategoryList.indexOf(obj);
            this.info.goodsCategoryList.splice(findIndex, 1);
            let isrepreCnt = this.info.goodsCategoryList.filter(item => {
                return item.isrepre == 'T';
            }).length;
            if (isrepreCnt == 0 && this.info.goodsCategoryList.length > 0) {
                this.info.goodsCategoryList[0].isrepre = 'T';
            }
        },
        // 브랜드조회 팝업
        openSearchBrandPopup: function() {
            this.info.isnobrand = 'F';
            let param = { brandname: this.info.brandname };
            this.$eventBus.$emit('modalShow', SearchBrandListPopup, param,
                (result) => {
                    this.info.brandidx = result.idx;
                    this.info.brandname = result.brandname;
                }
            );
        },
        // 브랜드정보 초기화
        initBrandInfo: function() {
            this.info.brandidx = '';
            this.info.brandname = '';
        },
        // 담당MD 조회 show/hide
        onChargemdShow: function() {
            if (!this.showInfo.ismdshow) {
                this.getChargemdList();
            } else {
                this.info.chargemdList = [];
                this.showInfo.ismdshow = false;
                this.sortData.chargemd = {
                    psort: 'mdsabun_asc',
                    mdsabun: 'mdsabun_asc',
                    empname: 'empname_asc',
                    mdcode: 'mdcode_asc',
                    mdname: 'mdname_asc'
                };
            }
        },
        // 담당MD 목록 조회
        getChargemdList: function() {
            let param = { psort: this.sortData.chargemd.psort };
            this.$http.post('/admin/goods/regist/chargemd/list', param)
                .then(result => {
                    this.$util.debug(result);
                    this.info.chargemdList = result.data.list;
                    this.showInfo.ismdshow = true;
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 담당MD 세팅
        setChargemdInfo: function(obj) {
            this.info.mdifidx = obj.mdifidx;
            this.info.mdsabun = obj.mdsabun;
            this.info.empname = obj.empname;
            setTimeout(function () {
                this.info.chargemdList = [];
                this.showInfo.ismdshow = false;
            }.bind(this), 50);
        },
        // 적용채널 전체적용 체크
        checkAllChannel: function() {
            let isAllCheck = this.info.isallchannel;
            this.info.muappchtypeArr = [];
            if (isAllCheck == 'T') {
                for(let type of this.commonCode.muappchtype){
                    this.info.muappchtypeArr.push(type.cmcode);
                }
            }
        },
        // 담당MD 조회 레이어 닫기
        closeChargemdLayer: function() {
            this.showInfo.ismdshow = false;
            this.info.chargemdList = [];
        },
        // 정렬
        sortToggle: function(target, key){
            let arr = key.split('_');
            let sortKey = arr[0];
            let sortOrder = (arr[1] === 'asc') ? 'desc' : 'asc';
            let sortName = sortKey + '_' + sortOrder;
            this.sortData = this.$options.data().sortData;

            this.sortData[target][sortKey] = sortName;
            this.sortData[target].psort = sortName;

            // 담당MD 조회
            if (target == 'chargemd') {
                this.info.chargemdList.sort((a, b) => {
                    a[sortKey] = this.$util.isNull(a[sortKey]) ? '' : a[sortKey];
                    b[sortKey] = this.$util.isNull(b[sortKey]) ? '' : b[sortKey];
                    if (a[sortKey] < b[sortKey]) {
                        return sortOrder == 'asc'? -1: 1;
                    } else if (a[sortKey] > b[sortKey]) {
                        return sortOrder == 'asc'? 1: -1;
                    }
                    return 0;
                });
                // this.getChargemdList();
            }
        }
    },
    watch: {
        // 카테고리 선택
        'info.depth1Category.idx': function(value) {
            let params = {depth: 1, idx: value};
            this.getCategoryCodeList(params);
        },
        'info.depth2Category.idx': function(value) {
            let params = {depth: 2, idx: value};
            this.getCategoryCodeList(params);
        },
        'info.depth3Category.idx': function(value) {
            let params = {depth: 3, idx: value};
            this.getCategoryCodeList(params);
        },
        'info.depth4Category.idx': function(value) {
            let params = {depth: 4, idx: value};
            this.getCategoryCodeList(params);
        },
        // 적용채널
        'info.muappchtypeArr': function(value) {
            if (value.length < this.commonCode.muappchtype.length) {
                this.info.isallchannel = 'F';
            } else {
                this.info.isallchannel = 'T';
            }
            this.info.muappchtype = this.info.muappchtypeArr.join();
        }
    }
}
</script>