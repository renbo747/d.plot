<template>
  <header class="the-header">
    <!-- Todo: (개발) 0418 기획 및 개발 -->
    <div class="the-header__inner">
      <h1 class="logo">
        <router-link :class="{ 'text-black': isBlack }" :to="{name:'shop',params:{init:true}}">
          <logo :logo-color="currentColor" :is-page-logo="false" />
        </router-link>
      </h1>

      <div class="menu">
        <i
          class="menu-icon search"
          role="button"
          @click="$router.push('/search')"
        >
          <img
            v-if="isBlack"
            src="@/assets/common/icon/icon-search-black-20px.svg"
            alt="검색"
          />
          <img
            v-else
            src="@assets.mobile/img/icon/icon-search-white-20px.svg"
            alt="검색"
          />
        </i>
        <i class="menu-icon cart" role="button" @click="$router.push('/cart')" :data-count="$store.state.cartCount">
          <img
            v-if="isBlack"
            src="@/assets/common/icon/icon-bag-black-20px.svg"
            alt="장바구니"
          />
          <img
            v-else
            src="@/assets/common/icon/icon-bag-white-20px.svg"
            alt="장바구니"
          />
        </i>
      </div>
    </div>
    <!-- // (개발) 0418 기획 및 개발 -->
  </header>
</template>
<script>
export default {
  props: {
    isBlack: {
      type: Boolean,
      default: true,
    },
  },
  computed: {
    currentColor() {
      return this.isBlack ? "#000000" : "#ffffff";
    },
  },
  mounted() {
    this.$front.getCartGoodsCount(this);
  }
};
</script>

<style scoped>
.modal-open .the-header {
  padding-right:initial !important;
}
</style>
