<template>
  <main class="dp-news-letter">
    <div class="container">
      <div class="dp-news-letter-wrap">
        <div class="dp-news-letter__img">
          <figure class="dp-news-letter__figure mb-0">
            <img
              src="@/assets/common/icon/icon-logo-black-h28px.svg"
              alt="임시 로고입니다"
            />
          </figure>
        </div>
        <div class="dp-news-letter__text__wrap">
          <p class="dp-news-letter__text text-gray-800 mb-0">
            D.PLOT의 뉴스레터 구독을 취소하시면 더 이상<br />
            뉴스레터가 발송되지 않습니다.<br />
            구독을 취소하시겠습니까?
          </p>
        </div>
        <div class="btn-group">
          <b-button
            class="dp-btn mr-10 not-hover"
            variant="outline-gray-800"
            squared
            @click="subcribeStay()"
          >
            <span>메인으로</span>
          </b-button>
          <b-button class="dp-btn text-white" variant="gray-800" squared @click="subcribeCancel()">
            <span>구독취소</span>
          </b-button>
        </div>
      </div>
      <!-- Todo: (pbl) modal 위치 찾을 경우 삭제 -->
      <!-- modal -->
      <!-- 개인정보처리방침 Modal -->
      <div class="mt-4">
        <b-button
          class="dp-btn text-white"
          variant="gray-800"
          v-b-modal.personalModal
          >개인정보처리방침</b-button
        >
      </div>
      <!-- 광고 푸시 동의 안내 Modal -->
      <!-- Confirm Modal -->
      <div class="mt-4">
        <b-button
          class="dp-btn text-white"
          variant="gray-800"
          v-b-modal.adPushModal
          >광고 push 동의 안내</b-button
        >

        <!-- ad-push Modal -->
        <b-modal
          id="adPushModal"
          modal-class="dp-modal alarm ad-push-modal"
          centered
        >
          <template #modal-header>
            <h5 class="modal-title">
              쿠폰, 할인, 추천상품, 이벤트 등<br />
              유용한 쇼핑정보를 제공해드립니다.
            </h5>
          </template>

          <div class="ad-push__body">
            <p class="modal-text text-gray-700 mb-0">
              마케팅정보 알림 수신여부는<br />
              <span class="text-black font-weight-700">전체메뉴 > 설정</span
              >에서 변경 가능합니다.
            </p>
          </div>

          <template #modal-footer="{ ok, cancel }">
            <div class="ap-push-btn-wrap">
              <b-button
                class="ap-push-btn dp-btn"
                variant="gray-800 not-hover"
                @click="ok()"
                >알림을 받을게요</b-button
              >
              <p class="cancel__p mb-0" @click="cancel()">다음에 받을게요</p>
            </div>
          </template>
        </b-modal>
        <!-- // Confirm Modal -->
      </div>
    </div>
  </main>
</template>

<script>
export default {
  data() {
    return {
      selectOptionData: "",
      sampleSelectOptions: [
        {
          label: "샘플 옵션 01",
          value: "샘플 데이터 01",
        },
        {
          label: "샘플 옵션 02",
          value: "샘플 데이터 02",
        },
      ],
    };
  },
  methods: {
     /***********************
     * 구독유지 처리
     ***********************/
    subcribeStay() {
      let message = '구독을 유지해주셔서 감사합니다!  더 좋은 소식으로 만나뵙겠습니다.';
      this.$eventBus.$emit("alert", "알림", message, () => {
        this.$router.push({ name: "shop" });
      });
    },
     /***********************
     * 구독유지 취소처리
     ***********************/
    subcribeCancel() {
      this.$http.post("/etc/subscribeCancel", {email:this.$route.query.email}).then((result) => {
        if (result.statusCode == 200) {
          let message = '구독이 취소되었습니다. 그동안 구독해주셔서 감사합니다.';
          this.$eventBus.$emit("alert", "알림", message, () => {
            this.$router.push({ name: "shop" });
          });
        }
      });
    },
  }
};
</script>
