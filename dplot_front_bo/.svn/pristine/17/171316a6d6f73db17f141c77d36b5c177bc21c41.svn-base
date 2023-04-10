<template>
    <div id="modal-wrap" class="modal" style="display: block;">
        <div class="modal-content" style="width: 720px;">
            <div class="pop-header">
                <h2>이미지 업로드</h2>
                <button type="button" class="pop-close" @click="onClose"></button>
            </div>
            <div class="pop-body">
                <div class="blue-box mg0">
                    <ul>
                        <li>이미지는 zip파일로 압축해서 업로드 하시기 바랍니다.</li>
                        <li>엑셀에서 작성한 파일명과 업로드하는 이미지 파일명은 동일해야 합니다.</li>
                        <li>파일명이 중복된 경우에는 동일 이미지로 간주됩니다.</li>
                        <li>이미지 파일명은 영문으로만 작성해 주세요.</li>
                        <p class="txt-orange"><i class="icon-alert"></i>사이즈: 720*720 / 용량: 2MB 이하 / 파일 : JPG, JPEG, PNG</p>
                    </ul>
                    <div class="mt20">
                        <input type="file" ref="imageFile" @change="handleFileUpload" hidden
                            accept=".zip" />
                        <input type="text" ref="imageFileName" readonly>
                        <button type="button" class="btn blue-line ml3" @click="fileAttach">파일찾기</button>
                        <button type="button" class="btn blue" @click="uploadImageFile">파일업로드</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: 'GoodsImageUpload',
    props : ['uploadParams'],
    data() {
        return {
            excelidx: this.uploadParams.excelidx,
            dealerno: this.uploadParams.dealerno,
            imageFile: null
        }
    },
    methods : {
        // 첨부파일(탐색기 열기)
        fileAttach: function() {
            this.$refs.imageFile.click();
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
            let params = { excelidx: this.excelidx, dealerno: this.dealerno };
            let files = [];

            if(this.$util.isNull(this.imageFile)) {
                alert("이미지 압축파일을 등록해주세요.");
                return ;
            } else {
               files.push({key: 'imgfile', file: this.imageFile});
            }

            params.files = files;

            this.$http.post('/admin/goods/upload/imagezip', params).then(result => {
                this.$util.debug(result);
                if (result.statusCode == 200) {
                    alert("업로드가 완료되었습니다.");
                    this.onClose(true);
                }
                this.imageFile = null;
                this.$refs.imageFile.value = '';
                this.$refs.imageFileName.value = '';
            })
            .catch(error => {
                this.$util.debug(error);
                this.imageFile = null;
                this.$refs.imageFile.value = '';
                this.$refs.imageFileName.value = '';
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