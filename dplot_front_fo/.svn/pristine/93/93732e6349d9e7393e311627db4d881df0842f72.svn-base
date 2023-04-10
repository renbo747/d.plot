<template>
  <article>
    <div class="bottom-sheet-modal-pc" :class="{ open: openBottomSheet }">
      <div class="container">
        <div class="container-inner">
          <div class="bottom-sheet__header">
            <b-button
              class="dp-btn dp-btn-icon text-white btn-h48"
              variant="gray-800"
              squared
              @click="bottomSheetClose"
            >
              <span>{{btnTitle}}</span>
              <i class="dp-icon md icon-arrow-down white"></i>
            </b-button>
          </div>
        </div>
      </div>
      <div class="container bottom-sheet-modal-container">
        <div class="container-inner">
          <div class="bottom-sheet__body no-scrollbar">
            <div class="bottom-sheet__info" v-if="isProductInfo">
              <p class="atten-new dp-caption info__ctg">
                {{ product.brandname }}
              </p>
              <p class="mb-0" style="font-size:24px;">{{ product.goodsname }}</p>
            </div>

            <!-- 바텀 시트 바디 -->
            <slot>
              <div class="bottom-sheet__option-area d-flex">
                <base-dropdown 
                v-for="(option, index) in itemOption" :key="index"
                v-model="option.selected"
                :placeholder="option.optionname" 
                :options="option.detail"
                :is-disabled="option.detail.length == 0"
                :option-hidden="true"
                @change="selectOption2(option, index, 'F', ...arguments)" />
              </div>
              <div class="bottom-sheet__add-option-area">
                <div v-if="addItem.length > 0">
                  <p class="dp-p-sm font-weight-500 dp-mb-10" >추가 구성상품</p>
                  <div class="bottom-sheet__option-area d-flex">
                    <base-dropdown 
                    placeholder="선택해주세요"
                    v-model="selectedAddItem"
                    :options="addItem"
                    :is-disabled="!selectedMainItem"
                    @change="selectAddItem2" />
                    <base-dropdown 
                    v-for="(option, index) in addItemOption" :key="index"
                    v-model="option.selected"
                    :placeholder="option.optionname" 
                    :options="option.detail"
                    :is-disabled="option.detail.length == 0"
                    :option-hidden="true"
                    @change="selectOption2(option, index, 'T', ...arguments)" />
                  </div>
                </div>
              </div>
            </slot>
            <div class="bottom-sheet-flex d-flex">
              <slot>
                <ul class="bottom-sheet__option-ul list-style-none">
                  <li v-for="(item, index) in selectedItem" :key="index">
                    <option-item
                      :item="item"
                      :is-mobile="false"
                      @delete="handleRemove"
                      v-if="item.istrash == 'F'"
                      @restockClick="openRestockModal(item)"
                    />
                  </li>
                </ul>
              </slot>

              <div class="bottom-sheet__btn-group">
                <div class="bottom-sheet__price-summary">
                  <header class="bottom-price-header">
                    <p class="fz-20 font-weight-500 text-black mb-0">
                      <!-- TODO: YIY 퍼블상 결제금액 현 문구는 총 주문금액 -->
                      총 주문금액
                    </p>
                  </header>
                  <hr class="dp-hr" />
                  <footer class="dp-cart__price-footer d-flex justify-content-end">
                    <p class="footer-summary__price mb-0 text-black">
                      <span class="vollkorn" style="font-size:20px;">{{$util.maskComma(getTotalOptionPrice)}}</span> 원
                    </p>
                  </footer>
                  <!-- <ul class="bottom-sheet__price-summary-list list-style-none">
                    <li class="d-flex justify-content-between">
                      <span class="summary__text">총 상품금액</span>
                      <p class="summary__price">
                        <span class="atten-new">{{$util.maskComma(getTotalOptionPrice)}}</span> 원
                      </p>
                    </li>
                    <li class="d-flex justify-content-between">
                      <span class="summary__text">총 할인 예상금액</span>
                      <p class="summary__price">
                        <span class="atten-new">{{$util.maskComma(getTotalDiscountPrice)}}</span> 원
                      </p>
                    </li>
                    <li class="d-flex justify-content-between">
                      <span class="summary__text">총 배송비</span>
                      <p class="summary__price">
                        <span class="atten-new">{{$util.maskComma(getDelivAmt)}}</span> 원
                      </p>
                    </li>
                  </ul>
                  <hr class="dp-hr" />
                  <footer class="bottom-price-footer d-flex justify-content-between" >
                    <span class="footer-summary__text dp-p text-black font-weight-500" >합계</span>
                    <p class="footer-summary__price font-weight-700 text-black dp-title">
                      <span class="atten-new">{{$util.maskComma(getTotalOptionPrice + getDelivAmt)}}</span> 원
                    </p>
                  </footer> -->
                </div>
                <div class="btn-group">
                  <slot name="bottom-sheet-footer">
                    <b-button
                      class="dp-btn mr-10 btn-h48"
                      variant="outline-gray-800 not-hover"
                      squared
                      @click="cart"
                    >
                    <span class="dp-p text-black font-weight-400">장바구니</span >
                    </b-button>
                    <b-button
                      class="dp-btn btn-h48"
                      variant="gray-800"
                      squared
                      @click="buy"
                    >
                    <span class="dp-p font-weight-400">바로구매</span>
                    </b-button>
                  </slot>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </article>
</template>

<script src="@views.mobile/components/BottomSheet.js">
</script>

<style scoped>
.modal-open .bottom-sheet-modal-pc{
  padding-right: 17px !important;
}
</style>