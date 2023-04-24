<template>
  <b-modal
    id="rewardInfoModal"
    :modal-class="{'dp-modal pop-over coupon-use-modal-pc': $store.state.platform == 'MAC001', 
                    'dp-modal page-layer coupon-use-modal': $store.state.platform != 'MAC001'}"
    scrollable
    :hide-footer="true"
    :centered = "$store.state.platform == 'MAC001'"
  >
    <!-- Page Modal Header -->
    <template #modal-header="{ cancel }">
      <h5 class="modal-title">적립금 사용안내</h5>
      <i class="modal-close" @click="cancel()">
        <img src="@/assets/common/icon/icon-close-modal-30px.svg" alt="닫기" />
      </i>
    </template>

    <!-- Page Modal Body -->
    <div class="page-layer__body">
      <section>
        <div>
          <div class="coupon-use__guide__header">
            <p class="mb-0">적립금 적립</p>
          </div>
          <div class="coupon-use__guide__body">
            <ul class="list-style-none ul-dot">
              <li>적립된 금액은 현금으로 환불되지 않습니다.</li>
              <li>구매 적립금은 구매 확정시 적립 됩니다.</li>
              <li>구매 적립금은 배송비를 제외한 결제금액입니다.</li>
            </ul>
          </div>
        </div>
      </section>
      <section>
        <div>
          <div class="coupon-use__guide__header">
            <p class="mb-0">적립금 사용</p>
          </div>
          <div class="coupon-use__guide__body">
            <ul class="list-style-none ul-dot">
              <li>
                구매 후 받으신 적립금은 다른 상품 구매 시 현금처럼 사용이
                가능합니다.
              </li>
              <li>
                적립금의 사용은 고객의 의사와 관계없이 유효기간이 임박한
                적립금이 가장 먼저 사용됩니다.
              </li>
              <li>
                주문 취소 시, 지급되었던 적립금은 자동 반환되며 유효기간이
                만료된 적립금은 소멸됩니다.
              </li>
              <!-- <li>
                배송완료 된 제품의 반품 시, 결제 금액에 따라 지급했던 적립금은
                환불 완료 후 회수됩니다.
              </li> -->
            </ul>
          </div>
        </div>
      </section>
      <section>
        <div>
          <div class="coupon-use__guide__header">
            <p class="mb-0">적립금 유효기간</p>
          </div>
          <div class="coupon-use__guide__body">
            <ul class="list-style-none ul-dot">
              <li>
                지급일로부터 6개월이 지난 적립금은 당월 마지막 날 소멸됩니다.
                (소멸된 적립금은 복구 불가하며, 적립금 내역에서 확인
                가능합니다.)
              </li>
            </ul>
          </div>
        </div>
      </section>
      <section>
        <div>
          <div class="coupon-use__guide__header">
            <p class="mb-0">적립금 사용 시 유의사항</p>
          </div>
          <div class="coupon-use__guide__body">
            <ul class="list-style-none ul-dot">
              <li>
                부정한 방법으로 적립된 적립금은
                사전 안내 없이 차감될 수
                있습니다.
              </li>
            </ul>
          </div>
        </div>
      </section>
    </div>
  </b-modal>
</template>

<script>
export default {};
</script>

<style>
</style>