<template>
  <div class="shop-list new-layout_list">
    <div class="container">
      <div class="container-inner">
        <div>
          <h2 class="atten-new font-weight-800 dp-mt-60" style="margin-bottom: 35px;">
            {{ $util.isNull(parentInfo) ? "" : parentInfo.name }}
          </h2>
          <ul class="shop-list-ctg list-style-none d-flex">
            <li
              v-for="(item, i) in cateList"
              :key="i"
              class="ctg-list"
              :class="{ active: item.cateidx == $route.params.idx }"
              @click="
                $router.push({
                  name: 'shop-list',
                  params: { idx: item.cateidx },
                })
              "
            >
              <span class="list-default">{{ item.catename }}</span>
            </li>
          </ul>
        </div>
        <section class="shop-list-container">
          <!-- 리스트 헤더 -->
          <header class="shop-list__header">
            <div class="header__top">
              <div>
                <p class="dp-p text-black font-weight-500 mb-0">
                  총 {{ pagingData.listTotal }}개
                </p>
              </div>
              <div class="header-right">
                <ul class="list-filter-box list-style-none d-flex">
                  <li
                    v-for="(item, index) in sortList"
                    :key="index"
                    class="dp-p"
                    :class="{ active: item.id === sortActive }"
                    @click="setSortActive(item.id)"
                  >
                    {{ item.label }}
                    <span class="dp-bar"></span>
                  </li>
                </ul>
              </div>
            </div>

            <!-- 필터 -->
            <div class="header__bottom" style="margin-bottom:10px">
              <ul class="header-filter__ul list-style-none no-scrollbar">
                <li v-for="(items, index) in setFilterList" :key="index">
                  <span class="dp-filter">
                    {{ items.value }}
                    <i class="dp-icon icon-close" @click="delFilter(items)"></i>
                  </span>
                </li>
              </ul>

              <div
                class="bottom__icon"
                v-if="setFilterList.length > 0"
                @click="getFilterInit()"
              >
                <i class="dp-icon icon-reset md"></i>
              </div>
            </div>
          </header>
          <!-- // 리스트 헤더 -->

          <!-- 리스트 바디 -->
          <div class="shop-list__body">
            <div class="container d-flex">
              <aside class="aside-filter new-aside-filter">
                <!-- 브랜드 -->
                <div class="filter__box" v-if="fBrandList.length > 1">
                  <p class="filter__title">브랜드</p>
                  <ul
                    class="list-style-none filter__ul brand__ul"
                  >
                    <template v-for="(list, index) in fBrandList">
                      <li :key="index" v-show="list.show">
                        <div :for="list.idx" class="filter__label mb-0">
                          <div class="base-checkbox">
                            <input
                              class="magic-checkbox"
                              type="checkbox"
                              :id="list.idx"
                              name="brand"
                              v-model="setBrandList"
                              :value="list.idx"
                              hidden
                              @change="setFilter('setBrandList', list.idx)"
                            />
                            <label :for="list.idx">
                              {{ $util.isNull(list.krname)?list.name:list.krname }}
                            </label>
                          </div>
                        </div>
                      </li>
                    </template>
                  </ul>
                  <b-button
                    class="dp-btn dp-btn-icon not-hover btn-h40 dp-mt-30"
                    variant="outline-gray-400"
                    squared
                    @click="addBrandList()"
                    v-if="fBrandList.length > brandShowMax"
                  >
                    <span class="text-gray-700">더 보기</span>
                    <i class="dp-icon icon-plus black"></i>
                  </b-button>
                </div>

                <!-- 혜택 -->
                <div class="filter__box" v-if="false">
                  <p class="filter__title">혜택</p>
                  <ul class="filter__ul benefit__ul list-style-none">
                    <li v-for="(list, index) in benefitList" :key="index">
                      <label :for="list.id" class="filter__label mb-0">
                        <div class="base-checkbox">
                          <input
                            class="magic-checkbox"
                            type="checkbox"
                            :id="list.id"
                            name="benefit"
                            v-model="setBenefitList"
                            :value="list.id"
                            hidden
                            @change="setFilter('setBenefitList', list.id)"
                          />
                          <label :for="list.id">
                            {{ list.name }}
                          </label>
                        </div>
                      </label>
                    </li>
                  </ul>
                </div>
                <!-- 추천 -->
                <div class="filter__box" v-if="recomList.length > 0">
                  <p class="filter__title">추천</p>
                  <ul class="filter__ul list-style-none border_radius_ul">
                    <li v-for="(list, index) in recomList" :key="index">
                      <label :for="list.id" class="filter__label mb-0">
                        <div class="base-checkbox">
                          <input class="magic-checkbox"
                          :id="list.id"
                          type="checkbox"
                          name="recom"
                          v-model="setRecomList"
                          :value="list.id"
                          hidden
                          @change="setFilter('setRecomList', list.id)"
                          />
                          <label :for="list.id" class="atten-new">{{ list.name }}</label>
                        </div>
                      </label>
                    </li>
                  </ul>
                </div>

                <!-- 상품유형 -->
                <div class="filter__box" v-if="giconList.length > 0">
                  <p class="filter__title">상품유형</p>
                  <ul class="filter__ul list-style-none border_radius_ul">
                    <li v-for="(list, index) in giconList" :key="index">
                      <label :for="list.id" class="filter__label mb-0">
                        <div class="base-checkbox">
                          <input class="magic-checkbox"
                            :id="list.id"
                            type="checkbox"
                            name="gicon"
                            v-model="setGiconList"
                            :value="list.id"
                            hidden
                            @change="setFilter('setGiconList', list.id)"
                          />
                          <label :for="list.id" class="atten-new">
                            {{ list.name }}
                          </label>
                        </div>
                      </label>
                    </li>
                  </ul>
                </div>
                <!-- 가격 -->
                <div class="filter__box" v-if="initMaxPrice !== initMinPrice">
                  <p class="filter__title">가격</p>
                  <div>
                    <p
                      class="
                        atten-new
                        font-weight-500
                        text-center
                        price_range
                      "
                    >
                      {{ $util.maskComma(setPriceValue.currentValue[0]) }} ~
                      <span v-if="setPriceValue.currentValue[1] < 40000000">{{ $util.maskComma(setPriceValue.currentValue[1]) }}</span>
                    </p>
                    <div class="filter__range-slider dp-mb-50">
                      <vue-slider
                        ref="slider"
                        style="cursor: pointer;"
                        v-model="setPriceValue.currentValue"
                        :enable-cross="true"
                        :tooltip="'none'"
                        :min="setPriceValue.min"
                        :max="setPriceValue.max"
                        :interval="setPriceValue.interval"
                        @drag-end="updateValue(setPriceValue.currentValue)"
                        :dragOnClick="true"
                        :dot-size="30"
                        :adsorb="true"
                      >
                        <template v-slot:dot>
                          <span class="filter__dot">
                            <span class="dot-box atten-new"></span>
                          </span>
                        </template>
                      </vue-slider>
                    </div>
                    <ul class="filter__ul type02 list-style-none price__ul">
                      <li v-for="(list, index) in priceList" :key="index">
                        <label :for="list.id" class="filter__label mb-0" v-if="priceList.length > 1">
                          <div class="base-checkbox">
                            <input
                              class="magic-checkbox"
                              type="radio"
                              :id="list.id"
                              name="price"
                              v-model="setPriceList"
                              :value="list.id"
                              @change="setPrice(list)"
                              hidden
                            />
                            <label :for="list.id">
                              <span class="label__item text-gray-700 atten-new">
                                <span v-if="list.startPrice >= 3000000"> {{ list.startPrice / 10000 }} 만원 초과</span><span v-if="list.endPrice < 3000000">{{ list.endPrice / 10000 }} 만원 이하</span>
                              </span>
                            </label>
                          </div>
                        </label>
                      </li>
                    </ul>
                  </div>
                </div>

                <!-- 평점 -->
                <div class="filter__box" v-if="false">
                  <p class="filter__title">평점</p>
                  <ul class="filter__ul type02 list-style-none rating__ul">
                    <li v-for="(list, index) in ratingList" :key="index">
                      <div class="base-checkbox">
                        <input
                          class="magic-checkbox"
                          type="checkbox"
                          :id="'rating' + list.rating"
                          name="rating"
                          v-model="setRatingList"
                          :value="list.rating"
                          hidden
                          @change="setFilter('setRatingList', list.rating)"
                        />
                        <label :for="'rating' + list.rating">
                          <span class="label__item dp-icon md">
                            <template v-for="(data, index) in list.rating">
                              <img
                                :key="index"
                                src="@assets.admin/common/icon/icon-star-black-22px.svg"
                                :alt="`${data} 평점`"
                              />
                            </template>
                            <template v-for="(data, index) in 5 - list.rating">
                              <img
                                :key="index + 5"
                                src="@assets.admin/common/icon/icon-star-gray-26px.svg"
                                :alt="`${data} 평점`"
                              />
                            </template>
                          </span>
                        </label>
                      </div>
                    </li>
                  </ul>
                </div>
                <b-button v-if="fBrandList.length > 1 || benefitList.length > 1 || initMaxPrice !== initMinPrice  || ratingList.length > 1 || giconList.length > 1 || recomList.length > 1"
                  class="dp-btn dp-btn-icon not-hover dp-mt-40"
                  variant="outline-gray-800"
                  squared
                  @click="getFilterInit()"
                >
                  <span>초기화</span>
                  <i class="dp-icon icon-reset md"></i>
                </b-button>
              </aside>

              <div class="product-list">
                <ul class="row" v-if="goodsList.length > 0">
                  <li
                    class="col-4"
                    v-for="(item, index) in goodsList"
                    :key="index"
                  >
                    <product :product-info="item" :height="300" :isBedge="true"/>
                  </li>
                </ul>
                <div v-if="goodsList.length<= 0 && isEnd" class="row list__empty-items">
                  <div class="list__icon">
                    <i class="empty-list-icon"></i>
                  </div>
                  <p class="empty-list__text">
                    검색조건에 맞는 상품이 없습니다.<br />
                    필터를 초기화 해주세요.
                  </p>
                </div>

                <div
                  class="d-flex justify-content-center"
                >
                  <base-pagination
                    class="justify-content-center dp-mt-10"
                    :currentPage="pagingData.currentPage"
                    :listTotal="pagingData.listTotal"
                    :listCnt="pagingData.listCnt"
                    @changePage="changePage"
                  />
                </div>
              </div>
            </div>
          </div>
        </section>
      </div>
    </div>
  </div>
</template>
<script src="@views.pc/shop/List.js"></script>

<style scoped>
  .shop-list-container {display: flex; flex-direction: column;}
  .shop-list .shop-list-container .header-filter__ul {margin-bottom: 0;}
</style>