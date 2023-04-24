<template>
    <!-- 쿠폰발급 중지/재개 팝업 -->
    <div id="modal-wrap" class="modal" style="display: block;">
        <div class="modal-content" style="width: 900px;">
            <div class="pop-header">
                <h2 v-if="info.cpnistype == constants.CPN_IS_STOP">쿠폰발급 중지</h2>
                <h2 v-if="info.cpnistype == constants.CPN_IS_RESTART">쿠폰발급 재개</h2>
                <button type="button" class="pop-close" @click="$modal.hide('commonModal')"></button>
            </div>
            <div class="pop-body">
                <div class="form-area">
                    <table cellpadding="0" cellspacing="0" class="gray-tb">
                        <colgroup>
                            <col width="170px">
                            <col width="">
                        </colgroup>
                        <tbody v-if="info.cpnistype == constants.CPN_IS_STOP">
                            <!-- 쿠폰발급 중지 팝업일 때 -->
                            <tr>
                                <th>적용시점<i class="essential"></i></th>
                                <td>
                                    <div class="radio_wrap dpib">
                                        <input type="radio" name="isnowstopD" id="isnowstopDT" v-model="info.isnowstop" value="T" />
                                        <label for="isnowstopDT">즉시 중지</label>
                                        <input type="radio" name="isnowstopD" id="isnowstopDF" v-model="info.isnowstop" value="F" />
                                        <label for="isnowstopDF">발급중지기간 설정</label>
                                    </div>
                                    <span class="ml3 txt-gray">(설정한 발급중지기간 종료일 이후 쿠폰 발급 재개)</span>
                                </td>
                            </tr>
                            <tr v-show="info.isnowstop=='F'">
                                <th>발급중지기간<i class="essential"></i></th>
                                <td>
                                    <CommonDatePickerFromTo
                                        :fromYYYYMMDD="info.cpnstopstdate"
                                        :fromHH="info.cpnstopsthour"
                                        :fromMM="info.cpnstopstmin"
                                        :toYYYYMMDD="info.cpnstopeddate"
                                        :toHH="info.cpnstopedhour"
                                        :toMM="info.cpnstopedmin"
                                        :useFrom="true"
                                        :useTo="true"
                                        @getDate="pickerCpnstopChangeEvent"
                                    />
                                </td>
                            </tr>
                            <!-- /쿠폰발급 중지 팝업일 때 -->
                        </tbody>
                        <tbody v-if="info.cpnistype == constants.CPN_IS_RESTART">
                            <!-- 쿠폰발급 재개 팝업일 때 -->
                            <tr>
                                <th>적용시점<i class="essential"></i></th>
                                <td>
                                    <div class="radio_wrap dpib">
                                        <input type="radio" name="isnowrestartD" id="isnowrestartDT" v-model="info.isnowrestart" value="T"/>
                                        <label for="isnowrestartDT">즉시 재개</label>
                                        <input type="radio" name="isnowrestartD" id="isnowrestartDF" v-model="info.isnowrestart" value="F"/>
                                        <label for="isnowrestartDF">발급재개시점 설정</label>
                                    </div>
                                    <span class="ml3 txt-gray">(설정한 발급재개시점 이후 쿠폰 발급 재개)</span>
                                </td>
                            </tr>
                            <tr v-show="info.isnowrestart=='F'">
                                <th>발급재개시점<i class="essential"></i></th>
                                <td>
                                    <CommonDatePickerFromTo
                                        :fromYYYYMMDD="info.cpnrestartdate"
                                        :fromHH="info.cpnrestarthour"
                                        :fromMM="info.cpnrestartmin"
                                        :useFrom="true"
                                        :useTo="false"
                                        @getDate="pickerCpnrestartChangeEvent"
                                    />
                                    <span class="ml3">이후 쿠폰 발급이 재개됩니다.</span>
                                </td>
                            </tr>
                            <!-- /쿠폰발급 재개 팝업일 때 -->
                        </tbody>
                    </table>
                </div>
                <div class="btn-group">
                    <button type="button" class="btn big blue" @click="changeIssue">확인</button>
                    <button type="button" class="btn big darkgray" @click="$modal.hide('commonModal')">취소</button>
                </div>
            </div>
        </div>
    </div>
    <!-- /쿠폰발급 중지/재개 팝업 -->
</template>

<script>
import store from "@js/store.js";
import CommonDatePickerFromTo from '@views.admin/common/CommonDatePickerFromTo.vue';

