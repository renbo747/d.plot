const route = [
  // 마이페이지
  {
    path: '/mypage',
    name: 'mypage',
    component: () => import('@views.mobile/mypage/Index.vue'),
  },
  {
    path: '/mypage/grade',
    name: 'mypage-grade',
    component: () => import('@views.mobile/mypage/grade/Index.vue'),
  },
  {
    path: '/mypage/my-review',
    name: 'mypage-my-review',
    redirect: { name: 'before' },
    component: () => import('@views.mobile/mypage/activity/myreview/Index.vue'),
    children: [{
      path: "before",
      name: "before",
      component: () => import('@views.mobile/mypage/activity/myreview/Index.vue'),
    },
    {
      path: "after",
      name: "after",
      component: () => import('@views.mobile/mypage/activity/myreview/Index.vue'),
    }]
  },
  {
    path: '/mypage/my-review/write',
    name: 'mypage-my-review-write',
    component: () => import('@views.mobile/mypage/activity/myreview/Write.vue'),
  },
  {
    path: '/mypage/message',
    name: 'mypage-message',
    component: () => import('@views.mobile/mypage/activity/message/Index.vue'),
  },
  {
    path: '/mypage/restock',
    name: 'mypage-restock',
    component: () => import('@views.mobile/mypage/activity/restock/RestockIndex.vue'),
  },
  {
    path: '/mypage/info-confirm',
    name: 'mypage-info-confirm',
    component: () =>
      import('@views.mobile/mypage/activity/memberInfo/InfoConfirm.vue'),
  },
  {
    path: '/mypage/info-modify',
    name: 'mypage-info-modify',
    component: () =>
      import('@views.mobile/mypage/activity/memberInfo/InfoModify.vue'),
  },
  {
    path: '/mypage/member-withdraw',
    name: 'mypage-member-withdraw',
    component: () =>
      import('@views.mobile/mypage/activity/memberInfo/MemberWithdraw.vue'),
  },
  {
    path: '/mypage/coupon',
    name: 'mypage-coupon',
    component: () => import('@views.mobile/mypage/benefit/Coupon.vue'),
  },
  {
    path: '/mypage/epoint',
    name: 'mypage-epoint',
    component: () => import('@views.mobile/mypage/benefit/Reward.vue'),
  },
  {
    path: '/mypage/reward',
    name: 'mypage-reward',
    component: () => import('@views.mobile/mypage/benefit/Reward.vue'),
  },
  {
    path: '/mypage/employee-reward',
    name: 'mypage-employee-reward',
    component: () => import('@views.mobile/mypage/benefit/Reward.vue'),
  },
  {
    path: '/mypage/invitation',
    name: 'mypage-invitation',
    component: () => import('@views.mobile/mypage/benefit/Invitation.vue'),
  },
  {
    path: '/mypage/like',
    name: 'mypage-like',
    // redirect: { name: 'mz_like' },
    redirect: { name: 'g_like' },
    component: () => import('@views.mobile/mypage/like/Index.vue'),
    children: [
    // {
    //   path: "mz_like",
    //   name: "mz_like",
    //   component: () => import('@views.mobile/mypage/activity/myreview/Index.vue'),
    // },
    {
      path: "g_like",
      name: "g_like",
      component: () => import('@views.mobile/mypage/activity/myreview/Index.vue'),
    },
    {
      path: "recent",
      name: "recent",
      component: () => import('@views.mobile/mypage/activity/myreview/Index.vue'),
    }]
  },
  {
    path: '/mypage/as/:pid',
    name: 'apply-as',
    component: () => import('@views.mobile/mypage/shop/as/Index.vue'),
    children: [{
      path: "/mypage/as/revise/:pid",
      name: "apply-as-revise",
      component: () => import("@views.mobile/mypage/shop/as/Index.vue")
    }]     
  },
  {
    path: '/mypage/as/detail/:pid',
    name: 'mypage-as-detail',
    component: () => import('@views.mobile/mypage/shop/as/AsDetail.vue'),
  },
  {
    path: '/mypage/as/apply/list',
    name: 'mypage-as-apply-list',
    component: () => import('@views.mobile/mypage/shop/as/AsApplyList.vue'),
  },
  {
    path: '/mypage/order',
    name: 'mypage-order',
    component: () => import('@views.mobile/mypage/shop/order/Index.vue'),
  },
  {
    path: "/mypage/previous-order",
    name: "mypage-previous-order",
    component: () => import("@views.mobile/mypage/shop/previousOrder/Index.vue"),
  },
  {
    path: '/mypage/order/detail/:ordno',
    name: 'mypage-order-detail',
    component: () => import('@views.mobile/mypage/shop/order/Detail.vue'),
  },
  {
    path: "/mypage/claim",
    name: "mypage-claim",
    component: () => import("@views.mobile/mypage/shop/claim/Index.vue"),
  },
  {
    path: '/mypage/claim/cancel-apply',
    name: 'mypage-claim-cancel-apply',
    component: () => import('@views.mobile/mypage/shop/claim/ClaimApply.vue'),
  },
  {
    path: '/mypage/claim/return-apply',
    name: 'mypage-claim-return-apply',
    component: () => import('@views.mobile/mypage/shop/claim/ClaimApply.vue'),
  },
  {
    path: '/mypage/claim/exchange-apply',
    name: 'mypage-claim-exchange-apply',
    component: () => import('@views.mobile/mypage/shop/claim/ClaimApply.vue'),
  },
  {
    path: '/mypage/claim/cancel-detail/:clmno',
    name: 'mypage-claim-cancel-detail',
    component: () => import('@views.mobile/mypage/shop/claim/ClaimDetail.vue'),
  },
  {
    path: '/mypage/claim/return-detail/:clmno',
    name: 'mypage-claim-return-detail',
    component: () => import('@views.mobile/mypage/shop/claim/ClaimDetail.vue'),
  },
  {
    path: '/mypage/claim/exchange-detail/:clmno',
    name: 'mypage-claim-exchange-detail',
    component: () => import('@views.mobile/mypage/shop/claim/ClaimDetail.vue'),
  }
]

export default route
