<template>
  <main class="dp-loading" style="width: 100%; margin : 0;">
    <div class="container">
      <div class="dp-loading-wrap">
        <div class="dp-loading__img mb-2">
          <figure class="dp-loading__figure">
            <logo />
          </figure>
        </div>
        <div class="dp-loading__text__wrap dp-panel">
          <p class="dp-loading__text mb-0">카카오인증 처리중입니다.</p>
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
const kakaoHeader = {
  Authorization: process.env.VUE_APP_KAKAO_ADMIN_KEY,
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
      if (!Kakao.isInitialized()) {
        Kakao.init(process.env.VUE_APP_KAKAO_SCRIPT_KEY);
      }
      this.getKakaoToken(this.$route.query.code);
    });
  },
  methods: {
    getKakaoToken(code) {
      const data = {
        grant_type: "authorization_code",
        client_id: process.env.VUE_APP_KAKAO_API_KEY,
        redirect_uri:
          window.sessionStorage.getItem("domain") +
          process.env.VUE_APP_KAKAO_REDIRECT_URI,
        code: code,
      };
      const queryString = Object.keys(data)
        .map((k) => encodeURIComponent(k) + "=" + encodeURIComponent(data[k]))
        .join("&");

      this.$http
        .post("https://kauth.kakao.com/oauth/token?" + queryString, null, {
          headers: kakaoHeader,
          withCredentials: false,
        })
        .then((result) => {
          //엑세스토큰 설정
          Kakao.Auth.setAccessToken(result.access_token);

          //카카오 계정정보 조회
          this.getKakaoUserInfo();
        })
        .catch((err) => {
          this.$eventBus.$emit(
            "alert",
            "알림",
            "카카오 로그인처리중 에러가 발생하였습니다.",
            ()=>{
              window.close();
            }
          );
        });
    },
    getKakaoUserInfo() {
      let data = null;
      Kakao.API.request({
        url: "/v2/user/me",
        success: (response) => {
          this.$util.debug("카카오 계정 정보");
          this.$util.debug(response);

          //임시전화번호

          //카카오 계정 회원가입여부체크
          this.checkUserInfo(response);
        },
        fail: (error) => {
          this.$util.error(error);
          window.close();
        },
      });
    },
    checkUserInfo(data) {
      const params = {
        //전화번호, ci, 출생년도는 사용자 동의 항목
        snsmemberno: data.id,
        name: data.kakao_account.name,
        conninfo: data.kakao_account.ci,
        email: data.kakao_account.email,
        snsemail: data.kakao_account.email,
        //카카오(다다픽 임시 애플리케이션으로 접근시 핸드폰번호 접근 권한이 없어 휴대폰번호가 등록 x)
        //추후제거
        birthdate: data.kakao_account.birthyear + "" + data.kakao_account.birthday,
        //mobile : this.$util.isNull(data.kakao_account.phone_number)?'01095043625':data.kakao_account.phone_number,
        mobile: data.kakao_account.phone_number,
        token: Kakao.Auth.getAccessToken(),
        snstype: "ULT002", //SNS TYPE 카카오
      };

      /**************
       * 이용약관정보 조회
       **************/
      Kakao.API.request({
        url: "/v1/user/service/terms",
        success: function (res) {
          let list = res.allowed_service_terms;
          params.isadsms = "F";
          //params.isadpush = "F";
          params.isadmailing = "F";
          params.dadamembertype = "DMT001"; //일반회원
          list.forEach((item) => {
            switch (item.tag) {
              case "sms":
                params.isadsms = "T";
                break;
              case "email":
                params.isadmailing = "T";
                break;
              case "permanent":
                params.dadamembertype = "DMT002"; //평생회원
                break;
              default:
                break;
            }
          });
        },
        fail: function (error) {
          this.$eventBus.$emit(
            "alert",
            "알림",
            "카카오 로그인처리중 에러가 발생하였습니다.",
            ()=>{
              window.close();
            }
          );
        },
      });
      setTimeout(() => {
        if (!this.$util.isNull(params.mobile)) {
          let index = params.mobile.indexOf(" ");
          if (index >= 0) {
            params.mobile = "0" + params.mobile.substring(index + 1);
          }
        /*}else {
          alert("휴대폰번호가 정보가 없습니다.");
          window.close();
          return;*/
        }

        //생일설정
        if (data.kakao_account.has_birthday) {
          params.birthdate =  data.kakao_account.birthyear + "" + data.kakao_account.birthday;
          params.islunar =
            data.kakao_account.birthday_type == "SOLAR" ? "N" : "Y";
        }
        //성별설정
        if (data.kakao_account.has_gender) {
          params.gender = data.kakao_account.gender == "male" ? "M" : "F";
        }
        window.opener.popupCallback(params);
        window.close();
      }, 1000);
    },
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