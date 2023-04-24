<template>
  <div class="tab-area" style="height: calc(90vh - 246px);">
    <div class="clearfix">
      <div class="bar-title small fl">임직원적립금 적립/차감/소멸내역</div>
    </div>
    <div class="mini-dash col3">
      <dl>
        <dt>총 적립</dt>
        <dd><p>{{ pay }}원</p></dd>
      </dl>
      <dl>
        <dt>총 차감</dt>
        <dd><p>{{ used }}원</p></dd>
      </dl>
      <dl>
        <dt>가용</dt>
        <dd><p>{{ pos }}원</p></dd>
      </dl>
    </div>
    <div class="boxing search-area">
      <dl>
        <dt>적립/차감/소멸일자</dt>
        <dd>
          <common-date-picker :value="searchData.startDate" @change="onChangeStartDate"></common-date-picker>
          <span>-</span>
          <common-date-picker :value="searchData.endDate" @change="onChangeEndDate"></common-date-picker>
        </dd>
      </dl>
    </div>
    <div class="btn-group">
      <button type="button" class="btn big blue" @click="onSearch">검색</button>
    </div>
    <div class="caption-group mt10 clearfix">
      <div class="total-group fl">
        <span class="total">전체 <strong>{{ totalcnt }}</strong>건</span>
        <span>적립 <strong>{{ paycnt }}</strong>건</span>
        <span>차감 <strong>{{ usecnt }}</strong>건</span>
        <span>소멸 <strong>{{ extinctcnt }}</strong>건</span>
      </div>
      <div class="btn-group fr">
        <button type="button" class="btn green-line" @click="downloadExcel"><i class="icon-excel"></i>엑셀다운로드</button>
        <select v-model="pagingData.pageCount">
          <option value="20">20개씩 보기</option>
          <option value="50">50개씩 보기</option>
          <option value="100">100개씩 보기</option>
        </select>
      </div>
    </div>
    <table cellpadding="0" cellspacing="0" class="data-tb align-c">
      <caption>적립금 목록</caption>
      <colgroup>
        <col width="5%" /><!-- No -->
        <col width="10%" /><!-- 포인트 -->
        <col width="10%" /><!-- 자동/수동 -->
        <col width="10%" /><!-- 종류 -->
        <col width="15%" /><!-- 상세 구분 -->
        <col width="" /><!-- 수동지급 사유 -->
        <col width="15%" /><!-- 주문번호 -->
        <col width="15%" /><!-- 적립/차감일자 -->
      </colgroup>
      <thead>
      <tr>
        <th>No</th>
        <th>포인트
          <button type="button" class="sort" :value="sortData.point"
                  :class="[{up : sortData.point=== 'point_asc'}, {down : sortData.point === 'point_desc'}]"
                  @click="sortToggle(sortData.point)"></button>
        </th>
        <th>자동/수동</th>
        <th>종류</th>
        <th>상세 구분</th>
        <th>수동지급/차감 사유</th>
        <th>주문번호</th>
        <th>적립/차감/소멸일자
          <button type="button" class="sort" :value="sortData.regdate"
                  :class="[{up : sortData.regdate=== 'regdate_asc'}, {down : sortData.regdate === 'regdate_desc'}]"
                  @click="sortToggle(sortData.regdate)"></button>
        </th>
      </tr>
      </thead>
      <tbody v-if="reserveList.length > 0">
      <tr v-for="(item, index) in reserveList" :key="index">
        <td>{{ loopNumberForPaging(index) }}</td>
        <td>{{ item.point }}</td>
        <td>{{ item.isauto }}</td>
        <td>{{ item.typekindname }}</td>
        <td>{{ item.typename }}</td>
        <td>{{ item.reason }}</td>
        <td><a href="javascript:void(0);" class="link" @click="goOrderDetail(item.ordercode)">{{ item.ordercode }}</a></td>
        <td>{{ item.regdate }}</td>
      </tr>
      </tbody>
      <tbody v-else>
      <tr>
        <td colspan="7">조회 결과가 존재하지 않습니다.</td>
      </tr>
      </tbody>
    </table>
    <div class="bottom-group">
      <common-page-navigator :pagingData="pagingData" v-on:setPagingData="setPagingData"/>
      <div class="btn-group">
        <button type="button" class="btn blue" @click="goNewReserve">적립금 수동지급</button>
      </div>
    </div>
    <OrderDetail v-if="isOrderDetailShow" :activeOrderCode="activeOrderCode" @closeDetail="closeOrderDetailPopup"></OrderDetail>
    <ManualRegist v-if="isNewReserveShow" @closePopup="closeNewReservePopup"></ManualRegist>
  </div>
