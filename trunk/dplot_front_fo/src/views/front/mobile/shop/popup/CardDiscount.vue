<template>
    <!-- 카드사 혜택 정보 Modal -->
      <b-modal
        id="cardBenefitModal"
        modal-class="dp-modal page-layer card-benefit-modal"
        :centered="iscenterd"
        scrollable
        :hide-footer="true"
      >
        <!-- Card benefit Modal Header -->
        <template #modal-header="{ cancel }">
          <h5 class="modal-title">카드사 혜택 안내</h5>
          <i class="modal-close" @click="cancel()">
            <img
              src="@assets.mobile/img/icon/icon-close-black-20px.svg"
              alt="닫기"
            />
          </i>
        </template>

        <!-- Card benefit Modal Body -->
        <template #default="{ hide }">
          <div class="page-layer__body">
            <div v-if="param.disCards1.length > 0">
              <p class="dp-mb-10 dp-p font-weight-500">즉시할인</p>
              <ul class="ul-dot dp-mb-20">
                <template v-for="(card,index) in param.disCards1">
                  <li :key="index">
                    {{card.content}}
                  </li>
                </template>
              </ul>
            </div>
            <div v-if="param.disCards2.length > 0">
              <p class="dp-mb-10 dp-p font-weight-500">청구할인</p>
              <ul class="ul-dot dp-mb-20">
                <li v-for="(card,index) in param.disCards2" :key="index">
                  {{card.cardname}} / {{card.flatrate > 0 ? $util.maskComma(card.flatrate)+'원 할인' : ''}}{{card.fixedrate > 0 ? card.fixedrate+'%' : ''}}  / {{$util.maskComma(card.amount)}}원 이상 구매 시
                </li>
              </ul>
            </div>
            <div class="card-benefit__box dp-mb-30"  v-if="param.disCards2.length > 0">
              <p class="dp-mb-10 dp-p-sm">유의사항</p>
              <ul class="ul-dot">
                <li>
                  <p class="text-gray-700 mb-0" v-html="param.note">
                  </p>
                </li>
              </ul>
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
      <!-- // 카드사 혜택 정보 Modal -->
</template>

<script>
export default {
    data() {
        return {
            iscenterd : false,
        }
    },
    props : {
        param : Object
    },
    mounted() {
        if(window.sessionStorage.getItem('platform') == 'MAC001') {
            this.iscenterd = true;
        }
    }
}
</script>

<style>

</style>