<template>
  <main class="cs-notice">
    <div class="container">
      <section class="cs-notice-detail dp-panel">
        <header class="cs-notice-detail-header mb-20">
          <h1 class="cs-notice-detail__title dp-title02 font-weight-400 text-black mb-20"
              :class="{new : Data.isread === 'true'}"           
          >
            {{ Data.subject }}
          </h1>
          <div class="cs-notice-detail__date">
            <p class="mb-0 cs-notice-detail__date-p dp-p text-gray-700 font-weight-400">
            {{ Data.regdate }}
            </p>
          </div>
        </header>
        <div>
          <div class="cs-notice-detail__img__area" style="overflow:auto;">
            <!-- <div v-if="Data.fullpath != null" class="cs-notice-detail__img">
              <img :src="Data.fullpath" />
            </div> -->
            <div v-html="Data.mobilecontent">
            </div>
          </div>
        </div>
      </section>
    </div>
  </main>
</template>


<script src="@views.mobile/cs/notice/Detail.js"/>