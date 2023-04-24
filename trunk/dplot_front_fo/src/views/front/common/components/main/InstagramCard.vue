<template>
  <div class="instagram-item">
    <figure class="item__img">
      <img
        :src="instaOption.media_type === 'IMAGE' ?  instaOption.media_url : instaOption.thumburl"
        alt="인스타그램 이미지"
        @click.self="goLink(instaOption.permalink)"
      />
    </figure>
  </div>
</template>

<script>
export default {
  props: {
    instaOption: {
      type: Object,
      default: () => ({
        thumbnail: "img-magazine-instacircle01@2x.png",
        name: "Jessica",
        imgsrc: "img-magazine-insta01@2x.jpg",
      }),
    },
  },
  methods: {
    goLink(link) {
      if (window.sessionStorage.getItem('platform') == "MAC001") {
        if (!this.$util.isNull(link)) {
          window.open(link, "_blank");
        }
      }else {
        if (!this.$util.isNull(link)) {
          window.open(link, "_blank");
        }
      }
    }
  }
}
</script>

<style lang="scss"></style>