</template>

<script>
import CommonDatePicker from "@views.admin/common/CommonDatePicker.vue";
import CommonPageNavigator from "@views.admin/common/CommonPageNavigator.vue";
import OrderDetail from "@views.admin/order/info/OrderDetail.vue";
import ManualRegist from "@views.admin/promotion/reserve/ManualRegist";

export default {
  name: "AdminMemberEmpPoint",
  props: ['activeUserNo'],
  components: { CommonDatePicker, CommonPageNavigator, OrderDetail, ManualRegist },
  data() {
    return {
      reserveList: [],
      isOrderDetailShow: false,   // 주문상세 노출여부
      activeOrderCode: '',        // 주문번호
      totalcnt: 0,
      paycnt: 0,
      usecnt: 0,
      extinctcnt: 0,
      pay: 0,
      used: 0,
      pos: 0,
      extinct: 0,
      expected: 0,
      tomorrow : null,
      searchData: {
        userno: this.activeUserNo,
        startDate: '',          // 조회시작날짜
        endDate: '',            // 조회종료날짜
        psort: 'regdate_desc',   // 정렬
        isempreservearr : ['T'],
        isempreserve : 'T'
      },
      sortData: {
        point : 'point_asc',
        regdate : 'regdate_desc'
      },
      pagingData: {
        pageCount: 20,      // 페이징 옵션(최대수)
        page: 1,            // 현재 페이지
        listCount: 0        // 총 수량
      },
      isNewReserveShow: false,    // 적립금수동지급 노출여부
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
      this.$http.post('/admin/member/reserve/list', params).then(result => {
        this.reserveList = result.data.list;
        this.totalcnt = result.data.totalcnt;
        this.paycnt = result.data.paycnt;
        this.usecnt = result.data.usecnt;
        this.extinctcnt = result.data.extinctcnt;
        this.pagingData.listCount = result.data.totalcnt;


        this.tomorrow = result.data.tomorrow;
        this.pay = result.data.pay;
        this.used = result.data.used;
        this.pos = result.data.pos;
        this.extinct = result.data.extinct;
        this.expected = result.data.expected;

        this.$util.dataSetSearchParam(this);
      }).catch(error => {
        this.$util.debug(error);
      })
    },
    loopNumberForPaging(index) {
      if(this.pagingData.page > 1){
        let page = this.pagingData.page - 1;
        return (index+1) + (page * this.pagingData.pageCount);
      } else {
        return (index+1);
      }
    },
    goOrderDetail: function(ordercode) {
      this.isOrderDetailShow = true;
      this.activeOrderCode = ordercode;
    },
    // 주문상세 팝업 닫기
    closeOrderDetailPopup: function() {
      this.isOrderDetailShow = false;
    },
    // 시작날짜 picker 콜백 함수
    onChangeStartDate: function (value) {
      this.searchData.startDate = value;
    },
    // 종료날짜 picker 콜백 함수
    onChangeEndDate: function (value) {
      this.searchData.endDate = value;
    },
    // 페이징데이터 세팅
    setPagingData(param){
      this.pagingData = param;
      this.onSearch();
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
    // 엑셀다운로드
    downloadExcel() {
      let config = { responseType: 'arraybuffer' };
      this.$http.post('/admin/member/reserve/exceldown', this.searchData, config)
          .then(result => {
            this.$util.debug(result);
          })
          .catch(error => {
            this.$util.debug(error);
          });
    },
    // 적립금수동지급 팝업 오픈
    goNewReserve: function() {
      this.isNewReserveShow = true;
    },
    // 적립금수동지급 팝업 닫기
    closeNewReservePopup: function(isreload) {
      this.isNewReserveShow = false;
      if (isreload) {
        this.onSearch();
      }
    },
  }
}
</script>

<style scoped />