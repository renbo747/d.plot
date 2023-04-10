<template>
   <!-- 개인정보 Modal -->
    <b-modal
      id="newsLetterPrivacyModal"
      modal-class="dp-modal pop-over news-letter-modal"
      hide-footer
      centered
    >
      <template #modal-header="{ cancel }">
        <h5 class="modal-title">개인정보 수집 및 이용 동의</h5>
        <i class="modal-close" @click="cancel()">
          <img
            src="@/assets/common/icon/icon-close-modal-30px.svg"
            alt="닫기"
          />
        </i>
      </template>

      <div class="news-letter-body">
        <ul class="ul-dot">
          <li class="news-letter__list">
            <div class="news-letter__item">
              <p class="news-letter__item__text text-gray-700 mb-0">
                뉴스레터 이메일 발송을 위한 최소한의 개인정보를 수집하고
                이용합니다.
              </p>
            </div>
          </li>
          <li class="news-letter__list">
            <div class="news-letter__item">
              <p class="news-letter__item__text text-gray-700 mb-0">
                수집된 정보는 이메일 발송 외에 다른 목적으로 이용되지 않으며,
                서비스가 종료되거나 구독을 해지할 경우 즉시 파기됩니다.
              </p>
            </div>
          </li>
          <li class="news-letter__list">
            <div class="news-letter__item">
              <p class="news-letter__item__text text-gray-700 mb-0">
                받으신 이메일 본문 하단의 구독해지 링크를 통해 구독 해지가
                가능합니다.
              </p>
            </div>
          </li>
        </ul>
      </div>
    </b-modal>
</template>

<script>
export default {

}
</script>

<style>

</style>