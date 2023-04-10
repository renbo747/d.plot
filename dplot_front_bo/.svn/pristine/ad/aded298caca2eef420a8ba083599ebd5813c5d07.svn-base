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
        <dl v-if="!isPartner">
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
          <button type="button" class="btn blue-line ml3" v-if="isRead && !isPartner" @click="calcPayment">지급완료</button>
          <select v-model="pagingData.pageCount" v-show="isRead">
            <option value="20">20개씩 보기</option>
            <option value="50">50개씩 보기</option>
            <option value="100">100개씩 보기</option>
          </select>
        </div>
      </div>
      <div class="scroll-x">
        <table cellpadding="0" cellspacing="0" class="data-tb align-c" :class="[{ 'adjust-width' : !isPartner}]" style="min-width: 2300px;">
          <caption>지급내역 목록</caption>
          <colgroup>
            <col v-if="!isPartner" width="30px" /><!-- checkbox -->
            <col width="2%" /><!-- No -->
            <col width="4%" /><!-- 년/월 -->
            <col v-if="!isPartner" width="" /><!-- 파트너사명 -->
            <col v-if="!isPartner" width="5%" /><!-- 파트너사코드 -->
            <col v-if="!isPartner" width="5%" /><!-- 사업자등록번호 -->
            <col width="5%" /><!-- 정산예정일 -->
            <col width="6%" /><!-- 총 판매금액 (판매가x수량) ⓐ -->
            <col width="6%" /><!-- 총 공급단가 (공급단가x수량) ⓑ -->
            <col width="5.5%" /><!-- 총 부가세(공급단가) (부가세x수량) ⓒ -->
            <col width="4.8%" /><!-- 전체금액 -->
            <col width="4.8%" /><!-- 분담금 ⓒ -->
            <col width="4.8%" /><!-- 전체금액 ⓓ -->
            <col width="4.8%" /><!-- 분담금 ⓔ -->
            <col width="4.8%" /><!-- 전체금액 ⓓ -->
            <col width="4.8%" /><!-- 분담금 ⓔ -->
            <col width="4.6%" /><!-- 배송비 ⓕ -->
            <col width="4.6%" /><!-- 추가결제금 ⓕ -->
            <col width="6%" /><!-- 정산합계 ⓐ+ⓑ+ⓒ+ⓓ+ⓔ+ⓕ -->
            <col width="5%" /><!-- 마감완료일 -->
            <col width="5%" /><!-- 지급완료일 -->
            <col width="5%" /><!-- 파트너사 확인일 -->
          </colgroup>
          <thead>
          <tr>
            <th rowspan="2" v-if="!isPartner"><input type="checkbox" v-model="allChecked" @change="checkedAll($event.target.checked)" /></th>
            <th rowspan="2">No</th>
            <th rowspan="2">년/월
              <button type="button" :value="sortData.month" class="sort"
                      :class="[{up : sortData.month === 'month_asc'}, {down : sortData.month === 'month_desc'}]"
                      @click="sortToggle(sortData.month)"></button>
            </th>
            <th rowspan="2" v-if="!isPartner">파트너사 명
              <button type="button" :value="sortData.name" class="sort"
                      :class="[{up : sortData.name === 'name_asc'}, {down : sortData.name === 'name_desc'}]"
                      @click="sortToggle(sortData.name)"></button>
            </th>
            <th rowspan="2" v-if="!isPartner">파트너사 코드
              <button type="button" :value="sortData.userno" class="sort"
                      :class="[{up : sortData.userno === 'userno_asc'}, {down : sortData.userno === 'userno_desc'}]"
                      @click="sortToggle(sortData.userno)"></button>
            </th>
            <th rowspan="2" v-if="!isPartner">사업자등록번호
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
            <th rowspan="2" class="tk-left tk-top tk-bottom">배송비<br>ⓗ</th>
            <th rowspan="2" class="tk-top tk-bottom">추가결제금<br>ⓘ</th>
            <th rowspan="2" class="tk-left tk-top tk-bottom">정산합계<br>(ⓐ-ⓑ-ⓒ)+ⓔ+ⓕ+ⓖ+ⓗ+ⓘ</th>
            <th rowspan="2" class="tk-left">마감완료일<button type="button" class="sort down"></button></th>
            <th rowspan="2">지급완료일<button type="button" class="sort down"></button></th>
            <th rowspan="2">파트너사 확인일<button type="button" class="sort down"></button></th>
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
            <td v-if="!isPartner"><input type="checkbox" v-model="row.IsChecked" /></td>
            <td>{{ loopNumberForPaging(index) }}</td>
            <td>{{ row.calcmonth}}</td>
            <td v-if="!isPartner"><a class="link" v-if="isRead" @click="showDtlPopup(row)">{{ row.name }}</a></td>
            <td v-if="!isPartner">{{ row.userno }}</td>
            <td v-if="!isPartner">{{ row.bizno }}</td>
            <td>{{ row.calcexpday }}</td>
            <td class="right">{{ row.saleamt }}</td>
            <td class="right">{{ row.supplyamt }}</td>
            <td class="right">{{ row.supplyvatamt }}</td>
            <td class="right">{{ row.promoamt }}</td>
            <td class="right">{{ row.promoshareamt }}</td>
            <td class="right">{{ row.basketcpnamt }}</td>
            <td class="right">{{ row.basketcpnshareamt }}</td>
            <td class="right">{{ row.cpnamt }}</td>
            <td class="right">{{ row.cpnshareamt }}</td>
            <td class="right">{{ row.ptndelivamt }}</td>
            <td class="right"><a class="link" v-if="isRead" @click="showCsPopup(row)">{{ row.addrpaytotprice }}</a></td>
            <td class="right" >
              <span v-if="isPartner"><a class="link" v-if="isRead" @click="showDtlPopup(row)">{{ row.calcamt }}</a></span>
              <span v-if="!isPartner">{{ row.calcamt }}</span>
            </td>
            <td>{{ row.enddate }}<br>{{ row.endtime }}</td>
            <td>{{ row.paydate }}<br>{{ row.paytime }}</td>
            <td>{{ row.partcfmdate }}<br>{{ row.partcfmtime }}</td>
          </tr>
          </tbody>
          <tbody v-else>
          <tr>
            <td colspan="19" v-if="!isPartner">조회 결과가 존재하지 않습니다.</td>
            <td colspan="15" v-if="isPartner">조회 결과가 존재하지 않습니다.</td>
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
  </div>
  <!-- /컨텐츠 영역 -->
