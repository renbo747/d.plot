<template>
  <main class="dp-app-error">
    <header class="dp-app-error-header">
      <div class="error-header">
        <div class="container">
          <div class="error-header-logo">
            <figure class="error-header-logo__figure mb-0">
              <img
                src="@/assets/common/icon/icon-logo-dplot-header.svg"
                alt="logo"
              />
            </figure>
          </div>
        </div>
      </div>
    </header>
    <div class="container">
      <div class="dp-app-error-wrap">
        <div class="dp-app-error__img">
          <figure class="dp-app-error__figure dp-mb-40">
            <img
              src="@/assets/common/icon/icon-logo-dplot-pc.svg"
              alt="임시 로고입니다"
            />
          </figure>
        </div>
        <div class="dp-app-error__text__wrap">
          <p class="dp-app-error__text text-gray-700 mb-0">
            D.PLOT의 뉴스레터 구독을 취소하시면 더 이상 뉴스레터가 발송되지
            않습니다.<br />
            구독을 취소하시겠습니까?
          </p>
        </div>
        <div class="btn-group">
          <b-button
            class="dp-btn mr-10 not-hover"
            variant="outline-gray-800"
            squared
            @click="subcribeStay()"
          >
            <span class="error-button__text">구독유지</span>
          </b-button>
          <b-button
            class="dp-btn text-white"
            variant="gray-800"
            @click="subcribeCancel()"
            squared
          >
            <span class="error-button__text">구독취소</span>
          </b-button>
        </div>
      </div>
    </div>
  </main>
</template>

<script>
export default {
  methods: {
    /***********************
     * 구독유지 처리
     ***********************/
    subcribeStay() {
      let message = '구독을 유지해주셔서 감사합니다!  더 좋은 소식으로 만나뵙겠습니다.';
      this.$eventBus.$emit("alert", "알림", message, () => {
        this.$router.push({ name: "shop" });
      });
    },
    /***********************
     * 구독유지 취소처리
     ***********************/
    subcribeCancel() {
      this.$http.post("/etc/subscribeCancel",  {email:this.$route.query.email}).then((result) => {
        if (result.statusCode == 200) {
          let message = '구독이 취소되었습니다. 그동안 구독해주셔서 감사합니다.';
          this.$eventBus.$emit("alert", "알림", message, () => {
            this.$router.push({ name: "shop" });
          });
        }
      });
    },
  },
  
};
</script>
