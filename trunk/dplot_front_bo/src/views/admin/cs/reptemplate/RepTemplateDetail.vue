<template>
    <!-- 답변내용 상세 팝업 -->
    <div id="modal-wrap" class="modal" style="display: block;">
        <div class="modal-content" style="width: 900px;">
            <div class="pop-header">
                <h2>답변내용상세</h2>
                <button type="button" class="pop-close" @click="onClose"></button>
            </div>
            <div class="pop-body">
                <div class="form-area">
                    <table cellpadding="0" cellspacing="0" class="gray-tb">
                        <colgroup>
                            <col width="170px">
                            <col width="">
                            <col width="170px">
                            <col width="">
                        </colgroup>
                        <tbody>
                            <tr>
                                <th>작성일</th>
                                <td>{{ info.regdate }}</td>
                                <th>작성자</th>
                                <td>{{ info.regusername }}</td>
                            </tr>
                            <tr>
                                <th>제목</th>
                                <td colspan="3">
                                    <input type="text" style="width: 100%;" placeholder="답변제목을 입력하세요." v-model="info.subject">
                                </td>
                            </tr>
                            <tr>
                                <th>답변내용</th>
                                <td colspan="3">
                                    <CommonEditor ref="repeditor" :style-object="{height: 300 + 'px'}"/>
                                </td>
                            </tr>
                            
                        </tbody>
                    </table>
                </div>
                <div class="btn-group">
                    <button type="button" class="btn big blue" @click="saveRepTemplate">저장</button>
                    <button type="button" class="btn big red" @click="removeRepTemplate">삭제</button>
                    <button type="button" class="btn big darkgray" @click="onClose">닫기</button>
                </div>
            </div>
        </div>
    </div>
    <!-- /답변내용 상세 팝업-->
</template>

<script>
import CommonEditor from "@views.admin/common/CommonEditor";
export default {
    name: 'admin.cs.reptemplate.detail',
    components: {
        CommonEditor
    },
    props: ['activeTplIdx'],
    mounted() {
        let params = { url: this.$options.name, isloading: false };
        this.$http.post('/admin/common/pageAuth/check', params)
            .then(result => {
                this.isRead = (result.data.isread === 'T');
                this.isWrite = (result.data.iswrite === 'T');
            
                if(this.isRead) {
                    // 답변내용 조회
                    this.getRepTemplate();
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
    data() {
        return {
            info: {},
            isRead : false,
            isWrite : false
        };
    },
    methods: {
        // 답변내용 조회
        getRepTemplate: function() {
            let params = { tplidx: this.activeTplIdx }
            this.$http.post('/admin/cs/reptemp/detail', params)
                .then(result => {
                    if(result.statusCode === 200) {
                        this.info = result.data.info;
                        this.$refs.repeditor.content = this.info.content;
                    }
                }).catch(error => {
                    this.$util.debug(error);
                });
        },
        // 답변저장
        saveRepTemplate: function() {
            this.info.content = this.$refs.repeditor.content;
            // 필수입력항목 체크
            let valid = [
                {field: 'info.subject', type: 'I', name:'답변제목', required: true},
                {field: 'info.content', type: 'I', name:'답변내용', required: true}
            ];
            let msg = this.$util.validMsg(this.$data, valid);
            if(!this.$util.isNull(msg)){
                alert(msg);
                return;
            }
            if(this.$util.isNull(this.info.subject.trim())) {
                alert('답변제목은 필수입력 항목입니다.');
                return;
            }
            if(this.$util.isNull(this.info.content.trim())) {
                alert('답변내용은 필수입력 항목입니다.');
                return;
            }
            
            if (confirm("저장 하시겠습니까?")) {
                this.$http.post('/admin/cs/reptemp/save', this.info)
                    .then(result => {
                        if(result.statusCode === 200) {
                            alert("저장이 완료되었습니다.");
                            this.onClose(true);
                        }
                    }).catch(error => {
                        this.$util.debug(error);
                    });
            }
        },
        // 답변삭제
        removeRepTemplate: function() {
            if (confirm("삭제 하시겠습니까?")) {
                this.$http.post('/admin/cs/reptemp/remove', this.info)
                    .then(result => {
                        if(result.statusCode === 200) {
                            alert("삭제가 완료되었습니다.");
                            this.onClose(true);
                        }
                    }).catch(error => {
                        this.$util.debug(error);
                    });
            }
        },
        // 팝업 닫기
        onClose: function(isreload) {
            if (typeof(isreload)==='boolean' && isreload) {
                this.$emit('closePopup', isreload);
            } else {
                this.$emit('closePopup');
            }
        }
    }
}
</script>
