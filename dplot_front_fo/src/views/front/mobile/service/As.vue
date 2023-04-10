<template>
  <main class="service-as">
    <div class="container">
      <div class="as-body">
        <div class="as-contents">
          <h2 class="as-sub-title">신속하고 편리한 'AS전용 문의 서비스'</h2>

          <p class="as-text">
            D.PLOT에서 구매한 상품에 문제가 생겼나요?<br />
            D.PLOT에서는 해당 상품의 AS 가능 여부를 확인하고 빠르고 정확한 AS
            처리를 돕는 'AS전용 문의 서비스'를 운영합니다.<br />
            <br />
            마이페이지에 위치한 'AS전용 문의 서비스'를 통해 관련 내용을
            접수하면 담당자 검토 후 신속하게 답변을 해드리겠습니다.<br />
            <br />
            단, 입점사 및 상품 특성에 따라 AS가 불가한 상품이 있을 수 있으니
            사전 양해 부탁드립니다.
          </p>
          <b-button class="dp-btn text-white" variant="gray-800" squared @click="goPage()">
            <span>AS전용 문의 서비스 바로가기</span>
          </b-button>
        </div>
        <figure class="as-figure">
          <img src="@/assets/common/img/img-as.jpg" alt="as 이미지" />
        </figure>
      </div>
    </div>
  </main>
</template>

<script>
export default {
  beforeCreate() {
    this.$store.commit("onSubHeaderOption", {
      title: "AS전용 문의 서비스",
      searchIcon: false,
      cartIcon: false,
      homeIcon: false,
    });
  },
  methods:{
    goPage(){
      this.$router.push({name:'mypage-as-apply-list',params:{init:true}});
    }
  }
};
</script>
