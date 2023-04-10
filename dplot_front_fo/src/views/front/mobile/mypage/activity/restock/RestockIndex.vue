<template>
  <main class="mypage-restock">
    <div class="container">
      <div class="restock-header d-flex">
        <div class="restock-header__total">
          <p class="total__text">
            전체 <span class="font-weight-500">{{ pagingData.listTotal }}</span
            >개
          </p>
        </div>
        <div class="restock-header__button">
          <b-button
            class="dp-btn header-cancel__btn btn-h32"
            variant="outline-gray-800"
            @click="delteProduct(true)"
            squared
          >
            <span class="cancel__btn__text">전체취소</span>
          </b-button>
        </div>
      </div>
      <hr class="dp-hr h1 mb-0" />
      <section class="dp-panel restock-body">
        <ul class="restock-list__ul list-style-none">
          <li
            class="restock-list__item__li"
            v-for="(list, index) in restockList"
            :key="index"
          >
            <!-- 상단 날짜 노출 여부-->
            <div
              class="restock-list__wrap"
              v-if="
                index == 0
                  ? true
                  : restockList[index].regdate != restockList[index - 1].regdate
              "
            >
              <p class="restock-list__date mb-0 atten-new text-gray-800">
                {{ list.regdate }}
              </p>
            </div>
            <div class="restock-list__item">
              <product-cart
                :product-info="list"
                :isIcon="true"
                :isHeader="true"
                :isFooter="false"
                :isSummary="false"
                :isPrice="false"
                :isPrice2="true"
                :isStatus="false"
                :isQuantity="false"
                :isSummary2="true"
                :isBadge="false"
                @delete="delteProduct"
              >
                <template #custom-contents>
                  <div
                    class="restock-list__alarm"
                    v-if="!$util.isNull(list.notidate)"
                  >
                    <p class="list__alarm__text text-gray-700 mb-0">
                      입고알림
                      <span class="text-secondary">{{ list.notidate }}</span>
                    </p>
                  </div>
                </template>
              </product-cart>
            </div>
          </li>
          <infinite-loading
            :identifier="infiniteId"
            @infinite="getRestockList"
            spinner="circles"
          >
            <div slot="no-more"></div>
            <div slot="no-results">
              <div class="container" v-if="isEnd && restockList.length <= 0">
                <div class="restock__circle">
                  <div class="d-flex flex-column align-items-center">
                    <div class="restock__list__empty">
                      <i class="dp-icon xl02 icon-alarm"></i>
                    </div>
                    <div class="restock__list__text">
                      <div class="restock__list__empty__text">
                        <p>재입고알림 신청내역이 없어요</p>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </infinite-loading>
        </ul>
      </section>
    </div>
  </main>
</template>
<script src="./RestockIndex.js"></script>
