<template>
    <div id="modal-wrap" class="modal" style="display: block;">
        <div class="modal-content" style="width: 1200px;">
            <div class="pop-header">
                <h2>공지사항 상세</h2>
                <button type="button" class="pop-close" @click="onClose"></button>
            </div>
            <div class="pop-body">
                <div class="gray-box mg0 clearfix">
                    <div class="fl">
                        <span>작성자 : {{info.writer}}</span>
                        <span class="left-bar">조회수 : {{info.hits}}</span>
                    </div>
                    <div class="fr txt-gray">
                        <span>등록일 : {{info.regdate}}</span>
                        <span class="left-bar">수정일 : {{info.moddate}}</span>
                    </div>
                </div>
                <div class="bar-title mt10">기본정보</div>
                <div class="boxing">
                    <div class="form-area">
                        <dl>
                            <dt>노출상태</dt>
                            <dd>
                                <div class="radio_wrap wide">
                                    <input type="radio" name="group00regist" id="group01regist" v-model="info.isdisplay" value="T" checked/><label for="group01regist">노출</label>
                                    <input type="radio" name="group00regist" id="group02regist" v-model="info.isdisplay" value="F" /><label for="group02regist">미노출</label>
                                </div>
                            </dd>
                        </dl>
                        <dl>
                            <dt>제목<i class="essential"></i></dt>
                            <dd><input type="text" style="width: 100%" v-model="info.subject" placeholder="공지사항 제목" /></dd>
                        </dl>
                        <dl>
                            <dt>설명</dt>
                            <dd><input type="text" style="width: 100%" v-model="info.notice_desc" placeholder="공지사항 설명" /></dd>
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
                                <th>예약여부<i class="essential"></i></th>
                                <td>
                                    <div class="radio_wrap wide dpib">
                                        <input type="radio" name="group01" id="group11" v-model="info.isrightnow" value="T" checked/><label for="group11">즉시등록</label>
                                        <input type="radio" name="group01" id="group12" v-model="info.isrightnow" value="F" /><label for="group12">예약</label>
                                    </div>
                                    <CommonDatePickerFromTo
                                        :fromYYYYMMDD="checkObj.startYYYYMMDD"
                                        :fromHH="checkObj.startHH"
                                        :fromMM="checkObj.startMi"
                                        :use-to="false"
                                        :from-disable="info.isrightnow === 'T'"
                                        @getDate="changeStartDate"
                                    />
                                    <span>분 부터</span>
                                </td>
                            </tr>
                            <tr>
                                <th>노출채널</th>
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
                                <th>대상유형</th>
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
                                <th>상단고정<i class="essential"></i></th>
                                <td>
                                    <div class="radio_wrap wide dpib">
                                        <input type="radio" name="group04" id="group41" v-model="info.istopfix" value="F" checked/><label for="group41">고정하지 않음</label>
                                        <input type="radio" name="group04" id="group42" v-model="info.istopfix" value="T"/><label for="group42">게시일로부터</label>
                                    </div>
                                    <CommonDatePickerFromTo
                                        :fromYYYYMMDD="checkObj.endYYYYMMDD"
                                        :fromHH="checkObj.endHH"
                                        :fromMM="checkObj.endMi"
                                        :use-to="false"
                                        :from-disable="info.istopfix === 'F'"
                                        @getDate="changeEndDate"
                                    />
                                    <span>분까지 상단 고정</span>
                                </td>
                            </tr>
                            <tr>
                                <th>내용(PC)<i class="essential"></i></th>
                                <td>
                                    <div>
                                        <CommonEditor ref="pcEditor"/>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th>내용(모바일)<i class="essential"></i></th>
                                <td>
                                    <div class="mb10">
                                        <button type="button" class="btn blue-line" @click="copyByContent">PC 내용을 복사</button>
                                    </div>
                                    <div class="mt10">
                                        <CommonEditor ref="mobileEditor"/>
                                    </div>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="btn-group">
                    <button type="button" class="btn big blue" @click="onSave">저장</button>
                    <button type="button" class="btn big darkgray" @click="onClose">취소</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import CommonDatePickerFromTo from "@views.admin/common/CommonDatePickerFromTo";
