<template>
    <!-- 적립금 수동차감 팝업 -->
    <div id="modal-wrap" class="modal" style="display: block;">
        <div class="modal-content" style="width: 1400px;">
            <div class="pop-header">
                <h2>적립금 수동차감</h2>
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
                                <th>차감사유<i class="essential"></i></th>
                                <td>
                                    <input type="text" class="dpib" style="width: 100%" placeholder="차감사유" v-model="info.reason" maxlength="200">
                                </td>
                            </tr>
                            <tr>
                                <th>대상회원<i class="essential"></i></th>
                                <td>
                                    <div class="caption-group clearfix dpb">
                                        <div class="total-group fl">
                                            <span class="total">적립금 차감대상 회원목록</span>
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
                                                <col width="3%" /><!-- checkbox -->
                                                <col width="5%" /><!-- No -->
                                                <col width="20%" /><!-- 아이디 -->
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
                                            <tbody v-if="info.memberList.length > 0">
                                                <tr v-for="(item, index) in info.memberList" :key="item.userno">
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
                                <th>차감포인트<i class="essential"></i></th>
                                <td>
                                    <input type="text" class="right" style="width: 80px;" v-model="info.point" maxlength="11"/><span class="ml3">포인트</span>
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
    <!-- /적립금 수동차감 팝업 -->
</template>

<script>
import XLSX from 'xlsx'
import CommonAddUserPopup from '@views.admin/common/popup/CommonAddUserPopup.vue';
import AdjustPointListPopup from '@views.admin/promotion/popup/AdjustPointListPopup.vue';

export default {
    name: 'admin.promotion.reserve.manualdeduction',
    mounted() {
        let params = { url: this.$options.name, isloading: false };
        this.$http.post('/admin/common/pageAuth/check', params)
            .then(result => {
                this.isRead = (result.data.isread === 'T');
                this.isWrite = (result.data.iswrite === 'T');
            
                if(!this.isRead) {
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
            sortData: {
                dadamembertypename: 'dadamembertypename_asc',
                memlvtypename: 'memlvtypename_asc',
                regdate: 'regdate_desc'
            },
            info: {
                isempreserve: 'F',      // 임직원적립금여부
                reason: '',             // 사유
                memberList: [],         // 특정회원목록
                isallchkmem: 'F',       // 특정회원목록 전체선택여부
                psort: 'regdate_desc',  // 특정회원목록 정렬조건
                point: ''               // 차감포인트
            }
        };
    },
    methods: {
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
        // 특정회원 추가 팝업
        openAddUserPopup: function() {
            let params = {isempreserve: this.info.isempreserve};
            this.$eventBus.$emit('modalShow', CommonAddUserPopup, params,
                (result) => {
                    // 팝업에서 가져온 결과 상품별 회원목록에 적용(이미 추가되어 있는 회원 제외)
                    let resultList = result.list;
                    for (let i=0; i<resultList.length; i++) {
                        resultList[i].ischecked = false;
                        let existCnt = this.info.memberList.filter(member => {
                            return member.userno == resultList[i].userno;
                        }).length;
                        if (existCnt == 0) {
                            this.info.memberList.push(resultList[i]);
                        }
                    }
                }
            );
        },
        // 특정회원 삭제
        removeUser: function() {
            let targetList = this.info.memberList.filter(item => {
                return item.ischecked == true;
            });
            // 선택항목 체크
            if (targetList.length == 0) {
                alert('삭제할 특정회원을 선택해주세요.');
            }
            // 목록에서 선택된 항목 삭제
            targetList.forEach(item => {
                let findIndex = this.info.memberList.indexOf(item);
                this.info.memberList.splice(findIndex, 1);
            });
            // 삭제후 목록이 없는경우 전체체크 해제
            if (this.info.memberList.length == 0) {
                this.info.isallchkmem = 'F';
            }
        },
        // 특정회원목록 전체체크
        checkAllMemberList: function(value) {
            this.info.memberList.forEach(item => {
                item.ischecked = value;
            });
        },
        // 특정회원목록 개별체크
        checkMemberList: function() {
            let checkedList = this.info.memberList.filter(item => {
                return item.ischecked == true;
            });

            if (this.info.memberList.length > checkedList.length){
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
                                    let existCnt = this.info.memberList.filter(member => {
                                        return member.userno == resultList[i].userno;
                                    }).length;
                                    if (existCnt == 0) {
                                        this.info.memberList.push(resultList[i]);
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

            this.info.memberList.sort((a, b) => {
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
                {field: 'info.reason'       , type: 'I', name:'차감사유', required: true},
                {field: 'info.point'        , type: 'I', name:'차감포인트', required: true}
            ];
            let msg = this.$util.validMsg(this.$data, valid);
            if (!this.$util.isNull(msg)){
                alert(msg);
                return false;
            }

            // 차감사유 trim 필수체크
            if (this.$util.isNull(this.info.reason.trim())) {
                alert('차감사유 입력해주세요.');
                return false;
            }
            // 대상회원 필수체크
            if (this.info.memberList.length == 0) {
                alert("특정회원을 추가해주세요.");
                return false;
            }

            return true;
        },
        // 저장
        save: function() {
            if (!this.checkValidation()) return;

            if (confirm("저장하시겠습니까?")) {
                this.$http.post('/admin/promotion/reserve/deduction', this.info)
                .then(result =>{
                    this.$util.debug(result);
                    if (result.statusCode == '200') {
                        let failCnt = result.data.failcnt;
                        let adjustList = result.data.adjustlist;
                        let adjustCnt = adjustList.length;

                        let msg = '저장이 완료되었습니다.';
                        let addMsg = '';
                        if (adjustCnt > 0) {
                            addMsg += '조정:' + adjustCnt + '건';
                        }
                        if (failCnt > 0) {
                            addMsg += !this.$util.isNull(addMsg)? ', ' : '';
                            addMsg = '실패:' + failCnt + '건';
                        }
                        msg += this.$util.isNull(addMsg)? '' : '('+addMsg+')';

                        alert(msg);
                        if (adjustList.length > 0) {
                            let params = {adjustList: adjustList};
                            this.$eventBus.$emit('modalShow', AdjustPointListPopup, params, 
                                (result) => {
                                    this.$emit('closePopup', true);
                                }
                            );
                        } else {
                            this.$emit('closePopup', true);
                        }
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
        // 임직원적립금 여부
        'info.isempreserve': function() {
            this.info.memberList = [];
        },
        // 숫자만입력
        'info.point': function(value) {
            value = value+'';
            if (this.$util.isNull(value)) return;
            return this.info.point = value.replace(/(^0|[^0-9])/gi, '');
        },
    }
}
</script>
