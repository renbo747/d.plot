<template>
    <!-- 재 적용 사은품 선택 팝업 -->
    <div id="modal-wrap" class="modal" style="display: block;">
        <div class="modal-content" style="width: 1000px;">
            <div class="pop-header">
                <h2>재 적용 사은품 선택</h2>
                <button type="button" class="pop-close" @click="$modal.hide('commonModal');"></button>
            </div>
            <div class="pop-body">
                <table cellpadding="0" cellspacing="0" class="data-tb align-c">
                    <colgroup>
                        <col width="40px" /><!-- checkbox -->
                        <col width="12%" /><!-- 사은품코드 -->
                        <col width="62px" /><!-- 사은품이미지 -->
                        <col width="25%" /><!-- 사은품명 -->
                        <col width="10%" /><!-- 적용수량 -->
                        <col width="" /><!-- 적용 프로모션 명 -->
                    </colgroup>
                    <thead>
                        <tr>
                            <th><input type="checkbox" id="chkall" v-model="isallchk" @change="checkAllList($event.target.checked)"/></th>
                            <th>사은품코드</th>
                            <th colspan="2">사은품명</th>
                            <th>적용수량</th>
                            <th>적용 프로모션 명</th>
                        </tr>
                    </thead>
                    <tbody v-if="reapplyGiftList.length > 0">
                        <tr v-for="item in reapplyGiftList" :key="item.promoidx+'_'+item.giftidx">
                            <td><input type="checkbox" :id="item.promoidx+'_'+item.giftidx" v-model="checkedList" :value="item" @change="checkList($event.target.checked)"/></td>
                            <td>{{ item.giftcode }}</td>
                            <td>
                                <div class="img-thumb size60" :class="{'no-image': $util.isNull(item.fullpath)}">
                                    <img :src="item.fullpath" v-if="!$util.isNull(item.fullpath)">
                                </div>
                            </td>
                            <td class="left no-left"><p class="mg0">{{ item.giftname }}</p></td>
                            <td>{{ $util.maskComma(item.rcvamt) }}</td>
                                <!-- <select class="short" v-model="item.rcvamt">
                                    <option v-for="i in item.rcvamt" :key="i" :value="i">{{ i }}</option>
                                </select> -->
                            <td>{{ item.promoname }}</td>
                        </tr>
                    </tbody>
                    <tbody v-else>
                        <tr><td colspan="6">조회 결과가 존재하지 않습니다.</td></tr>
                    </tbody>
                </table>
                <div class="btn-group">
                    <button type="button" class="btn big blue" @click="save">적용</button>
                    <button type="button" class="btn big darkgray" @click="$modal.hide('commonModal');">닫기</button>
                </div>
            </div>
        </div>
    </div>
    <!-- /재 적용 사은품 선택 팝업 -->
</template>

<script>
export default {
    name: 'AddReapplyGiftPopup',
    props: {
        modalComp: Object,
        params: Object,
        callbackFn: Function
    },
    components:{},
    data() {
        return {
            reapplyGiftList: [],
            validChkList: [],
            isallchk: false,
            checkedList: []
        }
    },
    mounted() {
        this.reapplyGiftList = this.params.reapplyGiftList;
        this.reapplyGiftList.forEach((item, index) => {
            if (index===0) {
                this.validChkList.push({promoidx: item.promoidx, giftselcnt: item.giftselcnt});
            } else {
                for (let i=0; i<this.validChkList.length; i++) {
                    if (this.validChkList[i].promoidx !== item.promoidx) {
                        this.validChkList.push({promoidx: item.promoidx, giftselcnt: item.giftselcnt});
                        break;
                    }
                }
            }
        });
    },
    methods: {
        // 목록 전체체크
        checkAllList: function(value) {
            this.checkedList = [];
            if (value) {
                this.reapplyGiftList.forEach(item => {
                    this.checkedList.push(item);
                });
            }
        },
        // 목록 개별체크
        checkList: function() {
            if (this.reapplyGiftList.length > this.checkedList.length){
                this.isallchk = false;
            } else {
                this.isallchk = true;
            }
        },
        // 적용
        save: function() {
            if (this.checkedList.length === 0) {
                alert('적용할 사은품을 선택해주세요.');
                return;
            }
            for (let i=0; i<this.checkedList.length; i++) {
                let promoidx = this.checkedList[i].promoidx;
                let selcnt = this.checkedList.filter(item => { return item.promoidx === promoidx; }).length;
                for (let j=0; j<this.validChkList.length; j++) {
                    let giftselcnt = this.validChkList[j].giftselcnt;
                    if (selcnt > giftselcnt) {
                        alert('\'' + this.checkedList[i].promoname + '\' 에서 최대 ' + giftselcnt + '개만 선택 가능합니다.');
                        return;
                    }
                }
            }
            // 팝업닫기 - 파라미터 전달
            this.closePopup(this.checkedList);
        },
        // 팝업닫기
        closePopup: function(targetList) {
            if( typeof(this.callbackFn) == 'function') {
                this.callbackFn({list: targetList});
            }
            this.$modal.hide('commonModal');
        }
    }
}
</script>