<template>
    <!-- 추가정보 일괄변경 팝업 -->
    <div id="modal-wrap" class="modal" style="display: block;">
        <div class="modal-content" style="width: 1400px;">
            <div class="pop-header">
                <h2>추가정보 일괄변경</h2>
                <button type="button" class="pop-close" @click="$emit('closePopup');"></button>
            </div>
            <div class="pop-body">
                <div class="blue-box mg0">총 {{ goodsnoList.length }}개 상품의 추가정보를 일괄 변경합니다.</div>
                <div class="clearfix mt10">
                    <div class="bar-title fl">추가정보
                        <span class="txt-orange ml10"><i class="icon-alert"></i>수정을 원하는 항목을 체크하신 후 일괄변경 하시기 바랍니다.</span>
                    </div>
                </div>
                <div class="form-area mt10" style="display: block;">
                    <table cellpadding="0" cellspacing="0" class="gray-tb">
                        <colgroup>
                            <col width="170px">
                            <col width="">
                        </colgroup>
                        <tbody>
                            <tr>
                                <th><input type="checkbox" id="isepifchange" v-model="chkObject.isepif.ischecked" true-value="T" false-value="F"><label for="isepifchange">EP연동여부</label></th>
                                <td>
                                    <div class="radio_wrap wide">
                                        <input type="radio" name="isepif" id="isepifT" v-model="info.isepif" value="T"/>
                                        <label for="isepifT">연동</label>
                                        <input type="radio" name="isepif" id="isepifF" v-model="info.isepif" value="F"/>
                                        <label for="isepifF">미연동</label>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th><input type="checkbox" id="ischkaswarmonth" v-model="chkObject.aswarmonth.ischecked" true-value="T" false-value="F"><label for="ischkaswarmonth">A/S 보증기간</label></th>
                                <td>
                                    <input type="text" style="width: 70px;" v-model="info.aswarmonth" maxlength="3">
                                    <span class="ml3">개월</span>
                                </td>
                            </tr>
                            <tr>
                                <th><input type="checkbox" id="ischkasnotice" v-model="chkObject.asnotice.ischecked" true-value="T" false-value="F"><label for="ischkasnotice">A/S 안내문구</label></th>
                                <td><textarea placeholder="A/S 안내문구를 입력하세요." v-model="info.asnotice" maxlength="500"></textarea></td>
                            </tr>
                            <tr>
                                <th><input type="checkbox" id="ischkmumembertype" v-model="chkObject.mumembertype.ischecked" true-value="T" false-value="F"><label for="ischkmumembertype">회원유형 별 노출여부</label></th>
                                <td>
                                    <div class="check-wrap">
                                        <input type="checkbox" id="chkAllMember" v-model="info.isallmember" true-value="T" false-value="F" @change="checkAllMembertype">
                                        <label for="chkAllMember">전체</label>
                                    </div>
                                    <div class="check-wrap" v-for="item in commonCode.dadamembertype" :key="item.cmcode">
                                        <input type="checkbox" :id="'mumembertypeD_'+item.cmcode" v-model="info.mumembertypeArr" :true-value="[]" :value="item.cmcode">
                                        <label :for="'mumembertypeD_'+item.cmcode">{{ item.codename }}</label>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th><input type="checkbox" id="ischkopenreview" v-model="chkObject.isopenreview.ischecked" true-value="T" false-value="F"><label for="ischkopenreview">리뷰 공개여부</label></th>
                                <td>
                                    <div class="radio_wrap wide">
                                        <input type="radio" name="isopenreviewD" id="isopenreviewDT" v-model="info.isopenreview" value="T"/>
                                        <label for="isopenreviewDT">공개</label>
                                        <input type="radio" name="isopenreviewD" id="isopenreviewDF" v-model="info.isopenreview" value="F"/>
                                        <label for="isopenreviewDF">비공개</label>
                                    </div>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="btn-group">
                    <button type="button" class="btn big blue" @click="openConfirmPopup">일괄변경</button>
                    <button type="button" class="btn big darkgray" @click="$emit('closePopup');">닫기</button>
                </div>
            </div>
        </div>
    </div>
    <!-- /추가정보 일괄변경 팝업 -->
