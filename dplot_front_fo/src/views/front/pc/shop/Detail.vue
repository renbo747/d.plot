<template>
  <div>
    <main class="shop-detail pc-top-padding">
      <div class="container">
        <div class="container-inner">
          <section class="shop-detail-main d-flex">
            <div class="detail-main__product">
<!--              <div class="detail__logo"></div>-->
              <!-- todo: (fe) 상품 상세 슬라이드 고정데이터로 되어있음 변경 요망 -->
              <div class="">
                <figure class="shop-detail__img">
                  <img :src="selectImg" />
                </figure>
              </div>
              <ul class="shop-detail__ul list-style-none">
                <li
                  v-for="(img, index) in detail.swiperlist" :key="index"
                  @click="changeGoodsPcImage(index)"
                >
                  <img v-if="index < 8"
                    :src="img.fullpath"
                    alt="상품 상세 이미지"
                  />
                </li>
              </ul>
              <!-- <swiper ref="goodsSwiper" class="top-swiper" :options="swiperOptionTop">
                <swiper-slide v-for="(img, index) in detail.swiperlist" :key="index">
                  <img
                    :src="img.fullpath"
                    alt="상품 상세 이미지"
                  />
                </swiper-slide>
              </swiper>
              <swiper class="thumbs-swiper" :options="swiperOptionBottom">
                <swiper-slide v-for="(img, index) in detail.swiperlist" :key="index">
                  <img
                    :src="img.fullpath"
                    alt="상품 상세 이미지"
                    @click="changeGoodsImage(index)"
                  />
                </swiper-slide>
              </swiper> -->
            </div>
            <div class="shop-detail__info">
              <header class="info__header">
                <p class="info__header__title atten-new text-black mb-0" @click="$router.push('/magazine/brand/detail/'+detail.brandidx)">
                  {{detail.brandname}}
                </p>
                <h1 class="dp-title01 font-weight-700 text-black mb-0">
                  {{detail.goodsname}}
                </h1>

                <div class="shop-detail__review-pc d-flex" v-if="detail.isopenreview === 'T' && detail.reviewcnt > 0">
                  <div class="product__rating dp-title02 atten-new">
                    {{detail.reviewavg}} <span>({{detail.reviewcnt}})</span>
                  </div>
                  <span class="shop-detail__more-pc dp-p text-black font-weight-700" @click="scrollMeTo('review')">리뷰보기</span>
                </div>
                <div class="header__badge-group">
                  <span class="dp-badge best atten-new" v-if="detail.isbestbadge == 'T'">BEST</span>
                  <span class="dp-badge atten-new" v-if="detail.isnewbadge == 'T'">NEW</span>
                  <span class="dp-badge atten-new" v-if="detail.iscouponbadge == 'T'">COUPON</span>
                  <!-- <span class="dp-badge sm" v-if="detail.ispreordbadge == 'T'">pre-order</span> -->
                  <span class="dp-badge sm atten-new" v-for="(badge, index) in detail.badge" :key="index">{{badge}}</span>
                </div>
                <div class="quick_icon_wrap">
                  <i class="dp-icon icon-heart" @click="changeWish(detail)" :class="{ on: detail.iswished == 'T' }"></i>
                  <i class="dp-icon icon-share" @click="snsShareModal(detail)"></i>
                </div>
              </header>
              <div class="info__body">
                <p class="info__body-text dp-title02 font-weight-400 mb-0">
                  {{detail.summary}}
                </p>

                <div class="shop-detail__hash-group">
                  <span class="dp-hashtag dp-p font-weight-400" v-for="(keyword, index) in detail.keywordlist" :key="index"># {{keyword}}</span>
                </div>
              </div>

              <hr class="dp-hr" />

              <!-- 쿠폰 -->
              <div class="add-info__coupon" :style="detail.isnewjoincoupon == 'F' ? 'margin-bottom:0;' : null">
                <div class="coupon-info">
                  <!-- <p class="coupon__text dp-p-sm font-weight-500 mb-0" v-if="isEmp">
                    임직원가
                  </p> -->
                  <div class="coupon__amount">
                    <p class="coupon__price mb-0">
                      <span class="vollkorn font-weight-700">{{$util.maskComma(detail.saleamt)}}</span> 원
                    </p>
                    <span class="coupon__dc-percent vollkorn font-weight-700" v-if="detail.disrate > 0">{{detail.disrate}}%</span>
                  </div>
                  <p class="coupon__dc-price" v-if="detail.disrate > 0">
                    <span class="vollkorn text-gray-600 font-weight-400"
                      >{{$util.maskComma(detail.marketprice)}}</span
                    >
                    원
                  </p>
                  <p class="text_row" :style="detail.isnewjoincoupon == 'F' ? 'margin-bottom:0;' : null">최대 {{$util.maskComma(this.totreserve)}}원 적립</p>
                  <div class="new_member" v-if="detail.isnewjoincoupon == 'T'">
                    <p>신규회원 혜택가<br><span style="color:#ed3536; font-size:16px; font-weight:400;">(신규가입 쿠폰팩 적용 시)</span></p>
                    <a href="/shop/promotion/welcome"><p class="new_member_price"><span class="vollkorn">{{$util.maskComma(this.totsaleamt)}}</span>원</p></a>
                  </div>
                </div>

                <b-button
                  class="detail-coupon-btn dp-btn text-white dp-btn-icon btn-h32"
                  variant="gray-800"
                  squared
                  @click="couponDownload"
                  v-if="couponcnt > 0 || downCouponList.length > 0"
                >
                  <span class="dp-p-sm font-weight-400">추가쿠폰받기</span>
                  <i class="dp-icon icon-download coupon_down"></i>
                </b-button>
              </div>

              <hr class="dp-hr" v-if="detail.isnewjoincoupon == 'T'"/>

              <!-- <div class="add-info__footer">
                <div class="footer__btn" @click="changeWish(detail)" style="cursor: pointer;">
                  <i class="dp-icon icon-heart" :class="{ on: detail.iswished == 'T' }"></i>
                  <span class="dp-p-sm text-black">{{detail.wishcnt}}</span>
                </div>
                <div class="footer__btn" @click="snsShareModal(detail)" style="cursor: pointer;">
                  <i class="dp-icon sm02 icon-share"></i>
                  <span class="dp-p-sm text-black">공유</span>
                </div>
              </div> -->
              
              <section class="shop-detail__etc-info">
                <div class="etc_infor_row">
                  <div class="etc_infor_col etc_infor_col__header">배송</div>
                  <div class="etc_infor_col">
                    <p v-if="detail.deliv.delivfaretype == 'DCT001'">{{detail.deliv.delivschtypename}} | 무료배송
                      <br>{{detail.deliv.delivschtypedetail}}</p>
                      <p v-if="detail.deliv.delivfaretype == 'DCT002'">{{detail.deliv.delivschtypename}} | {{$util.maskComma(detail.deliv.delivfare)}}원
                      <br>{{detail.deliv.delivschtypedetail}}</p>
                      <p v-if="detail.deliv.delivfaretype == 'DCT003'">{{detail.deliv.delivschtypename}} | {{$util.maskComma(detail.deliv.delivfare)}}원 ({{$util.maskComma(detail.deliv.delivfreefare)}}원 이상 구매시 무료)
                      <br>{{detail.deliv.delivschtypedetail}}</p>
                      <p v-if="detail.deliv.delivfaretype == 'DCT004'">{{detail.deliv.delivschtypename}} | 착불
                      <br>{{detail.deliv.delivschtypedetail}}</p>
                      <p v-if="detail.deliv.delivfaretype == 'DCT005'">{{detail.deliv.delivschtypename}} | 구매수량당 {{$util.maskComma(detail.deliv.delivfare)}}원
                      <br>{{detail.deliv.delivschtypedetail}}</p>
                      <p class="btn_text" @click="openModal('deliveryInfoModal')">자세히보기</p>
                  </div>
                </div>
                <div class="etc_infor_row" v-if="discountInfo.disamount > 0 || !$util.isEmpty(cardBenefit.discountCards.disCardsMsg)">
                  <div class="etc_infor_col etc_infor_col__header">카드혜택</div>
                  <div class="etc_infor_col">
                    <p>{{discountInfo.content}}</p>
                    <p v-for="(card,index) in cardBenefit.discountCards.disCardsMsg" :key="index">
                      {{card}}
                    </p>
                    <p class="btn_text" @click="openModal('cardBenefitModal')">자세히보기</p>
                  </div>
                </div>
                <div class="etc_infor_row">
                  <div class="etc_infor_col etc_infor_col__header">무이자할부</div>
                  <div class="etc_infor_col">
                    <p @click="openModal('noInterestCardModal')" class="card_p">카드사별 무이자 혜택 <i class="dp-icon icon-more"></i></p>
                  </div>
                </div>
              </section>

                <!-- <div class="shop-detail__shipping dp-mb-30">
                  <header class="d-flex etc-info__header">
                    <h2 class="shipping__title d-flex align-items-center text-black font-weight-500 mb-0">
                      배송정보
                      <span class="dp-p-sm font-weight-500 text-secondary ml-2">{{detail.deliv.delivschtypename}}</span>
                    </h2>

                    <b-button
                      class="shipping__btn dp-btn dp-btn-icon btn-h32 type02 not-hover rounded-pill"
                      variant="outline-gray-800"
                      @click="openModal('deliveryInfoModal')"
                    >
                      <span class="dp-caption text-gray-800">배송비 안내</span>
                      <i class="dp-icon icon-more sm"></i>
                    </b-button>
                  </header>
                  <div class="etc-info__body" v-if="detail.deliv != null">
                    <div class="shipping__info">
                      <p class="d-flex align-items-center mb-0">
                        <span class="info__title dp-p-sm font-weight-500">배송방법</span>
                        <span class="dp-bar h14"></span>
                        <span class="dp-p-sm text-gray-700">{{detail.deliv.delivtypename}}</span>
                        <span class="dp-bar h14"></span>
                        <span class="dp-p-sm text-gray-700">{{detail.deliv.delivfare  >  0 ?$util.maskComma(detail.deliv.delivfare) +'원':'무료배송'}}</span>
                      </p>
                    </div>
                  </div>
                </div> -->

                <!-- 카드사 혜택 정보 -->
                <!-- <div class="shop-detail__card dp-mb-30" v-if="discountInfo.isshow">
                  <header class="d-flex etc-info__header">
                    <h2 class="shipping__title d-flex align-items-center text-black font-weight-500 mb-0">
                      카드사 혜택 안내
                    </h2>

                    <b-button
                      class="card__btn dp-btn dp-btn-icon btn-h32 type02 not-hover rounded-pill"
                      variant="outline-gray-800"
                      @click="openModal('noInterestCardModal')"
                    >
                      <span class="dp-caption text-gray-800">무이자 할부</span>
                      <i class="dp-icon icon-more sm"></i>
                    </b-button>
                  </header>
                  <div class="etc-info__body" v-if="discountInfo.disamount > 0">
                    <div class="card__info">
                      <p class="dp-p text-gray-700 mb-12">
                        {{discountInfo.content}}
                      </p>
                      <div class="card-box-bottom d-flex">
                        <p class="dp-title02 font-weight-400 text-black mb-0">
                          <span class="dp-title02 atten-new font-weight-700 text-primary">{{$util.maskComma(discountInfo.disamount)}}</span>원 할인
                        </p>
                        <span
                          class="shop-detail__more-pc text-black font-weight-700 dp-p"
                          @click="openModal('cardBenefitModal')"
                          >혜택 더 보기</span
                        >
                      </div>
                    </div>
                  </div>
                </div> -->
                <!-- AS 정보 -->
                <!-- <div class="shop-detail__as dp-mb-30" v-if="!$util.isNull(detail.asnotice)">
                  <header class="etc-info__header">
                    <h2 class="shipping__title d-flex align-items-center text-black font-weight-500 mb-0" >
                      A/S 안내
                    </h2>
                  </header>
                  <div class="etc-info__body">
                    <div class="as-box">
                      <figure class="as__figure">
                        <img
                          src="~@/assets/common/icon/icon-as-info-50px.svg"
                          alt="수리 아이콘"
                        />
                      </figure>
                      <p class="dp-p-sm text-gray-700 mb-0">
                        {{detail.asnotice}}
                      </p>
                    </div>
                  </div>
                </div> -->
              

              <b-button
                class="dp-btn text-white btn-h48"
                variant="black"
                squared
                style="height:60px"
                @click="handleBuyBottomSheet"
              >
                <span>{{btnBuy}}</span>
              </b-button>
            </div>
          </section>
          <!-- // 상단 -->
          <section class="shop-detail-product-info pc-top-padding"
            id="tab"
            ref="tab_height"
          >
            <!--  메뉴 탭 영역  -->
            <div class="detail-tab-menu" :class="{ fixed: isScrollTab }">
              <div class="shop-detail__tab dp-tabs">
                <div class="nav-tabs" :class="{'active' : nowActive === 'detail'}" style="cursor: pointer" @click="scrollMeTo('detail')">
                  <span>상품상세</span>
                </div>
                <div class="nav-tabs" :class="{'active' : nowActive === 'review'}" style="cursor: pointer" @click="scrollMeTo('review')" v-if="detail.isopenreview === 'T'">
                  <span>리뷰({{detail.reviewcnt}})</span>
                </div>
                <div class="nav-tabs" :class="{'active' : nowActive === 'inquiry'}" style="cursor: pointer" @click="scrollMeTo('inquiry')">
                  <span>문의({{this.listTotal}})</span>
                </div>
              </div>
            </div>

            <!-- 상품 상세 -->
            <article class="shop-detail__contents" ref="detail">
              <div class="detail__contents__wrap d-flex">
                <div
                  class="contents__img-area no-scrollbar"
                  :class="{ on: isProductDetailMore }"
                >
                  <figure class="contents__figure" v-html="detail.content">
                  </figure>
                  <!-- <detail-product /> -->
                </div>
                <div
                  v-if="!isProductDetailMore"
                  class="shop-detail__contents__btn__area"
                >
                  <b-button
                    class="dp-btn dp-btn-icon detail__contents__btn"
                    variant="outline-gray-800 not-hover bg-white"
                    squared
                    @click="isProductDetailMore = true"
                  >
                    <span class="dp-title02 font-weight-400 text-black"
                      >상품 설명 더 보기</span
                    >
                    <i class="dp-icon md icon-arrow-down"></i>
                  </b-button>
                </div>
              </div>
            </article>
          </section>

          <section ref="review">
            <!--리뷰 -->
            <review-pc 
              :productInfo="{goodsno:detail.goodsno}" 
              :isReview="isReview"
              id="review" 
              v-if="detail.isopenreview === 'T'"
            />
            <!-- 문의 -->
            <article class="shop-detail__contents-inquiry" ref="inquiry">
              <div class="container">
                <div class="contents-inquiry__header d-flex">
                  <p
                    class="contents-inquiry__header__text text-black mb-0 font-weight-500 atten-new"
                  >
                    Q&A<span class="text-gray-600 atten-new ml-1" style="font-weight: 400; font-size: 18px;">({{this.listTotal}})</span>
                  </p>

                  <b-button
                    class="contents-inquiry__header__btn dp-btn dp-btn-icon not-hover question-btn btn-h48"
                    variant="outline-gray-800"
                    squared
                    @click="openModal('QnaModalPC')"
                  >
                    <!-- <i class="dp-icon md icon-question"></i> -->
                    <span class="dp-p text-black font-weight-400"
                      >상품문의 작성</span
                    >
                  </b-button>
                </div>

                <!-- 문의 리스트 타입 -->
                <ul class="dp-sort__ul list-style-none no-scrollbar" v-if="!inquiryType[0].checked || (inquiryType[0].checked && inquiryData.length > 0)">
                  <li v-for="(list, index) in inquiryType" :key="index">
                    <label :for="list.id" class="dp-sort__label">
                      <input
                        type="radio"
                        :id="list.id"
                        name="terms"
                        :checked="list.checked"
                        :ref="list.id"
                        @click="changeType(list.id)"
                        hidden
                      />
                      <span class="dp-sort__badge">{{ list.label }}</span>
                    </label>
                  </li>
                </ul>

                <div class="contents-inquiry__body">
                  <template v-if="inquiryData.length === 0">
                    <p class="text-center dp-p-sm">문의가 없습니다.</p>
                  </template>
                  <template v-else>
                    <ul class="contents-inquiry__ul list-style-none">
                      <li v-for="(list, index) in inquiryData" :key="index">
                        <div class="inquiry-list-item">
                          <div class="inquiry__question">
                            <!-- Header -->
                            <header class="question__header d-flex">
                              <div class="d-flex">
                                <div class="header__inner">
                                  <span
                                    class="dp-p mr-10 font-weight-700 text-black"
                                    >{{ list.isreply }}
                                  </span>
                                  <span
                                    class="dp-p mr-10 font-weight-400 text-gray-700"
                                    >{{ list.regdate }}
                                  </span>
                                  <span
                                    class="dp-p font-weight-400 text-gray-700"
                                    >{{ $util.maskUserId(list.writer) }}
                                  </span>
                                </div>
                                <div
                                  class="inquiry__edit"
                                  v-if="list.own"
                                >
                                  <span
                                    class="dp-p font-weight-400 text-gray-800"
                                    @click="productQnaReWirte(list)"
                                    >수정</span
                                  >
                                  <span class="dp-bar h10"></span>
                                  <span
                                    class="dp-p font-weight-400 text-gray-800"
                                    @click="productQnaDelete(list.idx)">삭제</span
                                  >
                                </div>
                              </div>

                              <template
                                v-if="
                                  (list.issecret == 'T' && !list.own)
                                  || list.isreply === '미답변'
                                "
                              >
                                <!-- <i class="dp-icon icon-arrow-down md"></i> -->
                              </template>
                              <template v-else>
                                <i
                                  class="dp-icon icon-arrow-down md"
                                  v-b-toggle="`accordion-${index}`"
                                ></i>
                              </template>
                            </header>
                            <!-- body -->
                            <div class="question__body">
                              <template
                                v-if="
                                  list.issecret == 'T' && !list.own
                                "
                              >
                                <p
                                  class="dp-p pt-20 pb-20 font-weight-400 text-gray-700 mb-0"
                                >
                                  비밀글입니다.
                                </p>
                              </template>
                              <template v-else>
                                <p
                                  class="dp-p pt-20 pb-20 text-gray-700 mb-0"
                                  v-html="list.content"
                                ></p>
                              </template>
                            </div>
                          </div>
                          <b-collapse
                            v-if="list.isreply !== '미답변'"
                            :id="`accordion-${index}`"
                            accordion="gnb-accordion"
                          >
                            <div class="inquiry__answer">
                              <p
                                class="dp-title02 mb-20 font-weight-400 text-black mb-0"
                                v-html="list.repcontent"
                              ></p>
                              <div class="answer__footer pt-20 d-flex">
                                <span class="dp-p mr-10 text-gray-700"
                                  >{{ list.repgetdate }}
                                </span>
                                <!-- <span class="dp-p mr-10 text-gray-700">{{
                                  list.repuserid
                                }}</span> -->
                                <!-- <base-like class="like-btn-pc" /> -->
                              </div>
                            </div>
                          </b-collapse>
                        </div>
                      </li>
                    </ul>
                  </template>
                </div>
              </div>
              <base-pagination class="detail-pagination" :currentPage="currentPage" :totalPage="totalPage" :listTotal="listTotal" :listCnt="listCnt" @changePage="changePage"/>
