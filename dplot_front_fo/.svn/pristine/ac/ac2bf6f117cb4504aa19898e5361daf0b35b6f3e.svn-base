<template>
  <div class="product-cart" :class="{ thumbnailHidden: isThumbnailHidden }">
    <div v-if="isHeader" class="product-cart__header dp-mb-20 d-flex">
      <base-checkbox 
            v-model="productInfo.chk" 
            :label="product.goodsname" 
            :id="productInfo.ordgdidx"
            @change="checkChange"
      />
      <i class="dp-icon icon-close-black" v-if="isIcon"></i>
    </div>
    <div class="product-cart__body">
      <product-thumbnail
        v-if="isThumbnail"
        :has-stock="product.stockcnt"
        :thumbnail-info="{
          id: product.goodsno,
          fullpath: product.fullpath,
        }"
        :is-checkbox="false"
        class="mr-3"
      />
      <div class="product-info">
        <p class="product-text mb-1 atten-new product__ctg">{{ productInfo.brandname }}<span v-if="!$util.isNull(product.dealername)"> ( {{product.dealername}} )</span></p>
          <div class="product-option">
            <!-- <p class="product__title mb-0">{{ product.goodsname }}</p>
            <div class="product__text d-flex mb-0" v-if="product.opthtml != ''" v-html="product.opthtml">
            </div> -->
            <ul class="list-style-none">
              <!-- <li class="product-text dp-p-sm">
                {{ product.goodsname }}
              </li> -->
              <li v-if="product.opthtml != ''" v-html="product.opthtml">
              </li>
              <li>수량 : {{ productInfo.ordcnt }}</li>
            </ul>
          </div>
        <!-- <p class="mb-1 atten-new product__ctg">수량 : {{ productInfo.ordcnt }}</p> -->

      </div>
    </div>
  </div>
</template>
<script>
export default {
  props: {
    // 헤더에 상품이름과 체크박스가 있을 때
    isHeader: {
      type: Boolean,
      default: false,
    },
    isIcon: {
      type: Boolean,
      default: true,
    },
    // 상품의 상태를 나타낼 때
    isStatus: {
      type: Boolean,
      default: true,
    },
    isPrice: {
      type: Boolean,
      default: true,
    },
    isTitle: {
      type: Boolean,
      default: true,
    },
    isThumbnail: {
      type: Boolean,
      default: true,
    },
    // 공통요소인 카테고리와 가격
    productInfo: {
      type: Object,
      default: () => ({
        chk:false,
        ordgdidx:"",
        ordno: "",
        goodsno: "",
        goodsname: "",
        fullpath: require("@/assets/mobile/img/shop/img-shop-product01.jpg"),
        brandname: "Artemide",
        goodaname: "피어스 마이크로 펜던램프",
        marketprice: 2119000,
        realgoodsamt: 2600000,
        coupon: 0,
        delivamt: 1000,
        stockcnt: 0,
        optioncode: "",
      }),
    },
    // 쎔네일이 사라졌을 경우 레이아웃 부분 변경을 위해
    isThumbnailHidden: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      product : {
        chk: false,
        goodsname : '',
        detail :[{
          saleamt : 0,
          marketprice : 0,
          ordcnt : 0
        }]
      }
    };
  },
  mounted() {
    this.product = this.productInfo;
  },
  methods: {
    handleCount() {
      this.$emit("count", this.count);
    },
    handleStatusBtn(status) {
      this.$emit("statusClick", status);
    },
    checkChange(){
      this.$emit("change");

    }
    
  },
};
</script>
