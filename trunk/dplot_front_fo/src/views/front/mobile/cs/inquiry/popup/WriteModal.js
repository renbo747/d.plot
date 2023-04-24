import ProductModal from './ProductModal.vue';
export default {
  props: {
    id: { type: String },
    param: Object,
    fnConfirm: { type: Function },
  },
  mounted() {    
    //this.Options = [];
    //this.orderList = []; //주문 번호 관련
    //this.orderNumber = ""; //선택했을 때 해당 주문번호 받아오기
    //his.chkGoodsList = []; //선택할 상품들 리스트

    if(this.$route.name === 'inquiry' || this.$route.name === 'cs-main' || this.$route.name === 'cs-cs-find-result'){
      this.$util.debug(this.$route.name + " 진입.......");
      this.InquiryCommon();
      if(!this.$util.isEmpty(this.param)){
        this.orderNumber = this.param.orderno;
        this.chkGoodsList = this.param.goodslist;
      }
    }

    if(this.$route.name === 'detail-inquiry'){
      this.$util.debug(this.$route.name + " 진입.......");
      this.InquiryCommon();
      this.text = "1:1문의 수정";
      this.idx = this.$route.params.idx;
      var iqParams = {
        idx: this.idx,
      }
      this.onInquiry(iqParams);

    }
    if(this.$route.name === 'detail-qna'){
      this.$util.debug(this.$route.name + " 진입.......");
      this.qnaCommon();
      this.text = "상품 Q&A 수정";
      this.idx = this.$route.params.idx;
      var qnaParams = {
          idx: this.idx,
      }
      this.onQna(qnaParams);


    }

  },
  data() {
    return {
      //* 공통 사용 시작 ////////////////////////////////////////
      idx: 0,              //수정 진입 시 전달할 idx
      text: "1:1문의 등록",         //문의 유형이 수정 or 등록
      placeHolder: "유형을 선택해주세요.",
      SelectType: "",
      Options: [],
      content: "",

      files:[],
      deletefilelist: [],

      //* 공통 사용 끝 //////////////////////////////////////////

      //* 1:1문의 - 주문상품선택 관련 시작 (OrdProductPCModal 쪽에도 선언 되어 있어야 함 )////////////////////////////////////////
      orderList: [], //주문 번호 관련
      orderNumber: "", //선택했을 때 해당 주문번호 받아오기
      chkGoodsList: [], //선택할 상품들 리스트
      //* 1:1문의 - 주문상품선택 관련 끝 ////////////////////////////////////////

      //* 상품 Q&A 관련 시작 ////////////////////////////////////////
      qnalist: [],
      qnaPopThumbnailData: {},
      skey:0,
      modalInfo: {
        comp: null,
        param: null,
        fnConfirm: () => {},
        fnCancel: () => {},
      },
      //* 상품 Q&A 관련 끝 ////////////////////////////////////////

    };
  },
  methods: {
    /*********************************
     * 1:1 문의유형 로드
     *********************************/
     InquiryCommon() {
      //공통코드 목록 불러오기
      let cmclassArr = ['INQUIRYTYPE'];
      //*
      this.$http.post('/common/code/map/list', {cmclass : cmclassArr, isloading: false}).then(result =>{
        for(var i=0; i<result.data.inquirytype.length; i++){
            var temp = {};
            temp.label = result.data.inquirytype[i].codename;
            temp.value = result.data.inquirytype[i].cmcode;
            //문의유형 화면쪽
            this.Options.push(temp);
        }
      })
    },
    /*********************************
     * Q&A 문의유형 로드
     *********************************/
     qnaCommon() {
      //공통코드 목록 불러오기
      let cmclassArr = ['QNATYPE'];
      //*
      this.$http.post('/common/code/map/list', {cmclass : cmclassArr, isloading: false}).then(result =>{
        for(var i=0; i<result.data.qnatype.length; i++){
            var temp = {};
            temp.label = result.data.qnatype[i].codename;
            temp.value = result.data.qnatype[i].cmcode;
            //문의유형 화면쪽
            this.Options.push(temp);
        }
      })
    },
    /*********************************
     * 1:1 문의 상세 진입
     *********************************/
    onInquiry(param) {
      this.$http.post('/cscenter/inquirydtl',param).then(result => {
        if(result.statusCode == 200){
            this.orderList = result.data.list.goodslist;
            this.files = result.data.list.files;
            this.placeHolder = result.data.list.inquirytypename;
            this.SelectType = result.data.list.inquirytype;
            //this.content = result.data.list.content;
            this.content = result.data.list.content.replace(/(<br>|<br\/>|<br \/>)/g, '\r\n');
            this.$refs.imageupload.bindFile(this.files);

            if(!this.$util.isEmpty(result.data.list.goodslist)){
              this.chkGoodsList = result.data.list.goodslist;
              this.orderNumber = result.data.list.goodslist[0].ordno;
            }
            if(!this.$util.isEmpty(this.param)){
              this.$util.debug(this.param);
              this.orderNumber = this.param.ordernumber;
              this.chkGoodsList = this.param.orderlist;
            }

            this.$nextTick(()=>{
              this.$util.debug(this.$refs);
    
              this.$refs.imageupload.bindFile(this.files);
            })
  

        }
      })
    },
    /*********************************
     * 상품 Q&A 상세 진입
     *********************************/
    onQna(param) {
      this.$http.post('/cscenter/qnadetail',param).then(result => {
        if(result.statusCode == 200){
            this.qnalist = result.data.list;
            this.files = result.data.list.files;
            this.placeHolder = result.data.list.qnatypename;
            this.SelectType = result.data.list.qnatype;
            this.content = result.data.list.content;
            this.content = this.content.replaceAll("<br/>","\n");
            this.qnaPopThumbnailData.fullpath = result.data.list.fullpath;
        }
      })
    },
    OptionCheck(val) {
      this.SelectType = val;
    },
    /**************************
     * 부모에서 전달받은 파일목록 매핑
     ****************************/
    bindFile(files){
      this.$util.debug("bindFile....");
      this.$util.debug(files);

      //저장된 파일리스트에 id값 부여
      for (let index = 0; index < files.length; index++) {
        files[index].id = 'files'+index;
        files[index].url = files[index].fullpath;
      }
      this.filelist = files;
    },
    /*********************************
     * 첨부파일 변경시 처리
     *********************************/
    changeFiles(files, deletelist) {

      this.files = files;
      this.deletefilelist = deletelist;
    },
    handleSelect() {
    },
    //모달
    openProductModal() {
      this.$util.debug("openProductModal .....");
      //선택된 체크박스
      let param ={
        chkordno: this.orderNumber,
        chkgoodslist: this.chkGoodsList,
      };
      this.modalInfo.comp = ProductModal;
      this.modalInfo.id = 'ProductModal';
      this.modalInfo.param = param;
      this.modalInfo.fnConfirm=(result)=>{
        this.$util.debug("ProductModal Close ....");
        this.$util.debug("ProductModal Close result data >>>>>>>>>>>>>>>>");
        this.$util.debug(result);        
        
        this.orderNumber = result.orderno;
        this.chkGoodsList = result.goodslist;
      }
      this.$nextTick(function () {
        this.$bvModal.show("ProductModal");
      });

      // this.$eventBus.$emit('showModal', ProductModal, 'ProductModal', param,(param)=>{
      

      // });
    },
    closeModal() {
      this.$util.debug("모달닫기");
      this.$bvModal.hide("WriteModal");
    },
    /**************************
     * 1:1 문의 등록 / 수정
     ****************************/
    registerInquiry() {
      this.$util.debug("registerInquiry....");

      if(this.$util.isNull(this.SelectType)){
        this.$eventBus.$emit('alert', '알림', "문의유형을 선택해주세요.");
        return;
      }
      if(this.$util.isNull(this.content)){
        this.$eventBus.$emit('alert', '알림', "내용을 입력해주세요.");
        return;
      }
      if(this.content.length > 500){
        this.$eventBus.$emit('alert', '알림', "내용은 최대 500자까지 입력가능합니다.");          
        return;
      }      

      this.$refs.imageupload.emitFileList();

      var regParams = {
        idx: this.idx,
        files: this.files,
        deletefilelist: this.deletefilelist,
        content: this.content,
        type: this.SelectType,

        //주문상품선택한 param
        ordno: this.orderNumber,
        goodslist: this.chkGoodsList,

      }
    
      this.$util.debug("registerInquiry file list >>>>>>>>>>>>>>>>>>>>>>>>>>>");
      this.$util.debug(JSON.stringify(this.file));
    
      if(this.idx == 0){
        //등록
        this.inquiryRegister(regParams);
      }else{
        //수정
        this.inquiryRevise(regParams);
      }
    },
    /**************************
     * 1:1 문의 저장
     ****************************/
    inquiryRegister(param) {
      this.$http.post('/cscenter/registerinq', param).then(result => {
        if (result.statusCode == 200) {
            this.$eventBus.$emit('alert', '알림', "등록이 완료되었습니다.");
          this.$router.push({name: 'inquiry'});
          this.fnConfirm(param);
          this.closeModal();       
        }
      })
    },
    /**************************
     * 1:1 문의 수정
     ****************************/
    inquiryRevise(param) {
      this.$http.post('/cscenter/reviseinq', param).then(result => {
        if (result.statusCode == 200) {
          this.$eventBus.$emit('alert', '알림', "수정이 완료되었습니다.");
          this.$router.push({name: 'detail-inquiry'});
          this.fnConfirm(param);
          this.closeModal();       
        }
      })  
    },
    /**************************
     * 상품 문의 수정
     ****************************/
    reviseQna(){
      this.$util.debug("reviseQna....");

      if(this.$util.isNull(this.SelectType)){
        this.$eventBus.$emit('alert', '알림', "문의유형을 선택해주세요.");
        return;
      }
      if(this.$util.isNull(this.content)){
        this.$eventBus.$emit('alert', '알림', "내용을 입력해주세요.");
        return;
      }
      if(this.content.length > 500){
        this.$eventBus.$emit('alert', '알림', "내용은 최대 500자까지 입력가능합니다.");          
        return;
      }      

      var param = {
        idx: this.idx,
        content: this.content,
        type: this.SelectType,
      }
      this.$http.post('/cscenter/reviseqna', param).then(result => {
        if (result.statusCode == 200) {
              this.$eventBus.$emit('alert', '알림', "수정이 완료되었습니다.");
              this.$router.replace({name: 'qna'});
              this.closeModal();
        }
      })
    },        
  },
  watch: {
  }
};