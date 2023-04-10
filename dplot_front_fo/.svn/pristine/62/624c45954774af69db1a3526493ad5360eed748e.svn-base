<template>
  <div>
    <!-- 문의하기 Modal -->
    <b-modal
      id="WriteModal"
      modal-class="dp-modal page-layer inquiry-modal-layer"
      scrollable
      :hide-footer="true"
    >
      <!-- 문의 등록 Modal Header -->
      <template #modal-header="{}">
        <h5 class="modal-title">{{ text }}</h5>
        <i class="modal-close" @click="closeModal()">
          <img
            src="@assets.mobile/img/icon/icon-close-black-20px.svg"
            alt="닫기"
          />
        </i>
      </template>

      <!-- 문의 등록 Modal Body -->
      <div class="inquiry-body">
        <div>
          <section class="inquiry-first-section dp-panel pt-0">
            <header class="inquiry-modal-header">
              <h2 class="section-title mb-0 dp-p text-black font-weight-700">
                문의유형
              </h2>
            </header>
            <div class="section-select">
              <base-select
                v-model="SelectType"
                :options="Options"
                :placeholder="placeHolder"
                @input="OptionCheck"
              />
            </div>
          </section>
          <section
            class="inquiry-second-section dp-panel pt-0"
            v-if="$route.name === 'inquiry' || $route.name === 'detail-inquiry' || $route.name === 'cs-main'|| $route.name === 'cs-cs-cs-find-result'" 
          >
            <header class="inquiry-modal-header">
              <div class="second-section-header-top d-flex">
                <h2 class="section-title mb-0 dp-p text-black font-weight-700">
                  주문상품 (선택)
                </h2>
                <div class="second-section-btn">
                  <b-button
                    class="dp-btn btn-h32 not-hover"
                    variant="outline-gray-800"
                    @click="openProductModal()"
                    squared
                  >
                    <span
                      class="dp-p-sm text-gray-800 font-weight-400"
                      v-if="$util.isEmpty(chkGoodsList)"
                      >주문상품선택</span
                    >
                    <span class="dp-p-sm text-gray-800 font-weight-400" v-else
                      >주문상품변경</span
                    >
                  </b-button>
                </div>
              </div>
              <div
                class="second-section__items"
                v-if="
                  !$util.isNull(orderNumber) && !$util.isEmpty(chkGoodsList)
                "
              >
                <div>
                  <p
                    class="mb-0 text-gray-700 font-weight-400 dp-p-sm"
                    v-for="(goodslist, index) in chkGoodsList"
                    :key="index"
                  >
                    {{ goodslist.goodsname }}
                    <span v-if="!$util.isNull(goodslist.optionname)"
                      >({{ goodslist.optionname }})</span
                    >
                  </p>
                </div>
              </div>
            </header>
          </section>

          <section
            class="inquiry-second-section dp-panel pt-0"
            v-if="$route.name === 'detail-qna'"
          >
            <header class="inquiry-write-header">
              <div class="inquiry-write-header-top d-flex">
                <h2 class="section-title mb-0 dp-p text-black font-weight-700">
                  문의 상품
                </h2>
                <div class="inquiry-write-btn__wrap"></div>
              </div>
            </header>
            <div class="detail-product__info__qna">
              <div class="qna__body">
                <div
                  class="inquiry-qna-body__thumbnail d-flex align-items-center"
                >
                  <div class="inquiry-qna-body__thumbnail__img">
                    <div>
                      <product-thumbnail
                        class="mr-10 cursor"
                        style="width: 150px"
                        :thumbnail-info="qnaPopThumbnailData"
                        :key="Date.now()"
                      />
                    </div>
                  </div>
                  <div class="inquiry-qna-body__thumbnail__info">
                    <p
                      class="qna__body-text text-gray-700 font-weight-400 mb-0"
                    >
                      {{ qnalist.goodsname }}
                    </p>
                    <div class="d-flex align-items-center">
                      <span v-if="!$util.isNull(qnalist.optionname)"
                        class="qna__body-text font-weight-400 text-gray-700"
                      >
                        옵션 : {{ qnalist.optionname }}
                      </span>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </section>
          <section class="inquiry-third-section dp-panel pt-0">
            <header class="inquiry-modal-header">
              <h2 class="section-title mb-0 dp-p text-black font-weight-700">
                내용
              </h2>
            </header>
            <div>
              <base-textarea
                v-model="content"
                placeholder="내용을 입력해주세요. (최대 500자)"
                :maxCount=500
              ></base-textarea>
            </div>
          </section>
          <section
            class="inquiry-fourth-section dp-panel pt-0"
            v-if="$route.name === 'detail-inquiry' || $route.name === 'inquiry' || $route.name === 'cs-main' || $route.name === 'cs-cs-find-result'"
          >
            <header class="inquiry-modal-header">
              <h2 class="section-title mb-0 dp-p text-black font-weight-700">
                이미지 / 동영상 첨부 (선택)
              </h2>
            </header>
            <div class="inquiry-fourth-section-slide">
              <div class="mb-20">
                <image-upload
                  ref="imageupload"
                  class="dp-mb-10"
                  @change="changeFiles"
                  :isMobile="true"
                />
              </div>
              <div>
                <ul class="ul-dot">
                  <li class="">
                    <p class="dp-p-sm text-gray-700 mb-0 font-weight-400">
                      이미지는 10MB 이하, 동영상은 50MB 이하 파일을 이미지 최대
                      10개, 동영상 최대 1개까지 등록 가능합니다.
                    </p>
                  </li>
                  <li>
                    <p class="dp-p-sm text-gray-700 mb-0 font-weight-400">
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
              <b-button
                class="dp-btn text-gray-800 login-btn__close col-6"
                variant="outline-gray-800 not-hover"
                block
                @click="closeModal()"
                >닫기</b-button
              >
              <b-button
                v-if="$route.name === 'inquiry' || $route.name === 'cs-main' || $route.name === 'cs-cs-find-result'"
                class="dp-btn text-white login-btn__confirm col-6"
                variant="gray-800"
                @click="registerInquiry()"
                >등록하기</b-button
              >
              <b-button
                v-if="$route.name === 'detail-inquiry'"
                class="dp-btn text-white login-btn__confirm col-6"
                variant="gray-800"
                @click="registerInquiry()"
                >수정하기</b-button
              >
              <b-button
                v-if="$route.name === 'detail-qna'"
                class="dp-btn text-white login-btn__confirm col-6"
                variant="gray-800"
                @click="reviseQna()"
                >수정하기</b-button
              >
            </div>
          </section>
        </div>
      </div>
    </b-modal>
    <!-- // 문의 등록 Modal -->
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

<script src="@views.mobile/cs/inquiry/popup/WriteModal.js"/>

<style>
</style>