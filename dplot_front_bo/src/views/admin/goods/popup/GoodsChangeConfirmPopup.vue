<template>
    <!-- 일괄변경 alert 팝업 -->
    <div id="modal-wrap" class="modal" style="display: block;">
        <div class="modal-content" style="width: 400px;">
            <div class="pop-header">
                <h2>일괄변경</h2>
                <button type="button" class="pop-close" @click="$modal.hide('commonModal');"></button>
            </div>
            <div class="pop-body">
                <p>변경된 내용으로 상품정보를 일괄변경 하시겠습니까?</p>
                <div class="blue-box mg0">
                    <ul>
                        <li v-for="item in checkedItemList" :key="item.key">{{ item.name }}</li>
                    </ul>
                </div>
                <hr class="solid" />
                <p class="txt-orange">
                    ※ 주의!<br>
                    - 선택한 항목의 정보가 모두 동일한 내용으로 변경됩니다.<br>
                    - 일괄변경 이후에는 변경 전 정보로 되돌릴 수 없습니다.
                </p>
                <div class="btn-group">
                    <button type="button" class="btn big blue" @click="confirm">확인</button>
                    <button type="button" class="btn big darkgray" @click="$modal.hide('commonModal');">취소</button>
                </div>
            </div>
        </div>
    </div>
    <!-- /일괄변경 alert 팝업 -->
</template>

<script src="./GoodsChangeConfirmPopup.js"></script>