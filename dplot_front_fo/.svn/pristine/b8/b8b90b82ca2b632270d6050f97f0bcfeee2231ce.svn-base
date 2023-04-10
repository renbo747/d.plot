<template>
  <div class="base-dropdown" :class="{ disabled: isDisabled }">
    <div class="dropdown__relative">
      <div class="dropdown__input" @click="open">
        <input type="text" :placeholder="placeholder" :value="label" readonly />
        <i
          class="dp-icon md"
          :class="{ 'icon-arrow-down': !isShow, 'icon-arrow-up': isShow }"
        ></i>
      </div>

      <ul v-if="isShow" class="dropdown__ul list-style-none">
        <template v-for="(list, index) in options" >
          <li
            :class="{ selected: list.value === newValue}"
            @click="handleOption(index)"
            :key="index"
          >
          {{ list.label }}
          </li>
        </template>
      </ul>
    </div>
    <div class="dropdown__backdrop" v-if="isShow" @click="isShow = false"></div>
  </div>
</template>

<script>
export default {
  props: {
    placeholder: {
      type: String,
      default: "placeholder",
    },
    isDisabled : {
      type: Boolean,
      default: false,
    },
    options: {
      type: Array,
      default: () => [],
    },
    value : {
      type: String,
      default:''
    }
  },
  data() {
    return {
      data : "",
      label: "",
      isShow: false
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
  mounted() {
  },
  methods: {
    open() {
      if(!this.isDisabled) {
        this.isShow = !this.isShow
      } else {
        this.isShow = false;
      }
    },
    handleOption(index) {
      this.isShow = false;
      this.newValue = this.options[index].value;
      this.label = this.options[index].label;
      this.$emit("change", this.options[index].value);
    },
  },
  watch : {
    value(newValue)  {
      if(this.$util.isBlank(newValue)) {
        this.label = "";
        return;
      }
      for(let i = 0 ; i < this.options.length ; i++) {
        if(this.options[i].value == newValue) {
          this.label = this.options[i].label;
          break;
        }
      }
    },
    options(oldValue, newValue)  {
      this.label = "";
      for(let i = 0 ; i < this.options.length ; i++) {
        if(this.options[i].value == this.value) {
          this.isShow = false;
          this.newValue = this.options[i].value;
          this.label = this.options[i].label;
          //this.handleOption(i);
          break;
        }
      }
    }
  }
};
</script>
