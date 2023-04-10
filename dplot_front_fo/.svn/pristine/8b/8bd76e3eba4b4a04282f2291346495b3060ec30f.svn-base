<template>
  <div class="">
    <div>
      <ul class="dp-sort__ul type02 scroll list-style-none no-scrollbar">
        <li v-for="(list, index) in termList" :key="index">
          <label :for="'term' + list.idx" class="dp-sort__label">
            <input
              type="radio"
              :id="'term' + list.idx"
              name="terms"
              v-model="checked"
              :value="list.termstype"
              :ref="'term' + list.idx"
              hidden
            />
            <span class="dp-sort__badge" @click="handleTerms(list.content)">{{
              list.termstypename
            }}</span>
          </label>
        </li>
      </ul>
      <div
        class="container"
        @click="openSaleListModal"
        v-html="termsData"
      ></div>
    </div>
    <component
      :key="skey"
      :is="modalInfo.comp"
      :param="modalInfo.param"
      :fnConfirm="modalInfo.fnConfirm"
      :fnCancel="modalInfo.fnCancel"
    />
  </div>
</template>

<script>
import SaleList from "@views.common/components/ui/modal/SaleList.vue";
export default {
  props: {
    params: {
      type: Object,
    },
  },
  data() {
    return {
      termsData: "이용약관 데이터",
      termList: [],
      checked: "",
      skey: 0,
      modalInfo: {
        comp: null,
        param: null,
        fnConfirm: () => {},
        fnCancel: () => {},
      },
    };
  },
  methods: {
    handleTerms(content) {
      this.termsData = this.$util.toEscape(content);
    },
    fetchList() {
      this.termList = this.params.termList;
      this.checked = this.params.termstype;
      this.termList.forEach((item) => {
        if (item.termstype == this.checked) {
          this.handleTerms(item.content);
          return;
        }
      });
    },
    /**************************************
     * 판매업체 목록 조회
     *************************************/
    openSaleListModal(e) {
      if (this.$util.isNull(e) || e == 'undefined') {
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
    this.fetchList();
  },
  watch: {
    params() {
      this.fetchList();
    },
    checked(val) {
      //this.fetchList();
    },
  },
};
</script>