export default {
    name: 'ControllIssuePopup',
    props: {
        modalComp: Object,
        params: Object,
        callbackFn: Function
    },
    components: {
        CommonDatePickerFromTo
    },
    data() {
        return {
            constants: store.getters['ADMIN'],
            info: {
                cpnisidx: '',       // 공통쿠폰발급이력IDX
                comcpnidx: '',      // 공통쿠폰정보일련번호
                cpninfoidx: '',     // 공통쿠폰일련번호
                cpnistype: '',      // (요청)쿠폰발급처리구분
                isnowstop: '',      // 즉시중지
                isnowrestart: '',   // 즉시재개
                cpnstopstday: '',   // 발급중지시작일
                cpnstopstdate: '',  // 발급중지시작일자
                cpnstopsthour: '',  // 발급중지시작시간
                cpnstopstmin: '',   // 발급중지시작분
                cpnstopedday: '',   // 발급중지종료일
                cpnstopeddate: '',  // 발급중지종료일자
                cpnstopedhour: '',  // 발급중지종료시간
                cpnstopedmin: '',   // 발급중지종료분
                cpnrestartday: '',  // 발급재개일
                cpnrestartdate: '', // 발급재개일자
                cpnrestarthour: '', // 발급재개시간
                cpnrestartmin: ''   // 발급재개분
            }
        }
    },
    mounted() {
        this.info.comcpnidx = this.params.comcpnidx;
        this.info.cpninfoidx = this.params.cpninfoidx;
        this.info.cpnistype = this.params.reqCpnistype;
        this.getCouponIssueInfo();
    },
    methods: {
        getCouponIssueInfo: function() {
            this.$http.post('/admin/promotion/coupon/issue/detail', this.info)
                    .then(result => {
                        this.$util.debug(result);
                        let data = result.data;
                        if (!this.$util.isNull(data)) {
                            for (const [key] of Object.entries(data)) {
                                this.info[key] = data[key];
                            }
                        } else {
                            this.info.isnowstop = 'T';
                            this.info.isnowrestart = 'T';
                        }
                    })
                    .catch(error => {
                        this.$util.debug(error);
                    });
        },
        // 발급중지기간 datepicker callback
        pickerCpnstopChangeEvent: function(data) {
            this.info.cpnstopstdate = data.fromYYYYMMDD;
            this.info.cpnstopsthour = data.fromHH;
            this.info.cpnstopstmin = data.fromMM;
            this.info.cpnstopstday = data.fromDate12;

            this.info.cpnstopeddate = data.toYYYYMMDD;
            this.info.cpnstopedhour = data.toHH;
            this.info.cpnstopedmin = data.toMM;
            this.info.cpnstopedday = data.toDate12;
        },
        // 발급재개일시 datepicker callback
        pickerCpnrestartChangeEvent: function(data) {
            this.info.cpnrestartdate = data.fromYYYYMMDD;
            this.info.cpnrestarthour = data.fromHH;
            this.info.cpnrestartmin = data.fromMM;
            this.info.cpnrestartday = data.fromDate12;
        },
        // 발급변경(중지/재개)
        changeIssue: function() {
            let cpnistypename = '';
            // 발급중지
            if(this.info.cpnistype == this.constants.CPN_IS_STOP) {
                cpnistypename = '발급중지';
                // 필수입력체크 체크
                if(this.$util.isNull(this.info.isnowstop)) {
                    alert('적용시점을 선택해주세요.');
                    return;
                }
                if(this.info.isnowstop=='F') {
                    let valid = [
                        {field: 'info.cpnstopstdate', type: 'I', name:'발급중지시작일자', required: true},
                        {field: 'info.cpnstopsthour', type: 'I', name:'발급중지시작시간', required: true},
                        {field: 'info.cpnstopstmin', type: 'I', name:'발급중지시작시간', required: true},
                        {field: 'info.cpnstopeddate', type: 'I', name:'발급중지종료일자', required: true},
                        {field: 'info.cpnstopedhour', type: 'I', name:'발급중지종료시간', required: true},
                        {field: 'info.cpnstopedmin', type: 'I', name:'발급중지종료시간', required: true}
                    ];
                    let msg = this.$util.validMsg(this.$data, valid);
                    if(!this.$util.isNull(msg)){
                        alert(msg);
                        return;
                    }
                    // 기간 From-To 체크
                    let cpnstopstday = parseInt(this.info.cpnstopstday);
                    let cpnstopedday = parseInt(this.info.cpnstopedday);
                    if (cpnstopstday > cpnstopedday) {
                        alert('발급중지 종료일시를 발급중지 시작일시 이후로 입력하세요.');
                        return;
                    }
                }
            }
            // 발급재개
            else if (this.info.cpnistype == this.constants.CPN_IS_RESTART) {
                cpnistypename = '발급재개';
                // 필수입력체크 체크
                if(this.$util.isNull(this.info.isnowrestart)) {
                    alert('발급재개시점을 선택해주세요.');
                    return;
                }
                if(this.info.isnowrestart=='F') {
                    let valid = [
                        {field: 'info.cpnrestartdate', type: 'I', name:'발급재개일자', required: true},
                        {field: 'info.cpnrestarthour', type: 'I', name:'발급재개시간', required: true},
                        {field: 'info.cpnrestartmin', type: 'I', name:'발급재개시간', required: true}
                    ];
                    let msg = this.$util.validMsg(this.$data, valid);
                    if(!this.$util.isNull(msg)){
                        alert(msg);
                        return;
                    }
                }
            }
            
            // 저장
            if(confirm(cpnistypename + ' 하시겠습니까?')) {
                this.$http.post('/admin/promotion/coupon/issue/save', this.info)
                    .then(result => {
                        this.$util.debug(result);
                        if (result.statusCode =='200') {
                            alert('처리가 완료되였습니다.');
                            this.closePopup({isreload: true});
                        }
                    })
                    .catch(error => {
                        this.$util.debug(error);
                    });
            }
        },
        closePopup(params){
            if(typeof(this.callbackFn) === 'function') {
                this.callbackFn(params);
            }
            this.$modal.hide('commonModal');
        }
    }
}
</script>