<template>
  <div class="aside-article mypage-order">
    <header class="aside-article__header">
      <div class="header-title">주문목록</div>
    </header>
    <div>
      <div class="mypage-order__header">
        <base-dropdown
          class="order-select"
          placeholder="조회기간"
          v-model="searchData.date"
          :options="orderPreiod"
          @change="changeSearch"
        />
        <base-dropdown
          class="order-select"
          placeholder="주문상태"
          v-model="searchData.state"
          :options="orderState"
          @change="changeSearch"
        />
      </div>
      <div v-if="orderList.length > 0" class="mypage-order__body">
        <template v-for="(order,index) in orderList">
          <div class="order-body__top dp-mb-30" :key="'header_' + index">
            <div class="order-date atten-new">{{$util.getFormatDate(order.orderdate, '.')}}</div>
            <div class="order-detail__btn">
              <b-button
                class="dp-btn dp-btn-icon not-hover btn-h32"
                variant="outline-gray-400"
                squared
                pill
                @click="goToDetail(order)"
              >
                <span class="text-gray-800">상세보기</span>
                <i class="dp-icon icon-arrow-right sm"></i>
              </b-button>
            </div>
          </div>
          <ul class="order__ul list-style-none" :key="'item_' + index">
            <li v-for="(item, index2) in order.items" :key="index2">
              <div class="product-item">
                <div class="item-box">
                  <div class="item-thumbnail">
                    <product-thumbnail 
                      :thumbnail-info="{ id: item.goodsno,fullpath: item.fullpath}"
                      :to="{name:'shop-detail',params:{pid:item.goodscode}}" 
                      :height="150"/>
                  </div>
                  <div class="item-info">
                    <p class="item-status">{{ item.statusnm }}</p>
                    <p class="item-ctg">{{ item.brandname }}</p>
                    <p class="item__p">{{ item.goodsname}}</p>
                    <p class="item__p option d-flex align-items-center" v-html="item.opthtml">
                    </p>
                  </div>
                </div>
                <div class="item-count">
                  <p class="item__p text-center">{{ item.ordcnt }}개</p>
                </div>
                <div class="item-price">
                  <p class="price__p text-center mb-0">
                    <span class="price__span atten-new">{{$util.maskComma(item.realgoodsamt)}}</span>원
                  </p>
                </div>
                <div class="item-button">
                  <template  v-for="(button, index3) in item.buttonData">
                  <b-button
                    v-if="!(item.isreview === 'T' && button.text === '리뷰작성')"
                    :key="index3"
                    class="dp-btn btn-h32"
                    variant="outline-gray-400"
                    squared
                    @click="handleFooterButton(item, button.text)"
                  >
                    <span class="text-gray-800">{{ button.text }}</span>
                  </b-button>
                  </template>
                </div>
              </div>
            </li>
          </ul>
        </template>
        <div class="restock-pagination dp-panel pb-0 d-flex">
          <div class="restock-pagination-wrap">
            <base-pagination 
            :currentPage="pagingData.currentPage"
            :listTotal="pagingData.listTotal"
            :listCnt="pagingData.listCnt"
            @changePage="changePage"/>
          </div>
        </div>
      </div>
      <!-- 주문목록 없을 시 -->
      <div v-if="isEnd && orderList.length <= 0" class="order__no-item">
        <div class="cart__icon-container">
          <i class="cart-icon"></i>
        </div>
        <p class="no-order__text">주문내역이 없어요</p>
      </div>
    </div>
  </div>
</template>

<script src="./Index.js">
</script>

<style scoped>
.restock-pagination {
  justify-content: center;
}
.mypage-order .product-item {
  margin-bottom: 30px;
}
</style>