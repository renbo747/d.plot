<template>
  <!-- 메일 발송 팝업 -->
  <div id="modal-wrap" class="modal" style="display: block;">
    <div class="modal-content" style="width: 900px;">
      <div class="pop-header">
        <h2>메일 발송</h2>
        <button type="button" class="pop-close" @click="$emit('close-popup')"></button>
      </div>
      <div class="pop-body">
        <div class="bar-title small">받는사람</div>
        <table cellpadding="0" cellspacing="0" class="gray-tb">
          <colgroup>
            <col width="120px">
            <col width="">
            <col width="120px">
            <col width="">
          </colgroup>
          <tbody>
          <tr>
            <th>이름</th>
            <td>{{ userInfo.name }}</td>
            <th>아이디</th>
            <td>{{ userInfo.userid }}</td>
          </tr>
          <tr>
            <th>이메일</th>
            <td colspan="3">{{ userInfo.email }}</td>
          </tr>
          </tbody>
        </table>
        <div class="bar-title small">메일 내용</div>
        <table cellpadding="0" cellspacing="0" class="gray-tb">
          <colgroup>
            <col width="120px">
            <col width="">
          </colgroup>
          <tbody>
          <tr>
            <th>제목</th>
            <td><input type="text" style="width: 100%;" v-model="boardInfo.subject"/></td>
          </tr>
          <tr>
            <th>내용</th>
            <td>
              <CommonEditor ref="editor"/>
            </td>
          </tr>
          </tbody>
        </table>
        <div class="btn-group">
          <button type="button" class="btn big blue" @click="sendMail">메일보내기</button>
          <button type="button" class="btn big darkgray" @click="$emit('close-popup')">닫기</button>
        </div>
      </div>
    </div>
  </div>
  <!-- /메일 발송 팝업 -->
</template>

<script>
import CommonEditor from "@views.admin/common/CommonEditor";

export default {
  name: "CommonMail",
  components: {
    CommonEditor
  },
  props: {
    userNo: Number
  },
  data() {
    return {
      userInfo: {
        name: '',
        userid: '',
        email: '',
      },
      boardInfo: {
        subject: '',
        content: '',
      }
    }
  },
  mounted() {
    this.onInit();
  },
  methods: {
    onInit() {
      this.setUserInfo();
    },

    setUserInfo() {
      this.$http.post("/admin/mail/init", {userNo: this.userNo}).then(result => {
        if (result.statusCode === 200) {
          this.userInfo = result.data;
        }
      }).catch(error => {
        this.$util.debug(error);
      });
    },

    sendMail() {
      if(this.$util.isNull(this.boardInfo.subject)) {
        alert("제목을 입력해주세요.");
        return ;
      }

      this.boardInfo.content = this.$refs.editor.content;
      if(this.$util.isNull(this.boardInfo.content)) {
        alert("내용을 입력해주세요.");
        return ;
      }

      let params = Object.assign(this.userInfo, this.boardInfo);
      this.$http.post("/admin/mail/send", params)
          .then(result => {
            if (result.statusCode === 200 && result.data.status === "0") {
              alert("메일이 정상적으로 발송 되었습니다.");
              this.$emit('close-popup');
            } else {
              alert(result.data.httpmessage);
            }
          })
          .catch(error => {
            this.$util.debug(error);
          });
    },
  }
}
</script>