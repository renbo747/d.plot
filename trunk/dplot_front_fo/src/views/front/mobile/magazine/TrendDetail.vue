<template>
  <main class="magazine-trend-detail">
    <section class="trend-detail__header__section">
      <div class="container">
        <h1>{{ info.subject }}</h1>
        <div class="d-flex align-content-center justify-content-between">
          <h2>{{info.desc}}</h2>
          <div class="footer__btn"  @click="snsShareModal()">
            <i class="dp-icon sm02 icon-share"></i>
          </div>
        </div>
      </div>
    </section>
    <section class="detail-contents__wrap">
        <div class="contents__box">
          <div class="container">
            <div v-html="info.mobile_content"/>
          </div>
          <!-- <div > -->
            <!-- <h2 class="trend-detail__title">
              빛의 아름다움과 기능의 균형을 추구합니다
            </h2>
            <p class="mb-0 trend-detail__description">
              AGO는 단순하고 정직하며 세련된 미학을 중시합니다. 우리는 이러한
              가치를 공유하고 생산에 대한 헌신을 통해 아이디어를 실현하는
              디자이너와 협력합니다. 모든 세부 사항이 제품을 정의한다는 것을
              인정합니다.
            </p> -->
          <!-- </div> -->
        </div>
      </section>
  </main>
</template>

<script src="./TrendDetail.js"></script>

<style scoped>
  h1 {margin-bottom: 10px; font-size: 18px; font-weight: 600; display: block !important;}
  h2 {font-size: 14px; font-weight: 400; color: #666; margin: 0; line-height: 1; display: flex; align-items: center;}
  .container {padding:0;}
  .trend-detail__header__section {border-bottom:1px solid #000; padding:0; margin:0 0 0; }
  .trend-detail__header__section .container {padding:20px 0; margin:0 20px 0; width: auto;}
</style>