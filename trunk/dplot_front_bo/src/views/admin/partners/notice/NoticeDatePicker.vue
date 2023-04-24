<!-- by. Jaeho -->
<template>
  <dl>
    <dt>{{ title }}</dt>
    <dd>
      <CommonDatePicker :value="timeInfo.startYear" @change="onChangeStartDate" ref="picker_start"/>
      <select class="short" v-model="timeInfo.startHour" style="width: 60px;">
        <option :key="i" v-for="(item, i) in hourList" :value="item">{{ item + " 시" }}</option>
      </select>
      <select class="short" v-model="timeInfo.startMinute" style="width: 60px;">
        <option :key="i" v-for="(item, i) in minuteList" :value="item">{{ item === '입력' ? item : item + ' 분' }}</option>
      </select>
      <span>~</span>
      <CommonDatePicker :value="timeInfo.endYear" @change="onChangeEndDate" ref="picker_end"/>
      <select class="short" v-model="timeInfo.endHour" style="width: 60px;">
        <option :key="i" v-for="(item, i) in hourList" :value="item">{{ item + " 시" }}</option>
      </select>
      <select class="short" v-model="timeInfo.endMinute" style="width: 60px;">
        <option :key="i" v-for="(item, i) in minuteList" :value="item">{{ item === '입력' ? item : item + ' 분' }}</option>
      </select>
    </dd>
  </dl>
</template>

<script>
import CommonDatePicker from "@views.admin/common/CommonDatePicker";
import util from "@js/util";

export default {
  name: "NoticeDatePicker",
  components: {CommonDatePicker},
  props: {
    title: {
      type: String,
      default: "시작/종료일시"
    },
  },
  data() {
    return {
      hourList: ["00", "01", "02", "03", "04", "05", "06", "07", "08", "09"
        , "10", "11", "12", "13", "14", "15", "16", "17", "18", "19"
        , "20", "21", "22", "23"
      ],
      minuteList: ["00", "10", "20", "30", "40", "50"],
      timeInfo: {
        startYear: '',
        endYear: '',
        startHour: "00",
        startMinute: "00",
        endHour: "00",
        endMinute: "00",
      },
      // isShowStartInput: false,
      // isShowEndInput: false,
    }
  },
  mounted() {
    // 날짜 셋팅
    this.$refs.picker_start.time = util.getDate('-');
    this.$refs.picker_end.time = util.getDate('-');
  },
  methods: {

    /**
     * 끝 연도를 string 형태로 반환 12자리
     * @returns {string} (ex.202110290300)
     */
    getEndTimeToString2() {
      if (this.endYear === "") {
        alert("끝 연도를 선택해주세요");
        return "";
      }

      let year = this.timeInfo.endYear.split("-")[0];
      let month = this.timeInfo.endYear.split("-")[1];
      let day = this.timeInfo.endYear.split("-")[2];
      let hour = this.timeInfo.endHour;
      let minute = this.timeInfo.endMinute;

      let date = year + month + day + hour + minute;

      return date;
    },

    /**
     * 시작 연도를 string 형태로 반환 14자리
     * @returns {string} (ex.20211030024000)
     */
    getStartTimeToString() {
      if (this.timeInfo.startYear === "") {
        alert("시작 연도를 선택해주세요");
        return "";
      }

      let year = this.timeInfo.startYear.split("-")[0];
      let month = this.timeInfo.startYear.split("-")[1];
      let day = this.timeInfo.startYear.split("-")[2];
      let hour = this.timeInfo.startHour;
      let minute = this.timeInfo.startMinute;

      let date = year + month + day + hour + minute + "00";

      return date;
    },

    /**
     * 시작 연도를 string 형태로 반환 12자리
     * @returns {string} (ex.202110290300)
     */
    getStartTimeToString2() {
      if (this.timeInfo.startYear === "") {
        alert("시작 연도를 선택해주세요");
        return "";
      }

      let year = this.timeInfo.startYear.split("-")[0];
      let month = this.timeInfo.startYear.split("-")[1];
      let day = this.timeInfo.startYear.split("-")[2];
      let hour = this.timeInfo.startHour;
      let minute = this.timeInfo.startMinute;

      let date = year + month + day + hour + minute;

      return date;
    },

    // 날짜 picker 콜백 함수
    onChangeStartDate(val) {
      this.timeInfo.startYear = val;
    },

    // 날짜 picker 콜백 함수
    onChangeEndDate(val) {
      this.timeInfo.endYear = val;
    },
  },
}
</script>
