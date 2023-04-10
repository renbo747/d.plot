<template>
  <!-- 리뷰 -->
  <article class="shop-detail__contents-review" :class="{ on : isReview }">
    <!-- 리뷰 헤더 -->
    <header class="contents-review__header">
      <div class="d-flex align-items-center dp-p font-weight-500" style="justify-content: space-between;">
        <p style="margin-bottom: 0;">
          <span class="atten-new" style="font-size:24px; font-weight:600;">REVIEW</span>
          <!-- <span class="atten-new ml-1">{{reviewavg}}</span> -->
          <span class="text-gray-600 atten-new ml-1">({{totalcnt}})</span>
        </p>
        <b-button
          class="dp-btn dp-btn-icon w-100"
          variant="outline-gray-800 not-hover"
          squared
          @click="goMyReviewList()"
        >
          <!-- <i class="dp-icon icon-review"></i> -->
          <span>상품리뷰 작성</span>
        </b-button>
      </div>
    </header>

    <!-- 리뷰 리스트 -->
    <div class="review_benefit">
      <p>상품 리뷰 작성하고<br /><strong>최대 2,000원</strong> 적립금 받으세요!</p>
      <div class="review_benefit_row">
        <div class="review_benefit_col">
          <div class="icon">
            <img src="@assets.common/icon/text_review.svg" />
          </div>
          <div class="label">
            <div class="title">텍스트<br>리뷰</div>
            <div class="desc">500원</div>
          </div>
        </div>
        <div class="review_benefit_col">
          <div class="icon">
            <img src="@assets.common/icon/photo_review.svg" />
          </div>
          <div class="label">
            <div class="title">포토/동영상<br>리뷰</div>
            <div class="desc">1,000원</div>
          </div>
        </div>
        <div class="review_benefit_col">
          <div class="icon">
            <img src="@assets.common/icon/first_review.svg" />
          </div>
          <div class="label">
            <div class="title">상품의<br>첫 리뷰</div>
            <div class="desc">1,000원</div>
          </div>
        </div>
      </div>
    </div>
    <div class="contents-review__list"  v-if="reviewFileTopList.length !== 0">
      <p class="dp-p-sm font-weight-500">포토 &amp; 동영상 리뷰</p>
      <div>
        <ul class="dp-touch-slider list-style-none">
          <!-- Todo: 마지막 리스트는 더보기 -->
          <li v-for="(list, index) in reviewFileTopList" :key="index">
            <div class="contents-review__item" @click="openModal('photoReviewModal',{photoList:reviewFileTopList,index:index, reviewidx:list.orgidx})">
              <!-- <img :src="list.filetype=='FLT001'?list.fullpath:$util.changeFileType(list.fullpath, '.jpg')" width="100" height="100"/>
              <i class="dp-icon icon-video-play" v-if="list.filetype == 'FLT002'"></i> -->
              <review-thumbnail :file="list" :imgCount="list.imgcnt" :size="100"/>
              <div class="review__more" v-if="index == reviewFileTopList.length -1 && reviewFileTopList.length > 5">
                <i class="dp-icon icon-plus"></i>
                <span class="dp-p-sm text-white">더보기</span>
              </div>
            </div>
          </li>
          <!-- Photo Review Modal -->
          <!-- // Photo Review Modal -->
        </ul>
      </div>
    </div>

    <!-- 리뷰 리스트 -->
    <ReviewList :reviewFileTopList="reviewFileTopList"></ReviewList>
  </article>
</template>

<script src= "./Review.js"></script>
<style scoped>
    .review_benefit {
    background:#fbfbfb;
    padding:30px 50px;
    margin:0 20px;
    margin-bottom: 25px;
    text-align: center;
  }
  .review_benefit p {
    font-size: 14px;
    word-break: keep-all;
  }
  .review_benefit_row {
    margin-top: 20px;
    width: 100%;
    display: flex;
    justify-content: space-between; 
    align-items: center;
  }
  .review_benefit_row .review_benefit_col {
    display: flex;
    align-items: center;
    flex-direction: column;
  }
  .review_benefit_row .review_benefit_col .label {
    display: flex;
    align-items: center;
    flex-direction: column;
    font-size: 14px;
  }
  .review_benefit_row .review_benefit_col .label .title {
    color:#888;
    margin-right: 5px;    
  }
  .review_benefit_row .review_benefit_col .label .desc {
    font-weight: 500;
  }
  .review_benefit_row .icon {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 20px;
    height: 20px;
    margin-bottom: 5px;
  }

  .review_benefit_row .icon img {
    max-width:100%;
  }
</style>