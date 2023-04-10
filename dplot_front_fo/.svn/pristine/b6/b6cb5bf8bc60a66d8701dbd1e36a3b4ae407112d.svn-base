<template>
    <!-- 할인/쿠폰 적용 모달 -->
    <b-modal
      id="couponApplyModal"
      :modal-class="{'dp-modal pop-over coupon-apply-pc-modal': $store.state.platform == 'MAC001', 
                    'dp-modal page-layer coupon-apply-modal': $store.state.platform != 'MAC001'}"
      scrollable
      centered
      :hide-footer="false"
    >
      <!-- Page Modal Header -->
      <template #modal-header="{ cancel }">
        <h5 class="modal-title">할인/쿠폰 적용</h5>
        <i class="modal-close" @click="cancel()">
          <img
            src="@assets.mobile/img/icon/icon-close-black-20px.svg"
            alt="닫기"
          />
        </i>
      </template>

      <!-- Page Modal Body -->
      <div class="page-layer__body">
        <template v-for="(items, index) in list">
          <section class="dp-section" :key="'s1'+index" :class="{'mt-0': index == 0, 'pt-0' : index > 1}">
            <!-- 상품 정보 -->
            <!-- <div class="add-discount-area">
              <div class="add-discount__header d-flex justify-content-between">
                <p class="dp-p mb-0 add-discount-title">상품할인쿠폰</p>
              </div>
            </div> -->
            <!-- PC상품정보 -->
            <div class="discount__product-info__area d-flex align-items-center" v-if="$store.state.platform == 'MAC001'">
              <div class="product-thumbnail-area">
                <product-thumbnail 
                  :thumbnail-info="{
                    id:items.goodsno,
                    fullpath:items.fullpath
                  }"
                  style="width: 150px" />
              </div>
              <div class="product-info__area">
                <div class="seller-name atten-new">{{items.brandname}}</div>
                <div class="product-name">{{items.goodsname}}</div>
                <div class="product-name"> 옵션 : {{items.fulloptionnm}}</div>
              </div>
              <div class="product-price__area">
                <div class="discount-price-area">
                  <span class="atten-new">{{$util.maskComma(items.price * items.ordcnt)}}</span>
                  <span class="price-unit">원</span>
                </div>
                <div class="current-price-area">
                  <span class="current-price" v-if="(items.price * items.ordcnt) < items.realmarketprice">{{$util.maskComma(items.realmarketprice)}}원</span>
                </div>
              </div>
              <div class="product-count-area"><span>{{items.ordcnt}}개</span></div>
            </div>
            <!-- 모바일 상품정보 -->
            <product-order v-else
              :is-status="false"
              :is-badge="false"
              :is-order="true"
              :product-info="items"
              :is-footer="false"
            />
            <applied-discount
              :item="items"
              :couponList="couponList"
              :is-employ="param.isemp"
              @change="couponChange"
            />
          </section>
          <hr class="dp-hr dp-hr-h6 justify-margin" :key="'f1'+index"/>
        </template>
        <section class="dp-section">
          <div class="add-discount-area">
            <div class="add-discount__header d-flex justify-content-between">
              <p class="dp-p mb-0 add-discount-title">추가할인쿠폰</p>
              <p class="atten-new dp-title mb-0">
                {{$util.maskComma($util.maskFloor(getCartCouponAmt))}}<span class="price-unit dp-p-sm">원</span>
              </p>
            </div>

            <form>
              <div class="coupon-apply-drop">
                <base-select
                  v-model="cartCouponIdx"
                  :options="cartCouponList"
                  placeholder="쿠폰을 선택해주세요"
                />
              </div>
            </form>
          </div>
        </section>
        <hr class="dp-hr dp-hr-h6 justify-margin" />
        <section class="dp-section">
          <div class="shipping-coupon-area">
            <div class="shipping-coupon__header d-flex justify-content-between">
              <p class="dp-p mb-0 shipping-coupon-title">배송비쿠폰</p>
              <p class="atten-new dp-title mb-0">
                {{$util.maskComma($util.maskFloor(getDelivCouponAmt))}}<span class="price-unit dp-p-sm">원</span>
              </p>
            </div>

            <form>
              <div class="coupon-apply-drop">
                <div class="form-group">
                  <base-select
                    v-model="delivCouponIdx"
                    :options="delivCouponList"
                    placeholder="쿠폰을 선택해주세요"
                    @change="delivCouponChange"
                  />
                </div>
              </div>
              <div class="coupon-apply-drop" v-if="delivCouponIdx > 0">
                <base-select
                  v-model="goodsDelivCouponIdx"
                  :options="delivCpnGoodsList"
                  placeholder="배송비 쿠폰 적용상품 선택"
                />
              </div>
            </form>
          </div>
        </section>
      </div>

      <!-- page modal footer -->
      <template #modal-footer="{ cancel }">
        <div class="btn-group">
          <b-button
            class="dp-btn not-hover squared"
            variant="white"
            @click="cancel()"
            >취소</b-button
          >
          <b-button
            class="dp-btn text-white apply-btn"
            variant="gray-800 btn-h44"
            style="margin-left:0px"
            @click="apply"
            >쿠폰적용</b-button
          >
        </div>
      </template>
    </b-modal>
    <!-- // 할인/쿠폰 적용 모달 -->
