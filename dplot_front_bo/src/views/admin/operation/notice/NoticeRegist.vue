<template>
    <div id="modal-wrap" class="modal" style="display: block;">
        <div class="modal-content" style="width: 1200px;">
            <div class="pop-header">
                <h2>공지사항 등록</h2>
                <button type="button" class="pop-close" @click="onClose"></button>
            </div>
            <div class="pop-body">
                <div class="bar-title">기본정보</div>
                <div class="boxing">
                    <div class="form-area">
                        <dl>
                            <dt>노출상태</dt>
                            <dd>
                                <div class="radio_wrap wide">
                                    <input type="radio" name="group00regist" id="group01regist" v-model="info.isdisplay" value="F" checked/><label for="group01regist">노출</label>
                                    <input type="radio" name="group00regist" id="group02regist" v-model="info.isdisplay" value="T" /><label for="group02regist">미노출</label>
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
                                        :fromYYYYMMDD="info.startYYYYMMDD"
                                        :fromHH="info.startHH"
                                        :fromMM="info.startMi"
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
                                        <input type="checkbox" v-model="info.isallmuappch" id="isallmuappch" true-value="T" false-value="F" @change="checkAllAppchtype">
                                        <label for="isallmuappch">전체</label>
                                    </div>
                                    <div class="check-wrap" v-for="item in commonCode.muappchtype" :key="item.cmcode">
                                        <input type="checkbox" v-model="info.muappchtypearr" :id="'muappchtype_'+item.cmcode" true-value="[]" :value="item.cmcode">
                                        <label :for="'muappchtype_'+item.cmcode">{{item.codename}}</label>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th>대상유형</th>
                                <td>
                                    <div class="check-wrap">
                                        <input type="checkbox" v-model="info.isallmumember" id="isallmumember" true-value="T" false-value="F" @change="checkAllMembertype">
                                        <label for="isallmumember">전체</label>
                                    </div>
                                    <div class="check-wrap" v-for="item in commonCode.mumembertype" :key="item.cmcode">
                                        <input type="checkbox" v-model="info.mumembertypearr" :id="'mumembertype_'+item.cmcode" true-value="[]" :value="item.cmcode">
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
                                        :fromYYYYMMDD="info.endYYYYMMDD"
                                        :fromHH="info.endHH"
                                        :fromMM="info.endMi"
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
    name: 'admin.operation.notice.regist',
    props: ['commonCodeList'],
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
                startYYYYMMDD: '',
                startHH: '',
                startMi:'',
                topedtime: '',
                endYYYYMMDD: '',
                endHH: '',
                endMi: '',
                muappchtype: '',
                muappchtypearr: [],
                isallmuappch: 'T',
                mumembertype: '',
                mumembertypearr: [],
                isallmumember: 'T',
                istopfix: 'F',
                istrash: 'F',
                writer: '',
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
                this.setInit();
            }else {
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
        setInit() {
            let userInfo = this.$storage.getLocalStorage(this.$store.getters.CONSTANTS.MANAGER_SESSION);
            this.info.writer = userInfo.name;
            this.info.endYYYYMMDD = this.$util.getAddDate(this.$util.getDate(''), 7, '-');
            this.info.endHH = this.$util.getTime('').substring(0,2);
            this.info.endMi = '59';

            this.checkAllAppchtype();
            this.checkAllMembertype();
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
        copyByContent(value) {
            this.$refs.mobileEditor.content = this.$refs.pcEditor.content;
        },
        // 조회조건 - 적용채널 전체체크
        checkAllAppchtype: function() {
            let isAllCheck = this.info.isallmuappch;
            this.info.muappchtypearr = [];
            if (isAllCheck == 'T') {
                for(let type of this.commonCode.muappchtype){
                    this.info.muappchtypearr.push(type.cmcode);
                }
            }
        },
        // 조회조건 - 적용채널 전체체크
        checkAllMembertype: function() {
            let isAllCheck = this.info.isallmumember;
            this.info.mumembertypearr = [];
            if (isAllCheck == 'T') {
                for(let type of this.commonCode.mumembertype){
                    this.info.mumembertypearr.push(type.cmcode);
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

            if(this.info.isrightnow === 'T') {
                this.info.poststtime = this.$util.getDate12NoSeparator();
            } else { 
                this.info.poststtime = this.info.startYYYYMMDD.replaceAll('-','') + this.info.startHH + this.info.startMi;
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
                this.info.topedtime = this.info.endYYYYMMDD.replaceAll('-','') + this.info.endHH + this.info.endMi;
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

            if(confirm("저장하시겠습니까?")){
                let params = this.info;
                this.$http.post('/admin/operation/notice/save', params)
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
        },
        // datepicker callback
        changeStartDate(data) {
          this.info.startYYYYMMDD = data.fromYYYYMMDD;
          this.info.startHH = data.fromHH;
          this.info.startMi = data.fromMM;
        },
        changeEndDate(data) {
          this.info.endYYYYMMDD = data.fromYYYYMMDD;
          this.info.endHH = data.fromHH;
          this.info.endMi = data.fromMM;
        },
        onClose(isreload) {
            this.info = this.$options.data().info;
            // 팝업 닫기
            if (typeof(isreload)==='boolean' && isreload) {
                this.$emit('closeRegist', isreload);
            } else {
                this.$emit('closeRegist');
            }
        },
    },
    watch: {
        'info.muappchtypearr': function(value){
            if (value.length < this.commonCode.muappchtype.length) {
                this.info.isallmuappch = 'F';
            } else {
                this.info.isallmuappch = 'T';
            }
            this.info.muappchtype = this.info.muappchtypearr.join();
        },
        'info.mumembertypearr': function(value){
            if (value.length < this.commonCode.mumembertype.length) {
                this.info.isallmumember = 'F';
            } else {
                this.info.isallmumember = 'T';
            }
            this.info.mumembertype = this.info.mumembertypearr.join();
        },
    },
}
</script>

<style>

</style>