<template>
  <div class="product-cart" :class="{ thumbnailHidden: isThumbnailHidden }">
    <div v-if="isHeader" class="product-cart__header dp-mb-20 d-flex">
      <base-checkbox
        v-model="product.chk"
        :label="product.goodsname"
        :status-text="'sold out'"
        :id="product.goodsno"
        @change="checkChange"
      />
    </div>
    <div class="product-cart__body">
      <product-thumbnail
        v-if="isThumbnail"
        :has-stock="product.stockcnt"
        :thumbnail-info="{
          id: product.goodsno,
          fullpath: product.fullpath,
        }"
        :to="{
          name: 'shop-detail',
          params: { pid: product.goodscode },
        }"
        :is-checkbox="false"
        class="mr-3"
      />
      <div class="product-info">
        <div v-if="isStatus" class="product-info__status mb-1">
          <p class="delivery-status mb-0" v-if="product.ordcnt > 0">{{ product.statusnm }}</p>
          <div
            v-if="isGuarantee"
            class="guarantee-container"
            @click="$emit('cardClick')"
          >
            <i class="dp-icon icon-guarantee sm01"></i>
            <span>개런티 카드</span>
            <i class="dp-icon icon-arrow-right sm"></i>
          </div>
        </div>
        <p class="mb-1 atten-new product__ctg">{{ product.brandname }}</p>
        <div v-if="isSummary" class="product-option">
          <ul class="list-style-none">
            <li>{{ product.goodsname }}</li>
            <li v-if="product.opthtml != ''" v-html="product.opthtml">
            </li>
            <li v-if="product.ascounter">{{ product.delivcnt }}개</li>
            <li v-if="product.ordcnt > 0 && !product.ascounter">{{ product.ordcnt }}개</li>
          </ul>
        </div>
        <div v-if="isSummary2" class="product-option">
          <ul class="list-style-none">
            <li v-if="product.opthtml != ''" v-html="product.opthtml">
            </li>
            <li>{{ product.ordcnt }}개</li>
          </ul>
        </div>
        <div v-if="isPrice" class="product__price">
          <div class="d-flex align-items-center">
            <p class="mb-0">
              <span class="price-number atten-new" v-if="isOrder">{{$util.maskComma(product.price*product.ordcnt)}}</span>
              <span class="price-number atten-new" v-else>{{$util.maskComma(product.realgoodsamt)}}</span>
              <span class="price-unit">원</span>
            </p>
            <div v-if="isBadge" class="badge__group ml-2">
              <span v-if="product.goodscpnamt > 0" class="dp-badge square primary">쿠폰</span>
              <span v-if="product.delivamt === 0" class="dp-badge square secondary">무료배송</span>
            </div>
          </div>
          <span v-if="isOrder && product.realmarketprice && (product.price*product.ordcnt) != product.realmarketprice" class="discount-number atten-new">
            {{$util.maskComma(product.realmarketprice)}}
          </span>
          <span v-if="!isOrder && product.realmarketprice && product.realgoodsamt != product.realmarketprice" class="discount-number atten-new">
            {{$util.maskComma(product.realmarketprice)}}
          </span>
        </div>
      </div>
    </div>
    <div class="product-cart__footer dp-mt-20" v-if="isFooter">
      <template v-if="isSelectCount">
        <div class="exchange-layout">
          <div class="d-flex">
            <base-select
              v-if="product.asview"
              class="sm"
              :options="product.selectOption"
              :value="product.select"
              placeholder="수량 선택"
              @input="changeOption"
            />
            <base-select 
              v-else
              class="sm"
              v-model="product.clmcnt"
              :options="product.selectOption"
              placeholder="수량 선택"
              :is-disabled="product.origincnt == 1 || product.ordstatus == 'ODS001'"
              @change="checkChange"
            />
            <!-- <b-button
              v-if="isExchange"
              class="dp-btn"
              variant="outline-gray-400"
              squared
              @click="showOpen"
            >
              <span>옵션변경</span>
            </b-button> -->
          </div>
          <div class="exchange-layout-select" v-if="isExchange">
            <base-select
              v-model="product.excoption"
              :options="product.excgoodslist"
              placeholder="교환옵션 선택"
            />
          </div>
        </div>
      </template>
      <template v-else>
        <base-counter v-if="product.basecounter" v-model="product.countervalue" @input="handleCount"/>
        <base-counter v-if="product.ascounter" v-model="product.countervalue" :item="product" @input="handleCount" :key="Date.now()"/>
        <ul class="mypage-footer list-style-none">
          <li v-for="(item, index) in product.buttonData" :key="index">
            <b-button
              v-if="!(product.isreview === 'T' && item.text === '리뷰작성') && product.ordcnt > 0"
              class="dp-btn btn-h32"
              variant="outline-gray-400 not-hover"
              squared
              @click="handleStatusBtn(item.text)"
            >
              <span class="text-gray-800">{{ item.text }}</span>
            </b-button>
          </li>
        </ul>
      </template>
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
    isCheck: {
      type: Boolean,
      default: false,
    },
    // 상품의 상태를 나타낼 때
    isStatus: {
      type: Boolean,
      default: true,
    },
    isOrder: {
      type: Boolean,
      default: false,
    },
    isPrice: {
      type: Boolean,
      default: true,
    },
    // 개런티 카드가 있을 때
    isGuarantee: {
      type: Boolean,
      default: false,
    },
    isExchange: {
      type: Boolean,
      default: false,
    },
    isThumbnail: {
      type: Boolean,
      default: true,
    },
    // 쎔네일이 사라졌을 경우 레이아웃 부분 변경을 위해
    isThumbnailHidden: {
      type: Boolean,
      default: false,
    },
    // 공통요소인 카테고리와 가격
    productInfo: {
      type: Object,
      default: () => ({
        chk:false,
        goodsname: "",
        fullpath: require("@/assets/mobile/img/shop/img-shop-product01.jpg"),
        brandname: "Artemide",
        goodaname: "피어스 마이크로 펜던램프",
        realmarketprice: 2119000,
        realgoodsamt: 2600000,
        coupon: 0,
        delivamt: 1000,
        stockcnt: 0,
        detail : []
      }),
    },
    // 쿠폰뱃지
    isBadge: {
      type: Boolean,
      default: true,
    },
    // 페이지 푸터 부분 결정
    isFooter: {
      type: Boolean,
      default: true,
    },
    isSummary: {
      type: Boolean,
      default: true,
    },
    isSummary2: {
      type: Boolean,
      default: false,
    },
    isSelectCount: {
      type: Boolean,
      default: false,
    },

  },
  data() {
    return {
      product : {
        goodsname : '',
        detail :[{
          saleamt : 0,
          marketprice : 0,
          ordcnt : 0
        }]
      },
      isExcShow : false
    };
  },
  mounted() {
    this.product = this.productInfo;
  },
  methods: {
    handleStatusBtn(status) {
      this.$emit("click", status);
    },
    checkChange(){
      this.$emit("change");
    },
    handleCount(val) {
      this.$util.debug(val);
      val.origincnt = this.product.delivcnt;
      this.$util.debug("handleCount...........");
      this.$util.debug(this.product.ordcnt);
    },
    handleDelete() {
      this.$emit("delete", this.product.detail);
    },
    showOpen() {
      if(this.product.excgoodslist.length > 0) {
        this.isExcShow = true;
      } else {
        this.$eventBus.$emit('alert', '알림', '교환가능한 옵션이 없습니다.');
      }
    },
    changeOption(val){
        this.$util.debug("수량변경");
        this.$util.debug(val);
        this.product.select = val;
    },    
  },
};
</script>

<style>
  .isThumbnailHidden {background:transparent;}
</style>