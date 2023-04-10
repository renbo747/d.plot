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
          <li>
            <span>Step 1.</span>
            <p>입점절차 및 사업자 인증</p>
          </li>
          <li>
            <span>Step 2.</span>
            <p>약관동의</p>
          </li>
          <li class="on">
            <span>Step 3.</span>
            <p>정보입력</p>
          </li>
          <li>
            <span>Step 4.</span>
            <p>신청완료</p>
          </li>
        </ul>
      </div>
      <h2 class="title">기본정보</h2>
      <div class="boxing">
        <div class="form-area">
          <dl>
            <dt><i class="essential"></i>아이디</dt>
            <dd>
              <input type="text" v-model="info.userId" ref="userId" @keyup="idCheck" placeholder="5~12자의 영문 소문자, 숫자">
              <span :class="isIdCheck ? 'txt-blue': 'txt-red'"><i :class="isIdCheck ? 'icon-alert-check': 'icon-alert2'"></i>{{isIdCheck ? '사용가능' : '오류! 5~12자의 영문 소문자와 숫자 조합만 사용가능'}} </span>
            </dd>
          </dl>
          <dl>
            <dt><i class="essential"></i>비밀번호</dt>
            <dd>
              <input type="password" v-model="info.userPw" ref="userPw" @keyup="pwCheck" placeholder="비밀번호는 6~15자 이내">
              <span :class="isPwCheck ? 'txt-blue': 'txt-red'"><i :class="isPwCheck ? 'icon-alert-check' : '' "></i>{{isPwCheck ? '사용가능' : '오류! 비밀번호는 6~15자 이내로 입력'}}</span>
            </dd>
          </dl>
          <dl>
            <dt><i class="essential"></i>비밀번호 확인</dt>
            <dd>
              <input type="password" v-model="userPwConfirm" ref="userPwConfirm" @keyup="pwConfirmCheck">
              <span :class="isPwConfirmCheck ? 'txt-blue': 'txt-red'"><i :class="isPwConfirmCheck ? 'icon-alert-check': 'icon-alert2'"></i>{{ isPwConfirmCheck ? '비밀번호 일치' : '비밀번호 입력오류!' }}</span>
            </dd>
          </dl>
        </div>
      </div>
      <h2 class="title">사업자정보</h2>
      <div class="boxing">
        <div class="form-area">
          <dl>
            <dt><i class="essential"></i>사업자등록번호</dt>
            <dd>
              <input type="text" v-model="bizNo1" style="width: 80px;" disabled><span>-</span>
              <input type="text" v-model="bizNo2" style="width: 72px;" disabled><span>-</span>
              <input type="text" v-model="bizNo3" style="width: 125px;" disabled>
            </dd>
          </dl>
          <dl>
            <dt><i class="essential"></i>상호</dt>
            <dd><input type="text" v-model="info.compName" disabled></dd>
          </dl>
          <dl>
            <dt><i class="essential"></i>업체명</dt>
            <dd><input type="text" v-model="info.name" ref="name"></dd>
          </dl>
          <dl>
            <dt><i class="essential"></i>대표자명</dt>
            <dd><input type="text" v-model="info.repreName" disabled></dd>
          </dl>
          <dl>
            <dt><i class="essential"></i>대표자 휴대폰</dt>
            <dd>
              <input type="text" v-model="info.repMobile" ref="repMobile">
            </dd>
          </dl>
          <dl>
            <dt><i class="essential"></i>업태</dt>
            <dd><input type="text" v-model="info.bizCondition" ref="bizCondition"></dd>
          </dl>
          <dl>
            <dt><i class="essential"></i>종목</dt>
            <dd><input type="text" v-model="info.bizItem" ref="bizItem"></dd>
          </dl>
          <dl>
            <dt><i class="essential"></i>주소</dt>
            <dd>
              <button type="button" class="btn blue-line" @click="searchAddress">주소검색</button>
              <input type="text" name="address1" v-model="info.addr" ref="addr" readonly style="width:360px;">
              <input type="text" name="address2" v-model="info.addrDetail" ref="addrDetail" style="width:360px;">

            </dd>
          </dl>
          <dl>
            <dt><i class="essential"></i>판매상품유형</dt>
            <dd>
              <select style="width: 300px;" v-model="info.sellProdType" ref="sellProdType">
                <option value="">대표카테고리 선택</option>
                <option v-for="item in prodTypeList" v-bind:key="item.idx" :value="item.name">{{ item.name }} </option>
              </select>
            </dd>
          </dl>
          <dl>
            <dt><i class="essential"></i>통신판매신고번호</dt>
            <dd>
              <input type="text" v-model="info.comSellNo" ref="comSellNo" placeholder="제2021-서울용산-0161호">
              <span class="txt-orange"><i class="icon-alert"></i>통신판매업 신고 후 가입을 진행해주시기 바랍니다.</span>
            </dd>
          </dl>
          <dl>
            <dt><i class="essential"></i>고객문의 연락처</dt>
            <dd>
              <input type="text" v-model="info.tel" ref="tel">
            </dd>
          </dl>
          <dl>
            <dt><i class="essential"></i>대표 이메일</dt>
            <dd>
              <input type="text" placeholder="예) master@gmai.com" v-model="info.email" ref="email">
              <span class="txt-orange"><i class="icon-alert"></i>고객문의 연락처 및 이메일은 주문정보에 노출되니 정확한 정보를 입력해주시기 바랍니다.</span>
            </dd>
          </dl>
          <dl>
            <dt><i class="essential"></i>주요취급브랜드</dt>
            <dd>
              <input type="text" v-model="info.handbrand" ref="handbrand">
            </dd>
          </dl>
        </div>
      </div>
      <h2 class="title">제휴담당자</h2>
      <div class="boxing">
        <div class="form-area">
          <dl>
            <dt><i class="essential"></i>담당자 이름</dt>
            <dd><input type="text" v-model="info.salesChargeName" ref="salesChargeName" readonly></dd>
          </dl>
          <dl>
            <dt><i class="essential"></i>담당자 휴대폰</dt>
            <dd>
              <input type="text" v-model="info.salesChargeMobile" ref="salesChargeMobile" readonly>
              <button type="button" class="btn blue-line" @click="userAuthCheck">실명인증</button>
            </dd>
          </dl>
          <dl>
            <dt><i class="essential"></i>담당자 이메일</dt>
            <dd><input type="text" v-model="info.salesChargeEmail" ref="salesChargeEmail"></dd>
          </dl>
        </div>
      </div>
      <h2 class="title">정산담당자</h2>
      <div class="boxing">
        <div class="form-area">
          <dl>
            <dt><i class="essential"></i>담당자 이름</dt>
            <dd><input type="text" v-model="info.accountChargeName" ref="accountChargeName"></dd>
          </dl>
          <dl>
            <dt><i class="essential"></i>담당자 휴대폰</dt>
            <dd>
              <input type="text" v-model="info.accountChargeMobile" ref="accountChargeMobile">
            </dd>
          </dl>
          <dl>
            <dt><i class="essential"></i>담당자 이메일</dt>
            <dd>
              <input type="text" v-model="info.accountChargeEmail" ref="accountChargeEmail">
            </dd>
          </dl>
        </div>
      </div>
      <h2 class="title">세금계산서 담당자</h2>
      <div class="boxing">
        <div class="form-area">
          <dl>
            <dt><i class="essential"></i>담당자 이름</dt>
            <dd><input type="text" v-model="info.taxChargeName" ref="taxChargeName"></dd>
          </dl>
          <dl>
            <dt><i class="essential"></i>담당자 휴대폰</dt>
            <dd>
              <input type="text" v-model="info.taxChargeMobile" ref="taxChargeMobile">
            </dd>
          </dl>
          <dl>
            <dt><i class="essential"></i>담당자 이메일</dt>
            <dd>
              <input type="text" v-model="info.taxChargeEmail" ref="taxChargeEmail">
              <span class="txt-orange"><i class="icon-alert"></i>세금계산서를 받는 이메일 주소입니다.</span>
            </dd>
          </dl>
        </div>
      </div>
      <h2 class="title">정산정보</h2>
      <div class="boxing">
        <div class="form-area">
          <dl>
            <dt><i class="essential"></i>예금주</dt>
            <dd><input type="text" v-model="info.remitDepositor" ref="remitDepositor"></dd>
          </dl>
