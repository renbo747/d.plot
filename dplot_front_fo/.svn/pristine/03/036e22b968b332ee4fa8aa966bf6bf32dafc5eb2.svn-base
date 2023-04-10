<template>
  <main class="dp-search-result shop-list">
    <div class="container">
      <div class="container-inner">
        <!-- 상품 검색결과 -->
        <search-relation
          v-model="searchKeyword"
          :is-mobile="false"
          placeholder="검색어를 입력해주세요"
          ref="search"
          type="search2"
        />
        <div class="product-search-result">
          <div class="header__top">
            <ul class="header-filter__ul no-scrollbar relative-search dplot_v2">
              <li
                v-for="(list, index) in relatedList"
                :key="index"
                @click="setSearch(list.keyword)"
              >
                <span class="dp-filter"> {{ list.keyword }} </span>
              </li>
            </ul>
          </div>
          <b-tabs>
            <!-- 상품탭 -->
            <b-tab
              :active="$route.name == 'goods'"
              @click="tapConverter('goods')"
            >
              <template #title>
                <p class="tab-title">
                  상품<span class="atten-new">({{ goodsLen }})</span>
                </p>
              </template>
              <section class="product__section">
                <!-- 리스트 헤더 -->
                <header class="shop-list__header" v-if="goodsList.length > 0">
                  <div class="header__top">
                    <div>
                      <p class="dp-p text-black font-weight-500 mb-0">
                        총 {{ goodsLen }}개
                      </p>
                    </div>
                    <div class="header-right">
                      <ul class="list-filter-box list-style-none d-flex">
                        <li
                          v-for="(item, index) in sortList"
                          :key="index"
                          class="dp-p"
                          :class="{ active: item.value === sortActive }"
                          @click="sortSelect(item.value)"
                        >
                          {{ item.label }}
                          <span class="dp-bar"></span>
                        </li>
                      </ul>
                    </div>
                  </div>

                  <!-- 필터 -->
                  <div class="header__bottom">
                    <ul class="header-filter__ul no-scrollbar">
                      <li v-for="(flist, index) in setFilterList" :key="index">
                        <span class="dp-filter">
                          {{ flist.value }}
                          <i
                            class="dp-icon icon-close"
                            @click="delFilter(flist)"
                          ></i>
                        </span>
                      </li>
                    </ul>

                    <div class="bottom__icon">
                      <i class="dp-icon icon-reset md" @click="getFilterInit()">
                      </i>
                    </div>
                  </div>
                </header>
                <!-- // 리스트 헤더 -->

                <!-- 리스트 바디 -->
                <div class="shop-list__body">
                  <div class="container d-flex">
                    <aside class="aside-filter" v-if="goodsList.length > 0">
                      <!-- 카테고리 임시 -->
                      <div class="filter__box" v-if="cateList.length > 0">
                        <p class="filter__title">카테고리</p>
                        <ul class="list-style-none filter__ul brand__ul">
                          <li v-for="(list, index) in cateList" :key="index">
                            <div
                              :for="'cate' + list.cateidx"
                              class="filter__label mb-0"
                            >
                              <div class="base-checkbox">
                                <input
                                  class="magic-checkbox"
                                  :id="'cate' + list.cateidx"
                                  type="checkbox"
                                  name="category"
                                  v-model="setCateList"
                                  :value="list.cateidx"
                                  @change="
                                    setFilter('setCateList', list.cateidx)
                                  "
                                  hidden
                                />
                                <label :for="'cate' + list.cateidx">
                                  {{ list.catename }}
                                </label>
                              </div>
                            </div>
                          </li>
                        </ul>
                      </div>
                      <div class="filter__box" v-if="fBrandList.length > 0">
                        <p class="filter__title">브랜드</p>
                        <ul class="list-style-none filter__ul brand__ul">
                          <template v-for="(list, index) in fBrandList">
                            <li :key="index" v-if="list.show">
                              <div
                                :for="'brand' + list.idx"
                                class="filter__label mb-0"
                              >
                                <div class="base-checkbox">
                                  <input
                                    class="magic-checkbox"
                                    type="checkbox"
                                    :id="'brand' + list.idx"
                                    :name="list.name"
                                    v-model="setBrandList"
                                    :value="list.idx"
                                    @change="
                                      setFilter('setBrandList', list.idx)
                                    "
                                    hidden
                                  />
                                  <label :for="'brand' + list.idx">
                                    {{
                                      $util.isNull(list.enname)
                                        ? list.name
                                        : list.enname
                                    }}
                                  </label>
                                </div>
                              </div>
                            </li>
                          </template>
                        </ul>
                        <b-button
                          class="dp-btn dp-btn-icon not-hover btn-h40 dp-mt-30"
                          variant="outline-gray-400"
                          v-b-modal.brandPlusModal
                          v-if="fBrandList.length >= 10"
                          @click="addBrandList()"
                          squared
                        >
                          <span class="text-gray-700">더 보기</span>
                          <i class="dp-icon icon-plus black"></i>
                        </b-button>
                      </div>

                      <!-- 컬러 -->
                      <!-- <div class="filter__box" v-if="colorList.length > 0">
                        <p class="filter__title">컬러</p>
                        <ul class="color__ul list-style-none">
                          <li v-for="(list, index) in colorList" :key="index">
                            <label
                              :for="list.colortype"
                              class="color__label mb-0"
                            >
                              <input
                                :id="list.colortype"
                                type="checkbox"
                                name="color"
                                v-model="setColorList"
                                :value="list.colortype"
                                 @change="setFilter('setColorList',list.colortype)"
                                hidden
                              />
                              <span
                                class="label__item"
                                :style="{
                                  backgroundColor: '#' + list.bg,
                                  borderColor: '#' + list.bg,
                                }"
                              ></span>
                            </label>
                          </li>
                        </ul>
                      </div> -->

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

                      <!-- 가격 -->
                      <div class="filter__box" v-if="goodsList.length > 0">
                        <p class="filter__title"  >가격(원)</p>
                        <div>
                          <p
                            class="
                              atten-new
                              font-weight-500
                              dp-p dp-mb-10
                              text-center
                            "
                          >
                            {{ $util.maskComma(setPriceValue.currentValue[0]) }} ~
                            {{$util.maskComma(setPriceValue.currentValue[1]) }}
                          </p>
                          <div class="filter__range-slider dp-mb-50">
                            <vue-slider
                              style="cursor: pointer"
                              ref="slider"
                              v-model="setPriceValue.currentValue"
                              :enable-cross="true"
                              :tooltip="'none'"
                              :min="setPriceValue.min"
                              :max="setPriceValue.max"
                              :interval="setPriceValue.interval"
                              :process="true"
                              @drag-end="updateValue(setPriceValue.currentValue)"
                              :dragOnClick="true"
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
                                      {{ $util.maskComma(list.startPrice) }} ~
                                      {{ $util.maskComma(list.endPrice) }}
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
                                @change="
                                  setFilter('setRatingList', list.rating)
                                "
                              />
                              <label :for="'rating' + list.rating">
                                <span class="label__item dp-icon md">
                                  <template
                                    v-for="(data, index) in list.rating"
                                  >
                                    <img
                                      :key="index"
                                      src="@assets.admin/common/icon/icon-star-black-22px.svg"
                                      :alt="`${data} 평점`"
                                    />
                                  </template>
                                  <template
                                    v-for="(data, index) in 5 - list.rating"
                                  >
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
                      <b-button
                        v-if="fBrandList.length > 0 || benefitList.length > 0 || goodsList.length > 0  || ratingList.length > 0"
                        class="dp-btn dp-btn-icon not-hover dp-mt-40"
                        variant="outline-gray-800"
                        @click="getFilterInit()"
                        squared
                      >
                        <span>초기화</span>
                        <i class="dp-icon icon-reset md"></i>
                      </b-button>
                    </aside>
                    <!-- Todo: (수정) HTML 구조 변경 0711 -->
                    <!-- <div class="product-list">
                      <ul class="row">
                        <li
                          class="col-4"
                          v-for="(item, index) in goodsList"
                          :key="index"
                        >
                          <product :product-info="item" />
                        </li>
                      </ul>

                      <div class="pagination-wrap">
                        <base-pagination class="justify-content-center" 
                        v-if="pagingData.listTotal >= 10"
                        :currentPage="pagingData.currentPage"
                        :listTotal="pagingData.listTotal"
                        :listCnt="pagingData.listCnt"
                        @changePage="changePage"
                      />
                      </div>
                    </div>
                  </div> -->
                    <div class="product-list"  style="width:100%;">
                      <ul class="row" v-if="isEnd&& goodsList.length > 0">
                        <li
                          class="col-4"
                          v-for="(item, index) in goodsList"
                          :key="index"
                        >
                          <product :product-info="item" isBedge="true"/>
                        </li>
                      </ul>
                      <div class="no-result" v-if="isEnd && goodsList.length <= 0" style="width:calc(100% - 60px); margin-left:3px;">
                        <i class="dp-mb-20">
                          <img
                            src="@assets.admin/common/icon/icon-exclamation-mark-128px.svg"
                            alt="검색결과 없음"
                          />
                        </i>
                        <p class="no-search__text"  v-if="setFilterList.length == 0">
                          <span class="search-word">{{ $util.maskTxt($route.query.content, 10, "...") }}</span>의
                          검색결과가 없습니다. <br />다른 검색어를 입력해주세요.
                        </p>
                        <template v-if="setFilterList.length > 0">
                          <p class="no-search__text">
                            검색 조건에 맞는 상품이 없습니다. <br />필터를 초기화
                            해주세요.
                          </p>
                          <!-- <b-button
                            v-if="true"
                            class="dp-btn dp-btn-icon not-hover reset-btn"
                            variant="outline-gray-800"
                            @click="getFilterInit()"
                            squared
                          >
                            <span>초기화</span>
                            <i class="dp-icon icon-reset black"></i>
                          </b-button> -->
                        </template>
                        <!-- <b-button
                          v-if="true"
                          class="dp-btn dp-btn-icon not-hover reset-btn"
                          variant="outline-gray-800"
                          squared
                        >
                          <span>초기화</span>
                          <i class="dp-icon icon-reset black"></i>
                        </b-button> -->
                      </div>

                      <div class="pagination-wrap">
                        <base-pagination class="justify-content-center" 
                         v-if="pagingData.listTotal >= 10"
                        :currentPage="pagingData.currentPage"
                        :listTotal="pagingData.listTotal"
                        :listCnt="pagingData.listCnt"
                        @changePage="changePage"/>
                      </div>
                    </div>
                  </div>
                </div>
                <!-- // 리스트 바디 -->
              </section>
              <!-- <section  v-if="isEnd== true && goodsList.length <= 0">
                <div class="no-result">
                  <i class="dp-mb-20">
                    <img
                      src="@assets.admin/common/icon/icon-exclamation-mark-128px.svg"
                      alt="검색결과 없음"
                    />
                  </i>
                  <p class="no-search__text" v-if="setFilterList.length == 0">
                    <span class="search-word">{{ $util.maskTxt($route.query.content, 10, "...") }}</span
                    >의 검색결과가 없습니다. <br />다른 검색어를 입력해주세요.
                  </p>
                  <template v-else>
                    <p class="no-search__text">
                      검색 조건에 맞는 상품이 없습니다. <br />필터를 초기화
                      해주세요.
                    </p>
                    <b-button
                      v-if="true"
                      class="dp-btn dp-btn-icon not-hover reset-btn"
                      variant="outline-gray-800"
                      @click="getFilterInit()"
                      squared
                    >
                      <span>초기화</span>
                      <i class="dp-icon icon-reset black"></i>
                    </b-button>
                  </template>
                </div>
              </section> -->
            </b-tab>
            <!-- // 상품탭 -->

            <!-- 브랜드탭 -->
            <b-tab
              :active="$route.name == 'brand'"
              @click="tapConverter('brand')"
            >
              <template #title>
                <p class="tab-title">
                  브랜드<span class="atten-new">({{ brandLen }})</span>
                  
                </p>
              </template>
              <section v-if="brandList.length > 0" class="brand__section">
                <div class="tab__area">
                  <ul class="list-style-none tab-content-list__ul row">
                    <li
                      v-for="(list, index) in brandList"
                      :key="index"
                      class="col-3 dp-mb-20"
                      @click="
                        $router.push('/magazine/brand/detail/' + list.idx)
                      "
                    >
                      <div class="brand-wrap">
                        <div class="brand-content">
                          <div
                            class="img__wrap"
                            :style="{
                              backgroundImage: 'url(' + list.fullpath + ')',
                            }"
                          ></div>
                           <div class="product-name__wrap">
                              <p class="mb-2 dp-title02 font-weight-400 text-gray-700 text-center">
                                {{ list.name }}
                              </p>
                              <p class="mb-0 dp-p text-gray-700 text-center">
                                {{ list.headcopy }}
                              </p>
                            </div>
                        </div>
                      </div>
                    </li>
                  </ul>
                  <div class="pagination-wrap dp-mt-20">
                    <base-pagination
                      class="justify-content-center dp-mt-10"
                      style="margin-left: 270px"
                      v-if="pagingData.listTotal >= 10"
                      :currentPage="pagingData.currentPage"
                      :listTotal="pagingData.listTotal"
                      :listCnt="pagingData.listCnt"
                      @changePage="changePage"
                    />
                  </div>
                </div>
              </section>
              <!-- 검색결과 없을 때 -->
              <section v-if="isEnd && brandList.length <= 0">
                <div class="no-result">
                  <i class="dp-mb-20">
                    <img
                      src="@assets.admin/common/icon/icon-exclamation-mark-128px.svg"
                      alt="검색결과 없음"
                    />
                  </i>
                  <p class="no-search__text">
                    <span class="search-word">{{
                      $util.maskTxt($route.query.content, 10, "...")
                    }}</span
                    >의 검색결과가 없습니다. <br />다른 검색어를 입력해주세요.
                  </p>
                </div>
              </section>
            </b-tab>
            <!-- // 브랜드탭 -->

            <!-- 컨텐츠탭 2022-07-16 yiy 추후 오픈-->
            <!-- <b-tab :active="$route.name == 'contents'"  @click="tapConverter('contents')">
              <template #title>
                <p class="tab-title">
                  컨텐츠<span class="atten-new">({{ contentLen }})</span>
                </p>
              </template>
              <section v-if="contentList.length > 0" class="contents__section">
                <div class="tab__area">
                  <ul class="list-style-none tab-content-list__ul row">
                    <li
                      v-for="(list, index) in contentList"
                      :key="index"
                      class="col-6"
                      @click="
                        $router.push('/magazine/trend/detail/' + list.tridx)
                      "
                    >
                      <div>
                        <magazine-card :option="list" :is-route-link="false" />
                      </div>
                    </li>
                  </ul>
                  <div class="d-flex justify-content-center" v-if="pagingData.listTotal >= 10">
                     <base-pagination  style="margin-left:270px"
                     class="justify-content-center dp-mt-10"
                      :currentPage="pagingData.currentPage"
                      :listTotal="pagingData.listTotal"
                      :listCnt="pagingData.listCnt"
                      @changePage="changePage"
                    />
                  </div>
                </div>
              </section>
              <section v-if="isEnd== true && contentList.length <= 0">
                <div class="no-result">
                  <i class="dp-mb-20">
                    <img
                      src="@assets.admin/common/icon/icon-exclamation-mark-128px.svg"
                      alt="검색결과 없음"
                    />
                  </i>
                  <p class="no-search__text">
                    <span class="search-word">{{ $util.maskTxt($route.query.content, 10, "...")  }}</span
                    >의 검색결과가 없습니다. <br />다른 검색어를 입력해주세요.
                  </p>
                </div>
              </section>
            </b-tab> -->
          </b-tabs>
        </div>
        <!-- // 상품 검색결과 -->
      </div>
    </div>
    <!-- // 상품 필터 Modal -->
  </main>
</template>

<script src="./Result"></script>
