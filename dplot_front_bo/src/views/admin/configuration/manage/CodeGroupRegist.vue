<template>
    <!-- 공통코드그룹 등록 팝업 -->
    <div id="modal-wrap" class="modal" style="display: block;">
        <div class="modal-content" style="width: 1000px;">
            <div class="pop-header">
                <h2>공통코드그룹 등록</h2>
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
                                    <input type="radio" v-model="info.istrash" name="group00" id="group01" value="F" checked/><label for="group01">사용함</label>
                                    <input type="radio" v-model="info.istrash" name="group00" id="group02" value="T" /><label for="group02">사용안함</label>
                                </div>
                            </dd>
                        </dl>
                        <dl>
                            <dt>공통코드그룹<i class="essential"></i></dt>
                            <dd><input type="text" v-model="info.cmclass" style="width: 100%" placeholder="공통코드그룹" /></dd>
                        </dl>
                        <dl>
                            <dt>공통코드그룹명<i class="essential"></i></dt>
                            <dd><input type="text" v-model="info.classname" style="width: 100%" placeholder="공통코드그룹명" /></dd>
                        </dl>
                        <dl>
                            <dt>설명<i class="essential"></i></dt>
                            <dd><input type="text" v-model="info.detail" style="width: 100%" placeholder="공통코드그룹 설명" /></dd>
                        </dl>
                    </div>
                </div>
                <div class="btn-group">
                    <button type="button" class="btn big blue" @click="onSave">저장</button>
                    <button type="button" class="btn big darkgray" @click="onClose">취소</button>
                </div>
            </div>
        </div>
    </div>
    <!-- /공통코드그룹 등록 팝업 -->
</template>

<script>
export default {
    name: 'admin.configuration.manage.codegroupregist',
    components: {

    },
    data() {
        return {
            info: {
                istrash: 'F',
                cmclass: '',
                classname: '',
                detail: '',
            },
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
            params.type = 'G';
            
            if(this.checkValidation()) {
                if(confirm("저장하시겠습니까?")){
                    this.$http.post("/admin/configuration/manage/code/save", params)
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
                {field: 'info.cmclass', type: 'I', name:'[기본정보] 공통코드그룹', required: true},
                {field: 'info.classname', type: 'I', name:'[기본정보] 공통코드그룹명', required: true},
                {field: 'info.detail', type: 'I', name:'[기본정보] 설명', required: true},
            ];
            let msg = this.$util.validMsg(this.$data, valid);
            if(!this.$util.isNull(msg)){
                checkResult = false;
                alert(msg);
            }

            return checkResult;
        },
        onClose(isreload) {
            this.info = this.$options.data().info;
            if(typeof(isreload) === 'boolean' && isreload) {
                this.$emit('closeClassRegist', true);
            } else {
                this.$emit('closeClassRegist');
            }
        },
    }
}
</script>

<style>

</style>