<template>
    <!-- 배송지 목록 모달 -->
    <b-modal
    id="postModal"
    :modal-class="{'dp-modal pop-over yes-shipping-list-modal': $store.state.platform == 'MAC001', 
                    'dp-modal page-layer yes-shipping-list-modal': $store.state.platform != 'MAC001'}"
    hide-footer
    scrollable
    centered
    >
    <!-- Popover Modal Header -->
    <template #modal-header="{ cancel }">
        <h5 class="modal-title">주소검색</h5>
        <i class="modal-close" @click="cancel()">
        <img
            src="@assets.mobile/img/icon/icon-close-black-20px.svg"
            alt="닫기"
        />
        </i>
    </template>

    <!-- PopoverModal Body -->
    <div class="page-layer__body">
        <vue-daum-postcode @complete="onComplete" />
    </div>
    </b-modal>
</template>

<script>
export default {
    props:{
        fnConfirm : Function
    },
    methods: {
        onComplete(result) {
            if( typeof(this.fnConfirm) == 'function') { 
                this.fnConfirm(result);
                this.$bvModal.hide('postModal');
            }
        }
    }
}
</script>

<style>
.vue-daum-postcode-container {
    height : 500px !important
}
</style>