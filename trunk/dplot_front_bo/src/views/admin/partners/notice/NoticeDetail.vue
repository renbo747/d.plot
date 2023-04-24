<template>
  <!-- 공지사항 상세 팝업 -->
  <div id="modal-wrap" class="modal" style="display: block;">
    <div class="modal-content" style="width: 1200px;">
      <div class="pop-header">
        <h2>공지사항 상세</h2>
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
            <dl v-if="isAdmin">
              <dt>필독공지</dt>
              <dd><input type="checkbox" id="chk01" v-model="boardInfo.isessnotice"><label for="chk01">필독 공지글로
                등록</label></dd>
            </dl>
            <dl v-if="isAdmin">
              <dt>팝업공지</dt>
              <dd><input type="checkbox" id="chk02" value="T"
                         v-model="boardInfo.ispapopnotice"><label for="chk02">파트너사 팝업 공지로 등록</label>
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
            <dl v-if="isAdmin">
              <dt>사용여부</dt>
              <dd>
                <div class="radio_wrap wide">
                  <input type="radio" name="sms" id="rd11" value="F" checked
                         v-model="boardInfo.istrash"/><label for="rd11">사용</label>
                  <input type="radio" name="sms" id="rd12" value="T"
                         v-model="boardInfo.istrash"/><label for="rd12">미사용</label>
                </div>
              </dd>
            </dl>
            <dl>
              <dt>제목</dt>
              <dd><input type="text" style="width: 100%;" v-model="boardInfo.subject" :disabled="!isAdmin"></dd>
            </dl>
          </div>
        </div>
        <div class="boxing" style="border: 0">
          <CommonEditor ref="editor" :style-object="{height : 300+'px'}" :disable="!isAdmin"/>
        </div>
        <div class="boxing">
          <div class="form-area">
            <dl>
              <dt>링크 URL 1</dt>
              <dd>
                <input type="text" style="width: calc(100% - 72px);" v-model="boardInfo.linkurl1" :disabled="!isAdmin" >
                <button type="button" class="btn blue-line" @click="goLink(boardInfo.linkurl1)">링크이동</button>
              </dd>
            </dl>
            <dl>
              <dt>링크 URL 2</dt>
              <dd>
                <input type="text" style="width: calc(100% - 72px);" v-model="boardInfo.linkurl2" :disabled="!isAdmin">
                <button type="button" class="btn blue-line" @click="goLink(boardInfo.linkurl2)">링크이동</button>
              </dd>
            </dl>
            <dl>
              <dt>첨부파일</dt>
              <dd>
                <label for="input-file" class="btn blue-line" v-if="isAdmin">파일첨부</label>
                <input type="file" id="input-file" style="display: none" accept="image/*"
                       ref="inputFile" @change="selectFile($event.target)" multiple>
                <div>
                  <!-- DB 파일 데이터 -->
                  <div v-for="(row, i) in boardInfo.uploadedfile" :key="(i+1)*-1">
                    <a @click="imageView(row.fullpath)" class="file-link">{{ row.imgforiname }}</a>
                    <button v-if="isAdmin" type="button" class="file-del" @click="deleteUploadFile(i, 'N')"></button>
                  </div>
                  <!-- 새로 추가된 파일 데이터  -->
                  <div v-for="(row, i) in addUploadFile" :key="i">
                    <label class="file-link" style="text-decoration: none !important; color: #666">
                      {{ row.file.name }}
                    </label>
                    <button v-if="isAdmin" type="button" class="file-del" @click="deleteUploadFile(i, 'Y')"></button>
                  </div>
                </div>
              </dd>
            </dl>
          </div>
        </div>
        <div class="btn-group">
          <button type="button" class="btn big blue" @click="goSave" v-if="isWrite && isAdmin">저장</button>
          <button type="button" class="btn big darkgray" @click="onClose">닫기</button>
        </div>
      </div>
    </div>
  </div>
  <!-- /공지사항 상세 팝업-->

</template>

<script>
import CommonEditor from "@views.admin/common/CommonEditor";
import CommonDatePickerFromTo from "@views.admin/common/CommonDatePickerFromTo";

