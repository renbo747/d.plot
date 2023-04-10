<template>
  <main class="cs-inquiry">
    <div class="inquiry-write">
      <div class="container">
        <section class="cs-inquiry-header">
          <header class="cs-inquiry-section__header">
            <h2 class="cs-section__title text-black font-weight-700 mb-0">
              {{text}}
            </h2>
          </header>
        </section>
        <section class="inquiry-first-section dp-panel pt-0">
          <header class="inquiry-write-header">
            <h2 class="section-title mb-0 text-black">
              문의유형
            </h2>
          </header>
          <div class="section-select">
            <base-dropdown
              v-model="SelectType"
              :options="Options"
              :placeholder="placeHolder"
              @change="OptionCheck"
            />
          </div>
        </section>
        <section class="inquiry-second-section dp-panel pt-0" 
                  v-if="$route.name.indexOf('write-inquiry-register') != -1 || $route.name.indexOf('write-inquiry-revise') != -1">
          <header class="inquiry-write-header">
            <div class="inquiry-write-header-top d-flex">
              <h2 class="section-title mb-0 text-black">
                주문상품 (선택)
              </h2>
              <div class="inquiry-write-btn__wrap"
                  @click="goSelectOrder">
                <b-button
                  class="dp-btn btn-h32 not-hover inquiry-write-btn"
                  variant="outline-gray-800"
                  squared
                >
                  <span class="dp-p-sm text-gray-800 font-weight-400">{{ $util.isEmpty(chkGoodsList) ? '주문상품선택' : '주문상품변경' }}</span>
                  <!-- <span class="dp-p-sm text-gray-800 font-weight-400">주문상품변경</span> -->
                </b-button>
              </div>
            </div>
            <div class="inquiry-write__items" v-if="!$util.isNull(orderNumber) && !$util.isEmpty(chkGoodsList)">
              <div>
                <p class="inquiry-write__items__name mb-0 text-black"
                    v-for="(goodslist, index) in chkGoodsList"
                    :key="index"
                >{{goodslist.goodsname}} <span v-if="!$util.isNull(goodslist.optionname)">({{goodslist.optionname}})</span></p>
              </div>
            </div>
          </header>
        </section>

        <section class="inquiry-second-section dp-panel pt-0" v-if="$route.name.indexOf('write-qna-register') != -1 || $route.name.indexOf('write-qna-revise') != -1">
          <header class="inquiry-write-header">
            <div class="inquiry-write-header-top d-flex">
              <h2 class="section-title mb-0 text-black">
                문의 상품
              </h2>
              <div class="inquiry-write-btn__wrap">
              </div>
            </div>
          </header>
          <div class="detail-product__info__qna">
              <div class="qna__body">
                <div class="inquiry-qna-body__thumbnail d-flex align-items-center">
                  <div class="inquiry-qna-body__thumbnail__img">
                    <div>
                      <product-thumbnail
                        class="mr-10 cursor"
                        style="width: 150px"
                        :thumbnail-info="qnaThumbnailData"
                        :key="Date.now()"
                      />
                    </div>
                  </div>
                  <div class="inquiry-qna-body__thumbnail__info">
                    <p
                      class="qna__body-text text-gray-700 font-weight-400 mb-0"
                    >
                    {{qnalist.goodsname}}
                    </p>
                    <div class="d-flex align-items-center">
                      <span v-if="!$util.isNull(qnalist.optionname)"
                        class="qna__body-text font-weight-400 text-gray-700"
                      >
                        옵션 : {{qnalist.optionname}}
                      </span>
                    </div>
                  </div>
                </div>
              </div>
          </div>          
        </section>


        <section class="inquiry-third-section dp-panel pt-0">
          <header class="inquiry-write-header">
            <h2 class="section-title mb-0 text-black">내용</h2>
          </header>
          <div class="cursor">
            <base-textarea
              v-model="content"
              placeholder="내용을 입력해주세요. (최대 500자)"
              :maxCount=500
              :isCount="true"
            ></base-textarea>
          </div>
        </section>
        <section class="inquiry-fourth-section" v-if="$route.name.indexOf('write-inquiry-register') != -1 || $route.name.indexOf('write-inquiry-revise') != -1">
          <header class="inquiry-write-header">
            <h2 class="section-title mb-0 text-black font-weight-700">
              이미지 / 동영상 첨부 (선택)
            </h2>
          </header>
          <div class="inquiry-fourth-section-slide">
            <div class="mb-20 cursor">
        <!-- <image-upload :is-mobile="false" :file-list="registerModalData" />-->              
              <image-upload ref="imageupload" class="dp-mb-20" @change="changeFile" :isMobile='false' />

            </div>
            <div>
              <ul class="ul-dot">
                <li>
                  <p
                    class="inquiry-write__items__name text-gray-700 mb-0 font-weight-400"
                  >
                    이미지는 10MB 이하, 동영상은 50MB 이하 파일을 이미지 최대
                    10개, 동영상 최대 1개까지 등록 가능합니다.
                  </p>
                </li>
                <li>
                  <p
                    class="inquiry-write__items__name text-gray-700 mb-0 font-weight-400"
                  >
                    문의하신 내용에 답변이 등록되면 회원정보의 이메일과
                    휴대전화로 안내해드립니다.
                  </p>
                </li>
              </ul>
            </div>
          </div>
        </section>
        <section class="inquiry-footer-section">
          <div class="footer-section-btn btn-group row">
            <div class="col-6">
              <router-link v-if="$route.name === 'write-inquiry-register' || $route.name === 'write-inquiry-revise'" to="/cs/inquiry/index/inquiry">
                <b-button class="dp-btn text-gray-800 inquiry-footer-section__btn" variant="outline-gray-800 not-hover">취소하기</b-button>
              </router-link>
              <router-link v-if="$route.name === 'mypage-write-inquiry-register' || $route.name === 'mypage-write-inquiry-revise'" to="/mypage/inquiry/index/inquiry">
                <b-button class="dp-btn text-gray-800 inquiry-footer-section__btn" variant="outline-gray-800 not-hover">취소하기</b-button>
              </router-link>
              <router-link v-if="$route.name === 'write-qna-register' || $route.name === 'write-qna-revise'" to="/cs/inquiry/index/qna">
                <b-button class="dp-btn text-gray-800 inquiry-footer-section__btn" variant="outline-gray-800 not-hover">취소하기</b-button>
              </router-link>
              <router-link v-if="$route.name === 'mypage-write-qna-register' || $route.name === 'mypage-write-qna-revise'" to="/mypage/inquiry/index/qna">
                <b-button class="dp-btn text-gray-800 inquiry-footer-section__btn" variant="outline-gray-800 not-hover">취소하기</b-button>
              </router-link>
            </div>
            <div class="col-6">
              <a v-if="$route.name.indexOf('write-inquiry-register') != -1" @click="registerInquiry()">
                <b-button class="dp-btn text-white inquiry-footer-section__btn" variant="gray-800">등록하기</b-button>
              </a>
              <a v-if="$route.name.indexOf('write-inquiry-revise') != -1" @click="registerInquiry()">
                <b-button class="dp-btn text-white inquiry-footer-section__btn" variant="gray-800">수정하기</b-button>
              </a>
              <a v-if="$route.name.indexOf('write-qna-register') != -1">
                <b-button class="dp-btn text-white inquiry-footer-section__btn" variant="gray-800">등록하기</b-button>
              </a>
              <a v-if="$route.name.indexOf('write-qna-revise') != -1" @click="reviseQna()">
                <b-button class="dp-btn text-white inquiry-footer-section__btn" variant="gray-800">수정하기</b-button>
              </a>
            </div>
          </div>
        </section>
      </div>
    </div>

  </main>
</template>

<script src="./Write.js"/>