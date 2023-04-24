<template>
    <!-- 다운로드 스크립트 복사 -->
    <div id="modal-wrap" class="modal" style="display: block;">
        <div class="modal-content" style="width: 600px;">
            <div class="pop-header">
                <h2>다운로드 스크립트</h2>
                <button type="button" class="pop-close" @click="onClose"></button>
            </div>
            <div class="pop-body">
                <textarea v-model="scriptData" style="height: 180px; background: #fff !important;" readonly></textarea>
                <div class="btn-group mt10">
                    <button type="button" class="btn big blue" @click="copyScript">복사</button>
                    <button type="button" class="btn big darkgray" @click="onClose">닫기</button>
                </div>
            </div>
        </div>
    </div>
    <!-- /다운로드 스크립트 복사 -->
</template>

<script>

export default {
    name: 'CopyDownloadScriptPopup',
    props: {
        modalComp: Object,
        params: Object,
        callbackFn: Function
    },
    data() {
        return {
            scriptData: ''
        }
    },
    mounted() {
        this.scriptData = "this.$http.post('/coupon/down', {cpninfoidx:"+this.params.cpninfoidx+"}).then(result => {\n"
                               + "    if (result.statusCode == 200) {\n"
                               + "        //성공\n"
                               + "        alert('쿠폰 받기가 완료 되었어요.');\n"
                               + "    }else{\n"
                               + "        //실패\n"
                               + "    }\n"
                               + "});";
    },
    methods: {
        copyScript: function() {
            const tempTextArea = document.createElement('textarea');
            document.body.appendChild(tempTextArea);
            tempTextArea.value = this.scriptData;
            tempTextArea.select();
            document.execCommand('copy');
            document.body.removeChild(tempTextArea);
            alert('스크립트가 복사되었습니다.');
        },
        onClose: function() {
            if(typeof(this.callbackFn) === 'function') {
                this.callbackFn();
            }
            this.$modal.hide('commonModal');
        }
    }
}
</script>