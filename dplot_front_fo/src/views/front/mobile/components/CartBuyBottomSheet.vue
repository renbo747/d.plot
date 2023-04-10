<template>
  <article>
    <div class="bottom-sheet-modal cart-buy" :class="{ open: openBottomSheet }">
      <header class="bottom-sheet__header">
        <i
          class="dp-icon icon-arrow-down"
          :class="{ open: openBottomSheet }"
          @click="bottomSheetClose"
        ></i>
      </header>
      <div class="bottom-sheet__body">
        <slot></slot>
      </div>

      <div class="bottom-sheet__btn-group">
        <div class="bottom-sheet__price-summary">
          <hr class="dp-hr" />
          <div class="d-flex justify-content-between summary-contents">
            <span class="summary__text">결제예상금액</span>
            <p class="summary__price">
              <span class="atten-new">{{$util.maskComma(totalAmt)}}</span> 원
            </p>
          </div>
        </div>
        <div class="btn-group">
          <b-button
            class="dp-btn"
            variant="gray-800"
            squared
            @click="$emit('confirm')"
          >
            <span>{{ cartBtnText }}</span>
          </b-button>
        </div>
      </div>
    </div>

    <div class="bottom-sheet__backdrop" @click="bottomSheetClose"></div>
  </article>
</template>

<script>
export default {
  props: {
    isProductInfo: {
      type: Boolean,
      default: false,
    },
    cartBtnText: {
      type: String,
      default: "구매하기",
    },
    totalAmt:{
      type:Number,
      default:0
    }
  },
  data() {
    return {
      openBottomSheet: false,
    };
  },
  methods: {
    bottomSheetClose() {
      this.$emit("close");
    },
  },
};
</script>
