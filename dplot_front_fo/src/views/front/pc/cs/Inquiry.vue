<template>
  <div class="dp-inquiry">
    <header>
      <div class="header-title">문의</div>
    </header>
    <div class="">
      <div class="inquiry-top">
        <ul class="inquiry__ul dp-sort__ul list-style-none no-scrollbar">
          <li v-for="(list, index) in inquiryType" :key="index">
            <label :for="list.id" class="dp-sort__label">
              <input
                type="radio"
                :id="list.id"
                name="terms"
                :checked="list.checked"
                :ref="list.id"
                hidden
              />
              <span class="dp-sort__badge dp-p" @click="handleInquiry(index)">{{
                list.label
              }}</span>
            </label>
          </li>
        </ul>
        <b-button
          class="dp-btn dp-btn-icon not-hover question-btn"
          variant="outline-gray-800"
          squared
        >
          <!-- <i class="dp-icon icon-question md"></i> -->
          <span>상품문의 작성</span>
        </b-button>
      </div>
      <div class="container">
        <div v-html="inquiryData"></div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  /*
  props: {
    inquiryType: {
      type: Array,
      default: () => [
        {
          id: "01",
          label: "전체",
          checked: true,
        },
        {
          id: "02",
          label: "미답변",
          checked: false,
        },
        {
          id: "03",
          label: "답변완료",
          checked: false,
        },
        {
          id: "04",
          label: "내문의",
          checked: false,
        },
      ],
    },
  },
   */
  data() {
    return {
      inquiryData: "이용약관 데이터",
      inquiryType: [
        {
          id: "01",
          label: "전체",
          checked: true,
        },
        {
          id: "02",
          label: "미답변",
          checked: false,
        },
        {
          id: "03",
          label: "답변완료",
          checked: false,
        },
        {
          id: "04",
          label: "내문의",
          checked: false,
        },
      ],
    };
  },
  methods: {
    handleInquiry(i) {
      switch (i) {
        case 0:
          this.inquiryData = "전체 데이터";
          break;
        case 1:
          this.inquiryData = "미답변 데이터";
          break;
        case 2:
          this.inquiryData = "답변완료 데이터";
          break;
        case 3:
          this.inquiryData = "내문의 데이터";
          break;
      }
    },
  },
};
</script>
