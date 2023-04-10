<template>
  <!-- 컨텐츠 영역 -->
  <div class="content m-leftmenu">
    
    <!-- 파트너사 동의현황 상세 팝업 -->
    <ConsentDetail
        ref="refDetail"
        v-if="isDetailShow"
        :postst-list="poststList"
        :idx="isDetailIdx"
    />

    <!-- 파트너사 동의현황 등록 팝업 -->
    <ConsentNewPost
        ref="refNewPost"
        v-if="isNewPostShow"
        :postst-list="poststList"
    />

    <!-- 파트너사 동의 목록 팝업   -->
    <PartnersAgreeListPopup ref="refAgreePopup" v-show="isAgreePopupShow"/>

    <AdminCommonNavigator/>
    <div class="inner">
      <div class="boxing search-area">
        <dl>
          <dt>직접검색</dt>
          <dd>
            <select v-model="searchData.skey">
              <option value="subject">제목</option>
              <option value="writer">작성자</option>
            </select>
            <input type="text" v-model="searchData.sword" @keyup.enter="onSearch(1)"/>
          </dd>
        </dl>
        <dl>
          <dt>조회기간</dt>
          <dd>
            <select v-model="searchData.sdate">
              <option value="start">시작일</option>
              <option value="end">종료일</option>
            </select>
            <CommonDatePicker :value="searchData.startDate" @change="onChangeStartDate"/>
            <span>-</span>
            <CommonDatePicker :value="searchData.endDate" @change="onChangeEndDate"/>
            <div class="radio_wrap">
              <input type="radio" v-model="searchData.period" id="rd00" value='-1'/><label
                for="rd00">어제</label>
              <input type="radio" v-model="searchData.period" id="rd99" value='0'/><label
                for="rd99">오늘</label>
              <input type="radio" v-model="searchData.period" id="rd88" value='7'/><label
                for="rd88">일주일</label>
              <input type="radio" v-model="searchData.period" id="rd77" value='1'/><label
                for="rd77">1개월</label>
              <input type="radio" v-model="searchData.period" id="rd66" value='3'/><label
                for="rd66">3개월</label>
              <input type="radio" v-model="searchData.period" id="rd55" value='6'/><label
                for="rd55">6개월</label>
            </div>
          </dd>
        </dl>
        <dl>
          <dt>상태</dt>
          <dd>
            <div class="radio_wrap">
              <div>
                <input type="radio" id="rds123" value="" v-model="searchData.postst">
                <label for="rds123">전체</label>
              </div>
              <div v-for="(row, i) in poststList" :key="i">
                <input type="radio" :id="'rds' + i" :value="row.cmcode" v-model="searchData.postst">
                <label :for="'rds' + i">{{ row.codename }}</label>
              </div>
            </div>
          </dd>
        </dl>
      </div>
      <div class="btn-group" v-if="isRead">
        <button type="button" class="btn big blue" @click="onSearch">검색</button>
        <button type="button" class="btn big gray" @click="onSearchDataReset">초기화</button>
      </div>
      <div class="caption-group mt10 clearfix">
        <div class="total-group fl">
          <span class="total">전체 <strong>{{ stateList.total }}</strong>건</span>
          <span v-if="searchData.isAdmin">대기 <strong>{{ stateList.wait }}</strong>건</span>
          <span v-if="searchData.isAdmin">진행중 <strong>{{ stateList.ongoing }}</strong>건</span>
          <span v-if="searchData.isAdmin">중지 <strong>{{ stateList.stop }}</strong>건</span>
          <span v-if="searchData.isAdmin">종료 <strong>{{ stateList.end }}</strong>건</span>
        </div>
        <div class="btn-group fr">
          <button type="button" class="btn blue-line" @click="onChangePostst('POS003', '중지')" v-if="isWrite && searchData.isAdmin">중지
          </button>
          <button type="button" class="btn red-line" @click="onChangePostst('POS004', '종료')" v-if="isWrite && searchData.isAdmin">종료
          </button>
          <select v-model="pagingData.pageCount" v-if="isRead">
            <option value="20">20개씩 보기</option>
            <option value="50">50개씩 보기</option>
            <option value="100">100개씩 보기</option>
          </select>
        </div>
      </div>
      <table cellpadding="0" cellspacing="0" class="data-tb align-c">
        <caption>파트너사 동의현황</caption>
        <colgroup>
          <col v-if="searchData.isAdmin" width="4%"/><!-- checkbox -->
          <col width="4%"/><!-- No -->
          <col v-if="searchData.isAdmin" width="7%"/><!-- 파트너사 -->
          <col width=""/><!-- 제목 -->
          <col width="10%"/><!-- 작성자 -->
          <col width="15%"/><!-- 시작일시 -->
          <col width="15%"/><!-- 종료일시 -->
          <col width="7%"/><!-- 상태 -->
          <col v-if="!searchData.isAdmin" width="7%"/><!-- 동의여부 -->
          <col v-if="searchData.isAdmin" width="7%"/><!-- 동의 -->
          <col v-if="searchData.isAdmin" width="7%"/><!-- 미동의 -->
        </colgroup>
        <thead>
        <tr>
          <th v-if="searchData.isAdmin"><input type="checkbox" id="chkall" v-model="isChecked" @change="onCheckAll($event.target.checked)"/>
          <th>No</th>
          <th v-if="searchData.isAdmin">파트너사</th>
          <th>제목</th>
          <th>작성자
            <button type="button" v-if="isRead"
                    :value="sortData.writer"
                    class="sort"
                    :class="[{up : sortData.writer === 'writer_asc'}, {down : sortData.writer === 'writer_desc'}]"
                    @click="sortToggle(sortData.writer)">
            </button>
          </th>
          <th>시작일시
            <button type="button" v-if="isRead"
                    :value="sortData.startdate"
                    class="sort"
                    :class="[{up : sortData.startdate === 'startdate_asc'}, {down : sortData.startdate === 'startdate_desc'}]"
                    @click="sortToggle(sortData.startdate)">
            </button>
          </th>
          <th>종료일시
            <button type="button" v-if="isRead"
                    :value="sortData.enddate"
                    class="sort"
                    :class="[{up : sortData.enddate === 'enddate_asc'}, {down : sortData.enddate === 'enddate_desc'}]"
                    @click="sortToggle(sortData.enddate)">
            </button>
          </th>
          <th>상태
            <button type="button" v-if="isRead"
                    :value="sortData.postst"
                    class="sort"
                    :class="[{up : sortData.postst === 'postst_asc'}, {down : sortData.postst === 'postst_desc'}]"
                    @click="sortToggle(sortData.postst)">
            </button>
          </th>
          <th v-if="!searchData.isAdmin">동의여부
            <button type="button" v-if="isRead"
                    :value="sortData.partneragree"
                    class="sort"
                    :class="[{up : sortData.partneragree === 'partneragree_asc'}, {down : sortData.partneragree === 'partneragree_desc'}]"
                    @click="sortToggle(sortData.partneragree)">
            </button>
          </th>
          <th v-if="searchData.isAdmin">동의
            <button type="button" v-if="isRead"
                    :value="sortData.isagree"
                    class="sort"
                    :class="[{up : sortData.isagree === 'isagree_asc'}, {down : sortData.isagree === 'isagree_desc'}]"
                    @click="sortToggle(sortData.isagree)">
            </button>
          </th>
          <th v-if="searchData.isAdmin">미동의
            <button type="button" v-if="isRead"
                    :value="sortData.isnotagree"
                    class="sort"
                    :class="[{up : sortData.isnotagree === 'isnotagree_asc'}, {down : sortData.isnotagree === 'isnotagree_desc'}]"
                    @click="sortToggle(sortData.isnotagree)">
            </button>
          </th>
        </tr>
        </thead>
        <tbody v-if="listData.length > 0">
        <tr v-for="(row, i) in listData" :key="i">
          <td v-if="searchData.isAdmin"><input type="checkbox" v-model="listCheckData" :value="row.idx" id="chk10"/></td>
          <td>{{ row.rn }}</td>
          <td v-if="searchData.isAdmin">{{ row.isallagree }}</td>
          <td class="left"><a class="link" @click="goDetail(row.idx)" style="cursor:pointer;">{{ row.subject }}</a>
          </td>
          <td>{{ row.writer }}</td>
          <td>{{ row.poststtime }}</td>
          <td>{{ row.postedtime }}</td>
          <td>{{ row.postst }}</td>
          <td v-if="!searchData.isAdmin">{{ row.consent === 1 ? '동의' : row.unconsent === 1 ? '미동의' : '' }}</td>
          <td v-if="searchData.isAdmin"><a class="link" @click="goAgree(row.idx, 'T')" style="cursor:pointer;">{{ row.consent }}</a></td>
          <td v-if="searchData.isAdmin"><a class="link red" @click="goAgree(row.idx, 'F')" style="cursor:pointer;">{{ row.unconsent }}</a></td>
        </tr>
        </tbody>
        <tbody v-else>
        <tr>
          <td :colspan="searchData.isAdmin ? 11 : 7">조회 결과가 존재하지 않습니다.</td>
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
</template>