<!--          <dl>-->
<!--            <dt><i class="essential"></i>개설일자</dt>-->
<!--            <dd>-->
<!--              <input type="text" v-model="accountYear" ref="accountYear" placeholder="2000" style="width: 140px;">-->
<!--              <input type="text" v-model="accountMonth" ref="accountMonth" placeholder="01" style="width: 77px;">-->
<!--              <input type="text" v-model="accountDate" ref="accountDate" placeholder="01" style="width: 77px;">-->
<!--            </dd>-->
<!--          </dl>-->
          <dl>
            <dt><i class="essential"></i>은행/계좌번호</dt>
            <dd>
              <select style="width: 120px;" v-model="info.remitBank" ref="remitBank">
                <option value="">은행 선택</option>
                <option value="002">산업은행</option>
                <option value="003">기업은행</option>
                <option value="004">국민은행</option>
                <option value="007">수협은행/수협중앙회</option>
                <option value="011">농협은행</option>
                <option value="012">농협중앙회</option>
                <option value="020">우리은행</option>
                <option value="023">SC제일은행</option>
                <option value="027">한국씨티은행</option>
                <option value="031">대구은행</option>
                <option value="032">부산은행</option>
                <option value="034">광주은행</option>
                <option value="035">제주은행</option>
                <option value="037">전북은행</option>
                <option value="039">경남은행</option>
                <option value="045">새마을금고중앙회</option>
                <option value="048">신협중앙회</option>
                <option value="050">상호저축은행</option>
                <option value="054">HSBC은행</option>
                <option value="055">도이치은행</option>
                <option value="057">제이피모간체이스은행</option>
                <option value="060">BOA은행</option>
                <option value="062">중국공상은행</option>
                <option value="064">산림조합중앙회</option>
                <option value="071">우체국</option>
                <option value="081">KEB하나은행</option>
                <option value="088">신한은행</option>
                <option value="089">K뱅크</option>
                <option value="090">카카오뱅크</option>
                <option value="209">유안타증권</option>
                <option value="218">KB증권</option>
                <option value="238">미래에셋대우</option>
                <option value="240">삼성증권</option>
                <option value="243">한국투자증권</option>
                <option value="247">NH투자증권</option>
                <option value="261">교보증권</option>
                <option value="262">하이투자증권</option>
                <option value="263">현대차투자증권</option>
                <option value="264">키움증권</option>
                <option value="265">이베스트투자증권</option>
                <option value="266">SK증권</option>
                <option value="267">대신증권</option>
                <option value="269">한화투자증권</option>
                <option value="270">하나금융투자</option>
                <option value="278">신한금융투자</option>
                <option value="279">동부증권</option>
                <option value="280">유진투자증권</option>
                <option value="287">메리츠종합금융증권</option>
                <option value="290">부국증권</option>
                <option value="291">신영증권</option>
                <option value="292">케이프투자증권</option>
                <option value="103">SBI 저축은행</option>
              </select>
              <input type="text" v-model="info.remitAccount" style="width: 174px;" ref="remitAccount">
              <button type="button" class="btn blue-line" @click="accountAuthCheck">계좌실명인증</button>
              <span :class="isAccountAuthCheck ? 'txt-blue': 'txt-red'"><i v-show="isAccountAuthCheckText !== '' " class="icon-alert2" :class="isAccountAuthCheck ? 'icon-alert-check': 'icon-alert2'"></i>{{ isAccountAuthCheckText }}</span>
              <p class="txt-orange"><i class="icon-alert"></i>파트너사 정산은 전월 1일부터 말일까지의 구매확정건을 기준으로 영업일 기준 익월 말일에 정산됩니다.</p>
            </dd>
          </dl>
        </div>
      </div>
      <h2 class="title">첨부서류</h2>
      <div class="boxing">
        <div class="form-area">
          <dl>
            <dt><i class="essential"></i>사업자등록증사본</dt>
            <dd class="pt10">
              <button type="button" class="btn blue-line" @click="onBusinessFileClick(true)">파일첨부</button>
              <input type="file" ref="businessFile" v-on:change="handleFileUpload(true)" v-show="false" accept="image/jpeg, image/png, .pdf" />
              <div>{{ info.orgBizNoFile }}<button type="button" class="file-del" @click="fileInit(true);" v-show="info.businessFile != null"></button></div>
              <p class="txt-orange"><i class="icon-alert"></i>1년 이내에 발급 받은 사업자등록증을 첨부해 주시기 바랍니다.</p>
            </dd>
          </dl>
          <dl>
            <dt><i class="essential"></i>통장사본</dt>
            <dd>
              <button type="button" class="btn blue-line" @click="onBusinessFileClick(false)">파일첨부</button>
              <input type="file" ref="bCopyFile" v-on:change="handleFileUpload(false)" v-show="false" accept="image/jpeg, image/png, .pdf" />
              <div>{{ info.orgBCopyFile }}<button type="button" class="file-del" @click="fileInit(false);" v-show="info.bCopyFile != null"></button></div>
              <p class="txt-orange"><i class="icon-alert"></i>jpg, jpeg, png파일 또는 PDF 파일만 첨부 가능하며, 파일 최대 크기는 10MB를 초과 할 수 없습니다.</p>
            </dd>
          </dl>

          <dl>
            <dt><i class="essential"></i>통신판매업신고증</dt>
            <dd>
              <button type="button" class="btn blue-line" @click="onSellFileClick">파일첨부</button>
              <input type="file" ref="cSellFile" v-on:change="handleFileUploadSell" v-show="false" accept="image/jpeg, image/png, .pdf" />
              <div>{{ info.orgSellFile }}<button type="button" class="file-del" @click="fileInitSell" v-show="info.cSellFile != null"></button></div>
              <p class="txt-orange"><i class="icon-alert"></i>jpg, jpeg, png파일 또는 PDF 파일만 첨부 가능하며, 파일 최대 크기는 10MB를 초과 할 수 없습니다.</p>
            </dd>
          </dl>
        </div>
      </div>
      <h2 class="title">수신동의여부</h2>
      <div class="boxing">
        <div class="form-area">
          <dl>
            <dt>SMS 마케팅 수신</dt>
            <dd>
              <div class="radio_wrap wide">
                <input type="radio" name="sms" id="rd11" v-model="info.isAdSms" value="T"/><label for="rd11">동의</label>
                <input type="radio" name="sms" id="rd12" v-model="info.isAdSms" value="F"/><label for="rd12">동의안함</label>
              </div>
            </dd>
          </dl>
          <dl>
            <dt>이메일 마케팅 수신</dt>
            <dd>
              <div class="radio_wrap wide">
                <input type="radio" name="email" id="rd21" v-model="info.isAdMailing" value="T"/><label for="rd21">동의</label>
                <input type="radio" name="email" id="rd22" v-model="info.isAdMailing" value="F"/><label for="rd22">동의안함</label>
              </div>
            </dd>
          </dl>
        </div>
      </div>
      <div>
        <i class="icon-alert"></i>반품서비스를 이용하기 위해서는 회원가입 후 정보수정 화면에서 택배사 이용 신청을 필수로 하셔야 합니다.
      </div>
      <div class="btn-group">
        <button type="button" class="btn big blue" @click="insertPartnerShip">신청</button>
      </div>
    </div>
  </div>
