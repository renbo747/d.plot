<template>
  <div id="modal-wrap" class="modal" style="display: block;">
    <div class="modal-content" style="width: 600px;">
      <div class="pop-header">
        <h2>{{ headerName }}</h2>
        <button type="button" class="pop-close" @click="$modal.hide('commonModal');"></button>
      </div>
      <div class="pop-body">
        <div v-if="popupType === 'TYPE'" class="scroll-y" style="max-height: 500px;">
          <table cellpadding="0" cellspacing="0" class="data-tb align-c">
            <colgroup>
              <col width="35%" /><!-- 변경일시 -->
              <col width="15%" /><!-- 변경 전 -->
              <col width="15%" /><!-- 변경 후 -->
              <col width="35%" /><!-- 처리자 -->
            </colgroup>
            <thead>
            <tr>
              <th>변경일시</th>
              <th>변경 전</th>
              <th>변경 후</th>
              <th>처리자</th>
            </tr>
            </thead>
            <tbody v-if="listData.length > 0">
            <tr v-for="row in listData" v-bind:key="row.mhisidx">
              <td>{{ row.regdatestr }}</td>
              <td>{{ row.preval }}</td>
              <td>{{ row.aftval }}</td>
              <td>{{ row.regtypename }}({{ row.reguserid }})</td>
            </tr>
            </tbody>
            <tbody v-else>
            <tr>
              <td colspan="4">변경 이력이 없습니다.</td>
            </tr>
            </tbody>
          </table>
        </div>
        <div v-if="popupType === 'LV'" class="scroll-y" style="max-height: 500px;">
          <table cellpadding="0" cellspacing="0" class="data-tb align-c">
            <colgroup>
              <col width="30%" /><!-- 변경일시 -->
              <col width="15%" /><!-- 회원유형 -->
              <col width="15%" /><!-- 변경 전 -->
              <col width="15%" /><!-- 변경 후 -->
              <col width="25%" /><!-- 처리자 -->
            </colgroup>
            <thead>
            <tr>
              <th>변경일시</th>
              <th>회원유형</th>
              <th>변경 전</th>
              <th>변경 후</th>
              <th>처리자</th>
            </tr>
            </thead>
            <tbody v-if="listData.length > 0">
              <tr v-for="row in listData" v-bind:key="row.mhisidx">
                <td>{{ row.regdatestr }}</td>
                <td>{{ row.memlvtypename }}</td>
                <td>{{ row.preval }}</td>
                <td>{{ row.aftval }}</td>
                <td>{{ row.regtypename }}({{ row.reguserid }})</td>
              </tr>
            </tbody>
            <tbody v-else>
            <tr>
              <td colspan="5">변경 이력이 없습니다.</td>
            </tr>
            </tbody>
          </table>
        </div>

        <div v-if="popupType === 'ADEMAIL' || popupType === 'ADSMS' || popupType === 'ADPUSH' " class="scroll-y" style="max-height: 500px;">
          <table cellpadding="0" cellspacing="0" class="data-tb align-c">
            <colgroup>
              <col width="40%" /><!-- 변경일시 -->
              <col width="20%" /><!-- 동의여부 -->
              <col width="40%" /><!-- 처리자 -->
            </colgroup>
            <thead>
            <tr>
              <th>변경일시</th>
              <th>동의여부</th>
              <th>처리자</th>
            </tr>
            </thead>
            <tbody v-if="listData.length > 0">
              <tr v-for="row in listData" v-bind:key="row.mhisidx">
                <td>{{ row.regdatestr }}</td>
                <td>{{ row.aftval === 'T' ? '동의' : '거부' }}</td>
                <td>{{ row.regtypename }}({{ row.reguserid }})</td>
              </tr>
            </tbody>
            <tbody v-else>
              <tr>
                <td colspan="3">변경 이력이 없습니다.</td>
              </tr>
            </tbody>
          </table>
        </div>

        <div class="btn-group">
          <button type="button" class="btn darkgray" @click="$modal.hide('commonModal');">닫기</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "CommonMemberInfoHistory",
  props: {
    modalComp: Object,
    params: Object,
    callbackFn: Function
  },
  data() {
    return {
      popupType : '',
      headerName : '',
      listData : []
    }
  },
  mounted() {
    let types = {
        TYPE : { header : '회원유형 변경이력', MHISTYPE : 'MHT001'}
      , LV : { header : '회원등급 변경이력', MHISTYPE : 'MHT002'}
      , ADEMAIL : { header : '이메일동의 변경이력', MHISTYPE : 'MHT003'}
      , ADSMS : { header : 'SMS동의 변경이력', MHISTYPE : 'MHT004'}
      , ADPUSH : { header : 'PUSH동의 변경이력', MHISTYPE : 'MHT005'}
    };

    this.popupType = this.params.type;
    this.headerName = types[this.params.type].header;

    let param = { userno : this.params.userno, type: types[this.params.type].MHISTYPE, isloading: false};
    this.$http.post('/admin/common/member/history', param).then(result =>{
      this.$util.debug(result);
      this.listData = result.data.list;
    }).catch(error => {
      this.$util.debug(error);
    })
  }
}
</script>

<style scoped/>
