<template>
  <!-- 컨텐츠 영역 -->
  <div class="content m-leftmenu">
    <common-navigator></common-navigator>
    <div class="inner">
      <div class="boxing search-area">
        <dl>
          <dt>조회기간</dt>
          <dd>
            <CommonDatePicker :value="searchData.startdate" :formatType="formatType" @change="onChangeStartDate"/>
            <span>~</span>
            <CommonDatePicker :value="searchData.enddate" :formatType="formatType" @change="onChangeEndDate"/>
          </dd>
        </dl>
        <dl>
          <dt>직접검색</dt>
          <dd>
            <select v-model="searchData.skey">
              <option value="">전체</option>
              <option value="name">파트너사명</option>
              <option value="userno">파트너사코드</option>
              <option value="bizno">사업자등록번호</option>
            </select>
            <input type="text" v-model="searchData.sword" @keyup.enter="onSearch(1)"/>
          </dd>
        </dl>
        <dl>
          <dt>상태</dt>
          <dd>
            <div class="radio_wrap wide4">
              <div>
                <input type="radio" name="calcstatus" id="calcAll" value="" v-model="searchData.calcstatus"/>
                <label for="calcAll">전체</label>
              </div>
              <div v-for="item in commonCode.calcStatus" :key="item.cmcode">
                <input type="radio" name="calcstatus" :id="'calcstatus_'+item.cmcode" :value="item.cmcode" v-model="searchData.calcstatus"/>
                <label :for="'calcstatus_'+item.cmcode">{{ item.codename }}</label>
              </div>
            </div>
          </dd>
        </dl>
      </div>
      <div class="btn-group">
        <button type="button" class="btn big blue" v-if="isRead" @click="onSearch(1)">검색</button>
        <button type="button" class="btn big gray" v-if="isRead" @click="initData">초기화</button>
      </div>
      <div class="caption-group mt10 clearfix">
        <div class="total-group fl">
          <span class="total">전체 <strong>{{ totalCnt }}</strong>건</span>
        </div>
        <div class="btn-group fr">
          <button type="button" class="btn blue-line" v-if="isWrite" @click="calcStateUpdate('CAS002')">마감</button>
          <button type="button" class="btn blue-line" v-if="isRead" @click="showComparePopup">PG결과 대사</button>
          <button type="button" class="btn green-line" v-if="isWrite" @click="excelDown"><i class="icon-excel"></i>신고 용 엑셀다운로드</button>
          <select v-model="pagingData.pageCount" v-show="isRead">
            <option value="20">20개씩 보기</option>
            <option value="50">50개씩 보기</option>
            <option value="100">100개씩 보기</option>
          </select>
        </div>
      </div>
      <div class="scroll-x">
        <table cellpadding="0" cellspacing="0" class="data-tb align-c" style="min-width: 2000px;">
          <caption>정산예정내역 목록</caption>
          <colgroup>
            <col width="30px" /><!-- checkbox -->
            <col width="2%" /><!-- No -->
            <col width="4%" /><!-- 년/월 -->
            <col width="" /><!-- 파트너사명 -->
            <col width="5%" /><!-- 파트너사코드 -->
            <col width="5.5%" /><!-- 사업자등록번호 -->
            <col width="5.5%" /><!-- 정산예정일 -->
            <col width="5%" /><!-- 총 판매금액 (판매가x수량) ⓐ -->
            <col width="5.5%" /><!-- 총 공급단가 (공급단가x수량) ⓑ -->
            <col width="6.5%" /><!-- 총 부가세(공급단가) (부가세x수량) ⓒ -->
            <col width="5.5%" /><!-- 전체금액 -->
            <col width="5%" /><!-- 분담금 ⓒ -->
            <col width="5%" /><!-- 전체금액 ⓓ -->
            <col width="5%" /><!-- 분담금 ⓔ -->
            <col width="5%" /><!-- 전체금액  -->
            <col width="5%" /><!-- 분담금 ⓔ -->
            <col width="5%" /><!-- 배송비 ⓕ -->
            <col width="5%" /><!-- 추가결제금 ⓕ -->
            <col width="6.5%" /><!-- 정산합계 ⓐ+ⓑ+ⓒ+ⓓ+ⓔ+ⓕ+ⓘ -->
            <col width="4%" /><!-- 상태 -->
          </colgroup>
          <thead>
          <tr>
            <th rowspan="2"><input type="checkbox" v-model="allChecked" @change="checkedAll($event.target.checked)" /></th>
            <th rowspan="2">No</th>
            <th rowspan="2">년/월
              <button type="button" :value="sortData.month" class="sort"
                      :class="[{up : sortData.month === 'month_asc'}, {down : sortData.month === 'month_desc'}]"
                      @click="sortToggle(sortData.month)"></button>
            </th>
            <th rowspan="2">파트너사 명
              <button type="button" :value="sortData.name" class="sort"
                      :class="[{up : sortData.name === 'name_asc'}, {down : sortData.name === 'name_desc'}]"
                      @click="sortToggle(sortData.name)"></button>
            </th>
            <th rowspan="2">파트너사 코드
              <button type="button" :value="sortData.userno" class="sort"
                      :class="[{up : sortData.userno === 'userno_asc'}, {down : sortData.userno === 'userno_desc'}]"
                      @click="sortToggle(sortData.userno)"></button>
            </th>
            <th rowspan="2">사업자등록번호
              <button type="button" :value="sortData.bizno" class="sort"
                      :class="[{up : sortData.bizno === 'bizno_asc'}, {down : sortData.bizno === 'bizno_desc'}]"
                      @click="sortToggle(sortData.bizno)"></button>
            </th>
            <th rowspan="2">정산예정일</th>
            <th rowspan="2" class="tk-left tk-top tk-bottom">총 판매금액<br>(판매가x수량)<br>ⓐ</th>
            <th rowspan="2" class="tk-left tk-top tk-bottom">총 공급단가<br>(공급단가x수량)<br>ⓑ</th>
            <th rowspan="2" class="tk-top tk-bottom">총 부가세<br>(공급단가)<br>(부가세x수량)<br>ⓒ</th>
            <th colspan="2" class="tk-left tk-top">총 기본할인프로모션</th>
            <th colspan="2" class="tk-top">상품쿠폰</th>
            <th colspan="2" class="tk-top">장바구니쿠폰</th>
            <th rowspan="2" class="tk-left tk-top tk-bottom">기본배송비<br>ⓗ</th>
            <th rowspan="2" class="tk-top tk-bottom">추가배송비<br>ⓘ</th>
            <th rowspan="2" class="tk-left tk-top tk-bottom">정산합계<br>(ⓐ-ⓑ-ⓒ)+ⓔ+ⓕ+ⓖ+ⓗ+ⓘ</th>
            <th rowspan="2" class="tk-left">상태
              <button type="button" :value="sortData.calcstatus" class="sort"
                      :class="[{up : sortData.calcstatus === 'calcstatus_asc'}, {down : sortData.calcstatus === 'calcstatus_desc'}]"
                      @click="sortToggle(sortData.calcstatus)"></button>
            </th>
          </tr>
          <tr>
            <th class="bg-yellow tk-left tk-bottom">전체금액</th>
            <th class="bg-yellow tk-bottom">분담금<br>ⓔ</th>
            <th class="bg-yellow tk-bottom">전체금액</th>
            <th class="bg-yellow tk-bottom">분담금<br>ⓕ</th>
            <th class="bg-yellow tk-bottom">전체금액</th>
            <th class="bg-yellow tk-bottom">분담금<br>ⓖ</th>
          </tr>
          </thead>
          <tbody v-if="calcList.length > 0">
            <tr v-for="(row, index) in calcList" v-bind:key="index">
              <td><input type="checkbox" v-model="row.IsChecked" /></td>
              <td>{{ loopNumberForPaging(index) }}</td>
              <td>{{ row.calcmonth}}</td>
              <td><a class="link" v-if="isRead" @click="showDtlPopup(row)">{{ row.name }}</a></td>
              <td>{{ row.userno }}</td>
              <td>{{ row.bizno }}</td>
              <td>{{ row.calcexpday }}</td>
              <td class="right">{{ row.saleamt }}</td>
              <td class="right">{{ row.supplyamt }}</td>
              <td class="right">{{ row.supplyvatamt }}</td>
              <td class="right">{{ row.promoamt }}</td>
              <td class="right">{{ row.promoshareamt }}</td>
              <td class="right">{{ row.cpnamt }}</td>
              <td class="right">{{ row.cpnshareamt }}</td>
              <td class="right">{{ row.basketcpnamt }}</td>
              <td class="right">{{ row.basketcpnshareamt }}</td>
              <td class="right">{{ row.ptndelivamt }}</td>
              <td class="right"><a class="link" v-if="isRead" @click="showCsPopup(row)">{{ row.addrpaytotprice }}</a></td>
              <td class="right">{{ row.calcamt }}</td>
              <td>{{ row.calcstatusname }}<br>
                <button type="button" class="btn blue-line" v-if="isWrite && row.calcstatus === 'CAS002'" @click="reCalc(row)">재마감</button>
              </td>
            </tr>
          </tbody>
          <tbody v-else>
            <tr>
              <td colspan="19">조회 결과가 존재하지 않습니다.</td>
            </tr>
          </tbody>
        </table>
      </div>
      <div class="bottom-group">
        <common-page-navigator v-show="isRead" v-bind:pagingData="pagingData" v-on:setPagingData="setPagingData"></common-page-navigator>
      </div>
    </div>
    <AdjustCsResultPopup v-if="csShow" v-bind:csData="csData" v-on:closeCsPopup="closeCsPopup"></AdjustCsResultPopup>
    <AdjustEstimateDetail v-if="dtlShow" v-bind:dtlData="dtlData" v-on:closeDtlPopup="closeDtlPopup"></AdjustEstimateDetail>
    <AdjustCompareResult v-if="compareShow" v-bind:compareData="compareData" v-on:closeComparePopup="closeComparePopup"></AdjustCompareResult>

  </div>
  <!-- /컨텐츠 영역 -->
