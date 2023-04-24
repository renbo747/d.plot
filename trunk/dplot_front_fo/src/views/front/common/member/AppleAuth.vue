<template>
  <SnsLogin ref="snsLogin"></SnsLogin>
</template>

<script>
import SnsLogin from "@views.common/member/SnsLogin.vue";
export default {
  components: { SnsLogin },
  data() {
    return {};
  },
  mounted() {

    // const param = {
    //     snsmemberno : this.$route.query.snsmemberno,
    //     email : this.$route.query.email,
    //     snstype : 'ULT003'
    // }

    // this.$refs.snsLogin.snsLogin(param);
  },
  methods: {},
};
</script>
