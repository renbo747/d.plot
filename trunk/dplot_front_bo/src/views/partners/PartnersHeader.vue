<template>
  <div class="top-menu-p"> <!-- 파트너사 관리자는 top-menu-p 클래스 사용 -->
    <div class="top clearfix">
      <div class="logo">
        <img src="../../assets/img/logo.png" alt="D.PLOT" class="link" @click="goMain">
        <span class="left-name">{{user.name}}</span>
      </div>
      <ul class="user">
        <li class="name-info">
          <span class="name">{{user.name}}</span>
          님, 안녕하세요.
        </li>
        <li class="btns">
          <a href="javascript:void(0);" class="btn btn-outline-light" @click="goInfo">정보수정</a>
          <a href="javascript:void(0);" class="btn btn-outline-light" @click="goFrontMain">쇼핑몰메인</a>
          <a href="javascript:void(0);" class="btn btn-dark" @click="logOut">로그아웃</a>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
export default {
  name: "PartnersHeader",
  data() {
    return {
      user : this.$util.getUser(this.$store.getters.CONSTANTS.MANAGER_SESSION)
    }
  },
  mounted() {

  },
  methods: {
    goInfo(){
      this.$router.push({name : 'partners.info.auth'});
    },
    goMain(){
      this.$router.push({name : 'partners.main.dashboard'});
    },
    goFrontMain: function(){
      window.open(process.env.VUE_APP_PC_DOMAIN, "_blank");
    },
    logOut: function(){
      let param = {};
      this.$http.post('/partners/logout', param).then(data => {
        this.$util.debug(data);

        this.$storage.removeLocalStorage(this.$store.getters.CONSTANTS.PARTNER_USER);
        this.$storage.removeLocalStorage(this.$store.getters.CONSTANTS.MANAGER_SESSION);
        this.$router.push({name: 'partners.login'});

      }).catch(error => {
        this.$util.debug(error);
      });
    }
  }
}
</script>

<style scoped>

</style>