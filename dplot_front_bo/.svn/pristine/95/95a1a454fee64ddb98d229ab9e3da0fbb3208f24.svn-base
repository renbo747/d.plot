<template>
    <!-- 딜상품상세정보 팝업 -->
    <div id="modal-wrap" class="modal" style="display: block;">
        <div class="modal-content" style="width: 1600px;">
            <div class="pop-header">
                <h2>딜상품 상세정보</h2>
                <button type="button" class="pop-close" @click="$emit('closePopup');"></button>
            </div>
            <div class="pop-body">
                <div class="gray-box mg0 clearfix">
                    <div class="fl">[{{ basicInfo.isdealname+'상품' }}] {{ basicInfo.goodscode }} {{ basicInfo.orggoodsname }}</div>
                    <div class="fr">
                        <span>등록일 : {{ basicInfo.regdate }}</span>
                        <span class="left-bar">최종수정일 : {{ basicInfo.moddate }}</span>
                        <span class="left-bar">[{{ basicInfo.goodsselltypename }}]</span>
                    </div>
                </div>
                <div class="clearfix mt10">
                    <div class="btn-group fr" :style="basicInfo.orggoodsselltype === $store.getters['ADMIN'].GOODS_SELL_TYPE_GST006? 'visibility: hidden;' : ''">
                        <span class="txt-orange mr5" v-if="iscopy"><i class="icon-alert"></i>현재 상품은 복사한 상품입니다.</span>
                        <button type="button" class="btn big blue-line" v-if="!$util.isNull(basicInfo.dealno)" @click="goPriview">미리보기</button>
                        <button type="button" class="btn big gray" v-if="!iscopy" @click="iscopy = true">복사</button>
                        <button type="button" class="btn big blue" @click="goTemporarySave" v-if="iscopy">임시저장</button>
                        <button type="button" class="btn big blue" @click="goSave">저장</button>
                    </div>
                </div>
                <div class="tab-group">
                    <div class="tab-group mt0">
                        <scrollactive active-class="active" scrollContainerSelector=".dtl" bezier-easing-value=".5,0,.35,1"
                            :highlightFirstItem=true :offset="900" :scrollOffset="500" :duration="600" >
                            <ul>
                                <!-- 활성화탭 li에 class="active" 추가 -->
                                <li v-for="(value, name, index) in tabObject" :key="index" @click="onActive(value)"> 
                                    <a :href="value.href" class="scrollactive-item">{{ value.tabName }}</a>
                                </li>
                            </ul>
                        </scrollactive>
                    </div>
                </div>
                <div class="tab-area dtl" style="max-height: calc(90vh - 190px);">
                    <!-- 기본정보 Area -->  
                    <div class="boxing" id="dealTab1">
                        <div class="boxing-title" :class="{closed: !tabObject.dealTab1.isOpen}">
                            기본 정보
                            <i class="arrcodi" @click="onToggle(tabObject.dealTab1)"></i>
                        </div>
                        <div class="form-area" :style="{display: tabObject.dealTab1.isOpen? 'block':'none'}">
                            <dl>
                                <dt>카테고리<i class="essential"></i></dt>
                                <dd>
                                    <span>카테고리 명 검색</span>
                                    <input type="search" style="width: 440px;" placeholder="예) 냉장고" :maxlength="100" v-model="basicInfo.categoryname" @keyup.enter="onCategorynameShow">
                                    <button type="button" class="btn-search" @click="onCategorynameShow">검색</button>
                                    <!-- 카테고리 명 검색결과 리스트 -->
                                    <div class="result-component" style="display: block; margin-left: 100px;" v-show="showInfo.iscateshow" v-click-outside="closeCatenameLayer">
                                        <div class="list">
                                            <ul v-if="basicInfo.categorynameList.length > 0">
                                                <li v-for="item in basicInfo.categorynameList" :key="item.fullcategoryidx">
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
                                            <ul v-if="basicInfo.categoryTemplateList.length > 0">
                                                <li v-for="(item, index) in basicInfo.categoryTemplateList" :key="item.cateidx">
                                                    <input type="checkbox" :id="item.cateidx" v-model="item.ischecked"><label :for="item.cateidx">{{ item.fullcategoryname }}</label>
                                                    <button type="button" class="del" @click="deleteCateTemplate(item, index)"></button>
                                                </li>
                                            </ul>
                                            <ul v-else>
                                                <li><input type="checkbox"><label>조회 결과가 존재하지 않습니다.</label></li>
                                            </ul>
                                        </div>
                                        <div class="btn-group" v-if="basicInfo.categoryTemplateList.length > 0">
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
                                                            <li v-for="item in basicInfo.categoryList.depth1List" 
                                                                :key="item.idx" @click="setCategoryCodeInfo(item)"
                                                                :class="{ active: basicInfo.depth1Category.idx == item.idx }">
                                                                <a href="javascript:void(0);">{{ item.value }}</a>
                                                            </li>
                                                        </ul>
                                                    </div>
                                                </td>
                                                <td>
                                                    <div class="list">
                                                        <ul>
                                                            <li v-for="item in basicInfo.categoryList.depth2List"
                                                                :key="item.idx" @click="setCategoryCodeInfo(item)"
                                                                :class="{ active: basicInfo.depth2Category.idx == item.idx }">
                                                                <a href="javascript:void(0);">{{ item.value }}</a>
                                                            </li>
                                                        </ul>
                                                    </div>
                                                </td>
                                                <td>
                                                    <div class="list">
                                                        <ul>
                                                            <li v-for="item in basicInfo.categoryList.depth3List"
                                                                :key="item.idx" @click="setCategoryCodeInfo(item)"
                                                                :class="{ active: basicInfo.depth3Category.idx == item.idx }">
                                                                <a href="javascript:void(0);">{{ item.value }}</a>
                                                            </li>
                                                        </ul>
                                                    </div>
                                                </td>
                                                <td>
                                                    <div class="list">
                                                        <ul>
                                                            <li v-for="item in basicInfo.categoryList.depth4List"
                                                                :key="item.idx" @click="setCategoryCodeInfo(item)"
                                                                :class="{ active: basicInfo.depth4Category.idx == item.idx }">
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
                                            <li v-for="(item, index) in basicInfo.goodsCategoryList" :key="index" @click="selectCategory(item)">
                                                <span class="box" :class="{'blue': item.isselected=='T'}">선택카테고리</span>
                                                <span class="ml10" :class="{'txt-blue': item.isselected=='T'}">{{ item.fullcategoryname }}</span>
                                                <div class="left-bar" v-if="isWrite">
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
                                <dt>딜명<i class="essential"></i></dt>
                                <dd><input type="text" value="" style="width: 100%;" placeholder="딜명을 입력하세요!" v-model="basicInfo.goodsname" :maxlength="70"></dd>
                                <dd class="dd-right" style="width: 105px;"><span>({{ basicInfo.goodsname.length }}/70)</span></dd>
                            </dl>
                            <dl>
                                <dt>검색키워드</dt>
                                <dd><input type="text" value="" style="width: 100%;" placeholder="검색어는 ,(콤마)로 구분됩니다." v-model="basicInfo.keyword" :maxlength="100"></dd>
                                <dd class="dd-right" style="width: 105px;"><span>({{ basicInfo.keyword.length }}/100)</span></dd>
                            </dl>
                            <dl>
                            <dt>전시기간<i class="essential"></i></dt>
                            <dd>
                                <CommonDatePickerFromTo
                                    :fromYYYYMMDD="basicInfo.disstdate"
                                    :fromHH="basicInfo.dissthour"
                                    :fromMM="basicInfo.disstmin"
                                    :toYYYYMMDD="basicInfo.diseddate"
                                    :toHH="basicInfo.disedhour"
                                    :toMM="basicInfo.disedmin"
                                    :useFrom="true"
                                    :useTo="true"
                                    @getDate="pickerChangeEvent"
                                />
                                <select v-model="basicInfo.disperiod">
                                    <option id="periodD_day" value="">선택</option>
                                    <option id="periodD_day_1" value="day_1">1일</option>
                                    <option id="periodD_day_3" value="day_3">3일</option>
                                    <option id="periodD_day_5" value="day_5">5일</option>
                                    <option id="periodD_day_7" value="day_7">7일</option>
                                    <option id="periodD_day_15" value="day_15">15일</option>
                                    <option id="periodD_month_1" value="month_1">1개월</option>
                                    <option id="periodD_month_3" value="month_3">3개월</option>
                                    <option id="periodD_month_6" value="month_6">6개월</option>
                                    <option id="periodD_all_0" value="all_0">상시</option>
                                </select>
                            </dd>
                            </dl>
                            <dl>
                                <dt>전시 여부</dt>
                                <dd>
                                    <div class="radio_wrap wide">
                                        <input type="radio" name="isdisplayD" id="isdisplayDT" value="T" v-model="basicInfo.isdisplay"/>
                                        <label for="isdisplayDT">전시</label>
                                        <input type="radio" name="isdisplayD" id="isdisplayDF" value="F" v-model="basicInfo.isdisplay"/>
                                        <label for="isdisplayDF">미 전시</label>
                                    </div>
                                </dd>
                            </dl>
                            <dl>
                                <dt>판매상태</dt>
                                <dd>
                                    <div class="radio_wrap">
                                        <div v-for="item in commonCode.goodsselltype" :key="item.cmcode">
                                            <input type="radio" name="goodsselltypeD" :id="'goodsselltypeD_'+item.cmcode" :value="item.cmcode" v-model="basicInfo.goodsselltype"/>
                                            <label :for="'goodsselltypeD_'+item.cmcode">{{ item.codename }}</label>
                                        </div>
                                    </div>
                                </dd>
                            </dl>
                            <dl>
                                <dt>담당MD<i class="essential"></i></dt>
                                <dd>
                                    <input type="search" style="width: 220px;" placeholder="담당MD를 선택하세요."
                                        :value="$util.isNull(basicInfo.mdcode)? '' : '['+basicInfo.mdcode+'] '+basicInfo.mdname" readonly disabled>
                                    <button type="button" class="btn-search" @click="onChargemdShow">검색</button>
                                    <!-- 담당MD 조회 컴포넌트 -->
                                    <div class="result-component ml0 no-round" style="display: block; width: 600px;" v-show="showInfo.ismdshow" v-click-outside="closeChargemdLayer">
                                        <div class="list-header" style="width: 100%;">
                                            <table cellspacing="0" cellpadding="0" class="data-tb align-c mg0" style="width: 100%;">
                                                <colgroup>
                                                    <col width="6%">
                                                    <col width="6%">
                                                    <col width="15%">
                                                    <col width="15%">
                                                    <col width="22%">
                                                    <col width="36%">
                                                </colgroup>
                                                <thead>
                                                    <tr>
                                                        <th>선택</th>
                                                        <th>No</th>
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
                                                        <th>연락처
                                                            <button type="button" :value="sortData.chargemd.mdtel" class="sort"
                                                                :class="[{up : sortData.chargemd.mdtel === 'mdtel_asc'}, {down : sortData.chargemd.mdtel === 'mdtel_desc'}]"
                                                                @click="sortToggle('chargemd', sortData.chargemd.mdtel)"></button>
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
                                                    <col width="22%">
                                                    <col width="36%">
                                                </colgroup>
                                                <tbody v-if="basicInfo.chargemdList.length > 0">
                                                    <tr v-for="(item, index) in basicInfo.chargemdList" :key="item.mdcode">
                                                        <td>
                                                            <input type="radio" class="circle" name="mdcodeD" v-model="basicInfo.mdcode"
                                                                :value="item.mdcode" :id="'mdcodeD' + index"
                                                                @change="setChargemdInfo(item)">
                                                        </td>
                                                        <td>{{ index+1 }}</td>
                                                        <td>{{ item.mdcode }}</td>
                                                        <td>{{ item.mdname }}</td>
                                                        <td>{{ item.mdtel }}</td>
                                                        <td>{{ item.mdemail }}</td>
                                                    </tr>
                                                </tbody>
                                                <tbody v-else>
                                                    <tr><td colspan="6">조회 결과가 존재하지 않습니다.</td></tr>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                    <!-- // 담당MD 조회 컴포넌트 -->
                                </dd>
                            </dl>
                            <dl>
                                <dt>적용채널</dt>
                                <dd>
                                    <div class="check-wrap">
                                        <input type="checkbox" id="chkAllChannelD" v-model="basicInfo.isallchannel" true-value="T" false-value="F" @change="checkAllChannel">
                                        <label for="chkAllChannelD">전체적용</label>
                                    </div>
                                    <div class="check-wrap" v-for="item in commonCode.muappchtype" :key="item.cmcode">
                                        <input type="checkbox" :id="'muappchtypeD_'+item.cmcode" v-model="basicInfo.muappchtypeArr" :true-value="[]" :value="item.cmcode"/>
                                        <label :for="'muappchtypeD_'+item.cmcode">{{ item.codename }}</label>
                                    </div>
                                </dd>
                            </dl>
                        </div>
                    </div>
                    <!-- //기본정보 영역 -->

                    <!-- 구성상품 Area -->
                    <div class="boxing" id="dealTab2">
                        <div class="boxing-title" :class="{closed: !tabObject.dealTab2.isOpen}">
                            구성상품<i class="arrcodi" @click="onToggle(tabObject.dealTab2)"></i>
                        </div>
                        <div class="form-area" :style="{display: tabObject.dealTab2.isOpen? 'block':'none'}">
                            <div class="blue-box">
                                <ul>
                                    <li>딜 구성 상품은 최대 000개까지 일괄 등록 가능하며, 000개 초과 시 일괄 등록 대상에서 제외합니다.</li>
                                    <li>엑셀 파일 업로드와 동시에 구성 상품 정보를 체크하며, 정상 건은 추가 등록하고 등록 불가 상품은 제외 처리하여 추가 등록하지 않습니다.</li>
                                    <li>존재하지 않는 상품번호 또는 문자가 입력된 경우 제외 처리 됩니다.</li>
                                    <li>상태가 판매중인 상품만 딜로 구성 할 수 있습니다.</li>
                                </ul>
                                <button type="button" class="btn big green mt20" @click="downloadExcelTemplate">엑셀양식 다운로드</button>
                                <div class="mt10">
                                    <input type="file" ref="excelFile" @change="handleFileUpload('excelFile')" hidden
                                        accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" />
                                    <input type="text" ref="excelFileName" readonly>
                                    <button type="button" class="btn blue-line ml3" @click="fileAttach('excelFile')">파일찾기</button>
                                    <button type="button" class="btn blue-line" @click="readExcelFile('excelFile')">일괄등록</button>
                                </div>
                            </div>
                            <div style="margin: 20px;">
                                <div class="caption-group clearfix">
                                    <div class="total-group fl">
                                        <span class="total">전체 <strong>{{ constInfo.constGoodsList.length }}</strong>건</span>
                                    </div>
                                    <div class="btn-group fr">
                                        <button type="button" class="btn blue-line" @click="openConstGoodsPopup">구성상품추가</button>
                                        <button type="button" class="btn red-line" @click="removeConstGoods">삭제</button>
                                        <button type="button" class="btn green-line" @click="downloadExcel"><i class="icon-excel"></i>엑셀다운로드</button>
                                    </div>
                                </div>
                                <div class="scroll-y" style="width: 100%; max-height: 500px;">
                                    <table cellpadding="0" cellspacing="0" class="data-tb align-c" style="margin-left: 0;">
                                        <colgroup>
                                            <col width="2%" /><!-- checkbox -->
                                            <col width="3.5%" /><!-- 노출순위 -->
                                            <col width="3.5%" /><!-- 대표상품 -->
                                            <col width="3.5%" /><!-- 노출여부 -->
                                            <col width="3.5%" /><!-- 판매구분 -->
                                            <col width="4%" /><!-- 파트너사 -->
                                            <col width="5%" /><!-- 상품코드 -->
                                            <col width="62px" /><!-- 이미지 -->
                                            <col width="" /><!-- 상품명 -->
                                            <col width="4%" /><!-- 상품유형 -->
                                            <col width="5%" /><!-- 판매가 -->
                                            <col width="4%" /><!-- 판매상태 -->
                                            <col width="6%" /><!-- 전시시작일 -->
                                            <col width="6%" /><!-- 전시종료일 -->
                                            <col width="6%" /><!-- 배송정보명 -->
                                            <col width="5%" /><!-- 배송비 -->
                                            <col width="4%" /><!-- 배송유형 -->
                                        </colgroup>
                                        <thead>
                                            <tr>
                                                <th><input type="checkbox" id="chkgoodsD" v-model="constInfo.isallchkgoods" @change="checkAllConstGoodsList($event.target.checked)" true-value="T" false-value="F" /></th>
                                                <th>노출순위</th>
                                                <th>대표상품</th>
                                                <th>노출여부</th>
                                                <th>판매구분</th>
                                                <th>파트너사</th>
                                                <th>상품코드</th>
                                                <th>이미지</th>
                                                <th>상품명</th>
                                                <th>상품유형</th>
                                                <th>판매가</th>
                                                <th>판매상태</th>
                                                <th>전시시작일</th>
                                                <th>전시종료일</th>
                                                <th>배송정보명</th>
                                                <th>배송비</th>
                                                <th>배송유형</th>
                                            </tr>
                                        </thead>
                                        <tbody v-if="constInfo.constGoodsList.length > 0">
                                            <tr v-for="(item, index) in constInfo.constGoodsList" :key="index">
                                                <td><input type="checkbox" :id="'chkgoodsD_'+index" v-model="moveData.targetIdx" :value="index" @change="checkConstGoodsList"/></td>
                                                <td>{{ item.sortnum }}</td>
                                                <td><input type="radio" class="circle" name="ismaingoodsD" :checked="item.ismaingoods=='T'" @change="setIsMainGoods(item)"/></td>
                                                <td v-if="item.isdisplay == 'T'"><button type="button" class="btn blue" @click="changeDisplay(item)">노출</button></td>
                                                <td v-if="item.isdisplay == 'F'"><button type="button" class="btn red" @click="changeDisplay(item)">숨김</button></td>
                                                <td>{{ item.ispbgoodsname }}</td>
                                                <td>{{ item.dealername }}</td>
                                                <td>{{ item.goodscode }}</td>
                                                <td>
                                                    <div class="img-thumb size60" :class="{'no-image': $util.isNull(item.fullpath)}">
                                                        <img :src="item.fullpath" v-if="!$util.isNull(item.fullpath)">
                                                    </div>
                                                </td>
                                                <td class="left">{{ item.goodsname }}</td>
                                                <td>{{ item.goodsdivtypename }}</td>
                                                <td class="right">{{ item.price }}</td>
                                                <td>{{ item.goodsselltypename }}</td>
                                                <td>{{ item.disstdate }}</td>
                                                <td>{{ item.diseddate }}</td>
                                                <td>{{ item.delivbname }}</td>
                                                <td>{{ item.delivfaretext }}</td>
                                                <td>{{ item.iscombdelivname }}</td>
                                            </tr>
                                        </tbody>
                                        <tbody v-else>
                                            <tr><td colspan="19">조회 결과가 존재하지 않습니다.</td></tr>
                                        </tbody>
                                    </table>
                                </div>
                                <div class="bottom-group" v-if="isWrite">
                                    <CommonArraySort :list-data="constInfo.constGoodsList"
                                                    :move-data="moveData"
                                                    :is-active-save-btn="false"
                                                    key-name="goodsno"
                                    />
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- //구성상품 영역 -->

                    <!-- 이미지정보 Area -->
                    <div class="boxing" id="dealTab3">
                        <div class="boxing-title" :class="{closed: !tabObject.dealTab3.isOpen}">
                            이미지 정보<i class="arrcodi" @click="onToggle(tabObject.dealTab3)"></i>
                        </div>
                        <div class="form-area" :style="{display: tabObject.dealTab3.isOpen? 'block':'none'}">
                            <div style="margin: 20px;">
                                <table cellpadding="0" cellspacing="0" class="gray-tb">
                                    <colgroup>
                                        <col width="170px">
                                        <col width="">
                                    </colgroup>
                                    <tbody>
                                        <tr>
                                            <th>대표이미지<i class="essential"></i></th>
                                            <td ref="repreimgtd">
                                                <div class="radio_wrap dpb">
                                                    <input type="radio" name="ismaingoodsimageD" id="ismaingoodsimageDT" @change="setPcrepreImg('T')" v-model="imageInfo.ismaingoodsimage" value="T"/>
                                                    <label for="ismaingoodsimageDT">대표상품 이미지 사용</label>
                                                    <input type="radio" name="ismaingoodsimageD" id="ismaingoodsimageDF" @change="setPcrepreImg('F')" v-model="imageInfo.ismaingoodsimage" value="F"/>
                                                    <label for="ismaingoodsimageDF">이미지 직접등록</label>
                                                </div>
                                                <div class="dpb">
                                                    <div class="img-with-text mt10" style="width: 202px;">
                                                        <div class="img-thumb size200" :class="{'no-image': $util.isNull(files['pcrepreimgfile'])}">
                                                            <img :src="imgPreview['pcrepreimgfile']" alt="대표이미지"
                                                                v-if="!$util.isNull(files['pcrepreimgfile']) && files['pcrepreimgfile'].status != 'N'" >
                                                            <img :src="files['pcrepreimgfile'].fullpath" alt="대표이미지" 
                                                                v-if="!$util.isNull(files['pcrepreimgfile']) && files['pcrepreimgfile'].status == 'N'" >
                                                        </div>
                                                        <button type="button" class="btn blue-line mt10" style="width: 100%;"
                                                            v-if="imageInfo.ismaingoodsimage=='F' && $util.isNull(files['pcrepreimgfile'])" @click="fileAttach('pcrepreimgfile')">파일 올리기</button>
                                                        <input type="file" ref="pcrepreimgfile" @change="handleFileUpload('pcrepreimgfile')" accept="image/jpeg, image/png" hidden/>
                                                        <button type="button" class="btn blue-line mt10" style="width: calc(50% - 3px);"
                                                            v-if="imageInfo.ismaingoodsimage=='F' && !$util.isNull(files['pcrepreimgfile'])" @click="fileAttach('pcrepreimgfile')">변경</button>
                                                        <button type="button" class="btn red-line mt10" style="width: calc(50% - 4px);"
                                                            v-if="imageInfo.ismaingoodsimage=='F' && !$util.isNull(files['pcrepreimgfile'])" @click="removeFile('pcrepreimgfile')">삭제</button>
                                                    </div>
                                                    <div class="img-with-text text">
                                                        <p class="txt-orange"><i class="icon-alert"></i>딜 판매상품의 대표 이미지입니다. 보기 쉬운 간결한 이미지를 활용해 주세요.</p>
                                                        <p class="txt-orange"><i class="icon-alert"></i>사이즈: 460*460 / 최소: 200*200 / 용량: 10MB 이하 / 파일 : JPG, JPEG, PNG</p>
                                                    </div>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <th>추가이미지</th>
                                            <td>
                                                <input type="file" id="addimgFileD" ref="addimgFile" @change="handleFileUpload('addimgFile', $event.target)" 
                                                    accept="image/jpeg, image/png" hidden multiple/>
                                                <div>
                                                    <div class="img-with-text" style="width: 202px;" v-for="(index, n) in 5" :key="index">
                                                        <div class="img-thumb size200 no-image" v-if="$util.isNull(files['addimgfilelist'][n])"></div>
                                                        <div class="img-thumb size200" v-else>
                                                            <img :src="imgPreview['addimgfilelist'][n]" :alt="'추가이미지'+n" v-if="files['addimgfilelist'][n].status != 'N'">
                                                            <img :src="files['addimgfilelist'][n].fullpath" :alt="'추가이미지'+n" v-else>
                                                        </div>
                                                        <button type="button" class="btn blue-line mt10" style="width: 100%;"
                                                            v-if="$util.isNull(files['addimgfilelist'][n])" @click="fileAttach('addimgFile')">파일 올리기</button>
                                                        <button type="button" class="btn blue-line mt10" style="width: calc(50% - 3px);"
                                                            v-if="!$util.isNull(files['addimgfilelist'][n])" @click="fileAttach('changeaddimgfile'+n)">변경</button>
                                                        <button type="button" class="btn red-line mt10" style="width: calc(50% - 4px);"
                                                            v-if="!$util.isNull(files['addimgfilelist'][n])" @click="removeFile('addimgFile', n)">삭제</button>
                                                        <input type="file" :id="'changeaddimgfile'+n" :ref="'changeaddimgfile'+n" @change="handleFileUpload('changeaddimgfile', $event.target, n)" 
                                                            accept="image/jpeg, image/png" hidden/>
                                                    </div>
                                                </div>
                                                <div class="mt20">
                                                    <div class="img-with-text" style="width: 202px;" v-for="(index, n) in 5" :key="index+5">
                                                        <div class="img-thumb size200 no-image" v-if="$util.isNull(files['addimgfilelist'][n+5])"></div>
                                                        <div class="img-thumb size200" v-else>
                                                            <img :src="imgPreview['addimgfilelist'][n+5]" :alt="'추가이미지'+(n+5)" v-if="files['addimgfilelist'][n].status != 'N'">
                                                            <img :src="files['addimgfilelist'][n+5].fullpath" :alt="'추가이미지'+(n+5)" v-else>
                                                        </div>
                                                        <button type="button" class="btn blue-line mt10" style="width: 100%;"
                                                            v-if="$util.isNull(files['addimgfilelist'][n+5])" @click="fileAttach('addimgFile')">파일 올리기</button>
                                                        <button type="button" class="btn blue-line mt10" style="width: calc(50% - 3px);"
                                                            v-if="!$util.isNull(files['addimgfilelist'][n+5])" @click="fileAttach('changeaddimgfile'+(n+5))">변경</button>
                                                        <button type="button" class="btn red-line mt10" style="width: calc(50% - 4px);"
                                                            v-if="!$util.isNull(files['addimgfilelist'][n+5])" @click="removeFile('addimgFile', n+5)">삭제</button>
                                                        <input type="file" :id="'changeaddimgfile'+(n+5)" :ref="'changeaddimgfile'+(n+5)" @change="handleFileUpload('changeaddimgfile', $event.target, n+5)" 
                                                            accept="image/jpeg, image/png" hidden/>
                                                    </div>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <th>모바일이미지</th>
                                            <td>
                                                <div class="img-with-text" style="width: 202px;">
                                                    <div class="img-thumb size460wide" :class="{'no-image': $util.isNull(files['morepreimgfile'])}">
                                                        <img :src="imgPreview['morepreimgfile']" alt="대표이미지(모바일)" class="fit"
                                                            v-if="!$util.isNull(files['morepreimgfile']) && files['morepreimgfile'].status != 'N'">
                                                        <img :src="files['morepreimgfile'].fullpath" alt="대표이미지(모바일)" class="fit"
                                                            v-if="!$util.isNull(files['morepreimgfile']) && files['morepreimgfile'].status == 'N'">
                                                    </div>
                                                    <button type="button" class="btn blue-line mt10" style="width: 100%;"
                                                        v-if="$util.isNull(files['morepreimgfile'])" @click="fileAttach('morepreimgfile')">파일 올리기</button>
                                                    <input type="file" ref="morepreimgfile" @change="handleFileUpload('morepreimgfile')" accept="image/jpeg, image/png" hidden/>
                                                    <button type="button" class="btn blue-line mt10" style="width: calc(50% - 3px);"
                                                        v-if="!$util.isNull(files['morepreimgfile'])" @click="fileAttach('morepreimgfile')">변경</button>
                                                    <button type="button" class="btn red-line mt10" style="width: calc(50% - 4px);"
                                                        v-if="!$util.isNull(files['morepreimgfile'])" @click="removeFile('morepreimgfile')">삭제</button>
                                                </div>
                                                <div class="img-with-text text">
                                                    <p class="txt-orange"><i class="icon-alert"></i>모바일 리스팅 및 와이드형 화면에 노출되는 이미지를 업로드 해 주세요.</p>
                                                    <p class="txt-orange"><i class="icon-alert"></i>사이즈: 460*460 / 최소: 200*200 / 용량: 10MB 이하 / 파일 : JPG, JPEG, PNG</p>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <th>공지이미지</th>
                                            <td>
                                                <div class="radio_wrap wide">
                                                    <input type="radio" name="isusenoticeD" id="isusenoticeDF" v-model="imageInfo.isusenotice" value="F">
                                                    <label for="isusenoticeDF">미사용</label>
                                                    <input type="radio" name="isusenoticeD" id="isusenoticeDT" v-model="imageInfo.isusenotice" value="T"/>
                                                    <label for="isusenoticeDT">사용</label>
                                                </div>
                                                <div class="mt10" v-show="imageInfo.isusenotice=='T'">
                                                    <common-editor ref="noticeeditorD" :style-object="styleObject"/>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <th>인트로이미지</th>
                                            <td>
                                                <div class="radio_wrap wide">
                                                    <input type="radio" name="isuseintroD" id="isuseintroDT" v-model="imageInfo.isuseintro" value="F"/>
                                                    <label for="isuseintroDT">미사용</label>
                                                    <input type="radio" name="isuseintroD" id="isuseintroDF" v-model="imageInfo.isuseintro" value="T"/>
                                                    <label for="isuseintroDF">사용</label>
                                                </div>
                                                <div class="mt10" v-show="imageInfo.isuseintro=='T'">
                                                    <common-editor ref="introeditorD" :style-object="styleObject"/>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <th>PC용 상품상세설명<i class="essential"></i></th>
                                            <td>
                                                <div>
                                                    <common-editor ref="pceditorD" :style-object="styleObject"/>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <th>모바일용 상품상세설명<i class="essential"></i></th>
                                            <td>
                                                <div class="mb10">
                                                    <!-- <input type="checkbox" id="pc-sameD" @change="setSameAsPcDetailContrent" v-model="imageInfo.issamepccont"><label for="pc-same">PC용 상품상세설명과 동일</label> -->
                                                    <button type="button" class="btn blue-line" @click="setSameAsPcDetailContrent">PC용 상품상세설명 복사</button>
                                                </div>
                                                <div class="mt10">
                                                    <common-editor ref="mobileeditorD" :style-object="styleObject"/>
                                                </div>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                    <!-- //이미지정보 영역 -->

                    <!-- 추가정보 Area -->
                    <div class="boxing" id="dealTab4">
                        <div class="boxing-title" :class="{closed: !tabObject.dealTab4.isOpen}">
                            추가정보<i class="arrcodi" @click="onToggle(tabObject.dealTab4)"></i>
                        </div>
                        <div class="form-area" :style="{display: tabObject.dealTab4.isOpen? 'block':'none'}">
                            <div style="margin: 20px;">
                                <table cellpadding="0" cellspacing="0" class="gray-tb">
                                    <colgroup>
                                        <col width="170px">
                                        <col width="">
                                    </colgroup>
                                    <tbody>
                                        <tr>
                                            <th>회원유형 별 노출여부</th>
                                            <td>
                                                <div class="check-wrap">
                                                    <input type="checkbox" id="chkAllMemberD" v-model="etcInfo.isallmember" true-value="T" false-value="F" @change="checkAllMembertype">
                                                    <label for="chkAllMemberD">전체</label>
                                                </div>
                                                <div class="check-wrap" v-for="item in commonCode.dadamembertype" :key="item.cmcode">
                                                    <input type="checkbox" :id="'mumembertypeD_'+item.cmcode" v-model="etcInfo.mumembertypeArr" :true-value="[]" :value="item.cmcode">
                                                    <label :for="'mumembertypeD_'+item.cmcode">{{ item.codename }}</label>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <th>리뷰 공개유무</th>
                                            <td>
                                                <div class="radio_wrap wide">
                                                    <input type="radio" name="isopenreviewD" id="isopenreviewDT" v-model="etcInfo.isopenreview" value="T"/>
                                                    <label for="isopenreviewDT">공개</label>
                                                    <input type="radio" name="isopenreviewD" id="isopenreviewDF" v-model="etcInfo.isopenreview" value="F"/>
                                                    <label for="isopenreviewDF">비공개</label>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <th>관리자 메모</th>
                                            <td>
                                                <div class="memo-write">
                                                    <textarea placeholder="메모를 작성해 주세요!" v-model="etcInfo.inputmemo"></textarea>
                                                    <button type="button" class="btn big blue" @click="addMeno">메모<br>저장</button>
                                                </div>
                                                <div class="scroll-y" style="width: 100%; max-height: 300px; margin-bottom: 0;">
                                                    <table cellpadding="0" cellspacing="0" class="data-tb align-c">
                                                        <colgroup>
                                                            <col width="5%" /><!-- No -->
                                                            <col width="15%" /><!-- 작성일시 -->
                                                            <col width="8%" /><!-- 작성자 -->
                                                            <col width="" /><!-- 상품명 -->
                                                            <col width="40px" /><!-- 삭제 -->
                                                        </colgroup>
                                                        <thead>
                                                            <tr>
                                                                <th>No</th>
                                                                <th>작성일시
                                                                    <button type="button" class="sort" :value="sortData.memo.regdate"
                                                                        :class="[{up : sortData.memo.regdate === 'regdate_asc'}, {down : sortData.memo.regdate === 'regdate_desc'}]"
                                                                        @click="sortToggle('memo', sortData.memo.regdate)"></button>
                                                                </th>
                                                                <th>작성자</th>
                                                                <th colspan="2">내용</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody v-if="etcInfo.isshowmemo">
                                                            <tr v-for="item in etcInfo.goodsMemoList" :key="item.idx" v-show="item.istrash=='F'">
                                                                <td>{{ item.no }}</td>
                                                                <td>{{ item.regdate }}</td>
                                                                <td>{{ item.regusername }}</td>
                                                                <td class="left">{{ item.memo }}</td>
                                                                <td class="no-left"><button type="button" class="del mg0" @click="removeMeno(item)"></button></td>
                                                            </tr>
                                                        </tbody>
                                                        <tbody v-else>
                                                            <tr><td colspan="5">조회 결과가 존재하지 않습니다.</td></tr>
                                                        </tbody>
                                                    </table>
                                                </div>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                    <!-- //추가정보 영역 -->
                    <div class="btn-group" v-if="basicInfo.orggoodsselltype != $store.getters['ADMIN'].GOODS_SELL_TYPE_GST006">
                        <button type="button" class="btn big blue-line" v-if="!$util.isNull(basicInfo.dealno)" @click="goPriview">미리보기</button>
                        <button type="button" class="btn big gray" v-if="!iscopy" @click="iscopy = true">복사</button>
                        <button type="button" class="btn big blue" @click="goTemporarySave" v-if="iscopy">임시저장</button>
                        <button type="button" class="btn big blue" @click="goSave">저장</button>
                        <button type="button" class="btn big darkgray" @click="$emit('closePopup');">닫기</button>
                    </div>
                    <div class="btn-group" v-if="basicInfo.orggoodsselltype == $store.getters['ADMIN'].GOODS_SELL_TYPE_GST006">
                        <button type="button" class="btn big darkgray" @click="$emit('closePopup');">닫기</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- /상품상세정보 팝업 -->
</template>

<script src="./DealDetail.js"/>

<style scoped>
.tab-group ul li a.active {
    background: #fff;
    border: 1px solid #167cd8;
    border-bottom: 1px solid #fff;
    z-index: 2;
}
img.fit{
    object-fit: cover;
}
</style>
