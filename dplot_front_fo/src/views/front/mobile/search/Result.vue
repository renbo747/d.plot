<template>
  <main class="dp-search-result">
    <div class="container">
      <!-- 상품 검색결과 -->
      <div class="product-search-result">
        <div class="header__top">
          <ul
            class="
              header-filter__ul
              list-style-none
              no-scrollbar
              justify-margin
            "
          >
            <li
              v-for="(list, index) in relatedList"
              :key="index"
              @click="setSearch(list.keyword)"
            >
              <span class="dp-filter"> {{ list.keyword }} </span>
            </li>
          </ul>
        </div>
        <b-tabs class="justify-margin" v-model="activeTap">
          <!-- 상품탭 -->
          <b-tab  :active="$route.name == 'goods'"  @click="tapConverter('goods')">
            <template #title>
              <p class="tab-title">
                상품<span class="atten-new">({{ goodsLen }})</span>
              </p>
            </template>
            <section v-if="goodsList.length > 0" class="product__section">
              <header class="shop-list__header">
                <div class="header__top">
                  <div>
                    <p class="dp-p-sm text-black font-weight-500 mb-0">
                      총 {{ goodsLen }}개
                    </p>
                  </div>
                  <div class="header-right d-flex">
                    <div class="list-icon-box">
                      <i class="dp-icon icon-sort"></i>
                    </div>
                    <div class="list-select-box">
                      <base-select
                        class="select-list"
                        v-model="sortActive"
                        @change="sortSelect"
                        :options="sortList"
                      />
                    </div>
                    <div class="list-filter-box">
                      <i
                        v-if="isFilter"
                        class="dp-icon icon-filter"
                        @click="openFilterModal()"
                      ></i>
                    </div>
                  </div>
                </div>

                <!-- 필터-->
                <div class="header__bottom">
                  <ul class="header-filter__ul list-style-none no-scrollbar">
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
                    <i class="dp-icon icon-reset" @click="getFilterInit()"></i>
                  </div>
                </div>
              </header>

              <div class="shop-list__body">
                <div class="container">
                  <ul class="row product-list horizontal">
                    <li
                      class="col-12"
                      v-for="(item, index) in goodsList"
                      :key="index"
                    >
                      <product
                        :product-info="item"
                        :is-horizontal="true"
                        isBedge="true"
                        :has-like="true"
                      />
                    </li>
                  </ul>
                </div>
              </div>
            </section>
            <infinite-loading :identifier="infiniteId" @infinite="getGoodsList" spinner="circles">
             <div slot="no-more"></div>
             <div slot="no-results">
              <section>
                <div class="d-flex align-items-center flex-column dp-mt-80">
                  <i class="dp-icon xl02 dp-mb-20">
                    <img
                      src="@assets.admin/common/icon/icon-exclamation-mark-64px.svg"
                      alt="검색결과 없음"
                    />
                  </i>
                  <p class="no-search__text" v-if="setFilterList.length == 0">
                    <span class="search-word">{{ $route.query.content }}</span
                    >의 검색결과가 없습니다. <br />
                  </p>
                  <template v-else>
                    <p class="no-search__text">
                      검색 조건에 맞는 상품이 없습니다. <br />필터를 초기화 해주세요.
                    </p>
                    <b-button
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
               </section>
             </div>
            </infinite-loading>
            <!-- 검색결과 없을 때 -->
          </b-tab>
          <!-- // 상품탭 -->

          <!-- 브랜드탭 -->
          <b-tab :active="$route.name == 'brand'"  @click="tapConverter('brand')">
            <template #title>
              <p class="tab-title">
                브랜드<span class="atten-new">({{ brandLen }})</span>
              </p>
            </template>
            <section v-if="brandList.length > 0" class="brand__section">
              <div class="tab__area">
                <ul class="list-style-none tab-content-list__ul">
                  <li
                    v-for="(list, index) in brandList"
                    :key="index"
                    @click="$router.push('/magazine/brand/detail/' + list.idx)"
                  >
                    <div>
                      <div class="d-flex">
                        <div class="img__wrap">
                          <figure class="img-figure">
                            <img :src="list.fullpath" />
                          </figure>
                        </div>
                        <div class="content__li__text__area">
                          <div class="badge__wrap">
                            <div class="dp-badge sm">BRAND</div>
                          </div>
                          <div class="brand__wrap">
                            <p class="mb-0 atten-new dp-caption text-uppercase">
                              {{ list.name }}
                            </p>
                          </div>
                          <div class="product-name__wrap">
                            <p class="mb-0 dp-p-sm text-gray-700">
                              {{ list.headcopy }}
                            </p>
                          </div>
                        </div>
                      </div>
                    </div>
                  </li>
                </ul>
              </div>
            </section>
            <!-- 검색결과 없을 때 -->
            <infinite-loading
              :identifier="infiniteId"
              @infinite="getBrandList"
              spinner="circles"
            >
              <div slot="no-more"></div>
              <div slot="no-results">
                <section>
                  <div class="d-flex align-items-center flex-column dp-mt-80">
                    <i class="dp-icon xl02 dp-mb-20">
                      <img
                        src="@assets.admin/common/icon/icon-exclamation-mark-64px.svg"
                        alt="검색결과 없음"
                      />
                    </i>
                    <p class="no-search__text">
                      <span class="search-word">{{ $route.query.content }}</span
                      >의 검색결과가 없습니다. <br />다른 검색어를 입력해주세요.
                    </p>
                    <!--필터는 상품에만-->
                    <!-- <b-button
                      class="dp-btn dp-btn-icon not-hover reset-btn"
                      variant="outline-gray-800"
                      v-if="false"
                      squared
                    >
                      <span>초기화</span>
                      <i
                        class="dp-icon icon-reset black"
                        @click="filterClear()"
                      ></i>
                    </b-button> -->
                  </div>
                </section>
              </div>
            </infinite-loading>
          </b-tab>
          <!-- // 브랜드탭 -->

          <!-- 컨텐츠탭 -->
          <!-- <b-tab :active="$route.name == 'contents'"  @click="tapConverter('contents')">
            <template #title>
              <p class="tab-title">
                컨텐츠<span class="atten-new">({{ contentLen }})</span>
              </p>
            </template>
            <section v-if="contentList.length > 0" class="contents__section">
              <div class="tab__area">
                <ul class="list-style-none tab-content-list__ul">
                  <li
                    v-for="(list, index) in contentList"
                    :key="index"
                    @click="
                      $router.push('/magazine/trend/detail/' + list.tridx)
                    "
                  >
                    <div>
                      <div class="d-flex">
                        <div class="img__wrap">
                          <figure class="img-figure">
                            <img :src="list.fullpath" />
                          </figure>
                        </div>
                        <div class="content__li__text__area">
                          <div class="badge__wrap">
                            <div class="dp-badge sm secondary">MAGAZINE</div>
                          </div>
                          <div class="brand__wrap">
                            <p class="mb-0 dp-p-sm">
                              {{ list.catename }}
                            </p>
                          </div>
                          <div class="product-name__wrap">
                            <p class="mb-0 dp-p-sm text-gray-700">
                              {{ list.subject }}
                            </p>
                          </div>
                        </div>
                      </div>
                    </div>
                  </li>
                </ul>
              </div>
            </section>
            <infinite-loading
              :identifier="infiniteId"
              @infinite="getContentsList"
              spinner="circles"
            >
              <div slot="no-more"></div>
              <div slot="no-results">
                <section>
                  <div class="d-flex align-items-center flex-column dp-mt-80">
                    <i class="dp-icon xl02 dp-mb-20">
                      <img
                        src="@assets.admin/common/icon/icon-exclamation-mark-64px.svg"
                        alt="검색결과 없음"
                      />
                    </i>
                    <p class="no-search__text">
                      <span class="search-word">{{ $route.query.content }}</span
                      >의 검색결과가 없습니다. <br />
                      다른 검색어를 입력해주세요.
                    </p>
                  </div>
                </section>
              </div>
            </infinite-loading>
          </b-tab> -->
          <!-- // 컨텐츠탭 -->
        </b-tabs>
      </div>
      <!-- // 상품 검색결과 -->
    </div>
    <!-- // 상품 필터 Modal -->
  </main>
</template>

<script src="./Result.js">
</script>
