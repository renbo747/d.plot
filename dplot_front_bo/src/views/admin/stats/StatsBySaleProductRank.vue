<template>
  <!-- 컨텐츠 영역 -->
  <div class="content m-leftmenu">
    <CommonNavigator></CommonNavigator>
    <div class="inner">
      <div class="boxing search-area pd0">
        <dl>
          <dt>조회기간</dt>
          <dd>
            <CommonDatePicker :value="searchData.startdate" @change="onChangeStartDate"/>
            <span>-</span>
            <CommonDatePicker :value="searchData.enddate" @change="onChangeEndDate"/>
            <div class="radio_wrap">
              <input type="radio" v-model="searchData.period" id="rd1" value='-1'/><label for="rd1">어제</label>
              <input type="radio" v-model="searchData.period" id="rd2" value='0'/><label for="rd2">오늘</label>
              <input type="radio" v-model="searchData.period" id="rd3" value='7'/><label for="rd3">일주일</label>
              <input type="radio" v-model="searchData.period" id="rd4" value='1'/><label for="rd4">1개월</label>
              <input type="radio" v-model="searchData.period" id="rd5" value='3'/><label for="rd5">3개월</label>
              <input type="radio" v-model="searchData.period" id="rd6" value='6'/><label for="rd6">6개월</label>
            </div>
          </dd>
        </dl>
        <dl>
          <dt>매출산정기준</dt>
          <dd>
            <div class="radio_wrap wide">
              <input type="radio" name="group06" id="group61" v-model="searchData.amtstandard" value="NORMAL"><label for="group61">일반기준</label>
              <input type="radio" name="group06" id="group62" v-model="searchData.amtstandard" value="ACCOUNT"><label for="group62">회계기준</label>
            </div>
          </dd>
        </dl>
        <dl>
          <dt>조회기준</dt>
          <dd>
            <div class="radio_wrap wide">
              <input type="radio" name="group07" id="group71" v-model="searchData.standard" value="PAY"><label for="group71">결제완료</label>
              <input type="radio" name="group07" id="group72" v-model="searchData.standard" value="BUY"><label for="group72">구매확정</label>
            </div>
          </dd>
        </dl>
        <dl>
          <dt>상품구분</dt>
          <dd>
            <div class="radio_wrap wide">
              <input type="radio" name="group00" id="group01" v-model="searchData.gubun" value="GOODS" @change="searchList"><label for="group01">상품별</label>
              <input type="radio" name="group00" id="group02" v-model="searchData.gubun" value="OPTION" @change="searchList"><label for="group02">단품별</label>
            </div>
          </dd>
        </dl>
        <dl>
          <dt>상품분류</dt>
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
          <dt>직접검색</dt>
          <dd>
            <select v-model="searchData.skey">
              <option value="">전체</option>
              <option value="dealercode">공급사코드</option>
              <option value="dealername">공급사명</option>
              <option value="brandcode">브랜드코드</option>
              <option value="brandname">브랜드명</option>
              <option value="goodsno">상품코드</option>
              <option value="optioncode">단품코드</option>
              <option value="goodsname">상품명</option>
              <option value="optionname">옵션명</option>
            </select>
            <input type="text" v-model="searchData.sword" maxlength="200" @keyup.enter="searchList(1)"/>
          </dd>
        </dl>
        <dl>
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
        <dl>
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
        <dl>
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
        <dl>
          <dt>주문경로</dt>
          <dd>
            <div class="check-wrap">
              <input type="checkbox" id="chkAllAppch" v-model="searchData.isallchannel" true-value="T" false-value="F" @change="checkAllAppchtype">
              <label for="chkAllAppch">전체</label>
            </div>
            <div class="check-wrap" v-for="item in commonCode.muappchtype" :key="item.cmcode">
              <input type="checkbox" :id="'muappchtype_'+item.cmcode" v-model="searchData.muappchtypeArr" :true-value="[]" :value="item.cmcode">
              <label :for="'muappchtype_'+item.cmcode">{{ item.codename }}</label>
            </div>
          </dd>
          <dd style="width: 130px;">
            <input type="checkbox" id="chk_searchAll" v-model="searchData.isTotCheck" true-value="T" false-value="F" @change="checkTotAll"><label for="chk_searchAll" class="txt-blue">조건 전체체크</label>
          </dd>
        </dl>
      </div>
      <div class="btn-group" v-show="isRead">
        <button type="button" class="btn big blue" @click="searchList">검색</button>
        <button type="button" class="btn big gray" @click="initSearchData">초기화</button>
      </div>
      <div class="caption-group mt10 clearfix">
        <div class="total-group fl">
          <span class="total">전체 <strong>{{ totalCnt }}</strong>건</span>
        </div>
        <div class="btn-group fr">
          <i class="icon-alert"></i><span class="txt-orange mr5">쿠폰할인은 상품쿠폰할인금액만 취합</span>
          <button type="button" class="btn green-line" v-show="isRead" @click="goExcelDownload"><i class="icon-excel"></i>엑셀다운로드</button>
          <select v-model="pagingData.pageCount" v-show="isRead">
            <option value="20">20개씩 보기</option>
            <option value="50">50개씩 보기</option>
            <option value="100">100개씩 보기</option>
          </select>
        </div>
      </div>
      <!-- 상품 별 -->
      <table cellpadding="0" cellspacing="0" class="data-tb align-r" v-if="searchData.gubun === 'GOODS'">
        <caption>목록</caption>
        <colgroup>
          <col width="3.5%" /><!-- 순위 -->
          <col width="7%" /><!-- 상품코드 -->
          <col width="" /><!-- 상품명 -->
          <col width="6.5%" /><!-- 주문수량 ① -->
          <col width="5.5%" /><!-- 환불수량 ② -->
          <col width="6.5%" /><!-- 판매수량 ①-② -->
          <col width="7%" /><!-- 취급고 합계 -->
          <col width="7%" /><!-- 매출 합계 ⓐ -->
          <col width="7%" /><!-- 환불 합계 ⓑ -->
          <col width="6%" /><!-- 영업이익 -->
          <col width="8%" /><!-- 영업이익률(%) -->
          <col width="6%" /><!-- 상품쿠폰할인 -->
        </colgroup>
        <thead>
        <tr>
          <th>순위
            <button type="button" :value="sortData.rank" class="sort"
                    :class="[{up : sortData.rank === 'rank_asc'}, {down : sortData.rank === 'rank_desc'}]"
                    @click="sortToggle(sortData.rank)"></button>
          </th>
          <th>상품코드
            <button type="button" :value="sortData.goodscode" class="sort"
                    :class="[{up : sortData.goodscode === 'goodscode_asc'}, {down : sortData.goodscode === 'goodscode_desc'}]"
                    @click="sortToggle(sortData.goodscode)"></button>
          </th>
          <th>상품명
            <button type="button" :value="sortData.goodsname" class="sort"
                    :class="[{up : sortData.goodsname === 'goodsname_asc'}, {down : sortData.goodsname === 'goodsname_desc'}]"
                    @click="sortToggle(sortData.goodsname)"></button>
          </th>
          <th>순 주문수량 ①
            <button type="button" :value="sortData.frstordcnt" class="sort"
                    :class="[{up : sortData.frstordcnt === 'frstordcnt_asc'}, {down : sortData.frstordcnt === 'frstordcnt_desc'}]"
                    @click="sortToggle(sortData.frstordcnt)"></button>
          </th>
          <th>환불수량 ②
            <button type="button" :value="sortData.clmcnt" class="sort"
                    :class="[{up : sortData.clmcnt === 'clmcnt_asc'}, {down : sortData.clmcnt === 'clmcnt_desc'}]"
                    @click="sortToggle(sortData.clmcnt)"></button>
          </th>
          <th>판매수량 ①-②
            <button type="button" :value="sortData.recnt" class="sort"
                    :class="[{up : sortData.recnt === 'recnt_asc'}, {down : sortData.recnt === 'recnt_desc'}]"
                    @click="sortToggle(sortData.recnt)"></button>
          </th>
          <th>
            <i class="icon-th-q" @click="toggleData.billing = !toggleData.billing"></i>취급고 합계
            <button type="button" :value="sortData.billing" class="sort"
                    :class="[{up : sortData.billing === 'billing_asc'}, {down : sortData.billing === 'billing_desc'}]"
                    @click="sortToggle(sortData.billing)"></button>
            <div class="th-cell" :class="[{ 'dpb' : toggleData.billing}, { 'dpn' : !toggleData.billing}]" style="width: 130px;"><!-- dpb:보임 / dpn:숨김 -->
              순주문금액 - 환불합계
            </div>
          </th>
          <th>
            <i class="icon-th-q" @click="toggleData.price = !toggleData.price"></i>매출 합계 ⓐ
            <button type="button" :value="sortData.price" class="sort"
                    :class="[{up : sortData.price === 'price_asc'}, {down : sortData.price === 'price_desc'}]"
                    @click="sortToggle(sortData.price)"></button>
            <div class="th-cell" :class="[{dpb : toggleData.price}, {dpn : !toggleData.price}]" style="width: 345px;"><!-- dpb:보임 / dpn:숨김 -->
              매출(일반기준) = 판매가(프로모션할인가)*수량<br>
              매출(회계기준) = 판매가(프로모션할인가)*수량 – 쿠폰할인 + 배송비
            </div>
          </th>
          <th>
            <i class="icon-th-q" @click="toggleData.rtnamt = !toggleData.rtnamt"></i>환불 합계 ⓑ
            <button type="button" :value="sortData.rtnamt" class="sort"
                    :class="[{up : sortData.rtnamt === 'rtnamt_asc'}, {down : sortData.rtnamt === 'rtnamt_desc'}]"
                    @click="sortToggle(sortData.rtnamt)"></button>
            <div class="th-cell" :class="[{dpb : toggleData.rtnamt}, {dpn : !toggleData.rtnamt}]" style="width: 325px;"><!-- dpb:보임 / dpn:숨김 -->
              고객반환 실 환불금액(적립금/D포인트/임직원포인트 합산 금액)
            </div>
          </th>
          <th>
            <i class="icon-th-q" @click="toggleData.margin = !toggleData.margin"></i>영업이익
            <button type="button" :value="sortData.margin" class="sort"
                    :class="[{up : sortData.margin === 'margin_asc'}, {down : sortData.margin === 'margin_desc'}]"
                    @click="sortToggle(sortData.margin)"></button>
            <div class="th-cell" :class="[{dpb : toggleData.margin}, {dpn : !toggleData.margin}]" style="width: 240px;"><!-- dpb:보임 / dpn:숨김 -->
              영업이익 = 위탁상품 마진 + 직매입 마진<br>
              - 위탁상품 마진 : 판매가 * 각파트너사 수수료<br>
              - 직매입 마진 : 판매가 - 원가
            </div>
          </th>
          <th>
            <i class="icon-th-q" @click="toggleData.marginper = !toggleData.marginper"></i>영업이익률(%)
            <button type="button" :value="sortData.marginper" class="sort"
                    :class="[{up : sortData.marginper === 'marginper_asc'}, {down : sortData.marginper === 'marginper_desc'}]"
                    @click="sortToggle(sortData.marginper)"></button>
            <div class="th-cell" :class="[{dpb : toggleData.marginper}, {dpn : !toggleData.marginper}]" style="width: 110px;"><!-- dpb:보임 / dpn:숨김 -->
              영업이익 / 순매출
            </div>
          </th>
          <th>상품쿠폰할인
            <button type="button" :value="sortData.goodscpnamt" class="sort"
                    :class="[{up : sortData.goodscpnamt === 'goodscpnamt_asc'}, {down : sortData.goodscpnamt === 'goodscpnamt_desc'}]"
                    @click="sortToggle(sortData.goodscpnamt)"></button>
          </th>
        </tr>
        </thead>
        <tbody v-if="listData.length > 0">
        <tr class="bg blue">
          <td colspan="3">합계</td>
          <td>{{ frstordcnt }}</td>
          <td>{{ clmcnt }}</td>
          <td>{{ recnt }}</td>
          <td>{{ billing }}</td>
          <td>{{ price }}</td>
          <td>{{ rtnamt }}</td>
          <td>{{ margin }}</td>
          <td>{{ marginper }}%</td>
          <td>{{ goodscpnamt }}</td>
        </tr>
        <tr v-for="(row, index) in listData" v-bind:key="index">
          <td class="center">{{ row.ranking }}</td>
          <td class="center">{{ row.goodscode }}</td>
          <td class="left">{{ row.goodsname }}</td>
          <td>{{ row.frstordcnt }}</td>
          <td>{{ row.clmcnt }}</td>
          <td>{{ row.recnt }}</td>
          <td>{{ row.billing }}</td>
          <td>{{ row.price }}</td>
          <td>{{ row.rtnamt}}</td>
          <td>{{ row.margin }}</td>
          <td>{{ row.marginper }}%</td>
          <td>{{ row.goodscpnamt }}</td>
        </tr>
        </tbody>
        <tbody v-else>
        <tr>
          <td colspan="13" class="center">조회 결과가 존재하지 않습니다.</td>
        </tr>
        </tbody>
      </table>
      <!-- //상품 별 -->
      <!-- 단품 별 -->
      <table cellpadding="0" cellspacing="0" class="data-tb align-r" v-if="searchData.gubun === 'OPTION'">
        <caption>목록</caption>
        <colgroup>
          <col width="3.5%" /><!-- 순위 -->
          <col width="6.5%" /><!-- 상품코드 -->
          <col width="5%" /><!-- 단품코드 -->
          <col width="" /><!-- 상품명 -->
          <col width="8%" /><!-- 옵션명 -->
          <col width="7%" /><!-- 주문수량 ① -->
          <col width="6%" /><!-- 환불수량 ② -->
          <col width="7%" /><!-- 판매수량 ①-② -->
          <col width="7%" /><!-- 취급고 합계 -->
          <col width="7%" /><!-- 매출 합계 ⓐ -->
          <col width="7%" /><!-- 환불 합계 ⓑ -->
          <col width="7%" /><!-- 영업이익 -->
          <col width="8%" /><!-- 영업이익률(%) -->
          <col width="7%" /><!-- 상품쿠폰할인 -->
        </colgroup>
        <thead>
        <tr>
          <th>순위
            <button type="button" :value="sortData.rank" class="sort"
                    :class="[{up : sortData.rank === 'rank_asc'}, {down : sortData.rank === 'rank_desc'}]"
                    @click="sortToggle(sortData.rank)"></button>
          </th>
          <th>상품코드
            <button type="button" :value="sortData.goodscode" class="sort"
                    :class="[{up : sortData.goodscode === 'goodscode_asc'}, {down : sortData.goodscode === 'goodscode_desc'}]"
                    @click="sortToggle(sortData.goodscode)"></button>
          </th>
          <th>단품코드
            <button type="button" :value="sortData.optioncode" class="sort"
                    :class="[{up : sortData.optioncode === 'optioncode_asc'}, {down : sortData.optioncode === 'optioncode_desc'}]"
                    @click="sortToggle(sortData.optioncode)"></button>
          </th>
          <th>상품명
            <button type="button" :value="sortData.goodsname" class="sort"
                    :class="[{up : sortData.goodsname === 'goodsname_asc'}, {down : sortData.goodsname === 'goodsname_desc'}]"
                    @click="sortToggle(sortData.goodsname)"></button>
          </th>
          <th>옵션명
            <button type="button" :value="sortData.optionname" class="sort"
                    :class="[{up : sortData.optionname === 'optionname_asc'}, {down : sortData.optionname === 'optionname_desc'}]"
                    @click="sortToggle(sortData.optionname)"></button>
          </th>
          <th>순 주문수량 ①
            <button type="button" :value="sortData.frstordcnt" class="sort"
                    :class="[{up : sortData.frstordcnt === 'frstordcnt_asc'}, {down : sortData.frstordcnt === 'frstordcnt_desc'}]"
                    @click="sortToggle(sortData.frstordcnt)"></button>
          </th>
          <th>환불수량 ②
            <button type="button" :value="sortData.clmcnt" class="sort"
                    :class="[{up : sortData.clmcnt === 'clmcnt_asc'}, {down : sortData.clmcnt === 'clmcnt_desc'}]"
                    @click="sortToggle(sortData.clmcnt)"></button>
          </th>
          <th>판매수량 ①-②
            <button type="button" :value="sortData.recnt" class="sort"
                    :class="[{up : sortData.recnt === 'recnt_asc'}, {down : sortData.recnt === 'recnt_desc'}]"
                    @click="sortToggle(sortData.recnt)"></button>
          </th>
          <th>
            <i class="icon-th-q" @click="toggleData.billing = !toggleData.billing"></i>취급고 합계
            <button type="button" :value="sortData.billing" class="sort"
                    :class="[{up : sortData.billing === 'billing_asc'}, {down : sortData.billing === 'billing_desc'}]"
                    @click="sortToggle(sortData.billing)"></button>
            <div class="th-cell" :class="[{ 'dpb' : toggleData.billing}, { 'dpn' : !toggleData.billing}]" style="width: 130px;"><!-- dpb:보임 / dpn:숨김 -->
              순주문금액 - 환불합계
            </div>
          </th>
          <th>
            <i class="icon-th-q" @click="toggleData.price = !toggleData.price"></i>매출 합계 ⓐ
            <button type="button" :value="sortData.price" class="sort"
                    :class="[{up : sortData.price === 'price_asc'}, {down : sortData.price === 'price_desc'}]"
                    @click="sortToggle(sortData.price)"></button>
            <div class="th-cell" :class="[{dpb : toggleData.price}, {dpn : !toggleData.price}]" style="width: 345px;"><!-- dpb:보임 / dpn:숨김 -->
              매출(일반기준) = 판매가(프로모션할인가)*수량<br>
              매출(회계기준) = 판매가(프로모션할인가)*수량 – 쿠폰할인 + 배송비
            </div>
          </th>
          <th>
            <i class="icon-th-q" @click="toggleData.rtnamt = !toggleData.rtnamt"></i>환불 합계 ⓑ
            <button type="button" :value="sortData.rtnamt" class="sort"
                    :class="[{up : sortData.rtnamt === 'rtnamt_asc'}, {down : sortData.rtnamt === 'rtnamt_desc'}]"
                    @click="sortToggle(sortData.rtnamt)"></button>
            <div class="th-cell" :class="[{dpb : toggleData.rtnamt}, {dpn : !toggleData.rtnamt}]" style="width: 325px;"><!-- dpb:보임 / dpn:숨김 -->
              고객반환 실 환불금액(적립금/D포인트/임직원포인트 합산 금액)
            </div>
          </th>
          <th>
            <i class="icon-th-q" @click="toggleData.margin = !toggleData.margin"></i>영업이익
            <button type="button" :value="sortData.margin" class="sort"
                    :class="[{up : sortData.margin === 'margin_asc'}, {down : sortData.margin === 'margin_desc'}]"
                    @click="sortToggle(sortData.margin)"></button>
            <div class="th-cell" :class="[{dpb : toggleData.margin}, {dpn : !toggleData.margin}]" style="width: 240px;"><!-- dpb:보임 / dpn:숨김 -->
              영업이익 = 위탁상품 마진 + 직매입 마진<br>
              - 위탁상품 마진 : 판매가 * 각파트너사 수수료<br>
              - 직매입 마진 : 판매가 - 원가
            </div>
          </th>
          <th>
            <i class="icon-th-q" @click="toggleData.marginper = !toggleData.marginper"></i>영업이익률(%)
            <button type="button" :value="sortData.marginper" class="sort"
                    :class="[{up : sortData.marginper === 'marginper_asc'}, {down : sortData.marginper === 'marginper_desc'}]"
                    @click="sortToggle(sortData.marginper)"></button>
            <div class="th-cell" :class="[{dpb : toggleData.marginper}, {dpn : !toggleData.marginper}]" style="width: 110px;"><!-- dpb:보임 / dpn:숨김 -->
              영업이익 / 순매출
            </div>
          </th>
          <th>상품쿠폰할인
            <button type="button" :value="sortData.goodscpnamt" class="sort"
                    :class="[{up : sortData.goodscpnamt === 'goodscpnamt_asc'}, {down : sortData.goodscpnamt === 'goodscpnamt_desc'}]"
                    @click="sortToggle(sortData.goodscpnamt)"></button>
          </th>
        </tr>
        </thead>
        <tbody v-if="listData.length > 0">
        <tr class="bg blue">
          <td colspan="5">합계</td>
          <td>{{ frstordcnt }}</td>
          <td>{{ clmcnt }}</td>
          <td>{{ recnt }}</td>
          <td>{{ billing }}</td>
          <td>{{ price }}</td>
          <td>{{ rtnamt }}</td>
          <td>{{ margin }}</td>
          <td>{{ marginper }}%</td>
          <td>{{ goodscpnamt }}</td>
        </tr>
        <tr v-for="(row, index) in listData" v-bind:key="index">
          <td class="center">{{ row.ranking }}</td>
          <td class="center">{{ row.goodscode }}</td>
          <td class="center">{{ row.optioncode }}</td>
          <td class="left">{{ row.goodsname }}</td>
          <td class="left">{{ row.optionname}}
