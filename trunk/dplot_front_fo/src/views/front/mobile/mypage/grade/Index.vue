<template>
  <main class="mypage-grade">
    <!-- 등급 표시 -->
    <section class="grade__summary">
      <figure class="summary__figure">
        <img :src="`${require('@/assets/mobile/img/mypage/grade/' + gradeData[userIndex].img)}`"/>
      </figure>

      <div class="summary__info">
        <h1 class="info__text">
          <b>{{ memberinfo.name }}님</b>의 {{ month }}월 등급은
          <b>{{ memberinfo.memlvtypenm }}</b
          >입니다.
        </h1>
        <span class="info__period"
          >등급 산정기간 {{ $util.getFormatDate(startdate, ".") }}~{{
            $util.getFormatDate(enddate, ".")
          }}</span
        >
      </div>
    </section>

    <!-- 예상 등급 -->
    <section class="grade__expectation grade-section">
      <div class="container">
        <h2 class="grade__title">다음달 예상등급 : {{gradeData[nextIndex].label}}</h2>
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
        </div>
      </div>
    </section>

    <!-- 등급 필요 내역 -->
    <section class="grade__necessity grade-section">
      <div class="container">
        <h2 class="grade__title">{{nextmonth}}월에 {{gradeData[nextIndex].label}}로 등업하려면</h2>
        <div class="grade__body">
          <div class="">
            <p class="necessity__p">
              추가 <span>{{ this.requiredAmount | comma }}원</span> 구매 필요
            </p>
            <base-progress :percent="getAmountPercent">
              <template #mark>
                <span>{{ $util.maskComma(orderinfo.ordamt) }}원</span>
                <span>{{ $util.maskComma(userIndex== 4?gradeData[userIndex].maxamt : gradeData[nextIndex].maxamt) }}원</span>
              </template>
            </base-progress>
          </div>
          <div class="dp-mt-30">
            <p class="necessity__p">
              추가 <span>{{ requiredCount }}건</span> 구매 필요
            </p>
            <base-progress :percent="getCountPercent">
              <template #mark>
                <span>{{ orderinfo.ordcnt }}건</span>
                <span>{{ userData.grade.upgradeCount }}건</span>
              </template>
            </base-progress>
          </div>
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
                  height: `${20 * index}px`,
                  borderColor: currentColor,
                  '--border-color': currentColor,
                }"
                :key="index"
                @click="setData(index)"
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
          <div class="benefit-body" :style="{ borderColor: currentColor }">
            <div class="">
              <h3 class="body__title">
                <span
                  class="title__span"
                  v-html="currentBenefitData.title"
                ></span>
                <span v-if="currentBenefitData.recently !== null"
                  >(최근 {{ currentBenefitData.recently }}개월 누적 기준)</span
                >
              </h3>
            </div>
            <ul class="body__ul list-style-none">
              <li v-for="(data, index) in currentBenefitData.list" :key="index">
                <span class="body__number">{{ index + 1 }}</span>
                <span class="body__text">{{ data }}</span>
              </li>
            </ul>
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
  </main>
</template>

<script src="./Index.js"></script>
