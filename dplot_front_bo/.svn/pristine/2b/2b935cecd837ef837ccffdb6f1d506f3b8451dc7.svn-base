export default {
    name: 'GoodsChangeConfirmPopup',
    props : {
        modalComp: Object,
        params: Object,
        callbackFn: Function
    },
    mounted() {
        this.checkedItemList = this.params.checkItemList
    },
    data() {
        return {
            checkedItemList: [] // 일괄변경 체크 항목
        }
    },
    methods : {
        confirm: function() {
            if( typeof(this.callbackFn) == 'function') {
                this.callbackFn({isconfirm: 'T'});
            }
            this.$modal.hide('commonModal');
        }
    }
}