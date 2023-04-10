<template>
  <div>
    <!-- 유저 상세 정보 팝업 -->
    <AdminMemberInfo
        v-if="isMemberDetailShow"
        v-bind:activeUserNo="memberDetailUserNo"
        v-on:closeDetail="closeMemberInfoPopup"
        style="z-index: 1005;"
    />

    <!-- 시리얼프로모션 상세 팝업 -->
    <div id="modal-wrap" class="modal" style="display: block;">
      <div class="modal-content" style="width: 1200px;">
        <div class="pop-header">
          <h2>시리얼프로모션 상세</h2>
          <button type="button" class="pop-close" @click="$emit('close')"></button>
        </div>
        <div class="pop-body">
          <div class="gray-box mg0 clearfix">
            <div class="fl">
              <span>작성자 : {{ boardInfo.reguserid }}</span>
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
                <dt>사용여부</dt>
                <dd>
                  <div class="radio_wrap wide">
                    <input type="radio" name="tr_group001" id="tr_group011" value="F"
                           v-model="boardInfo.istrash"/><label
                      for="tr_group011">사용함</label>
                    <input type="radio" name="tr_group002" id="tr_group021" value="T"
                           v-model="boardInfo.istrash"/><label
                      for="tr_group021">사용안함</label>
                  </div>
                </dd>
              </dl>
              <dl>
                <dt>관리제목<i class="essential"></i></dt>
                <dd><input type="text" style="width: 100%" placeholder="관리제목" v-model="boardInfo.title"/></dd>
              </dl>
            </div>
          </div>
          <div class="clearfix">
            <div class="bar-title fl">조건설정</div>
            <div class="fr">
              <span class="txt-orange"><i class="icon-alert"></i>여러 개의 시리얼번호의 자동발급 제한 : 최대 5천개/1회, 10회/1일</span>
            </div>
          </div>
          <div class="form-area">
            <table cellpadding="0" cellspacing="0" class="gray-tb">
              <colgroup>
                <col width="170px">
                <col width="">
              </colgroup>
              <tbody>
              <tr>
                <th>진행기간<i class="essential"></i></th>
                <td>
                  <CommonDatePickerFromTo
                      :fromYYYYMMDD="serialTimeInfo.startYYYYMMDD"
                      :fromHH="serialTimeInfo.startHH"
                      :fromMM="serialTimeInfo.startMM"
                      :toYYYYMMDD="serialTimeInfo.toYYYYMMDD"
                      :toHH="serialTimeInfo.toHH"
                      :toMM="serialTimeInfo.toMM"
                      @getDate="pickerChangeEvent"
                  />
                </td>
              </tr>
              <tr>
                <th>혜택 구분<i class="essential"></i></th>
                <td>
                  <div class="radio_wrap wide3">
                    <div v-for="(row, i) in commonCode.srptype" :key="i">
                      <input type="radio" name="group01" :value="row.cmcode" :id="'group01' + i"
                             v-model="boardInfo.srptype"/>
                      <label :for="'group01' + i">{{ row.codename }}</label>
                    </div>
                  </div>
                </td>
              <tr>
                <th>시리얼번호 생성방법<i class="essential"></i></th>
                <td>
                  <div class="radio_wrap dpib">
                    <div v-for="(row, i) in commonCode.srctype" :key="i">
                      <input type="radio" name="group02" :value="row.cmcode" :id="'group02' + i"
                             v-model="boardInfo.srctype"/>
                      <label :for="'group02' + i">{{ row.codename }}</label>
                    </div>
                  </div>
                  <span class="txt-orange ml10"><i class="icon-alert"></i>시리얼번호 : 영문대문자 및 숫자 조합 20자</span>
                </td>
              </tr>

              <!-- 시리얼번호 발급 기준 -->
              <tr v-if="
            (boardInfo.srptype === $store.getters['ADMIN'].SRPTYPE_SRT_SAVE ||
                        boardInfo.srptype === $store.getters['ADMIN'].SRPTYPE_SRT_EPOINT ||
                        boardInfo.srptype === $store.getters['ADMIN'].SRPTYPE_SRT_COUPON)&&
            (boardInfo.srctype === $store.getters['ADMIN'].SRCTYPE_SRC_AUTO ||
                        boardInfo.srctype === $store.getters['ADMIN'].SRCTYPE_SRC_DIR)
            ">
                <th>시리얼번호 발급기준<i class="essential"></i></th>
                <td>
                  <div class="dpb">
                    <input type="radio" name="group03" class="circle" id="group31" value="T"
                           v-model="boardInfo.isequalserial" @click="onClickEqual"/>
                    <label for="group31">한 개의 동일한 시리얼 번호</label>
                    <span>(선착순</span>
                    <input type="number" class="center ml3" style="width: 60px;" value="T" v-model="boardInfo.dupcnt"/>
                    <span class="ml3">회 사용 유효 / 전체회원 대상)</span>
                  </div>
                  <div class="dpb">
                    <input type="radio" name="group03" class="circle" id="group32" value="F"
                           v-model="boardInfo.isequalserial" @click="onClickEqual"/>
                    <label for="group32">여러 개의 다른 시리얼 번호</label>
                    <input type="number" class="center" style="width: 60px;" v-model="boardInfo.issuecnt"/>
                    <span class="ml3">개(각 번호당 1회만 사용 유효)</span>
                  </div>
                </td>
              </tr>
              <!-- /시리얼번호 발급 기준 -->

              <!-- 시리얼번호 등록 (직접) -->
              <tr v-if="
            (boardInfo.srptype === $store.getters['ADMIN'].SRPTYPE_SRT_SAVE ||
                        boardInfo.srptype === $store.getters['ADMIN'].SRPTYPE_SRT_EPOINT ||
                        boardInfo.srptype === $store.getters['ADMIN'].SRPTYPE_SRT_COUPON)&&
            (boardInfo.srctype === $store.getters['ADMIN'].SRCTYPE_SRC_DIR)
            ">
                <th>시리얼번호 등록<i class="essential"></i></th>
                <td>
                  <div class="dpb">
                    <input type="text" @input="bindSerialInput" :value="serialInput" maxlength="20"
                           @keyup.enter="onSerialNumDupCheck"/>
                    <button type="button" class="btn blue-line ml3" @click="onSerialNumDupCheck">중복확인</button>
                    <span class="txt-orange ml10"><i class="icon-alert"></i>영문대문자 및 숫자 조합 최소 20자, 재 등록시 기존 번호는 삭제후 재 발급합니다.</span>
                  </div>
                  <div class="dpb scroll-y mb0 mt10" style="max-height: 300px;">
                    <div class="check-field">
                      <ul class="clearfix">
                        <li v-for="(row, i) in serialList" :key="i">{{ row.serialno }}
                          <button type="button" class="del ml10" @click="onRemoveCerealCode(i)"></button>
                        </li>
                      </ul>
                    </div>
                  </div>
                </td>
              </tr>
              <!-- /시리얼번호 등록 -->

              <!-- 시리얼번호 등록 (xlsx) -->
              <tr v-if="
            (boardInfo.srptype === $store.getters['ADMIN'].SRPTYPE_SRT_SAVE ||
                        boardInfo.srptype === $store.getters['ADMIN'].SRPTYPE_SRT_EPOINT ||
                        boardInfo.srptype === $store.getters['ADMIN'].SRPTYPE_SRT_COUPON)&&
            (boardInfo.srctype === $store.getters['ADMIN'].SRCTYPE_SRC_EXCEL)
            ">
                <th>시리얼번호 등록<i class="essential"></i></th>
                <td>
                  <label for="input-file-new" class="btn blue-line">파일첨부</label>
                  <input type="file" id="input-file-new" style="display: none" accept=".xls,.xlsx"
                         ref="inputFile" @change="selectFile($event)">
                  <span v-for="(row, i) in uploadFile" :key="i">
                    <a class="file-link" style="text-decoration: none !important; color: #666">{{ row.file.name }}</a>
                    <button type="button" class="file-del" @click="deleteUploadFile(i)"></button>
                  </span>
                  <span class="txt-orange ml10"><i class="icon-alert"></i>재 등록시 기존 시리얼 번호는 삭제되고 새로 발급됩니다.</span>
                </td>
              </tr>

              <!-- 적립금 지급 -->
              <tr v-if="
            boardInfo.srptype === $store.getters['ADMIN'].SRPTYPE_SRT_SAVE
            ">
                <th>적립금 지급<i class="essential"></i></th>
                <td>
                  <span>시리얼번호를 입력한 회원에게 적립금</span>
                  <input type="number" class="right ml3" style="width: 60px;" v-model="boardInfo.srppoint"/>
                  <span class="ml3">포인트를 지급</span>
                </td>
              </tr>
              <!-- /적립금 지급 -->

              <!-- D포인트 지급 -->
              <tr v-if="
            boardInfo.srptype === $store.getters['ADMIN'].SRPTYPE_SRT_EPOINT
            ">
                <th>D포인트 지급<i class="essential"></i></th>
                <td>
                  <span>시리얼번호를 입력한 회원에게 D포인트</span>
                  <input type="number" class="right ml3" style="width: 60px;" v-model="boardInfo.srppoint"/>
                  <span class="ml3">포인트를 지급</span>
                </td>
              </tr>
              <!-- /D포인트 지급 -->

              <!-- D포인트 중복사용여부 -->
              <tr v-if="
            boardInfo.srptype === $store.getters['ADMIN'].SRPTYPE_SRT_EPOINT
            ">
                <th>D포인트 중복사용여부<i class="essential"></i></th>
                <td>
                  <div class="radio_wrap wide dpib">
                    <input type="radio" name="group04" id="group41" value="T" v-model="boardInfo.isepointdup"/><label
                      for="group41">허용</label>
                    <input type="radio" name="group04" id="group42" value="F" v-model="boardInfo.isepointdup"/><label
                      for="group42">미허용</label>
                  </div>
                  <span class="txt-orange ml10"><i class="icon-alert"></i>적립금과 함께 사용 가능한지의 여부</span>
                </td>
              </tr>
              <!-- /D포인트 중복사용여부 -->

              <!-- D포인트 유효기간 -->
              <tr v-if="
            boardInfo.srptype === $store.getters['ADMIN'].SRPTYPE_SRT_EPOINT
            ">
                <th>D포인트 유효기간<i class="essential"></i></th>
                <td>D포인트 적립금 프로모션을 통해 지급되는 D포인트의 유효기간은 진행기간과 동일하게 자동 설정됩니다.</td>
              </tr>
              <!-- /D포인트 유효기간 -->

              <!-- 쿠폰 지급 -->
              <tr v-if="
            boardInfo.srptype === $store.getters['ADMIN'].SRPTYPE_SRT_COUPON
            ">
                <th>쿠폰 지급<i class="essential"></i></th>
                <td>
                  <span>시리얼번호를 입력한 회원에게</span>
                  <select style="width: 450px;" v-model="boardInfo.comcpnidx">
                    <option v-for="(row, i) in couponList" :key="i" :value="row.comcpnidx">{{ row.title }}</option>
                  </select>
                  <span class="ml3">쿠폰을 지급</span>
                </td>
              </tr>
              <!-- /쿠폰 지급 -->

              </tbody>
            </table>
          </div>

          <div class="bar-title">S/N 등록 내역</div>
          <div class="caption-group clearfix">
            <div class="total-group fl">
              <span class="total">전체 <strong>{{ serialNoList.length }}</strong>건</span>
            </div>
            <div class="btn-group fr">
              <button type="button" class="btn green-line" @click="onSerialNoExcelDownload"><i class="icon-excel"></i>엑셀다운로드
              </button>
            </div>
          </div>
          <div class="scroll-y" style="max-height: 400px;">
            <table cellpadding="0" cellspacing="0" class="data-tb align-c">
              <caption>D포인트</caption>
              <colgroup>
                <col width="5%"/><!-- No -->
                <col width=""/><!-- 시리얼번호 -->
                <col width="12%"/><!-- 아이디 -->
                <col width="12%"/><!-- 이름 -->
                <col width="10%"/><!-- 유형 -->
                <col width="10%"/><!-- 등급 -->
                <col width="15%"/><!-- S/N 등록일자 -->
              </colgroup>
              <thead>
              <tr>
                <th>No</th>
                <th>시리얼번호</th>
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
                <th>S/N 등록일자
                  <button type="button"
                          :value="sortData.regdate"
                          class="sort"
                          :class="[{up : sortData.regdate === 'regdate_asc'}, {down : sortData.regdate === 'regdate_desc'}]"
                          @click="sortToggle(sortData.regdate)"></button>
                </th>
              </tr>
              </thead>
              <tbody>
              <tr v-for="(row, i) in serialNoList" :key="i">
                <td>{{ $util.addZero(i + 1) }}</td>
                <td>{{ row.serialno }}</td>
                <td>{{ row.userid }}</td>
                <td><a @click="goMemberInfoPopup(row.userno)" class="link">{{ row.name }}</a></td>
                <td>{{ row.dadamembertype }}</td>
                <td>{{ row.memlvtype }}</td>
                <td>{{ row.regdate }}</td>
              </tr>
              </tbody>
            </table>
          </div>
          <div class="btn-group mt20">
            <button type="button" class="btn big blue" @click="goSave" v-if="isWrite">저장</button>
            <button type="button" class="btn big darkgray" @click="$emit('close')">취소</button>
          </div>
        </div>
      </div>
    </div>
    <!-- /시리얼프로모션 상세 팝업 -->

  </div>
