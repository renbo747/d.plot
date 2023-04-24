<template>
  <DatePicker v-model="time"
              value-type="format"
              :type="formatType"
              style="width: 160px;"
              @change="changeDate"
              @input-error="inputError"
              @input="inputText"
              ref="picker"
              :disabled="disable"
  />
</template>

<script>
import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/index.css'
import 'vue2-datepicker/locale/ko.js'

export default {
  name: "CommonDatePicker",
  props: {
    value : String,
    formatType : {
      type : String,
      default : 'date'
    },
    disable: {            // disable 처리 유무
      default: false,
      type: Boolean
    }
  },
  components: {
    DatePicker
  },
  methods: {
    changeDate(value) { // 날짜 변동시 이벤트 버스 실행
      this.$emit("change", value);
    },
    inputError(value) { // 입력 에러시 출력될 함수
      this.$util.info('Date Picker Input Error : ' + value);
    },
    inputText(value) { // 텍스트 입력시 출력될 함수
      this.$util.info("Date Picker Input Text : " + value);
    },
  },
  data() {
    return {
      time: this.value,
    }
  },
  computed: {
    getValue() {
      return this.value;
    }
  },
  watch: {
    value(newValue, oldValue) {
      // 날짜가 빈값이 되면 이전 값으로 되돌림
      if (this.$util.isNull(newValue)) {
        this.time = oldValue;
      } else {
        this.time = this.getValue;
      }
    }
  }
}
</script>
