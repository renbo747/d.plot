<template>
    <!-- 컨텐츠 영역 -->
    <div class="content m-leftmenu">
      <!-- 공지사항 상세 팝업 -->
      <NoticeDetail
          ref="refDetail"
          v-if="isDetailShow"
          :idx="isDetailIdx"
      />

      <!-- 공지사항 등록 팝업 -->
      <NoticeNewPost
          ref="refNewPost"
          v-if="isNewPostShow"
      />
      <common-navigator></common-navigator>
      <div class="inner">
        <div class="boxing search-area">
          <dl>
            <dt>직접검색</dt>
            <dd>
              <select v-model="searchData.skey">
                <option value="">전체</option>
                <option value="subject">제목</option>
                <option value="writer">작성자</option>
                <option value="content">내용</option>
              </select>
              <input type="text" v-model="searchData.sword" @keyup.enter="onSearch(1)"/>
            </dd>
          </dl>
          <dl>
            <dt>등록일자</dt>
            <dd>
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
          <dl v-if="searchData.isAdmin">
            <dt>사용여부</dt>
            <dd>
              <div class="radio_wrap">
                <input type="radio" name="status" id="rd44" value="" v-model="searchData.istrash"/><label
                  for="rd44">전체</label>
                <input type="radio" name="status" id="rd22" value="F" v-model="searchData.istrash"/><label
                  for="rd22">사용</label>
                <input type="radio" name="status" id="rd33" value="T" v-model="searchData.istrash"/><label
                  for="rd33">미사용</label>
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
            <span class="total">조회결과</span>
          </div>
          <div class="btn-group fr">
            <button type="button" class="btn blue-line" @click="onChangeTrash('F', '사용')" v-if="isWrite && searchData.isAdmin">사용</button>
            <button type="button" class="btn red-line" @click="onChangeTrash('T', '미사용')" v-if="isWrite && searchData.isAdmin">미사용</button>
            <select v-model="pagingData.pageCount" v-if="isRead">
              <option value="20">20개씩 보기</option>
              <option value="50">50개씩 보기</option>
              <option value="100">100개씩 보기</option>
            </select>
          </div>
        </div>
        <table cellpadding="0" cellspacing="0" class="data-tb align-c">
          <caption>파트너사 공지사항</caption>
          <colgroup>
            <col v-if="searchData.isAdmin" width="4%"/><!-- checkbox -->
            <col width="5%"/><!-- No -->
            <col width="10%"/><!-- 구분 -->
            <col width=""/><!-- 제목 -->
            <col v-if="searchData.isAdmin" width="7%"/><!-- 사용여부 -->
            <col width="10%"/><!-- 작성자 -->
            <col width="12%"/><!-- 등록일 -->
            <col width="7%"/><!-- 조회수 -->
          </colgroup>
          <thead>
          <tr>
            <th v-if="searchData.isAdmin"><input type="checkbox" id="chkall" v-model="isChecked" @change="onCheckAll($event.target.checked)"/>
            </th>
            <th>No</th>
            <th>구분
              <button type="button" v-if="isRead"
                      :value="sortData.isessnotice"
                      class="sort"
                      :class="[{up : sortData.isessnotice === 'isessnotice_asc'}, {down : sortData.isessnotice === 'isessnotice_desc'}]"
                      @click="sortToggle(sortData.isessnotice)"></button>
            </th>
            <th>제목</th>
            <th v-if="searchData.isAdmin">사용여부</th>
            <th>작성자
              <button type="button" v-if="isRead"
                      :value="sortData.writer" class="sort" 
                      :class="[{up : sortData.writer === 'writer_asc'}, {down : sortData.writer === 'writer_desc'}]"
                      @click="sortToggle(sortData.writer)"></button>
            </th>
            <th>등록일
              <button type="button" v-if="isRead"
                      :value="sortData.regdate" class="sort"
                      :class="[{up : sortData.regdate === 'regdate_asc'}, {down : sortData.regdate === 'regdate_desc'}]"
                      @click="sortToggle(sortData.regdate)"></button>
            </th>
            <th>조회수
              <button type="button" v-if="isRead"
                      :value="sortData.hits" class="sort"
                      :class="[{up : sortData.hits === 'hits_asc'}, {down : sortData.hits === 'hits_desc'}]"
                      @click="sortToggle(sortData.hits)"></button>
            </th>
          </tr>
          </thead>
          <tbody v-if="listData.length > 0">
          <tr v-for="(row,  i) in listData" :key="i">
            <td v-if="searchData.isAdmin"><input type="checkbox" v-model="listCheckData" :value="row.idx" id="chk10"/></td>
            <td>{{ row.rn }}</td>
            <td>{{ row.isessnotice }}</td>
            <td class="left"><a class="link" @click="goDetail(row.idx)"
                                style="cursor:pointer;">{{ row.subject }}</a></td>
            <td v-if="searchData.isAdmin">{{ row.istrash }}</td>
            <td>{{ row.writer }}</td>
            <td>{{ row.regdate }}</td>
            <td>{{ row.hits }}</td>
          </tr>
          </tbody>
          <tbody v-else>
          <tr>
            <td :colspan="searchData.isAdmin ? 8 : 6">조회 결과가 존재하지 않습니다.</td>
          </tr>
          </tbody>
        </table>
        <div class="bottom-group">
          <CommonPageNavigator :pagingData="pagingData" v-on:setPagingData="setPagingData"/>
          <div class="btn-group">
            <button type="button" class="btn blue" @click="goNewPost" v-if="isWrite && searchData.isAdmin">공지등록</button>
          </div>
        </div>
      </div>
    </div>
    <!-- /컨텐츠 영역 -->
