<template>
  <div class="content m-leftmenu">
    <common-navigator></common-navigator>
    <div class="inner">
      <div class="clearfix">
        <div class="bar-title fl">전체회원</div>
      </div>
      <div class="boxing search-area">
        <dl>
          <dt>직접검색</dt>
          <dd>
            <select v-model="searchData.skey">
              <option value="">전체</option>
              <option value="id">아이디</option>
              <option value="name">이름</option>
              <option value="mobile">휴대폰번호</option>
              <option value="email">이메일</option>
            </select>
            <input type="text" v-model="searchData.sword" @keyup.enter="onSearch(1)"/>
          </dd>
        </dl>
        <dl>
          <dt>조회기간</dt>
          <dd>
            <select v-model="searchData.sdate">
              <option value="reg">가입일</option>
              <option value="lastlog">최근접속일</option>
              <option value="lastbuy">최근구매일</option>
              <option value="first">첫 구매일자</option>
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
          </dd>
        </dl>
        <dl>
          <dt>회원유형</dt>
          <dd>
            <div>
              <input type="checkbox" id="chkAllMember" v-model="searchData.isallmember" true-value="T" false-value="F" @change="checkAllMembertype">
              <label for="chkAllMember">전체</label>
            </div>
            <div v-for="item in commonCode.DADAMEMBERTYPE" :key="item.cmcode">
              <input type="checkbox" :id="'membertype_'+item.cmcode" v-model="searchData.membertypeArr" :true-value="[]" :value="item.cmcode">
              <label :for="'membertype_'+item.cmcode">{{ item.codename }}</label>
            </div>
          </dd>
        </dl>
        <dl>
          <dt>회원등급</dt>
          <dd>
            <div>
              <input type="checkbox" id="chkAllMemlv" v-model="searchData.isallmemlv" true-value="T" false-value="F" @change="checkAllMemlvtype">
              <label for="chkAllMemlv">전체</label>
            </div>
            <div v-for="item in commonCode.MEMLVTYPE" :key="item.cmcode">
              <input type="checkbox" :id="'memlvtype_'+item.cmcode" v-model="searchData.memlvtypeArr" :true-value="[]" :value="item.cmcode">
              <label :for="'memlvtype_'+item.cmcode">{{ item.codename }}</label>
            </div>
          </dd>
        </dl>
        <dl>
          <dt>가입경로</dt>
          <dd>
            <div>
              <input type="checkbox" id="chkAllJoinType" v-model="searchData.isalljointype" true-value="T" false-value="F" @change="checkAllJointype">
              <label for="chkAllJoinType">전체</label>
            </div>
            <div v-for="item in commonCode.JOINTYPE" :key="item.cmcode">
              <input type="checkbox" :id="'jointype_'+item.cmcode" v-model="searchData.jointypeArr" :true-value="[]" :value="item.cmcode">
              <label :for="'jointype_'+item.cmcode">{{ item.codename }}</label>
            </div>
          </dd>
        </dl>
        <dl>
          <dt>성별</dt>
          <dd>
            <div class="radio_wrap wide3">
              <input type="radio" name="gender" id="gd11" v-model="searchData.gender" value=""/><label for="gd11">전체</label>
              <input type="radio" name="gender" id="gd22" v-model="searchData.gender" value="M"/><label for="gd22">남</label>
              <input type="radio" name="gender" id="gd33" v-model="searchData.gender" value="F"/><label for="gd33">여</label>
            </div>
          </dd>
        </dl>
        <dl>
          <dt>내/외국인구분</dt>
          <dd>
            <div class="radio_wrap wide3">
              <input type="radio" name="nation" id="nation11" v-model="searchData.isdomastic" value="" /><label for="nation11">전체</label>
              <input type="radio" name="nation" id="nation22" v-model="searchData.isdomastic" value="T" /><label for="nation22">내국인</label>
              <input type="radio" name="nation" id="nation33" v-model="searchData.isdomastic" value="F" /><label for="nation33">외국인</label>
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
          <span>금일가입 <strong>{{ todayCnt }}</strong>명</span>
        </div>
        <div class="btn-group fr">
          <button type="button" class="btn blue-line" v-show="isWrite" @click="goMemoPopup('BLACK')">블랙</button>
          <button type="button" class="btn blue-line" v-show="isWrite" @click="goMemoPopup('WITHDRAW')">탈퇴</button>
          <button type="button" class="btn green-line" v-show="isRead" @click="goExcelDownload"><i class="icon-excel"></i>엑셀다운로드</button>
          <select v-model="pagingData.pageCount" v-show="isRead">
            <option value="20">20개씩 보기</option>
            <option value="50">50개씩 보기</option>
            <option value="100">100개씩 보기</option>
          </select>
        </div>
      </div>
      <table cellpadding="0" cellspacing="0" class="data-tb align-c">
        <caption>전체회원목록</caption>
        <colgroup>
          <col width="" /><!-- checkbox -->
          <col width="" /><!-- No -->
          <col width="" /><!-- 아이디 -->
          <col width="" /><!-- 이름 -->
          <col width="" /><!-- 유형 -->
          <col width="" /><!-- 등급 -->
          <col width="" /><!-- 휴대폰번호 -->
          <col width="" /><!-- 이메일 -->
          <col width="" /><!-- 성별 -->
          <col width="" /><!-- 생년월일 -->
          <col width="" /><!-- 총적립금 -->
          <col width="" /><!-- 보유쿠폰 -->
          <col width="" /><!-- 적립금 -->
          <col width="" /><!-- 포인트 -->
          <col width="" /><!-- 임직원포인트 -->
          <col width="" /><!-- 최근접속일시 -->
          <col width="" /><!-- 최근구매일시 -->
          <col width="" /><!-- 첫구매일자 -->
          <col width="" /><!-- 가입일 -->
        </colgroup>
        <thead>
        <tr>
          <th><input type="checkbox" v-model="allChecked" @change="checkedAll($event.target.checked)" /></th>
          <th>No</th>
          <th>아이디
            <button type="button" :value="sortData.id" class="sort" v-if="isRead"
                    :class="[{up : sortData.id === 'id_asc'}, {down : sortData.id === 'id_desc'}]"
                    @click="sortToggle(sortData.id)"></button>
          </th>
          <th>이름</th>
          <th>유형
            <button type="button" :value="sortData.membertype" class="sort" v-if="isRead"
                    :class="[{up : sortData.membertype === 'membertype_asc'}, {down : sortData.membertype === 'membertype_desc'}]"
                    @click="sortToggle(sortData.membertype)"></button>
          </th>
          <th>등급
            <button type="button" :value="sortData.memlvtype" class="sort" v-if="isRead"
                    :class="[{up : sortData.memlvtype === 'memlvtype_asc'}, {down : sortData.memlvtype === 'memlvtype_desc'}]"
                    @click="sortToggle(sortData.memlvtype)"></button>
          </th>
          <th>휴대폰번호</th>
          <th>이메일</th>
          <th>성별</th>
          <th>생년월일</th>
          <th>보유쿠폰
            <button type="button" :value="sortData.coupon" class="sort" v-if="isRead"
                    :class="[{up : sortData.coupon === 'coupon_asc'}, {down : sortData.coupon === 'coupon_desc'}]"
                    @click="sortToggle(sortData.coupon)"></button>
          </th>
          <th>적립금</th>
          <th>포인트</th>
          <th>임직원포인트</th>
          <th>최근접속일시
            <button type="button" :value="sortData.lastlogindate" class="sort" v-if="isRead"
                    :class="[{up : sortData.lastlogindate === 'lastlogindate_asc'}, {down : sortData.lastlogindate === 'lastlogindate_desc'}]"
                    @click="sortToggle(sortData.lastlogindate)"></button>
          </th>
          <th>최근구매일시
            <button type="button" :value="sortData.lastbuydate" class="sort" v-if="isRead"
                    :class="[{up : sortData.lastbuydate === 'lastbuydate_asc'}, {down : sortData.lastbuydate === 'lastbuydate_desc'}]"
                    @click="sortToggle(sortData.lastbuydate)"></button>
          </th>
          <th>첫구매일자
            <button type="button" :value="sortData.frstbuydate" class="sort" v-if="isRead"
                    :class="[{up : sortData.frstbuydate === 'frstbuydate_asc'}, {down : sortData.frstbuydate === 'frstbuydate_desc'}]"
                    @click="sortToggle(sortData.frstbuydate)"></button>
          </th>
          <th>가입일
            <button type="button" :value="sortData.regdate" class="sort" v-if="isRead"
                    :class="[{up : sortData.regdate === 'regdate_asc'}, {down : sortData.regdate === 'regdate_desc'}]"
                    @click="sortToggle(sortData.regdate)"></button>
          </th>
          <th>추천인ID</th>
        </tr>
        </thead>
        <tbody v-if="listData.length > 0">
        <tr v-for="(row, index) in listData" v-bind:key="index">
          <td><input type="checkbox" v-model="row.IsChecked" /></td>
          <td>{{ loopNumberForPaging(index) }}</td>
          <td>{{ row.userid }}</td>
          <td>
            <i v-if="row.isblkmember === 'T'" class="black"></i>
            <a href="javascript:void(0)" v-if="isRead" class="link" @click="goDetail(row.userno)">{{ row.username }}</a>
            <a href="javascript:void(0)" v-else>{{ row.username }}</a>
          </td>
          <td>
            <a href="javascript:void(0)" v-if="isRead" class="link" @click="historyPopupOpen(row.userno, 'TYPE')" >{{ row.dadamembertypename }}</a>
            <a href="javascript:void(0)" v-else >{{ row.dadamembertypename }}</a>
          </td>
          <td>
            <a href="javascript:void(0)" v-if="isRead" class="link" @click="historyPopupOpen(row.userno, 'LV')" >{{ row.memlvtypename }}</a>
            <a href="javascript:void(0)" v-else >{{ row.memlvtypename }}</a>
          </td>
          <td>
            <a href="javascript:void(0)" class="link" @click="goSms(row.userno)" v-if="isWrite">{{ row.mobile }}</a>
            <a href="javascript:void(0)" v-else>{{ row.mobile }}</a>
          </td>
          <td>
            <a href="javascript:void(0)" @click="goMail(row.userno)" v-if="isWrite" class="link">{{ row.email}}</a>
            <a href="javascript:void(0)" v-else>{{ row.email}}</a>
          </td>
          <td>{{ row.gendername }}</td>
          <td>{{  row.birthdate }}</td>
          <td>{{ row.havecoupon }}</td>
          <td class="right">{{ row.paypoint }}</td>
          <td class="right">{{ row.epoint }}</td>
          <td class="right">{{ row.emppoint }}</td>
          <td>{{ row.lastlogindate }}</td>
          <td>{{ row.lastbuydate }}</td>
          <td>{{ row.frstbuydate }}</td>
          <td>{{ row.regdate }}</td>
          <td>
            <a href="javascript:void(0)" v-if="isRead" class="link" @click="goDetail(row.recuserno)">{{ row.recuserid }}</a>
            <a href="javascript:void(0)" v-else>{{ row.recuserid }}</a>
          </td>
        </tr>
        </tbody>
        <tbody v-else>
        <tr>
          <td colspan="19">조회 결과가 존재하지 않습니다.</td>
        </tr>
        </tbody>
      </table>
      <div class="bottom-group">
        <common-page-navigator v-show="isRead" v-bind:pagingData="pagingData"
                               v-on:setPagingData="setPagingData"></common-page-navigator>
      </div>
    </div>
    <!-- SMS 보내기   -->
    <CommonSms @close="goCloseSms" :user-no="activeUserNo" v-if="isCommonSmsShow"/>

    <!-- 메일 보내기   -->
    <CommonMail v-on:close-popup="goCloseMail" :user-no="activeUserNo" v-if="isCommonMailShow"/>

    <AdminMemberInfo v-if="detailShow" v-bind:activeUserNo="activeUserNo" v-on:closeDetail="closeDetail"></AdminMemberInfo>
    <AdminMemberMemoPopup v-if="memoPopupShow" :userNoArr="userNoArr" :memoType="memoType" v-on:closeMemo="closeMemo"></AdminMemberMemoPopup>
  </div>
