<template>
  <main class="mypage-order-detail">
    <div class="container">
      <div class="order-detail__header dp-mb-20">
        <div class="d-flex align-items-center dp-mb-20">
          <span class="order-detail__date atten-new">{{$util.getFormatDate(orderInfo.orderdate, '.')}}</span>
          <span class="dp-bar h10"></span>
          <span class="order-detail__number atten-new">{{orderInfo.ordno}}</span>
        </div>
        <div class="btn-group detail-header-btn" v-if="ordstatus != 'ODS002'">
          <b-button
            class="dp-btn btn-h32"
            variant="outline-gray-400"
            squared
            @click="openTransaction"
          >
            <span class="text-gray-800">거래명세서</span>
          </b-button>
          <template v-if="possbleAddrChange">
            <b-button class="dp-btn btn-h32" variant="outline-gray-400" squared @click="allCancel">
              <span class="text-gray-800">전체주문취소</span>
            </b-button>
          </template>
        </div>
      </div>
      <hr class="dp-hr justify-margin" />
      <div class="order-detail__body">
        <!-- 브랜드별 배송상품 -->
        <template v-for="(dealer, index) in dealers">
          <div class="order-exchange__section" :key="'delear_'+index">
            <div class="brand-name">
              <span class="atten-new">{{dealer.dealernm}}</span> 배송상품
            </div>
            <template v-for="(item, index2) in dealer.items">
              <div :key="'item_' + index2" class="order-item">
                <product-order
                  :product-info="item"
                  :is-badge="false"
                  :key="index2"
                  @click="handleFooterButton(item, ...arguments)"
                  />
              </div>
              <div class="exchange-status" :key="'claim_' + index2" v-if="item.claim.length > 0">
                <ul class="detail-add">
                  <li></li>
                </ul>
                <ul class="list-style-none exchange-status__ul">
                  <li v-for="(claim, index3) in item.claim" :key="index3">
                    <product-order
                      :is-thumbnail="false"
                      :product-info="claim"
                      :is-badge="false"
                      @click="handleFooterButton(claim, ...arguments)"
                    />
                  </li>
                </ul>
              </div>
            </template>
          </div>
          <hr class="dp-hr justify-margin" :key="'line_'+index"/>
        </template>
        <!-- // 브랜드별 배송상품 -->
        
        <!-- 비회원일 때 뜨는 주문자 정보 -->
        <template>
          <div class="order-detail__section padding-top-20">
            <div class="section-title">
              <h2 class="mb-0">주문자 정보</h2>
            </div>
            <div class="section-info">
              <ul class="list-style-none section-info__ul">
                <li>
                  <ul class="list-style-none d-flex">
                    <li>주문자</li>
                    <li>{{orderInfo.ordname}}</li>
                  </ul>
                </li>
                <li>
                  <ul class="list-style-none d-flex">
                    <li>연락처</li>
                    <li>{{$util.maskTel(orderInfo.ordtel)}}</li>
                  </ul>
                </li>
                <li>
                  <ul class="list-style-none d-flex">
                    <li>이메일</li>
                    <li>{{$util.maskTel(orderInfo.ordemail)}}</li>
                  </ul>
                </li>
              </ul>
            </div>
          </div>
          <hr class="dp-hr justify-margin" />
        </template>
        <!-- // 비회원일 때 뜨는 주문자 정보 -->

        <!-- 배송정보 -->
        <div class="order-detail__section padding-top-20">
          <div
            class="section-title d-flex justify-content-between align-items-center"
          >
            <h2 class="mb-0">배송정보</h2>
            <div class="delivery-change__btn">
              <b-button
                v-if="possbleAddrChange"
                class="dp-btn dp-btn-icon not-hover btn-h32"
                variant="outline-gray-400"
                pill
                @click="changeAddr"
              >
                <span class="text-gray-800">배송지 변경</span>
                <i class="dp-icon icon-pick-black sm01"></i>
              </b-button>
            </div>
          </div>
          <div class="section-info">
            <ul class="list-style-none section-info__ul">
              <li>
                <ul class="list-style-none d-flex">
                  <li>받는사람</li>
                  <li>{{orderInfo.rcvname}}</li>
                </ul>
              </li>
              <li>
                <ul class="list-style-none d-flex">
                  <li>연락처</li>
                  <li>{{$util.maskTel(orderInfo.rcvtel1)}}</li>
                </ul>
              </li>
              <li>
                <ul class="list-style-none d-flex">
                  <li>주소</li>
                  <li>{{orderInfo.rcvaddrroad}} {{orderInfo.rcvaddrdetailroad}}</li>
                </ul>
              </li>
              <li>
                <ul class="list-style-none d-flex">
                  <li>배송요청사항</li>
                  <li v-if="orderInfo.rcvreqtype != 'RVT006'">{{orderInfo.rcvreqtypenm}}</li>
                  <li v-else>{{orderInfo.rcvreqdetail}}</li>
                </ul>
              </li>
            </ul>
          </div>
        </div>
        <hr class="dp-hr justify-margin" />
        <!-- // 배송정보 -->

        <!-- 주문금액 정보 -->
        <div class="order-detail__section padding-top-20">
          <div class="section-title">
            <h2 class="mb-0">주문금액 정보</h2>
          </div>
          <div class="section-info">
            <ul class="list-style-none dp-price-info">
              <li>
                <span class="price-info__key">총 주문금액</span>
                <span class="price-info__value">{{$util.maskComma(orderInfo.ordtotprice)}}<span class="price-info__unit">원</span></span>
              </li>
              <li>
                <span class="price-info__key"
                  >배송비
                  <b-button
                    class="dp-btn dp-btn-icon not-hover btn-h30 order-detail__button"
                    variant="outline-gray-800"
                    pill
                    @click="delivDetail"
                    v-if="orderInfo.dadadelivamt + orderInfo.ptndelivamt > 0"
                  >
                    <span class="text-gray-800">배송비 상세</span>
                    <i class="dp-icon icon-arrow-right sm"></i> </b-button
                ></span>
                <span class="price-info__value" v-if="orderInfo.dadadelivamt + orderInfo.ptndelivamt > 0">{{$util.maskComma(orderInfo.dadadelivamt + orderInfo.ptndelivamt)}}<span class="price-info__unit">원</span></span>
                <span class="price-info__value" v-else><span class="price-info__unit">무료배송</span></span>
              </li>
            </ul>
          </div>
          <div class="total-price">
            <p class="mb-0">총 결제금액</p>
            <p class="mb-0">
              <span class="atten-new" v-if="ordstatus != 'ODS002'">{{$util.maskComma(orderInfo.paytotprice)}}</span>
              <span class="atten-new" v-else>0</span>
              원
            </p>
          </div>
        </div>
        <hr class="dp-hr justify-margin" />
        <!-- // 주문금액 정보 -->

        <!-- 환불/추가결제정보 -->
        <template v-if="claimList.length > 0">
          <div class="order-detail__section padding-top-20">
            <div class="section-title d-flex justify-content-between align-items-center">
              <h2 class="mb-0">환불/추가결제정보</h2>
            </div>
            <div class="section-info">
              <template v-for="(claim, index) in claimList">
                <div class="section-info__title" :key="'title'+index">
                  <p class="section-info__p mb-0">{{$util.getFormatDate(claim.clmreqdate, '.')}} {{claim.clmtypenm}}신청</p>
                  <p class="mb-0 d-flex align-items-center" role="button" @click="goClaimDetail(claim)">
                    <span class="section-info__span">상세내역</span><i class="dp-icon icon-arrow-right"></i>
                  </p>
                </div>
                <ul class="list-style-none dp-price-info li-mt-10" :key="'content'+index">
                  <li v-if="claim.rtnpayamt > 0">
                    <span class="price-info__key" v-if="orderInfo.paywaytype == 'PWT001'">
                      {{orderInfo.paywaytypename}}({{payInfo.cardcompany}})
                    </span>
                    <span class="price-info__key" v-else>{{orderInfo.paywaytypename}}</span>
                    <span class="price-info__value">{{$util.maskComma(claim.rtnpayamt)}}<span class="price-info__unit">원</span></span>
                  </li>
                  <li v-if="claim.addrpaytotprice > 0">
                    <span class="price-info__key" v-if="claim.addpaywaytype == 'PWT001'">추가결제 신용카드({{claim.cardcompany}})</span>
                    <span class="price-info__key" v-else>{{claim.addpaywaytypenm}}</span>
                    <span class="price-info__value">{{$util.maskComma(claim.addrpaytotprice)}}<span class="price-info__unit">원</span></span>
                  </li>
                </ul>
              </template>
            </div>
          </div>
          <hr class="dp-hr justify-margin" />
        </template>
        <!-- // 환불/추가결제정보 -->

        <!-- 결제정보 -->
        <template  v-if="ordstatus != 'ODS002'">
          <div class="order-detail__section padding-top-20">
            <div class="section-title d-flex justify-content-between align-items-center">
              <h2 class="mb-0">결제정보</h2>
              <div class="receipt__btn" v-if="payInfo.receipturl != null">
                <b-button
                  class="dp-btn dp-btn-icon not-hover btn-h32"
                  variant="outline-gray-400"
                  squared
                  @click="goReceipt"
                >
                  <span class="text-gray-800">구매영수증</span>
                </b-button>
              </div>
            </div>
            <div class="section-info">
              <ul class="list-style-none dp-price-info li-mt-10">
                <li>
                  <span class="price-info__key" v-if="orderInfo.paywaytype == 'PWT001'">
                    {{payInfo.cardcompany}}카드({{payInfo.planmonth != null && payInfo.planmonth > 1 ? payInfo.planmonth +'개월 할부' : '일시불'}})
                  </span>
                  <span class="price-info__key" v-if="orderInfo.paywaytype == 'PWT002'" style="display:block">
                    {{payInfo.virbank}}은행 {{payInfo.accntnumber}} (예금주 : {{payInfo.vircusname}})<br />
                    <div class="d-flex align-items-center">
                      <span class="dp-p-sm font-weight-400 text-gray-700">입금기한</span>
                      <p class="mb-0 dp-p-sm text-gray-700 font-weight-500 text-black">
                        <span class="atten-new font-weight-500 text-primary" style="padding-left:10px;">{{$util.getFormatDate2(payInfo.virdueday.substring(0,12),'yyyy.MM.dd HH:mm')}}</span> 까지
                      </p>
                    </div>
                    <div class="d-flex align-items-center" v-if="!$util.isBlank(orderInfo.paymentdate)">>
                      <span class="dp-p-sm font-weight-400 text-gray-700">입금완료</span>
                        <span class="atten-new font-weight-500" style="padding-left:10px;">{{$util.getFormatDate2(orderInfo.paymentdate.substring(0,8),'yyyy.MM.dd')}}</span>
                    </div>
                  </span>
                  <span class="price-info__key" v-if="orderInfo.paywaytype != 'PWT001' && orderInfo.paywaytype != 'PWT002'">{{orderInfo.paywaytypename}}</span>
                  <span class="price-info__value">{{$util.maskComma(payInfo.rpaytotprice)}}<span class="price-info__unit">원</span></span>
                </li>
              </ul>
            </div>
          </div>
          <hr class="dp-hr justify-margin" />
        </template>
        <!-- // 결제정보 -->

        <!-- 현금영수증 신청 -->
        <template v-if="orderInfo.cashreceiptkey != null && ordstatus != 'ODS002'">
          <div class="order-detail__section padding-top-20">
            <div class="section-title">
              <h2 class="mb-0">현금영수증 신청</h2>
            </div>
            <div class="section-info">
              <ul class="list-style-none dp-price-info">
                <li>
                  <span class="price-info__key">현금영수증</span>
                  <span class="price-info__unit">신청완료({{orderInfo.cashreceipttype}})</span>
                </li>
              </ul>
            </div>
          </div>
          <hr class="dp-hr justify-margin" />
        </template>
        <!-- // 현금영수증 신청 -->
      </div>
    </div>
  </main>
</template>

<script src="@views.mobile/nonemember/shop/order/Detail.js">
</script>

<style scoped>
.order-item:not(:first-child) {
  margin-top:30px;
}
</style>