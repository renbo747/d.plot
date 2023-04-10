<template>
<div>
  <video-player
    class="vjs-custom-skin"
    ref="videoPlayer"
    :options="playerOptions"
    :playsinline="true"
  />
  <video ref="snapshot" payload="metadata"/>
  <canvas ref="canvasId" ></canvas>
</div>
</template>

<script>
import 'video.js/dist/video-js.css'
import 'vue-video-player/src/custom-theme.css'

export default {
  name: 'HelloWorld',
  data() {
    return {
      playerOptions: {
        // videojs options
        muted: true,
        height:400,
        language: 'en',
        playbackRates: [0.7, 1.0, 1.5, 2.0],
        sources: [
          {
            type: 'video/mp4',
            src: 'http://localhost:81/video/Bike%20-%2072566.mp4'
          },
        ],
        poster:''
      },
    }
  },
  mounted() {
    const video = this.$refs.videoPlayer.$refs.video;
    const snapshot = this.$refs.snapshot;

    snapshot.src = this.playerOptions.sources[0].src + "#t=15";

    const canvas = this.$refs.canvasId;
    
    video.addEventListener('loadeddata', () => {
      this.draw(snapshot, canvas);
    });

    video.addEventListener('play', function () {

    }, false);

    video.addEventListener('pause', function () {

    }, false);
    
   },
   methods: {
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
      snapshotCreated (snapshot) {
      }
  },
}
</script>
<style scoped></style>
