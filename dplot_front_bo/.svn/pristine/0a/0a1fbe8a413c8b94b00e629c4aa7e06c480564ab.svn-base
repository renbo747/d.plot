<template>
    <!-- 컨텐츠 영역 -->
    <div class="content m-leftmenu">
      <common-navigator></common-navigator>
      <div class="inner">
        <div class="clearfix">
          <div class="bar-title fl">휴면회원</div>
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
            <dt>휴면처리일</dt>
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
        </div>
        <div class="btn-group" v-show="isRead">
          <button type="button" class="btn big blue" @click="onSearch(1)">검색</button>
          <button type="button" class="btn big gray" @click="initParam">초기화</button>
        </div>
        <div class="caption-group mt10 clearfix">
          <div class="total-group fl">
            <span class="total">전체 <strong>{{ totalCnt }}</strong>명</span>
            <span>금일휴면 <strong>{{ todayCnt }}</strong>명</span>
          </div>
          <div class="btn-group fr">
            <button type="button" class="btn blue-line" v-show="isWrite" @click="restoreMember">복원</button>
            <button type="button" class="btn blue-line" v-show="isWrite" @click="resignMember">탈퇴</button>
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
            <col width="5%" /><!-- checkbox -->
            <col width="15%" /><!-- 휴면처리일시 -->
            <col width="10%" /><!-- 아이디 -->
            <col width="10%" /><!-- 이름 -->
            <col width="8%" /><!-- 회원유형 -->
            <col width="8%" /><!-- 등급 -->
            <col width="" /><!-- 이메일 -->
            <col width="15%" /><!-- 휴대폰번호 -->
            <col width="15%" /><!-- 경과일 -->
          </colgroup>
          <thead>
          <tr>
            <th><input type="checkbox" id="chkall" v-model="allChecked" @change="checkedAll($event.target.checked)"/></th>
            <th>휴면처리일시
              <button type="button" :value="sortData.sleepdate" class="sort" v-if="isRead"
                      :class="[{up : sortData.sleepdate === 'sleepdate_asc'}, {down : sortData.sleepdate === 'sleepdate_desc'}]"
                      @click="sortToggle(sortData.sleepdate)"></button>
            </th>
            <th>아이디
              <button type="button" :value="sortData.id" class="sort" v-if="isRead"
                      :class="[{up : sortData.id === 'id_asc'}, {down : sortData.id === 'id_desc'}]"
                      @click="sortToggle(sortData.id)"></button>
            </th>
            <th>이름</th>
            <th>회원유형
              <button type="button" :value="sortData.membertype" class="sort" v-if="isRead"
                      :class="[{up : sortData.membertype === 'membertype_asc'}, {down : sortData.membertype === 'membertype_desc'}]"
                      @click="sortToggle(sortData.membertype)"></button>
            </th>
            <th>등급
              <button type="button" :value="sortData.memlvtype" class="sort" v-if="isRead"
                      :class="[{up : sortData.memlvtype === 'memlvtype_asc'}, {down : sortData.id === 'memlvtype_desc'}]"
                      @click="sortToggle(sortData.memlvtype)"></button>
            </th>
            <th>이메일</th>
            <th>휴대폰번호
              <button type="button" :value="sortData.mobile" class="sort" v-if="isRead"
                      :class="[{up : sortData.mobile === 'mobile_asc'}, {down : sortData.mobile === 'mobile_desc'}]"
                      @click="sortToggle(sortData.mobile)"></button>
            </th>
            <th>경과일</th>
          </tr>
          </thead>
          <tbody v-if="listData.length > 0">
          <tr v-for="row in listData" v-bind:key="row.no">
            <td><input type="checkbox" v-model="row.IsChecked"/></td>
            <td>{{ row.changesleepdate }}</td>
            <td>{{ row.userid }}</td>
            <td>{{ $util.nameFilter(row.name) }}</td>
            <td>{{ row.dadamembertypename }}</td>
            <td>{{ row.memlvtypename }}</td>
            <td>{{ $util.emailFilter(row.email) }}</td>
            <td>{{ $util.phoneNumberFilter(row.mobile, true) }}</td>
            <td>{{ row.sleepduringdate }}일</td>
          </tr>
          </tbody>
          <tbody v-else>
            <tr>
              <td colspan="9">조회 결과가 존재하지 않습니다.</td>
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
  name: "admin.member.dormancy.list",
  components: {CommonNavigator, CommonPageNavigator, CommonDatePicker},
  data() {
    return {
      searchData: {
        startdate: '',
        enddate: '',
        period: '3',
        skey : '',
        sword : ''
      },
      pagingData: {
        pageCount: 20,
        page: 1,
        listCount: 0
      },
      sortData: {
        sleepdate: 'sleepdate_desc',
        id: 'id_desc',
        membertype: 'membertype_desc',
        memlvtype: 'memlvtype_desc',
        mobile: 'mobile_desc'
      },
      todayCnt: 0,
      totalCnt:0,
      listData: [],
      isRead : false,
      isWrite : false,
      allChecked: false,
    }
  },
  mounted() {
    this.$http.post('/admin/common/pageAuth/check', {url : this.$options.name, isloading: false}).then(result => {
      this.isRead = (result.data.isread === 'T');
      this.isWrite = (result.data.iswrite === 'T');
      if(this.isRead) {
        this.initParam();

        if(typeof this.$route.params.type !== 'undefined' && this.$route.params.type === 'LINK'){
          let linkParam = this.$route.params;
          this.searchData.period = linkParam.period;
          this.searchData.startdate = linkParam.startdate;
          this.searchData.enddate = linkParam.enddate;
        }

        this.onSearch();
      }
    }).catch(error => {
      this.$util.debug(error);
    })
  },
  methods: {
    onSearch(page){
      let param = this.searchData;
      param.pageCount = this.pagingData.pageCount;
      param.page = (typeof page === 'undefined') ? this.pagingData.page : 1;
      param.listCount = this.pagingData.listCount;

      this.$http.post("/admin/member/dormancy/list", param).then(result => {
        this.pagingData.listCount = result.data.listcount;
        this.totalCnt = result.data.listcount;
        this.todayCnt = (result.data.today !== null) ? result.data.today : 0;
        this.listData = result.data.list;
        this.$util.dataSetSearchParam(this);

        this.allChecked = false;
      }).catch(error => {
        this.$util.debug(error);
      })
    },
    initParam() {
      this.searchData = this.$options.data().searchData;
      this.searchData.startdate = this.$util.getAddMonth(this.$util.getDate(''), -3, '-');
      this.searchData.enddate = this.$util.getDate('-');
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
    restoreMember() {
      let msg = '선택한 휴면회원을 정상회원으로 복원 하시겠습니까?'

      if(confirm(msg)) {
        let userNoArrStr = '';

        this.listData.forEach(function (row) {
          if (row.IsChecked) {
            if (userNoArrStr === '') {
              userNoArrStr += row.userno;
            } else {
              userNoArrStr += "," + row.userno;
            }
          }
        });

        if (userNoArrStr === '') {
          alert('선택된 항목이 없습니다.');
          return false;
        }

        let param = {
          state: '',
          user_no_arr_str: userNoArrStr
        }

        this.$http.post("/admin/member/sleep/restore", param)
            .then(result => {
              this.$util.debug(result);
              this.onSearch();
            }).catch(error => {
          this.$util.debug(error);
        });
      }
    },
    resignMember() {
      let msg = '선택한 휴면회원을 탈퇴 처리 하시겠습니까?\n진행중인 주문/클레임건이 존재하는 회원은 탈퇴 대상에서 제외 됩니다.';

      if(confirm(msg)) {
        let userNoArrStr = '';

        this.listData.forEach(function (row) {
          if (row.IsChecked) {
            if(row.procnt <= 0) {
              if (userNoArrStr === '') {
                userNoArrStr += row.userno;
              } else {
                userNoArrStr += "," + row.userno;
              }
            }
          }
        });

        if (userNoArrStr === '') {
          alert('처리할 대상이 존재하지 않습니다.');
          return false;
        }

        let param = {
          state: '',
          user_no_arr_str: userNoArrStr
        }

        this.$http.post("/admin/member/sleep/resign", param)
            .then(result => {
              this.$util.debug(result);
              this.onSearch();
            }).catch(error => {
          this.$util.debug(error);
        });
      }
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

<style scoped/>