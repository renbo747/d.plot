<template>
  <main class="new-list">
    <div class="container">
      <section class="new-list__section">
        <header class="new-list__header">
          <h1 class="atten-new dp-mb-40">{{name}}</h1>
        </header>

        <div class="new-list__body">
          <masonry-product :list="shopNewList" :text-size="'lg'" />
        </div>
        <infinite-loading :identifier="infiniteId" @infinite="infiniteHandler" spinner="circles">
             <div slot="no-more"></div>
             <div slot="no-results"><p>조회된 결과가 없습니다.</p></div>
        </infinite-loading>
      </section>
    </div>
  </main>
</template>

<script src="@views.mobile/shop/NewList.js"></script>
