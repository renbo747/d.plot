<template>
  <div class="order-coupon" :class="{ disabled: couponInfo.isdownload == 'T' && isDownload}">
    <div class="order-coupon-area d-flex flex-row">
      <div class="coupon__text-area">
        <div class="discount-title" v-if="couponInfo.ispercent == 'T'">{{ couponInfo.dispercent }}% 할인</div>
        <div class="discount-title" v-else-if="couponInfo.comcpntype == 'CCT003'">배송비 {{ $util.maskComma(couponInfo.maxdisamt) }}원</div>
        <div class="discount-title" v-else>{{$util.maskComma(couponInfo.disprice)}}원 할인</div>
        <div class="coupon-title">{{ couponInfo.cpnname }}</div>
        <div class="coupon-use-condition">
            <font v-if="couponInfo.isorderlimit == 'F' && couponInfo.ispercent == 'T' && couponInfo.maxdisamt > 0">최대 {{$util.maskComma(couponInfo.maxdisamt)}}원 할인</font>
            <font v-if="couponInfo.isorderlimit == 'F' && couponInfo.ispercent == 'T' && couponInfo.maxdisamt <= 0">구매 제한 없음</font>
            <font v-else-if="couponInfo.isorderlimit == 'T' && couponInfo.ispercent == 'T' && couponInfo.orlimitamt > 0 && couponInfo.maxdisamt > 0">{{$util.maskComma(couponInfo.orlimitamt)}}원 이상 구매 시 최대 {{$util.maskComma(couponInfo.maxdisamt)}}원 할인</font>
            <font v-else-if="couponInfo.isorderlimit == 'T' && couponInfo.ispercent == 'T' && couponInfo.orlimitamt > 0">{{$util.maskComma(couponInfo.orlimitamt)}}원 이상 구매 시 사용가능</font>
            <font v-else-if="couponInfo.isorderlimit == 'T' && couponInfo.ispercent == 'T' && couponInfo.orlimitamt <= 0">최대 {{$util.maskComma(couponInfo.maxdisamt)}}원 할인</font>
            <font v-else-if="couponInfo.isorderlimit == 'T' && couponInfo.ispercent == 'F' && couponInfo.orlimitamt > 0">{{$util.maskComma(couponInfo.orlimitamt)}}원 이상 구매 시 사용가능</font>
            <font v-else-if="couponInfo.isorderlimit == 'F' && couponInfo.ispercent == 'F' && couponInfo.disprice > 0">최대 {{$util.maskComma(couponInfo.disprice)}}원 할인</font>
            <font v-else-if="couponInfo.comcpntype == 'CCT003' && couponInfo.orlimitamt <= 0">배송비 최대 {{$util.maskComma(couponInfo.maxdisamt)}}원 할인</font>
            <font v-else-if="couponInfo.comcpntype == 'CCT003' && couponInfo.orlimitamt > 0">{{$util.maskComma(couponInfo.orlimitamt)}}원 이상 구매시 배송비 최대 {{$util.maskComma(couponInfo.maxdisamt)}}원 할인<br/></font>
            <font v-else></font>
            <!-- <font v-if="couponInfo.istotcate == 'F' || couponInfo.goodsrangetype != 'GGT001'">일부상품 및 특가 상품 제외</font>
            <font v-else></font> -->
        </div>
        <div class="coupon-special">
          <span v-if="couponInfo.istotcate == 'F' || couponInfo.goodsrangetype != 'GGT001'">일부상품 및 특가 상품 제외</span>
        </div>
        <!-- TODO: 7/16 YIY 쿠폰별 일부상품 및 특가 상품 제외 문구 추가필요 --> 
        <!-- <div class="coupon-special">
          <span>{{ couponInfo.useSpecial }}</span>
        </div> -->
        <div
          class="coupon-use-period"
          :class="{ periodColor: couponInfo.isdeadline == 'T' }"
          v-if="!$util.isNull(couponInfo.cpnuseedday)"
        >
          ~ {{$util.getFormatDate2(couponInfo.cpnuseedday)}}까지
        </div>
      </div>
      <div
        class="coupon-download-area d-flex align-items-center justify-content-center"
        @click="download"
      >
        <div class="circle-style d-flex flex-column">
          <span></span><span></span><span></span><span></span><span></span
          ><span></span><span></span><span></span><span></span>
        </div>
        <div
          class="icon-download-area d-flex flex-column align-item-center justify-content-center"
           v-if="isDownload"
        >
          <div
            class="icon-area d-flex align-items-center justify-content-center"
          >
            <i class="dp-icon sm02 icon-download"></i>
          </div>
          <div class="down-text">Down</div>
        </div>
        <div v-else>
          <p class="mb-0 down-text pt-0">사용가능</p>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  props: {
    isDownload: {
      type: Boolean,
      default: true,
    },
    couponInfo: {
      type: Object
    },
  },
  methods : {
    download() {
      this.$emit('down');
    }
  },
  mounted(){
  }
};
</script>

<style scoped></style>
