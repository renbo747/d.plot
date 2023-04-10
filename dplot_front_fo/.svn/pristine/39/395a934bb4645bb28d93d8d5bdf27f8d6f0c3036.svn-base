export default {
  props: {
    param: Object,
    fnConfirm: { type: Function },
  },
  data() {
    return {
      goodsno: "",
      saveType: "register", //진입시 수정인지 등록인지 확인 (등록: register, 수정: revise)
      placeHolder: "",
      qnatypeOptions: [],
      optionCode: "",
      SelectOptionCode: [],
      privateInquiryCheckbox: {},
      isInsert: true,
      btnText: "등록 완료", //수정/등록 화면 노출
      //수정인 경우 사용하는 것들 끝 /////////////////////////////////////////////////////

      //옵션 갯수 몇개인지 카운트
      optionCnt: 0,
      optionList: [],
      index: 0, //선택된 옵션이 없으면 0 , 순서대로 1번 선택되면 1 2번 선택되면 2 .....
      skey: 0,
      //옵션별 전달할 파라미터
      optionnm1: "",
      optionnm2: "",
      optionnm3: "",
      optionnm4: "",
      searchData: {
        idx: 0,
        qnatype: "",
        issecret: "F",
        content: "",
      },
    };
  },
  methods: {
    /*********************************
    * 옵션 갯수 검색 (모바일용)
    *********************************/
    onPopQna() {
      this.$http.post('/cscenter/option', { goodsno: this.searchData.goodsno }).then(result => {
        if (result.statusCode == 200) {
          this.optionList = result.data.optionlist;
          this.optionCnt = result.data.optionlist.length;
          for (var i = 0; i < result.data.optionlist.length; i++) {
            this.optionList[i].selected = "";
            this.optionList[i].detail = [];
          }
          this.getOptionDetail(0, "F");
        }
      })
    },
    /*********************************
    * 옵션 선택별 상세 리스트 조회
    *********************************/
    getOptionDetail(index, islast) {
      for (var i = 0; i < this.optionList.length; i++) {
        switch (i) {
          case 0:
            this.optionnm1 = this.optionList[i].selected;
            break;
          case 1:
            this.optionnm2 = this.optionList[i].selected;
            break;
          case 2:
            this.optionnm3 = this.optionList[i].selected;
            break;
          case 3:
            this.optionnm4 = this.optionList[i].selected;
            break;
        }
      }

      var dtlParam = {
        goodsno: this.searchData.goodsno,
        index: index,
        islast: islast,
        isloading: false,
        optionnm1: this.optionnm1,
        optionnm2: this.optionnm2,
        optionnm3: this.optionnm3,
        optionnm4: this.optionnm4,
      }
      this.$http.post('/cscenter/optiondtl', dtlParam).then(result => {
        if (result.statusCode == 200) {
          this.$util.debug("onPopQna optiondtl success...........");
          for (var i = 0; i < result.data.optiondtl.length; i++) {
            var temp = {};
            temp.label = result.data.optiondtl[i].optionnm;
            temp.optioncode = result.data.optiondtl[i].optioncode;
            if (islast == "T") {
              temp.value = result.data.optiondtl[i].optionnm + "|" + result.data.optiondtl[i].optioncode;
            } else {
              temp.value = result.data.optiondtl[i].optionnm;
            }

            if (index < this.optionList.length) {
              this.optionList[index].detail.push(temp);
            }
          }
          this.skey = Date.now();
        }
      })
    },
    /*********************************
    * 옵션 선택 이벤트 발생시(모바일 drop-select 용)
    *********************************/
    selectOption(item, index, selected) {
      for (var i = index + 1; i < this.optionList.length; i++) {
        this.optionList[i].selected = "";
        this.optionList[i].detail = [];
      }

      if (index + 1 == this.optionList.length - 1) {
        this.getOptionDetail(index + 1, "T");
      } else if (index + 1 == this.optionList.length) {
        for (let i = 0; i < this.optionList[index].detail.length; i++) {
          if (this.optionList[index].detail[i].value == selected) {
            this.searchData.optioncode = this.optionList[index].detail[i].optioncode;
          }
        }
        //alert( this.searchData.optioncode);
      } else {
        this.getOptionDetail(index + 1, "F");
      }
      this.skey = Date.now();

    },
    /*********************************
    * 수정 진입 시 내 옵션 가져오기
    *********************************/
    getSltOption() {
      this.$http.post("/cscenter/reviseopn", this.searchData).then((result) => {
        if (result.statusCode == 200) {
          this.$util.debug(result.data.seloption);
          var list = result.data.seloption;

          if (!this.$util.isNull(list.optionnm1)) {
            this.selectOption(list, 0, list.optionnm1);
            if (!this.$util.isNull(list.optionnm2)) {
              this.selectOption(list, 1, list.optionnm2);
              if (!this.$util.isNull(list.optionnm3)) {
                this.selectOption(list, 2, list.optionnm3);
                if (!this.$util.isNull(list.optionnm4)) {
                  this.selectOption(list, 3, list.optionnm4);
                }
              }
            }
          }
        }
      });
    },
    /*********************************
    * 저장 버튼 클릭 시
    *********************************/
    QnaMobileRegister() {
      if (this.searchData.qnatype == "") {
        this.$eventBus.$emit("alert", "알림", "문의유형을 선택해주세요.");
        return;
      }
      if (this.optionnm1 != "") {
        for (var i = 0; i < this.optionList.length; i++) {
          var detail = this.optionList[i].detail;
          if (
            !this.$util.isEmpty(detail) &&
            this.$util.isNull(this.optionList[i].selected)
          ) {
            this.$eventBus.$emit(
              "alert",
              "알림",
              "옵션 선택 시 모든 옵션을 선택해주세요."
            );
            return;
          }
        }
      }
      if (
        this.searchData.content == "" ||
        this.searchData.content.length < 10
      ) {
        this.$eventBus.$emit(
          "alert",
          "알림",
          "문의내용을 입력해주세요. (최소 10글자)"
        );
        return;
      }
      var param = {
        index: this.index,
        optionnm1: this.optionnm1,
        optionnm2: this.optionnm2,
        optionnm3: this.optionnm3,
        optionnm4: this.optionnm4,
      };
      param = Object.assign(param, this.searchData);
      if (this.isInsert) {
        this.register(param); //저장처리
      } else {
        this.revise(param); //수정처리
      }
    },
    /*********************************
    * 저장 처리
    *********************************/
    register(param) {
      this.$http.post('/cscenter/regqna', param).then(result => {
        if (result.statusCode == 200) {
          if (result.data.msg == "success") {
            this.$eventBus.$emit('alert', '알림', "등록이 완료되었습니다");
            this.$root.$emit("bv::hide::modal", 'QnaModal');
            this.$root.$emit("bv::hide::modal", 'QnaModalPC');
            this.fnConfirm(param);
          } else {
            this.$eventBus.$emit('alert', '알림', "등록이 실패되었습니다");
          }
        }
      })
    },
    /*********************************
    * 수정 처리
    *********************************/
    revise(param) {
      this.$http.post('/cscenter/revqna', param).then(result => {
        if (result.statusCode == 200) {
          if (result.data.msg == "success") {
            this.$eventBus.$emit('alert', '알림', '수정이 완료되었습니다');
            this.$root.$emit("bv::hide::modal", 'QnaModal');
            this.$root.$emit("bv::hide::modal", 'QnaModalPC');
            this.fnConfirm(param);
          } else {
            this.$eventBus.$emit('alert', '알림', "수정이 실패되었습니다");
          }
        }
      })
    },
  },
  created() {
    /**************************************
     * 문의 유형 목록 조회
     **************************************/
    let cmclassArr = ["QNATYPE"];
    this.$http
      .post("/common/code/map/list", { cmclass: cmclassArr, isloading: false })
      .then((result) => {
        this.qnatypeOptions = [];
        for (var i = 0; i < result.data.qnatype.length; i++) {
          var temp = {};
          temp.label = result.data.qnatype[i].codename;
          temp.value = result.data.qnatype[i].cmcode;
          this.qnatypeOptions.push(temp);
        }
      });
  },
  mounted() {
    /************************
     * 기본 값 셋팅
     ****************************/
    //공통 데이터 셋팅
    this.searchData.goodsno = this.param.goodsno;
    this.isInsert = this.param.savetype == "register" ? true : false; //저장:true,수정:false
    this.btnText = this.isInsert ? "등록 완료" : "수정 완료";
    //상품 옵션 조회
    this.onPopQna();

    //수정처리 화면일시 데이터 셋팅
    if (!this.isInsert) {
      this.searchData.idx = this.param.list.idx;
      this.searchData.qnatype = this.param.list.qnatype;
      this.searchData.issecret = this.param.list.issecret;
      this.searchData.content = this.param.list.content.replaceAll(
        "<br/>",
        "\n"
      );
      //기 선택 옵션코드 조회
      if (!this.$util.isNull(this.param.list.optioncode)) {
        this.searchData.optioncode = this.param.list.optioncode;
        this.getSltOption();
      }
    }
  },
};