<template>
  <main class="mypage-restock">
    <div class="container">
      <!-- 헤더 -->
      <section class="mypage-restock-header">
        <header class="restock-section__header">
          <h2 class="restock-section__title">재입고알림 신청목록</h2>
        </header>
      </section>
      <div class="mypage-restock__area" v-if="restockList.length > 0">
        <div class="restock-header d-flex">
          <div class="restock-header__total">
            <p class="total__text mb-0">전체 {{ pagingData.listTotal }}개</p>
          </div>
          <div class="restock-header__button">
            <b-button
              class="dp-btn header-cancel__btn btn-h32"
              variant="outline-gray-800 not-hover"
              squared
              @click="delteProduct(true)"
            >
              <span class="cancel__btn__text">전체취소</span>
            </b-button>
          </div>
        </div>
        <hr class="dp-hr mb-0" />
        <section class="dp-panel restock-body">
          <ul class="restock-list__ul list-style-none">
            <li
              class="restock-list__item__li"
              v-for="(list, index) in restockList"
              :key="index"
            >
              <!-- <div
                class="restock-list__wrap"
                v-if="list.cartItem.id === 'restock01'"
              > -->
              <!-- 상단 날짜 노출 여부-->
              <div
                class="restock-list__wrap"
                v-if="
                  index == 0
                    ? true
                    : restockList[index].regdate !=
                      restockList[index - 1].regdate
                "
              >
                <p class="restock-list__date mb-0 atten-new text-gray-800">
                  {{ list.regdate }}
                </p>
              </div>
              <div class="restock-list__item">
                <product-cart-pc
                  :product-info="list"
                  :is-badge="false"
                  :is-alarm="true"
                  :isPrice="false"
                  :isPrice2="true"
                  :is-status="false"
                  :is-count="false"
                  :is-coupon="false"
                  :is-option="false"
                  :is-modal="false"
                  @delete="delteProduct"
                />
                <div class="icon-close-area" @click="delteProduct(false, list)">
                  <i
                    class="dp-icon icon-close-black md"
                    style="cursor: pointer"
                  ></i>
                </div>
              </div>
            </li>
          </ul>
          <div class="restock-pagination dp-panel pb-0 d-flex">
            <div class="restock-pagination-wrap">
              <base-pagination
                :currentPage="pagingData.currentPage"
                :listTotal="pagingData.listTotal"
                :listCnt="pagingData.listCnt"
                @changePage="changePage"
              />
            </div>
          </div>
        </section>
      </div>
      <div class="mypage-restock__empty" v-if="isEnd && restockList.length <= 0">
        <div class="restock__circle">
          <div class="d-flex flex-column align-items-center">
            <div class="restock__list__empty">
              <i class="dp-icon h68 icon-alarm"></i>
            </div>
            <div class="restock__list__text">
              <div class="restock__list__empty__text">
                <p class="mb-0 font-weight-400 text-gray-700 empty__text__p">
                  재입고알림 신청내역이 없어요
                </p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </main>
</template>
<script src="./RestockIndex.js"></script>
