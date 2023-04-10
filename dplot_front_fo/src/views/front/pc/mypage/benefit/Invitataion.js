export default {
    data() {
        return {
            // 초대로 가입한 친구의 유무
            isInvitation: true,
            // 가입한 친구목록
            inviteinfo: {},
            membercnt: 0,
            reclimitcnt: 0,
            rewardList: [],
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
            this.inviteinfo = {};
            this.pagingData.currentPage = 1;
            this.pagingData.listTotal = 0;
            this.getRewardInfo();
        },
        /*****************************
        * 페이지 변경시
        ****************************/
        changePage(page) {
            this.pagingData.currentPage = page;
            this.rewardList = [];
            this.getRewardInfo();
        },
        /*******************************
         * 리워드 보상정보 조회
         ******************************/
        getRewardInfo() {
            this.$http.post('/mypage/rewardpay/list', this.pagingData).then(result => {
                if (result.statusCode == 200) {
                    this.rewardList = result.data.rewardpaylist;
                    this.membercnt = result.data.membercnt;
                    this.reclimitcnt = result.data.reclimitcnt;
                    this.pagingData.listTotal = result.data.totcnt;
                }
            })
        },
        /*******************************
         * 친구초대 상세보기
         ******************************/
        goInvitationEvent() {
            this.$router.push("/mypage/friend-invitation");
        },
    },
    mounted() {
        this.initData();
    }
};