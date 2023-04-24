<template>
  <main class="dp-withdraw-complete">
    <div class="container">
      <section class="withdraw-complete__body">
        <div class="d-flex flex-column align-items-center">
          <div class="dp-logo_-area">
            <logo />
          </div>
          <div class="withdraw-complete__text-area">
            <p class="mb-0 dp-p-sm withdraw-complete__text">
              회원 탈퇴가 완료되었습니다 <br />
              그동안 서비스를 이용해주셔서 감사드립니다.
            </p>
          </div>
        </div>
      </section>
      <section class="withdraw-complete__footer">
        <div class="btn-area">
          <b-button
            class="dp-btn text-white"
            variant="gray-800"
            squared
            @click="goToMain"
          >
            <span>메인으로 이동</span>
          </b-button>
        </div>
      </section>
    </div>
  </main>
</template>

<script>
export default {
  beforeCreate() {
    this.$store.commit("onSubHeaderOption", {
      title: "회원탈퇴완료",
      searchIcon: false,
      cartIcon: false,
      homeIcon: true,
    });
  },
  methods: {
    goToMain() {
      this.$router.push("/shop");
    },
  },
};
</script>

<style scoped></style>
