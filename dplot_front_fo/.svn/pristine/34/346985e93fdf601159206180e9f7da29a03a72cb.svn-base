<template>
  <main class="dp-mypage-return">
    <div class="container">
      <section class="mypage-return__body">
        <!-- 내역이 있을 떄-->
        <div v-if="claimList.length > 0" class="mypage-order__body">
          <template v-for="(claim,index) in claimList">
            <div class="product-info__area" :key="'header_' + index">
              <div class="product-info__header">
                <div class="apply-date atten-new">{{$util.getFormatDate(claim.clmreqdate, '.')}}</div>
                <div class="apply-detail__btn">
                  <b-button
                    class="dp-btn dp-btn-icon not-hover btn-h30"
                    variant="outline-gray-400"
                    squared
                    pill
                   @click="goToDetail(claim)"
                  >
                    <span class="text-gray-800">상세보기</span>
                    <i class="dp-icon icon-arrow-right sm"></i>
                  </b-button>
                </div>
              </div>
              <hr class="dp-hr h1 return-hr" />
              <div class="apply__product-info__area">
                <!-- 클레임 목록 -->
                <ul
                  class="list-style-none apply__product-info__area__ul"
                  :key="'item_' + index">
                  <li
                    class="apply__product-info__li"
                    v-for="(item, index2) in claim.items" :key="index2">
                    <div>
                      <product-order
                        :product-info="item"
                        :is-badge="false"
                        @click="handleFooterButton(item, ...arguments)"
                      />
                    </div>
                    <div v-if="item.excitem != null">
                      <product-order
                        :product-info="item.excitem"
                        :is-badge="false"
                        :is-thumbnail-hidden="true"
                        @click="handleFooterButton(item.excitem, ...arguments)"
                      />
                    </div>
                  </li>
                </ul>
              </div>
            </div>
          </template>
        </div>
        <!-- 내역이 없을 떄-->
        <div v-else class="d-flex flex-column align-items-center as__empty-apply__area">
          <div class="icon-empty-apply__area d-flex align-items-center justify-content-center">
            <i class="dp-icon icon-empty-apply xl02"></i>
          </div>
          <div>
            <p class="mb-0 as__empty-apply__text dp-p-sm">
              {{claimName}} 내역이 없어요
            </p>
          </div>
        </div>
      </section>
    </div>
    <infinite-loading :identifier="infiniteId" @infinite="getClmList" spinner="circles">
      <div slot="no-more"></div>
      <div slot="no-results"></div>
    </infinite-loading>
  </main>
</template>

<script src="./Index.js">
</script>

<style scoped>
.product-cart {
  margin-bottom:20px;
}
</style>