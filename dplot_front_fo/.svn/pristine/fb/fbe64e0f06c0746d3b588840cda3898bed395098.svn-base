<template>
    <!-- 배송비 상세 모달 -->
    <b-modal
      id="deliveryModal"
      :modal-class="{'dp-modal pop-over detail-shipping-modal': $store.state.platform == 'MAC001', 
                    'dp-modal page-layer detail-shipping-modal': $store.state.platform != 'MAC001'}"
      scrollable
      :hide-footer="true"
      centered
    >
      <!-- Page Modal Header -->
      <template #modal-header="{ cancel }">
        <h5 class="modal-title">배송비 상세</h5>
        <i class="modal-close" @click="cancel()">
          <img
            src="@assets.mobile/img/icon/icon-close-black-20px.svg"
            alt="닫기"
          />
        </i>
      </template>

      <!-- Page Modal Body -->
      <div class="page-layer__body detail-shipping-modal__body">
        <template v-for="(deliv, idx) in list">
          <template v-if="$store.state.platform == 'MAC001'">
            <div class="seller-name-area" :key="idx +  '_0'">
              <p class="mb-0 dp-caption seller-name">{{deliv.dealernm}} <span class="text-black">배송상품</span></p>
            </div>
            <section class="bundle-shipping dp-section" :key="idx + '_1'" v-if="deliv.combdeliv.length > 0">
              <div class="bundle-shipping-area">
                <div class="shipping__header">
                  <p class="shipping-product__title mb-0 dp-p-sm">묶음배송 상품</p>
                  <div>
                    <span class="shipping__price mb-0 atten-new">{{$util.maskComma(deliv.combdelivamt)}}</span>
                    <span class="dp-p-sm price-unit">원</span>
                  </div>
                </div>
                <hr class="dp-hr h1" />
                <div class="bundle-shipping__body">
                  <div class="shipping-product__area" v-for="(item, idx2) in deliv.combdeliv" :key="idx2 + '_a'">
                    <p class="product-name dp-p-sm mb-0">
                      {{$util.isBlank(item.brandname) ? '' : '[' + item.brandname + ']'}} {{item.goodsname}}
                    </p>
                    <ul class="detail-add">
                      <li>
                        <span>기본배송비 <font v-if="item.delivfaretype == 'DCT003'">({{$util.maskComma(item.delivfreefare)}}원 이상 무료배송)</font>
                        </span><span class="atten-new">{{$util.maskComma(item.basedelivamt)}}<span class="price-unit dp-caption">원</span></span>
                      </li>
                      <li v-if="isolatetype != 'N'">
                        <span v-if="isolatetype == 'I'">도서산간 추가 배송비</span>
                        <span v-if="isolatetype == 'J'">제주지역 추가 배송비</span>
                        <span class="atten-new">{{$util.maskComma(item.isodelivamt)}}<span class="price-unit dp-caption">원</span></span>
                      </li>
                    </ul>
                  </div>
                </div>
              </div>
            </section>
            <section class="individual-shipping dp-section" :key="idx + '_2'" v-if="deliv.eachdeliv.length > 0">
              <div class="individual-shipping-area">
                <div class="shipping__header">
                  <p class="shipping-product__title mb-0 dp-p-sm">개별배송 상품</p>
                  <div>
                    <span class="shipping__price mb-0 atten-new">{{$util.maskComma(deliv.eachdelivamt)}}</span>
                    <span class="dp-p-sm price-unit">원</span>
                  </div>
                </div>
                <hr class="dp-hr h1" />
                <div class="individual-shipping__body">
                  <div>
                    <div class="shipping-product__area"  v-for="(item, idx2) in deliv.eachdeliv" :key="idx2 + '_b'">
                      <p class="product-name dp-p-sm mb-0">
                        {{$util.isBlank(item.brandname) ? '' : '[' + item.brandname + ']'}} {{item.goodsname}}
                      </p>
                      <ul class="detail-add">
                        <li>
                          <span>기본배송비 <font v-if="item.delivfaretype == 'DCT003'">({{$util.maskComma(item.delivfreefare)}}원 이상 무료배송)</font>
                          </span><span class="atten-new">{{$util.maskComma(item.basedelivamt)}}<span class="price-unit dp-caption">원</span></span>
                        </li>
                        <li v-if="isolatetype != 'N'">
                          <span v-if="isolatetype == 'I'">도서산간 추가 배송비</span>
                          <span v-if="isolatetype == 'J'">제주지역 추가 배송비</span>
                          <span class="atten-new">{{$util.maskComma(item.isodelivamt)}}<span class="price-unit dp-caption">원</span></span>
                        </li>
                      </ul>
                    </div>
                  </div>
                </div>
              </div>
            </section>
          </template>
          <template v-else>
            <div class="seller-name-area" :key="idx +  '_0'">
              <p class="mb-0 dp-caption seller-name">{{deliv.dealernm}} 배송상품</p>
            </div>
            <section class="bundle-shipping dp-section" :key="idx + '_1'" v-if="deliv.combdeliv.length > 0">
              <div class="bundle-shipping-area">
                <div class="shipping__header d-flex justify-content-between">
                  <p class="shipping-product__title mb-0 dp-p-sm">묶음배송 상품</p>
                  <div>
                    <span class="shipping__price mb-0 atten-new dp-price01">{{$util.maskComma(deliv.delivamt)}}</span>
                    <span class="dp-p-sm price-unit">원</span>
                  </div>
                </div>
                <hr class="dp-hr h1" />
                <div class="bundle-shipping__body">
                  <div class="shipping-product__area" v-for="(item, idx2) in deliv.combdeliv" :key="idx2 + '_a'">
                    <p class="product-name dp-p-sm mb-0">
                      {{$util.isBlank(item.brandname) ? '' : '[' + item.brandname + ']'}} {{item.goodsname}}
                    </p>
                    <ul class="detail-add">
                      <li>
                        <span>기본배송비 <font v-if="item.delivfaretype == 'DCT003'">({{$util.maskComma(item.delivfreefare)}}원 이상 무료배송)</font>
                        </span><span class="atten-new dp-price02">{{$util.maskComma(item.basedelivamt)}}<span class="price-unit dp-caption">원</span></span>
                      </li>
                      <li v-if="isolatetype != 'N'">
                        <span v-if="isolatetype == 'I'">도서산간 추가 배송비</span>
                        <span v-if="isolatetype == 'J'">제주지역 추가 배송비</span>
                        <span class="atten-new dp-price02">{{$util.maskComma(item.isodelivamt)}}<span class="price-unit dp-caption">원</span></span>
                      </li>
                    </ul>
                  </div>
                </div>
              </div>
            </section>
            <section class="individual-shipping dp-section" :key="idx + '_2'" v-if="deliv.eachdeliv.length > 0">
              <div class="individual-shipping-area">
                <div class="shipping__header d-flex justify-content-between">
                  <p class="shipping-product__title mb-0 dp-p-sm">개별배송 상품</p>
                  <div>
                    <span class="shipping__price mb-0 atten-new dp-price01">{{$util.maskComma(deliv.eachdelivamt)}}</span>
                    <span class="dp-p-sm price-unit">원</span>
                  </div>
                </div>
                <hr class="dp-hr h1" />
                <div class="individual-shipping__body">
                  <div>
                    <div class="shipping-product__area"  v-for="(item, idx2) in deliv.eachdeliv" :key="idx2 + '_b'">
                      <p class="product-name dp-p-sm mb-0">
                        {{$util.isBlank(item.brandname) ? '' : '[' + item.brandname + ']'}} {{item.goodsname}}
                      </p>
                      <ul class="detail-add">
                        <li>
                          <span>기본배송비 <font v-if="item.delivfaretype == 'DCT003'">({{$util.maskComma(item.delivfreefare)}}원 이상 무료배송)</font>
                          </span><span class="atten-new dp-price02">{{$util.maskComma(item.basedelivamt)}}<span class="price-unit dp-caption">원</span></span>
                        </li>
                        <li v-if="isolatetype != 'N'">
                          <span v-if="isolatetype == 'I'">도서산간 추가 배송비</span>
                          <span v-if="isolatetype == 'J'">제주지역 추가 배송비</span>
                          <span class="atten-new dp-price02">{{$util.maskComma(item.isodelivamt)}}<span class="price-unit dp-caption">원</span></span>
                        </li>
                      </ul>
                    </div>
                  </div>
                </div>
              </div>
            </section>
          </template>
          <hr class="dp-hr h1" :key="idx + '_3'">
          <br :key="idx + '_4'"/>
        </template>
        <div class="total-shipping-price__area d-flex justify-content-between">
          <p class="mb-0 dp-p total-shipping-title">배송비 총합</p>
          <div>
            <span class="total-shipping-price atten-new dp-title">
              {{$util.maskComma(totAmt.dadadelivamt + totAmt.ptndelivamt)}}
            </span>
            <span class="dp-p-sm">원</span>
          </div>
        </div>
        <div class="confirm-btn-area">
          <b-button class="dp-btn text-white" variant="gray-800" squared @click="close">
            <span>확인</span>
          </b-button>
        </div>
      </div>
    </b-modal>
    <!-- // 배송비 상세 모달 -->
