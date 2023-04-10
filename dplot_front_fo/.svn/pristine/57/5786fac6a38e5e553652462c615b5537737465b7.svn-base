const route = [
  //마이페이지
  {
    path: "/mypage",
    name: "mypage",
    component: () => import("@views.pc/mypage/Index.vue"),
    redirect: "/mypage/dashboard",
    children: [
      {
        path: "/mypage/dashboard",
        name: "mypage-dashboard",
        component: () => import("@views.pc/mypage/Dashboard.vue"),
      },
      {
        path: "/mypage/grade",
        name: "mypage-grade",
        component: () => import("@views.pc/mypage/grade/Index.vue"),
      },
      //1:1문의
      {
        path: "/mypage/inquiry/index",
        name: "mypage-inquiry-index",
        redirect: { name: 'mypage-inquiry' },
        component: () => import("@views.pc/cs/inquiry/Index.vue"),
        children: [
        {
          path: "inquiry",
          name: "mypage-inquiry",
          component: () => import("@views.pc/cs/inquiry/Index.vue")
        },
        {
          path: "qna",
          name: "mypage-qna",
          component: () => import("@views.pc/cs/inquiry/Index.vue")
        }
        ]
      },
      {
        path: "/mypage/inquiry/detail",
        name: "mypage-inquiry-detail",
        redirect: { name: 'cs-inquiry-index' },
        component: () => import("@views.pc/cs/inquiry/Detail.vue"),
        children: [{
          path: "inquiry/:idx",
          name: "mypage-detail-inquiry",
          component: () => import("@views.pc/cs/inquiry/Detail.vue")
        },
        {
          path: "qna/:idx",
          name: "mypage-detail-qna",
          component: () => import("@views.pc/cs/inquiry/Detail.vue")
        }]
      },
      {
        path: "/mypage/inquiry/write",
        name: "mypage-inquiry-write",
        redirect: { name: 'cs-inquiry-index' },
        component: () => import("@views.pc/cs/inquiry/Write.vue"),
        children: [{
          path: "register",
          name: "mypage-write-inquiry-register",
          component: () => import("@views.pc/cs/inquiry/Write.vue")
        },
        {
          path: "revise/:idx",
          name: "mypage-write-inquiry-revise",
          component: () => import("@views.pc/cs/inquiry/Write.vue")
        },
        {
          path: "qna/register",
          name: "mypage-write-qna-register",
          component: () => import("@views.pc/cs/inquiry/Write.vue")
        },
        {
          path: "qna/revise/:idx",
          name: "mypage-write-qna-revise",
          component: () => import("@views.pc/cs/inquiry/Write.vue")
        },
      ]
      },          
      //1:1문의 끝
      {
        path: "/mypage/my-review",
        name: "mypage-my-review",
        redirect: "/mypage/my-review/before",
        component: () => import("@views.pc/mypage/activity/myreview/Index.vue"),
        children: [{
          path: "/mypage/my-review/before",
          name: "before",
          component: () => import("@views.pc/mypage/activity/myreview/Index.vue")
        },
        {
          path: "/mypage/my-review/after",
          name: "after",
          component: () => import("@views.pc/mypage/activity/myreview/Index.vue")
        }]
      },
      {
        path: "/mypage/my-review/write",
        name: "mypage-my-review-write",
        component: () => import("@views.pc/mypage/activity/myreview/Write.vue"),
      },
      {
        path: "/mypage/message",
        name: "mypage-message",
        component: () => import("@views.pc/mypage/activity/message/Index.vue"),
      },
      {
        path: "/mypage/restock",
        name: "mypage-restock",
        component: () => import("@views.pc/mypage/activity/restock/RestockIndex.vue"),
      },
      {
        path: "/mypage/info-confirm",
        name: "mypage-info-confirm",
        component: () =>
          import("@views.pc/mypage/activity/memberInfo/InfoConfirm.vue"),
      },
      {
        path: "/mypage/info-modify",
        name: "mypage-info-modify",
        component: () =>
          import("@views.pc/mypage/activity/memberInfo/InfoModify.vue"),
      },
      {
        path: "/mypage/member-withdraw",
        name: "mypage-member-withdraws",
        component: () =>
          import("@views.pc/mypage/activity/memberInfo/MemberWithdraw.vue"),
      },
      {
        path: "/mypage/coupon",
        name: "mypage-coupon",
        component: () => import("@views.pc/mypage/benefit/Coupon.vue"),
      },
      {
        path: "/mypage/epoint",
        name: "mypage-epoint",
        component: () => import("@views.pc/mypage/benefit/Reward.vue"),
      },
      {
        path: "/mypage/reward",
        name: "mypage-reward",
        component: () => import("@views.pc/mypage/benefit/Reward.vue"),
      },
      {
        path: "/mypage/employee-reward",
        name: "mypage-employee-reward",
        component: () => import("@views.pc/mypage/benefit/Reward.vue"),
      },
      {
        path: "/mypage/invitation",
        name: "mypage-invitation",
        component: () => import("@views.pc/mypage/benefit/Invitation.vue"),
      },
      {
        path: "/mypage/friend-invitation",
        name: "friend-invitation",
        component: () => import("@views.pc/mypage/benefit/FriendInvitation.vue"),
      },
      {
        path: "/mypage/like",
        name: "mypage-like",
        // redirect: { name: 'mz_like' },
        redirect: { name: 'g_like' },
        component: () => import("@views.pc/mypage/like/Index.vue"),
        children: [
        // {
        //   path: "mz_like",
        //   name: "mz_like",
        //   component: () => import("@views.pc/mypage/like/Index.vue")
        // },
        {
          path: "g_like",
          name: "g_like",
          component: () => import("@views.pc/mypage/like/Index.vue")
        },
        {
          path: "recent",
          name: "recent",
          component: () => import("@views.pc/mypage/like/Index.vue")
        }]
      },
      {
        path: "/mypage/as/:pid",
        name: "apply-as",
        component: () => import("@views.pc/mypage/shop/as/Index.vue"),
        children: [{
          path: "/mypage/as/revise/:pid",
          name: "apply-as-revise",
          component: () => import("@views.pc/mypage/shop/as/Index.vue")
        }]     
      },
      {
        path: "/mypage/as/detail/:pid",
        name: "mypage-as-detail",
        component: () => import("@views.pc/mypage/shop/as/AsDetail.vue"),
      },
      {
        path: "/mypage/as/apply/list",
        name: "mypage-as-apply-list",
        component: () => import("@views.pc/mypage/shop/as/AsApplyList.vue"),
      },
      {
        path: "/mypage/previous-order",
        name: "mypage-previous-order",
        component: () => import("@views.pc/mypage/shop/previousOrder/Index.vue"),
      },
      {
        path: "/mypage/order",
        name: "mypage-order",
        component: () => import("@views.pc/mypage/shop/order/Index.vue"),
      },
      {
        path: "/mypage/order/detail/:ordno",
        name: "mypage-order-detail",
        component: () => import("@views.pc/mypage/shop/order/Detail.vue"),
      },
      {
        path: "/mypage/claim",
        name: "mypage-claim",
        component: () => import("@views.pc/mypage/shop/claim/Index.vue"),
      },
      {
        path: '/mypage/claim/cancel-apply',
        name: 'mypage-claim-cancel-apply',
        component: () => import('@views.pc/mypage/shop/claim/ClaimApply.vue'),
      },
      {
        path: '/mypage/claim/return-apply',
        name: 'mypage-claim-return-apply',
        component: () => import('@views.pc/mypage/shop/claim/ClaimApply.vue'),
      },
      {
        path: '/mypage/claim/exchange-apply',
        name: 'mypage-claim-exchange-apply',
        component: () => import('@views.pc/mypage/shop/claim/ClaimApply.vue'),
      },
      {
        path: '/mypage/claim/cancel-detail/:clmno',
        name: 'mypage-claim-cancel-detail',
        component: () => import('@views.pc/mypage/shop/claim/ClaimDetail.vue'),
      },
      {
        path: '/mypage/claim/return-detail/:clmno',
        name: 'mypage-claim-return-detail',
        component: () => import('@views.pc/mypage/shop/claim/ClaimDetail.vue'),
      },
      {
        path: '/mypage/claim/exchange-detail/:clmno',
        name: 'mypage-claim-exchange-detail',
        component: () => import('@views.pc/mypage/shop/claim/ClaimDetail.vue'),
      }
    ],
  },
]

export default route;