<template>
  <div class="md-card">
    <figure class="card__img" @click="$router.push('/magazine/trend/detail/'+option.tridx)" style="cursor: pointer">
      <img :src="option.fullpath"/>
    </figure>
    <div class="card__contents">
      <template v-for="(list, index) in option.keyword">
        <p class="contents__text" :key="index" v-if="!$util.isNull(list)">#{{ list }}</p>
      </template>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    option: {
      type: Object
    },
  },
  methods:{
    // goLink(list){
    //   this.$util.debug(list);
    //   if (window.sessionStorage.getItem('platform') == "MAC001") {
    //     if (!this.$util.isNull(list.pclinkurl)) {
    //       if (list.ispcnwindow == "T") {
    //         window.open(list.pclinkurl, "_blank");
    //       }else {
    //         window.location.href = list.pclinkurl;
    //       }
    //     }
    //   }else {
    //     if (!this.$util.isNull(list.pclinkurl)) {
    //       if (list.ismonwindow == "T") {
    //         window.open(list.molinkurl, "_blank");
    //       }else {
    //         window.location.href = list.molinkurl;
    //       }
    //     }
    //   }
    // }
  }
};
</script>
