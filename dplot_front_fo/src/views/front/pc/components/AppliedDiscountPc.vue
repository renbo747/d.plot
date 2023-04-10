<template>
  <div class="applied-discount">
    <div class="coupon-apply-drop-area">
      <div class="coupon-apply-drop">
        <form>
          <base-dropdown
            :options="selectOptions"
            @input="handleSelect"
            placeholder="쿠폰을 선택해주세요"
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
    <div class="applied-discount__area" v-if="isApplied">
      <div
        class="applied-amount d-flex justify-content-between align-items-center"
      >
        <div class="applied-amount__title">적용 수량</div>
        <div class="applied-amount__select-box">
          <base-dropdown :options="selectOptions02" placeholder="수량 선택" />
        </div>
      </div>
      <ul class="list-style-none applied-discount-ul">
        <li class="applied-discount-li">
          <p class="discount-title dp-caption">
            {{
              isGeneralMember
                ? discountTitle.generalMember
                : discountTitle.employeeMember
            }}
          </p>
          <p class="discount-rate atten-new dp-price01">
            {{ discountRate.promotion
            }}<span class="price-unit dp-caption">원</span>
          </p>
        </li>
        <li class="applied-discount-li">
          <p class="discount-title dp-caption">상품할인 쿠폰</p>
          <p class="discount-rate atten-new dp-price01">
            {{ discountRate.product }}
            <span class="price-unit dp-caption">원</span>
          </p>
        </li>
        <li class="applied-discount-li">
          <p class="discount-title dp-caption">추가할인 쿠폰</p>
          <p class="discount-rate atten-new dp-price01">
            {{ discountRate.addDiscount
            }}<span class="price-unit dp-caption">원</span>
          </p>
        </li>
      </ul>
      <hr class="dp-hr h1" />
      <div class="total-discount">
        <p class="dp-p mb-0 total-discount-title">총 할인</p>
        <p class="atten-new dp-title mb-0">
          {{
            isApplied ? totalDiscountRate.applied : totalDiscountRate.noApplied
          }}<span class="price-unit dp-p-sm">원</span>
        </p>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    // 쿠폰 셀렉트옵션
    selectOptions: {
      type: Array,
      default: () => [
        {
          label: "적용할 수 있는 쿠폰이 없습니다.",
          value: "emptyCoupon",
        },
        {
          label: "&Traditin 5% 특별할인 쿠폰",
          value: "&Traditin 5% 특별할인 쿠폰",
        },
        {
          label: "&Traditin 5% 특별할인 쿠폰",
          value: "&Traditin 5% 특별할인 쿠폰",
        },
        {
          label: "적용안함",
          value: "noCoupon",
        },
      ],
    },
    // 적용 수량 셀렉트 옵션
    selectOptions02: {
      type: Array,
      default: () => [
        {
          label: "1",
          value: "1",
        },
        {
          label: "2",
          value: "2",
        },
        {
          label: "3",
          value: "3",
        },
        {
          label: "4",
          value: "4",
        },
        {
          label: "5",
          value: "5",
        },
      ],
    },
    // 일반회원인지 임직원인지
    isGeneralMember: {
      type: Boolean,
      default: true,
    },
    discountTitle: {
      type: Object,
      default: () => ({
        generalMember: "프로모션할인",
        employeeMember: "임직원할인",
      }),
    },
    // 쿠폰을 중복 체크했을 경우
    duplicateUse: {
      type: Boolean,
      default: false,
    },
    // 할인 된 가격
    discountRate: {
      type: Object,
      default: () => ({
        promotion: "-30000",
        product: "-5000",
        addDiscount: "-5000",
      }),
    },
    // 쿠폰 선택 했을 떄
    isApplied: {
      type: Boolean,
      default: false,
    },
    // 총 할인 가격
    totalDiscountRate: {
      type: Object,
      default: () => ({
        applied: "-40000",
        noApplied: "0",
      }),
    },
  },
  methods: {
    handleSelect(value) {
      if (value === "emptyCoupon" || value === "noCoupon") {
        this.isApplied = false;
      } else {
        this.isApplied = true;
      }
    },
  },
};
</script>
