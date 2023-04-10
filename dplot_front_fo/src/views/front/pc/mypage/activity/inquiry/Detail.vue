<template>
  <main class="cs-inquiry">
    <div class="container">
      <div class="cs-inquiry-detail">
        <!-- 헤더 -->
        <section class="cs-inquiry-header">
          <header class="cs-inquiry-section__header">
            <h2
              class="cs-section__title text-black font-weight-700 mb-0"
              v-if="isInquiry === 'Inquiry'"
            >
              1:1 문의
            </h2>
            <h2
              class="cs-section__title text-black font-weight-700 mb-0"
              v-if="isInquiry === 'ProductQna'"
            >
              상품Q&A
            </h2>
          </header>
        </section>
        <!-- 배송상태 및 문의내용 -->
        <section class="inquiry-detail-state dp-panel pt-0">
          <div class="inquiry-detail-state-header d-flex">
            <div class="inquiry-detail-state-header__left d-flex">
              <p
                class="mb-0 detail-state-header__left__title font-weight-700 text-black mr-10"
              >
                답변대기
              </p>
              <p
                class="mb-0 detail-state-header__left__date text-gray-700 font-weight-400"
              >
                2021.11.04
              </p>
            </div>
            <div class="inquiry-detail-state-header__right">
              <div class="inquiry-detail-edit text-gray-800">
                <span
                  class="inquiry-detail-edit-text text-gray-800 font-weight-400 cursor"
                  >삭제</span
                >
                <span class="dp-bar"></span>
                <span
                  class="inquiry-detail-edit-text text-gray-800 font-weight-400 cursor"
                  >수정</span
                >
              </div>
            </div>
          </div>
          <div class="inquiry-detail-state-body">
            <header class="detail-state-body-header">
              <h2
                class="detail-state-body-header__text dp-p text-gray-700 font-weight-400 mb-0"
              >
                [배송] 배송 문의합니다.
              </h2>
            </header>
            <div class="inquiry-detail-state-body-info">
              <div class="state-body-info">
                <p class="mb-0 state-body-info__p text-black font-weight-400">
                  이거 어제 송장등록은 되었는데 왜 배송조회가 안될까요? 배송이
                  언제 되는지 확인좀 부탁드립니다.
                  <br />
                  수고하세요!
                </p>
              </div>
            </div>
          </div>
        </section>
        <!-- swiper 내용 -->
        <section class="inquiry-detail-product">
          <!--  1:1 문의일때  -->
          <div class="detail-product__info" v-if="isInquiry === 'Inquiry'">
            <div class="detail-product__info__item d-flex mb-10">
              <div class="detail-product__info__item__title">
                <p
                  class="mb-0 info__item__title__p font-weight-400 text-gray-700"
                >
                  주문번호
                </p>
              </div>
              <div class="detail-product__info__item__text">
                <p
                  class="mb-0 info__item__title__p font-weight-500 text-gray-800"
                >
                  2022092812345
                </p>
              </div>
            </div>
            <div class="detail-product__info__item order d-flex">
              <div class="detail-product__info__item__title">
                <p
                  class="mb-0 info__item__title__p font-weight-400 text-gray-700"
                >
                  주문상품
                </p>
              </div>
              <div class="detail-product__info__item__text">
                <p
                  class="mb-0 info__item__title__p font-weight-500 text-gray-800"
                >
                  팝아트 LED 무선 포터블 램프 (레드, L)
                </p>
                <p
                  class="mb-0 info__item__title__p font-weight-500 text-gray-800"
                >
                  팝아트 LED 무선 포터블 램프 (블루, L)
                </p>
              </div>
            </div>
          </div>
          <!--  상품 Q&A 일때  -->
          <div
            class="detail-product__info__qna"
            v-if="isInquiry === 'ProductQna'"
          >
            <div class="detail-product__info__qna__header">
              <div class="qna__header mb-20">
                <h2 class="qna__header__title text-black font-weight-500 mb-0">
                  문의상품
                </h2>
              </div>
              <div class="qna__body">
                <div class="inquiry-qna-body__thumbnail d-flex">
                  <div class="inquiry-qna-body__thumbnail__img">
                    <div>
                      <product-thumbnail
                        class="mr-10 cursor"
                        style="width: 150px"
                        :thumbnail-info="qnaThumbnailData"
                      />
                    </div>
                  </div>
                  <div class="inquiry-qna-body__thumbnail__info">
                    <p
                      class="qna__body-text text-gray-700 font-weight-400 mb-0"
                    >
                      라운드테이블
                    </p>
                    <div class="d-flex align-items-center">
                      <span
                        class="qna__body-text font-weight-400 text-gray-700"
                      >
                        옵션 : 무선램프
                      </span>
                      <span class="dp-bar"></span>
                      <span class="qna__body-text font-weight-400 text-gray-700"
                        >Matt White</span
                      >
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="detail-product__info__slide">
            <div class="product__info__slide__info cursor">
              <swiper class="swiper" :options="swiperDetailOption">
                <swiper-slide
                  v-for="(list, index) in detailOptionData"
                  :key="index"
                >
                  <div class="info__slide__img">
                    <figure
                      class="info__slide__img__thumbnail cursor"
                      @click="showModal(list.id, index)"
                    >
                      <img :src="list.src" alt="리뷰 이미지" />
                    </figure>
                  </div>
                </swiper-slide>
              </swiper>
              <div class="detail-product__info__slide__btn">
                <div class="swiper-btn-prev">
                  <img
                    src="@assets.pc/img/icon/icon-arrowLeft-white-41px.svg"
                    alt="prev button"
                  />
                </div>
                <div class="swiper-btn-next">
                  <img
                    src="@assets.pc/img/icon/icon-arrowRight-white-41px.svg"
                    alt="next button"
                  />
                </div>
              </div>
            </div>
          </div>
          <div class="detail__info__text-box dp-panel" v-if="isMemo">
            <div class="detail__info__text-wrap">
              <span class="detail__info__text text-black font-weight-400"
                >답변을 준비중입니다. 조금만 기다려주세요.</span
              >
            </div>
          </div>
          <div class="detail__info__text-box not-empty dp-panel" v-else>
            <div class="text-box-flex d-flex">
              <span class="detail__info__text text-black font-weight-400 mb-20">
                고객님이 문의하신 상품은 CJ 대한통운 송장번호 123412341234로
                발송되어, 현재 집하중으로 조회됩니다.<br />
                명절 연휴로 인한 물량 증가로 택배사 배송이 지연될 수 있으니 양해
                부탁드립니다
              </span>
              <span class="text-gray-700 font-weight-400 dp-p-sm"
                >2021.11.04</span
              >
            </div>
          </div>
          <div class="detail-product__info__list__btn">
            <router-link to="/mypage/inquiry">
              <b-button
                class="dp-btn info__list__btn not-hover"
                variant="outline-gray-800"
                squared
              >
                <span class="info__list__btn__text text-gray-800"
                  >목록보기</span
                >
              </b-button>
            </router-link>
          </div>
        </section>
      </div>
    </div>
    <!-- 이미지 모달 -->
    <div class="img-modal" v-if="isModalOpen === true" @click="closeModal">
      <div class="image-content">
        <template v-if="isMediaType">
          <swiper :options="modalSwiperOption">
            <swiper-slide
              class="swiper-slide"
              v-for="(list, index) in detailOptionData"
              :key="index"
            >
              <figure @click.stop>
                <img :src="list.src" alt="리뷰 이미지 확대" />
              </figure>
            </swiper-slide>
          </swiper>
          <div class="modal-swiper-prev" @click.stop>
            <img
              src="@assets.pc/img/icon/icon-arrowLeft-white-41px.svg"
              alt="prev button"
            />
          </div>
          <div class="modal-swiper-next" @click.stop>
            <img
              src="@assets.pc/img/icon/icon-arrowRight-white-41px.svg"
              alt="next button"
            />
          </div>
        </template>
        <template v-else>
          <div class="video-area">
            <iframe
              width="560"
              height="315"
              src="https://www.youtube-nocookie.com/embed/PIoK5ZdYk6E?controls=0"
              frameborder="0"
              allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
              allowfullscreen
            ></iframe>
          </div>
        </template>
        <i class="dp-icon cursor-pointer" @click="closeModal"></i>
      </div>
    </div>
    <!-- // 이미지 모달 -->
  </main>
