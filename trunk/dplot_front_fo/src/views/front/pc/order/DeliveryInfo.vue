<template>
  <main class="dp-order-delivery pc-top-padding">
    <div class="container">
      <div class="container-inner">
        <header class="dp-cart-header">
          <h1 class="dp-cart-title mb-0 font-weight-700 text-black">
            주문결제
          </h1>
        </header>
      </div>
    </div>
    <layout-right>
      <template #left-contents>
        <section class="dp-order-delivery-content dp-panel pb-0">
          <header
            class="order-payment-header d-flex align-items-center justify-content-between"
          >
            <div class="payment-header-title">
              <h2
                class="payment-header-title-h2 text-black font-weight-500 mb-0"
              >
                배송지
              </h2>
            </div>
            <div class="payment-header__btn">
              <b-button
                class="option-list__btn dp-btn btn-h32 not-hover d-flex align-items-center"
                variant="outline-gray-800 type02"
                squared
                pill
              >
                <span class="dp-caption font-weight-400 text-gray-800"
                  >배송지 변경</span
                >
                <i class="dp-icon sm01 icon-pick"></i>
              </b-button>
            </div>
          </header>

          <div class="delivery-info-body" v-if="isInfo">
            <ul class="delivery-info__list__wrap list-style-none">
              <!-- 받는 사람 -->
              <li class="delivery-info__list">
                <form>
                  <div class="form-group">
                    <base-input-horizon
                      :is-label="true"
                      label="받는 사람"
                      placeholder="이름을 입력해주세요"
                    />
                  </div>
                </form>
              </li>
              <!-- 연락처 -->
              <li class="delivery-info__list">
                <form>
                  <div class="form-group">
                    <base-input-horizon
                      :is-label="true"
                      label="연락처"
                      placeholder="휴대전화 번호 - 없이 입력"
                    />
                  </div>
                </form>
              </li>
              <!-- 주소 -->
              <li class="delivery-info__list delivery-info__address">
                <form>
                  <div class="form-group d-flex align-items-end">
                    <div class="delivery-info__adderss__input">
                      <base-input-horizon
                        :is-readonly="true"
                        :is-label="true"
                        label="주소"
                        placeholder=""
                      />
                    </div>
                    <div class="delivery-info__address__btn">
                      <b-button
                        class="dp-btn text-white"
                        variant="gray-800"
                        squared
                      >
                        <span class="dp-p-sm">우편번호 찾기</span>
                      </b-button>
                    </div>
                  </div>
                  <div class="form-group delivery-info__address__plus">
                    <base-input
                      :is-readonly="true"
                      placeholder=""
                      v-model="deliveryData01"
                      @input="handleDelivery01"
                    />
                  </div>
                  <div class="delivery-info__address__list">
                    <base-input
                      placeholder="상세주소 입력"
                      v-model="deliveryData02"
                      @input="handleDelivery02"
                    />
                  </div>
                </form>
              </li>
            </ul>
            <!-- 배송지 체크박스 -->
            <div class="delivery-info__setting__checkbox">
              <div class="delivery-info__setting__check">
                <base-checkbox
                  label="기본 배송지로 설정"
                  v-model="deliveryCheckbox.id"
                  :id="deliveryCheckbox.id"
                  name="deliveryCheckbox"
                  :is-checked="deliveryCheckbox.checked"
                  @change="deliveryCheckbox.checked = !deliveryCheckbox.checked"
                />
              </div>
            </div>
            <!-- 배송 요청 사항 -->
            <div class="delivery-info__setting__drop">
              <div>
                <base-dropdown
                  placeholder="배송 요청사항 (선택)"
                  @change="getData"
                />
              </div>
            </div>
          </div>
          <div class="order-payment-body" v-else>
            <div class="d-flex align-items-center mb-10">
              <div class="order-payment-orderer">
                <p
                  class="order-payment-orderer__name text-black font-weight-500 mb-0"
                >
                  홍길동
                </p>
              </div>
              <div class="order-payment-badge">
                <span class="dp-badge h26 lh-1">기본 배송지</span>
              </div>
            </div>
            <div class="order-payment-address">
              <p
                class="order-payment-address-text mb-0 text-gray-700 font-weight-400"
              >
                서울시 강남구 학동로 146 와일리 타워 2층
              </p>
              <p
                class="order-payment-address-text payment-address-phone mb-0 text-gray-700 font-weight-400"
              >
                010-1234-5678
              </p>
            </div>
            <!-- 드롭 다운 메뉴 및 인풋 박스 -->
            <div class="order-payment-form">
              <form>
                <div class="order-payment-drop form-group">
                  <base-dropdown
                    class="btn-h48"
                    :options="dropDownOption"
                    @change="handleDrop"
                    placeholder="배송 요청사항 (선택)"
                  />
                </div>
                <div class="order-payment-drop pt-10" v-if="isInput">
                  <base-input
                    class="btn-h48"
                    v-model="inputData"
                    placeholder="배송요청사항을 입력해주세요"
                    @input="handleInput"
                  />
                </div>
              </form>
            </div>
          </div>
        </section>
      </template>
      <template #right-contents>
        <div class="dp-delivery__btn-group" v-if="isInfo">
          <div class="dp-cart__price-summary">
            <header class="dp-cart__price-header">
              <p class="fz-20 font-weight-500 text-black mb-0">결제금액</p>
            </header>
            <hr class="dp-hr text-black" />
            <ul class="dp-cart__price-summary-list list-style-none">
              <li
                class="d-flex justify-content-between mb-10"
                v-for="(list, index) in amountPriceData"
                :key="index"
                @change="handlePrice(list.id)"
              >
                <div class="d-flex align-items-center">
                  <span
                    class="dp-order-price__text font-weight-400 text-gray-700"
                    >{{ list.name }}</span
                  >
                  <div class="delivery-only" v-if="list.id === 'price02'">
                    <b-button
                      class="delivery-only-btn dp-btn not-hover"
                      variant="outline-gray-800"
                      pill
                    >
                      <span class="delivery-only-btn__text">배송비 상세</span>
                      <i class="dp-icon sm icon-arrow-right"></i>
                    </b-button>
                  </div>
                </div>

                <p class="dp-order-price__text font-weight-500 mb-0">
                  <span
                    class="dp-order-price__text-lg atten-new dp-p font-weight-500"
                    >{{ list.price }}</span
                  >
                  {{ list.unit }}
                </p>
              </li>
            </ul>
            <hr class="dp-hr dp-hr-order mb-0" />
            <footer class="dp-cart__price-footer">
              <div class="d-flex justify-content-between">
                <span class="footer-summary__text text-black font-weight-500"
                  >최종결제금액</span
                >
                <p
                  class="footer-summary__price mb-0 font-weight-700 text-black dp-title"
                >
                  <span class="atten-new">0</span> 원
                </p>
              </div>
              <div class="footer-summary__text-primary">
                <p class="mb-0 footer-summary__price-primary text-primary">
                  0원 적립예정
                </p>
              </div>
            </footer>
            <hr class="dp-hr dp-hr-order mb-0" />
            <section class="order-payment-agreement">
              <div class="d-flex align-items-center justify-content-between">
                <div class="order-payment-product__agree">
                  <base-checkbox
                    label="이용약관 모두 동의"
                    v-model="agreeCheckbox.id"
                    :id="agreeCheckbox.id"
                    name="sampleCheckbox"
                    :is-checked="agreeCheckbox.checked"
                    @change="agreeCheckbox.checked = !agreeCheckbox.checked"
                  />
                </div>
                <div class="order-payment-check d-flex align-items-center">
                  <i class="dp-icon icon-arrow-down sm02"></i>
                </div>
              </div>
              <div class="order-payment-agreement__list">
                <div class="order-payment-agreement-header">
                  <p
                    class="order-payment-agreement-header__text mb-0 text-black font-weight-500"
                  >
                    주문상품 정보(상품, 가격, 배송정보) 동의
                  </p>
                </div>
                <ul class="order-payment-agreement__list__ul list-style-none">
                  <li
                    class="d-flex align-items-center justify-content-between mb-10"
                  >
                    <base-checkbox
                      label="개인정보 수집 및 이용 동의"
                      v-model="orderPaymentAgree[0].id"
                      :id="orderPaymentAgree[0].id"
                      name="sampleCheckbox"
                      :is-checked="orderPaymentAgree[0].checked"
                      @change="
                        orderPaymentAgree[0].checked =
                          !orderPaymentAgree[0].checked
                      "
                    />
                    <i @click="handleTermsModal">
                      <img
                        class="more-icon"
                        src="@/assets/common/icon/icon-more-black-16px.svg"
                        alt="더보기 아이콘"
                      />
                    </i>
                  </li>
                  <li
                    class="d-flex align-items-center justify-content-between mb-10"
                  >
                    <base-checkbox
                      label="개인정보 제공 및 위탁 동의"
                      v-model="orderPaymentAgree[1].id"
                      :id="orderPaymentAgree[1].id"
                      name="sampleCheckbox"
                      :is-checked="orderPaymentAgree[1].checked"
                      @change="
                        orderPaymentAgree[1].checked =
                          !orderPaymentAgree[1].checked
                      "
                    />
                    <i @click="handleTermsModal">
                      <img
                        class="more-icon"
                        src="@/assets/common/icon/icon-more-black-16px.svg"
                        alt="더보기 아이콘"
                      />
                    </i>
                  </li>
                  <li class="d-flex align-items-center justify-content-between">
                    <base-checkbox
                      label="결제대행 서비스 이용약관 동의"
                      v-model="orderPaymentAgree[2].id"
                      :id="orderPaymentAgree[2].id"
                      name="sampleCheckbox"
                      :is-checked="orderPaymentAgree[2].checked"
                      @change="
                        orderPaymentAgree[2].checked =
                          !orderPaymentAgree[2].checked
                      "
                    />
                    <i @click="handleTermsModal">
                      <img
                        class="more-icon"
                        src="@/assets/common/icon/icon-more-black-16px.svg"
                        alt="더보기 아이콘"
                      />
                    </i>
                  </li>
                </ul>
              </div>
            </section>
          </div>
          <div class="btn-group">
            <b-button
              class="dp-btn btn-h48"
              variant="gray-800"
              :disabled="true"
              squared
            >
              <span class="btn-group-text font-weight-400">구매하기</span>
            </b-button>
          </div>
        </div>
        <div class="dp-delivery__btn-group" v-else>
          <div class="dp-cart__price-summary">
            <header class="dp-cart__price-header">
              <p class="fz-20 font-weight-500 text-black mb-0">결제금액</p>
            </header>
            <hr class="dp-hr text-black" />
            <ul class="dp-cart__price-summary-list list-style-none">
              <li
                class="d-flex justify-content-between mb-10"
                v-for="(list, index) in amountPriceData01"
                :key="index"
                @change="handlePrice(list.id)"
              >
                <div class="d-flex align-items-center">
                  <span
                    class="dp-order-price__text font-weight-400 text-gray-700"
                    >{{ list.name }}</span
                  >
                  <div class="delivery-only" v-if="list.id === 'price02'">
                    <b-button
                      class="delivery-only-btn dp-btn not-hover"
                      variant="outline-gray-800"
                      pill
                    >
                      <span class="delivery-only-btn__text">배송비 상세</span>
                      <i class="dp-icon sm icon-arrow-right"></i>
                    </b-button>
                  </div>
                </div>

                <p class="dp-order-price__text font-weight-500 mb-0">
                  <span
                    class="dp-order-price__text-lg atten-new dp-p font-weight-500"
                    >{{ list.price }}</span
                  >
                  {{ list.unit }}
                </p>
              </li>
            </ul>
            <hr class="dp-hr dp-hr-order mb-0" />
            <footer class="dp-cart__price-footer">
              <div class="d-flex justify-content-between">
                <span class="footer-summary__text text-black font-weight-500"
                  >최종결제금액</span
                >
                <p
                  class="footer-summary__price mb-0 font-weight-700 text-black dp-title"
                >
                  <span class="atten-new">1,491,400</span> 원
                </p>
              </div>
              <div class="footer-summary__text-primary">
                <p class="mb-0 footer-summary__price-primary text-primary">
                  3,000원 적립예정
                </p>
              </div>
            </footer>
            <hr class="dp-hr dp-hr-order mb-0" />
            <section class="order-payment-agreement">
              <div class="d-flex align-items-center justify-content-between">
                <div class="order-payment-product__agree">
                  <base-checkbox
                    label="이용약관 모두 동의"
                    v-model="agreeCheckbox.id"
                    :id="agreeCheckbox.id"
                    name="sampleCheckbox"
                    :is-checked="agreeCheckbox.checked"
                    @change="agreeCheckbox.checked = !agreeCheckbox.checked"
                  />
                </div>
                <div class="order-payment-check d-flex align-items-center">
                  <i class="dp-icon icon-arrow-down sm02"></i>
                </div>
              </div>
              <div class="order-payment-agreement__list">
                <div class="order-payment-agreement-header">
                  <p
                    class="order-payment-agreement-header__text mb-0 text-black font-weight-500"
                  >
                    주문상품 정보(상품, 가격, 배송정보) 동의
                  </p>
                </div>
                <ul class="order-payment-agreement__list__ul list-style-none">
                  <li
                    class="d-flex align-items-center justify-content-between mb-10"
                  >
                    <base-checkbox
                      label="개인정보 수집 및 이용 동의"
                      v-model="orderPaymentAgree[0].id"
                      :id="orderPaymentAgree[0].id"
                      name="sampleCheckbox"
                      :is-checked="orderPaymentAgree[0].checked"
                      @change="
                        orderPaymentAgree[0].checked =
                          !orderPaymentAgree[0].checked
                      "
                    />
                    <i @click="handleTermsModal">
                      <img
                        class="more-icon"
                        src="@/assets/common/icon/icon-more-black-16px.svg"
                        alt="더보기 아이콘"
                      />
                    </i>
                  </li>
                  <li
                    class="d-flex align-items-center justify-content-between mb-10"
                  >
                    <base-checkbox
                      label="개인정보 제공 및 위탁 동의"
                      v-model="orderPaymentAgree[1].id"
                      :id="orderPaymentAgree[1].id"
                      name="sampleCheckbox"
                      :is-checked="orderPaymentAgree[1].checked"
                      @change="
                        orderPaymentAgree[1].checked =
                          !orderPaymentAgree[1].checked
                      "
                    />
                    <i @click="handleTermsModal">
                      <img
                        class="more-icon"
                        src="@/assets/common/icon/icon-more-black-16px.svg"
                        alt="더보기 아이콘"
                      />
                    </i>
                  </li>
                  <li class="d-flex align-items-center justify-content-between">
                    <base-checkbox
                      label="결제대행 서비스 이용약관 동의"
                      v-model="orderPaymentAgree[2].id"
                      :id="orderPaymentAgree[2].id"
                      name="sampleCheckbox"
                      :is-checked="orderPaymentAgree[2].checked"
                      @change="
                        orderPaymentAgree[2].checked =
                          !orderPaymentAgree[2].checked
                      "
                    />
                    <i @click="handleTermsModal">
                      <img
                        class="more-icon"
                        src="@/assets/common/icon/icon-more-black-16px.svg"
                        alt="더보기 아이콘"
                      />
                    </i>
                  </li>
                </ul>
              </div>
            </section>
          </div>
          <div class="btn-group">
            <b-button class="dp-btn btn-h48" variant="gray-800" squared>
              <span class="btn-group-text font-weight-400"
                >63,200원 결제하기</span
              >
            </b-button>
          </div>
        </div>
      </template>
    </layout-right>
  </main>
