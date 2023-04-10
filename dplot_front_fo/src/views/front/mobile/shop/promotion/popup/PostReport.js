export default {
    props: {
      param: Object,
    },
    data() {
      return {
        reportRadio: "",
        etcreason:"",
        commonCodeList: [],
      };
    },
    methods: {
      /****************************
       * 공통코드 조회
       ****************************/
      getCommonCode() {
        let param = { cmclass: "REPORTTYPE" };
        this.$http.post("/common/code/list", param).then((result) => {
          this.$util.debug("********** 신고하기 공통코드 조회***********");
          this.$util.debug(result);
          this.commonCodeList = result.data.list;
          this.$util.debug(this.commonCodeList);
        });
      },
      /****************************
       * 라디오 변경시 라디오 선택값 변경
       ****************************/
      changePostReportData(id) {
         // this.reportRadio = id;
          this.$util.debug("ssss");
          this.$util.debug(this.reportRadio); 
      },
      /*************************
       * 신고하기 처리
       *************************/
      saveReport() {
          let param = {
              commentidx:this.param.commentidx,
              reporttype:this.reportRadio,
              etcreason:this.etcreason
          }
          if (this.$util.isNull(param.commentidx)) {
              this.$eventBus.$emit("alert", "알림", "신고할 댓글이 없습니다.");
              return;
          }
          if (this.$util.isNull(param.reporttype)) {
              this.$eventBus.$emit("alert", "알림", "신고사유를 선택해 주세요.");
              return;
          }
          if (param.notitype == "CRT008") {
              if (param.etcreason.length < 10) {
                  this.$eventBus.$emit("alert", "알림", "신고내용을 10자 이상 작성해주세요.");
                  return;
              }
              if (param.etcreason.length > 500) {
                  this.$eventBus.$emit("alert", "알림", "신고내용은 500자까지 작성 가능합니다.");
                  return;
              }
          }
          this.$util.debug(param);
          this.$http.post('/shop/repreport', param).then(result => {
              if (result.statusCode == 200) {
                  this.$eventBus.$emit("alert", "알림", "해당 댓글이 신고되었습니다.",()=>{
                      this.$bvModal.hide('postReportModal');
                  });
              }else {
                  this.$eventBus.$emit("alert", "알림", result.message);
              }
          })
      }
    },
    mounted() {
      this.getCommonCode();
    },
  };