import PromotionList from "@views.common/components/shop/promotion/PromotionList";
import promotionStModal from './popup/promotionStModal.vue';

export default {
  components: {
    PromotionList,
  },
  beforeCreate() {
    this.$store.commit("onSubHeaderOption", {
      title: "PROMOTION",
      searchIcon: true,
      cartIcon: true,
      homeIcon: false,
    });
  },
  data() {
    return {
      pagingData: {
        currentPage: 1,  // 현재 페이지
        listTotal: 0,   // 조회목록 전체 수
        listCnt: 4,   // 한페이지에 노출할 목록수
        init: 'F'
      },
      isevent: "all",
      mainList: [],
      //Tab 관련 시작 //////////////////////////////////////////////////////////
      tabTitle: [
        {
          title: "전체",
          value: "all",
        },
        {
          title: "이벤트",
          value: "event",
        },
        {
          title: "기획전",
          value: "special"
        }],
      isEnd: false
      //Tab 관련 시작 //////////////////////////////////////////////////////////        
    };
  },
  methods: {
    /*********************************
     * 메인 접근
     *********************************/
    onInit() {
      this.$util.debug("onInit.......");
      //1:1 문의 데이터 로드
      this.isEnd = false;
      let param = Object.assign({}, this.pagingData);
      param.iskeep = true;
      param.reqname = this.$route.name;

      this.$http.post('/shop/promotion', param).then(result => {
        if (result.statusCode == 200) {
          this.pagingData.listTotal = result.data.listcount;
          this.mainList = result.data.list;
          this.isEnd = true;
        }
      })
    },
    /*********************************
     * 모달창 오픈 이벤트
     *********************************/
    openModal(modalId, list) {
      if (modalId === 'promotionStModal') {
        if (list.postidx != "null") {
          this.$util.debug("promotionStModal open .................");
          this.$eventBus.$emit("showModal", promotionStModal, modalId, { list: list });
        } else {
          this.$eventBus.$emit("alert", "확인", "당첨자 내역이 없습니다.")
        }
      }
    },
    /*********************************
     * 프로모션 탭 전환 관련 이벤트
     *********************************/
    changeTap(name) {
      this.$router.replace({ name: name, params: { init: true } });
    },
    /*********************************
     * 페이징 처리
     *********************************/
    changePage(page) {
      this.pagingData.currentPage = page;
      this.$store.commit("savedPosition", {});
      this.onInit();
    },
  },
  created() {
    if (this.$route.params.init) {
      this.$storage.removeSessionStorage('param-' + this.$route.name);
    } else {
      const param = this.$storage.getSessionStorage('param-' + this.$route.name);
      if (!this.$util.isEmpty(param)) {
        this.pagingData.currentPage = param.currentPage;
      }
    }
  },
  mounted() {
    this.onInit();
  },
  updated() {
    this.$nextTick(() => {
      if (this.mainList.length > 0) {
        this.$eventBus.$emit('scrollTo', this.$route.name, (flag) => {
          if (!flag) {
            window.scrollTo(0, 0);
          }
        });
      } else {
        window.scrollTo(0, 0);
      }
    })
  }
};