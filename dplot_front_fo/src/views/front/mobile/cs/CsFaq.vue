<template>
  <main class="cs-faq cs-main">
    <div class="container">
      <!-- 검색창 -->
      <section class="cs-faq__search dp-panel pb-0">
        <div class="search__input">
          <base-search
            placeholder="궁금한 점을 검색해보세요"
            v-model="searchKeyword"
            @input="handleInput"
            @search="goToFaq"
            @isEnter="isEnter(searchKeyword)"
          />
        </div>
      </section>
      <!-- 자주하는 질문 카테고리 필터 -->
      <section class="dp-panel question-filter">
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
                <span class="label__item">{{ list.label }}</span>
              </label>
            </li>
          </ul>
        </div>
      </section>
      <!-- 자주하는 질문 회원/멤버 카테고리 -->
      <section class="dp-panel question-category pt-0">
        <header class="question-category-header mb-20">
          <h2 class="question-category__title text-black font-weight-500 dp-p">
            {{ faq }}
          </h2>
        </header>
        <div class="question-category-body">
          <ul class="dp-accordion list-style-none">
            <li v-for="(list, index) in faqData" :key="index">
              <div class="accordion-item">
                <div
                  class="accordion__header"
                  v-b-toggle="`accordion-0${index}`"
                    @click="faqHit(list.idx,index)"
                >
                  <span class="header__text" style="word-break: break-all;">[{{list.faqtypename}}] {{ list.subject }}</span>
                  <i class="dp-icon icon-arrow-down sm02"></i>
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
      </section>

      <infinite-loading
        :identifier="infiniteId"
        @infinite="infiniteHandler"
        spinner="circles"
      >
        <div slot="no-more"></div>
        <div slot="no-results"><p class="text-center dp-p-sm"></p></div>
      </infinite-loading>
    </div>
  </main>
</template>


<script src="@views.mobile/cs/CsFaq.js"/>