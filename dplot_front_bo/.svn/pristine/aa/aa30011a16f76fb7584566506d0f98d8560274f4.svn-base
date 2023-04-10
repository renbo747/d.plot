<template>
  <!-- 출석체크이벤트 등록 팝업 -->
  <div id="modal-wrap" class="modal" style="display: block;">
    <div class="modal-content" style="width: 1400px;">
      <div class="pop-header">
        <h2>출석체크이벤트 등록</h2>
        <button type="button" class="pop-close" @click="$emit('close')"></button>
      </div>
      <div class="pop-body">
        <div class="bar-title">기본정보</div>
        <div class="boxing">
          <div class="form-area">
            <dl>
              <dt>사용여부</dt>
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
              <dd><input type="text" style="width: 100%" placeholder="설명" v-model="boardInfo.evdesc"/></dd>
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
              <th>진행기간<i class="essential"></i></th>
              <td>
                <CommonDatePickerFromTo
                    :fromYYYYMMDD="eventTimeInfo.startYYYYMMDD"
                    :fromHH="eventTimeInfo.startHH"
                    :fromMM="eventTimeInfo.startMM"
                    :toYYYYMMDD="eventTimeInfo.toYYYYMMDD"
                    :toHH="eventTimeInfo.toHH"
                    :toMM="eventTimeInfo.toMM"
                    @getDate="getEventTimeDate"
                />
              </td>
            </tr>
            <tr>
              <th>적용채널</th>
              <td>
                <div class="check-wrap">
                  <input type="checkbox" id="all1" v-model="checkObj.isallmuappch" true-value="T" false-value="F"
                         @change="checkAllMuAppch($event.target.checked)">
                  <label for="all1">전체</label>
                </div>
                <div class="check-wrap" v-for="(row, i) in pageCode.muappchtypecode" :key="i">
                  <input type="checkbox" :id="'group1' + i" :value="row.cmcode" v-model="checkObj.muappchTypeChecked">
                  <label :for="'group1' + i">{{ row.codename }}</label>
                </div>
              </td>
            </tr>
            <tr>
              <th>대상회원유형</th>
              <td>
                <div class="check-wrap">
                  <input type="checkbox" id="all2" v-model="checkObj.isallmumember" true-value="T" false-value="F"
                         @change="checkAllMuMemer($event.target.checked)">
                  <label for="all2">전체</label>
                </div>
                <div class="check-wrap" v-for="(row, i) in pageCode.mumembertypecode" :key="i">
                  <input type="checkbox" :id="'group2' + i" :value="row.cmcode" v-model="checkObj.mumemberTypeChecked">
                  <label :for="'group2' + i">{{ row.codename }}</label>
                </div>
              </td>
            </tr>
            <tr>
              <th>대상회원등급</th>
              <td>
                <div class="check-wrap">
                  <input type="checkbox" id="all3" v-model="checkObj.isallmumemlv" true-value="T" false-value="F"
                         @change="checkAllMuMemLv($event.target.checked)">
                  <label for="all3">전체</label>
                </div>
                <div class="check-wrap" v-for="(row, i) in pageCode.mumemlvtypecode" :key="i">
                  <input type="checkbox" :id="'group3' + i" :value="row.cmcode" v-model="checkObj.mumemlvTypeChecked">
                  <label :for="'group3' + i">{{ row.codename }}</label>
                </div>
              </td>
            </tr>
            <tr>
              <th>노출여부<i class="essential"></i></th>
              <td>
                <div class="radio_wrap wide3">
                  <div v-for="(row, i) in pageCode.disptypecode" :key="i">
                    <input type="radio" :id="'group4' + i" :value="row.cmcode" v-model="boardInfo.disptype">
                    <label :for="'group4' + i">{{ row.codename }}</label>
                  </div>
                </div>
              </td>
            </tr>
            <tr>
              <th>옵션<i class="essential"></i></th>
              <td>
                <div class="dpb">
                  <input type="checkbox" id="group61" true-value="T" false-value="F"
                         v-model="boardInfo.iscomment"/><label for="group61">댓글</label>
                  <span v-show="boardInfo.iscomment === 'T'" class="txt-gray dpib"
                        style="width: 40px; text-align: center;">비밀글</span>
                  <div v-show="boardInfo.iscomment === 'T'" class="radio_wrap dpib ml3">
                    <input type="radio" name="group061" id="group611" value="F" v-model="boardInfo.isseccomment"/><label
                      for="group611">미허용</label>
                    <input type="radio" name="group061" id="group612" value="T" v-model="boardInfo.isseccomment"/><label
                      for="group612">허용</label>
                  </div>
                </div>
              </td>
            </tr>
            <tr>
              <th>개인정보동의<i class="essential"></i></th>
              <td>
                <div class="radio_wrap wide dpib">
                  <input type="radio" name="group07" id="group71" :value="false" v-model="isInfo"/><label
                    for="group71">불필요</label>
                  <input type="radio" name="group07" id="group72" :value="true" v-model="isInfo"/><label
                    for="group72">필요</label>
                </div>
                <div class="dpib ml3" v-if="isInfo === true">
                  <input type="checkbox" id="group81" true-value="T" false-value="F"
                         v-model="boardInfo.isperagree"/><label for="group81">개인정보 수집 및 이용
                  동의</label>
                  <input type="checkbox" id="group82" true-value="T" false-value="F"
                         v-model="boardInfo.ismktagree"/><label for="group82">마케팅 정보 활용
                  동의</label>
                </div>
              </td>
            </tr>
            <tr>
              <th>출석인정기준<i class="essential"></i></th>
              <td>
                <div class="radio_wrap wide">
                  <input type="radio" name="group09" id="group91" value="F" v-model="boardInfo.iscontattend"/><label
                    for="group91">미연속출석</label>
                  <input type="radio" name="group09" id="group92" value="T" v-model="boardInfo.iscontattend"/><label
                    for="group92">연속출석</label>
                </div>
              </td>
            </tr>
            <tr>
              <th>출석수<i class="essential"></i></th>
              <td>
                <input type="number" class="center" style="width: 50px;" v-model="boardInfo.winattendcnt"/>
                <span class="ml3">일 출석 시 혜택 지급 / 총 출석 가능일 수 : </span>
                <input type="text" class="center" style="width: 50px;" :value="getEventTimeDateCount" readonly/>
                <span class="ml3">일</span>
              </td>
            </tr>
            <tr>
              <th>혜택지급반복</th>
              <td>
                <div class="radio_wrap dpib">
                  <input type="radio" name="group10" id="group101" value="T" v-model="boardInfo.iswagestop"/><label
                    for="group101">혜택지급 제한(1회)</label>
                  <input type="radio" name="group10" id="group102" value="F" v-model="boardInfo.iswagestop"/><label
                    for="group102">혜택지급 반복</label>
                </div>
                <span class="ml3">(출석수 충족 시 초기화) / 총 혜택지급 가능 횟수</span>
                <input type="text" class="center" style="width: 50px;" :value="getBenefitCount" readonly/>
                <span class="ml3">회</span>
              </td>
            </tr>
            <tr>
              <th>대표이미지(PC)<i class="essential"></i></th>
              <td>
                <div class="img-with-text" style="width: 202px;">
                  <div class="img-thumb size200" :class="{'no-image': $util.isNull(files['pcimgFile'])}">
                    <img :src="imgPreview['pcimgFile']" alt="대표이미지(PC)" v-if="!$util.isNull(files['pcimgFile'])">
                  </div>
                  <button type="button" class="btn blue-line mt10" style="width: 100%;"
                          v-if="$util.isNull(files['pcimgFile'])" @click="fileAttach('pcimgFile')">파일 올리기
                  </button>
                  <input type="file" ref="pcimgFile" @change="handleFileUpload('pcimgFile')"
                         accept="image/jpeg, image/png" hidden/>
                  <button type="button" class="btn blue-line mt10" style="width: calc(50% - 3px);"
                          v-if="!$util.isNull(files['pcimgFile'])" @click="fileAttach('pcimgFile')">변경
                  </button>
                  <button type="button" class="btn red-line mt10" style="width: calc(50% - 4px);"
                          v-if="!$util.isNull(files['pcimgFile'])" @click="removeFile('pcimgFile')">삭제
                  </button>
                </div>
                <div class="img-with-text text">
                  <p class="txt-orange"><i class="icon-alert"></i>판매상품의 대표 이미지입니다. 보기 쉬운 간결한 이미지를 활용해 주세요.</p>
                  <p class="txt-orange"><i class="icon-alert"></i>사이즈: 460*460 / 최소: 200*200 / 용량: 10MB 이하 / 파일 : JPG,
                    JPEG, PNG</p>
                </div>
              </td>
            </tr>
            <tr>
              <th>대표이미지(모바일)<i class="essential"></i></th>
              <td>
                <div class="mb10">
                  <input type="checkbox" v-model="copyimgcheck" @change="setSameAsPcepreImg" id="copy-img"><label
                    for="copy-img">PC 대표 이미지를 복사</label>
                </div>
                <!-- 모바일 이미지-->
                <div class="img-with-text" style="width: 202px;">
                  <div class="img-thumb"
                       :class="[[copyimgcheck ? 'size200':'size460wide'],{'no-image': $util.isNull(files['mobileimgFile'])}]">
                    <img :src="imgPreview['mobileimgFile']" alt="대표이미지(모바일)"
                         v-if="!$util.isNull(files['mobileimgFile'])">
                  </div>
                  <button type="button" class="btn blue-line mt10" style="width: 100%;"
                          v-if="$util.isNull(files['mobileimgFile'])" @click="fileAttach('mobileimgFile')">파일 올리기
                  </button>
                  <input type="file" ref="mobileimgFile" @change="handleFileUpload('mobileimgFile')"
                         accept="image/jpeg, image/png" hidden/>
                  <button type="button" class="btn blue-line mt10" style="width: calc(50% - 3px);"
                          v-if="!$util.isNull(files['mobileimgFile'])" @click="fileAttach('mobileimgFile')">변경
                  </button>
                  <button type="button" class="btn red-line mt10" style="width: calc(50% - 4px);"
                          v-if="!$util.isNull(files['mobileimgFile'])" @click="removeFile('mobileimgFile')">삭제
                  </button>
                </div>
                <div class="img-with-text text" v-show="!copyimgcheck">
                  <p class="txt-orange"><i class="icon-alert"></i>모바일 리스팅 및 와이드형 화면에 노출되는 이미지를 업로드 해 주세요.</p>
                  <p class="txt-orange"><i class="icon-alert"></i>사이즈: 460*460 / 최소: 200*200 / 용량: 10MB 이하 / 파일 : JPG,
                    JPEG, PNG</p>
                </div>
                <div class="img-with-text text" v-show="copyimgcheck">
                  <p class="txt-orange"><i class="icon-alert"></i>판매상품의 대표 이미지입니다. 보기 쉬운 간결한 이미지를 활용해 주세요.</p>
                  <p class="txt-orange"><i class="icon-alert"></i>사이즈: 460*460 / 최소: 200*200 / 용량: 10MB 이하 / 파일 : JPG,
                    JPEG, PNG</p>
                </div>
              </td>
            </tr>
            <tr>
              <th>내용(PC)<i class="essential"></i></th>
              <td>
                <div>
                  <CommonEditor ref="pcEditor"/>
                </div>
              </td>
            </tr>
            <tr>
              <th>내용(모바일)<i class="essential"></i></th>
              <td>
                <div class="mb10">
                  <input type="checkbox" id="copy-text"><label for="copy-text">PC 내용을 복사</label>
                </div>
                <div class="mt10">
                  <CommonEditor ref="mobileEditor"/>
                </div>
              </td>
            </tr>
            <tr>
              <th>혜택 구분<i class="essential"></i></th>
              <td>
                <div class="radio_wrap wide3">
                  <div v-for="(row, i) in pageCode.bettypecode" :key="i">
                    <input type="radio" :id="'group134' + i" :value="row.cmcode" v-model="boardInfo.benefittype">
                    <label :for="'group134' + i">{{ row.codename }}</label>
                  </div>
                </div>
              </td>
            </tr>
            <!-- 적립금 선택 시 노출 -->
            <tr v-if="boardInfo.benefittype === pageCode.bettypecode[0].cmcode">
              <th>적립금 지급<i class="essential"></i></th>
              <td>
                <span>혜택지급 조건을 만족한 회원에게 적립금</span>
                <input type="number" class="center" style="width: 50px;" v-model="boardInfo.paypoint"/>
                <span class="ml3">포인트를 지급</span>
              </td>
            </tr>
            <!-- /적립금 선택 시 노출 -->
            <!-- e포인트 선택 시 노출 -->
            <tr v-if="boardInfo.benefittype === pageCode.bettypecode[1].cmcode">
              <th>D포인트 지급<i class="essential"></i></th>
              <td>
                <span>혜택지급 조건을 만족한 회원에게 D포인트</span>
                <input type="number" class="right ml3" style="width: 60px;" v-model="boardInfo.paypoint"/>
                <span class="ml3">포인트를 지급</span>
              </td>
            </tr>
            <tr v-if="boardInfo.benefittype === pageCode.bettypecode[1].cmcode">
              <th>D포인트 중복사용여부<i class="essential"></i></th>
              <td>
                <div class="radio_wrap wide dpib">
                  <input type="radio" name="group04" id="group413" value="T" v-model="boardInfo.isepointdup"/><label
                    for="group413">허용</label>
                  <input type="radio" name="group04" id="group424" value="F" v-model="boardInfo.isepointdup"/><label
                    for="group424">미허용</label>
                </div>
                <span class="txt-orange ml10"><i class="icon-alert"></i>적립금과 함께 사용 가능한지의 여부</span>
              </td>
            </tr>
            <tr v-if="boardInfo.benefittype === pageCode.bettypecode[1].cmcode">
              <th>D포인트 유효기간<i class="essential"></i></th>
              <td>
                <span>시리얼프로모션을 통해 지급되는 D포인트의 유휴기간은 진행기간과 동일하게 자동 설정됩니다.</span>
              </td>
            </tr>
            <!-- /e포인트 선택 시 노출 -->
            <!-- 쿠폰 선택 시 노출-->
            <tr v-if="boardInfo.benefittype === pageCode.bettypecode[2].cmcode">
              <th>쿠폰 지급<i class="essential"></i></th>
              <td>
                <span>혜택지급 조건을 만족한 회원에게</span>
                <select style="width: 450px;" v-model="boardInfo.comcpnidx">
                  <option v-for="(row, i) in couponList" :key="i" :value="row.idx">{{ row.fulltitle }}</option>
                </select>
                <span class="ml3">쿠폰을 지급</span>
              </td>
            </tr>
            <!-- /쿠폰 선택 시 노출-->
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
  <!-- /출석체크이벤트 등록 팝업 -->
