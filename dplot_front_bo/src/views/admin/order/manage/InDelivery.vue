<template>
    <!-- 컨텐츠 영역 -->
    <div class="content m-leftmenu">
        <common-navigator></common-navigator>
        <div class="inner">
            <div class="clearfix">
                <div class="bar-title fl">배송중</div>
            </div>
            <div class="boxing search-area pd0" v-if="!isPartner">
                <dl>
                    <dt>직접검색</dt>
                    <dd>
                        <select v-model="searchData.skey">
                            <option v-for="item in searchData.skeyArr" :key="item.key" :value="item.key">{{ item.name }}</option>
                        </select>
                        <input type="text" v-model="searchData.sword" maxlength="200" @keyup.enter="searchList(1)"/>
                    </dd>
                </dl>
                <dl>
                    <dt>조회일자</dt>
                    <dd>
                        <select v-model="searchData.dtkey">
                            <option value="order">주문일</option>
                            <option value="payment">결제일</option>
                            <option value="invoicereg">송장번호저장일</option>
                        </select>
                        <CommonDatePicker :value="searchData.startdate" @change="onChangeStartDate"/>
                        <span>-</span>
                        <CommonDatePicker :value="searchData.enddate" @change="onChangeEndDate"/>
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
                    <dt>상품검색</dt>
                    <dd>
                        <select v-model="searchData.gdkey">
                            <option value="goodsname">상품명</option>
                            <option value="goodscode">상품코드</option>
                            <option value="optioncode">단품코드</option>
                            <option value="optionname">옵션명</option>
                        </select>
                        <input type="text" v-model="searchData.gdword" maxlength="500"/>
                    </dd>
                </dl>
                <dl>
                    <dt>판매구분</dt>
                    <dd>
                        <div class="radio_wrap wide2">
                            <input type="radio" name="ispbgoods" id="ispbgoodsAll" v-model="searchData.ispbgoods" value="" @click="searchData.dealerno=''"/>
                            <label for="ispbgoodsAll">전체</label>
                            <input type="radio" name="ispbgoods" id="ispbgoodsT" v-model="searchData.ispbgoods" value="T" @click="searchData.dealerno=''"/>
                            <label for="ispbgoodsT">자사</label>
                            <input type="radio" name="ispbgoods" id="ispbgoodsF" v-model="searchData.ispbgoods" value="F"/>
                            <label for="ispbgoodsF">파트너사</label>
                        </div>
                        <select :disabled="searchData.ispbgoods!='F'" v-model="searchData.dealerno">
                            <option value="">파트너사 전체</option>
                            <option v-for="item in searchData.partnerList" :key="item.no" :value="item.no">{{ item.name }} </option>
                        </select>
                    </dd>
                    <dd style="width: 104px;" v-if="!searchData.isshowdetailarea">
                        <!-- 펼쳤을 경우 i class에 close -> open -->
                        <button type="button" class="btn black-line" @click="searchData.isshowdetailarea = !searchData.isshowdetailarea">
                            <i class="icon-arr-detail close"></i>상세검색
                        </button>
                    </dd>
                </dl>
                <!-- 상세검색 펼침 영역(공통) -->
                <dl v-if="searchData.isshowdetailarea">
                    <dt>주문경로</dt>
                    <dd>
                        <div class="check-wrap">
                            <input type="checkbox" id="chkAllChannel" v-model="searchData.isallchannel" true-value="T" false-value="F" @change="checkAllChannel">
                            <label for="chkAllChannel">전체</label>
                        </div>
                        <div class="check-wrap" v-for="item in commonCode.muappchtype" :key="item.cmcode">
                            <input type="checkbox" :id="'muappchtype_'+item.cmcode" v-model="searchData.muappchtypeArr" :true-value="[]" :value="item.cmcode"/>
                            <label :for="'muappchtype_'+item.cmcode">{{ item.codename }}</label>
                        </div>
                    </dd>
                </dl>
                <dl v-if="searchData.isshowdetailarea">
                    <dt>금액조건</dt>
                    <dd>
                        <select v-model="searchData.pricekey">
                            <option value="goods">상품구매금액</option>
                            <option value="payment">결제금액</option>
                        </select>
                        <input type="text" class="short" v-model="searchData.minprice" maxlength="11"/>
                        <span>~</span>
                        <input type="text" class="short" v-model="searchData.maxprice" maxlength="11" />
                    </dd>
                </dl>
                <dl v-if="searchData.isshowdetailarea">
                    <dt>회원구분</dt>
                    <dd>
                        <div class="radio_wrap wide3">
                            <input type="radio" name="isnonmember" id="isnonmemberAll" v-model="searchData.isnonmember" value="" />
                            <label for="isnonmemberAll">전체</label>
                            <input type="radio" name="isnonmember" id="isnonmemberF" v-model="searchData.isnonmember" value="F" />
                            <label for="isnonmemberF">회원</label>
                            <input type="radio" name="isnonmember" id="isnonmemberT" v-model="searchData.isnonmember" value="T"/>
                            <label for="isnonmemberT">비회원</label>
                        </div>
                    </dd>
                </dl>
                <dl v-if="searchData.isshowdetailarea">
                    <dt>주문구분</dt>
                    <dd>
                        <div class="radio_wrap wide3">
                            <input type="radio" name="isfrstorder" id="isfrstorderAll" v-model="searchData.isfrstorder" value="" />
                            <label for="isfrstorderAll">전체</label>
                            <input type="radio" name="isfrstorder" id="isfrstorderT" v-model="searchData.isfrstorder" value="T" />
                            <label for="isfrstorderT">첫 주문</label>
                            <input type="radio" name="isfrstorder" id="isfrstorderF" v-model="searchData.isfrstorder" value="F"/>
                            <label for="isfrstorderF">재 주문</label>
                        </div>
                    </dd>
                </dl>
                <dl v-if="searchData.isshowdetailarea">
                    <dt>회원유형</dt>
                    <dd>
                        <div class="check-wrap">
                            <input type="checkbox" id="chkAllMember" v-model="searchData.isallmember" true-value="T" false-value="F" @change="checkAllMembertype">
                            <label for="chkAllMember">전체</label>
                        </div>
                        <div class="check-wrap" v-for="item in commonCode.dadamembertype" :key="item.cmcode">
                            <input type="checkbox" :id="'dadamembertype_'+item.cmcode" v-model="searchData.mumembertypeArr" :true-value="[]" :value="item.cmcode">
                            <label :for="'dadamembertype_'+item.cmcode">{{ item.codename }}</label>
                        </div>
                    </dd>
                </dl>
                <dl v-if="searchData.isshowdetailarea">
                    <dt>회원등급</dt>
                    <dd>
                        <div class="check-wrap">
                            <input type="checkbox" id="chkAllMemlv" v-model="searchData.isallmemlv" true-value="T" false-value="F" @change="checkAllMemlvtype">
                            <label for="chkAllMemlv">전체</label>
                        </div>
                        <div class="check-wrap" v-for="item in commonCode.memlvtype" :key="item.cmcode">
                            <input type="checkbox" :id="'mumemlvtype_'+item.cmcode" v-model="searchData.mumemlvtypeArr" :true-value="[]" :value="item.cmcode">
                            <label :for="'mumemlvtype_'+item.cmcode">{{ item.codename }}</label>
                        </div>
                    </dd>
                </dl>
                <dl v-if="searchData.isshowdetailarea">
                    <dt>결제수단</dt>
                    <dd>
                        <div class="check-wrap">
                            <input type="checkbox" id="chkAllPayway" v-model="searchData.isallpayway" true-value="T" false-value="F" @change="checkAllPaywaytype">
                            <label for="chkAllPayway">전체</label>
                        </div>
                        <div class="check-wrap" v-for="item in commonCode.paywaytype" :key="item.cmcode">
                            <input type="checkbox" :id="'paywaytype_'+item.cmcode" v-model="searchData.paywaytypeArr" :true-value="[]" :value="item.cmcode">
                            <label :for="'paywaytype_'+item.cmcode">{{ item.codename }}</label>
                        </div>
                    </dd>
                    <dd style="width: 104px;" v-if="searchData.isshowdetailarea">
                        <!-- 펼쳤을 경우 i class에 close -> open -->
                        <button type="button" class="btn black-line" @click="searchData.isshowdetailarea = !searchData.isshowdetailarea">
                            <i class="icon-arr-detail open"></i>상세검색
                        </button>
                    </dd>
                </dl>
                <!-- // 상세검색 펼침 영역(공통) -->
            </div>
            <div class="boxing search-area pd0" v-if="isPartner">
                <dl>
                    <dt>직접검색</dt>
                    <dd>
                        <select v-model="searchData.skey">
                            <option v-for="item in searchData.skeyArr" :key="item.key" :value="item.key">{{ item.name }}</option>
                        </select>
                        <input type="text" v-model="searchData.sword" maxlength="200" @keyup.enter="searchList(1)"/>
                    </dd>
                </dl>
                <dl>
                    <dt>조회일자</dt>
                    <dd>
                        <select v-model="searchData.dtkey">
                            <option value="order">주문일</option>
                            <option value="payment">결제일</option>
                        </select>
                        <CommonDatePicker :value="searchData.startdate" @change="onChangeStartDate"/>
                        <span>-</span>
                        <CommonDatePicker :value="searchData.enddate" @change="onChangeEndDate"/>
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
            </div>
            <div class="btn-group" v-if="isRead">
                <button type="button" class="btn big blue" @click="searchList(1)">검색</button>
                <button type="button" class="btn big gray" @click="initSearchData">초기화</button>
            </div>
            <div class="caption-group mt10 clearfix">
                <div class="total-group fl">
                    <span class="total">전체 <strong>{{ count.totalcnt }}</strong>건</span>
                </div>
                <div class="btn-group fr">
                    <button type="button" v-if="isWrite" class="btn blue-line" @click="checkValidCompDeliv($store.getters['ADMIN'].ORDER_COMPLETE_DELIV)">배송완료</button>
                    <button type="button" v-if="isRead" class="btn green-line" @click="fnExcelDownload"><i class="icon-excel"></i>엑셀다운로드</button>
                    <select v-model="pagingData.pageCount" v-if="isRead">
                        <option value="20">20개씩 보기</option>
                        <option value="50">50개씩 보기</option>
                        <option value="100">100개씩 보기</option>
                    </select>
                </div>
            </div>
            <div class="scroll-x">
                <table cellpadding="0" cellspacing="0" class="data-tb align-c" :style="!isPartner? 'width: 3700px;':'width: 2500px;'">
                    <caption>주문 목록</caption>
                    <colgroup v-if="!isPartner">
                        <col width="1%" /><!-- checkbox -->
                        <col width="2.2%" /><!-- 주문경로 -->
                        <col width="2.7%" /><!-- 주문일 -->
                        <col width="2.7%" /><!-- 결제일 -->
                        <col width="3.5%" /><!-- 주문번호 -->
                        <col width="2.5%" /><!-- 첫 구매 -->
                        <col width="2.5%" /><!-- 주문자 -->
                        <col width="2.5%" /><!-- 아이디 -->
                        <col width="3.4%" /><!-- 연락처 -->
                        <col width="2.2%" /><!-- 회원구분 -->
                        <col width="2%" /><!-- 유형 -->
                        <col width="2%" /><!-- 등급 -->
                        <col width="2.5%" /><!-- 판매구분 -->
                        <col width="2.7%" /><!-- 파트너사 -->
                        <col width="2.7%" /><!-- 상품코드 -->
                        <col width="2.7%" /><!-- 단품코드 -->
                        <col width="2.5%" /><!-- 상품순번 -->
                        <col width="62px" /><!-- 상품이미지 -->
                        <col width="" /><!-- 상품명 -->
                        <col width="3.5%" /><!-- 옵션 -->
                        <col width="2.5%" /><!-- 주문수량 -->
                        <col width="3%" /><!-- 판매단가 -->
                        <col width="3%" /><!-- 판매금액 -->
                        <col width="2.5%" /><!-- 결제수단 -->
                        <col width="4%" /><!-- 추가상품여부 -->
                        <col width="2.5%" /><!-- 배송유형 -->
                        <col width="2.5%" /><!-- 배송조건 -->
                        <col width="2%" /><!-- 배송비 -->
                        <col width="2.5%" /><!-- 수령인명 -->
                        <col width="3.4%" /><!-- 연락처 -->
                        <col width="5%" /><!-- 주소 -->
                        <col width="3%" /><!-- 택배사 -->
                        <col width="3%" /><!-- 송장번호 -->
                        <col width="3.5%" /><!-- 직배송 안내문구 -->
                        <col width="2.5%" /><!-- 배송수량 -->
                        <col width="3%" /><!-- 주문상태 -->
                    </colgroup>
                    <colgroup v-else>
                        <col width="1%" /><!-- checkbox -->
                        <col width="4.2%" /><!-- 주문일 -->
                        <col width="4.2%" /><!-- 결제일 -->
                        <col width="5.8%" /><!-- 주문번호 -->
                        <col width="4.2%" /><!-- 상품코드 -->
                        <col width="3.4%" /><!-- 단품코드 -->
                        <col width="3.4%" /><!-- 상품순번 -->
                        <col width="62px" /><!-- 상품이미지 -->
                        <col width="" /><!-- 상품명 -->
                        <col width="5.8%" /><!-- 옵션 -->
                        <col width="3.5%" /><!-- 주문수량 -->
                        <col width="4%" /><!-- 판매단가 -->
                        <col width="4%" /><!-- 판매금액 -->
                        <col width="3.5%" /><!-- 결제수단 -->
                        <col width="3.5%" /><!-- 배송유형 -->
                        <col width="3.5%" /><!-- 배송조건 -->
                        <col width="3.5%" /><!-- 배송비 -->
                        <col width="3.5%" /><!-- 수령인명 -->
                        <col width="5.5%" /><!-- 연락처 -->
                        <col width="7%" /><!-- 주소 -->
                        <col width="3.5%" /><!-- 택배사 -->
                        <col width="5.5%" /><!-- 송장번호 -->
                        <col width="6%" /><!-- 직배송 안내문구 -->
                        <col width="3.5%" /><!-- 배송수량 -->
                        <col width="3.5%" /><!-- 주문상태 -->
                    </colgroup>
                    <thead>
                        <tr>
                            <th><input type="checkbox" id="chkall" v-model="isallchk" @change="checkAllList($event.target.checked)"/></th>
                            <th v-if="!isPartner">주문경로
                                <button type="button" class="sort" :value="sortData.ordpathtype"
                                    :class="[{up : sortData.ordpathtype === 'ordpathtype_asc'}, {down : sortData.ordpathtype === 'ordpathtype_desc'}]"
                                    @click="sortToggle(sortData.ordpathtype)"></button>
                            </th>
                            <th>주문일
                                <button type="button" class="sort" :value="sortData.orderdate"
                                    :class="[{up : sortData.orderdate === 'orderdate_asc'}, {down : sortData.orderdate === 'orderdate_desc'}]"
                                    @click="sortToggle(sortData.orderdate)"></button>
                            </th>
                            <th>결제일
                                <button type="button" class="sort" :value="sortData.paymentdate"
                                    :class="[{up : sortData.paymentdate === 'paymentdate_asc'}, {down : sortData.paymentdate === 'paymentdate_desc'}]"
                                    @click="sortToggle(sortData.paymentdate)"></button>
                            </th>
                            <th>주문번호
                                <button type="button" class="sort" :value="sortData.ordno"
                                    :class="[{up : sortData.ordno === 'ordno_asc'}, {down : sortData.ordno === 'ordno_desc'}]"
                                    @click="sortToggle(sortData.ordno)"></button>
                            </th>
                            <th v-if="!isPartner">첫 구매
                                <button type="button" class="sort" :value="sortData.isfrstorder"
                                    :class="[{up : sortData.isfrstorder === 'isfrstorder_asc'}, {down : sortData.isfrstorder === 'isfrstorder_desc'}]"
                                    @click="sortToggle(sortData.isfrstorder)"></button>
                            </th>
                            <th v-if="!isPartner">주문자
                                <button type="button" class="sort" :value="sortData.ordname"
                                    :class="[{up : sortData.ordname === 'ordname_asc'}, {down : sortData.ordname === 'ordname_desc'}]"
                                    @click="sortToggle(sortData.ordname)"></button>
                            </th>
                            <th v-if="!isPartner">아이디
                                <button type="button" class="sort" :value="sortData.ordid"
                                    :class="[{up : sortData.ordid === 'ordid_asc'}, {down : sortData.ordid === 'ordid_desc'}]"
                                    @click="sortToggle(sortData.ordid)"></button>
                            </th>
                            <th v-if="!isPartner">연락처
                                <button type="button" class="sort" :value="sortData.ordtel"
                                    :class="[{up : sortData.ordtel === 'ordtel_asc'}, {down : sortData.ordtel === 'ordtel_desc'}]"
                                    @click="sortToggle(sortData.ordtel)"></button>
                            </th>
                            <th v-if="!isPartner">회원구분
                                <button type="button" class="sort" :value="sortData.isnonmember"
                                    :class="[{up : sortData.isnonmember === 'isnonmember_asc'}, {down : sortData.isnonmember === 'isnonmember_desc'}]"
                                    @click="sortToggle(sortData.isnonmember)"></button>
                            </th>
                            <th v-if="!isPartner">유형
                                <button type="button" class="sort" :value="sortData.membertype"
                                    :class="[{up : sortData.membertype === 'membertype_asc'}, {down : sortData.membertype === 'membertype_desc'}]"
                                    @click="sortToggle(sortData.membertype)"></button>
                            </th>
                            <th v-if="!isPartner">등급
                                <button type="button" class="sort" :value="sortData.memlvtype"
                                    :class="[{up : sortData.memlvtype === 'memlvtype_asc'}, {down : sortData.memlvtype === 'memlvtype_desc'}]"
                                    @click="sortToggle(sortData.memlvtype)"></button>
                            </th>
                            <th v-if="!isPartner">판매구분
                                <button type="button" class="sort" :value="sortData.ispbgoods"
                                    :class="[{up : sortData.ispbgoods === 'ispbgoods_asc'}, {down : sortData.ispbgoods === 'ispbgoods_desc'}]"
                                    @click="sortToggle(sortData.ispbgoods)"></button>
                            </th>
                            <th v-if="!isPartner">파트너사
                                <button type="button" class="sort" :value="sortData.dealername"
                                    :class="[{up : sortData.dealername === 'dealername_asc'}, {down : sortData.dealername === 'dealername_desc'}]"
                                    @click="sortToggle(sortData.dealername)"></button>
                            </th>
                            <th>상품코드
                                <button type="button" class="sort" :value="sortData.goodscode"
                                    :class="[{up : sortData.goodscode === 'goodscode_asc'}, {down : sortData.goodscode === 'goodscode_desc'}]"
                                    @click="sortToggle(sortData.goodscode)"></button>
                            </th>
                            <th>단품코드
                                <button type="button" class="sort" :value="sortData.optioncode"
                                    :class="[{up : sortData.optioncode === 'optioncode_asc'}, {down : sortData.optioncode === 'optioncode_desc'}]"
                                    @click="sortToggle(sortData.optioncode)"></button>
                            </th>
                            <th>상품순번
                                <button type="button" class="sort" :value="sortData.goodsturn"
                                    :class="[{up : sortData.goodsturn === 'goodsturn_asc'}, {down : sortData.goodsturn === 'goodsturn_desc'}]"
                                    @click="sortToggle(sortData.goodsturn)"></button>
                            </th>
                            <th colspan="2">상품명
                                <button type="button" class="sort" :value="sortData.goodsname"
                                    :class="[{up : sortData.goodsname === 'goodsname_asc'}, {down : sortData.goodsname === 'goodsname_desc'}]"
                                    @click="sortToggle(sortData.goodsname)"></button>
                            </th>
                            <th>옵션
                                <button type="button" class="sort" :value="sortData.optionconts"
                                    :class="[{up : sortData.optionconts === 'optionconts_asc'}, {down : sortData.optionconts === 'optionconts_desc'}]"
                                    @click="sortToggle(sortData.optionconts)"></button>
                            </th>
                            <th>주문수량
                                <button type="button" class="sort" :value="sortData.ordcnt"
                                    :class="[{up : sortData.ordcnt === 'ordcnt_asc'}, {down : sortData.ordcnt === 'ordcnt_desc'}]"
                                    @click="sortToggle(sortData.ordcnt)"></button>
                            </th>
                            <th>판매단가
                                <button type="button" class="sort" :value="sortData.price"
                                    :class="[{up : sortData.price === 'price_asc'}, {down : sortData.price === 'price_desc'}]"
                                    @click="sortToggle(sortData.price)"></button>
                            </th>
                            <th>판매금액
                                <button type="button" class="sort" :value="sortData.totprice"
                                    :class="[{up : sortData.totprice === 'totprice_asc'}, {down : sortData.totprice === 'totprice_desc'}]"
                                    @click="sortToggle(sortData.totprice)"></button>
                            </th>
                            <th>결제수단
                                <button type="button" class="sort" :value="sortData.paywaytype"
                                    :class="[{up : sortData.paywaytype === 'paywaytype_asc'}, {down : sortData.paywaytype === 'paywaytype_desc'}]"
                                    @click="sortToggle(sortData.paywaytype)"></button>
                            </th>
                            <th v-if="!isPartner">추가상품여부
                                <button type="button" class="sort" :value="sortData.isaddgoods"
                                    :class="[{up : sortData.isaddgoods === 'isaddgoods_asc'}, {down : sortData.isaddgoods === 'isaddgoods_desc'}]"
                                    @click="sortToggle(sortData.isaddgoods)"></button>
                            </th>
                            <th>배송유형
                                <button type="button" class="sort" :value="sortData.iscombdeliv"
                                    :class="[{up : sortData.iscombdeliv === 'iscombdeliv_asc'}, {down : sortData.iscombdeliv === 'iscombdeliv_desc'}]"
                                    @click="sortToggle(sortData.iscombdeliv)"></button>
                            </th>
                            <th>배송조건
                                <button type="button" class="sort" :value="sortData.delivfaretype"
                                    :class="[{up : sortData.delivfaretype === 'delivfaretype_asc'}, {down : sortData.delivfaretype === 'delivfaretype_desc'}]"
                                    @click="sortToggle(sortData.delivfaretype)"></button>
                            </th>
                            <th>배송비
                                <button type="button" class="sort" :value="sortData.delivamt"
                                    :class="[{up : sortData.delivamt === 'delivamt_asc'}, {down : sortData.delivamt === 'delivamt_desc'}]"
                                    @click="sortToggle(sortData.delivamt)"></button>
                            </th>
                            <th>수령인명
                                <button type="button" class="sort" :value="sortData.rcvname"
                                    :class="[{up : sortData.rcvname === 'rcvname_asc'}, {down : sortData.rcvname === 'rcvname_desc'}]"
                                    @click="sortToggle(sortData.rcvname)"></button>
                            </th>
                            <th>연락처
                                <button type="button" class="sort" :value="sortData.rcvtel"
                                    :class="[{up : sortData.rcvtel === 'rcvtel_asc'}, {down : sortData.rcvtel === 'rcvtel_desc'}]"
                                    @click="sortToggle(sortData.rcvtel)"></button>
                            </th>
                            <th>주소
                                <button type="button" class="sort" :value="sortData.rcvaddr"
                                    :class="[{up : sortData.rcvaddr === 'rcvaddr_asc'}, {down : sortData.rcvaddr === 'rcvaddr_desc'}]"
                                    @click="sortToggle(sortData.rcvaddr)"></button>
                            </th>
                            <th>택배사
                                <button type="button" class="sort" :value="sortData.logistypename"
                                    :class="[{up : sortData.logistypename === 'logistypename_asc'}, {down : sortData.logistypename === 'logistypename_desc'}]"
                                    @click="sortToggle(sortData.logistypename)"></button>
                            </th>
                            <th>송장번호
                                <button type="button" class="sort" :value="sortData.invoiceno"
                                    :class="[{up : sortData.invoiceno === 'invoiceno_asc'}, {down : sortData.invoiceno === 'invoiceno_desc'}]"
                                    @click="sortToggle(sortData.invoiceno)"></button>
                            </th>
                            <th>직배송 안내문구
                                <button type="button" class="sort" :value="sortData.dirdelivmsg"
                                    :class="[{up : sortData.dirdelivmsg === 'dirdelivmsg_asc'}, {down : sortData.dirdelivmsg === 'dirdelivmsg_desc'}]"
                                    @click="sortToggle(sortData.dirdelivmsg)"></button>
                            </th>
                            <th>배송수량
                                <button type="button" class="sort" :value="sortData.delivcnt"
                                    :class="[{up : sortData.delivcnt === 'delivcnt_asc'}, {down : sortData.delivcnt === 'delivcnt_desc'}]"
                                    @click="sortToggle(sortData.delivcnt)"></button>
                            </th>
                            <th>주문상태
                                <button type="button" class="sort" :value="sortData.ordstatus"
                                    :class="[{up : sortData.ordstatus === 'ordstatus_asc'}, {down : sortData.ordstatus === 'ordstatus_desc'}]"
                                    @click="sortToggle(sortData.ordstatus)"></button>
                            </th>
                        </tr>
                    </thead>
                    <tbody v-if="list.length > 0">
                        <tr v-for="item in list" :key="item.orgdelividx">
                            <td><input type="checkbox" :id="item.orgdelividx" v-model="checkedList" :value="item.orgdelividx" :disabled="item.ispbgoods==='T'" @change="checkList($event.target.checked)"/></td>
                            <td v-if="!isPartner">{{ item.ordpathtypename }}</td>
                            <td>{{ item.orderdate }}<br>{{ item.ordertime }}</td>
                            <td>{{ item.paymentdate }}<br>{{ item.paymenttime }}</td>
                            <td><a href="javascript:void(0);" class="link" @click="goOrderDetail(item.ordno)">{{ item.ordno }}</a></td>
                            <td v-if="!isPartner">{{ item.isfrstorder }}</td>
                            <td v-if="!isPartner && item.isnonmember==='F'"><a href="javascript:void(0);" class="link" @click="goMemDetail(item.orduserno)">{{ item.ordname }}</a></td>
                            <td v-if="!isPartner && item.isnonmember==='T'">{{ item.ordname }}</td>
                            <td v-if="!isPartner">{{ item.ordid }}</td>
                            <td v-if="!isPartner">{{ $util.maskTel(item.ordtel) }}</td>
                            <td v-if="!isPartner">{{ item.isnonmembername }}</td>
                            <td v-if="!isPartner">{{ item.membertypename }}</td>
                            <td v-if="!isPartner">{{ item.memlvtypename }}</td>
                            <td v-if="!isPartner">{{ item.ispbgoodsname }}</td>
                            <td v-if="!isPartner">{{ item.dealername }}</td>
                            <td>{{ item.goodscode }}</td>
                            <td>{{ item.optioncode }}</td>
                            <td>{{ item.goodsturn }}</td>
                            <td>
                                <div class="img-thumb size60 link" @click="goFrontGoodsDetail(item.goodscode)" :class="{'no-image': $util.isNull(item.fullpath)}">
                                    <img :src="item.fullpath" v-if="!$util.isNull(item.fullpath)">
                                </div>
                            </td>
                            <td class="left no-left">
                                <a href="javascript:void(0);" class="link" @click="goGoodsDetail(item.goodsno)">{{ item.goodsname }}</a>
                            </td>
                            <td style="white-space: pre-wrap">{{ item.optionconts }}</td>
                            <td>{{ $util.maskComma(item.origincnt) }}</td>
                            <td class="right">{{ $util.maskComma(item.price) }}</td>
                            <td class="right">{{ $util.maskComma(item.totprice) }}</td>
                            <td>{{ item.paywaytypename }}</td>
                            <td v-if="!isPartner">{{ item.isaddgoods }}</td>
                            <td>{{ item.iscombdelivname }}</td>
                            <td>{{ item.delivfaretypename }}</td>
                            <td class="right">{{ $util.maskComma(item.delivamt) }}</td>
                            <td>{{ item.rcvname }}</td>
                            <td>{{ $util.maskTel(item.rcvtel) }}</td>
                            <td>{{ item.rcvaddr }}{{ $util.isNull(item.rcvaddrdetail)? '':' '+item.rcvaddrdetail }}</td>
                            <td>{{ item.logistypename }}</td>
                            <td v-if="item.delivtype===$store.getters['ADMIN'].DELIV_PARCEL">
                                <a href="javascript:void(0);" class="link" @click="goDelivTracking(item.logistype, item.invoiceno)">{{ item.invoiceno }}</a>
                            </td>
                            <td v-if="item.delivtype===$store.getters['ADMIN'].DELIV_DIRECT">
                                <!-- <button type="button" class="btn blue ml3" @click="goDirectDelivTracking(item)">배송조회</button> -->
                            </td>
                            <td>{{ item.dirdelivmsg }}</td>
                            <td>{{ $util.maskComma(item.delivcnt) }}</td>
                            <td><a href="javascript:void(0);" class="link" @click="goOrderHistory(item.orgdelividx)">{{ item.ordstatusname }}</a></td>
                        </tr>
                    </tbody>
                    <tbody v-else>
                        <tr><td :colspan="isPartner? 25:36">조회 결과가 존재하지 않습니다.</td></tr>
                    </tbody>
                </table>
            </div>
            <div class="bottom-group">
                <div class="paging">
                    <CommonPageNavigator v-show="isRead" :pagingData="pagingData" v-on:setPagingData="setPagingData" />
                </div>
            </div>
        </div>
        <OrderDetail v-if="isShowOrderDetail" :activeOrderCode="activeOrderCode" @closeDetail="closeOrderDetail"></OrderDetail>
        <AdminMemberInfo v-if="isShowMemDetail" :activeUserNo="activeUserNo" @closeDetail="closeMemDetail"></AdminMemberInfo>
        <GoodsDetail v-if="isGoodsDetailShow" :activeGoodsNo="activeGoodsNo" @closePopup="closeGoodsDetail"></GoodsDetail>
    </div>
    <!-- /컨텐츠 영역 -->
