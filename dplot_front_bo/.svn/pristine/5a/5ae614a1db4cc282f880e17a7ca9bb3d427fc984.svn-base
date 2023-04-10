<template>
  <!-- 1:1 문의 상세 팝업 -->
  <div id="modal-wrap" class="modal" style="display: block;">
    <CommonImageView
        ref="picturePopup"
        v-if="isPictureViewPopupShow"
        :img-path="imgPath"
        @close="closeUploadFilePopup"
    />
    <CommonVideoView
        ref="videoPopup"
        v-if="isVideoViewPopupShow"
        :videoInfo="videoInfo"
        @close="closeVideoPopup"
    />
    <div class="modal-content" style="width: 1100px;">
      <div class="pop-header">
        <h2>1:1 문의 상세</h2>
        <button type="button" class="pop-close" @click="onClose"></button>
      </div>
      <div class="pop-body">
        <div class="qna-area">
          <div class="title orange"><i class="icon-q"></i>질문</div>
          <div class="bd-wrap">
            <table cellpadding="0" cellspacing="0">
              <caption>1:1 문의 - 질문</caption>
              <colgroup>
                <col width="10%">
                <col width="15%">
                <col width="10%">
                <col width="15%">
                <col width="10%">
                <col width="15%">
                <col width="10%">
                <col width="15%">
                <col width="2%">
              </colgroup>
              <tbody>
              <tr>
                <th>문의유형</th>
                <td>{{ listData.inquirytype }}</td>
                <th>주문번호</th>
                <td>{{ listData.ordno }}</td>
                <th>송장번호</th>
                <td>{{ listData.invoiceno }}</td>
                <th>문의일시</th>
                <td>{{ listData.regdate }}</td>
              </tr>
              <tr v-if="isAdmin">
                <th>고객명</th>
                <td>{{ listData.name }}</td>
                <th>아이디</th>
                <td>{{ listData.memberid }}</td>
                <th>회원유형/등급</th>
                <td>{{ listData.dadamembertype }}/{{ listData.memlvtype }}</td>
                <th>전송여부</th>
                <td>{{ listData.ispass }}</td>
              </tr>
              <tr>
                <th>상품명</th>
                <td colspan="7">{{ listData.goodsname }}<br>{{ !$util.isNull(listData.optionname) ? '옵션 : '+listData.optionname : ''}}</td>
              </tr>
              <tr class="contents">
                <th>질문내용</th>
                <td colspan="7" v-html="listData.content">
                  <!-- {{ listData.content }} -->
                </td>
              </tr>
              <tr>
                <th>첨부 이미지</th>
                <td colspan="3">
                  <span v-for="(row, i) in fileList" :key="i">
                    <a  class="file-link link"
                     @click="showPicture(row.fullpath)">
                    {{ row.imgfname }}
                  </a>
                  </span>
                </td>
                <th>첨부 동영상</th>
                <td>
                  <span v-for="(row, n) in videoList" :key="n">
                    <a  class="file-link link"
                     @click="showVideo(row)">
                    {{ row.imgfname }}
                  </a>
                  </span>
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
              <caption>1:1 문의 - 답변</caption>
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
                <td>{{ listData.ispbgoods }}</td>
                <th>파트너사</th>
                <td>{{ listData.dealername }}</td>
                <th>상태</th>
                <td>{{ listData.isreply }}</td>
              </tr>
              <tr>
                <th>답변자</th>
                <td>{{ listData.repname }}</td>
                <th>아이디</th>
                <td>{{ listData.repid }}</td>
                <th>답변일시</th>
                <td>{{ listData.repregdate }}</td>
              </tr>
              <tr>
                <th>문구 템플릿</th>
                <td colspan="5">
                  <select v-model="listData.tplidx" @change="changeContent()" style="width: 500px;">
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
          <button type="button" class="btn big blue" @click="goSave" v-show="isWrite && listData.isreply === '대기'">저장</button>
          <button type="button" class="btn big darkgray" @click="onClose">닫기</button>
        </div>
      </div>
    </div>
  </div>
  <!-- /1:1 문의 상세 팝업-->
</template>

<script>
import CommonEditor from "@views.admin/common/CommonEditor";
import CommonImageView from "@views.admin/common/popup/CommonImageView";
import CommonVideoView from "@views.admin/common/popup/CommonVideoView";

export default {
  name: "admin.cs.oneone.detail",
  components: {CommonEditor, CommonImageView, CommonVideoView},
  props: {
    idx: Number,
  },
  data() {
    return {
      isAdmin: this.$util.isAuthorized('ADMIN_USER'),
      listData: {},
      fileList: [],
      videoList: [],
      template: [],
      imgPath: null,
      videoInfo: null,
      isPictureViewPopupShow: false,
      isVideoViewPopupShow: false,
      isRead: false,
      isWrite: false
    }
  },
  mounted() {
    // 권한 설정
    this.$http.post('/admin/common/pageAuth/check', {url : this.$options.name}).then(result => {
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
    });
  },
  methods: {

    ///////////////////////// 내부 사용 메서드 /////////////////////////
    // 초기화
    onOpen() {
      let params = {
        idx: this.idx
      }

      this.$http.post("/admin/cs/oneone/detail", params)
          .then(result => {
            if (result.statusCode === 200) {
              let data = result.data;
              this.listData = data.list;
              this.fileList = data.filelist;
              this.template = data.template;
              this.videoList = data.videofile;
              this.$refs.editor.content = this.listData.repcontent;

              if (this.listData.isreply === '대기') {
                let userInfo = this.$util.getUser(this.$store.getters['CONSTANTS'].MANAGER_SESSION);
                this.listData.repname = userInfo.name;
                this.listData.repuserno = userInfo.no;
                this.listData.repid = userInfo.id;
                this.listData.repregdate = this.$util.getDate('-') + " " + this.$util.getTime(':')
              }
            }
          })
          .catch(error => {
            this.$util.debug(error);
          })
    },

    // 닫기
    onClose(isreload) {
      if(typeof(isreload) === 'boolean' && isreload) {
        this.$parent.goCloseDetailPopup(true);
      } else {
        this.$parent.goCloseDetailPopup();
      }
      this.listData = [];
      this.$refs.editor.content = "";
    },

    goSave() {
      if (confirm("저장 하시겠습니까?")) {
        let params = {
          // isreply : this.listData.isreply,
          userno: this.listData.userno,
          repuserno: this.listData.repuserno,
          repregdate: '',
          repcontent: this.$refs.editor.content,
          idx: this.listData.idx,
          tplidx: this.listData.tplidx,
          mobile: this.listData.mobile,
        };

        this.$http.post("/admin/cs/oneone/update", params)
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
      let tplidx = this.listData.tplidx;
      this.template.forEach(m => {
        if(m.tplidx === tplidx) {
          this.$refs.editor.content = m.content;
        }
      });
    },
    showVideo(videofile) {
        this.videoInfo = videofile;
        this.isVideoViewPopupShow = true;
    },
    showPicture(url) {
        this.imgPath = url;
        this.isPictureViewPopupShow = true;
    },
    closeUploadFilePopup() {
        this.isPictureViewPopupShow = false;
    },
    closeVideoPopup() {
        this.isVideoViewPopupShow = false;
    },
    /////////////////////////////////////////////////////////////////

    //////////////////////// 외부, 콜백 메서드 /////////////////////////
    /////////////////////////////////////////////////////////////////

    ///////////////////////// 팝업 메서드 /////////////////////////////
    /////////////////////////////////////////////////////////////////

  },


}
</script>
