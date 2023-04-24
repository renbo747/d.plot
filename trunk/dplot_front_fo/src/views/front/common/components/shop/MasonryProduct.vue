<template>
  <ul class="masonry__ul row list-style-none">
    <li v-for="(list, index) in goodsList" :key="index" class="col-6 col-xl-3">
      <template>
        <div class="masonry__item">
          <product-thumbnail
            class="masonry__img"
            :to="{ name: 'shop-detail', params: { pid: list.goodscode } }"
            :thumbnail-info="{ fullpath: list.fullpath }"
            :is-bg="true"
            :has-stock="list.stockcnt"
            style='cursor: pointer'
          />
          <div class="base-wish">
            <i
              class="heart-icon"
              :class="{ on: list.iswished == 'T' }"
              @click.stop="changeWish(list)"
            ></i>
          </div>
        </div>
      </template>
    </li>
  </ul>
</template>

<script>
export default {
  props: {
    list: {
      type: Array,
    },
  },
  data(){
    return {
      goodsList:[]
    }
  },
  mounted(){
    this.goodsList= this.list;
  },
  methods: {
    /**********************
     * 좋아요 버튼 처리
     ********************/
    changeWish(list) {
      if(!this.$store.state.isLogin) {
            // this.$eventBus.$emit("alert", "알림", "로그인 후 이용해주세요.");
            this.$eventBus.$emit('confirm', '로그인', '로그인 하시겠습니까?',()=>{
                this.$storage.setSessionStorage('redirectPath', {name : this.$route.name, query : this.$route.query});
                this.$router.push({name : 'member-login'});
            });
            return;
        }
        let param = {
          goodsno: list.goodsno,
          cateidx: list.cateidx,
          iswished: list.iswished,
        };
        this.$http.post("/goods/wish", param).then((result) => {
          if (result.statusCode == 200) {
            this.$util.debug("a"+list.iswished);
            list.iswished = list.iswished == "T" ? "F" : "T";
            this.$util.debug("성공");
            this.$util.debug("b"+list.iswished);
          }
        });
    },
  },
  watch:{
    list(value){
      this.goodsList = value;
    }
  }
};
</script>
