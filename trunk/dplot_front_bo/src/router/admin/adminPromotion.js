import PromotionManageSide from "@views.admin/side/PromotionManageSide";
import EventList from "@views.admin/promotion/event/manage/EventList";
import CouponList from "@views.admin/promotion/coupon/CouponList";
import GiftList from "@views.admin/promotion/promotion/GiftList";
import RecomReward from "@views.admin/promotion/promotion/RecomReward";
import PromotionList from '@views.admin/promotion/promotion/PromotionList';
import ReserveList from '@views.admin/promotion/reserve/ReserveList';
import AutoPayment from '@views.admin/promotion/reserve/AutoPayment';
import AnnounceList from "@views.admin/promotion/event/announce/AnnounceList";
import EpointList from "@views.admin/promotion/promotion/EpointList";
import SerialList from "@views.admin/promotion/promotion/serial/SerialList";

const route = {
    path: 'promotion',
    name: 'admin.promotion',
    component: PromotionManageSide,
    children: [
        {
            path: 'event/manage',
            name: 'admin.promotion.event.manage',
            component: EventList
        },
        {
            path: 'event/announce',
            name: 'admin.promotion.event.announce',
            component: AnnounceList
        },
        // {
        //     path: 'checkevent/manage',
        //     name: 'admin.promotion.checkevent.manage',
        //     component: CheckEventList
        // },
        {
            path: 'coupon/couponList',
            name: 'admin.promotion.coupon.couponlist',
            component: CouponList
        },
        {
            path: 'promotion/giftList',
            name: 'admin.promotion.promotion.giftlist',
            component: GiftList
        },
        {
            path: 'promotion/recomReward',
            name: 'admin.promotion.promotion.recomreward',
            component: RecomReward
        },
        {
            path: 'promotion/epoint',
            name: 'admin.promotion.promotion.epoint',
            component: EpointList
        },
        {
            path: 'promotion/promotionList',
            name: 'admin.promotion.promotion.promotionlist',
            component: PromotionList
        },
        {
            path: 'promotion/serial',
            name: 'admin.promotion.promotion.serial',
            component: SerialList
        },
        {
            path: 'reserve/reserveList',
            name: 'admin.promotion.reserve.reservelist',
            component: ReserveList
        },
        {
            path: 'reserve/autoPayment',
            name: 'admin.promotion.reserve.autopayment',
            component: AutoPayment
        },
        {
            path: 'reserve/autoPayment',
            name: 'admin.promotion.reserve.autopayment',
            component: AutoPayment
        },


    ]
}

export default route;
