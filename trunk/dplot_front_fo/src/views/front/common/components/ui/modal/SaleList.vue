<template>
  <b-modal
    id="saleListModal"
    modal-class="dp-modal pop-over sale-list-modal"
    hide-footer
    centered
  >
    <template #modal-header="{ cancel }">
      <h5 class="modal-title">판매업체 리스트</h5>
      <i class="modal-close" @click="cancel()">
        <img src="@/assets/common/icon/icon-close-modal-30px.svg" alt="닫기" />
      </i>
    </template>

    <ul class="sale-list__ul list-style-none">
      <li v-for="(list, index) in saleList" :key="index">
        {{ list.name }}
      </li>
    </ul>
  </b-modal>
</template>

<script>
export default {
  data() {
    return {
      saleList: [
        "주식회사 이스턴에디션",
        "원더그린 주식회사",
        "(주)루밍",
        "메종 바로바우",
        "(주)스페이스로직",
        "아템포코리아 주식회사",
        "(주)황덕기술단",
        "주식회사 앰비언스",
        "(주)세보코리아",
        "디자인스타트 주식회사",
      ],
    };
  },
  methods: {
    getDealerList() {
      this.$http.post("/etc/dealer", {}).then((result) => {
        if (result.statusCode == 200) {
          this.saleList = result.data.salelist;
        }
      });
    },
  },
  mounted() {
    this.getDealerList();
  },
};
</script>

<style>
</style>