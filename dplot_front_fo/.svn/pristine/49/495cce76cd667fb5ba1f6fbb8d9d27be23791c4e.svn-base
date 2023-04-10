<template>
  <main class="magazine-review">
    <div class="container">
      <div class="best-review">
        <h2 class="review-title">베스트 리뷰</h2>
        <div class="best-review__body">
          <swiper :options="bestReviewSwiperOption" ref="bestReview" v-if ="bestReviewList.length > 0">
            <swiper-slide v-for="(list, index) in bestReviewList" :key="index">
              <figure class="review-img" @click="openModal('photoReviewModal', {photoList:bestFileTopList,index:index, reviewidx:list.reviewidx})">
                <i v-if="list.files[0].filetype=='FLT002'" class="dp-icon icon-video-play xxl"></i>
                <img
                  :src="list.files[0].filetype=='FLT002'?$util.changeFileType(list.files[0].fullpath,'.jpg') :list.files[0].fullpath"
                  alt="리뷰 이미지"
                />
              </figure>
              <div class="review-info">
                <div class="review-info__head">
                  <div class="info-head__left">
                    <p class="dp-p-sm mb-0 text-gray-700">
                      <span>{{$util.maskUserId(list.reguserid)}}</span>
                      <span class="dp-bar h10"></span>
                      <span>{{list.regdate}}</span>
                    </p>
                  </div>
                  <div class="info-head__right">
                    <i class="dp-icon star-icon"></i>
                    <p class="mb-0 atten-new dp-p-sm font-weight-600">{{list.totpoint}}</p>
                  </div>
                </div>
                <div class="review-info__body">
                  <router-link
                    class="info-item"
                    :to="{ name: 'shop-detail', params: { pid: list.goodscode } }"
                  >
                    <figure class="item-img">
                      <img
                        :src="list.fullpath"
                        alt="상품 이미지"
                      />
                    </figure>
                    <div>
                      <p class="item-p">{{list.goodsname}}</p>
                      <p class="item-p">옵션:{{list.optionnm}}</p>
                    </div>
                  </router-link>
                  <p class="info-text">
                   {{list.content}}
                  </p>
                </div>
              </div>
            </swiper-slide>
          </swiper>
          <div class="swiper-pagination" slot="pagination"></div>
          <div class="swiper-button-group">
            <div class="swiper-button-prev">
              <img
                src="@assets.pc/img/icon/icon-arrowLeft-white-41px.svg"
                alt="prev button"
              />
            </div>
            <div class="swiper-button-next">
              <img
                src="@assets.pc/img/icon/icon-arrowRight-white-41px.svg"
                alt="next button"
              />
            </div>
          </div>
        </div>
      </div>
      <ReviewLiveList></ReviewLiveList>
    </div>
  </main>
</template>

<script src="./Review.js"></script>