</template>

<script>
import CommonNavigator from "@views.admin/common/CommonNavigator";
import CommonPageNavigator from "@views.admin/common/CommonPageNavigator";
import CommonDatePicker from "@views.admin/common/CommonDatePicker";
import AdjustCsResultPopup from "@views.admin/adjust/estimate/AdjustCsResult";
import AdjustEstimateDetail from "@views.admin/adjust/estimate/AdjustEstimateDetail";
import AdjustCompareResult from "@views.admin/adjust/estimate/AdjustCompareResult";

export default {
  name: "admin.adjust.estimate",
  components: {
    AdjustCompareResult,
    AdjustEstimateDetail, AdjustCsResultPopup, CommonNavigator, CommonPageNavigator, CommonDatePicker},
  data() {
    return {
      searchData: {
        startdate: '',
        enddate: '',
        calcstatus : '',
        calctype : 'ESTIMATE',
        skey: 'name',
        sword: '',
        psort: ''
      },
      commonCode: {
        calcStatus : []    //마감상태
      },
      pagingData: {
        pageCount: 20,
        page: 1,
        listCount: 0
      },
      sortData: {
        month: 'month_desc',
        name: 'name_desc',
        userno: 'userno_desc',
        bizno: 'bizno_desc',
        calcstatus: 'calcstatus_desc'
      },
      calcList : [],
      totalCnt : 0,
      formatType : 'month',
      allChecked: false,
      isRead : false,
      isWrite : false,
      csShow : false,
      csData : {},
      dtlShow : false,
      dtlData : {},
      compareShow : false,
      compareData : {},
    }
  },
  mounted() {
    this.$http.post('/admin/common/pageAuth/check', {url : this.$options.name, isloading: false}).then(result => {
      this.isRead = (result.data.isread === 'T');
      this.isWrite = (result.data.iswrite === 'T');

      if (this.isRead) {
        this.initData();
        let params = { cmclass : 'CALCSTATUS' };
        this.$http.post('/common/code/list', params).then(result => {
          if (result.statusCode === 200) {
            this.commonCode.calcStatus = result.data.list;
          }
        }).catch(error => {
          this.$util.debug(error);
        });
      }
    });
  },
  methods : {
    initData(){
      this.searchData = this.$options.data().searchData;
      let startDate = this.$util.getAddMonth(this.$util.getDate(""), -1, "-");
      let endDate = this.$util.getAddMonth(this.$util.getDate(""), -1, "-");

      this.searchData.startdate = startDate.substring(0, 7);
      this.searchData.enddate = endDate.substring(0, 7);
    },
    onSearch(page) {
      let param = this.searchData;
      param.pageCount = this.pagingData.pageCount;
      param.page = (typeof page === 'undefined') ? this.pagingData.page : 1;
      param.listCount = this.pagingData.listCount;

      this.$http.post('/admin/adjust/estimate/list', param).then(result => {
        this.pagingData.listCount = result.data.listcount;
        this.calcList = result.data.list;
        this.totalCnt = result.data.listcount;
        this.$util.dataSetSearchParam(this);
      }).catch(error => {
        this.$util.debug(error);
      })
    },
    reCalc(obj){
      let calcMonth = obj.calcmonth.replace(/-/g, '');
      let param = {
        userno : obj.userno,
        calcmonth : calcMonth,
        calcidx : obj.calcidx
      }

      if(confirm('재 마감처리 하시겠습니까?')){
        this.$http.post('/admin/adjust/recalculate', param).then(result => {
          if(result.data.count > 0){
            alert('재 마감처리가 완료되었습니다.');
            this.onSearch(1);
          }
        }).catch(error => {
          this.$util.debug(error);
        });
      }
    },
    calcStateUpdate(state){
      let idx = [];
      this.calcList.forEach(function (row) {
        if(row.IsChecked && row.calcstatus === 'CAS001'){
          idx.push(row.calcidx);
        }
      });

      if(idx.length <= 0){
        alert('마감 처리할 데이터가 존재하지 않습니다.');
        return;
      }

      if(confirm("체크된 내역 중 이미 마감된 내용은 제외 처리 됩니다.\n마감 하시겠습니까?")) {
        let param = {
          calcstatus : state,
          calcidx : idx
        }

        this.$http.post('/admin/adjust/state/update', param).then(result => {
          if(result.data.count > 0){
            alert('마감처리가 완료되었습니다.');
            this.onSearch(1);
          }
        }).catch(error => {
          this.$util.debug(error);
        })
      }

    },
    excelDown(){
      let idx = [];
      this.calcList.forEach(function (row) {
        if(row.IsChecked && row.calcstatus === 'CAS002'){
          idx.push(row.calcidx);
        }
      });

      if(idx.length <= 0){
        alert('처리할 데이터가 존재하지 않습니다.');
        return;
      }

      if(confirm("엑셀 다운로드 시 최종 마감처리가 완료되며\n데이터수정이 불가능합니다.\n그래도 진행 하시겠습니까?")) {
        let param = {
          calcstatus : 'CAS003',
          calcidx : idx
        }
        let config = { responseType: 'arraybuffer' };
        this.$http.post('/admin/adjust/calc/excel', param, config).then(result => {
          this.$util.debug(result);
          this.onSearch(1);
        }).catch(error => {
          this.$util.debug(error)
        });

      }

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
    checkedAll: function (val) {
      this.calcList.forEach(function (row) {
        row.IsChecked = val;
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
    showCsPopup(obj){
      this.csData = obj;
      this.csShow = true;
    },
    closeCsPopup: function () {
      this.csData = {};
      this.csShow = false;
    },
    showDtlPopup(obj){
      this.dtlData = obj;
      this.dtlShow = true;
    },
    closeDtlPopup: function () {
      this.dtlData = {};
      this.dtlShow = false;
    },
    showComparePopup(){
      this.compareShow = true;
    },
    closeComparePopup: function () {
      this.compareShow = false;
    },

  }
}
</script>

<style scoped />