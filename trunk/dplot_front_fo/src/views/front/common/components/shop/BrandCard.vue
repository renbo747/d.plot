<template>
  <div class="brand-card" @click="goLink(option)">
    <span class="card__label atten-new">{{ option.subject }}</span>

    <figure class="card__img">
      <img :src="option.fullpath"/>
    </figure>
  </div>
</template>

<script>
export default {
  props: {
    option: {
      type: Object,
      default: () => ({
        desc: null,
        ex45idx: null,
        fullpath: null,
        goodslist: [],
        isbnimg: null,
        ismonwindow: null,
        ispcnwindow: null,
        molinkurl: null,
        pclinkurl: null,
        subject: null,
      }),
    },
  },
  methods:{
    goLink(list){
      this.$util.debug(list);
      if (window.sessionStorage.getItem('platform') == "MAC001") {
        if (!this.$util.isNull(list.pclinkurl)) {
          if (list.ispcnwindow == "T") {
            window.open(list.pclinkurl, "_blank");
          }else {
            window.location.href = list.pclinkurl;
          }
        }
      }else {
        if (!this.$util.isNull(list.pclinkurl)) {
          if (list.ismonwindow == "T") {
            window.open(list.molinkurl, "_blank");
          }else {
            window.location.href = list.molinkurl;
          }
        }
      }
    }
  }
};
</script>
