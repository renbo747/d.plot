<template>
    
    <!-- 배송지 정보 수정 팝업 -->
    <div id="modal-wrap" class="modal" style="display: block;">
        <div class="modal-content" style="width: 600px;">
            <div class="pop-header">
                <h2>배송지 정보 수정</h2>
                <button type="button" class="pop-close" @click="closePopup"></button>
            </div>
            <div class="pop-body">
                <table cellpadding="0" cellspacing="0" class="gray-tb">
                    <colgroup>
                        <col width="120px">
                        <col width="157px">
                        <col width="120px">
                        <col width="">
                    </colgroup>
                    <tbody>
                        <tr>
                            <th>수령인 명</th>
                            <td colspan="3">
                                <input type="text" v-model="info.rcvname" maxlength="50"/>
                            </td>
                        </tr>
                        <tr>
                            <th>연락처1</th>
                            <td>
                                <input type="text" style="width: 137px;" v-model="info.rcvtel1" maxlength="20"/>
                            </td>
                            <th>연락처2</th>
                            <td>
                                <input type="text" style="width: 137px;" v-model="info.rcvtel2" maxlength="20"/>
                            </td>
                        </tr>
                        <tr>
                            <th>주소</th>
                            <td colspan="3">
                                <div class="dpb">
                                    <input type="text" class="short" v-model="info.rcvpost" readonly>
                                    <button type="button" class="btn blue-line ml3" @click="searchAddress">주소검색</button>
                                </div>
                                <input type="text" class="dpb" style="width: 100%;" v-model="info.rcvaddr" readonly >
                                <input type="text" class="dpb" style="width: 100%;" v-model="info.rcvaddrdetail">
                                <span class="small-txt">[{{ info.rcvaddrroad===''? ' ':'도로명' }}] {{ info.rcvaddrroad + " " + info.rcvaddrdetailroad }}</span>
                            </td>
                        </tr>
                        <tr>
                            <th>배송요청사항</th>
                            <td colspan="3">
                                <div class="dpb">
                                    <select style="width: 100%;" v-model="info.rcvreqtype" @change="info.rcvreqdetail=''">
                                        <option v-for="rcvreqtype in commonCode.rcvreqtype" :key="rcvreqtype.cmcode" :value="rcvreqtype.cmcode">{{ rcvreqtype.codename }}</option>
                                    </select>
                                </div>
                                <div class="dpb" v-if="info.rcvreqtype === $store.getters['ADMIN'].RCV_REQ_INPUT">
                                    <input type="text" style="width: 100%;" v-model="info.rcvreqdetail" maxlength="100"/>
                                </div>
                            </td>
                        </tr>
                    </tbody>
                </table>
                <div class="btn-group">
                    <button type="button" class="btn big blue" @click="save">저장</button>
                    <button type="button" class="btn big darkgray" @click="closePopup">닫기</button>
                </div>
            </div>
        </div>
    </div>
    <!-- /배송지 정보 수정 팝업 -->
</template>

<script>
export default {
    name: 'ChangeRcvinfoPopup',
    props: ['activeOrdno'],
    data() {
        return {
            info: {},
            commonCode: {
                rcvreqtype: []  // 배송요청타입
            },
        }
    },
    mounted() {
        this.getCommonCodeList();
    },
    methods: {
        // 공통코드 목록 조회
        getCommonCodeList: function() {
            let cmclassArr = ['RCVREQTYPE'];
            this.$http.post('/common/code/map/list', {cmclass : cmclassArr, isloading: false})
                .then(result =>{
                    let data = result.data;
                    for (const [key] of Object.entries(data)) {
                        this.commonCode[key] = data[key];
                    }
                    // 배송지정보 조회
                    this.getRcvInfo();
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 배송지정보 조회
        getRcvInfo: function() {
            let params = {
                ordno: this.activeOrdno
            };
            this.$http.post('/admin/order/manage/info', params)
                .then(result =>{
                    this.$util.debug(result);
                    this.info = result.data.info;
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 주소검색
        searchAddress: function() {
            new window.daum.Postcode({
                oncomplete: (data) => {
                    this.$set(this.info, 'rcvpost', data.zonecode);
                    this.$set(this.info, 'rcvaddrroad', this.$util.isNull(data.roadAddress)? data.autoRoadAddress : data.roadAddress);
                    this.$set(this.info, 'rcvaddr', this.$util.isNull(data.jibunAddress)? data.autoJibunAddress : data.jibunAddress);
                    this.$set(this.info, 'rcvsigungucode', data.sigunguCode);
                    this.$set(this.info, 'rcvbuildingcode', data.buildingCode);
                    this.$set(this.info, 'rcvroadnamecode', data.roadnameCode);
                }
            }).open();
        },
        // 배송지정보 저장
        save: function() {
            let valid = [
                {field: 'info.rcvname'      , type: 'I', name:'수령인명',       required: true},
                {field: 'info.rcvtel1'      , type: 'I', name:'연락처',         required: true},
                {field: 'info.rcvpost'      , type: 'I', name:'주소',           required: true},
                {field: 'info.rcvreqtype'   , type: 'S', name:'배송요청사항',    required: true},
            ];
            let msg = this.$util.validMsg(this.$data, valid);
            if(!this.$util.isNull(msg)){
                alert(msg);
                return;
            }

            if (confirm('저장하시겠습니까?')) {
                this.$http.post('/admin/order/manage/rcv/update', this.info)
                    .then(result =>{
                        this.$util.debug(result);
                        if (result.statusCode == '200') {
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