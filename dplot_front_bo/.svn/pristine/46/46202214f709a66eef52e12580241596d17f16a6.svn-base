import CSManageSide from "@views.admin/side/CSManageSide";
import FaqList from "@views.admin/cs/faq/FaqList";
import ProductList from "@views.admin/cs/product/ProductList";
import OneOneList from "@views.admin/cs/oneone/OneOneList";
import PartnersList from "@views.admin/cs/partners/PartnersList";
import AsList from "@views.admin/cs/as/AsList";
import RepTemplateList from "@views.admin/cs/reptemplate/RepTemplateList";

const route = {
    path: 'cs',
    name: 'admin.cs',
    component: CSManageSide,
    children: [
        {
            path: 'faq',
            name: 'admin.cs.faq',
            component: FaqList
        },
        {
            path: 'product',
            name: 'admin.cs.product',
            component: ProductList
        },
        {
            path: 'oneone',
            name: 'admin.cs.oneone',
            component: OneOneList
        },
        {
            path: 'partners',
            name: 'admin.cs.partners',
            component: PartnersList
        },
        {
            path: 'aslist',
            name: 'admin.cs.aslist',
            component: AsList
        },
        {
            path: 'reptemplatelist',
            name: 'admin.cs.reptemplatelist',
            component: RepTemplateList
        },
    ]
}

export default route;