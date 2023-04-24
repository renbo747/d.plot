<template>
  <div class="tab-area" style="height: calc(90vh - 246px);">
    <div class="clearfix">
      <div class="bar-title small fl">쿠폰 발급/이용내역</div>
    </div>
    <div class="boxing search-area">
      <dl>
        <dt>조회기간</dt>
        <dd>
          <select v-model="searchData.sdate">
            <option value="pay">발급일자</option>
            <option value="use">사용일시</option>
          </select>
          <CommonDatePicker :value="searchData.startDate" @change="onChangeStartDate"/>
          <span>-</span>
          <CommonDatePicker :value="searchData.endDate" @change="onChangeEndDate"/>
        </dd>
      </dl>
    </div>
    <div class="btn-group">
      <button type="button" class="btn big blue" @click="onSearch">검색</button>
    </div>
    <div class="caption-group mt10 clearfix">
      <div class="total-group fl">
        <span class="total">전체 <strong>{{ total }}</strong>건</span>
        <span>미이용 <strong>{{ pay }}</strong>건</span>
        <span>이용 <strong>{{ used }}</strong>건</span>
      </div>
      <div class="btn-group fr">
        <select v-model="pagingData.pageCount">
          <option value="20">20개씩 보기</option>
          <option value="50">50개씩 보기</option>
          <option value="100">100개씩 보기</option>
        </select>
      </div>
    </div>
    <table cellpadding="0" cellspacing="0" class="data-tb align-c">
      <caption>쿠폰 목록</caption>
      <colgroup>
        <col width="3%" /><!-- No -->
        <col width="8%" /><!-- 쿠폰번호 -->
        <col width="" /><!-- 쿠폰명 -->
        <col width="6%" /><!-- 쿠폰종류 -->
        <col width="6%" /><!-- 발급종류 -->
        <col width="12%" /><!-- 사용기간 -->
        <col width="8%" /><!-- 사용채널 -->
        <col width="8%" /><!-- 할인 -->
        <col width="6%" /><!-- 발급방법 -->
        <col width="10%" /><!-- 발급일시 -->
        <col width="8%" /><!-- 주문번호 -->
        <col width="10%" /><!-- 사용일시 -->
      </colgroup>
      <thead>
      <tr>
        <th>No</th>
        <th>쿠폰번호</th>
        <th>쿠폰명</th>
        <th>쿠폰종류</th>
        <th>발급종류</th>
        <th>사용기간</th>
        <th>사용채널</th>
        <th>할인</th>
        <th>발급방법</th>
        <th>발급일시
          <button type="button"
                  :value="sortData.issuedate"
                  class="sort"
                  :class="[{up : sortData.issuedate === 'issuedate_asc'}, {down : sortData.issuedate === 'issuedate_desc'}]"
                  @click="sortToggle(sortData.issuedate)">
          </button>
        </th>
        <th>주문번호</th>
        <th>사용일시
          <button type="button"
                  :value="sortData.usedate"
                  class="sort"
                  :class="[{up : sortData.usedate === 'usedate_asc'}, {down : sortData.usedate === 'usedate_desc'}]"
                  @click="sortToggle(sortData.usedate)">
          </button>
        </th>
      </tr>
      </thead>
      <tbody v-if="listData.length > 0">
      <tr v-for="(row, i) in listData" :key="i">
        <td>{{ loopNumberForPaging(i) }}</td>
        <td>{{ row.comcpnno }}</td>
        <td class="left"><a class="link" @click="goDetail(row.comcpnidx, row.cpninfoidx)">{{ row.cpnname }}</a></td>
        <td>{{ row.comcpntypename }}</td>
        <td>{{ row.cpnissuetypename }}</td>
        <td>{{ row.cpnusestday }}<br>~ {{ row.cpnuseedday }}</td>
        <td>{{ row.muappchtypename }}</td>
        <td>{{ row.discountconts }}</td>
        <td>{{ row.isautopayname }}</td>
        <td>{{ row.issuedate }}</td>
        <td><a class="link" @click="goOrderDetail(row.ordno)">{{ row.ordno }}</a></td>
        <td>{{ row.usedate }}</td>
      </tr>
      </tbody>
      <tbody v-else>
      <tr>
        <td colspan="12">조회 결과가 존재하지 않습니다.</td>
      </tr>
      </tbody>
    </table>
    <div class="bottom-group">
      <CommonPageNavigator :pagingData="pagingData" v-on:setPagingData="setPagingData"/>
      <div class="btn-group">
        <button type="button" class="btn blue" @click="goRegist">쿠폰 등록</button>
      </div>
    </div>
    <CouponRegist v-if="isShowRegist" :activeComcpnidx="activeComcpnidx" :activeCpninfoidx="activeCpninfoidx" @closePopup="closeRegist"></CouponRegist>
    <CouponDetail v-if="isShowDetail" :activeComcpnidx="activeComcpnidx" :activeCpninfoidx="activeCpninfoidx" @closePopup="closeDetail"></CouponDetail>
    <OrderDetail v-if="isOrderDetailShow" :activeOrderCode="activeOrderCode" @closeDetail="closeOrderDetailPopup"></OrderDetail>
  </div>
