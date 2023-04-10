<template>
    <!-- 컨텐츠 영역 -->
    <div class="content m-leftmenu">
        <common-navigator></common-navigator>
        <CodeGroupDetail v-if="showClassDetail" v-bind:cmclass="cmclass" v-on:closeClassDetail="closeClassDetail"></CodeGroupDetail>
        <CodeGroupRegist v-if="showClassRegist" v-on:closeClassRegist="closeClassRegist"></CodeGroupRegist>
        <CodeDetail v-if="showCodeDetail" v-bind:code="code" v-on:closeCodeDetail="closeCodeDetail"></CodeDetail>
        <CodeRegist v-if="showCodeRegist" v-bind:cmclass="searchClass" v-on:closeCodeRegist="closeCodeRegist"></CodeRegist>
        <div class="inner">
            <div class="boxing search-area">
                <dl>
                    <dt>직접검색</dt>
                    <dd>
                        <input v-model="searchData.sword" type="text"  @keyup.enter="onSearch(1)"/>
                    </dd>
                </dl>
                <dl>
                    <dt>사용여부</dt>
                    <dd>
                        <div class="radio_wrap wide3">
                            <input type="radio" v-model="searchData.istrash" name="grouplist00" id="grouplist01" value=""  checked><label for="grouplist01">전체</label>
                            <input type="radio" v-model="searchData.istrash" name="grouplist00" id="grouplist02" value="F" ><label for="grouplist02">사용</label>
                            <input type="radio" v-model="searchData.istrash" name="grouplist00" id="grouplist03" value="T" ><label for="grouplist03">미사용</label>
                        </div>
                    </dd>
                </dl>
            </div>
            <div class="btn-group">
                <button type="button" class="btn big blue" v-if="isRead" @click="onSearch(1)">검색</button>
                <button type="button" class="btn big gray" v-if="isRead" @click="onSearchDataReset">초기화</button>
            </div>
            <div class="caption-group mt10 clearfix">
                <div class="total-group fl">
                    <span class="total">전체 <strong>{{pagingData.listCount}}</strong>건</span>
                </div>
                <div class="btn-group fr">
                    <button type="button" class="btn blue-line" v-if="isWrite" @click="onChangeUse('G','F')">사용</button>
                    <button type="button" class="btn red-line" v-if="isWrite" @click="onChangeUse('G','T')">미사용</button>
                    <button type="button" class="btn blue-line" v-if="isRead" @click="onSearchCode">공통코드 조회</button>
                    <button type="button" class="btn green-line" v-if="isRead" @click="fnExcelDownload('G')"><i class="icon-excel"></i>엑셀다운로드</button>
                    <select v-model="pagingData.pageCount" v-if="isRead">
                        <option value="20">20개씩 보기</option>
                        <option value="50">50개씩 보기</option>
                        <option value="100">100개씩 보기</option>
                    </select>
                </div>
            </div>
            <table cellpadding="0" cellspacing="0" class="data-tb align-c">
                <caption>공통코드그룹 목록</caption>
                <colgroup>
                    <col width="3%" /><!-- radio -->
                    <col width="5%" /><!-- No -->
                    <col width="20%" /><!-- 공통코드 그룹 -->
                    <col width="20%" /><!-- 공통코드 그룹명 -->
                    <col width="" /><!-- 설명 -->
                    <col width="10%" /><!-- 사용여부 -->
                </colgroup>
                <thead>
                    <tr>
                        <th>선택</th>
                        <th>No</th>
                        <th>공통코드 그룹</th>
                        <th>공통코드 그룹명</th>
                        <th>설명</th>
                        <th>사용여부</th>
                    </tr>
                </thead>
                <tbody v-if="this.classList.length > 0">
                    <tr v-for="(row, index) in this.classList" :key="index">
                        <td><input type="radio" v-model="checkClass" name="group00" :id="'radioclass'+index" class="circle" :value="row.cmclass"/></td>
                        <td>{{row.no}}</td>
                        <td><a class="link" @click="openClassDetail(row.cmclass)">{{row.cmclass}}</a></td>
                        <td>{{row.classname}}</td>
                        <td>{{row.detail}}</td>
                        <td>{{row.istrash}}</td>
                    </tr>
                </tbody>
                <tbody v-else>
                    <tr><td colspan="6">대상 공통코드그룹이 없습니다.</td></tr>
                </tbody>
            </table>
            <div class="bottom-group">
                <div class="paging">
                    <CommonPageNavigator v-show="isRead" v-bind:pagingData="this.pagingData"
                               v-on:setPagingData="setPagingData"></CommonPageNavigator>
                </div>
                <div class="btn-group">
                    <button type="button" class="btn blue" v-if="isWrite" @click="openClassRegist">공통코드그룹 등록</button>
                </div>
            </div>
            <hr class="dash" />
            <div class="caption-group mt10 clearfix">
                <div class="total-group fl">
                    <span class="total">전체 <strong>{{codeList.length}}</strong>건</span>
                </div>
                <div class="btn-group fr">
                    <button type="button" class="btn blue-line" v-if="isWrite" @click="onChangeUse('C','F')">사용</button>
                    <button type="button" class="btn red-line" v-if="isWrite" @click="onChangeUse('C','T')">미사용</button>
                    <button type="button" class="btn green-line" v-if="isRead" @click="fnExcelDownload('C')"><i class="icon-excel"></i>엑셀다운로드</button>
                </div>
            </div>
            <div class="scroll-y" style="max-height: 500px;">
                <table cellpadding="0" cellspacing="0" class="data-tb align-c">
                    <caption>공통코드 목록</caption>
                    <colgroup>
                        <col width="3%" /><!-- radio -->
                        <col width="5%" /><!-- No -->
                        <col width="20%" /><!-- 공통코드 -->
                        <col width="20%" /><!-- 공통코드명 -->
                        <col width="" /><!-- 설명 -->
                        <col width="20%" /><!-- 비고 -->
                        <col width="10%" /><!-- 사용여부 -->
                    </colgroup>
                    <thead>
                        <tr>
                            <th>선택</th>
                            <th>No</th>
                            <th>공통코드</th>
                            <th>공통코드명</th>
                            <th>설명</th>
                            <th>비고</th>
                            <th>사용여부</th>
                        </tr>
                    </thead>
                    <tbody v-if="this.codeList.length > 0">
                        <tr v-for="(code, num) in this.codeList" :key="num">
                            <td><input type="radio" v-model="checkCode" name="group01" :id="'radiocode'+num" class="circle" :value="code"/></td>
                            <td>{{code.no}}</td>
                            <td><a class="link" @click="openCodeDetail(code)">{{code.cmcode}}</a></td>
                            <td>{{code.codename}}</td>
                            <td>{{code.detail}}</td>
                            <td>{{code.note}}</td>
                            <td>{{code.istrash}}</td>
                        </tr>
                    </tbody>
                    <tbody v-else>
                        <tr><td colspan="7">대상 공통코드가 없습니다.</td></tr>
                    </tbody>
                </table>
            </div>
            <div class="bottom-group">
                <div class="btn-group">
                    <button type="button" class="btn blue" v-if="isWrite" @click="openCodeRegist">공통코드 등록</button>
                </div>
            </div>
        </div>
    </div>
    <!-- /컨텐츠 영역 -->
