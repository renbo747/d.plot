<template>
  <div>
  <!-- 컨텐츠 영역 -->
  <div class="content m-leftmenu">
    <common-navigator></common-navigator>
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
      </div>
      <div class="btn-group" v-show="isRead">
        <button type="button" class="btn big blue" @click="onSearch(1)">검색</button>
        <button type="button" class="btn big gray" @click="initParam">초기화</button>
      </div>
      <div class="caption-group mt10 clearfix">
        <div class="total-group fl">
          <span class="total">전체 <strong>{{ totalCnt }}</strong>건</span>
        </div>
        <div class="btn-group fr">
          <button type="button" class="btn green-line" v-show="isRead" @click="goExcelDownload" ><i class="icon-excel"></i>엑셀다운로드</button>
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
          <col width="5%" /><!-- No -->
          <col width="" /><!-- 일자 -->
          <col width="15%" /><!-- 전체 -->
          <col width="15%" /><!-- PC -->
          <col width="15%" /><!-- 모바일 -->
          <col width="15%" /><!-- 모바일App -->
        </colgroup>
        <thead>
        <tr>
          <th>No</th>
          <th>일자
            <button type="button" :value="sortData.date" class="sort"
                    :class="[{up : sortData.date === 'date_asc'}, {down : sortData.date === 'date_desc'}]"
                    @click="sortToggle(sortData.date)"></button>
          </th>
          <th>전체
            <button type="button" :value="sortData.total" class="sort"
                    :class="[{up : sortData.total === 'total_asc'}, {down : sortData.total === 'total_desc'}]"
                    @click="sortToggle(sortData.total)"></button>
          </th>
          <th>PC
            <button type="button" :value="sortData.pc" class="sort"
                    :class="[{up : sortData.pc === 'pc_asc'}, {down : sortData.pc === 'pc_desc'}]"
                    @click="sortToggle(sortData.pc)"></button>
          </th>
          <th>모바일
            <button type="button" :value="sortData.mobile" class="sort"
                    :class="[{up : sortData.mobile === 'mobile_asc'}, {down : sortData.mobile === 'mobile_desc'}]"
                    @click="sortToggle(sortData.mobile)"></button>
          </th>
          <th>모바일App
            <button type="button" :value="sortData.app" class="sort"
                    :class="[{up : sortData.app === 'app_asc'}, {down : sortData.app === 'app_desc'}]"
                    @click="sortToggle(sortData.app)"></button>
          </th>
        </tr>
        </thead>
        <tbody v-if="listData.length > 0">
        <tr class="bg blue">
          <td></td>
          <td class="center">합계</td>
          <td>{{ total }}</td>
          <td>{{ pc }}</td>
          <td>{{ mobile }}</td>
          <td>{{ app }}</td>
        </tr>
        <tr v-for="(row, index) in listData" v-bind:key="index">
          <td class="center">{{ loopNumberForPaging(index) }}</td>
          <td class="center">{{ row.joindate }}</td>
          <td>{{ row.total }}</td>
          <td>{{ row.pc }}</td>
          <td>{{ row.mobile }}</td>
          <td>{{ row.app }}</td>
        </tr>
        </tbody>
        <tbody v-else>
        <tr>
          <td colspan="6" class="center">조회 결과가 존재하지 않습니다.</td>
        </tr>
        </tbody>
      </table>
      <div class="bottom-group">
        <CommonPageNavigator v-show="isRead" v-bind:pagingData="this.pagingData" v-on:setPagingData="setPagingData"/>
      </div>
    </div>
  </div>
  <!-- /컨텐츠 영역 -->
  </div>
</template>

<script>
import CommonNavigator from "@views.admin/common/CommonNavigator";
import CommonPageNavigator from "@views.admin/common/CommonPageNavigator";
import CommonDatePicker from "@views.admin/common/CommonDatePicker";

export default {
  name: "admin.stats.newmember",
  components: {CommonNavigator, CommonPageNavigator, CommonDatePicker},
  data() {
    return {
      searchData: {
        startdate: '',
        enddate: '',
        period: '3',
        type : 'day',
        state: '',
        skey: 'id',
        sword: '',
        psort: ''
      },
      pagingData: {
        pageCount: 20,
        page: 1,
        listCount: 0
      },
      sortData: {
        date: 'date_desc',
        total: 'total_desc',
        pc: 'pc_desc',
        mobile: 'mobile_desc',
        app: 'app_desc'
      },
      isRead : false,
      isWrite : false,
      listData: [],
      totalCnt : 0,
      total : 0,
      pc : 0,
      mobile : 0,
      app : 0
    }
  },
  mounted() {
    this.initParam();


    this.$http.post('/admin/common/pageAuth/check', {url : this.$options.name, isloading: false}).then(result => {
      this.isRead = (result.data.isread === 'T');
      this.isWrite = (result.data.iswrite === 'T');

      if(this.isRead){
        this.onSearch();
      }

    }).catch(error => {
      this.$util.debug(error);
    })
  },
  methods: {
    loopNumberForPaging(index) {
      if(this.pagingData.page > 1){
        let page = this.pagingData.page - 1;
        return (index+1) + (page * this.pagingData.pageCount);
      } else {
        return (index+1);
      }

    },
    initParam(){
      this.searchData = this.$options.data().searchData;
      this.searchData.startdate = this.$util.getAddMonth(this.$util.getDate(''), -3, '-');
      this.searchData.enddate = this.$util.getDate('-');
    },
    onSearch(page) {
      let param = this.searchData;

      param.pageCount = this.pagingData.pageCount;
      param.page = (typeof page === 'undefined') ? this.pagingData.page : 1;
      param.listCount = this.pagingData.listCount;

      this.$http.post('/admin/stats/member/list', param).then(result => {

        this.listData = result.data.list;
        this.totalCnt = result.data.cnt;
        this.pagingData.listCount = result.data.cnt;
        this.total = result.data.total;
        this.pc = result.data.pc;
        this.mobile = result.data.mobile;
        this.app = result.data.app;

        this.$util.dataSetSearchParam(this);
      }).catch(error => {
        this.$util.debug(error);
      })
    },
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
    setPagingData(obj) {
      this.pagingData = obj;
      this.onSearch();
    },
    // 날짜 picker 콜백 함수
    onChangeStartDate(val) {
      this.searchData.startdate = val;
    },
    // 날짜 picker 콜백 함수
    onChangeEndDate(val) {
      this.searchData.enddate = val;
    },
    goExcelDownload(){
      if(this.listData.length <= 0){
        alert('데이터가 존재하지 않습니다.');
        return;
      }
      let param = this.searchData;
      let config = { responseType: 'arraybuffer' };
      this.$http.post('/admin/stats/member/list/excel', param, config);
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