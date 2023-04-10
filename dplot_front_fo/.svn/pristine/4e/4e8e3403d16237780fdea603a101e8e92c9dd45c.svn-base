import StoreInquiryModal from './popup/StoreInquiryModal.vue';
import WriteModal from './inquiry/popup/WriteModal.vue';

export default {
  beforeCreate() {
    this.$store.commit("onSubHeaderOption", {
      title: "고객센터",
      searchIcon: true,
      cartIcon: true,
      homeIcon: false,
    });
  },
  props: {
    id: {
      type: String,
    },
  },
  data() {
    return {
      searchKeyword: "",
      faqData: [],
      noticeData: [],
      pagingData: {
        currentPage: 1,  // 현재 페이지
        listTotal: 0,   // 조회목록 전체 수
        listCnt: 6,   // 한페이지에 노출할 목록수
        init: 'F'
      },
    };
  },
  methods: {
    onFaq(param) {
      this.$http.post('/cscenter/mainfaq', param).then(result => {
        this.faqData = result.data.list;
      }).catch(error => {
        this.$util.debug(error);
      })
    },
    onNotice() {
      let param = Object.assign({}, this.pagingData);
      param.iskeep = true;
      param.reqname = this.$route.name;
      this.$http.post('/cscenter/notice', param).then(result => {
        this.noticeData = result.data.noticelist;
      }).catch(error => {
        this.$util.debug(error);
      })
    },
    handleSearch() {
    },
    isEnter(searchKeyword) {
      if (searchKeyword == "") {
        this.$eventBus.$emit('alert', '확인', "검색어를 입력해주세요.");
        return;
      }
      this.$router.push({ name: 'cs-cs-find-result', query: { searchKeyword: searchKeyword } });
    },
    searchClick() {
      if (this.searchKeyword == "") {
        this.$eventBus.$emit('alert', '확인', "검색어를 입력해주세요.");
        return;
      }
      this.$router.push({ name: 'cs-cs-find-result', query: { searchKeyword: this.searchKeyword } });
    },
    goToFaq(type) {
      this.$router.push({ name: 'cs-cs-faq', params: { faqType: type } });
    },
    faqHit(idx, index) {
      this.$util.debug(idx);
      this.faqData[index].isOpen = this.$util.isNull(this.faqData[index].isOpen) ? true : !this.faqData[index].isOpen;
      if (this.faqData[index].isOpen) {
        var param = {
          idx: idx
          , isloading: false
        }
        this.$http.post('/cscenter/hit', param).then(result => {
        }).catch(error => {
          this.$util.debug(error);
        })
      }
    },
    goFaqPage() {
      this.$router.push({ name: 'cs-cs-faq', params: { faqtype: '1', init: true } });
    },
    goDetail(idx) {
      var param = {
        idx: idx
        , isloading: false
      }
      this.$http.post('/cscenter/hit', param).then(result => {
        this.$router.push({ name: 'cs-notice-detail', params: { idx: idx } });
      }).catch(error => {
        this.$util.debug(error);
      })
    },
    goToInquiry() {
      this.$router.push({ name: 'cs-inquiry-index' });
    },
    //pc만 입점문의 링크
    goStoreInquiryPC() {
      this.$router.push({ name: 'cs-inquiry-store-inquiry' });
    },
    goToWrite() {
      this.$router.push({ name: 'write-inquiry-register' });
    },
    sendChk() {
      this.$util.debug("click");
    },
    openModal() {
      if (!this.$store.state.isLogin) {
        // this.$eventBus.$emit("alert", "알림", "로그인 후 이용해주세요.");
        this.$eventBus.$emit('confirm', '로그인', '로그인 하시겠습니까?', () => {
          this.$storage.setSessionStorage('redirectPath', { path: this.$route.path, query: this.$route.query });
          this.$router.push({ name: 'member-login' });
        });
        return;
      }
      this.$util.debug("openModal ...");
      var param = {}
      this.$eventBus.$emit('showModal', WriteModal, 'WriteModal', param, (param) => {
      });
    },
  },
  created() {
    if (this.$route.params.init) {
      this.$storage.removeSessionStorage('param-' + this.$route.name);
    } else {
      const param = this.$storage.getSessionStorage('param-' + this.$route.name);
      if (!this.$util.isEmpty(param)) {
        this.pagingData.init = 'T';
      }
    }
  },
  mounted() {
    var param = {
      //자주하는 질문 노출 개수
      page: 0,
      limit: 6,
    };
    this.onFaq(param);

    this.onNotice();
  },
  updated() {
    this.$nextTick(() => {
      if (this.noticeData.length > 0 && this.pagingData.init == 'T') {
        this.$eventBus.$emit('scrollTo', this.$route.name);
        this.pagingData.init = 'F';
      }
    });
  },
};