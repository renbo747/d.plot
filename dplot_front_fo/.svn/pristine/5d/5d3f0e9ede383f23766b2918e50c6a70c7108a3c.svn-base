<template>
  <!-- 배너 이미지   -->
  <div
    class="event-banner-wrap"
    @click="goLink()"
  > 
    <!-- router-link가 원 디자인인데 수정필요-->
    <a href="javascript:void(0);">
      <img :src="bannerInfo.fullpath" />
    </a>
    <!-- <div class="event-banner__txt-area">
      <div class="event-banner__best atten-new">{{ bannerInfo.subject }}</div>
      <div class="event-banner__tit">{{ bannerInfo.desc }}</div>
      <span class="event-banner__shortcuts" @click="goLink()">바로가기</span>
    </div> -->
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
  methods: {
    goLink() {
      this.$util.debug(this.bannerInfo);
      if (window.sessionStorage.getItem("platform") == "MAC001") {
        if (!this.$util.isNull(this.bannerInfo.pclinkurl)) {
          if (this.bannerInfo.ispcnwindow == "T") {
            window.open(this.bannerInfo.pclinkurl, "_blank");
          } else {
            window.location.href = this.bannerInfo.pclinkurl;
          }
        }
      } else {
        if (!this.$util.isNull(this.bannerInfo.pclinkurl)) {
          if (this.bannerInfo.ismonwindow == "T") {
            window.open(this.bannerInfo.molinkurl, "_blank");
          } else {
            window.location.href = this.bannerInfo.molinkurl;
          }
        }
      }
    },
  },
};
</script>
