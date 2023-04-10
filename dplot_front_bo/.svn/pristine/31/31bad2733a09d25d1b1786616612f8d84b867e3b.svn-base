<template>
  <!-- 공지사항 등록 팝업 -->
  <div id="modal-wrap" class="modal" style="display: block;">
    <div class="modal-content" style="width: 1200px;">
      <div class="pop-header">
        <h2>공지사항 등록</h2>
        <button type="button" class="pop-close" @click="onClose"></button>
      </div>
      <div class="pop-body">
        <div class="boxing">
          <div class="form-area">
            <dl>
              <dt>작성자</dt>
              <dd><p>{{ boardInfo.writer }}</p></dd>
            </dl>
            <dl>
              <dt>등록일</dt>
              <dd><p>{{ boardInfo.regdate }}</p></dd>
            </dl>
            <dl>
              <dt>필독공지</dt>
              <dd><input type="checkbox" id="chk011" v-model="boardInfo.isessnotice"><label for="chk011">필독 공지글로
                등록</label></dd>
            </dl>
            <dl>
              <dt>팝업공지</dt>
              <dd><input type="checkbox" id="chk022" v-model="boardInfo.ispapopnotice">
                <label for="chk022">파트너사 팝업 공지로 등록</label>
              </dd>
              <CommonDatePickerFromTo
                  v-show="boardInfo.ispapopnotice"
                  :fromYYYYMMDD="timeInfo.startYYYYMMDD"
                  :fromHH="timeInfo.startHH"
                  :fromMM="timeInfo.startMM"
                  :toYYYYMMDD="timeInfo.toYYYYMMDD"
                  :toHH="timeInfo.toHH"
                  :toMM="timeInfo.toMM"
                  @getDate="getTimeDate"
              />
            </dl>
            <dl>
              <dt>사용여부</dt>
              <dd>
                <div class="radio_wrap wide">
                  <input type="radio" name="sms" id="rd110" value="F" checked v-model="boardInfo.istrash"/>
                  <label for="rd110">사용</label>
                  <input type="radio" name="sms" id="rd120" value="T" v-model="boardInfo.istrash"/>
                  <label for="rd120">미사용</label>
                </div>
              </dd>
            </dl>
            <dl>
              <dt>제목</dt>
              <dd><input type="text" style="width: 100%;" v-model="boardInfo.subject" ref="subjectObj"></dd>
            </dl>
          </div>
        </div>
        <div class="boxing" style="border: 0">
          <CommonEditor ref="editor" :style-object="styleObject"/>
        </div>
        <div class="boxing">
          <div class="form-area">
            <dl>
              <dt>링크 URL 1</dt>
              <dd><input type="text" style="width: 100%;" v-model="boardInfo.linkurl1"></dd>
            </dl>
            <dl>
              <dt>링크 URL 2</dt>
              <dd><input type="text" style="width: 100%;" v-model="boardInfo.linkurl2"></dd>
            </dl>
            <dl>
              <dt>첨부파일</dt>
              <dd>
                <label for="input-file-new" class="btn blue-line">파일첨부</label>
                <input type="file" id="input-file-new" style="display: none" accept="image/*"
                       ref="inputFile" @change="selectFile($event.target)" multiple>
                <div v-for="(row, i) in uploadFile" :key="i">
                  <a class="file-link">{{ row.file.name }}</a>
                  <button type="button" class="file-del" @click="deleteUploadFile(i)"></button>
                </div>
              </dd>
            </dl>
          </div>
        </div>
        <div class="btn-group">
          <button type="button" class="btn big blue" @click="goSave">저장</button>
          <button type="button" class="btn big darkgray" @click="onClose">취소</button>
        </div>
      </div>
    </div>
  </div>
  <!-- /공지사항 등록 팝업-->
</template>

<script>
import CommonEditor from "../../common/CommonEditor";
import CommonDatePickerFromTo from "@views.admin/common/CommonDatePickerFromTo";

