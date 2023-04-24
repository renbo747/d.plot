<template>
    <!-- 정산완료 팝업 -->
    <div id="modal-wrap" class="modal" style="display: block;">
        <div class="modal-content" style="width: 300px;">
            <div class="pop-header">
                <h2>정산완료</h2>
                <button type="button" class="pop-close" @click="onClose"></button>
            </div>
            <div class="pop-body">
                <div class="txt-alert-box">
                    <strong class="txt-black">{{ calcdata.calcdate}} </strong>정산이 완료되었습니다.<br>
                    정산내역을 확인하시기 바랍니다.
                </div>               
                <div class="btn-group">
                    <button type="button" class="btn big blue" @click="goCalcPage">정산내역확인</button>
                </div>  
                
            </div>
        </div>
    </div>
    <!-- /정산완료 팝업 -->
</template>

<script>
export default {
    name: 'calcpopup',
    props: ['list'],
    data() {
        return {
            calcdata: this.list[0],
        }
    },
    methods: {
        goCalcPage() {
            let param = {
                idx : this.calcdata.calcidx
            }

            this.$http.post('/partners/adjust/cfm/update', param)
            .then(result => {
                if(result.statusCode === 200) {
                    // 확인 업데이트 시 이동 or 이동시 확인 업데이트
                    this.$router.push({name : 'partners.adjust.decision'});
                }
            })
        },
        onClose(){
            // 팝업 닫기
            this.$emit('closePopup', 'CAL');
        },
    }
}
</script>

<style>

</style>