</template>

<script>
import CommonDatePickerFromTo from "@views.admin/common/CommonDatePickerFromTo";
import CommonEditor from "@views.admin/common/CommonEditor";
import util from "@js/util";

export default {
  name: "admin.promotion.checkevent.newpost",
  components: {CommonEditor, CommonDatePickerFromTo},
  props: {
    pageCode: {                 // 페이지 코드
      disptypecode: {}, // 노출 구분
      muappchtypecode: {}, // 적용 채널
      mumembertypecode: {}, // 대상 회원
      mumemlvtypecode: {}, // 대상 회원 등급
      completetypecode: {}, // 진행 상태
      bettypecode: {},      // 혜택 구분
    },
  },
  data() {
    return {
      isInfo: false,
      copyimgcheck: false,
      boardInfo: {
        isdupenter: '',             // N.N 중복응모 사용 여부
        isenter: 'F',               // N.N 응모 사용 여부
        dupentertype: '',           // N.N 중복응모허용타입
        dupentercnt: 0,             // N.N 중복응모횟수
        istrash: 'F',               // 사용여부
        subject: '',                // 제목
        evdesc: '',                 // 이벤트 설명
        evsttime: '',               // 이벤트 시작일
        evedtime: '',               // 이벤트 종료일
        muappchtype: '',            // 적용 채널
        mumembertype: '',           // 회원 유형
        mumemlvtype: '',            // 회원 등급
        disptype: '',               // 노출 여부
        iscomment: 'T',             // 댓글 사용 여부
        isseccomment: 'F',          // 비밀글 사용 여부
        isperagree: 'F',            // 개인정보동의여부
        ismktagree: 'F',            // 마케팅 정보 동의 여부
        userno: '',                 // 작성자 번호
        reguserid: '',              // 작성자 아이디
        writer: '',                 // 작성자 이름
        iscontattend: 'F',          // 연속출석인정여부
        winattendcnt: 0,            // 당첨출석수
        iswagestop: 'T',            // 혜택지급반복
        benefittype: '',            // 혜택구분
        paypoint: 0,               // 지급액
        isepointdup: 'F',           // 중복사용여부
        comcpnidx: '',              // 쿠폰 idx
        evinfo: '',                 // 내용
        evinfomobile: '',           // 모바일 내용
        readcnt: 0,                 // 조횟수
        goodstitle: ''              // 연관상품목록타이틀
      },
      count: 0,
      files: {                      // 첨부 파일
        pcimgFile: '',
        mobileimgFile: '',
      },
      imgPreview: {                 // 이미지용
        pcimgFile: '',
        mobileimgFile: '',
      },
      eventTimeInfo: {              // 진행기간
        startYYYYMMDD: '',
        startHH: '',
        startMM: '',
        toYYYYMMDD: '',
        toHH: '',
        toMM: ''
      },
      checkObj: {
        isallmuappch: 'T',
        muappchTypeChecked: [],          // 적용 채널
        isallmumember: 'T',
        mumemberTypeChecked: [],         // 회원 유형
        isallmumemlv: 'T',
        mumemlvTypeChecked: [],          // 회원 등급
      },
      couponList: [],                     // 쿠폰 리스트
    }
  },
  methods: {
    ///////////////////////////////////내부 사용 메서드///////////////////////////////////////////
    // 저장
    goSave() {

      // 유효성 체크
      if (!this.checkValidation()) {
        return;
      }

      if (confirm("저장 하시겠습니까?")) {
        let params = JSON.parse(JSON.stringify(this.boardInfo));

        // 이미지 파일 셋팅
        let files = [];
        files.push({key: 'pcimgFile', file: this.files.pcimgFile.file});
        files.push({key: 'mobileimgFile', file: this.files.mobileimgFile.file});
        params.files = files;

        // 타입에 따른 데이터 셋팅
        switch (params.benefittype) {
          case this.pageCode.bettypecode[0].cmcode: // 적립금
            params.isepointdup = ''; // e포인트 중복사용여부
            params.comcpnidx = ''; // 쿠폰
            break;
          case this.pageCode.bettypecode[1].cmcode: // E포인트
            params.comcpnidx = ''; // 쿠폰
            break;
          case this.pageCode.bettypecode[2].cmcode: // 쿠폰
            params.isepointdup = ''; // e포인트 중복사용여부
            params.paypoint = 0; // 적립액
            break;
        }

        this.$http.post("/admin/promotion/checkevent/save", params)
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

    onInit() {
      this.setUserInfo();
      this.setTimeInfo();
      this.setCode();
      this.getCouponList();
    },

    // 유효성 체크
    checkValidation() {
      this.setEditorText();

      let msg = '';
      let checkResult = true;
      let valid = [
        {field: 'boardInfo.subject', type: 'I', name: '제목', required: true},
        {field: 'boardInfo.evinfo', type: 'I', name: '내용(PC)', required: true},
        {field: 'boardInfo.evinfomobile', type: 'I', name: '내용(모바일)', required: true},
        {field: 'boardInfo.winattendcnt', type: 'I', name: '출석수', required: true},
      ]

      // 혜택 구분 - 적립금
      if (this.boardInfo.benefittype === this.pageCode.bettypecode[0].cmcode) {
        valid.push({field: 'boardInfo.paypoint', type: 'I', name: '적립금', required: true});
      }

      // 혜택 구분 - E포인트
      if (this.boardInfo.benefittype === this.pageCode.bettypecode[1].cmcode) {
        valid.push({field: 'boardInfo.paypoint', type: 'I', name: '적립금', required: true});
      }

      // 혜택 구분 - 쿠폰폰
      if (this.boardInfo.benefittype === this.pageCode.bettypecode[2].cmcode) {
        valid.push({field: 'boardInfo.comcpnidx', type: 'I', name: '쿠폰', required: true});
      }

      // (1) 기본정보 검사
      msg = this.$util.validMsg(this.$data, valid);
      if (!this.$util.isNull(msg)) {
        checkResult = false;
        alert(msg);
        return;
      }

      // (2) 첨부 파일 검사
      if (util.isNull(this.files.pcimgFile)) {
        alert("대표이미지(PC)를 첨부해주세요.");
        checkResult = false;
        return;
      }
      if (util.isNull(this.files.mobileimgFile)) {
        alert("대표이미지(모바일)을 첨부해주세요.");
        checkResult = false;
        return;
      }

      return checkResult;
    },

    // codename array get
    getCmCode(codeArr) {
      return codeArr.map(obj => obj.cmcode);
    },

    // 에디터 내용 가져오기
    setEditorText() {
      if (this.$util.isNull(this.$refs.pcEditor.content)) {
        this.boardInfo.evinfo = ''
      } else {
        this.boardInfo.evinfo = this.$refs.pcEditor.content;
      }

      if (this.$util.isNull(this.$refs.mobileEditor.content)) {
        this.boardInfo.evinfomobile = ''
      } else {
        this.boardInfo.evinfomobile = this.$refs.mobileEditor.content;
      }
    },

    // 유저 정보 셋팅
    setUserInfo() {
      let userInfo = this.$util.getUser(this.$store.getters['CONSTANTS'].MANAGER_SESSION);
      this.boardInfo.userno = userInfo.no;
      this.boardInfo.writer = userInfo.name;
      this.boardInfo.reguserid = userInfo.id;
    },

    // 코드 체크값 셋팅
    setCode() {
      // 적용 채널
      this.checkObj.muappchTypeChecked = this.getCmCode(this.pageCode.muappchtypecode);
      // 회원 유형
      this.checkObj.mumemberTypeChecked = this.getCmCode(this.pageCode.mumembertypecode);
      // 회원 등급
      this.checkObj.mumemlvTypeChecked = this.getCmCode(this.pageCode.mumemlvtypecode);
      // 노출 여부
      this.boardInfo.disptype = this.pageCode.disptypecode[0].cmcode;
      // 혜택 구분
      this.boardInfo.benefittype = this.pageCode.bettypecode[0].cmcode;
    },

    // 검색 시간 데이터 셋팅
    setTimeInfo() {
      // 날짜 셋팅
      this.eventTimeInfo.startYYYYMMDD = this.$util.getDate('-');
      this.eventTimeInfo.startHH = '00';
      this.eventTimeInfo.startMM = '00';

      this.eventTimeInfo.toYYYYMMDD = this.$util.getDate('-');
      this.eventTimeInfo.toHH = '00';
      this.eventTimeInfo.toMM = '59';
    },

    // 쿠폰 리스트
    getCouponList() {
      this.$http.post("/admin/promotion/checkevent/coupon", {})
          .then(result => {
            if (result.statusCode === 200) {
              this.couponList = result.data.list;
              this.boardInfo.comcpnidx = this.couponList[0].idx;
            }
          })
          .catch(error => {
            this.$util.debug(error);
          });
    },

    // 첨부파일(탐색기 열기)
    fileAttach(fileTypeKey) {
      if (Array.isArray(this.$refs[fileTypeKey])) {
        this.$refs[fileTypeKey][0].click();
      } else {
        this.$refs[fileTypeKey].click();
      }
    },

    // 가져온 파일 세팅
    handleFileUpload(fileTypeKey, target) {
      // PC, 모바일 대표이미지
      let file = this.$refs[fileTypeKey];
      if (this.$util.isNull(file)) {
        return;
      }
      let fileType = ['image/png', 'image/jpeg', 'image/png'];
      if (!fileType.includes(file.files[0].type)) {
        alert('jpg, jpeg, png파일만 첨부 가능합니다.');
        file.value = null;
        this.files[fileTypeKey] = '';
        return false;
      }
      if (file.files[0].size > 10485760) {
        alert('파일 최대 크기는 10MB를 초과 할 수 없습니다.');
        file.value = null;
        this.files[fileTypeKey] = '';
        return false;
      }
      let fileObj = {
        file: file.files[0],
        iscreated: true
      }
      this.files[fileTypeKey] = fileObj;
      this.imgPreview[fileTypeKey] = URL.createObjectURL(fileObj.file);

      if (fileTypeKey === 'pcimgFile' && this.copyimgcheck) {
        this.setSameAsPcepreImg();
      }
      // 모바일 대표이미지 변경시 PC이미지와 동일 체크 해제
      if (fileTypeKey === 'mobileimgFile') {
        this.copyimgcheck = false;
      }
    },

    // 파일 삭제
    removeFile(fileTypeKey, index) {
      if (confirm('파일을 삭제 하시겠습니까?')) {
        this.files[fileTypeKey] = '';
        this.imgPreview[fileTypeKey] = '';
        this.$refs[fileTypeKey].value = null;

        // 모바일 대표이미지 변경시 PC이미지와 동일 체크 해제
        if (fileTypeKey === 'mobileimgFile') {
          this.copyimgcheck = false;
        }
      }
    },

    // PC 대표이미지와 동일하게 세팅
    setSameAsPcepreImg() {
      if (this.copyimgcheck) {
        this.files.mobileimgFile = this.files.pcimgFile;
        this.imgPreview.mobileimgFile = this.imgPreview.pcimgFile;
      } else {
        this.files.mobileimgFile = '';
        this.imgPreview.mobileimgFile = '';
      }
    },

    // 적용 채널 전체 체크
    checkAllMuAppch(value) {
      if (value) {
        // 적용 채널
        this.checkObj.muappchTypeChecked = this.getCmCode(this.pageCode.muappchtypecode);
      } else {
        this.checkObj.muappchTypeChecked = [];
      }
    },

    // 회원 유형 전체 체크
    checkAllMuMemer(value) {
      if (value) {
        // 적용 채널
        this.checkObj.mumemberTypeChecked = this.getCmCode(this.pageCode.mumembertypecode);
      } else {
        this.checkObj.mumemberTypeChecked = [];
      }
    },

    // 회원 등급 전체 체크
    checkAllMuMemLv(value) {
      if (value) {
        // 적용 채널
        this.checkObj.mumemlvTypeChecked = this.getCmCode(this.pageCode.mumemlvtypecode);
      } else {
        this.checkObj.mumemlvTypeChecked = [];
      }
    },
    ///////////////////////////////////////////////////////////////////////////////////////////

    ///////////////////////////////////외부, 콜백 메서드//////////////////////////////////////////
    // 진행기간 DatePicker 콜백 메서드
    getEventTimeDate(date) {
      this.boardInfo.evsttime = date.fromDate12;
      this.boardInfo.evedtime = date.toDate12;
    },
    ///////////////////////////////////////////////////////////////////////////////////////////
  },
  mounted() {
    // 권한 설정
    this.onInit();
  },
  watch: {
    // 적용 채널 체크 상태 검사
    'checkObj.muappchTypeChecked'(value) {
      if (value.length < this.pageCode.muappchtypecode.length) {
        this.checkObj.isallmuappch = 'F';
      } else {
        this.checkObj.isallmuappch = 'T';
      }
      this.boardInfo.muappchtype = this.checkObj.muappchTypeChecked.join();
    },

    // 회원 유형 체크 상태 검사
    'checkObj.mumemberTypeChecked'(value) {
      if (value.length < this.pageCode.mumembertypecode.length) {
        this.checkObj.isallmumember = 'F';
      } else {
        this.checkObj.isallmumember = 'T';
      }
      this.boardInfo.mumembertype = this.checkObj.mumemberTypeChecked.join();
    },

    // 회원 등급 체크 상태 검사
    'checkObj.mumemlvTypeChecked'(value) {
      if (value.length < this.pageCode.mumemlvtypecode.length) {
        this.checkObj.isallmumemlv = 'F';
      } else {
        this.checkObj.isallmumemlv = 'T';
      }
      this.boardInfo.mumemlvtype = this.checkObj.mumemlvTypeChecked.join();
    },

    // 댓글이 없을 경우
    'boardInfo.iscomment'(value) {
      if (value === 'F') {
        this.boardInfo.isseccomment = 'F'
      }
    },

    // 개인정보동의
    isInfo(value) {
      if (!value) {
        this.boardInfo.ismktagree = "F";
        this.boardInfo.isperagree = "F";
      }
    },
  },
  computed: {
    // 총 출석 가능일 수
    getEventTimeDateCount() {
      return this.$util.getDateDiff(this.boardInfo.evsttime, this.boardInfo.evedtime);
    },

    // 총 혜택지급 가능 횟수
    getBenefitCount() {
      if (this.boardInfo.iswagestop === 'T') {
        return 1;
      }

      let count = this.boardInfo.winattendcnt * 1;

      // 공백이거나 0이면 리턴
      if (count === 0 || this.$util.isNull(count)) {
        return 0;
      }
      let day = this.$util.getDateDiff(this.boardInfo.evsttime, this.boardInfo.evedtime)
      return isNaN(day / count) ? 0 : Math.floor(day / count);
    }
  }
}
</script>
