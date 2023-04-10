<template>
    <div class="content m-leftmenu">
        <common-navigator></common-navigator>
        <div class="inner">
            <div class="caption-group clearfix">
                <div class="total-group fl">
                    <div class="bar-title">스플래시이미지 목록</div>
                </div>
                <div class="btn-group fr">
                    <button type="button" class="btn big blue" v-if="isWrite" @click="addSplash">추가</button>
                </div>
            </div>
            <table cellpadding="0" cellspacing="0" class="data-tb align-c">
                <caption>스플래시이미지관리 목록</caption>
                <colgroup>
                    <col width="4%" /><!-- No -->
                    <col width="" /><!-- 스플래시이미지 -->
                    <col width="" /><!-- 삭제 -->
                </colgroup>
                <thead>
                    <tr>
                        <th>노출</th>
                        <th>스플래시이미지</th>
                        <th>삭제</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="(row, index) in this.list" :key="index">
                        <td><input type="radio" v-model="checkidx" :value="index" @change="changeDisplay(index)"/></td>
                        <td>
                            <div class="img-with-text" style="width: 144px;">
                                <div class="img-thumb" style="width: 144px; height: 304px;" :class="{'no-image': $util.isNull(files.filelist[index])}"><!-- 이미지 없는 경우 no-image 클래스 추가 -->
                                    <img style="width: 144px; height: 304px;" :src="imgPreView[index]" alt="이미지" v-if="!$util.isNull(files.filelist[index])">
                                </div>
                                <input type="file" :ref="'pcimgfile'+index" @change="handleFileUpload('pcimgfile'+index,index)" accept="image/jpeg, image/png" hidden/>
                                <button type="button" class="btn blue-line mt10" style="width: 100%;" @click="fileAttach('pcimgfile'+index)">{{$util.isNull(files.filelist[index]) ? '파일 올리기' : '변경'}}</button>
                            </div>
                            <div class="img-with-text text">
                                <p class="txt-orange"><i class="icon-alert"></i>모바일 리스팅 및 와이드형 화면에 노출되는 이미지를 업로드 해 주세요.</p>
                                <p class="txt-orange"><i class="icon-alert"></i>사이즈: 최대 1440*3040 / 용량: 10MB 이하 / 파일 : JPG, JPEG, PNG</p>
                            </div>
                        </td>
                        <td><button type="button" :disabled="row.isdisplay === 'T'" class="btn blue-line" @click="removeSplash(index)">삭제</button></td>
                    </tr>           
                </tbody>
            </table>  
            <div class="btn-group">
                <button type="button" v-if="isWrite" class="btn big blue" @click="save">저장</button>
            </div>
        </div>
    </div>
</template>

