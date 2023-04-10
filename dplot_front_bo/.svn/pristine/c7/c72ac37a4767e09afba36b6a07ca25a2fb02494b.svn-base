<template>
  <!-- 이벤트등록(일반형) 팝업 -->
  <div id="modal-wrap" class="modal" style="display: block;">
    <div class="modal-content" style="width: 1400px;">
      <div class="pop-header">
        <h2>이벤트 등록</h2>
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
              <dt>이벤트 설명</dt>
              <dd><input type="text" style="width: 100%" placeholder="이벤트 설명" v-model="boardInfo.evdesc"/></dd>
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
              <th>발표일<i class="essential"></i></th>
              <td>
                <CommonDatePickerFromTo
                    :toYYYYMMDD="pubTimeInfo.toYYYYMMDD"
                    :toHH="pubTimeInfo.toHH"
                    :toMM="pubTimeInfo.toMM"
                    :use-from="false"
                    :use-to="true"
                    @getDate="getEventPubTimeDate"
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
                <div class="check-wrap" v-for="(row, i) in pageCode.muappchtype" :key="i">
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
                <div class="check-wrap" v-for="(row, i) in pageCode.mumembertype" :key="i">
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
                <div class="check-wrap" v-for="(row, i) in pageCode.mumemlvtype" :key="i">
                  <input type="checkbox" :id="'group3' + i" :value="row.cmcode" v-model="checkObj.mumemlvTypeChecked">
                  <label :for="'group3' + i">{{ row.codename }}</label>
                </div>
              </td>
            </tr>
            <tr>
              <th>노출여부<i class="essential"></i></th>
              <td>
                <div class="radio_wrap wide3">
                  <div v-for="(row, i) in pageCode.disptype" :key="i">
                    <input type="radio" :id="'group4' + i" :value="row.cmcode" v-model="boardInfo.disptype">
                    <label :for="'group4' + i">{{ row.codename }}</label>
                  </div>
                </div>
              </td>
            </tr>
            <tr>
              <th>옵션</th>
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
                <div class="dpb">
                  <input type="checkbox" id="group62" true-value="T" false-value="F" v-model="boardInfo.isenter"/><label
                    for="group62">응모</label>
                  <span v-show="boardInfo.isenter === 'T'" class="txt-gray dpib"
                        style="width: 40px; text-align: center;">중복</span>
                  <div v-show="boardInfo.isenter === 'T'" class="radio_wrap dpib ml3">
                    <input type="radio" name="group062" id="group621" value='F' v-model="boardInfo.isdupenter"/><label
                      for="group621">미허용</label>
                    <input type="radio" name="group062" id="group622" value='T' v-model="boardInfo.isdupenter"/><label
                      for="group622">허용</label>
                  </div>
                  <div class="dpib ml3" v-show="boardInfo.isdupenter === 'T'">
                    <select v-model="boardInfo.dupentertype">
                      <option value="">선택</option>
                      <option v-for="(row, i) in pageCode.dupentertype" :key="i" :value="row.cmcode">
                        {{ row.codename }}
                      </option>
                    </select>
                    <input type="text" class="ml3 center" style="width: 50px" v-model="boardInfo.dupentercnt"/>
                    <span>회까지 허용</span>
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
                  <input type="checkbox" id="copy-text" @click="copyByContent($event.target.checked)"><label
                    for="copy-text">PC 내용을 복사</label>
                </div>
                <div class="mt10">
                  <CommonEditor ref="mobileEditor"/>
                </div>
              </td>
            </tr>
            <tr>
              <th>연관상품목록타이틀<i class="essential"></i></th>
              <td><input type="text" style="width: 100%" placeholder="연관상품 목록 타이틀" v-model="boardInfo.goodstitle"/></td>
            </tr>
            <tr>
              <th>연관상품(선택)</th>
              <td>
                <div class="caption-group clearfix">
                  <div class="total-group fl">
                    <span class="total">적용대상 상품목록</span>
                  </div>
                  <div class="btn-group fr">
                    <button type="button" class="btn blue-line" @click="openGoodsAdditionPopup">상품추가</button>
                    <button type="button" class="btn red-line" @click="removeGoodsAddition">삭제</button>
                  </div>
                </div>
                <div class="scroll-y" style="width: 100%; max-height: 350px; margin-bottom: 0;">
                  <table cellpadding="0" cellspacing="0" class="data-tb align-c">
                    <colgroup>
                      <col width="3%"/><!-- checkbox -->
                      <col width="4%"/><!-- No -->
                      <col width="6%"/><!-- 판매구분 -->
                      <col width="10%"/><!-- 파트너사명 -->
                      <col width="8%"/><!-- 상품코드 -->
                      <col width="62px"/><!-- 이미지 -->
                      <col width=""/><!-- 상품명 -->
                      <col width="7%"/><!-- 판매가 -->
                    </colgroup>
                    <thead>
                    <tr>
                      <th><input type="checkbox" id="chkall" @click="checkAll($event.target.checked)"/></th>
                      <th>No</th>
                      <th>판매구분</th>
                      <th>파트너사명</th>
                      <th>상품코드</th>
                      <th colspan="2">상품명</th>
                      <th>판매가</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr v-for="(row, i) in productList" :key="i">
                      <td><input type="checkbox" id="chk01" :value="i" v-model="moveData.targetIdx"/></td>
                      <td>{{ $util.lpad(i+1,2,'0') }}</td>
                      <td>{{ row.ispbgoodsname }}</td>
                      <td>{{ row.dealername }}</td>
                      <td>{{ row.goodscode }}</td>
                      <td>
                        <div class="img-thumb size60" :class="{'no-image': $util.isNull(row.fullpath)}">
                          <img :src="row.fullpath" v-if="!$util.isNull(row.fullpath)" alt="사진">
                        </div>
                      </td>
                      <td class="left no-left">
                        <span class="small-txt">{{ row.fullcategoryname }}</span>
                        <p class="mg0">{{ row.goodsname }}</p>
                      </td>
                      <td class="right">{{ row.price }}</td>
                    </tr>
                    </tbody>
                  </table>
                </div>
                <div class="bottom-group">
                  <CommonArraySort
                      :move-data="moveData"
                      :list-data="productList"
                      key-name="goodsno"
                      :is-active-save-btn="false"
                  />
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
  <!-- /이벤트등록(일반형) 팝업 -->
