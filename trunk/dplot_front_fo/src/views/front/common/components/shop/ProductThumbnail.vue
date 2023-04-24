<template>
  <div class="product-thumbnail " :class="{ bg: isBg }">
    <!-- 링크 이동 -->
    <!--<router-link :to="to">-->
    <a href="javascript:void(0);" @click.prevent="goDetail()">
      <template v-if="!isBg">
        <!-- 이미지 태그 사용 시 -->
        <figure ref="imgdiv" class="thumbnail__img">
          <img :src="thumbnailInfo.fullpath" alt="상품 이미지" />
        </figure>
      </template>
      <template v-else>
        <!-- 배경 이미지 사용 시 -->
        <figure
          class="thumbnail__img"
          :style="{ backgroundImage: `url(${thumbnailInfo.fullpath})` }"
        ></figure>
      </template>


      <!-- 재고 상태 -->
      <div v-if="hasStock === 0 && hasEnd == false" class="product-status">
        <span class="status-text" :class="size">{{ statusText }}</span>
      </div>
      <!-- 판매종료 -->
      <div v-if="hasEnd" class="product-status">
        <span class="status-text" :class="size">판매종료</span>
      </div>
    <!--</router-link>-->
  </a>

    <base-checkbox
      v-if="isCheckbox"
      v-model="newValue"
      :id="id"
      label="" 
      :is-checked="newValue"
      @change="change"/>
  </div>
</template>

<script>
export default {
  props: {
    isBg: {
      type: Boolean,
      default: false,
    },
    isCheckbox: {
      type: Boolean,
      default: false,
    },
    to: {
      type: Object,
      default: () => ({
        name: "",
        params: {},
      }),
    },
    size: {
      type: String,
      default: "",
    },
    id : {
      type: String,
      default : "id",
    },
    thumbnailInfo: {
      type: Object,
      default: () => ({
        id: 1,
        fullpath: require("@/assets/mobile/img/shop/img-shop-product01.jpg"),
      }),
    },
    hasStock: {
      type: [String, Number],
      default: 1,
    },
    statusText: {
      type: String,
      default: "Sold Out",
    },
    hasEnd: {
      type: Boolean,
      default: false
    },
    value : {
      type:Boolean,
      default : false
    },
    height : {
      type: Number,
      default :0
    }
  },
  computed: {
    newValue: {
      get() {
        return this.value;
      },
      set(val) {
        this.$emit("input", val);
      },
    },
  },
  mounted() {
    if(this.height != 0) {
      this.$refs.imgdiv.style.height = this.height + 'px'
    }
    //this.$refs.imgdiv.style.height = '150px'
  },
  methods : {
    change(){
      this.$emit('change');
    },
    goDetail(){
      if (this.hasEnd) {
        this.$eventBus.$emit('alert', '알림', "판매종료 상품입니다.");
        return;
      }
      if (this.$util.isBlank(this.to.name)){
        return;
      }
      this.$router.push(this.to);
    }
  }
};
</script>

<style scoped>
.thumbnail__img {
  overflow:hidden !important;
}
img {
  position:relative !important;
  height:auto !important;
}

</style>