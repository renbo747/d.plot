<template>
  <main class="dp-order pc-top-padding">
    <div class="container">
      <div class="container-inner">
        <header class="dp-cart-header">
          <h1 class="dp-cart-title mb-0 font-weight-700 text-black">
            주문결제
          </h1>
        </header>
      </div>
    </div>
    <layout-right>
      <template #left-contents>
        <!-- 비회원일경우 -->
        <template v-if="isnonmember == 'T'">
          <!-- 비회원 개인정보 수집 이용 동의 -->
          <section class="dp-order-panel pt-0">
          <div class="order-payment-body">
              <div class="my-collapse my-collapse-non-member">
              <div class="non-member-list">
                  <base-checkbox 
                    v-model="nonMemberInfo.isAdult"
                    label="만 14세 이상입니다." 
                    id="nonMember01" 
                  />
              </div>
              <div class="non-member-list">
                  <base-checkbox
                    v-model="nonMemberInfo.isAgree"
                    label="비회원 개인정보 수집이용에 대한 동의"
                    id="nonMember02"
                  />
                  <div class="non-member__div">
                  <p class="non-member__p">
                      <span>비회원 개인정보 수집이용약관</span>
                  </p>
                  <p class="non-member__p">
                      <span>수집하는 개인정보 항목</span>
                      <span>- 이름, 전화번호, 주소</span>
                  </p>
                  <p class="non-member__p">
                      <span>개인정보 수집 이용목적</span>
                      <span>- 주문/배송정보 고지</span>
                  </p>
                  <p class="non-member__p">
                      <span>보유기간 및 이용기간</span>
                      <span>- 보유기간 5년</span>
                  </p>
                  </div>
              </div>
              </div>
          </div>
          </section>
          <hr class="dp-hr h6" />

          <!-- 주문지 정보 -->
          <section class="dp-order-panel">
          <header class="order-payment-header d-flex align-items-center justify-content-between">
              <div class="payment-header-title">
              <h2 class="payment-header-title-h2 text-black font-weight-500 mb-0">
                  주문자 정보
              </h2>
              </div>
          </header>
          <div class="order-payment-body">
              <div class="my-collapse my-collapse-order-info">
              <form>
                  <div class="form-group">
                  <base-input-horizon
                      v-model="nonMemberInfo.ordname"
                      :is-label="true"
                      label="주문자"
                      placeholder="이름을 입력해주세요"
                      :max-length="20"
                  />
                  </div>
                  <div class="form-group">
                  <base-input-horizon
                      :type="'number'"
                      v-model="nonMemberInfo.ordtel"
                      :is-label="true"
                      :is-readonly="nonMemberInfo.isSend || nonMemberInfo.isAuth"
                      :is-clear="!nonMemberInfo.isAuth"
                      label="연락처"
                      :max-length="11"
                      placeholder="휴대전화 번호 - 없이 입력"
                  />
                  </div>
                  <b-button
                    class="dp-btn auth-btn btn-h48 mb-10"
                    variant="outline-gray-400"
                    squared
                    @click="sendAuth"
                  >
                    <span class="dp-btn__gray-800 text-gray-800">인증번호 {{nonMemberInfo.isSend ? '재발송' : '발송'}}</span>
                  </b-button>
                  <div class="certification-number-area">
                  <base-input
                      :type="'number'"
                      v-model="nonMemberInfo.authnum"
                      placeholder="인증번호"
                      :is-readonly="nonMemberInfo.isAuth || !nonMemberInfo.isSend"
                      :data-timer="hhmm"
                      :max-length="6"
                      class="certification-number auth-number reset-auth"
                  />
                  <b-button
                      class="dp-btn text-white certification-confirm-btn"
                      variant="gray-800"
                      squared
                      @click="confirmAuth"
                      >확인</b-button
                  >
                  </div>
                  <div class="form-group">
                  <base-input-horizon
                      :type="'email'"
                      v-model="nonMemberInfo.ordemail"
                      :is-label="true"
                      label="이메일"
                      placeholder="@포함 직접입력"
                  />
                  </div>
              </form>
              </div>
          </div>
          </section>
          <hr class="dp-hr h6" />

          <!-- 배송지 정보 -->
          <section class="dp-order-panel">
          <header class="order-payment-header d-flex align-items-center justify-content-between">
              <div class="payment-header-title">
                <h2 class="payment-header-title-h2 text-black font-weight-500 mb-0">
                    배송지 정보
                </h2>
                <base-checkbox
                  v-model="nonMemberInfo.isSame"
                  label="주문자 정보와 동일"
                  class="order-info-checkbox"
                  @change="sameOrder"
                />
              </div>
          </header>
          <div class="order-payment-body">
              <div class="my-collapse my-collapse-shipping-info">
              <form class="form-area">
                  <div class="form-group">
                  <base-input-horizon
                      v-model="addr.rcvname"
                      label="받는사람"
                      :is-label="true"
                      placeholder="이름을 입력해주세요"
                  />
                  </div>
                  <div class="form-group">
                  <base-input-horizon
                      :type="'tel'"
                      v-model="addr.rcvtel1"
                      label="연락처"
                      :is-label="true"
                      :max-length="11"
                      placeholder="휴대전화 번호 - 없이 입력"
                  />
                  </div>
                  <div class="form-group d-flex align-items-end">
                  <base-input-horizon
                      v-model="addr.rcvpost"
                      label="주소"
                      :is-label="true"
                      :is-readonly="true"
                      placeholder=""
                  />
                  <b-button
                      class="dp-btn text-white post-find-btn"
                      variant="gray-800"
                      squared
                      @click="postAddr"
                  >
                      <span>우편번호 찾기</span>
                  </b-button>
                  </div>
                  <div class="form-group">
                    <base-input 
                      v-model="addr.rcvaddrroad"
                      placeholder="" 
                      :is-readonly="true" />
                  </div>
                  <div class="form-group">
                    <base-input
                      v-model="addr.rcvaddrdetailroad"
                      placeholder="상세주소 입력" />
                  </div>
                  <div class="order-payment-drop form-group">
                    <base-dropdown
                      class="btn-h48"
                      v-model="rcvreqtype"
                      :options="reqList"
                      @change="reqtypeChange"
                      placeholder="배송 요청사항 (선택)"
                    />
                  </div>
                  <div class="order-payment-drop pt-10" v-if="directinput">
                    <base-input
                      class="btn-h48"
                      v-model="rcvreqdetail"
                      placeholder="배송요청사항을 입력해주세요"
                    />
                  </div>
              </form>
              </div>
          </div>
          </section>
          <hr class="dp-hr h6" />
        </template>
        <template v-else><!--Todo: 배송지 section 간격조정 dp-panel 추가 6월14일 -->
          <!-- 배송지 입력 -->
          <section class="dp-order-panel dp-panel">
            <!-- 배송지 입력 헤더 -->
            <header class="order-payment-header d-flex align-items-center justify-content-between">
              <div class="payment-header-title">
                <h2 class="payment-header-title-h2 text-black font-weight-500 mb-0">
                  배송지
                </h2>
              </div>
              <div class="payment-header__btn" v-if="$util.isBlank(addr.rcvpost)">
                <b-button
                  class="option-list__btn dp-btn btn-h32 not-hover d-flex align-items-center"
                  variant="outline-gray-800 type02"
                  squared
                  pill
                  @click="addAddr"
                >
                  <span class="dp-caption font-weight-400 text-gray-800">배송지 추가</span>
                  <i class="dp-icon sm01 icon-pick"></i>
                </b-button>
              </div>
              <div class="payment-header__btn" v-else>
                <b-button
                  class="option-list__btn dp-btn btn-h32 not-hover d-flex align-items-center"
                  variant="outline-gray-800 type02"
                  squared
                  pill
                  @click="changeAddr"
                >
                  <span class="dp-caption font-weight-400 text-gray-800">배송지 변경</span>
                  <i class="dp-icon sm01 icon-pick"></i>
                </b-button>
              </div>
            </header>
            <!-- 배송지 입력 바디 -->
            <div class="order-payment-body" v-if="$util.isBlank(addr.rcvpost)">
              <!-- <div class="d-flex align-items-center mb-10">
                <div class="order-payment-orderer">
                  <p class="order-payment-orderer__name text-black font-weight-500 mb-0">
                    기본배송지 정보가 없습니다.
                  </p>
                </div>
              </div> -->
              <div class="order-payment-address">
                <p class="order-payment-address-text mb-0 text-gray-700 font-weight-400">
                  배송지를 추가해주세요.
                </p>
              </div>
            </div>
            <div class="order-payment-body" v-else>
              <div class="d-flex align-items-center mb-10">
                <div class="order-payment-orderer">
                  <p class="order-payment-orderer__name text-black font-weight-500 mb-0">
                    {{addr.rcvname}}
                  </p>
                </div>
                <div class="order-payment-badge" v-if="addr.isdefault=='T'">
                  <span class="dp-badge h26 lh-1">기본 배송지</span>
                </div>
              </div>
              <div class="order-payment-address">
                <p class="order-payment-address-text mb-0 text-gray-700 font-weight-400">
                  우{{addr.rcvpost}} / {{addr.rcvaddrroad}} {{addr.rcvaddrdetailroad}}
                </p>
                <p class="order-payment-address-text payment-address-phone mb-0 text-gray-700 font-weight-400">
                  {{$util.maskTel(addr.rcvtel1)}}
                </p>
              </div>
              <!-- 드롭 다운 메뉴 및 인풋 박스 -->
              <div class="order-payment-form">
                <form>
                  <div class="order-payment-drop form-group">
                    <base-dropdown
                      class="btn-h48"
                      v-model="rcvreqtype"
                      :options="reqList"
                      @change="reqtypeChange"
                      placeholder="배송 요청사항 (선택)"
                    />
                  </div>
                  <div class="order-payment-drop pt-10" v-if="directinput">
                    <base-input
                      class="btn-h48"
                      v-model="rcvreqdetail"
                      placeholder="배송요청사항을 입력해주세요"
                    />
                  </div>
                </form>
              </div>
            </div>
          </section>
          <!--// Todo: 배송지 section 간격조정 dp-panel 추가 6월14일 -->
          <hr class="dp-hr h6" />
        </template>
        <!-- 주문상품 -->
        <section class="dp-order-panel pb-0">
          <header
            class="order-payment-header d-flex align-items-center justify-content-between"
          >
            <div class="payment-header-title">
              <h2
                class="payment-header-title-h2 text-black font-weight-500 mb-0"
              >
                주문상품
              </h2>
            </div>
          </header>
          <div>
            <ul
              class="dp-cart-product-menu-wrap d-flex align-items-center justify-content-center list-style-none"
            >
              <li class="dp-cart-product-menu product-menu-info">
                <p
                  class="dp-cart-product-menu__title product-menu-info__title mb-0 text-gray-700"
                >
                  상품정보
                </p>
              </li>
              <li class="dp-cart-product-menu product-menu-price">
                <p class="dp-cart-product-menu__title mb-0 text-gray-700">
                  판매금액
                </p>
              </li>
              <li class="dp-cart-product-menu product-menu-count">
                <p class="dp-cart-product-menu__title mb-0 text-gray-700">
                  수량
                </p>
              </li>
            </ul>
          </div>
          <div class="dp-order-product__list">
            <div>
              <div class="dp-order-product__list__item"  v-for="dealer in dealers" :key="dealer.dealerno + '_' + skey">
                <div class="d-flex align-items-center mb-30" v-for="items in dealer.items" :key="items.goodsno + '_' + items.optioncode + '_' + skey">
                  <div class="product-menu-info d-flex align-items-center">
                    <div class="product-menu-info__thumbnail">
                      <product-thumbnail
                        :thumbnail-info="{
                          id:items.goodsno,
                          fullpath:items.fullpath
                        }"
                        :to="{
                          name: 'shop-detail',
                          params: { pid: items.goodscode },
                        }"
                      />
                    </div>
                    <div class="d-flex flex-column">
                      <div class="product-menu-info__thumbnail__text">
                        <p
                          class="thumbnail__text__ctg mb-0 text-black atten-new font-weight-600"
                        >
                          {{items.brandname}}
                        </p>
                      </div>
                      <div class="product-menu-info__thumbnail__text">
                        <p class="thumbnail__text__info mb-0 text-gray-700">
                          {{items.goodsname}}
                        </p>
                      </div>
                      <div>
                        <p class="thumbnail__text__info mb-0 text-gray-700">
                          {{items.optionname}}
                        </p>
                      </div>
                    </div>
                  </div>
                  <div class="product-menu-price product-menu__in-list">
                    <p class="mb-0 dp-order-product__list__p">
                      <span class="atten-new font-weight-600 text-black dp-order-product__list__span">{{$util.maskComma(items.price * items.ordcnt)}}</span>원
                    </p>
                    <p class="atten-new text-gray-600 product-menu-count__p02 mb-0" v-if="items.realmarketprice && (items.price * items.ordcnt) != items.realmarketprice">
                      {{$util.maskComma(items.realmarketprice)}}
                    </p>
                  </div>
                  <div class="product-menu-count product-menu__in-list">
                    <p class="product-menu-count__p mb-0 font-weight-500 text-black">{{items.ordcnt}}개</p>
                  </div>
                </div>
                <div class="product__list__item__total">
                  <p class="product__list__item__total__text text-gray-700 mb-0">
                    {{dealer.dealernm}}
                    <span class="text-primary ml-1" v-if="dealer.delivamt > 0">배송비 {{$util.maskComma(dealer.delivamt)}}</span>
                    <span class="text-primary ml-1" v-else>무료배송</span>
                  </p>
                </div>
              </div>
              <hr class="dp-hr dp-hr-order" />
            </div>
          </div>
        </section>
        <hr class="dp-hr h6 dp-hr-order" />
        <!-- 할인/쿠폰 -->
        <template v-if="isnonmember == 'F'">
          <section class="dp-order-panel">
            <div>
              <div
                class="order-payment-product d-flex align-items-center justify-content-between"
              >
                <div class="coupon-apply-area">
                  <p class="dp-p text-black font-weight-500 mb-0 mr-10">
                    할인/쿠폰
                  </p>
                  <p class="coupon-apply-text mb-0">※ 최대할인 기준으로 쿠폰이 자동 적용됩니다. (직접변경 가능)</p>
                </div>
                <b-button
                  class="order-payment-product-btn dp-btn dp-btn-icon btn-h30 not-hover"
                  variant="outline-gray-800"
                  pill
                  @click="couponChange"
                >
                  <span class="dp-caption text-gray-800">쿠폰 변경</span>
                  <i class="dp-icon sm icon-arrow-right"></i>
                </b-button>
              </div>
            </div>
            <div>
              <ul class="cart-price list-style-none">
                <li class="d-flex justify-content-between mb-10" v-if="totAmt.totsalepromoamt > 0">
                  <span class="cart-price-text font-weight-400 text-gray-700">{{isemp ? '임직원' : '프로모션'}}할인</span>
                  <p class="cart-price-text font-weight-500 mb-0">
                    <span class="atten-new cart-price-text font-weight-500">{{$util.maskComma((totAmt.totsalepromoamt) * -1)}}</span>
                    원
                  </p>
                </li>
                <li class="d-flex justify-content-between mb-10" v-if="totAmt.totgoodscpnamt > 0">
                  <span class="cart-price-text font-weight-400 text-gray-700">상품쿠폰</span>
                  <p class="cart-price-text font-weight-500 mb-0">
                    <span class="atten-new cart-price-text font-weight-500">{{$util.maskComma((totAmt.totgoodscpnamt) * -1)}}</span>
                    원
                  </p>
                </li>
                <li class="d-flex justify-content-between mb-10" v-if="totAmt.basketcpnamt > 0">
                  <span class="cart-price-text font-weight-400 text-gray-700">추가쿠폰</span>
                  <p class="cart-price-text font-weight-500 mb-0">
                    <span class="atten-new cart-price-text font-weight-500">{{$util.maskComma(totAmt.basketcpnamt * -1)}}</span>
                    원
                  </p>
                </li>
                <li class="d-flex justify-content-between" v-if="totAmt.totdelivcpnamt > 0">
                  <span class="cart-price-text font-weight-400 text-gray-700">배송비쿠폰</span>
                  <p class="cart-price-text font-weight-500 mb-0">
                    <span class="atten-new cart-price-text font-weight-500">{{$util.maskComma(totAmt.totdelivcpnamt * -1)}}</span>
                    원
                  </p>
                </li>
              </ul>
              <hr class="dp-hr" />
              <div class="order-payment-product_total mb-30">
                <div class="d-flex align-items-center justify-content-end">
                  <p class="fz-16 font-weight-500 text-black mb-0 mr-20">
                    할인총합
                  </p>
                  <p class="cart-price-text text-black font-weight-500 mb-0">
                    <span class="atten-new cart-price-text font-weight-500">{{$util.maskComma((totAmt.totsalepromoamt + totAmt.totgoodscpnamt + totAmt.basketcpnamt + totAmt.totdelivcpnamt) * -1)}}</span>
                    원
                  </p>
                </div>
              </div>
            </div>
            <div class="cart-price-footer" v-if="downCouponList.length > 0">
              <b-button
                class="dp-btn dp-btn-icon not-hover"
                variant="outline-gray-800"
                squared
                @click="couponDownload"
              >
                <span>쿠폰 더 받기</span>
                <i class="dp-icon sm02 icon-download"></i>
              </b-button>
            </div>
          </section>
          <hr class="dp-hr h6 dp-hr-order" />
          <!-- D포인트/적립금 -->
          <section class="dp-order-panel">
            <div class="d-flex align-items-center justify-content-between">
              <div class="order-payment-product">
                <p class="dp-p text-black font-weight-500 mb-0">적립금</p>
              </div>
            </div>
            <div class="order-payment-point">
              <ul class="list-style-none">
                <li class="point__li form-group mb-0">
                  <form class="d-flex align-items-center justify-content-between">
                    <div class="point-check">
                      <div>
                        <base-checkbox
                          :label="'적립금'"
                          :id="'pointChk1'"
                          v-model="chk.respoint"
                          :disabled="memberInfo.respoint == 0"
                          @change="changeChkPoint('respoint')"
                        />
                      </div>
                      <p class="point-check-amount font-weight-500 text-primary mb-0">
                        <span class="atten-new">{{$util.maskComma(memberInfo.respoint)}}</span>원
                      </p>
                    </div>
                    <div class="point-check-inputbox">
                    <base-input
                        :type="'number'"
                        v-model="usepoint.reservetotamt"
                        :is-disabled="memberInfo.respoint == 0"
                        @blur="blurPoint('reservetotamt')"
                        :placeholder="'0원'"
                      />
                    </div>
                  </form>
                </li>
                <!-- <li class="point__li form-group mb-0">
                  <form class="d-flex align-items-flex-start justify-content-between">
                    <div class="point-check">
                      <div>
                        <base-checkbox
                          :label="'D포인트'"
                          :id="'pointChk2'"
                          v-model="chk.epoint"
                          :disabled="memberInfo.epoint == 0"
                          @change="changeChkPoint('epoint')"
                        />
                      </div>
                      <p class="point-check-amount font-weight-500 text-primary mb-0">
                        <span class="atten-new">{{$util.maskComma(memberInfo.epoint)}}</span>P
                      </p>
                    </div>
                    <div class="point-check-inputbox">
                    <base-input
                        :type="'number'"
                        v-model="usepoint.epointtotamt"
                        :is-disabled="memberInfo.epoint == 0"
                        @blur="blurPoint('epointtotamt')"
                        :placeholder="'0P'"
                      />
                    </div>
                  </form>
                </li> -->
                <li class="point__li form-group mb-0" v-if="isemp">
                  <form class="d-flex align-items-flex-start justify-content-between">
                    <div class="point-check">
                      <div>
                        <base-checkbox
                          :label="'임직원 적립금'"
                          :id="'pointChk3'"
                          v-model="chk.emppoint"
                          :disabled="memberInfo.emppoint == 0"
                          @change="changeChkPoint('emppoint')"
                        />
                      </div>
                      <p class="point-check-amount font-weight-500 text-primary mb-0">
                        <span class="atten-new">{{$util.maskComma(memberInfo.emppoint)}}</span>원
                      </p>
                    </div>
                    <div class="point-check-inputbox">
                    <base-input
                        :type="'number'"
                        v-model="usepoint.empreservetotamt"
                        :is-disabled="memberInfo.emppoint == 0"
                        @blur="blurPoint('empreservetotamt')"
                        :placeholder="'0원'"
                      />
                    </div>
                  </form>
                </li>
              </ul>
            </div>
          </section>
          <hr class="dp-hr h6 dp-hr-order" v-if="isnonmember == 'F'" />

          <!-- 사은품 -->
          <template v-if="giftList.length > 0">
            <section class="dp-order-panel">
              <div class="order-payment-gift">
                <div class="d-flex align-items-center justify-content-between">
                  <div class="order-payment-product">
                    <p class="dp-p text-black font-weight-500 mb-0">사은품</p>
                  </div>
                </div>
                <div>
                  <template v-for="(gift, idx) in giftList">
                    <section class="dp-panel pt-0 pb-0" :key="'gift1'+idx" v-if="gift.gifttermtype == 'GFT004'">
                      <header class="order-payment-gift__title">
                        <p class="mb-20 dp-p-sm text-black font-weight-500">
                          {{gift.promoname}}
                        </p>
                      </header>
                      <div class="d-flex row">
                        <div class="order-payment-gift_item d-flex align-items-center col-4">
                          <div class="order-payment-gift__thumbnail mr-10">
                            <product-thumbnail
                              :is-checkbox="false"
                              :thumbnail-info="gift"
                            />
                          </div>
                          <div class="order-payment-gift__text">
                            <p class="dp-p-sm text-gray-700 mb-0" style="word-break:break-all;">
                              [증정] {{gift.detail[0][0].giftname}}
                            </p>
                            <p class="dp-p-sm text-gray-700 mb-0">{{gift.detail[0][0].rcvamt}}개</p>
                          </div>
                        </div>
                      </div>
                    </section>

                    <section class="mt-50" :key="'gift2'+idx" v-else>
                      <header class="order-payment-gift__first__title mb-20">
                        <p class="dp-p-sm text-black font-weight-500 mb-0">
                          {{gift.promoname}} (최대 {{gift.giftselcnt}}개 선택)
                        </p>
                        <p class="gift__first__title-dot dp-caption text-gray-700 font-weight-400 mb-0">
                          사은품은 최대 {{gift.giftselcnt}}개 까지 선택 가능합니다.
                        </p>
                      </header>

                      <div class="order-payment-gift__first__product d-flex row" v-for="(goods, idx2) in gift.detail" :key="idx2">
                        <div class="mr-30 d-flex align-items-center col-4" v-for="(good, idx3) in goods" :key="idx3">
                          <div class="order-payment-gift__thumbnail mr-10">
                            <product-thumbnail
                              :is-checkbox="true"
                              :id="good.id"
                              :thumbnail-info="good"
                              v-model="good.chk"
                              @change="changeGiftChk(gift, good)"
                              :key="good.id +'_' + good.chk"
                              style="width:155px"
                            />
                          </div>
                          <div class="order-payment-gift__text">
                            <p class="dp-p-sm text-gray-700 mb-0" style="word-break:break-all;">
                              [선택{{(idx2+1)}}] {{good.giftname}}
                            </p>
                            <p class="dp-p-sm text-gray-700 mb-0">{{good.rcvamt}}개</p>
                          </div>
                        </div>
                      </div>
                    </section>
                  </template>
                </div>
              </div>
            </section>
            <hr class="dp-hr h6 dp-hr-order"/>
          </template>
        </template>
        <!-- 결제수단 -->
        <section class="dp-order-panel">
          <div class="order-payment-method">
            <div class="d-flex align-items-center justify-content-between" v-if="totAmt.rpaytotprice > 0">
              <div class="order-payment-product">
                <p class="dp-p text-black font-weight-500 mb-0">결제수단</p>
              </div>
            </div>
            <div>
              <div v-if="totAmt.rpaytotprice > 0">
                <div class="method__box">
                  <ul class="list-style-none method__ul">
                    <li v-for="(payway, index) in paywayList" :key="index">
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
              </div>
              <div class="dp-panel method-card-panel" v-if="totAmt.rpaytotprice > 0 && payInfo.paywaytype == 'PWT001'">
                <div class="method-card">
                  <form>
                    <div class="form-group">
                      <base-dropdown
                        class="btn-h48"
                        placeholder="카드를 선택해주세요"
                        :options="cardList"
                        v-model="payInfo.cardCompany"
                      />
                    </div>
                    <div>
                      <div class="form-group">
                        <base-dropdown
                          class="btn-h48"
                          v-model="payInfo.cardInstallmentPlan"
                          :is-disabled="totAmt.rpaytotprice < 50000"
                          :options="cardPlan"
                          placeholder="일시불"
                        />
                      </div>
                    </div>
                  </form>
                </div>
              </div>
              <div class="dp-panel method-bank-transfer-panel" :class="{'mt-0':totAmt.rpaytotprice == 0}">
                <div class="method-bank-transfer">
                  <div class="method-bank-transfer__checkbox" v-if="totAmt.rpaytotprice > 0 && (payInfo.paywaytype == 'PWT002' || payInfo.paywaytype == 'PWT003')">
                    <base-checkbox
                      label="구매안전(에스크로) 서비스 신청"
                      v-model="payInfo.useEscrow"
                      :id="'useEscrow'"
                    />
                  </div>
                  <template v-if="payInfo.paywaytype == 'PWT002' || payInfo.paywaytype == 'PWT003' || payInfo.paywaytype == 'PWT005' || usepoint.empreservetotamt > 0">
                    <header class="method-bank-transfer__title">
                      <h1 class="mb-10 dp-p text-black font-weight-500">
                        현금영수증
                      </h1>
                    </header>

                    <ul class="method-bank-transfer__radio__list d-flex list-style-none">
                      <li v-for="(list, index) in cashReceipt" :key="index">
                        <base-radio
                          type="radio"
                          v-model="cashReceiptType.cashreceipttype"
                          :id="list.id"
                          :name="'cashReceiptType'"
                          :label="list.label"
                          :val="list.label"
                          @change="changeCashReceiptType"
                        />
                      </li>
                    </ul>
                    <div class="method-bank-transfer__radio-select pt-0" v-show="cashReceiptType.selected != ''">
                      <form class="method-bank-transfer__input d-flex align-items-center method-account__input">
                        <div class="form-group mb-0">
                          <base-dropdown
                            :key='skey'
                            class="btn-h48"
                            placeholder=""
                            v-model="cashReceiptType.selected"
                            :options="cashReceiptType.type"
                            :is-disabled="cashReceiptType.isDisabled"
                          />
                        </div>
                        <div class="method-account__input__text">
                          <div>
                            <base-input
                              class="btn-h48"
                              type="tel"
                              placeholder="-없이 숫자만 입력"
                              v-model="cashReceiptType.registrationNumber"
                            />
                          </div>
                        </div>
                      </form>
                    </div>
                  </template>
                </div>
              </div>
            </div>
          </div>
        </section>
      </template>
      <template #right-contents>
        <div class="dp-cart__btn-group">
          <div class="dp-cart__price-summary">
            <header class="dp-cart__price-header">
              <p class="fz-20 font-weight-500 text-black mb-0">결제금액</p>
            </header>
            <hr class="dp-hr dp-hr-receipt" />
            <ul class="dp-cart__price-summary-list list-style-none">
              <li class="d-flex justify-content-between mb-10">
                <div class="d-flex align-items-center">
                  <span class="dp-order-price__text font-weight-400 text-gray-700">총 주문금액</span>
                </div>
                <p class="dp-order-price__text font-weight-500 mb-0">
                  <span class="dp-order-price__text-lg atten-new dp-p font-weight-500" >{{$util.maskComma(totAmt.ordtotprice)}}</span>원
                </p>
              </li>
              <li class="d-flex justify-content-between mb-10">
                <div class="d-flex align-items-center">
                  <span class="dp-order-price__text font-weight-400 text-gray-700">배송비</span>
                  <div class="delivery-only" v-if="totAmt.totdelivamt > 0">
                    <b-button
                      class="delivery-only-btn dp-btn not-hover"
                      variant="outline-gray-800"
                      pill
                      @click="delivDetail"
                    >
                      <span class="delivery-only-btn__text">배송비 상세</span>
                      <i class="dp-icon sm icon-arrow-right"></i>
                    </b-button>
                  </div>
                </div>
                <p class="dp-order-price__text font-weight-500 mb-0">
                  <template v-if="totAmt.totdelivamt == 0">무료배송</template>
                  <template v-else>
                    <span class="dp-order-price__text-lg atten-new dp-p font-weight-500">{{$util.maskComma(totAmt.totdelivamt)}}</span>원
                  </template>
                </p>
              </li>
              <li class="d-flex justify-content-between mb-10" v-if="totAmt.totcpnamt > 0">
                <div class="d-flex align-items-center">
                  <span class="dp-order-price__text font-weight-400 text-gray-700">할인/쿠폰적용</span>
                </div>
                <p class="dp-order-price__text font-weight-500 mb-0">
                  <span class="dp-order-price__text-lg atten-new dp-p font-weight-500" >{{$util.maskComma(totAmt.totcpnamt * -1)}}</span>원
                </p>
              </li>
              <template v-if="isnonmember == 'F'">
                <li class="d-flex justify-content-between mb-10" v-if="usepoint.reservetotamt > 0">
                  <div class="d-flex align-items-center">
                    <span class="dp-order-price__text font-weight-400 text-gray-700">적립금사용</span>
                  </div>
                  <p class="dp-order-price__text font-weight-500 mb-0">
                    <span class="dp-order-price__text-lg atten-new dp-p font-weight-500" >{{$util.maskComma(usepoint.reservetotamt * -1)}}</span>원
                  </p>
                </li>
                <li class="d-flex justify-content-between mb-10" v-if="usepoint.epointtotamt > 0">
                  <div class="d-flex align-items-center">
                    <span class="dp-order-price__text font-weight-400 text-gray-700">D포인트 사용</span>
                  </div>
                  <p class="dp-order-price__text font-weight-500 mb-0">
                    <span class="dp-order-price__text-lg atten-new dp-p font-weight-500" >{{$util.maskComma(usepoint.epointtotamt * -1)}}</span>P
                  </p>
                </li>
                <li class="d-flex justify-content-between mb-10" v-if="usepoint.empreservetotamt > 0">
                  <div class="d-flex align-items-center">
                    <span class="dp-order-price__text font-weight-400 text-gray-700">임직원적립금 사용</span>
                  </div>
                  <p class="dp-order-price__text font-weight-500 mb-0">
                    <span class="dp-order-price__text-lg atten-new dp-p font-weight-500" >{{$util.maskComma(usepoint.empreservetotamt * -1)}}</span>원
                  </p>
                </li>
              </template>
            </ul>
            <hr class="dp-hr mb-0" />
            <footer class="dp-cart__price-footer">
              <div class="d-flex justify-content-between">
                <span class="footer-summary__text text-black font-weight-500">최종결제금액</span>
                <p class="footer-summary__price mb-0 font-weight-700 text-black dp-title">
                  <span class="atten-new">{{$util.maskComma(totAmt.rpaytotprice)}}</span> 원
                </p>
              </div>
              <div class="footer-summary__text-primary">
                <p class="mb-0 footer-summary__price-primary text-primary">
                  {{$util.maskComma(reserveAmt)}}원 적립예정
                </p>
              </div>
            </footer>
            <hr class="dp-hr mb-0" />
            <section class="order-payment-agreement">
              <div class="d-flex align-items-center justify-content-between">
                <div class="order-payment-product__agree">
                  <base-checkbox
                    label="이용약관 모두 동의"
                    v-model="agreeTerm.allagree"
                    :id="'allAgreeTerm'"
                    name="allAgreeTerm"
                    @change="checkAllAgree"
                  />
                </div>
                <div class="order-payment-check d-flex align-items-center"
                  v-b-toggle:agree>
                  <i class="dp-icon icon-arrow-down sm02"></i>
                </div>
              </div>
              <b-collapse id="agree" accordion="agree-accordion" tag="div">
              <div class="order-payment-agreement__list">
                <div class="order-payment-agreement-header">
                  <p class="order-payment-agreement-header__text mb-0 text-black font-weight-500">
                    주문상품 정보(상품, 가격, 배송정보) 동의
                  </p>
                </div>
                <ul class="order-payment-agreement__list__ul list-style-none">
                  <li class="d-flex align-items-center justify-content-between mb-10" v-for="(termlist,index) in  termList" :key="index">
                    
                    <span class="agreement-text"
                        >{{ termlist.termstypename}}</span
                      >
                    <i @click="showModal('termsModal', { termstype: termlist.termstype })">
                      <img
                        class="more-icon"
                        src="@/assets/common/icon/icon-more-black-16px.svg"
                        alt="더보기 아이콘"
                      />
                    </i>
                  </li>
                  <!-- <li
                    class="d-flex align-items-center justify-content-between mb-10"
                  >
                    <base-checkbox
                      label="개인정보 제공 및 위탁 동의"
                      v-model="agreeTerm.agree[1]"
                      :id="'payagree2'"
                      name="payagree"
                      @change="changeAgree"
                    />
                    <i @click="handleTermsModal">
                      <img
                        class="more-icon"
                        src="@/assets/common/icon/icon-more-black-16px.svg"
                        alt="더보기 아이콘"
                      />
                    </i>
                  </li>
                  <li class="d-flex align-items-center justify-content-between">
                    <base-checkbox
                      label="결제대행 서비스 이용약관 동의"
                      v-model="agreeTerm.agree[2]"
                      :id="'payagree3'"
                      name="payagree"
                      @change="changeAgree"
                    />
                    <i @click="handleTermsModal">
                      <img
                        class="more-icon"
                        src="@/assets/common/icon/icon-more-black-16px.svg"
                        alt="더보기 아이콘"
                      />
                    </i>
                  </li> -->
                </ul>
              </div>
              </b-collapse>
            </section>
          </div>
          <div class="btn-group">
            <b-button
              class="dp-btn btn-h48"
              variant="gray-800"
              squared
              @click="orderPayment"
            >
            <span class="btn-group-text font-weight-400" v-if="totAmt.rpaytotprice > 0">{{$util.maskComma(totAmt.rpaytotprice)}}원 결제하기</span>
            <span class="btn-group-text font-weight-400" v-else>주문하기</span>
            </b-button>
          </div>
        </div>
      </template>
    </layout-right>


  </main>
</template>

<script src="@views.mobile/order/Index.js">
</script>

<style scope>
.mt-0 {
  padding-top : 0px !important;
}
html {
  overflow: initial !important;
}

input[type="number"]::-webkit-outer-spin-button,
input[type="number"]::-webkit-inner-spin-button {
    -webkit-appearance: none;
    margin: 0;
}
</style>