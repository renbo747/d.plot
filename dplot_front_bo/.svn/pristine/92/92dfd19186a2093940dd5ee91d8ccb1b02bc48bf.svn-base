<template>
    <div id="modal-wrap" class="modal" style="display: block;">
        <div class="modal-content" style="width: 1400px;">
            <div class="pop-header">
                <h2>전시영역5컨텐츠 등록</h2>
                <button type="button" class="pop-close" @click="onClose"></button>
            </div>
            <div class="pop-body">
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
                            <dt>제목<i class="essential"></i></dt>
                            <dd><input type="text" v-model="info.subject" style="width: 100%" placeholder="전시영역 컨텐츠 제목" /></dd>
                        </dl>
                        <dl>
                            <dt>설명</dt>
                            <dd><input type="text" v-model="info.desc" style="width: 100%" placeholder="전시영역 컨텐츠 설명" /></dd>
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
                                <th>대상회원유형<i class="essential"></i></th>
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
                                <th>대상회원등급<i class="essential"></i></th>
                                <td>
                                    <div class="check-wrap">
                                        <input type="checkbox" v-model="info.isallmumemlv" id="isallmumemlv" true-value="T" false-value="F" @change="checkAllMemLvtype">
                                        <label for="isallmumemlv">전체</label>
                                    </div>
                                    <div class="check-wrap" v-for="item in commonCode.mumemlvtype" :key="item.cmcode">
                                        <input type="checkbox" v-model="info.mumemlvtypearr" :id="'mumemlvtype_'+item.cmcode" true-value="[]" :value="item.cmcode">
                                        <label :for="'mumemlvtype_'+item.cmcode">{{item.codename}}</label>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th>노출위치<i class="essential"></i></th>
                                <td>
                                    <div class="radio_wrap">
                                        <input type="radio" name="group05" v-model="info.ex5type" id="group51" value="EX5001" checked=""><label for="group51">PC : 좌 / 모바일 : 상</label>
                                        <input type="radio" name="group05" v-model="info.ex5type" id="group52" value="EX5002"><label for="group52">PC : 우 / 모바일 : 하</label>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th>링크(PC)<i class="essential"></i></th>
                                <td>
                                    <input type="text" v-model="info.pclinkurl" style="width: calc(100% - 100px);" placeholder="전시영역 클릭 시 연결되는 PC 화면 주소">
                                    <input type="checkbox" v-model="info.ispcnwindow" id="group02" true-value="T" false-value="F" class="ml10"><label for="group21">새창</label>
                                </td>
                            </tr>
                            <tr>
                                <th>링크(모바일)<i class="essential"></i></th>
                                <td>
                                    <input type="text" v-model="info.molinkurl" style="width: calc(100% - 100px);" placeholder="전시영역 클릭 시 연결되는 모바일 화면 주소">
                                    <input type="checkbox" v-model="info.ismonwindow" id="group02" true-value="T" false-value="F" class="ml10"><label for="group21">새창</label>
                                </td>
                            </tr>
                            <tr>
                                <th>배너 이미지(PC)</th>
                                <td>
                                    <div class="img-with-text" style="width: 202px;">
                                        <div class="img-thumb" style="width: 305px; height: 305px;" :class="{'no-image': $util.isNull(files['pcimgfile'])}"><!-- 이미지 없는 경우 no-image 클래스 추가 -->
                                            <img style="width: 305px; height: 305px;" :src="imgPreview['pcimgfile']" alt="배너 이미지(pc)" v-if="!$util.isNull(files['pcimgfile'])">
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
                                        <p class="txt-orange"><i class="icon-alert"></i>사이즈: 610*610 / 용량: 10MB 이하 / 파일 : JPG, JPEG, PNG</p>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th>배너 이미지(모바일)</th>
                                <td>
                                    <div class="mb10">
                                        <input type="checkbox" v-model="info.copyimgcheck" @change="setSameAsPcepreImg" id="copy-img"><label for="copy-img">PC 대표 이미지를 복사</label>
                                    </div>
                                    <div class="img-with-text" style="width: 202px;">
                                        <div class="img-thumb" style="width: 320px; height: 320px;" :class="{'no-image': $util.isNull(files['mobileimgfile'])}">
                                            <img style="width: 320px; height: 320px;" :src="imgPreview['mobileimgfile']" alt="배너 이미지(모바일)" v-if="!$util.isNull(files['mobileimgfile'])">
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
                                        <p class="txt-orange"><i class="icon-alert"></i>사이즈: 640*640 / 용량: 10MB 이하 / 파일 : JPG, JPEG, PNG</p>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th>상품<i class="essential"></i></th>
                                <td>
                                    <div class="caption-group clearfix">
                                        <div class="total-group fl">
                                            <span class="total">노출대상 상품목록</span>
                                        </div>
                                        <div class="btn-group fr">
                                            <button type="button" class="btn blue-line" @click="openGoodsAdditionPopup">추가</button>
                                            <button type="button" class="btn red-line" @click="removeGoodsAddition">삭제</button>
                                        </div>
                                    </div>
                                    <div class="scroll-y" style="width: 100%; max-height: 350px; margin-bottom: 0;">
                                        <table cellpadding="0" cellspacing="0" class="data-tb align-c">
                                            <colgroup>
                                                <col width="3%"><!-- checkbox -->
                                                <col width="4%"><!-- No -->
                                                <col width="6%"><!-- 판매구분 -->
                                                <col width="10%"><!-- 파트너사명 -->
                                                <col width="8%"><!-- 상품코드 -->
                                                <col width="62px"><!-- 이미지 -->
                                                <col width=""><!-- 상품명 -->
                                                <col width="7%"><!-- 판매가 -->
                                                <col width="10%"><!-- 키워드 -->
                                            </colgroup>
                                            <thead>
                                                <tr>
                                                    <th><input type="checkbox" id="chkall" v-model="isallchk" @change="checkAllList($event.target.checked)"/></th>
                                                    <th>No</th>
                                                    <th>판매구분</th>
                                                    <th>파트너사명</th>
                                                    <th>상품코드</th>
                                                    <th colspan="2">상품명</th>
                                                    <th>판매가</th>
                                                    <th>키워드</th>
                                                </tr>
                                            </thead>
                                            <tbody v-if="this.goodslist.length > 0">
                                                <tr v-for="(row, n) in this.goodslist" :key="n">
                                                    <td><input type="checkbox" v-model="moveData.targetIdx" :id="row.goodscode" :value="n" @change="checkList($event.target.checked)"/></td>
                                                    <td>{{ n + 1 }}</td>
                                                    <td>{{ row.ispbgoodsname }}</td>
                                                    <td>{{ row.dealername }}</td>
                                                    <td>{{ row.goodscode }}</td>
                                                    <td>
                                                        <div class="img-thumb size60" :class="{'no-image': $util.isNull(row.fullpath)}">
                                                            <img :src="row.fullpath" v-if="!$util.isNull(row.fullpath)">
                                                        </div>
                                                    </td>
                                                    <td class="left no-left">
                                                        <span class="small-txt">{{ row.fullcategoryname }}</span>
                                                        <p class="mg0">{{ row.goodsname }}</p>
                                                    </td>
                                                    <td class="right">{{ row.price }}</td>
                                                    <td><input type="text" v-model="row.keyword"/></td>
                                                </tr>
                                            </tbody>
                                            <tbody v-else>
                                                <tr><td colspan="8">노출대상 상품이 존재하지 않습니다.</td></tr>
                                            </tbody>
                                        </table>
                                    </div>
                                    <div class="bottom-group">
                                        <CommonArraySort :list-data="goodslist"
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
import CommonAddGoodsPopup from '@views.admin/common/popup/CommonAddGoodsPopup.vue';

