
import PartnersSide from "@views/partners/PartnersSide";
import NoticeList from "@views.admin/partners/notice/NoticeList";
import ConsentList from "@views.admin/partners/consent/ConsentList";
import ReviewList from "@views.admin/operation/review/ReviewList";
import ProductList from "@views.admin/cs/product/ProductList";
import OneOneList from "@views.admin/cs/oneone/OneOneList";
import AsList from "@views.admin/cs/as/AsList";
import PartnersList from '@/views/admin/cs/partners/PartnersList';

const route = {
    path: 'operation',
    name: 'partners.operation',
    component: PartnersSide,
    children: [
        {
            path: 'noticelist',
            name: 'partners.partners.notice',
            component: NoticeList
        },
        {
            path: 'consentlist',
            name: 'partners.partners.consent',
            component: ConsentList
        },
        {
            path: 'reviewlist',
            name: 'partners.operation.reviewlist',
            component: ReviewList
        },
        {
            path: 'product',
            name: 'partners.cs.product',
            component: ProductList
        },
        {
            path: 'oneone',
            name: 'partners.cs.oneone',
            component: OneOneList
        },
        {
            path: 'aslist',
            name: 'partners.cs.aslist',
            component: AsList
        },
        {
            path: 'partners',
            name: 'partners.cs.partners',
            component: PartnersList
        },
    ]
}

export default route;