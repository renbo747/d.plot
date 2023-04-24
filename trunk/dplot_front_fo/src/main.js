import 'babel-polyfill'
import 'core-js/stable'
import 'regenerator-runtime/runtime'

import Vue from "vue";
import VueRouter from "vue-router";
import BootstrapVue from "bootstrap-vue"
import VueCookies from 'vue-cookies'
import VModal from 'vue-js-modal'
import VueScrollactive from 'vue-scrollactive'
import VueVideoPlayer from 'vue-video-player'
import VueDaumPostcode from "vue-daum-postcode"
import Meta from 'vue-meta';
import VueLazyload from 'vue-lazyload'
import LoadScript from "vue-plugin-load-script";
import VueAppleLogin from 'vue-apple-login';
import VS2 from 'vue-script2'
import VueCryptojs from 'vue-cryptojs'

import 'viewerjs/dist/viewer.css'
import VueViewer from 'v-viewer'
import VueHtmlToPaper from 'vue-html-to-paper';
import VueGtm from '@gtm-support/vue2-gtm';
import VueScrollmagic from 'vue-scrollmagic'

import App from './App.vue'

import pcRouter from './router/pc.js'
import mobileRouter from './router/mobile.js'

import http from './js/http.js'
import util from './js/util.js'
import frontUtil from './js/frontUtil.js'
import store from './js/store.js'
import storage from './js/storage.js'
import eventBus from './js/event.js'
import './js/filter.js'

import "@views.common/components/globalComponents"; // Global Component (PC 모바일 공용)

Vue.config.productionTip = false;
Vue.config.devtools = true;

/*----------------------------- router, platform 설정 ------------------------------*/
let routes;
let platform = 'MAC001';//pc
let system = 'FRONT';
let domain = location.protocol + '//' + location.host;

let isMobile = util.isMobile();

//리다이렉트일 경우 router 실행안함(IOS 네트워크 오류해결용)
let isredirect = false;
if(window.location.href.indexOf('kmcCert') == -1 && window.location.href.indexOf('auth') == -1) {
    if(isMobile) {
      const temp = window.location.href
      if(location.host.indexOf('www.') > -1){
        window.location.replace(window.location.href.replace('www.', 'm.'));
        isredirect = true;
      } else if(location.host == 'dplot.co.kr') {
        window.location.replace(window.location.href.replace('dplot.co.kr', 'm.dplot.co.kr'));
        isredirect = true;
      }
        
    } else if(!isMobile && domain.indexOf('m.') > -1) {
        window.location.replace(window.location.href.replace('m.', 'www.'));
        isredirect = true;
    }
}

if (navigator.userAgent.match(/DPLOT_WEBVIEW/i) != null ) {
    platform = 'MAC003'; 
} else if(isMobile) {
    platform = 'MAC002';
}
//platform = 'MAC003';
util.debug('Domain : ' + domain);
util.debug('Host : ' + document.location.href);
util.debug("Environment : " + JSON.stringify(process.env));
util.debug('Platform : ' + platform);
util.debug('Browser : ' + util.browser());

if (platform === 'MAC002' || platform === 'MAC003') {
    require("@views.mobile/components/mobileComponents"); // Mobile 용 Global Components Manage
    require("./assets/common/scss/app.scss"); // 공통 컴포넌트용 SCSS
    require("./assets/mobile/scss/app.scss");
    routes =  mobileRouter;
} else {
    require("@views.pc/components/pcComponents"); // PC 용 Global Components Manage
    require("./assets/common/scss/app.scss"); // 공통 컴포넌트용 SCSS22
    require("./assets/pc/scss/app.scss");
    routes = pcRouter;
}

window.sessionStorage.setItem('domain', domain);
window.sessionStorage.setItem('platform', platform);
window.sessionStorage.setItem('system', system);
store.commit('platform', platform);

/*----------------------------- Router 설정22 ------------------------------*/
Vue.use(VueRouter);

