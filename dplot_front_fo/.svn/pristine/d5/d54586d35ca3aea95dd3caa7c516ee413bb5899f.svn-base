<template>
  <!-- 무이자 할부 안내 Modal -->
  <b-modal
    id="noInterestCardModal"
    modal-class="dp-modal page-layer no-interest-modal"
    :centered="iscenterd"
    scrollable
    :hide-footer="true"
  >
    <!-- No interest Card Modal Header -->
    <template #modal-header="{ cancel }">
      <h5 class="modal-title">무이자 할부 안내</h5>
      <i class="modal-close" @click="cancel()">
        <img
          src="@assets.mobile/img/icon/icon-close-black-20px.svg"
          alt="닫기"
        />
      </i>
    </template>

    <!-- No interest Card Modal Body -->
    <template #default="{ hide }">
      <div class="page-layer__body">
        <div class="dp-mb-30" v-if="param.disCards1.length > 0">
          <p class="dp-mb-10 dp-p font-weight-500">무이자 할부</p>
          <ul class="ul-dot dp-mb-20">
            <template v-for="(card, index) in param.disCards1">
              <li :key="index">
                {{ card.content }}
              </li>
            </template>
          </ul>
        </div>
        <div class="dp-mb-30" v-if="param.disCards2.length > 0">
          <div>
            <p class="dp-mb-10 dp-p font-weight-500">부분 무이자 할부</p>
            <template v-for="(card, index) in param.disCards2">
              <ul class="ul-dot dp-mb-20" :key="'contents' + index">
                <li v-for="(item, itemidx) in card.list" :key="itemidx">
                  <div>
                    {{ card.cardname }} /  {{ $util.maskComma(item.amount) + "원 이상" }} / {{item.month + "개월"}} / {{ item.discnote ? item.discnote : '' }}
                  </div>
                </li>
              </ul>
            </template>
          </div>
          <div class="no-interest__box">
            <p class="dp-p-sm dp-mb-10">유의사항</p>
            <ul class="ul-dot">
              <li>
                <p class="text-gray-700 mb-0" v-html="param.note">
                  </p>
              </li>
            </ul>
          </div>
        </div>
        <div class="btn-group">
          <b-button
            class="dp-btn text-white"
            variant="gray-800"
            @click="hide()"
            squared
            >확인
          </b-button>
        </div>
      </div>
    </template>
  </b-modal>
  <!-- // 무이자 할부 안내 Modal -->
</template>

<script>
export default {
  data() {
    return {
      iscenterd: false,
    };
  },
  props: {
    param: Object,
  },
  mounted() {
    if (window.sessionStorage.getItem("platform") == "MAC001") {
      this.iscenterd = true;
    }
  },
};
</script>

<style>
</style>