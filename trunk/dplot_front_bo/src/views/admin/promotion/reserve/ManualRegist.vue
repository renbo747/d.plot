<template>
    <!-- 적립금 수동지급 팝업 -->
    <div id="modal-wrap" class="modal" style="display: block;">
        <div class="modal-content" style="width: 1400px;">
            <div class="pop-header">
                <h2>적립금 수동지급</h2>
                <button type="button" class="pop-close" @click="$emit('closePopup');"></button>
            </div>
            <div class="pop-body">
                <div class="bar-title">조건설정</div>
                <div class="form-area">
                    <table cellpadding="0" cellspacing="0" class="gray-tb">
                        <colgroup>
                            <col width="170px">
                            <col width="">
                        </colgroup>
                        <tbody>
                            <tr>
                                <th>구분<i class="essential"></i></th>
                                <td>
                                    <div class="radio_wrap wide">
                                        <input type="radio" name="isempreserveD" id="isempreserveDF" v-model="info.isempreserve" value="F">
                                        <label for="isempreserveDF">적립금</label>
                                        <input type="radio" name="isempreserveD" id="isempreserveDT" v-model="info.isempreserve" value="T">
                                        <label for="isempreserveDT">임직원적립금</label>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th>지급사유<i class="essential"></i></th>
                                <td>
                                    <input type="text" class="dpib" style="width: 68%" placeholder="지급사유" v-model="info.resreason" maxlength="200">
                                    <span class="txt-orange ml3"><i class="icon-alert"></i>이벤트 제목, C/S 관련 문구 등을 작성 (사용자에게 노출되는 적립사유)</span>
                                </td>
                            </tr>
                            <tr>
                                <th>지급일<i class="essential"></i></th>
                                <td>
                                    <div class="radio_wrap wide dpib">
                                        <input type="radio" name="isnowpayD" id="isnowpayDF" v-model="info.isnowpay" value="T">
                                        <label for="isnowpayDF">즉시지급</label>
                                        <input type="radio" name="isnowpayD" id="isnowpayDT" v-model="info.isnowpay" value="F">
                                        <label for="isnowpayDT">일자설정</label>
                                    </div>
                                    <div class="dpib" v-if="info.isnowpay=='F'">
                                        <CommonDatePickerFromTo
                                            :useFrom="true" :useTo="false"
                                            :fromYYYYMMDD="info.respaydate"
                                            :fromHH="info.respayhour"
                                            :fromMM="info.respaymi"
                                            @getDate="pickerChangeEvent" />
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th>대상범위<i class="essential"></i></th>
                                <td>
                                    <div class="radio_wrap">
                                        <input type="radio" name="ismemtypeD" id="ismemtypeDT" v-model="info.ismemtype" value="T">
                                        <label for="ismemtypeDT">특정 유형/등급 대상</label>
                                        <input type="radio" name="ismemtypeD" id="ismemtypeDF" v-model="info.ismemtype" value="F">
                                        <label for="ismemtypeDF">특정 회원 대상</label>
                                    </div>
                                </td>
                            </tr>
                            <tr v-if="info.ismemtype == 'T'">
                                <th>대상회원유형</th>
                                <td>
                                    <div class="check-wrap">
                                        <input type="checkbox" id="chkAllMemberD" v-model="info.isallmember" true-value="T" false-value="F" @change="checkAllMembertype">
                                        <label for="chkAllMemberD">전체</label>
                                    </div>
                                    <div class="check-wrap" v-for="item in commonCode.dadamembertype" :key="item.cmcode">
                                        <input type="checkbox" :id="'mumembertypeD_'+item.cmcode" v-model="info.mumembertypeArr" :true-value="[]" :value="item.cmcode"
                                            :disabled="info.isempreserve==='T' && item.detail !== 'T'">
                                        <label :for="'mumembertypeD_'+item.cmcode">{{ item.codename }}</label>
                                    </div>
                                </td>
                            </tr>
                            <tr v-if="info.ismemtype == 'T'">
                                <th>대상회원등급</th>
                                <td>
                                    <div class="check-wrap">
                                        <input type="checkbox" id="chkAllMemlvD" v-model="info.isallmemlv" true-value="T" false-value="F" @change="checkAllMemlvtype">
                                        <label for="chkAllMemlvD">전체</label>
                                    </div>
                                    <div class="check-wrap" v-for="item in commonCode.memlvtype" :key="item.cmcode">
                                        <input type="checkbox" :id="'mumemlvtypeD_'+item.cmcode" v-model="info.mumemlvtypeArr" :true-value="[]" :value="item.cmcode">
                                        <label :for="'mumemlvtypeD_'+item.cmcode">{{ item.codename }}</label>
                                    </div>
                                </td>
                            </tr>
                            <tr v-if="info.ismemtype == 'F'">
                                <th>대상회원<i class="essential"></i></th>
                                <td>
                                    <div class="caption-group clearfix dpb">
                                        <div class="total-group fl">
                                            <span class="total">적립금 지급대상 회원목록</span>
                                        </div>
                                        <div class="btn-group fr">
                                            <input type="file" ref="userExcelFile" @change="readExcelFile('userExcelFile', $event)" hidden
                                                accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" />
                                            <button type="button" class="btn black-line" @click="downloadExcelTemplate('MemberTemplate.xlsx')">양식 다운로드</button>                               
                                            <button type="button" class="btn green-line"  @click="fileAttach('userExcelFile')">엑셀파일 올리기</button>
                                            <button type="button" class="btn blue-line" @click="openAddUserPopup">회원추가</button>
                                            <button type="button" class="btn red-line" @click="removeUser">삭제</button>
                                        </div>
                                    </div>
                                    <div class="scroll-y" style="width: 100%; max-height: 400px; margin-bottom: 0;">
                                        <table cellpadding="0" cellspacing="0" class="data-tb align-c" style="margin-left: 0;">
                                            <caption>특정회원목록</caption>
                                            <colgroup>
                                                <col width="5%" /><!-- checkbox -->
                                                <col width="5%" /><!-- No -->
                                                <col width="15%" /><!-- 아이디 -->
                                                <col width="15%" /><!-- 이름 -->
                                                <col width="15%" /><!-- 유형 -->
                                                <col width="15%" /><!-- 등급 -->
                                                <col width="" /><!-- 가입일 -->
                                            </colgroup>
                                            <thead>
                                                <tr>
                                                    <th><input type="checkbox" id="chkallgoodsmem" v-model="info.isallchkmem" @change="checkAllMemberList($event.target.checked)" true-value="T" false-value="F" /></th>
                                                    <th>No</th>
                                                    <th>아이디</th>
                                                    <th>이름</th>
                                                    <th>유형
                                                        <button type="button" class="sort" :value="sortData.dadamembertypename"
                                                            :class="[{up : sortData.dadamembertypename=== 'dadamembertypename_asc'}, {down : sortData.dadamembertypename === 'dadamembertypename_desc'}]"
                                                            @click="sortToggle(sortData.dadamembertypename)"></button>
                                                    </th>
                                                    <th>등급
                                                        <button type="button" class="sort" :value="sortData.memlvtypename"
                                                            :class="[{up : sortData.memlvtypename=== 'memlvtypename_asc'}, {down : sortData.memlvtypename === 'memlvtypename_desc'}]"
                                                            @click="sortToggle(sortData.memlvtypename)"></button>
                                                    </th>
                                                    <th>가입일
                                                        <button type="button" class="sort" :value="sortData.regdate"
                                                            :class="[{up : sortData.regdate=== 'regdate_asc'}, {down : sortData.regdate === 'regdate_desc'}]"
                                                            @click="sortToggle(sortData.regdate)"></button>
                                                    </th>
                                                </tr>
                                            </thead>
                                            <tbody v-if="info.targetMemberList.length > 0">
                                                <tr v-for="(item, index) in info.targetMemberList" :key="item.userno">
                                                    <td><input type="checkbox" :id="'member_'+index" v-model="item.ischecked" @change="checkMemberList($event.target.checked)"/></td>
                                                    <td>{{ index+1 }}</td>
                                                    <td>{{ item.userid }}</td>
                                                    <td>{{ item.username }}</td>
                                                    <td>{{ item.dadamembertypename }}</td>
                                                    <td>{{ item.memlvtypename }}</td>
                                                    <td>{{ item.regdate }}</td>
                                                </tr>
                                            </tbody>
                                            <tbody v-else>
                                                <tr><td colspan="7">조회 결과가 존재하지 않습니다.</td></tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th>지급포인트<i class="essential"></i></th>
                                <td>
                                    <input type="text" class="right" style="width: 80px;" v-model="info.paypoint" maxlength="11"/><span class="ml3">포인트</span>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="btn-group">
                    <button type="button" class="btn big blue" @click="save">저장</button>
                    <button type="button" class="btn big darkgray" @click="$emit('closePopup');">취소</button>
                </div>
            </div>
        </div>
    </div>
    <!-- /적립금 수동지급 팝업 -->
