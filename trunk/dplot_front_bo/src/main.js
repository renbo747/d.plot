import 'babel-polyfill'
import 'core-js/stable'
import 'regenerator-runtime/runtime'

import Vue from 'vue'
import BootstrapVue from "bootstrap-vue"
import VueCookies from 'vue-cookies'
import VModal from 'vue-js-modal'
import VueScrollactive from 'vue-scrollactive'
import VueVideoPlayer from 'vue-video-player'
import VueDaumPostcode from "vue-daum-postcode"
import Meta from 'vue-meta';
import VueLazyload from 'vue-lazyload'
import LoadScript from "vue-plugin-load-script";
import VueAppend from 'vue-append'

import 'viewerjs/dist/viewer.css'
import VueViewer from 'v-viewer'

import App from './App.vue'
import adminRouter from './router/admin.js'
import partnersRouter from './router/partners.js'

import http from './js/http.js'
import util from './js/util.js'
import store from './js/store.js'
import storage from './js/storage.js'
import eventBus from './js/event.js'
import './js/filter.js'

Vue.config.productionTip = false;
Vue.config.devtools = false;

/*----------------------------- router, platform 설정 ------------------------------*/
let router;
let platform = 'MAC001';
let system = 'ADMIN';

util.debug('Host : ' + document.location.href);
util.debug("Environment : " + JSON.stringify(process.env));
util.debug('Platform : ' + platform);
util.debug('Browser : ' + util.browser());

// if(process.env.NODE_ENV !== 'local'){
    if(window.location.hostname.includes('admin.')){
        document.title = 'D.PLOT ADMIN';
        require('@assets.admin/css/style.css');
        system = 'ADMIN';
        router = adminRouter;
    } else if(window.location.hostname.includes('partner.')){
        document.title = 'D.PLOT PARTNER';
        require('@assets.admin/css/style.css');
        system = 'PARTNER';
        router = partnersRouter;
    }

    // if(window.location.hostname.includes('local.mobilefactory.co.kr')){
    //     require('@assets.admin/css/style.css');
    //     system = 'ADMIN';
    //     router = adminRouter;
    // }
// }

// if (document.location.href.includes('/admin')) {
//     require('@assets.admin/css/style.css');
//     system = 'ADMIN';
//     router = adminRouter;
// } else if(document.location.href.includes('/partners') || document.location.href.includes('/common/partnership')) {
//     require('@assets.admin/css/style.css');
//     system = 'PARTNER';
//     router = partnersRouter;
// }

window.sessionStorage.setItem('platform', platform);
window.sessionStorage.setItem('system', system);
store.commit('platform', platform);
/*----------------------------- App 설정 ------------------------------*/
Vue.prototype.$http = http;
Vue.prototype.$util = util;
Vue.prototype.$storage = storage;
Vue.prototype.$eventBus = eventBus;

// 퍼블리싱 오타로 인한 상품상세 오류발생 방지
Vue.config.ignoredElements = ['figrue'];

Vue.use(VueAppend);
Vue.use(LoadScript);
Vue.use(VueCookies);
Vue.use(BootstrapVue);//어드민에서 문제 없는 체크필요
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

Vue.use(VueVideoPlayer, /* {
    options: global default options,
    events: global videojs events
  } */
);

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

new Vue({
    store,
    router,
    render: h => h(App)
}).$mount('#app')