let router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  scrollBehavior(to, from, savedPosition) {
    if(to.params && to.params.init) {
      let storePosition = store.state.savedPosition;
      if(!util.isEmpty(storePosition)) {
        delete storePosition[to.name];
        store.commit('savedPosition', storePosition);
      }
    }
    if(to.fullPath.indexOf('/promotion') !== -1 && savedPosition){
      let name = to.name;
      let storePosition = store.state.savedPosition;
      storePosition[name] = savedPosition;
      store.commit('savedPosition', storePosition);
      return savedPosition;
    }

    if (savedPosition) {
      let name = to.name;
      let storePosition = store.state.savedPosition;
      storePosition[name] = savedPosition;
      store.commit('savedPosition', storePosition);
      // return savedPosition;
    }      
    return { x: 0, y: 0 };
  },
  routes,
});


const originalPush = VueRouter.prototype.push;
const originalReplace = VueRouter.prototype.replace;

VueRouter.prototype.replace = function push(location) {
  return originalReplace.call(this, location).catch(err => {
    if (err.name !== 'NavigationDuplicated') {
      return err;
    }
    
    if(typeof location  == 'string') {
      if(location.indexOf('?') > -1) {
        location = location + "&q=1";
      } else {
        location = location + "?q=1";
      }
    } else {
      if(Object.prototype.hasOwnProperty.call(location, 'query')) {
        location.query.q = '1';
      } else {
        location.query = {
          q : '1'
        }
      }
    }
    return originalReplace.call(this, location);
  });
};

VueRouter.prototype.push = function push(location) {
    return originalPush.call(this, location).catch(err => {
      if (err.name !== 'NavigationDuplicated') {
        return err;
      }
      return router.replace(location);
    });
};

//로그인여부 체크
async function checkAuth(callbackSucc, callbackFail) {
  const result = await http.post('/member/checkAuth', {isloading : false});
  if(result.statusCode == 200) {
    callbackSucc();
  }else {
    callbackFail();
  }
}

async function checkNonemember(callbackSucc, callbackFail) {
  const result = await http.post('/member/checkNonemember', {isloading : false});
  if(result.statusCode == 200) {
    callbackSucc();
  }else {
    callbackFail();
  }
}

router.beforeEach((to, from, next) => {
  util.debug(from);
  util.debug(to);
  util.debug('Router From Page : ' + from.name + ' -> To Page : ' + to.name);
  
  store.commit('isCart', false);
  store.commit('isMypage', false);

  if(to.name == 'order') {
    if(from.name == 'shop' || from.name == 'order-order-success' || from.name == 'mypage-order-detail' || from.name == 'nonemember-order-detail'){
      alert('만료된 페이지 입니다.');
      next({name : 'shop', replace: true});
    }
  } else if(to.name == 'order-order-success') {
    if(from.name == 'shop' || from.name == 'mypage-order-detail' || from.name == 'nonemember-order-detail'){
      alert('만료된 페이지 입니다.');
      next({name : 'shop', replace: true});
    }
  }

  if(to.path.indexOf('mypage') > -1 || to.path.indexOf('/cs/inquiry/') > -1) {
    checkAuth(()=>{
      next();
    },
    () => {
      storage.setSessionStorage('redirectPath', {name : to.name, params : to.params, query : to.query});
      next({name : 'member-login'});
    });
  } else if(to.path.indexOf('nonemember') > -1) {
    checkNonemember(()=>{
      next();
    },
    () => {
      next({name : 'none-member-order'});
    });
  } else if(to.name == 'member-login') {
    checkAuth(()=>{
      next({name : 'mypage'});
    },
    () => {
      next();
    });
  } else {
    if(!util.isBlank(storage.getLocalStorage('member-dormantAccount')) && to.name != 'member-dormantAccount') {
      next({name : 'member-dormantAccount', params : storage.getLocalStorage('member-dormantAccount')})
    } else {
      next();
    }
  }
})