export default {
  name: "admin.partners.notice.newpost",
  components: {
    CommonDatePickerFromTo,
    CommonEditor
  },
  data() {
    return {
      styleObject: {                                                            // 에디터 스타일
        'height': '400px',
      },
      boardInfo: {
        subject: "",                                                            // 제목
        userno: "",                                                             // 회원 번호
        writer: "",                                                             // 회원 이름
        content: "",                                                            // 내용
        linkurl1: "",                                                           // 연결 링크1
        linkurl2: "",                                                           // 연결 링크2
        ip: "",                                                          // 작성자_ip
        hits: 0,                                                                // 조회수
        istrash: "F",                                                           // 사용 여부
        regdate: this.$util.getFormatDate(this.$util.getDate(), '-'),   // 등록일자
        isessnotice: false,                                                     // 필독 공지 여부
        ispapopnotice: false,                                                   // 입점사 팝업 공지 여부
        poststtime: "",                                                         // 게시 시작일
        postedtime: "",                                                         // 게시 종료일
        isallagree: "",                                                         // 전체 동의 여부
        postst: "",                                                             // 게시 상태
      },
      timeInfo: {              // 진행기간
        startYYYYMMDD: '',
        startHH: '',
        startMM: '',
        toYYYYMMDD: '',
        toHH: '',
        toMM: ''
      },
      uploadFile: [],                                                           // 첨부파일 데이터
    }
  },
  mounted() {
    // 권한 설정
    this.$http.post('/admin/common/pageAuth/check', {url: this.$options.name}).then(result => {
      this.isRead = (result.data.isread === 'T');
      this.isWrite = (result.data.iswrite === 'T');
      if (this.isRead) {
        this.onInit();
      } else{
        alert('페이지 접근 권한이 없습니다.');
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
    onInit() {
      this.setUserInfo();
      this.setDateInfo();
    },

    // 유저 정보 셋팅
    setUserInfo() {
      let userInfo = this.$util.getUser(this.$store.getters['CONSTANTS'].MANAGER_SESSION);
      this.boardInfo.userno = userInfo.no;
      this.boardInfo.writer = userInfo.name;
    },

    // 시간 정보 셋팅
    setDateInfo() {
      this.timeInfo.startYYYYMMDD = this.$util.getDate('-');
      this.timeInfo.startHH = '00';
      this.timeInfo.startMM = '00';

      this.timeInfo.toYYYYMMDD = this.$util.getDate('-');
      this.timeInfo.toHH = '00';
      this.timeInfo.toMM = '59';
    },

    // 첨부 파일
    selectFile(target) {
      let files = target.files;

      for (let i = 0; i < files.length; i++) {
        if (this.uploadFile.length >= 5) {
          return;
        }

        let obj = {
          file: files[i],
          key: i
        }

        this.uploadFile.push(obj);
      }

      // 초기화
      this.$refs.inputFile.value = '';
    },

    // 첨부 파일 삭제
    deleteUploadFile(index) {
      this.uploadFile.splice(index, 1);
    },

    // 유효성 체크
    checkValidation() {
      let msg = '';
      let checkResult = true;
      let valid = [
        {field: 'boardInfo.subject', type: 'I', name: '제목', required: true},
      ]

      msg = this.$util.validMsg(this.$data, valid);
      if (!this.$util.isNull(msg)) {
        checkResult = false;
        alert(msg);
      }

      if(this.boardInfo.ispapopnotice) {
        if(this.boardInfo.poststtime > this.boardInfo.postedtime) {
          alert("게시종료일자를 시작일자 이후로 설정해주세요.");
          return false;
        }
      }

      return checkResult;
    },

    // 저장
    goSave() {
      // 유효성 체크
      if (!this.checkValidation()) {
        return;
      }

      if (confirm("저장 하시겠습니까?")) {
        let params = {};
        let files = [];
        
        this.uploadFile.forEach((file,index) => {
          files.push({key: 'file'+index , file: file.file});
        })

        params.files = files;

        // 팝업 공지에 따른 시작/종료일시 셋팅
        if (this.boardInfo.ispapopnotice === false) {
          this.boardInfo.poststtime = "";
          this.boardInfo.postedtime = "";
        }

        // 에디터 텍스트 데이터 저장
        this.boardInfo.content = this.$refs.editor.content;

        params = Object.assign({}, params, this.boardInfo);

        this.$http.post('/admin/partners/notice/save', params)
            .then(result => {
              if (result.statusCode === 200) {
                alert("저장이 완료되었습니다.");
                this.onClose(true);
              } else {
                alert("저장에 실패했습니다.");
              }
            })
            .catch(error => {
              this.$util.debug(error);
            })
      }
    },

    // 닫기
    onClose(isreload) {
      if(typeof(isreload) === 'boolean' && isreload) {
        this.$parent.goCloseNewPostPopup(true);
      } else {
        this.$parent.goCloseNewPostPopup();
      }
      this.boardInfo = [];
      this.$refs.editor.content = "";
    },

    // 진행기간 DatePicker 콜백 메서드
    getTimeDate(date) {
      this.boardInfo.poststtime = date.fromDate12;
      this.boardInfo.postedtime = date.toDate12;
    },
  },
}
</script>
