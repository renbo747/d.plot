<template>
  <!-- 컨텐츠 영역 -->
  <div class="content m-leftmenu">
    <!-- 상세 팝업 -->
    <PartnersDetail
        ref="refDetail"
        v-if="isDetailShow"
        :idx="isDetailIdx"
    />

    <!-- 등록 팝업   -->
    <PartnersNewPost ref="refNewPost" v-if="isNewPostShow"/>

    <AdminCommonNavigator/>
    <div class="inner">
      <div class="boxing search-area">
        <dl>
          <dt>조회기간</dt>
          <dd>
            <select v-model="searchData.sdate">
              <option value="start">질문일자</option>
              <option value="end">답변일자</option>
            </select>
            <CommonDatePicker :value="searchData.startDate" @change="onChangeStartDate"/>
            <span>-</span>
            <CommonDatePicker :value="searchData.endDate" @change="onChangeEndDate"/>
            <div class="radio_wrap">
              <input type="radio" v-model="searchData.period" id="rd1" value='-1'/><label
                for="rd1">어제</label>
              <input type="radio" v-model="searchData.period" id="rd2" value='0'/><label
                for="rd2">오늘</label>
              <input type="radio" v-model="searchData.period" id="rd3" value='7'/><label
                for="rd3">일주일</label>
              <input type="radio" v-model="searchData.period" id="rd4" value='1'/><label
                for="rd4">1개월</label>
              <input type="radio" v-model="searchData.period" id="rd5" value='3'/><label
                for="rd5">3개월</label>
              <input type="radio" v-model="searchData.period" id="rd6" value='6'/><label
                for="rd6">6개월</label>
            </div>
          </dd>
        </dl>
        <dl>
          <dt>직접검색</dt>
          <dd>
            <select v-model="searchData.skey">
              <option value="">전체</option>
              <option v-if="isAdmin" value="dealername">업체명</option>
              <option v-if="isAdmin" value="charge">담당자</option>
              <option v-if="!isAdmin" value="ordno">주문번호</option>
              <option v-if="!isAdmin" value="writer">질문자</option>
              <option value="subject">질문제목</option>
            </select>
            <input type="text" v-model="searchData.sword" @keyup.enter="onSearch(1)"/>
          </dd>
        </dl>
        <dl>
          <dt>상태</dt>
          <dd>
            <div class="radio_wrap">
              <input type="radio" name="use" id="rd01" value="" v-model="searchData.isreply"/><label
                for="rd01">전체</label>
              <input type="radio" name="use" id="rd02" value="F" v-model="searchData.isreply"/><label
                for="rd02">대기</label>
              <input type="radio" name="use" id="rd03" value="T" v-model="searchData.isreply"/><label
                for="rd03">완료</label>
            </div>
          </dd>
        </dl>
      </div>
      <div class="btn-group" v-show="isRead">
        <button type="button" class="btn big blue" @click="onSearch(1)">검색</button>
        <button type="button" class="btn big gray" @click="onSearchDataRest">초기화</button>
      </div>
      <div class="caption-group mt10 clearfix">
        <div class="total-group fl">
          <span class="total">전체 <strong>{{ stateData.total }}</strong>건</span>
          <span>대기 <strong>{{ stateData.stay }}</strong>건</span>
          <span>완료 <strong>{{ stateData.comp }}</strong>건</span>
        </div>
        <div class="btn-group fr">
          <button type="button" class=" btn red-line" @click="onDelete" v-show="isWrite && isAdmin">삭제</button>
          <select v-model="pagingData.pageCount" v-show="isRead">
            <option value="20">20개씩 보기</option>
            <option value="50">50개씩 보기</option>
            <option value="100">100개씩 보기</option>
          </select>
        </div>
      </div>
      <table cellpadding="0" cellspacing="0" class="data-tb align-c">
        <caption>파트너사 문의 목록</caption>
        <colgroup>
          <col width="3%"/><!-- checkbox -->
          <col width="3%"/><!-- No -->
          <col width="9%"/><!-- 주문번호 -->
          <col width=""/><!-- 질문제목 -->
          <col width="7%"/><!-- 질문자 -->
          <col width="10%"/><!-- 질문일시 -->
          <col width="7%" v-if="isAdmin"/><!-- 업체명 -->
          <col width="7%"/><!-- 담당자 -->
          <col width="10%"/><!-- 답변일시 -->
          <col width="5%"/><!-- 답변상태 -->
        </colgroup>
        <thead>
        <tr>
          <th><input type="checkbox" id="chkall" v-model="isChecked" @change="onCheckAll($event.target.checked)"/>
          <th>No</th>
          <th>주문번호
            <button type="button" v-if="isRead"
                    :value="sortData.ordno"
                    class="sort"
                    :class="[{up : sortData.ordno === 'ordno_asc'}, {down : sortData.ordno === 'ordno_desc'}]"
                    @click="sortToggle(sortData.ordno)">
            </button>
          </th>
          <th>질문제목
            <button type="button" v-if="isRead"
                    :value="sortData.subject"
                    class="sort"
                    :class="[{up : sortData.subject === 'subject_asc'}, {down : sortData.subject === 'subject_desc'}]"
                    @click="sortToggle(sortData.subject)">
            </button>
          </th>
          <th>질문자</th>
          <th>질문일시
            <button type="button" v-if="isRead"
                    :value="sortData.regdate"
                    class="sort"
                    :class="[{up : sortData.regdate === 'regdate_asc'}, {down : sortData.regdate === 'regdate_desc'}]"
                    @click="sortToggle(sortData.regdate)">
            </button>
          </th>
          <th v-if="isAdmin">업체명
            <button type="button" v-if="isRead"
                    :value="sortData.dealername"
                    class="sort"
                    :class="[{up : sortData.dealername === 'dealername_asc'}, {down : sortData.dealername === 'dealername_desc'}]"
                    @click="sortToggle(sortData.dealername)">
            </button>
          </th>
          <th>담당자</th>
          <th>답변일시
            <button type="button" v-if="isRead"
                    :value="sortData.repregdate"
                    class="sort"
                    :class="[{up : sortData.repregdate === 'repregdate_asc'}, {down : sortData.repregdate === 'repregdate_desc'}]"
                    @click="sortToggle(sortData.repregdate)">
            </button>
          </th>
          <th>답변상태
            <button type="button" v-if="isRead"
                    :value="sortData.isreply"
                    class="sort"
                    :class="[{up : sortData.isreply === 'isreply_asc'}, {down : sortData.isreply === 'isreply_desc'}]"
                    @click="sortToggle(sortData.isreply)">
            </button>
          </th>
        </tr>
        </thead>
        <tbody v-if="listData.length > 0">
        <tr v-for="(row, i) in listData" :key="i">
          <td><input type="checkbox" id="chk01" v-model="listCheckData" :value="row.idx"></td>
          <td>{{ row.rn }}</td>
          <td>{{ row.ordno }}</td>
          <td class="left"><a class="link" @click="goDetail(row.idx)">{{ row.subject }}</a></td>
          <td>{{ row.writer }}</td>
          <td>{{ row.regdate }}</td>
          <td v-if="isAdmin">{{ row.dealername }}</td>
          <td>{{ row.chargename }}</td>
          <td>{{ row.repregdate }}</td>
          <td>{{ row.isreply }}</td>
        </tr>
        </tbody>
        <tbody v-else>
        <td :colspan="isAdmin? 10:9">조회 결과가 존재하지 않습니다.</td>
        </tbody>
      </table>
      <div class="bottom-group">
        <CommonPageNavigator :pagingData="pagingData" v-on:setPagingData="setPagingData"/>
        <div class="btn-group">
          <button type="button" class="btn blue" @click="goNewPost" v-show="isWrite && isAdmin">문의 등록</button>
        </div>
      </div>
    </div>
  </div>
  <!-- /컨텐츠 영역 -->

