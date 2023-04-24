<template>
  <div class="tab-area" style="height: calc(90vh - 246px);">
    <div class="clearfix">
      <div class="bar-title small fl">주문내역</div>
    </div>
    <div class="boxing search-area">
      <dl>
        <dt>조회기간</dt>
        <dd>
          <select v-model="searchData.dtkey">
            <option value="order">주문일</option>
            <option value="payment">결제일</option>
          </select>
          <CommonDatePicker :value="searchData.startdate" @change="onChangeStartDate"/>
          <span>-</span>
          <CommonDatePicker :value="searchData.enddate" @change="onChangeEndDate"/>
        </dd>
      </dl>
    </div>
    <div class="btn-group">
      <button type="button" class="btn big blue" @click="searchList">검색</button>
    </div>
    <div class="caption-group mt10 clearfix">
      <div class="total-group fl">
        <span class="total">전체 <strong>{{ totalCnt }}</strong>건</span>
      </div>
      <div class="btn-group fr">
        <select v-model="pagingData.pageCount">
          <option value="20">20개씩 보기</option>
          <option value="50">50개씩 보기</option>
          <option value="100">100개씩 보기</option>
        </select>
      </div>
    </div>
    <div class="scroll-x">
      <table cellpadding="0" cellspacing="0" class="data-tb align-c" style="width: 250%;">
        <caption>주문 목록</caption>
        <colgroup>
          <col width="2.7%" /><!-- 주문경로 -->
          <col width="3.2%" /><!-- 주문일 -->
          <col width="3.2%" /><!-- 결제일 -->
          <col width="4.2%" /><!-- 주문번호 -->
          <col width="2.5%" /><!-- 첫 구매 -->
          <col width="2.7%" /><!-- 판매구분 -->
          <col width="3.2%" /><!-- 파트너사 -->
          <col width="3%" /><!-- 상품코드 -->
          <col width="3%" /><!-- 단품코드 -->
          <col width="2.7%" /><!-- 상품순번 -->
          <col width="62px" /><!-- 상품이미지 -->
          <col width="" /><!-- 상품명 -->
          <col width="4%" /><!-- 옵션 -->
          <col width="2.7%" /><!-- 주문수량 -->
          <col width="3%" /><!-- 판매단가 -->
          <col width="3.5%" /><!-- 판매금액 -->
          <col width="3.5%" /><!-- 결제수단 -->
          <col width="2.7%" /><!-- 배송유형 -->
          <col width="2.7%" /><!-- 배송조건 -->
          <col width="2.5%" /><!-- 배송비 -->
          <col width="2.7%" /><!-- 수령인명 -->
          <col width="4%" /><!-- 연락처 -->
          <col width="10%" /><!-- 주소 -->
          <col width="2.7%" /><!-- 택배사 -->
          <col width="3.2%" /><!-- 송장번호 -->
          <col width="3.5%" /><!-- 구매확정 -->
          <col width="2.7%" /><!-- 주문상태 -->
          <col width="2.7%" /><!-- 클레임여부 -->
        </colgroup>
        <thead>
        <tr>
          <th>주문경로
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
          <th>첫 구매
            <button type="button" class="sort" :value="sortData.isfrstorder"
                    :class="[{up : sortData.isfrstorder === 'isfrstorder_asc'}, {down : sortData.isfrstorder === 'isfrstorder_desc'}]"
                    @click="sortToggle(sortData.isfrstorder)"></button>
          </th>
          <th>판매구분
            <button type="button" class="sort" :value="sortData.ispbgoods"
                    :class="[{up : sortData.ispbgoods === 'ispbgoods_asc'}, {down : sortData.ispbgoods === 'ispbgoods_desc'}]"
                    @click="sortToggle(sortData.ispbgoods)"></button>
          </th>
          <th>파트너사
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
          <th>구매확정
            <button type="button" class="sort" :value="sortData.cfmconts"
                    :class="[{up : sortData.cfmconts === 'cfmconts_asc'}, {down : sortData.cfmconts === 'cfmconts_desc'}]"
                    @click="sortToggle(sortData.cfmconts)"></button>
          </th>
          <th>주문상태
            <button type="button" class="sort" :value="sortData.ordstatus"
                    :class="[{up : sortData.ordstatus === 'ordstatus_asc'}, {down : sortData.ordstatus === 'ordstatus_desc'}]"
                    @click="sortToggle(sortData.ordstatus)"></button>
          </th>
          <th>클레임여부</th>
        </tr>
        </thead>
        <tbody v-if="list.length > 0">
        <tr v-for="item in list" :key="item.orgdelividx">
          <td>{{ item.ordpathtypename }}</td>
          <td>{{ item.orderdate }}<br>{{ item.ordertime }}</td>
          <td>{{ item.paymentdate }}<br>{{ item.paymenttime }}</td>
          <td><a href="javascript:void(0);" class="link" @click="goOrderDetail(item.ordno)">{{ item.ordno }}</a></td>
          <td>{{ item.isfrstorder }}</td>
          <td>{{ item.ispbgoodsname }}</td>
          <td>{{ item.dealername }}</td>
          <td>{{ item.goodscode}}</td>
          <td>{{ item.optioncode }}</td>
          <td>{{ item.goodsturn }}</td>
          <td>
            <div class="img-thumb size60 link" :class="{'no-image': $util.isNull(item.fullpath)}">
              <img :src="item.fullpath" v-if="!$util.isNull(item.fullpath)">
            </div>
          </td>
          <td class="left no-left">
            <a href="javascript:void(0);" class="link" @click="goGoodsDetail(item.goodsno)">{{ item.goodsname }}</a>
          </td>
          <td style="white-space: pre-wrap">{{ item.optionconts }}</td>
          <td>{{ $util.maskComma(item.ordcnt) }}</td>
          <td class="right">{{ $util.maskComma(item.price) }}</td>
          <td class="right">{{ $util.maskComma(item.totprice) }}</td>
          <td>{{ item.paywaytypename }}</td>
          <td>{{ item.iscombdelivname }}</td>
          <td>{{ item.delivfaretypename }}</td>
          <td class="right">{{ $util.maskComma(item.delivamt) }}</td>
          <td>{{ item.rcvname }}</td>
          <td>{{ $util.maskTel(item.rcvtel) }}</td>
          <td>{{ item.rcvaddr }}</td>
          <td>{{ item.logistypename }}</td>
          <td>{{ item.invoiceno }}</td>
          <td>{{ item.cfmconts }}</td>
          <td>{{ item.ordstatusname }}</td>
          <td>{{ item.claimyn }}</td>
        </tr>
        </tbody>
        <tbody v-else>
        <tr><td colspan="26">조회 결과가 존재하지 않습니다.</td></tr>
        </tbody>
      </table>
    </div>
    <div class="bottom-group">
      <CommonPageNavigator :pagingData="pagingData" v-on:setPagingData="setPagingData" />
    </div>
    <OrderDetail v-if="isShowOrderDetail" :activeOrderCode="activeOrderCode" @closeDetail="closeOrderDetail"></OrderDetail>
    <GoodsDetail v-if="isGoodsDetailShow" :activeGoodsNo="activeGoodsNo" @closePopup="closeGoodsDetail"></GoodsDetail>
  </div>
