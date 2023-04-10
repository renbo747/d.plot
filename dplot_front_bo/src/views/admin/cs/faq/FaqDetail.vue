<template>
  <!-- 자주묻는질문 상세 팝업 -->
  <div id="modal-wrap" class="modal" style="display: block;">
    <div class="modal-content" style="width: 1200px;">
      <div class="pop-header">
        <h2>자주 묻는 질문 상세</h2>
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
                  <input type="radio" name="rad" id="rd11" value="F" v-model="boardInfo.istrash"/><label
                    for="rd11">사용</label>
                  <input type="radio" name="rad" id="rd12" value="T" v-model="boardInfo.istrash"/><label
                    for="rd12">미사용</label>
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
              <dd><input type="text" style="width: 100%;" v-model="boardInfo.subject"></dd>
            </dl>
          </div>
        </div>
        <div class="boxing">
          <CommonEditor ref="editor" :style-object="{height: 300 + 'px'}"/>
        </div>
        <div class="btn-group">
          <button type="button" class="btn big blue" @click="goSave" v-if="isWrite">저장</button>
          <button type="button" class="btn big darkgray" @click="onClose">닫기</button>
        </div>
      </div>
    </div>
  </div>
  <!-- /자주묻는질문 상세 팝업-->
</template>

<script>
import CommonEditor from "@views.admin/common/CommonEditor";

export default {
  name: "admin.cs.faq.detail",
  components: {
    CommonEditor
  },
  props: {
    commonCode: {
      faqtype: {},
      mumembertype: {},
    },
    idx: Number,
  },
  data() {
    return {
      boardInfo: [],
      isallmumember: 'T',
      mumemberTypeChecked: [],         // 회원 유형
      isWrite: false,
      isRead: false,
    }
  },
  methods: {

    ///////////////////////// 내부 사용 메서드 /////////////////////////
    onOpen() {
      let params = {idx: this.idx}
      this.$http.post("/admin/cs/faq/detail", params)
          .then(result => {
            let data = result.data;
            this.boardInfo = data;
            this.mumemberTypeChecked = this.boardInfo.mumembertype;
            this.$refs.editor.content = this.boardInfo.content;
          })
          .catch(error => {
            this.$util.debug(error);
          })
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

    goDelete() {
      let params = {
        idxList: [this.boardInfo.idx],
        istrash: 'T'
      }

      if (confirm("삭제 하시겠습니까?")) {
        this.$http.post("/admin/cs/faq/update", params)
            .then(result => {
              if (result.statusCode === 200) {
                alert("삭제가 완료되었습니다.");
                this.onClose();
              } else {
                alert("삭제에 실패했습니다.");
              }
            })
            .catch(error => {
              this.$util.debug(error);
            })
      }
    },

    // 닫기
    onClose(isreload) {
      if(typeof(isreload) === 'boolean' && isreload) {
        this.$parent.goCloseDetailPopup(true);
      } else { 
        this.$parent.goCloseDetailPopup();
      }
      this.boardInfo = [];
      this.$refs.editor.content = "";
    },

    // 저장
    goSave() {
      if (this.boardInfo.subject === "") {
        alert("제목을 입력해주세요.");
        this.$refs.subjectObj.focus();
        return;
      }

      let params = this.boardInfo;

      params.isloading = false;

      this.$http.post("/admin/cs/faq/check", params)
      .then(result => {
          if (result.statusCode == 200) {
              let msg = result.data.msg;
              if (confirm(msg)) {
                // update시 "idxList" 형태로 controller에서 사용하므로
                this.boardInfo.idxList = [this.boardInfo.idx];

                // 에디터 텍스트 데이터 저장
                this.boardInfo.content = this.$refs.editor.content;

                // // checkMuMemberType 공백 데이터 제거, 복사
                // let copyCheckMuMemberType = this.checkMuMemberType.slice();
                //
                // // 전체 체크시 첫번째 값 삭제
                // if (this.isTypeCheckAll) {
                //   copyCheckMuMemberType.shift();
                // }
                //
                // this.boardInfo.mumembertype = copyCheckMuMemberType.toString();

                // 데이터 통신
                this.$http.post("/admin/cs/faq/update", this.boardInfo)
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
      })
      .catch(error => {
          this.$util.debug(error);
      })    
    },
    /////////////////////////////////////////////////////////////////

    //////////////////////// 외부, 콜백 메서드 /////////////////////////
    /////////////////////////////////////////////////////////////////

    ///////////////////////// 팝업 메서드 /////////////////////////////
    /////////////////////////////////////////////////////////////////

  },
  mounted() {
    // 권한 설정
    this.$http.post('/admin/common/pageAuth/check', {url : this.$options.name}).then(result => {
        this.isRead = (result.data.isread === 'T');
        this.isWrite = (result.data.iswrite === 'T');
  
        if(this.isRead){
          this.onOpen();
        }else{
          alert('페이지 접근 권한이 없습니다.');
          this.info = this.$options.data().info
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
