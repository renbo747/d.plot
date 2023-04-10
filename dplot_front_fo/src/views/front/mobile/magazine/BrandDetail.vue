<template>
  
  <main class="dp-brand-detail">
    
    <section class="brand-detail__header">
      <div class="container">
        <div class="brand-detail__title">
          <!--<h1 class="mb-0">
            {{ brandInfo.enname }} <span class="title-kor">({{ brandInfo.name }})</span>
          </h1>-->
          <h1 class="mb-0">
            {{ brandInfo.enname }}
            <div class="d-flex" style="margin-left:auto; align-items:center;">
            <i
                class="dp-icon sm02 icon-heart mr-1"
                :class="{ on: brandInfo.iswished === 'T' }"
                @click="changeWish"
              ></i>
              <span class="wish-count">{{ brandInfo.wishcnt }}</span>
            <i class="dp-icon sm02 icon-share" @click="snsShareModal()" style="margin-bottom:1px"></i>
            </div>
          </h1>
          <div class="d-flex align-content-center justify-content-end dp-mt-10">
            <!-- <div class="icon-heart__area">
              <i
                  class="dp-icon sm02 icon-heart mr-1"
                  :class="{ on: brandInfo.iswished === 'T' }"s
                  @click="changeWish"
                ></i>
                <span class="wish-count">{{ brandInfo.wishcnt }}</span>
              <i class="dp-icon sm02 icon-share" @click="snsShareModal()"></i>
            </div> -->
          </div>
        </div>
      </div>
    </section>
    <!-- 브랜드 설명-->
    <section :class="brandInfo.fullpath.length  === 0 ? 'detail-contents__wrap img-none' : 'detail-contents__wra'">
      <div class="contents__box">
        <img :src="brandInfo.fullpath" style="width: 100%"/>
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
          <a @click="goLink(list)">
            <event-banner :banner-info="list" class="black" />
          </a>
        </swiper-slide>
        <div class="swiper-pagination" slot="pagination"></div>
      </swiper>
    </section>
    <!-- 브랜드 상품-->
    <section :class="brandInfo.fullpath.length  === 0 ? 'brand__product__section img-none':'brand__product__section'">
      <div class="brand__product-list__header">
        <div class="header__top">
          <div>
            <p class="mb-0 product-count__p">총 {{ brandProduct.length }}개</p>
          </div>
          <div class="header-right d-flex">
            <!-- <div class="list-icon-box">
              <i class="dp-icon icon-sort"></i>
            </div> -->
            <div class="list-select-box">
              <base-select
                class="select-list"
                v-model="selectOptionData"
                :options="listSelectOptions"
                placeholder="최신순"
                @change="handleSelect"
              />
            </div>
          </div>
        </div>
      </div>
      <div class="brand__product-area">
        <div class="container">
          <ul class="row product-list dplot_v2">
            <li
              class="col-6"
              v-for="(item, index) in brandProduct"
              :key="index"
            >
              <product :product-info="item" :isBedge="true"/>
            </li>
          </ul>
        </div>
      </div>
    </section>
  </main>
</template>

<script src="./BrandDetail.js">
</script>
