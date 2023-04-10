<template>
    <!-- 클레임 처리이력 팝업 -->
    <div id="modal-wrap" class="modal" style="display: block;">
        <div class="modal-content" style="width: 800px;">
            <div class="pop-header">
                <h2>클레임 처리이력</h2>
                <button type="button" class="pop-close" @click="closePopup"></button>
            </div>
            <div class="pop-body">
                <div class="scroll-y" style="max-height: 500px;">
                    <table cellpadding="0" cellspacing="0" class="data-tb align-c">
                        <colgroup>
                            <col width="6%" /><!-- No -->
                            <col width="25%" /><!-- 처리일시 -->
                            <col width="25%" /><!-- 처리상태 -->
                            <col width="20%" /><!-- 처리구분 -->
                            <col width="" /><!-- 처리자 -->
                        </colgroup>
                        <thead>
                            <tr>
                                <th>No</th>
                                <th>처리일시</th>
                                <th>처리상태</th>
                                <th>처리구분</th>
                                <th>처리자</th>
                            </tr>
                        </thead>
                        <tbody v-if="list.length > 0">
                            <tr v-for="(item, index) in list" :key="item.clmstidx" >
                                <td>{{ list.length-index }}</td>
                                <td>{{ item.clmprcdate }}</td>
                                <td>{{ item.clmprcaftstatus }}</td>
                                <td>{{ item.clmprctypename }}</td>
                                <td>{{ item.clmprcmanager }}</td>
                            </tr>
                        </tbody>
                        <tbody v-else>
                            <tr><td colspan="5">조회 결과가 존재하지 않습니다.</td></tr>
                        </tbody>
                    </table>
                </div>
                <div class="btn-group">
                    <button type="button" class="btn darkgray" @click="closePopup">닫기</button>
                </div>
            </div>
        </div>
    </div>
    <!-- /클레임 처리이력 팝업 -->
</template>

<script>

export default {
    name: 'ClaimStatusHistoryPopup',
    props: {
        modalComp: Object,
        params: Object,
        callbackFn: Function
    },
    data() {
        return {
            list: []    // 클레임처리이력
        }
    },
    mounted() {
        this.selectClmprcHistoryList();
    },
    methods: {
        // 클레임처리이력 조회
        selectClmprcHistoryList: function() {
            this.$http.post('/admin/order/claim/status/list', { clmgdidx: this.params.clmgdidx, ordgdidx: this.params.ordgdidx, clmidx: this.params.clmidx })
                    .then(result => {
                        this.$util.debug(result);
                        this.list = result.data.list;
                    })
                    .catch(error => {
                        this.$util.debug(error);
                    });
        },
        // 팝업닫기
        closePopup(params){
            if(typeof(this.callbackFn) === 'function') {
                this.callbackFn(params);
            }
            this.$modal.hide('commonModal');
        }
    }
}
</script>