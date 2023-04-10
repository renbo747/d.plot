import { swiper, swiperSlide } from "vue-awesome-swiper";
import WriteModal from './popup/WriteModal.vue';
import ProductModal from './popup/ProductModal.vue';
import ImageModal from '@views.mobile/mypage/activity/myreview/popup/ImageModal.vue';


export default {
  components: {
    swiper,
    swiperSlide,    
  },
  beforeCreate() {
        if(this.$route.name === 'detail-inquiry'){
            this.$store.commit("onSubHeaderOption", {
                title: "1:1 문의",
                searchIcon: true,
                cartIcon: true,
                homeIcon: false,
            });                
        }
        if(this.$route.name === 'detail-qna'){
            this.$store.commit("onSubHeaderOption", {
                title: "상품 Q&A",
                searchIcon: true,
                cartIcon: true,
                homeIcon: false,
            });                
        }
  },
  mounted() {
    if(this.$store.state.platform != 'MAC001'){
      this.swiperDetailOption.width = "600";
    }
    this.idx = this.$route.params.idx;
    var param = {
      idx: this.idx,
    }

    if((this.$route.name).indexOf('detail-inquiry') != -1){
      this.onInquiry(param);
    }

    if((this.$route.name).indexOf('detail-qna') != -1){
      this.onQna(param);
    }

  },
  data() {
    return {
      // 공통 사용 시작 //////////////////////////////////////////////
      idx: "",
      files:[],
      //상세 데이터 담을 리스트 (1:1문의,상품Q&A 공통 사용)
      data: [],
      selectedIndex: "",
      isMediaType: true, // Todo : 미디어 타입으로 이미지와 영상 표시 구분 필요.
      isModalOpen: false,
      swiperDetailOption: {
        slidesPerView: 5,
        spaceBetween: 10,
        navigation: {
          nextEl: ".swiper-btn-next",
          prevEl: ".swiper-btn-prev",
        },
        observeParents: true,
        observer: true,
      },
      modalSwiperOption: {
        navigation: {
          nextEl: ".modal-swiper-next",
          prevEl: ".modal-swiper-prev",
        },
      },
      // 공통 사용 끝 ////////////////////////////////////////////////

      // 1:1 문의 관련 시작 //////////////////////////////////////////
      //주문상품선택 리스트
      ordno: "",
      orderList: [],
      // 1:1 문의 관련 끝 //////////////////////////////////////////

      // 상품 문의 관련 시작 //////////////////////////////////////////
      qnaThumbnailData: {},
      // 상품 문의 관련 끝 //////////////////////////////////////////
    };
  },
  methods: {
    /**************************
     * 1:1 문의 상세 진입
     ****************************/
    onInquiry(param) {
      this.$http.post('/cscenter/inquirydtl',param).then(result => {
          if(result.statusCode == 200){
              this.data = result.data.list;
              this.orderList = result.data.list.goodslist;
              this.files = result.data.list.files;
            
              if(!this.$util.isEmpty(result.data.list.goodslist)){
                this.ordno = result.data.list.goodslist[0].ordno;
              }

          }else{
            this.$router.push({ name: 'cs-inquiry-index'});
          }
      })
    },
    /**************************
     * Q&A 상세 진입
     ****************************/
    onQna(param) {
      this.$http.post('/cscenter/qnadetail',param).then(result => {
          if(result.statusCode == 200){
              this.qnaThumbnailData = {};
              this.data = result.data.list;
              this.qnaThumbnailData.id = result.data.list.goodsno;
              this.qnaThumbnailData.fullpath = result.data.list.fullpath;
          }
      })
    },
    /**************************
     * 삭제 클릭 이벤트
     ****************************/
    goDelete(pageType) {
      this.$eventBus.$emit('confirm', '확인' ,'문의 내용을 삭제하시겠습니까?' , ()=>{
        //파일도 같이 삭제
        let arridx = [];
        for(var index in this.files){
            arridx.push(this.files[index].idx);
        }
        var param = {
          idx: this.idx,
          fileidx: arridx,
        }
        
        //1:1 문의 삭제
        if(pageType.indexOf('detail-inquiry') != -1){
          this.onInquiryDelete(param);
        }

        //상품 Q&A 삭제
        if(pageType.indexOf('detail-qna') != -1){
          this.onQnaDelete(param);
        }

      });

    },
    /**************************
     * 1:1 문의 삭제 처리
     ****************************/
    onInquiryDelete(param) {
      this.$http.post('/cscenter/inquirydelete',param).then(result => {
          if(result.statusCode == 200){
              this.$eventBus.$emit('alert', '확인' ,result.data.msg)
                  if((this.$route.name).indexOf('mypage') != -1){
                    this.$router.push({name: 'mypage-inquiry'});
                  }else{
                    this.$router.push({name: 'inquiry'});
                  }
          }
      })
    },
    /**************************
     * 상품 Q&A 삭제 처리
     ****************************/
    onQnaDelete(param) {
      this.$http.post('/cscenter/qnadelete',param).then(result => {
          if(result.statusCode == 200){
              this.$eventBus.$emit('alert', '확인' ,result.data.message);
              if((this.$route.name).indexOf('mypage') != -1){
                this.$router.push({name: 'mypage-qna'});
              }else{
                this.$router.push({name: 'qna'});
              }

          }
      })
    },
    /**************************
     * 수정으로 이동
     ****************************/
     goWrite(pageType) {
        if(pageType === 'detail-inquiry'){
            this.$router.push({name: 'write-inquiry-revise', params: {idx: this.idx}});
        }else if(pageType === 'mypage-detail-inquiry') { 
          this.$router.push({name: 'mypage-write-inquiry-revise', params: {idx: this.idx}});
        }else if(pageType === 'detail-qna') {
          this.$router.push({name: 'write-qna-revise', params: {idx: this.idx}});
        }else if(pageType === 'mypage-detail-qna') {
          this.$router.push({name: 'mypage-write-qna-revise', params: {idx: this.idx}});
        }
    },
    showModal(id) {
      this.selectedIndex = id - 1;
      this.isModalOpen = !this.isModalOpen;
    },
    closeModal() {
      this.isModalOpen = false;
    },
    openWriteModal(routeName){
        var param = {
            routename: routeName,
            detaildata: this.data,
            ordernumber: this.ordno,
            orderlist: this.orderList,
            files: this.files,
        }    
        this.$eventBus.$emit('showModal', WriteModal, 'WriteModal', param,() => {
          this.onInquiry({idx: this.idx});
        });
    },
    /*****************************
     * 이미지 모달 열기
     ****************************/
    openModal(files, index) {
      this.$util.debug("Image Modal open");
      let param = {
          files: files,
          filetype: files[index].filetype
      }
      this.$eventBus.$emit('showNoBvModal', ImageModal, param);
    },
    /*****************************
     * 목록보기 페이지 이동
     ****************************/
     goListPage(routeName){
      this.$util.debug("goListPage");
      this.$util.debug(routeName);
      var link = routeName.replace('detail-',"");
      this.$router.push({name: link});
    },
    goShopDetail() {
      this.$util.debug("goShopDetail...");
    },
  },
  watch: {
    isModalOpen(val) {
      if (val) {
        document.documentElement.style.overflow = "hidden";
      } else {
        document.documentElement.style.overflow = "auto";
      }
    },
  },
};