<template>
  <!-- 이벤트발표 등록 팝업 -->
  <div id="modal-wrap" class="modal" style="display: block;">
    <div class="modal-content" style="width: 1400px;">
      <div class="pop-header">
        <h2>이벤트발표 등록</h2>
        <button type="button" class="pop-close" @click="$emit('close')"></button>
      </div>
      <div class="pop-body">
        <div class="bar-title">기본정보</div>
        <div class="boxing">
          <div class="form-area">
            <dl>
              <dt>사용여부<i class="essential"></i></dt>
              <dd>
                <div class="radio_wrap wide">
                  <input type="radio" name="group00" id="group01" value="F" v-model="boardInfo.istrash" checked/><label
                    for="group01">사용함</label>
                  <input type="radio" name="group00" id="group02" value="T" v-model="boardInfo.istrash"/><label
                    for="group02">사용안함</label>
                </div>
              </dd>
            </dl>
            <dl>
              <dt>제목<i class="essential"></i></dt>
              <dd><input type="text" style="width: 100%" placeholder="제목" v-model="boardInfo.subject"/></dd>
            </dl>
            <dl>
              <dt>설명</dt>
              <dd><input type="text" style="width: 100%" placeholder="설명" v-model="boardInfo.notice_desc"/></dd>
            </dl>
          </div>
        </div>
        <div class="bar-title">조건설정</div>
        <div class="form-area">
          <table cellpadding="0" cellspacing="0" class="gray-tb">
            <colgroup>
              <col width="170px">
              <col width="">
            </colgroup>
            <tbody>
            <tr>
              <th>예약여부<i class="essential"></i></th>
              <td>
                <div class="radio_wrap wide dpib">
                  <input type="radio" name="group01" id="group11" checked value="T"
                         v-model="boardInfo.isrightnow"/><label for="group11">즉시등록</label>
                  <input type="radio" name="group01" id="group12" value="F" v-model="boardInfo.isrightnow"/><label
                    for="group12">예약</label>
                </div>
                <div class="dpib" v-show="boardInfo.isrightnow === 'F'">
                  <CommonDatePickerFromTo
                      :useFrom="true"
                      :useTo="false"
                      text="분 부터"
                      :fromYYYYMMDD="fromTimeInfo.fromYYYYMMDD"
                      :fromHH="fromTimeInfo.fromHH"
                      :fromMM="fromTimeInfo.fromMM"
                      @getDate="getTimeDate"
                  />
                </div>
              </td>
            </tr>
            <tr>
              <th>종료 이벤트</th>
              <td>
                <select style="width: 40%;" v-model="boardInfo.eventidx">
                  <option v-for="(row, i) in endEventList" :value="row.eventidx" :key="i">{{ row.subject }}</option>
                </select>
              </td>
            </tr>
            <tr>
              <th>발표내용(PC)<i class="essential"></i></th>
              <td>
                <div>
                  <CommonEditor ref="pcEditor"/>
                </div>
              </td>
            </tr>
            <tr>
              <th>발표내용(모바일)<i class="essential"></i></th>
              <td>
                <div class="mb10">
                  <input type="checkbox" id="copy-text" @click="onCopyContent($event.target.checked)"><label
                    for="copy-text">PC 내용을 복사</label>
                </div>
                <div class="mt10">
                  <CommonEditor ref="mobileEditor"/>
                </div>
              </td>
            </tr>
            </tbody>
          </table>
        </div>
        <div class="bar-title">당첨자 관리</div>
        <div class="form-area">
          <table cellpadding="0" cellspacing="0" class="gray-tb">
            <colgroup>
              <col width="170px">
              <col width="">
            </colgroup>
            <tbody>
            <tr>
              <th>당첨자 선정 회원<i class="essential"></i></th>
              <td>
                <div class="caption-group clearfix dpb">
                  <div class="total-group fl">
                    <span class="total">이벤트 당첨자 회원목록</span>
                  </div>
                  <div class="btn-group fr">
                    <button type="button" class="btn black-line" @click="downloadExcelTemplate('CardBenefitTemplate.xlsx')">양식 다운로드</button>                               
                    <label for="input-file" class="btn green-line" style="margin-right: 2px">엑셀파일 올리기</label>
                    <input type="file" id="input-file" style="display: none" @change="onExcelRead($event)"
                           ref="excelFiles"
                           accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet">
                    <button type="button" class="btn red-line" @click="onDeleteUser">삭제</button>
                  </div>
                </div>
                <div class="scroll-y" style="width: 100%; max-height: 400px; margin-bottom: 0;">
                  <table cellpadding="0" cellspacing="0" class="data-tb align-c" style="margin-left: 0;">
                    <caption>특정회원목록</caption>
                    <colgroup>
                      <col width="5%"/><!-- checkbox -->
                      <col width="5%"/><!-- No -->
                      <col width="15%"/><!-- 아이디 -->
                      <col width="15%"/><!-- 이름 -->
                      <col width="15%"/><!-- 유형 -->
                      <col width="15%"/><!-- 등급 -->
                      <col width=""/><!-- 가입일 -->
                    </colgroup>
                    <thead>
                    <tr>
                      <th><input type="checkbox" id="chkall" @click="onEnterCheckAll($event.target.checked)"/></th>
                      <th>No</th>
                      <th>아이디</th>
                      <th>이름</th>
                      <th>유형
                        <button type="button" class="sort down"></button>
                      </th>
                      <th>등급
                        <button type="button" class="sort down"></button>
                      </th>
                      <th>가입일
                        <button type="button" class="sort down"></button>
                      </th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr v-for="(row, i) in enterList" :key="i">
                      <td><input type="checkbox" :id="'chk01_enter_0' + i" :value="row"
                                 v-model="enterCheckList"/></td>
                      <td>{{ $util.addZero(i + 1) }}</td>
                      <td>{{ row.userid }}</td>
                      <td>{{ row.username }}</td>
                      <td>{{ row.dadamembertypename }}</td>
                      <td>{{ row.memlvtypename }}</td>
                      <td>{{ row.regdate }}</td>
                    </tr>
                    </tbody>
                  </table>
                </div>
              </td>
            </tr>
            </tbody>
          </table>
        </div>
        <div class="btn-group">
          <button type="button" class="btn big blue" @click="goSave">저장</button>
          <button type="button" class="btn big darkgray" @click="$emit('close')">취소</button>
        </div>
      </div>
    </div>
  </div>
  <!-- /이벤트발표 등록 팝업 -->
