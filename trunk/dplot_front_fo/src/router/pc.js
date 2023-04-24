import Index from "@views.pc/Index.vue";

import common from '@router/common.js';
import main from '@router.pc/main.js';
import member from '@router.pc/member.js';
import mypage from '@router.pc/mypage.js';
import shop from '@router.pc/shop.js';
import order from '@router.pc/order.js';
import cs from '@router.pc/cs.js';
import etc from '@router.pc/etc.js';
import search from '@router.pc/search.js';
import service from '@router.pc/service.js';
import nonemember from '@router.pc/nonemember.js';
import promotion from '@router.pc/promotion.js';

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
    //component: () => import("@views.pc/NotFoundPage.vue")
  }
];

export default routes;
