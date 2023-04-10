<template>
    <!-- D포인트 혜택 안내 (로그인 시) Modal -->
    <b-modal
    id="pointModal"
    modal-class="dp-modal pop-over point-modal"
    hide-footer
    centered
    >
    <template #modal-header="{ cancel }">
        <h5 class="modal-title">홈길동 회원님 예상 D포인트</h5>
        <i class="modal-close" @click="cancel()">
        <img
            src="@assets.mobile/img/icon/icon-close-black-20px.svg"
            alt="닫기"
        />
        </i>
    </template>

    <p class="modal-text02 text-black dp-mb-20">
        현재 등급은 <span class="font-weight-600">등급 1</span> 입니다. <br />
        다음 등급까지 남은 이번달 구매금액은
        <span class="font-weight-600">23,000원!</span>
    </p>
    <div>
        <table class="dp-table dp-mb-20">
        <thead>
            <tr class="text-center">
            <th>등급</th>
            <th>적립률</th>
            </tr>
        </thead>
        <tbody class="text-center">
            <tr>
            <td>등급 2</td>
            <td><span class="point-text">1.5%</span></td>
            </tr>
            <tr class="point-background">
            <td>등급 1</td>
            <td><span class="point-text">1%</span></td>
            </tr>
        </tbody>
        </table>
    </div>
    <ul class="ul-dot">
        <li>일부 제휴카드/임직원 카드로 결제 시 0.5% 적립</li>
        <li>복합 결제 진행 시 D포인트 적립액 상이 가능</li>
    </ul>
    </b-modal>
    <!-- // D포인트 혜택 안내 (로그인 시) Modal -->
</template>

<script>
export default {
    
}
</script>

<style>

</style>