import GoodsManageSide from "@views.admin/side/GoodsManageSide.vue";
import GoodsRegist from "@views.admin/goods/regist/GoodsRegist.vue";
import DealRegist from "@views.admin/goods/regist/DealRegist.vue";
import AllGoodsManage from "@views.admin/goods/manage/AllGoodsManage.vue";
import GoodsApprovalManage from "@views.admin/goods/manage/GoodsApprovalManage.vue";
import GoodsChangeAll from "@views.admin/goods/manage/GoodsChangeAll.vue";
import GoodsUploadAll from "@views.admin/goods/manage/GoodsUploadAll.vue";
import BrandList from "@views.admin/goods/brand/BrandList.vue";
import CategoryList from "@views.admin/goods/category/CategoryList.vue";
import BrandBannerList from '@/views/admin/goods/brand/BrandBannerList.vue';

const route = {
    path: 'goods',
    name: 'admin.goods',
    component: GoodsManageSide,
    children: [
        {
            path: 'regist/goodsRegist',
            name: 'admin.goods.regist.goodsRegist',
            component: GoodsRegist
        },
        {
            path: 'regist/dealRegist',
            name: 'admin.goods.regist.dealRegist',
            component: DealRegist
        },
        {
            path: 'manage/allGoodsManage',
            name: 'admin.goods.manage.allGoodsManage',
            component: AllGoodsManage
        },
        {
            path: 'manage/goodsApprovalManage',
            name: 'admin.goods.manage.goodsApprovalManage',
            component: GoodsApprovalManage
        },
        {
            path: 'manage/goodsChangeAll',
            name: 'admin.goods.manage.goodsChangeAll',
            component: GoodsChangeAll
        },
        {
            path: 'manage/goodsUploadAll',
            name: 'admin.goods.manage.goodsUploadAll',
            component: GoodsUploadAll
        },
        {
            path: 'brand/brandList',
            name: 'admin.goods.brand.brandList',
            component: BrandList
        },
        {
            path: 'brand/brandBannerList',
            name: 'admin.goods.brand.brandbannerlist',
            component: BrandBannerList
        },
        {
            path: 'category/categoryList',
            name: 'admin.goods.category.categoryList',
            component: CategoryList
        }
    ]
}

export default route;