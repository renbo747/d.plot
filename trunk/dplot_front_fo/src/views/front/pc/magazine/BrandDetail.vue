<template>
  <main class="dp-brand-detail-pc">
    <div class="container">
      <!-- 브랜드 이름-->
      <section class="brand-detail__header">
        <div class="brand-detail__title">
          <h1 class="mb-0">
            {{ brandInfo.enname }} <!-- <span class="title-kor">({{ brandInfo.name }})</span> -->
          </h1>
          <div class="brand-detail__utility">
            <div class="icon-heart__area" style="cursor: pointer">
              <i
                class="dp-icon sm02 icon-heart mr-2"
                :class="{ on: brandInfo.iswished === 'T' }"
                @click="changeWish"
              ></i>
              <span class="wish-count">{{ brandInfo.wishcnt }}</span>
            </div>
            <!-- <span class="dp-bar"></span> -->
            <i class="dp-icon sm02 icon-share" style="cursor: pointer" @click="snsShareModal()"></i>
          </div>
        </div>
      </section>
      <!-- 브랜드 설명-->
      <section class="detail-contents__wrap">
        <div class="contents__box">
          <img :src="brandInfo.fullpath" />
          <div class="container" v-if="!$util.isNull(brandInfo.detail)">
            <!-- <p class="mb-0 brand-detail__description">
              {{ brandInfo.detail }}
            </p> -->
          </div>
        </div>
      </section>
      <!-- 연관 콘텐츠 -->
      <section class="detail-contents__related-box" v-if="contentslist.length > 0">
        <div class="container">
          <div class="detail-contents__related">
            <p class="related-title"># 연관콘텐츠</p>
            <ul class="ul-dot">
              <li v-for="(item,index) in contentslist" :key="index" @click="goLink(item)">
                {{item.title}}
              </li>
            </ul>
          </div>
        </div>
      </section>
      <!-- 이벤트 배너-->
      <section class="event__banner__section" v-if="eventBanner.length > 0">
        <swiper class="swiper main-popup-swiper" :options="bannerSwiperOption">
          <swiper-slide v-for="(list, index) in eventBanner" :key="index">
            <!-- <a @click="goLink(list)"> -->
              <event-banner :banner-info="list" class="black" />
            <!-- </a> -->
          </swiper-slide>
          <div class="swiper-pagination" slot="pagination"></div>
        </swiper>
      </section>
      <!-- 브랜드 상품-->
      <section class="brand__product__section">
        <div class="brand__product-list__header">
          <div class="header__top">
            <div class="d-flex justify-content-between">
              <p class="mb-0 product-count__p">
                총 {{ brandProduct.length }}개
              </p>
              <div class="list-select-box">
                <ul class="list-style-none d-flex">
                  <li v-for="(list, index) in listSelectOptions" :key="index">
                    <div @click="handleSelect(list.value)">
                      <span :class="selectOptionData === list.value ? 'list__span__active' : 'list__span'">{{ list.label }}</span>
                      <span class="dp-bar"></span>
                    </div>
                  </li>
                </ul>
              </div>
            </div>
          </div>
        </div>
        <div class="brand__product-area">
          <div class="container">
            <ul class="list-style-none row">
              <li
                class="col-3"
                v-for="(item, index) in brandProduct"
                :key="index"
              >
                <product :product-info="item" :isBedge="true"/>
              </li>
            </ul>
          </div>
        </div>
      </section>
    </div>
  </main>
</template>

<script src="@views.mobile/magazine/BrandDetail.js">
</script>
