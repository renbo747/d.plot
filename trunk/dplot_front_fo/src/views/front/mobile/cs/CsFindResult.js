import { BIconArrowReturnRight } from "bootstrap-vue";
import InfiniteLoading from "vue-infinite-loading";
import WriteModal from './inquiry/popup/WriteModal.vue';

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
    this.onInit();
    this.faqCommon();
  },
  data() {
    return {
      infiniteId: + new Date(),
      isloading: true,
      currentPage: 1,        //현재페이지
      totalPage: 5,         // 페이징영역 노출 최대 페이지(1~N, 1+N~N+N)        
      listTotal: 0,        //조회 목록 전체 수
      listCnt: 10,        //페이지 목록 수
      inputKeyword: "",
      seachKeyword: "",
      isFindSuccess: true,
      faq:"",
      faqData: [],
      questionData: [],
    };
  },
  methods: {
    onInit: function() {
      this.seachKeyword = this.$route.query.searchKeyword;
      let params = {
        searchkeyword : this.seachKeyword,
        perpage : 10,
        startpage : this.currentPage,
      }
      this.$http.post('/cscenter/faq',params).then(result => {
        var chText = this.seachKeyword;
        if(result.data.list.length > 0){
          this.isFindSuccess = true;
        //*
          for(let i=0; i < result.data.list.length; i++){
            var regex = new RegExp(chText, 'g');
            var splitSubject = result.data.list[i].subject.split(regex);
            result.data.list[i].subject = result.data.list[i].subject.replace(regex, '<span>'+chText+'</span>');
            var dupRegex = new RegExp(chText, 'gi');
            var cprSubject = result.data.list[i].subject.split(dupRegex);
            if(splitSubject.length != cprSubject.length){
              if(chText == chText.toUpperCase()){
                chText = chText.toLowerCase();
              }else{
                chText = chText.toUpperCase();
              }
              regex = new RegExp(chText, 'g');
              result.data.list[i].subject = result.data.list[i].subject.replace(regex, '<span>'+chText+'</span>');
            }
          }
        //*/
        }else{
          this.isFindSuccess = false;
        }
        this.currentPage = 1;
        this.faqData = result.data.list;
        this.listTotal = result.data.listcount;  
      })
      
    },
    /*********************
     * PC 페이징 처리
     *********************/            
    changePage(page) {
      this.seachKeyword = this.$route.query.searchKeyword;
      let params = {
        searchkeyword : this.seachKeyword,
        perpage : 10,
        startpage : page,
      }
      this.$http.post('/cscenter/faq',params).then(result => {
        var chText = this.seachKeyword;
        if(result.data.list.length > 0){
          this.isFindSuccess = true;
        //*
        for(let i=0; i < result.data.list.length; i++){
          var regex = new RegExp(chText, 'g');
          var splitSubject = result.data.list[i].subject.split(regex);
          result.data.list[i].subject = result.data.list[i].subject.replace(regex, '<span>'+chText+'</span>');
          var dupRegex = new RegExp(chText, 'gi');
          var cprSubject = result.data.list[i].subject.split(dupRegex);
          if(splitSubject.length != cprSubject.length){
            if(chText == chText.toUpperCase()){
              chText = chText.toLowerCase();
            }else{
              chText = chText.toUpperCase();
            }
            regex = new RegExp(chText, 'g');
            result.data.list[i].subject = result.data.list[i].subject.replace(regex, '<span>'+chText+'</span>');
          }
        }
      //*/
        }else{
          this.isFindSuccess = false;
        }
        this.currentPage = page;
        this.faqData = result.data.list;
        this.listTotal = result.data.listcount;  
      })

    },
    /*********************
     * 스크롤페이징 처리
     *********************/
    infiniteHandler($state) {
      if(this.isloading){
          var params = {
              searchkeyword : this.seachKeyword,    
              startpage : this.currentPage +1,
              //페이지당 보여줄 갯수
              perpage: this.listCnt,                
          }
          this.$http.post('/cscenter/faq', params).then(result => {
              if (result.statusCode == 200) {
                  if (result.data.list.length == 0) {
                      $state.complete();
                      this.isloading = false;
                  } else {
                      $state.loaded();
                      this.isloading = true;
                      this.currentPage += 1;
                      
                      //* 하이라이팅
                      var chText = this.seachKeyword;
                      for(let i=0; i < result.data.list.length; i++){
                        var regex = new RegExp(chText, 'g');
                        var splitSubject = result.data.list[i].subject.split(regex);
                        result.data.list[i].subject = result.data.list[i].subject.replace(regex, '<span>'+chText+'</span>');
                        var dupRegex = new RegExp(chText, 'gi');
                        var cprSubject = result.data.list[i].subject.split(dupRegex);
                        if(splitSubject.length != cprSubject.length){
                          if(chText == chText.toUpperCase()){
                            chText = chText.toLowerCase();
                          }else{
                            chText = chText.toUpperCase();
                          }
                          regex = new RegExp(chText, 'g');
                          result.data.list[i].subject = result.data.list[i].subject.replace(regex, '<span>'+chText+'</span>');
                        }              
                      }
                      //*/                
                      this.faqData = [...this.faqData || [], ...result.data.list];
                  }
                  this.$util.debug(this.faqData);
              }
          });
      }
    },        
    handleInput(val) {
      this.inputData = val;
    },
    isEnter(inputKeyword){
      if(inputKeyword == ""){
        this.$eventBus.$emit('alert', '확인' ,"검색어를 입력해주세요.");
        return;
      }
      if(inputKeyword == this.seachKeyword){
        return;
      }
      this.$router.push({name:'cs-cs-find-result', query: {searchKeyword: inputKeyword}});
    },
    openModal(){
      this.$util.debug("openModal ...");  
      var param = {}
      this.$eventBus.$emit('showModal', WriteModal, 'WriteModal', param, (param) => {
      });
    },    
    goToFaq() {
      if(this.inputKeyword == ""){
        alert("검색어를 입력해주세요.");
        return;
      }
      if(this.inputKeyword == this.seachKeyword){
        return;
      }
      this.$router.push({name:'cs-cs-find-result', query: {searchKeyword: this.inputKeyword}});
    },
    goToInquiry() {
      this.$router.push("/cs/inquiry/index/inquiry");
    },
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
                  this.questionData.push(temp);
              }
          })
    },
    //모바일 - 자주하는 질문 - 네모 박스 클릭 시
    changeFaq(id){
      this.$util.debug("changeFaq.................");
      this.$util.debug(id);
      this.$router.push({name:'cs-cs-faq', params: {faqtype: id}});
    },
    goToWrite(){
      this.$router.push({name:'write-inquiry-register'});
    },    
  },
  watch: {
    '$route' (to, from) {
        this.onInit();
        this.faqCommon();
    }
  },
};
