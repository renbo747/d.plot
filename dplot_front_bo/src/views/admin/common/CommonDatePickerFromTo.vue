<!-- By. Jaeho
     [ Option ]
     - useFrom, useTo // from, to 사용할 유형을 줄 수 있음 (기본 true, 사용 안함 false)
     - fromDisable, toDisable // from, to disable 여부 (기본 false, 사용 true)

     [ Data ]
     - fromYYYYMMDD, HH, MM // 시작 날짜, 시, 분
     - toYYYYMMDD, HH, MM // 끝 날짜, 시, 분
-->
<template>
  <span>
    <DatePicker v-if="useFrom" :disabled="fromDisable" v-model="timeInfo.fromYYYYMMDD" value-type="format"
                style="width: 160px"/>
    <DatePicker v-if="useFrom" :disabled="fromDisable" v-model="timeInfo.fromHH" class="com-time ml3" type="time" format="HH시"
                value-type="format"
                style="width: 70px"/>
    <DatePicker v-if="useFrom" :disabled="fromDisable" v-model="timeInfo.fromMM" class="com-time ml3" type="time" format="mm분"
                value-type="format"
                style="width: 70px"/>
    <span v-if="useFrom && useTo" class="ml3">~</span>
    <DatePicker v-if="useTo" :disabled="toDisable" v-model="timeInfo.toYYYYMMDD" class="ml3" value-type="format" style="width: 160px"/>
    <DatePicker v-if="useTo" :disabled="toDisable" v-model="timeInfo.toHH" class="com-time ml3" type="time" format="HH시" value-type="format"
                style="width: 70px"/>
    <DatePicker v-if="useTo" :disabled="toDisable" v-model="timeInfo.toMM" class="com-time ml3" type="time" format="mm분" value-type="format"
                style="width: 70px"/>
    {{ text }}
  </span>
</template>

<script>
import DatePicker from "vue2-datepicker";
import util from "@js/util";

export default {
  name: "CommonDatePickerFromTo",
  components: {
    DatePicker
  },
  props: {
    useFrom: {                // ~부터 사용할지 유무
      default: true,
      type: Boolean
    },
    useTo: {                  // ~까지 사용할지 유무
      default: true,
      type: Boolean
    },
    fromDisable: {            // ~부터 disable 처리 유무
      default: false,
      type: Boolean
    },
    toDisable: {              // ~까지 disable 처리 유무
      default: false,
      type: Boolean
    },
    index : {
      default: null,
      type: Number
    },
    fromYYYYMMDD: String,   // 시작 년.월.일
    fromHH: String,         // 시작 시간
    fromMM: String,         // 시작 분
    toYYYYMMDD: String,     // 끝 년.월.일
    toHH: String,           // 끝 시간
    toMM: String,           // 끝 분
    text: String,           // 맨 뒤 텍스트
  },
  data() {
    return {
      timeInfo: {
        fromYYYYMMDD: '',
        fromHH: '',
        fromMM: '',
        toYYYYMMDD: '',
        toHH: '',
        toMM: '',
      },
      // oldTimeInfo: {
      //   fromYYYYMMDD: '',
      //   fromHH: '',
      //   fromMM: '',
      //   toYYYYMMDD: '',
      //   toHH: '',
      //   toMM: '',
      // }
    }
  },
  methods: {
    change() {
      let result = {};

      // From 사용
      if (this.useFrom) {
        let fromYYYYMMDD = this.timeInfo.fromYYYYMMDD.replace(/-/g, '');
        let fromHH = this.timeInfo.fromHH.substring(0, 2);
        let fromMM = this.timeInfo.fromMM.substring(0, 2);
        let fromDate12 = fromYYYYMMDD.concat(fromHH, fromMM);

        result.fromYYYYMMDD = this.timeInfo.fromYYYYMMDD;
        result.fromHH = fromHH;
        result.fromMM = fromMM;
        result.fromDate12 = fromDate12;
      }

      // To 사용
      if (this.useTo) {
        let toYYYYMMDD = this.timeInfo.toYYYYMMDD.replace(/-/g, '');
        let toHH = this.timeInfo.toHH.substring(0, 2);
        let toMM = this.timeInfo.toMM.substring(0, 2);
        let toDate12 = toYYYYMMDD.concat(toHH, toMM);

        result.toYYYYMMDD = this.timeInfo.toYYYYMMDD;
        result.toHH = toHH;
        result.toMM = toMM;
        result.toDate12 = toDate12;
      }

      if(this.index === null) {
        this.$emit("getDate", result);
      } else {
        this.$emit("getDate", result, this.index);
      }
    }
  },
  mounted() {
    if (this.useFrom) {
      if(!this.$util.isNull(this.fromYYYYMMDD)) {
        this.timeInfo.fromYYYYMMDD = this.fromYYYYMMDD;
      }
      if(!this.$util.isNull(this.fromHH)) {
        this.timeInfo.fromHH = this.fromHH + "시";
      }
      if(!this.$util.isNull(this.fromHH)) {
        this.timeInfo.fromMM = this.fromMM + "분";
      }
    }

    if(this.useTo) {
      if(!this.$util.isNull(this.toYYYYMMDD)) {
        this.timeInfo.toYYYYMMDD = this.toYYYYMMDD;
      }
      if(!this.$util.isNull(this.toHH)) {
        this.timeInfo.toHH = this.toHH + "시";
      }
      if(!this.$util.isNull(this.toMM)) {
        this.timeInfo.toMM = this.toMM + "분";
      }
    }
  },
  watch: {
    fromYYYYMMDD(value) {
      this.timeInfo.fromYYYYMMDD = value;
    },
    fromHH(value) {
      this.timeInfo.fromHH = value + "시";
    },
    fromMM(value) {
      this.timeInfo.fromMM = value + "분";
    },
    toYYYYMMDD(value) {
      this.timeInfo.toYYYYMMDD = value;
    },
    toHH(value) {
      this.timeInfo.toHH = value + "시";
    },
    toMM(value) {
      this.timeInfo.toMM = value + "분";
    },
    'timeInfo.fromYYYYMMDD'(value, oldValue) {
      if (util.isNull(value)) {
        this.timeInfo.fromYYYYMMDD = oldValue;
      } else {
        this.timeInfo.fromYYYYMMDD = value;
      }

      this.change();
    },
    'timeInfo.fromHH'(value, oldValue) {
      if (util.isNull(value)) {
        this.timeInfo.fromHH = oldValue;
      } else {
        this.timeInfo.fromHH = value;
      }

      this.change();
    },
    'timeInfo.fromMM'(value, oldValue) {
      if (util.isNull(value)) {
        this.timeInfo.fromMM = oldValue;
      } else {
        this.timeInfo.fromMM = value;
      }

      this.change();
    },
    'timeInfo.toYYYYMMDD'(value, oldValue) {
      if (util.isNull(value)) {
        this.timeInfo.toYYYYMMDD = oldValue;
      } else {
        this.timeInfo.toYYYYMMDD = value;
      }

      this.change();
    },
    'timeInfo.toHH'(value, oldValue) {
      if (util.isNull(value)) {
        this.timeInfo.toHH = oldValue;
      } else {
        this.timeInfo.toHH = value;
      }

      this.change();
    },
    'timeInfo.toMM'(value, oldValue) {
      if (util.isNull(value)) {
        this.timeInfo.toMM = oldValue;
      } else {
        this.timeInfo.toMM = value;
      }

      this.change();
    },

  }
}
</script>
