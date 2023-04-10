<template>
    
    <!-- D포인트 혜택 안내 (비로그인 시) Modal -->
    <b-modal
    id="gradePointModal"
    modal-class="dp-modal pop-over point-modal"
    hide-footer
    centered
    >
    <template #modal-header="{ cancel }">
        <h5 class="modal-title">등급별 DADA 적립금</h5>
        <i class="modal-close" @click="cancel()">
        <img
            src="@assets.mobile/img/icon/icon-close-black-20px.svg"
            alt="닫기"
        />
        </i>
    </template>

    <div>
        <table class="dp-table dp-mb-20">
        <thead>
            <tr class="text-center">
            <th>등급</th>
            <th>적립률</th>
            </tr>
        </thead>
        <tbody class="text-center">
            <tr v-for="(reserve, index) in param" :key="index">
            <td>{{reserve.memlvtypename}}</td>
            <td><span class="point-text">{{reserve.amt}}%</span></td>
            </tr>
        </tbody>
        </table>
    </div>
    <ul class="ul-dot">
        <li>일부 제휴카드/임직원 카드로 결제 시 0.5% 적립</li>
        <li>복합 결제 진행 시 D포인트 적립액 상이 가능</li>
    </ul>
    </b-modal>
    <!-- // D포인트 혜택 안내 (비로그인 시) Modal -->
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