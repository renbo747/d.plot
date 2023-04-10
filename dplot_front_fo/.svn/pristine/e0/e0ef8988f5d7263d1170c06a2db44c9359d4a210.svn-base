<template>
    <div class="title_wrap">
        <div class="title_number">
            <img :src="'http://d2ljev2ny8cy61.cloudfront.net/dplot/platfrom/promotion/common/tab_title0'+ data.idx +'.png'" alt="" />
        </div>
        <div class="title_content">{{data.title}}</div>
        <div :class="['title_desc', data.gotoBtn ? 'goToBtn' : '']" :style="{textAlign: `${data.descType == 'leftAlign' ? 'left' : ''}`}" v-html="data.desc"></div>
    </div>
</template>

<script>
    export default {
        props: {
            data: Object
        }
    }
</script>

<style scoped>
    .title_wrap { width: 100%; margin: 0 auto; font-family: 'AttenNew', 'AppleSDGothicNeo', sans-serif; overflow: hidden; display: flex; flex-direction: column; justify-content: center; align-items: center; padding:100px 0;}
	.title_wrap .title_content { font-size: 54px; display: block; letter-spacing: 0; margin-top: 30px; font-weight: 700;}
    .title_wrap .title_desc { margin-top: 30px; font-size: 30px; letter-spacing: -2px; text-align:center; min-width:720px;}
    .title_wrap .goToBtn {position: relative; cursor:pointer}
    .title_wrap .goToBtn::after {width: 151px; height: 36px; background:url('http://d2ljev2ny8cy61.cloudfront.net/dplot/platfrom/promotion/common/gotoBtn.png') no-repeat; content:''; position: absolute; right: 0; bottom: 1rem;}

    @media (max-width : 500px){
        .title_wrap { width: 100%; margin: 0 auto; font-family: 'AttenNew', 'AppleSDGothicNeo', sans-serif; overflow: hidden; display: flex; flex-direction: column; justify-content: center; align-items: center; padding:40px 0;}
        .title_wrap .title_content { font-size: 27px; display: block; letter-spacing: 0; margin-top: 15px; font-weight: 700;}
        .title_wrap .title_desc { margin-top: 15px; font-size: 15px; letter-spacing: -1px; text-align:center; min-width:90%;}
        .title_wrap .title_number {max-width:32px;}
        .title_wrap .title_number img {max-width:100%;}
        .title_wrap .goToBtn {position: relative; cursor:pointer}
        .title_wrap .goToBtn::after {width: 60px; height: 15px; background:url('http://d2ljev2ny8cy61.cloudfront.net/dplot/platfrom/promotion/common/gotoBtn.png') no-repeat; content:''; position: absolute; right: 0; bottom: 0.5rem; background-size:100%;}
    }
</style>