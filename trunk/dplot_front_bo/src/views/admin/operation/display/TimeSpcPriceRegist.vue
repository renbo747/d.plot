<template>
    <!-- 타임특가 등록 팝업 -->
    <div id="modal-wrap" class="modal" style="display: block;">
        <div class="modal-content" style="width: 1400px;">
            <div class="pop-header">
                <h2>타임특가 등록</h2>
                <button type="button" class="pop-close" @click="onClose"></button>
            </div>
            <div class="pop-body">
                <div class="bar-title">기본정보</div>
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
                <div class="bar-title">조건설정(공통)</div>
                <div class="form-area">
                    <table cellpadding="0" cellspacing="0" class="gray-tb">
                        <colgroup>
                            <col width="170px">
                            <col width="">
                        </colgroup>
                        <tbody>
                            <tr>
                                <th>노출기간<i class="essential"></i></th>
                                <CommonDatePickerFromTo
                                  :fromYYYYMMDD="info.expStartDate"
                                  :fromHH="info.expStartHour"
                                  :fromMM="info.expStartMi"
                                  :toYYYYMMDD="info.expEndDate"
                                  :toHH="info.expEndHour"
                                  :toMM="info.expEndMi"
                                  :useFrom="true"
                                  :useTo="true"
                                  @getDate="pickerEventBusExp"
                                />
                            </tr>
                            <tr>
                                <th>판매기간<i class="essential"></i></th>
                                <CommonDatePickerFromTo
                                  :fromYYYYMMDD="info.saleStartDate"
                                  :fromHH="info.saleStartHour"
                                  :fromMM="info.saleStartMi"
                                  :toYYYYMMDD="info.saleEndDate"
                                  :toHH="info.saleEndHour"
                                  :toMM="info.saleEndMi"
                                  :useFrom="true"
                                  :useTo="true"
                                  @getDate="pickerEventBusSale"
                                />
                            </tr>
                            <tr>
                                <th>대상 상품<i class="essential"></i></th>
                                <td>
                                    <div class="caption-group clearfix">
                                        <div class="total-group fl">
                                            <span class="total">적용대상 상품</span>
                                        </div>
                                        <div class="btn-group fr">
                                            <button type="button" class="btn blue-line" v-show="info.goodsno === ''" @click="openGoodsAdditionPopup">상품추가</button>
                                            <button type="button" class="btn red-line" v-show="info.goodsno !== ''" @click="removeGoods">삭제</button>
                                        </div>
                                    </div>
                                    <div class="scroll-y" style="width: 100%; max-height: 350px; margin-bottom: 0;">
                                        <table cellpadding="0" cellspacing="0" class="data-tb align-c">
                                            <colgroup>
                                                <col width="3%" /><!-- checkbox -->
                                                <col width="4%" /><!-- No -->
                                                <col width="6%" /><!-- 판매구분 -->
                                                <col width="10%" /><!-- 파트너사명 -->
                                                <col width="8%" /><!-- 상품코드 -->
                                                <col width="62px" /><!-- 이미지 -->
                                                <col width="" /><!-- 상품명 -->
                                                <col width="7%" /><!-- 판매가 -->
                                            </colgroup>
                                            <thead>
                                                <tr>
                                                    <th><input type="checkbox" id="chkall" /></th>
                                                    <th>No</th>
                                                    <th>판매구분</th>
                                                    <th>파트너사명</th>
                                                    <th>상품코드</th>
                                                    <th colspan="2">상품명</th>
                                                    <th>판매가</th>
                                                </tr>
                                            </thead>
                                            <tbody v-show="info.goodsno !== ''">
                                                <tr>
                                                    <td><input type="checkbox" id="chk01" /></td>
                                                    <td>1</td>
                                                    <td>{{info.goodsinfo.ispbgoodsname}}</td>
                                                    <td>{{info.goodsinfo.dealername}}</td>
                                                    <td>{{info.goodsinfo.goodscode}}</td>
                                                    <td>
                                                        <div class="img-thumb size60" :class="{'no-image': $util.isNull(info.goodsinfo.fullpath)}">
                                                            <img :src="info.goodsinfo.fullpath" v-if="!$util.isNull(info.goodsinfo.fullpath)">
                                                        </div>
                                                    </td>
                                                    <td class="left no-left">
                                                        <span class="small-txt">{{ info.goodsinfo.fullcategoryname }}</span>
                                                        <p class="mg0">{{ info.goodsinfo.goodsname }}</p>
                                                    </td>
                                                    <td class="right">{{ info.goodsinfo.price }}</td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th>할인율<i class="essential"></i></th>
                                <td>
                                    <div class="radio_wrap wide dpib">
                                        <input type="radio" v-model="info.isflatrate" name="group05" id="group51" value="T"/><label for="group51">정액</label>
                                        <input type="radio" v-model="info.isflatrate" name="group05" id="group52" value="F"/><label for="group52">정률</label>
                                    </div>
                                    <div :class="info.isflatrate === 'T' ? 'dpib' : 'dpn'"><input type="text" v-model="info.discrateT" class="right" style="width: 80px;" /><span class="ml3">원</span></div><!-- dpib <-> dpn으로 노출여부변경 -->
                                    <div :class="info.isflatrate === 'F' ? 'dpib' : 'dpn'"><input type="text" v-model="info.discrateF" class="right" style="width: 80px;" @blur="checkRate" /><span class="ml3">%</span></div><!-- dpib <-> dpn으로 노출여부변경 -->
                                </td>
                            </tr>
                            <tr>
                                <th>분담비율<i class="essential"></i></th>
                                <td>
                                    <span>파트너사</span><input type="text" v-model="info.prtpercent" class="ml3 right" style="width: 50px;" /><span class="ml3">%</span>
                                    <span>(D.PLOT</span><input type="text" class="ml3 right" style="width: 50px;" :value="100 - info.prtpercent" readonly /><span class="ml3">%)</span>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="btn-group">
                    <button type="button" class="btn big blue" @click="onSave">저장</button>
                    <button type="button" class="btn big darkgray" @click="onClose">취소</button>
                </div>
            </div>
        </div>
    </div>
    <!-- /타임특가 등록 팝업 -->
