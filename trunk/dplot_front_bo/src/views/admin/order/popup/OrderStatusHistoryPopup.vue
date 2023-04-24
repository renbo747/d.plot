<template>
    <!-- 변경이력 팝업 -->
    <div id="modal-wrap" class="modal" style="display: block;">
        <div class="modal-content" style="width: 600px;">
            <div class="pop-header">
                <h2>주문상태 변경이력</h2>
                <button type="button" class="pop-close" @click="closePopup"></button>
            </div>
            <div class="pop-body">
                <div class="scroll-y" style="max-height: 500px;">
                    <table cellpadding="0" cellspacing="0" class="data-tb align-c">
                        <colgroup>
                            <col width="10%" /><!-- No -->
                            <col width="35%" /><!-- 변경일자 -->
                            <col width="20%" /><!-- 처리자 -->
                            <col width="35%" /><!-- 변경내역 -->
                        </colgroup>
                        <thead>
                            <tr>
                                <th>No</th>
                                <th>변경일자</th>
                                <th>처리자</th>
                                <th>변경내역</th>
                            </tr>
                        </thead>
                        <tbody v-if="list.length > 0">
                            <tr v-for="(item, index) in list" :key="item.ordstidx" >
                                <td>{{ index+1 }}</td>
                                <td>{{ item.regdate }}</td>
                                <td>{{ item.reguserid }}</td>
                                <td>{{ (!$util.isNull(item.prestatusname)? item.prestatusname + ' > ': '') + item.aftstatusname}}</td>
                            </tr>
                        </tbody>
                        <tbody v-else>
                            <tr><td colspan="4">조회 결과가 존재하지 않습니다.</td></tr>
                        </tbody>
                    </table>
                </div>
                <div class="btn-group">
                    <button type="button" class="btn darkgray" @click="closePopup">닫기</button>
                </div>
            </div>
        </div>
    </div>
    <!-- /변경이력 팝업 -->
</template>

<script>

export default {
    name: 'OrderStatusHistoryPopup',
    props: {
        modalComp: Object,
        params: Object,
        callbackFn: Function
    },
    data() {
        return {
            list: []    // 주문상태이력
        }
    },
    mounted() {
        this.selectStatusHistoryList();
    },
    methods: {
        // 상태변경이력 조회
        selectStatusHistoryList: function() {
            this.$http.post('/admin/order/manage/status/list', { orgdelividx: this.params.orgdelividx, ordgdidx: this.params.ordgdidx })
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