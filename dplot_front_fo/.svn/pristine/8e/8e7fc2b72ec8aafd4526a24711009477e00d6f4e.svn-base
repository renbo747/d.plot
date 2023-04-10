<template>
  <main class="dp-cart pc-top-padding">
    <div class="container">
      <div class="container-inner">
        <header class="dp-cart-header">
          <h1 class="dp-cart-title mb-0 font-weight-700 text-black">
            장바구니
          </h1>
        </header>
      </div>
    </div>
    <layout-right>
      <template #left-contents>
        <section class="dp-cart-product">
          <header class="dp-cart-product-header">
            <div class="d-flex align-items-center justify-content-between">
              <div class="dp-cart-product-checkbox">
                <base-checkbox 
                v-model="checkAll"
                id="allCheckbox"
                name="allCheckbox"
                label="전체 선택"
                @change="checkAllChange" />
              </div>
              <div class="dp-cart-product-delete">
                <p class="dp-cart-product-delete__p mb-0 font-weight-500 text-gray-700" @click="checkDelete">
                  선택삭제
                </p>
              </div>
            </div>
          </header>
          <div>
            <ul class="dp-cart-product-menu-wrap d-flex align-items-center justify-content-center list-style-none" >
              <li class="dp-cart-product-menu product-menu-info">
                <p class="dp-cart-product-menu__title mb-0 text-gray-700">상품정보</p>
              </li>
              <li class="dp-cart-product-menu product-menu-price">
                <p class="dp-cart-product-menu__title mb-0 text-gray-700">판매금액</p>
              </li>
              <li class="dp-cart-product-menu product-menu-count">
                <p class="dp-cart-product-menu__title mb-0 text-gray-700">수량</p>
              </li>
              <li class="dp-cart-product-menu product-menu-select">
                <p class="dp-cart-product-menu__title mb-0 text-gray-700">선택</p>
              </li>
            </ul>
            <div class="dp-cart-product-list-wrap" v-if="dealers.length > 0">
              <template v-for="(dealer, index) in dealers">
                <div class="pt-pb-30" :key="dealer.dealerno + '_' + skey">
                  <div class="dp-cart-product-list mt-20" v-for="items in dealer.items" :key="items.goodsno + '_' + skey">
                    <product-cart-pc
                      :is-cart="true"
                      :product-info="items"
                      :is-price="items.detail.length == 1 ? true : false"
                      @optionClick="changeOptionModal"
                      @quickBuy="quickOrder"
                      @change="itemChange"
                      @delete="itemsDelete"
                    />
                    <div class="dp-cart-product-list__option mt-20 ml-30" v-if="items.detail.length > 1">
                      <div class="list__option" v-for="item in items.detail" :key="item.goodsno + '_' + item.optioncode + '_' + skey">
                        <option-item 
                          :is-mobile="false" 
                          :item="item" 
                          @change="itemChange" 
                          @delete="itemDelete(items, ...arguments)"
                          @restockClick="openRestockModal(item)"
                          v-if="item.istrash == 'F'"/>
                      </div>
                      <div class="dp-cart-product-list__option__total d-flex align-items-center justify-content-end">
                        <div>
                          <p class="option__total__price font-weight-500 text-black mb-0">
                            상품금액
                          </p>
                        </div>
                        <div>
                          <p class="option__total__unit font-weight-500 text-black mb-0">
                            <span class="option__total__num atten-new font-weight-700">{{$util.maskComma(items.sumsaleamt)}}</span
                            >원
                          </p>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="cart-caption">
                    <p class="cart-caption-title mb-0 text-gray-700 font-weight-400">
                      {{dealer.dealernm}} 
                      <!-- <span class="text-primary ml-1">{{dealer.delivamt2 == 0 ? '무료배송' : '배송비 ' + $util.maskComma(dealer.delivamt2)}}</span> -->
                    </p>
                  </div>
                </div>
                <hr class="dp-hr h1" :key="'line' + index"/>
              </template>
            </div>
            <div class="empty-cart"  v-else-if="lodingSucc =='T'">
              <div class="empty-cart-area d-flex flex-column align-items-center" >
                <div class="empty-cart-icon-wrap d-flex align-items-center justify-content-center" >
                  <i class="dp-icon h50 icon-bag"></i>
                </div>
                <div class="empty-text">
                  <p class="dp-p text-gray-700 font-weight-400 mb-0">
                    장바구니가 비어있어요
                  </p>
                </div>
              </div>
            </div>
          </div>
        </section>
      </template>
      <template #right-contents>
        <div class="dp-cart__btn-group">
          <div class="dp-cart__price-summary">
            <header class="dp-cart__price-header">
              <p class="fz-20 font-weight-500 text-black mb-0">주문금액</p>
            </header>
            <hr class="dp-hr dp-hr-receipt" />
            <ul class="dp-cart__price-summary-list list-style-none">
              <li class="d-flex justify-content-between">
                <span class="summary__text">총 주문금액</span>
                <p class="summary__price">
                  <span class="atten-new">{{$util.maskComma(totprice)}}</span> 원
                </p>
              </li>
              <li class="d-flex justify-content-between">
                <span class="summary__text">총 할인 예상금액</span>
                <p class="summary__price">
                  <span class="atten-new">{{$util.maskComma(totcpnamt)}}</span> 원
                </p>
              </li>
              <li class="d-flex justify-content-between">
                <span class="summary__text">총 배송비</span>
                <p class="summary__price">
                  <span class="atten-new">{{$util.maskComma(totdelivamt)}}</span> 원
                </p>
              </li>
            </ul>
            <hr class="dp-hr h1" />
            <footer class="dp-cart__price-footer d-flex justify-content-between">
              <span class="footer-summary__text dp-p text-black font-weight-500">결제예상금액</span>
              <p
                class="footer-summary__price mb-0 font-weight-700 text-black dp-title"
              >
                <span class="atten-new">{{$util.maskComma(totprice - totcpnamt + totdelivamt)}}</span> 원
              </p>
            </footer>
          </div>
          <div class="btn-group">
            <b-button
              class="dp-btn btn-h48"
              variant="gray-800"
              squared
              @click="goOrder"
            >
              <span class="dp-p font-weight-400">구매하기{{orderlist.length > 0 ? '('+orderlist.length+')': ''}}</span>
            </b-button>
          </div>
        </div>
      </template>
    </layout-right>
    <!-- 7/19 임시숨김처리 -->
    <!-- <div class="container">
      <div class="container-inner dp-cart-slide">
        <header class="dp-cart-slide-header">
          <h2 class="dp-cart-slide-title mb-0">이런 상품은 어떠세요?</h2>
        </header>
        <recopick :recopick="recopick"></recopick>
        <div class="dp-cart-slide__body" v-if="recentItem.length > 0">
          <swiper class="swiper d-flex" :options="swiperCartOption">
            <swiper-slide v-for="(item, index) in recentItem" :key="index">
              <div class="dp-cart-slide__item">
                <product 
                  :product-info="item" 
                  :height="300"/>
              </div>
            </swiper-slide>
            <div class="swiper-pagination" slot="pagination"></div>
          </swiper>
        </div>
      </div>
    </div> -->

  </main>
</template>

<script src="@views.mobile/cart/Index.js">
</script>

<style>
</style>