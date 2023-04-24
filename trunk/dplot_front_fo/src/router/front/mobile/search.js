const route = [
  {
    path: "/search",
    name: "search",
    component: () => import("@views.mobile/search/Index.vue"),
  },
  {
    path: "/search/result",
    name: "search-result",
    redirect: { name: 'goods' },
    component: () => import("@views.mobile/search/Result.vue"),
    children: [{
      path: "goods",
      name: "goods",
      component: () => import("@views.mobile/search/Result.vue")
    },
    {
      path: "brand",
      name: "brand",
      component: () => import("@views.mobile/search/Result.vue")
    },
    {
      path: "contents",
      name: "contents",
      component: () => import("@views.mobile/search/Result.vue")
    }]
  },
]

export default route;