<template>
  <div>
    <!-- 시리얼 등록 팝업 -->
    <SerialNewPost
        v-if="isNewPostShow"
        ref="refNewPost"
        :common-code="commonCode"
        @close="closeNewPostPopup"
    />
    <!-- /시리얼 등록 팝업 -->

    <!-- 시리얼 상세 팝업 -->
    <SerialDetail
        v-if="isDetailShow"
        ref="refDetail"
        :common-code="commonCode"
        :srp-idx="detailIdx"
        @close="closeDetailPopup"
    />
    <!-- /시리얼 상세 팝업 -->

    <!-- 컨텐츠 영역 -->
    <div class="content m-leftmenu">
      <AdminCommonNavigator/>
      <div class="inner">
        <div class="clearfix">
          <div class="bar-title fl">시리얼프로모션 관리</div>
        </div>
        <div class="boxing search-area">
          <dl>
            <dt>직접검색</dt>
            <dd>
              <select v-model="searchData.skey">
                <option value="">전체</option>
                <option value="title">관리제목</option>
                <option value="reguserid">등록자</option>
              </select>
              <input type="text" v-model="searchData.sword" @keyup.enter="onSearch(1)"/>
            </dd>
          </dl>
          <dl>
            <dt>조회기간</dt>
            <dd>
              <select v-model="searchData.sdate">
                <option value="">전체</option>
                <option value="srpstday">시작일자</option>
                <option value="srpedday">종료일자</option>
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
                <input type="radio" name="group0212" id="group2114" value="" v-model="searchData.istrash"><label
                  for="group2114">전체</label>
                <input type="radio" name="group0213" id="group2224" value="사용" v-model="searchData.istrash"><label
                  for="group2224">사용</label>
                <input type="radio" name="group0214" id="group2334" value="미사용" v-model="searchData.istrash"><label
                  for="group2334">미사용</label>
              </div>
            </dd>
          </dl>
          <dl>
            <dt>진행상태</dt>
            <dd>
              <div class="check-wrap">
                <input type="checkbox" id="pall1" v-model="checkObj.isAllPromost" true-value="T" false-value="F"
                       @change="checkAllPromos($event.target.checked)">
                <label for="pall1">전체</label>
              </div>
              <div class="check-wrap" v-for="(row, i) in commonCode.promosttype" :key="i">
                <input type="checkbox" :id="'pgroup41' + i" :value="row.codename" v-model="checkObj.promostChecked">
                <label :for="'pgroup41' + i">{{ row.codename }}</label>
              </div>
            </dd>
          </dl>
          <dl>
            <dt>혜택구분</dt>
            <dd>
              <div class="check-wrap">
                <input type="checkbox" id="sall1" v-model="checkObj.isAllSrp" true-value="T" false-value="F"
                       @change="checkAllSrp($event.target.checked)">
                <label for="sall1">전체</label>
              </div>
              <div class="check-wrap" v-for="(row, i) in commonCode.srptype" :key="i">
                <input type="checkbox" :id="'group1' + i" :value="row.codename" v-model="checkObj.srpTypeChecked">
                <label :for="'group1' + i">{{ row.codename }}</label>
              </div>
            </dd>
          </dl>
          <dl>
            <dt>S/N 생성방법</dt>
            <dd>
              <div class="check-wrap">
                <input type="checkbox" id="rall1" v-model="checkObj.isAllSrc" true-value="T" false-value="F"
                       @change="checkAllSrc($event.target.checked)">
                <label for="rall1">전체</label>
              </div>
              <div class="check-wrap" v-for="(row, i) in commonCode.srctype" :key="i">
                <input type="checkbox" :id="'group2' + i" :value="row.codename" v-model="checkObj.srcTypeChecked">
                <label :for="'group2' + i">{{ row.codename }}</label>
              </div>
            </dd>
          </dl>
        </div>
        <div class="btn-group" v-if="isRead">
          <button type="button" class="btn big blue" @click="onSearch">검색</button>
          <button type="button" class="btn big gray" @click="onSearchClear">초기화</button>
        </div>
        <div class="caption-group mt10 clearfix">
          <div class="total-group fl">
            <span class="total">전체 <strong>{{ pagingData.listCount }}</strong>건</span>
          </div>
          <div class="btn-group fr" v-if="isRead">
            <button type="button" class="btn green-line" @click="onExcelDownload">
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
          <caption>D포인트</caption>
          <colgroup>
            <col width="3%"/><!-- No -->
            <col width=""/><!-- 관리제목 -->
            <col width="5%"/><!-- 혜택구분 -->
            <col width="8%"/><!-- S/N 생성방법 -->
            <col width="12%"/><!-- S/N 발급기준 -->
            <col width="4%"/><!-- 등록 -->
            <col width="5%"/><!-- 사용가능 -->
            <col width="7%"/><!-- 지급혜택 -->
            <col width="7%"/><!-- 소멸일자(D포인트) -->
            <col width="6%"/><!-- 중복사용여부(D포인트) -->
            <col width="4%"/><!-- 사용여부 -->
            <col width="6%"/><!-- 시작일자 -->
            <col width="6%"/><!-- 종료일자 -->
            <col width="5%"/><!-- 등록자 -->
            <col width="6%"/><!-- 등록일자 -->
            <col width="4%"/><!-- 진행상태 -->
          </colgroup>
          <thead>
          <tr>
            <th>No</th>
            <th>관리제목</th>
            <th>혜택구분</th>
            <th>S/N 생성방법</th>
            <th>S/N 발급기준</th>
            <th>등록
              <button type="button" v-if="isRead"
                      :value="sortData.ncount"
                      class="sort"
                      :class="[{up : sortData.ncount === 'ncount_asc'}, {down : sortData.ncount === 'ncount_desc'}]"
                      @click="sortToggle(sortData.ncount)"></button>
            </th>
            <th>사용가능
              <button type="button" v-if="isRead"
                      :value="sortData.availablecount"
                      class="sort"
                      :class="[{up : sortData.availablecount === 'availablecount_desc_asc'}, {down : sortData.availablecount === 'availablecount_desc'}]"
                      @click="sortToggle(sortData.availablecount)"></button>
            </th>
            <th>지급혜택</th>
            <th>소멸일자(D포인트)
              <button type="button" v-if="isRead"
                      :value="sortData.epoedday"
                      class="sort"
                      :class="[{up : sortData.epoedday === 'epoedday_asc'}, {down : sortData.epoedday === 'epoedday_desc'}]"
                      @click="sortToggle(sortData.epoedday)"></button>
            </th>
            <th>중복사용여부(D포인트)</th>
            <th>사용여부</th>
            <th>시작일자
              <button type="button" v-if="isRead"
                      :value="sortData.srpstday"
                      class="sort"
                      :class="[{up : sortData.srpstday === 'srpstday_asc'}, {down : sortData.srpstday === 'srpstday_desc'}]"
                      @click="sortToggle(sortData.srpstday)"></button>
            </th>
            <th>종료일자
              <button type="button" v-if="isRead"
                      :value="sortData.srpedday"
                      class="sort"
                      :class="[{up : sortData.srpedday === 'srpedday_asc'}, {down : sortData.srpedday === 'srpedday_desc'}]"
                      @click="sortToggle(sortData.srpedday)"></button>
            </th>
            <th>등록자</th>
            <th>등록일자
              <button type="button" v-if="isRead"
                      :value="sortData.regdate"
                      class="sort"
                      :class="[{up : sortData.regdate === 'regdate_asc'}, {down : sortData.regdate === 'regdate_desc'}]"
                      @click="sortToggle(sortData.regdate)"></button>
            </th>
            <th>진행상태</th>
          </tr>
          </thead>
          <tbody v-if="listData.length > 0">
          <tr v-for="(row, i) in listData" :key="i">
            <td>{{ row.rn }}</td>
            <td><a class="link" @click="goDetail(row.srpidx)">{{ row.title }}</a></td>
            <td>{{ row.srptype }}</td>
            <td>{{ row.srctype }}</td>
            <td>{{ row.isequalserial }}</td>
            <td><a class="link" @click="onSerialNoExcelDownload(row.srpidx)">{{ row.ncount }}</a></td>
            <td>{{ row.availablecount }}</td>
            <td>{{ row.srppoint }}</td>
            <td>{{ row.epoedday }}</td>
            <td>{{ row.isepointdup }}</td>
            <td>{{ row.istrash }}</td>
            <td>{{ row.srpstday }}</td>
            <td>{{ row.srpedday }}</td>
            <td>{{ row.regname }}</td>
            <td>{{ row.regdate }}</td>
            <td>{{ row.bptype }}</td>
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
            <button type="button" class="btn blue" @click="goNewPost" v-if="isWrite">시리얼프로모션 등록</button>
          </div>
        </div>
      </div>
    </div>
    <!-- /컨텐츠 영역 -->
  </div>
