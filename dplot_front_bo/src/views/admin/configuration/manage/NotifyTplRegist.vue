<template>
    <!-- 상품정보고시 등록 팝업 -->
    <div id="modal-wrap" class="modal" style="display: block;">
        <div class="modal-content" style="width: 1200px;">
            <div class="pop-header">
                <h2>상품정보고시 등록</h2>
                <button type="button" class="pop-close" @click="onClose"></button>
            </div>
            <div class="pop-body">
                <div class="bar-title">기본정보</div>
                <div class="boxing">
                    <div class="form-area">
                        <dl>
                            <dt>사용여부<i class="essential"></i></dt>
                            <dd>
                                <div class="radio_wrap wide">
                                    <input type="radio" v-model="info.istrash" name="rgroup00" id="rgroup01" value="F" checked/><label for="rgroup01">사용함</label>
                                    <input type="radio" v-model="info.istrash" name="rgroup00" id="rgroup02" value="T" /><label for="rgroup02">사용안함</label>
                                </div>
                            </dd>
                        </dl>
                        <dl>
                            <dt>상품정보고시명<i class="essential"></i></dt>
                            <dd><input type="text" v-model="info.title" style="width: 100%" placeholder="상품정보고시명" /></dd>
                        </dl>
                        <dl>
                            <dt>설명</dt>
                            <dd><input type="text" v-model="info.note" style="width: 100%" placeholder="상품정보고시 설명" /></dd>
                        </dl>
                    </div>
                </div>
                <div class="bar-title">조건설정</div>
                <div class="form-area">
                    <table cellpadding="0" cellspacing="0" class="gray-tb">
                        <colgroup>
                            <col width="170px">
                            <col width="">
                        </colgroup>
                        <tbody>
                            <tr v-for="(row,index) in this.itemList" :key="index">
                                <th>항목/내용<i class="essential"></i></th>
                                <td>
                                    <div class="dpb">
                                        <span>항목</span>
                                        <input type="text" v-model="row.notifyname" class="ml10" placeholder="항목 명칭" />
                                        <span class="ml10">내용</span>
                                        <input type="text" v-model="row.notifydata" class="ml10" style="width: calc(100% - 395px);" placeholder="내용" />
                                        <button type="button" class="del" @click="removeItem(index)"></button>
                                        <button type="button" v-if="index === itemList.length - 1" class="add" @click="addItem()"></button>
                                    </div>
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
    <!-- /상품정보고시 등록 팝업 -->
</template>

<script>
export default {
    name: 'admin.configuration.manage.notifyregist',
    components: {

    },
    data() {
        return {
            info: {
                istrash: 'F',
                title: '',
                note: '',
            },
            itemList: [
                {
                    notifyname: '',
                    notifydata: '',
                }
            ],
            isRead: false,
            isWrite: false,
        }
    },
    mounted() {
        this.$http.post('/admin/common/pageAuth/check', {url : this.$options.name}).then(result => {
            this.isRead = (result.data.isread === 'T');
            this.isWrite = (result.data.iswrite === 'T');

            if(!this.isRead){
                alert('페이지 접근 권한이 없습니다.');
                this.onClose();
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
        });
    },
    methods: {
        onSave() {
            let params = this.info;
            params.itemlist = this.itemList;
            
            if(this.checkValidation()) {
                if(confirm("저장하시겠습니까?")){
                    this.$http.post("/admin/configuration/manage/notify/save", params)
                    .then(result => {
                        if(result.statusCode === 200) {
                            alert("저장이 완료되었습니다.");
                            this.onClose(true);
                        }
                    })
                    .catch(error => {
                        this.$util.debug(error);
                    })
                }
            }
        },
        checkValidation() {
            let checkResult = true;
            // 필수체크
            let valid = [
                {field: 'info.istrash', type: 'S', name:'[기본정보] 사용여부', required: true},
                {field: 'info.title', type: 'I', name:'[기본정보] 상품정보고시명', required: true},
            ];
            let msg = this.$util.validMsg(this.$data, valid);
            if(!this.$util.isNull(msg)){
                alert(msg);
                return false;
            }

            for(let i = 0; i < this.itemList.length; i++) {
                let item = this.itemList[i];
                if(item.notifyname === '') {
                    alert("[조건설정] 항목을 입력해주세요.");
                    checkResult = false;
                    break;
                }
                if(item.notifydata === '') {
                    alert("[조건설정] 내용을 입력해주세요.");
                    checkResult = false;
                    break;
                }
            }

            return checkResult;
        },
        removeItem(index) {
            if(this.itemList.length === 1) {
                alert("마지막 항목은 삭제할 수 없습니다.");
                return;
            }

            this.itemList.splice(index,1);
        },
        addItem() {
            let item = {
                notifyname: '',
                notifydata: '',
            }

            this.itemList.push(item);
        },
        onClose(isreload) {
            this.info = this.$options.data().info;
            this.itemList = this.$options.data().itemList;
            if(typeof(isreload) === 'boolean' && isreload) {
                this.$emit('closeRegist', true);
            } else {
                this.$emit('closeRegist');
            }
        },
    }
}
</script>

<style>

</style>