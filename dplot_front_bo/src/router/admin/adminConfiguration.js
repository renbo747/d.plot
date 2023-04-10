import ConfigurationManageSide from "@views.admin/side/ConfigurationManageSide";
import OperatorList from "@views.admin/configuration/operator/OperatorList";
import CodeManageList from "@views.admin/configuration/manage/CodeManageList";
import ConfigTerms from "@views.admin/configuration/manage/ConfigTerms";
import GoodsModifyAuth from "@views.admin/configuration/manage/GoodsModifyAuth";
import NotifyTplList from "@views.admin/configuration/manage/NotifyTplList";

const route = {
    path: 'configuration',
    name: 'admin.configuration',
    component: ConfigurationManageSide,
    children: [
        {
            path: 'manage/codeList',
            name: 'admin.configuration.manage.codelist',
            component: CodeManageList
        },
        {
            path: 'manage/configTerms',
            name: 'admin.configuration.manage.terms',
            component: ConfigTerms 
        },
        {
            path: 'manage/goodsModiftAuth',
            name: 'admin.configuration.manage.goodsmodifyauth',
            component: GoodsModifyAuth 
        },
        {
            path: 'manage/notifyTplList',
            name: 'admin.configuration.manage.notifylist',
            component: NotifyTplList 
        },
        {
            path: 'operator/operatorList',
            name: 'admin.configuration.operator.operatorlist',
            component: OperatorList
        },
    ]
}

export default route;
