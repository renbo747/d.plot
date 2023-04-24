
export default {
  data() {
    return {
      memberInfo:{},
      orderCntInfo:{},
    };
  },
  methods: {
  /********************************
   * 마이페이지 정보조회
   ********************************/
    getMypageInfo() {
      this.$http.post('/nonemember/list', {}).then(result => {
        if (result.statusCode == 200) {
          if(result.data.memberinfo == 0) {
            this.$eventBus.$emit('alert', '비회원 주문조회 해주세요.', ()=>{
              this.$router.replace('login');
            });
            return;
          }
          this.memberInfo = result.data.memberinfo;
          this.orderCntInfo = result.data.ordercntinfo;
          this.orderCntInfo.deliing = this.orderCntInfo.prepgoodscnt +  this.orderCntInfo.prepdelivcnt;
          //취소수량(클레임취소 + 입금전취소)
          this.orderCntInfo.claimcnccnt = this.orderCntInfo.claimcnccnt + this.orderCntInfo.cancelcnt;
        }
      })
    },
  },
  mounted() {
    this.getMypageInfo();
  },
};