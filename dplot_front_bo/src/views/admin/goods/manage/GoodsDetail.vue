<template>
    <!-- 상품상세정보 팝업 -->
    <div id="modal-wrap" class="modal" style="display: block;">
        <div class="modal-content" style="width: 1600px;">
            <div class="pop-header">
                <h2>상품 상세정보</h2>
                <button type="button" class="pop-close" @click="$emit('closePopup');"></button>
            </div>
            <div class="pop-body">
                <div class="gray-box mg0 clearfix">
                    <div class="fl">[{{ basicInfo.orgispbgoods=='T'? '일반상품' : basicInfo.dealername }}] {{ basicInfo.goodscode }} {{ basicInfo.orggoodsname }}</div>
                    <div class="fr">
                        <span>등록일 : {{ basicInfo.regdate }}</span>
                        <span class="left-bar">최종수정일 : {{ basicInfo.moddate }}</span>
                        <span class="left-bar">[{{ sellInfo.goodsselltypename }}]</span>
                        <span class="left-bar" v-if="basicInfo.ispbgoods=='F'">[{{ basicInfo.goodsapprtypename }}]</span>
                    </div>
                </div>
                <div class="clearfix mt10">
                    <div class="btn-group fr" :style="sellInfo.orggoodsselltype===$store.getters['ADMIN'].GOODS_SELL_TYPE_GST006? 'visibility: hidden;' : ''">
                        <span class="txt-orange mr5" v-if="iscopy"><i class="icon-alert"></i>현재 상품은 복사한 상품입니다.</span>
                        <button type="button" class="btn big blue-line" v-if="!$util.isNull(basicInfo.goodscode) && !iscopy" @click="goPriview">미리보기</button>
                        <button type="button" class="btn big gray" v-if="!iscopy" @click="iscopy = true">복사</button>
                        <!-- <button type="button" class="btn big gray" v-if="iscopy" @click="iscopy = false">복사해제</button> -->
                        <button type="button" class="btn big blue" @click="goTemporarySave" v-if="iscopy">임시저장</button>
                        <button type="button" class="btn big blue" @click="changeGoodsAppr($store.getters['ADMIN'].GOODS_STATUS_APPROVAL)"
                            v-if="!isPartner && !iscopy && basicInfo.goodsapprtype===$store.getters['ADMIN'].GOODS_STATUS_REQ">승인</button>
                        <button type="button" class="btn big red" @click="changeGoodsAppr($store.getters['ADMIN'].GOODS_STATUS_REJECT)"
                            v-if="!isPartner && !iscopy && basicInfo.goodsapprtype===$store.getters['ADMIN'].GOODS_STATUS_REQ">반려</button>
                        <button type="button" class="btn big blue" @click="goSave" 
                            v-if="!isPartner || (isPartner && !iscopy && basicInfo.goodsapprtype === $store.getters['ADMIN'].GOODS_STATUS_APPROVAL)">저장</button>
                        <button type="button" class="btn big red" @click="goReqApprv"
                            v-if="isPartner && !iscopy && basicInfo.goodsapprtype===$store.getters['ADMIN'].GOODS_STATUS_REJECT">재승인요청</button>
                        <button type="button" class="btn big red" @click="goReqApprv"
                            v-if="isPartner && (iscopy || (!iscopy && basicInfo.goodsapprtype===$store.getters['ADMIN'].GOODS_STATUS_TEMP))">승인요청</button>
                    </div>
                </div>
                <div class="tab-group">
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
                <div class="tab-area dtl" style="max-height: calc(90vh - 190px);">
                    <!-- 기본정보 Area -->  
                    <div class="boxing" id="goodsTab1">
                        <div class="boxing-title" :class="{closed: !tabObject.goodsTab1.isOpen}">
                            기본 정보
                            <i class="arrcodi" @click="onToggle(tabObject.goodsTab1)"></i>
                        </div>
                        <div class="form-area" :style="{display: tabObject.goodsTab1.isOpen? 'block':'none'}">
                            <dl v-if="!isPartner">
                                <dt>판매구분</dt>
                                <dd>
                                    <div class="radio_wrap wide">
                                        <input type="radio" name="ispbgoodsD" id="ispbgoodsDT" v-model="basicInfo.ispbgoods" value="T" @click="basicInfo.dealerno=''" disabled/>
                                        <label for="ispbgoodsDT">직매입</label>
                                        <input type="radio" name="ispbgoodsD" id="ispbgoodsDF" v-model="basicInfo.ispbgoods" value="F" disabled/>
                                        <label for="ispbgoodsDF">위탁</label>
                                    </div>
                                    <select style="width: 250px;" v-if="basicInfo.ispbgoods=='F'" v-model="basicInfo.dealerno" disabled>
                                        <option value="">파트너사 선택</option>
                                        <option v-for="item in basicInfo.partnerList" :key="item.no" :value="item.no">{{ item.name }} </option>
                                    </select>
                                </dd>
                            </dl>
                            <fieldset :disabled="!iscopy && isPartner && editauth.category.isauth===false">
                                <legend>카테고리 수정권한</legend>
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
                                        <button type="button" class="btn blue-line" @click="addCategory" data-auth="category">추가</button>
                                        <button type="button" class="btn blue-line" @click="onCateTemplateShow" data-auth="category">카테고리템플릿</button>
                                        <!-- 카테고리템플릿 검색결과 리스트 -->
                                        <div class="result-component right round" style="display: block;" v-show="showInfo.istempshow" v-click-outside="closeCatetempLayer">
                                            <div class="list checklist">
                                                <ul v-if="basicInfo.categoryTemplateList.length > 0">
                                                    <li v-for="(item, index) in basicInfo.categoryTemplateList" :key="item.cateidx">
                                                        <input type="checkbox" :id="'catetempD_'+item.cateidx" v-model="item.ischecked"><label :for="'catetempD_'+item.cateidx">{{ item.fullcategoryname }}</label>
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
                                                                <li v-for="item in basicInfo.categoryObj.depth1list" 
                                                                    :key="item.idx" @click="setCategoryCodeInfo('1', item.idx)"
                                                                    :class="{ active: basicInfo.depth1Category.idx == item.idx }">
                                                                    <a href="javascript:void(0);">{{ item.value }}</a>
                                                                </li>
                                                            </ul>
                                                        </div>
                                                    </td>
                                                    <td>
                                                        <div class="list">
                                                            <ul>
                                                                <li v-for="item in basicInfo.categoryObj.depth2list"
                                                                    :key="item.idx" @click="setCategoryCodeInfo('2', item.idx)"
                                                                    :class="{ active: basicInfo.depth2Category.idx == item.idx }">
                                                                    <a href="javascript:void(0);">{{ item.value }}</a>
                                                                </li>
                                                            </ul>
                                                        </div>
                                                    </td>
                                                    <td>
                                                        <div class="list">
                                                            <ul>
                                                                <li v-for="item in basicInfo.categoryObj.depth3list"
                                                                    :key="item.idx" @click="setCategoryCodeInfo('3', item.idx)"
                                                                    :class="{ active: basicInfo.depth3Category.idx == item.idx }">
                                                                    <a href="javascript:void(0);">{{ item.value }}</a>
                                                                </li>
                                                            </ul>
                                                        </div>
                                                    </td>
                                                    <td>
                                                        <div class="list">
                                                            <ul>
                                                                <li v-for="item in basicInfo.categoryObj.depth4list"
                                                                    :key="item.idx" @click="setCategoryCodeInfo('4', item.idx)"
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
                                                    <input type="radio" class="circle" name="cate-selD" :id="'isrepreD_'+index" :checked="item.isrepre=='T'" @change="setIsrepreCategory(item)" data-auth="category">
                                                    <label :for="'isrepreD_'+index">대표</label>
                                                    <span class="box" :class="{'blue': item.isselected==='T'}">선택카테고리</span>
                                                    <span class="ml10" :class="{'txt-blue': item.isselected==='T'}">{{ item.fullcategoryname }}</span>
                                                    <div class="left-bar" v-if="isWrite" data-auth="category">
                                                        <input type="checkbox" class="star" v-model="item.isstar" true-value="T" false-value="F" @click="saveCategoryTemp(item)">
                                                    </div>
                                                    <div class="left-bar" data-auth="category"><button type="button" class="del" @click="removeGoodsCategory(item)"></button></div>
                                                    <span class="txt-orange left-bar small-txt" v-if="index==0"><i class="icon-alert"></i>별 아이콘 체크 시 [카테고리템플릿]에 저장됩니다.</span>
                                                </li>
                                            </ul>
                                        </div>
                                    </dd>
                                </dl>
                            </fieldset>
                            <fieldset :disabled="!iscopy && isPartner && editauth.goodsname.isauth===false">
                                <legend>상품명 수정권한</legend>
                                <dl>
                                    <dt>상품명<i class="essential"></i></dt>
                                    <dd><input type="text" value="" style="width: 100%;" placeholder="상품명을 입력하세요!" v-model="basicInfo.goodsname" :maxlength="250"></dd>
                                </dl>
                            </fieldset>
                            <fieldset :disabled="!iscopy && isPartner && editauth.summary.isauth===false">
                                <legend>상품요약설명 수정권한</legend>
                                <dl>
                                    <dt>상품요약설명</dt>
                                    <dd><textarea style="width: 100%;" v-model="basicInfo.summary" :maxlength="250"></textarea></dd>
                                </dl>
                            </fieldset>
                            <dl>
                                <dt>검색키워드</dt>
                                <dd>
                                    <input type="text" value="" style="width: 100%;" placeholder="검색어는 ,(콤마)로 구분됩니다." v-model="basicInfo.keyword" :maxlength="100">
                                </dd>
                                <dd class="dd-right" style="width: 105px;"><span>({{ basicInfo.keyword.length }}/100)</span></dd>
                            </dl>
                            <dl>
                                <dt>브랜드<i class="essential"></i></dt>
                                <dd>
                                    <input type="search" style="width: 220px;" placeholder="브랜드명을 검색하세요." :maxlength="200"
                                        v-model="basicInfo.brandname" :disabled="basicInfo.isnobrand=='T'"
                                        @keyup.enter="openSearchBrandPopup"
                                        @change="basicInfo.brandidx = ''">
                                    <button type="button" class="btn-search" @click="openSearchBrandPopup">검색</button>
                                    <input type="checkbox" id="no-brandD" class="ml10" v-model="basicInfo.isnobrand" @click="initBrandInfo" true-value='T' false-value='F'>
                                    <label for="no-brandD">브랜드 없음</label>
                                    <span class="left-bar txt-red" v-if="basicInfo.isnobrand=='T'">설정안함</span>
                                </dd>
                            </dl>
                            <dl>
                                <dt>담당MD<i class="essential"></i></dt>
                                <dd>
                                    <input type="search" style="width: 220px;" placeholder="사원이름 또는 메일주소를 입력하세요." v-if="isPartner" v-model="basicInfo.mdsword" @keyup.enter="onChargemdShow">
                                    <button type="button" class="btn-search" @click="onChargemdShow" v-if="isPartner">검색</button>
                                    <input type="text" style="width: 220px;" placeholder="담당MD를 선택하세요." v-if="isPartner" 
                                        :value="$util.isNull(basicInfo.mdsabun)? '' : '['+basicInfo.mdsabun+'] '+basicInfo.empname" readonly disabled>
                                    <input type="search" style="width: 220px;" placeholder="담당MD를 선택하세요." v-if="!isPartner"
                                        :value="$util.isNull(basicInfo.mdsabun)? '' : '['+basicInfo.mdsabun+'] '+basicInfo.empname" readonly disabled>
                                    <button type="button" class="btn-search" @click="onChargemdShow" v-if="!isPartner">검색</button>
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
                                                        <th>MD코드
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
                                                <tbody v-if="basicInfo.chargemdList.length > 0">
                                                    <tr v-for="(item, index) in basicInfo.chargemdList" :key="item.mdifidx">
                                                        <td>
                                                            <input type="radio" class="circle" name="mdifidxD" v-model="basicInfo.mdifidx"
                                                                :value="item.mdifidx" :id="'mdifidxD' + index"
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
                            <dl v-if="!isPartner">
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
                    
                    <!-- 판매정보 영역 -->
                    <div class="boxing" id="goodsTab2">
                        <div class="boxing-title" :class="{closed: !tabObject.goodsTab2.isOpen}">
                            판매 정보
                            <i class="arrcodi" @click="onToggle(tabObject.goodsTab2)"></i>
                        </div>
                        <div class="form-area" :style="{display: tabObject.goodsTab2.isOpen? 'block':'none'}">
                            <dl>
                                <dt>과세여부</dt>
                                <dd>
                                    <div class="radio_wrap wide">
                                        <input type="radio" name="istaxfreeD" id="istaxfreeDF" value="F" v-model="sellInfo.istaxfree" disabled/>
                                        <label for="istaxfreeDF">과세</label>
                                        <input type="radio" name="istaxfreeD" id="istaxfreeDT" value="T" v-model="sellInfo.istaxfree" disabled/>
                                        <label for="istaxfreeDT">면세</label>
                                    </div>
                                </dd>
                            </dl>
                            <dl>
                                <dt>판매상태</dt>
                                <dd>
                                    <div class="radio_wrap">
                                        <div v-for="item in commonCode.goodsselltype" :key="item.cmcode">
                                            <input type="radio" name="goodsselltypeD" :id="'goodsselltypeD_'+item.cmcode" :value="item.cmcode" v-model="sellInfo.goodsselltype"
                                                :disabled="!iscopy && isPartner && editauth.soldout.isauth===false 
                                                        && (item.cmcode === $store.getters['ADMIN'].GOODS_SELL_TYPE_GST004||item.cmcode === $store.getters['ADMIN'].GOODS_SELL_TYPE_GST005)"/>
                                            <label :for="'goodsselltypeD_'+item.cmcode">{{ item.codename }}</label>
                                        </div>
                                    </div>
                                </dd>
                            </dl>
                            <dl>
                                <dt>전시기간</dt>
                                <dd>
                                    <CommonDatePickerFromTo
                                        :fromYYYYMMDD="sellInfo.disstdate"
                                        :fromHH="sellInfo.dissthour"
                                        :fromMM="sellInfo.disstmin"
                                        :toYYYYMMDD="sellInfo.diseddate"
                                        :toHH="sellInfo.disedhour"
                                        :toMM="sellInfo.disedmin"
                                        :useFrom="true"
                                        :useTo="true"
                                        @getDate="pickerChangeEvent"
                                    />
                                    <select v-model="sellInfo.disperiod">
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
                                <dt>판매가격(대표상품)<i class="essential"></i></dt>
                                <dd>
                                    <span>정상가</span>
                                <input type="text" style="width: 120px;" v-model="sellInfo.marketprice" maxlength="11" readonly/>
                                    <span>원</span>
                                    <span class="left-bar">판매가</span>
                                <input type="text" style="width: 120px;" v-model="sellInfo.price" maxlength="11" readonly/>
                                    <span>원</span>
                                </dd>
                            </dl>
                            <dl>
                                <dt>최소~최대 주문수량<i class="essential"></i></dt>
                                <dd>
                                <input type="text" style="width: 80px;" v-model="sellInfo.minordcnt" maxlength="11"/>
                                    <span>개 부터 ~</span>
                                <input type="text" style="width: 80px;" v-model="sellInfo.maxordcnt" maxlength="11"/>
                                    <span>개 이내</span>
                                </dd>
                            </dl>
                            <dl>
                                <dt>1일 최대 주문수량<i class="essential"></i></dt>
                                <dd>
                                <input type="text" style="width: 80px;" v-model="sellInfo.daymaxordcnt" maxlength="11"/>
                                    <span>개 까지 구매가능</span>
                                </dd>
                            </dl>
                            <dl>
                                <dt>1인당 최대 구매수량<i class="essential"></i></dt>
                                <dd>
                                <input type="text" style="width: 80px;" v-model="sellInfo.permaxordcnt" maxlength="11"/>
                                    <span>개 까지 구매가능 (기간없이)</span>
                                </dd>
                            </dl>
                            <fieldset :disabled="!iscopy && isPartner && editauth.display.isauth===false">
                                <legend>전시 여부 수정권한</legend>
                                <dl>
                                    <dt>전시 여부</dt>
                                    <dd>
                                        <div class="radio_wrap wide">
                                            <input type="radio" name="isdisplayD" id="isdisplayDT" value="T" v-model="sellInfo.isdisplay"/>
                                            <label for="isdisplayDT">전시</label>
                                            <input type="radio" name="isdisplayD" id="isdisplayDF" value="F" v-model="sellInfo.isdisplay"/>
                                            <label for="isdisplayDF">미 전시</label>
                                        </div>
                                    </dd>
                                </dl>
                            </fieldset>
                            <dl v-if="!isPartner">
                                <dt>첫 구매시 할인혜택여부</dt>
                                <dd>
                                    <div class="radio_wrap wide">
                                        <input type="radio" name="isfrstsaleD" id="isfrstsaleDT" value="T" v-model="sellInfo.isfrstsale"/>
                                        <label for="isfrstsaleDT">적용</label>
                                        <input type="radio" name="isfrstsaleD" id="isfrstsaleDF" value="F" v-model="sellInfo.isfrstsale"/>
                                        <label for="isfrstsaleDF">미 적용</label>
                                    </div>
                                </dd>
                            </dl>
                            <dl v-if="!isPartner">
                                <dt>취소시 판매자 승인여부</dt>
                                <dd>
                                    <div class="radio_wrap wide">
                                        <input type="radio" name="iscncappr" id="iscncapprDF" value="F" v-model="sellInfo.iscncappr"/>
                                        <label for="iscncapprDF">필요없음</label>
                                        <input type="radio" name="iscncappr" id="iscncapprDT" value="T" v-model="sellInfo.iscncappr"/>
                                        <label for="iscncapprDT">승인필요</label>
                                    </div>
                                </dd>
                            </dl>
                            <dl>
                                <dt>상품상태</dt>
                                <dd>
                                    <div class="radio_wrap">
                                        <div v-for="item in commonCode.goodsdivtype" :key="item.cmcode">
                                            <input type="radio" name="goodsdivtypeD" :id="'goodsdivtypeD_'+item.cmcode" :value="item.cmcode" v-model="sellInfo.goodsdivtype"/>
                                            <label :for="'goodsdivtypeD_'+item.cmcode">{{ item.codename }}</label>
                                        </div>
                                    </div>
                                </dd>
                            </dl>
                            <dl v-if="!isPartner">
                                <dt>판매대상</dt>
                                <dd>
                                    <div class="radio_wrap">
                                        <input type="radio" name="selltargetD" id="selltargetD_all" value="ALL" v-model="sellInfo.selltarget"/>
                                        <label for="selltargetD_all">전체회원</label>
                                        <input type="radio" name="selltargetD" id="selltargetD_type" value="TYPE" v-model="sellInfo.selltarget"/>
                                        <label for="selltargetD_type">등급별</label>
                                        <input type="radio" name="selltargetD" id="selltargetD_sp" value="SP" v-model="sellInfo.selltarget"/>
                                        <label for="selltargetD_sp">특정회원</label>
                                    </div>
                                </dd>
                            </dl>
                            <dl v-if="!isPartner && sellInfo.selltarget=='TYPE'">
                                <dt>등급별</dt>
                                <dd>
                                    <div class="check-wrap">
                                        <input type="checkbox" id="chkAllMemlvD" v-model="sellInfo.isallmemlv" true-value="T" false-value="F" @change="checkAllMemlvtype">
                                        <label for="chkAllMemlvD">전체</label>
                                    </div>
                                    <div class="check-wrap" v-for="item in commonCode.memlvtype" :key="item.cmcode">
                                        <input type="checkbox" :id="'mumemlvtypeD_'+item.cmcode" v-model="sellInfo.mumemlvtypeArr" :true-value="[]" :value="item.cmcode">
                                        <label :for="'mumemlvtypeD_'+item.cmcode">{{ item.codename }}</label>
                                    </div>
                                </dd>
                            </dl>
                            <dl v-if="!isPartner && sellInfo.selltarget=='SP'">
                                <dt>특정회원</dt>
                                <dd class="full">
                                    <div class="caption-group clearfix dpb">
                                        <div class="total-group fl">
                                            <span class="total">특정회원목록</span>
                                        </div>
                                        <div class="btn-group fr">
                                        <input type="file" ref="spUserExcelFile" @change="readExcelFile('spUserExcelFile', $event)" hidden
                                            accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" />
                                        <button type="button" class="btn black-line" @click="downloadExcelTemplate('MemberTemplate.xlsx')">양식 다운로드</button>
                                        <button type="button" class="btn green-line"  @click="fileAttach('spUserExcelFile')">엑셀파일 올리기</button>
                                        <button type="button" class="btn blue-line" @click="openAddUserPopup">회원추가</button>
                                        <button type="button" class="btn red-line" @click="removeUser">삭제</button>
                                        </div>
                                    </div>
                                    <div class="scroll-y" style="width: 100%; max-height: 400px;">
                                        <table cellpadding="0" cellspacing="0" class="data-tb align-c" style="margin-left: 0;">
                                            <caption>특정회원목록</caption>
                                            <colgroup>
                                                <col width="5%" /><!-- checkbox -->
                                                <col width="5%" /><!-- No -->
                                                <col width="15%" /><!-- 아이디 -->
                                                <col width="15%" /><!-- 이름 -->
                                                <col width="15%" /><!-- 유형 -->
                                                <col width="15%" /><!-- 등급 -->
                                                <col width="" /><!-- 가입일 -->
                                            </colgroup>
                                            <thead>
                                                <tr>
                                                    <th><input type="checkbox" id="chkallgoodsmemD" v-model="sellInfo.isallchkmem" @change="checkAllMemberList($event.target.checked)" true-value="T" false-value="F" /></th>
                                                    <th>No</th>
                                                    <th>아이디</th>
                                                    <th>이름</th>
                                                    <th>유형
                                                        <button type="button" class="sort" :value="sortData.goodsMember.membertype"
                                                            :class="[{up : sortData.goodsMember.membertype=== 'membertype_asc'}, {down : sortData.goodsMember.membertype === 'membertype_desc'}]"
                                                            @click="sortToggle('goodsMember', sortData.goodsMember.membertype)"></button>
                                                    </th>
                                                    <th>등급
                                                        <button type="button" class="sort" :value="sortData.goodsMember.memlv"
                                                            :class="[{up : sortData.goodsMember.memlv=== 'memlv_asc'}, {down : sortData.goodsMember.memlv === 'memlv_desc'}]"
                                                            @click="sortToggle('goodsMember', sortData.goodsMember.memlv)"></button>
                                                    </th>
                                                    <th>가입일
                                                        <button type="button" class="sort" :value="sortData.goodsMember.regdate"
                                                            :class="[{up : sortData.goodsMember.regdate=== 'regdate_asc'}, {down : sortData.goodsMember.regdate === 'regdate_desc'}]"
                                                            @click="sortToggle('goodsMember', sortData.goodsMember.regdate)"></button>
                                                    </th>
                                                </tr>
                                            </thead>
                                            <tbody v-if="sellInfo.goodsMemberList.length > 0">
                                                <tr v-for="(item, index) in sellInfo.goodsMemberList" :key="item.userno">
                                                    <td><input type="checkbox" :id="'goodsMember_'+index" v-model="item.ischecked" @change="checkMemberList($event.target.checked)"/></td>
                                                    <td>{{ index+1 }}</td>
                                                    <td>{{ item.userid }}</td>
                                                    <td>{{ item.username }}</td>
                                                    <td>{{ item.dadamembertypename }}</td>
                                                    <td>{{ item.memlvtypename }}</td>
                                                    <td>{{ item.regdate }}</td>
                                                </tr>
                                            </tbody>
                                            <tbody v-else>
                                                <tr><td colspan="7">조회 결과가 존재하지 않습니다.</td></tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </dd>
                            </dl>
                        </div>
                    </div>
                    <!-- //판매정보 영역 -->
                    
                    <!-- 옵션정보 영역 -->
                    <div class="boxing" id="goodsTab3">
                        <div class="boxing-title" :class="{closed: !tabObject.goodsTab3.isOpen}">
                            옵션 정보
                            <i class="arrcodi" @click="onToggle(tabObject.goodsTab3)"></i>
                        </div>
                        <div class="form-area" :style="{display: tabObject.goodsTab3.isOpen? 'block':'none'}">
                            <!-- 옵션정보(위탁) -->
                            <div class="blue-box" v-if="basicInfo.ispbgoods=='F'">
                                <ul>
                                    <li>정상가, 판매가, 수수료율, 상품명은 필수 입력 항목입니다.</li>
                                    <!-- <li>상품정렬순서는 상품명 앞 01, 02, 03 순으로 붙이면 순차적으로 정렬됩니다.</li> -->
                                    <li>단품은 그리드에서 행 추가 후 등록하시면 편리합니다.</li>
                                </ul>
                                <button type="button" class="btn big blue mt20" @click="downloadExcelTemplate('AdminGoodsOptionTemplate.xlsx')" v-if="!isPartner">양식 다운로드</button>
                                <button type="button" class="btn big blue mt20" @click="downloadExcelTemplate('PartnerGoodsOptionTemplate.xlsx')" v-if="isPartner">양식 다운로드</button>
                                <div class="mt10">
                                    <input type="file" ref="optionExcelFile" @change="handleFileUpload('optionExcelFile')" hidden
                                        accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" />
                                    <input type="text" ref="optionExcelFileName" readonly>
                                    <button type="button" class="btn blue-line ml3" @click="fileAttach('optionExcelFile')" :disabled="isPartner && !editauth.option.isauth">파일찾기</button>
                                    <button type="button" class="btn blue-line" @click="readExcelFile('optionExcelFile')" :disabled="isPartner && !editauth.option.isauth">일괄등록</button>
                                </div>
                            </div>
                            <div style="margin: 20px;" v-if="basicInfo.ispbgoods=='F'">
                                <fieldset :disabled="!iscopy && isPartner && editauth.option.isauth===false">
                                    <legend>옵션 수정권한</legend>
                                    <div class="caption-group clearfix">
                                        <div class="total-group fl">
                                            <span class="total">전체 <strong>{{ optionInfo.optionList.length }}</strong>건</span>
                                        </div>
                                        <div class="btn-group fr">
                                            <button type="button" class="btn blue-line ml3" @click="setOptDisplay('T')">노출</button>
                                            <button type="button" class="btn red-line" @click="setOptDisplay('F')">숨김</button>
                                            <!-- <select v-model="optionInfo.totcolortype" v-if="isWrite">
                                                <option value="">검색컬러 선택</option>
                                                <option v-for="item in commonCode.colortype" :key="item.cmcode" :value="item.cmcode">{{item.codename}}</option>
                                            </select> -->
                                            <!-- <button type="button" class="btn blue-line ml3" @click="setAllColorType">일괄지정</button> -->
                                            <button type="button" class="btn blue-line" @click="addOption">행 추가</button>
                                            <button type="button" class="btn red-line" @click="removeOption">행 삭제</button>
                                            <button type="button" class="btn blue-line" @click="openOtionItemPopup">항목관리</button>
                                        </div>
                                    </div>
                                </fieldset>
                                <div class="scroll-y" style="width: 100%; max-height: 500px;">
                                    <table cellpadding="0" cellspacing="0" class="data-tb align-c" style="margin-left: 0;">
                                        <colgroup>
                                            <col width="3%" /><!-- checkbox -->
                                            <col width="8%" /><!-- 옵션코드 -->
                                            <col width="6%" /><!-- 대표상품 -->
                                            <col width="6%" /><!-- 옵션상태 -->
                                            <col width="8%" /><!-- 정상가 -->
                                            <col width="8%" /><!-- 판매가 -->
                                            <col width="6%" /><!-- 수수료율(%) -->
                                            <col width="6%" /><!-- 재고 -->
                                            <!-- <col width="" /> -->
                                            <!-- <col width="8%" /> -->
                                            <col width="" v-for="(item, itemIdx) in optionInfo.optionItemList" :key="itemIdx"/><!-- 항목 -->
                                        </colgroup>
                                        <thead>
                                            <tr>
                                                <th><input type="checkbox" id="chkalloptD" v-model="optionInfo.isallchkopt" @change="checkAllOptionList($event.target.checked)" true-value="T" false-value="F" :disabled="editauth.option.isauth===false"/></th>
                                                <th>옵션코드</th>
                                                <th>대표상품</th>
                                                <th>옵션상태</th>
                                                <th>정상가</th>
                                                <th>판매가</th>
                                                <th>수수료율(%)</th>
                                                <th>재고</th>
                                                <!-- <th>상품명</th> -->
                                                <!-- <th>검색컬러지정</th> -->
                                                <th class="bg-yellow" v-for="(item, itemIdx) in optionInfo.optionItemList" :key="itemIdx">{{ item.optionname }}</th>
                                            </tr>
                                        </thead>
                                        <tbody v-if="optionInfo.optionList.length > 0">
                                            <tr v-for="(item, index) in optionInfo.optionList" :key="index">
                                                <td><input type="checkbox" :id="'chkoptD_'+item.optionno" v-model="item.ischecked" @change="checkOptionList($event.target.checked)" :disabled="editauth.option.isauth===false"/></td>
                                                <td>{{ item.optioncode }}</td>
                                                <td><input type="radio" class="circle" name="ismaingoodsD" :checked="item.ismaingoods=='T'" @change="setIsMainGoods(item)" :disabled="editauth.option.isauth===false"/></td>
                                                <td v-if="item.isoptdisplay == 'T'"><button type="button" class="btn blue" @click="changeOptDisplay(item)" :disabled="editauth.option.isauth===false">노출</button></td>
                                                <td v-if="item.isoptdisplay == 'F'"><button type="button" class="btn red" @click="changeOptDisplay(item)" :disabled="editauth.option.isauth===false">숨김</button></td>
                                                <td class="right"><input type="text" class="right" v-model="item.marketprice" @input="changeMarketprice(item)" maxlength="11" :disabled="!iscopy && isPartner && editauth.marketprice.isauth===false"></td>
                                                <td class="right"><input type="text" class="right" v-model="item.price" @input="changePrice(item)" maxlength="11" :disabled="!iscopy && isPartner && editauth.price.isauth===false"></td>
                                                <td><input type="text" class="center" v-model="item.commrate" maxlength="5" @change="chgInfo.chgOpt='T'" 
                                                        :disabled="isPartner || (!iscopy && isPartner && editauth.commrate.isauth===false)" @input="checkInputRate(item, 'commrate', $event.target.value)"></td>
                                                <td><input type="text" class="center" v-model="item.stockcnt" maxlength="11" @change="chgInfo.chgOpt='T'" 
                                                        :disabled="!iscopy && isPartner && editauth.stockcnt.isauth===false" oninput="this.value = this.value.replace(/(^0[0-9]|[^0-9])/gi, '');" ></td>
                                                <!-- <td class="left"><input type="text" v-model="item.goodsname" maxlength="250" @change="chgInfo.chgOpt='T'"></td> -->
                                                <!-- <td>
                                                    <select v-model="item.colortype" @change="chgInfo.chgOpt='T'">
                                                        <option value="">검색컬러 선택</option>
                                                        <option v-for="item in commonCode.colortype" :key="item.cmcode" :value="item.cmcode">{{item.codename}}</option>
                                                    </select>
                                                </td> -->
                                                <td v-for="(item, itemIdx) in optionInfo.optionItemList" :key="itemIdx">
                                                    <input type="text" class="center" :ref="'optionnm'+item.sort+'_'+index" maxlength="100" @change="chgInfo.chgOpt='T'" :disabled="editauth.option.isauth===false">
                                                    <input type="hidden" class="center" :ref="'optionno'+item.sort+'_'+index">
                                                </td>
                                            </tr>
                                        </tbody>
                                        <tbody v-else>
                                            <tr><td :colspan="8+optionInfo.optionItemList.length">조회 결과가 존재하지 않습니다.</td></tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                            <!-- // 옵션정보(위탁) -->

                            <!-- 옵션정보(직매입) -->
                            <div class="blue-box" v-if="basicInfo.ispbgoods=='T'">
                                <ul>
                                    <li>정상가, 판매가, 상품명, 안전재고는 필수 입력 항목입니다.</li>
                                    <li>필수 항목 입력 후 Original상품코드와 매핑(연결)을 해주셔야 정상적으로 등록이 가능합니다.</li>
                                    <!-- <li>상품정렬순서는 상품명 앞 01, 02, 03 순으로 붙이면 순차적으로 정렬됩니다.</li> -->
                                    <li>단품은 그리드에서 행 추가 후 등록하시면 편리합니다.</li>
                                </ul>
                                <button type="button" class="btn big blue mt20" @click="downloadExcelTemplate('PbGoodsOptionTemplate.xlsx')">양식 다운로드</button>
                                <div class="mt10">
                                    <input type="file" ref="optionExcelFile" @change="handleFileUpload('optionExcelFile')" hidden
                                        accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" />
                                    <input type="text" ref="optionExcelFileName" readonly>
                                    <button type="button" class="btn blue-line ml3" @click="fileAttach('optionExcelFile')">파일찾기</button>
                                    <button type="button" class="btn blue-line" @click="readExcelFile('pbOptionExcelFile')">일괄등록</button>
                                </div>
                            </div>
                            <div style="margin: 20px;" v-if="basicInfo.ispbgoods=='T'">
                                <div class="caption-group clearfix">
                                    <div class="total-group fl">
                                        <span class="total">전체 <strong>{{ optionInfo.optionList.length }}</strong>건</span>
                                    </div>
                                    <div class="btn-group fr">
                                        <button type="button" class="btn blue-line ml3" @click="setOptDisplay('T')">노출</button>
                                        <button type="button" class="btn red-line" @click="setOptDisplay('F')">숨김</button>
                                        <!-- <select v-model="optionInfo.totcolortype" v-if="isWrite">
                                            <option value="">검색컬러 선택</option>
                                            <option v-for="item in commonCode.colortype" :key="item.cmcode" :value="item.cmcode">{{item.codename}}</option>
                                        </select> -->
                                        <!-- <button type="button" class="btn blue-line ml3" @click="setAllColorType">일괄지정</button> -->
                                        <button type="button" class="btn blue-line" @click="addOption">행 추가</button>
                                        <button type="button" class="btn red-line" @click="removeOption">행 삭제</button>
                                        <button type="button" class="btn blue-line" @click="openOtionItemPopup">항목관리</button>
                                    </div>
                                </div>
                                <div class="scroll-y" style="width: 100%; max-height: 500px;">
                                    <table cellpadding="0" cellspacing="0" class="data-tb align-c" style="margin-left: 0;">
                                        <colgroup>
                                            <col width="3%" /><!-- checkbox -->
                                            <col width="8%" /><!-- 옵션코드 -->
                                            <col width="6%" /><!-- 대표상품 -->
                                            <col width="6%" /><!-- 옵션상태 -->
                                            <col width="8%" /><!-- 정상가 -->
                                            <col width="8%" /><!-- 판매가 -->
                                            <col width="6%" /><!-- 마진율 -->
                                            <!-- <col width="" /> -->
                                            <!-- <col width="8%" /> -->
                                            <col width="" v-for="(item, itemIdx) in optionInfo.optionItemList" :key="itemIdx" />
                                            <col width="6%" /><!-- 안전재고 -->
                                            <col width="6%" /><!-- 상품조회 -->
                                        </colgroup>
                                        <thead>
                                            <tr>
                                                <th><input type="checkbox" id="chkalloptD" v-model="optionInfo.isallchkopt" @change="checkAllOptionList($event.target.checked)" true-value="T" false-value="F" /></th>
                                                <th>옵션코드</th>
                                                <th>대표상품</th>
                                                <th>옵션상태</th>
                                                <th>정상가</th>
                                                <th>판매가</th>
                                                <th>마진율(%)</th>
                                                <!-- <th>상품명</th> -->
                                                <!-- <th>검색컬러지정</th> -->
                                                <th class="bg-yellow" v-for="(item, itemIdx) in optionInfo.optionItemList" :key="itemIdx">{{ item.optionname }}</th>
                                                <th>안전재고</th>
                                                <th>상품조회</th>
                                            </tr>
                                        </thead>
                                        <tbody v-if="optionInfo.optionList.length > 0">
                                            <tr v-for="(item, index) in optionInfo.optionList" :key="index">
                                                <td><input type="checkbox" :id="'chkoptD_'+item.optionno" v-model="item.ischecked" @change="checkOptionList($event.target.checked)"/></td>
                                                <td>{{ item.optioncode }}</td>
                                                <td><input type="radio" class="circle" name="ismaingoodsD" :checked="item.ismaingoods=='T'" @change="setIsMainGoods(item)"/></td>
                                                <td v-if="item.isoptdisplay == 'T'"><button type="button" class="btn blue" @click="changeOptDisplay(item)">노출</button></td>
                                                <td v-if="item.isoptdisplay == 'F'"><button type="button" class="btn red" @click="changeOptDisplay(item)">숨김</button></td>
                                                <td class="right"><input type="text" class="right" v-model="item.marketprice" @input="changeMarketprice(item)" maxlength="11"></td>
                                                <td class="right"><input type="text" class="right" v-model="item.price" @input="changePrice(item)" maxlength="11"></td>
                                                <td><input type="text" class="center" v-model="item.marginrate" readonly></td>
                                                <!-- <td class="left"><input type="text" v-model="item.goodsname" maxlength="250" @change="chgInfo.chgOpt='T'"></td> -->
                                                <!-- <td>
                                                    <select v-model="item.colortype" @change="chgInfo.chgOpt='T'">
                                                        <option value="">검색컬러 선택</option>
                                                        <option v-for="item in commonCode.colortype" :key="item.cmcode" :value="item.cmcode">{{item.codename}}</option>
                                                    </select>
                                                </td> -->
                                                <td v-for="(item, itemIdx) in optionInfo.optionItemList" :key="itemIdx">
                                                    <input type="text" class="center" :ref="'optionnm'+item.sort+'_'+index" maxlength="100" @change="chgInfo.chgOpt='T'">
                                                    <input type="hidden" class="center" :ref="'optionno'+item.sort+'_'+index">
                                                </td>
                                                <td><input type="text" class="center" v-model="item.safestockcnt" maxlength="11" oninput="this.value = this.value.replace(/(^0[0-9]|[^0-9])/gi, '');" @change="chgInfo.chgOpt='T'"></td>
                                                <td v-if="item.linkedcnt > 0"><a href="javascript:void(0);" class="link" @click="openSearchOrgCodePopup(item)">연결({{ item.linkedcnt }})</a></td>
                                                <td v-else><button type="button" class="btn blue-line" @click="openSearchOrgCodePopup(item)">연결</button></td>
                                            </tr>
                                        </tbody>
                                        <tbody v-else>
                                            <tr><td :colspan="8+optionInfo.optionItemList.length">조회 결과가 존재하지 않습니다.</td></tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                            <!-- // 옵션정보(직매입) -->
                        </div>
                    </div>
                    <!-- //옵션정보 영역 -->

                    <!-- 이미지정보 영역 -->
                    <div class="boxing" id="goodsTab4">
                        <div class="boxing-title" :class="{closed: !tabObject.goodsTab4.isOpen}" >
                            이미지 정보
                            <i class="arrcodi" @click="onToggle(tabObject.goodsTab4)"></i>
                        </div>
                        <div class="form-area" :style="{display: tabObject.goodsTab4.isOpen? 'block':'none'}">
                            <div style="margin: 20px;">
                                <table cellpadding="0" cellspacing="0" class="gray-tb">
                                    <colgroup>
                                        <col width="170px">
                                        <col width="">
                                    </colgroup>
                                    <tbody>
                                        <tr>
                                            <th>아이콘설정</th>
                                            <td>
                                                <div class="check-wrap">
                                                    <input type="checkbox" id="chkAllGicontype" v-model="imageInfo.isallgicontype" true-value="T" false-value="F" @change="checkAllGicontype">
                                                    <label for="chkAllGicontype">전체</label>
                                                </div>
                                                <div class="check-wrap" v-for="item in commonCode.gicontype" :key="item.cmcode">
                                                    <input type="checkbox" :id="'gicontype_'+item.cmcode" v-model="imageInfo.mugicontypeArr" :true-value="[]" :value="item.cmcode">
                                                    <label :for="'gicontype_'+item.cmcode">{{ item.codename }}</label>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <th>대표이미지(PC)<i class="essential"></i></th>
                                            <td ref="repreimgtd">
                                                <div class="img-with-text" style="width: 202px;">
                                                    <div class="img-thumb size200" :class="{'no-image': $util.isNull(files['pcrepreimgfile'])}">
                                                        <img :src="imgPreview['pcrepreimgfile']" alt="대표이미지(PC)"
                                                            v-if="!$util.isNull(files['pcrepreimgfile']) && files['pcrepreimgfile'].status != 'N'" >
                                                        <img :src="files['pcrepreimgfile'].fullpath" alt="대표이미지(PC)" 
                                                            v-if="!$util.isNull(files['pcrepreimgfile']) && files['pcrepreimgfile'].status == 'N'" >
                                                    </div>
                                                    <button type="button" class="btn blue-line mt10" style="width: 100%;"
                                                        v-if="$util.isNull(files['pcrepreimgfile']) && (iscopy || editauth.image.isauth)" @click="fileAttach('pcrepreimgfile')">파일 올리기</button>
                                                    <input type="file" ref="pcrepreimgfile" @change="handleFileUpload('pcrepreimgfile')" accept="image/jpeg, image/png" hidden/>
                                                    <button type="button" class="btn blue-line mt10" style="width: calc(50% - 3px);"
                                                        v-if="!$util.isNull(files['pcrepreimgfile']) && (iscopy || editauth.image.isauth)" @click="fileAttach('pcrepreimgfile')">변경</button>
                                                    <button type="button" class="btn red-line mt10" style="width: calc(50% - 4px);"
                                                        v-if="!$util.isNull(files['pcrepreimgfile']) && (iscopy || editauth.image.isauth)" @click="removeFile('pcrepreimgfile')">삭제</button>
                                                </div>
                                                <div class="img-with-text text">
                                                    <p class="txt-orange"><i class="icon-alert"></i>판매상품의 대표 이미지입니다. 보기 쉬운 간결한 이미지를 활용해 주세요.</p>
                                                    <p class="txt-orange"><i class="icon-alert"></i>사이즈: 720*720 / 용량: 2MB 이하 / 파일 : JPG, JPEG, PNG</p>
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
                                                            v-if="$util.isNull(files['addimgfilelist'][n]) && (iscopy || editauth.image.isauth)" @click="fileAttach('addimgFile')">파일 올리기</button>
                                                        <button type="button" class="btn blue-line mt10" style="width: calc(50% - 3px);"
                                                            v-if="!$util.isNull(files['addimgfilelist'][n]) && (iscopy ||editauth.image.isauth)" @click="fileAttach('changeaddimgfile'+n)">변경</button>
                                                        <button type="button" class="btn red-line mt10" style="width: calc(50% - 4px);"
                                                            v-if="!$util.isNull(files['addimgfilelist'][n]) && (iscopy || editauth.image.isauth)" @click="removeFile('addimgFile', n)">삭제</button>
                                                        <input type="file" :id="'changeaddimgfile'+n" :ref="'changeaddimgfile'+n" @change="handleFileUpload('changeaddimgfile', $event.target, n)" 
                                                            accept="image/jpeg, image/png" hidden/>
                                                    </div>
                                                </div>
                                                <div class="mt20">
                                                    <div class="img-with-text" style="width: 202px;" v-for="(index, n) in 2" :key="index+5">
                                                        <div class="img-thumb size200 no-image" v-if="$util.isNull(files['addimgfilelist'][n+5])"></div>
                                                        <div class="img-thumb size200" v-else>
                                                            <img :src="imgPreview['addimgfilelist'][n+5]" :alt="'추가이미지'+(n+5)" v-if="files['addimgfilelist'][n+5].status != 'N'">
                                                            <img :src="files['addimgfilelist'][n+5].fullpath" :alt="'추가이미지'+(n+5)" v-else>
                                                        </div>
                                                        <button type="button" class="btn blue-line mt10" style="width: 100%;"
                                                            v-if="$util.isNull(files['addimgfilelist'][n+5]) && (iscopy || editauth.image.isauth)" @click="fileAttach('addimgFile')">파일 올리기</button>
                                                        <button type="button" class="btn blue-line mt10" style="width: calc(50% - 3px);"
                                                            v-if="!$util.isNull(files['addimgfilelist'][n+5]) && (iscopy || editauth.image.isauth)" @click="fileAttach('changeaddimgfile'+(n+5))">변경</button>
                                                        <button type="button" class="btn red-line mt10" style="width: calc(50% - 4px);"
                                                            v-if="!$util.isNull(files['addimgfilelist'][n+5]) && (iscopy || editauth.image.isauth)" @click="removeFile('addimgFile', n+5)">삭제</button>
                                                        <input type="file" :id="'changeaddimgfile'+(n+5)" :ref="'changeaddimgfile'+(n+5)" @change="handleFileUpload('changeaddimgfile', $event.target, n+5)" 
                                                            accept="image/jpeg, image/png" hidden/>
                                                    </div>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <th>대표이미지(모바일)<i class="essential"></i></th>
                                            <td>
                                                <div class="mb10">
                                                    <input type="checkbox" id="typical-sameD" @change="setSameAsPcepreImg" v-model="imageInfo.issamepcimg" :disabled="!iscopy && isPartner && !editauth.image.isauth">
                                                    <label for="typical-sameD">PC 대표 이미지와 동일</label>
                                                </div>
                                                <div class="img-with-text" style="width: 202px;">
                                                    <div class="img-thumb size200" :class="{'no-image': $util.isNull(files['morepreimgfile'])}">
                                                        <img :src="imgPreview['morepreimgfile']" alt="대표이미지(모바일)" class="fit"
                                                            v-if="!$util.isNull(files['morepreimgfile']) && files['morepreimgfile'].status != 'N'">
                                                        <img :src="files['morepreimgfile'].fullpath" alt="대표이미지(모바일)" class="fit"
                                                            v-if="!$util.isNull(files['morepreimgfile']) && files['morepreimgfile'].status == 'N'">
                                                    </div>
                                                    <button type="button" class="btn blue-line mt10" style="width: 100%;"
                                                        v-if="$util.isNull(files['morepreimgfile']) && (iscopy || editauth.image.isauth)" @click="fileAttach('morepreimgfile')">파일 올리기</button>
                                                    <input type="file" ref="morepreimgfile" @change="handleFileUpload('morepreimgfile')" accept="image/jpeg, image/png" hidden/>
                                                    <button type="button" class="btn blue-line mt10" style="width: calc(50% - 3px);"
                                                        v-if="!$util.isNull(files['morepreimgfile']) && (iscopy || editauth.image.isauth)" @click="fileAttach('morepreimgfile')">변경</button>
                                                    <button type="button" class="btn red-line mt10" style="width: calc(50% - 4px);"
                                                        v-if="!$util.isNull(files['morepreimgfile']) && (iscopy || editauth.image.isauth)" @click="removeFile('morepreimgfile')">삭제</button>
                                                </div>
                                                <div class="img-with-text text">
                                                    <p class="txt-orange"><i class="icon-alert"></i>모바일 리스팅 및 와이드형 화면에 노출되는 이미지를 업로드 해 주세요.</p>
                                                    <p class="txt-orange"><i class="icon-alert"></i>사이즈: 720*720 / 용량: 2MB 이하 / 파일 : JPG, JPEG, PNG</p>
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
                                                    <CommonEditor ref="noticeeditor" style-object="height:'200px';"/>
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
                                                    <CommonEditor ref="introeditor" style-object="height:'200px';"/>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <th style="width:170px;">PC용 상품상세설명<i class="essential"></i></th>
                                            <td style="calc(100% + 170px);">
                                                <div>
                                                    <CommonEditor ref="pceditor" style-object="height:'200px';" :disable="isPartner && editauth.pccontent.isauth===false"/>
                                                </div>
                                            </td>
                                        </tr>
                                        <legend>모바일용 상품상세설명 수정권한</legend>
                                        <tr>
                                            <th style="width:170px;">모바일용 상품상세설명<i class="essential"></i></th>
                                            <td style="calc(100% + 170px);">
                                                <div class="mb10">
                                                    <!-- <input type="checkbox" id="pc-sameD" @change="setSameAsPcDetailContrent" v-model="imageInfo.issamepccont" :disabled="!iscopy && isPartner && !editauth.image.isauth">
                                                    <label for="pc-same">PC용 상품상세설명과 동일</label> -->
                                                    <button type="button" class="btn blue-line" @click="setSameAsPcDetailContrent" :disabled="!iscopy && isPartner && !editauth.image.isauth">PC용 상품상세설명 복사</button>
                                                </div>
                                                <div class="mt10">
                                                    <CommonEditor ref="mobileeditor" style-object="height:'200px';" :disable="isPartner && editauth.mocontent.isauth===false"/>
                                                </div>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                    <!-- //이미지정보 영역 -->

                    <!-- 배송정보 영역 -->
                    <div class="boxing" id="goodsTab5">
                        <div class="boxing-title" :class="{closed: !tabObject.goodsTab5.isOpen}">
                            배송 정보
                            <i class="arrcodi" @click="onToggle(tabObject.goodsTab5)"></i>
                        </div>
                        <div class="form-area" :style="{display: tabObject.goodsTab5.isOpen? 'block':'none'}">
                            <fieldset :disabled="!iscopy && isPartner && editauth.delividx.isauth===false">
                                <legend>배송템플릿 수정권한</legend>
                                <dl>
                                    <dt>배송템플릿 선택</dt>
                                    <dd>
                                        <select style="width: 400px;" v-model="deliveryInfo.delividx">
                                            <option value="">배송템플릿 선택</option>
                                            <option v-for="item in deliveryInfo.delivTempList" :key="item.delividx" :value="item.delividx">[{{item.iscombdelivname}}]{{ item.delivbname }}</option>
                                        </select>
                                        <button type="button" class="btn blue" @click="openDelivTempListPopup">배송템플릿</button>
                                        <span class="txt-orange"><i class="icon-alert"></i>자주 쓰는 배송정보는 템플릿으로 관리하시면 편리합니다.</span>
                                    </dd>
                                </dl>
                            </fieldset>
                            <!-- <dl>
                                <dt>배송유형</dt>
                                <dd class="clearfix">
                                    <div class="radio_wrap wide">
                                        <input type="radio" name="iscombdelivD" id="iscombdelivDF" v-model="deliveryInfo.iscombdeliv" value="F"/>
                                        <label for="iscombdelivDF">개별배송</label>
                                        <input type="radio" name="iscombdelivD" id="iscombdelivDT" v-model="deliveryInfo.iscombdeliv" value="T"/>
                                        <label for="iscombdelivDT">묶음배송</label>
                                    </div>
                                    <span class="txt-orange"><i class="icon-alert"></i>묶음배송은 배송관리코드가 동일한 상품만 적용이 가능합니다.</span>
                                </dd>
                            </dl> -->
                            <dl v-if="!$util.isNull(deliveryInfo.delivTemp.delivschtype)">
                                <dt>배송안내</dt>
                                <dd v-if="deliveryInfo.delivTemp.delivschtype!='DSC001' && !$util.isNull(deliveryInfo.delivTemp.delivschtype)"><input type="text" value="" style="width: 100%;" placeholder="배송안내문구를 입력하세요!" v-model="basicInfo.delivinfo" :maxlength="15"><span class="txt-orange"><i class="icon-alert"></i>최대 15자까지만 입력이 가능합니다.</span></dd>
                                <dd v-if="deliveryInfo.delivTemp.delivschtype=='DSC001'"><input type="text" value="주문확인 후 최대 1~4일 내 배송완료" style="width: 100%;" v-model="basicInfo.delivinfo" readonly></dd>
                             </dl>
                            <div class="col2" style="padding: 0 10px 10px;">
                                <div class="left" style="padding: 0 10px;">
                                    <div class="bar-title small">기본정보</div>
                                    <table cellpadding="0" cellspacing="0" class="gray-tb">
                                        <colgroup>
                                            <col width="170px">
                                            <col width="">
                                        </colgroup>
                                        <tbody>
                                            <tr>
                                                <th>배송유형</th>
                                                <td>{{ deliveryInfo.delivTemp.iscombdelivname }}</td>
                                            </tr>
                                            <tr>
                                                <th>배송비</th>
                                                <td>
                                                    {{ deliveryInfo.delivTemp.delivfaretypename }} 
                                                    {{ deliveryInfo.delivTemp.delivfaretype==$store.getters['ADMIN'].DELIV_FARE_DCT002? deliveryInfo.delivTemp.delivfare + '원' : '' }}
                                                    {{ deliveryInfo.delivTemp.delivfaretype==$store.getters['ADMIN'].DELIV_FARE_DCT003? 
                                                            deliveryInfo.delivTemp.delivfare + '원 ('+deliveryInfo.delivTemp.delivfreefare + '원 이상 무료)' : '' }}
                                                </td>
                                            </tr>
                                            <tr>
                                                <th>배송방법</th>
                                                <td>{{ deliveryInfo.delivTemp.delivtypename }}</td>
                                            </tr>
                                            <tr>
                                                <th>택배사</th>
                                                <td>{{ deliveryInfo.delivTemp.logistypename }}</td>
                                            </tr>
                                            <tr>
                                                <th>배송일정</th>
                                                <td>
                                                    {{ deliveryInfo.delivTemp.delivschtypename }}
                                                    <!-- {{ deliveryInfo.delivTemp.delivschtype == 'DSC001'? '(주문확인 후 최대 1~4일 내 배송완료)':'' }}
                                                    {{ deliveryInfo.delivTemp.delivschtype == 'DSC002'? '(배송템플릿 선택 후 직접 입력)':'' }}
                                                    {{ deliveryInfo.delivTemp.delivschtype == 'DSC003'? '(배송템플릿 선택 후 직접 입력)':'' }}
                                                    {{ deliveryInfo.delivTemp.delivschtype == 'DSC004'? '(배송템플릿 선택 후 직접 입력)':'' }}
                                                    {{ deliveryInfo.delivTemp.delivschtype == 'DSC005'? '(주문확인 후 최대 7~14일 내 배송완료)':'' }} -->
                                                </td>
                                            </tr>
                                            <tr>
                                                <th>배송가능지역</th>
                                                <td>{{ deliveryInfo.delivTemp.nationdelivconts }}</td>
                                            </tr>
                                            <tr>
                                                <th>도서산간 추가배송비(편도)</th>
                                                <td>{{ $util.isNull(deliveryInfo.delivTemp.chejufare)? '':'제주 '+deliveryInfo.delivTemp.chejufare+'원' }}/
                                                    {{ $util.isNull(deliveryInfo.delivTemp.isolfare)? '':'도서산간 '+deliveryInfo.delivTemp.isolfare+'원' }}
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                                <div class="right" style="padding: 0 10px;">
                                    <div class="bar-title small">교환/반품</div>
                                    <table cellpadding="0" cellspacing="0" class="gray-tb">
                                        <colgroup>
                                            <col width="170px">
                                            <col width="">
                                        </colgroup>
                                        <tbody>
                                            <tr>
                                                <th>반품택배사</th>
                                                <td>{{ deliveryInfo.delivTemp.rtnlogistypename }}</td>
                                            </tr>
                                            <tr>
                                                <th>교환 배송비</th>
                                                <td>
                                                    <span>편도 <input type="text" class="short right" readonly v-model="deliveryInfo.delivTemp.exowfare">원</span>
                                                    <span class="left-bar">왕복 <input type="text" class="short right" readonly v-model="deliveryInfo.delivTemp.exrtnfare">원</span>
                                                </td>
                                            </tr>
                                            <tr>
                                                <th>반품 배송비</th>
                                                <td>
                                                    <span>편도 <input type="text" class="short right" readonly v-model="deliveryInfo.delivTemp.rfowfare">원</span>
                                                    <span class="left-bar">왕복 <input type="text" class="short right" readonly v-model="deliveryInfo.delivTemp.rfrtnfare">원</span>
                                                </td>
                                            </tr>
                                            <tr>
                                                <th>출고지 주소</th>
                                                <td>
                                                    <input type="text" class="dpb short" readonly v-model="deliveryInfo.delivTemp.relpost">
                                                    <input type="text" class="dpb" style="width: 100%;" readonly v-model="deliveryInfo.delivTemp.reladdr">
                                                    <input type="text" class="dpb" style="width: 100%;" readonly v-model="deliveryInfo.delivTemp.reladdrdetail">
                                                    <span class="small-txt">
                                                        [{{ $util.isNull(deliveryInfo.delivTemp.reladdr)? ' ':'도로명' }}] 
                                                        {{ deliveryInfo.delivTemp.reladdr }} {{ deliveryInfo.delivTemp.reladdrdetail }}
                                                    </span>
                                                </td>
                                            </tr>
                                            <tr>
                                                <th>교환/반품 주소</th>
                                                <td>
                                                    <input type="text" class="dpb short" readonly v-model="deliveryInfo.delivTemp.rfpost">
                                                    <input type="text" class="dpb" style="width: 100%;" readonly v-model="deliveryInfo.delivTemp.rfaddr">
                                                    <input type="text" class="dpb" style="width: 100%;" readonly v-model="deliveryInfo.delivTemp.rfaddrdetail">
                                                    <span class="small-txt">
                                                        [{{ $util.isNull(deliveryInfo.delivTemp.rfaddr)? ' ':'도로명' }}] 
                                                        {{ deliveryInfo.delivTemp.rfaddr }} {{ deliveryInfo.delivTemp.rfaddrdetail }}
                                                    </span>
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- //배송정보 영역 -->

                    <!-- 상품정보고시 영역 -->
                    <div class="boxing" id="goodsTab6">
                        <div class="boxing-title" :class="{closed: !tabObject.goodsTab6.isOpen}" >
                            상품정보고시
                            <i class="arrcodi" @click="onToggle(tabObject.goodsTab6)"></i>
                        </div>
                        <div class="form-area" :style="{display: tabObject.goodsTab6.isOpen? 'block':'none'}">
                            <div style="margin: 20px;">
                                <div class="bar-title">상품정보고시</div>
                                <fieldset :disabled="!iscopy && isPartner && editauth.notify.isauth===false">
                                    <legend>상품정보고시 수정권한</legend>
                                    <table cellpadding="0" cellspacing="0" class="gray-tb">
                                        <colgroup>
                                            <col width="170px">
                                            <col width="">
                                        </colgroup>
                                        <tbody>
                                            <tr>
                                                <th>상품정보고시<i class="essential"></i></th>
                                                <td>
                                                    <select style="width: 250px;" v-model="notifyInfo.notifytplidx" @change="getNotifyTempItemList">
                                                        <option value="">상품군 선택</option>
                                                        <option v-for="item in notifyInfo.notifyTempList" :key="item.idx" :value="item.idx">{{ item.title }}</option>
                                                    </select>
                                                    <span class="txt-orange ml3"><i class="icon-alert"></i>상품정보 고시에 따른 세부 사항을 정확히 기재해 주셔야 합니다.</span>
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                    <dl v-if="notifyInfo.notifyTempItemList.length > 0">
                                        <dt>{{ notifyInfo.notifytpltitle }}</dt>
                                        <dd>
                                            <input type="checkbox" id="alltxtD" @click="setAllRefDetail($event.target.checked)" v-model="notifyInfo.chkRef"><label for="alltxtD">전체 "상품상세설명 참고"로 표기</label>
                                        </dd>
                                    </dl>
                                    <table cellpadding="0" cellspacing="0" class="gray-tb">
                                        <colgroup>
                                            <col width="170px">
                                            <col width="">
                                            <!-- <col width="70px"> -->
                                        </colgroup>
                                        <tbody>
                                            <tr v-for="(item) in notifyInfo.notifyTempItemList" :key="item.notifyitemidx">
                                                <th>{{ item.notifyname }}</th>
                                                <td>
                                                    <textarea placeholder="세부사항을 입력하세요!" v-model="item.notifydata" maxlength="1000"></textarea>
                                                    <div style="text-align: right;">({{ $util.isNull(item.notifydata)? '0':item.notifydata.length }}/1000)</div>
                                                </td>
                                                <!-- <td>
                                                    <button type="button" class="add mg0" @click="addNotifyItem(index)"></button>
                                                    <button type="button" class="del" @click="removeNotifyItem(index)" v-if="index > 0"></button>
                                                </td> -->
                                            </tr>
                                        </tbody>
                                    </table>
                                </fieldset>
                                <div class="clearfix">
                                    <div class="bar-title fl">KC인증</div>
                                    <div class="fr"><p class="txt-orange"><i class="icon-alert"></i>사실과 다른 인증정보를 입력하실 경우, 관련법에 의하여 처벌받으실 수 있으니, 정확한 정보만 입력해주시기 바랍니다.</p></div>
                                </div>
                                <table cellpadding="0" cellspacing="0" class="gray-tb">
                                    <colgroup>
                                        <col width="170px">
                                        <col width="">
                                    </colgroup>
                                    <tbody>
                                        <tr>
                                            <th>대상여부</th>
                                            <td>
                                                <div class="radio_wrap">
                                                    <div v-for="item in commonCode.kcdivtype" :key="item.cmcode">
                                                        <input type="radio" name="kcdivtypeD" :id="'kcdivtypeD_'+item.cmcode" :value="item.cmcode" v-model="notifyInfo.kcdivtype"/>
                                                        <label :for="'kcdivtypeD_'+item.cmcode">{{ item.codename }}</label>
                                                    </div>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr v-if="notifyInfo.kcdivtype == $store.getters['ADMIN'].KC_DIV_TYPE_KDT002" >
                                            <th>인증선택</th>
                                            <td>
                                                <div class="dpb" v-for="(item, index) in notifyInfo.kccertlist" :key="item.idx">
                                                    <input type="text" class="ml3" placeholder="인증번호입력(-포함)" v-model="item.kccertno" maxlength="30" @input="item.iscert=''">
                                                    <button type="button" class="btn blue-line ml3" @click="kcCertification(item)">인증확인</button>
                                                    <button type="button" class="add" @click="addKccertItem(index)"></button>
                                                    <button type="button" class="del" @click="removeKccertItem(index)" v-if="notifyInfo.kccertlist.length>1"></button>
                                                    <span class="left-bar txt-blue" v-show="item.iscert=='T'">적합</span>
                                                    <span class="left-bar txt-red" v-show="item.iscert=='F'">부 적합</span>
                                                    <span class="left-bar txt-orange" v-show="$util.isNull(item.iscert)">미 인증</span>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr v-if="notifyInfo.kcdivtype == $store.getters['ADMIN'].KC_DIV_TYPE_KDT002" >
                                            <th>파일첨부</th>
                                            <td>
                                                <button type="button" class="btn blue-line" @click="fileAttach('kcFile')">파일첨부</button>
                                                <div class="dpib" v-for="(item, index) in files.kcfilelist" :key="'kcFile'+index">
                                                    <a href="javascript:void(0);" class="file-link"
                                                        :style="item.isimage? '':'color: #666 !important; text-decoration: none !important'" @click="viewKcFile(item, index)">
                                                        {{ item.status == 'N'? item.imgforiname : item.file.name }}
                                                    </a>
                                                    <button type="button" class="file-del" @click="removeFile('kcFile', index)"></button>
                                                </div>
                                                <input type="file" id="input-fileD" accept="image/jpeg, image/png, .pdf"
                                                    ref="kcFile" @change="handleFileUpload('kcFile', $event.target)" hidden multiple />
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                    <!-- //상품정보고시 영역 -->

                    <!-- 추가정보 영역 -->
                    <div class="boxing" id="goodsTab7">
                        <div class="boxing-title" :class="{closed: !tabObject.goodsTab7.isOpen}">
                            추가정보
                            <i class="arrcodi" @click="onToggle(tabObject.goodsTab7)"></i>
                        </div>
                        <div class="form-area" :style="{display: tabObject.goodsTab7.isOpen? 'block':'none'}">
                            <div style="margin: 20px;">
                                <table cellpadding="0" cellspacing="0" class="gray-tb">
                                    <colgroup>
                                        <col width="170px">
                                        <col width="">
                                    </colgroup>
                                    <tbody>
                                        <tr>
                                            <th>EP연동여부<i class="essential"></i></th>
                                            <td>
                                                <div class="radio_wrap wide">
                                                    <input type="radio" name="isepif" id="isepifT" v-model="etcInfo.isepif" value="T"/>
                                                    <label for="isepifT">연동</label>
                                                    <input type="radio" name="isepif" id="isepifF" v-model="etcInfo.isepif" value="F"/>
                                                    <label for="isepifF">미연동</label>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <th style="width:170px;">A/S 보증기간<i class="essential"></i></th>
                                            <td>
                                                <input type="text" style="width: 70px;" v-model="etcInfo.aswarmonth" maxlength="3">
                                                <span class="ml3">개월</span>
                                            </td>
                                        </tr>
                                        <tr>
                                            <th style="width:170px;">A/S 안내문구</th>
                                            <td><textarea placeholder="A/S 안내문구를 입력하세요." v-model="etcInfo.asnotice" maxlength="500"></textarea></td>
                                        </tr>
                                        <tr v-if="!isPartner">
                                            <th style="width:170px;">회원유형 별 노출여부</th>
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
                                        <tr v-if="!isPartner">
                                            <th style="width:170px;">리뷰 공개여부</th>
                                            <td>
                                                <div class="radio_wrap wide">
                                                    <input type="radio" name="isopenreviewD" id="isopenreviewDT" v-model="etcInfo.isopenreview" value="T"/>
                                                    <label for="isopenreviewDT">공개</label>
                                                    <input type="radio" name="isopenreviewD" id="isopenreviewDF" v-model="etcInfo.isopenreview" value="F"/>
                                                    <label for="isopenreviewDF">비공개</label>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr v-if="!isPartner">
                                            <th style="width:170px;">리뷰묶어보기 상품</th>
                                            <td>
                                                <div class="caption-group clearfix">
                                                    <div class="total-group fl">
                                                        <span class="total">리뷰묶어보기 상품목록</span>
                                                    </div>
                                                    <div class="btn-group fr">
                                                        <button type="button" class="btn blue-line" @click="openGoodsAdditionPopup('review')">상품추가</button>
                                                        <button type="button" class="btn red-line" @click="removeGoodsAddition('review')">삭제</button>
                                                    </div>
                                                </div>
                                                <div class="scroll-y" style="width: 100%; max-height: 500px; margin-bottom: 0;">
                                                    <table cellpadding="0" cellspacing="0" class="data-tb align-c">
                                                        <colgroup>
                                                            <col width="3%" /><!-- checkbox -->
                                                            <col width="4%" /><!-- No -->
                                                            <col width="10%" /><!-- 상품코드 -->
                                                            <col width="62px" /><!-- 이미지 -->
                                                            <col width="" /><!-- 상품명 -->
                                                            <col width="7%" /><!-- 판매가 -->
                                                            <col width="7%" /><!-- 판매상태 -->
                                                            <col width="8%" /><!-- 등록일자 -->
                                                        </colgroup>
                                                        <thead>
                                                            <tr>
                                                                <th><input type="checkbox" id="chkallgoodsgrpD" v-model="etcInfo.isallchkgoodsgrp" @change="checkAllGoodsGrpList($event.target.checked)" true-value="T" false-value="F" /></th>
                                                                <th>No</th>
                                                                <th>상품코드</th>
                                                                <th colspan="2">상품명</th>
                                                                <th>판매가</th>
                                                                <th>판매상태</th>
                                                                <th>등록일자</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody v-if="etcInfo.goodsGrpList.length > 0">
                                                            <tr v-for="(item, index) in etcInfo.goodsGrpList" :key="index">
                                                                <td><input type="checkbox" :id="'goodsgrpaddD_'+index" v-model="item.ischecked" @change="checkGoodsGrpList($event.target.checked)"/></td>
                                                                <td>{{ index+1 }}</td>
                                                                <td>{{ item.goodsno }}</td>
                                                                <td>
                                                                    <div class="img-thumb size60" :class="{'no-image': $util.isNull(item.fullpath)}">
                                                                        <img :src="item.fullpath" v-if="!$util.isNull(item.fullpath)">
                                                                    </div>
                                                                </td>
                                                                <td class="left no-left">
                                                                    <span class="small-txt">{{ item.fullcategoryname }}</span>
                                                                    <p class="mg0">{{ item.goodsname }}</p>
                                                                </td>
                                                                <td>{{ $util.maskComma(item.price) }}</td>
                                                                <td>{{ item.goodsdivtypename }}</td>
                                                                <td>{{ item.regdate }}</td>
                                                            </tr>
                                                        </tbody>
                                                        <tbody v-else>
                                                            <tr><td colspan="8">조회 결과가 존재하지 않습니다.</td></tr>
                                                        </tbody>
                                                    </table>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <th style="width:170px;">추가상품</th>
                                            <td>
                                                <div class="caption-group clearfix">
                                                    <div class="total-group fl">
                                                        <span class="total">추가상품목록</span>
                                                    </div>
                                                    <div class="btn-group fr">
                                                        <button type="button" class="btn blue-line" @click="openGoodsAdditionPopup('addgoods')">상품추가</button>
                                                        <button type="button" class="btn red-line" @click="removeGoodsAddition('addgoods')">삭제</button>
                                                    </div>
                                                </div>
                                                <div class="scroll-y" style="width: 100%; max-height: 500px; margin-bottom: 0;">
                                                    <table cellpadding="0" cellspacing="0" class="data-tb align-c">
                                                        <colgroup>
                                                            <col width="3%" /><!-- checkbox -->
                                                            <col width="4%" /><!-- No -->
                                                            <col width="10%" /><!-- 상품코드 -->
                                                            <col width="62px" /><!-- 이미지 -->
                                                            <col width="" /><!-- 상품명 -->
                                                            <col width="7%" /><!-- 판매가 -->
                                                            <col width="7%" /><!-- 판매상태 -->
                                                            <col width="8%" /><!-- 등록일자 -->
                                                        </colgroup>
                                                        <thead>
                                                            <tr>
                                                                <th><input type="checkbox" id="chkallgoodsaddD" v-model="etcInfo.isallchkgoods" @change="checkAllGoodsAddList($event.target.checked)" true-value="T" false-value="F" /></th>
                                                                <th>No</th>
                                                                <th>상품코드</th>
                                                                <th colspan="2">상품명</th>
                                                                <th>판매가</th>
                                                                <th>판매상태</th>
                                                                <th>등록일자</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody v-if="etcInfo.goodsAdditionList.length > 0">
                                                            <tr v-for="(item, index) in etcInfo.goodsAdditionList" :key="index">
                                                                <td><input type="checkbox" :id="'goodsaddD_'+index" v-model="item.ischecked" @change="checkGoodsAddList($event.target.checked)"/></td>
                                                                <td>{{ index+1 }}</td>
                                                                <td>{{ item.goodsno }}</td>
                                                                <td>
                                                                    <div class="img-thumb size60" :class="{'no-image': $util.isNull(item.fullpath)}">
                                                                        <img :src="item.fullpath" v-if="!$util.isNull(item.fullpath)">
                                                                    </div>
                                                                </td>
                                                                <td class="left no-left">
                                                                    <span class="small-txt">{{ item.fullcategoryname }}</span>
                                                                    <p class="mg0">{{ item.goodsname }}</p>
                                                                </td>
                                                                <td>{{ $util.maskComma(item.price) }}</td>
                                                                <td>{{ item.goodsdivtypename }}</td>
                                                                <td>{{ item.regdate }}</td>
                                                            </tr>
                                                        </tbody>
                                                        <tbody v-else>
                                                            <tr><td colspan="8">조회 결과가 존재하지 않습니다.</td></tr>
                                                        </tbody>
                                                    </table>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <th style="width:170px;">관리자 메모</th>
                                            <td style="width:calc(100%-170px);">
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
                </div>
                <div class="btn-group" v-if="sellInfo.orggoodsselltype != $store.getters['ADMIN'].GOODS_SELL_TYPE_GST006">
                    <button type="button" class="btn big blue-line" v-if="!$util.isNull(basicInfo.goodscode) && !iscopy" @click="goPriview">미리보기</button>
                    <button type="button" class="btn big gray" v-if="!iscopy" @click="iscopy = true">복사</button>
                    <!-- <button type="button" class="btn big gray" v-if="iscopy" @click="iscopy = false">복사해제</button> -->
                    <button type="button" class="btn big blue" @click="goTemporarySave" v-if="iscopy">임시저장</button>
                    <button type="button" class="btn big blue" @click="changeGoodsAppr($store.getters['ADMIN'].GOODS_STATUS_APPROVAL)"
                        v-if="!isPartner && !iscopy && basicInfo.goodsapprtype===$store.getters['ADMIN'].GOODS_STATUS_REQ">승인</button>
                    <button type="button" class="btn big red" @click="changeGoodsAppr($store.getters['ADMIN'].GOODS_STATUS_REJECT)"
                        v-if="!isPartner && !iscopy && basicInfo.goodsapprtype===$store.getters['ADMIN'].GOODS_STATUS_REQ">반려</button>
                    <button type="button" class="btn big blue" @click="goSave" 
                        v-if="!isPartner || (isPartner && !iscopy && basicInfo.goodsapprtype === $store.getters['ADMIN'].GOODS_STATUS_APPROVAL)">저장</button>
                    <button type="button" class="btn big red" @click="goReqApprv"
                        v-if="isPartner && !iscopy && basicInfo.goodsapprtype===$store.getters['ADMIN'].GOODS_STATUS_REJECT">재승인요청</button>
                    <button type="button" class="btn big red" @click="goReqApprv"
                        v-if="isPartner && (iscopy || (!iscopy && basicInfo.goodsapprtype===$store.getters['ADMIN'].GOODS_STATUS_TEMP))">승인요청</button>
                    <button type="button" class="btn big darkgray pop-close" @click="$emit('closePopup');">닫기</button>
                </div>
                <div class="btn-group" v-if="sellInfo.orggoodsselltype == $store.getters['ADMIN'].GOODS_SELL_TYPE_GST006">
                    <button type="button" class="btn big darkgray pop-close" @click="$emit('closePopup');">닫기</button>
                </div>
            </div>
        </div>
    </div>
    <!-- /상품상세정보 팝업 -->
</template>

<script src="./GoodsDetail.js"/>

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
fieldset {
    border: 0px;
    margin: 0px;
    padding: 0px;
}
/* fieldset>tr {
    width: 100%;
    display: inline-table;
} */
fieldset[disabled] {
    pointer-events: none;
}
input[type="radio"][disabled].circle:before,
fieldset[disabled] input[type="radio"].circle:before {
    border: 1px solid #ddd !important;
    background: #eee !important;
}
input[type="radio"][disabled] .circle:checked:after,
fieldset[disabled] input[type="radio"].circle:checked:after {
    border: 3px solid #eee !important;
    background: #ddd !important;
}
legend {
    display: none;
}
</style>
