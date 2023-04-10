import Vue from 'vue'
import VueRouter from 'vue-router'

/** js file import ************************************/
import storage from '@/js/storage'
import util from '@/js/util'
import store from '@/js/store'

/** vue file import ********************************/
import AdminMain from '@views.admin/Main.vue'
import AdminLogin from '@views.admin/AdminLogin.vue'

import adminGoods from "/src/router/admin/adminGoods.js";
import adminCs from "/src/router/admin/adminCs.js";
import adminPartners from "/src/router/admin/adminPartners.js";
import adminOperation from "/src/router/admin/adminOperation.js";
import adminPromotion from "/src/router/admin/adminPromotion.js";
import adminMember from "/src/router/admin/adminMember.js";
import adminConfiguration from "/src/router/admin/adminConfiguration.js";
import adminFavorit from "/src/router/admin/adminFavorit.js";
import adminOrder from "/src/router/admin/adminOrder.js";
import adminAdjust from "@router/admin/adminAdjust";
import adminStats from "@router.admin/adminStats";
import KmcCertReqPopup from "@views/common/components/KmcCertReqPopup";
import KmcCertResPopup from "@views/common/components/KmcCertResPopup";

Vue.use(VueRouter);

let adminRoute = [];
adminRoute.push(adminFavorit);
adminRoute.push(adminPartners);
adminRoute.push(adminGoods);
adminRoute.push(adminCs);
adminRoute.push(adminOperation);
adminRoute.push(adminPromotion);
adminRoute.push(adminMember);
adminRoute.push(adminConfiguration);
adminRoute.push(adminOrder);
adminRoute.push(adminAdjust);
adminRoute.push(adminStats);

const routes = [
    // {
    //   path: '/admin/dev',
    //   name: 'AdminDevReference',
    //   component: AdminDevReference
    // },
    {
        path: '/',
        name: 'admin.main',
        component: AdminMain,
        children: adminRoute
    },
    {
        path: '/login',
        name: 'admin.login',
        component: AdminLogin,
        meta: {authNotRequired: true}
    },
    {
        path: '/common/partnership/kmc/request',
        name: 'common.partnership.kmc.cert.req',
        component: KmcCertReqPopup,
        meta: {authNotRequired: true}
    },
    {
        path: '/common/partnership/kmc/response',
        name: 'common.partnership.kmc.cert.res',
        component: KmcCertResPopup,
        meta: {authNotRequired: true}
    },
    {
        path: "/:pathMatch(.*)*",
        name: "notfound",
        component: () => import("@views.admin/NotFoundPage.vue")
    }
];

const router = new VueRouter({
    mode: 'history',
    scrollBehavior(to, from, savedPosition) {
        // 항상 맨 위로 스크롤
        return {top: 0}
    },
    routes
})

router.beforeEach((to, from, next) => {

    // 로그인정보가 있는 경우에만 페이지 진입 허용
    if (to.matched.some(function (routeInfo) {
        return !routeInfo.meta.authNotRequired
    })) {
        if (util.isAuthorized(store.getters.CONSTANTS.MANAGER_SESSION)) {
            // 새로고침이 아닌 경우 storage에 저장된 검색조건 초기화
            // 새로고침하는 경우 from.name이 undefined로 들어옴
            let searchParams = storage.getSessionStorage('searchParams');
            if (!util.isNull(from.name) && !util.isNull(searchParams)) {
                storage.removeSessionStorage('searchParams');
            }

            if(to.name === 'admin.main') {
                router.push({name: 'admin.main.dashboard'});
            }
            next();
        } else {
            if(to.path !== '/') {
                alert("로그인 정보가 존재하지 않습니다.");
            }
            router.push({name: "admin.login"});
        }
    } else {
        next();
    }
})

// NavigationDuplicated 에러 무시
// - 같은 페이지로 router.push('path')를 하게될때 발생
const originalPush = VueRouter.prototype.push;
VueRouter.prototype.push = function push(location) {
    return originalPush.call(this, location).catch(err => err);
};

export default router;
