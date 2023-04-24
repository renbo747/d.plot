<template>
  <div>
    <!-- 등록 팝업 -->
    <FaqNewPost
        ref="refNewPost"
        v-if="isNewPostShow"
        :common-code="commonCode"
    />

    <!-- 상세 팝업  -->
    <FaqDetail
        ref="refDetail"
        v-if="isDetailShow"
        :common-code="commonCode"
        :idx="isDetailIdx"
    />

    <!-- 컨텐츠 영역 -->
    <div class="content m-leftmenu">
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
              <input type="text" v-model="searchData.sword" @keyup.enter="onSearch"/>
            </dd>
          </dl>
          <dl>
            <dt>등록일자</dt>
            <dd>
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
            <dt>분류</dt>
            <dd>
              <select v-model="searchData.faqtype">
                <option value="">전체</option>
                <option v-for="(row, i) in commonCode.faqtype" :key="i" :value="row.cmcode">{{ row.codename }}</option>
              </select>
            </dd>
          </dl>
          <dl>
            <dt>사용여부</dt>
            <dd>
              <div class="radio_wrap wide3">
                <div>
                  <input type="radio" name="use" id="rd01" value="" v-model="searchData.istrash"/><label
                    for="rd01">전체</label>
                </div>
                <div>
                  <input type="radio" name="use" id="rd02" value="F" v-model="searchData.istrash"/><label
                    for="rd02">사용</label>
                </div>
                <div>
                  <input type="radio" name="use" id="rd03" value="T" v-model="searchData.istrash"/><label
                    for="rd03">미사용</label>
                </div>
              </div>
            </dd>
          </dl>
          <dl>
            <dt>노출대상</dt>
            <dd>
              <div class="check-wrap">
                  <input type="checkbox" v-model="searchData.isallmumember" id="isallmumember" true-value="T" false-value="F" @change="checkAllMembertype">
                  <label for="isallmumember">전체</label>
              </div>
              <div class="check-wrap" v-for="item in commonCode.mumembertype" :key="item.cmcode">
                  <input type="checkbox" v-model="searchData.mumembertypearr" :id="'mumembertype_'+item.cmcode" true-value="[]" :value="item.cmcode">
                  <label :for="'mumembertype_'+item.cmcode">{{item.codename}}</label>
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
            <span class="total">전체 <strong>{{ stateList.total }}</strong>건</span>
            <span>사용 <strong>{{ stateList.use }}</strong>건</span>
            <span>미사용 <strong>{{ stateList.unuse }}</strong>건</span>
          </div>
        </div>
        <div class="scroll-y" style="max-height: calc(100vh - 600px);">
          <table cellpadding="0" cellspacing="0" class="data-tb align-c">
            <caption>자주 묻는 질문 목록</caption>
            <colgroup>
              <col width="4%"/><!-- checkbox -->
              <col width="8%"/><!-- 메인노출 -->
              <col width="8%"/><!-- 노출순서 -->
              <col width="6%"/><!-- 분류 -->
              <col width=""/><!-- 제목 -->
              <col width="10%"/><!-- 노출대상 -->
              <col width="6%"/><!-- 사용여부 -->
              <col width="8%"/><!-- 작성자 -->
              <col width="10%"/><!-- 등록일 -->
              <col width="8%"/><!-- 조회수 -->
            </colgroup>
            <thead>
            <tr>
              <th></th>
              <th>메인노출</th>
              <th>노출순서
                <button type="button" v-if="isRead"
                        :value="sortData.sortnum"
                        class="sort"
                        :class="[{up : sortData.sortnum === 'sortnum_asc'}, {down : sortData.sortnum === 'sortnum_desc'}]"
                        @click="sortToggle(sortData.sortnum)">
                </button>
              </th>
              <th>분류
                <button type="button" v-if="isRead"
                        :value="sortData.faqtype"
                        class="sort"
                        :class="[{up : sortData.faqtype === 'faqtype_asc'}, {down : sortData.faqtype === 'faqtype_desc'}]"
                        @click="sortToggle(sortData.faqtype)">
                </button>
              </th>
              <th>제목</th>
              <th>노출대상</th>
              <th>사용여부</th>
              <th>작성자</th>
              <th>등록일
                <button type="button" v-if="isRead"
                        :value="sortData.regdate"
                        class="sort"
                        :class="[{up : sortData.regdate === 'regdate_asc'}, {down : sortData.regdate === 'regdate_desc'}]"
                        @click="sortToggle(sortData.regdate)">
                </button>
              </th>
              <th>조회수
                <button type="button" v-if="isRead"
                        :value="sortData.hits"
                        class="sort"
                        :class="[{up : sortData.hits === 'hits_asc'}, {down : sortData.hits === 'hits_desc'}]"
                        @click="sortToggle(sortData.hits)">
                </button>
              </th>
            </tr>
            </thead>
            <tbody v-if="listData.length > 0">
            <tr v-for="(row,i) in listData" :key="i">
              <td><input type="checkbox" v-model="moveData.targetIdx" v-bind:true-value="i"/></td>
              <td>{{ row.isfaqmain === 'T' ? '●' : '' }}</td>
              <td>{{ $util.isNull(row.sortnum) ? '-' : row.sortno }}</td>
              <td>{{ row.faqtypecode }}</td>
              <td class="left"><a class="link" @click="goDetail(row.idx)"
                                  style="cursor:pointer;">{{ row.subject }}</a></td>
              <td>{{ row.mumembertype }}</td>
              <td>{{ row.istrash }}</td>
              <td>{{ row.writer }}</td>
              <td>{{ row.regdate }}</td>
              <td>{{ row.hits }}</td>
            </tr>
            </tbody>
            <tbody v-else>
            <tr>
              <td colspan="10">조회 결과가 존재하지 않습니다.</td>
            </tr>
            </tbody>
          </table>
        </div>
        <div class="bottom-group">
          <!-- !! 필수 값 !!

              넘겨준 listData 안에 sortnum(노출순서), idx(고유값) 라는 이름으로 되있어야함

          -->
          <!--
              goChangeSortNum 메서드 안에서 저장 성공시

              this.moveData.targetIdx = '';               // ** 중요
              this.moveData.isSuccess = false;            // ** 중요

              위 값을 초기화 해줘야함
          -->
          <CommonArraySort :list-data="listData"
                           :move-data="moveData"
                           v-on:getModifySortNumArray="getModifySortNumArray"
                           :is-active-save-btn="true"
                           v-if="isWrite"
          />
          <div class="btn-group">
            <button type="button" class="btn blue" @click="goNewPost" v-if="isWrite">자주묻는질문 등록</button>
          </div>
        </div>
      </div>
    </div>
    <!-- /컨텐츠 영역 -->
  </div>

