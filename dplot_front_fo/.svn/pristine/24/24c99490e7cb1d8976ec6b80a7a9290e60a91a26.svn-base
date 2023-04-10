<template>
  <div class="product-cart" :class="{ thumbnailHidden: isThumbnailHidden }">
    <div v-if="isHeader" class="product-cart__header dp-mb-20 d-flex">
      <base-checkbox
        v-model="product.chk"
        :label="product.goodsname"
        :status-text="'sold out'"
        :id="product.goodsno"
        :is-disabled="product.goodsselltype == 'GST003' || product.goodsselltype == 'GST006'? true:false"
        @change="checkChange"
      />
      <i
        class="dp-icon icon-close-black"
        @click="handleDelete"
        v-if="isIcon"
      ></i>
    </div>
    <div class="product-cart__body">
      <product-thumbnail
        v-if="isThumbnail"
        :has-stock="product.stockcnt"
        :status-text="product.statusText"
        :thumbnail-info="{
          id: product.goodsno,
          fullpath: product.fullpath,
        }"
        :to="{
          name: 'shop-detail',
          params: { pid: product.goodscode },
        }"
        :is-checkbox="false"
        :height="100"
        :has-end="product.goodsselltype == 'GST003' || product.goodsselltype == 'GST006'? true:false"
        class="mr-3"
      />
      <div class="product-info">
        <div v-if="isStatus" class="product-info__status mb-1">
          <p class="delivery-status mb-0">{{ productStatus }}</p>
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
        <p class="product-text mb-1 atten-new product__ctg">{{ product.brandname }}</p>
        <div v-if="isSummary" class="product-option">
          <ul class="list-style-none">
            <li class="product-text dp-p-sm">{{ product.goodsname }}</li>
            <li v-if="product.opthtml != '' && isPrice" v-html="product.opthtml"></li>
            <li v-if="isQuantity && !isCart">{{ product.detail[0].ordcnt }}개</li>
          </ul>
        </div>
        <div v-if="isSummary2" class="product-option">
          <ul class="list-style-none">
            <li class="dp-p-sm">{{ product.goodsname }}</li>
            <li v-if="product.opthtml != ''" v-html="product.opthtml"></li>
            <!--css에서 li 마지막번째 비노출 처리해놨음ㅅ.... -->
            <li>0개</li>
          </ul>
        </div>
        <!--2022-04-29 yiy 추가 부장님하고 달라서추가 -->
        <div v-if="isPrice" class="product__price">
          <div class="d-flex align-items-center">
            <p class="mb-0">
              <span class="price-number atten-new">{{
                $util.maskComma(
                  product.detail[0].saleamt * product.detail[0].ordcnt
                )
              }}</span>
              <span class="price-unit">원</span>
            </p>
            <div v-if="isBadge" class="badge__group">
              <span
                v-if="product.goodscpnamt > 0"
                class="dp-badge square primary"
                >쿠폰</span
              >
              <span
                v-if="product.delivamt === 0"
                class="dp-badge square secondary"
                >무료배송</span
              >
            </div>
          </div>
          <span
            v-if="product.detail[0].saleamt != product.detail[0].marketprice"
            class="discount-number atten-new"
          >
            {{
              $util.maskComma(
                product.detail[0].marketprice * product.detail[0].ordcnt
              )
            }}
          </span>
        </div>
        <div v-if="isPrice2" class="product__price">
          <div class="d-flex align-items-center">
            <p class="mb-0">
              <span class="price-number atten-new">{{
                $util.maskComma(product.saleamt)
              }}</span>
              <span class="price-unit">원</span>
            </p>
            <div v-if="isBadge" class="badge__group">
              <span
                v-if="product.goodscpnamt > 0"
                class="dp-badge square primary"
                >쿠폰</span
              >
              <span
                v-if="product.delivamt === 0"
                class="dp-badge square secondary"
                >무료배송</span
              >
            </div>
          </div>
          <span class="discount-number atten-new"> </span>
        </div>
        <slot name="custom-contents"></slot>
      </div>
    </div>
    <div class="product-cart__footer dp-mt-20" v-if="isFooter">
      <template v-if="isCart">
        <base-counter
          v-if="isPrice && product.stockcnt > 0"
          :item="product.detail[0]"
          @input="handleCount"
        />
        <ul class="product-cart__ul list-style-none">
          <li v-if="product.stockcnt !== 0">
            <b-button
              class="dp-btn btn-h32"
              variant="outline-gray-400 not-hover"
              squared
              @click="$emit('optionClick', product)"
            >
              <span class="text-gray-800">옵션변경</span>
            </b-button>
          </li>
          <li v-if="product.stockcnt !== 0">
            <b-button
              class="dp-btn text-white btn-h32"
              variant="gray-800"
              squared
              @click="$emit('quickBuy', product)"
            >
              <span>바로구매</span>
            </b-button>
          </li>
          <li v-if="isPrice && product.stockcnt === 0">
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
          </li>
        </ul>
      </template>
      <template v-if="!isCart && isSelectCount">
        <div class="exchange-layout">
          <div class="d-flex">
            <base-select
              class="sm"
              :options="selectOptions02"
              placeholder="수량 선택"
            />
            <b-button
              v-if="isExchange"
              class="dp-btn"
              variant="outline-gray-400"
              squared
            >
              <span>옵션변경</span>
            </b-button>
          </div>
          <div v-if="isExchange" class="exchange-layout-select">
            <base-select
              :options="selectOptions03"
              placeholder="교환옵션 선택"
            />
          </div>
        </div>
      </template>
      <template>
      <ul v-if="isClaim" class="mypage-footer list-style-none">
        <li v-for="(item, index) in buttonData" :key="index">
          <b-button
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
      default: true,
    },
    isClaim: {
      type: Boolean,
      default: false,
    },
    isIcon: {
      type: Boolean,
      default: true,
    },
    isSelectCount: {
      type: Boolean,
      default: false,
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
    isPrice2: {
      type: Boolean,
      default: false,
    },
    productStatus: {
      type: String,
      default: "배송완료",
    },
    // 개런티 카드가 있을 때
    isGuarantee: {
      type: Boolean,
      default: true,
    },
    isTitle: {
      type: Boolean,
      default: true,
    },
    isQuantity: {
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
        chk: false,
        goodsname: "",
        fullpath: require("@/assets/mobile/img/shop/img-shop-product01.jpg"),
        brandname: "Artemide",
        goodaname: "피어스 마이크로 펜던램프",
        marketprice: 2119000,
        saleamt: 2600000,
        coupon: 0,
        delivamt: 1000,
        stockcnt: 0,
        detail: [],
      }),
    },
    isOption: {
      type: Boolean,
      default: false,
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
    isCart: {
      type: Boolean,
      default: false,
    },
    isSummary: {
      type: Boolean,
      default: true,
    },
    isSummary2: {
      type: Boolean,
      default: false,
    },
    buttonData: {
      type: Array,
      default: () => [
        {
          text: "교환신청",
        },
        {
          text: "반품신청",
        },
      ],
    },
    isThumbnailHidden: {
      type: Boolean,
      default: false,
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
    handleStatusBtn(status) {
      this.$emit("click", status);
    },
    checkChange() {
      this.$emit("change");
    },
    handleCount(item) {
      this.product.detail[0] = item;
      this.$emit("change");
    },
    handleDelete() {
      this.$util.debug(this.$route.name);
      if (this.$route.name == "mypage-restock") {
        //전체삭제여부, 상품정보
        this.$emit("delete", false, this.product);
      } else {
        this.$emit("delete", this.product.detail);
      }
    },
  },
};
</script>