<script>
import CommonNavigator from '@views.admin/common/CommonNavigator.vue';
export default {
    name: 'admin.operation.setting.splashimglist',
    components: {
        CommonNavigator
    },
    data() {
        return {
            list:[],
            removelist: [],
            files: {
                filelist: [],
                addlist: [],
            },
            removefilelist : [],
            imgPreView: [],
            isnew: true,
            checkidx: '',
            isRead: false,
            isWrite: false,
        }
    },
    mounted() {
        this.$http.post('/admin/common/pageAuth/check', {url : this.$options.name, isloading: false}).then(result => {
            this.isRead = (result.data.isread === 'T');
            this.isWrite = (result.data.iswrite === 'T');

            if(this.isRead){
                this.init();
            }
        }).catch(error => {
            this.$util.debug(error);
        });
    },
    methods: {
        init(){
            this.list = [];
            this.removelist = [];
            this.files = this.$options.data().files;
            this.removefilelist = [];
            this.imgPreView = [];
            this.isnew = true;
            this.checkidx = '';

            this.search();
        },
        search() {
            let param = {};

            this.$http.post("/admin/operation/setting/splashimg/search", param)
            .then(result => {
                let data = result.data;
                this.list = data.list;
                if(this.list.length > 0) {
                    this.isnew = false;
                    this.list.forEach((m,index) => {
                        if(m.isdisplay === 'T') {
                            this.checkidx = index;
                        }
                    });
                    this.files.filelist = data.filelist;
                    this.files.filelist.forEach(m => {
                        this.files.addlist.push('');
                        this.imgPreView.push(m.fullpath);
                    });
                } else {
                    this.addSplash();
                }
            })
            .catch(error => {
                this.$util.debug(error);
            });
        },
        save() {
            if(this.list.length === 0) {
                alert("저장할 항목이 없습니다.");
                return ;
            }

            for(let i=0; i<this.files.filelist.length; i++) {
                if(this.$util.isNull(this.files.filelist[i])) {
                    alert("스플래시 이미지를 등록해주세요.");
                    return ;
                }
            }

            let params = {
                list: this.list,
            }

            let files = [];

            this.files.addlist.forEach((m,index) => {
                if(!this.$util.isNull(m)) {
                    files.push({key:'file'+index, file: this.files.addlist[index].file});
                }
            });

            if(this.removefilelist.length > 0){
                params.removefilelist = this.removefilelist;
            }

            if(this.removelist.length > 0) {
                params.removelist = this.removelist;
            }

            params.files = files;

            if(confirm("저장 하시겠습니까?")) {
                this.$http.post("/admin/operation/setting/splashimg/save", params)
                .then(result => {
                    if(result.statusCode === 200) {
                        alert("저장되었습니다.");
                        this.init();
                    }
                })
                .catch(error => {
                    this.$util.debug(error);
                });
            }
        },
        // 첨부파일(탐색기 열기)
        fileAttach: function(fileTypeKey) {
            if (Array.isArray(this.$refs[fileTypeKey])) {
                this.$refs[fileTypeKey][0].click();
            } else {
                this.$refs[fileTypeKey].click();
            }
        },
        // 가져온 파일 세팅
        handleFileUpload: function(fileTypeKey, index) {
            let file = this.$refs[fileTypeKey][0];
            if (this.$util.isNull(file)) {
                return;
            }
            
            if(Object.prototype.hasOwnProperty.call(this.files.filelist[index],'idx')){
                this.removefilelist.push(this.files.filelist[index].idx);
            }

            let fileType = ['image/png','image/jpeg', 'image/png'];
            if(!fileType.includes(file.files[0].type)){
                alert('jpg, jpeg, png파일만 첨부 가능합니다.');
                file.value = null;
                this.files.filelist[index] = '';
                this.files.addlist[index] = '';
                return false;
            }
            if(file.files[0].size > 1048576 * 10){
                alert('파일 최대 크기는 10MB를 초과 할 수 없습니다.');
                file.value = null;
                this.files.filelist[index] = '';
                this.files.addlist[index] = '';
                return false;
            }

            let fileObj = {
                file: file.files[0],
                iscreated: true
            }

            this.$set(this.files.filelist,index,fileObj);
            this.$set(this.files.addlist,index,fileObj);
            this.$set(this.imgPreView,index,URL.createObjectURL(fileObj.file));
        },
        addSplash() {
            let obj =    {
                isdisplay: 'F',
            }

            if(this.list.length === 0) {
                obj.isdisplay = 'T';
                this.checkidx = 0;
            } else { 
                this.checkidx++;
            }

            this.list.unshift(obj);
            this.files.filelist.unshift('');
            this.files.addlist.unshift('');
            this.imgPreView.unshift('');
        },
        removeSplash(index) {
            if(confirm("삭제 하시겠습니까?")) {
                if(Object.prototype.hasOwnProperty.call(this.list[index],'splidx')) {
                    this.removelist.push(this.list[index].splidx);
                    if(Object.prototype.hasOwnProperty.call(this.files.filelist[index],'idx')){
                        this.removefilelist.push(this.files.filelist[index].idx);
                    }
                }

                if(index < this.checkidx) {
                    this.checkidx--;
                }

                this.list.splice(index,1);
                this.files.filelist.splice(index,1);
                this.files.addlist.splice(index,1);
                this.imgPreView.splice(index,1);
            }
        },
        changeDisplay(n) {
            this.list.forEach((m,index) => {
                if(index === n) {
                    m.isdisplay = 'T';
                } else {
                    m.isdisplay = 'F';
                }
            });
        }
    },
    
}
</script>

<style>

</style>