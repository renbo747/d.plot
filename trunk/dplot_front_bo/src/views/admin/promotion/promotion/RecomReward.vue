<template>
    <!-- 컨텐츠 영역 -->
    <div class="content m-leftmenu">
        <common-navigator></common-navigator>
        <RecomRewardLog v-if="showList" v-on:closeList="closeList"></RecomRewardLog>
        <div class="inner">
            <div class="boxing">
                <div class="form-area">
                    <dl>
                        <dt>사용여부</dt>
                        <dd>
                            <div class="radio_wrap wide">
                                <input type="radio" v-model="info.istrash" name="group00" id="group01" value="F" checked/><label for="group01">사용함</label>
                                <input type="radio" v-model="info.istrash" name="group00" id="group02" value="T" /><label for="group02">사용안함</label>
                            </div>
                        </dd>
                    </dl>
                </div>
            </div>
            <div class="bar-title small">조건설정</div>
            <div class="form-area">
                <table cellpadding="0" cellspacing="0" class="gray-tb">
                    <colgroup>
                        <col width="180px">
                        <col width="">
                    </colgroup>
                    <tbody>
                        <tr>
                            <th>진행기간<i class="essential"></i></th>
                            <td>
                                <CommonDatePickerFromTo
                                  :fromYYYYMMDD="info.startdate"
                                  :fromHH="info.starthour"
                                  :fromMM="info.startmi"
                                  :toYYYYMMDD="info.enddate"
                                  :toHH="info.endhour"
                                  :toMM="info.endmi"
                                  :useFrom="true"
                                  :useTo="true"
                                  @getDate="pickerEventBus"
                                />
                            </td>
                        </tr>
                        <tr>
                            <th>혜택 구분<i class="essential"></i></th>
                            <td>
                                <div class="radio_wrap wide3">
                                    <input type="radio" v-model="info.recomtype" name="group01" id="group11" value="RCT001" checked /><label for="group11">적립금</label>
                                    <input type="radio" v-model="info.recomtype" name="group01" id="group12" value="RCT002"/><label for="group12">D포인트</label>
                                    <input type="radio" v-model="info.recomtype" name="group01" id="group13" value="RCT003"/><label for="group13">쿠폰</label>
                                </div>
                            </td>
                        </tr>
                        <tr v-show="info.recomtype === 'RCT001' || info.recomtype === 'RCT002'">
                            <th>피추천인 지급<i class="essential"></i></th>
                            <td>
                                <span>추천 받고 가입한 회원(피추천인)에게 {{info.recomtype === 'RCT001' ? '적립금 ': ''}}{{info.recomtype === 'RCT002' ? 'D포인트 ': ''}}</span>
                                <input type="text" v-model="info.revpoint" class="center" style="width: 60px;" />
                                <span>포인트를 회원가입 완료 후 지급</span>
                            </td>
                        </tr>
                        <tr v-show="info.recomtype === 'RCT003'">
                            <th>피추천인 지급<i class="essential"></i></th>
                            <td>
                                <span>추천 받고 가입한 회원(피추천인)에게</span>
                                <select v-model="info.revcpidx" style="width: 450px;">
                                    <option v-for="item in couponlist" :key="item.comcpnidx" :value="item.comcpnidx">{{item.title}}</option>
                                </select>
                                <span>쿠폰을 회원가입 완료 후 지급</span>
                            </td>
                        </tr>
                        <tr>
                            <th>추천인 지급 조건<i class="essential"></i></th>
                            <td>
                                <input type="checkbox" v-model="info.isjoingive" id="group21" true-value="T" false-value="F"/><label for="group21">피추천인 회원가입 시 지급</label>
                                <input type="checkbox" v-model="info.iscfmgive" id="group22" true-value="T" false-value="F"/><label for="group22">피추천인이 첫 구매확정 시 지급</label>
                            </td>
                        </tr>
                        <tr v-show="info.recomtype === 'RCT001' || info.recomtype === 'RCT002'">
                            <th>추천인 지급(회원가입)<i class="essential"></i></th>
                            <td>
                                <span>추천한 기존회원(추천인)에게 {{info.recomtype === 'RCT001' ? '적립금 ': ''}}{{info.recomtype === 'RCT002' ? 'D포인트 ': ''}}</span>
                                <input type="text" v-model="info.recjoinpoint" class="center" style="width: 60px;" />
                                <span>포인트 지급</span>
                            </td>
                        </tr>
                        <tr v-show="info.recomtype === 'RCT001' || info.recomtype === 'RCT002'">
                            <th>추천인 지급(첫구매확정)<i class="essential"></i></th>
                            <td>
                                <span>추천한 기존회원(추천인)에게 {{info.recomtype === 'RCT001' ? '적립금 ': ''}}{{info.recomtype === 'RCT002' ? 'D포인트 ': ''}}</span>
                                <input type="text" v-model="info.reccfmpoint" class="center" style="width: 60px;" />
                                <span>포인트 지급</span>
                            </td>
                        </tr>
                        <tr v-show="info.recomtype === 'RCT002'">
                            <th>D포인트 중복사용여부<i class="essential"></i></th>
                            <td>
                                <div class="radio_wrap wide dpib">
                                    <input type="radio" v-model="info.isepointdup" name="group03" id="group31" value="T" checked/><label for="group31">허용</label>
                                    <input type="radio" v-model="info.isepointdup" name="group03" id="group32" value="F" /><label for="group32">미허용</label>
                                </div>
                                <span class="txt-orange ml10"><i class="icon-alert"></i>적립금과 함께 사용 가능한지의 여부</span>
                            </td>
                        </tr>
                        <tr v-show="info.recomtype === 'RCT002'">
                            <th>D포인트 유효기간<i class="essential"></i></th>
                            <td>D포인트 적립금 프로모션을 통해 지급되는 D포인트의 유효기간은 진행기간과 동일하게 자동 설정됩니다.</td>
                        </tr>
                        <tr v-show="info.recomtype === 'RCT003'">
                            <th>추천인 지급(회원가입)<i class="essential"></i></th>
                            <td>
                                <span>추천한 기존회원(추천인)에게</span>
                                <select v-model="info.recjoincpidx" style="width: 450px;">
                                    <option v-for="item in couponlist" :key="item.comcpnidx" :value="item.comcpnidx">{{item.title}}</option>
                                </select>
                                <span>쿠폰을 지급</span>
                            </td>
                        </tr>
                        <tr v-show="info.recomtype === 'RCT003'">
                            <th>추천인 지급(첫구매확정)<i class="essential"></i></th>
                            <td>
                                <span>추천한 기존회원(추천인)에게</span>
                                <select v-model="info.reccfmcpidx" style="width: 450px;">
                                    <option v-for="item in couponlist" :key="item.comcpnidx" :value="item.comcpnidx">{{item.title}}</option>
                                </select>
                                <span>쿠폰을 지급</span>
                            </td>
                        </tr>
                        <tr>
                            <th>피추천인 수 제한<i class="essential"></i></th>
                            <td>
                                <span>1명의 회원이 최대한 추천 받을 수 있는 회원의 수를 </span>
                                <input type="text" v-model="info.reclimitcnt" class="center" style="width: 60px;"/>
                                <span>명으로 제한</span>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div class="bottom-group">
                <div class="btn-group left">
                    <button type="button" v-if="isRead" class="btn big blue-line" @click="searchRewardList">이력조회</button>
                </div>
                <div class="btn-group">
                    <button type="button" v-if="isWrite" class="btn big blue" @click="onSave">저장</button>
                </div>
            </div>
        </div>
    </div>
    <!-- /컨텐츠 영역 -->
