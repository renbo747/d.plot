import PartnersManageSide from "@views.admin/side/PartnersManageSide";
import PartnersApplyList from "@views.admin/partners/apply/PartnersApplyList";
import NoticeList from "@views.admin/partners/notice/NoticeList";
import ConsentList from "@views.admin/partners/consent/ConsentList";
import StatusList from "@views.admin/partners/status/StatusList";

const route = {
    path: 'partners',
    name: 'admin.partners',
    component: PartnersManageSide,
    children: [
        {
            path: 'applyList',
            name: 'admin.partners.applyList',
            component: PartnersApplyList,
        },
        {
            path: 'notice',
            name: 'admin.partners.notice',
            component: NoticeList
        },
        {
            path: 'consent',
            name: 'admin.partners.consent',
            component: ConsentList
        },
        {
            path: 'status',
            name: 'admin.partners.status',
            component: StatusList
        }
    ]
}

export default route;