</template>

<script>
import FaqNewPost from "@views.admin/cs/faq/FaqNewPost";
import FaqDetail from "@views.admin/cs/faq/FaqDetail";
import CommonNavigator from '@/views/admin/common/CommonNavigator'
import CommonDatePicker from "@views.admin/common/CommonDatePicker";
import CommonArraySort from "@views.admin/common/CommonArraySort";

export default {
  name: "admin.cs.faq",
  components: {
    CommonArraySort,
    CommonDatePicker,
    FaqDetail,
    FaqNewPost,
    CommonNavigator
  },
  data() {
    return {
      moveData: {                       // 노출순위 데이터
        moveValue: '',                  // 움직일 값
        targetIdx: '',                  // 대상 위치
        code: 'U',                      // 위, 아래 코드
        isSuccess: false,               // 저장 성공 여부 (** 중요)
      },
      listData: [],                     // 리스트 데이터
      stateList: {                      // 상태 리스트
        total: '',
        use: '',
        unuse: '',
      },
      searchData: {
        sdate: 'start',                 // 조회기간 ( 시작일, 종료일 )
        startDate: '',                  // 시작 연도
        endDate: '',                    // 끝 연도
        skey: '',                       // 검색 조건 (제목 작성자)
        sword: '',                      // 검색 단어
        period: '3',                    // 날짜 검색 옵션 (어제,오늘,일주일...)
        istrash: '',                    // 사용 여부
        faqtype: '',                    // 분류
        isallmumember: 'T',
        mumembertypearr: [],
      },
      // pagingData: {
      //   pageCount: 20,                  // 페이징 옵션 (최대수)
      //   page: 1,                        // 현재 페이지
      //   listCount: 0                    // 총 페이지
      // },
      sortData: {
        sortnum: 'sortnum_desc',          // 노출 순서
        faqtype: 'faqtype_desc',    // 분류
        regdate: 'regdate_desc',        // 등록일
        hits: 'hits_desc',          // 조회수
      },
      isDetailShow: false, // 상세 팝업 열림 여부
      isDetailIdx: '',    // 상세 팝업 idx
      isNewPostShow: false, // 등록 팝업 열림 여부
      faqTypeCode: [],    // 분류코드
      isRead: false,
      isWrite: false,
      commonCode: {
        faqtype: {},
        mumembertype: {},
      },

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
        this.searchCodeList();
      }
    }).catch(error => {
      this.$util.debug(error);
    })
  },
  methods: {

    ///////////////////////// 내부 사용 메서드 /////////////////////////
    // 페이지에 사용 될 코드 조회
    searchCodeList() {
      let cmclassArr = ['MUMEMBERTYPE', 'FAQTYPE'];
      this.$http.post('/common/code/map/list', {cmclass: cmclassArr, isloading: false})
          .then(result => {
            let data = result.data;
            for (const [key] of Object.entries(data)) {
              this.commonCode[key] = data[key];
            }

            this.onSearchDataReset();
            this.$util.componentSetSearchParam(this);
            this.onSearch();
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

    // 검색
    onSearch() {
      // 페이징
      let param = this.searchData;

      this.$http.post("/admin/cs/faq/search", param)
          .then(result => {
            let data = result.data;
            this.listData = data.list;
            this.stateList = data.statelist
          })
          .catch(error => {
            this.$util.debug(error);
          });
    },
    // 초기화
    onSearchDataReset() {
      this.searchData.skey = '';
      this.searchData.sword = '';
      this.searchData.period = '3';
      this.searchData.faqtype = '';
      this.searchData.istrash = '';
      this.searchData.startDate = this.$util.getAddMonth(this.$util.getDate(''), -3, '-');
      this.searchData.endDate = this.$util.getDate('-');
      this.searchData.isallmumember = 'T';

      this.checkAllMembertype();
    },

    // 순서 저장
    getModifySortNumArray(array) {
      if (array.length === 0) {
        alert("변경된 데이터가 없습니다.");
        return;
      }

      let params = {
        numsave: '',
        list: array
      }

      this.$http.post("/admin/cs/faq/update", params)
          .then(result => {
            if (result.statusCode === 200) {
              alert("저장이 완료되었습니다.");
              this.onSearch();
              this.moveData.targetIdx = '';
              this.moveData.isSuccess = false;            // ** 중요
            }
          })
          .catch(error => {
            this.$util.debug(error);
          });
    },

    /////////////////////////////////////////////////////////////////

    //////////////////////// 외부, 콜백 메서드 /////////////////////////
    // 페이징 콜백 함수
    setPagingData(param) {
      this.pagingData = param;
      this.onSearch();
    },

    // 날짜 picker 콜백 함수
    onChangeStartDate(val) {
      this.searchData.startDate = val;
    },

    // 조회조건 - 적용채널 전체체크
    checkAllMembertype: function() {
        let isAllCheck = this.searchData.isallmumember;
        this.searchData.mumembertypearr = [];
        if (isAllCheck === 'T') {
            for(let type of this.commonCode.mumembertype){
                this.searchData.mumembertypearr.push(type.cmcode);
            }
        }
    },

    // 날짜 picker 콜백 함수
    onChangeEndDate(val) {
      this.searchData.endDate = val;
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
    },
    'searchData.mumembertypearr': function(value){
        if (value.length < this.commonCode.mumembertype.length) {
            this.searchData.isallmumember = 'F';
        } else {
            this.searchData.isallmumember = 'T';
        }
    },
  },
}
</script>
