<template>
  <main class="dp-loading">
    <div class="container">
      <div class="dp-loading-wrap">
        <div class="dp-loading__img mb-2">
          <figure class="dp-loading__figure">
            <logo />
          </figure>
        </div>
        <div class="dp-loading__text__wrap dp-panel">
          <p class="dp-loading__text mb-0">
            아이디찾기 처리중입니다.
          </p>
        </div>
        <div class="dp-loading__animation">
          <div class="lds-ring">
            <div></div>
            <div></div>
            <div></div>
            <div></div>
          </div>
        </div>
      </div>
    </div>
  </main>
</template>

<script>
import IdFindSuccess from "@views.mobile/member/popup/IdFindSuccess.vue"
import IdFindSnsSuccess from "@views.mobile/member/popup/IdFindSnsSuccess.vue"
import IdFindFail from "@views.mobile/member/popup/IdFindFail.vue"

export default {
    beforeCreate() {
        this.$store.commit("onSubHeaderOption", {
        title: "본인확인",
        searchIcon: false,
        cartIcon: false,
        homeIcon: false,
        });
    },
    mounted() {
        const memberInfo = this.$route.params.memberInfo;
        
        if(memberInfo == null) {
            // if (window.sessionStorage.getItem('platform') == "MAC001") {
            //      this.$router.push({name:'member-id-find-fail'});
            // }else{
            //      this.$eventBus.$emit('showModal', IdFindFail, 'id-find-fail');
            //      if (this.$route.name  != 'member-login') {
            //          this.$router.replace({name : 'member-login'});
            //      }
            // }
                this.$eventBus.$emit('showModal', IdFindFail, 'id-find-fail');
                 if (this.$route.name  != 'member-login') {
                     this.$router.replace({name : 'member-login'});
                 }
           
        } else {
            if(memberInfo.joinchtype != 'UCT001') {
                // if (window.sessionStorage.getItem('platform') == "MAC001") {
                //     this.$router.push({name:'member-id-find-sns-success',params:memberInfo});
                // }else{
                //     this.$eventBus.$emit('showModal', IdFindSnsSuccess, 'id-find-sns-success', memberInfo);
                //      if (this.$route.name  != 'member-login') {
                //         this.$router.replace({name : 'member-login'});
                //      }
                // }
                    this.$eventBus.$emit('showModal', IdFindSnsSuccess, 'id-find-sns-success', memberInfo);
                     if (this.$route.name  != 'member-login') {
                        this.$router.replace({name : 'member-login'});
                     }
            } else {
                // if (window.sessionStorage.getItem('platform') == "MAC001") {
                //     this.$router.push({name:'member-id-find-success',params:memberInfo});
                // }else{
                //     this.$eventBus.$emit('showModal', IdFindSuccess, 'id-find-success', memberInfo);
                //      if (this.$route.name  != 'member-login') {
                //         this.$router.replace({name : 'member-login'});
                //     }
                // }
                    this.$eventBus.$emit('showModal', IdFindSuccess, 'id-find-success', memberInfo);
                    if (this.$route.name  != 'member-login') {
                        this.$router.replace({name : 'member-login'});
                    }
            }
        }

       
    }
}
</script>

<style lang="scss">
.dp-loading {
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 0 auto;
  height: 100vh;
}
.dp-loading-wrap {
  text-align: center;
}

.dp-loading__figure {
  width: 200px;
  margin: 0 auto;
}

.dp-loading__text {
  font-size: 18px;
  letter-spacing: -0.72px;
  color: $gray-700;
  font-weight: 400;
}
.lds-ring {
  display: inline-block;
  position: relative;
  width: 80px;
  height: 80px;
}
.lds-ring div {
  box-sizing: border-box;
  display: block;
  position: absolute;
  width: 64px;
  height: 64px;
  margin: 8px;
  border: 8px solid $primary;
  border-radius: 50%;
  animation: lds-ring 1.2s cubic-bezier(0.5, 0, 0.5, 1) infinite;
  border-color: $primary transparent transparent transparent;
}
.lds-ring div:nth-child(1) {
  border-color: #ddd;
  animation-delay: -0.45s;
}
.lds-ring div:nth-child(2) {
  animation-delay: -0.3s;
}
.lds-ring div:nth-child(3) {
  animation-delay: -0.15s;
}
@keyframes lds-ring {
  0% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(360deg);
  }
}
</style>