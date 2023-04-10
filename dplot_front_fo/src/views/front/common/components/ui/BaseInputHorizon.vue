<template>
  <div class="base-input-horizon" :class="{ 'is-success': valid }">
    <label v-if="isLabel" class="input-label" :class="{ require: isRequire }">{{
      label
    }}</label>
    <div class="position-relative">
      <input
        :type="type"
        class="form-control"
        :placeholder="placeholder"
        v-model="newValue"
        :disabled="isDisabled"
        :readonly="isReadonly"
        :maxlength="maxLength > 0 ? maxLength : -1"
        @keypress="keypress"
      />
      <i
        v-if="newValue !== '' && !isReadonly"
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
    isRequire: {
      type: Boolean,
      default: true,
    },
    maxLength : {
      type : Number,
      default : 0
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
    isEnter() {
      this.$emit("isEnter");
    },
    keypress(e) {
      if (window.event.keyCode == 13) {
        this.isEnter(e);
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
