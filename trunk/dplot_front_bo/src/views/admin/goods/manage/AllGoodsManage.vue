<template>
    <div class="content m-leftmenu">
        <common-navigator></common-navigator>
        <div class="inner">
            <div class="boxing search-area">
                <dl>
                    <dt>직접검색</dt>
                    <dd>
                        <select v-model="searchData.skey">
                            <option v-for="item in searchData.skeyArr" :key="item.key" :value="item.key">{{ item.name }}</option>
                        </select>
                        <input type="text" v-model="searchData.sword" maxlength="200" @keyup.enter="searchList(1)"/>
                    </dd>
                </dl>
                <dl v-if="!isPartner">
                    <dt>판매구분</dt>
                    <dd>
                        <div class="radio_wrap wide3">
                            <input type="radio" name="ispbgoods" id="ispbgoodsAll" v-model="searchData.ispbgoods" value="" @click="searchData.dealerno=''"/>
                            <label for="ispbgoodsAll">전체</label>
                            <input type="radio" name="ispbgoods" id="ispbgoodsT" v-model="searchData.ispbgoods" value="T" @click="searchData.dealerno=''"/>
                            <label for="ispbgoodsT">직매입</label>
                            <input type="radio" name="ispbgoods" id="ispbgoodsF" v-model="searchData.ispbgoods" value="F"/>
                            <label for="ispbgoodsF">위탁</label>
                        </div>
                        <select :disabled="searchData.ispbgoods!='F'" v-model="searchData.dealerno">
                            <option value="">파트너사 전체</option>
                            <option v-for="item in partnerList" :key="item.no" :value="item.no">{{ item.name }} </option>
                        </select>
                    </dd>
                </dl>
                <!-- <dl v-if="!isPartner">
                    <dt>상품구분</dt>
                    <dd>
                        <div class="radio_wrap wide3">
                            <input type="radio" name="isdeal" id="isdealAll" v-model="searchData.isdeal" value=""/>
                            <label for="isdealAll">전체</label>
                            <input type="radio" name="isdeal" id="isdealF" v-model="searchData.isdeal" value="F"/>
                            <label for="isdealF">일반상품</label>
                            <input type="radio" name="isdeal" id="isdealT" v-model="searchData.isdeal" value="T"/>
                            <label for="isdealT">딜 상품</label>
                        </div>
                    </dd>
                </dl> -->
                <dl>
                    <dt>판매상태</dt>
                    <dd>
                        <div class="check-wrap">
                            <input type="checkbox" id="chkAllSellType" v-model="searchData.isallselltype" true-value="T" false-value="F" @change="checkAllSellType">
                            <label for="chkAllSellType">전체</label>
                        </div>
                        <div class="check-wrap" v-for="item in commonCode.goodsselltype" :key="item.cmcode">
                            <input type="checkbox" :id="'goodsselltype_'+item.cmcode" v-model="searchData.goodsselltypeArr" :true-value="[]" :value="item.cmcode"/>
                            <label :for="'goodsselltype_'+item.cmcode">{{ item.codename }}</label>
                        </div>
                    </dd>
                </dl>
                <dl>
                    <dt>조회기간</dt>
                    <dd>
                        <select v-model="searchData.sdate">
                            <option value="reg">등록일</option>
                            <option value="mod">처리일</option>
                        </select>
                        <common-date-picker :value="searchData.startDate" @change="onChangeStartDate"></common-date-picker>
                        <span>-</span>
                        <common-date-picker :value="searchData.endDate" @change="onChangeEndDate"></common-date-picker>
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
                <dl v-if="!isPartner">
                    <dt>전시상태</dt>
                    <dd>
                        <div class="radio_wrap wide3">
                            <input type="radio" name="isdisplay" id="isdisplayAll" v-model="searchData.isdisplay" value=""/>
                            <label for="isdisplayAll">전체</label>
                            <input type="radio" name="isdisplay" id="isdisplayT" v-model="searchData.isdisplay" value="T"/>
                            <label for="isdisplayT">전시</label>
                            <input type="radio" name="isdisplay" id="isdisplayF" v-model="searchData.isdisplay" value="F"/>
                            <label for="isdisplayF">미 전시</label>
                        </div>
                    </dd>
                </dl>
                <dl v-if="!isPartner">
                    <dt>가격</dt>
                    <dd>
                        <select v-model="searchData.sprice">
                            <option value="price">판매가</option>
                            <option value="marketprice">정상가</option>
                        </select>
                        <input type="text" class="short right" v-model="searchData.minprice" maxlength="11"/><span>원 ~</span>
                        <input type="text" class="short right" v-model="searchData.maxprice" maxlength="11"/><span>원 이내</span>
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
                    <span v-if="!isPartner">판매대기 <strong>{{ count.gst001cnt }}</strong>건</span>
                    <span v-if="!isPartner">판매중 <strong>{{ count.gst002cnt }}</strong>건</span>
                    <span v-if="!isPartner">판매중지 <strong>{{ count.gst003cnt }}</strong>건</span>
                    <span v-if="!isPartner">수기품절 <strong>{{ count.gst005cnt }}</strong>건</span>
                </div>
                <div class="btn-group fr">
                    <select v-model="goodsselltype" v-if="isWrite">
                        <option value="">판매상태 선택</option>
                        <option v-for="item in commonCode.goodsselltype" :key="item.cmcode" :value="item.cmcode">{{item.codename}}</option>
                    </select>
                    <button type="button" class="btn blue-line ml3" v-if="isWrite" @click="setAllGoodsSellType">일괄변경</button>
                    <button type="button" class="btn blue-line" v-if="isWrite && isEditDisplay" @click="setIsDisplay('T')">전시</button>
                    <button type="button" class="btn blue-line" v-if="isWrite && isEditDisplay" @click="setIsDisplay('F')">미 전시</button>
                    <button type="button" class="btn red-line" v-if="isWrite" @click="removeGoods">삭제</button>
                    <button type="button" class="btn green-line" v-if="isRead" @click="downloadExcel"><i class="icon-excel"></i>엑셀다운로드</button>
                    <select v-model="pagingData.pageCount" v-if="isRead">
                        <option value="20">20개씩 보기</option>
                        <option value="50">50개씩 보기</option>
                        <option value="100">100개씩 보기</option>
                    </select>
                </div>
            </div>
            <table cellpadding="0" cellspacing="0" class="data-tb align-c">
                <colgroup v-if="!isPartner">
                    <col width="2%" /><!-- checkbox -->
                    <col width="2.5%" /><!-- No -->
                    <col width="4%" /><!-- 판매구분 -->
                    <col width="5%" /><!-- 파트너사명 -->
                    <col width="3.5%" /><!-- 상품구분 -->

                    <col width="5%" /><!-- 브랜드코드 -->
                    <col width="5%" /><!-- 브랜드명 -->

                    <col width="7%" /><!-- 상품코드 -->
                    <col width="62px" /><!-- 상품명(이미지) -->
                    <col width="" /><!-- 상품명 -->
                    <col width="5%" /><!-- 정상가 -->
                    <col width="5%" /><!-- 판매가 -->
                    <col width="4%" /><!-- 수수료율 -->
                    <col width="3.5%" /><!-- 재고 -->
                    <col width="4.5%" /><!-- 조회수 -->
                    <col width="4%" /><!-- 판매상태 -->
                    <col width="4%" /><!-- 전시상태 -->
                    <col width="4%" /><!-- MD -->
                    <col width="6%" /><!-- 등록일 -->
                    <col width="6%" /><!-- 수정일 -->
                </colgroup>
                <colgroup v-else>
                    <col width="2%" /><!-- checkbox -->
                    <col width="2.5%" /><!-- No -->
                    <col width="5%" /><!-- 판매상태 -->

                    <col width="5%" /><!-- 브랜드코드 -->
                    <col width="5%" /><!-- 브랜드명 -->

                    <col width="7%" /><!-- 상품코드 -->
                    <col width="62px" /><!-- 상품명(이미지) -->
                    <col width="" /><!-- 상품명 -->
                    <col width="5%" /><!-- 정상가 -->
                    <col width="5%" /><!-- 판매가 -->
                    <col width="4%" /><!-- 수수료율 -->
                    <col width="3.5%" /><!-- 재고 -->
                    <col width="4.5%" /><!-- 조회수 -->
                    <col width="5%" /><!-- 전시상태 -->
                    <col width="4%" /><!-- MD -->
                    <col width="6%" /><!-- 등록일 -->
                    <col width="6%" /><!-- 수정일 -->
                </colgroup>
                <thead>
                    <tr>
                        <th><input type="checkbox" id="chkall" v-model="isallchk" @change="checkAllGoodsList($event.target.checked)"/></th>
                        <th>No</th>
                        <th v-if="isPartner">판매상태
                            <button type="button" class="sort" :value="sortData.goodsselltype" v-if="isRead"
                                :class="[{up : sortData.goodsselltype=== 'goodsselltype_asc'}, {down : sortData.goodsselltype === 'goodsselltype_desc'}]"
                                @click="sortToggle(sortData.goodsselltype)"></button>
                        </th>
                        <th v-if="!isPartner">판매구분</th>
                        <th v-if="!isPartner">파트너사명</th>
                        <th v-if="!isPartner">상품구분</th>
                        <th>브랜드코드</th>
                        <th>브랜드명</th>
                        <th>상품코드
                            <button type="button" class="sort" :value="sortData.code" v-if="isRead"
                                :class="[{up : sortData.code=== 'code_asc'}, {down : sortData.code === 'code_desc'}]"
                                @click="sortToggle(sortData.code)"></button>
                        </th>
                        <th colspan="2">상품명</th>
                        <th>정상가
                            <button type="button" class="sort" :value="sortData.marketprice" v-if="isRead"
                                :class="[{up : sortData.marketprice=== 'marketprice_asc'}, {down : sortData.marketprice === 'marketprice_desc'}]"
                                @click="sortToggle(sortData.marketprice)"></button>
                        </th>
                        <th>판매가
                            <button type="button" class="sort" :value="sortData.price" v-if="isRead"
                                :class="[{up : sortData.price=== 'price_asc'}, {down : sortData.price === 'price_desc'}]"
                                @click="sortToggle(sortData.price)"></button>
                        </th>
                        <th>수수료율
                            <button type="button" class="sort" :value="sortData.rate" v-if="isRead"
                                :class="[{up : sortData.rate=== 'rate_asc'}, {down : sortData.rate === 'rate_desc'}]"
                                @click="sortToggle(sortData.rate)"></button>
                        </th>
                        <th>재고
                            <button type="button" class="sort" :value="sortData.stock" v-if="isRead"
                                :class="[{up : sortData.stock=== 'stock_asc'}, {down : sortData.stock === 'stock_desc'}]"
                                @click="sortToggle(sortData.stock)"></button>
                        </th>
                        <th>조회수
                            <button type="button" class="sort" :value="sortData.hits" v-if="isRead"
                                :class="[{up : sortData.hits=== 'hits_asc'}, {down : sortData.hits === 'hits_desc'}]"
                                @click="sortToggle(sortData.hits)"></button>
                        </th>
                        <th v-if="!isPartner">판매상태</th>
                        <th>전시상태
                            <button type="button" class="sort" :value="sortData.isdisplay" v-if="isPartner && isRead"
                                :class="[{up : sortData.isdisplay=== 'isdisplay_asc'}, {down : sortData.isdisplay === 'isdisplay_desc'}]"
                                @click="sortToggle(sortData.isdisplay)"></button>
                        </th>
                        <th>MD
                            <button type="button" class="sort" :value="sortData.mdname" v-if="isPartner && isRead"
                                :class="[{up : sortData.mdname=== 'mdname_asc'}, {down : sortData.mdname === 'mdname_desc'}]"
                                @click="sortToggle(sortData.mdname)"></button>
                        </th>
                        <th>등록일
                            <button type="button" class="sort" :value="sortData.regdate" v-if="isRead"
                                :class="[{up : sortData.regdate=== 'regdate_asc'}, {down : sortData.regdate === 'regdate_desc'}]"
                                @click="sortToggle(sortData.regdate)"></button>
                        </th>
                        <th>수정일
                            <button type="button" class="sort" :value="sortData.moddate" v-if="isRead"
                                :class="[{up : sortData.moddate=== 'moddate_asc'}, {down : sortData.moddate === 'moddate_desc'}]"
                                @click="sortToggle(sortData.moddate)"></button>
                        </th>
                    </tr>
                </thead>
                <tbody v-if="allGoodsList.length > 0">
                    <tr v-for="(item, index) in allGoodsList" :key="index" :class="{'bg gray': item.goodsselltype == $store.getters['ADMIN'].GOODS_SELL_TYPE_GST006}">
                        <td>
                            <input type="checkbox" v-if="item.goodsselltype != $store.getters['ADMIN'].GOODS_SELL_TYPE_GST006"
                                :id="item.goodsno" v-model="checkedList" :value="item.goodsno" @change="checkGoodsList($event.target.checked)"/>
                        </td>
                        <td>{{ loopNumberForPaging(index) }}</td>
                        <td v-if="isPartner">{{ item.goodsselltypename }}</td>
                        <td v-if="!isPartner">{{ item.ispbgoodsname }}</td>
                        <td v-if="!isPartner"><a href="javascript:void(0)" class="link" @click="goPartnerDetail(item.dealerno)">{{ item.dealername }}</a></td>
                        <td v-if="!isPartner">{{ item.isdealname }}</td>
                        <td>{{ item.brcode }}</td>
                        <td>{{ item.brandname }}</td>
                        <td>{{ item.goodscode }}</td>
                        <td>
                            <div class="img-thumb size60 link" @click="goFrontGoodsDetail(item.goodscode)" :class="{'no-image': $util.isNull(item.fullpath)}">
                                <img :src="item.fullpath" v-if="!$util.isNull(item.fullpath)">
                            </div>
                        </td>
                        <td class="left no-left">
                            <span class="small-txt">{{ item.fullcategoryname }}</span>
                            <a href="javascript:void(0)" class="dpb link" @click="item.isdeal=='T'? goDealDetail(item.goodsno) : goGoodsDetail(item.goodsno)"
                                :class="{'middle-line': item.goodsselltype == $store.getters['ADMIN'].GOODS_SELL_TYPE_GST006}">{{ item.goodsname }}</a>
                        </td>
                        <td class="right">{{ $util.maskComma(item.marketprice) }}</td>
                        <td class="right">{{ $util.maskComma(item.price) }}</td>
                        <td>{{ item.commrate }}</td>
                        <td>{{ $util.maskComma(item.stockcnt) }}</td>
                        <td>{{ $util.maskComma(item.hits) }}</td>
                        <td v-if="!isPartner">{{ item.goodsselltypename }}</td>
                        <td>{{ item.isdisplayname }}</td>
                        <td>{{ item.mdname }}</td>
                        <td>{{ item.regdate }}</td>
                        <td><a href="javascript:void(0)" class="link" @click="goGoodsHistory(item.goodsno)">{{ item.moddate }}</a></td>
                    </tr>
                </tbody>
                <tbody v-else>
                    <tr><td :colspan="isPartner? '15': '18'">조회 결과가 존재하지 않습니다.</td></tr>
                </tbody>
            </table>
            <div class="bottom-group">
                <common-page-navigator :pagingData="pagingData" v-on:setPagingData="setPagingData"/>
            </div>
        </div>
        <PartnersApplyDetail v-if="isPartnerDetailShow" :activeUserNo="activeDealerNo" @closeDetail="closePartnerDetail"></PartnersApplyDetail>
        <GoodsDetail v-if="isGoodsDetailShow" :activeGoodsNo="activeGoodsNo" @closePopup="closeGoodsDetail"></GoodsDetail>
        <DealDetail v-if="isDealDetailShow" :activeGoodsNo="activeGoodsNo" @closePopup="closeDealDetail"></DealDetail>
    </div>
