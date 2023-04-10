<template>
  <div>
    <EpointNewPost
        v-if="isNewPostShow"
        ref="refNewPost"
        :page-code="pageCode"
        @close="closeNewPostPopup"
    />
    <EpointDetail
        v-if="isDetailShow"
        ref="refDetail"
        :page-code="pageCode"
        :epo-idx="detailIdx"
        @close="closeDetailPopup"
    />

    <!-- 컨텐츠 영역 -->
    <div class="content m-leftmenu">
      <AdminCommonNavigator/>
      <div class="inner">
        <div class="clearfix">
          <div class="bar-title fl">D포인트 관리</div>
        </div>
        <div class="boxing search-area">
          <dl>
            <dt>직접검색</dt>
            <dd>
              <select v-model="searchData.skey">
                <option value="">전체</option>
                <option value="eponame">관리제목</option>
                <option value="eporeason">지급사유</option>
              </select>
              <input type="text" v-model="searchData.sword" @keyup.enter="onSearchList(1)"/>
            </dd>
          </dl>
          <dl>
            <dt>조회기간</dt>
            <dd>
              <select v-model="searchData.sdate">
                <option value="">전체</option>
                <option value="epovalidday">소멸일자(유효기간)</option>
                <option value="epopayday">지급일자</option>
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
            <dt>중복사용여부</dt>
            <dd class="radio_wrap">
              <input type="radio" id="group01" value="" v-model="searchData.isepointdup" checked><label
                for="group01">전체</label>
              <input type="radio" id="group02" value='T' v-model="searchData.isepointdup"><label
                for="group02">허용</label>
              <input type="radio" id="group03" value='F' v-model="searchData.isepointdup"><label
                for="group03">미허용</label>
            </dd>
          </dl>
          <dl>
            <dt>지급대상범위</dt>
            <dd class="radio_wrap">
              <input type="radio" id="group11" value="" v-model="searchData.ismemtype"><label
                for="group11">전체</label>
              <input type="radio" id="group12" value="T" v-model="searchData.ismemtype"><label
                for="group12">특정유형/등급</label>
              <input type="radio" id="group13" value="F" v-model="searchData.ismemtype"><label
                for="group13">회원특정</label>
            </dd>
          </dl>
        </div>
        <div class="btn-group" v-if="isRead">
          <button type="button" class="btn big blue" @click="onSearchList(1)">검색</button>
          <button type="button" class="btn big gray" @click="onSearchDataReset">초기화</button>
        </div>
        <div class="caption-group mt10 clearfix">
          <div class="total-group fl">
            <span class="total">전체 <strong>{{ pagingData.listCount }}</strong>건</span>
          </div>
          <div class="btn-group fr" v-if="isRead">
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
          <caption>D포인트</caption>
          <colgroup>
            <col width="5%"/><!-- No -->
            <col width="15%"/><!-- 관리제목 -->
            <col width="8%"/><!-- 지급대상범위 -->
            <col width="10%"/><!-- 지급대상 -->
            <col width="8%"/><!-- 지급포인트 -->
            <col width="8%"/><!-- 소멸일자 -->
            <col width="8%"/><!-- 중복사용여부 -->
            <col width=""/><!-- 지급사유 -->
            <col width="8%"/><!-- 지급일자 -->
          </colgroup>
          <thead>
          <tr>
            <th>No</th>
            <th>관리제목</th>
            <th>지급대상범위</th>
            <th>지급대상수
              <button type="button" v-if="isRead"
                      :value="sortData.count"
                      class="sort"
                      :class="[{up : sortData.count === 'count_asc'}, {down : sortData.count === 'count_desc'}]"
                      @click="sortToggle(sortData.count)"></button>
            </th>
            <th>지급포인트
              <button type="button" v-if="isRead"
                      :value="sortData.point"
                      class="sort"
                      :class="[{up : sortData.point === 'point_asc'}, {down : sortData.point === 'point_desc'}]"
                      @click="sortToggle(sortData.point)"></button>
            </th>
            <th>소멸일자
              <button type="button" v-if="isRead"
                      :value="sortData.epovalidday"
                      class="sort"
                      :class="[{up : sortData.epovalidday === 'epovalidday_asc'}, {down : sortData.epovalidday === 'epovalidday_desc'}]"
                      @click="sortToggle(sortData.epovalidday)"></button>
            </th>
            <th>중복사용여부</th>
            <th>지급 사유</th>
            <th>지급일자
              <button type="button" v-if="isRead"
                      :value="sortData.epopayday"
                      class="sort"
                      :class="[{up : sortData.epopayday === 'epopayday_asc'}, {down : sortData.epopayday === 'epopayday_desc'}]"
                      @click="sortToggle(sortData.epopayday)"></button>
            </th>
          </tr>
          </thead>
          <tbody v-if="listData.length > 0">
          <tr v-for="(row, i) in listData" :key="i">
            <td>{{ row.rn }}</td>
            <td><a @click="goDetail(row.epoidx)" class="link">{{ row.eponame }}</a></td>
            <td>{{ row.ismemtype }}</td>
            <td>{{ row.count }}</td>
            <td>{{ $util.maskComma(row.payepoint) }}</td>
            <td>{{ row.epovalidday }}</td>
            <td>{{ row.isepointdup }}</td>
            <td class="left">{{ row.eporeason }}</td>
            <td>{{ row.epopayday }}</td>
          </tr>
          </tbody>
          <tbody v-else>
          <tr>
            <td colspan="9">조회 결과가 존재하지 않습니다.</td>
          </tr>
          </tbody>
        </table>
        <div class="bottom-group">
          <CommonPageNavigator :pagingData="pagingData" v-on:setPagingData="setPagingData"/>
          <div class="btn-group">
            <button type="button" class="btn blue" @click="goNewPost" v-if="isWrite">D포인트 수동지급</button>
          </div>
        </div>
      </div>
    </div>
    <!-- /컨텐츠 영역 -->
  </div>
