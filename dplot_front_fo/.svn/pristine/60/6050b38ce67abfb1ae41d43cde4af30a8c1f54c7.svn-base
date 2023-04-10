<template>
  <b-modal
    id="brandPlusModal"
    modal-class="dp-modal pop-over none-title brand-plus-modal"
    hide-footer
    centered
  >
    <template #modal-header="{ cancel }">
      <i class="modal-close" @click="cancel()">
        <img
          src="@/assets/common/icon/icon-close-modal-30px.svg"
          alt="닫기"
        />
      </i>
    </template>

    <div>
      <brand-filter :param="param" :fnConfirm="fnConfirm"/>
    </div>
  </b-modal>
</template>

<script>
export default {
  props: {
    param: { type: Object },
    fnConfirm: { type: Function },
    fnCancel: { type: Function },
  }
};
</script>

<style>
</style>