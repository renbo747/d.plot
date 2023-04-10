<template>
  <div>
    <!-- 출첵 이벤트 등록 -->
    <CheckEventNewPost
        @close="closeNewPostPopup"
        ref="refNewPost"
        :pageCode="pageCode"
        v-if="isNewPostShow"
    />
    <!-- /출첵 이벤트 등록 -->

    <!-- 출첵 이벤트 상세 -->
    <CheckEventDetail
        @close="closeDetailPopup"
        ref="refDetail"
        :pageCode="pageCode"
        :eventIdx="detailIdx"
        v-if="isDetailShow"
    />
    <!-- /출첵 이벤트 상세 -->

    <!-- 컨텐츠 영역 -->
    <div class="content m-leftmenu">
      <AdminCommonNavigator/>
      <div class="inner">
        <div class="clearfix">
          <div class="bar-title fl">출석체크이벤트 관리</div>
        </div>
        <div class="boxing search-area">
          <dl>
            <dt>직접검색</dt>
            <dd>
              <input type="text" v-model="searchData.sword" style="width: 400px" placeholder="이벤트명"/>
            </dd>
          </dl>
          <dl>
            <dt>조회기간</dt>
            <dd>
              <select v-model="searchData.sdate">
                <option value="evsttime">시작일자</option>
                <option value="evedtime">종료일자</option>
                <option value="pubtime">발표일자</option>
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
          <dl>
            <dt>사용여부</dt>
            <dd>
              <div class="radio_wrap wide3">
                <input type="radio" name="group001" id="use01" value="" v-model="searchData.istrash" checked/><label
                  for="use01">전체</label>
                <input type="radio" name="group001" id="use02" value="F" v-model="searchData.istrash"/><label
                  for="use02">사용</label>
                <input type="radio" name="group001" id="use03" value="T" v-model="searchData.istrash"/><label
                  for="use03">미사용</label>
              </div>
            </dd>
          </dl>
          <dl>
            <dt>적용채널</dt>
            <dd>
              <div class="check-wrap">
                <input type="checkbox" id="all1" v-model="checkObj.isallmuappch" true-value="T" false-value="F"
                       @change="checkAllMuAppch($event.target.checked)">
                <label for="all1">전체</label>
              </div>
              <div class="check-wrap" v-for="(row, i) in pageCode.muappchtypecode" :key="i">
                <input type="checkbox" :id="'group1' + i" :value="row.cmcode" v-model="checkObj.muappchTypeChecked">
                <label :for="'group1' + i">{{ row.codename }}</label>
              </div>
            </dd>
          </dl>
          <dl>
            <dt>노출여부</dt>
            <dd>
              <div class="check-wrap">
                <input type="checkbox" id="all2" v-model="checkObj.isalldispt" true-value="T" false-value="F"
                       @change="checkAllDisp($event.target.checked)">
                <label for="all2">전체</label>
              </div>
              <div class="check-wrap" v-for="(row, i) in pageCode.disptypecode" :key="i">
                <input type="checkbox" :id="'group2' + i" :value="row.cmcode" v-model="checkObj.dispTypeChecked">
                <label :for="'group2' + i">{{ row.codename }}</label>
              </div>
            </dd>
          </dl>
          <dl>
            <dt>진행상태</dt>
            <dd>
              <div class="check-wrap">
                <input type="checkbox" id="all3" v-model="checkObj.isallcomplete" true-value="T" false-value="F"
                       @change="checkAllComp($event.target.checked)">
                <label for="all3">전체</label>
              </div>
              <div class="check-wrap" v-for="(row, i) in pageCode.completetypecode" :key="i">
                <input type="checkbox" :id="'group31' + i" :value="row.cmcode" v-model="checkObj.completeChecked">
                <label :for="'group31' + i">{{ row.codename }}</label>
              </div>
            </dd>
          </dl>
          <dl>
            <dt>출석인정기준</dt>
            <dd>
              <div class="radio_wrap wide3">
                <input type="radio" name="group07" id="group71" value="" v-model="searchData.iscontattend"/><label
                  for="group71">전체</label>
                <input type="radio" name="group07" id="group72" value="F" v-model="searchData.iscontattend"/><label
                  for="group72">미연속출석</label>
                <input type="radio" name="group07" id="group73" value="T" v-model="searchData.iscontattend"/><label
                  for="group73">연속출석</label>
              </div>
            </dd>
          </dl>
          <dl>
            <dt>개인정보동의</dt>
            <dd>
              <div class="radio_wrap">
                <input type="radio" name="group4" id="group410" value="" v-model="searchData.isagree" checked/><label
                  for="group410">전체</label>
                <input type="radio" name="group4" id="group420" value="peragree" v-model="searchData.isagree"/><label
                  for="group420">개인정보 수집 및 이용</label>
                <input type="radio" name="group4" id="group430" value="mktagree" v-model="searchData.isagree"/><label
                  for="group430">마케팅 정보 활용</label>
              </div>
            </dd>
          </dl>
          <dl>
            <dt>대상회원유형</dt>
            <dd>
              <div class="check-wrap">
                <input type="checkbox" id="all5" v-model="checkObj.isallmumember" true-value="T" false-value="F"
                       @change="checkAllMuMemer($event.target.checked)">
                <label for="all5">전체</label>
              </div>
              <div class="check-wrap" v-for="(row, i) in pageCode.mumembertypecode" :key="i">
                <input type="checkbox" :id="'group5' + i" :value="row.cmcode" v-model="checkObj.mumemberTypeChecked">
                <label :for="'group5' + i">{{ row.codename }}</label>
              </div>
            </dd>
          </dl>
          <dl>
            <dt>대상회원등급</dt>
            <dd>
              <div class="check-wrap">
                <input type="checkbox" id="all6" v-model="checkObj.isallmumemlv" true-value="T" false-value="F"
                       @change="checkAllMuMemLv($event.target.checked)">
                <label for="all6">전체</label>
              </div>
              <div class="check-wrap" v-for="(row, i) in pageCode.mumemlvtypecode" :key="i">
                <input type="checkbox" :id="'group6' + i" :value="row.cmcode" v-model="checkObj.mumemlvTypeChecked">
                <label :for="'group6' + i">{{ row.codename }}</label>
              </div>
            </dd>
          </dl>
        </div>
        <div class="btn-group" v-if="isRead">
          <button type="button" class="btn big blue" @click="onSearchList">검색</button>
          <button type="button" class="btn big gray" @click="onSearchClear">초기화</button>
        </div>
        <div class="caption-group mt10 clearfix">
          <div class="total-group fl">
            <span class="total">전체 <strong>{{ stateList.total }}</strong>건</span>
            <span>사용 <strong>{{ stateList.use }}</strong>건</span>
            <span>미사용 <strong>{{ stateList.unuse }}</strong>건</span>
          </div>
          <div class="btn-group fr">
            <button type="button" class="btn blue-line" @click="onChangeUse('F')" v-if="isWrite">사용</button>
            <button type="button" class="btn red-line" @click="onChangeUse('T')" v-if="isWrite">미사용</button>
            <button type="button" class="btn green-line" @click="onExcelDownload"><i class="icon-excel"></i>엑셀다운로드
            </button>
            <select v-model="pagingData.pageCount" v-if="isRead">
              <option value="20">20개씩 보기</option>
              <option value="50">50개씩 보기</option>
              <option value="100">100개씩 보기</option>
            </select>
          </div>
        </div>
        <table cellpadding="0" cellspacing="0" class="data-tb align-c">
          <caption>이벤트</caption>
          <colgroup>
            <col width="2%"/><!-- checkbox -->
            <col width=""/><!-- 이벤트명 -->
            <col width="7%"/><!-- 개인정보동의 -->
            <col width="7%"/><!-- 적용채널 -->
            <col width="6%"/><!-- 유형 -->
            <col width="6%"/><!-- 등급 -->
            <col width="4%"/><!-- 조회 -->
            <col width="4%"/><!-- 댓글 -->
            <col width="6%"/><!-- 출석인정기준 -->
            <col width="4%"/><!-- 출석수 -->
            <col width="6%"/><!-- 혜택지급반복 -->
            <col width="7%"/><!-- 시작일자 -->
            <col width="7%"/><!-- 종료일자 -->
            <col width="7%"/><!-- 등록일자 -->
            <col width="5%"/><!-- 사용여부 -->
            <col width="6%"/><!-- 노출여부 -->
            <col width="5%"/><!-- 진행상태 -->
          </colgroup>
          <thead>
          <tr>
            <th><input type="checkbox" id="chkall" v-model="isChecked" @change="onCheckAll($event.target.checked)"/>
            </th>
            <th>이벤트명</th>
            <th>개인정보동의</th>
            <th>적용채널</th>
            <th>유형</th>
            <th>등급</th>
            <th>조회
              <button type="button"
                      :value="sortData.hits"
                      class="sort"
                      :class="[{up : sortData.hits === 'hits_asc'}, {down : sortData.hits === 'hits_desc'}]"
                      @click="sortToggle(sortData.hits)"></button>
            </th>
            <th>댓글
              <button type="button"
                      :value="sortData.commentcount"
                      class="sort"
                      :class="[{up : sortData.commentcount === 'commentcount_asc'}, {down : sortData.commentcount === 'commentcount_desc'}]"
                      @click="sortToggle(sortData.commentcount)"></button>
            </th>
            <th>출석인정기준</th>
            <th>출석수</th>
            <th>혜택지급반복</th>
            <th>시작일자
              <button type="button"
                      :value="sortData.evsttime"
                      class="sort"
                      :class="[{up : sortData.evsttime === 'evsttime_asc'}, {down : sortData.evsttime === 'evsttime_desc'}]"
                      @click="sortToggle(sortData.evsttime)"></button>
            </th>
            <th>종료일자
              <button type="button"
                      :value="sortData.evedtime"
                      class="sort"
                      :class="[{up : sortData.evedtime === 'evedtime_asc'}, {down : sortData.evedtime === 'evedtime_desc'}]"
                      @click="sortToggle(sortData.evedtime)"></button>
            </th>
            <th>등록일자
              <button type="button"
                      :value="sortData.regdate"
                      class="sort"
                      :class="[{up : sortData.regdate === 'regdate_asc'}, {down : sortData.regdate === 'regdate_desc'}]"
                      @click="sortToggle(sortData.regdate)"></button>
            </th>
            <th>사용여부</th>
            <th>노출여부</th>
            <th>진행상태</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="(row, i) in listData" :key="i">
            <td><input type="checkbox" :id="'chk0' + i" v-model="listCheckData" :value="row.eventidx"/></td>
            <td class="left"><a @click="goDetail(row.eventidx)" class="link">{{ row.subject }}</a></td>
            <td>{{ row.isagree }}</td>
            <td>{{ row.muappcode }}</td>
            <td>{{ row.mumembercode }}</td>
            <td>{{ row.mumemlvcode }}</td>
            <td>{{ row.hits }}</td>
            <td>{{ row.commentcount }}</td>
            <td>{{ row.iscontattend }}</td>
            <td>{{ row.winattendcnt }}</td>
            <td>{{ row.benefitcount }}</td>
            <td>{{ row.evsttime }}</td>
            <td>{{ row.evedtime }}</td>
            <td>{{ row.regdate }}</td>
            <td>{{ row.istrash }}</td>
            <td>{{ row.disptype }}</td>
            <td>{{ row.nopubtimecomplete }}</td>
          </tr>
          </tbody>
        </table>
        <div class="bottom-group">
          <CommonPageNavigator :pagingData="pagingData" v-on:setPagingData="setPagingData"/>
          <div class="btn-group">
            <button type="button" class="btn blue" @click="goNewPost" v-if="isWrite">출석체크이벤트 등록</button>
          </div>
        </div>
      </div>
    </div>
    <!-- /컨텐츠 영역 -->
  </div>
