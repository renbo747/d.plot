import AdjustManageSide from "@views.admin/side/AdjustManageSide";
import AdjustEstimate from "@views.admin/adjust/estimate/AdjustEstimate";
import AdjustDecision from "@views.admin/adjust/decision/AdjustDecision";

const route = {
    path: 'adjust',
    name: 'admin.adjust',
    component: AdjustManageSide,
    children: [
        {
            path: 'estimate',
            name: 'admin.adjust.estimate',
            component: AdjustEstimate,
        },
        {
            path: 'decision',
            name: 'admin.adjust.decision',
            component: AdjustDecision,
        }
    ]
}

export default route;