</template>

<script>
import CommonNavigator from '../../common/CommonNavigator'
import CommonDatePickerFromTo from "@views.admin/common/CommonDatePickerFromTo";
import RecomRewardLog from './RecomRewardLog.vue'
export default {
    name: 'admin.promotion.promotion.recomreward',
    components: {
        CommonNavigator,
        CommonDatePickerFromTo,
        RecomRewardLog
    },
    data() {
        return {
            info: {
                istrash: 'F',       // 사용여부
                startdate: '',
                starthour: '',
                startmi: '',
                enddate: '',
                endhour: '',
                endmi: '',
                recomtype: '',      // 추천리워드혜택구분
                revpoint: '',       // 피추천인지급포인트
                isjoingive: 'T',    // 회원가입시지급여부
                iscfmgive: 'T',     // 첫구매확정지급
                recjoinpoint: '',   // 추천인회원가입지급포인트
                reccfmpoint: '',    // 추천인구매확정지급포인트
                reclimitcnt: 5,      // 피추천인수제한
                isepointdup: '',    // D포인트중복사용여부
                revcpidx: '',       // 피추천인쿠폰idx
                recjoincpidx: '',   // 추천인회원가입쿠폰idx
                reccfmcpidx: '',    // 추천인구매확정쿠폰idx
            },
            couponlist: [],         // 제공가능한 쿠폰
            isfirst: 'T',         // 첫 등록인지 확인
            showList: false,      // 이력조회 팝업 오픈여부
            isRead: false,
            isWrite: false,
        }
    },
    mounted() {
        this.$http.post('/admin/common/pageAuth/check', {url : this.$options.name}).then(result => {
            this.isRead = (result.data.isread === 'T');
            this.isWrite = (result.data.iswrite === 'T');

            if(this.isRead) {
                this.oninit();
            }
        }).catch(error => {
            this.$util.debug(error);
        });
    },
    methods: {
        // 초기 세팅
        oninit() {
            let param = {};

            this.$http.post('/admin/promotion/promotion/reward/search', param)
            .then(result => {
                if(result.statusCode === 200) {
                    if(result.data.info !== null) {
                        this.info = result.data.info;
                        this.isfirst = 'F';
                    }
                    this.couponlist = result.data.couponlist;
                }
            })
            .catch(error => {
                this.$util.debug(error);
            })
        },
        // datepicker callback
        pickerEventBus(data) {
            this.info.startdate = data.fromYYYYMMDD;
            this.info.starthour = data.fromHH;
            this.info.startmi = data.fromMM;
            this.info.recomstday = data.fromDate12;

            this.info.enddate = data.toYYYYMMDD;
            this.info.endhour = data.toHH;
            this.info.endmi = data.toMM;
            this.info.recomedday = data.toDate12;
        },
        // 이력조회
        searchRewardList() {
            this.showList = true;
        },
        // 이력조회 닫기
        closeList() {
            this.showList = false;
        },
        // 저장
        onSave() {
            if(!this.checkValidation()) {
                return ;
            }

            if(confirm("저장하시겠습니까?")) {
                let params = this.info;
                params.isfirst = this.isfirst;

                this.$http.post('/admin/promotion/promotion/reward/save', params)
                .then(result => {
                    if(result.statusCode === 200) {
                        alert("저장되었습니다.");
                    }
                })
                .catch(error => {
                    this.$util.debug(error);
                })
            }
        },
        // 저장 유효성 확인
        checkValidation() {
            let msg = '';
            if(this.info.istrash === 'T') {
                if(this.info.recomstday > 0 && this.info.recomstday < 12) {
                    alert("진행시작일자를 선택해주세요.");
                    return false;
                }
                if(this.info.recomedday > 0 && this.info.recomedday < 12) {
                    alert("진행종료일자를 선택해주세요.");
                    return false;
                }

                return true;
            }

            let valid = [
                {field: 'info.istrash', type: 'S', name: '사용여부', required: true},
                {field: 'info.startdate', type: 'I', name: '진행시작일자', required: true},
                {field: 'info.starthour', type: 'I', name: '진행시작일자', required: true},
                {field: 'info.startmi', type: 'I', name: '진행시작일자', required: true},
                {field: 'info.enddate', type: 'I', name: '진행종료일자', required: true},
                {field: 'info.endhour', type: 'I', name: '진행종료일자', required: true},
                {field: 'info.endmi', type: 'I', name: '진행종료일자', required: true},
                {field: 'info.recomtype', type: 'S', name: '혜택구분', required: true},
                {field: 'info.reclimitcnt', type: 'I', name: '피추천인 수 제한', required: true},
            ];

            if(this.info.recomtype === 'RCT001' || this.info.recomtype === 'RCT002') {
                valid.push({field: 'info.revpoint', type: 'I', name: '피추천인지급포인트', required: true});
                valid.push({field: 'info.recjoinpoint', type: 'I', name: '추천인회원가입지급포인트', required: true});
                valid.push({field: 'info.reccfmpoint', type: 'I', name: '추천인구매확정지급포인트', required: true});
                if(this.info.recomtype === 'RCT002'){
                    valid.push({field: 'info.isepointdup', type: 'I', name: 'D포인트 중복사용여부', required: true});
                }
            } else if(this.info.recomtype === 'RCT003') {
                valid.push({field: 'info.revcpidx', type: 'S', name: '피추천인지급쿠폰', required: true});
                valid.push({field: 'info.recjoincpidx', type: 'S', name: '추천인회원가입지급쿠폰', required: true});
                valid.push({field: 'info.reccfmcpidx', type: 'S', name: '추천인구매확정지급쿠폰', required: true});
            }

            msg = this.$util.validMsg(this.$data, valid);
            if(!this.$util.isNull(msg)){
                alert(msg);
                return false;
            }

            if(this.info.recomstday.length < 12) {
                alert("진행시작일자를 선택해주세요.");
                return false;
            }

            if(this.info.recomedday.length < 12) {
                alert("진행종료일자를 선택해주세요.");
                return false;
            }

            if(this.info.recomstday >= this.info.recomedday) {
                alert("진행종료일자를 시작일자 이후로 설정해주세요.");
                return false;
            }

            return true;
        }
    },
    watch: {
        'info.revpoint' : function(value, oldValue){
            if(this.$util.isNull(value)) return;
            let check = /^[0-9]{0,11}$/g;
            if(check.test(value)){
                return this.info.revpoint = value;
            }else{ 
                return this.info.revpoint = oldValue;
            }
        },
        'info.recjoinpoint' : function(value, oldValue){
            if(this.$util.isNull(value)) return;
            let check = /^[0-9]{0,11}$/g;
            if(check.test(value)){
                return this.info.recjoinpoint = value;
            }else{ 
                return this.info.recjoinpoint = oldValue;
            }
        },
        'info.reccfmpoint' : function(value, oldValue){
            if(this.$util.isNull(value)) return;
            let check = /^[0-9]{0,11}$/g;
            if(check.test(value)){
                return this.info.reccfmpoint = value;
            }else{ 
                return this.info.reccfmpoint = oldValue;
            }
        },
        'info.reclimitcnt' : function(value, oldValue){
            if(this.$util.isNull(value)) return;
            let check = /^[0-9]{0,11}$/g;
            if(check.test(value)){
                return this.info.reclimitcnt = value;
            }else{ 
                return this.info.reclimitcnt = oldValue;
            }
        }
    }
}
</script>

<style>

</style>