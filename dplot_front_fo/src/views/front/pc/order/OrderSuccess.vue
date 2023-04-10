<template>
  <main class="order-success pc-top-padding dplot_v2">
    <section class="container container-600">
      <header
        class="order-success-header d-flex align-items-center justify-content-center"
      >
        <h1 class="mb-0 dp-shop-title font-weight-700 text-black">주문완료</h1>
      </header>
      <div class="order-success-body">
        <!--  카드 결제 인 경우  -->
        <div
          v-if="orderInfo.paywaytype !== 'PWT002'"
          class="order-success-message-body d-flex flex-column align-items-center justify-content-center"
        >
          <p
            class="order-success-message dp-shop-title font-weight-500 text-black mb-0 pb-10"
          >
            주문이 완료되었어요!
          </p>
          <p class="dp-p text-black font-weight-400 mb-0">
            주문번호: {{orderInfo.ordno}}
          </p>
        </div>
        <!--  무종장 결제 인 경우  -->
        <div
          v-if="orderInfo.paywaytype === 'PWT002'"
          class="order-success-message-body message-body-bank d-flex flex-column align-items-center justify-content-center"
        >
          <p
            class="order-success-message dp-shop-title font-weight-400 text-black mb-0 pb-10"
          >
            입금완료 시 배송이 시작됩니다.
          </p>
          <p class="dp-p text-black font-weight-400 mb-0">
            주문번호: {{orderInfo.ordno}}
          </p>
        </div>
        <hr class="dp-hr h6 mb-0" />
        <template>
          <div class="dp-panel">
            <p class="order-success__title dp-title02 font-weight-500 text-black">
              결제정보
            </p>
            <ul class="order-success__pay list-style-none">
              <li class="order-success__pay__list" v-if="orderInfo.reservetotamt > 0">
                <div class="pay__list__title">
                  <p class="mb-0 pay__list__text text-gray-700 font-weight-400">
                    적립금
                  </p>
                </div>
                <div class="pay__list__title-text">
                  <p class="order-success__pay__list-text mb-0 pay__list__text text-gray-700 font-weight-400">
                    <span class="atten-new text-black font-weight-500" >{{$util.maskComma(orderInfo.reservetotamt)}}</span>원
                  </p>
                </div>
              </li>
              <li class="order-success__pay__list" v-if="orderInfo.empreservetotamt > 0">
                <div class="pay__list__title">
                  <p class="mb-0 pay__list__text text-gray-700 font-weight-400">
                    임직원적립금
                  </p>
                </div>
                <div class="pay__list__title-text">
                  <p class="order-success__pay__list-text mb-0 pay__list__text text-gray-700 font-weight-400">
                      <span class="atten-new text-black font-weight-500" >{{$util.maskComma(orderInfo.empreservetotamt)}}</span>원
                  </p>
                </div>
              </li>
              <li class="order-success__pay__list" v-if="orderInfo.paywaytype === 'PWT001' && orderInfo.rpaytotprice > 0">
                <div class="pay__list__title">
                  <p class="mb-0 pay__list__text text-gray-700 font-weight-400">
                    {{orderInfo.paywaytypename}}({{payInfo.cardcompany}}/{{payInfo.planmonth != null && payInfo.planmonth > 1 ? payInfo.planmonth +'개월 할부' : '일시불'}})
                  </p>
                </div>
                <div class="pay__list__title-text">
                  <p class="order-success__pay__list-text mb-0 pay__list__text text-gray-700 font-weight-400">
                    <span class="atten-new text-black font-weight-500" >{{$util.maskComma(orderInfo.rpaytotprice)}}</span >원
                  </p>
                </div>
              </li>
              <li class="order-success__pay__list" v-if="(orderInfo.paywaytype !== 'PWT001' && orderInfo.paywaytype !== 'PWT009' ) && orderInfo.rpaytotprice > 0">
                <div class="pay__list__title">
                  <p class="mb-0 pay__list__text text-gray-700 font-weight-400">
                    {{orderInfo.paywaytypename}}
                  </p>
                </div>
                <div class="pay__list__title-text">
                  <p class="order-success__pay__list-text mb-0 pay__list__text text-gray-700 font-weight-400">
                    <span class="atten-new text-black font-weight-500" >{{$util.maskComma(orderInfo.rpaytotprice)}}</span >원
                  </p>
                </div>
              </li>

              <li class="order-success__pay__list" v-if="orderInfo.paywaytype === 'PWT002'">
                <div class="pay__list__title">
                  <p class="mb-0 pay__list__text text-gray-700 font-weight-400">
                    입금계좌
                  </p>
                </div>
                <div class="pay__list__title-text">
                  <p class="order-success__pay__list-text mb-0 pay__list__text text-gray-700 font-weight-400 text-right">
                    {{payInfo.virbank}}은행 {{payInfo.accntnumber}}<br />
                    (예금주 : {{payInfo.vircusname}} )
                  </p>
                </div>
              </li>
              <li
                class="order-success__pay__list"
                v-if="orderInfo.paywaytype === 'PWT002'"
              >
                <div class="pay__list__title">
                  <p class="mb-0 pay__list__text text-gray-700 font-weight-400">
                    입금기한
                  </p>
                </div>
                <div class="pay__list__title-text">
                  <p class="order-success__pay__list-text mb-0 pay__list__text text-gray-700 font-weight-400" >
                    <span class="atten-new font-weight-400 text-primary">{{this.$util.getFormatDate2(payInfo.virdueday.substring(0,12))}}</span>까지
                  </p>
                </div>
              </li>
            </ul>
            <!-- <ul class="order-success__pay list-style-none">
              <li class="order-success__pay__list">
                <div class="pay__list__title">
                  <p class="mb-0 pay__list__text text-gray-700 font-weight-400">
                    결제금액
                  </p>
                </div>
                <div class="pay__list__title-text">
                  <p class="order-success__pay__list-text mb-0 pay__list__text text-gray-700 font-weight-400" >
                    <span class="atten-new text-black font-weight-500" >{{$util.maskComma(orderInfo.rpaytotprice)}}</span >원
                  </p>
                </div>
              </li>
              <li class="order-success__pay__list">
                <div class="pay__list__title">
                  <p class="mb-0 pay__list__text text-gray-700 font-weight-400">
                    결제수단
                  </p>
                </div>
                <div class="pay__list__title-text">
                  <p
                    v-if="orderInfo.paywaytype === 'PWT001'"
                    class="order-success__pay__list-text mb-0 pay__list__text text-gray-700 font-weight-400"
                  >
                    {{payInfo.cardcompany}}카드 ({{payInfo.planmonth != null && payInfo.planmonth > 1 ? payInfo.planmonth +'개월 할부' : '일시불'}})
                  </p>
                  <p
                    v-if="orderInfo.paywaytype !== 'PWT001'"
                    class="order-success__pay__list-text mb-0 pay__list__text text-gray-700 font-weight-400"
                  >
                    {{orderInfo.paywaytypename}}
                  </p>
                </div>
              </li>
              <li
                class="order-success__pay__list"
                v-if="orderInfo.paywaytype === 'PWT002'"
              >
                <div class="pay__list__title">
                  <p class="mb-0 pay__list__text text-gray-700 font-weight-400">
                    입금계좌
                  </p>
                </div>
                <div class="pay__list__title-text">
                  <p class="order-success__pay__list-text mb-0 pay__list__text text-gray-700 font-weight-400">
                    {{payInfo.virbank}}은행 {{payInfo.accntnumber}}<br />
                    (예금주 : {{payInfo.vircusname}} )
                  </p>
                </div>
              </li>
              <li
                class="order-success__pay__list"
                v-if="orderInfo.paywaytype === 'PWT002'"
              >
                <div class="pay__list__title">
                  <p class="mb-0 pay__list__text text-gray-700 font-weight-400">
                    입금기한
                  </p>
                </div>
                <div class="pay__list__title-text">
                  <p class="order-success__pay__list-text mb-0 pay__list__text text-gray-700 font-weight-400" >
                    <span class="atten-new font-weight-400 text-primary">{{this.$util.getFormatDate2(payInfo.virdueday.substring(0,12))}}</span>까지
                  </p>
                </div>
              </li>
            </ul> -->
          </div>
          <hr class="dp-hr" />
        </template>
        <div class="dp-panel">
          <p class="order-success__title dp-title02 font-weight-500 text-black">
            배송정보
          </p>
          <ul class="order-success__pay list-style-none">
            <li class="order-success__pay__list">
              <div class="pay__list__title">
                <p class="pay__list__text mb-0 pay__list__text text-gray-700 font-weight-400">
                  주문상품
                </p>
              </div>
              <div class="pay__list__title-text">
                <p class="order-success__pay__list-text mb-0 pay__list__text text-gray-700 font-weight-400">
                  {{goodsList[0].goodsname}} <font v-if="goodsList.length > 1">외 {{goodsList.length - 1}} 건 </font>
                </p>
              </div>
            </li>
            <li class="order-success__pay__list">
              <div class="pay__list__title">
                <p class="mb-0 pay__list__text text-gray-700 font-weight-400">
                  받는사람
                </p>
              </div>
              <div class="pay__list__title-text">
                <p
                  class="order-success__pay__list-text mb-0 pay__list__text text-gray-700 font-weight-400"
                >
                  {{orderInfo.rcvname}}
                </p>
              </div>
            </li>
            <li class="order-success__pay__list">
              <div class="pay__list__title">
                <p class="mb-0 pay__list__text text-gray-700 font-weight-400">
                  연락처
                </p>
              </div>
              <div class="pay__list__title-text">
                <p
                  class="order-success__pay__list-text mb-0 pay__list__text text-gray-700 font-weight-400"
                >
                  {{$util.maskTel(orderInfo.rcvtel1)}}
                </p>
              </div>
            </li>
            <li class="order-success__pay__list">
              <div class="pay__list__title">
                <p class="mb-0 pay__list__text text-gray-700 font-weight-400">
                  주소
                </p>
              </div>
              <div class="pay__list__title-text">
                <p
                  class="order-success__pay__list-text mb-0 pay__list__text text-gray-700 font-weight-400"
                >
                  {{orderInfo.rcvaddrroad}} {{orderInfo.rcvaddrdetail}}
                </p>
              </div>
            </li>
          </ul>
        </div>
        <hr class="dp-hr" />
        <div class="order-success__buttons dp-panel">
          <b-button
            class="dp-btn btn-mr10 to-main-btn btn-h48"
            variant="outline-gray-800"
            squared
            @click="$router.replace('/shop');"
          >
            <span class="pay__list__text">쇼핑 계속하기</span>
          </b-button>
          <b-button
            class="dp-btn text-white btn-h48"
            variant="gray-800"
            squared
            @click="goDetail"
          >
            <span class="pay__list__text">주문내역 보기</span>
          </b-button>
        </div>
      </div>
    </section>
  </main>
