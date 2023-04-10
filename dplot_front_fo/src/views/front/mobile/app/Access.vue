<template>
  <main class="dp-app">
    <div class="container">
      <header class="dp-app-header">
        <h1 class="dp-app-title mb-0">
          편리한 서비스 제공을 위해<br />
          다음 접근 권한 허용이 필요합니다.
        </h1>
      </header>
      <section class="app-section">
        <header class="app-section-header mb-20">
          <h2 class="app-section-title mb-0">필수 접근 권한</h2>
        </header>
        <div class="app-access">
          <ul class="app-access__wrap list-style-none">
            <li
              class="app-access__list"
              v-for="(list, index) in accessItemData"
              :key="index"
            >
              <div class="access__list__item d-flex">
                <div class="access__item__img__wrap">
                  <div class="access__item__img d-flex">
                    <i
                      class="dp-icon h30"
                      :style="{ 'background-image': `url(${list.iconImage})` }"
                    ></i>
                  </div>
                </div>
                <div class="access__item__text">
                  <p class="access__item__text__p mb-1 font-weight-500">
                    {{ list.title }}
                  </p>
                  <p class="access__item__text__p text-gray-700 mb-0">
                    {{ list.text }}
                  </p>
                </div>
              </div>
            </li>
          </ul>
        </div>
      </section>
      <section class="app-section pt-0">
        <header class="app-section-header mb-20">
          <h2 class="app-section-title mb-0">선택 접근 권한</h2>
        </header>
        <div class="app-access">
          <div class="app-access__list">
            <div class="access__list__item d-flex">
              <div class="access__item__img__wrap">
                <div class="access__item__img d-flex">
                  <i class="dp-icon h30 access-icon"></i>
                </div>
              </div>
              <div class="access__item__text">
                <p class="access__item__text__p mb-1 font-weight-500">
                  생체 인증 정보(지문, FACE ID)
                </p>
                <p class="access__item__text__p text-gray-700 mb-0">
                  간편 로그인 이용
                </p>
              </div>
            </div>
          </div>
        </div>
        <div class="app-access__explanation">
          <ul class="ul-dot">
            <li class="access__explanation__item">
              <div class="access__explanation__text">
                <p class="access__explanation__text__p text-gray-700 mb-0">
                  상기 항목의 경우 고객님께 보다 편리한 서비스를 제공하기 위해
                  사용됩니다.
                </p>
              </div>
            </li>
            <li class="access__explanation__item mt-2">
              <div class="access__explanation__text">
                <p class="access__explanation__text__p text-gray-700 mb-0">
                  동의하지 않으신 서비스의 이용이 제한될 수 있습니다.
                </p>
              </div>
            </li>
          </ul>
        </div>
      </section>
      <div class="app-access-button">
        <div>
          <b-button class="dp-btn text-white" variant="gray-800" squared>
            <span class="app-button-text">확인했습니다</span>
          </b-button>
        </div>
      </div>
    </div>
  </main>
</template>

<script>
export default {
  data: function () {
    return {
      accessItemData: [
        {
          id: "access01",
          title: "기기 및 앱 기록",
          text: "서비스 최적화 및 오류 확인",
          iconImage: require("@/assets/common/icon/icon-device-white-30px.svg"),
        },
        {
          id: "access02",
          title: "사진, 카메라",
          text: "포토/동영상 리뷰 작성, 1:1 문의작성",
          iconImage: require("@/assets/common/icon/icon-camera-white-30px.svg"),
        },
        {
          id: "access03",
          title: "저장공간",
          text: "이미지 직접 저장",
          iconImage: require("@/assets/common/icon/icon-photo-white-30px.svg"),
        },
      ],
    };
  },
};
</script>
