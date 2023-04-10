<template>
  <div class="magazine-card">
    <span class="card__label atten-new">
      <span class="card__label-relative" v-if="isbedge">
        {{ option.label }}
        <span
          class="card-point"
          :style="{ background: option.pointColor }"
        ></span>
      </span>
    </span>

    <figure class="card__img" @click="goLink(option)">
      <img :src="option.fullpath" alt="이미지" height="450"/>
    </figure>

    <div class="card__contents">
      <h2 class="card__title">{{ option.title }}</h2>
      <h3 class="card__sub__title">{{ option.desc }}</h3>
      <p class="card__text">{{ option.text }}</p>
      <a class="card__link" href="javascript:void(0);" @click="goLink(option)" v-if="isRouteLink">
        <span class="link__text cursor">자세히 보기</span>
        <i class="link__more"></i>
      </a>

      <div class="card__tag-group">
        <span v-for="(tag, index) in option.tags" :key="index">{{ '#'+tag }}</span>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      subtitle: "서브타이틀 // 개발 필요22"
    }
  },
  props: {
    isbedge: {
      tyep: false,
      default: true
    },
    isRouteLink: {
      type: Boolean,
      default: true,
    },
    option: {
      type: Object,
      default: () => ({
        label: "Designer",
        fullpath: require("@/assets/mobile/img/main/dummy-main-img01.jpg"),
        title: "빛의 형태를 디자인하다",
        subtitle: "",
        text: "루이스폴센은 그저 램프를 디자인하는게 아니고<br />빛을 형상화해 사람들에게 좋은 느낌을 전달해 줄 수<br />있는 실내외 분위기를 만들고자 노력합니다.",
        to: "#",
        tags: ["LIFE", "VALUE"],
      }),
    },
  },
  methods: {
    goLink(option){
      if (window.sessionStorage.getItem('platform') == "MAC001") {
        if (!this.$util.isNull(option.pclinkurl)) {
          if (option.ispcnwindow == "T") {
            window.open(option.pclinkurl, "_blank");
          }else {
            window.location.href = option.pclinkurl;
          }
        }
      }else {
        if (!this.$util.isNull(option.pclinkurl)) {
          if (option.ismonwindow == "T") {
            window.open(option.molinkurl, "_blank");
          }else {
            window.location.href = option.molinkurl;
          }
        }
      }
    }
  }
};
</script>

<style scoped>
  .dp-magazine-trend-pc .magazine__trend__contents__section .trend__contents__wrap .card__contents {margin-top: 40px; padding:0;}
  .dp-magazine-trend-pc .magazine__trend__contents__section .trend__contents__wrap .card__title {font-size: 32px; margin-bottom: 20px; color: #111; word-break: break-all;}
  
  h3 {font-size: 18px; color: #666; margin-bottom: 0;}  

  
  @media (max-width: 500px){
    .dp-magazine-trend .magazine__trend__contents__section .trend__contents__wrap .card__contents {margin-top: 24px;}
    .dp-magazine-trend .magazine__trend__contents__section .trend__contents__wrap .card__title {font-size: 24px; margin-bottom: 10px; color: #111; word-break: break-all;}
    h3 {font-size: 14px; color: #666; margin-bottom: 0;}    

  }
</style>