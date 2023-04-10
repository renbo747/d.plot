<template>
  <div class="container">
    <div class="container-inner">
      <h2 class="atten-new font-weight-800 dp-mt-60">브랜드</h2>
      <main class="dp-magazine-brand-pc">
        <div class="tab-content__header">
          <div class="container">
            <div class="letter-filter-list" v-if="brandData.length > 0">
              <!-- 전체선택버튼 -->
              <div class="letter__all__check">
                <label :for="allCheckData.id" class="all__label mb-0">
                  <input
                    :id="allCheckData.id"
                    v-model="selectBrand"
                    type="radio"
                    name="category"
                    value="ALL"
                    hidden
                  />
                  <span class="all__label__item" @click="handleAllCheck('ALL')">{{ allCheckData.label }}</span>
                </label>
              </div>
              <!-- 글자선택버튼 -->
              <div class="letter__check">
                <!-- 알파벳 -->
                <div class="letter__check__alphabet">
                  <ul class="list-style-none filter__ul" v-if="alphabetData.length > 0" :class="alphaExistETC ? 'alphabet__ul' :'letter__ul'" :style="alphaExistETC ? {} : [{ 'margin-bottom': '10px', 'margin-right': '10px'}]">
                    <li
                      v-for="(list, index) in alphabetData"
                      :key="index"
                      @click="handleAllCheck(list.label)"
                    >
                      <label :for="list.id" class="letter__label mb-0">
                        <input
                          :id="list.id"
                          v-model="selectBrand"
                          type="radio"
                          name="category"
                          :value="list.label"
                          hidden
                        />
                        <span class="label__item">{{ list.label }}</span>
                      </label>
                    </li>
                  </ul>
                </div>
                <!-- 한글 -->
                <div class="letter__check__ko">
                  <ul class="list-style-none filter__ul" :class="letterExistETC ? 'alphabet__ul' :'letter__ul'">
                    <li
                      v-for="(list, index) in letterData"
                      :key="index"
                      @click="handleAllCheck(list.label)"
                    >
                      <label :for="list.id" class="letter__label mb-0">
                        <input
                          :id="list.id"
                          v-model="selectBrand"
                          type="radio"
                          name="category"
                          :value="list.label"
                          hidden
                        />
                        <span class="label__item">{{ list.label }}</span>
                      </label>
                    </li>
                  </ul>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="magazine-brand__tabs dplot_v2">
          <b-tabs v-model="currentTab">
            <!-- Tabs 반복문 -->
            <template v-for="(tab, index) in tabTitle">
              <b-tab :title="tab.value" :key="index" >
               
                <div class="shop-list">
                  <ul data-v-ca81474a="" class="shop-list-ctg list-style-none d-flex">
                    <li v-for="(tab, index) in tabTitle" :class="currentTab === index ? 'ctg-list active' : 'ctg-list'" :key="index" @click="changeCategory(index)"><span class="list-default">{{tab.value}}</span></li>
                  </ul>
                </div>

                <div class="container" v-if="tab.idx === selectCateIdx">
                  <div class="brand__tab-content__wrap">
                    <div class="tab-content__body" v-if="brandData.length > 0">
                      <ul class="list-style-none row">
                        <template v-for="(list, index) in brandData">
                        <li
                          v-show="(selectCateIdx == 0 || list.cateidx == selectCateIdx) && (selectBrand === 'ALL' || list.filterfrstname === selectBrand || list.filterfrstename === selectBrand)"
                          :key="index"
                          class="col-3"
                        >
                        <router-link :to="{name:'magazine-brand-detail' ,params:{bid:list.idx, init:true}}">
                          <div class="brand-wrap">
                            <div class="brand-content">
                                <div
                                  class="img__wrap"
                                  :style="{
                                    backgroundImage: 'url('+list.logopath+')',
                                  }"
                                ></div>
                              <div class="product-name__wrap">
                                <p class="product-name-ko">{{list.name}}</p>
                                <p
                                  class="product-name-eng mb-0 text-gray-700 text-center"
                                >
                                  {{ list.headcopy }}
                                </p>
                              </div>
                            </div>
                          </div>
                        </router-link>
                        </li>
                        </template>
                      </ul>
                    </div>
                    <div v-else class="brand__empty">
                      <div class="brand__icon">
                        <i class="brand-list-icon"></i>
                      </div>
                      <p class="empty-brand__text">브랜드 상품이 없어요</p>
                    </div>
                  </div>
                </div>
              </b-tab>
            </template>
          </b-tabs>
        </div>
      </main>
    </div>
  </div>
</template>

<script src="@views.mobile/magazine/Brand.js"></script>
