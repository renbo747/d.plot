<template>
  <!-- 응모기록 팝업 -->
  <div id="modal-wrap" class="modal" style="display: block;">
    <div class="modal-content" style="width: 400px;">
      <div class="pop-header">
        <h2>응모기록</h2>
        <button type="button" class="pop-close" @click="$emit('close')"></button>
      </div>
      <div class="pop-body">
        <div class="scroll-y" style="max-height: 500px;">
          <table cellpadding="0" cellspacing="0" class="data-tb align-c" style="margin-left: 0;">
            <thead>
            <tr>
              <th>응모일시</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="(row, i) in listData" :key="i">
              <td>{{row.regdate}}</td>
            </tr>
            </tbody>
          </table>
        </div>
        <div class="btn-group">
          <button type="button" class="btn darkgray" @click="$emit('close')">닫기</button>
        </div>
      </div>
    </div>
  </div>
  <!-- /응모기록 팝업 -->
</template>

<script>
export default {
  name: "EntryListPopup",
  data(){
    return {
      listData: [],
    }
  },
  props: {
    enterObj: Object
  },
  mounted() {
    let params = this.enterObj;

    this.$http.post("/admin/promotion/event/popup/search/enter", params)
        .then(result => {
          if (result.statusCode === 200) {
            this.listData = result.data.list;
          }
        })
        .catch(error => {
          this.$util.debug(error);
        });
  }
}
</script>

<style scoped>

</style>
