<template>
  <!-- 컨텐츠 영역 -->
  <div class="content partner m-leftmenu"> <!-- 파트너사 관리자는 partner 클래스 추가 -->
    <div class="page-info clearfix">
      <div class="title">정보수정</div>
    </div>
    <div class="inner">
      <div class="blue-box mg0">정보를 안전하게 보호하기 위해 비밀번호를 다시 한 번 확인합니다.</div>
      <table cellpadding="0" cellspacing="0" class="gray-tb mt10">
        <colgroup>
          <col width="150px">
          <col width="">
        </colgroup>
        <tbody>
        <tr>
          <th>아이디</th>
          <td><input type="text" v-model="user.id" style="width: 194px;" readonly></td>
        </tr>
        <tr>
          <th>제휴 담당자 명</th>
          <td><input type="text" v-model="info.chargename" style="width: 194px;" readonly></td>
        </tr>
        <tr>
          <th>휴대폰</th>
          <td>
            <input type="text" v-model="info.chargemobile" style="width: 194px;" readonly>
            <button v-if="!timer.isStart && !authComplete" type="button" class="btn blue-line ml3" @click="sendAuthMessage">본인인증</button>
            <button v-if="timer.isStart && !authComplete" type="button" class="btn blue-line ml3" @click="sendAuthMessage">재발송</button>
          </td>
        </tr>
        <tr>
          <th>인증번호</th>
          <td>
            <input type="text" v-model="authConfirmNumber" placeholder="휴대폰 인증번호 6자리" style="width: 194px;" :readonly="authComplete"><span v-show="timer.isStart" style="color:red" class="ml3">{{ timer.expiredTime }}</span>
            <div class="mt10">인증번호 유효 시간은 발송 후 10분 이내 입니다.</div>
          </td>
        </tr>
        </tbody>
      </table>
      <div class="btn-group mb10">
        <button type="button" class="btn big blue" @click="authConfirm">확인</button>
      </div>
    </div>
    <PartnersApplyDetail v-if="detailShow" v-bind:activeUserNo="info.userno"
                         v-on:closeDetail="closeDetail"></PartnersApplyDetail>
  </div>
  <!-- /컨텐츠 영역 -->
</template>

<script>
import PartnersApplyDetail from "@views.admin/partners/apply/PartnersApplyDetail";

export default {
  name: "partners.info.auth",
  components : {PartnersApplyDetail},
  data() {
    return {
      user : this.$util.getUser(this.$store.getters.CONSTANTS.MANAGER_SESSION),
      info : {
        userno : '',
        chargename : '',
        chargemobile : ''
      },
      timer : {
        isStart : false,
        timeCounter : 600,
        expiredTime : '',
        authNumber : ''
      },
      authConfirmNumber : '',
      polling : null,
      authComplete : false,
      detailShow : false
    }
  },
  mounted() {
    this.init();
  },
  methods : {
    init(){
      this.$http.post('/partners/common/charger', {userno : this.user.no, chargetype : 'CHA001'}).then(result => {
        this.info.userno = result.data.userno;
        this.info.chargename = result.data.chargename;
        this.info.chargemobile = result.data.chargemobile;
      }).catch(error => {
        this.$util.debug(error);
      })
    },
    sendAuthMessage(){
      if( (typeof this.info.userno !== 'undefined' && this.info.userno !== '') && (typeof this.info.chargemobile !== 'undefined' && this.info.chargemobile !== '')){
        this.$http.post('/partners/common/send/auth/message', this.info).then(result => {
          if(result.statusCode === 200) {
            this.timer.authNumber = result.data.number;
            this.timerStart();
          } else {
            alert('인증번호 전송 중 에러가 발생 하였습니다.');
          }
        }).catch(error => {
          this.$util.debug(error);
        })
      }
    },
    timerStart() {
      this.timer.timeCounter = 600;
      this.timer.isStart = true;
      this.polling = setInterval( () =>{
        this.timer.timeCounter--;
        this.secondsToMinuteSeconds();
        if (this.timer.timeCounter <= 0) {
          this.timeStop();
        }
      },1000)
    },
    timeStop(){
      this.timer.isStart = false;
      clearInterval(this.polling)
    },
    secondsToMinuteSeconds(){
      let minutes = Math.floor(this.timer.timeCounter / 60) % 60 || 0
      let seconds = this.timer.timeCounter % 60 || 0
      this.timer.expiredTime = [minutes,seconds].map(v => v < 10 ? "0" + v : v).filter((v,i) => v !== "00" || i > 0).join(":");
    },
    authConfirm() {
      if(typeof this.timer.authNumber === 'undefined' || this.timer.authNumber === ''){
        alert('인증번호를 전송 후 확인이 가능합니다.');
        return false;
      }

      if(typeof this.authConfirmNumber === 'undefined' || this.authConfirmNumber === ''){
        alert('인증번호 입력 후 확인이 가능합니다.');
        return false;
      }

      if(parseInt(this.authConfirmNumber) === this.timer.authNumber){
        this.timeStop();
        this.authComplete = true;
        this.detailShow = true;
      } else {
        alert('인증번호가 올바르지 않습니다.');
        return false;
      }
    },
    closeDetail: function () {
      this.detailShow = false;
      this.timer.isStart = false;
      this.timer.expiredTime = '';
      this.timer.authNumber = '';
      this.authConfirmNumber = '';
      this.polling = null;
      this.authComplete = false;

      this.init();
    }
  },
  beforeDestroy() {
    clearInterval(this.polling);
  }
}
</script>

<style scoped>

</style>