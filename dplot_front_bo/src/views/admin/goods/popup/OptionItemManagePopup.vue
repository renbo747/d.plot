<template>
    <!-- 항목관리 팝업 -->
    <div id="modal-wrap" class="modal" style="display: block;">
        <div class="modal-content" style="width: 560px;">
            <div class="pop-header">
                <h2>항목관리</h2>
                <button type="button" class="pop-close" @click="$modal.hide('commonModal');"></button>
            </div>
            <div class="pop-body">
                <div class="blue-box mg0">
                    <ul>
                        <li>최대 4개까지 추가 가능하며, 항목명은 최대 20자까지 입력 가능합니다.</li>
                    </ul>
                </div>
                <div style="margin: 20px 0;">
                    <div class="dpb" v-for="(item, index) in optionItemList" :key="item.optionno">
                        <input type="text" style="width: calc(100% - 58px);" :placeholder="'항목 '+(index+1)" v-model="item.optionname">
                        <button type="button" class="add" @click="addItem(index)" v-if="index<3"></button>
                        <button type="button" class="del" @click="removeItem(index)" v-if="index>0"></button>
                    </div>
                </div>
                <div class="btn-group">
                    <button type="button" class="btn big blue" @click="sendData">적용</button>
                    <button type="button" class="btn big darkgray" @click="$modal.hide('commonModal');">취소</button>
                </div>
            </div>
        </div>
    </div>
    <!-- /항목관리 팝업 -->
</template>

<script src="./OptionItemManagePopup.js"></script>