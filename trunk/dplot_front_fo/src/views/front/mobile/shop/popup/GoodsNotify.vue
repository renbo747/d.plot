<template>
    <!-- 상품정보제공고시 Modal -->
    <b-modal
        id="productInfoModal"
        modal-class="dp-modal page-layer product-info-modal"
        scrollable
        :hide-footer="true"
        centered
    >
        <!-- Product Info Modal Header -->
        <template #modal-header="{ cancel }">
        <h5 class="modal-title">상품정보제공고시</h5>
        <i class="modal-close" @click="cancel()">
            <img
            src="@assets.mobile/img/icon/icon-close-black-20px.svg"
            alt="닫기"
            />
        </i>
        </template>

        <!-- Product Info Modal Body -->
        <template #default="{ hide }">
        <div class="page-layer__body">
            <div>
            <table class="dp-table dp-mb-30">
                <colgroup>
                <col width="137" />
                <col width="*" />
                </colgroup>
                <tbody>
                <tr v-for="(notify, index) in param" :key="index">
                    <th>{{notify.notifyname}}</th>
                    <td>{{notify.notifydata}}</td>
                </tr>
                <tr v-if="!$util.isNull(param[0].kccertno)">
                    <th>KC 인증 정보</th>
                    <td>{{param[0].kccertno}}</td>
                </tr>
                </tbody>
            </table>
            </div>

            <div class="btn-group">
            <b-button
                class="dp-btn text-white"
                variant="gray-800"
                @click="hide()"
                squared
                >확인</b-button
            >
            </div>
        </div>
        </template>
    </b-modal>
    <!-- // 상품정보제공고시 Modal -->
</template>

<script>
export default {
    props :{
        param : Array
    }
}
</script>

<style>

</style>