<template>
    <!-- 반려사유 팝업 -->
    <div id="modal-wrap" class="modal" style="display: block;">
        <div class="modal-content" style="width: 400px;">
            <div class="pop-header">
                <h2>반려사유</h2>
                <button type="button" class="pop-close" @click="$modal.hide('commonModal');"></button>
            </div>
            <div class="pop-body">
                <textarea placeholder="반려사유를 입력하세요." v-model="contents" maxlength="200"></textarea>
                <div class="btn-group mt10">
                    <button type="button" class="btn blue" @click="sendRejectRs">저장</button>
                    <button type="button" class="btn darkgray" @click="$modal.hide('commonModal');">취소</button>
                </div>
            </div>
        </div>
    </div>
    <!-- /반려사유 팝업 -->
</template>

<script src="./InputRejectRsPopup.js"></script>