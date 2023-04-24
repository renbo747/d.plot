<template>
  <b-modal
    id="reviewRewardModal"
    modal-class="dp-modal pop-over reward-modal"
    hide-footer
    centered
  >
    <!-- 리뷰적립금 모달 헤더 -->
    <template #modal-header="{ cancel }">
      <h5 class="modal-title">리뷰 적립금</h5>
      <i class="modal-close" @click="cancel()">
        <img
          src="@assets.mobile/img/icon/icon-close-black-20px.svg"
          alt="닫기"
        />
      </i>
    </template>

    <!-- 리뷰적립금 모달 바디 -->
    <div class="reward-modal__body">
      <p class="reward-modal__text dp-mb-10">
        동영상/포토 리뷰 작성 시 최대 {{$util.maskComma(maxReward)}}원 적립!
      </p>
      <table class="dp-table dp-mb-20">
        <tbody>
          <tr>
            <th>동영상 리뷰</th>
            <td>{{$util.maskComma(reviewReward.movreviewamt)}}원</td>
          </tr>
          <tr>
            <th>포토리뷰</th>
            <td>{{$util.maskComma(reviewReward.photoreviewamt)}}원</td>
          </tr>
          <tr>
            <th>텍스트리뷰</th>
            <td>{{$util.maskComma(reviewReward.textreviewamt)}}원</td>
          </tr>
        </tbody>
      </table>
      <ul class="ul-dot">
        <li>구매한 상품에 한해서만 리뷰 작성이 가능합니다.</li>
        <li>동일 상품에 대해 중복하여 작성할 수 없습니다.</li>
        <li>첫 리뷰를 작성 시 {{$util.maskComma(reviewReward.frstreviewamt)}}원을 추가로 드립니다.</li>
      </ul>
    </div>
  </b-modal>
</template>

<script>
export default {
  data() {
    return {
      reviewReward: {},
    };
  },
  methods: {
    getReviewReward() {
      this.$http.post("/review/reward", {}).then((result) => {
        if (result.statusCode == 200) {
            this.reviewReward = result.data.reviewreward;
            this.$util.debug("sss"+JSON.stringify(this.reviewReward));
        }
      });
    },
  },
  created() {
      this.getReviewReward();
  },
  computed: {
      maxReward() {
          let movie = this.$util.isNull(this.reviewReward.movreviewamt) ? 0 : this.reviewReward.movreviewamt;
          let photo = this.$util.isNull(this.reviewReward.photoreviewamt) ? 0 : this.reviewReward.photoreviewamt;
        
          if (movie >= photo) {
              return movie;
          }
          return photo;
      }
  }
};
</script>