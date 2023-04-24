<template>
  <main class="dp-mypage-cancel-apply-pc">
    <section class="cancel-apply__header__area">
      <p class="cancel-apply__header mb-0">{{claimName}}신청</p>
    </section>
    <!-- 신청 상품 목록 -->
    <section class="apply__product-list">
      <ul class="list-style-none">
        <li v-for="(item,index) in dispitems" :key="index">
          <div class="product-item">
            <div class="item-box">
              <base-checkbox 
                v-model="item.chk" 
                :id="item.ordgdidx"
                :label="null" 
                v-if="dispitems.length > 1"
                @change="calClaimAmt(item)"/>
              <div class="item-thumbnail">
                <product-thumbnail
                  :thumbnail-info="{
                    id: item.goodsno,
                    fullpath: item.fullpath,
                  }"
                  :to="{
                    name: 'shop-detail',
                    params: { pid: item.goodscode },
                  }"
                  :height="150"
                />
              </div>
              <div class="item-info">
                <p class="item-ctg">{{item.brandname}}</p>
                <p class="item__p">{{item.goodsname}}</p>
                <p class="item__p option d-flex align-items-center" v-html="item.opthtml">
                </p>
              </div>
            </div>
            <div class="item-count">
              <p class="item__p text-center">{{item.ordcnt}}개</p>
            </div>
            <div class="item-price">
              <p class="price__p text-center mb-0">
                <span class="price__span atten-new">{{$util.maskComma(item.realgoodsamt)}}</span>원
              </p>
            </div>
            <div class="item-select">
              <base-select
                class="sm"
                v-model="item.clmcnt"
                :options="item.selectOption"
                :is-disabled="item.origincnt == 1 || ordstatus == 'ODS001'"
                @change="calClaimAmt(item)"
              />
              <!-- 옵션 변경-->
              <!-- <b-button
                v-if="claimType == 'CLM003'"
                class="dp-btn btn-h32 option-btn"
                variant="outline-gray-400"
                squared
                @click="showOption"
              >
                <span class="cart-btn-text text-gray-800">옵션변경</span>
              </b-button> -->
            </div>
          </div>
        </li>
      </ul>
    </section>

    <!-- 교환옵션 -->
    <section class="cancel-reason__area pt-0" v-if="claimType == 'CLM003'">
      <div class="cancel-reason__header">
        <p class="mb-0 cancel-reason__title title">교환옵션</p>
      </div>
      <div>
        <form class="">
          <base-dropdown 
            placeholder="교환옵션 선택"
            v-model="dispitems[0].excoption" 
            :options="dispitems[0].excgoodslist" />
        </form>
      </div>
    </section>

    <!-- 클레임사유 -->
    <section class="cancel-reason__area pt-0">
      <div class="cancel-reason__header">
        <p class="mb-0 cancel-reason__title title">{{claimName}}사유</p>
      </div>
      <div class="dp-mb-30">
        <form class="dp-mb-30">
          <base-dropdown 
            class="dp-mb-10"
            v-model="rstype" 
            :options="rslist"
            placeholder="선택해주세요"
            @change="calClaimAmt" />
          <base-textarea
            v-model="clmdetail"
            placeholder="상세사유를 입력해주세요. (최대 30자)"
            :max-count="30"
          />
        </form>
        <div class="" v-show="claimType != 'CLM001'">
          <div class="img-apply__header">
            <p class="mb-0 img-apply__title title">이미지 등록</p>
          </div>
          <div class="">
            <image-upload ref="imageupload" @change="changeFile" :isMobile='false' />
          </div>
          <ul class="ul-dot dp-mt-20">
            <li>
              이미지는 10MB 이하, 동영상은 50MB 이하 파일을 이미지 최대 10개,
              동영상 최대 1개까지 등록 가능합니다.
            </li>
          </ul>
        </div>
      </div>
      <!-- <footer>
        <base-checkbox v-model="saveCart" :label="claimName + '상품 장바구니 담기'" />
      </footer> -->
    </section>
    <hr class="dp-hr" />

    <!-- 회수지 정보 -->
    <section class="address-info__area" v-if="claimType != 'CLM001'">
      <div class="address-info__header">
        <p class="mb-0 address-info__title title">회수지 정보</p>

        <b-button
          class="dp-btn btn-h32 not-hover"
          variant="outline-gray-800 type02"
          squared
          pill
          @click="changeAddr('REC')"
        >
          <span class="dp-caption font-weight-400 text-gray-800">회수지 변경</span>
          <i class="dp-icon sm01 icon-pick"></i>
        </b-button>
      </div>
      <hr class="h1" />
      <div class="address-info__body">
        <div class="address-info__ul__area">
          <ul class="list-style-none">
            <li>
              <div>
                <span class="address-info__key">받는사람</span>
                <span class="address-info__value">{{recAddr.recname}}</span>
              </div>
            </li>
            <li>
              <div>
                <span class="address-info__key">연락처</span>
                <span class="address-info__value">{{$util.maskTel(recAddr.rectel)}}</span>
              </div>
            </li>
            <li>
              <div class="d-flex align-items-start">
                <span class="address-info__key">주소</span>
                <span class="address-info__value">{{recAddr.recaddrroad}} {{recAddr.recaddrdetailroad}}</span>
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

    <!-- 배송지 정보 -->
    <section class="address-info__area" v-if="claimType == 'CLM003'">
      <div class="address-info__header">
        <p class="mb-0 address-info__title title">배송지 정보</p>

        <b-button
          class="dp-btn btn-h32 not-hover"
          variant="outline-gray-800 type02"
          squared
          pill
          @click="changeAddr('EXC')"
        >
          <span class="dp-caption font-weight-400 text-gray-800">배송지 변경</span>
          <i class="dp-icon sm01 icon-pick"></i>
        </b-button>
      </div>
      <hr class="h1" />
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
                <span class="address-info__value">{{excAddr.excdlvaddrroad}} {{excAddr.excdlvaddrdetailroad}}</span>
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

    <!-- 환불계좌정보 -->
    <section class="refund-account-info__area" v-if="ordstatus != 'ODS001' && bforder.paywaytype == 'PWT002'">
      <div class="refund-account-info__header">
        <p class="mb-0 refund-account-info__title title">환불계좌정보</p>
      </div>
      <div>
        <form>
          <div class="form-group">
            <base-input
              label="예금주"
              v-model="refInfo.refcusname"
              :is-readonly="refInfo.confirm"
              :is-label="true"
              placeholder="예금주"
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
              :is-disabled="refInfo.confirm"
              :options="banklist"
              placeholder="은행선택"></base-select>
          </div>
          <div class="apply__refund-account d-flex">
            <div class="refund-account__input">
              <base-input
                label="계좌번호"
                v-model="refInfo.refaccnumber"
                :is-readonly="refInfo.confirm"
                :is-label="true"
                placeholder="- 없이 숫자 입력"
              />
            </div>
            <div class="refund-account__btn">
              <b-button
                class="dp-btn apply__btn"
                variant="outline-gray-800"
                @click="accountConfirm"
                squared
              >
                <span>확인</span>
              </b-button>
            </div>
          </div>
        </form>
      </div>
    </section>
    <hr class="dp-hr" />

    <!-- 환불/추가결제정보 -->
    <section class="payment-info__area" v-if="calinfo.ordtotprice > 0 || calinfo.addrpaytotprice > 0">
      <div class="payment-info__header">
        <p class="mb-0 header__title title">환불/추가결제정보</p>
      </div>
      <hr class="h1" />
      <div class="payment-info__body">
        <template v-if="calinfo.ordtotprice > 0">
          <ul class="list-style-none payment-info__ul">
            <li class="pb-20">
              <div>
                <ul class="list-style-none">
                  <li>
                    <div class="d-flex justify-content-between">
                      <span class="payment-info__key title">주문금액</span>
                      <span class="atten-new l-price">{{$util.maskComma(calinfo.ordtotprice + calinfo.dadadelivamt + calinfo.ptndelivamt)}}<span class="l-price-unit">원</span></span
                      >
                    </div>
                  </li>
                  <li>
                    <div class="d-flex justify-content-between">
                      <span class="payment-info__key">주문상품금액</span>
                      <span class="atten-new price">{{$util.maskComma(calinfo.ordtotprice)}}<span class="price-unit">원</span></span>
                    </div>
                  </li>
                  <li>
                    <div class="d-flex justify-content-between">
                      <span class="payment-info__key">배송비 결제금액</span>
                      <span class="atten-new price">{{$util.maskComma(calinfo.dadadelivamt + calinfo.ptndelivamt)}}<span class="price-unit">원</span></span>
                    </div>
                  </li>
                </ul>
              </div>
            </li>
            <hr class="h1" v-if="calinfo.minusamt > 0"/>
            <li class="pt-20">
              <div>
                <ul class="list-style-none">
                  <li v-if="calinfo.minusam > 0">
                    <div class="d-flex justify-content-between">
                      <span class="payment-info__key title font-weight-500">할인금액 차감</span>
                      <span class="atten-new l-price">{{$util.maskComma(calinfo.minusamt * -1)}}
                        <span class="l-price-unit">원</span>
                      </span>
                    </div>
                  </li>
                  <!-- <li>
                    <div class="d-flex justify-content-between">
                      <span class="payment-info__key">결제수단할인 차감</span>
                      <span class="atten-new price">0<span class="price-unit">원</span></span>
                    </div>
                  </li> -->
                  <li v-if="(calinfo.totdelivcpnamt + calinfo.totrtndelivamt) > 0">
                    <div class="d-flex justify-content-between">
                      <span class="payment-info__key">배송비 차감</span>
                      <span class="atten-new price">{{$util.maskComma((calinfo.totdelivcpnamt + calinfo.totrtndelivamt)  * -1)}}<span class="price-unit">원</span></span>
                    </div>
                  </li>
                </ul>
              </div>
            </li>
          </ul>
          <hr class="h1" v-if="calinfo.rtnamt > 0"/>
          <ul class="list-style-none payment-info__ul" v-if="calinfo.rtnamt > 0">
            <li>
              <div class="d-flex justify-content-between">
                <span class="refund-info__key title font-weight-500">환불예정금액</span>
                <span class="atten-new l-price" v-if="ordstatus != 'ODS001'">{{$util.maskComma(calinfo.rtnamt)}}<span class="l-price-unit">원</span></span>
                <span class="atten-new l-price" v-else>{{$util.maskComma(calinfo.rtnamt-calinfo.rtnpayamt)}}<span class="l-price-unit">원</span></span>
              </div>
            </li>
            <li>
              <div class="d-flex justify-content-between">
                <span class="refund-info__key">{{bforder.paywaytypename}}</span>
                <span class="atten-new price" v-if="ordstatus != 'ODS001'">{{$util.maskComma(calinfo.rtnpayamt)}}<span class="price-unit">원</span></span>
                <span class="atten-new price" v-else>0<span class="price-unit">원</span></span>
              </div>
            </li>
          </ul>
        </template>
        <hr class="h1" v-if="calinfo.addrpaytotprice > 0"/>
        <ul class="list-style-none payment-info__ul" v-if="calinfo.addrpaytotprice > 0">
          <li>
            <div class="d-flex justify-content-between align-items-center">
              <span class="refund-info__key title">추가결제금액</span>
              <span>
                <!-- <span class="refund-info__key mr-6 text-black">신용카드(하나)</span> -->
                <span class="atten-new l-price">{{$util.maskComma(calinfo.addrpaytotprice)}}</span>
                <span class="l-price-unit">원</span>
              </span>
            </div>
          </li>
          <li v-if="calinfo.adddelivamt > 0">
            <div class="d-flex justify-content-between">
              <span class="refund-info__key">{{claimName}}배송비</span>
              <span class="atten-new price">{{$util.maskComma(calinfo.adddelivamt)}}<span class="price-unit">원</span></span>
            </div>
          </li>
          <li v-if="calinfo.addpaytotprice > 0">
            <div class="d-flex justify-content-between">
              <span class="refund-info__key">할인반환</span>
              <span class="atten-new price">{{$util.maskComma(calinfo.addpaytotprice)}}<span class="price-unit">원</span></span>
            </div>
          </li>
        </ul>
      </div>
    </section>
    <hr class="dp-hr"  v-if="calinfo.addrpaytotprice > 0"/>

    <!-- 관리자 클레임신청(추가결제필요)-->
    <section class="add-payment__area" v-if="calinfo.addrpaytotprice > 0">
      <!-- <div class="add-payment__header">
        <div class="d-flex justify-content-between">
          <p class="mb-0 header__title">추가결제</p>
          <span class="text-primary dp-caption"
            ><span class="dp-p atten-new font-weight-500 mr-1">{{$util.maskComma(calinfo.addrpaytotprice)}}</span
            >원</span
          >
        </div>
      </div>
      <hr class="h1 dp-mt-20 dp-mb-30" /> -->
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

            <div v-if="calinfo.addrpaytotprice >= 50000">
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
      </div>
    </section>
    <section class="add-payment__area">
      <div class="add-payment__footer">
        <div class="btn-group d-flex justify-content-center">
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
  </main>
</template>

<script src="@views.mobile/nonemember/shop/claim/ClaimApply.js">
</script>

<style scoped>
.btn-group {margin-top : 0px !important;}
</style>
