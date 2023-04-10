<template>
  <div class="dp-product" :class="{ horizontal: isHorizontal }">
    <product-thumbnail
      :thumbnail-info="{
        fullpath: giftInfo.fullpath,
      }"
    />
    <div class="product__contents">
      <h4 class="product__title">{{ giftInfo.giftname }}</h4>
    </div>
  </div>
</template>

<script>
import ProductThumbnail from "@views.common/components/shop/ProductThumbnail";
export default {
  components: { ProductThumbnail },
  props: {
    isHorizontal: {
      type: Boolean,
      default: false,
    },
    giftInfo: {
      type: Object,
    },
  },
  data() {
    return {
    };
  },
  methods: {
  },
};
</script>
