<template>
  <div>
    <div class="visual">
      <h1>입 / 점 / 신 / 청</h1>
      <p>성공적인 비즈니스 파트너! D.PLOT과 함께하세요!</p>
    </div>
    <!-- 컨텐츠 영역 -->
    <div class="join-content">
      <div class="flow clearfix">
        <ul>
          <li class="on">
            <span>Step 1.</span>
            <p>입점절차 및 사업자 인증</p>
          </li>
          <li>
            <span>Step 2.</span>
            <p>약관동의</p>
          </li>
          <li>
            <span>Step 3.</span>
            <p>정보입력</p>
          </li>
          <li>
            <span>Step 4.</span>
            <p>신청완료</p>
          </li>
        </ul>
      </div>
      <h2 class="title">입점절차</h2>
      <p>아래는 D.PLOT 에서 판매 활동을 하기 위한 입점 절차입니다.</p>
      <div class="diagram">
        <ul class="clearfix">
          <li><p>사업자 인증</p></li>
          <li><p>약관동의 및<br>정보입력</p></li>
          <li><p>가입완료</p></li>
          <li><p>입점심사</p></li>
          <li><p>계약체결</p></li>
          <li><p>입점완료</p></li>
        </ul>
      </div>
      <h2 class="title">사업자인증</h2>
      <p>
        - 사업자 등록번호가 있는 사업자에 한해 파트너사 신청이 가능합니다.<br>
        - 인증이 되지 않을 경우 고객센터로 문의 바랍니다. (1666-3642)
      </p>
      <div class="boxing">
        <div class="form-area middle">
          <dl>
            <dt>상호명</dt>
            <dd><input type="text" v-model="bizAuthInfo.compName" ref="compName" :readonly="isBizAuth" style="width: 339px;"></dd>
          </dl>
          <dl>
            <dt>사업자 번호</dt>
            <dd>
              <input type="text" v-model="bizAuthInfo.bizNo1" ref="bizNo1" maxlength="3" @keyup="isNumberCheck" :readonly="isBizAuth" style="width: 95px;"><span>-</span>
              <input type="text" v-model="bizAuthInfo.bizNo2" ref="bizNo2" maxlength="2" @keyup="isNumberCheck" :readonly="isBizAuth" style="width: 90px;"><span>-</span>
              <input type="text" v-model="bizAuthInfo.bizNo3" ref="bizNo3" maxlength="5" @keyup="isNumberCheck"  :readonly="isBizAuth" style="width: 132px;">
            </dd>
          </dl>
          <dl>
            <dt>대표자명</dt>
            <dd><input type="text" v-model="bizAuthInfo.repreName" ref="repreName" :readonly="isBizAuth" style="width: 339px;"></dd>
          </dl>
          <dl>
            <dt>영업시작일</dt>
            <dd>
              <input type="text" v-model="salesStYear" ref="salesStYear" @keyup="isNumberCheck" :readonly="isBizAuth" maxlength="4" placeholder="년(4자)" style="width: 117px;">
              <input type="text" v-model="salesStMonth" ref="salesStMonth" @keyup="isNumberCheck" :readonly="isBizAuth" maxlength="2" placeholder="월" style="width: 65px;">
              <input type="text" v-model="salesStDate" ref="salesStDate" @keyup="isNumberCheck" :readonly="isBizAuth" maxlength="2" placeholder="일" style="width: 65px;">
              <button type="button" class="btn" :class="isBizAuth ? 'red-line': 'blue-line'" @click="isBizAuthCheck" :disabled="isBizAuth">{{ isBizAuth ? '인증완료' : '사업자인증' }}</button>
            </dd>
          </dl>
        </div>
      </div>
      <div class="btn-group">
        <button type="button" class="btn big blue" @click="goNext">확인</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'PartnershipAuth',
  data() {
    return {
      bizAuthInfo: {
        compName: '',
        repreName: '',
        bizNo1: '',
        bizNo2: '',
        bizNo3: '',
        salesStDt: '',
      },
      isBizAuth: false,
      salesStYear: '',
      salesStMonth: '',
      salesStDate: '',
    }
  },
  methods: {
    isBizAuthCheck: function(){

      let salesStMonth = parseInt(this.salesStMonth) < 10 ? '0'+parseInt(this.salesStMonth) : this.salesStMonth;
      let salesStDate = parseInt(this.salesStDate) < 10 ? '0'+parseInt(this.salesStDate) : this.salesStDate;

      this.bizAuthInfo.salesStDt = this.salesStYear + '' + salesStMonth + '' + salesStDate;

      let paramMsgForKey = {
        compName: '상호명',
        repreName: '대표자명',
        bizNo1: '사업자번호',
        bizNo2: '사업자번호',
        bizNo3: '사업자번호',
        salesStDt: '영업시작일'
      }

      for (const [key, value] of Object.entries(this.bizAuthInfo)) {
        if( (value == null || value.trim() === '')){
          // this.info[key].scroll();
          if(typeof this.$refs[key] !== 'undefined'){
            this.$refs[key].focus();
          }
          alert('필수값 ' + paramMsgForKey[key] + ' 값이 입력되지 않았습니다.');
          return false;
        }
      }

      this.$http.post('/partnership/biz/numberCheck', this.bizAuthInfo).then(result =>{
        this.$util.debug(result);
        this.isBizAuth = result.data.exist;

        if(!result.data.exist){
          alert('사업자 인증에 실패하였습니다.');
        }
      }).catch(error => {
        this.$util.debug(error);
      });


    },
    goNext: function(){
      if(this.isBizAuth){
        let param = {};
        for (const [key, value] of Object.entries(this.bizAuthInfo)) {
          param[key] = value;
        }
        param.isAuth = this.isBizAuth;

        this.$router.push({name: 'common.partnership.agree', query : param});
      } else {
        alert('사업자 인증이 필요합니다.');
      }
    },
    isNumberCheck: function(event){
      event.target.value = event.target.value.replace(/[^\d]/g, "");
    }
  }
}
</script>

<style scoped src="@assets.admin/css/partnership.css"></style>