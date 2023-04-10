<template>
  <div class="dp-product" :class="{ horizontal: isHorizontal }">
    <product-thumbnail
      :thumbnail-info="{ id: product.goodsno, fullpath: product.fullpath }"
      :to="{ name: 'shop-detail', params: { pid: product.goodscode } }"
      :hasStock="product.stockcnt"
      :height="height"
      :has-end="
        product.goodsselltype == 'GST003' || product.goodsselltype == 'GST006'
          ? true
          : false
      "
      style="cursor: pointer"
    />
    <div class="product__contents" @click="goLink">
      <div class="text_inner">
        <p class="product__ctg atten-new">{{ product.brandname }}</p>
        <h4 class="product__title">{{ product.goodsname }}</h4>
        <div class="product__price__like">
          <div class="product__price__wrap">
            <div class="product__price-area">
              <!-- <p
                class="product__price"
                :style="[
                  $util.saleRate(product.marketprice, product.saleamt) > 0
                    ? { marginBottom: '2px' }
                    : { marginBottom: '14px' },
                ]"
              > -->
              
              <p class="product__price">
                <span class="product__price-number vollkorn">{{
                  $util.maskComma(product.saleamt)
                }}</span>
                <span class="product__price-unit">원</span>
              </p>

              <span
                v-if="$util.saleRate(product.marketprice, product.saleamt) > 0"
                class="product__discount-percentage vollkorn"
                >{{ $util.saleRate(product.marketprice, product.saleamt) }}%</span
              >
            </div>

            <!-- todo: (수정) v-if 위치 변경 0722 -->
            <p class="product__discount-area">
              <template v-if="product.saleamt != product.marketprice">
                <span class="product__discount-number vollkorn">{{
                  $util.maskComma(product.marketprice)
                }}</span>
                <span class="product__discount-unit">원</span>
              </template>
            </p>
          </div>
          <div class="product__color__area" v-if="isCart">
            <ul class="color__ul list-style-none">
              <li
                v-for="(item, index) in product.colorData"
                :key="index"
                class="color__list"
              >
                <div
                  class="color__circle mb-0"
                  :style="{
                    backgroundColor: '#' + item,
                    borderColor: '#' + item,
                  }"
                ></div>
              </li>
            </ul>
          </div>
          <div class="header__badge-group no-scrollbar" v-if="isBedge">
          <span class="dp-badge best atten-new" v-if="product.isbestbadge == 'T' || product.isbest == 'T'">BEST</span>
            <span class="dp-badge atten-new" v-if="product.isnewbadge == 'T' || product.isnew == 'T'">NEW</span>
<!--             <span class="dp-badge atten-new" v-if="product.issalebadge == 'T'">SALE</span> -->
            <span class="dp-badge atten-new" v-if="(product.iscouponbadge == 'T' || product.iscoupon == 'T')">COUPON</span>
           
            <span v-if="!$util.isNull(product.mugicontype)">
              <span v-for="(badge, index) in product.mugicontype.split(',')" :key="index" style="display:inline">
                <span class="dp-badge atten-new" v-if="badge == 'GCT001'">EXCLUSIVE</span>
                <span class="dp-badge atten-new" v-if="badge == 'GCT002'">LIMITED</span>
                <span class="dp-badge atten-new" v-if="badge == 'GCT003'">PRE-ORDER</span>
              </span>
            </span>
            <span v-else>
                <span class="dp-badge atten-new" v-if="product.isexclusive == 'T'">EXCLUSIVE</span>
                <span class="dp-badge atten-new" v-if="product.islimited == 'T'">LIMITED</span>
                <span class="dp-badge atten-new" v-if="product.ispreorder == 'T'">PRE-ORDER</span>
            </span>
          </div>
        </div>
      </div>
      <i
        class="product__like"
        :class="{ on: product.iswished == 'T' }"
        @click.stop="changeWish()"
      ></i>
    </div>
  </div>
</template>

<script>
import ProductThumbnail from "@views.common/components/shop/ProductThumbnail";
export default {
  components: { ProductThumbnail },
  props: {
    product_type: {
      type : String,
      default : 'normal'
    },
    isBedge: {
      type: Boolean,
      default: false
    },
    isHorizontal: {
      type: Boolean,
      default: false,
    },
    productInfo: {
      type: Object,
    },
    height: {
      type: Number,
      default: 0,
    },
    isCart: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      isLike: false,
      colorData: [
        {
          id: "color01",
          bgColor: "#aa0000",
          borderColor: "#aa0000",
          text: "색상명",
        },
        {
          id: "color02",
          bgColor: "#df0000",
          borderColor: "#df0000",
          text: "색상명",
        },
        {
          id: "color03",
          bgColor: "#eb5b00",
          borderColor: "#eb5b00",
          text: "색상명",
        },
        {
          id: "color04",
          bgColor: "#ffd800",
          borderColor: "#ffd800",
          text: "색상명",
        },
        {
          id: "color05",
          bgColor: "#5c3500",
          borderColor: "#5c3500",
          text: "색상명",
        },
        {
          id: "color06",
          bgColor: "#b26700",
          borderColor: "#b26700",
          text: "색상명",
        },
      ],
      product: {
        goodsno: 0,
      },
      mugicontype : {},
    };
  },
  mounted() {
    this.product = this.productInfo;
    /*
      프로모션 상세 페이지에서 할인율이 2배로 계산되어 주석 처리(2022-12-01, James)
      if(this.product_type == "promotion"){
      console.log(this.product);
      this.product = {...this.product, saleamt : this.product.saleamt - this.product.goodscpnamt};
    }*/
  },
  methods: {
    /**********************
     * 좋아요 버튼 처리
     ********************/
    changeWish() {
      this.$util.debug("조하요!!!");
      this.$front.goodsChangeWish(this, this.product);
    },
    goLink() {
      if (
        this.product.goodsselltype == "GST003" ||
        this.product.goodsselltype == "GST006"
      ) {
        this.$eventBus.$emit("alert", "알림", "판매종료 상품입니다.");
      } else {
        this.$router.push({name:'shop-detail', params:{pid:this.productInfo.goodscode}});
      }
    },
  },
  watch: {
    productInfo() {
      this.product = this.productInfo;
    },
  },
};
</script>

<style scoped>
  @media (min-width: 1200px){
    .dp-product .product__ctg {font-size: 18px; font-weight: 600;}
    .dp-product .product-thumbnail {margin-bottom: 27px;}
    .dp-product .product__title {font-size: 18px; color: #666;}
    .dp-product .product__price-number {font-size: 26px;}
    .dp-product .product__price-unit {font-size: 18px;}
    .dp-product .product__discount-percentage {font-size: 26px;}
    .dp-product .product__discount-number {font-size: 16px;}
    .dp-product .product__discount-unit {font-size: 12px;}
    .dp-product .product__like {position: absolute; top: 0; right: 0;}
  }
</style>