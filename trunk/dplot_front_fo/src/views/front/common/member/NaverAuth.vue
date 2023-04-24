<template>
  <main class="dp-loading">
    <div class="container">
      <div class="dp-loading-wrap">
        <div class="dp-loading__img mb-2">
          <figure class="dp-loading__figure">
            <logo />
          </figure>
        </div>
        <div class="dp-loading__text__wrap dp-panel">
          <p class="dp-loading__text mb-0">네이버인증 처리중입니다.</p>
        </div>
        <div class="dp-loading__animation">
          <div class="lds-ring">
            <div></div>
            <div></div>
            <div></div>
            <div></div>
          </div>
        </div>
      </div>
    </div>
  </main>
</template>

<script>
const naverHeader = {
  Authorization: "",
  "Content-type": "application/x-www-form-urlencoded;charset=utf-8",
};
export default {
  created(){
    if(this.$store.state.platform == 'MAC001'){
      document.body.style.minWidth = '600px';
    } else{
      document.body.style.maxWidth = '600px';
    }
  },
  mounted() {
    window.addEventListener("load", () => {
      
      document.getElementById("HappytalkIframe").style.display = "none";
      const naverLogin = new naver.LoginWithNaverId({
        clientId: process.env.VUE_APP_NAVER_CLIENT_KEY,
      });

      naverLogin.init();
      naverLogin.getLoginStatus((status) => {
        this.$util.debug("네이버 계정 정보");
        this.$util.debug(JSON.stringify(naverLogin));
        if (status) {
          const params = {
            snsmemberno: naverLogin.user.id,
            name: naverLogin.user.name,
            conninfo: null,
            email: naverLogin.user.email,
            snsemail: naverLogin.user.email,
            //mobile : naverLogin.user.mobile.replace(/-/g,''),
            mobile: naverLogin.user.mobile,
            //birthdate : naverLogin.user.birthyear + naverLogin.user.birthday.replace(/-/g,''),
            birthdate: naverLogin.user.birthyear + naverLogin.user.birthday,
            gender: naverLogin.user.gender == "M" ? "M" : "F",
            token: naverLogin.accessToken.accessToken,
            snstype: "ULT001", //SNS TYPE NAVER
          };

          this.$http
            .get("/v1/nid/me", {
              headers: {
                Authorization: "Bearer " + naverLogin.accessToken.accessToken,
                withCredentials: false,
              },
            })
            .then((result) => {
              params.conninfo = result.response.ci;
              window.opener.popupCallback(params);
              window.close();
            })
            .catch((error) => {
              this.$eventBus.$emit(
                "alert",
                "알림",
                "네이버 로그인에 실패했습니다. 잠시후 다시 이용해주세요.",
                () => {
                  window.close();
                }
              );
            });
        } else {
          this.$eventBus.$emit(
            "alert",
            "알림",
            "네이버 로그인에 실패했습니다. 잠시후 다시 이용해주세요.",
            () => {
              window.close();
            }
          );
        }
      });
    });
  },
};
</script>

<style lang="scss">
.dp-loading {
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 0 auto;
  height: 100vh;
}
.dp-loading-wrap {
  text-align: center;
}

.dp-loading__figure {
  width: 200px;
  margin: 0 auto;
}

.dp-loading__text {
  font-size: 18px;
  letter-spacing: -0.72px;
  color: $gray-700;
  font-weight: 400;
}
.lds-ring {
  display: inline-block;
  position: relative;
  width: 80px;
  height: 80px;
}
.lds-ring div {
  box-sizing: border-box;
  display: block;
  position: absolute;
  width: 64px;
  height: 64px;
  margin: 8px;
  border: 8px solid $primary;
  border-radius: 50%;
  animation: lds-ring 1.2s cubic-bezier(0.5, 0, 0.5, 1) infinite;
  border-color: $primary transparent transparent transparent;
}
.lds-ring div:nth-child(1) {
  border-color: #ddd;
  animation-delay: -0.45s;
}
.lds-ring div:nth-child(2) {
  animation-delay: -0.3s;
}
.lds-ring div:nth-child(3) {
  animation-delay: -0.15s;
}
@keyframes lds-ring {
  0% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(360deg);
  }
}
</style>