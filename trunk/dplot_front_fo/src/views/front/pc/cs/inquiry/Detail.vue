<template>
  <main class="cs-inquiry">
    <div class="container">
      <div class="cs-inquiry-detail">
        <!-- 헤더 -->
        <section class="cs-inquiry-header">
          <header class="cs-inquiry-section__header">
            <h2
              class="cs-section__title text-black font-weight-700 mb-0"
              v-if="($route.name).indexOf('detail-inquiry') != -1"
            >
              1:1 문의
            </h2>
            <h2
              class="cs-section__title text-black font-weight-700 mb-0"
              v-if="($route.name).indexOf('detail-qna') != -1"
            >
              상품Q&A
            </h2>
          </header>
        </section>
        <!-- 배송상태 및 문의내용 -->
        <section class="inquiry-detail-state dp-panel pt-0">
          <div class="inquiry-detail-state-header d-flex">
            <div class="inquiry-detail-state-header__left d-flex">
              <p  v-if="($route.name).indexOf('detail-inquiry') != -1" class="mb-0 detail-state-header__left__title font-weight-500 text-black mr-10">
                {{ data.inquirytypename }}문의
              </p>
              <p  v-else class="mb-0 detail-state-header__left__title font-weight-500 text-black mr-10">
                {{ data.qnatypename }}문의
              </p>
              <p
                class="mb-0 detail-state-header__left__date text-gray-700 font-weight-400"
              >
                {{data.regdate}}
              </p>
            </div>
            <div class="inquiry-detail-state-header__right">
              <div class="inquiry-detail-edit text-gray-800">
                <span style="color:#F54C2B !important" v-if="data.isreply === '답변완료'"
                  class="inquiry-detail-edit-text text-secondary font-weight-700"
                  :class="{
                    'text-secondary': data.isreply === '답변대기',
                    'text-primary': data.isreply === '답변완료',
                  }"
                  >{{data.isreply}}</span
                >
                <span v-if="data.isreply === '답변대기'"
                  class="inquiry-detail-edit-text text-secondary font-weight-700"
                  :class="{
                    'text-secondary': data.isreply === '답변대기',
                    'text-primary': data.isreply === '답변완료',
                  }"
                  >{{data.isreply}}</span
                >
                <span v-if="data.isreply ==='답변대기'">
                  <span class="dp-bar"></span>
                  <span class="inquiry-detail-edit-text text-gray-800 font-weight-400 cursor" @click="goWrite($route.name)">수정</span>
                  <span class="dp-bar"></span>
                  <span class="inquiry-detail-edit-text text-gray-800 font-weight-400 cursor" @click="goDelete($route.name)">삭제</span>
                </span>
              </div>
            </div>
          </div>
          <div class="inquiry-detail-state-body">
            
            <div class="inquiry-detail-state-body-info">
              <div class="state-body-info">
                <div class="info-question">
                  <p style="word-break:break-all" class="mb-0 state-body-info__p text-black font-weight-400" v-html="data.content">
                  </p>
                </div>

                <div class="info-answer">
                  <p class="d-flex align-items-center info-answer-title">
                    <span class="font-weight-700">[ 답변 ]</span>
                    <span v-if="data.isreply === '답변완료'" class="text-gray-700 font-weight-400 ml-2 info-answer-date">{{data.repregdate}}</span>

                  </p>
                  <div class="">
                    <p v-if="data.isreply === '답변대기'" class="info-answer-text text-black font-weight-400 mb-10">
                      답변을 준비중입니다. <br />조금만 기다려주세요.
                    </p>
                    <p v-else class="info-answer-text text-black font-weight-400 mb-10" v-html="$util.toEscape(data.repcontent)" style="overflow-x: auto;">
                    </p>
                  </div>
                </div>
              </div>
            </div>

          </div>
        </section>
        <!-- swiper 내용 -->
        <section class="inquiry-detail-product">
          <!--  1:1 문의일때  -->
          <div class="detail-product__info" v-if="($route.name).indexOf('detail-inquiry') && !$util.isEmpty(orderList)">
            <div class="detail-product__info__item d-flex mb-10">
              <div class="detail-product__info__item__title">
                <p
                  class="mb-0 info__item__title__p font-weight-400 text-gray-700"
                >
                  주문번호
                </p>
              </div>
              <div class="detail-product__info__item__text">
                <p
                  class="mb-0 info__item__title__p font-weight-500 text-gray-800"
                >
                  {{ordno}}
                </p>
              </div>
            </div>
            <div class="detail-product__info__item order d-flex">
              <div class="detail-product__info__item__title">
                <p
                  class="mb-0 info__item__title__p font-weight-400 text-gray-700"
                >
                  주문상품
                </p>
              </div>
              <div class="detail-product__info__item__text"
              >
                <p 
                    v-for="(item, index) in orderList"
                    :key="index"                                            
                class="mb-0 info__item__title__p font-weight-500 text-gray-800">
                  {{item.goodsname}} <span v-if="!$util.isNull(item.optionname)">( {{item.optionname}} )</span>
                </p>
              </div>
            </div>
          </div>
          <!--  상품 Q&A 일때  -->
          <div
            class="detail-product__info__qna"
            v-if="($route.name).indexOf('detail-qna') != -1"
          >
            <div class="detail-product__info__qna__header">
              <div class="qna__header mb-20">
                <h2 class="qna__header__title text-black font-weight-500 mb-0">
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
                        :thumbnail-info="qnaThumbnailData"
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
                    <div class="d-flex align-items-center">
                      <span v-if="!$util.isNull(data.optionname)"
                        class="qna__body-text font-weight-400 text-gray-700"
                      >
                        옵션 : {{data.optionname}}
                      </span>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div v-if="!$util.isEmpty(files)" class="detail-product__info__slide">
            <div class="product__info__slide__info cursor">
              <swiper class="swiper" :options="swiperDetailOption">
                <swiper-slide
                  v-for="(list, index) in files"
                  :key="index"
                >
                  <div class="info__slide__img" style="position: relative;">
                    <figure
                      class="info__slide__img__thumbnail cursor"
                      @click="openModal(files, index)"
                    >
                      <img :src="list.filetype == 'FLT001'? list.fullpath:$util.changeFileType(list.fullpath, '.jpg')" alt="리뷰 이미지" />
                      <i class="dp-icon icon-video-play xl" style="transform: translate(-50%, -50%); position: absolute; top: 50%; left: 50%;" v-if="list.filetype == 'FLT002'"></i>
                    </figure>
                  </div>
                </swiper-slide>
              </swiper>
              <div class="detail-product__info__slide__btn" v-if="files.length > 5">
                <div class="swiper-btn-prev">
                  <img
                    src="@assets.pc/img/icon/icon-arrowLeft-white-41px.svg"
                    alt="prev button"
                  />
                </div>
                <div class="swiper-btn-next">
                  <img
                    src="@assets.pc/img/icon/icon-arrowRight-white-41px.svg"
                    alt="next button"
                  />
                </div>
              </div>
            </div>
          </div>
          <div class="detail-product__info__list__btn">
            <a>
              <b-button
                class="dp-btn info__list__btn not-hover"
                variant="outline-gray-800"
                squared
                @click="goListPage($route.name)"
              >
                <span class="info__list__btn__text text-gray-800"
                  >목록보기</span
                >
              </b-button>
            </a>
          </div>
        </section>
      </div>
    </div>
    <!-- 이미지 모달 -->
    <div class="img-modal" v-if="isModalOpen === true" @click="closeModal">
      <div class="image-content">
        <template v-if="isMediaType">
          <swiper :options="modalSwiperOption">
            <swiper-slide
              class="swiper-slide"
              v-for="(list, index) in files"
              :key="index"
            >
              <figure @click.stop>
                <img :src="list.fullpath" alt="리뷰 이미지 확대" />
              </figure>
            </swiper-slide>
          </swiper>
          <div class="modal-swiper-prev" @click.stop v-show="files.length > 1">
            <img
              src="@assets.pc/img/icon/icon-arrowLeft-white-41px.svg"
              alt="prev button"
            />
          </div>
          <div class="modal-swiper-next" @click.stop v-show="files.length > 1">
            <img
              src="@assets.pc/img/icon/icon-arrowRight-white-41px.svg"
              alt="next button"
            />
          </div>
        </template>
        <template v-else>
          <div class="video-area">
            <iframe
              width="560"
              height="315"
              src="https://www.youtube-nocookie.com/embed/PIoK5ZdYk6E?controls=0"
              frameborder="0"
              allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
              allowfullscreen
            ></iframe>
          </div>
        </template>
        <i class="dp-icon cursor-pointer" @click="closeModal"></i>
      </div>
    </div>
    <!-- // 이미지 모달 -->
  </main>
</template>


<script src="@views.mobile/cs/inquiry/Detail.js"/>