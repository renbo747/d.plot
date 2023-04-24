import { swiper, swiperSlide } from "vue-awesome-swiper";
import ImageModal from '@views.mobile/mypage/activity/myreview/popup/ImageModal.vue';
export default {
    components: {
        swiper,
        swiperSlide,
    },
    mounted() {
        this.asidx = this.$route.params.pid;
        var param = {asidx: this.asidx};
        this.onInit(param);
    },      
    data() {
      return {
        asidx: "0",
        files:[],
        product: {},

        // 고객정보
        customerInfo: [
          {
            infoKey: "접수자",
            infoValue: "",
          },
          {
            infoKey: "연락처",
            infoValue: "",
          },
          {
            infoKey: "주소",
            infoValue: "",
          },
        ],
        swiperDetailOption: {
          slidesPerView: 5,
          spaceBetween: 30,
          navigation: {
            nextEl: ".img-swiper-next",
            prevEl: ".img-swiper-prev",
          },
          observeParents: true,
          observer: true,
        },
      };
    },
    methods: {
        /********************
         * 메인 접근
         ********************/
        onInit(param) {
            this.$util.debug("onInit.......");
            //1:1 문의 데이터 로드
            this.$http.post('/mypage/as/applydetail', param).then(result => {
                if (result.statusCode == 200) {
                    this.$util.debug("onInit success.......");
                    if (this.$util.isNull(result.data.list)) {
                      this.$eventBus.$emit("alert", "알림", "해당 AS전용문의 내역이 존재하지 않습니다.", () => {
                        this.$router.go(-1);
                      });
                    } else {
                      this.product = result.data.list;
                      this.product.ordcnt = result.data.list.ascnt;
                      //this.product.ordstatusnm = result.data.list.statusnm;
                      this.product.opthtml = result.data.list.optionname;
                      this.customerInfo[0].infoValue = result.data.list.writer;
                      this.customerInfo[1].infoValue = this.$util.maskTel(result.data.list.tel);
                      this.customerInfo[2].infoValue = result.data.list.addrroad + " " + (!this.$util.isNull(result.data.list.addrroaddetail)? result.data.list.addrroaddetail : "");
                      this.files = result.data.files;
                    }
                }
            })            
        },          
        /********************
         * 수정 클릭 이벤트 
         ********************/
         goToaApplyAs() {
            this.$util.debug("goToaApplyAs.......");
            this.$router.push({name: 'apply-as-revise' , params: {pid:this.asidx}});
        },
        /********************
         * 삭제 클릭 이벤트
         ********************/
         goDeleteApply() {
            this.$util.debug("goDeleteApply .......");
            this.$eventBus.$emit('confirm', '확인' ,'문의내역을 삭제하시겠습니까?' , ()=>{
                var param = {asidx : this.asidx};
                this.$http.post('/mypage/as/applydelete', param).then(result => {
                    if (result.statusCode == 200) {
                        this.$util.debug("onInit success.......");
                        if(result.data.msg == 'success'){
                            this.$eventBus.$emit('alert', '알림', "문의내역이 삭제되었습니다.");
                            this.$router.push({name: 'mypage-as-apply-list'});
                        }
                    }
                })                 
            });
        
        },
        /*****************************
         * 이미지 모달 열기
         ****************************/
        openModal(files,index) {
          // let index = this.$refs.swiper.swiper.realIndex;
          let param = {
            files: files,
            filetype: files[index].filetype,
            index: index
          }
          this.$eventBus.$emit('showNoBvModal', ImageModal, param);
        },
    },
    // watch: {
    //   'files': function(value){
    //     if (value.length < 5) {
    //       this.swiperDetailOption.loop = false;
    //     }else {
    //       this.swiperDetailOption.loop = true;
    //     }
    //   }
    // }
  };