import CommonEditor from "@views.admin/common/CommonEditor";
export default {
    name: 'admin.operation.notice.detail',
    props: {
        commonCodeList: Object,
        boardIdx: Number
    },
    components: {
        CommonDatePickerFromTo,
        CommonEditor
    },
    data() {
        return {
            info: {
                isdisplay: 'T',
                subject: '',
                notice_desc: '',
                content: '',
                mobilecontent: '',
                isrightnow: 'T',
                poststtime: '',
                topedtime: '',
                muappchtype: '',
                mumembertype: '',
                istopfix: 'F',
                writer: '',
            },
            checkObj: {
                muappchtypearr: [],
                isallmuappch: 'F',
                mumembertypearr: [],
                isallmumember: 'F',
                startYYYYMMDD: '',
                startHH: '',
                startMi:'',
                endYYYYMMDD: '',
                endHH: '',
                endMi: '',
            },
            idx: this.boardIdx,
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
                if(!this.isWrite){
                    let buttons = this.$el.getElementsByTagName('button');
    
                    for(let button of buttons){
                        if(button.className !== 'pop-close') {
                            button.style.display = 'none';
                            button.disabled = true;
                        }
                    }
                }
                this.onSearch();
            }else {
                alert('페이지 접근 권한이 없습니다.');
                this.onClose();
            }

        }).catch(error => {
            this.$util.debug(error);
        });
    },
    methods: {
        setInit() {
            if(this.info.isrightnow === 'F') {
                this.checkObj.startYYYYMMDD = this.$util.getFormatDate(this.info.poststtime.substring(0,8),'-');
                this.checkObj.startHH = this.info.poststtime.substring(8,10);
                this.checkObj.startMi = this.info.poststtime.substring(10,12);
            } else {
                this.checkObj.startYYYYMMDD = '';
                this.checkObj.startHH = '';
                this.checkObj.startMi = '';
            }

            if(this.info.istopfix === 'F') {
                this.checkObj.endYYYYMMDD = this.$util.getAddDate(this.info.poststtime.substring(0,8), 7, '-');
                this.checkObj.endHH = this.info.poststtime.substring(8,10)
                this.checkObj.endMi = '59';
            } else {
                this.checkObj.endYYYYMMDD = this.$util.getFormatDate(this.info.topedtime.substring(0,8),'-');
                this.checkObj.endHH = this.info.topedtime.substring(8,10);
                this.checkObj.endMi = this.info.topedtime.substring(10,12);
            }

            if(!this.$util.isNull(this.info.muappchtype)) {
                this.checkObj.muappchtypearr = this.info.muappchtype.split(',');
            }
            if(!this.$util.isNull(this.info.mumembertype)) {
                this.checkObj.mumembertypearr = this.info.mumembertype.split(',');
            }
            
            this.$refs.pcEditor.content = this.info.content;
            this.$refs.mobileEditor.content = this.info.mobilecontent;
        },
        onSearch() {
            let param = {
                idx: this.idx
            }

            this.$http.post('/admin/operation/notice/detail',param)
            .then(result => {
                if(result.statusCode === 200) {
                    let data = result.data;
                    this.info = data.info;

                    this.setInit();
                }
            })
            .error(result => {
                this.$util.debug(result);
            })
        },
        // 에디터 내용 가져오기
        setEditorText() {
            if (this.$util.isNull(this.$refs.pcEditor.content)) {
                this.info.content = ''
            } else {
                this.info.content = this.$refs.pcEditor.content;
            }

            if (this.$util.isNull(this.$refs.mobileEditor.content)) {
                this.info.mobilecontent = ''
            } else {
                this.info.mobilecontent = this.$refs.mobileEditor.content;
            }
        },
        // PC 내용 복사
        copyByContent() {
            this.$refs.mobileEditor.content = this.$refs.pcEditor.content;
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
        checkValidation() {
            this.setEditorText();

            let msg = '';
            let valid = [
                {field: 'info.subject', type: 'I', name: '제목', required: true},
                {field: 'info.muappchtype', type: 'S', name: '적용채널', required: true},
                {field: 'info.mumembertype', type: 'S', name: '대상유형', required: true},
                {field: 'info.content', type: 'I', name: '내용(PC)', required: true},
                {field: 'info.mobilecontent', type: 'I', name: '내용(모바일)', required: true},
            ];

            msg = this.$util.validMsg(this.$data, valid);
            if(!this.$util.isNull(msg)){
                alert(msg);
                return false;
            }

            // 확인필요
            if(this.info.isrightnow === 'T') {
                if(this.info.oriisrightnow === 'F'){
                    this.info.poststtime = this.$util.getDate12NoSeparator();
                } else {
                    this.info.poststtime = this.info.oripoststtime;
                }
            } else { 
                this.info.poststtime = this.checkObj.startYYYYMMDD.replaceAll('-','') + this.checkObj.startHH + this.checkObj.startMi;
                if(this.info.poststtime.length < 12) {
                    alert("예약시간을 선택해주세요.");
                    return false;
                }
                if(this.info.poststtime < this.$util.getDate12NoSeparator()) {
                    alert("현재시간 이후로 예약시간을 설정해주세요.");
                    return false;
                }
            }

            if(this.info.istopfix === 'T') {
                this.info.topedtime = this.checkObj.endYYYYMMDD.replaceAll('-','') + this.checkObj.endHH + this.checkObj.endMi;
                if(this.info.topedtime.length < 12) {
                    alert("상단고정 종료일을 선택해주세요.");
                    return false;
                }
                if(this.info.poststtime >= this.info.topedtime) {
                    alert("상단고정 종료일을 게시시작일 이후로 선택해주세요.");
                    return false;
                }
            } else {
                this.info.topedtime = '';
            }

            return true;
        },
        onSave() {
            if(!this.checkValidation()){
                return;
            }

            if(this.info.isdisplay === 'F') {
                let param = {
                    idx : this.idx,
                    overmsg : '저장하시겠습니까?',
                    zeromsg : '저장',
                    isloading: false
                }

                this.$http.post("/admin/operation/notice/check", param)
                .then(result => {
                    if (result.statusCode == 200) {
                        let msg = result.data.msg;

                        if(confirm(msg)){
                            let params = this.info;
                            params.idx = this.idx;
                            this.$http.post('/admin/operation/notice/modify', params)
                            .then(result => {
                                if(result.statusCode === 200) {
                                    alert("저장이 완료되었습니다.");
                                    this.onClose(true);
                                }
                            })
                            .catch(error => {
                                this.$util.debug(error);
                            });
                        }
                    }
                })
                .catch(error => {
                    this.$util.debug(error);
                })
            } else {
                if(confirm('저장하시겠습니까?')){
                    let params = this.info;
                    params.idx = this.idx;
                    this.$http.post('/admin/operation/notice/modify', params)
                    .then(result => {
                        if(result.statusCode === 200) {
                            alert("저장이 완료되었습니다.");
                            this.onClose(true);
                        }
                    })
                    .catch(error => {
                        this.$util.debug(error);
                    });
                }
            }
        },
        // datepicker callback
        changeStartDate(data) {
          this.checkObj.startYYYYMMDD = data.fromYYYYMMDD;
          this.checkObj.startHH = data.fromHH;
          this.checkObj.startMi = data.fromMM;
        },
        changeEndDate(data) {
          this.checkObj.endYYYYMMDD = data.fromYYYYMMDD;
          this.checkObj.endHH = data.fromHH;
          this.checkObj.endMi = data.fromMM;
        },
        onClose(isreload) {
            this.info = this.$options.data().info;
            this.checkObj = this.$options.data().checkObj;
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
    },
}
</script>

<style>

</style>