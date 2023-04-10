<template>
  <main class="my-review">
    <div class="my-review__tabs">
      <b-tabs>
        <!-- 작성가능 탭 -->
        <b-tab title="작성가능" :active="$route.name === 'before'" @click="changeTap('before')">
          <!-- todo : 리뷰 적립금 모달 추가 (7/8) -->
          <div class="review-point__guide__area">
            <i class="dp-icon icon-info" @click="showModal('reviewRewardModal')"></i>
            <p class="mb-0 review-point__guide__title" @click="showModal('reviewRewardModal')">리뷰적립금</p>
          </div>
          <!-- // todo : 리뷰 적립금 모달 추가 (7/8) -->
          <!-- 리뷰 작성할 아이템 있음 -->
          <div v-if="reviewList.length > 0" class="review__items">
            <div
              v-for="(item, $index) in reviewList"
              :key="$index"
              class="item-content"
            >
            <div v-if="item.isdelyn != 'T'">
              <div>
                <div class="review-item">
                  <product-thumbnail
                    :thumbnail-info="{ id: item.goodsno,fullpath: item.fullpath}"
                    :to="{name:'shop-detail',params:{pid:item.goodscode}}"
                    :height="100"
                  />
                  <div class="item-text">
                    <p class="mb-0">{{ item.orderdate }}</p>
                    <p class="mb-0">
                      {{ item.goodsname }}
                    </p>
                    <p v-if="!$util.isNull(item.opthtml)" class="mb-0">
                      <span v-html="item.opthtml"></span>
                    </p>
                  </div>
                </div>
              </div>
              <div>
                <b-button
                  class="dp-btn not-hover"
                  variant="outline-gray-400"
                  squared
                  @click="goToReviewWrite(item)"
                >
                  <span>리뷰작성</span>
                </b-button>
              </div>
            </div>
            </div>
          </div>
          <infinite-loading :identifier="infiniteId" @infinite="infiniteHandler" spinner="circles">
             <div slot="no-more"></div>
             <div slot="no-results">
              <div class="review__no-items" v-if="isEnd && reviewList.length <= 0">
                <div class="review__icon">
                  <i class="review-list-icon"></i>
                </div>
                <p class="no-review__text">리뷰 작성가능한 상품이 없어요</p>
              </div>
             </div>
          </infinite-loading>
        </b-tab>
        <!-- // 작성가능 탭 -->

        <!-- 작성완료 탭 -->
        <b-tab title="작성완료" :active="$route.name === 'after'" @click="changeTap('after')">
          <!-- todo : 리뷰 적립금 모달 추가 (7/8) -->
          <div class="review-point__guide__area">
            <i class="dp-icon icon-info" @click="showModal('reviewRewardModal')"></i>
            <p class="mb-0 review-point__guide__title" @click="showModal('reviewRewardModal')">리뷰적립금</p>
          </div>
          <!-- // todo : 리뷰 적립금 모달 추가 (7/8) -->
          <!-- 작성한 리뷰 있음 -->
          <div v-if="reviewList.length > 0" class="review__items">
            <div
              v-for="(item, index) in reviewList"
              :key="index"
              :class="{ last: index + 1 === reviewList.length }"
            >
              <div class="item-content">
                <div class="review-item complete-review">
                  <product-thumbnail
                    :thumbnail-info="{id: item.id,fullpath: item.fullpath}"
                    :to="{name:'shop-detail',params:{pid:item.goodscode}}"
                  />
                  <div class="item-text">
                    <p class="mb-0">{{ item.orderdate }}</p>
                    <p class="mb-0">
                      {{ item.goodsname }}
                    </p>
                     <p v-if="!$util.isNull(item.opthtml)" class="mb-0">
                       <span v-html="item.opthtml"></span>
                    </p>
                  </div>
                </div>
                <div class="review-info">
                  <div>
                    <div class="review-header">
                      <div
                        class="d-flex justify-content-between align-items-start"
                      >
                        <div class="text-left">
                          <p class="dp-caption mb-0 text-gray-700">
                            {{ item.regdate }}
                          </p>
                          <i class="dp-icon star-icon"></i>
                          <p class="mb-0 atten-new dp-p-sm font-weight-600">
                            {{ item.totpoint }}
                          </p>
                        </div>
                        <div class="text-right">
                          <div class="review-edit">
                            <span @click="deleteReview(item)">삭제</span>
                            <span class="dp-bar h10"></span>
                            <span @click="goToReviewWrite(item)">수정</span>
                          </div>
                        </div>
                      </div>
                    </div>
                    <div class="review-body text-gray-700 dp-mt-20">
                      <p
                        class="review-content mb-0"
                        v-html="item.content"
                      ></p>
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
                              <figure @click="openModal(item.files, index)" height="100">
                                <img :src="list.filetype == 'FLT001'? list.fullpath:$util.changeFileType(list.fullpath, '.jpg')"/>
                                <i v-if="list.filetype == 'FLT002'" style="transform: translate(-50%, -50%); position: absolute; top: 50%; left: 50%;" class="dp-icon icon-video-play"></i>
                              </figure>
                              
                            </div>
                          </swiper-slide>
                        </swiper>
                      </div>
                    </div>
                  </div>
                </div>

                <hr class="dp-hr justify-margin" />
              </div>
            </div>
          </div>
          <infinite-loading :identifier="infiniteId" @infinite="infiniteHandler" spinner="circles">
             <div slot="no-more"></div>
             <div slot="no-results">
              <div class="review__no-items">
                <div class="review__icon">
                  <i class="review-list-icon"></i>
                </div>
                <p class="no-review__text">작성하신 리뷰가 없어요</p>
              </div>
             </div>
          </infinite-loading>

        </b-tab>
      </b-tabs>

      <!-- // 작성완료 탭 -->
    </div>

    <!-- 이미지 모달 -->
    <!-- <div class="img-modal" v-if="isModalOpen === true" @click="closeModal">
      <div class="image-content">
        <template v-if="!isMediaType">
          <swiper>
            <swiper-slide
              class="swiper-slide"
              v-for="(list, index) in reviewList[selectedIndex].files"
              :key="index"
            >
              <figure @click.stop>
                <img :src="list.fullpath" alt="리뷰 이미지 확대" />
              </figure>
            </swiper-slide>
          </swiper>
        </template>
        <template v-else>
          <div class="video-area">
            <video-player class="vjs-custom-skin" ref="videoPlayer" :options="playerOptions" :playsinline="true"/>
          </div>
        </template>
        <i class="dp-icon lg" @click="closeModal"></i>
      </div>
    </div> -->
    <!-- // 이미지 모달 -->
    <!--  리뷰적립금 Modal -->
    
    <!--  // 리뷰적립금 Modal -->
    <!-- // todo : 리뷰 적립금 모달 추가 (7/8) -->
  </main>
</template>
<script src="./Index.js">
</script>