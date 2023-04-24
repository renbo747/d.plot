<template>
  <!-- 이벤트발표 상세 팝업 -->
  <div id="modal-wrap" class="modal" style="display: block;">
    <div class="modal-content" style="width: 1400px;">
      <div class="pop-header">
        <h2>당첨자발표 상세</h2>
        <button type="button" class="pop-close" @click="$emit('close')"></button>
      </div>
      <div class="pop-body">
        <div class="gray-box mg0 clearfix">
          <div class="fl">
            <span>작성자 : {{ boardInfo.writer }}</span>
            <span class="left-bar">조회수 : {{ boardInfo.hits }}</span>
          </div>
          <div class="fr txt-gray">
            <span>등록일 : {{ boardInfo.regdate }}</span>
            <span class="left-bar">수정일 : {{ boardInfo.moddate }}</span>
          </div>
        </div>
        <div class="bar-title mt10">기본정보</div>
        <div class="boxing">
          <div class="form-area">
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
                      :fromYYYYMMDD="boardInfo.fromyyyymmdd"
                      :fromHH="boardInfo.fromhh"
                      :fromMM="boardInfo.frommm"
                      @getDate="getTimeDate"
                  />
                </div>
              </td>
            </tr>
            <tr>
              <th>종료 이벤트<i class="essential"></i></th>
              <td>
                <select style="width: 40%;" v-model="boardInfo.eventidx" @change="resetEnter">
                  <option v-for="(row, i) in eventList" :value="row.eventidx" :key="i">{{ row.subject }}</option>
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
                  <button type="button" class="btn blue-line" @click="onCopyContent">PC 내용을 복사</button>
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
                    <button type="button" class="btn black-line" @click="downloadExcelTemplate('MemberTemplate.xlsx')">양식 다운로드</button>
                    <label for="input-file" class="btn green-line" style="margin-right: 2px">엑셀파일 올리기</label>
                    <input type="file" id="input-file" style="display: none" @change="onExcelRead($event)"
                           ref="excelFiles"
                           accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet">
                    <button type="button" class="btn red-line" @click="goUserDelete()" v-if="isWrite">삭제</button>
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
                      <th><input type="checkbox" id="chkall" v-model="isCheckAllEnter"
                                 @click="onEnterCheckAll($event.target.checked)"/></th>
                      <th>No</th>
                      <th>아이디</th>
                      <th>이름</th>
                      <th>유형
                        <button type="button"
                                :value="sortData.dadamembertype"
                                class="sort"
                                :class="[{up : sortData.dadamembertype === 'dadamembertype_asc'}, {down : sortData.dadamembertype === 'dadamembertype_desc'}]"
                                @click="sortToggle(sortData.dadamembertype)"></button>
                      </th>
                      <th>등급
                        <button type="button"
                                :value="sortData.memlvtype"
                                class="sort"
                                :class="[{up : sortData.memlvtype === 'memlvtype_asc'}, {down : sortData.memlvtype === 'memlvtype_desc'}]"
                                @click="sortToggle(sortData.memlvtype)"></button>
                      </th>
                      <th>가입일
                        <button type="button"
                                :value="sortData.regdate"
                                class="sort"
                                :class="[{up : sortData.regdate === 'regdate_asc'}, {down : sortData.regdate === 'regdate_desc'}]"
                                @click="sortToggle(sortData.regdate)"></button>
                      </th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr v-for="(row, i) in enterList" :key="i">
                      <td><input type="checkbox" :id="'chk01_enter_0' + i" :value="i"
                                 v-model="enterCheckList" @change="changeEnterList"/></td>
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
          <button type="button" class="btn big blue" @click="onSave" v-if="isWrite">저장</button>
          <button type="button" class="btn big darkgray" @click="$emit('close')">취소</button>
        </div>
      </div>
    </div>
  </div>
  <!-- /이벤트발표 상세 팝업 -->
</template>

