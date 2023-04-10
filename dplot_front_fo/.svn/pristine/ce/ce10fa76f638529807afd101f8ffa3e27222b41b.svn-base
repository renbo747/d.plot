<template>
  <div class="applied-discount">
    <div class="coupon-apply-drop-area">
      <div class="coupon-apply-drop">
        <p class="dp-p font-weight-500 add-discount-title">상품 할인쿠폰</p>
        <form>
          <base-select
            v-model="cpninfoidx"
            :options="product.couponList"
            placeholder="쿠폰을 선택해주세요"
            @change="handleSelect"
          />
        </form>
      </div>
      <b-button
        class="dp-btn text-white used-coupon-btn"
        variant="gray-800"
        squared
        pill
        v-if="duplicateUse"
      >
        <span>이미 사용하신 쿠폰이에요</span>
      </b-button>
    </div>
    <!-- 적용 된 할인 정보 -->
    <div class="applied-discount__area" v-show="isApplied || product.salepromoamt > 0">
      <div class="applied-amount d-flex justify-content-between align-items-center" v-if="product.coupon.isdirectcoupon == 'F' && isApplied">
        <div class="applied-amount__title">적용 수량</div>
        <div class="applied-amount__select-box">
          <base-select 
            v-model="product.coupon.usecnt"
            :options="cntList"
            placeholder="수량 선택" 
            @change="handleCnt"
            />
        </div>
      </div>
      <div class="applied-amount d-flex justify-content-between align-items-center" v-else/>
      <ul class="list-style-none applied-discount-ul">
        <li class="applied-discount-li" v-if="product.salepromoamt > 0">
          <p class="discount-title dp-caption">{{isEmploy ? '임직원할인' : '프로모션할인'}}</p>
          <p class="discount-rate atten-new dp-price01">
            {{ $util.maskComma(product.salepromoamt * -1)}}<span class="price-unit dp-caption">원</span>
          </p>
        </li>
        <li class="applied-discount-li" v-if="isApplied">
          <p class="discount-title dp-caption">상품할인 쿠폰</p>
          <p class="discount-rate atten-new dp-price01">
            {{ $util.maskComma(cpnamt) }}<span class="price-unit dp-caption">원</span>
          </p>
        </li>
        <!-- <li class="applied-discount-li">
          <p class="discount-title dp-caption">추가할인 쿠폰</p>
          <p class="discount-rate atten-new dp-price01">
            {{ discountRate.addDiscount}}<span class="price-unit dp-caption">원</span>
          </p>
        </li> -->
      </ul>
      <hr class="dp-hr h1" />
      <div class="total-discount">
        <p class="dp-p mb-0 total-discount-title">총 할인</p>
        <p class="atten-new dp-title mb-0">
          {{ $util.maskComma((product.salepromoamt) * -1 + cpnamt)}}<span class="price-unit dp-p-sm">원</span>
        </p>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    item : {
      type:Object
    },
    couponList : {
      type:Array
    },
    // 일반회원인지 임직원인지
    isEmploy: {
      type: Boolean,
      default: false,
    },
    // 쿠폰을 중복 체크했을 경우
    duplicateUse: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      cntList : [],
      cpninfoidx : null,
      product : {
        coupon: {
          cpninfoidx : 0,
          usecnt : 0,
        }
      },
      cpnamt : 0,
      isApplied : false,
    }
  },
  mounted() {
    this.product = this.item;
    this.cntList = this.product.coupon.cntList;
    this.cpninfoidx = this.product.coupon.cpninfoidx;
    if (!this.$util.isBlank(this.cpninfoidx) && this.cpninfoidx !== "emptyCoupon" && this.cpninfoidx !== "noCoupon") {
      this.isApplied = true;
      this.handleCnt();
    }
  },
  methods: {
    handleSelect() {
      if (this.$util.isBlank(this.cpninfoidx) || this.cpninfoidx === "emptyCoupon" || this.cpninfoidx === "noCoupon") {
        this.isApplied = false;
        this.product.coupon = {
          cpninfoidx : this.cpninfoidx,
          usecnt : 0,
        }
        this.cpnamt = 0;
        this.isDirect = '';
      } else {
        this.isApplied = true;
        for(let i = 0 ; i < this.product.couponList.length ; i++) {
          //선택된 쿠폰
          if(this.product.couponList[i].cpninfoidx == this.cpninfoidx) {
            this.product.coupon = this.$util.deepClone(this.product.couponList[i]);
            this.cntList = this.product.coupon.cntList;
            
            //쿠폰 적용수량
            if(this.product.coupon.isdirectcoupon == 'T'){
              if(this.product.ordcnt > this.product.couponList[i].remaincnt) {
                  this.product.coupon.usecnt = this.product.couponList[i].remaincnt;
              } else {
                  this.product.coupon.usecnt = this.product.ordcnt;
              }
              this.isDirect = 'T';
            } else {
              this.product.coupon.usecnt = 1;
              this.isDirect = 'F';
            }
            //this.$front.setCouponUsed(this.product.coupon, this.couponList);
            break;
          }
        }
      }

      let couponAmt = 0;
      if(!this.$util.isNull(this.product.coupon.discountamt)){
        couponAmt = this.product.coupon.discountamt;
      }

      this.cpnamt = couponAmt * this.product.coupon.usecnt * -1;
      //this.cpnamt = this.product.coupon.discountamt * this.product.coupon.usecnt * -1;
      this.$emit('change', this.product);
    },
    handleCnt() {
      //this.$front.setCouponUsed(this.product.coupon, this.couponList);
      this.cpnamt = this.product.coupon.discountamt * this.product.coupon.usecnt * -1;
      this.$emit('change', this.product);
    }
  },
};
</script>
