<template>
  <div class="base-wish">
    <i
      class="heart-icon"
      :class="{ on: hasActive }"
      @click.stop="handleWish"
    ></i>
  </div>
</template>

<script>
export default {
  props: {
    data: {
      type: Object,
      default: () => ({
        isSelected: false,
        isCount: 0,
      }),
    },
  },
  data() {
    return {
      hasActive: this.data.isSelected,
      count: this.data.isCount,
    };
  },
  methods: {
    handleWish() {
      if(!this.$store.state.isLogin) {
            // this.$eventBus.$emit("alert", "알림", "로그인 후 이용해주세요.");
            this.$eventBus.$emit('confirm', '로그인', '로그인 하시겠습니까?',()=>{
              this.$storage.setSessionStorage('redirectPath', {path : this.$route.path, query : this.$route.query});
              this.$router.push({name : 'member-login'});
            });
            return;
      }
      this.hasActive = !this.hasActive;
      if (!this.hasActive) {
        this.count--;
      } else {
        this.count++;
      }
      this.$emit("input", { active: this.hasActive, count: this.count });
    },
  },
};
</script>
