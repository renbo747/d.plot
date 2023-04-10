<template>
  <div class="dp-product-cart">
    <div class="product-cart__area d-flex align-items-center">
      <div class="product-info-area d-flex align-items-center">
        <!--체크박스 -->
        <div class="product-cart__checkbox-area">
          <base-checkbox
            v-model="productInfo.chk" 
            :label="null" 
            :id="productInfo.ordgdidx"
            @change="checkChange"
            class="product-cart__checkbox"
          />
        </div>
        <!-- 상품 썸네일 -->
        <div class="product-thumbnail-area">
          <product-thumbnail
            :has-stock="product.stockcnt"
            :thumbnail-info="{
              id: product.goodsno,
              fullpath: product.fullpath,
            }"
            :is-checkbox="false"
            style="width: 150px"
          />
        </div>
        <!-- 상품 정보 -->
        <div class="product-info">
          <div class="product-info__header d-flex align-items-star">
            <p class="product__ctg mb-0 atten-new">
              {{ product.brandname }}<span v-if="!$util.isNull(product.dealername)"> ( {{product.dealername}} )</span>
            </p>
          </div>
          <div class="product-info__body">
            <p class="product__title mb-0">{{ product.goodsname }} 
              <!-- <span v-if="!$util.isNull(product.opthtml)">( {{product.opthtml}} ) </span> -->
            </p>
              <div class="product__text d-flex mb-0" v-if="!$util.isNull(product.opthtml)" v-html="product.opthtml">
            </div>
            <p class="product__title mb-0">
              수량 : {{ product.ordcnt }}개
            </p>
            
          </div>
        </div>
      </div>
      <!-- 상품 금액 -->
      <div class="product__price-area">
        <div v-if="isPrice">
          <div class="price-number-area d-flex align-items-center">
            <span class="price-number atten-new">{{$util.maskComma(product.realgoodsamt)}}</span>
            <span class="price-unit">원</span>
          </div>
          <div class="discount-number-area d-flex justify-content-end">
            <span class="discount-number" v-if="product.realgoodsamt != product.realmarketprice">
              {{$util.maskComma(product.realmarketprice)}}
            </span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    // 체크박스 라벨
    label: {
      type: Object,
      default: null,
    },
    isCart: {
      type: Boolean,
      default: false,
    },
    // 상품 정보
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
    isCoupon: {
      type: Boolean,
      default: true,
    },
    isPrice: {
      type: Boolean,
      default: true,
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
    checkChange(){
      this.$emit("change");
    },
  },
  watch:{
    'product.chk'(val){
    }
  }
};
</script>

<style scoped></style>
