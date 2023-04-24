<template>
  <div>
    <!--  파트너사 선택 팝업  -->
    <PartnersListPopup
        ref="partnersListPopup"
        v-show="isPartnersShow"
        :checkPartenrs="partnersList"
        @getPartnersList="getPartnersList"
        @closePartnersPopup="goClosePartnersPopup"/>

    <!-- 파트너사 동의공지 등록 팝업 -->
    <div id="modal-wrap" class="modal" style="display: block;">
      <div class="modal-content" style="width: 1200px;">
        <div class="pop-header">
          <h2>파트너사 동의공지 등록</h2>
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
                <dt>파트너사</dt>
                <dd>
                  <div class="radio_wrap wide">
                    <input type="radio" name="partners" id="rds11" value="T" v-model="boardInfo.isallagree"
                           @click="onClickAllBtn()"/><label for="rds11">전체</label>
                    <input type="radio" name="partners" id="rds122" value="F" v-model="boardInfo.isallagree"
                           @click="goPartnersPopup()"/><label for="rds122">선택</label>
                  </div>
                  <input type="text" style="width: 500px;" v-model="partnersListed" disabled>
                  <button type="button" class="btn black-line" style="margin-left : 5px;" @click="downloadExcelTemplate('MemberTemplate.xlsx')">양식 다운로드</button>
                </dd>
              </dl>
              <dl>
                <dt>상태</dt>
                <dd>
                  <div class="radio_wrap wide4">
                    <div v-for="(row, i) in poststList" :key="i">
                      <input type="radio" :id="'rds' + i" :value="row.cmcode" v-model="boardInfo.postst">
                      <label :for="'rds' + i">{{ row.codename }}</label>
                    </div>
                  </div>
                </dd>
              </dl>
              <dl>
                <dt>동의 시작/종료일시</dt>
                <dd>
                  <CommonDatePickerFromTo
                      :fromYYYYMMDD="timeInfo.startYYYYMMDD"
                      :fromHH="timeInfo.startHH"
                      :fromMM="timeInfo.startMM"
                      :toYYYYMMDD="timeInfo.toYYYYMMDD"
                      :toHH="timeInfo.toHH"
                      :toMM="timeInfo.toMM"
                      @getDate="getEventTimeDate"
                  />
                </dd>
              </dl>
              <dl>
                <dt>팝업공지</dt>
                <dd><input type="checkbox" id="chk02" v-model="boardInfo.ispapopnotice"><label for="chk02">파트너사 메인화면 팝업
                  공지로 등록</label></dd>
              </dl>
              <dl>
                <dt>제목</dt>
                <dd><input type="text" style="width: 100%;" v-model="boardInfo.subject" ref="subjectObj"></dd>
              </dl>
            </div>
          </div>
          <div class="boxing" style="border: 0">
            <CommonEditor ref="editor" :style-object="{height : 300+'px'}"/>
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
    <!-- /파트너사 동의공지 등록 팝업-->
  </div>
</template>

<script>
import PartnersListPopup from "@views.admin/partners/popup/PartnersListPopup";
import CommonEditor from "@views.admin/common/CommonEditor";
import CommonDatePickerFromTo from "@views.admin/common/CommonDatePickerFromTo";

