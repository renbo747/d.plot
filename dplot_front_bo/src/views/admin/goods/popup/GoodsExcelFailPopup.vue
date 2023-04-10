<template>
    <div id="modal-wrap" class="modal" style="display: block;">
        <div class="modal-content" style="width: 720px;">
            <div class="pop-header">
                <h2>업로드 실패</h2>
                <button type="button" class="pop-close" @click="onClose"></button>
            </div>
            <div class="pop-body">
                <div class="gray-box mg0">
                    <div class="clearfix">
                        <div class="fl">파일명 : {{ excelFailParams.exfilename }}</div>
                        <div class="fr txt-gray"> 업로드 일시 : {{ excelFailParams.uploaddate }}</div>
                    </div>
                </div>
                <div class="caption-group mt10 clearfix">
                    <div class="total-group fl">
                        <span class="total">실패건수 <strong>{{ excelFailParams.failcnt }}</strong>건</span>
                    </div>
                </div>
                <div class="boxing" style="height: 100px;">
                    <dl>
                        <dd >
                            <ul><li>{{ excelFailParams.failrow }}</li></ul>
                        </dd>
                    </dl>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: 'GoodsExcelFailPopup',
    props : ['excelFailParams'],
    data() {
        return {
            info: {
                excelidx: '',
                excelfilename: '',
                uploaddate: ''
            }
        }
    },
    methods : {
        // 첨부파일(탐색기 열기)
        fileAttach: function() {
            this.$refs['imageFile'].click();
        },
        // 가져온 파일 세팅
        handleFileUpload: function() {
            let file = this.$refs.imageFile;
            if (this.$util.isNull(file.files[0])) {
                return;
            }
            let fileType = ['application/x-zip-compressed'];
            if(!fileType.includes(file.files[0].type)){
                alert('압축 파일만 첨부 가능합니다.');
                file.value = null;
                return false;
            }
            
            this.imageFile = file.files[0];
            this.$refs.imageFileName.value = file.files[0].name;
        },
        uploadImageFile: function() {
            let params = {
                idx: this.idx
            }
            let files = [];

            if(this.$util.isNull(this.imageFile)) {
                alert("이미지 파일을 등록해주세요.");
                return ;
            } else {
               files.push({key: 'imgfile', file: this.imageFile});
            }

            params.files = files;

            this.$http.post('/admin/goods/manage/upload/imagezip', params)
                .then(result => {
                    this.$util.debug(result);
                    if (result.statusCode == 200) {
                        alert("저장 되었습니다.");
                        this.onClose(true);
                    } else {
                        alert(result.message);
                    }
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        onClose(isreload){
            // 팝업 닫기
            if (typeof(isreload)==='boolean' && isreload) {
                this.$emit('closeUpload', isreload);
            } else {
                this.$emit('closeUpload');
            }
        },
    }
}
</script>

<style scoped>
.blue-box ul li {
    font-size: 14px;
    color: #333;
    line-height: 28px;
}
.blue-box ul li:before {
    content: "";
    display: inline-block;
    width: 3px;
    height: 3px;
    border-radius: 50%;
    background: #333;
    margin-right: 10px;
    vertical-align: middle;
}
</style>