</template>

<script>
import XLSX from 'xlsx'
import CommonDatePickerFromTo from '@views.admin/common/CommonDatePickerFromTo.vue';
import CommonAddUserPopup from '@views.admin/common/popup/CommonAddUserPopup.vue';

export default {
    name: 'admin.promotion.reserve.manualregist',
    components: {
        CommonDatePickerFromTo
    },
    mounted() {
        let params = { url: this.$options.name, isloading: false };
        this.$http.post('/admin/common/pageAuth/check', params)
            .then(result => {
                this.isRead = (result.data.isread === 'T');
                this.isWrite = (result.data.iswrite === 'T');
            
                if(this.isRead) {
                    // 초기화
                    this.onInit();
                } else {
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
    data() {
        return {
            user: {},
            commonCode: {
                dadamembertype: [], // 다다픽회원유형
                memlvtype: [],      // 회원등급
            },
            sortData: {
                dadamembertypename: 'dadamembertypename_asc',
                memlvtypename: 'memlvtypename_asc',
                regdate: 'regdate_desc'
            },
            info: {
                isempreserve: 'F',  // 임직원적립금여부
                resreason: '',      // 지급사유
                isnowpay: 'T',      // 즉시지급여부
                respayday: '',      // 지급일시
                respaydate: '',     // 지급일
                respayhour: '',     // 지급시간
                respaymi: '',       // 지급분
                ismemtype: 'T',     // 특정유형등급대상여부
                isallmemlv: 'T',    // 다중대상회원등급전체여부
                mumemlvtype: '',    // 다중대상회원등급
                mumemlvtypeArr: [], // 다중대상회원등급Array
                isallmember: 'T',   // 다중대상회원유형전체여부
                mumembertype: '',   // 다중대상회원유형
                mumembertypeArr: [],// 다중대상회원유형Array
                targetMemberList: [],   // 특정회원목록
                isallchkmem: 'F',       // 특정회원목록 전체선택여부
                psort: 'regdate_desc',  // 특정회원목록 정렬조건
                paypoint: ''            // 지급포인트
            }
        };
    },
    methods: {
        // 화면 초기화
        onInit: function() {
            // 공통코드 조회
            this.getCommonCodeList();
        },
        // 공통코드 목록 조회
        getCommonCodeList: function() {
            let cmclassArr = ['DADAMEMBERTYPE', 'MEMLVTYPE'];
            this.$http.post('/common/code/map/list', {cmclass : cmclassArr, isloading: false})
                .then(result =>{
                    let data = result.data;
                    for (const [key] of Object.entries(data)) {
                        this.commonCode[key] = data[key];
                    }
                    this.checkAllMemlvtype();
                    this.checkAllMembertype();
                })
                .catch(error => {
                    this.$util.debug(error);
                });
        },
        // 대상회원유형 전체체크
        checkAllMembertype: function() {
            let isAllCheck = this.info.isallmember;
            this.info.mumembertypeArr = [];
            if (isAllCheck == 'T') {
                if (this.info.isempreserve === 'T') {
                    for(let type of this.commonCode.dadamembertype){
                        if (type.detail === 'T') {
                        this.info.mumembertypeArr.push(type.cmcode);
                        }
                    }
                } else {
                    for(let type of this.commonCode.dadamembertype){
                        this.info.mumembertypeArr.push(type.cmcode);
                    }
                }
            }
        },
        // 대상회원등급 전체체크
        checkAllMemlvtype: function() {
            let isAllCheck = this.info.isallmemlv;
            this.info.mumemlvtypeArr = [];
            if (isAllCheck == 'T') {
                for(let type of this.commonCode.memlvtype){
                    this.info.mumemlvtypeArr.push(type.cmcode);
                }
            }
        },
        // 지급일 DatePicker 콜백
        pickerChangeEvent: function(date) {
            this.info.respaydate = date.fromYYYYMMDD;
            this.info.respayhour = date.fromHH;
            this.info.respaymi = date.fromMM;
            this.info.respayday = date.fromDate12;
        },
        // 특정회원 추가 팝업
        openAddUserPopup: function() {
            let params = {isempreserve: this.info.isempreserve};
            this.$eventBus.$emit('modalShow', CommonAddUserPopup, params,
                (result) => {
                    // 팝업에서 가져온 결과 상품별 회원목록에 적용(이미 추가되어 있는 회원 제외)
                    let resultList = result.list;
                    for (let i=0; i<resultList.length; i++) {
                        resultList[i].ischecked = false;
                        let existCnt = this.info.targetMemberList.filter(member => {
                            return member.userno == resultList[i].userno;
                        }).length;
                        if (existCnt == 0) {
                            this.info.targetMemberList.push(resultList[i]);
                        }
                    }
                }
            );
        },
        // 특정회원 삭제
        removeUser: function() {
            let targetList = this.info.targetMemberList.filter(item => {
                return item.ischecked == true;
            });
            // 선택항목 체크
            if (targetList.length == 0) {
                alert('삭제할 특정회원을 선택해주세요.');
            }
            // 목록에서 선택된 항목 삭제
            targetList.forEach(item => {
                let findIndex = this.info.targetMemberList.indexOf(item);
                this.info.targetMemberList.splice(findIndex, 1);
            });
            // 삭제후 목록이 없는경우 전체체크 해제
            if (this.info.targetMemberList.length == 0) {
                this.info.isallchkmem = 'F';
            }
        },
        // 특정회원목록 전체체크
        checkAllMemberList: function(value) {
            this.info.targetMemberList.forEach(item => {
                item.ischecked = value;
            });
        },
        // 특정회원목록 개별체크
        checkMemberList: function() {
            let checkedList = this.info.targetMemberList.filter(item => {
                return item.ischecked == true;
            });

            if (this.info.targetMemberList.length > checkedList.length){
                this.info.isallchkmem = 'F';
            } else {
                this.info.isallchkmem = 'T';
            }
        },
        // 첨부파일(탐색기 열기)
        fileAttach: function(fileTypeKey) {
            this.$refs[fileTypeKey].click();
        },
        // 엑셀파일 읽기
        readExcelFile: function(fileTypeKey, event) {
            let file = null;
            let headerInfo = [];
            // 판매정보 특정회원
            if (fileTypeKey.indexOf('userExcelFile') > -1) {
                file = event.target.files[0];
                headerInfo = ['userid'];
            }
            let reader = new FileReader();
            let tmpResult = {};
            reader.onload = () => {
                let data = reader.result;
                let workbook = XLSX.read(data, {type: 'array'});
                workbook.SheetNames.forEach(sheetName => {
                    const roa = XLSX.utils.sheet_to_json(workbook.Sheets[sheetName], {header: headerInfo, defval: ''});
                    if (roa.length) tmpResult = roa ;
                });
                this.readExcelData(fileTypeKey, tmpResult);
            };
            reader.readAsArrayBuffer(file);
        },
        // 엑셀일괄 업로드
        readExcelData(fileTypeKey, excelData) {
            this.$util.debug(excelData);
            if (excelData.length == 0) {
                alert("엑셀파일에 데이터가 존재하지 않습니다.");
                return;
            }
            
            // 특정회원
            if (fileTypeKey.indexOf('userExcelFile') > -1) {
                let useridlist = excelData.splice(1, excelData.length); // 헤더여부에 따라 달라짐
                this.$http.post('/admin/goods/regist/userinfo', { useridlist: useridlist })
                    .then(result => {
                        if (result.statusCode == '200') {
                            let resultList = result.data.list;
                            if ( resultList.length == 0) {
                                alert("회원정보가 존재하지않습니다. 입력한 사용자ID를 확인해주세요.");
                            } else {                                
                                // 상품별 회원목록에 적용(이미 추가되어 있는 회원 제외)
                                let resultCnt = 0;
                                for (let i=0; i<resultList.length; i++) {
                                    let existCnt = this.info.targetMemberList.filter(member => {
                                        return member.userno == resultList[i].userno;
                                    }).length;
                                    if (existCnt == 0) {
                                        this.info.targetMemberList.push(resultList[i]);
                                        resultCnt++;
                                    }
                                }
                                alert(resultCnt + '건 추가되었습니다.');
                            }
                        }
                        this.$refs.userExcelFile.value='';
                    })
                    .catch(error => {
                        this.$util.debug(error);
                        this.$refs.userExcelFile.value='';
                    });
            }
        },
        // 정렬
        sortToggle(key){
            let arr = key.split('_');
            let sortKey = arr[0];
            let sortOrder = (arr[1] === 'asc') ? 'desc' : 'asc';
            let sortName = sortKey + '_' + sortOrder;

            this.sortData = this.$options.data().sortData;

            this.sortData[sortKey] = sortName;
            this.info.psort = sortName;

            this.info.targetMemberList.sort((a, b) => {
                a[sortKey] = this.$util.isNull(a[sortKey]) ? '' : a[sortKey];
                b[sortKey] = this.$util.isNull(b[sortKey]) ? '' : b[sortKey];
                
                if (a[sortKey] < b[sortKey]) {
                    return sortOrder == 'asc'? -1: 1;
                } else if (a[sortKey] > b[sortKey]) {
                    return sortOrder == 'asc'? 1: -1;
                }
                return 0;
            });
        },
        // 유효성체크
        checkValidation: function() {
            let valid = [
                {field: 'info.isempreserve' , type: 'S', name:'구분', required: true},
                {field: 'info.resreason'    , type: 'I', name:'지급사유', required: true},
                {field: 'info.isnowpay'     , type: 'S', name:'지급일구분', required: true},
                {field: 'info.ismemtype'    , type: 'S', name:'대상구분', required: true},
                {field: 'info.paypoint'     , type: 'I', name:'지급포인트', required: true}
            ];
            let msg = this.$util.validMsg(this.$data, valid);
            if (!this.$util.isNull(msg)){
                alert(msg);
                return false;
            }

            // 지급사유 trim 필수체크
            if (this.$util.isNull(this.info.resreason.trim())) {
                alert('지급사유를 입력해주세요.');
                return false;
            }
            // 지급일 필수체크
            if (this.info.isnowpay == 'F') {
                let valid = [
                    {field: 'info.respaydate' , type: 'I', name:'지급일', required: true},
                    {field: 'info.respayhour' , type: 'I', name:'지급시간', required: true},
                    {field: 'info.respaymi'   , type: 'I', name:'지급시간', required: true}
                ];
                let msg = this.$util.validMsg(this.$data, valid);
                if (!this.$util.isNull(msg)){
                    alert(msg);
                    return false;
                }
            }
            // 대상범위에 따른 필수체크
            if (this.info.ismemtype == 'T') {
                if (this.info.mumembertypeArr.length == 0 || this.info.mumemlvtypeArr.length == 0) {
                    alert("대상회원유형 또는 대상회원등급을 선택해주세요.");
                    return false;
                }
            } else {
                if (this.info.targetMemberList.length == 0) {
                    alert("특정회원을 추가해주세요.");
                    return false;
                }
            }

            return true;
        },
        // 저장
        save: function() {
            if (!this.checkValidation()) return;

            if (confirm("저장하시겠습니까?")) {
                this.$http.post('/admin/promotion/reserve/save', this.info)
                .then(result =>{
                    this.$util.debug(result);
                    if (result.statusCode == '200') {
                        alert('저장이 완료되였습니다.');
                        this.$emit('closePopup', true);
                    }
                })
                .catch(error => {
                    this.$util.debug(error);
                });
            }
        },

        // 엑셀양식다운로드
        downloadExcelTemplate: function(filename) {
            let params = { filename: filename }   // 서버에 저장되어있는 파일명
            let config = { responseType: 'arraybuffer' };
            this.$http.post('/admin/common/excel/download', params, config);
        },
    },
    watch: {
        // 즉시지급여부
        'info.isnowpay': function() {
            this.info.respaydate = ' ';
            this.info.respayhour = '';
            this.info.respaymi = '';
            this.info.respayday = '';
        },
        // 임직원적립금 여부
        'info.isempreserve': function(value) {
            if (value === 'T') {
                for (let i=this.info.mumembertypeArr.length-1; i>=0; i--) {
                    let code = this.info.mumembertypeArr[i];
                    for (let j=0; j<this.commonCode.dadamembertype.length; j++) {
                        let codeItem = this.commonCode.dadamembertype[j];
                        if (code === codeItem.cmcode && codeItem.detail !== 'T') {
                            this.info.mumembertypeArr.splice(i, 1);
                        }
                    }
                }
            } else if (value === 'F') {
                if (this.info.isallmember === 'T') {
                    this.checkAllMembertype();
                }
            } else {
                this.info.targetMemberList = [];
            }
        },
        // 특정유형등급대상여부
        'info.ismemtype': function(value) {
            if (value == 'T') {
                this.info.targetMemberList = [];
            } else {
                this.info.mumembertype = '';
                this.info.mumemlvtype = '';
                this.info.isallmember = 'T';
                this.info.isallmemlv = 'T';

                this.checkAllMemlvtype();
                this.checkAllMembertype();
            }
        },
        // 대상회원유형
        'info.mumembertypeArr': function(value) {
            let chkCnt = this.commonCode.dadamembertype.length;
            if (this.info.isempreserve === 'T') {
                chkCnt = this.commonCode.dadamembertype.filter(obj => {return obj.detail === 'T';}).length;
            }
            if (value.length < chkCnt) {
                this.info.isallmember = 'F';
            } else {
                this.info.isallmember = 'T';
            }
            this.info.mumembertype = this.info.mumembertypeArr.join();
        },
        // 대상회원등급
        'info.mumemlvtypeArr': function(value) {
            if (value.length < this.commonCode.memlvtype.length) {
                this.info.isallmemlv = 'F';
            } else {
                this.info.isallmemlv = 'T';
            }
            this.info.mumemlvtype = this.info.mumemlvtypeArr.join();
        },
        // 숫자만입력
        'info.paypoint': function(value) {
            value = value+'';
            if (this.$util.isNull(value)) return;
            return this.info.paypoint = value.replace(/(^0|[^0-9])/gi, '');
        },
    }
}
</script>
