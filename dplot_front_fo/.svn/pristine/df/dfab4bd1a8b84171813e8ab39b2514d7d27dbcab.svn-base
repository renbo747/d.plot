<template>
    <!-- 주문상품선택 Modal -->
    <b-modal
      id="AsApplyModal"
      modal-class="dp-modal page-layer select-product-modal"
      scrollable
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
      <div class="select-product-body">
        <div v-for="(list, index) in product" :key="index">
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
        <section class="select-footer-section">
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
        </section>
      </div>
    </b-modal>
    <!-- // 주문상품선택 Modal -->
</template>

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
        product: [],
      }
    },
    methods: {
      /*********************************
       * 화면 데이터 로딩
       *********************************/
      onInit(param){
        this.$util.debug("onInit....");
        this.$http.post('/nonemember/as/applymyorder',param).then(result => {
            if(result.statusCode == 200){
              this.$util.debug(result.data.list);
              this.product = result.data.list;
              for(var i=0; i<this.product.length; i++){
                this.product[i].chk = false;
                this.product[i].opthtml = this.product[i].optionname;
                this.product[i].ordcnt = result.data.list[i].asordcnt;
              }
            }
        })
      },
      /*********************************
       * 체크박스 선택 이벤트
       *********************************/
      checkChange(item){
        this.$util.debug("checkChange....");
        this.$util.debug(item);
        for(let i = 0 ; i < this.product.length ; i++) {
          if(item.orderidx != this.product[i].orderidx){
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
          this.$router.push({ name: 'nonemember-apply-as', params:{pid:ordgdidx}});
          this.$root.$emit("bv::hide::modal", "AsApplyModal");
        }else{
          this.$eventBus.$emit('alert', '확인' ,"주문목록을 선택해주세요.");
          return;
        }      
      },
    },
};
</script>
