<template>
  <main class="main">
    <section class="main__banner" ref="mainBanner" >
      <!-- swiper loop로 복제시 click 이벤트는 복제가 안되기때문에 click이벤트 위에다 걸어줌 -->
      <swiper :options="swiperOption" v-if="mainBannerList.length > 0"   
        ref="mainSwiper" @click="goLink('mainSwiper')"
        @slide-change="handleSwiper"
        >
        <swiper-slide v-for="(list, index) in mainBannerList" :key="index">
            <div
              class="banner__item"
              :style="{backgroundImage: 'url('+list.fullpath+')'}"
            ></div>
        </swiper-slide>
        <div class="swiper-pagination" slot="pagination"></div>
      </swiper>

      <i class="banner-icon" role="button" @click="nextSection">
        <img
          src="@assets.mobile/img/icon/icon-arrow-top-white-32px.svg"
          alt="세션 올리기"
        />
      </i>
    </section>
    <!-- // (개발) 0418 슬라이더 배경 컬러값 이벤트 바인딩 -->
    <div class="contents-page">
      <!-- 중단 메뉴 -->
      <section class="main__menu-section">
        <!-- todo: (개발) 0418 중단 메뉴 링크 연결 -->
        <ul class="main__magazine-menu no-scrollbar list-style-none">
          <li>
            <span
              class="magazine-menu__name"
              role="button"
              @click="$router.push({name: 'magazine-trend', query: {catename: 'ALL'}})"
              >ALL</span
            >
          </li>
          <li>
            <span
              class="magazine-menu__name"
              role="button"
              @click="$router.push({name: 'magazine-trend',  query: {catename: 'TrenD'}})"
              >TrenD</span
            >
          </li>
          <li>
            <span
              class="magazine-menu__name"
              role="button"
              @click="$router.push({name: 'magazine-trend',  query: {catename: 'BranD'}})"
              >BrandD</span
            >
          </li>
          <li>
            <span
              class="magazine-menu__name"
              role="button"
               @click="$router.push('/magazine/keyword')"
              >Topic</span
            >
          </li>
        </ul>
        <!-- // (개발) 0418 중단 메뉴 링크 연결 -->
      </section>
      <!-- // 중단 메뉴 -->
      <!-- 매거진 영역 -->
      <template v-for="(list, index) in magazineDataList">
        <section class="dp-section main-section" :ref="list.type" :key="index">
          <div class="main__rep">
            <magazine-card :option="list.exhibitdata" />
          </div>
          <div class="container">
            <ul class="main__ul list-style-none">
              <li v-for="(list, index) in list.goodsdatalist" :key="index">
                <magazine-product :option="list" v-on:sendlike="likeChange"/>
              </li>
            </ul>
          </div>
        </section>
      </template>
      <!-- // 매거진 영역 -->

      <!-- 인스타그램 영역 -->
      <section class="dp-section insta-section">
        <header class="insta__header" v-if="instagramData.length > 0">
          <h1 class="insta__title atten-new">#Instagram</h1>

          <a class="insta__more" @click="goInstaLink">
            <img
              src="@assets.mobile/img/icon/icon-more-black-32px.svg"
              alt="더 보기"
            />
          </a>
        </header>
        <ul class="insta__ul dp-touch-slider list-style-none" v-if="instagramData.length > 0">
          <li v-for="(item, index) in instagramData" :key="index">
            <instagram-card :instaOption="item" />
          </li>
        </ul>
      </section>
      <!-- // 인스타그램 영역 -->
    </div>
  </main>
</template>

<script src="./Index.js">
</script>
