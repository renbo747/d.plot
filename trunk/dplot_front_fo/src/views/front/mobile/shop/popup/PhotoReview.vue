<template>
  <div>
    <b-modal
      id="photoReviewModal"
      modal-class="dp-modal page-layer photo-review-modal"
      scrollable
      centered
      :hide-footer="true"
    >
      <!-- Photo Review Modal Header -->
      <template #modal-header="{ cancel }">
        <h5 class="modal-title">포토 & 동영상 리뷰</h5>
        <i class="modal-close" @click="cancel()">
          <img
            src="@assets.mobile/img/icon/icon-close-black-20px.svg"
            alt="닫기"
          />
        </i>
      </template>

      <!-- Photo Review Modal Body -->
      <div class="page-layer__body">
        <div class="justify-margin photo-swiper dp-mb-20">
          <swiper
            ref="mySwiper"
            :options="photoSwiperOptions"
            @slideChange="slideCheck"
          >
            <swiper-slide v-for="(list, index) in photoList" :key="index">
              <!-- <li
                :style="{
                  backgroundImage: `url(${
                    list.filetype == 'FLT001'
                      ? list.fullpath
                      : $util.changeFileType(list.fullpath, '.jpg')
                  })`,
                }"
                @click="slideChange(index)"
              >
              <i v-if="list.filetype == 'FLT002'" style="transform: translate(-50%, -50%); position: absolute; top: 50%; left: 50%;" class="dp-icon icon-video-play xl"></i>
              </li> -->
              <li>
              <review-thumbnail :file="list" :imgCount="list.imgcnt" :size="nowIndex === index ? 96 : 100"
                @click="slideChange(index)"/>
              </li>
            </swiper-slide>
          </swiper>

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
        <div class="photo-modal__photos justify-margin dp-mb-20">
          <figure
            v-for="(list, index) in reviewInfo.files"
            :key="index"
            @click="imagemodal(reviewInfo.files, index)"
          >
            <i v-if=" list.filetype == 'FLT002'" class="dp-icon icon-video-play xl"></i>
            <img
              :src="
                list.filetype == 'FLT001'
                  ? list.fullpath
                  : $util.changeFileType(list.fullpath, '.jpg')
              "
              alt="리뷰이미지"
            />
          </figure>
        </div>
      </div>
      <div class="review-list-item">
        <div class="review-header">
          <div class="d-flex justify-content-between align-items-start">
            <div>
              <p class="dp-caption">
                {{ reviewInfo.regdate }}
                <span class="dp-ml-10">{{
                  $util.maskUserId(reviewInfo.reguserid)
                }}</span>
              </p>
              <p class="mb-0 dp-p-sm text-gray-700" v-html="reviewInfo.opthtml">
              </p>
              <div class="d-flex align-items-center grade">
                <i class="dp-icon star-icon"></i>
                <p class="mb-0 atten-new dp-p-sm font-weight-600">
                  {{ reviewInfo.totpoint }}
                </p>
              </div>
            </div>

            <div v-if="reviewInfo.isbest === 'T'">
              <i class="dp-icon badge-icon"></i>
            </div>
          </div>
        </div>
        <div class="review-body text-gray-700 dp-mb-10">
          <p class="mb-0" v-html="reviewInfo.content">
          </p>
        </div>
<!--Todo: 신고하기, 좋아요 기능이 생길 때 주석해제-->
<!--        <div
          class="review-footer d-flex align-items-center justify-content-end"
        >
          <i class="dp-icon like-icon"></i>
          <base-like
            :isActive="reviewInfo.ismyhelp == 'T' ? true : false"
            :value="reviewInfo.goodcnt"
            :reviewidx="reviewInfo.reviewidx"
          />
          <p
            class="mb-0 text-gray-800 review-report cursor"
            @click="openModal('postReportModal', reviewInfo.reviewidx)"
          >
            신고하기
          </p>
        </div>-->
      </div>
    </b-modal>
    <component
      :key="skey + 'm'"
      :is="modalInfo.comp"
      :id="modalInfo.id"
      :param="modalInfo.param"
      :fnConfirm="modalInfo.fnConfirm"
      :fnCancel="modalInfo.fnCancel"
    />
  </div>
</template>

<script src="./PhotoReview.js"></script>

<style>
</style>