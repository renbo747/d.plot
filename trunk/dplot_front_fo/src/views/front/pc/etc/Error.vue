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
          <figure class="dp-app-error__figure mb-0">
            <img
              src="@/assets/common/icon/icon-logo-dplot-pc.svg"
              alt="임시 로고입니다"
            />
          </figure>
        </div>
        <div class="dp-app-error__title">
          <p class="dp-app-error__title__p">
            서비스 이용에 불편을 드려 죄송합니다.
          </p>
        </div>
        <div class="dp-app-error__text__wrap">
          <p class="dp-app-error__text text-gray-700 mb-0">
            입력하신 페이지의 주소가 잘못 입력되었거나, 변경 혹은 삭제되어
            요청하신 페이지를 찾을 수 없습니다.<br />
            입력하신 주소가 정확한지 다시 한번 확인해주세요.
          </p>
          <!-- <p class="dp-app-error__text text-gray-700 mb-0" v-if="code != '404' && message != ''">
            {{message}}
          </p>
          <p class="dp-app-error__text text-gray-700 mb-0" v-else>
            서버에서 정보를 가져오는 도중 오류가 발생하였습니다.<br />
            잠시 후 다시 시도해주세요.
          </p> -->
        </div>
        <div class="btn-group">
          <b-button
            class="dp-btn mr-10 not-hover"
            variant="outline-gray-800"
            squared
            @click="historyBack"
          >
            <span class="error-button__text">이전페이지</span>
          </b-button>
          <b-button
            class="dp-btn text-white"
            variant="gray-800"
            @click="goToMain"
            squared
          >
            <span class="error-button__text">메인 페이지</span>
          </b-button>
        </div>
      </div>
    </div>
  </main>
</template>

<script>
export default {
  data() {
    return {
      code: '',
      message : ''
    };
  },
  mounted() {
    this.code = this.$route.params.code;

    if(this.$route.query.message) {
      this.message = this.$route.query.message;
    }
  },
  methods: {
    historyBack() {
      this.$router.go(-1);
    },
    goToMain() {
      this.$router.push("/shop");
    }
  },
};
</script>
