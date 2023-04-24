<template>
  <div class="dp-like-button" @click="handleLike">
    <i class="dp-icon like-icon" :class="{ active: hasActive }"></i>
    <p class="mb-0 dp-caption">{{ count }}</p>
  </div>
</template>

<script>
export default {
  props: {
    isActive: {
      type: Boolean,
      default: false,
    },
    value: {
      type: Number,
      default: 0,
    },
    reviewidx: {
      type: Number,
    },
  },
  data() {
    return {
      hasActive: this.isActive,
      count: this.value,
    };
  },
  methods: {
    handleLike() {
      if(!this.$store.state.isLogin) {
          this.$eventBus.$emit('confirm', '로그인', '로그인 하시겠습니까?',()=>{
              this.$storage.setSessionStorage('redirectPath', {path : this.$route.path, query : this.$route.query});
              this.$router.push({name : 'member-login'});
          });
          return;
      }

      this.$util.debug(this.reviewidx);
      this.$http.post("/review/help", { reviewidx: this.reviewidx }).then((result) => {
          if (result.statusCode == 200) {
            this.$util.debug("리뷰 좋아요");
            this.hasActive = !this.hasActive;
            if (!this.hasActive) {
              this.count--;
            } else {
              this.count++;
            }
            this.$emit("input", { active: this.hasActive, count: this.count });
          }
        });
    },
  },
  watch: {
    value(value) {
      this.count = value;
    },
    isActive(value) {
      this.hasActive = value;
    },
  },
};
</script>
