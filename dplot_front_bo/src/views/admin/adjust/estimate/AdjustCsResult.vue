<template>
  <div id="modal-wrap" class="modal" style="display: block;">
    <div class="modal-content" style="width: 1600px;">
      <div class="pop-header">
        <h2>CS비용처리내역</h2>
        <button type="button" class="pop-close" @click="onClose"></button>
      </div>
      <div class="pop-body">
        <table cellpadding="0" cellspacing="0" class="gray-tb align-c">
          <colgroup>
            <col width="170px">
            <col width="">
            <col width="170px">
            <col width="200px">
            <col width="170px">
            <col width="200px">
            <col width="170px">
            <col width="200px">
          </colgroup>
          <tbody>
          <tr>
            <th>파트너사명</th>
            <td>{{ info.name }}</td>
            <th>파트너사코드</th>
            <td>{{ info.userno }}</td>
            <th>사업자등록번호</th>
            <td>{{ info.bizno }}</td>
            <th>정산예정일</th>
            <td>{{ info.calcexpday }}</td>
          </tr>
          </tbody>
        </table>
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
          <colgroup>
            <col width="3%" /><!-- No -->
            <col width="8%" /><!-- 파트너사명 -->
            <col width="7%" /><!-- 파트너사코드 -->
            <col width="8%" /><!-- 주문번호 -->
            <col width="7%" /><!-- 상품코드 -->
            <col width="7%" /><!-- 단품코드 -->
            <col width="" /><!-- 상품명 -->
            <col width="8%" /><!-- 옵션명 -->
            <col width="5%" /><!-- 처리구분 -->
            <col width="8%" /><!-- 사유 -->
            <col width="10%" /><!-- 완료일시 -->
            <col width="4.5%" /><!-- 배송비 -->
            <col width="5.5%" /><!-- 추가결제금 -->
          </colgroup>
          <thead>
          <tr>
            <th>No</th>
            <th>파트너사 명</th>
            <th>파트너사 코드</th>
            <th>주문번호</th>
            <th>상품코드</th>
            <th>단품코드</th>
            <th>상품명</th>
            <th>옵션명</th>
            <th>처리구분</th>
            <th>사유</th>
            <th>완료일시</th>
            <th>배송비</th>
            <th>추가결제금</th>
          </tr>
          </thead>
          <tbody v-if="csList.length > 0">
            <tr v-for="(row, index) in csList" v-bind:key="index">
              <td>{{ loopNumberForPaging(index) }}</td>
              <td>{{ row.dealername }}</td>
              <td>{{ row.dealerno }}</td>
              <td>{{ row.ordno }}</td>
              <td>{{ row.goodsno }}</td>
              <td>{{ row.optioncode }}</td>
              <td class="left">{{ row.goodsname }}</td>
              <td class="left">{{ row.optionname }}</td>
              <td>{{ row.clmtypename }}</td>
              <td>{{ row.clmreason }}</td>
              <td>{{ row.clmcompdate }}</td>
              <td class="right">{{ row.delivamt }}</td>
              <td class="right">{{ row.adddelivamt }}</td>
            </tr>
          </tbody>
          <tbody v-else>
          <tr>
            <td colspan="12">조회 결과가 존재하지 않습니다.</td>
          </tr>
          </tbody>
        </table>
        <div class="bottom-group">
          <common-page-navigator v-bind:pagingData="pagingData" v-on:setPagingData="setPagingData"></common-page-navigator>
        </div>
        <div class="btn-group mt20">
          <button type="button" class="btn big darkgray" @click="onClose">닫기</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import CommonPageNavigator from "@views.admin/common/CommonPageNavigator";

export default {
  name: "AdjustCsResultPopup",
  components: {CommonPageNavigator},
  props: ['csData'],
  data() {
    return {
      info : this.csData,
      pagingData: {
        pageCount: 20,
        page: 1,
        listCount: 0
      },
      csList : [],
      totalCnt : 0,
    }
  },
  mounted() {
    console.log(this.csData);
    this.onSearch();
  },
  methods : {
    onSearch(page){
      let param = {
        calcidx : this.info.calcidx
      }

      param.pageCount = this.pagingData.pageCount;
      param.page = (typeof page === 'undefined') ? this.pagingData.page : 1;
      param.listCount = this.pagingData.listCount;

      this.$http.post('/admin/adjust/cs/list', param).then(result => {
        this.pagingData.listCount = result.data.listcount;
        this.csList = result.data.list;
        this.totalCnt = result.data.listcount;
        this.$util.dataSetSearchParam(this);
      }).catch(error => {
        this.$util.debug(error);
      })
    },
    onClose() {
      this.$emit('closeCsPopup');
    },
    setPagingData: function (obj) {
      this.pagingData = obj;
      this.onSearch();
    },
    loopNumberForPaging(index) {
      if(this.pagingData.page > 1){
        let page = this.pagingData.page - 1;
        return (index+1) + (page * this.pagingData.pageCount);
      } else {
        return (index+1);
      }
    },
  }
}
</script>

<style scoped>

</style>