export default {
    props: ['commonCodeList'],
    name: 'admin.operation.shopping.shopexfive.regist',
    components: {
        CommonDatePickerFromTo,
        CommonArraySort
    },
data() {
        return {
            info: {
                isdisplay: 'F',
                subject: '',
                desc: '',
                exsttime: '',
                exedtime: '',
                muappchtype: '',
                muappchtypearr: [],
                isallmuappch: 'T',
                mumembertype: '',
                mumembertypearr: [],
                isallmumember: 'T',
                mumemlvtype: '',
                mumemlvtypearr: [],
                isallmumemlv: 'T',
                isbnimg: 'T',
                ex5type: '',
                pclinkurl: '',
                ispcnwindow: 'F',
                molinkurl: '',
                ismonwindow: 'F',
                copyimgcheck: false,
            },
            goodslist: [],
            timeInfo: {              // 진행기간
                startYYYYMMDD: '',
                startHH: '',
                startMM: '',
                toYYYYMMDD: '',
                toHH: '',
                toMM: ''
            },
            commonCode: this.commonCodeList,
            files: {
                pcimgfile: '',
                mobileimgfile: '',
            },
            imgPreview: {
                pcimgfile: '',
                mobileimgfile: '',
            },
            moveData: {                       // 노출순위 데이터
                moveValue: '',                  // 움직일 값
                targetIdx: [],                  // 대상 위치
                code: 'U',                      // 위, 아래 코드
                isSuccess: false,               // 저장 성공 여부 (** 중요)
            },
            isallchk: false,
            isRead: false,
            isWrite: false,
        }
    },
    mounted() {
        this.$http.post('/admin/common/pageAuth/check', {url : this.$options.name}).then(result => {

            this.isRead = (result.data.isread === 'T');
            this.isWrite = (result.data.iswrite === 'T');
      
            if(this.isRead) {
                this.setInit();
            } else{
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
            this.checkAllAppchtype();
            this.checkAllMembertype();
            this.checkAllMemLvtype();
        },
         // 진행기간 DatePicker 콜백 메서드
        getEventTimeDate(date) {
            this.info.exsttime = date.fromDate12;
            this.info.exedtime = date.toDate12;
        },
        goSave() {
            if(this.checkValidation()) {
                let params = this.info;
                
                let files = [];
                if(!this.$util.isNull(this.files.pcimgfile)){
                    files.push({key: 'pcimgfile', file: this.files.pcimgfile.file});
                }
                if(!this.$util.isNull(this.files.mobileimgfile)){
                    files.push({key: 'mobileimgfile', file: this.files.mobileimgfile.file});
                }
                params.files= files;

                if(this.goodslist.length > 0){
                    params.goodslist = this.goodslist;
                }

                if(this.info.isdisplay === 'T') {
                    let param = {
                        isdisplay : 'T',
                        ex5type: this.info.ex5type,
                        exsttime: this.info.exsttime,
                        exedtime: this.info.exedtime,
                        isloading: false
                    }
                    
                    param.overmsg = '저장하시겠습니까?';
                    param.zeromsg = '저장';
            
                    this.$http.post("/admin/operation/shopping/shopexfive/check", param)
                    .then(result => {
                        if (result.statusCode == 200) {
                            params.msg = result.data.msg;
                            this.checkSave(params);
                        }
                    })
                    .catch(error => {
                        this.$util.debug(error);
                    })
                } else {
                    params.msg = "저장하시겠습니까?";
                    this.checkSave(params);
                }
            }
        },
        checkSave(params) {
            if(confirm(params.msg)){
                this.$http.post("/admin/operation/shopping/shopexfive/save", params)
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
                {field: 'info.subject', type: 'I', name: '제목', required: true},
                {field: 'info.pclinkurl', type: 'I', name: '링크(PC)', required: true},
                {field: 'info.molinkurl', type: 'I', name: '링크(모바일)', required: true},
                {field: 'info.ex5type', type: 'S', name: '노출위치', required: true},
                {field: 'info.muappchtype', type: 'S', name: '노출채널', required: true},
                {field: 'info.mumembertype', type: 'S', name: '대상회원유형', required: true},
                {field: 'info.mumemlvtype', type: 'S', name: '대상회원등급', required: true},
            ];

            msg = this.$util.validMsg(this.$data, valid);
            if(!this.$util.isNull(msg)){
                alert(msg);
                return false;
            }

            if(this.info.exsttime.length < 12) {
                alert("전시시작시간을 선택해주세요.");
                return false;
            }
            if(this.info.exedtime.length < 12) {
                alert("전시종료시간을 선택해주세요.");
                return false;
            }
            if(this.info.exsttime >= this.info.exedtime) {
                alert("전시종료시간을 시작시간 이후로 설정해주세요.");
                return false;
            }

            /*if(this.files.pcimgfile === ''){
                alert("대표 이미지(PC)를 등록해주세요.");
                return false;
            }
            if(this.files.mobileimgfile === ''){
                alert("대표 이미지(모바일)를 등록해주세요.");
                return false;
            } */

            if(this.goodslist.length === 0) {
                alert("상품을 등록해주세요.");
                return false;
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
            this.imgPreview[fileTypeKey] = URL.createObjectURL(fileObj.file);

            if(fileTypeKey === 'pcimgfile' && this.info.copyimgcheck){
                this.setSameAsPcepreImg();
            }
            // 모바일 대표이미지 변경시 PC이미지와 동일 체크 해제
            if (fileTypeKey === 'mobileimgfile') {
                this.info.copyimgcheck = false;
            }
        },
        removeFile(fileTypeKey, n) {
            if(confirm('파일을 삭제 하시겠습니까?')){
                this.files[fileTypeKey] = '';
                this.imgPreview[fileTypeKey] = '';
                this.$refs[fileTypeKey].value = null;

                // 모바일 대표이미지 변경시 PC이미지와 동일 체크 해제
                if (fileTypeKey == 'mobileimgfile') {
                    this.info.copyimgcheck = false;
                }
            }
        },
        // PC 대표이미지와 동일하게 세팅
        setSameAsPcepreImg: function() {
            if (this.info.copyimgcheck) {
                this.files.mobileimgfile = this.files.pcimgfile;
                this.imgPreview.mobileimgfile = this.imgPreview.pcimgfile;
            } else {
                this.files.mobileimgfile = '';
                this.imgPreview.mobileimgfile = '';
            }
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
        // 조회조건 - 적용채널 전체체크
        checkAllMemLvtype: function() {
            let isAllCheck = this.info.isallmumemlv;
            this.info.mumemlvtypearr = [];
            if (isAllCheck == 'T') {
                for(let type of this.commonCode.mumemlvtype){
                    this.info.mumemlvtypearr.push(type.cmcode);
                }
            }
        },
        // 추가상품 팝업 오픈
        openGoodsAdditionPopup: function() {
            let param = {
                isshowkeyword: 'T'
            }
            this.$eventBus.$emit('modalShow', CommonAddGoodsPopup, param,
                (result) => {
                    // 팝업에서 가져온 결과 추가상품 목록에 적용(이미 추가되어 있는 상품 제외)
                    let resultList = result.list;
                    for (let i=0; i<resultList.length; i++) {
                        let existCnt = this.goodslist.filter(item => {
                            return item.goodsno == resultList[i].goodsno;
                        }).length;
                        if (existCnt == 0) {
                            resultList[i].sortnum = this.goodslist.length + 1;
                            this.goodslist.push(resultList[i]);
                        }
                    }
                }
            );
        },
        // 상품 삭제
        removeGoodsAddition: function() {
            if(this.moveData.targetIdx.length === 0) {
                alert("선택된 상품이 없습니다.");
                return ;
            }

            this.moveData.targetIdx.sort((a,b) => {
                if (a < b) {
                    return 1;
                } else { 
                    return -1;
                }
            })

            this.moveData.targetIdx.forEach(n => {
                this.goodslist.splice(n, 1);
            });
            this.moveData.targetIdx = [];

            if(this.goodslist.length === 0) {
                this.isallchk = false;
            }
        },
        // 목록 전체체크
        checkAllList: function(value) {
            this.moveData.targetIdx = [];
            if (value) {
                for (let i in this.goodslist) {
                    this.moveData.targetIdx[i] = i;
                }
            }
        },
         // 목록 개별체크
        checkList: function() {
            if (this.goodslist.length > this.moveData.targetIdx.length){
                this.isallchk = false;
            } else {
                this.isallchk = true;
            }
        },
        onClose(isreload) {
            this.info = this.$options.data().info;
            this.timeInfo = this.$options.data().timeInfo;
            this.files = this.$options.data().files;
            this.goodslist = this.$options.data().goodslist;
            this.imgPreview = this.$options.data().imgPreview;
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
        'info.mumemlvtypearr': function(value){
            if (value.length < this.commonCode.mumemlvtype.length) {
                this.info.isallmumemlv = 'F';
            } else {
                this.info.isallmumemlv = 'T';
            }
            this.info.mumemlvtype = this.info.mumemlvtypearr.join();
        },
    }
}
</script>

<style>

</style>