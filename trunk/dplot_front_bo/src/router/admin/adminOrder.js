import OrderManageSide from "@views.admin/side/OrderManageSide";
import AllOrderManage from "@views.admin/order/manage/AllOrderManage";
import WaitingDeposit from "@views.admin/order/manage/WaitingDeposit";
import PreparingGoods from "@views.admin/order/manage/PreparingGoods";
import PreparingDelivery from "@views.admin/order/manage/PreparingDelivery";
import InDelivery from "@views.admin/order/manage/InDelivery";
import CompleteDelivery from "@views.admin/order/manage/CompleteDelivery";
import CancelList from "@views.admin/order/claim/CancelList";
import ReturnList from "@views.admin/order/claim/ReturnList";
import ExchangeList from "@views.admin/order/claim/ExchangeList";
import RefundList from "@views.admin/order/claim/RefundList";

const route = {
    path: 'order',
    name: 'admin.order',
    component: OrderManageSide,
    children: [
        {
            path: 'manage/allOrderManage',
            name: 'admin.order.manage.allordermanage',
            component: AllOrderManage
        },
        {
            path: 'manage/waitingDeposit',
            name: 'admin.order.manage.waitingdeposit',
            component: WaitingDeposit
        },
        {
            path: 'manage/preparingGoods',
            name: 'admin.order.manage.preparinggoods',
            component: PreparingGoods
        },
        {
            path: 'manage/preparingDelivery',
            name: 'admin.order.manage.preparingdelivery',
            component: PreparingDelivery
        },
        {
            path: 'manage/inDelivery',
            name: 'admin.order.manage.indelivery',
            component: InDelivery
        },
        {
            path: 'manage/completeDelivery',
            name: 'admin.order.manage.completedelivery',
            component: CompleteDelivery
        },
        {
            path: 'claim/cancelList',
            name: 'admin.order.claim.cancellist',
            component: CancelList
        },
        {
            path: 'claim/returnList',
            name: 'admin.order.claim.returnlist',
            component: ReturnList
        },
        {
            path: 'claim/exchangeList',
            name: 'admin.order.claim.exchangelist',
            component: ExchangeList
        },
        {
            path: 'claim/refundList',
            name: 'admin.order.claim.refundlist',
            component: RefundList
        }
    ]
}

export default route;
