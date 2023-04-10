export default {
  beforeCreate() {
    this.$store.commit("onSubHeaderOption", {
      title: "회원탈퇴",
      searchIcon: false,
      cartIcon: false,
      homeIcon: true,
    });
    let param = { cmclass: "WITHDRAWRTYPE" };
    this.$http.post("/common/code/list", param).then((result) => {
      this.$util.debug("********** 탈퇴사유 공통코드 조회***********");
      this.$util.debug(result);
      this.commonCodeList = result.data.list;
      this.commonCodeList.forEach(item => {
        item.value = item.cmcode;
        item.label = item.codename;
      });
      this.$util.debug(this.commonCodeList);
    });
  },
  data() {
    return {
      // benefit
      benefitInfo: [
        {
          name: "보유 적립금",
          reward: 12345,
          unit: "원",
        },
        {
          name: "D포인트",
          reward: 5000,
          unit: "p",
        },
        {
          name: "임직원 적립금",
          reward: 100000,
          unit: "원",
        },
        {
          name: "쿠폰",
          reward: 3,
          unit: "장",
        },
      ],
      // 사유선택
      reasonSelectOptions: [
        {
          label: "이용빈도가 낮다",
          value: "reason01",
        },
        {
          label: "상품종류가 부족하다.",
          value: "reason02",
        },
        {
          label: "상품가격이 비싸다.",
          value: "reason03",
        },
        {
          label: "상품가격에 비해 품질이 떨어진다.",
          value: "reason04",
        },
        {
          label: "배송이 느리다.",
          value: "reason05",
        },
        {
          label: "반품/교환이 만족스럽지 못하다.",
          value: "reason06",
        },
        {
          label: "상담원 고객응대가 만족스럽지 못하다.",
          value: "reason07",
        },
        {
          label: "쿠폰, 적립금, 할인 등 쇼핑몰 혜택이 부족하다.",
          value: "reason08",
        },
        {
          label: "개인정보 유출이 염려된다.",
          value: "reason09",
        },
        {
          label: "기타",
          value: "reasonEtc",
        },
      ],
      commonCodeList: [],
      // 기타 선택시 textarea
      isTextarea: false,
      textareaData: "",
      // 임직원 여부
      isEmployee: true,
      payInfo: {},
      memberInfo: {},
      select: "",
      isAgree: false
    };
  },
  methods: {
    /**************************
     * 회원 정보 조회(쿠폰, 적립금, D포인트)
     **************************/
    getMemberInfo() {
      this.$http.post('/mypage/payInfo', {}).then(result => {
        if (result.statusCode == 200) {
          this.payInfo = result.data.payinfo;
          this.memberInfo = result.data.memberinfo;
        }
      });
    },
    /**************************
     * 회원 탈퇴 처리
     **************************/
    setMemberWithdraw() {
      if (this.$util.isNull(this.select)) {
        this.$eventBus.$emit('alert', '알림', "회원탈퇴 사유를 선택해주세요.");
        return;
      }
      if (this.select == "WDR010" && this.$util.isNull(this.textareaData)) {
        this.$eventBus.$emit('alert', '알림', "회원탈퇴 사유내용을 입력해주세요.");
        return;
      }
      if (this.select == "WDR010" && this.textareaData.length > 200) {
        this.$eventBus.$emit('alert', '알림', "회원탈퇴 사유내용은 200자까지 입력가능합니다.");
        return;
      }
      if (!this.isAgree) {
        this.$eventBus.$emit('alert', '알림', "회원탈퇴 동의를 해주세요.");
        return;
      }

      let param = {};
      if (this.select == "reasonEtc") {
        param.withdrawreason = this.textareaData;
      } else {
        for (let i = 0; i < this.reasonSelectOptions.length; i++) {
          if (this.commonCodeList[i].value == this.select) {
            param.withdrawreason = this.commonCodeList[i].label;
          }
        }
      }
      this.$http.post('/member/withdraw', param).then(result => {
        if (result.statusCode == 200) {
          this.logout();
        } else {
          this.$eventBus.$emit('alert', '알림', result.message);
        }
      })
    },
    /**************************
     * 로그아웃 처리
     **************************/
    logout(){
      this.$http.post("/member/logout", {}).then((result) => {
        if (result.statusCode == 200) {
          this.$router.push({ name: 'withdraw-complete' });
        }else {
          this.$eventBus.$emit('alert', '알림', result.message);
        }
      });
    },
    /**************************
    * 회원 탈퇴 취소시
    **************************/
    goToInfoModify() {
      this.$router.go(-1);
    }
  },
  mounted() {
    this.getMemberInfo();
  }
};