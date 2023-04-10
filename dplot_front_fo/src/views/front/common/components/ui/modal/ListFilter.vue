<template>
  <!-- 상품 필터 Modal -->
  <b-modal
    id="productFilterModal"
    modal-class="dp-modal page-layer product-filter-modal"
    scrollable
    :hide-footer="true"
  >
    <!-- Product Filter Modal Header -->
    <template #modal-header="{ cancel }">
      <h5 class="modal-title">필터</h5>
      <i class="modal-close" @click="cancel()">
        <img src="@/assets/common/icon/icon-close-modal-30px.svg" alt="닫기" />
      </i>
    </template>

    <!-- Product Filter Modal Body -->09+
    <template #default="{ ok }">
      <div class="page-layer__body">
        <!-- 카테고리 -->
        <div class="filter__box">
          <p class="filter__title">카테고리</p>
          <ul class="list-style-none filter__ul category__ul">
            <li>
              <label for="chkall" class="filter__label mb-0">
                <input
                  id="chkall"
                  type="checkbox"
                  name="category"
                  v-model="setCateList"
                  :value="$route.query.idx"
                  @change="onChecked($route.query.idx)"
                  hidden
                />
                <span class="label__item">전체</span>
              </label>
            </li>
            <li v-for="(list, index) in categoryData" :key="index">
              <label :for="list.id" class="filter__label mb-0">
                <input
                  id="ca"
                  type="checkbox"
                  name="category"
                  v-model="setCateList"
                  :value="$route.query.idx"
                  @change="onChecked($route.query.idx)"
                  hidden
                />
                <span class="label__item">{{ list.label }}</span>
              </label>
            </li>
          </ul>
        </div>

        <!-- 브랜드 -->
        <div class="filter__box">
          <p class="filter__title">브랜드</p>
          <swiper :options="modalBrandOption">
            <swiper-slide v-for="(list, index) in modalBrandData" :key="index">
              <ul class="list-style-none filter__ul brand__ul">
                <li v-for="(list, index) in list.data" :key="index">
                  <label :for="list.id" class="filter__label mb-0">
                    <input
                      :id="list.id"
                      type="checkbox"
                      name="category"
                      :checked="list.checked"
                      hidden
                    />
                    <div class="label__item">
                      <template v-if="list.type === 'img'">
                        <img
                          :src="`${require('@/assets/common/dummy/' +
                            list.img)}`"
                          alt="브랜드 로고"
                        />
                      </template>
                      <template v-else>
                        {{ list.text }}
                      </template>
                    </div>
                  </label>
                </li>
              </ul>
            </swiper-slide>

            <div class="swiper-pagination" slot="pagination"></div>
          </swiper>
        </div>

        <!-- 컬러 -->
        <div class="filter__box">
          <p class="filter__title">컬러</p>
          <ul class="color__ul list-style-none">
            <li v-for="(list, index) in modalColorData" :key="index">
              <label :for="list.id" class="color__label mb-0">
                <input
                  :id="list.id"
                  type="checkbox"
                  name="color"
                  :checked="list.checked"
                  hidden
                />
                <span
                  class="label__item"
                  :style="{
                    backgroundColor: list.bgColor,
                    borderColor: list.borderColor,
                  }"
                ></span>
              </label>
            </li>
          </ul>
        </div>

        <!-- 혜택 -->
        <div class="filter__box">
          <p class="filter__title">혜택</p>
          <ul class="filter__ul benefit__ul list-style-none">
            <li v-for="(list, index) in modalBenefitData" :key="index">
              <label :for="list.id" class="filter__label mb-0">
                <input
                  :id="list.id"
                  type="checkbox"
                  name="benefit"
                  :checked="list.checked"
                  hidden
                />
                <span class="label__item">{{ list.label }}</span>
              </label>
            </li>
          </ul>
        </div>

        <!-- 가격 -->
        <div class="filter__box">
          <p class="filter__title">가격</p>
          <div>
            <p class="atten-new font-weight-500 dp-p-sm text-center">
              {{ modalPriceValue.currentValue[0] | comma }} ~
              {{ modalPriceValue.currentValue[1] | comma }}
            </p>
            <div class="filter__range-slider">
              <vue-slider
                v-model="modalPriceValue.currentValue"
                :enable-cross="false"
                :tooltip="'none'"
                :min="modalPriceValue.min"
                :max="modalPriceValue.max"
                :interval="modalPriceValue.interval"
                :dot-size="30"
                :adsorb="true"
              >
                <template v-slot:dot>
                  <span class="filter__dot">
                    <span class="dot-box"></span>
                  </span>
                </template>
              </vue-slider>
            </div>

            <ul class="filter__ul type02 row list-style-none">
              <li
                v-for="(list, index) in modalPriceData"
                :key="index"
                class="col-6"
              >
                <label :for="list.id" class="filter__label mb-0">
                  <input
                    :id="list.id"
                    type="radio"
                    name="price"
                    :checked="list.checked"
                    hidden
                  />
                  <span class="label__item">
                    {{ list.startPrice | comma }} ~
                    {{ list.endPrice | comma }}
                  </span>
                </label>
              </li>
            </ul>
          </div>
        </div>

        <!-- 평점 -->
        <div class="filter__box">
          <p class="filter__title">평점</p>
          <ul class="filter__ul type02 row list-style-none">
            <li
              v-for="(list, index) in modalRatingData"
              :key="index"
              class="col-4"
            >
              <label :for="list.id" class="filter__label mb-0">
                <input
                  :id="list.id"
                  type="checkbox"
                  name="rating"
                  :checked="list.checked"
                  hidden
                />
                <span class="label__item">
                  <img
                    v-for="(data, index) in list.rating"
                    :key="index"
                    src="@assets.admin/common/icon/icon-star-black-18px.svg"
                    :alt="`${data} 평점`"
                  />
                </span>
              </label>
            </li>
          </ul>
        </div>

        <!-- 버튼 그룹 -->
        <div class="btn-group d-flex justify-content-center align-items-center">
          <b-button variant="btn dp-btn btn-outline-gray-800 rounded-0">
            <span>초기화</span>
            <i class="dp-icon reset-icon"></i>
          </b-button>
          <b-button
            variant="btn dp-btn text-white btn-gray-800 rounded-0"
            @click="ok()"
            >선택보기</b-button
          >
        </div>
      </div>
    </template>
  </b-modal>
</template>

<script>
export default {
  components: {
    swiper,
    swiperSlide,
    VueSlider,
  },
  data() {
    return {};
  },
  methods: {
    getCateList() {
      let param = {
        idx: this.$route.query.idx,
        platform: window.sessionStorage.getItem("platform"),
      };
      this.$http.post("/shop/filter/cateList", param).then((result) => {
        if (result.statusCode == 200) {
          this.cateList = result.data.catelist;
          this.cateList.forEach((element) => {
            element.checked = false;
          });
        }
      });
    },
  },
};
</script>

<style>
</style>