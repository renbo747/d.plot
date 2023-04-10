<template>
  <!-- 상품 문의 상세 팝업 -->
  <div id="modal-wrap" class="modal" style="display: block;">
    <div class="modal-content" style="width: 1100px;">
      <div class="pop-header">
        <h2>상품 문의 상세</h2>
        <button type="button" class="pop-close" @click="onClose"></button>
      </div>
      <div class="pop-body">
        <div class="qna-area">
          <div class="title orange"><i class="icon-q"></i>질문</div>
          <div class="bd-wrap">
            <table cellpadding="0" cellspacing="0">
              <caption>상품 문의 - 질문</caption>
              <colgroup>
                <col width="10%">
                <col width="14%">
                <col width="10%">
                <col width="15%">
                <col width="11%">
                <col width="14%">
                <col width="10%">
                <col width="16%">
                <col width="2%">
              </colgroup>
              <tbody>
              <tr v-if="isAdmin">
                <th>고객명</th>
                <td>{{ boardInfo.name }}</td>
                <th>아이디</th>
                <td>{{ boardInfo.writer }}</td>
                <th>회원유형/등급</th>
                <td>{{ boardInfo.usertype }}</td>
                <th>문의일시</th>
                <td>{{ boardInfo.regdate }}</td>
              </tr>
              <tr v-else>
                <th>문의구분</th>
                <td colspan="3">{{ boardInfo.qnatype }}</td>
                <th>문의일시</th>
                <td colspan="3">{{ boardInfo.regdate }}</td>
              </tr>
              <tr>
                <th>상품명</th>
                <td colspan="7">{{ boardInfo.goodsname }}<br>{{ !$util.isNull(boardInfo.optionname) ? '옵션 : '+boardInfo.optionname : ''}}</td>
              </tr>
              <tr class="contents">
                <th>질문내용</th>
                <td colspan="7" v-html="boardInfo.content">
                  <!-- {{ boardInfo.content }} -->
                </td>
              </tr>
              </tbody>
            </table>
          </div>
        </div>
        <div class="qna-area">
          <div class="title blue"><i class="icon-a"></i>답변</div>
          <div class="bd-wrap">
            <table cellpadding="0" cellspacing="0">
              <caption>상품 문의 - 답변</caption>
              <colgroup>
                <col width="10%">
                <col width="20%">
                <col width="10%">
                <col width="20%">
                <col width="10%">
                <col width="">
              </colgroup>
              <tbody>
              <tr>
                <th>판매구분</th>
                <td>{{ boardInfo.ispbgoods }}</td>
                <th>파트너사</th>
                <td>{{ boardInfo.dealername }}</td>
                <th>상태</th>
                <td>{{ boardInfo.isreply }}</td>
              </tr>
              <tr>
                <th>답변자</th>
                <td>{{ boardInfo.repusername }}</td>
                <th>아이디</th>
                <td>{{ boardInfo.repuserid }}</td>
                <th>답변일시</th>
                <td>{{ boardInfo.repregdate }}</td>
              </tr>
              <tr>
                <th>문구 템플릿</th>
                <td colspan="5">
                  <select v-model="boardInfo.tplidx" @change="changeContent()" style="width: 500px;">
                    <option value="">자주 쓰는 답변 선택</option>
                    <option v-for="(row, c) in this.template" :key="c" :value="row.tplidx">{{ row.subject }}</option>
                  </select>
                </td>
              </tr>
              <tr class="contents">
                <th>답변내용</th>
                <td colspan="7">
                  <CommonEditor ref="editor"/>
                </td>
              </tr>
              </tbody>
            </table>
          </div>
        </div>
        <div class="btn-group">
          <button type="button" class="btn big blue" @click="goSave" v-if="isWrite && boardInfo.isreply === '대기'">저장</button>
          <button type="button" class="btn big darkgray" @click="onClose">닫기</button>
        </div>
      </div>
    </div>
  </div>
  <!-- /상품 문의 상세 팝업-->
</template>

<script>
import CommonEditor from "@views.admin/common/CommonEditor";
export default {
  name: "admin.cs.product.detail",
  components: {CommonEditor},
  data() {
    return {
      isAdmin: this.$util.isAuthorized('ADMIN_USER'),
      boardInfo: {},
      template: [],
      isRead: false,
      isWrite: false
    }
  },
  mounted() {
    // 권한 설정
    this.$http.post('/admin/common/pageAuth/check', {url: this.$options.name}).then(result => {
      this.isRead = (result.data.isread === 'T');
      this.isWrite = (result.data.iswrite === 'T');
      if(this.isRead){
          this.onOpen();
      }else{
        alert('페이지 접근 권한이 없습니다.');
        this.info = this.$options.data().info
        this.onClose();
      }

      if(!this.isWrite){
        let buttons = this.$el.getElementsByTagName('button');

        for(let button of buttons){
          if(button.className !== 'pop-close') {
              button.style.display = 'none';
              button.disabled = true;
          }
        }
      }
    }).catch(error => {
      this.$util.debug(error);
    })
  },
  methods: {

    ///////////////////////// 내부 사용 메서드 /////////////////////////
    onOpen() {
      let params = {
        idx: this.idx
      }

      this.$http.post("/admin/cs/product/detail", params)
          .then(result => {
            if (result.statusCode === 200) {
              let data = result.data;
              this.boardInfo = data.list;
              this.template = data.template;
              this.boardInfo.idx = this.idx;
              this.$refs.editor.content = this.boardInfo.repcontent;

              if (this.boardInfo.isreply === '대기') {
                // 유저 정보 셋팅
                let userInfo = this.$util.getUser(this.$store.getters['CONSTANTS'].MANAGER_SESSION);
                this.boardInfo.repusername = userInfo.name;
                this.boardInfo.repuserno = userInfo.no;
                this.boardInfo.repuserid = userInfo.id;
                this.boardInfo.repregdate = this.$util.getDate('-') + " " + this.$util.getTime(':')
              }
            }
          })
          .catch(error => {
            this.$util.debug(error);
          });
    },

    onClose(isreload) {
      if(typeof(isreload) === 'boolean' && isreload) {
        this.$parent.goCloseDetailPopup(true);
      } else {
        this.$parent.goCloseDetailPopup();
      }
      this.boardInfo = [];
      this.$refs.editor.content = "";
    },

    goSave() {
      if(this.$util.isNull(this.$refs.editor.content)) {
        alert("답변내용을 입력해주세요.");
        return ;
      }
      
      if (confirm("저장 하시겠습니까?")) {
        let params = this.boardInfo;
        params.repcontent = this.$refs.editor.content;
        params.idx = this.boardInfo.idx;
        this.$http.post("/admin/cs/product/update", params)
            .then(result => {
              if (result.statusCode === 200) {
                alert("저장이 완료되었습니다.");
                this.onClose(true);
              } else {
                alert("저장이 실패했습니다.");
              }
            })
            .catch(error => {
              this.$util.debug(error);
            });
      }
    },

    changeContent() {
      let tplidx = this.boardInfo.tplidx;
      this.template.forEach(m => {
        if(m.tplidx === tplidx) {
          this.$refs.editor.content = m.content;
        }
      });
    },
    /////////////////////////////////////////////////////////////////

    //////////////////////// 외부, 콜백 메서드 /////////////////////////
    /////////////////////////////////////////////////////////////////

    ///////////////////////// 팝업 메서드 /////////////////////////////
    /////////////////////////////////////////////////////////////////

  },
  props: {
    idx: Number
  }
}
</script>
