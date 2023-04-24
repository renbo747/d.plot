<template>
  <main class="cs-inquiry">
    <section class="cs-inquiry-tab__wrap">
      <!-- 헤더 -->
      <section class="cs-inquiry-header">
        <header class="cs-inquiry-section__header">
          <h2 class="cs-section__title text-black font-weight-700 mb-0">
            나의 문의
          </h2>
        </header>
      </section>

      <div class="cs-inquiry-tabs">
        <b-tabs>
          <!-- 1:1 문의 탭 -->
          <b-tab title="1:1문의" active>
            <div v-if="isInquiry" class="inquiry__area">
              <div class="container" v-if="isInquiryList">
                <div class="cs-inquiry__header">
                  <div class="cs-inquiry__header__btn d-flex">
                    <div class="cs-inquiry__header__total">
                      <p
                        class="cs-inquiry__btn__total mb-0 font-weight-400 text-gray-700"
                      >
                        1:1 상담을 통해 접수된 문의내역입니다.<br />
                        추가로 문의하실 사항이 있으시면, 문의하기 또는 채팅
                        상담을 이용해주세요.
                      </p>
                    </div>
                    <div>
                      <router-link to="/mypage/inquiry/write">
                        <b-button
                          class="cs-inquiry__button dp-btn not-hover"
                          variant="outline-black"
                          squared
                        >
                          <i class="dp-icon icon-inquiry sm02 mr-1"></i>
                          <span
                            class="cs-inquiry__header-title text-black font-weight-400"
                            >문의하기</span
                          >
                        </b-button>
                      </router-link>
                    </div>
                  </div>
                  <div class="cs-inquiry__header__select row">
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
                <div class="cs-inquiry__list dp-panel pb-0">
                  <ul class="cs-inquiry__list__ul list-style-none">
                    <li
                      v-for="(list, index) in inquiryListData"
                      :key="index"
                      class="cs-inquiry__list__item cursor"
                    >
                      <div class="d-flex inquiry__list__item">
                        <div class="inquiry__list__item__box d-flex">
                          <p
                            class="inquiry__list__item__state mb-0 font-weight-700 text-black mr-10 mb-2"
                          >
                            {{ list.state }}
                          </p>
                          <p
                            class="mb-0 inquiry__list__item__date text-gray-700 font-weight-400"
                          >
                            {{ list.date }}
                          </p>
                        </div>
                        <div>
                          <p
                            class="mb-0 inquiry__list__item__text text-gray-700 font-weight-400"
                            @click="goToInquiryDetail"
                          >
                            {{ list.text }}
                          </p>
                        </div>
                      </div>
                    </li>
                  </ul>
                  <div class="inquiry-pagination dp-panel pb-0 d-flex">
                    <div class="inquiry-pagination-wrap">
                      <base-pagination />
                    </div>
                  </div>
                </div>
              </div>
              <div class="container" v-else>
                <div class="cs-inquiry__list__none">
                  <div class="cs-inquiry__list__circle">
                    <div class="d-flex flex-column align-items-center">
                      <div class="cs-inquiry__list__empty">
                        <i class="dp-icon xl02 icon-not-found"></i>
                      </div>
                      <div class="cs-inquiry__list__text">
                        <div class="cs-inquiry__list__empty__text">
                          <p
                            class="cs-inquiry__list__empty__text__p mb-0 font-weight-400 text-gray-700"
                          >
                            등록된 문의 내역이 없습니다.
                          </p>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </b-tab>
          <!-- 상품Q&A 탭 -->
          <b-tab title="상품Q&A">
            <div class="cs-product-qna__tab">
              <div class="container" v-if="isQna">
                <div class="cs-product-qna dp-panel pb-0">
                  <div class="cs-product-qna-header dp-panel pt-0">
                    <div class="cs-product-qna__header__text">
                      <header class="qna__header__text">
                        <p class="qna__header__text__p mb-0 text-gray-700">
                          상품Q&A를 통해 접수된 문의 내역입니다.
                        </p>
                      </header>
                    </div>
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
                <div class="cs-qna-list-wrap">
                  <ul class="cs-qna-list-ul list-style-none">
                    <li
                      v-for="(item, index) in qnaListData"
                      :key="index"
                      class="cs-qna-list__item cursor"
                      @change="handleIcon(item.id)"
                    >
                      <div class="cs-qna-list__item__state d-flex mb-20">
                        <p
                          class="cs-qna-list__item__state__text mb-0 font-weight-700 text-black mr-10"
                        >
                          {{ item.state }}
                        </p>
                        <p
                          class="cs-qna-list__item__state__date mb-0 text-gray-700 font-weight-400"
                        >
                          {{ item.date }}
                        </p>
                        <div class="cs-qna-list__item__state__icon">
                          <i
                            class="dp-icon h22 icon-lock ml-2"
                            v-if="item.id === 'qna1' || item.id === 'qna3'"
                          ></i>
                        </div>
                      </div>
                      <div class="cs-qna-list__item__info d-flex">
                        <div class="cs-qna-list__item__thumbnail">
                          <product-thumbnail
                            style="width: 150px"
                            :is-disabled="true"
                            :thumbnail-info="{
                              id: item.id,
                              src: item.src,
                            }"
                          />
                        </div>
                        <div
                          class="cs-qna-list__item__text d-flex"
                          @click="goToInquiryQna"
                        >
                          <header class="qna-item__title mb-10">
                            <p class="mb-10 font-weight-500">
                              {{ item.brname }}
                            </p>
                            <h2
                              class="qna-item__title__h2 font-weight-700 text-gray-800 mb-0"
                            >
                              {{ item.title }}
                            </h2>
                          </header>
                          <div class="qna-item__text">
                            <p
                              class="qna-item__text__p text-gray-700 font-weight-400 mb-0"
                              v-html="item.text"
                            ></p>
                          </div>
                        </div>
                      </div>
                    </li>
                  </ul>
                  <div class="inquiry-pagination dp-panel pb-0 d-flex">
                    <div class="inquiry-pagination-wrap">
                      <base-pagination />
                    </div>
                  </div>
                </div>
              </div>
              <div class="container" v-else>
                <div class="cs-inquiry__list__none">
                  <div class="cs-inquiry__list__circle">
                    <div class="d-flex flex-column align-items-center">
                      <div class="cs-inquiry__list__empty">
                        <i class="dp-icon xl02 icon-not-found"></i>
                      </div>
                      <div class="cs-inquiry__list__text">
                        <div class="cs-inquiry__list__empty__text">
                          <p
                            class="cs-inquiry__list__empty__text__p mb-0 font-weight-400 text-gray-700"
                          >
                            등록된 문의 내역이 없습니다.
                          </p>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </b-tab>
        </b-tabs>
      </div>
    </section>
  </main>
