const route = [{
  path: "/main",
  name: "main",
  component: () => import("@views.mobile/main/Index.vue"),
},
{
  path: "/magazine/keyword",
  name: "magazine-keyword",
  component: () => import("@views.mobile/magazine/Keyword.vue"),
},
{
  path: "/magazine/trend",
  name: "magazine-trend",
  component: () => import("@views.mobile/magazine/Trend.vue"),
},
{
  path: "/magazine/trend/detail/:tid",
  name: "magazine-trend-detail",
  component: () => import("@views.mobile/magazine/TrendDetail.vue"),
},
{
  path: "/magazine/brand",
  name: "magazine-brand",
  component: () => import("@views.mobile/magazine/Brand.vue"),
},
{
  path: "/magazine/review",
  name: "magazine-review",
  component: () => import("@views.mobile/magazine/Review.vue"),
},
{
  path: "/magazine/brand/detail/:bid",
  name: "magazine-brand-detail",
  component: () => import("@views.mobile/magazine/BrandDetail.vue"),
},
// 초대장 가입 이벤트
{
  path: "invitation-event",
  name: "invitation-event",
  component: () => import("@views.mobile/invitation/InvitationEvent.vue"),
},
]

export default route;