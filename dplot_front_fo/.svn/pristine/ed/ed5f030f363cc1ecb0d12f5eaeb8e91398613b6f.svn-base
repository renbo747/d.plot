<template>
  <div class="mypage mypage-grade">
    <header class="mypage__header">
      <div class="header-title">나의 등급안내</div>
    </header>
    <!-- 등급 표시 -->
    <section class="grade__summary">
      <figure class="summary__figure">
        <img  :src="`${require('@/assets/mobile/img/mypage/grade/' + gradeData[userIndex].img)}`" />
      </figure>

      <div class="summary__info">
        <h1 class="info__text">
          <b>{{ memberinfo.name }}님</b>의  {{ month }}월 등급은
          <b>{{ memberinfo.memlvtypenm }}</b
          >입니다.
        </h1>
        <span class="info__period">등급 산정기간 {{ $util.getFormatDate(startdate, ".") }}~{{
            $util.getFormatDate(enddate, ".")
          }}</span>
      </div>
    </section>

    <!-- 예상 등급 -->
    <section class="grade__expectation grade-section">
      <div class="container">
        <h2 class="grade__title">다음달 예상등급 : <b>{{gradeData[nextIndex].label}}</b></h2>
        <div class="grade__body">
          <table class="dp-table text-center">
            <thead>
              <tr>
                <th>현 구매금액</th>
                <th>현 구매건수</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>
                  <b class="atten-new font-weight-600">{{
                    $util.maskComma(orderinfo.ordamt)
                  }}</b>
                  원
                </td>
                <td>
                  <b class="atten-new font-weight-600">{{
                    orderinfo.ordcnt
                  }}</b>
                  건
                </td>
              </tr>
            </tbody>
          </table>

          <p class="expectation__p">
            이번달에 추가로 <b>{{  orderinfo.ordcnt }}</b
            >건 <b>{{ $util.maskComma(orderinfo.ordamt)}}</b
            >원 구매하시면 다음달에 <b>{{gradeData[nextIndex].label}}</b>등급으로 향상됩니다.
          </p>
        </div>
      </div>
    </section>

    <!-- 등급별 혜택 -->
    <section class="grade__benefit grade-section">
      <div class="container">
        <div class="benefit-box">
          <div class="benefit-header">
            <template v-for="(list, index) in gradeData">
              <div
                class="header-relative"
                :class="{ active: currentBenefit === list.grade }"
                :style="{
                  height: `${50 * index}px`,
                }"
                :key="index"
              >
                <div class="header__grade">
                  <p class="grade__p" :style="`color: ${list.color}`">
                    {{ list.label }}
                  </p>
                  <figure class="grade__figure">
                    <img
                      :src="`${require('@/assets/mobile/img/mypage/grade/' +
                        list.src)}`"
                    />
                  </figure>
                </div>
              </div>
            </template>
          </div>
          <div class="benefit-body">
            <template v-for="(list, index) in gradeData">
              <ul
                class="body__ul list-style-none"
                :style="{
                  marginTop: `-${50 * index}px`,
                }"
                :key="index"
              >
                <li v-for="(data, index) in list.benefitPcList.list" :key="index">
                  <span class="body__text">{{ data }}</span>
                </li>
              </ul>
            </template>
          </div>
        </div>
      </div>
    </section>

    <!-- 유의사항 -->
    <section class="grade__etc grade-section">
      <div class="container">
        <h2 class="grade__title">유의사항</h2>
        <ul class="ul-dot">
          <li>회원등급은 구매금액을 기준으로 매월 1일에 반영합니다.</li>
          <li>
            구매금액은 결제완료된 주문에 대한 실 결제 금액을 기준으로
            적용합니다.
          </li>
          <li>
            할인 쿠폰은 매월 1일에 발행되며 [마이페이지 > 쿠폰] 에서 다운받으실
            수 있습니다. 직접 다운을 받으신 후 사용이 가능합니다.
          </li>
          <li>
            생일 축하 쿠폰은 생년월일을 기준으로 생일 7일 전에 자동 발행되며,
            발행 시의 회원등급을 적용합니다.
          </li>
          <li>
            회원 등급의 종류, 규모, 지급방식은 내부 정책에 의하여 수시로 변경될
            수 있습니다.
          </li>
          <li>적립금의 유효기간은 그 적립일로부터 12개월입니다.</li>
        </ul>
      </div>
    </section>
  </div>
</template>

<script src="@views.mobile/mypage/grade/Index.js"></script>
