<template>
    <!-- 판매정보 일괄변경 팝업 -->
    <div id="modal-wrap" class="modal" style="display: block;">
        <div class="modal-content" style="width: 1400px;">
            <div class="pop-header">
                <h2>판매정보 일괄변경</h2>
                <button type="button" class="pop-close" @click="$emit('closePopup');"></button>
            </div>
            <div class="pop-body">
                <div class="blue-box mg0">총 {{ goodsnoList.length }}개 상품의 판매정보를 일괄 변경합니다.</div>
                <div class="clearfix mt10">
                    <div class="bar-title fl">판매정보
                        <span class="txt-orange ml10"><i class="icon-alert"></i>수정을 원하는 항목을 체크하신 후 일괄변경 하시기 바랍니다.</span>
                    </div>
                </div>
                <div class="boxing mt10 dt-wide">
                    <div class="form-area" style="display: block;">
                        <dl>
                            <dt><input type="checkbox" id="ischktaxfree" v-model="chkObject.istaxfree.ischecked" true-value="T" false-value="F"><label for="ischktaxfree">과세여부</label></dt>
                            <dd>
                                <div class="radio_wrap wide">
                                    <input type="radio" name="istaxfreeD" id="istaxfreeDF" value="F" v-model="info.istaxfree"/>
                                    <label for="istaxfreeDF">과세</label>
                                    <input type="radio" name="istaxfreeD" id="istaxfreeDT" value="T" v-model="info.istaxfree"/>
                                    <label for="istaxfreeDT">면세</label>
                                </div>
                            </dd>
                        </dl>
                        <dl>
                            <dt><input type="checkbox" id="ischkgoodsselltype" v-model="chkObject.goodsselltype.ischecked" true-value="T" false-value="F"><label for="ischkgoodsselltype">판매상태</label></dt>
                            <dd>
                                <div class="radio_wrap">
                                    <div v-for="item in commonCode.goodsselltype" :key="item.cmcode">
                                        <input type="radio" name="goodsselltypeD" :id="'goodsselltypeD_'+item.cmcode" :value="item.cmcode" v-model="info.goodsselltype"/>
                                        <label :for="'goodsselltypeD_'+item.cmcode">{{ item.codename }}</label>
                                    </div>
                                </div>
                            </dd>
                        </dl>
                        <dl>
                            <dt><input type="checkbox" id="ischkdisperiod" v-model="chkObject.disperiod.ischecked" true-value="T" false-value="F"><label for="ischkdisperiod">전시기간</label></dt>
                            <dd>
                                <CommonDatePickerFromTo
                                    :fromYYYYMMDD="info.disstdate"
                                    :fromHH="info.dissthour"
                                    :fromMM="info.disstmin"
                                    :toYYYYMMDD="info.diseddate"
                                    :toHH="info.disedhour"
                                    :toMM="info.disedmin"
                                    :useFrom="true"
                                    :useTo="true"
                                    @getDate="pickerChangeEvent"
                                />
                                <select v-model="info.disperiod">
                                    <option id="period" value="">선택</option>
                                    <option id="period_day_1" value="day_1">1일</option>
                                    <option id="period_day_3" value="day_3">3일</option>
                                    <option id="period_day_5" value="day_5">5일</option>
                                    <option id="period_day_7" value="day_7">7일</option>
                                    <option id="period_day_15" value="day_15">15일</option>
                                    <option id="period_month_1" value="month_1">1개월</option>
                                    <option id="period_month_3" value="month_3">3개월</option>
                                    <option id="period_month_6" value="month_6">6개월</option>
                                    <option id="period_all_0" value="all_0">상시</option>
                                </select>
                            </dd>
                        </dl>
                        <dl>
                            <dt><input type="checkbox" id="ischkprice" v-model="chkObject.price.ischecked" true-value="T" false-value="F"><label for="ischkprice">판매가격(대표상품)</label></dt>
                            <dd>
                                <span>정상가</span>
                                <input type="text" style="width: 120px;" v-model="info.marketprice" maxlength="11"/>
                                <span>원</span>
                                <span class="left-bar">판매가</span>
                                <input type="text" style="width: 120px;" v-model="info.price" maxlength="11"/>
                                <span>원</span>
                            </dd>
                        </dl>
                        <dl>
                            <dt><input type="checkbox" id="ischkordcnt" v-model="chkObject.ordcnt.ischecked" true-value="T" false-value="F"><label for="ischkordcnt">최소~최대 주문수량</label></dt>
                            <dd>
                                <input type="text" style="width: 80px;" v-model="info.minordcnt" maxlength="11"/>
                                <span>개 부터 ~</span>
                                <input type="text" style="width: 80px;" v-model="info.maxordcnt" maxlength="11"/>
                                <span>개 이내</span>
                            </dd>
                        </dl>
                        <dl>
                            <dt><input type="checkbox" id="ischkdaymaxordcnt" v-model="chkObject.daymaxordcnt.ischecked" true-value="T" false-value="F"><label for="ischkdaymaxordcnt">1일 최대 주문수량</label></dt>
                            <dd>
                                <input type="text" style="width: 80px;" v-model="info.daymaxordcnt" maxlength="11"/>
                                <span>개 까지 구매가능</span>
                            </dd>
                        </dl>
                        <dl>
                            <dt><input type="checkbox" id="ischkpermaxordcnt" v-model="chkObject.permaxordcnt.ischecked" true-value="T" false-value="F"><label for="ischkpermaxordcnt">1인당 최대 구매수량</label></dt>
                            <dd>
                            <input type="text" style="width: 80px;" v-model="info.permaxordcnt" maxlength="11"/>
                                <span>개 까지 구매가능 (기간없이)</span>
                            </dd>
                        </dl>
                        <dl>
                            <dt><input type="checkbox" id="ischkdisplay" v-model="chkObject.isdisplay.ischecked" true-value="T" false-value="F"><label for="ischkdisplay">전시 여부</label></dt>
                            <dd>
                                <div class="radio_wrap wide">
                                    <input type="radio" name="isdisplayD" id="isdisplayDT" value="T" v-model="info.isdisplay"/>
                                    <label for="isdisplayDT">전시</label>
                                    <input type="radio" name="isdisplayD" id="isdisplayDF" value="F" v-model="info.isdisplay"/>
                                    <label for="isdisplayDF">미 전시</label>
                                </div>
                            </dd>
                        </dl>
                        <dl>
                            <dt><input type="checkbox" id="ischkfrstsale" v-model="chkObject.isfrstsale.ischecked" true-value="T" false-value="F"><label for="ischkfrstsale">첫 구매시 할인혜택여부</label></dt>
                            <dd>
                                <div class="radio_wrap wide">
                                    <input type="radio" name="isfrstsaleD" id="isfrstsaleDT" value="T" v-model="info.isfrstsale"/>
                                    <label for="isfrstsaleDT">적용</label>
                                    <input type="radio" name="isfrstsaleD" id="isfrstsaleDF" value="F" v-model="info.isfrstsale"/>
                                    <label for="isfrstsaleDF">미 적용</label>
                                </div>
                            </dd>
                        </dl>
                        <dl>
                            <dt><input type="checkbox" id="ischkcncappr" v-model="chkObject.iscncappr.ischecked" true-value="T" false-value="F"><label for="ischkcncappr">취소시 판매자 승인여부</label></dt>
                            <dd>
                                <div class="radio_wrap wide">
                                    <input type="radio" name="iscncapprD" id="iscncapprDF" value="F" v-model="info.iscncappr"/>
                                    <label for="iscncapprDF">필요없음</label>
                                    <input type="radio" name="iscncapprD" id="iscncapprDT" value="T" v-model="info.iscncappr"/>
                                    <label for="iscncapprDT">승인필요</label>
                                </div>
                            </dd>
                        </dl>
                        <dl>
                            <dt><input type="checkbox" id="ischkgoodsdivtype" v-model="chkObject.goodsdivtype.ischecked" true-value="T" false-value="F"><label for="ischkgoodsdivtype">상품상태</label></dt>
                            <dd>
                                <div class="radio_wrap">
                                    <div v-for="item in commonCode.goodsdivtype" :key="item.cmcode">
                                        <input type="radio" name="goodsdivtypeD" :id="'goodsdivtypeD_'+item.cmcode" :value="item.cmcode" v-model="info.goodsdivtype"/>
                                        <label :for="'goodsdivtypeD_'+item.cmcode">{{ item.codename }}</label>
                                    </div>
                                </div>
                            </dd>
                        </dl>
                    </div>
                </div>
                <div class="btn-group">
                    <button type="button" class="btn big blue" @click="openConfirmPopup">일괄변경</button>
                    <button type="button" class="btn big darkgray" @click="$emit('closePopup');">닫기</button>
                </div>
            </div>
        </div>
    </div>
    <!-- /판매정보 일괄변경 팝업 -->
