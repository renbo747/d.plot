const route = [
  {
    path: '/cs/main',
    name: 'cs-main',
    component: () => import('@views.mobile/cs/Index.vue'),
  },
  {
    path: '/cs/cs-faq/:faqtype',
    name: 'cs-cs-faq',
    component: () => import('@views.mobile/cs/CsFaq.vue'),
  },
  {
    path: '/cs/cs-find-result',
    name: 'cs-cs-find-result',
    component: () => import('@views.mobile/cs/CsFindResult.vue'),
  },
  {
    path: '/cs/inquiry/index',
    name: 'cs-inquiry-index',
    redirect: { name: 'inquiry' },
    component: () => import('@views.mobile/cs/inquiry/Index.vue'),
    children: [{
      path: "inquiry",
      name: "inquiry",
      component: () => import("@views.mobile/cs/inquiry/Index.vue")
    },
    {
      path: "qna",
      name: "qna",
      component: () => import("@views.mobile/cs/inquiry/Index.vue")
    }]
  },
  {
    path: '/cs/inquiry/detail',
    name: 'cs-inquiry-detail',
    redirect: { name: 'cs-inquiry-index' },
    component: () => import("@views.mobile/cs/inquiry/Detail.vue"),
    children: [{
      path: "inquiry/:idx",
      name: "detail-inquiry",
      component: () => import("@views.mobile/cs/inquiry/Detail.vue")
    },
    {
      path: "qna/:idx",
      name: "detail-qna",
      component: () => import("@views.mobile/cs/inquiry/Detail.vue")
    }]
  },
  {
    path: '/cs/inquiry/write',
    name: 'cs-inquiry-write',
    component: () => import('@views.mobile/cs/inquiry/Write.vue'),
  },
  {
    path: '/cs/inquiry/inquiry-qna',
    name: 'cs-inquiry-qna',
    component: () => import('@views.mobile/cs/inquiry/InquiryQna.vue'),
  },
  {
    path: '/cs/notice',
    name: 'cs-notice',
    component: () => import('@views.mobile/cs/notice/Index.vue'),
  },
  {
    path: '/cs/notice/detail/:idx',
    name: 'cs-notice-detail',
    component: () => import('@views.mobile/cs/notice/Detail.vue'),
  },
]

export default route
