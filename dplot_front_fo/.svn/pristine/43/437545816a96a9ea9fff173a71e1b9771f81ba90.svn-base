<template>
  <!-- 이미지 모달 -->
  <div class="img-modal" @click.self="closeModal()">
    <div class="image-content">
      <template v-if="isphoto">
        <swiper :options="modalSwiperOption">
          <swiper-slide
            class="swiper-slide"
            v-for="(list, index) in files"
            :key="index"
          >
            <figure @click.stop>
              <img :src="list.fullpath" alt="리뷰 이미지 확대" />
            </figure>
          </swiper-slide>
        </swiper>
        <template v-if="platform == 'MAC001'">
          <div class="modal-swiper-prev" @click.stop  v-show="files.length > 1">
            <img
              src="@assets.pc/img/icon/icon-arrowLeft-white-41px.svg"
              alt="prev button"
            />
          </div>
          <div class="modal-swiper-next" @click.stop v-show="files.length > 1">
            <img
              src="@assets.pc/img/icon/icon-arrowRight-white-41px.svg"
              alt="next button"
            />
          </div>
        </template>
        <template v-else>
          <div class="swiper-button-group" v-show="files.length > 1">
            <div class="swiper-button-prev" @click.stop>
              <img
                src="@assets.pc/img/icon/icon-arrowLeft-white-41px.svg"
                alt="prev button"
              />
            </div>
            <div class="swiper-button-next" @click.stop>
              <img
                src="@assets.pc/img/icon/icon-arrowRight-white-41px.svg"
                alt="next button"
              />
            </div>
          </div>
        </template>
      </template>
      <template v-else>
        <div class="video-area" v-if="files.length > 0">
          <video-player
            class="vjs-custom-skin"
            ref="videoPlayer"
            :options="playerOptions"
            :playsinline="true"
          />
        </div>
      </template>
      <i class="dp-icon cursor-pointer" @click.self="closeModal()" style="z-index:20; cursor: pointer;"></i>
    </div>
  </div>
  <!-- // 이미지 모달 -->
</template>

<script>
import { swiper, swiperSlide } from "vue-awesome-swiper";
export default {
  components: {
    swiper,
    swiperSlide,
  },
  props: {
    params: Object,
  },
  data() {
    return {
      isphoto: true,
      files: [],
      modalSwiperOption: {
        slidesPerView: "auto",
        spaceBetween: 10,
        observeParents: true,
        observer: true,
        navigation: {
          nextEl: window.sessionStorage.getItem("platform") == 'MAC001'?'.modal-swiper-next':'.swiper-button-next',
          prevEl: window.sessionStorage.getItem("platform") == 'MAC001'?".modal-swiper-prev":'.swiper-button-prev',
        },
      },
      playerOptions: {
        // videojs options
        muted: false,
        width: "400px",
        height: "400px",
        autoplay: false,
        sources: [
          {
            type: "video/mp4",
            src: "",
          },
        ],
      },
      platform: window.sessionStorage.getItem("platform"),
    };
  },
  methods: {
    closeModal() {
      this.$util.debug("모달닫기");
      this.$modal.hide("commonModal");
    },
    setFile() {
      this.isphoto = this.params.filetype == "FLT001" ? true : false;
      let templist = this.params.files;
      if (this.isphoto) {
        this.files = templist.filter((x) => x.filetype == "FLT001");
      } else {
        this.files = templist.filter((x) => x.filetype != "FLT001");
        this.$nextTick(function () {
          this.$refs.videoPlayer.player.src(this.files[0].fullpath);
        });
      }
       this.modalSwiperOption.initialSlide = 2;
    },
  },
  mounted() {
    this.setFile();
  },
  watch: {
    params(val) {
      this.setFile();
    },
  },
};
</script>

<style>
.vm--container {
  z-index: 10000 !important;
}
.swiper-wrapper {
    align-items: center;
}
.imready__list .swiper-wrapper {
    align-items: inherit;
}
.img-modal .image-content {
  height: fit-content !important;
  max-height: 80vh;
  background: transparent !important;
  overflow: inherit !important;
}
.img-modal figure img {
    max-height: 80vh;
}

.img-modal .video-area {
  padding-bottom:initial !important;
}
</style>