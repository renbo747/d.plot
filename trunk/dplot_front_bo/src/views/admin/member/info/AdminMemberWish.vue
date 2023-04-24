<template>
  <div class="tab-area" style="height: calc(90vh - 246px);">
    <div class="clearfix">
      <div class="bar-title small fl">찜한상품</div>
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
    <div class="caption-group clearfix">
      <div class="total-group fl">
        <span class="total">전체 <strong>{{ totalcnt }}</strong>건</span>
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
      <caption>찜한상품 목록</caption>
      <colgroup>
        <col width="4%" /><!-- No -->
        <col width="10%" /><!-- 찜한일자 -->
        <col width="10%" /><!-- 판매구분 -->
        <col width="10%" /><!-- 파트너사 -->
        <col width="10%" /><!-- 상품코드 -->
        <col width="9%" /><!-- 단품코드 -->
        <col width="62px" /><!-- 상품이미지 -->
        <col width="" /><!-- 상품명 -->
        <col width="10%" /><!-- 옵션 -->
        <col width="10%" /><!-- 판매가 -->
      </colgroup>
      <thead>
      <tr>
        <th>No</th>
        <th>찜한일자
          <button type="button" class="sort" :value="sortData.regdate"
                  :class="[{up : sortData.regdate=== 'regdate_asc'}, {down : sortData.regdate === 'regdate_desc'}]"
                  @click="sortToggle(sortData.regdate)"></button>
        </th>
        <th>판매구분</th>
        <th>파트너사</th>
        <th>상품코드</th>
        <th>단품코드</th>
        <th colspan="2">상품명</th>
        <th>옵션</th>
        <th>판매가</th>
      </tr>
      </thead>
      <tbody v-if="wishList.length > 0">
      <tr v-for="(item, index) in wishList" :key="index">
        <td>{{ loopNumberForPaging(index) }}</td>
        <td>{{ item.regdate}}</td>
        <td>{{ item.ispbgoodsname }}</td>
        <td>{{ item.partnersname }}</td>
        <td>{{ item.goodscode }}</td>
        <td>{{ item.optioncode }}</td>
        <td>
          <div class="img-thumb size60" :class="{'no-image': $util.isNull(item.imagepath)}">
            <img :src="item.imagepath" v-if="!$util.isNull(item.imagepath)">
          </div>
        </td>
        <td class="left no-left"><a class="link" @click="goGoodsDetail(item.goodsno)">{{ item.goodsname }}</a></td>
        <td>{{ item.optionname }}</td>
        <td class="right">{{ item.price }}</td>
      </tr>
      </tbody>
      <tbody v-else>
      <tr>
        <td colspan="9">조회 결과가 존재하지 않습니다.</td>
      </tr>
      </tbody>
    </table>
    <div class="bottom-group">
      <common-page-navigator :pagingData="pagingData" v-on:setPagingData="setPagingData"/>
    </div>
    <GoodsDetail v-if="isGoodsDetailShow" :activeGoodsNo="activeGoodsNo" @closePopup="closeGoodsDetail"></GoodsDetail>
  </div>
</template>

<script>
import CommonDatePicker from "@views.admin/common/CommonDatePicker";
import CommonPageNavigator from "@views.admin/common/CommonPageNavigator";
import GoodsDetail from "@views.admin/goods/manage/GoodsDetail.vue";

export default {
  name: "AdminMemberWish",
  props: ['activeUserNo'],
  components: { CommonDatePicker, CommonPageNavigator, GoodsDetail},
  data() {
    return {
      wishList: [],
      totalcnt: 0,
      isGoodsDetailShow: false,
      activeGoodsNo: null,
      searchData: {
        userno: this.activeUserNo,
        startDate: '',          // 조회시작날짜
        endDate: '',            // 조회종료날짜
        psort: 'regdate_desc',   // 정렬
      },
      sortData: {
        regdate : 'regdate_desc'
      },
      pagingData: {
        pageCount: 20,      // 페이징 옵션(최대수)
        page: 1,            // 현재 페이지
        listCount: 0        // 총 수량
      },
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
      this.$http.post('/admin/member/wish/list', params).then(result => {
        this.wishList = result.data.list;
        this.totalcnt = result.data.count;
        this.pagingData.listCount = this.totalcnt;
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
    goGoodsDetail: function(value) {
      // $('body').find('.modal-content').css('width', '1600px');
      this.activeGoodsNo = value;
      this.isGoodsDetailShow = true;
    },
    closeGoodsDetail(){
      // $('body').find('.modal-content').css('width', '1600px');
      this.isGoodsDetailShow = false;
      this.onSearch();
    },
  }
}
</script>

<style scoped />