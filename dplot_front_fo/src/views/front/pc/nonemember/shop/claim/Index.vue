<template>
  <main class="aside-article dp-mypage-return">
    <header class="aside-article__header">
      <div class="header-title">취소/교환/반품 목록</div>
    </header>
    <section class="mypage-return__body">
      <!-- 내역이 있을 떄-->
      <div v-if="claimList.length > 0">
        <template v-for="(claim,index) in claimList">
          <div class="product-info__area" :key="'header_' + index">
            <div class="d-flex justify-content-between align-items-center product-info__header">
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
              <ul class="list-style-none" :key="'item_' + index">
                <li
                  class="apply__product-info__li"
                  v-for="(item, index2) in claim.items" :key="index2"
                >
                  <div class="product-item">
                    <div class="item-box">
                      <div class="item-thumbnail">
                        <product-thumbnail 
                          :thumbnail-info="{ id: item.goodsno,fullpath: item.fullpath}"
                          :to="{name:'shop-detail',params:{pid:item.goodscode}}" 
                          :height="150"/>
                      </div>
                      <div class="item-info">
                        <p class="item-status">{{ item.statusnm }}</p>
                        <p class="item-ctg">{{ item.brandname }}</p>
                        <p class="item__p">{{ item.goodsname}}</p>
                        <p class="item__p option d-flex align-items-center" v-html="item.opthtml"></p>
                      </div>
                    </div>
                    <div class="item-count">
                      <p class="item__p text-center">{{ item.clmcnt }}개</p>
                    </div>
                    <div class="item-price">
                      <p class="price__p text-center mb-0">
                        <span class="price__span atten-new">{{$util.maskComma(item.realgoodsamt)}}</span>원
                      </p>
                    </div>
                    <div class="item-button">
                      <b-button
                        v-for="(button, index3) in item.buttonData"
                        :key="index3"
                        class="dp-btn btn-h32"
                        variant="outline-gray-400"
                        squared
                        @click="handleFooterButton(item, button.text)"
                      >
                        <span class="text-gray-800">{{ button.text }}</span>
                      </b-button>
                    </div>
                  </div>
                  <div class="product-item" v-if="item.excitem != null">
                    <div class="item-box">
                      <div class="item-thumbnail">
                      </div>
                      <div class="item-info">
                        <p class="item-ctg">{{ item.excitem.brandname }}</p>
                        <p class="item__p">{{ item.excitem.goodsname}}</p>
                        <p class="item__p option d-flex align-items-center" v-html="item.excitem.opthtml"></p>
                      </div>
                    </div>
                    <div class="item-count">
                      <p class="item__p text-center">{{ item.excitem.clmcnt }}개</p>
                    </div>
                    <div class="item-price">
                      <p class="price__p text-center mb-0">
                        <span class="price__span atten-new">{{$util.maskComma(item.excitem.realgoodsamt)}}</span>원
                      </p>
                    </div>
                    <div class="item-button">
                      <b-button
                        v-for="(button, index3) in item.excitem.buttonData"
                        :key="index3"
                        class="dp-btn btn-h32"
                        variant="outline-gray-400"
                        squared
                        @click="handleFooterButton(item.excitem, button.text)"
                      >
                        <span class="text-gray-800">{{ button.text }}</span>
                      </b-button>
                    </div>
                  </div>
                </li>
              </ul>
            </div>
          </div>
        </template>
        <div class="restock-pagination dp-panel pb-0 d-flex">
          <div class="restock-pagination-wrap">
            <base-pagination 
            :currentPage="pagingData.currentPage"
            :listTotal="pagingData.listTotal"
            :listCnt="pagingData.listCnt"
            @changePage="changePage"/>
          </div>
        </div>
      </div>
      <!-- 내역이 없을 떄-->
      <div v-else class="d-flex flex-column align-items-center as__empty-apply__area">
        <div class="icon-empty-apply__area d-flex align-items-center justify-content-center">
          <i class="dp-icon icon-empty-apply h68"></i>
        </div>
        <div>
          <p class="mb-0 as__empty-apply__text">
            {{claimName}} 내역이 없어요
          </p>
        </div>
      </div>
    </section>
  </main>
</template>

<script src="./Index.js">
</script>

<style scoped>
.restock-pagination {
  justify-content: center;
}
.mypage-order .product-item {
  margin-bottom: 30px;
}
</style>