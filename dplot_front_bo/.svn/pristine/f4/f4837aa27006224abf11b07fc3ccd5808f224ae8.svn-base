<template>
  <div>
    <!-- 프로모션 등록 -->
    <PromotionRegist
        @close="closeNewPostPopup"
        ref="refNewPost"
        :pageCode="pageCode"
        v-if="isNewPostShow"
    />

    <!-- 프로모션 상세 -->
    <PromotionDetail
        @close="closeDetailPopup"
        ref="refDetail"
        :pageCode="pageCode"
        v-bind:eventIdx="detailIdx"
        v-if="isDetailShow"
    />

    <!-- 프로모션 발표 상세 팝업 -->
    <AnnounceDetail
        v-if="isAnnounceShow"
        :idx="announceIdx"
        @close="closeEventDetailPopup"
    />
    <!-- /프로모션 발표 상세 팝업 -->

    <!-- 컨텐츠 영역 -->
    <div class="content m-leftmenu">
      <common-navigator></common-navigator>
      <div class="inner">
        <div class="boxing search-area">
          <dl>
            <dt>직접검색</dt>
            <dd>
              <input type="text" v-model="searchData.sword" style="width: 400px" placeholder="제목" @keyup.enter="onSearchList(1)"/>
            </dd>
          </dl>
          <dl>
            <dt>조회기간</dt>
            <dd>
              <select v-model="searchData.sdate">
                <option value="">전체</option>
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
            <dt>노출상태</dt>
            <dd>
              <div class="radio_wrap wide3">
                <input type="radio" name="group001" id="use01" value="" v-model="searchData.isdisplay" checked/><label
                  for="use01">전체</label>
                <input type="radio" name="group001" id="use02" value="T" v-model="searchData.isdisplay"/><label
                  for="use02">노출</label>
                <input type="radio" name="group001" id="use03" value="F" v-model="searchData.isdisplay"/><label
                  for="use03">미노출</label>
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
              <div class="check-wrap" v-for="(row, i) in pageCode.muappchtype" :key="i">
                <input type="checkbox" :id="'group1' + i" :value="row.cmcode" v-model="checkObj.muappchTypeChecked">
                <label :for="'group1' + i">{{ row.codename }}</label>
              </div>
            </dd>
          </dl>
          <dl>
            <dt>전시여부</dt>
            <dd>
              <div class="check-wrap">
                <input type="checkbox" id="all2" v-model="checkObj.isalldispt" true-value="T" false-value="F"
                       @change="checkAllDisp($event.target.checked)">
                <label for="all2">전체</label>
              </div>
              <div class="check-wrap" v-for="(row, i) in pageCode.disptype" :key="i">
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
              <div class="check-wrap" v-for="(row, i) in pageCode.completetype" :key="i">
                <input type="checkbox" :id="'group31' + i" :value="row.cmcode" v-model="checkObj.completeChecked">
                <label :for="'group31' + i">{{ row.codename }}</label>
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
              <div class="check-wrap" v-for="(row, i) in pageCode.mumembertype" :key="i">
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
              <div class="check-wrap" v-for="(row, i) in pageCode.mumemlvtype" :key="i">
                <input type="checkbox" :id="'group6' + i" :value="row.cmcode" v-model="checkObj.mumemlvTypeChecked">
                <label :for="'group6' + i">{{ row.codename }}</label>
              </div>
            </dd>
          </dl>
        </div>
        <div class="btn-group" v-if="isRead">
          <button type="button" class="btn big blue" @click="onSearchList(1)">검색</button>
          <button type="button" class="btn big gray" @click="onSearchClear">초기화</button>
        </div>
        <div class="caption-group mt10 clearfix">
          <div class="total-group fl">
            <span class="total">전체 <strong>{{ stateList.total }}</strong>건</span>
            <span>노출 <strong>{{ stateList.showcnt }}</strong>건</span>
            <span>미노출 <strong>{{ stateList.hidecnt }}</strong>건</span>
          </div>
          <div class="btn-group fr">
            <button type="button" class="btn blue-line" @click="onChangeUse('T')" v-if="isWrite">노출</button>
            <button type="button" class="btn red-line" @click="onChangeUse('F')" v-show="isWrite">미노출</button>
            <button type="button" v-if="isWrite" class="btn black-line" @click="deleteList">목록삭제</button>
            <button type="button" class="btn green-line" @click="onExcelDownload" v-if="isRead">
              <i class="icon-excel"></i>엑셀다운로드
            </button>
            <select v-model="pagingData.pageCount" v-if="isRead">
              <option value="20">20개씩 보기</option>
              <option value="50">50개씩 보기</option>
              <option value="100">100개씩 보기</option>
            </select>
          </div>
        </div>
        <table cellpadding="0" cellspacing="0" class="data-tb align-c">
          <caption>프로모션관리</caption>
          <colgroup>
            <col width="3%" /><!-- checkbox -->
            <col width="" /><!-- 제목 -->
            <col width="7%" /><!-- 적용채널 -->
            <col width="7%" /><!-- 유형 -->
            <col width="7%" /><!-- 등급 -->
            <col width="5%" /><!-- 조회 -->
            <col width="5%" /><!-- 댓글 -->                   
            <col width="7%" /><!-- 시작일자 -->
            <col width="7%" /><!-- 종료일자 -->
            <col width="7%" /><!-- 발표일자 -->
            <col width="7%" /><!-- 등록일자 -->
            <col width="5%" /><!-- 노출상태 -->
            <col width="6%" /><!-- 노출여부 -->                    
            <col width="5%" /><!-- 진행상태 -->
            <col width="5%" /><!-- 당첨자발표 -->
          </colgroup>
          <thead>
          <tr>
            <th><input type="checkbox" id="chkall" v-model="isChecked" @change="onCheckAll($event.target.checked)"/>
            <th>제목</th>
            <th>적용채널</th>
            <th>유형</th>  
            <th>등급</th>
            <th>조회
              <button type="button" v-if="isRead"
                      :value="sortData.hits"
                      class="sort"
                      :class="[{up : sortData.hits === 'hits_asc'}, {down : sortData.hits === 'hits_desc'}]"
                      @click="sortToggle(sortData.hits)"></button>
            </th>
            <th>댓글
              <button type="button" v-if="isRead"
                      :value="sortData.commentcount"
                      class="sort"
                      :class="[{up : sortData.commentcount === 'commentcount_asc'}, {down : sortData.commentcount === 'commentcount_desc'}]"
                      @click="sortToggle(sortData.commentcount)"></button>
            </th>
            <th>시작일자
              <button type="button" v-if="isRead"
                      :value="sortData.evsttime"
                      class="sort"
                      :class="[{up : sortData.evsttime === 'evsttime_asc'}, {down : sortData.evsttime === 'evsttime_desc'}]"
                      @click="sortToggle(sortData.evsttime)"></button>
            </th>
            <th>종료일자
              <button type="button" v-if="isRead"
                      :value="sortData.evedtime"
                      class="sort"
                      :class="[{up : sortData.evedtime === 'evedtime_asc'}, {down : sortData.evedtime === 'evedtime_desc'}]"
                      @click="sortToggle(sortData.evedtime)"></button>
            </th>
            <th>발표일자
              <button type="button" v-if="isRead"
                      :value="sortData.pubtime"
                      class="sort"
                      :class="[{up : sortData.pubtime === 'pubtime_asc'}, {down : sortData.pubtime === 'pubtime_desc'}]"
                      @click="sortToggle(sortData.pubtime)"></button>
            </th>
            <th>등록일자
              <button type="button" v-if="isRead"
                      :value="sortData.regdate"
                      class="sort"
                      :class="[{up : sortData.regdate === 'regdate_asc'}, {down : sortData.regdate === 'regdate_desc'}]"
                      @click="sortToggle(sortData.regdate)"></button>
            </th>
            <th>노출상태</th>
            <th>전시여부</th>
            <th>진행상태</th>
            <th>당첨자발표</th>
          </tr>
          </thead>
          <tbody v-if="listData.length > 0">
          <tr v-for="(row, i) in listData" :key="i">
            <td><input type="checkbox" :id="'chk0' + i" v-model="listCheckData" :value="row.eventidx" @change="changeCheck"/></td>
            <td class="left"><a class="link" @click="goDetail(row.eventidx)">{{ row.subject }}</a></td>
            <td>{{ row.muappcode }}</td>
            <td>{{ row.mumembercode }}</td>
            <td>{{ row.mumemlvcode }}</td>
            <td>{{ row.hits }}</td>
            <td>{{ row.commentcount == null ? '-' : row.commentcount }}</td>
            <td>{{ row.evsttime }}</td>
            <td>{{ row.evedtime }}</td>
            <td>{{ row.pubtime }}</td>
            <td>{{ row.regdate }}</td>
            <td>{{ row.isdisplay }}</td>
            <td>{{ row.disptype }}</td>
            <td>{{ row.iscomplete }}</td>
            <td v-if="row.isannounce === 'Y'"><a class="link" @click="goEventDetail(row.postidx)">
              {{ row.isannounce }}</a>
            </td>
            <td v-if="row.isannounce === 'N'">{{ row.isannounce }}</td>
          </tr>
          </tbody>
          <tbody v-else>
          <tr>
            <td colspan="15">조회 결과가 존재하지 않습니다.</td>
          </tr>
          </tbody>
        </table>
        <div class="bottom-group">
          <CommonPageNavigator :pagingData="pagingData" v-on:setPagingData="setPagingData"/>
          <div class="btn-group">
            <button type="button" class="btn blue" @click="goNewPost" v-show="isWrite">프로모션 등록</button>
          </div>
        </div>
      </div>
    </div>
    <!-- /컨텐츠 영역 -->
  </div>
