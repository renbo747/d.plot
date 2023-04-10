<template>
    <div id="modal-wrap" class="modal" style="display: block;">
        <div class="modal-content" style="width: 500px;">
            <div class="pop-header">
                <h2>단어중복</h2>
                <button type="button" class="pop-close" @click="onClose"></button>
            </div>
            <div class="pop-body">
                <div class="mb10">중복된 단어가 존재합니다. 중복된 단어를 제거 후 다시 등록하시기 바랍니다.</div>
                <div class="red-box mg0">           
                    <ul>
                        <li v-for="(row, index) in list" :key="index">{{ row.bannedword }}</li>
                    </ul>
                </div>
                <div class="btn-group mt10">
                    <button type="button" class="btn big blue" @click="onClose">확인</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: 'admin.operation.setting.duplbannedpopup',
    props: {
        dupllist: Array
    },
    data() {
        return {
            list: this.dupllist,
        }
    },
    methods: {
        onClose() {
            this.$emit('closeDupl');
        },
    },
}
</script>

<style>

</style>