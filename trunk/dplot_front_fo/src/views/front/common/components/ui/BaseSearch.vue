<template>
  <div class="base-input-search" :class="{ 'is-success': valid }">
    <label v-if="isLabel" class="input-label">{{ label }}</label>
    <div class="position-relative">
      <input
        :type="type"
        class="form-control"
        :placeholder="placeholder"
        v-model="newValue"
        :disabled="isDisabled"
        :readonly="isReadonly"
        @keyup.enter="isEnter(newValue)"
      />
      <i
        v-if="newValue !== ''"
        class="dp-icon icon-input-remove"
        @click="clearValue"
      ></i>
      <i
        v-if="isMobile"
        class="search__input__icon dp-icon sm02 icon-search"
        @click="$emit('search')"
      ></i>
      <i
        v-else
        class="search__input__icon dp-icon h28 icon-search"
        @click="$emit('search')"
      ></i>

      <!-- Todo: 성공일 때 조건 삽입 -->
      <i v-if="valid" class="dp-icon icon-input-success"></i>
    </div>
    <p v-if="invalid" class="error-msg"><slot></slot></p>
  </div>
</template>

<script>
export default {
  props: {
    type: {
      type: String,
      default: "text",
    },
    label: {
      type: String,
      default: "라벨명",
    },
    isLabel: {
      type: Boolean,
      default: false,
    },
    value: {
      default: "",
    },
    placeholder: {
      type: String,
      default: "placeholder",
    },
    isDisabled: {
      type: Boolean,
      default: false,
    },
    isReadonly: {
      type: Boolean,
      default: false,
    },
    valid: {
      // 성공일 때 조건값 가져오기
      type: Boolean,
      default: false,
    },
    invalid: {
      // 실패일 때 조건값 가져오기
      type: Boolean,
      default: false,
    },
    //모바일에서 isMobile
    isMobile: {
      type: Boolean,
      default: true,
    },
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
  methods: {
    clearValue() {
      this.$emit("input", "");
    },
    isEnter(newValue) {
      this.$emit("isEnter", newValue);
    },
  },
};
</script>
