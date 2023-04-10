<template>
  <main class="cs-inquiry">
    <section class="cs-inquiry-tab__wrap">
      <!-- 헤더 -->
      <section class="cs-inquiry-header">
        <header class="cs-inquiry-section__header">
          <h2 class="cs-section__title text-black font-weight-700 mb-0">
            나의 문의
          </h2>
        </header>
      </section>

      <div class="cs-inquiry-tabs">
        <b-tabs>
          <!-- 1:1 문의 탭 -->
          <b-tab title="나의 문의" :active="isInquiry === true"  @click="changeTap('isInquiry')">
            <div v-if="isInquiry" class="inquiry__area">
              <div class="container" v-if="isInquiryList">
                <div class="cs-inquiry__header">
                  <div class="cs-inquiry__header__btn d-flex">
                    <div class="cs-inquiry__header__total">
                      <p
                        class="cs-inquiry__btn__total mb-0 font-weight-400 text-gray-700"
                      >
                        1:1 상담을 통해 접수된 문의내역입니다.<br />
                        추가로 문의하실 사항이 있으시면, 문의하기 또는 채팅
                        상담을 이용해주세요.
                      </p>
                    </div>
                    <div>
                      <router-link to="/cs/inquiry/write">
                        <b-button
                          class="cs-inquiry__button dp-btn not-hover"
                          variant="outline-black"
                          squared
                        >
                          <i class="dp-icon icon-inquiry sm02 mr-1"></i>
                          <span
                            class="cs-inquiry__header-title text-black font-weight-400"
                            >문의하기</span
                          >
                        </b-button>
                      </router-link>
                    </div>
                  </div>
                  <div class="cs-inquiry__header__select row">
                    <div class="col-6">
                      <base-dropdown
                        placeholder="최근 1개월"
                        class="inquiry-select"
                        v-model="lastMonthSelect"
                        :options="inquiryPeriodSelect"
                        @change="dataCheck"
                      />
                    </div>
                    <div class="col-6">
                      <base-dropdown
                        placeholder="전체문의"
                        class="inquiry-select"
                        v-model="typeSelect"
                        :options="inquiryTotalSelect"
                        @change="dataCheck"
                      />
                    </div>
                  </div>
                </div>
                <div class="cs-inquiry__list dp-panel pb-0">
                  <ul class="cs-inquiry__list__ul list-style-none">
                    <li
                      v-for="(list, index) in inquiryListData"
                      :key="index"
                      class="cs-inquiry__list__item cursor"
                    >
                      <div class="d-flex inquiry__list__item">
                        <div class="inquiry__list__item__box d-flex">
                          <p
                            class="inquiry__list__item__state mb-0 font-weight-700 text-black mr-10 mb-2"
                          >
                            {{ list.isreply }}
                          </p>
                          <p
                            class="mb-0 inquiry__list__item__date text-gray-700 font-weight-400"
                          >
                            {{ list.regdate }}
                          </p>
                        </div>
                        <div>
                          <p
                            class="mb-0 inquiry__list__item__text text-gray-700 font-weight-400"
                            @click="goToInquiryDetail(list.idx)"
                          >
                            {{ list.content }}
                          </p>
                        </div>
                      </div>
                    </li>
                  </ul>
                  <div class="inquiry-pagination dp-panel pb-0 d-flex">
                    <div class="inquiry-pagination-wrap">
                      <base-pagination :currentPage="currentPage" :totalPage="totalPage" :listTotal="listTotal" :listCnt="listCnt" @changePage="changePage"/>
                    </div>
                  </div>
                </div>
              </div>
              <div class="container" v-else>
                <div class="cs-inquiry__list__none">
                  <div class="cs-inquiry__list__circle">
                    <div class="d-flex flex-column align-items-center">
                      <div class="cs-inquiry__list__empty">
                        <i class="dp-icon xl02 icon-not-found"></i>
                      </div>
                      <div class="cs-inquiry__list__text">
                        <div class="cs-inquiry__list__empty__text">
                          <p
                            class="cs-inquiry__list__empty__text__p mb-0 font-weight-400 text-gray-700"
                          >
                            등록된 문의 내역이 없습니다.
                          </p>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </b-tab>
          <!-- 상품Q&A 탭 -->
          <b-tab title="상품Q&A" :active="isInquiry === false" @click="changeTap('isQna')">
            <div class="cs-product-qna__tab">
              <div class="container" v-if="isQna">
                <div class="cs-product-qna dp-panel pb-0">
                  <div class="cs-product-qna-header dp-panel pt-0">
                    <div class="cs-product-qna__header__text">
                      <header class="qna__header__text">
                        <p class="qna__header__text__p mb-0 text-gray-700">
                          상품Q&A를 통해 접수된 문의 내역입니다.
                        </p>
                      </header>
                    </div>
                    <div class="cs-product-qna__header__select row">
                      <div class="col-6">
                        <base-dropdown
                          placeholder="최근 1개월"
                          class="inquiry-select"
                          v-model="lastMonthSelect"
                          :options="inquiryPeriodSelect"
                          @change="dataCheck"
                        />
                      </div>
                      <div class="col-6">
                        <base-dropdown
                          placeholder="전체문의"
                          class="inquiry-select"
                          v-model="typeSelect"
                          :options="inquiryTotalSelect"
                          @change="dataCheck"
                        />
                      </div>
                    </div>
                  </div>
                </div>
                <div class="cs-qna-list-wrap">
                  <ul class="cs-qna-list-ul list-style-none">
                    <li
                      v-for="(item, index) in qnaListData"
                      :key="index"
                      class="cs-qna-list__item cursor"
                      @change="handleIcon(item.id)"
                    >
                      <div class="cs-qna-list__item__state d-flex mb-20">
                        <p
                          class="cs-qna-list__item__state__text mb-0 font-weight-700 text-black mr-10"
                        >
                          {{ item.state }}
                        </p>
                        <p
                          class="cs-qna-list__item__state__date mb-0 text-gray-700 font-weight-400"
                        >
                          {{ item.date }}
                        </p>
                        <div class="cs-qna-list__item__state__icon">
                          <i
                            class="dp-icon h22 icon-lock ml-2"
                            v-if="item.id === 'qna1' || item.id === 'qna3'"
                          ></i>
                        </div>
                      </div>
                      <div class="cs-qna-list__item__info d-flex">
                        <div class="cs-qna-list__item__thumbnail">
                          <product-thumbnail
                            style="width: 150px"
                            :is-disabled="true"
                            :thumbnail-info="{
                              id: item.id,
                              fullpath: item.fullpath,
                            }"
                          />
                        </div>
                        <div
                          class="cs-qna-list__item__text d-flex"
                          @click="goToInquiryQna(item.idx)"
                        >
                          <header class="qna-item__title mb-10">
                            <h2
                              class="qna-item__title__h2 font-weight-700 text-gray-800 mb-0"
                            >
                              
                            </h2>
                          </header>
                          <div class="qna-item__text">
                            <p
                              class="qna-item__text__p text-gray-700 font-weight-400 mb-0"
                              v-html="item.text"
                            ></p>
                          </div>
                        </div>
                      </div>
                    </li>
                  </ul>
                  <div class="inquiry-pagination dp-panel pb-0 d-flex">
                    <div class="inquiry-pagination-wrap">
                      <base-pagination :currentPage="currentPage" :totalPage="totalPage" :listTotal="listTotal" :listCnt="listCnt" @changePage="changePage"/>
                    </div>
                  </div>
                </div>
              </div>
              <div class="container" v-else>
                <div class="cs-inquiry__list__none">
                  <div class="cs-inquiry__list__circle">
                    <div class="d-flex flex-column align-items-center">
                      <div class="cs-inquiry__list__empty">
                        <i class="dp-icon xl02 icon-not-found"></i>
                      </div>
                      <div class="cs-inquiry__list__text">
                        <div class="cs-inquiry__list__empty__text">
                          <p
                            class="cs-inquiry__list__empty__text__p mb-0 font-weight-400 text-gray-700"
                          >
                            등록된 문의 내역이 없습니다.
                          </p>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </b-tab>
        </b-tabs>
      </div>
    </section>
  </main>
</template>

<script src="@views.mobile/cs/inquiry/Index.js"/>