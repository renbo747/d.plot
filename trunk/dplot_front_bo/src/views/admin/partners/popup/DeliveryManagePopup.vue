<template>
  <!-- 반품 택배사 관리 팝업 -->
  <div id="modal-wrap" class="modal" style="display: block;">
    <div class="modal-content" style="width: 630px;">
      <div class="pop-header">
        <h2>반품 택배사 관리</h2>
        <button type="button" class="pop-close" v-on:click="$modal.hide('commonModal');"></button>
      </div>
      <div class="pop-body">
        <div class="blue-box mg0">
          <ul>
            <li>반품 택배사 등록을 위해 계약한 택배사의 계약번호를 등록해 주세요.</li>
            <li>우체국택배의 경우 이용 신청 후 승인 완료까지 평균 1-2영업일 소요됩니다.</li>
          </ul>
        </div>
        <div class="boxing mt20">
          <dl class="slim-dt" v-for="(data, index) in deliveryList" v-bind:key="data.logistype">
            <dt>{{ data.logistname }}</dt>
            <dd>
              <div class="dpb">
                <input type="text" style="width: 300px;" v-model="deliveryList[index].locontcode" @keyup="codeChange($event, index)">
                <button v-if="data.logistype !== 'LGT002'" type="button" class="btn ml3 size80" :class=" (deliveryList[index].isvalid === 'T') ? 'blue-line' : 'red-line' " @click="validCheck(data, index)">유효성체크</button>
                <button v-if="data.logistype !== 'LGT002'" type="button" class="btn gray" @click="initDeliveryData(index)">초기화</button>
                <button v-if="data.logistype === 'LGT002'" type="button" class="btn blue-line ml3 size80" @click="postOfficeUseApply()">이용신청</button>
              </div>
              <span v-if="data.logistype === 'LGT001' " class="small-txt txt-orange dpb"><i class="icon-alert"></i>CJ 대한통운의 경우 계약번호를 8자리로 입력해 주셔야 합니다.<br>(7자리일 경우 앞에 0을 입력해 주세요!)</span>
            </dd>
          </dl>
        </div>
        <div class="btn-group">
          <button type="button" class="btn big blue" @click="deliverySave">저장</button>
          <button type="button" class="btn big darkgray" @click="$modal.hide('commonModal');">취소</button>
        </div>
      </div>
    </div>
  </div>
  <!-- /반품 택배사 관리 팝업 -->
</template>

<script>
export default {
  name: 'DeliveryManagePopup',
  props : {
    modalComp: Object,
    params: Object,
    callbackFn: Function
  },
  data() {
    return {
      userno : this.params.userno,
      bizno : this.params.bizno,
      deliveryList: [],
      originDeliveryList: []
    }
  },
  mounted() {
    this.setDeliveryData();
  },
  methods: {
    setDeliveryData() {
      let param = { userno : this.userno, cmclass : 'LOGISTYPE' };
      this.$http.post('/admin/partners/apply/delivery/list', param).then(result =>{

        this.originDeliveryList = JSON.parse(JSON.stringify(result.data.deliverylist));

        for(let code of result.data.logistypelist){
          const matched = result.data.deliverylist.filter(row => (code.cmcode === row.logistype));
          if(matched.length > 0){
            this.deliveryList.push(matched[0]);
          } else {
            let data = {
              idx : null,
              userno : this.userno,
              isvalid : 'F',
              logistype : code.cmcode,
              logistname : code.codename,
              locontcode : ''
            };
            this.deliveryList.push(data);
          }
        }
      }).catch(error => {
        this.$util.debug(error);
      })

    },
    initDeliveryData(index) {
      let locontCode = '';
      let isValid = 'F';
      let thisObj = this;
      this.originDeliveryList.forEach(function(row){
        if(row.logistype === thisObj.deliveryList[index].logistype){
          locontCode = row.locontcode
          isValid = row.isvalid;
        }
      });

      this.deliveryList[index].locontcode = locontCode;
      this.deliveryList[index].isvalid = isValid;
    },
    validCheck(data, index) {

      if(data.locontcode === '' || typeof data.locontcode === 'undefined'){
        alert('계약번호가 입력되지 않았습니다.');
        return false;
      }

      let param = {locontcode : data.locontcode, logistype : data.logistype, bizno: this.bizno}

      this.$http.post('/admin/partners/apply/delivery/valid', param).then(result =>{
        this.$util.debug(result);
        this.deliveryList[index].isvalid = (result.data.result === 'success') ? 'T' : 'F';
        let message = (result.data.result === 'success') ? '유효성 체크가 정상적으로 완료 되었습니다.' : '유효성 체크에 실패 하였습니다.';
        alert(message);
      }).catch(error => {
        this.$util.debug(error);
      })
    },
    codeChange(event, index) {
      let locontCode = '';
      let thisObj = this;
      this.originDeliveryList.forEach(function(row){
        if(row.logistype === thisObj.deliveryList[index].logistype){
          locontCode = row.locontcode
        }
      });

      this.deliveryList[index].isvalid = (locontCode=== event.target.value) ? this.deliveryList[index].isvalid : 'F';
    },
    postOfficeUseApply(){
      this.$eventBus.$emit('modalShow', null, {userno : this.activeUserNo},
          (result) => {
            alert(result);
          }
      );
    },
    deliverySave() {
      let param = { 'data' : this.deliveryList }
      this.$http.post("/admin/partners/apply/delivery/save", param).then(result => {
        let msg = (result.data.result === 'success') ? '반품택배사 등록이 완료 되었습니다.' : '반품택배사 등록에 실패 하였습니다.\n관리자에게 문의 바랍니다.';

        if(result.data.result === 'success' && typeof(this.callbackFn) == 'function') {
          this.callbackFn(msg, this.deliveryList);
          this.$modal.hide('commonModal');
        }

      }).catch(error => {
        this.$util.debug(error);
      })
    }
  }
}
</script>