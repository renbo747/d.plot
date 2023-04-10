<template>
  <div class="shop-detail">
    <!-- 상품 상단 -->
    <section class="shop-detail__top">
      <!-- 상품 썸네일 -->
      <div class="shop-detail__thumbnail">
        <swiper class="swiper" :options="swiperOption">
          <swiper-slide v-for="(img, index) in detail.swiperlist" :key="index">
            <figure class="thumbnail__figure">
              <img
                :src="img.fullpath"
                alt="상품 이미지"
              />
            </figure>
          </swiper-slide>

          <div class="swiper-pagination" slot="pagination" v-show="detail.swiperlist.length > 1"></div>
        </swiper>
      </div>

      <!-- 상품 정보 -->
      <div class="shop-detail__info">
        <header class="info__header">
          <p v-if="detail.brandname" class="atten-new dp-title mb-1" @click="$router.push('/magazine/brand/detail/'+detail.brandidx)">{{detail.brandname}}<i class="dp-icon icon-more"></i></p>
          <h1 class="dp-title02">
            {{detail.goodsname}}
          </h1>
          <div class="shop-detail__review" v-if="detail.isopenreview === 'T' && detail.reviewcnt > 0">
            <div class="product__rating atten-new">{{detail.reviewavg}} <span>({{detail.reviewcnt}})</span></div>
            <span class="shop-detail__more" @click="scrollMeTo('review')">리뷰보기</span>
          </div>

          <div class="header__badge-group no-scrollbar">
            <span class="dp-badge best atten-new" v-if="detail.isbestbadge == 'T'">BEST</span>
            <span class="dp-badge atten-new" v-if="detail.isnewbadge == 'T'">NEW</span>
            <span class="dp-badge atten-new" v-if="detail.iscouponbadge == 'T'">COUPON</span>
            <!-- <span class="dp-badge sm" v-if="detail.ispreordbadge == 'T'">pre-order</span> -->
            <span class="dp-badge sm atten-new" v-for="(badge, index) in detail.badge" :key="index">{{badge}}</span>
          </div>
        </header>
        <div class="info__body">
          <p class="dp-p-sm text-gray-700">
            {{detail.summary}}
          </p>

          <div class="shop-detail__hash-group">
            <span class="dp-hashtag" v-for="(keyword, index) in detail.keywordlist" :key="index"># {{keyword}}</span>
          </div>
        </div>
        <div class="quick_icon_wrap">
          <i class="dp-icon icon-heart" @click="changeWish(detail)" :class="{ on: detail.iswished == 'T' }"></i>
          <i class="dp-icon icon-share" @click="snsShareModal"></i>
      </div>
      </div>
    </section>
    <!-- // 상단 -->

    <!-- 상품 추가 정보 -->
    <section class="shop-detail__add-info">
      <div class="add-info__coupon">
        <div class="coupon-info">
          <!-- <p class="coupon__text dp-caption" v-if="isEmp">임직원가</p> -->
          <div class="coupon__amount">
            <p class="coupon__price">
              <span class="vollkorn">{{$util.maskComma(detail.saleamt)}}</span> 원
            </p>
            <span class="coupon__dc-percent vollkorn" v-if="detail.disrate > 0">{{detail.disrate}}%</span>
          </div>
          <p class="coupon__dc-price" v-if="detail.disrate > 0">
            <span class="vollkorn">{{$util.maskComma(detail.marketprice)}}</span> 원
          </p>
        </div>
        <b-button
          class="dp-btn text-white dp-btn-icon btn-h32"
          variant="gray-800"
          squared
          @click="couponDownload"
          v-if="(couponcnt > 0 || downCouponList.length > 0)"
        >
          <span>추가쿠폰받기</span>
          <i class="dp-icon icon-download"></i>
        </b-button>
      </div>
      <div class="text_row">
        <p style="font-weight: 400; font-size: 14px;">최대 {{$util.maskComma(this.totreserve)}}원 적립</p>
      </div>
      <div class="new_member" v-if="detail.isnewjoincoupon == 'T'">
        <p>신규회원 혜택가<br><span style="font-weight: 300; color:#ed3536; font-size: 14px;">(신규가입 쿠폰팩 적용 시)</span></p>
        <a href="/shop/promotion/welcome"><p class="new_member_price"><span class="vollkorn">{{$util.maskComma(this.totsaleamt)}}</span>원</p></a>
      </div>
      <hr v-else style="border:0; border-top:1px solid #ddd; margin:0;"/>

      <!-- 브랜드 정보 -->
      <!-- 07/16 숨김처리 -->
      <!-- <div class="add-info__brand" v-if="!$util.isNull(detail.brandidx)">
        <div class="brand__left">
          <figure class="brand__figure">
            <img :src="detail.brandimgurl" @error="replaceBrandImg"/>
          </figure>
          <b-button
            class="dp-btn dp-btn-icon"
            variant="outline-gray-800 not-hover btn-h30"
            @click="$router.push('/magazine/brand/detail/'+detail.brandidx)"
            squared
          >
            <span class="dp-caption">브랜드샵</span>
            <i class="dp-icon icon-more sm"></i>
          </b-button>
        </div>
        <div class="brand__right">
          <p class="dp-caption text-gray-700 mb-0">{{detail.headcopy}}
          </p>
        </div>
      </div> -->
      <!-- <footer class="add-info__footer">
        <div class="footer__btn" @click="changeWish(detail)">
          <i class="dp-icon icon-heart" :class="{ on: detail.iswished == 'T' }"></i>
          <span class="dp-caption">{{detail.wishcnt}}</span>
        </div>
        <div class="footer__btn" @click="snsShareModal">
          <i class="dp-icon icon-share"></i>
          <span class="dp-caption">공유</span>
        </div>
      </footer> -->
    </section>
    <!-- // 상품 추가 정보 -->

    <!-- 기타 정보 -->
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
          <p class="card_p" @click="openModal('noInterestCardModal')">카드사별 무이자 혜택 <i class="dp-icon icon-more"></i></p>
        </div>
      </div>
      <!-- 배송 정보 -->
      <!-- <div class="shop-detail__shipping dp-mb-30">
        <header class="etc-info__header">
          <h2 class="d-flex align-items-center dp-p font-weight-500 mb-0">
            배송정보
            <span class="dp-caption font-weight-500 text-secondary ml-2">{{detail.deliv.delivschtypename}}</span>
          </h2>

          <b-button
            class="dp-btn dp-btn-icon btn-h30 type02 not-hover rounded-pill"
            variant="outline-gray-800"
            @click="openModal('deliveryInfoModal')"
          >
            <span class="dp-caption text-gray-800">배송비 안내</span>
            <i class="dp-icon icon-more sm"></i>
          </b-button>
        </header>
        <div class="etc-info__body">
          <div class="shipping__info">
            <p class="d-flex align-items-center mb-0" >
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
        <header class="etc-info__header">
          <h2 class="d-flex align-items-center dp-p font-weight-500 mb-0">
            카드사 혜택 안내
          </h2>

          <b-button
            class="dp-btn dp-btn-icon btn-h30 type02 not-hover rounded-pill"
            variant="outline-gray-800"
            @click="openModal('noInterestCardModal')"
          >
            <span class="dp-caption text-gray-800">무이자 할부</span>
            <i class="dp-icon icon-more sm"></i>
          </b-button>
        </header>
        <div class="etc-info__body" v-if="discountInfo.disamount > 0">
          <div class="card__info">
            <p class="dp-p-sm text-gray-700 mb-2">
              {{discountInfo.content}}
            </p>
            <div class="d-flex card__info__flex">
              <p class="dp-p-sm mb-0">
                <span class="dp-p atten-new font-weight-600 text-primary">{{$util.maskComma(discountInfo.disamount)}}원 할인</span>
              </p>
              <span class="shop-detail__more" @click="openModal('cardBenefitModal')">혜택 더 보기</span>
            </div>
          </div>
        </div>
      </div> -->

      <!-- 혜택 정보 -->
      <!-- <div class="shop-detail__benefit">
        <header class="etc-info__header">
          <h2 class="d-flex align-items-center dp-p font-weight-500 mb-0">
            D.PLOT만의 혜택 모음
          </h2>
        </header>
        <div class="etc-info__body">
          <div class="benefit-info">
            <span class="info-text">
              <img src="@assets.mobile/img/shop/img-point.png" />
              <span class="dp-p-sm font-weight-500">적립금 최대  {{$util.isNull(reserve.purcfmamt)?0:reserve.purcfmamt}}% 적립</span>
            </span>

            <span class="info-text" @click="openModal('giftModal')">
              <img src="@assets.mobile/img/shop/img-gift.png" />
              <span class="dp-p-sm font-weight-500">구매사은품</span>
            </span>
          </div>
        </div>
      </div> -->
    </section>
    <!-- // 배송 정보 -->

    <!-- 상세 페이지 / 리뷰 / 문의 -->
    <section id="tab" ref="tab_height">
      <!-- 탭 영역 -->
      <div class="detail-tab-menu" :class="{ fixed: isScrollTab }">
        <div class="shop-detail__tab dp-tabs">
          <div class="nav-tabs" :class="{'active' : nowActive === 'detail'}" @click="scrollMeTo('detail')">
            <span>상품상세</span>
          </div>
          <div class="nav-tabs" :class="{'active' : nowActive === 'review'}" @click="scrollMeTo('review')" v-if="detail.isopenreview === 'T'">
            <span>리뷰({{detail.reviewcnt}})</span>
          </div>
          <div class="nav-tabs" :class="{'active' : nowActive === 'inquiry'}" @click="scrollMeTo('inquiry')">
            <span>문의({{this.listTotal}})</span>
          </div>
        </div>
      </div>
      <!-- 상품 상세 -->
      <article class="shop-detail__contents" ref="detail">
        <div class="contents__img-area" :class="{ on: isProductDetailMore }">
          <!-- todo: (수정) 상품 상세 템플릿 추가 0714    -->
          <!-- <detail-product /> -->
          <!-- // todo: (수정) 상품 상세 템플릿 추가 0714    -->
          <figure class="contents__figure" v-html="detail.mobile_content">
          </figure>

          <b-button
            v-if="!isProductDetailMore"
            class="dp-btn dp-btn-icon"
            variant="outline-gray-800 not-hover bg-white"
            squared
            @click="isProductDetailMore = true"
          >
            <span style="font-size: 14px;">상품 설명 더 보기</span>
            <i class="dp-icon icon-arrow-down"></i>
          </b-button>
        </div>

        <hr class="dp-hr" />

        <!-- 정보 안내 -->
        <ul class="contents__ul list-style-none">
          <li>
            <div class="contents__menu" @click="openModal('productInfoModal')">
              <span class="dp-p-sm font-weight-500">상품정보제공 고시</span>
              <i class="dp-icon icon-more"></i>
            </div>
          </li>
          <li>
            <div class="contents__menu" @click="openModal('sellerInfoModal')">
              <span class="dp-p-sm font-weight-500">판매자 정보</span>
              <i class="dp-icon icon-more"></i>
            </div>
          </li>
          <li>
            <div class="contents__menu" @click="openModal('exchangeModal')">
              <span class="dp-p-sm font-weight-500">교환 및 반품</span>
              <i class="dp-icon icon-more"></i>
            </div>
          </li>
        </ul>

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

        <hr class="dp-hr" ref="review"/>

        <!-- 서비스 소개 -->
        <!-- <div class="contents__service">
          <div class="container">
            <p class="dp-p font-weight-500 mb-3">Service</p>
            <ul class="service__ul list-style-none">
              <li>
                <div class="service__item" v-b-modal.serviceIntroModal>
                  <div class="service__circle">
                    <img
                      src="@/assets/common/icon/icon-document-black-34px.svg"
                      alt="아이콘"
                    />
                  </div>
                  <p class="service__caption">소개</p>
                </div>
                <b-modal
                  id="serviceIntroModal"
                  modal-class="dp-modal pop-over"
                  hide-footer
                  centered
                >
                  <template #modal-header="{ cancel }">
                    <h5 class="modal-title">서비스 소개</h5>
                    <i class="modal-close" @click="cancel()">
                      <img
                        src="@/assets/common/icon/icon-close-modal-30px.svg"
                        alt="닫기"
                      />
                    </i>
                  </template>
                  <div class="dp-mb-0">
                    <p class="modal-text02 mb-0 word-break-all">
                      디플롯은 특별한 혹은 소소한, 진중한 때론 재미난
                      브랜드와 제품 이야기가 있는 프리미엄 라이프스타일 쇼핑
                      플랫폼입니다.<br />
                      <br />
                      이야기 즉 플롯은 브랜드와 디자이너의 철학, 제품의
                      쓸모와 아름다움, 나아가 사용자의 삶 속에서 함께
                      만들어가는 이야기까지 아우르죠.<br />
                      <br />
                      모든 사람이 제품 이면의 이야기를 궁금해하는 것은
                      아니겠지만 디플롯은 단지 숫자로만 제품의 가치를 논하고
                      싶지 않았어요.<br />
                      이 제품이 왜 좋은 지, 누구를 위한 것인지, 어떻게
                      사용해야 하는 지에 대한 구체적이고 친절한 정보를
                      전달해야겠다고 생각했어요.<br />
                      <br />
                      다양한 제품의 플롯을 탐색하며 자신의 취향을 발견하고,
                      쇼핑을 통해 그 개성을 실현시키길 바라요.<br />
                      디플롯이 들려주는 형형색색 갖가지 플롯으로 지식과
                      감성을, 그리고 나만의 시간과 공간을 채워가는 즐거움을
                      경험해보세요
                    </p>
                  </div>
                </b-modal>
              </li>  -->
              <!--              <li>-->
              <!--                <div class="service__item" v-b-modal.guaranteeModal>-->
              <!--                  <div class="service__circle">-->
              <!--                    <img-->
              <!--                      src="@/assets/common/icon/icon-card-black-34px.svg"-->
              <!--                      alt="아이콘"-->
              <!--                    />-->
              <!--                  </div>-->
              <!--                  <p class="service__caption">개런티 카드</p>-->
              <!--                </div>-->

              <!--                &lt;!&ndash; 개런티 카드 Modal &ndash;&gt;-->
              <!--                <b-modal-->
              <!--                  id="guaranteeModal"-->
              <!--                  modal-class="dp-modal pop-over"-->
              <!--                  hide-footer-->
              <!--                  centered-->
              <!--                >-->
              <!--                  <template #modal-header="{ cancel }">-->
              <!--                    <h5 class="modal-title">개런티</h5>-->
              <!--                    <i class="modal-close" @click="cancel()">-->
              <!--                      <img-->
              <!--                        src="@/assets/common/icon/icon-close-modal-30px.svg"-->
              <!--                        alt="닫기"-->
              <!--                      />-->
              <!--                    </i>-->
              <!--                  </template>-->

              <!--                  <div>-->
              <!--                    <p class="modal-text02 text-center mb-0">-->
              <!--                      D.PLOT는 디자이너와 브랜드의 가치를 존중합니다. 따라서-->
              <!--                      위조품을 판매 하지 않습니다. 이를 위해 상시 모니터링-->
              <!--                      제도를 운영하며, 국내/해외 감정팀 및 본사 전문가 풀을-->
              <!--                      활용하여 연계하고 있습니다.-->
              <!--                    </p>-->
              <!--                  </div>-->
              <!--                </b-modal>-->
              <!--                &lt;!&ndash; // 개런티 카드 Modal &ndash;&gt;-->
              <!--              </li>-->
              <!-- <li>
                <div class="service__item" v-b-modal.asModal>
                  <div class="service__circle">
                    <img
                      src="@/assets/common/icon/icon-setting-black-34px.svg"
                      alt="아이콘"
                    />
                  </div>
                  <p class="service__caption">A/S</p>
                </div>
                <b-modal
                  id="asModal"
                  modal-class="dp-modal pop-over"
                  hide-footer
                  centered
                >
                  <template #modal-header="{ cancel }">
                    <h5 class="modal-title">A/S신청</h5>
                    <i class="modal-close" @click="cancel()">
                      <img
                        src="@/assets/common/icon/icon-close-modal-30px.svg"
                        alt="닫기"
                      />
                    </i>
                  </template>

                  <div>
                    <p class="modal-text02 mb-0">
                      D.PLOT에서 구매한 상품에 문제가 생겼나요?<br />
                      D.PLOT에서는 해당 상품의 AS 가능 여부를 확인하고 빠르고
                      정확한 AS 처리를 돕는 'AS전용 문의 서비스'를
                      운영합니다.<br />
                      <br />
                      마이페이지에 위치한 'AS전용 문의 서비스'를 통해 관련
                      내용을 접수하면 담당자 검토 후 신속하게 답변을
                      해드리겠습니다.
                    </p>
                  </div>
                </b-modal>
              </li>
              <li>
                <div class="service__item" v-b-modal.guaranteeModal>
                  <div class="service__circle">
                    <img
                      src="@/assets/common/icon/icon-card-black-34px.svg"
                      alt="아이콘"
                    />
                  </div>
                  <p class="service__caption">정품보증</p>
                </div>

                <b-modal
                  id="guaranteeModal"
                  modal-class="dp-modal pop-over"
                  hide-footer
                  centered
                >
                  <template #modal-header="{ cancel }">
                    <h5 class="modal-title">정품보증</h5>
                    <i class="modal-close" @click="cancel()">
                      <img
                        src="@/assets/common/icon/icon-close-modal-30px.svg"
                        alt="닫기"
                      />
                    </i>
                  </template>

                  <div>
                    <p class="modal-text02 mb-0">
                      D.PLOT에 입점한 업체는 브랜드 고유성을 가진 제조사 또는 각
                      브랜드와 정식으로 계약한 딜러사입니다.<br />
                      <br />
                      D.PLOT이 담아내는 브랜드와 상품의 가치를 받들고, 고객과의
                      지속적인 신뢰 관계를 맺기 위해 공식적인 방식으로 정직하게
                      생산한 ‘정품’만 선보이고자 판매자 선정과 검증에 각고의
                      노력을 기울입니다.
                    </p>
                  </div>
                </b-modal>
              </li>
            </ul>
          </div>
        </div> -->

        <!-- 고객센터 -->
        <!-- <div class="contents__cs">
          <div class="container">
            <div class="d-flex justify-content-between">
              <div class="">
                <p class="dp-p-sm mb-1 text-gray-700">고객센터</p>
                <a href="tel:1666-3642" class="cs__tel atten-new">1666-3642</a>

                <p class="dp-p-sm text-gray-700 mb-0">
                  월~금 10:00 ~ 16:00 까지<br />
                  점심시간 12:00 ~ 13:00 <br/>※휴뮤 - 토,일 및 공휴일
                </p>
              </div>

              <a
                href="tel:1666-3642"
                class="dp-btn btn btn-outline-gray-800 type02 not-hover dp-btn-icon btn-h30 rounded-pill"
              >
                <i class="dp-icon icon-tel"></i>
                <span class="font-weight-500">전화하기</span>
              </a>
            </div>
          </div>
        </div> -->
      </article>

      <!-- 리뷰 -->
      <review :productInfo="{goodsno:detail.goodsno}"
        id="review"
        ref="review_height"
        :isReview="isReview"
        v-if="detail.isopenreview === 'T'"
      ></review>
      <!-- 리뷰 종료 -->

      <hr class="dp-hr" />

      <!-- 문의 -->
      <article class="shop-detail__contents-inquiry" ref="inquiry">
        <div class="container">
          <div class="contents-inquiry__header">
            <p class="dp-p-sm font-weight-500 atten-new" style="font-size: 24px; font-weight: 600 !important;">Q&A<span class="text-gray-600 atten-new ml-1" style="font-size: 16px; font-weight: 400;">({{this.listTotal}})</span></p>

            <b-button
              class="dp-btn dp-btn-icon not-hover question-btn"
              variant="outline-gray-800"
              squared
              @click="openModal('QnaModal')"
            >
              <!-- <i class="dp-icon icon-question"></i> -->
              <span>상품문의 작성</span>
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
                  @click="changeType(list.id, index)"
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
                      <header class="question__header">
                        <div class="header__inner">
                          <span class="dp-caption font-weight-600">{{
                            list.isreply
                          }}</span>
                          <span class="dp-caption text-gray-700">{{
                            list.regdate
                          }}</span>
                          <span class="dp-caption text-gray-700">{{
                            $util.maskUserId(list.writer)
                          }}</span>
                        </div>

                        <template
                          v-if="(list.issecret == 'T' && !list.own)
                                  || list.isreply === '미답변'"
                        >
                        </template>
                        <template v-else>
                          <i
                            class="dp-icon icon-arrow-down sm"
                            v-b-toggle="`accordion-${index}`"
                          ></i>
                        </template>
                      </header>
                      <div class="question__body">
                        <template
                          v-if="list.issecret == 'T' && !list.own"
                        >
                          <p class="dp-p-sm text-gray-700 mb-0">
                            비밀글입니다.
                          </p>
                        </template>
                        <template v-else>
                          <p
                            class="dp-p-sm text-gray-700 mb-0"
                            v-html="list.content"
                          ></p>
                        </template>
                        <div class="inquiry__edit" v-if="list.own && list.isreply === '미답변'">
                          <span @click="productQnaReWirte(list)">수정</span>
                          <span class="dp-bar h10"></span>
                          <span @click="productQnaDelete(list.idx)">삭제</span>
                        </div>
                      </div>
                    </div>
                    <b-collapse
                      v-if="list.isreply !== '미답변'"
                      :id="`accordion-${index}`"
                      accordion="gnb-accordion"
                    >
                      <div class="inquiry__answer">
                        <p
                          class="dp-p-sm mb-0"
                          v-html="list.repcontent"
                        ></p>
                        <div class="answer__footer">
                          <span class="dp-caption text-gray-700">{{
                            list.repgetdate
                          }}</span>
                          <!-- <span class="dp-caption text-gray-700">{{
                            list.repuserid
                          }}</span> -->
                          <!-- <base-like /> -->
                        </div>
                      </div>
                    </b-collapse>
                  </div>
                </li>
              </ul>
              <b-button
                class="dp-btn dp-btn-icon"
                variant="outline-gray-800 not-hover type02"
                squared
                @click="MobilePage()"
                v-if="QnaListBtn"
              >
                <span>문의 더 보기</span>
                <i class="dp-icon icon-arrow-down"></i>
              </b-button>
            </template>
          </div>
        </div>
      </article>
    </section>
    <!-- // 상세 페이지 / 리뷰 / 문의 -->
    <div class="shop-detail__buy-btn" v-if="!isBuyBottomSheet">
      <div class="position-relative">
        <!-- <div class="shop-detail__docent">
          <span
            class="circle"
            :class="{ play: !isPlay, pause: isPlay }"
            @click="playDocent"
          ></span>
        </div> -->
        <b-button
          class="dp-btn text-white"
          variant="gray-800"
          squared
          @click="isBuyBottomSheet=true"
        >
          <span>{{btnBuy}}</span>
        </b-button>
      </div>
    </div>

    <bottom-sheet
      v-if="isBuyBottomSheet"
      :productInfo="detail"
      :items="selectedItem"
      :isProductInfo="true"
      @close="isBuyBottomSheet = false"
      @cart="handleCart"
      @buy="handleBuy"
    ></bottom-sheet>
  </div>
</template>

<script src="./Detail.js">
</script>

<style scoped>

  .card_p {color: #000; font-weight: 400;}
  .shop-detail__info {position: relative;}
  .quick_icon_wrap {
    display: flex;
    position: absolute;
    top: 30px;
    right: 20px;
    align-items: center;
  }
  .quick_icon_wrap i { margin-left: 5px; }
  .shop-detail .shop-detail__contents-inquiry .contents-inquiry__header {display: flex; justify-content: space-between; align-items: center;}
  .shop-detail .shop-detail__contents-inquiry .dp-btn {
    font-size: 14px;
    height: auto;
    padding: 10px 20px;
    width: auto !important;
    margin-bottom: 0;
  }
</style>