</template>

<script>
import { swiper, swiperSlide } from "vue-awesome-swiper";

export default {
  components: {
    swiper,
    swiperSlide,
  },
  beforeCreate() {
    this.$store.commit("onSubHeaderOption", {
      title: "1:1 문의",
      searchIcon: true,
      cartIcon: true,
      homeIcon: false,
    });
  },
  data() {
    return {
      isMemo: true, // 메모페드가 비어있을 때 true
      imgUrl: "", // 초기값이자 변수선언
      // 페이지 종류 문의 "Inquiry" / 상품 Q&A "ProductQna"
      isInquiry: "ProductQna",
      swiperDetailOption: {
        slidesPerView: 5,
        spaceBetween: 10,
        navigation: {
          nextEl: ".swiper-btn-next",
          prevEl: ".swiper-btn-prev",
        },
        observeParents: true,
        observer: true,
        loop: true,
      },
      isMediaType: true, // Todo : 미디어 타입으로 이미지와 영상 표시 구분 필요.
      isModalOpen: false,
      selectedIndex: "",
      detailOptionData: [
        {
          id: "Detail01",
          src: require("@/assets/pc/img/shop/img-shop-card01.jpg"),
          detailImg: [
            require("@/assets/pc/img/shop/img-shop-trend01.jpg"),
            require("@/assets/pc/img/shop/img-shop-trend02.jpg"),
            require("@/assets/pc/img/shop/img-shop-trend03.jpg"),
            require("@/assets/pc/img/shop/img-shop-trend07.jpg"),
          ],
        },
        {
          id: "Detail02",
          src: require("@/assets/pc/img/shop/img-shop-card02.jpg"),
          detailImg: [
            require("@/assets/pc/img/shop/img-shop-trend01.jpg"),
            require("@/assets/pc/img/shop/img-shop-trend02.jpg"),
            require("@/assets/pc/img/shop/img-shop-trend03.jpg"),
            require("@/assets/pc/img/shop/img-shop-trend07.jpg"),
          ],
        },
        {
          id: "Detail03",
          src: require("@/assets/pc/img/shop/img-shop-card03.jpg"),
          detailImg: [
            require("@/assets/pc/img/shop/img-shop-trend01.jpg"),
            require("@/assets/pc/img/shop/img-shop-trend02.jpg"),
            require("@/assets/pc/img/shop/img-shop-trend03.jpg"),
            require("@/assets/pc/img/shop/img-shop-trend07.jpg"),
          ],
        },
        {
          id: "Detail04",
          src: require("@/assets/pc/img/shop/img-shop-card04.jpg"),
          detailImg: [
            require("@/assets/pc/img/shop/img-shop-trend01.jpg"),
            require("@/assets/pc/img/shop/img-shop-trend02.jpg"),
            require("@/assets/pc/img/shop/img-shop-trend03.jpg"),
            require("@/assets/pc/img/shop/img-shop-trend07.jpg"),
          ],
        },
        {
          id: "Detail05",
          src: require("@/assets/pc/img/shop/img-shop-card05.jpg"),
          detailImg: [
            require("@/assets/pc/img/shop/img-shop-trend01.jpg"),
            require("@/assets/pc/img/shop/img-shop-trend02.jpg"),
            require("@/assets/pc/img/shop/img-shop-trend03.jpg"),
            require("@/assets/pc/img/shop/img-shop-trend07.jpg"),
          ],
        },
      ],
      qnaThumbnailData: {
        id: "qnaImg01",
        src: require("@/assets/pc/img/shop/img-shop-newin05.jpg"),
      },
      modalSwiperOption: {
        navigation: {
          nextEl: ".modal-swiper-next",
          prevEl: ".modal-swiper-prev",
        },
      },
    };
  },
  methods: {
    showModal(id) {
      // this.imgUrl = imgUrl;
      this.selectedIndex = id - 1;

      this.isModalOpen = !this.isModalOpen;
    },
    closeModal() {
      this.isModalOpen = false;
    },
  },
  watch: {
    isModalOpen(val) {
      if (val) {
        document.documentElement.style.overflow = "hidden";
      } else {
        document.documentElement.style.overflow = "auto";
      }
    },
  },
};
</script>
