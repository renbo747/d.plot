<template>
  <div class="login-wrap" @keyup.enter="login">
    <img src="../../assets/img/logo-login.png" alt="">
    <div class="input-id">
      <input type="text" v-model='id' placeholder="아이디" >
    </div>
    <div class="input-pw">
      <input type="password" v-model='pw' placeholder="비밀번호">
    </div>
    <input type="checkbox" id="rememberID" v-model="idRememberCheck"><label for="rememberID">아이디 기억하기</label>
    <button type="button" class="btn blue btn-login" v-on:click="login">로그인</button>
    <div class="login-info">
      <ul>
        <li>아이디/비밀번호 분실 시 IT개발팀으로 연락주시기 바랍니다.</li>
        <li>IT개발팀 : 02-2008-5732</li>
      </ul>
    </div>
    <p class="login-copy">Copyright ⓒ D.PLOT All rights reserved.</p>
  </div>
</template>

<script>

export default {
  name: 'AdminLogin',
  data () {
    return {
      id: '',
      pw: '',
      idRememberCheck: false
    }
  },
  mounted() {
    let rememberId = this.$cookies.get("remember_id");

    if(rememberId !== null){
      this.id = rememberId;
    }

    let errorCode = this.$route.query.error;
    if('401' === errorCode){
      this.errorMsg('로그인 세션이 중복/만료 되었습니다. \n다시 로그인 바랍니다.');
      this.$router.push("/login");
    }
  },
  methods: {
    login: function(){
        let param = {
          id : this.id,
          pw : this.pw
        }

        if(this.$util.isNull(this.id)) {
          alert("아이디를 입력해주세요.");
          return ;
        } else if(this.$util.isNull(this.pw)) {
          alert("비밀번호를 입력해주세요.");
          return ;
        }

        this.$http.post('/admin/login_act', param).then(result => {

          if(result.status === 200) {
            this.$storage.setLocalStorage(this.$store.getters.CONSTANTS.ADMIN_USER, result);
            this.$storage.setLocalStorage(this.$store.getters.CONSTANTS.MANAGER_SESSION, result);
            this.$storage.removeLocalStorage(this.$store.getters.CONSTANTS.PARTNER_USER);

            if (this.idRememberCheck) {
              this.$cookies.set("remember_id", this.id, "7d");
            }

            this.$router.push({name: 'admin.main.dashboard'});
          }
        }).catch(error =>{
          this.$util.debug(error);
        });
    },
    errorMsg: function(msg){
      alert(msg);
    }
  }
}
</script>