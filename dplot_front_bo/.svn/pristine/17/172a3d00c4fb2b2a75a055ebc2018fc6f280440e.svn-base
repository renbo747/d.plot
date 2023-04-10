<template>
    <div id="modal-wrap" class="modal" style="display: block;">
        <div class="modal-content" style="width: 1400px;">
            <div class="pop-header">
                <h2>키워드영역컨텐츠 상세</h2>
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
                <div class="bar-title mt10">기본정보</div>
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
                            <dt>제목<i class="essential"></i></dt>
                            <dd><input type="text" v-model="info.subject" style="width: 100%" placeholder="키워드영역 컨텐츠 제목" /></dd>
                        </dl>
                        <dl>
                            <dt>설명</dt>
                            <dd><input type="text" v-model="info.desc" style="width: 100%" placeholder="키워드영역 컨텐츠 설명" /></dd>
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
                                <th>전시기간<i class="essential"></i></th>
                                <td>
                                    <CommonDatePickerFromTo
                                        :fromYYYYMMDD="timeInfo.startYYYYMMDD"
                                        :fromHH="timeInfo.startHH"
                                        :fromMM="timeInfo.startMM"
                                        :toYYYYMMDD="timeInfo.toYYYYMMDD"
                                        :toHH="timeInfo.toHH"
                                        :toMM="timeInfo.toMM"
                                        @getDate="getEventTimeDate"
                                    />
                                </td>
                            </tr>
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
                                <th>대표 이미지(PC)<i class="essential"></i></th>
                                <td>
                                    <div class="img-with-text" style="width: 202px;">
                                        <div class="img-thumb" style="width: 480px; height: 245px;" :class="{'no-image': $util.isNull(files['pcimgfile'])}"><!-- 이미지 없는 경우 no-image 클래스 추가 -->
                                            <img style="width: 480px; height: 245px;" :src="imgPreview['pcimgfile']" alt="대표 이미지(pc)" v-if="!$util.isNull(files['pcimgfile'])">
                                        </div>
                                        <button type="button" class="btn blue-line mt10" style="width: 100%;"
                                            v-if="$util.isNull(files['pcimgfile'])" @click="fileAttach('pcimgfile')">파일 올리기</button>
                                        <input type="file" ref="pcimgfile" @change="handleFileUpload('pcimgfile')" accept="image/jpeg, image/png" hidden/>
                                        <button type="button" class="btn blue-line mt10" style="width: calc(50% - 3px);"
                                            v-if="!$util.isNull(files['pcimgfile'])" @click="fileAttach('pcimgfile')">변경</button>
                                        <button type="button" class="btn red-line mt10" style="width: calc(50% - 4px);"
                                            v-if="!$util.isNull(files['pcimgfile'])" @click="removeFile('pcimgfile')">삭제</button>
                                    </div>
                                    <div class="img-with-text text">
                                        <p class="txt-orange"><i class="icon-alert"></i>대표 이미지입니다. 보기 쉬운 간결한 이미지를 활용해 주세요.</p>
                                        <p class="txt-orange"><i class="icon-alert"></i>사이즈: 1920*980 / 용량: 10MB 이하 / 파일 : JPG, JPEG, PNG</p>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th>대표 이미지(모바일)<i class="essential"></i></th>
                                <td>
                                    <div class="mb10">
                                        <input type="checkbox" v-model="info.copyimgcheck" @change="setSameAsPcepreImg" id="copy-img"><label for="copy-img">PC 대표 이미지를 복사</label>
                                    </div>
                                    <div class="img-with-text" style="width: 202px;">
                                        <div class="img-thumb" style="width: 360px; height: 626px;" :class="{'no-image': $util.isNull(files['mobileimgfile'])}">
                                            <img style="width: 360px; height: 626px;" :src="imgPreview['mobileimgfile']" alt="대표 이미지(모바일)" v-if="!$util.isNull(files['mobileimgfile'])">
                                        </div>
                                        <button type="button" class="btn blue-line mt10" style="width: 100%;"
                                            v-if="$util.isNull(files['mobileimgfile'])" @click="fileAttach('mobileimgfile')">파일 올리기</button>
                                        <input type="file" ref="mobileimgfile" @change="handleFileUpload('mobileimgfile')" accept="image/jpeg, image/png" hidden/>
                                        <button type="button" class="btn blue-line mt10" style="width: calc(50% - 3px);"
                                            v-if="!$util.isNull(files['mobileimgfile'])" @click="fileAttach('mobileimgfile')">변경</button>
                                        <button type="button" class="btn red-line mt10" style="width: calc(50% - 4px);"
                                            v-if="!$util.isNull(files['mobileimgfile'])" @click="removeFile('mobileimgfile')">삭제</button>
                                    </div>
                                    <div class="img-with-text text">
                                        <p class="txt-orange"><i class="icon-alert"></i>모바일 리스팅 및 와이드형 화면에 노출되는 이미지를 업로드 해 주세요.</p>
                                       <p class="txt-orange"><i class="icon-alert"></i>사이즈: 720*1252 / 용량: 10MB 이하 / 파일 : JPG, JPEG, PNG</p>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th>키워드<i class="essential"></i></th>
                                <td>
                                    <div class="caption-group clearfix">
                                        <div class="total-group fl">
                                            <span class="total">키워드 목록</span>
                                        </div>
                                        <div class="btn-group fr">
                                            <button type="button" class="btn blue-line" @click="addKeyword">추가</button>
                                            <button type="button" class="btn red-line" @click="removeKeyword">삭제</button>
                                        </div>
                                    </div>
                                    <div class="scroll-y" style="width: 100%; max-height: 350px; margin-bottom: 0;">
                                        <table cellpadding="0" cellspacing="0" class="data-tb align-c row-3div">
                                            <colgroup>
                                                <col width="3%"><!-- checkbox -->
                                                <col width="4%"><!-- No -->
                                                <col width=""><!-- 키워드 및 링크 -->
                                            </colgroup>
                                            <thead>
                                                <tr>
                                                    <th rowspan="3"><input type="checkbox" id="chkall" v-model="isallchk" @change="checkAllList($event.target.checked)"/></th>
                                                    <th rowspan="3">No</th>
                                                    <th>키워드 및 링크</th>
                                                </tr>
                                            </thead>
                                            <tbody v-if="this.keywordlist.length > 0">
                                                <template v-for="(row, n) in this.keywordlist">
                                                <tr :key="'a'+n">
                                                    <td rowspan="3"><input type="checkbox" v-model="moveData.targetIdx" :id="row.reviewidx" :value="n" @change="checkList($event.target.checked)"/></td>
                                                    <td rowspan="3">{{n +1}}</td>
                                                    <td class="left">
                                                        <span class="dpib"  style="width: 100px;">키워드</span>
                                                        <input type="text" v-model="keywordlist[n].keyword" style="width: calc(100% - 250px);" placeholder="키워드 텍스트(최대 30바이트)">
                                                        <input type="checkbox" v-model="keywordlist[n].isemp" true-value="T" false-value="F" :id="'groupa'+n" class="ml10"><label :for="'groupa'+n">강조</label>
                                                    </td>
                                                </tr>
                                                <tr :key="'b'+n">
                                                    <td class="left">
                                                        <span class="dpib"  style="width: 100px;">링크(PC)</span>
                                                        <input type="text" v-model="keywordlist[n].pclinkurl" style="width: calc(100% - 250px);" placeholder="키워드 클릭 시 연결되는 PC 화면 주소">
                                                        <input type="checkbox" v-model="keywordlist[n].ispcnwindow" true-value="T" false-value="F" :id="'groupb'+n" class="ml10"><label :for="'groupb'+n">새창</label>
                                                    </td>
                                                </tr>
                                                <tr :key="'c'+n">
                                                    <td class="left">
                                                        <span class="dpib"  style="width: 100px;">링크(모바일)</span>
                                                        <input type="text" v-model="keywordlist[n].molinkurl" style="width: calc(100% - 250px);" placeholder="키워드 클릭 시 연결되는 모바일 화면 주소">
                                                        <input type="checkbox" v-model="keywordlist[n].ismonwindow" true-value="T" false-value="F"  :id="'groupc'+n" class="ml10"><label :for="'groupc'+n">새창</label>
                                                    </td>
                                                </tr>
                                                </template>
                                            </tbody>
                                        </table>
                                    </div>
                                    <div class="bottom-group">
                                        <CommonArraySort :list-data="keywordlist"
                                            :move-data="moveData"
                                            :is-active-save-btn="false"
                                            v-if="isWrite"
                                        />
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
import CommonDatePickerFromTo from "@views.admin/common/CommonDatePickerFromTo";
import CommonArraySort from "@views.admin/common/CommonArraySort";
export default {
    name: 'admin.operation.magazine.keyword.detail',
    props: {
        commonCodeList: Object,
        idx: Number,
    },
    components: {
        CommonDatePickerFromTo,
        CommonArraySort
    },
    data() {
        return {
            info: {},
            files: {},
            addfiles: {
                pcimgfile: '',
                mobileimgfile: '',
            },
            imgPreview: {
                pcimgfile: '',
                mobileimgfile: '',
            },
            timeInfo: {              // 진행기간
                startYYYYMMDD: '',
                startHH: '',
                startMM: '',
                toYYYYMMDD: '',
                toHH: '',
                toMM: ''
            },
            checkObj: {
                muappchtypearr: [],
                isallmuappch: 'F',
                mumembertypearr: [],
                isallmumember: 'F',
                mumemlvtypearr: [],
                isallmumemlv: 'F',
            },
            moveData: {                       // 노출순위 데이터
                moveValue: '',                  // 움직일 값
                targetIdx: [],                  // 대상 위치
                code: 'U',                      // 위, 아래 코드
                isSuccess: false,               // 저장 성공 여부 (** 중요)
            },
            isallchk: false,
            keywordlist: [],
            commonCode: this.commonCodeList,
            deletefile: [],
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
                    mzwdidx : this.idx
                }

                this.$http.post("/admin/operation/magazine/keyword/detail", param)
                    .then(result =>{
                        this.info = result.data.info;
                        this.files = result.data.files;
                        this.imgPreview.pcimgfile = this.files.pcimgfile.fullpath;
                        this.imgPreview.mobileimgfile = this.files.mobileimgfile.fullpath;
                        this.keywordlist = result.data.keywordlist;
                        this.info.copyimgcheck = false;
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
            this.timeInfo.startYYYYMMDD = this.$util.getFormatDate(this.info.kwsttime.substring(0,8),'-');
            this.timeInfo.startHH = this.info.kwsttime.substring(8,10);
            this.timeInfo.startMM = this.info.kwsttime.substring(10,12);
            this.timeInfo.toYYYYMMDD = this.$util.getFormatDate(this.info.kwedtime.substring(0,8),'-');
            this.timeInfo.toHH = this.info.kwedtime.substring(8,10);
            this.timeInfo.toMM = this.info.kwedtime.substring(10,12);

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
         // 진행기간 DatePicker 콜백 메서드
        getEventTimeDate(date) {
            this.info.kwsttime = date.fromDate12;
            this.info.kwedtime = date.toDate12;
        },
        goSave() {
            if(this.checkValidation()) {
                let params = this.info;
                let files = [];
                
                if(!this.$util.isNull(this.addfiles.pcimgfile)){
                    files.push({key: 'pcimgfile', file: this.addfiles.pcimgfile.file});
                }
                if(!this.$util.isNull(this.addfiles.mobileimgfile)){
                    // pc이미지 복사를 한 경우 file key가 없고 복사시 기존에 저장되어있는 pcimgfile을 저장해야하므로 pcimgfile의 idx를 보냄
                    if(Object.prototype.hasOwnProperty.call(this.files.mobileimgfile,'file')){
                        files.push({key: 'mobileimgfile', file: this.addfiles.mobileimgfile.file});
                    } else {
                        params.copycheck = true;
                        params.copyidx = this.files.pcimgfile.idx;
                    }
                }
                params.files = files;
                params.deletefile = new Set(this.deletefile);

                if(this.keywordlist.length > 0){
                    params.keywordlist = this.keywordlist;
                }

                let param = {
                    idx: this.idx,
                    isdisplay : this.info.isdisplay,
                    kwsttime: this.info.kwsttime,
                    kwedtime: this.info.kwedtime,
                    isloading: false
                }
                
                param.overmsg = '저장하시겠습니까?';
                param.zeromsg = '수정';
        
                this.$http.post("/admin/operation/magazine/keyword/check", param)
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
                this.$http.post("/admin/operation/magazine/keyword/modify", params)
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
            let msg = '';
            let valid = [
                {field: 'info.subject', type: 'I', name: '카피', required: true},
                {field: 'info.muappchtype', type: 'S', name: '노출채널', required: true},
                {field: 'info.mumembertype', type: 'S', name: '대상회원유형', required: true},
                {field: 'info.mumemlvtype', type: 'S', name: '대상회원등급', required: true},
            ];

            msg = this.$util.validMsg(this.$data, valid);
            if(!this.$util.isNull(msg)){
                alert(msg);
                return false;
            }

            if(this.info.kwsttime.length < 12) {
                alert("전시시작시간을 선택해주세요.");
                return false;
            }
            if(this.info.kwedtime.length < 12) {
                alert("전시종료시간을 선택해주세요.");
                return false;
            }
            if(this.info.kwsttime >= this.info.kwedtime) {
                alert("전시종료시간을 시작시간 이후로 설정해주세요.");
                return false;
            }

            if(this.files.pcimgfile === ''){
                alert("대표 이미지(PC)를 등록해주세요.");
                return false;
            }
            if(this.files.mobileimgfile === ''){
                alert("대표 이미지(모바일)를 등록해주세요.");
                return false;
            } 

            if(this.keywordlist.length === 0) {
                alert("키워드를 추가해주세요.");
                return;
            }
            
            for(let i=0; i<this.keywordlist.length; i++) {
                let item = this.keywordlist[i];
                if(this.$util.isNull(item.keyword)) {
                    alert("키워드를 입력해주세요.");
                    return false;
                } else if(this.$util.isNull(item.pclinkurl)) {
                    alert("링크(PC)를 입력해주세요.");
                    return false;
                } else if(this.$util.isNull(item.molinkurl)) {
                    alert("링크(모바일)을 입력해주세요.");
                    return false;
                }
            }

            return true;
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
        handleFileUpload: function(fileTypeKey, target) {
            // PC, 모바일 대표이미지
            let file = this.$refs[fileTypeKey];
            if (this.$util.isNull(file)) {
                return;
            }

            if(Object.prototype.hasOwnProperty.call(this.files[fileTypeKey],'idx')){
                if(fileTypeKey !== 'mobileimgfile' || !this.info.copyimgcheck) {
                    this.deletefile.push(this.files[fileTypeKey].idx);
                }
            }

            let fileType = ['image/png','image/jpeg', 'image/png'];
            if(!fileType.includes(file.files[0].type)){
                alert('jpg, jpeg, png파일만 첨부 가능합니다.');
                file.value = null;
                this.files[fileTypeKey] = '';
                return false;
            }
            if(file.files[0].size > 1048576 * 10){
                alert('파일 최대 크기는 10MB를 초과 할 수 없습니다.');
                file.value = null;
                this.files[fileTypeKey] = '';
                return false;
            }
            let fileObj = {
                file: file.files[0],
                iscreated: true
            }
            
            this.files[fileTypeKey] = fileObj;
            this.addfiles[fileTypeKey] = fileObj;
            this.imgPreview[fileTypeKey] = URL.createObjectURL(fileObj.file);

            if(fileTypeKey === 'pcimgfile' && this.info.copyimgcheck){
                this.setSameAsPcepreImg();
            }
            // 모바일 대표이미지 변경시 PC이미지와 동일 체크 해제
            if (fileTypeKey === 'mobileimgfile') {
                this.info.copyimgcheck = false;
            }
        },
        removeFile(fileTypeKey, index) {
            if(confirm('파일을 삭제 하시겠습니까?')){
                if(Object.prototype.hasOwnProperty.call(this.files[fileTypeKey],'idx')){
                    if(fileTypeKey !== 'mobileimgfile' || !this.info.copyimgcheck) {
                        this.deletefile.push(this.files[fileTypeKey].idx);
                    }
                }

                this.files[fileTypeKey] = '';
                this.addfiles[fileTypeKey] = '';
                this.imgPreview[fileTypeKey] = '';
                this.$refs[fileTypeKey].value = null;

                // 모바일 대표이미지 변경시 PC이미지와 동일 체크 해제
                if (fileTypeKey === 'mobileimgfile') {
                    this.info.copyimgcheck = false;
                }
            }
        },
        // PC 대표이미지와 동일하게 세팅
        setSameAsPcepreImg: function() {
            if(Object.prototype.hasOwnProperty.call(this.files.mobileimgfile,'idx')){
                if(this.info.copyimgcheck) {
                    if(!this.deletefile.includes(this.files.mobileimgfile.idx)) {
                        this.deletefile.push(this.files.mobileimgfile.idx);
                    }
                }
            }
            if (this.info.copyimgcheck) {
                this.files.mobileimgfile = this.files.pcimgfile;
                this.addfiles.mobileimgfile = this.files.pcimgfile;
                this.imgPreview.mobileimgfile = this.imgPreview.pcimgfile;
            } else {
                this.files.mobileimgfile = '';
                this.addfiles.mobileimgfile = '';
                this.imgPreview.mobileimgfile = '';
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
        addKeyword() {
            let obj = {
                keyword: '',
                isemp: 'F',
                pclinkurl: '',
                ispcnwindow: 'F',
                molinkurl: '',
                ismonwindow: 'F',
                sortnum : this.keywordlist.length
            }; 
            this.keywordlist.push(obj);
        },
        removeKeyword() {
            if(this.moveData.targetIdx.length === 0) {
                alert("선택된 키워드가 없습니다.");
                return ;
            }

            this.moveData.targetIdx.sort((a,b) => b-a);

            this.moveData.targetIdx.forEach(n => {
                this.keywordlist.splice(n, 1);
            });
            this.moveData.targetIdx = [];
            if(this.keywordlist.length === 0) {
                this.isallchk = false;
            }
        },
        // 목록 전체체크
        checkAllList: function(value) {
            this.moveData.targetIdx = [];
            if (value) {
                for (let i in this.keywordlist) {
                    this.moveData.targetIdx[i] = i;
                }
            }
        },
         // 목록 개별체크
        checkList: function() {
            if (this.keywordlist.length > this.moveData.targetIdx.length){
                this.isallchk = false;
            } else {
                this.isallchk = true;
            }
        },
        onClose(isreload){
            this.info = {};
            this.files = {};
            this.timeInfo = this.$options.data().timeInfo;
            this.addfiles = this.$options.data().addfiles;
            this.keywordlist = [];
            this.deletefile = [];
            this.imgPreview = this.$options.data().imgPreview;
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