<!--            <span class="dpb">- 크기 : 245</span>-->
<!--            <span class="dpb">- 색상 : 빨강</span>-->
          </td>
          <td>{{ row.frstordcnt }}</td>
          <td>{{ row.clmcnt }}</td>
          <td>{{ row.recnt }}</td>
          <td>{{ row.billing }}</td>
          <td>{{ row.price }}</td>
          <td>{{ row.rtnamt}}</td>
          <td>{{ row.margin }}</td>
          <td>{{ row.marginper }}%</td>
          <td>{{ row.goodscpnamt }}</td>
        </tr>
        </tbody>
        <tbody v-else>
        <tr>
          <td colspan="15" class="center">조회 결과가 존재하지 않습니다.</td>
        </tr>
        </tbody>
      </table>
      <!-- //단품 별 -->
      <div class="bottom-group">
        <CommonPageNavigator v-show="isRead" :pagingData="pagingData" v-on:setPagingData="setPagingData"/>
      </div>
    </div>
  </div>
  <!-- /컨텐츠 영역 -->
</template>

<script>
import CommonNavigator from "@views.admin/common/CommonNavigator.vue";
import CommonPageNavigator from "@views.admin/common/CommonPageNavigator.vue";
import CommonDatePicker from "@views.admin/common/CommonDatePicker.vue";

