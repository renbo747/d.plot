<template>
  <main class="mypage-like">
    <div class="mypage-like__header">
      <div class="like__header__area">
        <p class="mb-0 like__header__title">LIKE</p>
      </div>
    </div>
    <div class="mypage-like__tabs">
      <b-tabs v-model="tabIndex">
        <!-- like 탭 -->
        <!-- <b-tab
          title="LIKE"
          :active="$route.name === 'mz_like'"
          @click="changeTap('mz_like')"
        > -->
          <!-- like 있음 -->
          <!-- <div>
            <div class="container">
              <div class="like__area content__area">
                <div class="like-tab__ul__area">
                  <ul class="dp-sort__ul type02 list-style-none">
                    <li v-for="(list, index) in likeTab" :key="index">
                      <div>
                        <label :for="list.id" class="dp-sort__label">
                          <input
                            type="radio"
                            :id="list.id"
                            name="liketab"
                            v-model="searchData.type"
                            :value="list.id"
                            :ref="list.id"
                            hidden
                          />
                          <span class="dp-sort__badge atten-new">{{
                            list.label
                          }}</span>
                        </label>
                      </div>
                    </li>
                  </ul>
                </div>
                <div class="like-tab__content__area" v-if="likeList.length > 0">
                  <div
                    class="
                      like-tab__content__header
                      d-flex
                      justify-content-between
                      align-items-center
                    "
                  >
                    <div>
                      <span class="total__count"
                        >전체 {{ pagingData.listTotal }}개</span
                      >
                    </div>
                    <div class="total-delete__btn__area">
                      <b-button
                        class="dp-btn not-hover btn-h32"
                        variant="outline-gray-800 type02"
                        @click="delLike(true)"
                        squared
                      >
                        <span>전체삭제</span>
                      </b-button>
                    </div>
                  </div>
                  <hr class="like-hr mt-0" />
                  <div class="like-tab__content__body">
                    <div>
                      <ul class="list-style-none like-tab__content__ul">
                        <li
                          class="like-tab__content__li"
                          v-for="(list, index) in likeList"
                          :key="index"
                        >
                          <div>
                            <div class="d-flex align-items-center">
                              <div class="logo__img__wrap">
                                <img
                                  :src="
                                    !$util.isNull(list.fullpath)
                                      ? list.fullpath
                                      : 'https://fakeimg.pl/150/'
                                  "
                                  height="150"
                                  width="150"
                                   @click="goDetail(list)"
                                />
                              </div>
                              <div class="content__li__text__area">
                                <div class="badge__wrap">
                                  <div
                                    class="dp-badge"
                                    v-if="list.type == 'brand'"
                                  >
                                    {{ list.type }}
                                  </div>
                                  <div class="dp-badge secondary" v-else>
                                    {{ list.type }}
                                  </div>
                                </div>
                                <div class="title__wrap">
                                  <p class="mb-0 atten-new">
                                    {{ list.subject }}
                                  </p>
                                </div>
                                <div class="text__wrap">
                                  <p class="mb-0">{{ list.contents }}</p>
                                </div>
                              </div>
                              <div
                                class="close-icon__wrap"
                                @click="delLike(false, list)"
                              >
                                <i class="dp-icon icon-close-black"></i>
                              </div>
                            </div>
                          </div>
                          <hr class="like-hr" />
                        </li>
                      </ul>
                    </div>
                  </div>
                </div> -->
                <!-- 기존 퍼블은 LIKE의 내부 탭에서 항목에 대한 목록이 없을시 탭도 비노출되어 -->
                <!-- <div v-if="isEnd == true && likeList.length <= 0">
                  <div class="empty__area">
                    <div
                      class="empty__body d-flex flex-column align-items-center"
                    >
                      <div
                        class="
                          empty-icon-area
                          d-flex
                          align-items-center
                          justify-content-center
                        "
                      >
                        <i class="dp-icon icon-empty-like h68"></i>
                      </div>
                      <div class="empty-text__area">
                        <p class="mb-0 empty-text">Like가 없어요</p>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="like__area__footer">
                <div class="pagination__area d-flex justify-content-center">
                  <base-pagination
                    :currentPage="pagingData.currentPage"
                    :listTotal="pagingData.listTotal"
                    :listCnt="pagingData.listCnt"
                    @changePage="changePage"
                  />
                </div>
              </div>
            </div>
          </div> -->
          <!-- like 없음 -->
        <!-- </b-tab> -->
        <!-- // like 탭 -->

        <!-- like 상품 탭 -->
        <b-tab
          title="Like 상품"
          :active="$route.name === 'g_like'"
          @click="changeTap('g_like')"
        >
          <!-- like 상품 있음 -->
          <div v-if="likeList.length > 0">
            <div class="container">
              <div class="like-product__area content__area">
                <div class="like-tab__content__area">
                  <div
                    class="
                      like-tab__content__header
                      d-flex
                      justify-content-between
                      align-items-center
                    "
                  >
                    <div>
                      <span class="total__count"
                        >전체 {{ pagingData.listTotal }}개</span
                      >
                    </div>
                    <div class="total-delete__btn__area">
                      <b-button
                        class="dp-btn not-hover btn-h32"
                        variant="outline-gray-800 type02"
                        @click="delGoodsLike(true)"
                        squared
                      >
                        <span>전체삭제</span>
                      </b-button>
                    </div>
                  </div>
                  <hr class="like-hr mt-0" />
                  <div class="like-tab__content__body">
                    <div>
                      <ul class="list-style-none like-tab__content__ul">
                        <li
                          class="like-tab__content__li"
                          v-for="(list, index) in likeList"
                          :key="index"
                        >
                          <div class="product-layout__area">
                            <div class="d-flex">
                              <div class="thumbnail__area">
                                <product-thumbnail
                                  :thumbnail-info="{
                                    fullpath: list.fullpath,
                                  }"
                                  :to="{
                                    name: 'shop-detail',
                                    params: { pid: list.goodscode },
                                  }"
                                  :has-stock="list.stockcnt"
                                  :has-end="list.goodsselltype == 'GST003' || list.goodsselltype == 'GST006'? true:false"
                                />
                              </div>
                              <div class="product-info__area">
                                <p class="mb-0 atten-new product-ctg">
                                  {{ list.brandname }}
                                </p>
                                <p class="mb-0 product-title">
                                  {{ list.goodsname }}
                                </p>
                              </div>
                              <div class="price__area">
                                <p class="mb-0 product-price__area">
                                  <span class="product-price atten-new">{{
                                    $util.maskComma(list.saleamt)
                                  }}</span>
                                  <span class="price-unit">원</span>
                                </p>
                                <i
                                  class="product__like"
                                  :class="{ on: list.iswished == 'T' }"
                                  @click="changeWish(list)"
                                ></i>
                              </div>
                              <div class="close-icon__wrap">
                                <i
                                  class="dp-icon icon-close-black sm02"
                                  @click="delGoodsLike(false, list)"
                                ></i>
                              </div>
                            </div>
                            <hr class="like-hr" />
                          </div>
                        </li>
                      </ul>
                    </div>
                  </div>
                </div>
              </div>
              <div class="like__area__footer">
                <div class="pagination__area d-flex justify-content-center">
                  <base-pagination
                    :currentPage="pagingData.currentPage"
                    :listTotal="pagingData.listTotal"
                    :listCnt="pagingData.listCnt"
                    @changePage="changePage"
                  />
                </div>
              </div>
            </div>
          </div>
          <!-- like 상품 없음 -->
          <div v-if="likeList.length <= 0 && isEnd">
            <div class="empty__area">
              <div class="empty__body d-flex flex-column align-items-center">
                <div
                  class="
                    empty-icon-area
                    d-flex
                    align-items-center
                    justify-content-center
                  "
                >
                  <i class="dp-icon icon-empty-like-product h68"></i>
                </div>
                <div class="empty-text__area">
                  <p class="empty-text mb-0">Like 상품이 없어요</p>
                </div>
              </div>
            </div>
          </div>
        </b-tab>
        <!-- // like 상품 탭 -->

        <!-- 최근 본 상품 탭 -->
        <b-tab
          title="최근 본 상품"
          :active="$route.name === 'recent'"
          @click="changeTap('recent')"
        >
          <!-- 최근 본 상품 있음 -->
          <div v-if="recentList.length > 0">
            <div class="container">
              <div class="recent-view-product__area content__area">
                <div class="like-tab__content__area">
                  <div
                    class="
                      like-tab__content__header
                      d-flex
                      justify-content-between
                      align-items-center
                    "
                  >
                    <div>
                      <span class="total__count"
                        >전체 {{ pagingData.listTotal }}개</span
                      >
                    </div>
                    <div class="total-delete__btn__area">
                      <b-button
                        class="dp-btn not-hover btn-h32"
                        variant="outline-gray-800 type02"
                        squared
                        @click="deleteRecent(true)"
                      >
                        <span>전체삭제</span>
                      </b-button>
                    </div>
                  </div>
                  <hr class="like-hr mt-0" />
                  <div class="like-tab__content__body">
                    <div
                      class="contents01"
                      v-for="(list, index) in recentList"
                      :key="index"
                    >
                      <div class="data__wrap">
                        <span class="date atten-new">{{
                          $util.getFormatDate(list.regdate, ".")
                        }}</span>
                      </div>
                      <ul class="list-style-none like-tab__content__ul">
                        <li
                          class="like-tab__content__li"
                          v-for="(list, index) in list.list"
                          :key="index"
                        >
                          <div class="product-layout__area">
                            <div class="d-flex">
                              <div class="thumbnail__area">
                                <product-thumbnail
                                  :thumbnail-info="{
                                    fullpath: list.fullpath,
                                  }"
                                  :to="{
                                    name: 'shop-detail',
                                    params: { pid: list.goodscode },
                                  }"
                                  :has-stock="list.stockcnt"
                                  :key="Date.now()"
                                />
                              </div>
                              <div class="product-info__area">
                                <p class="mb-0 atten-new product-ctg">
                                  {{ list.brandname }}
                                </p>
                                <p class="mb-0 product-title">
                                  {{ list.goodsname }}
                                </p>
                              </div>
                              <div class="price__area">
                                <p class="mb-0 product-price__area">
                                  <span class="product-price atten-new">{{
                                    $util.maskComma(list.saleamt)
                                  }}</span>
                                  <span class="price-unit">원</span>
                                </p>
                                <i
                                  class="product__like"
                                  :class="{ on: list.iswished == 'T' }"
                                  @click="changeWish(list)"
                                ></i>
                              </div>
                              <div
                                class="close-icon__wrap"
                                @click="deleteRecent(false, list)"
                              >
                                <i class="dp-icon icon-close-black sm02"></i>
                              </div>
                            </div>
                            <hr class="like-hr" />
                          </div>
                        </li>
                      </ul>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <!-- 최근 본 상품 없음 -->
          <div v-if="isEnd && recentList.length <= 0">
            <div class="empty__area">
              <div class="empty__body d-flex flex-column align-items-center">
                <div
                  class="
                    empty-icon-area
                    d-flex
                    align-items-center
                    justify-content-center
                  "
                >
                  <i class="dp-icon icon-empty-recent-view h68"></i>
                </div>
                <div class="empty-text__area">
                  <p class="empty-text mb-0">최근본상품이 없어요</p>
                </div>
              </div>
            </div>
          </div>
        </b-tab>
        <!-- // 최근 본 상품 탭 -->
      </b-tabs>
    </div>
  </main>
</template>

<script src="./Index.js"></script>