</template>

<script>
import EpointNewPost from "@views.admin/promotion/promotion/EpointNewPost";
import EpointDetail from "@views.admin/promotion/promotion/EpointDetail";
import AdminCommonNavigator from "@views.admin/common/CommonNavigator";
import CommonDatePicker from "@views.admin/common/CommonDatePicker";
import CommonPageNavigator from "@views.admin/common/CommonPageNavigator";

export default {
  name: "admin.promotion.promotion.epoint",
  components: {
    CommonPageNavigator,
    CommonDatePicker,
    AdminCommonNavigator,
    EpointDetail,
    EpointNewPost
  },
  data() {
    return {
      isRead: false,
      isWrite: false,
      isNewPostShow: false,             // 등록 팝업

      isDetailShow: false,              // 상세 팝업
      detailIdx: '',                    // 상세 인덱스

      pageCode: {
        // 회원 유형
        mumembertype: {},
        // 회원 등급
        mumemlvtype: {},
      },

      searchData: {
        sdate: '',                          // 조회기간 ( 소멸일자, 지급일자 )
        startDate: '',                      // 시작 연도
        endDate: '',                        // 끝 연도
        skey: '',                           // 검색 조건 (관리제목, 지급사유)
        sword: '',                          // 검색 단어
        period: '3',                        // 날짜 검색 옵션 (어제,오늘,일주일...)
        isepointdup: '',                    // 중복사용여부
        ismemtype: '',                      // 지급대상범위
      },

      sortData: {
        count: 'count_desc',                  // 지급대상수
        point: 'point_desc',                  // 지급포인트
        epovalidday: 'epovalidday_desc',      // 소멸일자
        epopayday: 'epopayday_desc',          // 지급일자
      },

      pagingData: {
        pageCount: 20,        // 페이징 옵션(최대수)
        page: 1,              // 현재 페이지
        listCount: 0          // 전체 수
      },

      listData: [],                     // 리스트
    }
  },
  methods: {
    ///////////////////////////////////내부 사용 메서드///////////////////////////////////////////
    onInit() {
      this.onSearchList();
      this.onCommonCode();
    },

    // 검색
    onSearchList(page) {
      let params = this.searchData;
      params.pageCount = this.pagingData.pageCount;
      params.page = (typeof page === 'undefined') ? this.pagingData.page : 1;
      params.listCount = this.pagingData.listCount;

      params = Object.assign({}, params, this.sortData);

      this.$http.post("/admin/promotion/promotion/epoint/search", params)
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

    // 공통 코드
    onCommonCode() {
      let cmclassArr = ['MUMEMBERTYPE', 'MUMEMLVTYPE',];
      this.$http.post('/common/code/map/list', {cmclass: cmclassArr, isloading: false})
          .then(result => {
            let data = result.data;
            this.pageCode.mumembertype = data.mumembertype;
            this.pageCode.mumemlvtype = data.mumemlvtype;
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

      this.onSearchList();
    },

    // 초기화
    onSearchDataReset() {
      this.searchData = this.$options.data().searchData;
      this.searchData.startDate = this.$util.getAddMonth(this.$util.getDate(''), -3, '-');
      this.searchData.endDate = this.$util.getDate('-');
    },

    // 엑셀다운로드
    onExcelDownload() {
      let param = {
        list: this.listData
      };
      let postConfig = {responseType: 'arraybuffer'};
      this.$http.post("/admin/promotion/promotion/epoint/excel", param, postConfig)
          .then(result => {
            this.$util.debug(result);
          })
          .catch(error => {
            this.$util.debug(error);
          });
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
    goDetail(idx) {
      this.detailIdx = idx;
      this.isDetailShow = true;
    },

    // 등록 팝업 닫기
    closeNewPostPopup(isreload) {
      this.isNewPostShow = false;
      if (isreload) {
        this.onSearchList();
      }
    },

    // 상세 팝업 닫기
    closeDetailPopup(isreload) {
      this.isDetailShow = false;
      if (isreload) {
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