</template>

<script>
import SerialNewPost from "@views.admin/promotion/promotion/serial/SerialNewPost";
import SerialDetail from "@views.admin/promotion/promotion/serial/SerialDetail";
import AdminCommonNavigator from "@views.admin/common/CommonNavigator";
import CommonDatePicker from "@views.admin/common/CommonDatePicker";
import CommonPageNavigator from "@views.admin/common/CommonPageNavigator";

export default {
  name: "admin.promotion.promotion.serial",
  components: {CommonPageNavigator, CommonDatePicker, AdminCommonNavigator, SerialNewPost, SerialDetail},
  data() {
    return {
      isNewPostShow: false,
      isDetailShow: false,
      detailIdx: '',
      isRead: false,
      isWrite: false,
      listData: [],                         // 리스트 데이터
      commonCode: {
        srptype: [],
        srctype: [],
        promosttype: [],
      },
      searchData: {
        sdate: '',                          // 조회기간 (시작, 종료, 등록 )
        startDate: '',                      // 시작 연도
        endDate: '',                        // 끝 연도
        skey: '',                           // 검색 조건 ( 관리제목, 등록자 )
        sword: '',                          // 검색 단어
        period: '3',                        // 날짜 검색 옵션 (어제,오늘,일주일...)

        srptype: '',                        // 혜택 구분
        srctype: '',                        // s/n 생성 구분
        istrash: '',                        // 사용여부
        promost: '',                         // 진행상태
      },

      pagingData: {
        pageCount: 20,        // 페이징 옵션(최대수)
        page: 1,              // 현재 페이지
        listCount: 0          // 전체 수
      },

      checkObj: {
        isAllPromost: 'T',
        promostChecked: [],
        isAllSrp: 'T',
        srpTypeChecked: [],          // 혜택 구분
        isAllSrc: 'T',
        srcTypeChecked: [],         //  생성 타입
      },

      sortData: {
        ncount: 'ncount_desc',                  // 등록
        availablecount: 'availablecount_desc',                  // 사용가능
        epoedday: 'epoedday_desc',                  // 소멸일자(epoint)
        srpstday: 'srpstday_desc',                  // 시작일자
        srpedday: 'srpedday_desc',                  // 종료일자
        regdate: 'regdate_desc',                  // 등록일자
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
        this.$util.componentSetSearchParam(this);
        this.onInit();
      }
    }).catch(error => {
      this.$util.debug(error);
    })
  },
  methods: {

    ///////////////////////// 내부 사용 메서드 /////////////////////////
    onInit() {
      this.getCommonCodeList();
      this.onSearch();
    },

    // 검색
    onSearch() {
      let params = this.searchData;
      params.pageCount = this.pagingData.pageCount;
      params.page = (typeof page === 'undefined') ? this.pagingData.page : 1;
      params.listCount = this.pagingData.listCount;

      Object.assign(params, this.sortData);

      this.$http.post("/admin/promotion/promotion/serial/search", params)
          .then(result => {
            if (result.statusCode === 200) {
              let data = result.data;
              this.listData = data.list;
              this.pagingData.listCount = data.state.total;
            }
          })
          .catch(error => {
            this.$util.debug(error);
          });
    },

    // 초기화 버튼
    onSearchClear() {
      this.searchData = this.$options.data().searchData;
      this.searchData.startDate = this.$util.getAddMonth(this.$util.getDate(''), -3, '-');
      this.searchData.endDate = this.$util.getDate('-');
      this.checkAllSrc(true);
      this.checkAllSrp(true);
      this.checkAllPromos(true);
    },

    // 공통코드 세팅
    getCommonCodeList() {
      let cmclassArr = ['SRPTYPE', 'SRCTYPE', 'PROMOSTTYPE'];
      this.$http.post('/common/code/map/list', {cmclass: cmclassArr, isloading: false})
          .then(result => {
            let data = result.data;
            for (const [key] of Object.entries(data)) {
              this.commonCode[key] = data[key];
            }

            // 코드값 셋팅
            this.checkAllSrp(true);
            this.checkAllSrc(true);
            this.checkAllPromos(true);
          })
          .catch(error => {
            this.$util.debug(error);
          });
    },

    // 혜택 구분 전체 체크
    checkAllSrp(value) {
      if (value) {
        // 적용 채널
        this.checkObj.srpTypeChecked = this.getCodeName(this.commonCode.srptype);
      } else {
        this.checkObj.srpTypeChecked = [];
      }
    },

    // S/N 생성 방법 전체 체크
    checkAllSrc(value) {
      if (value) {
        // 적용 채널
        this.checkObj.srcTypeChecked = this.getCodeName(this.commonCode.srctype);
      } else {
        this.checkObj.srcTypeChecked = [];
      }
    },

    // 진행상태
    checkAllPromos(value) {
      if (value) {
        // 적용 채널
        this.checkObj.promostChecked = this.getCodeName(this.commonCode.promosttype);
      } else {
        this.checkObj.promostChecked = [];
      }
    },

    // codename array get
    getCodeName(codeArr) {
      return codeArr.map(obj => obj.codename);
    },

    // 엑셀다운로드
    onExcelDownload() {
      let param = {
        list: this.listData
      };
      let postConfig = {responseType: 'arraybuffer'};
      this.$http.post("/admin/promotion/promotion/serial/excel/list", param, postConfig)
          .then(result => {
            this.$util.debug(result);
          })
          .catch(error => {
            this.$util.debug(error);
          });
    },

    // 시리얼 번호 엑셀다운로드
    onSerialNoExcelDownload(srpIdx) {
      let param = {
        srpidx: srpIdx
      };
      let postConfig = {responseType: 'arraybuffer'};
      this.$http.post("/admin/promotion/promotion/serial/excel/snlist", param, postConfig)
          .then(result => {
            this.$util.debug(result);
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
      this.sortData.psort = sortName;

      this.onSearch();
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

    // 날짜 picker 콜백 함수
    onChangeEndDate(val) {
      this.searchData.endDate = val;
    },

    /////////////////////////////////////////////////////////////////

    ///////////////////////// 팝업 메서드 /////////////////////////////
    // 등록 팝업 열기
    goNewPost() {
      this.isNewPostShow = true;
    },

    // 상세 팝업 열기
    goDetail(idx) {
      this.detailIdx = idx;
      this.isDetailShow = true;
    },

    // 상세 팝업 닫기
    closeDetailPopup(isreload) {
      this.isDetailShow = false;
      if (typeof isreload === 'boolean' && isreload) {
        this.onSearch();
      }
    },

    // 등록 팝업 닫기
    closeNewPostPopup(isreload) {
      this.isNewPostShow = false;
      if (typeof isreload === 'boolean' && isreload) {
        this.onSearch();
      }
    },
    /////////////////////////////////////////////////////////////////
  },
  watch: {
    // 혜택 구분
    'checkObj.srpTypeChecked'(value) {
      if (value.length < this.commonCode.srptype.length) {
        this.checkObj.isAllSrp = 'F';
      } else {
        this.checkObj.isAllSrp = 'T';
      }
      this.searchData.srptype = this.checkObj.srpTypeChecked.join();
    },
    // 혜택 구분
    'checkObj.srcTypeChecked'(value) {
      if (value.length < this.commonCode.srctype.length) {
        this.checkObj.isAllSrc = 'F';
      } else {
        this.checkObj.isAllSrc = 'T';
      }
      this.searchData.srctype = this.checkObj.srcTypeChecked.join();
    },
    // 혜택 구분
    'checkObj.promostChecked'(value) {
      if (value.length < this.commonCode.promosttype.length) {
        this.checkObj.isAllPromost = 'F';
      } else {
        this.checkObj.isAllPromost = 'T';
      }
      this.searchData.promost = this.checkObj.promostChecked.join();
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
