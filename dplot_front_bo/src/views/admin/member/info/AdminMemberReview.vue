<template>
  <div class="tab-area" style="height: calc(90vh - 246px);">
    <div class="clearfix">
      <div class="bar-title small fl">리뷰작성내역</div>
    </div>
    <div class="boxing search-area">
      <dl>
        <dt>조회기간</dt>
        <dd>
          <common-date-picker :value="searchData.startDate" @change="onChangeStartDate"></common-date-picker>
          <span>-</span>
          <common-date-picker :value="searchData.endDate" @change="onChangeEndDate"></common-date-picker>
        </dd>
      </dl>
    </div>
    <div class="btn-group">
      <button type="button" class="btn big blue" @click="onSearch">검색</button>
    </div>
    <div class="caption-group mt10 clearfix">
      <div class="total-group fl">
        <span class="total">전체 <strong>{{ totalCnt }}</strong>건</span>
      </div>
      <div class="btn-group fr">
        <select v-model="pagingData.pageCount">
          <option value="20">20개씩 보기</option>
          <option value="50">50개씩 보기</option>
          <option value="100">100개씩 보기</option>
        </select>
      </div>
    </div>
    <table cellpadding="0" cellspacing="0" class="data-tb align-c">
      <caption>리뷰 목록</caption>
      <colgroup>
        <col width="3%" /><!-- No -->
        <col width="4%" /><!-- 베스트 -->
        <col width="4%" /><!-- 블라인드 -->
        <col width="15%" /><!-- 상품명 -->
        <col width="%" /><!-- 리뷰내용 -->
        <col width="6%" /><!-- 아이디 -->
        <col width="6%" /><!-- 이름 -->
        <col width="6%" /><!-- 총상품평점 -->
        <col width="5%" /><!-- 도움 -->
        <col width="9%" /><!-- 사진 -->
        <col width="4%" /><!-- 신고 -->
        <col width="8%" /><!-- 시작일자 -->
        <col width="8%" /><!-- 종료일자 -->
        <col width="8%" /><!-- 등록일자 -->
      </colgroup>
      <thead>
      <tr>
        <th>No</th>
        <th>베스트</th>
        <th>미노출</th>
        <th>상품명</th>
        <th>리뷰내용</th>
        <th>아이디</th>
        <th>이름</th>
        <th>총상품평점
          <button type="button"
                  :value="sortData.totpoint"
                  class="sort"
                  :class="[{up : sortData.totpoint === 'totpoint_asc'}, {down : sortData.totpoint === 'totpoint_desc'}]"
                  @click="sortToggle(sortData.totpoint)">
          </button>
        </th>
        <th>좋아요
          <button type="button"
                  :value="sortData.goodcnt"
                  class="sort"
                  :class="[{up : sortData.goodcnt === 'goodcnt_asc'}, {down : sortData.goodcnt === 'goodcnt_desc'}]"
                  @click="sortToggle(sortData.goodcnt)">
          </button>
        </th>
        <th>포토&동영상
          <button type="button"
                  :value="sortData.filecnt"
                  class="sort"
                  :class="[{up : sortData.filecnt === 'filecnt_asc'}, {down : sortData.filecnt === 'filecnt_desc'}]"
                  @click="sortToggle(sortData.filecnt)">
          </button>
        </th>
        <th>신고</th>
        <th>시작일자
          <button type="button"
                  :value="sortData.beststtime"
                  class="sort"
                  :class="[{up : sortData.beststtime === 'beststtime_asc'}, {down : sortData.beststtime === 'beststtime_desc'}]"
                  @click="sortToggle(sortData.beststtime)">
          </button>
        </th>
        <th>종료일자
          <button type="button"
                  :value="sortData.bestedtime"
                  class="sort"
                  :class="[{up : sortData.bestedtime === 'bestedtime_asc'}, {down : sortData.bestedtime === 'bestedtime_desc'}]"
                  @click="sortToggle(sortData.bestedtime)">
          </button>
        </th>
        <th>등록일자
          <button type="button"
                  :value="sortData.regdate"
                  class="sort"
                  :class="[{up : sortData.regdate === 'regdate_asc'}, {down : sortData.regdate === 'regdate_desc'}]"
                  @click="sortToggle(sortData.regdate)">
          </button>
        </th>
      </tr>
      </thead>
      <tbody v-if="this.list.length > 0">
        <tr v-for="(row, index) in this.list" :key="index">
          <td>{{ loopNumberForPaging(index) }}</td>
          <td>{{row.isbest === 'T' ? '●' : '-'}}</td>
          <td>{{row.isdisplay === 'T' ? '-' : '●'}}</td>
          <td class="left">{{row.goodsname}}</td>
          <td class="left"><a class="link" @click="goDetail(row.reviewidx)">{{row.content}}</a></td>
          <td>{{row.reguserid}}</td>
          <td>{{row.regusername}}</td>
          <td>{{row.totpoint}}</td>
          <td>{{row.goodcnt}}</td>
          <td>{{row.filecnt}}</td>
          <td>{{row.noticnt}}</td>
          <td>{{$util.isNull(row.beststtime) ? '-': row.beststtime}}</td>
          <td>{{$util.isNull(row.bestedtime) ? '-': row.bestedtime}}</td>
          <td>{{row.regdate}}</td>
        </tr>
      </tbody>
      <tbody v-else>
        <tr><td colspan="14">조회 결과가 존재하지 않습니다.</td></tr>
      </tbody>
    </table>
    <div class="bottom-group">
      <common-page-navigator :pagingData="pagingData" v-on:setPagingData="setPagingData"/>
    </div>
    <ReviewDetail v-if="showDetail" v-bind:reviewidx="idx" v-on:closeDetail="closeDetail"></ReviewDetail>
  </div>
