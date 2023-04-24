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
          <dt>분류선택</dt>
          <dd>
            <div class="radio_wrap wide">
              <input type="radio" name="group00" id="group01" v-model="searchData.type" value="AGE"><label for="group01">연령별</label>
              <input type="radio" name="group00" id="group02" v-model="searchData.type" value="GENDER"><label for="group02">성별</label>
            </div>
          </dd>
        </dl>
        <dl>
          <dt>회원유형</dt>
          <dd>
            <div class="check-wrap">
              <input type="checkbox" id="chkAllMember" v-model="searchData.isallmember" true-value="T" false-value="F" @change="checkAllMembertype">
              <label for="chkAllMember">전체</label>
            </div>
            <div class="check-wrap" v-for="item in commonCode.dadamembertype" :key="item.cmcode">
              <input type="checkbox" :id="'dadamembertype_'+item.cmcode" v-model="searchData.mumembertypeArr" :true-value="[]" :value="item.cmcode">
              <label :for="'dadamembertype_'+item.cmcode">{{ item.codename }}</label>
            </div>
          </dd>
        </dl>
        <dl>
          <dt>회원등급</dt>
          <dd>
            <div class="check-wrap">
              <input type="checkbox" id="chkAllMemlv" v-model="searchData.isallmemlv" true-value="T" false-value="F" @change="checkAllMemlvtype">
              <label for="chkAllMemlv">전체</label>
            </div>
            <div class="check-wrap" v-for="item in commonCode.memlvtype" :key="item.cmcode">
              <input type="checkbox" :id="'mumemlvtype_'+item.cmcode" v-model="searchData.mumemlvtypeArr" :true-value="[]" :value="item.cmcode">
              <label :for="'mumemlvtype_'+item.cmcode">{{ item.codename }}</label>
            </div>
          </dd>
        </dl>
        <dl>
          <dt>주문경로</dt>
          <dd>
            <div class="check-wrap">
              <input type="checkbox" id="chkAllAppch" v-model="searchData.isallchannel" true-value="T" false-value="F" @change="checkAllAppchtype">
              <label for="chkAllAppch">전체</label>
            </div>
            <div class="check-wrap" v-for="item in commonCode.muappchtype" :key="item.cmcode">
              <input type="checkbox" :id="'muappchtype_'+item.cmcode" v-model="searchData.muappchtypeArr" :true-value="[]" :value="item.cmcode">
              <label :for="'muappchtype_'+item.cmcode">{{ item.codename }}</label>
            </div>
          </dd>
        </dl>
        <dl>
          <dt>결제수단</dt>
          <dd>
            <div class="check-wrap">
              <input type="checkbox" id="chkAllPayway" v-model="searchData.isallpayway" true-value="T" false-value="F" @change="checkAllPaywaytype">
              <label for="chkAllPayway">전체</label>
            </div>
            <div class="check-wrap" v-for="item in commonCode.paywaytype" :key="item.cmcode">
              <input type="checkbox" :id="'paywaytype_'+item.cmcode" v-model="searchData.paywaytypeArr" :true-value="[]" :value="item.cmcode">
              <label :for="'paywaytype_'+item.cmcode">{{ item.codename }}</label>
            </div>
          </dd>
          <dd style="width: 130px;">
            <input type="checkbox" id="chk_searchAll" v-model="searchData.isTotCheck" true-value="T" false-value="F" @change="checkTotAll"><label for="chk_searchAll" class="txt-blue">조건 전체체크</label>
          </dd>
        </dl>
      </div>
      <div class="btn-group" v-show="isRead">
        <button type="button" class="btn big blue" @click="searchList">검색</button>
        <button type="button" class="btn big gray" @click="initSearchData">초기화</button>
      </div>
      <div class="caption-group mt10 clearfix">
        <div class="total-group fl">
          <span class="total">전체 <strong>{{ totalCnt }}</strong>건</span>
        </div>
        <div class="btn-group fr">
          <button type="button" class="btn green-line" v-show="isRead" @click="goExcelDownload"><i class="icon-excel"></i>엑셀다운로드</button>
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
          <col width="" /><!-- 분류 -->
          <col width="7%" /><!-- 매출발생명수 -->
          <col width="7%" /><!-- 총 주문건수 -->
          <col width="7%" /><!-- 순 주문건수 -->
          <col width="9%" /><!-- 순 주문금액 -->
          <col width="7%" /><!-- 객단가 -->
          <col width="5%" /><!-- 취소건수 -->
          <col width="5%" /><!-- 교환건수 -->
          <col width="5%" /><!-- 환불건수 -->
          <col width="5%" /><!-- 반품건수 -->
          <col width="7%" /><!-- 신규회원수 -->
          <col width="7%" /><!-- SMS수신동의수 -->
          <col width="7%" /><!-- 탈퇴회원수 -->
          <col width="7%" /><!-- 휴면회원전환수 -->
        </colgroup>
        <thead>
        <tr>
          <th rowspan="2">분류</th>
          <th rowspan="2">주문자수
            <button type="button" :value="sortData.humancnt" class="sort"
                    :class="[{up : sortData.humancnt === 'humancnt_asc'}, {down : sortData.humancnt === 'humancnt_desc'}]"
                    @click="sortToggle(sortData.humancnt)"></button>
          </th>
          <th rowspan="2">총 주문건수
            <button type="button" :value="sortData.ordercnt" class="sort"
                    :class="[{up : sortData.ordercnt === 'ordercnt_asc'}, {down : sortData.ordercnt === 'ordercnt_desc'}]"
                    @click="sortToggle(sortData.ordercnt)"></button>
          </th>
          <th rowspan="2">순 주문건수
            <button type="button" :value="sortData.pordercnt" class="sort"
                    :class="[{up : sortData.pordercnt === 'pordercnt_asc'}, {down : sortData.pordercnt === 'pordercnt_desc'}]"
                    @click="sortToggle(sortData.pordercnt)"></button>
          </th>
          <th rowspan="2">
            <i class="icon-th-q" @click="toggleData.price = !toggleData.price"></i>순 주문금액
            <button type="button" :value="sortData.price" class="sort"
                    :class="[{up : sortData.price === 'price_asc'}, {down : sortData.price === 'price_desc'}]"
                    @click="sortToggle(sortData.price)"></button>
            <div class="th-cell" :class="[{dpb : toggleData.price}, {dpn : !toggleData.price}]" style="width: 240px;"><!-- dpb:보임 / dpn:숨김 -->
              판매가(프로모션할인가) – 쿠폰할인
            </div>
          </th>
          <th rowspan="2">
            <i class="icon-th-q" @click="toggleData.unitamt = !toggleData.unitamt"></i>객단가
            <button type="button" :value="sortData.unitamt" class="sort"
                    :class="[{up : sortData.unitamt === 'unitamt_asc'}, {down : sortData.unitamt === 'unitamt_desc'}]"
                    @click="sortToggle(sortData.unitamt)"></button>
            <div class="th-cell" :class="[{dpb : toggleData.unitamt}, {dpn : !toggleData.unitamt}]" style="width: 240px;"><!-- dpb:보임 / dpn:숨김 -->
              순 주문금액/순 주문건수
            </div>
          </th>
          <th rowspan="2">취소건수
            <button type="button" :value="sortData.cnccnt" class="sort"
                    :class="[{up : sortData.cnccnt === 'cnccnt_asc'}, {down : sortData.cnccnt === 'cnccnt_desc'}]"
                    @click="sortToggle(sortData.cnccnt)"></button>
          </th>
          <th rowspan="2">교환건수
            <button type="button" :value="sortData.exccnt" class="sort"
                    :class="[{up : sortData.exccnt === 'exccnt_asc'}, {down : sortData.exccnt === 'exccnt_desc'}]"
                    @click="sortToggle(sortData.exccnt)"></button>
          </th>
          <th rowspan="2">환불건수
            <button type="button" :value="sortData.ramtcnt" class="sort"
                    :class="[{up : sortData.ramtcnt === 'ramtcnt_asc'}, {down : sortData.ramtcnt === 'ramtcnt_desc'}]"
                    @click="sortToggle(sortData.ramtcnt)"></button>
          </th>
          <th rowspan="2">반품건수
            <button type="button" :value="sortData.rtncnt" class="sort"
                    :class="[{up : sortData.rtncnt === 'rtncnt_asc'}, {down : sortData.rtncnt === 'rtncnt_desc'}]"
                    @click="sortToggle(sortData.rtncnt)"></button>
          </th>
          <th colspan="4">회원</th>
        </tr>
        <tr>
          <th class="bg-yellow">신규회원수
            <button type="button" :value="sortData.regcnt" class="sort"
                    :class="[{up : sortData.regcnt === 'regcnt_asc'}, {down : sortData.regcnt === 'regcnt_desc'}]"
                    @click="sortToggle(sortData.regcnt)"></button>
          </th>
          <th class="bg-yellow">SMS수신동의수
            <button type="button" :value="sortData.smscnt" class="sort"
                    :class="[{up : sortData.smscnt === 'smscnt_asc'}, {down : sortData.smscnt === 'smscnt_desc'}]"
                    @click="sortToggle(sortData.smscnt)"></button>
          </th>
          <th class="bg-yellow">탈퇴회원수
            <button type="button" :value="sortData.rescnt" class="sort"
                    :class="[{up : sortData.rescnt === 'rescnt_asc'}, {down : sortData.rescnt === 'rescnt_desc'}]"
                    @click="sortToggle(sortData.rescnt)"></button>
          </th>
          <th class="bg-yellow">휴면회원전환수
            <button type="button" :value="sortData.dorcnt" class="sort"
                    :class="[{up : sortData.dorcnt === 'dorcnt_asc'}, {down : sortData.dorcnt === 'dorcnt_desc'}]"
                    @click="sortToggle(sortData.dorcnt)"></button>
          </th>
        </tr>
        </thead>
        <tbody v-if="listData.length > 0">
        <tr class="bg blue">
          <td>합계</td>
          <td>{{ humancnt }}</td>
          <td>{{ ordercnt }}</td>
          <td>{{ pordercnt }}</td>
          <td>{{ price }}</td>
          <td>{{ unitamt }}</td>
          <td>{{ cnccnt }}</td>
          <td>{{ exccnt }}</td>
          <td>{{ ramtcnt }}</td>
          <td>{{ rtncnt }}</td>
          <td>{{ regcnt }}</td>
          <td>{{ smscnt }}</td>
          <td>{{ rescnt }}</td>
          <td>{{ dorcnt }}</td>
        </tr>
        <tr v-for="(row, index) in listData" v-bind:key="index">
          <td class="center">{{ row.name }}</td>
          <td>{{ row.humancnt }}</td>
          <td>{{ row.ordercnt }}</td>
          <td>{{ row.pordercnt }}</td>
          <td>{{ row.price }}</td>
          <td>{{ row.unitamt }}</td>
          <td>{{ row.cnccnt }}</td>
          <td>{{ row.exccnt }}</td>
          <td>{{ row.ramtcnt }}</td>
          <td>{{ row.rtncnt }}</td>
          <td>{{ row.regcnt }}</td>
          <td>{{ row.smscnt }}</td>
          <td>{{ row.rescnt }}</td>
          <td>{{ row.dorcnt }}</td>
        </tr>
        </tbody>
        <tbody v-else>
        <tr>
          <td colspan="14" class="center">조회 결과가 존재하지 않습니다.</td>
        </tr>
        </tbody>
      </table>
      <div class="bottom-group">
        <CommonPageNavigator v-show="isRead" :pagingData="pagingData" v-on:setPagingData="setPagingData"/>
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
  name: "admin.stats.age",
  components: {
    CommonNavigator,
    CommonPageNavigator,
    CommonDatePicker
  },
  data() {
    return {
      isRead: false,
      isWrite: false,
      searchData: {
        startdate: '',          // 조회시작날짜
        enddate: '',            // 조회종료날짜
        period: '3',             // 조회기간
        type : 'AGE',
        standard : 'PAY',
        amtstandard : 'NORMAL',
        isallchannel: 'T',      // 다중적용채널전체여부
        muappchtypeArr: [],     // 다중적용채널Array
        isallmember: 'T',       // 다중대상회원유형전체여부
        mumembertypeArr: [],    // 다중대상회원유형Array
        isallmemlv: 'T',        // 다중대상회원등급전체여부
        mumemlvtypeArr: [],     // 다중대상회원등급Array
        isallpayway: 'T',        // 결제수단전체여부
        paywaytypeArr: [],      // 결제수단Array
        psort: '',   // 정렬
        isTotCheck : 'T',
      },
      pagingData: {
        pageCount: 20,      // 페이징 옵션(최대수)
        page: 1,            // 현재 페이지
        listCount: 0        // 총 수량
      },
      commonCode: {
        muappchtype: [],    // 다중적용채널
        dadamembertype: [], // 다다픽회원유형
        memlvtype: [],      // 회원등급
        paywaytype: []          // 결제수단
      },
      sortData: {
        humancnt : 'humancnt_desc',
        ordercnt : 'ordercnt_desc',
        pordercnt : 'pordercnt_desc',
        price : 'price_desc',
        unitamt : 'unitamt_desc',
        cnccnt : 'cnccnt_desc',
        exccnt : 'exccnt_desc',
        ramtcnt : 'ramtcnt_desc',
        rtncnt : 'rtncnt_desc',
        regcnt : 'regcnt_desc',
        smscnt : 'smscnt_desc',
        dorcnt : 'dorcnt_desc',
        rescnt : 'rescnt_desc',
      },
      toggleData : {
        unitamt : false,
        price : false,
      },
      totalCnt : 0,
      humancnt : 0,
      ordercnt : 0,
      pordercnt : 0,
      price : 0,
      unitamt : 0,
      cnccnt : 0,
      exccnt : 0,
      ramtcnt : 0,
      rtncnt : 0,
      regcnt : 0,
      smscnt : 0,
      dorcnt : 0,
      rescnt : 0,
      listData: [],
    }
  },
  mounted() {
    let params = { url: this.$options.name, isloading: false };
    this.$http.post('/admin/common/pageAuth/check', params)
        .then(result => {
          this.isRead = (result.data.isread === 'T');
          this.isWrite = (result.data.iswrite === 'T');
          if(this.isRead) {
            // 초기화
            this.initSearchData();
          }
        }).catch(error => {
      this.$util.debug(error);
    });
  },
  methods : {
    initSearchData() {
      this.searchData = this.$options.data().searchData;
      this.searchData.startdate = this.$util.getAddMonth(this.$util.getDate(''), -3, '-');
      this.searchData.enddate = this.$util.getDate('-');

      this.getCommonCodeList();
      this.checkAllAppchtype();
      this.checkAllMembertype();
      this.checkAllMemlvtype();
      this.checkAllPaywaytype();
    },
    searchList() {
      let params = Object.assign(this.searchData, this.pagingData);
      this.$http.post('/admin/stats/age/list', params).then(result =>{

        this.totalCnt = result.data.total;
        this.pagingData.listCount = result.data.total;
        this.listData = result.data.list;

        this.humancnt = result.data.humancnt;
        this.ordercnt = result.data.ordercnt;
        this.pordercnt = result.data.pordercnt;
        this.price = result.data.price;
        this.unitamt = result.data.unitamt;
        this.cnccnt = result.data.cnccnt;
        this.exccnt = result.data.exccnt;
        this.ramtcnt = result.data.ramtcnt;
        this.rtncnt = result.data.rtncnt;
        this.regcnt = result.data.regcnt;
        this.smscnt = result.data.smscnt;
        this.dorcnt = result.data.dorcnt;
        this.rescnt = result.data.rescnt;

        this.$util.dataSetSearchParam(this);

      }).catch(error =>{
        this.$util.debug(error);
      })

    },
    // 공통코드 목록 조회
    getCommonCodeList() {
      let cmclassArr = ['MUAPPCHTYPE', 'DADAMEMBERTYPE', 'MEMLVTYPE', 'PAYWAYTYPE'];
      this.$http.post('/common/code/map/list', {cmclass : cmclassArr, isloading: false})
          .then(result =>{
            let data = result.data;
            for (const [key] of Object.entries(data)) {
              this.commonCode[key] = data[key];
            }

            this.checkAllAppchtype();
            this.checkAllMembertype();
            this.checkAllMemlvtype();
            this.checkAllPaywaytype();
          })
          .catch(error => {
            this.$util.debug(error);
          });
    },
    // 날짜 picker 콜백 함수
    onChangeStartDate(val) {
      this.searchData.startdate = val;
    },
    // 날짜 picker 콜백 함수
    onChangeEndDate(val) {
      this.searchData.enddate = val;
    },
    // 조회조건 - 적용채널 전체체크
    checkAllAppchtype() {
      let isAllCheck = this.searchData.isallchannel;
      this.searchData.muappchtypeArr = [];
      if (isAllCheck === 'T') {
        for(let type of this.commonCode.muappchtype){
          this.searchData.muappchtypeArr.push(type.cmcode);
        }
      }
    },
    // 조회조건 - 대상회원유형 전체체크
    checkAllMembertype() {
      let isAllCheck = this.searchData.isallmember;
      this.searchData.mumembertypeArr = [];
      if (isAllCheck === 'T') {
        for(let type of this.commonCode.dadamembertype){

          this.searchData.mumembertypeArr.push(type.cmcode);
        }
      }
    },
    // 조회조건 - 대상회원등급 전체체크
    checkAllMemlvtype() {
      let isAllCheck = this.searchData.isallmemlv;
      this.searchData.mumemlvtypeArr = [];
      if (isAllCheck === 'T') {
        for(let type of this.commonCode.memlvtype){
          this.searchData.mumemlvtypeArr.push(type.cmcode);
        }
      }
    },
    // 조회조건 - 결제수단 전체체크
    checkAllPaywaytype: function() {
      let isAllCheck = this.searchData.isallpayway;
      this.searchData.paywaytypeArr = [];
      if (isAllCheck === 'T') {
        for(let type of this.commonCode.paywaytype){
          this.searchData.paywaytypeArr.push(type.cmcode);
        }
      }
    },
    checkTotAll(){
      let isAllCheck = this.searchData.isTotCheck;
      this.searchData.isallmemlv = isAllCheck;
      this.searchData.isallmember = isAllCheck;
      this.searchData.isallchannel = isAllCheck;
      this.searchData.isallpayway = isAllCheck;

      this.checkAllAppchtype();
      this.checkAllMembertype();
      this.checkAllMemlvtype();
      this.checkAllPaywaytype();
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
      this.$http.post('/admin/stats/age/list/excel', param, config);
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
    },
    // 다중적용채널
    'searchData.muappchtypeArr': function(value) {
      if (value.length < this.commonCode.muappchtype.length) {
        this.searchData.isallchannel = 'F';
      } else {
        this.searchData.isallchannel = 'T';
      }
    },
    // 대상회원유형
    'searchData.mumembertypeArr': function(value) {
      if (value.length < this.commonCode.dadamembertype.length) {
        this.searchData.isallmember = 'F';
      } else {
        this.searchData.isallmember = 'T';
      }
    },
    // 대상회원등급
    'searchData.mumemlvtypeArr': function(value) {
      if (value.length < this.commonCode.memlvtype.length) {
        this.searchData.isallmemlv = 'F';
      } else {
        this.searchData.isallmemlv = 'T';
      }
    },
    // 결제수단
    'searchData.paywaytypeArr': function(value) {
      if (value.length < this.commonCode.paywaytype.length) {
        this.searchData.isallpayway = 'F';
      } else {
        this.searchData.isallpayway = 'T';
      }
    },
  }
}
</script>

<style scoped>

</style>