<template>
  <div id="email-personal-info" style="width: 800px; padding: 0 40px; margin: 0 auto; font-family: 'Noto Sans KR', sans-serif; background-color: #ffffff;">
    <p v-html="content"></p>
  </div>
</template>

<script>
import SaleList from "@views.common/components/ui/modal/SaleList.vue";
export default {
  data() {
    return {
      termsList: [],
      selectOptionData: "",
      content: "",
      skey: 0,
      modalInfo: {
        comp: null,
        param: null,
        fnConfirm: () => {},
        fnCancel: () => {},
      },
      sampleSelectOptions: [
        {
          label: "샘플 옵션 01",
          value: "샘플 데이터 01",
        },
        {
          label: "샘플 옵션 02",
          value: "샘플 데이터 02",
        },
      ],
    };
  },
  methods: {
    getTermList() {
      let param = { termstype: "TRT013" };
      this.$http.post("/etc/useTerms", param).then((result) => {
        if (result.statusCode == 200) {
          this.termsList = result.data.termslist;
          this.termsList.forEach((item) => {
            item.value = item.idx;
            item.label =
              "개인정보 처리방침 " +
              item.regdatestr +
              "시행" +
              "(v" +
              item.version +
              ")";
          });
          this.selectOptionData = this.termsList[0].value;
          this.content = this.$util.toEscape(this.termsList[0].content);
        }
      });
    },
    getContent() {
      this.termsList.forEach((item) => {
        if (this.selectOptionData == item.value) {
          this.content = this.$util.toEscape(item.content);
        }
      });
    },
    /**************************************
     * 판매업체 목록 조회
     *************************************/
    openSaleListModal(e) {
      if (this.$util.isNull(e) || e == "undefined") {
        return;
      }
      let clickid = e.target.id;
      if (clickid == "salelist") {
        this.skey = Date.now();
        this.modalInfo.comp = SaleList;
        this.$nextTick(function () {
          this.$bvModal.show("saleListModal");
        });
      }
    },
  },
  mounted() {
    this.getTermList();
  },
};
</script>

<style>
</style>