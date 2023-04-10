<template>
  <main class="cs-notice">
    <div class="container">
      <ul class="cs-notice__ul dp-panel list-style-none">
        <li v-for="(list, index) in noticeListData" :key="index"
            :class="{new : list.isread === 'true'}"
        >
          <a
            class="notice-link d-flex align-items-start"
            @click="goDetail(list.idx)"
          >
            <span class="notice-title dp-p-sm text-gray-800"  :class="{ active: list.isread === 'true' }">{{ list.subject }}</span>
            <span class="notice-date dp-p-sm text-gray-600 font-weight-400">{{ list.poststtime }}</span>
          </a>
        </li>
      </ul>
    </div>
    <infinite-loading :identifier="infiniteId" @infinite="infiniteHandler" spinner="circles">
        <div slot="no-more"></div>
        <div slot="no-results"><p class="text-center dp-p-sm"></p></div>
    </infinite-loading>
  </main>
</template>

<script src="@views.mobile/cs/notice/Index.js"/>