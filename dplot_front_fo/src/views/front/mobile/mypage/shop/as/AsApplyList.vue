<template>
  <main class="dp-as-apply-list">
    <div class="container">
      <section class="as-apply-list__header">
        <div class="as-apply__btn-area">
          <b-button
            class="dp-btn dp-btn-icon not-hover"
            variant="outline-gray-800"
            squared
            @click="openModal()"
          >          
            <i class="dp-icon icon-question"></i>
            <span>AS전용 문의</span>
          </b-button>
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
          <div class="test" v-for="(list, index) in product" :key="index">
            <div class="as-product-info__area">
              <div class="d-flex justify-content-between align-items-center">
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
                    <span class="text-gray-800">상세보기</span>
                    <i class="dp-icon icon-arrow-right sm"></i>
                  </b-button>
                </div>
              </div>
              <hr class="h1" />
              <div class="apply__product-info__area">
                <product-order
                  :key="Date.now()"
                  :product-info="list"
                  :isSummary="true"
                  :isHeader="false"
                  :isGuarantee="false"
                  :isFooter="false"
                  :isBadge="false"
                  :to="{ name: 'shop-detail', params: { pid: list.goodscode } }"
                />
              </div>
              <hr class="dp-hr justify-margin" />
            </div>
          </div>
        </template>
        <!-- as 신청 목록이 없을 때-->
        <template v-if="isEnd && $util.isEmpty(product)">
          <div class="d-flex flex-column align-items-center as__empty-apply__area">
            <div class="icon-empty-apply__area d-flex align-items-center justify-content-center">
              <i class="dp-icon icon-empty-apply xl02"></i>
            </div>
            <div>
              <p class="mb-0 as__empty-apply__text dp-p-sm">
                AS전용 문의 내역이 없어요
              </p>
            </div>
          </div>
        </template>
      </section>
      <infinite-loading :identifier="infiniteId" @infinite="infiniteHandler" spinner="circles">
        <div slot="no-more"></div>
        <div slot="no-results"><p class="text-center dp-p-sm"></p></div>
      </infinite-loading>
    </div>
  </main>
</template>

<script src="@views.mobile/mypage/shop/as/AsApplyList.js"/>
