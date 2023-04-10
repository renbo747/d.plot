<template>
  <main class="cs-notice">
    <div class="container">
      <section class="cs-notice-header">
        <header class="cs-inquiry-section__header">
          <h2 class="cs-section__title text-black font-weight-700 mb-0">
            공지사항
          </h2>
        </header>
      </section>
      <section class="cs-notice-detail dp-panel pt-0">
        <header class="cs-notice-detail-header">
          <h1 class="cs-notice-detail__title dp-title02 font-weight-400 text-black mb-10"
              :class="{new : Data.isread === 'true'}"          
          >
          {{ Data.subject }}
          </h1>
          <div class="cs-notice-detail__date">
            <p
              class="mb-0 cs-notice-detail__date-p dp-p text-gray-700 font-weight-400"
            >
              {{Data.regdate}}
            </p>
          </div>
        </header>
        <div>
          <div class="cs-notice-detail__img__area" style="overflow:auto;">
            <!-- <div v-if="Data.fullpath != null" class="cs-notice-detail__img">
              <img :src="Data.fullpath" />
            </div> -->
            <div v-html="Data.content">
            </div>
          </div>
        </div>
        <div class="cs-notice-detail__btn">
          <div class="notice-detail__btn__wrap">
            <router-link to="/cs/notice">
              <b-button
                class="dp-btn notice-detail__btn"
                variant="outline-gray-800"
              >목록보기</b-button
              >
            </router-link>
          </div>
        </div>
      </section>
    </div>
  </main>
</template>

<script src="@views.mobile/cs/notice/Detail.js"/>
