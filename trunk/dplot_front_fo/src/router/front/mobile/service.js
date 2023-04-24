const route = [ 
  {
    path: "/service",
    name: "service",
    component: () => import("@views.mobile/service/Index.vue"),
  },
  {
    path: "/service/as",
    name: "service-as",
    component: () => import("@views.mobile/service/As.vue"),
  },
  {
    path: "/service/intro",
    name: "service-intro",
    component: () => import("@views.mobile/service/Intro.vue"),
  }
]

export default route