</template>

<script>
import AdminCommonNavigator from "@views.admin/common/CommonNavigator";
import CommonDatePicker from "@views.admin/common/CommonDatePicker";
import CommonPageNavigator from "@views.admin/common/CommonPageNavigator";
import PartnersDetail from "@views.admin/cs/partners/PartnersDetail";
import PartnersNewPost from "@views.admin/cs/partners/PartnersNewPost";

export default {
  name: "admin.cs.partners",
  components: {
    PartnersNewPost,
    PartnersDetail,
    CommonPageNavigator,
    CommonDatePicker,
    AdminCommonNavigator
  },
  data() {
    return {
      isDetailShow: false,      // 상세 팝업 열림 여부
      isDetailIdx: '',          // 상세 팝업 idx
      isNewPostShow: false,     // 등록 열림 여부
      listData: [],             // 리스트 데이터
      listCheckData: [],        // 체크된 데이터 리스트
      isChecked: false,         // 체크 상태
      searchData: {
        sdate: 'start',         // 조회기간 (문의일자, 답변일자)
        startDate: '',          // 시작 연도
        endDate: '',            // 끝 연도
        skey: '',               // 검색 조건 (제목 작성자 내용)
        sword: '',              // 검색 단어
        period: '3',            // 날짜 검색 옵션 (어제,오늘,일주일...)
        isreply: '',            // 상태
        dealerno: ''            // 입점업체번호
      },
      pagingData: {
        pageCount: 20,          // 페이징 옵션(최대수)
        page: 1,                // 현재 페이지
        listCount: 0            // 전체 수
      },
      sortData: {
        ordno: 'ordno_desc',
        subject: 'subject_desc',
        regdate: 'regdate_desc',
        dealername: 'dealername_desc',
        repregdate: 'repregdate_desc',
        isreply: 'isreply_desc',
      },
      stateData: {
        comp: '',
        stay: '',
        total: ''
      },
      isAdmin: false, // 접속자 등급 여부 ( 어드민, 파트너사 구분 )
      userInfo: {},
      isRead: false,
      isWrite: false,
    }
  },
  mounted() {
    // 권한 설정
    this.$http.post('/admin/common/pageAuth/check', {url: this.$options.name, isloading: false}).then(result => {
      this.isRead = (result.data.isread === 'T');
      this.isWrite = (result.data.iswrite === 'T');
      this.searchData.startDate = this.$util.getAddMonth(this.$util.getDate(''), -3, '-');
      this.searchData.endDate = this.$util.getDate('-');
      if (this.isRead) {
        this.isAdmin = this.$util.isAuthorized('ADMIN_USER');
        this.$util.componentSetSearchParam(this);
        this.userInfo = this.$util.getUser(this.$store.getters['CONSTANTS'].MANAGER_SESSION);

        if(typeof this.$route.params.type !== 'undefined' && this.$route.params.type === 'LINK'){
          let linkParam = this.$route.params;
          this.searchData.isreply = linkParam.isreply;
          this.searchData.period = linkParam.period;
          this.searchData.startDate = linkParam.startdate;
          this.searchData.endDate = linkParam.enddate;
        }
        // 목록 조회
        this.onSearch();
      }
    }).catch(error => {
      this.$util.debug(error);
    })
  },
  methods: {
    // 검색
    onSearch(page) {
      this.isChecked = false;

      let params = this.searchData;
      params.pageCount = this.pagingData.pageCount;
      params.page = (typeof page === 'undefined') ? this.pagingData.page : 1;
      params.listCount = this.pagingData.listCount;
      if (!this.isAdmin) {
        this.searchData.dealerno = this.userInfo.no;
      }
      params.isAdmin = this.isAdmin;

      this.$http.post("/admin/cs/partners/search", params)
          .then(result => {
            if (result.statusCode === 200) {
              let data = result.data;
              this.listData = data.list;
              this.pagingData.listCount = data.liststate.total;
              this.stateData = data.liststate;
            }
          })
          .catch(error => {
            this.$util.debug(error);
          });
    },
    // 테이블 소트
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

    // 전체 체크
    onCheckAll(checked) {
      if (checked) { // 전체 체크
        for (let i in this.listData) {
          this.listCheckData[i] = this.listData[i].idx;
        }
      } else { // 전체 체크 해제
        this.listCheckData = [];
      }
    },

    // 삭제
    onDelete() {
      if (this.listCheckData.length === 0) {
        alert("선택항목을 체크하시기 바랍니다.");
      } else {
        let params = {
          idxlist: this.listCheckData,
          istrash: 'T',
        }

        if (confirm("선택항목을 삭제하시겠습니까?")) {
          this.$http.post("/admin/cs/partners/update", params)
              .then(result => {
                if (result.statusCode === 200) {
                  alert("삭제가 완료되었습니다.");
                  this.onSearch();
                }
              })
              .catch(error => {
                this.$util.debug(error);
              });
        }
      }
    },

    // 초기화
    onSearchDataRest() {
      this.searchData.sdate = 'start';
      this.searchData.skey = '';
      this.searchData.sword = '';
      this.searchData.period = '3';
      this.searchData.startDate = this.$util.getAddMonth(this.$util.getDate(''), -3, '-');
      this.searchData.endDate = this.$util.getDate('-');
      this.searchData.isreply = '';
    },

    // 날짜 picker 콜백 함수
    onChangeEndDate(val) {
      this.searchData.endDate = val;
    },

    // 날짜 picker 콜백 함수
    onChangeStartDate(val) {
      this.searchData.startDate = val;
    },

    // 페이징 콜백 함수
    setPagingData(param) {
      this.pagingData = param;
      this.onSearch();
    },


    /////////////////////////////////////////////////////////////////

    ///////////////////////// 팝업 메서드 /////////////////////////////
    // 상세 팝업 열기
    goDetail(idx) {
      // this.$refs.refDetail.onOpen(idx);
      this.isDetailShow = true;
      this.isDetailIdx = idx;
    },

    // 등록 팝업 열기
    goNewPost() {
      this.isNewPostShow = true;
    },

    // 상세 팝업 닫기
    goCloseDetailPopup(isreload) {
      this.isDetailShow = false;
      if(isreload) {
        this.onSearch();
      }
    },

    // 등록 팝업 닫기
    goCloseNewPostPopup(isreload) {
      this.isNewPostShow = false;
      if(isreload) {
        this.onSearch();
      }
    },


    /////////////////////////////////////////////////////////////////
  },

  watch: {
    'searchData.period': function (val) {
      let dayType = ["-1", "0", "7"];
      let monthType = ["1", "3", "6"];
      let valueToInt = parseInt(val);

      if (dayType.includes(val)) {
        if (valueToInt >= 0) {
          this.searchData.startDate = this.$util.getAddDate(this.$util.getDate(''), (valueToInt * -1), '-');
          this.searchData.endDate = this.$util.getDate('-');
        } else {
          this.searchData.startDate = this.$util.getAddDate(this.$util.getDate(''), valueToInt, '-');
          this.searchData.endDate = this.$util.getAddDate(this.$util.getDate(''), valueToInt, '-');
        }
      } else if (monthType.includes(val)) {
        this.searchData.startDate = this.$util.getAddMonth(this.$util.getDate(''), (valueToInt * -1), '-');
        this.searchData.endDate = this.$util.getDate('-');
      }
    }
  }
}
</script>
