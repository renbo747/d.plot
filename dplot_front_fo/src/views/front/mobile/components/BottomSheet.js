import BottomCommon from "@views.mobile/components/BottomCommon.js"
export default {
    mixins : [BottomCommon],
    props: {
        isProductInfo: {
            type: Boolean,
            default: false,
        },
        productInfo: {
            type: Object
        },
        items : {
            type : Array
        },
        isCart : {
            type : Boolean,
            default : false
        },
        btnTitle : {
            type : String,
            default : '구매하기'
        }
    },
    mounted(){
        this.product = this.productInfo;
        this.selectedItem = this.items;
        this.initSelectedOption();
        this.getOptionList(this.productInfo.goodsno, 'F');
        this.getAddItemsList();
    },
};