<template>
  <div class="tab-area" style="height: calc(90vh - 246px);">
    <div class="clearfix">
      <div class="bar-title small fl">1:1 문의</div>
    </div>
    <div class="boxing search-area">
      <dl>
        <dt>조회기간</dt>
        <dd>
          <select>
            <option value="">문의일자</option>
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
    <table cellpadding="0" cellspacing="0" class="data-tb align-c">
      <caption>1:1 문의 목록</caption>
      <colgroup>
        <col width="3%" /><!-- No -->
        <col width="8%" /><!-- 주문번호 -->
        <col width="7%" /><!-- 송장번호 -->
        <col width="" /><!-- 상품명 -->
        <col width="5%" /><!-- 문의유형 -->
        <col width="20%" /><!-- 제목 -->
        <col width="8%" /><!-- 문의일시 -->
        <col width="6%" /><!-- 답변자 -->
        <col width="8%" /><!-- 답변일시 -->
<!--        <col width="6%" />&lt;!&ndash; 첨부이미지 &ndash;&gt;-->
        <col width="5%" /><!-- 답변상태 -->
      </colgroup>
      <thead>
      <tr>
        <th>No</th>
        <th>주문번호</th>
        <th>송장번호</th>
        <th>상품명</th>
        <th>문의유형</th>
        <th>문의내용</th>
        <th>문의일시
          <button type="button"
                  :value="sortData.regdate"
                  class="sort"
                  :class="[{up : sortData.regdate === 'regdate_asc'}, {down : sortData.regdate === 'regdate_desc'}]"
                  @click="sortToggle(sortData.regdate)">
          </button>
        </th>
        <th>답변자</th>
        <th>답변일시
          <button type="button"
                  :value="sortData.repregdate"
                  class="sort"
                  :class="[{up : sortData.repregdate === 'repregdate_asc'}, {down : sortData.repregdate === 'repregdate_desc'}]"
                  @click="sortToggle(sortData.repregdate)">
          </button>
        </th>
<!--        <th>첨부이미지</th>-->
        <th>답변상태</th>
      </tr>
      </thead>
      <tbody v-if="listData.length > 0">
      <tr v-for="(row, index) in listData" v-bind:key="index">
        <td>{{ loopNumberForPaging(index)}}</td>
        <td><a class="link" @click="goOrderDetail(row.ordno)">{{ row.ordno }}</a></td>
        <td>{{ row.invoiceno }}</td>
        <td class="left"><a class="link" @click="goGoodsDetail(row.goodsno)">{{ row.goodsname }}</a></td>
        <td>{{  row.inquirytype }}</td>
        <td class="left"><a class="link" @click="goDetail(row.idx)">{{ row.content }}</a></td>
        <td>{{ row.regdate }}</td>
        <td>{{ row.repname }}</td>
        <td>{{ row.repregdate }}</td>
<!--        <td>2</td>-->
        <td>{{ row.isreply }}</td>
      </tr>
      </tbody>
      <tbody v-else>
      <tr>
        <td colspan="11">조회 결과가 존재하지 않습니다.</td>
      </tr>
      </tbody>
    </table>
    <div class="bottom-group">
      <CommonPageNavigator :pagingData="pagingData" v-on:setPagingData="setPagingData"/>
    </div>

    <GoodsDetail v-if="isGoodsDetailShow" :activeGoodsNo="activeGoodsNo" @closePopup="closeGoodsDetail"></GoodsDetail>
    <OneOneDetail
        ref="refDetail"
        v-if="isDetailShow"
        :idx="isDetailIdx"
    />
    <OrderDetail v-if="isOrderDetailShow" :activeOrderCode="activeOrderCode" @closeDetail="closeOrderDetailPopup"></OrderDetail>
  </div>
</template>

<script>
import CommonDatePicker from "@views.admin/common/CommonDatePicker";
import CommonPageNavigator from "@views.admin/common/CommonPageNavigator";
import OneOneDetail from "@views.admin/cs/oneone/OneOneDetail.vue";
import GoodsDetail from "@views.admin/goods/manage/GoodsDetail.vue";
import OrderDetail from "@views.admin/order/info/OrderDetail.vue";

export default {
  name: "AdminMemberQuestion",
  props: ['activeUserNo'],
  components: {CommonDatePicker, CommonPageNavigator, OneOneDetail, GoodsDetail, OrderDetail},
  data() {
    return {
      isDetailShow: false,      // 상세 팝업
      isGoodsDetailShow: false,
      activeGoodsNo: null,
      isOrderDetailShow: false,
      activeOrderCode : '',
      listData: [],
      totalCnt: 0,
      isDetailIdx: 0,
      searchData: {
        sdate: 'start',
        startDate: '',
        endDate: '',
        userno : this.activeUserNo
      },
      pagingData: {
        pageCount: 20,        // 페이징 옵션(최대수)
        page: 1,              // 현재 페이지
        listCount: 0          // 전체 수
      },
      sortData: {
        regdate: 'regdate_desc',
        repregdate: 'repregdate_desc',
      },
    }
  },
  mounted() {
    this.searchData.startDate = this.$util.getAddMonth(this.$util.getDate(''), -3, '-');
    this.searchData.endDate = this.$util.getDate('-');
    this.onSearch();
  },
  methods : {
    sortToggle(key) {
      let arr = key.split("_");
      let sortKey = arr[0];
      let sortOrder = (arr[1] === 'asc') ? 'desc' : 'asc';
      let sortName = sortKey + '_' + sortOrder;

      this.sortData = this.$options.data().sortData;

      this.sortData[sortKey] = sortName;
      this.searchData.psort = sortName;

      this.onSearch();
    },
    onSearch() {
      let param = this.searchData;
      param.pageCount = this.pagingData.pageCount;
      param.page = (typeof page === 'undefined') ? this.pagingData.page : 1;
      param.listCount = this.pagingData.listCount;

      this.$http.post("/admin/cs/oneone/search", param).then(result => {
        let data = result.data;
        this.listData = data.list;
        this.pagingData.listCount = data.statelist.total;
        this.totalCnt = data.statelist.total;
      }).catch(error => {
          this.$util.debug(error);
      });
    },
    setPagingData(param) {
      this.pagingData = param;
      this.onSearch();
    },
    onChangeEndDate(val) {
      this.searchData.endDate = val;
    },
    // 날짜 picker 콜백 함수
    onChangeStartDate(val) {
      this.searchData.startDate = val;
    },
    loopNumberForPaging(index) {
      if(this.pagingData.page > 1){
        let page = this.pagingData.page - 1;
        return (index+1) + (page * this.pagingData.pageCount);
      } else {
        return (index+1);
      }
    },
    goDetail(idx) {
      this.isDetailIdx = idx;
      this.isDetailShow = true;
      this.$refs.refDetail.onOpen();
    },
    goCloseDetailPopup() {
      this.isDetailShow = false;
      this.onSearch();
    },
    goGoodsDetail: function(value) {
      // $('body').find('.modal-content').css('width', '1600px');
      this.activeGoodsNo = value;
      this.isGoodsDetailShow = true;
    },
    closeGoodsDetail(){
      // $('body').find('.modal-content').css('width', '1600px');
      this.isGoodsDetailShow = false;
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
  }
}
</script>

<style scoped>

</style>