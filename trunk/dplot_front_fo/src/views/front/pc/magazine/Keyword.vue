<template>
  <main class="magazine-keyword-pc">
    <!-- 키워드 있을 때-->
    <section class="magazine-keyword__section" :style="{
                  background: 'url('+fullpath+') no-repeat center/cover',
                }" v-if="keywordList.length > 0">
      <div class="container">
        <div class="keyword__wrap">
          <div><p class="mb-0 keyword-title">{{ subject }}</p></div>
          <div class="keyword__ul__wrap">
            <ul class="list-style-none">
              <li v-for="(list, index) in keywordList" :key="index">
                <div @click="goLink(list)">
                  <span class="keyword__li" style="cursor : pointer;" v-if="list.isemp === 'T'"
                    ># {{ list.keyword }}</span
                  >
                  <span class="keyword__li text-white" style="cursor : pointer;" v-else
                    ># {{ list.keyword }}</span
                  >
                </div>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </section>
    <!-- 키워드 없을 때-->
    <section class="empty-keyword__section" v-if="keywordReady && keywordList.length === 0">
      <div class="container">
        <div class="empty__keyword__wrap">
          <div>
            <p class="mb-0 atten-new empty__keywords__P">Comming soon</p>
          </div>
        </div>
      </div>
    </section>
  </main>
</template>

<script src="@views.mobile/magazine/Keyword.js"></script>
