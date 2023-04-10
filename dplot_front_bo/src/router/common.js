const route = [{
    path: "/common/kmcCertResult",
    name: "common-kmcCertResult",
    component: () => import("@views/common/components/KmcCertResult.vue")
  },
    {
        path: "/common/private/policy",
        name: "common.private.policy",
        component: () => import("@views/common/components/PrivatePolicy.vue")
    }
]
//모바일,웹 공통
export default route;