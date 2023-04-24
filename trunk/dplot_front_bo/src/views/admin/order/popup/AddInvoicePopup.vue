<template>
    <!-- 송장추가 팝업 -->
    <div id="modal-wrap" class="modal" style="display: block;">
        <div class="modal-content" style="width: 560px;">
            <div class="pop-header">
                <h2>송장추가</h2>
                <button type="button" class="pop-close" @click="closePopup"></button>
            </div>
            <div class="pop-body">
                <div class="blue-box mg0">
                    <ul>
                        <li>여러 개의 박스로 나눌 경우 송장을 추가로 입력하시기 바랍니다.</li>
                    </ul>
                </div>
                <table cellpadding="0" cellspacing="0" class="data-tb align-c">
                    <colgroup>
                        <col width="120px" /><!-- 택배사 -->
                        <col width="" /><!-- 송장번호 -->
                        <col width="60px" /><!-- 배송수량 -->
                    </colgroup>
                    <thead>
                        <tr>
                            <th>택배사</th>
                            <th>송장번호</th>
                            <th>배송수량</th>
                        </tr>
                    </thead>
                    <tbody v-if="info.orderDelivList.length > 0">
                        <tr v-for="(item, index) in info.orderDelivList" :key="index">
                            <td>
                                <select v-model="item.logistype" :disabled="item.istracking==='T' && $util.isNull(item.trckerrmsg)">
                                    <option v-for="logistype in commonCode.logistype" :key="logistype.cmcode" :value="logistype.cmcode">{{ logistype.codename }}</option>
                                </select>
                            </td>
                            <td class="left">
                                <input type="text" style="width: calc(100% - 58px);" placeholder="송장번호" v-model="item.invoiceno" :disabled="item.istracking==='T' && $util.isNull(item.trckerrmsg)"
                                    maxlength="50" oninput="this.value = this.value.replace(/(^0|[^0-9])/gi, '');"/>
                                <button type="button" class="add" @click="addInvoice(index)"></button>
                                <button type="button" class="del" @click="removeInvoice(index)" v-if="index>0 && !(item.istracking==='T' && $util.isNull(item.trckerrmsg))"></button>
                            </td>
                            <td>
                                <input type="text" v-model="item.ordcnt" :disabled="item.istracking==='T' && $util.isNull(item.trckerrmsg)"
                                    maxlength="5" oninput="this.value = this.value.replace(/(^0|[^0-9])/gi, '');"/>
                            </td>
                        </tr>
                    </tbody>
                </table>
                <div class="btn-group">
                    <button type="button" class="btn big blue" @click="saveInvoice">저장</button>
                    <button type="button" class="btn big darkgray" @click="closePopup">취소</button>
                </div>
            </div>
        </div>
    </div>
    <!-- /송장추가 팝업 -->
</template>

<script>
export default {
    name: 'AddInvoicePopup',
    props: ['activeOrdgdidx', 'activeOrgdelividx'],
    data() {
        return {
            isPartner: false,
            user: {},
            info: {
                ordgdidx: '',       // 주문상품IDX
                orderDelivList: [], // 송장목록
                delOrgdelivIdxList: []  // 삭제IDX 목록
            },
            commonCode: {
                logistype: []       // 택배사종류
            },
        }
    },
    mounted() {
        this.isPartner = this.$util.isAuthorized(this.$store.getters['CONSTANTS'].PARTNER_USER);
        this.user = this.$util.getUser(this.$store.getters['CONSTANTS'].MANAGER_SESSION);
        this.info.ordgdidx = this.activeOrdgdidx
        // 공통코드 조회
        this.getCommonCodeList();
    },
    methods: {
        // 공통코드 목록 조회
        getCommonCodeList: function() {
            let cmclassArr = ['LOGISTYPE'];
            this.$http.post('/common/code/map/list', {cmclass : cmclassArr, isloading: false})
                .then(result =>{
                    let data = result.data;
                    for (const [key] of Object.entries(data)) {
                        this.commonCode[key] = data[key];
                    }
                    // 목록 조회
                    this.searchInvoiceList();
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 송장목록 조회
        searchInvoiceList: function() {
            this.$http.post('/admin/order/manage/invoice/list', { ordgdidx: this.activeOrdgdidx })
                .then(result => {
                    this.$util.debug(result);
                    this.info.orderDelivList = result.data.list;

                    if (this.info.orderDelivList.length === 0) {
                        let param = { 
                            ordgdidx: this.activeOrdgdidx,
                            orgdelividx: this.activeOrgdelividx,
                            logistype: '',
                            invoiceno: '',
                            ordcnt: '',
                            frstordcnt: 0,
                            status: 'I'
                        };
                        this.info.orderDelivList.splice(1, 0, param);
                    }
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 송장 추가
        addInvoice: function(index) {
            let param = { 
                ordgdidx: this.activeOrdgdidx,
                orgdelividx: '',
                logistype: '',
                invoiceno: '',
                ordcnt: '',
                frstordcnt: this.info.orderDelivList[0].frstordcnt,
                status: 'I'
            };
            this.info.orderDelivList.splice(index+1, 0, param);
        },
        // 송장 삭제
        removeInvoice: function(index) {
            if (this.info.orderDelivList[index].status === 'N') {
                this.info.delOrgdelivIdxList.push(this.info.orderDelivList[index].orgdelividx);
            }
            this.info.orderDelivList.splice(index, 1);
            if (this.info.orderDelivList.length === 0) {
                this.addInvoice(0);
            }
        },
        // 송장 저장
        saveInvoice: function() {
            if (this.info.orderDelivList.length === 0) {
                alert('한 개 이상의 송장정보를 추가해주세요.');
                return;
            }
            let frstordcnt = 0;
            let totordcnt = 0;
            for (let i=0; i<this.info.orderDelivList.length; i++) {
                let item = this.info.orderDelivList[i];
                if (this.$util.isNull(item.logistype)) {
                    alert('택배사를 선택해주세요.');
                    return;
                }
                if (this.$util.isNull(item.invoiceno.trim())) {
                    alert('송장번호를 입력해주세요.');
                    return;
                }
                if (this.$util.isNull(item.ordcnt) || item.ordcnt === 0) {
                    alert('배송수량을 입력해주세요.');
                    return;
                }
                if (Number(item.frstordcnt) < Number(item.ordcnt)) {
                    alert('배송수량이 주문수량 이상입니다. 확인후 진행해주세요.');
                    return;
                }
                totordcnt = Number(totordcnt) + Number(item.ordcnt);
                frstordcnt = Number(item.frstordcnt);
            }
            if (Number(frstordcnt) < Number(totordcnt)) {
                alert('총 배송수량이 주문수량 이상입니다. 확인후 진행해주세요.');
                return;
            }
            if (frstordcnt !== totordcnt) {
                alert('총 배송수량이 주문수량과 다릅니다. 확인후 진행해주세요.');
                return;
            }
            if (confirm('저장하시겠습니까?')) {
                if (this.isPartner) {
                    params.dealerno = this.user.no;
                }
                this.$http.post('/admin/order/manage/invoice/add', this.info)
                    .then(result => {
                        this.$util.debug(result);
                        if (result.statusCode == 200) {
                            alert('저장이 완료되었습니다.');
                            this.$emit('closePopup', true);
                        }
                    })
                    .catch(error => {
                        this.$util.debug(error);
                    });
            }
        },
        // 팝업 닫기
        closePopup: function() {
            this.$emit('closePopup');
        },
    }
}
</script>