</template>

<script>
import CommonDatePicker from "@views.admin/common/CommonDatePicker.vue";
import CommonPageNavigator from "@views.admin/common/CommonPageNavigator.vue";
import CouponRegist from '@views.admin/promotion/coupon/CouponRegist.vue';
import CouponDetail from '@views.admin/promotion/coupon/CouponDetail.vue';
import OrderDetail from "@views.admin/order/info/OrderDetail.vue";


export default {
  name: "AdminMemberCoupon",
  props: ['activeUserNo'],
  components: {CommonDatePicker, CommonPageNavigator, CouponDetail, OrderDetail, CouponRegist},
  data() {
    return {
      listData : [],
      isOrderDetailShow: false,
      isShowDetail: false,
      isShowRegist: false,        // 쿠폰등록 팝업 오픈여부
      activeComcpnidx: null,        // 오픈한 쿠폰상세 쿠폰idx
      activeCpninfoidx: null,       // 오픈한 쿠폰상세 쿠폰정보idx
      pay: 0,
      used: 0,
      total: 0,
      searchData: {
        userno: this.activeUserNo,
        startDate: '',          // 조회시작날짜
        endDate: '',            // 조회종료날짜
        sdate: 'pay',
        psort: 'issuedate_desc'
      },
      sortData: {
        issuedate : 'issuedate_desc',
        usedate : 'usedate_desc'
      },
      pagingData: {
        pageCount: 20,      // 페이징 옵션(최대수)
        page: 1,            // 현재 페이지
        listCount: 0        // 총 수량
      },
    }
  },
  mounted() {
    this.searchData.startDate = this.$util.getAddMonth(this.$util.getDate(), -3, '-');
    this.searchData.endDate = this.$util.getDate('-');
    this.onSearch();
  },
  methods : {
    onSearch() {
      let params = Object.assign(this.searchData, this.pagingData);
      this.$http.post('/admin/member/coupon/list', params).then(result => {
        this.listData = result.data.list;
        this.total = result.data.total;
        this.pay = result.data.pay;
        this.used = result.data.used;
        this.pagingData.listCount = result.data.total;
      }).catch(error => {
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
    setPagingData(param){
      this.pagingData = param;
      this.onSearch();
    },
    goOrderDetail: function(ordercode) {
      this.isOrderDetailShow = true;
      this.activeOrderCode = ordercode;
    },
    // 주문상세 팝업 닫기
    closeOrderDetailPopup: function() {
      this.isOrderDetailShow = false;
    },
    // 쿠폰상세 팝업 열기
    goDetail: function(comcpnidx, cpninfoidx) {
      this.activeComcpnidx = comcpnidx;
      this.activeCpninfoidx = cpninfoidx;
      this.isShowDetail = true;
    },
    // 쿠폰상세 팝업 닫기
    closeDetail: function(isreload) {
      this.isShowDetail = false;
      if (isreload) {
        this.onSearch();
      }
    },
    onChangeStartDate: function (value) {
      this.searchData.startDate = value;
    },
    // 종료날짜 picker 콜백 함수
    onChangeEndDate: function (value) {
      this.searchData.endDate = value;
    },
    sortToggle(key){
      let arr = key.split('_');
      let sortKey = arr[0];
      let sortOrder = (arr[1] === 'asc') ? 'desc' : 'asc';
      let sortName = sortKey + '_' + sortOrder;

      this.sortData[sortKey] = sortName;
      this.searchData.psort = sortName;

      this.onSearch();
    },
    // 쿠폰등록 팝업 열기
    goRegist: function() {
      this.activeComcpnidx = '';
      this.activeCpninfoidx = '';
      this.isShowRegist = true;
    },
    // 쿠폰등록 팝업 닫기
    closeRegist: function(isreload) {
      this.isShowRegist = false;
      if (isreload) {
        this.searchList();
      }
    }
  }
}
</script>

<style scoped />