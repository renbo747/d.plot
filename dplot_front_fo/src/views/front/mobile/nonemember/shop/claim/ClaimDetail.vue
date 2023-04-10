<template>
  <main class="dp-mypage-cancel-detail">
    <div class="container">
      <!-- 신청일 주문번호-->
      <section class="cancel-detail__header">
        <div class="header-container">
          <span class="dp-p-sm font-weight-500">{{claimName}}신청</span>
          <div class="number-container">
            <span class="apply-date">{{$util.getFormatDate(claimInfo.clmreqdate, '.')}}</span>
            <span class="dp-bar h10"></span>
            <span class="apply-number">{{claimInfo.ordno}}</span>
          </div>
        </div>
      </section>
      <hr class="dp-hr justify-margin" />

      <!-- 취소승인대기 상품-->
      <template v-for="(dealer, index) in dealers">
        <section :key="'item_'+index">
          <div class="cancel-status__area">
            <div class="cancel-status-product__header">
              <p class="mb-0 cancel-product__header__title">
                {{dealer.dealernm}} 배송상품
              </p>
            </div>
            <hr class="dp-hr h1" />
            <div class="cancel-status__product-info__area" v-for="(item, index2) in dealer.items" :key="index2">
              <product-order
                :product-info="item"
                :is-badge="false"
                @click="handleFooterButton(item, ...arguments)"
                />
            </div>
            <div class="status-message__area">
              <!-- <p class="mb-0 status-message dp-p-sm" v-if="item.clmstatus == 'CNS001' || item.clmstatus == 'RTS001' || item.clmstatus == 'EXS001'">
                주문{{claimName}} 요청을 확인하는 중이에요
              </p>
              <p class="mb-0 status-message dp-p-sm" v-if="item.clmstatus == 'CNS002' || item.clmstatus == 'RTS002' || item.clmstatus == 'EXS002'">
                추가결제가 완료되면 {{claimName}}신청이 완료됩니다.
              </p> -->
            </div>
          </div>
        </section>
        <hr class="dp-hr justify-margin" :key="'line_'+index"/>
      </template>
      
      <!-- 교환 옵션-->
      <template v-if="claimInfo.clmtype == 'CLM003'">
        <section class="cancel-reason__area">
          <div class="header__container">
            <p class="header__title">교환옵션</p>
          </div>
          <hr class="dp-hr h1" />
          <div class="info__body" v-html="excOption">
          </div>
        </section>
        <hr class="dp-hr justify-margin" />
      </template>

      <!-- 취소 사유-->
      <section class="cancel-reason__area section-pt-20">
        <div class="header__container">
          <p class="header__title">{{claimName}}사유</p>
        </div>
        <hr class="dp-hr h1" />
        <div class="info__body">
          <span class="info-text" v-if="claimInfo.clmtype == 'CLM001'">{{claimInfo.cnctypenm}}</span>
          <span class="info-text" v-if="claimInfo.clmtype == 'CLM002'">{{claimInfo.rtntypenm}}</span>
          <span class="info-text" v-if="claimInfo.clmtype == 'CLM003'">{{claimInfo.exctypenm}}</span>
          <span class="mb-0 reason-text dp-p-sm" v-if="!this.$util.isNull(claimInfo.clmdetail)">/ 상세사유 : {{claimInfo.clmdetail}}</span>
          <div class="image-list mt-3">
            <div v-if="item.files !== null" class="claimInfo-images dp-mt-30">
              <div class="image-list">
                <swiper :options="imgSwiperOption01">
                  <swiper-slide
                    v-for="(list, index) in item.files"
                    :key="index"
                  >
                    <div class="img__item">
                      <figure @click="openModal(item.files, index)" height="100">
                        <img :src="list.filetype == 'FLT001'? list.fullpath:$util.changeFileType(list.fullpath, '.jpg')"/>
                      </figure>
                    </div>
                  </swiper-slide>
                </swiper>
              </div>
            </div>
          </div>
        </div>
      </section>
      <hr class="dp-hr justify-margin" />

      <!-- 회수지 정보 -->
      <template v-if="claimInfo.clmtype != 'CLM001'">
        <section class="address-info__area section-pt-20">
          <div class="header__container">
            <p class="header__title">회수지 정보</p>
          </div>
          <hr class="dp-hr h1" />
          <div class="address-info__body">
            <div class="address-info__ul__area">
              <ul class="list-style-none">
                <li>
                  <div>
                    <span class="address-info__key">받는사람</span>
                    <span class="address-info__value">{{claimInfo.recname}}</span>
                  </div>
                </li>
                <li>
                  <div>
                    <span class="address-info__key">연락처</span
                    ><span class="address-info__value">{{$util.maskTel(claimInfo.rectel)}}</span>
                  </div>
                </li>
                <li>
                  <div class="d-flex align-items-start">
                    <span class="address-info__key">주소</span>
                    <span class="address-info__value">{{claimInfo.recaddrroad}}<br/> {{claimInfo.recaddrdetailroad}}</span>
                  </div>
                </li>
                <!-- <li>
                  <div>
                    <span class="address-info__key">배송요청사항</span
                    ><span class="address-info__value">문 앞에 놓아주세요.</span>
                  </div>
                </li> -->
              </ul>
            </div>
          </div>
        </section>
        <hr class="dp-hr justify-margin" />
      </template>

      <!-- 배송 정보 -->
      <template v-if="claimInfo.clmtype == 'CLM003'">
        <section class="address-info__area section-pt-20">
          <div class="header__container">
            <p class="header__title">배송정보</p>
          </div>
          <hr class="dp-hr h1" />
          <div class="address-info__body">
            <div class="address-info__ul__area">
              <ul class="list-style-none">
                <li>
                  <div>
                    <span class="address-info__key">받는사람</span>
                    <span class="address-info__value">{{claimInfo.excdlvname}}</span>
                  </div>
                </li>
                <li>
                  <div>
                    <span class="address-info__key">연락처</span
                    ><span class="address-info__value">{{$util.maskTel(claimInfo.excdlvtel)}}</span>
                  </div>
                </li>
                <li>
                  <div class="d-flex align-items-start">
                    <span class="address-info__key">주소</span>
                    <span class="address-info__value">{{claimInfo.excdlvaddrroad}}<br/>{{claimInfo.excdlvaddrdetailroad}}</span>
                  </div>
                </li>
                <!-- <li>
                  <div>
                    <span class="address-info__key">배송요청사항</span>
                    <span class="address-info__value">문 앞에 놓아주세요.</span>
                  </div>
                </li> -->
              </ul>
            </div>
          </div>
        </section>
        <hr class="dp-hr justify-margin" />
      </template>

      <!-- 환불계좌정보 -->
      <template v-if="claimInfo.refcusname != null">
        <section class="refund-account-info__area section-pt-20">
          <div class="header__container">
            <p class="header__title">환불계좌정보</p>
          </div>
          <hr class="dp-hr h1" />
          <div class="refund-account-info__body">
            <div class="d-flex justify-content-between">
              <span class="dp-p-sm">{{claimInfo.refcusname}}</span>
              <span class="dp-p-sm">{{claimInfo.refbank}} {{claimInfo.refaccnumber}}</span>
            </div>
          </div>
        </section>
        <hr class="dp-hr justify-margin" />
      </template>

      <!-- 환불/추가결제정보 -->
      <template  v-if="claimInfo.ordtotprice > 0">
        <section class="payment-info__area section-pt-20">
          <div class="header__container">
            <p class="header__title">환불정보</p>
          </div>
          <hr class="dp-hr h1" />
          <div class="payment-info__body">
            <ul class="list-style-none payment-info__ul li-pt-10" v-if="claimInfo.addrpaytotprice > 0">
              <li>
                <div class="d-flex justify-content-between align-items-center">
                  <span class="refund-info__key title font-weight-500">추가결제금액</span>
                  <span>
                    <span class="refund-info__key mr-6 text-black" v-if="claimInfo.addpaywaytype == 'PWT001'">
                      {{claimInfo.addpaywaytypenm}}{{$util.isBlank(claimInfo.cardcompany) ? '' : '(' + claimInfo.cardcompany + ')'}}
                    </span>
                    <span class="refund-info__key mr-6 text-black" v-else>{{claimInfo.addpaywaytypenm}}</span>
                    <span class="atten-new dp-price01">{{$util.maskComma(claimInfo.addrpaytotprice)}}</span>
                    <span class="price-unit">원</span>
                  </span>
                </div>
              </li>
              <li v-if="claimInfo.adddelivamt > 0">
                <div class="d-flex justify-content-between">
                  <span class="refund-info__key">{{claimName}}배송비</span>
                  <span class="atten-new dp-price02">{{$util.maskComma(claimInfo.adddelivamt)}}
                    <span class="price-unit">원</span>
                  </span>
                </div>
              </li>
              <li v-if="claimInfo.addpaytotprice > 0">
                <div class="d-flex justify-content-between">
                  <span class="refund-info__key">할인반환</span>
                    <span class="atten-new dp-price02">{{$util.maskComma(claimInfo.addpaytotprice)}}
                    <span class="price-unit">원</span>
                  </span>
                </div>
              </li>
            </ul>
            <hr class="dp-hr h1" v-if="claimInfo.addrpaytotprice > 0"/>
            <ul class="list-style-none payment-info__ul" v-if="claimInfo.clmtype != 'CLM003'">
              <li>
                <ul class="list-style-none">
                  <li>
                    <div class="d-flex justify-content-between">
                      <span class="payment-info__key title">주문금액</span>
                      <span class="atten-new dp-price01">{{$util.maskComma(claimInfo.ordtotprice + claimInfo.dadadelivamt + claimInfo.ptndelivamt)}}
                        <span class="price-unit">원</span>
                      </span>
                    </div>
                  </li>
                  <li>
                    <div class="d-flex justify-content-between">
                      <span class="payment-info__key">주문상품금액</span>
                        <span class="atten-new dp-price02">{{$util.maskComma(claimInfo.ordtotprice)}}
                      <span class="price-unit">원</span></span>
                    </div>
                  </li>
                  <li>
                    <div class="d-flex justify-content-between">
                      <span class="payment-info__key">배송비 결제금액</span>
                      <span class="atten-new dp-price02">{{$util.maskComma(claimInfo.dadadelivamt + claimInfo.ptndelivamt)}}
                        <span class="price-unit">원</span>
                      </span>
                    </div>
                  </li>
                </ul>
              </li>
              <li>
                <div>
                  <ul class="list-style-none">
                    <li v-if="!((claimInfo.totdelivcpnamt + claimInfo.totrtndelivamt) == 0)">
                      <div class="d-flex justify-content-between">
                        <span class="payment-info__key title font-weight-500">차감금액</span>
                        <span class="atten-new dp-price01">{{$util.maskComma((claimInfo.totsalepromoamt + claimInfo.totgoodscpnamt + claimInfo.totdelivcpnamt  + claimInfo.totrtndelivamt) * -1)}}
                          <span class="price-unit font-weight-500">원</span>
                        </span>
                      </div>
                    </li>
                    <!-- <li>
                      <div class="d-flex justify-content-between">
                        <span class="payment-info__key">결제수단할인 차감</span>
                        <span class="atten-new dp-price02">103,000<span class="price-unit">원</span></span>
                      </div>
                    </li> -->
                    <li v-if="(claimInfo.totdelivcpnamt + claimInfo.totrtndelivamt) > 0">
                      <div class="d-flex justify-content-between">
                        <span class="payment-info__key">배송비 차감</span>
                        <span class="atten-new dp-price02">{{$util.maskComma((claimInfo.totdelivcpnamt + claimInfo.totrtndelivamt)  * -1)}}
                          <span class="price-unit">원</span>
                        </span>
                      </div>
                    </li>
                  </ul>
                </div>
              </li>
            </ul>
            <hr class="dp-hr h1" v-if="claimInfo.rtnamt > 0"/>
            <ul class="list-style-none payment-info__ul li-pt-10" v-if="claimInfo.rtnamt > 0">
              <li>
                <div class="d-flex justify-content-between">
                  <span class="refund-info__key title font-weight-500">환불예정금액</span>
                  <span class="atten-new dp-price01">{{$util.maskComma(claimInfo.rtnamt)}}
                    <span class="price-unit font-weight-500">원</span>
                  </span>
                </div>
              </li>
              <li v-if="claimInfo.rtnamt - claimInfo.finadjustamt > 0">
                <div class="d-flex justify-content-between">
                  <span class="refund-info__key">조정금액</span>
                  <span class="atten-new dp-price02">{{$util.maskComma((claimInfo.rtnamt - claimInfo.finadjustamt) * -1)}}
                    <span class="price-unit font-weight-500">원</span>
                  </span>
                </div>
              </li>
              <li v-if="claimInfo.adjustreason != null">
                <div class="d-flex justify-content-between">
                  <span class="refund-info__key">조정사유</span>
                  <span class="atten-new dp-price02">
                      <div class="status-message__area pt-0 dp-mt-10">
                        <p class="mb-0 status-message dp-p-sm">{{claimInfo.adjustreason}}</p>
                      </div>
                  </span>
                </div>
              </li>
            </ul>
          </div>
        </section>
        <hr class="dp-hr justify-margin" />
      </template>
      
      <!--최종환불금액-->
      <template v-if="claimInfo.clmtype != 'CLM003'">
        <section class="total-refund__area section-pt-20">
          <div class="header__container">
            <div class="d-flex justify-content-between align-items-center">
              <p class="mb-0 header__title">최종환불금액</p>
              <span class="atten-new dp-title font-weight-600">{{$util.maskComma(claimInfo.finadjustamt)}}
                <span class="price-unit">원</span>
              </span>
            </div>
          </div>
          <hr class="dp-hr h1" />
          <div class="total-refund__body dp-mt-20">
            <div class="d-flex justify-content-between">
              <span class="total-refund__key" v-if="orderInfo.paywaytype == 'PWT001'">
                {{orderInfo.paywaytypename}}{{$util.isBlank(orderInfo.cardcompany) ? '' : '(' + orderInfo.cardcompany + ')'}}
              </span>
              <span class="rtotal-refund__key" v-else>{{orderInfo.paywaytypename}}</span>
              <span class="atten-new dp-price02 font-weight-600">{{$util.maskComma(claimInfo.rtnpayamt)}}
                <span class="price-unit">원</span>
              </span>
            </div>
          </div>
        </section>
        <hr class="dp-hr justify-margin" />
      </template>

      <!-- 추가결제정보 -->
      <template v-if="claimInfo.addrpaytotprice > 0">
        <section class="payment-info__area section-pt-20">
          <div class="header__container">
            <p class="header__title">추가결제정보</p>
          </div>
          <hr class="dp-hr h1" />
          <div class="payment-info__body">
            <ul class="list-style-none payment-info__ul li-pt-10" v-if="claimInfo.addrpaytotprice > 0">
              <li>
                <div class="d-flex justify-content-between align-items-center">
                  <span class="refund-info__key title font-weight-500">추가결제금액</span>
                  <span>
                    <span class="refund-info__key mr-6 text-black" v-if="claimInfo.addpaywaytype == 'PWT001'">
                      {{claimInfo.addpaywaytypenm}}{{$util.isBlank(claimInfo.cardcompany) ? '' : '(' + claimInfo.cardcompany + ')'}}
                    </span>
                    <span class="refund-info__key mr-6 text-black" v-else>{{claimInfo.addpaywaytypenm}}</span>
                    <span class="atten-new dp-price01">{{$util.maskComma(claimInfo.addrpaytotprice)}}</span>
                    <span class="price-unit">원</span>
                  </span>
                </div>
              </li>
              <li v-if="claimInfo.adddelivamt > 0">
                <div class="d-flex justify-content-between">
                  <span class="refund-info__key">{{claimName}}배송비</span>
                  <span class="atten-new dp-price02">{{$util.maskComma(claimInfo.adddelivamt)}}
                    <span class="price-unit">원</span>
                  </span>
                </div>
              </li>
              <li v-if="claimInfo.addpaytotprice > 0">
                <div class="d-flex justify-content-between">
                  <span class="refund-info__key">할인반환</span>
                    <span class="atten-new dp-price02">{{$util.maskComma(claimInfo.addpaytotprice)}}
                    <span class="price-unit">원</span>
                  </span>
                </div>
              </li>
            </ul>
          </div>
        </section>
        <hr class="dp-hr justify-margin" />
      </template>

      <!-- 결제수단별 환불시점안내 -->
      <template v-if="claimInfo.clmtype != 'CLM003'">
        <section class="refund-guide__area section-pt-20">
          <div class="refund-guide__header dp-mb-20">
            <p class="header__title">결제수단별 환불시점안내</p>
          </div>
          <div class="refund-guide__body">
            <ul class="list-style-none refund-guide__ul">
              <li>
                <div class="refund-guide__text">
                  <p class="text-black">무통장입금</p>
                  <p class="refund-p">당일: 즉시 등록환불계좌 입금</p>
                  <p class="refund-p">당일이휴: 익일 등록환불계좌 입금</p>
                </div>
              </li>
              <li>
                <div class="refund-guide__text">
                  <p class="text-black">신용카드</p>
                  <p class="refund-p">
                    당일 : 전체 (당일취소) / 부분 (영업일 3~5일)
                  </p>
                </div>
              </li>
              <li>
                <div class="refund-guide__text">
                  <p class="text-black">체크카드</p>
                  <p class="refund-p">당일: 즉시 등록환불계좌 입금</p>
                </div>
              </li>
              <li>
                <div class="refund-guide__text">
                  <p class="text-black">계좌이체</p>
                  <p class="refund-p">결제 시 이용계좌 즉시 환불</p>
                </div>
              </li>
              <li>
                <div class="refund-guide__text">
                  <p class="text-black">D포인트/적립금/임직원적립금</p>
                  <p class="refund-p">즉시환불</p>
                </div>
              </li>
              <li>
                <p class="text-secondary">
                  * 실제 환불되는 금액은 할인 적용 조건에 따라 환불예정 금액과
                  다소 상이할 수 있습니다.
                </p>
              </li>
            </ul>
          </div>
        </section>
        <hr class="dp-hr justify-margin" />
      </template>

      <!-- 관리자 취소신청(추가결제필요)-->
      <section class="add-payment__area section-pt-20" v-if="(claimInfo.cncstatus == 'CNS002' || claimInfo.rtnstatus == 'RTS002' || claimInfo.excstatus == 'EXS002') && claimInfo.addrpaytotprice > 0">
        <div class="add-payment__header">
          <div class="d-flex justify-content-between align-items-center header__container">
            <p class="header__title">추가결제</p>
            <div class="text-primary">
              <span class="atten-new dp-p font-weight-500">{{$util.maskComma(claimInfo.addrpaytotprice)}}</span>
              <span class="dp-caption">원</span>
            </div>
          </div>
        </div>
        <hr class="dp-hr h1 dp-mb-30" />
        <div class="add-payment__body">
          <div class="method__box">
            <ul class="list-style-none method__ul">
              <li v-for="(payway, index) in paywaylist" :key="index">
                <label :for="payway.cmcode" class="method__label mb-0">
                <input
                  :id="payway.cmcode"
                  type="radio"
                  name="payMethod"
                  :value="payway.cmcode"
                  v-model="payInfo.paywaytype"
                  @change="changePayWayType"
                  hidden
                />
                <span class="label__item">{{payway.codename}}</span>
                </label>
              </li>
            </ul>
          </div>
          <div class="dp-mt-30" v-if="payInfo.paywaytype == 'PWT001'">
            <form>
              <div class="add-payment__title">
                <label>카드종류</label>
                <base-select
                  placeholder="카드를 선택해주세요"
                  :options="cardlist"
                  v-model="payInfo.cardCompany"
                />
              </div>
              <div class="add-payment__title" v-if="claimInfo.addrpaytotprice >= 50000">
                <label>할부선택</label>
                <base-select 
                  v-model="payInfo.cardInstallmentPlan"
                  :options="cardPlan"
                  placeholder="일시불"/>
              </div>
            </form>
          </div>
        </div>
        <div class="add-payment__footer">
          <p class="mb-0 dp-p-sm text-secondary">
            * {{claimName}}신청을 완료하시려면 추가결제가 필요합니다.
          </p>
          <div class="btn-group d-flex">
            <b-button
              class="dp-btn not-hover"
              variant="outline-black"
              squared
              @click="$router.go(-1)"
            >
              <span>뒤로</span>
            </b-button>
            <b-button class="dp-btn text-white" variant="gray-800" squared @click="paymentProc">
              <span>추가결제</span>
            </b-button>
          </div>
        </div>
      </section>
    </div>
  </main>
</template>

<script src="@views.mobile/nonemember/shop/claim/ClaimDetail.js">
</script>
