<template>
  <b-modal
    id="epointModal"
    :modal-class="{'dp-modal page-layer coupon-use-modal-pc':$store.state.platform == 'MAC001',
                  'dp-modal page-layer coupon-use-modal':$store.state.platform != 'MAC001'}"
    scrollable
    :hide-footer="true"
    :centered = "$store.state.platform == 'MAC001'"
  >
    <!-- Page Modal Header -->
    <template #modal-header="{ cancel }">
      <h5 class="modal-title">D포인트 사용안내</h5>
      <i class="modal-close" @click="cancel()">
        <img src="@/assets/common/icon/icon-close-modal-30px.svg" alt="닫기" />
      </i>
    </template>

    <!-- Page Modal Body -->
    <div class="page-layer__body">
      <section>
        <div>
          <div class="coupon-use__guide__header">
            <p class="mb-0">D포인트 사용</p>
          </div>
          <div class="coupon-use__guide__body">
            <ul class="list-style-none ul-dot">
              <li>D포인트는 상품 구매 시 현금처럼 사용이 가능합니다.</li>
              <li>
                주문 취소 시, 사용한 D포인트는 자동 반환되며 유효기간 경과된
                D포인트는 소멸 됩니다.
              </li>
            </ul>
          </div>
        </div>
      </section>
      <section>
        <div>
          <div class="coupon-use__guide__header">
            <p class="mb-0">D포인트 유효기간</p>
          </div>
          <div class="coupon-use__guide__body">
            <ul class="list-style-none ul-dot">
              <li>
                소멸된 D포인트는 복구 불가하며 D포인트 내역에서 확인 가능합니다.
              </li>
            </ul>
          </div>
        </div>
      </section>
      <section>
        <div>
          <div class="coupon-use__guide__header">
            <p class="mb-0">D포인트 적립</p>
          </div>
          <div class="coupon-use__guide__body">
            <ul class="list-style-none ul-dot">
              <li>
                특정 이벤트 혹은 프로모션에 의해 적립되는 D포인트는 지급과 사용에
                있어 별도의 기준(지급, 유효기간, 사용제한 등)들이 적용 될 수
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