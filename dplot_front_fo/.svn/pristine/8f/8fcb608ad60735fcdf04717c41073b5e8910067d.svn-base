<template>
  <div class="shop-card">
    <div class="shop-card__title atten-new">
      <slot>
        <p :style="{color: '#'+catecolor}">{{ title }}</p>
      </slot>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    title: {
      type: String,
      default: "title",
    },
    catecolor: {
      type:String,
      default: "000000",
    }
  },
};
</script>

<style >
  .shop-card__title p {width: 100%; word-break: keep-all;}
</style>