<template>
  <div class="container">
    <div class="cs-inquiry cs-store-inquiry">
      <section class="cs-inquiry-header">
        <header class="cs-inquiry-section__header">
          <h2 class="cs-section__title text-black font-weight-700 mb-0">
            입점/제휴문의
          </h2>
        </header>
      </section>
      <div>
        <header class="cs-store-inquiry-header">
          <h1 class="dp-p text-black font-weight-400 mb-0">
            D.PLOT에 입점/제휴문의를 위해 아래 정보를 입력해주세요. <br />
            입력하신 정보는 내부 검토 후 별도의 안내를 드립니다.
          </h1>
        </header>
        <!--  form  -->
        <div class="store-inquiry-form">
          <form>
            <ul class="list-style-none">
              <li v-for="(list, index) in inputListData" :key="index">
                <div>
                  <div class="dp-panel pt-0">
                    <p class="dp-p store-inquiry-form-p">{{ list.label }}</p>
                    <base-input
                      :placeholder="list.placeholder"
                      v-model="list.value"
                      :ref="list.ref"
                    />
                  </div>
                </div>
              </li>
            </ul>
            <div>
              <div>
                <p class="dp-p">기타 문의사항</p>
                <base-textarea
                  v-model="textareaData"
                  placeholder="내용을 입력해주세요. (최대 500자)"
                  max-count="500"
                ></base-textarea>
              </div>
            </div>
          </form>
        </div>
        <!-- 사업자정보제공 안내  -->
        <div class="store-inquiry-info dp-panel pb-0">
          <div class="cs-store-inquiry-header">
            <h2 class="dp-title02 text-black font-weight-700 mb-0">
              사업자정보제공 안내
            </h2>
          </div>
          <div>
            <ul class="store-inquiry-info__list list-style-none">
              <li>
                <div class="store-inquiry-info__list__item">
                  <div class="store-inquiry-info__list__title">
                    <h3
                      class="mb-0 text-black font-weight-500 info__list__title"
                    >
                      수집 및 이용목적
                    </h3>
                  </div>
                  <div class="store-inquiry-info__list__text">
                    <p class="info__list__text__p mb-0">
                      입점/제휴 상담신청을 위한 담당자 연락처 정보 확보
                    </p>
                  </div>
                </div>
              </li>
              <li>
                <div class="store-inquiry-info__list__item">
                  <div class="store-inquiry-info__list__title">
                    <h3
                      class="mb-0 text-black font-weight-500 info__list__title"
                    >
                      제공받는자
                    </h3>
                  </div>
                  <div class="store-inquiry-info__list__text">
                    <p class="info__list__text__p mb-0">(주)다다엠앤씨</p>
                  </div>
                </div>
              </li>
              <li>
                <div class="store-inquiry-info__list__item">
                  <div class="store-inquiry-info__list__title">
                    <h3
                      class="mb-0 text-black font-weight-500 info__list__title"
                    >
                      수집 및 이용목적
                    </h3>
                  </div>
                  <div class="store-inquiry-info__list__text">
                    <p class="info__list__text__p mb-0">
                      사업자등록번호, 협력업체 담당자의 인적사항 (이름,
                      전화번호, 휴대폰번호, 이메일주소) 등
                    </p>
                  </div>
                </div>
              </li>
              <li>
                <div class="store-inquiry-info__list__item">
                  <div class="store-inquiry-info__list__title">
                    <h3
                      class="mb-0 text-black font-weight-500 info__list__title"
                    >
                      보유 및 이용기간
                    </h3>
                  </div>
                  <div class="store-inquiry-info__list__text">
                    <p
                      class="mb-0 info__list__text__p text-gray-700 font-weight-400"
                    >
                      원칙적으로 개인정보의 수집목적 또는 제공받은 목적이
                      달성되면 지체없이 파기합니다. 다만, 관계법률에 의해 보존할
                      필요가 있는 경우에는 일정기간 보존합니다. 이 경우, 당사는
                      보관하는 정보를 그 보관 목적으로만 이용합니다.
                    </p>
                  </div>
                </div>
              </li>
            </ul>
            <div class="store-inquiry-checkbox">
              <div class="form-group">
                <base-checkbox
                  label="위의 안내 사항을 모두 확인하였으며, 이에 동의합니다."
                  v-model="sampleCheckbox.id"
                  :id="sampleCheckbox.id"
                  name="sampleCheckbox"
                  :is-checked="sampleCheckbox.checked"
                  @change="sampleCheckbox.checked = !sampleCheckbox.checked"
                />
              </div>
              <div class="store-inquiry-btn__wrap">
                <b-button
                  class="store-inquiry-btn dp-btn text-white"
                  variant="gray-800"
                  squared
                >
                  <span>등록하기</span>
                </b-button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      textareaData: "",
      inputListData: [
        {
          id: "input01",
          label: "업체명",
          placeholder: "업체명을 입력해주세요",
          value: "",
        },
        {
          id: "input02",
          label: "사업자등록번호",
          placeholder: "사업자등록번호를 입력해주세요",
          value: "",
        },
        {
          id: "input03",
          label: "홈페이지 URL",
          placeholder: "URL을 입력해주세요",
          value: "",
        },
        {
          id: "input04",
          label: "제안상품명(주요상품)",
          placeholder: "내용을 입력해주세요",
          value: "",
        },
        {
          id: "input05",
          label: "브랜드 자사몰 / 판매처 URL",
          placeholder: "URL을 입력해주세요",
          value: "",
        },
        {
          id: "input06",
          label: "담당자명/직급",
          placeholder: "담당자정보를 입력해주세요",
          value: "",
        },
        {
          id: "input07",
          label: "담당자 메일 주소",
          placeholder: "이메일주소를 입력해주세요",
          value: "",
        },
        {
          id: "input08",
          label: "담당자 연락처",
          placeholder: "전화번호를 입력해주세요",
          value: "",
        },
      ],
      sampleCheckbox: {
        id: "sampleChk01",
        checked: false,
      },
    };
  },
};
</script>
