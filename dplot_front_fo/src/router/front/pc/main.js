const route = [{
  path: "/main",
  name: "main",
  component: () => import("@views.pc/main/Index.vue"),
},
{
  path: "/magazine",
  name: "magazine",
  component: () => import("@views.pc/magazine/Index.vue"),
},
{
  path: "/magazine/keyword",
  name: "magazine-keyword",
  component: () => import("@views.pc/magazine/Keyword.vue"),
},
{
  path: "/magazine/review",
  name: "magazine-review",
  component: () => import("@views.pc/magazine/Review.vue"),
},
{
  path: "/magazine/trend",
  name: "magazine-trend",
  component: () => import("@views.pc/magazine/Trend.vue"),
},
{
  path: "/magazine/trend/detail/:tid",
  name: "magazine-trend-detail",
  component: () => import("@views.pc/magazine/TrendDetail.vue"),
},
{
  path: "/magazine/brand",
  name: "magazine-brand",
  component: () => import("@views.pc/magazine/Brand.vue"),
},
{
  path: "/magazine/brand/detail/:bid",
  name: "magazine-brand-detail",
  component: () => import("@views.pc/magazine/BrandDetail.vue"),
},
// 초대장 가입 이벤트
{
  path: "invitation-event",
  name: "invitation-event",
  component: () => import("@views.pc/invitation/InvitationEvent"),
},
]

export default route;