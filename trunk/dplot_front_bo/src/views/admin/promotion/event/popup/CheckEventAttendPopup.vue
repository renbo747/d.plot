<template>
  <!-- 출석 및 혜택지급 기록 팝업 -->
  <div id="modal-wrap" class="modal" style="display: block;">
    <div class="modal-content" style="width: 600px;">
      <div class="pop-header">
        <h2>출석 및 혜택지급 기록</h2>
        <button type="button" class="pop-close" @click="$emit('close')"></button>
      </div>
      <div class="pop-body">
        <div class="scroll-y" style="max-height: 500px;">
          <table cellpadding="0" cellspacing="0" class="data-tb align-c" style="margin-left: 0;">
            <colgroup>
              <col width="25%"/><!-- 출석인정대상일 -->
              <col width="20%"/><!-- 출석체크 -->
              <col width="15%"/><!-- 출석인정 -->
              <col width="25%"/><!-- 인정여부 -->
              <col width="15%"/><!-- 혜택지급 -->
            </colgroup>
            <thead>
            <tr>
              <th>출석인정대상일</th>
              <th>출석체크</th>
              <th>출석인정</th>
              <th>인정여부</th>
              <th>혜택지급</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="(row, i) in attenList" :key="i">
              <td>{{row.attenddateyyyymmdd}}</td>
              <td>{{row.attenddatehhii}}</td>
              <td>{{row.isattend}}</td>
              <td>{{row.notatreason}}</td>
              <td>{{row.benefitpaycount}}</td>
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
  <!-- /출석 및 혜택지급 기록 팝업 -->
</template>

<script>
export default {
  name: "CheckEventAttendPopup",
  props: {
    attendPopupObj: Object
  },
  data() {
    return {
      attenList: [],
    }
  },
  mounted() {
    this.$http.post("/admin/promotion/event/popup/atten", this.attendPopupObj)
        .then(result => {
          if (result.statusCode === 200) {
            let data = result.data;
            this.attenList = data.list;
          }
        })
        .catch(error => {
          this.$util.debug(error);
        });
  },
}
</script>

<style scoped>

</style>
