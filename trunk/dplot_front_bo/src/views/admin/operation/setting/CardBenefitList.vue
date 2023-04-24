<template>
    <div class="content m-leftmenu">
        <common-navigator></common-navigator>
        <div class="inner">
            <!-- 카드혜택 -->
            <div class="bar-title">카드혜택</div>
            <table cellpadding="0" cellspacing="0" class="gray-tb">
                <colgroup>
                    <col width="180px">
                    <col width="">
                </colgroup>
                <tbody>
                    <tr>
                        <th>카드사별 청구할인<i class="essential"></i></th>
                        <td>
                          <div class="caption-group clearfix">
                              <div class="btn-group fr">
                                  <button type="button" class="btn blue-line" v-if="isWrite" @click="addColumn('DISC')">추가</button>
                                  <button type="button" class="btn red-line" v-if="isWrite" @click="removeDiscount('DISC')">삭제</button>
                              </div>
                          </div>
                          <div class="scroll-y" style="width: 100%; max-height: 400px; margin-bottom: 0;">
                              <table cellpadding="0" cellspacing="0" class="data-tb align-c" style="margin-left: 0;">
                                  <caption>카드사별 청구할인 목록</caption>
                                  <colgroup>
                                      <col width="4%"><!-- checkbox -->
                                      <col width="130px"><!-- 카드 -->
                                      <col width=""><!-- 금액(원) -->
                                      <col width=""><!-- 정액(원) -->
                                      <col width="10%"><!-- 정률(%) -->
                                      <col width="350px"><!-- 적용시작시분 -->
                                      <col width="350px"><!-- 적용종료시분 -->
                                  </colgroup>
                                  <thead>
                                      <tr>
                                          <th><input v-model="isdiscallchk" type="checkbox" id="chkalldisc" @change="checkAllDiscList($event.target.checked)"></th>
                                          <th>카드</th>
                                          <th>금액(원)</th>
                                          <th>정액(원)</th>
                                          <th>정률(%)</th>
                                          <th>적용시작시분</th>
                                          <th>적용종료시분</th>
                                      </tr>
                                  </thead>
                                  <tbody v-if="discountlist.length > 0">
                                      <tr v-for="(row, index) in discountlist" :key="index">
                                          <td><input type="checkbox" v-model="discheckedlist" :id="index+'disc'" :value="index" @change="checkDiscList($event.target.checked)"></td>
                                          <td>
                                            <select v-model="row.cardcompany" style="width: 130px;">
                                              <option value="">카드사</option>
                                              <option v-for="(card,m) in commonCode.cardcompany" :key="m+'disc'+index" :value="card.cmcode">{{card.codename}}</option>
                                            </select>
                                          </td>
                                          <td>
                                            <input type="text" v-model="row.amount" class="right">
                                          </td>
                                          <td>
                                            <input type="text" v-model="row.flatrate" class="right">
                                          </td>
                                          <td>
                                            <input type="text" v-model="row.fixedrate" class="right" @input="checkInputRate(row,'fixedrate', $event.target.value)">
                                          </td>
                                          <td>
                                            <CommonDatePickerFromTo
                                                :fromYYYYMMDD="disctimeinfo[index].startYYYYMMDD"
                                                :fromHH="disctimeinfo[index].startHH"
                                                :fromMM="disctimeinfo[index].startMi"
                                                :use-to="false"
                                                :index="index"
                                                @getDate="getDisCountTimeStartDate"
                                            />
                                          </td>
                                          <td>
                                            <CommonDatePickerFromTo
                                                :fromYYYYMMDD="disctimeinfo[index].endYYYYMMDD"
                                                :fromHH="disctimeinfo[index].endHH"
                                                :fromMM="disctimeinfo[index].endMi"
                                                :use-to="false"
                                                :index="index"
                                                @getDate="getDisCountTimeEndDate"
                                            />
                                          </td>
                                      </tr>
                                    </tbody>
                                    <tbody v-else>
                                        <tr>
                                            <td colspan="7">청구할인 목록이 존재하지 않습니다.</td>
                                        </tr>
                                    </tbody>
                              </table>
                          </div>
                      </td>
                    </tr>
                    <tr>
                        <th>유의사항<i class="essential"></i></th>
                        <td>
                          <CommonEditor ref="beneEditor"/>
                        </td>
                    </tr>
                </tbody>
            </table>
            <!-- /카드혜택 -->
            <!-- 부분무이자 -->
            <div class="bar-title">부분무이자</div>
            <table cellpadding="0" cellspacing="0" class="gray-tb">
                <colgroup>
                    <col width="180px">
                    <col width="">
                </colgroup>
                <tbody>
                    <tr>
                        <th>카드사별 부분무이자<i class="essential"></i></th>
                        <td>
                          <div class="caption-group clearfix">
                              <div class="btn-group fr">
                                <input type="file" ref="excelFile" @change="readExcelFile('excelFile', $event)" hidden
                                    accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" />
                                <button type="button" class="btn black-line" @click="downloadExcelTemplate('CardBenefitTemplate.xlsx')">양식 다운로드</button>
                                <button type="button" class="btn green-line" v-if="isWrite" @click="fileAttach('excelFile')">엑셀파일 올리기</button>
                                <button type="button" class="btn blue-line" v-if="isWrite" @click="addColumn('FREE')">추가</button>
                                <button type="button" class="btn red-line" v-if="isWrite" @click="removeDiscount('FREE')">삭제</button>
                              </div>
                          </div>
                          <div class="scroll-y" style="width: 100%; max-height: 400px; margin-bottom: 0;">
                              <table cellpadding="0" cellspacing="0" class="data-tb align-c" style="margin-left: 0;">
                                  <caption>카드사별 부분무이자 목록</caption>
                                  <colgroup>
                                      <col width="4%"><!-- checkbox -->
                                      <col width="130px"><!-- 카드 -->
                                      <col width="10%"><!-- 개월(월) -->
                                      <col width=""><!-- 금액(원) -->
                                      <col width=""><!-- 부분무이자 정책 -->
                                      <col width="350px"><!-- 적용시작시분 -->
                                      <col width="350px"><!-- 적용종료시분 -->
                                  </colgroup>
                                  <thead>
                                      <tr>
                                          <th><input type="checkbox" v-model="isfreeallchk" id="chkallfree" @change="checkAllFreeList($event.target.checked)"></th>
                                          <th>카드</th>
                                          <th>개월(월)</th>
                                          <th>금액(원)</th>
                                          <th>부분무이자 정책</th>
                                          <th>적용시작시분</th>
                                          <th>적용종료시분</th>
                                      </tr>
                                  </thead>
                                  <tbody v-if="freelist.length > 0">
                                      <tr v-for="(row, n) in freelist" :key="n">
                                          <td><input type="checkbox" v-model="freecheckedlist" :id="n+'free'" :value="n" @change="checkFreeList($event.target.checked)"></td>
                                          <td>
                                            <select v-model="row.cardcompany" style="width: 130px;">
                                              <option value="">카드사</option>
                                              <option v-for="(card,k) in commonCode.cardcompany" :key="k+'free'+n" :value="card.cmcode">{{card.codename}}</option>
                                            </select>
                                          </td>
                                          <td>
                                            <input type="text" v-model="row.month" class="right">
                                          </td>
                                          <td>
                                            <input type="text" v-model="row.amount" class="right">
                                          </td>
                                          <td>
                                            <input type="text" v-model="row.discnote" class="right">
                                          </td>
                                          <td>
                                            <CommonDatePickerFromTo
                                                :fromYYYYMMDD="freetimeinfo[n].startYYYYMMDD"
                                                :fromHH="freetimeinfo[n].startHH"
                                                :fromMM="freetimeinfo[n].startMi"
                                                :use-to="false"
                                                :index="n"
                                                @getDate="getFreeTimeStartDate"
                                            />
                                          </td>
                                          <td>
                                            <CommonDatePickerFromTo
                                                :fromYYYYMMDD="freetimeinfo[n].endYYYYMMDD"
                                                :fromHH="freetimeinfo[n].endHH"
                                                :fromMM="freetimeinfo[n].endMi"
                                                :use-to="false"
                                                :index="n"
                                                @getDate="getFreeTimeEndDate"
                                            />
                                          </td>
                                      </tr>
                                    </tbody>
                                    <tbody v-else>
                                        <tr>
                                            <td colspan="7">부분무이자 목록이 존재하지 않습니다.</td>
                                        </tr>
                                    </tbody>
                                </table>
                          </div>
                      </td>
                    </tr>
                    <tr>
                        <th>유의사항<i class="essential"></i></th>
                        <td>
                          <CommonEditor ref="interEditor"/>
                        </td>
                    </tr>
                </tbody>
            </table>
            <!-- /부분무이자 -->

            <div class="btn-group">
                <button type="button" class="btn big blue" v-if="isWrite" @click="onSave">저장</button>
            </div>
        </div>
    </div>
