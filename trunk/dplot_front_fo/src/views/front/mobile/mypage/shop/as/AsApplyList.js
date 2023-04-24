import InfiniteLoading from "vue-infinite-loading";
import AsApplyModal from './popup/AsApplyModal.vue';

export default {
  components: {
    InfiniteLoading
  },
  beforeCreate() {
    this.$store.commit("onSubHeaderOption", {
      title: "AS전용 문의 내역",
      searchIcon: true,
      cartIcon: true,
      homeIcon: false,
    });
  },
  created() {
    if (this.$route.params.init) {
      this.$storage.removeSessionStorage('param-' + this.$route.name);
      //this.$storage.removeSessionStorage('list-' + this.$route.name);
    } else {
      const param = this.$storage.getSessionStorage('param-' + this.$route.name);
      if (!this.$util.isEmpty(param)) {
        this.month = param.lastmonth;
        this.asStatus = param.asstatus;
        this.pagingData.currentPage = param.currentPage;
        this.pagingData.init = 'T';
      }
      // const list = this.$storage.getSessionStorage('list-' + this.$route.name);
      // if(!this.$util.isEmpty(list)) {
      //     this.product = list;
      // }
    }
    this.commonTypeCode();
  },
  updated() {
    this.$nextTick(() => {
      if (this.product.length > 0 && this.pagingData.init == 'T') {
        this.$eventBus.$emit('scrollTo', this.$route.name);
        this.$storage.removeSessionStorage('param-' + this.$route.name);
        this.pagingData.init = 'F';
        this.isTop = false;
      } else {
        if (this.isTop) {
          this.$util.scrollToTop();
          this.isTop = false;
        }
      }
    });
  },
  data() {
    return {
      infiniteId: + new Date(),
      pagingData: {
        currentPage: 1,         // 현재 페이지
        listTotal: 0,           // 조회목록 전체 수
        listCnt: 5,             // 한페이지에 노출할 목록수
        init: 'F'
      },
      isloading: true,
      month: "1",          //최근 개월수 선택 했을 때
      asStatus: "all",       //문의 타입 선택 했을 때
      monthSelect: [],
      asStatusSelect: [],
      //접수 리스트
      product: [],
      //내 주문목록
      myOrder: [],
      isEnd: false,
      isTop:true
    };
  },
  methods: {
    /**********************
     * 데이터 초기화
     ********************/
    initData() {
      this.infiniteId += 1;
      this.pagingData.listTotal = 0;
      this.product = [];
      this.isEnd = false;
    },
    /*********************
     * AS전용 문의 유형 코드 호출
     *********************/
    commonTypeCode() {
      this.$util.debug("commonTypeCode.......");
      this.asStatusSelect = [];
      this.asStatusSelect.push({
        label: "전체",
        value: "all",
      });

      //공통코드 목록 불러오기
      let cmclassArr = ['ASSTATUS'];
      this.$http.post('/common/code/map/list', { cmclass: cmclassArr, isloading: false })
        .then(result => {
          for (var i = 0; i < result.data.asstatus.length; i++) {
            var temp = {};
            if (result.data.asstatus[i].cmcode != 'AST002') {
              temp.value = result.data.asstatus[i].cmcode;
              temp.label = result.data.asstatus[i].codename;
              this.asStatusSelect.push(temp);
            }
          }

          this.monthSelect = [
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
          ]
        })
    },
    goToAsDetail(asidx) {
      this.$router.push({ name: "mypage-as-detail", params: { pid: asidx } });
    },
    /*********************
     * as 목록 조회(스크롤페이징 처리)
     *********************/
    infiniteHandler($state) {
      let param = {
        lastmonth: this.month,
        asstatus: this.asStatus,
        iskeep: true,
        reqname: this.$route.name
      }
      param = Object.assign(param, this.pagingData);
      this.isEnd = false;
      this.$http.post('/mypage/as/applylist', param).then(result => {
        if (result.statusCode == 200) {
          this.pagingData.currentPage += 1;
          this.pagingData.listTotal = result.data.listcnt;
          this.myOrder = result.data.myorder;

          for (let i = 0; i < result.data.list.length; i++) {
            this.product.push(result.data.list[i]);
          }

          if (result.data.list.length == 0 || result.data.list.length < this.pagingData.listCnt) {
            $state.complete();
          } else {
            $state.loaded();
          }
          //this.$storage.setSessionStorage('list-' + this.$route.name, this.product);
        } else {
          $state.complete();
        }
        this.isEnd = true;
      });
    },
    /*********************
     * 검색조건변경
     *********************/
    changeSearch() {
      this.$util.debug("changeSearch ........................");
      this.pagingData.currentPage = 1;
      this.initData();
    },
    openModal() {
      if (this.$util.isEmpty(this.myOrder)) {
        this.$eventBus.$emit('alert', '알림', "AS문의가 가능한 상품이 없습니다.");
      } else {
        this.$util.debug("openModal ........................");
        this.$eventBus.$emit("showModal", AsApplyModal, 'AsApplyModal', {}, () => {
          this.$util.debug("AsApplyModal Close ....");
          this.$util.debug("AsApplyModal Close result data >>>>>>>>>>>>>>>>");
        });
      }
    },
  },
};