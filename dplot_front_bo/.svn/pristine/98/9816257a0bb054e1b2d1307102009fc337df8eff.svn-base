<template>
  <!-- 상세 내역 팝업 -->
  <div id="modal-wrap" class="modal" style="display: block;">
    <div class="modal-content" style="width: 1600px;">
      <div class="pop-header">
        <h2>상세 내역</h2>
        <button type="button" class="pop-close" @click="onClose"></button>
      </div>
      <div class="pop-body">
        <table cellpadding="0" cellspacing="0" class="gray-tb align-c">
          <colgroup>
            <col width="170px">
            <col width="">
            <col width="170px">
            <col width="300px">
            <col width="170px">
            <col width="300px">
          </colgroup>
          <tbody>
          <tr>
            <th>파트너사명</th>
            <td>{{ info.name }}</td>
            <th>파트너사코드</th>
            <td>{{ info.userno }}</td>
            <th>사업자등록번호</th>
            <td>{{ info.bizno }}</td>
          </tr>
          </tbody>
        </table>
        <div class="boxing search-area">
          <dl>
            <dt>조회기간</dt>
            <dd>
              <select v-model="searchData.datetype">
                <option value="complete">완료일</option>
                <option value="apply">신청일</option>
              </select>
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
              <button type="button" class="btn blue" @click="onSearch(1)">검색</button>
            </dd>
          </dl>
        </div>
        <div class="caption-group mt10 clearfix">
          <div class="total-group fl">
            <span class="total">상세내역 <strong>{{ totalCnt }}</strong>건</span>
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
          <table cellpadding="0" cellspacing="0" class="data-tb align-c" style="min-width: 2800px;">
            <colgroup>
              <col width="1.5%" /><!-- No -->
              <col width="2.5%" /><!-- 구분 -->
              <col width="5%" /><!-- 주문/클레임 번호 -->
              <col width="4%" /><!-- 주문/클레임 일시 -->
              <col width="4%" /><!-- 상품코드 -->
              <col width="4%" /><!-- 단품코드 -->
              <col width="3%" /><!-- 상품순번 -->
              <col width="" /><!-- 상품명 -->
              <col width="6%" /><!-- 옵션명 -->
              <col width="4%" /><!-- 판매가 ⓐ -->
              <col width="3.5%" /><!-- 부가세(판매가) -->
              <col width="4%" /><!-- 판매단가 ⓑ -->
              <col width="3%" /><!-- 수수료율 ⓒ -->
              <col width="4%" /><!-- 공급단가 ⓓ(ⓑ*ⓒ) -->
              <col width="3.5%" /><!-- 부가세(공급단가) ⓔ -->
              <col width="3%" /><!-- 판매수량 ⓗ -->
              <col width="3.5%" /><!-- 전체금액 -->
              <col width="3.5%" /><!-- 분담금 ⓕ -->
              <col width="3.5%" /><!-- 전체금액 -->
              <col width="3.5%" /><!-- 분담금 ⓖ -->
              <col width="3%" /><!-- 배송비 ⓘ -->
              <col width="3%" /><!-- 추가결제금 ⓙ -->
              <col width="3%" /><!-- 조정금액 ⓚ -->
              <col width="3%" /><!-- 주문상태 -->
              <col width="4%" /><!-- 완료일시 -->
            </colgroup>
            <thead>
            <tr>
              <th rowspan="2">No</th>
              <th rowspan="2">구분
                <button type="button" :value="sortData.type" class="sort"
                        :class="[{up : sortData.type === 'type_asc'}, {down : sortData.type === 'type_desc'}]"
                        @click="sortToggle(sortData.type)"></button>
              </th>
              <th rowspan="2">주문번호<br>/클레임번호
                <button type="button" :value="sortData.ordno" class="sort"
                        :class="[{up : sortData.ordno === 'ordno_asc'}, {down : sortData.ordno === 'ordno_desc'}]"
                        @click="sortToggle(sortData.ordno)"></button>
              </th>
              <th rowspan="2">주문<br>/클레임일시
                <button type="button" :value="sortData.orderdate" class="sort"
                        :class="[{up : sortData.orderdate === 'orderdate_asc'}, {down : sortData.orderdate === 'orderdate_desc'}]"
                        @click="sortToggle(sortData.orderdate)"></button>
              </th>
              <th rowspan="2">상품코드
                <button type="button" :value="sortData.goodsno" class="sort"
                        :class="[{up : sortData.goodsno === 'goodsno_asc'}, {down : sortData.goodsno === 'goodsno_desc'}]"
                        @click="sortToggle(sortData.goodsno)"></button>
              </th>
              <th rowspan="2">단품코드
                <button type="button" :value="sortData.optioncode" class="sort"
                        :class="[{up : sortData.optioncode === 'optioncode_asc'}, {down : sortData.optioncode === 'optioncode_desc'}]"
                        @click="sortToggle(sortData.optioncode)"></button>
              </th>
              <th rowspan="2">상품순번
                <button type="button" :value="sortData.turn" class="sort"
                        :class="[{up : sortData.turn === 'turn_asc'}, {down : sortData.turn === 'turn_desc'}]"
                        @click="sortToggle(sortData.turn)"></button>
              </th>
              <th rowspan="2">상품명
                <button type="button" :value="sortData.goodsname" class="sort"
                        :class="[{up : sortData.goodsname === 'goodsname_asc'}, {down : sortData.goodsname === 'goodsname_desc'}]"
                        @click="sortToggle(sortData.goodsname)"></button>
              </th>
              <th rowspan="2">옵션명
                <button type="button" :value="sortData.optionname" class="sort"
                        :class="[{up : sortData.optionname === 'optionname_asc'}, {down : sortData.optionname === 'optionname_desc'}]"
                        @click="sortToggle(sortData.optionname)"></button>
              </th>
              <th rowspan="2" class="tk-left tk-top tk-bottom">판매단가<br>ⓐ</th>
              <th rowspan="2" class="tk-top tk-bottom">부가세(판매가)</th>
              <th rowspan="2" class="tk-top tk-bottom">판매단가(VAT제외)<br>ⓑ</th>
              <th rowspan="2" class="tk-left tk-top tk-bottom">수수료율<br>ⓒ</th>
              <th rowspan="2" class="tk-top tk-bottom">공급단가<br>ⓓ(ⓑ*ⓒ)</th>
              <th rowspan="2" class="tk-top tk-bottom">부가세(공급단가)<br>ⓔ</th>
              <th rowspan="2" class="tk-left tk-top tk-bottom">판매수량<br>ⓗ</th>
              <th colspan="2" class="tk-left tk-top">기본할인프로모션</th>
              <th colspan="2" class="tk-top">상품쿠폰</th>
              <th colspan="2" class="tk-top">장바구니쿠폰</th>
              <th rowspan="2" class="tk-left tk-top tk-bottom">기본배송비<br>ⓘ</th>
              <th rowspan="2" class="tk-top tk-bottom">추가배송비<br>ⓙ</th>
              <th rowspan="2" class="tk-left tk-top tk-bottom">정산금액<br>(ⓐ-ⓓ-ⓔ)*ⓗ+ⓕ+ⓖ+ⓘ+ⓙ+ⓚ</th>
              <th rowspan="2" class="tk-left">주문상태
                <button type="button" :value="sortData.ordstatus" class="sort"
                        :class="[{up : sortData.ordstatus === 'ordstatus_asc'}, {down : sortData.ordstatus === 'ordstatus_desc'}]"
                        @click="sortToggle(sortData.ordstatus)"></button>
              </th>
              <th rowspan="2">완료일시
                <button type="button" :value="sortData.completedate" class="sort"
                        :class="[{up : sortData.completedate === 'completedate_asc'}, {down : sortData.completedate === 'completedate_desc'}]"
                        @click="sortToggle(sortData.completedate)"></button>
              </th>
            </tr>
            <tr>
              <th class="bg-yellow tk-left tk-bottom">전체금액</th>
              <th class="bg-yellow tk-bottom">분담금<br>ⓕ</th>
              <th class="bg-yellow tk-bottom">전체금액</th>
              <th class="bg-yellow tk-bottom">분담금<br>ⓖ</th>
              <th class="bg-yellow tk-bottom">전체금액</th>
              <th class="bg-yellow tk-bottom">분담금<br>ⓚ</th>
            </tr>
            </thead>
            <tbody v-if="detailList.length > 0">
              <tr v-for="(row, index) in detailList" v-bind:key="index">
                <td>{{ loopNumberForPaging(index) }}</td>
                <td>{{ row.type }}</td>
                <td>{{ row.ordno }}</td>
                <td>{{ row.orddate }}<br>{{ row.ordtime }}</td>
                <td>{{ row.goodsno }}</td>
                <td>{{ row.optioncode }}</td>
                <td>{{ row.goodsturn }}</td>
                <td class="left">{{ row.goodsname }}</td>
                <td class="left">{{ row.optionname }}</td>
                <td class="right">{{ row.price }}</td>
                <td class="right">{{ row.vatamt }}</td>
                <td class="right">{{ row.saleamtmvat }}</td>
                <td class="right">{{ row.commrate }}%</td>
                <td class="right">{{ row.supplyamt }}</td>
                <td class="right">{{ row.supplyvatamt }}</td>
                <td class="right">{{ row.cnt }}</td>
                <td class="right">{{ row.salepromoamt }}</td>
                <td class="right">{{ row.promoshareamt }}</td>
                <td class="right">{{ row.goodscpnamt }}</td>
                <td class="right">{{ row.cpnshareamt }}</td>
                <td class="right">{{ row.basketcpnamt }}</td>
                <td class="right">{{ row.basketcpnshareamt }}</td>
                <td class="right">{{ row.delivamt }}</td>
                <td class="right">{{ row.addrpaytotprice }}</td>
                <td class="right">{{ row.calcamt }}</td>
                <td>{{ row.statusname }}</td>
                <td>{{ row.comdate }}<br>{{ row.comtime }}</td>
              </tr>
            </tbody>
            <tbody v-else>
            <tr>
              <td colspan="22">조회 결과가 존재하지 않습니다.</td>
            </tr>
            </tbody>
          </table>
        </div>
        <div class="bottom-group">
          <common-page-navigator v-bind:pagingData="pagingData"
                                 v-on:setPagingData="setPagingData"></common-page-navigator>
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
import CommonDatePicker from "@views.admin/common/CommonDatePicker";

