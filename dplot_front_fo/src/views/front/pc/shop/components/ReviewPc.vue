<template>
  <article class="shop-detail__contents-review" :class="{on : isReview}">
    <header class="contents-review__header d-flex">
      <p
        class="
          contents-review__number
          d-flex
          align-items-center
          text-black
          font-weight-500
          mb-0
        "
      >
        <span class="review__rate__text atten-new">REVIEW</span>
        <!-- <span class="review__rate__number atten-new ml-1">{{reviewavg}}</span> -->
        <span
          class="
            review__rate__total
            text-gray-600
            font-weight-400
            atten-new
            ml-1
          "
          >({{ totalcnt }})</span
        >
      </p>
      <b-button
        class="content-review__btn dp-btn dp-btn-icon btn-h48 text-black"
        variant="outline-gray-800 not-hover"
        squared
        @click="goMyReviewList()"
      >
        <!-- <i class="dp-icon md icon-review"></i> -->
        <span class="review__btn__text dp-p text-black font-weight-400"
          >상품리뷰 작성</span
        >
      </b-button>
    </header>
    
    <!-- 리뷰 리스트 -->
    <div class="review_benefit">
      <p>상품 리뷰 작성하고 <strong>최대 2,000원</strong> 적립금 받으세요!</p>
      <div class="review_benefit_row">
        <div class="review_benefit_col">
          <div class="icon">
            <img src="@assets.common/icon/text_review.svg" />
          </div>
          <div class="label">
            <div class="title">텍스트 리뷰</div>
            <div class="desc">500원</div>
          </div>
        </div>
        <div class="review_benefit_col">
          <div class="icon">
            <img src="@assets.common/icon/photo_review.svg" />
          </div>
          <div class="label">
            <div class="title">포토/동영상 리뷰</div>
            <div class="desc">1,000원</div>
          </div>
        </div>
        <div class="review_benefit_col">
          <div class="icon">
            <img src="@assets.common/icon/first_review.svg" />
          </div>
          <div class="label">
            <div class="title">상품의 첫 리뷰</div>
            <div class="desc">1,000원</div>
          </div>
        </div>
      </div>
    </div>
    <div class="contents-review__list" v-if="reviewFileTopList.length !== 0">
      <p class="dp-title02 text-black font-weight-500">
        포토 &amp; 동영상 리뷰
      </p>
      <div class="review__slider">
        <ul class="dp-touch-slider d-flex list-style-none">
          <li v-for="(list, index) in reviewFileTopList" :key="index">
            <div class="contents-review__item" @click="openModal('photoReviewModal',{photoList:reviewFileTopList,index:index, reviewidx:list.orgidx})">
              <review-thumbnail :file="list" :imgCount="list.imgcnt" :size="190"/>
              <div class="review__more" v-if="index == reviewFileTopList.length -1 && reviewFileTopList.length > 5">
                <i class="icon-plus"></i>
                <span class="dp-title02 font-weight-400 text-white">더보기</span>
              </div>
            </div>
          </li>
        </ul>
      </div>
    </div>
    <!-- 리뷰 리스트 -->
    <reviewlist-pc :reviewFileTopList="reviewFileTopList"/>
  </article>
</template>

<script src="@views.mobile/shop/components/Review.js">
</script>
<style scoped>
  .shop-detail .shop-detail__contents-review .contents-review__header .review__rate__text  {font-size: 24px; font-weight: 600;}
  .shop-detail .shop-detail__contents-review .contents-review__header .review__rate__total {font-size: 18px;}
  .review_benefit {
    background:#fbfbfb;
    padding:47px 100px;
    margin-bottom: 40px;
    text-align: center;
  }
  .review_benefit p {
    font-size: 18px;
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
  }
  .review_benefit_row .review_benefit_col .label {
    display: flex;
    align-items: center;;
    font-size: 18px;
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
    margin-right:10px;
  }
</style>