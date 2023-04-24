<template>
    <!-- 교환 및 반품 Modal -->
    <b-modal
        id="exchangeModal"
        modal-class="dp-modal page-layer exchange-modal"
        scrollable
        :hide-footer="true"
    >
        <!-- Exchange Info Modal Header -->
        <template #modal-header="{ cancel }">
        <h5 class="modal-title">교환 및 반품</h5>
        <i class="modal-close" @click="cancel()">
            <img
            src="@assets.mobile/img/icon/icon-close-black-20px.svg"
            alt="닫기"
            />
        </i>
        </template>

        <!-- Exchange Info Modal Body -->
        <template #default="{ hide }">
        <div class="page-layer__body">
            <div class="dp-mb-20">
            <p class="dp-p font-weight-500 dp-mb-10">
                교환 및 반품 주소
            </p>
            <ul class="ul-dot dp-mb-20">
                <li>
                반품 회수지가 브랜드마다 상이하므로 반품/환불 시
                고객센터로 먼저 연락 부탁드립니다.
                </li>
            </ul>
            </div>
            <div class="dp-mb-20">
            <p class="dp-p font-weight-500 dp-mb-10">
                교환 및 반품 가능기간
            </p>
            <ul class="ul-dot dp-mb-20">
                <li>
                상품 청약철회 가능기간은 상품 수령일로부터 7일
                이내입니다.
                </li>
            </ul>
            </div>
            <div class="dp-mb-20">
            <p class="dp-p font-weight-500 dp-mb-10">교환/반품 안내</p>
            <ul class="ul-dot dp-mb-20">
                <li>
                반품비 여부는 상품 상세정보를 참조하시기
                바랍니다.(설치상품은 크기, 상황, 지역 등에 따라
                교환/반품비가 추가로 부과될 수 있으므로 확인 필수)
                </li>
                <li>
                고객 변심에 의한 반품일 경우, 주문에 포함된 배송비는
                차감 후 결제됩니다. 주문 제작 의류의 경우, 주문 당일에만
                취소가 가능하며 제작이 시작된 이후(주문익일)부터는
                교환/반품 기간내라도 취소, 교환 및 반품이 불가능합니다.
                D.PLOT 에서 구입하신 상품에 대한 품질 보증 및
                피해보상에 관한 사항은 소비자분쟁
                해결기준(공정거래위원회고시 제2018-2호) 에 의거하며,
                문의사항은 D.PLOT 고객센터 (1666-3642)로 문의해주시기
                바랍니다.
                </li>
            </ul>
            </div>
            <div class="dp-mb-30">
            <p class="dp-p font-weight-500 dp-mb-10">
                교환/반품 불가 안내
            </p>
            <ul class="ul-dot dp-mb-20">
                <li>
                가전제품의 경우 포장을 개봉하였거나 포장이 훼손되어
                상품가치가 상실된 경우
                </li>
                <li>
                포장을 개봉하였거나 포장이 훼손되어 상품 가치가 상실된
                경우
                </li>
                <li>
                시간의 경과에 의하여 재판매가 곤란할 정도로 상품 등의
                가치가 현저히 감소한 경우
                </li>
                <li>복제가 가능한 상품 등의 포장을 훼손한 경우</li>
                <li>
                사용 (착용) / 설치 (조립) 후에는 제품 불량 시를 제외하고
                교환/반품 기간 내라도 교환 및 반품이 불가합니다.
                </li>
                <li>
                고객님의 사용 또는 일부 소비에 의하여 상품의 가치가
                현저히 감소한 경우. 단, 화장품 등의 경우 시용제품을
                제공한 경우에 한 합니다.
                </li>
            </ul>
            <div class="exchange-info__box">
                <p class="dp-p-sm text-gray-700 mb-0">
                ※ 고객님의 단순 변심으로 인한 교환, 반품을 하실 경우
                상품 반송 비용은 고객님께서 부담하셔야 합니다. (색상
                교환, 사이즈 교환 등 포함. 단, 제품의 하자, 배송오류,
                무료반품 이벤트 진행 시에는 예외 될 수 있음)
                </p>
                <p class="dp-p-sm text-gray-700 mb-0">
                ※ 자세한 내용은 1:1 문의 및 고객센터를 통해 확인해주시기
                바랍니다.
                </p>
            </div>
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
    <!-- // 교환 및 반품 Modal -->
</template>

<script>
export default {

}
</script>

<style>

</style>