<template>
    <div class="content m-leftmenu">
        <common-navigator></common-navigator>
        <DuplBannedPopup v-if="showdupl" :dupllist="dupllist" v-on:closeDupl="closeDupl"></DuplBannedPopup>
        <div class="inner">
            <div class="boxing search-area">
                <dl>
                    <dt>금칙어 등록</dt>
                    <dd>
                        <input type="text" v-model="addwordstr" placeholder="단어당 20자 이내" /><button type="button" v-if="isWrite" class="btn blue" @click="addBan">등록</button>
                        <ul class="dot-list mt20">
                            <li>한번에 여러 개의 금칙어 등록을 원할 시에는 단어 사이에 콤마(,)로 구분</li>
                            <li>하나의 금칙어는 최대 20자까지 등록 가능</li>
                            <li>특수문자는 입력 불가</li>
                        </ul>
                    </dd>
                </dl>
            </div>
            <div class="caption-group mt20 clearfix">
                <div class="total-group fl">
                    <span class="total">등록된 금칙어 <strong>{{ list.length }}</strong>건</span>
                </div>
                <div class="btn-group fr" v-if="isRead">
                    <input type="search" v-model="searchword" placeholder="금칙어 조회">
                    <button type="button" class="btn-search bg" style="margin-left: -3px;" @click="searchList">검색</button>
                    <select v-model="sort" @change="searchList">
                        <option value="asc">가나다 순</option>
                        <option value="desc">가나다 역순</option>
                    </select>
                </div>
            </div>
            <div class="boxing forbiddenword-group clearfix">
                <div v-for="(row, n) in list" :key="n">{{ row.bannedword }}<button type="button" class="file-del" @click="removeBan(n)"></button></div>
            </div>
        </div>
    </div>
</template>

<script>
import CommonNavigator from '@views.admin/common/CommonNavigator.vue';
import DuplBannedPopup from "./DuplBannedPopup.vue";
export default {
    name: 'admin.operation.setting.bannedlist',
    components: {
        CommonNavigator,
        DuplBannedPopup
    },
    data() {
        return {
            addwordstr: '',
            list: [],
            searchword: '',
            sort: 'asc',
            dupllist: [],
            showdupl: false,
            isRead: false,
            isWrite: false,
        }
    },
    mounted() {
        this.$http.post('/admin/common/pageAuth/check', {url : this.$options.name, isloading: false}).then(result => {
            this.isRead = (result.data.isread === 'T');
            this.isWrite = (result.data.iswrite === 'T');

            if(this.isRead){
                this.searchList();
            }
        }).catch(error => {
            this.$util.debug(error);
        });
    },
    methods: {
        // 검색
        searchList() {
            let param = {
                searchword: this.searchword,
                sort: this.sort,
            };

            this.$http.post("/admin/operation/setting/banned/search", param)
            .then(result => {
                let data = result.data;
                this.list = data.list;
                this.pagingData.listCount = data.state.totalcnt;
            })
            .catch(error => {
                this.$util.debug(error);
            });
        },
        removeBan(index) {
            let param = {
                bannedword: this.list[index].bannedword
            }

            if(confirm("삭제 하시겠습니까?")) {
                this.$http.post("/admin/operation/setting/banned/remove", param)
                .then(result => {
                    if(result.statusCode === 200) {
                        alert("삭제 되었습니다.");
                        this.searchList();
                    }
                })
                .catch(error => {
                    this.$util.debug(error);
                });
            }
        },
        addBan() {
            if(this.checkValidation()) {
                let param = {
                    list: this.addwordstr.split(","),
                    isloading: false,
                }
    
                this.$http.post("/admin/operation/setting/banned/save", param)
                .then(result => {
                    if(result.statusCode === 200) {
                        let data = result.data;
                        if(data.check === 'T') {
                            alert("저장 되었습니다.");
                            this.addwordstr = '';
                            this.searchList();
                        } else {
                            this.openDupl(data.list);
                        }
                    }
                })
                .catch(error => {
                    this.$util.debug(error);
                });
            }
        },
        checkValidation() {
            let addlist = this.addwordstr.split(",");
            
            if(addlist.length === 0){
                alert("추가할 금칙어를 입력해주세요.");
                return false;
            }

            for(let i=0; i<addlist.length; i++) {
                if(addlist[i].length > 20) {
                    alert("하나의 금칙어는 최대 20자까지 등록 가능합니다.");
                    return false;
                }
            }

            return true;
        },
        openDupl(list) {
            this.dupllist = list;
            this.showdupl = true;
        },
        closeDupl() {
            this.showdupl = false;
        }
    },
    watch: {
        'addwordstr': function(value, oldValue) {
            if(this.$util.isNull(value)) return;
            let check =  /[`~!@#$%^&*()_|+\-=?;:'"\s\\.<>/]/gi;
            if(check.test(value)){
                return this.addwordstr = oldValue;
            }else{
                return this.addwordstr = value;
            }
        },
    }
}
</script>

<style>

</style>