</template>

<script>
export default {
  data() {
    return {
      // Todo: 결제 수단에 따라 나타나는 조건값 수정 필요 종류
      // PayCard, PayBankTransfer
      isOrderSuccess : 'card',
      orderInfo : {},
      payInfo : {},
      goodsList: [{
        goodsname : ''
      }],
    };
  },
  mounted() {
    this.getOrderInfo();
  },
   methods : {
    getOrderInfo() {
      const param = {
        ordno : this.$route.params.ordno
      }
      this.$http.post('/order/detail', param).then(result => {
        if (result.statusCode == 200) {
          this.orderInfo = result.data.orderinfo;
          this.payInfo = result.data.payinfo;
          this.goodsList = result.data.ordergoods;

          // gtmDataLayer 데이터 작업
          let ecommerce = new Object();
          let items =  new Array();
          let value = new Object();
          let numcnt = 0;
          for (let i = 0; i < this.goodsList.length; i++) {
              let item1 = new Object();
              item1.item_id = this.goodsList[i].goodscode;
              item1.id = this.goodsList[i].goodscode;
              item1.item_name = this.goodsList[i].goodsname;
              item1.price = this.goodsList[i].realgoodsamt;
              item1.quantity = this.goodsList[i].ordcnt;
              numcnt += this.goodsList[i].ordcnt;
              item1.item_brand = this.goodsList[i].brandname;
              item1.item_category = this.goodsList[i].fullcategoryname;
              item1.item_variant = this.goodsList[i].fulloptionnm;
              items.push(item1);
          }
          ecommerce.transaction_id = this.orderInfo.ordno;
          ecommerce.value = this.orderInfo.rpaytotprice;
          ecommerce.shipping = this.orderInfo.dadadelivamt + this.orderInfo.ptndelivamt;
          ecommerce.first_order = this.orderInfo.isfrstorder;
          ecommerce.items = items;
          ecommerce.num_items = this.goodsList.length;
          ecommerce.num_quantity = numcnt;
          this.$front.gtmDataLayer('purchase', ecommerce);
        } else {
          this.$eventBus.$emit('alert', '알림', result.message, ()=>{
            this.$router.replace('/shop');
          });
        }
      });
    },
    goDetail() {
      if(this.$store.state.isLogin) {
        this.$router.replace({name : 'mypage-order-detail', params : {ordno : this.orderInfo.ordno}});
      } else {
        this.$router.replace({name : 'nonemember-order-detail', params : {ordno : this.orderInfo.ordno}});
      }
    }
  }
};
</script>

<style scoped>
</style>