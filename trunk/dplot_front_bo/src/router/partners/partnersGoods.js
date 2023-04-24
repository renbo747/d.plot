
import GoodsRegist from "@views.admin/goods/regist/GoodsRegist.vue";
import AllGoodsManage from "@views.admin/goods/manage/AllGoodsManage.vue";
import PartnersSide from "@views/partners/PartnersSide.vue";
import GoodsApprovalManage from "@views.admin/goods/manage/GoodsApprovalManage.vue";
import GoodsUploadAll from '@views.admin/goods/manage/GoodsUploadAll'

const route = {
    path: 'goods',
    name: 'partners.goods',
    component: PartnersSide,
    children: [
        {
            path: 'regist/goodsRegist',
            name: 'partners.goods.regist.goodsRegist',
            component: GoodsRegist
        },
        {
            path: 'manage/allGoodsManage',
            name: 'partners.goods.manage.allGoodsManage',
            component: AllGoodsManage
        },
        {
            path: 'manage/goodsApprovalManage',
            name: 'partners.goods.manage.goodsApprovalManage',
            component: GoodsApprovalManage
        },
        {
            path: 'manage/goodsUploadAll',
            name: 'partners.goods.manage.goodsUploadAll',
            component: GoodsUploadAll
        }
    ]
}

export default route;