</template>

<script>
import PromotionRegist from "@views.admin/operation/shopping/promotion/manage/PromotionRegist";
import PromotionDetail from "@views.admin/operation/shopping/promotion/manage/PromotionDetail";
import CommonNavigator from '@views.admin/common/CommonNavigator.vue';
import CommonDatePicker from "@views.admin/common/CommonDatePicker";
import CommonPageNavigator from "@views.admin/common/CommonPageNavigator";
import AnnounceDetail from "@views.admin/operation/shopping/promotion/announce/AnnounceDetail";

export default {
  name: "admin.operation.shopping.promotion.managelist",
  components: {
    CommonPageNavigator,
    CommonDatePicker,
    PromotionDetail,
    PromotionRegist,
    CommonNavigator,
    AnnounceDetail
  },
  data() {
    return {
      isNewPostShow: false,             // 등록 팝업

      isDetailShow: false,              // 상세 팝업
      detailIdx: '',                    // 상세 인덱스

      announceIdx: '',                  // 발표 상세 인덱스
      isAnnounceShow: false,            // 발표 팝업

      isRead: false,                    // 읽기
      isWrite: false,                   // 쓰기
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
      pageCode: {                 // 페이지 코드
        disptype: {}, // 노출 구분
        muappchtype: {}, // 적용 채널
        mumembertype: {}, // 대상 회원
        mumemlvtype: {}, // 대상 회원 등급
        completetype: {}, // 진행 상태
      },
      listData: [],               // 리스트 데이터
      stateList: {                      // 상태 리스트
        total: '',
        showcnt: '',
        hidecnt: '',
      },
      searchData: {
        sdate: '',                 // 조회기간 ( 시작, 종료일, 발표, 등록 )
        startDate: '',                  // 시작 연도
        endDate: '',                    // 끝 연도
        // skey: '',                       // 검색 조건 (제목 작성자)
        sword: '',                      // 검색 단어
        period: '3',                    // 날짜 검색 옵션 (어제,오늘,일주일...)

        isdisplay: '',                    // 노출상태
        muappchtype: '',                // 적용 채널
        disptype: '',                   // 전시 여부
        completetype: '',               // 진행상태
        mumembertype: '',               // 회원 유형
        mumemlvtype: '',                // 회원 등급
        codename: '일반',                // DB검색용 codename
      },
      sortData: {
        hits: 'hits_desc',                  // 조회수
        commentcount: 'commentcount_desc',  // 댓글
        evsttime: 'evsttime_desc',          // 시작일자
        evedtime: 'evedtime_desc',          // 종료일자
        pubtime: 'pubtime_desc',            // 발표일자
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
  mounted() {
    // 권한 설정
    this.$http.post('/admin/common/pageAuth/check', {url: this.$options.name, isloading: false}).then(result => {
      this.isRead = (result.data.isread === 'T');
      this.isWrite = (result.data.iswrite === 'T');
      this.searchData.startDate = this.$util.getAddMonth(this.$util.getDate(''), -3, '-');
      this.searchData.endDate = this.$util.getDate('-');
      if (this.isRead) {
        this.onInit();
        this.$util.componentSetSearchParam(this);
      }
    }).catch(error => {
      this.$util.debug(error);
    })
  },
  methods: {
    ///////////////////////////////////내부 사용 메서드////////////////////////////////////////
    // 초기화
    onInit() {
      this.searchCodeList();
      this.onSearchList();
    },

    // 페이지에 사용 될 코드 조회
    searchCodeList() {
      let cmclassArr = ['MUAPPCHTYPE', 'MUMEMBERTYPE', 'MUMEMLVTYPE', 'DISPTYPE', 'DUPENTERTYPE'];
      this.$http.post('/common/code/map/list', {cmclass: cmclassArr, isloading: false})
          .then(result => {
            let data = result.data;
            for (const [key] of Object.entries(data)) {
              this.pageCode[key] = data[key];
            }
            this.pageCode.completetype = [
              {'cmcode': 'COM001', 'codename': '진행전'},
              {'cmcode': 'COM002', 'codename': '진행중'},
              {'cmcode': 'COM003', 'codename': '종료'}
            ];

            this.setCode();
          })
          .catch(error => {
            this.$util.debug(error);
          });
    },

    // 리스트 검색
    onSearchList() {
      let params = this.searchData;
      params.pageCount = this.pagingData.pageCount;
      params.page = (typeof page === 'undefined') ? this.pagingData.page : 1;
      params.listCount = this.pagingData.listCount;

      params = Object.assign({}, params, this.checkObj);
      params = Object.assign({}, params, this.sortData);

      this.$http.post("/admin/operation/shopping/promotion/search", params)
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

    // codename array get
    getCmCode(codeArr) {
      return codeArr.map(obj => obj.cmcode);
    },

    // 페이지 코드값 가져오기
    setCode() {
      // 적용 채널
      this.checkObj.muappchTypeChecked = this.getCmCode(this.pageCode.muappchtype);
      // 회원 유형
      this.checkObj.mumemberTypeChecked = this.getCmCode(this.pageCode.mumembertype);
      // 회원 등급
      this.checkObj.mumemlvTypeChecked = this.getCmCode(this.pageCode.mumemlvtype);
      // 노출 여부
      this.checkObj.dispTypeChecked = this.getCmCode(this.pageCode.disptype);
      // 진행상태
      this.checkObj.completeChecked = this.getCmCode(this.pageCode.completetype);
    },

    // 적용 채널 전체 체크
    checkAllMuAppch(value) {
      if (value) {
        // 적용 채널
        this.checkObj.muappchTypeChecked = this.getCmCode(this.pageCode.muappchtype);
      } else {
        this.checkObj.muappchTypeChecked = [];
      }
    },

    // 회원 유형 전체 체크
    checkAllMuMemer(value) {
      if (value) {
        // 적용 채널
        this.checkObj.mumemberTypeChecked = this.getCmCode(this.pageCode.mumembertype);
      } else {
        this.checkObj.mumemberTypeChecked = [];
      }
    },

    // 회원 등급 전체 체크
    checkAllMuMemLv(value) {
      if (value) {
        // 적용 채널
        this.checkObj.mumemlvTypeChecked = this.getCmCode(this.pageCode.mumemlvtype);
      } else {
        this.checkObj.mumemlvTypeChecked = [];
      }
    },

    // 회원 등급 전체 체크
    checkAllDisp(value) {
      if (value) {
        // 적용 채널
        this.checkObj.dispTypeChecked = this.getCmCode(this.pageCode.disptype);
      } else {
        this.checkObj.dispTypeChecked = [];
      }
    },

    // 진행 상태 전체 체크
    checkAllComp(value) {
      if (value) {
        // 적용 채널
        this.checkObj.completeChecked = this.getCmCode(this.pageCode.completetype);
      } else {
        this.checkObj.completeChecked = [];
      }
    },

    // 초기화 버튼
    onSearchClear() {
      this.searchData = this.$options.data().searchData;
      this.setCode();
      this.setTime();
    },

    // 조회기간 셋팅
    setTime() {
      this.searchData.startDate = this.$util.getAddMonth(this.$util.getDate(''), -3, '-');
      this.searchData.endDate = this.$util.getDate('-');
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

    // 전체 체크
    onCheckAll(checked) {
      this.listCheckData = [];
      if (checked) { // 전체 체크
        for (let i in this.listData) {
          this.listCheckData[i] = this.listData[i].eventidx;
        }
      }
    },

    changeCheck() {
      if(this.listCheckData.length === this.listData.length) {
        this.isChecked = true;
      } else {
        this.isChecked = false;
      }
    },

    // 사용, 미사용 변경
    onChangeUse(show) {
      if (this.listCheckData.length < 1) {
        alert("프로모션을 선택해주세요.");
        return;
      }

      let params = {
        idxlist : this.listCheckData,
        isdisplay: show,
        overmsg:'상태를 변경 하시겠습니까?',
        zeromsg:'미노출',
        isloading: false
      }

      if(show === 'F') {
        this.$http.post("/admin/operation/shopping/promotion/check", params)
        .then(result => {
            if (result.statusCode == 200) {
                params.msg = result.data.msg;
                this.changeDisplay(params);
            }
        })
        .catch(error => {
            this.$util.debug(error);
        })
      } else {
        params.msg = "상태를 전환 하시겠습니까?";
        this.changeDisplay(params);
      }
      
    },

    changeDisplay(params) {
      if(confirm(params.msg)) {
        this.$http.post("/admin/operation/shopping/promotion/update/use", params)
        .then(result => {
          if (result.statusCode === 200) {
            this.listCheckData = [];
            this.isChecked = false;
            this.onSearchList();
          }
        })
        .catch(error => {
          this.$util.debug(error);
        });
      }
    },

    deleteList() {
      if (this.listCheckData.length == 0) {
          alert("체크된 목록이 없습니다.");
          return;
      }

      let params = {
        idxlist : this.listCheckData,
        istrash: 'T',
        overmsg:'삭제 하시겠습니까?',
        zeromsg:'삭제',
        isloading: false
      }

      this.$http.post("/admin/operation/shopping/promotion/check", params)
        .then(result => {
            if (result.statusCode == 200) {
              if(confirm(result.data.msg)) {
                this.$http.post("/admin/operation/shopping/promotion/update/use", params)
                .then(result => {
                  if (result.statusCode === 200) {
                    this.listCheckData = [];
                    this.isChecked = false;
                    this.onSearchList();
                  }
                })
                .catch(error => {
                  this.$util.debug(error);
                });
              }
            }
        })
        .catch(error => {
            this.$util.debug(error);
        })
      
    },

    // 엑셀다운로드
    onExcelDownload() {
      let param = {
        list: this.listData
      };
      let postConfig = {responseType: 'arraybuffer'};
      this.$http.post("/admin/promotion/event/excel", param, postConfig)
          .then(result => {
            this.$util.debug(result);
          })
          .catch(error => {
            this.$util.debug(error);
          });
    },
    ///////////////////////////////////////////////////////////////////////////////////////////

    ///////////////////////////////////외부, 콜백 메서드///////////////////////////////////////
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
    closeNewPostPopup(isreload) {
      this.isNewPostShow = false;
      if(isreload) {
        this.onSearchList();
      }
    },

    // 상세 팝업 닫기
    closeDetailPopup(isreload) {
      this.isDetailShow = false;
      if(isreload) {
        this.onSearchList();
      }
    },

    // 발표 상세 팝업 열기
    goEventDetail(idx) {
      this.announceIdx = idx;
      this.isAnnounceShow = true;
    },

    // 발표 상세 팝업 닫기
    closeEventDetailPopup(isreload) {
      this.isAnnounceShow = false;
      if(isreload) {
        this.onSearchList();
      }
    },
    ///////////////////////////////////////////////////////////////////////////////////////////
  },
  watch: {
    // 적용 채널 체크 상태 검사
    'checkObj.muappchTypeChecked'(value) {
      if (value.length < this.pageCode.muappchtype.length) {
        this.checkObj.isallmuappch = 'F';
      } else {
        this.checkObj.isallmuappch = 'T';
      }
      this.searchData.muappchtype = this.checkObj.muappchTypeChecked.join();
    },

    // 회원 유형 체크 상태 검사
    'checkObj.mumemberTypeChecked'(value) {
      if (value.length < this.pageCode.mumembertype.length) {
        this.checkObj.isallmumember = 'F';
      } else {
        this.checkObj.isallmumember = 'T';
      }
      this.searchData.mumembertype = this.checkObj.mumemberTypeChecked.join();
    },

    // 회원 등급 체크 상태 검사
    'checkObj.mumemlvTypeChecked'(value) {
      if (value.length < this.pageCode.mumemlvtype.length) {
        this.checkObj.isallmumemlv = 'F';
      } else {
        this.checkObj.isallmumemlv = 'T';
      }
      this.searchData.mumemlvtype = this.checkObj.mumemlvTypeChecked.join();
    },

    // 회원 등급 체크 상태 검사
    'checkObj.dispTypeChecked'(value) {
      if (value.length < this.pageCode.disptype.length) {
        this.checkObj.isalldispt = 'F';
      } else {
        this.checkObj.isalldispt = 'T';
      }
      this.searchData.disptype = this.checkObj.dispTypeChecked.join();
    },

    // 회원 등급 체크 상태 검사
    'checkObj.completeChecked'(value) {
      if (value.length < this.pageCode.completetype.length) {
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
