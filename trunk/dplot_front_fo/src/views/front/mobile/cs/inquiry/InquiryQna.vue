<template>
  <main class="cs-inquiry">
    <div class="container">
      <div class="cs-inquiry-detail qna">
        <section class="inquiry-detail-state dp-panel pb-0">
          <div class="inquiry-detail-state-header d-flex">
            <div class="inquiry-detail-state-header__left d-flex">
              <p class="mb-0 dp-caption font-weight-700 text-black mr-10">
                답변대기
              </p>
              <p class="mb-0 dp-caption text-gray-700 font-weight-400">
                2021.11.04
              </p>
            </div>
            <div class="inquiry-detail-state-header__right">
              <div class="inquiry-detail-edit text-gray-800">
                <span class="dp-caption text-gray-800 font-weight-400"
                  >삭제</span
                >
                <span class="dp-bar h10"></span>
                <span class="dp-caption text-gray-800 font-weight-400"
                  >수정</span
                >
              </div>
            </div>
          </div>
          <div class="inquiry-detail-state-body">
            <header class="detail-state-body-header">
              <h2
                class="detail-state-body-header__text dp-p text-gray-800 font-weight-400 mb-0"
              >
                [배송] 배송 문의합니다.
              </h2>
            </header>
            <div class="inquiry-detail-state-body-info">
              <div>
                <p class="mb-0 dp-p-sm text-black font-weight-400">
                  이거 어제 송장등록은 되었는데 왜 배송조회가 안될까요? 배송이
                  언제 되는지 확인좀 부탁드립니다. <br />
                  <br />수고하세요!
                </p>
              </div>
            </div>
          </div>
          <div class="detail-product__info__slide">
            <div class="product__info__slide__info dp-panel">
              <swiper class="swiper" :options="swiperDetailOption">
                <swiper-slide
                  v-for="(list, index) in detailOptionData"
                  :key="index"
                >
                  <div class="info__slide__img">
                    <figure
                      class="info__slide__img__thumbnail"
                      @click="showModal(list.id, index)"
                    >
                      <img :src="list.src" alt="리뷰 이미지" />
                    </figure>
                  </div>
                </swiper-slide>
              </swiper>
            </div>
          </div>
        </section>
        <section class="inquiry-detail-product dp-panel pt-0">
          <div class="inquiry-qna">
            <header class="inquiry-qna-header">
              <h2
                class="inquiry-qna-header__title mb-0 dp-p text-black font-weight-700"
              >
                문의상품
              </h2>
            </header>
            <div class="inquiry-qna-body">
              <div class="inquiry-qna-body__area">
                <div class="inquiry-qna-body__thumbnail d-flex">
                  <div class="inquiry-qna-body__thumbnail__img">
                    <div>
                      <product-thumbnail
                        class="mr-10"
                        style="width: 100px"
                        :thumbnail-info="qnaThumbnailData"
                      />
                    </div>
                  </div>
                  <div class="inquiry-qna-body__thumbnail__info">
                    <p class="dp-p text-black font-weight-400 mb-0">
                      스토케 트립트랩의자
                    </p>
                    <p class="dp-p-sm font-weight-400 text-gray-600">블루, L</p>
                  </div>
                </div>
              </div>

              <div class="detail__info__text-box not-empty dp-panel">
                <div class="text-box-flex d-flex">
                  <span class="dp-p-sm text-black font-weight-400 mb-20">
                    고객님이 문의하신 상품은 CJ 대한통운 송장번호 123412341234로
                    발송되어, 현재 집하중으로 조회됩니다.<br />
                    명절 연휴로 인한 물량 증가로 택배사 배송이 지연될 수 있으니
                    양해 부탁드립니다
                  </span>
                  <span class="text-gray-700 font-weight-400 dp-caption"
                    >2021.11.04</span
                  >
                </div>
              </div>
            </div>
          </div>
        </section>
      </div>
    </div>
    <!-- 이미지모달-->
    <div class="img-modal" v-if="isModalOpen === true" @click="closeModal">
      <div class="image-content">
        <template v-if="isMediaType">
          <swiper>
            <swiper-slide
              class="swiper-slide"
              v-for="(item, index) in detailOptionData"
              :key="index"
            >
              <figure @click.stop>
                <img :src="item.src" alt="리뷰 이미지 확대" />
              </figure>
            </swiper-slide>
          </swiper>
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
        <i class="dp-icon lg" @click="closeModal"></i>
      </div>
    </div>
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
      title: "상품 Q&A",
      searchIcon: true,
      cartIcon: true,
      homeIcon: false,
    });
  },
  data() {
    return {
      swiperDetailOption: {
        slidesPerView: "auto",
        spaceBetween: 10,
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
      modalSwiperOption: {
        slidesPerView: "auto",
        loop: true,
      },
      qnaThumbnailData: {
        id: "qnaImg01",
        src: require("@/assets/mobile/img/shop/img-shop-newin05.jpg"),
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