</template>

<script>
import CommonNavigator from '@views.admin/common/CommonNavigator'
import CommonPageNavigator from "@views.admin/common/CommonPageNavigator";
import CodeGroupRegist from "./CodeGroupRegist.vue";
import CodeGroupDetail from "./CodeGroupDetail.vue";
import CodeRegist from "./CodeRegist.vue";
import CodeDetail from "./CodeDetail.vue";
export default {
    name: 'admin.configuration.manage.codelist',
    components: {
        CommonNavigator,
        CommonPageNavigator,
        CodeGroupRegist,
        CodeGroupDetail,
        CodeRegist,
        CodeDetail
    },
    data() {
        return {
            searchData: {
                sword: '',      // 검색단어
                istrash: '',    // 사용여부
            },
            pagingData: {
                pageCount: 20,      // 페이징 옵션(최대수)
                page: 1,            // 현재 페이지
                listCount: 0        // 총 수량
            },
            searchClass: '',    // 조회한 그룹
            checkClass: '',     // 선택한 공통코드그룹
            checkCode: '',      // 선택한 공통코드
            classList: [],      // 공통코드그룹 데이터
            codeList: [],       // 공통코드 데이터
            cmclass: '',        // 상세팝업 공통코드그룹
            code: {cmclass: '',cmcode:''},         // 상세팝업 공통코드
            showClassRegist: false, // 공통코드그룹 등록팝업 오픈여부
            showClassDetail: false, // 공통코드그룹 상세팝업 오픈여부
            showCodeRegist: false,  // 공통코드 등록팝업 오픈여부
            showCodeDetail: false,  // 공통코드 상세팝업 오픈여부
            isRead: false,
            isWrite: false,
        }
    },
    mounted() {
        this.$http.post('/admin/common/pageAuth/check', {url : this.$options.name, isloading: false}).then(result => {
            this.isRead = (result.data.isread === 'T');
            this.isWrite = (result.data.iswrite === 'T');

            if(this.isRead) {
                this.$util.componentSetSearchParam(this);
                this.onSearch();
            }
        }).catch(error => {
            this.$util.debug(error);
        });
    },
    methods: {
        onSearch(page) {
            let params = this.searchData;
            params.pageCount = this.pagingData.pageCount;
            params.page = (typeof page === 'undefined') ? this.pagingData.page : 1;
            params.listCount = this.pagingData.listCount;

            this.$http.post('/admin/configuration/manage/code/search', params)
            .then(result => {
                if(result.statusCode === 200) {
                    let data = result.data;
                    this.classList = data.list;
                    this.codeList = [];
                    this.pagingData.listCount = data.total_cnt;
                    this.checkClass = '';
                    this.checkCode = '';
                    this.searchClass = '';
                    this.$util.dataSetSearchParam(this);
                }
            })
            .catch(error => {
                this.$util.debug(error);
            })
        },
        // 검색조건 초기화
        onSearchDataReset() {
            this.searchData = this.$options.data().searchData;
        },
        // 공통코드 조회
        onSearchCode() {
            if(this.$util.isNull(this.checkClass)){
                alert("선택된 공통코드 그룹이 없습니다.");
                return ;
            }

            let param = {
                cmclass: this.checkClass
            }

            this.$http.post('/admin/configuration/manage/code/search/code', param)
            .then(result => {
                if(result.statusCode === 200) {
                    let data = result.data;
                    this.codeList = data.list;
                    this.searchClass = this.checkClass;
                    this.checkCode = '';
                }
            })
            .catch(error => {
                this.$util.debug(error);
            })
        },
        // 페이징 콜백
        setPagingData(param){
            this.pagingData = param;
            this.onSearch();
        },
        // 엑셀다운로드
        fnExcelDownload(type) {
            let param = this.searchData;
            // 코드그룹 type : 'G', 코드 type : 'C'
            param.type = type;
            // 검색한 그룹
            param.cmclass = this.searchClass;
            let postConfig = { responseType: 'arraybuffer' };
            this.$http.post("/admin/configuration/manage/code/excel", param, postConfig)
            .then(result => {
                this.$util.debug(result);
            })
            .catch(error => {
                this.$util.debug(error);
            });
        },
        onChangeUse(type, use){
            let params = {
                type: type,
                istrash : use
            }
            // 코드그룹 type : 'G', 코드 type : 'C'
            if(type === 'G') {
                if(this.checkClass === ''){
                    alert("선택된 공통그룹코드가 없습니다.");
                    return false;
                }
                params.cmclass = this.checkClass;
            } else {
                if(this.checkCode === ''){
                    alert("선택된 공통코드가 없습니다.");
                    return false;
                }
                params.cmclass = this.checkCode.cmclass;
                params.cmcode = this.checkCode.cmcode;
            }

            if(confirm("상태를 전환하시겠습니까?")){
                this.$http.post("/admin/configuration/manage/code/update", params)
                .then(result => {
                    if(result.statusCode === 200) {
                        alert("상태가 수정되었습니다.");
                        if(type === 'G') {
                            this.onSearch();
                        } else {
                            this.onSearchCode();
                        }
                    }
                })
                .catch(error => {
                    this.$util.debug(error);
                });
            }
        },
        openClassRegist() {
            this.showClassRegist = true;
        },
        closeClassRegist() {
            this.showClassRegist = false;
            // this.onSearch();
        },
        openCodeRegist() {
            if(this.searchClass === '') {
                alert("공통코드그룹을 조회 후 다시 시도해주세요.");
                return;
            }
            this.showCodeRegist = true;
        },
        closeCodeRegist() {
            this.showCodeRegist = false;
            // this.onSearchCode();
        },
        openClassDetail(cmclass) {
            this.cmclass = cmclass;
            this.showClassDetail = true;
        },
        closeClassDetail(isreload) {
            this.showClassDetail = false;
            if(isreload) {
                this.onSearch();
            }
        },
        openCodeDetail(code) {
            this.code = code;
            this.showCodeDetail = true;
        },
        closeCodeDetail(isreload) {
            this.showCodeDetail = false;
            if(isreload) {
                this.onSearchCode();
            }
        }
    },
}
</script>

<style>

</style>