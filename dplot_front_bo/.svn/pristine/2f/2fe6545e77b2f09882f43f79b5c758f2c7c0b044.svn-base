<template>
  <div class="tab-area" style="height: calc(90vh - 246px);">
    <div class="clearfix">
      <div class="bar-title small fl">장바구니</div>
    </div>
    <div class="caption-group clearfix">
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
    <div class="scroll-x">
      <table cellpadding="0" cellspacing="0" class="data-tb align-c" style="width: 120%;">
        <caption>장바구니 목록</caption>
        <colgroup>
          <col width="2.5%" /><!-- No -->
          <col width="6.5%" /><!-- 담은일자 -->
          <col width="6%" /><!-- 경과일수 -->
          <col width="5%" /><!-- 판매구분 -->
          <col width="8%" /><!-- 파트너사 -->
          <col width="7%" /><!-- 상품코드 -->
          <col width="7%" /><!-- 단품코드 -->
          <col width="62px" /><!-- 상품이미지 -->
          <col width="" /><!-- 상품명 -->
          <col width="7%" /><!-- 옵션 -->
          <col width="3.5%" /><!-- 수량 -->
          <col width="6%" /><!-- 판매가 -->
          <col width="6.5%" /><!-- 상품금액 -->
          <col width="5%" /><!-- 배송유형 -->
          <col width="5%" /><!-- 배송조건 -->
          <col width="4%" /><!-- 배송비 -->
        </colgroup>
        <thead>
        <tr>
          <th>No</th>
          <th>담은일자
            <button type="button"
                    :value="sortData.regdate"
                    class="sort"
                    :class="[{up : sortData.regdate === 'regdate_asc'}, {down : sortData.regdate === 'regdate_desc'}]"
                    @click="sortToggle(sortData.regdate)">
            </button>
          </th>
          <th>경과일수
            <button type="button"
                    :value="sortData.elapsedday"
                    class="sort"
                    :class="[{up : sortData.elapsedday === 'elapsedday_asc'}, {down : sortData.elapsedday === 'elapsedday_desc'}]"
                    @click="sortToggle(sortData.elapsedday)">
            </button>
          </th>
          <th>판매구분</th>
          <th>파트너사</th>
          <th>상품코드
            <button type="button"
                    :value="sortData.goodsno"
                    class="sort"
                    :class="[{up : sortData.goodsno === 'goodsno_asc'}, {down : sortData.goodsno === 'goodsno_desc'}]"
                    @click="sortToggle(sortData.goodsno)">
            </button>
          </th>
          <th>단품코드
            <button type="button"
                    :value="sortData.optioncode"
                    class="sort"
                    :class="[{up : sortData.optioncode === 'optioncode_asc'}, {down : sortData.optioncode === 'optioncode_desc'}]"
                    @click="sortToggle(sortData.optioncode)">
            </button>
          </th>
          <th colspan="2">상품명</th>
          <th>옵션</th>
          <th>수량</th>
          <th>정상가
            <button type="button"
                    :value="sortData.marketprice"
                    class="sort"
                    :class="[{up : sortData.marketprice === 'marketprice_asc'}, {down : sortData.marketprice === 'marketprice_desc'}]"
                    @click="sortToggle(sortData.marketprice)">
            </button>
          </th>
          <th>판매가
            <button type="button"
                    :value="sortData.price"
                    class="sort"
                    :class="[{up : sortData.price === 'price_asc'}, {down : sortData.price === 'price_desc'}]"
                    @click="sortToggle(sortData.price)">
            </button>
          </th>
          <th>배송유형</th>
          <th>배송조건</th>
          <th>배송비</th>
        </tr>
        </thead>
        <tbody v-if="listData.length > 0">
        <tr v-for="(item, index) in listData" :key="index">
          <td>{{ loopNumberForPaging(index) }}</td>
          <td>{{ item.regdate }}</td>
          <td>{{ item.elapsedday }}</td>
          <td>{{ item.ispbgoodsname }}</td>
          <td>{{ item.dealername }}</td>
          <td>{{ item.goodsno }}</td>
          <td>{{ item.optioncode }}</td>
          <td>
            <div class="img-thumb size60" :class="{'no-image': $util.isNull(item.imgurl)}">
              <img :src="item.imgurl" v-if="!$util.isNull(item.imgurl)" alt="">
            </div>
          </td>
          <td class="left no-left"><a @click="goGoodsDetail(item.goodsno)" class="link">{{ item.goodsname }}</a></td>
          <td>{{ item.optionname }}</td>
          <td>{{ item.ordcnt }}</td>
          <td class="right">{{ item.marketpriceformat }}</td>
          <td class="right">{{ item.priceformat }}</td>
          <td>{{ item.delivtypename }}</td>
          <td>{{ item.delivfaretypename }}</td>
          <td class="right">{{ item.delivfare }}</td>
        </tr>
        </tbody>
        <tbody v-else>
        <tr>
          <td colspan="17">조회 결과가 존재하지 않습니다.</td>
        </tr>
        </tbody>
      </table>
    </div>
    <div class="bottom-group">
      <CommonPageNavigator :pagingData="pagingData" v-on:setPagingData="setPagingData"/>
    </div>
    <GoodsDetail v-if="isGoodsDetailShow" :activeGoodsNo="activeGoodsNo" @closePopup="closeGoodsDetail"></GoodsDetail>
  </div>
</template>

<script>
import CommonPageNavigator from "@views.admin/common/CommonPageNavigator";
import GoodsDetail from "@views.admin/goods/manage/GoodsDetail.vue";

export default {
  name: "AdminMemberCart",
  props: ['activeUserNo'],
  components: {CommonPageNavigator, GoodsDetail},
  data() {
    return {
      isGoodsDetailShow: false,
      activeGoodsNo: null,
      listData: [],
      totalCnt: 0,
      searchData: {
        userno : this.activeUserNo
      },
      pagingData: {
        pageCount: 20,        // 페이징 옵션(최대수)
        page: 1,              // 현재 페이지
        listCount: 0          // 전체 수
      },
      sortData: {
        regdate : 'regdate_desc',
        elapsedday : 'elapsedday_desc',
        goodsno : 'goodsno_desc',
        optioncode : 'optioncode_desc',
        marketprice : 'marketprice_desc',
        price : 'price_desc',
      },
    }
  },
  mounted() {
    this.onSearch();
  },
  methods : {
    onSearch(){
      let params = Object.assign(this.searchData, this.pagingData);
      this.$http.post('/admin/member/cart/list', params).then(result => {
        this.listData = result.data.list;
        this.totalCnt = result.data.count;
        this.pagingData.listCount = this.totalCnt;
      }).catch(error => {
        this.$util.debug(error);
      })
    },
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
    setPagingData(param) {
      this.pagingData = param;
      this.onSearch();
    },
    loopNumberForPaging(index) {
      if(this.pagingData.page > 1){
        let page = this.pagingData.page - 1;
        return (index+1) + (page * this.pagingData.pageCount);
      } else {
        return (index+1);
      }
    },
    goGoodsDetail: function(value) {
      this.activeGoodsNo = value;
      this.isGoodsDetailShow = true;
    },
    closeGoodsDetail(){
      this.isGoodsDetailShow = false;
      this.onSearch();
    },
  }
}
</script>

<style scoped />