</template>

<script>
import XLSX from 'xlsx'
import CommonNavigator from '@views.admin/common/CommonNavigator.vue';
import CommonDatePickerFromTo from "@views.admin/common/CommonDatePickerFromTo";
import CommonEditor from "@views.admin/common/CommonEditor";

export default {
    name: 'admin.operation.setting.cardbenefitlist',
    components: {
        CommonNavigator,
        CommonDatePickerFromTo,
        CommonEditor
    },
    data() {
        return {
            discountlist: [],           // 청구할인 리스트
            isdiscallchk: false,        // 청구할인 전체체크여부
            discheckedlist: [],         // 청구할인 체크리스트
            discdeletelist: [],         // 청구할인 삭제리스트
            freelist: [],               // 부분무이자 리스트
            isfreeallchk: false,        // 부분무이자 전체체크여부
            freecheckedlist: [],        // 부분무이자 체크리스트
            freedeletelist: [],         // 부분무이자 삭제리스트
            disctimeinfo: [],           // 청구할인 각 row 시간정보
            freetimeinfo: [],           // 부분무이자 각 row 시간정보
            benenote: '',               // 카드혜택유의사항
            internote: '',              // 무이자유의사항
            commonCode: {
                cardcompany: [],        // 카드사 코드
            },
            excelFile: null,
            isRead: false,
            isWrite: false,
        }
    },
    mounted() {
        this.$http.post('/admin/common/pageAuth/check', {url : this.$options.name, isloading: false}).then(result => {
            this.isRead = (result.data.isread === 'T');
            this.isWrite = (result.data.iswrite === 'T');

            if(this.isRead){
                this.getCommonCodeList();
            }
        }).catch(error => {
            this.$util.debug(error);
        });
    },
    methods: {
        // 공통코드 목록 조회
        getCommonCodeList: function() {
            let cmclassArr = ['CARDCOMPANY'];
            this.$http.post('/common/code/map/list', {cmclass : cmclassArr, isloading: false})
            .then(result =>{
                let data = result.data;
                for (const [key] of Object.entries(data)) {
                    this.commonCode[key] = data[key];
                }
                // 검색조건 초기화
                this.$util.componentSetSearchParam(this);
                // 목록 조회
                this.searchList();
            })
            .catch(error => {
                this.$util.debug(error);
            });
        },
        // 청구할인 DatePicker 콜백 메서드
        getDisCountTimeStartDate(date, index) {
            this.discountlist[index].applysttime = date.fromDate12;
        },
        getDisCountTimeEndDate(date, index) {
            this.discountlist[index].applyedtime = date.fromDate12;
        },
        // 부분무의자 DatePicker 콜백 메서드
        getFreeTimeStartDate(date, index) {
            this.freelist[index].applysttime = date.fromDate12;
        },
        getFreeTimeEndDate(date, index) {
            this.freelist[index].applyedtime = date.fromDate12;
        },
        // 검색
        searchList() {
            let param = {};

            this.$http.post("/admin/operation/setting/cardbenefit/search", param)
            .then(result => {
                let data = result.data;
                this.discountlist = data.discountlist;
                this.freelist = data.freelist;
                this.isdiscallchk = false;
                this.isfreeallchk = false;
                this.discheckedlist = [];
                this.discdeletelist = [];
                this.freecheckedlist = [];
                this.freedeletelist = [];
                this.$refs.beneEditor.content = data.note.benenote;
                this.$refs.interEditor.content = data.note.internote;
                this.initTimeInfo();
            })
            .catch(error => {
                this.$util.debug(error);
            });
        },
        // 시간 세팅
        initTimeInfo() {
            this.disctimeinfo = [];
            this.freetimeinfo = [];
            this.discountlist.forEach(item => {
                let temp = {};
                temp.startYYYYMMDD = this.$util.getFormatDate(item.applysttime.substring(0,8),'-');
                temp.startHH = item.applysttime.substring(8,10);
                temp.startMi = item.applysttime.substring(10,12);
                temp.endYYYYMMDD = this.$util.getFormatDate(item.applyedtime.substring(0,8),'-');
                temp.endHH = item.applyedtime.substring(8,10);
                temp.endMi = item.applyedtime.substring(10,12);

                this.disctimeinfo.push(temp);
            });

            this.freelist.forEach(item => {
                let temp = {};
                temp.startYYYYMMDD = this.$util.getFormatDate(item.applysttime.substring(0,8),'-');
                temp.startHH = item.applysttime.substring(8,10);
                temp.startMi = item.applysttime.substring(10,12);
                temp.endYYYYMMDD = this.$util.getFormatDate(item.applyedtime.substring(0,8),'-');
                temp.endHH = item.applyedtime.substring(8,10);
                temp.endMi = item.applyedtime.substring(10,12);

                this.freetimeinfo.push(temp);
            });
        },
        // 에디터 내용 가져오기
        setEditorText() {
             if (this.$util.isNull(this.$refs.beneEditor.content)) {
                this.benenote = ''
            } else {
                this.benenote = this.$refs.beneEditor.content;
            }

            if (this.$util.isNull(this.$refs.interEditor.content)) {
                this.internote = ''
            } else {
                this.internote = this.$refs.interEditor.content;
            }
        },
        // 추가
        addColumn(type) {
            let temp = {
                isdiscount: 'T',
                cardcompany: '',
                amount: '',
                flatrate: '',
                fixedrate: '',
                month: '',
                discnote: '',
                applysttime: '',
                applyedtime: '',
                istrash: 'F',
            }

            let timeTemp = {
                startYYYYMMDD: '',
                startHH: '',
                startMi: '',
                endYYYYMMDD: '',
                endHH: '',
                endMi: '',
            }

            if(type === 'DISC') {
                this.discountlist.push(temp);
                this.disctimeinfo.push(timeTemp);
            } else {
                temp.isdiscount = 'F';
                this.freelist.push(temp);
                this.freetimeinfo.push(timeTemp);
            }
        },
        // 삭제
        removeDiscount(type) {
            if(type === 'DISC') {
                if(this.discheckedlist.length === 0) {
                    alert("삭제할 청구할인을 선택해주세요.");
                    return;
                }
                this.discheckedlist.sort((a,b) => {
                    return b-a;
                });

                this.discheckedlist.forEach(index => {
                    if(Object.prototype.hasOwnProperty.call(this.discountlist[index],'tcidx')){
                        this.discdeletelist.push(this.discountlist[index].tcidx);
                    }
                    this.discountlist.splice(index,1);
                    this.disctimeinfo.splice(index,1);
                });

                this.isdiscallchk = false;
                this.discheckedlist = [];
            } else {
                if(this.freecheckedlist.length === 0) {
                    alert("삭제할 부분무이자를 선택해주세요.");
                    return;
                }
                this.freecheckedlist.sort((a,b) => {
                    return b-a;
                });

                this.freecheckedlist.forEach(index => {
                    if(Object.prototype.hasOwnProperty.call(this.freelist[index],'tcidx')){
                        this.freedeletelist.push(this.freelist[index].tcidx);
                    }
                    this.freelist.splice(index,1);
                    this.freetimeinfo.splice(index,1);
                });

                this.isfreeallchk = false;
                this.freecheckedlist = [];
            }
        },
        // 청구할인 목록 전체체크
        checkAllDiscList: function(value) {
            this.discheckedlist = [];
            if (value) {
                this.discountlist.forEach((item,i) => {
                    this.discheckedlist.push(i);
                });
            }
        },
        // 청구할인 목록 개별체크
        checkDiscList: function() {
            if (this.discountlist.length > this.discheckedlist.length){
                this.isdiscallchk = false;
            } else {
                this.isdiscallchk = true;
            }
        },
        // 무분무이자 목록 전체체크
        checkAllFreeList: function(value) {
            this.freecheckedlist = [];
            if (value) {
                this.freelist.forEach((item,i) => {
                    this.freecheckedlist.push(i);
                });
            }
        },
        // 부분무이자 목록 개별체크
        checkFreeList: function() {
            if (this.freelist.length > this.freecheckedlist.length){
                this.isfreeallchk = false;
            } else {
                this.isfreeallchk = true;
            }
        },
        // 저장
        onSave() {
            if(this.checkValidation()) {
                if(confirm('저장 하시겠습니까?')) {
                    let params = {};
    
                    params.discountlist = this.discountlist;
                    params.freelist = this.freelist;
    
                    if(this.discdeletelist.length > 0) {
                        params.discdeletelist = this.discdeletelist;
                    }
                    if(this.freedeletelist.length > 0) {
                        params.freedeletelist = this.freedeletelist;
                    }
    
                    params.benenote = this.benenote;
                    params.internote = this.internote;
    
                    this.$http.post('/admin/operation/setting/cardbenefit/save', params)
                    .then(result => {
                        if(result.statusCode === 200) {
                            alert("저장 되었습니다.");
                            this.searchList();
                        }
                    })
                }
            }
        },
        // validation 체크
        checkValidation() {
            this.setEditorText();

            let msg = '';
            let valid = [
                {field: 'benenote', type: 'I', name: '카드혜택유의사항', required: true},
                {field: 'internote', type: 'I', name: '무이자유의사항', required: true},
            ];

            msg = this.$util.validMsg(this.$data, valid);
            if(!this.$util.isNull(msg)){
                alert(msg);
                return false;
            }

            if(this.discountlist.length === 0) {
                alert("카드사별 청구할인 정보을 추가해주세요.");
                return false;
            }
            if(this.freelist.length === 0){
                alert("카드사별 부분무이자 정보를 추가해주세요.");
                return false;
            }

            // 청구할인 validation 체크
            for(let i=0; i<this.discountlist.length; i++){
                let data = this.discountlist[i];
                if(this.$util.isNull(data.cardcompany)) {
                    alert("카드를 선택해주세요.");
                    return false;
                }
                if(this.$util.isNull(data.amount)) {
                    alert("금액(원)을 입력해주세요.");
                    return false;
                }
                if(this.$util.isNull(data.flatrate) && this.$util.isNull(data.fixedrate)) {
                    alert("정액(원) 또는 정률(%)를 입력해주세요.");
                    return false;
                }
                if(!this.$util.isNull(data.flatrate) && !this.$util.isNull(data.fixedrate)) {
                    if(data.flatrate != 0 && data.fixedrate != 0) {
                        alert("정액(원)과 정률(%) 중 하나만 입력해주세요.");
                        return false; 
                    }
                }
                if(this.$util.isNull(data.applysttime)) {
                    alert("청구할인 적용시작시분을 입력해주세요.");
                    return false;
                }
                if(this.$util.isNull(data.applyedtime)) {
                    alert("청구할인 적용종료시분을 입력해주세요.");
                    return false;
                }
                if(data.applysttime.length < 12) {
                    alert("청구할인 적용시작시분을 입력해주세요.");
                    return false;
                }
                if(data.applyedtime.length < 12) {
                    alert("청구할인 적용종료시분을 입력해주세요.");
                    return false;
                }
                if(data.applyedtime < data.applysttime) {
                    alert("청구할인 적용종료시분을 적용시작 이후로 입력해주세요.");
                    return false;
                }
            }

            // 카드사별 기간이 겹치는 구간이 있는지 체크 
            // 통과한다면 다시 체크 안하도록 체크된 코드에 넣어줌
            let checkCode = [];
            for(let i=0; i<this.discountlist.length; i++) {
                let data = this.discountlist[i];
                if(checkCode.indexOf(data.cardcompany) > -1) {
                    continue;
                }
                for(let j=i+1; j<this.discountlist.length; j++) {
                    let data2 = this.discountlist[j];
                    if(data.cardcompany === data2.cardcompany) {
                        if((data.applysttime >= data2.applysttime && data.applysttime < data2.applyedtime) || (data.applyedtime > data2.applysttime && data.applyedtime <= data2.applyedtime)) {
                            alert("각 카드사별 청구할인은 동일 기간 내 하나만 입력가능합니다.");
                            return false;
                        }
                    }
                }
                checkCode.push(data.cardcompany);
            }

            // 무이자 validation 체크
            for(let i=0; i<this.freelist.length; i++){
                let data = this.freelist[i];
                if(this.$util.isNull(data.cardcompany)) {
                    alert("카드를 선택해주세요.");
                    return false;
                }
                if(this.$util.isNull(data.month)) {
                    alert("개월(월)을 입력해주세요.");
                    return false;
                }
                if(this.$util.isNull(data.amount)) {
                    alert("금액(원)을 입력해주세요.");
                    return false;
                }
                if(this.$util.isNull(data.discnote)) {
                    alert("부분무이자 정책을 입력해주세요.");
                    return false;
                }
                if(this.$util.isNull(data.applysttime)) {
                    alert("부분무이자 적용시작시분을 입력해주세요.");
                    return false;
                }
                if(this.$util.isNull(data.applyedtime)) {
                    alert("부분무이자 적용종료시분을 입력해주세요.");
                    return false;
                }
                if(data.applysttime.length < 12) {
                    alert("부분무이자 적용시작시분을 입력해주세요.");
                    return false;
                }
                if(data.applyedtime.length < 12) {
                    alert("부분무이자 적용종료시분을 입력해주세요.");
                    return false;
                }
                if(data.applyedtime < data.applysttime) {
                    alert("부분무이자 적용종료시분을 적용시작 이후로 입력해주세요.");
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
        handleFileUpload: function(fileTypeKey, target, index) {
            // 엑셀올리기
            let file = this.$refs.excelFile;
            if (this.$util.isNull(file.files[0])) {
                return;
            }
            let fileType = ['application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'];
            if(!fileType.includes(file.files[0].type)){
                alert('엑셀 파일만 첨부 가능합니다.');
                file.value = null;
                return false;
            }
            
            if(file.files[0].size > 10485760){
                alert('파일 최대 크기는 10MB를 초과 할 수 없습니다.');
                file.value = null;
                return false;
            }
            this.excelFile = file.files[0];
        },
        // 엑셀파일 읽기
        readExcelFile: function(fileTypeKey,event) {
            let file = null;
            let headerInfo = [];
            this.excelFile = event.target.files[0];
            if (this.$util.isNull(this.excelFile)) {
                alert('파일을 선택해주세요');
                return;
            }
            file = this.excelFile;
            headerInfo = ['cardcompany', 'month', 'amount', 'discnote', 'applysttime', 'applyedtime'];
           
            let reader = new FileReader();
            let tmpResult = {};
            reader.onload = () => {
                let data = reader.result;
                let workbook = XLSX.read(data, {type: 'array'});
                workbook.SheetNames.forEach(sheetName => {
                    if (sheetName === 'Sheet1') {
                        const roa = XLSX.utils.sheet_to_json(workbook.Sheets[sheetName], {header: headerInfo, defval: ''});
                        if (roa.length) tmpResult = roa ;
                    }
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
        
            // 직매입옵션 일괄등록
            if (fileTypeKey.indexOf('excelFile') > -1) {
                let requiredHeader = ['cardcompany', 'month', 'amount', 'discnote', 'applysttime', 'applyedtime'];
                let requiredHeaderName = ['카드', '개월(월)', '금액(원)', '부분무이자 정책', '적용시작시분', '적용종료시분'];
                
                // 유효성 체크
                for (let i=1; i<excelData.length; i++) {
                    let item = excelData[i];
                    let keyset = Object.keys(item);
                    // 필수입력항목 체크
                    for (let j=0; j<keyset.length; j++) {
                        let key = keyset[j];
                        if (requiredHeader.indexOf(key)>-1 && this.$util.isNull(item[key])) {
                            alert("필수입력항목(" + requiredHeaderName[j] + ") 입력되지 않았습니다. 확인 후 진행해주세요.");
                            this.$refs.excelFile.value = '';
                            this.excelFile = null;
                            return;
                        }
                    }
                    
                    let cardcompany = item.cardcompany;
                    let cardcode = '';
                    for(let j=0; j<this.commonCode.cardcompany.length; j++) {
                        if(this.commonCode.cardcompany[j].codename === cardcompany) {
                            cardcode = this.commonCode.cardcompany[j].cmcode;
                            break;
                        }
                    }

                    let applysttime = '';
                    let applyedtime = '';        
                    item.applysttime = item.applysttime + '';
                    item.applyedtime = item.applyedtime + '';
                    
                    if(item.applysttime.length === 12) {    
                        applysttime = item.applysttime;
                    }
                    if(item.applyedtime.length === 12) {
                        applyedtime = item.applyedtime;
                    }

                    // 목록 추가
                    let temp = {
                        isdiscount: 'F',
                        cardcompany: cardcode,
                        amount: item.amount+'',
                        flatrate: '',
                        fixedrate: '',
                        month: item.month+'',
                        discnote: item.discnote,
                        applysttime: applysttime,
                        applyedtime: applyedtime,
                        istrash: 'F',
                    }
                    
                    let timeTemp = {
                        startYYYYMMDD: applysttime.length === 12 ? this.$util.getFormatDate(applysttime.substring(0,8),'-') : '',
                        startHH: applysttime.length === 12 ? applysttime.substring(8,10) : '',
                        startMi: applysttime.length === 12 ? applysttime.substring(10,12) : '',
                        endYYYYMMDD: applyedtime.length === 12 ? this.$util.getFormatDate(applyedtime.substring(0,8),'-')  : '',
                        endHH: applyedtime.length === 12 ? applyedtime.substring(8,10) : '',
                        endMi: applyedtime.length === 12 ? applyedtime.substring(10,12) : '',
                    }

                    this.freelist.push(temp);
                    this.freetimeinfo.push(timeTemp);
                }
                
                this.$refs.excelFile.value = '';
                this.excelFile = null;
            }
        },
        checkInputRate: function(obj, colName, value) {
            value = value+'';
            if (this.$util.isNull(value)) {
                obj[colName] = value;
                obj['old'+colName] = value;
            } else {
                var pattern = /^([0-9]{0,2}|100)[/.]?([0-9]{0,2})?$/;
                if (!pattern.test(value)) {
                    value = obj['old'+colName];
                }
                obj[colName] = value;
                obj['old'+colName] = value;
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
        'discountlist': {
                deep: true,
                handler(value) {
                value.forEach(item => {
                    item.amount = (item.amount+'').replace(/([^0-9])/gi, '');
                    item.flatrate = (item.flatrate+'').replace(/([^0-9])/gi, '');
                });
            },
        },
        'freelist': {
                deep: true,
                handler(value) {
                value.forEach(item => {
                    item.month = (item.month+'').replace(/([^0-9])/gi, '');
                    item.amount = (item.amount+'').replace(/([^0-9])/gi, '');
                });
            },
        },
    }
}
</script>

<style>

</style>