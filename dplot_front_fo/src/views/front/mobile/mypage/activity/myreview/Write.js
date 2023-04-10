import WriteCaution from './popup/WriteCaution.vue'
import ReviewReward from './popup/ReviewReward.vue'

export default {
  beforeCreate() {
    this.$store.commit("onSubHeaderOption", {
      title: "리뷰등록",
      searchIcon: true,
      cartIcon: true,
      homeIcon: false,
    });
  },
  data() {
    return {
      reviewParam: {
        isEdit: "F",
        totpoint: 0,
        content: "",
        files: [{
          filetype: null,
          imgforiname: null,
          imgpath: null,
          reguserid: null,
          sort: null,
          imgtype: null,
          istrash: null,
          fullpath: null,
          host: null,
          imgfname: null,
          regdate: null,
          orgidx: null,
          idx: null,
          status: null
        }]//files고정으로 가야함 keycheck함
      },
      reviewDetail: {},
    };
  },
  methods: {
    showModal(modalId) {
      if (modalId == 'writeCautionModal') {
        this.$eventBus.$emit('showModal', WriteCaution, modalId);
      } else if (modalId == 'reviewRewardModal') {
        this.$eventBus.$emit('showModal', ReviewReward, modalId);
      } else {
        return;
      }
    },
    /*********************************
     * 리뷰 상세 조회
     *********************************/
    getMyReviewDetail() {
      let param = {
        orderidx: this.$route.query.orderidx,
        ordgdidx: this.$route.query.ordgdidx
      }
      this.$http.post('/review/detail', param).then(result => {
        if (result.statusCode == 200) {
          this.$util.debug("리뷰 상세 조회");
          this.$util.debug(JSON.stringify(result.data.reviewdetail));
          this.reviewDetail = result.data.reviewdetail;
          let optionnames = this.reviewDetail.optionnm.split(',');
          this.reviewDetail.opthtml = '';
          if (!this.$util.isBlank(this.reviewDetail.optionnm) && optionnames.length > 0) {
            
            optionnames[0] = (this.reviewDetail.isaddgoods == "T" ? "추가상품: " : "옵션: ") + optionnames[0];
            for (let i = 0; i < optionnames.length; i++) {
              optionnames[i] = '<span>' + optionnames[i] + '</span>';
            }
            this.reviewDetail.opthtml = optionnames.join('<span class="dp-bar h10"></span>');
          }
          this.reviewDetail.reviewmax = this.reviewmax;

          if (this.reviewDetail.isreview == "T") {
            this.$store.commit("onSubHeaderOption", {
              title: "리뷰수정",
              searchIcon: true,
              cartIcon: true,
              homeIcon: false,
            });
            this.reviewParam.totpoint = this.reviewDetail.totpoint;
            //this.reviewParam.content = this.reviewDetail.content;
            this.reviewParam.content = this.reviewDetail.content.replace(/(<br>|<br\/>|<br \/>)/g, '\r\n');
            this.reviewParam.files = this.reviewDetail.files;
            this.reviewParam.isEdit = "T";
            this.$refs.imageupload.bindFile(this.reviewParam.files);
          }
        }
      })
    },
    /*********************************
     * 첨부파일 변경시 처리
     *********************************/
    changeFile(files, deletelist) {
      this.$util.debug("changeFile....");
      this.$util.debug(files);
      this.$util.debug(deletelist);
      this.reviewParam.files = files;
      this.reviewParam.deletefilelist = deletelist;
    },
    /*********************************
    * 리뷰 등록처리
    *********************************/
    saveMyReview() {
      if (this.reviewParam.totpoint == 0 || this.$util.isNull(this.reviewParam.totpoint)) {
        this.$eventBus.$emit('alert', '알림', "별점을 선택해주세요.");
        return;
      }
      if (this.reviewParam.content.length < 10) {
        this.$eventBus.$emit('alert', '알림', "리뷰를 최소 10자 이상 작성해주세요.");
        return;
      }
      if (this.reviewParam.content.length > 500) {
        this.$eventBus.$emit('alert', '알림', "리뷰는 최대 500자까지 작성가능합니다.");
        return;
      }
      this.$util.debug(JSON.stringify(this.reviewParam));
      this.$refs.imageupload.emitFileList();
      this.reviewParam.orderidx = this.$route.query.orderidx;
      this.reviewParam.ordgdidx = this.$route.query.ordgdidx;
      this.reviewParam.reviewidx = this.reviewDetail.reviewidx;
      this.reviewParam.goodsno = this.reviewDetail.goodsno;

      this.$http.post('/review/save', this.reviewParam).then(result => {
        if (result.statusCode == 200) {
          this.$util.debug("리뷰 저장 처리성공");
          this.$router.replace({ name: 'after' });
        } else {
          this.$eventBus.$emit('alert', '알림', result.message);
        }
      })
    },
    searchChangeReviewFunc(){
      this.reviewParam.content = this.reviewParam.content.split('  ').join(' ');
    },
  },
  mounted() {
    this.reviewParam.orderidx = this.$route.query.orderidx;
    this.reviewParam.orderidx = this.$route.query.orderidx;

    this.getMyReviewDetail();
  },
};