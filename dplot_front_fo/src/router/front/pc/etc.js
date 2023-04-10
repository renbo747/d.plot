const route = [
  {
    path: "/etc/error/:code",
    name: "etc-error",
    component: () => import("@views.pc/etc/Error.vue"),
  },
  {
    path: "/etc/news-letter",
    name: "etc-news-letter",
    component: () => import("@views.pc/etc/NewsLetter"),
  },
]

export default route