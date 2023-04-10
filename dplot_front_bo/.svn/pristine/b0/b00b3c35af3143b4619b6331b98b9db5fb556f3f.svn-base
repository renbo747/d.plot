<template>
  <!-- 답글 관리 팝업 -->
  <div id="modal-wrap" class="modal" style="display: block;">
    <div class="modal-content" style="width: 600px;">
      <div class="pop-header">
        <h2>답글 관리</h2>
        <button type="button" class="pop-close" @click="$emit('close', 'T')"></button>
      </div>
      <div class="pop-body">
        <textarea placeholder="내용을 입력하세요." v-model="content"></textarea>
        <div class="btn-group mt10">
          <button type="button" class="btn blue" @click="goSave">저장</button>
          <button type="button" class="btn darkgray" @click="$emit('close', 'T')">취소</button>
        </div>
      </div>
    </div>
  </div>
  <!-- /답글 관리 팝업 -->
</template>

<script>
export default {
  name: "ReplyInfoPopup",
  data() {
    return {
      content: '',
    }
  },
  props: {
    replyObj: Object
  },
  computed: {
    getContent() {
      return this.replyObj.comment;
    }
  },
  mounted() {
    this.content = this.getContent;
  },
  methods: {
    goSave() {
      if (confirm("저장 하시겠습니까?")) {
        let params = this.replyObj;
        params.comment = this.content;

        this.$http.post("/admin/operation/shopping/promotion/popup/modify/comment", params)
            .then(result => {
              if (result.statusCode === 200) {
                this.$emit("close", 'F');
              }
            })
            .catch(error => {
              this.$util.debug(error);
            });
      }
    }
  }
}
</script>
