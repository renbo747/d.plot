<template>
  <div>
    <b-modal
      id="photoReviewModalPc"
      modal-class="dp-modal pop-over photo-review-modal"
      centered
      :hide-footer="true"
    >
      <!-- Photo Review Modal Header -->
      <template #modal-header="{ cancel }">
        <h5 class="modal-title">포토 & 동영상 리뷰</h5>
        <i class="modal-close" @click="cancel()">
          <img
            src="@/assets/common/icon/icon-close-modal-30px.svg"
            alt="닫기"
          />
        </i>
      </template>

      <!-- Photo Review Modal Body -->
      <div class="pop-over__body">
        <div class="photo-swiper dp-mb-30">
          <swiper
            ref="mySwiper"
            :options="photoSwiperOptions"
            @slideChange="slideChange"
          >
            <swiper-slide v-for="(list, index) in photoList" :key="index">
              <li
                :style="{
                  backgroundImage: `url(${
                    list.filetype == 'FLT001'
                      ? list.fullpath
                      : $util.changeFileType(list.fullpath, '.jpg')
                  })`, 
                }"
              >
              <i class="dp-icon icon-video-play xl" style="transform: translate(-50%, -50%); position: absolute; top: 50%; left: 50%;" v-if="list.filetype == 'FLT002'"></i>
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
        <div class="photo-modal__photos dp-mb-30">
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
              alt="리뷰이미지" height="558px"
            />
          </figure>
        </div>
      </div>
      <div v-if="true" class="dp-mb-30 item-thumbnail">
        <figure class="item-img">
          <img
            src="@assets.pc/img/main/img-review-item01.jpg"
            alt="상품 이미지"
          />
        </figure>
        <div>
          <p class="item-p">{{ reviewInfo.goodsname }}</p>
          <p class="item-p text-gray-700" v-html="reviewInfo.opthtml"></p>
        </div>
      </div>
      <div class="review-list-item">
        <div class="review-header">
          <div class="d-flex justify-content-between align-items-start">
            <div>
              <p class="dp-p text-gray-700">
                {{ reviewInfo.regdate }}
                <span class="dp-ml-10">{{
                  $util.maskUserId(reviewInfo.reguserid)
                }}</span>
              </p>
              <p class="dp-mb-10 dp-p text-gray-700">
                {{ reviewInfo.optionnm }}
              </p>
              <div class="d-flex align-items-center grade">
                <i class="dp-icon star-icon"></i>
                <p class="mb-0 atten-new dp-title font-weight-600">
                  {{ reviewInfo.totpoint }}
                  <!-- 작성자 기준 점수라 리뷰수는 필요 없음...-->
                  <!--<span class="font-weight-400 text-gray-700">(23)</span>-->
                </p>
              </div>
            </div>

            <div v-if="reviewInfo.isbest === 'T'">
              <i class="dp-icon badge-icon"></i>
            </div>
          </div>
        </div>
        <div class="review-body text-gray-700 dp-mb-10">
          <p class="mb-0 dp-p">
            {{ reviewInfo.content }}
          </p>
        </div>
<!--       Todo: 신고하기 및 좋아요 생기면 주석해제-->
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

<script src="./PhotoReviewPc.js">
</script>
<style></style>