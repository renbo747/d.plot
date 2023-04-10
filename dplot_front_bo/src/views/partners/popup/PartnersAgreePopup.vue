<template>
    <!-- 동의공지 팝업 -->
    <div id="modal-wrap" class="modal" style="display: block;">
        <div class="modal-content" style="width: 800px;">
            <div class="pop-header f-height">
                <h2>{{ agreeList[nowIdx].subject }}</h2>
                <button type="button" class="pop-close" @click="onClose"></button>
            </div>
            <div class="pop-body">
                <div class="txt-gray midium mb10">동의기간 : {{agreeList[nowIdx].posttime}}</div>
                <div class="boxing notice-con" style="min-height: calc(30vh - 32px);" v-html="agreeList[nowIdx].content">
                </div>
                <div class="btn-group">
                    <button type="button" class="btn big blue" @click="goAgree('T')">동의</button>
                    <button type="button" class="btn big red" @click="goAgree('F')">미동의</button>
                </div>              
            </div>
        </div>
    </div>
    <!-- /동의공지 팝업 -->
</template>

<script>
export default {
    name: 'agreepopup',
    props: ['list'],
    data() {
        return {
            agreeList: this.list,
            nowIdx: 0,
        }
    },
    methods: {
        goAgree(isagree) {
            let params = {
                postidx : this.agreeList[this.nowIdx].idx,
                isagree: isagree
            }

            let msg = isagree === 'T' ? '동의' : '미 동의';
            let msg2 = isagree === 'T' ? '동의가' : '미 동의로';

            if(confirm(msg + ' 하시겠습니까?')) {
                this.$http.post('/partners/partners/consent/agree', params)
                .then(result => {
                    if(result.statusCode === 200) {
                        alert(msg2 + ' 완료되었습니다.');
                        this.onClose();
                    }
                })
            }
        },
        onClose(){
            if(this.agreeList.length === this.nowIdx + 1) {
                // 팝업 닫기
                this.$emit('closePopup', 'AGREE');
            } else {
                this.nowIdx++; 
            }
        },
    }
}
</script>

<style>

</style>