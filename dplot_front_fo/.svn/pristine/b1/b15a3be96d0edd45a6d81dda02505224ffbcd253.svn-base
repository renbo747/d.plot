/**
 * 메시지 목록 페이지(마이페이지_54P 참고)
 * 1.PUSH, 알림톡, SMS
 * 2.수신일로부터 1개월(최신순)
 * 3.데이터(수신일, 제목(생략가능), 내용, 링크(SMS는 메시지내의 URL))
 * 4.삭제는 CONFIRM없이 바로 삭제처리
 */
import InfiniteLoading from "vue-infinite-loading";
export default {
  beforeCreate() {
    this.$store.commit("onSubHeaderOption", {
      title: "Message",
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
      messageList: [],
      isloading: true,           //스크롤페이징 조회할지 여부
      infiniteId: + new Date(),   //스크롤페이징 id
      pagingData: {
        currentPage: 1,         // 현재 페이지
        listTotal: 0,           // 조회목록 전체 수
        listCnt: 5              // 한페이지에 노출할 목록수
      },
      isEnd:false
    };
  },
  methods: {
    /**********************
     * 데이터 초기화
     ********************/
    initData() {
      this.messageList = [];
      this.infiniteId += 1;
      this.isloading = true;
      this.pagingData.currentPage = 1;
      this.pagingData.listTotal = 0;
    },
    /*********************************
     * 메시지 조회
     ********************************/
    getMessageList($state) {
      let param = Object.assign(this.pagingData);
      this.isEnd = false;
      this.$http.post("/mypage/msgList", param).then((result) => {
        if (result.statusCode == 200) {
          if (result.data.messagelist.length == 0) {
            this.isloading = false;
            $state.complete();
          } else {
            this.isloading = true;
            this.pagingData.currentPage += 1;

            let msglist  = [...result.data.messagelist];
            msglist.forEach(item => {
              item.message = item.message.replaceAll('\n', '<br/>');
            });
            this.messageList = [...this.messageList || [], ...msglist];
            this.pagingData.listTotal = this.messageList.length;
            this.$util.debug(this.messageList);
            $state.loaded();
          }
        }
        this.isEnd = true;
      })
    },
    /*********************************
     * 메시지 삭제
     ********************************/
    deleteMessage(msgidx) {
      let param = {
        msgidx: msgidx
      }
      this.$http.post("mypage/msgList/del", param).then((result => {
        if (result.statusCode == 200) {
          this.$util.debug("삭제완료");
          this.initData();
        }
      }))
    },
    /*********************************
    * message에 링크있을경우 열기
    ********************************/
    goLink(url) {
      if (!this.$util.isNull(url)) {
        window.open(url, "_blank");
      }
    }
  },
  mounted() {
    this.initData();
  }
};