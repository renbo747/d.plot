const route = [{
    path: "/member/login",
    name: "member-login",
    component: () => import("@views.mobile/member/Login.vue"),
  },
  {
    path: "/member/dormant-account",
    name: "member-dormantAccount",
    component: () => import("@views.mobile/member/DormantAccount.vue"),
  },
  {
    path: "/member/sign-up",
    name: "member-signUp",
    component: () => import("@views.mobile/member/SignUp.vue"),
  },
  {
    path: "/withdraw-complete",
    name: "withdraw-complete",
    component: () =>
      import("@views.mobile/mypage/activity/memberInfo/WithdrawComplete"),
  },
]
export default route;