</template>

<script>
import CommonAddGoodsPopup from '@views.admin/common/popup/CommonAddGoodsPopup.vue';
import CommonDatePickerFromTo from "@views.admin/common/CommonDatePickerFromTo";
export default {
    name: 'admin.operation.display.timespcregist',
    components: {
        CommonDatePickerFromTo
    },
    data() {
        return {
            info: {
                istrash: 'F',    // 사용여부
                expStartDate: '',
                expStartHour: '',
                expStartMi: '',
                expEndDate: '',
                expEndHour: '',
                expEndMi: '',
                saleStartDate: '',
                saleStartHour: '',
                saleStartMi: '',
                saleEndDate: '',
                saleEndHour: '',
                saleEndMi: '',
                goodsno: '',
                goodsinfo: {},
                isflatrate: '',
                discrateT: '',
                discrateF: '',
                prtpercent: 0,
                reguserid: '',
            },
            isRead: false,
            isWrite: false,
        }
    },
    mounted() {
        this.$http.post('/admin/common/pageAuth/check', {url : this.$options.name}).then(result => {

            this.isRead = (result.data.isread === 'T');
            this.isWrite = (result.data.iswrite === 'T');

            if(this.isRead){
                let userInfo = this.$storage.getLocalStorage("ADMIN_USER");
                this.info.reguserid = userInfo.id;
            }
                else {
                alert('페이지 접근 권한이 없습니다.');
                this.info = this.$options.data().info
                this.$emit('closeRegist');
            }

            if(!this.isWrite){
                let buttons = this.$el.getElementsByTagName('button');

                for(let button of buttons){
                if(button.className !== 'pop-close') {
                    button.style.display = 'none';
                    button.disabled = true;
                }
                }
            }
        }).catch(error => {
            this.$util.debug(error);
        })
    },
    methods: {
        // datepicker callback
        pickerEventBusExp(data) {
          this.info.expStartDate = data.fromYYYYMMDD;
          this.info.expStartHour = data.fromHH;
          this.info.expStartMi = data.fromMM;
          this.info.expsttime = data.fromDate12;

          this.info.expEndDate = data.toYYYYMMDD;
          this.info.expEndHour = data.toHH;
          this.info.expEndMi = data.toMM;
          this.info.expedtime = data.toDate12;
        },
        // datepicker callback
        pickerEventBusSale(data) {
          this.info.saleStartDate = data.fromYYYYMMDD;
          this.info.saleStartHour = data.fromHH;
          this.info.saleStartMi = data.fromMM;
          this.info.salesttime = data.fromDate12;

          this.info.saleEndDate = data.toYYYYMMDD;
          this.info.saleEndHour = data.toHH;
          this.info.saleEndMi = data.toMM;
          this.info.saleedtime = data.toDate12;
        },
        onClose(){
            this.info = this.$options.data().info;
            this.$emit('closeRegist');
        },
        // 추가상품 팝업 오픈
        openGoodsAdditionPopup(index) {
            let param = {
                issingle: true,
            }
            this.$eventBus.$emit('modalShow', CommonAddGoodsPopup, param,
                (result) => {
                    let resultList = result.list;
                    this.info.goodsinfo = resultList[0];
                    this.info.goodsno = this.info.goodsinfo.goodsno;
                }
            );
        },
        removeGoods(){
            this.info.goodsno = '';
            this.info.goodsinfo = {};
        },
        onSave(){
            let params = this.info;
            if(this.checkValidataion()){
                if(confirm("저장하시겠습니까?")){
                    params.discrate = params.isflatrate === 'T' ? params.discrateT : params.discrateF;

                    this.$http.post('/admin/operation/display/time/save', params)
                    .then(result => {
                    if(result.statusCode === 200){
                        alert("저장이 완료되었습니다.");
                        this.onClose();
                    }else {
                        alert("저장에 실패했습니다.");
                    }
                })
                .catch(error => {
                    this.$util.debug(error);
                });
                }
            }
        },
        checkValidataion(){
            let check = true;

            let valid = [
                {field: 'info.expStartDate', type: 'I', name: '노출시작시간', required: true},
                {field: 'info.expStartHour', type: 'I', name: '노출시작시간', required: true},
                {field: 'info.expStartMi', type: 'I', name: '노출시작시간', required: true},
                {field: 'info.expEndDate', type: 'I', name: '노출종료시간', required: true},
                {field: 'info.expEndHour', type: 'I', name: '노출종료시간', required: true},
                {field: 'info.expEndMi', type: 'I', name: '노출종료시간', required: true},
                {field: 'info.saleStartDate', type: 'I', name: '판매시작시간', required: true},
                {field: 'info.saleStartHour', type: 'I', name: '판매시작시간', required: true},
                {field: 'info.saleStartMi', type: 'I', name: '판매시작시간', required: true},
                {field: 'info.saleEndDate', type: 'I', name: '판매종료시간', required: true},
                {field: 'info.saleEndHour', type: 'I', name: '판매종료시간', required: true},
                {field: 'info.saleEndMi', type: 'I', name: '판매종료시간', required: true},
                {field: 'info.goodsno', type: 'I', name: '대상상품', required: true},
                {field: 'info.isflatrate', type: 'S', name: '할인율', required: true},
                {field: 'info.prtpercent', type: 'I', name: '분담비율', required: true}
            ];

            let msg = '';

            msg = this.$util.validMsg(this.$data,valid);
            if(!this.$util.isNull(msg)){
                alert(msg);
                return false;
            }

            if(this.info.expsttime >= this.info.expedtime){
                alert("노출 종료일자를 시작일자 이후로 설정해주세요.");
                return false;
            }
            if(this.info.salesttime >= this.info.saleedtime){
                alert("판매 종료일자를 시작일자 이후로 설정해주세요.");
                return false;
            }

            if(this.info.expsttime > this.info.salesttime || this.info.expsttime > this.info.saleedtime || this.info.expedtime < this.info.salesttime || this.info.expedtime < this.info.saleedtime){
                alert("판매기간은 노출기간을 벗어날 수 없습니다.");
                return false;
            }

            return check;
        },
        checkRate(){
            if(this.info.discrateF.length === this.info.discrateF.indexOf('.') + 1){
                this.info.discrateF = this.info.discrateF.replace('.','');
            }
        }
    },
    watch: {
        'info.discrateT' : function(value, oldValue){
            if(this.$util.isNull(value)) return;
            let check = /^[0-9]{0,10}$/gi;
            if(check.test(value)){
                return this.info.discrateT = value;
            }else{
                return this.info.discrateT = oldValue;
            }
        },
        'info.discrateF' : function(value,oldValue){
            if(this.$util.isNull(value)) return;
            let check = /^(\d{0,2}(\.)?)|^(\d{0,2}(\.\d{1,2})?|100(\.00?)?)$/gi;
            if(check.test(value)){
                return this.info.discrateF = value;
            }else{
                return this.info.discrateF = oldValue;
            }
        },
        'info.prtpercent' : function(value, oldValue){
            if(this.$util.isNull(value)) return;
            let check = /^(\d{0,2}|^100)$/gi;
            if(check.test(value)){
                return this.info.prtpercent = value;
            }else{ 
                return this.info.prtpercent = oldValue;
            }
        }
    }
}
</script>

<style>

</style>