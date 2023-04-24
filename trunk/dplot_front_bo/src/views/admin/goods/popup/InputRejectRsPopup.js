export default {
    name: 'InputRejectRsPopup',
    props : {
        modalComp: Object,
        params: Object,
        callbackFn: Function
    },
    data() {
        return {
            contents: '반려합니다.'   // 반려사유
        }
    },
    methods : {
        // 반려사유 전달
        sendRejectRs: function() {
            if (this.$util.isNull(this.contents.trim())) {
                alert("반려사유를 입력해주세요.");
                return;
            }
            if( typeof(this.callbackFn) == 'function') {
                this.callbackFn({data: this.contents});
            }
            this.$modal.hide('commonModal');
        }
    }
}