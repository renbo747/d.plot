
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
import PartnersSide from "@views/partners/PartnersSide";

const route = {
    path: 'order',
    name: 'partners.order',
    component: PartnersSide,
    children: [
        {
            path: 'manage/allOrderManage',
            name: 'partners.order.manage.allordermanage',
            component: AllOrderManage
        },
        {
            path: 'manage/waitingDeposit',
            name: 'partners.order.manage.waitingdeposit',
            component: WaitingDeposit
        },
        {
            path: 'manage/preparingGoods',
            name: 'partners.order.manage.preparinggoods',
            component: PreparingGoods
        },
        {
            path: 'manage/preparingDelivery',
            name: 'partners.order.manage.preparingdelivery',
            component: PreparingDelivery
        },
        {
            path: 'manage/inDelivery',
            name: 'partners.order.manage.indelivery',
            component: InDelivery
        },
        {
            path: 'manage/completeDelivery',
            name: 'partners.order.manage.completedelivery',
            component: CompleteDelivery
        },
        {
            path: 'claim/cancelList',
            name: 'partners.order.claim.cancellist',
            component: CancelList
        },
        {
            path: 'claim/returnList',
            name: 'partners.order.claim.returnlist',
            component: ReturnList
        },
        {
            path: 'claim/exchangeList',
            name: 'partners.order.claim.exchangelist',
            component: ExchangeList
        }
    ]
}

export default route;
