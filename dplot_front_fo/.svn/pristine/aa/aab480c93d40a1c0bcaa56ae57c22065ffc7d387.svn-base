<template>
  <div class="dp-progress">
    <div class="progress-rail">
      <div class="progress-gauge" :style="`width:${percent}%`"></div>
    </div>
    <div v-if="isMark" class="progress-mark">
      <slot name="mark">
        <span>{{ min }}</span>
        <span>{{ max }}</span>
      </slot>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    isMark: {
      type: Boolean,
      default: true,
    },
    min: {
      type: [String, Number],
      default: 0,
    },
    max: {
      type: [String, Number],
      default: 100,
    },
    percent: {
      type: Number,
      default: 20,
    },
  },
};
</script>
