<template>
  <div>
    <video
      style="display:none"
      ref="videoEl" 
      :src="src"
      @loadeddata="onLoadedData"
      @seeked="onSeeked"
    />
    <canvas ref="canvas"></canvas>
  </div>
</template>

<script>
export default {
  props: {
    src : {
      type: String,
      default: "",
    },
    width: {
      type:Number,
      default: 150,
    },
    height : {
      type:Number,
      default: 150,
    }
  },
  mounted() {
  },
  methods: {
    onLoadedData() {
      this.video.currentTime = 15;
    },
    onSeeked() {
      this.draw(this.video, this.canvas);
    },
    draw(video, canvas) {
      const ratio = video.videoWidth / video.videoHeight;
      const w = video.videoWidth - 100;
      const h = parseInt(w / ratio, 10);
      canvas.width = w;
      canvas.height = h;
      const context = canvas.getContext('2d');
      //context.fillRect(0, 0, w, h);
      context.drawImage(video, 0, 0, w, h);
      
      const dataURL = canvas.toDataURL('image/jpeg');
      this.playerOptions.poster = dataURL;
    },
  },
  computed: {
    video() {
      return (this.$refs.videoEl) ? this.$refs.videoEl : null;
    },
    canvas() {
      return (this.$refs.canvas) ? this.$refs.canvas : null;
    },
  },
}
</script>
<style scoped></style>
