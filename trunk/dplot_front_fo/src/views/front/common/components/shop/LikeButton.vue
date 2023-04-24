<template>
  <div class="like-button">
    <i class="heart-icon">
      <img @click="isLike = !isLike" :src="imgSrc" alt="하트 아이콘" />
    </i>
  </div>
</template>

<script>
export default {
  data() {
    return {
      isLike: true,
    };
  },
  computed: {
    imgSrc() {
      return this.isLike
        ? require("@assets.pc/img/icon/icon-heart-white-24px.svg")
        : require("@assets.pc/img/icon/icon-heart-red-24px.svg");
    },
  },
};
</script>
