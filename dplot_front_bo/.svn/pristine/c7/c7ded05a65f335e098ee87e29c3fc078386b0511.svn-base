<template>
    <div class="content m-leftmenu">
        
        <common-navigator></common-navigator>
        <div class="inner">
            <div class="bar-title">기본정보</div>
            <div class="boxing">
                <div class="form-area">
                    <dl>
                        <dt>노출여부<i class="essential"></i></dt>
                        <dd>
                            <div class="radio_wrap wide">
                                <input type="radio" v-model="info.issearchlink" name="group00" id="group01" value="T" checked=""><label for="group01">노출</label>
                                <input type="radio" v-model="info.issearchlink" name="group00" id="group02" value="F"><label for="group02">미노출</label>
                            </div>
                        </dd>
                    </dl>
                </div>
            </div>
            <div class="bar-title">조건설정</div>
            <div class="form-area">
                <table cellpadding="0" cellspacing="0" class="gray-tb">
                    <colgroup>
                        <col width="170px">
                        <col width="">
                    </colgroup>
                    <tbody>
                        <tr>
                            <th>검색창노출텍스트<i class="essential"></i></th>
                            <td>
                                <input type="text" v-model="info.searchtext" style="width: 100%;" placeholder="검색창 노출 텍스트">
                            </td>
                        </tr>
                        <tr>
                            <th>링크(PC)<i class="essential"></i></th>
                            <td>
                                <input type="text" v-model="info.searchlinkpc" style="width: calc(100% - 100px);" placeholder="배너 클릭 시 연결되는 PC 화면 주소">
                                <input type="checkbox" v-model="info.isnewlinkpc" id="group02" class="ml10" true-value="T" false-value="F"><label for="group21">새창</label>
                            </td>
                        </tr>
                        <tr>
                            <th>링크(모바일)<i class="essential"></i></th>
                            <td>
                                <input type="text" v-model="info.searchlinkmo" style="width: calc(100% - 100px);" placeholder="배너 클릭 시 연결되는 모바일 화면 주소">
                                <input type="checkbox" v-model="info.isnewlinkmo" id="group02" class="ml10" true-value="T" false-value="F"><label for="group21">새창</label>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div class="btn-group">
                <button type="button" v-if="isWrite" class="btn big blue" @click="save">저장</button>
            </div>      
        </div>
    </div>
</template>

<script>
import CommonNavigator from '@views.admin/common/CommonNavigator.vue';
export default {
    name: 'admin.operation.setting.searchlink',
    components: {
        CommonNavigator
    },
    data() {
        return {
            info: {
                issearchlink: 'T',
                searchtext: '',
                searchlinkpc: '',
                searchlinkmo: '',
                isnewlinkpc: 'F',
                isnewlinkmo: 'F',
            },
            isRead: false,
            isWrite: false,
        }
    },
    mounted() {
        this.$http.post('/admin/common/pageAuth/check', {url : this.$options.name}).then(result => {
            this.isRead = (result.data.isread === 'T');
            this.isWrite = (result.data.iswrite === 'T');

            if(this.isRead){
                this.search();
            }
        }).catch(error => {
            this.$util.debug(error);
        });
    },
    methods: {
        search() {
            let param = {};

            this.$http.post('/admin/operation/setting/searchlink/search', param)
            .then(result => {
                let info = result.data.info;
                if(!this.$util.isNull(info)) {
                    this.info.issearchlink = info.issearchlink;
                    this.info.searchtext = info.searchtext;
                    this.info.searchlinkpc = info.searchlinkpc;
                    this.info.searchlinkmo = info.searchlinkmo;
                    this.info.isnewlinkpc = info.isnewlinkpc;
                    this.info.isnewlinkmo = info.isnewlinkmo;
                }
            })
            .catch(error => {
                this.$util.debug(error);
            });
        },
        save() {
            if(this.checkValidation()) {
                let param = this.info;

                if(confirm("저장 하시겠습니까?")) {
                    this.$http.post('/admin/operation/setting/searchlink/save', param)
                    .then(result => {
                        if(result.statusCode === 200) {
                            alert("저장 되었습니다.");
                            this.search();
                        }
                    })
                    .catch(error => {
                        this.$util.debug(error);
                    });
                }
            }
        },
        checkValidation() {
            let msg = '';
            let valid = [
                {field: 'info.issearchlink', type: 'S', name: '노출여부', required: true},
                {field: 'info.searchtext', type: 'I', name: '검색창노출텍스트', required: true},
                {field: 'info.searchlinkpc', type: 'I', name: '링크(PC)', required: true},
                {field: 'info.searchlinkmo', type: 'I', name: '링크(모바일)', required: true},
            ];

            msg = this.$util.validMsg(this.$data, valid);
            if(!this.$util.isNull(msg)){
                alert(msg);
                return false;
            }

            return true;
        }
    },
}
</script>

<style>

</style>