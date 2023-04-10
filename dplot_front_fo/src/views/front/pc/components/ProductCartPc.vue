<template>
  <div class="dp-product-cart">
    <div class="product-cart__area d-flex align-items-center">
      <div class="product-info-area d-flex align-items-center">
        <!--체크박스 -->
        <div class="product-cart__checkbox-area">
          <base-checkbox
            v-model="product.chk" 
            :label="null" 
            :id="product.goodsno"
            @change="checkChange"
            :is-disabled="product.goodsselltype == 'GST003' || product.goodsselltype == 'GST006'? true:false"
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
            :to="{
              name: 'shop-detail',
              params: { pid: product.goodscode },
            }"
            :is-checkbox="false"
            :height="150"
            style="width: 150px; cursor: pointer;"
            :has-end="product.goodsselltype == 'GST003' || product.goodsselltype == 'GST006'? true:false"
          />
        </div>
        <!-- 상품 정보 -->
        <div class="product-info">
          <div class="product-info__header d-flex align-items-star">
            <p class="product__ctg mb-0 atten-new" v-if="!$util.isBlank(product.brandname)">
              {{ product.brandname }}
            </p>
            <div v-if="isBadge" class="badge__group d-flex">
              <span v-if="product.goodscpnamt > 0" class="dp-badge square primary coupon-badge">쿠폰</span>
              <span v-if="product.delivamt === 0" class="dp-badge square secondary coupon-badge">무료배송</span>
            </div>
          </div>
          <div class="product-info__body">
            <p class="product__title mb-0">{{ product.goodsname }}</p>
            <div class="product__text d-flex mb-0" v-if="product.opthtml != '' && isPrice" v-html="product.opthtml">
            </div>
            <div class="product__alarm" v-if="isAlarm">
              <p class="product__alarm__text text-gray-700 mb-0" v-if="!$util.isNull(product.notidate)">
                입고알림 <span class="text-secondary">{{product.notidate}}</span>
              </p>
            </div>
          </div>
        </div>
      </div>
      <!-- 상품 금액 -->
      <div class="product__price-area">
        <div class="product__price__flex" v-if="isPrice">
          <div class="price-number-area d-flex align-items-center">
            <span class="price-number atten-new">{{$util.maskComma(product.detail[0].saleamt * product.detail[0].ordcnt)}}</span><br/>
            <span class="price-unit">원</span>
          </div>
          <div class="discount-number-area d-flex justify-content-end" v-if="product.detail[0].saleamt != product.detail[0].marketprice">
            <span class="discount-number">
              {{$util.maskComma(product.detail[0].marketprice * product.detail[0].ordcnt)}}
            </span>
            <span class="price-unit-gray">원</span>
          </div>
        </div>
        <div class="product__price__flex" v-if="isPrice2">
          <div class="price-number-area d-flex align-items-center">
            <span class="price-number atten-new">{{$util.maskComma(product.saleamt)}}</span>
            <span class="price-unit">원</span>
          </div>
          <div class="discount-number-area d-flex justify-content-end">
            <span class="discount-number">
            </span>
          </div>
        </div>
      </div>
      <div class="price-amount-area d-flex" v-if="isModal">
        <!-- 상품 수량 -->
        <div class="product__counter-area">
          <div v-if="isPrice && product.stockcnt > 0">
            <base-counter 
              :item="product.detail[0]"
              @input="handleCount"/>
          </div>
        </div>

        <!-- 구매 버튼-->
        <div class="cart-btn-area">
          <div v-if="isCart">
            <div v-if="product.stockcnt > 0">
              <b-button
                class="dp-btn text-white btn-h32 purchase-btn product-cart-btn"
                variant="gray-800"
                squared
                @click="$emit('quickBuy', product)"
              >
                <span class="cart-btn-text">바로구매</span>
              </b-button>
              <!-- 옵션 변경-->
              <b-button
                class="dp-btn btn-h32 option-btn product-cart-btn"
                variant="outline-gray-400"
                squared
                @click="$emit('optionClick', product)"
              >
                <span class="cart-btn-text text-gray-800">옵션변경</span>
              </b-button>
            </div>
            <div v-if="isPrice && product.stockcnt == 0">
              <b-button
                class="dp-btn btn-h32 not-hover alarm-btn product-cart-btn"
                variant="outline-gray-800 type02"
                squared
                pill
                @click="openRestockModal(product)"
              >
                <span class="dp-caption font-weight-400 text-gray-800"
                  >재입고알림</span
                >
                <i class="dp-icon sm01 icon-alarm"></i>
              </b-button>
            </div>
          </div>
        </div>
        <!-- 닫기 버튼 -->
        <div class="icon-close-area">
          <i class="dp-icon icon-close-black sm02" style="cursor: pointer;" @click="handleDelete"></i>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import RestockAlarm from "@views.pc/cart/popup/RestockAlarm.vue"
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
        goodsname: "",
        fullpath: require("@/assets/mobile/img/shop/img-shop-product01.jpg"),
        brandname: "Artemide",
        goodaname: "피어스 마이크로 펜던램프",
        marketprice: 2119000,
        saleamt: 2600000,
        coupon: 0,
        delivamt: 1000,
        stockcnt: 0
      }),
    },
    // 쿠폰 뱃지
    // 쿠폰뱃지
    isBadge: {
      type: Boolean,
      default: true,
    },
    isCoupon: {
      type: Boolean,
      default: true,
    },
    isPrice: {
      type: Boolean,
      default: true,
    },
    isPrice2: {
      type: Boolean,
      default: false,
    },
    isCount: {
      type: Boolean,
      default: true,
    },
    isDouble: {
      type: Boolean,
      default: true,
    },
    isBtn: {
      type: Boolean,
      default: false,
    },
    isOption: {
      type: Boolean,
      default: false,
    },
    isAlarm: {
      type: Boolean,
      default: false,
    },
    isModal: {
      type: Boolean,
      default: true,
    },
  },
  data() {
    return {
      product : {
        goodsname : '',
        goodsfullpath: '',
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
    handleCount(item) {
      this.product.detail[0] = item;
      this.$emit("change");
    },
    handleDelete() {
      this.$emit("delete", this.product.detail);
    },
    //재입고 모달 열기
    openRestockModal(item){
      if(this.$store.state.isLogin) {
        this.$eventBus.$emit('showModal', RestockAlarm, 'restockAlarmModal', item);
      } else {
        this.$eventBus.$emit('confirm', '로그인', '로그인 하시겠습니까?',()=>{
          this.$storage.setSessionStorage('redirectPath', {name : this.$route.name, query : this.$route.query});
          this.$router.push({name : 'member-login'});
        });
      }
    }
  },
};
</script>

<style scoped></style>
