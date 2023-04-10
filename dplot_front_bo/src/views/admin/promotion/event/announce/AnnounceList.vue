<template>
  <div>
    <!-- 이벤트 발표 등록 팝업 -->
    <AnnounceNewPost
        v-if="isNewPostShow"
        @close="closeNewPostPopup"
    />
    <!-- /이벤트 발표 등록 팝업 -->

    <!-- 이벤트 발표 상세 팝업 -->
    <AnnounceDetail
        v-if="isDetailShow"
        :idx="detailIdx"
        @close="closeDetailPopup"
    />
    <!-- /이벤트 발표 상세 팝업 -->

    <!-- 컨텐츠 영역 -->
    <div class="content m-leftmenu">
      <AdminCommonNavigator/>
      <div class="inner">
        <div class="clearfix">
          <div class="bar-title fl">이벤트발표 관리</div>
        </div>
        <div class="boxing search-area">
          <dl>
            <dt>직접검색</dt>
            <dd>
              <select v-model="searchData.skey">
                <option value="">전체</option>
                <option value="announcesubject">당첨자발표제목</option>
                <option value="eventsubject">이벤트제목</option>
                <option value="writer">작성자명</option>
              </select>
              <input type="text" v-model="searchData.sword" @keyup.enter="onSearch(1)"/>
            </dd>
          </dl>
          <dl>
            <dt>조회기간</dt>
            <dd>
              <select v-model="searchData.sdate">
                <option value="" selected>전체</option>
                <option value="poststtime" selected>게시일자</option>
                <option value="regdate">등록일자</option>
              </select>
              <CommonDatePicker :value="searchData.startDate" @change="onChangeStartDate"/>
              <span>-</span>
              <CommonDatePicker :value="searchData.endDate" @change="onChangeEndDate"/>
              <div class="radio_wrap">
                <input type="radio" v-model="searchData.period" id="rd111" value='-1'/><label
                  for="rd111">어제</label>
                <input type="radio" v-model="searchData.period" id="rd222" value='0'/><label
                  for="rd222">오늘</label>
                <input type="radio" v-model="searchData.period" id="rd333" value='7'/><label
                  for="rd333">일주일</label>
                <input type="radio" v-model="searchData.period" id="rd444" value='1'/><label
                  for="rd444">1개월</label>
                <input type="radio" v-model="searchData.period" id="rd555" value='3'/><label
                  for="rd555">3개월</label>
                <input type="radio" v-model="searchData.period" id="rd666" value='6'/><label
                  for="rd666">6개월</label>
              </div>
            </dd>
          </dl>
        </div>
        <div class="btn-group" v-if="isRead">
          <button type="button" class="btn big blue" @click="onSearch(1)">검색</button>
          <button type="button" class="btn big gray" @click="onSearchDataReset">초기화</button>
        </div>
        <div class="caption-group mt10 clearfix">
          <div class="total-group fl">
            <span class="total">전체 <strong>{{ pagingData.listCount }}</strong>건</span>
          </div>
          <div class="btn-group fr">
            <button type="button" class="btn green-line" @click="onExcelDownload" v-if="isRead">
              <i class="icon-excel"></i>엑셀다운로드
            </button>
            <select v-model="pagingData.pageCount" v-show="isRead">
              <option value="20">20개씩 보기</option>
              <option value="50">50개씩 보기</option>
              <option value="100">100개씩 보기</option>
            </select>
          </div>
        </div>
        <table cellpadding="0" cellspacing="0" class="data-tb align-c">
          <caption>이벤트발표</caption>
          <colgroup>
            <col width="3%"/><!-- checkbox -->
            <col width="4%"/><!-- No -->
            <col width=""/><!-- 당첨자발표제목 -->
            <col width="25%"/><!-- 이벤트명 -->
            <col width="6%"/><!-- 당첨자 -->
            <col width="6%"/><!-- 조회 -->
            <col width="8%"/><!-- 작성자명 -->
            <col width="6%"/><!-- 예약여부 -->
            <col width="10%"/><!-- 게시일자 -->
            <col width="10%"/><!-- 등록일자 -->
          </colgroup>
          <thead>
          <tr>
            <th><input type="checkbox" id="chkall" v-model="isChecked" @change="onCheckAll($event.target.checked)"/>
            <th>No</th>
            <th>당첨자발표제목</th>
            <th>이벤트명</th>
            <th>당첨자수
              <button type="button"
                      :value="sortData.count"
                      class="sort"
                      :class="[{up : sortData.count === 'count_asc'}, {down : sortData.count === 'count_desc'}]"
                      @click="sortToggle(sortData.count)"></button>
            </th>
            <th>조회
              <button type="button"
                      :value="sortData.hits"
                      class="sort"
                      :class="[{up : sortData.hits === 'hits_asc'}, {down : sortData.hits === 'hits_desc'}]"
                      @click="sortToggle(sortData.hits)"></button>
            </th>
            <th>작성자명</th>
            <th>예약여부</th>
            <th>게시일자
              <button type="button"
                      :value="sortData.poststtime"
                      class="sort"
                      :class="[{up : sortData.poststtime === 'poststtime_asc'}, {down : sortData.poststtime === 'poststtime_desc'}]"
                      @click="sortToggle(sortData.poststtime)"></button>
            </th>
            <th>등록일자
              <button type="button"
                      :value="sortData.regdate"
                      class="sort"
                      :class="[{up : sortData.regdate === 'regdate_asc'}, {down : sortData.regdate === 'regdate_desc'}]"
                      @click="sortToggle(sortData.regdate)"></button>
            </th>
          </tr>
          </thead>
          <tbody v-if="listData.length > 0">
          <tr v-for="(row, i) in listData" :key="i">
            <td><input type="checkbox" :id="'chk0' + i" :value="row.idx" v-model="listCheckData"/></td>
            <td>{{ $util.addZero(i + 1) }}</td>
            <td class="left"><a class="link" @click="goDetail(row.idx)">{{ row.announcesubject }}</a></td>
            <td class="left">{{ row.eventsubject }}</td>
            <td>{{ row.count }}</td>
            <td>{{ row.hits }}</td>
            <td>{{ row.writer }}</td>
            <td>{{ row.isrightnow }}</td>
            <td>{{ row.poststtime }}</td>
            <td>{{ row.regdate }}</td>
          </tr>
          </tbody>
          <tbody v-else>
          <tr>
            <td colspan="16">조회 결과가 존재하지 않습니다.</td>
          </tr>
          </tbody>
        </table>
        <div class="bottom-group">
          <CommonPageNavigator :pagingData="pagingData" v-on:setPagingData="setPagingData"/>
          <div class="btn-group">
            <button type="button" class="btn blue" @click="goNewPost" v-if="isWrite">당첨자발표 등록</button>
          </div>
        </div>
      </div>
    </div>
    <!-- /컨텐츠 영역 -->
  </div>