</template>


<script>
import CommonNavigator from '@views.admin/common/CommonNavigator.vue';
import CommonPageNavigator from "../../common/CommonPageNavigator";
import CommonDatePicker from "@views.admin/common/CommonDatePicker";
import NoticeNewPost from "@views.admin/partners/notice/NoticeNewPost";
import NoticeDetail from "@views.admin/partners/notice/NoticeDetail";

export default {
  name: "admin.partners.notice",
  components: {
    CommonDatePicker,
    CommonPageNavigator,
    CommonNavigator,
    NoticeNewPost,
    NoticeDetail
  },
  data() {
    return {
      searchData: {
        isAdmin: false,
        startDate: '',        // 시작 연도
        endDate: '',          // 끝 연도
        skey: '',             // 검색 조건 (제목 작성자 내용)
        sword: '',            // 검색 단어
        istrash: '',          // 사용 여부
        period: '3',          // 날짜 검색 옵션 (어제,오늘,일주일...)
      },
      pagingData: {
        pageCount: 20,        // 페이징 옵션 (최대수)
        page: 1,              // 현재 페이지
        listCount: 0          // 총 페이지
      },
      sortData: {
        isessnotice: 'isessnotice_desc',
        writer: 'writer_desc',
        regdate: 'regdate_desc',
        hits: 'hits_desc'
      },
      isChecked: false,       // 전체 체크
      listData: [],           // response 데이터 리스트
      listCheckData: [],      // 체크된 데이터 리스트

      isDetailShow: false,    // 상세 팝업 열림 여부
      isDetailIdx : '',       // 상세 클릭 idx

      isNewPostShow: false,   // 등록 팝업 열림 여부

      isRead: false,
      isWrite: false
    }
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
  },

  mounted() {
    // 권한 설정
    this.$http.post('/admin/common/pageAuth/check', {url: this.$options.name, isloading: false}).then(result => {
      this.isRead = (result.data.isread === 'T');
      this.isWrite = (result.data.iswrite === 'T');
      this.searchData.startDate = this.$util.getAddMonth(this.$util.getDate(''), -3, '-');
      this.searchData.endDate = this.$util.getDate('-');
      if (this.isRead) {
        this.searchData.isAdmin = this.$util.isAuthorized('ADMIN_USER');
        this.$util.componentSetSearchParam(this);
        this.onSearch();
      }
    }).catch(error => {
      this.$util.debug(error);
    })
  },

  methods: {
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

    // 상세 팝업 열기
    goDetail(idx) {
      this.isDetailIdx = idx;
      this.isDetailShow = true;
    },

    // 등록 팝업 열기
    goNewPost() {
      this.isNewPostShow = true;
    },

    // 사용, 미사용 변경
    onChangeTrash(istrash, text) {
      if (this.listCheckData.length === 0) { // 체크된 데이터가 없다면
        alert("체크된 리스트가 없습니다.");
      } else {
        let params = {
          idxList: this.listCheckData,
          istrash: istrash
        }

        if (confirm("선택항목을 " + text + "하시겠습니까?")) {
          this.$http.post("/admin/partners/notice/update/istrash", params)
              .then(result => {
                if (result.statusCode == 200) {
                  alert("저장이 완료되었습니다.");
                  this.onCheckAll();
                  this.onSearch();
                } else {
                  alert("저장에 실패했습니다.");
                }
              })
              .catch(error => {
                this.$util.debug(error);
              })
        }
      }
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

    // 검색
    onSearch(page) {
      this.isChecked = false;

      let param = this.searchData;
      param.pageCount = this.pagingData.pageCount;
      param.page = (typeof page === 'undefined') ? this.pagingData.page : 1;
      param.listCount = this.pagingData.listCount;

      this.$http.post("/admin/partners/notice/search", param)
          .then(result => {
            let data = result.data;
            this.listData = data.list;
            this.pagingData.listCount = data.listcount;

            if(typeof this.$route.params.type !== 'undefined' && this.$route.params.type === 'LINK'){
              this.goDetail(this.$route.params.idx);
            }
          })
          .catch(error => {
            this.$util.debug(error);
          });
    },

    onSearchDataReset() {
      this.searchData.skey = '';
      this.searchData.sword = '';
      this.searchData.isdealer = '';
      this.searchData.dealerno = '';
      this.searchData.period = '3';
      this.searchData.isTrash = '';
      this.searchData.startDate = this.$util.getAddMonth(this.$util.getDate(''), -3, '-');
      this.searchData.endDate = this.$util.getDate('-');
    },

    // 날짜 picker 콜백 함수
    onChangeEndDate(val) {
      this.searchData.endDate = val;
    },

    // 날짜 picker 콜백 함수
    onChangeStartDate(val) {
      this.searchData.startDate = val;
    },
  }
}
</script>
