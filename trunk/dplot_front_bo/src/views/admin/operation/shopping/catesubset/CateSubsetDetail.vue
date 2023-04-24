<template>
    <div id="modal-wrap" class="modal" style="display: block;">
        <div class="modal-content" style="width: 1400px;">
            <div class="pop-header">
                <h2>배너대용문구 상세</h2>
                <button type="button" class="pop-close" @click="onClose"></button>
            </div>
            <div class="pop-body">
                <div class="gray-box mg0 clearfix">
                    <div class="fl">
                        <span>작성자 : {{info.reguserid}}</span>
                    </div>
                    <div class="fr txt-gray">
                        <span>등록일 : {{info.regdate}}</span>
                        <span class="left-bar">수정일 : {{info.moddate}}</span>
                    </div>
                </div>
                <div class="bar-title">기본정보</div>
                <div class="boxing">
                    <div class="form-area">
                        <dl>
                            <dt>노출상태<i class="essential"></i></dt>
                            <dd>
                                <div class="radio_wrap wide">
                                    <input type="radio" name="group000" id="group001" v-model="info.isdisplay" value="T" checked/><label for="group001">노출</label>
                                    <input type="radio" name="group000" id="group002" v-model="info.isdisplay" value="F"/><label for="group002">미노출</label>
                                </div>
                            </dd>
                        </dl>
                        <dl>
                            <dt>설명</dt>
                            <dd><input type="text" v-model="info.desc" style="width: 100%" placeholder="배너대용문구 설명" /></dd>
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
                                <th>노출채널<i class="essential"></i></th>
                                <td>
                                    <div class="check-wrap">
                                        <input type="checkbox" v-model="checkObj.isallmuappch" id="isallmuappch" true-value="T" false-value="F" @change="checkAllAppchtype">
                                        <label for="isallmuappch">전체</label>
                                    </div>
                                    <div class="check-wrap" v-for="item in commonCode.muappchtype" :key="item.cmcode">
                                        <input type="checkbox" v-model="checkObj.muappchtypearr" :id="'muappchtype_'+item.cmcode" true-value="[]" :value="item.cmcode">
                                        <label :for="'muappchtype_'+item.cmcode">{{item.codename}}</label>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th>대상회원유형<i class="essential"></i></th>
                                <td>
                                    <div class="check-wrap">
                                        <input type="checkbox" v-model="checkObj.isallmumember" id="isallmumember" true-value="T" false-value="F" @change="checkAllMembertype">
                                        <label for="isallmumember">전체</label>
                                    </div>
                                    <div class="check-wrap" v-for="item in commonCode.mumembertype" :key="item.cmcode">
                                        <input type="checkbox" v-model="checkObj.mumembertypearr" :id="'mumembertype_'+item.cmcode" true-value="[]" :value="item.cmcode">
                                        <label :for="'mumembertype_'+item.cmcode">{{item.codename}}</label>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th>대상회원등급<i class="essential"></i></th>
                                <td>
                                    <div class="check-wrap">
                                        <input type="checkbox" v-model="checkObj.isallmumemlv" id="isallmumemlv" true-value="T" false-value="F" @change="checkAllMemLvtype">
                                        <label for="isallmumemlv">전체</label>
                                    </div>
                                    <div class="check-wrap" v-for="item in commonCode.mumemlvtype" :key="item.cmcode">
                                        <input type="checkbox" v-model="checkObj.mumemlvtypearr" :id="'mumemlvtype_'+item.cmcode" true-value="[]" :value="item.cmcode">
                                        <label :for="'mumemlvtype_'+item.cmcode">{{item.codename}}</label>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th>문구(PC)<i class="essential"></i></th>
                                <td>
                                    <div>
                                        <CommonEditor ref="pceditor"/>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th>문구(모바일)<i class="essential"></i></th>
                                <td>
                                    <div>
                                        <CommonEditor ref="moeditor"/>
                                    </div>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="btn-group">
                    <button type="button" class="btn big blue" @click="goSave">저장</button>
                    <button type="button" class="btn big darkgray" @click="onClose">취소</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import CommonEditor from "@views.admin/common/CommonEditor";
