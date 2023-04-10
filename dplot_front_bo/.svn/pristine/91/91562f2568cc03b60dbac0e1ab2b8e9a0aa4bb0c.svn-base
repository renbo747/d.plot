<template>
  <div>
    <PartnersHeader/>
    <PartnersSide />
    <component v-bind:is="modalComp"/>
    <common-footer/>
  </div>
</template>

<script>
import PartnersHeader from '@/views/partners/PartnersHeader'
import PartnersSide from '@/views/partners/PartnersSide'
import CommonFooter from '@/views/admin/common/CommonFooter.vue'

export default {
  components: { PartnersHeader, PartnersSide, CommonFooter },
  name: 'PartnersMain',
  data () {
    return {
      modalComp : null,
      params: {},
      callbackFn: function() {}
    }
  },
  mounted() {
    // 모달 팝업 SHOW
    this.$eventBus.$on('modalShow', function(comp, params, callbackFn) {
      this.$modal.show(comp, {
        'modalComp': comp,
        'params': params,
        'callbackFn': callbackFn
      }, {
        name: "commonModal",
        draggable: false,
        resizable: false,
        width: '100%',
        height: 'auto',
      })
    });
  }
}
</script>