</template>

<script>
export default {
    data() {
        return {
            list : [],
            dealers : [],
            isolatetype : 'N',
            totAmt : {
              dadadelivamt : 0,
              ptndelivamt : 0
            }        
        }
    },
    props : {
      param : Object
    },
    mounted(){
      this.dealers = this.param.dealers;
      this.totAmt = this.param.totAmt;
      this.isolatetype = this.param.isolatetype;
      this.delivList();
    },
    methods: {
      delivList() {
        for(let i = 0 ; i < this.dealers.length ; i++) {
          this.setDeliv(this.dealers[i], this.dealers[i].items);
        }
      },
      setDeliv(dealer, items) {
        let temp = {
          dealerno : dealer.dealerno,
          dealernm : dealer.dealernm,
          combdeliv : [],
          combdelivamt : 0,
          eachdeliv : [],
          eachdelivamt : 0
        }
        
        for(let i = 0 ; i < items.length ; i++) {
          if(items[i].iscombdeliv == 'T') {
            temp.combdeliv.push(items[i]);
            temp.combdelivamt = temp.combdelivamt + items[i].delivamt + items[i].adddelivamt;
          } else {
            temp.eachdeliv.push(items[i]);
            temp.eachdelivamt = temp.eachdelivamt + items[i].delivamt + items[i].adddelivamt;
          }
        }

        this.list.push(temp);
      },
      close() {
        this.$bvModal.hide('deliveryModal');
      }
    },
}
</script>
<style>
</style>