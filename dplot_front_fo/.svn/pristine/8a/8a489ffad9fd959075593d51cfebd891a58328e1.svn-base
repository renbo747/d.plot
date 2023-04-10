<template>
  <div class="base-radio">
    <input
      class="magic-radio"
      type="radio"
      :id="id"
      :name="name"
      :value="val"
      :disabled="disabled"
      :checked="isChecked"
      v-model="newValue"
      @change="handleRadio"
    />
    <label :for="id">{{ label }}</label>
  </div>
</template>

<script>
export default {
  props: {
    val: {
      type : String,
      default : '',
    },
    label: {
      type: String,
      default: "Normal",
    },
    id: {
      type: String,
      default: "id",
    },
    name: {
      type: String,
      default: "name",
    },
    value: {
      type : String,
      default : ""
    },
    disabled: {
      type: Boolean,
      default: false,
    },
    isChecked: {
      type: Boolean,
      default: false,
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
  data() {
    return {
      model : ''
    }
  },
  mounted() {
    this.model = this.value;
  },
  methods: {
    handleRadio() {
      this.$emit("change");
    },
  },
};
</script>