<script>
import CommonDatePickerFromTo from "@views.admin/common/CommonDatePickerFromTo";
import CommonEditor from "@views.admin/common/CommonEditor";
import XLSX from "xlsx";

export default {
  name: "admin.operation.shopping.promotion.announce.detail",
  components: {CommonEditor, CommonDatePickerFromTo},
  props: {
    idx: Number,
  },
  data() {
    return {
      isRead: false,
      isWrite: false,
      boardInfo: {
        content: '',        // pc 내용
        eventidx: '',       // 선택한 eventidx
        hits: '',           // 조회수
        isrightnow: '',     // 예약여부
        mobilecontent: '',  // 모바일 내용
        notice_desc: '',    // 설명
        poststtime: '',     // 게시일시
        regdate: '',        // 등록일시
        subject: '',        // 제목
        writer: '',         // 작성자
        fromyyyymmdd: '',   // 예약 날자
        fromhh: '',         // 예약 시간
        frommm: '',          // 예약 분
        moddate: '',        // 수정일자
      },
      sortData: {
        dadamembertype: 'dadamembertype_desc',  // 유형
        memlvtype: 'memlvtype_desc',            // 등급
        regdate: 'regdate_desc',                // 등록일자
      },
      oldeventidx: '',
      eventList: [],        // 종료 이벤트 리스트

      isCheckAllEnter: false,   // 당첨자 전체 선택
      enterList: [],        // 당첨자 리스트
      enterCheckList: [],   // 당첨자 체크 리스트
      removeEnterList: [],  // 당첨자 삭제 처리 리스트
      dbEnterList: [],      // DB 당첨자 리스트
    }
  },
  methods: {
    ///////////////////////////////////내부 사용 메서드///////////////////////////////////////////
    // 초기화
    onInit() {
      this.onSearch();
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
        {field: 'boardInfo.eventidx', type: 'S', name: '종료 프로모션', required: true},
      ]

      // (1) 기본 정보 검사
      msg = this.$util.validMsg(this.$data, valid);
      if (!this.$util.isNull(msg)) {
        checkResult = false;
        alert(msg);
        return;
      }

      if(this.enterList.length === 0) {
        alert("당첨자 선정 회원을 입력해주세요.");
        return fasle;
      }

      let endEvent = this.eventList.find(obj => obj.eventidx === this.boardInfo.eventidx);

      if(this.boardInfo.isrightnow === 'F' && endEvent.evedtime > this.boardInfo.poststtime) {
        alert("종료된 프로모션의 종료일자 이후로 예약일자를 설정해주세요.");
        return false;
      }

      return checkResult;
    },

    // 테이블 소트
    sortToggle(key) {
      let arr = key.split("_");
      let sortKey = arr[0];
      let sortOrder = (arr[1] === 'asc') ? 'desc' : 'asc';
      let sortName = sortKey + '_' + sortOrder;

      this.sortData = this.$options.data().sortData;

      this.sortData[sortKey] = sortName;

      this.isCheckAllEnter = false;
      this.enterCheckList = [];

      this.enterList.sort((a,b) =>{
        if(sortOrder === 'asc') {
          if(a[sortKey] > b[sortKey]) {
            return 1;
          } else {
            return -1;
          } 
        } else {
          if(a[sortKey] > b[sortKey]) {
            return -1;
          } else { 
            return 1;
          }
        }
      })
    },

    // 댓글 재조회
    // onSearchEnter() {
    //   let params = {
    //     eventidx: this.boardInfo.eventidx,
    //     sort: this.sortData.sort,
    //     isloading: false,
    //   }

    //   this.$http.post("/admin/operation/shopping/promotion/announce/search/enter", params)
    //       .then(result => {
    //         if (result.statusCode === 200) {
    //           this.enterList = result.data.list;
    //         }
    //       })
    //       .catch(error => {
    //         this.$util.debug(error);
    //       });
    // },

    // 저장
    onSave() {
      if (!this.checkValidation()) {
        return;
      }
      if (confirm("저장 하시겠습니까?")) {
        let params = this.boardInfo;

        // 현재는 응모가 없이 당첨만 관리하기 때문에 insert update로 추가, 논리삭제만 관리하지만
        // 추후 응모가 생길 시 따로 테이블에서 당첨여부 관리가 필요함
        if(params.eventidx !== this.oldeventidx) {
          // 기존 프로모션과 수정 후 프로모션이 다른 경우
          params.oldeventidx = this.oldeventidx;
          params.insertlist = this.enterList;
        } else {
          let insertlist = this.enterList.filter(m => !Object.prototype.hasOwnProperty.call(m,'idx'));
          if(insertlist.length > 0){
            params.insertlist = insertlist;
          }
        }

        if(this.removeEnterList.length > 0) {
          params.removelist = this.removeEnterList;
        }

        params.idx = this.idx;

        this.$http.post("/admin/promotion/event/announce/update", params)
          .then(result => {
            if (result.statusCode === 200) {
              alert("저장이 완료되었습니다.");
              this.$emit('close',true);
            }
          })
          .catch(error => {
            this.$util.debug(error);
          });
      }
    },

    // 이벤트 상세 정보 검색
    onSearch() {
      let params = {
        idx: this.idx
      }
      this.$http.post("/admin/operation/shopping/promotion/announce/detail", params)
          .then(result => {
            if (result.statusCode === 200) {
              let data = result.data;
              this.eventList = data.eventlist;
              this.boardInfo = data.boardinfo;
              this.enterList = data.enterlist;
              this.oldeventidx = data.boardinfo.eventidx;
              this.dbEnterList = JSON.parse(JSON.stringify(data.enterlist));

              this.setBoardInfo();
            }
          })
          .catch(error => {
            this.$util.debug(error);
          });
    },

    // 이벤트 내역 셋팅
    setBoardInfo() {
      // 내용
      this.$refs.pcEditor.content = this.boardInfo.content;
      this.$refs.mobileEditor.content = this.boardInfo.mobilecontent;
    },

    // 날짜 데이터 현재 시간으로 초기화
    setTimeInfo() {
      this.boardInfo.fromyyyymmdd = this.$util.getDate('-');
      this.boardInfo.fromhh = '00';
      this.boardInfo.frommm = '59';
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

    // PC 내용 복사
    onCopyContent() {
      this.$refs.mobileEditor.content = this.$refs.pcEditor.content;
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
        // this.$http.post("/admin/promotion/event/announce/enter/user", params)
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
        this.enterList.forEach((obj,index) => {
          this.enterCheckList.push(index);
        });
      }
    },

    changeEnterList() {
      if(this.enterCheckList.length < this.enterList.length) {
        this.isCheckAllEnter = false;
      } else {
        this.isCheckAllEnter = true;
      }
    },

    resetEnter() {
      this.enterList.forEach(m => {
        if(Object.prototype.hasOwnProperty.call(m,'idx')) {
          this.removeEnterList.push(m.idx);
        }
      })

      this.isCheckAllEnter = false;
      this.enterList = [];
      this.enterCheckList = [];
    },

    // 당첨자 삭제
    goUserDelete() {
      // alert("유저 삭제");
      if (this.enterCheckList.length === 0) {
        alert("선택된 값이 없습니다.");
        return;
      }

      this.enterCheckList.sort((a,b) => b-a);

      this.enterCheckList.forEach(index => {
        if(Object.prototype.hasOwnProperty.call(this.enterList[index],'idx')) {
          this.removeEnterList.push(this.enterList[index].idx);
        }
          this.enterList.splice(index,1);
      });

      this.enterCheckList = [];
      this.isCheckAllEnter = false;
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
    // 권한 설정
    this.$http.post('/admin/common/pageAuth/check', {url: this.$options.name}).then(result => {
      this.isRead = (result.data.isread === 'T');
      this.isWrite = (result.data.iswrite === 'T');
      if (this.isRead) {
        this.onInit();
      } else {
        alert("페이지 접근 권한이 없습니다.");
        this.$emit('close');
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
}
</script>
