<template>
  <main class="my-page-dashboard">
    <div>
      <div class="dashboard-container">
        <!-- 회원등급이 없을 때 -->
        <div class="dashboard-assets__header">
          <p class="assets-title">안녕하세요 <b>{{memberInfo.name}}</b>님</p>
        </div>
      </div>
      <!-- order/delivery ect section -->
      <section class="dashboard-grade-check">
        <header class="dashboard-grade-check-header">
          <div class="d-flex align-items-center justify-content-between">
            <div class="check-header__title">
              <h2 class="check-header__title__h2 text-black font-weight-500">
                주문/배송 조회
                <!-- <span class="check-header__title__caption text-gray-600">(최근 1개월)</span> -->
              </h2>
            </div>
            <div class="more-btn-wrap">
              <router-link to="/nonemember/order">
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
              <router-link to="/nonemember/claim">
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
              <router-link to="/nonemember/claim">
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
              <router-link to="/nonemember/claim">
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
    </div>
  </main>
</template>
<script src="./Dashboard.js"></script>
