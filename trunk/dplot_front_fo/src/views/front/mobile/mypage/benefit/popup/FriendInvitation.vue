<template>
  <b-modal
    id="friendInvitationModal"
    modal-class="dp-modal page-layer friend-invitation-modal"
    scrollable
    :hide-footer="true"
  >
    <!-- Page Modal Header -->
    <template #modal-header="{ cancel }">
      <h5 class="modal-title">친구초대</h5>
      <i class="modal-close" @click="cancel()">
        <img src="@/assets/common/icon/icon-close-modal-30px.svg" alt="닫기" />
      </i>
    </template>

    <!-- Page Modal Body -->
    <div class="page-layer__body">
      <section class="benefit__section">
        <p class="mb-0 invitation__title">
          D.PLOT를 친구에게 소개하고, 친구와 함께 혜택을 받아보세요.
        </p>
        <p class="mb-0 invitation__description">
          최대 {{ rewardInfo.reclimitcnt }}명의 친구와 함께 혜택을 받을 수
          있어요.
        </p>
        <div class="benefit__area">
          <p class="mb-0 benefit__title">친구와 함께 받는 혜택</p>
          <!-- 적립금 -->
          <ul
            class="list-style-none ul-dot"
            v-if="rewardInfo.recomtype == constants.RECOMTYPE_RCT_SAVE"
          >
            <li>
              <span
                >친구에게 적립금
                {{ $util.maskComma(rewardInfo.recjoinpoint) }}원 지급</span
              >
            </li>
            <li>
              <span
                >나에게 적립금 {{ $util.maskComma(rewardInfo.revpoint) }}원
                지급</span
              >
            </li>
          </ul>
          <ul
            class="list-style-none ul-dot"
            v-else-if="rewardInfo.recomtype == constants.RECOMTYPE_RCT_EPOINT"
          >
            <li>
              <span
                >친구에게 D포인트
                {{ $util.maskComma(rewardInfo.recjoinpoint) }}P 지급</span
              >
            </li>
            <li>
              <span
                >나에게 D포인트 {{ $util.maskComma(rewardInfo.revpoint) }}P
                지급</span
              >
            </li>
          </ul>
          <ul class="list-style-none ul-dot" v-else>
            <li>
              <span>친구에게 쿠폰 {{ rewardInfo.recjoincpname }} 지급</span>
            </li>
            <li>
              <span>나에게 쿠폰 {{ rewardInfo.revcpname }} 지급</span>
            </li>
          </ul>
        </div>
      </section>
      <hr class="dp-hr justify-margin" />
      <section class="how-invitation__section">
        <div class="how-invitation__header">
          <p class="mb-0 header__title">친구 초대 방법</p>
        </div>
        <div class="how-invitation__body">
          <ul class="list-style-none">
            <li>
              <span class="step">Step 1</span
              ><span class="step__description"
                >친구가 회원가입할 때 추천인 아이디에 나의 아이디를 입력</span
              >
            </li>
            <li>
              <span class="step">Step 2</span
              ><span class="step__description"
                >친구가 회원 가입 완료하면 혜택 증정!</span
              >
            </li>
          </ul>
        </div>
        <div class="how-invitation__footer">
          <ul class="list-style-none">
            <li>
              <div
                class="d-flex align-items-center invitation__div"
                @click="sendKakao"
              >
                <i class="dp-icon icon-kakao sm01 icon-left"></i>
                <p class="mb-0 invitation__p">카카오톡으로 초대하기</p>
                <i class="dp-icon icon-more sm icon-right"></i>
              </div>
            </li>
            <li>
              <div class="d-flex align-items-center invitation__div">
                <i class="dp-icon icon-message sm01 icon-left"></i>
                <!-- <a
                  class="mb-0 invitation__p"
                  href="sms:?body=https://dev-m.dplot.co.kr/invitation-event"
                  >문자메시지로 초대하기</a
                > -->
                <a class="mb-0 invitation__p" @click="sendSMS()">문자메시지로 초대하기</a>
                <i class="dp-icon icon-more sm icon-right"></i>
              </div>
            </li>
            <li>
              <div
                class="d-flex align-items-center invitation__div"
                @click="getUrlBtn()"
              >
                <i class="dp-icon icon-link sm01 icon-left"></i>
                <p class="mb-0 invitation__p">초대장 링크 복사하기</p>
                <input
                  type="text"
                  ref="urlinput"
                  v-model="url"
                  readonly
                  style="position: absolute; top: -9999em"
                />
                <i class="dp-icon icon-more sm icon-right"></i>
              </div>
            </li>
            <li>
              <div
                class="d-flex align-items-center invitation__div"
                @click="getUseridBtn()"
              >
                <i class="dp-icon icon-my sm01 icon-left"></i>
                <p class="mb-0 invitation__p">내 아이디 복사하기</p>
                <input
                  type="text"
                  ref="idinput"
                  readonly
                  v-model="userid"
                  style="position: absolute; top: -9999em"
                />
                <i class="dp-icon icon-more sm icon-right"></i>
              </div>
            </li>
          </ul>
        </div>
      </section>
    </div>
  </b-modal>
</template>

<script>
export default {
  data() {
    return {
      constants: this.$store.getters["MEMBER"],
      userid: "",
      url: window.sessionStorage.getItem("domain") + "/invitation-event",
      rewardInfo: {
        reclimitcnt: 0,
        recomtype: null,
        recjoinpoint: 0,
        revpoint: 0,
        recjoincpname: "",
        revcpname: "",
      },
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
        link: {
          mobileWebUrl: this.url,
          webUrl: this.url,
        },
      });
    },
    /*******************************
     * sms보내기
     ******************************/
    sendSMS() {
      let agent = navigator.userAgent.toLowerCase();
      if (agent.indexOf("android") > -1) {
        location.href =
          "sms:" + "" + "?" + "body=" + this.url;
      } else if (agent.indexOf("ios") > -1 || agent.indexOf("iphone") > -1) {
        location.href =
          "sms:" + "" + "&" + "body=" + this.url;
      } else {
       "sms:" + "" + "?" + "body=" + this.url;
      }
    },
  },
  mounted() {
    this.getRewardInfo();
    if (!Kakao.isInitialized()) {
      Kakao.init(process.env.VUE_APP_KAKAO_SCRIPT_KEY);
    }
  },
};
</script>

<style>
</style>