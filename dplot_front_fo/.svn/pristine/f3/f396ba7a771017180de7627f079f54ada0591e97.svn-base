import { swiper, swiperSlide } from "vue-awesome-swiper";

export default {
    components: {
        swiper,
        swiperSlide,
    },
    beforeCreate() {
        this.$store.commit("onSubHeaderOption", {
        title: "나의 문의",
        searchIcon: true,
        cartIcon: true,
        homeIcon: false,
        });
    },
    mounted() {
        if(this.$route.query.pagetype == 'isInquiry'){
            this.onInit();
        }
        if(this.$route.query.pagetype == 'isQna'){
            this.onQna();
        }
    },  
    data() {
      return {
        idx: "",
        files:[],
        isMemo: true, // 메모페드가 비어있을 때 true
        imgUrl: "", // 초기값이자 변수선언
        // 페이지 종류 문의 "Inquiry" / 상품 Q&A "ProductQna"
        isInquiry: "Inquiry",
        data: {},
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
        isMediaType: true, // Todo : 미디어 타입으로 이미지와 영상 표시 구분 필요.
        isModalOpen: false,
        selectedIndex: "",
        orderList: [],
        
        qnaThumbnailData: {
            id: "",
            fullpath: "",
        },
        modalSwiperOption: {
            navigation: {
            nextEl: ".modal-swiper-next",
            prevEl: ".modal-swiper-prev",
            },
        },
      };
    },
    methods: {
        onInit: function() {
            this.idx = this.$route.query.idx;
            this.isInquiry = 'Inquiry';
            var detailParams = {
                idx: this.idx,
            }
            this.$http.post('/cscenter/inquirydetail',detailParams).then(result => {
                if(result.statusCode == 200){
                    this.data = result.data.list;
                    this.orderList = result.data.orderlist;
                    this.files = result.data.list.files;
                    if(result.data.list.repcontent != null && result.data.list.isreply != '답변대기'){
                        this.isMemo = false;
                    }
                }
            })
        },
        onQna: function() {
            this.idx = this.$route.query.idx;
            this.isInquiry = 'ProductQna';
            var detailParams = {
                idx: this.idx,
            }
            this.$http.post('/cscenter/inquiryqnadetail',detailParams).then(result => {
                if(result.statusCode == 200){
                    this.qnaThumbnailData = {};
                    this.data = result.data.list;
                    this.qnaThumbnailData.id = result.data.list.goodsno;
                    this.qnaThumbnailData.fullpath = result.data.list.fullpath;
                }
            })
        },        
        inquiryDelete() {
            //ProductQna / Inquiry
            if(this.isInquiry == 'Inquiry'){

                this.$eventBus.$emit('confirm', '확인' ,'문의 내용을 삭제하시겠습니까?' , ()=>{
                    //파일도 같이 삭제
                    var arridx = [];
                    for(var index in this.files){
                        arridx.push(this.files[index].idx);
                    }                    
                    var deleteParams = {
                        idx: this.idx,
                        fileidx: arridx,
                    }
                    this.$http.post('/cscenter/inquirydelete',deleteParams).then(result => {
                        if(result.statusCode == 200){
                            this.$eventBus.$emit('alert', '확인' ,result.data.msg)
                            if(result.data.msgcode == 'AL-041'){
                                this.$router.push("/cs/inquiry/index?type=isInquiry");
                            }
                        }
                    })

                  });
            }
            
            if(this.isInquiry == 'ProductQna'){

                this.$eventBus.$emit('confirm', '확인' ,'문의 내용을 삭제하시겠습니까?' , ()=>{
 
                    //파일도 같이 삭제
                    var arridx = [];
                    for(var index in this.files){
                        arridx.push(this.files[index].idx);
                    }
                    var deleteParams = {
                        idx: this.idx,
                        fileidx: arridx,
                    }
                    this.$http.post('/cscenter/deleteMyqna',deleteParams).then(result => {
                        if(result.statusCode == 200){
                            this.$eventBus.$emit('alert', '확인' ,result.data.message);
                            this.$router.push("/cs/inquiry/index?type=isQna");
                        }
                    }) 
                });                           
            }

        },            
        showModal(id) {
            // this.imgUrl = imgUrl;
            this.selectedIndex = id - 1;

            this.isModalOpen = !this.isModalOpen;
        },
        closeModal() {
            this.isModalOpen = false;
        },
        goWrite () {
            this.$router.push({name:'cs-inquiry-write', query: {idx: this.idx, pagetype: this.isInquiry}});
        }
    },

};