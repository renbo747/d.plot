import { swiper, swiperSlide } from "vue-awesome-swiper";
import ImageUpload from "@views.mobile/components/ImageUpload.vue";

import Delivery from "@views.mobile/shop/popup/Delivery.vue";
import CardDiscount from "@views.mobile/shop/popup/CardDiscount.vue";
import CardInterest from "@views.mobile/shop/popup/CardInterest.vue";
import goodsnotify from "@views.mobile/shop/popup/GoodsNotify.vue";
import Dealer from "@views.mobile/shop/popup/Dealer.vue";
import AsInfo from "@views.mobile/shop/popup/AsInfo.vue";
import Point from "@views.mobile/shop/popup/Point.vue";
import GradePoint from "@views.mobile/shop/popup/GradePoint.vue";
import Gift from "@views.mobile/shop/popup/Gift.vue";
import QnaModal from "@views.mobile/shop/popup/QnaModal.vue";
import WriteModal from '@views.mobile/cs/inquiry/popup/WriteModal.vue';
import QnaModalPC from "@views.pc/shop/popup/QnaModalPC.vue";
import SnsShareModal from "@views.mobile/shop/popup/SnsShareModal.vue";
import CouponDownload from "@views.mobile/order/popup/CouponDownload.vue";
import DetailProduct from '@views.common/components/shop/DetailProduct.vue'
export default {
  props: {
    fnConfirm: { type: Function },
  },
  components: {
    swiper,
    swiperSlide,
    ImageUpload,
    DetailProduct
  },
  beforeCreate() {
    this.$store.commit("onSubHeaderOption", {
      title: "",
      searchIcon: true,
      cartIcon: true,
      homeIcon: true,
    });
  },
  data() {
    return {
      btnBuy : '',
      isnonmember : 'F',
      searchType: "All",
      goodscode: 0,
      QnaListBtn: true,
      discountInfo : {
        isshow : false,
        content: '',
        disamount : 0,
      },
      discountInfo2 : {
        isshow : false,
        content: '',
        disamount : 0,
      },
      /*todo: (fe) 상품 상세 슬라이드 고정데이터로 되어있음 변경 요망*/
      swiperOptionTop: {
        loop: true,
        slidesPerView: 1, // 슬라이드 개수
        //notNextTick: true,
        thumbs: {
          swiper: this.swiperOptionBottom,
        },
      },
      swiperOptionBottom: {
        loop: true, // 슬라이드 반복여부
        spaceBetween: 10, // 슬라이드 간격
        slidesPerView: 8, // 슬라이드 개수
        //notNextTick: true,
        //centeredSlides: true, // true 인 경우 활성 슬라이드는 항상 왼쪽이 아닌 가운데에 배치
        touchRatio: 0.2, //터치 비율을 조정
        slideToClickedSlide: true, // 해당 슬라이드 클릭시 슬라이드 위치로 이동
      },
      detail: {
        swiperlist: [],
        deliv: {
          delivtypename: '',
          delivschtypename : '',
        },
        isnewjoincoupon: 'T', //신규회원 혜택가 노출
      },
      selectImg: '',
      selectedItem: [],
      cardBenefit: {
        discountCards: [],
        interestFreeCards: []
      },
      recopick: process.env.VUE_APP_RECOPICK_SHOP,
      isEmp: false,
      isPlay: false,
      isBtnOpen: false,
      isBuyBottomSheet: false,
      isDetailWish: false,
      isProductDetailMore: false,
      swiperOption: {
        pagination: {
          el: ".swiper-pagination",
        },
      },
      photoSwiperOptions: {
        slidesPerView: "auto",
        spaceBetween: 10,
        loop: true,
        observeParents: true,
        observer: true,
        navigation: {
          nextEl: ".swiper-button-next",
          prevEl: ".swiper-button-prev",
        },
        centeredSlides: true,
      },
      inquiryType: [
        {
          id: "inquiry_All",
          label: "전체",
          checked: true,
        },
        {
          id: "inquiry_F",
          label: "미답변",
          checked: false,
        },
        {
          id: "inquiry_T",
          label: "답변완료",
          checked: false,
        },
        {
          id: "inquiry_My",
          label: "내문의",
          checked: false,
        },
      ],
      inquiryData : [],
      //상품문의 작성 시작 /////////////////////////////////////////////////////////////
      currentPage: 1,
      totalPage: 5,         // 페이징영역 노출 최대 페이지(1~N, 1+N~N+N)        
      listTotal: 0,        //조회 목록 전체 수
      listCnt: 5,        //페이지 목록 수  

      chkbox: "F",
      qnatype: "",
      placeHolder: "",
      qnatypeOptions: [],
      optionCode: "",
      SelectOptionCode: [],
      privateInquiryCheckbox: {},
      qnaTextArea: "",

      //수정인 경우 사용하는 것들 시작 /////////////////////////////////////////////////////
      idx: 0,
      saveType: "register",           //진입시 수정인지 등록인지 확인 (등록: register, 수정: revise)
      btnText: "등록 완료",           //수정/등록 화면 노출
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

      //상품문의 작성 끝 //////////////////////////////////////////////////////////////////
      reserve : {
        purcfmamt : 0
      },
      promreserve : {
        reservepoint : 0
      },
      downCouponList : [],
      isReview: false,
      isScrollTab : false,
      isScrollSheet: false,
      nowActive: 'detail',
      couponcnt: 0,
      isFirstOrder: 'F',
      totreserve: 0,
      totsaleamt: 0
    };
  },
  created() {
    //레코픽 다른스타일 추천
  },
  mounted() {
    if (this.$util.isBlank(this.$route.params.pid)) {
      // this.$eventBus.$emit('alert', '알림', '상품정보가 없습니다.');
      this.$eventBus.$emit('alert','알림', '상품정보가 없습니다.', ()=>{
        // this.$router.replace('/')
        this.$router.go(-1);
      });
      //this.$router.replace({name : 'main'});
      return;
    }

    window.addEventListener("scroll", this.scroll);
    window.addEventListener("scroll", this.tabScroll);
    
    this.goodscode = this.$route.params.pid;
    this.getDetail();
    this.productQna();
  },
  beforeDestroy() {
    document.documentElement.style.overflow = "auto";
    window.removeEventListener("scroll", this.scroll);
    window.removeEventListener("scroll", this.tabScroll);
  },
  methods: {
    /***************************
     *특정 ref로 스크롤 이동처리
     ************************/
    scroll() {
      let scH = window.scrollY;
      if (scH > 1067) {
        this.isScrollSheet = true;
      } else {
        this.isScrollSheet = false;
      }

      let review = this.$refs.review.offsetTop - 150;
      let inquiry = this.$refs.inquiry.offsetTop -150;

      if (window.sessionStorage.getItem('platform') == "MAC001") {
        review -= 50;
        inquiry -= 50;
      }

      if(scH < review) {
        this.nowActive = 'detail';
      } else if (review <= scH && inquiry > scH) {
        this.nowActive = 'review';
      } else if(scH >= inquiry) {
        this.nowActive = 'inquiry';
      }

    },
    // reviewScroll() {
    //   let review = this.$refs.review_height.offsetTop;
    //   let tab = this.$refs.review_height.offsetHeight;

    //   window.scrollTo({ top: review - 100 - tab, behavior: "smooth" });
    //   this.isReview = true;
    // },
    tabScroll() {
      let scH = window.scrollY;
      let tab = this.$refs.tab_height.offsetTop;
      if (scH > tab) {
        this.isScrollTab = true;
      } else {
        this.isScrollTab = false;
      }
    },
    scrollMeTo(refName) {
      var element = this.$refs[refName];
      var top = element.offsetTop;

      let topMinus = 0;
      if (window.sessionStorage.getItem('platform') == "MAC001") {
        if(this.isScrollTab) {
          if(refName === 'review' ) {
            topMinus = 170;
          } else {
            topMinus = 150;
          }
        } else {
          if(refName === 'review') {
            topMinus = 230;
          } else {
            topMinus = 210;
          }
        }
      } else {
        if(this.isScrollTab) {
          if(refName === 'review') {
            topMinus = 110;
          }else if(refName === 'detail'){
            topMinus = 50
          }else {
            topMinus = 100;
          }
        } else {
          if(refName === 'review') {
            topMinus = 150;
          }else if(refName === 'detail'){
            topMinus = 100
          }else {
            topMinus = 130;
          }
        }
      }
      window.scrollTo({ top: top - topMinus, behavior: "smooth" });
    },
    handleBuyBottomSheet() {
      let scH = window.scrollY;
      if (scH > 1067) {
        if (!this.isBuyBottomSheet) {
          this.isBtnOpen = !this.isBtnOpen;
          this.isBuyBottomSheet = !this.isBuyBottomSheet;
          this.isScrollSheet = true;
        }
      } else {
        if (!this.isBuyBottomSheet) {
          this.isBtnOpen = !this.isBtnOpen;
          this.isBuyBottomSheet = !this.isBuyBottomSheet;
          this.isScrollSheet = true;
        }
      }
    },
    /*********************************
     * 상품문의유형 로드
     *********************************/
    qnaCommon() {
      //공통코드 목록 불러오기
      let cmclassArr = ['QNATYPE'];
      this.$http.post('/common/code/map/list', { cmclass: cmclassArr, isloading: false })
        .then(result => {
          this.qnatypeOptions = [];
          //*
          for (var i = 0; i < result.data.qnatype.length; i++) {
            var temp = {};
            temp.label = result.data.qnatype[i].codename;
            temp.value = result.data.qnatype[i].cmcode;
            this.qnatypeOptions.push(temp);
          }
          //*/
        })
    },
    //상품 문의
    productQna() {
      this.qnaCommon();

      var params = {
        //페이지당 보여줄 갯수
        perpage: this.totalPage,
        startpage: this.currentPage,
        goodscode: this.goodscode,
      }

      this.$http.post('/cscenter/qnagoods', params).then(result => {
        if (result.statusCode == 200) {
          this.inquiryData = [];
          this.SelectOptionCode = [
            {
              label: "선택",
              value: ""
            }
          ];
          for (var i = 0; i < result.data.goodsoption.length; i++) {
            var temp = {};
            temp.label = result.data.goodsoption[i].optionname;
            temp.value = (result.data.goodsoption[i].optionno).toString();
            this.SelectOptionCode.push(temp);
          }
          this.currentPage = 1;
          this.listTotal = result.data.qnalistcount;
          this.inquiryData = result.data.qnalist;
          if(this.listTotal === this.inquiryData.length) {
            this.QnaListBtn = false;
          }
        }
      })

    },
    changeType(id, index) {
      //문의 유형 전체/미답변/답변완료/내문의
      this.searchType = id.replace("inquiry_", '');

      this.inquiryType.forEach((m,i) => {
        m.checked = i === index;
      });

      this.QnaListBtn = true;

      var param = {
        searchtype: this.searchType,
        perpage: 5,
        startpage: 1,
        goodscode: this.goodscode,
      }

      this.$http.post('/cscenter/qnagoods', param).then(result => {
        if (result.statusCode == 200) {
          this.inquiryData = [];
          this.currentPage = 1;
          this.listTotal = result.data.qnalistcount;
          this.inquiryData = result.data.qnalist;
          if(this.listTotal === this.inquiryData.length) {
            this.QnaListBtn = false;
          }
        }
      })

    },
    productQnaReWirte(list) {
      this.$util.debug("productQnaReWirte......");

      var param = {
        list: list,
        goodscode: this.goodscode,
        qnatypeOptions: this.qnatypeOptions,
        SelectOptionCode: this.SelectOptionCode,
        //등록이면 register, 수정이면 revise
        savetype: "revise"
      }
      this.$eventBus.$emit('showModal', QnaModal, 'QnaModal', param, (param) => {
        this.productQna();
      });
    },
    productQnaDelete(idx) {
      this.$eventBus.$emit('confirm', '확인', '문의 내용을 삭제하시겠습니까?', () => {
        var param = { idx: idx }

        this.$http.post('/cscenter/qnadelete', param).then(result => {
          if (result.statusCode == 200) {
            this.$eventBus.$emit('alert', '알림', result.data.message);
            this.productQna();
          }
        })
      });
    },
    QnaRegister() {
      if (this.qnatype == "") {
        this.$eventBus.$emit('alert', '알림', "문의유형을 선택해주세요.");
        return;
      }
      if (this.qnaTextArea == "" && this.qnaTextArea.length < 10) {
        this.$eventBus.$emit('alert', '알림', "문의내용을 입력해주세요. (최소 10글자)");
        return;
      }
      var issecret = "T";
      if (this.privateInquiryCheckbox.checked) {
        issecret = "T";
      } else {
        issecret = "F";
      }

      var param = {
        goodscode: this.goodscode,
        qnatype: this.qnatype,
        optioncode: this.optionCode,
        content: this.qnaTextArea,
        issecret: this.chkbox,
        index: this.index,
        optionnm1: this.optionnm1,
        optionnm2: this.optionnm2,
        optionnm3: this.optionnm3,
        optionnm4: this.optionnm4,
      }
      this.$http.post('/cscenter/insertGoodsQna', param).then(result => {
        if (result.statusCode == 200) {
          if (result.data.msg == "success") {
            this.$eventBus.$emit('alert', '알림', "등록되었습니다.");
            this.$root.$emit("bv::hide::modal", 'QnaModalPC');
            this.productQna();
          } else {
            this.$eventBus.$emit('alert', '알림', "등록에 실패하였습니다.");
          }

        }
      })
    },
    chkChange() {
      if (this.privateInquiryCheckbox.checked) {
        this.privateInquiryCheckbox.checked = false;
      } else {
        this.privateInquiryCheckbox.checked = true;
      }
    },
    goPcModalOpen(modalId) {
      if (this.$store.state.isLogin) {
        this.$root.$emit("bv::show::modal", modalId);
      } else {
        this.$eventBus.$emit('alert', '알림', "로그인을 하셔야 본 서비스를 이용하실 수 있습니다.");
        //경로 이동

      }
    },
    //PC 페이징처리
    changePage(page) {
      var Params = {
        perpage: this.listCnt,
        startpage: page,
        goodscode: this.goodscode,
        searchtype: this.searchType,
      }
      this.$http.post('/cscenter/qnagoods', Params).then(result => {
        if (result.statusCode == 200) {
          this.inquiryData = [];
          this.currentPage = page;
          this.listTotal = result.data.qnalistcount;
          this.inquiryData = result.data.qnalist;
        }
      })

    },
    MobilePaging() {
      var Params = {
        perpage: this.listCnt,
        startpage: this.currentPage,
        goodscode: this.goodscode,
        searchtype: this.searchType,
      }
      this.$http.post('/cscenter/qnagoods', Params).then(result => {
        if (result.statusCode == 200) {
          this.listTotal = result.data.qnalistcount;
          if (result.data.qnalist.length > 0) {
            this.inquiryData = [...this.inquiryData || [], ...result.data.qnalist];
            if(this.listTotal === this.inquiryData.length) {
              this.QnaListBtn = false;
            }
          }
        }
      })
    },
    MobilePage() {
      if (Math.ceil(this.listTotal / this.totalPage) >= this.currentPage + 1) {
        this.QnaListBtn = true;
        this.currentPage += 1;
        this.MobilePaging();
      } else {
        this.QnaListBtn = false;
      }
    },
    // 상품정보 조회
    getDetail() {
      const param = {
        goodscode: this.goodscode
      }

      this.$http.post('/goods/detail', param).then(result => {
        if (result.statusCode == 200) {
          result.data.saleamt = result.data.saleamt - result.data.goodscpnamt;
          this.detail = result.data;
          
          if(this.detail.stockcnt == 0) {
            this.btnBuy = '품절(재입고 알림)';
          } else {
            this.btnBuy = '구매하기';
          }

          this.selectImg = this.detail.swiperlist[0].fullpath;

          // 아이콘 전체일 시 확인
          if(this.detail.badge.length > 0) {
            if(this.detail.badge[0] === '전체') {
              let realBadge = ['EXCLUSIVE', 'LIMITED', 'PRE-ORDER'];
              this.detail.badge = realBadge;
            }
          }
          //할인율
          this.detail.disrate = this.$util.saleRate(result.data.marketprice, result.data.saleamt);
          //검색키워드
          this.detail.keywordlist = result.data.keyword.split(',');
          //임직원여부
          if (result.data.memberinfo.authtype == 'member' && result.data.memberinfo.membertype != 'DMT001' && result.data.memberinfo.membertype != 'DMT002') {
            this.isEmp = true;
          }
          //적립금혜택
          this.reserve = this.detail.reserve;
          this.promreserve = this.detail.promreserve;

          // if(this.isFirstOrder == 'F') { // 첫구매
          //   this.totreserve = Math.round(this.detail.saleamt * (this.reserve.frstpurcfmamt / 100));
          //   if(this.reserve.ismulti == 'T') {
          //     this.totreserve += this.totreserve*this.reserve.cfmmulti;
          //   }
          //   if(!this.$util.isNull(this.promreserve)) {
          //     this.totreserve += this.promreserve.reservepoint;
          //   }
          // } else {
          // 최대적립금액 계산
          this.totreserve = Math.round((this.detail.saleamt * ( this.reserve.frstpurcfmamt / 100)));
          if(this.reserve.ismulti == 'T') {
            this.totreserve += this.totreserve*this.reserve.cfmmulti;
          }
          if(this.promreserve?.length > 0) {
            let sumreserve = 0;
            for (let i = 0; i < this.promreserve.length; i++) {
              const treserve = this.promreserve[i];
              sumreserve += treserve.reservepoint;
            }
            this.totreserve += sumreserve;
          }
          // }
          
          // 신규할인혜택 판매가격 계산
          // this.detail.isnewjoincoupon 신규할인
          //alert("isnewjoincoupon:"+this.detail.isnewjoincoupon);
          // 신규가입쿠폰 할인가 서버에서 계산
          this.totsaleamt = this.detail.totsaleamt;
         
          /*
          if(this.detail.saleamt >= 1 && this.detail.saleamt < 50000) {
            this.totsaleamt = this.detail.saleamt - Math.round((this.detail.saleamt * ( 10 / 100)));
            if(this.detail.saleamt - this.totsaleamt > 120000) {
              this.totsaleamt = this.detail.saleamt - 120000;
            }
          } else if(this.detail.saleamt >= 50000 && this.detail.saleamt < 100000) {
            this.totsaleamt = this.detail.saleamt - 10000;
          } else if(this.detail.saleamt >= 100000 && this.detail.saleamt < 150000) {
            this.totsaleamt = this.detail.saleamt - 15000;
          } else if(this.detail.saleamt >= 150000 && this.detail.saleamt < 200000) {
            this.totsaleamt = this.detail.saleamt - Math.round((this.detail.saleamt * ( 10 / 100)));
            if(this.detail.saleamt - this.totsaleamt > 120000) {
              this.totsaleamt = this.detail.saleamt - 120000;
            }
          } else if(this.detail.saleamt >= 200000 && this.detail.saleamt < 250000) {
            this.totsaleamt = this.detail.saleamt - 25000;
          } else if(this.detail.saleamt >= 250000) {
            this.totsaleamt = this.detail.saleamt - Math.round((this.detail.saleamt * ( 10 / 100)));
            if(this.detail.saleamt - this.totsaleamt > 120000) {
              this.totsaleamt = this.detail.saleamt - 120000;
            }
          } */

          //카드혜택정보
          this.setCardBenefit(result.data.cardpromotion);

          if(result.data.memberinfo.authtype == 'nonMember') {
            this.isnonmember = 'T';
          }

          //다운로드 쿠폰
          this.couponcnt = this.detail.couponcnt;
          this.downCouponList = this.detail.couponlist;

          //최근본상품 저장
          this.$front.setRecentItem(this, this.detail);

          // gtmDataLayer 데이터 작업.
          let ecommerce = new Object();
          let items =  new Array();
          let item1 = new Object();
          item1.item_id = this.detail.goodscode;
          item1.item_name = this.detail.goodsname;
          item1.price = this.detail.price;
          item1.item_brand = this.detail.brandname;
          items.push(item1);
          
          ecommerce.items = items;
          this.$front.gtmDataLayer('view_item', ecommerce);
        } else {
          this.$eventBus.$emit('alert','알림',result.message, ()=>{
            // this.$router.replace('/')
            this.$router.go(-1);
          });
        }
      });
    },
    //카트해택 세팅
    setCardBenefit(cardinfo) {
      //카드즉시할인정보
      var cnt = 0;
      for (let i = cardinfo.discountcards.length - 1; i >= 0; i--) {
        const card = cardinfo.discountcards[i];

        //할인종료날짜
        if (this.$util.getDateDiff(this.$util.getDate(), card.dueDate.replace(/-/g, '')) < 0) {
          cardinfo.interestfreecards.splice(i, 1);
          continue;
        }
        //예산금액
        if (card.balance <= 0) {
          cardinfo.interestfreecards.splice(i, 1);
          continue;
        }
        
        this.discountInfo.isshow = true;

        let content = card.cardCompany + '카드 / ';
        content += this.$util.maskComma(card.discountAmount) + '원 할인 / ';
        content += this.$util.maskComma(card.minimumPaymentAmount) + '원 이상 구매시 ';
        
        cardinfo.discountcards[i].content = content;
        if(cnt >= 2) { // 상세에 최대 2개만 보여주기 위해
          break;
        }
        if(this.discountInfo.disamount < card.discountAmount) {
          this.discountInfo.content = card.cardCompany + '카드 / 즉시할인 ' + this.$util.maskComma(card.discountAmount) + '원';
          this.discountInfo.disamount = card.discountAmount;
          cnt ++;
        }
      }

      //카드 할부정보
      for (let i = cardinfo.interestfreecards.length - 1; i >= 0; i--) {
        cardinfo.interestfreecards[i].isshow = true;

        const card = cardinfo.interestfreecards[i];
        //할인종료날짜
        if(this.$util.getDateDiff(this.$util.getDate(), card.dueDate.replace(/-/g,'')) < 0){
          cardinfo.interestfreecards.splice(i, 1);
          continue;
        }

        let content = card.cardCompany + '카드 / ';
        content += this.$util.maskComma(card.minimumPaymentAmount) + '원 이상 / ';
        content += card.installmentFreeMonths[0] + '~' + card.installmentFreeMonths[card.installmentFreeMonths.length - 1] + ' 개월';
        cardinfo.interestfreecards[i].content = content;
      }
      
      let disCards = []; //청구할인
      let disCardsMsg = []; //청구할인
      let insCards = []; //부분무이자할부
      for(let i = 0 ; i < cardinfo.cardbenefit.length ; i++) {
        this.discountInfo.isshow = true;
        if(cardinfo.cardbenefit[i].isdiscount == 'T'){
          disCards.push(cardinfo.cardbenefit[i]); 

          const card = cardinfo.cardbenefit[i];
          let content = card.cardname + ' / 청구할인 ';
          content += this.$util.maskComma(card.flatrate) + '원';
          if(i < 3) {
            disCardsMsg.push(content);
          }
        } else {
          let idx = this.$front.containIdx(insCards, "cardname", cardinfo.cardbenefit[i].cardname);
          if (idx == -1) {
            let temp =  Object.assign({}, cardinfo.cardbenefit[i]);
            temp.list =  cardinfo.cardbenefit.filter((x) => x.cardname == temp.cardname);
            insCards.push(temp);
          }
        }
      }

      this.cardBenefit = {
        discountCards : {
          disCards1 : cardinfo.discountcards,
          disCards2 : disCards,
          disCardsMsg : disCardsMsg,
          note : cardinfo.benenote
        },
        interestFreeCards : {
          disCards1 : cardinfo.interestfreecards,
          disCards2 : insCards,
          note : cardinfo.internote
        }
      }
    },
    openModal(modalId) {
      if (modalId == 'deliveryInfoModal') {
        this.$eventBus.$emit('showModal', Delivery, modalId, this.detail.deliv);
      } else if (modalId == 'cardBenefitModal') {
        this.$eventBus.$emit('showModal', CardDiscount, modalId, this.cardBenefit.discountCards);
      } else if (modalId == 'noInterestCardModal') {
        this.$eventBus.$emit('showModal', CardInterest, modalId, this.cardBenefit.interestFreeCards);
      } else if (modalId == 'productInfoModal') {
        this.$eventBus.$emit('showModal', goodsnotify, modalId, this.detail.goodsnotifylist);
      } else if (modalId == 'sellerInfoModal') {
        this.$eventBus.$emit('showModal', Dealer, modalId, this.detail.dealer);
      } else if (modalId == 'exchangeModal') {
        this.$eventBus.$emit('showModal', AsInfo, modalId);
      } else if (modalId == 'QnaModal') {
        if(!this.$store.state.isLogin) {
          // this.$eventBus.$emit("alert", "알림", "로그인 후 이용해주세요.");
          this.$eventBus.$emit('confirm', '로그인', '로그인 하시겠습니까?',()=>{
            this.$storage.setSessionStorage('redirectPath', {path : this.$route.path, query : this.$route.query});
            this.$router.push({name : 'member-login'});
          });
          return;
        }
        var regParam = {
          goodsno: this.detail.goodsno,
          //등록이면 register, 수정이면 revise
          savetype: "register"
        }
        this.$eventBus.$emit('showModal', QnaModal, modalId, regParam, (param) => {
          this.productQna();
        });
      } else if (modalId == 'QnaModalPC') {
        /******************************
         * 미로그인시처리
         *****************************/
        if(!this.$store.state.isLogin) {
          this.$eventBus.$emit('confirm', '로그인', '로그인 하시겠습니까?',()=>{
            this.$storage.setSessionStorage('redirectPath', {path : this.$route.path, query : this.$route.query});
            this.$router.push({name : 'member-login'});
          });
          return;
        }
        /******************************
         * 로그인시처리
         *****************************/
        var regPCParam = {
          goodsno: this.detail.goodsno,
          //등록이면 register, 수정이면 revise
          savetype: "register"
        }

        this.$eventBus.$emit('showModal', QnaModalPC, modalId, regPCParam, (param) => {
          //상품 QNA 재조회
          this.productQna();
        });
      } else if (modalId == 'pointModal') {
        if (this.detail.memberinfo.authtype == 'member') {
          this.$eventBus.$emit('showModal', Point, modalId);
        } else {
          this.$eventBus.$emit('showModal', GradePoint, 'gradePointModal', this.detail.reservelist);
        }
      } else if (modalId == 'giftModal') {
        if (this.detail.giftlist.length == 0) {
          this.$toast.clear();
          this.$toast.open('사은품 미제공 상품입니다.');
          return;
        }
        this.$eventBus.$emit('showModal', Gift, modalId, this.detail.giftlist);
      }
    },
    playDocent() {
      if (!this.isPlay) {
        alert("음성 파일을 실행합니다.");
        this.isPlay = true;
      } else {
        alert("실행을 멈춥니다.");
        this.isPlay = false;
      }
    },
    handleSelect() {
    },
    changePostReportData(index) {
      if (index === "reportRadio06") {
        this.reportRadio = "기타";
      }
    },
    /**********************
     * 장바구니 버튼 처리
     ********************/
    handleCart() {
      if (!this.$front.checkCartItem(this, this.selectedItem)) {
        return;
      }

      this.isBuyBottomSheet = false;
      this.$front.saveCart(this, this.selectedItem, () => {
        this.selectedItem = [];
        this.$eventBus.$emit('confirm', '장바구니로 이동', '장바구니에 담았습니다.',
          () => {
            this.$router.push({ name: 'cart' })
          },
          () => {

          },
          '장바구니이동',
          '계속쇼핑하기'
        );
      })
    },
    /**********************
     * 바로구매 버튼 처리
     ********************/
    handleBuy() {
      if (!this.$front.checkCartItem(this, this.selectedItem)) {
        return;
      }
      if(this.$store.state.isLogin) {
        this.$router.push({name:'order', query : {items :  this.$front.makeOrderStr(this.selectedItem), islogin : this.$store.state.isLogin}});
      } else {
        this.$eventBus.$emit('confirm', "비회원구매 안내", '비회원으로 구매 시 추가할인 및 쿠폰, 포인트 혜택을 받으실 수 없습니다.로그인 후 구매하시겠습니까?', 
          ()=>{
            this.$router.push({name:'order', query : {items :  this.$front.makeOrderStr(this.selectedItem), islogin : this.$store.state.isLogin}});
          },
          ()=>{
            this.$storage.setSessionStorage('redirectPath', {name : 'order', query : {items :  this.$front.makeOrderStr(this.selectedItem), islogin : true}});
            this.$router.replace({name:'member-login'});
          }, '비회원구매','로그인'
        );
      }
    },
    /**********************
     * 좋아요 버튼 처리
     ********************/
    changeWish(detail) {
      this.$front.goodsChangeWish(this, detail, detail.wishcnt);
    },
    replaceBrandImg(e) {
      e.target.src = "https://fakeimg.pl/40/";
    },
    /**********************
     * SNS 공유하기 모달
     ********************/
    snsShareModal() {
      let description = "[D.PLOT]";
      description = this.detail.summary;

      let param = {
        kakao:  {
          objectType: "feed",
          content: {
            title: "[D.PLOT]" + this.detail.goodsname,
            description: '',
            imageUrl:this.detail.swiperlist[0].fullpath,
            link: {
              mobileWebUrl: window.location.href,
              webUrl:window.location.href
            },
          }
        },
        meta: {
          title: "D.PLOT",
          summary:description,
          img:this.detail.swiperlist[0].fullpath,
          url:document.location.href
        }
      }

      this.$eventBus.$emit('showModal', SnsShareModal, "snsShareModal", param);
    },
    getDownCouponList(callback) {
      let param = {
        isloading : false,
        comcpntype : 'CCT001',
        items : [{
          goodsno : this.detail.goodsno,
          ordcnt : 1
        }],
        isoption : 'F'
      }
      this.$http.post('/coupon/order/list', param).then(result => {
        this.downCouponList = result.data.list;
        if(callback) {
          callback();
        }
      });
    },
    couponDownload() {
      if(this.$store.state.isLogin) {
        this.getDownCouponList(() => {
          if(this.downCouponList.length == 0) {
            this.$eventBus.$emit('alert', '알림', '다운로드 가능한 쿠폰이 없습니다.');
            return;
          }
          let param = {
            isloading : false,
            items : [{
              goodsno : this.detail.goodsno,
              ordcnt : 1
            }],
            isoption : 'F',
            couponList : this.downCouponList
          }
          this.$eventBus.$emit('showModal', CouponDownload, 'couponAddModal', param, (result)=>{
            this.downCouponList = result;
          });
        });
      } else {
        this.$eventBus.$emit('confirm', "쿠폰다운로드 안내", '로그인을 하셔야 본 서비스를 이용하실 수 있습니다. 로그인 하시겠습니까?', 
          ()=>{
            this.$storage.setSessionStorage('redirectPath', {name : this.$route.name, params : this.$route.params});
            this.$router.push({name : 'member-login'});
          }
        );
      }
    },
    changeGoodsImage(index) {
      this.$refs.goodsSwiper.swiper.slideTo(index);
    },
    changeGoodsPcImage(index) {
      this.selectImg = this.detail.swiperlist[index].fullpath;
    },
    optinBottomSheet() {
      this.$nextTick(()=>{
        this.isBuyBottomSheet = true;
      });
    }
  },
  // 공유하기용 헤더 메타데이터 설정
  metaInfo() {
    return {
      meta: [
        {
          vmid: 'url',
          property: 'og:url',
          content: window.location.href,
        },
        {
          vmid: 'title',
          property: 'og:title',
          content: this.detail.goodsname,
        },
        {
          vmid: 'description',
          property: 'og:description',
          content: this.detail.summary,
        },
        {
          vmid: 'image',
          property: 'og:image',
          content: 'http://d2yxem29c3b5r4.cloudfront.net/img/item/38/38PM_1653544056497.png', // ?안하면 오류남
        },
        // {
        //   vmid: 'og:img',
        //   property: 'og:img',
        //   content: this.detail.swiperlist[0]?.fullpath, // ?안하면 오류남
        // },
        {
          vmid: 'recopick:title',
          property: 'recopick:title',
          content: this.detail.goodsname,
        },
        {
          vmid: 'recopick:image',
          property: 'recopick:image',
          content: this.detail.swiperlist[0]?.fullpath,
        },
        {
          vmid: 'recopick:price',
          property: 'recopick:price',
          content: this.detail.marketprice,
        },
        {
          vmid: 'recopick:price:currency',
          property: 'recopick:price:currency',
          content: '원',
        },
        {
          vmid: 'recopick:sale_price',
          property: 'recopick:sale_price',
          content: this.detail.saleamt,
        },
        {
          vmid: 'recopick:sale_price:currency',
          property: 'recopick:sale_price:currency',
          content: '원',
        },
      ],
      changed : (newInfo, addedTags, removedTags) => {
        const meta = newInfo.meta;
        for(let i = 0 ; i < meta.length ; i++) {
          if(meta[i].vmid == "recopick:title" && !this.$util.isBlank(meta[i].content)) {
            const depth1name = this.$util.isBlank(this.detail.depth1name) ? '' : this.detail.depth1name;
            const depth2name = this.$util.isBlank(this.detail.depth2name) ? '' : this.detail.depth2name;
            const depth3name = this.$util.isBlank(this.detail.depth3name) ? '' : this.detail.depth3name;
            recoPick('sendLog', 'view', { id: this.detail.goodscode + '', c1: depth1name, c2: depth2name, c3: depth3name });
          }
        }
      }
    }
  },
}