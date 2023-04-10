<template>
  <article>
    <div class="bottom-sheet-modal" :class="{ open: openBottomSheet }">
      <header class="bottom-sheet__header">
        <i
          class="dp-icon icon-arrow-down"
          :class="{ open: openBottomSheet }"
          @click="bottomSheetClose"
        ></i>
      </header>
      <div class="bottom-sheet__body">
        <div class="bottom-sheet__info" v-if="isProductInfo">
          <p class="atten-new dp-caption info__ctg" style="font-size: 14px;">
            {{ product.brandname }}
          </p>
          <p class="dp-p mb-0 text-gray-700">{{ product.goodsname }}</p>
        </div>

        <!-- 바텀 시트 바디 -->
        <slot>
          <div class="bottom-sheet__option-area">
            <!-- 2022-06-26 yiy 옵션간격이 붙어서 수정 -->
            <!--<div v-for="(option, index) in itemOption" :key="index">-->
              <base-select v-for="(option, index) in itemOption" :key="index"
                v-model="option.selected"
                :placeholder="option.optionname" 
                :is-disabled="option.detail.length == 0"
                :options="option.detail"
                @change="selectOption(option, index, 'F')"
                />
            <!--</div>-->
          </div>
          <div class="bottom-sheet__add-option-area">
            <div v-if="addItem.length > 0">
              <p class="dp-p-sm font-weight-500 dp-mb-10">추가 구성상품</p>
                <div class="bottom-sheet__option-area">
                  <base-select 
                  v-model="selectedAddItem"
                  placeholder="선택해주세요"
                  :options="addItem"
                  :is-disabled="!selectedMainItem"
                  @change="selectAddItem"
                    />
                  <!--<div v-for="(option, index) in addItemOption" :key="index">-->
                  
                  <base-select v-for="(option, index) in addItemOption" :key="index"
                    v-model="option.selected"
                    :placeholder="option.optionname"
                    :is-disabled="option.detail.length == 0 || !selectedMainItem"
                    :options="option.detail"
                    @change="selectOption(option, index, 'T')"
                    />
                  </div>
              <!--</div>-->
            </div>
          </div>

          <ul class="bottom-sheet__option-ul list-style-none">
            <li v-for="(item, index) in selectedItem" :key="index">
              <option-item
                :item="item"
                :is-mobile="true"
                @delete="handleRemove"
                v-if="item.istrash == 'F'"
                @restockClick="openRestockModal(item)"
              />
            </li>
          </ul>
        </slot>
      </div>

      <div class="bottom-sheet__btn-group">
        <div class="bottom-sheet__price-summary">
          <hr class="dp-hr" />
          <div class="d-flex justify-content-between">
            <span class="summary__text">상품금액</span>
            <p class="summary__price">
              <span class="vollkorn" style="font-size: 22px;">{{$util.maskComma(getTotalOptionPrice)}}</span> 원
            </p>
          </div>
        </div>
        <div class="btn-group">
          <slot name="bottom-sheet-footer">
            <b-button
              class="dp-btn"
              variant="outline-gray-800 not-hover"
              squared
              @click="cart"
            >
              <span>{{isCart ? '취소' : '장바구니'}}</span>
            </b-button>
            <b-button
              class="dp-btn"
              variant="gray-800"
              squared
              @click="buy"
            >
              <span>{{isCart ? '변경' : '바로구매'}}</span>
            </b-button>
          </slot>
        </div>
      </div>
    </div>

    <div class="bottom-sheet__backdrop" @click="bottomSheetClose"></div>
  </article>
</template>

<script src="@views.mobile/components/BottomSheet.js">
</script>

<style scoped>
.bottom-sheet-modal {z-index: 2000 !important;}
</style>