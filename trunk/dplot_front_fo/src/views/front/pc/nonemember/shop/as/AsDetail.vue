<template>
  <main class="dp-as-detail-pc">
    <div class="container">
      <section class="as-detail__header">
        <div class="as-detail__header__title__area">
          <p class="mb-0 header__title">AS전용 문의</p>
        </div>
        <div class="d-flex">
          <div>
            <span class="apply-title">AS전용 문의 접수일</span>
            <span class="apply-date atten-new">{{product.regdate}}</span>
          </div>
          <span class="dp-bar h14 mt-2"></span>
          <span class="atten-new apply-number">{{product.ordno}}</span>
        </div>
      </section>
      <hr class="dp-hr" />
      <section class="as-detail__shipping-product dp-section">
        <div class="as-product__area">
          <div class="as-product__header">
            <p class="mb-0 as-product__header__title">{{product.brandname}}</p>
          </div>
          <hr class="h1" />
          <div class="apply__product-info__area d-flex align-items-center">
            <product-thumbnail 
            :thumbnailInfo="product"
            :to="{ name: 'shop-detail', params: { pid: product.goodscode } }"
            style="width: 150px" />
            <div class="product-info__area">
              <p class="mb-0 delivery-status">{{product.statusnm}}</p>
              <p class="mb-0 atten-new product__ctg">{{product.brandname}}</p>
              <p class="mb-0 product__name">{{product.goodsname}}</p>
              <p v-if="!$util.isNull(product.optionname)" class="mb-0 product__option d-flex align-items-center">
                <span>옵션 : &nbsp;</span><span v-html="product.optionname"></span>
              </p>
            </div>
            <div class="product__count__area">
              <p class="mb-0 product__count">{{product.ascnt}}개</p>
            </div>
            <div class="product__price__area">
              <p class="mb-0 product__price">
                {{$util.maskComma(product.realgoodsamt)}}<span class="price-unit">원</span>
              </p>
            </div>
          </div>
        </div>
      </section>
      <hr class="dp-hr" />
      <section class="as-center__guide__area dp-section">
        <div class="as-center__guide">
          <div class="as-center__guide__header">
            <p class="mb-0 as-center__guide__header__title">
              협력사 AS 센터안내
            </p>
          </div>
          <div class="as-center__guide__body">
            <div class="d-flex justify-content-between align-items-center">
              <div class="d-flex flex-column">
                <div class="seller-name__area">
                  <span class="seller-name">{{product.dealernm}}</span>
                </div>
                <div class="seller-phone__area">
                  <span class="atten-new seller-phone" v-html="$util.maskTel(product.dealertel)"></span>
                </div>
              </div>
              <div></div>
            </div>
          </div>
          <div class="as-center__guide__footer">
            <p class="mb-0 dp-caption">
              협력사 AS센터 이용 시 보다 빠른 안내가능가 가능합니다.
            </p>
          </div>
        </div>
      </section>
      <hr class="dp-hr" />
      <section class="as__apply-info__area dp-section">
        <div class="as__apply-info">
          <div class="as__apply-info__header">
            <p class="mb-0 apply-info__header__title">AS전용 문의 접수정보</p>
          </div>
          <hr class="h1" />
          <div class="as__apply-info__body">
            <div class="customer-info__area">
              <ul class="list-style-none customer-info__ul">
                <li v-for="(list, index) in customerInfo" :key="index">
                  <div class="d-flex">
                    <span class="customer-info__key">{{ list.infoKey }}</span>
                    <span class="customer-info__value">{{
                      list.infoValue
                    }}</span>
                  </div>
                </li>
              </ul>
            </div>
          </div>
        </div>
      </section>
      <hr class="dp-hr" />
      <section class="as__content__area dp-section">
        <div class="as__content">
          <div class="as__content__header">
            <p class="mb-0 as__content__header__title">AS 접수내용</p>
          </div>
          <hr class="h1" />
          <div class="as__content__body">
            <div class="as__content__text__area">
              <p class="mb-0 as__content__text" v-html="product.content">
              </p>
            </div>
            <div class="detail-product__info__slide">
              <div class="product__info__slide__info cursor">
                <swiper class="swiper" ref="swiper" :options="swiperDetailOption" v-if="files.length > 0">
                  <swiper-slide v-for="(list, index) in files" :key="index">
                    <div class="info__slide__img" @click="openModal(files,index)">
                      <figure
                        class="info__slide__img__thumbnail cursor"
                      >
                        <img style="width: 250px;" :src="list.filetype == 'FLT001'? list.fullpath:$util.changeFileType(list.fullpath, '.jpg')" />
                        <i class="dp-icon icon-video-play xl" style="transform: translate(-50%, -50%); position: absolute; top: 50%; left: 50%;" v-if="list.filetype == 'FLT002'"></i>
                      </figure>
                    </div>
                  </swiper-slide>
                </swiper>
              </div>
            </div>
            <hr class="h1" />
            <div class="answer__content__area">
              <div class="answer__content__header">
                <div>
                  <span class="answer__content__header__title">답변내용</span>
                  <span v-if="product.isreply == 'T'" class="answer-date">{{product.repregdate}}</span>
                </div>
              </div>
              <div class="answer__content__body">
                <div class="answer__content">
                  <p v-if="product.isreply == 'F'" class="mb-0 answer__content__text">
                    안녕하세요. <br />
                    신청 접수가 완료되었습니다.빠른 시간안에 연락 드리겠습니다.
                  </p>
                  <div v-if="product.isreply == 'T'" v-html="product.repcontent" class="mb-0 dp-p-sm">
                  </div>
                  <p v-if="product.isreply == 'T'" class="mb-0 manager-name">전담매니저 : {{product.repuserno}}</p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>
      <section class="as-detail__btn__area">
        <div class="btn-group">
          <b-button
            v-if="product.isreply == 'F'"
            class="dp-btn not-hover"
            variant="outline-gray-800"
            squared
            @click="goToaApplyAs"
          >
            <span>수정</span>
          </b-button>
          <b-button 
            v-if="product.isreply == 'F'"          
            class="dp-btn text-white" 
            variant="gray-800" 
            squared
            @click="goDeleteApply()"
          >
            <span>삭제</span>
          </b-button>
        </div>
      </section>
    </div>
  </main>
</template>

<script src="@views.pc/nonemember/shop/as/AsDetail.js"/>