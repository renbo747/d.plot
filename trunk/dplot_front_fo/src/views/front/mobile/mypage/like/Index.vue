<template>
  <main class="mypage-like">
    <div class="mypage-like__tabs">
      <b-tabs v-model="tabIndex">
        <!-- like 탭 -->
        <!-- <b-tab title="LIKE" :active="$route.name === 'mz_like'" @click="changeTap('mz_like')"> -->
        <!-- like 있음 -->
        <!-- <div>
            <div class="container">
              <div class="like__area">
                <div class="like-tab__ul__area">
                  <ul class="dp-sort__ul type02 list-style-none">
                    <li v-for="(list, index) in likeTab" :key="index">
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
                    </li>
                  </ul>
                </div>
                <div class="like-tab__content__area">
                  <div class="like-tab__content__header d-flex justify-content-between align-items-center" v-if="likeList.length > 0">
                    <div><span class="dp-p-sm">전체 {{pagingData.listTotal}}개</span></div>
                    <div class="total-delete__btn__area">
                      <b-button
                        class="dp-btn not-hover btn-h32"
                        variant="outline-gray-800 type02"
                        squared
                      >
                        <span class="dp-p-sm" @click="delLike(true)">전체삭제</span>
                      </b-button>
                    </div>
                  </div>
                  <hr class="like-hr mt-0" v-if="likeList.length > 0"/>
                  <div class="like-tab__content__body">
                    <div>
                      <ul class="list-style-none like-tab__content__ul">
                        <li
                          class="like-tab__content__li"
                          v-for="(list, index) in likeList"
                          :key="index"
                          @click="goDetail(list)"
                        >
                          <div>
                            <div class="d-flex">
                              <div class="logo__img__wrap">
                                <img :src="!$util.isNull(list.fullpath)?list.fullpath:'https://fakeimg.pl/100/'"  height="100" width="100"/>
                              </div>
                              <div class="content__li__text__area">
                                <div class="badge__wrap">
                                  <div class="dp-badge sm" v-if="list.type=='brand'">
                                    {{ list.type }}
                                  </div>
                                  <div class="dp-badge sm secondary" v-else>
                                    {{ list.type }}
                                  </div>
                                </div>
                                <div class="title__wrap">
                                  <p class="mb-0 atten-new dp-caption">
                                    {{ list.subject }}
                                  </p>
                                </div>
                                <div class="text__wrap">
                                  <p class="mb-0 dp-p-sm">{{ list.contents }}</p>
                                </div>
                              </div>
                              <div class="close-icon__wrap" @click="delLike(false, list)">
                                <i class="dp-icon icon-close-black"></i>
                              </div>
                            </div>
                          </div>
                          <hr class="like-hr" />
                        </li>
                      </ul>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <infinite-loading :identifier="infiniteId" @infinite="getList" spinner="circles">
             <div slot="no-more"></div>
             <div slot="no-results">
              <div class="empty__area">
              <div class="empty__body d-flex flex-column align-items-center">
                <div class="empty-icon-area d-flex align-items-center justify-content-center">
                  <i class="dp-icon icon-empty-like xl02"></i>
                </div>
                <div class="empty-text__area">
                  <p class="dp-p-sm text-gray-700 mb-0">Like가 없어요</p>
                </div>
              </div>
            </div>
             </div>
          </infinite-loading>
        </b-tab> -->
        <!-- // like 탭 -->

        <!-- like 상품 탭 -->
        <b-tab
          title="Like 상품"
          :active="$route.name === 'g_like'"
          @click="changeTap('g_like')"
        >
          <!-- like 상품 있음 -->
          <div>
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
                    v-if="likeList.length > 0"
                  >
                    <div>
                      <span class="dp-p-sm"
                        >전체 {{ pagingData.listTotal }}개</span
                      >
                    </div>
                    <div class="total-delete__btn__area">
                      <b-button
                        class="dp-btn not-hover btn-h32"
                        variant="outline-gray-800 type02"
                        squared
                      >
                        <span class="dp-p-sm" @click="delGoodsLike(true)"
                          >전체삭제</span
                        >
                      </b-button>
                    </div>
                  </div>
                  <hr class="like-hr mt-0" v-if="likeList.length > 0" />
                  <div class="like-tab__content__body">
                    <div>
                      <ul class="list-style-none like-tab__content__ul">
                        <li
                          class="col-12 like-tab__content__li"
                          v-for="(item, index) in likeList"
                          :key="index"
                        >
                          <product
                            :product-info="item"
                            :is-horizontal="true"
                            :has-stock="item.stockcnt"
                          />
                          <div class="close-icon__wrap">
                            <i
                              class="dp-icon icon-close-black"
                              @click="delGoodsLike(false, item)"
                            ></i>
                          </div>
                          <hr class="like-hr" />
                        </li>
                      </ul>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <infinite-loading
            :identifier="infiniteId"
            @infinite="getGoodsList"
            spinner="circles"
          >
            <div slot="no-more"></div>
            <div slot="no-results">
              <div class="empty__area" v-if="isEnd && likeList.length <= 0">
                <div class="empty__body d-flex flex-column align-items-center">
                  <div
                    class="
                      empty-icon-area
                      d-flex
                      align-items-center
                      justify-content-center
                    "
                  >
                    <i class="dp-icon icon-empty-like xl02"></i>
                  </div>
                  <div class="empty-text__area">
                    <p class="dp-p-sm text-gray-700 mb-0">Like 상품이 없어요</p>
                  </div>
                </div>
              </div>
            </div>
          </infinite-loading>
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
              <div
                class="recent-view-product__area content__area"
                :key="Date.now()"
              >
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
                      <span class="dp-p-sm"
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
                        <span class="dp-p-sm">전체삭제</span>
                      </b-button>
                    </div>
                  </div>
                  <hr class="like-hr mt-0" />
                  <div class="like-tab__content__body">
                    <div
                      class="contents0"
                      v-for="(list, index) in recentList"
                      :key="index"
                    >
                      <div class="data__wrap">
                        <span class="date">{{
                          $util.getFormatDate(list.regdate, ".")
                        }}</span>
                      </div>
                      <ul class="list-style-none like-tab__content__ul">
                        <li
                          class="col-12 like-tab__content__li"
                          v-for="(item, index) in list.list"
                          :key="index"
                        >
                          <!-- <product
                            :product-info="item"
                            :is-horizontal="true"
                            :has-stock="item.stockcnt"
                            :status-text="item.statusText"
                          /> -->
                          <product
                            :product-info="item"
                            :is-horizontal="true"
                            :has-stock="item.stockcnt"
                            :key="Date.now()"
                          />
                          <div
                            class="close-icon__wrap"
                            @click="deleteRecent(false, item)"
                          >
                            <i class="dp-icon icon-close-black"></i>
                          </div>
                          <hr class="like-hr" />
                        </li>
                      </ul>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div v-if="recentList.length <= 0 &&  isEnd">
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
                  <i class="dp-icon icon-empty-recent-view xl02"></i>
                </div>
                <div class="empty-text__area">
                  <p class="dp-p-sm text-gray-700 mb-0">최근본상품이 없어요</p>
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