</template>

<script>
import CommonDatePickerFromTo from "@views.admin/common/CommonDatePickerFromTo";
import CommonEditor from "@views.admin/common/CommonEditor";
import CommonAddGoodsPopup from "@views.admin/common/popup/CommonAddGoodsPopup.vue";
import CommonArraySort from "@views.admin/common/CommonArraySort";
import util from "@js/util";

export default {
  name: "admin.promotion.event.manage.newpost",
  components: {CommonArraySort, CommonEditor, CommonDatePickerFromTo},
  props: {
    pageCode: {                 // 페이지 코드
      // 노출 구분
      disptype: {},
      // 이벤트 구분
      eventtype: {},
      // 적용 채널
      muappchtype: {},
      // 대상 회원
      mumembertype: {},
      // 대상 회원 등급
      mumemlvtype: {},
      // 중복 코드
      dupentertype: {},
    },
  },
  data() {
    return {
      isInfo: false,                      // 개인정보동의
      copyimgcheck: false,                // 이미지 복사
      checkObj: {
        isallmuappch: 'T',
        muappchTypeChecked: [],          // 적용 채널
        isallmumember: 'T',
        mumemberTypeChecked: [],         // 회원 유형
        isallmumemlv: 'T',
        mumemlvTypeChecked: [],          // 회원 등급
      },
      boardInfo: {
        istrash: 'F',               // 사용여부
        subject: '',                // 제목
        evdesc: '',                 // 이벤트 설명
        evsttime: '',               // 이벤트 시작일
        evedtime: '',               // 이벤트 종료일
        pubtime: '',                // 이벤트 발표일
        muappchtype: '',            // 적용 채널
        mumembertype: '',           // 회원 유형
        mumemlvtype: '',            // 회원 등급
        disptype: '',               // 노출 여부
        iscomment: 'T',             // 댓글 사용 여부
        isseccomment: 'F',          // 비밀글 사용 여부
        isenter: 'T',               // 응모 사용 여부
        isdupenter: 'F',            // 중복 응모 여부
        isperagree: 'F',            // 개인정보동의여부
        ismktagree: 'F',            // 마케팅 정보 동의 여부
        userno: '',                 // 작성자 번호
        reguserid: '',              // 작성자 아이디
        writer: '',                 // 작성자 이름
        iscontattend: 'F',          // N.N : 연속출석인정여부
        winattendcnt: 0,            // N.N : 당첨출석수
        dupentertype: '',           // 중복응모허용타입
        dupentercnt: 0,            // 중복응모횟수
        evinfo: '',                 // 내용
        evinfomobile: '',           // 모바일 내용
        readcnt: 0,                // 조횟수
        goodstitle: ''              // 연관상품목록타이틀
      },
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
      pubTimeInfo: {                //발표일
        toYYYYMMDD: '',
        toHH: '',
        toMM: '',
      },
      moveData: {
        moveValue: '',              // 움직일 값
        targetIdx: [],              // 대상 위치
        code: 'U',                  // 위, 아래 코드
        isSuccess: false            // 저장 성공 여부 (** 중요)
      },
      productList: [],              // 연관상품
    }
  },
  methods: {
    ///////////////////////////////////내부 사용 메서드////////////////////////////////////////
    // 초기화
    onInit() {
      this.setUserInfo();
      this.setTimeInfo();
      this.setCode();
    },

    // codename array get
    getCmCode(codeArr) {
      return codeArr.map(obj => obj.cmcode);
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
      this.checkObj.muappchTypeChecked = this.getCmCode(this.pageCode.muappchtype);
      // 회원 유형
      this.checkObj.mumemberTypeChecked = this.getCmCode(this.pageCode.mumembertype);
      // 회원 등급
      this.checkObj.mumemlvTypeChecked = this.getCmCode(this.pageCode.mumemlvtype);
      // 노출 여부
      this.boardInfo.disptype = this.pageCode.disptype[0].cmcode;
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

      this.pubTimeInfo.toYYYYMMDD = this.$util.getAddDate(this.$util.getDate(''), 1, '-');
      this.pubTimeInfo.toHH = '00';
      this.pubTimeInfo.toMM = '00';
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

    // 진행기간 DatePicker 콜백 메서드
    getEventTimeDate(date) {
      this.boardInfo.evsttime = date.fromDate12;
      this.boardInfo.evedtime = date.toDate12;
    },

    // 발표기간 DatePicker 콜백 메서드
    getEventPubTimeDate(date) {
      this.boardInfo.pubtime = date.toDate12;
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

    // PC 내용 복사
    copyByContent(value) {
      if (value) {
        this.$refs.mobileEditor.content = this.$refs.pcEditor.content;
      } else {
        this.$refs.mobileEditor.content = '';
      }
    },

    // 추가상품 팝업 오픈
    openGoodsAdditionPopup() {
      this.$eventBus.$emit('modalShow', CommonAddGoodsPopup, null,
          (result) => {
            this.addGoodsAddition(result.list);
          }
      );
    },

    // 상품 추가
    addGoodsAddition(list) {
      list.forEach(obj => {
        // 중복 제거
        let isExist = this.productList.find(element => element.goodsno === obj.goodsno);
        if (typeof isExist === 'undefined') {
          this.productList.push(obj);
        }
      });
    },

    // 상품 삭제
    removeGoodsAddition() {
      let idx = this.moveData.targetIdx.slice();
      idx.forEach(obj => {
        util.removeArrOnce(this.moveData.targetIdx, obj);
        delete this.productList[obj];
      });

      this.productList = this.productList.filter(obj => !util.isNull(obj));
    },

    // 상품 전체 체크
    checkAll(check) {
      if (check) {
        this.moveData.targetIdx = [];

        let size = this.productList.length;
        for (let i = 0; i < size; i++) {
          this.moveData.targetIdx.push(i);
        }
      } else {
        this.moveData.targetIdx = [];
      }
    },

    // 적용 채널 전체 체크
    checkAllMuAppch(value) {
      if (value) {
        // 적용 채널
        this.checkObj.muappchTypeChecked = this.getCmCode(this.pageCode.muappchtype);
      } else {
        this.checkObj.muappchTypeChecked = [];
      }
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
      this.setEditorText();

      let msg = '';
      let checkResult = true;
      let valid = [
        {field: 'boardInfo.subject', type: 'I', name: '제목', required: true},
        {field: 'boardInfo.evinfo', type: 'I', name: '내용(PC)', required: true},
        {field: 'boardInfo.evinfomobile', type: 'I', name: '내용(모바일)', required: true},
        {field: 'boardInfo.goodstitle', type: 'I', name: '연관상품목록타이틀', required: true},
      ]

      // (1) 기본 정보 검사
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

    // 저장
    goSave() {
      // 유효성 체크
      if (!this.checkValidation()) {
        return;
      }

      if (confirm("저장 하시겠습니까?")) {
        let params = this.boardInfo;

        // 이미지 파일 셋팅
        let files = [];

        // 이미지 첨부
        files.push({key: 'pcimgFile', file: this.files.pcimgFile.file});
        files.push({key: 'mobileimgFile', file: this.files.mobileimgFile.file});
        params.files = files;

        // 저장시 응모를 하지 않을 경우 중복여부,중복횟수를 삭제
        if (this.boardInfo.isenter === 'F') {
          this.boardInfo.dupentercnt = null
          this.boardInfo.dupentertype = null
        }

        // 이벤트 상품
        params.productlist = this.productList;

        this.$http.post("/admin/promotion/event/save", params)
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
    }
    ///////////////////////////////////////////////////////////////////////////////////////////
  },
  mounted() {
    // 초기화
    this.onInit();
  },
  watch: {
    // 적용 채널 체크 상태 검사
    'checkObj.muappchTypeChecked'(value) {
      if (value.length < this.pageCode.muappchtype.length) {
        this.checkObj.isallmuappch = 'F';
      } else {
        this.checkObj.isallmuappch = 'T';
      }
      this.boardInfo.muappchtype = this.checkObj.muappchTypeChecked.join();
    },

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

    // 댓글이 없을 경우
    'boardInfo.iscomment'(value) {
      if (value === 'F') {
        this.boardInfo.isseccomment = 'F'
      }
    },

    // 응모가 없을 경우
    'boardInfo.isenter'(value) {
      if (value === 'F') {
        this.boardInfo.dupentertype = this.pageCode.dupentertype[0].cmcode;
        this.boardInfo.dupentercnt = 0;
        this.boardInfo.isdupenter = 'F';
      }
    },

    // 개인정보동의
    isInfo(value) {
      if (!value) {
        this.boardInfo.ismktagree = "F";
        this.boardInfo.isperagree = "F";
      }
    }
  },
}
</script>
