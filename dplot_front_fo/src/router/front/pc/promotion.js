const route = [
    // 프로모션 페이지
    {
        path: "/shop/promotion/welcome",
        name: "welcome",
        component: () => import("@views.pc/promotion/Index_0915.vue"),
      },
      // {
      //   path: "/shop/promotion/raffle",
      //   name: "raffle",
      //   component: () => import("@views.pc/promotion/Raffle.vue"),
      // },
      {
        path: "/shop/promotion/raffle",
        name: "raffle2",
        component: () => import("@views.pc/promotion/Raffle2.vue"),
      },
      {
        path: "/shop/promotion/grandopen",
        name: "grand",
        component: () => import("@views.pc/promotion/Grand.vue"),
      },
      {
        path: "/test/promotion/grandopen",
        name: "grand_test",
        component: () => import("@views.pc/promotion/Grand_test.vue"),
      },
      {
        path: "/shop/promotion/holiday",
        name: "holiday",
        component: () => import("@views.pc/promotion/holiday/Holiday.vue"),
      }
]

export default route;