</template>
<script>
export default {
  data() {
    return {
      //배송지 정보 없음 = true
      isInfo: true,
      //상세주소 데이터
      deliveryData01: "",
      deliveryData02: "",
      //체크박스 데이터
      deliveryCheckbox: {
        id: "deliveryChk01",
        checked: false,
      },
      // 배송지정보 없음 결제금액 데이터
      amountPriceData: [
        {
          id: "price01",
          name: "총 상품금액",
          price: "0",
          unit: "원",
        },
        {
          id: "price02",
          name: "배송비",
          price: "0",
          unit: "원",
        },
        {
          id: "price03",
          name: "할인/쿠폰 적용",
          price: "0",
          unit: "원",
        },
        {
          id: "price04",
          name: "D포인트 사용",
          price: "0",
          unit: "P",
        },
        {
          id: "price05",
          name: "적립금 사용",
          price: "0",
          unit: "원",
        },
        {
          id: "price06",
          name: "임직원적립금 사용",
          price: "0",
          unit: "원",
        },
      ],
      // 배송지정보 있음 결제금액 데이터
      amountPriceData01: [
        {
          id: "price01",
          name: "총 상품금액",
          price: "460,200",
          unit: "원",
        },
        {
          id: "price02",
          name: "배송비",
          price: "3000",
          unit: "원",
        },
        {
          id: "price03",
          name: "할인/쿠폰 적용",
          price: "-100,000",
          unit: "원",
        },
        {
          id: "price04",
          name: "D포인트 사용",
          price: "-100,000",
          unit: "P",
        },
        {
          id: "price05",
          name: "적립금 사용",
          price: "-100,000",
          unit: "원",
        },
        {
          id: "price06",
          name: "임직원적립금 사용",
          price: "-100,000",
          unit: "원",
        },
      ],
      //이용약관 동의
      agreeCheckbox: {
        id: "agreechk01",
        checked: false,
      },
      orderPaymentAgree: [
        {
          id: "agree01",
          checked: false,
        },
        {
          id: "agree02",
          checked: false,
        },
        {
          id: "agree03",
          checked: false,
        },
      ],
    };
  },
  methods: {
    dataCheck(val) {
      // 자식으로부터 넘어온 데이터 확인용
    },
    //상세주소
    handleDelivery01(val) {
      this.deliveryData01 = val;
    },
    handleDelivery02(val) {
      this.deliveryData02 = val;
    },
    //드롭다운
    getData(val) {
    },
    handleOrder() {
      this.$root.$emit("bv::show::modal", "OrderSuccessModal");
    },
    handlePrice(index) {
      if (index === "price02") {
        this.isDelivery = true;
      }
    },
    handleTermsModal() {
      this.$root.$emit("bv::show::modal", "termsModal");
    },
  },
};
</script>
