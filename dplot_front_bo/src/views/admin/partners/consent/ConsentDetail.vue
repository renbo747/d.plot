<template>
  <div>

    <!--  파트너사 선택 팝업  -->
    <PartnersListPopup
        ref="partnersListPopup"
        v-show="isPartnersShow"
        :checkPartenrs="partnersList"
        :boardIdx="boardInfo.idx"
        @getPartnersList="getPartnersList"
        @closePartnersPopup="goClosePartnersPopup"/>

    <!-- 파트너사 동의공지 상세 팝업 -->
    <div id="modal-wrap" class="modal" style="display: block;">
      <div class="modal-content" style="width: 1200px;">
        <div class="pop-header">
          <h2>파트너사 동의공지 상세</h2>
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
                <dt>파트너사</dt>
                <dd>
                  <div class="radio_wrap wide">
                    <input type="radio" name="partners" id="rd001" value="T" v-model="boardInfo.isallagree"/><label
                      for="rd001" @click="onClickAllBtn">전체</label>
                    <input type="radio" name="partners" id="rd002" value="F" v-model="boardInfo.isallagree"
                           @click="goPartnersPopup()"/><label
                      for="rd002">선택</label>
                  </div>
                  <input type="text" style="width: 500px;" v-model="partnersListed" disabled>
                  <button type="button" class="btn black-line" style="margin-left : 5px;" @click="downloadExcelTemplate('MemberTemplate.xlsx')">양식 다운로드</button>
                </dd>
              </dl>
              <dl>
                <dt>상태</dt>
                <dd v-if="isAdmin">
                  <div class="radio_wrap wide5">
                    <div v-for="(row, i) in poststList" :key="i">
                      <input type="radio" :id="'rd' + i" :value="row.cmcode" v-model="boardInfo.postst"><label
                        :for="'rd' + i">{{ row.codename }}</label>
                    </div>
                  </div>
                </dd>
                <dd v-else><p>{{ boardInfo.postname }}</p></dd>
              </dl>
              <dl>
                <dt>동의 시작/종료일시</dt>
                <dd v-if="isAdmin">
                  <CommonDatePickerFromTo
                      :fromYYYYMMDD="timeInfo.startyear"
                      :fromHH="timeInfo.starthour"
                      :fromMM="timeInfo.startminute"
                      :toYYYYMMDD="timeInfo.endyear"
                      :toHH="timeInfo.endhour"
                      :toMM="timeInfo.endminute"
                      @getDate="getTimeDate"
                  />
                </dd>
                <dd v-else>{{ boardInfo.agreetime }}</dd>
              </dl>
              <dl v-if="isAdmin">
                <dt>팝업공지</dt>
                <dd><input type="checkbox" id="chk02" value="T"
                           v-model="boardInfo.ispapopnotice"><label for="chk02">파트너사 팝업 공지로 등록</label></dd>
              </dl>
              <dl>
                <dt>제목</dt>
                <dd><input type="text" style="width: 100%;" v-model="boardInfo.subject" ref="subjectObj" :disabled="!isAdmin"></dd>
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
                  <input type="text" style="width: calc(100% - 72px);" v-model="boardInfo.linkurl1" :disabled="!isAdmin">
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
                  <label for="input-file-detail" class="btn blue-line"  v-if="isAdmin">파일첨부</label>
                  <input type="file" id="input-file-detail" style="display: none" accept="image/*"
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
                      <button type="button" class="file-del" @click="deleteUploadFile(i, 'Y')"></button>
                    </div>
                  </div>
                </dd>
              </dl>
            </div>
          </div>
          <div class="btn-group">
            <button type="button" class="btn big blue" @click="goAgree('T')" v-if="isWrite && !isAdmin && boardInfo.postst === 'POS002'">동의</button>
            <button type="button" class="btn big blue" @click="goAgree('F')" v-if="isWrite && !isAdmin && boardInfo.postst === 'POS002'">미 동의</button>
            <button type="button" class="btn big blue" @click="goSave" v-if="isWrite && isAdmin">저장</button>
            <button type="button" class="btn big darkgray" @click="onClose">닫기</button>
          </div>
        </div>
      </div>
    </div>
    <!-- /파트너사 동의공지 상세 팝업-->
  </div>

