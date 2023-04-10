<template>
  <main class="dp-mypage-cancel-apply">
    <div class="container">
      <section class="apply__product-list">
        <ul class="list-style-none apply__product-list__ul">
          <li v-for="(item,index) in dispitems" :key="index">
            <product-order
              :product-info="item"
              :is-header="true"
              :is-status="false"
              :is-badge="false"
              :is-summary="false"
              :is-summary2="true"
              :is-select-count="true"
              :is-exchange="claimType=='CLM003' ? true : false"
              :class="{'return-product-cart' : dispitems.length == 1}"
              :key="item.ordgdidx"
              @change="calClaimAmt(item)"
            />
          </li>
        </ul>
      </section>

      <!-- 클래임사유 -->
      <section class="payment-info__area pt-0">
        <div class="payment-info__header">
          <p class="mb-0 payment-info__header__title dp-p">{{claimName}}사유</p>
        </div>
        <div class="dp-mb-30">
          <form>
            <base-select 
              class="dp-mb-10" 
              v-model="rstype" 
              :options="rslist"
              placeholder="선택해주세요"
              @change="calClaimAmt(item)" />
            <base-textarea
              v-model="clmdetail"
              placeholder="상세사유를 입력해주세요. (최대 30자)"
              :max-count="30"
            />
          </form>

          <div class="" v-show="claimType != 'CLM001'">
            <div class="">
              <image-upload ref="imageupload" @change="changeFile" :isMobile='true'/>
            </div>
            <ul class="ul-dot dp-mt-10">
              <li>
                이미지는 10MB 이하, 동영상은 50MB 이하 파일을 이미지 최대 10개,
                동영상 최대 1개까지 등록 가능합니다.
              </li>
            </ul>
          </div>
        </div>
        <footer v-if="claimType != 'CLM003'">
          <base-checkbox v-model="saveCart" :label="claimName + '상품 장바구니 담기'" />
        </footer>
      </section>

      <hr class="dp-hr justify-margin" />

      <!-- 회수지 정보 -->
      <section class="address-info__area" v-if="claimType != 'CLM001'">
        <div class="address-info__header">
          <p class="mb-0 dp-p address-info__title">회수지 정보</p>

          <b-button
            class="dp-btn btn-h32 not-hover"
            variant="outline-gray-800 type02"
            squared
            pill
            @click="changeAddr('REC')"
          >
            <span class="dp-caption font-weight-400 text-gray-800"
              >회수지 변경</span
            >
            <i class="dp-icon sm01 icon-pick"></i>
          </b-button>
        </div>
        <hr class="dp-hr h1" />
        <div class="address-info__body">
          <div class="address-info__ul__area">
            <ul class="list-style-none">
              <li>
                <div>
                  <span class="address-info__key">받는사람</span
                  ><span class="address-info__value">{{recAddr.recname}}</span>
                </div>
              </li>
              <li>
                <div>
                  <span class="address-info__key">연락처</span
                  ><span class="address-info__value">{{$util.maskTel(recAddr.rectel)}}</span>
                </div>
              </li>
              <li>
                <div class="d-flex align-items-start">
                  <span class="address-info__key">주소</span>
                  <span class="address-info__value">{{recAddr.recaddrroad}} <br />{{recAddr.recaddrdetailroad}}</span>
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

      <!-- 배송지 정보 -->
      <section class="address-info__area" v-if="claimType == 'CLM003'">
        <div class="address-info__header">
          <p class="mb-0 dp-p address-info__title">배송지 정보</p>

          <b-button
            class="dp-btn btn-h32 not-hover"
            variant="outline-gray-800 type02"
            squared
            pill
            @click="changeAddr('EXC')"
          >
            <span class="dp-caption font-weight-400 text-gray-800"
              >배송지 변경</span
            >
            <i class="dp-icon sm01 icon-pick"></i>
          </b-button>
        </div>
        <hr class="dp-hr h1" />
        <div class="address-info__body">
          <div class="address-info__ul__area">
            <ul class="list-style-none">
              <li>
                <div>
                  <span class="address-info__key">받는사람</span
                  ><span class="address-info__value">{{excAddr.excdlvname}}</span>
                </div>
              </li>
              <li>
                <div>
                  <span class="address-info__key">연락처</span
                  ><span class="address-info__value">{{$util.maskTel(excAddr.excdlvtel)}}</span>
                </div>
              </li>
              <li>
                <div class="d-flex align-items-start">
                  <span class="address-info__key">주소</span>
                  <span class="address-info__value">{{excAddr.excdlvaddrroad}} <br />{{excAddr.excdlvaddrdetailroad}}</span>
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

      <!-- 환불계좌정보 -->
      <section class="payment-info__area" v-if="ordstatus != 'ODS001' && bforder.paywaytype == 'PWT002'">
        <div class="payment-info__header">
          <p class="mb-0 payment-info__header__title dp-p">환불계좌정보</p>
        </div>
        <div>
          <form>
            <div class="form-group">
              <base-input
                label="예금주"
                :is-readonly="refInfo.confirm"
                v-model="refInfo.refcusname"
                :is-label="true"
                placeholder="아이디를 입력해주세요."
              />
            </div>
            <div class="form-group">
              <base-input
                label="생년월일"
                :type="'tel'"
                :max-length="6"
                :is-readonly="refInfo.confirm"
                v-model="refInfo.refbirthday"
                :is-label="true"
                placeholder="생년월일을 입력해주세요."
              />
            </div>
            <div class="form-group">
              <label>환불은행</label>
              <base-select 
                v-model="refInfo.refbank"
                :options="banklist"
                :is-disabled="refInfo.confirm"
                placeholder="은행선택"></base-select>
            </div>
            <div class="apply__refund-account">
              <base-input
                v-model="refInfo.refaccnumber"
                label="계좌번호"
                :is-readonly="refInfo.confirm"
                :is-label="true"
                placeholder="- 없이 숫자 입력"
              />
              <b-button
                class="dp-btn apply__btn"
                variant="outline-gray-800"
                squared
                @click="accountConfirm"
              >
                <span>확인</span>
              </b-button>
            </div>
          </form>
        </div>
      </section>

      <hr class="dp-hr justify-margin" />

      <!-- 환불/추가결제정보 -->
      <section class="payment-info__area" v-if="calinfo.ordtotprice > 0 || calinfo.addrpaytotprice > 0">
        <div class="payment-info__header">
          <p class="mb-0 payment-info__header__title dp-p">환불/추가결제정보</p>
        </div>
        <hr class="dp-hr h1" />
        <div class="payment-info__body">
          <template v-if="calinfo.ordtotprice > 0">
            <ul class="list-style-none payment-info__ul">
              <li>
                <div>
                  <ul class="list-style-none detail-section__ul">
                    <li>
                      <div class="d-flex justify-content-between">
                        <span class="payment-info__key title">주문금액</span>
                        <span class="payment-info__value atten-new dp-price01">{{$util.maskComma(calinfo.ordtotprice + calinfo.dadadelivamt + calinfo.ptndelivamt)}}<span>원</span></span>
                      </div>
                    </li>
                    <li>
                      <div class="d-flex justify-content-between">
                        <span class="payment-info__key">주문상품</span>
                        <span class="payment-info__value atten-new">{{$util.maskComma(calinfo.ordtotprice)}}<span>원</span></span>
                      </div>
                    </li>
                    <li>
                      <div class="d-flex justify-content-between">
                        <span class="payment-info__key">배송비</span>
                        <span class="payment-info__value atten-new">{{$util.maskComma(calinfo.dadadelivamt + calinfo.ptndelivamt)}}<span>원</span></span>
                      </div>
                    </li>
                  </ul>
                </div>
              </li>
              <li class="h1">
                <div v-if="calinfo.minusamt > 0">
                  <ul class="list-style-none detail-section__ul">
                    <li>
                      <div class="d-flex justify-content-between">
                        <span class="payment-info__key title">할인금액</span>
                        <span class="payment-info__value atten-new dp-price01">{{$util.maskComma(calinfo.minusamt * -1)}}
                          <span>원</span>
                        </span>
                      </div>
                    </li>
                    <li v-if="calinfo.totsalepromoamt > 0">
                      <div class="d-flex justify-content-between">
                        <span class="payment-info__key">{{isemp ? '임직원' : '프로모션'}}할인</span>
                        <span class="payment-info__value atten-new">{{$util.maskComma(calinfo.totsalepromoamt * -1)}}<span>원</span></span>
                      </div>
                    </li>
                    <li v-if="calinfo.totgoodscpnamt > 0">
                      <div class="d-flex justify-content-between">
                        <span class="payment-info__key">상품쿠폰</span>
                        <span class="payment-info__value atten-new">{{$util.maskComma(calinfo.totgoodscpnamt * -1)}}<span>원</span></span>
                      </div>
                    </li>
                    <!-- <li>
                      <div class="d-flex justify-content-between">
                        <span class="payment-info__key">결제수단할인차감</span>
                        <span class="payment-info__value atten-new">0<span>원</span></span>
                      </div>
                    </li> -->
                    <li v-if="calinfo.basketcpnamt > 0">
                      <div class="d-flex justify-content-between">
                        <span class="payment-info__key">추가쿠폰</span>
                        <span class="payment-info__value atten-new">{{$util.maskComma(calinfo.basketcpnamt * -1)}}<span>원</span></span>
                      </div>
                    </li>
                    <li v-if="calinfo.totdelivcpnamt > 0">
                      <div class="d-flex justify-content-between">
                        <span class="payment-info__key">배송비쿠폰</span>
                        <span class="payment-info__value atten-new">{{$util.maskComma(calinfo.totdelivcpnamt * -1)}}<span>원</span></span>
                      </div>
                    </li>
                    <li v-if="calinfo.totrtndelivamt > 0">
                      <div class="d-flex justify-content-between">
                        <span class="payment-info__key">배송비차감</span>
                        <span class="payment-info__value atten-new">{{$util.maskComma(calinfo.totrtndelivamt * -1)}}<span>원</span></span>
                      </div>
                    </li>
                  </ul>
                </div>
              </li>
            </ul>
            <hr class="dp-hr h1 dp-mt-20 dp-mb-20" v-if="calinfo.rtnamt > 0"/>
            <ul class="list-style-none detail-section__ul" v-if="calinfo.rtnamt > 0">
              <li>
                <div class="d-flex justify-content-between">
                  <span class="refund-info__key title">환불예정금액</span>
                  <span class="refund-info__value atten-new dp-price01" v-if="ordstatus != 'ODS001'">{{$util.maskComma(calinfo.rtnamt)}}<span>원</span></span>
                  <span class="refund-info__value atten-new dp-price01" v-else>{{$util.maskComma(calinfo.rtnamt-calinfo.rtnpayamt)}}<span>원</span></span>
                </div>
              </li>
              <li v-if="calinfo.rtnresamt > 0">
                <div class="d-flex justify-content-between">
                  <span class="refund-info__key">적립금</span>
                  <span class="refund-info__value atten-new">{{$util.maskComma(calinfo.rtnresamt)}}<span>원</span></span>
                </div>
              </li>
              <li v-if="calinfo.rtnepoamt > 0">
                <div class="d-flex justify-content-between">
                  <span class="refund-info__key">D포인트</span>
                  <span class="refund-info__value atten-new">{{$util.maskComma(calinfo.rtnepoamt)}}<span> P</span></span>
                </div>
              </li>
              <li v-if="calinfo.rtnempresamt > 0">
                <div class="d-flex justify-content-between">
                  <span class="refund-info__key">임직원적립금</span>
                  <span class="refund-info__value atten-new">{{$util.maskComma(calinfo.rtnempresamt)}}<span>원</span></span>
                </div>
              </li>
              <li v-if="calinfo.rtnpayamt > 0">
              <div class="d-flex justify-content-between">
                <span class="refund-info__key" v-if="bforder.paywaytype == 'PWT001'">
                  {{bforder.paywaytypename}}({{bforder.cardcompany}})
                </span>
                <span class="refund-info__key" v-else>{{bforder.paywaytypename}}</span>
                <span class="atten-new price" v-if="ordstatus != 'ODS001'">{{$util.maskComma(calinfo.rtnpayamt)}}<span class="price-unit">원</span></span>
                <span class="atten-new price" v-else>0<span class="price-unit">원</span></span>
              </div>
            </li>
            </ul>
          </template>
          <hr class="dp-hr h1 dp-mt-20 dp-mb-20" v-if="calinfo.addrpaytotprice > 0"/>
          <ul class="list-style-none" v-if="calinfo.addrpaytotprice > 0">
            <li>
              <div class="d-flex justify-content-between">
                <span class="refund-info__key title">추가결제금액</span>
                <span>
                  <span class="atten-new dp-price01">{{$util.maskComma(calinfo.addrpaytotprice)}}</span>
                  <span>원</span>
                </span>
              </div>
            </li>
            <li v-if="calinfo.adddelivamt > 0">
              <div class="d-flex justify-content-between">
                <span class="refund-info__key">{{claimName}}배송비</span>
                <span class="refund-info__value atten-new">{{$util.maskComma(calinfo.adddelivamt)}}<span>원</span></span>
              </div>
            </li>
            <li v-if="calinfo.addpaytotprice > 0">
              <div class="d-flex justify-content-between">
                <span class="refund-info__key">할인반환</span>
                <span class="refund-info__value atten-new">{{$util.maskComma(calinfo.addpaytotprice)}}<span>원</span></span>
              </div>
            </li>
          </ul>
        </div>
      </section>
      <hr class="dp-hr justify-margin" v-if="calinfo.addrpaytotprice > 0"/>

      <!-- 사은품 -->
      <template v-if="giftList.length > 0">
        <section class="payment-info__area">
          <div class="payment-info__header">
            <p class="mb-0 payment-info__header__title dp-p">사은품</p>
          </div>
          <div class="" v-for="(gift,index) in giftList" :key="index">
            <p class="dp-p-sm font-weight-500 dp-mb-20">
              {{gift.promoname}}(최대 {{gift.giftselcnt}}개 선택)
            </p>
            <ul class="row list-style-none" style="margin-bottom: 10px;" v-for="(goods,index2) in gift.detail" :key="index2">
              <li class="col-6 col-xl-4" v-for="(good,index3) in goods" :key="index3">
                <div class="free-item">
                  <product-thumbnail 
                    :is-checkbox="true" 
                    :id="good.id"
                    :thumbnail-info="good"
                    v-model="good.chk"
                    @change="changeGiftChk(gift, good)"
                    :key="good.id +'_' + good.chk"/>
                  <p class="dp-p-sm text-gray-700 mt-2 mb-1">
                    [선택{{(index2+1)}}] 선택 사은품<br />
                    {{good.giftname}}
                  </p>
                  <p class="dp-p-sm text-gray-700 mb-0">{{good.rcvamt}}개</p>
                </div>
              </li>
            </ul>
          </div>
        </section>
        <hr class="dp-hr justify-margin" />
      </template>
      <!-- 관리자 취소신청(추가결제필요)-->
      <section class="add-payment__area" v-if="calinfo.addrpaytotprice > 0">
        <!-- <div class="add-payment__header">
          <div class="d-flex justify-content-between">
            <p class="dp-p font-weight-500 mb-0">추가결제</p>
            <span class="text-primary dp-caption">
              <span class="dp-p atten-new font-weight-500 mr-1">{{$util.maskComma(calinfo.addrpaytotprice)}}</span>원
            </span>
          </div>
        </div>
        <hr class="dp-hr h1 dp-mt-20 dp-mb-30" /> -->
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
          <div v-if="payInfo.paywaytype == 'PWT001'">
            <form>
              <div class="mb-2">
                <label>카드종류</label>
                <base-select
                  placeholder="카드를 선택해주세요"
                  :options="cardlist"
                  v-model="payInfo.cardCompany"
                />
              </div>

              <div v-if="calinfo.addrpaytotprice >= 50000">
                <label>할부선택</label>
                <base-select 
                  v-model="payInfo.cardInstallmentPlan"
                  :options="cardPlan"
                  placeholder="일시불"/>
              </div>
            </form>
          </div>
        </div>
        <p class="mb-0 dp-p-sm text-secondary">
          * {{claimName}}신청을 완료하시려면 추가결제가 필요합니다.
        </p>
      </section>
      <section class="add-payment__area">
      <div class="add-payment__footer">
        <div class="btn-group d-flex">
          <b-button
            class="dp-btn not-hover"
            variant="outline-gray-800"
            squared
            @click="$router.go(-1)"
          >
            <span>뒤로</span>
          </b-button>
          <b-button 
            class="dp-btn text-white" 
            variant="gray-800" 
            squared
            @click="claimApply"
            >
            <span>신청완료</span>
          </b-button>
        </div>
      </div>
      </section>
    </div>
  </main>
</template>

<script src="./ClaimApply.js">
</script>

<style scoped>
.btn-group {margin-top : 0px !important;}
</style>