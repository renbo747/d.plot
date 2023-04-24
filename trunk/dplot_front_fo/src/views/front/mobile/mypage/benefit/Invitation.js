import FriendInvitation from './popup/FriendInvitation.vue'
import InfiniteLoading from "vue-infinite-loading";
export default {
  beforeCreate() {
    this.$store.commit("onSubHeaderOption", {
      title: "초대 친구목록",
      searchIcon: true,
      cartIcon: true,
      homeIcon: false,
    });
  },
  components: {
    InfiniteLoading
  },
  data() {
    return {
      // 초대로 가입한 친구의 유무
      isInvitation: true,
      // 가입한 친구목록
      memberBenefitInfo: [
      ],
      constants: this.$store.getters['MEMBER'],
      rewardList: [],
      isloading: true,           //스크롤페이징 조회할지 여부
      inviteinfo: {},
      infiniteId: + new Date(),   //스크롤페이징 id
      membercnt: 0,
      reclimitcnt: 0,
      pagingData: {
        currentPage: 1,         // 현재 페이지
        listTotal: 0,           // 조회목록 전체 수
        listCnt: 5              // 한페이지에 노출할 목록수
      },
    };
  },
  methods: {
    /**********************
      * 데이터 초기화
      ********************/
    initData() {
      this.rewardList = [];
      this.infiniteId += 1;
      this.isloading = true;
      this.pagingData.currentPage = 1;
      this.pagingData.listTotal = 0;
    },
    /*******************************
     * 리워드 보상정보 조회
     ******************************/
    getRewardInfo($state) {
      if (this.isloading) {
   
        this.$http.post('/mypage/rewardpay/list', this.pagingData).then(result => {
          if (result.statusCode == 200) {
            this.membercnt = result.data.membercnt;
            this.reclimitcnt = result.data.reclimitcnt;
            if (result.data.rewardpaylist.length ==  0) {
              this.isloading = false;
              $state.complete();
            }else {
              this.rewardList = [...this.rewardList || [], ...result.data.rewardpaylist];
              this.pagingData.listTotal = result.data.totcnt;
              this.pagingData.currentPage += 1;
              $state.loaded();
              this.isloading = true;
            }
          }
        })
      }
    },
    openModal() {
      this.$eventBus.$emit('showModal', FriendInvitation, 'friendInvitationModal');
    }
  },
  mounted() {
    this.initData();
  },

};