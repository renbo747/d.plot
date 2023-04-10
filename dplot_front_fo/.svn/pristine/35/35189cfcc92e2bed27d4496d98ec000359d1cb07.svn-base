export default {
    beforeCreate() {
      this.$store.commit("onSubHeaderOption", {
        title: "고객센터",
        searchIcon: true,
        cartIcon: true,
        homeIcon: false,
      });
    },
    props: {
      id: { 
        type: String,
      },
    },
    mounted() {
    },      
    data() {
      return {
        mobileNo: "",
        searchKeyword: "",
        faqData: [],
        textareaData: "",
        noticeData: [],

        inputListData: [
          {
            id: "input01",
            label: "업체명",
            placeholder: "업체명을 입력해주세요",
            ref:"busname",
            value: "",
          },
          {
            id: "input02",
            label: "사업자등록번호",
            placeholder: "사업자등록번호를 입력해주세요",
            ref:"busnum",
            value: "",
          },
          {
            id: "input03",
            label: "홈페이지 URL",
            placeholder: "URL을 입력해주세요",
            ref:"url",
            value: "",
          },
          {
            id: "input04",
            label: "제안상품명(주요상품)",
            placeholder: "내용을 입력해주세요",
            ref:"goods",
            value: "",
          },
          {
            id: "input05",
            label: "브랜드 자사몰 / 판매처 URL",
            placeholder: "URL을 입력해주세요",
            ref:"brand",
            value: "",
          },
          {
            id: "input06",
            label: "담당자명/직급",
            placeholder: "담당자정보를 입력해주세요",
            ref:"name",
            value: "",
          },
          {
            id: "input07",
            label: "담당자 메일 주소",
            placeholder: "이메일주소를 입력해주세요",
            ref:"email",
            value: "",
          },
        ],
        StoreChkBox: {
          id: "sendChk",
          checked: false,
        },
      };
    },
    methods: {
      //pc만 입점문의 링크
      goStoreInquiryPC(){
        this.$router.push({name:'cs-inquiry-store-inquiry'});

      },
      sendChk(){
        this.$util.debug("click");         
      },
      registerStore() {
        if(this.inputListData[0].value == ""){
          this.$eventBus.$emit('alert', '알림', "업체명을 입력해주세요.");
          this.$refs.busname[0].$refs.binput.focus();
          return;
        }
        if(this.inputListData[1].value == ""){
          this.$eventBus.$emit('alert', '알림', "사업자번호를 입력해주세요.");
          this.$refs.busnum[0].$refs.binput.focus();
          return;
        }
        // if(this.inputListData[2].value == ""){
        //   this.inputListData[2].value = "-";
        // }
        if(this.inputListData[3].value == ""){
          this.$eventBus.$emit('alert', '알림', "제안상품 정보를 입력해주세요.");
          this.$refs.goods[0].$refs.binput.focus();
          return;
        }
        // if(this.inputListData[4].value == ""){
        //   this.inputListData[4].value = "-";
        // }
        if(this.inputListData[5].value == ""){
          this.$eventBus.$emit('alert', '알림', "담당자명/직급을 입력해주세요.");
          this.$refs.name[0].$refs.binput.focus();
          return;
        }
        if(this.inputListData[6].value == ""){
          this.$eventBus.$emit('alert', '알림', "담당자 메일주소를 입력해주세요.");
          this.$refs.email[0].$refs.binput.focus();
          return;
        }
        if(this.mobileNo == ""){
          this.$eventBus.$emit('alert', '알림', "담당자 연락처를 입력해주세요.");
           this.$refs.tel.$refs.binput.focus();
          return;
        }
        // if(this.textareaData == ""){
        //   this.textareaData = "-";
        // }
        if(!this.StoreChkBox.checked){
          this.$eventBus.$emit('alert', '알림', "사업자정보 수집 및 이용에 동의해주세요.");
          return;
        }

        var param = {
          companyname: this.inputListData[0].value,
          businessnumber: this.inputListData[1].value,
          homepageurl: this.$util.isNull(this.inputListData[2].value)?"-":this.inputListData[2].value,
          mainproduct: this.inputListData[3].value,
          brandurl: this.$util.isNull(this.inputListData[4].value)?"-":this.inputListData[4].value,
          manager: this.inputListData[5].value,
          manageremail: this.inputListData[6].value,
          managerphone: this.mobileNo,
          etc: this.$util.isNull(this.textareaData)?"-":this.textareaData,
          sendchk: this.StoreChkBox.checked,
        }

        this.$util.debug(param);         
        this.$http.post('/cscenter/store',param).then(result => {
          if(result.statusCode == 200){
            if(result.data.httpcode == "401"){
                this.$eventBus.$emit('alert', '알림', result.data.httpmessage);
            }else{
                this.$eventBus.$emit('alert', '알림', "입점/제휴 문의가 등록되었습니다.", () => {
                  this.$router.push({name: 'shop'});    
                });
                //this.$bvModal.hide('storeInquiryModal');
            }
          }
        })
  

      },
      getPhoneMask(val) {
        let res = this.$util.getMask(val);
        this.mobileNo = res;
      },
    },
  };