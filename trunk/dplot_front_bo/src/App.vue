<template>
  <div id="app">
    <router-view />
    <loading
      :loader="loader"
      :active.sync="isLoading"
      :can-cancel="false"
      :z-index="99999"
      :color="color"
      :width="80"
      :height="80"
      :background-color="backgroundColor"
      :is-full-page="true"
    />
  </div>
</template>

<script>
import Loading from "vue-loading-overlay";
import "vue-loading-overlay/dist/vue-loading.css";

export default {
    name: 'App',
    components: {
        Loading
    },
    data() {
      return {
        loader : "bars",
        color : "#00b2fd",
        backgroundColor : "#eeeeee",
        isLoading : false,
      }
    }, 
    mounted() {
      //this.$util.debug("Environment : " + JSON.stringify(process.env));
      //통신 로딩이미지
      this.$eventBus.$on('loading', (flag) => {
          this.isLoading = flag;
      });

      let vh = window.innerHeight * 0.01;
      document.documentElement.style.setProperty("--vh", `${vh}px`);
    }
}
</script>