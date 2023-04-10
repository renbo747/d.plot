<template>
  <main class="my-page-dashboard">
    <div>
       <template v-if="true">
        <div class="dashboard-container">
          <!-- 회원등급이 없을 때 -->
          <div class="dashboard-assets__header">
            <p class="assets-title">안녕하세요 <b style="cursor: pointer;" @click="$router.push('/mypage/info-confirm')">{{memberInfo.name}}</b>님</p>
            <span class="dp-badge square primary" v-if="memberInfo.membertype == 'DMT002'">평생회원</span>
          </div>

          <!-- point/coupon ect section -->
          <section class="dashboard-assets-wrap">
            <!-- D포인트 -->
            <!-- <div class="dashboard-assets__item">
              <router-link to="/mypage/epoint">
                <div class="dashboard-assets__item__title d-flex">
                  <i class="dp-icon icon-point h26"></i>
                  <p
                    class="assets__item__title__p mb-0 text-gray-700 font-weight-400"
                  >
                    D포인트
                  </p>
                </div>
                <div class="dashboard-assets__item__text">
                  <p class="assets__item__text__p mb-0">
                    <span class="mb-0 atten-new text-black font-weight-600 assets__item__text__num">{{$util.maskComma(memberInfo.epoint)}}</span>P
                  </p>
                </div>
              </router-link>
            </div> -->
            <!-- 쿠폰 -->
            <div class="dashboard-assets__item">
              <router-link to="/mypage/coupon">
                <div class="dashboard-assets__item__title d-flex">
                  <i class="dp-icon icon-coupon h26"></i>
                  <p
                    class="assets__item__title__p mb-0 text-gray-700 font-weight-400"
                  >
                    쿠폰
                  </p>
                  <p class="assets__item__text__p mb-0 text-black">
                    <span
                      class="atten-new font-weight-600 assets__item__text__num"
                      >{{memberInfo.couponcnt}}</span
                    >장
                  </p>
                </div>
              </router-link>
            </div>
            <!-- 적립금 -->
            <div class="dashboard-assets__item">
              <router-link to="/mypage/reward">
                <div class="dashboard-assets__item__title d-flex">
                  <i class="dp-icon icon-reward h26"></i>
                  <p
                    class="assets__item__title__p mb-0 text-gray-700 font-weight-400"
                  >
                    적립금
                  </p>
                  <p class="assets__item__text__p mb-0 text-black">
                    <span
                      class="atten-new font-weight-600 assets__item__text__num"
                      > {{$util.maskComma(memberInfo.respoint)}}</span
                    >원
                  </p>
                </div>

              </router-link>
            </div>
            <!-- 임직원 적립금 -->
            <div class="dashboard-assets__item" v-if="memberInfo.membertype != 'DMT001' && memberInfo.membertype != 'DMT002'">
              <router-link to="/mypage/employee-reward">
                <div class="dashboard-assets__item__title d-flex">
                  <i class="dp-icon icon-reward h26"></i>
                  <p
                    class="assets__item__title__p mb-0 text-gray-700 font-weight-400"
                  >
                    임직원적립금
                  </p>
                  <p class="assets__item__text__p mb-0 text-black">
                    <span
                      class="atten-new font-weight-600 assets__item__text__num"
                      >{{$util.maskComma(memberInfo.emppoint)}}</span
                    >원
                  </p>
                </div>
              </router-link>
            </div>
          </section>
        </div>
      </template>
      <template v-else>
      <div class="row dashboard-container mr-0 ml-0">
        <!-- grade section -->
        <section class="dashboard-grade col-6">
          <div class="dashboard-grade-section">
            <div class="dashboard-grade-section__top mb-20">
              <div
                class="grade-section__top__items d-flex align-items-center justify-content-between"
              >
                <div class="d-flex align-items-center">
                  <div class="dashboard-grade__circle">
                    <img :src="`${require('@/assets/mobile/img/mypage/grade/' + gradeData[userIndex].src)}`"/>
                    <!--<i class="dp-icon icon-grade h74"></i>-->
                  </div>
                  <div class="dashboard-grade__text">
                    <h2
                      class="dashboard-grade__bronze mb-0 atten-new text-gray-700 font-weight-600"
                    >
                       {{memberInfo.memlvtypenm}}
                    </h2>
                    <router-link to="/">
                      <p
                        class="dashboard-grade__name font-weight-500 text-black mb-0"
                      >
                        {{memberInfo.name}}님
                      </p>
                    </router-link>
                  </div>
                </div>
                <div class="dashboard-grade-btn-wrap">
                  <router-link to="/mypage/grade">
                    <b-button
                      class="dashboard-grade-btn dp-btn btn-h36 not-hover"
                      variant="outline-gray-800 type02"
                      squared
                      pill
                    >
                      <span class="grade-btn__caption text-black"
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
                <p
                  class="dashboard-grade-amount__p text-gray-700 font-weight-400 mb-0"
                >
                  100,000원 및 3건 더 구매 시 다음달
                  <span class="font-weight-500">{{gradeData[nextIndex].label}}</span> 등급
                </p>
              </div>
            </div>
          </div>
        </section>
        <!-- point/coupon ect section -->
        <section class="dashboard-assets-wrap col-6 pr-0 pl-0">
          <div class="dashboard-assets d-flex">
            <!-- D포인트 -->
            <div class="dashboard-assets__item d-flex flex-column">
              <router-link to="/mypage/epoint">
                <div class="dashboard-assets__item__title d-flex">
                  <i class="dp-icon icon-point h26"></i>
                  <p
                    class="assets__item__title__p mb-0 text-gray-700 font-weight-400"
                  >
                    D포인트
                  </p>
                </div>
                <div class="dashboard-assets__item__text">
                  <p class="assets__item__text__p mb-0">
                    <span class="mb-0 atten-new text-black font-weight-600 assets__item__text__num">
                      {{$util.maskComma(memberInfo.epoint)}}
                    </span>P
                  </p>
                </div>
              </router-link>
            </div>
            <!-- 쿠폰 -->
            <div class="dashboard-assets__item d-flex flex-column">
              <router-link to="/mypage/coupon">
                <div class="dashboard-assets__item__title d-flex">
                  <i class="dp-icon icon-coupon h26"></i>
                  <p
                    class="assets__item__title__p mb-0 text-gray-700 font-weight-400"
                  >
                    쿠폰
                  </p>
                </div>
                <div class="dashboard-assets__item__text">
                  <p class="assets__item__text__p mb-0 text-black">
                    <span
                      class="atten-new font-weight-600 assets__item__text__num"
                      >{{memberInfo.couponcnt}}</span
                    >장
                  </p>
                </div>
              </router-link>
            </div>
            <!-- 적립금 -->
            <div
              class="dashboard-assets__item dashboard-assets__item-last d-flex flex-column"
            >
              <router-link to="/mypage/reward">
                <div class="dashboard-assets__item__title d-flex">
                  <i class="dp-icon icon-reward h26"></i>
                  <p
                    class="assets__item__title__p mb-0 text-gray-700 font-weight-400"
                  >
                    적립금
                  </p>
                </div>
                <div class="dashboard-assets__item__text">
                  <p class="assets__item__text__p mb-0 text-black">
                    <span class="atten-new font-weight-600 assets__item__text__num">
                      {{$util.maskComma(memberInfo.respoint)}}
                    </span>원
                  </p>
                </div>
              </router-link>
            </div>
          </div>
          <!-- 임직원 적립금 -->
          <div class="dashboard-assets-executives dp-panel pb-0" v-if="memberInfo.membertype != 'DMT001' && memberInfo.membertype != 'DMT002'">
            <router-link to="/mypage/employee-reward">
              <div class="assets-executives__wrap d-flex">
                <div class="assets-executives-title">
                  <p
                    class="assets-executives-title__p text-gray-700 font-weight-400 mb-0"
                  >
                    임직원 적립금
                  </p>
                </div>
                <div class="assets-executives-amount">
                  <p
                    class="assets-executives-amount__p text-black font-weight-400 mb-0"
                  >
                    <span
                      class="assets-executives-amount__num atten-new font-weight-600 text-black"
                      >{{$util.maskComma(memberInfo.emppoint)}}</span
                    >원
                  </p>
                </div>
              </div>
            </router-link>
          </div>
        </section>
      </div>
      </template>

      <!-- order/delivery ect section -->
      <section class="dashboard-grade-check">
        <header class="dashboard-grade-check-header">
          <div class="d-flex align-items-center justify-content-between">
            <div class="check-header__title">
              <h2 class="check-header__title__h2 text-black font-weight-500">
                주문/배송 조회
                <span class="check-header__title__caption text-gray-600"
                  >(최근 1개월)</span
                >
              </h2>
            </div>
            <div class="more-btn-wrap">
              <router-link :to="{name:'mypage-order',params:{init:true}}">
                <div class="check-header__more__btn d-flex align-items-center">
                  <span
                    class="check-header__more__btn__title text-gray-700 mr-1"
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
                    class="dashboard-check-list__item__num-p mb-0 atten-new font-weight-600"
                    :class="{ active: orderCntInfo.waitingcnt <= 0 }"
                  >
                     {{ orderCntInfo.waitingcnt }}
                  </p>
                </div>
                <div class="dashboard-check-list__item__state">
                  <p class="dashboard-check-list__item__state-p mb-0 font-weight-400"
                    :class="{ active: orderCntInfo.waitingcnt <= 0 }">
                    입금대기
                  </p>
                </div>
              </div>
            </li>
            <li class="dashboard-check-list">
              <div class="dashboard-check-list__item">
                <div class="dashboard-check-list__item__num">
                  <p class="dashboard-check-list__item__num-p mb-0 atten-new font-weight-600"
                    :class="{ active: orderCntInfo.deliing <=0 }"
                  >
                     {{ orderCntInfo.deliing}}
                  </p>
                </div>
                <div class="dashboard-check-list__item__state">
                  <p class="dashboard-check-list__item__state-p mb-0 font-weight-400"
                    :class="{ active: orderCntInfo.deliing <=0 }">
                    상품/배송준비중
                  </p>
                </div>
              </div>
            </li>
            <li class="dashboard-check-list">
              <div class="dashboard-check-list__item">
                <div class="dashboard-check-list__item__num">
                  <p
                    class="dashboard-check-list__item__num-p mb-0 atten-new font-weight-600"
                    :class="{ active: orderCntInfo.indelivcnt <= 0 }"
                  >
                     {{ orderCntInfo.indelivcnt }}
                  </p>
                </div>
                <div class="dashboard-check-list__item__state">
                  <p class="dashboard-check-list__item__state-p mb-0 font-weight-400"
                    :class="{ active: orderCntInfo.indelivcnt <= 0 }">
                    배송중
                  </p>
                </div>
              </div>
            </li>
            <li class="dashboard-check-list">
              <div class="dashboard-check-list__item">
                <div class="dashboard-check-list__item__num">
                  <p
                    class="dashboard-check-list__item__num-p mb-0 atten-new font-weight-600"
                    :class="{ active: orderCntInfo.compdelivcnt <= 0 }"
                  >
                     {{ orderCntInfo.compdelivcnt }}
                  </p>
                </div>
                <div class="dashboard-check-list__item__state">
                  <p class="dashboard-check-list__item__state-p mb-0 font-weight-400"
                    :class="{ active: orderCntInfo.compdelivcnt <= 0 }">
                    배송완료
                  </p>
                </div>
              </div>
            </li>
            <li class="dashboard-check-list">
              <div class="dashboard-check-list__item">
                <div class="dashboard-check-list__item__num">
                  <p
                    class="dashboard-check-list__item__num-p mb-0 atten-new font-weight-600"
                    :class="{ active: orderCntInfo.confirmcnt <= 0 }"
                  >
                     {{ orderCntInfo.confirmcnt }}
                  </p>
                </div>
                <div class="dashboard-check-list__item__state">
                  <p class="dashboard-check-list__item__state-p mb-0 font-weight-400"
                    :class="{ active: orderCntInfo.confirmcnt <= 0 }">
                    구매확정
                  </p>
                </div>
              </div>
            </li>
          </ul>
          <div class="dashboard-check-state d-flex">
            <div class="dashboard-check-state-wrap d-flex">
              <router-link :to="{name: 'mypage-claim', params: {state: 'CLM001',init:true}}">
                <div class="dashboard-check-state__item d-flex">
                  <div class="dashboard-check-state__text__wrap">
                    <span class="dashboard-check-state__text text-gray-700">
                      취소
                    </span>
                  </div>
                  <div class="dashboard-check-state__number__wrap">
                    <span class="dashboard-check-state__number atten-new font-weight-500" :class="{'text-black':orderCntInfo.claimcnccnt <=0, 'text-primary':orderCntInfo.claimcnccnt > 0 }">
                      {{ orderCntInfo.claimcnccnt }}
                    </span>
                  </div>
                </div>
              </router-link>
            </div>
            <div class="dp-bar check-state-bar"></div>
            <div class="dashboard-check-state-wrap d-flex">
              <router-link :to="{name: 'mypage-claim', params: {state: 'CLM002',init:true}}">
                <div class="dashboard-check-state__item d-flex">
                  <div class="dashboard-check-state__text__wrap">
                    <span class="dashboard-check-state__text text-gray-700">
                      반품
                    </span>
                  </div>
                  <div class="dashboard-check-state__number__wrap">
                    <span class="dashboard-check-state__number atten-new font-weight-500" :class="{'text-black':orderCntInfo.claimrtncnt <=0, 'text-primary':orderCntInfo.claimrtncnt > 0 }">
                      {{ orderCntInfo.claimrtncnt }}
                    </span>
                  </div>
                </div>
              </router-link>
            </div>

            <div class="dp-bar check-state-bar"></div>
            <div class="dashboard-check-state-wrap">
              <router-link :to="{name: 'mypage-claim', params: {state: 'CLM003',init:true}}">
                <div class="dashboard-check-state__item d-flex">
                  <div class="dashboard-check-state__text__wrap">
                    <span class="dashboard-check-state__text text-gray-700">
                      교환
                    </span>
                  </div>
                  <div class="dashboard-check-state__number__wrap">
                    <span class="dashboard-check-state__number atten-new font-weight-500" :class="{'text-black':orderCntInfo.claimexccnt <=0, 'text-primary':orderCntInfo.claimexccnt > 0 }">
                      {{orderCntInfo.claimexccnt}}
                    </span>
                  </div>
                </div>
              </router-link>
            </div>
          </div>
        </div>
      </section>
      <!-- review section -->
      <section class="dashboard-review">
        <header class="dashboard-review-header">
          <div class="d-flex align-items-center justify-content-between">
            <div class="review-header__title">
              <h2
                class="review-header__title__h2 text-black font-weight-500 mb-0"
              >
                나의 리뷰
              </h2>
            </div>
            <div class="more-btn-wrap">
              <router-link :to="{name:'before', params:{init:true}}">
                <div class="review-header__more__btn d-flex align-items-center">
                  <span
                    class="review-header__more__btn__title text-gray-700 mr-1"
                    >더보기</span
                  >
                  <i class="dp-icon sm icon-arrow-right"></i>
                </div>
              </router-link>
            </div>
          </div>
        </header>
        <div v-if="reviewList.length > 0">
          <ul class="dashboard-review-list__ul list-style-none">
            <li
              class="dashboard-review-list__item"
              v-for="(item, index) in reviewList"
              :key="index"
            >
              <div class="d-flex align-items-center" v-if="item.isdelyn != 'T'">
                <div class="dashboard-review-list d-flex">
                  <div class="dashboard-review__thumbnail mr-4" @click="goToReviewDetail(item)">
                    <product-thumbnail
                      style="width: 150px"
                      :height="150"
                      :thumbnailInfo="item"
                    />
                  </div>
                  <div class="dashboard-review__text">
                    <div class="review__text__ctg mb-1" v-if="!$util.isNull(item.brandname)">
                      <p class="review__ctg mb-0 atten-new">{{item.brandname}}</p>
                    </div>

                    <div class="review__text__title mb-1">
                      <p class="review__title mb-0 text-gray-700">
                        {{ item.goodsname }}
                      </p>
                    </div>
                    <div class="review-text-option d-flex">
                      <span class="review-option text-gray-700" v-html="item.opthtml"></span>
                    </div>
                  </div>
                </div>
                <div class="dashboard-review-amount__wrap">
                  <div class="dashboard-review-amount">
                    <p class="amount__number text-gray-700 mb-0">
                      {{ item.ordcnt }}개
                    </p>
                  </div>
                </div>
                <div class="dashboard-review-reward__wrap">
                  <div class="dashboard-review-reward">
                    <p class="review-reward__p text-black mb-0">
                      최대 {{ $util.maskComma(item.reviewmax) }}원 지급
                    </p>
                  </div>
                </div>
                  <div class="dashboard-review-button__wrap">
                    <b-button
                      class="dashboard-review-btn dp-btn btn-h32 not-hover"
                      variant="outline-gray-800"
                      squared
                      @click="goToReviewDetail(item)"
                    >
                      <span class="dashboard-review-btn__text text-gray-800"
                        >리뷰작성</span>
                    </b-button>
                  </div>
              </div>
            </li>
          </ul>
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
                      class="dashboard-review-text mb-0 font-weight-400 text-gray-700 dp-p-sm"
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
    </div>
  </main>
</template>
<script src="./Dashboard.js"></script>
