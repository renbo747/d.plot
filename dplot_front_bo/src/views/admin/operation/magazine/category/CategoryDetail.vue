<template>
     <div id="modal-wrap" class="modal" style="display: block;">
        <div class="modal-content" style="width: 1400px;">
            <div class="pop-header">
                <h2>매거진카테고리관리 상세</h2>
                <button type="button" class="pop-close" @click="onClose"></button>
            </div>
            <div class="pop-body">
                <div class="gray-box mg0 clearfix">
                    <div class="fl">
                        <span>작성자 : {{ info.reguserid }}</span>
                    </div>
                    <div class="fr txt-gray">
                        <span>등록일 : {{ info.regdate }}</span>
                        <span class="left-bar">수정일 : {{info.moddate}} </span>
                    </div>
                </div>  
                <div class="bar-title">기본정보</div>
                <div class="boxing">
                    <div class="form-area">
                        <dl>
                            <dt>노출상태<i class="essential"></i></dt>
                            <dd>
                                <div class="radio_wrap wide">
                                    <input type="radio" name="group00" id="group01" v-model="info.isdisplay" value="T" checked/><label for="group01">노출</label>
                                    <input type="radio" name="group00" id="group02" v-model="info.isdisplay" value="F" /><label for="group02">미노출</label>
                                </div>
                            </dd>
                        </dl>
                        <dl>
                            <dt>제목<i class="essential"></i></dt>
                            <dd><input type="text" style="width: 100%" v-model="info.subject" placeholder="카테고리 제목" /></dd>
                        </dl>
                        <dl>
                            <dt>설명</dt>
                            <dd><input type="text" style="width: 100%" v-model="info.desc" placeholder="카테고리 설명" /></dd>
                        </dl>
                    </div>
                </div>
                <div class="btn-group">
                    <button type="button" class="btn big blue" @click="goSave">저장</button>
                    <button type="button" class="btn big darkgray" @click="onClose">취소</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    props: ['idx'],
    name: 'admin.operation.magazine.category.detail',
    components: {

    },
    data() {
        return {
            info: {},
            isRead: false,
            isWrite: false,
        }
    },
    mounted() {
        this.$http.post('/admin/common/pageAuth/check', {url : this.$options.name}).then(result => {

            this.isRead = (result.data.isread === 'T');
            this.isWrite = (result.data.iswrite === 'T');
      
            if(this.isRead) {
                let param = {
                    mzcateidx : this.idx,
                }

                this.$http.post("/admin/operation/magazine/category/detail", param)
                .then(result =>{
                    this.info = result.data.info;
                })
                .catch(error => {
                    this.$util.debug(error);
                });
            } else {
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
        goSave() {
            if(this.checkValidation()) {
                let params = this.info;

                if(params.isdisplay === 'F') {
                    let param = {
                        idx: params.mzcateidx,
                        isdisplay: params.isdisplay,
                        overmsg: '저장하시겠습니까?',
                        zeromsg: '저장',
                        isloading: false,
                    }

                    this.$http.post("/admin/operation/magazine/category/check", param)
                    .then(result => {
                        if (result.statusCode == 200) {
                            params.msg = result.data.msg;
                            this.update(params);
                        }
                    })
                    .catch(error => {
                        this.$util.debug(error);
                    })
                } else {
                    params.msg = '저장하시겠습니까?';
                    this.update(params);
                }
            }
        },
        update(params) {
            if(confirm(params.msg)){
                this.$http.post("/admin/operation/magazine/category/modify", params)
                .then(result => {
                    if(result.statusCode === 200){
                        alert("저장되었습니다.");
                        this.onClose(true);
                    }
                })
                .catch(error => {
                    this.$util.debug(error);
                });
            }
        },
        checkValidation() {
            let msg = '';
            let valid = [
                {field: 'info.subject', type: 'I', name: '제목', required: true},
            ];

            msg = this.$util.validMsg(this.$data, valid);
            if(!this.$util.isNull(msg)){
                alert(msg);
                return false;
            }

            return true;
        },
        onClose(isreload) {
            this.info = this.$options.data().info;
            // 팝업 닫기
            if (typeof(isreload)==='boolean' && isreload) {
                this.$emit('closeDetail', isreload);
            } else {
                this.$emit('closeDetail');
            }
        },
    }
}
</script>

<style>

</style>