</template>

<script>

import CommonDatePicker from "@views.admin/common/CommonDatePicker";
import CommonPageNavigator from "@views.admin/common/CommonPageNavigator";
import OrderDetail from "@views.admin/order/info/OrderDetail";
import GoodsDetail from "@views.admin/goods/manage/GoodsDetail.vue";

export default {
  name: "AdminMemberOrder",
  props: ['activeUserNo'],
  components: {
    CommonPageNavigator,
    CommonDatePicker,
    OrderDetail,
    GoodsDetail
  },
  data() {
    return {
      searchData: {
        skey: '',               // 직접검색KEY
        sword: '',              // 직접검색어
        dtkey: 'order',
        period: '',             // 조회일자기간
        startdate: '',          // 조회시작일자
        enddate: '',            // 조회종료일자
        psort: 'orderday_desc', // 정렬조건 (default: 최근주문일)
        userno : this.activeUserNo
      },
      sortData: {
        ordpathtype: 'ordpathtype_asc', // 주문경로
        orderdate: 'orderdate_desc',    // 주문일
        paymentdate: 'paymentdate_asc', // 결제일
        ordno: 'ordno_asc',             // 주문번호
        isfrstorder: 'isfrstorder_asc', // 첫구매여부
        ispbgoods: 'ispbgoods_asc',     // 판매구분
        dealername: 'dealername_asc',   // 판매사명
        goodscode: 'goodscode_asc',     // 상품코드
        optioncode: 'optioncode_asc',   // 단품코드
        goodsturn: 'goodsturn_asc',     // 상품순번
        goodsname: 'goodsname_asc',     // 상품명
        optionconts: 'optionconts_asc', // 옵션내용
        ordcnt: 'ordcnt_asc',           // 주문수량
        price: 'price_asc',         // 판매단가
        totprice: 'totprice_asc',   // 판매가
        paywaytype: 'paywaytype_asc',   // 결제수단
        iscombdeliv: 'iscombdeliv_asc', // 배송유형
        delivfaretype: 'delivfaretype_asc', // 배송조건
        delivamt: 'delivamt_asc',       // 배송비
        rcvname: 'rcvname_asc',         // 수령인명
        rcvtel: 'rcvtel_asc',           // 수령인연락처
        rcvaddr: 'rcvaddr_asc',         // 수령인주소
        logistypename: 'logistypename_asc', // 택배사
        invoiceno: 'invoiceno_asc',     // 송장번호
        cfmconts: 'cfmconts_asc',       // 구매확정
        ordstatus: 'ordstatus_asc',     // 주문상태
      },
      pagingData: {
        pageCount: 20,      // 페이징 옵션(최대수)
        page: 1,            // 현재 페이지
        listCount: 0        // 총 수량
      },
      list: [],
      totalCnt : 0,
      activeGoodsNo : '',
      activeOrderCode: '',
      isGoodsDetailShow: false,   // 상품상세 팝업 노출여부
      isShowOrderDetail: false,   // 주문상세 팝업 노출여부
    }
  },
  mounted() {
    this.searchData.startdate = this.$util.getAddMonth(this.$util.getDate(''), -6, '-');
    this.searchData.enddate = this.$util.getDate('-');
  },
  methods : {
    searchList() {
      let params = Object.assign(this.searchData, this.pagingData);
      this.$http.post('/admin/order/manage/list', params).then(result => {
        let data = result.data;
        this.list = data.list;
        this.totalCnt = data.count.totalcnt;
        this.pagingData.listCount = data.count.totalcnt;
        this.$util.dataSetSearchParam(this);
      }).catch(error => {
        this.$util.debug(error);
      });
    },
    // 날짜 picker 콜백 함수
    onChangeStartDate(value) {
      this.searchData.startdate = value;
    },
    // 날짜 picker 콜백 함수
    onChangeEndDate(value) {
      this.searchData.enddate = value;
    },
    // 주문상세 팝업 오픈
    goOrderDetail: function(value) {
      this.isShowOrderDetail = true;
      this.activeOrderCode = value;
    },
    // 주문상세 팝업 닫기
    closeOrderDetail: function() {
      this.isShowOrderDetail = false;
    },
    goGoodsDetail: function(value) {
      this.activeGoodsNo = value;
      this.isGoodsDetailShow = true;
    },
    closeGoodsDetail(){
      this.isGoodsDetailShow = false;
      this.onSearch();
    },
    // 페이징 콜백
    setPagingData(param){
      this.pagingData = param;
      this.searchList();
    },
    sortToggle(key){
      let arr = key.split('_');
      let sortKey = arr[0];
      let sortOrder = (arr[1] === 'asc') ? 'desc' : 'asc';
      let sortName = sortKey + '_' + sortOrder;

      this.sortData[sortKey] = sortName;
      this.searchData.psort = sortName;

      this.searchList();
    },
  }
}
</script>

<style scoped>

</style>