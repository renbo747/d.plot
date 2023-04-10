<template>
    <!-- 주문상품선택 Modal -->
    <b-modal
      id="AsApplyModal"
      modal-class="dp-modal page-layer select-product-modal"
      scrollable
      centered
      :hide-footer="true"
    >
      <!-- 주문상품선택 Modal Header -->
      <template #modal-header="{ cancel }">
        <h5 class="modal-title">주문상품선택</h5>
        <i class="modal-close" @click="cancel()">
          <img
            src="@assets.mobile/img/icon/icon-close-black-20px.svg"
            alt="닫기"
          />
        </i>
      </template>

      <!-- 주문상품선택 Modal Body -->
      <div class="select-product-header">
        <!-- scss index scss 안 select box 참조 -->
        <div class="cs-product-qna dp-panel pt-0">
          <div class="cs-product-qna__header__select row">
            <div class="col-12">
              <base-select
                placeholder="최근 1개월"
                class="inquiry-select"
                v-model="popMonthSelect"
                :options="popMonthOption"
                @change="popMonthCheck"
              />
            </div>
          </div>
        </div>
      </div>
      <div class="select-product-body list">
        <div
            v-for="(list, index) in product"
            :key="index"
        >
        <section class="select-product-area dp-panel pt-0">
          <div>
            <div class="select-product-item-info pt-0">
              <div class="select-product-item">
                <div class="select-product-date__list">
                  <div class="select-product-date__list__data">
                    <span class="date__list__span text-gray-700 font-weight-400"
                      >{{list.regdate}}</span
                    >
                    <span class="dp-bar select-product-date-bar"></span>
                    <span class="date__list__span text-gray-800 font-weight-500"
                      >{{list.ordno}}</span
                    >
                  </div>
                </div>
              </div>
            </div>
            <ul class="select-product-item-ul list-style-none dp-panel">
              <li class="select-product-item-li">
                <div>
                  <product-inquiry
                    :is-header="true"
                    :is-badge="false"
                    :is-price="false"
                    :is-option="true"
                    :is-icon="false"
                    :is-modal="false"
                    :key="skey"
                    :productInfo="list"
                    @change="checkChange(list)"
                  />
                </div>
              </li>
            </ul>
          </div>
          <hr class="dp-hr h6" />
        </section>
        </div>
      </div>
      <div class="select-footer-section">
        <div class="footer-section-btn btn-group row">
          <b-button
            class="dp-btn text-gray-800 login-btn__close col-6"
            variant="outline-gray-800 not-hover"
            block
            @click="closeModal()"
            >취소하기</b-button
          >
          <b-button
            class="dp-btn text-white login-btn__confirm col-6"
            variant="gray-800"
            @click="goAs()"
            >선택하기</b-button
          >
        </div>
      </div>

    </b-modal>
    <!-- // 주문상품선택 Modal -->
</template>

<style scoped>
  .select-footer-section {
    bottom:15px;
  }
  .select-product-body.list {
    height:65vh;overflow:scroll;overflow-x:hidden;margin-bottom:30px;
  }
</style>

<script>
export default {
    props:{
        param : Object,
        fnConfirm :  { type: Function },
    },
    mounted() {
      var param = {
        date: this.popMonthSelect,
      }
      this.onInit(param);
    },  
    data() {
      return {
        skey: 0,                      //checkbox 선택키
        popMonthSelect: "m1",          //선택된 개월
        product: [],
        //문의분류 리스트
        popInquiryOption: [
            {
              label: "전체",
              value: "all",
            },
        ],            
        //개월수 선택 리스트
        popMonthOption: [
          {
            label: "최근 1개월",
            value: "m1",
          },
          {
            label: "최근 3개월",
            value: "m3",
          },
          {
            label: "최근 6개월",
            value: "m6",
          },
          {
            label: "최근 1년",
            value: "y1",
          },
        ],
        
      }
    },
    methods: {
      /*********************************
       * 화면 데이터 로딩
       *********************************/
      onInit(param){
        this.$util.debug("onInit....");
        this.$http.post('/mypage/as/applymyorder',param).then(result => {
            if(result.statusCode == 200){
              this.$util.debug(result.data.list);
              this.product = result.data.list;
              for(var i=0; i<this.product.length; i++){
                this.product[i].chk = false;
                this.product[i].opthtml = this.product[i].optionname;
                this.product[i].ordcnt = this.product[i].asordcnt;
              }
            }
        })
      },
      /*********************************
       * 개월 수 선택 값 세팅
       *********************************/
      popMonthCheck(val){
        this.$util.debug("popMonthCheck....");
        
        this.popMonthSelect = val;

        this.$util.debug("현재 선택....");
        this.$util.debug(this.popMonthSelect);
        var param = {
          date: this.popMonthSelect,
        }
        this.onInit(param);

      },
      /*********************************
       * 체크박스 선택 이벤트
       *********************************/
      checkChange(item){
        this.$util.debug("as checkChange....");
        this.$util.debug(item);
        for(let i = 0 ; i < this.product.length ; i++) {
          if(item.ordgdidx != this.product[i].ordgdidx){
            this.product[i].chk = false;
          }
        }
        this.skey = Date.now();
      },
      closeModal() {
        this.$root.$emit("bv::hide::modal", "AsApplyModal");
      },
      /*********************************
       * 선택하기 버튼 클릭
       *********************************/
      goAs(){
        var chk = false;
        var ordgdidx = "";
        for(var i=0; i < this.product.length; i++){
          if(this.product[i].chk == true){
            chk = true;
            ordgdidx = this.product[i].ordgdidx;
          }        
        }

        if(chk){
          this.$router.push({ name: 'apply-as', params:{pid:ordgdidx}});
          this.$root.$emit("bv::hide::modal", "AsApplyModal");
        }else{
          this.$eventBus.$emit('alert', '확인' ,"주문목록을 선택해주세요.");
          return;
        }      
      },
    },
};
</script>
