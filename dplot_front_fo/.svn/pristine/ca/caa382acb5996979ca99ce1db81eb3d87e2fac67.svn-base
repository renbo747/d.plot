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
      <section>
        <ul class="cs-notice__ul dp-panel pt-0 list-style-none">
          <li
            v-for="(list, index) in noticeListData"
            :key="index"
            class="cursor"
            :class="{ new: list.isread === 'true' }"
            @click="goDetail(list.idx)"
          >
            <a
              style="cursor: pointer"
              class="notice-link d-flex align-items-start"
            >
              <span
                class="notice-title text-gray-800"
                :class="{ active: list.isread == 'true' }"
                >{{ list.subject }}</span
              >
              <span class="notice-date text-gray-600 font-weight-400">{{
                list.poststtime
              }}</span>
            </a>
          </li>
        </ul>
        <div class="inquiry-pagination dp-panel pb-0 d-flex">
          <div class="inquiry-pagination-wrap">
            <base-pagination
              :currentPage="pagingData.currentPage"
              :listTotal="pagingData.listTotal"
              :listCnt="pagingData.listCnt"
              @changePage="changePage"
            />
          </div>
        </div>
      </section>
    </div>
  </main>
</template>

<script src="@views.pc/cs/notice/Index.js"/>
