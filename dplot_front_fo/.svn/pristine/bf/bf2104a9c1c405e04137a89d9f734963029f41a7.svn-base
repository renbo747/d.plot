import Index from "@views.mobile/Index.vue";

import common from '@router/common.js';
import main from '@router.mobile/main.js';
import member from '@router.mobile/member.js';
import mypage from '@router.mobile/mypage.js';
import shop from '@router.mobile/shop.js';
import order from '@router.mobile/order.js';
import cs from '@router.mobile/cs.js';
import etc from '@router.mobile/etc.js';
import search from '@router.mobile/search.js';
import service from '@router.mobile/service.js';
import nonemember from '@router.mobile/nonemember.js';
import promotion from '@router.mobile/promotion.js';

let childRoute = [
  ... common,
  ... main,
  ... member,
  ... shop,
  ... mypage,
  ... order,
  ... cs,
  ... etc,
  ... search,
  ... service,
  ... nonemember,
  ... promotion
];

const routes = [
  {
    path: "/",
    name: "index",
    component: Index,
    redirect: "/shop",
    children: childRoute,
  },
  {
    path: "/:pathMatch(.*)*",
    name: "notfound",
    redirect: "/etc/error/404"
    //component: () => import("@views.mobile/NotFoundPage.vue")
  }
];

export default routes;
