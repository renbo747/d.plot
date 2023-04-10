<template>
    <!-- 주문상품선택 Modal -->
    <b-modal
      id="OrdProductPCModal"
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

      <div class="select-product-header">
        <!-- scss index scss 안 select box 참조 -->
        <div class="cs-product-qna dp-panel pt-0">
          <div class="cs-product-qna__header__select row">
            <div class="col-6">
              <base-dropdown
                placeholder="최근 1개월"
                class="inquiry-select"
                v-model="popMonthSelect"
                :options="popMonthOption"
                @change="popMonthCheck"
              />
            </div>
            <div class="col-6">
              <base-dropdown
                placeholder="전체문의"
                class="inquiry-select"
                v-model="popInquirySelect"
                :options="popInquiryOption"
                @change="popInquiryCheck"
              />
            </div>
          </div>
        </div>
      </div>
      <!-- 주문상품선택 Modal Body -->
      <div class="select-product-body" v-if="orderList.length > 0">

        <div
            v-for="(list, index) in orderList"
            :key="index"
        >
        <section class="select-product-area dp-panel pt-0" v-if="!$util.isEmpty(list.goods)">
          <div>
            <div class="select-product-item-info pt-0">
              <div class="select-product-item">
                <div class="select-product-date__list">
                  <div class="select-product-date__list__data">
                    <span class="date__list__span text-gray-700 font-weight-400"
                      >{{list.orderdate}}</span
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
              <li class="select-product-item-li"
                v-for="(item, index2) in list.goods"
                :key="index2"              
              >
                <div>
                  <product-order-pc
                    :is-badge="false"
                    :is-price="false"
                    :is-option="true"
                    :is-icon="false"
                    :is-modal="false"
                    :productInfo="item"
                    @change="checkChange(item)"
                    :key="skey"
                  />
                </div>
              </li>
            </ul>
          </div>
          <hr class="dp-hr h6" />
        </section>
        </div>
      </div>
      <div v-else class="row list__empty-items" style="width: 100%; margin-left: 0px; height: 500px;">
        <div class="list__icon">
          <i class="empty-list-icon"></i>
        </div>
        <p class="empty-list__text">
          주문상품목록이 없습니다.
        </p>
      </div>
      <section class="select-footer-section">
        <div class="footer-section-btn btn-group row">
          <b-button
            class="dp-btn text-gray-800 login-btn__close col-6"
            variant="outline-gray-800 not-hover"
            block
            @click="$bvModal.hide('OrdProductPCModal')"
            >취소하기</b-button
          >
          <b-button
            class="dp-btn text-white login-btn__confirm col-6"
            variant="gray-800"
            @click="btnOrderSelect"
            >선택하기</b-button
          >
        </div>
      </section>

    </b-modal>
    <!-- // 주문상품선택 Modal -->
</template>

<style scoped>
 .select-product-body {
    height: 65vh;overflow: scroll;overflow-x: hidden;margin-bottom: 20px;
 }
</style>

