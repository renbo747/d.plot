<template>
  <main class="my-page-dashboard">
    <div class="container">
      <!-- grade section // 회원등급 없을 때 -->
      <section class="dashboard-grade justify-margin">
        <div class="dashboard-assets__header">
          <p class="assets-title">안녕하세요 <b>{{memberInfo.name}}</b>님</p>
        </div>
      </section>
      <hr class="dp-hr justify-margin" />
      <!-- order/delivery ect section -->
      <section class="dashboard-grade-check dp-panel">
        <header class="dashboard-grade-check-header">
          <div class="d-flex align-items-center justify-content-between">
            <div class="check-header__title">
              <h2 class="check-header__title__h2 dp-p text-black font-weight-500">
                주문/배송 조회
              </h2>
            </div>
            <div class="more-btn-wrap">
              <router-link to="/nonemember/order">
                <div class="check-header__more__btn d-flex align-items-center">
                  <span
                    class="check-header__more__btn__title dp-caption text-gray-700 mr-1"
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
                  <p class="dashboard-check-list__item__state-p mb-0 font-weight-400" :class="{ active: orderCntInfo.waitingcnt <= 0 }">
                    입금대기
                  </p>
                </div>
              </div>
            </li>
            <li class="dashboard-check-list">
              <div class="dashboard-check-list__item">
                <div class="dashboard-check-list__item__num">
                  <p
                    class="dashboard-check-list__item__num-p mb-0 atten-new font-weight-600"
                    :class="{ active:  orderCntInfo.prepgoodscnt +  orderCntInfo.prepdelivcnt <=0 }"
                  >
                    {{ orderCntInfo.prepgoodscnt +  orderCntInfo.prepdelivcnt}}
                  </p>
                </div>
                <div class="dashboard-check-list__item__state">
                  <p class="dashboard-check-list__item__state-p mb-0 font-weight-400" :class="{ active: orderCntInfo.prepgoodscnt +  orderCntInfo.prepdelivcnt <=0 }">
                    상품/배송<br />준비중
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
                  <p class="dashboard-check-list__item__state-p mb-0 font-weight-400" :class="{ active: orderCntInfo.indelivcnt <= 0 }">
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
                  <p class="dashboard-check-list__item__state-p mb-0 font-weight-400" :class="{ active: orderCntInfo.compdelivcnt <= 0 }">
                    배송<br />완료
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
                  <p class="dashboard-check-list__item__state-p mb-0 font-weight-400" :class="{ active: orderCntInfo.confirmcnt <= 0 }">
                    구매<br />확정
                  </p>
                </div>
              </div>
            </li>
          </ul>
          <div class="dashboard-check-state d-flex">
            <div class="dashboard-check-state-wrap d-flex">
              <router-link to="/nonemember/claim">
                <div class="dashboard-check-state__item d-flex">
                  <div class="dashboard-check-state__text__wrap">
                    <span class="dashboard-check-state__text text-gray-700">취소</span>
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
              <router-link to="/nonemember/claim">
                <div class="dashboard-check-state__item d-flex">
                  <div class="dashboard-check-state__text__wrap">
                    <span class="dashboard-check-state__text text-gray-700">반품</span>
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
              <router-link to="/nonemember/claim">
                <div class="dashboard-check-state__item d-flex">
                  <div class="dashboard-check-state__text__wrap">
                    <span class="dashboard-check-state__text text-gray-700">교환</span>
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
      <hr class="dp-hr justify-margin" />
      <!-- menu section -->
      <section class="dashboard-menu dp-panel pt-0">
        <div class="dashboard-menu-wrap">
          <ul class="dashboard-menu-list justify-margin list-style-none">
            <li class="dashboard-menu-list__item">
              <router-link to="/nonemember/order">
                <div class="menu-list__item__wrap d-flex">
                  <div class="menu-list__item__left d-flex">
                    <div class="menu-list__item__icon">
                      <i class="dp-icon icon-delivery h26"></i>
                    </div>
                    <div class="menu-list__item__title">
                      <span
                        class="menu-list__item__title__text text-black font-weight-400"
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
              <router-link to="/nonemember/claim">
                <div class="menu-list__item__wrap d-flex">
                  <div class="menu-list__item__left d-flex">
                    <div class="menu-list__item__icon">
                      <i class="dp-icon icon-return h26"></i>
                    </div>
                    <div class="menu-list__item__title">
                      <span
                        class="menu-list__item__title__text text-black font-weight-400"
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
              <router-link :to="{name:'nonemember-as-apply-list', params:{init:true}}">
                <div class="menu-list__item__wrap d-flex">
                  <div class="menu-list__item__left d-flex">
                    <div class="menu-list__item__icon">
                      <i class="dp-icon icon-notice h26"></i>
                    </div>
                    <div class="menu-list__item__title">
                      <span
                        class="menu-list__item__title__text text-black font-weight-400"
                        >AS 신청내역</span
                      >
                    </div>
                  </div>
                  <div class="menu-list__item__right">
                    <i class="dp-icon icon-arrow-right"></i>
                  </div>
                </div>
              </router-link>
            </li>
          </ul>
        </div>
      </section>
    </div>
  </main>
</template>
<script src="./Index.js"></script>