<!--               <base-pagination  /> --> 
           </article>
            <!-- 스타일 추천 -->
            <!-- Recopick 구축테스트_상품상세 스크립트 -->
            <!-- <div class="contents__recommend">
              <header class="recommend__header">
                <p class="recommend__title">다른스타일 추천</p>
              </header>
              <div class="recommend__body">
                <recopick :recopick="recopick"></recopick>
              </div>
            </div> -->
            <!-- 브랜드 배너 -->
            <!-- 07/16 숨김처리 -->
            <!-- <div class="add-info__brand" v-if="!$util.isNull(detail.brandidx)">
              <div class="brand__box d-flex">
                <div class="d-flex brand-box__left">
                  <figure class="brand__figure">
                    <img :src="detail.brandimgurl" @error="replaceBrandImg"/>
                  </figure>

                  <p
                    class="brand__slogan dp-title02 text-gray-700 font-weight-500 mb-0"
                  >
                    {{detail.headcopy}}
                  </p>
                </div>
                <div>
                  <b-button
                    class="dp-btn dp-btn-icon"
                    variant="outline-gray-800 not-hover btn-h48"
                    squared
                    @click="$router.push('/magazine/brand/detail/'+detail.brandidx)"
                  >
                    <span class="dp-p text-black">브랜드샵</span>
                    <i class="dp-icon icon-more sm02"></i>
                  </b-button>
                </div>
              </div>
            </div> -->
            <!-- 서비스 소개 -->
            <div class="contents__product-info">
              <ul class="contents__ul list-style-none">
                <li>
                  <div class="contents__menu" @click="openModal('productInfoModal')" style="cursor: pointer">
                    <span class="fz-18 text-black font-weight-500"
                      >상품정보제공 고시</span
                    >
                    <i class="dp-icon md icon-more"></i>
                  </div>
                </li>
                <li>
                  <div class="contents__menu" @click="openModal('sellerInfoModal')" style="cursor: pointer">
                    <span class="fz-18 text-black font-weight-500">판매자 정보</span>
                    <i class="dp-icon md icon-more"></i>
                  </div>
                </li>
                <li>
                  <div class="contents__menu" @click="openModal('exchangeModal')" style="cursor: pointer">
                    <span class="fz-18 text-black font-weight-500">교환 및 반품</span>
                    <i class="dp-icon md icon-more"></i>
                  </div>
                </li>
              </ul>
            </div>
          </section>
        </div>
      </div>
    </main>
    <div class="shop-detail__buy-btn-wrap" :class="{ on: isBtnOpen }" 
      v-if="isScrollSheet">
      <div class="shop-detail__buy-btn" v-if="!isBuyBottomSheet">
        <div class="position-relative container">
          <div class="container-inner">
            <div class="buy-btn-fixed">
              <b-button
                class="dp-btn text-white dp-btn-icon btn-h48"
                variant="gray-800"
                squared
                @click="handleBuyBottomSheet"
              >
                <span>{{btnBuy}}</span>
                <i class="dp-icon md icon-arrow-up white"></i>
              </b-button>
            </div>
          </div>
        </div>
      </div>
    </div>

      <bottom-price
        v-if="isBuyBottomSheet"
        :productInfo="detail"
        :items="selectedItem"
        :isProductInfo="true"
        :btn-title="btnBuy"
        @close="isBuyBottomSheet = false"
        @cart="handleCart"
        @buy="handleBuy"
      ></bottom-price>
  </div>
