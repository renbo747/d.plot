<template>
  <main class="cs-faq cs-main">
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
            v-model="searchKeyword"
            @input="handleInput"
            @search="goToFaq"
            @isEnter="isEnter(searchKeyword)"
            :is-mobile="false"
            style="cursor: pointer"
          />
        </div>
      </section>
      <!-- 자주하는 질문 카테고리 필터 -->
      <section class="dp-panel question-filter pb-0">
        <div>
          <ul class="list-style-none question__ul">
            <li
              v-for="(list, index) in questionData"
              :key="index"
              class="cursor"
            >
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
                <span class="label__item">{{ list.label }}</span>
              </label>
            </li>
          </ul>
        </div>
      </section>
      <!-- 자주하는 질문 회원/멤버 카테고리 -->
      <section class="question-category">
        <header class="question-category-header">
          <h2 class="question-category__title text-black font-weight-500 mb-0">
            {{faq}}
          </h2>
        </header>
        <div class="question-category-body">
          <ul class="dp-accordion question-category-accordion list-style-none">
            <li
              class="dp-accordion-list cursor"
              v-for="(list, index) in faqData"
              :key="index"
            >
              <div class="accordion-item">
                <div class="accordion__header" v-b-toggle="`accordion-0${index}`"
                    @click="faqHit(list.idx,index)">
                  <span class="header__text" style="word-break: break-all;">[{{list.faqtypename}}] {{ list.subject }}</span>
                  <i
                    class="dp-icon icon-arrow-down md"
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
        <div class="question-category-pagination d-flex">
          <div class="question-category-pagination-wrap">
            <base-pagination :currentPage="currentPage" :totalPage="totalPage" :listTotal="listTotal" :listCnt="listCnt" @changePage="changePage"/>
          </div>
        </div>
      </section>
    </div>
  </main>
</template>

<script src="@views.mobile/cs/CsFaq.js"/>