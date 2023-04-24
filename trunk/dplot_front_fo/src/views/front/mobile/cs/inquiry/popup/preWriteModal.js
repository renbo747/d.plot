import ProductModal from './ProductModal.vue';
export default {
  props: {
    id: { type: String },
    param: Object,
    fnConfirm: { type: Function },
  },
  mounted() {    
    if(this.$route.name === 'inquiry'){
      this.$util.debug(this.$route.name + " 진입.......");
      this.InquiryCommon();
    }

    if(this.$route.name === 'detail-inquiry'){
      this.$util.debug(this.$route.name + " 진입.......");
      this.InquiryCommon();
      this.text = "1:1 문의 수정";
      this.idx = this.$route.query.idx;
      var iqParams = {
        idx: this.idx,
      }
      this.onInquiry(iqParams);

    }
    if(this.$route.name === 'detail-qna'){
      this.$util.debug(this.$route.name + " 진입.......");
      this.qnaCommon();
      this.text = "상품 Q&A 수정";
      this.idx = this.$route.query.idx;
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
      text: "1:1 문의 등록",         //문의 유형이 수정 or 등록
      placeHolder: "유형을 선택해주세요.",
      SelectType: "",
      Options: [],
      content: "",

      files:[],
      deletefilelist: [],

      //* 공통 사용 끝 //////////////////////////////////////////


      data: {},
      pagetype: "",
      //모달 데이터
      //등록이면 T, 수정이면 F
      savetype: "T",
      optionHolder: "유형을 선택해주세요.",
      type: "등록",
      modalParam: {},
      filelist: [],
      isChangeProduct: true, //주문상품선택 true , 상품선택 시 변환되는 주문상품변경 false
      isShow: true, //주문상품변경 true 일때 나오는 주문상품명
      // 1:1 문의 등록 모달 셀렉트 박스
      detaildata: {},
      textareaData: "",
      registerModalData: [
        {
          id: 1,
          src: require("@/assets/mobile/img/shop/img-shop-card01.jpg"),
          alt: "이미지 미리보기",
          type: "image",
        },
      ],
      //상품선택모달
      productCartData: [
        /*           {
                    id: "productData01",
                    src: require("@/assets/mobile/img/shop/img-shop-product01.jpg"),
                    ctg: "Artemide",
                    title: "피어스 마이크로 펜던램프",
                    priceNumber: 560000,
                  }, */
      ],

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
    onInquiry(param) {
      this.$http.post('/cscenter/inquirydtl',param).then(result => {
        if(result.statusCode == 200){
            this.orderList = result.data.list.goodslist;
            this.files = result.data.list.files;
            this.placeHolder = result.data.list.inquirytypename;
            this.SelectType = result.data.list.inquirytype;
            this.content = result.data.list.content;
            this.$refs.imageupload.bindFile(this.files);

            if(!this.$util.isEmpty(result.data.list.goodslist)){
              this.chkGoodsList = result.data.list.goodslist;
              this.orderNumber = result.data.list.goodslist[0].ordno;
            }

            this.$nextTick(()=>{
              this.$util.debug(this.$refs);
    
              this.$refs.imageupload.bindFile(this.files);
            })
  

        }
      })
    },
    OptionCheck(val) {
      this.SelectType = val;
    },
    onInit: function () {
      this.type = this.param.type;
      this.pagetype = "isInquiry";
      this.SelectOptions = [];

      if (this.type == '수정') {
        this.selectOptionData = this.param.selectOptionData;
        this.textareaData = this.param.textareaData;
        this.type = this.param.type;
        this.detaildata = this.param.detaildata;
        this.optionHolder = this.param.detaildata.inquiry;
        this.selectOptionData = this.param.detaildata.inquirytypecode;
        this.textareaData = this.param.detaildata.content;
        this.files = this.param.detaildata.files;
        this.savetype = "F";
        this.idx = this.param.detaildata.idx;
        
      }

      //공통코드 목록 불러오기
      let cmclassArr = ['INQUIRYTYPE'];
      this.$http.post('/common/code/map/list', { cmclass: cmclassArr, isloading: false })
        .then(result => {
          for (var i = 0; i < result.data.inquirytype.length; i++) {
            var temp = {};
            temp.label = result.data.inquirytype[i].codename;
            temp.value = result.data.inquirytype[i].cmcode;
            this.SelectOptions.push(temp);    
          }

          this.$nextTick(()=>{
            this.$util.debug(this.$refs);
  
            this.$refs.imageupload.bindFile(this.files);
          })

        })
      //*

      //*/
    },
    onQna: function () {
      this.type = this.param.type;
      this.pagetype = "ProductQna";
      this.SelectOptions = [];

      if (this.type == '수정') {
        this.selectOptionData = this.param.selectOptionData;
        this.textareaData = this.param.textareaData;
        this.type = this.param.type;
        this.detaildata = this.param.detaildata;
        this.optionHolder = this.param.detaildata.inquiry;
        this.selectOptionData = this.param.detaildata.inquirytypecode;
        this.textareaData = this.param.detaildata.content;
        this.files = this.param.detaildata.files;
        this.savetype = "F";
        this.idx = this.param.detaildata.idx;
        
      }
      this.SelectOptions = [];
      //공통코드 목록 불러오기
      let cmclassArr = ['QNATYPE'];
      this.$http.post('/common/code/map/list', { cmclass: cmclassArr, isloading: false })
        .then(result => {
          for (var i = 0; i < result.data.qnatype.length; i++) {
            var temp = {};
            temp.label = result.data.qnatype[i].codename;
            temp.value = result.data.qnatype[i].cmcode;
            this.SelectOptions.push(temp);
          }
        })
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
    ProductModalopen() {
      var param = {
        pagetype: this.pagetype,
      }
      this.$eventBus.$emit('showModal', ProductModal, 'ProductModal', param,(param) =>{
        
      });      
    },
    //모달
    openProductModal() {
      var param = {
        pagetype: this.pagetype,
      }
      this.$eventBus.$emit('showModal', ProductModal, 'ProductModal', param);
    },
    closeModal() {
      this.$util.debug("모달닫기");
      this.$bvModal.hide("WriteModal");
    },
    //등록하기 버튼
    registerInquiry() { 
      if (this.$util.isNull(this.selectOptionData)) {
        this.$eventBus.$emit('alert', '알림', "문의유형을 선택해주새요.");
        return;
      }
      if (!this.$util.isNull(this.textareaData)) {
        if (this.textareaData.length >= 500) {
          this.$eventBus.$emit('alert', '알림', "내용은 500자까지 입력가능합니다.");
          return;
        }
      }
      this.$refs.imageupload.emitFileList();
      var regParams = {
        idx: this.idx,
        files: this.files,
        deletefilelist: this.deletefilelist,
        textareaData: this.textareaData,
        typeSelect: this.selectOptionData,
        savetype: this.savetype,
      }


      this.$http.post('/cscenter/registerinq', regParams).then(result => {
        if (result.statusCode == 200) {
          if (this.savetype == 'T') {
            this.$eventBus.$emit('alert', '알림', "등록이 완료되었습니다.");
          }
          if (this.savetype == 'F') {
            this.$eventBus.$emit('alert', '알림', "수정이 완료되었습니다.");
          }
          this.fnConfirm();
          this.closeModal();
        } else {
          this.$eventBus.$emit('alert', '알림', result.message);
        }
      })
    },
  },
  watch: {
    'param'(val) {
      this.type = val.type;
    }
  }


};