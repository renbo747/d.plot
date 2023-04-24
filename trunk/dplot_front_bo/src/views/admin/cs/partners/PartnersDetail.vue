<template>
  <!-- 파트너사 문의 상세 팝업 -->
  <div id="modal-wrap" class="modal" style="display: block;">
    <div class="modal-content" style="width: 1100px;">
      <div class="pop-header">
        <h2>파트너사 문의 상세</h2>
        <button type="button" class="pop-close" @click="onClose"></button>
      </div>
      <div class="pop-body">
        <div class="qna-area">
          <div class="title orange"><i class="icon-q"></i>질문</div>
          <div class="bd-wrap">
            <table cellpadding="0" cellspacing="0">
              <caption>파트너사 문의 - 질문</caption>
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
                <th>담당자</th>
                <td>{{ boardInfo.writer }}</td>
                <th>질문일시</th>
                <td>{{ boardInfo.regdate }}</td>
                <th>답변상태</th>
                <td>{{ boardInfo.isreply }}</td>
              </tr>
              <tr>
                <th>파트너사</th>
                <td colspan="3">
                  <select style="width: 240px;" v-model="boardInfo.dealerno" @change="onDealerChange('F')"
                          ref="dealerSelectBox" :disabled="isAdmin !== true">
                    <option value="">선택</option>
                    <option v-for="(row, i) in dealerList" :key="i" :value="row.no">{{ row.name }}</option>
                  </select>
                  <select v-show="boardInfo.dealerno !== '' && chargeList.length !== 0" style="width: 240px;"
                          v-model="boardInfo.partchargeidx" :disabled="isAdmin !== true">
                    <option v-for="(row, i) in chargeList" :key="i" :value="row.idx">{{ row.charge }}</option>
                  </select>
                </td>
                <th>주문번호</th>
                <td>
                  <div v-if="isAdmin === true">
                    <input type="search" style="width: 250px;" placeholder="" disabled v-model="boardInfo.ordno">
                    <button type="button" class="btn-search" @click="openOrderPopup">검색</button>
                  </div>
                  <div v-else>
                    <input type="text" style="width: 250px;" placeholder="" disabled read v-model="boardInfo.ordno">
                  </div>
                </td>
              </tr>
              <tr>
                <th>제목</th>
                <td colspan="7">
                  <input type="text" style="width: 100%;" v-model="boardInfo.subject" ref="subject"
                         :disabled="isAdmin !== true"/>
                </td>
              </tr>
              <tr class="contents">
                <th>질문내용</th>
                <td colspan="7">
                  <CommonEditor ref="editor" v-bind:disable="!isAdmin"/>
                </td>
              </tr>
              </tbody>
            </table>
          </div>
        </div>
        <div class="qna-area" v-show="!isAdmin || (isAdmin && !$util.isNull(boardInfo.repcontent))">
          <div class="title blue"><i class="icon-a"></i>답변</div>
          <div class="bd-wrap">
            <table cellpadding="0" cellspacing="0">
              <caption>파트너사 문의 - 답변</caption>
              <colgroup>
                <col width="10%">
                <col width="40%">
                <col width="10%">
                <col width="">
              </colgroup>
              <tbody>
              <tr>
                <th>답변자</th>
                <td>{{ boardInfo.repname }}</td>
                <th>답변일시</th>
                <td>{{ boardInfo.repregdate }}</td>
              </tr>
              <tr class="contents">
                <th>답변내용</th>
                <td colspan="7">
                  <CommonEditor ref="repEditor" v-bind:disable="isAdmin"/>
                </td>
              </tr>
              </tbody>
            </table>
          </div>
        </div>
        <div class="btn-group">
          <button type="button" class="btn big blue" @click="goSave" v-show="isWrite && boardInfo.isreply !== '완료'">저장</button>
          <button type="button" class="btn big darkgray" @click="onClose">닫기</button>
        </div>
      </div>
    </div>
  </div>
  <!-- /파트너사 문의 상세 팝업-->
</template>

<script>
import CommonEditor from "@views.admin/common/CommonEditor";
import CommonAddOrderPopup from '@views.admin/common/popup/CommonAddOrderPopup';

