<template>
  <div class="base-counter">
    <button
      type="button"
      class="base-counter__btn decrement"
      @click="decrement"
    ></button>
    <input
      type="text"
      class="base-counter__input atten-new"
      v-model="value"
      @blur="change"
      readonly
    />
    <button
      type="button"
      class="base-counter__btn increment"
      @click="increment"
    ></button>
  </div>
</template>

<script>
export default {
  props: {
    item: {
      type : Object,
      default: () => ({
        ordcnt : 0,
      }),
    }
  },
  data() {
    return {
      isOk: true,
      value : 0,
      data : this.item,
    };
  },
  mounted() {
    this.value = this.data.ordcnt;
    if(this.value > this.data.stockcnt) {
      this.value = this.data.stockcnt;
      this.change();
    } else if(this.value > this.data.maxordcnt) {
      this.value = this.data.maxordcnt;
      this.change();
    }
  },
  methods: {
    decrement() {
      this.value--;
      this.change();
    },
    increment() {
      this.value++;
      this.change();
    },
    change() {
      this.isOk = true;
      if(this.value < 1) {
          this.isOk = false;
      } else  if(this.data.stockcnt < this.value) {
          this.$eventBus.$emit('alert', '알림', "구매가능수량을 초과하였습니다.");
          this.isOk = false;
      } else if(this.data.minordcnt != 0 && this.data.minordcnt > this.value) {
          this.$eventBus.$emit('alert', '알림', "최소 " + this.data.minordcnt + "개부터 주문 가능합니다.");
          this.isOk = false;
      } else if(this.data.maxordcnt != 0 && this.data.maxordcnt < this.value) {
          this.$eventBus.$emit('alert', '알림', "최대 " + this.data.maxordcnt + "개까지 주문 가능합니다.");
          this.isOk = false;
      } else if(this.data.daymaxordcnt != 0 && this.data.daymaxordcnt < (this.value + this.data.dayordcnt)) {
          this.$eventBus.$emit('alert', '알림', "하루 " + this.data.daymaxordcnt + "개까지 주문가능합니다.");
          this.isOk = false;
      } else if(this.data.permaxordcnt != 0 && this.data.permaxordcnt < (this.value + this.data.allordcnt)) {
          this.$eventBus.$emit('alert', '알림', this.data.permaxordcnt + "개까지 주문가능합니다.");
          this.isOk = false;
      } else if(this.data.origincnt < this.value) {
          this.isOk = false;
      }
      if(this.isOk) {
        this.data.ordcnt = this.value;
        this.$emit("input", this.data);
      }else {
        this.value = this.data.ordcnt;
      }
    }
  }
};
</script>
