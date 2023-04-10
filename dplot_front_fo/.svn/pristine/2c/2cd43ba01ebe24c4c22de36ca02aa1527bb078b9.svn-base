<template>
  <div class="option-item">
    <div v-if="isMobile">
      <div class="item__top">
        <p class="item__title" v-html="data.opthtml"></p>
        <i class="dp-icon icon-circle-close" @click="handleRemove"></i>
      </div>
      <div class="item__bottom">
        <template v-if="data.stockcnt !== 0">
          <base-counter :item="data" @input="handleCount" />
        </template>
        <template v-else>
          <span class="dp-p-sm text-gray-700">Sold Out</span>
        </template>

        <p class="item__price">
          <span class="vollkorn" v-if="data.stockcnt !== 0">{{
            $util.maskComma(data.ordcnt * data.saleamt)
          }}</span>
          <span class="vollkorn" v-else>{{
            $util.maskComma(data.saleamt)
          }}</span>
          원
        </p>
      </div>
      <!-- Todo: 모바일 재입고 알림 버튼-->
      <div class="cart-option-button" v-if="data.stockcnt == 0">
        <b-button
          class="option-list__btn dp-btn btn-h32 not-hover"
          variant="outline-gray-800 type02"
          squared
          pill
          @click="$emit('restockClick')"
        >
          <span class="dp-caption font-weight-400 text-gray-800"
            >재입고알림</span
          >
          <i class="dp-icon sm icon-alarm"></i>
        </b-button>
      </div>
    </div>
    <div v-else class="option-item-pc">
      <div class="d-flex align-items-center justify-content-between">
        <!-- 상품 이름 -->
        <div class="product-info-area d-flex">
          <p class="item__title" v-html="data.opthtml"></p>
          <div v-if="data.stockcnt == 0">
            <b-button
              class="dp-btn btn-h32 not-hover"
              variant="outline-gray-800 type02"
              squared
              pill
              @click="$emit('restockClick')"
            >
              <span class="dp-caption font-weight-400 text-gray-800"
                >재입고알림</span
              >
              <i class="dp-icon sm icon-alarm"></i>
            </b-button>
          </div>
        </div>
        <!-- 가격 -->
        <div class="product-price-area">
          <p class="product-price mb-0">
            <span class="vollkorn" style="font-size:20px;" v-if="data.stockcnt !== 0">{{
              $util.maskComma(data.ordcnt * data.saleamt)
            }}</span>
            <span class="vollkorn" style="font-size:20px;" v-else>{{
              $util.maskComma(data.saleamt)
            }}</span>
            <span class="price-unit">원</span>
          </p>
        </div>
        <!-- 옵션 카운터 부분-->
        <div class="product-count-area">
          <template v-if="data.stockcnt !== 0">
            <base-counter :item="data" @input="handleCount" />
          </template>
          <template v-else>
            <span class="text-gray-700 sold-out">Sold Out</span>
          </template>
        </div>
        <!-- 닫기 아이콘-->
        <div class="close-icon-area">
          <i class="dp-icon icon-circle-close" style="cursor: pointer;" @click="handleRemove"></i>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      data: {
        optname: "",
        saleamt: 0,
        stockcnt: 0,
        minordcnt: 0,
        maxordcnt: 0,
        daymaxordcnt: 0,
        permaxordcnt: 0,
        dayordcnt: 0,
        perordcnt: 0,
        ordcnt: 1,
        istrash: "F",
      },
    };
  },
  props: {
    isMobile: {
      type: Boolean,
      default: false,
    },
    item: {
      type: Object,
      default: () => ({
        optname: "",
        saleamt: 0,
        stockcnt: 0,
        ordcnt: 0,
        minordcnt: 0,
        maxordcnt: 0,
        daymaxordcnt: 0,
        permaxordcnt: 0,
        dayordcnt: 0,
        perordcnt: 0,
        istrash: "F",
      }),
    },
  },
  mounted() {
    this.data = this.item;
  },
  methods: {
    handleRemove() {
      this.data.istrash = "T";
      this.$emit("delete", this.data);
    },
    handleCount(item) {
      this.data = item;
      this.$emit("change");
    },
  },
};
</script>