export default {
  name: "admin.stats.salerank",
  components: {
    CommonNavigator,
    CommonPageNavigator,
    CommonDatePicker
  },
  data() {
    return {
      isRead: false,
      isWrite: false,
      searchData: {
        skey: '',               // 직접검색 조건
        sword: '',              // 직접검색 단어
        startdate: '',          // 조회시작날짜
        enddate: '',            // 조회종료날짜
        period: '3',             // 조회기간
        gubun : 'GOODS',
        standard : 'PAY',
        amtstandard : 'NORMAL',
        isnonmember: '',        // 비회원주문여부
        isallchannel: 'T',      // 다중적용채널전체여부
        muappchtypeArr: [],     // 다중적용채널Array
        isallmember: 'T',       // 다중대상회원유형전체여부
        mumembertypeArr: [],    // 다중대상회원유형Array
        isallmemlv: 'T',        // 다중대상회원등급전체여부
        mumemlvtypeArr: [],     // 다중대상회원등급Array
        isallsell: 'T',
        muselltypeArr: [],
        depth1cateidx: '',      //대분류일련번호
        depth2cateidx: '',      //중분류일련번호
        depth3cateidx: '',      //소분류일련번호
        depth4cateidx: '',      //세분류일련번호
        psort: '',   // 정렬
        isTotCheck : 'T',
      },
      pagingData: {
        pageCount: 20,      // 페이징 옵션(최대수)
        page: 1,            // 현재 페이지
        listCount: 0        // 총 수량
      },
      commonCode: {
        muappchtype: [],    // 다중적용채널
        dadamembertype: [], // 다다픽회원유형
        memlvtype: [],      // 회원등급
        goodsselltype: [],
      },
      categoryObj: {
        depth1list: [],
        depth2list: [],
        depth3list: [],
        depth4list: [],
      },
      sortData: {
        rank : 'rank_asc',
        goodscode : 'goodscode_asc',
        goodsname : 'goodsname_asc',
        optioncode : 'optioncode_asc',
        optionname : 'optionname_asc',
        frstordcnt : 'frstordcnt_desc',
        clmcnt : 'clmcnt_desc',
        recnt : 'recnt_desc',
        billing : 'billing_desc',
        price : 'price_desc',
        rtnamt : 'rtnamt_desc',
        orgamt : 'orgamt_desc',
        margin : 'margin_desc',
        marginper : 'marginper_desc',
        goodscpnamt : 'goodscpnamt_desc',
      },
      toggleData : {
        price : false,
        billing : false,
        rtnamt : false,
        margin : false,
        marginper : false,
      },
      totalCnt : 0,
      frstordcnt : 0,
      clmcnt : 0,
      recnt : 0,
      billing : 0,
      price : 0,
      rtnamt : 0,
      orgamt : 0,
      margin : 0,
      marginper : 0,
      goodscpnamt : 0,
      listData: [],
    }
  },
  mounted() {
    let params = { url: this.$options.name, isloading: false };
    this.$http.post('/admin/common/pageAuth/check', params)
        .then(result => {
          this.isRead = (result.data.isread === 'T');
          this.isWrite = (result.data.iswrite === 'T');
          if(this.isRead) {
            // 초기화
            this.initSearchData();
          }
        }).catch(error => {
      this.$util.debug(error);
    });
  },
  methods : {
    initSearchData() {
      this.searchData = this.$options.data().searchData;
      this.searchData.startdate = this.$util.getAddMonth(this.$util.getDate(''), -3, '-');
      this.searchData.enddate = this.$util.getDate('-');

      this.getCommonCodeList();
      this.getCategoryCodeList(1, 0);
      this.checkAllAppchtype();
      this.checkAllMembertype();
      this.checkAllMemlvtype();
    },
    searchList() {
      let params = Object.assign(this.searchData, this.pagingData);
      this.$http.post('/admin/stats/product/list', params).then(result =>{

        this.totalCnt = result.data.total;
        this.pagingData.listCount = result.data.total;
        this.listData = result.data.list;

        this.frstordcnt = result.data.frstordcnt;
        this.clmcnt = result.data.clmcnt;
        this.recnt = result.data.recnt;
        this.billing = result.data.billing;
        this.price = result.data.price;
        this.rtnamt = result.data.rtnamt;
        this.orgamt = result.data.orgamt;
        this.margin = result.data.margin;
        this.marginper = result.data.marginper;
        this.goodscpnamt = result.data.goodscpnamt;

        this.$util.dataSetSearchParam(this);

      }).catch(error =>{
        this.$util.debug(error);
      })

    },
    // 공통코드 목록 조회
    getCommonCodeList() {
      let cmclassArr = ['MUAPPCHTYPE', 'DADAMEMBERTYPE', 'MEMLVTYPE', 'GOODSSELLTYPE'];
      this.$http.post('/common/code/map/list', {cmclass : cmclassArr, isloading: false})
          .then(result =>{
            let data = result.data;
            for (const [key] of Object.entries(data)) {
              this.commonCode[key] = data[key];
            }

            this.checkAllAppchtype();
            this.getCategoryCodeList(1, 0);
            this.checkAllMembertype();
            this.checkAllMemlvtype();
            this.checkAllSelltype();
          })
          .catch(error => {
            this.$util.debug(error);
          });
    },
    getCategoryCodeList(targetDepth, parent) {
      let params = { idx: parent, isloading: false  };
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

    // 날짜 picker 콜백 함수
    onChangeStartDate(val) {
      this.searchData.startdate = val;
    },
    // 날짜 picker 콜백 함수
    onChangeEndDate(val) {
      this.searchData.enddate = val;
    },
    // 조회조건 - 적용채널 전체체크
    checkAllAppchtype() {
      let isAllCheck = this.searchData.isallchannel;
      this.searchData.muappchtypeArr = [];
      if (isAllCheck === 'T') {
        for(let type of this.commonCode.muappchtype){
          this.searchData.muappchtypeArr.push(type.cmcode);
        }
      }
    },
    checkAllSelltype() {
      let isAllCheck = this.searchData.isallsell;
      this.searchData.muselltypeArr = [];
      if (isAllCheck === 'T') {
        for(let type of this.commonCode.goodsselltype){
          this.searchData.muselltypeArr.push(type.cmcode);
        }
      }
    },
    // 조회조건 - 대상회원유형 전체체크
    checkAllMembertype() {
      let isAllCheck = this.searchData.isallmember;
      this.searchData.mumembertypeArr = [];
      if (isAllCheck === 'T') {
        for(let type of this.commonCode.dadamembertype){

          this.searchData.mumembertypeArr.push(type.cmcode);
        }
      }
    },
    // 조회조건 - 대상회원등급 전체체크
    checkAllMemlvtype() {
      let isAllCheck = this.searchData.isallmemlv;
      this.searchData.mumemlvtypeArr = [];
      if (isAllCheck === 'T') {
        for(let type of this.commonCode.memlvtype){
          this.searchData.mumemlvtypeArr.push(type.cmcode);
        }
      }
    },
    checkTotAll(){
      let isAllCheck = this.searchData.isTotCheck;
      this.searchData.isallmemlv = isAllCheck;
      this.searchData.isallmember = isAllCheck;
      this.searchData.isallchannel = isAllCheck;

      this.checkAllAppchtype();
      this.checkAllMembertype();
      this.checkAllMemlvtype();
    },
    // 페이징데이터 세팅
    setPagingData(param){
      this.pagingData = param;
      this.searchList();
    },
    // 정렬
    sortToggle(key){
      let arr = key.split('_');
      let sortKey = arr[0];
      let sortOrder = (arr[1] === 'asc') ? 'desc' : 'asc';
      let sortName = sortKey + '_' + sortOrder;

      this.sortData[sortKey] = sortName;
      this.searchData.psort = sortName;

      this.searchList();
    },
    goExcelDownload(){
      if(this.listData.length <= 0){
        alert('데이터가 존재하지 않습니다.');
        return;
      }
      let param = this.searchData;
      let config = { responseType: 'arraybuffer' };
      this.$http.post('/admin/stats/product/list/excel', param, config);
    },
    toggleTitle(value){
      this.toggleData[value] = !this.toggleData[value];
    }
  },
  watch : {
    'searchData.period': function (val) {
      let dayType = ["-1", "0", "7"];
      let monthType = ["1", "3", "6"];
      let valueToInt = parseInt(val);

      if (dayType.includes(val)) {
        if (valueToInt >= 0) {
          this.searchData.startdate = this.$util.getAddDate(this.$util.getDate(''), (valueToInt * -1), '-');
          this.searchData.enddate = this.$util.getDate('-');
        } else {
          this.searchData.startdate = this.$util.getAddDate(this.$util.getDate(''), valueToInt, '-');
          this.searchData.enddate = this.$util.getAddDate(this.$util.getDate(''), valueToInt, '-');
        }
      } else if (monthType.includes(val)) {
        this.searchData.startdate = this.$util.getAddMonth(this.$util.getDate(''), (valueToInt * -1), '-');
        this.searchData.enddate = this.$util.getDate('-');
      }
    },
    // 다중적용채널
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
  }
}
</script>

<style scoped>

</style>