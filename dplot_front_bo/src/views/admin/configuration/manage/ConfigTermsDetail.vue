<template>
     <!-- 약관 상세 팝업 -->
    <div id="modal-wrap" class="modal" style="display: block;">
        <div class="modal-content" style="width: 1280px;">
            <div class="pop-header">
                <h2>약관 상세</h2>
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
                            <td>{{info.isessen === 'T' ? '필수':'선택'}}</td>
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
                                <CommonEditor ref="editor" v-bind:disable="true"/>
                            </td>
                        </tr>
                    </tbody>
                </table>
                <div class="btn-group">
                    <button type="button" class="btn big darkgray" @click="onClose">닫기</button>
                </div>
            </div>
        </div>
    </div>
    <!-- /약관 상세 팝업 -->
</template>

<script>
import CommonEditor from "@views.admin/common/CommonEditor";
export default {
    name: 'admin.configuration.manage.termsdetail',
    props: ['idx'],
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
                idx: this.idx
            }

            this.$http.post('admin/configuration/manage/terms/detail', param)
            .then(result => {
                if(result.statusCode === 200) {
                    this.info = result.data.info;
                    this.$refs.editor.content = this.info.content;
                }
            })
        },
        onClose() {
            this.info = this.$options.data().info;
            this.$emit('closeDetail');
        }
    }
}
</script>

<style>

</style>