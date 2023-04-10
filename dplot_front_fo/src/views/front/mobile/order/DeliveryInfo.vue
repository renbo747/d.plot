<template>
  <main class="delivery-info">
    <div class="container">
      <section class="dp-panel pb-0">
        <header class="delivery-info-header">
          <h1 class="dp-p text-black font-weight-500 mb-0">배송지</h1>
        </header>

        <div class="delivery-info-body">
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
                <div class="form-group d-flex">
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
          <div>
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
            <div class="dp-panel pb-0">
              <base-select
                placeholder="배송 요청사항 (선택)"
                @change="getData"
              />
            </div>
          </div>
        </div>
      </section>

      <b-button
        class="dp-btn text-white dp-mt-30"
        variant="gray-800"
        squared
        @click="handleOrder"
      >
        <span>주문완료 (삭제예정)</span>
      </b-button>
    </div>

    <!-- Signup Success Modal -->
    <b-modal
      id="OrderSuccessModal"
      modal-class="dp-modal page-layer order-success"
      scrollable
      :hide-footer="true"
    >
      <!-- Signup Success Modal Header -->
      <template #modal-header="{ cancel }">
        <h5 class="modal-title">주문완료</h5>
        <i class="modal-close" @click="cancel()">
          <img
            src="@assets.mobile/img/icon/icon-close-black-20px.svg"
            alt="닫기"
          />
        </i>
      </template>

      <!-- Signup Success Modal Body -->
      <div class="page-layer__body">
        <div
          v-if="isOrderSuccess"
          class="order-success-message-body d-flex flex-column align-items-center justify-content-center"
        >
          <p class="order-success-message font-weight-500 text-black mb-0">
            주문이 완료되었어요!
          </p>
          <p class="dp-p text-black font-weight-500 mb-0">
            주문번호: 20220101123456
          </p>
        </div>
        <div
          v-else
          class="order-success-message-body message-body-bank d-flex flex-column align-items-center justify-content-center"
        >
          <p class="order-success-message font-weight-500 text-black mb-0">
            입금완료 시 배송이 시작됩니다.
          </p>
          <p class="dp-p text-black font-weight-500 mb-0">
            주문번호: 20220101123456
          </p>
        </div>
        <hr class="dp-hr justify-margin" />
        <div class="dp-panel">
          <p class="order-success__title dp-p font-weight-500 text-black">
            결제정보
          </p>
          <ul class="order-success__pay list-style-none">
            <li class="order-success__pay__list">
              <div class="pay__list__title">
                <p class="mb-0 dp-p-sm text-gray-700 font-weight-400">
                  결제금액
                </p>
              </div>
              <div class="pay__list__title-text">
                <p
                  class="order-success__pay__list-text mb-0 dp-p-sm text-gray-700 font-weight-400"
                >
                  <span class="atten-new text-black font-weight-500"
                    >63,200</span
                  >
                  원
                </p>
              </div>
            </li>
            <li class="order-success__pay__list">
              <div class="pay__list__title">
                <p class="mb-0 dp-p-sm text-gray-700 font-weight-400">
                  결제수단
                </p>
              </div>
              <div class="pay__list__title-text">
                <p
                  class="order-success__pay__list-text mb-0 dp-p-sm text-gray-700 font-weight-400"
                >
                  무통자입금
                </p>
              </div>
            </li>
            <li class="order-success__pay__list" v-if="isList">
              <div class="pay__list__title">
                <p class="mb-0 dp-p-sm text-gray-700 font-weight-400">
                  입금계좌
                </p>
              </div>
              <div class="pay__list__title-text">
                <p
                  class="order-success__pay__list-text mb-0 dp-p-sm text-gray-700 font-weight-400"
                >
                  국민은행 5674587878997<br />
                  (예금주 : 다다엠앤씨 )
                </p>
              </div>
            </li>
            <li class="order-success__pay__list" v-if="isList">
              <div class="pay__list__title">
                <p class="mb-0 dp-p-sm text-gray-700 font-weight-400">
                  입금기한
                </p>
              </div>
              <div class="pay__list__title-text">
                <p
                  class="order-success__pay__list-text mb-0 dp-p-sm text-gray-700 font-weight-500 text-black"
                >
                  <span class="atten-new font-weight-500 text-primary"
                    >2021.11.28 11:39</span
                  >
                  까지
                </p>
              </div>
            </li>
          </ul>
        </div>
        <hr class="dp-hr justify-margin" />
        <div class="dp-panel">
          <p class="order-success__title dp-p font-weight-500 text-black">
            배송정보
          </p>
          <ul class="order-success__pay list-style-none">
            <li class="order-success__pay__list">
              <div class="pay__list__title">
                <p class="mb-0 dp-p-sm text-gray-700 font-weight-400">
                  주문상품
                </p>
              </div>
              <div class="pay__list__title-text">
                <p
                  class="order-success__pay__list-text mb-0 dp-p-sm text-gray-700 font-weight-400"
                >
                  보랄전기드립주전자 외 2건
                </p>
              </div>
            </li>
            <li class="order-success__pay__list">
              <div class="pay__list__title">
                <p class="mb-0 dp-p-sm text-gray-700 font-weight-400">
                  받는사람
                </p>
              </div>
              <div class="pay__list__title-text">
                <p
                  class="order-success__pay__list-text mb-0 dp-p-sm text-gray-700 font-weight-400"
                >
                  박지민
                </p>
              </div>
            </li>
            <li class="order-success__pay__list">
              <div class="pay__list__title">
                <p class="mb-0 dp-p-sm text-gray-700 font-weight-400">연락처</p>
              </div>
              <div class="pay__list__title-text">
                <p
                  class="order-success__pay__list-text mb-0 dp-p-sm text-gray-700 font-weight-400"
                >
                  010-1234-1234
                </p>
              </div>
            </li>
            <li class="order-success__pay__list">
              <div class="pay__list__title">
                <p class="mb-0 dp-p-sm text-gray-700 font-weight-400">주소</p>
              </div>
              <div class="pay__list__title-text">
                <p
                  class="order-success__pay__list-text mb-0 dp-p-sm text-gray-700 font-weight-400"
                >
                  서울시 강남구 밤고개로 26길 50 101동 120호
                </p>
              </div>
            </li>
          </ul>
        </div>
        <div class="order-success__buttons">
          <b-button
            class="dp-btn btn-mr10 to-main-btn"
            variant="outline-gray-800"
            squared
          >
            <span>쇼핑 계속하기</span>
          </b-button>
          <b-button class="dp-btn text-white" variant="gray-800" squared>
            <span>주문내역 보기</span>
          </b-button>
        </div>
      </div>
    </b-modal>
    <!-- //Signup Success Modal -->
  </main>
</template>

<script>
export default {
  beforeCreate() {
    this.$store.commit("onSubHeaderOption", {
      title: "주문/결제",
      searchIcon: false,
      cartIcon: false,
      homeIcon: false,
    });
  },
  data() {
    return {
      //상세주소 데이터
      deliveryData01: "",
      deliveryData02: "",
      //체크박스 데이터
      deliveryCheckbox: {
        id: "deliveryChk01",
        checked: false,
      },

      //모달
      isOrderSuccess: true,
      isList: true,
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
  },
};
</script>
