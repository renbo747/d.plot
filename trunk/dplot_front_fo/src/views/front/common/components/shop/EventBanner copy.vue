<template>
  <!-- 배너 이미지   -->
  <div
    class="event-banner-wrap"
    :style="{
      backgroundImage: `url(${bannerInfo.imgUrl})`,
    }"
  >
    <div class="event-banner__txt-area">
      <div class="event-banner__best atten-new">{{ bannerInfo.wording }}</div>
      <div class="event-banner__tit" v-html="bannerInfo.tit"></div>
      <span class="event-banner__shortcuts">바로가기</span>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    bannerInfo: {
      type: Object,
      default: () => ({
        imgUrl: require("@/assets/mobile/img/shop/img-shop-banner01.jpg"),
        wording: "Best Brand",
        tit: "공간을 변화시키는 <br /> 아름다운 조명",
      }),
    },
  },
};
</script>
