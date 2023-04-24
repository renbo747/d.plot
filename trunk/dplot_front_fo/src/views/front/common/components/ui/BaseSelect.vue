<template>
  <select class="form-select" v-model="newValue" @change="handleChange" :disabled="isDisabled">
    <option v-if="isPlaceholder" value="" selected disabled>{{ placeholder }}</option>
    <template v-for="(list, index) in options">
      <option :key="index" :value="list.value">{{ list.label }}</option>
    </template>
  </select>
</template>

<script>
export default {
  props: {
    // 셀렉트 박스 선택된 값
    value: {
      default: "",
    },
    placeholder: {
      type: String,
      default: "선택",
    },
    isDisabled:{
      type: Boolean,
      default : false,
    },
    options: {
      // 셀렉트박스 옵션 리스트
      type: [Array, Object],
      default: () => [],
    },
    isPlaceholder:{
      type:Boolean,
      default:true
    }
  },
  computed: {
    newValue: {
      get() {
        return this.value;
      },
      set(val) {
        this.$emit("input", val);
      },
    },
  },
  methods : {
    handleChange() {
      this.$emit("change");
    }
  }
};
</script>
