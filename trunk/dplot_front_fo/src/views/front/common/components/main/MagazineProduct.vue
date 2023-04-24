<template>
  <div class="magazine-product">
    <product-thumbnail :thumbnail-info="{ id: 14, fullpath: option.fullpath }" 
      :to="{
        name: 'shop-detail',
        params: { pid : option.goodscode}
      }"/>
    <div class="product__detail" @click="$router.push(`/shop/detail/${option.goodscode}`)">
      <div class="product__contents">
        <p class="product__ctg atten-new single-ellipsis">
          {{ option.brenname }}
        </p>
        <p class="product__text single-ellipsis">{{ option.text }}</p>
        <div class="product__tag">
          <span v-for="(tag, index) in option.tags" :key="index">{{
            '#'+tag
          }}</span>
        </div>
      </div>
      <div class="product__footer">
        <div class="product__rating atten-new">
          {{ option.rating }} <span>({{ option.users }})</span>
        </div>
        <div
          class="product__like"
          :class="{ like: option.iswished === 'T' }"
          @click.stop="changeWish(option)"
        ></div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    option: {
      type: Object,
      default: () => ({
        id: 1,
        fullpath: require("@/assets/mobile/img/main/dummy-main-img01-1.jpg"),
        category: "LOUIS POULSEN",
        text: "PH 5 미니 펜던트 램프 블루",
        tags: ["Louis", "Poulsen"],
        rating: 3.9,
        users: 19,
      }),
    },
  },
  data() {
    return {
      isLike: false,
    };
  },
  methods: {
    changeWish(option) {
      if(!this.$store.state.isLogin) {
            // this.$eventBus.$emit("alert", "알림", "로그인 후 이용해주세요.");
            this.$eventBus.$emit('confirm', '로그인', '로그인 하시겠습니까?',()=>{
              this.$storage.setSessionStorage('redirectPath', {path : this.$route.path, query : this.$route.query});
              this.$router.push({name : 'member-login'});
            });
            return;
        }
        let param = {
          goodsno: option.goodsno,
          cateidx: option.cateidx,
          iswished: option.iswished,
        };
        this.$http.post("/goods/wish", param).then((result) => {
          if (result.statusCode == 200) {
            option.iswished = option.iswished == "T" ? "F" : "T";
            this.$emit("sendlike", option.goodsno, option.iswished);
          }
        });
    },
  }
};
</script>
