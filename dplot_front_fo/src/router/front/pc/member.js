const route = [{
    path: "/member/login",
    name: "member-login",
    component: () => import("@views.pc/member/Login.vue"),
  },
  // 회원가입 관련 페이지
  {
    path: "/member/sign-up",
    name: "member-signUp",
    component: () => import("@views.pc/member/SignUp.vue"),
  },
  {
    path: "/member/sign-up-executive",
    name: "member-sign-up-executive",
    component: () => import("@views.pc/member/SignUpExecutive.vue"),
  },
  {
    path: "/member/sign-up-agreement",
    name: "member-sign-up-agreement",
    component: () => import("@views.pc/member/SignUpAgreement.vue"),
  },
  {
    path: "/member/sign-up-complete",
    name: "member-sign-up-complete",
    component: () => import("@views.pc/member/SignUpComplete.vue"),
  },
  {
    path: "/member/sign-up-sns",
    name: "member-sign-up-sns",
    component: () => import("@views.pc/member/SignUpSns.vue"),
  },
  //id 관련 페이지
  {
    path: "/member/id-find",
    name: "member-id-find",
    component: () => import("@views.pc/member/IdFind.vue"),
  },
  {
    path: "/member/id-find-success",
    name: "member-id-find-success",
    component: () => import("@views.pc/member/IdFindSuccess.vue"),
  },
  {
    path: "/member/id-find-sns-success",
    name: "member-id-find-sns-success",
    component: () => import("@views.pc/member/IdFindSnsSuccess.vue"),
  },
  {
    path: "/member/id-find-fail",
    name: "member-id-find-fail",
    component: () => import("@views.pc/member/IdFindFail.vue"),
  },
  //password 관련 페이지
  {
    path: "/member/password-reset",
    name: "member-password-reset",
    component: () => import("@views.pc/member/PasswordReset.vue"),
  },
  {
    path: "/member/password-new",
    name: "member-password-new",
    component: () => import("@views.pc/member/PasswordNew.vue"),
  },
  {
    path: "/member/password-change",
    name: "member-password-change",
    component: () => import("@views.pc/member/PasswordChange.vue"),
  },
  {
    path: "/member/sns-connect",
    name: "member-sns-connect",
    component: () => import("@views.pc/member/SnsConnect.vue"),
  },
  {
    path: "/member/nav-signup",
    name: "nav-signup",
    component: () => import("@views.pc/member/NaverSignup.vue"),
  },
  {
    path: "/member/signup-already",
    name: "member-signup-already",
    component: () => import("@views.pc/member/SignUpAlready.vue"),
  },
  //휴면계정
  {
    path: "/member/dormant-account",
    name: "member-dormantAccount",
    component: () => import("@views.pc/member/DormantAccount.vue"),
  },
  {
    path: "/withdraw-complete",
    name: "withdraw-complete",
    component: () =>
      import("@views.pc/mypage/activity/memberInfo/WithdrawComplete"),
  },
]



export default route;