export default {
    name: 'admin.operation.shopping.subset.detail',
    props: {
        commonCodeList: Object,
        idx: Number,
    },
    components: {
        CommonEditor
    },
    data() {
        return {
            info: {},
            checkObj: {
                muappchtypearr: [],
                isallmuappch: 'F',
                mumembertypearr: [],
                isallmumember: 'F',
                mumemlvtypearr: [],
                isallmumemlv: 'F',
            },
            commonCode: this.commonCodeList,
            isRead: false,
            isWrite: false,
        }
    },
    mounted() {
        this.$http.post('/admin/common/pageAuth/check', {url : this.$options.name}).then(result => {

            this.isRead = (result.data.isread === 'T');
            this.isWrite = (result.data.iswrite === 'T');
      
            if(this.isRead){
                let param = {
                    subidx : this.idx
                }

                this.$http.post("/admin/operation/shopping/subset/detail", param)
                    .then(result =>{
                        this.info = result.data.info;
                        this.setDateInit();
                    })
                    .catch(error => {
                        this.$util.debug(error);
                    });
            }else{
                alert('페이지 접근 권한이 없습니다.');
                this.onClose();
            }

            if(!this.isWrite){
                let buttons = this.$el.getElementsByTagName('button');

                for(let button of buttons){
                    if(button.className !== 'pop-close') {
                        button.style.display = 'none';
                        button.disabled = true;
                    }
                }
            }
        }).catch(error => {
            this.$util.debug(error);
        });
    },
    methods: {
        setDateInit() {
            this.$refs.pceditor.content = this.info.content;
            this.$refs.moeditor.content = this.info.mobilecontent;

            if(!this.$util.isNull(this.info.muappchtype)) {
                this.checkObj.muappchtypearr = this.info.muappchtype.split(',');
            }
            if(!this.$util.isNull(this.info.mumembertype)) {
                this.checkObj.mumembertypearr = this.info.mumembertype.split(',');
            }
            if(!this.$util.isNull(this.info.mumemlvtype)) {
                this.checkObj.mumemlvtypearr = this.info.mumemlvtype.split(',');
            }
        },
        goSave() {
            if(this.checkValidation()) {
                let params = this.info;

                let param = {
                    idx: this.idx,
                    isdisplay : params.isdisplay,
                    isloading: false
                }
                
                param.overmsg = '저장하시겠습니까?';
                param.zeromsg = '수정';
        
                this.$http.post("/admin/operation/shopping/subset/check", param)
                .then(result => {
                    if (result.statusCode == 200) {
                        params.msg = result.data.msg;
                        this.update(params);
                    }
                })
                .catch(error => {
                    this.$util.debug(error);
                })
            }
        },
        update(params) {
            if(confirm(params.msg)){
                this.$http.post("/admin/operation/shopping/subset/modify", params)
                .then(result => {
                    if(result.statusCode === 200){
                        alert("저장되었습니다.");
                        this.onClose(true);
                    }
                })
                .catch(error => {
                    this.$util.debug(error);
                });
            }   
        },
        checkValidation() {
            this.setEditorText();

            let msg = '';
            let valid = [
                {field: 'info.content', type: 'I', name: '문구(PC)', required: true},
                {field: 'info.mobilecontent', type: 'I', name: '문구(모바일)', required: true},
                {field: 'info.muappchtype', type: 'S', name: '노출채널', required: true},
                {field: 'info.mumembertype', type: 'S', name: '대상회원유형', required: true},
                {field: 'info.mumemlvtype', type: 'S', name: '대상회원등급', required: true},
            ];

            msg = this.$util.validMsg(this.$data, valid);
            if(!this.$util.isNull(msg)){
                alert(msg);
                return false;
            }

            return true;
        },
        // 에디터 내용 가져오기
        setEditorText() {
            if (this.$util.isNull(this.$refs.pceditor.content)) {
                this.info.content = ''
            } else {
                this.info.content = this.$refs.pceditor.content;
            }
            
            if (this.$util.isNull(this.$refs.moeditor.content)) {
                this.info.mobilecontent = ''
            } else {
                this.info.mobilecontent = this.$refs.moeditor.content;
            }
        },
        // 조회조건 - 적용채널 전체체크
        checkAllAppchtype: function() {
            let isAllCheck = this.checkObj.isallmuappch;
            this.checkObj.muappchtypearr = [];
            if (isAllCheck == 'T') {
                for(let type of this.commonCode.muappchtype){
                    this.checkObj.muappchtypearr.push(type.cmcode);
                }
            }
        },
        // 조회조건 - 적용채널 전체체크
        checkAllMembertype: function() {
            let isAllCheck = this.checkObj.isallmumember;
            this.checkObj.mumembertypearr = [];
            if (isAllCheck == 'T') {
                for(let type of this.commonCode.mumembertype){
                    this.checkObj.mumembertypearr.push(type.cmcode);
                }
            }
        },
        // 조회조건 - 적용채널 전체체크
        checkAllMemLvtype: function() {
            let isAllCheck = this.checkObj.isallmumemlv;
            this.checkObj.mumemlvtypearr = [];
            if (isAllCheck == 'T') {
                for(let type of this.commonCode.mumemlvtype){
                    this.checkObj.mumemlvtypearr.push(type.cmcode);
                }
            }
        },
        onClose(isreload){
            this.info = {};
            // 팝업 닫기
            if (typeof(isreload)==='boolean' && isreload) {
                this.$emit('closeDetail', isreload);
            } else {
                this.$emit('closeDetail');
            }
        },
    },
    watch: {
        'checkObj.muappchtypearr': function(value){
            if (value.length < this.commonCode.muappchtype.length) {
                this.checkObj.isallmuappch = 'F';
            } else {
                this.checkObj.isallmuappch = 'T';
            }
            this.info.muappchtype = this.checkObj.muappchtypearr.join();
        },
        'checkObj.mumembertypearr': function(value){
            if (value.length < this.commonCode.mumembertype.length) {
                this.checkObj.isallmumember = 'F';
            } else {
                this.checkObj.isallmumember = 'T';
            }
            this.info.mumembertype = this.checkObj.mumembertypearr.join();
        },
        'checkObj.mumemlvtypearr': function(value){
            if (value.length < this.commonCode.mumemlvtype.length) {
                this.checkObj.isallmumemlv = 'F';
            } else {
                this.checkObj.isallmumemlv = 'T';
            }
            this.info.mumemlvtype = this.checkObj.mumemlvtypearr.join();
        },
    }
}
</script>

<style>

</style>