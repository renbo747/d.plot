<template>
    <!-- 약관 수정 팝업 -->
    <div id="modal-wrap" class="modal" style="display: block;">
        <div class="modal-content" style="width: 1280px;">
            <div class="pop-header">
                <h2>약관 수정</h2>
                <button type="button" class="pop-close" @click="onClose"></button>
            </div>
            <div class="pop-body">
                <table cellpadding="0" cellspacing="0" class="gray-tb">
                    <colgroup>
                        <col width="100px">
                        <col width="140px">
                        <col width="100px">
                        <col width="140px">
                        <col width="100px">
                        <col width="120px">
                        <col width="100px">
                        <col width="180px">
                        <col width="100px">
                        <col width="160px">
                    </colgroup>
                    <tbody>
                        <tr>
                            <th>구분</th>
                            <td>{{info.iscusterms}}</td>
                            <th>등록자</th>
                            <td>{{info.reguserid}}</td>
                            <th>버전</th>
                            <td>{{info.version}}</td>
                            <th>필수여부</th>
                            <td>
                                <div class="radio_wrap">
                                    <input type="radio" v-model="info.isessen" name="group00" id="group01" value="T" checked><label for="group01">필수</label>
                                    <input type="radio" v-model="info.isessen" name="group00" id="group02" value="F"><label for="group02">선택</label>
                                </div>
                            </td>
                            <th>업데이트일시</th>
                            <td>{{info.moddate}}</td>
                        </tr>
                        <tr>
                            <th>약관명</th>
                            <td colspan="9">{{info.termsname}}</td>
                        </tr>
                        <tr>
                            <th>약관 내용</th>
                            <td colspan="9">
                                <CommonEditor ref="editor"/>
                            </td>
                        </tr>
                    </tbody>
                </table>
                <div class="btn-group">
                    <button type="button" class="btn big blue" @click="onSave('V')">버전 업데이트</button>
                    <button type="button" class="btn big blue" @click="onSave('U')">수정</button>
                    <button type="button" class="btn big darkgray" @click="onClose">닫기</button>
                </div>
            </div>
        </div>
    </div>
    <!-- /약관 수정 팝업 -->
</template>

<script>
import CommonEditor from "@views.admin/common/CommonEditor";
export default {
    name: 'admin.configuration.manage.termsmodify',
    props: ['termsidx'],
    components: {
        CommonEditor
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

            if(this.isRead){
                this.onInit();
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
        onInit() {
            let param = {
                termsidx: this.termsidx
            }

            this.$http.post('admin/configuration/manage/terms/detail', param)
            .then(result => {
                if(result.statusCode === 200) {
                    this.info = result.data.info;
                    this.$refs.editor.content = this.info.content;
                }
            })
        },
        onSave(type) {
            this.setEditorText();

            let params = this.info;

            let msg = '수정 시 ';
            if(type === 'V') {
                params.versionup = 'T';
                msg += '버전 업데이트와 동시에\n';
            } else {
                params.versionup = 'F';
            }
        
            
            if(confirm(msg+"사용자 화면에 수정된 내용이 노출됩니다.\n약관 내용을 수정하시겠습니까?")){
                this.$http.post('admin/configuration/manage/terms/update', params)
                .then(result => {
                    if(result.statusCode === 200) {
                        alert("저장이 완료되었습니다.");
                        this.onClose(true);
                    }
                })
            }
        },
        // 에디터 내용 가져오기
        setEditorText() {
            if (this.$util.isNull(this.$refs.editor.content)) {
                this.info.content = ''
            } else {
                this.info.content = this.$refs.editor.content;
            }
        },
        onClose(isreload) {
            this.info = this.$options.data().info;
            if(typeof(isreload) === 'boolean' && isreload) {
                this.$emit('closeModify', true);
            } else {
                this.$emit('closeModify');
            }
        }
    }
}
</script>

<style>

</style>