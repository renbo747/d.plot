<template>
  <div class="base-input" :class="{ 'is-success': valid }">
    <label v-if="isLabel" class="input-label">{{ label }}</label>
    <div class="position-relative">
      <input
        :type="type"
        class="form-control"
        :placeholder="placeholder"
        :value="value"
        v-model="newValue"
        :disabled="isDisabled"
        :readonly="isReadonly"
        :autocomplete="autocomplete"
        ref="binput"
        @blur="blur"
        @keypress="keypress"
        @keyup="keyup"
        :maxlength="maxLength > 0 ? maxLength : -1"
      />
      <i
        v-if="newValue != 0 && newValue !== '' && type !='number' && !isReadonly && !valid"
        class="dp-icon icon-input-remove"
        @click="clearValue"
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
    maxLength: {
      type: Number,
      default: 500,
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
    autocomplete:{
      type: String,
      default: ""
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
  methods: {
    clearValue() {
      this.$emit("input", "");
    },
    blur() {
      this.$emit("blur");
    },
    enter() {
      this.$emit("enter");
    },
    keyup() {
      this.$emit("keyup");
    },
    keypress(e) {
      if (window.event.keyCode == 13) {
        this.enter(e);
        e.preventDefault();
        return;
      }
      if(this.type == 'number') {
        if(this.maxLength > 0 && e.target.value.length >= this.maxLength) {
          e.preventDefault();
        }
      }
    }
  },
};
</script>