router.onError(error => {
  if (/ChunkLoadError:.*failed./i.test(error.message)) {
    util.debug("############################################");
    util.debug("router-error ::chunkLoadError");
    util.debug("router-error ::chunkLoadError");
    util.debug("router-error ::chunkLoadError");
    util.debug("router-error ::chunkLoadError");
    util.debug("############################################");
    window.location.reload();
  }else if (/Loading.*chunk.*failed./i.test(error.message)) {
    util.debug("############################################");
    util.debug("router-fail ::chunkLoadError");
    util.debug("router-fail ::chunkLoadError");
    util.debug("router-fail ::chunkLoadError");
    util.debug("router-fail ::chunkLoadError");
    util.debug("############################################");
    window.location.reload();
  }
});

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

Vue.use(VS2);
Vue.use(LoadScript);
Vue.use(VueCookies);
Vue.use(BootstrapVue);
Vue.use(VueScrollactive);
Vue.use(VueDaumPostcode);
Vue.use(VueCryptojs);
Vue.use(VueHtmlToPaper);
Vue.use(VueScrollmagic)

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

//애플 로그인
if(util.browser() == 'Safari') {
    Vue.loadScript("https://appleid.cdn-apple.com/appleauth/static/jsapi/appleid/1/en_US/appleid.auth.js");
}
Vue.use(VueAppleLogin, {
    clientId : process.env.VUE_APP_APPLE_CLIENT_KEY,
    scope : 'name email',
    redirectURI : domain + '/apple-auth',
    state : new Date().getTime() + "",
    nonce : process.env.VUE_APP_APPLE_NONCE,
    usePopup : true //or false defaults to false
});

Vue.use(Meta, {
    keyName: 'metaInfo',
    attribute: 'data-vue-meta',
    ssrAttribute: 'data-vue-meta-server-rendered',
    tagIDKeyName: 'vmid',  // 변화시키는 구분 값
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

if(isredirect) {
  router = new VueRouter();
}

Vue.use(VueGtm, {
  id: 'GTM-MKQNL7F', // Your GTM single container ID, array of container ids ['GTM-xxxxxx', 'GTM-yyyyyy'] or array of objects [{id: 'GTM-xxxxxx', queryParams: { gtm_auth: 'abc123', gtm_preview: 'env-4', gtm_cookies_win: 'x'}}, {id: 'GTM-yyyyyy', queryParams: {gtm_auth: 'abc234', gtm_preview: 'env-5', gtm_cookies_win: 'x'}}], // Your GTM single container ID or array of container ids ['GTM-xxxxxx', 'GTM-yyyyyy']
  // queryParams: {
  //   // Add URL query string when loading gtm.js with GTM ID (required when using custom environments)
  //   gtm_auth: 'AB7cDEf3GHIjkl-MnOP8qr',
  //   gtm_preview: 'env-4',
  //   gtm_cookies_win: 'x',
  // },
  defer: false, // Script can be set to `defer` to speed up page load at the cost of less accurate results (in case visitor leaves before script is loaded, which is unlikely but possible). Defaults to false, so the script is loaded `async` by default
  compatibility: false, // Will add `async` and `defer` to the script tag to not block requests for old browsers that do not support `async`
  nonce: '2726c7f26c', // Will add `nonce` to the script tag
  enabled: true, // defaults to true. Plugin can be disabled by setting this to false for Ex: enabled: !!GDPR_Cookie (optional)
  debug: false, // Whether or not display console logs debugs (optional)
  loadScript: true, // Whether or not to load the GTM Script (Helpful if you are including GTM manually, but need the dataLayer functionality in your components) (optional)
  vueRouter: router, // Pass the router instance to automatically sync with router (optional)
  // ignoredViews: ['homepage'], // Don't trigger events for specified router names (optional)
  trackOnNextTick: false, // Whether or not call trackView in Vue.nextTick
});

const vue = new Vue({
  store,
  router,
  render: h => h(App)
}).$mount('#app')

export default vue;