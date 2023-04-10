<template>
  <div class="sns-share__link-group">
    <a @click="sharefacebook()">
      <img
        src="@assets.mobile/img/icon/icon-share-facebook.png"
        alt="페이스북 공유하기"
      />
    </a>
    <a @click="shareKakao()">
      <img
        src="@assets.mobile/img/icon/icon-share-kakao.png"
        alt="카카오톡 공유하기"
      />
    </a>
    <a @click="sendSms()" v-if="platform !== 'MAC001'">
      <img
        src="@assets.mobile/img/icon/icon-share-mail.png"
        alt="메일 공유하기"
      />
    </a>
    <a @click="getUrlBtn()">
      <img
        src="@assets.mobile/img/icon/icon-share-url.png"
        alt="URL 공유하기"
      />
      <input
        type="text"
        ref="urlinput"
        v-model="url"
        style="position: absolute; top: -9999em"
      />
    </a>
  </div>
</template>
<script>
export default {
  props: {
    param: Object,
  },
  data() {
    return {
      url: document.location.href,
      platform: window.sessionStorage.getItem("platform"),
      serverCallbackArgs:{
        id: '',
        key: '',
      }
    };
  },
  methods: {
    /***************************
     * 카카오톡 공유하기
     ***************************/
    shareKakao() {

      this.serverCallbackArgs.id = "testid";
      this.serverCallbackArgs.key = "testkey";
      this.param.kakao.serverCallbackArgs = this.serverCallbackArgs;
    
      //alert("ggggg:"+this.param.kakao.serverCallbackArgs.id);
      Kakao.Link.sendDefault(this.param.kakao);
    },
    /*****************************
     * 페이스북 공유하기
     ******************************/
    sharefacebook() {
      let firstConvert = window.location.pathname.substr(
        window.location.pathname.indexOf("/", 1)
      );
      let type = firstConvert.substr(1, firstConvert.indexOf("/", 1) - 1);
      let code = window.location.href.substr(
        window.location.href.lastIndexOf("/") + 1,
        window.location.href.length
      );

      if (type !== "brand" && type !== "promotion") {
        type = "goods";
      }

      let linkUrl =
        process.env.VUE_APP_SERVER_URL + "/api/shop/share/" + type + "/" + code;

      window.open("http://www.facebook.com/sharer.php?u=" + linkUrl);
    },
    /*****************************
     * 주소복사
     ******************************/
    getUrlBtn() {
      this.$refs.urlinput.select();
      let isCopy = document.execCommand("copy");
      this.$refs.urlinput.blur();
      if (isCopy) {
        this.$toast.clear();
        this.$toast.open(
          "주소가 복사되었습니다. </br> 입력하실 곳에 붙여넣기 해주세요."
        );
      }
    },
    /***************************
     * SMS 보내기
     ***************************/
    sendSms() {
      let agent = navigator.userAgent.toLowerCase();
      if (agent.indexOf("android") > -1) {
        location.href = "sms:" + "" + "?" + "body=" + this.url;
      } else if (agent.indexOf("ios") > -1 || agent.indexOf("iphone") > -1) {
        location.href = "sms:" + "" + "&" + "body=" + this.url;
      } else {
        "sms:" + "" + "?" + "body=" + this.url;
      }
    },
  },
  mounted() {
    if (!Kakao.isInitialized()) {
      Kakao.init(process.env.VUE_APP_KAKAO_SCRIPT_KEY);
    }
  },
};
</script>

<style>
</style>
