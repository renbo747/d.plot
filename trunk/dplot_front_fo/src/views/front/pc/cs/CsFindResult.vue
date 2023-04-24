<template>
  <main class="cs-faq">
    <div class="container">
      <!-- 헤더 -->
      <section class="cs-faq-header">
        <header class="cs-section__header">
          <h2 class="cs-section__title text-black font-weight-700 mb-0">
            자주하는 질문
          </h2>
        </header>
      </section>
      <!-- 검색창 -->
      <section class="cs-faq__search dp-panel pb-0">
        <div class="search__input">
          <base-search
            placeholder="궁금한 점을 검색해보세요"
            v-model="inputKeyword"
            @input="handleInput"
            @search="goToFaq"
            @isEnter="isEnter(inputKeyword)"
            :is-mobile="false"
            style="cursor: pointer"
          />
        </div>
      </section>
      <div v-if="isFindSuccess">
        <section class="dp-panel cs-faq-find-result__wrap pb-0">
          <div class="cs-faq__total">
            <p class="cs-faq__total-text mb-0 font-weight-500 text-gray-700">
              <span class="text-primary mr-1">{{ this.$route.query.searchKeyword }}</span>검색결과 총 {{ listTotal }}개
            </p>
          </div>
          <div class="cs-faq__result mb-0">
            <ul class="dp-accordion list-style-none">
              <li v-for="(list, index) in faqData" :key="index">
                <div class="accordion-item">
                  <div class="accordion__header">
                    <div class="header__text" v-html="list.subject" style="word-break: break-all;"></div>
                    <i
                      class="dp-icon icon-arrow-down sm02 cursor"
                      v-b-toggle="`accordion-0${index}`"
                    ></i>
                  </div>
                  <b-collapse
                    :id="`accordion-0${index}`"
                    accordion="gnb-accordion"
                    tag="div"
                  >
                    <div class="accordion__body">
                      <div v-html="list.content"></div>
                    </div>
                  </b-collapse>
                </div>
              </li>
            </ul>

          </div>
          <div class="question-category-pagination dp-panel pb-80 d-flex">
            <div class="question-category-pagination-wrap">
              <base-pagination :currentPage="currentPage" :totalPage="totalPage" :listTotal="listTotal" :listCnt="listCnt" @changePage="changePage"/>
            </div>
          </div>
        </section>
        <section class="cs-faq__btn d-flex">
          <div class="cs-faq__btn__total">
            <p class="mb-0 font-weight-400 text-gray-700 dp-p">
              원하시는 정보를 찾지 못하셨다면, 1:1 문의나 채팅상담을
              이용해주세요.
            </p>
          </div>
          <div class="cs-faq__btn_inquiry">
            <b-button
              class="cs-faq__btn_inquiry-item dp-btn not-hover cursor"
              variant="outline-black"
              squared
              @click="goToWrite()"
            >
              <span class="text-gray-800 dp-p font-weight-400"
                >1:1 문의하기</span
              >
            </b-button>
          </div>
        </section>
      </div>
      <div v-else>
        <div class="cs-faq-find-result__empty">
          <div>
            <div class="d-flex flex-column align-items-center">
              <div class="cs-faq-find__fail">
                <i class="dp-icon xl02 icon-not-found"></i>
              </div>
              <div class="dp-panel">
                <div class="cs-faq__total-empty">
                  <p class="cs-faq__total-p mb-0 font-weight-400 text-gray-700">
                    검색 결과가 없습니다.<br />
                    원하시는 정보를 찾지 못하셨다면, 1:1 문의나 채팅상담을
                    이용해주세요.
                  </p>
                </div>
              </div>
            </div>
          </div>
        </div>
        <section class="cs-faq__btn d-flex">
          <div class="cs-faq__btn__total">
            <p class="mb-0 font-weight-400 text-gray-700 dp-p">
              원하시는 정보를 찾지 못하셨다면, 1:1 문의나 채팅상담을
              이용해주세요.
            </p>
          </div>
          <div class="cs-faq__btn_inquiry">
            <b-button
              class="cs-faq__btn_inquiry-item dp-btn not-hover cursor"
              variant="outline-black"
              squared
              @click="goToWrite()"
            >
              <span class="text-gray-800 dp-p font-weight-400"
                >1:1 문의하기</span
              >
            </b-button>
          </div>
        </section>
        <section style="margin-top:50px" class="question-filter">
          <header class="question-category-header mb-20">
            <h2
              class="question-category__title text-black font-weight-500 dp-p"
            >
              질문 분류로 찾기
            </h2>
          </header>          
          <div>
            <ul class="list-style-none question__ul">
              <li v-for="(list, index) in questionData" :key="index">
                <label :for="list.id" class="question__label mb-0">
                  <input
                    :id="list.id"
                    type="radio"
                    name="questionList"
                    value="label"
                    :checked="list.checked"
                    @click="changeFaq(list.id)"
                    hidden
                  />
                  <span class="label__item is-fail">{{ list.label }}</span>
                </label>
              </li>
            </ul>
          </div>
        </section>
        
      </div>
    </div>
  </main>
</template>

<script src="@views.mobile/cs/CsFindResult.js"/>