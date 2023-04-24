<template>
  <div class="base-checkbox">
    <input
      class="magic-checkbox"
      type="checkbox"
      :id="id"
      :name="name"
      ref="bck"
      :disabled="disabled"
      :true-value="trueValue"
      :false-value="falseValue"
      :is-checked="isChecked"
      v-model="newValue"
      @change="handleCheckbox"
    />
    <label :for="id">
      <slot>
        {{ label }}
      </slot>
    </label>
  </div>
</template>

<script>
export default {
  props: {
    label: {
      type: String,
      default: "Normal",
    },
    id: {
      type: [String, Number],
      default: "id",
    },
    name: {
      type: String,
      default: "name",
    },
    value:{
      type:[Boolean, String, Array],
      default:true
    },
    trueValue : {
      type:[Boolean, String],
      default:true
    },
    falseValue : {
      type:[Boolean, String],
      default:false
    },
    isChecked : {
      type:[Boolean, String],
      default:false
    },
    disabled: {
      type: Boolean,
      default: false,
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
    handleCheckbox() {
      this.$emit("change");
    },
  },
};
</script>
