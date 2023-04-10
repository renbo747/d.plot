<template>
  <article>
    <div class="main-popup-modal">
      <div class="bottom-sheet__body">
        <swiper
          class="swiper main-popup-swiper"
          :options="mainPopupSwiperOption"
          ref="popupSwiper" @click="goLink" v-if="thumbnailData.length > 0"
        >
          <swiper-slide v-for="(img, index) in thumbnailData" :key="index">
            <div
              class="popup-thumbnail__image"
              :style="{
                backgroundImage: 'url('+img.fullpath+')'
              }"
              style="cursor: pointer"
            ></div>
          </swiper-slide>
          <div class="swiper-pagination" slot="pagination" v-show="thumbnailData.length > 1"></div>
        </swiper>
      </div>
      <div class="bottom-sheet__footer">
        <div class="btn-group">
          <slot name="bottom-sheet-footer">
            <b-button
              class="dp-btn"
              variant="white not-hover text-gray-700"
              squared
              @click="bottomSheetClose('T')"
            >
              <span>오늘 하루 보지 않기</span>
            </b-button>
            <b-button
              class="dp-btn"
              variant="white not-hover text-gray-700"
              squared
              @click="bottomSheetClose"
            >
              <span>닫기</span>
            </b-button>
          </slot>
        </div>
      </div>
    </div>
    <div class="bottom-sheet__backdrop" @click="bottomSheetClose"></div>
  </article>
</template>

<script>
import { swiper, swiperSlide } from "vue-awesome-swiper";
export default {
  components: {
    swiper,
    swiperSlide,
  },
  props: {
    thumbnailData: {
      type: Array,
      default: () => [
        // {
        //   id: 1,
        //   imgUrl: "img-main-popup02.jpg",
        // },
        // {
        //   id: 2,
        //   imgUrl: "img-main-popup03.jpg",
        // },
        // {
        //   id: 3,
        //   imgUrl: "img-main-popup04.jpg",
        // },
      ],
    },
  },
  data() {
    return {
      mainPopupSwiperOption: {
        loop: this.thumbnailData.length <= 1 ? false : true, 
        pagination: {
          el: ".main-popup-swiper .swiper-pagination",
          clickable: true,
        },
      },
    };
  },
  methods: {
    bottomSheetClose(check) {
      if(check === 'T') {
        let now = new Date();
        now.setHours(24,0,0);
        let name = '';
        if("main".includes(this.$route.name)) {
          name = 'MAIN_';
        } else {
          name = 'SHOP_';
        }
        if (window.sessionStorage.getItem('platform') == "MAC001") {
          name += 'POP_CLOSE_ONEDAY_PC';
        } else {
          name += 'POP_CLOSE_ONEDAY_MO';
        }
        this.$cookies.set(name, 1, now.toUTCString());
      }
      this.$emit("close");
    },
    goLink() {
      let list = this.thumbnailData[this.$refs.popupSwiper.swiper.realIndex];
      if (window.sessionStorage.getItem('platform') == "MAC001") {
          if (!this.$util.isNull(list.linkpc)) {
              if (list.ispcnwindow == "T") {
                  window.open(list.linkpc, "_blank");
              } else {
                  window.location.href = list.linkpc;
              }
          }
      } else {
          if (!this.$util.isNull(list.linkmo)) {
              if (list.ismonwindow == "T") {
                  window.open(list.linkmo, "_blank");
              } else {
                  window.location.href = list.linkmo;
              }
          }
      }
    },
  },
  // watch: {
  //   'thumbnailData': function(value) {
  //     if(value.length === 1) {
  //       this.mainPopupSwiperOption.loop = false;
  //     } else {
  //       this.mainPopupSwiperOption.loop = true;
  //     }
  //   }
  // }
};
</script>
