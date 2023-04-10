<template>
  <div class="review-thumbnail" @click="$emit('click')">
    <!-- 이미지 배경 -->
    <figure class="review-thumbnail__figure">
      <img :src="file.filetype=='FLT001'?file.fullpath:$util.changeFileType(file.fullpath, '.jpg')" :style="'width: '+size+'px; height: '+size+'px;'"/>
    </figure>

    <!-- 플레이 버튼 -->
    <div v-if="file.filetype=='FLT002'" class="review-thumbnail__backdrop">
      <i class="review-thumbnail__play">
        <img src="@/assets/common/icon/icon-play-white-40px.svg" alt="" />
      </i>
    </div>

    <!-- 등록된 이미지 갯수 -->
    <div class="review-thumbnail__count" v-if="imgCount > 1">
      {{ imgCount }}
    </div>
  </div>
</template>

<script>
export default {
  props: {
    size: {
      type: Number,
      default: 100,
    },
    imgCount: {
      type: Number,
      default: 0,
    },
    file: {
      type: Object,
      default: () => {
        return {
          src: "https://picsum.photos/seed/picsum/200/200",
          alt: "리뷰 이미지",
          imgCount: 5,
        };
      },
    },
  },
};
</script>

<style lang="scss">

.review-thumbnail {
  position: relative;
  width: 170px;
  height: 170px;
  max-width: 190px;
  max-height: 190px;
  background: #777;
  cursor: pointer;
  .review-thumbnail__figure {
    margin-bottom: 0;
    img {
      width: 100%;
    }
  }  

  .review-thumbnail__backdrop {
    position: absolute;
    top: 0;
    left: 0;
    display: flex;
    align-items: center;
    justify-content: center;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.3);
  }
  .review-thumbnail__play {
    width: 30px;
    height: 30px;
    @include media-breakpoint-up(lg) {
      width: 40px;
      height: 40px;
    }
    img {
      width: 100%;
    }
  }
  .review-thumbnail__count {
    position: absolute;
    bottom: 0;
    right: 0;
    display: flex;
    align-items: center;
    justify-content: center;
    width: 28px;
    height: 28px;
    background: rgba(0, 0, 0, 0.5);
    font-family: "AttenNew", sans-serif;
    font-size: 14px;
    letter-spacing: -0.54px;
    color: #fff;
    @include media-breakpoint-up(lg) {
      width: 34px;
      height: 34px;
      font-size: 18px;
    }
  }
}

@media (max-width: 500px){
    .review-thumbnail {
      width: 100px !important;
      height: 100px !important;
    }
  }
</style>
