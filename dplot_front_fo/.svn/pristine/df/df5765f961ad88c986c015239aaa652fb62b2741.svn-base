<template>
  <b-modal
    id="snsShareModal"
    modal-class="dp-modal page-layer sns-share"
    scrollable
    centered
    :hide-footer="true"
  >
    <!-- Page Modal Header -->
    <template #modal-header="{ cancel }">
      <h5 class="modal-title">공유하기</h5>
      <i class="modal-close" @click="cancel()">
        <img
          src="@assets.mobile/img/icon/icon-close-black-20px.svg"
          alt="닫기"
        />
      </i>
    </template>

    <!-- Page Modal Body -->
    <div class="page-layer__body">
      <sns-contents :param="param"/>
    </div>
  </b-modal>
</template>

<script>
export default {
  props: {
    param: Object,
  },
  data() {
    return {};
  },
  methods: {},
  mounted() {
  },
};
</script>

<style>
</style>