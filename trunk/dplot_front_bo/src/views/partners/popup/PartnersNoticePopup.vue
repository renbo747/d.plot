<template>
    <!-- 공지사항 팝업 -->
    <div id="modal-wrap" class="modal" style="display: block;">
        <div class="modal-content" style="width: 800px;">
            <div class="pop-header f-height">
                <h2>{{ noticeList[nowIdx].subject }}</h2>
                <button type="button" class="pop-close" @click="onClose"></button>
            </div>
            <div class="pop-body">
                <div class="boxing notice-con" style="min-height: 30vh;" v-html="noticeList[nowIdx].content">
                </div>
                <div class="clearfix mt10">
                    <input type="checkbox" id="close" v-model="closeEver"><label for="close">다시보지않기</label>
                    <div class="btn-group fr">
                        <button type="button" class="btn big darkgray" @click="onClose">닫기</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- /공지사항 팝업 -->
</template>

<script>
export default {
    name: 'noticepopup',
    props: ['list'],
    data() {
        return {
            noticeList: this.list,
            closeEver: true,
            closeIdxList: [],
            nowIdx: 0,
        }
    },
    mounted() {
        // 공지사항 다시보지않기 처리용 쿠키 조회
        let noticeIdxList = this.$cookies.get("notice_popup");

        if(noticeIdxList !== null){
            this.closeIdxList = noticeIdxList.split(',');
        }
    },
    methods: {
        onClose(){
            if(this.closeEver) {
                this.closeIdxList.push(this.noticeList[this.nowIdx].idx);
                this.$cookies.set("notice_popup", this.closeIdxList.join(), "1y");
            }

            if(this.noticeList.length === this.nowIdx + 1) {
                // 팝업 닫기
                this.$emit('closePopup', 'NOTI');
            } else {
                this.nowIdx++;
                this.closeEver = true;   
            }
        },
    }
}
</script>

<style>

</style>