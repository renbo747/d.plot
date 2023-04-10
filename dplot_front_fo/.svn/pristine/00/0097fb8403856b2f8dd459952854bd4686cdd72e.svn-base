import InfiniteLoading from "vue-infinite-loading";
import WriteModal from './popup/WriteModal.vue';

export default {
  components: {
    InfiniteLoading
  },
  props: {
    fnConfirm: { type: Function },
  },
  created() {
    this.$store.commit("onSubHeaderOption", {
      title: "나의 문의내역",
      searchIcon: true,
      cartIcon: true,
      homeIcon: false,
    });
  },
  mounted() {
    this.isEnd = false;
    if ((this.$route.name).indexOf('inquiry') != -1) {
      this.inquiryCommon();
      var inquiryParams = {
        perpage: this.listCnt,
        startpage: 1,
        inquirytype: this.inquirySelect,
        lastmonth: this.monthSelect,
      }
      this.onInquiry(inquiryParams);
    }

    if ((this.$route.name).indexOf('qna') != -1) {
      this.qnaTypeCommon();

      var qnaParams = {
        perpage: this.listCnt,
        startpage: 1,
        qnatype: this.qnaTypeSelect,
        lastmonth: this.monthSelect,
      }
      this.onQna(qnaParams);
    }
    this.$nextTick(() => {
      this.$util.scrollToTop();
    });
  },
  data() {
    return {
      //공통 사용 시작 /////////////////////////////////////////////////////////
      infiniteId: + new Date(),
      isloading: true,
      currentPage: 1,        //현재페이지
      totalPage: 5,         // 페이징영역 노출 최대 페이지(1~N, 1+N~N+N)        
      listTotal: 0,        //조회 목록 전체 수
      listCnt: 20,        //페이지 목록 수

      monthSelect: "1",
      monthOption: [
        {
          label: "최근 1개월",
          value: "1",
        },
        {
          label: "최근 3개월",
          value: "3",
        },
        {
          label: "최근 6개월",
          value: "6",
        },
        {
          label: "최근 1년",
          value: "12",
        },
      ],
      //공통 사용 끝 /////////////////////////////////////////////////////////

      //1:1 문의만 사용 시작 /////////////////////////////////////////////////
      inquirySelect: "all",
      inquiryOption: [
        {
          label: "전체문의",
          value: "all",
        },
      ],
      //문의 사항 리스트
      inquiryList: [],
      //1:1 문의만 사용 끝 /////////////////////////////////////////////////

      //상품 Q&A 만 사용 시작 //////////////////////////////////////////////
      qnaTypeSelect: "",
      qnaOptions: [
        {
          label: "전체문의",
          value: "all",
        },
      ],
      qnaList: [],
      isEnd: false,
      isToTop:false
      //상품 Q&A 만 사용 끝   //////////////////////////////////////////////
    };
  },
  methods: {
    openWriteModal() {
      var param = {}
      this.$eventBus.$emit('showModal', WriteModal, 'WriteModal', param, (param) => {
        var inquiryParams = {
          perpage: this.listCnt,
          startpage: 1,
          inquirytype: this.inquirySelect,
          lastmonth: this.monthSelect,
        }
        this.onInquiry(inquiryParams);
      });
    },
    /**************************
     * inquirytype 호출
     ****************************/
    inquiryCommon() {
      this.$util.debug("inquiryCommon.......");
      //공통코드 목록 불러오기
      let cmclassArr = ['INQUIRYTYPE'];
      this.$http.post('/common/code/map/list', { cmclass: cmclassArr, isloading: false })
        .then(result => {
          for (var i = 0; i < result.data.inquirytype.length; i++) {
            var temp = {};
            temp.label = result.data.inquirytype[i].codename;
            temp.value = result.data.inquirytype[i].cmcode;
            this.inquiryOption.push(temp);
          }
        })
    },
    /**************************
     * Q&A 문의 유형 코드 호출
     ****************************/
    qnaTypeCommon() {
      //공통코드 목록 불러오기
      let cmclassArr = ['QNATYPE'];
      this.$http.post('/common/code/map/list', { cmclass: cmclassArr, isloading: false })
        .then(result => {
          for (var i = 0; i < result.data.qnatype.length; i++) {
            var temp = {};
            temp.label = result.data.qnatype[i].codename;
            temp.value = result.data.qnatype[i].cmcode;
            this.qnaOptions.push(temp);
          }
        })
    },
    /**************************
     * 1:1 문의 메인 화면 접근
     ****************************/
    onInquiry(param) {
      //1:1 문의 데이터 로드
      this.isEnd = false;
      this.$http.post('/cscenter/inquiry', param).then(result => {
        if (result.statusCode == 200) {
          this.inquiryList = [];
          this.currentPage = param.startpage;
          this.listTotal = result.data.listcount;
          this.inquiryList = result.data.list;
        }
        this.isEnd = true;
      })
    },
    /**************************
     * Q&A 메인 화면 접근
     ****************************/
    onQna(param) {
      this.isEnd = false;
      this.$http.post('/cscenter/qna', param).then(result => {
        if (result.statusCode == 200) {
          this.qnaList = [];
          this.qnaList = result.data.list;
          this.listTotal = result.data.listcount;
          this.currentPage = param.startpage;
        }
        this.isEnd = true;
      })
    },
    /**************************
     * 1:1 문의 글 상세 접근
     ****************************/
    goToInquiryDetail(idx) {
      this.$router.push("/cs/inquiry/detail");
    },
    /**************************
     * 1:1 문의 / 상품 Q&A 탭 전환
     ****************************/
    changeTap(name) {
      this.$util.debug("changeTap.......");
      this.isEnd = false;
      if (this.$route.name.indexOf('mypage') != -1) {
        name = "mypage-" + name;
      }
      if (this.$route.name !== name) {
        this.$router.replace({ name: name });
      }
    },
    /**************************
     * 1:1 문의 개월 수 선택
     ****************************/
    iqDateCheck(val) {
      this.$util.debug("iqDateCheck.......");
      this.$util.debug(val);

      this.monthSelect = val;

      var inquiryParams = {
        perpage: this.listCnt,
        startpage: 1,
        inquirytype: this.inquirySelect,
        lastmonth: this.monthSelect,
      }
      this.isloading = true;
      this.onInquiry(inquiryParams);

    },
    /**************************
     * 1:1 문의 유형 선택
     ****************************/
    inquiryCheck(val) {
      this.$util.debug("inquiryCheck.......");
      this.$util.debug(val);

      this.inquirySelect = val;

      var inquiryParams = {
        perpage: this.listCnt,
        startpage: 1,
        inquirytype: this.inquirySelect,
        lastmonth: this.monthSelect,
      }
      this.isloading = true;
      this.onInquiry(inquiryParams);

    },
    /**************************
     * 1:1 문의 상세 진입
     ****************************/
    goInqDetail(idx) {
      if (this.$route.name.indexOf('mypage') != -1) {
        this.$router.push({ name: 'mypage-detail-inquiry', params: { idx: idx } });
      } else {
        this.$router.push({ name: 'detail-inquiry', params: { idx: idx } });
      }
    },
    /**************************
     * 1:1 문의 페이징
     ****************************/
    inquiryPage(page) {
      this.$util.debug("inquiryPage.......");
      this.$util.debug(page);

      var inquiryParams = {
        perpage: this.listCnt,
        startpage: page,
        inquirytype: this.inquirySelect,
        lastmonth: this.monthSelect,
      }
      this.onInquiry(inquiryParams);
    },
    /**************************
     * Q&A 개월 수 선택
     ****************************/
    qnaDateCheck(val) {
      this.$util.debug("qnaDateCheck.......");
      this.$util.debug(val);

      this.monthSelect = val;

      var qnaParams = {
        perpage: this.listCnt,
        startpage: 1,
        qnatype: this.qnaTypeSelect,
        lastmonth: this.monthSelect,
      }
      this.isloading = true;
      this.onQna(qnaParams);

    },
    /**************************
     * Q&A 문의 유형 선택
     ****************************/
    qnaCheck(val) {
      this.$util.debug("qnaCheck.......");
      this.$util.debug(val);

      this.qnaTypeSelect = val;

      var qnaParams = {
        perpage: this.listCnt,
        startpage: 1,
        qnatype: this.qnaTypeSelect,
        lastmonth: this.monthSelect,
      }
      this.isloading = true;
      this.onQna(qnaParams);

    },
    goInquiryReg() {
      this.$util.debug("goInquiryReg......");
      if (this.$route.name.indexOf('mypage') != -1) {
        this.$router.push({ name: 'mypage-write-inquiry-register' });
      } else {
        this.$router.push({ name: 'write-inquiry-register' });
      }
    },
    /**************************
     * Q&A 상세 진입
     ****************************/
    goQnaDetail(idx) {
      if (this.$route.name.indexOf('mypage') != -1) {
        this.$router.push({ name: 'mypage-detail-qna', params: { idx: idx } });
      } else {
        this.$router.push({ name: 'detail-qna', params: { idx: idx } });
      }
    },
    /**************************
     * Q&A 비밀글 여부 아이콘
     ****************************/
    handleIcon(index) {
      if (index === "T") {
        this.isDelivery = true;
      }
    },
    /**************************
     * Q&A 페이징
     ****************************/
    qnaPage(page) {
      this.$util.debug("qnaPage.......");
      this.$util.debug(page);

      var qnaParams = {
        perpage: this.listCnt,
        startpage: page,
        qnatype: this.qnaTypeSelect,
        lastmonth: this.monthSelect,
      }
      this.onQna(qnaParams);

    },
    /*********************
     *  1:1 문의 스크롤페이징 처리
     ***********************/
    iqInfiniteHandler($state) {
      this.$util.debug("infiniteHandler.......");

      if (this.isloading) {
        this.isEnd = false;
        var inquiryParams = {
          perpage: this.listCnt,
          startpage: this.currentPage + 1,
          inquirytype: this.inquirySelect,
          lastmonth: this.monthSelect,
        }
        this.$http.post('/cscenter/inquiry', inquiryParams).then(result => {
          if (result.statusCode == 200) {
            if (result.data.list.length == 0) {
              $state.complete();
              this.isloading = false;
            } else {
              $state.loaded();
              this.isloading = true;
              this.currentPage += 1;
              this.inquiryList = [...this.inquiryList || [], ...result.data.list];
            }
          }
          this.isEnd = true;
          if (!this.isToTop) {
            this.$nextTick(() => {
                this.$util.scrollToTop();
                this.isToTop = true;
            });
        }
        });
      }

    },
    /*********************
     *  Q&A 스크롤페이징 처리
     ***********************/
    qnaInfiniteHandler($state) {
      if (this.isloading) {
        this.isEnd = false;
        var qnaParams = {
          perpage: this.listCnt,
          startpage: this.currentPage + 1,
          qnatype: this.qnaTypeSelect,
          lastmonth: this.monthSelect,
        }
        this.$http.post('/cscenter/qna', qnaParams).then(result => {
          if (result.statusCode == 200) {
            if (result.data.list.length == 0) {
              $state.complete();
              this.isloading = false;
            } else {
              $state.loaded();
              this.isloading = true;
              this.currentPage += 1;
              this.qnaList = [...this.qnaList || [], ...result.data.list];
            }
          }
          if (!this.isToTop) {
            this.$nextTick(() => {
                this.$util.scrollToTop();
                this.isToTop = true;
            });
        }
          this.isEnd = true;
        });
      }
    },

  },
};