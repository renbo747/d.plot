<template>
  <main class="dp-mypage-coupon-pc">
    <section class="coupon-list__section">
      <div class="container">
        <div class="coupon-list__header">
          <p class="mb-0 coupon-list__header__title">쿠폰 목록</p>
        </div>
        <div class="coupon-list__body">
          <div class="coupon__box__area">
            <div class="use-imminent__box coupon__box">
              <div class="use-imminent__box__header d-flex">
                <i class="dp-icon icon-imminent md"></i>
                <p class="mb-0 box__title">사용임박(7일 이내)</p>
              </div>
              <div class="use-imminent__box__footer box__footer">
                <p class="mb-0">
                  <span class="coupon__count text-primary atten-new">{{deadlinecnt}}</span
                  ><span class="coupon__unit">장</span>
                </p>
              </div>
            </div>
            <div class="use-possible__box coupon__box">
              <div class="use-possible__box__header d-flex">
                <i class="dp-icon icon-coupon md"></i>
                <p class="mb-0 box__title">사용가능</p>
              </div>
              <div class="use-possible__box__footer box__footer">
                <p class="mb-0">
                  <span class="coupon__count atten-new">{{usecnt}}</span
                  ><span class="coupon__unit">장</span>
                </p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
    <!-- 프로모션 토드 등록-->
    <section class="promotion-code__section">
      <div class="container">
        <div class="promotion-code__header">
          <p class="mb-0 promotion-code__header__title">프로모션 코드 등록</p>
        </div>
        <div class="promotion-code__body">
          <div class="input__area">
            <base-input placeholder="빈칸, 하이픈 없이 입력하세요" v-model="serialno"/>
            <b-button
              class="dp-btn not-hover btn-h48"
              variant="outline-gray-800"
              squared
              @click="addPromotion()"
            >
              <span>등록</span>
            </b-button>

            <div class="coupon-use__guide__area" style="cursor: pointer;" @click="openModal()">
              <i class="dp-icon icon-info md"></i>
              <p class="mb-0 coupon-use__guide__title">쿠폰 사용안내</p>
            </div>
          </div>
        </div>
      </div>
    </section>
    <!-- 쿠폰 -->
    <section class="coupon-use__tab__section">
      <div class="coupon-use__tabs">
        <b-tabs v-model="tapActive">
          <b-tab active>
            <template #title>
              <p class="tab-title mb-0">사용가능({{usecnt}})</p>
            </template>
            <!-- 사용가능 쿠폰 있을 때-->
            <div class="use-coupon__area coupon__area" v-if="useCouponList.length > 0">
              <div class="container">
                <ul class="list-style-none row coupon__ul">
                  <li
                    class="col-6"
                    v-for="(list, index) in useCouponList"
                    :key="index"
                  >
                    <div>
                      <order-coupon
                        :coupon-info="list"
                        :isDownload="false"
                      />
                    </div>
                  </li>
                </ul>
                <div>
                  <div class="pagination__area d-flex justify-content-center">
                    <base-pagination
                      class="justify-content-center dp-mt-10"
                      :currentPage="pagingData.currentPage"
                      :listTotal="pagingData.listTotal"
                      :listCnt="pagingData.listCnt"
                      @changePage="changePage"
                    />
                  </div>
                </div>
              </div>
            </div>
            <!-- 사용가능 쿠폰 없을 때-->
            <div class="use-coupon__empty__area empty__area" v-if="isEnd && useCouponList.length <= 0">
              <div class="empty__body d-flex flex-column align-items-center">
                <div
                  class="empty-icon-area d-flex align-items-center justify-content-center"
                >
                  <i class="dp-icon icon-coupon-lg h68"></i>
                </div>
                <div class="empty-text__area">
                  <p class="text-gray-700 mb-0 empty-text">
                    사용가능한 쿠폰이 없어요
                  </p>
                </div>
              </div>
            </div>
          </b-tab>
          <b-tab>
            <template #title>
              <p class="tab-title mb-0">추가다운 가능({{downcnt}})</p>
            </template>
            <!-- 다운가능 쿠폰 있을 때-->
            <div class="download-coupon__area coupon__area" v-if="downList.length> 0">
              <div class="container">
                <div class="download-icon__area d-flex justify-content-center">
                  <b-button
                    class="dp-btn text-white dp-btn-icon btn-h48"
                    variant="gray-800"
                    squared
                    @click="downloadAll()"
                  >
                    <span>전체 쿠폰받기</span>
                    <i class="dp-icon icon-download"></i>
                  </b-button>
                </div>
                <div class="coupon__ul">
                  <ul class="list-style-none row">
                    <li
                      class="col-6"
                      v-for="(list, index) in downList"
                      :key="index"
                    >
                      <div>
                        <order-coupon
                          :coupon-info="list"
                          :isDownload="true"
                          @down="downloadCoupon(list)"
                        />
                      </div>
                    </li>
                  </ul>
                </div>
                <div>
                  <div class="pagination__area d-flex justify-content-center">
                    <base-pagination
                      class="justify-content-center dp-mt-10"
                      :currentPage="pagingData.currentPage"
                      :listTotal="pagingData.listTotal"
                      :listCnt="pagingData.listCnt"
                      @changePage="changePage"
                    />
                  </div>
                </div>
              </div>
            </div>
            <!-- 다운가능 쿠폰 없을 때-->
            <div class="download-coupon__empty__area empty__area" v-if="isEnd && downList.length <= 0">
              <div class="empty__body d-flex flex-column align-items-center">
                <div
                  class="empty-icon-area d-flex align-items-center justify-content-center"
                >
                  <i class="dp-icon icon-coupon-lg h68"></i>
                </div>
                <div class="empty-text__area">
                  <p class="text-gray-700 mb-0 empty-text">
                    다운가능한 쿠폰이 없어요
                  </p>
                </div>
              </div>
            </div>
          </b-tab>
        </b-tabs>
      </div>
    </section>
  </main>
</template>

<script src="./Coupon.js"></script>
