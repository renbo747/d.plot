const route = [
    // 장바구니 & 주문결제
    {
        path: "/cart",
        name: "cart",
        component: () => import("@views.mobile/cart/Index.vue"),
    },
    {
        path: "/order",
        name: "order",
        component: () => import("@views.mobile/order/Index.vue"),
    },
    {
        path: "/order/delivery-info/:ordno",
        name: "order-delivery-info",
        component: () => import("@views.mobile/order/DeliveryInfo.vue"),
    },
]

export default route;