import Vue from 'vue'
import VueRouter from 'vue-router'

/** js file import ************************************/
import storage from '@/js/storage'
import util from '@/js/util'
import store from '@/js/store'

import PartnershipAuth from '@views.admin/common/partnership/PartnershipAuth.vue'
import PartnershipAgree from '@views.admin/common/partnership/PartnershipAgree.vue'
import PartnershipJoin from '@views.admin/common/partnership/PartnershipJoin.vue'
import PartnershipComplete from '@views.admin/common/partnership/PartnershipComplete.vue'
import PartnershipWait from '@views.admin/common/partnership/PartnershipWait.vue'
import PartnershipDelivTemplate from '@views.admin/common/partnership/PartnershipDelivTemplate.vue'

import PartnersMain from "@views/partners/PartnersMain";
import PartnersLogin from "@views/partners/PartnersLogin";

import partnersGoods from "/src/router/partners/partnersGoods.js";
import partnersOperation from "/src/router/partners/partnersOperation.js";
import partnersOrder from "/src/router/partners/partnersOrder.js";
import partnersAdjust from "/src/router/partners/partnersAdjust.js";

import PartnersSide from "@views/partners/PartnersSide.vue";
import PartnersInfoAuth from "@views/partners/PartnersInfoAuth.vue";
import KmcCertReqPopup from "@views/common/components/KmcCertReqPopup";
import KmcCertResPopup from "@views/common/components/KmcCertResPopup";
import PartnersDashBoard from "@views/partners/PartnersDashBoard";


Vue.use(VueRouter);

let partnersRoute = [];
partnersRoute.push(partnersGoods);
partnersRoute.push(partnersOperation);
partnersRoute.push(partnersOrder);
partnersRoute.push(partnersAdjust);

const infoRoutes = {
    path: 'info',
    name: 'partners.info',
    component: PartnersSide,
    children: [
        {
            path: 'auth',
            name: 'partners.info.auth',
            component: PartnersInfoAuth
        },
    ],
}
partnersRoute.push(infoRoutes);

const dashBoardRoute = {
    path: 'main',
    name: 'partners.main.dashboard',
    component: PartnersDashBoard
}
partnersRoute.push(dashBoardRoute);


const routes = [
    {
        path: '/',
        name: 'partners.main',
        component: PartnersMain,
        children: partnersRoute
    },
    {
        path: '/login',
        name: 'partners.login',
        component: PartnersLogin,
        meta: {authNotRequired: true}
    },
    {
        path: '/common/partnership/auth',
        name: 'common.partnership.auth',
        component: PartnershipAuth,
        meta: {authNotRequired: true}
    },
    {
        path: '/common/partnership/agree',
        name: 'common.partnership.agree',
        component: PartnershipAgree,
        meta: {authNotRequired: true}
    },
    {
        path: '/common/partnership/join',
        name: 'common.partnership.join',
        component: PartnershipJoin,
        meta: {authNotRequired: true}
    },
    {
        path: '/common/partnership/complete',
        name: 'common.partnership.complete',
        component: PartnershipComplete,
        meta: {authNotRequired: true}
    },
    {
        path: '/common/partnership/wait',
        name: 'common.partnership.wait',
        component: PartnershipWait,
        meta: {authNotRequired: true}
    },
    {
        path: '/common/partnership/template',
        name: 'common.partnership.template',
        component: PartnershipDelivTemplate,
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
            if(to.name === 'partners.main') {
                router.push({name: 'partners.main.dashboard'});
            }
            next();
        } else {
            if(to.path !== '/') {
                alert("로그인 정보가 존재하지 않습니다.");
            }
            router.push({name: "partners.login"});
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
