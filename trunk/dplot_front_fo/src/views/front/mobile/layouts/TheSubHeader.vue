<template>
  <header class="the-header on">
    <div class="the-header__inner">
      <div class="the-header__left">
        <i class="the-header__prev" @click="$emit('prev')">
          <img
            src="@assets.mobile/img/icon/icon-prev-white-20px.svg"
            alt="이전으로"
          />
        </i>
        <h1 class="the-header__title atten-new">{{ headerTitle }}</h1>
      </div>
      <div class="menu">
        <i v-if="option.searchIcon" class="menu-icon search" role="button" @click="$router.push('/search')">
          <img
            src="@assets.mobile/img/icon/icon-search-white-20px.svg"
            alt="검색"
          />
        </i>
        <i v-if="option.cartIcon" class="menu-icon cart" role="button" @click="$router.push('/cart')" :data-count="$store.state.cartCount">
          <img
            src="@assets.mobile/img/icon/icon-bag-white-20px.svg"
            alt="장바구니"
          />
        </i>
        <i v-if="option.homeIcon" class="menu-icon home" role="button" @click="$router.push('/shop')">
          <img
            src="@assets.mobile/img/icon/icon-home-white-20px.svg"
            alt="홈"
          />
        </i>
      </div>
    </div>
  </header>
</template>

<script>
export default {
  props: {
    option: {
      type: Object,
      default: () => ({
        title: "서브 헤더 타이틀",
        searchIcon: true,
        cartIcon: true,
        homeIcon: true,
      }),
    },
  },
  computed : {
    headerTitle : function(){
      if(this.option.title == '상품 상세') return null;
      return this.option.title
    }
  },
  mounted() {
    this.$front.getCartGoodsCount(this);
  }
};
</script>
