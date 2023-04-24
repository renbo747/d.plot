<template>
  <main class="dp-as-detail">
    <div class="container">
      <section class="as-detail__header">
        <div class="d-flex">
          <div>
            <span class="dp-p-sm font-weight-500">AS전용 문의 접수일</span>
            <span class="dp-p-sm apply-date font-weight-500">{{product.regdate}}</span>
          </div>
          <span class="dp-bar h10 mt-2"></span>
          <span class="dp-p-sm apply-number">{{product.ordno}}</span>
        </div>
      </section>
      <hr class="dp-hr justify-margin" />
      <section class="as-detail__shipping-product dp-section">
        <div class="as-product__area">
          <div class="as-product__header">
            <p class="mb-0 dp-caption as-product__header__title">
              {{product.brandname}}
            </p>
          </div>
          <hr class="dp-hr h1" />
          <div class="apply__product-info__area">
            <product-order
              :key="Date.now()"
              :product-info="product"
              :isSummary="true"
              :isHeader="false"
              :isGuarantee="false"
              :isFooter="false"
              :isBadge="false"
              :to="{ name: 'shop-detail', params: { pid: product.goodscode } }"
            />
          </div>
        </div>
      </section>
      <hr class="dp-hr justify-margin" />
      <section class="as-center__guide__area dp-section">
        <div class="as-center__guide">
          <div class="as-center__guide__header">
            <p class="mb-0 as-center__guide__header__title dp-p-sm">
              협력사 AS 센터안내
            </p>
          </div>
          <div class="as-center__guide__body">
            <div class="d-flex justify-content-between align-items-center">
              <div class="d-flex flex-column">
                <div class="seller-name__area">
                  <span class="dp-caption seller-name">{{product.dealernm}}</span>
                </div>
                <div class="seller-phone__area">
                  <span class="atten-new seller-phone" v-html="$util.maskTel(product.dealertel)"></span>
                </div>
              </div>
              <div>
                <b-button
                  class="dp-btn dp-btn-icon not-hover btn-h30 phone-btn"
                  variant="outline-gray-400"
                  squared
                  pill
                >
                  <i class="dp-icon icon-phone"></i>
                  <span class="text-black">전화하기</span>
                </b-button>
              </div>
            </div>
          </div>
          <div class="as-center__guide__footer">
            <p class="mb-0 dp-caption">
              협력사 AS센터 이용 시 보다 빠른 안내가능가 가능합니다.
            </p>
          </div>
        </div>
      </section>
      <hr class="dp-hr justify-margin" />
      <section class="as__apply-info__area dp-section">
        <div class="as__apply-info">
          <div class="as__apply-info__header">
            <p class="mb-0 dp-p">AS전용 문의 접수정보</p>
          </div>
          <hr class="dp-hr h1" />
          <div class="as__apply-info__body">
            <div class="customer-info__area">
              <ul class="list-style-none customer-info__ul">
                <li v-for="(list, index) in customerInfo" :key="index">
                  <div class="d-flex">
                    <span class="customer-info__key dp-p-sm">{{
                      list.infoKey
                    }}</span>
                    <span class="customer-info__value dp-p-sm">{{
                      list.infoValue
                    }}</span>
                  </div>
                </li>
              </ul>
            </div>
          </div>
        </div>
      </section>
      <hr class="dp-hr justify-margin" />
      <section class="as__content__area dp-section">
        <div class="as__content">
          <div class="as__content__header">
            <p class="mb-0 as__content__header__title dp-p font-weight-500">
              AS 접수내용
            </p>
          </div>
          <hr class="dp-hr h1" />
          <div class="as__content__body">
            <div class="as__content__text__area">
              <p class="mb-0 dp-p-sm as__content__text" v-html="product.content">
              </p>
            </div>
            <div class="detail-product__info__slide">
              <div class="product__info__slide__info dp-panel">
                <swiper class="swiper" ref="swiper" :options="swiperDetailOption" v-if="files.length > 0">
                  <swiper-slide v-for="(list, index) in files" :key="index">
                    <div class="info__slide__img"  @click="openModal(files,index)">
                      <figure
                        class="info__slide__img__thumbnail"
                      >
                        <img :src="list.filetype == 'FLT001'? list.fullpath:$util.changeFileType(list.fullpath, '.jpg')" />
                        <i class="dp-icon icon-video-play xl" style="transform: translate(-50%, -50%); position: absolute; top: 50%; left: 50%;" v-if="list.filetype == 'FLT002'"></i>
                      </figure>
                    </div>
                  </swiper-slide>
                </swiper>
              </div>
            </div>
            <hr class="dp-hr h1" />
            <div class="answer__content__area">
              <div class="answer__content__header">
                <div>
                  <span class="dp-caption font-weight-bold">답변내용</span>
                  <span v-if="product.isreply == 'T'" class="dp-caption answer-date">{{product.repregdate}}</span>
                </div>
              </div>
              <div class="answer__content__body">
                <div class="answer__content">
                  <p v-if="product.isreply == 'F'" class="mb-0 dp-p-sm">
                    안녕하세요. <br />
                    신청 접수가 완료되었습니다. <br />빠른 시간안에 연락
                    드리겠습니다.
                  </p>
                  <div v-if="product.isreply == 'T'" v-html="product.repcontent" class="mb-0 dp-p-sm">
                  </div>
                  <p v-if="product.isreply == 'T' && !$util.isNull(product.repuserno)" class="mb-0 manager-name dp-caption">
                    전담매니저 : {{product.repuserno}}
                  </p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>
      <section class="as-detail__btn__area">
        <div class="btn-group d-flex">
          <b-button
            v-if="product.isreply == 'F'"          
            class="dp-btn not-hover"
            variant="outline-gray-800"
            squared
            @click="goToaApplyAs()"
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

<script src="@views.mobile/nonemember/shop/as/AsDetail.js"/>