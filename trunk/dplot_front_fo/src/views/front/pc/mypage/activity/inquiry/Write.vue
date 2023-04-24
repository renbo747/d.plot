<template>
  <main class="cs-inquiry">
    <div class="inquiry-write">
      <div class="container">
        <section class="cs-inquiry-header">
          <header class="cs-inquiry-section__header">
            <h2 class="cs-section__title text-black font-weight-700 mb-0">
              1:1 문의 등록
            </h2>
          </header>
        </section>
        <section class="inquiry-first-section dp-panel pt-0">
          <header class="inquiry-write-header">
            <h2 class="section-title mb-0 text-black font-weight-700">
              문의유형
            </h2>
          </header>
          <div class="section-select">
            <base-dropdown
              v-model="selectOptionData"
              :options="sampleSelectOptions"
              placeholder="유형을 선택해주세요."
              @input="handleSelect"
            />
          </div>
        </section>
        <section class="inquiry-second-section dp-panel pt-0">
          <header class="inquiry-write-header">
            <div class="inquiry-write-header-top d-flex">
              <h2 class="section-title mb-0 text-black font-weight-700">
                주문상품 (선택)
              </h2>
              <div class="inquiry-write-btn__wrap">
                <b-button
                  class="dp-btn btn-h32 not-hover inquiry-write-btn"
                  variant="outline-gray-800"
                  v-b-modal.selectProductModal
                  squared
                >
                  <span
                    class="dp-p-sm text-gray-800 font-weight-400"
                    v-if="isChangeProduct"
                    >주문상품선택</span
                  >
                  <span class="dp-p-sm text-gray-800 font-weight-400" v-else
                    >주문상품변경</span
                  >
                </b-button>
              </div>
            </div>
            <div class="inquiry-write__items" v-if="isShow">
              <div>
                <p
                  class="inquiry-write__items__name mb-0 text-gray-700 font-weight-500"
                >
                  팝아트 LED 무선 포터블 램프 (레드, L)
                </p>
                <p class="mb-0 text-gray-700 font-weight-500">
                  팝아트 LED 무선 포터블 램프 (블루, L)
                </p>
              </div>
            </div>
          </header>
        </section>
        <section class="inquiry-third-section dp-panel pt-0">
          <header class="inquiry-write-header">
            <h2 class="section-title mb-0 text-black font-weight-700">내용</h2>
          </header>
          <div class="cursor">
            <base-textarea
              v-model="textareaData"
              placeholder="내용을 입력해주세요. (최대 500자)"
              maxCount="500"
            ></base-textarea>
          </div>
        </section>
        <section class="inquiry-fourth-section">
          <header class="inquiry-write-header">
            <h2 class="section-title mb-0 text-black font-weight-700">
              이미지 / 동영상 첨부 (선택)
            </h2>
          </header>
          <div class="inquiry-fourth-section-slide">
            <div class="mb-20 cursor">
              <image-upload :is-mobile="false" :file-list="registerModalData" />
            </div>
            <div>
              <ul class="ul-dot">
                <li>
                  <p
                    class="inquiry-write__items__name text-gray-700 mb-0 font-weight-400"
                  >
                    이미지는 10MB 이하, 동영상은 50MB 이하 파일을 이미지 최대
                    10개, 동영상 최대 1개까지 등록 가능합니다.
                  </p>
                </li>
                <li>
                  <p
                    class="inquiry-write__items__name text-gray-700 mb-0 font-weight-400"
                  >
                    문의하신 내용에 답변이 등록되면 회원정보의 이메일과
                    휴대전화로 안내해드립니다.
                  </p>
                </li>
              </ul>
            </div>
          </div>
        </section>
        <section class="inquiry-footer-section">
          <div class="footer-section-btn btn-group row">
            <div class="col-6">
              <router-link to="/cs/inquiry/index">
                <b-button
                  class="dp-btn text-gray-800 inquiry-footer-section__btn"
                  variant="outline-gray-800 not-hover"
                  >취소하기</b-button
                >
              </router-link>
            </div>
            <div class="col-6">
              <router-link to="/">
                <b-button
                  class="dp-btn text-white inquiry-footer-section__btn"
                  variant="gray-800"
                  >등록하기</b-button
                >
              </router-link>
            </div>
          </div>
        </section>
      </div>
    </div>
    <!-- 주문상품선택 Modal -->
    <b-modal
      id="selectProductModal"
      modal-class="dp-modal page-layer select-product-modal"
      scrollable
      :hide-footer="true"
    >
      <!-- 주문상품선택 Modal Header -->
      <template #modal-header="{ cancel }">
        <h5 class="modal-title">주문상품선택</h5>
        <i class="modal-close" @click="cancel()">
          <img
            src="@assets.mobile/img/icon/icon-close-black-20px.svg"
            alt="닫기"
          />
        </i>
      </template>

      <!-- 주문상품선택 Modal Body -->
      <div class="select-product-body">
        <div class="select-product-header">
          <!-- scss index scss 안 select box 참조 -->
          <div class="cs-product-qna dp-panel pt-0">
            <div class="cs-product-qna__header__select row">
              <div class="col-6">
                <base-dropdown
                  placeholder="최근 1개월"
                  class="inquiry-select"
                  v-model="currentSelect"
                  :options="inquiryPeriodSelect"
                  @input="dataCheck"
                />
              </div>
              <div class="col-6">
                <base-dropdown
                  placeholder="전체문의"
                  class="inquiry-select"
                  v-model="currentSelect02"
                  :options="inquiryTotalSelect"
                  @input="dataCheck"
                />
              </div>
            </div>
          </div>
        </div>
        <section class="select-product-area dp-panel pt-0">
          <div>
            <div class="select-product-item-info pt-0">
              <div class="select-product-item">
                <div class="select-product-date__list">
                  <div class="select-product-date__list__data">
                    <span class="date__list__span text-gray-700 font-weight-400"
                      >2022.07.01</span
                    >
                    <span class="dp-bar select-product-date-bar"></span>
                    <span class="date__list__span text-gray-800 font-weight-500"
                      >2022092812345</span
                    >
                  </div>
                </div>
              </div>
            </div>
            <ul class="select-product-item-ul list-style-none dp-panel">
              <li class="select-product-item-li">
                <div>
                  <product-cart-pc
                    :is-badge="false"
                    :is-price="false"
                    :is-option="true"
                    :is-icon="false"
                    :is-modal="false"
                    :product-info="productCartData[0]"
                  />
                </div>
              </li>
              <li class="select-product-item-li">
                <div>
                  <product-cart-pc
                    :is-badge="false"
                    :is-price="false"
                    :is-option="true"
                    :is-icon="false"
                    :is-modal="false"
                    :product-info="productCartData[1]"
                  />
                </div>
              </li>
            </ul>
          </div>
          <hr class="dp-hr h6" />
          <div>
            <div class="select-product-item-info">
              <div class="select-product-item">
                <div class="select-product-date__list">
                  <div class="select-product-date__list__data">
                    <span class="date__list__span text-gray-700 font-weight-400"
                      >2022.07.01</span
                    >
                    <span class="dp-bar select-product-date-bar"></span>
                    <span class="date__list__span text-gray-800 font-weight-500"
                      >2022092812345</span
                    >
                  </div>
                </div>
              </div>
            </div>
            <ul class="select-product-item-ul list-style-none dp-panel">
              <li class="select-product-item-li">
                <div>
                  <product-cart-pc
                    :is-badge="false"
                    :is-price="false"
                    :is-option="true"
                    :is-icon="false"
                    :is-modal="false"
                    :product-info="productCartData[2]"
                  />
                </div>
              </li>
              <li class="select-product-item-li">
                <div>
                  <product-cart-pc
                    :is-badge="false"
                    :is-price="false"
                    :is-option="true"
                    :is-icon="false"
                    :is-modal="false"
                    :product-info="productCartData[3]"
                  />
                </div>
              </li>
            </ul>
          </div>
        </section>
        <section class="select-footer-section">
          <div class="footer-section-btn btn-group row">
            <b-button
              class="dp-btn text-gray-800 login-btn__close col-6"
              variant="outline-gray-800 not-hover"
              block
              @click="$bvModal.hide('selectProductModal')"
              >취소하기</b-button
            >
            <b-button
              class="dp-btn text-white login-btn__confirm col-6"
              variant="gray-800"
              >선택하기</b-button
            >
          </div>
        </section>
      </div>
    </b-modal>
    <!-- // 주문상품선택 Modal -->
  </main>
