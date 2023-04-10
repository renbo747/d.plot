
import AdjustDecision from "@views.admin/adjust/decision/AdjustDecision";
import PartnersStatsBySalesDate from "@views/partners/PartnersStatsBySalesDate";
import PartnersSide from "@views/partners/PartnersSide";

const route = {
    path: 'adjust',
    name: 'partners.adjust',
    component: PartnersSide,
    children: [
        {
            path: 'decision',
            name: 'partners.adjust.decision',
            component: AdjustDecision,
        },
        {
            path: 'stat',
            name: 'partners.adjust.stat',
            component: PartnersStatsBySalesDate,
        }
    ]
}

export default route;