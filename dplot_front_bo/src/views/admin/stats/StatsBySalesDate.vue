<template>
  <!-- 컨텐츠 영역 -->
  <div class="content m-leftmenu">
    <CommonNavigator></CommonNavigator>
    <div class="inner">
      <div class="boxing search-area pd0">
        <dl>
          <dt>조회기간</dt>
          <dd>
            <div class="radio_wrap wide3">
              <input type="radio" v-model="searchData.type" id="group01" value="day"><label for="group01">일별</label>
              <input type="radio" v-model="searchData.type" id="group02" value="week"><label for="group02">주별</label>
              <input type="radio" v-model="searchData.type" id="group03" value="month"><label for="group03">월별</label>
            </div>
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
        </dl>
        <dl>
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
          <button type="button" class="btn green-line" v-show="isRead" @click="goExcelDownload"><i class="icon-excel"></i>엑셀다운로드</button>
          <select v-model="pagingData.pageCount" v-show="isRead">
            <option value="20">20개씩 보기</option>
            <option value="50">50개씩 보기</option>
            <option value="100">100개씩 보기</option>
          </select>
        </div>
      </div>
      <table cellpadding="0" cellspacing="0" class="data-tb align-r">
        <caption>목록</caption>
        <colgroup>
          <col width="3%" /><!-- No -->
          <col width="" /><!-- 일자 -->
          <col width="7%" /><!-- 구매건수 -->
          <col width="7%" /><!-- 구매수량 -->
          <col width="7%" /><!-- 최초판매가 -->
          <col width="6%" /><!-- 프로모션할인 -->
          <col width="6%" /><!-- 쿠폰할인 -->
          <col width="5%" /><!-- 적립금 -->
          <col width="5%" /><!-- D포인트 -->
          <col width="6%" /><!-- 임직원포인트 -->
          <col width="7%" /><!-- 배송비 -->
          <col width="7%" /><!-- 순주문금액 -->
          <col width="7%" /><!-- 실 결제금액 -->
          <col width="7%" /><!-- 영업이익 -->
          <col width="7%" /><!-- 매출 ① -->
          <col width="7%" /><!-- 환불합계 ② -->
        </colgroup>
        <thead>
        <tr>
          <th>No</th>
          <th>일자
            <button type="button" :value="sortData.target" class="sort"
                    :class="[{up : sortData.target === 'target_asc'}, {down : sortData.target === 'target_desc'}]"
                    @click="sortToggle(sortData.target)"></button>
          </th>
          <th>순 주문건수
            <button type="button" :value="sortData.ordercnt" class="sort"
                    :class="[{up : sortData.ordercnt === 'ordercnt_asc'}, {down : sortData.ordercnt === 'ordercnt_desc'}]"
                    @click="sortToggle(sortData.ordercnt)"></button>
          </th>
          <th>순 주문수량
            <button type="button" :value="sortData.salecnt" class="sort"
                    :class="[{up : sortData.salecnt === 'salecnt_asc'}, {down : sortData.salecnt === 'salecnt_desc'}]"
                    @click="sortToggle(sortData.salecnt)"></button>
          </th>
          <th>최초판매가
            <button type="button" :value="sortData.stamt" class="sort"
                    :class="[{up : sortData.stamt === 'stamt_asc'}, {down : sortData.stamt === 'stamt_desc'}]"
                    @click="sortToggle(sortData.stamt)"></button>
          </th>
          <th>프로모션할인
            <button type="button" :value="sortData.salepromoamt" class="sort"
                    :class="[{up : sortData.salepromoamt === 'salepromoamt_asc'}, {down : sortData.salepromoamt === 'salepromoamt_desc'}]"
                    @click="sortToggle(sortData.salepromoamt)"></button>
          </th>
          <th>쿠폰할인
            <button type="button" :value="sortData.goodscpnamt" class="sort"
                    :class="[{up : sortData.goodscpnamt === 'goodscpnamt_asc'}, {down : sortData.goodscpnamt === 'goodscpnamt_desc'}]"
                    @click="sortToggle(sortData.goodscpnamt)"></button>
          </th>
          <th>적립금
            <button type="button" :value="sortData.reserveamt" class="sort"
                    :class="[{up : sortData.reserveamt === 'reserveamt_asc'}, {down : sortData.reserveamt === 'reserveamt_desc'}]"
                    @click="sortToggle(sortData.reserveamt)"></button>
          </th>
          <th>D포인트
            <button type="button" :value="sortData.epointamt" class="sort"
                    :class="[{up : sortData.epointamt === 'epointamt_asc'}, {down : sortData.epointamt === 'epointamt_desc'}]"
                    @click="sortToggle(sortData.epointamt)"></button>
          </th>
          <th>임직원포인트
            <button type="button" :value="sortData.empreserveamt" class="sort"
                    :class="[{up : sortData.empreserveamt === 'empreserveamt_asc'}, {down : sortData.empreserveamt === 'empreserveamt_desc'}]"
                    @click="sortToggle(sortData.empreserveamt)"></button>
          </th>
          <th>배송비
            <button type="button" :value="sortData.delivamt" class="sort"
                    :class="[{up : sortData.delivamt === 'delivamt_asc'}, {down : sortData.delivamt === 'delivamt_desc'}]"
                    @click="sortToggle(sortData.delivamt)"></button>
          </th>
          <th>
            <i class="icon-th-q" @click="toggleData.pure = !toggleData.pure"></i>순 주문금액
            <button type="button" :value="sortData.pure" class="sort"
                    :class="[{up : sortData.pure === 'pure_asc'}, {down : sortData.pure === 'pure_desc'}]"
                    @click="sortToggle(sortData.pure)"></button>
            <div class="th-cell" :class="[{dpb : toggleData.pure}, {dpn : !toggleData.pure}]" style="width: 240px;"><!-- dpb:보임 / dpn:숨김 -->
              판매가(프로모션할인가) – 쿠폰할인
            </div>
          </th>
          <th>
            <i class="icon-th-q" @click="toggleData.realamt = !toggleData.realamt"></i>실 결제금액
            <button type="button" :value="sortData.realamt" class="sort"
                    :class="[{up : sortData.realamt === 'realamt_asc'}, {down : sortData.realamt === 'realamt_desc'}]"
                    @click="sortToggle(sortData.realamt)"></button>
            <div class="th-cell" :class="[{dpb : toggleData.realamt}, {dpn : !toggleData.realamt}]" style="width: 240px;"><!-- dpb:보임 / dpn:숨김 -->
              최초판매가 – 프로모션할인 – 쿠폰 – 적립금 – D포인트 – 임직원포인트 + 배송비
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
            <i class="icon-th-q" @click="toggleData.price = !toggleData.price"></i>매출
            <button type="button" :value="sortData.price" class="sort"
                    :class="[{up : sortData.price === 'price_asc'}, {down : sortData.price === 'price_desc'}]"
                    @click="sortToggle(sortData.price)"></button>
            <div class="th-cell" :class="[{dpb : toggleData.price}, {dpn : !toggleData.price}]" style="width: 345px;"><!-- dpb:보임 / dpn:숨김 -->
              매출(일반기준) = 판매가(프로모션할인가)*수량<br>
              매출(회계기준) = 판매가(프로모션할인가)*수량 – 쿠폰할인 + 배송비
            </div>
          </th>
          <th>
            <i class="icon-th-q" @click="toggleData.rtnamt = !toggleData.rtnamt"></i>환불합계
            <button type="button" :value="sortData.rtnamt" class="sort"
                    :class="[{up : sortData.rtnamt === 'rtnamt_asc'}, {down : sortData.rtnamt === 'rtnamt_desc'}]"
                    @click="sortToggle(sortData.rtnamt)"></button>
            <div class="th-cell" :class="[{dpb : toggleData.rtnamt}, {dpn : !toggleData.rtnamt}]" style="width: 325px;"><!-- dpb:보임 / dpn:숨김 -->
              고객반환 실 환불금액(적립금/D포인트/임직원포인트 합산 금액)
            </div>
          </th>
        </tr>
        </thead>
        <tbody v-if="listData.length > 0">
        <tr class="bg blue">
          <td colspan="2">합계</td>
          <td>{{ ordercnt }}</td>
          <td>{{ salecnt }}</td>
          <td>{{ stamt }}</td>
          <td>{{ salepromoamt }}</td>
          <td>{{ goodscpnamt }}</td>
          <td>{{ reserveamt }}</td>
          <td>{{ epointamt }}</td>
          <td>{{ empreserveamt }}</td>
          <td>{{ delivamt }}</td>
          <td>{{ pure }}</td>
          <td>{{ realamt }}</td>
          <td>{{ margin }}</td>
          <td>{{ price }}</td>
          <td>{{ rtnamt }}</td>
        </tr>
        <tr v-for="(row, index) in listData" v-bind:key="index">
          <td class="center">{{ loopNumberForPaging(index) }}</td>
          <td class="center">{{ row.target }}</td>
          <td>{{ row.ordercnt }}</td>
          <td>{{ row.salecnt }}</td>
          <td>{{ row.stamt }}</td>
          <td>{{ row.salepromoamt }}</td>
          <td>{{ row.goodscpnamt }}</td>
          <td>{{ row.reserveamt }}</td>
          <td>{{ row.epointamt }}</td>
          <td>{{ row.empreserveamt }}</td>
          <td>{{ row.delivamt }}</td>
          <td>{{ row.pure }}</td>
          <td>{{ row.realamt }}</td>
          <td>{{ row.margin }}</td>
          <td>{{ row.price }}</td>
          <td>{{ row.rtnamt }}</td>
        </tr>
        </tbody>
        <tbody v-else>
        <tr>
          <td colspan="17" class="center">조회 결과가 존재하지 않습니다.</td>
        </tr>
        </tbody>
      </table>
      <div class="bottom-group">
        <CommonPageNavigator v-show="isRead" :pagingData="pagingData" v-on:setPagingData="setPagingData"/>
      </div>
    </div>
  </div>
  <!-- /컨텐츠 영역 -->
