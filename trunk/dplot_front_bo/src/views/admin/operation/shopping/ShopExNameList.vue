<template>
    <div class="content m-leftmenu">
        <common-navigator></common-navigator>
        <div class="inner">
            <!-- 전시영역 -->
            <template v-for="(row, n) in exlist"> 
            <div :key="n+'a'" class="bar-title" >전시영역 {{n+1}}</div>
            <table :key="n"  v-if="isRead" cellpadding="0" cellspacing="0" class="gray-tb">
                <colgroup>
                    <col width="180px">
                    <col width="">
                </colgroup>
                <tbody>
                    <tr>
                        <th>전시영역명<i class="essential"></i></th>
                        <td>
                            <input type="text" v-model="exlist[n].exhibitname" style="width: 100%;" placeholder="전시영역 제목">
                        </td>
                    </tr>
                    <tr>
                        <th>소개문구 제공여부<i class="essential"></i></th>
                        <td>
                            <div class="radio_wrap wide">
                                <input type="radio" v-model="exlist[n].isintro" :name="'group00'+n" :id="'group01'+n" value="T" checked><label :for="'group01'+n">제공함</label>
                                <input type="radio" v-model="exlist[n].isintro" :name="'group00'+n" :id="'group02'+n" value="F"><label :for="'group02'+n">제공안함</label>
                            </div>
                        </td>
                    </tr>
                    <tr v-show="exlist[n].isintro === 'T'">
                        <th>전시영역 소개문구<i class="essential"></i></th>
                        <td>
                            <input type="text" v-model="exlist[n].intro" style="width: 100%;" placeholder="소개문구 텍스트">
                        </td>
                    </tr>
                </tbody>
            </table>
            </template>
           <!-- /전시영역 -->
            <div class="btn-group">
                <button type="button" v-if="isWrite" class="btn big blue" @click="onSave">저장</button>
            </div>
        </div>
    </div>
</template>

<script>
import CommonNavigator from '@views.admin/common/CommonNavigator.vue';
export default {
    name: 'admin.operation.shopping.shopexname',
    components: {
        CommonNavigator  
    },
    data() {
        return {
            exlist: [],
            isRead: false,
            isWrite: false,
        }
    },
    mounted() {
        this.$http.post('/admin/common/pageAuth/check', {url : this.$options.name, isloading: false}).then(result => {
            this.isRead = (result.data.isread === 'T');
            this.isWrite = (result.data.iswrite === 'T');

            if(this.isRead){
                this.onSearch();
            }
        }).catch(error => {
            this.$util.debug(error);
        });
    },
    methods: {
        onSearch() {
            let param = {};

            this.$http.post("/admin/operation/shopping/exname/search", param)
            .then(result => {
                let data = result.data;
                this.exlist = data.list;
            })
            .catch(error => {
                this.$util.debug(error);
            });
        },
        onSave() {
            if(this.validation()) {
                this.exlist.forEach(obj => {
                    if(obj.isintro === 'F') {
                        obj.intro = '';
                    }
                });
    
                let param = {
                    list : this.exlist,
                }
    
                if(confirm("저장 하시겠습니까?")) {
                    this.$http.post("/admin/operation/shopping/exname/save", param)
                    .then(result => {
                        if(result.statusCode === 200) {
                            alert("저장되었습니다.");
                            this.onSearch();
                        }
                    })
                    .catch(error => {
                        this.$util.debug(error);
                    });
                }
            }
        },
        validation() {
            for(let i = 0; i < this.exlist.length; i++) {
                let obj = this.exlist[i];
                if(this.$util.isNull(obj.exhibitname)) {
                    alert("전시영역 "+(i+1)+"의 전시영역명을 입력해주세요.");
                    return false;
                }
                if(this.$util.isNull(obj.isintro)) {
                    alert("전시영역 "+(i+1)+"의 소개문구여부를 선택해주세요.");
                    return false;
                }
                if(obj.isintro === 'T' && this.$util.isNull(obj.intro)) {
                    alert("전시영역 "+(i+1)+"의 소개문구를 입력해주세요.");
                    return false;
                }
            }

            return true;
        }
    },
}
</script>

<style>

</style>