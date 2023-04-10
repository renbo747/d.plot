<template>
  <!-- 신고정보 팝업 -->
  <div id="modal-wrap" class="modal" style="display: block;">
    <div class="modal-content" style="width: 1000px;">
      <div class="pop-header">
        <h2>신고정보</h2>
        <button type="button" class="pop-close" @click="$emit('close')"></button>
      </div>
      <div class="pop-body">
        <div class="scroll-y" style="max-height: 500px;">
          <table cellpadding="0" cellspacing="0" class="data-tb align-c" style="margin-left: 0;">
            <colgroup>
              <col width="4%"/><!-- No -->
              <col width="10%"/><!-- 아이디 -->
              <col width="10%"/><!-- 이름 -->
              <col width="10%"/><!-- 유형 -->
              <col width="10%"/><!-- 등급 -->
              <col width="15%"/><!-- 신고일시 -->
              <col width="20%"/><!-- 사유구분 -->
              <col width=""/><!-- 기타사유 -->
            </colgroup>
            <thead>
            <tr>
              <th>No</th>
              <th>아이디</th>
              <th>이름</th>
              <th>유형
                <button type="button" class="sort down"></button>
              </th>
              <th>등급
                <button type="button" class="sort down"></button>
              </th>
              <th>신고일시
                <button type="button" class="sort down"></button>
              </th>
              <th>사유구분</th>
              <th>기타사유</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="(row, i) in dataList" :key="i">
              <td>{{ row.num }}</td>
              <td>{{ row.userid }}</td>
              <td>{{ row.name }}</td>
              <td>{{ row.dadamembertype }}</td>
              <td>{{ row.mumemlvtype }}</td>
              <td>{{ row.regdate }}</td>
              <td>{{ row.reporttype }}</td>
              <td class="left">{{ row.etcreason }}</td>
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
  <!-- /신고정보 팝업 -->
</template>

<script>
export default {
  name: "ReportInfoPopup",
  props: {
    reportObj: Object
  },
  data() {
    return {
      dataList: []
    }
  },
  mounted() {
    this.$http.post("/admin/operation/shopping/promotion/popup/search/report", this.reportObj)
        .then(result => {
          if (result.statusCode === 200) {
            this.dataList = result.data.list;
          }
        })
        .catch(error => {
          this.$util.debug(error);
        });
  }
}
</script>
