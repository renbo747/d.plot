<template>
  <main class="magazine">
    <section class="magazine__slide">
      <!-- swiper loop로 복제시 click 이벤트는 복제가 안되기때문에 click이벤트 위에다 걸어줌 -->
       <swiper class="swiper" 
        :options="swiperOptionPc" ref="mainSwiper" v-if="mainBannerList.length > 0" @click="goLink('mainSwiper')" @slide-change="handleSwiper">
        <swiper-slide v-for="(list, index) in mainBannerList" :key="index">
          <div
            class="magazine__item"
            :style="{
              backgroundImage: 'url('+ list.fullpath+ ')'}"
          ></div>
          <!-- 5/30일 퍼블에 없어져서 일단주석--> 
          <!--
          <div class="item__info">
            <h2 class="info__title" v-html="list.subject"></h2>
            <p class="info__text" v-html="list.desc"></p>
            <p class="info__sub atten-new">
              <span>Story</span>
              <img src="@assets.pc/img/icon/icon-more-white-40px.svg" />
            </p>
          </div>
          -->
        </swiper-slide>
      </swiper>

      <div class="slide__btn swiper-btn-prev">
        <img
          src="@assets.pc/img/icon/icon-arrowLeft-white-41px.svg"
          alt="prev button"
        />
      </div>
      <div class="slide__btn swiper-btn-next">
        <img
          src="@assets.pc/img/icon/icon-arrowRight-white-41px.svg"
          alt="next button"
        />
      </div>
    </section>

    <div class="content">
      <!--  매거진 섹션 메뉴바 영역  -->
      <div class="container">
        <div class="container-inner">
          <section class="magazine__menu" id="menu" ref="magazine_menu">
            <ul class="list-style-none menu__list" :class="{ fixed: isMenu }">
              <li class="menu__item">
                <span
                  class="menu__name"
                  role="button"
                  @click="$router.push({name: 'magazine-trend', query: {catename: 'ALL'}})"
                  >ALL</span
                >
              </li>
              <li class="menu__item">
                <span
                  class="menu__name"
                  role="button"
                  @click="$router.push({name: 'magazine-trend', query: {catename: 'TrenD'}})"
                  >TrenD</span
                >
              </li>
              <li class="menu__item">
                <span
                  class="menu__name"
                  role="button"
                  @click="$router.push({name: 'magazine-trend', query: {catename: 'BranD'}})"
                  >BrandD</span
                >
              </li>
              <li class="menu__item">
                <span
                  class="menu__name"
                  role="button"
                  @click="$router.push('/magazine/keyword')"
                  >Topic</span
                >
              </li>
            </ul>
          </section>
        </div>
      </div>
      <!--  // 매거진 섹션 메뉴바 영역 end -->
      <!-- 매거진 섹션 영역 -->
      <div class="container">
        <div class="container-inner">
          <template v-for="(list, index) in magazineDataList">
            <section
              class="dp-section main-section"
              :ref="list.type"
              :key="index"
            >
              <div class="main__rep">
                <magazine-card :option="list.exhibitdata" :is-route-link="false"/>
              </div>
              <div class="sub__ref">
                <ul class="main__ul list-style-none">
                  <li v-for="(list, index) in list.goodsdatalist" :key="index">
                    <magazine-product :option="list" v-on:sendlike="likeChange"/>
                  </li>
                </ul>
                <a href="javascript:void(0);"
                  @click="goLink(list.exhibitdata)"
                  class="card__link"
                >
                  <span class="link__text cursor">자세히 보기</span>
                  <i class="link__more"></i>
                </a>
              </div>
            </section>
          </template>
        </div>
      </div>
      <!--  // 매거진 섹션 영역 -->
    </div>

    <!-- // 매거진 인스타그램 영역 -->
    <section class="dp-section instagram__section">
      <div class="container" v-if="instagramData.length > 0">
        <div class="container-inner">
          <div class="instagram-header__container">
            <h2 class="instagram__title atten-new"># Instagram</h2>
            <a
              href="javascript:void(0);"
              class="card__link" @click="goInstaLink"
            >
              <span class="link__text cursor">자세히 보기</span>
              <i class="link__more"></i>
            </a>
          </div>
          <div class="instagram__slide">
            <template>
              <!-- <swiper class="swiper" :options="swiperMultiOption" ref="instaSwiper" @click="goLink('instaSwiper')"> -->
              <swiper class="swiper" :options="swiperMultiOption" ref="instaSwiper">
                <swiper-slide
                  v-for="(item, index) in instagramData"
                  :key="index"
                >
                  <instagram-card :instaOption="item" />
                </swiper-slide>
              </swiper>

              
              <div class="swiper-btn-prev" v-if="instagramData.length > 4">
                <img
                  src="@assets.pc/img/icon/icon-arrowLeft-white-41px.svg"
                  alt="prev button"
                />
              </div>
              <div class="swiper-btn-next" v-if="instagramData.length > 4">
                <img
                  src="@assets.pc/img/icon/icon-arrowRight-white-41px.svg"
                  alt="next button"
                />
              </div>
            </template>
          </div>
        </div>
      </div>
    </section>
    <!-- // 매거진 인스타그램 영역 -->
  </main>
</template>

<script src="@views.mobile/main/Index.js">
</script>