</template>

<script src="@views.mobile/shop/Detail.js">
</script>

<style>
.modal-open .buy-btn-fixed .dp-btn{
  right:8px !important;
}

.shop-detail .detail-main__product .shop-detail__img {width: 700px; height: 700px;}
.shop-detail .shop-detail__ul li {margin-right: 12px; margin-bottom: 12px;}
.shop-detail .shop-detail__ul li img {width: 77px;}
.shop-detail .info__header__title {font-size: 18px; margin-bottom: 2px !important;}
.shop-detail .dp-title01 {font-size: 24px;}
.shop-detail .product__rating {background-size: 17px; background-position: left 3px; padding-left: 20px;}
.shop-detail .info__body-text {padding-bottom: 10px; color: #666;}
.shop-detail .shop-detail__hash-group {padding-bottom: 24px;}
.shop-detail .add-info__coupon {position: relative;}
.shop-detail .add-info__coupon .coupon-info {width: 100%;}
.shop-detail .add-info__coupon .coupon__price {font-weight: medium; font-size: 20px;}
.shop-detail .add-info__coupon .coupon__price span {font-size: 30px;}
.shop-detail .add-info__coupon .coupon__dc-percent {font-size: 30px; color: #99cc00;}
.shop-detail .add-info__coupon .coupon__dc-price {font-weight: medium; font-size: 16px; line-height: 1; margin-bottom: 15px;}
.shop-detail .add-info__coupon .coupon__dc-price span {font-size: 20px;}
.shop-detail .text_row {display: block; width: 100%; font-size: 18px; padding-bottom: 23px; border-bottom:1px solid #d2d2d2; margin-bottom: 23px;}
.shop-detail .dp-btn.detail-coupon-btn {position: absolute; top: 30px; right: 0; background: #000; font-size: 16px; width: 126px; height: 40px; max-width: 100%; display: flex; justify-content: center; align-items: center;}
.shop-detail .icon-download.coupon_down {background-image: url('~@/assets/common/icon/coupon_down.png'); background-size: 9px !important; width: 9px;}
.shop-detail .new_member {font-size: 16px; color: #000; font-weight: 600; line-height: 1.4; display: flex; justify-content: space-between; align-items: center; padding:0; margin-bottom: 0;}
.shop-detail .new_member p {margin-bottom: 0;}
.shop-detail .new_member .new_member_price {font-size: 18px; font-weight: 400; border-bottom:1px solid #000;}
.shop-detail .new_member .new_member_price .vollkorn {font-size: 26px; font-weight: 700}
.shop-detail .shop-detail__etc-info .etc_infor_row {display: flex; margin-bottom: 25px;}
.shop-detail .shop-detail__etc-info .etc_infor_row:last-child {margin-bottom: 0;}
.shop-detail .shop-detail__etc-info .etc_infor_row .etc_infor_col.etc_infor_col__header {font-weight: 600; color: #000; min-width: 105px;}
.shop-detail .shop-detail__etc-info .etc_infor_row .etc_infor_col {font-size: 16px; letter-spacing: -1px; color: #666;}
.shop-detail .shop-detail__etc-info .etc_infor_row .etc_infor_col p {font-size: 16px; color: #888; margin-bottom: 0;}
.shop-detail .shop-detail__etc-info .etc_infor_row .etc_infor_col p.card_p {font-size: 16px; font-weight: 400; color: #000; cursor:pointer;}
.shop-detail .shop-detail__etc-info .etc_infor_row .etc_infor_col .btn_text {font-weight: 400; display: inline-block; color: #000; border-bottom: 1px solid #000; cursor:pointer; margin-top: 5px;}
.shop-detail__etc-info {padding:27px 0 43px;}
.shop-detail .shop-detail__contents-inquiry .contents-inquiry__header__text {font-size: 24px; font-weight: 600 !important;}
.shop-detail__info {position: relative;}
.quick_icon_wrap { position: absolute; top: 0px; right: 0px; align-items: center;  display: flex;}
.quick_icon_wrap .dp-icon {display: inline-block;}
.quick_icon_wrap i { margin-left: 10px; width:20px; height: 20px; background-size: 18px !important;}
</style>