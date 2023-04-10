<template>
  <label class="switch">
    <input type="checkbox" ref="btoggole" :checked="isChecked" @click.prevent="handleToggle" />
    <span class="slider round"></span>
  </label>
</template>

<script>
export default {
  props: {
    isChecked: {
      type: Boolean,
      default: false,
    },
  },
  methods: {
    handleToggle(e) {
      this.$emit("click", e.target.checked);
    },
  },
  watch: {
    isChecked(val) {
    },
  },
};
</script>
