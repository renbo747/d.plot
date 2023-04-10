<template>
  <main class="my-page-dashboard">
    <div class="container">
      <template v-if="true">
        <!-- grade section // 회원등급 없을 때 -->
        <section class="dashboard-grade justify-margin">
          <div class="dashboard-assets__header">
            <p class="assets-title">
              안녕하세요
              <b @click="$router.push('/mypage/info-confirm')">{{
                memberInfo.name
              }}</b
              >님
            </p>
            <span
              class="dp-badge square primary"
              v-if="memberInfo.membertype == 'DMT002'"
              >평생회원</span
            >
          </div>
        </section>
      </template>
      <template v-else>
        <!-- grade section -->
        <section class="dashboard-grade justify-margin">
          <div class="dashboard-grade-section">
            <div class="dashboard-grade-section__top mb-20">
              <div
                class="
                  grade-section__top__items
                  d-flex
                  align-items-center
                  justify-content-between
                "
              >
                <div class="d-flex align-items-center">
                  <div class="dashboard-grade__circle">
                    <img
                      :src="`${require('@/assets/mobile/img/mypage/grade/' +
                        gradeData[userIndex].src)}`"
                    />
                  </div>
                  <div class="dashboard-grade__text">
                    <h2
                      class="
                        mb-0
                        atten-new
                        dp-p-sm
                        text-gray-700
                        font-weight-600
                      "
                    >
                      {{ memberInfo.memlvtypenm }}
                    </h2>
                    <router-link to="/">
                      <p class="dp-p font-weight-500 text-black mb-0">
                        {{ memberInfo.name }}님
                      </p>
                    </router-link>
                  </div>
                </div>
                <div class="dashboard-grade-btn-wrap">
                  <router-link to="/mypage/grade">
                    <b-button
                      class="dashboard-grade-btn dp-btn btn-h30 not-hover"
                      variant="outline-gray-800 type02"
                      squared
                      pill
                    >
                      <span class="grade-btn__caption dp-caption text-black"
                        >회원등급안내</span
                      >
                      <i
                        class="grade-btn__icon dp-icon icon-arrow-right sm"
                      ></i>
                    </b-button>
                  </router-link>
                </div>
              </div>
            </div>
          </div>
          <div>
            <div>
              <div class="dashboard-grade-progress">
                <base-progress :is-mark="false" :percent="getAmountPercent" />
              </div>
              <div class="dashboard-grade-amount">
                <p class="dp-p-sm text-gray-700 font-weight-400 mb-0">
                  100,000원 및 3건 더 구매 시 다음달
                  <span class="font-weight-500">{{
                    gradeData[nextIndex].label
                  }}</span>
                  등급
                </p>
              </div>
            </div>
          </div>
        </section>
      </template>
      <!-- point/coupon ect section -->
      <section class="dashboard-assets-wrap dp-panel">
        <div class="dashboard-assets d-flex">
          <!-- e-포인트 -->
          <!-- <div class="dashboard-assets__item d-flex flex-column">
            <router-link to="/mypage/epoint">
              <div class="dashboard-assets__item__title">
                <p
                  class="
                    assets__item__title__p
                    mb-0
                    text-gray-700
                    font-weight-400
                  "
                >
                  D포인트
                </p>
              </div>
              <div class="dashboard-assets__item__text">
                <p class="assets__item__text__p mb-0">
                  <span
                    class="mb-0 atten-new text-black font-weight-600 dp-p"
                    >{{ $util.maskComma(memberInfo.epoint) }}</span
                  >P
                </p>
              </div>
            </router-link>
          </div>
          <div class="dp-bar dashboard-assets-bar"></div> -->
          <!-- 쿠폰 -->
          <div class="dashboard-assets__item d-flex flex-column">
            <router-link to="/mypage/coupon">
              <div class="dashboard-assets__item__title">
                <p
                  class="
                    assets__item__title__p
                    mb-0
                    text-gray-700
                    font-weight-400
                  "
                >
                  쿠폰
                </p>
              </div>
              <div class="dashboard-assets__item__text">
                <p class="assets__item__text__p mb-0 text-black">
                  <span class="atten-new font-weight-600 dp-p">{{
                    memberInfo.couponcnt
                  }}</span
                  >장
                </p>
              </div>
            </router-link>
          </div>
          <div class="dp-bar dashboard-assets-bar"></div>
          <!-- 적립금 -->
          <div class="dashboard-assets__item d-flex flex-column">
            <router-link to="/mypage/reward">
              <div class="dashboard-assets__item__title">
                <p
                  class="
                    assets__item__title__p
                    mb-0
                    text-gray-700
                    font-weight-400
                  "
                >
                  적립금
                </p>
              </div>
              <div class="dashboard-assets__item__text">
                <p class="assets__item__text__p mb-0 text-black">
                  <span class="atten-new font-weight-600 dp-p">{{
                    $util.maskComma(memberInfo.respoint)
                  }}</span
                  >원
                </p>
              </div>
            </router-link>
          </div>
        </div>
        <!-- 임직원 적립금 -->
        <div
          class="dashboard-assets-executives dp-panel pb-0"
          v-if="
            memberInfo.membertype != 'DMT001' &&
            memberInfo.membertype != 'DMT002'
          "
        >
          <router-link to="/mypage/employee-reward">
            <div class="assets-executives__wrap d-flex">
              <div class="assets-executives-title">
                <p
                  class="
                    assets-executives-title__p
                    text-gray-700
                    font-weight-400
                    mb-0
                  "
                >
                  임직원 적립금
                </p>
              </div>
              <div class="assets-executives-amount">
                <p
                  class="
                    assets-executives-amount__p
                    text-black
                    font-weight-400
                    mb-0
                  "
                >
                  <span
                    class="
                      assets-executives-amount__num
                      atten-new
                      font-weight-600
                      text-black
                    "
                    >{{ $util.maskComma(memberInfo.emppoint) }}</span
                  >원
                </p>
              </div>
            </div>
          </router-link>
        </div>
      </section>
      <hr class="dp-hr justify-margin" />
      <!-- order/delivery ect section -->
      <section class="dashboard-grade-check dp-panel">
        <header class="dashboard-grade-check-header">
          <div class="d-flex align-items-center justify-content-between">
            <div class="check-header__title">
              <h2
                class="check-header__title__h2 dp-p text-black font-weight-500"
              >
                주문/배송 조회
                <span class="dp-caption text-gray-600">(최근 1개월)</span>
              </h2>
            </div>
            <div class="more-btn-wrap">
              <router-link :to="{name:'mypage-order',params:{init:true}}">
                <div class="check-header__more__btn d-flex align-items-center">
                  <span
                    class="
                      check-header__more__btn__title
                      dp-caption
                      text-gray-700
                      mr-1
                    "
                    >더보기</span
                  >
                  <i class="dp-icon sm icon-arrow-right"></i>
                </div>
              </router-link>
            </div>
          </div>
        </header>
        <div class="dashboard-grade-check-body">
          <ul class="dashboard-check-ul d-flex list-style-none">
            <li class="dashboard-check-list">
              <div class="dashboard-check-list__item">
                <div class="dashboard-check-list__item__num">
                  <p
                    class="
                      dashboard-check-list__item__num-p
                      mb-0
                      atten-new
                      font-weight-600
                    "
                    :class="{ active: orderCntInfo.waitingcnt <= 0 }"
                  >
                    {{ orderCntInfo.waitingcnt }}
                  </p>
                </div>
                <div class="dashboard-check-list__item__state">
                  <p
                    class="
                      dashboard-check-list__item__state-p
                      mb-0
                      font-weight-400
                    "
                    :class="{ active: orderCntInfo.waitingcnt <= 0 }"
                  >
                    입금대기
                  </p>
                </div>
              </div>
            </li>
            <li class="dashboard-check-list">
              <div class="dashboard-check-list__item">
                <div class="dashboard-check-list__item__num">
                  <p
                    class="
                      dashboard-check-list__item__num-p
                      mb-0
                      atten-new
                      font-weight-600
                    "
                    :class="{ active: orderCntInfo.deliing <= 0 }"
                  >
                    {{ orderCntInfo.deliing }}
                  </p>
                </div>
                <div class="dashboard-check-list__item__state">
                  <p
                    class="
                      dashboard-check-list__item__state-p
                      mb-0
                      font-weight-400
                    "
                    :class="{ active: orderCntInfo.deliing <= 0 }"
                  >
                    상품/배송<br />준비중
                  </p>
                </div>
              </div>
            </li>
            <li class="dashboard-check-list">
              <div class="dashboard-check-list__item">
                <div class="dashboard-check-list__item__num">
                  <p
                    class="
                      dashboard-check-list__item__num-p
                      mb-0
                      atten-new
                      font-weight-600
                    "
                    :class="{ active: orderCntInfo.indelivcnt <= 0 }"
                  >
                    {{ orderCntInfo.indelivcnt }}
                  </p>
                </div>
                <div class="dashboard-check-list__item__state">
                  <p
                    class="
                      dashboard-check-list__item__state-p
                      mb-0
                      font-weight-400
                    "
                    :class="{ active: orderCntInfo.indelivcnt <= 0 }"
                  >
                    배송중
                  </p>
                </div>
              </div>
            </li>
            <li class="dashboard-check-list">
              <div class="dashboard-check-list__item">
                <div class="dashboard-check-list__item__num">
                  <p
                    class="
                      dashboard-check-list__item__num-p
                      mb-0
                      atten-new
                      font-weight-600
                    "
                    :class="{ active: orderCntInfo.compdelivcnt <= 0 }"
                  >
                    {{ orderCntInfo.compdelivcnt }}
                  </p>
                </div>
                <div class="dashboard-check-list__item__state">
                  <p
                    class="
                      dashboard-check-list__item__state-p
                      mb-0
                      font-weight-400
                    "
                    :class="{ active: orderCntInfo.compdelivcnt <= 0 }"
                  >
                    배송<br />완료
                  </p>
                </div>
              </div>
            </li>
            <li class="dashboard-check-list">
              <div class="dashboard-check-list__item">
                <div class="dashboard-check-list__item__num">
                  <p
                    class="
                      dashboard-check-list__item__num-p
                      mb-0
                      atten-new
                      font-weight-600
                    "
                    :class="{ active: orderCntInfo.confirmcnt <= 0 }"
                  >
                    {{ orderCntInfo.confirmcnt }}
                  </p>
                </div>
                <div class="dashboard-check-list__item__state">
                  <p
                    class="
                      dashboard-check-list__item__state-p
                      mb-0
                      font-weight-400
                    "
                    :class="{ active: orderCntInfo.confirmcnt <= 0 }"
                  >
                    구매<br />확정
                  </p>
                </div>
              </div>
            </li>
          </ul>
          <div class="dashboard-check-state d-flex">
            <div class="dashboard-check-state-wrap d-flex">
              <router-link :to="{name: 'mypage-claim', params: {state: 'CLM001', init:true}}">
                <div class="dashboard-check-state__item d-flex">
                  <div class="dashboard-check-state__text__wrap">
                    <span class="dashboard-check-state__text text-gray-700"
                      >취소</span
                    >
                  </div>
                  <div class="dashboard-check-state__number__wrap">
                    <span
                      class="
                        dashboard-check-state__number
                        atten-new
                        font-weight-500
                      "
                      :class="{
                        'text-black': orderCntInfo.claimcnccnt <= 0,
                        'text-primary': orderCntInfo.claimcnccnt > 0,
                      }"
                    >
                      {{ orderCntInfo.claimcnccnt }}
                    </span>
                  </div>
                </div>
              </router-link>
            </div>
            <div class="dp-bar check-state-bar"></div>
            <div class="dashboard-check-state-wrap d-flex">
              <router-link :to="{name: 'mypage-claim', params: {state: 'CLM002', init:true}}">
                <div class="dashboard-check-state__item d-flex">
                  <div class="dashboard-check-state__text__wrap">
                    <span class="dashboard-check-state__text text-gray-700"
                      >반품</span
                    >
                  </div>
                  <div class="dashboard-check-state__number__wrap">
                    <span
                      class="
                        dashboard-check-state__number
                        atten-new
                        font-weight-500
                      "
                      :class="{
                        'text-black': orderCntInfo.claimrtncnt <= 0,
                        'text-primary': orderCntInfo.claimrtncnt > 0,
                      }"
                    >
                      {{ orderCntInfo.claimrtncnt }}
                    </span>
                  </div>
                </div>
              </router-link>
            </div>

            <div class="dp-bar check-state-bar"></div>
            <div class="dashboard-check-state-wrap">
              <router-link :to="{name: 'mypage-claim', params: {state: 'CLM003', init:true}}">
                <div class="dashboard-check-state__item d-flex">
                  <div class="dashboard-check-state__text__wrap">
                    <span class="dashboard-check-state__text text-gray-700"
                      >교환</span
                    >
                  </div>
                  <div class="dashboard-check-state__number__wrap">
                    <span
                      class="
                        dashboard-check-state__number
                        atten-new
                        font-weight-500
                      "
                      :class="{
                        'text-black': orderCntInfo.claimexccnt <= 0,
                        'text-primary': orderCntInfo.claimexccnt > 0,
                      }"
                    >
                      {{ orderCntInfo.claimexccnt }}
                    </span>
                  </div>
                </div>
              </router-link>
            </div>
          </div>
        </div>
      </section>
      <hr class="dp-hr justify-margin" />
      <!-- review section -->
      <section class="dashboard-review dp-panel">
        <header class="dashboard-review-header">
          <div class="d-flex align-items-center justify-content-between">
            <div class="review-header__title">
              <h2
                class="
                  review-header__title__h2
                  dp-p
                  text-black
                  font-weight-500
                  mb-0
                "
              >
                나의 리뷰
              </h2>
            </div>
            <div class="more-btn-wrap">
              <router-link :to="{name:'mypage-my-review', params:{init:true}}">
                <div class="review-header__more__btn d-flex align-items-center">
                  <span
                    class="
                      review-header__more__btn__title
                      dp-caption
                      text-gray-700
                      mr-1
                    "
                    >더보기</span
                  >
                  <i class="dp-icon sm icon-arrow-right"></i>
                </div>
              </router-link>
            </div>
          </div>
        </header>
        <div class="review-bubble-speech" v-if="reviewList.length > 0">
          <span
            >지금 리뷰 작성하면 최대
            <span class="text-primary">{{ $util.maskComma(reviewmax) }}원</span>
            적립</span
          >
        </div>
        <div class="dashboard-review-slide" v-if="reviewList.length > 0">
          <div class="image-list">
            <swiper :options="imgSwiperOption">
              <swiper-slide v-for="(list, index) in reviewList" :key="index">
                <div class="img__item" v-if="list.isdelyn != 'T'">
                  <figure @click="goToReviewDetail(list)">
                    <img :src="list.fullpath" />
                  </figure>
                </div>
              </swiper-slide>
            </swiper>
          </div>
        </div>
        <div class="dashboard-review-empty-area" v-else>
          <div class="dp-panel dashboard-review-empty__wrap">
            <div class="dashboard-review-empty">
              <div class="d-flex flex-column align-items-center">
                <div class="dashboard-review__empty__img d-flex">
                  <i class="dp-icon icon-review-empty xl02"></i>
                </div>
                <div class="dp-panel dashboard-review-text-wrap pb-0">
                  <div class="dashboard-review-text-box">
                    <p
                      class="
                        dashboard-review-text
                        mb-0
                        font-weight-400
                        text-gray-700
                        dp-p-sm
                      "
                    >
                      리뷰 작성가능한 상품이 없어요
                    </p>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>
      <hr class="dp-hr justify-margin" />
      <!-- menu section -->
      <section class="dashboard-menu dp-panel pt-0">
        <div class="dashboard-menu-wrap">
          <ul class="dashboard-menu-list justify-margin list-style-none">
            <li class="dashboard-menu-list__item">
              <router-link :to="{name:'mypage-order',params:{init:true}}">
                <div class="menu-list__item__wrap d-flex">
                  <div class="menu-list__item__left d-flex">
                    <div class="menu-list__item__icon">
                      <i class="dp-icon icon-delivery h26"></i>
                    </div>
                    <div class="menu-list__item__title">
                      <span
                        class="
                          menu-list__item__title__text
                          text-black
                          font-weight-400
                        "
                        >주문/배송조회</span
                      >
                    </div>
                  </div>
                  <div class="menu-list__item__right">
                    <i class="dp-icon icon-arrow-right"></i>
                  </div>
                </div>
              </router-link>
            </li>
            <li class="dashboard-menu-list__item">
              <router-link :to="{name: 'mypage-claim', params: {init:true}}">
                <div class="menu-list__item__wrap d-flex">
                  <div class="menu-list__item__left d-flex">
                    <div class="menu-list__item__icon">
                      <i class="dp-icon icon-return h26"></i>
                    </div>
                    <div class="menu-list__item__title">
                      <span
                        class="
                          menu-list__item__title__text
                          text-black
                          font-weight-400
                        "
                        >취소/교환/반품</span
                      >
                    </div>
                  </div>
                  <div class="menu-list__item__right">
                    <i class="dp-icon icon-arrow-right"></i>
                  </div>
                </div>
              </router-link>
            </li>
            <li class="dashboard-menu-list__item">
              <router-link :to="{name:'mypage-as-apply-list',params:{init:true}}">
                <div class="menu-list__item__wrap d-flex">
                  <div class="menu-list__item__left d-flex">
                    <div class="menu-list__item__icon">
                      <i class="dp-icon icon-notice h26"></i>
                    </div>
                    <div class="menu-list__item__title">
                      <span
                        class="
                          menu-list__item__title__text
                          text-black
                          font-weight-400
                        "
                        >AS전용 문의내역</span
                      >
                    </div>
                  </div>
                  <div class="menu-list__item__right">
                    <i class="dp-icon icon-arrow-right"></i>
                  </div>
                </div>
              </router-link>
            </li>
            <li class="dashboard-menu-list__item border-none">
              <router-link to="/mypage/previous-order">
                <div class="menu-list__item__wrap d-flex">
                  <div class="menu-list__item__left d-flex">
                    <div class="menu-list__item__icon">
                      <i class="dp-icon icon-previous-order h26"></i>
                    </div>
                    <div class="menu-list__item__title">
                      <span
                        class="
                          menu-list__item__title__text
                          text-black
                          font-weight-400
                        "
                        >이전주문내역(다다픽)</span
                      >
                    </div>
                  </div>
                  <div class="menu-list__item__right">
                    <i class="dp-icon icon-arrow-right"></i>
                  </div>
                </div>
              </router-link>
            </li>
            <hr class="dp-hr" />
            <li class="dashboard-menu-list__item">
              <router-link :to="{name:'g_like',params:{init:true}}">
                <div class="menu-list__item__wrap d-flex">
                  <div class="menu-list__item__left d-flex">
                    <div class="menu-list__item__icon">
                      <i class="dp-icon icon-like h26"></i>
                    </div>
                    <div class="menu-list__item__title">
                      <span
                        class="
                          menu-list__item__title__text
                          text-black
                          font-weight-400
                        "
                        >Like</span
                      >
                    </div>
                  </div>
                  <div class="menu-list__item__right">
                    <i class="dp-icon icon-arrow-right"></i>
                  </div>
                </div>
              </router-link>
            </li>
            <!--  최근 본 상품 링크 다시 걸기  -->
            <li class="dashboard-menu-list__item">
              <router-link :to="{name:'recent',params:{init:true}}">
                <div class="menu-list__item__wrap d-flex">
                  <div class="menu-list__item__left d-flex">
                    <div class="menu-list__item__icon">
                      <i class="dp-icon icon-clock h26"></i>
                    </div>
                    <div class="menu-list__item__title">
                      <span
                        class="
                          menu-list__item__title__text
                          text-black
                          font-weight-400
                        "
                        >최근 본 상품</span
                      >
                    </div>
                  </div>
                  <div class="menu-list__item__right">
                    <i class="dp-icon icon-arrow-right"></i>
                  </div>
                </div>
              </router-link>
            </li>
            <li class="dashboard-menu-list__item border-none">
              <router-link :to="{name:'mypage-restock', params:{init:true}}">
                <div class="menu-list__item__wrap d-flex">
                  <div class="menu-list__item__left d-flex">
                    <div class="menu-list__item__icon">
                      <i class="dp-icon icon-alarm h26"></i>
                    </div>
                    <div class="menu-list__item__title">
                      <span
                        class="
                          menu-list__item__title__text
                          text-black
                          font-weight-400
                        "
                        >재입고알림</span
                      >
                    </div>
                  </div>
                  <div class="menu-list__item__right">
                    <i class="dp-icon icon-arrow-right"></i>
                  </div>
                </div>
              </router-link>
            </li>
            <hr class="dp-hr" />
            <li class="dashboard-menu-list__item">
              <router-link to="/mypage/coupon">
                <div class="menu-list__item__wrap d-flex">
                  <div class="menu-list__item__left d-flex">
                    <div class="menu-list__item__icon">
                      <i class="dp-icon icon-coupon h26"></i>
                    </div>
                    <div class="menu-list__item__title">
                      <span
                        class="
                          menu-list__item__title__text
                          text-black
                          font-weight-400
                        "
                        >쿠폰</span
                      >
                    </div>
                  </div>
                  <div class="menu-list__item__right">
                    <i class="dp-icon icon-arrow-right"></i>
                  </div>
                </div>
              </router-link>
            </li>
            <!-- <li class="dashboard-menu-list__item">
              <router-link to="/mypage/epoint">
                <div class="menu-list__item__wrap d-flex">
                  <div class="menu-list__item__left d-flex">
                    <div class="menu-list__item__icon">
                      <i class="dp-icon icon-point h26"></i>
                    </div>
                    <div class="menu-list__item__title">
                      <span
                        class="
                          menu-list__item__title__text
                          text-black
                          font-weight-400
                        "
                        >포인트</span
                      >
                    </div>
                  </div>
                  <div class="menu-list__item__right">
                    <i class="dp-icon icon-arrow-right"></i>
                  </div>
                </div>
              </router-link>
            </li> -->
            <li class="dashboard-menu-list__item">
              <router-link to="/mypage/reward">
                <div class="menu-list__item__wrap d-flex">
                  <div class="menu-list__item__left d-flex">
                    <div class="menu-list__item__icon">
                      <i class="dp-icon icon-reward h26"></i>
                    </div>
                    <div class="menu-list__item__title">
                      <span
                        class="
                          menu-list__item__title__text
                          text-black
                          font-weight-400
                        "
                        >적립금</span
                      >
                    </div>
                  </div>
                  <div class="menu-list__item__right">
                    <i class="dp-icon icon-arrow-right"></i>
                  </div>
                </div>
              </router-link>
            </li>
            <li class="dashboard-menu-list__item" v-if="isreward">
              <router-link to="/mypage/invitation">
                <div class="menu-list__item__wrap d-flex">
                  <div class="menu-list__item__left d-flex">
                    <div class="menu-list__item__icon">
                      <i class="dp-icon icon-invite h26"></i>
                    </div>
                    <div class="menu-list__item__title">
                      <span
                        class="
                          menu-list__item__title__text
                          text-black
                          font-weight-400
                        "
                        >친구초대</span
                      >
                    </div>
                  </div>
                  <div class="menu-list__item__right">
                    <i class="dp-icon icon-arrow-right"></i>
                  </div>
                </div>
              </router-link>
            </li>
            <li class="dashboard-menu-list__item border-none">
              <router-link :to="{ name: 'inquiry', params:{init:true}}">
                <div class="menu-list__item__wrap d-flex">
                  <div class="menu-list__item__left d-flex">
                    <div class="menu-list__item__icon">
                      <i class="dp-icon icon-inquiry h26"></i>
                    </div>
                    <div class="menu-list__item__title">
                      <span
                        class="
                          menu-list__item__title__text
                          text-black
                          font-weight-400
                        "
                        >나의 문의</span
                      >
                    </div>
                  </div>
                  <div class="menu-list__item__right">
                    <i class="dp-icon icon-arrow-right"></i>
                  </div>
                </div>
              </router-link>
            </li>
            <hr class="dp-hr" />
            <li class="dashboard-menu-list__item">
              <router-link to="/mypage/message">
                <div class="menu-list__item__wrap d-flex">
                  <div class="menu-list__item__left d-flex">
                    <div class="menu-list__item__icon">
                      <i class="dp-icon icon-message h26"></i>
                    </div>
                    <div class="menu-list__item__title">
                      <span
                        class="
                          menu-list__item__title__text
                          text-black
                          font-weight-400
                        "
                        >Message</span
                      >
                    </div>
                  </div>
                  <div class="menu-list__item__right">
                    <i class="dp-icon icon-arrow-right"></i>
                  </div>
                </div>
              </router-link>
            </li>
            <li class="dashboard-menu-list__item border-none">
              <router-link
                :to="
                  $store.state.joinchtype == 'UCT001'
                    ? '/mypage/info-confirm'
                    : '/mypage/info-modify'
                "
              >
                <div class="menu-list__item__wrap d-flex">
                  <div class="menu-list__item__left d-flex">
                    <div class="menu-list__item__icon">
                      <i class="dp-icon icon-profile h26"></i>
                    </div>
                    <div class="menu-list__item__title">
                      <span
                        class="
                          menu-list__item__title__text
                          text-black
                          font-weight-400
                        "
                        >회원정보변경</span
                      >
                    </div>
                  </div>
                  <div class="menu-list__item__right">
                    <i class="dp-icon icon-arrow-right"></i>
                  </div>
                </div>
              </router-link>
            </li>
            <hr class="dp-hr" />
          </ul>
        </div>
        <div class="dashboard-menu-log-out dp-panel pb-0">
          <div class="menu-log-out__wrap">
            <router-link :to="$store.state.joinchtype == 'UCT001' ? '/mypage/info-confirm?type=withdraw' : '/mypage/member-withdraw'">
              <p class="menu-log-out__text mb-0 text-gray-700 font-weight-400">
                회원탈퇴
              </p>
            </router-link>
            <span class="dp-bar"></span>
            <a href="javascript:void(0);" @click="logout()">
              <p class="menu-log-out__text mb-0 text-gray-700 font-weight-400">
                로그아웃
              </p>
            </a>
          </div>
        </div>
      </section>
    </div>
  </main>
</template>
<script src="./Index.js"></script>