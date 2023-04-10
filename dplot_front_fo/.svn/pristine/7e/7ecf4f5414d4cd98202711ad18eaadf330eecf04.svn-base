<template>
  <div class="contents-review__board-list">
    <div class="container">
      <div class="board-list__header"  v-if="searchData.isfile !== 'reviewall' || (searchData.isfile === 'reviewall' && reviewList.length > 0)">
        <ul class="dp-sort__ul list-style-none no-scrollbar">
          <li v-for="(list, index) in reviewType" :key="index">
            <label :for="list.id" class="dp-sort__label">
              <input
                type="radio"
                :id="list.id"
                name="review"
                v-model="searchData.isfile"
                :value="list.id"
                :ref="list.id"
                hidden
              />
              <span class="dp-sort__badge">{{ list.label }}</span>
            </label>
          </li>
        </ul>
        </div>
        <div
          class="d-flex justify-content-between align-items-center dp-mb-10"
        >
          <p class="dp-p-sm font-weight-500 mb-0">총 {{pagingData.listTotal}}건</p>
        <div class="list-select-box mr-0">
          <base-select
            class="select-list sm"
            v-model="searchData.order"
            :options="sampleSelectOptions"
          />
        </div>
      </div>

      <div class="board-list__body">
        <template v-if="reviewList.length === 0">
          <p class="text-center dp-p-sm">첫 리뷰를 작성해주세요.</p>
        </template>
        <template v-else>
          <ul class="board-list__ul list-style-none">
            <li v-for="(list, index) in reviewList" :key="index">
              <div class="review-list-item">
                <div class="review-header">
                  <div class="d-flex justify-content-between align-items-start">
                    <div>
                      <p class="dp-caption">
                        {{ list.regdate }}
                        <span class="dp-ml-10">{{ $util.maskUserId(list.reguserid) }}</span>
                      </p>
                      <p class="mb-0 dp-p-sm text-gray-700">
                        {{ list.goodsname }} {{list.optionnm}}
                      </p>
                      <div class="d-flex align-items-center grade">
                        <i class="dp-icon star-icon"></i>
                        <p class="mb-0 atten-new dp-p-sm font-weight-600">
                          {{ list.totpoint }}
                        </p>
                      </div>
                    </div>
                    <div class="text-right" v-if="list.ismyreview == 'T'">
                      <i v-if="list.grade" class="dp-icon badge-icon"></i>
                      <div class="review-edit">
                        <span @click="goToReviewWrite(list)">수정</span>
                        <span class="dp-bar h10"></span>
                        <span @click="deleteReview(list)">삭제</span>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="review-body text-gray-700 dp-mb-10" v-if="list.files.length > 0">
                  <p class="review-content mb-0" :class="{ 'with-photo': list.files[0].fullpath !== '' }"
                    v-html="list.content">
                  </p>
                  <!-- <template v-if="list.files[0].filetype == 'FLT001'">
                    <div v-if="list.files[0].fullpath !== ''" class="review-img-content">
                      <img :src="list.files[0].fullpath" @click="openModal('photoReviewModal',{photoList:reviewFileTopList,  reviewidx:list.reviewidx})"/>
                    </div>
                  </template>
                  <template v-else>
                    <div v-if="list.files[0].fullpath !== ''" class="review-img-content" style="position: relative;">
                      <img :src="$util.changeFileType(list.files[0].fullpath, '.jpg')" @click="openModal('photoReviewModal',{photoList:reviewFileTopList,  reviewidx:list.reviewidx})"/>
                      <i class="dp-icon icon-video-play xl" style="transform: translate(-50%, -50%); position: absolute; top: 50%; left: 50%;" v-if="list.filetype == 'FLT002'"></i>
                    </div>
                  </template> -->
                  <div v-if="list.files[0].fullpath !== ''" class="review-img-content" @click="openModal('photoReviewModal',{photoList:reviewFileTopList,  reviewidx:list.reviewidx})">
                    <review-thumbnail :file="list.files[0]" :imgCount="list.files.length" :size="100"/>
                  </div>
                </div>
                <div class="review-body text-gray-700 dp-mb-10" v-else>
                  <p class="review-content mb-0">{{list.content}}</p>
                </div>
                <div
                  class="
                    review-footer
                    d-flex
                    align-items-center
                    justify-content-end
                  "
                >
                  <base-like :is-active="list.ismyhelp == 'T'?true:false" :value="list.goodcnt" :reviewidx="list.reviewidx"/>
                  <p
                    class="mb-0 text-gray-800 review-report cursor"
                    @click="openModal('postReportModal', {reviewidx:list.reviewidx})"
                  >
                    신고하기
                  </p>
                </div>
              </div>
            </li>
          </ul>
          <b-button
            class="dp-btn dp-btn-icon"
            variant="outline-gray-800 not-hover type02"
            @click="addReviewList()"
            squared
            v-if="pagingData.listTotal > reviewList.length"
          >
            <span>리뷰 더 보기</span>
            <i class="dp-icon icon-arrow-down"></i>
          </b-button>
        </template>
      </div>
    </div>
  </div>
</template>

<script src="./ReviewList.js"></script>

<style>
</style>