export default {
  name: "AdjustEstimateDetail",
  components: {CommonPageNavigator, CommonDatePicker},
  props: ['dtlData'],
  data() {
    return {
      info : this.dtlData,
      searchData: {
        startdate: '',
        enddate: '',
        period: '1',
        datetype: 'complete',
        psort: '',
        userno : 0
      },
      pagingData: {
        pageCount: 20,
        page: 1,
        listCount: 0
      },
      sortData: {
        type: 'type_desc',
        ordno: 'ordno_desc',
        orderdate: 'orderdate_desc',
        goodsno: 'goodsno_desc',
        optioncode: 'optioncode_desc',
        turn: 'turn_desc',
        goodsname: 'goodsname_desc',
        optionname: 'optionname_desc',
        ordstatus: 'ordstatus_desc',
        completedate: 'completedate_desc'
      },
      totalCnt: 0,
      detailList: []
    }
  },
  mounted() {
    console.log(this.info);
    let calcMonthArr = this.info.calcmonth.split("-");
    let calcLastDate = new Date(parseInt(calcMonthArr[0]), parseInt(calcMonthArr[1]), 0)

    this.searchData.startdate = this.info.calcmonth + '-01';
    this.searchData.enddate = this.$util.getDateFormat(calcLastDate, "yyyy-MM-dd");

    this.onSearch(1);
  },
  methods : {
    onSearch(page) {
      let param = this.searchData;
      param.calcidx = this.dtlData.calcidx;
      param.pageCount = this.pagingData.pageCount;
      param.page = (typeof page === 'undefined') ? this.pagingData.page : 1;
      param.listCount = this.pagingData.listCount;

      this.$http.post('/admin/adjust/detail/list', param).then(result => {
        this.pagingData.listCount = result.data.listcount;
        this.detailList = result.data.list;
        this.totalCnt = result.data.listcount;
        this.$util.dataSetSearchParam(this);
      }).catch(error => {
        this.$util.debug(error);
      })
    },
    setPagingData: function (obj) {
      this.pagingData = obj;
      this.onSearch();
    },
    onChangeStartDate(val) {
      this.searchData.startdate = val;
    },
    // 날짜 picker 콜백 함수
    onChangeEndDate(val) {
      this.searchData.enddate = val;
    },
    loopNumberForPaging(index) {
      if(this.pagingData.page > 1){
        let page = this.pagingData.page - 1;
        return (index+1) + (page * this.pagingData.pageCount);
      } else {
        return (index+1);
      }
    },
    sortToggle: function (key) {
      let arr = key.split("_");
      let sortKey = arr[0];
      let sortOrder = (arr[1] === 'asc') ? 'desc' : 'asc';
      let sortName = sortKey + '_' + sortOrder;

      this.sortData = this.$options.data().sortData;
      this.sortData[sortKey] = sortName;
      this.searchData.psort = sortName;

      this.onSearch();
    },
    onClose() {
      this.$emit('closeDtlPopup');
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