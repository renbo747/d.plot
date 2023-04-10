<template>
  <main class="mypage-order">
    <div class="container">
      <div v-if="orderList.length > 0" class="mypage-order__body">
        <template v-for="(order,index) in orderList">
          <div class="order-body__top dp-mb-30" :key="'header_' + index">
            <div class="order-date atten-new">{{$util.getFormatDate(order.orderdate, '.')}}</div>
            <div class="order-detail__btn">
              <b-button
                class="dp-btn dp-btn-icon not-hover btn-h30"
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
              <product-order
                :key="item.ordstatus"
                :product-info="item"
                :is-badge="false"
                @click="handleFooterButton(item, ...arguments)"
              />
            </li>
          </ul>
        </template>
      </div>
      <!-- 주문목록 없을 시 -->
      <div v-else class="order__no-item">
        <div class="cart__icon-container">
          <i class="cart-icon"></i>
        </div>
        <p class="no-order__text">주문내역이 없어요</p>
      </div>
    </div>
    <infinite-loading :identifier="infiniteId" @infinite="getOrdList" spinner="circles">
      <div slot="no-more"></div>
      <div slot="no-results"></div>
    </infinite-loading>
  </main>
</template>

<script src="./Index.js">
</script>

<style scoped>
.product-cart {
  margin-bottom:20px;
}
</style>