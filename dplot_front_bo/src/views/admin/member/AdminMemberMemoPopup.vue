<template>
  <div id="modal-wrap" class="modal" style="display: block;">
    <div class="modal-content" style="width: 400px;">
      <div class="pop-header">
        <h2 v-if="memoType === 'BLACK'">메모</h2>
        <h2 v-if="memoType === 'WITHDRAW'">탈퇴사유</h2>
        <button type="button" class="pop-close" @click="onClose"></button>
      </div>
      <div class="pop-body">
        <textarea placeholder="내용을 입력하세요." v-model="memo"></textarea>
        <div class="btn-group mt10">
          <button type="button" class="btn blue" @click="goInsert">저장</button>
          <button type="button" class="btn darkgray" @click="onClose">취소</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "AdminMemberMemoPopup",
  props: ['userNoArr', 'memoType'],
  data() {
    return {
      memo : '',
    }
  },
  methods :{
    goInsert(){
      let url;
      let msg;
      if(this.memoType  === 'BLACK') {
        url = '/admin/member/update/blackMemo';
        msg = '블랙회원으로 등록 하시겠습니까?';
      } else if(this.memoType === 'WITHDRAW'){
        url = '/admin/member/update/withdrawMemo';
        msg = '탈퇴회원 처리 하시겠습니까?';
      }

      if(confirm(msg)) {
        let param = {
          memo: this.memo,
          userNoArr: this.userNoArr
        }

        this.$http.post(url, param).then(result => {
          this.$util.debug(result);
          this.onClose();
        }).catch(error => {
          this.$util.debug(error);
        })
      }
    },
    onClose() {
      this.memo = '';
      this.$emit('closeMemo');
    },
  }
}
</script>

<style scoped>

</style>