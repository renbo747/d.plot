<template>
  <div id="modal-wrap" class="modal" style="display: block;">
    <div class="modal-content" style="width: 480px;">
      <div class="pop-header">
        <h2>아이디/비밀번호 찾기</h2>
        <button type="button" class="pop-close" @click="$modal.hide('commonModal');"></button>
      </div>
      <div class="pop-body">
        <div class="tab-group mt0">
          <ul><!-- 활성화탭 li에 class="active" 추가 -->
            <li v-bind:class="{ active: activeTab === 'ID' }"><a @click="activeTabChange('ID')">아이디 찾기</a></li>
            <li v-bind:class="{ active: activeTab === 'PW' }"><a @click="activeTabChange('PW')">비밀번호 찾기</a></li>
          </ul>
        </div>
        <div class="tab-area" v-if="activeTab === 'ID'">
          <div class="title">입점 시 등록되어 있는 제휴 담당자 휴대폰으로 인증문자가 발송됩니다.</div>
          <table cellpadding="0" cellspacing="0" class="gray-tb mt10">
            <colgroup>
              <col width="120px">
              <col width="">
            </colgroup>
            <tbody>
            <tr>
              <th>제휴 담당자 명</th>
              <td><input type="text" v-model="info.name" style="width: 194px;" :readonly="isAuthComplete"></td>
            </tr>
            <tr>
              <th>휴대폰</th>
              <td>
                <input type="text" v-model="info.phone" style="width: 194px;" :readonly="isAuthComplete">
                <button type="button" class="btn blue-line ml3" @click="userAuthCheck">본인 인증</button>
              </td>
            </tr>
            </tbody>
          </table>
          <div class="btn-group mb10">
            <button type="button" class="btn big blue" @click="idFind">아이디 찾기</button>
          </div>
          <!-- 아이디 show -->
          <div v-if="isShowIdFindArea">
            <div class="blue-box mt20 text-center">검색하신 {{ info.name }}님의 파트너사 아이디 입니다.
              <ul>
                <li v-for="(row, index) in findList" v-bind:key="index"><strong>{{ row.userid }}</strong></li>
              </ul>
            </div>
          <div class="btn-group mt10">
            <button type="button" class="btn big darkgray" @click="$modal.hide('commonModal');">닫기</button>
          </div>
          </div>
        </div>

        <div class="tab-area" v-if="activeTab === 'PW'">
          <div class="title">입점 시 등록되어 있는 제휴 담당자 휴대폰으로 인증문자가 발송됩니다.</div>
          <table cellpadding="0" cellspacing="0" class="gray-tb mt10">
            <colgroup>
              <col width="120px">
              <col width="">
            </colgroup>
            <tbody>
            <tr>
              <th>아이디</th>
              <td><input type="text" v-model="info.id" ref="id" style="width: 194px;"></td>
            </tr>
            <tr>
              <th>제휴 담당자 명</th>
              <td><input type="text" v-model="info.name" style="width: 194px;" :readonly="isAuthComplete"></td>
            </tr>
            <tr>
              <th>휴대폰</th>
              <td>
                <input type="text" v-model="info.phone" style="width: 194px;" :readonly="isAuthComplete">
                <button type="button" class="btn blue-line ml3" @click="userAuthCheck">본인 인증</button>
              </td>
            </tr>
            </tbody>
          </table>
          <div class="btn-group mb10">
            <button type="button" class="btn big blue" @click="passwordFind">비밀번호 찾기</button>
          </div>

          <!-- 비밀번호 설정 show -->
          <div v-if="isShowPwInputArea">
            <div class="title mt20">새로운 비밀번호를 설정하세요!</div>
            <table cellpadding="0" cellspacing="0" class="gray-tb mt10">
              <colgroup>
                <col width="120px">
                <col width="">
              </colgroup>
              <tbody>
              <tr>
                <th>새 비밀번호</th>
                <td>
                  <input type="password" v-model="info.userPw" style="width: 194px;" ref="userPw" @keyup="pwCheck" placeholder="비밀번호는 6~15자 이내">
                  <span :class="isPwCheck ? 'txt-blue': 'txt-red'"><i :class="isPwCheck ? 'icon-alert-check' : '' "></i>{{isPwCheck ? '사용가능' : '오류! 비밀번호는 6~15자 이내로 입력'}}</span>
                </td>
              </tr>
              <tr>
                <th>새 비밀번호 확인</th>
                <td>
                  <input type="password" v-model="userPwConfirm" placeholder="" style="width: 194px;" ref="userPwConfirm" @keyup="pwConfirmCheck">
                  <span :class="isPwConfirmCheck ? 'txt-blue': 'txt-red'"><i :class="isPwConfirmCheck ? 'icon-alert-check': 'icon-alert2'"></i>{{ isPwConfirmCheck ? '비밀번호 일치' : '비밀번호 입력오류!' }}</span>
                </td>
              </tr>
              </tbody>
            </table>
            <div class="btn-group mt10">
              <button type="button" class="btn big blue" @click="savePassword">비밀번호 저장</button>
              <button type="button" class="btn big darkgray" @click="$modal.hide('commonModal');">닫기</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "PartnersFindIdPassword",
  props: {
    modalComp: Object,
    params: Object,
    callbackFn: Function
  },
  data() {
    return {
      findList : [],
      info : {
        id : '',
        name : '',
        phone : '',
        ci :'',
        userPw : '',
        chargetype : 'CHA001'
      },
      activeTab : 'ID',
      isShowPwInputArea : false,
      isShowIdFindArea : false,
      isAuthComplete : false,
      userPwConfirm: '',
      isPwCheck: false,
      isPwConfirmCheck: false,
    }
  },
  mounted() {

  },
  methods : {
    activeTabChange(type){
      this.info.id = '';
      this.info.name = '';
      this.info.phone = '';
      this.info.ci = '';
      this.info.userPw = '';
      this.activeTab = type;
      this.isShowPwInputArea = false;
      this.isShowIdFindArea = false;
      this.isAuthComplete = false;
      this.userPwConfirm = '';
      this.isPwCheck = false;
      this.isPwConfirmCheck = false;
    },
    userAuthCheck: function(){
      window.popupCallback = this.certClose;
      this.certPopupObj = window.open('/common/partnership/kmc/request?code='+process.env.VUE_APP_KMC_CERT_PARTNERS+"&name="+this.info.name+"&phone="+this.info.phone, "KMCISWindow", "width=425, height=550, resizable=0, scrollbars=no, status=0, titlebar=0, toolbar=0, left=435, top=250");
    },
    certClose(certData){
      if(certData != null) {
        if (typeof certData.ci !== 'undefined') {
          this.info.ci = certData.ci;
          this.info.name = certData.name;
          this.info.phone = certData.phoneno;
          this.isAuthComplete = true;

          window.popupCallback = null;
        }
      }
    },
    passwordFind(){
      if(this.isAuthComplete !== true){
        alert('본인인증 완료 후 다시 시도 바랍니다.')
        return false;
      }

      if(typeof this.info.id === 'undefined' || this.info.id === ''){
        alert('아이디를 입력 바랍니다.');
        this.$refs['id'].focus();
        this.$refs['id'].scrollIntoView();
        return false;
      }

      this.isShowPwInputArea = true;

    },
    idFind(){
      if(this.isAuthComplete !== true){
        alert('본인인증 완료 후 다시 시도 바랍니다.')
        return false;
      }

      this.$http.post('/partnership/find/id', this.info).then(result => {
        if(result.data.code === 'success'){
          this.findList = result.data.list;
          this.isShowIdFindArea = true;
        } else {
          alert(result.data.msg);
        }
      }).catch(error => {
        this.$util.debug(error);
      })
    },
    pwCheck: function(){
      this.isPwCheck = (this.info.userPw.length >= 6 && this.info.userPw.length <= 15);
      if(this.isPwCheck){
        this.isPwConfirmCheck = this.info.userPw === this.userPwConfirm;
      }
    },
    pwConfirmCheck: function(){
      this.isPwConfirmCheck = (this.userPwConfirm.length >= 6 && this.userPwConfirm.length <= 15 && this.info.userPw === this.userPwConfirm);
    },
    savePassword(){
      if(this.isPwCheck && this.isPwConfirmCheck){
        this.$http.post('/partnership/password/save', this.info).then(result => {
          if(result.data.code === 'success'){
            alert(result.data.msg);
            this.$modal.hide('commonModal');
          } else {
            alert(result.data.msg);
          }
        }).catch(error => {
          this.$util.debug(error);
        })
      } else {
        alert('패스워드가 올바르지 않습니다.');
        return false;
      }
    }
  }
}
</script>

<style scoped />