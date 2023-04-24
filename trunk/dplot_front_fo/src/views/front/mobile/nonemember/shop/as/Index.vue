<template>
  <main class="dp-as">
    <div class="container">
      <div class="as-section section01">
        <p class="item-name dp-mb-20"></p>
        <product-order
          :key="Date.now()"
          :is-cart="true"
          :is-status="false"
          :is-title="false"
          :is-option="true"
          :is-select-count="product.ordcnt > 1"
          :product-info="product"
        />
      </div>
      <hr class="dp-hr justify-margin" />
      <div class="as-section as-center__guide">
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
      <hr class="dp-hr justify-margin" />
      <div class="as-section as-form">
        <div class="dp-mb-30">
          <p class="as-form__info">
            AS전용 문의완료 시, 전담 매니저가 고객님께 전화를 드려 상세한 안내를
            해드립니다.
          </p>
          <ul class="as-info__list__wrap list-style-none">
            <!-- 접수자 -->
            <li class="as-info__list">
              <form>
                <div class="form-group">
                  <base-input-horizon
                    :is-label="true"
                    label="접수자"
                    v-model="wirter"
                    placeholder="이름을 입력해주세요"
                  />
                </div>
              </form>
            </li>
            <!-- 연락처 -->
            <li class="as-info__list">
              <form>
                <div class="form-group">
                  <base-input-horizon
                    :is-label="true"
                    label="연락처"
                    v-model="tel"
                    placeholder="휴대전화 번호 - 없이 입력"
                  />
                </div>
              </form>
            </li>
            <!-- 주소 -->
            <li class="as-info__list as-info__address">
              <form>
                <div class="form-group d-flex dp-mb-10">
                  <div class="as-info__adderss__input">
                    <base-input-horizon
                      :key="skey"
                      :is-label="true"
                      label="주소"
                      :is-readonly="true"
                      v-model="address.post"                      
                      placeholder=""
                    />
                  </div>
                  <div class="as-info__address__btn">
                    <b-button
                      @click="execDaumPostModal()"
                      class="dp-btn text-white"
                      variant="gray-800"
                      squared
                    >
                      <span class="dp-p-sm">우편번호 찾기</span>
                    </b-button>
                  </div>
                </div>
                <div class="form-group dp-mb-10">
                  <base-input
                    :key="skey"
                    placeholder=""
                    v-model="address.addrroad"
                  />
                </div>
                <div class="as-info__address__list">
                  <base-input
                    :key="skey"                  
                    placeholder="상세주소 입력"
                    v-model="address.detail"
                    @input="addressInput2"
                  />
                </div>
              </form>
            </li>
          </ul>
        </div>
        <div class="dp-mb-30">
          <p class="as-section__title font-weight-500">AS 접수내용</p>
          <base-textarea
            class="as-textarea textarea-h-320"
            placeholder="제품 고장 및 이상에 대한 내용을 입력해주세요 "
            :is-count="true"
            :max-count="5000"
            v-model="content"
          ></base-textarea>
        </div>
        <div class="dp-mb-20">
          <p class="as-section__title">이미지 등록</p>
          <image-upload ref="imageupload" class="dp-mb-20" @change="changeFile" :isMobile='true' />
          <ul class="ul-dot">
            <li>
              이미지는 10MB 이하, 동영상은 50MB 이하 파일을 이미지 최대 10개,
              동영상 최대 1개까지 등록 가능합니다.
            </li>
          </ul>
        </div>
      </div>
      <div class="btn-group as-footer__btn">
        <b-button
          class="dp-btn back-btn"
          variant="outline-gray-800"
          squared
          block
          @click="goBack()"
        >
          <span>뒤로</span>
        </b-button>
        <b-button
          @click="applyComplt()"        
          class="dp-btn text-white success-apply-btn"
          variant="gray-800"
          squared
        >
          <span>{{btntext}}</span>
        </b-button>
      </div>
    </div>

    <!-- as신청완료 모달 -->
    <b-modal
      id="asApplySuccess"
      modal-class="dp-modal page-layer as-apply-success-modal"
      hide-footer
      scrollable
    >
      <!-- as신청완료 모달 헤더 -->
      <template #modal-header="{}">
        <h5 class="modal-title">AS 신청완료</h5>
        <i class="modal-close" @click="goListPage()">
          <img
            src="@assets.mobile/img/icon/icon-close-black-20px.svg"
            alt="닫기"
          />
        </i>
      </template>

      <!-- as신청완료 모달 바디 -->
      <div class="page-layer__body">
        <div class="d-flex align-items-center flex-column">
          <div class="icon-section">
            <i class="dp-icon xl02 check-circle-icon"></i>
          </div>
          <div class="text-section">
            <h2 class="as-modal__h2">AS전용 문의가 완료되었습니다.</h2>
            <p class="as-modal__p">
              수리비용 및 소요기간 등 AS 관련한 <br />
              자세한 사항은 전담매니저 확인 후 <br />
              별도 안내해드립니다.
            </p>
          </div>
          <b-button
            class="dp-btn text-white btn-h38 as-ok-btn"
            variant="gray-800"
            squared
            @click="goListPage()"
          >
            <span>확인</span>
          </b-button>
        </div>
      </div>
    </b-modal>
    <!-- // as신청완료 모달 -->
  </main>
</template>


<script src="@views.mobile/nonemember/shop/as/Index.js"/>