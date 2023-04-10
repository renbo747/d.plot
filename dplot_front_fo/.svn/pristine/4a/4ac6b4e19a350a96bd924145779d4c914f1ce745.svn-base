<template>
  <main class="aside-article dp-mypage-cancel-detail">
    <header class="aside-article__header">
      <div class="header-title">{{claimName}} 상세</div>
    </header>
    <section class="cancel-detail__header">
      <div class="d-flex align-items-center">
        <span class="apply-cancel">{{claimName}}신청</span>
        <div class="d-flex align-items-center justify-content-center">
          <span class="apply-date atten-new">{{$util.getFormatDate(claimInfo.clmreqdate, '.')}}</span>
          <span class="dp-bar h14"></span>
          <span class="apply-number atten-new">{{claimInfo.ordno}}</span>
        </div>
      </div>
    </section>
    <hr class="dp-hr" />
    <!-- 클레임승인대기 상품-->
    <template v-for="(dealer, index) in dealers">
      <section :key="'item_'+index">
        <div class="cancel-status__area">
          <div class="cancel-status-product__header">
            <p class="mb-0 cancel-product__header__title">{{dealer.dealernm}} 배송상품</p>
          </div>
          <hr class="h1" />
          <div class="cancel-status__product-info__area">
            <ul class="list-style-none">
              <li class="apply__product-info__li" v-for="(item, index2) in dealer.items" :key="index2">
                <div class="product-item">
                  <div class="item-box">
                    <div class="item-thumbnail">
                      <product-thumbnail 
                      :thumbnail-info="{ id: item.goodsno,fullpath: item.fullpath}"
                      :to="{name:'shop-detail',params:{pid:item.goodscode}}" 
                      :height="150"/>
                    </div>
                    <div class="item-info">
                      <p class="item-status">{{ item.statusnm }}</p>
                      <p class="item-ctg">{{ item.brandname }}</p>
                      <p class="item__p">{{ item.goodsname}}</p>
                      <p class="item__p option d-flex align-items-center" v-html="item.opthtml">
                      </p>
                    </div>
                  </div>
                  <div class="item-count">
                    <p class="item__p text-center">{{ item.ordcnt }}개</p>
                  </div>
                  <div class="item-price">
                    <p class="price__p text-center mb-0">
                      <span class="price__span atten-new">{{$util.maskComma(item.realgoodsamt)}}</span>원
                    </p>
                  </div>
                  <div class="item-button">
                    <b-button
                      v-for="(button, index3) in item.buttonData"
                      :key="index3"
                      class="dp-btn btn-h32"
                      variant="outline-gray-400"
                      squared
                      @click="handleFooterButton(item, button.text)"
                    >
                      <span class="text-gray-800">{{ button.text }}</span>
                    </b-button>
                  </div>
                </div>
              </li>
            </ul>
          </div>
          <div class="status-message__area">
            <!-- <p class="mb-0 status-message" v-if="item.clmstatus == 'CNS001' || item.clmstatus == 'RTS001' || item.clmstatus == 'EXS001'">
              주문{{claimName}} 요청을 확인하는 중이에요
            </p>
            <p class="mb-0 status-message" v-if="item.clmstatus == 'CNS002' || item.clmstatus == 'RTS002' || item.clmstatus == 'EXS002'">
              추가결제가 완료되면 {{claimName}}신청이 완료됩니다.
            </p> -->
          </div>
        </div>
      </section>
      <hr class="dp-hr" :key="'line_'+index"/>
    </template>
    <!-- 사은품 -->
    <template v-if="giftList.length > 0">
      <section class="free-item__area">
        <div class="free-item__header">
          <p class="mb-0 header__title">사은품</p>
        </div>
        <div class="free-item__body">
          <template v-for="(gift,index) in giftList">
            <div class="free-item__container" :key="index">
              <p class="event__title mb-0">
                {{ gift.promoname }}
              </p>
              <div class="section__free-item">
                <ul class="row list-style-none">
                  <li
                    class="col-6 col-xl-4"
                    v-for="(good, index2) in gift.detail"
                    :key="index2"
                  >
                    <div class="free-item d-flex">
                      <product-thumbnail
                        :is-checked="false"
                        :thumbnail-info="good"
                        style="width: 150px"
                      />
                      <div class="free-item-info d-flex flex-column justify-content-center">
                        <p class="free-item__name mb-0">
                          [선택{{(index2+1)}}] 선택 사은품<br />
                          {{good.giftname}}
                        </p>
                        <p class="free-item__quantity mb-0">
                          {{ good.rcvamt }}개
                        </p>
                      </div>
                    </div>
                  </li>
                </ul>
              </div>
            </div>

            <!-- 반품시 안내사항 -->
            <ul class="ul-dot dp-mt-20" :key="'msg_index'+index" v-if="gift.isadd == 'F'">
              <li class="return-description">
                반품 시 사은품도 함께 반품해주세요
              </li>
            </ul>
          </template>
        </div>
      </section>
      <hr class="dp-hr" />
    </template>
    <!-- 취소 사유-->
    <section class="cancel-reason__area">
      <div class="cancel-reason__header">
        <div>
          <p class="mb-0 cancel-reason__title header__title">{{claimName}}사유</p>
        </div>
      </div>
      <hr class="h1" />
      <div class="cancel-reason__body">
        <div>
          <span class="mb-0 reason-text dp-p-sm" v-if="claimInfo.clmtype == 'CLM001'">{{claimInfo.cnctypenm}} </span>
          <span class="mb-0 reason-text dp-p-sm" v-if="claimInfo.clmtype == 'CLM002'">{{claimInfo.rtntypenm}} </span>
          <span class="mb-0 reason-text dp-p-sm" v-if="claimInfo.clmtype == 'CLM003'">{{claimInfo.exctypenm}} </span>
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
      </div>
    </section>
    <hr class="dp-hr" />
    <!-- 회수지 정보 -->
    <template v-if="claimInfo.clmtype != 'CLM001'">
      <section class="address-info__area" >
        <div class="address-info__header">
          <p class="mb-0 address-info__title header__title">회수지 정보</p>
        </div>
        <hr class="h1" />
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
                  <span class="address-info__key">연락처</span>
                  <span class="address-info__value">{{$util.maskTel(claimInfo.rectel)}}</span>
                </div>
              </li>
              <li>
                <div class="d-flex align-items-start">
                  <span class="address-info__key">주소</span>
                  <span class="address-info__value">{{claimInfo.recaddrroad}} {{claimInfo.recaddrdetailroad}}</span>
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
      <hr class="dp-hr" />
    </template>

    <!-- 배송 정보 -->
    <template v-if="claimInfo.clmtype == 'CLM003'">
      <section class="address-info__area" >
        <div class="address-info__header">
          <p class="mb-0 address-info__title header-title">배송정보</p>
        </div>
        <hr class="h1" />
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
                  <span class="address-info__key">연락처</span>
                  <span class="address-info__value">{{$util.maskTel(claimInfo.excdlvtel)}}</span>
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
                  <span class="address-info__key">배송요청사항</span
                  ><span class="address-info__value">문 앞에 놓아주세요.</span>
                </div>
              </li> -->
            </ul>
          </div>
        </div>
      </section>
      <hr class="dp-hr" />
    </template>

    <!-- 환불계좌정보 -->
    <template v-if="claimInfo.refcusname != null">
      <section class="refund-account-info__area">
        <div class="refund-account-info__header">
          <p class="mb-0 refund-account-info__title header__title">
            환불계좌정보
          </p>
        </div>
        <hr class="h1" />
        <div class="refund-account-info__body">
          <div class="d-flex justify-content-between">
            <span>{{claimInfo.refcusname}}</span>
            <span>{{claimInfo.refbank}} {{claimInfo.refaccnumber}}</span>
          </div>
        </div>
      </section>
      <hr class="dp-hr" />
    </template>

    <!-- 환불/추가결제정보 -->
    <section class="payment-info__area" v-if="claimInfo.ordtotprice > 0 || claimInfo.addrpaytotprice > 0">
      <div class="payment-info__header">
        <p class="mb-0 header__title">환불/추가결제정보</p>
      </div>
      <hr class="h1" />
      <div class="payment-info__body">
        <ul class="list-style-none payment-info__ul" v-if="claimInfo.clmtype != 'CLM003'">
          <li class="pb-20">
            <div>
              <ul class="list-style-none">
                <li>
                  <div class="d-flex justify-content-between">
                    <span class="payment-info__key title">주문금액</span>
                    <span class="atten-new l-price">{{$util.maskComma(claimInfo.ordtotprice + claimInfo.dadadelivamt + claimInfo.ptndelivamt)}}
                      <span class="l-price-unit">원</span>
                    </span>
                  </div>
                </li>
                <li>
                  <div class="d-flex justify-content-between">
                    <span class="payment-info__key">주문상품</span>
                    <span class="atten-new price">{{$util.maskComma(claimInfo.ordtotprice)}}<span class="price-unit">원</span></span>
                  </div>
                </li>
                <li>
                  <div class="d-flex justify-content-between">
                    <span class="payment-info__key">배송비</span>
                    <span class="atten-new price">{{$util.maskComma(claimInfo.dadadelivamt + claimInfo.ptndelivamt)}}
                      <span class="price-unit">원</span>
                    </span>
                  </div>
                </li>
              </ul>
            </div>
          </li>
          <hr class="h1" />
          <li class="pt-20" v-if="!(claimInfo.totsalepromoamt == 0 && claimInfo.totgoodscpnamt == 0 && claimInfo.basketcpnamt == 0 && (claimInfo.totdelivcpnamt + claimInfo.totrtndelivamt) ==0)">
            <div>
              <ul class="list-style-none">
                <li>
                  <div class="d-flex justify-content-between">
                    <span class="payment-info__key title font-weight-500">할인금액</span>
                    <span class="atten-new l-price">{{$util.maskComma((claimInfo.basketcpnamt + claimInfo.totsalepromoamt + claimInfo.totgoodscpnamt + claimInfo.totdelivcpnamt  + claimInfo.totrtndelivamt) * -1)}}
                      <span class="l-price-unit">원</span>
                    </span>
                  </div>
                </li>
                <li v-if="claimInfo.totsalepromoamt > 0">
                  <div class="d-flex justify-content-between">
                    <span class="payment-info__key">{{isemp ? '임직원' : '프로모션'}}할인</span>
                    <span class="atten-new price">{{$util.maskComma(claimInfo.totsalepromoamt * -1)}}<span class="price-unit">원</span></span
                    >
                  </div>
                </li>
                <li v-if="claimInfo.totgoodscpnamt > 0">
                  <div class="d-flex justify-content-between">
                    <span class="payment-info__key">상품쿠폰</span>
                    <span class="atten-new price">{{$util.maskComma(claimInfo.totgoodscpnamt * -1)}}<span class="price-unit">원</span></span>
                  </div>
                </li>
                <!-- <li>
                  <div class="d-flex justify-content-between">
                    <span class="payment-info__key">결제수단할인 차감</span>
                    <span class="atten-new price">-10,000<span class="price-unit">원</span></span>
                  </div>
                </li> -->
                <li v-if="claimInfo.basketcpnamt > 0">
                  <div class="d-flex justify-content-between">
                    <span class="payment-info__key">추가쿠폰</span>
                    <span class="atten-new price">{{$util.maskComma(claimInfo.basketcpnamt * -1)}}<span class="price-unit">원</span></span>
                  </div>
                </li>
                <li v-if="(claimInfo.totdelivcpnamt) > 0">
                  <div class="d-flex justify-content-between">
                    <span class="payment-info__key">배송비쿠폰</span>
                    <span class="atten-new price">{{$util.maskComma((claimInfo.totdelivcpnamt)  * -1)}}<span class="price-unit">원</span></span>
                  </div>
                </li>
                <li v-if="(claimInfo.totrtndelivamt) > 0">
                  <div class="d-flex justify-content-between">
                    <span class="payment-info__key">배송비차감</span>
                    <span class="atten-new price">{{$util.maskComma((claimInfo.totrtndelivamt)  * -1)}}<span class="price-unit">원</span></span>
                  </div>
                </li>
              </ul>
            </div>
          </li>
        </ul>
        <hr class="h1" v-if="claimInfo.rtnamt > 0"/>
        <ul class="list-style-none payment-info__ul" v-if="claimInfo.rtnamt - claimInfo.finadjustamt > 0 || claimInfo.adjustreason != null">
          <!-- <li>
            <div class="d-flex justify-content-between align-items-center">
              <span class="refund-info__key title">환불예정금액</span>
              <span class="atten-new l-price">{{$util.maskComma(claimInfo.rtnamt)}}
                <span class="l-price-unit">원</span>
              </span>
            </div>
          </li> -->
          <li v-if="claimInfo.rtnamt - claimInfo.finadjustamt">
            <div class="d-flex justify-content-between">
              <span class="refund-info__key">조정금액</span>
              <span class="atten-new price">{{$util.maskComma((claimInfo.rtnamt - claimInfo.finadjustamt) * -1)}}
                <span class="price-unit">원</span>
              </span>
            </div>
          </li>
          <li v-if="claimInfo.adjustreason != null">
            <div class="d-flex justify-content-between">
              <span class="refund-info__key text-secondary">{{claimInfo.adjustreason}}</span>
            </div>
          </li>
        </ul>
        <hr class="h1"/>
        <ul class="list-style-none payment-info__ul" v-if="claimInfo.clmtype != 'CLM003'">
          <li v-if="claimInfo.rtnamt > 0">
            <div class="d-flex justify-content-between">
              <span class="refund-info__key title font-weight-500">최종환불금액</span>
              <span class="atten-new l-price">{{$util.maskComma(claimInfo.finadjustamt)}}<span class="l-price-unit">원</span></span>
            </div>
          </li>
          <li v-if="claimInfo.rtnresamt > 0">
            <div class="d-flex justify-content-between">
              <span class="refund-info__key">적립금</span><span class="atten-new price">{{$util.maskComma(claimInfo.rtnresamt)}}<span class="price-unit">원</span></span>
            </div>
          </li>
          <li v-if="claimInfo.rtnepoamt > 0">
            <div class="d-flex justify-content-between">
              <span class="refund-info__key">D포인트</span><span class="atten-new price">{{$util.maskComma(claimInfo.rtnepoamt)}}<span class="price-unit"> P</span></span>
            </div>
          </li>
          <li v-if="claimInfo.rtnempresamt > 0">
            <div class="d-flex justify-content-between">
              <span class="refund-info__key">임직원적립금</span><span class="atten-new price">{{$util.maskComma(claimInfo.rtnempresamt)}}<span class="price-unit">원</span></span>
            </div>
          </li>
          <li v-if="claimInfo.rtnpayamt > 0">
            <div class="d-flex justify-content-between">
              <span class="refund-info__key" v-if="orderInfo.paywaytype == 'PWT001'">
                {{orderInfo.paywaytypename}}{{$util.isBlank(orderInfo.cardcompany) ? '' : '(' + orderInfo.cardcompany + ')'}}
              </span>
              <span class="refund-info__key" v-if="orderInfo.paywaytype != 'PWT001' && orderInfo.paywaytype != 'PWT009'">{{orderInfo.paywaytypename}}</span>
              <span class="atten-new price">{{$util.maskComma(claimInfo.rtnpayamt)}}
                <span class="price-unit">원</span>
              </span>
            </div>
          </li>
        </ul>
        <hr class="h1" v-if="claimInfo.addrpaytotprice > 0"/>
        <ul class="list-style-none payment-info__ul" v-if="claimInfo.addrpaytotprice > 0">
          <li>
            <div class="d-flex justify-content-between align-items-center">
              <span class="refund-info__key title">추가결제금액</span>
              <span>
                <span class="refund-info__key mr-6 text-black" v-if="claimInfo.addpaywaytype == 'PWT001'">
                  {{claimInfo.addpaywaytypenm}}{{$util.isBlank(claimInfo.cardcompany) ? '' : '(' + claimInfo.cardcompany + ')'}}
                </span>
                <span class="refund-info__key mr-6 text-black" v-else>{{claimInfo.addpaywaytypenm}}</span>
                <span class="atten-new l-price">{{$util.maskComma(claimInfo.addrpaytotprice)}}</span>
                <span class="l-price-unit">원</span>
              </span>
            </div>
          </li>
          <li v-if="claimInfo.adddelivamt > 0">
            <div class="d-flex justify-content-between">
              <span class="refund-info__key">{{claimName}}배송비</span>
              <span class="atten-new price">{{$util.maskComma(claimInfo.adddelivamt)}}
                <span class="price-unit">원</span>
              </span>
            </div>
          </li>
          <li v-if="claimInfo.addpaytotprice > 0">
            <div class="d-flex justify-content-between">
              <span class="refund-info__key">할인반환</span>
              <span class="atten-new price">{{$util.maskComma(claimInfo.addpaytotprice)}}
                <span class="price-unit">원</span>
              </span>
            </div>
          </li>
        </ul>
      </div>
    </section>
    <hr class="dp-hr" />

    <!-- 결제수단별 환불시점안내 -->
    <template v-if="claimInfo.clmtype != 'CLM003'">
      <section class="refund-guide__area">
        <div class="refund-guide__header dp-mb-30">
          <p class="mb-0 header__title">결제수단별 환불시점안내</p>
        </div>
        <div class="refund-guide__body">
          <ul class="list-style-none refund-guide__ul">
            <li>
              <div class="refund-guide__text">
                <p class="text-black">무통장입금</p>
                <span class="refund-p">당일: 즉시 등록환불계좌 입금</span>
                <span class="refund-p">당일이후: 익일 등록환불계좌 입금</span>
              </div>
            </li>
            <li>
              <div class="refund-guide__text">
                <p class="text-black">신용카드</p>
                <span class="refund-p"
                  >당일 : 전체 (당일취소) / 부분 (영업일 3~5일)</span
                >
              </div>
            </li>
            <li>
              <div class="refund-guide__text">
                <p class="text-black">체크카드</p>
                <span class="refund-p"
                  >당일 : 전체 (당일취소) / 부분 (영업일 3~5일)</span
                >
              </div>
            </li>
            <li>
              <div class="refund-guide__text">
                <p class="text-black">계좌이체</p>
                <span class="refund-p">결제 시 이용계좌 즉시 환불</span>
              </div>
            </li>
            <li>
              <div class="refund-guide__text">
                <p class="text-black">적립금/임직원적립금</p>
                <span class="refund-p">즉시환불</span>
              </div>
            </li>
          </ul>
        </div>
      </section>
      <hr class="dp-hr" />
    </template>

    <!-- 교환상세 페이지의 교환옵션 -->
    <section class="exchange-option__area" v-if="claimInfo.clmtype == 'CLM003'">
      <div class="exchange__header">
        <div><p class="mb-0 header__title">교환옵션</p></div>
      </div>
      <hr class="h1" />
      <div class="exchange__body dp-mt-30">
        <div class="d-flex align-items-center" v-html="excOption">
        </div>
      </div>
    </section>
    <hr class="dp-hr" />

    <!-- 관리자 취소신청(추가결제필요)-->
    <section class="add-payment__area" v-if="(claimInfo.cncstatus == 'CNS002' || claimInfo.rtnstatus == 'RTS002' || claimInfo.excstatus == 'EXS002') && claimInfo.addrpaytotprice > 0">
      <div class="add-payment__header">
        <div class="d-flex justify-content-between">
          <p class="mb-0 header__title">추가결제</p>
          <span class="text-primary dp-caption">
            <span class="dp-p atten-new font-weight-500 mr-1">{{$util.maskComma(claimInfo.addrpaytotprice)}}</span>원
          </span>
        </div>
      </div>
      <hr class="h1 dp-mt-20 dp-mb-30" />
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
            <div class="mb-20">
              <label>카드종류</label>
              <base-dropdown
                class="btn-h48"
                placeholder="카드를 선택해주세요"
                :options="cardlist"
                v-model="payInfo.cardCompany"
              />
            </div>

            <div v-if="claimInfo.addrpaytotprice >= 50000">
              <label>할부선택</label>
              <base-dropdown
                class="btn-h48"
                v-model="payInfo.cardInstallmentPlan"
                :options="cardPlan"
                placeholder="일시불"
              />
            </div>
          </form>
        </div>
      </div>
      <div class="add-payment__footer">
        <p class="mb-0 text-secondary">
          * {{claimName}}신청을 완료하시려면 추가결제가 필요합니다.
        </p>
        <div class="btn-group d-flex justify-content-center">
          <b-button
            class="dp-btn not-hover"
            variant="outline-gray-800"
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
  </main>
</template>

<script src="@views.mobile/mypage/shop/claim/ClaimDetail.js">
</script>