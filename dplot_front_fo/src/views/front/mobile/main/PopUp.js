import { swiper, swiperSlide } from "vue-awesome-swiper";

export default {
  components: {
    swiper,
    swiperSlide,
  },
  data() {
    return {
      modalOption: {
        slidesPerView: "auto",
        loop: true,
        pagination: {
          el: ".swiper-pagination",
          clickable: true,
        },
      },
      thumbnailData: [],
      sampleCheckbox: {
        id: "sampleChk01",
        checked: false,
      },
    };
  },
  mounted() {
    this.setInit();
  },
  methods: {
    setInit() {
        this.$http.post('/mz/popup', {})
        .then(result => {
            if(result.statusCode === 200) {
                let data = result.data;
                this.thumbnailData = data.popuplist;
            }
        })
    },
    goLink(list) {
        // 구현예정
    }
  }
};