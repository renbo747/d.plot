<template>
  <main class="my-review-write">
    <div class="d-flex justify-content-between dp-mb-60 align-items-end">
      <h2 class="my-review__title">리뷰등록</h2>
      <div class="info-icon" @click="showModal('reviewRewardModal')" style="cursor: pointer;">
        <i class="dp-icon md"></i>
        <span>리뷰 적립금</span>
      </div>
    </div>
    <div class="review-write__item">
      <div class="review-item dp-mb-50">
        <div class="d-flex align-items-center">
          <product-thumbnail
            :thumbnail-info="{
              id: reviewDetail.goodsno,
              fullpath: reviewDetail.fullpath
            }"
          />
          <div class="item-text">
            <p class="mb-0">
              {{ reviewDetail.goodsname }}
            </p>
            <p v-if="!$util.isNull(reviewDetail.opthtml)" class="mb-0 d-flex align-items-center">
               <span  v-html="reviewDetail.opthtml">
               </span>
            </p>
          </div>
          <div>
            <p class="mb-0 text-gray-700">{{ reviewDetail.orderdate }}</p>
          </div>
        </div>
        <!-- <div class="review-edit text-gray-800">
          <span class="cursor-pointer">삭제</span>
          <span class="dp-bar h10"></span>
          <span class="cursor-pointer">수정</span>
        </div> -->
      </div>
      <div class="write-review__body dp-mb-50">
        <p class="write-review-p">상품은 만족스러웠나요?</p>
        <figure
          class="d-flex align-items-center justify-content-center product-star"
        >
           <i @click="reviewParam.totpoint = 1">
            <img
              :src="reviewParam.totpoint >= 1? require('@assets.admin/common/icon/icon-star-black-26px.svg') : require('@assets.admin/common/icon/icon-star-gray-26px.svg') "
            />
          </i>
          <i @click="reviewParam.totpoint = 2">
            <img
              :src="reviewParam.totpoint >= 2? require('@assets.admin/common/icon/icon-star-black-26px.svg') : require('@assets.admin/common/icon/icon-star-gray-26px.svg') "
            />
          </i>
          <i @click="reviewParam.totpoint = 3">
            <img
              :src="reviewParam.totpoint >= 3? require('@assets.admin/common/icon/icon-star-black-26px.svg') : require('@assets.admin/common/icon/icon-star-gray-26px.svg') "
            />
          </i>
          <i @click="reviewParam.totpoint = 4">
            <img
              :src="reviewParam.totpoint >= 4? require('@assets.admin/common/icon/icon-star-black-26px.svg') : require('@assets.admin/common/icon/icon-star-gray-26px.svg') "
            />
          </i>
          <i @click="reviewParam.totpoint = 5">
            <img
              :src="reviewParam.totpoint == 5? require('@assets.admin/common/icon/icon-star-black-26px.svg') : require('@assets.admin/common/icon/icon-star-gray-26px.svg') "
            />
          </i>
        </figure>
      </div>
      <div class="review-write__form dp-mb-60">
        <div class="form-header">
          <p class="mb-0">리뷰 작성</p>
          <div class="info-icon" @click="showModal('writeCautionModal')" style="cursor: pointer;">
            <i class="dp-icon md"></i>
            <span>작성유의사항</span>
          </div>
        </div>
        <form>
          <base-textarea
            class="dp-mb-20 textarea-h-140"
            v-model.trim="reviewParam.content"
            placeholder="내용을 입력해주세요 (최소 10자)"
            :isCount="true"
            :maxCount="500"
            @input="searchChangeReviewFunc()"
          />
        </form>
      </div>
      <div class="review-write__img dp-mb-60">
        <p class="image-upload__title">이미지 / 동영상 첨부 (선택)</p>
        <image-upload ref="imageupload" class="dp-mb-20" @change="changeFile" :isMobile='false' />
        <ul class="image-upload__p ul-dot">
          <li>
            이미지는 10MB 이하, 동영상은 50MB 이하 파일을 이미지 최대 10개,
            동영상 최대 1개까지 등록 가능합니다.
          </li>
        </ul>
      </div>
      <div class="review-write__button">
        <b-button class="dp-btn text-white btn-h48" variant="gray-800" squared @click="saveMyReview()">
           {{reviewParam.isEdit=='T'?'수정 완료':'등록 완료'}}
        </b-button>
      </div>
    </div>
  </main>
</template>
<script src="@views.mobile/mypage/activity/myreview/Write.js"></script>
