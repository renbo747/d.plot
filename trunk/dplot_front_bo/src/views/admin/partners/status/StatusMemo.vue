<template>
    <div id="modal-wrap" class="modal" style="display: block;">
        <div class="modal-content" style="width: 800px;">
            <div class="pop-header">
                <h2>{{partnername}}({{partnerid}}) Memo</h2>
                <button type="button" class="pop-close" @click="onClose"></button>
            </div>
            <div class="pop-body">
                <div class="memo-write">
                    <textarea placeholder="메모를 작성해 주세요!" v-model="savememo"></textarea>
                    <button type="button" class="btn big blue" @click="goSave(-1)">메모<br>추가</button>
                </div>
                <ul class="memo-list" v-if="this.memoList.length > 0">
                    <li v-for="(row, i) in this.memoList" :key="i">
                        <div class="memo-info clearfix">
                            <div class="info-group">
                                <span class="num">{{row.rownum}}</span>
                                <span>{{row.regdate}}</span>
                                <span>{{row.regusername}}</span>
                            </div>
                            <div class="btn-group">
                                <button type="button" class="btn blue" @click="row.readonly === 'T' ? changeReadonly(i) : goSave(i)">{{row.readonly === 'T' ? '수정' : '저장'}}</button>
                                <button type="button" class="btn red-line" @click="removeMemo(row.idx)">삭제</button>
                            </div>
                        </div>
                        <textarea :disabled="row.readonly === 'T'" v-model="row.adminmemo"></textarea>
                    </li>
                </ul>
                <div class="bottom-group">
                    <CommonPageNavigator v-bind:pagingData="this.pagingData" v-on:setPagingData="setPagingData"/>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import CommonPageNavigator from "../../common/CommonPageNavigator";

export default {
    name: 'admin.partners.status.memo',
    props: {
        row: Object
    },
    components:{
        CommonPageNavigator,
    },
    data() {
        return {
            partnername : '',
            partnerid : '',
            savememo : '',
            memoList : [],
            pagingData: {
                pageCount: 20, // 페이징 옵션 (최대수)
                page: 1,       // 현재 페이지
                listCount: 0   // 총 페이지
            },
            isRead: false,
            isWrite: false
        }
    },
    mounted() {
        this.$http.post('/admin/common/pageAuth/check', {url : this.$options.name}).then(result => {

                this.isRead = (result.data.isread === 'T');
                this.isWrite = (result.data.iswrite === 'T');

                if(this.isRead){
                    this.partnername = this.row.name;
                    this.partnerid = this.row.dealer_id;
                    this.savememo = '';
                    this.pagingData.page = 1;
                    
                    this.onSearch();
                } else {
                    alert("페이지 접근 권한이 없습니다.");
                    this.info = this.$options.data().info;
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
    methods : {
        goSave(i){
            let params = {
                idx : i === -1 ? 0 : this.memoList[i].idx,
                adminmemo : i === -1 ? this.savememo : this.memoList[i].adminmemo,
                userno : this.row.no
            };

            if(this.$util.isNull(params.adminmemo)) {
                alert("메모를 입력해주세요.");
                return ;
            }

            if(confirm("저장하시겠습니까?")){
                this.$http.post("/admin/partners/status/memo/save", params)
                    .then(result => {
                        alert("저장되었습니다.");
                        if(i === -1){
                            this.savememo = '';
                        }
                        this.onSearch();
                    })
                    .catch(error => {
                        alert("저장에 실패했습니다.");
                        this.$util.debug(error);
                    })
            }
        },
        changeReadonly(i){
            this.memoList[i].readonly = 'F';
        },
        onSearch(){
            let params = {
                userno : this.row.no
            };
            params.pageCount = this.pagingData.pageCount;
            params.page = (typeof page === 'undefined') ? this.pagingData.page : 1;
            params.listCount = this.pagingData.listCount;

            this.$http.post("/admin/partners/status/memo", params)
                .then(result => {
                    let data = result.data;
                    this.memoList = data.list;
                    this.pagingData.listCount = data.listcount;
                })
                .catch(error => {
                    this.$util.debug(error);
                })
        },
        removeMemo(idx){
            let param = {
                idx : idx,
                isuse : 'F',
            }

            if(confirm("삭제하시겠습니까?")){
                this.$http.post("/admin/partners/status/memo/save", param)
                    .then(result => {
                        alert("삭제가 완료되었습니다.")
                        this.onSearch();
                    })
                    .catch(error => {
                        alert("삭제를 실패했습니다.")
                        this.$util.debug(error);
                    })
            }
        },
        onClose(){
            this.memoList = [];
            this.$emit('closeMemo', true);
        },
        setPagingData(param){
            this.pagingData = param;
            this.onSearch();
        },
    }
}
</script>

<style>

</style>