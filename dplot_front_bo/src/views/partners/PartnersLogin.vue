<template>
  <div class="login-wrap" @keyup.enter="login">
    <img src="../../assets/img/logo-login-partner.png" alt="">
    <div class="input-id">
      <input type="text" v-model='id' placeholder="아이디">
    </div>
    <div class="input-pw">
      <input type="password" v-model='pw' placeholder="비밀번호">
    </div>
 
    <div class="clearfix">
      <div class="fl">
        <input type="checkbox" id="rememberID" v-model="idRememberCheck"><label for="rememberID">아이디 기억하기</label>
      </div>
      <div class="fr">
        <a class="link" @click="openFindIdPassword">아이디/비밀번호찾기</a>
      </div>
    </div>
    <button type="button" class="btn blue btn-login" v-on:click="login">로그인</button>
    <div class="login-info-p">
      <div>
        <strong>성공적인 비즈니스 파트너!</strong><br>
        <strong class="large-txt txt-blue">D.PLOT</strong>과 함께 하세요!
      </div>
      <button type="button" class="btn blue-line btn-login" @click="joinPartner">입점신청</button>
    </div>
    <p class="login-copy">Copyright ⓒ D.PLOT All rights reserved.</p>
  </div>
</template>

<script>
import PartnersFindIdPassword from '@views/partners/PartnersFindIdPassword.vue';
import SecondAuth from '@views/partners/SecondAuth.vue';

export default {
  name: 'PartnersLogin',
  data () {
    return {
      id: '',
      pw: '',
      chargename: '',
      phone: '',
      idRememberCheck: false,
      result: Object,
    }
  },
  mounted() {
    let rememberId = this.$cookies.get("partners_remember_id");

    if(rememberId !== null){
      this.id = rememberId;
    }

    let errorCode = this.$route.query.error;
    if('401' === errorCode){
      this.errorMsg('로그인 세션이 중복/만료 되었습니다. \n다시 로그인 바랍니다.');
      this.$router.push("/partners/login");
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

        this.$http.post('/partners/login_act', param).then(result => {

          if(result.status === 200) {
            this.result = result;
            if(process.env.NODE_ENV === 'local'){
              this.successLogin();
            }
            else{
              this.openSecondAuth();
            }
            /* if(result.reqDealst !== 'RDS002'){
              this.$router.push({name: 'common.partnership.wait'});
            } else {
              this.$storage.setLocalStorage(this.$store.getters.CONSTANTS.PARTNER_USER, result);
              this.$storage.setLocalStorage(this.$store.getters.CONSTANTS.MANAGER_SESSION, result);
              this.$storage.removeLocalStorage(this.$store.getters.CONSTANTS.ADMIN_USER);

              if (this.idRememberCheck) {
                this.$cookies.set("partners_remember_id", this.id, "7d");
              }
              this.$router.push({name: 'partners.main.dashboard'});
            }*/
          }

        }).catch(error =>{
          this.$util.debug(error);
        });
    },
    joinPartner() {
      this.$router.push({name : 'common.partnership.auth'});
    },
    errorMsg: function(msg){
      alert(msg);
    },
    openFindIdPassword(){
      this.$modal.show(PartnersFindIdPassword, {
        'modalComp': PartnersFindIdPassword,
        'params': {},
        'callbackFn': this.closePopup
      }, {
        name: "commonModal",
        draggable: false,
        resizable: false,
        width: '100%',
        height: 'auto',
      })
    },
    openSecondAuth(){
      this.$modal.show(SecondAuth, {
        'modalComp': SecondAuth,
        'params': {
          name: this.result.chargename,
          id: this.result.id,
          phone: this.result.chargemobile,
        },
        'callbackFn': this.closeSecondAuthPopup
      }, {
        name: "commonModal",
        draggable: false,
        resizable: false,
        width: '100%',
        height: 'auto',
      })
    },    
    closeSecondAuthPopup(result) {
      if(result.isAuthComplete==true){
        this.successLogin();
      }
    },
    successLogin: function(){
      if(this.result.reqDealst !== 'RDS002'){
          this.$router.push({name: 'common.partnership.wait'});
      } else {
        this.$storage.setLocalStorage(this.$store.getters.CONSTANTS.PARTNER_USER, this.result);
        this.$storage.setLocalStorage(this.$store.getters.CONSTANTS.MANAGER_SESSION, this.result);
        this.$storage.removeLocalStorage(this.$store.getters.CONSTANTS.ADMIN_USER);
        if (this.idRememberCheck) {
          this.$cookies.set("partners_remember_id", this.id, "7d");
        }
        this.$router.push({name: 'partners.main.dashboard'});
      }
    },
    closePopup() {
    }
  }
}
</script>