</template>

<script>
export default {
  data() {
    return {
      isInquiry: true, // true = 1:1 문의 / false = 상품 Q&A
      isInquiryList: true, // true 일때 문의사항 리스트 , false 일때 문의사항 없음
      isQna: true, // true 일때 Qna 리스트 , false 일때 상품Qna 없음
      //문의 셀렉트 박스
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
      //문의 사항 리스트
      inquiryListData: [
        {
          state: "답변대기",
          date: "2021.11.04",
          text: "[배송] 배송 문의합니다.",
        },
        {
          state: "답변대기",
          date: "2021.11.04",
          text: "[배송] 배송지를 변경 하고싶습니다.",
        },
        {
          state: "답변대기",
          date: "2021.11.04",
          text: "[회원/멤버십] 평생회원 혜택이 뭐가있나요?",
        },
        {
          state: "답변대기",
          date: "2021.11.04",
          text: "[결제] 결제방법이 뭐가있나요?",
        },
        {
          state: "답변대기",
          date: "2021.11.04",
          text: "[결제] 결제방법이 뭐가있나요?",
        },
      ],
      //상품 Q&A
      qnaListData: [
        {
          id: "qna1",
          src: require("@/assets/pc/img/shop/img-shop-product01.jpg"),
          state: "답변대기",
          date: "2021.11.04",
          title: "스토케 트립트랩의자 (블루, L)",
          text: "[배송] 이 상품 배송 얼마나 걸리나요? 확인해주시면 감사하겠습니다. 며칠 후에 선물해야되서 필요해서 그렇습니다.",
        },
        {
          id: "qna2",
          src: require("@/assets/pc/img/shop/img-shop-product02.jpg"),
          state: "답변대기",
          date: "2021.11.04",
          title: "스토케 트립트랩의자 (블루, L)",
          text: "[배송] 배송지를 변경 하고싶습니다.",
        },
        {
          id: "qna3",
          src: require("@/assets/pc/img/shop/img-shop-product03.jpg"),
          state: "답변완료",
          date: "2021.11.04",
          title: "스토케 트립트랩의자 (블루, L)",
          text: "[회원/멤버십] 평생회원 혜택이 뭐가있나요?",
        },
        {
          id: "qna4",
          src: require("@/assets/pc/img/shop/img-shop-product04.jpg"),
          state: "답변완료",
          date: "2021.11.04",
          title: "스토케 트립트랩의자 (블루, L)",
          text: "[결제] 결제방법이 뭐가있나요?",
        },
        {
          id: "qna5",
          src: require("@/assets/pc/img/shop/img-shop-product05.jpg"),
          state: "답변대기",
          date: "2021.11.04",
          title: "스토케 트립트랩의자 (블루, L)",
          text: "[결제] 결제방법이 뭐가있나요?",
        },
      ],
    };
  },
  methods: {
    dataCheck(val) {
    },
    goToInquiryWrite() {
      this.$router.push("/mypage/inquiry/write");
    },
    goToInquiryDetail() {
      this.$router.push("/mypage/inquiry/detail");
    },
    goToInquiryQna() {
      this.$router.push("/mypage/inquiry/detail");
    },
    handleIcon(index) {
      if (index === "qna1" || index === "qna3") {
        this.isDelivery = true;
      }
    },
    handleSelect() {
    },
    //모달
    handleHideModal() {
      this.$root.$emit("bv::hide::modal", "InquiryModal");
      this.$root.$emit("bv::show::modal", "selectProductModal");
    },
  },
};
</script>
