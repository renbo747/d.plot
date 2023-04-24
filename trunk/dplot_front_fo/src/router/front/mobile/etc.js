const route = [{
    path: "/etc/subscribe",
    name: "etc-subscribe",
    component: () => import("@views.mobile/etc/Subscribe"),
  },
  {
    path: "/etc/news-letter",
    name: "etc-news-letter",
    component: () => import("@views.mobile/etc/NewsLetter"),
  },
  {
    path: "/etc/error/:code",
    name: "etc-error",
    component: () => import("@views.mobile/etc/Error"),
  },
]

export default route


