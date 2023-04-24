const route = [
    {
        path: "/shop",
        name: "shop",
        component: () => import("@views.pc/shop/Index.vue"),
    }, 
    {
        path: "/shop/detail/:pid",
        name: "shop-detail",
        component: () => import("@views.pc/shop/Detail.vue"),
    },
    {
        path: "/shop/list/:idx",
        name: "shop-list",
        component: () => import("@views.pc/shop/List.vue"),
    },
    {
        path: "/shop/new-list",
        name: "shop-new-list",
        component: () => import("@views.pc/shop/NewList.vue"),
    },
    {
        path: "/shop/promotion",
        name: "shop-promotion",
        redirect: { name: 'all' },
        component: () => import("@views.pc/shop/promotion/Index.vue"),
        children: [{
            path: "all",
            name: "all",
            component: () => import("@views.pc/shop/promotion/Index.vue")
        },            
        {
            path: "event",
            name: "event",
            component: () => import("@views.pc/shop/promotion/Index.vue")
        },
        {
            path: "special",
            name: "special",
            component: () => import("@views.pc/shop/promotion/Index.vue")
        }] 
    },
    {
        path: "/shop/promotion/detail/:eventidx",
        name: "promotion-detail",
        component: () => import("@views.pc/shop/promotion/Detail.vue"),
    }
]

export default route;