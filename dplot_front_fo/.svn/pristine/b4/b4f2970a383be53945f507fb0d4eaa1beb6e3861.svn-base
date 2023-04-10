<template>
  <main class="dp-cart">
    <div class="container">
      <section class="cart-empty" v-if="dealers.length == 0 && lodingSucc =='T'">
        <div class="d-flex flex-column align-items-center">
          <div class="icon-cart-wrap">
            <i class="icon-cart">
              <img
                src="@/assets/common/icon/icon-bag-black-64px.svg"
                alt="장바구니"
              />
            </i>
          </div>
          <div>
            <p class="mb-0 dp-p-sm text-gray-700 font-weight-400">
              장바구니가 비어있어요
            </p>
          </div>
        </div>
      </section>

      <section v-else-if="lodingSucc =='T'">
        <!--      전체 선택 및 선택 삭제    -->
        <header
          class="cart-header d-flex align-items-center justify-content-between"
        >
          <div class="cart-header-checkbox">
            <base-checkbox
              v-model="checkAll"
              id="allCheckbox"
              name="allCheckbox"
              label="전체 선택"
              @change="checkAllChange"
            />
          </div>
          <div class="cart-header-button">
            <b-button
              class="cart-header-btn dp-btn btn-h32"
              variant="outline-gray-800"
              squared
              @click="checkDelete"
            >
              <span class="dp-p-sm text-gray-800 font-weight-400">선택 삭제</span>
            </b-button>
          </div>
        </header>
        <hr class="dp-hr justify-margin" />
        <div v-for="dealer in dealers" :key="dealer.dealerno + '_' + skey">
          <section class="dp-panel">
            <ul class="cart__ul list-style-none">
              <li v-for="items in dealer.items" :key="items.goodsno + '_' + skey">
                <div class="cart-product">
                  <product-cart
                    :is-cart="true"
                    :is-status="false"
                    :is-summary="true"
                    :isSelectCount="false"
                    :is-price="items.detail.length == 1 ? true : false"
                    :product-info="items"
                    @optionClick="changeOption"
                    @quickBuy="quickOrder"
                    @change="itemChange"
                    @delete="itemsDelete"
                    @restockClick="openRestockModal(item)"
                  />
                  <div class="cart-option" v-if="items.detail.length > 1">
                    <div class="cart-option-list" v-for="item in items.detail" :key="item.goodsno + '_' + item.optioncode + '_' + skey">
                      <option-item 
                        :is-mobile="true" 
                        :item="item"
                        @change="itemChange"
                        @delete="itemDelete(items, ...arguments)"
                        v-if="item.istrash == 'F'"
                        @restockClick="openRestockModal(item)"/>
                      <!-- <div class="cart-option-button" v-if="item.stockcnt == 0">
                        <b-button
                          class="option-list__btn dp-btn btn-h32 not-hover"
                          variant="outline-gray-800 type02"
                          squared
                          pill
                          @click="openRestockModal(item)"
                        >
                          <span class="dp-caption font-weight-400 text-gray-800"
                            >재입고알림</span
                          >
                          <i class="dp-icon sm icon-alarm"></i>
                        </b-button>
                      </div> -->
                    </div>
                  </div>
                  <hr class="dp-hr h1 mb-0" v-if="items.detail.length > 1" />
                  <div class="cart-option-price d-flex align-items-center justify-content-between" v-if="items.detail.length > 1">
                    <div>
                      <p class="mb-0 font-weight-700 text-black">상품금액</p>
                    </div>
                    <div>
                      <p class="mb-0 font-weight-700 text-black">
                        <span class="atten-new mr02">{{$util.maskComma(items.sumsaleamt)}}</span>
                        <span class="dp-p-sm">원</span>
                      </p>
                    </div>
                  </div>
                </div>
              </li>
            </ul>
            
            <div class="cart-caption">
              <p class="dp-caption mb-0 text-gray-700 font-weight-400">
                {{dealer.dealernm}}
                <!-- <span class="text-primary ml-1">{{dealer.delivamt2 == 0 ? '무료배송' : '배송비 ' + $util.maskComma(dealer.delivamt2)}}</span> -->
              </p>
            </div>
          </section>
          <hr class="dp-hr justify-margin" />
        </div>
        <!--      결제 예정 금액     -->
        <section class="dp-panel">
          <ul class="cart-price list-style-none">
            <li class="d-flex justify-content-between mb-10">
              <span class="dp-caption font-weight-400 text-gray-700"
                >총 주문금액</span
              >
              <p class="dp-p font-weight-500 mb-0">
                <span class="atten-new">{{$util.maskComma(totprice)}}</span> 원
              </p>
            </li>
            <li class="d-flex justify-content-between mb-10">
              <span class="dp-caption font-weight-400 text-gray-700"
                >총 할인 예상금액</span
              >
              <p class="dp-p font-weight-500 mb-0">
                <span class="atten-new">{{$util.maskComma(totcpnamt)}}</span> 원
              </p>
            </li>
            <li class="d-flex justify-content-between">
              <span class="dp-caption font-weight-400 text-gray-700">총 배송비</span>
              <p class="dp-p font-weight-500 mb-0">
                <span class="atten-new">{{$util.maskComma(totdelivamt)}}</span> 원
              </p>
            </li>
          </ul>
          <hr class="dp-hr h1 mb-0" />
          <div class="cart-price-footer d-flex justify-content-between align-items-center">
            <span class="price-footer__text dp-p-sm text-black font-weight-700">결제예상금액</span>
            <p class="price-footer__total font-weight-400 text-black dp-p-sm mb-0">
              <span class="atten-new dp-title02 font-weight-700">{{$util.maskComma(totprice - totcpnamt + totdelivamt)}}</span>
              원
            </p>
          </div>
        </section>
        <hr class="dp-hr justify-margin" />
        <!--      구매하기 버튼      -->
        <!-- todo: (개발) 0421 order-button 클래스 추가 -->
        <section class="dp-panel order-button">
          <b-button
            class="dp-btn"
            variant="gray-800"
            squared
            @click="goOrder"
          >
            <span>구매하기 {{orderlist.length > 0 ? '('+orderlist.length+')': ''}}</span>
          </b-button>
        </section>
      </section>
      <!--      장바구니 슬라이드      -->
      <!-- 7/19 임시 숨김처리 -->
      <!-- <section class="dp-panel cart-slider">
        <header class="cart-slider-header">
          <h2 class="dp-title02 font-weight-700 text-black mb-0">
            이런 상품은 어떠세요?
          </h2>
        </header>
        <recopick :recopick="recopick"></recopick>
        <ul class="cart-ul dp-touch-slider list-style-none" v-if="recentItem.length > 0">
          <li
            class="cart-ul-list"
            v-for="(item, index) in recentItem"
            :key="index"
          >
            <product 
              :product-info="item" 
              :has-stock="item.stockcnt"
              :key="Date.now()"
              :height="135"
              />
          </li>
        </ul>
      </section> -->
    </div>


    <!--    옵션 선택 모달     -->
    <bottom-sheet
      class="cart-option"
      v-if="isBuyBottomSheet"
      :productInfo="currentItem"
      :items="selectedItem"
      :isProductInfo="true"
      :isCart="true"
      @close="isBuyBottomSheet = false"
      @cart="isBuyBottomSheet = false"
      @buy="handleCart"
    ></bottom-sheet>

    <!-- 장바구니 구매 모달 뜨기 전 -->
    <div class="cart-buy" v-if="!isBuyBottomSheet">
      <b-button
        class="dp-btn"
        variant="gray-800"
        squared
        @click="isCartBuyBottomSheet = true"
        style="height:62px"
      >
        <span>구매하기 {{orderlist.length > 0 ? '('+orderlist.length+')': ''}}</span>
      </b-button>
    </div>



     <!-- 장바구니 구매하기 모달 -->
    <cart-buy-bottom-sheet
      v-if="isCartBuyBottomSheet"
      :totalAmt="totprice - totcpnamt + totdelivamt"
      @confirm="goOrder()"
      @close="isCartBuyBottomSheet = false"
      :cartBtnText="chkCnt?'구매하기 ('+ chkCnt+')':'구매하기'"
    >
      <ul class="cart-price list-style-none">
        <li class="d-flex justify-content-between mb-10">
          <span class="dp-caption font-weight-400 text-gray-700"
            >총 주문금액</span
          >
          <p class="dp-p font-weight-500 mb-0">
            <span class="atten-new">{{$util.maskComma(totprice)}}</span> 원
          </p>
        </li>
        <li class="d-flex justify-content-between mb-10">
          <span class="dp-caption font-weight-400 text-gray-700"
            >총 할인 예상금액</span
          >
          <p class="dp-p font-weight-500 mb-0">
            <span class="atten-new">{{$util.maskComma(totcpnamt)}}</span> 원
          </p>
        </li>
        <li class="d-flex justify-content-between">
          <span class="dp-caption font-weight-400 text-gray-700"
            >총 배송비</span
          >
          <p class="dp-p font-weight-500 mb-0">
            <span class="atten-new">{{$util.maskComma(totdelivamt)}}</span> 원
          </p>
        </li>
      </ul>
    </cart-buy-bottom-sheet>
  </main>
</template>

<script src="@views.mobile/cart/Index.js">
</script>
