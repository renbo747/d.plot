import 'babel-polyfill'
import Vue from 'vue'
import 'core-js/stable'
import 'regenerator-runtime/runtime'
import VueHead from 'vue-head'
import VueCookies from 'vue-cookies'
import VModal from 'vue-js-modal'
import VueScrollactive from 'vue-scrollactive'
import VueVideoPlayer from 'vue-video-player'
import VueDaumPostcode from "vue-daum-postcode"
import Toasted from 'vue-toasted';

import 'video.js/dist/video-js.css'
import 'vue-video-player/src/custom-theme.css'

import 'viewerjs/dist/viewer.css'
import VueViewer from 'v-viewer'

import Meta from 'vue-meta';
import VueLazyload from 'vue-lazyload'


import http from 'js/http.js'
import util from 'js/util.js'
import frontUtil from 'js/frontUtil.js'
import storage from 'js/storage.js'
import eventBus from 'js/event.js'
import 'js/filter.js'


import 'swiper/dist/css/swiper.css'

process.env.VUE_APP_LOG_LEVEL = "NULL"; // 테스트 디버그 삭제


util.debug('Host : ' + document.location.href);

Vue.config.productionTip = false

/*----------------------------- router, platform 설정 ------------------------------*/
let platform = 'MAC001';
let system = 'FRONT';


util.debug('=========' + platform);

window.localStorage.setItem('platform', platform);
window.localStorage.setItem('system', system);
/*----------------------------- App 설정 ------------------------------*/
Vue.prototype.$http = http;
Vue.prototype.$util = util;
Vue.prototype.$storage = storage;
Vue.prototype.$eventBus = eventBus;
if(system == 'FRONT') {
    Vue.prototype.$front = frontUtil;
}
// 퍼블리싱 오타로 인한 상품상세 오류발생 방지
Vue.config.ignoredElements = ['figrue'];

Vue.use(Meta, {
    keyName: 'metaInfo',
    attribute: 'data-vue-meta',
    ssrAttribute: 'data-vue-meta-server-rendered',
    tagIDKeyName: 'idx',  // 변화시키는 구분 값
    refreshOnceOnNavigation: true
});// 공유하기용 메타데이터

Vue.use(VueLazyload ,{
    lazyComponent: true, // 레지 컨테이너 (화면 보일시 로딩)
});// Lazyload 비동기 로딩

Vue.use(VueHead);
Vue.use(VueCookies);
Vue.use(VueScrollactive);
Vue.use(VueDaumPostcode);
Vue.use(VModal, { 
    dynamic: true, 
    dynamicDefaults: { 
        width:'100%', 
        draggable: false,
        resizable: false,
        scrollable: true,
        height: 'auto'
    }
});

Vue.use(Toasted, {
    position : 'bottom-center',
    duration : 2000,
    containerClass : 'toast-container',
    className :'toast-body',
    theme : 'outline'
})

Vue.use(VueVideoPlayer, /* {
    options: global default options,
    events: global videojs events
  } */);

Vue.use(VueViewer, {
    defaultOptions: {
        title : false,
        toolbar : false,
        transition: true, //말 그대로 transition 효과
        backdrop : true, //이미지 외곽 클릭시 닫힘 기능
        movable : false, //이미지 이동 여부
        navbar : false, //네비게이션 바
        tooltip : false,
        zoomOnWheel : true,
    }
});


