import Terms from '@views/front/common/components/ui/modal/Terms.vue'
import LifetimeMember from '@views.mobile/member/popup/LifetimeMember.vue'
import SignUpComplete from '@views.mobile/member/popup/SignUpComplete.vue'
export default {
  beforeCreate() {
    this.$store.commit("onSubHeaderOption", {
      title: "회원가입",
      searchIcon: false,
      cartIcon: false,
      homeIcon: false,
    });
  },
  data() {
    return {
      platform: window.sessionStorage.getItem('platform'),
      snstype: "", //SNS타입코드
      isEmployee: 'F', //임직원 여부
      // input 부분
      signupData: {
        name: "",
        mobile: "",
        userid: "",
        userpw: "",
        userpw2: "",
        email: "",
        recommendId: "",
        gender:"",
        birthdate:""
      },
      email1: "",
      email2: "",
      emailinput: "",
      isAllAgree: false, //전체동의 여부
      islife: false,     //평생회원여부
      termList: [],      //이용약관 목록
      signupSuccess: {
        // 일반 회원가입, sns 회원가입 구분
        isSns: false,
        recommendIdData: "",
      },
      // todo:  가입된 경우 유저 타입으로 구분 필요
      // userMember (일반), kakaoMember (카카오), naverMember (네이버)
      userType: "naverMember",
      textData: "이미 가입된 회원정보가 있습니다.",
      idSuccess: false, //아이디 체크성공
      idFail: false, //아이디 체크실패(비유효한 아이디)
      idFail2: false, //아이디 체크실패(같은 아이디)
      pwSuccess: false, //비밀번호 체크 8자리 성공
      pwSuccess2: false, //비밀번호 체크 10자리 성공
      pwFail: false,  //비밀번호 체크 실패
      pwConfirmSuccess: false, //비밀번호 확인 성공
      pwConfirmFail: false, //비밀번호 확인 실패
      emaildomain: [],   //이메일 도메인 목록


      isAuth: 'F', //인증여부
      isSend: 'F', //인증번호 발송여부
      timer: null, //타이머
      timeout: 0, // 인증번호 입력 남은시간(초)
      isTimeOver: "F",
      authnum: '', // 인증번호
      hhmm: '', // timeout을 mm:ss로 변환해서 화면에 표시하는 computed 변수
      emailSuccess: false,
      emailFail: false,
      emailFail2: false,
      emailFail3: false,
      isreward: true,
    };
  },
  methods: {
    /*********************
     * 이용약관 목록 조회
     *********************/
    apiTerm() {
      let temp = ["TRT001", "TRT002", "TRT003", "TRT004", "TRT005", "TRT006"];
      this.$http.post('/member/signup/term', {termslist: temp, isloading: false }).then(result => {
        if (result.statusCode == 200) {
          var list = result.data.list;
          for (var i = 0; i < list.length; i++) {
            list[i].checked = false;
            list[i].id = "agreeChk" + (i + 1);
            list[i].label = list[i].prefix + list[i].termstypename;
          }
          this.termList = list;
          this.$util.debug(JSON.stringify(this.termList));
        }
      });
    },
    getEmpDomain() {
      this.empdomain = [];
      this.$http.post('/common/code/list', { cmclass: "EMPDOMAIN", isloading: false })
        .then(result => {
          for (var i = 0; i < result.data.list.length; i++) {
            const temp = {
              value: result.data.list[i].codename,
              label: result.data.list[i].codename,
              detail: result.data.list[i].detail
            }
            this.emaildomain.push(temp);
          }
        });
    },
    /*********************
     * 사용자 id check
     *********************/
    idCheck() {
      this.$util.debug(JSON.stringify(this.signupData));
      //userid 미입력시
      if (this.signupData.userid.trim().length == 0) {
        this.idSuccess = false;
        this.idFail = false;
        this.idFail2 = false;
        return;
      }

      if (!/^[a-z0-9]{6,12}$/.test(this.signupData.userid) || !this.$util.isExistEng(this.signupData.userid)) {
        //this.$eventBus.$emit('alert', '알림', "아이디는 6~12자의 영문, 숫자만 사용 할 수 있습니다.");
        this.idSuccess = false;
        this.idFail = true;
        this.idFail2 = false;
        return;
      }

      const param = {
        userid: this.signupData.userid,
        isloading: false
      }
      this.$http.post('/member/signup/idCheck', param).then(result => {
        if (result.statusCode == 200) {
          this.idSuccess = true;
          this.idFail = false;
          this.idFail2 = false;
        } else {
          this.idSuccess = false;
          this.idFail = false;
          this.idFail2 = true;
        }
      });
    },
    /*********************
     * 사용자 pw check
     *********************/
    pwCheck() {
      if (this.signupData.userpw.trim().length == 0) {
        this.pwSuccess = false;
        this.pwSuccess2 = false;
        this.pwFail = false;
        return;
      }
      if (this.$util.isPassword(this.signupData.userpw)) {
        if (this.signupData.userpw.length >= 8) {
          this.pwSuccess = true;
          this.pwFail = false;
        }
        if (this.signupData.userpw.length >= 10) {
          this.pwSuccess2 = true;
          this.pwFail = false;
        }
      } else {
        this.pwSuccess = false;
        this.pwFail = true;
      }
    },
    /********************
     * 사용자 pw, pw2 check
     *********************/
    pwConfirm() {
      if (this.$util.isNull(this.signupData.userpw) || this.$util.isNull(this.signupData.userpw2)) {
        this.pwConfirmFail = false;
        this.pwConfirmSuccess = false;
      } else {
        if (this.signupData.userpw == this.signupData.userpw2) {
          this.pwConfirmFail = false;
          this.pwConfirmSuccess = true;
        } else {
          this.pwConfirmFail = true;
          this.pwConfirmSuccess = false;
        }
      }
      this.$util.debug("pwConfirmFail::" + this.pwConfirmFail);
    },
    /********************
    * 사용자 이메일 check
    *********************/
    emailCheck() {
      if (this.isEmployee == 'T') {
        if (this.$util.isNull(this.email1) || this.$util.isNull(this.email2)) {
          this.emailFail = false;
          this.emailFail2 = false;
          this.emailFail3 = false;
          return;
        } else {
          this.signupData.email = this.email1 + '@' + this.email2;
        }
      } else {
        if (this.$util.isEmail(this.signupData.email)) {
          this.emailFail = false;
          this.emailFail2 = false;
          this.emailFail3 = false;
        } else {
          this.emailFail = true;
          this.emailFail2 = false;
          this.emailFail3 = false;
          return;
        }
        this.$util.debug(this.$util.isEmail(this.signupData.email));
      }
      this.$http.post('/member/signup/emailCheck', { email: this.signupData.email }).then(result => {
        if (result.statusCode == 200) {
          this.emailFail = false;
          this.emailFail2 = false;
          this.emailFail3 = false;
        } else {
          let str = result.data.message;
          if (result.message.includes('중복')) {
            this.emailFail = false;
            this.emailFail2 = true;
            this.emailFail3 = false;
          }
          if (result.message.includes('탈퇴')) {
            this.emailFail = false;
            this.emailFail2 = false;
            this.emailFail3 = true;
          }
        }
      });
    },
    /********************
     * 약관 모두 동의처리
     *********************/
    allAgreeChange() {
      for (var i = 0; i < this.termList.length; i++) {
        this.termList[i].checked = this.isAllAgree;
        this.$util.debug(" this.termList[i].checked:" + this.termList[i].checked);
      }
      this.$util.debug("isAllAgree:" + this.isAllAgree);
    },
    /********************
     * 이용약관 동의 항목 처리
     *********************/
    agreeChange(list) {
      var allAgree = true;
      for (var i = 0; i < this.termList.length; i++) {
        if (!this.termList[i].checked) {
          allAgree = false;
        }
      }
      this.isAllAgree = allAgree;
    },
    /*********************
     * 평생회원 처리시 이용약관 전체 동의 처리
     *********************/
    liftChange() {
      this.islife = !this.islife;
      if (this.islife) {
        for (var i = 0; i < this.termList.length; i++) {
          if (this.termList[i].isessen == 'F') {
            this.termList[i].checked = true;
          }
        }
      }
    },
    /************************
     * 인증번호 보내기
     ************************/
    sendAuthNum() {
      this.$util.debug("아이디체크");
      if (!this.idSuccess) {
        this.$eventBus.$emit('alert', '알림', '아이디를 확인해주세요.');
        return;
      } else if (!this.pwSuccess && !this.pwSuccess2) {
        this.$eventBus.$emit('alert', '알림', '비밀번호를 확인해주세요.');
        return;
      } else if (!this.pwConfirmSuccess) {
        this.$eventBus.$emit('alert', '알림', '비밀번호확인이 일치하지 않습니다.');
        return;
      } else if (this.emailFail || this.emailFail2 || this.emailFail3 || this.$util.isNull(this.email2) || this.$util.isNull(this.email1)) {
        this.$eventBus.$emit('alert', '알림', '이메일을 확인해주세요.');
        return;
      }

      var param = {
        email: this.email1 + '@' + this.email2
      }

      this.$http.post('/member/signup/sendAuth', param).then(result => {
        // 타이머 돌고 있으면 타이머 클리어
        if (this.timer) {
          clearTimeout(this.timer);
        }

        if (result.statusCode == 200) {
          this.isSend = 'T';
          this.authnum = ''; // 입력한 인증번호 초기화
          this.timeout = result.data.timeout; // 타이머 시간 세팅
          this.authbutton = '인증번호 재발송';
          this.isTimeOver = "F";

          this.countDownTimer();
          this.$toast.clear();
          this.$toast.open("인증번호를 발송하였습니다.");
        }
      });
    },
    /************************
     * 카운트 다운
     ************************/
    countDownTimer() {
      this.timer = setTimeout(() => {
        this.timeout -= 1;
        if (this.timeout == -1) {
          //clearTimeout(this.timer);
          this.isTimeOver = "T";
          return;
        }
        this.countDownTimer();
      }, 1000);
    },
    /************************
     * 인증번호 확인
     ************************/
    confirmAuthNum() {
      if (this.authnum == "") {
        this.$eventBus.$emit('alert', '알림', '인증번호를 입력해주세요.');
        return; //리턴시 카운터가 멈추는 현상이 생김
      } else if (this.isTimeOver == "T") {
        this.$eventBus.$emit('alert', '알림', '인증시간이 만료되었습니다.');
        return;
      } else {
        var param = {
          email: this.email1 + '@' + this.email2,
          authnum: this.authnum
        }
        this.$http.post('/member/signup/confirmAuth', param).then(result => {

          if (result.statusCode == 200) {
            this.isAuth = 'T';
            clearTimeout(this.timer);
          } else {
            this.authnum = '';
            this.isAuth = 'F';
          }
        });
      }
    },
    handleTextData() {
      if (this.userType === "userMember") {
        this.textData = "이미 가입된 회원정보가 있습니다.";
      } else if (this.userType === "kakaoMember") {
        this.textData = "카카오로 가입된 회원정보가 있습니다.";
      } else if (this.userType === "naverMember") {
        this.textData = "이미 가입된 회원정보가 있습니다.";
      }
    },
    /*******************
     * 모달 오픈
     *******************/
    showModal(modalId, param) {
      if (modalId == 'termsModal') {
        this.$eventBus.$emit('showModal', Terms, modalId, param);
      } else {
        this.$eventBus.$emit('showModal', LifetimeMember, modalId, null);
      }
    },
    /*********************
     * 회원가입 처리 
     *********************/
    handleSignUp() {
      /*****************************
       * 타당성 검사
       ******************************/
      if (!this.idSuccess) {
        this.$eventBus.$emit('alert', '알림', '아이디를 확인해주세요.');
        this.$refs.idfocus.$refs.binput.focus();
        return;
      }
      if (!this.pwSuccess && !this.pwSuccess2) {
        this.$eventBus.$emit('alert', '알림', '비밀번호를 확인해주세요.');
        this.$refs.pwfocus.$refs.binput.focus();
        return;
      }
      if (!this.pwConfirmSuccess) {
        this.$eventBus.$emit('alert', '알림', '비밀번호확인이 일치하지 않습니다.');
        this.$refs.pwckfocus.$refs.binput.focus();
        return;
      }
      if (this.emailFail || this.emailFail2 || this.emailFail3 || this.$util.isNull(this.signupData.email)) {
        this.$eventBus.$emit('alert', '알림', '이메일을 확인해주세요.');
        if (this.isEmployee == 'T') {
          this.$refs.empemailfocus.$refs.binput.focus();
        } else {
          this.$refs.emailfocus.$refs.binput.focus();
        }
        return;
      }
      if (this.isEmployee == 'T' && this.isSend == 'F') {
        this.$eventBus.$emit('alert', '알림', '이메일 인증을 완료해주세요.');
        this.$refs.empemailfocus.$refs.binput.focus();
        return;
      }
      if (this.isEmployee == 'T' && this.isAuth == 'F') {
        this.$eventBus.$emit('alert', '알림', '이메일 인증을 완료해주세요.');
        this.$refs.authnum.$refs.binput.focus();
        return;
      }

      var isRequireTerm = true;
      var isEmailTerm = false;
      var isSnsTerm = false;
      var isPushTerm = false;
      for (var i = 0; i < this.termList.length; i++) {
        if (!this.termList[i].checked && this.termList[i].isessen == 'T') {
          isRequireTerm = false;
        }
        if (this.termList[i].termstype == 'TRT005' && this.termList[i].checked) {
          isEmailTerm = true;
        }
        if (this.termList[i].termstype == 'TRT006' && this.termList[i].checked) {
          isSnsTerm = true;
        }
      }
      if (!isRequireTerm) {
        this.$eventBus.$emit('alert', '알림', '서비스약관에 동의해주세요.');
        this.$refs.ck.$refs.bck.focus();
        return;
      }
      //평생회원 
      // if (this.islife && (!isEmailTerm || !isSnsTerm)) {
      //   this.$eventBus.$emit('alert', '알림', '평생회원일경우 마켓팅 수신동의는 필수입니다.');
      //   return;
      // }

      var valid = [
        { field: 'signupData.userid', name: '아이디', type: 'I', required: true, minLength: 4, maxLength: 12 },
        { field: 'signupData.userpw', name: '비밀번호', type: 'I', required: true, minLength: 8 },
        { field: 'signupData.userpw2', name: '비밀번호', type: 'I', required: true, minLength: 8 },
        { field: 'signupData.name', name: '이름', type: 'I', required: true, minLength: 8 },
        { field: 'signupData.email', name: '이메일', type: 'I' },
      ];
      var msg = this.$util.validMsg(this.$data, valid);
      if (msg != null) {
        this.$eventBus.$emit('alert', '알림', msg);
        return;
      }

      /*******************************
       * 회원기본정보 셋팅
       ********************************/
      //광고성이메일수신여부
      if (isEmailTerm) {
        this.signupData.isadmailing = "T";
      } else {
        this.signupData.isadmailing = "F";
      }
      //광고성SMS 수신여부
      if (isSnsTerm) {
        this.signupData.isadsms = "T";
      } else {
        this.signupData.isadsms = "F";
      }
      //광고성PUSH 수신동의
      if (isPushTerm) {
        this.signupData.isadpush = "T";
      } else {
        this.signupData.isadpush = "F";
      }
      //apple_snstype 설정
      if (!this.$util.isNull(this.snstype)) {
        this.signupData.snstype = "ULT003";
      }

      //임직원 회원유형 셋팅
      if (this.isEmployee === 'T') {
        this.signupData.dadamembertype = 'DMT003'

        for (let index = 0; index < this.emaildomain.length; index++) {
          if (this.emaildomain[index].value == this.email2) {
            this.signupData.companytype = this.emaildomain[index].detail;
            break;
          }
        }
      } else {
        if (this.islife) {
          this.signupData.dadamembertype = 'DMT002'; //평생회원
        } else {
          this.signupData.dadamembertype = 'DMT001'; //일반회원
        }
      }

      this.$eventBus.$emit('confirm', '회원가입', '회원가입 하시겠습니까?', () => {

        // 파라미터 설정
        var param = this.signupData;

        this.$http.post('/member/signup/act', param).then(result => {
          if (result.statusCode == 200) {
            // dataLayer.push({
            //     'event':  'sign_up'
            // });
            this.$storage.removeSessionStorage("KMCINFO");
            let userno = result.data.userno;
            if (this.platform == 'MAC001') {
              //애플 로그인도 일반가입으로 가입되어 isSns = "F"로보냄
              this.$router.push({ name: 'member-sign-up-complete', query: { userno: userno, isSns: "F" } });
            } else {
              //this.showModal('SignupSuccessModal');
              let params = { userno: userno, isSns: "F" };
              this.$eventBus.$emit('showModal', SignUpComplete, 'SignupSuccessModal', params);
            }
          } else {
            this.$eventBus.$emit('alert', '알림', result.message);
          }
        });
      });
    }
  },
  mounted() {
    //인증에서 넘어온 초기 데이터 바인딩
    //애플:::{"snstype":"ULT003","issns":"T","dadamembertype":"DMT001"}
    let kmcInfo = this.$storage.getSessionStorage("KMCINFO");
    if (this.$util.isEmpty(kmcInfo)) {
      this.$eventBus.$emit('alert', '알림', '잘못된 접근입니다.', () => {
        this.$router.push({ name: 'shop' });
      });
    } else {
      //임직원여부 체크
      if (kmcInfo.isEmp) {
        this.isEmployee = "T";
      } else {
        this.isEmployee = "F";
        //친구초대한 아이디 셋팅
        let inviteno = ""
        inviteno =  window.sessionStorage.getItem("inviteno");
        if (!this.$util.isNull(inviteno)) {
          let userid = this.$CryptoJS.AES.decrypt(inviteno, this.$front.invitenoCrykey()).toString(this.CryptoJS.enc.Utf8);
          this.signupData.recommendId = userid.replaceAll("\"","");
        }

        // 추천리워드 사용안함시 추천인 아이디 초기화
        this.$http.post('/etc/reward', {isloading: false})
        .then(result =>{
          if(result.statusCode === 200) {
            if(this.$util.isNull(result.data.rewardinfo)) {
              this.signupData.recommendId = "";
              this.isreward = false;
            }
          }
        })
      }
      //애플 회원가입일 경우
      if (kmcInfo.isApple) {
        this.signupData.snstype = "ULT003";
        this.signupData.snsmemberno = kmcInfo.appleInfo.snsmemberno;
        this.signupData.snsemail = kmcInfo.appleInfo.snsemail;
        this.signupData.token = kmcInfo.appleInfo.token;
        this.signupData.issns = "T"; //현 솔루션상 토큰은 사용하지않고 애플 토큰이 길어서.. sub값으로 저장
      }
      this.signupData.name = kmcInfo.kmcInfo.name;
      this.signupData.mobile = kmcInfo.kmcInfo.phoneno;
    }
    //생일
    this.signupData.birthdate = kmcInfo.kmcInfo.birthday;
    //내국인 여부
    if (kmcInfo.kmcInfo.nation == 2) {
      this.signupData.isdomastic = "F"
    } else {
      this.signupData.isdomastic = "T"
    }
    //성별 정보
    if (kmcInfo.kmcInfo.gender == 0) {
      this.signupData.gender = "M"
    } else {
      this.signupData.gender = "F"
    }
    //ci 정보
    if (!this.$util.isNull(kmcInfo.kmcInfo.ci)) {
      this.signupData.conninfo = kmcInfo.kmcInfo.ci;
    }
    //di 정보
    if (!this.$util.isNull(kmcInfo.kmcInfo.di)) {
      this.signupData.dival = kmcInfo.kmcInfo.di;
    }
    //약관 목록 조회
    this.apiTerm();
    if (this.isEmployee == 'T') {
      this.getEmpDomain();
    }
  },
  computed: {
    // 타임아웃을 mm:ss로 변경
    limitTime() {
      const date = new Date(0);
      if (this.isTimeOver == "T") {
        return "00:00";
      }
      date.setSeconds(this.timeout);
      this.hhmm = date.toISOString().substr(14, 5);
      return this.hhmm;
    }
  },
  beforeDestroy() {
    clearTimeout(this.timer);
    window.sessionStorage.removeItem("inviteno");
  }
};