</template>

<script>
export default {
    name : 'CouponChange',
    data() {
        return {
          skey : 0,
          dealers : null,
          list : [],
          couponList : [],
          cartCouponIdx : '',
          cartCouponList : [],
          delivCouponIdx : '',
          goodsDelivCouponIdx : '',
          delivCouponList : [],
          delivCpnGoodsList : [],
          delivCpnAmt : 0,
        };
    },
    props : {
        param : Object,
        fnConfirm : Function
    },
    mounted(){
      this.getOrderList();
      this.couponList = this.param.couponList;
      this.cartCouponList = this.param.cartCouponList;
      this.delivCouponList = this.param.delivCouponList;
      this.cartCouponIdx = this.param.cartCouponIdx;
      this.delivCouponIdx = this.param.delivCouponIdx;
      if (!this.$util.isBlank(this.delivCouponIdx) && this.delivCouponIdx !== "emptyCoupon"  && this.delivCouponIdx !== "noCoupon") {
        this.delivCouponChange();
        this.goodsDelivCouponIdx = this.param.goodDelivCouponIdx;
      }
    },
    methods: {
      getOrderList() {
        this.dealers = this.$util.deepClone(this.param.dealers);

        for(let i = 0 ; i < this.dealers.length ; i++ ) {
          for(let j = 0 ; j < this.dealers[i].items.length ; j++) {
            this.list.push(this.dealers[i].items[j]);
          }
        }
      },
      delivCouponChange() {
        if (this.$util.isBlank(this.delivCouponIdx) || this.delivCouponIdx === "emptyCoupon" || this.delivCouponIdx === "noCoupon") {
          //this.delivCoupon = '';
          this.goodsDelivCouponIdx = '';
          this.delivCpnGoodsList = [];
        }

        for(let i = 1 ; i < this.delivCouponList.length ; i++) {
          if(this.delivCouponIdx == this.delivCouponList[i].cpnmisidx) {
            this.delivCpnGoodsList = this.delivCouponList[i].detail;
            this.goodsDelivCouponIdx = this.delivCouponList[i].detail[0].value;
            break;
          }
        }
      },
      couponChange() {
        this.$front.setGoodsCouponList(this.list, this.couponList);
      },
      apply() {
        let cartCoupon = null;
        let delivCoupon = null;
        if(this.cartCouponIdx > 0) {
          for(let i = 1 ; i < this.cartCouponList.length ; i++) {
            if(this.cartCouponIdx == this.cartCouponList[i].cpnmisidx) {
              cartCoupon =  this.cartCouponList[i];
              break;
            }
          }
        }
        
        if (this.goodsDelivCouponIdx > 0) {
          for(let i = 1 ; i < this.delivCpnGoodsList.length ; i++) {
            if(this.goodsDelivCouponIdx == this.delivCpnGoodsList[i].cpnmisidx) {
              delivCoupon = this.delivCpnGoodsList[i];
              break;
            }
          }
        }
        
        this.fnConfirm(this.dealers, cartCoupon, delivCoupon, this.cartCouponIdx, this.delivCouponIdx, this.goodsDelivCouponIdx);
        this.$bvModal.hide('couponApplyModal');
      }
    },
    computed : {
      getCartCouponAmt() {
        let value = 0;
        if(this.cartCouponList.length <= 1) {
          return value;
        }
        
        for(let i = 1 ; i < this.cartCouponList.length ; i++) {
          if(this.cartCouponIdx == this.cartCouponList[i].cpnmisidx) {
            value =  this.cartCouponList[i].discountamt;
            break;
          }
        }
        return value;
      },
      getDelivCouponAmt() {
        let value = 0;
        if(this.delivCouponList.length <= 1) {
          return value;
        }
        for(let i = 1 ; i < this.delivCpnGoodsList.length ; i++) {
          if(this.goodsDelivCouponIdx == this.delivCpnGoodsList[i].cpnmisidx) {
            value = this.delivCpnGoodsList[i].discountamt;
            break;
          }
        }
        return value;
      }
    }
}
</script>