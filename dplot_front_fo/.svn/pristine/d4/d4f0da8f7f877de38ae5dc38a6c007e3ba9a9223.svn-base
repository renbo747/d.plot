const route = [{
  path: "/cs",
  name: "cs",
  component: () => import("@views.pc/cs/Index.vue"),
  redirect: "/cs/main",
  children: [
    {
      path: "/cs/main",
      name: "cs-main",
      component: () => import("@views.pc/cs/Main.vue"),
    },
    //자주하는 질문
    {
      path: "/cs/cs-faq/:faqtype",
      name: "cs-cs-faq",
      component: () => import("@views.pc/cs/CsFaq.vue"),
    },
    {
      path: "/cs/cs-find-result",
      name: "cs-cs-find-result",
      component: () => import("@views.pc/cs/CsFindResult.vue"),
    },
    //문의
    {
      path: "/cs/inquiry/index",
      name: "cs-inquiry-index",
      redirect: { name: 'inquiry' },
      component: () => import("@views.pc/cs/inquiry/Index.vue"),
      children: [
      {
        path: "inquiry",
        name: "inquiry",
        component: () => import("@views.pc/cs/inquiry/Index.vue")
      },
      {
        path: "qna",
        name: "qna",
        component: () => import("@views.pc/cs/inquiry/Index.vue")
      }
      ]
    },
    {
      path: "/cs/inquiry/detail",
      name: "cs-inquiry-detail",
      redirect: { name: 'cs-inquiry-index' },
      component: () => import("@views.pc/cs/inquiry/Detail.vue"),
      children: [{
        path: "inquiry/:idx",
        name: "detail-inquiry",
        component: () => import("@views.pc/cs/inquiry/Detail.vue")
      },
      {
        path: "qna/:idx",
        name: "detail-qna",
        component: () => import("@views.pc/cs/inquiry/Detail.vue")
      }]
    },
    {
      path: "/cs/inquiry/write",
      name: "cs-inquiry-write",
      redirect: { name: 'cs-inquiry-index' },
      component: () => import("@views.pc/cs/inquiry/Write.vue"),
      children: [{
        path: "register",
        name: "write-inquiry-register",
        component: () => import("@views.pc/cs/inquiry/Write.vue")
      },
      {
        path: "revise/:idx",
        name: "write-inquiry-revise",
        component: () => import("@views.pc/cs/inquiry/Write.vue")
      },
      {
        path: "qna/register",
        name: "write-qna-register",
        component: () => import("@views.pc/cs/inquiry/Write.vue")
      },
      {
        path: "qna/revise/:idx",
        name: "write-qna-revise",
        component: () => import("@views.pc/cs/inquiry/Write.vue")
      },
    ]
    },
    {
      path: "/cs/store-inquiry",
      name: "cs-inquiry-store-inquiry",
      component: () => import("@views.pc/cs/inquiry/StoreInquiry.vue"),
    },
    //공지사항
    {
      path: "/cs/notice",
      name: "cs-notice",
      component: () => import("@views.pc/cs/notice/Index.vue"),
    },
    {
      path: "/cs/notice/detail/:idx",
      name: "cs-notice-detail",
      component: () => import("@views.pc/cs/notice/Detail.vue"),
    },
  ],
},
]

export default route;