<script>
import ConsentDetail from "@views.admin/partners/consent/ConsentDetail";
import ConsentNewPost from "@views.admin/partners/consent/ConsentNewPost";
import AdminCommonNavigator from "@views.admin/common/CommonNavigator";
import CommonDatePicker from "@views.admin/common/CommonDatePicker";
import CommonPageNavigator from "@views.admin/common/CommonPageNavigator";
import PartnersAgreeListPopup from "@views.admin/partners/popup/PartnersAgreeListPopup";

export default {
  name: "admin.partners.consent",
  components: {
    PartnersAgreeListPopup,
    CommonPageNavigator,
    CommonDatePicker,
    AdminCommonNavigator,
    ConsentNewPost,
    ConsentDetail
  },
  data() {
    return {
      searchData: {
        isAdmin: false,
        sdate: 'start',                 // 조회기간 ( 시작일, 종료일 )
        startDate: '',                  // 시작 연도
        endDate: '',                    // 끝 연도
        skey: 'subject',                // 검색 조건 (제목 작성자)
        sword: '',                      // 검색 단어
        postst: '',                     // 게시 상태
        period: '3',                    // 날짜 검색 옵션 (어제,오늘,일주일...)
      },
      pagingData: {
        pageCount: 20,                  // 페이징 옵션 (최대수)
        page: 1,                        // 현재 페이지
        listCount: 0                    // 총 페이지
      },
      sortData: {
        writer: 'writer_desc',          // 작성자
        startdate: 'startdate_desc',    // 시작일시
        enddate: 'enddate_desc',        // 종료일시
        postst: 'postst_desc',          // 상태
        isagree: 'isagree_desc',        // 동의
        isnotagree: 'isnotagree_desc',  // 미동의
        partneragree: 'partneragree_desc', // 동의여부
      },
      stateList: {
        total: '',
        ongoing: '',
        stop: '',
        end: ''
      },
      isChecked: false,                 // 전체 체크
      listData: [],                     // response 데이터 리스트
      listCheckData: [],                // 체크된 데이터 리스트
      isDetailShow: false,              // 상세 팝업 열림 여부
      isDetailIdx: '',                  // 상세 팝업 idx
      isNewPostShow: false,             // 등록 팝업 열림 여부
      isAgreePopupShow: false,          // 동의 현황 목록 팝업 열림 여부
      poststList: [],                   // 게시 상태 코드
      isRead: false,
      isWrite: false,
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
        this.onInit();
      }
    }).catch(error => {
      this.$util.debug(error);
    })

  },
  methods: {

    // 현황 팝업 열기
    goAgree(idx, isagree) {
      this.isAgreePopupShow = true;
      this.$refs.refAgreePopup.onOpen(idx, isagree);
    },

    // 현황 팝업 닫기
    goCloseAgreePopup() {
      this.isAgreePopupShow = false;
    },

    // 상세 팝업 닫기
    goCloseDetailPopup(isreload) {
      this.isDetailShow = false;
      if (isreload) {
        this.onSearch();
      }
    },

    // 등록 팝업 닫기
    goCloseNewPostPopup(isreload) {
      this.isNewPostShow = false;
      if (isreload) {
        this.onSearch();
      }
    },
    /////////////////////////////////////////////////////////////////

    ///////////////////////// 팝업 메서드 /////////////////////////////
    // 상세 팝업 열기
    goDetail(idx) {
      this.isDetailShow = true;
      this.isDetailIdx = idx;
      // this.$refs.refDetail.onOpen(idx);
    },

    // 등록 팝업 열기
    goNewPost() {
      this.isNewPostShow = true;
    },

    // commondatepicker 콜백
    onChangeEndDate(val) {
      this.searchData.endDate = val;
    },

    // 중지, 종료 변경
    onChangePostst(postst, text) {
      if (this.listCheckData.length === 0) { // 체크된 데이터가 없다면
        alert("선택항목을 체크하시기 바랍니다.");
      } else { // 데이터 통신
        if (confirm("선택항목을 " + text + "하시겠습니까")) {
          let params = {
            idxList: this.listCheckData,
            postst: postst
          }

          this.$http.post("/admin/partners/consent/update/trash", params)
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
    /////////////////////////////////////////////////////////////////

    //////////////////////// 외부, 콜백 메서드 /////////////////////////
    // commondatepicker 콜백
    onChangeStartDate(val) {
      this.searchData.startDate = val;
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

    // 초기화
    onInit() {
      this.onSearch();
      this.onCommonCode();
    },

    ///////////////////////// 내부 사용 메서드 /////////////////////////
    // 검색
    onSearch(page) {
      this.isChecked = false;

      let param = this.searchData;
      param.pageCount = this.pagingData.pageCount;
      param.page = (typeof page === 'undefined') ? this.pagingData.page : 1;
      param.listCount = this.pagingData.listCount;

      this.$http.post("/admin/partners/consent/search", param)
          .then(result => {
            let data = result.data;
            this.listData = data.list;
            this.pagingData.listCount = data.statelist.total;
            this.stateList = data.statelist
            this.stateList.total = data.statelist.total;
            // this.poststList = data.poststlist;

            // 전체 체크 해제
            this.onCheckAll();

            if(typeof this.$route.params.type !== 'undefined' && this.$route.params.type === 'LINK'){
              this.goDetail(this.$route.params.idx);
            }
          })
          .catch(error => {
            this.$util.debug(error);
          });
    },

    onCommonCode() {
      let cmclassArr = ['POSTST'];
      this.$http.post('/common/code/map/list', {cmclass: cmclassArr, isloading: false})
          .then(result => {
            let data = result.data;
            this.poststList = data.postst;
          })
          .catch(error => {
            this.$util.debug(error);
          });
    },

    onSearchDataReset() {
      this.searchData.skey = 'subject';
      this.searchData.sword = '';
      this.searchData.period = '3';
      this.searchData.postst = '';
      this.searchData.startDate = this.$util.getAddMonth(this.$util.getDate(''), -3, '-');
      this.searchData.endDate = this.$util.getDate('-');
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
    }
    /////////////////////////////////////////////////////////////////

  }
}
</script>