</template>

<script>
import AdminCommonNavigator from "@views.admin/common/CommonNavigator";
import AnnounceDetail from "@views.admin/promotion/event/announce/AnnounceDetail";
import AnnounceNewPost from "@views.admin/promotion/event/announce/AnnounceNewPost";
import CommonPageNavigator from "@views.admin/common/CommonPageNavigator";
import CommonDatePicker from "@views.admin/common/CommonDatePicker";

export default {
  name: "admin.promotion.event.announce",
  components: {CommonDatePicker, CommonPageNavigator, AdminCommonNavigator, AnnounceDetail, AnnounceNewPost},
  data() {
    return {
      isNewPostShow: false,
      isDetailShow: false,
      isRead: false,
      isWrite: false,
      detailIdx: '',
      listData: [],
      searchData: {
        sdate: '',            // 조회기간 (게시일자, 등록일자)
        startDate: '',                  // 시작 연도
        endDate: '',                    // 끝 연도
        skey: '',        // 검색 조건 (당첨자발표제목, 이벤트제목, 작성자명)
        sword: '',                      // 검색 단어
        period: '3',                    // 날짜 검색 옵션 (어제,오늘,일주일...)
      },
      sortData: {
        count: 'count_desc',                // 당첨자수
        hits: 'hits_desc',                  // 조회수
        poststtime: 'poststtime_desc',      // 게시일자
        regdate: 'regdate_desc',            // 등록일자
      },
      pagingData: {
        pageCount: 20,        // 페이징 옵션(최대수)
        page: 1,              // 현재 페이지
        listCount: 0          // 전체 수
      },
      listCheckData: [],          // 체크 리스트 데이터
      isChecked: false,           // 전체체크
    }
  },
  methods: {
    ///////////////////////////////////내부 사용 메서드////////////////////////////////////////
    // 페이지 초기화
    onInit() {
      this.onSearch();
    },

    // 검색
    onSearch() {
      let params = JSON.parse(JSON.stringify(this.searchData));
      params.pageCount = this.pagingData.pageCount;
      params.page = (typeof page === 'undefined') ? this.pagingData.page : 1;
      params.listCount = this.pagingData.listCount;

      this.$http.post("/admin/promotion/event/announce/search", params)
          .then(result => {
            if (result.statusCode === 200) {
              let data = result.data;
              this.listData = data.list;
              this.pagingData.listCount = data.listcount.total;
            }
          })
          .catch(error => {
            this.$util.debug(error);
          });
    },

    // 초기화
    onSearchDataReset() {
      this.searchData.period = '3';
      this.searchData.sdate = '';
      this.searchData.skey = '';
      this.searchData.sword = '';
    },

    // 엑셀다운로드
    onExcelDownload() {
      let param = {
        list: this.listData
      };
      let postConfig = {responseType: 'arraybuffer'};
      this.$http.post("/admin/promotion/event/announce/excel", param, postConfig)
          .then(result => {
            this.$util.debug(result);
          })
          .catch(error => {
            this.$util.debug(error);
          });
    },

    // 페이징 콜백 함수
    setPagingData(param) {
      this.pagingData = param;
      this.onSearch();
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
    ///////////////////////////////////////////////////////////////////////////////////////////

    ///////////////////////////////////외부, 콜백 메서드///////////////////////////////////////
    // 날짜 picker 콜백 함수
    onChangeStartDate(val) {
      this.searchData.startDate = val;
    },

    // 날짜 picker 콜백 함수
    onChangeEndDate(val) {
      this.searchData.endDate = val;
    },
    ///////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////팝업 메서드////////////////////////////////////////////
    // 등록 팝업 열기
    goNewPost() {
      this.isNewPostShow = true;
    },

    // 상세 팝업 열기
    goDetail(idx) {
      this.detailIdx = idx;
      this.isDetailShow = true;
    },

    // 등록 팝업 닫기
    closeNewPostPopup() {
      this.isNewPostShow = false;
      if (this.isRead) {
        this.onSearch();
      }
    },

    // 상세 팝업 닫기
    closeDetailPopup() {
      this.isDetailShow = false;
      if (this.isRead) {
        this.onSearch();
      }
    },
    ///////////////////////////////////////////////////////////////////////////////////////////
  },
  mounted() {
    // 권한 설정
    this.$http.post('/admin/common/pageAuth/check', {url: this.$options.name, isloading: false}).then(result => {
      this.isRead = (result.data.isread === 'T');
      this.isWrite = (result.data.iswrite === 'T');
      this.searchData.startDate = this.$util.getAddMonth(this.$util.getDate(''), -3, '-');
      this.searchData.endDate = this.$util.getDate('-');
      if (this.isRead) {
        this.$util.componentSetSearchParam(this);
        this.onInit();
      }
    }).catch(error => {
      this.$util.debug(error);
    })
  },
  watch: {
    // 조회기간
    'searchData.period'(val) {
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
