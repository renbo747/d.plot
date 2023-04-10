<template>
  <div id="modal-wrap" class="modal" style="display: block;">
    <div class="modal-content" style="width: 600px;">
      <div class="pop-header">
        <h2>조정차감목록</h2>
        <button type="button" class="pop-close" @click="onClose"></button>
      </div>
      <div class="pop-body">
        <div class="scroll-y" style="max-height: 500px;">
          <table cellpadding="0" cellspacing="0" class="data-tb align-c">
            <colgroup>
              <col width="6%" /><!-- no -->
              <col width="23%" /><!-- 아이디 -->
              <col width="23%" /><!-- 이름 -->
              <col width="24%" /><!-- 차감포인트 -->
              <col width="24%" /><!-- 실제차감포인트 -->
            </colgroup>
            <thead>
              <tr>
                <th>No</th>
                <th>아이디</th>
                <th>이름</th>
                <th>차감포인트</th>
                <th>실제차감포인트</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(item, index) in list" :key="index">
                <td>{{ index + 1 }}</td>
                <td>{{ item.userid }}</td>
                <td>{{ item.username }}</td>
                <td>{{ $util.maskComma(item.usepoint) }}</td>
                <td>{{ $util.maskComma(item.adjustpoint) }}</td>
              </tr>
            </tbody>
          </table>
        </div>
        <div class="btn-group">
          <button type="button" class="btn darkgray" @click="onClose">닫기</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "AdjustPointListPopup",
  props: {
    modalComp: Object,
    params: Object,
    callbackFn: Function
  },
  data() {
    return {
      list : []
    }
  },
  mounted() {
    this.list = this.params.adjustList;
  },
  methods: {
    onClose: function() {
      if(typeof(this.callbackFn) == 'function') {
          this.callbackFn();
      }
      this.$modal.hide('commonModal');
    }
  }
}
</script>

<style scoped/>
