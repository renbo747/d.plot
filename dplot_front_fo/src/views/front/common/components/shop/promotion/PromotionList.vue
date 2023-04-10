<template>
  <div class="dp-event__items">
    <div class="promotion__description">
      <!-- 프로모션 이미지 -->
      <div class="promotion__img-wrap">
        <router-link :to="to">
          <figure class="mb-0 banner-img-box">
            <template
              v-if="$util.isNull(promotionInfo.fullpath)"
            >
              <img
                src="@/assets/common/img/img-no-image-default.svg"
                alt="배너이미지"
                class="event__banner-img"
              />
            </template>
            <template v-else>
              <img
                :src="promotionInfo.fullpath"
                alt="배너이미지"
                class="event__banner-img"
              />
            </template>
          </figure>
        </router-link>
        <!-- 종료된 이벤트의 경우 (배경 있음) -->
        <div v-if="promotionInfo.eventday == '종료'" class="promotion-status">
          <div class="promotion-status__wrap">
            <span class="promotion-status-text dp-title02">{{
              promotionStatus.statusText
            }}</span>
            <b-button v-if="!$util.isNull(promotionInfo.pubtime)"
              class="dp-btn promotion-status-btn not-hover"
              variant="outline-gray-800"
              squared
              @click="$emit('modal')"
            >
              <span>{{ promotionStatus.statusBtn }}</span>
            </b-button>
          </div>
        </div>
      </div>

      <!-- 프로모션 설명 부분  -->
      <router-link :to="to">
        <div class="container">
          <div class="promotion__description-title">
            <span class="promotion__title">[ {{promotionInfo.eventname}} ]</span>
            {{ promotionInfo.subject }}
          </div>
          <div
            class="banner__description__bottom d-flex justify-content-between"
          >
            <div class="promotion__date">
              {{ promotionInfo.startdate }} ~ {{ promotionInfo.enddate }}
            </div>
            <div v-if="promotionInfo.usedday === 'true'" class="promotion__d-day atten-new">
              {{ promotionInfo.dday }}
            </div>
          </div>
        </div>
      </router-link>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    to: {
      type: Object,
      default: () => ({
        name: "promotion-detail",
        params: { pid: 1 },
      }),
    },
    promotionInfo: {
      type: Object,
      default: () => ({
        id: 1,
        imgUrl: require("@/assets/mobile/img/shop/img-shop-banner01.jpg"),
        subject: "[프로모션]",
        evdesc:
          "프로모션 제목이 두줄일 경우 프로모션 제목이 두줄일 경우 프로모션 제목이 두줄일 경우",
        date: "2021.10.05 ~ 2021.10.15",
        dDay: "D-7",
        isDday: false,
      }),
    },
    promotionStatus: {
      type: Object,
      default: () => ({
        statusText: "FINISH",
        statusBtn: "당첨자 발표",
      }),
    },
    promotionStatusModal: {
      type: Object,
      default: () => ({
        id: 1,
        subject: "[프로모션]",
        content:
          "프로모션 제목이 두줄일 경우 프로모션 제목이 두줄일 경우 프로모션 제목이 두줄일 경우",
        date: "2021.10.05 ~ 2021.10.15",
      }),
    },
  },
};
</script>
