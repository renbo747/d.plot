const route = [{
    path: "/common/kmcCertResult",
    name: "common-kmcCertResult",
    component: () => import("@views/common/components/KmcCertResult.vue")
  },
  {
    path: "/common/kmcCertPopup",
    name: "common-kmcCertReqPopup",
    component: () => import("@views/common/components/KmcCertReqPopup.vue")
  },
  {
    path: "/common/tossPayment",
    name: "common-tossPaymentPopup",
    component: () => import("@views/common/components/TossPayment.vue")
  },
  {
    path: "/common/naverPayment",
    name: "common-naverPaymentPopup",
    component: () => import("@views/common/components/NaverPayment.vue")
  },
  {
    path: "/common/private/policy",
    name: "common.private.policy",
    component: () => import("@views/common/components/PrivatePolicy.vue")
  },
  {
    path: "kakao-auth",
    name: "member-kakaoAuth",
    component: () => import("@views.common/member/KakaoAuth.vue"),
  },
  {
    path: "naver-auth",
    name: "member-naverAuth",
    component: () => import("@views.common/member/NaverAuth.vue"),
  },
  {
    path: "apple-auth",
    name: "member-appleAuth",
    component: () => import("@views.common/member/AppleAuth.vue"),
  },
  {
    path: "id-find-cert-result",
    name: "member-idFindCertResult",
    component: () => import("@views.common/member/IdFindCertResult.vue"),
  },
  {
    path: "password-reset-cert-result",
    name: "member-passwordResetCertResult",
    component: () => import("@views.common/member/PasswordResetCertResult.vue"),
  },
  {
    path: "order-tossPaymentSucc",
    name: "order-tossPaymentSucc",
    component: () => import("@views.common/order/TossPaymentSucc.vue")
  },
  {
    path: "order-tossPaymentFail",
    name: "order-tossPaymentFail",
    component: () => import("@views.common/order/TossPaymentFail.vue")
  },
  {
    path: "order-naverpaymentSucc",
    name: "order-naverPaymentSucc",
    component: () => import("@views.common/order/NaverPaymentSucc.vue")
  },
  {
    path: "claim-tossPaymentSucc",
    name: "claim-tossPaymentSucc",
    component: () => import("@views.common/claim/TossPaymentSucc.vue")
  },
  {
    path: "claim-tossPaymentFail",
    name: "claim-tossPaymentFail",
    component: () => import("@views.common/claim/TossPaymentFail.vue")
  },
  {
    path: "claim-naverPaymentSucc",
    name: "claim-naverPaymentSucc",
    component: () => import("@views.common/claim/NaverPaymentSucc.vue")
  },
  {
    path: "personal",
    name: "personal",
    component: () => import("@views.common/term/personal.vue")
  },
]
//모바일,웹 공통
export default route;