</template>

<script>
import GoodsChangeConfirmPopup from '@views.admin/goods/popup/GoodsChangeConfirmPopup.vue';

export default {
    name: 'admin.goods.manage.goodsChangeEtcInfo',
    props : ['checkedList', 'ckey'],
    mounted() {
        // 초기화
        this.onInit();
    },
    data() {
        return {
            goodsnoList: [],    // 상품번호 목록
            chkObject: {        // 체크항목 목록
                isepif: { key: 'isepif', name:'EP연동여부', ischecked:'F' },
                aswarmonth: { key: 'aswarmonth', name:'AS보증기간', ischecked:'F' },
                asnotice: { key: 'asnotice', name:'AS안내문구', ischecked:'F' },
                mumembertype: { key: 'mumembertype', name:'회원유형 별 노출여부', ischecked:'F' },
                isopenreview: { key: 'isopenreview', name:'리뷰 공개여부', ischecked:'F' }
            },
            commonCode: {
                dadamembertype: []     // 다다픽회원유형
            },
            info: {
                aswarmonth: '',         // as보증기간_월
                asnotice: '',           // as안내문구
                isallmember: '',        // 회원유형전체여부
                mumembertype: '',       // 다중대상회원유형
                mumembertypeArr: [],    // 다중대상회원유형Array
                isopenreview: ''        // 리뷰공개여부
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
            let params = {
                ckey: this.ckey,
                isepifchange: this.chkObject.isepif.ischecked,
                isaswarmonthchange: this.chkObject.aswarmonth.ischecked,
                isasnoticechange: this.chkObject.asnotice.ischecked,
                ismumembertypehange: this.chkObject.mumembertype.ischecked,
                isopenreviewchange: this.chkObject.isopenreview.ischecked,
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
            // EP연동여부
            if (checkResult && this.chkObject.isepif.ischecked === 'T') {
                // 필수체크
                if (this.$util.isNull(this.info.isepif)) {
                    alert('EP연동여부를 선택해주세요.');
                    checkResult = false;
                }
            }
            // as보증기간
            if (checkResult && this.chkObject.aswarmonth.ischecked === 'T') {
                // 필수체크
                if (this.$util.isNull(this.info.aswarmonth)) {
                    alert('AS보증기간을 입력해주세요.');
                    checkResult = false;
                }
            }
            // // 회원유형 별 노출여부
            // if (this.chkObject.mumembertype.ischecked === 'T') {
            //     // 필수체크
            //     if (this.$util.isNull(this.info.mumembertype)) {
            //         alert('회원유형 별 노출여부를 선택해주세요.');
            //         checkResult = false;
            //     }
            // }
            // 리뷰 공개여부
            if (checkResult && this.chkObject.isopenreview.ischecked === 'T') {
                // 필수체크
                if (this.$util.isNull(this.info.isopenreview)) {
                    alert('리뷰 공개여부를 선택해주세요.');
                    checkResult = false;
                }
            }
            return checkResult;
        },
        // 공통코드 세팅
        getCommonCodeList: function() {
            let cmclassArr = ['DADAMEMBERTYPE'];
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
        // 판매대상-유형별 전체체크
        checkAllMembertype: function() {
            let isAllCheck = this.info.isallmember;
            this.info.mumembertypeArr = [];
            if (isAllCheck == 'T') {
                for(let type of this.commonCode.dadamembertype){
                    this.info.mumembertypeArr.push(type.cmcode);
                }
            }
        },
    },
    watch: {
        // 추가정보-회원유형별
        'info.mumembertypeArr': function(value) {
            if (value.length < this.commonCode.dadamembertype.length) {
                this.info.isallmember = 'F';
            } else {
                this.info.isallmember = 'T';
            }
            this.info.mumembertype = this.info.mumembertypeArr.join();
        },
        // 숫자만 입력
        'info.aswarmonth': function(value) {
            value = value+'';
            if (this.$util.isNull(value)) return;
            return this.info.aswarmonth = value.replace(/(^0|[^0-9])/gi, '');
        },
    }
}
</script>
