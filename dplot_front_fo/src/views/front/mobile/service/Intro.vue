<template>
  <main class="service-intro">
    <div class="container">
      <div class="intro-body">
        <h2 class="intro-sub-title">믿고 사는 진짜 상품 ‘정품보증’</h2>

        <p class="intro-text">
          D.PLOT에 입점한 업체는 브랜드 고유성을 가진<br />
          제조사 또는 각 브랜드와 정식으로 계약한 딜러사입니다.<br />
          <br />
          D.PLOT이 담아내는 브랜드와 상품의 가치를 받들고, 고객과의 지속적인
          신뢰 관계를 맺기 위해 <br />
          공식적인 방식으로 정직하게 생산한 ‘정품’만 선보이고자 판매자 선정과
          검증에 각고의 노력을 기울입니다.
        </p>

        <div class="intro-contents-box">
          <figure class="intro-figure">
            <img
                src="@assets.mobile/img/service/img-m-certificate.png"
                alt="정품 보증 확인 이미지"
            />
          </figure>
        </div>

        <div class="">
          <h2 class="intro-sub-title">D.PLOT의 깐깐한 입점 절차</h2>

          <p class="intro-text">
            진짜 상품 추구라는 D.PLOT의 신념에 따라<br />
            모든 상품은 입점부터 고객 구매에 이르는 전 과정에서<br />
            지속적인 정품 검증 단계를 거치게 됩니다.
          </p>

          <div class="intro-procedure">
            <ul class="procedure-ul list-style-none">
              <li>
                <div class="procedure-item">
                  <figure class="procedure-figure">
                    <img src="@/assets/common/icon/icon-service-step01.svg" />
                  </figure>
                  <div class="procedure-contents">
                    <p class="procedure-title atten-new">STEP 01</p>
                    <span class="procedure-text">입점/제휴문의</span>
                  </div>
                </div>
              </li>
              <li>
                <div class="procedure-item">
                  <figure class="procedure-figure">
                    <img src="@/assets/common/icon/icon-service-step02.svg" />
                  </figure>
                  <div class="procedure-contents">
                    <p class="procedure-title atten-new">STEP 02</p>
                    <span class="procedure-text">
                      <span>판매자 검증</span>
                      <small class="procedure-small"
                      >브랜드 정식 계약 여부 확인</small
                      ></span
                    >
                  </div>
                </div>
              </li>
              <li>
                <div class="procedure-item">
                  <figure class="procedure-figure">
                    <img src="@/assets/common/icon/icon-service-step03.svg" />
                  </figure>
                  <div class="procedure-contents">
                    <p class="procedure-title atten-new">STEP 03</p>
                    <span class="procedure-text">입점계약</span>
                  </div>
                </div>
              </li>
              <li>
                <div class="procedure-item">
                  <figure class="procedure-figure">
                    <img src="@/assets/common/icon/icon-service-step04.svg" />
                  </figure>
                  <div class="procedure-contents">
                    <p class="procedure-title atten-new">STEP 04</p>
                    <span class="procedure-text">판매 상품 등록정보 검수</span>
                  </div>
                </div>
              </li>
              <li>
                <div class="procedure-item">
                  <figure class="procedure-figure">
                    <img src="@/assets/common/icon/icon-service-step05.svg" />
                  </figure>
                  <div class="procedure-contents">
                    <p class="procedure-title atten-new">STEP 05</p>
                    <span class="procedure-text">상품 판매 개시 승인</span>
                  </div>
                </div>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </div>
  </main>
</template>

<script>
export default {
  beforeCreate() {
    this.$store.commit("onSubHeaderOption", {
      title: "정품보증",
      searchIcon: false,
      cartIcon: false,
      homeIcon: false,
    });
  },
};
</script>