</template>

<script>
import CommonDatePickerFromTo from "@views.admin/common/CommonDatePickerFromTo";
import CommonEditor from "@views.admin/common/CommonEditor";
import XLSX from "xlsx";

export default {
  name: "admin.promotion.event.announce.newpost",
  components: {CommonEditor, CommonDatePickerFromTo},
  data() {
    return {
      boardInfo: {
        userno: '',                   // 작성자 번호
        writer: '',                   // 작성자 이름
        istrash: 'F',                 // 사용여부
        subject: '',                  // 제목
        notice_desc: '',               // 설명
        isessnotice: 'F',             // N.N 필독공지여부
        ispapopnotice: 'F',           // N.N 입점사팝업공지여부
        ip: '',                       // ip
        hits: 0,                      // 조회수
        poststtime: '',               // 게시시작일
        isrightnow: 'T',              // 즉시등록여부
        eventidx: '',                 // 종료 이벤트 idx
        content: '',                  // 내용
        mobilecontent: '',            // 모바일 내용
        // [재호] 담청자 명단 (컬럼 추가되야함) -> t_dadaevnet_enter로 사용
      },
      fromTimeInfo: {                   // 예약 날짜
        fromYYYYMMDD: '',
        fromHH: '',
        fromMM: ''
      },
      endEventList: [],               // 종료 이벤트 리스트

      enterList: [],                  // 당첨자 리스트
      enterCheckList: [],             // 당첨자 체크 리스트
    }
  },
  methods: {
    ///////////////////////////////////내부 사용 메서드///////////////////////////////////////////
    onInit() {
      this.setTimeInfo();
      this.setEndEventList();
      this.setUserInfo();
    },

    // 저장
    goSave() {
      if (!this.checkValidation()) {
        return;
      }

      if (confirm("저장 하시겠습니까?")) {
        let params = this.boardInfo;
        this.enterList.forEach(obj => {
          obj.eventidx = this.boardInfo.eventidx;
          obj.isfail = 'F';
        });

        params.enterlist = this.enterList;

        this.$http.post("/admin/promotion/event/announce/save", params)
            .then(result => {
              if (result.statusCode === 200) {
                alert("저장이 완료되었습니다.");
                this.$emit('close');
              }
            })
            .catch(error => {
              this.$util.debug(error);
            });
      }
    },

    // 유효성 체크
    checkValidation() {
      this.setEditorText();

      let msg = '';
      let checkResult = true;
      let valid = [
        {field: 'boardInfo.subject', type: 'I', name: '제목', required: true},
        // {field: 'boardInfo.notice_desc', type: 'I', name: '설명', required: true},
        {field: 'boardInfo.content', type: 'I', name: '내용(PC)', required: true},
        {field: 'boardInfo.mobilecontent', type: 'I', name: '내용(모바일)', required: true},
      ]

      // (1) 기본 정보 검사
      msg = this.$util.validMsg(this.$data, valid);
      if (!this.$util.isNull(msg)) {
        checkResult = false;
        alert(msg);
        return;
      }

      return checkResult;
    },

    // 에디터 내용 셋팅
    setEditorText() {
      if (this.$util.isNull(this.$refs.pcEditor.content)) {
        this.boardInfo.content = ''
      } else {
        this.boardInfo.content = this.$refs.pcEditor.content;
      }

      if (this.$util.isNull(this.$refs.mobileEditor.content)) {
        this.boardInfo.mobilecontent = ''
      } else {
        this.boardInfo.mobilecontent = this.$refs.mobileEditor.content;
      }
    },

    // 검색 시간 데이터 셋팅
    setTimeInfo() {
      this.fromTimeInfo.fromYYYYMMDD = this.$util.getDate('-');
      this.fromTimeInfo.fromHH = '00';
      this.fromTimeInfo.fromMM = '59';
    },

    // 종료 이벤트 조회
    setEndEventList() {
      this.$http.post("/admin/promotion/event/announce/search/event", {})
          .then(result => {
            if (result.statusCode === 200) {
              this.endEventList = result.data.list;
              this.boardInfo.eventidx = this.endEventList[0].eventidx;
            }
          })
          .catch(error => {
            this.$util.debug(error);
          });
    },

    // 유저 정보
    setUserInfo() {
      let userInfo = this.$util.getUser(this.$store.getters['CONSTANTS'].MANAGER_SESSION);
      this.boardInfo.userno = userInfo.no;
      this.boardInfo.writer = userInfo.name;
    },

    // 엑셀파일 읽기
    onExcelRead(event) {
      if (event.target.files.length === 0) {
        alert("파일이 없습니다.");
        return;
      }

      const file = event.target.files[0];
      let reader = new FileReader();
      let tmpResult = {};
      reader.onload = () => {
        let data = reader.result;
        let workbook = XLSX.read(data, {type: 'binary'});
        workbook.SheetNames.forEach(sheetName => {
          const roa = XLSX.utils.sheet_to_json(workbook.Sheets[sheetName]);
          tmpResult = roa;
        });
        // 엑셀 처리 부분
        this.onExcelDataUpload(tmpResult);
      };
      reader.readAsArrayBuffer(file);

      // value 초기화
      this.$refs.excelFiles.value = null;
    },

    // 엑셀일괄 업로드
    onExcelDataUpload(excelJsonData) {
      let excelObj = [];

      // 받아온 excel Data 파싱해서 원하는 데이터로 변경
      excelJsonData.forEach(obj => {
        if (Object.prototype.hasOwnProperty.call(obj, 'userid')) {
          excelObj.push(obj.userid);
        }

        if (Object.prototype.hasOwnProperty.call(obj, '아이디')) {
          excelObj.push(obj.아이디);
        }
      })

      // 중복 체크후 데이터를 넣을 객체
      let excelResult = [];

      excelObj.forEach(obj => {
        // 중복 제거
        let isExist = this.enterList.find(element => element.userid === obj.userid);
        if (typeof isExist === 'undefined') {
          excelResult.push(obj);
        }
      });

      // 유저 정보 조회
      if (excelResult.length !== 0) {
        // let userIdList = excelResult.map(obj => obj.userid);
        let params = {
          useridlist: excelResult
        }

        this.$http.post("/admin/promotion/promotion/epoint/search/member", params)
            .then(result => {
              if (result.statusCode === 200) {
                let data = result.data;
                this.enterCheckList = [];
                if (this.enterList.length === 0) {
                  Object.assign(this.enterList, data.list);
                  this.enterList.splice();
                } else {
                  data.list.forEach(obj => {
                    this.enterList.push(obj);
                  })
                }
              }
            })
            .catch(error => {
              this.$util.debug(error);
            });
      }
    },

    // 당첨자 전체 체크
    onEnterCheckAll(checked) {
      this.enterCheckList = [];
      if (checked) {
        this.enterList.forEach(obj => {
          this.enterCheckList.push(obj);
        });
      }
    },

    // 이벤트 당첨자 삭제
    onDeleteUser() {
      if (this.enterCheckList.length === 0) {
        alert("선택된 값이 없습니다.");
        return;
      }

      let removedEnterList = [];
      this.enterList.forEach(obj => {
        // 중복 제거
        let isExist = this.enterCheckList.find(element => element.userid === obj.userid);
        if (typeof isExist === 'undefined') {
          removedEnterList.push(obj);
        }
      })

      this.enterList = removedEnterList;
    },

    // PC 내용 복사
    onCopyContent(value) {
      if (value) {
        this.$refs.mobileEditor.content = this.$refs.pcEditor.content;
      } else {
        this.$refs.mobileEditor.content = '';
      }
    },

    // 엑셀양식다운로드
    downloadExcelTemplate: function(filename) {
        let params = { filename: filename }   // 서버에 저장되어있는 파일명
        let config = { responseType: 'arraybuffer' };
        this.$http.post('/admin/common/excel/download', params, config);
    },

    ///////////////////////////////////////////////////////////////////////////////////////////

    ///////////////////////////////////외부, 콜백 메서드//////////////////////////////////////////
    // 시간 콜백 메서드
    getTimeDate(date) {
      this.boardInfo.poststtime = date.fromDate12;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////팝업 메서드//////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////
  },
  mounted() {
    this.onInit();
  },
}
</script>
