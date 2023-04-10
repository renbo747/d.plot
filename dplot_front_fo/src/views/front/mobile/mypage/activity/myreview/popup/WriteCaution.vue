<template>
  <b-modal
    id="writeCautionModal"
    modal-class="dp-modal pop-over write-caution-modal"
    hide-footer
    centered
  >
    <!-- 작성유의사항 모달 헤더 -->
    <template #modal-header="{ cancel }">
      <h5 class="modal-title">작성유의사항</h5>
      <i class="modal-close" @click="cancel()">
        <img
          src="@assets.mobile/img/icon/icon-close-black-20px.svg"
          alt="닫기"
        />
      </i>
    </template>

    <!-- 작성유의사항 모달 바디 -->
    <div class="caution-modal__body">
      <p class="caution-modal__text dp-mb-30">
        후기 내용이 아래와 같은 경우에는 검토 후 비공개 조치 될 수 있으며,
        적립혜택이 회수 될 수 있습니다.
      </p>
      <ul class="ul-dot">
        <li>욕설. 비난 등 업체나 타인에게 불쾌한 내용을 작성한 경우</li>
        <li>
          해당 상품과 무관한 내용이나 동일 문자 반복 등 부적합한 내용을 작성한
          경우
        </li>
        <li>상품의 기능이나 효과 등에 오해의 소지가 있는 내용을 작성한 경우</li>
        <li>저작권, 초상권 등 타인의 권리를 침해하는 이미지를 사용한 경우</li>
        <li>구매한 상품이 아니거나 캡쳐 사진을 첨부할 경우</li>
      </ul>
    </div>
  </b-modal>
</template>

<script>
export default {
    props: {
        id: { type: String },
    }
};
</script>

<style>
</style>