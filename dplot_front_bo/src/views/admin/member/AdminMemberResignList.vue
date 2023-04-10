<template>
  <!-- 컨텐츠 영역 -->
  <div class="content m-leftmenu">
    <common-navigator></common-navigator>
    <div class="inner">
      <div class="clearfix">
        <div class="bar-title fl">탈퇴회원</div>
      </div>
      <div class="boxing search-area">
        <dl>
          <dt>직접검색</dt>
          <dd>
            <select v-model="searchData.skey">
              <option value="">전체</option>
              <option value="id">아이디</option>
              <option value="withdrawreason">탈퇴사유</option>
            </select>
            <input type="text" v-model="searchData.sword" @keyup.enter="onSearch(1)"/>
          </dd>
        </dl>
        <dl>
          <dt>탈퇴일자</dt>
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
          <dt>탈퇴유형</dt>
          <dd>
            <div>
              <input type="checkbox" id="chkAllMember" v-model="searchData.iswithdraw" true-value="T" false-value="F" @change="checkAllWithdrawType">
              <label for="chkAllMember">전체</label>
            </div>
            <div v-for="item in commonCode.WITHDRAWTYPE" :key="item.cmcode">
              <input type="checkbox" :id="'withdrawtype_'+item.cmcode" v-model="searchData.withdrawtypeArr" :true-value="[]" :value="item.cmcode">
              <label :for="'withdrawtype_'+item.cmcode">{{ item.codename }}</label>
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
          <span class="total">전체 <strong>{{ totalCnt }}</strong>명</span>
          <span>금일탈퇴 <strong>{{ todayCnt }}</strong>명</span>
        </div>
        <div class="btn-group fr">
          <select v-model="pagingData.pageCount" v-show="isRead">
            <option value="20">20개씩 보기</option>
            <option value="50">50개씩 보기</option>
            <option value="100">100개씩 보기</option>
          </select>
        </div>
      </div>
      <table cellpadding="0" cellspacing="0" class="data-tb align-c">
        <caption>휴면회원목록</caption>
        <colgroup>
          <col width="15%" /><!-- 탈퇴일시 -->
          <col width="15%" /><!-- 아이디 -->
          <col width="15%" /><!-- 탈퇴유형 -->
          <col width="" /><!-- 탈퇴사유 -->
          <col width="10%" /><!-- 경과일 -->
          <col width="10%" /><!-- 복구처리 -->
        </colgroup>
        <thead>
        <tr>
          <th>탈퇴일시
            <button type="button" :value="sortData.withdraw" class="sort" v-if="isRead"
                    :class="[{up : sortData.withdraw === 'withdraw_asc'}, {down : sortData.withdraw === 'withdraw_desc'}]"
                    @click="sortToggle(sortData.withdraw)"></button>
          </th>
          <th>아이디
            <button type="button" :value="sortData.id" class="sort" v-if="isRead"
                    :class="[{up : sortData.id === 'id_asc'}, {down : sortData.id === 'id_desc'}]"
                    @click="sortToggle(sortData.id)"></button>
          </th>
          <th>탈퇴유형</th>
          <th>탈퇴사유</th>
          <th>경과일</th>
          <th>복구처리</th>
        </tr>
        </thead>
        <tbody v-if="listData.length > 0">
        <tr v-for="row in listData" v-bind:key="row.no">
          <td>{{ row.withdrawdate }}</td>
          <td>{{ row.userid }}</td>
          <td>{{ row.withdrawtypename }}</td>
          <td class="left">{{ row.withdrawreason }}</td>
          <td>{{ row.afterdate }}</td>
          <td><button v-if="row.afterdate < 30" type="button" class="btn blue-line" @click="goResignRestore(row)">복구</button></td>
        </tr>
        </tbody>
        <tbody v-else>
        <tr>
          <td colspan="6">조회 결과가 존재하지 않습니다.</td>
        </tr>
        </tbody>
      </table>
      <div class="bottom-group">
        <common-page-navigator v-show="isRead" v-bind:pagingData="pagingData" v-on:setPagingData="setPagingData"></common-page-navigator>
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
  name: "admin.member.resign.list",
  components: {CommonNavigator, CommonPageNavigator, CommonDatePicker},
  data() {
    return {
      searchData: {
        startdate: '',
        enddate: '',
        period: '3',
        skey : '',
        sword : '',
        iswithdraw: 'T',
        withdrawtypeArr: []
      },
      pagingData: {
        pageCount: 20,
        page: 1,
        listCount: 0
      },
      sortData: {
        withdraw: 'withdraw_desc',
        id: 'id_desc'
      },
      commonCode: {
        WITHDRAWTYPE: []
      },
      todayCnt: 0,
      totalCnt:0,
      listData: [],
      isRead : false,
      isWrite : false
    }
  },
  mounted() {
    this.$http.post('/admin/common/pageAuth/check', {url : this.$options.name, isloading: false}).then(result => {
      this.isRead = (result.data.isread === 'T');
      this.isWrite = (result.data.iswrite === 'T');

      if(this.isRead) {
        this.$http.post('/common/code/list', {cmclass : 'WITHDRAWTYPE', isloading: false}).then(result =>{
          this.commonCode.WITHDRAWTYPE = result.data.list;
          this.initParam();
          this.checkAllWithdrawType();

          if(typeof this.$route.params.type !== 'undefined' && this.$route.params.type === 'LINK'){
            let linkParam = this.$route.params;
            this.searchData.period = linkParam.period;
            this.searchData.startdate = linkParam.startdate;
            this.searchData.enddate = linkParam.enddate;
          }

          this.onSearch();
        }).catch(error => {
          this.$util.debug(error);
        })

      }
    }).catch(error => {
      this.$util.debug(error);
    })
  },
  methods: {
    onSearch(page) {
      let param = this.searchData;
      param.pageCount = this.pagingData.pageCount;
      param.page = (typeof page === 'undefined') ? this.pagingData.page : 1;
      param.listCount = this.pagingData.listCount;

      this.$http.post("/admin/member/resign/list", param).then(result => {
        this.pagingData.listCount = result.data.listcount;
        this.totalCnt = result.data.listcount;
        this.todayCnt = (result.data.today !== null) ? result.data.today : 0;
        this.listData = result.data.list;
        this.$util.dataSetSearchParam(this);
      }).catch(error => {
        this.$util.debug(error);
      })
    },
    initParam() {
      this.searchData = this.$options.data().searchData;
      this.searchData.startdate = this.$util.getAddMonth(this.$util.getDate(''), -3, '-');
      this.searchData.enddate = this.$util.getDate('-');
    
      this.checkAllWithdrawType();
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
    checkedAll(val) {
      this.listData.forEach(function (row) {
        row.IsChecked = val;
      });
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
    checkAllWithdrawType() {
      let isAllCheck = this.searchData.iswithdraw;
      this.searchData.withdrawtypeArr = [];
      if (isAllCheck === 'T') {
        for(let type of this.commonCode.WITHDRAWTYPE){
          this.searchData.withdrawtypeArr.push(type.cmcode);
        }
      }
    },
    goResignRestore(row) {
      let msg = '탈퇴회원을 복구 처리 하시겠습니까?';

      if(confirm(msg)) {
        let param = {
          userno : row.userno
        }

        this.$http.post("/admin/member/resign/restore", param)
            .then(result => {
              this.$util.debug(result);
              this.onSearch();
            }).catch(error => {
          this.$util.debug(error);
        });
      }
    }
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
    },
    'searchData.withdrawtypeArr': function(value) {
      if (value.length < this.commonCode.WITHDRAWTYPE.length) {
        this.searchData.iswithdraw = 'F';
      } else {
        this.searchData.iswithdraw = 'T';
      }
    },
  }
}
</script>

<style scoped>

</style>