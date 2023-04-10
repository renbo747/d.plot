<template>
    <!-- 반려, 철회 사유 팝업 -->
    <div id="modal-wrap" class="modal" style="display: block;">
        <div class="modal-content" style="width: 400px;">
            <div class="pop-header">
                <h2>{{ reqName }}</h2>
                <button type="button" class="pop-close" @click="$modal.hide('commonModal');"></button>
            </div>
            <div class="pop-body">
                <textarea :placeholder="reqName+' 사유 입력 필수'" maxlength="200" v-model="reason"></textarea>
                <table cellpadding="0" cellspacing="0" class="gray-tb mt10" v-if="returnStatusArr.indexOf(params.reqStatus) > -1">
                    <colgroup>
                        <col width="120px">
                        <col width="">
                    </colgroup>
                    <tbody>
                        <tr>
                            <th>택배사</th>
                            <td>
                                <select v-model="rtnlogistype">                    
                                    <option :value="null">선택</option>
                                    <option v-for="row in commonCode.logistype" :key="row.cmcode" :value="row.cmcode">{{ row.codename }}</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <th>송장번호</th>
                            <td>
                                <input type="text" placeholder="송장번호" v-model="rtninvoiceno" maxlength="50" oninput="this.value = this.value.replace(/(^0|[^0-9])/gi, '');">
                            </td>
                        </tr>
                    </tbody>
                </table>
                <div class="btn-group mt10">
                    <button type="button" class="btn blue" @click="sendData">저장</button>
                </div>
            </div>
        </div>
    </div>
    <!-- /반려, 철회사유 팝업 -->
</template>

<script>
import store from "@js/store.js";
export default {
    name: 'InputReasonPopup',
    props : {
        modalComp: Object,
        params: Object,
        callbackFn: Function
    },
    data() {
        return {
            returnStatusArr: [],    // 반송상태리스트
            reqName: '',  // 팝업타이틀
            reason: '',   // 사유
            rtnlogistype: null,   // 반송택배사코드
            rtninvoiceno: null,   // 반송운송장번호
            commonCode: {
                logistype: []      // 택배사코드
            },
        }
    },
    mounted() {
        this.reqName = this.params.reqName;
        this.returnStatusArr = [
            store.getters['ADMIN'].RETURN_A_RETURN,
            store.getters['ADMIN'].EXCHANGE_A_RETURN
        ]
        // 공통코드 조회
        this.getCommonCodeList();
    },
    methods : {
        // 공통코드 목록 조회
        getCommonCodeList: function() {
            let cmclassArr = ['LOGISTYPE'];
            this.$http.post('/common/code/map/list', {cmclass : cmclassArr, isloading: false})
                .then(result =>{
                    let data = result.data;
                    for (const [key] of Object.entries(data)) {
                        this.commonCode[key] = data[key];
                    }
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 데이터 전달
        sendData: function() {
            if (this.$util.isNull(this.reason.trim())) {
                alert(this.reqName + " 사유를 입력해주세요.");
                return;
            }
            if (this.returnStatusArr.indexOf(this.params.reqStatus) > -1) {
                if (this.$util.isNull(this.rtnlogistype)) {
                    alert("반송 택배사를 선택해주세요.");
                    return;
                }
                if (this.$util.isNull(this.rtninvoiceno)) {
                    alert("반송 운송장번호를 입력해주세요.");
                    return;
                }
            }
            if( typeof(this.callbackFn) == 'function') {
                let data = {
                    reason: this.reason,
                    rtnlogistype: this.rtnlogistype,
                    rtninvoiceno: this.rtninvoiceno
                }
                this.callbackFn(data);
            }
            this.$modal.hide('commonModal');
        }
    }
}
</script>