<template>
  <div id="modal-wrap" class="modal" style="display: block;">
    <div class="modal-content" style="width: 1200px;">
      <div class="pop-header">
        <h2>PG결과 대사</h2>
        <button type="button" class="pop-close" @click="onClose"></button>
      </div>
      <div class="pop-body">
        <div class="boxing search-area">
          <dl>
            <dt>결제일시</dt>
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
            <dt>확인상태</dt>
            <dd>
              <div class="radio_wrap wide3">
                <input type="radio" name="group01" id="rd11" v-model="searchData.issame" value="" /><label for="rd11">전체</label>
                <input type="radio" name="group01" id="rd12" v-model="searchData.issame" value="F" /><label for="rd12">차이발생</label>
                <input type="radio" name="group01" id="rd13" v-model="searchData.issame" value="T" /><label for="rd13">이상없음</label>
              </div>
            </dd>
          </dl>
        </div>
        <div class="btn-group">
          <button type="button" class="btn big blue" @click="onSearch">검색</button>
          <button type="button" class="btn big gray" @click="initData">초기화</button>
        </div>
        <div class="caption-group mt10 clearfix">
          <div class="total-group fl">
            <span class="total">주문내역</span>
          </div>
        </div>
        <div class="scroll-y" style="max-height: 500px;">
          <table cellpadding="0" cellspacing="0" class="data-tb align-c">
            <colgroup>
              <col width="5%" /><!-- No -->
              <col width="15%" /><!-- PG결제내역-결제일시 -->
              <col width="15%" /><!-- PG결제내역-결제수단 -->
              <col width="12%" /><!-- PG결제내역-결제금액 -->
              <col width="15%" /><!-- 주문내역-주문번호 -->
              <col width="15%" /><!-- 주문내역-결제일시 -->
              <col width="12%" /><!-- 주문내역-실 결제금액 -->
              <col width="" /><!-- 확인결과 -->
            </colgroup>
            <thead>
            <tr>
              <th rowspan="2">No</th>
              <th colspan="3">PG결제내역</th>
              <th colspan="3">주문내역</th>
              <th rowspan="2">확인결과</th>
            </tr>
            <tr>
              <th class="bg-yellow">결제일시</th>
              <th class="bg-yellow">결제수단</th>
              <th class="bg-yellow">결제금액</th>
              <th class="bg-yellow">주문번호</th>
              <th class="bg-yellow">결제일시</th>
              <th class="bg-yellow">실 결제금액</th>
            </tr>
            </thead>
            <tbody v-if="listData.length > 0">
            <tr v-for="(row, index) in listData" v-bind:key="index">
              <td>{{ index+1 }}</td>
              <td>{{ row.tosspaydate }}</td>
              <td>{{ row.tossmethod }}</td>
              <td class="right"><span v-if="row.tossamount != null">{{ $util.maskComma(row.tossamount) }}</span></td>
              <td><a class="link" @click="goOrderDetail(row.ordno)">{{ row.ordno }}</a></td>
              <td>{{ row.paymentdate }}</td>
              <td class="right"><span v-if="row.rpaytotprice != null">{{ $util.maskComma(row.rpaytotprice) }}</span></td>
              <td>
                <span class="midium txt-blue" v-if="row.issame === 'T'">이상없음</span>
                <span class="midium txt-red" v-if="row.issame === 'F'">차이발생</span>
              </td>
            </tr>
            </tbody>
            <tbody v-else>
            <tr>
              <td colspan="8">조회 결과가 존재하지 않습니다.</td>
            </tr>
            </tbody>
<!--            <tbody>-->
<!--            <tr>-->
<!--              <td>10</td>-->
<!--              <td>2021-10-11 12:11:22</td>-->
<!--              <td>카드결제</td>-->
<!--              <td class="right">25,000</td>-->
<!--              <td><a href="#" class="link">12345678901234</a></td>-->
<!--              <td>2021-10-11 12:11:22</td>-->
<!--              <td class="right">25,000</td>-->
<!--              <td><span class="midium txt-blue">이상없음</span></td>-->
<!--            </tr>-->
<!--            <tr>-->
<!--              <td>10</td>-->
<!--              <td>2021-10-11 12:11:22</td>-->
<!--              <td>카드결제</td>-->
<!--              <td class="right">25,000</td>-->
<!--              <td><a href="#" class="link">12345678901234</a></td>-->
<!--              <td>2021-10-11 12:11:22</td>-->
<!--              <td class="right">22,000</td>-->
<!--              <td><span class="midium txt-red">차이발생</span></td>-->
<!--            </tr>-->
<!--            </tbody>-->
          </table>
        </div>
        <div class="btn-group">
          <button type="button" class="btn big darkgray" @click="onClose">닫기</button>
        </div>
      </div>
    </div>
    <OrderDetail v-if="isShowOrderDetail" :activeOrderCode="activeOrderCode" @closeDetail="closeOrderDetail"></OrderDetail>
  </div>
</template>

<script>
import CommonDatePicker from "@views.admin/common/CommonDatePicker";
import OrderDetail from "@views.admin/order/info/OrderDetail";

export default {
  name: "AdjustCompareResult",
  props: ['compareData'],
  components: {CommonDatePicker, OrderDetail},
  data() {
    return {
      info : this.compareData,
      searchData: {
        startdate: '',
        enddate: '',
        period: '1',
        issame : ''
      },
      listData : [],
      activeOrderCode: '',
      isShowOrderDetail: false,   // 주문상세 팝업 노출여부
    }
  },
  mounted() {
    this.initData();
  },
  methods : {
    onSearch(){
      let param = this.searchData;

      this.$http.post('/admin/adjust/pay/compare', param).then(result => {
        this.listData = result.data.list;
        this.$util.dataSetSearchParam(this);
      }).catch(error => {
        this.$util.debug(error);
      })
    },
    initData() {
      this.searchData = this.$options.data().searchData;
      this.searchData.startdate = this.$util.getAddMonth(this.$util.getDate(''), -1, '-');
      this.searchData.enddate = this.$util.getDate('-');
    },
    onClose() {
      this.$emit('closeComparePopup');
    },
    onChangeStartDate(val) {
      this.searchData.startdate = val;
    },
    // 날짜 picker 콜백 함수
    onChangeEndDate(val) {
      this.searchData.enddate = val;
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
  },
  watch: {
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
    }
  }
}
</script>

<style scoped>

</style>