</template>

<script>
import CommonNavigator from '@/views/admin/common/CommonNavigator.vue'
import CommonDatePicker from '@views.admin/common/CommonDatePicker.vue';
import CommonPageNavigator from '@views.admin/common/CommonPageNavigator.vue';
import PartnersApplyDetail from '@views.admin/partners/apply/PartnersApplyDetail.vue';
import GoodsDetail from '@views.admin/goods/manage/GoodsDetail.vue';
import DealDetail from '@views.admin/goods/manage/DealDetail.vue';
import GoodsHistoryPopup from '@views.admin/goods/popup/GoodsHistoryPopup.vue';

export default {
    name: 'admin.goods.manage.allGoodsManage',
    components: {
        CommonNavigator,
        CommonDatePicker,
        CommonPageNavigator,
        PartnersApplyDetail,
        GoodsDetail,
        DealDetail
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
            isPartner: this.$util.isAuthorized(this.$store.getters['CONSTANTS'].PARTNER_USER),
            user: {},
            goodsselltype: '',
            searchData: {
                gbn: 'allgoods',
                skeyArr: [
                    {key: '', name: '전체', isShowPartner: true},
                    {key: 'goodsname', name: '상품명', isShowPartner: true},
                    {key: 'goodscode', name: '상품코드', isShowPartner: true},
                    {key: 'dealername', name: '파트너사명', isShowPartner: false},
                    {key: 'mdname', name: 'MD명', isShowPartner: true},
                    {key: 'brandname', name: '브랜드명', isShowPartner: false}
                ],
                skey: '',               //검색키워드
                sword: '',              //검색어
                ispbgoods: '',          //직매입여부
                dealerno: '',           //입점업체번호
                isdeal: 'F',            //딜여부
                isallselltype: 'T',     //상품판매상태 전체체크여부
                goodsselltypeArr: '',   //상품판매상태Array
                sdate: 'reg',           //기간구분(default: 등록일자)
                startDate: '',          //등록시작일자 
                endDate: '',            //등록종료일자
                period: 'months_3',     //기간
                depth1cateidx: '',      //대분류일련번호
                depth2cateidx: '',      //중분류일련번호
                depth3cateidx: '',      //소분류일련번호
                depth4cateidx: '',      //세분류일련번호
                isdisplay: '',          //전시여부
                sprice: 'price',        //금액구분(default: 판매가)
                minprice: '',           //최소가격
                maxprice: '',           //최대가격
                psort: 'regdate_desc'   //정렬조건
            },
            pagingData: {
              pageCount: 20,        //페이징 옵션 (최대수)
              page: 1,              //현재 페이지
              listCount: 0          //총 페이지
            },
            sortData: {
                code : 'code_asc',
                marketprice : 'marketprice_asc',
                price : 'price_asc',
                rate : 'rate_asc',
                stock : 'stock_asc',
                hits : 'hits_asc',
                regdate : 'regdate_desc',
                moddate : 'moddate_asc',
                goodsselltype : 'goodsselltype_asc',
                isdisplay : 'isdisplay_asc',
                mdname : 'mdname_asc'
            },
            categoryObj: {
                depth1list: [],
                depth2list: [],
                depth3list: [],
                depth4list: [],
            },
            commonCode: {
                goodsselltype: []   //상품판매상태
            },
            isRead : false,
            isWrite : false,
            checkedList: [],
            activeDealerNo: null,
            activeGoodsNo: null,
            isPartnerDetailShow: false, //파트너사상세팝업여부
            isGoodsDetailShow: false,   //상품상세팝업여부
            isDealDetailShow: false,    //딜상품상세팝업여부
            isGoodsHistoryShow: false,  //상품이력팝업여부
            isallchk: false,    //전체체크여부
            partnerList: [],    //파트너사 목록
            allGoodsList: [],   //상품목록
            isEditDisplay: false,   //전시 수정가능 여부
            count: {
                totalcnt: 0,    //전체 건수
                gst001cnt: 0,   //판매대기 건수
                gst002cnt: 0,   //판매중 건수
                gst003cnt: 0,   //판매중지 건수
                gst005cnt: 0,   //수기품절 건수
            },
            isLink : false, //대시보드에서 링크를 타고왔는지 체크
        }
    },
    methods : {
        onInit: function() {
            if(typeof this.$route.params.type !== 'undefined' && this.$route.params.type === 'LINK'){
              this.isLink = true;
            }

            this.user = this.$util.getUser(this.$store.getters['CONSTANTS'].MANAGER_SESSION);
            this.getCommonCodeList();
            this.getPartnerList();
            this.getCategoryCodeList(1, 0);
        },
        // 검색조건 초기화
        initSearchData: function () {
            this.searchData.skey = '';
            this.searchData.sword = '';
            this.searchData.isdeal = 'F';
            this.searchData.isallselltype = 'T';
            this.searchData.sdate = 'reg';
            this.searchData.startDate = this.$util.getAddMonth(this.$util.getDate(), -3, '-');
            this.searchData.endDate = this.$util.getDate('-');
            this.searchData.period = 'months_3';
            this.searchData.depth1cateidx =  '';
            this.searchData.depth2cateidx = '';
            this.searchData.depth3cateidx = '';
            this.searchData.depth4cateidx = '';
            this.searchData.isdisplay = '';
            this.searchData.sprice = 'price';
            this.searchData.minprice = '';
            this.searchData.maxprice = '';
            this.categoryObj.depth2list = [];
            this.categoryObj.depth3list = [];
            this.categoryObj.depth4list = [];
            
            if (this.isPartner) {
                this.searchData.ispbgoods = 'F';
                this.searchData.dealerno = this.user.no;
                for (let i=this.searchData.skeyArr.length-1; i>0; i--) {
                    if (!this.searchData.skeyArr[i].isShowPartner) {
                        this.searchData.skeyArr.splice(i, 1);
                    }
                }
            } else {
                this.searchData.ispbgoods = '';
                this.searchData.dealerno = '';
            }

            this.checkAllSellType();
        },
        // 공통코드 목록 조회
        getCommonCodeList: function() {
            let cmclassArr = ['GOODSSELLTYPE'];
            this.$http.post('/common/code/map/list', {cmclass : cmclassArr, isloading: false})
                .then(result =>{
                    let data = result.data;
                    for (const [key] of Object.entries(data)) {
                        this.commonCode[key] = data[key];
                    }
                    // 검색조건 초기화
                    this.initSearchData();
                    this.$util.componentSetSearchParam(this);

                    if(this.isLink){
                      let linkParam = this.$route.params;
                      this.searchData.period = linkParam.period;
                      this.searchData.startDate = linkParam.startdate;
                      this.searchData.endDate = linkParam.enddate;
                      this.searchData.goodsselltypeArr = linkParam.goodsselltype;
                    }
                    // 목록 조회
                    // this.searchList();	// 최초 로딩 시 조회되지 않도록 김태선님이 요청(James, 2022-09-28)
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 파트너사 목록 조회
        getPartnerList: function() {
            let params = this.searchData;
            params.isloading = false;
            this.$http.post('/admin/goods/manage/partner/list', params)
                .then(result => {
                    this.$util.debug(result);
                    this.partnerList = result.data.list;
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 상품구분상태 전체체크
        checkAllSellType: function() {
            let isAllCheck = this.searchData.isallselltype;
            this.searchData.goodsselltypeArr = [];
            if (isAllCheck == 'T') {
                for(let type of this.commonCode.goodsselltype){
                    this.searchData.goodsselltypeArr.push(type.cmcode);
                }
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
        // 페이징데이터 세팅
        setPagingData(param){
            this.pagingData = param;
            this.searchList();
        },
        // 정렬
        sortToggle(key){
            if (!this.isRead) return;
            
            let arr = key.split('_');
            let sortKey = arr[0];
            let sortOrder = (arr[1] === 'asc') ? 'desc' : 'asc';
            let sortName = sortKey + '_' + sortOrder;

            this.sortData[sortKey] = sortName;
            this.searchData.psort = sortName;

            this.searchList();
        },
        // 상품목록 전체체크
        checkAllGoodsList: function(value) {
            this.checkedList = [];
            if (value) {
                this.allGoodsList.forEach(item => {
                    if (item.goodsselltype != this.$store.getters['ADMIN'].GOODS_SELL_TYPE_GST006) {
                        this.checkedList.push(item.goodsno);
                    }
                });
            }
        },
        // 상품목록 개별체크
        checkGoodsList: function() {
            let endCnt = this.allGoodsList.filter(item => {
                return item.goodsselltype != this.$store.getters['ADMIN'].GOODS_SELL_TYPE_GST006;
            }).length;
            if (endCnt > this.checkedList.length){
                this.isallchk = false;
            } else {
                this.isallchk = true;
            }
        },
        // 목록 조회
        searchList: function(page) {
            let params = Object.assign(this.searchData, this.pagingData);
            params.page = (typeof page === 'undefined') ? this.pagingData.page : 1;
            params.isPartner = this.isPartner;
            params.isloading = true;
            this.$http.post('/admin/goods/manage/list', params)
                .then(result => {
                    this.$util.debug(result);
                    let data = result.data;
                    this.allGoodsList = data.list;
                    this.count = data.count;
                    this.pagingData.listCount = data.count.totalcnt;
                    this.checkedList = [];
                    this.isallchk = false;
                    if(this.isPartner) {
                        this.isEditDisplay = data.iseditdisplay;
                    } else {
                        this.isEditDisplay = true;
                    }
                    this.$util.dataSetSearchParam(this);
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 판매상태 일괄변경
        setAllGoodsSellType: function() {
            if (this.$util.isNull(this.goodsselltype)) {
                alert('판매상태를 선택해주세요.');
                return;
            }
            if (this.checkedList.length == 0) {
                alert('변경할 상품을 선택해주세요.');
                return;
            }
            if (confirm('선택한 상품의 판매상태를 변경하시겠습니까?')) {
                let params = { goodsselltype: this.goodsselltype, goodsnolist: this.checkedList };
                this.$http.post('/admin/goods/manage/goodsselltype/update', params)
                    .then(result => {
                        this.$util.debug(result);
                        if (result.statusCode == '200') {
                            alert('변경이 완료되었습니다.');
                            this.searchList();
                        }
                    })
                    .catch(error => {
                        this.$util.debug(error);
                    });
            }
        },
        // 전시여부 변경
        setIsDisplay: function(value) {
            if (this.checkedList.length == 0) {
                alert('변경할 상품을 선택해주세요.');
                return;
            }
            if (confirm('선택한 상품의 전시상태를 변경하시겠습니까?')) {
                let params = { isdisplay: value, goodsnolist: this.checkedList };
                this.$http.post('/admin/goods/manage/display/update', params)
                    .then(result => {
                        this.$util.debug(result);
                        if (result.statusCode == '200') {
                            alert('변경이 완료되었습니다.');
                            this.searchList();
                        }
                    })
                    .catch(error => {
                        this.$util.debug(error);
                    });
            }
        },
        // 상품삭제
        removeGoods: function(value) {
            if (this.checkedList.length == 0) {
                alert('삭제할 상품을 선택해주세요.');
                return;
            }
            
            // 판매중지 상태의 상품만 삭제 가능
            for (let i=0; i<this.checkedList.length; i++) {
                let goodsno = this.checkedList[i];
                for (let j=0; j<this.allGoodsList.length; j++) {
                    let item = this.allGoodsList[j];
                    if (goodsno == item.goodsno && item.goodsselltype != this.$store.getters['ADMIN'].GOODS_SELL_TYPE_GST003) {
                        alert('상품삭제는 판매중지 이후에 처리 가능합니다.');
                        return;
                    }
                }
            }

            if (confirm('선택한 상품을 삭제하시겠습니까?')) {
                let params = {
                    isdisplay: 'F',
                    goodsselltype: this.$store.getters['ADMIN'].GOODS_SELL_TYPE_GST006, 
                    goodsnolist: this.checkedList 
                };
                this.$http.post('/admin/goods/manage/delete', params)
                    .then(result => {
                        this.$util.debug(result);
                        if (result.statusCode == '200') {
                            alert('삭제가 완료되었습니다.');
                            this.searchList();
                        } else {
                            alert('삭제가 실패하였습니다.');
                        }
                    })
                    .catch(error => {
                        this.$util.debug(error);
                    });
            }
        },
        // 엑셀다운로드
        downloadExcel: function() {
            if (this.allGoodsList.length == 0) {
                alert('다운로드할 내역이 존재하지 않습니다.');
                return;
            }
            let config = { responseType: 'arraybuffer' };
            let params = this.searchData;
            params.isPartner = this.isPartner;
            this.$http.post('/admin/goods/manage/exceldown', params, config)
                .then(result => {
                    this.$util.debug(result);
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        loopNumberForPaging(index) {
            if(this.pagingData.page > 1){
                let page = this.pagingData.page - 1;
                return (index+1) + (page * this.pagingData.pageCount);
            } else {
                return (index+1);
            }
        },
        // 파트너사 상세정보
        goPartnerDetail: function(value) {
            this.activeDealerNo = value;
            this.isPartnerDetailShow = true;
        },
        closePartnerDetail: function () {
          this.isPartnerDetailShow = false;
        },
        // 상품 상세정보
        goGoodsDetail: function(value) {
            this.activeGoodsNo = value;
            this.isGoodsDetailShow = true;
        },
        closeGoodsDetail: function (isreload) {
            this.isGoodsDetailShow = false;
            if (isreload === true) {
                this.searchList();
            }
        },
        // 딜상품 상세정보
        goDealDetail: function(value) {
            this.activeGoodsNo = value;
            this.isDealDetailShow = true;
        },
        closeDealDetail: function (isreload) {
            this.isDealDetailShow = false;
            if (isreload === true) {
                this.searchList();
            }
        },
        // 상품변경이력
        goGoodsHistory: function(value) {
            this.$eventBus.$emit('modalShow', GoodsHistoryPopup, { goodsno: value }, null);
        },
        // Front 화면으로 이동
        goFrontGoodsDetail: function(value) {
            window.open(process.env.VUE_APP_PC_GOODS_DETAIL_URL + value, "_blank");
        }
    },
    watch: {
        // 상품구분상태
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
        },
        // 숫자만입력
        'searchData.minprice': function(value) {
            value = value+'';
            if (this.$util.isNull(value)) return;
            return this.searchData.minprice = value.replace(/(^0[\d]|[^\d])/gi, '');
        },
        'searchData.maxprice': function(value) {
            value = value+'';
            if (this.$util.isNull(value)) return;
            return this.searchData.maxprice = value.replace(/(^0[\d]|[^\d])/gi, '');
        }
    }
}
</script>