<template>
  <div :id="'recopick_widget_' + recopick" :data-widget_id="recopick">
    <script2>{{recopickJs}}</script2>
  </div>
</template>

<script>
export default {
  props: {
    recopick: {
      type: String
    }
  },
  data() {
    return {
      recopickJs : ''
    }
  },
  mounted () {
    this.$nextTick(()=>{
      this.recopickJs = "(function(w,n){w[n]=w[n]||function(){(w[n].q=w[n].q||[]).push(arguments)};}(window, 'recoPick'));recoPick('widget', 'recopick_widget_" + this.recopick + "');"
    });
  }
}
</script>

<style>
</style>