export default {
  name: "admin.cs.partners.detail",
  props: {
    idx: Number
  },
  components: {
    CommonEditor
  },
  data() {
    return {
      boardInfo: {
        isreply: 'F',
        subject: '',
        regdate: '',
        dealerno: '',
        ordno: '',
        writer: '',
        idx: '',
        content: '',
        repuserno: '',
        repcontent: '',
        repname: '',
      },
      isAdmin: this.$util.isAuthorized('ADMIN_USER'), // 접속자 등급 여부 ( 어드민, 파트너사 구분 )
      userInfo: {},
      dealerList: [],                            // selectBox 파트너사 리스트
      chargeList: [],                            // selectBox 담당자 리스트
      isDealerSelect: false,                     // 팝업을 열때 파트너를 선택했는지
      isRead: false,
      isWrite: false
    }
  },
  methods: {

    ///////////////////////// 내부 사용 메서드 /////////////////////////
    // 상세 조회 오픈 시
    onOpen() {
      this.title = ''

      this.$http.post("/admin/cs/partners/detail", {idx: this.idx})
          .then(result => {
            if (result.statusCode === 200) {
              let data = result.data;
              this.boardInfo = data.list;
              this.$refs.editor.content = data.list.content;
              this.$refs.repEditor.content = data.list.repcontent;
              this.searchDealer();
              this.setUserInfo();
            }
          })
          .catch(error => {
            this.$util.debug(error);
          });
    },

    // 접속자 셋팅
    setUserInfo() {
      this.userInfo = this.$util.getUser(this.$store.getters['CONSTANTS'].MANAGER_SESSION);

      // 처음 답변을 작성하는 경우 && 파트너사 로그인
      if (this.$util.isNull(this.boardInfo.repuserno) && !this.isAdmin) {
        this.boardInfo.repname = this.userInfo.name;
        this.boardInfo.repuserno = this.userInfo.no;
      }
    },

    // 저장
    goSave() {
      if (this.boardInfo.dealerno === "") {
        this.$refs.dealerSelectBox.focus();
        alert("파트너사를 선택해주세요.");
        return;
      }

      if (this.boardInfo.subject === "") {
        this.$refs.subject.focus();
        alert("제목을 입력해주세요.");
        return;
      }

      if (this.$refs.editor.content == null) {
        alert("내용을 입력해주세요.");
        return;
      }

      if (confirm("저장 하시겠습니까?")) {
        let params = Object.assign({}, this.boardInfo);
        params.content = this.$refs.editor.content;
        params.isAdmin = this.isAdmin;

        // 이미 완료 상태인 경우
        if (this.boardInfo.isreply === '완료') {
          params.isreply = null;
          params.repregdate = null;
        }
        // 파트너사 로그인인 경우
        else if (!this.isAdmin) {
          params.repcontent = this.$refs.repEditor.content;
          params.repregdate = '';
          params.isreply = 'T';
        } else {
          params.isreply = 'F';
          params.repregdate = null;
        }
        
        this.$http.post("/admin/cs/partners/update", params)
            .then(result => {
              if (result.statusCode === 200) {
                alert("저장이 완료되었습니다.");
                this.onClose(true);
              }
            })
            .catch(error => {
              this.$util.debug(error);
              alert("저장이 실패했습니다.");
            });
      }
    },

    onClose(isreload) {
      if(typeof(isreload) === 'boolean' && isreload) {
        this.$parent.goCloseDetailPopup(true);
      } else {
        this.$parent.goCloseDetailPopup();
      }
      this.boardInfo = {};
      this.$refs.editor.content = "";
      this.$refs.repEditor.content = '';
    },

    // selectbox 파트너사 목록 조회
    searchDealer() {
      let params = {};

      this.$http.post("/admin/cs/partners/box/dealer", params)
          .then(result => {
            if (result.statusCode === 200) {
              this.dealerList = result.data.list;
              this.onDealerChange("T");
            }
          })
          .catch(error => {
            this.$util.debug(error);
          });
    },

    // 셀렉박스 클릭 이벤트
    // flag
    // - F : 셀렉 박스로 부터
    // - T : 메소드로 부터
    // DB에 저장된 담당자를 셋팅하는 부분에서 현재 변경된 상태를 판단하기 위해 Flag 사용
    onDealerChange(flag) {
      if (flag === 'F') {
        // 세팅된 주문번호 존재시 초기화
        this.boardInfo.ordno = "";
      }

      let params = {
        userno: this.boardInfo.dealerno,
        isloading: false,
      };

      this.$http.post("/admin/cs/partners/box/charge", params)
          .then(result => {
            if (result.statusCode === 200) {
              this.chargeList = result.data.list;
              if (this.boardInfo.partchargeidx != null) {
                if (flag === 'F') { // 셀렉 박스로 부터
                  this.boardInfo.partchargeidx = "";
                  this.boardInfo.partchargeidx = this.chargeList.length>0? this.chargeList[0].idx : "";
                }
              } else {
                this.boardInfo.partchargeidx = this.chargeList.length>0? this.chargeList[0].idx : "";
              }
            }
          })
          .catch(error => {
            this.$util.debug(error);
          });
    },
    /////////////////////////////////////////////////////////////////

    ///////////////////////// 팝업 메서드 /////////////////////////////
    openOrderPopup() {
      this.$eventBus.$emit('modalShow', CommonAddOrderPopup, { isdealer: 'T', dealerno: this.boardInfo.dealerno },
        (result) => {
          if (this.boardInfo.dealerno !== result.data.dealerno) {
            this.boardInfo.dealerno = result.data.dealerno;
            this.onDealerChange('F');
          }
          this.boardInfo.ordno = result.data.ordno;
        }
      );
    },
    /////////////////////////////////////////////////////////////////

  },

  mounted() {
    // 권한 설정
    this.$http.post('/admin/common/pageAuth/check', {url: this.$options.name}).then(result => {
      this.isRead = (result.data.isread === 'T');
      this.isWrite = (result.data.iswrite === 'T');
      if (this.isRead) {
        this.setUserInfo();
        this.onOpen();
      } else{
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
  }
}
</script>
