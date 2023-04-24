<template>
  <main class="app-setting">
    <div class="container">
      <section class="dp-panel app-setting-section">
        <div class="app-setting__login d-flex">
          <div class="setting__login__text">
            <p
              class="setting__login__text__p mb-0 text-gray-700"
              v-if="isLogin === 'Login'"
            >
              로그인해주세요
            </p>
            <p
              class="setting__login__text__p mb-0 text-gray-700"
              v-if="isLogin === 'Logout'"
            >
              dada1234
            </p>
          </div>
          <div class="setting__login__button">
            <div
              class="setting__login__button__wrap"
              v-if="isLogin === 'Login'"
            >
              <b-button
                class="setting__login__btn dp-btn btn-h32 not-hover"
                variant="gray-800"
                pill
              >
                <span class="login__btn__text">로그인</span>
              </b-button>
            </div>
            <div
              class="setting__login__button__wrap"
              v-if="isLogin === 'Logout'"
            >
              <b-button
                class="setting__login__btn logout dp-btn btn-h32 not-hover"
                variant="outline-gray-800"
                pill
              >
                <span class="login__btn__text">로그아웃</span>
              </b-button>
            </div>
          </div>
        </div>
      </section>
      <hr class="dp-hr justify-margin" />
      <section class="dp-panel">
        <div class="app-setting__login d-flex mb-20">
          <div class="setting__login__text">
            <p class="setting__login__text__p mb-0 text-gray-700">
              자동 로그인
            </p>
          </div>
          <div class="setting__login__button">
            <div class="setting__login__button__wrap">
              <base-toggle :is-checked="isToggle" @change="handleToggle" />
            </div>
          </div>
        </div>
        <div class="app-setting__login d-flex">
          <div class="setting__login__text">
            <p class="setting__login__text__p mb-0 text-gray-700">
              생체인증 로그인
            </p>
          </div>
          <div class="setting__login__button">
            <div class="setting__login__button__wrap">
              <base-toggle :is-checked="isToggle02" @change="handleToggle02" />
            </div>
          </div>
        </div>
      </section>
      <hr class="dp-hr h1 mb-0" />
      <section class="dp-panel">
        <div class="app-setting__login d-flex">
          <div class="setting__login__text">
            <p class="setting__login__text__p mb-0 text-gray-700">
              마케팅정보 PUSH알림
            </p>
          </div>
          <div class="setting__login__button">
            <div class="setting__login__button__wrap">
              <base-toggle :is-checked="isToggle03" @change="handleToggle03" />
            </div>
          </div>
        </div>
      </section>
      <hr class="dp-hr h1 mb-0" />
      <section class="dp-panel pb-0">
        <div class="app-setting__login d-flex">
          <div class="setting__login__text">
            <p class="setting__login__text__p mb-0">APP 버전 1.1.1</p>
          </div>
          <div class="setting__login__version">
            <div
              class="setting__login__version__text"
              v-if="isLogin === 'Login'"
            >
              <p class="setting__version__text__p mb-0">최신버전입니다.</p>
            </div>
            <router-link to="/">
              <div
                class="setting__login__version__text logout d-flex"
                v-if="isLogin === 'Logout'"
              >
                <p class="setting__version__text__p mb-0 mr-1">업데이트</p>
                <i class="dp-icon icon-arrow-right sm"></i>
              </div>
            </router-link>
          </div>
        </div>
      </section>
    </div>
  </main>
</template>

<script>
export default {
  beforeCreate() {
    this.$store.commit("onSubHeaderOption", {
      title: "APP 설정",
      searchIcon: true,
      cartIcon: true,
      homeIcon: false,
    });
  },
  data() {
    return {
      //로그인과 비로그인 나누기 Login / Logout
      isLogin: "Login",
      isToggle: false,
      isToggle02: true,
      isToggle03: false,
    };
  },
  methods: {
    handleToggle(value) {
      this.isToggle = value;
    },
    handleToggle02(value) {
      this.isToggle02 = value;
    },
    handleToggle03(value) {
      this.isToggle03 = value;
    },
  },
  mounted:{
    
  }
};
</script>
