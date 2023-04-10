<template>
  <b-modal
    :id="id"
    :modal-class="{'dp-modal page-layer pop-over terms-modal terms-modal-pc': $store.state.platform == 'MAC001', 
                    'dp-modal page-layer terms-modal': $store.state.platform != 'MAC001'}"
    scrollable
    :hide-footer="true"
  >
    <!-- Page Modal Header -->
    <template #modal-header="{ cancel }">
      <h5 class="modal-title">이용약관</h5>
      <i class="modal-close" @click="cancel()">
        <img
          src="@assets.mobile/img/icon/icon-close-black-20px.svg"
          alt="닫기"
        />
      </i>
    </template>

    <!-- Page Modal Body -->
    <div class="page-layer__body">
      <terms-contents :params="{termList:termList,termstype:param.termstype}"/>
    </div>
  </b-modal>
</template>

<script>
export default {
  props: {
    id: { type: String },
    param: { type: Object }
  },
  data() {
    return {
      termList: [],
      termsData:""
    };
  },
  mounted() {
    this.$util.debug("이용약관 모달 호출");
    this.$util.debug("param::" + JSON.stringify(this.param));
    this.apiTerm();
  },
  methods: {
    /*********************
     * 이용약관 목록 조회
     *********************/
    apiTerm() {
      let params = ["TRT001", "TRT002", "TRT003", "TRT004"];
      if (this.$util.isNull(this.param)) {
        params = ["TRT001", "TRT002", "TRT003", "TRT004"]
      }else {
        params = this.param.list;
      }
      this.$http.post("/member/signup/term", { termslist: params, isloading: false }).then((result) => {
          if (result.statusCode == 200) {
            this.termList = result.data.list;
            this.termList.forEach(item => {
              if (item.termstype === this.param.termstype) {
                item.checked = true;
              }else {
                item.checked = false;
              }
            });
          }
        });
    },
  },
};
</script>

<style>
</style>