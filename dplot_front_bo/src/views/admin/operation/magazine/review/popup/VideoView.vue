<template>
    <!-- 첨부사진 팝업 -->
    <div id="modal-wrap" class="modal" style="display: block;">
        <div class="modal-content" style="width: 600px;">
        <div class="pop-header">
            <h2>첨부 동영상</h2>
            <button type="button" class="pop-close" @click="$emit('close')"></button>
        </div>
        <div class="pop-body">
            <div class="slide-area mt20">
            <div class="slider" style="width: 100%;">
                <ul>
                    <li>
                        <video-player
                            class="vjs-custom-skin"
                            ref="videoPlayer"
                            :options="playerOptions"
                            :playsinline="true"
                        />
                        <!-- <video class="video-player" controls :options='playerOptions'>
                            <source src="https://d2yxem29c3b5r4.cloudfront.net/mov/exp109/109_1644457045685.mp4" type=video/mp4>
                        </video> -->
                    </li>
                </ul>
            </div>
            </div>
            <div class="btn-group mt20">
            <button type="button" class="btn darkgray" @click="$emit('close')">닫기</button>
            </div>
        </div>
        </div>
    </div>
</template>


<script>

export default {

    name: 'admin.VideoView',
    props: {
        videoInfo: Object
    },
    data (){
        return {
            playerOptions: {
                // videojs options
                muted: true,
                width: '400px',
                height: '400px',
                 sources: [
                    {
                        type: 'video/mp4',
                        src: this.videoInfo.fullpath,
                    }
                ],

            }
        }
    }
}
</script>

<style>

</style>