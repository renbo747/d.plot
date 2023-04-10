<template>
    <!-- 배송 정보 Modal -->
    <b-modal
    id="deliveryInfoModal"
    modal-class="dp-modal pop-over delivery-info-modal"
    hide-footer
    centered
    >
    <template #modal-header="{ cancel }">
        <h5 class="modal-title">배송 정보</h5>
        <i class="modal-close" @click="cancel()">
        <img
            src="@assets.mobile/img/icon/icon-close-black-20px.svg"
            alt="닫기"
        />
        </i>
    </template>

    <div>
        <table class="dp-table dp-mb-20">
        <colgroup>
            <col width="120" />
            <col width="*" />
        </colgroup>
        <tr>
            <th>배송방법</th>
            <td>{{param.delivtypename}}</td>
        </tr>
        <tr>
            <th>배송비</th>
            <td v-if="param.delivfaretype == 'DCT001'">무료</td>
            <td v-if="param.delivfaretype == 'DCT002'">{{$util.maskComma(param.delivfare)}}원</td>
            <td v-if="param.delivfaretype == 'DCT003'">{{$util.maskComma(param.delivfare)}}원 ({{$util.maskComma(param.delivfreefare)}}원 이상 구매시 무료)</td>
            <td v-if="param.delivfaretype == 'DCT004'">착불</td>
            <td v-if="param.delivfaretype == 'DCT005'">구매수량당 {{$util.maskComma(param.delivfare)}}원</td>
        </tr>
        <tr>
            <th>택배사</th>
            <td>{{param.logistypename}}</td>
        </tr>
        <tr>
            <th>배송일정</th>
            <td>{{param.delivschtypename}}</td>
        </tr>
        <tr>
            <th>배송안내</th>
            <td>{{param.delivschtypedetail}}</td>
        </tr>
        </table>
    </div>
    <ul class="ul-dot">
        <li>
        산간벽지나 도서지방은 별도의 추가금액을 지불하셔야 하는 경우가
        있습니다.
        </li>
        <li>
        고객님께서 주문하신 상품은 입금 확인후 배송해 드립니다. 다만,
        상품종류에 따라서 상품의 배송이 다소 지연될 수 있습니다.
        </li>
        <li>
        택배사정에 따라 예측치와 실제 배송완료일이 다를 수 있습니다
        </li>
    </ul>
    </b-modal>
    <!-- // 배송 정보 Modal -->
</template>

<script>
export default {
    props :{
        param : Object
    }
}
</script>

<style>

</style>