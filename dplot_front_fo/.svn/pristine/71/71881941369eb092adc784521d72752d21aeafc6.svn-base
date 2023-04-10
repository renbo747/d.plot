<template>
  <main class="cs-inquiry">
    <section class="cs-inquiry-tab__wrap">
      <div class="cs-inquiry-tabs">
        <b-tabs>
          <!-- 1:1 문의 탭 -->
          <b-tab title="1:1 문의"
            :active="$route.name === 'inquiry'" 
            @click="changeTap('inquiry')">
            <div v-if="$route.name === 'inquiry'" class="inquiry__area">
              <div class="container">
                <div class="cs-inquiry__header">
                  <div class="cs-inquiry__header__btn">
                    <b-button
                      class="dp-btn not-hover"
                      variant="outline-black"
                      squared
                       @click="openWriteModal()"
                    >
                      <i class="dp-icon icon-inquiry sm02 mr-1"></i>
                      <span class="text-black dp-p-sm font-weight-400">1:1 문의하기</span
                      >
                    </b-button>
                  </div>
                  <div class="cs-inquiry__header__select row">
                    <div class="col-6">
                      <base-select
                        placeholder="최근 1개월"
                        class="inquiry-select"
                        v-model="monthSelect"
                        :options="monthOption"
                        @input="iqDateCheck"
                      />
                    </div>
                    <div class="col-6">
                      <base-select
                        placeholder="전체문의"
                        class="inquiry-select"
                        v-model="inquirySelect"
                        :options="inquiryOption"
                        @input="inquiryCheck"
                      />
                    </div>
                  </div>
                </div>
                <div class="cs-inquiry__list dp-panel" v-if="!$util.isEmpty(inquiryList)">
                  <ul class="cs-inquiry__list__ul list-style-none">
                    <li
                      v-for="(list, index) in inquiryList"
                      :key="index"
                      class="cs-inquiry__list__item"
                      @click="goInqDetail(list.idx)"
                    >
                      <div class="d-flex inquiry__list__item">
                        <div class="d-flex">
                          <p
                            class="mb-0 dp-caption font-weight-700 mr-10 mb-2"
                          :class="{
                            'text-secondary': list.isreply === '답변대기',
                            'text-primary': list.isreply === '답변완료',
                          }"
                          >
                            {{ list.isreply }}
                          </p>
                          <p
                            class="mb-0 dp-caption text-gray-700 font-weight-400"
                          >
                            {{ list.regdate }}
                          </p>
                        </div>
                        <div>
                          <p
                            style="text-overflow:ellipsis; overflow:hidden;"
                            class="mb-0 dp-p-sm text-gray-700 font-weight-400"
                          >
                            [{{list.inquirytypename}}] <span v-html="list.content"></span>
                          </p>
                        </div>
                      </div>
                    </li>
                  </ul>
                </div>
                 <infinite-loading :identifier="infiniteId" @infinite="iqInfiniteHandler" spinner="circles">
                    <div slot="no-more"></div>
                    <div slot="no-results">
                      <div class="cs-inquiry__list__none" v-if="$util.isEmpty(inquiryList) && isEnd">
                        <div class="cs-inquiry__list__circle">
                          <div class="d-flex flex-column align-items-center">
                            <div class="cs-inquiry__list__empty">
                              <i class="dp-icon xl02 icon-not-found"></i>
                            </div>
                            <div class="cs-inquiry__list__text">
                              <div class="cs-inquiry__list__empty__text">
                                <p class="mb-0 font-weight-400 text-gray-700 dp-p-sm">
                                  등록된 문의 내역이 없습니다.
                                </p>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </infinite-loading>
              </div>
            </div>
          </b-tab>
          <!-- 상품Q&A 탭 -->
          <b-tab title="상품Q&A" :active="$route.name === 'qna'" @click="changeTap('qna')">
            <div class="cs-product-qna__tab">
              <div class="container" v-if="$route.name === 'qna'">
                <div class="cs-product-qna dp-panel">
                  <div class="cs-product-qna__header__select row">
                    <div class="col-6">
                      <base-select
                        placeholder="최근 1개월"
                        class="inquiry-select"
                        v-model="monthSelect"
                        :options="monthOption"
                        @input="qnaDateCheck"
                      />
                    </div>
                    <div class="col-6">
                      <base-select
                        placeholder="전체문의"
                        class="inquiry-select"
                        v-model="qnaTypeSelect"
                        :options="qnaOptions"
                        @input="qnaCheck"
                      />
                    </div>
                  </div>
                </div>
                <div class="cs-qna-list-wrap" v-if="!$util.isEmpty(qnaList)">
                  <ul class="cs-qna-list-ul list-style-none">
                    <li
                      v-for="(item, index) in qnaList"
                      :key="index"
                      class="cs-qna-list__item"
                      @change="handleIcon(item.issecret)"
                      @click="goQnaDetail(item.idx)"

                    >
                      <div class="cs-qna-list__item__state d-flex mb-2">
                        <p
                          class="mb-0 dp-caption font-weight-700 mr-10 mb-0"
                          :class="{
                            'text-secondary': item.isreply === '답변대기',
                            'text-primary': item.isreply === '답변완료',
                          }"
                        >
                          {{ item.isreply }}
                        </p>
                        <p
                          class="mb-0 dp-caption text-gray-700 font-weight-400"
                        >
                          {{ item.regdate }}
                        </p>
                        <div>
                          <i
                            class="dp-icon sm icon-lock ml-2"
                            v-if="item.issecret === 'T'"
                          ></i>
                        </div>
                      </div>
                      <div class="cs-qna-list__item__info d-flex">
                        <div class="cs-qna-list__item__thumbnail">
                          <product-thumbnail
                            style="width: 100px"
                            :is-disabled="true"
                            :thumbnail-info="{
                              id: item.idx,
                              fullpath: item.fullpath,
                            }"
                          />
                        </div>
                        <div
                          class="cs-qna-list__item__text d-flex"
                        >
                          <header class="qna-item__title">
                            <h2
                              class="dp-p-sm font-weight-700 text-gray-800 mb-2"
                            >
                              {{ item.subject }}
                            </h2>
                          </header>
                          <div class="qna-item__text">
                            <p
                              style="text-overflow:ellipsis; overflow:hidden;"
                              class="dp-p-sm text-gray-700 font-weight-400 mb-0"
                              v-html="'[' + item.qnatypename + '] ' + item.content"
                            ></p>
                          </div>
                        </div>
                      </div>
                    </li>
                  </ul>
                </div>
                <infinite-loading :identifier="infiniteId" @infinite="qnaInfiniteHandler" spinner="circles">
                    <div slot="no-more"></div>
                    <div slot="no-results">
                      <div class="cs-inquiry__list__none" v-if="$util.isEmpty(qnaList) && isEnd">
                        <div class="cs-inquiry__list__circle">
                          <div class="d-flex flex-column align-items-center">
                            <div class="cs-inquiry__list__empty">
                              <i class="dp-icon xl02 icon-not-found"></i>
                            </div>
                            <div class="cs-inquiry__list__text">
                              <div class="cs-inquiry__list__empty__text">
                                <p class="mb-0 font-weight-400 text-gray-700 dp-p-sm">
                                  등록된 문의 내역이 없습니다.
                                </p>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                </infinite-loading>
              </div>
            </div>
          </b-tab>
        </b-tabs>
        <!-- // 상품Q&A 탭 -->
      </div>
    </section>
  </main>
</template>


<script src="@views.mobile/cs/inquiry/Index.js"/>