export default {
  name: "admin.partners.consent.newpost",
  components: {
    CommonDatePickerFromTo,
    CommonEditor,
    PartnersListPopup,
  },
  data() {
    return {
      boardInfo: {
        userno: "",                                                             // 작성자 번호
        writer: "",                                                             // 작성자 이름
        ip: "",                                                          // ip
        hits: "0",                                                              // 조회수
        istrash: "F",                                                           // 사용 여부
        regdate: this.$util.getFormatDate(this.$util.getDate(), '-'),   // 등록일자
        isessnotice: "F",                                                       // 필독 공지 여부
        ispapopnotice: false,                                                   // 입점사 팝업 공지 여부
        subject: "",                                                            // 제목
        content: "",                                                            // 내용
        linkurl1: "",                                                           // 링크1
        linkurl2: "",                                                           // 링크2
        poststtime: "",                                                         // 게시 시작일
        postedtime: "",                                                         // 게시 종료일
        postst: "POS001",                                                       // 게시 상태
        isallagree: 'T',                                                        // 전체 동의 여부
      },
      timeInfo: {              // 진행기간
        startYYYYMMDD: '',
        startHH: '',
        startMM: '',
        toYYYYMMDD: '',
        toHH: '',
        toMM: ''
      },
      partnersList: [],                                                         // 파트너사 리스트
      uploadFile: [],                                                           // 첨부파일 데이터
      isPartnersShow: false,                                                    // 팝업
    }
  },
  props: {
    poststList: Array
  },
  computed: {
    // 화면에 보여지는 파트너사 수량을 위함
    partnersListed() {
      if (this.partnersList.length === 0) {
        return "";
      }

      if (this.partnersList.length === 1) {
        return this.partnersList[0].name;
      }

      let msg = this.partnersList[0].name + " 외 " + (this.partnersList.length - 1) + "명";

      return msg;
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
      this.setTimeInfo();
      this.setUserInfo();
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

    // 유저 정보 셋팅
    setUserInfo() {
      let userInfo = this.$util.getUser(this.$store.getters['CONSTANTS'].MANAGER_SESSION);
      this.boardInfo.userno = userInfo.no;
      this.boardInfo.writer = userInfo.name;
    },

    // 검색 시간 데이터 셋팅
    setTimeInfo() {
      // 날짜 셋팅
      this.timeInfo.startYYYYMMDD = this.$util.getDate('-');
      this.timeInfo.startHH = '00';
      this.timeInfo.startMM = '00';

      this.timeInfo.toYYYYMMDD = this.$util.getDate('-');
      this.timeInfo.toHH = '00';
      this.timeInfo.toMM = '59';
    },

    // 첨부 파일 삭제
    deleteUploadFile(index) {
      this.uploadFile.splice(index, 1);
    },

    // 파트너사 선택 팝업 콜백
    getPartnersList(val) {
      this.partnersList = val.check; // 체크 리스트
      this.addList = val.add; // 추가 리스트
    },

    // 파트너 선택 팝업 닫기
    goClosePartnersPopup() {
      this.isPartnersShow = false;
    },

    // 파트너 선택 팝업 열기
    goPartnersPopup() {
      this.isPartnersShow = true
      // this.$refs.partnersListPopup.checkListData = this.partnersList;
    },

    // 저장
    goSave() {
      if(this.checkValidation()) {
        if (confirm("저장 하시겠습니까?")) {
          // 삭제, 추가된 파일
          let params = {};
          let files = [];
          
          this.uploadFile.forEach((file,index) => {
            files.push({key: 'file'+index , file: file.file});
          })

          params.files = files;
  
          // 넘길 객체
          let partnerNoList = "";
  
          // 파트너사 셀렉 정보가 있다면
          if (this.partnersList.length !== 0) {
            this.partnersList.forEach(obj => {
              partnerNoList += obj.no + ",";
            })
          }
  
          // , 을 기준으로 유저 번호를 묶어서 보냄
          this.boardInfo.partnerNoList = partnerNoList;
  
          params = Object.assign({}, params, this.boardInfo);
  
          this.$http.post('/admin/partners/consent/save', params)
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

    // 진행기간 DatePicker 콜백 메서드
    getEventTimeDate(date) {
      this.boardInfo.poststtime = date.fromDate12;
      this.boardInfo.postedtime = date.toDate12;
    },

    // 파트너사 전체 버튼
    onClickAllBtn() {
      this.partnersList = [];
      this.$refs.partnersListPopup.listCheckData = [];
    },

    // 엑셀양식다운로드
    downloadExcelTemplate: function(filename) {
        let params = { filename: filename }   // 서버에 저장되어있는 파일명
        let config = { responseType: 'arraybuffer' };
        this.$http.post('/admin/common/excel/download', params, config);
    },

    // 닫기
    onClose(isreload) {
      if (typeof(isreload) === 'boolean' && isreload) {
        this.$parent.goCloseNewPostPopup(true);
      } else {
        this.$parent.goCloseNewPostPopup();
      }
      this.boardInfo = [];
      this.partnersList = [];
      this.$refs.editor.content = "";
    }
    /////////////////////////////////////////////////////////////////

  }

}
</script>