</template>

<script>
export default {
  data() {
    return {
      isChangeProduct: false, //주문상품선택 true , 상품선택 시 변환되는 주문상품변경 false
      isShow: true, //주문상품변경 true 일때 나오는 주문상품명
      // 1:1 문의 등록 셀렉트 박스
      selectOptionData: "",
      sampleSelectOptions: [
        {
          label: "샘플 옵션 01",
          value: "샘플 데이터 01",
        },
        {
          label: "샘플 옵션 02",
          value: "샘플 데이터 02",
        },
      ],
      textareaData: "",
      registerModalData: [
        {
          id: 1,
          src: require("@/assets/pc/img/shop/img-shop-card01.jpg"),
          alt: "이미지 미리보기",
          type: "image",
        },
        {
          id: 2,
          src: require("@/assets/pc/img/shop/img-shop-card02.jpg"),
          alt: "이미지 미리보기",
          type: "image",
        },
        {
          id: 3,
          src: require("@/assets/pc/img/shop/img-shop-card03.jpg"),
          alt: "이미지 미리보기",
          type: "image",
        },
        {
          id: 4,
          src: require("@/assets/pc/img/shop/img-shop-card04.jpg"),
          alt: "이미지 미리보기",
          type: "video",
        },
      ],
      //상품선택모달
      currentSelect: "",
      currentSelect02: "",
      inquiryPeriodSelect: [
        {
          label: "최근 1개월",
          value: "최근 1개월",
        },
        {
          label: "최근 3개월",
          value: "최근 3개월",
        },
        {
          label: "최근 6개월",
          value: "최근 6개월",
        },
        {
          label: "최근 1년",
          value: "최근 6년",
        },
        {
          label: "이전 내역",
          value: "이전 내역",
        },
      ],
      inquiryTotalSelect: [
        {
          label: "전체",
          value: "전체",
        },
        {
          label: "전체",
          value: "전체",
        },
        {
          label: "전체",
          value: "전체",
        },
        {
          label: "전체",
          value: "전체",
        },
        {
          label: "전체",
          value: "전체",
        },
        {
          label: "전체",
          value: "전체",
        },
      ],
      productCartData: [
        {
          id: "productData01",
          src: require("@/assets/mobile/img/shop/img-shop-product01.jpg"),
          ctg: "Artemide",
          title: "피어스 마이크로 펜던램프",
          priceNumber: 560000,
        },
        {
          id: "productData02",
          src: require("@/assets/mobile/img/shop/img-shop-product02.jpg"),
          ctg: "Artemide",
          title: "피어스 마이크로 펜던램프",
          priceNumber: 560000,
        },
        {
          id: "productData03",
          src: require("@/assets/mobile/img/shop/img-shop-product03.jpg"),
          ctg: "Artemide",
          title: "피어스 마이크로 펜던램프",
          priceNumber: 560000,
        },
        {
          id: "productData04",
          src: require("@/assets/mobile/img/shop/img-shop-product04.jpg"),
          ctg: "Artemide",
          title: "피어스 마이크로 펜던램프",
          priceNumber: 560000,
        },
      ],
    };
  },
  methods: {
    handleSelect() {
    },
    dataCheck(val) {
    },
  },
};
</script>
