<template>
  <main class="order-payment">
    <div class="container">
      <!-- 비회원일경우 -->
      <template v-if="isnonmember == 'T'">
        <!-- 비회원 개인정보 수집 및 활용동의 -->
        <section class="order-payment-section">
            <div class="d-flex align-items-center justify-content-between pt-pb-20">
            <div class="order-payment-product">
                <p class="dp-p text-black font-weight-500 mb-0">
                비회원 개인정보 수집 및 활용동의
                </p>
            </div>
            <div class="order-payment-check">
                <i class="dp-icon icon-arrow-down sm02" v-b-toggle.my-collapse-non-member></i>
            </div>
            </div>
            <!-- 개인정보 토글 콜랩스 -->
            <b-collapse
            visible
            id="my-collapse-non-member"
            class="my-collapse-non-member"
            >
            <section class="dp-panel">
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
            </section>
            </b-collapse>
        </section>

        <hr class="dp-hr justify-margin" />
        <!-- // 비회원일 경우 -->

        <!-- 주문자 정보 -->
        <section class="order-payment-section">
          <div
          class="d-flex align-items-center justify-content-between pt-pb-20"
          >
            <div class="order-payment-product">
                <p class="dp-p text-black font-weight-500 mb-0">주문자 정보</p>
            </div>
            <div class="order-payment-check">
                <i
                class="dp-icon icon-arrow-down sm02"
                v-b-toggle.my-collapse-order-info
                ></i>
            </div>
          </div>
          <!-- 주문자 정보 토글 콜랩스 -->
          <b-collapse
          id="my-collapse-order-info"
          class="my-collapse-order-info"
          >
            <section class="dp-panel">
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
                      :autocomplete="'one-time-code'"
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
                    >확인
                    </b-button>
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
            </section>
          </b-collapse>
        </section>

        <hr class="dp-hr justify-margin" />

        <!-- 배송지 정보 -->
        <section class="order-payment-section">
            <div
            class="d-flex align-items-center justify-content-between pt-pb-20"
            >
            <div class="order-payment-product">
                <p class="dp-p text-black font-weight-500 mb-0">배송지 정보</p>
            </div>
            <div class="order-payment-check">
                <i
                class="dp-icon icon-arrow-down sm02"
                v-b-toggle.my-collapse-shipping-info
                ></i>
            </div>
            </div>
            <!-- 배송지 토글 콜랩스 -->
            <b-collapse
            id="my-collapse-shipping-info"
            class="my-collapse-shipping-info"
            >
            <section class="dp-panel">
                <form class="form-area">
                <base-checkbox
                    v-model="nonMemberInfo.isSame"
                    label="주문자 정보와 동일"
                    class="order-info-checkbox"
                    @change="sameOrder"
                />
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
                <div class="form-group d-flex">
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

                <!-- <base-checkbox
                    v-model="nonMemberInfo.isSame"
                    label="주문자 정보와 동일"
                    class="order-info-checkbox"
                    @change="sameOrder"
                /> -->

                <div class="order-payment-drop">
                    <base-select
                      v-model="rcvreqtype"
                      :options="reqList"
                      @change="reqtypeChange"
                      placeholder="배송 요청사항 (선택)"
                    />
                </div>
                <div class="order-payment-drop pt-10" v-if="directinput">
                  <base-input
                    v-model="rcvreqdetail"
                    placeholder="배송요청사항을 입력해주세요"
                  />
                </div>
                </form>
            </section>
            </b-collapse>
        </section>

        <hr class="dp-hr justify-margin" />
      </template>
      <template v-else>
        <!-- 배송지 입력 -->
        <section class="dp-panel delivery-write">
          <!-- 배송지 입력 헤더 -->
          <header class="order-payment-header d-flex align-items-center justify-content-between">
            <div class="payment-header-title">
              <h2 class="dp-p text-black font-weight-500 mb-0">배송지</h2>
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
                <p class="dp-p text-black font-weight-500 mb-0">{{addr.rcvname}}</p>
              </div>
            </div> -->
            <div class="order-payment-address mb-20">
              <p class="order-payment-address-text mb-0 text-gray-700 font-weight-400">
                배송지를 추가해주세요.
              </p>
            </div>
          </div>
          <div class="order-payment-body" v-else>
            <div class="d-flex align-items-center mb-10">
              <div class="order-payment-orderer">
                <p class="dp-p text-black font-weight-500 mb-0">{{addr.rcvname}}</p>
              </div>
              <div class="order-payment-badge" v-if="addr.isdefault=='T'">
                <span class="dp-badge lh-1">기본 배송지</span>
              </div>
            </div>
            <div class="order-payment-address mb-20">
              <p class="order-payment-address-text mb-0 text-gray-700 font-weight-400">
                우{{addr.rcvpost}} / {{addr.rcvaddrroad}} {{addr.rcvaddrdetailroad}}
              </p>
              <p
                class="payment-address-phone mb-0 dp-p-sm text-gray-700 font-weight-400"
              >
                {{$util.maskTel(addr.rcvtel1)}}
              </p>
            </div>

            <div class="order-payment-form">
              <form>
                <div class="order-payment-drop">
                  <base-select
                    v-model="rcvreqtype"
                    :options="reqList"
                    @change="reqtypeChange"
                    placeholder="배송 요청사항 (선택)"
                  />
                </div>
                <div class="order-payment-drop pt-10" v-if="directinput">
                  <base-input
                    v-model="rcvreqdetail"
                    placeholder="배송요청사항을 입력해주세요"
                  />
                </div>
              </form>
            </div>
          </div>
        </section>
        <hr class="dp-hr justify-margin" />
      </template>
      <!-- 주문 상품 -->
      <section class="order-payment-product-wrap pt-pb-20">
        <div class="d-flex align-items-center justify-content-between">
          <div class="order-payment-product">
            <p class="dp-p text-black font-weight-500 mb-0">주문상품</p>
          </div>
          <div class="order-payment-check d-flex align-items-center">
            <div class="check-text-wrap">
              <span class="order-payment-title dp-p-sm text-black font-weight-400" v-if="orderlist.length > 0" >{{orderlist[0].goodsname}} &nbsp;</span>
            </div>
            <span class="dp-p-sm text-black font-weight-400" v-if="orderlist.length > 1"> 외 {{orderlist.length - 1}}건</span>
            <i class="dp-icon icon-arrow-down sm02" v-b-toggle.my-collapse></i>
          </div>
        </div>
      </section>
      <!-- 상품 토글 콜랩스 -->
      <b-collapse id="my-collapse">
        <template v-for="(dealer, index) in dealers">
          <section class="dp-panel" :key="'item_' + index">
            <ul class="cart-product cart-product-sale collapse-section02 list-style-none">
              <li
                class="cart-product-sale__list"
                v-for="(items, index2) in dealer.items" :key="index2 + '_' + skey"
              >
                <product-order
                  :is-status="false"
                  :is-badge="false"
                  :is-order="true"
                  :product-info="items"
                  :is-footer="false"
                />
              </li>
            </ul>

            <div class="cart-caption">
              <p class="dp-caption mb-0 text-gray-700 font-weight-400">
                {{dealer.dealernm}}
                <span class="text-primary ml-1" v-if="dealer.delivamt > 0">배송비 {{$util.maskComma(dealer.delivamt)}}</span>
                <span class="text-primary ml-1" v-else>무료배송</span>
              </p>
            </div>
          </section>
          <hr class="dp-hr justify-margin" :key="'line_' + index"/>
        </template>
      </b-collapse>

      <hr class="dp-hr justify-margin" />
      <template v-if="isnonmember == 'F'">
        <!-- 할인 / 쿠폰 -->
        <section class="order-payment-product-wrap pt-pb-20">
          <div class="d-flex align-items-center justify-content-between">
            <div class="order-payment-product d-flex align-items-center">
              <p class="dp-p text-black font-weight-500 mb-0 mr-10">할인/쿠폰</p>
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
            <div class="order-payment-check d-flex align-items-center">
              <p class="dp-p text-primary font-weight-500 mb-0">
                <span class="atten-new mr01">{{$util.maskComma((totAmt.totsalepromoamt + totAmt.totgoodscpnamt + totAmt.basketcpnamt + totAmt.totdelivcpnamt) * -1)}}</span>원
              </p>
              <i
                class="dp-icon icon-arrow-down sm02"
                v-b-toggle.my-collapse-sale
              ></i>
            </div>
          </div>
        </section>
        <!-- 할인 토글 콜랩스 -->
        <b-collapse id="my-collapse-sale">
          <p class="dp-caption text-gray-700">
            ※ 최대할인 기준으로 쿠폰이 자동 적용됩니다. (직접변경 가능)
          </p>
          <!--      결제 예정 금액     -->
          <section class="dp-panel pt-0">
            <ul class="cart-price list-style-none">
              <li class="d-flex justify-content-between mb-10" v-if="totAmt.totsalepromoamt > 0">
                <span class="dp-caption font-weight-400 text-gray-700">{{isemp ? '임직원' : '프로모션'}}할인</span>
                <p class="dp-caption font-weight-500 mb-0">
                  <span class="atten-new dp-p font-weight-500">{{$util.maskComma((totAmt.totsalepromoamt) * -1)}}</span> 원
                </p>
              </li>
              <li class="d-flex justify-content-between mb-10" v-if="totAmt.totgoodscpnamt > 0">
                <span class="dp-caption font-weight-400 text-gray-700">상품쿠폰</span>
                <p class="dp-caption font-weight-500 mb-0">
                  <span class="atten-new dp-p font-weight-500">{{$util.maskComma((totAmt.totgoodscpnamt) * -1)}}</span> 원
                </p>
              </li>
              <li class="d-flex justify-content-between mb-10" v-if="totAmt.basketcpnamt > 0">
                <span class="dp-caption font-weight-400 text-gray-700">추가쿠폰</span>
                <p class="dp-caption font-weight-500 mb-0">
                  <span class="atten-new dp-p font-weight-500">{{$util.maskComma(totAmt.basketcpnamt * -1)}}</span> 원
                </p>
              </li>
              <li class="d-flex justify-content-between" v-if="totAmt.totdelivcpnamt > 0">
                <span class="dp-caption font-weight-400 text-gray-700">배송비쿠폰</span>
                <p class="dp-caption font-weight-500 mb-0">
                  <span class="atten-new dp-p font-weight-500">{{$util.maskComma(totAmt.totdelivcpnamt * -1)}}</span> 원
                </p>
              </li>
            </ul>
            <div class="cart-price-footer" v-if="isnonmember == 'T' || downCouponList.length > 0">
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
        </b-collapse>
        <hr class="dp-hr justify-margin" />
        <!-- D포인트 / 적립금 -->
        <section class="order-payment-point pt-pb-20">
          <div class="d-flex align-items-center justify-content-between">
            <div class="order-payment-product">
              <p class="dp-p text-black font-weight-500 mb-0">적립금</p>
            </div>
            <div class="order-payment-check d-flex align-items-center">
              <p class="dp-p text-primary font-weight-500 mb-0">
                <span class="atten-new mr01">{{$util.maskComma(Number(usepoint.reservetotamt) + Number(usepoint.epointtotamt) + Number(usepoint.empreservetotamt))}}</span>원
              </p>
              <i
                class="dp-icon icon-arrow-down sm02"
                v-b-toggle.my-collapse-point
              ></i>
            </div>
          </div>
          <!-- D포인트 / 적립금 토글 콜랩스 -->
          <b-collapse id="my-collapse-point">
            <section class="dp-panel">
              <ul class="list-style-none">
                <li class="form-group">
                  <form class="d-flex align-items-flex-start">
                    <div class="point-check d-flex flex-column">
                      <div>
                        <base-checkbox
                          :label="'적립금'"
                          :id="'pointChk1'"
                          v-model="chk.respoint"
                          :disabled="memberInfo.respoint == 0"
                          @change="changeChkPoint('respoint')"
                        />
                      </div>
                      <p class="point-check-amount dp-p-sm font-weight-500 text-primary mb-0">
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
                <!-- <li class="form-group">
                  <form class="d-flex align-items-flex-start">
                    <div class="point-check d-flex flex-column">
                      <div>
                        <base-checkbox
                          :label="'D포인트'"
                          :id="'pointChk2'"
                          v-model="chk.epoint"
                          :disabled="memberInfo.epoint == 0"
                          @change="changeChkPoint('epoint')"
                        />
                      </div>
                      <p class="point-check-amount dp-p-sm font-weight-500 text-primary mb-0">
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
                <li class="form-group" v-if="isemp">
                  <form class="d-flex align-items-flex-start">
                    <div class="point-check d-flex flex-column">
                      <div>
                        <base-checkbox
                          :label="'임직원 적립금'"
                          :id="'pointChk3'"
                          :value="true"
                          v-model="chk.emppoint"
                          :disabled="memberInfo.emppoint == 0"
                          @change="changeChkPoint('emppoint')"
                        />
                      </div>
                      <p class="point-check-amount dp-p-sm font-weight-500 text-primary mb-0">
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
            </section>
          </b-collapse>
        </section>
        <hr class="dp-hr justify-margin"/>
        <!-- 사은품 -->
        <template v-if="giftList.length > 0">
          <section class="order-payment-gift">
            <div class="d-flex align-items-center justify-content-between">
              <div class="order-payment-product">
                <p class="dp-p text-black font-weight-500 mb-0">사은품</p>
              </div>
              <div class="order-payment-check">
                <i
                  class="dp-icon icon-arrow-down sm02"
                  v-b-toggle.my-collapse-gift
                ></i>
              </div>
            </div>
            <!-- 사은품 토글 콜랩스 -->
            <b-collapse id="my-collapse-gift"  class="my-collapse-gift">
              <template v-for="(gift, idx) in giftList">
                <section class="dp-panel pt-20" :key="'gift1' + idx" v-if="gift.gifttermtype == 'GFT004'">
                  <header class="order-payment-gift__title">
                    <p class="mb-20 dp-p-sm text-black font-weight-500">
                      {{gift.promoname}}
                    </p>
                  </header>
                  <!-- todo: (개발) 0422 태그변경 -->
                  <ul class="gift-flex-ul row list-style-none">
                    <li class="col-6 col-xl-4">
                      <div class="order-payment-gift__thumbnail mb-10">
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
                    </li>
                  </ul>
                  <!-- // (개발) 0422 태그변경 -->
                </section>
                <section class="dp-panel pt-0" :key="'gift1' + idx" v-else>
                  <header class="order-payment-gift__first__title mb-20">
                    <p class="dp-p-sm text-black font-weight-500 mb-0">
                      {{gift.promoname}} (최대 {{gift.giftselcnt}}개 선택)
                    </p>
                    <p class="gift__first__title-dot dp-caption text-gray-700 font-weight-400 mb-0">
                      사은품은 최대 {{gift.giftselcnt}}개 까지 선택 가능합니다.
                    </p>
                  </header>
                  
                  <div class="order-payment-gift__first__product">
                    <div class="d-flex flex-column align-items-flex-start" v-for="(goods, idx2) in gift.detail" :key="idx2">
                      <ul class="gift-flex-ul row list-style-none">
                        <li class="col-6 col-xl-4" v-for="(good, idx3) in goods" :key="idx3" >
                          <div class="order-payment-gift__thumbnail mb-10">
                          <product-thumbnail
                              :is-checkbox="true"
                              :id="good.id"
                              :thumbnail-info="good"
                              v-model="good.chk"
                              @change="changeGiftChk(gift,good)"
                              :key="good.id +'_' + good.chk"
                            />
                          </div>
                          <div class="order-payment-gift__text">
                            <p class="dp-p-sm text-gray-700 mb-0" style="word-break:break-all;">
                              [선택{{idx2}}] {{good.giftname}}
                            </p>
                            <p class="dp-p-sm text-gray-700 mb-0">{{good.rcvamt}}개</p>
                          </div>
                        </li>
                      </ul>
                    </div>
                  </div>
                </section>
              </template>
            </b-collapse>
          </section>
          <hr class="dp-hr justify-margin"/>
        </template>
      </template>
      <!-- 결제 수단 -->
      <section class="order-payment-method pt-pb-20">
        <div class="d-flex align-items-center justify-content-between">
          <div class="order-payment-product">
            <p class="dp-p text-black font-weight-500 mb-0">결제수단</p>
          </div>
          <div class="order-payment-check d-flex align-items-center">
            <p class="dp-p text-primary font-weight-500 mb-0" v-if="totAmt.rpaytotprice > 0">{{paywayName}}</p>
            <i
              class="dp-icon icon-arrow-down sm02"
              v-b-toggle.my-collapse-method
            ></i>
          </div>
        </div>
        <!-- 결제 토글 콜랩스 -->
        <b-collapse id="my-collapse-method">
          <section class="dp-panel" v-if="totAmt.rpaytotprice > 0">
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
          </section>
          <div class="dp-panel method-card-panel" v-if="totAmt.rpaytotprice > 0 && payInfo.paywaytype == 'PWT001'">
            <div class="method-card">
              <form>
                <div class="form-group">
                  <base-select
                    placeholder="카드를 선택해주세요"
                    :options="cardList"
                    v-model="payInfo.cardCompany"
                  />
                </div>
                <div>
                  <div class="form-group">
                    <base-select 
                    v-model="payInfo.cardInstallmentPlan"
                    :is-disabled="totAmt.rpaytotprice < 50000"
                    :options="cardPlan"
                    placeholder="일시불"/>
                  </div>
                </div>
              </form>
            </div>

            <!-- <div>
              <div class="method-card-alert d-flex align-items-center justify-content-center" >
                <p class="mb-0 text-black dp-p-sm font-weight-400">
                  현대카드 7% 결제일 할인
                </p>
              </div>
            </div> -->
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
                <div class="method-bank-transfer__radio-select" v-show="cashReceiptType.selected != ''">
                  <form class="method-bank-transfer__input">
                    <div class="form-group">
                      <base-select 
                        placeholder="휴대폰 번호"
                        v-model="cashReceiptType.selected"
                        :options="cashReceiptType.type"
                        :is-disabled="cashReceiptType.isDisabled"/>
                    </div>
                    <div>
                      <div>
                        <base-input
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
        </b-collapse>
      </section>
      <hr class="dp-hr justify-margin" />

      <!-- 최종 결제 금액 -->
      <section class="order-payment-price pt-pb-20">
        <div class="d-flex align-items-center justify-content-between">
          <div class="order-payment-product">
            <p class="dp-p text-black font-weight-500 mb-0">최종결제금액</p>
          </div>
          <div class="order-payment-check d-flex align-items-center">
            <p class="dp-p text-primary font-weight-500 mb-0">
              <span class="atten-new mr01">{{$util.maskComma(totAmt.rpaytotprice)}}</span>원
            </p>
            <i
              class="dp-icon icon-arrow-down sm02"
              v-b-toggle.my-collapse-price
            ></i>
          </div>
        </div>
        <!-- 결제 금액 토글 콜랩스 -->
        <b-collapse id="my-collapse-price">
          <section class="dp-panel">
            <ul class="order-payment-price list-style-none">
              <li class="d-flex justify-content-between mb-10">
                <div class="d-flex align-items-center">
                  <span class="dp-caption font-weight-400 text-gray-700">총 주문금액</span>
                </div>
                <p class="dp-caption font-weight-500 mb-0">
                  <span class="atten-new dp-p font-weight-500">{{$util.maskComma(totAmt.ordtotprice)}}</span>원
                </p>
              </li>
              <li class="d-flex justify-content-between mb-10">
                <div class="d-flex align-items-center">
                  <span class="dp-caption font-weight-400 text-gray-700">배송비</span>
                  <div class="delivery-only" v-if="totAmt.totdelivamt > 0">
                    <b-button
                      class="delivery-only-btn dp-btn btn-h30 not-hover"
                      variant="outline-gray-800"
                      pill
                      @click="delivDetail"
                    >
                      <span>배송비 상세</span>
                      <i class="dp-icon sm icon-arrow-right"></i>
                    </b-button>
                  </div>
                </div>

                <p class="dp-caption font-weight-500 mb-0">
                  <template v-if="totAmt.totdelivamt == 0">무료배송</template>
                  <template v-else>
                    <span class="atten-new dp-p font-weight-500">{{$util.maskComma(totAmt.totdelivamt)}}</span>원
                  </template>
                </p>
              </li>
              <li class="d-flex justify-content-between mb-10" v-if="totAmt.totcpnamt > 0">
                <div class="d-flex align-items-center">
                  <span class="dp-caption font-weight-400 text-gray-700">할인/쿠폰</span>
                </div>
                <p class="dp-caption font-weight-500 mb-0">
                  <span class="atten-new dp-p font-weight-500">{{$util.maskComma(totAmt.totcpnamt * -1)}}</span>원
                </p>
              </li>
              <template v-if="isnonmember == 'F'">
                <li class="d-flex justify-content-between mb-10" v-if="usepoint.reservetotamt > 0">
                  <div class="d-flex align-items-center">
                    <span class="dp-caption font-weight-400 text-gray-700">적립금사용</span>
                  </div>
                  <p class="dp-caption font-weight-500 mb-0">
                    <span class="atten-new dp-p font-weight-500">{{$util.maskComma(usepoint.reservetotamt * -1)}}</span>원
                  </p>
                </li>
                <li class="d-flex justify-content-between mb-10" v-if="usepoint.epointtotamt > 0">
                  <div class="d-flex align-items-center">
                    <span class="dp-caption font-weight-400 text-gray-700">D포인트 사용</span>
                  </div>
                  <p class="dp-caption font-weight-500 mb-0">
                    <span class="atten-new dp-p font-weight-500">{{$util.maskComma(usepoint.epointtotamt * -1)}}</span>P
                  </p>
                </li>
                <li class="d-flex justify-content-between mb-10" v-if="usepoint.empreservetotamt > 0">
                  <div class="d-flex align-items-center">
                    <span class="dp-caption font-weight-400 text-gray-700">임직원적립금 사용</span>
                  </div>
                  <p class="dp-caption font-weight-500 mb-0">
                    <span class="atten-new dp-p font-weight-500">{{$util.maskComma(usepoint.empreservetotamt * -1)}}</span>원
                  </p>
                </li>
              </template>
            </ul>
            <hr class="dp-hr h1" />
            <div class="order-payment-price-footer-wrap">
              <div class="order-payment-price-footer d-flex justify-content-between">
                <span class="order-payment-price-footer__text dp-p text-black font-weight-700">최종결제금액</span>
                <p class="order-payment-price-footer__price font-weight-700 text-black dp-title">
                  <span class="atten-new">{{$util.maskComma(totAmt.rpaytotprice)}}</span> 원
                </p>
              </div>
              <div class="order-payment-price-footer__save">
                <p class="dp-caption text-primary font-weight-400 mb-0">
                  {{$util.maskComma(reserveAmt)}}원 적립예정
                </p>
              </div>
            </div>
          </section>
        </b-collapse>
      </section>
      <hr class="dp-hr justify-margin" />

      <!-- 이용약관 동의 -->
      <section class="order-payment-agreement dp-panel" style="margin-bottom:60px;">
        <div class="d-flex align-items-center justify-content-between">
          <div class="order-payment-product">
            <base-checkbox
              label="이용약관 모두 동의"
              v-model="agreeTerm.allagree"
              :id="'allAgreeTerm'"
              name="allAgreeTerm"
              @change="checkAllAgree"
            />
          </div>
          <div class="order-payment-check d-flex align-items-center">
            <i class="dp-icon icon-arrow-down sm02" v-b-toggle.my-collapse-agreement></i>
          </div>
        </div>
        <b-collapse id="my-collapse-agreement">
          <section class="dp-panel order-payment-agreement__agree">
            <div class="order-payment-agreement-header">
              <p class="mb-0 dp-p-sm text-black font-weight-500">
                주문상품 정보(상품, 가격, 배송정보) 동의
              </p>
            </div>
            <ul class="list-style-none">
              <li class="d-flex align-items-center justify-content-between mb-10" v-for="(termlist,index) in  termList" :key="index">
                
                <span class="dp-p-sm text-gray-700"
                  >{{ termlist.termstypename }}</span
                >
                <i @click="showModal('termsModal', { termstype: termlist.termstype })">
                  <img
                    class="more-icon"
                    src="@/assets/common/icon/icon-more-black-16px.svg"
                    alt="더보기 아이콘"
                  />
                </i>
              </li>
              <!--
              <li class="d-flex align-items-center justify-content-between mb-10">
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
              </li>
              -->
            </ul>
          </section>
        </b-collapse>
      </section>
      <section class="order-payment-btn dp-panel order-payment-agreement__buy">
        <b-button class="dp-btn text-white" variant="gray-800" squared @click="orderPayment">
          <span
            v-if="totAmt.rpaytotprice > 0"
            style="font-size: 20px;"
          ><span class="vollkorn" style="font-size: 26px;">{{$util.maskComma(totAmt.rpaytotprice)}}</span>원 결제하기</span>
          <span v-else>주문하기</span>
        </b-button>
      </section>
    </div>
  </main>
</template>

<script src="./Index.js">
</script>

<style scope>
.mt-0 {
  padding-top : 0px !important;
}
.base-input-horizon .input-label {
  padding-top : 10px;
}
</style>