</template>

<script>
import CommonNavigator from "@views.admin/common/CommonNavigator";
import CommonPageNavigator from "@views.admin/common/CommonPageNavigator";
import CommonDatePicker from "@views.admin/common/CommonDatePicker";
import AdjustEstimateDetail from "@views.admin/adjust/estimate/AdjustEstimateDetail";
import AdjustCsResultPopup from "@views.admin/adjust/estimate/AdjustCsResult";

export default {
  name: "admin.adjust.decision",
  components: {AdjustEstimateDetail, AdjustCsResultPopup, CommonNavigator, CommonPageNavigator, CommonDatePicker},
  data() {
    return {
      isPartner: false,
      user: {},
      searchData: {
        startdate: '',
        enddate: '',
        calcstatus : 'CAS003',
        calctype : 'DICISION',
        skey: 'name',
        sword: '',
        psort: ''
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
        calcstatus: 'enddate_desc'
      },
      calcList : [],
      totalCnt : 0,
      formatType : 'month',
      allChecked: false,
      isRead : true,
      isWrite : true,
      csShow : false,
      csData : {},
      dtlShow : false,
      dtlData : {},
    }
  },
  mounted() {
    this.isPartner = this.$util.isAuthorized(this.$store.getters['CONSTANTS'].PARTNER_USER);
    this.user = this.$util.getUser(this.$store.getters['CONSTANTS'].MANAGER_SESSION);

    this.$http.post('/admin/common/pageAuth/check', {url : this.$options.name, isloading: false}).then(result => {
      this.isRead = (result.data.isread === 'T');
      this.isWrite = (result.data.iswrite === 'T');

      if (this.isRead) {
        this.initData();

        if(typeof this.$route.params.type !== 'undefined' && this.$route.params.type === 'LINK'){
          this.searchData.startdate =this.$route.params.calcmonth;
          this.searchData.enddate =this.$route.params.calcmonth;
        }

        this.onSearch(1);
      }
    });
  },
  methods : {
    initData(){
      this.searchData = this.$options.data().searchData;
      let startDate = this.$util.getDate("-");
      let endDate = this.$util.getDate("-");

      this.searchData.startdate = startDate.substring(0, 7);
      this.searchData.enddate = endDate.substring(0, 7);
    },
    onSearch(page) {
      if(this.isPartner){
        this.searchData.userno = this.user.no;
      }
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
    calcPayment() {
      let idx = [];
      this.calcList.forEach(function (row) {
        if(row.IsChecked && row.ispayment === 'F'){
          idx.push(row.calcidx);
        }
      });

      let param = {
        calcidx : idx
      }

      if(idx.length <= 0){
        alert('지급완료 처리할 데이터가 존재하지 않습니다.');
        return;
      }

      if(confirm("체크된 내역 중 이미 지급된 내용은 제외 처리 됩니다.\n지급완료 처리 하시겠습니까?")) {
        this.$http.post('/admin/adjust/payment/complete', param).then(result => {
          if(result.data.count > 0){
            alert('지급완료 처리 었습니다.');
            this.onSearch(1);
          }
        }).catch(error => {
          this.$util.debug(error);
        })
      }

    },
    setPagingData(obj) {
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
    checkedAll(val) {
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
    closeCsPopup() {
      this.csData = {};
      this.csShow = false;
    },
    showDtlPopup(obj){
      this.dtlData = obj;
      this.dtlShow = true;
    },
    closeDtlPopup() {
      this.dtlData = {};
      this.dtlShow = false;
    },
  }
}
</script>

<style scoped>
.adjust-width{
  width : 2200px
}
</style>