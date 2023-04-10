<template>
          <b-modal
        id="promotionStModal"
        modal-class="dp-modal page-layer promotion-modal"
        scrollable
        :hide-footer="true"
      >
        <!-- Page Modal Header -->
        <template #modal-header="{ cancel }">
          <h5 class="modal-title">당첨자발표</h5>
          <i class="modal-close" @click="cancel()">
            <img
              src="@assets.mobile/img/icon/icon-close-black-20px.svg"
              alt="닫기"
            />
          </i>
        </template>

        <!-- Page Modal Body -->
        <div class="page-layer__body">
          <div class="promotion-modal__description-title">
            <span class="dp-p-sm promotion-modal__title">[{{eventList.subject}}]</span>
            {{eventList.notice_desc}}
          </div>

          <p class="promotion-modal__date dp-p-sm mb-0">
            발표일 : {{list.pubtime}}
          </p>

          <hr class="dp-hr h1" />

          <div class="promotion-modal__text-wrap" v-html="eventList.mobilecontent">

          </div>
          <div class="winning-list">
            <p class="winning-list__title">명단</p>
            <ul class="winning-list__ul list-style-none">
              <li
              v-for="(list, index) in winnerList"
              :key="index"              
              >{{list.userid}}</li>
            </ul>
          </div>
          <b-button @click="closeModal()" class="dp-btn text-white" variant="gray-800" squared>
            <span>확인</span>
          </b-button>
        </div>
      </b-modal>
</template>

<script>
export default {
  props: {
    param: Object,
    fnConfirm: { type: Function },
  },
  mounted() {
    this.list = this.param.list;
    this.eventidx = this.param.list.eventidx;
    this.postidx = this.param.list.postidx;
    if(this.postidx != 'null'){
      this.onEvent({postidx: this.postidx, eventidx: this.eventidx});
    }

  },
  data() {
    return {
      eventidx: 0,      //전달받은 idx 세팅 변수
      postidx: "null",       //당첨자 발표 idx(없으면 string null 로 들어옴)
      list: [],    //param으로 전달받은 list 데이터 세팅 변수
      eventList: [],  //이벤트 내용 리스트
      winnerList: [], //당첨자 명단 리스트
    };
  },
  methods: {
    /*********************************
     * 이벤트 내용 조회
     *********************************/
    onEvent(param) {
      this.$util.debug("onEvent ..........................");
      this.$util.debug(param.postidx);

      this.$http.post('/shop/winnerant', param).then(result => {
        if (result.statusCode == 200) {
          this.$util.debug("onEvent success.......");
          this.eventList = result.data.list;
          this.winnerList = result.data.winnerlist;
        }
      })      


    }, 
    /*********************************
     * Modal 닫기 이벤트
     *********************************/
    closeModal() {
      this.$util.debug("closeModal .........................");
      this.$root.$emit("bv::hide::modal", "promotionStModal");
    },   
  },
  watch: {
  }
};

</script>
