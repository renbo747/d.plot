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
          <dt>조회기준</dt>
          <dd>
            <div class="radio_wrap wide">
              <input type="radio" name="group07" id="group71" v-model="searchData.standard" value="PAY"><label for="group71">결제완료</label>
              <input type="radio" name="group07" id="group72" v-model="searchData.standard" value="BUY"><label for="group72">구매확정</label>
            </div>
          </dd>
        </dl>
      </div>
      <div class="btn-group">
        <button type="button" class="btn big blue" @click="searchList">검색</button>
        <button type="button" class="btn big gray" @click="initSearchData">초기화</button>
      </div>
      <div class="caption-group mt10 clearfix">
        <div class="total-group fl">
          <span class="total">전체 <strong>{{ totalCnt }}</strong>건</span>
        </div>
        <div class="btn-group fr">
          <button type="button" class="btn green-line" @click="goExcelDownload"><i class="icon-excel"></i>엑셀다운로드</button>
          <select v-model="pagingData.pageCount">
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
          <col width="9%" /><!-- 일자 -->
          <col width="7%" /><!-- 구매건수 -->
          <col width="7%" /><!-- 구매수량 -->
          <col width="8%" /><!-- 상품구매금액 -->
          <col width="8%" /><!-- 배송비 -->
          <col width="8%" /><!-- 할인분담금 -->
          <col width="8%" /><!-- 쿠폰분담금 -->
          <col width="8%" /><!-- 실 결제금액 -->
          <col width="9%" /><!-- 매출 ① -->
          <col width="9%" /><!-- 환불합계 ② -->
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
            <button type="button" :value="sortData.frstordcnt" class="sort"
                    :class="[{up : sortData.frstordcnt === 'frstordcnt_asc'}, {down : sortData.frstordcnt === 'frstordcnt_desc'}]"
                    @click="sortToggle(sortData.frstordcnt)"></button>
          </th>
          <th>상품구매금액
            <button type="button" :value="sortData.ordamt" class="sort"
                    :class="[{up : sortData.ordamt === 'ordamt_asc'}, {down : sortData.ordamt === 'ordamt_desc'}]"
                    @click="sortToggle(sortData.ordamt)"></button>
          </th>
          <th>배송비
            <button type="button" :value="sortData.delivamt" class="sort"
                    :class="[{up : sortData.delivamt === 'delivamt_asc'}, {down : sortData.delivamt === 'delivamt_desc'}]"
                    @click="sortToggle(sortData.delivamt)"></button>
          </th>
          <th>할인분담금
            <button type="button" :value="sortData.promoshareamt" class="sort"
                    :class="[{up : sortData.promoshareamt === 'promoshareamt_asc'}, {down : sortData.promoshareamt === 'promoshareamt_desc'}]"
                    @click="sortToggle(sortData.promoshareamt)"></button>
          </th>
          <th>쿠폰분담금
            <button type="button" :value="sortData.cpnshareamt" class="sort"
                    :class="[{up : sortData.cpnshareamt === 'cpnshareamt_asc'}, {down : sortData.cpnshareamt === 'cpnshareamt_desc'}]"
                    @click="sortToggle(sortData.cpnshareamt)"></button>
          </th>
          <th>실 결제금액
            <button type="button" :value="sortData.realamt" class="sort"
                    :class="[{up : sortData.realamt === 'realamt_asc'}, {down : sortData.realamt === 'realamt_desc'}]"
                    @click="sortToggle(sortData.realamt)"></button>
          </th>
          <th>
            <i class="icon-th-q" @click="toggleData.price = !toggleData.price"></i>매출 ①
            <button type="button" :value="sortData.price" class="sort"
                    :class="[{up : sortData.price === 'price_asc'}, {down : sortData.price === 'price_desc'}]"
                    @click="sortToggle(sortData.price)"></button>
            <div class="th-cell" :class="[{dpb : toggleData.price}, {dpn : !toggleData.price}]" style="width: 345px;"><!-- dpb:보임 / dpn:숨김 -->
              매출 = 상품구매금액 + 배송비 - 쿠폰분담금 - 할인분담금
            </div>
          </th>
          <th>
            <i class="icon-th-q" @click="toggleData.rtnamt = !toggleData.rtnamt"></i>환불합계 ②
            <button type="button" :value="sortData.rtnamt" class="sort"
                    :class="[{up : sortData.rtnamt === 'rtnamt_asc'}, {down : sortData.rtnamt === 'rtnamt_desc'}]"
                    @click="sortToggle(sortData.rtnamt)"></button>
            <div class="th-cell" :class="[{dpb : toggleData.rtnamt}, {dpn : !toggleData.rtnamt}]" style="width: 325px;"><!-- dpb:보임 / dpn:숨김 -->
              고객반환 실 환불금액
            </div>
          </th>
        </tr>
        </thead>
        <tbody v-if="listData.length > 0">
        <tr class="bg blue">
          <td colspan="2">합계</td>
          <td>{{ ordercnt }}</td>
          <td>{{ frstordcnt }}</td>
          <td>{{ ordamt }}</td>
          <td>{{ delivamt }}</td>
          <td>{{ promoshareamt }}</td>
          <td>{{ cpnshareamt }}</td>
          <td>{{ realamt }}</td>
          <td>{{ price }}</td>
          <td>{{ rtnamt }}</td>
        </tr>
        <tr v-for="(row, index) in listData" v-bind:key="index">
          <td class="center">{{ loopNumberForPaging(index) }}</td>
          <td class="center">{{ row.target }}</td>
          <td>{{ row.ordercnt }}</td>
          <td>{{ row.frstordcnt }}</td>
          <td>{{ row.ordamt }}</td>
          <td>{{ row.delivamt }}</td>
          <td>{{ row.promoshareamt }}</td>
          <td>{{ row.cpnshareamt }}</td>
          <td>{{ row.realamt }}</td>
          <td>{{ row.price }}</td>
          <td>{{ row.rtnamt }}</td>
        </tr>
        </tbody>
        <tbody v-else>
        <tr>
          <td colspan="13" class="center">조회 결과가 존재하지 않습니다.</td>
        </tr>
        </tbody>
      </table>
      <div class="bottom-group">
        <CommonPageNavigator :pagingData="pagingData" v-on:setPagingData="setPagingData"/>
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
  name: "partners.adjust.stat",
  components: {
    CommonNavigator,
    CommonPageNavigator,
    CommonDatePicker
  },
  data() {
    return {
      isRead: false,
      isWrite: false,
      user: {},
      searchData: {
        startdate: '',          // 조회시작날짜
        enddate: '',            // 조회종료날짜
        period: '3',             // 조회기간
        standard : 'PAY',
        psort: '',   // 정렬
      },
      pagingData: {
        pageCount: 20,      // 페이징 옵션(최대수)
        page: 1,            // 현재 페이지
        listCount: 0        // 총 수량
      },
      sortData: {
        target : 'target_asc',
        ordercnt : 'ordercnt_desc',
        frstordcnt : 'frstordcnt_desc',
        ordamt : 'ordamt_desc',
        delivamt : 'delivamt_desc',
        promoshareamt : 'promoshareamt_desc',
        cpnshareamt : 'cpnshareamt_desc',
        realamt : 'realamt_desc',
        price : 'price_desc',
        rtnamt : 'rtnamt_desc',
      },
      toggleData : {
        price : false,
        rtnamt : false,
      },
      totalCnt : 0,
      ordercnt : 0,
      frstordcnt : 0,
      ordamt : 0,
      delivamt : 0,
      promoshareamt : 0,
      cpnshareamt : 0,
      pure : 0,
      price : 0,
      rtnamt : 0,
      listData: [],

    }
  },
  mounted() {
    this.initSearchData();
    this.user = this.$util.getUser(this.$store.getters['CONSTANTS'].MANAGER_SESSION);
  },
  methods : {
    initSearchData() {
      this.searchData = this.$options.data().searchData;
      this.searchData.startdate = this.$util.getAddMonth(this.$util.getDate(''), -3, '-');
      this.searchData.enddate = this.$util.getDate('-');
    },
    searchList() {
      let params = Object.assign(this.searchData, this.pagingData);
      params.userno = this.user.no;
      this.$http.post('/partners/common/sale/list', params).then(result =>{

        this.totalCnt = result.data.total;
        this.pagingData.listCount = result.data.total;
        this.listData = result.data.list;

        this.ordercnt = result.data.ordercnt;
        this.frstordcnt = result.data.frstordcnt;
        this.ordamt = result.data.ordamt;
        this.delivamt = result.data.delivamt;
        this.promoshareamt = result.data.promoshareamt;
        this.cpnshareamt = result.data.cpnshareamt;
        this.realamt = result.data.realamt;
        this.price = result.data.price;
        this.rtnamt = result.data.rtnamt;

        this.$util.dataSetSearchParam(this);

      }).catch(error =>{
        this.$util.debug(error);
      })

    },
    // 날짜 picker 콜백 함수
    onChangeStartDate(val) {
      this.searchData.startdate = val;
    },
    // 날짜 picker 콜백 함수
    onChangeEndDate(val) {
      this.searchData.enddate = val;
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
      this.$http.post('/partners/common/sale/list/excel', param, config);
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
    }
  }
}
</script>

<style scoped>

</style>