import store from "@js/store.js";
import DelivTempDetailPopup from "@views.admin/goods/popup/DelivTempDetailPopup.vue";

export default {
    name: 'DelivTempListPopup',
    props : {
        modalComp: Object,
        params: Object,
        callbackFn: Function
    },
    mounted() {
        this.dealerno = this.params.dealerno;
        this.searchDelivTempList();
    },
    data :function(){
        return {
            dealerno: null,
            isPartner: this.$util.isAuthorized(store.getters['CONSTANTS'].PARTNER_USER),
            // 모달팝업에서 store 가져오지 못해서 별도 세팅
            constants: store.getters['ADMIN'],
            delivTempList: []
        }
    },
    methods : {
        // 배송템플릿 목록 조회
        searchDelivTempList: function() {
            let params = { dealerno: this.dealerno, isPartner: this.isPartner}; 
            this.$http.post('/admin/goods/regist/delivtemp/list', params)
                .then(result => {
                    this.$util.debug(result);
                    this.delivTempList = result.data.list;
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 모달 팝업 SHOW
        openDelivTempPopup: function(params) {
            this.$modal.show(
                DelivTempDetailPopup, {
                    'params': params,
                    'callbackFn': () => {
                        this.searchDelivTempList();
                    }
                }, {
                    name: "delivTempDetail"
                })
        },
        // 배송템플릿 신규등록 팝업 오픈
        openNewDelivTempPopup: function() {
            let params = {
                type: 'NEW',
                delividx: '',
                dealerno: this.dealerno,
                isPartner: this.isPartner
            }
            this.openDelivTempPopup(params);
        },
        // 배송템플릿 상세 팝업 오픈
        openDelivTempDetailPopup: function(obj) {
            let params = {
                type: 'DETAIL',
                delividx: obj.delividx,
                dealerno: this.dealerno,
                isPartner: this.isPartner
            }
            this.openDelivTempPopup(params);
        },
        // 팝업닫기
        closePopup() {
            if( typeof(this.callbackFn) == 'function') {
                this.callbackFn();
            }
            this.$modal.hide('commonModal');
        }
    }
}