</template>

<script>
import CommonNavigator from '@views.admin/common/CommonNavigator.vue'
import CommonDatePicker from '@views.admin/common/CommonDatePicker';
import CommonPageNavigator from '@views.admin/common/CommonPageNavigator';
import OrderDetail from '@views.admin/order/info/OrderDetail.vue';
import AdminMemberInfo from '@views.admin/member/info/AdminMemberInfo.vue';
import GoodsDetail from '@views.admin/goods/manage/GoodsDetail.vue';
import OrderStatusHistoryPopup from '@views.admin/order/popup/OrderStatusHistoryPopup.vue';

export default {
    name: 'admin.order.manage.indelivery',
    components: {
        CommonNavigator,
        CommonDatePicker,
        CommonPageNavigator,
        GoodsDetail,
        OrderDetail,
        AdminMemberInfo
    },
    mounted() {
        this.isPartner = this.$util.isAuthorized(this.$store.getters['CONSTANTS'].PARTNER_USER);
        this.user = this.$util.getUser(this.$store.getters['CONSTANTS'].MANAGER_SESSION);
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
            isPartner: false,
            user: {},
            searchData: {
                isshowdetailarea: false,    // 상세 검색조건영역 펼침여부
                ordstatus: '',              // 조회할주문상태 (배송중)
                skeyArr: [
                    {key: '', name: '전체', isShowPartner: true, isShowAdmin: true},
                    {key: 'ordno', name: '주문번호', isShowPartner: true, isShowAdmin: true},
                    {key: 'ordname', name: '주문자명', isShowPartner: false, isShowAdmin: true},
                    {key: 'ordid', name: '주문자ID', isShowPartner: false, isShowAdmin: true},
                    {key: 'ordtel', name: '주문자연락처', isShowPartner: false, isShowAdmin: true},
                    {key: 'rcvname', name: '수령인명', isShowPartner: false, isShowAdmin: true},
                    {key: 'rcvtel', name: '수령인연락처', isShowPartner: false, isShowAdmin: true},
                    {key: 'goodscode', name: '상품코드', isShowPartner: true, isShowAdmin: false},
                    {key: 'goodsname', name: '상품명', isShowPartner: true, isShowAdmin: false},
                    {key: 'optioncode', name: '단품코드', isShowPartner: true, isShowAdmin: false},
                    {key: 'optionname', name: '옵션명', isShowPartner: true, isShowAdmin: false}
                ],
                skey: '',               // 직접검색KEY
                sword: '',              // 직접검색어
                gdkey: '',              // 상품검색KEY
                gdword: '',             // 상품검색어
                dtkey: '',              // 조회일자KEY
                period: '',             // 조회일자기간
                startdate: '',          // 조회시작일자
                enddate: '',            // 조회종료일자
                ispbgoods: '',          // 직매입여부
                dealerno: '',           // 입점업체번호
                partnerList: [],        // 파트너사 목록
                isallchannel: '',       // 전체채널여부
                muappchtypeArr: [],     // 다중적용채널Array
                pricekey: '',           // 금액조건KEY
                minprice: '',           // 최소금액
                maxprice: '',           // 최대금액
                isnonmember: '',        // 비회원주문여부
                isfrstorder: '',        // 첫구매여부
                isallmember: '',        // 다중대상회원유형전체여부
                mumembertypeArr: [],    // 다중대상회원유형Array
                isallmemlv: '',         // 다중대상회원등급전체여부
                mumemlvtypeArr: [],     // 다중대상회원등급Array
                isallpayway: '',        // 결제수단전체여부
                paywaytypeArr: [],      // 결제수단Array
                psort: 'orderday_desc'  // 정렬조건 (default: 최근주문일)
            },
            commonCode: {
                muappchtype: [],        // 적용채널
                dadamembertype: [],     // 다다픽회원유형
                memlvtype: [],          // 회원등급
                paywaytype: []         // 결제수단
            },
            sortData: {
                ordpathtype: 'ordpathtype_asc', // 주문경로
                orderdate: 'orderdate_asc',     // 주문일
                paymentdate: 'paymentdate_asc', // 결제일
                ordno: 'ordno_asc',             // 주문번호
                isfrstorder: 'isfrstorder_asc', // 첫구매여부
                ordname: 'ordname_asc',         // 주문자명
                ordid: 'ordid_asc',             // 주문자id
                ordtel: 'ordtel_asc',           // 주문자연락처
                isnonmember: 'isnonmember_asc', // 회원구분
                membertype: 'membertype_asc',   // 회원유형
                memlvtype: 'memlvtype_asc',     // 회원등급
                ispbgoods: 'ispbgoods_asc',     // 판매구분
                dealername: 'dealername_asc',   // 판매사명
                goodscode: 'goodscode_asc',     // 상품코드
                optioncode: 'optioncode_asc',   // 단품코드
                goodsturn: 'goodsturn_asc',     // 상품순번
                goodsname: 'goodsname_asc',     // 상품명
                optionconts: 'optionconts_asc', // 옵션내용
                ordcnt: 'ordcnt_asc',           // 주문수량
                price: 'price_asc',             // 판매단가
                totprice: 'totprice_asc',       // 판매가
                paywaytype: 'paywaytype_asc',   // 결제수단
                isaddgoods: 'isaddgoods_asc',   // 추가상품여부
                iscombdeliv: 'iscombdeliv_asc', // 배송유형
                delivfaretype: 'delivfaretype_asc', // 배송조건
                delivamt: 'delivamt_asc',       // 배송비
                rcvname: 'rcvname_asc',         // 수령인명
                rcvtel: 'rcvtel_asc',           // 수령인연락처
                rcvaddr: 'rcvaddr_asc',         // 수령인주소
                logistypename: 'logistypename_asc', // 택배사
                invoiceno: 'invoiceno_asc',     // 송장번호
                delivcnt: 'delivcnt_asc',       // 배송수량
                ordstatus: 'ordstatus_asc',     // 주문상태
                dirdelivmsg: 'dirdelivmsg_asc',     // 직배송 안내문구
            },
            pagingData: {
                pageCount: 20,      // 페이징 옵션(최대수)
                page: 1,            // 현재 페이지
                listCount: 0        // 총 수량
            },
            isallchk: false,        // 목록 전체체크여부
            list: [],               // 조회 데이터
            count: {                // 데이터 건수
                totalcnt: 0,        // 전체 건수
                invoicecnt: 0,      // 송장등록 건수
                noninvoicecnt: 0    // 송장미등록 건수
            },
            checkedList: [],        // 선택된 목록
            isRead : false,
            isWrite : false,
            activeGoodsNo : '',
            activeOrderCode: '',
            activeUserNo: '',
            isGoodsDetailShow: false,   // 상품상세 팝업 노출여부
            isShowMemDetail: false,     // 회원상세 팝업 노출여부
            isShowOrderDetail: false,   // 주문상세 팝업 노출여부
            isLink : false, //대시보드에서 링크를 타고왔는지 체크
        };
    },
    methods: {
        // 화면 초기화
        onInit: function() {
            if(typeof this.$route.params.type !== 'undefined' && this.$route.params.type === 'LINK'){
              this.isLink = true;
            }
            this.searchData.ordstatus = this.$store.getters['ADMIN'].ORDER_IN_DELIVERY;
            // 공통코드 조회
            this.getCommonCodeList();
            // 파트너사 목록 조회
            this.getPartnerList();
        },
        // 검색조건 초기화
        initSearchData: function () {
            this.searchData.skey ='';
            this.searchData.sword = '';
            this.searchData.dtkey = 'order';
            this.searchData.period = 'aday_0';
            this.searchData.startdate = this.$util.getDate('-');
            this.searchData.enddate = this.$util.getDate('-');
            
            if (!this.isPartner) {
                for (let i=this.searchData.skeyArr.length-1; i>0; i--) {
                    if (!this.searchData.skeyArr[i].isShowAdmin) {
                        this.searchData.skeyArr.splice(i, 1);
                    }
                }
                this.searchData.ispbgoods = '';
                this.searchData.dealerno = '';
                this.searchData.gdkey = 'goodsname';
                this.searchData.gdword = '';
                this.searchData.isallchannel = 'T';
                this.searchData.pricekey = 'goods';
                this.searchData.minprice = '';
                this.searchData.maxprice = '';
                this.searchData.isnonmember = '';
                this.searchData.isfrstorder = '';
                this.searchData.isallmember = 'T';
                this.searchData.isallmemlv = 'T';
                this.searchData.isallpayway = 'T';

                this.checkAllChannel();
                this.checkAllMembertype();
                this.checkAllMemlvtype();
                this.checkAllPaywaytype();
            } else {
                for (let i=this.searchData.skeyArr.length-1; i>0; i--) {
                    if (!this.searchData.skeyArr[i].isShowPartner) {
                        this.searchData.skeyArr.splice(i, 1);
                    }
                }
                this.searchData.ispbgoods = 'F';
                this.searchData.dealerno = this.user.no;
            }
        },
        // 목록 조회
        searchList: function(page) {
            // 조회일자 1년 이내 체크
            if (this.$util.isNull(this.searchData.startdate) || this.$util.isNull(this.searchData.enddate)){
                alert('조회일자는 필수 조회조건 입니다.');
                return;
            } else {
                let startDate = this.searchData.startdate.replaceAll('-', '');
                let endDate = this.searchData.enddate.replaceAll('-', '');
                let checkMaxDate = this.$util.getAddDate(this.$util.getAddYear(startDate, 1), -1);
                if (endDate > checkMaxDate) {
                    alert('조회일자는 최대 1년까지만 조회 가능합니다.');
                    return;
                }
            }

            // 목록조회
            this.pagingData.page = this.$util.isNull(page)? this.pagingData.page : 1;
            let params = Object.assign(this.searchData, this.pagingData);
            params.isPartner = this.isPartner;
            params.isloading = true;
            this.$http.post('/admin/order/manage/list', params)
                .then(result => {
                    this.$util.debug(result);
                    let data = result.data;
                    this.list = data.list;
                    this.count = data.count;
                    this.pagingData.listCount = data.count.totalcnt;
                    this.$util.dataSetSearchParam(this);
                    this.isallchk = false;
                    this.checkedList = [];
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 공통코드 목록 조회
        getCommonCodeList: function() {
            let cmclassArr = ['MUAPPCHTYPE', 'DADAMEMBERTYPE', 'MEMLVTYPE', 'PAYWAYTYPE'];
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
                      this.searchData.startdate = linkParam.startdate;
                      this.searchData.enddate = linkParam.enddate;
                    }
                    // 목록 조회
                    this.searchList();
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
                    this.searchData.partnerList = result.data.list;
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 조회조건 - 적용채널 전체 체크
        checkAllChannel: function() {
            let isAllCheck = this.searchData.isallchannel;
            this.searchData.muappchtypeArr = [];
            if (isAllCheck == 'T') {
                for(let type of this.commonCode.muappchtype){
                    this.searchData.muappchtypeArr.push(type.cmcode);
                }
            }
        },
        // 조회조건 - 대상회원유형 전체체크
        checkAllMembertype: function() {
            let isAllCheck = this.searchData.isallmember;
            this.searchData.mumembertypeArr = [];
            if (isAllCheck == 'T') {
                for(let type of this.commonCode.dadamembertype){
                    this.searchData.mumembertypeArr.push(type.cmcode);
                }
            }
        },
        // 조회조건 - 대상회원등급 전체체크
        checkAllMemlvtype: function() {
            let isAllCheck = this.searchData.isallmemlv;
            this.searchData.mumemlvtypeArr = [];
            if (isAllCheck == 'T') {
                for(let type of this.commonCode.memlvtype){
                    this.searchData.mumemlvtypeArr.push(type.cmcode);
                }
            }
        },
        // 조회조건 - 결제수단 전체체크
        checkAllPaywaytype: function() {
            let isAllCheck = this.searchData.isallpayway;
            this.searchData.paywaytypeArr = [];
            if (isAllCheck == 'T') {
                for(let type of this.commonCode.paywaytype){
                    this.searchData.paywaytypeArr.push(type.cmcode);
                }
            }
        },
        // 목록 전체체크
        checkAllList: function(value) {
            this.checkedList = [];
            if (value) {
                this.list.forEach(item => {
                    if (item.ispbgoods === 'F') {
                        this.checkedList.push(item.orgdelividx);
                    }
                });
            }
        },
        // 목록 개별체크
        checkList: function() {
            let checkLength = this.list.filter(item => {
                return item.ispbgoods === 'F';
            }).length;
            if (checkLength > this.checkedList.length){
                this.isallchk = false;
            } else {
                this.isallchk = true;
            }
        },
        // 날짜 picker 콜백 함수
        onChangeStartDate(value) {
            this.searchData.startdate = value;
        },
        // 날짜 picker 콜백 함수
        onChangeEndDate(value) {
            this.searchData.enddate = value;
        },
        // 페이징 콜백
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

            this.sortData = this.$options.data().sortData;
            this.sortData[sortKey] = sortName;
            this.searchData.psort = sortName;

            this.searchList();
        },
        // 엑셀다운로드
        fnExcelDownload: function() {
            if (this.list.length == 0) {
                alert('다운로드할 내역이 존재하지 않습니다.');
                return;
            }
            let config = { responseType: 'arraybuffer' };
            let params = this.searchData;
            params.isPartner = this.isPartner;
            params.fileName = '배송중_주문목록.xlsx';
            this.$http.post('/admin/order/manage/exceldown', params, config)
                .then(result => {
                    this.$util.debug(result);
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 배송완료 전 체크
        checkValidCompDeliv: function(reqStatus) {
            let params = {
                orderdelivList: [],
                ordstatus: reqStatus,
                confMsg : ''
            }
            if (this.checkedList.length === 0) {
                alert('처리할 목록을 선택해주세요.');
                return;
            }
            for (let i=0; i<this.checkedList.length; i++) {
                let orgdelividx = this.checkedList[i];
                for (let j=0; j<this.list.length; j++) {
                    let item = this.list[j];
                    if (orgdelividx === item.orgdelividx) {
                        if (item.ispbgoods === 'T') {
                            alert('파트너사 상품만 배송완료 처리가 가능합니다. 확인후 진행해주세요.');
                            return;
                        }
                        if (item.ordstatus != this.$store.getters['ADMIN'].ORDER_IN_DELIVERY) {
                            alert('배송중인 상품만 배송완료 처리가 가능합니다. 확인후 진행해주세요.');
                            return;
                        }
                        params.orderdelivList.push(item);
                    }
                }
            }
            
            params.isloading = false;
            this.$http.post('/admin/order/manage/compdeliv/valid', params).then(result => {
                if (result.data.resultcnt > 0) {
                    params.confMsg = '동일한 송장정보를 가진 상품이 존재합니다. 일괄 배송완료 처리하시겠습니까?';
                } else {
                    params.confMsg = '배송완료로 처리 하시겠습니까?';
                }
                this.fnChangeOrdStatus(params);
            }).catch(error => {
                this.$util.debug(error);
            });
        },
        // 주문상태 변경
        fnChangeOrdStatus: function(params) {
            if (confirm(params.confMsg)) {
                if (this.isPartner) {
                    params.dealerno = this.user.no;
                }
                this.$http.post('/admin/order/manage/status/update', params)
                    .then(result => {
                        this.$util.debug(result);
                        if (result.statusCode == 200) {
                            alert('처리가 완료되었습니다.');
                            this.searchList();
                        }
                    })
                    .catch(error => {
                        this.$util.debug(error);
                    });
            }
        },
        // 배송추적
        goDelivTracking: function(logistype, invoiceno) {
            let params = {
                invoiceno: invoiceno,
                logistype: logistype
            }
            this.$http.post('/common/deliv/tracking', params)
                .then(result => {
                    this.$util.debug(result);
                    window.open(result.data.url, "_blank", "width=400,height=650");
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 직배송 배송조회 팝업
        goDirectDelivTracking: function(obj) {
            // TODO: 직배송 배송조회 팝업
            alert("상품명: " +  obj.goodsname + ", 직배송 배송조회 팝업 구현예정");
        },
        // Front 화면으로 이동
        goFrontGoodsDetail: function(value) {
            window.open(process.env.VUE_APP_PC_GOODS_DETAIL_URL + value, "_blank");
        },
        // 주문상세 팝업 오픈
        goOrderDetail: function(value) {
            this.isShowOrderDetail = true;
            this.activeOrderCode = value;
        },
        // 주문상세 팝업 닫기
        closeOrderDetail: function(isreload) {
            this.isShowOrderDetail = false;
            if (isreload) {
                this.searchList();
            }
        },
        // 회원상세 팝업 오픈
        goMemDetail: function(value) {
            this.isShowMemDetail = true;
            this.activeUserNo = value;
        },
        // 회원상세 팝업 닫기
        closeMemDetail: function() {
            this.isShowMemDetail = false;
        },
        // 상품상세 팝업 오픈
        goGoodsDetail: function(value) {
            this.activeGoodsNo = value;
            this.isGoodsDetailShow = true;
        },
        // 상품상세 팝업 닫기
        closeGoodsDetail: function () {
            this.isGoodsDetailShow = false;
        },
        // 주문상태변경이력 팝업 오픈
        goOrderHistory: function(value) {
            let param = { orgdelividx: value };
            this.$eventBus.$emit('modalShow', OrderStatusHistoryPopup, param, null);
        }
    },
    watch: {
        // 조회기간
        'searchData.period': function (value) {
            let params = value.split('_');
            let type = params[0];
            let addValue = parseInt(params[1]) * -1;

            if (type == 'aday') {
                this.searchData.startdate = this.$util.getAddDate(this.$util.getDate(), addValue, '-');
                this.searchData.enddate = this.$util.getAddDate(this.$util.getDate(), addValue, '-');
            } else if (type == 'days') {
                this.searchData.startdate = this.$util.getAddDate(this.$util.getDate(), addValue, '-');
                this.searchData.enddate = this.$util.getDate('-');
            } else if (type == 'months') {
                this.searchData.startdate = this.$util.getAddMonth(this.$util.getDate(), addValue, '-');
                this.searchData.enddate = this.$util.getDate('-');
            }
        },
        // 주문경로
        'searchData.muappchtypeArr': function(value) {
            if (value.length < this.commonCode.muappchtype.length) {
                this.searchData.isallchannel = 'F';
            } else {
                this.searchData.isallchannel = 'T';
            }
        },
        // 대상회원유형
        'searchData.mumembertypeArr': function(value) {
            if (value.length < this.commonCode.dadamembertype.length) {
                this.searchData.isallmember = 'F';
            } else {
                this.searchData.isallmember = 'T';
            }
        },
        // 대상회원등급
        'searchData.mumemlvtypeArr': function(value) {
            if (value.length < this.commonCode.memlvtype.length) {
                this.searchData.isallmemlv = 'F';
            } else {
                this.searchData.isallmemlv = 'T';
            }
        },
        // 결제수단
        'searchData.paywaytypeArr': function(value) {
            if (value.length < this.commonCode.paywaytype.length) {
                this.searchData.isallpayway = 'F';
            } else {
                this.searchData.isallpayway = 'T';
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