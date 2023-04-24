const route = [
    {
        path: "/shop",
        name: "shop",
        component: () => import("@views.mobile/shop/Index.vue"),
    },
    {
        path: "/shop/list/:idx",
        name: "shop-list",
        component: () => import("@views.mobile/shop/List.vue"),
    },
    {
        path: "/shop/newList",
        name: "shop-newList",
        component: () => import("@views.mobile/shop/NewList.vue"),
    },
    {
        path: "/shop/detail/:pid",
        name: "shop-detail",
        component: () => import("@views.mobile/shop/Detail.vue"),
    },
    {
        path: "/shop/promotion",
        name: "shop-promotion",
        redirect: { name: 'all' },
        component: () => import("@views.mobile/shop/promotion/Index.vue"),
        children: [{
            path: "all",
            name: "all",
            component: () => import("@views.mobile/shop/promotion/Index.vue")
        },            
        {
            path: "event",
            name: "event",
            component: () => import("@views.mobile/shop/promotion/Index.vue")
        },
        {
            path: "special",
            name: "special",
            component: () => import("@views.mobile/shop/promotion/Index.vue")
        }]    
    },
    {
        path: "/shop/promotion/detail/:eventidx",
        name: "promotion-detail",
        component: () => import("@views.mobile/shop/promotion/Detail.vue"),
    }]

export default route;