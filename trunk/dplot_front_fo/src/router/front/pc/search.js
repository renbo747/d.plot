const route = [
    {
        path: "/search",
        name: "search",
        redirect: "/search/result",
      },
      {
        path: "/search/result",
        name: "search-result",
        redirect: { name: 'goods' },
        component: () => import("@views.pc/search/Result.vue"),
        children: [{
          path: "goods",
          name: "goods",
          component: () => import("@views.pc/search/Result.vue")
        },
        {
          path: "brand",
          name: "brand",
          component: () => import("@views.pc/search/Result.vue")
        },
        {
          path: "contents",
          name: "contents",
          component: () => import("@views.pc/search/Result.vue")
        }]
      },
]

export default route;