</template>

<script>
import CommonNavigator from "@views.admin/common/CommonNavigator";
import CommonPageNavigator from "@views.admin/common/CommonPageNavigator";
import CommonDatePicker from "@views.admin/common/CommonDatePicker";
import CommonMail from "@views.admin/common/popup/CommonMail";
import CommonSms from "@views.admin/common/popup/CommonSms";
import AdminMemberInfo from "@views.admin/member/info/AdminMemberInfo.vue";
import AdminMemberMemoPopup from "@views.admin/member/AdminMemberMemoPopup";
import CommonMemberInfoHistory from "@views.admin/common/popup/CommonMemberInfoHistory";

export default {
  name: "admin.member.total.list",
  components: {
    AdminMemberMemoPopup,
    AdminMemberInfo, CommonNavigator, CommonPageNavigator, CommonDatePicker, CommonMail, CommonSms},
  data() {
    return {
      searchData: {
        skey: '',
        sword: '',
        sdate: 'reg',
        startdate: '',
        enddate: '',
        period: '3',
        isallmember: 'T',    //회원유형전체여부
        membertypeArr: [], //다중대상회원유형Array
        isallmemlv: 'T',     //회원등급전체여부
        memlvtypeArr: [],  //다중대상회원등급Array
        isalljointype : 'T',
        jointypeArr: [],  //가입경로Array
        jointype: '', //가입경로
        gender: '', //성별
        isdomastic: '',
      },
      pagingData: {
        pageCount: 20,
        page: 1,
        listCount: 0
      },
      sortData: {
        id: 'id_desc',
        membertype: 'membertype_desc',
        memlvtype: 'memlvtype_desc',
        coupon: 'coupon_desc',
        lastlogindate: 'lastlogindate_desc',
        lastbuydate: 'lastbuydate_desc',
        frstbuydate: 'frstbuydate_desc',
        regdate: 'regdate_desc',
      },
      commonCode: {
        DADAMEMBERTYPE: [], // 다다픽회원유형
        MEMLVTYPE: [],       // 회원등급
        JOINTYPE: []
      },
      todayCnt: 0,
      totalCnt:0,
      listData: [],
      isRead : false,
      isWrite : false,
      isCommonSmsShow: false,
      isCommonMailShow: false,
      activeUserNo: null,
      detailShow: false,
      memoPopupShow: false,
      memoType: '',
      userNoArr: {},
      allChecked: false,
    }
  },
  mounted() {

    this.$http.post('/admin/common/pageAuth/check', {url : this.$options.name, isloading: false}).then(result => {
      this.isRead = (result.data.isread === 'T');
      this.isWrite = (result.data.iswrite === 'T');

      if(this.isRead) {

        const cmclassArr = ['DADAMEMBERTYPE', 'MEMLVTYPE', 'JOINTYPE'];
        this.$http.post('/common/code/map/list', {cmclass : cmclassArr, isloading: false}).then(result =>{

          this.commonCode.DADAMEMBERTYPE = result.data.dadamembertype;
          this.commonCode.MEMLVTYPE = result.data.memlvtype;
          this.commonCode.JOINTYPE = result.data.jointype;

          this.initParam();
          if(typeof this.$route.params.type !== 'undefined' && this.$route.params.type === 'LINK'){

            let linkParam = this.$route.params;
            this.searchData.period = linkParam.period;
            this.searchData.startdate = linkParam.startdate;
            this.searchData.enddate = linkParam.enddate;
            if(typeof linkParam.jointype !== 'undefined'){
              this.searchData.isalljointype = 'F';
              this.searchData.jointypeArr = [linkParam.jointype];
            }
          }

          // this.onSearch();	// 최초 로딩 시 조회되지 않도록 김태선님이 요청(James, 2022-09-28)
        }).catch(error => {
          this.$util.debug(error);
        })
      }
    }).catch(error => {
      this.$util.debug(error);
    })
  },
  methods: {
    initParam: function () {
      this.searchData = this.$options.data().searchData;
      this.searchData.startdate = this.$util.getAddMonth(this.$util.getDate(''), -3, '-');
      this.searchData.enddate = this.$util.getDate('-');

      this.checkAllMembertype();
      this.checkAllMemlvtype();
      this.checkAllJointype();
    },

    loopNumberForPaging(index) {
      if(this.pagingData.page > 1){
        let page = this.pagingData.page - 1;
        return (index+1) + (page * this.pagingData.pageCount);
      } else {
        return (index+1);
      }

    },
    onSearch(page) {
      let param = this.searchData;

      param.pageCount = this.pagingData.pageCount;
      param.page = (typeof page === 'undefined') ? this.pagingData.page : 1;
      param.listCount = this.pagingData.listCount;

      this.$http.post('/admin/member/total/list', param).then(result => {
        this.pagingData.listCount = result.data.listcount;
        this.totalCnt = result.data.listcount;
        this.todayCnt = (result.data.today !== null) ? result.data.today : 0;
        this.listData = result.data.list;
        this.$util.dataSetSearchParam(this);

        this.$util.debug(result);
      }).catch(error => {
        this.$util.debug(error);
      })

    },
    checkedAll: function (val) {
      this.listData.forEach(function (row) {
        row.IsChecked = val;
      });
    },
    onChangeStartDate(val) {
      this.searchData.startdate = val;
    },
    // 날짜 picker 콜백 함수
    onChangeEndDate(val) {
      this.searchData.enddate = val;
    },
    setPagingData: function (obj) {
      this.pagingData = obj;
      this.onSearch();
    },
    // 유형별 전체체크
    checkAllMembertype() {
      let isAllCheck = this.searchData.isallmember;
      this.searchData.membertypeArr = [];
      if (isAllCheck === 'T') {
        for(let type of this.commonCode.DADAMEMBERTYPE){
          this.searchData.membertypeArr.push(type.cmcode);
        }
      }
    },
    // 등급별 전체체크
    checkAllMemlvtype() {
      let isAllCheck = this.searchData.isallmemlv;
      this.searchData.memlvtypeArr = [];
      if (isAllCheck === 'T') {
        for(let type of this.commonCode.MEMLVTYPE){
          this.searchData.memlvtypeArr.push(type.cmcode);
        }
      }
    },
    checkAllJointype() {
      let isAllCheck = this.searchData.isalljointype;
      this.searchData.jointypeArr = [];
      if (isAllCheck === 'T') {
        for(let type of this.commonCode.JOINTYPE){
          this.searchData.jointypeArr.push(type.cmcode);
        }
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
    goSms(userNo) {
      this.activeUserNo = userNo;
      this.isCommonSmsShow = true;
    },

    goCloseSms() {
      this.isCommonSmsShow = false;
    },

    goMail(userNo) {
      this.activeUserNo = userNo;
      this.isCommonMailShow = true;
    },

    goCloseMail() {
      this.isCommonMailShow = false;
    },
    goExcelDownload(){
      let param = this.searchData;
      let config = { responseType: 'arraybuffer' };
      this.$http.post('/admin/member/total/list/excel', param, config);
    },
    goMemoPopup(type) {
      let userNoArrTemp = [];
      let userNoProcessTemp = [];
      this.memoType = type;

      if(type === 'WITHDRAW'){
        this.listData.forEach(function (row) {
          if(row.IsChecked && row.procnt <= 0){
            userNoArrTemp.push(row.userno);
          } else if(row.IsChecked) {
            userNoProcessTemp.push(row.userid);
          }
        });
        if(userNoProcessTemp.length > 0){
          let msg = userNoProcessTemp.join(", ");
          alert(msg + "회원은 진행중인 주문/클레임건이 존재 하여 대상에서 제외됩니다.")
        }
      } else {
        this.listData.forEach(function (row) {
          if(row.IsChecked){
            userNoArrTemp.push(row.userno);
          }
        });
      }

      if(userNoArrTemp.length <= 0){
        alert("처리할 대상이 존재하지 않습니다.");
      } else {
        this.userNoArr = userNoArrTemp;
        this.memoPopupShow = true;
      }
    },
    goDetail(userNo){
      this.activeUserNo = userNo;
      this.detailShow = true;
      // window.open('/admin/partners/applyList', '_blank');
    },
    historyPopupOpen(userno, type){
      let param = { userno: userno, type : type };
      this.$eventBus.$emit('modalShow', CommonMemberInfoHistory, param);
    },
    closeDetail: function () {
      this.detailShow = false;
    },
    closeMemo() {
      this.memoType = '';
      this.userNoArr = {};
      this.memoPopupShow = false;
      this.onSearch();
    }
  },
  watch: {
    // 유형별
    'searchData.membertypeArr': function(value) {
      if (value.length < this.commonCode.DADAMEMBERTYPE.length) {
        this.searchData.isallmember = 'F';
      } else {
        this.searchData.isallmember = 'T';
      }
    },
    // 등급별
    'searchData.memlvtypeArr': function(value) {
      if (value.length < this.commonCode.MEMLVTYPE.length) {
        this.searchData.isallmemlv = 'F';
      } else {
        this.searchData.isallmemlv = 'T';
      }
    },
    'searchData.jointypeArr': function(value) {
      if (value.length < this.commonCode.JOINTYPE.length) {
        this.searchData.isalljointype = 'F';
      } else {
        this.searchData.isalljointype = 'T';
      }
    },
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
