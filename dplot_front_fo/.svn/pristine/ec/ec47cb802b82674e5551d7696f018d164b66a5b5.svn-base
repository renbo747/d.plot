<template>
  <main class="my-review-write">
    <form>
    <div class="review-write__item">
      <div class="info-icon" @click="showModal('reviewRewardModal')">
        <i class="dp-icon"></i>
        <span>리뷰 적립금</span>
      </div>
      <div class="review-item">
        <product-thumbnail
          :thumbnail-info="{
            id: reviewDetail.goodsno,
            fullpath: reviewDetail.fullpath,
          }"
        />
        <div class="item-text">
          <div class="item-text__header">
            <p class="mb-0 text-left">{{ reviewDetail.orderdate }}</p>
          </div>
          <p class="mb-0 item-text-p">
            {{ reviewDetail.goodsname }}
          </p>
          <p v-if="!$util.isNull(reviewDetail.opthtml)" class="mb-0 item-text-p">
             <span v-html="reviewDetail.opthtml"></span>
          </p>
        </div>
      </div>
      <div class="write-review__body dp-mt-40">
        <p class="dp-p font-weight-500 text-center">상품은 만족스러웠나요?</p>
        <figure
          class="d-flex align-items-center justify-content-center dp-mb-40 product-star"
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
        <div class="review-write__form dp-mb-20">
          <div class="form-header">
            <p class="mb-0">리뷰 작성</p>
            <div class="info-icon" @click="showModal('writeCautionModal')">
              <i class="dp-icon"></i>
              <span>작성유의사항</span>
            </div>
          </div>
        
            <base-textarea
              class="textarea-h-140"
              v-model="reviewParam.content"
              placeholder="내용을 입력해주세요 (최소 10자)"
              :isCount="true"
              :maxCount="500"
              @input="searchChangeReviewFunc()"
            />
        
        </div>
        <div class="review-write__img">
          <p class="image-upload__title">이미지 / 동영상 첨부 (선택)</p>
          <image-upload ref="imageupload" class="dp-mb-10" @change="changeFile" :isMobile='true'/>
          <p class="image-upload__p mb-0">
            이미지는 10MB 이하, 동영상은 50MB 이하 파일을 이미지 최대 10개, 동영상
            최대 1개까지 등록 가능합니다.
          </p>
        </div>
      <div class="review-write__button">
        <b-button class="dp-btn text-white" variant="gray-800" squared @click="saveMyReview()">
          {{reviewParam.isEdit=='T'?'수정 완료':'등록 완료'}}
        </b-button>
      </div>
    </div>

    <!-- 리뷰적립금 모달 -->
   
    <!-- // 리뷰적립금 모달 -->

    <!-- 작성유의사항 모달 -->
   
    <!-- // 작성유의사항 모달 -->
    </form>
  </main>
</template>
<script src="./Write.js"></script>