</template>

<script>
import CommonEditor from "@views.admin/common/CommonEditor";
import PartnersListPopup from "@views.admin/partners/popup/PartnersListPopup";
import CommonDatePickerFromTo from "@views.admin/common/CommonDatePickerFromTo";

export default {
  name: "admin.partners.consent.detail",
  components: {CommonDatePickerFromTo, PartnersListPopup, CommonEditor},
  props: {
    poststList: Array,
    idx: Number,
  },
  data() {
    return {
      isAdmin: this.$util.isAuthorized('ADMIN_USER'),
      boardInfo: {},            // 게시판 정보
      addUploadFile: [],        // 추가 파일 데이터
      deleteFile: [],           // 삭제 파일 데이터
      partnersList: [],         // 선택된 파트너사 리스트
      deleteList: [],           // 삭제된 파트너사 리스트
      addList: [],              // 추가된 파트너사 리스트
      isPartnersShow: false,    // 파트너사 선택 팝업
      isRead: false,            // 읽기 권한
      isWrite: false,           // 쓰기 권한
      timeInfo: {              // 진행기간
        startyear: '',
        starthour: '',
        startminute: '',
        endyear: '',
        endhour: '',
        endminute: '',
      },
    }
  },
  methods: {

    ///////////////////////// 내부 사용 메서드 /////////////////////////
    // 열기
    onOpen() {
      let params = {idx: this.idx}
      this.$http.post("/admin/partners/consent/detail", params)
          .then(result => {
            let data = result.data;

            this.boardInfo = data;
            this.boardInfo.ispapopnotice = (this.boardInfo.ispapopnotice !== 'false');
            this.$refs.editor.content = this.boardInfo.content;

            this.setTimeInfo();

            // 체크한 파트너 리스트
            for (let i = 0; i < data.nolist.length; i++) {
              let list = {
                no: data.nolist[i],
                name: data.namelist[i],
                userid: data.useridlist[i]
              }
              this.partnersList.push(list);
            }
          })
          .catch(error => {
            this.$util.debug(error);
          })
    },

    // 검색 시간 데이터 셋팅
    setTimeInfo() {
      // 날짜 셋팅
      this.timeInfo.startyear = this.boardInfo.startyear;
      this.timeInfo.starthour = this.boardInfo.starthour;
      this.timeInfo.startminute = this.boardInfo.startminute;

      this.timeInfo.endyear = this.boardInfo.endyear;
      this.timeInfo.endhour = this.boardInfo.endhour;
      this.timeInfo.endminute = this.boardInfo.endminute;
    },

    // 저장
    goSave() {
      if(this.checkValidation()) {
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
          // this.boardInfo.poststtime = this.$refs.picker.getStartTimeToString2();
          // this.boardInfo.postedtime = this.$refs.picker.getEndTimeToString2();
  
          // 삭제된 파트너사 체크 리스트 저장
          let deleteNoList = []
          this.deleteList.forEach(obj => {
            deleteNoList.push(obj.no);
          })
  
          // 추가된 파트너사 체크 리스트 저장
          let addList = []
          this.addList.forEach(obj => {
            let temp = {
              postidx: this.boardInfo.idx,
              userno: obj.no,
              isagree: 'F'
            }
            addList.push(temp);
          })
  
          if (deleteNoList.length !== 0) {
            this.boardInfo.deleteNoList = deleteNoList;
          }
  
          if (addList.length !== 0) {
            this.boardInfo.addList = addList;
          }
  
          params = Object.assign({}, params, this.boardInfo);
  
          // 데이터 통신
          this.$http.post("/admin/partners/consent/update", params)
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
      }

    },

    goAgree(isagree) {
      let param = {
        postidx: this.boardInfo.idx,
        isagree : isagree,
      }
      
      let msg = '동의';
      if(isagree === 'F') {
        msg = '미 동의';
      }
      if(confirm(msg+' 하시겠습니까?')) {
        this.$http.post('/admin/partners/consent/agree', param)
        .then(result => {
          if(result.statusCode === 200) {
            if(isagree === 'T') {
              alert("동의가 완료되었습니다.");
            } else{
              alert("미 동의로 처리되었습니다.");
            }
            this.onClose(true);
          }
        })
        .catch(error => {
          this.$util.debug(error);
        });
      }
    },

     // validation 체크
    checkValidation() {
      this.setContent();

      let msg = '';
      let valid = [
          {field: 'boardInfo.subject', type: 'I', name: '제목', required: true},
          {field: 'boardInfo.content', type: 'I', name: '내용', required: true},
      ];

      msg = this.$util.validMsg(this.$data, valid);
      if(!this.$util.isNull(msg)){
          alert(msg);
          return false;
      }

      if(this.boardInfo.poststtime.length < 12) {
        alert("동의시작시간을 입력해주세요.");
        return false;
      }
      if(this.boardInfo.postedtime.length < 12) {
        alert("동의종료시간을 입력해주세요.");
        return false;
      }
      if(this.boardInfo.poststtime > this.boardInfo.postedtime) {
        alert("동의종료시간을 시작시간 이후로 입력해주세요.");
        return false;
      }

      if (this.boardInfo.isallagree === 'F' && this.partnersList.length === 0) {
        alert("파트너사를 선택해주세요.");
        return false;
      }

      return true;
    },

    setContent() {
      if(this.$util.isNull(this.$refs.editor.content)) {
        this.boardInfo.content = '';
      } else {
        this.boardInfo.content = this.$refs.editor.content;
      }
    },

    // 닫기
    onClose(isreload) {
      if (typeof(isreload) === 'boolean' && isreload) {
        this.$parent.goCloseDetailPopup(true);
      } else {
        this.$parent.goCloseDetailPopup();
      }
      this.boardInfo = [];
      this.partnersList = [];
      this.deleteList = [];
      this.addList = [];
      this.deleteFile = [];
      this.addUploadFile = [];
      this.$refs.editor.content = "";
    },

    // 파트너사 전체 버튼
    onClickAllBtn() {
      this.partnersList = [];
    },

    // 링크
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

    // 이미지 미리 보기
    imageView(url) {
      this.$viewerApi({
        images: [url]
      })
    },

    // 엑셀양식다운로드
    downloadExcelTemplate: function(filename) {
        let params = { filename: filename }   // 서버에 저장되어있는 파일명
        let config = { responseType: 'arraybuffer' };
        this.$http.post('/admin/common/excel/download', params, config);
    },

    /////////////////////////////////////////////////////////////////

    //////////////////////// 외부, 콜백 메서드 /////////////////////////
    // 파트너사 선택 팝업 콜백
    getPartnersList(val) {
      this.partnersList = val.check; // 체크 리스트
      this.deleteList = val.delete; // 삭제 리스트
      this.addList = val.add; // 추가 리스트
    },

    // 진행기간 DatePicker 콜백 메서드
    getTimeDate(date) {
      this.boardInfo.poststtime = date.fromDate12;
      this.boardInfo.postedtime = date.toDate12;
    },
    /////////////////////////////////////////////////////////////////

    ///////////////////////// 팝업 메서드 /////////////////////////////
    // 파트너 선택 팝업 열기
    goPartnersPopup() {
      this.isPartnersShow = true
      // this.$refs.partnersListPopup.checkListData = this.partnersList;
      // this.$refs.partnersListPopup.searchCheckList(this.boardInfo.idx); // 검색용 게시판 idx
    },

    // 파트너 선택 팝업 닫기
    goClosePartnersPopup() {
      this.isPartnersShow = false;
    },
    /////////////////////////////////////////////////////////////////

  },
  mounted() {
    // 권한 설정
    this.$http.post('/admin/common/pageAuth/check', {url: this.$options.name}).then(result => {
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
    }).catch(error => {
      this.$util.debug(error);
    })
  },
  computed: {
    // 화면에 보여지는 파트너사 수량 셋팅
    partnersListed() {
      if (this.partnersList.length === 0 || this.boardInfo.isallagree === 'T') {
        return "";
      }

      let msg = this.partnersList.length === 1 ? this.partnersList[0].name : this.partnersList[0].name + " 외 " + (this.partnersList.length - 1) + "명";

      return msg;
    }
  }

}
</script>