<script>
export default {
    props:{
        param : Object,
        fnConfirm :  { type: Function },
    },
    mounted() {
      this.commonCode();
      this.onInit();
    },
    data() {
      return {
        skey: 0,                      //checkbox 선택키
        popMonthSelect: "1",          //선택된 개월
        popInquirySelect: "all",      //선택된 문의유형
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
        ],
      //* 주문상품선택 관련 시작 ////////////////////////////////////////
      orderList: [], //주문 번호 관련
      orderNumber: "", //선택했을 때 해당 주문번호 받아오기
      chkGoodsList: [], //선택한 상품들 리스트
      //* 주문상품선택 관련 끝 ////////////////////////////////////////
        
      }
    },
    methods: {
      /*********************************
       * 드롭박스 목록 공통코드 호출
       *********************************/
      commonCode() {
        this.$util.debug("commonCode....");
        //공통코드 목록 불러오기
        let cmclassArr = ['ORDSTATUS'];
        //*
        this.$http.post('/common/code/map/list', {cmclass : cmclassArr, isloading: false}).then(result =>{
          for(var i=0; i<result.data.ordstatus.length; i++){
            var temp = {};
            temp.label = result.data.ordstatus[i].codename;
            temp.value = result.data.ordstatus[i].cmcode;
            //문의유형 화면쪽
            this.popInquiryOption.push(temp);
          }
        })
        //*/
      },
      /*********************************
       * 화면 데이터 로딩
       *********************************/
      onInit(){
        this.$util.debug("onInit....");

        this.$util.debug("전달받은 param >>>>>>>>>>>>>>>>>>>>>>");
        this.$util.debug(this.param);
        let chkOrdno = "";
        let chkOrdList = [];
        if(!this.$util.isNull(this.param.chkordno)){
          chkOrdno = this.param.chkordno;
          this.$util.debug(chkOrdno);
        }
        if(!this.$util.isEmpty(this.param.chkgoodslist)){
          chkOrdList = this.param.chkgoodslist;
        }
        
        var serachParam = {
          lastmonth : this.popMonthSelect,
          ordstatus: this.popInquirySelect,

        }

        this.$http.post('/cscenter/searchorder',serachParam).then(result => {
          if(result.statusCode == 200){
            this.orderList = result.data.orderlist;
            this.$util.debug("Loading data >>>>>>>>>>>>>>>>>>>>>>>>");
            this.$util.debug(this.orderList);
            for(var i = 0; i < this.orderList.length; i++){
              for(var j=0; j < this.orderList[i].goods.length; j++){
                let list = this.orderList[i].goods[j];
                list.opthtml = list.optionname;
                list.chk = false;

                //주문 변경 시에 체크 여부 확인
                if(this.orderList[i].ordno == chkOrdno && !this.$util.isNull(chkOrdno)){
                  if(!this.$util.isEmpty(chkOrdList)){
                    for(var k = 0; k<chkOrdList.length; k++){
                      if(list.ordgdidx == chkOrdList[k].ordgdidx){
                        list.chk = true;
                      }
                    }
                  }
                }

                //상품 옵션명
                /*
                if(list[i].isaddgoods == 'F') {
                   list[i].optionname = '옵션 : ' + list[i].optionname;
                 } else {
                   list[i].optionname = '추가상품 : ' + list[i].optionname;
                 }

                 let optionnames = list[i].optionname.split('|');
                 list[i].opthtml = '';
                 if(optionnames.length > 0) {
                   for(let i = 0 ; i < optionnames.length ; i++) {
                     optionnames[i] = '<span>' + optionnames[i] + '</span>';
                   } 
                   list[i].opthtml = optionnames.join('<span class="dp-bar h10"></span>');  
                 }
                 //*/
              }
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
        this.onInit();

      },
      /*********************************
       * 문의 유형 선택 값 세팅
       *********************************/
      popInquiryCheck(val) {
        this.$util.debug("popInquiryCheck....");

        this.popInquirySelect = val;

        this.$util.debug("현재 선택....");
        this.$util.debug(this.popInquirySelect);
        this.onInit();
      },
      /*********************************
       * 체크박스 선택 이벤트
       *********************************/
      checkChange(item){
        this.$util.debug("checkChange....");
        this.$util.debug(item);
        //*
        for(let i = 0 ; i < this.orderList.length ; i++) {
          //주문번호가 다른 경우 체크 해제
          if(item.orderidx != this.orderList[i].orderidx) {
            for(let y = 0 ; y < this.orderList[i].goods.length ; y++) {
              this.orderList[i].goods[y].chk = false;
            }
          }else{
            //동일 주문번호 판매사가 다른 경우 체크
            for(var n = 0; n < this.orderList[i].goods.length; n++){
              if(this.orderList[i].goods[n].chk){
                if(item.dealerno != this.orderList[i].goods[n].dealerno){
                  this.orderList[i].goods[n].chk = false;
                }
              }
            }
          }
        }
        //*/
        this.skey = Date.now();
      },
      /*********************************
       * 선택하기 버튼 클릭
       *********************************/
      btnOrderSelect(){
        this.$util.debug("btnOrderSelect....");
        this.orderNumber = "";
        this.chkGoodsList = [];
        for(let i = 0 ; i < this.orderList.length ; i++) {
          for(let y = 0 ; y < this.orderList[i].goods.length ; y++) {
            if(this.orderList[i].goods[y].chk){
              this.orderNumber = this.orderList[i].ordno;

              this.chkGoodsList.push(this.orderList[i].goods[y]);
            }
          }
        }
        
        this.$util.debug(JSON.stringify(this.chkGoodsList));
        this.$util.debug(this.orderNumber);

        if(this.$util.isEmpty(this.chkGoodsList)){
          this.$util.debug("chkGoodsList is null......");
          this.$eventBus.$emit('alert', '알림', "문의하실 상품을 선택해주세요.");
          return;
        }
        //성공 후 모달 닫기
        var param = {
          orderno: this.orderNumber,
          goodslist: this.chkGoodsList,
        }
        this.$util.debug("선택된 체크박스 return 해줄 data >>>>>>>>>>>>>>>>>>>>>>>>");
        this.fnConfirm(param);
        this.$bvModal.hide('OrdProductPCModal');

      },
    },
};
</script>