</template>

<script>
import CheckEventNewPost from "@views.admin/promotion/event/checkmanage/CheckEventNewPost";
import CheckEventDetail from "@views.admin/promotion/event/checkmanage/CheckEventDetail";
import AdminCommonNavigator from "@views.admin/common/CommonNavigator";
import CommonDatePicker from "@views.admin/common/CommonDatePicker";
import CommonPageNavigator from "@views.admin/common/CommonPageNavigator";

export default {
  name: "admin.promotion.checkevent.manage",
  components: {
    CommonPageNavigator,
    CommonDatePicker,
    AdminCommonNavigator,
    CheckEventNewPost,
    CheckEventDetail
  },
  data() {
    return {
      isNewPostShow: false,             // 등록 팝업
      isDetailShow: false,              // 상세 팝업
      detailIdx: '',                    // 상세 인덱스
      isRead: false,                    // 읽기 권한
      isWrite: false,                   // 쓰기 권한
      pageCode: {                 // 페이지 코드
        disptypecode: {}, // 노출 구분
        muappchtypecode: {}, // 적용 채널
        mumembertypecode: {}, // 대상 회원
        mumemlvtypecode: {}, // 대상 회원 등급
        completetypecode: {}, // 진행 상태
        bettypecode: {},      // 혜택 구분
      },
      stateList: {                      // 상태 리스트
        total: '',
        use: '',
        unuse: '',
      },
      listData: [],               // 리스트 데이터
      checkObj: {
        isallmuappch: 'T',
        muappchTypeChecked: [],          // 적용 채널
        isallmumember: 'T',
        mumemberTypeChecked: [],         // 회원 유형
        isallmumemlv: 'T',
        mumemlvTypeChecked: [],          // 회원 등급
        isalldispt: 'T',
        dispTypeChecked: [],             // 노출 여부
        isallcomplete: 'T',
        completeChecked: [],           // 진행 상태
      },
      searchData: {
        sdate: 'evsttime',                 // 조회기간 ( 시작, 종료일, 발표, 등록 )
        startDate: '',                  // 시작 연도
        endDate: '',                    // 끝 연도
        // skey: '',                       // 검색 조건 (제목 작성자)
        sword: '',                      // 검색 단어
        period: '3',                    // 날짜 검색 옵션 (어제,오늘,일주일...)

        istrash: '',                    // 사용 여부
        muappchtype: '',                // 적용 채널
        disptype: '',                   // 노출 여부
        completetype: '',               // 진행상태
        isagree: '',                    // 개인정보동의
        mumembertype: '',               // 회원 유형
        mumemlvtype: '',                // 회원 등급
        iscontattend: '',               // 출석인정기준

        codename: '출석체크',           // DB 검색용 codename
      },
      sortData: {
        hits: 'hits_desc',                  // 조회수
        commentcount: 'commentcount_desc',  // 댓글
        evsttime: 'evsttime_desc',          // 시작일자
        evedtime: 'evedtime_desc',          // 종료일자
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
    ///////////////////////////////////내부 사용 메서드///////////////////////////////////////////
    onInit() {
      this.onSearchList();
      this.onSearchCode();
      this.setTime();
    },

    // 조회기간 셋팅
    setTime() {
      this.searchData.startDate = this.$util.getAddMonth(this.$util.getDate(''), -3, '-');
      this.searchData.endDate = this.$util.getDate('-');
    },

    // 전체 체크
    onCheckAll(checked) {
      this.listCheckData = [];
      if (checked) { // 전체 체크
        for (let i in this.listData) {
          this.listCheckData[i] = this.listData[i].eventidx;
        }
      }
    },

    // 사용, 미사용 변경
    onChangeUse(isTrash) {
      if (this.listCheckData.length < 1) {
        alert("이벤트를 선택해주세요.");
        return;
      }

      if (confirm("상태를 전환 하시겠습니까?")) {
        let params = {};

        // 사용여부
        params.istrash = isTrash;

        // 체크 리스트
        params.idxlist = this.listCheckData;

        this.$http.post("/admin/promotion/event/update/use", params)
            .then(result => {
              if (result.statusCode === 200) {
                this.listCheckData = [];
                this.onSearchList();
              }
            })
            .catch(error => {
              this.$util.debug(error);
            });
      }
    },

    // 엑셀다운로드
    onExcelDownload() {
      let param = {
        list: this.listData
      };
      let postConfig = {responseType: 'arraybuffer'};
      this.$http.post("/admin/promotion/checkevent/excel", param, postConfig)
          .then(result => {
            this.$util.debug(result);
          })
          .catch(error => {
            this.$util.debug(error);
          });
    },

    // 검색 초기화
    onSearchClear() {
      this.searchData = this.$options.data().searchData;
      this.setCode();
      this.setTime();
    },

    // 리스트 검색
    onSearchList() {
      let params = this.searchData;
      params.pageCount = this.pagingData.pageCount;
      params.page = (typeof page === 'undefined') ? this.pagingData.page : 1;
      params.listCount = this.pagingData.listCount;

      params = Object.assign({}, params, this.checkObj);
      params = Object.assign({}, params, this.sortData);

      this.$http.post("/admin/promotion/checkevent/search", params)
          .then(result => {
            if (result.statusCode === 200) {
              let data = result.data;
              this.listData = data.list;
              this.stateList = data.statelist;
              this.pagingData.listCount = data.statelist.total;
            }
          })
          .catch(error => {
            this.$util.debug(error);
          });
    },

    // 코드값 검색
    onSearchCode() {
      this.$http.post("/admin/promotion/checkevent/code", {})
          .then(result => {
            if (result.statusCode === 200) {
              this.pageCode = result.data;
              this.pageCode.completetypecode = [
                {'cmcode': 'COM001', 'codename': '진행전'},
                {'cmcode': 'COM002', 'codename': '진행중'},
                {'cmcode': 'COM003', 'codename': '종료'}
              ];

              this.setCode();
            }
          })
          .catch(error => {
            this.$util.debug(error);
          });
    },

    // 페이지 코드값 가져오기
    setCode() {
      // 적용 채널
      this.checkObj.muappchTypeChecked = this.getCmCode(this.pageCode.muappchtypecode);
      // 회원 유형
      this.checkObj.mumemberTypeChecked = this.getCmCode(this.pageCode.mumembertypecode);
      // 회원 등급
      this.checkObj.mumemlvTypeChecked = this.getCmCode(this.pageCode.mumemlvtypecode);
      // 노출 여부
      this.checkObj.dispTypeChecked = this.getCmCode(this.pageCode.disptypecode);
      // 진행상태
      this.checkObj.completeChecked = this.getCmCode(this.pageCode.completetypecode);
    },

    // 적용 채널 전체 체크
    checkAllMuAppch(value) {
      if (value) {
        // 적용 채널
        this.checkObj.muappchTypeChecked = this.getCmCode(this.pageCode.muappchtypecode);
      } else {
        this.checkObj.muappchTypeChecked = [];
      }
    },

    // 회원 유형 전체 체크
    checkAllMuMemer(value) {
      if (value) {
        // 적용 채널
        this.checkObj.mumemberTypeChecked = this.getCmCode(this.pageCode.mumembertypecode);
      } else {
        this.checkObj.mumemberTypeChecked = [];
      }
    },

    // 회원 등급 전체 체크
    checkAllMuMemLv(value) {
      if (value) {
        // 적용 채널
        this.checkObj.mumemlvTypeChecked = this.getCmCode(this.pageCode.mumemlvtypecode);
      } else {
        this.checkObj.mumemlvTypeChecked = [];
      }
    },

    // 회원 등급 전체 체크
    checkAllDisp(value) {
      if (value) {
        // 적용 채널
        this.checkObj.dispTypeChecked = this.getCmCode(this.pageCode.disptypecode);
      } else {
        this.checkObj.dispTypeChecked = [];
      }
    },

    // 진행 상태 전체 체크
    checkAllComp(value) {
      if (value) {
        // 적용 채널
        this.checkObj.completeChecked = this.getCmCode(this.pageCode.completetypecode);
      } else {
        this.checkObj.completeChecked = [];
      }
    },

    // codename array get
    getCmCode(codeArr) {
      return codeArr.map(obj => obj.cmcode);
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

      this.onSearchList();
    },
    ///////////////////////////////////////////////////////////////////////////////////////////

    ///////////////////////////////////외부, 콜백 메서드//////////////////////////////////////////
    // 페이징 콜백 함수
    setPagingData(param) {
      this.pagingData = param;
      this.onSearchList();
    },

    // 날짜 picker 콜백 함수
    onChangeStartDate(val) {
      this.searchData.startDate = val;
    },

    // 날짜 picker 콜백 함수
    onChangeEndDate(val) {
      this.searchData.endDate = val;
    },
    ///////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////팝업 메서드//////////////////////////////////////////////
    // 등록 팝업 열기
    goNewPost() {
      this.isNewPostShow = true;
    },

    // 상세 팝업 열기
    goDetail(eventIdx) {
      this.detailIdx = eventIdx;
      this.isDetailShow = true;
    },

    // 등록 팝업 닫기
    closeNewPostPopup() {
      this.isNewPostShow = false;
      if (this.isRead) {
        this.onSearchList();
      }
    },

    // 상세 팝업 닫기
    closeDetailPopup() {
      this.isDetailShow = false;
      if (this.isRead) {
        this.onSearchList();
      }
    },
    ///////////////////////////////////////////////////////////////////////////////////////////
  },
  mounted() {
    // 권한 설정
    this.$http.post('/admin/common/pageAuth/check', {url: this.$options.name, isloading: false}).then(result => {
      this.isRead = (result.data.isread === 'T');
      this.isWrite = (result.data.iswrite === 'T');
      this.setTime();
      this.onSearchCode();
      if (this.isRead) {
        this.$util.componentSetSearchParam(this);
        this.onInit();
      }
    }).catch(error => {
      this.$util.debug(error);
    })
  },
  watch: {
    // 적용 채널 체크 상태 검사
    'checkObj.muappchTypeChecked'(value) {
      if (value.length < this.pageCode.muappchtypecode.length) {
        this.checkObj.isallmuappch = 'F';
      } else {
        this.checkObj.isallmuappch = 'T';
      }
      this.searchData.muappchtype = this.checkObj.muappchTypeChecked.join();
    },

    // 회원 유형 체크 상태 검사
    'checkObj.mumemberTypeChecked'(value) {
      if (value.length < this.pageCode.mumembertypecode.length) {
        this.checkObj.isallmumember = 'F';
      } else {
        this.checkObj.isallmumember = 'T';
      }
      this.searchData.mumembertype = this.checkObj.mumemberTypeChecked.join();
    },

    // 회원 등급 체크 상태 검사
    'checkObj.mumemlvTypeChecked'(value) {
      if (value.length < this.pageCode.mumemlvtypecode.length) {
        this.checkObj.isallmumemlv = 'F';
      } else {
        this.checkObj.isallmumemlv = 'T';
      }
      this.searchData.mumemlvtype = this.checkObj.mumemlvTypeChecked.join();
    },

    // 회원 등급 체크 상태 검사
    'checkObj.dispTypeChecked'(value) {
      if (value.length < this.pageCode.disptypecode.length) {
        this.checkObj.isalldispt = 'F';
      } else {
        this.checkObj.isalldispt = 'T';
      }
      this.searchData.disptype = this.checkObj.dispTypeChecked.join();
    },

    // 회원 등급 체크 상태 검사
    'checkObj.completeChecked'(value) {
      if (value.length < this.pageCode.completetypecode.length) {
        this.checkObj.isallcomplete = 'F';
      } else {
        this.checkObj.isallcomplete = 'T';
      }
      this.searchData.completetype = this.checkObj.completeChecked.join();
    },

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
