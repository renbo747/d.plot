<template>
  <div
    class="base-input base-textarea"
    :class="{ 'is-success': valid, 'is-error': countError }"
  >
    <div class="position-relative">
      <template v-if="isCount">
        <textarea
          class="form-control"
          v-model="newValue"
          :placeholder="placeholder"
          :disabled="isDisabled"
          @keyup="liveCount"
          :maxlength="maxCount"
        ></textarea>
      </template>
      <template v-else>
        <textarea
          class="form-control"
          v-model="newValue"
          :placeholder="placeholder"
          :disabled="isDisabled"
          :maxlength="maxCount"
        ></textarea>
      </template>
      <!-- 텍스트 카운트 추가 -->
      <div v-if="isCount" class="textarea__count">
        <span :class="{ 'text-danger': countError }">{{ newValue.length }}</span>
        / {{ maxCount }}
      </div>
      <!-- // 텍스트 카운트 추가 -->
    </div>
    <p class="error-msg"><slot></slot></p>
  </div>
</template>

<script>
export default {
  props: {
    maxCount: {
      type: Number,
      default: 500,
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
    isCount: {
      type: Boolean,
      default: true,
    },
    valid: {
      // 성공일 때 조건값 가져오기
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      currentCount: 0,
      countError: false,
    };
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
    liveCount() {
      this.currentCount = this.value.length;

      if (this.currentCount > this.maxCount) {
        this.countError = true;
      } else {
        this.countError = false;
      }
    },
  }
};
</script>
