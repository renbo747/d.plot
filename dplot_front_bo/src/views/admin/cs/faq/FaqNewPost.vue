<template>
  <!-- 자주 묻는 질문 등록 팝업 -->
  <div id="modal-wrap" class="modal" style="display: block;">
    <div class="modal-content" style="width: 1200px;">
      <div class="pop-header">
        <h2>자주 묻는 질문 등록</h2>
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
              <dt>분류</dt>
              <dd>
                <select v-model="boardInfo.faqtype" ref="faqType">
                  <option v-for="(row, i) in commonCode.faqtype" :key="i" :value="row.cmcode">
                    {{ row.codename }}
                  </option>
                </select>
              </dd>
            </dl>
            <dl>
              <dt>노출대상</dt>
              <dd>
                <div class="check-wrap">
                  <input type="checkbox" id="rd1010" true-value="T" false-value="F" v-model="isallmumember"
                         @click="checkAllMuMemer($event.target.checked)">
                  <label for="rd1010">전체</label>
                </div>
                <div class="check-wrap" v-for="(row, i) in commonCode.mumembertype" :key="i">
                  <input type="checkbox" :id="'murd12' + i" :value="row.cmcode" v-model="mumemberTypeChecked">
                  <label :for="'murd12' + i">{{ row.codename }}</label>
                </div>
              </dd>
            </dl>
            <dl>
              <dt>사용여부</dt>
              <dd>
                <div class="radio_wrap wide">
                  <input type="radio" name="rad" id="rd11" value="F" v-model="boardInfo.istrash"/><label for="rd11">사용</label>
                  <input type="radio" name="rad" id="rd12" value="T" v-model="boardInfo.istrash"/><label for="rd12">미사용</label>
                </div>
              </dd>
            </dl>
            <dl>
              <dt>메인노출</dt>
              <dd>
                <div class="radio_wrap wide">
                  <input type="radio" name="rad2" id="rd113" value="T" v-model="boardInfo.isfaqmain"/><label for="rd113">노출</label>
                  <input type="radio" name="rad2" id="rd123" value="F" v-model="boardInfo.isfaqmain"/><label for="rd123">미노출</label>
                </div>
              </dd>
            </dl>
            <dl>
              <dt>제목</dt>
              <dd><input type="text" style="width: 100%;" v-model="boardInfo.subject" ref="subjectObj"></dd>
            </dl>
          </div>
        </div>
        <div class="boxing">
          <CommonEditor ref="editor"/>
        </div>
        <div class="btn-group">
          <button type="button" class="btn big blue" @click="goSave">저장</button>
          <button type="button" class="btn big darkgray" @click="onClose">취소</button>
        </div>
      </div>
    </div>
  </div>
  <!-- /자주 묻는 질문 등록 팝업-->
</template>

<script>
import CommonEditor from "@views.admin/common/CommonEditor";

export default {
  name: "admin.cs.faq.newpost",
  components: {CommonEditor},
  props: {
    commonCode: {
      faqtype: {},
      mumembertype: {},
    },
  },
  data() {
    return {
      boardInfo: {
        subject: "",        // 제목
        writer: "",         // 회원 이름
        content: "",        // 내용
        ip: "",             // 작성자_ip
        hits: 0,            // 조회수
        istrash: "F",       // 사용 여부
        isessnotice: "F",   // 필독 공지 여부
        ispapopnotice: "F", // 입점사 팝업 공지 여부
        isallagree: "",     // 전체 동의 여부
        postst: "",         // 전체동의여부
        faqtype: "",        // 분류
        mumembertype: [],   // 노출 대상
        sortnum: '',        // 정렬순서
        isfaqmain: 'T',
      },
      isallmumember: 'T',
      mumemberTypeChecked: [],         // 회원 유형
      isRead: false,
      isWrite: false,
    }
  },
  mounted() {
    this.$http.post('/admin/common/pageAuth/check', {url : this.$options.name}).then(result => {

        this.isRead = (result.data.isread === 'T');
        this.isWrite = (result.data.iswrite === 'T');
  
        if(this.isRead){
            // 유저 정보 셋팅
            let userInfo = this.$util.getUser(this.$store.getters['CONSTANTS'].MANAGER_SESSION);
            this.boardInfo.writer = userInfo.name;
            this.boardInfo.faqtype = this.commonCode.faqtype[0].cmcode;
            this.boardInfo.regdate = this.$util.getDate('-');
            this.checkAllMuMemer(true);
        } else {
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
    });
  },
  methods: {

    ///////////////////////// 내부 사용 메서드 /////////////////////////

    onClose(isreload) {
      if(typeof(isreload) === 'boolean' && isreload) {
        this.$parent.goCloseNewPostPopup(true);
      } else {
        this.$parent.goCloseNewPostPopup();
      }
      this.boardInfo = [];
      this.$refs.editor.content = "";
    },

    goSave() {
      if (this.boardInfo.subject === "") {
        alert("제목을 입력해주세요.");
        this.$refs.subjectObj.focus();
        return;
      }

      if (this.boardInfo.faqtype === "") {
        alert("분류를 선택해주세요.");
        this.$refs.faqType.focus();
        return;
      }

      // 에디터 텍스트
      this.boardInfo.content = this.$refs.editor.content;

      if(this.$util.isNull(this.boardInfo.content)) {
        alert("내용을 입력해주세요.");
        return;
      }

      let params = this.boardInfo;

      // // checkMuMemberType 공백 데이터 제거, 복사
      // let copyCheckMuMemberType = this.checkMuMemberType.slice();
      //
      // // 전체 체크시 첫번째 값 삭제
      // if (this.isTypeCheckAll) {
      //   copyCheckMuMemberType.shift();
      // }
      //
      // this.boardInfo.mumembertype = copyCheckMuMemberType.toString();
      if(params.isfaqmain === 'T' && params.istrash === 'F') {
        let param = {
          isloading: false,
          istrash: params.istrash,
          isfaqmain: params.isfaqmain,
        }

        this.$http.post("/admin/cs/faq/check", param)
        .then(result => {
            if (result.statusCode == 200) {
                params.msg = result.data.msg;
                this.checkSave(params);
            }
        })
        .catch(error => {
            this.$util.debug(error);
        })
      } else {
        params.msg = "저장 하시겠습니까?";
        this.checkSave(params);
      }
    },
    checkSave(params) {
      if (confirm(params.msg)) {
        this.$http.post('/admin/cs/faq/save', params)
        .then(result => {
          if (result.statusCode === 200) {
            alert("저장이 완료되었습니다.");
            this.onClose(true);
          } else {
            alert("저장이 실패했습니다.");
          }
        })
        .catch(error => {
          this.$util.debug(error);
        })
      }
    },
    // 회원 유형 전체 체크
    checkAllMuMemer(value) {
      if (value) {
        // 적용 채널
        this.mumemberTypeChecked = this.getCmCode(this.commonCode.mumembertype);
      } else {
        this.mumemberTypeChecked = [];
      }
    },

    // codename array get
    getCmCode(codeArr) {
      return codeArr.map(obj => obj.cmcode);
    },

    /////////////////////////////////////////////////////////////////
  },
  watch: {
    // 회원 유형 체크 상태 검사
    mumemberTypeChecked(value) {
      if (value.length < this.commonCode.mumembertype.length) {
        this.isallmumember = 'F';
      } else {
        this.isallmumember = 'T';
      }
      this.boardInfo.mumembertype = this.mumemberTypeChecked.join();
    },
  }
}
</script>
