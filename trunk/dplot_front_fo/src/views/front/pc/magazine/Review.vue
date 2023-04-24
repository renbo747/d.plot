<template>
  <main class="magazine-review">
    <div class="container">
      <div class="container-inner">
        <h2 class="head__review-title">Review 리뷰</h2>
        <div class="best-review">
          <h3 class="review-title">베스트 리뷰</h3>
          <div class="best-review__body">
            <swiper :options="bestReviewSwiperOptionPc">
              <swiper-slide v-for="(list, index) in bestReviewList" :key="index">
                <div class="best-review__img" @click="openModal('photoReviewModalPc', {photoList:bestFileTopList,index:index, reviewidx:list.reviewidx})">
                  <div class="main-img__container">
                    <figure ref="reviewimgdiv" class="main-img" v-if="list.fstfiles">
                      <i class="dp-icon icon-video-play xl" v-if="list.fstfiles.filetype=='FLT002'"></i>
                      <img :src="list.fstfiles.filetype=='FLT002'?$util.changeFileType(list.fstfiles.fullpath,'.jpg'):list.fstfiles.fullpath" alt="리뷰 이미지"/>
                    </figure>
                  </div>
                  <div class="sub-img__container">
                    <ul class="sub-img__ul list-style-none">
                        <li  v-for="(item, index) in list.files" :key="index">
                          <figure class="sub-img">
                            <i class="dp-icon icon-video-play xl" v-if="item.filetype=='FLT002'"></i>
                            <img
                              :src="item.filetype=='FLT002'?$util.changeFileType(item.fullpath,'.jpg'): item.fullpath"
                              alt="리뷰 이미지"
                            />
                          </figure>
                        </li>
                    </ul>
                  </div>
                </div>
                <div class="review-info">
                  <div class="review-info__head">
                    <router-link class="info-item" :to="{ name: 'shop-detail', params: { pid:  list.goodscode} }">
                      <figure class="item-img">
                        <img
                          :src="list.fullpath"
                          alt="상품 이미지"
                        />
                      </figure>
                      <div>
                        <p class="item-p">{{list.goodsname}}</p>
                        <p class="item-p d-flex align-items-center" v-html="list.opthtml">
                        </p>
                      </div>
                    </router-link>
                  </div>
                  <div class="review-info__body">
                    <div>
                      <p class="info-date">
                        <span>{{list.regdate}}</span>
                        <span>{{$util.maskUserId(list.reguserid)}}</span>
                      </p>
                      <p class="info-name">{{list.optionname}}</p>
                      <div class="info-rate">
                        <i class="dp-icon star-icon"></i>
                        <p class="rate-number atten-new">{{list.totpoint}}</p>
                      </div>
                    </div>
                    <p class="info-text"> {{list.content}}</p>
                  </div>
                </div>
              </swiper-slide>
            </swiper>
            <div class="swiper-pagination" slot="pagination"></div>
          </div>
        </div>
       <ReviewLiveList></ReviewLiveList>
      </div>
    </div>
  </main>
</template>

<script src="./Review.js"></script>

