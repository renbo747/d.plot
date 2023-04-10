<template>
  <main class="cs-inquiry">
    <div class="container">
      <div class="cs-inquiry-detail">
        <section class="inquiry-detail-state dp-panel">
          <p v-if="$route.name.indexOf('detail-inquiry') != -1" class="dp-p font-weight-700">{{ data.inquirytypename }} 문의</p>
          <p v-else class="dp-p font-weight-700">{{ data.qnatypename }} 문의</p>
          <div class="inquiry-detail-state-header d-flex">
            <div class="inquiry-detail-state-header__left d-flex">
              <p class="mb-0 dp-caption font-weight-700 mr-10"
                :class="{
                  'text-secondary': data.isreply === '답변대기',
                  'text-primary': data.isreply === '답변완료',
                }"
              >
                {{data.isreply}}
              </p>
              <p class="mb-0 dp-caption text-gray-700 font-weight-400">
                {{data.regdate}}
              </p>
            </div>
            <div class="inquiry-detail-state-header__right">
              <div v-if="data.isreply ==='답변대기'" class="inquiry-detail-edit text-gray-800">
                <span class="dp-caption text-gray-800 font-weight-400" @click="openWriteModal($route.name)">수정</span
                >
                <span class="dp-bar h10"></span>
                <span class="dp-caption text-gray-800 font-weight-400" @click="goDelete($route.name)"
                  >삭제</span
                >
              </div>
            </div>
          </div>
          <div class="inquiry-detail-state-body">
            <div class="inquiry-detail-state-body-info">
              <!-- 질문 -->
              <div class="info-question">
                <p style="word-break: break-all" class="mb-0 dp-p-sm text-black font-weight-400" v-html="data.content">
                </p>
              </div>

              <!-- 답변 -->
              <div class="info-answer">
                <p class="d-flex align-items-center info-answer-title">
                  <span class="dp-p-sm font-weight-700">[ 답변 ]</span>
                  <span v-if="data.isreply === '답변완료'" class="text-gray-700 font-weight-400 dp-caption ml-2">{{data.repregdate}}</span>
                </p>
                <div class="">
                  <p v-if="data.isreply === '답변대기'" class="dp-p-sm text-black font-weight-400 mb-10">
                    답변을 준비중입니다. <br />조금만 기다려주세요.
                  </p>
                  <p v-else class="dp-p-sm text-black font-weight-400 mb-10" v-html="$util.toEscape(data.repcontent)" style="overflow-x:auto;">
                  </p>
                </div>
              </div>
            </div>
          </div>
        </section>
        <section class="inquiry-detail-product dp-panel pt-0">
          <div class="detail-product__info" v-if="$route.name.indexOf('detail-inquiry') != -1 && !$util.isEmpty(orderList)">
            <div class="detail-product__info__item d-flex mb-10">
              <div class="detail-product__info__item__title">
                <p class="mb-0 dp-p-sm font-weight-400 text-gray-700">
                  주문번호
                </p>
              </div>
              <div class="detail-product__info__item__text">
                <p class="mb-0 dp-p-sm font-weight-500 text-gray-800">
                  {{ordno}}
                </p>
              </div>
            </div>
            <div class="detail-product__info__item order d-flex">
              <div class="detail-product__info__item__title">
                <p class="mb-0 dp-p-sm font-weight-400 text-gray-700">
                  주문상품
                </p>
              </div>
              <div class="detail-product__info__item__text">
                <p 
                  v-for="(item, index) in orderList"
                  :key="index"                                
                 class="mb-0 dp-p-sm font-weight-500 text-gray-800">
                  {{item.goodsname}} <span v-if="!$util.isNull(item.optionname)">( {{item.optionname}} )</span>
                </p>
              </div>
            </div>
          </div>
          
          <div
            class="detail-product__info__qna"
            v-if="$route.name.indexOf('detail-qna') != -1"
          >
            <div class="detail-product__info__qna__header">
              <div class="qna__header mb-20">
                <h2 class="mb-0  dp-p-sm qna__header__title text-black font-weight-500 mb-0">
                  문의상품
                </h2>
              </div>
              <div class="qna__body">
                <div class="inquiry-qna-body__thumbnail d-flex">
                  <div class="inquiry-qna-body__thumbnail__img">
                    <div>
                      <product-thumbnail
                        class="mr-10 cursor"
                        style="width: 150px"
                        :thumbnailInfo="qnaThumbnailData"
                        :class="{bg: true}"
                        :to="{name: 'shop-detail', params:{pid: data.goodscode}}"
                      />
                    </div>
                  </div>
                  <div class="inquiry-qna-body__thumbnail__info">
                    <p
                      class="qna__body-text text-gray-700 font-weight-400 mb-0"
                    >
                      {{data.goodsname}}
                    </p>
                    <div v-if="!$util.isNull(data.optionnm1)" class="d-flex align-items-center">
                      <span
                        class="qna__body-text font-weight-400 text-gray-700"
                      >
                        옵션 : {{data.optionnm1}}
                      </span>
                      <span class="dp-bar"></span>
                      <span class="qna__body-text font-weight-400 text-gray-700"
                        >{{data.optionnm2}}</span
                      >
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <div v-if="!$util.isEmpty(files)" class="detail-product__info__slide">
            <div class="product__info__slide__info dp-panel">
              <swiper class="swiper" :options="swiperDetailOption">
                <swiper-slide
                  v-for="(list, index) in files"
                  :key="index"
                >
                  <div class="info__slide__img" style="position: relative;">
                    <figure
                      class="info__slide__img__thumbnail"
                      @click="openModal(files, index)"
                    >
                      <img :src="list.filetype == 'FLT001'? list.fullpath:$util.changeFileType(list.fullpath, '.jpg')" />
                      <i class="dp-icon icon-video-play xl" style="transform: translate(-50%, -50%); position: absolute; top: 50%; left: 50%;" v-if="list.filetype == 'FLT002'"></i>
                    </figure>
                  </div>
                </swiper-slide>
              </swiper>
            </div>
          </div>
        </section>
      </div>
    </div>
    <div class="img-modal" v-if="isModalOpen === true" @click="closeModal">
      <div class="image-content">
        <template v-if="isMediaType">
          <swiper>
            <swiper-slide
              class="swiper-slide"
              v-for="(list, index) in files"
              :key="index"
            >
              <figure @click.stop>
                <img :src="list.fullpath" />
              </figure>
            </swiper-slide>
          </swiper>
        </template>
        <template v-else>
          <div class="video-area">
            <iframe
              width="560"
              height="315"
              :src="list.fullpath"
              frameborder="0"
              allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
              allowfullscreen
            ></iframe>
          </div>
        </template>
        <i class="dp-icon lg" @click="closeModal"></i>
      </div>
    </div>
    <!-- 이미지 모달 -->
  </main>
</template>


<script src="@views.mobile/cs/inquiry/Detail.js"/>