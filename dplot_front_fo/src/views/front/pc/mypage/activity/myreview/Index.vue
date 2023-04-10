<template>
  <div class="aside-article my-review">
    <header class="aside-article__header">
      <div class="header-title">나의 리뷰</div>
      <div class="review-point__guide__area" style="cursor: pointer;" @click="showModal('reviewRewardModal')">
        <i class="dp-icon icon-info md"></i>
        <p class="mb-0 review-point__guide__title">리뷰적립금</p>
      </div>
    </header>
    <div class="my-review__tabs">
      <b-tabs>
        <!-- 작성가능 탭 -->
        <b-tab
          title="작성가능"
          :active="$route.name === 'before'"
          @click="changeTap('before')"
        >
          <!-- 리뷰 작성할 아이템 있음 -->
          <div v-if="reviewList.length > 0" class="review__items">
            <div
              v-for="(item, $index) in reviewList"
              :key="$index"
              class="item-content"
            >
              <div class="review-item" v-if="item.isdelyn != 'T'">
                <div class="d-flex align-items-center">
                  <div class="item-thumbnail">
                    <product-thumbnail
                      :thumbnail-info="{ id: item.goodsno,fullpath: item.fullpath}"
                      :to="{name:'shop-detail',params:{pid:item.goodscode}}"
                      :height="150"
                    />
                  </div>
                  <div class="item-name">
                    <p class="mb-0">{{ item.goodsname }}</p>
                    <p
                      v-if="!$util.isNull(item.opthtml)"
                      class="mb-0 d-flex align-items-center"
                      v-html="item.opthtml"
                    >
                      
                    </p>
                  </div>
                </div>
                <div class="d-flex align-items-center text-right">
                  <div class="item-date">
                    <p class="mb-0">{{ item.orderdate }}</p>
                  </div>
                  <div class="write-review-button">
                    <a @click="goToReviewWrite(item)">
                      <b-button
                        class="dp-btn not-hover"
                        variant="outline-gray-400"
                        squared
                      >
                        <span>리뷰작성</span>
                      </b-button>
                    </a>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <!-- 리뷰 작성할 아이템 없음 -->
          <div v-if="isEnd && reviewList.length <= 0" class="review__no-items">
            <div class="review__icon">
              <i class="review-list-icon"></i>
            </div>
            <p class="no-review__text">리뷰 작성가능한 상품이 없어요</p>
          </div>
        </b-tab>
        <!-- // 작성가능 탭 -->

        <!-- 작성완료 탭 -->
        <b-tab
          title="작성완료"
          :active="$route.name === 'after'"
          @click="changeTap('after')"
        >
          <!-- 작성한 리뷰 있음 -->
          <div v-if="reviewList.length > 0" class="review__items">
            <div
              v-for="(item, index) in reviewList"
              :key="index"
              :class="{ last: index + 1 === reviewList.length }"
            >
              <div class="item-content complete-review-content">
                <div class="review-item complete-review">
                  <div class="item-left">
                    <div class="item-thumbnail">
                      <product-thumbnail
                        :thumbnail-info="{
                          id: item.id,
                          fullpath: item.fullpath,
                        }"
                        :to="{
                          name: 'shop-detail',
                          params: { pid: item.goodscode },
                        }"
                        :height="150"
                      />
                    </div>
                    <div class="item-text">
                      <p class="mb-0">
                        {{ item.goodsname }}
                      </p>
                      <p v-if="!$util.isNull(item.opthtml)" class="mb-0 d-flex align-items-center">
                        <span v-html="item.opthtml"></span>
                      </p>
                    </div>
                  </div>
                  <div class="item-date">
                    <p class="mb-0">{{ item.orderdate }}</p>
                  </div>
                </div>
                <div class="review-info">
                  <div>
                    <div class="review-header">
                      <div
                        class="d-flex justify-content-between align-items-start"
                      >
                        <div class="text-left">
                          <p class="review-date mb-0 text-gray-600">
                            {{ item.regdate }}
                          </p>
                          <i class="dp-icon star-icon"></i>
                          <p class="mb-0 atten-new review-rate">
                            {{ item.totpoint }}
                          </p>
                        </div>
                        <div class="text-right">
                          <div class="review-edit">
                            <span
                              class="cursor-pointer"
                              @click="deleteReview(item)"
                              >삭제</span
                            >
                            <span class="dp-bar h10"></span>
                            <span
                              class="cursor-pointer"
                              @click="goToReviewWrite(item)"
                              >수정</span
                            >
                          </div>
                        </div>
                      </div>
                    </div>
                    <div class="review-body text-gray-700 dp-mt-20">
                      <p class="review-content mb-0" v-html="item.content"></p>
                    </div>
                    <div
                      v-if="item.files !== null"
                      class="review-footer dp-mt-30"
                    >
                      <div class="image-list">
                        <swiper :options="imgSwiperOption">
                          <swiper-slide
                            v-for="(list, index) in item.files"
                            :key="index"
                          >
                            <div class="img__item">
                              <figure class="cursor-pointer" @click="openModal(item.files, index)">
                                 <img :src="list.filetype == 'FLT001'? list.fullpath:$util.changeFileType(list.fullpath, '.jpg')"/>
                                 <i v-if="list.filetype == 'FLT002'" style="transform: translate(-50%, -50%); position: absolute; top: 50%; left: 50%;" class="dp-icon icon-video-play xl"></i>
                              </figure>
                            </div>
                          </swiper-slide>
                        </swiper>
                        <template v-if="!$util.isNull(item.files) && item.files.length > 5">
                        <div class="img-swiper-prev">
                          <img
                            src="@assets.pc/img/icon/icon-arrowLeft-white-41px.svg"
                            alt="prev button"
                          />
                        </div>
                        <div class="img-swiper-next">
                          <img
                            src="@assets.pc/img/icon/icon-arrowRight-white-41px.svg"
                            alt="next button"
                          />
                        </div>
                        </template>
                      </div>
                    </div>
                  </div>
                </div>
                <hr class="dp-hr h6" />
              </div>
            </div>
          </div>
          <!-- 작성한 리뷰 없음 -->
          <div  v-if="isEnd && reviewList.length <= 0" class="review__no-items">
            <div class="review__icon">
              <i class="review-list-icon"></i>
            </div>
            <p class="no-review__text">작성하신 리뷰가 없어요</p>
          </div>
        </b-tab>
      </b-tabs>
    </div>
    <base-pagination
      class="justify-content-center dp-mt-10"
      :currentPage="pagingData.currentPage"
      :listTotal="pagingData.listTotal"
      :listCnt="pagingData.listCnt"
      @changePage="changePage"
    />

    <!--  리뷰적립금 Modal -->

    <!--  // 리뷰적립금 Modal -->
  </div>
</template>

<script src="./Index.js"></script>