</template>

<script>
import CommonDatePicker from "@views.admin/common/CommonDatePicker";
import CommonPageNavigator from "@views.admin/common/CommonPageNavigator";
import ReviewDetail from "@views.admin/operation/review/ReviewDetail";

export default {
  name: "AdminMemberReview",
  props: ['activeUserNo'],
  components: { CommonDatePicker, CommonPageNavigator, ReviewDetail},
  data() {
    return {
      totalCnt: 0,
      searchData: {
        userno: this.activeUserNo,
        startDate: '',          // 조회시작날짜
        endDate: '',            // 조회종료날짜
        psort: 'regdate_desc',   // 정렬
      },
      sortData: {
        totpoint: 'totpoint_desc',              // 총상품평점 정렬기준
        goodcnt: 'goodcnt_desc',                // 좋아요 정렬기준
        filecnt: 'filecnt_desc',                // 포토&동영상 정렬기준
        beststtime: 'beststtime_desc',          // 시작일자 정렬기준
        bestedtime: 'bestedtime_desc',          // 종료일자 정렬기준
        regdate: 'regdate_desc',                // 등록일자 정렬기준
      },
      pagingData: {
        pageCount: 20,      // 페이징 옵션(최대수)
        page: 1,            // 현재 페이지
        listCount: 0        // 총 수량
      },
      list : [],
      showDetail : false
    }
  },
  mounted() {
    this.searchData.startDate = this.$util.getAddMonth(this.$util.getDate(), -3, '-');
    this.searchData.endDate = this.$util.getDate('-');
    this.onSearch();
  },
  methods : {
    onSearch() {
      let params = Object.assign(this.searchData, this.pagingData);
      this.$http.post('/admin/member/review/list', params).then(result => {
        this.list = result.data.list;
        this.totalCnt = result.data.count.totalcnt;
        this.pagingData.listCount = this.totalCnt;
      }).catch(error => {
        this.$util.debug(error);
      })
    },
    loopNumberForPaging(index) {
      if(this.pagingData.page > 1){
        let page = this.pagingData.page - 1;
        return (index+1) + (page * this.pagingData.pageCount);
      } else {
        return (index+1);
      }
    },
    // 시작날짜 picker 콜백 함수
    onChangeStartDate: function (value) {
      this.searchData.startDate = value;
    },
    // 종료날짜 picker 콜백 함수
    onChangeEndDate: function (value) {
      this.searchData.endDate = value;
    },
    // 페이징데이터 세팅
    setPagingData(param){
      this.pagingData = param;
      this.onSearch();
    },
    sortToggle(key){
      let arr = key.split('_');
      let sortKey = arr[0];
      let sortOrder = (arr[1] === 'asc') ? 'desc' : 'asc';
      let sortName = sortKey + '_' + sortOrder;

      this.sortData[sortKey] = sortName;
      this.searchData.psort = sortName;

      this.onSearch();
    },
    goDetail(idx) {
      this.idx = idx;
      this.showDetail = true;
    },
    closeDetail(isreload) {
      this.showDetail = false;
      if(isreload) {
        this.searchList();
      }
    }
  }
}
</script>

<style scoped />
