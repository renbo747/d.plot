<template>
  <div id="modal-wrap" class="modal" style="display: block;">
    <div class="modal-content" style="width: 480px;">
      <div class="pop-header">
        <h2>2차인증</h2>
        <button type="button" class="pop-close" @click="$modal.hide('commonModal');"></button>
      </div>
      <div class="pop-body">
        <div class="title">입점 시 등록되어 있는 제휴 담당자 휴대폰으로 인증문자가 발송됩니다.</div>
          <table cellpadding="0" cellspacing="0" class="gray-tb mt10">
            <colgroup>
              <col width="120px">
              <col width="">
            </colgroup>
            <tbody>
            <tr>
              <th>아이디</th>
              <td><input type="text" v-model="info.id" ref="id" style="width: 194px;" readonly="true"></td>
            </tr>
            <tr>
              <th>제휴 담당자 명</th>
              <td><input type="text" v-model="info.name" style="width: 194px;" readonly="true"></td>
            </tr>
            <tr>
              <th>휴대폰</th>
              <td>
                <input type="text" v-model="info.phone" style="width: 194px;" :readonly="isAuthComplete">
                <button type="button" class="btn blue-line ml3" @click="userAuthCheck">본인 인증</button>
              </td>
            </tr>
            </tbody>
          </table>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "SecondAuth",
  props: {
    modalComp: Object,
    params: Object,
    callbackFn: Function
  },
  data() {
    return {
      findList : [],
      info : {
        id : '',
        name : '',
        phone : '',
        ci :'',
       
        chargetype : 'CHA001'
      },
      isAuthComplete : false,
    }
  },
  mounted() {

  },
  created() {
    this.info.id = this.params.id;
    this.info.name = this.params.name;
    this.info.phone = this.params.phone;
  },
  methods : {
    userAuthCheck: function(){
      window.popupCallback = this.certClose;
      this.certPopupObj = window.open('/common/partnership/kmc/request?code='+process.env.VUE_APP_KMC_CERT_PARTNERS+"&name="+this.info.name+"&phone="+this.info.phone, "KMCISWindow", "width=425, height=550, resizable=0, scrollbars=no, status=0, titlebar=0, toolbar=0, left=435, top=250");
    },
    certClose(certData){
      if(certData != null) {
        if (typeof certData.ci !== 'undefined') {
          this.info.ci = certData.ci;
          this.info.name = certData.name;
          this.info.phone = certData.phoneno;
          this.isAuthComplete = true;
          window.popupCallback = null;
          this.$modal.hide('commonModal');
          this.callbackFn({isAuthComplete: this.isAuthComplete});
        }
      }
    },
  }
}
</script>

<style scoped />