</template>

<script>
import CommonDatePickerFromTo from "@views.admin/common/CommonDatePickerFromTo";
import XLSX from "xlsx";
import AdminMemberInfo from "@views.admin/member/info/AdminMemberInfo";

export default {
  name: "admin.promotion.promotion.serial.detail",
  components: {AdminMemberInfo, CommonDatePickerFromTo},
  props: {
    srpIdx: Number,
    commonCode: {
      srptype: [],
      srctype: [],
      promosttype: [],
    },
  },
  data() {
    return {
      isRead: false,
      isWrite: false,
      uploadFile: [],       // 첨부파일 데이터
      boardInfo: {
        srpidx: '',         // 시리얼프로모션IDX
        srpstday: '',       // 진행시작일자
        srpedday: '',       // 진행종료일자
        srptype: '',        // 시리얼프로모션 혜택 구분
        srctype: '',        // 시리얼 생성 타입
        isequalserial: '',  // 동일시리얼여부
        issuecnt: '',       // 발급갯수
        dupcnt: '',         // 중복사용가능횟수
        srppoint: '',       // 지급포인트
        isepointdup: '',    // D포인트중복사용여부
        comcpnidx: '',      // 쿠폰IDX
        istrash: 'F',       // 삭제여부
        reguserid: '',      // 등록자ID
        regdate: '',        // 등록일시
        moduserid: '',      // 수정자ID
        moddate: '',        // 수정일시
        title: '',          // 관리제목
      },

      copyBoardInfo: {},    // 데이터 비교용 복사본

      // 수정 여부 판별할 날짜 데이터 저장용
      copyDay: {
        epopayday: '',
        epovalidday: '',
        type: 'update',
      },

      serialNoList: [],     // 저장된 시리얼 번호 리스트

      serialInput: '',      // 시리얼 번호 입력 텍스트
      serialList: [],       // 시리얼 번호 저장 리스트
      serialXlsxList: [],   // 시리얼 번호 엑셀 저장 리스트
      copySerialXlsxList: [],   // 시리얼 번호 엑셀 저장 리스트 (비교용)

      couponList: [],       // 쿠폰 리스트 조회

      sortData: {
        dadamembertype: 'dadamembertype_desc',
        memlvtype: 'memlvtype_desc',
        regdate: 'regdate_desc',
      },

      serialTimeInfo: {     // 진행기간
        startYYYYMMDD: '',
        startHH: '',
        startMM: '',
        toYYYYMMDD: '',
        toHH: '',
        toMM: ''
      },

      isMemberDetailShow: false,        // 유저 상세 팝업
      memberDetailUserNo: '',           // 유저 번호
    }
  },
  methods: {
    ///////////////////////////////////내부 사용 메서드///////////////////////////////////////////
    goSave() {
      let paramFieldList = [];
      if (!this.checkValidation(paramFieldList)) {
        return;
      }

      let params = {};
      for (let key in paramFieldList) {
        let element = paramFieldList[key];
        params[element] = this.boardInfo[element];
      }

      // 직접 등록
      if (params.srctype === this.$store.getters['ADMIN'].SRCTYPE_SRC_DIR) {
        params.serialnolist = this.serialList;
      }

      // 엑셀 등록
      if (params.srctype === this.$store.getters['ADMIN'].SRCTYPE_SRC_EXCEL) {
        params.serialnolist = this.serialXlsxList;
        params.isequalserial = 'T';
        params.issuecnt = '';
        params.dupcnt = '';
      }

      params.srpidx = this.srpIdx;

      // D포인트 유효기간 판별용 날짜
      Object.assign(params, this.copyDay);

      // 시리얼 번호 삭제 여부 판별
      this.isCheckDeleteSerialNum(params);

      if (confirm("저장 하시겠습니까?")) {
        this.$http.post("/admin/promotion/promotion/serial/update", params)
            .then(result => {
              if (result.statusCode === 200) {
                alert("저장이 완료되었습니다.");
                this.$emit('close', true);
              }
            })
            .catch(error => {
              this.$util.debug(error);
            });
      }
    },

    // 유효성 체크
    checkValidation(paramFieldList) {
      let msg = '';
      let checkResult = true;
      let valid = [
        {field: 'boardInfo.istrash', type: 'I', name: '사용여부', required: true},
        {field: 'boardInfo.title', type: 'I', name: '관리제목', required: true},
        {field: 'boardInfo.srpstday', type: 'I', name: '진행시작일자', required: true},
        {field: 'boardInfo.srpedday', type: 'I', name: '진행종료일자', required: true},
        {field: 'boardInfo.srptype', type: 'I', name: '시리얼프로모션 혜택 구분', required: true},
        {field: 'boardInfo.srctype', type: 'I', name: '시리얼 생성 타입', required: true},
      ]

      // 시리얼번호 발급 기준
      if ((this.boardInfo.srptype === this.$store.getters['ADMIN'].SRPTYPE_SRT_SAVE ||
          this.boardInfo.srptype === this.$store.getters['ADMIN'].SRPTYPE_SRT_EPOINT ||
          this.boardInfo.srptype === this.$store.getters['ADMIN'].SRPTYPE_SRT_COUPON) &&
          (this.boardInfo.srctype === this.$store.getters['ADMIN'].SRCTYPE_SRC_AUTO ||
              this.boardInfo.srctype === this.$store.getters['ADMIN'].SRCTYPE_SRC_DIR)) {
        valid.push({field: 'boardInfo.isequalserial', type: 'I', name: '동일시리얼여부', required: true})
        if (this.boardInfo.isequalserial === 'T') {
          if (this.$util.isNull(this.boardInfo.dupcnt)) {
            this.boardInfo.dupcnt = '';
          }
          valid.push({field: 'boardInfo.dupcnt', type: 'I', name: '중복사용가능횟수', required: true})
          // alert("중복사용가능횟수를 입력해주세요.");
          // checkResult = false;
          // return;
        } else {
          valid.push({field: 'boardInfo.issuecnt', type: 'I', name: '발급갯수', required: true})
        }
      }

      // 시리얼번호 등록 (직접)
      if ((this.boardInfo.srptype === this.$store.getters['ADMIN'].SRPTYPE_SRT_SAVE ||
          this.boardInfo.srptype === this.$store.getters['ADMIN'].SRPTYPE_SRT_EPOINT ||
          this.boardInfo.srptype === this.$store.getters['ADMIN'].SRPTYPE_SRT_COUPON) &&
          (this.boardInfo.srctype === this.$store.getters['ADMIN'].SRCTYPE_SRC_DIR)) {
        // if (this.serialList.length === 0) {
        //   alert("시리얼번호를 등록해주세요.");
        //   checkResult = false;
        //   return;
        // }
        // 여러 개의 다른 시리얼 번호 && 등록된 시리얼 갯수와 발급 갯수가 다르다면
        if (this.boardInfo.isequalserial === 'F' && (this.serialList.length !== this.boardInfo.issuecnt * 1)) {
          alert("발급기준의 시리얼 번호 갯수와 등록된 번호의 갯수가 다릅니다.");
          checkResult = false;
          return;
        }
      }

      // 시리얼번호 등록 (xlsx)
      if ((this.boardInfo.srptype === this.$store.getters['ADMIN'].SRPTYPE_SRT_SAVE ||
          this.boardInfo.srptype === this.$store.getters['ADMIN'].SRPTYPE_SRT_EPOINT ||
          this.boardInfo.srptype === this.$store.getters['ADMIN'].SRPTYPE_SRT_COUPON) &&
          (this.boardInfo.srctype === this.$store.getters['ADMIN'].SRCTYPE_SRC_EXCEL)) {
        if (this.uploadFile.length === 0 && this.serialNoList.length === 0) {
          alert("시리얼번호를 등록해주세요.");
          checkResult = false;
          return;
        }
      }

      // 적립금 지급
      if (this.boardInfo.srptype === this.$store.getters['ADMIN'].SRPTYPE_SRT_SAVE) {
        valid.push({field: 'boardInfo.srppoint', type: 'I', name: '적립금', required: true})
      }

      // D포인트, 중복사용여부
      if (this.boardInfo.srptype === this.$store.getters['ADMIN'].SRPTYPE_SRT_EPOINT) {
        valid.push({field: 'boardInfo.srppoint', type: 'I', name: '적립금', required: true})
        valid.push({field: 'boardInfo.isepointdup', type: 'I', name: 'D포인트 중복사용 여부', required: true})
      }

      // 쿠폰
      if (this.boardInfo.srptype === this.$store.getters['ADMIN'].SRPTYPE_SRT_COUPON) {
        valid.push({field: 'boardInfo.comcpnidx', type: 'I', name: '쿠폰', required: true})
      }

      // (1) 기본 정보 검사
      msg = this.$util.validMsg(this.$data, valid);
      if (!this.$util.isNull(msg)) {
        checkResult = false;
        alert(msg);
        return;
      }

      // 저장될 obj field name 객체 생성
      for (let i = 0; i < valid.length; i++) {
        let fieldName = valid[i].field.split(".");
        fieldName.shift();
        paramFieldList.push(fieldName[0]);
      }

      return checkResult;
    },

    // 시리얼 번호 삭제 여부
    isCheckDeleteSerialNum(params) {
      // 혜택 구분이 변경된 경우
      if (params.srptype !== this.copyBoardInfo.srptype) {
        // 변경전 [쿠폰] 인 경우
        if (this.copyBoardInfo.srptype === this.$store.getters["ADMIN"].SRPTYPE_SRT_COUPON) {
          params.comcpnidx = '';
        }
        // 변경전 [D포인트] 인경우
        else if (this.copyBoardInfo.srptype === this.$store.getters["ADMIN"].SRPTYPE_SRT_COUPON) {
          params.isepointdup = 'T';
        }
      }

      let srcType = params.srctype // 생성타입
      // 생성 타입이 변경된 경우
      if (srcType !== this.copyBoardInfo.srctype) {
        // console.log("생성 타입 변경");
        params.isdelserial = 'T';
      }
      // 엑셀업로드가 아니고 && 발급기준이 변경된 경우
      else if (
          (srcType !== this.$store.getters['ADMIN'].SRCTYPE_SRC_EXCEL) && params.isequalserial !== this.copyBoardInfo.isequalserial) {
        // console.log("발급 기준 변경");
        params.isdelserial = 'T';
        // 한 개
        if ("T" === params.isequalserial) {
          params.issuecnt = '';
        } else {
          params.dupcnt = '';
        }
      }
      // 엑셀 파일 재 업로드
      else if ((srcType === this.$store.getters['ADMIN'].SRCTYPE_SRC_EXCEL) && params.serialnolist.length !== 0) {
        // console.log("엑셀 재 업로드");
        params.isdelserial = 'T';
      }
      // 엑셀업로드가 아니고 && 발급 갯수 변경
      else if ((srcType !== this.$store.getters['ADMIN'].SRCTYPE_SRC_EXCEL) && params.issuecnt !== this.copyBoardInfo.issuecnt) {
        // console.log("발급 갯수 변경");
        params.isdelserial = 'T';
      }
    },

    onInit() {
      this.onSearch();
      this.searchCouponList();
    },

    // 상세 조회
    onSearch() {
      let params = {
        srpidx: this.srpIdx,
      }

      this.$http.post("/admin/promotion/promotion/serial/detail", params)
          .then(result => {
            if (result.statusCode === 200) {
              let data = result.data;
              this.boardInfo = data.list;
              this.copyBoardInfo = Object.assign({}, this.boardInfo);
              this.serialNoList = data.snlist;
              // this.serialList = Object.assign([], data.snlist);

              this.copyDay.epopayday = data.list.srpstday;
              this.copyDay.epovalidday = this.boardInfo.srpedday;

              // 시간 셋팅
              this.setDate();
            }
          })
          .catch(error => {
            this.$util.debug(error);
          });
    },

    // S/N 발급 내역 조회
    onSerialNoSearch() {
      let params = {
        srpidx: this.srpIdx,
        psort: this.sortData.psort,
        isloading: false,
      }

      this.$http.post("/admin/promotion/promotion/serial/detail/snlist", params)
          .then(result => {
            if (result.statusCode === 200) {
              this.serialNoList = result.data.list;
            }
          })
          .catch(error => {
            this.$util.debug(error);
          });
    },

    // 쿠폰 리스트 조회
    searchCouponList() {
      let params = {
        isloading: false,
      }
      this.$http.post("/admin/common/coupon/list", params)
          .then(result => {
            if (result.statusCode === 200) {
              this.couponList = result.data.list;
            }
          })
          .catch(error => {
            this.$util.debug(error);
          });
    },

    // 날짜 데이터 셋팅
    setDate() {
      this.serialTimeInfo.startYYYYMMDD = this.boardInfo.startyyyymmdd;
      this.serialTimeInfo.startHH = this.boardInfo.starthh;
      this.serialTimeInfo.startMM = this.boardInfo.startmm;

      this.serialTimeInfo.toYYYYMMDD = this.boardInfo.toyyyymmdd;
      this.serialTimeInfo.toHH = this.boardInfo.tohh;
      this.serialTimeInfo.toMM = this.boardInfo.tomm;
    },

    // 시리얼 중복 체크
    onSerialNumDupCheck() {
      // 시리얼 번호 타입
      if (this.boardInfo.isequalserial === 'T') { // 한 개
        if (this.serialList.length === 1) {
          alert("발급 기준에 따라 1개 이상의 번호를 등록할 수 없습니다.");
          return;
        }
      } else { // 여러개
        if (this.$util.isNull(this.boardInfo.issuecnt)) {
          alert("발급기준 시리얼 번호 갯수를 입력해주세요.");
          return;
        }

        if (this.serialList.length === this.boardInfo.issuecnt * 1) {
          alert("발급 기준에 따라 " + this.boardInfo.issuecnt + "개 이상의 번호를 등록할 수 없습니다.");
          return;
        }
      }

      if (this.serialInput.length !== 20) {
        alert("길이는 20자입니다.");
        return;
      }

      let isExist = this.serialList.find(element => element.serialno === this.serialInput);
      if (typeof isExist === 'undefined') { // 중복 아님
        let params = {
          serialnolist: [this.serialInput],
          isloading: false,
        }
        this.$http.post("/admin/promotion/promotion/serial/check/dup", params)
            .then(result => {
              if (result.statusCode === 200) {
                if (result.data.check === false) {
                  this.serialList.push({serialno: this.serialInput});
                }
              }
            })
      } else {
        alert("시리얼 번호가 중복됩니다.");
      }
    },

    // 시리얼 발급 기준 클릭 이벤트
    onClickEqual() {
      this.serialList = [];
      this.serialInput = '';
    },

    // 시리얼 번호 입력 바인딩
    bindSerialInput(event) {
      this.serialInput = event.target.value;
    },

    // 시리얼 번호 삭제
    onRemoveCerealCode(num) {
      this.serialList.splice(num, 1);
    },

    // 첨부 파일
    selectFile(event) {
      let file = event.target.files[0];
      let fileType = file.name.split(".")[1];
      if (!['xlsx', 'xls'].includes(fileType)) {
        alert("xlsx, xls 형식의 파일만 업로드 가능합니다.");
        return;
      }

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
        this.onExcelDataUpload(tmpResult, file);
      };
      reader.readAsArrayBuffer(file);

      // 초기화
      this.$refs.inputFile.value = '';
    },

    // 엑셀일괄 업로드
    onExcelDataUpload(excelJsonData, file) {
      let excelObj = [];

      // 받아온 excel Data 파싱해서 원하는 데이터로 변경
      excelJsonData.forEach(obj => {
        let temp = {};
        if (Object.prototype.hasOwnProperty.call(obj, 'serialno')) {
          temp.serialno = obj.serialno.toUpperCase();
        }

        if (Object.prototype.hasOwnProperty.call(obj, '시리얼번호')) {
          temp.serialno = obj.시리얼번호.toUpperCase();
        }

        // 빈값 체크
        if (Object.keys(temp).length !== 0) {
          excelObj.push(temp);
        }
      })

      // 길이가 20자 이하인 시리얼 번호 체크
      let lengthMsg = '';
      excelObj.forEach(obj => {
        if (obj.serialno.length !== 20) {
          lengthMsg += obj.serialno + '\n';
        }
      })

      if (!this.$util.isEmpty(lengthMsg)) {
        alert("시리얼 번호는 20자입니다.\n\n" + lengthMsg);
        return;
      }

      // 중복 체크후 데이터를 넣을 객체
      let excelResult = [];
      let dupList = [];

      // 중복된 시리얼 번호 체크
      excelObj.forEach(obj => {
        // 중복 제거
        let isExist = this.serialXlsxList.find(element => element.serialno === obj.serialno);
        if (typeof isExist === 'undefined') {
          excelResult.push(obj);
        } else {
          dupList.push(obj);
        }
      });

      // 중복된 시리얼 번호 알림
      if (dupList.length > 0) {
        let dupMsg = '';
        dupList.forEach(obj => {
          dupMsg += obj.serialno + '\n';
        });

        alert("중복된 시리얼번호가 존재합니다.\n\n" + dupMsg);
        return;
      }

      // DB 중복 체크
      let params = {
        serialnolist: excelResult.map(obj => obj.serialno),
        isloading: false,
      }
      this.$http.post("/admin/promotion/promotion/serial/check/dup/serial", params)
          .then(result => {
            if (result.statusCode === 200) {
              let data = result.data;
              if (data.list.length === 0) { // 중복 없음
                let obj = {
                  file: file,
                  key: this.uploadFile.length + 1
                }
                this.uploadFile.push(obj);
                this.serialXlsxList = this.serialXlsxList.concat(excelResult);
                this.copySerialXlsxList.push(excelResult);
              } else { // 중복 있음
                let msg = '';
                data.list.forEach(obj => {
                  msg += obj.serialno + '\n';
                });
                alert("이미 생성된 시리얼번호와 중복됩니다.\n\n" + msg);
              }
            }
          })
          .catch(error => {
            this.$util.debug(error);
          });
    },

    // 첨부 파일 삭제
    deleteUploadFile(index) {
      this.uploadFile.splice(index, 1); // 파일 삭제
      let deleteSerialNo = this.copySerialXlsxList[index]; // 삭제될 번호
      this.serialXlsxList = this.serialXlsxList.filter(it => !deleteSerialNo.includes(it));
      this.copySerialXlsxList.splice(index, 1);
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

      this.onSerialNoSearch();
    },

    // 시리얼 번호 엑셀다운로드
    onSerialNoExcelDownload() {
      let param = {
        srpidx: this.srpIdx
      };
      let postConfig = {responseType: 'arraybuffer'};
      this.$http.post("/admin/promotion/promotion/serial/excel/snlist", param, postConfig)
          .then(result => {
            this.$util.debug(result);
          })
          .catch(error => {
            this.$util.debug(error);
          });
    },
    ///////////////////////////////////////////////////////////////////////////////////////////

    ///////////////////////////////////외부, 콜백 메서드//////////////////////////////////////////
    // datepicker callback
    pickerChangeEvent(data) {
      this.boardInfo.srpstday = data.fromDate12;
      this.boardInfo.srpedday = data.toDate12;
    },
    ///////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////팝업 메서드//////////////////////////////////////////////
    // 유저 상세 팝업
    goMemberInfoPopup(userNo) {
      this.isMemberDetailShow = true;
      this.memberDetailUserNo = userNo;
    },

    closeMemberInfoPopup() {
      this.isMemberDetailShow = false;
      this.memberDetailUserNo = '';
    },
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
    serialInput(val) {
      this.serialInput = val.replace(/[ㄱ-힣~!@#$%^&*()_+|<>?:{}= ]/g, '').toUpperCase();
    },

    'boardInfo.issuecnt'(val, oldVal) {
      // 직접등록 && 수량이 적어지면
      if ((this.boardInfo.srctype === this.$store.getters["ADMIN"].SRCTYPE_SRC_DIR) && (this.serialList.length > val)) {
        if (confirm("수량이 적어지면 등록된 시리얼 번호가 줄어듭니다.\n변경하시겠습니까?")) {
          let count = oldVal - val;
          for (let i = 0; i < count; i++) {
            this.serialList.shift();
          }
        }
      }

      if (val * 1 > 5000) {
        alert("자동 발급은 최대 5000개로 제한됩니다.");
        this.boardInfo.issuecnt = '5000';
      }
    },
  },
}
</script>

<style scoped>
.check-field ul li {
  width: 25%;
}
</style>