</template>
<script>

export default {
  name: 'common.partnership.join',
  data(){
    return{
      info: {
        userId: '',
        userPw: '',
        compName: '',
        name: '',
        repreName: '',
        repMobile: '',
        repCI: '',
        bizNo: '',
        bizCondition: '',
        bizItem: '',
        post: '',
        addr: '',
        addrDetail: '',
        sigungucode: '',
        buildingcode: '',
        roadnamecode: '',
        sellProdType: '',
        comSellNo: '',
        tel: '',
        email: '',
        salesChargeName: '',
        salesChargeMobile: '',
        salesChargeEmail: '',
        accountChargeName: '',
        accountChargeMobile: '',
        accountChargeEmail: '',
        taxChargeName: '',
        taxChargeMobile: '',
        taxChargeEmail: '',
        remitDepositor: '',

        remitBank: '',
        remitAccount: '',
        orgBizNoFile : '',
        businessFile : null,
        orgBCopyFile : '',
        bCopyFile : null,
        orgSellFile : '',
        cSellFile : null,
        handbrand : '',
        isAdSms: 'T',
        isAdMailing: 'T',
      },
      isIdCheck: false,
      isPwCheck: false,
      userPwConfirm: '',
      isPwConfirmCheck: false,
      isUserAuthCheck: false,
      isAccountAuthCheck: false,
      isAccountAuthCheckText: '',
      bizNo1: '',
      bizNo2: '',
      bizNo3: '',
      // accountYear: '',
      // accountMonth: '',
      // accountDate: '',
      prodTypeList: [],
      bankTypeList: [],
      certPopupObj : null,
      intervalObj : null,
    }
  },
  // beforeRouteEnter: (to, from, next) => {
  //   ( (from.name === 'common.partnership.agree' || from.name === 'common.partnership.join') && to.query.isAuth) ? next() : next('/common/partnership/auth');
  // },
  mounted() {
    let param = this.$route.query;

    if(typeof param !== 'undefined') {
      this.bizNo1 = param.bizNo1;
      this.bizNo2 = param.bizNo2;
      this.bizNo3 = param.bizNo3;
      this.info.compName = param.compName;
      this.info.repreName = param.repreName;
      this.info.bizNo = this.bizNo1 + '-' + this.bizNo2 + '-' + this.bizNo3;
    }

    this.$http.post("/partnership/init/data", {}).then(result =>{
      this.prodTypeList = result.data.prodtypelist;
      this.bankTypeList = result.data.banktypelist;
    }).catch(error => {
      this.$util.debug(error);
    });
  },
  methods: {
    idCheck: function(){
      let regex = /^(?=.*[a-zA-Z])(?=.*[0-9]).{5,12}$/;
      let param = { userId : this.info.userId }

      if(this.info.userId.length >= 5 && this.info.userId.length <= 12 && regex.test(this.info.userId)){
        this.$http.post('/partnership/id/check', param).then(result => {
          this.isIdCheck = !result.data.isavailable;
        }).catch(error => {
          this.$util.debug(error);
          this.isIdCheck = false;
        });
      } else {
        this.isIdCheck = false;
      }
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
    userAuthCheck: function(){
      window.popupCallback = this.certClose;
      this.certPopupObj = window.open('/common/partnership/kmc/request?code='+process.env.VUE_APP_KMC_CERT_PARTNERS, "KMCISWindow", "width=425, height=550, resizable=0, scrollbars=no, status=0, titlebar=0, toolbar=0, left=435, top=250");
    },
    certClose(certData){
      if(certData != null) {
        if (typeof certData.ci !== 'undefined') {
          this.info.repCI = certData.ci;
          this.info.salesChargeName = certData.name;
          this.info.salesChargeMobile = certData.phoneno;
          this.isUserAuthCheck = true;
          window.popupCallback = null;
        }
      }
    },
    accountAuthCheck: function(){

      if(this.info.remitAccount === '' || this.info.remitBank === '' || this.info.remitDepositor === ''){
        alert('필수입력값 넣어야함');
        return;
      }

      let param = {
        accounttype : "2",
        bank : this.info.remitBank,
        birthday : this.info.bizNo,
        account : this.info.remitAccount,
        name : this.info.remitDepositor
      }

      this.$http.post('/common/bank/account', param).then(result => {
        this.isAccountAuthCheck = result.data.exist;
        this.isAccountAuthCheckText = result.data.msg;
      }).catch(error => {
        this.$util.debug(error);
      });
    },
    onBusinessFileClick: function(isBusiness){
      (isBusiness) ? this.$refs.businessFile.click() : this.$refs.bCopyFile.click();
    },
    onSellFileClick(){
      this.$refs.cSellFile.click();
    },
    handleFileUpload: function(isBusiness){
      let file = (isBusiness) ? this.$refs.businessFile : this.$refs.bCopyFile;

      let fileType = ['image/png','image/jpeg', 'image/png', 'application/pdf']
      if(!fileType.includes(file.files[0].type)){
        alert('jpg, jpeg, png파일 또는 PDF 파일만 첨부 가능 합니다.');
        file.value = null;
        (isBusiness) ? this.info.orgBizNoFile = '' : this.info.orgBCopyFile = '';
        return false;
      }

      if(file.files[0].size > 10485760){
        alert('파일 최대 크기는 10MB를 초과 할 수 없습니다.');
        file.value = null;
        (isBusiness) ? this.info.orgBizNoFile = '' : this.info.orgBCopyFile = '';
        return false;
      }

      (isBusiness) ? this.info.businessFile = file.files[0] : this.info.bCopyFile = file.files[0];
      (isBusiness) ? this.info.orgBizNoFile = file.files[0].name : this.info.orgBCopyFile = file.files[0].name;
    },
    fileInit: function(isBusiness){
      (isBusiness) ? this.info.orgBizNoFile = '' : this.info.orgBCopyFile = '';
      (isBusiness) ? this.info.businessFile = null : this.info.bCopyFile = null;
    },
    handleFileUploadSell(){
      let file = this.$refs.cSellFile;
      let fileType = ['image/png','image/jpeg', 'image/png', 'application/pdf']
      if(!fileType.includes(file.files[0].type)){
        alert('jpg, jpeg, png파일 또는 PDF 파일만 첨부 가능 합니다.');
        file.value = null;
        this.info.cSellFile = '';
        return false;
      }

      if(file.files[0].size > 10485760){
        alert('파일 최대 크기는 10MB를 초과 할 수 없습니다.');
        file.value = null;
        this.info.orgSellFile = '';
        return false;
      }

      this.info.cSellFile = file.files[0];
      this.info.orgSellFile = file.files[0].name;
    },
    fileInitSell(){
      this.info.orgSellFile = '';
      this.info.cSellFile = null;
    },
    searchAddress() {
      new window.daum.Postcode({
        oncomplete: (data) => {
          this.info.post = data.zonecode;
          this.info.addr = data.address
          this.info.sigungucode = data.sigunguCode;
          this.info.buildingcode = data.buildingCode;
          this.info.roadnamecode = data.roadnameCode;
        }
      }).open();
    },
    insertPartnerShip: function(){
      //validation 체크
      let includeKey = ['businessFile', 'bCopyFile', 'orgBizNoFile', 'orgBCopyFile', 'orgSellFile', 'cSellFile'];
      let isValidate = true;

      let metaParam = {
        isIdCheck: { msg: '사용할 수 없는 아이디 입니다.', ref: 'userId'},
        isPwCheck: { msg: '비밀번호 확인이 필요 합니다.', ref: 'userPw'},
        isPwConfirmCheck: { msg: '비밀번호 확인이 필요 합니다.', ref: 'userPwConfirm'},
        isUserAuthCheck: { msg: '실명인증 완료 후 가입이 가능합니다.', ref: 'repMobile'},
        isAccountAuthCheck: { msg: '계좌실명인증 완료 후 가입이 가능합니다.', ref: 'remitAccount'},
      }

      if(this.info.userId === ''){
        alert('아이디를 입력해 주세요.');
        return false;
      }

      //각각의 아이디, 패스워드, 패스워드확인, 실명인증, 계좌실명인증여부 체크
      if(!this.isIdCheck || !this.isPwCheck || !this.isPwConfirmCheck || !this.isUserAuthCheck || !this.isAccountAuthCheck){
        for(const [key, obj] of Object.entries(metaParam)){
          if(!this[key]){
            alert(obj.msg);
            return false;
          }
        }
      }

      //사업자등록증사본파일, 통장사본파일 체크
      if(this.info.businessFile == null || this.info.bCopyFile == null){
        let msg = (this.info.businessFile == null) ? '사업자등록증사본을 등록해 주세요.' : '통장사본을 등록해 주세요.';
        alert(msg);
        return false;
      }

      if(this.info.cSellFile == null){
        alert("통신판매업신고증을 등록해 주세요.");
        return false;
      }

      //모든항목이 필수 값

      let checkData = this.info;
      // checkData.accountYear = this.accountYear;
      // checkData.accountMonth = this.accountMonth;
      // checkData.accountDate = this.accountDate;

      for (const [key, value] of Object.entries(checkData)) {
        if(!includeKey.includes(key)){
          if( (value === '' || value == null) && typeof this.$refs[key] !== 'undefined'){
            // this.info[key].scroll();
            this.$refs[key].focus();
            this.$refs[key].scrollIntoView();
            isValidate = false;
            break;
          }
        }
      }

      //정산정보 생년월일 info에 처리
      // this.info.remitBirth = this.accountYear+''+this.accountMonth+''+this.accountDate;

      if(!isValidate) {
        setTimeout(function () {
          alert('필수값이 입력되지 않았습니다.');
        }.bind(this), 500);

        return false;
      }

      let phoneKey = ['repMobile', 'salesChargeMobile', 'accountChargeMobile', 'taxChargeMobile'];
      let mobileRegValid = true;
      for(let key of phoneKey){
        if(!this.$util.isPhone(this.info[key])){
          mobileRegValid = false;
          this.$refs[key].focus();
          this.$refs[key].scrollIntoView();
          break;
        }
      }

      if(!mobileRegValid) {
        setTimeout(function () {
          alert('휴대폰 형식이 올바르지 않습니다.');
        }.bind(this), 500);

        return false;
      }

      let emailKey = ['email', 'salesChargeEmail', 'accountChargeEmail', 'taxChargeEmail'];
      let emailRegValid = true;

      for(let key of emailKey){
        if(!this.$util.isEmail(this.info[key])){
          emailRegValid = false;
          this.$refs[key].focus();
          this.$refs[key].scrollIntoView();
          break;
        }
      }

      if(!emailRegValid) {
        setTimeout(function () {
          alert('이메일 형식이 올바르지 않습니다.');
        }.bind(this), 500);

        return false;
      }

      let params = {
        "files": []
      }
      params = Object.assign({}, params, this.info);

      params.files.push({key: 'businessFile', file : this.info.businessFile});
      params.files.push({key: 'bCopyFile', file : this.info.bCopyFile});
      params.files.push({key: 'cSellFile', file : this.info.cSellFile});

      this.$http.post('/partnership/insert', params).then(result => {
        if(result.data.insertresult){
          this.$router.push({name: 'common.partnership.complete'});
        } else {
          alert('파트너사 등록에 실패 하였습니다.\n관리자에게 문의 바랍니다.');
        }
      }).catch(error => {
        this.$util.debug(error);
      });
    },
  }
}
</script>

<style scoped src="@assets.admin/css/partnership.css"></style>