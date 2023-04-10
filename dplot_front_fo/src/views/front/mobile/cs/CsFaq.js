import InfiniteLoading from "vue-infinite-loading";
export default {
    components: {
      InfiniteLoading
    },
    beforeCreate() {
      this.$store.commit("onSubHeaderOption", {
        title: "자주하는 질문",
        searchIcon: true,
        cartIcon: true,
        homeIcon: false,
      });
    },
    mounted() { 
        this.questionData = [];

        this.faqCommon(); 
        let faqtype = "FAT00" + this.$route.params.faqtype;
        //faq 종류에 따른 param
        var faqParams = {
            faqtype : faqtype,
            startpage : this.currentPage,
            //페이지당 보여줄 갯수
            perpage: this.listCnt,                
        }

        this.onFaq(faqParams);
    },    
    data() {
      return {
        infiniteId: + new Date(),
        isloading: true,
        currentPage: 1,        //현재페이지
        totalPage: 5,         // 페이징영역 노출 최대 페이지(1~N, 1+N~N+N)        
        listTotal: 0,        //조회 목록 전체 수
        listCnt: 10,        //페이지 목록 수
        searchKeyword: "",
        faq: "",
        faqData: [],        
        questionData: [],
      };
    },
    methods: {
        /*********************
         * FAQ 문의 유형 코드 호출 
         *********************/
        faqCommon() {
            //공통코드 목록 불러오기
            let cmclassArr = ['FAQTYPE'];
            this.$http.post('/common/code/map/list', {cmclass : cmclassArr, isloading: false})
                .then(result =>{
                    for(var i=0; i<result.data.faqtype.length; i++){
                        var temp = {};
                        temp.id = i+1;
                        temp.label = result.data.faqtype[i].codename;
                        if(this.$route.params.faqtype - 1 == i){
                          temp.checked = true;
                        }else{
                          temp.checked = false;
                        }
                        this.questionData.push(temp);
                    }
                })
        },          
        onFaq(param) {
        this.$http.post('/cscenter/faq',param).then(result => {
            this.currentPage = param.startpage;
            this.listTotal = result.data.listcount;
            this.faqData = result.data.list;
        })
        },
        handleInput(val) {
            this.inputData = val;
        },
        faqHit(idx,index){
            this.$util.debug(idx);
            this.faqData[index].isOpen = this.$util.isNull(this.faqData[index].isOpen) ? true : !this.faqData[index].isOpen;
            if (this.faqData[index].isOpen) {
              var param = {
                idx : idx
                ,isloading : false
              }
              this.$http.post('/cscenter/hit',param).then(result => {
              }).catch(error => {
                this.$util.debug(error);
              })
            }
        },    
        goToFaq() {
            this.$router.push({name:'cs-cs-find-result', query: {searchKeyword: this.searchKeyword}});
        },
        //모바일 - 자주하는 질문 - 네모 박스 클릭 시
        changeFaq(id){
            this.$util.debug(this.$route.params.faqtype);
            if (this.$route.params.faqtype == id) {
                return;
            }
            //this.$router.replace('cs-faq?faqtype='+id);
            this.$router.push({name:'cs-cs-faq', params: {faqtype: id}});

        },
        /*********************
         * PC 페이징 처리
         *********************/        
        changePage(page) {
          var faqtype = "FAT00" + this.$route.params.faqtype;
          //faq 종류에 따른 param
          var faqParams = {
              faqtype : faqtype,
              //페이지당 보여줄 갯수
              perpage: this.listCnt,
              startpage : page,
          }
          this.$http.post('/cscenter/faq',faqParams).then(result => {
              this.currentPage = page;
              this.listTotal = result.data.listcount;
              this.faqData = result.data.list;
            })
        },
        /*********************
         * 리뷰목록 조회(스크롤페이징 처리)
         *********************/
        infiniteHandler($state) {
           if(this.isloading){
              let faqtype = "FAT00" + this.$route.params.faqtype;
              //faq 종류에 따른 param
              var faqParams = {
                  faqtype : faqtype,
                  startpage : this.currentPage +1,
                  //페이지당 보여줄 갯수
                  perpage: this.listCnt,                
              }
              this.$http.post('/cscenter/faq', faqParams).then(result => {
                  if (result.statusCode == 200) {
                      if (result.data.list.length == 0) {
                          $state.complete();
                          this.isloading = false;
                      } else {
                          $state.loaded();
                          this.isloading = true;
                          this.currentPage += 1;
                          this.faqData = [...this.faqData || [], ...result.data.list];
                      }
                      this.$util.debug(this.faqData);
                  }
              });
           }
        },
        isEnter(searchKeyword){
            if(searchKeyword == ""){
              this.$eventBus.$emit('alert', '확인' ,"검색어를 입력해주세요.");
              return;
            }
            if(searchKeyword == this.seachKeyword){
              return;
            }
            this.$router.push({name:'cs-cs-find-result', query: {searchKeyword: searchKeyword}});
          },        
    },
    watch: {
        $route() {
            this.onInit();
        }
    }
  };