</template>

<script>
import CommonNavigator from "@views.admin/common/CommonNavigator";
import CommonPageNavigator from "@views.admin/common/CommonPageNavigator";
import CommonDatePicker from "@views.admin/common/CommonDatePicker";

export default {
  name: "admin.stats.saledate",
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
        startdate: '',          // 조회시작날짜
        enddate: '',            // 조회종료날짜
        period: '3',             // 조회기간
        type : 'day',
        standard : 'PAY',
        amtstandard : 'NORMAL',
        isnonmember: '',        // 비회원주문여부
        isallchannel: 'T',      // 다중적용채널전체여부
        muappchtypeArr: [],     // 다중적용채널Array
        isallmember: 'T',       // 다중대상회원유형전체여부
        mumembertypeArr: [],    // 다중대상회원유형Array
        isallmemlv: 'T',        // 다중대상회원등급전체여부
        mumemlvtypeArr: [],     // 다중대상회원등급Array
        isallpayway: 'T',        // 결제수단전체여부
        paywaytypeArr: [],      // 결제수단Array
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
        paywaytype: []          // 결제수단
      },
      sortData: {
        target : 'target_asc',
        ordercnt : 'ordercnt_desc',
        salecnt : 'salecnt_desc',
        stamt : 'stamt_desc',
        salepromoamt : 'salepromoamt_desc',
        goodscpnamt : 'goodscpnamt_desc',
        reserveamt : 'reserveamt_desc',
        epointamt : 'epointamt_desc',
        empreserveamt : 'empreserveamt_desc',
        delivamt : 'delivamt_desc',
        pure : 'pure_desc',
        realamt : 'realamt_desc',
        margin : 'margin_desc',
        price : 'price_desc',
        rtnamt : 'rtnamt_desc',
        orgamt : 'orgamt_desc',
      },
      toggleData : {
        pure : false,
        realamt : false,
        margin : false,
        price : false,
        rtnamt : false,
      },
      totalCnt : 0,
      ordercnt : 0,
      salecnt : 0,
      price : 0,
      pure : 0,
      salepromoamt : 0,
      goodscpnamt : 0,
      margin : 0,
      rtnamt : 0,
      stamt : 0,
      delivamt : 0,
      reserveamt : 0,
      epointamt : 0,
      empreserveamt : 0,
      realamt : 0,
      orgamt : 0,
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
      this.checkAllAppchtype();
      this.checkAllMembertype();
      this.checkAllMemlvtype();
      this.checkAllPaywaytype();
    },
    searchList() {
      let params = Object.assign(this.searchData, this.pagingData);
      this.$http.post('/admin/stats/sale/date/list', params).then(result =>{

        this.totalCnt = result.data.total;
        this.pagingData.listCount = result.data.total;
        this.listData = result.data.list;

        this.ordercnt = result.data.ordercnt;
        this.salecnt = result.data.salecnt;
        this.price = result.data.price;
        this.pure = result.data.pure;
        this.salepromoamt = result.data.salepromoamt;
        this.goodscpnamt = result.data.goodscpnamt;
        this.margin = result.data.margin;
        this.rtnamt = result.data.rtnamt;
        this.stamt = result.data.stamt;
        this.delivamt = result.data.delivamt;
        this.reserveamt = result.data.reserveamt;
        this.epointamt = result.data.epointamt;
        this.empreserveamt = result.data.empreserveamt;
        this.realamt = result.data.realamt;
        this.orgamt = result.data.orgamt;

        this.$util.dataSetSearchParam(this);

      }).catch(error =>{
        this.$util.debug(error);
      })

    },
    // 공통코드 목록 조회
    getCommonCodeList() {
      let cmclassArr = ['MUAPPCHTYPE', 'DADAMEMBERTYPE', 'MEMLVTYPE', 'PAYWAYTYPE'];
      this.$http.post('/common/code/map/list', {cmclass : cmclassArr, isloading: false})
          .then(result =>{
            let data = result.data;
            for (const [key] of Object.entries(data)) {
              this.commonCode[key] = data[key];
            }

            this.checkAllAppchtype();
            this.checkAllMembertype();
            this.checkAllMemlvtype();
            this.checkAllPaywaytype();
          })
          .catch(error => {
            this.$util.debug(error);
          });
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
    // 조회조건 - 결제수단 전체체크
    checkAllPaywaytype: function() {
      let isAllCheck = this.searchData.isallpayway;
      this.searchData.paywaytypeArr = [];
      if (isAllCheck === 'T') {
        for(let type of this.commonCode.paywaytype){
          this.searchData.paywaytypeArr.push(type.cmcode);
        }
      }
    },
    checkTotAll(){
      let isAllCheck = this.searchData.isTotCheck;
      this.searchData.isallmemlv = isAllCheck;
      this.searchData.isallmember = isAllCheck;
      this.searchData.isallchannel = isAllCheck;
      this.searchData.isallpayway = isAllCheck;

      this.checkAllAppchtype();
      this.checkAllMembertype();
      this.checkAllMemlvtype();
      this.checkAllPaywaytype();
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
      this.$http.post('/admin/stats/sale/date/list/excel', param, config);
    },
    loopNumberForPaging(index) {
      if(this.pagingData.page > 1){
        let page = this.pagingData.page - 1;
        return (index+1) + (page * this.pagingData.pageCount);
      } else {
        return (index+1);
      }
    },
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
    // 결제수단
    'searchData.paywaytypeArr': function(value) {
      if (value.length < this.commonCode.paywaytype.length) {
        this.searchData.isallpayway = 'F';
      } else {
        this.searchData.isallpayway = 'T';
      }
    },
  }
}
</script>

<style scoped>

</style>