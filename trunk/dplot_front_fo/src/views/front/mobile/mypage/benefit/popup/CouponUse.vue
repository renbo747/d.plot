<template>
    <b-modal
      id="couponUseModal"
      :modal-class="{'dp-modal pop-over coupon-use-modal-pc': $store.state.platform == 'MAC001', 
                    'dp-modal page-layer coupon-use-modal': $store.state.platform != 'MAC001'}"
      scrollable
      :hide-footer="true"
      :centered = "$store.state.platform == 'MAC001'"
    >
      <!-- Page Modal Header -->
      <template #modal-header="{ cancel }">
        <h5 class="modal-title">쿠폰 사용안내</h5>
        <i class="modal-close" @click="cancel()">
          <img
            src="@/assets/common/icon/icon-close-modal-30px.svg"
            alt="닫기"
          />
        </i>
      </template>

      <!-- Page Modal Body -->
      <div class="page-layer__body">
        <section>
          <div>
            <div class="coupon-use__guide__header">
              <p class="mb-0">프로모션 코드 등록</p>
            </div>
            <div class="coupon-use__guide__body">
              <ul class="list-style-none ul-dot">
                <li :class="{'guide__text':$store.state.platform != 'MAC001'}">
                  쇼핑몰에서 발행한 종이쿠폰/시리얼쿠폰/모바일쿠폰 등의
                  인증번호를 등록하시면 온라인쿠폰으로 발급되어 사용이
                  가능합니다.
                </li>
                <li :class="{'guide__text':$store.state.platform != 'MAC001'}">
                  쿠폰은 주문 시 1회에 한해 적용되며, 1회 사용 시 재 사용이
                  불가능합니다.
                </li>
              </ul>
            </div>
          </div>
        </section>
        <section>
          <div>
            <div class="coupon-use__guide__header">
              <p class="mb-0">쿠폰 사용</p>
            </div>
            <div class="coupon-use__guide__body">
              <ul class="list-style-none ul-dot">
                <li :class="{'guide__text':$store.state.platform != 'MAC001'}">
                  주문 취소 시 유효기간이 남아있는 쿠폰인 경우 재발급 됩니다.
                </li>
                <li :class="{'guide__text':$store.state.platform != 'MAC001'}">
                  쿠폰은 적용 가능한 상품이 따로 적용되어 있는 경우 해당 상품
                  구매 시에만 사용이 가능합니다.
                </li>
                <li :class="{'guide__text':$store.state.platform != 'MAC001'}">
                  쿠폰은 주문/결제 단계에서 사용조건 충족 시 적용 또는 적용
                  안함으로 선택 할 수 있으며, 미 충족 시에서 노출되지 않습니다.
                </li>
                <li :class="{'guide__text':$store.state.platform != 'MAC001'}">
                   쿠폰은 유효기간이 정해져 있으며, 유효기간 만료 시에는 사용이
                  불가합니다.
                </li>
                <li :class="{'guide__text':$store.state.platform != 'MAC001'}">
                  유효기간이 지난 쿠폰은 복원이 불가합니다.
                </li>
              </ul>
            </div>
          </div>
        </section>
        <section>
          <div>
            <div class="coupon-use__guide__header">
              <p class="mb-0">쿠폰 유효기간</p>
            </div>
            <div class="coupon-use__guide__body">
              <ul class="list-style-none ul-dot">
                <li class="guide__text">
                  유효기간이 만료된 쿠폰은 보유 목록에서 자동으로 삭제 됩니다.
                </li>
                <li class="guide__text">
                  쿠폰은 반드시 기간내 사용하여야 합니다. 유효기간이 지난
                  이후에는 쿠폰 유효기간 만료로 사용 불가합니다.
                </li>
              </ul>
            </div>
          </div>
        </section>
      </div>
    </b-modal>
</template>
<script>
export default ({
    data() {
        return {
        }
    },
})
</script>
