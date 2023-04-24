<template>
  <main class="mypage-friend-invitation">
    <section class="friend-invitation__title">
      <h1>초대 친구목록</h1>
      <p class="mb-0 invitation__title">
        D.PLOT를 친구에게 소개하고, 친구와 함께 혜택을 받아보세요.
      </p>
      <p class="mb-0 invitation__description">
        최대 {{ rewardInfo.reclimitcnt }}명의 친구와 함께 혜택을 받을 수 있어요.
      </p>
    </section>
    <section class="benefit__section">
      <div class="benefit__area">
        <p class="mb-0 benefit__title">친구와 함께 받는 혜택</p>
        <div
          class="benefit-box__area"
          v-if="rewardInfo.recomtype == constants.RECOMTYPE_RCT_SAVE"
        >
          <div class="benefit-box">
            <p class="mb-0">
              친구에게 적립금 {{ $util.maskComma(rewardInfo.recjoinpoint) }}원
              지급
            </p>
          </div>
          <span class="dp-bar"></span>
          <div class="benefit-box">
            <p class="mb-0">
              나에게 적립금 {{ $util.maskComma(rewardInfo.revpoint) }}원 지급
            </p>
          </div>
        </div>
        <div
          class="benefit-box__area"
          v-else-if="rewardInfo.recomtype == constants.RECOMTYPE_RCT_EPOINT"
        >
          <div class="benefit-box">
            <p class="mb-0">
              친구에게 D포인트
              {{ $util.maskComma(rewardInfo.recjoinpoint) }}원 지급
            </p>
          </div>
          <span class="dp-bar"></span>
          <div class="benefit-box">
            <p class="mb-0">
              나에게 D포인트 {{ $util.maskComma(rewardInfo.revpoint) }}원
              지급
            </p>
          </div>
        </div>
        <div class="benefit-box__area" v-else>
          <div class="benefit-box">
            <p class="mb-0">
              친구에게 쿠폰 {{ $util.maskComma(rewardInfo.recjoincpname) }}원
              지급
            </p>
          </div>
          <span class="dp-bar"></span>
          <div class="benefit-box">
            <p class="mb-0">
              나에게 쿠폰 {{ $util.maskComma(rewardInfo.revcpname) }}원 지급
            </p>
          </div>
        </div>
      </div>
    </section>
    <section class="how-invitation__section">
      <div class="how-invitation__header">
        <p class="mb-0 header__title">친구 초대 방법</p>
      </div>
      <div class="how-invitation__body">
        <ul class="list-style-none">
          <li>
            <span class="step atten-new">Step 1</span
            ><span class="step__description"
              >친구가 회원가입할 때 추천인 아이디에 나의 아이디를 입력</span
            >
          </li>
          <li>
            <span class="step atten-new">Step 2</span
            ><span class="step__description"
              >친구가 회원 가입 완료하면 혜택 증정!</span
            >
          </li>
        </ul>
      </div>
      <div class="how-invitation__footer">
        <div>
          <ul class="list-style-none how-invitation__ul">
            <li @click="sendKakao">
              <div class="d-flex align-items-center invitation__div" style="cursor: pointer;">
                <i class="dp-icon icon-kakao sm02 icon-left"></i>
                <p class="mb-0 invitation__p">카카오톡으로 초대하기</p>
                <i class="dp-icon icon-more sm icon-right"></i>
              </div>
            </li>
            <li @click="getUseridBtn()">
              <div class="d-flex align-items-center invitation__div" style="cursor: pointer;">
                <i class="dp-icon icon-my sm02 icon-left"></i>
                <p class="mb-0 invitation__p">내 아이디 복사하기</p>
                 <input
                  type="text"
                  ref="idinput"
                  v-model="userid"
                  style="position: absolute; top: -9999em"
                />
                <i class="dp-icon icon-more sm icon-right"></i>
              </div>
            </li>
            <li  @click="getUrlBtn()">
              <div class="d-flex align-items-center invitation__div" style="cursor: pointer;">
                <i class="dp-icon icon-link sm02 icon-left"></i>
                <p class="mb-0 invitation__p">초대장 링크 복사하기</p>
                <input
                  type="text"
                  ref="urlinput"
                  v-model="url"
                  style="position: absolute; top: -9999em"
                />
                <i class="dp-icon icon-more sm icon-right"></i>
              </div>
            </li>
          </ul>
        </div>
      </div>
    </section>
  </main>
</template>

<script>
export default {
  data() {
    return {
      rewardInfo: {},
      constants: this.$store.getters["MEMBER"],
      userid: "",
      encuserid:"",
      userno: "",
      url: window.sessionStorage.getItem("domain") + "/invitation-event",
    };
  },
  methods: {
    /*******************************
     * 리워드 보상정보 조회
     ******************************/
    getRewardInfo() {
      this.$http.post("/etc/reward", {}).then((result) => {
        if (result.statusCode == 200) {
          this.userid = result.data.memberinfo.userid;
          this.encuserid = this.$CryptoJS.AES.encrypt(JSON.stringify(this.userid), this.$front.invitenoCrykey()).toString();
          this.url = this.url + "?no=" + encodeURIComponent(this.encuserid); 
          this.rewardInfo = this.$util.isNull(result.data.rewardinfo)?this.rewardInfo:result.data.rewardinfo;
        }
      });
    },
    /*******************************
     * 아이디 복사
     ******************************/
    getUseridBtn() {
      this.$refs.idinput.select();
      let isCopy = document.execCommand("copy");
      if (isCopy) {
        this.$toast.clear();
        this.$toast.open(
          "아이디가 복사되었습니다. </br> 입력하실 곳에 붙여넣기 해주세요."
        );
      }
    },
    /*******************************
     * 주소 복사
     ******************************/
    getUrlBtn() {
      this.$refs.urlinput.select();
      let isCopy = document.execCommand("copy");
      if (isCopy) {
        this.$toast.clear();
        this.$toast.open(
          "주소가 복사되었습니다. </br> 입력하실 곳에 붙여넣기 해주세요."
        );
      }
    },
    /*******************************
     * 카카오 초대 메시지
     ******************************/
    sendKakao() {
      Kakao.Link.sendDefault({
        objectType: "text",
        text: "[D.PLOT] 회원가입하시고 친구와 함께 가입혜택을 받으세요.",
        link:{
              mobileWebUrl: this.url,
              webUrl: this.url,
        }
      });
    },
  },
  mounted() {
    if (!Kakao.isInitialized()) {
      Kakao.init(process.env.VUE_APP_KAKAO_SCRIPT_KEY);
    }
    this.getRewardInfo();
  },
};
</script>
