`
<template>
  <!-- e포인트 수동지급 팝업 -->
  <div id="modal-wrap" class="modal" style="display: block;">
    <div class="modal-content" style="width: 1400px;">
      <div class="pop-header">
        <h2>D포인트 수동지급</h2>
        <button type="button" class="pop-close" @click="$emit('close')"></button>
      </div>
      <div class="pop-body">
        <div class="bar-title">기본정보</div>
        <div class="boxing">
          <div class="form-area">
            <dl>
              <dt>관리제목<i class="essential"></i></dt>
              <dd><input type="text" style="width: 100%" placeholder="관리제목" v-model="boardInfo.eponame"/></dd>
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
              <th>D포인트 중복사용여부<i class="essential"></i></th>
              <td>
                <div class="radio_wrap wide dpib">
                  <input type="radio" name="group00" id="group01" value="T" v-model="boardInfo.isepointdup"/><label
                    for="group01">허용</label>
                  <input type="radio" name="group00" id="group02" value="F" v-model="boardInfo.isepointdup"/><label
                    for="group02">미허용</label>
                </div>
                <span class="txt-orange ml10"><i class="icon-alert"></i>적립금과 함께 사용 가능한지의 여부</span>
              </td>
            </tr>
            <tr>
              <th>지급사유<i class="essential"></i></th>
              <td>
                <input type="text" class="dpib" style="width: 100%;"
                       placeholder="이벤트 제목, C/S 관련 문구 등을 작성 (사용자에게 노출되는 적립사유)" v-model="boardInfo.eporeason"/>
              </td>
            </tr>
            <tr>
              <th>지급일<i class="essential"></i></th>
              <td>
                <div class="radio_wrap wide dpib">
                  <input type="radio" name="group01" id="group11" value="T" v-model="boardInfo.isnowpay"/><label
                    for="group11">즉시지급</label>
                  <input type="radio" name="group01" id="group12" value="F" v-model="boardInfo.isnowpay"/><label
                    for="group12">일자설정</label>
                </div>
                <div class="dpib" v-show="boardInfo.isnowpay === 'F'">
                  <CommonDatePickerFromTo
                      :toYYYYMMDD="epointTimeInfo.toYYYYMMDD"
                      :toHH="epointTimeInfo.toHH"
                      :toMM="epointTimeInfo.toMM"
                      @getDate="getEventTimeDate"
                      :use-to="true"
                      :use-from="false"
                  />
                </div>
              </td>
            </tr>
            <tr>
              <th>D포인트 유효기간<i class="essential"></i></th>
              <td>
                <div class="dpb">
                  <input type="radio" name="group02" class="circle" id="group21" value="T" v-model="isDay"/><label
                    for="group21">지급
                  후</label>
                  <input type="text" class="right ml3" style="width: 80px;" v-model="boardInfo.epousedaydnt"/>
                  <span class="ml3">일간 사용 가능(최대 30일)</span>
                </div>
                <div class="dpb">
                  <input type="radio" name="group02" class="circle" value="F" v-model="isDay" id="group22"/><label
                    for="group22">지급 후</label>
                  <CommonDatePicker :value="boardInfo.epovaliddayformat" @change="onChangeValidDay"/>
                  <span class="ml3">23:59까지 사용 가능</span>
                </div>
              </td>
            </tr>
            <tr>
              <th>대상범위<i class="essential"></i></th>
              <td>
                <div class="radio_wrap">
                  <input type="radio" name="group03" id="group31" value="T" v-model="boardInfo.ismemtype"/><label
                    for="group31">특정 유형/등급 대상</label>
                  <input type="radio" name="group03" id="group32" value="F" v-model="boardInfo.ismemtype"/><label
                    for="group32">특정 회원 대상</label>
                </div>
              </td>
            </tr>

            <!-- 특정 유형/등급 대상 지정 -->
            <tr v-if="boardInfo.ismemtype === 'T'">
              <th>대상회원유형<i class="essential"></i></th>
              <td>
                <div class="check-wrap">
                  <input type="checkbox" id="all2" v-model="checkObj.isallmumember" true-value="T" false-value="F"
                         @change="checkAllMuMemer($event.target.checked)">
                  <label for="all2">전체</label>
                </div>
                <div class="check-wrap" v-for="(row, i) in pageCode.mumembertype" :key="i">
                  <input type="checkbox" :id="'group24' + i" :value="row.cmcode" v-model="checkObj.mumemberTypeChecked">
                  <label :for="'group24' + i">{{ row.codename }}</label>
                </div>
              </td>
            </tr>
            <tr v-if="boardInfo.ismemtype === 'T'">
              <th>대상회원등급<i class="essential"></i></th>
              <td>
                <div class="check-wrap">
                  <input type="checkbox" id="all3" v-model="checkObj.isallmumemlv" true-value="T" false-value="F"
                         @change="checkAllMuMemLv($event.target.checked)">
                  <label for="all3">전체</label>
                </div>
                <div class="check-wrap" v-for="(row, i) in pageCode.mumemlvtype" :key="i">
                  <input type="checkbox" :id="'group35' + i" :value="row.cmcode" v-model="checkObj.mumemlvTypeChecked">
                  <label :for="'group35' + i">{{ row.codename }}</label>
                </div>
              </td>
            </tr>
            <!-- /특정 유형/등급 대상 지정 -->

            <!-- 특정 회원 대상 지정 -->
            <tr v-if="boardInfo.ismemtype === 'F'">
              <th>대상회원<i class="essential"></i></th>
              <td>
                <div class="caption-group clearfix dpb">
                  <div class="total-group fl">
                    <span class="total">적용대상 회원목록</span>
                  </div>
                  <div class="btn-group fr">
                    <button type="button" class="btn black-line" @click="downloadExcelTemplate('MemberTemplate.xlsx')">양식 다운로드</button>                               
                    <label for="input-file" class="btn green-line" style="margin-right: 2px">엑셀파일 올리기</label>
                    <input type="file" id="input-file" style="display: none" @change="onExcelRead($event)"
                           ref="excelFiles"
                           accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet">
                    <button type="button" class="btn blue-line" @click="openAddUserPopup">회원추가</button>
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
                      <th><input type="checkbox" id="chkall" @click="onUserCheckAll($event.target.checked)"/></th>
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
                    <tr v-for="(row, i) in memberList" :key="i">
                      <td><input type="checkbox" :id="'chk01_enter_0' + i" :value="row"
                                 v-model="memberCheckList"/></td>
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
            <!-- 특정 회원 대상 지정 -->

            <tr>
              <th>지급포인트<i class="essential"></i></th>
              <td>
                <input type="number" class="right" style="width: 80px;" v-model="boardInfo.payepoint"/><span
                  class="ml3">포인트</span>
              </td>
            </tr>
            <tr>
              <th>사용제한<i class="essential"></i></th>
              <td>
                <input type="number" class="right" style="width: 80px;" v-model="boardInfo.uselimitordamt"/><span
                  class="ml3">원 이상 결제 시 1회 주문 당 최대</span>
                <input type="number" class="right" style="width: 80px;" v-model="boardInfo.uselimitpoint"/><span
                  class="ml3">포인트까지 사용 가능(실결제금액 기준)</span>
              </td>
            </tr>
            </tbody>
          </table>
        </div>
        <div class="btn-group">
          <button type="button" class="btn big blue" @click="goSave" v-if="isWrite">저장</button>
          <button type="button" class="btn big darkgray" @click="$emit('close')">취소</button>
        </div>
      </div>
    </div>
  </div>
  <!-- /e포인트 수동지급 팝업 -->
</template>

<script>
import CommonDatePickerFromTo from "@views.admin/common/CommonDatePickerFromTo";
import CommonDatePicker from "@views.admin/common/CommonDatePicker";
import XLSX from "xlsx";
import CommonAddUserPopup from "@views.admin/common/popup/CommonAddUserPopup";

export default {
  name: "admin.promotion.promotion.epoint.newpost",
  components: {CommonDatePicker, CommonDatePickerFromTo},
  data() {
    return {
      isRead: false,
      isWrite: false,

      isDay: 'T',                   // e포인트 유효기간 종류 (일수 : true)

      checkObj: {
        isallmumember: 'T',
        mumemberTypeChecked: [],         // 회원 유형
        isallmumemlv: 'T',
        mumemlvTypeChecked: [],          // 회원 등급
      },

      boardInfo: {
        eponame: '',              // E포인트관리제목
        isepointdup: 'T',        // E포인트중복사용여부
        eporeason: '',          // E포인트지급사유
        isnowpay: 'T',           // 즉시지급여부
        epopayday: '',          // E포인트지급일
        epousedaydnt: '',       // E포인트사용가능일수
        epovalidday: '',        // E포인트유효일자
        epovaliddayformat: '',  // e포인트 유효일자 포맷팅
        ismemtype: 'T',        // 특정유형등급대상여부
        mumembertype: '',     // 다중대상회원유형
        mumemlvtype: '',      // 다중대상회원등급
        payepoint: '',        // 지급포인트
        uselimitordamt: '',   //사용제한주문금액
        uselimitpoint: '',    // 사용제한포인트
        reguserid: '',        // 등록자ID
        epostday: '',         // E포인트유효시작일
        epoedday: '',         // E포인트유효종료일
      },

      epointTimeInfo: {              // 진행기간
        toYYYYMMDD: '',
        toHH: '',
        toMM: ''
      },

      sortData: {
        dadamembertype: 'dadamembertype_desc',      // 유형
        memlvtype: 'memlvtype_desc',            // 등급
        regdate: 'regdate_desc',                // 등록일자
      },

      memberList: [],         // 유저 리스트
      memberCheckList: [],    // 유저 체크 리스트
    }
  },
  props: {
    pageCode: {                 // 페이지 코드
      mumembertype: {}, // 대상 회원
      mumemlvtype: {}, // 대상 회원 등급
    },
  },
  methods: {
    ///////////////////////////////////내부 사용 메서드///////////////////////////////////////////
    onInit() {
      this.setCode();
      this.setTimeInfo();
      this.setUserInfo();
    },

    // 코드 체크값 셋팅
    setCode() {
      // 회원 유형
      this.checkObj.mumemberTypeChecked = this.getCmCode(this.pageCode.mumembertype);
      // 회원 등급
      this.checkObj.mumemlvTypeChecked = this.getCmCode(this.pageCode.mumemlvtype);
    },

    // 시간 셋팅
    setTimeInfo() {
      // 지급일 설정
      this.boardInfo.epovaliddayformat = this.$util.getDate('-');
      this.boardInfo.epovalidday = this.$util.getDate('') + '2359';

      // e포인트 유효기간
      this.epointTimeInfo.toYYYYMMDD = this.$util.getDate('-');
      this.epointTimeInfo.toHH = '00';
      this.epointTimeInfo.toMM = '00';
    },

    // 접속자 정보 가져오기
    setUserInfo() {
      this.boardInfo.reguserid = this.$util.getUser('ADMIN_USER').id;
    },

    // codename array get
    getCmCode(codeArr) {
      return codeArr.map(obj => obj.cmcode);
    },

    // 회원 유형 전체 체크
    checkAllMuMemer(value) {
      if (value) {
        // 적용 채널
        this.checkObj.mumemberTypeChecked = this.getCmCode(this.pageCode.mumembertype);
      } else {
        this.checkObj.mumemberTypeChecked = [];
      }
    },

    // 회원 등급 전체 체크
    checkAllMuMemLv(value) {
      if (value) {
        // 적용 채널
        this.checkObj.mumemlvTypeChecked = this.getCmCode(this.pageCode.mumemlvtype);
      } else {
        this.checkObj.mumemlvTypeChecked = [];
      }
    },

    // 유효성 체크
    checkValidation() {
      let msg = '';
      let checkResult = true;
      let valid = [
        {field: 'boardInfo.eponame', type: 'I', name: '관리제목', required: true},
        {field: 'boardInfo.isepointdup', type: 'I', name: '중복사용여부', required: true},
        {field: 'boardInfo.eporeason', type: 'I', name: '지급사유', required: true},
        {field: 'boardInfo.isnowpay', type: 'I', name: '지급일', required: true},
        {field: 'boardInfo.ismemtype', type: 'I', name: '대상범위', required: true},
        {field: 'boardInfo.payepoint', type: 'I', name: '지급포인트', required: true},
        {field: 'boardInfo.uselimitordamt', type: 'I', name: '사용제한주문금액', required: true},
        {field: 'boardInfo.uselimitpoint', type: 'I', name: '사용제한포인트', required: true},
      ]

      // 즉시 지급
      if (this.isnowpay === 'T') {
        valid.push({field: 'boardInfo.epousedaydnt', type: 'I', name: 'D포인트 유효기간 (일수)', required: true});
      }

      // e포인트 유효 기간별 지급
      if (this.isDay === 'T') {
        valid.push({field: 'boardInfo.epousedaydnt', type: 'I', name: 'D포인트 유효기간 (일수)', required: true});
      } else {
        valid.push({field: 'boardInfo.epopayday', type: 'I', name: 'D포인트 유효기간 (날짜)', required: true});
      }

      // 대상범위
      if (this.boardInfo.ismemtype === 'T') {
        valid.push({field: 'boardInfo.mumembertype', type: 'I', name: '대상회원유형', required: true},
            {field: 'boardInfo.mumemlvtype', type: 'I', name: '대상회원등급', required: true})
      } else {
        if (this.memberList.length === 0) {
          alert("특정 회원 대상을 입력해주세요.");
          return;
        }
      }

      // (1) 기본 정보 검사
      msg = this.$util.validMsg(this.$data, valid);
      if (!this.$util.isNull(msg)) {
        checkResult = false;
        alert(msg);
        return;
      }

      // (2) 포인트 검사
      if (this.boardInfo.payepoint * 1 < this.boardInfo.uselimitpoint * 1) {
        alert("사용가능 포인트는 지급포인트보다 작아야 합니다.");
        checkResult = false;
        return;
      }

      // (3) 날짜 검사
      let startDate = '';
      let endDate = '';

      // 즉시 지급일 경우
      if (this.boardInfo.isnowpay === 'T') {
        startDate = this.$util.getDate12NoSeparator();
        // 지급 후 : 일수
        if (this.isDay === 'T') {
          endDate = this.$util.getAddDate(startDate, this.boardInfo.epousedaydnt * 1, '') + startDate.substring(8);
        } else { // 지급 후 : 날짜
          endDate = this.boardInfo.epovalidday;
        }
      } else {
        startDate = this.boardInfo.epopayday;
        let startCopy = this.$util.getFormatDate(startDate);
        // 지급 후 : 일수
        if (this.isDay === 'T') {
          endDate = this.$util.getAddDate(startCopy, this.boardInfo.epousedaydnt * 1, '') + startDate.substring(8, 12);
        } else { // 지급 후 : 날짜
          endDate = this.boardInfo.epovalidday;
        }
      }

      if (startDate >= endDate) {
        alert('유효기간을 지급일 이후로 설정해주세요.');
        checkResult = false;
        return;
      } else {
        this.boardInfo.epostday = startDate;
        this.boardInfo.epoedday = endDate;
      }

      return checkResult;
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
        let isExist = this.memberList.find(element => element.userid === obj.userid);
        if (typeof isExist === 'undefined') {
          excelResult.push(obj);
        }
      });

      // 유저 정보 조회
      if (excelResult.length !== 0) {
        // let userIdList = excelResult.map(obj => obj.userid);
        let params = {
          useridlist: excelResult,
          isloading: false,
        }

        this.onSearchUserInfo(params);
      }
    },

    // 유저 정보 조회
    onSearchUserInfo(params) {
      this.$http.post("/admin/promotion/promotion/epoint/search/member", params)
          .then(result => {
            if (result.statusCode === 200) {
              let data = result.data;
              this.memberCheckList = [];
              if (this.memberList.length === 0) {
                Object.assign(this.memberList, data.list);
                this.memberList.splice();
              } else {
                data.list.forEach(obj => {
                  this.memberList.push(obj);
                })
              }
            }
          })
          .catch(error => {
            this.$util.debug(error);
          });
    },

    // 저장
    goSave() {
      if (!this.checkValidation()) {
        return;
      }

      if (confirm("저장 하시겠습니까?")) {
        // 데이터 저장
        let params = Object.assign({}, this.boardInfo);

        // 지급일 종류 별 셋팅
        if (params.isnowpay === 'T') { // 즉시 지급
          params.epopayday = this.boardInfo.epostday;
        }

        // e포인트 유효기간 별 셋팅
        if (this.isDay === 'T') { // 일간
          params.epovalidday = this.boardInfo.epoedday;
        } else { // 날짜
          params.epousedaydnt = null;
        }

        // 대상범위 별 셋팅
        if (params.ismemtype === 'T') { // 특정 유형/등급 대상
          this.memberList = [];
        } else { // 특정 회원 대상
          params.mumemlvtype = '';
          params.mumembertype = '';
          params.memberlist = this.memberList;
        }

        // 저장 가능 여부 판별용
        params.epopayday = this.boardInfo.epostday;
        params.epovalidday = this.boardInfo.epoedday;

        this.$http.post("/admin/promotion/promotion/epoint/save", params)
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

    // 특정회원 추가 팝업
    openAddUserPopup() {
      this.$eventBus.$emit('modalShow', CommonAddUserPopup, null,
          (result) => {
            let list = result.list;
            list.forEach(obj => {
              let isExist = this.memberList.find(element => element.userid === obj.userid);
              if (typeof isExist === 'undefined') {
                this.memberList.push(obj);
              }
            })
          }
      );
    },

    // 테이블 소트
    sortToggle(key) {
      let arr = key.split("_");
      let sortKey = arr[0];
      let sortOrder = (arr[1] === 'asc') ? 'desc' : 'asc';
      let sortName = sortKey + '_' + sortOrder;

      this.sortData = this.$options.data().sortData;

      this.sortData[sortKey] = sortName;
      this.sortData.psort = sortName;

      this.memberList.sort((a,b) => {
        let sort = 1;
        if(sortOrder === 'asc') {
          sort = 1;
        } else {
          sort = -1;
        }

        return a[sortKey] > b[sortKey] ? sort : -1 * sort;
      });

    },

    // 회원 전체 체크
    onUserCheckAll(checked) {
      this.memberCheckList = [];
      if (checked) {
        this.memberList.forEach(obj => {
          this.memberCheckList.push(obj);
        });
      }
    },

    // 회원 삭제
    onDeleteUser() {
      if (this.memberList.length === 0) {
        alert("선택된 값이 없습니다.");
        return;
      }

      let removedEnterList = [];
      this.memberList.forEach(obj => {
        // 중복 제거
        let isExist = this.memberCheckList.find(element => element.userid === obj.userid);
        if (typeof isExist === 'undefined') {
          removedEnterList.push(obj);
        }
      })

      this.memberList = removedEnterList;
    },

    // 엑셀양식다운로드
    downloadExcelTemplate: function(filename) {
        let params = { filename: filename }   // 서버에 저장되어있는 파일명
        let config = { responseType: 'arraybuffer' };
        this.$http.post('/admin/common/excel/download', params, config);
    },

    ///////////////////////////////////////////////////////////////////////////////////////////

    ///////////////////////////////////외부, 콜백 메서드//////////////////////////////////////////
    // 진행기간 DatePicker 콜백 메서드
    getEventTimeDate(date) {
      this.boardInfo.epopayday = date.toDate12;
    },
    onChangeValidDay(value) {
      this.boardInfo.epovalidday = value.replace(/-/g, "").concat("2359");
      this.epovaliddayformat = value;
    },
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
  watch: {
    // 회원 유형 체크 상태 검사
    'checkObj.mumemberTypeChecked'(value) {
      if (value.length < this.pageCode.mumembertype.length) {
        this.checkObj.isallmumember = 'F';
      } else {
        this.checkObj.isallmumember = 'T';
      }
      this.boardInfo.mumembertype = this.checkObj.mumemberTypeChecked.join();
    },

    // 회원 등급 체크 상태 검사
    'checkObj.mumemlvTypeChecked'(value) {
      if (value.length < this.pageCode.mumemlvtype.length) {
        this.checkObj.isallmumemlv = 'F';
      } else {
        this.checkObj.isallmumemlv = 'T';
      }
      this.boardInfo.mumemlvtype = this.checkObj.mumemlvTypeChecked.join();
    },
  }
}
</script>