</template>

<script>
import CommonDatePickerFromTo from '@views.admin/common/CommonDatePickerFromTo.vue';
import GoodsChangeConfirmPopup from '@views.admin/goods/popup/GoodsChangeConfirmPopup.vue';

export default {
    name: 'admin.goods.manage.goodsChangeSellInfo',
    props : ['checkedList', 'ckey'],
    components: {
        CommonDatePickerFromTo
    },
    mounted() {
        // 초기화
        this.onInit();
    },
    data() {
        return {
            goodsnoList: [],    // 상품번호 목록
            chkObject: {        // 체크항목 목록
                istaxfree: { key: 'istaxfree', name:'과세여부', ischecked:'F' },
                goodsselltype: { key: 'goodsselltype', name:'판매상태', ischecked:'F' },
                disperiod: { key: 'disperiod', name:'전시기간', ischecked:'F' },
                price: { key: 'price', name:'판매가격', ischecked:'F' },
                ordcnt: { key: 'ordcnt', name:'최소~최대 주문수량', ischecked:'F' },
                daymaxordcnt: { key: 'daymaxordcnt', name:'1일 최대 주문수량', ischecked:'F' },
                permaxordcnt: { key: 'permaxordcnt', name:'1인당 최대 구매수량', ischecked:'F' },
                isdisplay: { key: 'isdisplay', name:'전시여부', ischecked:'F' },
                isfrstsale: { key: 'isfrstsale', name:'첫 구매시 할인혜택여부', ischecked:'F' },
                iscncappr: { key: 'iscncappr', name:'취소시 판매자 승인여부', ischecked:'F' },
                goodsdivtype: { key: 'goodsdivtype', name:'상품상태', ischecked:'F' }
            },
            commonCode: {
                goodsselltype: [],  // 상품판매상태
                goodsdivtype: []    // 상품구분상태
            },
            info: {
                istaxfree: '',      //면세여부
                goodsselltype: '',  //상품판매상태 (defualt: 판매대기)
                disstday: '',       //전시시작일
                disstdate: '',      //전시시작일자
                disedday: '',       //전시종료일
                diseddate: '',      //전시종료일자
                dissthour: '',      //전시시작시간
                disstmin: '',       //전시시작분
                disedhour: '',      //전시종료시간
                disedmin: '',       //전시종료분
                disperiod: '',      //전시기간
                marketprice: '',    //정상가
                price: '',          //판매가
                minordcnt: '',      //최소주문수량
                maxordcnt: '',      //최대주문수량
                daymaxordcnt: '',   //일최대주문수량
                permaxordcnt: '',   //일인당최대구매수량
                isdisplay: '',      //전시여부
                isfrstsale: '',     //첫구매할일적용여부
                iscncappr: '',      //취소시 판매자 승인여부
            }
        }
    },
    methods: {
        // 화면초기화
        onInit: function() {
            // 넘어온 파라미터 세팅
            this.goodsnoList = this.checkedList;
            // 공통코드 조회
            this.getCommonCodeList();
            // 전시기간 세팅 (defualt: 상시)
            this.info.disperiod = 'all_0';
        },
        // 일괄변경 확인팝업 오픈
        openConfirmPopup: function() {
            // 파라미터 세팅
            let checkItemList = [];
            for (const [key, value] of Object.entries(this.chkObject)) {
                if (this.chkObject[key].ischecked === 'T') {
                    checkItemList.push(value);
                }
            }
            // 선택한 항목 체크
            if (checkItemList.length == 0) {
                alert("변경할 항목을 선택해주세요.");
                return;
            }

            // 일괄변경 확인팝업 오픈
            if (this.checkValidation()) {
                this.$eventBus.$emit('modalShow', GoodsChangeConfirmPopup, { checkItemList: checkItemList},
                    (result) => {
                        if (result.isconfirm === 'T') {
                            this.changeAll();
                        }
                    }
                );
            }
        },
        // 일괄변경
        changeAll: function() {
            // 변경파라미터 세팅
            let params = {
                ckey: this.ckey,
                istaxfreechange: this.chkObject.istaxfree.ischecked,
                isgoodsselltypechange: this.chkObject.goodsselltype.ischecked,
                isdisperiodchange: this.chkObject.disperiod.ischecked,
                ispricechange: this.chkObject.price.ischecked,
                isordcntchange: this.chkObject.ordcnt.ischecked,
                isdaymaxordcntchange: this.chkObject.daymaxordcnt.ischecked,
                ispermaxordcntchange: this.chkObject.permaxordcnt.ischecked,
                isdisplaychange: this.chkObject.isdisplay.ischecked,
                isfrstsalechange: this.chkObject.isfrstsale.ischecked,
                iscncapprchange: this.chkObject.iscncappr.ischecked,
                isgoodsdivtypechange: this.chkObject.goodsdivtype.ischecked,
                goodsnoList: this.goodsnoList
            }
            params = Object.assign({}, params, this.info);
            this.$http.post("/admin/goods/manage/update", params)
                .then(result => {
                    if (result.statusCode === 200) {
                        alert("일괄변경이 완료되었습니다.");
                        this.$emit('closePopup', 'T');
                    }
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 입력 validation 체크
        checkValidation: function() {
            let checkResult = true;

            // 과세여부
            if (this.chkObject.istaxfree.ischecked === 'T') {
                // 필수체크
                if (this.$util.isNull(this.info.istaxfree)) {
                    alert('과세여부를 선택해주세요.');
                    checkResult = false;
                }
            }
            // 판매상태
            if (checkResult && this.chkObject.goodsselltype.ischecked === 'T') {
                // 필수체크
                if (this.$util.isNull(this.info.goodsselltype)) {
                    alert('판매상태를 선택해주세요.');
                    checkResult = false;
                }
            }
            // // 전시기간
            // if (checkResult && this.chkObject.disperiod.ischecked === 'T') {
            //     // 필수체크
            //     if (this.$util.isNull(this.info.disstdate)) {
            //         alert('전시시작일자를 입력해주세요.');
            //         checkResult = false;
            //     }
            //     if (checkResult && this.$util.isNull(this.info.diseddate)) {
            //         alert('전시종료일자를 입력해주세요.');
            //         checkResult = false;
            //     }
            //     if (checkResult) {
            //         let valid = [
            //             {field: 'info.disstmin', type: 'I', name:'전시시작분', required: true},
            //             {field: 'info.diseddate', type: 'I', name:'전시종료일자', required: true},
            //             {field: 'info.disedhour', type: 'I', name:'전시종료시간', required: true},
            //             {field: 'info.disedmin', type: 'I', name:'전시종료분', required: true}
            //         ];
            //         let msg = this.$util.validMsg(this.$data, valid);
            //         if(!this.$util.isNull(msg)){
            //             alert(msg);
            //             checkResult = false;
            //         }
            //     }
            // }
            // 판매가격
            if (checkResult && this.chkObject.price.ischecked === 'T') {
                // 필수체크
                let valid = [
                    {field: 'info.marketprice', type: 'I', name:'정상가', required: true},
                    {field: 'info.price', type: 'I', name:'판매가', required: true}
                ];
                let msg = this.$util.validMsg(this.$data, valid);
                if(!this.$util.isNull(msg)){
                    alert(msg);
                    checkResult = false;
                }
            }
            // 최소~최대 주문수량
            if (checkResult && this.chkObject.ordcnt.ischecked === 'T') {
                // 필수체크
                let valid = [
                    {field: 'info.minordcnt', type: 'I', name:'최소 주문수량', required: true},
                    {field: 'info.maxordcnt', type: 'I', name:'최대 주문수량', required: true}
                ];
                let msg = this.$util.validMsg(this.$data, valid);
                if(!this.$util.isNull(msg)){
                    alert(msg);
                    checkResult = false;
                }
            }
            // 1일 최대 주문수량
            if (checkResult && this.chkObject.daymaxordcnt.ischecked === 'T') {
                // 필수체크
                if (this.$util.isNull(this.info.daymaxordcnt)) {
                    alert('1일 최대 주문수량을 입력해주세요.');
                    checkResult = false;
                }
            }
            // // 전시여부
            // if (checkResult && this.chkObject.isdisplay.ischecked === 'T') {
            //     // 필수체크
            //     if (this.$util.isNull(this.info.isdisplay)) {
            //         alert('전시여부를 선택해주세요.');
            //         checkResult = false;
            //     }
            // }
            // // 첫 구매시 할인혜택여부
            // if (checkResult && this.chkObject.isfrstsale.ischecked === 'T') {
            //     // 필수체크
            //     if (this.$util.isNull(this.info.isfrstsale)) {
            //         alert('첫 구매시 할인혜택여부를 선택해주세요.');
            //         checkResult = false;
            //     }
            // }
            // // 취소승인필요여부
            // if (checkResult && this.chkObject.iscncappr.ischecked === 'T') {
            //     // 필수체크
            //     if (this.$util.isNull(this.info.iscncappr)) {
            //         alert('취소승인필요여부를 선택해주세요.');
            //         checkResult = false;
            //     }
            // }
            // // 상품상태
            // if (checkResult && this.chkObject.goodsdivtype.ischecked === 'T') {
            //     // 필수체크
            //     if (this.$util.isNull(this.info.goodsdivtype)) {
            //         alert('상품상태를 선택해주세요.');
            //         checkResult = false;
            //     }
            // }

            return checkResult;
        },
        // 공통코드 세팅
        getCommonCodeList: function() {
            let cmclassArr = ['GOODSSELLTYPE', 'GOODSDIVTYPE'];
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
        // datepicker callback
        pickerChangeEvent(data) {
            this.info.disstdate = data.fromYYYYMMDD;
            this.info.dissthour = data.fromHH;
            this.info.disstmin = data.fromMM;
            this.info.disstday = data.fromDate12;

            this.info.diseddate = data.toYYYYMMDD;
            this.info.disedhour = data.toHH;
            this.info.disedmin = data.toMM;
            this.info.disedday = data.toDate12;
        },
    },
    watch: {
        // 전시기간
        'info.disperiod': function (value) {
            let params = value.split('_');
            let type = params[0];
            let addValue = parseInt(params[1]);

            // 상시
            if (type == 'all') {
                this.info.disstdate = this.$util.getDate('-');
                this.info.diseddate = this.$util.getFormatDate('20991231', '-');
            } else if (type == 'day') {
                this.info.diseddate = this.$util.getAddDate(this.info.disstdate, addValue, '-');
            } else if (type == 'month') {
                this.info.diseddate = this.$util.getAddMonth(this.info.disstdate, addValue, '-');
            }
            this.info.dissthour = '00';
            this.info.disedhour = '11';
            this.info.disstmin = '00';
            this.info.disedmin = '59';
        },
        // 숫자만입력
        'info.marketprice': function(value) {
            value = value+'';
            if (this.$util.isNull(value)) return;
            return this.info.marketprice = value.replace(/(^0|[^0-9])/gi, '');
        },
        'info.price': function(value) {
            value = value+'';
            if (this.$util.isNull(value)) return;
            return this.info.price = value.replace(/(^0|[^0-9])/gi, '');
        },
        'info.minordcnt': function(value) {
            value = value+'';
            if (this.$util.isNull(value)) return;
            return this.info.minordcnt = value.replace(/(^0|[^0-9])/gi, '');
        },
        'info.maxordcnt': function(value) {
            value = value+'';
            if (this.$util.isNull(value)) return;
            return this.info.maxordcnt = value.replace(/(^0|[^0-9])/gi, '');
        },
        'info.daymaxordcnt': function(value) {
            value = value+'';
            if (this.$util.isNull(value)) return;
            return this.info.daymaxordcnt = value.replace(/(^0|[^0-9])/gi, '');
        },
        'info.permaxordcnt': function(value) {
            value = value+'';
            if (this.$util.isNull(value)) return;
            return this.info.permaxordcnt = value.replace(/(^0|[^0-9])/gi, '');
        },
        // 'info.disstmin': function(value) {
        //     value = value+'';
        //     if (this.$util.isNull(value)) return;
        //     return this.info.disstmin = value.replace(/(^[6-9]|[^0-9])/gi, '');
        // },
        // 'info.disedmin': function(value) {
        //     value = value+'';
        //     if (this.$util.isNull(value)) return;
        //     return this.info.disedmin = value.replace(/(^[6-9]|[^0-9])/gi, '');
        // }
    }
}
</script>