<template>
  <main class="dp-as-apply-list-pc">
    <div class="container">
      <section class="as-apply-list__header">
        <div class="header__title__area">
          <p class="mb-0 header__title">AS전용 문의내역</p>
        </div>
        <div class="d-flex justify-content-between">
          <div class="header__description__area">
            <p class="mb-0 header__description">
              AS전용 문의내역 입니다. AS 관련한 자세한 사항은 전담매니저 확인 후
              별도 안내해드립니다.
            </p>
          </div>
          <div class="as-apply__btn-area">
            <b-button
              class="dp-btn dp-btn-icon not-hover"
              variant="outline-gray-800"
              squared
              @click="openModal()"
            >
              <i class="dp-icon icon-question h22"></i>
              <span>AS전용 문의</span>
            </b-button>
          </div>
        </div>
      </section>
      <section class="as-apply-list__body">
        <div class="d-flex select__area">
          <base-select
            class="order-select"
            v-model="month"
            :options="monthSelect"
            @change="changeSearch"
            placeholder="최근 1개월"
          />
          <base-select
            class="order-select"
            v-model="asStatus"
            :options="asStatusSelect"
            @change="changeSearch"
            placeholder="전체"
          />
        </div>
        <!-- as 신청 목록이 있을 때-->
        <template v-if="!$util.isEmpty(product)">
          <div
            class="as-product-info__wrap"
            v-for="(list, index) in product"
            :key="index"
          >
            <div class="as-product-info__area">
              <div
                class="
                  d-flex
                  justify-content-between
                  align-items-center
                  as-product-info__header
                "
              >
                <div class="apply-date atten-new">{{ list.regdate }}</div>
                <div class="apply-detail__btn">
                  <b-button
                    :key="Date.now()"
                    class="dp-btn dp-btn-icon not-hover btn-h30"
                    variant="outline-gray-400"
                    squared
                    pill
                    @click="goToAsDetail(list.asidx)"
                  >
                    <span class="text-gray-800 detail-view">상세보기</span>
                    <i class="dp-icon icon-arrow-right sm"></i>
                  </b-button>
                </div>
              </div>
              <hr class="h1" />
              <div class="apply__product-info__area d-flex align-items-center">
                <product-thumbnail
                  :thumbnailInfo="list"
                  :to="{ name: 'shop-detail', params: { pid: list.goodscode } }"
                  style="width: 150px"
                />
                <div class="product-info__area">
                  <p class="mb-0 delivery-status">{{ list.statusnm }}</p>
                  <p class="mb-0 atten-new product__ctg">
                    {{ list.brandname }}
                  </p>
                  <p class="mb-0 product__name">{{ list.goodsname }}</p>
                  <p class="mb-0 product__option d-flex align-items-center">
                    <span v-html="list.optionname"></span>
                  </p>
                </div>
                <div class="product__count__area">
                  <p class="mb-0 product__count">{{ list.ascnt }}개</p>
                </div>
                <div class="product__price__area">
                  <p class="mb-0 product__price">
                    {{ $util.maskComma(list.realgoodsamt)
                    }}<span class="price-unit">원</span>
                  </p>
                </div>
              </div>
            </div>
            <hr class="dp-hr" />
          </div>
          <div class="d-flex flex-column align-items-center">
            <base-pagination
              :currentPage="pagingData.currentPage"
              :listTotal="pagingData.listTotal"
              :listCnt="pagingData.listCnt"
              @changePage="changePage"
            />
          </div>
        </template>
        <!-- as 신청 목록이 없을 때-->
        <template v-if="isEnd && $util.isEmpty(product)">
          <div
            class="d-flex flex-column align-items-center as__empty-apply__area"
          >
            <div
              class="
                icon-empty-apply__area
                d-flex
                align-items-center
                justify-content-center
              "
            >
              <i class="dp-icon icon-empty-apply h68"></i>
            </div>
            <div>
              <p class="mb-0 as__empty-apply__text">
                AS전용 문의 내역이 없어요
              </p>
            </div>
          </div>
        </template>
      </section>
    </div>
  </main>
</template>

<script src="@views.pc/mypage/shop/as/AsApplyList.js"/>
