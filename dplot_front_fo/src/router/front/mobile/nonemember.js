const route = [
  // 비회원
  {
    path: "/etc/none-member-order",
    name: "none-member-order",
    component: () => import("@views.mobile/nonemember/NoneMemberOrder.vue"),
  },
  {
    path: '/nonemember',
    name: 'nonemember',
    component: () => import('@views.mobile/nonemember/Index.vue'),
  },
  {
    path: '/nonemember/as/:pid',
    name: 'nonemember-apply-as',
    component: () => import('@views.mobile/nonemember/shop/as/Index.vue'),
    children: [{
      path: "/nonemember/as/revise/:pid",
      name: "nonemember-apply-as-revise",
      component: () => import("@views.mobile/nonemember/shop/as/Index.vue")
    }]     
  },
  {
    path: '/nonemember/as/detail/:pid',
    name: 'nonemember-as-detail',
    component: () => import('@views.mobile/nonemember/shop/as/AsDetail.vue'),
  },
  {
    path: '/nonemember/as/apply/list',
    name: 'nonemember-as-apply-list',
    component: () => import('@views.mobile/nonemember/shop/as/AsApplyList.vue'),
  },
  {
    path: '/nonemember/order',
    name: 'nonemember-order',
    component: () => import('@views.mobile/nonemember/shop/order/Index.vue'),
  },
  {
    path: '/nonemember/order/detail/:ordno',
    name: 'nonemember-order-detail',
    component: () => import('@views.mobile/nonemember/shop/order/Detail.vue'),
  },
  {
    path: "/nonemember/claim",
    name: "nonemember-claim",
    component: () => import("@views.mobile/nonemember/shop/claim/Index.vue"),
  },
  {
    path: '/nonemember/claim/cancel-apply',
    name: 'nonemember-claim-cancel-apply',
    component: () => import('@views.mobile/nonemember/shop/claim/ClaimApply.vue'),
  },
  {
    path: '/nonemember/claim/return-apply',
    name: 'nonemember-claim-return-apply',
    component: () => import('@views.mobile/nonemember/shop/claim/ClaimApply.vue'),
  },
  {
    path: '/nonemember/claim/exchange-apply',
    name: 'nonemember-claim-exchange-apply',
    component: () => import('@views.mobile/nonemember/shop/claim/ClaimApply.vue'),
  },
  {
    path: '/nonemember/claim/cancel-detail/:clmno',
    name: 'nonemember-claim-cancel-detail',
    component: () => import('@views.mobile/nonemember/shop/claim/ClaimDetail.vue'),
  },
  {
    path: '/nonemember/claim/return-detail/:clmno',
    name: 'nonemember-claim-return-detail',
    component: () => import('@views.mobile/nonemember/shop/claim/ClaimDetail.vue'),
  },
  {
    path: '/nonemember/claim/exchange-detail/:clmno',
    name: 'nonemember-claim-exchange-detail',
    component: () => import('@views.mobile/nonemember/shop/claim/ClaimDetail.vue'),
  }
]

export default route
