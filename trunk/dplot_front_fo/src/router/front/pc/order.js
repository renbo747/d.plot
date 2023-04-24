const route = [
    // 장바구니
    {
        path: "/cart",
        name: "cart",
        component: () => import("@views.pc/cart/Index.vue"),
      },
      // 주문 결제 완료
      {
        path: "/order",
        name: "order",
        component: () => import("@views.pc/order/Index.vue"),
      },
      {
        path: "/order/order-success/:ordno",
        name: "order-order-success",
        component: () => import("@views.pc/order/OrderSuccess.vue"),
      },
      {
        path: "/order/delivery-info",
        name: "order-delivery-info",
        component: () => import("@views.pc/order/DeliveryInfo.vue"),
      },
]


export default route;