export default {
  name: "admin.partners.notice.detail",
  components: {
    CommonDatePickerFromTo,
    CommonEditor,
  },

  data() {
    return {
      boardInfo: {},
      // uploadedfile: [],    // DB 데이터
      addUploadFile: [],      // 추가 데이터
      deleteFile: [],   // 삭제 데이터
      timeInfo: {              // 진행기간
        startYYYYMMDD: '',
        startHH: '',
        startMM: '',
        toYYYYMMDD: '',
        toHH: '',
        toMM: ''
      },
      isAdmin: this.$util.isAuthorized('ADMIN_USER'),
      isRead: false,
      isWrite: false
    }
  },
  methods: {
    // 닫기
    onClose(isreload) {
      if(typeof(isreload) === 'boolean' && isreload) {
        this.$parent.goCloseDetailPopup(true);
      } else {
        this.$parent.goCloseDetailPopup();
      }
      this.boardInfo = [];
      this.deleteFile = [];
      this.addUploadFile = [];
      this.$refs.editor.content = "";
    },

    // 열기
    onOpen() {
      let params = {
        idx: this.idx, 
        isadmin : this.isAdmin ? 'T' : 'F'
      }

      this.$http.post("/admin/partners/notice/detail", params)
          .then(result => {
            this.boardInfo = result.data;
            this.boardInfo.ispapopnotice = (this.boardInfo.ispapopnotice !== 'false');
            this.boardInfo.isessnotice = (this.boardInfo.isessnotice !== 'false');
            this.$refs.editor.content = this.boardInfo.content;
            this.setTimeInfo();
          })
          .catch(error => {
            this.$util.debug(error);
          })
    },

    // 저장
    goSave() {
      if (this.boardInfo.subject === "") {
        alert("제목을 입력해주세요.");
        this.$refs.subjectObj.focus();
        return;
      }

      if(this.boardInfo.ispapopnotice) {
        if(this.boardInfo.poststtime > this.boardInfo.postedtime) {
          alert("게시종료일자를 시작일자 이후로 설정해주세요.");
          return false;
        }
      }

      // 에디터 텍스트 데이터 저장
      this.boardInfo.content = this.$refs.editor.content;

      if(this.$util.isNull(this.boardInfo.content)) {
        alert("내용을 입력해주세요.");
        return false;
      }


      if (confirm("저장 하시겠습니까?")) {
        // 삭제, 추가된 파일
        let params = {
          'deletefile': this.deleteFile
        }

        let files = [];
        this.addUploadFile.forEach((file,index) => {
          files.push({key: 'file'+index , file: file.file});
        })
        params.files = files;

        // update시 "idxList" 형태로 controller에서 사용하므로
        this.boardInfo.idxList = [this.boardInfo.idx];

        // 팝업 공지에 따른 시작/종료일시 셋팅
        if (this.boardInfo.ispapopnotice === false) {
          this.boardInfo.poststtime = "";
          this.boardInfo.postedtime = "";
        }
        
        params = Object.assign({}, params, this.boardInfo);

        // 데이터 통신
        this.$http.post("/admin/partners/notice/update", params)
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

    // 링크 이동
    goLink(url) {
      if (url.indexOf("http") === 0) {
        window.open(url, "_blank");
      } else {
        window.open("https://" + url, "_blank");
      }
    },

    // 첨부 파일
    selectFile(target) {
      let files = target.files;

      for (let i = 0; i < files.length; i++) {
        let totalSize = this.boardInfo.uploadedfile.length + this.addUploadFile.length;
        if (totalSize >= 5) {
          return;
        }

        // 실제 저장될 파일 데이터
        let addFile = {
          file: files[i],
          key: i
        }

        this.addUploadFile.push(addFile);
      }

      // 초기화
      this.$refs.inputFile.value = '';
    },

    // 첨부 파일 삭제
    deleteUploadFile(index, isNewFile) {
      // 새로 추가된 파일
      if (isNewFile === 'Y') {
        this.addUploadFile.splice(index, 1);
      } else {
        let file = this.boardInfo.uploadedfile.splice(index, 1)[0];
        this.deleteFile.push(file);
      }
    },

    // 진행기간 DatePicker 콜백 메서드
    getTimeDate(date) {
      this.boardInfo.poststtime = date.fromDate12;
      this.boardInfo.postedtime = date.toDate12;
    },

    // 이미지 미리 보기
    imageView(url) {
      this.$viewerApi({
        images: [url]
      })
    },

    // 검색 시간 데이터 셋팅
    setTimeInfo() {
      // 날짜 셋팅
      this.timeInfo.startYYYYMMDD = this.boardInfo.startyear;
      this.timeInfo.startHH = this.boardInfo.starthour;
      this.timeInfo.startMM = this.boardInfo.startminute;

      this.timeInfo.toYYYYMMDD = this.boardInfo.endyear;
      this.timeInfo.toHH = this.boardInfo.endhour;
      this.timeInfo.toMM = this.boardInfo.endminute;
    },
  },

  mounted() {
    // 권한 설정
    this.$http.post('/admin/common/pageAuth/check', {url: this.$options.name})
    .then(result => {
        this.isRead = (result.data.isread === 'T');
        this.isWrite = (result.data.iswrite === 'T');
        if (this.isRead) {
          this.onOpen();
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
    })
    .catch(error => {
      this.$util.debug(error);
    })
  },
  props: {
    idx: Number
  }
}
</script>
